package com.insigma.siis.local.pagemodel.xbrm;


import java.io.PrintWriter;
/**
两边的表结构要相同

*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.insigma.odin.framework.persistence.HBUtil;

public class OracleToSqlite {
	private String[] colNames;
	private String[] colTypes;
	private String[] colValues;
	private String path;
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}


	private PrintWriter out;
	private String wheresql;
	private String sql;
	
	public String getWheresql() {
		return wheresql;
	}

	public String getSql() {
		return sql;
	}

	public void setWheresql(String wheresql) {
		this.wheresql = wheresql;
	}

	public OracleToSqlite(String path,PrintWriter out,String wheresql) {
		super();
		this.path = path;
		this.out = out;
		this.wheresql = wheresql;
	}

	private Connection getSqliteConn(){
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:/"+path);//设置sqlite的路径
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	private Connection getOracleConn(){
		//设置oracle信息
		/*String forname = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String user = "zwhzyq";
		String password = "zwhzyq";
		Connection con = null;
		try {
			Class.forName(forname).newInstance();
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		Connection con = HBUtil.getHBSession().connection();
		return con;
	}
	
	public void importTable(String tableName){
		List list = new ArrayList();
		Connection conn = getOracleConn();
		initColumn(conn, tableName);
		String sql = createSelectSql(tableName);
		System.err.println(sql);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			int index = 0;
			int total = 0;
			while(rs.next()){
				for(int i = 0;i<colNames.length;i++){
					String value = getColValue(rs,colNames[i],colTypes[i]);
					colValues[i] = value;
				}
				String insertSql = createInsertSql(tableName);
				list.add(insertSql);
				index++;
				total++;
				if(index==10000){
					writeSqlite(list);
					index = 0;
					list = new ArrayList();
				}
			}
			writeSqlite(list);
			JSGLBS.outPrintlnSuc(out, "表"+tableName+"导出成功,总共导出" + total + "条!");
		} catch (Exception e) {
			JSGLBS.outPrintlnErr(out, "表"+tableName+"导出失败");
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void writeSqlite(List list){
		Connection conn = getSqliteConn();
		try {
			//Connection conn = getSqliteConn();
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			for(int i = 0;i<list.size();i++){
				st.addBatch((String)list.get(i));
			}
			st.executeBatch();
			conn.commit();
			st.clearBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	public void writeSqlite(String sql,List<Object[]> listArgs ) throws Exception{
		Connection conn = getSqliteConn();
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//Connection conn = getSqliteConn();
			for(Object[] args : listArgs){
				for(int i=0;i<args.length;i++){
					pstmt.setObject(i+1, args[i]);
				}
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			conn.commit();
			pstmt.clearBatch();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	private String getColValue(ResultSet rs,String colName,String colType){
		String result = "";
		try {
			if(colType.equals("java.math.BigDecimal")){
				result = String.valueOf(rs.getLong(colName));
			}
			
			else if(colType.equals("oracle.sql.CLOB")){
				result = rs.getString(colName);
			}
			else{
				result = rs.getString(colName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(result == null || result.equals("null")){
			result = "";
		}
		result = result.replaceAll("'", "''");
		return result;
	}
	
	private String createSelectSql(String tableName){
		StringBuffer sbf = new StringBuffer("select ");
		for(int i = 0;i<colNames.length;i++){
			sbf.append(colNames[i]);
			if(i<colNames.length-1){
				sbf.append(",");
			}
		}
		
		if(wheresql!=null&&!"".equals(wheresql)){
			sbf.append(" from ").append(tableName);
			sbf.append(" "+wheresql);
		}else if(sql!=null&&!"".equals(sql)){
			sbf.delete(0, 6);
			return sql.replace("{*}", sbf.toString());
		}else{
			sbf.append(" from ").append(tableName);
		}
		return sbf.toString();
	}
	
	private String createInsertSql(String tableName){
		StringBuffer sbf = new StringBuffer("insert into ");
		sbf.append(tableName).append("(");
		for(int i = 0;i<colNames.length;i++){
			sbf.append(colNames[i]);
			if(i<colNames.length-1){
				sbf.append(",");
			}
		}
		sbf.append(") values(");
		for(int i = 0;i<colValues.length;i++){
			sbf.append("'").append(colValues[i]).append("'");
			if(i<colValues.length-1){
				sbf.append(",");
			}
		}
		sbf.append(")");
		return sbf.toString();
	}
	
	private void initColumn(Connection conn,String tableName){
		try {
			String sql = "select * from " + tableName;
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.execute();
			ResultSetMetaData rsmd = (ResultSetMetaData) pstmt.getMetaData();
			if(rsmd !=null && rsmd.getColumnCount()>0){
				int count = rsmd.getColumnCount();
				colNames = new String[count];
				colTypes = new String[count];
				colValues = new String[count];
				int j = 0;
				for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
					
					colNames[j] = rsmd.getColumnName(i);
					colTypes[j] = rsmd.getColumnClassName(i);
					j++;
				}
				
				/*for(int i = 0;i<j;i++){
					System.err.println(colNames[i] + ":" + colTypes[i]);
				}*/
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		
		//OracleToSqlite ots = new OracleToSqlite("");
		//ots.importTable("A01");//输入要导入的表名

	}

	public void setSql(String sql) {
		this.sql = sql;
		
	}

	public void update() throws Exception {
		Connection conn = getSqliteConn();
		try {
			conn.setAutoCommit(false);
			conn.createStatement().executeUpdate("update DEPLOY_CLASSIFY set DC003='<h5><span class=''fontf''>'||DC003||'</span></h5>'");
			conn.createStatement().executeUpdate("update JS_DW set jsdw002='<h5><span class=''fontf2''>'||jsdw002||'</span></h5>'");
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
