package com.insigma.siis.local.pagemodel.sample;

import org.apache.log4j.Logger;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class SimpleWindowPageModel extends PageModel {

	public static Logger log = Logger.getLogger(SimpleWindowPageModel.class);
	
	@Override
	public int doInit() throws RadowException {
		this.isShowMsg = false;
		this.getPageElement("aab001").setValue(this.getRadow_parent_data());
		throw new RadowException("“Ï≥£!");
		//return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("btn1.onclick")
	public int btn1Onclick(){
		this.closeCueWindowByYes("win1");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
