package com.insigma.siis.local.pagemodel.edu;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.EduXrdx;
import com.insigma.siis.local.business.entity.MeetingTheme;
import com.insigma.siis.local.business.entity.Train;
import com.insigma.siis.local.business.entity.TrainAtt;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;

public class xrdxListPageModel extends PageModel{
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String xrdx00=this.getPageElement("xrdx00").getValue();
    	@SuppressWarnings("unchecked")
		List<String> xrdx01s= HBUtil.getHBSession().createSQLQuery("select xrdx01 from edu_xrdx where xrdx00='"+xrdx00+"' ").list();
    	if(xrdx01s.size()>0) {
    		this.getPageElement("xrdx01").setValue(xrdx01s.get(0));
    	}
		this.setNextEventName("editgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("editgrid.dogridquery")
	@NoRequiredValidate         
	public int grid1Query(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String xrdx00=this.getPageElement("xrdx00").getValue();
		String sql="select t.a0000,a01.a0101,a01.a0104,a01.a0107,a01.a0111a,a01.a0141,b01.b0101,a01.a0192a,t.sortid,a01.a0184," + 
				" a37.a3707c,a01.a0288,a01.a0192f,"+
				" (select sum(t1.xrdx08) from edu_xrdx t1, edu_xrdx_ry t2  where t1.xrdx00=t2.xrdx00  and t2.a0000=a01.a0000 and t1.xrdx02<>'0203') xrdx08," +
				" (select sum(t1.xrdx09) from edu_xrdx t1, edu_xrdx_ry t2  where t1.xrdx00=t2.xrdx00  and t2.a0000=a01.a0000 and t1.xrdx02<>'0203') xrdx09," +
				" (select sum(t1.xrdx08) from edu_xrdx t1, edu_xrdx_ry t2  where t1.xrdx00=t2.xrdx00  and t2.a0000=a01.a0000 and t1.xrdx02<>'0203' and MONTHS_BETWEEN(to_date(to_char(sysdate,'yyyyMMdd'),'yyyymmdd'),to_date(t1.xrdx05,'yyyymmdd'))<=36) xrdx083," +
				" (select sum(t1.xrdx09) from edu_xrdx t1, edu_xrdx_ry t2  where t1.xrdx00=t2.xrdx00  and t2.a0000=a01.a0000 and t1.xrdx02<>'0203' and MONTHS_BETWEEN(to_date(to_char(sysdate,'yyyyMMdd'),'yyyymmdd'),to_date(t1.xrdx05,'yyyymmdd'))<=36) xrdx093," +
				" (select sum(t1.xrdx08) from edu_xrdx t1, edu_xrdx_ry t2  where t1.xrdx00=t2.xrdx00  and t2.a0000=a01.a0000 and t1.xrdx02<>'0203' and MONTHS_BETWEEN(to_date(to_char(sysdate,'yyyyMMdd'),'yyyymmdd'),to_date(t1.xrdx05,'yyyymmdd'))<=60) xrdx085," +
				" (select sum(t1.xrdx09) from edu_xrdx t1, edu_xrdx_ry t2  where t1.xrdx00=t2.xrdx00  and t2.a0000=a01.a0000 and t1.xrdx02<>'0203' and MONTHS_BETWEEN(to_date(to_char(sysdate,'yyyyMMdd'),'yyyymmdd'),to_date(t1.xrdx05,'yyyymmdd'))<=60) xrdx095" +	
				" from a01,edu_xrdx_ry t,a02,b01,a37" + 
				" where a01.a0000=t.a0000 and a01.a0000=a37.a0000(+) and a01.a0163='1' " + 
				" and a01.a0000=a02.a0000" + 
				" and b01.b0111=a02.a0201b" + 
				" and a02.a0279='1' " + 
				" and t.xrdx00='"+xrdx00+"'" + 
				" order by t.sortid" ;
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("dodel")
	@GridDataRange
	@NoRequiredValidate
	public int dodel(String a0000) throws RadowException{ 
		String xrdx00=this.getPageElement("xrdx00").getValue();
		String sortid=this.getPageElement("sortid").getValue();
		HBSession sess = HBUtil.getHBSession();
		try {
			Statement stmt = sess.connection().createStatement();
			String sql="";
			if(sortid!=null && !"".equals(sortid) && !"null".equals(sortid)) {
				sql="delete from edu_xrdx_ry where xrdx00='"+xrdx00+"' and a0000='"+a0000+"' and sortid="+sortid+"";
			}else {
				sql="delete from edu_xrdx_ry where xrdx00='"+xrdx00+"' and a0000='"+a0000+"' and sortid is null ";
			}
			
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setNextEventName("editgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;			
	}
	
	//确认保存
	@PageEvent("TJsave")
	public int saveSelect(String a0000s) throws RadowException, AppException{
		String xrdx00=this.getPageElement("xrdx00").getValue();
		String[] a0000ss=a0000s.split(",");
		String sql="";
		HBSession sess = HBUtil.getHBSession();
		
		try {
			int i=Integer.valueOf(getMax_sort(xrdx00,sess))+1;
			HBSession hbsess = HBUtil.getHBSession();
			Statement  stmt = hbsess.connection().createStatement();
			ArrayList<String> a0000list= new ArrayList<String>();
			for(String a0000:a0000ss) {
				sql="delete from edu_xrdx_ry where xrdx00='"+xrdx00+"' and a0000='"+a0000+"'";
				stmt.executeUpdate(sql);
				sql="insert into edu_xrdx_ry(xrdx00,a0000,sortid) values ('"+xrdx00+"','"+a0000+"',"+i+")";
				stmt.executeUpdate(sql);
				i++;		
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getExecuteSG().addExecuteCode("queryPerson();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 获取最大的排序号
	 * @return
	 */
	public String getMax_sort(String xrdx00,HBSession session){
    	@SuppressWarnings("unchecked")
		List<String> sorts= HBUtil.getHBSession().createSQLQuery("select sortid from edu_xrdx_ry where xrdx00='"+xrdx00+"' order by sortid desc ").list();
    	String sort="0";
    	if(sorts.size()>0) {
    		if(sorts.get(0)!=null) {
    			sort=String.valueOf(sorts.get(0));
    		}
    	}
		return sort;
	}
	
	
	
	/**
	 * 修改人员信息的双击事件
	 *
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("editgrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		//获得当前页面是浏览  还是  编辑  机构树
		//String lookOrWrite = this.getPageElement("lookOrWrite").getValue();

		String a0000=this.getPageElement("editgrid").getValue("a0000",this.getPageElement("editgrid").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			String rmbs=this.getPageElement("rmbs").getValue();
			/*if(rmbs.contains(a0000)) {
				this.setMainMessage("已经打开了");
				return EventRtnType.FAILD;
			}*/
			this.getPageElement("rmbs").setValue(rmbs+"@"+a0000);
			this.getExecuteSG().addExecuteCode("var rmbWin=window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height='+(screen.height-30)+', width=1024, top=0, left='+(screen.width/2-512)+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');var loop = setInterval(function() {if(rmbWin.closed) {clearInterval(loop);removeRmbs('"+a0000+"');}}, 500);");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}
}