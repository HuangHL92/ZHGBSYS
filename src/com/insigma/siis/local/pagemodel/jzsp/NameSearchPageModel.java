package com.insigma.siis.local.pagemodel.jzsp;


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

public class NameSearchPageModel extends PageModel {

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
			String sp0102 = this.getPageElement("sp0102").getValue();
			String sql = "select * from a01 where status!='4' and a0101='"+sp0102.replaceAll("'", "''")+"'";
		
			this.pageQuery(sql, "SQL", start, limit);
        	return EventRtnType.SPE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.SPE_SUCCESS;
		}
	}
	//关闭
	@PageEvent("clearSelect")
	public int clearSelect() throws RadowException, AppException{
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('findById321').close();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//确认保存
	@PageEvent("saveSelect")
	public int saveSelect() throws RadowException, AppException{
		
		
		this.getExecuteSG().addExecuteCode("window.realParent.$('#tplb').val('');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	

}
