package com.insigma.siis.local.util;

import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;

public class TYGSsqlUtil { 

	public static final String A57 = null;


	public static final String B01ZIP = null;


	public static final String A36 = null;


	public static final String A15 = null;


	public static final String A14 = null;


	public static final String A05 = null;


	public static final String A06 = null;


	public static final String A02 = null;


	public static final String A99Z1 = null;


	public static final String A30 = null;


	public static String A01 = "A0000,A0101,A0102,A0104,A0107,A0111A,A0114A,A0115A,A0117,A0128,A0134,A0140,A0141,A0144,A3921,"
							+ "A3927,A0160,A0163,A0165,A0184,A0187A,A0192,A0192A,A0221,A0288,A0192E,"
							+ "A0192C,A0196,A0197,A0195,A1701,A14Z101,A15Z101,A0120,A0121,A0122,A2949,A0180,QRZXL,QRZXLXX,QRZXW,QRZXWXX,ZZXL,ZZXLXX,ZZXW,ZZXWXX,ZGXL,ZGXLXX,ZGXW,ZGXWXX,TCSJSHOW,TCFSSHOW";
	
	
	public static String A01zip= "A0000,A0101,A0104,A0107,A0111A,A0114A,A0115A,A0117,A0128,A0134,A0140,A0141,A0144,A3921,"
			+ "A3927,A0160,A0163,A0165,A0184,A0187A,A0192,A0192A,A0123,A0221,A0288,A0192E,"
			+ "A0192C,A0196,A0197,A0195,A1701,A14Z101,A15Z101,A15Z191,A0120,A0121,A0122,A2949,A0180";
	
	public static String A02zip = "A0000,A0201A,A0201B,A0201D,A0201E,A0215A,A0219,A0223,A0225,A0243,A0245,A0247,"
							+ "A0251B,A0255,A0265,A0267,A0272,A0281,A0279";
	
	public static String A05zip = "A0000,A0531,A0501B,A0504,A0511,A0517,A0524,A0526,A0528B,A0530,A0532";
	
	public static String A06zip = "A0000,A0601,A0602,A0604,A0607,A0611,A0699";
	
	public static String A08 = "A0000,A0801A,A0801B,A0901A,A0901B,A0804,A0807,A0904,A0814,A0824,A0827,A0834,A0835,A0837,A0811,A0899";
	
	
	public static String A08zip = "A0000,A0801A,A0801B,A0901A,A0901B,A0804,A0807,A0904,A0814,A0824,A0827,A0837,A0811,A0831,A0832,"
									+"A0834,A0835,A0838,A0839,A0899";
	
	public static String A14zip = "A0000,A1404A,A1404B,A1407,A1411A,A1414,A1415,A1424,A1428";
	
	public static String A15zip = "A0000,A1517,A1521";
	
	public static String A30zip = "A0000,A3001,A3004,A3007A,A3038";
	
	public static String A33zip = "A0000,A3310,A3321,A3321C,A3321D,A3322,A3323,A3331,A3332,A3333,A3350,A3360,A3361,A3362,A3371,"+
								  "A3372,A3381,A3382,A3385";
	
	public static String A36zip = "A0000,A3601,A3604A,A3607,A3627,A3611,SORTID";
	
	public static String A57zip = "A0000,A5714";
	
	public static String A99Z1zip = "A0000,A99Z101,A99Z102,A99Z103,A99Z104,A99Z191,A99Z195";
	
	public static String B01zip = "B0101,B0104,B0114,B0111,B0117,B0121,B0124,B0127,B0131,B0194,B0227,B0232,B0233,B0236,B0234,"
								+"B0150,B0183,B0185,B0164,B0167,B0268,B0269,B0238,B0239,B0180,SORTID";
	//暂时弃用
	public static String B01 = "B0101,B0104,B0107,B0114,B0111,B0117,B0121,B0124,B0127,B0131,B0194,B0227,B0232,B0233,B0236,B0234,B0238,B0239,B0150,B0183,B0185,SORTID";
	
	/*----------------------------------------------------------------------------------------------------------------*/
	
	public static String A01Mysql= "ID,A0101,A0104,A0107,A0111A,A0114A,A0115A,A0117,A0128,A0134,A0140,A0141,A0144,A3921,A3927,A0160,"
			+ "A0163,A0165,A0184,A0187A,A0192,A0192A,A0221,A0288,A0192E,A0192C,A0196,A0197,A0195,A1701,A14Z101,A15Z101,A0120,A0121,"
			+ "A0122,A2949,A0180";
	
	public static String A02Mysql = "ID,SID,A0201A,A0201B,A0201D,A0201E,A0215A,A0219,A0223,A0225,A0243,A0245,A0247,A0251B,A0255,A0265,"
			+ "A0267,A0272,A0281,A0279";

	public static String A05Mysql = "ID,SID,A0531,A0501B,A0504,A0511,A0517,A0524";

	public static String A06Mysql = "ID,SID,A0601,A0602,A0604,A0607,A0611,A0699";

	public static String A08Mysql = "ID,SID,A0801A,A0801B,A0901A,A0901B,A0804,A0807,A0904,A0814,A0824,A0827,A0837,A0811,A0898";

	public static String A14Mysql = "ID,SID,A1404A,A1404B,A1407,A1411A,A1414,A1415,A1424,A1428";

	public static String A15Mysql = "ID,SID,A1517,A1521";

	public static String A36Mysql = "ID,SID,A3601,A3604A,A3607,A3627,A3611,A3699";

	public static String A57Mysql = "ID,A5714";

	public static String B01Mysql = "ID,SID,B0101,B0104,B0114,B0117,B0124,B0127,B0131,B0194,B0227,B0232,B0233,B0236,B0234,B0238,B0239,B0150,B0183,B0185,B0180,SORTID";

	public static String A99Z1Mysql = "ID,A99Z101,A99Z102,A99Z103,A99Z104,A99Z191";
	
/*----------------------------------------------------------------------------------------------------------------*/
	
	public static String A01MysqlHZB= "A0000,A0101,A0104,A0107,A0111A,A0114A,A0115A,A0117,A0128,A0134,A0140,A0141,A0144,A3921,A3927,A0160,"
			+ "A0163,A0165,A0184,A0187A,A0192,A0192A,A0221,A0288,A0192E,A0192C,A0196,A0197,A0195,A1701,A14Z101,A15Z101,A0120,A0121,"
			+ "A0122,A2949,A0180,A0123,A15Z191";
	
	public static String A02MysqlHZB = "A0200,A0000,A0201A,A0201B,A0201D,A0201E,A0215A,A0219,A0223,A0225,A0243,A0245,A0247,A0251B,A0255,A0265,"
			+ "A0267,A0272,A0281,A0279";

