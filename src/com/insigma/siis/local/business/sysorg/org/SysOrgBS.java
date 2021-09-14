package com.insigma.siis.local.business.sysorg.org;

import java.beans.IntrospectionException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Query;
import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.BSSupport;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.sysorg.org.dto.B01DTO;
import com.insigma.siis.local.business.sysrule.SysRuleBS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.util.ExeclUtil;
import com.picCut.servlet.SaveLrmFile;

public class SysOrgBS extends BSSupport{
	private static Logger log = Logger.getLogger(SaveLrmFile.class);
	//根据主键查询
	public static B01 LoadB01(String id){
		try{
			return (B01) HBUtil.getHBSession().get(B01.class, id);
		}catch(Exception e){
			return null;
		}
	}

	public static List<B01DTO> selectB01s(){
		return selectB01s(null);
	}
	//树加载
	public static List<B01DTO> selectB01s(String nsjg){
		CurrentUser user = SysUtil.getCacheCurrentUser();
		//内设机构不查询标示
		String nsjgsql = (nsjg!=null && nsjg.equals("0")) ? "where t.b0194<>'2' " : " ";
		List<B01DTO> b01dtolist = new ArrayList();
		String sql="select t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,b.type userrule,t.b0194 from b01 t  left   join (select * from competence_userdept where  userid='"+user.getId()+"') b  on t.b0111=b.b0111 "+nsjgsql+" order by t.sortid";
		if(SysRuleBS.issupermanager()){
			sql="select t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,'1' userrule,t.b0194 from b01 t";
		}
		/*List list = HBUtil.getHBSession().createSQLQuery(sql).list();
		Object[] object=null;
		for(int i=0;i<list.size();i++){
			B01DTO b01dto=new B01DTO();
			object= (Object[]) list.get(i);
			String a = (String)object[0];
			b01dto.setB0111((String)object[0]);
			b01dto.setB0121((String)object[1]);
			b01dto.setB0101((String)object[2]);
			b01dto.setB0117((String)object[3]);
			b01dto.setSortid(((BigDecimal) object[4]).longValue());
			if((String)object[5]==null){
				b01dto.setType("2");
			}else{
				b01dto.setType((String)object[5]);
			}
			b01dto.setB0194((String)object[6]);
			b01dtolist.add(b01dto);
		}*/
		ResultSet rs = null;
		try {
			  rs = HBUtil.getHBSession().connection().prepareStatement(sql).executeQuery();
			  while(rs.next()){
				  	B01DTO b01dto=new B01DTO();
					b01dto.setB0111(rs.getString(1));
					b01dto.setB0121(rs.getString(2));
					b01dto.setB0101(rs.getString(3));
					b01dto.setB0117(rs.getString(4));
					b01dto.setSortid(Long.valueOf(rs.getObject(5)!=null?rs.getObject(5).toString():"0"));
					if(StringUtil.isEmpty(rs.getString(6))){
						b01dto.setType("2");
					}else{
						b01dto.setType(rs.getString(6));
					}
					b01dto.setB0194(rs.getString(7));
					b01dtolist.add(b01dto);
			  }
		} catch (SQLException e) {
			e.printStackTrace();
			//测试，暂不抛出异常
		}
		
		
		return b01dtolist;
	}
	
	
	//树加载、人员信息查询页面用
	public static List<B01DTO> selectB01sByPeople(){
		CurrentUser user = SysUtil.getCacheCurrentUser();
		//内设机构不查询标示
		List<B01DTO> b01dtolist = new ArrayList();
		String sql="select t.b0111 from b01 t,competence_userdept b where t.b0111=b.b0111 and userid='"+user.getId()+"'";
		List<String> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		String sql2="select count(*) from b01";
		String str = HBUtil.getHBSession().createSQLQuery(sql2).list().get(0).toString();
		if(str.equals(String.valueOf(list.size()+1))){
			b01dtolist=selectB01s();
		}else{
			HashSet hashSet=new HashSet();
			for(int i=0;i<list.size();i++){
				if(!list.get(i).equals("001.001")&&list.get(i).length()>6){
					getHashSetTree(hashSet,list.get(i));
				}
			}
			hashSet.add("001.001");
			hashSet.add("-1");
			if(hashSet.size()>0){
			    Iterator ir=hashSet.iterator();
			    StringBuffer sb = new StringBuffer();
			    while(ir.hasNext()){
			    	sb.append("'").append(ir.next()).append("',");
			    }
			    String ids = sb.toString();
			    ids=ids.substring(0, ids.length()-1);
			    b01dtolist = selectB01sByPeople(ids,null);
			}else{
				return b01dtolist;
			}
		}
		return b01dtolist;
	}
	
	//树加载、人员信息查询页面用
	public static List<B01DTO> selectB01sByPeopleNsjg(){
		CurrentUser user = SysUtil.getCacheCurrentUser();
		//内设机构不查询标示
		List<B01DTO> b01dtolist = new ArrayList();
		String sql="select t.b0111 from b01 t,competence_userdept b where t.b0111=b.b0111 and userid='"+user.getId()+"' and t.b0194<>'2'";
		List<String> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		String sql2="select count(*) from b01";
		String str = HBUtil.getHBSession().createSQLQuery(sql2).list().get(0).toString();
		if(str.equals(String.valueOf(list.size()+1))){
			b01dtolist=selectB01s("0");
		}else{
			HashSet hashSet=new HashSet();
			for(int i=0;i<list.size();i++){
				if(!list.get(i).equals("001.001")&&list.get(i).length()>6){
					getHashSetTree(hashSet,list.get(i));
				}
			}
			hashSet.add("001.001");
			hashSet.add("-1");
			if(hashSet.size()>0){
			    Iterator ir=hashSet.iterator();
			    StringBuffer sb = new StringBuffer();
			    while(ir.hasNext()){
			    	sb.append("'").append(ir.next()).append("',");
			    }
			    String ids = sb.toString();
			    ids=ids.substring(0, ids.length()-1);
			    b01dtolist = selectB01sByPeople(ids,"0");
			}else{
				return b01dtolist;
			}
		}
		return b01dtolist;
	}
	
