package com.insigma.siis.local.pagemodel.comm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.siis.local.epsoft.config.AppConfig;
/**
 * 通用查询，只支持sql
 * @author zhangy
 *
 */
public class CommonQueryBS {
	/**
	 * 查询语句
	 */
	String querySQL;
	/**
	 * 数据库连接
	 */
	Connection conn;
	
	PreparedStatement statement = null;
	
	public CommonQueryBS(){
		super();
	}
	
	public CommonQueryBS(Connection conn,String sql){
		this.conn=conn;
		this.querySQL=sql;
	}
	/**
	 * 执行查询，返回的记录集中每条记录为一个HashMap对象
	 */	
	@SuppressWarnings("unchecked")
	public Vector query() throws AppException{
		
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
				HashMap hm=new HashMap();
				for(int j=1;j<=cols;j++){
					//System.out.println((rsmd.getColumnName(j)).toLowerCase()+"-=="+(rs.getObject(j)!=null?rs.getObject(j).toString():""));
					hm.put((rsmd.getColumnName(j)).toLowerCase(),rs.getObject(j)!=null?rs.getObject(j).toString():"");
				}
				rtnVector.add(hm);
			}
			//if(rs!=null) rs.close();
			//if(stmt!=null) stmt.close();
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("查询失败",e);
		}finally{
			try{
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
			}catch(SQLException ex){
				throw new AppException(ex.toString());
			}
		}
		return rtnVector;	
	}
	
	public PreparedStatement getPreparedStatement(Connection connection,String sql) throws AppException{
		try{
			this.conn = connection;
			this.statement = this.conn.prepareStatement(sql);
		}catch(SQLException e){
			try{
				if(statement!=null)
					statement.close();
			}catch(SQLException ex){
				throw new AppException(ex.toString());
			}
		}
		return statement;
	}
	
	public void isColse() throws AppException{
		try{
			if(statement!=null)
				statement.close();
		}catch(SQLException ex){
			throw new AppException(ex.toString());
		}
	}
//	/**
//	 * 返回查询记录集，每条记录为一个beanClass对象
//	 */	
//	public Vector query(Class beanClass) throws AppException{
//		Vector rsVector=this.query();
//		MessageAssembler ma=new MessageAssembler();
//		HashMap tempHM=new HashMap();
//		tempHM.put("tempRS",rsVector);
//		Vector rtnVector=ma.disassembleForPageQuery(tempHM,"tempRS",beanClass);
//	
//		return rtnVector;
//	}

	/**
	 * @$comment 通过特定SQL获取指定的事件信息
	 * @param sql 拼装的sql语句
	 * @return List<HashMap>
	 * @throws AppException
	 * @author lixn
	 */
	@SuppressWarnings("unchecked")
	public static List<HashMap> getQueryInfoByManulSQL(String sql) throws AppException{
		HBSession sess = HBUtil.getHBSession();
 		CommonQueryBS query=new CommonQueryBS();
		query.setConnection(sess.connection());	
 		query.setQuerySQL(sql);
		Vector<?> vector=query.query();
		Iterator<?> iterator = vector.iterator();
		List<HashMap> hmLst=new java.util.ArrayList<HashMap>();
		while (iterator.hasNext())
        {
			HashMap tmp= (HashMap)iterator.next();		
			hmLst.add(tmp);
		}
		return hmLst;		
	}
	
	/**
	 * @param connection
	 */
	public void setConnection(Connection connection) {
		conn = connection;
	}

	/**
	 * @param string
	 */
	public void setQuerySQL(String string) {
		querySQL = string;
	}
	
	public static void systemOut(String string){
		String aaa005 = AppConfig.LOG_CONTROL;
		if(aaa005.equals("ON")){
			System.out.println(string);
		}
	}
	/**
	*====================================================================================================
	* 方法名称:sysOutRtn 是否输出SQL部分信息，并返回原SQL<br>
	* 方法创建日期:未知<br>
	* 方法创建人员:未知<br>
	* 方法最后修改日期:2019年11月14日<br>
	* 方法最后修改人员:未知<br>
	* 方法功能描述:该方法为补充systemOut方法，主要特点存在返回值<br>
	* 	（因存在大部分输出SQL后并执行，导致代码增多，调用该方法可直接执行，且执行前输出SQL脚本内容）
	*====================================================================================================
	* @param string 任意字符串
	*/
	public static String sysOutRtn(String string){
		String aaa005 = AppConfig.LOG_CONTROL;
		if(aaa005.equals("ON")){
			System.out.println(new Date() + "：" +string);
		}
		return string;
	}
}
