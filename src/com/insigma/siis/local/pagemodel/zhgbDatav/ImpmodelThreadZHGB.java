package com.insigma.siis.local.pagemodel.zhgbDatav;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.siis.local.business.entity.Datarecrejlog;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.ImpModLogThread;

public class ImpmodelThreadZHGB implements Runnable {
	
	private Imprecord  imp;
	private CurrentUser user;
	private UserVO userVo;
	private String impno;
    public ImpmodelThreadZHGB(Imprecord  imp,String impno, CurrentUser user,UserVO userVo) {
        this.imp = imp;
        this.user = user;
        this.userVo = userVo;
        this.impno = impno;
    }

	@Override
	public void run() {
		HBSession sess = HBUtil.getHBSession();
		String impdeptid = imp.getImpdeptid();
		String imptype = imp.getImptype();
		String ftype = imp.getFiletype();
		String imprecordid = imp.getImprecordid();
		String imptemptable = imp.getImptemptable().toUpperCase();
		String A0165 = imp.getA0165();
		Long sortid = 1L;
		String optype = "1";						//日志类型 1、接收
		String a0165sql = "1=1";
		try {
			sess.beginTransaction();
			imp.setImpstutas("4");
			sess.update(imp);
			sess.flush();
			CommonQueryBS.systemOut("index-start---------------"+ DateUtil.getTime());
			new Thread(new ImpModLogThread("index", "创建索引", sortid++, optype, imprecordid, false)).start();
			if(imp.getFiletype().equalsIgnoreCase("7z")||imp.getFiletype().equalsIgnoreCase("xls")||imp.getFiletype().equalsIgnoreCase("xlsx")||imp.getFiletype().equalsIgnoreCase("zip")){
				checkIndexZip(imptemptable);
			}else{
				checkIndex(imptemptable);
			}
			CommonQueryBS.systemOut("index-end---------------"+ DateUtil.getTime());
			String A0000Sql = "";
			//oracle    pywu 20170609核对字段是否对应数据库
				String orgs = "select 1 from a02 a where a.a0000=a0000 and a.a0201b like '"+impdeptid+"%'";
				String borgs = "select d.b0111 from b01 d where d.b0111 like '"+impdeptid+"%' ";
				
				
				//处理 不可覆盖的管理人员类别人员。
				if(A0165!=null && !A0165.equals("")){
					a0165sql = "a0165 >'"+A0165+"'";
					
					new Thread(new ImpModLogThread(imptemptable+"A01", "数据比较", sortid++, optype, imprecordid, false)).start();
					//String a01sql = "create table a"+imptemptable+"a01  as  select t.a0000 a0000t,k.a0000 from a01"+imptemptable+" t, a01 k where t.a0184=k.a0184 and k.a0165 <='"+A0165+"' ";
					String a01sql = "create table a"+imptemptable+"a01  as  select t.a0000 a0000t from a01"+imptemptable+" t where t.a0165 <='"+A0165+"' ";
					sess.createSQLQuery(a01sql).executeUpdate();
					
					if(!(imp.getFiletype().equalsIgnoreCase("7z")||imp.getFiletype().equalsIgnoreCase("xls")||imp.getFiletype().equalsIgnoreCase("xlsx")||imp.getFiletype().equalsIgnoreCase("zip"))){
						sess.createSQLQuery("delete from a11"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
						sess.createSQLQuery("delete from a29"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
						sess.createSQLQuery("delete from a30"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
						sess.createSQLQuery("delete from a31"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
						sess.createSQLQuery("delete from a37"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
						sess.createSQLQuery("delete from a41"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
						sess.createSQLQuery("delete from a53"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
						sess.createSQLQuery("delete from a61"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
						sess.createSQLQuery("delete from a60"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
						sess.createSQLQuery("delete from a62"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
						sess.createSQLQuery("delete from a63"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
						sess.createSQLQuery("delete from a64"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
						sess.createSQLQuery("delete from a68"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
						sess.createSQLQuery("delete from a69"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
						sess.createSQLQuery("delete from a71"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
					}
					sess.createSQLQuery("delete from a01"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
					sess.createSQLQuery("delete from a02"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
					sess.createSQLQuery("delete from a06"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
					sess.createSQLQuery("delete from a08"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
					sess.createSQLQuery("delete from a05"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
					sess.createSQLQuery("delete from a14"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
					sess.createSQLQuery("delete from a15"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
					sess.createSQLQuery("delete from a36"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
					sess.createSQLQuery("delete from a57"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
					sess.createSQLQuery("delete from a99Z1"+imptemptable+" where a0000 in (select a0000t from a"+imptemptable+"a01)").executeUpdate();
				}
				
				/*if(!imptype.equals("4")){
					A0000Sql = "SELECT a0000 FROM a01 WHERE "+a0165sql+" AND a01.a0000 IN (SELECT a02.a0000 FROM a02 WHERE a02.A0201B IN (SELECT cu.b0111 FROM "
							+ "competence_userdept cu WHERE	cu.userid = '"+userVo.getId()+"') AND a0281 = 'true' "
							+ "AND a02.a0201b LIKE '"+impdeptid+"%')";
				} else if("4".equals(imptype)){
					if(A0165!=null && !A0165.equals("")){
						a0165sql = " a." + a0165sql;
					}
					A0000Sql = "select a.a0000 from A01 a,a01"+imptemptable+" t WHERE "+a0165sql+" and (t.a0101 = a.A0101 and t.a0184 = a.A0184 and t.A0184 is not null) or t.a0000 = a.A0000";
				}*/
				if(!imptype.equals("4")){
					//A0000Sql = "SELECT a0000 FROM a01 WHERE "+a0165sql+" AND CONCAT (a0000, '') IN (SELECT t.a0000 from(SELECT a02.a0000,SUBSTR(a02.a0201b,1,"+impdeptid.length()+") as str FROM a02) t WHERE t.str = '"+impdeptid+"') ";
					//SELECT a0000 FROM a01 WHERE CONCAT (a0000, '') IN (SELECT t.a0000 from(SELECT a02.a0000,SUBSTR(a02.a0201b,1,"+impdeptid.length()+") as str FROM a02) t WHERE t.str = '"+impdeptid+"')
					String aa01sql = "create table aa"+imptemptable+"a01  as  SELECT a0000 FROM a01 WHERE "+a0165sql+" AND a01.a0000 IN (SELECT a02.a0000 FROM a02 "
							+ "WHERE a02.A0201B IN (SELECT cu.b0111 FROM competence_userdept cu WHERE	cu.userid = '"+userVo.getId()+"') AND a0281 = 'true' "
							+ "AND a02.a0201b LIKE '"+impdeptid+"%') ";
					sess.createSQLQuery(aa01sql).executeUpdate();
				}else if("4".equals(imptype)){
					if(A0165!=null && !A0165.equals("")){
						a0165sql = " a." + a0165sql;
					}
					String aa01sql = "create table aa"+imptemptable+"a01  as  select a.a0000 from A01 a,a01"+imptemptable+" t WHERE "+a0165sql+" AND "
							+ "(t.a0101 = a.A0101 and t.a0184 = a.A0184 and t.A0184 is not null and t.A0184 <> '') or t.a0000 = a.A0000";
					sess.createSQLQuery(aa01sql).executeUpdate();
				}
				A0000Sql = "SELECT X.a0000 FROM AA" + imptemptable + "A01 X";
				
				//a06 //delete 
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				new Thread(new ImpModLogThread("A06", "接收A06数据", sortid++, optype, imprecordid, false)).start();
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(" delete from a06 where A0000 in ("+A0000Sql+")").executeUpdate();
				/*}*/
				//insert
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery(" MERGE INTO a06 a USING a06"+imptemptable+" t ON (a.a0600 = t.a0600)  WHEN MATCHED THEN UPDATE " +
						"SET a.A0000=t.A0000,a.A0601=t.A0601,a.A0602=t.A0602,a.A0604=t.A0604,a.A0607=t.A0607," +
						"a.A0611=t.A0611,a.A0614=t.A0614,a.SORTID=t.SORTID,a.UPDATED=t.UPDATED,a.A0699=t.A0699 " +
						" WHEN NOT MATCHED THEN INSERT " +
						"(A0600,A0000,A0601,A0602, A0604, A0607, A0611, A0614, SORTID, UPDATED, A0699)"+
						"VALUES (t.A0600,t.A0000,t.A0601,t.A0602, t.A0604, t.A0607, t.A0611, t.A0614, t.SORTID, t.UPDATED, t.A0699) " +
						"").executeUpdate();
				//a08 //delete 
				new Thread(new ImpModLogThread("A08", "接收A08数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(" delete from a08 where  A0000 in ("+A0000Sql+")").executeUpdate();
				/*}*/
				//insert
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery(" MERGE INTO a08 a USING a08"+imptemptable+" t ON (t.a0800 = a.a0800)  WHEN MATCHED THEN UPDATE " +
						" SET a.A0000=t.A0000,a.A0801A=t.A0801A,a.A0801B=t.A0801B,a.A0804=t.A0804,a.A0807=t.A0807,a.A0811=t.A0811,a.A0814=t.A0814,a.A0824=t.A0824," +
						" a.A0827=t.A0827,a.A0831=t.A0831,a.A0832=t.A0832,a.A0834=t.A0834,a.A0835=t.A0835,a.A0837=t.A0837,a.A0838=t.A0838,a.A0839=t.A0839," +
						" a.A0898=t.A0898,a.A0899=t.A0899,a.A0901A=t.A0901A,a.A0901B=t.A0901B,a.A0904=t.A0904,a.SORTID=t.SORTID,a.UPDATED=t.UPDATED,a.WAGE_USED=t.WAGE_USED" + 
						"  WHEN NOT MATCHED THEN INSERT" +
						"(A0000,A0800,A0801A,A0801B,A0804,A0807,A0811,A0814,A0824,A0827, A0831,A0832,A0834,A0835,A0837,A0838,A0839,A0898," +
						" A0899,A0901A, A0901B,A0904,SORTID,updated,wage_used)"+
						" VALUES (t.A0000,t.A0800,t.A0801A,t.A0801B,t.A0804,t.A0807,t.A0811,t.A0814,t.A0824,t.A0827, t.A0831,t.A0832,t.A0834,t.A0835,t.A0837,t.A0838,t.A0839,t.A0898," +
						" t.A0899,t.A0901A, t.A0901B,t.A0904,t.SORTID,t.updated,t.wage_used ) ").executeUpdate();
				//a11 //delete 
				if(!imp.getFiletype().equalsIgnoreCase("zip")&&!imp.getFiletype().equalsIgnoreCase("xls")){
					new Thread(new ImpModLogThread("A11", "接收A11数据", sortid++, optype, imprecordid, false)).start();
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){
						sess.createSQLQuery(" delete from a11 where  A0000 in ("+A0000Sql+")").executeUpdate();
					}
					//insert
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					sess.createSQLQuery(" MERGE INTO a11 a USING a11"+imptemptable+" t ON (a.a1100 = t.a1100)  WHEN MATCHED THEN UPDATE" +
							" SET a.A1127=t.A1127,a.A1131=t.A1131,a.A1134=t.A1134,a.A1151=t.A1151,a.UPDATED=t.UPDATED,a.A1108=t.A1108,a.A1107C=t.A1107C," +
							" a.A0000=t.A0000,a.A1101=t.A1101,a.A1104=t.A1104,a.A1107=t.A1107," +
							"a.A1107A=t.A1107A,a.A1107B=t.A1107B,a.A1111=t.A1111,a.A1114=t.A1114,a.A1121A=t.A1121A, "
							+ "a.g11025=t.g11025, a.g11024=t.g11024, a.g11023=t.g11023, a.g11022=t.g11022,"
							+ " a.g11021=t.g11021, a.g11020=t.g11020, a.g11003=t.g11003, a.g11006=t.g11006 " +
							" WHEN NOT MATCHED THEN INSERT" +
							"(A0000,A1100,A1101,A1104,A1107,A1107A,a1107b,a1111,a1114,a1121a,a1127,a1131,a1134,a1151,updated,A1108,A1107C "
							+ ",g11025,g11024,g11023,g11022,g11021,g11020,g11003,g11006)"+
							" VALUES (t.A0000,t.A1100,t.A1101,t.A1104,t.A1107,t.A1107A,t.a1107b ,t.a1111 ,t.a1114 ,t.a1121a ,t.a1127 ,t.a1131 ,t.a1134 ,t.a1151 ,t.updated,t.A1108,t.A1107C"
							+ ",t.g11025,t.g11024,t.g11023,t.g11022,t.g11021,t.g11020,t.g11003,t.g11006 )" +
							" ").executeUpdate();
				}
				
				//a14 //delete 
				new Thread(new ImpModLogThread("A14", "接收A14数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(" delete from a14 where  A0000 in ("+A0000Sql+")").executeUpdate();
				/*}*/
				//insert
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery(" MERGE INTO a14 a USING a14"+imptemptable+" t ON (a.a1400 = t.a1400)  WHEN MATCHED THEN UPDATE " +
						" SET a.A0000=t.A0000,a.A1404A=t.A1404A,a.A1404B=t.A1404B,a.A1407=t.A1407,a.A1411A=t.A1411A,a.A1414=t.A1414," +
						" a.A1415=t.A1415,a.A1424=t.A1424,a.A1428=t.A1428,a.SORTID=t.SORTID,a.UPDATED=t.UPDATED " +
						"  WHEN NOT MATCHED THEN INSERT" +
						"(a0000, a1400, a1404a, a1404b, a1407 ,a1411a ,a1414 ,a1415, a1424, a1428,sortid ,updated )"+
						" VALUES (t.a0000, t.a1400, t.a1404a, t.a1404b, t.a1407 ,t.a1411a ,t.a1414 ,t.a1415, t.a1424, t.a1428,t.sortid ,t.updated )" +
						" ").executeUpdate();
				//a15 //delete 
				new Thread(new ImpModLogThread("A15", "接收A15数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(" delete from a15 where  A0000 in ("+A0000Sql+")").executeUpdate();
				/*}*/
				//insert
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery(" MERGE INTO a15 a USING a15"+imptemptable+" t ON (a.a1500 = t.a1500)  WHEN MATCHED THEN UPDATE" +
						" SET a.A0000=t.A0000,a.A1517=t.A1517,a.A1521=t.A1521,a.UPDATED=t.UPDATED,a.A1527=t.A1527" +
						"  WHEN NOT MATCHED THEN INSERT" +
						"(a0000, a1500, a1517,a1521, updated, a1527)"+
						" VALUES (t.a0000, t.a1500, t.a1517,t.a1521, t.updated, t.a1527) ").executeUpdate();
				//a29 //delete 
				if(!imp.getFiletype().equalsIgnoreCase("zip")&&!imp.getFiletype().equalsIgnoreCase("xls")){
					new Thread(new ImpModLogThread("A29", "接收A29数据", sortid++, optype, imprecordid, false)).start();
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){
						sess.createSQLQuery(" delete from a29 where  A0000 in ("+A0000Sql+")").executeUpdate();
					}
					//insert
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					/*sess.createSQLQuery(" MERGE INTO a29 a USING a29"+imptemptable+" t ON (a.a0000 = t.a0000)  WHEN MATCHED THEN UPDATE" +
							" SET a.A2907=t.A2907,a.A2911=t.A2911,a.A2921A=t.A2921A,a.A2941=t.A2941,a.A2944=t.A2944,a.A2947=t.A2947,a.A2949=t.A2949,a.UPDATED=t.UPDATED," +
							" a.A2947A=t.A2947A,a.A2921B=t.A2921B,a.A2947B=t.A2947B" +
							",a.A2921C=t.A2921C,a.A2921D=t.A2921D  " +
							" WHEN NOT MATCHED THEN INSERT" +
							" VALUES (t.a0000, t.a2907 ,t.a2911, t.a2921a ,t.a2941, t.a2944, t.a2947 ,t.a2949, t.updated,t.a2947a,t.A2921B,t.A2947B,t.A2921C,t.A2921D)" +
							" ").executeUpdate();*/
					sess.createSQLQuery(" MERGE INTO a29 a USING a29"+imptemptable+" t ON (a.a0000 = t.a0000)  WHEN MATCHED THEN UPDATE" +
							" SET a.A2907=t.A2907,a.A2911=t.A2911,a.A2921A=t.A2921A,a.A2941=t.A2941,a.A2944=t.A2944,a.A2947=t.A2947,a.A2949=t.A2949,a.UPDATED=t.UPDATED," +
							" a.A2921B=t.A2921B,a.A2947B=t.A2947B" +
							",a.A2921C=t.A2921C,a.A2921D=t.A2921D  " +
							" WHEN NOT MATCHED THEN INSERT" +
							"(a0000, a2907 ,a2911, a2921a ,a2941, a2944, a2949, updated,A2921B,A2947B,A2921C,A2921D,A2947)"+
							" VALUES (t.a0000, t.a2907 ,t.a2911, t.a2921a ,t.a2941, t.a2944, t.a2949, t.updated,t.A2921B,t.A2947B,t.A2921C,t.A2921D,t.A2947)" +
							" ").executeUpdate();
				}
				if(!imp.getFiletype().equalsIgnoreCase("zip")&&!imp.getFiletype().equalsIgnoreCase("xls")){
					//a30 //delete 
					new Thread(new ImpModLogThread("A30", "接收A30数据", sortid++, optype, imprecordid, false)).start();
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){
						sess.createSQLQuery(" delete from a30 where  A0000 in ("+A0000Sql+")").executeUpdate();
					}
					//insert
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					/*sess.createSQLQuery(" MERGE INTO a30 a USING a30"+imptemptable+" t ON (a.a0000 = t.a0000)  WHEN MATCHED THEN UPDATE" +
							" SET a.A3001=t.A3001,a.A3004=t.A3004,a.A3007A=t.A3007A,a.A3034=t.A3034,a.UPDATED=t.UPDATED" +
							" " +
							" WHEN NOT MATCHED THEN INSERT VALUES (t.a0000,t.a3001, t.a3004, t.a3007a ,t.a3034 ,t.updated)" +
							" ").executeUpdate();*/
					sess.createSQLQuery(" MERGE INTO a30 a USING a30"+imptemptable+" t ON (a.a0000 = t.a0000)  WHEN MATCHED THEN UPDATE" +
							" SET a.A3001=t.A3001,a.A3004=t.A3004,a.A3034=t.A3034,a.UPDATED=t.UPDATED,a.A3007A=t.A3007A,a.A3038=t.A3038" +
							" " +
							" WHEN NOT MATCHED THEN INSERT(a0000,a3001, a3004, a3034 ,updated,A3007A,a3038) VALUES (t.a0000,t.a3001, t.a3004, t.a3034 ,t.updated,t.A3007A,t.a3038)" +
							" ").executeUpdate();
				}
				
				//a31 //delete 
				if(!imp.getFiletype().equalsIgnoreCase("zip")&&!imp.getFiletype().equalsIgnoreCase("xls")){
					new Thread(new ImpModLogThread("A31", "接收A31数据", sortid++, optype, imprecordid, false)).start();
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){
						sess.createSQLQuery(" delete from a31 where  A0000 in ("+A0000Sql+")").executeUpdate();
						}
					//insert
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					
					sess.createSQLQuery("MERGE INTO a31 a USING a31"+imptemptable+" t ON (a.a0000 = t.a0000)  WHEN MATCHED THEN UPDATE " +
							" SET a.A3101=t.A3101,a.A3117A=t.A3117A,a.A3137=t.A3137," +
							" a.A3138=t.A3138,a.UPDATED=t.UPDATED,a.A3104=t.A3104,a.A3107=t.A3107,a.A3118=t.A3118" +
							"  WHEN NOT MATCHED THEN INSERT " +
							"(a0000,a3101,  a3117a,a3137, a3138 ,updated,a3104,a3107,a3118)"+
							" VALUES (t.a0000,t.a3101,  t.a3117a,t.a3137, t.a3138 ,t.updated,t.a3104,t.a3107,t.a3118)" +
							" ").executeUpdate();
				}
				
				//a36 //delete 
				new Thread(new ImpModLogThread("A36", "接收A36数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(" delete from a36 where  A0000 in ("+A0000Sql+")").executeUpdate();
					/*}*/
				//insert
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery("MERGE INTO a36 a USING a36"+imptemptable+" t ON (a.a3600 = t.a3600)  WHEN MATCHED THEN UPDATE" +
						" SET a.A0000=t.A0000,a.A3601=t.A3601,a.A3604A=t.A3604A,a.A3607=t.A3607,a.A3611=t.A3611,a.A3627=t.A3627,a.SORTID=t.SORTID,a.UPDATED=t.UPDATED,a.A3684=t.A3684 " +
						"  WHEN NOT MATCHED THEN INSERT " +
						"(a0000,a3600,a3601,a3604a,a3607,a3611,a3627 ,sortid ,updated,A3684)"+
						"VALUES (t.a0000,t.a3600, t.a3601, t.a3604a, t.a3607, t.a3611, t.a3627 ,t.sortid ,t.updated,t.A3684) " +
						"").executeUpdate();
				//a37 //delete 
				if(!imp.getFiletype().equalsIgnoreCase("zip")&&!imp.getFiletype().equalsIgnoreCase("xls")){
					new Thread(new ImpModLogThread("A37", "接收A37数据", sortid++, optype, imprecordid, false)).start();
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){
						sess.createSQLQuery(" delete from a37 where  A0000 in ("+A0000Sql+")").executeUpdate();
						}
					//insert
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					sess.createSQLQuery(" MERGE INTO a37 a USING a37"+imptemptable+" t ON (a.A0000 = t.A0000)  WHEN MATCHED THEN UPDATE " +
							" SET a.A3701=t.A3701,a.A3707A=t.A3707A,a.A3707C=t.A3707C,a.A3707E=t.A3707E,a.A3707B=t.A3707B,a.A3708=t.A3708,a.A3711=t.A3711,a.A3714=t.A3714,a.UPDATED=t.UPDATED" +
							" WHEN NOT MATCHED THEN INSERT " +
							"(a0000,a3701,a3707a,a3707c,a3707e,a3707b,a3708,a3711,a3714,updated)"+
							" VALUES (t.a0000,t.a3701,t.a3707a,t.a3707c,t.a3707e,t.a3707b,t.a3708,t.a3711,t.a3714,t.updated)" +
							" ").executeUpdate();
					//a41 //delete 
					new Thread(new ImpModLogThread("A41", "接收A41数据", sortid++, optype, imprecordid, false)).start();
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){
						sess.createSQLQuery(" delete from a41 where  A0000 in ("+A0000Sql+")").executeUpdate();
					}
					//insert
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					sess.createSQLQuery("MERGE INTO a41 a USING a41"+imptemptable+" t ON (a.A4100 = t.A4100)  WHEN MATCHED THEN UPDATE" +
							" SET a.A0000=t.A0000,a.A1100=t.A1100,a.A4101=t.A4101,a.A4102=t.A4102,a.A4103=t.A4103,a.A4104=t.A4104,a.A4105=t.A4105,a.A4199=t.A4199" +
							" WHEN NOT MATCHED THEN INSERT" +
							"(a4100, a0000,a1100 ,a4101, a4102, a4103 ,a4104, a4105 ,a4199)"+
							" VALUES (t.a4100, t.a0000,t.a1100 ,t.a4101, t.a4102, t.a4103 ,t.a4104, t.a4105 ,t.a4199)" +
							" ").executeUpdate();
					//a53 //delete 
					/*new Thread(new ImpModLogThread("A53", "接收A53数据", sortid++, optype, imprecordid, false)).start();
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){
						sess.createSQLQuery(" delete from a53 where  A0000 in ("+A0000Sql+")").executeUpdate();
					}
					//insert
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					sess.createSQLQuery(" MERGE INTO a53 a USING a53"+imptemptable+" t ON (a.A5300 = t.A5300)  WHEN MATCHED THEN UPDATE" +
							" SET a.A0000=t.A0000,a.A5304=t.A5304,a.A5315=t.A5315,a.A5317=t.A5317,a.A5319=t.A5319,a.A5321=t.A5321,a.A5323=t.A5323,a.A5327=t.A5327,a.A5399=t.A5399,a.UPDATED=t.UPDATED" +
							" WHEN NOT MATCHED THEN INSERT" +
							"(a0000,a5300,a5304,a5315,a5317,a5319,a5321,a5323,a5327,a5399,updated)"+
							" VALUES (t.a0000,t.a5300,t.a5304,t.a5315,t.a5317,t.a5319,t.a5321,t.a5323,t.a5327,t.a5399,t.updated)" +
							" ").executeUpdate();*/
				}
				
				//a57 //delete 
				new Thread(new ImpModLogThread("A57", "接收A57数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(" delete from a57 where  A0000 in ("+A0000Sql+")").executeUpdate();
					/*}*/
				//insert
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery(" MERGE INTO a57 a USING a57"+imptemptable+" t ON (a.A0000 = t.A0000)  WHEN MATCHED THEN UPDATE" +
						" SET a.A5714=t.A5714,a.UPDATED=t.UPDATED,a.PHOTODATA=t.PHOTODATA,a.PHOTONAME=t.PHOTONAME,a.PHOTSTYPE=t.PHOTSTYPE,a.PHOTOPATH=t.PHOTOPATH,a.PICSTATUS=t.PICSTATUS" +
						"  WHEN NOT MATCHED THEN INSERT" +
						" (a0000,a5714 ,updated,PHOTODATA,PHOTONAME,PHOTSTYPE,PHOTOPATH,PICSTATUS) VALUES (t.a0000,t.a5714 ,t.updated,t.PHOTODATA,t.PHOTONAME,t.PHOTSTYPE,t.PHOTOPATH,'1')" +
						" ").executeUpdate();
				//a60 //delete
				
				/*if(!imp.getFiletype().equalsIgnoreCase("zip")){
					new Thread(new ImpModLogThread("A60", "接收A60数据", sortid++, optype, imprecordid, false)).start();
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					if (!imptype.equals("4")) {
						sess.createSQLQuery(" delete from a60 where  A0000 in ("+A0000Sql+")").executeUpdate();
					}
					//insert
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					sess.createSQLQuery("MERGE INTO a60 a USING a60"+imptemptable+" t ON (a.A0000 = t.A0000)  WHEN MATCHED THEN UPDATE" +
							" SET a.A6001=t.A6001,a.A6002=t.A6002,a.A6003=t.A6003,a.A6004=t.A6004,a.A6005=t.A6005,a.A6006=t.A6006," +
							"a.A6007=t.A6007,a.A6008=t.A6008,a.A6009=t.A6009,a.A6010=t.A6010,a.A6011=t.A6011,a.A6012=t.A6012,a.A6013=t.A6013," +
							"a.A6014=t.A6014,a.A6015=t.A6015,a.A6016=t.A6016,a.A6017=t.A6017"+
							"  WHEN NOT MATCHED THEN INSERT" +
							" (a0000,a6001,a6002,a6003,a6004,a6005,a6006,a6007,a6008,"+
							"a6009,a6010,a6011,a6012,a6013,a6014,a6015,a6016,a6017)"+
							" VALUES (t.a0000,t.a6001,t.a6002,t.a6003,t.a6004,t.a6005,t.a6006,t.a6007,t.a6008,"+
							"t.a6009,t.a6010,t.a6011,t.a6012,t.a6013,t.a6014,t.a6015,t.a6016,t.a6017)"+
							" ").executeUpdate();
					//a61 //delete
					new Thread(new ImpModLogThread("A61", "接收A61数据", sortid++, optype, imprecordid, false)).start();
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					if (!imptype.equals("4")) {
						sess.createSQLQuery(" delete from a61 where  A0000 in ("+A0000Sql+")").executeUpdate();
					}
					//insert
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					sess.createSQLQuery("MERGE INTO a61 a USING a61"+imptemptable+" t ON (a.A0000 = t.A0000)  WHEN MATCHED THEN UPDATE"+
							" SET a.A2970=t.A2970,a.A2970A=t.A2970A,a.A2970B=t.A2970B,a.A6104=t.A6104,a.A2970C=t.A2970C,"+
							"a.A6107=t.A6107,a.A6108=t.A6108,a.A6109=t.A6109,a.A6110=t.A6110,a.A6111=t.A6111,a.A6112=t.A6112,"+
							"a.A6113=t.A6113,a.A6114=t.A6114,a.A6115=t.A6115,a.A6116=t.A6116"+
							"  WHEN NOT MATCHED THEN INSERT" +
							"(a0000,a2970,a2970a,a2970b,a6104,a2970c,a6107,a6108,a6109,"+
							"a6110,a6111,a6112,a6113,a6114,a6115,a6116)"+
							" VALUES (t.a0000,t.a2970,t.a2970a,t.a2970b,t.a6104,t.a2970c,t.a6107,t.a6108,t.a6109,"+
							"t.a6110,t.a6111,t.a6112,t.a6113,t.a6114,t.a6115,t.a6116)"+
							" ").executeUpdate();
					//a62 //delete
					new Thread(new ImpModLogThread("A62", "接收A62数据", sortid++, optype, imprecordid, false)).start();
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					if (!imptype.equals("4")) {
						sess.createSQLQuery(" delete from a62 where  A0000 in ("+A0000Sql+")").executeUpdate();
					}
					//insert
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					sess.createSQLQuery("MERGE INTO a62 a USING a62"+imptemptable+" t ON (a.A0000 = t.A0000)  WHEN MATCHED THEN UPDATE"+
							" SET a.A2950=t.A2950,a.A6202=t.A6202,a.A6203=t.A6203,a.A6204=t.A6204,a.A6205=t.A6205"+
							"  WHEN NOT MATCHED THEN INSERT" +
							"(a0000,a2950,a6202,a6203,a6204,a6205)"+
							" VALUES (t.a0000,t.a2950,t.a6202,t.a6203,t.a6204,t.a6205)"+
							" ").executeUpdate();
					//a63 //delete
					new Thread(new ImpModLogThread("A63", "接收A63数据", sortid++, optype, imprecordid, false)).start();
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					if (!imptype.equals("4")) {
						sess.createSQLQuery(" delete from a63 where  A0000 in ("+A0000Sql+")").executeUpdate();
					}
					//insert
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					sess.createSQLQuery("MERGE INTO a63 a USING a63"+imptemptable+" t ON (a.A0000 = t.A0000)  WHEN MATCHED THEN UPDATE"+
							" SET a.A2951=t.A2951,a.A6302=t.A6302,a.A6303=t.A6303,a.A6304=t.A6304,a.A6305=t.A6305,a.A6306=t.A6306,"+
							"a.A6307=t.A6307,a.A6308=t.A6308,a.A6309=t.A6309,a.A6310=t.A6310"+
							"  WHEN NOT MATCHED THEN INSERT" +
							"(a0000,a2951,a6302,a6303,a6304,a6305,a6306,a6307,a6308,a6309,a6310)"+
							" VALUES (t.a0000,t.a2951,t.a6302,t.a6303,t.a6304,t.a6305,t.a6306,t.a6307,t.a6308,t.a6309,t.a6310)"+
							" ").executeUpdate();
					//a64 //delete
					new Thread(new ImpModLogThread("A64", "接收A64数据", sortid++, optype, imprecordid, false)).start();
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					if (!imptype.equals("4")) { 
						sess.createSQLQuery(" delete from a64 where  A0000 in ("+A0000Sql+")").executeUpdate();
					}
					//insert
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					sess.createSQLQuery("MERGE INTO a64 a USING a64"+imptemptable+" t ON (a.A6400 = t.A6400)  WHEN MATCHED THEN UPDATE"+
							" SET a.A6401=t.A6401,a.A6402=t.A6402,a.A6403=t.A6403,a.A6404=t.A6404,a.A6405=t.A6405,a.A6406=t.A6406,a.A6407=t.A6407,a.A6408=t.A6408,a.A64TYPE=t.A64TYPE,a.A0000=t.A0000"+
							"  WHEN NOT MATCHED THEN INSERT (a0000,a6401,a6402,a6403,a6404,a6405,a6406,a6407,a6408,A64TYPE,A6400)" +
							" VALUES (t.a0000,t.a6401,t.a6402,t.a6403,t.a6404,t.a6405,t.a6406,t.a6407,t.a6408,t.A64TYPE,t.A6400)"+
							" ").executeUpdate();
					//A68---
					new Thread(new ImpModLogThread("A68", "接收A68数据", sortid++, optype, imprecordid, false)).start();
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					if (!imptype.equals("4")) {
						sess.createSQLQuery(" delete from A68 where  A0000 in ("+A0000Sql+")").executeUpdate();
					}
					//insert
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					sess.createSQLQuery("MERGE INTO a68 a USING a68"+imptemptable+" t ON (a.A6800 = t.A6800)  WHEN MATCHED THEN UPDATE"+
							" SET a.A0000=t.A0000,a.A6801=t.A6801,a.A6802=t.A6802,a.A6803=t.A6803,a.A6804=t.A6804"+
							"  WHEN NOT MATCHED THEN INSERT (a.A0000,a.A6800,a.A6801,a.A6802,a.A6803,a.A6804)" +
							" VALUES (t.A0000,t.A6800,t.A6801,t.A6802,t.A6803,t.A6804)"+
							" ").executeUpdate();
					//A69---
					new Thread(new ImpModLogThread("A69", "接收A69数据", sortid++, optype, imprecordid, false)).start();
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					if (!imptype.equals("4")) { 
						sess.createSQLQuery(" delete from A69 where  A0000 in ("+A0000Sql+")").executeUpdate();
					}
					//insert
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					sess.createSQLQuery("MERGE INTO a69 a USING a69"+imptemptable+" t ON (a.a6900 = t.a6900)  WHEN MATCHED THEN UPDATE"+
							" SET a.A0000=t.A0000,a.A6901=t.A6901,a.A6902=t.A6902,a.A6903=t.A6903,a.A6904=t.A6904"+
							"  WHEN NOT MATCHED THEN INSERT (a.A0000,a.A6900,a.A6901,a.A6902,a.A6903,a.A6904)" +
							" VALUES (t.A0000,t.A6900,t.A6901,t.A6902,t.A6903,t.A6904)"+
							" ").executeUpdate();
					//A71---
					new Thread(new ImpModLogThread("A71", "接收A71数据", sortid++, optype, imprecordid, false)).start();
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					if (!imptype.equals("4")) { 
						sess.createSQLQuery(" delete from A71 where  A0000 in ("+A0000Sql+")").executeUpdate();
					}
					//insert
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					sess.createSQLQuery("MERGE INTO A71 a USING A71"+imptemptable+" t ON (a.A7100 = t.A7100)  WHEN MATCHED THEN UPDATE"+
							" SET a.A0000=t.A0000,a.A7101=t.A7101"+
							"  WHEN NOT MATCHED THEN INSERT (a.A0000,a.A7100,a.A7101)" +
							" VALUES (t.A0000,t.A7100,t.A7101)"+
							" ").executeUpdate();
				}*/
				
				
				//A05---
				new Thread(new ImpModLogThread("A05", "接收A05数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(" delete from A05 where  A0000 in ("+A0000Sql+")").executeUpdate();
				/*}*/
				//insert
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery("MERGE INTO a05 a USING a05"+imptemptable+" t ON (a.A0500 = t.A0500)  WHEN MATCHED THEN UPDATE"+
						" SET a.A0000=t.A0000,a.A0531=t.A0531,a.A0501B=t.A0501B,a.A0504=t.A0504,a.A0511=t.A0511,a.A0517=t.A0517,a.A0524=t.A0524,a.A0525=t.A0525 "+
						"  WHEN NOT MATCHED THEN INSERT (a.A0000,a.A0500,a.A0531,a.A0501B,a.A0504,a.A0511,a.A0517,a.A0524,a.A0525)" +
						" VALUES (t.A0000,t.A0500,t.A0531,t.A0501B,t.A0504,t.A0511,t.A0517,t.A0524,t.A0525)"+
						" ").executeUpdate();
				
				//A99Z1---
				new Thread(new ImpModLogThread("A99Z1", "接收A99Z1数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				/*if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){*/
					sess.createSQLQuery(" delete from A99Z1 where  A0000 in ("+A0000Sql+")").executeUpdate();
				/*}*/
				//insert
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery("MERGE INTO a99Z1 a USING a99Z1"+imptemptable+" t ON (a.A99Z100 = t.A99Z100)  WHEN MATCHED THEN UPDATE"+
						" SET a.A0000=t.A0000,a.A99Z101=t.A99Z101,a.A99Z102=t.A99Z102,a.A99Z103=t.A99Z103,a.A99Z104=t.A99Z104"+
						"  WHEN NOT MATCHED THEN INSERT (a.A0000,a.A99Z100,a.A99Z101,a.A99Z102,a.A99Z103,a.A99Z104)" +
						" VALUES (t.A0000,t.A99Z100,t.A99Z101,t.A99Z102,t.A99Z103,t.A99Z104)"+
						" ").executeUpdate();
				
				
				//a01
				new Thread(new ImpModLogThread("A01", "接收A01数据", sortid++, optype, imprecordid, false)).start();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				//update 统计关系所在单位
				/*if(!imptype.equals("4")){
					//delete 
					sess.createSQLQuery(" delete from a01 where "+a0165sql+" AND a01.a0000 IN (SELECT a02.a0000 FROM a02 WHERE a02.A0201B IN (SELECT cu.b0111 FROM "
							+ "competence_userdept cu WHERE	cu.userid = '"+userVo.getId()+"') AND a0281 = 'true' "
							+ "AND a02.a0201b LIKE '"+impdeptid+"%')").executeUpdate();
				}else if(imptype.equals("4")){*/
					sess.createSQLQuery(" delete from A01 where A0000 in ("+A0000Sql+")").executeUpdate();
					if("4".equals(imptype)){
						sess.createSQLQuery("update B01"+imptemptable+" t set t.psnb0111 =(select b.b0111 from b01 b where b.b0101=t.b0101 and b.b0114=t.b0114) where exists (select 1 from b01 b where b.b0101=t.b0101 and b.b0114=t.b0114)").executeUpdate();
						sess.createSQLQuery("update A01"+imptemptable+" t set t.a0195 =(select b.psnb0111 from b01"+imptemptable+" b where b.b0111=t.a0195) where  exists (select 1 from b01"+imptemptable+" b where b.b0111=t.a0195)").executeUpdate();
					}
				/*}*/
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				//insert
				sess.createSQLQuery(" MERGE INTO a01 a USING a01"+imptemptable+" t ON (a.A0000 = t.A0000)  WHEN MATCHED THEN UPDATE " +
						" SET a.A0192E=t.A0192E,a.A0199=t.A0199,a.A01K01=t.A01K01,a.A01K02=t.A01K02,a.CBDRESULT=t.CBDRESULT,a.CBDW=t.CBDW,"
						+ "a.ISVALID=t.ISVALID,a.NL=t.NL,a.NMZW=t.NMZW,a.NRZW=t.NRZW,a.ORGID=t.ORGID,a.QRZXL=t.QRZXL,a.QRZXLXX=t.QRZXLXX,"
						+ "a.QRZXW=t.QRZXW,a.QRZXWXX=t.QRZXWXX,a.RMLY=t.RMLY,a.STATUS=t.STATUS,a.TBR=t.TBR,a.TBRJG=t.TBRJG,"
						+ "a.USERLOG=t.USERLOG,a.XGR=t.XGR,a.ZZXL=t.ZZXL,a.ZZXLXX=t.ZZXLXX,a.ZZXW=t.ZZXW,a.ZZXWXX=t.ZZXWXX,a.A0155=t.A0155,"
						+ "a.AGE=t.AGE,a.JSNLSJ=t.JSNLSJ,a.RESULTSORTID=t.RESULTSORTID,a.TBSJ=t.TBSJ,a.XGSJ=t.XGSJ,a.SORTID=t.SORTID,"
						+ "a.A0194=t.A0194,a.A0104A=t.A0104A,a.A0111=t.A0111,a.A0114=t.A0114,a.A0117A=t.A0117A,a.A0128B=t.A0128B,"
						+ "a.A0141D=t.A0141D,a.A0144B=t.A0144B,a.A0144C=t.A0144C,a.A0148=t.A0148,a.A0148C=t.A0148C,a.A0149=t.A0149,"
						+ "a.A0151=t.A0151,a.A0153=t.A0153,a.A0157=t.A0157,a.A0158=t.A0158,a.A0159=t.A0159,a.A015A=t.A015A,a.A0161=t.A0161,"
						+ "a.A0162=t.A0162,a.A0180=t.A0180,a.A0191=t.A0191,a.A0192B=t.A0192B,a.A0193=t.A0193,a.A0194U=t.A0194U,"
						+ "a.A0198=t.A0198,a.A0101=t.A0101,a.A0102=t.A0102,a.A0104=t.A0104,a.A0107=t.A0107,"
						+ "a.A0111A=t.A0111A,a.A0114A=t.A0114A,a.A0115A=t.A0115A,a.A0117=t.A0117,a.A0128=t.A0128,a.A0134=t.A0134,"
						+ "a.A0140=t.A0140,a.A0141=t.A0141,a.A0144=t.A0144,a.A3921=t.A3921,a.A3927=t.A3927,a.A0160=t.A0160,a.A0163=t.A0163,"
						+ "a.A0165=t.A0165,a.A0184=t.A0184,a.A0187A=t.A0187A,a.A0192=t.A0192,a.A0192A=t.A0192A,a.A0221=t.A0221,"
						+ "a.A0288=t.A0288,a.A0192D=t.A0192D,a.A0192C=t.A0192C,a.A0196=t.A0196,a.A0197=t.A0197,a.A0195=t.A0195,"
						+ "a.A1701=t.A1701,a.A14Z101=t.A14Z101,a.A15Z101=t.A15Z101,a.A0120=t.A0120,a.A0121=t.A0121,a.A2949=t.A2949,"
						+ "a.A0122=t.A0122,a.a0192f=t.a0192f,a.ZGXL=t.ZGXL,a.ZGXW=t.ZGXW,a.ZGXLXX=t.ZGXLXX,a.ZGXWXX=t.ZGXWXX,a.TCSJSHOW=t.TCSJSHOW,a.TCFSSHOW=t.TCFSSHOW " +
						"  WHEN NOT MATCHED THEN INSERT" +
						" (a.A0192E, a.A0199, a.A01K01, a.A01K02, a.CBDRESULT, a.CBDW, a.ISVALID, a.NL, a.NMZW, a.NRZW, a.ORGID, a.QRZXL, "
						+ "a.QRZXLXX, a.QRZXW, a.QRZXWXX, a.RMLY, a.STATUS, a.TBR, a.TBRJG, a.USERLOG, a.XGR, a.ZZXL, a.ZZXLXX, a.ZZXW, "
						+ "a.ZZXWXX, a.A0155, a.AGE, a.JSNLSJ, a.RESULTSORTID, a.TBSJ, a.XGSJ, a.SORTID, a.A0194, a.A0104A, a.A0111, "
						+ "a.A0114, a.A0117A, a.A0128B, a.A0141D, a.A0144B, a.A0144C, a.A0148, a.A0148C, a.A0149, a.A0151, a.A0153, "
						+ "a.A0157, a.A0158, a.A0159, a.A015A, a.A0161, a.A0162, a.A0180, a.A0191, a.A0192B, a.A0193, a.A0194U, "
						+ "a.A0198, a.A0000, a.A0101, a.A0102, a.A0104, a.A0107, a.A0111A, a.A0114A, a.A0115A, a.A0117, a.A0128, "
						+ "a.A0134, a.A0140, a.A0141, a.A0144, a.A3921, a.A3927, a.A0160, a.A0163, a.A0165, a.A0184, a.A0187A, a.A0192, "
						+ "a.A0192A, a.A0221, a.A0288, a.A0192D, a.A0192C, a.A0196, a.A0197, a.A0195, a.A1701, a.A14Z101, a.A15Z101,"
						+ " a.A0120, a.A0121, a.A2949, a.A0122, a.a0192f, a.ZGXL, a.ZGXW, a.ZGXLXX, a.ZGXWXX,a.TCSJSHOW,a.TCFSSHOW)" +
						" VALUES (t.A0192E, t.A0199, t.A01K01, t.A01K02, t.CBDRESULT, t.CBDW, t.ISVALID, t.NL, t.NMZW, t.NRZW, t.ORGID,"
						+ " t.QRZXL, t.QRZXLXX, t.QRZXW, t.QRZXWXX, t.RMLY, t.STATUS, t.TBR, t.TBRJG, t.USERLOG, t.XGR, t.ZZXL, t.ZZXLXX, "
						+ "t.ZZXW, t.ZZXWXX, t.A0155, t.AGE, t.JSNLSJ, t.RESULTSORTID, t.TBSJ, t.XGSJ, t.SORTID, t.A0194, t.A0104A, t.A0111, "
						+ "t.A0114, t.A0117A, t.A0128B, t.A0141D, t.A0144B, t.A0144C, t.A0148, t.A0148C, t.A0149, t.A0151, t.A0153, t.A0157,"
						+ " t.A0158, t.A0159, t.A015A, t.A0161, t.A0162, t.A0180, t.A0191, t.A0192B, t.A0193, t.A0194U, t.A0198, t.A0000,"
						+ " t.A0101, t.A0102, t.A0104, t.A0107, t.A0111A, t.A0114A, t.A0115A, t.A0117, t.A0128, t.A0134, t.A0140, t.A0141, "
						+ "t.A0144, t.A3921, t.A3927, t.A0160, t.A0163, t.A0165, t.A0184, t.A0187A, t.A0192, t.A0192A, t.A0221, t.A0288,"
						+ " t.A0192D, t.A0192C, t.A0196, t.A0197, t.A0195, t.A1701, t.A14Z101, t.A15Z101, t.A0120, t.A0121, t.A2949, "
						+ "t.A0122,t.a0192f,t.ZGXL,t.ZGXW,t.ZGXLXX,t.ZGXWXX,t.TCSJSHOW,t.TCFSSHOW ) ").executeUpdate();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				
				//b01    //update 更新temp任职机构id 上级id 本级id
				if(!imptype.equals("4")){
					new Thread(new ImpModLogThread("B01", "接收B01数据", sortid++, optype, imprecordid, false)).start();
					//delete 
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					//insert
					CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
					if(impno.equals("2")){
						/*sess.createSQLQuery("MERGE INTO b01 a USING b01"+imptemptable+" t ON (a.b0111 = t.b0111) WHEN NOT MATCHED THEN INSERT" +
								" VALUES (t.b0101,t.b0104,t.b0107,t.b0111,t.b0114,t.b0117,t.b0121,t.b0124,t.b0127,t.b0131,t.b0140,t.b0141,t.b0142,t.b0143,t.b0150,t.b0180,t.b0183,t.b0185,t.b0188," +
								" t.b0189,t.b0190,t.b0191,t.b0191a,t.b0192,t.b0193,t.b0194,t.b01trans,t.b01ip,t.b0227,t.b0232,t.b0233,t.sortid,t.used,t.updated,t.create_user," +
								"t.create_date,t.update_user,t.update_date,t.status,t.b0238,t.b0239,t.b0234) ").executeUpdate();*/
						sess.createSQLQuery("MERGE INTO b01 a USING b01"+imptemptable+" t ON (a.b0111 = t.b0111) WHEN NOT MATCHED THEN INSERT" +
								"(b0101,b0104,b0107,b0111,b0114,b0117,b0121,b0124,b0127,b0131,b0140,b0141,b0142,b0143,b0150,b0180," +
								" b0191,b0194,b01trans,b01ip,b0227,b0232,b0233,sortid,used,updated,create_user," +
								"create_date,update_user,update_date,status,b0238,b0239,b0234)"+
								" VALUES (t.b0101,t.b0104,t.b0107,t.b0111,t.b0114,t.b0117,t.b0121,t.b0124,t.b0127,t.b0131,t.b0140,t.b0141,t.b0142,t.b0143,t.b0150,t.b0180," +
								" t.b0191,t.b0194,t.b01trans,t.b01ip,t.b0227,t.b0232,t.b0233,t.sortid,t.used,t.updated,t.create_user," +
								"t.create_date,t.update_user,t.update_date,t.status,t.b0238,t.b0239,t.b0234) ").executeUpdate();
						
					} else {
						//处理首选机构的机构排序
						String sqlSORT = "select sortid from b01 where b0111 = '"+ imp.getImpdeptid() +"'";
						Object obj = sess.createSQLQuery(sqlSORT).uniqueResult();
						
						sess.createSQLQuery(" delete from b01 where b0111 in ("+borgs+")").executeUpdate();
						sess.createSQLQuery("  MERGE INTO b01 a USING b01"+imptemptable+" t ON (a.b0111 = t.b0111)  WHEN MATCHED THEN UPDATE " +
								"SET a.B0235=t.B0235,a.B0236=t.B0236,a.B0101=t.B0101,a.B0104=t.B0104,a.B0107=t.B0107,a.B0114=t.B0114,a.B0117=t.B0117,"
								+ "a.B0121=t.B0121,a.B0124=t.B0124,a.B0127=t.B0127,a.B0131=t.B0131,a.B0140=t.B0140,a.B0141=t.B0141,a.B0142=t.B0142,"
								+ "a.B0143=t.B0143,a.B0150=t.B0150,a.B0180=t.B0180,a.B0183=t.B0183,a.B0185=t.B0185,a.B0188=t.B0188,a.B0189=t.B0189,"
								+ "a.B0190=t.B0190,a.B0191=t.B0191,a.B0191A=t.B0191A,a.B0192=t.B0192,a.B0193=t.B0193,a.B0194=t.B0194,a.B01TRANS=t.B01TRANS,"
								+ "a.B01IP=t.B01IP,a.B0227=t.B0227,a.B0232=t.B0232,a.B0233=t.B0233,a.SORTID=t.SORTID,a.USED=t.USED,a.UPDATED=t.UPDATED,"
								+ "a.CREATE_USER=t.CREATE_USER,a.CREATE_DATE=t.CREATE_DATE,a.UPDATE_USER=t.UPDATE_USER,a.UPDATE_DATE=t.UPDATE_DATE,"
								+ "a.STATUS=t.STATUS,a.B0238=t.B0238,a.B0239=t.B0239,a.B0234=t.B0234 " +
								"  WHEN NOT MATCHED THEN INSERT" +
								"(a.B0235,a.B0236,a.B0101,a.B0104,a.B0107,a.B0111,a.B0114,a.B0117,a.B0121,a.B0124,a.B0127,a.B0131,a.B0140,a.B0141,a.B0142,a.B0143,"
								+ "a.B0150,a.B0180,a.B0183,a.B0185,a.B0188,a.B0189,a.B0190,a.B0191,a.B0191A,a.B0192,a.B0193,a.B0194,a.B01TRANS,a.B01IP,a.B0227,"
								+ "a.B0232,a.B0233,a.SORTID,a.USED,a.UPDATED,a.CREATE_USER,a.CREATE_DATE,a.UPDATE_USER,a.UPDATE_DATE,a.STATUS,a.B0238,a.B0239,a.B0234)"+
								" VALUES (t.B0235,t.B0236,t.B0101,t.B0104,t.B0107,t.B0111,t.B0114,t.B0117,t.B0121,t.B0124,t.B0127,t.B0131,t.B0140,t.B0141,"
								+ "t.B0142,t.B0143,t.B0150,t.B0180,t.B0183,t.B0185,t.B0188,t.B0189,t.B0190,t.B0191,t.B0191A,t.B0192,t.B0193,t.B0194,"
								+ "t.B01TRANS,t.B01IP,t.B0227,t.B0232,t.B0233,t.SORTID,t.USED,t.UPDATED,t.CREATE_USER,t.CREATE_DATE,t.UPDATE_USER,"
								+ "t.UPDATE_DATE,t.STATUS,t.B0238,t.B0239,t.B0234) ").executeUpdate();
						
						//更新首选机构的机构排序
						if(obj!=null && !"".equals(obj.toString().trim())){
							String updateSORT = "update b01 b set b.SORTID = "+obj.toString()+" where b.b0111 = '"+imp.getImpdeptid()+"'";
							sess.createSQLQuery(updateSORT).executeUpdate();
						}
					}
					
					//导入机构授权
					/*sess.createSQLQuery(" delete from COMPETENCE_USERDEPT where b0111 like '"+impdeptid+"%' and userid='"+user.getId()+"'").executeUpdate();
					Connection conn = sess.connection();
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("select b0111 from b01 where b0111 like '"+impdeptid+"%'");
					PreparedStatement pstmt1 = conn.prepareStatement("insert into COMPETENCE_USERDEPT values(?,?,?,?)");
					int i = 0;
					if (rs != null){
						while (rs.next()) {
							pstmt1.setString(1, UUID.randomUUID().toString().replace("-", ""));
							pstmt1.setString(2, user.getId());
							pstmt1.setString(3, rs.getString(1));
							pstmt1.setString(4, "1");
							pstmt1.addBatch();
							i++;
							if(i%10000 == 0){
								pstmt1.executeBatch();
								pstmt1.clearBatch();
							}
						}
						pstmt1.executeBatch();
						pstmt1.clearBatch();
					}
					rs.close();
					pstmt1.close();
					stmt.close();
					conn.close();
					
					
					if(!"40288103556cc97701556d629135000f".equals(user.getId())){//给system用户设置权限
						sess.createSQLQuery(" delete from COMPETENCE_USERDEPT where b0111 like '"+impdeptid+"%' and userid='40288103556cc97701556d629135000f'").executeUpdate();
						Connection conn2 = sess.connection();
						Statement stmt2 = conn2.createStatement();
						ResultSet rs2 = stmt2.executeQuery("select b0111 from b01 where b0111 like '"+impdeptid+"%'");
						PreparedStatement pstmt2 = conn2.prepareStatement("insert into COMPETENCE_USERDEPT values(?,?,?,?)");
						int j = 0;
						if (rs2 != null){
							while (rs2.next()) {
								pstmt2.setString(1, UUID.randomUUID().toString().replace("-", ""));
								pstmt2.setString(2, "40288103556cc97701556d629135000f");
								pstmt2.setString(3, rs2.getString(1));
								pstmt2.setString(4, "1");
								pstmt2.addBatch();
								j++;
								if(j%10000 == 0){
									pstmt2.executeBatch();
									pstmt2.clearBatch();
								}
							}
							pstmt2.executeBatch();
							pstmt2.clearBatch();
						}
						rs2.close();
						pstmt2.close();
						stmt2.close();
						conn2.close();
					}*/
					
					sess.createSQLQuery(" delete from COMPETENCE_USERDEPT where b0111 like '"+impdeptid+"%' and userid='"+user.getId()+"'").executeUpdate();
					
					Connection conn = sess.connection();
					Statement stmt = conn.createStatement();
					
					
					//查询出所有导入时，待授权的机构
					ResultSet rs = stmt.executeQuery("select b0111 from b01 where b0111 like '"+impdeptid+"%'");
					//查询出所有待授权的管理员用户
					String sql="select distinct c.USERID from COMPETENCE_USERDEPT c left join smt_user u on c.USERID = u.userid where c.b0111 like '"+impdeptid+"%' and u.USERTYPE = '1'";
					List<String> list = sess.createSQLQuery(sql).list();
					
					if(list!=null && list.size() > 0){
						
						for (int k = 0; k < list.size(); k++) {
							sess.createSQLQuery(" delete from COMPETENCE_USERDEPT where b0111 like '"+impdeptid+"%' and userid='"+list.get(k)+"'").executeUpdate();
						}
						
						PreparedStatement pstmt1 = conn.prepareStatement("insert into COMPETENCE_USERDEPT values(?,?,?,?)");
						
						int i = 0;
						if (rs != null){
							while (rs.next()) {
								
								for (int k = 0; k < list.size(); k++) {
									pstmt1.setString(1, UUID.randomUUID().toString().replace("-", ""));
									//pstmt1.setString(2, user.getId());
									pstmt1.setString(2, list.get(k));
									pstmt1.setString(3, rs.getString(1));
									pstmt1.setString(4, "1");
									pstmt1.addBatch();
									i++;
									if(i%10000 == 0){
										pstmt1.executeBatch();
										pstmt1.clearBatch();
									}
								}
								
							}
							pstmt1.executeBatch();
							pstmt1.clearBatch();
						}
						
						rs.close();
						pstmt1.close();
						stmt.close();
						conn.close();
					}/*else{		//空库时需要给当前用户机构授权
						
					}*/
					
					//当前用户机构授权
					//sess.createSQLQuery(" delete from COMPETENCE_USERDEPT where b0111 like '"+impdeptid+"%' and userid='"+user.getId()+"'").executeUpdate();
					
					Connection conn2 = sess.connection();
					Statement stmt2 = conn2.createStatement();
					PreparedStatement pstmt2 = conn2.prepareStatement("insert into COMPETENCE_USERDEPT values(?,?,?,?)");
					//查询出所有导入时，待授权的机构
					ResultSet rs2 = stmt2.executeQuery("select b0111 from b01 where b0111 like '"+impdeptid+"%'");
					
					int i = 0;
					if (rs2 != null){
						while (rs2.next()) {
							pstmt2.setString(1, UUID.randomUUID().toString().replace("-", ""));
							pstmt2.setString(2, user.getId());
							pstmt2.setString(3, rs2.getString(1));
							pstmt2.setString(4, "1");
							pstmt2.addBatch();
							i++;
							if(i%10000 == 0){
								pstmt2.executeBatch();
								pstmt2.clearBatch();
							}
						}
						pstmt2.executeBatch();
						pstmt2.clearBatch();
					}
					rs2.close();
					pstmt2.close();
					stmt2.close();
					conn2.close();
					
					
					
				}
				
				/*if(imp.getFiletype().equalsIgnoreCase("hzb")){
					List colomns = sess.createSQLQuery("select t.COLUMN_NAME from (select COLUMN_NAME From User_Col_Comments a " +
							"Where Table_Name =upper('I_E"+imptemptable+"')) t,(select COLUMN_NAME From User_Col_Comments a " +
							"Where Table_Name =upper('INFO_EXTEND')) d where  d.COLUMN_NAME =t.COLUMN_NAME").list();
					StringBuffer insert_sql = new StringBuffer();            //字段连接 如(a0000,A0200......)
					StringBuffer update_sql = new StringBuffer();             //字段插入值连接(?,?,?)
					StringBuffer insert_sql2 = new StringBuffer();             //
					if(colomns != null && colomns.size()>0){
						for (int j = 0; j < colomns.size(); j++) {
							String column = (String) colomns.get(j);
							insert_sql.append("t.").append(column);
							insert_sql2.append(column);
							if(!column.equalsIgnoreCase("A0000")){
								update_sql.append("a.").append(column).append("=t.").append(column);
							}
							if(j != colomns.size()-1){
								insert_sql2.append(",");
								insert_sql.append(",");
								if(!column.equalsIgnoreCase("A0000")){
									update_sql.append(",");
								}
							}
						}
						if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){
							sess.createSQLQuery(" delete from INFO_EXTEND where exists (select 1 from a02 a where a.a0000=INFO_EXTEND.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
						}
						//insert
						CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
						sess.createSQLQuery(" MERGE INTO INFO_EXTEND a USING  I_E"+imptemptable+"  t ON (a.A0000 = t.A0000)"+(update_sql.toString().equals("")? " " :" WHEN MATCHED THEN UPDATE" +
								" SET " + (update_sql.substring(update_sql.length()-1).equals(",")?update_sql.substring(0, update_sql.length()-1):update_sql))+
								"  WHEN NOT MATCHED THEN INSERT (" +insert_sql2 +")" +
								" VALUES ("+insert_sql+")" +
								" ").executeUpdate();
					}
					
					List colomnsB01 = sess.createSQLQuery("select t.COLUMN_NAME from (select COLUMN_NAME From User_Col_Comments a " +
							"Where Table_Name =upper('B_E"+imptemptable+"')) t,(select COLUMN_NAME From User_Col_Comments a " +
							"Where Table_Name =upper('B01_EXT')) d where  d.COLUMN_NAME =t.COLUMN_NAME").list();
					StringBuffer insert_sqlb01 = new StringBuffer();            //字段连接 如(a0000,A0200......)
					StringBuffer insert_sqlb = new StringBuffer();            //字段连接 如(a0000,A0200......)
					StringBuffer update_sqlb01 = new StringBuffer();             //字段插入值连接(?,?,?)
					if(colomnsB01 != null && colomnsB01.size() > 0){
						for (int j = 0; j < colomnsB01.size(); j++) {
							String column = (String) colomnsB01.get(j);
							insert_sqlb01.append("t.").append(column);
							insert_sqlb.append(column);
							if(!column.equalsIgnoreCase("B0111")){
								update_sqlb01.append("a.").append(column).append("=t.").append(column);
							}
							if(j != colomnsB01.size()-1){
								insert_sqlb01.append(",");
								insert_sqlb.append(",");
								if(!column.equalsIgnoreCase("B0111")){
									update_sqlb01.append(",");
								}
							}
						}
						if(!imptype.equals("4") || ("4".equals(imptype) && imp.getFiletype().equalsIgnoreCase("zip"))){
							//insert
							CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
							if(impno.equals("2")||update_sqlb01.toString().equals("")){
								sess.createSQLQuery("MERGE INTO B01_EXT a USING B_E"+imptemptable+" t ON (a.b0111 = t.b0111) WHEN NOT MATCHED THEN INSERT" +
										" VALUES ("+insert_sqlb01+") ").executeUpdate();
							} else {
								sess.createSQLQuery(" delete from B01_EXT where b0111 in ("+borgs+")").executeUpdate();
								sess.createSQLQuery("  MERGE INTO B01_EXT a USING  B_E"+imptemptable+" t ON (a.b0111 = t.b0111)  WHEN MATCHED THEN UPDATE " +
										"SET " + (update_sqlb01.substring(update_sqlb01.length()-1).equals(",")?update_sqlb01.substring(0, update_sqlb01.length()-1):update_sqlb01) +
										"  WHEN NOT MATCHED THEN INSERT (" +insert_sqlb +")" +
										" VALUES ("+insert_sqlb01+") ").executeUpdate();
							}
						}
					}
				}*/
				//a02 先删除a02数据 ----- ‘现有数据’与‘来源数据’ 中已存在的同样人员（身份证相同）数据  ------ 之后插入临时表数据
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				new Thread(new ImpModLogThread("A02", "接收A02数据", sortid++, optype, imprecordid, false)).start();
				if(!imptype.equals("4")){
					//update 更新temp任职机构id
					//delete 
//					sess.createSQLQuery(" delete from a02 where  A0000 in (select a0000 from a01 where "+a0165sql+" and ((status='3' OR status='2' ) and ORGID like '"+ impdeptid +"%') union select a0000 from a01 where "+a0165sql+" and (status='1' and a0000 in (select a0000 from a02 where A0255='1' and  a0201b like '"+ impdeptid +"%' )))").executeUpdate();
					sess.createSQLQuery(" delete from a02 a2 where a2.A0000 in ("+A0000Sql+")").executeUpdate();
					sess.createSQLQuery(" delete from a02 where a02.A0000 not in (select a0000 from a01)").executeUpdate();
				} else if("4".equals(imptype)){
					sess.createSQLQuery(" delete from a02 a2 where  a2.A0000 in ("+A0000Sql+")").executeUpdate();
					sess.createSQLQuery(" delete from a02 where a02.A0000 not in (select a0000 from a01)").executeUpdate();
					sess.createSQLQuery("update B01"+imptemptable+" t set t.psnb0111 =(select b.b0111 from b01 b where b.b0101=t.b0101 and b.b0114=t.b0114) where exists (select 1 from b01 b where b.b0101=t.b0101 and b.b0114=t.b0114)").executeUpdate();
					sess.createSQLQuery("update A02"+imptemptable+" t set t.a0201b =(select b.psnb0111 from b01"+imptemptable+" b where b.b0111=t.a0201b),t.ERROR_INFO ='1' where  exists (select 1 from b01"+imptemptable+" b where b.b0111=t.a0201b)").executeUpdate();
					sess.createSQLQuery("update A02"+imptemptable+" t set t.a0201b ='-1' where (ERROR_INFO='2' OR a0201b is null)").executeUpdate();
				}/* else {
					sess.createSQLQuery("update B01"+imptemptable+" t set t.psnb0111 =(select b.b0111 from b01 b where b.b0101=t.b0101 and b.b0114=t.b0114) where exists (select 1 from b01 b where b.b0101=t.b0101 and b.b0114=t.b0114)").executeUpdate();
					sess.createSQLQuery("update A02"+imptemptable+" t set t.a0201b =(select b.psnb0111 from b01"+imptemptable+" b where b.b0111=t.a0201b),t.ERROR_INFO ='1' where  exists (select 1 from b01"+imptemptable+" b where b.b0111=t.a0201b)").executeUpdate();
					sess.createSQLQuery("update A02"+imptemptable+" t set t.a0201b ='-1' where (ERROR_INFO='2' OR a0201b is null)").executeUpdate();
				}*/
//				sess.createSQLQuery(" delete from a02 where exists (select 1 from A02_temp k where k.imprecordid='"+ imprecordid + 
//						"' and k.a0200=a02.a0200) ").executeUpdate();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				//insert
				
				sess.createSQLQuery(" MERGE INTO a02 a USING a02"+imptemptable+" t ON (a.a0200 = t.a0200)  WHEN MATCHED THEN UPDATE" +
						" SET a.A0000=t.A0000,a.A0201A=t.A0201A,a.A0201B=t.A0201B,a.A0201D=t.A0201D,a.A0201E=t.A0201E,a.A0215A=t.A0215A,"
						+ "a.A0219=t.A0219,a.A0223=t.A0223,a.A0225=t.A0225,a.A0243=t.A0243,a.A0245=t.A0245,a.A0247=t.A0247,a.A0251B=t.A0251B,"
						+ "a.A0255=t.A0255,a.A0265=t.A0265,a.A0267=t.A0267,a.A0272=t.A0272,a.A0281=t.A0281,a.A0221T=t.A0221T,a.B0238=t.B0238,"
						+ "a.B0239=t.B0239,a.A0221A=t.A0221A,a.WAGE_USED=t.WAGE_USED,a.UPDATED=t.UPDATED,a.A4907=t.A4907,a.A4904=t.A4904,"
						+ "a.A4901=t.A4901,a.A0299=t.A0299,a.A0295=t.A0295,a.A0289=t.A0289,a.A0288=t.A0288,a.A0284=t.A0284,a.A0277=t.A0277,"
						+ "a.A0271=t.A0271,a.A0259=t.A0259,a.A0256C=t.A0256C,a.A0256B=t.A0256B,a.A0256A=t.A0256A,a.A0256=t.A0256,a.A0251=t.A0251,"
						+ "a.A0229=t.A0229,a.A0222=t.A0222,a.A0221W=t.A0221W,a.A0221=t.A0221,a.A0219W=t.A0219W,a.A0216A=t.A0216A,a.A0215B=t.A0215B,"
						+ "a.A0209=t.A0209,a.A0207=t.A0207,a.A0204=t.A0204,a.A0201C=t.A0201C,a.A0201=t.A0201,a.A0279=t.A0279 " +
						"  WHEN NOT MATCHED THEN INSERT" +
						"(a.A0000,a.A0200,a.A0201A,a.A0201B,a.A0201D,a.A0201E,a.A0215A,a.A0219,a.A0223,a.A0225,a.A0243,a.A0245,a.A0247,a.A0251B,"
						+ "a.A0255,a.A0265,a.A0267,a.A0272,a.A0281,a.A0221T,a.B0238,a.B0239,a.A0221A,a.WAGE_USED,a.UPDATED,a.A4907,a.A4904,a.A4901,"
						+ "a.A0299,a.A0295,a.A0289,a.A0288,a.A0284,a.A0277,a.A0271,a.A0259,a.A0256C,a.A0256B,a.A0256A,a.A0256,a.A0251,a.A0229,"
						+ "a.A0222,a.A0221W,a.A0221,a.A0219W,a.A0216A,a.A0215B,a.A0209,a.A0207,a.A0204,a.A0201C,a.A0201,a.A0279)"+
						" VALUES (t.A0000,t.A0200,t.A0201A,t.A0201B,t.A0201D,t.A0201E,t.A0215A,t.A0219,t.A0223,t.A0225,t.A0243,t.A0245,t.A0247,"
						+ "t.A0251B,t.A0255,t.A0265,t.A0267,t.A0272,t.A0281,t.A0221T,t.B0238,t.B0239,t.A0221A,t.WAGE_USED,t.UPDATED,t.A4907,"
						+ "t.A4904,t.A4901,t.A0299,t.A0295,t.A0289,t.A0288,t.A0284,t.A0277,t.A0271,t.A0259,t.A0256C,t.A0256B,t.A0256A,t.A0256,"
						+ "t.A0251,t.A0229,t.A0222,t.A0221W,t.A0221,t.A0219W,t.A0216A,t.A0215B,t.A0209,t.A0207,t.A0204,t.A0201C,t.A0201,t.A0279)" +
						" ").executeUpdate();
			
			
			CommonQueryBS.systemOut("time---------" + DateUtil.getTime()); 
			new Thread(new ImpModLogThread("A01", "更新A01排序字段", sortid++, optype, imprecordid, false)).start();
			//更新A01的排序字段
			if(!imptype.equals("4")){
				if(DBUtil.getDBType().equals(DBType.ORACLE)){
					String tableExt = getNo() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmssS");
					String torgidSql = "create table torgid"+tableExt+"(A0000 VARCHAR2(120),TORGID VARCHAR2(200))";
					sess.createSQLQuery(torgidSql.toUpperCase()).executeUpdate();
					sess.createSQLQuery("insert into torgid"+tableExt+" SELECT W.A0000,W.A0201B FROM (SELECT ROW_NUMBER () OVER (PARTITION BY V.a0000 ORDER BY B01.SORTID DESC) rn,A02.A0000,"
							+ "A02.A0201B FROM (SELECT A01.A0000,MIN (LENGTH(A0201B)) minlength FROM a02,a01 WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0201B LIKE '"+impdeptid+"%'"
							+ "GROUP BY A01.A0000) V,A02,B01 WHERE V.A0000 = A02.A0000 AND B01.B0111 = A02.A0201B AND A02.A0281 = 'true' AND LENGTH (A02.A0201B) "
							+ "= V.minlength) W WHERE W.RN = 1").executeUpdate();
					sess.createSQLQuery("Create index tid" + tableExt + " on torgid" + tableExt + "(A0000)").executeUpdate();
					sess.createSQLQuery("UPDATE a01 SET A01.TORGID = (SELECT t.TORGID FROM torgid"+tableExt+" t WHERE t.a0000 = A01.A0000 ) WHERE EXISTS (SELECT 1 FROM torgid"+tableExt+" t WHERE t.a0000 = A01.A0000 )").executeUpdate();
					sess.createSQLQuery("drop table torgid"+tableExt).executeUpdate();
					
					String torderSql = "create table torder"+tableExt+"(A0000 VARCHAR2(120),TORDER VARCHAR2(8))";
					sess.createSQLQuery(torderSql.toUpperCase()).executeUpdate();
					sess.createSQLQuery("insert into torder"+tableExt+" SELECT a01.a0000,LPAD (MAX(a02.a0225), 5, 0) FROM a02,a01 WHERE a01.a0000 = a02.a0000 "
							+ "AND a02.a0281 = 'true' AND a01.torgid = a02.a0201b AND a01.torgid LIKE '"+impdeptid+"%' GROUP BY a01.a0000").executeUpdate();
					sess.createSQLQuery("Create index tder" + tableExt + " on torder" + tableExt + "(A0000)").executeUpdate();
					sess.createSQLQuery("UPDATE a01 SET A01.torder = (SELECT t.torder FROM torder"+tableExt+" t WHERE t.a0000 = A01.A0000 ) WHERE EXISTS (SELECT 1 FROM torder"+tableExt+" t WHERE t.a0000 = A01.A0000 )").executeUpdate();
					sess.createSQLQuery("drop table torder"+tableExt).executeUpdate();
				}else{
					HBUtil.executeUpdate("UPDATE a01 SET A01.TORGID = GET_TORGID (A01.A0000) WHERE EXISTS (SELECT 1 FROM a02 WHERE A02.a0000 = A01.A0000 AND A02.A0201B LIKE '"+impdeptid+"%')");
					HBUtil.executeUpdate("update a01 set a01.torder= lpad((select max(a0225) from a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a01.torgid=a02.a0201b),5,'0') WHERE A01.TORGID LIKE '"+impdeptid+"%'");
				}
				//HBUtil.executeUpdate("update a01 set a01.torder= lpad((select max(a0225) from a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a01.torgid=a02.a0201b),5,'0') WHERE A01.TORGID LIKE '"+impdeptid+"%'");  
				//HBUtil.executeUpdate("update a01 set a01.torder= lpad((select max(a0225) from a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a01.torgid=a02.a0201b),5,'0') WHERE SUBSTR(A01.TORGID, 1, length('"+impdeptid+"')) = '"+impdeptid+"'");  
				
			}else if("4".equals(imptype)){
				HBUtil.executeUpdate("update a01 set a01.torgid= get_torgid(a0000) where a0000 in (SELECT t.a0000 FROM a01"+imptemptable+" t)");
				HBUtil.executeUpdate("update a01 set a01.torder= lpad((select max(a0225) from a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a01.torgid=a02.a0201b),5,'0') where a01.a0000 in (SELECT t.a0000 FROM a01"+imptemptable+" t)");
			}
//			String tables1[] = {"A01", "A02","A06","A08", "A11", "A14", "A15", "A29","A30", 
//					"A31","A36","A37","A41", "A53","A57", "B01", "B01_EXT", "INFO_EXTEND"};
			imp.setImpstutas("2");
			sess.update(imp);
			sess.getTransaction().commit();
			new Thread(new ImpModLogThread("PHOTO", "迁移图片", sortid++, optype, imprecordid, false)).start();
			String photo_path = "";
			String fName = "";
			/*if(imp.getFiletype().equalsIgnoreCase("zip")){
				String p = AppConfig.HZB_PATH + "/temp/upload/" +imprecordid +"/" + imprecordid;
				File file = new File(p);
				File[] subs = file.listFiles();
				File f = subs[0];
				fName = f.getName();
				photo_path = p + "/" + fName + "/Photos/";
			}else{*/
				photo_path = AppConfig.HZB_PATH + "/temp/upload/unzip/"+imprecordid +"/Photos/";
			/*}*/
			File photos = new File(photo_path);
			
			if(photos.exists() && photos.isDirectory()){
				PhotosUtil.moveIMPCmd(imprecordid,photo_path);
				PhotosUtil.moveIMPOtherCmd(imprecordid,photo_path);
				PhotosUtil.removDirImpCmd(imprecordid,fName);
				new Thread(new ImpModLogThread("DONE", "迁移完成", sortid++, optype, imprecordid, true)).start();
				sess.flush();
			} else {
				new Thread(new ImpModLogThread("DONE", "完成:导入包未发现图片或临时图片被删除", sortid++, optype, imprecordid, true)).start();
				sess.flush();
			}

			if((imp.getFiletype().equalsIgnoreCase("7z")||imp.getFiletype().equalsIgnoreCase("xls")||imp.getFiletype().equalsIgnoreCase("xlsx")||imp.getFiletype().equalsIgnoreCase("zip"))&&!"4".equals(imptype)){
				String tables1[] = {"A01", "A02", "A05", "A06", "A08", "A14", "A15", "A36", "A57", "B01", "A99Z1","A30"};
//				new Thread(new ImpModLogThread("ALL", "删除临时数据", sortid++, optype, imprecordid, false)).start();
				for (int i = 0; i < tables1.length; i++) {
					sess.createSQLQuery(" drop table " + tables1[i] + ""+imptemptable+"").executeUpdate();
					
				}
			}else{
				String tables1[] = {"A01", "A02","A06","A08", "A11", "A14", "A15", "A29","A30", 
							"A31","A36","A37","A41", "A53","A57","A60","A61","A62","A63","A64", "B01", "B_E", "I_E"
							,"A05", "A68", "A69", "A71", "A99Z1", "A65"};
//				new Thread(new ImpModLogThread("ALL", "删除临时数据", sortid++, optype, imprecordid, false)).start();
				for (int i = 0; i < tables1.length; i++) {
					try {
						sess.createSQLQuery(" drop table " + tables1[i] + ""+imptemptable+"").executeUpdate();
					} catch (Exception e) {
						
					}
				}
				
			}
			
			if(A0165!=null && !A0165.equals("")){
				sess.createSQLQuery(" drop table a"+imptemptable+"a01").executeUpdate();
			}
			sess.createSQLQuery(" drop table aa"+imptemptable+"a01").executeUpdate();
			sess.createSQLQuery(" delete from verify_process where batch_num like '"+ impdeptid +"%' and result_flag in ('0','1')").executeUpdate();
			try {
				if (ftype.equalsIgnoreCase("hzb") || ftype.equalsIgnoreCase("zb3")) {
					new LogUtil("463", "IMP_RECORD", "", "", "导入应用库", new ArrayList(),userVo).start();
				} else if (ftype.equalsIgnoreCase("zzb3")){
					new LogUtil("473", "IMP_RECORD", "", "", "导入应用库", new ArrayList(),userVo).start();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
//			new Thread(new DeleteTempThread(imprecordid)).start();
//			sess.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				new Thread(new ImpModLogThread("DONE", "迁移异常", sortid++, optype, imprecordid, true)).start();
//				new ImpModLogThread("DONE", "迁移异常", sortid++, optype, imprecordid, false);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(sess!=null){
				try {
					sess.getTransaction().rollback();
					imp.setImpstutas("1");
					sess.update(imp);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally{
			if(sess != null){
				sess.close();
			}
		}

	}
	/**检查索引创建索引，索引对应如下; 若 imptemptable 为af20150111， 则对应A01表为A01af20150111表
	 * A01(A01af20150111).a0000------a0000A01af20150111
	 * A02(A02af20150111).a0200------a0200A02af20150111
	 * 
	 * @param imptemptable
	 */
	public static void checkIndex(String imptemptable) {
		HBSession sess = HBUtil.getHBSession();
		try {
		
				CommonQueryBS.systemOut("index...");
				try {
					sess.createSQLQuery("Create index A0000A01" + imptemptable + " on A01" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0101A01" + imptemptable + " on A01" + imptemptable + "(A0101)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0104A01" + imptemptable + " on A01" + imptemptable + "(A0104)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0148A01" + imptemptable + " on A01" + imptemptable + "(A0148)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0163A01" + imptemptable + " on A01" + imptemptable + "(A0163)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0184A01" + imptemptable + " on A01" + imptemptable + "(A0184)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index ORGIDA01" + imptemptable + " on A01" + imptemptable + "(ORGID)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index STATUSA01" + imptemptable + " on A01" + imptemptable + "(STATUS)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				
				try {
					sess.createSQLQuery("Create index A0200A02" + imptemptable + " on A02" + imptemptable + "(A0200)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A02" + imptemptable + " on A02" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0201BA02" + imptemptable + " on A02" + imptemptable + "(A0201B)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0255A02" + imptemptable + " on A02" + imptemptable + "(A0255)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0600A06" + imptemptable + " on A06" + imptemptable + "(A0600)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A06" + imptemptable + " on A06" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0800A08" + imptemptable + " on A08" + imptemptable + "(A0800)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A08" + imptemptable + " on A08" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0801BA08" + imptemptable + " on A08" + imptemptable + "(A0801B)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A1100A11" + imptemptable + " on A11" + imptemptable + "(A1100)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A11" + imptemptable + " on A11" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A1400A14" + imptemptable + " on A14" + imptemptable + "(A1400)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A14" + imptemptable + " on A14" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A15" + imptemptable + " on A15" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A1500A15" + imptemptable + " on A15" + imptemptable + "(A1500)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A29" + imptemptable + " on A29" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A30" + imptemptable + " on A30" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A31" + imptemptable + " on A31" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A3600A36" + imptemptable + " on A36" + imptemptable + "(A3600)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A36" + imptemptable + " on A36" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A37" + imptemptable + " on A37" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A4100A41" + imptemptable + " on A41" + imptemptable + "(A4100)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A41" + imptemptable + " on A41" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A5300A53" + imptemptable + " on A53" + imptemptable + "(A5300)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A53" + imptemptable + " on A53" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A57" + imptemptable + " on A57" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A6000A60" + imptemptable + " on A60" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A61" + imptemptable + " on A61" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A62" + imptemptable + " on A62" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A63" + imptemptable + " on A63" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A6400A64" + imptemptable + " on A64" + imptemptable + "(A6400)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index B0111B01" + imptemptable + " on B01" + imptemptable + "(B0111)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index B0121B01" + imptemptable + " on B01" + imptemptable + "(B0121)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index B0131B01" + imptemptable + " on B01" + imptemptable + "(B0131)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index B0111B_E" + imptemptable + " on B_E" + imptemptable + "(B0111)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000I_E" + imptemptable + " on I_E" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void savelog(String table, String detail,Long sortid, String optype,String imprecordid, boolean islast) throws Exception {
		HBSession sess = HBUtil.getHBSession();
		try {
			List<Datarecrejlog> pros = sess.createQuery(" from Datarecrejlog where sortid="+(sortid-1)+" and imprecordid='" + imprecordid+ "'").list();
			if(pros!=null && pros.size()>0){
				Datarecrejlog pro = pros.get(0);
				pro.setStarttime(DateUtil.getTimestamp());
				pro.setOpstatus("2");
				sess.update(pro);
			}
			if(!islast){
				Datarecrejlog pro = new Datarecrejlog();
				pro.setStarttime(DateUtil.getTimestamp());
				pro.setDatatable(table);
				pro.setDetail(detail);
				pro.setImprecordid(imprecordid);
				pro.setSortid(sortid);
				pro.setOptype(optype);
				pro.setOpstatus("1");
				sess.save(pro);
			}
			
			sess.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
	
	public static void appendFileContent(String fileName, String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
           FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static void checkIndexZip(String imptemptable) {
		HBSession sess = HBUtil.getHBSession();
		try {
			if(DBUtil.getDBType().equals(DBType.MYSQL)){
				CommonQueryBS.systemOut("index...");
				try {
					sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index A0000A01" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index TBRA01" + imptemptable + "(TBR)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index STATUSA01" + imptemptable + "(STATUS)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A01" + imptemptable + " add index A0184A01" + imptemptable + "(A0184)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {
					sess.createSQLQuery("ALTER TABLE A02" + imptemptable + " add index A0200A02" + imptemptable + "(A0200)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A02" + imptemptable + " add index A0000A02" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A02" + imptemptable + " add index A0201BA02" + imptemptable + "(A0201B)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {
					sess.createSQLQuery("ALTER TABLE A06" + imptemptable + " add index A0600A06" + imptemptable + "(A0600)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A06" + imptemptable + " add index A0000A06" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A08" + imptemptable + " add index A0800A08" + imptemptable + "(A0800)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A08" + imptemptable + " add index A0000A08" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A08" + imptemptable + " add index A0801BA08" + imptemptable + "(A0801B)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A14" + imptemptable + " add index A1400A14" + imptemptable + "(A1400)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A14" + imptemptable + " add index A0000A14" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A15" + imptemptable + " add index A1500A15" + imptemptable + "(A1500)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A15" + imptemptable + " add index A0000A15" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A36" + imptemptable + " add index A3600A36" + imptemptable + "(A3600)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A36" + imptemptable + " add index A0000A36" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE A57" + imptemptable + " add index A0000A57" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE B01" + imptemptable + " add index B0111B01" + imptemptable + "(B0111)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE B01" + imptemptable + " add index B0131B01" + imptemptable + "(B0131)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("ALTER TABLE B01" + imptemptable + " add index B0121B01" + imptemptable + "(B0121)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				CommonQueryBS.systemOut("index...");
				try {
					sess.createSQLQuery("Create index A0000A01" + imptemptable + " on A01" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0101A01" + imptemptable + " on A01" + imptemptable + "(A0101)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0104A01" + imptemptable + " on A01" + imptemptable + "(A0104)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0148A01" + imptemptable + " on A01" + imptemptable + "(A0148)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0163A01" + imptemptable + " on A01" + imptemptable + "(A0163)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0184A01" + imptemptable + " on A01" + imptemptable + "(A0184)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index ORGIDA01" + imptemptable + " on A01" + imptemptable + "(ORGID)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index STATUSA01" + imptemptable + " on A01" + imptemptable + "(STATUS)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				
				try {
					sess.createSQLQuery("Create index A0200A02" + imptemptable + " on A02" + imptemptable + "(A0200)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A02" + imptemptable + " on A02" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0201BA02" + imptemptable + " on A02" + imptemptable + "(A0201B)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0255A02" + imptemptable + " on A02" + imptemptable + "(A0255)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0600A06" + imptemptable + " on A06" + imptemptable + "(A0600)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A06" + imptemptable + " on A06" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0800A08" + imptemptable + " on A08" + imptemptable + "(A0800)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A08" + imptemptable + " on A08" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0801BA08" + imptemptable + " on A08" + imptemptable + "(A0801B)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A1400A14" + imptemptable + " on A14" + imptemptable + "(A1400)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A14" + imptemptable + " on A14" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A15" + imptemptable + " on A15" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A1500A15" + imptemptable + " on A15" + imptemptable + "(A1500)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A3600A36" + imptemptable + " on A36" + imptemptable + "(A3600)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A36" + imptemptable + " on A36" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index A0000A57" + imptemptable + " on A57" + imptemptable + "(A0000)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index B0111B01" + imptemptable + " on B01" + imptemptable + "(B0111)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index B0121B01" + imptemptable + " on B01" + imptemptable + "(B0121)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sess.createSQLQuery("Create index B0131B01" + imptemptable + " on B01" + imptemptable + "(B0131)").executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static String getNo() {
		String no = "";
		String[] key = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
				"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		for (int i = 0; i < 4; i++) {
			Random random = new Random();
			int r = random.nextInt(35);
			no = no + key[r];
		}
		CommonQueryBS.systemOut(no);
		return no;
	}
}
