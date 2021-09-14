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

public class WarnWindowTXPageModel extends PageModel{
	
	public static int syDayCount = 0;
	public static int birthDaycount = 0;
	private CustomQueryBS cbBs=new CustomQueryBS();
	
	public WarnWindowTXPageModel(){
		HBSession sess = HBUtil.getHBSession();
		String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
		//Object syday =  sess.getSession().createSQLQuery("select t.syday from a32 t where t.userid = '"+userid+"'").uniqueResult();
		//WarnWindowTXPageModel.setSyDayCount(syday!=null && !StringUtil.isEmpty(syday.toString())?Integer.parseInt(syday.toString()):30);
		Object birthday = sess.getSession().createSQLQuery("select t.birthday from a32 t where t.userid = '"+userid+"'").uniqueResult();
		WarnWindowTXPageModel.setBirthDaycount(birthday!=null&&!StringUtil.isEmpty(birthday.toString())?Integer.parseInt(birthday.toString()):30);
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
		
		String hidden = this.getPageElement("retireTime").getValue();
//		System.out.println(hidden);
		if("".equals(hidden) || hidden.isEmpty()){
			//在hidden控件为空时执行
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
			//退休人员提醒
			//已超过退休时间
			if(list.size()>0){
				if(DBType.ORACLE == DBUtil.getDBType()){
					StringBuffer sbretire1 = new StringBuffer();
					sbretire1.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM (SELECT T.newA0107 Birthday,T.Today,MONTHS_BETWEEN (T.Today,T.newA0107) age,"
						+ "T .a0104 sex,T .a0000 FROM (SELECT b.a0104,b.A0000,isdate2 (SUBSTR(RPAD(b.A0107, 8, '01'), 0, 6)) newA0107, "
						+ "isdate2 (TO_CHAR(SYSDATE, 'yyyyMM')) Today FROM a01 b WHERE b.a0163 = '1' AND (LENGTH (b.A0107) = 6 OR "
						+ "LENGTH (b.A0107) = 8)) T WHERE T.newA0107 <> TO_DATE ('180001', 'yyyyMM')) Aa,a32,a01 WHERE "
						+ "DECODE (sex,'1',a32.mage * 12,a32.fmage * 12) < Aa.age AND a32.userid = '"+userid+"' AND aa.a0000 = a01.a0000  ");
					this.request.getSession().setAttribute("listName_swtx", "退休人员提醒");
					this.request.getSession().setAttribute("sql_swtx", "40288103556cc97701556d629135000f".equals(userid)?sbretire1.toString():sbretire1.toString()+personViewSQL);
					this.pageQuery("40288103556cc97701556d629135000f".equals(userid)?sbretire1.toString():sbretire1.toString()+personViewSQL,"SQL", start, limit);
					 
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					StringBuffer sbretire1 = new StringBuffer();
					sbretire1.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM (SELECT date_format(t.newA0107, '%Y-%m-%d') Birthday,date_format(now(), '%Y-%m-%d') "
						+ "Today, TIMESTAMPDIFF(MONTH,date_format(t.newA0107, '%Y-%m-%d'),date_format(now(), '%Y-%m-%d')) age, "
						+ "t.a0104 sex,t.a0000 FROM (SELECT b.A0000,b.A0104,RPAD(b.A0107, 8, '01') newA0107 FROM a01 b WHERE b.a0163 = '1' "
						+ "AND (LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) t ) Aa,a32,a01 WHERE CASE WHEN sex = '1' THEN a32.mage * 12 "
						+ "WHEN sex != '1' THEN a32.fmage * 12 END < Aa.age AND a32.userid = '"+userid+"' AND aa.a0000 = a01.a0000   ");
					this.request.getSession().setAttribute("listName_swtx", "退休人员提醒");
					this.request.getSession().setAttribute("sql_swtx", "40288103556cc97701556d629135000f".equals(userid)?sbretire1.toString():sbretire1.toString()+personViewSQL);
					this.pageQuery("40288103556cc97701556d629135000f".equals(userid)?sbretire1.toString():sbretire1.toString()+personViewSQL,"SQL", start, limit);
				}
				
			}else{
				if(DBType.ORACLE == DBUtil.getDBType()){
					StringBuffer sbretire2 = new StringBuffer();
					sbretire2.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM(SELECT T .newA0107 Birthday,T .Today,MONTHS_BETWEEN (T .Today, T .newA0107) age,"
						+ "T .a0104 sex,T .a0000 FROM(SELECT b.a0104,b.A0000,isdate2 (SUBSTR (RPAD(b.A0107, 8, '01'), 0, 6)) newA0107, "
						+ "isdate2 (TO_CHAR(SYSDATE, 'yyyyMM')) Today FROM a01 b	WHERE b.a0163 = '1' AND "
						+ "(LENGTH (b.A0107) = 6 OR LENGTH (b.A0107) = 8)) T WHERE T .newA0107 <> TO_DATE ('180001', 'yyyyMM')) Aa,a01 "
						+ "WHERE DECODE (sex, '1', 60 * 12, 55 * 12) < Aa.age AND aa.a0000 = a01.a0000  ");
					this.request.getSession().setAttribute("listName_swtx", "退休人员提醒");
					this.request.getSession().setAttribute("sql_swtx", "40288103556cc97701556d629135000f".equals(userid)?sbretire2.toString():sbretire2.toString()+personViewSQL);
					this.pageQuery("40288103556cc97701556d629135000f".equals(userid)?sbretire2.toString():sbretire2.toString()+personViewSQL,"SQL", start, limit);

				}else if(DBType.MYSQL == DBUtil.getDBType()){
					StringBuffer sbretire2 = new StringBuffer();
					sbretire2.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM (SELECT date_format(t.newA0107, '%Y-%m-%d') Birthday,date_format(now(), '%Y-%m-%d') "
							+ "Today,TIMESTAMPDIFF(MONTH,date_format(t.newA0107, '%Y-%m-%d'),date_format(now(), '%Y-%m-%d')) age,t.a0104 sex,t.a0000 "
							+ "FROM (SELECT b.A0000,b.A0104,RPAD(b.A0107, 8, '01') newA0107 FROM a01 b WHERE b.a0163 = '1' AND "
							+ "(LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) t) Aa,a01 WHERE CASE WHEN sex = '1' THEN 60 * 12 WHEN sex != '1' "
							+ "THEN 55 * 12 END < Aa.age AND aa.a0000 = a01.a0000  ");
					this.request.getSession().setAttribute("listName_swtx", "退休人员提醒");
					this.request.getSession().setAttribute("sql_swtx", "40288103556cc97701556d629135000f".equals(userid)?sbretire2.toString():sbretire2.toString()+personViewSQL);
					 this.pageQuery("40288103556cc97701556d629135000f".equals(userid)?sbretire2.toString():sbretire2.toString()+personViewSQL,"SQL", start, limit);
				}
				
			}
		}else{
			this.request.getSession().setAttribute("listName_swtx", "退休人员提醒");
			this.request.getSession().setAttribute("sql_swtx", hidden);
			this.pageQuery(hidden, "SQL", start, limit);
		}
		
		 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("type1")
	public int change() throws RadowException{
		HBSession sess = null;
		sess = HBUtil.getHBSession();
		String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();//获取用户id
		String sqlA32 = "from A32 t where t.userid = '"+userid+"'";
		List list = sess.createQuery(sqlA32).list();
		String number = this.getPageElement("type1").getValue();
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
		if("0".equals(number)){
			//已超过退休时间
			if(list.size()>0){
				if(DBType.ORACLE == DBUtil.getDBType()){
					//将查询结果赋值到grid
					StringBuffer sbretire1 = new StringBuffer();
					sbretire1.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM (SELECT T.newA0107 Birthday,T.Today,MONTHS_BETWEEN (T.Today,T.newA0107) age,"
						+ "T .a0104 sex,T .a0000 FROM (SELECT b.a0104,b.A0000,isdate2 (SUBSTR(RPAD(b.A0107, 8, '01'), 0, 6)) newA0107, "
						+ "isdate2 (TO_CHAR(SYSDATE, 'yyyyMM')) Today FROM a01 b WHERE b.a0163 = '1' AND (LENGTH (b.A0107) = 6 OR "
						+ "LENGTH (b.A0107) = 8)) T WHERE T.newA0107 <> TO_DATE ('180001', 'yyyyMM')) Aa,a32,a01 WHERE "
						+ "DECODE (sex,'1',a32.mage * 12,a32.fmage * 12) < Aa.age AND a32.userid = '"+userid+"' AND aa.a0000 = a01.a0000  ");
					this.getPageElement("retireTime").setValue("40288103556cc97701556d629135000f".equals(userid)?sbretire1.toString():sbretire1.toString()+personViewSQL);
					this.setNextEventName("persongrid1.dogridquery");
					
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					//将查询结果赋值到grid
					StringBuffer sbretire1 = new StringBuffer();
					sbretire1.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM (SELECT date_format(t.newA0107, '%Y-%m-%d') Birthday,date_format(now(), '%Y-%m-%d') "
						+ "Today, TIMESTAMPDIFF(MONTH,date_format(t.newA0107, '%Y-%m-%d'),date_format(now(), '%Y-%m-%d')) age, "
						+ "t.a0104 sex,t.a0000 FROM (SELECT b.A0000,b.A0104,RPAD(b.A0107, 8, '01') newA0107 FROM a01 b WHERE b.a0163 = '1' "
						+ "AND (LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) t ) Aa,a32,a01 WHERE CASE WHEN sex = '1' THEN a32.mage * 12 "
						+ "WHEN sex != '1' THEN a32.fmage * 12 END < Aa.age AND a32.userid = '"+userid+"' AND aa.a0000 = a01.a0000   ");
					this.getPageElement("retireTime").setValue("40288103556cc97701556d629135000f".equals(userid)?sbretire1.toString():sbretire1.toString()+personViewSQL);
					this.setNextEventName("persongrid1.dogridquery");	
					
				}

			}else{
				if(DBType.ORACLE == DBUtil.getDBType()){
					StringBuffer sbretire2 = new StringBuffer();
					sbretire2.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM(SELECT T .newA0107 Birthday,T .Today,MONTHS_BETWEEN (T .Today, T .newA0107) age,"
						+ "T .a0104 sex,T .a0000 FROM(SELECT b.a0104,b.A0000,isdate2 (SUBSTR (RPAD(b.A0107, 8, '01'), 0, 6)) newA0107, "
						+ "isdate2 (TO_CHAR(SYSDATE, 'yyyyMM')) Today FROM a01 b	WHERE b.a0163 = '1' AND "
						+ "(LENGTH (b.A0107) = 6 OR LENGTH (b.A0107) = 8)) T WHERE T .newA0107 <> TO_DATE ('180001', 'yyyyMM')) Aa,a01 "
						+ "WHERE DECODE (sex, '1', 60 * 12, 55 * 12) < Aa.age AND aa.a0000 = a01.a0000  ");
					this.getPageElement("retireTime").setValue("40288103556cc97701556d629135000f".equals(userid)?sbretire2.toString():sbretire2.toString()+personViewSQL);
					this.setNextEventName("persongrid1.dogridquery");
					
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					//将查询结果赋值到grid
					StringBuffer sbretire1 = new StringBuffer();
					sbretire1.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM (SELECT date_format(t.newA0107, '%Y-%m-%d') Birthday,date_format(now(), '%Y-%m-%d') "
							+ "Today,TIMESTAMPDIFF(MONTH,date_format(t.newA0107, '%Y-%m-%d'),date_format(now(), '%Y-%m-%d')) age,t.a0104 sex,t.a0000 "
							+ "FROM (SELECT b.A0000,b.A0104,RPAD(b.A0107, 8, '01') newA0107 FROM a01 b WHERE b.a0163 = '1' AND "
							+ "(LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) t) Aa,a01 WHERE CASE WHEN sex = '1' THEN 60 * 12 WHEN sex != '1' "
							+ "THEN 55 * 12 END < Aa.age AND aa.a0000 = a01.a0000  ");
					this.getPageElement("retireTime").setValue("40288103556cc97701556d629135000f".equals(userid)?sbretire1.toString():sbretire1.toString()+personViewSQL);
					this.setNextEventName("persongrid1.dogridquery");
					
					
				}
				
			}
		}else if("1".equals(number)){
			//一年内退休
			if(list.size()>0){
				if(DBType.ORACLE == DBUtil.getDBType()){
					StringBuffer sbretire3 = new StringBuffer();
					sbretire3.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM "
							+ "(SELECT T .newA0107 Birthday,T .Today,MONTHS_BETWEEN (T .Today, T .newA0107) age,T .a0104 sex,T .a0000 "
							+ "FROM (SELECT b.a0104,b.A0000,isdate2 (SUBSTR (RPAD(b.A0107, 8, '01'), 0, 6)) newA0107,"
							+ "isdate2 (TO_CHAR(SYSDATE, 'yyyyMM')) Today FROM a01 b WHERE b.a0163 = '1' AND "
							+ "( LENGTH (b.A0107) = 6 OR LENGTH (b.A0107) = 8)) T WHERE T .newA0107 <> TO_DATE ('180001', 'yyyyMM') ) "
							+ "Aa,a01,a32 WHERE DECODE (sex,'1',(a32.mage - 1) * 12,(a32.fmage - 1) * 12) <= Aa.age AND "
							+ "DECODE (sex,'1',a32.mage * 12,a32.fmage * 12) >= aa.age AND a01.a0000 = aa.a0000 AND "
							+ "a32.userid = '"+userid+"' ");
					this.getPageElement("retireTime").setValue("40288103556cc97701556d629135000f".equals(userid)?sbretire3.toString():sbretire3.toString()+personViewSQL);
					this.setNextEventName("persongrid1.dogridquery");
				    
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					StringBuffer sbretire3 = new StringBuffer();
					sbretire3.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM"
							+ "(SELECT date_format(t.newA0107, '%Y-%m-%d') Birthday,date_format(now(), '%Y-%m-%d') "
							+ "Today,TIMESTAMPDIFF(MONTH,date_format(t.newA0107, '%Y-%m-%d'),date_format(now(), '%Y-%m-%d')) "
							+ "age, t.a0104 sex,t.a0000 FROM (SELECT b.A0000,b.A0104,RPAD(b.A0107, 8, '01') newA0107 "
							+ "FROM a01 b WHERE b.a0163 = '1' AND (LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) t) "
							+ "Aa,a32,a01 WHERE CASE WHEN sex = '1' THEN (a32.mage - 1) * 12 WHEN sex != '1' THEN "
							+ "(a32.fmage - 1) * 12 END <= Aa.age AND CASE WHEN sex = '1' THEN a32.mage * 12 WHEN sex != '1' "
							+ "THEN a32.fmage * 12 END >= Aa.age AND a32.userid = '"+userid+"' AND aa.a0000 = a01.a0000  ");
					this.getPageElement("retireTime").setValue("40288103556cc97701556d629135000f".equals(userid)?sbretire3.toString():sbretire3.toString()+personViewSQL);
					this.setNextEventName("persongrid1.dogridquery");
				}
				

			}else{
				if(DBType.ORACLE == DBUtil.getDBType()){
					StringBuffer sbretire4 = new StringBuffer();
					sbretire4.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM(SELECT T .newA0107 Birthday,T .Today,"
							+ "MONTHS_BETWEEN (T .Today, T .newA0107) age,T .a0104 sex,T .a0000 FROM(SELECT b.a0104,b.A0000,isdate2 "
							+ "(SUBSTR (RPAD(b.A0107, 8, '01'), 0, 6)) newA0107,isdate2 (TO_CHAR(SYSDATE, 'yyyyMM')) Today FROM	a01 b "
							+ "WHERE b.a0163 = '1' AND (LENGTH (b.A0107) = 6 OR LENGTH (b.A0107) = 8)) T WHERE T .newA0107 <> TO_DATE "
							+ "('180001', 'yyyyMM')) Aa,a01 WHERE DECODE (sex, '1', 59 * 12, 54 * 12) <= Aa.age AND DECODE "
							+ "(sex, '1', 60 * 12, 55 * 12) >= aa.age AND a01.a0000 = aa.a0000 "); 
					this.getPageElement("retireTime").setValue("40288103556cc97701556d629135000f".equals(userid)?sbretire4.toString():sbretire4.toString()+personViewSQL);
					this.setNextEventName("persongrid1.dogridquery");
					
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					StringBuffer sbretire4 = new StringBuffer();
					sbretire4.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM(SELECT date_format"
							+ "(t.newA0107, '%Y-%m-%d') Birthday,date_format(now(), '%Y-%m-%d') Today,TIMESTAMPDIFF"
							+ "(MONTH,date_format(t.newA0107, '%Y-%m-%d'),date_format(now(), '%Y-%m-%d')) age,t.a0104 sex,t.a0000 "
							+ "FROM (SELECT b.A0000,b.A0104,RPAD(b.A0107, 8, '01') newA0107 FROM a01 b WHERE b.a0163 = '1'AND "
							+ "(LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) t) Aa,a01 WHERE CASE WHEN sex = '1' THEN 59 * 12 "
							+ "WHEN sex != '1' THEN 54 * 12 END <= Aa.age AND CASE WHEN sex = '1' THEN 60 * 12 WHEN sex != '1' "
							+ "THEN 55 * 12 END >= Aa.age AND aa.a0000 = a01.a0000  "); 
					this.getPageElement("retireTime").setValue("40288103556cc97701556d629135000f".equals(userid)?sbretire4.toString():sbretire4.toString()+personViewSQL);
					this.setNextEventName("persongrid1.dogridquery");
				}

			}
		}else if("2".equals(number)){
			//下月退休
			if(list.size()>0){
				if(DBType.ORACLE == DBUtil.getDBType()){
					StringBuffer sbretire5 = new StringBuffer();
					sbretire5.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM (SELECT T .newA0107 Birthday,"
							+ "T .Today,MONTHS_BETWEEN (T .Today, T .newA0107) age,T .a0104 sex,T .a0000 FROM"
							+ "(SELECT b.a0104,b.A0000,isdate2 (SUBSTR (RPAD(b.A0107, 8, '01'), 0, 6)) newA0107, "
							+ "isdate2 (TO_CHAR(SYSDATE, 'yyyyMM')) Today FROM a01 b WHERE b.a0163 = '1' AND "
							+ "(LENGTH (b.A0107) = 6 OR LENGTH (b.A0107) = 8)) T WHERE T .newA0107 <> TO_DATE ('180001', 'yyyyMM')) "
							+ "Aa,a01,a32 WHERE DECODE (sex,'1',a32.mage * 12 - 1,a32.fmage * 12 - 1) = aa.age "
							+ "AND a01.a0000 = aa.a0000 AND a32.userid = '"+userid+"'  ");
					this.getPageElement("retireTime").setValue("40288103556cc97701556d629135000f".equals(userid)?sbretire5.toString():sbretire5.toString()+personViewSQL);
					this.setNextEventName("persongrid1.dogridquery");
				   
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					StringBuffer sbretire5 = new StringBuffer();
					sbretire5.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM (SELECT date_format(t.newA0107, "
							+ "'%Y-%m-%d') Birthday,date_format(now(), '%Y-%m-%d') Today,TIMESTAMPDIFF(MONTH,date_format(t.newA0107, "
							+ "'%Y-%m-%d'),date_format(now(), '%Y-%m-%d')) age,t.a0104 sex,t.a0000 FROM(SELECT b.A0000,b.A0104,"
							+ "RPAD(b.A0107, 8, '01') newA0107 FROM a01 b WHERE b.a0163 = '1' AND (LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) "
							+ "t) Aa,a32,a01 WHERE CASE WHEN sex = '1' THEN a32.mage * 12 - 1 WHEN sex != '1' THEN a32.fmage * 12 - 1 "
							+ "END = Aa.age AND a32.userid = '"+userid+"' AND aa.a0000 = a01.a0000  ");
					this.getPageElement("retireTime").setValue("40288103556cc97701556d629135000f".equals(userid)?sbretire5.toString():sbretire5.toString()+personViewSQL);
					this.setNextEventName("persongrid1.dogridquery");
				    
				    
				}
				

			}else{
				if(DBType.ORACLE == DBUtil.getDBType()){
					StringBuffer sbretire6 = new StringBuffer();
					sbretire6.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM(SELECT T .newA0107 "
							+ "Birthday,T .Today,MONTHS_BETWEEN (T .Today, T .newA0107) age,T .a0104 sex,T .a0000 "
							+ "FROM(SELECT b.a0104,b.A0000,isdate2 (SUBSTR (RPAD(b.A0107, 8, '01'), 0, 6)) newA0107,"
							+ "isdate2 (TO_CHAR(SYSDATE, 'yyyyMM')) Today FROM a01 b WHERE b.a0163 = '1' AND "
							+ "(LENGTH (b.A0107) = 6 OR LENGTH (b.A0107) = 8)) T WHERE T .newA0107 <> "
							+ "TO_DATE ('180001', 'yyyyMM')) Aa,a01 WHERE DECODE (sex, '1', 60 * 12 - 1, 55 * 12 - 1) = aa.age "
							+ "AND a01.a0000 = aa.a0000 ");
					this.getPageElement("retireTime").setValue("40288103556cc97701556d629135000f".equals(userid)?sbretire6.toString():sbretire6.toString()+personViewSQL);
					this.setNextEventName("persongrid1.dogridquery");
				    
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					StringBuffer sbretire6 = new StringBuffer();
					sbretire6.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM (SELECT date_format(t.newA0107, "
							+ "'%Y-%m-%d') Birthday,date_format(now(), '%Y-%m-%d') Today,TIMESTAMPDIFF(MONTH,date_format(t.newA0107, "
							+ "'%Y-%m-%d'),date_format(now(), '%Y-%m-%d')) age,t.a0104 sex,t.a0000 FROM(SELECT b.A0000,b.A0104,"
							+ "RPAD(b.A0107, 8, '01') newA0107 FROM a01 b WHERE b.a0163 = '1' AND (LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) t)"
							+ " Aa,a01 WHERE CASE WHEN sex = '1' THEN 60 * 12 - 1 WHEN sex != '1' THEN 55 * 12 - 1 END = Aa.age and "
							+ "aa.a0000 = a01.a0000  ");
					this.getPageElement("retireTime").setValue("40288103556cc97701556d629135000f".equals(userid)?sbretire6.toString():sbretire6.toString()+personViewSQL);
					this.setNextEventName("persongrid1.dogridquery");
				    
				}
			}
		}else{
			//本月退休
            if(list.size()>0){
            	if(DBType.ORACLE == DBUtil.getDBType()){
            		StringBuffer sbretire7 = new StringBuffer();
                	sbretire7.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM(SELECT T .newA0107 Birthday,"
                			+ "T .Today,MONTHS_BETWEEN (T .Today, T .newA0107) age,T .a0104 sex,T .a0000 FROM(SELECT b.a0104,b.A0000,"
                			+ "isdate2 (SUBSTR (RPAD(b.A0107, 8, '01'), 0, 6)) newA0107,isdate2 (TO_CHAR(SYSDATE, 'yyyyMM')) Today "
                			+ "FROM a01 b WHERE b.a0163 = '1' AND (LENGTH (b.A0107) = 6 OR LENGTH (b.A0107) = 8)) T WHERE "
                			+ "T .newA0107 <> TO_DATE ('180001', 'yyyyMM')) Aa,a01,a32 WHERE DECODE (sex,'1',a32.mage * 12,a32.fmage * 12) "
                			+ "= aa.age AND a01.a0000 = aa.a0000 and a32.userid = '"+userid+"' ");
                	this.getPageElement("retireTime").setValue("40288103556cc97701556d629135000f".equals(userid)?sbretire7.toString():sbretire7.toString()+personViewSQL);
                	this.setNextEventName("persongrid1.dogridquery");
    			    
            	}else if(DBType.MYSQL == DBUtil.getDBType()){
            		StringBuffer sbretire7 = new StringBuffer();
                	sbretire7.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM (SELECT date_format(t.a0107, "
                			+ "'%Y-%m-%d') Birthday,date_format(now(), '%Y-%m-%d') Today,TIMESTAMPDIFF(MONTH,date_format(t.a0107, "
                			+ "'%Y-%m-%d'),date_format(now(), '%Y-%m-%d')) age,t.a0104 sex,t.a0000 FROM a01 t) Aa,a32,a01 WHERE CASE "
                			+ "WHEN sex = '1' THEN a32.mage * 12 WHEN sex != '1' THEN a32.fmage * 12 END = Aa.age AND "
                			+ "a32.userid = '"+userid+"' AND aa.a0000 = a01.a0000  ");
                	this.getPageElement("retireTime").setValue("40288103556cc97701556d629135000f".equals(userid)?sbretire7.toString():sbretire7.toString()+personViewSQL);
                	this.setNextEventName("persongrid1.dogridquery");
    			    
    			    
            	}
            	
			}else{
				if(DBType.ORACLE == DBUtil.getDBType()){
					StringBuffer sbretire8 = new StringBuffer();
					sbretire8.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM(SELECT T .newA0107 "
							+ "Birthday,T .Today,MONTHS_BETWEEN (T .Today, T .newA0107) age,T .a0104 sex,T .a0000 "
							+ "FROM(SELECT b.a0104,b.A0000,isdate2 (SUBSTR (RPAD(b.A0107, 8, '01'), 0, 6)) newA0107,"
							+ "isdate2 (TO_CHAR(SYSDATE, 'yyyyMM')) Today FROM a01 b WHERE b.a0163 = '1' AND (LENGTH (b.A0107) = 6 "
							+ "OR LENGTH (b.A0107) = 8)) T WHERE T .newA0107 <> TO_DATE ('180001', 'yyyyMM')) Aa,a01 WHERE "
							+ "DECODE (sex, '1', 60 * 12, 55 * 12) = aa.age AND a01.a0000 = aa.a0000  ");
					this.getPageElement("retireTime").setValue("40288103556cc97701556d629135000f".equals(userid)?sbretire8.toString():sbretire8.toString()+personViewSQL);
					this.setNextEventName("persongrid1.dogridquery");
				    
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					StringBuffer sbretire8 = new StringBuffer();
					sbretire8.append("SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM (SELECT date_format(t.newA0107, "
							+ "'%Y-%m-%d') Birthday,date_format(now(), '%Y-%m-%d') Today,TIMESTAMPDIFF(MONTH,date_format(t.newA0107, "
							+ "'%Y-%m-%d'),date_format(now(), '%Y-%m-%d')) age,t.a0104 sex,t.a0000 FROM(SELECT b.A0000,b.A0104,"
							+ "RPAD(b.A0107, 8, '01') newA0107 FROM a01 b WHERE b.a0163 = '1' AND (LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) t) "
							+ "Aa,a01 WHERE CASE WHEN sex = '1' THEN 60 * 12 WHEN sex != '1' THEN 55 * 12 END = Aa.age AND aa.a0000 = a01.a0000  ");
					this.getPageElement("retireTime").setValue("40288103556cc97701556d629135000f".equals(userid)?sbretire8.toString():sbretire8.toString()+personViewSQL);
					this.setNextEventName("persongrid1.dogridquery");
				    
				}
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	public static int getSyDayCount() {
		return syDayCount;
	}

	public static void setSyDayCount(int syDayCount) {
		WarnWindowTXPageModel.syDayCount = syDayCount;
	}
	
	public static void setBirthDaycount(int birthDaycount) {
		WarnWindowTXPageModel.birthDaycount = birthDaycount;
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
