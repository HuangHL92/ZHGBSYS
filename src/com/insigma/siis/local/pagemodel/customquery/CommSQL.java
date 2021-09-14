package com.insigma.siis.local.pagemodel.customquery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.helperUtil.DateUtil;

import net.sf.json.JSONObject;

public class CommSQL {
	public static Map<String,List<Object[]>> A01_CONFIG_LIST = new HashMap<String, List<Object[]>>();
	public static Map<String,List<Object[]>> A01_SORTCONFIG_LIST = new HashMap<String, List<Object[]>>();
	public static int MAXROW=50000;
	
	
	
	@SuppressWarnings("unchecked")
	public static  List<Object[]> getHIS_CONFIG_INIT(String userid){
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			HBSession sess = HBUtil.getHBSession();
			
				list = sess.createSQLQuery("select t.dicid,t.code,t.name,t.gridwidth,t.aboutcode,t.formtype,t.tdesc,t.renderer,t.orderid,t.align,t.sortable "
						+ " from his_config t where t.isvali='false' and userid='"+userid+"' order by t.orderid").list();
				
				if(list==null||list.size()==0){
					HBUtil.executeUpdate("insert into his_config select t.*,'"+userid+"' from HIS_CONFIG_INIT t");
					sess.flush();
					list = sess.createSQLQuery("select t.dicid,t.code,t.name,t.gridwidth,t.aboutcode,t.formtype,t.tdesc,t.renderer,t.orderid,t.align,t.sortable  "
							+ " from his_config t where t.isvali='false' and userid='"+userid+"' order by t.orderid").list();
				}else{
					list = sess.createSQLQuery("select t.dicid,t.code,t.name,t.gridwidth,t.aboutcode,t.formtype,t.tdesc,t.renderer,t.orderid,t.align,t.sortable "
							+ " from his_config t where t.isvali='false' and userid='"+userid+"' order by t.orderid").list();
				}
				
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Object[]> getA01_config(String userid){
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			HBSession sess = HBUtil.getHBSession();
			list = sess.createSQLQuery("select t.dicid,t.code,t.name,t.gridwidth,t.aboutcode,t.formtype,t.tdesc,t.renderer,t.orderid,t.isvali,t.sortable,t.excelwidth,t.align "
					+ " from a01_config t where userid='"+userid+"' and t.dicid!='a0000' order by t.orderid").list();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//查询a01的列表自定义排序 
	public static List<Object[]> getA01_sort_config(String userid){
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			HBSession sess = HBUtil.getHBSession();
			list = sess.createSQLQuery("select t.dicid,t.code,t.name,t.orderType,t.tdesc,t.orderid,t.isvali "
					+ " from a01_sortConfig t where userid='"+userid+"' order by t.orderid").list();
			
			//如果当前用户是新用户，a01_config表无记录，则新增初始化记录
			if(list==null||list.size()==0){
				HBUtil.executeUpdate("insert into A01_SORTCONFIG (DICID,CODE,NAME,TDESC,ORDERID,ISVALI,USERID,ORDERTYPE) values('torgid','TORGID','任职机构','任职机构',1,'false','"+ userid +"','false')");
				
				HBUtil.executeUpdate("insert into A01_SORTCONFIG (DICID,CODE,NAME,TDESC,ORDERID,ISVALI,USERID,ORDERTYPE) values('torder','TORDER','集体内排序','集体内排序',3,'true','"+ userid +"','true')");
				
				sess.flush();
				list = sess.createSQLQuery("select t.dicid,t.code,t.name,t.orderType,t.tdesc,t.orderid,t.isvali "
						+ " from a01_sortConfig t where userid='"+userid+"' order by t.orderid").list();
				
				A01_SORTCONFIG_LIST.put(userid, list);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public static void initA01_config(String userid){
		try {
			List<Object[]> list = A01_CONFIG_LIST.get(userid);
			HBSession sess = HBUtil.getHBSession();
			if(list==null||list.size()==0){
				list = sess.createSQLQuery("select t.dicid,t.code,t.name,t.gridwidth,t.aboutcode,t.formtype,t.tdesc,t.renderer,t.orderid,t.align,t.sortable "
						+ " from a01_config t where t.isvali='true' and userid='"+userid+"' order by t.orderid").list();
				
				if(list==null||list.size()==0){
					HBUtil.executeUpdate("insert into a01_config select t.*,'"+userid+"' from A01_CONFIG_INIT t");
					sess.flush();
					list = sess.createSQLQuery("select t.dicid,t.code,t.name,t.gridwidth,t.aboutcode,t.formtype,t.tdesc,t.renderer,t.orderid,t.align,t.sortable  "
							+ " from a01_config t where t.isvali='true' and userid='"+userid+"' order by t.orderid").list();
					A01_CONFIG_LIST.put(userid, list);
				}else{
					A01_CONFIG_LIST.put(userid, list);
				}
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getComSQL(){
		return  "select  a01.a0000 from A01 a01 ";
		//return "select  a01.a0000 from A01 a01 ";
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static String getComFields(String userid,String sid){
		
		StringBuilder sb = new StringBuilder();
		for(Object[] o : A01_CONFIG_LIST.get(userid)){
			if("a0101".equals(o[0])) {
				//两个字的名字要加空格和三个字的对齐
				//两个半角空格对不齐一个汉字，所以这里加了一个全角空格
				sb.append("(case  when length(a0101)=2 then (Substr(a0101,0,1))||'　'||(Substr(a0101,2,2))else a0101 end) as "+o[0].toString()+",");
			}else if("a0134".equals(o[0].toString())
					||"a0107".equals(o[0].toString())
					||"a0288".equals(o[0].toString())
					||"a0192c".equals(o[0].toString()) 
					||"a0192f".equals(o[0].toString())
					||"a2949".equals(o[0].toString())){
				sb.append("decode("+o[1].toString()+",'','',substr("+o[1].toString()+",1,4)||'.'||substr("+o[1].toString()+",5,2) ) as "+o[0].toString()+",");
			}else {
				sb.append(o[1].toString()+" as "+o[0].toString()+",");
			}
		}
		sb.deleteCharAt(sb.length()-1);
		
		//sb.append(",(select sort from A01SEARCHTEMP tmp where sessionid = '"+sid+"' and tmp.a0000 = a01.a0000) as sort ");
		return  sb.toString();
		//return "select  a01.a0000 from A01 a01 ";
	}
	
	
	
	
	/**
	 * 
	 * @return
	 */
	public static String getComSQL(String sid){
		return "select  a01.a0000,'"+sid+"' sessionid from A01 a01 ";
	}
	/**
	 * 干部名册查询
	 */
	public static String getGbmcSQL(String sid){
		return "  SELECT t.a0201b,t.a0201a," +
				" a01.A0000 AS a0000, '" +sid+"' sessionid ,"+
				" A0101 AS a0101,  " + //-- 姓名
				" A0192A AS a0192a, " + //-- 现工作单位及职务全称
				" A0192 AS a0192,   " + //-- 现工作单位及职务简称
				" ( SELECT CODE_NAME FROM code_value WHERE code_type = 'GB2261' AND code_value = a0104 ) AS a0104,    " + //-- 性别
				" ( SELECT CODE_NAME FROM code_value WHERE code_type = 'GB3304' AND code_value = A0117 ) AS a0117,  " + //-- 民族
				" A0111A AS A0111A, " + //-- 籍贯
				" QRZXL, " + //-- 最高全日制学历
				" QRZXLXX AS qrzxlxx,  " + //-- 院校系专业（最高全日制学历）
				" ZZXL AS zzxl, " + //-- 最高在职学历
				" ZZXLXX AS zzxlxx, " + //-- 院校系专业（最高在职学历）
				" Format_Date_Point(a0107) as a0107,   " + //-- 出生日期
				"  Format_Date_Point(a0144) as a0144,     " + //-- 入党时间
				"  Format_Date_Point(a0134) as a0134,   " + //-- 参加工作时间
				" Format_Date_Point(a0288) as a0288,     " + //-- 任现职务层次时间
				" Format_Date_Point(a0192C ) as a0192C,       " + //-- 任该职级时间
				" A0195 as a0195,  " + //-- 统计关系所在code
				" ( SELECT b0101 FROM b01 WHERE b0111 = a01.A0195 ) AS a0195A,   " + //-- 统计关系所在单位
				" ( case when ZGXW is  not null then ZGXW||chr(10) end  ) " +
//				" ||(case when ZZXW is  not null then ZZXW||chr(10) end ) " +
				" || A0196 as  comments   " + //-- 最高学位，专业技术
				"  FROM A01 a01 left join (select a0000,a0201b,a0201a from a02 where a0255='1' and a0281 = 'true'  ) t on t.a0000 = a01.a0000 "  ;
	}
	public static String getComSQL(String sid,boolean isgbk, String querydb){
		if(isgbk) {
				return "select   a01.a0000, a01.a1701 from A01 a01 ";//'"+sid+"' sessionid,
		}else{
			return "select  a01.a0000,'"+sid+"' sessionid from V_JS_A01 a01 ";
		}
	
	}
	/**
	 * 
	 * @return
	 */
	public static String getComSQLQuery(String userid, String sid){
		//return "select  "+getComFields(userid)+"  from A01 a01 ";
		//return "select  a01."+getComFields(userid,sid)+"  from A01 a01 ";
		
		return "select  a01."+getComFields(userid,sid)+"  from A01 a01 join (SELECT sort,a0000 from A01SEARCHTEMP where sessionid = '"+sid+"') tp on a01.a0000 = tp.a0000";
	}
	public static String getComSQLQuery(String userid, String sid, boolean isgbk, String querydb){
		//return "select  "+getComFields(userid)+"  from A01 a01 ";
		//return "select  a01."+getComFields(userid,sid)+"  from A01 a01 ";
		if(isgbk) {
			return "select  a01."+getComFields(userid,sid)+",'1' v_xt  from A01 a01 join (SELECT sort,a0000 from A01SEARCHTEMP where sessionid = '"+sid+"') tp on a01.a0000 = tp.a0000";
		} else {
			return "select  a01."+getComFields(userid,sid)+",a01.v_xt  from V_JS_A01 a01 join (SELECT sort,a0000 from A01SEARCHTEMP where sessionid = '"+sid+"') tp on a01.a0000 = tp.a0000 where a01.v_xt='"+querydb+"' ";
		}
		
	}
	/**
	 * 
	 * @return
	 */
	public static String getComSQLQuery2(String userid, String sid){
		//return "select  "+getComFields(userid)+"  from A01 a01 ";
		return "select  "+getComFields(userid,sid)+"  from A01 a01 ";
		
	}
	
	/**
	 * 包含本级
	 * oracle 用substr 不走索引
	 * mysql 用like 走索引
	 * @param str
	 * @param pos
	 * @param length
	 * @param value
	 * @return
	 */
	public static String subString(String str,Integer pos,Integer length, String value){
		if(DBUtil.getDBType()==DBType.ORACLE){
			return "substr("+str+", "+pos+", "+length+")='"+value+"'";
		}else{
			return str+" like "+"'"+value+"%'";//mysql少量数据走索引更快
		}
	}
	
	
	/**
	 * 不包含本级
	 * oracle 用substr 不走索引
	 * mysql 用like 走索引
	 * @param str
	 * @param pos
	 * @param length
	 * @param value
	 * @return
	 */
	public static String subString2(String str,Integer pos,Integer length, String value){
		if(DBUtil.getDBType()==DBType.ORACLE){
			return "substr("+str+", "+pos+", "+(length+1)+")='"+value+".'";
		}else{
			return str+" like "+"'"+value+".%'";//mysql少量数据走索引更快
		}
	}
	
	public static String getCondiQuerySQL(String userid, StringBuffer a01sb,StringBuffer a02sb, StringBuffer a02_a0201b_sb, StringBuffer cu_b0111_sb, StringBuffer orther_sb,String a0163, String qtxzry){
		
	  /*离退，当离退管理中选 离休、退休、退职，               显示：离退人员。     查询：离退人员。

		退出管理， 选择 【调出或转出】 下的选项时，            显示：调出人员。     查询：历史人员

			   选择【其它退出方式】中的 【离退休】         显示：离退人员。     查询：离退人员
		【因选举退出登记】【开除】【辞退】【辞去公职】【其它】 显示：其它人员。     查询：历史人员
						    【死亡】           显示：已去世。       查询：历史人员           */
		String xzry = "";
		String lsltry = "";
		if("1".equals(a0163)){
			xzry = " and a01.status = '1' ";
			lsltry = " and 1=2 ";
		}else if("2".equals(a0163)||"3".equals(a0163)||"4".equals(a0163)||"5".equals(a0163)){
			xzry = " and 1=2 ";
			lsltry = " and a01.a0163 != '1' ";//现在改为只有现职和非现职
		}else{
			xzry = " and a01.status = '1' ";
			lsltry = " AND (a01.status = '3' OR a01.status = '2') ";
		}
		
		String a02str = "";
		if("0".equals(qtxzry)){
			a02str = " and "+concat("a01.a0000", "''")+" in "+
		               "(select a02.a0000 "+
		                  "from A02 a02 "+
		                "where a02.A0201B in "+
		                       "(select cu.b0111 "+
		                          "from competence_userdept cu "+
		                         "where cu.userid = '"+userid+"') "+
		                   "and a02.a0255 = '1' "+
		                   a02_a0201b_sb +a02sb+
		                   ") ";
		}else{
			a02str = " and  (exists (select 1 from a02   where a02.a0000=a01.a0000) " +
					" and not exists " +
					" (select 1 from a02   where a02.a0000=a01.a0000 and (a02.a0201b !='-1' and a0255='1')) or "
					+ " not exists (select 1 from a02   where a02.a0000=a01.a0000 )    ) "; //职务不为其它单位且任职为在职的 不需要选出来
		}
		
		return getComSQL()+
         " where fkly is null and 1=1 "+orther_sb+
           xzry +
           a02str +
           a01sb +
        " UNION ALL ("+getComSQL()+
                   " where 1=1 "+orther_sb+
                    " and a01.orgid in "+
                         "(select cu.b0111 "+
                            "from competence_userdept cu "+
                           "where cu.userid = "+
                                 "'"+userid+"' "+cu_b0111_sb+
                             ") "+ a01sb +
                     " "+lsltry+") ";
	}
	
	
	public static String getCondiQuerySQL(String userid, StringBuffer a01sb,StringBuffer a02sb, StringBuffer a02_a0201b_sb, StringBuffer cu_b0111_sb, StringBuffer orther_sb,String a0163, String qtxzry,String sid){
		
		  /*离退，当离退管理中选 离休、退休、退职，               显示：离退人员。     查询：离退人员。

			退出管理， 选择 【调出或转出】 下的选项时，            显示：调出人员。     查询：历史人员

				   选择【其它退出方式】中的 【离退休】         显示：离退人员。     查询：离退人员
			【因选举退出登记】【开除】【辞退】【辞去公职】【其它】 显示：其它人员。     查询：历史人员
							    【死亡】           显示：已去世。       查询：历史人员           */
			//String xzry = "";
			//String lsltry = "";
			/*if("1".equals(a0163)){
				xzry = " and a01.status = '1' ";
				lsltry = " and 1=2 ";
			}else if("2".equals(a0163)||"3".equals(a0163)||"4".equals(a0163)||"5".equals(a0163)){
				xzry = " and 1=2 ";
				lsltry = " and a01.a0163 != '1' ";//现在改为只有现职和非现职
			}else if("21".equals(a0163)||"22".equals(a0163)||"23".equals(a0163)||"29".equals(a0163)){
				xzry = " and 1=2 ";
				lsltry = " and a01.a0163 = '"+a0163+"' ";//现在改为只有现职和非现职
			}else{
				xzry = " and a01.status = '1' ";
				lsltry = " AND (a01.status = '3' OR a01.status = '2') ";
			}*/
			String a0163sql = "";
			if("2".equals(a0163)){
				a0163sql = " and a0163 in('2','21','22','23','29')";
			}else if("".equals(a0163)){
				
			}else{
				a0163sql = " and a0163='"+a0163+"'";
			}
			String a02str = "";
			if("0".equals(qtxzry)){
				a02str = " and "+concat("a01.a0000", "''")+" in "+
			               "(select a02.a0000 "+
			                  "from A02 a02 "+
			                "where a02.A0201B in "+
			                       "(select cu.b0111 "+
			                          "from competence_userdept cu "+
			                         "where cu.userid = '"+userid+"') "+
			                   "and a02.a0281='true' " +
			                   a02_a0201b_sb +a02sb+
			                   ") "+a0163sql;
			}else{
				a02str = " and not exists (select 1 from a02   where a02.a0000=a01.a0000 and a0281='true' and a0201b in(select b0111 from b01 where b0111!='-1')  "+a02sb+")   "
						+ " and a01.status!='4' "+a0163sql ;
				/*a02str = " and  (exists (select 1 from a02   where a02.a0000=a01.a0000) " +
						" and not exists " +
						" (select 1 from a02   where a02.a0000=a01.a0000 and (a02.a0201b !='-1' and a0255='1')) or "
						+ " not exists (select 1 from a02   where a02.a0000=a01.a0000 )    ) ";*/ //职务不为其它单位且任职为在职的 不需要选出来
			}
			
			return getComSQL(sid)+
	         " where fkly is null and 1=1 "+orther_sb+
	         //  xzry +
	           a02str +
	           a01sb 
	        /*" UNION ALL ("+getComSQL(sid)+
	                   " where 1=1 "+orther_sb+
	                    " and a01.orgid in "+
	                         "(select cu.b0111 "+
	                            "from competence_userdept cu "+
	                           "where cu.userid = "+
	                                 "'"+userid+"' "+cu_b0111_sb+
	                             ") "+ a01sb +
	                     " "+lsltry+") "*/;
		}
	public static String getCondiQuerySQL(String userid, StringBuffer a01sb,String a01sb1,StringBuffer a02sb, StringBuffer a02_a0201b_sb, StringBuffer cu_b0111_sb, StringBuffer orther_sb,String a0163, String qtxzry,String sid,boolean isgbk,String querydb){
		
		  /*离退，当离退管理中选 离休、退休、退职，               显示：离退人员。     查询：离退人员。

			退出管理， 选择 【调出或转出】 下的选项时，            显示：调出人员。     查询：历史人员

				   选择【其它退出方式】中的 【离退休】         显示：离退人员。     查询：离退人员
			【因选举退出登记】【开除】【辞退】【辞去公职】【其它】 显示：其它人员。     查询：历史人员
							    【死亡】           显示：已去世。       查询：历史人员           */
			//String xzry = "";
			//String lsltry = "";
			/*if("1".equals(a0163)){
				xzry = " and a01.status = '1' ";
				lsltry = " and 1=2 ";
			}else if("2".equals(a0163)||"3".equals(a0163)||"4".equals(a0163)||"5".equals(a0163)){
				xzry = " and 1=2 ";
				lsltry = " and a01.a0163 != '1' ";//现在改为只有现职和非现职
			}else if("21".equals(a0163)||"22".equals(a0163)||"23".equals(a0163)||"29".equals(a0163)){
				xzry = " and 1=2 ";
				lsltry = " and a01.a0163 = '"+a0163+"' ";//现在改为只有现职和非现职
			}else{
				xzry = " and a01.status = '1' ";
				lsltry = " AND (a01.status = '3' OR a01.status = '2') ";
			}*/
			String a0163sql = "";
			if("2".equals(a0163)){
				a0163sql = " and a0163 in('2','21','22','23','29')";
			}else if("".equals(a0163)){
				
			}else{
				a0163sql = " and a0163='"+a0163+"'";
			}
			String a02str = "";
			if("0".equals(qtxzry)){
				if(isgbk) {
					a02str = " and "+concat("a01.a0000", "''")+" in "+
				               "(select a02.a0000 "+
				                  "from A02 a02 "+
				                "where a02.a0201b in "+
				                       "(select cu.b0111 "+
				                          "from competence_userdept cu  ,b01 b "+
				                         "where  cu.b0111=b.b0111 and cu.userid = '"+userid+"'"+a01sb1+") "+
				                   "and a02.a0281='true' " +
				                   a02_a0201b_sb +a02sb+
				                   ") "+a0163sql;
				} else {
					a02str = " and "+concat("a01.a0000", "''")+" in "+
				               "(select a02.a0000 "+
				                  "from v_js_a02 a02 "+
				                "where 1=1 "+
				                   "and a02.a0281='true' " +
				                   a02_a0201b_sb +a02sb+
				                   ") "+a0163sql;
				}
				
			}else{
				if(isgbk) {
					a02str = " and not exists (select 1 from a02  where a02.a0000=a01.a0000 and a0281='true' and a0201b in(select b0111 from b01 where b0111!='-1')  "+a02sb+")   "
							+ " and a01.status!='4' "+a0163sql ;
				} else {
					a02str = " and not exists (select 1 from v_js_a02 a02  where a02.v_xt=a01.v_xt and a02.a0000=a01.a0000 and a0281='true' and a0201b in(select b0111 from v_js_b01 b01 where v_xt='"+querydb+"' and b0111!='-1')  "+a02sb+")   "
							+ " and a01.status!='4' "+a0163sql ;
				}
				
				/*a02str = " and  (exists (select 1 from a02   where a02.a0000=a01.a0000) " +
						" and not exists " +
						" (select 1 from a02   where a02.a0000=a01.a0000 and (a02.a0201b !='-1' and a0255='1')) or "
						+ " not exists (select 1 from a02   where a02.a0000=a01.a0000 )    ) ";*/ //职务不为其它单位且任职为在职的 不需要选出来
			}
			if(isgbk) {
				return getComSQL(sid,isgbk,querydb)+
				         " where fkly is null and  1=1 "+orther_sb+
				         //  xzry +
				           a02str +
				           a01sb 
				        /*" UNION ALL ("+getComSQL(sid)+
				                   " where 1=1 "+orther_sb+
				                    " and a01.orgid in "+
				                         "(select cu.b0111 "+
				                            "from competence_userdept cu "+
				                           "where cu.userid = "+
				                                 "'"+userid+"' "+cu_b0111_sb+
				                             ") "+ a01sb +
				                     " "+lsltry+") "*/;
			} else {
				return getComSQL(sid,isgbk,querydb)+
				         " where  a01.v_xt='"+querydb+"' "+orther_sb+
				         //  xzry +
				           a02str +
				           a01sb 
				        /*" UNION ALL ("+getComSQL(sid)+
				                   " where 1=1 "+orther_sb+
				                    " and a01.orgid in "+
				                         "(select cu.b0111 "+
				                            "from competence_userdept cu "+
				                           "where cu.userid = "+
				                                 "'"+userid+"' "+cu_b0111_sb+
				                             ") "+ a01sb +
				                     " "+lsltry+") "*/;
			}
			
		}
	public static String getCondiQuerySQLp(String userid, StringBuffer a01sb,StringBuffer a02sb, StringBuffer a02_a0201b_sb, StringBuffer cu_b0111_sb, StringBuffer orther_sb,String qtxzry,String sid,boolean isgbk,String querydb){
		
		  /*离退，当离退管理中选 离休、退休、退职，               显示：离退人员。     查询：离退人员。

			退出管理， 选择 【调出或转出】 下的选项时，            显示：调出人员。     查询：历史人员

				   选择【其它退出方式】中的 【离退休】         显示：离退人员。     查询：离退人员
			【因选举退出登记】【开除】【辞退】【辞去公职】【其它】 显示：其它人员。     查询：历史人员
							    【死亡】           显示：已去世。       查询：历史人员           */
			//String xzry = "";
			//String lsltry = "";
			/*if("1".equals(a0163)){
				xzry = " and a01.status = '1' ";
				lsltry = " and 1=2 ";
			}else if("2".equals(a0163)||"3".equals(a0163)||"4".equals(a0163)||"5".equals(a0163)){
				xzry = " and 1=2 ";
				lsltry = " and a01.a0163 != '1' ";//现在改为只有现职和非现职
			}else if("21".equals(a0163)||"22".equals(a0163)||"23".equals(a0163)||"29".equals(a0163)){
				xzry = " and 1=2 ";
				lsltry = " and a01.a0163 = '"+a0163+"' ";//现在改为只有现职和非现职
			}else{
				xzry = " and a01.status = '1' ";
				lsltry = " AND (a01.status = '3' OR a01.status = '2') ";
			}*/
			String a0163sql = "";
			String a02str = "";
			if("0".equals(qtxzry)){
				if(isgbk) {
					a02str = " and "+concat("a01.a0000", "''")+" in "+
				               "(select a02.a0000 "+
				                  "from A02 a02 "+
				                "where a02.a0201b in "+
				                       "(select cu.b0111 "+
				                          "from competence_userdept cu  ,b01 b "+
				                         "where  cu.b0111=b.b0111 and cu.userid = '"+userid+"') "+
				                   "and a02.a0281='true' " +
				                   a02_a0201b_sb +a02sb+
				                   ") "+a0163sql;
				} else {
					a02str = " and "+concat("a01.a0000", "''")+" in "+
				               "(select a02.a0000 "+
				                  "from v_js_a02 a02 "+
				                "where 1=1 "+
				                   "and a02.a0281='true' " +
				                   a02_a0201b_sb +a02sb+
				                   ") "+a0163sql;
				}
				
			}else{
				if(isgbk) {
					a02str = " and not exists (select 1 from a02  where a02.a0000=a01.a0000 and a0281='true' and a0201b in(select b0111 from b01 where b0111!='-1')  "+a02sb+")   "
							+ " and a01.status!='4' "+a0163sql ;
				} else {
					a02str = " and not exists (select 1 from v_js_a02 a02  where a02.v_xt=a01.v_xt and a02.a0000=a01.a0000 and a0281='true' and a0201b in(select b0111 from v_js_b01 b01 where v_xt='"+querydb+"' and b0111!='-1')  "+a02sb+")   "
							+ " and a01.status!='4' "+a0163sql ;
				}
				
				/*a02str = " and  (exists (select 1 from a02   where a02.a0000=a01.a0000) " +
						" and not exists " +
						" (select 1 from a02   where a02.a0000=a01.a0000 and (a02.a0201b !='-1' and a0255='1')) or "
						+ " not exists (select 1 from a02   where a02.a0000=a01.a0000 )    ) ";*/ //职务不为其它单位且任职为在职的 不需要选出来
			}
			if(isgbk) {
				return getComSQL(sid,isgbk,querydb)+
				         " where fkly is null and  1=1 "+orther_sb+
				         //  xzry +
				           a02str +
				           a01sb 
				        /*" UNION ALL ("+getComSQL(sid)+
				                   " where 1=1 "+orther_sb+
				                    " and a01.orgid in "+
				                         "(select cu.b0111 "+
				                            "from competence_userdept cu "+
				                           "where cu.userid = "+
				                                 "'"+userid+"' "+cu_b0111_sb+
				                             ") "+ a01sb +
				                     " "+lsltry+") "*/;
			} else {
				return getComSQL(sid,isgbk,querydb)+
				         " where  a01.v_xt='"+querydb+"' "+orther_sb+
				         //  xzry +
				           a02str +
				           a01sb 
				        /*" UNION ALL ("+getComSQL(sid)+
				                   " where 1=1 "+orther_sb+
				                    " and a01.orgid in "+
				                         "(select cu.b0111 "+
				                            "from competence_userdept cu "+
				                           "where cu.userid = "+
				                                 "'"+userid+"' "+cu_b0111_sb+
				                             ") "+ a01sb +
				                     " "+lsltry+") "*/;
			}
			
		}
	
	/**
	 * oracle 拼接 '' 不走索引
	 * mysql 直接用 =
	 * @param field
	 * @param str
	 * @return
	 */
	public static String concat(String field, String str) {
		if(DBUtil.getDBType()==DBType.ORACLE){
			return "concat("+field+", "+str+")";
		}else{
			return field;
		}
	}
	/**
	 * oracle 排序
	 * mysql 排序    null和空字符串排在前面处理
	 * @param
	 * @return
	 */
	public static String OrderBy(String sort) {
		//机构排序
		//String b01ordersql = "(select min(A0201B) from a02 where a01.a0000 = a02.a0000  and a02.a0281 = 'true'),";
		//String ordersql = ",(select max(a0225) from b01, a02 where a01.a0000 = a02.a0000 and a02.a0201b = b01.b0111 and a02.a0255 = '1')";
		//集体内排序
		//String ordersql = "(select max(a0225) from a02 where a01.a0000 = a02.a0000   and a02.a0281 = 'true')";
		/*if(DBUtil.getDBType()==DBType.ORACLE){
			//return " order by "+field+ordersql;
			return " order by "+b01ordersql+ordersql;
		}else{
			//return " order by CASE WHEN  "+field+" is null or "+field+"='' THEN 'zzzz' else "+field+" end"+ordersql;
			return " order by "+b01ordersql+ordersql;
		}*/
		
		//将用户的自定义排序字段更新
		/*List<Object[]> list = HBUtil.getHBSession().createSQLQuery("select t.dicid,t.orderType"
				+ " from a01_sortconfig t where t.isvali='true' and userid='"+userid+"' order by t.orderid").list();
		A01_SORTCONFIG_LIST.put(userid, list);
		
		String order =  getSort(userid);
		
		if(order != null && !order.equals("")){
			return " order by sort,"+ order;
		}
		*/
		if(sort==null||sort.equals(null+" "+null))
		{
			sort="sort";
		}
		else {
			sort+=", sort";
		}
		if(DBUtil.getDBType()==DBType.ORACLE){
			
			//return " order by a01.torgid,a01.torder ";
			return " order by "+sort;
		}else{
			//return " order by a01.torgid,a01.torder";
			
			return " order by "+sort;
		}
	}
	
	public static String OrderByF(String userid,String isA0225,String b0111,String querySQL) {
		return OrderByF(userid, isA0225, b0111, querySQL, null);
	}
	public static String OrderByF(String userid,String isA0225,String b0111,String querySQL,String a02_a0201b_sb) {
		String a0201b_sb = "";
		if(!StringUtils.isEmpty(b0111)){
			a0201b_sb = "and a0201b like '"+b0111+"%'";
		}else{
			a0201b_sb = a02_a0201b_sb;
		}
		
		String ordersql = "";
		if(isA0225 != null && isA0225.equals("1")){
			//集体内排序
			ordersql = "(select lpad(max(a0225),25,'"+0+"')  from a02 where a01.a0000 = a02.a0000  and a02.a0281 = 'true' and a02.A0201B = '"+b0111+"')";
			return " order by "+ ordersql;
		}
		
		/*//将用户的自定义排序字段更新
		List<Object[]> list = HBUtil.getHBSession().createSQLQuery("select t.dicid,t.orderType"
				+ " from a01_sortconfig t where t.isvali='true' and userid='"+userid+"' order by t.orderid").list();
		A01_SORTCONFIG_LIST.put(userid, list);
		
		String order =  getSort(userid);*/
		String order = "";
		if(order != null && !order.equals("")){
			return " order by "+ order;
		}else{
			
			
			//2020年6月4日17:52:17 新排序字段b0269; zoul
			//ordersql = "(select min(rpad(b0269, 25, '.') || lpad(a0225, 25, '0')) from b01, a02 x where b01.b0111 = x.a0201b and a01.a0000 = x.a0000 and x.a0201b like '"+a0200+"%' and x.a0281 = 'true')";
			ordersql = "((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0') from ( select a02.a0000,b0269,a0225,row_number()over(partition by a02.a0000 order by nvl(a02.a0279,0) desc,b0269) rn from a02,b01 where a02.a0201b=b01.b0111 and a0281 = 'true' "+a0201b_sb+")  t where rn=1 and t.a0000=a01.a0000))";

			/*" (select lpad(max(a0225), 25, '0')"+
	                    "   from a02"+
	                    "  where a01.a0000 = a02.a0000"+
	                       " and a02.a0281 = 'true'"+
	                       " )"*/
			//ordersql += "(select lpad(max(a0225),25,'"+0+"')  from a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a02.A0201B = '"+a0200+"')";
			return " order by "+ ordersql;
			
			//根据客户要求，如果手动排序未选择，默认是按全选的来
			//return " order by a01.torder ";
			
			//=======================================================
			//邹志林 2019-10-23 包含下级单位情况下判断是否一个法人机构下面，如果是一个法人机构下面则按照集体内排序 
			/*int ii1 = querySQL.indexOf("substr(a02.a0201b, 1, "+a0200.length()+")");
			int ii2 = querySQL.indexOf("'"+a0200+"'");
			if (ii1>0 && ii2>ii1 && ii2-ii1<30){
				 String sql = "select count(distinct a0201b) as ACOUNT from A02 where A0201B LIKE '"+a0200+"%' and a02.a0281 = 'true' and a0201b in (select b0111 from b01 where b0194='1') ";
				 Object count = HBUtil.getHBSession().createSQLQuery(sql).uniqueResult();
				 if (count!=null && Long.parseLong(count.toString())==1){
						//集体内排序
						ordersql = "(select lpad(max(a0225),25,'"+0+"')  from a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a02.A0201B = '"+a0200+"')";
						return " order by "+ ordersql;
				 }
			} 
			//==================================================
			
			return " order by torgid asc,torder asc ";*/
		}
		
	}
	
	
	/**
	 * 去除右边的字符
	 * @param field
	 * @param str
	 * @return
	 */
	public static String rtrim(String field, String str){
		if(DBUtil.getDBType()==DBType.ORACLE){
			return "rtrim('"+field+"','"+str+"')";
		}else{
			return "TRIM(TRAILING '"+str+"' FROM '"+field+"')";
		}
	}


	public static void updateA01_config(String jsonp,String userid) throws AppException {
		
		JSONObject jsonObject = JSONObject.fromObject(jsonp);
		Iterator<String> it = jsonObject.keys();
		
		HBSession sess = HBUtil.getHBSession();
		Connection con = sess.connection();
		try {
			
			con.setAutoCommit(false);
			PreparedStatement pstat = con.prepareStatement("update a01_config set isvali=?,orderid=? where dicid=? and userid='"+userid+"'");
			// 遍历jsonObject数据，添加到Map对象
			int i = 0;
			while (it.hasNext()) {
				String nodeid = it.next();
				String isvali =  jsonObject.get(nodeid).toString();
				pstat.setString(1, isvali);
				pstat.setInt(2, i);
				pstat.setString(3, nodeid);
				pstat.addBatch();
				//System.out.println(nodeid);
				i++;
			}
			pstat.executeBatch();
			con.commit();
			/*HBUtil.executeUpdate("update a01_config set isvali='true' where dicid in('"+jsonp+"') and userid='"+userid+"'");
			HBUtil.executeUpdate("update a01_config set isvali='false' where dicid not in('"+jsonp+"','a0000') and userid='"+userid+"'");
			*/
			List<Object[]> list = HBUtil.getHBSession().createSQLQuery("select t.dicid,t.code,t.name,t.gridwidth,t.aboutcode,t.formtype,t.tdesc,t.renderer,t.orderid,t.align,t.sortable  "
					+ " from a01_config t where t.isvali='true' and userid='"+userid+"' order by t.orderid").list();
			A01_CONFIG_LIST.put(userid, list);
			
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new AppException(e.getMessage());
		}
		
	}
	
	
	//更新自定义排序表
	public static void updateA01_sortConfig(String jsonp,String userid) throws AppException {
		
		JSONObject jsonObject = JSONObject.fromObject(jsonp);
		Iterator<String> it = jsonObject.keys();
		
		HBSession sess = HBUtil.getHBSession();
		Connection con = sess.connection();
		try {
			
			con.setAutoCommit(false);
			PreparedStatement pstat = con.prepareStatement("update a01_sortconfig set isvali=?,orderid=?,orderType=? where dicid=? and userid='"+userid+"'");
			// 遍历jsonObject数据，添加到Map对象
			int i = 1;
			while (it.hasNext()) {
				
				if(i%2 == 0){
					String nodeid = it.next();
					i = i+1;
					continue;
				}
				
				String nodeid = it.next();
				
				String isvali =  jsonObject.get(nodeid).toString();		//获得自定义排序字段是否生效：true，false
				String orderType =  jsonObject.get(nodeid+"orderType").toString();		//获得自定义排序类型是否生效：true，false
				
				pstat.setString(1, isvali);		//是否有效
				pstat.setInt(2, i);				//序号
				pstat.setString(3, orderType);	//排序类型
				pstat.setString(4, nodeid);  	//数据项编码
				pstat.addBatch();
				i = i+1;
			}
			pstat.executeBatch();
			con.commit();
			
			
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new AppException(e.getMessage());
		}
		
	}
	
	
	public static Map<String, List<Object[]>> getInfoExt(String tableName){
		String sql = getInfoSQL(tableName);
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			List<Object[]> list = sess.createSQLQuery(sql).list();
			Map<String, List<Object[]>> info_map = new LinkedHashMap<String, List<Object[]>>();
			if(list!=null&&list.size()>0){
				for(Object[] os : list){
					List<Object[]> os_list = info_map.get(os[0]+"___"+os[4]);
					if(os_list==null){
						os_list = new ArrayList<Object[]>();
						os_list.add(os);
						info_map.put(os[0].toString()+"___"+os[4].toString(), os_list);
					}else{
						os_list.add(os);
					}
					
				}
			}
			return info_map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getInfoSQL(String tableName){
		return "select t.table_code, t.col_code,t.col_name,t.code_type,a.add_type_name,t.col_data_type_should " +
		" from (select ctc.table_code, ctc.col_code,ctc.col_name,ctc.code_type," +
		" ctc.col_data_type_should,ctc.is_new_code_col,av.isused,av.ADD_VALUE_SEQUENCE from code_table_col ctc " +
		" left join add_value av on ctc.col_code=av.col_code) t " +
		" left join add_type a on t.table_code=a.table_code where t.is_new_code_col='1' and t.isused='1' and t.table_code " +
		" in("+tableName+") order by t.ADD_VALUE_SEQUENCE";
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static String getSort(String userid){
		StringBuilder sb = new StringBuilder();
		
		if(A01_SORTCONFIG_LIST.get(userid).size() == 0){
			return  sb.toString();
		}
		
		for(Object[] o : A01_SORTCONFIG_LIST.get(userid)){
			
			sb.append(o[0].toString()+" ");
			
			String orderType = "desc";
			if(o[1].toString() != null && o[1].toString().equals("true") ){
				orderType = "asc";
			}
			
			sb.append(orderType+",");
		}
		sb.deleteCharAt(sb.length()-1);
		
		return  sb.toString();
	}
	
	
	
	public static String to_date(String f){
		if(f.length()==6){
			f = f+"01";
		}
		if(DBUtil.getDBType()==DBType.ORACLE){
			return "to_date('"+f+"','yyyymmdd')";
			
		}else{
			return "str_to_date('"+f+"','%Y%m%d')";
		}
	}
	/**
	 * +1时间加1天
	 * @param d
	 * @return
	 */
	public static String adddate(String d){
		
		if(DBUtil.getDBType()==DBType.ORACLE){
			return d+"+1";
			
		}else{
			return "date_add("+d+", interval 1 day)";
		}
	}

	public static CharSequence getComFields() {
		
		return "A0000 as a0000,A0101 as a0101,A0192A as a0192a,A0104 as a0104,A0117 as a0117,A0111A as a0111,(select A0801B from a08 a8 where a8.a0000 = a01.a0000 and a8.A0831= '1') as qrzxl,QRZXLXX as qrzxlxx,(select A0801B from a08 a8 where a8.a0000 = a01.a0000 and a8.A0838= '1') as zzxl,ZZXLXX as zzxlxx,A0107 as a0107,A0140 as a0140,decode(ISDATE (a01.a0107),0,0,1,trunc(months_between(sysdate,to_date(decode(length(a01.a0107),8,a01.a0107,6,concat(a01.a0107,'01')),'yyyymmdd'))/12,0)) as nl,A0134 as a0134,concat(concat(substr(cp.A0243,0,4),'.'),substr(cp.A0243,5,2)) as a0192f,A0192C as a0192c";
	}

	/**
	 * 饶溪廉，20200203创建，防止编译报错，还有逻辑上还没补足
	 * @param userID
	 * @param a01sb
	 * @param a02sb
	 * @param a02_a0201b_sb
	 * @param cu_b0111_sb
	 * @param orther_sb
	 * @param a0163
	 * @return
	 */
	public static String getCondiQuerySQL(String userID, StringBuffer a01sb, StringBuffer a02sb,
			StringBuffer a02_a0201b_sb, StringBuffer cu_b0111_sb, StringBuffer orther_sb, String a0163) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	/**
     * 获取字段树数据
     */
    public static List<Object[]> getA01ConfigTpbjTree(String userId) throws AppException {
        // 静态变量没有从A01_CONFIG_TPBJ表中找，有就直接返回
        HBSession sess = HBUtil.getHBSession();
        String sql = "select * from A01_CONFIG_TPBJ where userid='" + userId + "' and dicid not in('A0000','A0104','A0107','A0117','A0111A','A0140','A0196','A0192a','A0196C')  order by orderid";
        List<Object[]> list = sess.createSQLQuery(sql).list();
        if (list != null && list.size() > 0) {
            return list;
        }
        initA01ConfigTpbj(userId);
        return getA01ConfigTpbjTree(userId);
    }
    public static void initA01ConfigTpbj(String userId) throws AppException {
        // 如果A01_CONFIG_TPBJ表中也没有，就从初始表A01_CONFIG_TPBJ_INIT中初始化
        HBSession sess = HBUtil.getHBSession();
        String sql = "insert into a01_config_tpbj select t.*,'" + userId + "' from A01_CONFIG_TPBJ_INIT t";
        HBUtil.executeUpdate(sql);
        sess.flush();
    }
	
    public static void updateA01ConfigTpbj(String jsonp, String userId) throws AppException {
        JSONObject jsonObject = JSONObject.fromObject(jsonp);
        Iterator<String> it = jsonObject.keys();

        HBSession sess = HBUtil.getHBSession();
        Connection con = sess.connection();
        try {
            con.setAutoCommit(false);
            PreparedStatement pstat = con.prepareStatement("update a01_config_tpbj set isvali=?,orderid=? where dicid=? and userid='" + userId + "'");
            // 遍历jsonObject数据，添加到Map对象
            int i = 0;
            while (it.hasNext()) {
                String nodeid = it.next();
                String isvali = jsonObject.get(nodeid).toString();
                pstat.setString(1, isvali);
                pstat.setInt(2, i);
                pstat.setString(3, nodeid);
                pstat.addBatch();
                i++;
            }
            pstat.executeBatch();
            con.commit();
            List<Object[]> list = HBUtil.getHBSession().createSQLQuery("select t.dicid,t.code,t.name,t.gridwidth,t.aboutcode,t.formtype,t.tdesc,t.renderer,t.orderid,t.align  "
                    + " from a01_config_tpbj t where t.isvali='true' and userid='" + userId + "' order by t.orderid").list();
            A01_CONFIG_LIST.put(userId, list);
        } catch (Exception e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            throw new AppException(e.getMessage());
        }
    }
    
    
    public static List<Object[]> getA01ConfigTpbjChecked(String userId) throws AppException {
        // 静态变量没有从A01_CONFIG_TPBJ表中找，有就直接返回
        HBSession sess = HBUtil.getHBSession();
        String sql = "select * from A01_CONFIG_TPBJ where userid='" + userId + "' order by orderid";
        List<Object[]> list = sess.createSQLQuery(sql).list();
        if (list != null && list.size() > 0) {
            return list;
        }
        initA01ConfigTpbj(userId);
        return getA01ConfigTpbjChecked(userId);
    }
    
}
