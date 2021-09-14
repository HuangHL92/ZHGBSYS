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
	 * @$comment ͨ���ض�SQL��ȡָ�����¼���Ϣ
	 * @param sql ƴװ��sql���
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
	* ��������:sysOutRtn �Ƿ����SQL������Ϣ��������ԭSQL<br>
	* ������������:δ֪<br>
	* ����������Ա:δ֪<br>
	* ��������޸�����:2019��11��14��<br>
	* ��������޸���Ա:δ֪<br>
	* ������������:�÷���Ϊ����systemOut��������Ҫ�ص���ڷ���ֵ<br>
	* 	������ڴ󲿷����SQL��ִ�У����´������࣬���ø÷�����ֱ��ִ�У���ִ��ǰ���SQL�ű����ݣ�
	*====================================================================================================
	* @param string �����ַ���
	*/
	public static String sysOutRtn(String string){
		String aaa005 = AppConfig.LOG_CONTROL;
		if(aaa005.equals("ON")){
			System.out.println(new Date() + "��" +string);
		}
		return string;
	}
}
