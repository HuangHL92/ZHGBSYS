package com.insigma.siis.local.pagemodel.templateconf;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class TemplateAnalysisPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("selectduty")
	public int selectduty(String pid){
		String sqljh="select quantity from DUTY_NUM where unit = '"+pid+"'";
		
		return EventRtnType.NORMAL_SUCCESS;
	}

}