	public static List<B01DTO> selectB01sByPeople(String ids,String nsjg){
		CurrentUser user = SysUtil.getCacheCurrentUser();
		//内设机构不查询标示
		List<B01DTO> b01dtolist = new ArrayList();
		String[] a =ids.split(",");
		String sql="";
		String nsjgsql = (nsjg!=null && nsjg.equals("0")) ? " and t.b0194<>'2' " : " ";
		if(a.length>999){
			StringBuffer sqlString = new StringBuffer();
			for (int i = 0; i < a.length; i++) {  
                if (i == (a.length - 1)) {  
                    sqlString.append(a[i]); //SQL拼装，最后一条不加“,”。  
                }else if((i%999)==0 && i>0){  
                    sqlString.append(a[i]).append(") or t.B0111 in ("); //解决ORA-01795问题  
                }else{  
                    sqlString.append(a[i]).append(",");  
                }  
            } 
			sql="select t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,b.type userrule,t.b0194 from b01 t  left   join (select * from competence_userdept where  userid='"+user.getId()+"') b  on t.b0111=b.b0111 where t.B0111 in ("+sqlString+") "+nsjgsql+" order by t.sortid";
		}else{
			if(ids.length()>0){
				sql="select t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,b.type userrule,t.b0194 from b01 t  left   join (select * from competence_userdept where  userid='"+user.getId()+"') b  on t.b0111=b.b0111 where t.B0111 in ("+ids+")"+nsjgsql+" order by t.sortid";
			}
		}
		ResultSet rs = null;
		try {
			  rs = HBUtil.getHBSession().connection().prepareStatement(sql).executeQuery();
			  while(rs.next()){
				  	B01DTO b01dto=new B01DTO();
					b01dto.setB0111(rs.getString(1));
					b01dto.setB0121(rs.getString(2));
					b01dto.setB0101(rs.getString(3));
					b01dto.setB0117(rs.getString(4));
					b01dto.setSortid(Long.valueOf(rs.getObject(5)!=null?rs.getObject(5).toString():"0"));
					if(StringUtil.isEmpty(rs.getString(6))){
						b01dto.setType("2");
					}else{
						b01dto.setType(rs.getString(6));
					}
					b01dto.setB0194(rs.getString(7));
					b01dtolist.add(b01dto);
			  }
		} catch (SQLException e) {
			e.printStackTrace();
			//测试，暂不抛出异常
		}
		
		
		return b01dtolist;
	}
	
