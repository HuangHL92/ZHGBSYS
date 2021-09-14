package com.insigma.siis.local.pagemodel.jzsp.jgld;


import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;

import net.sf.json.JSONArray;

public class ApprJGLDPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("gridcq.dogridquery");
		return 0;
	}
	@PageEvent("initX")
	public int initX() throws RadowException, AppException{
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@PageEvent("gridcq.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		try {
			String sp0100 = this.getPageElement("sp0100").getValue();
			String sql = "select * from SP_BUS_LOG where spb00='"+sp0100+"' order by spbl00 asc";
		
			this.pageQuery(sql, "SQL", start, limit);
        	return EventRtnType.SPE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("≤È—Ø ß∞‹£°");
			return EventRtnType.SPE_SUCCESS;
		}
	}
	
	

}
