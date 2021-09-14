package com.insigma.siis.local.pagemodel.zhgbDatav;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.siis.local.business.datavaerify.UploadHzbFileBS;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.ImpSynControl;
import com.insigma.siis.local.util.TYGSsqlUtil;

public class ImpZzbPart1ThreadZHGB implements Runnable {
	private String houzhui;
	private String imprecordid;
	private String uuid;
	private String from_file;
	private String B0111;
	private String deptid;
	private String impdeptid;
	private String logfilename;
	private ImpSynControl control;
	private UserVO userVo;
	private String no;
	private String fxz;
	
	public ImpZzbPart1ThreadZHGB(UserVO userVo, String houzhui, String imprecordid,
			String uuid, String from_file, String b0111, String deptid,
			String impdeptid, String logfilename, ImpSynControl control,
			String no, String fxz) {
		super();
		this.houzhui = houzhui;
		this.imprecordid = imprecordid;
		this.uuid = uuid;
		this.from_file = from_file;
		B0111 = b0111;
		this.deptid = deptid;
		this.impdeptid = impdeptid;
		this.logfilename = logfilename;
		this.control = control;
		this.no = no;
		this.userVo = userVo;
		this.fxz = fxz;
	}

	@Override
	public void run() {
		String imprecordid = uuid; // 导入记录id
		String process_run = "3"; // 导入过程序号
		UploadHzbFileBSZHGB uploadbs = new UploadHzbFileBSZHGB(); // 业务处理bs
		// ----------------------------------------------------------------------------------------
		
		
		String tableExt = control.getTableExt();
		Connection conn =  null;
		try {
			if(houzhui.equalsIgnoreCase("7z")||houzhui.equalsIgnoreCase("zip")){
				String tables[] = { "A01", "A02", "A05", "A06", "A08", "A14", "A15", "A36", "A57", "B01", "A99Z1", "A30" };
				
				int time = 11;
			
				if(DBUtil.getDBType().equals(DBType.MYSQL)){
					while (true) {
						int number = 0;
//						if(number == 0){
//							throw new AppException("222");
//						}
						number = control.getNumber();
						if (number < time) {
							appendFileContent(logfilename,"==============================================="+ "\n");
							appendFileContent(logfilename, tables[number]+"数据导入"+ DateUtil.getTime()+ "\n"+ "neicun："+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
							KingbsconfigBS.saveImpDetail(process_run, "1", "提取表"+ tables[number] + "数据， 剩余"+ (control.getNumber2()) + "张。", imprecordid);
							CommonQueryBS.systemOut(tables[number]+ "数据导入"+ DateUtil.getTime() + "===>导入数据开始");
							int t_n = uploadbs.saveData_SaxHander4(houzhui.toLowerCase(), tables[number], imprecordid, uuid,from_file, B0111, deptid, impdeptid, tableExt);
							CommonQueryBS.systemOut(tables[number]+ "数据导入"+ DateUtil.getTime() + "===>导入数据结束");
							appendFileContent(logfilename, tables[number]+ "数据导入完成"+ DateUtil.getTime()+ "\n"+ "neicun："+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
							appendFileContent(logfilename,"==============================================="+ "\n");
							control.addT_n(t_n);
						} else {
							break;
						}
					}
				} else {
					while (true) {
						int number = 0;
						number = control.getNumber();
						if (number < time) {
							appendFileContent(logfilename,"==============================================="+ "\n");
							appendFileContent(logfilename, tables[number]+"数据导入"+ DateUtil.getTime()+ "\n"+ "neicun："+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
							KingbsconfigBS.saveImpDetail(process_run, "1", "提取表"+ tables[number] + "数据， 剩余"+ (control.getNumber2()) + "张。", imprecordid);
							CommonQueryBS.systemOut(tables[number]+ "数据导入"+ DateUtil.getTime() + "===>导入数据开始");
							int t_n = uploadbs.saveData_SaxHander3(houzhui.toLowerCase(), tables[number], imprecordid, uuid,from_file, B0111, deptid, impdeptid, tableExt);
							CommonQueryBS.systemOut(tables[number]+ "数据导入"+ DateUtil.getTime() + "===>导入数据结束");
							appendFileContent(logfilename, tables[number]+ "数据导入完成"+ DateUtil.getTime()+ "\n"+ "neicun："+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
							appendFileContent(logfilename,"==============================================="+ "\n");
							control.addT_n(t_n);
						} else {
							break;
						}
					}
				}
			}else{
				/*String tables[] = { "A01", "A02", "A06", "A08", "A11", "A14", "A15",
					"A29", "A30", "A31", "A36", "A37", "A41", "A53", "A57", "B01",
					"I_E", "B_E","A60", "A61", "A62", "A63", "A64" , "A05" , "A99Z1" };*/
				/*int time = houzhui.equalsIgnoreCase("hzb")? 28 : 16;*/
				
				String tables[] = { "A01", "A02", "A06", "A08", "A14", "A15", "A36", "A57", "A29", "B01","A11", "A41","A30","A37","A31",
						"I_E", "B_E", "A05" , "A99Z1" };
				int time = houzhui.equalsIgnoreCase("hzb")? 19 : 15;
			
				if(DBUtil.getDBType().equals(DBType.MYSQL)){
					while (true) {
						int number = 0;
//						if(number == 0){
//							throw new AppException("222");
//						}
						number = control.getNumber();
						if (number < time) {
							appendFileContent(logfilename,"==============================================="+ "\n");
							appendFileContent(logfilename, tables[number]+"数据导入"+ DateUtil.getTime()+ "\n"+ "neicun："+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
							KingbsconfigBS.saveImpDetail(process_run, "1", "提取表"+ tables[number] + "数据， 剩余"+ (control.getNumber2()) + "张。", imprecordid);
							CommonQueryBS.systemOut(tables[number]+ "数据导入"+ DateUtil.getTime() + "===>导入数据开始");
							int t_n = uploadbs.saveData_SaxHander4(houzhui.toLowerCase(), tables[number], imprecordid, uuid,from_file, B0111, deptid, impdeptid, tableExt);
							CommonQueryBS.systemOut(tables[number]+ "数据导入"+ DateUtil.getTime() + "===>导入数据结束");
							appendFileContent(logfilename, tables[number]+ "数据导入完成"+ DateUtil.getTime()+ "\n"+ "neicun："+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
							appendFileContent(logfilename,"==============================================="+ "\n");
							control.addT_n(t_n);
						} else {
							break;
						}
					}
				} else {
					while (true) {
						int number = 0;
						number = control.getNumber();
						if (number < time) {
							appendFileContent(logfilename,"==============================================="+ "\n");
							appendFileContent(logfilename, tables[number]+"数据导入"+ DateUtil.getTime()+ "\n"+ "neicun："+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
							KingbsconfigBS.saveImpDetail(process_run, "1", "提取表"+ tables[number] + "数据， 剩余"+ (control.getNumber2()) + "张。", imprecordid);
							CommonQueryBS.systemOut(tables[number]+ "数据导入"+ DateUtil.getTime() + "===>导入数据开始");
							int t_n = uploadbs.saveData_SaxHander3(houzhui.toLowerCase(), tables[number], imprecordid, uuid,from_file, B0111, deptid, impdeptid, tableExt);
							CommonQueryBS.systemOut(tables[number]+ "数据导入"+ DateUtil.getTime() + "===>导入数据结束");
							appendFileContent(logfilename, tables[number]+ "数据导入完成"+ DateUtil.getTime()+ "\n"+ "neicun："+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
							appendFileContent(logfilename,"==============================================="+ "\n");
							control.addT_n(t_n);
						} else {
							break;
						}
					}
				}
			}
			

			if (control.getStatus() == 2) {
				HBSession sess = HBUtil.getHBSession();
				conn = sess.connection();
				conn.setAutoCommit(true);
				
				if(houzhui.equalsIgnoreCase("zip")){
					KingbsconfigBS.saveImpDetail(process_run, "1", "正在更新学历学位",imprecordid);
					TYGSsqlUtil.updateA08(tableExt);
				}
				if(houzhui.equalsIgnoreCase("7z")){
					KingbsconfigBS.saveImpDetail(process_run, "1", "正在更新A01——任现职务时间",imprecordid);
					TYGSsqlUtil.updateA0192F(tableExt);
				}
				
				if(houzhui.equalsIgnoreCase("zb3")){
					
					if(DBType.ORACLE == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.ORACLE)){
						
						KingbsconfigBS.saveImpDetail(process_run, "1", "正在提取A05表",imprecordid);
						sess.createSQLQuery("insert into a05" + tableExt + " select a.a0000,sys_guid(),'0',a.a0221,a.a0243,a.a0245,null,'1','1','2' from a02" + tableExt + " a where a0200 in("
								+ " select a0200 from ("
								+ " select t.a0000,t.a0200,t.a0223,t.a0221,row_number()over(partition by t.a0000 order by t.a0221 asc,to_number(t.a0223) asc) a0221rn from ("
								+ "select a0000,a0200,a0223,A0221 from a02" + tableExt + " where a0255='1' ) t ) x where x.a0221rn =1 )").executeUpdate();
						
						//主职务
						KingbsconfigBS.saveImpDetail(process_run, "1", "正在更新A02——主职务",imprecordid);
						sess.createSQLQuery("UPDATE A02"+tableExt+" a SET a.A0279 = '0'").executeUpdate();
						sess.createSQLQuery("UPDATE A02"+tableExt+" a SET a.A0279 = '1' WHERE a.A0200 IN ("
							+ "SELECT T.A0200 FROM(SELECT ROW_NUMBER() OVER(PARTITION BY v.A0000 ORDER BY v.A0221,v.A0219,v.A0223) rn,v.A0200 "
							+ "FROM A02"+tableExt+" v WHERE v.A0255 = '1') T WHERE T.rn = 1)").executeUpdate();
						
						//更新任现职务时间
						KingbsconfigBS.saveImpDetail(process_run, "1", "正在更新A01——任现职务时间",imprecordid);
						String A0192TableSql = "create table A0192"+tableExt+"(A0000 VARCHAR2(120),newA0223 VARCHAR2(200))";
						sess.createSQLQuery(A0192TableSql.toUpperCase()).executeUpdate();
						sess.createSQLQuery("insert into A0192"+tableExt+" SELECT W.A0000,W.newA0223 FROM "
								+ "(SELECT ROW_NUMBER () OVER ( PARTITION BY V.a0000 ORDER BY	V.a0223 DESC) a0223rn,V.* FROM "
								+ "(SELECT T .A0000,T .A0243,T .A0223,WM_CONCAT (T .A0243) OVER (PARTITION BY T .A0000 ORDER BY T .A0223) newA0223 "
								+ "FROM(SELECT A .A0000,Substr(A.A0243, 1, 4)||'.'||Substr(A.A0243, 5, 2) A0243,A .A0223 FROM A02" + tableExt + " A WHERE A .A0255 = '1' AND A .A0281 = 'true' AND (LENGTH (A.A0243) = 6 OR LENGTH (A.A0243) = 8)) T ) V ) W "
								+ "WHERE W.A0223RN = 1 ").executeUpdate();
						sess.createSQLQuery("Create index AA0192" + tableExt + " on A0192" + tableExt + "(A0000)").executeUpdate();
						sess.createSQLQuery("UPDATE A01" + tableExt + " A01 SET A01.A0192F = (SELECT X.newA0223 FROM A0192" + tableExt + " X WHERE X.A0000 = A01.A0000) WHERE 1 = 1 AND EXISTS (SELECT 1 FROM A0192" + tableExt + " X WHERE X.A0000 = A01.A0000)").executeUpdate();
						sess.createSQLQuery("drop table A0192"+tableExt).executeUpdate();
						
						//sess.createSQLQuery("Create index A0111aa01" + tableExt + " on a01" + tableExt + "(a0111a)").executeUpdate();
						//sess.createSQLQuery("Create index A0114aa01" + tableExt + " on a01" + tableExt + "(A0114a)").executeUpdate();
						//sess.createSQLQuery("Create index A2949A29" + tableExt + " on A29" + tableExt + "(A2949)").executeUpdate();
						
						//sess.createSQLQuery("UPDATE a01"+tableExt+" t set t.A0111 = (select min(c.code_value) from code_value c where c.code_type = 'ZB01' and c.code_name3 = t.A0111a) where  A0111a is not null and exists (select 1 from code_value where code_type='ZB01' and code_name3=t.A0111a)").executeUpdate();
						//sess.createSQLQuery("UPDATE a01"+tableExt+" t set t.A0114 = (select min(c.code_value) from code_value c where c.code_type = 'ZB01' and c.code_name3 = t.A0114a) where  A0114a is not null and exists (select 1 from code_value where code_type='ZB01' and code_name3=t.A0114a)").executeUpdate();
						KingbsconfigBS.saveImpDetail(process_run, "1", "正在更新A01——是否具有两年工作经验",imprecordid);
						sess.createSQLQuery("UPDATE a01"+tableExt+" t set t.A0197 = '0'").executeUpdate();
						sess.createSQLQuery("UPDATE a01"+tableExt+" t set t.A0197 = '1' where a0194 >=24").executeUpdate();
						
						KingbsconfigBS.saveImpDetail(process_run, "1", "正在更新A01——现职务层次",imprecordid);
						sess.createSQLQuery("Create index A0000A05" + tableExt + " on A05" + tableExt + "(A0000)").executeUpdate();
						sess.createSQLQuery("UPDATE A01"+tableExt+" T SET (T.A0221,T.A0288) = (SELECT c.a0501b,c.a0504 FROM a05"+tableExt+" c WHERE c.a0000 = T .a0000) "
								+ "WHERE 1 = 1 AND EXISTS (SELECT 1 FROM a05"+tableExt+" c WHERE c.a0000 = T .a0000)").executeUpdate();
						KingbsconfigBS.saveImpDetail(process_run, "1", "正在更新A01——公务员登记时间",imprecordid);
						sess.createSQLQuery("Create index A0000A29" + tableExt + " on A29" + tableExt + "(A0000)").executeUpdate();
						sess.createSQLQuery("UPDATE A01"+tableExt+" t set t.A2949 = (select c.a2949 from a29"+tableExt+" c where c.a0000 = t.a0000) where 1=1 and exists (select 1 from a29"+tableExt+" c where c.a0000 = t.a0000)").executeUpdate();
						
						//最高学历维护
						KingbsconfigBS.saveImpDetail(process_run, "1", "正在更新A01——最高学历",imprecordid);
						String A0801TableSql = "create table A0801"+tableExt+"(A0000 varchar2(120),A0801A varchar2(60),ZGXLXX varchar2(120))";
						sess.createSQLQuery(A0801TableSql.toUpperCase()).executeUpdate();
						sess.createSQLQuery("insert into A0801"+tableExt+" SELECT T .A0000,T .A0801A,T .ZGXLXX FROM (SELECT ROW_NUMBER () OVER (PARTITION BY v.A0000 "
								+ "ORDER BY v.A0834) rn,v.A0000,v.A0801A,CONCAT (v.A0814, v.A0824) ZGXLXX FROM A08"+tableExt+" v WHERE v.A0899 = 'true' AND v.A0834 = '1') T "
								+ "WHERE T .rn = 1 ").executeUpdate();
						sess.createSQLQuery("Create index AA0801" + tableExt + " on A0801" + tableExt + "(A0000)").executeUpdate();
						sess.createSQLQuery("UPDATE A01"+tableExt+" A01 SET (A01.ZGXL, A01.ZGXLXX) = (SELECT T .A0801A,T .ZGXLXX FROM A0801"+tableExt+" T "
								+ "WHERE T.A0000 = A01.A0000) WHERE 1 = 1 AND EXISTS (SELECT 1 FROM A0801"+tableExt+" T WHERE T.A0000 = A01.A0000)").executeUpdate();
						sess.createSQLQuery("drop table A0801"+tableExt).executeUpdate();
						//最高学位维护
						KingbsconfigBS.saveImpDetail(process_run, "1", "正在更新A01——最高学位",imprecordid);
						String A0901TableSql = "create table A0901"+tableExt+"(A0000 varchar2(120),newA0901A varchar2(60),ZGXWXX varchar2(120))";
						sess.createSQLQuery(A0901TableSql.toUpperCase()).executeUpdate();
						sess.createSQLQuery("insert into A0901"+tableExt+" SELECT W.A0000,SUBSTR(W.NEWA0901A, 1, 30) NEWA0901A,W.ZGXWXX FROM (SELECT ROW_NUMBER () OVER (PARTITION BY "
								+ "Y.A0000 ORDER BY Y.A0000) rn,Y.A0000,Y.NEWA0901A,Y.ZGXWXX FROM (SELECT T .A0000,WM_CONCAT (T .A0901A) OVER (PARTITION BY T .A0000) "
								+ "newA0901A,ZGXWXX FROM (SELECT v.A0000,v.A0901A,CONCAT (v.A0814, v.A0824) ZGXWXX FROM A08"+tableExt+" v WHERE v.A0899 = 'true' AND v.A0835 = '1') "
								+ "T) Y) W WHERE W.RN = 1 ").executeUpdate();
						sess.createSQLQuery("Create index AA0901" + tableExt + " on A0901" + tableExt + "(A0000)").executeUpdate();
						sess.createSQLQuery("UPDATE A01"+tableExt+" A01 SET (A01.ZGXW,A01.ZGXWXX) = (SELECT W.NEWA0901A,W.ZGXWXX FROM A0901"+tableExt+" "
								+ "W WHERE W.A0000 = A01.A0000) WHERE 1 = 1 AND EXISTS (SELECT 1 FROM  A0901"+tableExt+" W WHERE W.A0000 = A01.A0000)").executeUpdate();
						sess.createSQLQuery("drop table A0901"+tableExt).executeUpdate();
					}
					if(DBType.MYSQL == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.MYSQL)){
						
						KingbsconfigBS.saveImpDetail(process_run, "1", "正在提取A05表",imprecordid);
						Statement smt = sess.connection().createStatement();
						smt.executeUpdate("insert into a05" + tableExt + " SELECT a.a0000,uuid(),'0',a.a0221,a.a0288,a.a0245,NULL,'1','1','2' "
								+ "FROM a02" + tableExt + " a WHERE a0200 IN ( SELECT T.A0200 FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = V.A0000 "
								+ "THEN @rowNO + 1 ELSE 1 END ) RN, V.A0000, V.A0200, V.A0223, V.A0221, "
								+ "(@pre_parent_code := V.A0000) GRN FROM A02" + tableExt + " V,(SELECT @rowNO := 0 ,@pre_parent_code := '') XXX "
								+ "WHERE V.A0255 = '1' ORDER BY V.A0000,V.A0221,V.A0223) T WHERE T.RN = 1)");
						
						//主职务
						KingbsconfigBS.saveImpDetail(process_run, "1", "正在更新A02——主职务",imprecordid);
						sess.createSQLQuery("UPDATE A02"+tableExt+" a SET a.A0279 = '0'").executeUpdate();
						Statement smt2 = sess.connection().createStatement();
						smt2.executeUpdate("UPDATE A02"+tableExt+" A SET A.A0279 = '1' WHERE A.A0200 IN (SELECT T.A0200 FROM(SELECT (@rowNO := CASE WHEN @pre_parent_code = V.A0000 THEN @rowNO + 1 ELSE 1 END ) "
								+ " RN, V.A0200, V.A0000, (@pre_parent_code := V.A0000) GRN FROM A02"+tableExt+" V,(SELECT @rowNO := 0,@pre_parent_code := '') XXX WHERE V.A0255 = '1' "
								+ "ORDER BY V.A0000, V.A0221, V.A0219, V.A0223 ) T WHERE T.RN = 1)");
						
						//更新任现职务时间
						KingbsconfigBS.saveImpDetail(process_run, "1", "正在更新A01——任现职务时间",imprecordid);
						sess.createSQLQuery("UPDATE A01" + tableExt + " b,( SELECT T.A0000,group_concat(T.a0243 ORDER BY T.a0223) newa0243 FROM(SELECT a.A0000,"
								+ "CONCAT(Substring(a.A0243, 1, 4),'.',Substring(a.A0243, 5, 2)) a0243,a.A0223 "
								+ "FROM A02" + tableExt + " a WHERE a.A0255 = '1' AND a.A0281 = 'true' AND (LENGTH (a.A0243) = 6 OR LENGTH (a.A0243) = 8)) T "
								+ "GROUP BY T.A0000 ) X SET b.A0192F = X.newa0243 WHERE b.A0000 = X.A0000").executeUpdate();
						
						//String time= System.currentTimeMillis() +"";
						//sess.createSQLQuery("ALTER TABLE A01" + tableExt + " add index A0111AA01" + tableExt + "(A0111A)").executeUpdate();
						//sess.createSQLQuery("ALTER TABLE A01" + tableExt + " add index A0114AA01" + tableExt + "(A0114A)").executeUpdate();
						//sess.createSQLQuery("create TABLE c_ZB01"+time+" as select * from code_value AS k where k.code_type = 'ZB01'").executeUpdate();
						//sess.createSQLQuery("UPDATE a01"+tableExt+" AS t,c_ZB01"+time+" AS k SET t.A0111 = k.code_value WHERE t.A0111a = k.code_name3  AND k.code_type = 'ZB01'").executeUpdate();
						//sess.createSQLQuery("UPDATE a01"+tableExt+" AS t,c_ZB01"+time+" AS k SET t.A0114 = k.code_value WHERE t.A0114a = k.code_name3  AND k.code_type = 'ZB01'").executeUpdate();
						//sess.createSQLQuery("drop table c_ZB01"+time+"").executeUpdate();
						KingbsconfigBS.saveImpDetail(process_run, "1", "正在更新A01——是否具有两年工作经验",imprecordid);
						sess.createSQLQuery("UPDATE a01"+tableExt+" t set t.A0197 = '0'").executeUpdate();
						sess.createSQLQuery("UPDATE a01"+tableExt+" t set t.A0197 = '1' where a0194 >=24").executeUpdate();
						
						//sess.createSQLQuery("ALTER TABLE A01" + tableExt + " add index A2949A01" + tableExt + "(A2949)").executeUpdate();
						//sess.createSQLQuery("ALTER TABLE A29" + tableExt + " add index A2949A29" + tableExt + "(A2949)").executeUpdate();
						KingbsconfigBS.saveImpDetail(process_run, "1", "正在更新A01——现职务层次",imprecordid);
						/*sess.createSQLQuery("UPDATE A01"+tableExt+" t set t.A0221 = (select max(c.a0501b) from a05"+tableExt+" c where c.a0000 = t.a0000),"
								+ "t.A0288 = (select min(c.a0504) from a05"+tableExt+" c where c.a0000 = t.a0000) where  1=1 and exists (select 1 from a05"+tableExt+" c where c.a0000 = t.a0000)").executeUpdate();*/
						sess.createSQLQuery("ALTER TABLE A05" + tableExt + " add index A0000A05" + tableExt + "(A0000)").executeUpdate();
						sess.createSQLQuery("UPDATE A01"+tableExt+" t,A05"+tableExt+" c SET t.A0221 = c.a0501b,t.A0288 = c.a0504 WHERE c.a0000 = t.a0000 ").executeUpdate();
						
						KingbsconfigBS.saveImpDetail(process_run, "1", "正在更新A01——公务员登记时间",imprecordid);
						sess.createSQLQuery("ALTER TABLE A29" + tableExt + " add index A0000A29" + tableExt + "(A0000)").executeUpdate();
						sess.createSQLQuery("UPDATE a01"+tableExt+" AS t, A29" + tableExt + " AS k SET t.A2949 = k.A2949 WHERE k.a0000 = t.a0000 ").executeUpdate();
						
						//最高学历维护
						KingbsconfigBS.saveImpDetail(process_run, "1", "正在更新A01——最高学历",imprecordid);
						Statement smtA0801 = sess.connection().createStatement();
						String A0801TableSql = "create table A0801"+tableExt+"(A0000 varchar(120),A0801A varchar(60),ZGXLXX varchar(120)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
						smtA0801.execute(A0801TableSql.toUpperCase());
						smtA0801.executeUpdate("insert into A0801"+tableExt+" SELECT T.A0000,T.A0801A,T.ZGXLXX FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = V.A0000  THEN "
								+ "@rowNO + 1 ELSE 1 END) RN,V.A0000,V.A0834,V.A0801A,CONCAT(IFNULL(V.A0814,''), IFNULL(V.A0824,'')) ZGXLXX,(@pre_parent_code := V.A0000) GRN FROM A08"+tableExt+"  V,"
								+ "(SELECT @rowNO := 0 ,@pre_parent_code := '')  XXX WHERE V.A0899 = 'true' AND V.A0834 = '1' ORDER BY V.A0000,V.A0834 DESC) T "
								+ "WHERE T.RN = 1");
						sess.createSQLQuery("ALTER TABLE A0801" + tableExt + " add index A0000A0801" + tableExt + "(A0000)").executeUpdate();
						
						Statement smt3 = sess.connection().createStatement();
						smt3.executeUpdate("UPDATE A01"+tableExt+" A01,A0801"+tableExt+" Y SET A01.ZGXL = Y.A0801A,A01.ZGXLXX = Y.ZGXLXX WHERE Y.A0000 = A01.A0000");
						smt3.execute("drop table A0801"+tableExt);
						smt3.close();
						
						//最高学位维护
						KingbsconfigBS.saveImpDetail(process_run, "1", "正在更新A01——最高学位",imprecordid);
						Statement smtA0901 = sess.connection().createStatement();
						String A0901TableSql = "create table A0901"+tableExt+"(A0000 varchar(120),newA0901A varchar(60),ZGXWXX varchar(120)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
						smtA0901.execute(A0901TableSql.toUpperCase());
						smtA0901.executeUpdate("insert into A0901"+tableExt+" SELECT T.A0000,group_concat(T.A0901A ORDER BY T.A0835) newA0901A,ZGXWXX FROM(SELECT V.A0000,"
								+ "V.A0835,V.A0901A,CONCAT(IFNULL(V.A0814,''), IFNULL(V.A0824,'')) ZGXWXX FROM A08"+tableExt+" V WHERE V.A0899 = 'true' AND V.A0835 = '1' ORDER BY V.A0000,V.A0835 DESC) T "
								+ "GROUP BY T.A0000");
						sess.createSQLQuery("ALTER TABLE A0901"+tableExt+" add index A0000A0901" + tableExt + "(A0000)").executeUpdate();
						
						Statement smt4 = sess.connection().createStatement();
						smt4.executeUpdate("UPDATE A01"+tableExt+" A01,A0901"+tableExt+" Y SET A01.ZGXW = Y.newA0901A, A01.ZGXWXX = Y.ZGXWXX WHERE Y.A0000 = A01.A0000");
						smt4.execute("drop table A0901"+tableExt);
						smt4.close();
					}
				}
				
				Integer n = 0;
				
				//非现职处理
				if("false".equals(fxz)){
					String fxzSql = "SELECT T.a0000 FROM ( SELECT a.a0000 FROM a01"+tableExt+" a WHERE a.a0163 <> '1' ) T";
					
					String numSql = "SELECT count(1) FROM ( SELECT a.a0000 FROM a01"+tableExt+" a WHERE a.a0163 <> '1' ) T";
					Object obj = sess.createSQLQuery(numSql).uniqueResult();
					if(obj!=null){
						n = Integer.parseInt(""+obj);
					}
					if(houzhui.equalsIgnoreCase("7z")||houzhui.equalsIgnoreCase("zip")){
						String tables[] = { "A02", "A05", "A06", "A08", "A14", "A15", "A36", "A57", "A99Z1", "A01" };
						for(int i=0;i<tables.length;i++){
							try {
								sess.createSQLQuery("delete from "+tables[i]+tableExt+" where A0000 in ("+fxzSql+")").executeUpdate();
							} catch (Exception e) {
							}
						}
					}else{
						String tables[] = { "A02", "A06", "A08", "A11", "A14", "A15",
								"A29", "A30", "A31", "A36", "A37", "A41", "A53", "A57", 
								"I_E", "A60", "A61", "A62", "A63", "A64" , "A05" , "A68", "A69", "A71", "A99Z1", "A01" };
						for(int i=0;i<tables.length;i++){
							try {
								sess.createSQLQuery("delete from "+tables[i]+tableExt+" where A0000 in ("+fxzSql+")").executeUpdate();
							} catch (Exception e) {
							}
						}
					}
				}
				
				conn.createStatement().execute("update imp_record set TOTAL_NUMBER='"
						+ (control.getT_n() - n)
						+ "',PROCESS_STATUS='2' where IMP_RECORD_ID='"
						+ imprecordid + "'");
				conn.close();
				CommonQueryBS.systemOut("END INSERT---------" + DateUtil.getTime());
				appendFileContent(logfilename, "导入完成" + "\n");
				/**/
				KingbsconfigBS.saveImpDetail(process_run, "2", "提取完成",
						imprecordid);
				try {
					if (houzhui.equalsIgnoreCase("hzb")) {
						new LogUtil("421", "IMP_RECORD", "", "", "导入临时库",
								new ArrayList(), userVo).start();
					} else {
						new LogUtil("422", "IMP_RECORD", "", "", "导入临时库",
								new ArrayList(), userVo).start();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					this.delFolder(control.getUnzip() + "Table/");
					this.delFolder(control.getUnzip() + "gwyinfo.xml");
					this.delFolder(control.getFilePath());
					/*if(!control.getFilePath().substring(control.getFilePath().lastIndexOf(".")+1).equalsIgnoreCase("zip")){*/
						this.delFolder(control.getUpload_file());
					/*}*/
				} catch (Exception e) {
					e.printStackTrace();
				}
				appendFileContent(logfilename, "删除缓存文件" + DateUtil.getTime()
						+ "\n");
				CommonQueryBS.systemOut("delete file END---------"
						+ DateUtil.getTime());
			}
		} catch (AppException e) {
			e.printStackTrace();
			uploadbs.rollbackImpTable(imprecordid, tableExt); 
			try {
				KingbsconfigBS.saveImpDetail(process_run, "4", "失败:" + e.getMessage(), imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}// 结束提示信息。
			if (no.equals("1")) {
				control.errStatus("1");
			} else {
				control.errStatus("2");
			}
			this.delFolder(control.getUnzip());
			this.delFolder(control.getFilePath());
			this.delFolder(control.getUpload_file());
		} catch (Exception e) {
			e.printStackTrace();
			try {
				KingbsconfigBS.saveImpDetail(process_run, "4", "失败:"
						+ e.getMessage(), imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}// 结束提示信息。
			if (no.equals("1")) {
				control.errStatus("1");
			} else {
				control.errStatus("2");
			}
//			uploadbs.rollbackImp(imprecordid);
			uploadbs.rollbackImpTable(imprecordid, tableExt); 
			this.delFolder(control.getUnzip());
			this.delFolder(control.getFilePath());
			this.delFolder(control.getUpload_file());
			e.printStackTrace();
		} catch (Throwable t){
			try {
				KingbsconfigBS.saveImpDetail("3","4","error:"+t.getMessage(),imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			CommonQueryBS.systemOut(DateUtil.getTime()+"--->查询表"+"异常时占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			t.printStackTrace();
		}finally{
			if(conn != null ){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private String getRootPath() {
		String classPath = this.getClass().getClassLoader().getResource("/")
				.getPath();
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String rootPath = "";
		if ("\\".equals(File.separator)) {
			rootPath = classPath.substring(1, classPath
					.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		if ("/".equals(File.separator)) {
			rootPath = classPath.substring(0, classPath
					.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		return rootPath;
	}

	public static void appendFileContent(String fileName, String content) {
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 删除文件夹
	// param folderPath 文件夹完整绝对路径
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}
}
