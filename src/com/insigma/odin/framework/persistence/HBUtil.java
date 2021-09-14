package com.insigma.odin.framework.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.util.hibwrap.WrapSessionFactoryImpl;
import com.lbs.leaf.persistence.HibernateHelper;

/**
 * 
 * @author zhangy
 *
 */

public class HBUtil {
	private static final ThreadLocal hbsessCache=new ThreadLocal();
	
	
	public static SessionFactory getHBSessionFactory(){
		return new WrapSessionFactoryImpl(HibernateHelper.getSessionFactory());
	}
	
	/**
	 * 取得一个HBSession
	 * @return
	 */
	public static HBSession getHBSession(){
//		HBSession hbsess=(HBSession)hbsessCache.get();
//		if(hbsess==null){
//			Session sess=HibernateHelper.currentSession();
//			hbsess=new HBSession(sess);
//			hbsessCache.set(hbsess);
//		}
		//Session sess=getHBSessionFactory().openSession();
		HBSession hbsess=(HBSession)hbsessCache.get();
		if(hbsess==null){
			hbsess=new HBSession();
			hbsess.setSession(getHBSessionFactory().openSession());
		}
		
		hbsessCache.set(hbsess);
//		Session sess=HibernateHelper.currentSession();
//		HBSession hbsess=new HBSession(sess);
		return hbsess;
	}
	
	public static void closeHBSession(){
		
		HBSession hbsess=(HBSession)hbsessCache.get();
		
		if(hbsess != null){			
			hbsessCache.set(null);
			hbsess.close();
		}
	}
	
