package com.insigma.siis.local.pagemodel.sysmanager.role;

import java.util.ArrayList;
import java.util.List;

import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class ShowGrantWindowPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		this.setNextEventName("rolegrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("rolegrid.dogridquery")
	public int roleQuery(int start,int limit) throws RadowException{
		String message = this.getRadow_parent_data();
		String[] messages = message.split(",");
		String objectid = messages[0];
		List list = new ArrayList();
		try {
			list = PrivilegeManager.getInstance().getIRoleControl().findRoleByObject(objectid);
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		this.setSelfDefResData(this.getPageQueryData(list, start, limit));
		return EventRtnType.SPE_SUCCESS;
	}
}
