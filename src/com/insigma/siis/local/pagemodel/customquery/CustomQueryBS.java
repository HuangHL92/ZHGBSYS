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
 * 组合查询工具类
 */

public class  CustomQueryBS {
	public  List<CodeTableCol> ctcList=null;//信息集
	public List<Map<String,String>> collectionList=null;//信息集表中中所有信息
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
	 * @$comment 返回查询项目列名
	 * @param code 信息集代码
	 * @return List<CodeTableCol>
	 * @throws 
	 * @author lixy
	 */
    public static TreeMap<String, String>  getCtcListByCollectionCode(String code,List<CodeTableCol> ctcList){
    	TreeMap<String, String>  map=null;
    	
    	if(ctcList!=null){
    		map=new TreeMap();
        	for (CodeTableCol c : ctcList) {
				if(c.getColLectionName().equals(code) && "1".equals(c.getIsZbx()))//根据信息集编码判断是否字段项是否属于这个信息集
					map.put(c.getColCode(), c.getColName());
			}
        }
    	
        return map;
    }
    
    
	/**
	 * @$comment 保存查询语句
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
    	cq.setQueryname(queryName);//查询条件名称
    	cq.setQuerysql(querySql);//查询sql
    	cq.setQuerydescription(queryDes);//排序sql
    	cq.setLoginname(LoginName);//用户登录名
    	cq.setGridstring(data);//json保存grid中数据
    	sess.saveOrUpdate(cq);
    	return cq;
    }
    
	/**
	 * @param osql 
	 * @$comment 保存列表
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
			if(cqli != null && !"".equals(cqli)){	//覆盖操作 删除原列表
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
			throw new AppException("保存列表数据失败");
		}
		

    }
    /**
     * @author wangs2
     *        保存列表
     * @param listName	名称
     * @param LoginName	登录名
     * @param sql
     * @param cqli		uuid
     * @param parentid		父ID
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
			if(cqli != null && !"".equals(cqli)){	//覆盖操作 删除原列表
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
			throw new AppException("保存列表数据失败");
		}
    }
    public static void updateList(String listName,String listCount,String listData,String LoginName, String sql,String cqli) throws AppException{
    	Customquerylist cql=(Customquerylist)HBUtil.getHBSession().get(Customquerylist.class, cqli);
    	if(cql == null || "".equals(cql)){
    		System.out.println(cqli+"暂无数据，无法覆盖！");
    		throw new AppException(cqli+"暂无数据，无法覆盖！");
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
			if(cqli != null && !"".equals(cqli)){	//覆盖操作 删除原列表
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
			throw new AppException("保存列表数据失败");
		}
		

    }
    /**
	 * @param osql 
	 * @$comment 事务提醒保存列表
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
			
			throw new AppException("存在非法日期，列表无法保存！");
		}
		

    }
    
	/**
	 * @$comment 删除固定条件查询中的项
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
	 * @$comment 删除列表
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
	 * @$comment 删除列表
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
	 * @$comment 删除上次查询sql
	 * @param 
	 * @return 
	 * @throws 
	 * @author lixy
	 */    
    public static void delLastTimeCq() throws AppException{
    	HBSession sess = HBUtil.getHBSession();
    	Query query=sess.createSQLQuery("delete from customquery where queryname='上次查询'");
    	query.executeUpdate();
    }
    
	/**
	 * @$comment 删除常用条件sql
	 * @param 
	 * @return 
	 * @throws 
	 * @author lixy
	 */    
    public static void delComm() throws AppException{
    	HBSession sess = HBUtil.getHBSession();
    	Query query=sess.createSQLQuery("delete from customquery where queryname='常用条件'");
    	query.executeUpdate();
    }
    
	
	/**
	 * @$comment 返回查询信息集
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
	 * @$comment 返回保存的列表
	 * @param loginname登录用户名
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
		//使用迭代器将查询到的数据放入list中
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
	 * @$comment 返回固定查询条件
	 * @param loginname登录用户名
	 * @return List<Map<String, String>>
	 * @throws AppException 
	 * @throws 
	 * @author lixy
	 */
    public static List<Map<String, String>> getCustomSqlList(String loginname){
    	return getCustomSqlList(loginname,false);
    }
    /**
	 * @$comment 返回固定查询条件
	 * @param loginname登录用户名
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
	 * @$comment 返回所有可以作为条件的查询项信息
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
	 * @$comment 根据字段名称查询查询指标项对象
	 * @param colCode
	 * @return CodeTableCol
	 * @throws 
	 * @author lixy
	 */
    public static CodeTableCol getCtc(String colCode,List<CodeTableCol> ctcList){
    	CodeTableCol ctc=null;
    	
        if(ctcList!=null){
        	for (CodeTableCol c : ctcList) {//根据字段名称判断是否字段项是否属于这个信息集
				if(c.getColCode().equals(colCode)){
					ctc=c;
					break;
				}					
			}
        }
		
    	return ctc;
    }
    
