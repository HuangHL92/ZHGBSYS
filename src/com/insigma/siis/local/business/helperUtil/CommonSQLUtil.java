package com.insigma.siis.local.business.helperUtil;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;

public class CommonSQLUtil {
	/**
	 * È¥³ýÓÒ±ßµÄ×Ö·û
	 * @param field
	 * @param str
	 * @return
	 */
	public static String rtrim(String field, String str){
		if(DBUtil.getDBType()==DBType.ORACLE){
			return "rtrim('"+field+"','"+str+"')";
		}else{
			return "TRIM(TRAILING '"+str+"' FROM '"+field+"')";
		}
	}
	
	/**
	 * to_number
	 * @param field
	 * @param str
	 * @return
	 */
	public static String to_number(String field){
		if(DBUtil.getDBType()==DBType.ORACLE){
			return " to_number('"+field+"') ";
		}else{
			return " cast( '"+field+"'   as   SIGNED   INTEGER) ";
		}
	}
	
	/**
	 * uuid
	 * @param field
	 * @param str
	 * @return
	 */
	public static String UUID(){
		if(DBUtil.getDBType()==DBType.ORACLE){
			return " sys_guid() ";
		}else{
			return " REPLACE(UUID(),'-','') ";
		}
	}
	
	
	
	/**
	 * nvl
	 * @param field
	 * @param str
	 * @return
	 */
	public static String NVL(){
		if(DBUtil.getDBType()==DBType.ORACLE){
			return " NVL";
		}else{
			return " IFNULL";
		}
	}
	
	
	
	
	
	
}
