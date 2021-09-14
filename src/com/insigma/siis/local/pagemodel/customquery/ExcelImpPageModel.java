package com.insigma.siis.local.pagemodel.customquery;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class ExcelImpPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
