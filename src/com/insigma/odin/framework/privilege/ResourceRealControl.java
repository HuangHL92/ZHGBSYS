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
 * ��Դ������
 * 
 * @author jinwei
 * @version 1.0
 * @created 18-����-2009 11:48:11
 */
public class ResourceRealControl extends AbstractPrivilegeBase implements
		IResourceControl {

	private LogUtil applog = new LogUtil();
	// ��¼��־��Ϣ
	private Logger log = Logger.getLogger(ResourceRealControl.class);
	// ��ѯ���õ�HQL
	private String hql = "";
	// ʹ�ò�����ѯʱHQL��Ĳ�����
	private HashMap<String, Object> params = new HashMap<String, Object>();

	public ResourceRealControl() {

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * �÷���δʵ�֡������жϸ��û��Ƿ���Ȩ���ʸ���Դ
	 * @param userId
	 * @param resourceId
	 */
	public boolean isResourcePermit(String userId, String resourceId) {
		return false;
	}

	/**
	 * �÷���δʵ�֡�������ȡ��ǰ��¼�߿ɼ�����Դ��
	 * @param cueUser
	 * @param start
	 * @param limit
	 */
	public List<Object> queryByUser(UserVO cueUser, int start, int limit,
			HashMap params) {
		return null;
	}

	/**
	 * ������Դ��������ѯ����Դ
	 * @param name	���������Դ����
	 * @param isEquel  �Ƿ�ȷ��ѯ��true��ʾ��ȷ��ѯ��false��ʾģ����ѯ��
	 * @return �����Դ���ϵ�List�б�
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
	 * ��ȡ���еĹ�����Դ���������˶���Ȩ���ʵ���Դ
	 * @throws PrivilegeException
	 * @return ��Ź�����Դ��list�б�
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
	 * ��ȡ�û��ڸó�������ӵ�е���Դ�����Ӧ�Ĺ�����Ϣ�б�(��ʱ�����ݳ�����ѯ)
	 * @param cueUser  ��ǰ�ĵ�½�û�UserVO��
	 * @param sceneID  ����ID
	 * @param isNodeAndLeaf  Ԥ���������Ƿ�����ǽڵ��Ҷ�ӣ�false��Ϊȫ��
	 * @return list��Դ�б�
	 * @throws PrivilegeException
	 */
	public List<Object> getUserFunctions(UserVO cueUser, String sceneID,
			boolean isNodeAndLeaf) throws PrivilegeException {
		// �����û���ӵ�е���Ч����
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
			if(gList==null || gList.size()==0){  //�����û�û���û������� jinwei 2011.3.28
				hql = "select distinct f from SmtFunction f,SmtResource r,SmtAct t,SmtAcl l where "
					+ "f.resourceid = r.id and r.status='"
					+ ResourceHelper.RESOURCE_STATUS_R
					+ "' and l.resourceid = r.id "
					
					//�ܼ���ʶ
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
		
			
				//jinwei 2016.6.8 ������ȥ��SmtUsergroupref����
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
		            //�ܼ���ʶ
		            +" and (f.param3 is null or f.param3 like '"+sectype+"'||'%') "
		            
		            + "and t.roleid = l.roleid and t.objectid in("+b.toString()+") order by f.parent,f.orderno";
			
			}
			
			
			
			
			funcList = super.query(hql, params);
	     
			
			
			
		} else {
			hql = "select f from SmtFunction f,SmtResource r where f.resourceid = r.id and r.status='"
					+ ResourceHelper.RESOURCE_STATUS_R + "' order by f.parent,f.orderno";
			funcList = super.query(hql, params);
		}
		
		//��¼��½��־
		HBSession sess = HBUtil.getHBSession();
		
		LogMain logmain = new LogMain();
		logmain.setSystemlogid(UUID.randomUUID().toString().trim().replaceAll("-", "")); 							//����id
		logmain.setUserlog(user.getId()); 	//�����û����˴�Ϊ�û���¼������
		logmain.setSystemoperatedate(new Date()); 			//ϵͳ����ʱ��
		logmain.setEventtype("��½ϵͳ"); 				//�������
		logmain.setEventobject(""); 				//���������ͣ���Ϣ����
		logmain.setObjectid("1111111"); 						//�����漰�������
		logmain.setObjectname("ϵͳ");   			//��ǰ�������漰���������
		sess.save(logmain);
		sess.flush();
		
		return PrivilegeUtil.getVOList(funcList, FunctionVO.class);

	}

	/**
	 * ��֤ɢ��ֵ
	 */
	public boolean validateHashCode(Object obj) throws PrivilegeException {
		ResourceVO resVO = (ResourceVO) obj;
		return super.validateHashCode(resVO.getId(), resVO.getHashcode(),
				SmtResource.class);
	}
	
	/**
	 * ����������Դ�����ݴ������ԴID���жϽ��б������²�����ID��������и��²���������������б��������
	 * @param resource  ��Ҫ������޸ĵ���Դ
	 * @return �����ɹ�����true��ʧ���򷵻�false
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
			//throw new PrivilegeException("��ָ������ԴID��");
		} else {
			if (get(SmtResource.class, resource.getParent()) == null) {
				throw new PrivilegeException("����ԴID�����ڣ�");
			}
		}
		if (isNullOrEmpty(resource.getOwner())) {
			resource.setOwner(getPrivilegeManager().getCueLoginUser().getId());
		}
		if (resource.getType() == null || resource.getType().trim().equals("")) {
			throw new PrivilegeException("��Դ����δָ����");
		}
		SmtResource smtresource = new SmtResource();
		BeanUtil.propertyCopy(resource, smtresource);
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		if (!isNullOrEmpty(resource.getId())) {
			if(smtresource.getOwner()==null || smtresource.getOwner().trim().length()==0) {
				smtresource.setOwner(this.getPrivilegeManager().getCueLoginUser().getId());
			}
			getPrivilegeManager().getHbSession().saveOrUpdate(smtresource);
			privilegeLog.log("������Դ", "����IResourceControl�ӿڵ�saveOrUpdateResource����", null);
			return true;
		}
		save(smtresource);
		privilegeLog.log("������Դ"+smtresource.getName(), "����IResourceControl�ӿڵ�saveOrUpdateResource����", null);
		return true;
	}

	/**
	 * ������ԴID��ѯ���ӽڵ���Դ
	 * @param parentId  ��ԴID
	 * @return  ����Դ����list
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
	 * ��������Function�����ݴ����function��ID���жϽ��б������²�����ID��������и��²���������������б��������
	 * @param function  ��Ҫ������޸ĵ�Function
	 * @return �����ɹ�����true��ʧ���򷵻�false
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
			privilegeLog.log("����function"+sfunction.getTitle(), "����IResourceControl�ӿڵ�saveOrUpdateFunction����", null);
		}else {
			update(sfunction);
			privilegeLog.log("����functio", "����IResourceControl�ӿڵ�saveOrUpdateFunction����", null);
		}
		return true;
	}

	/*
	 *  �ж�һ���ַ���ֵ�Ƿ���null�򡰡�
	 */
	private boolean isNullOrEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}

	/**
	 * ���ݴ���Function��ID��ɾ��Function��ͬʱɾ����Ӧ����Դ������function
	 * @param functionid  ��Ҫɾ��function��ID
	 * @return ɾ���ɹ�����true��ʧ���򷵻�false
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
				logstr=logstr+"ɾ����Դ"+resource.getName()+"��ͬʱ";
			}
			privilegeLog.log(logstr+"ɾ��function"+function.getTitle(), "����IResourceControl�ӿڵ�deleteFunctionn����", null);
			return true;
		}
		return false;
	}

	private boolean isHaveOplog(String functionid) throws PrivilegeException{
		if(DBUtil.getDBType().equals(DBType.MYSQL))
			return false;
		boolean rtn = false;
		if(HBUtil.getHBSession().createQuery("from SbdsUserlog g where g.functionid='"+functionid+"'").list().size()>0){
			throw new PrivilegeException("��ģ���Ѿ������ҵ�񣬲�����ɾ����");
		}
		return rtn;
	}
	
	/*
	 * ���ݸ�functionɾ������function
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