	public static HashSet getHashSetTree(HashSet hashSet,String str){
		if(str.length()>6&&!str.equals("001.001")){
			hashSet.add(str);
			getHashSetTree(hashSet,str.substring(0, str.length()-4));
		}
		return hashSet;
	}
	
	
	//查询B01表的行数
	public static List<String[]> selectB01SStartWith(String id){
		String sql="select b.B0111 from b01 b where b.b0111 like '"+id+"%'";
		List<String[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		return list;
	}
	
	/**
	 * 删除本级及下级机构
	 * @param id
	 * @return count 是否成功
	 * @throws SQLException
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static int delB01(String id) throws SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException{
		String userid=SysUtil.getCacheCurrentUser().getId();//获取用户权限id
		//根据权限列表删除机构a0195（统计关系所在单位）a0000（人员统一标识符）
		//a0201b（任职机构代码）
        String sql="delete from b01 where b0111 like '"+id+"%'";
		//String sql="delete from b01 where EXISTS (select b0111 from competence_userdept  where userid='"+userid+"'and b0111 like '"+id+"%' )  ";
		//String sqla02="update a02 t set t.a0201b='-1',t.a0201a='其他单位',t.a0201c='' where t.a0201b like '"+id+"%'";
		System.out.println(sql);
		
		//根据权限更新啊a0195统计关系所在单位，a0000人员统一标识符,a0201b任职机构代码  ,b0111机构主键
		//String sqla01="update a01 t set t.a0195='' where a0000 in (select a0000 from a02 where a02.a0201b like '"+id+"%')";
		String sqla01="update a01 t set t.a0195='"+id+"' where a0000 in "
				+ "(select a0000 from a02,competence_userdept c where a0201b like'"+id+"%'"
						+"and c.userid='"+userid+"' and a0201b=c.b0111)";
//		String sqla01="update a01 t set t.a0195='' where a0000 in "
//		+ "(select a0000 from a02,competence_userdept c where a0201b like'"+id+"%'"
//				+"and c.userid='"+userid+"' and a0201b=c.b0111)";
//		
		
		List list =selectB01SStartWith(id);
		B01 logb01 = new B01();
		//SysOrgBS.getTime();
		for(int i=0 ;i<list.size();i++){
			B01 b01 = LoadB01(list.get(i).toString());
		//TODO 人员修改日志
			try {
				new LogUtil().createLog("23", "B01", b01.getB0111(), b01.getB0101(), "机构删除", new Map2Temp().getLogInfo(b01, logb01));
				log.info("被删除机构id："+b01.getB0111()
						+"/被删除机构名称："+ b01.getB0101()
						+"/删除人id："+PrivilegeManager.getInstance().getCueLoginUser().getId()
						+"/删除人"+PrivilegeManager.getInstance().getCueLoginUser().getLoginname()
						+"/删除时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//删除权限,根据用户权限删除
	   // String sql3 = "delete from COMPETENCE_USERDEPT  where  b0111 like '"+id+"%' ";
		
		String sql3 = "delete from COMPETENCE_USERDEPT where b0111 like '"+id+"%' and userid='"+userid+"'";
		System.out.println(sql3);

		HBUtil.getHBSession().createSQLQuery(sql3).executeUpdate();
		//SysOrgBS.getTime();
		int counta01 =HBUtil.getHBSession().createSQLQuery(sqla01).executeUpdate();
		//int counta02 =HBUtil.getHBSession().createSQLQuery(sqla02).executeUpdate();
		int count =HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
		
		//删除机构校验的数据
		String sql_verify_error_list=" delete from verify_error_list where vel002 like '"+id+"%' ";
		HBUtil.getHBSession().createSQLQuery(sql_verify_error_list).executeUpdate();
		//SysOrgBS.getTime();
		return count;
	}
	
	//查询B01表的行数
	public static String selectB01Count(){
		String sql="select count(*) from b01";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
		return str;
	}
	
	//查询是否有下级节点  false没有 true有
	public static String hasChildren(String id){
		String sql="from B01 b where B0121='"+id+"' order by sortid";// -1其它现职人员
		List<B01> list = HBUtil.getHBSession().createQuery(sql).list();
		if(list!=null && list.size()>0){
			return "false";
		}else{
			return "true";
		}
	}
	
	//根据主键查询是否存在数据库中
	public static String selectCountById(String id) throws RadowException{
		String sql="select count(*) from b01 t where B0111='"+id+"'";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
		if(str.equals("0")){
			throw new RadowException("当前机构不存在数据库中!");
		}
		return str;
	}
	
		//编制统计功能
		//查询是否有下级机构数量
		//仅仅法人单位下的人员有编制
		public static String selectCountBySubIdBz_Counts(String sql) throws RadowException{
			String userId = SysUtil.getCacheCurrentUser().getId();
			sql=" select count(*) from b01 t "
					+ " where t.b0194='1' "//机构类型 1法人单位 2内设机构 3机构分组
					+ " and t.b0111 in (select cu.b0111 from COMPETENCE_USERDEPT cu "
					+ " where cu.userid='"+userId+"' "
					+ sql
					+ " and cu.type in ('0','1') "
					//+ " and cu.b0111=t.b0111"
					+ " ) ";
			String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
			return str;
		}
		
		//领导职数统计功能
		//查询是否有下级机构数量
		public static String selectCountBySubId_Counts(String sql) throws RadowException{
			String userId = SysUtil.getCacheCurrentUser().getId();
			sql=" select count(*) from b01 t "
					+ " where t.b0194 in ('1','2')"//机构类型 1法人单位 2内设机构 3机构分组
					+ " and t.b0111 in (select cu.b0111 from COMPETENCE_USERDEPT cu "
					+ " where cu.userid='"+userId+"' "
					+ sql
					+ " and cu.type in ('0','1') "
					//+ " and cu.b0111=t.b0111"
					+ " ) ";
			String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
			return str;
		}
	
	//编制统计功能
	//查询是否有下级机构数量
	//仅仅法人单位下的人员有编制
	public static String selectCountBySubIdBz(String id) throws RadowException{
		String userId = SysUtil.getCacheCurrentUser().getId();
		String sql=" select count(*) from b01 t "
				+ " where t.B0121='"+id+"'"//上级单位id
				+ " and t.b0194='1' "//机构类型 1法人单位 2内设机构 3机构分组
				+ " and t.b0111 in (select cu.b0111 from COMPETENCE_USERDEPT cu "
				+ " where cu.userid='"+userId+"' "
				+ " and cu.type in ('0','1') "
				//+ " and cu.b0111=t.b0111"
				+ " ) ";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
		return str;
	}
	
	//领导职数统计功能
	//查询是否有下级机构数量
	public static String selectCountBySubId(String id) throws RadowException{
		String userId = SysUtil.getCacheCurrentUser().getId();
		String sql=" select count(*) from b01 t "
				+ " where t.B0121='"+id+"'"//上级单位id
				+ " and t.b0194 in ('1','2')"//机构类型 1法人单位 2内设机构 3机构分组
				+ " and t.b0111 in (select cu.b0111 from COMPETENCE_USERDEPT cu "
				+ " where cu.userid='"+userId+"' "
				+ " and cu.type in ('0','1') "
				//+ " and cu.b0111=t.b0111"
				+ " ) ";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
		return str;
	}
	
	//编制统计功能
	//查询下级机构
	public static List<B01> selectListBySubIdBz(String id) throws RadowException{
		String userId = SysUtil.getCacheCurrentUser().getId();
		String sql=" from B01 "
				+ " where B0121='"+id+"' "//上级单位编码  
				+ " and B0194 ='1' "//1-法人单位 （下级机构编制人员对比，仅仅统计法人单位，法人单位下的人员才拥有编制）
				+ " and b0111 in (select cu.b0111 from Competenceuserdept cu "
    						+ " where cu.userid='"+userId+"' "
    						+ " and cu.type in ('0','1') "
			        		//+ " and cu.b0111=b0111"
			        		+ " ) "
				+ " order by sortid ";//排序字段  
		List<B01> list = HBUtil.getHBSession().createQuery(sql).list();
		return list;
	}

	//领导职数统计功能
	//查询下级机构
	//法人单位与内设机构下的人员统计
	public static List<B01> selectListBySubId(String id) throws RadowException{
		String userId = SysUtil.getCacheCurrentUser().getId();
		String sql=" from B01  "
				+ " where B0121='"+id+"' "//上级单位编码  
				+ " and B0194 !='3' "//3 机构分组
				+ " and b0111 in (select cu.b0111 from Competenceuserdept cu "
        						+ " where cu.userid='"+userId+"' "
        						+ " and cu.type in ('0','1') "
				        		//+ " and cu.b0111=b0111"
				        		+ " ) "
				+ " order by sortid ";//排序字段  
		List<B01> list = HBUtil.getHBSession().createQuery(sql).list();
		return list;
	}
	
	//查询下级机构导出EXECL用
	public static List<B01> selectListByExecl(String id) throws RadowException{
		/**
		 * update by zoul 2017年5月17日
		 * ['1','法人单位'],['2','内设机构'],['3','机构分组']
		 * 统计本机及下级内设机构
		 */
		String sql="from B01 where (B0121='"+id+"' and b0194 in ('2')) or B0111='"+id+"' order by sortid";
		List<B01> list = HBUtil.getHBSession().createQuery(sql).list();
		return list;
	}
	
	/**
	 * 获取CodeValue对象
	 * @param
	 * @param
	 * @return
	 */
	public static CodeValue getCodeValue(String codeType ,String codeValue,String subCodeValue ){
		CodeValue codeValueObj = null;
		String hql = "from CodeValue where codeType=:codeType and codeValue=:codeValue";
		if(!StringUtil.isEmpty(subCodeValue)){
			hql = hql+" and subCodeValue = :subCodeValue";
		}
		HBSession sess = HBUtil.getHBSession();
		Query query =  sess.createQuery(hql).setString("codeType", codeType==null?"":codeType.toUpperCase()).setString("codeValue", codeValue==null?"":codeValue.toUpperCase());
		if(!StringUtil.isEmpty(subCodeValue)){
			query = query.setString("subCodeValue", subCodeValue);
		}
		List<CodeValue> list = query.list();
		if(list!=null && !list.isEmpty()){
			codeValueObj = list.get(0);
		}
		return codeValueObj;
	}
	
	//页面初始化查询
	public static Object[] queryInit(){
		Object[] area = (Object[]) HBUtil.getHBSession().createSQLQuery("select b0101,b0111,b0194 from B01 where b0111='-1'").uniqueResult();
		return area;
	}
	
