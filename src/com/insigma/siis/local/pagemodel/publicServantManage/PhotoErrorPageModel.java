package com.insigma.siis.local.pagemodel.publicServantManage;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;

public class PhotoErrorPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException,AppException{
		this.setNextEventName("errorGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("errorGrid.dogridquery")
	public int errorQuery(int start, int limit) throws RadowException {
		String userId = SysManagerUtils.getUserloginName();
		String sql = "";
		if("system".equals(userId)){
			sql = "select per001,per002,per003,per004,per005 from "
					+ "photoup_error order by per004";
		}else{
			sql = "select per001,per002,per003,per004,per005 from "
					+ "photoup_error where per005='"+userId+"' order by per004";
		}
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}

}
