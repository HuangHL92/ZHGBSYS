package com.insigma.siis.local.pagemodel.fxyp.gbtp;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class MNTPGBTPPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}

	
	@PageEvent("initX")
	public int initX() throws RadowException, AppException{
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("ShowData")
	public int ShowData(String type) throws RadowException{
		try {
			String t = this.request.getParameter("type");
			String querysql = "select * from zdgw_way  where dwtype='"+t+"' order by sortid ";
			//System.out.println(sql);
			this.pageQuery(querysql, "SQL", 0, 2000);
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("≤È—Ø ß∞‹£°");
		}
		
		return EventRtnType.SPE_SUCCESS;
	}
}
