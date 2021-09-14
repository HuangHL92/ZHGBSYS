package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.rowset.serial.SerialException;

import org.hsqldb.lib.StringUtil;

import com.fr.report.core.A.t;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;

public class WarnWindowSYQPageModel extends PageModel{
	
	public static int syDayCount = 0;
	//public static int birthDaycount = 0;
	private CustomQueryBS cbBs=new CustomQueryBS();
	
	public WarnWindowSYQPageModel(){
		HBSession sess = HBUtil.getHBSession();
		String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
		Object syday =  sess.getSession().createSQLQuery("select t.syday from a32 t where t.userid = '"+userid+"'").uniqueResult();
		WarnWindowSYQPageModel.setSyDayCount(syday!=null && !StringUtil.isEmpty(syday.toString())?Integer.parseInt(syday.toString()):30);
		//Object birthday = sess.getSession().createSQLQuery("select t.birthday from a32 t where t.userid = '"+userid+"'").uniqueResult();
		//WarnWindowSYQPageModel.setBirthDaycount(birthday!=null&&!StringUtil.isEmpty(birthday.toString())?Integer.parseInt(birthday.toString()):30);
	}
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException,AppException {
		this.setNextEventName("persongrid1.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("persongrid1.rowdbclick")
	@GridDataRange
	public int persongrid1OnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		String a0000=this.getPageElement("persongrid1").getValue("a0000",this.getPageElement("persongrid1").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			this.request.getSession().setAttribute("personIdSet", null);
			/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"','人员信息修改',1009,630,null,"
					+ "{a0000:'"+a0000+"',gridName:'persongrid1',maximizable:false,resizable:false,draggable:false},true);");*/
			this.getExecuteSG().addExecuteCode("window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height=645, width=1009, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no')");
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}
	
	/**
	 * 查询
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("persongrid1.dogridquery")
	@NoRequiredValidate         
	public int dogridQuery1(int start,int limit) throws RadowException{
		HBSession sess = null;
		sess = HBUtil.getHBSession();
		String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();//获取用户id
		String sqlA32 = "from A32 t where t.userid = '"+userid+"'";
		List list = sess.createQuery(sqlA32).list();
		String personViewSQL = "";
		if(DBType.ORACLE == DBUtil.getDBType()){
			personViewSQL = " and exists (select t.b0111 "+
			          "from COMPETENCE_USERDEPT t,a02 b "+
			          "where t.userid = '"+userid+"' "+
			          "and (t.type = '1' or t.type = '0') "+
			          "and b.a0201b = t.b0111 and b.a0000 = a01.a0000) ";
		}
		if(DBType.MYSQL== DBUtil.getDBType()){
			personViewSQL = "AND a01.a0000 IN (SELECT b.a0000 FROM a02 b WHERE b.A0201B IN "
					+ "(SELECT t.b0111 FROM competence_userdept t WHERE t.userid = '"+userid+"'))";
		}
		personViewSQL = personViewSQL + "and not exists(select 1 from COMPETENCE_USERPERSON cu "+
				"where cu.a0000 = a01.a0000 and cu.userid = '"+userid+"')";
		//试用期到期提醒
			if(list.size()>0){
				if(DBType.ORACLE == DBUtil.getDBType()){
					 //在a32中有数据的情况下
					StringBuffer sbpro1 = new StringBuffer();
					sbpro1.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM (SELECT newA0288 Workday,ADD_MONTHS (T .newA0288, 12) EndDay,T.a0000 FROM "
						+ "(SELECT b.A0000,isdate3 (RPAD(b.A0288, 8, '01')) newA0288 FROM a01 b WHERE b.a0163 = '1' AND b.A0221 IN "
						+ "( '1A98','1B98','1C98','27','911','C98') AND (LENGTH (b.a0288) = 6 OR LENGTH (b.a0288) = 8)) T WHERE "
						+ "T .newA0288 <> TO_DATE ('18000101', 'yyyyMMdd')) Aa,a32,a01 WHERE ((Aa.EndDay - TRUNC (SYSDATE) <= a32.syday "
						+ "AND Aa.EndDay - TRUNC (SYSDATE) >= 0) OR TRUNC (MONTHS_BETWEEN (SYSDATE, Aa.WORKDAY) / 12) >= 1) AND "
						+ "a32.userid = '"+userid+"' AND aa.a0000 = a01.a0000   ");
					this.request.getSession().setAttribute("listName_swtx", "试用期到期提醒");
					this.request.getSession().setAttribute("sql_swtx", "40288103556cc97701556d629135000f".equals(userid)?sbpro1.toString():sbpro1.toString()+personViewSQL);
					this.pageQuery("40288103556cc97701556d629135000f".equals(userid)?sbpro1.toString():sbpro1.toString()+personViewSQL,"SQL", start, limit);

				}else if(DBType.MYSQL == DBUtil.getDBType()){
					 //在a32中有数据的情况下
					StringBuffer sbpro1 = new StringBuffer();
					sbpro1.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM (SELECT date_format(t.newA0288, '%Y-%m-%d') Workday,"
						+ "date_add(date_format(t.newA0288, '%Y-%m-%d'),INTERVAL 12 MONTH) EndDay, t.a0000 "
						+ "FROM (SELECT b.A0000,RPAD(b.A0288, 8, '01') newA0288 FROM a01 b WHERE b.a0163 = '1' "
						+ "AND b.A0221 IN ('1A98','1B98','1C98','27','911','C98')AND (LENGTH(b.a0288) = 6 OR LENGTH(b.a0288) = 8)) t) Aa,"
						+ "a32,a01 WHERE ((TIMESTAMPDIFF(DAY,date_format(now(), '%Y-%m-%d'),Aa.EndDay) <= a32.syday AND "
						+ "TIMESTAMPDIFF(DAY,date_format(now(), '%Y-%m-%d'),Aa.EndDay) >= 0) OR "
						+ "TIMESTAMPDIFF(YEAR,Aa.Workday,date_format(now(), '%Y-%m-%d')) >= 1) AND "
						+ "a32.userid = '"+userid+"' AND a01.A0000 = Aa.a0000  ");
					this.request.getSession().setAttribute("listName_swtx", "试用期到期提醒");
					this.request.getSession().setAttribute("sql_swtx", "40288103556cc97701556d629135000f".equals(userid)?sbpro1.toString():sbpro1.toString()+personViewSQL);
					this.pageQuery("40288103556cc97701556d629135000f".equals(userid)?sbpro1.toString():sbpro1.toString()+personViewSQL,"SQL", start, limit);
				}
		   
				
			}else{
				if(DBType.ORACLE == DBUtil.getDBType()){
					StringBuffer sbpro2 = new StringBuffer();
					sbpro2.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM (SELECT newA0288 Workday,ADD_MONTHS (newA0288, 12) EndDay,T .a0000 "
						+ "FROM(SELECT b.A0000,isdate3 (RPAD(b.A0288, 8, '01')) newA0288 FROM a01 b WHERE b.a0163 = '1' "
						+ "AND b.A0221 IN ('1A98','1B98','1C98','27','911','C98') AND (LENGTH (b.a0288) = 6 OR LENGTH (b.a0288) = 8)) T "
						+ "WHERE T .newA0288 <> TO_DATE ('18000101', 'yyyyMMdd')) Aa,a01 WHERE "
						+ "((Aa.EndDay - TRUNC (SYSDATE) <= 30 AND Aa.EndDay - TRUNC (SYSDATE) >= 0) OR "
						+ "TRUNC (MONTHS_BETWEEN (SYSDATE, Aa.WORKDAY) / 12) >= 1) AND aa.a0000 = a01.a0000   ");
					this.request.getSession().setAttribute("listName_swtx", "试用期到期提醒");
					this.request.getSession().setAttribute("sql_swtx", "40288103556cc97701556d629135000f".equals(userid)?sbpro2.toString():sbpro2.toString()+personViewSQL);
					this.pageQuery("40288103556cc97701556d629135000f".equals(userid)?sbpro2.toString():sbpro2.toString()+personViewSQL,"SQL", start, limit);
					
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					//试用期到期提醒
					StringBuffer sbpro2 = new StringBuffer();
					sbpro2.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM(SELECT date_format(t.newA0288, '%Y-%m-%d') Workday,"
						+ "date_add(date_format(t.newA0288, '%Y-%m-%d'),INTERVAL 12 MONTH) EndDay,t.a0000 "
						+ "FROM (SELECT b.A0000,RPAD(b.A0288, 8, '01') newA0288 FROM a01 b WHERE b.a0163 = '1' "
						+ "AND b.A0221 IN ('1A98','1B98','1C98','27','911','C98') AND (LENGTH(b.a0288) = 6 OR LENGTH(b.a0288) = 8)) t) Aa,a01 "
						+ "WHERE((TIMESTAMPDIFF(DAY,date_format(now(), '%Y-%m-%d'),Aa.EndDay) <= 30 AND "
						+ "TIMESTAMPDIFF(DAY,date_format(now(), '%Y-%m-%d'),Aa.EndDay) >= 0) OR "
						+ "TIMESTAMPDIFF(YEAR,Aa.Workday,date_format(now(), '%Y-%m-%d')) >= 1) AND Aa.a0000 = a01.A0000 ");
					this.request.getSession().setAttribute("listName_swtx", "试用期到期提醒");
					this.request.getSession().setAttribute("sql_swtx", "40288103556cc97701556d629135000f".equals(userid)?sbpro2.toString():sbpro2.toString()+personViewSQL);
					this.pageQuery("40288103556cc97701556d629135000f".equals(userid)?sbpro2.toString():sbpro2.toString()+personViewSQL,"SQL", start, limit);
				}
				
			}
		 
		return EventRtnType.SPE_SUCCESS;
	}

	public static int getSyDayCount() {
		return syDayCount;
	}

	public static void setSyDayCount(int syDayCount) {
		WarnWindowSYQPageModel.syDayCount = syDayCount;
	}
	
	/**
	 * 保存列表
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 * @throws UnsupportedEncodingException 
	 * @throws SQLException 
	 * @throws SerialException 
	 */
	@PageEvent("dataSave.onclick")
	@NoRequiredValidate
	@Transaction
	public int dataSave() throws UnsupportedEncodingException, SerialException, AppException, SQLException, RadowException{
		
		//判断列表是否有数据
		List<HashMap<String,Object>> list22 = this.getPageElement("persongrid1").getValueList();
		if(list22.size() == 0){
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','没有要保存的数据！',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		String saveName = this.request.getSession().getAttribute("listName_swtx").toString();
		String sql = this.request.getSession().getAttribute("sql_swtx").toString();
		
		String loginName=SysUtil.getCacheCurrentUser().getLoginname();
		sql = "select a0000 from ("+sql+") a";
		cbBs.saveSWTXList(saveName, "", "", loginName,sql);
		this.setMainMessage("保存成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
