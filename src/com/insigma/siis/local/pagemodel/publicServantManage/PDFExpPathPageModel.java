package com.insigma.siis.local.pagemodel.publicServantManage;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;

public class PDFExpPathPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		return 0;
	}

	@PageEvent("close.onclick")
	public int close() throws RadowException{
		String WinId=this.getRadow_parent_data();
		this.closeCueWindow(WinId);
		return 0;
	}
}
