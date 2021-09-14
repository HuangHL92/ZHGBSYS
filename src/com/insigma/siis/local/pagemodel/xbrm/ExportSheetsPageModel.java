package com.insigma.siis.local.pagemodel.xbrm;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class ExportSheetsPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		
		
		this.setNextEventName("gridcq.dogridquery");
		this.setNextEventName("initx");
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("gridcq.dogridquery")
	public int dogridquery(int start,int limit) throws Exception{
		try {
			String rbId = this.getPageElement("rbId").getValue();
			String sql = "select js0100,dc001,js01.a0000,a0101,a0163,'13' a0247 from a01,js01 where a01.a0000=js01.a0000 and rb_id='"+rbId+"'";
			this.pageQuery(sql, "SQL", start, limit);
        	return EventRtnType.SPE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("≤È—Ø ß∞‹£°");
			return EventRtnType.SPE_SUCCESS;
		}
			
	}
	
	@PageEvent("initx")
	public int initx() throws Exception{
		return 0 ;
	}
}
