package com.insigma.odin.framework.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class DBUtil {
	
	private DBUtil(){}
	
	private static Logger log = Logger.getLogger(DBUtil.class);
	
	private static DBType cueDBType = null;
	
	public static DBType getDBType(Session sess){
		if(cueDBType==null){
			Connection conn = sess.connection();
			DatabaseMetaData dbMetaData = null;
			String databaseName = null;
			int databaseMajorVersion = 0;
			try {
				dbMetaData = conn.getMetaData();
				databaseName = dbMetaData.getDatabaseProductName();
				databaseMajorVersion = dbMetaData.getDatabaseMajorVersion();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			log.info("database："+databaseName+"，majorVersion："+databaseMajorVersion);
			if(databaseName!=null && databaseName.equalsIgnoreCase("mysql")){
				cueDBType = DBType.MYSQL;
			}else if(databaseName!=null){
				cueDBType = DBType.ORACLE;
			}
			if(cueDBType!=null) return cueDBType;
			
			String dbtype = GlobalNames.sysConfig.get("SYS_DBTYPE");
			if(dbtype!=null && dbtype.equalsIgnoreCase("mysql")){
				cueDBType = DBType.MYSQL;
			}else{
				cueDBType = DBType.ORACLE;
			}
		}	
		return cueDBType;
	}
	
	public static DBType getDBType(){
		return getDBType(HBUtil.getHBSession().getSession());
	}
	
	public enum DBType{
		ORACLE,MYSQL
	}
//整理数据相关方法

	public static void updateData() {//updataMysql
		HBSession sess = HBUtil.getHBSession();
		for (String updatasql : updataMysql) {
			try{
				sess.createSQLQuery(updatasql).executeUpdate();
				//System.out.println(updatasql);
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
	}

	public static void updateOneYESorNO() {//YESorNOsql
		HBSession sess = HBUtil.getHBSession();
		for (String updatasql : YESorNOsql) {
			try{
				sess.createSQLQuery(updatasql).executeUpdate();
				//System.out.println(updatasql);
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		
	}
	/**
	 * 获取数据库uuid函数字符串，用于拼接SQL
	 * @return 如果是Oracle返回sys_guid();MySQL返回uuid();
	 */
	public static String UUID(){
		if(DBUtil.getDBType()==DBType.ORACLE) return " sys_guid() ";
		return " uuid() ";
	}

	public static void deleteNull() {//deleteNullSql
		HBSession sess = HBUtil.getHBSession();
		for (String updatasql : deleteNullSql) {
			try{
				sess.createSQLQuery(updatasql).executeUpdate();
				//System.out.println(updatasql);
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		
	}

	public static void updatepinyin() {//updatepinyin
		if(DBType.MYSQL==DBUtil.getDBType()){
			String sql="DROP FUNCTION IF EXISTS getPY";
			StringBuffer sb=new StringBuffer("");
			sb.append(" CREATE FUNCTION getPY(in_string VARCHAR(255)) ")
			  .append(" RETURNS varchar(255) ")
			  .append(" BEGIN  ")
			  .append(" DECLARE tmp_str VARCHAR(255) CHARSET utf8 DEFAULT ''; ")
			  .append(" DECLARE str_len SMALLINT DEFAULT 0; ")
			  .append(" DECLARE tmp_char VARCHAR(10) CHARSET utf8 DEFAULT '';  ")
			  .append(" DECLARE tmp_len SMALLINT DEFAULT 0; ")
			  .append(" DECLARE tmp_rs VARCHAR(255) CHARSET utf8 DEFAULT ''; ")
			  .append(" DECLARE tmp_cc VARCHAR(10) CHARSET utf8 DEFAULT ''; ")
			  .append("  SET tmp_str = in_string; ")
			  .append(" SET str_len = LENGTH(tmp_str); ")
			  .append(" WHILE str_len > 0 DO  ")
			  .append(" SET tmp_char = LEFT(tmp_str,1); ")
			  .append(" SET tmp_cc = tmp_char; ")
			  .append(" IF LENGTH(tmp_char) > 1 THEN  ")
			  .append(" if(CONVERT( '吖' USING gbk ) COLLATE gbk_chinese_ci <= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci and CONVERT( '驁' USING gbk ) COLLATE gbk_chinese_ci >= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci) then set tmp_cc='A';end if;")
			  .append(" if(CONVERT( '八' USING gbk ) COLLATE gbk_chinese_ci <= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci and CONVERT( '簿' USING gbk ) COLLATE gbk_chinese_ci >= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci) then set tmp_cc='B';end if;")
			  .append(" if(CONVERT( '嚓' USING gbk ) COLLATE gbk_chinese_ci <= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci and CONVERT( '錯' USING gbk ) COLLATE gbk_chinese_ci >= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci) then set tmp_cc='C';end if;")
			  .append(" if(CONVERT( '咑' USING gbk ) COLLATE gbk_chinese_ci <= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci and CONVERT( '鵽' USING gbk ) COLLATE gbk_chinese_ci >= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci) then set tmp_cc='D';end if;")
			  .append(" if(CONVERT( '妸' USING gbk ) COLLATE gbk_chinese_ci <= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci and CONVERT( '樲' USING gbk ) COLLATE gbk_chinese_ci >= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci) then set tmp_cc='E';end if;")
			  .append(" if(CONVERT( '发' USING gbk ) COLLATE gbk_chinese_ci <= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci and CONVERT( '猤' USING gbk ) COLLATE gbk_chinese_ci >= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci) then set tmp_cc='F';end if;")
			  .append(" if(CONVERT( '旮' USING gbk ) COLLATE gbk_chinese_ci <= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci and CONVERT( '腂' USING gbk ) COLLATE gbk_chinese_ci >= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci) then set tmp_cc='G';end if;")
			  .append(" if(CONVERT( '妎' USING gbk ) COLLATE gbk_chinese_ci <= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci and CONVERT( '夻' USING gbk ) COLLATE gbk_chinese_ci >= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci) then set tmp_cc='H';end if;")
			  .append(" if(CONVERT( '丌' USING gbk ) COLLATE gbk_chinese_ci <= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci and CONVERT( '攈' USING gbk ) COLLATE gbk_chinese_ci >= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci) then set tmp_cc='J';end if;")
			  .append(" if(CONVERT( '咔' USING gbk ) COLLATE gbk_chinese_ci <= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci and CONVERT( '穒' USING gbk ) COLLATE gbk_chinese_ci >= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci) then set tmp_cc='K';end if;")
			  .append(" if(CONVERT( '垃' USING gbk ) COLLATE gbk_chinese_ci <= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci and CONVERT( '骆' USING gbk ) COLLATE gbk_chinese_ci >= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci) then set tmp_cc='L';end if;")
			  .append(" if(CONVERT( '嘸' USING gbk ) COLLATE gbk_chinese_ci <= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci and CONVERT( '椧' USING gbk ) COLLATE gbk_chinese_ci >= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci) then set tmp_cc='M';end if;")
			  .append(" if(CONVERT( '拏' USING gbk ) COLLATE gbk_chinese_ci <= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci and CONVERT( '诺' USING gbk ) COLLATE gbk_chinese_ci >= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci) then set tmp_cc='N';end if;")
			  .append(" if(CONVERT( '筽' USING gbk ) COLLATE gbk_chinese_ci <= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci and CONVERT( '漚' USING gbk ) COLLATE gbk_chinese_ci >= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci) then set tmp_cc='O';end if;")
			  .append(" if(CONVERT( '妑' USING gbk ) COLLATE gbk_chinese_ci <= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci and CONVERT( '曝' USING gbk ) COLLATE gbk_chinese_ci >= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci) then set tmp_cc='P';end if;")
			  .append(" if(CONVERT( '七' USING gbk ) COLLATE gbk_chinese_ci <= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci and CONVERT( '裠' USING gbk ) COLLATE gbk_chinese_ci >= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci) then set tmp_cc='Q';end if;")
			  .append(" if(CONVERT( '亽' USING gbk ) COLLATE gbk_chinese_ci <= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci and CONVERT( '鶸' USING gbk ) COLLATE gbk_chinese_ci >= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci) then set tmp_cc='R';end if;")
			  .append(" if(CONVERT( '仨' USING gbk ) COLLATE gbk_chinese_ci <= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci and CONVERT( '蜶' USING gbk ) COLLATE gbk_chinese_ci >= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci) then set tmp_cc='S';end if;")
			  .append(" if(CONVERT( '侤' USING gbk ) COLLATE gbk_chinese_ci <= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci and CONVERT( '籜' USING gbk ) COLLATE gbk_chinese_ci >= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci) then set tmp_cc='T';end if;")
			  .append(" if(CONVERT( '屲' USING gbk ) COLLATE gbk_chinese_ci <= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci and CONVERT( '鶩' USING gbk ) COLLATE gbk_chinese_ci >= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci) then set tmp_cc='W';end if;")
			  .append(" if(CONVERT( '夕' USING gbk ) COLLATE gbk_chinese_ci <= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci and CONVERT( '鑂' USING gbk ) COLLATE gbk_chinese_ci >= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci) then set tmp_cc='X';end if;")
			  .append(" if(CONVERT( '丫' USING gbk ) COLLATE gbk_chinese_ci <= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci and CONVERT( '韻' USING gbk ) COLLATE gbk_chinese_ci >= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci) then set tmp_cc='Y';end if;")
			  .append(" if(CONVERT( '帀' USING gbk ) COLLATE gbk_chinese_ci <= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci and CONVERT( '咗' USING gbk ) COLLATE gbk_chinese_ci >= CONVERT( tmp_cc USING gbk ) COLLATE gbk_chinese_ci) then set tmp_cc='Z';end if;")
			  .append(" END IF; ")
			  .append(" SET tmp_rs = CONCAT(tmp_rs,tmp_cc); ")
			  .append(" SET tmp_len = CHAR_LENGTH(tmp_char) + 1; ")
			  .append(" SET tmp_str = SUBSTRING(tmp_str,tmp_len); ")
			  .append(" SET str_len = LENGTH(tmp_str); ")
			  .append("  END WHILE; ")
			  .append("  RETURN tmp_rs;  ")
			  .append(" END; ");
			HBSession sess = HBUtil.getHBSession();
			sess.createSQLQuery(sql).executeUpdate();//System.out.println(sql);
			sess.createSQLQuery(sb.toString()).executeUpdate();//System.out.println(sb.toString());
			sess.createSQLQuery("update a01 set a0102=getpy(a0101)").executeUpdate();
			sess.createSQLQuery("update b01 set b0107=getpy(b0101)").executeUpdate();
		}else if(DBType.ORACLE==DBUtil.getDBType()){
			StringBuffer sb=new StringBuffer("");
			sb.append(" CREATE OR REPLACE FUNCTION getpy(P_NAME IN VARCHAR2) RETURN VARCHAR2 AS ")
			  .append(" V_COMPARE VARCHAR2(100); ")
			  .append("  V_RETURN VARCHAR2(4000); ")
			  .append("  FUNCTION F_NLSSORT(P_WORD IN VARCHAR2) RETURN VARCHAR2 AS ")
			  .append("   BEGIN ")
			  .append("  RETURN NLSSORT(P_WORD, 'NLS_SORT=SCHINESE_PINYIN_M'); ")
			  .append(" END; ")
			  .append("  BEGIN ")
			  .append("  FOR I IN 1..LENGTH(P_NAME) LOOP ")
			  .append("  V_COMPARE := F_NLSSORT(SUBSTR(P_NAME, I, 1)); ")
			  .append("  IF V_COMPARE >= F_NLSSORT('吖') AND V_COMPARE <= F_NLSSORT('驁') THEN  V_RETURN := V_RETURN || 'A';")
			  .append(" ELSIF V_COMPARE >= F_NLSSORT('八') AND V_COMPARE <= F_NLSSORT('簿') THEN  V_RETURN := V_RETURN || 'B';")
			  .append(" ELSIF V_COMPARE >= F_NLSSORT('嚓') AND V_COMPARE <= F_NLSSORT('錯') THEN  V_RETURN := V_RETURN || 'C';")
			  .append(" ELSIF V_COMPARE >= F_NLSSORT('咑') AND V_COMPARE <= F_NLSSORT('鵽') THEN  V_RETURN := V_RETURN || 'D';")
			  .append(" ELSIF V_COMPARE >= F_NLSSORT('妸') AND V_COMPARE <= F_NLSSORT('樲') THEN  V_RETURN := V_RETURN || 'E';")
			  .append(" ELSIF V_COMPARE >= F_NLSSORT('发') AND V_COMPARE <= F_NLSSORT('猤') THEN  V_RETURN := V_RETURN || 'F';")
			  .append(" ELSIF V_COMPARE >= F_NLSSORT('旮') AND V_COMPARE <= F_NLSSORT('腂') THEN  V_RETURN := V_RETURN || 'G';")
			  .append(" ELSIF V_COMPARE >= F_NLSSORT('妎') AND V_COMPARE <= F_NLSSORT('夻') THEN  V_RETURN := V_RETURN || 'H';")
			  .append(" ELSIF V_COMPARE >= F_NLSSORT('丌') AND V_COMPARE <= F_NLSSORT('攈') THEN  V_RETURN := V_RETURN || 'J';")
			  .append(" ELSIF V_COMPARE >= F_NLSSORT('咔') AND V_COMPARE <= F_NLSSORT('穒') THEN  V_RETURN := V_RETURN || 'K';")
			  .append(" ELSIF V_COMPARE >= F_NLSSORT('垃') AND V_COMPARE <= F_NLSSORT('擽') THEN  V_RETURN := V_RETURN || 'L';")
			  .append(" ELSIF V_COMPARE >= F_NLSSORT('嘸') AND V_COMPARE <= F_NLSSORT('椧') THEN  V_RETURN := V_RETURN || 'M';")
			  .append(" ELSIF V_COMPARE >= F_NLSSORT('拏') AND V_COMPARE <= F_NLSSORT('瘧') THEN  V_RETURN := V_RETURN || 'N';")
			  .append(" ELSIF V_COMPARE >= F_NLSSORT('筽') AND V_COMPARE <= F_NLSSORT('漚') THEN  V_RETURN := V_RETURN || 'O';")
			  .append(" ELSIF V_COMPARE >= F_NLSSORT('妑') AND V_COMPARE <= F_NLSSORT('曝') THEN  V_RETURN := V_RETURN || 'P';")
			  .append(" ELSIF V_COMPARE >= F_NLSSORT('七') AND V_COMPARE <= F_NLSSORT('裠') THEN  V_RETURN := V_RETURN || 'Q';")
			  .append(" ELSIF V_COMPARE >= F_NLSSORT('亽') AND V_COMPARE <= F_NLSSORT('鶸') THEN  V_RETURN := V_RETURN || 'R';")
			  .append(" ELSIF V_COMPARE >= F_NLSSORT('仨') AND V_COMPARE <= F_NLSSORT('蜶') THEN  V_RETURN := V_RETURN || 'S';")
			  .append(" ELSIF V_COMPARE >= F_NLSSORT('侤') AND V_COMPARE <= F_NLSSORT('籜') THEN  V_RETURN := V_RETURN || 'T';")
			  .append(" ELSIF V_COMPARE >= F_NLSSORT('屲') AND V_COMPARE <= F_NLSSORT('鶩') THEN  V_RETURN := V_RETURN || 'W';")
			  .append(" ELSIF V_COMPARE >= F_NLSSORT('夕') AND V_COMPARE <= F_NLSSORT('鑂') THEN  V_RETURN := V_RETURN || 'X';")
			  .append(" ELSIF V_COMPARE >= F_NLSSORT('丫') AND V_COMPARE <= F_NLSSORT('韻') THEN  V_RETURN := V_RETURN || 'Y';")
			  .append(" ELSIF V_COMPARE >= F_NLSSORT('帀') AND V_COMPARE <= F_NLSSORT('咗') THEN  V_RETURN := V_RETURN || 'Z';")
			  .append("  ELSE ")
			  .append("  V_RETURN := UPPER(SUBSTR(P_NAME,1,1)); ")
			  .append(" END IF;")
			  .append("  END LOOP;")
			  .append(" RETURN V_RETURN;")
			  .append(" END;");
			HBSession sess = HBUtil.getHBSession();
			sess.createSQLQuery(sb.toString()).executeUpdate();//System.out.println(sb.toString());
			sess.createSQLQuery("update a01 set a0102=getpy(a0101)").executeUpdate();
			sess.createSQLQuery("update b01 set b0107=getpy(b0101)").executeUpdate();
		}
		
	}
	/**
	 * 获取数据库 关于 某字段 不为空字符串
	 */
	public static String getColumnIsNotNull(String columnStr){
		if(columnStr == null || "".equals(columnStr)) return "";
		return DBUtil.getDBType()==DBType.ORACLE ? " "+ columnStr + " is not null" : " "+ columnStr + " is not null and " + columnStr + " <> '' " ;
	}
	
	/**
	 * 追加：更新MySQL数据库中的空字段(将空更新为null)
	 */
	public static String[] updataMysql = {
			"update A01 set a0101=null where trim(replace(a0101,'　',''))='';",
			"update A01 set A0104=null where trim(replace(A0104,'　',''))='';",
			"update A01 set A0107=null where trim(replace(A0107,'　',''))='';",
			"update A01 set A0111A=null where trim(replace(A0111A,'　',''))='';",
			"update A01 set A0114A=null where trim(replace(A0114A,'　',''))='';",
			"update A01 set A0117=null where trim(replace(A0117,'　',''))='';",
			"update A01 set A0128=null where trim(replace(A0128,'　',''))='';",
			"update A01 set A0134=null where trim(replace(A0134,'　',''))='';",
			"update A01 set A0141=null where trim(replace(A0141,'　',''))='';",
			"update A01 set A0144=null where trim(replace(A0144,'　',''))='';",
			"update A01 set A0160=null where trim(replace(A0160,'　',''))='';",
			"update A01 set A0163=null where trim(replace(A0163,'　',''))='';",
			"update A01 set A0165=null where trim(replace(A0165,'　',''))='';",
			"update A01 set A0184=null where trim(replace(A0184,'　',''))='';",
			"update A01 set A0221=null where trim(replace(A0221,'　',''))='';",
			"update A01 set A0192E=null where trim(replace(A0192E,'　',''))='';",
			"update A01 set A0288=null where trim(replace(A0288,'　',''))='';",
			"update A01 set A0134=null where trim(replace(A0134,'　',''))='';",
			"update A01 set A0192C=null where trim(replace(A0192C,'　',''))='';",
			"update A01 set A0196=null where trim(replace(A0196,'　',''))='';",
			"update A01 set A0197=null where trim(replace(A0197,'　',''))='';",
			"update A01 set A1701=null where trim(replace(A1701,'　',''))='';",
			"update A01 set A0197=null where trim(replace(A0197,'　',''))='';",
			"update A01 set A0195=null where trim(replace(A0195,'　',''))='';",
			"update A01 set A2949=null where trim(replace(A2949,'　',''))='';",
			"update A01 set A0192A=null where trim(replace(A0192A,'　',''))='';",
			"update A01 set A0192=null where trim(replace(A0192,'　',''))='';",
			"update A01 set A0121=null where trim(replace(A0121,'　',''))='';",
			"update A01 set A14Z101=null where trim(replace(A14Z101,'　',''))='';",
			"update A01 set A15Z101=null where trim(replace(A15Z101,'　',''))='';",
			"update A02 set A0201A=null where trim(replace(A0201A,'　',''))='';",
			"update A02 set A0201B=null where trim(replace(A0201B,'　',''))='';",
			"update A02 set A0201D=null where trim(replace(A0201D,'　',''))='';",
			"update A02 set A0215A=null where trim(replace(A0215A,'　',''))='';",
			"update A02 set A0219=null where trim(replace(A0219,'　',''))='';",
			"update A02 set A0243=null where trim(replace(A0243,'　',''))='';",
			"update A02 set A0251B=null where trim(replace(A0251B,'　',''))='';",
			"update A02 set A0255=null where trim(replace(A0255,'　',''))='';",
			"update A02 set A0265=null where trim(replace(A0265,'　',''))='';",
			"update A02 set A0279=null where trim(replace(A0279,'　',''))='';",
			"update A02 set A0281=null where trim(replace(A0281,'　',''))='';",
			"update A05 set A0531=null where trim(replace(A0531,'　',''))='';",
			"update A05 set A0501B=null where trim(replace(A0501B,'　',''))='';",
			"update A05 set A0524=null where trim(replace(A0524,'　',''))='';",
			"update A06 set A0601=null where trim(replace(A0601,'　',''))='';",
			"update A06 set A0699=null where trim(replace(A0699,'　',''))='';",
			"update A08 set A0801B=null where trim(replace(A0801B,'　',''))='';",
			"update A08 set A0801A=null where trim(replace(A0801A,'　',''))='';",
			"update A08 set A0901B=null where trim(replace(A0901B,'　',''))='';",
			"update A08 set A0901A=null where trim(replace(A0901A,'　',''))='';",
			"update A08 set A0804=null where trim(replace(A0804,'　',''))='';",
			"update A08 set A0807=null where trim(replace(A0807,'　',''))='';",
			"update A08 set A0814=null where trim(replace(A0814,'　',''))='';",
			"update A08 set A0837=null where trim(replace(A0837,'　',''))='';",
			"update A08 set A0898=null where trim(replace(A0898,'　',''))='';",
			"update A08 set A0899=null where trim(replace(A0899,'　',''))='';",
			"update A14 set A1404A=null where trim(replace(A1404A,'　',''))='';",
			"update A14 set A1407=null where trim(replace(A1407,'　',''))='';",
			"update A15 set A1517=null where trim(replace(A1517,'　',''))='';",
			"update A15 set A1521=null where trim(replace(A1521,'　',''))='';",
			"update A36 set A3601=null where trim(replace(A3601,'　',''))='';",
			"update A36 set A3604A=null where trim(replace(A3604A,'　',''))='';",
			"update A36 set A3607=null where trim(replace(A3607,'　',''))='';",
			"update A36 set A3627=null where trim(replace(A3627,'　',''))='';",
			"update A36 set A3611=null where trim(replace(A3611,'　',''))='';",
			"update A57 set A5714=null where trim(replace(A5714,'　',''))='';",
			"update B01 set B0101=null where trim(replace(B0101,'　',''))='';",
			"update B01 set B0104=null where trim(replace(B0104,'　',''))='';",
			"update B01 set B0114=null where trim(replace(B0114,'　',''))='';",
			"update B01 set B0117=null where trim(replace(B0117,'　',''))='';",
			"update B01 set B0124=null where trim(replace(B0124,'　',''))='';",
			"update B01 set B0127=null where trim(replace(B0127,'　',''))='';",
			"update B01 set B0131=null where trim(replace(B0131,'　',''))='';",
			"update B01 set B0194=null where trim(replace(B0194,'　',''))='';" 
	};
	
	public static String[] YESorNOsql = {
			"update A01 set A0197=0 where A0197 is null",
			"update A02 set a0201d=0 where a0201d is null",
			"update A02 set a0219='2' where a0219 is null",
			"update A02 set a0251b=0 where a0251b is null",
			"update A02 set A0255=0 where a0255 is null",
			"update A02 set a0281='false' where a0281 is null",
			"update A02 set a0279=0 where a0279 is null",
			"update A05 set a0524=0 where a0524 is null",
			"update A06 set a0699='false' where a0699 is null",
			"update A08 set a0899='false' where a0899 is null"
	};
	
	public static String[] deleteNullSql={
			"update A01 set a0111a=replace(replace(a0111a,'　',''),' ','')",
			"update A01 set a0114a=replace(replace(a0114a,'　',''),' ','')",
			"update A01 set a0192=replace(replace(a0192,'　',''),' ','')",
			"update A01 set a0192a=replace(replace(a0192a,'　',''),' ','')",
			"update A02 set a0201a=replace(replace(a0201a,'　',''),' ','')",
			"update A02 set a0215a=replace(replace(a0215a,'　',''),' ','')",
			"update A36 set a3601=replace(replace(a3601,'　',''),' ','')",
			"update A36 set a3604a=replace(replace(a3604a,'　',''),' ','')",
			"update A36 set a3607=replace(replace(a3607,'　',''),' ','')",
			"update A36 set a3627=replace(replace(a3627,'　',''),' ','')",
			"update A36 set a3611=replace(replace(a3611,'　',''),' ','')",
			"update A08 set a0801a=replace(replace(a0801a,'　',''),' ','')",
			"update A08 set a0901a=replace(replace(a0901a,'　',''),' ','')",
			"update A08 set a0814=replace(replace(a0814,'　',''),' ','')",
			"update A08 set a0824=replace(replace(a0824,'　',''),' ','')",
			"update A02 set a0245=replace(replace(a0245,'　',''),' ','')",
			"update A02 set a0267=replace(replace(a0267,'　',''),' ','')",
			"update B01 set b0114=replace(replace(b0114,'　',''),' ','')",
			"update B01 set b0111=replace(replace(b0111,'　',''),' ','')",
			
	};
	// zxw 维护权限 - JavaFunction
		public static void MaintenanceAuthorityFunc(){
			try {
				String valueFromTab = HBUtil.getValueFromTab("count(1)", "competence_userdept cu", "EXISTS ("
						+ "select 1 from smt_user su "
						+ "where su.userid = cu.userid and "
						+ "su.loginname not in ('system','checker','admin','admin_zhitong') and "
						+ "su.otherinfo is not null and "
						+ "substr(cu.b0111,1,length(su.OTHERINFO))<>su.OTHERINFO ) and "
						+ "userid not in('40288103556cc97701556d629135000f','4028810f5f2aed67015f2aefeeee0002','U001')");
				if("0".equals(valueFromTab)){
					CommonQueryBS.systemOut("未发现需要维护用户...");
				}else{
					HBUtil.executeUpdate("delete from competence_userdept cu where "
							+ "EXISTS ("
							+ "select 1 from smt_user su "
							+ "where su.userid = cu.userid and "
							+ "su.loginname not in ('system','checker','admin','admin_zhitong') and "
							+ "su.otherinfo is not null and "
							+ "substr(cu.b0111,1,length(su.OTHERINFO))<>su.OTHERINFO ) and "
							+ "userid not in('40288103556cc97701556d629135000f','4028810f5f2aed67015f2aefeeee0002','U001')");
					CommonQueryBS.systemOut("成功执行一次权限控制操作...");
				}
			} catch (AppException e) {
				e.printStackTrace();
				CommonQueryBS.systemOut("执行权限控制操作...失败！");
			}
		};
		
		/**
		 * 获取数据库 关于 某字段 为空字符串
		 */
		public static String getColumnIsNull(String columnStr){
			if(columnStr == null || "".equals(columnStr)) return "";
			return " decode("+columnStr+ ",null,'X0','','X0',"+columnStr+ ") = 'X0' ";
			//return DBUtil.getDBType()==DBType.ORACLE ? " "+ columnStr + " is null" : " ("+ columnStr + " is null or " + columnStr + " = '') " ;
		}
		
		public static void updateB01Data() {//updateB01Data
			HBSession sess = HBUtil.getHBSession();
			for (String updatasql : updataB01Mysql) {
				try{
					sess.createSQLQuery(updatasql).executeUpdate();
					//System.out.println(updatasql);
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		}
		
		public static String[] updataB01Mysql={
				"update B01 set B0117='',B0124='',B0131='',B0238='',B0239='',B0227='0',B0232='0',B0233='0',B0236='0',B0234='0' where B0194 = '2'",
				"update B01 set B0117='',B0124='',B0131='',B0127='',B0238='',B0239='',B0150='0',B0183='0',B0185='0',B0227='0',B0232='0',B0233='0',B0236='0',B0234='0' where B0194 = '3'",
		};
}
