package com.insigma.siis.local.pagemodel.publicServantManage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.insigma.odin.framework.ActionSupport;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A32;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;

public class MsgResponse extends ActionSupport {
	
	public ActionForward start(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		final Set<String> set = new HashSet<String>();
		
		Runnable r = new Runnable() {
			public void run() {
				String personViewSQL = "";
				if(DBType.ORACLE == DBUtil.getDBType()){
					personViewSQL = " and exists (select t.b0111 "+
					          "from COMPETENCE_USERDEPT t,a02 b "+
					          "where t.userid = '"+SysUtil.getCacheCurrentUser().getUserVO().getId()+"' "+
					          "and (t.type = '1' or t.type = '0') "+
					          "and b.a0201b = t.b0111 and b.a0000 = a01.a0000) "
					          + "and not exists(select 1 from COMPETENCE_USERPERSON cu where cu.a0000 = a01.a0000 and cu.userid = '"+SysUtil.getCacheCurrentUser().getUserVO().getId()+"')";
				}
				if(DBType.MYSQL== DBUtil.getDBType()){
					personViewSQL = "AND a01.a0000 IN (SELECT b.a0000 FROM a02 b WHERE b.A0201B IN "
							+ "(SELECT t.b0111 FROM competence_userdept t WHERE t.userid = '"+SysUtil.getCacheCurrentUser().getUserVO().getId()+"'))"
							+ " and not exists(select 1 from COMPETENCE_USERPERSON cu where cu.a0000 = a01.a0000 and cu.userid = '"+SysUtil.getCacheCurrentUser().getUserVO().getId()+"')";
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String sql = "SELECT A0107,A0000 FROM A01 A WHERE LENGTH (A .A0107) = 8";
				List<Object[]> listA0107 = HBUtil.getHBSession().createSQLQuery(sql+personViewSQL).list();
				for(Object[] obj : listA0107){
					//出生年月
					Object a = obj[0];
					if(a != null){
						String a0107 = a.toString();
						try {
							Date d = sdf.parse(a0107);
							if(!a0107.equals(sdf.format(d))){
								String idCard = "";
								if(obj[1]!=null){
									idCard = obj[1].toString();
								}
								set.add(idCard);
							}
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		};
		Thread t1 = new Thread(r);
		
		Runnable r2 = new Runnable() {
			public void run() {
				String personViewSQL = "";
				if(DBType.ORACLE == DBUtil.getDBType()){
					personViewSQL = " and exists (select t.b0111 "+
					          "from COMPETENCE_USERDEPT t,a02 b "+
					          "where t.userid = '"+SysUtil.getCacheCurrentUser().getUserVO().getId()+"' "+
					          "and (t.type = '1' or t.type = '0') "+
					          "and b.a0201b = t.b0111 and b.a0000 = a01.a0000) "
					          + "and not exists(select 1 from COMPETENCE_USERPERSON cu where cu.a0000 = a01.a0000 and cu.userid = '"+SysUtil.getCacheCurrentUser().getUserVO().getId()+"')";
				}
				if(DBType.MYSQL== DBUtil.getDBType()){
					personViewSQL = "AND a01.a0000 IN (SELECT b.a0000 FROM a02 b WHERE b.A0201B IN "
							+ "(SELECT t.b0111 FROM competence_userdept t WHERE t.userid = '"+SysUtil.getCacheCurrentUser().getUserVO().getId()+"'))"
							+ " and not exists(select 1 from COMPETENCE_USERPERSON cu where cu.a0000 = a01.a0000 and cu.userid = '"+SysUtil.getCacheCurrentUser().getUserVO().getId()+"')";
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String sql2 = "SELECT A0134,A0000 FROM A01 A WHERE LENGTH (A .A0134) = 8";
				List<Object[]> listA0134 = HBUtil.getHBSession().createSQLQuery(sql2+personViewSQL).list();
				for(Object[] obj : listA0134){
					//参加工作时间
					Object b = obj[0];
					if(b != null){
						String a0134 = b.toString();
						try {
							Date d = sdf.parse(a0134);
							if(!a0134.equals(sdf.format(d))){
								String idCard = "";
								if(obj[1]!=null){
									idCard = obj[1].toString();
								}
								set.add(idCard);
							}
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		};
		Thread t2 = new Thread(r2);
		
		t1.start();
		t2.start();
		
		
		String s = "";
		try {
			t1.join();
			t2.join();
			for(String str : set){
				s = s + str + ",";
			}
			
			if(!"".equals(s) && s.length()>0){
				s = s.substring(0, s.length()-1);
				//this.setMainMessage(s+",上述人员的出生日期或参加工作时间格式有问题,请修改后再进行'事务提醒'操作！");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.doSuccess(request, s);
		return this.ajaxResponse(request, response);
	}
	
	public ActionForward load(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, String> map = new HashMap<String, String>();
		//初始化
		map.put("birthday", "0");
		map.put("probation", "0");
		map.put("retire", "0");
		map.put("getIn", "0");
		map.put("getOut","0");
		map.put("back","0");
		
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
		if(list.size()>0){
			if(DBType.ORACLE == DBUtil.getDBType()){
				//在A32表中有数据的情况
				//生日到期人数提醒
				StringBuffer sbbirth1 = new StringBuffer();
				sbbirth1.append("SELECT COUNT (1) FROM (SELECT CASE WHEN Days < 0 THEN ADD_MONTHS (Birthday, 12) ELSE Birthday "
						+ "END AS Birthday,b.A0000 FROM (SELECT isdate3 (TO_CHAR (SYSDATE, 'yyyy') || SUBSTR (newA0107, 5)) Birthday, "
						+ "isdate3 (TO_CHAR (SYSDATE, 'yyyy') || SUBSTR (newA0107, 5)) - TRUNC (SYSDATE) Days,isdate3 (newA0107) "
						+ "newDdays,A.A0000 FROM (SELECT b.A0000,RPAD (b.A0107, 8, '01') newA0107 FROM a01 b WHERE b.a0163 = '1' "
						+ "AND (LENGTH (b.A0107) = 6 OR LENGTH (b.A0107) = 8)) A) b WHERE b.NEWDDAYS <> TO_DATE ('18000101', 'yyyyMMdd')) "
						+ "Yy,a01,a32 WHERE Yy.Birthday - a32.birthday <= TRUNC (SYSDATE) AND a32.userid = '"+userid+"' AND yy.a0000 = a01.a0000  ");
				//试用期到期提醒
				StringBuffer sbpro1 = new StringBuffer();
				sbpro1.append("SELECT COUNT (1) FROM (SELECT newA0288 Workday,ADD_MONTHS (T .newA0288, 12) EndDay,T.a0000 FROM "
						+ "(SELECT b.A0000,isdate3 (RPAD(b.A0288, 8, '01')) newA0288 FROM a01 b WHERE b.a0163 = '1' AND b.A0221 IN "
						+ "( '1A98','1B98','1C98','27','911','C98') AND (LENGTH (b.a0288) = 6 OR LENGTH (b.a0288) = 8)) T WHERE "
						+ "T .newA0288 <> TO_DATE ('18000101', 'yyyyMMdd')) Aa,a32,a01 WHERE ((Aa.EndDay - TRUNC (SYSDATE) <= a32.syday "
						+ "AND Aa.EndDay - TRUNC (SYSDATE) >= 0) OR TRUNC (MONTHS_BETWEEN (SYSDATE, Aa.WORKDAY) / 12) >= 1) AND "
						+ "a32.userid = '"+userid+"' AND aa.a0000 = a01.a0000  ");
				//已超过退休时间人员
				StringBuffer sbretire1 = new StringBuffer();
				sbretire1.append("SELECT COUNT (1) FROM (SELECT T.newA0107 Birthday,T.Today,MONTHS_BETWEEN (T.Today,T.newA0107) age,"
						+ "T .a0104 sex,T .a0000 FROM (SELECT b.a0104,b.A0000,isdate2 (SUBSTR(RPAD(b.A0107, 8, '01'), 0, 6)) newA0107, "
						+ "isdate2 (TO_CHAR(SYSDATE, 'yyyyMM')) Today FROM a01 b WHERE b.a0163 = '1' AND (LENGTH (b.A0107) = 6 OR "
						+ "LENGTH (b.A0107) = 8)) T WHERE T.newA0107 <> TO_DATE ('180001', 'yyyyMM')) Aa,a32,a01 WHERE "
						+ "DECODE (sex,'1',a32.mage * 12,a32.fmage * 12) < Aa.age AND a32.userid = '"+userid+"' AND aa.a0000 = a01.a0000  ");
				
				ResultSet rs1 = null;
				ResultSet rs2 = null;
				ResultSet rs3 = null;
				try {
					rs1 = sess.connection().prepareStatement("40288103556cc97701556d629135000f".equals(userid)?sbbirth1.toString():sbbirth1.toString()+personViewSQL).executeQuery();
					rs2 = sess.connection().prepareStatement("40288103556cc97701556d629135000f".equals(userid)?sbpro1.toString():sbpro1.toString()+personViewSQL).executeQuery();
					rs3 = sess.connection().prepareStatement("40288103556cc97701556d629135000f".equals(userid)?sbretire1.toString():sbretire1.toString()+personViewSQL).executeQuery();
					if(rs1 != null && rs1.next()){
						map.put("birthday", rs1.getString(1));
					}
					if(rs2 != null && rs2.next()){
						map.put("probation", rs2.getString(1));
					}
					if(rs3 != null && rs3.next()){
						map.put("retire", rs3.getString(1));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else if(DBType.MYSQL == DBUtil.getDBType()){
				//在A32表中有数据的情况
				//生日到期人数提醒
				StringBuffer sbbirth1 = new StringBuffer();
				sbbirth1.append("SELECT count(1) FROM (SELECT CASE WHEN Days < 0 THEN date_add(Birthday, INTERVAL 12 MONTH) "
						+ "ELSE date_format(Birthday, '%Y-%m-%d') END AS Birthday,A0000 FROM (SELECT date_format(concat(date_format(now(), '%Y'),"
						+ "Substring(newA0107, 5)),'%Y-%m-%d') Birthday, TIMESTAMPDIFF(DAY,date_format(now(), '%Y-%m-%d'),"
						+ "date_format(concat(date_format(now(), '%Y'),Substring(newA0107, 5)),'%Y-%m-%d')) Days,newA0107,A0000 "
						+ "FROM (SELECT b.A0000,RPAD(b.A0107, 8, '01') newA0107 FROM a01 b WHERE b.a0163 = '1' AND (LENGTH(b.A0107) = 6 OR "
						+ "LENGTH(b.A0107) = 8)) a) xx) Yy,a32,a01 WHERE TIMESTAMPDIFF(DAY,date_format(now(), '%Y-%m-%d'),Yy.Birthday) <= a32.birthday "
						+ "AND a32.userid = '"+userid+"' AND a01.A0000 = Yy.A0000  ");
				//试用期到期提醒
				StringBuffer sbpro1 = new StringBuffer();
				sbpro1.append("SELECT count(1) FROM (SELECT date_format(t.newA0288, '%Y-%m-%d') Workday,"
						+ "date_add(date_format(t.newA0288, '%Y-%m-%d'),INTERVAL 12 MONTH) EndDay, t.a0000 "
						+ "FROM (SELECT b.A0000,RPAD(b.A0288, 8, '01') newA0288 FROM a01 b WHERE b.a0163 = '1' "
						+ "AND b.A0221 IN ('1A98','1B98','1C98','27','911','C98')AND (LENGTH(b.a0288) = 6 OR LENGTH(b.a0288) = 8)) t) Aa,"
						+ "a32,a01 WHERE ((TIMESTAMPDIFF(DAY,date_format(now(), '%Y-%m-%d'),Aa.EndDay) <= a32.syday AND "
						+ "TIMESTAMPDIFF(DAY,date_format(now(), '%Y-%m-%d'),Aa.EndDay) >= 0) OR "
						+ "TIMESTAMPDIFF(YEAR,Aa.Workday,date_format(now(), '%Y-%m-%d')) >= 1) AND "
						+ "a32.userid = '"+userid+"' AND a01.A0000 = Aa.a0000 ");
				

				//已超过退休时间人员
				StringBuffer sbretire1 = new StringBuffer();
				sbretire1.append("SELECT count(1) FROM (SELECT date_format(t.newA0107, '%Y-%m-%d') Birthday,date_format(now(), '%Y-%m-%d') "
						+ "Today, TIMESTAMPDIFF(MONTH,date_format(t.newA0107, '%Y-%m-%d'),date_format(now(), '%Y-%m-%d')) age, "
						+ "t.a0104 sex,t.a0000 FROM (SELECT b.A0000,b.A0104,RPAD(b.A0107, 8, '01') newA0107 FROM a01 b WHERE b.a0163 = '1' "
						+ "AND (LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) t ) Aa,a32,a01 WHERE CASE WHEN sex = '1' THEN a32.mage * 12 "
						+ "WHEN sex != '1' THEN a32.fmage * 12 END < Aa.age AND a32.userid = '"+userid+"' AND aa.a0000 = a01.a0000  ");
				
				ResultSet rs1 = null;
				ResultSet rs2 = null;
				ResultSet rs3 = null;
				try {
					rs1 = sess.connection().prepareStatement("40288103556cc97701556d629135000f".equals(userid)?sbbirth1.toString():sbbirth1.toString()+personViewSQL).executeQuery();
					rs2 = sess.connection().prepareStatement("40288103556cc97701556d629135000f".equals(userid)?sbpro1.toString():sbpro1.toString()+personViewSQL).executeQuery();
					rs3 = sess.connection().prepareStatement("40288103556cc97701556d629135000f".equals(userid)?sbretire1.toString():sbretire1.toString()+personViewSQL).executeQuery();
					if(rs1 != null && rs1.next()){
						map.put("birthday", rs1.getString(1));
					}
					if(rs2 != null && rs2.next()){
						map.put("probation", rs2.getString(1));
					}
					if(rs3 != null && rs3.next()){
						map.put("retire", rs3.getString(1));
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}

			}
			
		}else{
			if(DBType.ORACLE == DBUtil.getDBType()){
				//在A32中无数据的情况
				//生日到期人数提醒
				StringBuffer sbbirth2 = new  StringBuffer();
				sbbirth2.append("SELECT COUNT (1) FROM (SELECT CASE WHEN Days < 0 THEN ADD_MONTHS (Birthday, 12) "
						+ "ELSE Birthday END AS Birthday,A0000 FROM (SELECT isdate3 (TO_CHAR (SYSDATE, 'yyyy') || SUBSTR (A.newA0107, 5)) "
						+ "Birthday,isdate3 (TO_CHAR (SYSDATE, 'yyyy') || SUBSTR (A.newA0107, 5)) - TRUNC (SYSDATE) Days,"
						+ "isdate3 (newA0107) newDdays, A.A0000 FROM (SELECT b.A0000,RPAD (b.A0107, 8, '01') newA0107 FROM a01 b "
						+ "WHERE b.a0163 = '1' AND (LENGTH (b.A0107) = 6 OR LENGTH (b.A0107) = 8)) A) b WHERE "
						+ "b.NEWDDAYS <> TO_DATE ('18000101', 'yyyyMMdd')) Yy,a01 WHERE Yy.Birthday - 30 <= TRUNC (SYSDATE) "
						+ "AND yy.a0000 = a01.a0000  ");
				//试用期到期提醒
				StringBuffer sbpro2 = new StringBuffer();
				sbpro2.append("SELECT COUNT (1) FROM (SELECT newA0288 Workday,ADD_MONTHS (newA0288, 12) EndDay,T .a0000 "
						+ "FROM(SELECT b.A0000,isdate3 (RPAD(b.A0288, 8, '01')) newA0288 FROM a01 b WHERE b.a0163 = '1' "
						+ "AND b.A0221 IN ('1A98','1B98','1C98','27','911','C98') AND (LENGTH (b.a0288) = 6 OR LENGTH (b.a0288) = 8)) T "
						+ "WHERE T .newA0288 <> TO_DATE ('18000101', 'yyyyMMdd')) Aa,a01 WHERE "
						+ "((Aa.EndDay - TRUNC (SYSDATE) <= 30 AND Aa.EndDay - TRUNC (SYSDATE) >= 0) OR "
						+ "TRUNC (MONTHS_BETWEEN (SYSDATE, Aa.WORKDAY) / 12) >= 1) AND aa.a0000 = a01.a0000  ");
				//已超过退休时间人员
				StringBuffer sbretire2 = new StringBuffer();
				sbretire2.append("SELECT COUNT (1) FROM(SELECT T .newA0107 Birthday,T .Today,MONTHS_BETWEEN (T .Today, T .newA0107) age,"
						+ "T .a0104 sex,T .a0000 FROM(SELECT b.a0104,b.A0000,isdate2 (SUBSTR (RPAD(b.A0107, 8, '01'), 0, 6)) newA0107, "
						+ "isdate2 (TO_CHAR(SYSDATE, 'yyyyMM')) Today FROM a01 b	WHERE b.a0163 = '1' AND "
						+ "(LENGTH (b.A0107) = 6 OR LENGTH (b.A0107) = 8)) T WHERE T .newA0107 <> TO_DATE ('180001', 'yyyyMM')) Aa,a01 "
						+ "WHERE DECODE (sex, '1', 60 * 12, 55 * 12) < Aa.age AND aa.a0000 = a01.a0000 ");
				ResultSet rs1 = null;
				ResultSet rs2 = null;
				ResultSet rs3 = null;
				try {
					rs1 = sess.connection().prepareStatement("40288103556cc97701556d629135000f".equals(userid)?sbbirth2.toString():sbbirth2.toString()+personViewSQL).executeQuery();
					rs2 = sess.connection().prepareStatement("40288103556cc97701556d629135000f".equals(userid)?sbpro2.toString():sbpro2.toString()+personViewSQL).executeQuery();
					rs3 = sess.connection().prepareStatement("40288103556cc97701556d629135000f".equals(userid)?sbretire2.toString():sbretire2.toString()+personViewSQL).executeQuery();
					if(rs1 != null && rs1.next()){
						map.put("birthday", rs1.getString(1));
					}
					if(rs2 != null && rs2.next()){
						map.put("probation", rs2.getString(1));
					}
					if(rs3 != null && rs3.next()){
						map.put("retire", rs3.getString(1));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else if(DBType.MYSQL == DBUtil.getDBType()){
				//在A32表中无数据的情况
				//生日到期人数提醒
				StringBuffer sbbirth1 = new StringBuffer();
				sbbirth1.append("SELECT count(1) FROM (SELECT CASE WHEN Days < 0 THEN date_add(Birthday, INTERVAL 12 MONTH) "
						+ "ELSE date_format(Birthday, '%Y-%m-%d') END AS Birthday,A0000 FROM (SELECT date_format(concat(date_format(now(), '%Y'),"
						+ "Substring(newA0107, 5)),'%Y-%m-%d') Birthday,TIMESTAMPDIFF(DAY,date_format(now(), '%Y-%m-%d'),"
						+ "date_format(concat(date_format(now(), '%Y'),Substring(newA0107, 5)),'%Y-%m-%d')) Days,newA0107,A0000 FROM "
						+ "(SELECT b.A0000,RPAD(b.A0107, 8, '01') newA0107 FROM a01 b WHERE b.a0163 = '1' AND "
						+ "(LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) a ) xx ) Yy,a01 WHERE TIMESTAMPDIFF(DAY,date_format(now(), '%Y-%m-%d'),Yy.Birthday) <= 30 "
						+ "AND yy.a0000 = a01.a0000  ");
				//试用期到期提醒
				StringBuffer sbpro1 = new StringBuffer();
				sbpro1.append("SELECT count(1) FROM(SELECT date_format(t.newA0288, '%Y-%m-%d') Workday,"
						+ "date_add(date_format(t.newA0288, '%Y-%m-%d'),INTERVAL 12 MONTH) EndDay,t.a0000 "
						+ "FROM (SELECT b.A0000,RPAD(b.A0288, 8, '01') newA0288 FROM a01 b WHERE b.a0163 = '1' "
						+ "AND b.A0221 IN ('1A98','1B98','1C98','27','911','C98') AND (LENGTH(b.a0288) = 6 OR LENGTH(b.a0288) = 8)) t) Aa,a01 "
						+ "WHERE((TIMESTAMPDIFF(DAY,date_format(now(), '%Y-%m-%d'),Aa.EndDay) <= 30 AND "
						+ "TIMESTAMPDIFF(DAY,date_format(now(), '%Y-%m-%d'),Aa.EndDay) >= 0) OR "
						+ "TIMESTAMPDIFF(YEAR,Aa.Workday,date_format(now(), '%Y-%m-%d')) >= 1) AND Aa.a0000 = a01.A0000 ");

				//已超过退休时间人员
				StringBuffer sbretire1 = new StringBuffer();
				sbretire1.append("SELECT count(1) FROM (SELECT date_format(t.newA0107, '%Y-%m-%d') Birthday,date_format(now(), '%Y-%m-%d') "
							+ "Today,TIMESTAMPDIFF(MONTH,date_format(t.newA0107, '%Y-%m-%d'),date_format(now(), '%Y-%m-%d')) age,t.a0104 sex,t.a0000 "
							+ "FROM (SELECT b.A0000,b.A0104,RPAD(b.A0107, 8, '01') newA0107 FROM a01 b WHERE b.a0163 = '1' AND "
							+ "(LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) t) Aa,a01 WHERE CASE WHEN sex = '1' THEN 60 * 12 WHEN sex != '1' "
							+ "THEN 55 * 12 END < Aa.age AND aa.a0000 = a01.a0000  ");
				
				ResultSet rs1 = null;
				ResultSet rs2 = null;
				ResultSet rs3 = null;
				try {
					rs1 = sess.connection().prepareStatement("40288103556cc97701556d629135000f".equals(userid)?sbbirth1.toString():sbbirth1.toString()+personViewSQL).executeQuery();
					rs2 = sess.connection().prepareStatement("40288103556cc97701556d629135000f".equals(userid)?sbpro1.toString():sbpro1.toString()+personViewSQL).executeQuery();
					rs3 = sess.connection().prepareStatement("40288103556cc97701556d629135000f".equals(userid)?sbretire1.toString():sbretire1.toString()+personViewSQL).executeQuery();
					if(rs1 != null && rs1.next()){
						map.put("birthday", rs1.getString(1));
					}
					if(rs2 != null && rs2.next()){
						map.put("probation", rs2.getString(1));
					}
					if(rs3 != null && rs3.next()){
						map.put("retire", rs3.getString(1));
					}
				} catch (SQLException e) {	
					e.printStackTrace();
				}
			}
		}
		
		//待转入
		String sqlIn = "";
		if(DBType.ORACLE == DBUtil.getDBType()){
			sqlIn = "SELECT COUNT(1) FROM PEOPLE_TRANSFER WHERE LOGINID = '"+SysManagerUtils.getUserId()+"' "
					+ "AND OVERDAY >= (SELECT TO_CHAR(SYSDATE,'YYYYMMDD') FROM DUAL)";
		}
		if(DBType.MYSQL == DBUtil.getDBType()){
			sqlIn = "SELECT COUNT(1) FROM PEOPLE_TRANSFER WHERE LOGINID = '"+SysManagerUtils.getUserId()+"' "
					+ "AND OVERDAY >= (select date_format(now(),'%Y%m%d'))";
		}
		
		String in = sess.createSQLQuery(sqlIn).uniqueResult().toString();
		map.put("getIn", in);
		
		//待转出
		String sqlOut = "";
		if(DBType.ORACLE == DBUtil.getDBType()){
			sqlOut = "SELECT COUNT(1) FROM PEOPLE_TRANSFER WHERE USEID = '"+SysManagerUtils.getUserId()+"' "
					+ "AND OVERDAY >= (SELECT TO_CHAR(SYSDATE,'YYYYMMDD') FROM DUAL)";
		}
		if(DBType.MYSQL == DBUtil.getDBType()){
			sqlOut = "SELECT COUNT(1) FROM PEOPLE_TRANSFER WHERE USEID = '"+SysManagerUtils.getUserId()+"' "
					+ "AND OVERDAY >= (select date_format(now(),'%Y%m%d'))";
		}
		
		String out = sess.createSQLQuery(sqlOut).uniqueResult().toString();
		map.put("getOut", out);
		
		//退回人员
		String sqlBack = "";
		if(DBType.ORACLE == DBUtil.getDBType()){
			sqlBack = "SELECT COUNT(1) FROM PEOPLE_TRANSFER WHERE USEID = '"+SysManagerUtils.getUserId()+"' "
					+ "AND OVERDAY < (SELECT TO_CHAR(SYSDATE,'YYYYMMDD') FROM DUAL)";
		}
		if(DBType.MYSQL == DBUtil.getDBType()){
			sqlBack = "SELECT COUNT(1) FROM PEOPLE_TRANSFER WHERE USEID = '"+SysManagerUtils.getUserId()+"' "
					+ "AND OVERDAY < (select date_format(now(),'%Y%m%d'))";
		}
		String back = sess.createSQLQuery(sqlBack).uniqueResult().toString();
		map.put("back", back);
		
		this.doSuccess(request, map);
		return this.ajaxResponse(request, response);
	}
	
	/*public ActionForward loadno(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String newPage = request.getParameter("newpage");
		
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
		
		JSONArray listjson = null;
		//生日提醒
		if(list.size()>0){
			if(DBType.ORACLE == DBUtil.getDBType()){
				StringBuffer sbbirth1 = new  StringBuffer();
				sbbirth1.append(" Select a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                       ")
				  .append("   From (Select Case                                                  ")
				  .append("                  When Days < 0 Then                                  ")
				  .append("                   Add_Months(Birthday, 12)                           ")
				  .append("                  Else                                                ")
				  .append("                   Birthday                                           ")
				  .append("                End As Birthday,                                      ")
				  .append("                A0000                                                 ")
				  .append("           From (Select isdate3(To_Char(Sysdate, 'yyyy') ||           ")
				  .append("                                Substr(A0107, 5)) Birthday,           ")
				  .append("                        isdate3(To_Char(Sysdate, 'yyyy') ||           ")
				  .append("                                Substr(A0107, 5)) - Trunc(Sysdate) Days,    ")
				  .append("                        isdate3(A0107) newDdays,                        ")
				  .append("                        A0000                                         ")
				  .append("                   From A01 a                                         ")
				  .append("                  Where Length(a.A0107) = 8) b WHERE b.NEWDDAYS <> TRUNC (SYSDATE)) Yy, ")
				  .append("        a01,a32                                                       ")
				  .append("  Where Yy.Birthday - a32.birthday <= Trunc(Sysdate)                            ")
				  .append("    and a01.a0000 = yy.a0000 and a32.userid = '"+userid+"' and a01.a0163 = '1'  ");
				//this.pageQuery(sbbirth1.toString()+personViewSQL,"SQL", start, limit);
				listjson = createJson(newPage,sbbirth1.toString()+personViewSQL,"oracle");
			}else if(DBType.MYSQL == DBUtil.getDBType()){
				StringBuffer sbbirth1 = new  StringBuffer();
				sbbirth1.append("Select a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                         ")
				.append("  From (Select Case                                                          ")
				.append("                 When Days < 0 Then                                          ")
				.append("                  date_add(Birthday, interval 12 month)                      ")
				.append("                 Else                                                        ")
				.append("                  date_format(Birthday, '%Y-%m-%d')                          ")
				.append("               End As Birthday,                                              ")
				.append("               A0000                                                         ")
				.append("          From (Select date_format(concat(date_format(now(), '%Y'),          ")
				.append("                                          Substring(A0107, 5)),              ")
				.append("                                   '%Y-%m-%d') Birthday,                     ")
				.append("                       TIMESTAMPDIFF(day,                                    ")
				.append("                                     date_format(now(), '%Y-%m-%d'),         ")
				.append("                                     date_format(concat(date_format(now(),   ")
				.append("                                                                    '%Y'),   ")
				.append("                                                        Substring(A0107, 5)),")
				.append("                                                 '%Y-%m-%d')) Days,          ")
				.append("                                                                             ")
				.append("                       A0107,                                                ")
				.append("                       A0000                                                 ")
				.append("                  From A01 a                                                 ")
				.append("                 where Length(a.A0107) = 8) xx) Yy,                          ")
			    .append("       a32,                                                                  ")
				.append("       a01                                                                   ")
				.append(" Where TIMESTAMPDIFF(day, date_format(now(), '%Y-%m-%d'), Yy.Birthday) <=    ")
				.append("       a32.birthday                                                          ")
				.append("   and a32.userid = '"+userid+"'                                             ")
				.append("   and yy.a0000 = a01.a0000                                                  ")
				.append("   and a01.a0163 = '1'                                                      ");
				//this.pageQuery(sbbirth1.toString()+personViewSQL,"SQL", start, limit);
				listjson = createJson(newPage,sbbirth1.toString()+personViewSQL,"mysql");
			}
		}else{
			if(DBType.ORACLE == DBUtil.getDBType()){
				StringBuffer sbbirth2 = new  StringBuffer();
				sbbirth2.append(" Select a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                       ")
				  .append("   From (Select Case                                                  ")
				  .append("                  When Days < 0 Then                                  ")
				  .append("                   Add_Months(Birthday, 12)                           ")
				  .append("                  Else                                                ")
				  .append("                   Birthday                                           ")
				  .append("                End As Birthday,                                      ")
				  .append("                A0000                                                 ")
				  .append("           From (Select isdate3(To_Char(Sysdate, 'yyyy') ||           ")
				  .append("                                Substr(A0107, 5)) Birthday,           ")
				  .append("                        isdate3(To_Char(Sysdate, 'yyyy') ||           ")
				  .append("                                Substr(A0107, 5)) - Trunc(Sysdate) Days,    ")
				  .append("                        isdate3(A0107) newDdays,                        ")
				  .append("                        A0000                                         ")
				  .append("                   From A01 a                                         ")
				  .append("                  Where Length(a.A0107) = 8) b WHERE b.NEWDDAYS <> TRUNC (SYSDATE)) Yy, ")
				  .append("        a01                                                           ")
				  .append("  Where Yy.Birthday - 30 <= Trunc(Sysdate)                            ")
				  .append("    and a01.a0000 = yy.a0000 and a01.a0163 = '1'                     ");
				//this.pageQuery(sbbirth2.toString()+personViewSQL,"SQL", start, limit);
				listjson = createJson(newPage,sbbirth2.toString()+personViewSQL,"oracle");
			}else if(DBType.MYSQL == DBUtil.getDBType()){
				StringBuffer sbbirth2 = new  StringBuffer();
				sbbirth2.append("Select a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                         ")
				.append("  From (Select Case                                                          ")
				.append("                 When Days < 0 Then                                          ")
				.append("                  date_add(Birthday, interval 12 month)                      ")
				.append("                 Else                                                        ")
				.append("                  date_format(Birthday, '%Y-%m-%d')                          ")
				.append("               End As Birthday,                                              ")
				.append("               A0000                                                         ")
				.append("          From (Select date_format(concat(date_format(now(), '%Y'),          ")
				.append("                                          Substring(A0107, 5)),              ")
				.append("                                   '%Y-%m-%d') Birthday,                     ")
				.append("                       TIMESTAMPDIFF(day,                                    ")
				.append("                                     date_format(now(), '%Y-%m-%d'),         ")
				.append("                                     date_format(concat(date_format(now(),   ")
				.append("                                                                    '%Y'),   ")
				.append("                                                        Substring(A0107, 5)),")
				.append("                                                 '%Y-%m-%d')) Days,          ")
				.append("                                                                             ")
				.append("                       A0107,                                                ")
				.append("                       A0000                                                 ")
				.append("                  From A01 a                                                 ")
				.append("                 where Length(a.A0107) = 8) xx) Yy,                          ")
				.append("       a32,                                                                  ")
				.append("       a01                                                                   ")
				.append(" Where TIMESTAMPDIFF(day, date_format(now(), '%Y-%m-%d'), Yy.Birthday) <=    ")
				.append("       30                                                          ")
				.append("   and yy.a0000 = a01.a0000                                                  ")
				.append("   and a01.a0163 = '1'                                                      ");
				//this.pageQuery(sbbirth2.toString()+personViewSQL,"SQL", start, limit);
				listjson = createJson(newPage,sbbirth2.toString()+personViewSQL,"mysql");
			}
			
		}
		
		this.doSuccess(request, listjson);
		return this.ajaxResponse(request, response);
	}*/
	
	private JSONArray createJson(String page,String sql,String type){
		List<Map<String, String>> l = new ArrayList<Map<String,String>>();
		HBSession sess = HBUtil.getHBSession();
		
		int pagesize = 10;
		int pages=Integer.valueOf(page);
		int start=(pages-1)*pagesize;
		int end=pages*pagesize;
		
		String sizesql="select count(*) from ("+sql+")";
		String size=sess.createSQLQuery(sizesql).uniqueResult().toString();
		Map<String, String> sizeMap = new java.util.HashMap<String, String>();
		sizeMap.put("size", size);
		
		
		String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();	    
		String sqlA32 = "from A32 where userid='"+userid+"'";
		List li = sess.createQuery(sqlA32).list();
		if(li.size()>0){
		   A32 a32 = (A32) li.get(0);
		   sizeMap.put("birthdayCount", String.valueOf(a32.getBirthday()));
		}else{
			sizeMap.put("birthdayCount", "30");
		}
		
		String fysql = "";
		if("oracle".equals(type)){
			fysql="select tt.* from (select t.*,rownum rn from ("+sql+") t ) tt where rn>"+start+" and rn<="+end;
		}
		if("mysql".equals(type)){
			fysql= sql+ " limit " + start +","+pagesize;
		}
		
		List<Object[]> list = sess.createSQLQuery(fysql).list();
		int i = 1;
		if(list!=null && list.size()>0){
			for(Object[] obj : list){
				Map<String, String> values = new java.util.HashMap<String, String>();
				
				values.put("NUMBER", String.valueOf(i));
				values.put("NAME", (obj[0]!=null)?(obj[0].toString()):(null));
				values.put("SEX", (obj[1]!=null)?(("1".equals(obj[1].toString()))?("男"):("女")):(null));
				values.put("BIRTH", (obj[2]!=null)?(obj[2].toString()):(null));
				values.put("WORK", (obj[3]!=null)?(obj[3].toString()):(null));
				
				l.add(values);
				i++;
			}
		}else{
			sizeMap.put("nobody", "nobody");
		}
		l.add(sizeMap);
		JSONArray array = JSONArray.fromObject(l);
		return array;
		//throw new RuntimeException("未查询出近日过生日的人员！");
	}
	
	
	public ActionForward checkid(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String a0000 = request.getParameter("a0000");
		String loginName = request.getParameter("loginName");
		HBSession sess = HBUtil.getHBSession();
		
		Map<String, String> map = new HashMap<String, String>();
		
		if(loginName != null && !"".equals(loginName)){
			String sql = "SELECT a.USERID,a.USERNAME FROM SMT_USER a WHERE a.LOGINNAME = '"+loginName+"' AND a.USEFUL = '1'";
			try {
				Object[] obj = (Object[]) sess.createSQLQuery(sql).uniqueResult();
				if(obj != null){
					String userid = obj[0].toString();
					if(SysManagerUtils.getUserId().equals(userid)){
						map.put("error", "不能选本用户！");
						this.doSuccess(request, map);
					}
					String userName = obj[1].toString();//用户名字
					map.put("userName", userName);
				}else{
					map.put("error", "该用户不存在！");
				}
			} catch (Exception e) {
				map.put("wrong", "查询用户出错，请联系管理员！");
			}
		}
		
		this.doSuccess(request, map);
		return this.ajaxResponse(request, response);
	}
}
