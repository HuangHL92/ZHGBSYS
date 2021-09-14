package com.insigma.siis.local.comm.search;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;

import java.sql.*;
import java.util.Date;
import java.util.*;

/**
 * ͨ�ò�ѯ��ֻ֧��sql
 * @author zhangy
 *
 */
public class CommonMethodBS {
	/**
	 * ��ѯ���
	 */
	String querySQL;
	/**
	 * ���ݿ�����
	 */
	Connection conn;
	
	PreparedStatement statement = null;
	
	public CommonMethodBS(){
		super();
	}
	
	public CommonMethodBS(Connection conn, String sql){
		this.conn=conn;
		this.querySQL=sql;
	}
	/**
	 * ִ�в�ѯ�����صļ�¼����ÿ����¼Ϊһ��HashMap����
	 */	
	@SuppressWarnings("unchecked")
	public Vector query() throws AppException {
		
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
	
	public PreparedStatement getPreparedStatement(Connection connection,String sql) throws AppException {
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
	
	public void isColse() throws AppException {
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
	public static List<HashMap> getQueryInfoByManulSQL(String sql) throws AppException {
		HBSession sess = HBUtil.getHBSession();
 		CommonMethodBS query=new CommonMethodBS();
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
	
	
	public static void systemOut(Object string){
		String aaa005 = AppConfig.LOG_CONTROL;
		if(aaa005.equals("ON")){
			System.out.println(new Date() + "��" +string);
		}
	}
	
	public static void systemOut(String string){
		String aaa005 = AppConfig.LOG_CONTROL;
		if(aaa005.equals("ON")){
			System.out.println(new Date() + "��" +string);
		}
	}
	
	public static String sysOutRtn(String string){
		String aaa005 = AppConfig.LOG_CONTROL;
		if(aaa005.equals("ON")){
			System.out.println(new Date() + "��" +string);
		}
		return string;
	}
	
	
	/**
	 * @$comment ��ѯ
	 * @param sql ƴװ��sql���
	 * @return List<HashMap>
	 * @throws AppException
	 */
	@SuppressWarnings("unchecked")
	public static List<HashMap<String, Object>> getListBySQL(String sql) throws AppException {
		HBSession sess = HBUtil.getHBSession();
 		CommonMethodBS query=new CommonMethodBS();
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
	public static HashMap<String, Object> getMapBySQL(String sql) throws AppException {
		HBSession sess = HBUtil.getHBSession();
 		CommonMethodBS query=new CommonMethodBS();
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
	public static String getUUID16(){		 
		 String str = UUID.randomUUID().toString();
		 str = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18);
		 return str;
	 }
	
	/**
	 * ����32λ����
	 * @return
	 */
	public static String getUUID32(){		 
		 String str = UUID.randomUUID().toString();
		 str = str.replace("-", "");
		 return str;
	 }
	
	/**
     * �ַ���������
     * @param v
     * @return
     */
    public String str(Object v){
	           return v==null?"":v.toString();
    }
    
    /**
     * ʱ���ַ�����ȡƴ�Ӵ�����
     * @param v
     * @return
     */
    public String strsub(Object v){
    	String	string="";
    	if(v==null||"".equals(v)){
    		string="";
    	}else{
      	 string=v.toString();
    	 String string1=string.substring(0, 4);
    	 String string2=string.substring(4,6);
    	 string=string1+"."+string2;
    	}
	    
    	return string;
    }
    
    /**
     * ʱ���ַ�����ȡƴ�Ӵ�����
     * @param v
     * @return
     */
    public String substring(Object v){
    	String	string="";
    	if(v==null||"".equals(v)){
    		string="";
    	}else{
      	 string=v.toString();
    	 String string1=string.substring(0, 4);
    	 String string2=string.substring(4,6);
    	 string=string1+string2;
    	}	    
    	return string;
    }
    
    /**
     * ʱ���ַ�����ȡƴ�Ӵ�����yy.mm
     * @param v
     * @return
     */
    public String substringYymm(Object v){
    	String	string="";
    	if(v==null||"".equals(v)){
    		string="";
    	}else{
      	 string=v.toString();
    	 String string1=string.substring(2, 4);
    	 String string2=string.substring(4,6);
    	 string=string1+"."+string2;
    	}	    
    	return string;
    }
    
    /**
     * ʱ���ַ����滻������
     * @param v
     * @return
     */
    public String strReplace(Object v){
    	String	string="";
    	if(v==null){
    		string="";
    	}else{
      	 string=v.toString();
      	 string=string.replace("-", ".");
    	}	    
    	return string;
    }
    
    /**
     * ����codeType��codeValue��ѯcodeName
     * @param codeType
     * @param codeValue
     * @return
     * @throws AppException
     */
    public String getCodeName(String codeType,Object codeValue) throws AppException {
    	String sql="select code_name from code_value where code_type='"+codeType+"' and code_value='"+codeValue+"'";
    	HashMap<String, Object> map=getMapBySQL(sql);
    	String code_name="";
    	if(map!=null){
    		 code_name=map.get("code_name").toString();
    	}
		return code_name;
    	
    }
    
    /**
     * ����b0111��pb0208��ѯpb0202
     * @param b0111
     * @param pb0208
     * @return
     * @throws AppException
     */
    public String getPb0202(String b0111,String pb0208) throws AppException {
    	String sql="select pb0202 from pb02 where b0111='"+b0111+"' and pb0208='"+pb0208+"'";
    	HashMap<String, Object> map=getMapBySQL(sql);
    	String pb0202="";
    	if(map!=null){
    		pb0202=map.get("pb0202").toString();
    	}
		return pb0202;
    	
    }
    
    /**
     * ����b0111��ѯb0101
     * @param b0111
     * @return
     * @throws AppException
     */
    public static String getB0101ByB0111(String b0111) throws AppException {
    	String sql="select b0101 from b01 where b0111='"+b0111+"'";
    	HashMap<String, Object> map=getMapBySQL(sql);
    	String b0101="";
    	if(map!=null){
    		b0101=map.get("b0101").toString();
    	}
		return b0101;
    	
    }
    
    /**
     * ����a0000��ѯa0180a
     * @param b0111
     * @return
     * @throws AppException
     */
    public static String geta018011Bya0000(String a0000) throws AppException {
    	String sql="select a0801a  from a08  a0 where a0801a like '%�о���%' and A0000 ='"+a0000+"'";
    	HashMap<String, Object> map=getMapBySQL(sql);
    	String a0801a="";
    	if(map!=null){
    		a0801a=map.get("a0801a").toString();
    	}
		return a0801a;
    	
    }
    
    /**
     * ����a0000��ѯA0602
     * @param b0111
     * @return
     * @throws AppException
     */
    public static String getA0602Bya0000(String a0000) throws AppException {
    	String sql="select  A0602 from a06  WHERE SUBSTR(A0601,3) <=3 AND A0000 ='"+a0000+"'";
    	
    	HashMap<String, Object> map=getMapBySQL(sql);
    	String A0602="";
    	if(map!=null){
    		A0602=map.get("a0602").toString();
    	}
		return A0602;
    	
    }
    
}
