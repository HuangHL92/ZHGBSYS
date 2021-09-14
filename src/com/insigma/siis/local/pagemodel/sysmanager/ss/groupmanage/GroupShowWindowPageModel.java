package com.insigma.siis.local.pagemodel.sysmanager.ss.groupmanage;

import java.util.HashMap;

import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.util.PageQueryData;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;

public class GroupShowWindowPageModel extends PageModel {
	
	@PageEvent("groupgrid.dogridquery")
	public int doQuery(int start,int limit) throws RadowException{
		
		String name = this.getRadow_parent_data();
		HashMap<String,String> params = null;
		if(!name.equals("searchAll")){
			params = new HashMap<String,String>();
			params.put("name", name);
		}
		PageQueryData pq = null;
		try { 
			pq = PrivilegeManager.getInstance().getIGroupControl().pageQueryContantParentNameByUser(PrivilegeManager.getInstance().getCueLoginUser(), start, limit, params);
		} catch (PrivilegeException e) {
			this.setMainMessage("queryByUser失败"+e.getMessage());
		}
		this.setSelfDefResData(pq);
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("groupgrid.rowdbclick")
	@GridDataRange
	public int choose() throws RadowException{
		int rowIndex = this.getPageElement("groupgrid").getCueRowIndex();
		this.autoFillPageByGridElement(this.getPageElement("groupgrid"), rowIndex, true);
		String status = this.getPageElement("groupgrid").getValue("status",rowIndex).toString();
		String id = this.getPageElement("groupgrid").getValue("id",rowIndex).toString();
		if(status.equals("0"))
			this.createPageElement("status", ElementType.TEXT, true).setValue("无效");
		if(status.equals("1"))
			this.createPageElement("status", ElementType.TEXT, true).setValue("有效");
		try {
			GroupVO group = PrivilegeManager.getInstance().getIGroupControl().findById(id);
			if(group.getOwnerId() != null){
				UserVO user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(group.getOwnerId());
				this.createPageElement("ownerId", ElementType.TEXT, true).setValue(user.getLoginname());
			}
			if(group.getParentid() != null){
				GroupVO pgroup = PrivilegeManager.getInstance().getIGroupControl().findById(group.getParentid());
				this.createPageElement("parentid", ElementType.TEXT, true).setValue(pgroup.getName());
			}
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		this.closeCueWindow("groupWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("dogridgrant")
	public int groupgridChange(String groupid) throws RadowException{
		try {
			GroupVO group = PrivilegeManager.getInstance().getIGroupControl().findById(groupid);
			if(group.getOwnerId() != null){
				UserVO user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(group.getOwnerId());
				group.setOwnerId(user.getLoginname());
			}
			if(group.getParentid() != null){
				GroupVO pgroup = PrivilegeManager.getInstance().getIGroupControl().findById(group.getParentid());
				group.setParentid(pgroup.getName());
			}
			this.autoFillPage(group, true);
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		this.closeCueWindow("groupWin");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@Override
	public int doInit() throws RadowException{
		this.setNextEventName("groupgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
