package com.insigma.siis.local.pagemodel.repandrec.plat;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.OpLog;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class QueryReportPageModel extends PageModel {
	
	@PageEvent("clear.onclick")
	@NoRequiredValidate           
	public int resetonclick()throws RadowException, AppException {
		this.getPageElement("reporttype").setValue("");
		this.getPageElement("createtimesta").setValue("");
		this.getPageElement("createtimeend").setValue("");
//		this.getPageElement("createtimeend").setValue("");
//		this.getPageElement("gsearch").setValue("");
//		this.getPageElement("comboxArea_gsearch").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("query.onclick")
	@NoRequiredValidate           
	@OpLog
	public int query()throws RadowException, AppException {
		this.setNextEventName("MGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("repBtn.onclick")
	@NoRequiredValidate
	public int grantbtnOnclick()throws RadowException{
//		this.openWindow("dataorgwin", "pages.repandrec.plat.DataOrgRep");
		this.getExecuteSG().addExecuteCode("$h.openWin('dataorgwin','pages.repandrec.plat.DataOrgRep','上报窗口',650,625, '2@','"+request.getContextPath()+"');");

		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("repModelBtn.onclick")
	@NoRequiredValidate
	public int searchDept(String name) throws RadowException{
		this.openWindow("modelwin", "pages.repandrec.plat.ModelRep");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("refresh")
	@NoRequiredValidate
	public int refresh(String id)throws RadowException{
//		this.setRadow_parent_data(id);
//		this.openWindow("refreshWin", "pages.dataverify.RefreshOrgExp");
		this.getExecuteSG().addExecuteCode("$h.openWin('refreshWin','pages.dataverify.RefreshOrgExp','上报详情',600,150,'"+id+"','"+request.getContextPath()+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 查询
	 * @param start
	 * @param limit
	 * @return
	 */
	@PageEvent("MGrid.dogridquery")
	@NoRequiredValidate           ///??????
	public int dogridQuery(int start,int limit) throws RadowException{
		String to_format = DBUtil.getDBType().equals(DBType.MYSQL)? "DATE_FORMAT(report_time,'%Y%m%d')": "to_char(report_time,'yyyymmdd')";
		StringBuffer sql = new StringBuffer("select REPORT_TYPE reporttype,"+to_format+" reporttime,RECIEVE_FTP_USERNAME recievefptusername," +
				"PACKAGE_NAME packagename, FILE_NAME filename,RECIEVE_FTP_USERNAME recieveftpusername,REPORT_STATUE reportstatue,REPORT_FTP_ID reportftpid " +
				"from report_ftp where 1=1 ");
		String st = this.getPageElement("createtimesta").getValue();
		String et = this.getPageElement("createtimeend").getValue();
		String rt = this.getPageElement("reporttype").getValue();
		if(rt != null && !rt.equals("")){
			sql.append(" and REPORT_TYPE='" +rt+ "'");
		}
		if(st != null && !st.equals("")){
			sql.append(" and "+to_format+" >='" +st+ "'");
		}
		if(et != null && !et.equals("")){
			sql.append(" and "+to_format+" <='" +et+ "'");
		}
		
		this.pageQuery(sql.toString(),"SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("MGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
