package com.insigma.siis.local.pagemodel.customquery;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.Vector;
import javax.sql.rowset.serial.SerialException;
import org.hibernate.Query;
import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.CodeTableCol;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.Customquery;
import com.insigma.siis.local.business.entity.Customquerylist;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

/**
 * @author lixy
 * ��ϲ�ѯ������
 */

public class  CustomQueryBS {
	public  List<CodeTableCol> ctcList=null;//��Ϣ��
	public List<Map<String,String>> collectionList=null;//��Ϣ��������������Ϣ
	public static String logicSymbols="";
	private String sid;
	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public CustomQueryBS() {
		    ctcList=getCtcList();
		    collectionList=getCollectionList();
		    
	}
	
	/**
	 * @$comment ���ز�ѯ��Ŀ����
	 * @param code ��Ϣ������
	 * @return List<CodeTableCol>
	 * @throws 
	 * @author lixy
	 */
    public static TreeMap<String, String>  getCtcListByCollectionCode(String code,List<CodeTableCol> ctcList){
    	TreeMap<String, String>  map=null;
    	
    	if(ctcList!=null){
    		map=new TreeMap();
        	for (CodeTableCol c : ctcList) {
				if(c.getColLectionName().equals(code) && "1".equals(c.getIsZbx()))//������Ϣ�������ж��Ƿ��ֶ����Ƿ����������Ϣ��
					map.put(c.getColCode(), c.getColName());
			}
        }
    	
        return map;
    }
    
    
	/**
	 * @$comment �����ѯ���
	 * @param 
	 * @return 
	 * @throws 
	 * @author lixy
	 */    
    public static Customquery saveOrUodateCq(String queryId,String queryName,String querySql,String queryDes,String LoginName,String data) throws AppException{
    	HBSession sess = HBUtil.getHBSession();
    	Customquery cq=new Customquery();
    	if(queryId!=null&&!queryId.equals(""))
    	    cq.setQueryid(queryId);
    	else
    	    cq.setQueryid( UUID.randomUUID().toString());
    	cq.setCreatetime(new Date());
    	cq.setQueryname(queryName);//��ѯ��������
    	cq.setQuerysql(querySql);//��ѯsql
    	cq.setQuerydescription(queryDes);//����sql
    	cq.setLoginname(LoginName);//�û���¼��
    	cq.setGridstring(data);//json����grid������
    	sess.saveOrUpdate(cq);
    	return cq;
    }
    
