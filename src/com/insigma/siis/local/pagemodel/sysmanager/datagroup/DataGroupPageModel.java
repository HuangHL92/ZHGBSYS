package com.insigma.siis.local.pagemodel.sysmanager.datagroup;

import java.util.List;
import java.util.ListIterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;


import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtDatagroup;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.tree.TreeNode;

public class DataGroupPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		return 0;
	}

	@PageEvent("querybyid")
	@NoRequiredValidate
	public int query(String id) throws Exception {
		SmtDatagroup g = PrivilegeManager.getInstance().getIDataGroupControl().getDataGroupById(id);
		this.autoFillPage(g, false);
		this.getPageElement("dgtype").setValue(g.getType());
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("btnDelete.onclick")
	@NoRequiredValidate
	public int deleteResource() throws RadowException {
		String id = this.getPageElement("id").getValue();
		if(!"".equals(id) && !"ROOT".equals(id)){
			PrivilegeManager.getInstance().getIDataGroupControl().isPermitDeleteDataGroup(id);
		}else{
			throw new RadowException("请先单击选择要删除的数据组！");
		}
		this.setMessageType(EventMessageType.CONFIRM);
		this.setMainMessage("您确定要删除”"+this.getPageElement("name").getValue()+"“该组吗？");
		this.addNextEvent(NextEventValue.YES, "deleteGroup", id);
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("deleteGroup")
	@Transaction
	@NoRequiredValidate
	public int delete(String id) throws RadowException {
		PrivilegeManager.getInstance().getIDataGroupControl().deleteGroupById(id);
		this.setMainMessage("删除成功！");
		this.getExecuteSG().addExecuteCode("deleteGroup('"+id+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("btnSave.onclick")
	@Transaction
	public int saveResource() throws RadowException {
		SmtDatagroup g = new SmtDatagroup();		
		this.copyElementsValueToObj(g, this);
		boolean isSave = true;
		String msg = "保存成功！";
		if(!"".equals(g.getId())){
			isSave = false;
			msg = "更新成功！";
		}else{
			String parent=this.getPageElement("parent").getValue();
			if(parent.equals("")){
				throw new RadowException("请选择一个上级数据区域或根节点进行新建！");
			}
			if(parent.equals("ROOT")){//由于根节点是虚设的  所以如果是根节点  则设置parent为空
				g.setParent("");
			}
			g.setId(null);
		}
		g.setType(this.getPageElement("dgtype").getValue());
		PrivilegeManager.getInstance().getIDataGroupControl().saveOrUpdateDataGroup(g);
		this.setMainMessage(msg);
		this.getExecuteSG().addExecuteCode("changeTreeNode("+isSave+",'"+g.getId()+"','"+g.getName()+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@PageEvent("treeJsonData")
	public int getTreeJsonData() throws PrivilegeException {
		String node = this.getParameter("node");
		boolean isLoadAll = false;
		List<TreeNode> roots = PrivilegeManager.getInstance().getIDataGroupControl().getDataGroupTree(node, isLoadAll);
		JsonConfig jconfig = new JsonConfig();
		jconfig.setIgnoreDefaultExcludes(false);
		jconfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		if(!isLoadAll){
			jconfig.setExcludes(new String[] {"children" });
		}
		JSONArray ja = JSONArray.fromObject(roots,jconfig);
		if(isLoadAll){
			setDefaultValue(ja);
		}
		this.setSelfDefResData(ja.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
	private void setDefaultValue(JSONArray ja){
		for(ListIterator<JSONObject> lit = ja.listIterator();lit.hasNext();){
			JSONObject o = lit.next();
			//if(o.getBoolean("leaf")){
			o.put("href", "javascript:queryById('querybyid','"+o.get("id")+"')");
			//o.put("checked", false);
			if(o.getBoolean("leaf")){
				//
			}else{
				setDefaultValue(o.getJSONArray("children"));
			}
		}
	}
}
