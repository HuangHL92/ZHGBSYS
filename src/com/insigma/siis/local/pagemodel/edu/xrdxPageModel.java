package com.insigma.siis.local.pagemodel.edu;


import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fr.stable.StringUtils;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.Train;

public class xrdxPageModel extends PageModel{
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		this.setNextEventName("updateStatus");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		Calendar  c = new  GregorianCalendar();
		int year = c.get(Calendar.YEAR);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for(int i=0;i<100;i++){
			map.put(""+(year-i), year-i);
			if((year-i)==2015) {
				break;
			}
		}
		((Combo)this.getPageElement("year")).setValueListForSelect(map); 
//		if(this.getPageElement("year").getValue()==null || "0".equals(this.getPageElement("year").getValue())) {
//		String now=String.valueOf(year);
//		this.getPageElement("year").setValue(now); 
		this.setNextEventName("editgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("updateStatus")
	@NoRequiredValidate
	public int updateStatus() throws RadowException {
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		String now = sf.format(System.currentTimeMillis()).replace("-", "");
		HBSession sess = HBUtil.getHBSession();
		try {
			Statement stmt = sess.connection().createStatement();
			
			String sql="update edu_xrdx set status='2' where status='1' and xrdx05<"+now+"";
			stmt.executeUpdate(sql);

			sql="update edu_xrdx set status='3' where status='2' and xrdx06<"+now+"";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("editgrid.dogridquery")
	@NoRequiredValidate         
	public int grid1Query(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String year=this.getPageElement("year").getValue();
		String xrdx01=this.getPageElement("xrdx01").getValue();
		String type=this.getPageElement("type").getValue();
		String bjtype=this.getPageElement("bjtype").getValue();
		String sql=" select xrdx00,year," + 
				" decode(type,1,'上级选调',2,'本级选调',3,'其他培训','') type," + 
				" xrdx01,(select code_name from code_value t where code_type='PXLX' and code_value=t.xrdx02) xrdx02," + 
				" (select code_name from code_value t where code_type='TRANORG' and code_value=t.xrdx03) xrdx03," + 
				" decode(xrdx04,1,'是','') xrdx04," + 
				" xrdx05,xrdx06,xrdx07,xrdx08,xrdx09,xrdx10,xrdx11," + 
				" (select code_name from code_value t where code_type='ZB27' and code_value=t.xrdx12) xrdx12," + 
				" decode(status,1,'报名中',2,'正在培训',3,'培训结束','') status" + 
				" from edu_xrdx t  where 1 = 1 ";
		if(year!=null && !"".equals(year)) {
			sql+=" and year='"+year+"' ";
		}
		if(xrdx01!=null && !"".equals(xrdx01)) {
			sql+=" and xrdx01 like '%"+xrdx01+"%' ";
		}
		if(type!=null && !"".equals(type)) {
			sql+=" and type='"+type+"' ";
		}
		if(bjtype!=null && !"".equals(bjtype)) {
			sql+=" and xrdx02='"+bjtype+"' ";
		}
		sql+="  order by year desc ,xrdx05 desc ";
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("editgrid.rowdbclick")
	@GridDataRange       
	public int grid1rowdbclick() throws RadowException, AppException {
		String xrdx00 = this.getPageElement("editgrid").getValue("xrdx00", this.getPageElement("editgrid").getCueRowIndex()).toString();
		String xrdx01 = this.getPageElement("editgrid").getValue("xrdx01", this.getPageElement("editgrid").getCueRowIndex()).toString();
	
//		this.getExecuteSG().addExecuteCode("$h.openWin('xrdxList', 'pages.edu.xrdxList', '选任调训人员列表 ',1410,900, null, g_contextpath, null, { maximizable: false,resizable: false,closeAction: 'close',xrdx00:'"+xrdx00+"'})" );			
		this.getExecuteSG().addExecuteCode("$h.openWin('xrdxList', 'pages.edu.xrdxList', '"+xrdx01+"', 1410,900, null, g_contextpath, null, { maximizable: false,resizable: false,closeAction: 'close',xrdx00:'"+xrdx00+"'})" );

		return EventRtnType.NORMAL_SUCCESS;

	}
	@PageEvent("delBZ")
	@GridDataRange
	@NoRequiredValidate
	public int delBZ(String xrdx00) throws RadowException{ 
		HBSession sess = HBUtil.getHBSession();
		try {
			Statement stmt = sess.connection().createStatement();
			
			String sql="delete from edu_xrdx where xrdx00='"+xrdx00+"'";
			stmt.executeUpdate(sql);

			sql="delete from edu_xrdx_ry where xrdx00='"+xrdx00+"'";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setNextEventName("editgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
}