	/**
	 * 获取CodeValue对象
	 * @param
	 * @param
	 * @return
	 */
	public static Map getCodeValue(String codeType){
		CodeValue codeValueObj = null;
		String hql = "from CodeValue where codeType=:codeType ";
		HBSession sess = HBUtil.getHBSession();
		Query query =  sess.createQuery(hql).setString("codeType", codeType==null?"":codeType.toUpperCase());
		List<CodeValue> list = query.list();
		Map map = null;
		if(list!=null && !list.isEmpty()){
			map = new HashMap();
			CodeValue codeValue = new CodeValue();
			for(int i=0;i<list.size();i++){
				codeValue =list.get(i);
				map.put(codeValue.getCodeValue(), codeValue.getCodeName());
			}
		}
		return map;
	}
	public static byte[] wirteExeclLowOrgLeader(InputStream is,List<B01> list,int counts) throws RadowException, AppException{
		try {
			HSSFWorkbook work = new HSSFWorkbook(is);
			HSSFSheet sheet = work.getSheetAt(0);
			if(counts==0){//没有下级法人机构与内设机构
	            sheet.shiftRows(4, sheet.getLastRowNum(), -1);//将第6行向上移动一行
	            sheet.shiftRows(4, sheet.getLastRowNum(), -1);//将第6行向上移动一行
			}else if(counts==1){//存在一个下级机构
	            sheet.shiftRows(4, sheet.getLastRowNum(), -1);//将第6行向上移动一行
	            B01 b01 =list.get(0);
	            HSSFRow row = sheet.getRow(3);//获取第四行
				HSSFCell[] cells = new HSSFCell[15];//15个格子
				for(int k = 0;k<15;k++){
					cells[k] = row.getCell(k);//第四行的15个格子//获取此sheet对象第4行的15个cell对象 
				}
				//机构名称
				cells[0].setCellValue(b01.getB0101()==null?"":b01.getB0101());//每一个格子写入数据
				
				String temp="";
				temp=(String)getCodeValue("ZB03").get(b01.getB0127());//机构级别 ZB03   等
				cells[1].setCellValue(temp==null?"":temp);
				
				temp=HBUtil.getValueFromTab("code_name", "code_value", " code_type='B0194' and code_value='"+b01.getB0194()+"' "); 
				cells[2].setCellValue(temp==null?"":temp);
				
				
				
				long b0183 = b01.getB0183()==null?0:b01.getB0183();
				cells[7].setCellValue(b0183);//正职领导职数
				sheet.getRow(4).getCell(7).setCellValue(b0183);
			
				int rightLeaderCount = Integer.valueOf(CreateSysOrgBS.selectRightLeaderCount(b01.getB0111()));
				cells[8].setCellValue(rightLeaderCount);//实配人数
				sheet.getRow(4).getCell(8).setCellValue(rightLeaderCount);
				if(rightLeaderCount-b0183>0){
					cells[9].setCellValue(rightLeaderCount-b0183);
					sheet.getRow(4).getCell(9).setCellValue(rightLeaderCount-b0183);//超配=实配-正职领导职数
					cells[10].setCellValue("0");
					sheet.getRow(4).getCell(10).setCellValue("0");
				}else{
					cells[9].setCellValue("0");
					sheet.getRow(4).getCell(9).setCellValue("0");
					cells[10].setCellValue(b0183-rightLeaderCount);
					sheet.getRow(4).getCell(10).setCellValue(b0183-rightLeaderCount);//缺配=正职领导职数-实配
				}
				
				long b0185 = b01.getB0185()==null?0:b01.getB0185();
				cells[11].setCellValue(b0185);//副职领导职数
				sheet.getRow(4).getCell(11).setCellValue(b0185);
				
				int viceLeaderCount = Integer.valueOf(CreateSysOrgBS.selectViceLeaderCount(b01.getB0111()));
				cells[12].setCellValue(viceLeaderCount);//实配人数
				sheet.getRow(4).getCell(12).setCellValue(viceLeaderCount);
				if(viceLeaderCount-b0185>0){
					cells[13].setCellValue(viceLeaderCount-b0185);
					sheet.getRow(4).getCell(13).setCellValue(viceLeaderCount-b0185);//超配=实配-正职领导职数
					cells[14].setCellValue("0");
					sheet.getRow(4).getCell(14).setCellValue("0");
				}else{
					cells[13].setCellValue("0");
					sheet.getRow(4).getCell(13).setCellValue("0");
					cells[14].setCellValue(b0185-viceLeaderCount);
					sheet.getRow(4).getCell(14).setCellValue(b0185-viceLeaderCount);//缺配=正职领导职数-实配
				}
			
				//3
				cells[3].setCellValue(b0183+b0185);//正职领导职数+副职领导职数
				sheet.getRow(4).getCell(3).setCellValue(b0183+b0185);
				//4
				cells[4].setCellValue(viceLeaderCount+rightLeaderCount);//副职领导实配人数+正职领导实配人数
				sheet.getRow(4).getCell(4).setCellValue(viceLeaderCount+rightLeaderCount);
				int temp9 = 0;
				int temp13 = 0;
				int temp10 = 0;
				int temp14 = 0;
				try {
					temp9 = Integer.valueOf(cells[9].getStringCellValue());
				} catch (Exception e) {
					temp9 = 0;
				}
				try {
					temp13 = Integer.valueOf(cells[13].getStringCellValue());
				} catch (Exception e) {
					temp13 = 0;
				}
				try {
					temp10 = Integer.valueOf(cells[10].getStringCellValue());
				} catch (Exception e) {
					temp10 = 0;
				}
				try {
					temp10 = Integer.valueOf(cells[10].getStringCellValue());
				} catch (Exception e) {
					temp10 = 0;
				}
				//5
				cells[5].setCellValue(temp9+temp13);//超配
				sheet.getRow(4).getCell(5).setCellValue(temp9+temp13);
				//6
				cells[6].setCellValue(temp10+temp14);//超配
				sheet.getRow(4).getCell(6).setCellValue(temp10+temp14);
				/*//5
				cells[5].setCellValue(Integer.valueOf(cells[9].getStringCellValue())+Integer.valueOf(cells[13].getStringCellValue()));//超配
				sheet.getRow(4).getCell(5).setCellValue(Integer.valueOf(cells[9].getStringCellValue())+Integer.valueOf(cells[13].getStringCellValue()));
				//6
				cells[6].setCellValue(Integer.valueOf(cells[10].getStringCellValue())+Integer.valueOf(cells[14].getStringCellValue()));//超配
				sheet.getRow(4).getCell(6).setCellValue(Integer.valueOf(cells[10].getStringCellValue())+Integer.valueOf(cells[14].getStringCellValue()));*/
			}else{
				int c=0;//正职领导职数-总计
				int d=0;//实配人数-总计
				int e=0;//超配=实配-正职领导职数 总计 
				int f=0;//缺配=正职领导职数-实配 总计
				int g=0;//副职领导职数-总计
				int h=0;//实配人数-总计
				int ii=0;//超配=实配-正职领导职数 总计
				int j=0;//缺配=正职领导职数-实配 总计
//				int kk=0;//正职领导职数+副职领导职数 总计
//				int l=0;//副职领导实配人数+正职领导实配人数 总计
//				int m=0;//超配 总计
//				int n=0;//缺配
				
				//HSSFWorkbook hk = new HSSFWorkbook();
				HSSFCellStyle newstyle = work.createCellStyle();
				for(int i=0;i<counts-2;i++){//根据下级法人单位与内设机构数量，插入行数
					int insertRowNum =3;//插入行
					sheet.shiftRows(insertRowNum+i, sheet.getLastRowNum(), 1,true,false);//最后3行下移一行
					HSSFRow fromrow = sheet.getRow(4+i);//复制样式的单元格//倒数第三行
					HSSFRow torow = sheet.getRow(insertRowNum+i);//复制到//倒数第四行
					ExeclUtil.copyRow(newstyle,work,fromrow,torow,true);//复制单元格样式//复制第4+i行的样式到3+i行
				}
				B01 b01 = new B01();
				for(int i=0;i<list.size();i++){
					b01=list.get(i);
		            HSSFRow row = sheet.getRow(3+i);
					HSSFCell[] cells = new HSSFCell[18];
					for(int k = 0;k<18;k++){
						cells[k] = row.getCell(k);
					}
					//机构名称
					cells[0].setCellValue(b01.getB0101()==null?"":b01.getB0101());
					
					String temp="";
					temp=(String)getCodeValue("ZB03").get(b01.getB0127());//机构级别 ZB03   等
					cells[1].setCellValue(temp==null?"":temp);
					
					temp=HBUtil.getValueFromTab("code_name", "code_value", " code_type='B0194' and code_value='"+b01.getB0194()+"' ");
					cells[2].setCellValue(temp==null?"":temp);
					
					long b0183 = b01.getB0183()==null?0:b01.getB0183();
					cells[7].setCellValue(b0183);//正职领导职数
					c=(int) (c+b0183);//正职领导职数-总计
					//实配人数
					int rightLeaderCount = Integer.valueOf(CreateSysOrgBS.selectRightLeaderCount(b01.getB0111()));
					d=d+rightLeaderCount;//实配人数-总计
					cells[8].setCellValue(rightLeaderCount);
					if(rightLeaderCount-b0183>0){
						cells[9].setCellValue(rightLeaderCount-b0183);//超配=实配-正职领导职数
						e=(int) (e+(rightLeaderCount-b0183));//超配=实配-正职领导职数 总计 
					}else{
						if(rightLeaderCount-b0183!=0){
							cells[10].setCellValue(b0183-rightLeaderCount);//缺配=正职领导职数-实配
							f=(int) (f+(b0183-rightLeaderCount));//缺配=正职领导职数-实配 总计
						}
					}
					
					long b0185 = b01.getB0185()==null?0:b01.getB0185();
					cells[11].setCellValue(b0185);//副职领导职数
					g=(int) (g+b0185);//副职领导职数-总计
					//副职领导
					int viceLeaderCount = Integer.valueOf(CreateSysOrgBS.selectViceLeaderCount(b01.getB0111()));//实配人数
					h=h+viceLeaderCount;//实配人数-总计
					cells[12].setCellValue(viceLeaderCount);
					if(viceLeaderCount-b0185>0){
						cells[13].setCellValue(viceLeaderCount-b0185);//超配=实配-正职领导职数
						ii=(int) (ii+(viceLeaderCount-b0185));//超配=实配-正职领导职数 总计
					}else{
						if(viceLeaderCount-b0185!=0){
							cells[14].setCellValue(b0185-viceLeaderCount);//缺配=正职领导职数-实配
							j=(int) (j+(b0185-viceLeaderCount));//缺配=正职领导职数-实配 总计
						}
					}
					
					//3
					cells[3].setCellValue( ((b0183+b0185)==0)?"":((b0183+b0185)+"") );//正职领导职数+副职领导职数
					//4
					cells[4].setCellValue( ((viceLeaderCount+rightLeaderCount)==0)?"":((viceLeaderCount+rightLeaderCount)+"") );//副职领导实配人数+正职领导实配人数
					//5
					long chaopei=0;
					long qupei=0;
					//正职
					if(rightLeaderCount-b0183>0){//超配
						chaopei=chaopei+(rightLeaderCount-b0183);
					}else{//缺配
						qupei=chaopei+(b0183-rightLeaderCount);
					}
					//副职
					if(viceLeaderCount-b0185>0){
						chaopei=chaopei+(viceLeaderCount-b0185);
					}else{
						qupei=qupei+(b0185-viceLeaderCount);
					}
					cells[5].setCellValue( (chaopei==0)?"":(chaopei+"") );//超配
					//6
					cells[6].setCellValue( (qupei==0)?"":(qupei+"") );//缺配
					
				}
				sheet.getRow(counts+3).getCell(7).setCellValue(c);
				sheet.getRow(counts+3).getCell(8).setCellValue(d);
				sheet.getRow(counts+3).getCell(9).setCellValue(e);
				sheet.getRow(counts+3).getCell(10).setCellValue(f);
				sheet.getRow(counts+3).getCell(11).setCellValue(g);
				sheet.getRow(counts+3).getCell(12).setCellValue(h);
				sheet.getRow(counts+3).getCell(13).setCellValue(ii);
				sheet.getRow(counts+3).getCell(14).setCellValue(j);
				
				sheet.getRow(counts+3).getCell(3).setCellValue( (c+g==0)?"":((c+g)+"") );//kk
				sheet.getRow(counts+3).getCell(4).setCellValue( (d+h)==0?"":((d+h)+"") );
				sheet.getRow(counts+3).getCell(5).setCellValue( (e+ii)==0?"":((e+ii)+"") );//o
				sheet.getRow(counts+3).getCell(6).setCellValue( (f+j)==0?"":((f+j)+"") );
			}
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			work.write(bos);
			bos.close();
			is.close();
			byte[] bytes = null;
			return bytes = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		} catch (RadowException e) {
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
	}
	
	//编制人员对比
	public static byte[] wirteExeclLowOrgPeople(InputStream is,int counts,List<B01> list){
		try {
			HSSFWorkbook work = new HSSFWorkbook(is);
			HSSFSheet sheet = work.getSheetAt(0);
			if(counts==0){
	            sheet.shiftRows(4, sheet.getLastRowNum(), -1);
	            sheet.shiftRows(4, sheet.getLastRowNum(), -1);
			}else if(counts==1){
	            sheet.shiftRows(4, sheet.getLastRowNum(), -1);
	            B01 b01 =list.get(0);
	            HSSFRow row = sheet.getRow(3);
				HSSFCell[] cells = new HSSFCell[26];
				for(int k = 0;k<26;k++){
					cells[k] = row.getCell(k);
				}
				cells[0].setCellValue(b01.getB0101()==null?"":b01.getB0101());
				cells[1].setCellValue((String)getCodeValue("ZB03").get(b01.getB0127()==null?"":b01.getB0127()));
				
				long b0227 = b01.getB0227()==null?0:b01.getB0227();
				cells[2].setCellValue(b0227);
				//行政编制
				int civilServantCount = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"1"));
				cells[3].setCellValue(civilServantCount);
				if(civilServantCount-b0227>0){
					cells[4].setCellValue(civilServantCount-b0227);
					sheet.getRow(4).getCell(4).setCellValue(civilServantCount-b0227);
				}else{
					cells[5].setCellValue(b0227-civilServantCount);
					sheet.getRow(4).getCell(5).setCellValue(b0227-civilServantCount);
				}
				
//				long b0235 = b01.getB0235()==null?0:b01.getB0235();
//				cells[6].setCellValue(b0235);
				//政法专项编制数
				//int zfzxbzs = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"2"));
				//cells[7].setCellValue(zfzxbzs);
//				if(zfzxbzs-b0235>0){
//					cells[8].setCellValue(zfzxbzs-b0235);
//					sheet.getRow(4).getCell(8).setCellValue(zfzxbzs-b0235);
//				}else{
//					cells[9].setCellValue(b0235-zfzxbzs);
//					sheet.getRow(4).getCell(9).setCellValue(b0235-zfzxbzs);
//				}
				
				long b0232 = b01.getB0232()==null?0:b01.getB0232();
				cells[6].setCellValue(b0232);
				//事业编制(参公)
				int likeCivilServantCount = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"3"));
				cells[7].setCellValue(likeCivilServantCount);
				if(likeCivilServantCount-b0232>0){
					cells[8].setCellValue(likeCivilServantCount-b0232);
					sheet.getRow(4).getCell(8).setCellValue(likeCivilServantCount-b0232);
				}else{
					cells[9].setCellValue(b0232-likeCivilServantCount);
					sheet.getRow(4).getCell(9).setCellValue(b0232-likeCivilServantCount);
				}
				
