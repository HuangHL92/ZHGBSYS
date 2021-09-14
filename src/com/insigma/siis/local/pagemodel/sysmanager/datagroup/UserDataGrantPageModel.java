package com.insigma.siis.local.pagemodel.sysmanager.datagroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;


import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtDatagroup;
import com.insigma.odin.framework.privilege.vo.DataGroup;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.tree.TreeNode;

public class UserDataGrantPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		String userid = this.getRadow_parent_data();
		List<SmtDatagroup> dataGroupList = PrivilegeManager.getInstance().getIDataGroupControl().getAllDataGroupByUserId(userid,false);
		String cueUserDataGroupIds = "";
		for(Iterator<SmtDatagroup> dit = dataGroupList.iterator();dit.hasNext();){
			String datagroupid = dit.next().getId();
			cueUserDataGroupIds += datagroupid + ",";
		}
		this.getPageElement("dataGroupIds").setValue(cueUserDataGroupIds);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("dataGroupClick")
	@AutoNoMask
	public int dataGroupClick(String id){
		SmtDatagroup dataGroup = PrivilegeManager.getInstance().getIDataGroupControl().getDataGroupById(id);
		String rate = dataGroup.getRate();
		boolean isQuery = false;
		if("3".equals(rate)){ //当为某个特殊级别后，可查询其它表以表格形式显示
			isQuery = true;
		}
		this.getExecuteSG().addExecuteCode("querySpeDataGroup("+isQuery+","+JSONObject.fromObject(dataGroup)+");");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("btnSave.onclick")
	@Transaction
	public int saveResource() throws RadowException {
		String dataGroupIds = this.getPageElement("dataGroupIds").getValue();
		String userid = this.getPageElement("userid").getValue();
		String speGroupId = this.getPageElement("speGroupId").getValue();
		if(dataGroupIds.endsWith(",")){
			dataGroupIds = dataGroupIds.substring(0, dataGroupIds.length()-1);
		}
		String[] ids = dataGroupIds.split(",");
		List<DataGroup> dgs = new ArrayList<DataGroup>();
		if(ids.length==1 && ids[0].equals("")){
			//把用户从所有区域移除  ids.length=1  不做任何 操作  20130711  ljd
		}else{
			for(int i=0;i<ids.length;i++){//20130713  由i=0改成i=1
				DataGroup g = new DataGroup();
				g.setId(ids[i]);
				//g.setOritype(oritype);
				//g.setParentGroupId(parentGroupId);
				dgs.add(g);
			}
		}
		
		List<HashMap<String, Object>> gridData = this.getPageElement("speGrid").getValueList();
		for(Iterator<HashMap<String, Object>> it = gridData.iterator();it.hasNext();){
			HashMap<String, Object> rowData = it.next();
			Object checked = rowData.get("checked");
			boolean isAdd = false;
			if(checked !=null){
				if(checked instanceof Boolean && (Boolean)checked){
					isAdd = true;
				}else if(checked instanceof String && "1".equals(checked)){
					isAdd = true;
				}
			}
			if(isAdd){
				DataGroup g = new DataGroup();
				g.setId((String)rowData.get("id"));
				g.setOritype("1");
				g.setParentGroupId(speGroupId);
				dgs.add(g);
			}
		}
		PrivilegeManager.getInstance().getIDataGroupControl().resetUserDataGroups(userid, dgs);
		this.setMainMessage("保存用户数据权限成功！");
		this.closeCueWindowByYes("win_pup");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@PageEvent("treeJsonData")
	public int getTreeJsonData() throws PrivilegeException, RadowException {
		String userid = this.getParameter("userid");
		String node = this.getParameter("node");
		boolean isLoadAll = true;
		List<TreeNode> roots = PrivilegeManager.getInstance().getIDataGroupControl().getDataGroupTree(node, isLoadAll);
		JsonConfig jconfig = new JsonConfig();
		jconfig.setIgnoreDefaultExcludes(false);
		jconfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		if(!isLoadAll){
			jconfig.setExcludes(new String[] {"children" });
		}
		JSONArray ja = JSONArray.fromObject(roots,jconfig);
		if(isLoadAll){
			setDefaultValue(ja,userid);
		}
		this.setSelfDefResData(ja.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	private void setDefaultValue(JSONArray ja,String userid) throws RadowException{
		List<SmtDatagroup> dataGroupList = PrivilegeManager.getInstance().getIDataGroupControl().getAllDataGroupByUserId(userid,false);
		String cueUserDataGroupIds = "";
		for(Iterator<SmtDatagroup> dit = dataGroupList.iterator();dit.hasNext();){
			String datagroupid = dit.next().getId();
			cueUserDataGroupIds += ","+datagroupid + ",";
		}
		for(ListIterator<JSONObject> lit = ja.listIterator();lit.hasNext();){
			JSONObject o = lit.next();
			//o.put("href", "javascript:queryById('querybyid','"+o.get("id")+"')");
			boolean ischeck = false;
			if(cueUserDataGroupIds.indexOf(","+(String)o.get("id")+",")>=0){
				ischeck = true;
			}
			o.put("checked", ischeck);
			if(o.getBoolean("leaf")){
				//
			}else{
				setDefaultValue(o.getJSONArray("children"),userid);
			}
		}
	}
	
	@PageEvent("speGrid.dogridquery")
	public int doSpeGridQuery(int start,int limit) throws RadowException{
		String speGroupId = this.getPageElement("speGroupId").getValue();
		String userid = this.getPageElement("userid").getValue();
		SmtDatagroup dataGroup = PrivilegeManager.getInstance().getIDataGroupControl().getDataGroupById(speGroupId);
		String oritype = "";
		if(dataGroup.getRate().equals("3")){
			oritype = "1";
		}
		String sql = "select * from ( select u.userid id,u.loginname groupcode,u.username name,decode(t.datagroupid,null,'0','1') checked " +
				"from smt_user u,(select * from smt_userdatagroupref f where f.userid='"+userid+"' and f.parentdatagroup = '"+speGroupId+"' and f.oritype='"+oritype+"') t " +
				"where u.userid = t.datagroupid (+) ) order by groupcode ";
		this.pageQuery(sql,"SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
}
