package com.utils;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.Vector;



import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.util.BASE64Encoder;
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
				String value="";
				for(int j=1;j<=cols;j++){
					//zxb,20150108,调整
					//hm.put((rsmd.getColumnName(j)).toLowerCase(),rs.getString(j));
					
					if(rsmd.getColumnType(j)==Types.CLOB)
						value=clobToString(rs.getClob(j));
					else if(rsmd.getColumnType(j)==Types.BLOB)
						value=blobToString(rs.getBlob(j));
					else
						value=rs.getString(j);
					hm.put((rsmd.getColumnName(j)).toLowerCase(),value);
				}
				rtnVector.add(hm);
			}
			//if(rs!=null) rs.close();
			//if(stmt!=null) stmt.close();
		}catch(Exception e){
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
	
	
	/**
     * 将"Clob"型数据转换成"String"型数据
     * 需要捕获"SQLException","IOException"
     * prama:    colb1 将被转换的"Clob"型数据
     * return:    返回转好的字符串
     * @throws SQLException 
     * @throws IOException */
    private static String clobToString(Clob colb) throws SQLException, IOException
    {
        String outfile = "";
        if(colb != null){
            //oracle.sql.CLOB clob = (oracle.sql.CLOB)colb1;        
            java.io.Reader is = colb.getCharacterStream();
            java.io.BufferedReader br = new java.io.BufferedReader(is);          
            String s = br.readLine();
            while (s != null) {
                outfile += s;
                s = br.readLine();
            }
            is.close();
            br.close();    
        }
        return  outfile;        
    }
     
    /**
     * 将"Blob"型数据转换成"String"型数据
     * 需要捕获"SQLException","IOException"
     * prama:    colb1 将被转换的"Clob"型数据
     * return:    返回转好的字符串
     * @throws SQLException 
     * @throws IOException */
    private static String blobToString(Blob blob) throws SQLException, IOException
    {
        // Blob 是二进制文件，转成文字是没有意义的
        // 所以根据传输协议，EzManager的传输协议是无法支持的
           byte[] base64;   
           String newStr = ""; //返回字符串   
            
           if(blob!=null)
           {
               try {   
                   base64 = org.apache.commons.io.IOUtils.toByteArray(blob.getBinaryStream());   
                   newStr = new BASE64Encoder().encodeBuffer(base64);   
                } catch (IOException e) {          
                    e.printStackTrace();   
                } catch (SQLException e) {         
                    e.printStackTrace();   
                }      
           }
           return newStr;      
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
	
	/**
	 * @$comment 查询
	 * @param sql 拼装的sql语句
	 * @return List<HashMap>
	 * @throws AppException
	 */
	@SuppressWarnings("unchecked")
	public static List<HashMap<String, Object>> getListBySQL(String sql) throws AppException{
		HBSession sess = HBUtil.getHBSession();
 		CommonQueryBS query=new CommonQueryBS();
		query.setConnection(sess.connection());	
 		query.setQuerySQL(sql);
		Vector<?> vector=query.query();
		Iterator<?> iterator = vector.iterator();
		List<HashMap<String, Object>> hmLst=new java.util.ArrayList<HashMap<String, Object>>();
		while (iterator.hasNext())
        {
			HashMap<String, Object> tmp= (HashMap<String, Object>)iterator.next();		
			hmLst.add(tmp);
		}
		return hmLst;		
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> getMapBySQL(String sql) throws AppException{
		HBSession sess = HBUtil.getHBSession();
 		CommonQueryBS query=new CommonQueryBS();
		query.setConnection(sess.connection());	
 		query.setQuerySQL(sql);
		Vector<?> vector=query.query();
		Iterator<?> iterator = vector.iterator();
		while (iterator.hasNext())
        {
			HashMap<String, Object> tmp= (HashMap<String, Object>)iterator.next();		
			return tmp;
		}
		return null;		
	}
	
	/**
	 * 生成16位主键
	 * @return
	 */
	public static String getUUID(){		 
		 String str = UUID.randomUUID().toString();
		 str = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18);
		 return str;
	 }
	
}