				long b0233 = b01.getB0233()==null?0:b01.getB0233();
				cells[10].setCellValue(b0233);
				//事业编制(其他)
				int careerServantCount = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"4"));
				cells[11].setCellValue(careerServantCount);
				if(careerServantCount-b0233>0){
					cells[12].setCellValue(careerServantCount-b0233);
					sheet.getRow(4).getCell(12).setCellValue(careerServantCount-b0233);
				}else{
					cells[13].setCellValue(b0233-careerServantCount);
					sheet.getRow(4).getCell(13).setCellValue(b0233-careerServantCount);
				}
				
				
//				long b0236 = b01.getB0236()==null?0:b01.getB0236();
//				cells[14].setCellValue(b0236);
//				//工勤编制
//				int gqbzs = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"5"));
//				cells[15].setCellValue(gqbzs);
//				if(gqbzs-b0236>0){
//					cells[16].setCellValue(gqbzs-b0236);
//					sheet.getRow(4).getCell(16).setCellValue(gqbzs-b0236);
//				}else{
//					cells[17].setCellValue(b0236-gqbzs);
//					sheet.getRow(4).getCell(17).setCellValue(b0236-gqbzs);
//				}
//
//				long b0234 = b01.getB0234()==null?0:b01.getB0234();
//				cells[18].setCellValue(b0234);
//				//其他编制
//				int qtbzs = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"9"));
//				cells[19].setCellValue(qtbzs);
//				if(qtbzs-b0234>0){
//					cells[20].setCellValue(qtbzs-b0234);
//					sheet.getRow(4).getCell(20).setCellValue(qtbzs-b0234);
//				}else{
//					cells[21].setCellValue(b0234-qtbzs);
//					sheet.getRow(4).getCell(21).setCellValue(b0234-qtbzs);
//				}
//
//				sheet.getRow(4).getCell(2).setCellValue(b0227);
//				sheet.getRow(4).getCell(3).setCellValue(civilServantCount);
//
////				sheet.getRow(4).getCell(6).setCellValue(b0235);
////				sheet.getRow(4).getCell(7).setCellValue(zfzxbzs);
//
//				sheet.getRow(4).getCell(6).setCellValue(b0232);
//				sheet.getRow(4).getCell(7).setCellValue(likeCivilServantCount);
//
//				sheet.getRow(4).getCell(10).setCellValue(b0233);
//				sheet.getRow(4).getCell(11).setCellValue(careerServantCount);
//
//				sheet.getRow(4).getCell(14).setCellValue(b0236);
//				sheet.getRow(4).getCell(15).setCellValue(gqbzs);
//
//				sheet.getRow(4).getCell(19).setCellValue(b0234);
//				sheet.getRow(4).getCell(19).setCellValue(qtbzs);
//
			}else{
				int c=0,d=0,e=0,f=0,g=0,h=0,ii=0,j=0,kk=0,
				l=0,m=0,n=0,
				//o=0,p=0,q=0,r=0,
				s=0,t=0,u=0,v=0,w=0,x=0,y=0,z=0;
				
				HSSFCellStyle newstyle = work.createCellStyle();
				for(int i=0;i<counts-2;i++){
					int insertRowNum =3;//插入行
					sheet.shiftRows(insertRowNum+i, sheet.getLastRowNum(), 1,true,false);
					HSSFRow fromrow = sheet.getRow(6+i);//复制样式的单元格
					HSSFRow torow = sheet.getRow(insertRowNum+i);//复制到
					ExeclUtil.copyRow(newstyle,work,fromrow,torow,true);//复制单元格样式
				}
				B01 b01 = new B01();
				for(int i=0;i<list.size();i++){
					b01=list.get(i);
		            HSSFRow row = sheet.getRow(3+i);
					HSSFCell[] cells = new HSSFCell[26];
					for(int k = 0;k<26;k++){
						cells[k] = row.getCell(k);
					}
					String temp="";
					temp=b01.getB0101();
					cells[0].setCellValue((temp==null||"0".equals(temp))?"":temp);
					temp=(String)getCodeValue("ZB03").get(b01.getB0127()==null?"":b01.getB0127());
					cells[1].setCellValue((temp==null||"0".equals(temp))?"":temp);
					
					long b0227 = b01.getB0227()==null?0:b01.getB0227();
					cells[2].setCellValue((b0227==0)?"":(b0227+""));
					c=(int) (c+b0227);
					//行政编制数
					int civilServantCount = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"1"));
					d=d+civilServantCount;
					cells[3].setCellValue((civilServantCount==0)?"":(civilServantCount+""));
					if(civilServantCount-b0227>0){
						cells[4].setCellValue(civilServantCount-b0227);
						e=(int) (e+(civilServantCount-b0227));
					}else{
						if(civilServantCount-b0227!=0){
							cells[5].setCellValue(b0227-civilServantCount);
							f=(int) (f+(b0227-civilServantCount));
						}
					}
					
