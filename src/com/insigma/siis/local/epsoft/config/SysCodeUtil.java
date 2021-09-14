package com.insigma.siis.local.epsoft.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.util.EhCacheUtil;

/**
 * @author wengsh
 *
 */
public class SysCodeUtil {

	/**
	 * init 通过ehcache
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void init(){
		HBSession session = HBUtil.getHBSession();
		String sql = "select code_type from code_type where code_type in('A0251B','GB2261','GB3304','GB2261D','ZB130','ZB135','ZB125','ZB126','GB4761','GB4762','ZB09','ZB03','ZB148','ZB01','ZB139','ZB134','XZ91','XZ93','XZ94','XZ95','XZ96','XZ97','A3385','B0194')";
		List<String> list = session.createSQLQuery(sql).list();
		for (int i = 0; i < list.size(); i++) {
			String aaa100 = list.get(i);
			setComboxCacheData(aaa100);//设置代码-名称
			setSubCodeValueCacheData(aaa100);//设置代码-父节点
		}
		setCodeValueCacheData("ZB01", "2");//设置特殊 行政区划 名称2
		setCodeValueCacheData("ZB01", "3");//设置特殊 行政区划 名称3
	}

	/**
	 * 将combo值设置到缓存中
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public static void setComboxCacheData(String aaa100) {
		HBSession session = HBUtil.getHBSession();
		// 下拉框
		String sql = "select  aaa102, aaa103 from v_aa10 t where aaa100='" + aaa100 + "'  order by aaa102 ";
		List<Object[]> listdetail = session.createSQLQuery(sql).list();
		if (listdetail != null && listdetail.size() > 0) {
			Map<String, List<Map<String, String>>> codemap = new HashMap<String, List<Map<String, String>>>();
			StringBuffer sb = new StringBuffer("");
			for (int j = 0; j < listdetail.size(); j++) {
				Object[] codevalue = listdetail.get(j);
				sb.append("['" + codevalue[0].toString() + "','" + codevalue[1].toString() + "','']");
				if (j != listdetail.size() - 1) {
					sb.append(",");
				}
			}
			EhCacheUtil.setCache(aaa100, sb.toString());
			EhCacheUtil.setCache(aaa100 + "_select", listdetail);
		} else {
			EhCacheUtil.setCache(aaa100, "[]");
			EhCacheUtil.setCache(aaa100 + "_select", null);
		}
	}
	
	/**
	 * 将code_value(特别是code_name相关)值设置到缓存中
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public static void setCodeValueCacheData(String codetype, String codeNameNum) {
		if(codeNameNum == null || "".equals(codeNameNum)) {
			return ;
		}
		HBSession session = HBUtil.getHBSession();
		// 下拉框
		String sql = "select  code_value, code_name"+codeNameNum+" from code_value t where code_type='"+codetype+"' and "+DBUtil.getColumnIsNotNull("code_value")+" and CODE_STATUS = '1' order by inino,code_value";
		List<Object[]> listdetail = session.createSQLQuery(sql).list();
		codeNameNum = codeNameNum == "" ? "" : "_"+codeNameNum;
		if (listdetail != null && listdetail.size() > 0) {
			Map<String, List<Map<String, String>>> codemap = new HashMap<String, List<Map<String, String>>>();
			StringBuffer sb = new StringBuffer("");
			for (int j = 0; j < listdetail.size(); j++) {
				Object[] codevalue = listdetail.get(j);
				sb.append("['" + codevalue[0].toString() + "','" + codevalue[1].toString() + "','']");
				if (j != listdetail.size() - 1) {
					sb.append(",");
				}
			}
			EhCacheUtil.setCache(codetype + codeNameNum, sb.toString());
			EhCacheUtil.setCache(codetype + codeNameNum+ "_select", listdetail);
		} else {
			EhCacheUtil.setCache(codetype + codeNameNum, "[]");
			EhCacheUtil.setCache(codetype + codeNameNum+ "_select", null);
		}
	}
	
	/**
	 * 将code_value父节点的值设置到缓存中
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public static void setSubCodeValueCacheData(String aaa100) {
		HBSession session = HBUtil.getHBSession();
		// 下拉框
		String sql = "select code_type, code_value, sub_code_value from code_value where code_type= '"+aaa100+"' and "+DBUtil.getColumnIsNotNull("code_value")+" and CODE_STATUS = '1' order by inino,code_value ";
		List<Object[]> listdetail = session.createSQLQuery(sql).list();
		if (listdetail != null && listdetail.size() > 0) {
			Map<String, List<Map<String, String>>> codemap = new HashMap<String, List<Map<String, String>>>();
			StringBuffer sb = new StringBuffer("");
			for (int j = 0; j < listdetail.size(); j++) {
				Object[] codevalue = listdetail.get(j);
				sb.append("['" + codevalue[0] + "','" + codevalue[1] + "','" + codevalue[2] + "','']");
				if (j != listdetail.size() - 1) {
					sb.append(",");
				}
			}
			EhCacheUtil.setCache(aaa100 + "_sub", sb.toString());
			EhCacheUtil.setCache(aaa100 + "_sub_select", listdetail);
		} else {
			EhCacheUtil.setCache(aaa100 + "_sub", "[]");
			EhCacheUtil.setCache(aaa100 + "_sub_select", null);
		}
	}
	
	/**
	 * 通过code_type及code_value获取code_name
	 */
	public static String getCodeName(String codetype,Object codevalue) {
		if(codevalue == null || "".equals(codevalue)) return  "";
		return getCodeName(codetype, codevalue.toString());
	}
	
