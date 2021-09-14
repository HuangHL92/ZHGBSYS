package com.insigma.siis.local.pagemodel.publicServantManage;

import java.util.Hashtable;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class ImportLrmsPageModel extends PageModel {

	public Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	
	public ImportLrmsPageModel(){
		
	}
	
	//“≥√Ê≥ı ºªØ
	@Override
	public int doInit() throws RadowException {
//		this.getExecuteSG().addExecuteCode("odin.ext.getCmp('persongrid').view.refresh();");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
