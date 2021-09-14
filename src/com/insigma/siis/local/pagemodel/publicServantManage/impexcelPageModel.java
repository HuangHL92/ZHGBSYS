package com.insigma.siis.local.pagemodel.publicServantManage;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.search.ListOutPutPageModel;


public class impexcelPageModel extends PageModel{
	
	public static String temnames = "";
	@Override
	public int doInit() throws RadowException {
		
		return EventRtnType.NORMAL_SUCCESS;
	}
}
