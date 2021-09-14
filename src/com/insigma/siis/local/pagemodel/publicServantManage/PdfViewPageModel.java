package com.insigma.siis.local.pagemodel.publicServantManage;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class PdfViewPageModel extends PageModel {
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException,AppException {
		this.getExecuteSG().addExecuteCode("createPdf()");
//		String filePath = this.getRadow_parent_data();
//		if(StringUtil.isEmpty(filePath)||!filePath.toUpperCase().endsWith("PDF")){
//			this.getExecuteSG().addExecuteCode("setpdf()");
//		}else{
//			this.getExecuteSG().addExecuteCode("setpdf1('"+filePath+"')");
//		}
		return EventRtnType.NORMAL_SUCCESS;
	}

}
