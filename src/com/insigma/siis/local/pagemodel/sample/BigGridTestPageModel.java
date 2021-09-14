package com.insigma.siis.local.pagemodel.sample;


import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.event.EventRtnType;


public class BigGridTestPageModel extends PageModel {
	
	@Override
	public int doInit() {		
		return EventRtnType.NORMAL_SUCCESS;
	}

}