	public static String A05MysqlHZB = "A0500,A0000,A0531,A0501B,A0504,A0511,A0517,A0524,A0526,A0528B,A0530,A0532";

	public static String A06MysqlHZB = "A0600,A0000,A0601,A0602,A0604,A0607,A0611,A0699";

	public static String A08MysqlHZB = "A0800,A0000,A0801A,A0801B,A0901A,A0901B,A0804,A0807,A0904,A0814,A0824,A0827,A0837,A0811,"
									+"A0831,A0832,A0834,A0835,A0838,A0839,A0899";

	public static String A14MysqlHZB = "A1400,A0000,A1404A,A1404B,A1407,A1411A,A1414,A1415,A1424,A1428";

	public static String A15MysqlHZB = "A1500,A0000,A1517,A1521";

	public static String A36MysqlHZB = "A3600,A0000,A3601,A3604A,A3607,A3627,A3611,SORTID";

	public static String A57MysqlHZB = "A0000,A5714";

	public static String A65MysqlHZB = "A6500,A0000,A6501,A6502,A6503,A6504,A6505,A6506,A6507,A6508,TGPC,A0200,A6509,A6510,A6511,A6512,A6513,A6514,A6511A,A6515,A6516,A6526,A6511B,"+
										"A6511C,A6517A,A6517B,A6517C,A0200_OLD,A0500_1,A0500_2,A0500,SORTID";
	
	public static String B01MysqlHZB = "B0111,B0121,B0101,B0104,B0114,B0117,B0124,B0127,B0131,B0194,B0227,B0232,B0233,B0236,B0234,"
			+"B0238,B0239,B0150,B0183,B0185,B0180,B0164,B0167,B0268,B0269,SORTID";

	public static String A99Z1MysqlHZB = "A0000,A99Z101,A99Z102,A99Z103,A99Z104,A99Z191,A99Z195";
	
	/*----------------------------------------------------------------------------------------------------------------*/
	
	public static String A01EXCEL = "A0000,A0101,A0184,A0104,A0107,A0111A,A0114A,A0115A,A0117,A0128,A0134,A0140,A0141"
			+ ",A0144,A3921,A3927,A0160,A0163,A0165,A0123,A0187A,A0192A,A0192,A0221,A0288,A0192E"
			+ ",A0192C,A0196,A0197,A0195,A1701,A14Z101,A15Z101,A15Z191,A0120,A0121,A0122,A2949,A0180,STATUS";
	
	public static String A02EXCEL = "A0000,A0200,A0201A,A0201B,A0201D,A0201E,A0215A,A0219,A0223,A0225,A0243,A0245,A0247,A0251B,"
			+ "A0255,A0265,A0267,A0272,A0281,A0279";
	
	public static String A05EXCEL = "A0000,A0500,A0531,A0501B,A0504,A0511,A0517,A0528B,A0530,A0532,A0526,A0524";
	
	public static String A06EXCEL = "A0000,A0600,A0601,A0602,A0604,A0607,A0611,A0699";
	
	public static String A08EXCEL = "A0000,A0800,A0801A,A0801B,A0901A,A0901B,A0804,A0807,A0904,A0814,A0824,A0827,A0837,A0811,A0899";
	
	public static String A14EXCEL = "A0000,A1400,A1404A,A1404B,A1407,A1411A,A1414,A1415,A1424,A1428";
	
	public static String A15EXCEL = "A0000,A1500,A1517,A1521";
	
	public static String A36EXCEL = "A0000,A3600,A3601,A3604A,A3607,A3627,A3611,SORTID";
	
	public static String A57EXCEL = "";
	
	public static String B01EXCEL = "B0111,B0121,B0101,B0104,B0114,B0117,B0124,B0127,B0131,B0194,B0227,B0232,B0233,B0236"
			+ ",B0234,B0164,B0167,B0268,B0269,B0238,B0239,B0150,B0183,B0185,B0180,SORTID";
	
	public static String A99Z1EXCEL = "A0000,A99Z100,A99Z101,A99Z102,A99Z103,A99Z104,A99Z191,A99Z195";
	public static String A30EXCEL = "A0000,A3001,A3004,A3007A,A3038";
	public static String A33EXCEL = "A0000,A3300,A3310,A3321,A3321C,A3321D,A3322,A3323,A3331,A3332,A3333,A3350,A3360,A3361,A3362,A3371,A3372,A3381,A3382,A3385";
	