	/**
	 * @param osql 
	 * @$comment �����б�
	 * @param 
	 * @return 
	 * @throws UnsupportedEncodingException 
	 * @throws SQLException 
	 * @throws SerialException 
	 * @throws 
	 * @author lixy
	 */    
    public static void saveList(String listName,String listCount,String listData,String LoginName, String sql,String cqli) throws AppException, UnsupportedEncodingException, SerialException, SQLException{
    	HBSession sess = HBUtil.getHBSession();
    	Customquerylist cl=new Customquerylist();
    	cl.setCqli(UUID.randomUUID().toString());
    	cl.setListtime(new Date());
        //cl.setListcount(listCount);
        //cl.setListdata(Hibernate.createBlob(listData.getBytes()));
        cl.setLoginname(LoginName);
        cl.setListname(listName);
		sess.saveOrUpdate(cl);
		
    	
		try {
			if(cqli != null && !"".equals(cqli)){	//���ǲ��� ɾ��ԭ�б�
	    		HBUtil.executeUpdate("delete from listinfo where cqli = '"+cqli+"' ");
				sess.flush();
				HBUtil.executeUpdate("delete from customquerylist where cqli = '"+cqli+"' ");
				sess.flush();
			}
			HBUtil.executeUpdate("insert into listinfo select '"+cl.getCqli()+"',a0000 from ("+sql+") aa");
			sess.flush();
			Object ct = sess.createSQLQuery("select count(1) from listinfo where cqli='"+cl.getCqli()+"'").uniqueResult();
			cl.setListcount(ct.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("�����б�����ʧ��");
		}
		

    }
    /**
     * @author wangs2
     *        �����б�
     * @param listName	����
     * @param LoginName	��¼��
     * @param sql
     * @param cqli		uuid
     * @param parentid		��ID
     * @throws AppException
     * @throws UnsupportedEncodingException
     * @throws SerialException
     * @throws SQLException
     */
    public static void saveListNew(String listName,String LoginName, String sql,String cqli,String parentid) throws AppException, UnsupportedEncodingException, SerialException, SQLException{
    	HBSession sess = HBUtil.getHBSession();
    	Customquerylist cl=new Customquerylist();
    	cl.setCqli(UUID.randomUUID().toString());
    	cl.setListname(listName);
        cl.setLoginname(LoginName);
        cl.setParentid(parentid);
        cl.setListtime(new Date());
		sess.saveOrUpdate(cl);
    	
		try {
			if(cqli != null && !"".equals(cqli)){	//���ǲ��� ɾ��ԭ�б�
	    		HBUtil.executeUpdate("delete from listinfo where cqli = '"+cqli+"' ");
				sess.flush();
				HBUtil.executeUpdate("delete from customquerylist where cqli = '"+cqli+"' ");
				sess.flush();
			}
			HBUtil.executeUpdate("insert into listinfo select '"+cl.getCqli()+"',a0000 from ("+sql+") aa");
			String sql1="select count(1) from listinfo where cqli='"+cl.getCqli()+"'";
			Object ct = sess.createSQLQuery("select count(1) from listinfo where cqli='"+cl.getCqli()+"'").uniqueResult();
			cl.setListcount(ct.toString());
			CustomQueryPageModel.LISTADDCCQLI=cl.getCqli();
			CustomQueryPageModel.LISTADDNAME=cl.getListname();
			sess.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("�����б�����ʧ��");
		}
    }
    public static void updateList(String listName,String listCount,String listData,String LoginName, String sql,String cqli) throws AppException{
    	Customquerylist cql=(Customquerylist)HBUtil.getHBSession().get(Customquerylist.class, cqli);
    	if(cql == null || "".equals(cql)){
    		System.out.println(cqli+"�������ݣ��޷����ǣ�");
    		throw new AppException(cqli+"�������ݣ��޷����ǣ�");
    	}
    	HBSession sess = HBUtil.getHBSession();
    	Object PerID =sess.createSQLQuery("select PARENTID from customquerylist where cqli='"+cqli+"'").uniqueResult();
    	String parentid=(PerID==null || "".equals(PerID))?"-1":PerID.toString();
    	Customquerylist cl=new Customquerylist();
    	cl.setCqli(UUID.randomUUID().toString());
    	cl.setListtime(new Date());
        cl.setLoginname(LoginName);
        cl.setListname(listName);
        cl.setParentid(parentid);
		sess.saveOrUpdate(cl);
		try {
			if(cqli != null && !"".equals(cqli)){	//���ǲ��� ɾ��ԭ�б�
	    		HBUtil.executeUpdate("delete from listinfo where cqli = '"+cqli+"' ");
				sess.flush();
				HBUtil.executeUpdate("delete from customquerylist where cqli = '"+cqli+"' ");
				sess.flush();
			}
			HBUtil.executeUpdate("insert into listinfo select '"+cqli+"',a0000 from ("+sql+") aa");
			Object ct = sess.createSQLQuery("select count(1) from listinfo where cqli='"+cqli+"'").uniqueResult();
			cl.setListcount(ct.toString());
			HBUtil.executeUpdate("UPDATE customquerylist SET cqli='"+cqli+"' where cqli='"+cl.getCqli()+"'");
			sess.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("�����б�����ʧ��");
		}
		

    }
    /**
	 * @param osql 
	 * @$comment �������ѱ����б�
	 * @param 
	 * @return 
	 * @throws UnsupportedEncodingException 
	 * @throws SQLException 
	 * @throws SerialException 
	 * @throws 
	 * @author lixy
	 */    
    public static void saveSWTXList(String listName,String listCount,String listData,String LoginName, String sql) throws AppException, UnsupportedEncodingException, SerialException, SQLException{
    	HBSession sess = HBUtil.getHBSession();
    	Customquerylist cl=new Customquerylist();
    	cl.setCqli(UUID.randomUUID().toString());
    	cl.setListtime(new Date());
        //cl.setListcount(listCount);
        //cl.setListdata(Hibernate.createBlob(listData.getBytes()));
        cl.setLoginname(LoginName);
        cl.setListname(listName);
		sess.saveOrUpdate(cl);
		
    	
		try {
			Query query=sess.createSQLQuery("select 1 from customquerylist where listname='"+listName+"'");
	    	List<Object> list=query.list();
	    	if(list.size() > 0){
	    		Query query1=sess.createSQLQuery("select cqli from customquerylist  where  listname = '"+listName+"'");
	    		
	    		List<Object> list2 = query1.list();
	    		
	    		String cqli_old = list2.get(0).toString();
	    		
	    		HBUtil.executeUpdate("delete from listinfo where cqli = '"+cqli_old+"' ");
				sess.flush();
				HBUtil.executeUpdate("delete from customquerylist where cqli = '"+cqli_old+"' ");
				sess.flush();
				
	    	}
	    	
	    	String sqlNew = "insert into listinfo select '"+cl.getCqli()+"',a0000 from("+sql+") tt";
	    	
	    	if(DBUtil.getDBType()==DBType.MYSQL){
	    		sqlNew = sqlNew.replace("t.a0107", "substring(CONCAT(a0107,'01'),1,8)");
	    	}
	    	
			HBUtil.executeUpdate(sqlNew);
			
			sess.flush();
			Object ct = sess.createSQLQuery("select count(1) from listinfo where cqli='"+cl.getCqli()+"'").uniqueResult();
			cl.setListcount(ct.toString());
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new AppException("���ڷǷ����ڣ��б��޷����棡");
		}
		

    }
    
	/**
	 * @$comment ɾ���̶�������ѯ�е���
	 * @param 
	 * @return 
	 * @throws 
	 * @author lixy
	 */    
    public static void delCq(String queryId) throws AppException{
    	HBSession sess = HBUtil.getHBSession();
    	Customquery cq=new Customquery();
    	cq.setQueryid(queryId);
        sess.delete(cq);
    }
    
	/**
	 * @$comment ɾ���б�
	 * @param 
	 * @return 
	 * @throws 
	 * @author lixy
	 */    
    public static void delCl(String cqli) throws AppException{
    	HBSession sess = HBUtil.getHBSession();
        Customquerylist cql=new Customquerylist();
        cql.setCqli(cqli);
        sess.delete(cql);
        HBUtil.executeUpdate("delete from listinfo where cqli='"+cqli+"'");
    }
    /**
	 * @$comment ɾ���б�
	 * @param 
	 * @return 
	 * @throws 
	 * @author wangs2
	 */    
    public static void delClNew(String cqli) throws AppException{
    	HBSession sess = HBUtil.getHBSession();
        Customquerylist cql=new Customquerylist();
        cql.setCqli(cqli);
        sess.delete(cql);
        HBUtil.executeUpdate("delete from listinfo where cqli='"+cqli+"'");
        delClChildrens(cqli);
    }
    
    public static void delClChildrens(String cqli) throws AppException{
    	HBSession sess = HBUtil.getHBSession();
    	String sql="SELECT CQLI,LISTNAME,LISTCOUNT,LISTTIME,PARENTID  FROM CUSTOMQUERYLIST WHERE PARENTID='"+cqli+"' ORDER BY LISTTIME DESC";
		try {
			List<Object[]> listres = sess.createSQLQuery(sql).list();
			if(listres.size() >0){
				for(Object[] data : listres){
					String perID = data[0]+"";
					delClNew(perID);
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
    }
	/**
	 * @$comment ɾ���ϴβ�ѯsql
	 * @param 
	 * @return 
	 * @throws 
	 * @author lixy
	 */    
    public static void delLastTimeCq() throws AppException{
    	HBSession sess = HBUtil.getHBSession();
    	Query query=sess.createSQLQuery("delete from customquery where queryname='�ϴβ�ѯ'");
    	query.executeUpdate();
    }
    
	/**
	 * @$comment ɾ����������sql
	 * @param 
	 * @return 
	 * @throws 
	 * @author lixy
	 */    
    public static void delComm() throws AppException{
    	HBSession sess = HBUtil.getHBSession();
    	Query query=sess.createSQLQuery("delete from customquery where queryname='��������'");
    	query.executeUpdate();
    }
    
	
	/**
	 * @$comment ���ز�ѯ��Ϣ��
	 * @param 
	 * @return List<Map<String, String>>
	 * @throws AppException 
	 * @throws 
	 * @author lixy
	 */
    private List<Map<String, String>> getCollectionList(){
    	List<Map<String,String>> list=null;
    	try{
    	HBSession session = HBUtil.getHBSession();
		CommonQueryBS query = new CommonQueryBS();
		query.setConnection(session.connection());
		String sql = "select  TABLE_CODE,COL_LECTION_NAME  from code_table_col where COL_LECTION_NAME is not null group by TABLE_CODE,COL_LECTION_NAME  ORDER BY TABLE_CODE";
		query.setQuerySQL(sql);
		Vector<?> vector = query.query();
		Iterator<?> iterator = vector.iterator();
		if (iterator.hasNext()) {
			list=new ArrayList<Map<String,String>>();
			while (iterator.hasNext()) {

				Map<String, String> tmp = (Map<String, String>) iterator.next();
				list.add(tmp);
                
			}
		}
    	}
    	catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    	return list;
    }
    
    /**
	 * @$comment ���ر�����б�
	 * @param loginname��¼�û���
	 * @return List<Map<String, String>>
	 * @throws AppException 
	 * @throws 
	 * @author lixy
	 */
    List<Map<String, String>> getQueryList(String loginname){
    	List<Map<String,String>> list=null;
    	try{
    	HBSession session = HBUtil.getHBSession();
		CommonQueryBS query = new CommonQueryBS();
		query.setConnection(session.connection());
		//query.setConnection(session.connection());
		String sql = "select cqli,listname,listcount,listtime  from customquerylist where loginname='"+loginname+"' order by listtime desc";
		query.setQuerySQL(sql);
		Vector<?> vector = query.query();
		Iterator<?> iterator = vector.iterator();
		//ʹ�õ���������ѯ�������ݷ���list��
		if (iterator.hasNext()) {
			list=new ArrayList<Map<String,String>>();
			while (iterator.hasNext()) {

				Map<String, String> tmp = (Map<String, String>) iterator.next();
				list.add(tmp);
                
			}
		}
    	}
    	catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    	return list;
    }
    
	/**
	 * @$comment ���ع̶���ѯ����
	 * @param loginname��¼�û���
	 * @return List<Map<String, String>>
	 * @throws AppException 
	 * @throws 
	 * @author lixy
	 */
    public static List<Map<String, String>> getCustomSqlList(String loginname){
    	return getCustomSqlList(loginname,false);
    }
    /**
	 * @$comment ���ع̶���ѯ����
	 * @param loginname��¼�û���
	 * @return List<Map<String, String>>
	 * @throws AppException 
	 * @throws 
	 * @author lixy
	 */
    public static List<Map<String, String>> getCustomSqlList(String loginname,boolean isShare){
    	List<Map<String,String>> list=null;
    	try{
    	HBSession session = HBUtil.getHBSession();
		CommonQueryBS query = new CommonQueryBS();
		query.setConnection(session.connection());
		String sql;
		if(isShare){
			sql = "select queryid,querysql,querydescription,queryName,sharename from customquery where sharename is not null";
		}else{
			sql = "select queryid,querysql,querydescription,queryName from customquery where loginname='"+loginname+"' and sharename is null";
		}
		  
		query.setQuerySQL(sql);
		Vector<?> vector = query.query();
		Iterator<?> iterator = vector.iterator();
		if (iterator.hasNext()) {
			list=new ArrayList<Map<String,String>>();
			while (iterator.hasNext()) {

				Map<String, String> tmp = (Map<String, String>) iterator.next();
				list.add(tmp);
                
			}
		}
    	}
    	catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    	return list;
    }
	/**
	 * @$comment �������п�����Ϊ�����Ĳ�ѯ����Ϣ
	 * @param 
	 * @return List<CodeTableCol>
	 * @throws 
	 * @author lixy
	 */
    private static List<CodeTableCol> getCtcList(){
    	List<CodeTableCol> list=null;
    	
    	HBSession sess = HBUtil.getHBSession();
//		Query query= sess.createQuery("from CodeTableCol where COL_LECTION_NAME is not null");
		Query query= sess.createQuery("from CodeTableCol ");
		list=query.list();
		
    	return list;
    }
    
	/**
	 * @$comment �����ֶ����Ʋ�ѯ��ѯָ�������
	 * @param colCode
	 * @return CodeTableCol
	 * @throws 
	 * @author lixy
	 */
    public static CodeTableCol getCtc(String colCode,List<CodeTableCol> ctcList){
    	CodeTableCol ctc=null;
    	
        if(ctcList!=null){
        	for (CodeTableCol c : ctcList) {//�����ֶ������ж��Ƿ��ֶ����Ƿ����������Ϣ��
				if(c.getColCode().equals(colCode)){
					ctc=c;
					break;
				}					
			}
        }
		
    	return ctc;
    }
    
	/**
	 * @$comment ����ָ������Ϣ��ѯ��Ӧ��ֵ�б�
	 * @param colCode
	 * @return CodeTableCol
	 * @throws AppException 
	 * @throws 
	 * @author lixy
	 */
    public static TreeMap<String, String>  getAa10List(String colCode,List<CodeTableCol> ctcList) throws AppException{
    	TreeMap<String, String>  map=null;
    	
        if(ctcList!=null){
        	for (CodeTableCol c : ctcList) {
				if(c.getColCode().equals(colCode))
				{
					
					HBSession session = HBUtil.getHBSession();
					CommonQueryBS query = new CommonQueryBS();
					query.setConnection(session.connection());
					String sql = "select aaa102,aaa103 from v_aa10 where aaa100='"+c.getCodeType()+"'";
					query.setQuerySQL(sql);
					Vector<?> vector = query.query();
					Iterator<?> iterator = vector.iterator();
					if (iterator.hasNext()) {
						map=new TreeMap<String, String>();
						while (iterator.hasNext()) {

							HashMap tmp = (HashMap) iterator.next();
							map.put(tmp.get("aaa102").toString(), tmp.get("aaa103").toString());

						}
					}
				}
			}
        }
		
    	return map;
    }
    
	/**
	 * @$comment ����ָ����������ѯ��Ӧ��ֵ�б�
	 * @param colCode ָ����,aaa102 ָ����ֵ
	 * @return CodeTableCol
	 * @throws AppException 
	 * @throws 
	 * @author lixy
	 */
    public static String  getAaa103(String colCode,String aaa102,List<CodeTableCol> ctcList) throws AppException{
    	String  aaa103=null;
    	
        if(ctcList!=null){
        	for (CodeTableCol c : ctcList) {
				if(c.getColCode().equalsIgnoreCase(colCode))
				{
					
					HBSession session = HBUtil.getHBSession();
					CommonQueryBS query = new CommonQueryBS();
					query.setConnection(session.connection());
					//CodeType����Ϊ�գ�ȡ������ΪcodeType  mengl 20160629
					String sql = "select aaa103 from v_aa10 where aaa100='"+(!StringUtil.isEmpty(c.getCodeType())?c.getCodeType():c.getColCode().toUpperCase())+"' and aaa102='"+aaa102+"'";
					query.setQuerySQL(sql);
					Vector<?> vector = query.query();
					Iterator<?> iterator = vector.iterator();
					if (iterator.hasNext()) {
						while (iterator.hasNext()) {

							HashMap tmp = (HashMap) iterator.next();
							aaa103=tmp.get("aaa103").toString();

						}
					}
				}
			}
        }
		
    	return aaa103;
    }
    
    /**
	 * @$comment ���������������ɶ�Ӧ��ѯsql--ͳ�Ʒ�����
	 * 2017-5-22������sqlƴ���ع���fujun
	 * @param list
	 * @return String
	 * @throws AppException 
	 * @throws 
	 * @author wuh
	 */
    @SuppressWarnings("unused")
	public static String  createSQLToTj(List<HashMap<String, Object>> list) throws AppException{
    	String  sql=null;
    	int errNum = 1; 
    	String comsql = "";//��ѯ����
    	StringBuffer sqlbuf = new StringBuffer();//sql ��ѯ�в���
    	StringBuffer where_sql = new StringBuffer();//where ����
    	
    	//��where_sql��Ϊ�����֣�a01�Ĳ�ѯ����ֱ�ӷ���where_sql�У�
    	StringBuffer exists_sql = new StringBuffer();		//exists��sql
    	StringBuffer join_sql = new StringBuffer();			//�Ա�����ϵ������sql
    	StringBuffer existswhere_sql = new StringBuffer();	//existswhere����(�����ÿ�ű��ѯ����)
    	
    	String and_sql = ""; //and������ϵƴ�ӳɵ�sql
    	StringBuffer sqlend = new StringBuffer();//sql����
    	String comwhere = "A0000,A0101,A0104,A0117,A0141,A0192A,"
    			+ "A0148,A0160,A0192D,A0120,QRZXL,ZZXL,A0107,AGE,"
    			+ "A0140,A0134,A0165,A0121,A0184,ORGID,STATUS,"
    			+ "A0163,A0201B,A0221,A0221A,A0288,A0219,A0801B"; //���ò�ѯ����
    	//�ж���������������㳣��������ѯ���������򷵻�1������������򷵻�0
    	for(HashMap<String, Object> m:list){
    		String  colNames= m.get("colNamesValue").toString();//sql������
    		if(!comwhere.contains(colNames.toUpperCase())){
    			errNum = 0;
    			break;
    		}
    	}
    	//�ж���������������㳣���������������ݿ�Ϊoracle���������ﻯ��ͼ��ѯ
    	if(1==2&&errNum==1 && DBUtil.getDBType() == DBType.ORACLE){
    		int count = 0; 
    		comsql ="select  a01.a0000, a0101, a0104, a0117, a0141, a0192a, a0148,A0160,A0192D,A0120,QRZXL,ZZXL " 
	    			+ ",a0107,a0140,a0134,a0165,a0121,a0184,orgid,a01.status from A01 a01 where "
	    			+ "not exists (select 1 from COMPETENCE_USERPERSON cu where cu.a0000 = a01.a0000 "
	    			+ "and cu.userid = '"+SysManagerUtils.getUserId()+"')"
	    			+ "and exists (select 1  from MV_CYCX1 mc, competence_userdept cu where "
	    			+ "mc.A0201B = cu.b0111 and mc.a0000 = a01.a0000 "
	    			+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"' ";
    		if(list.size()>0){
	    		where_sql.append(" and (");
	    	}
    		for(HashMap<String, Object> m:list){//����grid������list����ѭ��
    			count++;
    			String tableName = m.get("tableName").toString().toUpperCase();//sql����������
	    		String  colNames= m.get("colNamesValue").toString();//sql������
	    		String opeators = m.get("opeartors").toString();//�����
	    		//���������Ϣ��  ���Ŵ���  --lzy update
	    		if("A0221".equals(colNames)||"A0801B".equals(colNames)||"A0901B".equals(colNames)||"A0148".equals(colNames)
	    				//||"A0107".equals(colNames)){
	    				){
	    			
	    			if("<={v}".equals(opeators)){
	    				opeators = ">={v}";
	    			}else if(">={v}".equals(opeators)){
	    				opeators = "<={v}";
	    			}else if("<{v}".equals(opeators)){
	    				opeators = ">{v}";
	    			}else if(">{v}".equals(opeators)){
	    				opeators = "<{v}";
	    			}
	    		}
	    		CodeValue operatorCodeValue = RuleSqlListBS.getCodeValue("OPERATOR", opeators);
	    		String colValues = m.get("colValues").toString();//sql����ֵ
	    		where_sql.append(m.get("leftBracket"));
	    		if(colNames.toUpperCase().equals("AGE")){//�ж���������䣬����ݳ������ڽ�������ƴװ
    				where_sql.append(" mc.a0107 ");
    				String startvalue = "";
    	    		String endvalue = "";
    	    		String strdate = DateUtil.getcurdate();//��ȡ����
	    			String year = strdate.substring(0, 4);//��ȡ��
	    			String mm = strdate.substring(4,6);//��ȡ��
	    			String syear = String.valueOf((Long.valueOf(year)-Long.valueOf(colValues)));
        			String eyear = String.valueOf((Long.valueOf(year)-Long.valueOf(colValues)-1));
    				startvalue = syear+mm+"01";
    				endvalue = eyear+mm+"01";
    				//�ж������������Ǵ������䣬���������С��ʱ��
    				if(m.get("opeartors").toString().equals(">{v}")){
    					opeators = "<{v}";
    				}else if(m.get("opeartors").toString().equals("<{v}")){
    					opeators = ">{v}";
    				}else if(m.get("opeartors").toString().equals(">={v}")){
    					opeators = "<={v}";
    				}else if(m.get("opeartors").toString().equals("<={v}")){
    					opeators = ">={v}";
    				}else{
    					opeators = m.get("opeartors").toString();
    				}
    				if(m.get("opeartors").toString().equals("={v}")){
    					where_sql.append(" > '"+endvalue+"' and mc.A0107<='"+startvalue+"'");//�����
	    			}else{
	    				where_sql.append(" "+opeators.replace("v", startvalue).replace("{", "'").replace("}", "'"));//�����
	    			}
    			}else{
    				where_sql.append(" mc."+colNames);
    			}
	    		if(!colNames.toUpperCase().equals("AGE")){//�ж�������������ֶ�
	    			if(!"11".equals(operatorCodeValue.getSubCodeValue())){
	    				where_sql.append(" "+opeators.replace("v", colValues).replace("{", "'").replace("}", "'")+" ");//�����
	    			}else{
	    				int colValuesInt = 0;
	        			try {
	    					colValuesInt = Integer.parseInt(colValues);
	    				} catch (NumberFormatException e) {
	    					throw new AppException("��"+operatorCodeValue.getCodeColumnName()+"���������Ӧ������ֻ��Ϊ����������");
	    				}
	        			String colValues11 = "";
	        			
	        			colValues11 =" "+m.get("opeartors").toString().replace("v", colValuesInt+"").replace("{", "").replace("}", "")
	            				.replace("trunc", "to_char(trunc").replace(")", "),'yyyymmdd')");//�����
	        				        			
	        			if(DBUtil.getDBType() == DBType.MYSQL){
	        				colValues11 = colValues11.replace("sysdate", "now()");
	        			}
	        			where_sql.append(colValues11);
	    			}
	    		}
	    		where_sql.append(m.get("rightBracket"));
	    		if(list.size()==count){
	    			where_sql.append(")");
	    		}
	    		where_sql.append(m.get("logicSymbols"));
    		}
    	}else{    		   	
	    	int count = 0;
	    	//��ʵ����ѯ����sql
	    	comsql ="select  a01.a0000, a0101, a0104, a0117, a0141, a0192a, a0148,A0160,A0192D,A0120,QRZXL,ZZXL " 
	    			+ ",a0107,a0140,a0134,a0165,a0121,a0184,orgid,a01.status from A01 a01 where exists (select 1 "
	    			+ " from A02 a02, competence_userdept cu where a02.A0201B = cu.b0111 and a02.a0000 = a01.a0000 "
	    			+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"') "
	    			+ "and not exists (select 1 from COMPETENCE_USERPERSON cu where cu.a0000 = a01.a0000 "
	    			+ "and cu.userid = '"+SysManagerUtils.getUserId()+"')";
	    	
	    	if(list.size()>0){
	    		where_sql.append(" and (");
	    	}
	    	for(HashMap<String, Object> m:list){//����grid������list����ѭ��
	    		count++;
	    		String tableName = m.get("tableName").toString().toUpperCase();//sql����������
	    		String  colNames= m.get("colNamesValue").toString();//sql������
	    		String opeators = m.get("opeartors").toString();//�����
	    		
	    		//���������Ϣ��  ���Ŵ���  --lzy update
	    		if("A0221".equals(colNames)||"A0801B".equals(colNames)||"A0901B".equals(colNames)||"A0148".equals(colNames)
	    				//||"A0107".equals(colNames)){
	    				){
	    			if("<={v}".equals(opeators)){
	    				opeators = ">={v}";
	    			}else if(">={v}".equals(opeators)){
	    				opeators = "<={v}";
	    			}else if("<{v}".equals(opeators)){
	    				opeators = ">{v}";
	    			}else if(">{v}".equals(opeators)){
	    				opeators = "<{v}";
	    			}
	    		}
	    		
	    		CodeValue operatorCodeValue = RuleSqlListBS.getCodeValue("OPERATOR", opeators);
	    		String colValues = m.get("colValues").toString();//sql����ֵ	  
	    		
	    		if(count != 1){
	    			existswhere_sql.append(m.get("leftBracket"));			//ƴ�������ţ���ҳ����������£�
	    		}
	    		
	    		//�������A01��A02�е�����   		
	    		if(tableName.equals("A01")){ 
	    			
	    			
	    			if(new String (exists_sql)==null || new String (exists_sql).equals("")){		//�жϡ�exists(select 1 from �� �����Ƿ�Ϊ��
	        			
	    				//exists_sqlΪ�գ�A01��ǰ��ѯ���������ڣ���һ��ƴ��A01�ģ�join_sql������exists����
	        			join_sql.append(" a01fujun.a0000 = a01.a0000");
	        			
		        		exists_sql.append(" exists (select 1 from A01 a01fujun ");
		        		
		        		existswhere_sql.append(" and").append(m.get("leftBracket"));
		        		
	        		}else{		//exists_sql��Ϊ�գ���ƴ��exists
	        			
	        			if(exists_sql.indexOf("A01")==-1){		//exists_sql����ֵ����������A01��ƴ��A01
	        				
	        				//׷��join_sql��A02��Ϣ
	        				join_sql.append(" and a01fujun.a0000 = a01.a0000");
	        				
	        				//׷��exists_sql��A01��Ϣ
	        				exists_sql.append(",A01 a01fujun ");
	        				
	        			}
	        			
	        		}
	    			
	    			
	    			
	    			//�������Ϊ���䣬����ת��Ϊ��ѯ��������
	    			if(colNames.toUpperCase().equals("AGE")){
	    				existswhere_sql.append(" a01fujun.a0107 ");
	    				String startvalue = "";
	    	    		String endvalue = "";
	    	    		String strdate = DateUtil.getcurdate();
		    			String year = strdate.substring(0, 4);
		    			String mm = strdate.substring(4,6);
		    			String syear = String.valueOf((Long.valueOf(year)-Long.valueOf(colValues)));
	        			String eyear = String.valueOf((Long.valueOf(year)-Long.valueOf(colValues)-1));
	    				startvalue = syear+mm+"01";
	    				endvalue = eyear+mm+"01";
	    				if(m.get("opeartors").toString().equals(">{v}")){
	    					opeators = "<{v}";
	    				}else if(m.get("opeartors").toString().equals("<{v}")){
	    					opeators = ">{v}";
	    				}else if(m.get("opeartors").toString().equals(">={v}")){
	    					opeators = "<={v}";
	    				}else if(m.get("opeartors").toString().equals("<={v}")){
	    					opeators = ">={v}";
	    				}else{
	    					opeators = m.get("opeartors").toString();
	    				}
	    				if(m.get("opeartors").toString().equals("={v}")){
	    					existswhere_sql.append(" > '"+endvalue+"' and a01fujun.A0107<='"+startvalue+"'");//�����
		    			}else{
		    				existswhere_sql.append(" "+opeators.replace("v", startvalue).replace("{", "'").replace("}", "'"));//�����
		    			}
	    			}else{
	    				
	    				existswhere_sql.append(" a01fujun."+colNames);
	    			} 
	    			
	    			
	        	}else if(tableName.equals("A02")){ //�����ѯְ����Ϣ�������A02
	        		
	        		
	        		if(new String (exists_sql)==null || new String (exists_sql).equals("")){		//�жϡ�exists(select 1 from �� �����Ƿ�Ϊ��
	        			
	        			//exists_sqlΪ�գ�A02��ǰ��ѯ���������ڣ���һ��ƴ��A02�ģ�join_sql������exists����
	        			join_sql.append(" a02.A0201B = cu.b0111 and a02.a0000 = a01.a0000"
		        				+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");
		        			
		        		exists_sql.append(" exists (select 1 from A02 a02, competence_userdept cu ");
		        		
	        		}else{		//exists_sql��Ϊ�գ���ƴ��exists
	        			
	        			if(exists_sql.indexOf("B01")==-1 && exists_sql.indexOf("A02")==-1){		//exists_sql����ֵ����������B01��A02��ƴ��A02
	        				//׷��join_sql��A02��Ϣ
	        				join_sql.append(" and a02.A0201B = cu.b0111 and a02.a0000 = a01.a0000"
			        				+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");
	        				//׷��exists_sql��A02��Ϣ
	        				exists_sql.append(",A02 a02, competence_userdept cu ");
	        			}
	        			//exists_sql�д���B01��A02������һ�����Ϊjoin_sql��exists_sqlƴ��A02��Ϣ
	        		}
	        		
	        		
	        	}else if(tableName.equals("B01")){
	        		
	        		if(new String (exists_sql)==null || new String (exists_sql).equals("")){		//�жϡ�exists(select 1 from �� �����Ƿ�Ϊ��
	        			
	        			//exists_sqlΪ�գ�B01��ǰ��ѯ���������ڣ���һ��ƴ��B01�ģ�join_sql������exists����
	        			join_sql.append(" a02.A0201B = cu.b0111 and a02.a0000 = a01.a0000"
		        				+ " and a02.A0201B = b01.b0111 "
		        				+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");
		        			
		        		exists_sql.append(" exists (select 1 from A02 a02, competence_userdept cu,B01 b01 ");
		        		
	        		}else{		//exists_sql��Ϊ�գ���ƴ��exists
	        			
	        			if(exists_sql.indexOf("B01")==-1 && exists_sql.indexOf("A02")==-1){		//exists_sql����ֵ����������B01��A02��ƴ��B01
	        				//׷��join_sql��B01��Ϣ
	        				join_sql.append(" and a02.A0201B = cu.b0111 and a02.a0000 = a01.a0000"
			        				+ " and a02.A0201B = b01.b0111 "
			        				+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");
	        				//׷��exists_sql��B01��Ϣ
	        				exists_sql.append(",A02 a02, competence_userdept cu,B01 b01 ");
	        				
	        			}else if(exists_sql.indexOf("B01")==-1 && exists_sql.indexOf("A02")!=-1){//exists_sql�д���A02��������B01����Ϊjoin_sql��exists_sqlƴ��B01����A02����Ϣ
	        				
	        				//׷��join_sql��B01��Ϣ
	        				join_sql.append(" and a02.A0201B = b01.b0111 ");
			        				
	        				//׷��exists_sql��B01��Ϣ
	        				exists_sql.append(",A02 a02, competence_userdept cu,B01 b01 ");
	        			}
	        		}
	        		
	        	}else{			//�����������
	        		
	        		if(new String (exists_sql)==null || new String (exists_sql).equals("")){		//�жϡ�exists(select 1 from �� �����Ƿ�Ϊ��
	        			
	        			//exists_sqlΪ�գ�tableName��ǰ��ѯ���������ڣ���һ��ƴ��tableName�ģ�join_sql������exists����
	        			join_sql.append(" "+tableName.toLowerCase()+".a0000 = a01.a0000");
		        			
		        		exists_sql.append(" exists (select 1 from "+tableName+" "+tableName.toLowerCase());
		        		
	        		}else{		//exists_sql��Ϊ�գ���ƴ��exists
	        			
	        			if(exists_sql.indexOf(tableName)==-1){		//exists_sql����ֵ����������tableName��ƴ��tableName
	        				//׷��join_sql��tableName��Ϣ
	        				join_sql.append(" and "+tableName.toLowerCase()+".a0000 = a01.a0000");
	        				
	        				//׷��exists_sql��tableName��Ϣ
	        				exists_sql.append(","+tableName+" "+tableName.toLowerCase());
	        			}
	        			
	        		}
	        		
	        	}
	    		
	    		
	    		//�ж�existswhere_sql�Ƿ�Ϊ�գ�Ϊ����Ϊ��һ��������������ǰ��and
        		if(new String (existswhere_sql)==null || new String (existswhere_sql).equals("") && !tableName.equals("A01")){
        			existswhere_sql.append(" and "+m.get("leftBracket")+tableName.toLowerCase()+"."+colNames);
        		}else if(!tableName.equals("A01")){
        			existswhere_sql.append(tableName.toLowerCase()+"."+colNames);
        		}
        		
	    		
	    		//�������ѯ��������ѯֵ
	    		if(!colNames.toUpperCase().equals("AGE")){//�����ѯ����
	    			if(!"11".equals(operatorCodeValue.getSubCodeValue())){
	    				if(tableName.equals("A01")){
	    					existswhere_sql.append(" "+opeators.replace("v", colValues).replace("{", "'").replace("}", "'")+" ");//�����
	    				}else{
	    					existswhere_sql.append(" "+opeators.replace("v", colValues).replace("{", "'").replace("}", "'"));//�����
	    				}
	    				
	    			}else{
	    				int colValuesInt = 0;
	        			try {
	    					colValuesInt = Integer.parseInt(colValues);
	    				} catch (NumberFormatException e) {
	    					throw new AppException("��"+operatorCodeValue.getCodeColumnName()+"���������Ӧ������ֻ��Ϊ����������");
	    				}
	        			String colValues11 = "";
	        			if(tableName.equals("A01")){
	        				colValues11 =" "+m.get("opeartors").toString().replace("v", colValuesInt+"").replace("{", "").replace("}", "")
	            					.replace("trunc", "to_char(trunc").replace(")", "),'yyyymmdd')");//�����
	        			}else{
	        				colValues11 =" "+m.get("opeartors").toString().replace("v", colValuesInt+"").replace("{", "").replace("}", "")
	            					.replace("trunc", "to_char(trunc").replace(")", "),'yyyymmdd')");//�����
	        			}
	        			
	        			
	        			if(DBUtil.getDBType() == DBType.MYSQL){
	        				colValues11 = colValues11.replace("sysdate", "now()");
	        			}
	        			existswhere_sql.append(colValues11);
	    			}
	    		}
	    		existswhere_sql.append(m.get("rightBracket"));			//ƴ�������ţ���ҳ����������£�
	    		
	    		
	    		//����ƴ�ӵ����existswhere_sql������������
	    		if(list.size()==count){
	    			existswhere_sql.insert(4,"( "); 
	    			
	    			existswhere_sql.append(") ) )");
	    		}
		    	existswhere_sql.append(m.get("logicSymbols"));		//ƴ���������ӹ�ϵ
		    	
	    	}						//forβ��
	    	
	    	
	    	
    	}
    	and_sql = comsql.toString();
    	
    	//�����exists��ѯ����ƴ��sql
    	if(new String (exists_sql)!=null && !new String (exists_sql).equals("")){
    		where_sql = where_sql.append(exists_sql).append(" where").append(join_sql).append(existswhere_sql);
    	}
    	
    	
    	if(1==2 && errNum==1 && DBUtil.getDBType() == DBType.ORACLE){//����ǳ��ò�ѯ�����������ݿ���oracle��������ƴװsql
    		and_sql = (and_sql+where_sql).replace(") and 1=1", ")) and 1=1"); 
    	}else{
    		and_sql = and_sql+where_sql;
    	}
    	//��װsql
    	sqlbuf.append(and_sql);
    	
    	sql=sqlbuf.toString();
    	CommonQueryBS.systemOut("---->"+sql);
    	String msg= checkSql(sql);
    	if(msg!=null)
    		throw new AppException(msg);  
    	return sql;
    }
    
	/**
	 * @$comment ���������������ɶ�Ӧ��ѯsql��ԭΪcreateSQL��Ŀǰ�����Ѹ��죬�˷����ѷ�����
	 * @param list
	 * @return String
	 * @throws AppException 
	 * @throws 
	 * @author caiy
	 */
    @SuppressWarnings("unused")
	public static String  createSQL_odl(List<HashMap<String, Object>> list) throws AppException{
    	String  sql=null;
    	int errNum = 1; 
    	String comsql = "";//��ѯ����
    	StringBuffer sqlbuf = new StringBuffer();//sql ��ѯ�в���
    	StringBuffer where_sql = new StringBuffer();//where ����
    	StringBuffer exists_sql = new StringBuffer();//exists��sql
    	StringBuffer existswhere_sql = new StringBuffer();//existswhere����
    	String and_sql = ""; //and������ϵƴ�ӳɵ�sql
    	StringBuffer sqlend = new StringBuffer();//sql����
    	String comwhere = "A0000,A0101,A0104,A0117,A0141,A0192A,"
    			+ "A0148,A0160,A0192D,A0120,QRZXL,ZZXL,A0107,AGE,"
    			+ "A0140,A0134,A0165,A0121,A0184,ORGID,STATUS,"
    			+ "A0163,A0201B,A0221,A0221A,A0288,A0219,A0801B"; //���ò�ѯ����
    	//�ж���������������㳣��������ѯ���������򷵻�1������������򷵻�0
    	for(HashMap<String, Object> m:list){
    		String  colNames= m.get("colNamesValue").toString();//sql������
    		if(!comwhere.contains(colNames.toUpperCase())){
    			errNum = 0;
    			break;
    		}
    	}
    	//�ж���������������㳣���������������ݿ�Ϊoracle���������ﻯ��ͼ��ѯ
    	if(errNum==1 && DBUtil.getDBType() == DBType.ORACLE){
    		int count = 0; 
    		comsql ="select  a01.a0000, a0101, a0104, a0117, a0141, a0192a, a0148,A0160,A0192D,A0120,QRZXL,ZZXL " 
	    			+ ",a0107,a0140,a0134,a0165,a0121,a0184,orgid,a01.status from A01 a01 where "
	    			+ "not exists (select 1 from COMPETENCE_USERPERSON cu where cu.a0000 = a01.a0000 "
	    			+ "and cu.userid = '"+SysManagerUtils.getUserId()+"')"
	    			+ "and exists (select 1  from MV_CYCX1 mc, competence_userdept cu where "
	    			+ "mc.A0201B = cu.b0111 and mc.a0000 = a01.a0000 "
	    			+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"' ";
    		if(list.size()>0){
	    		where_sql.append(" and (");
	    	}
    		for(HashMap<String, Object> m:list){//����grid������list����ѭ��
    			count++;
    			String tableName = m.get("tableName").toString().toUpperCase();//sql����������
	    		String  colNames= m.get("colNamesValue").toString();//sql������
	    		String opeators = m.get("opeartors").toString();//�����
	    		//���������Ϣ��  ���Ŵ���  --lzy update
	    		if("A0221".equals(colNames)||"A0801B".equals(colNames)||"A0901B".equals(colNames)||"A0148".equals(colNames)
	    				||"A0107".equals(colNames)){
	    			if("<={v}".equals(opeators)){
	    				opeators = ">={v}";
	    			}else if(">={v}".equals(opeators)){
	    				opeators = "<={v}";
	    			}else if("<{v}".equals(opeators)){
	    				opeators = ">{v}";
	    			}else if(">{v}".equals(opeators)){
	    				opeators = "<{v}";
	    			}
	    		}
	    		CodeValue operatorCodeValue = RuleSqlListBS.getCodeValue("OPERATOR", opeators);
	    		String colValues = m.get("colValues").toString();//sql����ֵ
	    		where_sql.append(m.get("leftBracket"));
	    		if(colNames.toUpperCase().equals("AGE")){//�ж���������䣬����ݳ������ڽ�������ƴװ
    				where_sql.append(" mc.a0107 ");
    				String startvalue = "";
    	    		String endvalue = "";
    	    		String strdate = DateUtil.getcurdate();//��ȡ����
	    			String year = strdate.substring(0, 4);//��ȡ��
	    			String mm = strdate.substring(4,6);//��ȡ��
	    			String syear = String.valueOf((Long.valueOf(year)-Long.valueOf(colValues)));
        			String eyear = String.valueOf((Long.valueOf(year)-Long.valueOf(colValues)-1));
    				startvalue = syear+mm+"01";
    				endvalue = eyear+mm+"01";
    				//�ж������������Ǵ������䣬���������С��ʱ��
    				if(m.get("opeartors").toString().equals(">{v}")){
    					opeators = "<{v}";
    				}else if(m.get("opeartors").toString().equals("<{v}")){
    					opeators = ">{v}";
    				}else if(m.get("opeartors").toString().equals(">={v}")){
    					opeators = "<={v}";
    				}else if(m.get("opeartors").toString().equals("<={v}")){
    					opeators = ">={v}";
    				}else{
    					opeators = m.get("opeartors").toString();
    				}
    				if(m.get("opeartors").toString().equals("={v}")){
    					where_sql.append(" > '"+endvalue+"' and mc.A0107<='"+startvalue+"'");//�����
	    			}else{
	    				where_sql.append(" "+opeators.replace("v", startvalue).replace("{", "'").replace("}", "'"));//�����
	    			}
    			}else{
    				where_sql.append(" mc."+colNames);
    			}
	    		if(!colNames.toUpperCase().equals("AGE")){//�ж�������������ֶ�
	    			if(!"11".equals(operatorCodeValue.getSubCodeValue())){
	    				where_sql.append(" "+opeators.replace("v", colValues).replace("{", "'").replace("}", "'")+" ");//�����
	    			}else{
	    				int colValuesInt = 0;
	        			try {
	    					colValuesInt = Integer.parseInt(colValues);
	    				} catch (NumberFormatException e) {
	    					throw new AppException("��"+operatorCodeValue.getCodeColumnName()+"���������Ӧ������ֻ��Ϊ����������");
	    				}
	        			String colValues11 = "";
	        			
	        			colValues11 =" "+m.get("opeartors").toString().replace("v", colValuesInt+"").replace("{", "").replace("}", "")
	            				.replace("trunc", "to_char(trunc").replace(")", "),'yyyymmdd')");//�����
	        				        			
	        			if(DBUtil.getDBType() == DBType.MYSQL){
	        				colValues11 = colValues11.replace("sysdate", "now()");
	        			}
	        			where_sql.append(colValues11);
	    			}
	    		}
	    		where_sql.append(m.get("rightBracket"));
	    		if(list.size()==count){
	    			where_sql.append(")");
	    		}
	    		where_sql.append(m.get("logicSymbols"));
    		}
    	}else{    		   	
	    	int count = 0;    	
	    	//��ʵ����ѯ����sql
	    	comsql ="select  a01.a0000, a0101, a0104, a0117, a0141, a0192a, a0148,A0160,A0192D,A0120,QRZXL,ZZXL " 
	    			+ ",a0107,a0140,a0134,a0165,a0121,a0184,orgid,a01.status from A01 a01 where exists (select 1 "
	    			+ " from A02 a02, competence_userdept cu where a02.A0201B = cu.b0111 and a02.a0000 = a01.a0000 "
	    			+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"') "
	    			+ "and not exists (select 1 from COMPETENCE_USERPERSON cu where cu.a0000 = a01.a0000 "
	    			+ "and cu.userid = '"+SysManagerUtils.getUserId()+"')";
	    	
	    	if(list.size()>0){
	    		where_sql.append(" and (");
	    	}
	    	for(HashMap<String, Object> m:list){//����grid������list����ѭ��
	    		count++;
	    		String tableName = m.get("tableName").toString().toUpperCase();//sql����������
	    		String  colNames= m.get("colNamesValue").toString();//sql������
	    		String opeators = m.get("opeartors").toString();//�����
	    		
	    		//���������Ϣ��  ���Ŵ���  --lzy update
	    		if("A0221".equals(colNames)||"A0801B".equals(colNames)||"A0901B".equals(colNames)||"A0148".equals(colNames)
	    				||"A0107".equals(colNames)){
	    			if("<={v}".equals(opeators)){
	    				opeators = ">={v}";
	    			}else if(">={v}".equals(opeators)){
	    				opeators = "<={v}";
	    			}else if("<{v}".equals(opeators)){
	    				opeators = ">{v}";
	    			}else if(">{v}".equals(opeators)){
	    				opeators = "<{v}";
	    			}
	    		}
	    		
	    		CodeValue operatorCodeValue = RuleSqlListBS.getCodeValue("OPERATOR", opeators);
	    		String colValues = m.get("colValues").toString();//sql����ֵ	  
	    			    		
	    		where_sql.append(m.get("leftBracket"));
	    		//�������A01��A02�е�����   		
	    		if(tableName.equals("A01")){ 
	    			//�������Ϊ���䣬����ת��Ϊ��ѯ��������
	    			if(colNames.toUpperCase().equals("AGE")){
	    				where_sql.append(" a01.a0107 ");
	    				String startvalue = "";
	    	    		String endvalue = "";
	    	    		String strdate = DateUtil.getcurdate();
		    			String year = strdate.substring(0, 4);
		    			String mm = strdate.substring(4,6);
		    			String syear = String.valueOf((Long.valueOf(year)-Long.valueOf(colValues)));
	        			String eyear = String.valueOf((Long.valueOf(year)-Long.valueOf(colValues)-1));
	    				startvalue = syear+mm+"01";
	    				endvalue = eyear+mm+"01";
	    				if(m.get("opeartors").toString().equals(">{v}")){
	    					opeators = "<{v}";
	    				}else if(m.get("opeartors").toString().equals("<{v}")){
	    					opeators = ">{v}";
	    				}else if(m.get("opeartors").toString().equals(">={v}")){
	    					opeators = "<={v}";
	    				}else if(m.get("opeartors").toString().equals("<={v}")){
	    					opeators = ">={v}";
	    				}else{
	    					opeators = m.get("opeartors").toString();
	    				}
	    				if(m.get("opeartors").toString().equals("={v}")){
	    					where_sql.append(" > '"+endvalue+"' and a01.A0107<='"+startvalue+"'");//�����
		    			}else{
		    				where_sql.append(" "+opeators.replace("v", startvalue).replace("{", "'").replace("}", "'"));//�����
		    			}
	    			}else{
	    				where_sql.append(" a01."+colNames);
	    			}  			
	        	}else if(tableName.equals("A02")){ //�����ѯְ����Ϣ�������A02
	        		where_sql.append(" exists (select 1 from A02 a02, competence_userdept cu where"
	        				+ " a02.A0201B = cu.b0111 and a02.a0000 = a01.a0000"
	        				+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");
	        		where_sql.append(" and a02."+colNames);
	        	}else if(tableName.equals("B01")){
	        		where_sql.append(" exists (select 1 from A02 a02, competence_userdept cu,B01 b01 where"
	        				+ " a02.A0201B = cu.b0111 and a02.a0000 = a01.a0000"
	        				+ " and a02.A0201B = b01.b0111 "
	        				+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");
	        		where_sql.append(" and b01."+colNames);
	        	}else{
	        		where_sql.append(" exists (select 1 from "+tableName+" "+tableName.toLowerCase()+" where"
	        				+ " "+tableName.toLowerCase()+".a0000 = a01.a0000");
	        		where_sql.append(" and "+tableName.toLowerCase()+"."+colNames);
	        	}
	    		if(!colNames.toUpperCase().equals("AGE")){//�����ѯ����
	    			if(!"11".equals(operatorCodeValue.getSubCodeValue())){
	    				if(tableName.equals("A01")){
	    					where_sql.append(" "+opeators.replace("v", colValues).replace("{", "'").replace("}", "'")+" ");//�����
	    				}else{
	    					where_sql.append(" "+opeators.replace("v", colValues).replace("{", "'").replace("}", "'")+") ");//�����
	    				}
	    				
	    			}else{
	    				int colValuesInt = 0;
	        			try {
	    					colValuesInt = Integer.parseInt(colValues);
	    				} catch (NumberFormatException e) {
	    					throw new AppException("��"+operatorCodeValue.getCodeColumnName()+"���������Ӧ������ֻ��Ϊ����������");
	    				}
	        			String colValues11 = "";
	        			if(tableName.equals("A01")){
	        				colValues11 =" "+m.get("opeartors").toString().replace("v", colValuesInt+"").replace("{", "").replace("}", "")
	            					.replace("trunc", "to_char(trunc").replace(")", "),'yyyymmdd')");//�����
	        			}else{
	        				colValues11 =" "+m.get("opeartors").toString().replace("v", colValuesInt+"").replace("{", "").replace("}", "")
	            					.replace("trunc", "to_char(trunc").replace(")", "),'yyyymmdd'))");//�����
	        			}
	        			
	        			
	        			if(DBUtil.getDBType() == DBType.MYSQL){
	        				colValues11 = colValues11.replace("sysdate", "now()");
	        			}
	        			where_sql.append(colValues11);
	    			}
	    		}
	    		where_sql.append(m.get("rightBracket"));
	    		if(list.size()==count){
	    			where_sql.append(")");
	    		}
	    		where_sql.append(m.get("logicSymbols"));		//ƴ�ӣ��������ӹ�ϵ
	
	    	}
    	}
    	and_sql = comsql.toString();
    	if(errNum==1 && DBUtil.getDBType() == DBType.ORACLE){//����ǳ��ò�ѯ�����������ݿ���oracle��������ƴװsql
    		and_sql = (and_sql+where_sql).replace(") and 1=1", ")) and 1=1"); 
    	}else{
    		and_sql = and_sql+where_sql;
    	}
    	//��װsql
    	sqlbuf.append(and_sql);
    	
    	sql=sqlbuf.toString();
    	CommonQueryBS.systemOut("---->"+sql);
    	String msg= checkSql(sql);
    	if(msg!=null)
    		throw new AppException(msg);  
    	return sql;
    }
    
    
    
    /**
	 * @$comment ���������������ɶ�Ӧ��ѯsql����������������fujun
	 * @param list
	 * @return String
	 * @throws AppException 
	 * @throws 
	 * @author caiy
	 */
    @SuppressWarnings("unused")
	public  String  createSQL(List<HashMap<String, Object>> list) throws AppException{
    	String  sql=null;
    	int errNum = 1; 
    	String comsql = "";//��ѯ����
    	StringBuffer sqlbuf = new StringBuffer();//sql ��ѯ�в���
    	StringBuffer where_sql = new StringBuffer();//where ����
    	
    	//��where_sql��Ϊ�����֣�a01�Ĳ�ѯ����ֱ�ӷ���where_sql�У�
    	StringBuffer exists_sql = new StringBuffer();		//exists��sql
    	StringBuffer join_sql = new StringBuffer();			//�Ա�����ϵ������sql
    	StringBuffer existswhere_sql = new StringBuffer();	//existswhere����(�����ÿ�ű��ѯ����)
    	
    	String and_sql = ""; //and������ϵƴ�ӳɵ�sql
    	StringBuffer sqlend = new StringBuffer();//sql����
    	String comwhere = "A0000,A0101,A0104,A0117,A0141,A0192A,"
    			+ "A0148,A0160,A0192D,A0120,QRZXL,ZZXL,A0107,AGE,"
    			+ "A0140,A0134,A0165,A0121,A0184,ORGID,STATUS,"
    			+ "A0163,A0201B,A0221,A0221A,A0288,A0219,A0801B"; //���ò�ѯ����
    	//�ж���������������㳣��������ѯ���������򷵻�1������������򷵻�0
    	for(HashMap<String, Object> m:list){
    		String  colNames= m.get("colNamesValue").toString();//sql������
    		if(!comwhere.contains(colNames.toUpperCase())){
    			errNum = 0;
    			break;
    		}
    	}
    	//�ж���������������㳣���������������ݿ�Ϊoracle���������ﻯ��ͼ��ѯ
    	if(1==2&&errNum==1 && DBUtil.getDBType() == DBType.ORACLE){
    		int count = 0; 
    		comsql ="select  a01.a0000, a0101, a0104, a0117, a0141, a0192a, a0148,A0160,A0192D,A0120,QRZXL,ZZXL " 
	    			+ ",a0107,a0140,a0134,a0165,a0121,a0184,orgid,a01.status from A01 a01 where "
	    			+ "not exists (select 1 from COMPETENCE_USERPERSON cu where cu.a0000 = a01.a0000 "
	    			+ "and cu.userid = '"+SysManagerUtils.getUserId()+"')"
	    			+ "and exists (select 1  from MV_CYCX1 mc, competence_userdept cu where "
	    			+ "mc.A0201B = cu.b0111 and mc.a0000 = a01.a0000 "
	    			+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"' ";
    		if(list.size()>0){
	    		where_sql.append(" and (");
	    	}
    		for(HashMap<String, Object> m:list){//����grid������list����ѭ��
    			count++;
    			String tableName = m.get("tableName").toString().toUpperCase();//sql����������
	    		String  colNames= m.get("colNamesValue").toString();//sql������
	    		String opeators = m.get("opeartors").toString();//�����
	    		//���������Ϣ��  ���Ŵ���  --lzy update
	    		if("A0221".equals(colNames)||"A0801B".equals(colNames)||"A0901B".equals(colNames)||"A0148".equals(colNames)
	    				//||"A0107".equals(colNames)){
	    				){
	    			if("<={v}".equals(opeators)){
	    				opeators = ">={v}";
	    			}else if(">={v}".equals(opeators)){
	    				opeators = "<={v}";
	    			}else if("<{v}".equals(opeators)){
	    				opeators = ">{v}";
	    			}else if(">{v}".equals(opeators)){
	    				opeators = "<{v}";
	    			}
	    		}
	    		CodeValue operatorCodeValue = RuleSqlListBS.getCodeValue("OPERATOR", opeators);
	    		String colValues = m.get("colValues").toString();//sql����ֵ
	    		where_sql.append(m.get("leftBracket"));
	    		if(colNames.toUpperCase().equals("AGE")){//�ж���������䣬����ݳ������ڽ�������ƴװ
    				where_sql.append(" mc.a0107 ");
    				String startvalue = "";
    	    		String endvalue = "";
    	    		String strdate = DateUtil.getcurdate();//��ȡ����
	    			String year = strdate.substring(0, 4);//��ȡ��
	    			String mm = strdate.substring(4,6);//��ȡ��
	    			String syear = String.valueOf((Long.valueOf(year)-Long.valueOf(colValues)));
        			String eyear = String.valueOf((Long.valueOf(year)-Long.valueOf(colValues)-1));
    				startvalue = syear+mm+"01";
    				endvalue = eyear+mm+"01";
    				//�ж������������Ǵ������䣬���������С��ʱ��
    				if(m.get("opeartors").toString().equals(">{v}")){
    					opeators = "<{v}";
    				}else if(m.get("opeartors").toString().equals("<{v}")){
    					opeators = ">{v}";
    				}else if(m.get("opeartors").toString().equals(">={v}")){
    					opeators = "<={v}";
    				}else if(m.get("opeartors").toString().equals("<={v}")){
    					opeators = ">={v}";
    				}else{
    					opeators = m.get("opeartors").toString();
    				}
    				if(m.get("opeartors").toString().equals("={v}")){
    					where_sql.append(" > '"+endvalue+"' and mc.A0107<='"+startvalue+"'");//�����
	    			}else{
	    				where_sql.append(" "+opeators.replace("v", startvalue).replace("{", "'").replace("}", "'"));//�����
	    			}
    			}else{
    				where_sql.append(" mc."+colNames);
    			}
	    		if(!colNames.toUpperCase().equals("AGE")){//�ж�������������ֶ�
	    			if(!"11".equals(operatorCodeValue.getSubCodeValue())){
	    				where_sql.append(" "+opeators.replace("v", colValues).replace("{", "'").replace("}", "'")+" ");//�����
	    			}else{
	    				int colValuesInt = 0;
	        			try {
	    					colValuesInt = Integer.parseInt(colValues);
	    				} catch (NumberFormatException e) {
	    					throw new AppException("��"+operatorCodeValue.getCodeColumnName()+"���������Ӧ������ֻ��Ϊ����������");
	    				}
	        			String colValues11 = "";
	        			
	        			colValues11 =" "+m.get("opeartors").toString().replace("v", colValuesInt+"").replace("{", "").replace("}", "")
	            				.replace("trunc", "to_char(trunc").replace(")", "),'yyyymmdd')");//�����
	        				        			
	        			if(DBUtil.getDBType() == DBType.MYSQL){
	        				colValues11 = colValues11.replace("sysdate", "now()");
	        			}
	        			where_sql.append(colValues11);
	    			}
	    		}
	    		where_sql.append(m.get("rightBracket"));
	    		if(list.size()==count){
	    			where_sql.append(")");
	    		}
	    		where_sql.append(m.get("logicSymbols"));
    		}
    	}else{    		   	
	    	int count = 0;
	    	/*exists (select 1 "
	    			+ " from A02 a02, competence_userdept cu where a02.A0201B = cu.b0111 and a02.a0000 = a01.a0000 "
	    			+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"') "
	    			+ "and
*/	    	//��ʵ����ѯ����sql
	    	comsql =CommSQL.getComSQL(this.getSid())+" where  not exists (select 1 from COMPETENCE_USERPERSON cu where cu.a0000 = a01.a0000 "
	    			+ "and cu.userid = '"+SysManagerUtils.getUserId()+"')";
	    	
	    	if(list.size()>0){
	    		where_sql.append(" and (");
	    	}
	    	for(HashMap<String, Object> m:list){//����grid������list����ѭ��
	    		count++;
	    		String tableName = m.get("tableName").toString().toUpperCase();//sql����������
	    		String  colNames= m.get("colNamesValue").toString();//sql������
	    		String opeators = m.get("opeartors").toString();//�����
	    		
	    		//���������Ϣ��  ���Ŵ���  --lzy update
	    		if("A0221".equals(colNames)||"A0801B".equals(colNames)||"A0901B".equals(colNames)||"A0148".equals(colNames)
	    				//||"A0107".equals(colNames)){
	    			){
	    			
	    			if("<={v}".equals(opeators)){
	    				opeators = ">={v}";
	    			}else if(">={v}".equals(opeators)){
	    				opeators = "<={v}";
	    			}else if("<{v}".equals(opeators)){
	    				opeators = ">{v}";
	    			}else if(">{v}".equals(opeators)){
	    				opeators = "<{v}";
	    			}
	    		}
	    		
	    		
	    		
	    		
	    		CodeValue operatorCodeValue = RuleSqlListBS.getCodeValue("OPERATOR", opeators);
	    		String colValues = m.get("colValues").toString();//sql����ֵ	  
	    		
	    		if("A0163".equals(colNames)&&"={v}".equals(opeators)){
	    			if("2".equals(colValues)){//��ѯ����ְ��Ա
	    				opeators = "!={v}";
	    				colValues = "1";
	    			}
	    		}else if("A0163".equals(colNames)&&"!={v}".equals(opeators)){
	    			if("2".equals(colValues)){//��ѯ����ְ��Ա
	    				opeators = "={v}";
	    				colValues = "1";
	    			}
	    		}
	    		
	    		if(count != 1){
	    			existswhere_sql.append(m.get("leftBracket"));			//ƴ�������ţ���ҳ����������£�
	    		}
	    		
	    		//�������A01��A02�е�����   		
	    		if(tableName.equals("A01")){ 
	    			
	    			
	    			if(new String (exists_sql)==null || new String (exists_sql).equals("")){		//�жϡ�exists(select 1 from �� �����Ƿ�Ϊ��
	        			
	    				//exists_sqlΪ�գ�A01��ǰ��ѯ���������ڣ���һ��ƴ��A01�ģ�join_sql������exists����
	        			join_sql.append(" a01fujun.a0000 = a01.a0000");
	        			
		        		exists_sql.append(" exists (select 1 from A01 a01fujun ");
		        		
		        		existswhere_sql.append(" and").append(m.get("leftBracket"));
		        		
	        		}else{		//exists_sql��Ϊ�գ���ƴ��exists
	        			
	        			if(exists_sql.indexOf("A01")==-1){		//exists_sql����ֵ����������A01��ƴ��A01
	        				
	        				//׷��join_sql��A02��Ϣ
	        				join_sql.append(" and a01fujun.a0000 = a01.a0000");
	        				
	        				//׷��exists_sql��A01��Ϣ
	        				exists_sql.append(",A01 a01fujun ");
	        				
	        			}
	        			
	        		}
	    			
	    			
	    			
	    			//�������Ϊ���䣬����ת��Ϊ��ѯ��������
	    			if(colNames.toUpperCase().equals("AGE")){
	    				existswhere_sql.append(" a01fujun.a0107 ");
	    				String startvalue = "";
	    	    		String endvalue = "";
	    	    		String strdate = DateUtil.getcurdate();
		    			String year = strdate.substring(0, 4);
		    			String mm = strdate.substring(4,6);
		    			String dd = strdate.substring(6, 8);
		    			String syear = String.valueOf((Long.valueOf(year)-Long.valueOf(colValues)));
	        			String eyear = String.valueOf((Long.valueOf(year)-Long.valueOf(colValues)-1));
	    				startvalue = syear+mm+dd;
	    				endvalue = eyear+mm+dd;
	    				if(m.get("opeartors").toString().equals(">{v}")){
	    					opeators = "<{v}";
	    				}else if(m.get("opeartors").toString().equals("<{v}")){
	    					opeators = ">{v}";
	    				}else if(m.get("opeartors").toString().equals(">={v}")){
	    					opeators = "<={v}";
	    				}else if(m.get("opeartors").toString().equals("<={v}")){
	    					opeators = ">={v}";
	    				}else{
	    					opeators = m.get("opeartors").toString();
	    				}
	    				if(m.get("opeartors").toString().equals("={v}")){
	    					existswhere_sql.append(" > '"+endvalue+"' and a01fujun.A0107<='"+startvalue+"'");//�����
		    			}else{
		    				existswhere_sql.append(" "+opeators.replace("v", startvalue).replace("{", "'").replace("}", "'"));//�����
		    			}
	    			}else{
	    				if(m.get("opeartors").toString().equals("({c} is null or {c}={v})")){//Ϊ��
	    					//existswhere_sql.append(" a01fujun."+colNames);
	    					opeators = m.get("opeartors").toString().replace("{c}", " a01fujun."+colNames);
	    				}else{
	    					existswhere_sql.append(" a01fujun."+colNames);
	    				}
	    				
	    			} 
	    			
	    			
	        	}else if(tableName.equals("A02")){ //�����ѯְ����Ϣ�������A02
	        		
	        		
	        		if(new String (exists_sql)==null || new String (exists_sql).equals("")){		//�жϡ�exists(select 1 from �� �����Ƿ�Ϊ��
	        			
	        			//exists_sqlΪ�գ�A02��ǰ��ѯ���������ڣ���һ��ƴ��A02�ģ�join_sql������exists����
	        			join_sql.append(" a02.A0201B = cu.b0111 and a02.a0000 = a01.a0000"
		        				+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");
		        			
		        		exists_sql.append(" exists (select 1 from A02 a02, competence_userdept cu ");
		        		
	        		}else{		//exists_sql��Ϊ�գ���ƴ��exists
	        			
	        			if(exists_sql.indexOf("B01")==-1 && exists_sql.indexOf("A02")==-1){		//exists_sql����ֵ����������B01��A02��ƴ��A02
	        				//׷��join_sql��A02��Ϣ
	        				join_sql.append(" and a02.A0201B = cu.b0111 and a02.a0000 = a01.a0000"
			        				+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");
	        				//׷��exists_sql��A02��Ϣ
	        				exists_sql.append(",A02 a02, competence_userdept cu ");
	        			}
	        			//exists_sql�д���B01��A02������һ�����Ϊjoin_sql��exists_sqlƴ��A02��Ϣ
	        		}
	        		
	        		
	        	}else if(tableName.equals("B01")){
	        		
	        		if(new String (exists_sql)==null || new String (exists_sql).equals("")){		//�жϡ�exists(select 1 from �� �����Ƿ�Ϊ��
	        			
	        			//exists_sqlΪ�գ�B01��ǰ��ѯ���������ڣ���һ��ƴ��B01�ģ�join_sql������exists����
	        			join_sql.append(" a02.A0201B = cu.b0111 and a02.a0000 = a01.a0000"
		        				+ " and a02.A0201B = b01.b0111 "
		        				+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");
		        			
		        		exists_sql.append(" exists (select 1 from A02 a02, competence_userdept cu,B01 b01 ");
		        		
	        		}else{		//exists_sql��Ϊ�գ���ƴ��exists
	        			
	        			if(exists_sql.indexOf("B01")==-1 && exists_sql.indexOf("A02")==-1){		//exists_sql����ֵ����������B01��A02��ƴ��B01
	        				//׷��join_sql��B01��Ϣ
	        				join_sql.append(" and a02.A0201B = cu.b0111 and a02.a0000 = a01.a0000"
			        				+ " and a02.A0201B = b01.b0111 "
			        				+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");
	        				//׷��exists_sql��B01��Ϣ
	        				exists_sql.append(",A02 a02,B01 b01 ");
	        				if(exists_sql.indexOf("competence_userdept")==-1){
	        					exists_sql.append(",competence_userdept cu");
	        				}
	        				
	        			}else if(exists_sql.indexOf("B01")==-1 && exists_sql.indexOf("A02")!=-1){//exists_sql�д���A02��������B01����Ϊjoin_sql��exists_sqlƴ��B01����A02����Ϣ
	        				
	        				//׷��join_sql��B01��Ϣ
	        				join_sql.append(" and a02.A0201B = b01.b0111 ");
			        				
	        				//׷��exists_sql��B01��Ϣ
	        				exists_sql.append(", B01 b01 ");
	        				if(exists_sql.indexOf("competence_userdept")==-1){
	        					exists_sql.append(",competence_userdept cu");
	        				}
	        			}
	        		}
	        		
	        	}else{			//�����������
	        		
	        		if(new String (exists_sql)==null || new String (exists_sql).equals("")){		//�жϡ�exists(select 1 from �� �����Ƿ�Ϊ��
	        			
	        			//exists_sqlΪ�գ�tableName��ǰ��ѯ���������ڣ���һ��ƴ��tableName�ģ�join_sql������exists����
	        			join_sql.append(" "+tableName.toLowerCase()+".a0000 = a01.a0000");
		        			
		        		exists_sql.append(" exists (select 1 from "+tableName+" "+tableName.toLowerCase());
		        		
	        		}else{		//exists_sql��Ϊ�գ���ƴ��exists
	        			
	        			if(exists_sql.indexOf(tableName)==-1){		//exists_sql����ֵ����������tableName��ƴ��tableName
	        				//׷��join_sql��tableName��Ϣ
	        				join_sql.append(" and "+tableName.toLowerCase()+".a0000 = a01.a0000");
	        				
	        				//׷��exists_sql��tableName��Ϣ
	        				exists_sql.append(","+tableName+" "+tableName.toLowerCase());
	        			}
	        			
	        		}
	        		
	        	}
	    		
	    		
	    		//�ж�existswhere_sql�Ƿ�Ϊ�գ�Ϊ����Ϊ��һ��������������ǰ��and
        		if(new String (existswhere_sql)==null || new String (existswhere_sql).equals("") && !tableName.equals("A01")){
        			if(m.get("opeartors").toString().equals("({c} is null or {c}={v})")){//Ϊ��
    					//existswhere_sql.append(" a01fujun."+colNames);
        				existswhere_sql.append(" and "+m.get("leftBracket"));
    					opeators = m.get("opeartors").toString().replace("{c}", tableName.toLowerCase()+"."+colNames);
    				}else{
    					existswhere_sql.append(" and "+m.get("leftBracket")+tableName.toLowerCase()+"."+colNames);
    				}
        			
        		}else if(!tableName.equals("A01")){
        			if(m.get("opeartors").toString().equals("({c} is null or {c}={v})")){//Ϊ��
    					//existswhere_sql.append(" a01fujun."+colNames);
    					opeators = m.get("opeartors").toString().replace("{c}", tableName.toLowerCase()+"."+colNames);
    				}else{
    					existswhere_sql.append(tableName.toLowerCase()+"."+colNames);
    				}
        			
        		}
        		
	    		
	    		//�������ѯ��������ѯֵ
	    		if(!colNames.toUpperCase().equals("AGE")){//�����ѯ����
	    			if(!"11".equals(operatorCodeValue.getSubCodeValue())){
	    				
	    				existswhere_sql.append(" "+opeators.replace("v", colValues).replace("{", "'").replace("}", "'")+" ");//�����
	    				
	    			}else{
	    				int colValuesInt = 0;
	        			try {
	    					colValuesInt = Integer.parseInt(colValues);
	    				} catch (NumberFormatException e) {
	    					throw new AppException("��"+operatorCodeValue.getCodeColumnName()+"���������Ӧ������ֻ��Ϊ����������");
	    				}
	        			String colValues11 = "";
	        			if(tableName.equals("A01")){
	        				colValues11 =" "+m.get("opeartors").toString().replace("v", colValuesInt+"").replace("{", "").replace("}", "")
	            					.replace("trunc", "to_char(trunc").replace(")", "),'yyyymmdd')");//�����
	        			}else{
	        				colValues11 =" "+m.get("opeartors").toString().replace("v", colValuesInt+"").replace("{", "").replace("}", "")
	            					.replace("trunc", "to_char(trunc").replace(")", "),'yyyymmdd')");//�����
	        			}
	        			
	        			
	        			if(DBUtil.getDBType() == DBType.MYSQL){
	        				colValues11 = colValues11.replace("sysdate", "now()");
	        			}
	        			existswhere_sql.append(colValues11);
	    			}
	    		}
	    		existswhere_sql.append(m.get("rightBracket"));			//ƴ�������ţ���ҳ����������£�
	    		
	    		
	    		//����ƴ�ӵ����existswhere_sql������������
	    		if(list.size()==count){
	    			existswhere_sql.insert(4,"( "); 
	    			
	    			existswhere_sql.append(") ) )");
	    		}
		    	existswhere_sql.append(m.get("logicSymbols"));		//ƴ���������ӹ�ϵ
		    	
	    	}						//forβ��
	    	
	    	
	    	
    	}
    	and_sql = comsql.toString();
    	
    	//�����exists��ѯ����ƴ��sql
    	if(new String (exists_sql)!=null && !new String (exists_sql).equals("")){
    		where_sql = where_sql.append(exists_sql).append(" where").append(join_sql).append(existswhere_sql);
    	}
    	
    	
    	if(1==2 && errNum==1 && DBUtil.getDBType() == DBType.ORACLE){//����ǳ��ò�ѯ�����������ݿ���oracle��������ƴװsql
    		and_sql = (and_sql+where_sql).replace(") and 1=1", ")) and 1=1"); 
    	}else{
    		and_sql = and_sql+where_sql;
    	}
    	//��װsql
    	sqlbuf.append(and_sql);
    	
    	sql=sqlbuf.toString();
    	CommonQueryBS.systemOut("---->"+sql);
    	String msg= checkSql(sql);
    	if(msg!=null)
    		throw new AppException(msg);  
    	return sql;
    }
    
    
    
	/**
	 * @$comment ������ɵ�sql�Ƿ���ȷ
	 * (У��sql�����ż�and�����Ƿ���ȱʧ���߶�������)
	 * @param String ����ƴװ�õ�sql���
	 * @return String
	 * @throws AppException 
	 * @throws 
	 * @author lixy
	 */
    private static String checkSql(String sql){
    	String errorMsg=null;
    	if(sql.lastIndexOf("and")==(sql.length()-4)||sql.lastIndexOf("or")==(sql.length()-3))
    		return errorMsg="��ѯ�������������������ӷ������飡"; 
    	if(sql.indexOf("and 1=1")!=(sql.length()-8))
    		return errorMsg="����������֮������ù�ϵ���ӷ����������飡";        	
    	int offset=0;
    	int leftBracketCnt=0;
    	int rightBracketCnt=0;  	
        while((offset = sql.indexOf("(", offset)) != -1){
            offset = offset + "(".length();
            leftBracketCnt++;
        }
        offset=0;
        while((offset = sql.indexOf(")", offset)) != -1){
            offset = offset + ")".length();
            rightBracketCnt++;
        }
        if(leftBracketCnt>rightBracketCnt){
        	return errorMsg="ȱʧ�����ţ�����ݲ�ѯ����һ���˲飡";
        }else if(leftBracketCnt<rightBracketCnt){
        	return errorMsg="ȱʧ�����ţ�����ݲ�ѯ����һ���˲飡";
        }
    	return errorMsg;
    }
    
    /**
	 * @$comment ����������������sql��ʾ����  
	 * 
	 * @param list ��������
	 * @return String
	 * @throws AppException 
	 * @throws 
	 * @author lixy
	 */
    public  String  createSQLView(List<HashMap<String, Object>> list) throws AppException{
    	String  sqlView=null;
    	//��װsql���Ľ�����Ϣ�����ڲ�����Ա���
    	for(HashMap<String, Object> m:list){
    		sqlView=sqlView+m.get("leftBracket")+m.get("colNames")+m.get("opeartors")+m.get("colValuesView")+m.get("rightBracket")+m.get("logicSymbols");
    	}
    	
    	return sqlView;
    }
    
    
    //�޸�a01ʱ�������޸��˺��޸�ʱ��
    public static  void  setA01(String a0000) throws AppException{
    	
    	HBSession sess = HBUtil.getHBSession();
    	A01 a01 = (A01)sess.get(A01.class, a0000);
    	
    	UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		Date date = new Date();//���ϵͳʱ��.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf.format(date);//��ʱ���ʽת���ɷ���TimestampҪ��ĸ�ʽ.
        Timestamp dates =Timestamp.valueOf(nowTime);//��ʱ��ת��
        a01.setXgr(user.getId());			//�޸���(��id)
        a01.setXgsj(dates.getTime());		    //�޸�ʱ��
    	
        sess.saveOrUpdate(a01);	
		sess.flush();
		
    }

	public static Customquery saveOrUodateCq(String queryId,String queryName,String querySql,String queryDes,
			String LoginName,String data, String data2) {
    	HBSession sess = HBUtil.getHBSession();
    	Customquery cq=new Customquery();
    	if(queryId!=null&&!queryId.equals(""))
    	    cq.setQueryid(queryId);
    	else
    	    cq.setQueryid( UUID.randomUUID().toString());
    	cq.setCreatetime(new Date());
    	cq.setQueryname(queryName);//��ѯ��������
    	cq.setQuerysql(querySql);//��ѯsql
    	cq.setQuerydescription(queryDes);//����sql
    	cq.setLoginname(LoginName);//�û���¼��
    	cq.setGridstring(data);//json����grid������
    	cq.setQuerycond(data2);//json����grid������
    	sess.saveOrUpdate(cq);
    	return cq;
    }
    
}
