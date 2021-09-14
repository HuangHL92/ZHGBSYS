package com.insigma.siis.local.pagemodel.sysmanager.ss.role;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.IRoleControl;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.util.PageQueryData;
import com.insigma.odin.framework.privilege.vo.RoleVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.EventDataCustomized;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.DateUtil;
import com.insigma.odin.framework.util.SysUtil;

public class rolePageModel extends PageModel {
	private IRoleControl rc = PrivilegeManager.getInstance().getIRoleControl();

	@Override
	public int doInit() {

		this.isShowMsg = false;
		return 1;
	}

	@PageEvent("btn_query.onclick")
	public int doQuery() throws RadowException {
		this.setNextEventName("grid6.dogridquery");
		this.isShowMsg = false;
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("grid6.afteredit")
	@GridDataRange(GridData.cuerow)
	@Transaction
	@AutoNoMask
	public int dogrid6AfterEdit() throws RadowException {
		this.isShowMsg = false;
		Grid grid6 = (Grid) this.getPageElement("grid6");
		RoleVO role = null;
		try {
			role = rc.getById((String) grid6.getValue("id"));
		} catch (PrivilegeException e) {
			e.printStackTrace();
			this.setMainMessage(e.getMessage());
			return EventRtnType.NORMAL_SUCCESS;
		}
		grid6.setValueToObj(role);

		try {
			rc.updateRole(role);
		} catch (PrivilegeException e) {
			this.isShowMsg = true;
			this.setMainMessage(e.getMessage());
			grid6.reload();
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("dogriddelete")
	public int dogriddelete(String id) throws Exception {
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES); // 当点击消息框的是确定时触发的下次事件
		ne.setNextEventName("suretodelete");
		ne.setNextEventParameter(id);
		this.addNextEvent(ne);
		NextEvent nec = new NextEvent();
		nec.setNextEventValue(NextEventValue.CANNEL);// 当点击消息框的是取消时触发的下次事件
		this.addNextEvent(nec);
		this.setMessageType(EventMessageType.CONFIRM); // 消息框类型，即confirm类型窗口
		this.setMainMessage("您确实要删除该记录？"); // 窗口提示信息
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("suretodelete")
	@Transaction
	public int delete(String id) throws RadowException {
		try {
			rc.deleteRole(id);
		} catch (PrivilegeException e) {
			e.printStackTrace();
			this.setMainMessage(e.getMessage());
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.isShowMsg = true;
		this.setMainMessage("成功删除该角色！");
		this.getPageElement("grid6").reload();
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("grid6.dogridquery")
	@EventDataCustomized("roleQName,roleOwner,roleQDesc")
	public int dogrid6Query(int start, int limit) throws RadowException{
		HashMap<String, String> params = new HashMap<String, String>();
		String name = getPageElement("roleQName") == null ? null
				: getPageElement("roleQName").getValue();
		String owner = getPageElement("roleOwner") == null ? null
				: getPageElement("roleOwner").getValue();
		String roleQDesc = getPageElement("roleQDesc") == null ? null
				: getPageElement("roleQDesc").getValue();

		if (name != null && !name.equals("")) {
			params.put("name", name);
		}
		if (owner != null && !owner.equals("")) {
			params.put("owner", owner);
		}
		if (roleQDesc != null && !roleQDesc.equals("")) {
			params.put("desc", roleQDesc);
		}
		PageQueryData pd = null;
		try {
			pd = rc.pageQueryByUserVO(SysUtil.getCacheCurrentUser().getUserVO(), start, limit, params);
		} catch (PrivilegeException e) {
			e.printStackTrace();
			this.setSelfDefResData(pd);
		}
		this.setSelfDefResData(pd);
		this.isShowMsg = false;
		return EventRtnType.SPE_SUCCESS;
	}

	/*
	 * @PageEvent("grid6.rowdbclick") public int dbclick() throws RadowException {
	 * this.openWindow("roleWindow", "pages.sysmanager.role.addRole");
	 * this.setRadow_parent_data(((Grid)getPageElement("grid6")).getJsonValues());
	 * this.isShowMsg = false; return EventRtnType.NORMAL_SUCCESS; }
	 */

	@PageEvent("addRole.onclick")
	public int addRole() throws RadowException {
		
		this.openWindow("roleWindow", "pages.sysmanager.ss.role.addRole");
		this.setRadow_parent_data("");
		return EventRtnType.NORMAL_SUCCESS;
	}

//	@PageEvent("delRole.onclick")
//	public void delRole() throws RadowException {
//		NextEvent ne = new NextEvent();
//		ne.setNextEventValue(NextEventValue.YES); // 当点击消息框的是确定时触发的下次事件
//		ne.setNextEventName("suretodeleteall");
//		this.addNextEvent(ne);
//		NextEvent nec = new NextEvent();
//		nec.setNextEventValue(NextEventValue.CANNEL);// 当点击消息框的是取消时触发的下次事件
//		this.addNextEvent(nec);
//		this.setMessageType(EventMessageType.CONFIRM); // 消息框类型，即confirm类型窗口
//		this.setMainMessage("您确实要删除选中的记录？"); // 窗口提示信息
//	}

	@PageEvent("suretodeleteall")
	@Transaction
	public void delAll() throws RadowException {
		List<HashMap<String, Object>> valuelist = getPageElement("grid6")
				.getValueList();
		boolean sign = false;

		for (int i = 0; i < valuelist.size(); i++) {
			if (valuelist.get(i).get("checked").equals(true)) {
				sign = true;
				try {
					PrivilegeManager.getInstance().getIRoleControl()
							.deleteRole((String) (valuelist.get(i).get("id")));
				} catch (PrivilegeException e) {
					e.printStackTrace();
					throw new RadowException(e.getMessage(), e);
				}
			}
		}
		if (!sign) {
			throw new RadowException("请选择要删除的角色！");
		}
		getPageElement("grid6").reload();
	}

	@PageEvent("clean.onclick")
	@NoRequiredValidate
	public int clean() throws RadowException {
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("dogridgrant")
	public void grant(String id) throws RadowException {
		this.setRadow_parent_data(id);
		this.openWindow("grantWindow", "pages.sysmanager.ss.role.roletree&roleid="
				+ id + "");
	}
}