	/*--------------------------------------------------------------------------------------------*/
	public static String OnlyA33zip = "A0101,A0134,A0184,A3310,A3321,A3321C,A3321D,A3322,A3323,A3331,A3332,A3333,A3350,A3360,A3361,A3362,A3371,"+
			  "A3372,A3381,A3382,A3385";
	public static void updateA08(String tableExt) throws Exception{
		HBSession sess = HBUtil.getHBSession();
		
		if(DBType.MYSQL == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.MYSQL)){
			sess.createSQLQuery("UPDATE A01" + tableExt + " b,( SELECT T.A0000,group_concat(T.a0243 ORDER BY T.a0223) newa0243 FROM(SELECT a.A0000,"
					+ "CONCAT(Substring(a.A0243, 1, 4),'.',Substring(a.A0243, 5, 2)) a0243,a.A0223 "
					+ "FROM A02" + tableExt + " a WHERE a.A0255 = '1' AND a.A0281 = 'true' AND (LENGTH (a.A0243) = 6 OR LENGTH (a.A0243) = 8)) T "
					+ "GROUP BY T.A0000 ) X SET b.A0192F = X.newa0243 WHERE b.A0000 = X.A0000").executeUpdate();
			
			String sqlPartQrzxl="A0000 not in (select a0000 from A08"+tableExt+" where a0831 ='1' )";
			//更新 全日制 输出学历
			Statement smtA0801A = sess.connection().createStatement();
			String A0801TableSql = "create table A0801A"+tableExt+"(A0000 varchar(120),A0800 varchar(120),A0801A varchar(60),ZGXLXX varchar(120)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
			smtA0801A.execute(A0801TableSql.toUpperCase());
			smtA0801A.executeUpdate("insert into A0801A"+tableExt+" SELECT T.A0000,T.A0800,T.A0801A,T.ZGXLXX FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = V.A0000 "
					+ "THEN @rowNO + 1 ELSE 1 END) RN,V.A0000,V.A0800,V.A0801A,CONCAT(IFNULL(V.A0814,''), IFNULL(V.A0824,'')) ZGXLXX,(@pre_parent_code := V.A0000) GRN "
					+ "FROM a08"+tableExt+" V,(SELECT @rowNO := 0 ,@pre_parent_code := '') XXX WHERE V.A0899 = 'true' AND V.A0837 = '1' AND V.A0801B IS NOT NULL"
					+ " ORDER BY V.A0000,V.A0801B) T WHERE T.RN = 1 and T."+sqlPartQrzxl);
			sess.createSQLQuery("ALTER TABLE A0801A" + tableExt + " add index A0000A0801A" + tableExt + "(A0000)").executeUpdate();
			sess.createSQLQuery("ALTER TABLE A0801A" + tableExt + " add index A0000A0831A" + tableExt + "(A0800)").executeUpdate();
			Statement smt = sess.connection().createStatement();
			smt.executeUpdate("UPDATE A08"+tableExt+" A08 SET A08.A0831 = '0' where A08.A0831 not in ('1')");
			smt.executeUpdate("UPDATE A08"+tableExt+" A08,A0801A"+tableExt+" Y SET A08.A0831  = '1' WHERE Y.A0800 = A08.A0800");
			smt.executeUpdate("UPDATE A01"+tableExt+" A01,A0801A"+tableExt+" Y SET A01.QRZXL = Y.A0801A,A01.QRZXLXX = Y.ZGXLXX WHERE Y.A0000 = A01.A0000");
			smt.execute("drop table A0801A"+tableExt);
			smt.close();
			
			String sqlPartQrzxw="A0000 not in (select a0000 from A08"+tableExt+" where a0832 ='1' )";
			//更新 全日制 输出学位
			Statement smtA0901A = sess.connection().createStatement();
			String A0901TableSql = "create table A0901A"+tableExt+"(A0000 varchar(120),A0800 varchar(120),A0901A varchar(60),ZGXLXX varchar(120)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
			smtA0901A.execute(A0901TableSql.toUpperCase());
			smtA0901A.executeUpdate("insert into A0901A"+tableExt+" SELECT T.A0000,T.A0800,T.A0901A,T.ZGXLXX FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = "
					+ "V.A0000 THEN @rowNO + 1 ELSE 1 END) RN,V.A0000,V.A0800,V.A0901A,CONCAT(IFNULL(V.A0814,''), IFNULL(V.A0824,'')) ZGXLXX,(@pre_parent_code := V.A0000) GRN FROM "
					+ "a08"+tableExt+" V,(SELECT @rowNO := 0 ,@pre_parent_code := '') XXX WHERE V.A0899 = 'true' AND V.A0837 = '1' AND "
					+ "V.A0901B IS NOT NULL ORDER BY V.A0000,V.A0901B) T WHERE T.RN = 1 and T."+sqlPartQrzxw);
			sess.createSQLQuery("ALTER TABLE A0901A" + tableExt + " add index A0000A0901A" + tableExt + "(A0000)").executeUpdate();
			sess.createSQLQuery("ALTER TABLE A0901A" + tableExt + " add index A0000A0832A" + tableExt + "(A0800)").executeUpdate();
			Statement smt2 = sess.connection().createStatement();
			smt2.executeUpdate("UPDATE A08"+tableExt+" A08 SET A08.A0832 = '0' where A08.A0832 not in ('1')");
			smt2.executeUpdate("UPDATE A08"+tableExt+" A08,A0901A"+tableExt+" Y SET A08.A0832  = '1' WHERE Y.A0800 = A08.A0800");
			smt2.executeUpdate("UPDATE A01"+tableExt+" A01,A0901A"+tableExt+" Y SET A01.QRZXW = Y.A0901A,A01.QRZXWXX = Y.ZGXLXX WHERE Y.A0000 = A01.A0000");
			smt2.execute("drop table A0901A"+tableExt);
			smt2.close();
			
			String sqlPartZzxl="A0000 not in (select a0000 from A08"+tableExt+" where A0838 ='1' )";
			//更新 在职 输出学历
			Statement smtA0802A = sess.connection().createStatement();
			String A0802TableSql = "create table A0802A"+tableExt+"(A0000 varchar(120),A0800 varchar(120),A0801A varchar(60),ZGXLXX varchar(120)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
			smtA0802A.execute(A0802TableSql.toUpperCase());
			smtA0802A.executeUpdate("insert into A0802A"+tableExt+" SELECT T.A0000,T.A0800,T.A0801A,T.ZGXLXX FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = V.A0000 "
					+ "THEN @rowNO + 1 ELSE 1 END) RN,V.A0000,V.A0800,V.A0801A,CONCAT(IFNULL(V.A0814,''), IFNULL(V.A0824,'')) ZGXLXX,(@pre_parent_code := V.A0000) GRN "
					+ "FROM a08"+tableExt+" V,(SELECT @rowNO := 0 ,@pre_parent_code := '') XXX WHERE V.A0899 = 'true' AND V.A0837 = '2' AND V.A0801B IS NOT NULL"
					+ " ORDER BY V.A0000,V.A0801B) T WHERE T.RN = 1 and T."+sqlPartZzxl);
			sess.createSQLQuery("ALTER TABLE A0802A" + tableExt + " add index A0000A0802A" + tableExt + "(A0000)").executeUpdate();
			sess.createSQLQuery("ALTER TABLE A0802A" + tableExt + " add index A0000A0838A" + tableExt + "(A0800)").executeUpdate();
			Statement smt3 = sess.connection().createStatement();
			smt3.executeUpdate("UPDATE A08"+tableExt+" A08 SET A08.A0838 = '0' where A08.A0838 not in ('1')");
			smt3.executeUpdate("UPDATE A08"+tableExt+" A08,A0802A"+tableExt+" Y SET A08.A0838  = '1' WHERE Y.A0800 = A08.A0800");
			smt3.executeUpdate("UPDATE A01"+tableExt+" A01,A0802A"+tableExt+" Y SET A01.ZZXL = Y.A0801A,A01.ZZXLXX = Y.ZGXLXX WHERE Y.A0000 = A01.A0000");
			smt3.execute("drop table A0802A"+tableExt);
			smt3.close();
			
			String sqlPartZzxw="A0000 not in (select a0000 from A08"+tableExt+" where A0839 ='1' )";
			//更新 在职 输出学位
			Statement smtA0902A = sess.connection().createStatement();
			String A0902TableSql = "create table A0902A"+tableExt+"(A0000 varchar(120),A0800 varchar(120),A0901A varchar(60),ZGXLXX varchar(120)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
			smtA0902A.execute(A0902TableSql.toUpperCase());
			smtA0902A.executeUpdate("insert into A0902A"+tableExt+" SELECT T.A0000,T.A0800,T.A0901A,T.ZGXLXX FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = "
					+ "V.A0000 THEN @rowNO + 1 ELSE 1 END) RN,V.A0000,V.A0800,V.A0901A,CONCAT(IFNULL(V.A0814,''), IFNULL(V.A0824,'')) ZGXLXX,(@pre_parent_code := V.A0000) GRN FROM "
					+ "a08"+tableExt+" V,(SELECT @rowNO := 0 ,@pre_parent_code := '') XXX WHERE V.A0899 = 'true' AND V.A0837 = '2' AND "
					+ "V.A0901B IS NOT NULL ORDER BY V.A0000,V.A0901B) T WHERE T.RN = 1 and T."+sqlPartZzxw);
			sess.createSQLQuery("ALTER TABLE A0902A" + tableExt + " add index A0000A0902A" + tableExt + "(A0000)").executeUpdate();
			sess.createSQLQuery("ALTER TABLE A0902A" + tableExt + " add index A0000A0839A" + tableExt + "(A0800)").executeUpdate();
			Statement smt4 = sess.connection().createStatement();
			smt4.executeUpdate("UPDATE A08"+tableExt+" A08 SET A08.A0839 = '0' where A08.A0839 not in ('1')");
			smt4.executeUpdate("UPDATE A08"+tableExt+" A08,A0902A"+tableExt+" Y SET A08.A0839  = '1' WHERE Y.A0800 = A08.A0800");
			smt4.executeUpdate("UPDATE A01"+tableExt+" A01,A0902A"+tableExt+" Y SET A01.ZZXW = Y.A0901A,A01.ZZXWXX = Y.ZGXLXX WHERE Y.A0000 = A01.A0000");
			smt4.execute("drop table A0902A"+tableExt);
			smt4.close();
			
			String sqlPartZgxl="A0000 not in (select a0000 from A08"+tableExt+" where A0834 ='1' )";
			//更新 最高 学历
			Statement smtA0801G = sess.connection().createStatement();
			String A0801GTableSql = "create table A0801G"+tableExt+"(A0000 varchar(120),A0800 varchar(120),A0801A varchar(60),ZGXLXX varchar(120)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
			smtA0801G.execute(A0801GTableSql.toUpperCase());
			smtA0801G.executeUpdate("insert into A0801G"+tableExt+" SELECT T.A0000,T.A0800,T.A0801A,T.ZGXLXX FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = V.A0000 "
					+ "THEN @rowNO + 1 ELSE 1 END) RN,V.A0000,V.A0800,V.A0801A,CONCAT(IFNULL(V.A0814,''), IFNULL(V.A0824,'')) ZGXLXX,(@pre_parent_code := V.A0000) GRN "
					+ "FROM a08"+tableExt+" V,(SELECT @rowNO := 0 ,@pre_parent_code := '') XXX WHERE V.A0899 = 'true' AND V.A0837 IN('1','2') AND V.A0801B IS NOT NULL"
					+ " ORDER BY V.A0000,V.A0801B) T WHERE T.RN = 1 and T."+sqlPartZgxl);
			sess.createSQLQuery("ALTER TABLE A0801G" + tableExt + " add index A0000A0834G" + tableExt + "(A0800)").executeUpdate();
			sess.createSQLQuery("ALTER TABLE A0801G" + tableExt + " add index A0000A0801G" + tableExt + "(A0000)").executeUpdate();
			//先维护最高学历标记A0834
			Statement smt5 = sess.connection().createStatement();
			smt5.executeUpdate("UPDATE A08"+tableExt+" A08 SET A08.A0834 = '0' where A08.A0834 not in ('1')");
			smt5.executeUpdate("UPDATE A08"+tableExt+" A08,A0801G"+tableExt+" Y SET A08.A0834  = '1' WHERE Y.A0800 = A08.A0800");
			//再更新最高学历
			smt5.executeUpdate("UPDATE A01"+tableExt+" A01,A0801G"+tableExt+" Y SET A01.ZGXL = Y.A0801A,A01.ZGXLXX = Y.ZGXLXX WHERE Y.A0000 = A01.A0000");
			smt5.execute("drop table A0801G"+tableExt);
			smt5.close();
			
			String sqlPartZgxW="A0000 not in (select a0000 from A08"+tableExt+" where A0835 ='1' )";
			//更新 最高 学位
			Statement smtA0901G = sess.connection().createStatement();
			String A0901GTableSql = "create table A0901G"+tableExt+"(A0000 varchar(120),A0800 varchar(120),A0901A varchar(60),ZGXLXX varchar(120)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
			smtA0901G.execute(A0901GTableSql.toUpperCase());
			smtA0901G.executeUpdate("insert into A0901G"+tableExt+" SELECT T.A0000,T.A0800,T.A0901A,T.ZGXLXX FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = "
					+ "V.A0000 THEN @rowNO + 1 ELSE 1 END) RN,V.A0000,V.A0800,V.A0901A,CONCAT(IFNULL(V.A0814,''), IFNULL(V.A0824,'')) ZGXLXX,(@pre_parent_code := V.A0000) GRN FROM "
					+ "a08"+tableExt+" V,(SELECT @rowNO := 0 ,@pre_parent_code := '') XXX WHERE V.A0899 = 'true' AND V.A0837 IN('1','2') AND "
					+ "V.A0901B IS NOT NULL ORDER BY V.A0000,V.A0901B) T WHERE T.RN = 1 and T."+sqlPartZgxW);
			sess.createSQLQuery("ALTER TABLE A0901G" + tableExt + " add index A0000A0901G" + tableExt + "(A0000)").executeUpdate();
			sess.createSQLQuery("ALTER TABLE A0901G" + tableExt + " add index A0000A0835G" + tableExt + "(A0800)").executeUpdate();
			//先维护最高学位标记A0835
			Statement smt6 = sess.connection().createStatement();
			smt6.executeUpdate("UPDATE A08"+tableExt+" A08 SET A08.A0835 = '0' where A08.A0835 not in ('1')");
			smt6.executeUpdate("UPDATE A08"+tableExt+" A08,A0901G"+tableExt+" Y SET A08.A0835  = '1' WHERE Y.A0800 = A08.A0800");
			//再更新最高学历
			smt6.executeUpdate("UPDATE A01"+tableExt+" A01,A0901G"+tableExt+" Y SET A01.ZGXW = Y.A0901A,A01.ZGXWXX = Y.ZGXLXX WHERE Y.A0000 = A01.A0000");
			smt6.execute("drop table A0901G"+tableExt);
			smt6.close();
			
		}
		if(DBType.ORACLE == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.ORACLE)){
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
			
			//更新 全日制 输出学历
			String A0801TableSql = "create table A0801"+tableExt+"(A0000 varchar2(120),A0800 varchar2(120),A0801A varchar2(60),ZGXLXX varchar2(120))";
			sess.createSQLQuery(A0801TableSql.toUpperCase()).executeUpdate();
			sess.createSQLQuery("insert into A0801"+tableExt+" SELECT T .A0000,T .A0800,T .A0801A,SUBSTR(T .ZGXLXX, 1, 30) FROM (SELECT ROW_NUMBER () OVER (PARTITION BY v.A0000 "
					+ "ORDER BY v.A0801B) rn,v.A0000,v.A0800,v.A0801A,CONCAT (v.A0814, v.A0824) ZGXLXX FROM A08"+tableExt+" v WHERE v.A0899 = 'true' AND v.A0837 = '1' AND "
					+ "v.A0801B IS NOT NULL and v.A0831 <> '1') T WHERE T .rn = 1 ").executeUpdate();
			sess.createSQLQuery("Create index AA0801" + tableExt + " on A0801" + tableExt + "(A0000)").executeUpdate();
			sess.createSQLQuery("Create index AA0831" + tableExt + " on A0801" + tableExt + "(A0800)").executeUpdate();
			sess.createSQLQuery("UPDATE A08"+tableExt+" A08 SET A08.A0831 = '0' where A08.A0831 <> '1'").executeUpdate();
			sess.createSQLQuery("UPDATE A08"+tableExt+" A08 SET A08.A0831 = '1' WHERE EXISTS (SELECT 1 FROM A0801"+tableExt+" Y WHERE Y.A0800 = A08.A0800)").executeUpdate();
			sess.createSQLQuery("UPDATE A01"+tableExt+" A01 SET (A01.QRZXL, A01.QRZXLXX) = (SELECT T .A0801A,T .ZGXLXX FROM A0801"+tableExt+" T "
					+ "WHERE T.A0000 = A01.A0000) WHERE 1 = 1 AND EXISTS (SELECT 1 FROM A0801"+tableExt+" T WHERE T.A0000 = A01.A0000)").executeUpdate();
			sess.createSQLQuery("drop table A0801"+tableExt).executeUpdate();
			
			//更新 全日制 输出学位
			String A0901TableSql = "create table A0901"+tableExt+"(A0000 varchar2(120),A0800 varchar2(120),A0901A varchar2(60),ZGXLXX varchar2(120))";
			sess.createSQLQuery(A0901TableSql.toUpperCase()).executeUpdate();
			sess.createSQLQuery("insert into A0901"+tableExt+" SELECT T .A0000,T .A0800,T .A0901A,SUBSTR(T .ZGXLXX, 1, 30) FROM (SELECT ROW_NUMBER () OVER (PARTITION BY v.A0000 "
					+ "ORDER BY v.A0901B) rn,v.A0000,v.A0800,v.A0901A,CONCAT (v.A0814, v.A0824) ZGXLXX FROM A08"+tableExt+" v WHERE v.A0899 = 'true' AND v.A0837 = '1' AND "
					+ "v.A0901B IS NOT NULL and v.A0832 <> '1') T WHERE T .rn = 1 ").executeUpdate();
			sess.createSQLQuery("Create index AA0901" + tableExt + " on A0901" + tableExt + "(A0000)").executeUpdate();
			sess.createSQLQuery("Create index AA0832" + tableExt + " on A0901" + tableExt + "(A0800)").executeUpdate();
			sess.createSQLQuery("UPDATE A08"+tableExt+" A08 SET A08.A0832 = '0'  where A08.A0832 <> '1'").executeUpdate();
			sess.createSQLQuery("UPDATE A08"+tableExt+" A08 SET A08.A0832 = '1' WHERE EXISTS (SELECT 1 FROM A0901"+tableExt+" Y WHERE Y.A0800 = A08.A0800)").executeUpdate();
			sess.createSQLQuery("UPDATE A01"+tableExt+" A01 SET (A01.QRZXW, A01.QRZXWXX) = (SELECT T .A0901A,T .ZGXLXX FROM A0901"+tableExt+" T "
					+ "WHERE T.A0000 = A01.A0000) WHERE 1 = 1 AND EXISTS (SELECT 1 FROM A0901"+tableExt+" T WHERE T.A0000 = A01.A0000)").executeUpdate();
			sess.createSQLQuery("drop table A0901"+tableExt).executeUpdate();
			
			//更新 在职 输出学历
			String A0802TableSql = "create table A0802"+tableExt+"(A0000 varchar2(120),A0800 varchar2(120),A0801A varchar2(60),ZGXLXX varchar2(120))";
			sess.createSQLQuery(A0802TableSql.toUpperCase()).executeUpdate();
			sess.createSQLQuery("insert into A0802"+tableExt+" SELECT T .A0000,T .A0800,T .A0801A,SUBSTR(T .ZGXLXX, 1, 30) FROM (SELECT ROW_NUMBER () OVER (PARTITION BY v.A0000 "
					+ "ORDER BY v.A0801B) rn,v.A0000,v.A0800,v.A0801A,CONCAT (v.A0814, v.A0824) ZGXLXX FROM A08"+tableExt+" v WHERE v.A0899 = 'true' AND v.A0837 = '2' AND "
					+ "v.A0801B IS NOT NULL  and v.A0838 <> '1') T WHERE T .rn = 1 ").executeUpdate();
			sess.createSQLQuery("Create index AA0802" + tableExt + " on A0802" + tableExt + "(A0000)").executeUpdate();
			sess.createSQLQuery("Create index AA0838" + tableExt + " on A0802" + tableExt + "(A0800)").executeUpdate();
			sess.createSQLQuery("UPDATE A08"+tableExt+" A08 SET A08.A0838 = '0'   where A08.A0838 <> '1'").executeUpdate();
			sess.createSQLQuery("UPDATE A08"+tableExt+" A08 SET A08.A0838 = '1' WHERE EXISTS (SELECT 1 FROM A0802"+tableExt+" Y WHERE Y.A0800 = A08.A0800)").executeUpdate();
			sess.createSQLQuery("UPDATE A01"+tableExt+" A01 SET (A01.ZZXL, A01.ZZXLXX) = (SELECT T .A0801A,T .ZGXLXX FROM A0802"+tableExt+" T "
					+ "WHERE T.A0000 = A01.A0000) WHERE 1 = 1 AND EXISTS (SELECT 1 FROM A0802"+tableExt+" T WHERE T.A0000 = A01.A0000)").executeUpdate();
			sess.createSQLQuery("drop table A0802"+tableExt).executeUpdate();
			
			//更新 在职 输出学位
			String A0902TableSql = "create table A0902"+tableExt+"(A0000 varchar2(120),A0800 varchar2(120),A0901A varchar2(60),ZGXLXX varchar2(120))";
			sess.createSQLQuery(A0902TableSql.toUpperCase()).executeUpdate();
			sess.createSQLQuery("insert into A0902"+tableExt+" SELECT T .A0000,T .A0800,T .A0901A,SUBSTR(T .ZGXLXX, 1, 30) FROM (SELECT ROW_NUMBER () OVER (PARTITION BY v.A0000 "
					+ "ORDER BY v.A0901B) rn,v.A0000,v.A0800,v.A0901A,CONCAT (v.A0814, v.A0824) ZGXLXX FROM A08"+tableExt+" v WHERE v.A0899 = 'true' AND v.A0837 = '2' AND "
					+ "v.A0901B IS NOT NULL  and v.A0839 <> '1') T WHERE T .rn = 1 ").executeUpdate();
			sess.createSQLQuery("Create index AA0902" + tableExt + " on A0902" + tableExt + "(A0000)").executeUpdate();
			sess.createSQLQuery("Create index AA0839" + tableExt + " on A0902" + tableExt + "(A0800)").executeUpdate();
			sess.createSQLQuery("UPDATE A08"+tableExt+" A08 SET A08.A0839 = '0' where A08.A0839 <> '1'").executeUpdate();
			sess.createSQLQuery("UPDATE A08"+tableExt+" A08 SET A08.A0839 = '1' WHERE EXISTS (SELECT 1 FROM A0902"+tableExt+" Y WHERE Y.A0800 = A08.A0800)").executeUpdate();
			sess.createSQLQuery("UPDATE A01"+tableExt+" A01 SET (A01.ZZXW, A01.ZZXWXX) = (SELECT T .A0901A,T .ZGXLXX FROM A0902"+tableExt+" T "
					+ "WHERE T.A0000 = A01.A0000) WHERE 1 = 1 AND EXISTS (SELECT 1 FROM A0902"+tableExt+" T WHERE T.A0000 = A01.A0000)").executeUpdate();
			sess.createSQLQuery("drop table A0902"+tableExt).executeUpdate();
			
			//更新 最高 学历
			String A0801GTableSql = "create table A0801G"+tableExt+"(A0000 varchar2(120),A0800 varchar2(120),A0801A varchar2(60),ZGXLXX varchar2(120))";
			sess.createSQLQuery(A0801GTableSql.toUpperCase()).executeUpdate();
			sess.createSQLQuery("insert into A0801G"+tableExt+" SELECT T .A0000,T .A0800,T .A0801A,SUBSTR(T .ZGXLXX, 1, 30) FROM (SELECT ROW_NUMBER () OVER (PARTITION BY v.A0000 "
					+ "ORDER BY v.A0801B) rn,v.A0000,v.A0800,v.A0801A,CONCAT (v.A0814, v.A0824) ZGXLXX FROM A08"+tableExt+" v WHERE v.A0899 = 'true' AND v.A0837 IN('1','2') AND "
					+ "v.A0801B IS NOT NULL and v.A0834 <> '1') T WHERE T .rn = 1  ").executeUpdate();
			sess.createSQLQuery("Create index AG0801" + tableExt + " on A0801G" + tableExt + "(A0000)").executeUpdate();
			sess.createSQLQuery("Create index AG0834" + tableExt + " on A0801G" + tableExt + "(A0800)").executeUpdate();
			sess.createSQLQuery("UPDATE A08"+tableExt+" A08 SET A08.A0834 = '0' where A08.A0834 <> '1'").executeUpdate();
			sess.createSQLQuery("UPDATE A08"+tableExt+" A08 SET A08.A0834 = '1' WHERE EXISTS (SELECT 1 FROM A0801G"+tableExt+" Y WHERE Y.A0800 = A08.A0800)").executeUpdate();
			
			sess.createSQLQuery("UPDATE A01"+tableExt+" A01 SET (A01.ZGXL, A01.ZGXLXX) = (SELECT T .A0801A,T .ZGXLXX FROM A0801G"+tableExt+" T "
					+ "WHERE T.A0000 = A01.A0000) WHERE 1 = 1 AND EXISTS (SELECT 1 FROM A0801G"+tableExt+" T WHERE T.A0000 = A01.A0000)").executeUpdate();
			sess.createSQLQuery("drop table A0801G"+tableExt).executeUpdate();
			
			//更新 最高 学位
			String A0901GTableSql = "create table A0901G"+tableExt+"(A0000 varchar2(120),A0800 varchar2(120),A0901A varchar2(60),ZGXLXX varchar2(120))";
			sess.createSQLQuery(A0901GTableSql.toUpperCase()).executeUpdate();
			sess.createSQLQuery("insert into A0901G"+tableExt+" SELECT T .A0000,T .A0800,T .A0901A,SUBSTR(T .ZGXLXX, 1, 30) FROM (SELECT ROW_NUMBER () OVER (PARTITION BY v.A0000 "
					+ "ORDER BY v.A0901B) rn,v.A0000,v.A0800,v.A0901A,CONCAT (v.A0814, v.A0824) ZGXLXX FROM A08"+tableExt+" v WHERE v.A0899 = 'true' AND v.A0837 IN('1','2') AND "
					+ "v.A0901B IS NOT NULL and v.A0835 <> '1') T WHERE T .rn = 1  ").executeUpdate();
			sess.createSQLQuery("Create index AG0901" + tableExt + " on A0901G" + tableExt + "(A0000)").executeUpdate();
			sess.createSQLQuery("Create index AG0835" + tableExt + " on A0901G" + tableExt + "(A0800)").executeUpdate();
			sess.createSQLQuery("UPDATE A08"+tableExt+" A08 SET A08.A0835 = '0' where A08.A0835 <> '1'").executeUpdate();
			sess.createSQLQuery("UPDATE A08"+tableExt+" A08 SET A08.A0835 = '1' WHERE EXISTS (SELECT 1 FROM A0901G"+tableExt+" Y WHERE Y.A0800 = A08.A0800)").executeUpdate();
			
			sess.createSQLQuery("UPDATE A01"+tableExt+" A01 SET (A01.ZGXW, A01.ZGXWXX) = (SELECT T .A0901A,T .ZGXLXX FROM A0901G"+tableExt+" T "
					+ "WHERE T.A0000 = A01.A0000) WHERE 1 = 1 AND EXISTS (SELECT 1 FROM A0901G"+tableExt+" T WHERE T.A0000 = A01.A0000)").executeUpdate();
			sess.createSQLQuery("drop table A0901G"+tableExt).executeUpdate();
			
		}
		
	}
	

	private static void updateZGXueliXuewei(String sql, HBSession sess, String tableExt) {
		String update ="update A08zg"+tableExt+" set a0834='0',a0835='0'  where a0000 in (select a0000 from ("+sql+") as a) and a0899='true'";
		sess.createSQLQuery(update).executeUpdate();
		sess.flush();
		String sql11="select a0800,a0801b from A08zg"+tableExt+" where a0000 in ("+sql+") and a0837='1' and a0831='1' and a0899='true'";
		String sql2="select a0800,a0801b from A08zg"+tableExt+" where a0000 in ("+sql+") and a0837='2' and a0838='1' and a0899='true'";
		Object[] qrz=(Object[]) sess.createSQLQuery(sql11).uniqueResult();
		Object[] zz=(Object[]) sess.createSQLQuery(sql2).uniqueResult();
		if((qrz==null||qrz.length<1)&&(zz!=null&&zz.length>0)){
			Object a0800=zz[0];
			sess.createSQLQuery("update A08zg"+tableExt+" set a0834='1' where a0800='"+a0800+"'").executeUpdate();
		}
		if((zz==null||zz.length<1)&&(qrz!=null&&qrz.length>0)){
			Object a0800=qrz[0];
			sess.createSQLQuery("update A08zg"+tableExt+" set a0834='1' where a0800='"+a0800+"'").executeUpdate();
		}
		if(qrz!=null&&zz!=null&&qrz.length>0&&zz.length>0){
			
			String qrzxueli=qrz[1].toString();
			String zzxueli=zz[1].toString();
			String qrzxl=qrzxueli.substring(0,1);
			String zzxl=zzxueli.substring(0, 1);
			if(qrzxl.compareTo(zzxl)==-1){
				sess.createSQLQuery("update A08zg"+tableExt+" set a0834='1' where a0800='"+qrz[0]+"'").executeUpdate();
			}else{
				sess.createSQLQuery("update A08zg"+tableExt+" set a0834='1' where a0800='"+zz[0]+"'").executeUpdate();
			}
			
			
		}
		
		String sql3="select a0800,a0901b from A08zg"+tableExt+" where a0000 in ("+sql+") and a0837='1' and a0832='1' and a0899='true'";
		String sql4="select a0800,a0901b from A08zg"+tableExt+" where a0000 in ("+sql+") and a0837='2' and a0839='1' and a0899='true'";
		List<Object[]> list1=sess.createSQLQuery(sql3).list();
		List<Object[]> list2=sess.createSQLQuery(sql4).list();
		if((list1==null||list1.size()<1)&&(list2!=null&&list2.size()>0)){
			for(int i=0;i<list2.size();i++){
				Object a0800=list2.get(i)[0];
				sess.createSQLQuery("update A08zg"+tableExt+" set a0835='1' where a0800='"+a0800+"'").executeUpdate();
			}
		}
		if((list1!=null&&list1.size()>0)&&(list2==null||list2.size()<1)){
			for(int i=0;i<list1.size();i++){
				Object a0800=list1.get(i)[0];
				sess.createSQLQuery("update A08zg"+tableExt+" set a0835='1' where a0800='"+a0800+"'").executeUpdate();
			
			}
		}
		if(list1!=null&&list2!=null&&list1.size()>0&&list2.size()>0){

			String qrzxuewei=list1.get(0)[1].toString();
			String zzxuewei=list2.get(0)[1].toString();
			String qrzxw=qrzxuewei.substring(0,1);
			String zzxw=zzxuewei.substring(0, 1);
			if(qrzxw.compareTo(zzxw)==-1){
				for(int i=0;i<list1.size();i++){
					Object a0800=list1.get(i)[0];
					sess.createSQLQuery("update A08zg"+tableExt+" set a0835='1' where a0800='"+a0800+"'").executeUpdate();
				
				}
			}else{
				for(int i=0;i<list2.size();i++){
					Object a0800=list2.get(i)[0];
					sess.createSQLQuery("update A08zg"+tableExt+" set a0835='1' where a0800='"+a0800+"'").executeUpdate();
				
				}
			}
		}
		sess.flush();
		
	}




	private static void updateZGZZXueliXuewei(String sql, HBSession sess, String tableExt) {
		String sql11=" update A08zg"+tableExt+" set a0838='0',a0839='0' where a0000 in (select a0000 from ("+sql+") as a) and a0899='true' and a0837='2'";
		sess.createSQLQuery(sql11).executeUpdate();
		String sql1 = "select a0800,a0801b from A08zg"+tableExt+" where a0000 in ("+sql+") and a0899='true' and a0837='2'";// 输出的最高在职学历 
		List<Object[]> list1 = sess.createSQLQuery(sql1).list();
		if(list1!=null&&list1.size()>0){
			Collections.sort(list1,new Comparator<Object[]>(){//学历排序
				@Override
				public int compare(Object[] o1, Object[] o2) {
					String a0801b_1 = o1[1]==null?"":o1[1].toString();//学历代码
					String a0801b_2 = o2[1]==null?"":o2[1].toString();
					if(a0801b_1==null||"".equals(a0801b_1)){
						return 1;
					}
					if(a0801b_2==null||"".equals(a0801b_2)){
						return -1;
					}
					//return sortmap.get(a0801b_1).compareTo(sortmap.get(a0801b_2));
					return a0801b_1.compareTo(a0801b_2);
				}
				
			});
		}
		//如果有多条记录,第一条记录学历代码不为空就是最高学历,剩余学历代码如果与第一条一样也不处理
		if(list1!=null&&list1.size()>0){
			Object[] a08=list1.get(0);
			String xuelidaima=a08[1]==null?"":a08[1].toString();
			if(!StringUtil.isEmpty(xuelidaima)){
				sess.createSQLQuery(" update A08zg"+tableExt+" set a0838='1' where a0800='"+a08[0]+"'").executeUpdate();
			}
		}
		String sql2="select a0800,a0901b from A08zg"+tableExt+" where a0000 in ("+sql+") and a0899='true' and a0837='2' and length(a0901b)>0 order by a0901b asc";
		List<Object[]> list2 = sess.createSQLQuery(sql2).list();
		//如果有多条记录,第一条记录学位代码不为空就是最高学位,剩余学位代码如果与第一条对比后规则一样也为最高学位
		if(list2!=null&&list2.size()>0){
			Object[] a08_1=list2.get(0);
			String xueweidaima=a08_1[1].toString();
			if(!StringUtil.isEmpty(xueweidaima)){
				sess.createSQLQuery(" update A08zg"+tableExt+" set a0839='1' where a0800='"+a08_1[0]+"'").executeUpdate();

				if(xueweidaima.startsWith("1")){
					for(int i=1;i<list2.size();i++){
						Object[] a08_x=list2.get(i);
						String xueweidaima_x=a08_x[1].toString();
						if(!StringUtil.isEmpty(xueweidaima_x)&&(xueweidaima_x.startsWith("1")||xueweidaima_x.startsWith("2"))){
							sess.createSQLQuery(" update A08zg"+tableExt+" set a0839='1' where a0800='"+a08_x[0]+"'").executeUpdate();
						}
					}
				}else{
					String reg=xueweidaima.substring(0,1);
						for(int i=1;i<list2.size();i++){
							Object[] a08_x=list2.get(i);
							String xueweidaima_x=a08_x[1].toString();
							if(!StringUtil.isEmpty(xueweidaima_x)&&xueweidaima_x.startsWith(reg)){
								sess.createSQLQuery(" update A08zg"+tableExt+" set a0839='1' where a0800='"+a08_x[0]+"'").executeUpdate();
							}
						}
					
				}
			
			}
			
		}
		sess.flush();
	}

	private static void updateZGQRZXueliXuewei(String sql, HBSession sess, String tableExt) {
		String sql11=" update A08zg"+tableExt+" set a0831='0',a0832='0' where a0000 in (select a0000 from ("+sql+") as a) and a0899='true' and a0837='1'";
		sess.createSQLQuery(sql11).executeUpdate();
		String sql1 = "select a0800,a0801b from A08zg"+tableExt+" where a0000 in ("+sql+") and a0899='true' and a0837='1'";//输出的最高全日制学历 
		List<Object[]> list1 = sess.createSQLQuery(sql1).list();
		if(list1!=null&&list1.size()>0){
			Collections.sort(list1,new Comparator<Object[]>(){//学历排序
				@Override
				public int compare(Object[] o1, Object[] o2) {
					String a0801b_1 = o1[1]==null?"":o1[1].toString();//学历代码
					String a0801b_2 = o2[1]==null?"":o2[1].toString();
					if(a0801b_1==null||"".equals(a0801b_1)){
						return 1;
					}
					if(a0801b_2==null||"".equals(a0801b_2)){
						return -1;
					}
					//return sortmap.get(a0801b_1).compareTo(sortmap.get(a0801b_2));
					return a0801b_1.compareTo(a0801b_2);
				}
				
			});
		}
		/*//如果只有一条学历信息,如果学历代码不为空就是最高学历
		if(list1!=null&&list1.size()==1){
			A08 a08=list1.get(0);
			String xuelidaima=a08.getA0801b();
			if(!StringUtil.isEmpty(xuelidaima)){
				a08.setA0831("1");
			}
			sess.update(a08);
		}
		*/
		//如果有多条记录,第一条记录学历代码不为空就是最高学历,剩余学历代码如果与第一条一样也不处理
				if(list1!=null&&list1.size()>0){
					Object[] a08=list1.get(0);
					String xuelidaima=a08[1]==null?"":a08[1].toString();
					if(!StringUtil.isEmpty(xuelidaima)){
						sess.createSQLQuery(" update A08zg"+tableExt+" set a0831='1' where a0800='"+a08[0]+"'").executeUpdate();
					}
				}
				
		String sql2="select a0800,a0901b from A08zg"+tableExt+" where a0000 in ("+sql+") and a0899='true' and a0837='1' and length(a0901b)>0 order by a0901b asc";
		List<Object[]> list2 = sess.createSQLQuery(sql2).list();
				/*//如果只有一条学位信息,如果学位代码不为空就是最高学位
				if(list2!=null&& list2.size()==1){
					A08 a08=list2.get(0);
					String xueweidaima=a08.getA0901b();
					if(!StringUtil.isEmpty(xueweidaima)){
						 a08.setA0832("1");
					}
					sess.update(a08);
				}*/
				//如果有多条记录,第一条记录学位代码不为空就是最高学位,剩余学位代码如果与第一条对比后规则一样也为最高学位
				if(list2!=null&&list2.size()>0){
					Object[] a08_1=list2.get(0);
					String xueweidaima=a08_1[1].toString();
					if(!StringUtil.isEmpty(xueweidaima)){
						sess.createSQLQuery("update A08zg"+tableExt+" set a0832='1' where a0800='"+a08_1[0]+"'").executeUpdate();
						

						if(xueweidaima.startsWith("1")){
							for(int i=1;i<list2.size();i++){
								Object[] a08_x=list2.get(i);
								String xueweidaima_x=a08_x[1].toString();
								if(!StringUtil.isEmpty(xueweidaima_x)&&(xueweidaima_x.startsWith("1")||xueweidaima_x.startsWith("2"))){
									sess.createSQLQuery("update A08zg"+tableExt+" set a0832='1' where a0800='"+a08_x[0]+"'").executeUpdate();	
								}
							}
						}else{
							String reg=xueweidaima.substring(0,1);
								for(int i=1;i<list2.size();i++){
									Object[] a08_x=list2.get(i);
									String xueweidaima_x=a08_x[1].toString();;
									if(!StringUtil.isEmpty(xueweidaima_x)&&xueweidaima_x.startsWith(reg)){
										sess.createSQLQuery("update A08zg"+tableExt+" set a0832='1' where a0800='"+a08_x[0]+"'").executeUpdate();	
									}
									
								}
							
						}
					
					}
					
					
				}
				sess.flush();
	}

	public static void updateA0192F(String tableExt) throws Exception{
		HBSession sess = HBUtil.getHBSession();
		
		if(DBType.MYSQL == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.MYSQL)){
			sess.createSQLQuery("UPDATE A01" + tableExt + " b,( SELECT T.A0000,group_concat(T.a0243 ORDER BY T.a0223) newa0243 FROM(SELECT a.A0000,"
					+ "CONCAT(Substring(a.A0243, 1, 4),'.',Substring(a.A0243, 5, 2)) a0243,a.A0223 "
					+ "FROM A02" + tableExt + " a WHERE a.A0255 = '1' AND a.A0281 = 'true' AND (LENGTH (a.A0243) = 6 OR LENGTH (a.A0243) = 8)) T "
					+ "GROUP BY T.A0000 ) X SET b.A0192F = X.newa0243 WHERE b.A0000 = X.A0000").executeUpdate();
			
		}
		if(DBType.ORACLE == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.ORACLE)){
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
			
			
		}
		
	}
}
