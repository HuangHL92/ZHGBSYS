package com.insigma.siis.local.pagemodel.sysmanager.role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Transaction;

import net.sf.json.JSONArray;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtAcl;
import com.insigma.odin.framework.privilege.entity.SmtFunction;
import com.insigma.odin.framework.privilege.entity.SmtRole;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.util.AbstractPrivilegeBase;
import com.insigma.odin.framework.privilege.util.BeanUtil;
import com.insigma.odin.framework.privilege.util.PrivilegeUtil;
import com.insigma.odin.framework.privilege.util.ResourcesPermissionConst;
import com.insigma.odin.framework.privilege.vo.FunctionVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.EventDataCustomized;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.tree.TreeNode;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.epsoft.util.Node;
import com.insigma.siis.local.epsoft.util.NodeUtil;

public class roletreePageModel extends PageModel {
	private List aclList = null;
	@Override
	public int doInit() throws RadowException {
		getPageElement("id").setValue(this.getRadow_parent_data());
		this.setNextEventName("querybyid");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@PageEvent("orgTreeJsonData")
	@EventDataCustomized("fid,id")
	public int getOrgTreeJsonData() throws RadowException, PrivilegeException {
		String node = this.getParameter("node");
		String roleid =this.getParameter("roleid");
		//List functionlist = PrivilegeManager.getInstance().getIResourceControl().getUserFunctions(SysUtil.getCacheCurrentUser().getUserVO(), SysUtil.getCacheCurrentUser().getSceneVO().getSceneid(), false);//SysUtil.getCacheCurrentUser().getFunctionList();//PrivilegeManager.getInstance().getIResourceControl().findByParentId(node);
		List functionlist = new ArrayList();
		functionlist = getUserFunctions(SysUtil.getCacheCurrentUser().getUserVO(), SysUtil.getCacheCurrentUser().getSceneVO().getSceneid(), false);
		for(int i=0;i<functionlist.size();i++){
			FunctionVO function =(FunctionVO) functionlist.get(i);
			String parent = function.getParent();
			String name = function.getTitle();
			if(function.getType().equals("2")){
				functionlist.remove(i);
				i--;
			}else{
				//系统管理菜单不可见，不可授权
				if(parent!=null){
//					if(parent.equals("S010000")){
//						functionlist.remove(i);
//						i--;
//					}
				}
				if(name!=null){
//					if(name.equals("系统管理")){
//						functionlist.remove(i);
//						i--;
//					}
				}
			}
		}
		/*List<FunctionVO> list = new ArrayList<FunctionVO>(); 
		for(int i=0;i<functionlist.size();i++) {
			FunctionVO function = (FunctionVO) functionlist.get(i);
			if(function.getParent()!=null && function.getParent().equals(node)) {
				list.add(function);
			}
		}
		Collections.sort(list,new Comparator(){
			public int compare(Object arg0, Object arg1) {
				if(arg0 instanceof FunctionVO && arg1 instanceof FunctionVO) {
					FunctionVO func1 =  (FunctionVO) arg0;
					FunctionVO func2 =  (FunctionVO) arg1;
					return func1.getOrderno()>func2.getOrderno()?1:-1;
				}
				return 0;
		}});
		StringBuffer jsonStr = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			for (Object resObj : list) {
				FunctionVO res = (FunctionVO) resObj;
				if(i==0 && last==1) {
					jsonStr.append("[{\"text\" :\"" + res.getTitle()
							+ "\" ,\"id\" :\"" + res.getFunctionid()
							+ "\" ,\"cls\" :\"folder\",");
					jsonStr.append("\"checked\":"+query(res.getFunctionid(),roleid));
					jsonStr.append("}]");
				}else if (i == 0) {
					jsonStr.append("[{\"text\" :\"" + res.getTitle()
							+ "\" ,\"id\" :\"" + res.getFunctionid()
							+ "\" ,\"cls\" :\"folder\",");
					jsonStr.append("\"checked\":"+query(res.getFunctionid(),roleid));
					jsonStr.append("}");
				} else if (i == (last - 1)) {
					jsonStr.append(",{\"text\" :\"" + res.getTitle()
							+ "\" ,\"id\" :\"" + res.getFunctionid()
							+ "\" ,\"cls\" :\"folder\",");
					jsonStr.append("\"checked\":"+query(res.getFunctionid(),roleid));
					jsonStr.append("}]");
				} else {
					jsonStr.append(",{\"text\" :\"" + res.getTitle()
							+ "\" ,\"id\" :\"" + res.getFunctionid()
							+ "\" ,\"cls\" :\"folder\",");
					jsonStr.append("\"checked\":"+query(res.getFunctionid(),roleid));
					jsonStr.append("}");
				}
				i++;
			}
		} else {
			jsonStr.append("{}");
		}
		//System.out.println(jsonStr.toString());
		this.setSelfDefResData(jsonStr.toString());
		*/
		List<TreeNode> root = PrivilegeManager.getInstance().getIResourceControl().getUserFunctionsTree(functionlist,roleid, node, true);
		this.setSelfDefResData(JSONArray.fromObject(root));
		return EventRtnType.XML_SUCCESS;
	}

	
	public boolean query(String functionid,String roleid) throws RadowException {

		if(aclList==null) {
			String acl = "select s.resourceid from SMT_ACL s where s.roleid='"+roleid+"'";
			HBSession sess = HBUtil.getHBSession();
			aclList = sess.createSQLQuery(acl).list();
		}
		for(int i=0;i<aclList.size();i++) {
			if(functionid.equals(aclList.get(i))){
				return true;
			}
		}
		return false;
	}
	
	@PageEvent("querybyid")
	@EventDataCustomized("fid,id")
	@NoRequiredValidate
	public int query() throws Exception {
		String roleid = this.getPageElement("id").getValue();
		String acl = "select s.resourceid from SMT_ACL s where s.roleid='"+roleid+"'";
		HBSession sess = HBUtil.getHBSession();
		List aclList = sess.createSQLQuery(acl).list();
		HashMap<String,String> allfunction = ResourcesPermissionConst.getSysPermission();
		List<HashMap<String,Object>> functionlist = new ArrayList<HashMap<String,Object>>();
		for(String key : allfunction.keySet()) {
			HashMap<String,Object> map = new HashMap<String,Object>();
			String name =  allfunction.get(key);
			map.put("functionid", key);
			map.put("title", name);
			map.put("logchecked", false);
			for(int j=0;j<aclList.size();j++) {
				String aclid =  (String) aclList.get(j);
				if(key.equals(aclid)) {
					map.put("logchecked", true);
				}
			}
			functionlist.add(map);
		}
		PageElement grid = createPageElement("resourcegrid","grid",false);
		grid.setValueList(functionlist);
		grid.reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("dogrant")
	public void save(String value) throws RadowException {
		String[] nodes = null;
		HashMap<String,String> nodemap = new HashMap<String,String>();
		if(value !=null) {
			nodes = value.split(",");
			for(int i=0;i<nodes.length;i++) {
				nodemap.put(nodes[i].split(":")[0], nodes[i].split(":")[1]);
			}
		}
		StringBuffer addresourceIds = new StringBuffer();
		StringBuffer removeresourceIds = new StringBuffer();
		for(String node :nodemap.keySet()) {
			if(nodemap.get(node).equals("true")) {
				addresourceIds.append(node+",");
			}else if(nodemap.get(node).equals("false")) {
				removeresourceIds.append(node+",");
			}
		}
		List<HashMap<String,Object>> functionlist = this.getPageElement("resourcegrid").getValueList();
		for(int i=0;i<functionlist.size();i++) {
			if(functionlist.get(i).get("logchecked").equals(true)) {
				addresourceIds.append(functionlist.get(i).get("functionid")+",");
			}else {
				removeresourceIds.append(functionlist.get(i).get("functionid")+",");
			}
		}
		String roleid = this.getPageElement("id").getValue();
		
		addresourceIds.append("S000000");
		String add = addresourceIds.toString();
		String remove = removeresourceIds.toString();
		//System.out.println("-------------"+add);
		//System.out.println("-------------"+remove);
		//System.out.println(remove);
		addResource(roleid,add);
		removeResource(roleid,remove);
		/*try {
			PrivilegeManager.getInstance().getIRoleControl().addResousesToRole(roleid, addresourceIds.toString());
			PrivilegeManager.getInstance().getIRoleControl().removeResourcesFromRole(roleid, removeresourceIds.toString());
		} catch (PrivilegeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		HBSession sess = HBUtil.getHBSession();
		String hql = "From SmtRole S where S.id = '"+roleid+"'";
		SmtRole user =(SmtRole) sess.createQuery(hql).list().get(0);
		List list = new ArrayList();
		try {
			new LogUtil().createLog("621", "SMT_ACL",roleid,user.getName(), "", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setMainMessage("授权成功！");
		this.closeCueWindowByYes("grantWindow");
	}
	public void addResource(String roleid,String addids){
		List<Node> nodeList = new ArrayList<Node>();
		HBSession sess = HBUtil.getHBSession();
		 String hql1 = "From SmtFunction";
		 List list2 = sess.createQuery(hql1).list();
		 for(int i=0;i<list2.size();i++){
			 SmtFunction sf = (SmtFunction)list2.get(i);
			 Node node = new Node(sf.getFunctionid(),sf.getTitle(),sf.getParent());
			 nodeList.add(node); 
		 }
		String[] ids = addids.split(",");
		List<SmtFunction> list = new ArrayList<SmtFunction>();
		for(int i=0;i<ids.length;i++){
			String hql="from SmtFunction t where t.functionid = '"+ids[i]+"'";
			List list1 = sess.createQuery(hql).list();
			if(list1.size()==1){
			SmtFunction s = (SmtFunction)list1.get(0);
			list.add(s);
			}
		}
		String hql="from SmtFunction t where t.functionid = 'GG00000'";
		List list1 = sess.createQuery(hql).list();
		if(list1.size()==1){
			SmtFunction s = (SmtFunction)list1.get(0);
			list.add(s);
		}
		//List<SmtFunction> parent = new ArrayList<SmtFunction>();
		//List<SmtFunction> child = new ArrayList<SmtFunction>();
		for(int i=0;i<list.size();i++){
			for(int j=0;j<list.size();j++){
				if(list.get(j).getFunctionid().equals("S000000")){
					continue;
				}
				if(list.get(i).getFunctionid().equals(list.get(j).getParent())){
					//parent.add(list.get(i));
					list.remove(i);
					i--;
					break;
				}
			}
		}
		 
		//System.out.println(mt.getChildNodes(nodeList, "402881fe538436cc0153843a10eb0002")); 
		for(int i=0;i<list.size();i++){
			if(list.get(i).getFunctionid().equals("S000000")){
				continue;
			}
			NodeUtil mt = new NodeUtil();
			String str = mt.getChildNodes(nodeList,list.get(i).getFunctionid());
			if(str.contains(",")){
			str = str.substring(1+list.get(i).getFunctionid().length()+1,str.length()-1);
			addids+=","+str;
			}else{
				continue;
			}
			
		}
		addids = addids.replaceAll(" ","");
		String[] resourceids = addids.split(",");
		for(int i=0;i<resourceids.length;i++){
			Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
			String hql3 = "from SmtAcl t where t.resourceid='"+resourceids[i]+"' and t.roleid='"+roleid+"'";
			List list3 = sess.createQuery(hql3).list();
			if(list3.size()==0){
				SmtAcl acl = new SmtAcl();
				acl.setResourceid(resourceids[i]);
				if(resourceids[i].equals("")){
					
				}
				acl.setRoleid(roleid);
				sess.save(acl);
				ts.commit();
			}
		}
		//System.out.println(addids);
	}
	public void removeResource(String roleid,String removeids){
		List<Node> nodeList = new ArrayList<Node>();
		HBSession sess = HBUtil.getHBSession();
		 String hql1 = "From SmtFunction";
		 List list2 = sess.createQuery(hql1).list();
		 for(int i=0;i<list2.size();i++){
			 SmtFunction sf = (SmtFunction)list2.get(i);
			 Node node = new Node(sf.getFunctionid(),sf.getTitle(),sf.getParent());
			 nodeList.add(node); 
		 }
		 String[] ids = removeids.split(",");
			List<SmtFunction> list = new ArrayList<SmtFunction>();
			for(int i=0;i<ids.length;i++){
				String hql="from SmtFunction t where t.functionid = '"+ids[i]+"'";
				List list1 = sess.createQuery(hql).list();
				if(list1.size()==1){
				SmtFunction s = (SmtFunction)list1.get(0);
				list.add(s);
				}
			}
			for(int i=0;i<list.size();i++){
				if(list.get(i).getFunctionid().equals("S000000")){
					continue;
				}
				for(int j=0;j<list.size();j++){
					if(list.get(i).getParent().equals(list.get(j).getFunctionid())){
						//parent.add(list.get(i));
						list.remove(i);
						i--;
						break;
					}
				}
			}
			 
			for(int i=0;i<list.size();i++){
				NodeUtil mt = new NodeUtil();
				String str = mt.getChildNodes(nodeList,list.get(i).getFunctionid());
				if(str.contains(",")){
				str = str.substring(1+list.get(i).getFunctionid().length()+1,str.length()-1);
				removeids+=str+",";
				}else{
					continue;
				}
				
			}
			removeids = removeids.replaceAll(" ","");
			String[] removeresourceids = removeids.split(",");
			for(int i=0;i<removeresourceids.length;i++){
				Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
				String hql3 = "from SmtAcl t where t.resourceid='"+removeresourceids[i]+"' and t.roleid='"+roleid+"'";
				List list3 = sess.createQuery(hql3).list();
				if(list3.size()==1){
					SmtAcl acl = (SmtAcl)list3.get(0);
					sess.delete(acl);
					ts.commit();
				}
			}
			//System.out.println(removeids);
			
	}
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("ab3");
		list.add("11");
		list.remove("11");
	}
	
	/**
	 * 	查询用户可以操作的授权
	 * @param paramUserVO
	 * @param paramString
	 * @param paramBoolean
	 * @return
	 * @throws PrivilegeException
	 */
	public List<Object> getUserFunctions(UserVO paramUserVO, String paramString, boolean paramBoolean) throws PrivilegeException{
		
		HBSession sess = HBUtil.getHBSession();
		PrivilegeManager localPrivilegeManager = PrivilegeManager.getInstance();
	    SmtUser localSmtUser = (SmtUser)sess.get(SmtUser.class, paramUserVO.getId());
	    UserVO localUserVO = new UserVO();
	    BeanUtil.propertyCopy(localSmtUser, localUserVO);
	    String sql = "";
	    HashMap<String, Object> hashmap = new HashMap();
	    
	    if (localUserVO.getId().equals(GlobalNames.sysConfig.get("SUPER_ID")))
	    {
	      localUserVO.setLoginname(paramUserVO.getLoginname());
	      localUserVO.setPasswd(paramUserVO.getPasswd());
	    }
	    Object localObject = new ArrayList();
	    String str = "select t from SmtAct t,SmtAcl l where (t.objectid='" + localUserVO.getId() + "' or t.objectid in (select ug.groupid from SmtUsergroupref ug where ug.userid='" + localUserVO.getId() + "')) and  t.roleid=l.roleid and l.resourceid='RESOURCE_ALL'";
	    int i = 0;
	    List list_smtact = sess.createQuery(str).list();
	    if (list_smtact.size() > 0)
	      i = 1;
	    if ((!(localPrivilegeManager.getIPermission().isSuperManager(localUserVO))) && (!(localPrivilegeManager.getIPermission().isSysPermission(localUserVO, "RESOURCE_ALL"))) && (i == 0))
	    {
	      List localList = localPrivilegeManager.getIGroupControl().getGroupsByUserId(paramUserVO.getId());
	      if ((localList == null) || (localList.size() == 0))
	    	  sql = "select distinct f from SmtFunction f,SmtResource r,SmtAct t where f.resourceid = r.id and r.status='1' and t.objectid=:userID order by f.parent,f.orderno";
	      else
	    	  sql = "select distinct f from SmtFunction f,SmtResource r,SmtAct t,SmtUsergroupref ug where f.resourceid = r.id and r.status='1'  and (t.objectid=:userID or (ug.userid=:userID and t.objectid = ug.groupid) ) order by f.parent,f.orderno";
	      hashmap.put("userID", localUserVO.getId());
	      localObject = sess.createQuery(sql).setParameter("userID", localUserVO.getId()).list();
	    }
	    else
	    {
	    	sql = "select f from SmtFunction f,SmtResource r where f.resourceid = r.id and r.status='1' order by f.parent,f.orderno";
	      localObject = sess.createQuery(sql).list();
	    }
	    return ((List<Object>)PrivilegeUtil.getVOList((List)localObject, FunctionVO.class));
	}
}