//					long b0235 = b01.getB0235()==null?0:b01.getB0235();
//					cells[6].setCellValue(b0235);
//					o = (int)(o+b0235);
					//政法专项编制数
//					int zfzxbzs = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"2"));
//					p = (int)(p+zfzxbzs);
//					cells[7].setCellValue(zfzxbzs);
//					if(zfzxbzs-b0235>0){
//						cells[8].setCellValue(zfzxbzs-b0235);
//						q = (int)(q+(zfzxbzs-b0235));
//					}else{
//						cells[9].setCellValue(b0235-zfzxbzs);
//						r = (int)(r+(b0235-zfzxbzs));
//					}
					
					long b0232 = b01.getB0232()==null?0:b01.getB0232();
					cells[6].setCellValue((b0232==0)?"":(b0232+""));
					g=(int) (g+b0232);
					//事业编制（参公）
					int likeCivilServantCount = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"3"));
					h=h+likeCivilServantCount;
					cells[7].setCellValue((likeCivilServantCount==0)?"":(likeCivilServantCount+""));
					if(likeCivilServantCount-b0232>0){
						cells[8].setCellValue(likeCivilServantCount-b0232);
						ii=(int) (ii+(likeCivilServantCount-b0232));
					}else{
						if(likeCivilServantCount-b0232!=0){
							cells[9].setCellValue(b0232-likeCivilServantCount);
							j=(int) (j+(b0232-likeCivilServantCount));
						}
					}
					
					long b0233 = b01.getB0233()==null?0:b01.getB0233();
					cells[10].setCellValue((b0233==0)?"":(b0233+""));
					kk=(int) (kk+b0233);
					//事业编制（其他）
					int careerServantCount = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"4"));
					l=l+careerServantCount;
					cells[11].setCellValue((careerServantCount==0)?"":(careerServantCount+""));
					if(careerServantCount-b0233>0){
						cells[12].setCellValue(careerServantCount-b0233);
						m=(int) (m+(careerServantCount-b0233));
					}else{
						if(careerServantCount-b0233!=0){
							cells[13].setCellValue(b0233-careerServantCount);
							n=(int) (n+(b0233-careerServantCount));
						}
					}
					
