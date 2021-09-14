package com.insigma.siis.local.pagemodel.repandrec.local;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsGainBS;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.utils.CommandUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;

public class ImpBZBPartThread implements Runnable {
	
	private String logfilename;
	private Imprecord imprecord;
	private String deptid;
	private String bzbDeptid;
	private UserVO userVo;
	private ImpZzb3Control control;
	private String no;
	private int tabsize;
	private String fxz;
	private String adress;
	
	public ImpBZBPartThread(String logfilename, Imprecord imprecord,
			String deptid, String bzbDeptid, UserVO userVo, ImpZzb3Control control, int tabsize, String no, String fxz, String adress) {
		super();
		this.logfilename = logfilename;
		this.imprecord = imprecord;
		this.deptid = deptid;
		this.userVo = userVo;
		this.control = control;
		this.bzbDeptid = bzbDeptid;
		this.no = no;
		this.tabsize = tabsize;
		this.fxz = fxz;
		this.adress = adress;
	}
	@Override
	public void run() {
		HBSession sess = HBUtil.getHBSession();
		String tableExt = control.getTableExt();
		String photo_file = control.getPhoto_file();
		String imprecordid = imprecord.getImprecordid();
		String process_run = "3";
		int t_n = 0;
		try {
			String[] tables = {"A02","A06","A08","A11","A14","A15","A29","A30","A31",
					"A36","A37","A41","A53","A57","B01","A01","A60","A61","A62","A63","A64"};
			while (true) {
				int i = control.getNumber();
				if(i > tabsize-1){
					break;
				}
				String table = tables[i];
				if(table.equals("A57")){
					appendFileContent(logfilename, "==============================================="+"\n");
					KingbsconfigBS.saveImpDetail("3","1","提取第"+i+"张表A57数据， 剩余"+control.getNumber2()+"张。",imprecordid);
					KingbsGainBS.impTimeData_A57(logfilename, imprecord, photo_file, deptid, "A57", bzbDeptid, tableExt);
					
				} else {
					appendFileContent(logfilename, "==============================================="+"\n");
					int count = 0;
					if("A11".equals(table) || "A30".equals(table) || "A31".equals(table) || "A37".equals(table)
							|| "A41".equals(table) || "A53".equals(table) || "A60".equals(table) || "A61".equals(table) || "A62".equals(table)
							|| "A63".equals(table) || "A64".equals(table)){
						control.getNumber2();
					}else{
						KingbsconfigBS.saveImpDetail("3","1","提取第"+i+"张表"+table+"数据， 剩余"+control.getNumber2()+"张。",imprecordid);
						count = KingbsGainBS.impTimeData_Table(logfilename, imprecord, photo_file, deptid, table, bzbDeptid, tableExt, tabsize);
					}
					if(table.equals("A01")){
						t_n = count;
					}
				}
			}
			if(control.getStatus() == 2){
				
				//KingbsconfigBS.saveImpDetail("3", "1", "正在更新A01表字段",imprecordid);
				
				//更新任现职务时间
				/*Connection conn = sess.connection();
				PreparedStatement pstmt_update = conn.prepareStatement("update a01"+tableExt+" set a0192f=? where a0000=?");
				PreparedStatement pstmt = conn.prepareStatement("select a0000 from a01"+tableExt, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				pstmt.setFetchSize(100);  
				pstmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
				ResultSet rs = pstmt.executeQuery();
				PreparedStatement pstmt2 = conn.prepareStatement("select a0201a_all,a0201b,a0243,a0255 from a02"+tableExt+" where a0000=? and a0281='true' order by a0223");
				int count = 0;
				while (rs.next()) {
					Map<String,String> zr = new LinkedHashMap<String, String>();//
					Map<String,String> ym = new LinkedHashMap<String, String>();//
					String a0000 = rs.getString("a0000");
					pstmt2.setString(1, a0000);
					ResultSet rs2 = pstmt2.executeQuery();
					while (rs2.next()) {
						String a0201a = rs2.getString("a0201a_all");
						String a0255 = rs2.getString("a0255");
						String a0243 = rs2.getString("a0243");
						
						if(a0243!=null && !"".equals(a0243.trim())){
							if(a0243.length()==6){
								a0243 = a0243.substring(0, 4) + "." + a0243.substring(4);
							}
							if(a0243.length()==8){
								a0243 = a0243.substring(0, 4) + "." + a0243.substring(4,6);
							}
						}
						
						if(a0201a!=null && !a0201a.equals("")){
							if(a0201a.startsWith("|")){
								a0201a = a0201a.substring(1);
							}
							String a0201arr[] = a0201a.split("\\|");
							String keyy = a0201arr[0];
							if(a0255!=null&&a0255.equals("1")){
								String vv = zr.get(keyy);
								if(vv == null){
									vv = a0243;
								} else {
									if(a0243!=null && !a0243.equals("")){
										vv = vv +"、" +a0243;
									}
								}
								zr.put(keyy, vv);
							} else {
								String vv = ym.get(keyy);
								if(vv == null){
									vv = a0243;
								} else {
									if(a0243!=null && !a0243.equals("")){
										vv = vv +"、" +a0243;
									}
									
								}
								ym.put(keyy, vv);
							}
						}
					}
					rs2.close();
					String zrsj = "";
					for(String key : zr.keySet()){//全名
						String val = zr.get(key);
						if(val!=null && !"".equals(val)){
							zrsj += val + "，";
						}
					}
					String ymsj = "";
					for(String key : ym.keySet()){//全名
						String val = ym.get(key);
						if(val!=null && !"".equals(val)){
							ymsj += val + "，";
						}
					}
					String sj = (zrsj.equals("")?"":zrsj.substring(0, zrsj.length()-1)) + (ymsj.equals("")?"":"("+ymsj.substring(0, ymsj.length()-1)+")");
					pstmt_update.setString(1, sj);
					pstmt_update.setString(2, a0000);
					pstmt_update.addBatch();
					count ++;
					if(count%8000==0){
						pstmt_update.executeBatch();
						pstmt_update.clearParameters();
					}
				}
				pstmt_update.executeBatch();
				pstmt_update.clearParameters();
				rs.close();
				pstmt2.close();
				pstmt.close();
				pstmt_update.close();
				conn.close();*/
				
				if(DBType.ORACLE == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.ORACLE)){
					
					KingbsconfigBS.saveImpDetail("3", "1", "正在提取A05表",imprecordid);
					sess.createSQLQuery("insert into a05" + tableExt + " select a.a0000,sys_guid(),'0',a.a0221,a.a0243,a.a0245,null,'1','1','2' from a02" + tableExt + " a where a0200 in("
							+ " select a0200 from ("
							+ " select t.a0000,t.a0200,t.a0223,t.a0221,row_number()over(partition by t.a0000 order by t.a0221 asc,to_number(t.a0223) asc) a0221rn from ("
							+ "select a0000,a0200,a0223,A0221 from a02" + tableExt + " where a0255='1' ) t ) x where x.a0221rn =1 )").executeUpdate();
					
					//主职务
					KingbsconfigBS.saveImpDetail("3", "1", "正在更新A02——主职务",imprecordid);
					sess.createSQLQuery("UPDATE A02"+tableExt+" a SET a.A0279 = '0'").executeUpdate();
					sess.createSQLQuery("UPDATE A02"+tableExt+" a SET a.A0279 = '1' WHERE a.A0200 IN ("
						+ "SELECT T.A0200 FROM(SELECT ROW_NUMBER() OVER(PARTITION BY v.A0000 ORDER BY v.A0221,v.A0219,v.A0223) rn,v.A0200 "
						+ "FROM A02"+tableExt+" v WHERE v.A0255 = '1') T WHERE T.rn = 1)").executeUpdate();
					
					//更新任现职务时间
					KingbsconfigBS.saveImpDetail("3", "1", "正在更新A01——任现职务时间",imprecordid);
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
					KingbsconfigBS.saveImpDetail("3", "1", "正在更新A01——是否具有两年工作经验",imprecordid);
					sess.createSQLQuery("UPDATE a01"+tableExt+" t set t.A0197 = '0'").executeUpdate();
					sess.createSQLQuery("UPDATE a01"+tableExt+" t set t.A0197 = '1' where a0194 >=24").executeUpdate();
					
					KingbsconfigBS.saveImpDetail("3", "1", "正在更新A01——现职务层次",imprecordid);
					sess.createSQLQuery("Create index A0000A05" + tableExt + " on A05" + tableExt + "(A0000)").executeUpdate();
					sess.createSQLQuery("UPDATE A01"+tableExt+" T SET (T.A0221,T.A0288) = (SELECT c.a0501b,c.a0504 FROM a05"+tableExt+" c WHERE c.a0000 = T .a0000) "
							+ "WHERE 1 = 1 AND EXISTS (SELECT 1 FROM a05"+tableExt+" c WHERE c.a0000 = T .a0000)").executeUpdate();
					KingbsconfigBS.saveImpDetail("3", "1", "正在更新A01——公务员登记时间",imprecordid);
					sess.createSQLQuery("Create index A0000A29" + tableExt + " on A29" + tableExt + "(A0000)").executeUpdate();
					sess.createSQLQuery("UPDATE A01"+tableExt+" t set t.A2949 = (select c.a2949 from a29"+tableExt+" c where c.a0000 = t.a0000) where 1=1 and exists (select 1 from a29"+tableExt+" c where c.a0000 = t.a0000)").executeUpdate();
					
					//最高学历维护
					KingbsconfigBS.saveImpDetail("3", "1", "正在更新A01——最高学历",imprecordid);
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
					KingbsconfigBS.saveImpDetail("3", "1", "正在更新A01——最高学位",imprecordid);
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
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					
					KingbsconfigBS.saveImpDetail("3", "1", "正在提取A05表",imprecordid);
					Statement smt = sess.connection().createStatement();
					smt.executeUpdate("insert into a05" + tableExt + " SELECT a.a0000,uuid(),'0',a.a0221,a.a0288,a.a0245,NULL,'1','1','2' "
							+ "FROM a02" + tableExt + " a WHERE a0200 IN ( SELECT T.A0200 FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = V.A0000 "
							+ "THEN @rowNO + 1 ELSE 1 END ) RN, V.A0000, V.A0200, V.A0223, V.A0221, "
							+ "(@pre_parent_code := V.A0000) GRN FROM A02" + tableExt + " V,(SELECT @rowNO := 0 ,@pre_parent_code := '') XXX "
							+ "WHERE V.A0255 = '1' ORDER BY V.A0000,V.A0221,V.A0223) T WHERE T.RN = 1)");
					
					//主职务
					KingbsconfigBS.saveImpDetail("3", "1", "正在更新A02——主职务",imprecordid);
					sess.createSQLQuery("UPDATE A02"+tableExt+" a SET a.A0279 = '0'").executeUpdate();
					Statement smt2 = sess.connection().createStatement();
					smt2.executeUpdate("UPDATE A02"+tableExt+" A SET A.A0279 = '1' WHERE A.A0200 IN (SELECT T.A0200 FROM(SELECT (@rowNO := CASE WHEN @pre_parent_code = V.A0000 THEN @rowNO + 1 ELSE 1 END ) "
							+ " RN, V.A0200, V.A0000, (@pre_parent_code := V.A0000) GRN FROM A02"+tableExt+" V,(SELECT @rowNO := 0,@pre_parent_code := '') XXX WHERE V.A0255 = '1' "
							+ "ORDER BY V.A0000, V.A0221, V.A0219, V.A0223 ) T WHERE T.RN = 1)");
					
					//更新任现职务时间
					KingbsconfigBS.saveImpDetail("3", "1", "正在更新A01——任现职务时间",imprecordid);
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
					KingbsconfigBS.saveImpDetail("3", "1", "正在更新A01——是否具有两年工作经验",imprecordid);
					sess.createSQLQuery("UPDATE a01"+tableExt+" t set t.A0197 = '0'").executeUpdate();
					sess.createSQLQuery("UPDATE a01"+tableExt+" t set t.A0197 = '1' where a0194 >=24").executeUpdate();
					
					//sess.createSQLQuery("ALTER TABLE A01" + tableExt + " add index A2949A01" + tableExt + "(A2949)").executeUpdate();
					//sess.createSQLQuery("ALTER TABLE A29" + tableExt + " add index A2949A29" + tableExt + "(A2949)").executeUpdate();
					KingbsconfigBS.saveImpDetail("3", "1", "正在更新A01——现职务层次",imprecordid);
					/*sess.createSQLQuery("UPDATE A01"+tableExt+" t set t.A0221 = (select max(c.a0501b) from a05"+tableExt+" c where c.a0000 = t.a0000),"
							+ "t.A0288 = (select min(c.a0504) from a05"+tableExt+" c where c.a0000 = t.a0000) where  1=1 and exists (select 1 from a05"+tableExt+" c where c.a0000 = t.a0000)").executeUpdate();*/
					sess.createSQLQuery("ALTER TABLE A05" + tableExt + " add index A0000A05" + tableExt + "(A0000)").executeUpdate();
					sess.createSQLQuery("UPDATE A01"+tableExt+" t,A05"+tableExt+" c SET t.A0221 = c.a0501b,t.A0288 = c.a0504 WHERE c.a0000 = t.a0000 ").executeUpdate();
					
					KingbsconfigBS.saveImpDetail("3", "1", "正在更新A01——公务员登记时间",imprecordid);
					sess.createSQLQuery("ALTER TABLE A29" + tableExt + " add index A0000A29" + tableExt + "(A0000)").executeUpdate();
					sess.createSQLQuery("UPDATE a01"+tableExt+" AS t, A29" + tableExt + " AS k SET t.A2949 = k.A2949 WHERE k.a0000 = t.a0000 ").executeUpdate();
					
					//最高学历维护
					KingbsconfigBS.saveImpDetail("3", "1", "正在更新A01——最高学历",imprecordid);
					Statement smtA0801 = sess.connection().createStatement();
					String A0801TableSql = "create table A0801"+tableExt+"(A0000 varchar(120),A0801A varchar(60),ZGXLXX varchar(120)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
					smtA0801.execute(A0801TableSql.toUpperCase());
					smtA0801.executeUpdate("insert into A0801"+tableExt+" SELECT T.A0000,T.A0801A,T.ZGXLXX FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = V.A0000  THEN "
							+ "@rowNO + 1 ELSE 1 END) RN,V.A0000,V.A0834,V.A0801A,CONCAT(V.A0814,V.A0824) ZGXLXX,(@pre_parent_code := V.A0000) GRN FROM A08"+tableExt+"  V,"
							+ "(SELECT @rowNO := 0 ,@pre_parent_code := '')  XXX WHERE V.A0899 = 'true' AND V.A0834 = '1' ORDER BY V.A0000,V.A0834 DESC) T "
							+ "WHERE T.RN = 1");
					sess.createSQLQuery("ALTER TABLE A0801" + tableExt + " add index A0000A0801" + tableExt + "(A0000)").executeUpdate();
					
					Statement smt3 = sess.connection().createStatement();
					smt3.executeUpdate("UPDATE A01"+tableExt+" A01,A0801"+tableExt+" Y SET A01.ZGXL = Y.A0801A,A01.ZGXLXX = Y.ZGXLXX WHERE Y.A0000 = A01.A0000");
					smt3.execute("drop table A0801"+tableExt);
					smt3.close();
					
					//最高学位维护
					KingbsconfigBS.saveImpDetail("3", "1", "正在更新A01——最高学位",imprecordid);
					Statement smtA0901 = sess.connection().createStatement();
					String A0901TableSql = "create table A0901"+tableExt+"(A0000 varchar(120),newA0901A varchar(60),ZGXWXX varchar(120)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
					smtA0901.execute(A0901TableSql.toUpperCase());
					smtA0901.executeUpdate("insert into A0901"+tableExt+" SELECT T.A0000,group_concat(T.A0901A ORDER BY T.A0835) newA0901A,ZGXWXX FROM(SELECT V.A0000,"
							+ "V.A0835,V.A0901A,CONCAT(V.A0814, V.A0824) ZGXWXX FROM A08"+tableExt+" V WHERE V.A0899 = 'true' AND V.A0835 = '1' ORDER BY V.A0000,V.A0835 DESC) T "
							+ "GROUP BY T.A0000");
					sess.createSQLQuery("ALTER TABLE A0901"+tableExt+" add index A0000A0901" + tableExt + "(A0000)").executeUpdate();
					
					Statement smt4 = sess.connection().createStatement();
					smt4.executeUpdate("UPDATE A01"+tableExt+" A01,A0901"+tableExt+" Y SET A01.ZGXW = Y.newA0901A, A01.ZGXWXX = Y.ZGXWXX WHERE Y.A0000 = A01.A0000");
					smt4.execute("drop table A0901"+tableExt);
					smt4.close();
				}
				
				//照片复制，暂不考虑网络版
				KingbsconfigBS.saveImpDetail("3", "1", "正在提取照片",imprecordid);
				
				File file = new File(adress);
				if(file.exists()){
					String WINDOWS_remove_ALL="cmd /c xcopy /y "; //xcopy c:\123\ d:\123\
					String photo_path_temp = adress.replace("/", "\\");
					String photo_path = AppConfig.HZB_PATH + "/temp/upload/unzip/"+imprecordid +"/Photos/";
					
					int i = new CommandUtil().executeCommand(WINDOWS_remove_ALL + photo_path_temp + " " + (photo_path.replace("/", "\\")));
					System.out.println(WINDOWS_remove_ALL + photo_path_temp + " " + (photo_path.replace("/", "\\")));
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
					String table[] = { "A02","A06","A08","A11","A14","A15","A29","A30","A31",
							"A36","A37","A41","A53","A57","A60","A61","A62","A63","A64","A01" };
					for(int i=0;i<table.length;i++){
						sess.createSQLQuery("delete from "+table[i]+tableExt+" where A0000 in ("+fxzSql+")").executeUpdate();
					}
				}
				
				KingbsconfigBS.saveImpDetail("3","2","提取完成",imprecordid);
				imprecord.setProcessstatus("2");
				imprecord.setTotalnumber((t_n-n)+"");
				sess.update(imprecord);
				sess.flush();
				appendFileContent(logfilename, "提取完成"+"\n");
				//记录日志
				try {
					new LogUtil("451", "IMP_RECORD", "", "", "导入临时库", new ArrayList() ,userVo).start();
				} catch (Exception e) {
					try {
						new LogUtil().createLog("451", "IMP_RECORD", "", "", "导入临时库", new ArrayList());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				KingbsconfigBS.saveImpDetail(process_run ,"4","失败:"+e.getMessage(),imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}//结束提示信息。 
			try {
				if (no.equals("1")) {
					control.errStatus("1");
				} else {
					control.errStatus("2");
				}
			} catch (Exception e2) {
				e.printStackTrace();
			}
			try {
				KingbsGainBS.dealRollback(imprecordid, tableExt);;
			} catch (Exception e2) {
				e.printStackTrace();
			}
			try {
				UploadHelpFileServlet.delFolder(photo_file);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
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
}
