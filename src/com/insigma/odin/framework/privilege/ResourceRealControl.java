package com.insigma.odin.framework.privilege;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.entity.SmtDatagroup;
import com.insigma.odin.framework.privilege.entity.SmtFunction;
import com.insigma.odin.framework.privilege.entity.SmtResource;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.helper.ResourceHelper;
import com.insigma.odin.framework.privilege.util.AbstractPrivilegeBase;
import com.insigma.odin.framework.privilege.util.BeanUtil;
import com.insigma.odin.framework.privilege.util.PrivilegeUtil;
import com.insigma.odin.framework.privilege.vo.FunctionVO;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.ResourceVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.tree.CheckTreeNode;
import com.insigma.odin.framework.tree.TreeNode;
import com.insigma.odin.framework.tree.TreeUtil;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.siis.local.business.entity.LogMain;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;

/**
 * 资源处理类
 * 
 * @author jinwei
 * @version 1.0
 * @created 18-九月-2009 11:48:11
 */
public class ResourceRealControl extends AbstractPrivilegeBase implements
		IResourceControl {

	private LogUtil applog = new LogUtil();
	// 记录日志信息
	private Logger log = Logger.getLogger(ResourceRealControl.class);
	// 查询所用的HQL
	private String hql = "";
	// 使用参数查询时HQL里的参数集
	private HashMap<String, Object> params = new HashMap<String, Object>();

	public ResourceRealControl() {

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * 该方法未实现。用来判断该用户是否有权访问该资源
	 * @param userId
	 * @param resourceId
	 */
	public boolean isResourcePermit(String userId, String resourceId) {
		return false;
	}

	/**
	 * 该方法未实现。用来获取当前登录者可见的资源集
	 * @param cueUser
	 * @param start
	 * @param limit
	 */
	public List<Object> queryByUser(UserVO cueUser, int start, int limit,
			HashMap params) {
		return null;
	}

	/**
	 * 根据资源名称来查询该资源
	 * @param name	所传入的资源名称
	 * @param isEquel  是否精确查询。true表示精确查询，false表示模糊查询。
	 * @return 存放资源集合的List列表
	 * @throws PrivilegeException
	 */
	public List queryByName(String name, boolean isEqual)
			throws PrivilegeException {
		String hql = "from SmtResource where name=:name";
		params.clear();
		params.put("name", name);
		return PrivilegeUtil.getVOList(query(hql, params), ResourceVO.class);
	}

	/**
	 * 获取所有的公共资源。即所有人都有权访问的资源
	 * @throws PrivilegeException
	 * @return 存放公共资源的list列表
	 */
	public List<Object> getPublicResource() throws PrivilegeException {
		hql = "from SmtResource t where t.publicflag!=:publicflag and t.status=:status";
		params.clear();
		params.put("publicflag", ResourceHelper.RF_PUB_FLAG_E);
		params.put("status", ResourceHelper.RESOURCE_STATUS_R);
		List<Object> list = super.query(hql, params);
		return PrivilegeUtil.getVOList(list, ResourceVO.class);
	}

	/**
	 * 获取用户在该场景下所拥有的资源及其对应的功能信息列表(暂时不根据场景查询)
	 * @param cueUser  当前的登陆用户UserVO类
	 * @param sceneID  场景ID
	 * @param isNodeAndLeaf  预留参数，是否仅仅是节点和叶子，false则为全部
	 * @return list资源列表
	 * @throws PrivilegeException
	 */
	public List<Object> getUserFunctions(UserVO cueUser, String sceneID,
			boolean isNodeAndLeaf) throws PrivilegeException {
		// 查找用户所拥有的有效功能
		PrivilegeManager pri = super.getPrivilegeManager();
		SmtUser user = (SmtUser) super.get(SmtUser.class, cueUser.getId());
		UserVO userVO = new UserVO();
		BeanUtil.propertyCopy(user, userVO);
		if(userVO.getId().equals(GlobalNames.sysConfig.get("SUPER_ID"))){
			userVO.setLoginname(cueUser.getLoginname());
			userVO.setPasswd(cueUser.getPasswd());
		}
		List<Object> funcList = new ArrayList<Object>();
		String rolehql = "select t from SmtAct t,SmtAcl l where (t.objectid='"+userVO.getId()+"' or t.objectid in (select ug.groupid from SmtUsergroupref ug where ug.userid='"+userVO.getId()+"')) and  t.roleid=l.roleid and l.resourceid='RESOURCE_ALL'";
		boolean isResAll = false;
		if(query(rolehql, params).size()>0) {
			isResAll = true;
		}
		
		if (!pri.getIPermission().isSuperManager(userVO) && !pri.getIPermission().isSysPermission(userVO, "RESOURCE_ALL") && !isResAll) {
			List<GroupVO> gList = pri.getIGroupControl().getGroupsByUserId(cueUser.getId());
			String sectype = "";
			try {
				sectype = HBUtil.getValueFromTab("sectype", "smt_user", "userid='"+userVO.getId()+"'");
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(gList==null || gList.size()==0){  //处理用户没有用户组的情况 jinwei 2011.3.28
				hql = "select distinct f from SmtFunction f,SmtResource r,SmtAct t,SmtAcl l where "
					+ "f.resourceid = r.id and r.status='"
					+ ResourceHelper.RESOURCE_STATUS_R
					+ "' and l.resourceid = r.id "
					
					//密级标识
		            +" and (f.param3 is null or f.param3 like '"+sectype+"'||'%') "
		            
					+ "and t.roleid = l.roleid and t.objectid=:userID order by f.parent,f.orderno";
				
				params.clear();
				params.put("userID", userVO.getId());
			}else{
				/*hql = "select distinct f from SmtFunction f,SmtResource r,SmtAct t,SmtAcl l,SmtUsergroupref ug where "
						+ "f.resourceid = r.id and r.status='"
						+ ResourceHelper.RESOURCE_STATUS_R
						+ "' and l.resourceid = r.id "
						+ "and t.roleid = l.roleid and (t.objectid=:userID or (ug.userid=:userID and t.objectid = ug.groupid) ) order by f.parent,f.orderno";*/
				//				this.hql = "select distinct f from SmtFunction f,SmtResource r,SmtAct t,SmtAcl l,SmtUsergroupref ug where f.resourceid = r.id and r.status='1' and l.resourceid = r.id and t.roleid = l.roleid and  (ug.userid=:userID and t.objectid = ug.groupid) "
				//		    	  		+"union all  select distinct f from SmtFunction f,SmtResource r,SmtAct t,SmtAcl l where f.resourceid = r.id and r.status='1' and l.resourceid = r.id and t.roleid = l.roleid and (t.objectid=:userID  ) ";
		
			
				//jinwei 2016.6.8 调整，去掉SmtUsergroupref关联
		        StringBuilder b = new StringBuilder();
		        for(int i=0;i<gList.size();i++){
		          GroupVO g = gList.get(i);
		          if(i==0){
		            b.append("'").append(g.getId()).append("'");
		          }else{
		            b.append(",").append("'").append(g.getId()).append("'");
		          }
		        }
		        b.append(",").append("'").append(userVO.getId()).append("'");
		        hql = "select distinct f from SmtFunction f,SmtResource r,SmtAct t,SmtAcl l,SmtRole ro where "
		            + "f.resourceid = r.id and r.status='"
		            + ResourceHelper.RESOURCE_STATUS_R
		            + "' and l.resourceid = r.id "
		            + " and t.roleid = ro.id "
		           // + " and ro.roletype = '1' "
		            //密级标识
		            +" and (f.param3 is null or f.param3 like '"+sectype+"'||'%') "
		            
		            + "and t.roleid = l.roleid and t.objectid in("+b.toString()+") order by f.parent,f.orderno";
			
			}
			
			
			
			
			funcList = super.query(hql, params);
	     
			
			
			
		} else {
			hql = "select f from SmtFunction f,SmtResource r where f.resourceid = r.id and r.status='"
					+ ResourceHelper.RESOURCE_STATUS_R + "' order by f.parent,f.orderno";
			funcList = super.query(hql, params);
		}
		
		//记录登陆日志
		HBSession sess = HBUtil.getHBSession();
		
		LogMain logmain = new LogMain();
		logmain.setSystemlogid(UUID.randomUUID().toString().trim().replaceAll("-", "")); 							//主键id
		logmain.setUserlog(user.getId()); 	//操作用户（此处为用户登录名）。
		logmain.setSystemoperatedate(new Date()); 			//系统操作时间
		logmain.setEventtype("登陆系统"); 				//具体操作
		logmain.setEventobject(""); 				//操作项类型（信息集）
		logmain.setObjectid("1111111"); 						//操作涉及对象编码
		logmain.setObjectname("系统");   			//当前操作所涉及对象的名称
		sess.save(logmain);
		sess.flush();
		
		return PrivilegeUtil.getVOList(funcList, FunctionVO.class);

	}

	/**
	 * 验证散列值
	 */
	public boolean validateHashCode(Object obj) throws PrivilegeException {
		ResourceVO resVO = (ResourceVO) obj;
		return super.validateHashCode(resVO.getId(), resVO.getHashcode(),
				SmtResource.class);
	}
	
	/**
	 * 保存或更新资源。根据传入的资源ID来判断进行保存或更新操作：ID存在则进行更新操作；不存在则进行保存操作。
	 * @param resource  需要保存或修改的资源
	 * @return 操作成功返回true，失败则返回false
	 * @throws PrivilegeException
	 */
	public boolean saveOrUpdateResource(ResourceVO resource)
			throws PrivilegeException {
		if (resource == null) {
			return false;
		}
		if (isNullOrEmpty(resource.getStatus())) {
			resource.setStatus("1");
		}
		if (isNullOrEmpty(resource.getParent())) {
			//throw new PrivilegeException("请指定父资源ID！");
		} else {
			if (get(SmtResource.class, resource.getParent()) == null) {
				throw new PrivilegeException("父资源ID不存在！");
			}
		}
		if (isNullOrEmpty(resource.getOwner())) {
			resource.setOwner(getPrivilegeManager().getCueLoginUser().getId());
		}
		if (resource.getType() == null || resource.getType().trim().equals("")) {
			throw new PrivilegeException("资源类型未指定！");
		}
		SmtResource smtresource = new SmtResource();
		BeanUtil.propertyCopy(resource, smtresource);
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		if (!isNullOrEmpty(resource.getId())) {
			if(smtresource.getOwner()==null || smtresource.getOwner().trim().length()==0) {
				smtresource.setOwner(this.getPrivilegeManager().getCueLoginUser().getId());
			}
			getPrivilegeManager().getHbSession().saveOrUpdate(smtresource);
			privilegeLog.log("更新资源", "调用IResourceControl接口的saveOrUpdateResource方法", null);
			return true;
		}
		save(smtresource);
		privilegeLog.log("保存资源"+smtresource.getName(), "调用IResourceControl接口的saveOrUpdateResource方法", null);
		return true;
	}

	/**
	 * 根据资源ID查询其子节点资源
	 * @param parentId  资源ID
	 * @return  子资源集合list
	 * @throws PrivilegeException
	 */
	public List findByParentId(String parentId) throws PrivilegeException {
		String hql = "from SmtFunction where ";
		HashMap<String, String> params = new HashMap<String, String>();
		if(parentId==null || "null".equals(parentId.toLowerCase())){
			hql += " (parent is null or parent='') ";
		}else{
			hql += "parent=:parentId ";
			params.put("parentId", parentId);
		}
		hql += " order by orderno ";
		return PrivilegeUtil.getVOList(query(hql, params), FunctionVO.class);
	}

	/**
	 * 保存或更新Function。根据传入的function的ID来判断进行保存或更新操作：ID存在则进行更新操作；不存在则进行保存操作。
	 * @param function  需要保存或修改的Function
	 * @return 操作成功返回true，失败则返回false
	 * @throws PrivilegeException
	 */
	public boolean saveOrUpdateFunction(FunctionVO function)
			throws PrivilegeException {
		SmtFunction sfunction = new SmtFunction();
		BeanUtil.propertyCopy(function, sfunction);
		if (isNullOrEmpty(sfunction.getLog())) {
			sfunction.setLog("0");
		}
		if (sfunction.getOrderno() == null
				|| isNullOrEmpty(sfunction.getOrderno().toString())) {
			sfunction.setOrderno((long) (findByParentId(
					sfunction.getParent()).size() + 1));
		}
		if(sfunction.getActive()==null) {
			sfunction.setActive("1");
		}
		if(sfunction.getAuflag()==null) {
			sfunction.setAuflag("0");
		}
		if(sfunction.getRpflag()==null) {
			sfunction.setRpflag("0");
		}
		if(sfunction.getUptype()==null) {
			sfunction.setUptype("9");
		}
		if(sfunction.getPublicflag()==null) {
			sfunction.setPublicflag("0");
		}
		if(sfunction.getRbflag()==null) {
			sfunction.setRbflag("1");
		}
		if(sfunction.getReauflag()==null) {
			sfunction.setReauflag("0");
		}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		if(sfunction.getFunctionid()==null ) {
			save(sfunction);
			privilegeLog.log("保存function"+sfunction.getTitle(), "调用IResourceControl接口的saveOrUpdateFunction方法", null);
		}else {
			update(sfunction);
			privilegeLog.log("更新functio", "调用IResourceControl接口的saveOrUpdateFunction方法", null);
		}
		return true;
	}

	/*
	 *  判断一个字符串值是否是null或“”
	 */
	private boolean isNullOrEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}

	/**
	 * 根据传入Function的ID来删除Function，同时删除相应的资源和其子function
	 * @param functionid  所要删除function的ID
	 * @return 删除成功返回true，失败则返回false
	 * @throws PrivilegeException
	 */
	public boolean deleteFunction(String functionid) throws PrivilegeException {
		SmtFunction function = (SmtFunction) get(SmtFunction.class, functionid);
		SmtResource resource = (SmtResource) get(SmtResource.class, function.getResourceid());
		if (function != null) {
			isHaveOplog(functionid);
			delete(function);
			if(resource!= null){
				delete(resource);
			}			
			deleteFunctionByParent(functionid);
			PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
			String logstr="";
			if(resource!=null){
				logstr=logstr+"删除资源"+resource.getName()+"的同时";
			}
			privilegeLog.log(logstr+"删除function"+function.getTitle(), "调用IResourceControl接口的deleteFunctionn方法", null);
			return true;
		}
		return false;
	}

	private boolean isHaveOplog(String functionid) throws PrivilegeException{
		if(DBUtil.getDBType().equals(DBType.MYSQL))
			return false;
		boolean rtn = false;
		if(HBUtil.getHBSession().createQuery("from SbdsUserlog g where g.functionid='"+functionid+"'").list().size()>0){
			throw new PrivilegeException("该模块已经办理过业务，不允许删除！");
		}
		return rtn;
	}
	
	/*
	 * 根据父function删除其子function
	 */
	private boolean deleteFunctionByParent(String functionid) throws PrivilegeException {
		String hql = "from SmtFunction where parent='"+functionid+"'";
		List functions = query(hql, null);
		for(int i=0;i<functions.size();i++) {
			SmtFunction sf = (SmtFunction) functions.get(i);
			isHaveOplog(sf.getFunctionid());
			deleteFunction(sf.getFunctionid());
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<TreeNode> getUserFunctionsTree(List<Object> funcs,String roleid,String nodeId,boolean isLoadAll){
		Collections.sort(funcs,new Comparator(){
			public int compare(Object arg0, Object arg1) {
				if(arg0 instanceof FunctionVO && arg1 instanceof FunctionVO) {
					FunctionVO func1 =  (FunctionVO) arg0;
					FunctionVO func2 =  (FunctionVO) arg1;
					int fn1=func1.getOrderno().intValue();
					int fn2=func2.getOrderno().intValue();
					int num=0;
					
					if(fn1>fn2){
						num=1;
					}
					if(fn1<fn2){
						num=-1;
					}
					if(fn1==fn2){
						num=0;
					}
					return num;
				}
				return 0;
		}});
		String acl = "select s.resourceid from SMT_ACL s where s.roleid='"+roleid+"'";
		HBSession sess = HBUtil.getHBSession();
		List aclList = sess.createSQLQuery(acl).list();
		List<TreeNode> root = transData(funcs,aclList,isLoadAll);
		return root;
	}
	
	private List<TreeNode> transData(List<Object> list ,List aclList, boolean isLoadAll){
		List<TreeNode>  treeNode = new ArrayList<TreeNode>();
		for(Iterator<Object> it = list.iterator();it.hasNext();){
			FunctionVO g = (FunctionVO) it.next();
			CheckTreeNode n = new CheckTreeNode();
			n.setId(g.getFunctionid());
			n.setText(g.getTitle());
			n.setParent(g.getParent());
			n.setChecked(false);
			for(int i=0;i<aclList.size();i++) {
				if(g.getFunctionid().equals(aclList.get(i))){
					n.setChecked(true);
					break;
				}
			}
			if(!isLoadAll){
				for(Iterator<Object> it2 = list.iterator();it2.hasNext();){
					FunctionVO f = (FunctionVO) it2.next();
					if(g.getFunctionid().equals(f.getParent())){
						n.setLeaf(false);
						break;
					}
				}
			}
			treeNode.add(n);
		}
		if(isLoadAll){
			treeNode = TreeUtil.buildTree(treeNode);
		}
		return treeNode;
	}
}