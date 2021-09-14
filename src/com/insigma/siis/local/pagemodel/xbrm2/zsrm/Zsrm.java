package com.insigma.siis.local.pagemodel.xbrm2.zsrm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class Zsrm {
	private static String zjkTableName = "ZHGB.?";   	//中间库
	private static String zskTableName = "ZHGBCS.?";	//正式库
	
	static {
		try {
			initConfig();
			System.out.println("ZhongJianKuTableName:"+zjkTableName);
			System.out.println("ZhengShiKuTableName:"+zskTableName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getZjkTableName(String tableName) {
		return zjkTableName.replaceAll("\\?", tableName);
	}
	
	/***
	   *     获取数据库名
	 * @throws SQLException
	 */
	private static void initConfig() throws SQLException {
		String sql = "select remotedatabase() RD from dual";
		List<String> listTable = new ArrayList<String>();
		java.sql.Connection con = HBUtil.getHBSession().connection();
		
		zskTableName = con.getMetaData().getUserName()+".?";
		
		Statement statement = con.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
  
		while (resultSet.next()) { 
			zjkTableName = resultSet.getString("RD");  
		} 
		if (resultSet != null) {
			resultSet.close();
		}
		resultSet = null;
		
		if (statement != null) {
			statement.close();
		}
		statement = null;
		return ;
	}
	
	/***
	   *     导入人员
	 * @param condition
	 * @return
	 */
	public int movePersonDB(String condition)  {
		java.sql.Connection con = null;
		Statement statement = null;
		try {
			con = HBUtil.getHBSession().connection();
			List<String> listTable = getAllPersonTables();
			
			statement = con.createStatement(); 
			for (String t:listTable) {
				String sArray[] = getInsertSQL(t,condition).split(";");
				for (String s:sArray) {
					if (!"".equals(s.trim()) ) {
						System.out.println(s);
						statement.addBatch(s);
					}
				}
			} 
			statement.executeBatch();
			con.commit();
		}catch(Exception e) {
			return EventRtnType.NORMAL_SUCCESS;
		}finally {
			try {
				if (statement != null) {
					statement.close();
				}
				statement = null;
				if (con != null) {
					con.close();
				}
				con = null;
			}catch(Exception e) {
				
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/***
	   *      得到所有的人员信息表
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	private List<String> getAllPersonTables() throws SQLException {
		String sql = "select TABLE_NAME from User_TABLES where table_name like 'A__' Order By table_name";
		List<String> listTable = new ArrayList<String>();
		java.sql.Connection con = HBUtil.getHBSession().connection();
		Statement statement = con.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
  
		while (resultSet.next()) { 
			 String tableName = resultSet.getString("TABLE_NAME"); 
			 listTable.add(tableName);
		} 
		if (resultSet != null) {
			resultSet.close();
		}
		resultSet = null;
		
		if (statement != null) {
			statement.close();
		}
		statement = null;
		return listTable;
	}
	private String getInsertSQL(String tableName,String condition) throws SQLException {
		String sql = "select COLUMN_NAME from cols where table_name = '"+tableName+"' and column_name <> 'V0201B' order by column_name";
		StringBuffer buffer = new StringBuffer();
		java.sql.Connection con = HBUtil.getHBSession().connection();
		Statement statement = con.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
 
		Map<String,String> rptNodeMap = new HashMap<String,String>();
		while (resultSet.next()) { 
			 String columnName = resultSet.getString("COLUMN_NAME"); 
			 buffer.append(","+columnName);
		} 
		if (resultSet != null) {
			resultSet.close();
		}
		if (statement != null) {
			statement.close();
		}
		resultSet = null;
		statement = null;
		if (buffer.length()>0) buffer.delete(0, 1);
		 
		if ("A32".equals(tableName)) {
			sql = " Delete from "+zskTableName.replaceAll("\\?", tableName) + " where userId IN ("+condition+")\"; ";
			
			sql += " Insert Into " + zskTableName.replaceAll("\\?", tableName) + "("+buffer.toString()+")" + 
					" select " + buffer.toString() + " From "+zjkTableName.replaceAll("\\?", tableName) + 
					" Where userId IN ("+condition+");";
			sql += " Delete from "+zjkTableName.replaceAll("\\?", tableName) + " where userId IN ("+condition+"); ";
		}else if ("A86".equals(tableName)) {
			sql = " Delete from "+zskTableName.replaceAll("\\?", tableName) + " Where A0200 IN (select A0200 from A02 Where A0000 IN ("+condition+") );";
			
			sql += " Insert Into " + zskTableName.replaceAll("\\?", tableName) + "("+buffer.toString()+")" + 
					" select " + buffer.toString() + " From "+zjkTableName.replaceAll("\\?", tableName) + 
					" Where A0200 IN (select A0200 from A02 Where A0000 IN ("+condition+") );";
			sql += " Delete from "+zjkTableName.replaceAll("\\?", tableName) + " where A0000 IN ("+condition+"); ";
		}else {
			sql = " Delete from "+zskTableName.replaceAll("\\?", tableName) + " Where A0000 IN ("+condition+") ;";
			
			sql += " Insert Into " + zskTableName.replaceAll("\\?", tableName) + "("+buffer.toString()+")" + 
				" select " + buffer.toString() + " From "+zjkTableName.replaceAll("\\?", tableName) + 
				" Where A0000 IN ("+condition+");  ";
			sql += " Delete from "+zjkTableName.replaceAll("\\?", tableName) + " where A0000 IN ("+condition+"); ";
		} 
		
		return sql;
	}

	
}