//					long b0236 = b01.getB0236()==null?0:b01.getB0236();
//					cells[14].setCellValue((b0236==0)?"":(b0236+""));
//					s=(int) (s+b0236);
//					//工勤编制
//					int qtsybzs = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"4"));
//					t=t+qtsybzs;
//					cells[15].setCellValue((qtsybzs==0)?"":(qtsybzs+""));
//					if(qtsybzs-b0236>0){
//						cells[16].setCellValue(qtsybzs-b0236);
//						u=(int) (u+(qtsybzs-b0236));
//					}else{
//						if(qtsybzs-b0236!=0){
//							cells[17].setCellValue(b0236-qtsybzs);
//							v=(int) (v+(b0236-qtsybzs));
//						}
//					}
//
//					long b0234 = b01.getB0234()==null?0:b01.getB0234();
//					cells[18].setCellValue((b0234==0)?"":(b0234+""));
//					w=(int) (w+b0234);
//					//其他编制
//					int qtbz = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"4"));
//					x=x+qtbz;
//					cells[19].setCellValue((qtbz==0)?"":(qtbz+""));
//					if(qtbz-b0234>0){
//						cells[20].setCellValue(qtbz-b0234);
//						y=(int) (y+(qtbz-b0234));
//					}else{
//						if(qtbz-b0234!=0){
//							cells[21].setCellValue(b0234-qtbz);
//							z=(int) (z+(b0234-qtbz));
//						}
//					}
//
					
					
					
				}
				
				sheet.getRow(counts+3).getCell(2).setCellValue((c==0)?"":(c+""));
				sheet.getRow(counts+3).getCell(3).setCellValue((d==0)?"":(d+""));
				sheet.getRow(counts+3).getCell(4).setCellValue((e==0)?"":(e+""));
				sheet.getRow(counts+3).getCell(5).setCellValue((f==0)?"":(f+""));
				