	/**
	 * @$comment 根据指标项信息查询对应的值列表
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
	 * @$comment 根据指标项列名查询对应的值列表
	 * @param colCode 指标项,aaa102 指标项值
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
					//CodeType配置为空，取列名作为codeType  mengl 20160629
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
	 * @$comment 根据网格数据生成对应查询sql--统计分析用
	 * 2017-5-22，方法sql拼接重构，fujun
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
    	String comsql = "";//查询主体
    	StringBuffer sqlbuf = new StringBuffer();//sql 查询列部分
    	StringBuffer where_sql = new StringBuffer();//where 部分
    	
    	//将where_sql分为三部分（a01的查询条件直接放在where_sql中）
    	StringBuffer exists_sql = new StringBuffer();		//exists主sql
    	StringBuffer join_sql = new StringBuffer();			//对表做关系关联的sql
    	StringBuffer existswhere_sql = new StringBuffer();	//existswhere部分(具体的每张表查询条件)
    	
    	String and_sql = ""; //and关联关系拼接成的sql
    	StringBuffer sqlend = new StringBuffer();//sql结束
    	String comwhere = "A0000,A0101,A0104,A0117,A0141,A0192A,"
    			+ "A0148,A0160,A0192D,A0120,QRZXL,ZZXL,A0107,AGE,"
    			+ "A0140,A0134,A0165,A0121,A0184,ORGID,STATUS,"
    			+ "A0163,A0201B,A0221,A0221A,A0288,A0219,A0801B"; //常用查询条件
    	//判断如果所有条件满足常用条件查询的条件，则返回1，如果不满足则返回0
    	for(HashMap<String, Object> m:list){
    		String  colNames= m.get("colNamesValue").toString();//sql中列名
    		if(!comwhere.contains(colNames.toUpperCase())){
    			errNum = 0;
    			break;
    		}
    	}
    	//判断如果所有条件满足常用条件，并且数据库为oracle，则启用物化视图查询
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
    		for(HashMap<String, Object> m:list){//根据grid中数据list进行循环
    			count++;
    			String tableName = m.get("tableName").toString().toUpperCase();//sql中条件表名
	    		String  colNames= m.get("colNamesValue").toString();//sql中列名
	    		String opeators = m.get("opeartors").toString();//运算符
	    		//特殊代码信息项  符号处理  --lzy update
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
	    		String colValues = m.get("colValues").toString();//sql中列值
	    		where_sql.append(m.get("leftBracket"));
	    		if(colNames.toUpperCase().equals("AGE")){//判断如果是年龄，则根据出生日期进行条件拼装
    				where_sql.append(" mc.a0107 ");
    				String startvalue = "";
    	    		String endvalue = "";
    	    		String strdate = DateUtil.getcurdate();//获取日期
	    			String year = strdate.substring(0, 4);//截取年
	    			String mm = strdate.substring(4,6);//截取月
	    			String syear = String.valueOf((Long.valueOf(year)-Long.valueOf(colValues)));
        			String eyear = String.valueOf((Long.valueOf(year)-Long.valueOf(colValues)-1));
    				startvalue = syear+mm+"01";
    				endvalue = eyear+mm+"01";
    				//判断运算符，如果是大于年龄，则出生日期小于时间
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
    					where_sql.append(" > '"+endvalue+"' and mc.A0107<='"+startvalue+"'");//运算符
	    			}else{
	    				where_sql.append(" "+opeators.replace("v", startvalue).replace("{", "'").replace("}", "'"));//运算符
	    			}
    			}else{
    				where_sql.append(" mc."+colNames);
    			}
	    		if(!colNames.toUpperCase().equals("AGE")){//判断如果不是年龄字段
	    			if(!"11".equals(operatorCodeValue.getSubCodeValue())){
	    				where_sql.append(" "+opeators.replace("v", colValues).replace("{", "'").replace("}", "'")+" ");//运算符
	    			}else{
	    				int colValuesInt = 0;
	        			try {
	    					colValuesInt = Integer.parseInt(colValues);
	    				} catch (NumberFormatException e) {
	    					throw new AppException("【"+operatorCodeValue.getCodeColumnName()+"】运算符对应的运算只能为正负整数！");
	    				}
	        			String colValues11 = "";
	        			
	        			colValues11 =" "+m.get("opeartors").toString().replace("v", colValuesInt+"").replace("{", "").replace("}", "")
	            				.replace("trunc", "to_char(trunc").replace(")", "),'yyyymmdd')");//运算符
	        				        			
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
	    	//用实体表查询的主sql
	    	comsql ="select  a01.a0000, a0101, a0104, a0117, a0141, a0192a, a0148,A0160,A0192D,A0120,QRZXL,ZZXL " 
	    			+ ",a0107,a0140,a0134,a0165,a0121,a0184,orgid,a01.status from A01 a01 where exists (select 1 "
	    			+ " from A02 a02, competence_userdept cu where a02.A0201B = cu.b0111 and a02.a0000 = a01.a0000 "
	    			+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"') "
	    			+ "and not exists (select 1 from COMPETENCE_USERPERSON cu where cu.a0000 = a01.a0000 "
	    			+ "and cu.userid = '"+SysManagerUtils.getUserId()+"')";
	    	
	    	if(list.size()>0){
	    		where_sql.append(" and (");
	    	}
	    	for(HashMap<String, Object> m:list){//根据grid中数据list进行循环
	    		count++;
	    		String tableName = m.get("tableName").toString().toUpperCase();//sql中条件表名
	    		String  colNames= m.get("colNamesValue").toString();//sql中列名
	    		String opeators = m.get("opeartors").toString();//运算符
	    		
	    		//特殊代码信息项  符号处理  --lzy update
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
	    		String colValues = m.get("colValues").toString();//sql中列值	  
	    		
	    		if(count != 1){
	    			existswhere_sql.append(m.get("leftBracket"));			//拼接左括号（在页面设置情况下）
	    		}
	    		
	    		//如果不是A01或A02中的条件   		
	    		if(tableName.equals("A01")){ 
	    			
	    			
	    			if(new String (exists_sql)==null || new String (exists_sql).equals("")){		//判断“exists(select 1 from 表” 部分是否为空
	        			
	    				//exists_sql为空，A01当前查询条件不存在，第一次拼接A01的，join_sql条件和exists条件
	        			join_sql.append(" a01fujun.a0000 = a01.a0000");
	        			
		        		exists_sql.append(" exists (select 1 from A01 a01fujun ");
		        		
		        		existswhere_sql.append(" and").append(m.get("leftBracket"));
		        		
	        		}else{		//exists_sql不为空，已拼接exists
	        			
	        			if(exists_sql.indexOf("A01")==-1){		//exists_sql存在值，但不存在A01，拼接A01
	        				
	        				//追加join_sql的A02信息
	        				join_sql.append(" and a01fujun.a0000 = a01.a0000");
	        				
	        				//追加exists_sql的A01信息
	        				exists_sql.append(",A01 a01fujun ");
	        				
	        			}
	        			
	        		}
	    			
	    			
	    			
	    			//如果列名为年龄，将其转化为查询出生日期
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
	    					existswhere_sql.append(" > '"+endvalue+"' and a01fujun.A0107<='"+startvalue+"'");//运算符
		    			}else{
		    				existswhere_sql.append(" "+opeators.replace("v", startvalue).replace("{", "'").replace("}", "'"));//运算符
		    			}
	    			}else{
	    				
	    				existswhere_sql.append(" a01fujun."+colNames);
	    			} 
	    			
	    			
	        	}else if(tableName.equals("A02")){ //如果查询职务信息，则关联A02
	        		
	        		
	        		if(new String (exists_sql)==null || new String (exists_sql).equals("")){		//判断“exists(select 1 from 表” 部分是否为空
	        			
	        			//exists_sql为空，A02当前查询条件不存在，第一次拼接A02的，join_sql条件和exists条件
	        			join_sql.append(" a02.A0201B = cu.b0111 and a02.a0000 = a01.a0000"
		        				+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");
		        			
		        		exists_sql.append(" exists (select 1 from A02 a02, competence_userdept cu ");
		        		
	        		}else{		//exists_sql不为空，已拼接exists
	        			
	        			if(exists_sql.indexOf("B01")==-1 && exists_sql.indexOf("A02")==-1){		//exists_sql存在值，但不存在B01和A02，拼接A02
	        				//追加join_sql的A02信息
	        				join_sql.append(" and a02.A0201B = cu.b0111 and a02.a0000 = a01.a0000"
			        				+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");
	        				//追加exists_sql的A02信息
	        				exists_sql.append(",A02 a02, competence_userdept cu ");
	        			}
	        			//exists_sql中存在B01和A02，任意一项，则不再为join_sql和exists_sql拼接A02信息
	        		}
	        		
	        		
	        	}else if(tableName.equals("B01")){
	        		
	        		if(new String (exists_sql)==null || new String (exists_sql).equals("")){		//判断“exists(select 1 from 表” 部分是否为空
	        			
	        			//exists_sql为空，B01当前查询条件不存在，第一次拼接B01的，join_sql条件和exists条件
	        			join_sql.append(" a02.A0201B = cu.b0111 and a02.a0000 = a01.a0000"
		        				+ " and a02.A0201B = b01.b0111 "
		        				+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");
		        			
		        		exists_sql.append(" exists (select 1 from A02 a02, competence_userdept cu,B01 b01 ");
		        		
	        		}else{		//exists_sql不为空，已拼接exists
	        			
	        			if(exists_sql.indexOf("B01")==-1 && exists_sql.indexOf("A02")==-1){		//exists_sql存在值，但不存在B01和A02，拼接B01
	        				//追加join_sql的B01信息
	        				join_sql.append(" and a02.A0201B = cu.b0111 and a02.a0000 = a01.a0000"
			        				+ " and a02.A0201B = b01.b0111 "
			        				+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");
	        				//追加exists_sql的B01信息
	        				exists_sql.append(",A02 a02, competence_userdept cu,B01 b01 ");
	        				
	        			}else if(exists_sql.indexOf("B01")==-1 && exists_sql.indexOf("A02")!=-1){//exists_sql中存在A02，不存在B01，则为join_sql和exists_sql拼接B01超出A02的信息
	        				
	        				//追加join_sql的B01信息
	        				join_sql.append(" and a02.A0201B = b01.b0111 ");
			        				
	        				//追加exists_sql的B01信息
	        				exists_sql.append(",A02 a02, competence_userdept cu,B01 b01 ");
	        			}
	        		}
	        		
	        	}else{			//其他任意表处理
	        		
	        		if(new String (exists_sql)==null || new String (exists_sql).equals("")){		//判断“exists(select 1 from 表” 部分是否为空
	        			
	        			//exists_sql为空，tableName当前查询条件不存在，第一次拼接tableName的，join_sql条件和exists条件
	        			join_sql.append(" "+tableName.toLowerCase()+".a0000 = a01.a0000");
		        			
		        		exists_sql.append(" exists (select 1 from "+tableName+" "+tableName.toLowerCase());
		        		
	        		}else{		//exists_sql不为空，已拼接exists
	        			
	        			if(exists_sql.indexOf(tableName)==-1){		//exists_sql存在值，但不存在tableName，拼接tableName
	        				//追加join_sql的tableName信息
	        				join_sql.append(" and "+tableName.toLowerCase()+".a0000 = a01.a0000");
	        				
	        				//追加exists_sql的tableName信息
	        				exists_sql.append(","+tableName+" "+tableName.toLowerCase());
	        			}
	        			
	        		}
	        		
	        	}
	    		
	    		
	    		//判断existswhere_sql是否为空，为空则为第一个条件，在条件前加and
        		if(new String (existswhere_sql)==null || new String (existswhere_sql).equals("") && !tableName.equals("A01")){
        			existswhere_sql.append(" and "+m.get("leftBracket")+tableName.toLowerCase()+"."+colNames);
        		}else if(!tableName.equals("A01")){
        			existswhere_sql.append(tableName.toLowerCase()+"."+colNames);
        		}
        		
	    		
	    		//给具体查询条件赋查询值
	    		if(!colNames.toUpperCase().equals("AGE")){//如果查询年龄
	    			if(!"11".equals(operatorCodeValue.getSubCodeValue())){
	    				if(tableName.equals("A01")){
	    					existswhere_sql.append(" "+opeators.replace("v", colValues).replace("{", "'").replace("}", "'")+" ");//运算符
	    				}else{
	    					existswhere_sql.append(" "+opeators.replace("v", colValues).replace("{", "'").replace("}", "'"));//运算符
	    				}
	    				
	    			}else{
	    				int colValuesInt = 0;
	        			try {
	    					colValuesInt = Integer.parseInt(colValues);
	    				} catch (NumberFormatException e) {
	    					throw new AppException("【"+operatorCodeValue.getCodeColumnName()+"】运算符对应的运算只能为正负整数！");
	    				}
	        			String colValues11 = "";
	        			if(tableName.equals("A01")){
	        				colValues11 =" "+m.get("opeartors").toString().replace("v", colValuesInt+"").replace("{", "").replace("}", "")
	            					.replace("trunc", "to_char(trunc").replace(")", "),'yyyymmdd')");//运算符
	        			}else{
	        				colValues11 =" "+m.get("opeartors").toString().replace("v", colValuesInt+"").replace("{", "").replace("}", "")
	            					.replace("trunc", "to_char(trunc").replace(")", "),'yyyymmdd')");//运算符
	        			}
	        			
	        			
	        			if(DBUtil.getDBType() == DBType.MYSQL){
	        				colValues11 = colValues11.replace("sysdate", "now()");
	        			}
	        			existswhere_sql.append(colValues11);
	    			}
	    		}
	    		existswhere_sql.append(m.get("rightBracket"));			//拼接右括号（在页面设置情况下）
	    		
	    		
	    		//条件拼接的最后将existswhere_sql完善括号起来
	    		if(list.size()==count){
	    			existswhere_sql.insert(4,"( "); 
	    			
	    			existswhere_sql.append(") ) )");
	    		}
		    	existswhere_sql.append(m.get("logicSymbols"));		//拼接条件连接关系
		    	
	    	}						//for尾部
	    	
	    	
	    	
    	}
    	and_sql = comsql.toString();
    	
    	//如果有exists查询，则拼接sql
    	if(new String (exists_sql)!=null && !new String (exists_sql).equals("")){
    		where_sql = where_sql.append(exists_sql).append(" where").append(join_sql).append(existswhere_sql);
    	}
    	
    	
    	if(1==2 && errNum==1 && DBUtil.getDBType() == DBType.ORACLE){//如果是常用查询条件并且数据库是oracle的则如下拼装sql
    		and_sql = (and_sql+where_sql).replace(") and 1=1", ")) and 1=1"); 
    	}else{
    		and_sql = and_sql+where_sql;
    	}
    	//组装sql
    	sqlbuf.append(and_sql);
    	
    	sql=sqlbuf.toString();
    	CommonQueryBS.systemOut("---->"+sql);
    	String msg= checkSql(sql);
    	if(msg!=null)
    		throw new AppException(msg);  
    	return sql;
    }
    
	/**
	 * @$comment 根据网格数据生成对应查询sql（原为createSQL，目前方法已改造，此方法已废弃）
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
    	String comsql = "";//查询主体
    	StringBuffer sqlbuf = new StringBuffer();//sql 查询列部分
    	StringBuffer where_sql = new StringBuffer();//where 部分
    	StringBuffer exists_sql = new StringBuffer();//exists主sql
    	StringBuffer existswhere_sql = new StringBuffer();//existswhere部分
    	String and_sql = ""; //and关联关系拼接成的sql
    	StringBuffer sqlend = new StringBuffer();//sql结束
    	String comwhere = "A0000,A0101,A0104,A0117,A0141,A0192A,"
    			+ "A0148,A0160,A0192D,A0120,QRZXL,ZZXL,A0107,AGE,"
    			+ "A0140,A0134,A0165,A0121,A0184,ORGID,STATUS,"
    			+ "A0163,A0201B,A0221,A0221A,A0288,A0219,A0801B"; //常用查询条件
    	//判断如果所有条件满足常用条件查询的条件，则返回1，如果不满足则返回0
    	for(HashMap<String, Object> m:list){
    		String  colNames= m.get("colNamesValue").toString();//sql中列名
    		if(!comwhere.contains(colNames.toUpperCase())){
    			errNum = 0;
    			break;
    		}
    	}
    	//判断如果所有条件满足常用条件，并且数据库为oracle，则启用物化视图查询
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
    		for(HashMap<String, Object> m:list){//根据grid中数据list进行循环
    			count++;
    			String tableName = m.get("tableName").toString().toUpperCase();//sql中条件表名
	    		String  colNames= m.get("colNamesValue").toString();//sql中列名
	    		String opeators = m.get("opeartors").toString();//运算符
	    		//特殊代码信息项  符号处理  --lzy update
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
	    		String colValues = m.get("colValues").toString();//sql中列值
	    		where_sql.append(m.get("leftBracket"));
	    		if(colNames.toUpperCase().equals("AGE")){//判断如果是年龄，则根据出生日期进行条件拼装
    				where_sql.append(" mc.a0107 ");
    				String startvalue = "";
    	    		String endvalue = "";
    	    		String strdate = DateUtil.getcurdate();//获取日期
	    			String year = strdate.substring(0, 4);//截取年
	    			String mm = strdate.substring(4,6);//截取月
	    			String syear = String.valueOf((Long.valueOf(year)-Long.valueOf(colValues)));
        			String eyear = String.valueOf((Long.valueOf(year)-Long.valueOf(colValues)-1));
    				startvalue = syear+mm+"01";
    				endvalue = eyear+mm+"01";
    				//判断运算符，如果是大于年龄，则出生日期小于时间
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
    					where_sql.append(" > '"+endvalue+"' and mc.A0107<='"+startvalue+"'");//运算符
	    			}else{
	    				where_sql.append(" "+opeators.replace("v", startvalue).replace("{", "'").replace("}", "'"));//运算符
	    			}
    			}else{
    				where_sql.append(" mc."+colNames);
    			}
	    		if(!colNames.toUpperCase().equals("AGE")){//判断如果不是年龄字段
	    			if(!"11".equals(operatorCodeValue.getSubCodeValue())){
	    				where_sql.append(" "+opeators.replace("v", colValues).replace("{", "'").replace("}", "'")+" ");//运算符
	    			}else{
	    				int colValuesInt = 0;
	        			try {
	    					colValuesInt = Integer.parseInt(colValues);
	    				} catch (NumberFormatException e) {
	    					throw new AppException("【"+operatorCodeValue.getCodeColumnName()+"】运算符对应的运算只能为正负整数！");
	    				}
	        			String colValues11 = "";
	        			
	        			colValues11 =" "+m.get("opeartors").toString().replace("v", colValuesInt+"").replace("{", "").replace("}", "")
	            				.replace("trunc", "to_char(trunc").replace(")", "),'yyyymmdd')");//运算符
	        				        			
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
	    	//用实体表查询的主sql
	    	comsql ="select  a01.a0000, a0101, a0104, a0117, a0141, a0192a, a0148,A0160,A0192D,A0120,QRZXL,ZZXL " 
	    			+ ",a0107,a0140,a0134,a0165,a0121,a0184,orgid,a01.status from A01 a01 where exists (select 1 "
	    			+ " from A02 a02, competence_userdept cu where a02.A0201B = cu.b0111 and a02.a0000 = a01.a0000 "
	    			+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"') "
	    			+ "and not exists (select 1 from COMPETENCE_USERPERSON cu where cu.a0000 = a01.a0000 "
	    			+ "and cu.userid = '"+SysManagerUtils.getUserId()+"')";
	    	
	    	if(list.size()>0){
	    		where_sql.append(" and (");
	    	}
	    	for(HashMap<String, Object> m:list){//根据grid中数据list进行循环
	    		count++;
	    		String tableName = m.get("tableName").toString().toUpperCase();//sql中条件表名
	    		String  colNames= m.get("colNamesValue").toString();//sql中列名
	    		String opeators = m.get("opeartors").toString();//运算符
	    		
	    		//特殊代码信息项  符号处理  --lzy update
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
	    		String colValues = m.get("colValues").toString();//sql中列值	  
	    			    		
	    		where_sql.append(m.get("leftBracket"));
	    		//如果不是A01或A02中的条件   		
	    		if(tableName.equals("A01")){ 
	    			//如果列名为年龄，将其转化为查询出生日期
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
	    					where_sql.append(" > '"+endvalue+"' and a01.A0107<='"+startvalue+"'");//运算符
		    			}else{
		    				where_sql.append(" "+opeators.replace("v", startvalue).replace("{", "'").replace("}", "'"));//运算符
		    			}
	    			}else{
	    				where_sql.append(" a01."+colNames);
	    			}  			
	        	}else if(tableName.equals("A02")){ //如果查询职务信息，则关联A02
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
	    		if(!colNames.toUpperCase().equals("AGE")){//如果查询年龄
	    			if(!"11".equals(operatorCodeValue.getSubCodeValue())){
	    				if(tableName.equals("A01")){
	    					where_sql.append(" "+opeators.replace("v", colValues).replace("{", "'").replace("}", "'")+" ");//运算符
	    				}else{
	    					where_sql.append(" "+opeators.replace("v", colValues).replace("{", "'").replace("}", "'")+") ");//运算符
	    				}
	    				
	    			}else{
	    				int colValuesInt = 0;
	        			try {
	    					colValuesInt = Integer.parseInt(colValues);
	    				} catch (NumberFormatException e) {
	    					throw new AppException("【"+operatorCodeValue.getCodeColumnName()+"】运算符对应的运算只能为正负整数！");
	    				}
	        			String colValues11 = "";
	        			if(tableName.equals("A01")){
	        				colValues11 =" "+m.get("opeartors").toString().replace("v", colValuesInt+"").replace("{", "").replace("}", "")
	            					.replace("trunc", "to_char(trunc").replace(")", "),'yyyymmdd')");//运算符
	        			}else{
	        				colValues11 =" "+m.get("opeartors").toString().replace("v", colValuesInt+"").replace("{", "").replace("}", "")
	            					.replace("trunc", "to_char(trunc").replace(")", "),'yyyymmdd'))");//运算符
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
	    		where_sql.append(m.get("logicSymbols"));		//拼接，条件连接关系
	
	    	}
    	}
    	and_sql = comsql.toString();
    	if(errNum==1 && DBUtil.getDBType() == DBType.ORACLE){//如果是常用查询条件并且数据库是oracle的则如下拼装sql
    		and_sql = (and_sql+where_sql).replace(") and 1=1", ")) and 1=1"); 
    	}else{
    		and_sql = and_sql+where_sql;
    	}
    	//组装sql
    	sqlbuf.append(and_sql);
    	
    	sql=sqlbuf.toString();
    	CommonQueryBS.systemOut("---->"+sql);
    	String msg= checkSql(sql);
    	if(msg!=null)
    		throw new AppException(msg);  
    	return sql;
    }
    
    
    
    /**
	 * @$comment 根据网格数据生成对应查询sql————————fujun
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
    	String comsql = "";//查询主体
    	StringBuffer sqlbuf = new StringBuffer();//sql 查询列部分
    	StringBuffer where_sql = new StringBuffer();//where 部分
    	
    	//将where_sql分为三部分（a01的查询条件直接放在where_sql中）
    	StringBuffer exists_sql = new StringBuffer();		//exists主sql
    	StringBuffer join_sql = new StringBuffer();			//对表做关系关联的sql
    	StringBuffer existswhere_sql = new StringBuffer();	//existswhere部分(具体的每张表查询条件)
    	
    	String and_sql = ""; //and关联关系拼接成的sql
    	StringBuffer sqlend = new StringBuffer();//sql结束
    	String comwhere = "A0000,A0101,A0104,A0117,A0141,A0192A,"
    			+ "A0148,A0160,A0192D,A0120,QRZXL,ZZXL,A0107,AGE,"
    			+ "A0140,A0134,A0165,A0121,A0184,ORGID,STATUS,"
    			+ "A0163,A0201B,A0221,A0221A,A0288,A0219,A0801B"; //常用查询条件
    	//判断如果所有条件满足常用条件查询的条件，则返回1，如果不满足则返回0
    	for(HashMap<String, Object> m:list){
    		String  colNames= m.get("colNamesValue").toString();//sql中列名
    		if(!comwhere.contains(colNames.toUpperCase())){
    			errNum = 0;
    			break;
    		}
    	}
    	//判断如果所有条件满足常用条件，并且数据库为oracle，则启用物化视图查询
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
    		for(HashMap<String, Object> m:list){//根据grid中数据list进行循环
    			count++;
    			String tableName = m.get("tableName").toString().toUpperCase();//sql中条件表名
	    		String  colNames= m.get("colNamesValue").toString();//sql中列名
	    		String opeators = m.get("opeartors").toString();//运算符
	    		//特殊代码信息项  符号处理  --lzy update
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
	    		String colValues = m.get("colValues").toString();//sql中列值
	    		where_sql.append(m.get("leftBracket"));
	    		if(colNames.toUpperCase().equals("AGE")){//判断如果是年龄，则根据出生日期进行条件拼装
    				where_sql.append(" mc.a0107 ");
    				String startvalue = "";
    	    		String endvalue = "";
    	    		String strdate = DateUtil.getcurdate();//获取日期
	    			String year = strdate.substring(0, 4);//截取年
	    			String mm = strdate.substring(4,6);//截取月
	    			String syear = String.valueOf((Long.valueOf(year)-Long.valueOf(colValues)));
        			String eyear = String.valueOf((Long.valueOf(year)-Long.valueOf(colValues)-1));
    				startvalue = syear+mm+"01";
    				endvalue = eyear+mm+"01";
    				//判断运算符，如果是大于年龄，则出生日期小于时间
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
    					where_sql.append(" > '"+endvalue+"' and mc.A0107<='"+startvalue+"'");//运算符
	    			}else{
	    				where_sql.append(" "+opeators.replace("v", startvalue).replace("{", "'").replace("}", "'"));//运算符
	    			}
    			}else{
    				where_sql.append(" mc."+colNames);
    			}
	    		if(!colNames.toUpperCase().equals("AGE")){//判断如果不是年龄字段
	    			if(!"11".equals(operatorCodeValue.getSubCodeValue())){
	    				where_sql.append(" "+opeators.replace("v", colValues).replace("{", "'").replace("}", "'")+" ");//运算符
	    			}else{
	    				int colValuesInt = 0;
	        			try {
	    					colValuesInt = Integer.parseInt(colValues);
	    				} catch (NumberFormatException e) {
	    					throw new AppException("【"+operatorCodeValue.getCodeColumnName()+"】运算符对应的运算只能为正负整数！");
	    				}
	        			String colValues11 = "";
	        			
	        			colValues11 =" "+m.get("opeartors").toString().replace("v", colValuesInt+"").replace("{", "").replace("}", "")
	            				.replace("trunc", "to_char(trunc").replace(")", "),'yyyymmdd')");//运算符
	        				        			
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
*/	    	//用实体表查询的主sql
	    	comsql =CommSQL.getComSQL(this.getSid())+" where  not exists (select 1 from COMPETENCE_USERPERSON cu where cu.a0000 = a01.a0000 "
	    			+ "and cu.userid = '"+SysManagerUtils.getUserId()+"')";
	    	