	/**
	 * 通过code_type及code_value获取code_name
	 */
	public static String getCodeName(String codetype,String codevalue) {
		if(codevalue == null || "".equals(codevalue)) return  "";
		return getCodeName(codetype, codevalue, "");
	}
	
	/**
	 * 通过code_type及code_value获取code_name、code_name2、code_name3
	 */
	@SuppressWarnings("unchecked")
	public static String getCodeName(String codetype,String codevalue, String codeNameNum) {
		if(codevalue == null || "".equals(codevalue)){
			return  "";
		}
		String codetypeNum = "".equals(codeNameNum) ? codetype : codetype + "_" + codeNameNum;
		List<Object[]> list=(List<Object[]>)EhCacheUtil.getObjectInCache(codetypeNum+"_select");
		if(list!=null) {
			for(int i=0;i<list.size();i++) {
				Object[] objectcode = list.get(i);
				if(objectcode[0].equals(codevalue)) {
					return objectcode[1].toString();
				}
			}
			return  "";
		}else {
			HBSession session=HBUtil.getHBSession();
			String sql="select  code_value, code_name"+codeNameNum+" from code_value t where code_type='"+codetype+"' and code_value='"+codevalue+"' and CODE_STATUS = '1'";
			List<Object[]> listdetail = session.createSQLQuery(sql).list();
			if (listdetail != null && listdetail.size() > 0) {
				Object[] names = listdetail.get(0);
				return names[1].toString();
			}else {
				return "";
			}
		}
		
	}
	
	/**
	 * 通过code_type及code_name获取code_value
	 */
	@SuppressWarnings("unchecked")
	public static String getCodeValue(String codetype,String codename) {
		if(codename == null || "".equals(codename)){
			return  "";
		}
		List<Object[]> list=(List<Object[]>)EhCacheUtil.getObjectInCache(codetype+"_select");
		if(list!=null) {
			for(int i=0;i<list.size();i++) {
				Object[] objectcode = list.get(i);
				if(objectcode[1].equals(codename)) {
					return objectcode[0].toString();
				}
			}
			return  "";
		}else {
			HBSession session=HBUtil.getHBSession();
			String sql="select  aaa102, aaa103 from v_aa10 t where aaa100='"+codetype+"' and aaa103='"+codename+"'";
			List<Object[]> listdetail = session.createSQLQuery(sql).list();
			if (listdetail != null && listdetail.size() > 0) {
				Object[] names = listdetail.get(0);
				return names[0].toString();
			}else {
				return "";
			}
		}
		
	}

