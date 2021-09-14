package com.insigma.siis.local.pagemodel.publicServantManage;

import java.util.Hashtable;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class ImportPhotosPageModel extends PageModel {
	
	public ImportPhotosPageModel(){
		
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException,AppException{
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Override
	public int doInit() throws RadowException {   
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
