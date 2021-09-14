package com.insigma.siis.local.pagemodel.weboffice;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class ExcelOfficePageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	public int initX(){
		this.getExecuteSG().addExecuteCode("haveCJ();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("updatename")
	public int updatename(String text){
		HBSession sess = HBUtil.getHBSession();
		String[] split = text.split(",");
		String id=split[1];
		String filename=split[3];
		String sql="update weboffice set filename='"+filename+"' where id='"+id+"'";
		sess.createSQLQuery(sql).executeUpdate();
		this.setMainMessage("方案名已修改成功");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	@PageEvent("expWeboffice")
	public int expWordws(String type)throws AppException, RadowException{
		String Path="pages/weboffice/WebOffice_Setup.exe";
		Path=request.getSession().getServletContext().getRealPath(Path);
	    this.getPageElement("downfile").setValue(Path.replace("\\", "/"));
		this.getExecuteSG().addExecuteCode("window.reloadTree()");
		return EventRtnType.NORMAL_SUCCESS;	
	}

}
