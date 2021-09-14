package com.insigma.siis.local.pagemodel.person;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.sys.manager.sysmanager.comm.entity.Sysopright;
import com.insigma.siis.local.business.entity.A01;

public class personMainPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();//this.getRadow_parent_data();
		this.getPageElement("a0000").setValue(a0000);
		return EventRtnType.NORMAL_SUCCESS;
	}
}
