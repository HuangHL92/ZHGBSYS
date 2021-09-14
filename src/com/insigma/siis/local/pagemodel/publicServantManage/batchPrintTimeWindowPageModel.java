package com.insigma.siis.local.pagemodel.publicServantManage;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class batchPrintTimeWindowPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		return 0;
	}

	
	@PageEvent("sureBtn.onclick")
	public int sure() throws AppException, RadowException {
		String time = this.getPageElement("time").getValue();
		Object flagNrm = this.getPageElement("choose").getValue().equals("1")?true:false;
		CommonQueryBS.systemOut(flagNrm.toString());
		this.getExecuteSG().addExecuteCode("parent.radow.doEvent('batchPrint','"+flagNrm+"@"+time+"')");
		this.closeCueWindow("batchPrintTimeWin");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
