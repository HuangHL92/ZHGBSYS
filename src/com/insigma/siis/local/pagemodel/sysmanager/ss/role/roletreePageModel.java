package com.insigma.siis.local.pagemodel.sysmanager.ss.role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.util.ResourcesPermissionConst;
import com.insigma.odin.framework.privilege.vo.FunctionVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.EventDataCustomized;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.tree.TreeNode;
import com.insigma.odin.framework.util.SysUtil;

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
		List functionlist = PrivilegeManager.getInstance().getIResourceControl().getUserFunctions(SysUtil.getCacheCurrentUser().getUserVO(), SysUtil.getCacheCurrentUser().getSceneVO().getSceneid(), false);//SysUtil.getCacheCurrentUser().getFunctionList();//PrivilegeManager.getInstance().getIResourceControl().findByParentId(node);
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
	@Transaction
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
		
		try {
			addresourceIds.append("S000000");
			PrivilegeManager.getInstance().getIRoleControl().addResousesToRole(roleid, addresourceIds.toString());
			PrivilegeManager.getInstance().getIRoleControl().removeResourcesFromRole(roleid, removeresourceIds.toString());
		} catch (PrivilegeException e) {
			e.printStackTrace();
			throw new RadowException(e.getMessage(),e);
		}
		
		this.setMainMessage("ÊÚÈ¨³É¹¦£¡");
		this.closeCueWindowByYes("grantWindow");
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("ab3");
		list.add("11");
		list.remove("11");
	}
}
