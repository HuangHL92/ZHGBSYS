package com.insigma.siis.local.pagemodel.dataverify;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.siis.local.business.datavaerify.UploadHzbFileBS;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.business.utils.NewSevenZipUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.util.SqlUtil;

public class ImpMakeHzbPart1Thread implements Runnable {
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
	
	public ImpMakeHzbPart1Thread(UserVO userVo, String houzhui, String imprecordid,
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
		String imprecordid = uuid; // �����¼id
		String process_run = "3"; // ����������
		UploadHzbFileBS uploadbs = new UploadHzbFileBS(); // ҵ����bs
		// ----------------------------------------------------------------------------------------
		String tableExt = control.getTableExt();
		Connection conn =  null;
		try {
			String tables[] = { "A01", "A02", "A06", "A08", "A11", "A14", "A15",
					"A29", "A30", "A31", "A36", "A37", "A41", "A53", "A57", "B01",
					"I_E", "B_E","A60", "A61", "A62", "A63", "A64", "A65" };

			/*String tables[] = { "A01", "A02", "A06", "A08", "A11", "A14", "A15",
				"A29", "A30", "A31", "A36", "A37", "A41", "A53", "A57", "B01",
				"I_E", "B_E","A60", "A61", "A62", "A63", "A64" , "A05" , "A68", "A69", "A71", "A99Z1" };*/
		
			int time = houzhui.equalsIgnoreCase("hzb")? 24 : 16;
		
			if(DBUtil.getDBType().equals(DBType.MYSQL)){
				while (true) {
					int number = 0;
					number = control.getNumber();
					if (number < time) {
						appendFileContent(logfilename,"==============================================="+ "\n");
						appendFileContent(logfilename, tables[number]+"���ݵ���"+ DateUtil.getTime()+ "\n"+ "neicun��"+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						KingbsconfigBS.saveImpDetail(process_run, "1", "��ȡ��"+ tables[number] + "���ݣ� ʣ��"+ (control.getNumber2()) + "�š�", imprecordid);
						CommonQueryBS.systemOut(tables[number]+ "���ݵ���"+ DateUtil.getTime() + "===>�������ݿ�ʼ");
						int t_n = uploadbs.saveData_SaxHander4_MakeHzb(houzhui.toLowerCase(), tables[number], imprecordid, uuid,from_file, B0111, deptid, impdeptid, tableExt);
						CommonQueryBS.systemOut(tables[number]+ "���ݵ���"+ DateUtil.getTime() + "===>�������ݽ���");
						appendFileContent(logfilename, tables[number]+ "���ݵ������"+ DateUtil.getTime()+ "\n"+ "neicun��"+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
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
						appendFileContent(logfilename, tables[number]+"���ݵ���"+ DateUtil.getTime()+ "\n"+ "neicun��"+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						KingbsconfigBS.saveImpDetail(process_run, "1", "��ȡ��"+ tables[number] + "���ݣ� ʣ��"+ (control.getNumber2()) + "�š�", imprecordid);
						CommonQueryBS.systemOut(tables[number]+ "���ݵ���"+ DateUtil.getTime() + "===>�������ݿ�ʼ");
						int t_n = uploadbs.saveData_SaxHander3_MakeHzb(houzhui.toLowerCase(), tables[number], imprecordid, uuid,from_file, B0111, deptid, impdeptid, tableExt);
						CommonQueryBS.systemOut(tables[number]+ "���ݵ���"+ DateUtil.getTime() + "===>�������ݽ���");
						appendFileContent(logfilename, tables[number]+ "���ݵ������"+ DateUtil.getTime()+ "\n"+ "neicun��"+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						appendFileContent(logfilename,"==============================================="+ "\n");
						control.addT_n(t_n);
					} else {
						break;
					}
				}
			}
		
			

			if (control.getStatus() == 2) {
				HBSession sess = HBUtil.getHBSession();
				conn = sess.connection();
				conn.setAutoCommit(true);
				
				if(houzhui.equalsIgnoreCase("hzb")){
					
					if(DBType.ORACLE == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.ORACLE)){
						
						KingbsconfigBS.saveImpDetail(process_run, "1", "������ȡA05��������ְ����",imprecordid);
						sess.createSQLQuery("insert into a05" + tableExt + " select a.a0000,sys_guid(),'0',a.a0221,a.a0243,a.a0245,null,'1','1','2' from a02" + tableExt + " a where a0200 in("
								+ " select a0200 from ("
								+ " select t.a0000,t.a0200,t.a0223,t.a0221,row_number()over(partition by t.a0000 order by t.a0221 asc,to_number(t.a0223) asc) a0221rn from ("
								+ "select a0000,a0200,a0223,A0221 from a02" + tableExt + " where a0255='1' and a0221 is not null) t ) x where x.a0221rn =1 )").executeUpdate();
						
						/*KingbsconfigBS.saveImpDetail(process_run, "1", "������ȡA05��������ְ��",imprecordid);
						sess.createSQLQuery("insert into a05" + tableExt + " SELECT a.a0000,sys_guid(),'1',a.a6501,a.a6502,a.a6508,NULL,'1','1','2' "
								+ "FROM A65"+tableExt+" a WHERE a.a6516 = '1'").executeUpdate();*/
						KingbsconfigBS.saveImpDetail(process_run, "1", "������ȡA05��������ְ��",imprecordid);
						sess.createSQLQuery("insert into a05" + tableExt + " SELECT a.a0000,sys_guid(),'1',a.a6501,a.a6502,a.a6508,NULL,'1','1','2' "
								+ "FROM (SELECT b.*,ROW_NUMBER () OVER (PARTITION BY b.a0000 ORDER BY b.a6501 ASC) rn FROM A65"+tableExt+" b ) a WHERE a.rn = 1").executeUpdate();
						
						//��ְ��
						KingbsconfigBS.saveImpDetail(process_run, "1", "���ڸ���A02������ְ��",imprecordid);
						sess.createSQLQuery("UPDATE A02"+tableExt+" a SET a.A0279 = '0'").executeUpdate();
						sess.createSQLQuery("UPDATE A02"+tableExt+" a SET a.A0279 = '1' WHERE a.A0200 IN ("
							+ "SELECT T.A0200 FROM(SELECT ROW_NUMBER() OVER(PARTITION BY v.A0000 ORDER BY v.A0221,v.A0219,v.A0223) rn,v.A0200 "
							+ "FROM A02"+tableExt+" v WHERE v.A0255 = '1') T WHERE T.rn = 1)").executeUpdate();
						
						//��������ְ��ʱ��
						KingbsconfigBS.saveImpDetail(process_run, "1", "���ڸ���A01��������ְ��ʱ��",imprecordid);
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
						KingbsconfigBS.saveImpDetail(process_run, "1", "���ڸ���A01�����Ƿ�������깤������",imprecordid);
						sess.createSQLQuery("UPDATE a01"+tableExt+" t set t.A0197 = '0'").executeUpdate();
						sess.createSQLQuery("UPDATE a01"+tableExt+" t set t.A0197 = '1' where a0194 >=24").executeUpdate();
						
						KingbsconfigBS.saveImpDetail(process_run, "1", "���ڸ���A01������ְ����",imprecordid);
						sess.createSQLQuery("Create index A0000A05" + tableExt + " on A05" + tableExt + "(A0000)").executeUpdate();
						sess.createSQLQuery("UPDATE A01"+tableExt+" T SET (T.A0221,T.A0288) = (SELECT c.a0501b,c.a0504 FROM a05"+tableExt+" c WHERE c.a0000 = T .a0000 AND c.A0531 = '0') "
								+ "WHERE 1 = 1 AND EXISTS (SELECT 1 FROM a05"+tableExt+" c WHERE c.a0000 = T .a0000 AND c.A0531 = '0')").executeUpdate();
						KingbsconfigBS.saveImpDetail(process_run, "1", "���ڸ���A01��������Ա�Ǽ�ʱ��",imprecordid);
						sess.createSQLQuery("Create index A0000A29" + tableExt + " on A29" + tableExt + "(A0000)").executeUpdate();
						sess.createSQLQuery("UPDATE A01"+tableExt+" t set t.A2949 = (select c.a2949 from a29"+tableExt+" c where c.a0000 = t.a0000) where 1=1 and exists (select 1 from a29"+tableExt+" c where c.a0000 = t.a0000)").executeUpdate();
						
						//���ѧ��ά��
						KingbsconfigBS.saveImpDetail(process_run, "1", "���ڸ���A01�������ѧ��",imprecordid);
						String A0801TableSql = "create table A0801"+tableExt+"(A0000 varchar2(120),A0801A varchar2(60),ZGXLXX varchar2(120))";
						sess.createSQLQuery(A0801TableSql.toUpperCase()).executeUpdate();
						sess.createSQLQuery("insert into A0801"+tableExt+" SELECT T .A0000,T .A0801A,T .ZGXLXX FROM (SELECT ROW_NUMBER () OVER (PARTITION BY v.A0000 "
								+ "ORDER BY v.A0834) rn,v.A0000,v.A0801A,CONCAT (v.A0814, v.A0824) ZGXLXX FROM A08"+tableExt+" v WHERE v.A0899 = 'true' AND v.A0834 = '1') T "
								+ "WHERE T .rn = 1 ").executeUpdate();
						sess.createSQLQuery("Create index AA0801" + tableExt + " on A0801" + tableExt + "(A0000)").executeUpdate();
						sess.createSQLQuery("UPDATE A01"+tableExt+" A01 SET (A01.ZGXL, A01.ZGXLXX) = (SELECT T .A0801A,T .ZGXLXX FROM A0801"+tableExt+" T "
								+ "WHERE T.A0000 = A01.A0000) WHERE 1 = 1 AND EXISTS (SELECT 1 FROM A0801"+tableExt+" T WHERE T.A0000 = A01.A0000)").executeUpdate();
						sess.createSQLQuery("drop table A0801"+tableExt).executeUpdate();
						//���ѧλά��
						KingbsconfigBS.saveImpDetail(process_run, "1", "���ڸ���A01�������ѧλ",imprecordid);
						String A0901TableSql = "create table A0901"+tableExt+"(A0000 varchar2(120),newA0901A varchar2(60),ZGXWXX varchar2(120))";
						sess.createSQLQuery(A0901TableSql.toUpperCase()).executeUpdate();
						sess.createSQLQuery("insert into A0901"+tableExt+" SELECT W.A0000,W.NEWA0901A,W.ZGXWXX FROM (SELECT ROW_NUMBER () OVER (PARTITION BY "
								+ "Y.A0000 ORDER BY Y.A0000) rn,Y.A0000,Y.NEWA0901A,Y.ZGXWXX FROM (SELECT T .A0000,WM_CONCAT (T .A0901A) OVER (PARTITION BY T .A0000) "
								+ "newA0901A,ZGXWXX FROM (SELECT v.A0000,v.A0901A,CONCAT (v.A0814, v.A0824) ZGXWXX FROM A08"+tableExt+" v WHERE v.A0899 = 'true' AND v.A0835 = '1') "
								+ "T) Y) W WHERE W.RN = 1 ").executeUpdate();
						sess.createSQLQuery("Create index AA0901" + tableExt + " on A0901" + tableExt + "(A0000)").executeUpdate();
						sess.createSQLQuery("UPDATE A01"+tableExt+" A01 SET (A01.ZGXW,A01.ZGXWXX) = (SELECT W.NEWA0901A,W.ZGXWXX FROM A0901"+tableExt+" "
								+ "W WHERE W.A0000 = A01.A0000) WHERE 1 = 1 AND EXISTS (SELECT 1 FROM  A0901"+tableExt+" W WHERE W.A0000 = A01.A0000)").executeUpdate();
						sess.createSQLQuery("drop table A0901"+tableExt).executeUpdate();
						
						////��ְ��ά��
						/*KingbsconfigBS.saveImpDetail(process_run, "1", "���ڸ���A01������ְ��",imprecordid);
						sess.createSQLQuery("Create index A0000A65" + tableExt + " on A65" + tableExt + "(A0000)").executeUpdate();
						sess.createSQLQuery("UPDATE A01"+tableExt+" a SET (a.A0192E,a.a0192C) = (SELECT b.a6501,b.a6502 FROM A65"+tableExt+" b WHERE a.A0000 = b.a0000 AND b.a6516 = '1')"
								+ " WHERE 1 = 1 AND EXISTS (SELECT 1 FROM A65"+tableExt+" b WHERE a.A0000 = b.a0000 AND b.a6516 = '1')").executeUpdate();*/
						KingbsconfigBS.saveImpDetail(process_run, "1", "���ڸ���A01������ְ��",imprecordid);
						sess.createSQLQuery("Create index A0000A65" + tableExt + " on A65" + tableExt + "(A0000)").executeUpdate();
						sess.createSQLQuery("UPDATE A01"+tableExt+" a SET (a.A0192E,a.a0192C) = (SELECT w.a6501,w.a6502 FROM(SELECT b.a0000,b.a6501,b.a6502,ROW_NUMBER () OVER (PARTITION BY b.a0000 ORDER BY b.a6501 ASC) rn FROM A65"+tableExt+" b) w WHERE w.RN = 1 AND a.A0000 = w.a0000 )"
								+ " WHERE 1 = 1 AND EXISTS (SELECT 1 FROM(SELECT b.a0000,b.a6501,b.a6502,ROW_NUMBER () OVER (PARTITION BY b.a0000 ORDER BY b.a6501 ASC) rn FROM A65"+tableExt+" b) w WHERE w.RN = 1 AND a.A0000 = w.a0000)").executeUpdate();
						
						//��λ����
						/*KingbsconfigBS.saveImpDetail(process_run, "1", "���ڸ���B01������λ����",imprecordid);
						sess.createSQLQuery("Create index B0111B01" + tableExt + " on B01" + tableExt + "(B0111)").executeUpdate();
						sess.createSQLQuery("UPDATE A02"+tableExt+" t set t.A0201A = (select c.b0101 from b01"+tableExt+" c where c.b0111 = t.a0201b) where 1=1 and exists (select 1 from b01"+tableExt+" c where c.b0111 = t.a0201b)").executeUpdate();*/
						
						//�� Ҫ��ɾ���׸ĺ��ְ����
						KingbsconfigBS.saveImpDetail(process_run, "1", "����ɾ�������׸ĺ�ְ����",imprecordid);
						sess.createSQLQuery("UPDATE a01"+tableExt+" SET A0221 = null,A0288 = null WHERE A0148 IS NULL").executeUpdate();
						sess.createSQLQuery("DELETE FROM a05"+tableExt+" a5 WHERE 1 = 1 AND EXISTS (SELECT 1 FROM "
								+ "(SELECT a.A0000 FROM a01"+tableExt+" a WHERE a.A0148 IS NULL) X "
								+ "WHERE a5.A0000 = X.A0000 AND a5.A0531 = '0')").executeUpdate();
						
						//���׸ĺ�ְ��  �쵼ְ�񡢳�Ա����쵼��Ա��ѡ�����÷�ʽ���Ƹ���� ����Ϊ��
						KingbsconfigBS.saveImpDetail(process_run, "1", "���ڸ���A02����ְ����Ϣ",imprecordid);
						sess.createSQLQuery("UPDATE A02"+tableExt+" a02 SET a02.A0219 = NULL,a02.A0201E = NULL,a02.A0201D = '0',"
								+ "a02.A0247 = NULL,a02.A0251B = NULL WHERE EXISTS (SELECT 1 FROM A65"+tableExt+" a65 WHERE "
								+ "a02.A0200 = a65.A0200 AND a02.A0215A IS NULL)").executeUpdate();
						
						//����ְ����ְ�����ơ���ְʱ�䡢��ְ�ĺ�
						KingbsconfigBS.saveImpDetail(process_run, "1", "��������A02����ְ����Ϣ",imprecordid);
						sess.createSQLQuery("UPDATE A02"+tableExt+" a02 SET (a02.A0215A,a02.A0243,a02.A0245) = ("
									+"SELECT CASE WHEN a65.a6501 = '11' THEN 'һ��Ѳ��Ա' WHEN a65.a6501 = '12' THEN '����Ѳ��Ա'"
									+"WHEN a65.a6501 = '13' THEN 'һ������Ա' WHEN a65.a6501 = '14' THEN '��������Ա'"
									+"WHEN a65.a6501 = '15' THEN '��������Ա' WHEN a65.a6501 = '16' THEN '�ļ�����Ա'"
									+"WHEN a65.a6501 = '17' THEN 'һ�����ο�Ա' WHEN a65.a6501 = '18' THEN '�������ο�Ա'"
									+"WHEN a65.a6501 = '19' THEN '�������ο�Ա' WHEN a65.a6501 = '1A' THEN '�ļ����ο�Ա'"
									+"WHEN a65.a6501 = '1B' THEN 'һ����Ա' WHEN a65.a6501 = '1C' THEN '������Ա'"
									+"ELSE '' END AS newA6501,a65.A6502,a65.A6508 FROM A65"+tableExt+" a65 WHERE a02.A0200 = a65.A0200 AND a02.A0215A IS NULL"
									+") WHERE 1 = 1 AND EXISTS (SELECT 1 FROM A65"+tableExt+" a65 WHERE a02.A0200 = a65.A0200 AND a02.A0215A IS NULL)").executeUpdate();
						
					}
					if(DBType.MYSQL == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.MYSQL)){ 
						
						KingbsconfigBS.saveImpDetail(process_run, "1", "������ȡA05������������ְ����",imprecordid);
						Statement smt = sess.connection().createStatement();
						smt.executeUpdate("insert into a05" + tableExt + " SELECT a.a0000,uuid(),'0',a.a0221,a.a0288,a.a0245,NULL,'1','1','2' "
								+ "FROM a02" + tableExt + " a WHERE a0200 IN ( SELECT T.A0200 FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = V.A0000 "
								+ "THEN @rowNO + 1 ELSE 1 END ) RN, V.A0000, V.A0200, V.A0223, V.A0221, "
								+ "(@pre_parent_code := V.A0000) GRN FROM A02" + tableExt + " V,(SELECT @rowNO := 0 ,@pre_parent_code := '') XXX "
								+ "WHERE V.A0255 = '1' AND (V.A0221 IS NOT NULL OR V.A0221 <> '') ORDER BY V.A0000,V.A0221,V.A0223) T WHERE T.RN = 1)");
						smt.close();
						
						KingbsconfigBS.saveImpDetail(process_run, "1", "������ȡA05������������ְ��",imprecordid); 
						/*sess.createSQLQuery("insert into a05" + tableExt + " SELECT a.a0000,uuid(),'1',a.a6501,a.a6502,a.a6508,NULL,'1','1','2' "
								+ "FROM A65"+tableExt+" a WHERE a.a6516 = '1'").executeUpdate();*/
						Statement smt5 = sess.connection().createStatement();
						smt5.executeUpdate("insert into a05" + tableExt + " SELECT a.a0000,uuid(),'1',a.a6501,a.a6502,a.a6508,"
								+ "NULL,'1','1','2' FROM  (SELECT (@rowNO := CASE WHEN @pre_parent_code = b.A0000 "
								+ "THEN @rowNO + 1 ELSE 1 END ) RN,b.*,(@pre_parent_code := b.A0000) GRN FROM "
								+ "a65" + tableExt + " b,(SELECT @rowNO := 0,@pre_parent_code := '') XXX ORDER "
								+ "BY b.a0000,b.a6501 ASC) a WHERE a.RN = 1 ");
						smt5.close();
						
						//��ְ��
						KingbsconfigBS.saveImpDetail(process_run, "1", "���ڸ���A02������ְ��",imprecordid);
						sess.createSQLQuery("UPDATE A02"+tableExt+" a SET a.A0279 = '0'").executeUpdate();
						Statement smt2 = sess.connection().createStatement();
						smt2.executeUpdate("UPDATE A02"+tableExt+" A SET A.A0279 = '1' WHERE A.A0200 IN (SELECT T.A0200 FROM(SELECT (@rowNO := CASE WHEN @pre_parent_code = V.A0000 THEN @rowNO + 1 ELSE 1 END ) "
								+ " RN, V.A0200, V.A0000, (@pre_parent_code := V.A0000) GRN FROM A02"+tableExt+" V,(SELECT @rowNO := 0,@pre_parent_code := '') XXX WHERE V.A0255 = '1' "
								+ "ORDER BY V.A0000, V.A0221, V.A0219, V.A0223 ) T WHERE T.RN = 1)");
						smt2.close();
						
						//��������ְ��ʱ��
						KingbsconfigBS.saveImpDetail(process_run, "1", "���ڸ���A01��������ְ��ʱ��",imprecordid);
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
						KingbsconfigBS.saveImpDetail(process_run, "1", "���ڸ���A01�����Ƿ�������깤������",imprecordid);
						sess.createSQLQuery("UPDATE a01"+tableExt+" t set t.A0197 = '0'").executeUpdate();
						sess.createSQLQuery("UPDATE a01"+tableExt+" t set t.A0197 = '1' where a0194 >=24").executeUpdate();
						
						//sess.createSQLQuery("ALTER TABLE A01" + tableExt + " add index A2949A01" + tableExt + "(A2949)").executeUpdate();
						//sess.createSQLQuery("ALTER TABLE A29" + tableExt + " add index A2949A29" + tableExt + "(A2949)").executeUpdate();
						KingbsconfigBS.saveImpDetail(process_run, "1", "���ڸ���A01������ְ����",imprecordid);
						/*sess.createSQLQuery("UPDATE A01"+tableExt+" t set t.A0221 = (select max(c.a0501b) from a05"+tableExt+" c where c.a0000 = t.a0000),"
								+ "t.A0288 = (select min(c.a0504) from a05"+tableExt+" c where c.a0000 = t.a0000) where  1=1 and exists (select 1 from a05"+tableExt+" c where c.a0000 = t.a0000)").executeUpdate();*/
						sess.createSQLQuery("ALTER TABLE A05" + tableExt + " add index A0000A05" + tableExt + "(A0000)").executeUpdate();
						sess.createSQLQuery("UPDATE A01"+tableExt+" t,A05"+tableExt+" c SET t.A0221 = c.a0501b,t.A0288 = c.a0504 WHERE c.a0000 = t.a0000 AND c.a0531 = '0'").executeUpdate();
						
						KingbsconfigBS.saveImpDetail(process_run, "1", "���ڸ���A01��������Ա�Ǽ�ʱ��",imprecordid);
						sess.createSQLQuery("ALTER TABLE A29" + tableExt + " add index A0000A29" + tableExt + "(A0000)").executeUpdate();
						sess.createSQLQuery("UPDATE a01"+tableExt+" AS t, A29" + tableExt + " AS k SET t.A2949 = k.A2949 WHERE k.a0000 = t.a0000 ").executeUpdate();
						
						//���ѧ��ά��
						KingbsconfigBS.saveImpDetail(process_run, "1", "���ڸ���A01�������ѧ��",imprecordid);
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
						
						//���ѧλά��
						KingbsconfigBS.saveImpDetail(process_run, "1", "���ڸ���A01�������ѧλ",imprecordid);
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
						
					    //��ְ��ά��
						KingbsconfigBS.saveImpDetail(process_run, "1", "���ڸ���A01������ְ��",imprecordid);
						Statement smt6 = sess.connection().createStatement();
						
						smt6.executeUpdate("ALTER TABLE A65" + tableExt + " add index A0000A65" + tableExt + "(A0000)");
						/*sess.createSQLQuery("UPDATE A01"+tableExt+" a,A65"+tableExt+" b SET a.A0192E = b.a6501,a.a0192C = b.a6502 WHERE a.A0000 = b.a0000 AND b.a6516 = '1'").executeUpdate();*/
						smt6.executeUpdate("UPDATE A01"+tableExt+" a,(SELECT a.a0000,a.a6501,a.a6502 FROM  (SELECT (@rowNO := CASE WHEN "
								+ "@pre_parent_code = b.A0000 THEN @rowNO + 1 ELSE 1 END ) RN,b.*,(@pre_parent_code := b.A0000) GRN "
								+ "FROM a65"+tableExt+" b,(SELECT @rowNO := 0,@pre_parent_code := '') XXX ORDER BY b.a0000,b.a6501 ASC) "
								+ "a WHERE a.RN = 1) w SET a.A0192E = w.a6501,a.a0192C = w.a6502 WHERE a.A0000 = w.a0000");
						smt6.close();
						//��λ����
						/*KingbsconfigBS.saveImpDetail(process_run, "1", "���ڸ���B01������λ����",imprecordid);
						sess.createSQLQuery("ALTER TABLE B01" + tableExt + " add index B0111B01" + tableExt + "(B0111)").executeUpdate();
						sess.createSQLQuery("UPDATE A02"+tableExt+" AS t, b01" + tableExt + " AS k SET t.A0201A = k.b0101 WHERE t.a0201b = k.b0111").executeUpdate();*/
						
						//�� Ҫ��ɾ���׸ĺ��ְ����
						KingbsconfigBS.saveImpDetail(process_run, "1", "����ɾ�������׸ĺ�ְ����",imprecordid);
						sess.createSQLQuery("UPDATE a01"+tableExt+" SET A0221 = null,A0288 = null WHERE (A0148 IS NULL OR A0148 = '')").executeUpdate();
						sess.createSQLQuery("DELETE a5 FROM a05"+tableExt+" a5 WHERE 1 = 1 AND EXISTS (SELECT 1 FROM "
								+ "(SELECT a.A0000 FROM a01"+tableExt+" a WHERE (a.A0148 IS NULL OR a.A0148 = '')) X "
								+ "WHERE X.A0000 = a5.A0000 AND a5.A0531 = '0')").executeUpdate();
						
						//���׸ĺ�ְ��  �쵼ְ�񡢳�Ա����쵼��Ա��ѡ�����÷�ʽ���Ƹ���� ����Ϊ��
						KingbsconfigBS.saveImpDetail(process_run, "1", "���ڸ���A02����ְ����Ϣ",imprecordid);
						sess.createSQLQuery("UPDATE A02"+tableExt+" a02,a65"+tableExt+" a65 SET a02.A0219 = NULL,a02.A0201E = NULL,a02.A0201D = '0',"
								+ "a02.A0247 = NULL,a02.A0251B = NULL WHERE a02.A0200 = a65.A0200 AND (a02.A0215A IS NULL OR a02.A0215A = '')").executeUpdate();
						
						//����ְ����ְ������
						KingbsconfigBS.saveImpDetail(process_run, "1", "��������A02����ְ����Ϣ",imprecordid);
						sess.createSQLQuery("UPDATE a02"+tableExt+" a02,a65"+tableExt+" a65 SET a02.A0215A = ("
									+"SELECT CASE WHEN a65.a6501 = '11' THEN 'һ��Ѳ��Ա' WHEN a65.a6501 = '12' THEN '����Ѳ��Ա'"
									+"WHEN a65.a6501 = '13' THEN 'һ������Ա' WHEN a65.a6501 = '14' THEN '��������Ա'"
									+"WHEN a65.a6501 = '15' THEN '��������Ա' WHEN a65.a6501 = '16' THEN '�ļ�����Ա'"
									+"WHEN a65.a6501 = '17' THEN 'һ�����ο�Ա' WHEN a65.a6501 = '18' THEN '�������ο�Ա'"
									+"WHEN a65.a6501 = '19' THEN '�������ο�Ա' WHEN a65.a6501 = '1A' THEN '�ļ����ο�Ա'"
									+"WHEN a65.a6501 = '1B' THEN 'һ����Ա' WHEN a65.a6501 = '1C' THEN '������Ա' ELSE '' END AS newA6501)"
									+",a02.A0243 = a65.A6502,a02.A0245 = a65.A6508 "
									+"WHERE a02.A0200 = a65.A0200 AND (a02.A0215A IS NULL OR a02.A0215A = '')").executeUpdate();
					}
				}
				
				Integer n = 0;
				
				//����ְ����
				if("false".equals(fxz)){
					String fxzSql = "SELECT T.a0000 FROM ( SELECT a.a0000 FROM a01"+tableExt+" a WHERE a.a0163 <> '1' ) T";
					
					String numSql = "SELECT count(1) FROM ( SELECT a.a0000 FROM a01"+tableExt+" a WHERE a.a0163 <> '1' ) T";
					Object obj = sess.createSQLQuery(numSql).uniqueResult();
					if(obj!=null){
						n = Integer.parseInt(""+obj);
					}

					for(int i=0;i<tables.length;i++){
						//System.out.println("delete from "+tables[i]+tableExt+" where A0000 in ("+fxzSql+")");
						if(!tables[i].equals("B01") && !tables[i].equals("B_E")){
							sess.createSQLQuery("delete from "+tables[i]+tableExt+" where A0000 in ("+fxzSql+")").executeUpdate();
						}
						
					}
				
				}
				
				conn.createStatement().execute("update imp_record set TOTAL_NUMBER='"
						+ (control.getT_n() - n)
						+ "',PROCESS_STATUS='2' where IMP_RECORD_ID='"
						+ imprecordid + "'");
				conn.close();
				CommonQueryBS.systemOut("END INSERT---------" + DateUtil.getTime());
				appendFileContent(logfilename, "�������" + "\n");
				KingbsconfigBS.saveImpDetail(process_run, "2", "��ȡ���",
						imprecordid);
				try {
					if (houzhui.equalsIgnoreCase("hzb")) {
						new LogUtil("421", "IMP_RECORD", "", "", "������ʱ��",
								new ArrayList(), userVo).start();
					} else {
						new LogUtil("422", "IMP_RECORD", "", "", "������ʱ��",
								new ArrayList(), userVo).start();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					this.delFolder(control.getUnzip() + "Table/");
					this.delFolder(control.getUnzip() + "gwyinfo.xml");
					this.delFolder(control.getFilePath());
					if(!control.getFilePath().substring(control.getFilePath().lastIndexOf(".")+1).equalsIgnoreCase("zip")){
						this.delFolder(control.getUpload_file());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				appendFileContent(logfilename, "ɾ�������ļ�" + DateUtil.getTime()
						+ "\n");
				CommonQueryBS.systemOut("delete file END---------"
						+ DateUtil.getTime());
			}
		} catch (AppException e) {
			e.printStackTrace();
			uploadbs.rollbackImpTableMake(imprecordid, tableExt); 
			try {
				KingbsconfigBS.saveImpDetail(process_run, "4", "ʧ��:" + e.getMessage(), imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}// ������ʾ��Ϣ��
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
				KingbsconfigBS.saveImpDetail(process_run, "4", "ʧ��:"
						+ e.getMessage(), imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}// ������ʾ��Ϣ��
			if (no.equals("1")) {
				control.errStatus("1");
			} else {
				control.errStatus("2");
			}
//			uploadbs.rollbackImp(imprecordid);
			uploadbs.rollbackImpTableMake(imprecordid, tableExt); 
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
			CommonQueryBS.systemOut(DateUtil.getTime()+"--->��ѯ��"+"�쳣ʱռ���ڴ棺"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
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
			// ��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ɾ���ļ���
	// param folderPath �ļ�����������·��
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // ɾ����������������
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // ɾ�����ļ���
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ɾ��ָ���ļ����������ļ�
	// param path �ļ�����������·��
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
				delAllFile(path + "/" + tempList[i]);// ��ɾ���ļ���������ļ�
				delFolder(path + "/" + tempList[i]);// ��ɾ�����ļ���
				flag = true;
			}
		}
		return flag;
	}
}