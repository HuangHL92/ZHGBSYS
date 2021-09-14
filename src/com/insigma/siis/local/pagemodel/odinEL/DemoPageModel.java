package com.insigma.siis.local.pagemodel.odinEL;

import java.util.HashMap;
import java.util.Map;

import org.apache.solr.common.util.Hash;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class DemoPageModel extends PageModel {
	
	@Override
	public int doInit() throws RadowException {
		
		this.setNextEventName("init1");
		return EventRtnType.NORMAL_SUCCESS;
		//请求结束
	}
	
	
	//初始化单位名和核定职数
	@PageEvent("init1")
	@NoRequiredValidate
	public  int  init1() throws RadowException{
		Map<String, String> m = new HashMap<String, String>();
		m.put("dsadas", "aqweretry");
		
		Combo co = new Combo(this);
		co.setId("select1");
		co.setValueListForSelect(m);
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
}
