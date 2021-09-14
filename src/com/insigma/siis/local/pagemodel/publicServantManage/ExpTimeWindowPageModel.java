package com.insigma.siis.local.pagemodel.publicServantManage;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class ExpTimeWindowPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		return 0;
	}
	
	@PageEvent("sureBtn.onclick")
	public int sure() throws AppException, RadowException {
		String time = this.getPageElement("time").getValue();
		this.getExecuteSG().addExecuteCode("parent.radow.doEvent('export','"+time+"')");
		this.closeCueWindow("expTimeWin");
		//closeCueWindowEX();
		return EventRtnType.NORMAL_SUCCESS;
	}
	public String closeCueWindowEX(){
		return "window.parent.Ext.WindowMgr.getActive().hide();";
	}
}