	/**
	 * 通过code_type及code_name2或code_name3获取code_value
	 * 
	 * iscodename2 true 根据名称2 获取编码值
	 * iscodename2 false 根据名称3 获取编码值
	 */
	public static String getCodeValue(String codetype,String codename, String code_name) {
		return getCodeValue(codetype,codename,"",code_name);
	}
	
	/**
	 * 通过code_type、sub_code_value及code_name2或code_name3获取code_value
	 * 
	 * iscodename2 true 根据名称2 获取编码值
	 * iscodename2 false 根据名称3 获取编码值
	 */
	@SuppressWarnings("unchecked")
	public static String getCodeValue(String codetype,String codename,String subcodevalue, String code_name) {
		if(codename == null || "".equals(codename)){
			return  "";
		}
		if(!"".equals(subcodevalue)) subcodevalue = " and sub_code_value = '"+subcodevalue+"' ";
		code_name = code_name.toLowerCase();
		String codetypeselect = codetype + "_" + code_name;
		String sql="select  code_value, code_name from code_value t where code_type='"+codetype+"' and code_name='"+codename+"' " + subcodevalue + " and CODE_STATUS = '1'";
		if("code_name2".equals(code_name)){
			sql="select  code_value, code_name2 from code_value t where code_type='"+codetype+"' and code_name2='"+codename+"' " + subcodevalue + " and CODE_STATUS = '1'";
		}else if ("code_name3".equals(code_name)){
			sql="select  code_value, code_name3 from code_value t where code_type='"+codetype+"' and code_name3='"+codename+"' " + subcodevalue + " and CODE_STATUS = '1'";
		}else{
			return getCodeValue( codetype, codename);
		}
		HBSession session=HBUtil.getHBSession();
		List<Object[]> listdetail = session.createSQLQuery(sql).list();
		if (listdetail != null && listdetail.size() > 0) {
			Object[] names = listdetail.get(0);
			EhCacheUtil.setCache(codetypeselect, names[0].toString());
			return names[0].toString();
		}else {
			return "";
		}
	}
	
	/**
	 * 通过code_type及code_value获取sub_code_value
	 */
	@SuppressWarnings("unchecked")
	public static String getSubCodeValue(String codetype,String codevalue) {
		if(codevalue == null || "".equals(codevalue)){
			return  "";
		}
		List<Object[]> list=(List<Object[]>)EhCacheUtil.getObjectInCache(codetype+"_sub_select");
		if(list!=null) {
			for(int i=0;i<list.size();i++) {
				Object[] objectcode = list.get(i);
				if(objectcode[1].equals(codevalue)) {
					return objectcode[2].toString();
				}
			}
			return  "";
		}else {
			try {
				return HBUtil.getValueFromTab("sub_code_value", "code_value", "code_value = '"+codevalue+"' and code_type = '"+codetype+"'");
			} catch (AppException e) { }
			return "";
		}
		
	}
	
	/**
	 * 通过code_type及code_value获取code_name
	 */
	@SuppressWarnings("unchecked")
	public static Boolean hasCode(String codetype) {
		List<Object[]> list=(List<Object[]>)EhCacheUtil.getObjectInCache(codetype+"_select");
		if(list!=null) {
			return true;
		}else {
			HBSession session=HBUtil.getHBSession();
			String sql="select  aaa102, aaa103 from v_aa10 t where aaa100='"+codetype+"'";
			List<Object[]> listdetail = session.createSQLQuery(sql).list();
			if (listdetail != null && listdetail.size() > 0) {
				return true;
			}else {
				return false;
			}
		}
		
	}

	/**
	 * 判断code_value是否在某父code_value节点下
	 * 
	 * codetype 类型
	 * codevalue 某级父节点
	 */
	@SuppressWarnings("unchecked")
	public static Boolean havaCodeValues(String codetype,String p_codevalue, String codevalue) {
		if(codetype==null || "".equals(codetype)) return false;
		HBSession session=HBUtil.getHBSession();
		String sql="select code_value,code_name from code_value where code_type='"+codetype+"' and sub_code_value ='"+p_codevalue+"' and code_value ='"+codevalue+"'";
		List<Object[]> list = session.createSQLQuery(sql).list();
		if(list!=null && list.size() > 0) return true;
		return  false;
	}
}