	    	if(list.size()>0){
	    		where_sql.append(" and (");
	    	}
	    	for(HashMap<String, Object> m:list){//根据grid中数据list进行循环
	    		count++;
	    		String tableName = m.get("tableName").toString().toUpperCase();//sql中条件表名
	    		String  colNames= m.get("colNamesValue").toString();//sql中列名
	    		String opeators = m.get("opeartors").toString();//运算符
	    		
	    		//特殊代码信息项  符号处理  --lzy update
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
	    		String colValues = m.get("colValues").toString();//sql中列值	  
	    		
	    		if("A0163".equals(colNames)&&"={v}".equals(opeators)){
	    			if("2".equals(colValues)){//查询非现职人员
	    				opeators = "!={v}";
	    				colValues = "1";
	    			}
	    		}else if("A0163".equals(colNames)&&"!={v}".equals(opeators)){
	    			if("2".equals(colValues)){//查询非现职人员
	    				opeators = "={v}";
	    				colValues = "1";
	    			}
	    		}
	    		
	    		if(count != 1){
	    			existswhere_sql.append(m.get("leftBracket"));			//拼接左括号（在页面设置情况下）
	    		}
	    		
	    		//如果不是A01或A02中的条件   		
	    		if(tableName.equals("A01")){ 
	    			
	    			
	    			if(new String (exists_sql)==null || new String (exists_sql).equals("")){		//判断“exists(select 1 from 表” 部分是否为空
	        			
	    				//exists_sql为空，A01当前查询条件不存在，第一次拼接A01的，join_sql条件和exists条件
	        			join_sql.append(" a01fujun.a0000 = a01.a0000");
	        			
		        		exists_sql.append(" exists (select 1 from A01 a01fujun ");
		        		
		        		existswhere_sql.append(" and").append(m.get("leftBracket"));
		        		
	        		}else{		//exists_sql不为空，已拼接exists
	        			
	        			if(exists_sql.indexOf("A01")==-1){		//exists_sql存在值，但不存在A01，拼接A01
	        				
	        				//追加join_sql的A02信息
	        				join_sql.append(" and a01fujun.a0000 = a01.a0000");
	        				
	        				//追加exists_sql的A01信息
	        				exists_sql.append(",A01 a01fujun ");
	        				
	        			}
	        			
	        		}
	    			
	    			
	    			
	    			//如果列名为年龄，将其转化为查询出生日期
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
	    					existswhere_sql.append(" > '"+endvalue+"' and a01fujun.A0107<='"+startvalue+"'");//运算符
		    			}else{
		    				existswhere_sql.append(" "+opeators.replace("v", startvalue).replace("{", "'").replace("}", "'"));//运算符
		    			}
	    			}else{
	    				if(m.get("opeartors").toString().equals("({c} is null or {c}={v})")){//为空
	    					//existswhere_sql.append(" a01fujun."+colNames);
	    					opeators = m.get("opeartors").toString().replace("{c}", " a01fujun."+colNames);
	    				}else{
	    					existswhere_sql.append(" a01fujun."+colNames);
	    				}
	    				
	    			} 
	    			
	    			
	        	}else if(tableName.equals("A02")){ //如果查询职务信息，则关联A02
	        		
	        		
	        		if(new String (exists_sql)==null || new String (exists_sql).equals("")){		//判断“exists(select 1 from 表” 部分是否为空
	        			
	        			//exists_sql为空，A02当前查询条件不存在，第一次拼接A02的，join_sql条件和exists条件
	        			join_sql.append(" a02.A0201B = cu.b0111 and a02.a0000 = a01.a0000"
		        				+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");
		        			
		        		exists_sql.append(" exists (select 1 from A02 a02, competence_userdept cu ");
		        		
	        		}else{		//exists_sql不为空，已拼接exists
	        			
	        			if(exists_sql.indexOf("B01")==-1 && exists_sql.indexOf("A02")==-1){		//exists_sql存在值，但不存在B01和A02，拼接A02
	        				//追加join_sql的A02信息
	        				join_sql.append(" and a02.A0201B = cu.b0111 and a02.a0000 = a01.a0000"
			        				+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");
	        				//追加exists_sql的A02信息
	        				exists_sql.append(",A02 a02, competence_userdept cu ");
	        			}
	        			//exists_sql中存在B01和A02，任意一项，则不再为join_sql和exists_sql拼接A02信息
	        		}
	        		
	        		
	        	}else if(tableName.equals("B01")){
	        		
	        		if(new String (exists_sql)==null || new String (exists_sql).equals("")){		//判断“exists(select 1 from 表” 部分是否为空
	        			
	        			//exists_sql为空，B01当前查询条件不存在，第一次拼接B01的，join_sql条件和exists条件
	        			join_sql.append(" a02.A0201B = cu.b0111 and a02.a0000 = a01.a0000"
		        				+ " and a02.A0201B = b01.b0111 "
		        				+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");
		        			
		        		exists_sql.append(" exists (select 1 from A02 a02, competence_userdept cu,B01 b01 ");
		        		
	        		}else{		//exists_sql不为空，已拼接exists
	        			
	        			if(exists_sql.indexOf("B01")==-1 && exists_sql.indexOf("A02")==-1){		//exists_sql存在值，但不存在B01和A02，拼接B01
	        				//追加join_sql的B01信息
	        				join_sql.append(" and a02.A0201B = cu.b0111 and a02.a0000 = a01.a0000"
			        				+ " and a02.A0201B = b01.b0111 "
			        				+ " AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");
	        				//追加exists_sql的B01信息
	        				exists_sql.append(",A02 a02,B01 b01 ");
	        				if(exists_sql.indexOf("competence_userdept")==-1){
	        					exists_sql.append(",competence_userdept cu");
	        				}
	        				
	        			}else if(exists_sql.indexOf("B01")==-1 && exists_sql.indexOf("A02")!=-1){//exists_sql中存在A02，不存在B01，则为join_sql和exists_sql拼接B01超出A02的信息
	        				
	        				//追加join_sql的B01信息
	        				join_sql.append(" and a02.A0201B = b01.b0111 ");
			        				
	        				//追加exists_sql的B01信息
	        				exists_sql.append(", B01 b01 ");
	        				if(exists_sql.indexOf("competence_userdept")==-1){
	        					exists_sql.append(",competence_userdept cu");
	        				}
	        			}
	        		}
	        		
	        	}else{			//其他任意表处理
	        		
	        		if(new String (exists_sql)==null || new String (exists_sql).equals("")){		//判断“exists(select 1 from 表” 部分是否为空
	        			
	        			//exists_sql为空，tableName当前查询条件不存在，第一次拼接tableName的，join_sql条件和exists条件
	        			join_sql.append(" "+tableName.toLowerCase()+".a0000 = a01.a0000");
		        			
		        		exists_sql.append(" exists (select 1 from "+tableName+" "+tableName.toLowerCase());
		        		
	        		}else{		//exists_sql不为空，已拼接exists
	        			
	        			if(exists_sql.indexOf(tableName)==-1){		//exists_sql存在值，但不存在tableName，拼接tableName
	        				//追加join_sql的tableName信息
	        				join_sql.append(" and "+tableName.toLowerCase()+".a0000 = a01.a0000");
	        				
	        				//追加exists_sql的tableName信息
	        				exists_sql.append(","+tableName+" "+tableName.toLowerCase());
	        			}
	        			
	        		}
	        		
	        	}
	    		
	    		
	    		//判断existswhere_sql是否为空，为空则为第一个条件，在条件前加and
        		if(new String (existswhere_sql)==null || new String (existswhere_sql).equals("") && !tableName.equals("A01")){
        			if(m.get("opeartors").toString().equals("({c} is null or {c}={v})")){//为空
    					//existswhere_sql.append(" a01fujun."+colNames);
        				existswhere_sql.append(" and "+m.get("leftBracket"));
    					opeators = m.get("opeartors").toString().replace("{c}", tableName.toLowerCase()+"."+colNames);
    				}else{
    					existswhere_sql.append(" and "+m.get("leftBracket")+tableName.toLowerCase()+"."+colNames);
    				}
        			
        		}else if(!tableName.equals("A01")){
        			if(m.get("opeartors").toString().equals("({c} is null or {c}={v})")){//为空
    					//existswhere_sql.append(" a01fujun."+colNames);
    					opeators = m.get("opeartors").toString().replace("{c}", tableName.toLowerCase()+"."+colNames);
    				}else{
    					existswhere_sql.append(tableName.toLowerCase()+"."+colNames);
    				}
        			
        		}
        		
	    		
	    		//给具体查询条件赋查询值
	    		if(!colNames.toUpperCase().equals("AGE")){//如果查询年龄
	    			if(!"11".equals(operatorCodeValue.getSubCodeValue())){
	    				
	    				existswhere_sql.append(" "+opeators.replace("v", colValues).replace("{", "'").replace("}", "'")+" ");//运算符
	    				
	    			}else{
	    				int colValuesInt = 0;
	        			try {
	    					colValuesInt = Integer.parseInt(colValues);
	    				} catch (NumberFormatException e) {
	    					throw new AppException("【"+operatorCodeValue.getCodeColumnName()+"】运算符对应的运算只能为正负整数！");
	    				}
	        			String colValues11 = "";
	        			if(tableName.equals("A01")){
	        				colValues11 =" "+m.get("opeartors").toString().replace("v", colValuesInt+"").replace("{", "").replace("}", "")
	            					.replace("trunc", "to_char(trunc").replace(")", "),'yyyymmdd')");//运算符
	        			}else{
	        				colValues11 =" "+m.get("opeartors").toString().replace("v", colValuesInt+"").replace("{", "").replace("}", "")
	            					.replace("trunc", "to_char(trunc").replace(")", "),'yyyymmdd')");//运算符
	        			}
	        			
	        			
	        			if(DBUtil.getDBType() == DBType.MYSQL){
	        				colValues11 = colValues11.replace("sysdate", "now()");
	        			}
	        			existswhere_sql.append(colValues11);
	    			}
	    		}
	    		existswhere_sql.append(m.get("rightBracket"));			//拼接右括号（在页面设置情况下）
	    		
	    		
	    		//条件拼接的最后将existswhere_sql完善括号起来
	    		if(list.size()==count){
	    			existswhere_sql.insert(4,"( "); 
	    			
	    			existswhere_sql.append(") ) )");
	    		}
		    	existswhere_sql.append(m.get("logicSymbols"));		//拼接条件连接关系
		    	
	    	}						//for尾部
	    	
	    	
	    	
    	}
    	and_sql = comsql.toString();
    	
    	//如果有exists查询，则拼接sql
    	if(new String (exists_sql)!=null && !new String (exists_sql).equals("")){
    		where_sql = where_sql.append(exists_sql).append(" where").append(join_sql).append(existswhere_sql);
    	}
    	
    	
    	if(1==2 && errNum==1 && DBUtil.getDBType() == DBType.ORACLE){//如果是常用查询条件并且数据库是oracle的则如下拼装sql
    		and_sql = (and_sql+where_sql).replace(") and 1=1", ")) and 1=1"); 
    	}else{
    		and_sql = and_sql+where_sql;
    	}
    	//组装sql
    	sqlbuf.append(and_sql);
    	
    	sql=sqlbuf.toString();
    	CommonQueryBS.systemOut("---->"+sql);
    	String msg= checkSql(sql);
    	if(msg!=null)
    		throw new AppException(msg);  
    	return sql;
    }
    
    
    
	/**
	 * @$comment 检查生成的sql是否正确
	 * (校验sql中括号及and符号是否有缺失或者多余的情况)
	 * @param String 传入拼装好的sql语句
	 * @return String
	 * @throws AppException 
	 * @throws 
	 * @author lixy
	 */
    private static String checkSql(String sql){
    	String errorMsg=null;
    	if(sql.lastIndexOf("and")==(sql.length()-4)||sql.lastIndexOf("or")==(sql.length()-3))
    		return errorMsg="查询条件最后不能添加条件链接符，请检查！"; 
    	if(sql.indexOf("and 1=1")!=(sql.length()-8))
    		return errorMsg="条件与条件之间必须用关系链接符相连，请检查！";        	
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
        	return errorMsg="缺失右括号，请根据查询条件一览核查！";
        }else if(leftBracketCnt<rightBracketCnt){
        	return errorMsg="缺失左括号，请根据查询条件一览核查！";
        }
    	return errorMsg;
    }
    
    /**
	 * @$comment 根据网格数据生成sql显示数据  
	 * 
	 * @param list 网格数据
	 * @return String
	 * @throws AppException 
	 * @throws 
	 * @author lixy
	 */
    public  String  createSQLView(List<HashMap<String, Object>> list) throws AppException{
    	String  sqlView=null;
    	//组装sql中文解释信息，便于操作人员理解
    	for(HashMap<String, Object> m:list){
    		sqlView=sqlView+m.get("leftBracket")+m.get("colNames")+m.get("opeartors")+m.get("colValuesView")+m.get("rightBracket")+m.get("logicSymbols");
    	}
    	
    	return sqlView;
    }
    
    
    //修改a01时，更新修改人和修改时间
    public static  void  setA01(String a0000) throws AppException{
    	
    	HBSession sess = HBUtil.getHBSession();
    	A01 a01 = (A01)sess.get(A01.class, a0000);
    	
    	UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		Date date = new Date();//获得系统时间.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf.format(date);//将时间格式转换成符合Timestamp要求的格式.
        Timestamp dates =Timestamp.valueOf(nowTime);//把时间转换
        a01.setXgr(user.getId());			//修改人(存id)
        a01.setXgsj(dates.getTime());		    //修改时间
    	
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
    	cq.setQueryname(queryName);//查询条件名称
    	cq.setQuerysql(querySql);//查询sql
    	cq.setQuerydescription(queryDes);//排序sql
    	cq.setLoginname(LoginName);//用户登录名
    	cq.setGridstring(data);//json保存grid中数据
    	cq.setQuerycond(data2);//json保存grid中数据
    	sess.saveOrUpdate(cq);
    	return cq;
    }
    
}