//				sheet.getRow(counts+3).getCell(6).setCellValue(o);
//				sheet.getRow(counts+3).getCell(7).setCellValue(p);
//				sheet.getRow(counts+3).getCell(8).setCellValue(q);
//				sheet.getRow(counts+3).getCell(9).setCellValue(r);
				
				sheet.getRow(counts+3).getCell(6).setCellValue((g==0)?"":(g+""));
				sheet.getRow(counts+3).getCell(7).setCellValue((h==0)?"":(h+""));
				sheet.getRow(counts+3).getCell(8).setCellValue((ii==0)?"":(ii+""));
				sheet.getRow(counts+3).getCell(9).setCellValue((j==0)?"":(j+""));
				sheet.getRow(counts+3).getCell(10).setCellValue((kk==0)?"":(kk+""));
				sheet.getRow(counts+3).getCell(11).setCellValue((l==0)?"":(l+""));
				sheet.getRow(counts+3).getCell(12).setCellValue((m==0)?"":(m+""));
				sheet.getRow(counts+3).getCell(13).setCellValue((n==0)?"":(n+""));
				
				sheet.getRow(counts+3).getCell(14).setCellValue((s==0)?"":(s+""));
				sheet.getRow(counts+3).getCell(15).setCellValue((t==0)?"":(t+""));
				sheet.getRow(counts+3).getCell(16).setCellValue((u==0)?"":(u+""));
				sheet.getRow(counts+3).getCell(17).setCellValue((v==0)?"":(v+""));
				sheet.getRow(counts+3).getCell(18).setCellValue((w==0)?"":(w+""));
				sheet.getRow(counts+3).getCell(19).setCellValue((x==0)?"":(x+""));
				sheet.getRow(counts+3).getCell(20).setCellValue((y==0)?"":(y+""));
				sheet.getRow(counts+3).getCell(21).setCellValue((z==0)?"":(z+""));
				
			}
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			work.write(bos);
			bos.close();
			is.close();
			byte[] bytes = null;
			return bytes = bos.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RadowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void getTime(){
        Calendar nowtime = new GregorianCalendar();  
        String strDateTime="["+String.format("%04d", nowtime.get(Calendar.YEAR))+"/"+  
                String.format("%02d", nowtime.get(Calendar.MONTH))+"/" +  
                String.format("%02d", nowtime.get(Calendar.DATE))+" " +  
                String.format("%02d", nowtime.get(Calendar.HOUR))+":" +  
                String.format("%02d", nowtime.get(Calendar.MINUTE))+":" +  
                String.format("%02d", nowtime.get(Calendar.SECOND))+"." +  
                String.format("%03d", nowtime.get(Calendar.MILLISECOND))+"]";  
        CommonQueryBS.systemOut("=========="+strDateTime); 
	}
	/**
	 * 机构删除前把选中的机构信息插入到CPB01表楼里面，机构回收用的代码
	 * @param id
	 */
	public static void selectOut(String id,CurrentUser user){
		//String sq1 = "insert into CPB01 select * from b01 where b0111 like  '"+id+"%'";
		//HBUtil.getHBSession().createSQLQuery(sq1).executeUpdate();
		//String sq2 = "insert into COMPETENCE_USERDEPTcpb01 select sys_guid(),"+user.getId()+",b0111,type from COMPETENCE_USERDEPT where b0111 like  '"+id+"%'";
		//HBUtil.getHBSession().createSQLQuery(sq2).executeUpdate();
	}
	/**
	 * 将需要维护的SQL写入到list中
	 * @param groupid 机构ID
	 * @return 是否维护完成
	 */
	public static boolean maintenanceB01(String groupid){
		List<String> list = new ArrayList<String>();
		list.add("update smt_user s set s.otherinfo='X001' where s.otherinfo='"+groupid+"'");//修改smt_user
		list.add("delete from statistics_age where b0111 like '"+groupid+"%'");//更新宏观分析库--lzy update
		list.add("delete from statistics_educationlevel where b0111 like '"+groupid+"%'");
		list.add("delete from statistics_highestpostlevel where b0111 like '"+groupid+"%'");
		list.add("delete from statistics_sex where b0111 like '"+groupid+"%'");
		list.add("update a01 set a0195 = '-1' where a0195 like '"+groupid+"%'");//维护a01中的统计关系所在单位
		boolean whetherSuccess = true;
		int count = list.size();
		int countTemp = 0;
		for(String sql:list){
			try {
				HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
				countTemp++;
			} catch (Exception e) {
				e.printStackTrace();
				whetherSuccess=false;
			}
		}
		if(whetherSuccess&&countTemp==count){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断该机构下是否有人员 现职人员
	 * @param groupid 机构ID
	 * @return 人员数量
	 */
	public static int whetherPersonOn1(String groupid) {
		int count_temp = 0;
		try {
			//String sql = "Select Count(1) From a02 t where t.a0201b like '"+groupid+"%' and a0255='1' ";//判断机构下是否有人员，增加该机构在在职状态
			String sql = "select count(1) from a02 b join a01 a on b.a0000=a.a0000 where a.a0163='1' "
					 + " and a.status!='4' "//点击人员新增时，没有点击保存，系统产生的垃圾数据
					 + " and b.a0281='true' "//输出标志
					+ " and b.a0201b like '"+groupid+"%' and b.a0255='1'";
			String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
			count_temp = Integer.parseInt(str);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return count_temp;
	}
	
	/**
	 * 判断该机构下是否有人员 非现职人员
	 * @param groupid 机构ID   a0163人员管理状态    a0281职务输出标识
	 * @return 人员数量
	 */
	public static int whetherPersonOn2(String groupid) {
		int count_temp = 0;
		try {
			//String sql = "SELECT count(a01.a0000) FROM A01 JOIN A02 ON A01.A0000 = A02.A0000 WHERE a01.a0163 IN ('2', '3') AND A02.A0201B LIKE '"+groupid+"%' AND EXISTS (SELECT a30.a0000 FROM a30 WHERE a30.a0000 = a01.a0000 AND a30.a3001 IN ('31', '32'))";
			String sql = "SELECT count(a01.a0000) "
					+ " FROM A01 JOIN A02 ON A01.A0000 = A02.A0000 "
					+ " WHERE a01.a0163 IN ('21', '22','23','29') "
					+ " and a02.a0281='true' "//输出标志
					+ " AND A02.A0201B LIKE '"+groupid+"%' ";
			String str2 = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
			count_temp = Integer.parseInt(str2);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return count_temp;
	}
	
	/**
	 * 判断该机构下是否有人员
	 * @param groupid 机构ID ，a0000主键,status状态 0完全删除 1正常 2历史库3离退人员4临时数据,
	 * b0201b任职机构代码   等      a0255   任职状态   等
	 * @return 人员数量
	 */
	public static int whetherPersonOn(String groupid) {
		int count_temp = 0;
		try {
			//String sql = "Select Count(1) From a02 t where t.a0201b like '"+groupid+"%' and a0255='1' ";//判断机构下是否有人员，增加该机构在在职状态
			String sql = "select count(1) from a02 b join a01 a on b.a0000=a.a0000 where a.status='1' and b.a0201b like '"+groupid+"%' and b.a0255='1'";
			String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
			sql = "SELECT count(a01.a0000) FROM A01 JOIN A02 ON A01.A0000 = A02.A0000 WHERE a01.status IN ('2', '3') AND A02.A0201B LIKE '"+groupid+"%' AND EXISTS (SELECT a30.a0000 FROM a30 WHERE a30.a0000 = a01.a0000 AND a30.a3001 IN ('31', '32'))";
			String str2 = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
			count_temp = Integer.parseInt(str)+Integer.parseInt(str2);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return count_temp;
	}
	/**
	 * 判断该机构是否有下级
	 * @param groupid 机构ID
	 * @return 人员数量
	 */
	public static int whetherHasChile(String groupid) {
		int count_temp = 0;
		try {
			//String sql = "Select Count(1) From a02 t where t.a0201b like '"+groupid+"%' and a0255='1' ";//判断机构下是否有人员，增加该机构在在职状态
			String sql = "select count(1) from b01 where b0121=:groupid";
			String str = HBUtil.getHBSession().createSQLQuery(sql).setString("groupid", groupid).uniqueResult().toString();
			count_temp=Integer.parseInt(str);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return count_temp;
	}
	
	
	
} 