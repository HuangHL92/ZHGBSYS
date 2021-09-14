package com.insigma.siis.local.pagemodel.comm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;

public class CommQuery {
	/**
	 * 查询语句
	 */
	String querySQL=null;
	/**
	 * 数据库连接
	 */
	 Connection conn=null;
	
	PreparedStatement statement = null;
	/**
	 * @$comment 查询
	 * @param sql 拼装的sql语句
	 * @return List<HashMap>
	 * @throws AppException
	 */
	public List<HashMap<String, Object>> getListBySQL(String sql) throws AppException{
		
		return getListBySQL(sql, false);		
	}
	
	public List<HashMap<String, String>> getListBySQL2(String sql) throws AppException{
		
		return getListBySQL2(sql, true);		
	}
	
	/**
	 * @$comment 查询
	 * @param sql 拼装的sql语句
	 * @return List<HashMap>
	 * @throws AppException
	 */
	public List<HashMap<String, Object>> getListBySQL(String sql,boolean isOrder) throws AppException{
		HBSession sess = HBUtil.getHBSession();
		this.setConnection(sess.connection());	
		this.setQuerySQL(sql);
		Vector<?> vector=this.query(isOrder);
		Iterator<?> iterator = vector.iterator();
		List<HashMap<String, Object>> hmLst=new java.util.ArrayList<HashMap<String, Object>>();
		while (iterator.hasNext())
		{
			HashMap<String, Object> tmp= (HashMap<String, Object>)iterator.next();		
			hmLst.add(tmp);
		}
		return hmLst;		
	}
	/**
	 * @$comment 查询
	 * @param sql 拼装的sql语句
	 * @return List<HashMap>
	 * @throws AppException
	 */
	public List<HashMap<String, String>> getListBySQL2(String sql,boolean isOrder) throws AppException{
		HBSession sess = HBUtil.getHBSession();
		this.setConnection(sess.connection());	
		this.setQuerySQL(sql);
		Vector<?> vector=this.query(isOrder);
		Iterator<?> iterator = vector.iterator();
		List<HashMap<String, String>> hmLst=new java.util.ArrayList<HashMap<String, String>>();
		while (iterator.hasNext())
		{
			HashMap<String, String> tmp= (HashMap<String, String>)iterator.next();		
			hmLst.add(tmp);
		}
		return hmLst;		
	}
	
	/**
	 * 执行查询，返回的记录集中每条记录为一个HashMap对象
	 * @param isOrder 
	 */	
	@SuppressWarnings("unchecked")
	public Vector query(boolean isOrder) throws AppException{
		Statement stmt=null;
		ResultSet rs=null;
		ResultSetMetaData rsmd=null;
		Vector rtnVector=new Vector();
		int cols;
		try{
			stmt=conn.createStatement();
			rs=stmt.executeQuery(querySQL);
			rsmd=rs.getMetaData();
			cols=rsmd.getColumnCount();
			while(rs.next()){
				HashMap hm= isOrder?new LinkedHashMap():new HashMap();
				for(int j=1;j<=cols;j++){
					hm.put((rsmd.getColumnName(j)).toLowerCase(),rs.getString(j));
				}
				rtnVector.add(hm);
			}
		}catch(Exception e){
			throw new AppException("查询失败",e);
		}finally{
			try{
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			}catch(SQLException ex){
				throw new AppException(ex.toString());
			}
		}
		return rtnVector;	
	}
	
	public void setConnection(Connection connection) {
		conn = connection;
	}
	
	public Connection getConnection() {
		HBSession sess = HBUtil.getHBSession();
		
		return sess.connection();
	}
	/**
	 * @param string
	 */
	public void setQuerySQL(String string) {
		querySQL = string;
	}
	
}