	/**
	 * 取当前系统时间
	 * @return
	 * @throws Exception
	 */
	public static java.util.Date getSysdate() throws AppException{
		java.util.Date sysdate=null;
		StringBuffer buffer = new StringBuffer();
		HBSession hbsess=getHBSession();
		Connection conn=hbsess.connection();
		String sql = "Select sysdate from dual";
		if(DBUtil.getDBType() == DBType.MYSQL){
			sql = "select NOW() ";
		}
		buffer.append(sql);
		try{
		PreparedStatement ps = conn.prepareStatement(buffer.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
			sysdate =new java.util.Date(rs.getTimestamp(1).getTime());
		if (rs != null)
			rs.close();
		if (ps != null)
			ps.close();
		}catch(Exception e){
			throw new AppException("数据库处理出现异常！",e);
		}
		return sysdate;
	}
	
	/**
	 * 取序列发生器的下一个值
	 * @param sequenceName
	 * @return
	 * @throws Exception
	 */
	public static String getSequence(String sequenceName) throws AppException{
		String sequenceID = null;
		StringBuffer buffer = new StringBuffer();
		HBSession hbsess=getHBSession();
		Connection conn=hbsess.connection();
		if(DBUtil.getDBType()==DBType.ORACLE){
			buffer.append("Select " + sequenceName + ".nextval from dual");
		}else{
			buffer.append("Select nextval('" + sequenceName + "') from dual");
			
		}
		
		try{
		PreparedStatement ps = conn.prepareStatement(buffer.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
			sequenceID = rs.getString(1);
		if (rs != null)
			rs.close();
		if (ps != null)
			ps.close();
		}catch(Exception e){
			throw new AppException("数据库处理异常！",e);
		}
		return sequenceID;
	}
	
	/**
	 * 取任意表中任意字段的值
	 * @param field
	 * @param table
	 * @param where
	 * @return
	 * @throws Exception
	 */
	public static String getValueFromTab(String field,String table,String where) throws AppException {
		String value = null;
		StringBuffer buffer = new StringBuffer();
		HBSession hbsess = getHBSession();
		Connection conn=hbsess.connection();
		buffer.append("Select " + field + " from ");
		buffer.append(table);
		if(!where.equals("")){
			buffer.append(" where "+where);
		}
		try{
		PreparedStatement ps = conn.prepareStatement(buffer.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
			value = rs.getString(1);
		if (rs != null)
			rs.close();
		if (ps != null)
			ps.close();
		}catch(Exception e){
			throw new AppException("数据库处理异常！",e);
		}
		return value;
	}
	
	/**
	 * 执行一个sql语句
	 * @param updateSQL
	 * @throws Exception
	 */
	public static int executeUpdate(String updateSQL) throws AppException{
		Statement stmt=null;
		HBSession hbsess=getHBSession();
		try{
			hbsess.flush();
			stmt=hbsess.connection().createStatement();
			return stmt.executeUpdate(updateSQL);
		}catch(Exception e){
			throw new AppException("数据库处理异常！",e);
		}finally{
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new AppException("数据库处理异常！",e);
				}
		}
		
	}
	
	/**
	 * 执行一个sql语句
	 * @param updateSQL
	 * @throws Exception 
	 * @throws Exception
	 */
	public static int executeUpdate(String updateSQL,Object[] args) throws Exception{
		PreparedStatement pstmt=null;
		HBSession hbsess=getHBSession();
		try{
			hbsess.flush();
			pstmt=hbsess.connection().prepareStatement(updateSQL);
			for(int i=0;i<args.length;i++){
				pstmt.setObject(i+1, args[i]);
			}
			return pstmt.executeUpdate();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					throw new AppException("数据库处理异常！",e);
				}
		}
		
	}
	
	
	/**
	 * 执行一个sql语句
	 * @param updateSQL
	 * @throws Exception
	 */
	public static List<Map<String,String>> queryforlist(String querySQL,Object[] args) throws AppException{
		PreparedStatement pstmt=null;
		HBSession hbsess=getHBSession();
		ResultSet rs = null;
		try {
			hbsess.flush();
			pstmt = hbsess.connection().prepareStatement(querySQL);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
			int columnCount = rsmd.getColumnCount(); // 返回此 ResultSet 对象中的列数
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			Map<String, String> rowData = new HashMap<String, String>();
			while (rs.next()) {
				rowData = new HashMap(columnCount);
				for (int i = 1; i <= columnCount; i++) {
					rowData.put(rsmd.getColumnName(i), rs.getString(i));
				}
				list.add(rowData);
			}
			return list;
		} catch (Exception e) {
			throw new AppException("数据库处理异常！", e);
		} finally {
			if (pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					throw new AppException("数据库处理异常！", e);
				}
			}
			if (rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					throw new AppException("数据库处理异常！", e);
				}
			}
				
		}
	}
	
	
	/**
	 * 获取oracle数据库表的注释信息，即表的comments
	 * 
	 * @param tableName
	 *            表名
	 * @return
	 * @throws Exception
	 *             jinwei
	 */
	public static String getOracleTableComments(String tableName) throws AppException{
		String comments = "";
		HBSession hbsess=getHBSession();
		Connection conn=hbsess.connection();
		try{
			PreparedStatement ps = conn.prepareStatement(" select * from user_tab_comments where table_name='"+tableName.toUpperCase()+"' ");
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				comments = rs.getString("comments");
			}
		}catch(Exception e){
			throw new AppException("数据库处理异常！",e);
		}
		return comments;
	}
	/**
	 * 获取oracle数据库表列的注释信息，即表的一列的comments
	 * @param tableName 表名
	 * @param colName 列名
	 * @return
	 * @throws Exception
	 * jinwei
	 */
	public static String getOracleTableColComments(String tableName,String colName) throws AppException{
		String comments = "";
		HBSession hbsess=getHBSession();
		Connection conn=hbsess.connection();
		try{
			PreparedStatement ps = conn.prepareStatement(" select * from user_col_comments where table_name='"+tableName.toUpperCase()+"' and column_name='"+colName.toUpperCase()+"' ");
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				comments = rs.getString("comments");
			}
		}catch(Exception e){
			throw new AppException("数据库处理异常！",e);
		}	
		return comments;
	}

	public static String getCodeName(String codetype, String codevalue,String codename) throws AppException{
		if(codename==null){
			codename = "code_name";
		}
		if(codevalue==null||"".equals(codevalue)){
			return codevalue;
		}
		String desc = "";
		HBSession hbsess=getHBSession();
		Connection conn=hbsess.connection();
		try{
			PreparedStatement ps = conn.prepareStatement(" select "+codename+" from CODE_VALUE where code_type='"+codetype.toUpperCase()+"' and CODE_VALUE='"+codevalue+"' ");
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				desc = rs.getString(codename);
			}
            if(rs!=null){
				rs.close();
			}
            if(ps!=null){
            	ps.close();
            }
		}catch(Exception e){
			throw new AppException("数据库处理异常！",e);
		}	
		return desc;
	}
	
	
	public static String getCodenumber(String codetype, String codevalue,String codename) throws AppException{
		if(codename==null){
			codename = "code_name";
		}
		if(codevalue==null||"".equals(codevalue)){
			return codevalue;
		}
		String desc = "";
		HBSession hbsess=getHBSession();
		Connection conn=hbsess.connection();
		try{
			//String sql =" select CODE_VALUE from CODE_VALUE where code_type='"+codetype.toUpperCase()+"' and "+codename+" ='"+codevalue+"' ";
			PreparedStatement ps = conn.prepareStatement(" select CODE_VALUE from CODE_VALUE where code_type='"+codetype.toUpperCase()+"' and "+codename+" ='"+codevalue+"' ");
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				desc = rs.getString("CODE_VALUE");
			}
            if(rs!=null){
				rs.close();
			}
            if(ps!=null){
            	ps.close();
            }
		}catch(Exception e){
			throw new AppException("数据库处理异常！",e);
		}	
		return desc;
	}
	
	public static String getCodeName(String codetype, String codevalue) throws AppException{
		return getCodeName(codetype, codevalue, null);
	}
	
	public static String getCodeValue(String codetype,String codename) throws AppException {
		if(codename==null||"".equals(codename)){
			return codename;
		}
		String desc = "";
		HBSession hbsess=getHBSession();
		Connection conn=hbsess.connection();
		try{
			PreparedStatement ps = conn.prepareStatement(" select code_value from CODE_VALUE where code_type='"+codetype.toUpperCase()+"' and CODE_NAME='"+codename+"' ");
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				
				desc = rs.getString("code_value");
			}
            if(rs!=null){
				rs.close();
			}
            if(ps!=null){
            	ps.close();
            }
		}catch(Exception e){
			throw new AppException("数据库处理异常！",e);
		}	
		return desc;
		
	}
	
	/**
	 * 执行批量sql
	 * @param list
	 * @throws AppException 
	 */
	public static void  batchSQLexqute(List<String> list) throws AppException {

		Statement stmt=null;
		HBSession hbsess=getHBSession();
		try{
			hbsess.flush();
			Connection connection = hbsess.connection();
			stmt=connection.createStatement();
			
			connection.setAutoCommit(false);//关闭自动提交
			
			
			
			for (String sql : list) {
				stmt.addBatch(sql);
			}
			
			stmt.executeBatch();
			
			connection.setAutoCommit(true);
			
		}catch(Exception e){
			throw new AppException("数据库处理异常！",e);
		}finally{
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new AppException("数据库处理异常！",e);
				}
		}
		
	
	}
	
}
