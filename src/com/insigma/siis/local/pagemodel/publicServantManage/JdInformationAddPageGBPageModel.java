package com.insigma.siis.local.pagemodel.publicServantManage;


import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.epsoft.util.LogUtil;

public class JdInformationAddPageGBPageModel extends PageModel {
	private LogUtil applog = new LogUtil();

	@Override
	@NoRequiredValidate
	public int doInit() throws RadowException {
		this.getExecuteSG().addExecuteCode("reShowMsg()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException {
		String a0000 = this.getPageElement("a0000").getValue();//String a0000 = this.getRadow_parent_data();
		if (a0000 == null || "".equals(a0000)) {//
			//this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		

		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	

}
