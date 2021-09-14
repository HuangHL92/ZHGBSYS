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
 * ͨ�ò�ѯ��ֻ֧��sql
 * @author zhangy
 *
 */
public class CommonQueryBS {
	/**
	 * ��ѯ���
	 */
	String querySQL;
	/**
	 * ���ݿ�����
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
	 * ִ�в�ѯ�����صļ�¼����ÿ����¼Ϊһ��HashMap����
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
					//zxb,20150108,����
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
			throw new AppException("��ѯʧ��",e);
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
     * ��"Clob"������ת����"String"������
     * ��Ҫ����"SQLException","IOException"
     * prama:    colb1 ����ת����"Clob"������
     * return:    ����ת�õ��ַ���
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
     * ��"Blob"������ת����"String"������
     * ��Ҫ����"SQLException","IOException"
     * prama:    colb1 ����ת����"Clob"������
     * return:    ����ת�õ��ַ���
     * @throws SQLException 
     * @throws IOException */
    private static String blobToString(Blob blob) throws SQLException, IOException
    {
        // Blob �Ƕ������ļ���ת��������û�������
        // ���Ը��ݴ���Э�飬EzManager�Ĵ���Э�����޷�֧�ֵ�
           byte[] base64;   
           String newStr = ""; //�����ַ���   
            
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
//	 * ���ز�ѯ��¼����ÿ����¼Ϊһ��beanClass����
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
	 * @$comment ��ѯ
	 * @param sql ƴװ��sql���
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
	 * ����16λ����
	 * @return
	 */
	public static String getUUID(){		 
		 String str = UUID.randomUUID().toString();
		 str = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18);
		 return str;
	 }
	
}
