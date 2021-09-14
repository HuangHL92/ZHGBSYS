package com.insigma.siis.local.business.sysmanager.verificationschemeconf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.BSSupport;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.util.BeanUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.Aa01;
import com.insigma.siis.local.business.entity.Aa10;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.CodeTable;
import com.insigma.siis.local.business.entity.CodeTableCol;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.VVerifyColVsl006;
import com.insigma.siis.local.business.entity.VerifyScheme;
import com.insigma.siis.local.business.entity.VerifySqlList;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

/**
 * 校验配置相关模块公共BS
 * 
 * @author mengl
 *
 */
public class RuleSqlListBS extends BSSupport {

	/**
	 * 根据 表（信息集）获取可选的字段（信息项），前提是该信息项配置了可用操作符
	 * 
	 * @author mengl
	 * @param vru004
	 *            或 vsl003 表
	 * @param vsl006sFlag
	 *            是否判断配置的运算符（vsl006s）不为空
	 * @param fliter
	 *            过滤条件
	 * @return
	 * @throws SQLException
	 */
	public static TreeMap<String, String> getVru005byVru004(String vru004, boolean vsl006sFlag, String fliter) {
		TreeMap<String, String> treeMap = new TreeMap<String, String>();
		String sql = " from VVerifyColVsl006 a WHERE a.tableName = '" + vru004 + "' ";
		// 1. vsl006sFlag 是否判断 配置的运算符（vsl006s）不为空
		if (vsl006sFlag) {
			sql += " AND trim(vsl006s) IS NOT NULL ";
		}
		// 2. 添加 过滤条件
		if (!StringUtil.isEmpty(fliter)) {
			sql += " And " + fliter;
		}
		sql += " order by a.tableName";
		@SuppressWarnings("unchecked")
		List<VVerifyColVsl006> list = HBUtil.getHBSession().createQuery(sql).setCacheable(true).list();
		if (list != null && list.size() > 0 && list.get(0) != null) {
			for (VVerifyColVsl006 vcv : list) {
				treeMap.put(vcv.getColumnName(), vcv.getColumnComments());
			}
		}
		return treeMap;
	}

	
	/**
	 * 根据 表（信息集）获取可选的字段（信息项），前提是该信息项配置了可用操作符
	 * 
	 * @author mengl
	 * @param vru004
	 *            或 vsl003 表
	 * @param vsl006sFlag
	 *            是否判断配置的运算符（vsl006s）不为空
	 * @param fliter
	 *            过滤条件
	 * @return
	 * @throws SQLException
	 */
	public static TreeMap<String, String> getVru005byVru0041(String vru004, boolean vsl006sFlag, String fliter) {
		TreeMap<String, String> treeMap = new TreeMap<String, String>();
		//V_Verify_Col_Vsl006改为查询code_table_col lihc
		//String sql = " from VVerifyColVsl006  WHERE id.tableName = '" + vru004 + "' ";
		String sql = "from CodeTableCol a WHERE a.tableCode = '" + vru004 + "'";
		// 1. vsl006sFlag 是否判断 配置的运算符（vsl006s）不为空
		if (vsl006sFlag) {
			sql += " AND trim(vsl006s) IS NOT NULL ";
		}
		// 2. 添加 过滤条件
		if (!StringUtil.isEmpty(fliter)) {
			sql += " And " + fliter;
		}
		//sql += " order by id.tableName";
		sql += " order by a.tableCode";
		@SuppressWarnings("unchecked")
		List<CodeTableCol> list = HBUtil.getHBSession().createQuery(sql).setCacheable(true).list();
		if (list != null && list.size() > 0 && list.get(0) != null) {
			for (CodeTableCol vcv : list) {
				treeMap.put(vcv.getColCode(), vcv.getColName());
			}
		}
//		List<VVerifyColVsl006> list = HBUtil.getHBSession().createQuery(sql).setCacheable(true).list();
//		if (list != null && list.size() > 0 && list.get(0) != null) {
//			for (VVerifyColVsl006 vcv : list) {
//				treeMap.put(vcv.getId().getColumnName(), vcv.getColumnComments());
//			}
//		}
		return treeMap;
	}
	/**
	 * 根据 表（信息集）获取可选的字段（信息项），前提是该信息项配置了可用操作符
	 * 
	 * @author wuh
	 * @param vru004
	 *            或 vsl003 表
	 * @param vsl006sFlag
	 *            是否判断配置的运算符（vsl006s）不为空
	 * @param fliter
	 *            过滤条件
	 * @return
	 * @throws SQLException
	 */
	public static TreeMap<String, String> getVru005byVru004_tj(String vru004, boolean vsl006sFlag, String fliter) {
		TreeMap<String, String> treeMap = new TreeMap<String, String>();
		String sql = " from VVerifyColVsl006 a WHERE a.tableName = '" + vru004 + "' ";
		// 1. vsl006sFlag 是否判断 配置的运算符（vsl006s）不为空
		if (vsl006sFlag) {
			sql += " AND trim(vsl006s) IS NOT NULL ";
		}
		// 2. 添加 过滤条件
		if (!StringUtil.isEmpty(fliter)) {
			sql += " And " + fliter;
		}
		sql += " order by a.tableName";
		@SuppressWarnings("unchecked")
		List<VVerifyColVsl006> list = HBUtil.getHBSession().createQuery(sql).setCacheable(true).list();
		if (list != null && list.size() > 0 && list.get(0) != null) {
			for (VVerifyColVsl006 vcv : list) {
				treeMap.put(vcv.getColumnName(), vcv.getColumnComments());
			}
		}
		return treeMap;
	}

	/**
	 * 根据 表（信息集）获取可选的字段（信息项），前提是该信息项配置了可用操作符
	 * 
	 * @author mengl
	 * @param vru004
	 *            或 vsl003 表
	 * @param vsl006sFlag
	 *            是否判断配置的运算符（vsl006s）不为空
	 * @return
	 * @throws SQLException
	 */
	public static TreeMap<String, String> getVru005byVru004(String vru004, boolean vsl006sFlag) throws SQLException {
		return getVru005byVru004(vru004, vsl006sFlag, null);
	}

	/**
	 * 根据 表（信息集）和字段（信息项）获取可选的操作符（vsl006），前提是该信息项配置了可用操作符
	 * 
	 * @author mengl
	 * @param vru004
	 *            或 vsl003： 表
	 * @param vru005
	 *            或 vsl004 ：字段
	 * @return
	 * @throws SQLException
	 */
	public static String getVsl006(String vru004, String vru005) throws SQLException {
		String sql1 = " from CodeTableCol a WHERE a.tableCode = '" + vru004 + "' and a.colCode = '" + vru005
				+ "' AND trim(vsl006s) IS NOT NULL order by colName";
		@SuppressWarnings("unchecked")
		List<CodeTableCol> list = HBUtil.getHBSession().createQuery(sql1).setCacheable(true).list();
		String vsl006s = "";
		StringBuffer vsl006sStr = new StringBuffer("");
		if (list != null && list.size() > 0 && list.get(0) != null) {
			vsl006s = list.get(0).getVsl006s();
		}
		if (!StringUtil.isEmpty(vsl006s)) {
			String[] vsl006Array = vsl006s.trim().split(",");

			for (int i = 0; i < vsl006Array.length; i++) {
				vsl006sStr.append("'").append(vsl006Array[i]).append("',");
			}
			vsl006s = vsl006sStr.substring(0, vsl006sStr.lastIndexOf(","));
		} else {
			vsl006s = "";
		}

		return vsl006s;
	}

	/**
	 * 获取指定表，字段的数据类型
	 * 
	 * @param tableName
	 * @param colName
	 * @return
	 */
	public static VVerifyColVsl006 getVverifyColVsl006(String tableName, String colName) {
		VVerifyColVsl006 vcv = null;
		HBSession sess = HBUtil.getHBSession();
		// 对字典类型表添加二级缓存，增加查询速度
		@SuppressWarnings("unchecked")
		List<VVerifyColVsl006> list = sess
				.createQuery("from VVerifyColVsl006  WHERE id.tableName =:tableName  and id.columnName =:columnName")
				.setString("tableName", tableName).setString("columnName", colName).setCacheable(true).list();
		if (list != null && !list.isEmpty() && list.get(0) != null) {
			vcv = list.get(0);
		}
		return vcv;
	}

	/**
	 * 获取表字段对应的code_type
	 * 
	 * @return
	 */
	public static CodeTableCol getCodeTableCol(String tabName, String colName) {
		CodeTableCol cr = null;
		String hql = "from CodeTableCol where tableCode=:tabName and colCode=:colName";
		HBSession sess = HBUtil.getHBSession();
		// 对字典类型表添加二级缓存，增加查询速度
		@SuppressWarnings("unchecked")
		List<CodeTableCol> list = sess.createQuery(hql).setString("tabName", tabName).setString("colName", colName)
				.setCacheable(true).list();
		if (list != null && !list.isEmpty()) {
			cr = list.get(0);
		}
		return cr;

	}

	/**
	 * 获取Aa10对象
	 * 
	 * @param aaa100
	 * @param aaa102
	 * @return
	 */
	public static Aa10 getAa10(String aaa100, String aaa102) {
		Aa10 aa10 = null;
		String hql = "from Aa10 where aaa100=:aaa100 and aaa102=:aaa102";
		HBSession sess = HBUtil.getHBSession();
		// 对字典类型表添加二级缓存，增加查询速度
		@SuppressWarnings("unchecked")
		List<Aa10> list = sess.createQuery(hql).setString("aaa100", aaa100 == null ? "" : aaa100.toUpperCase())
				.setString("aaa102", aaa102 == null ? "" : aaa102.toUpperCase()).setCacheable(true).list();
		if (list != null && !list.isEmpty()) {
			aa10 = list.get(0);
		}
		return aa10;
	}

	/**
	 * 获取Aa01对象
	 * 
	 * @param aaa100
	 * @param aaa102
	 * @return
	 */
	public static Aa01 getAa01(String aaa001, String aaa002) {
		Aa01 aa01 = null;
		String hql = "from Aa01 where aaa001=:aaa001 and aaa002=:aaa002";
		HBSession sess = HBUtil.getHBSession();
		// 对字典类型表添加二级缓存，增加查询速度
		@SuppressWarnings("unchecked")
		List<Aa01> list = sess.createQuery(hql).setString("aaa001", aaa001 == null ? "" : aaa001.toUpperCase())
				.setString("aaa002", aaa002 == null ? "" : aaa002.toUpperCase()).list();
		if (list != null && !list.isEmpty()) {
			aa01 = list.get(0);
		}
		return aa01;
	}

	/**
	 * 获取CodeValue对象
	 * 
	 * @param aaa100
	 * @param aaa102
	 * @return
	 */
	public static CodeValue getCodeValue(String codeType, String codeValue, String subCodeValue) {
		CodeValue codeValueObj = null;
		String hql = "from CodeValue where codeType=:codeType and codeValue=:codeValue";
		if (!StringUtil.isEmpty(subCodeValue)) {
			hql = hql + " and subCodeValue = :subCodeValue";
		}
		HBSession sess = HBUtil.getHBSession();
		Query query = sess.createQuery(hql).setString("codeType", codeType == null ? "" : codeType.toUpperCase())
				.setString("codeValue", codeValue == null ? "" : codeValue);
		if (!StringUtil.isEmpty(subCodeValue)) {
			query = query.setString("subCodeValue", subCodeValue);
		}
		// 对字典类型表添加二级缓存，增加查询速度
		@SuppressWarnings("unchecked")
		List<CodeValue> list = query.setCacheable(true).list();
		if (list != null && !list.isEmpty()) {
			codeValueObj = list.get(0);
		}
		return codeValueObj;
	}

	/**
	 * 获取CodeValue对象
	 * 
	 * @param aaa100
	 * @param aaa102
	 * @return
	 */
	public static CodeValue getCodeValue(String codeType, String codeValue) {
		return getCodeValue(codeType, codeValue, null);
	}

	/**
	 * 以Map形式返回所有CodeValue对象
	 * 
	 * @author mengl
	 * @return
	 */
	public static Map<String, String> getCodeValuesMapByCodeType(String codeType) {

		Map<String, String> map = new LinkedHashMap<String, String>();
		if (StringUtil.isEmpty(codeType)) {
			return map;
		}
		@SuppressWarnings("unchecked")
		List<CodeValue> list = HBUtil.getHBSession()
				.createQuery("from CodeValue where codeType =:codeType order by codeLevel,codeValue")
				.setString("codeType", codeType.toUpperCase()).list();
		if (list != null && list.size() > 0) {
			for (CodeValue codeValue : list) {
				map.put(codeValue.getCodeValue(), codeValue.getCodeName());
			}
		}
		return map;
	}

	/**
	 * 以Map形式返回所有CodeTable对象
	 * 
	 * @author mengl
	 * @return
	 */
	public static Map<String, String> getAllCodeTablesMap() {
		Map<String, String> map = new TreeMap<String, String>();
		@SuppressWarnings("unchecked")
		List<CodeTable> list = HBUtil.getHBSession()
				.createQuery(
						"from CodeTable where tableName not in('选调生信息集','考试信息集','公开遴选信息集','进入管理信息集','退出管理信息集','考试录用人员信息集','多媒体信息集') order by tableCode")
				.list();
		if (list != null && list.size() > 0) {
			for (CodeTable codeTable : list) {
				// if(!"拟任免信息集".equals(codeTable.getTableName())){
				map.put(codeTable.getTableCode(), codeTable.getTableName());
				// }
			}
		}
		return map;
	}

	/**
	 * 以Map形式返回所有CodeTable对象
	 * 
	 * @author WUH
	 * @return
	 */
	public static Map<String, String> getAllCodeTablesMap2() {
		Map<String, String> map = new TreeMap<String, String>();
		@SuppressWarnings("unchecked")
		List<CodeTable> list = HBUtil.getHBSession()
				.createQuery("from CodeTable where tableCode not in ('A36','A37','A57') order by tableCode").list();
		if (list != null && list.size() > 0) {
			for (CodeTable codeTable : list) {
				map.put(codeTable.getTableCode(), codeTable.getTableName());
			}
		}
		return map;
	}

	/**
	 * 以Map形式返回所有CodeTable对象
	 * 
	 * @author mengl
	 * @param codeType
	 * @return
	 */
	public static Map<String, String> getAllMapByCodeType(String codeType) {
		return getAllMapByCodeType(codeType, null);
	}

	/**
	 * 以Map形式返回所有CodeTable对象
	 * 
	 * @author mengl
	 * @param codeType
	 * @param filter
	 *            SQL形式的关于Code_Value的过滤条件
	 * @return
	 */
	public static Map<String, String> getAllMapByCodeType(String codeType, String filter) {

		Map<String, String> map = new TreeMap<String, String>();
		if (StringUtil.isEmpty(codeType)) {
			return map;
		}
		HBSession sess = HBUtil.getHBSession();
		StringBuffer sql = new StringBuffer(
				"select code_value,code_name from code_value where code_Type='" + codeType + "' ");

		if (!StringUtil.isEmpty(filter)) {
			sql.append(" and " + filter);
		}
		sql.append(" order by code_Value ");

		@SuppressWarnings("unchecked")
		List<Object[]> list = sess.createSQLQuery(sql.toString()).list();
		if (list != null && list.size() > 0) {
			for (Object[] objs : list) {
				
				map.put((String) objs[0], (String) objs[1]);


			}
		}
		return map;
	}

	/**
	 * 获取表名
	 * 
	 * @param tabCode
	 * @return
	 */
	public static String getTableName(String tabCode) {
		String tabName = "";
		@SuppressWarnings("unchecked")
		List<CodeTable> list = HBUtil.getHBSession().createQuery("from CodeTable where tableCode = '" + tabCode + "'")
				.setCacheable(true).list();
		if (list != null && list.size() > 0 && list.get(0) != null) {
			tabName = list.get(0).getTableName();
		}
		return tabName;
	}

	/**
	 * 获取表名
	 * 
	 * @param tabCode
	 * @return
	 */
	public static String getColName(String tabCode, String colCode) {
		String colName = "";
		@SuppressWarnings("unchecked")
		List<CodeTableCol> list = HBUtil.getHBSession()
				.createQuery("from CodeTableCol where tableCode = '" + tabCode + "' and colCode ='" + colCode + "'")
				.setCacheable(true).list();
		if (list != null && list.size() > 0 && list.get(0) != null) {
			colName = list.get(0).getColName();
		}
		return colName;
	}

	/**
	 * 为VerifySqlList中需要使用数据字典表却不能使用的数据，查询显示值，添加在原已有Map对象中返回
	 * 
	 * @param map
	 *            原已有数据
	 * @param vsl
	 * @return
	 */
	public static Map<String, Object> getCodeNameVerifySqlList(Map<String, Object> map, VerifySqlList vsl) {
		HBSession sess = HBUtil.getHBSession();

		String sql = new StringBuffer(" Select nvl((Select Table_Name	").append("           From Code_Table	")
				.append("          Where Table_Code = '" + vsl.getVsl003() + "'),'') vsl003_name,	")
				.append("        nvl((Select Col_Name	").append("           From Code_Table_Col	")
				.append("          Where Table_Code = '" + vsl.getVsl003() + "'	")
				.append("            And Col_Code = '" + vsl.getVsl004() + "'),'') vsl004_name,	")
				.append("        nvl((Select Table_Name	").append("           From Code_Table	")
				.append("          Where Table_Code = upper('" + vsl.getVsl008() + "')),'') vsl008_name,	")
				.append("        nvl((Select Col_Name	").append("           From Code_Table_Col	")
				.append("          Where Table_Code = '" + vsl.getVsl008() + "'	")
				.append("            And Col_Code = '" + vsl.getVsl009() + "'),'') vsl009_name,	")
				.append("        nvl((Select Aaa103	").append("           From v_Aa10	")
				.append("          Where Aaa102 = '" + vsl.getVsl013() + "'	")
				.append("            And Aaa100 = (Select upper(nvl(Code_Type,'" + vsl.getVsl004() + "'))	")
				.append("                            From Code_Table_Col	")
				.append("                           Where Table_Code = '" + vsl.getVsl003() + "'	")
				.append("                             And Col_Code = '" + vsl.getVsl004() + "')),'') vsl013_name	")
				.append("   From Dual	").toString();
		if (DBUtil.getDBType() == DBType.MYSQL) {
			sql = sql.replace("nvl(", "ifnull(");
		}

		@SuppressWarnings("unchecked")
		List<Object[]> list = sess.createSQLQuery(sql).list();
		if (list != null && list.size() > 0 && list.get(0) != null) {
			Object[] objs = list.get(0);
			map.put("vsl003_name", objs[0]);
			map.put("vsl004_name", objs[1]);
			map.put("vsl008_name", objs[2]);
			map.put("vsl009_name", objs[3]);
			map.put("vsl013_name", objs[4]);
		}
		return map;
	}

	/**
	 * SQL拼接使用：信息集是否存在校验
	 * 
	 * @param tabName
	 * @param colName
	 * @param months
	 * @return
	 */
	public static String sqlExiest(String vsl003, String a01Name, String vsl006Value) {
		String vsl006 = "";
		StringBuffer sql = new StringBuffer();
		if ("80".equals(vsl006Value)) {
			vsl006 = " EXISTS ";
			sql.append(vsl006 + "(SELECT 1 FROM " + vsl003 + " WHERE " + vsl003 + ".a0000 = " + a01Name + ".a0000) ");
		} else if ("81".equals(vsl006Value)) {
			vsl006 = " NOT EXISTS ";
			sql.append(vsl006 + "(SELECT 1 FROM " + vsl003 + " WHERE " + vsl003 + ".a0000 = " + a01Name + ".a0000)");
		}

		return sql.toString();
	}

	/**
	 * SQL拼接使用：身份证错误校验
	 * 
	 * @param tabName
	 * @param colName
	 * @param months
	 * @return
	 */
	public static String sqlA0184CompareToA0104(String a01Name) {
		StringBuffer sql = new StringBuffer();
		sql.append("  Case   ").append("   When Length(A0184) = 18 Then     ")
				.append("   Mod(Substr(A0184, -2, 1), 2)     ").append("   When Length(A0184) = 15 Then     ")
				.append("   Mod(Substr(A0184, -1), 2)       ").append("   Else     ").append("   Null    ")
				.append("   End <> Case ").append("   When A0104 Is Not Null Then ").append("   Mod(A0104, 2)  ")
				.append("   Else    ").append("   Null   ").append("   END  OR   ")
				.append("    (Length(A0184) <> 18 AND   Length(A0184) <> 15)   ");
		/*
		 * .append("   OR      ") .append("   Case    ") .append(
		 * "   When Length(A0184) = 18 Then ") .append("   Case    ") .append(
		 * "   When Length(A0107) = 6 Then   ") .append(
		 * "   Substr(A0184, 7, 6)    ") .append("   Else     ") .append(
		 * "   Substr(A0184, 7, 8)   ") .append("   End     ") .append(
		 * "   When Length(A0184) = 15 Then    ") .append("   Case    ")
		 * .append("   When Length(A0107) = 6 Then   ") .append(
		 * "   Concat('19', Substr(A0184, 7, 6))   ") .append("   Else      ")
		 * .append("   Concat('19', Substr(A0184, 7, 6))      ") .append(
		 * "   End    ") .append("   End <> A0107   ");
		 */

		return sql.toString();
	}

	/**
	 * 身份证重复校验
	 * 
	 * @param a01Name
	 * @return
	 */
	public static String sqlMoreA0184(String a01Name) {
		// return " EXISTS (SELECT 1 FROM "+a01Name+" a WHERE a.A0184 =
		// "+a01Name+".A0184 AND a.a0000!="+a01Name+".a0000) ";
		return a01Name + ".a0184 in ( select a0184 from " + a01Name + " group by a0184 having count(a0184)>1)";

	}

	/**
	 * 出生地非汉字或汉字少于两位校验
	 * 
	 * @param a01Name
	 * @return
	 */
	public static String sqlBirthPlace(String str) {
		return " get_chinese(" + str + ") = '0' ";
	}

	/**
	 * 照片为空
	 * 
	 * @param a01Name
	 * @return
	 */
	public static String sqlExistPhoto(String a01Name) {
		return " not exists (select 1 from a57_temp b," + a01Name + " where b.a0000=" + a01Name + ".a0000) ";
	}

	/**
	 * SQL拼接使用：某表某列添加N月
	 * 
	 * @param tabName
	 * @param colName
	 * @param months
	 * @return
	 */
	public static String sqlStrToDateAddMonths(String tabName, String colName, Long months) {
		StringBuffer sql = new StringBuffer();
		if (DBUtil.getDBType() == DBType.MYSQL) {
			/*
			 * sql.append(" (CASE   ") .append("  When ifnull(Length("
			 * +tabName+"."+colName+"), 0) = 8 Then ") .append(
			 * "   date_add(str_to_date("+tabName+"."+colName+
			 * ", '%Y%m%d'), INTERVAL "+months+" MONTH) ") .append(
			 * "  When ifnull(Length("+tabName+"."+colName+"), 0) = 6 Then ")
			 * .append("   date_add(str_to_date(concat("+tabName+"."+colName+
			 * ",'01'), '%Y%m%d'), INTERVAL "+months+" MONTH) ") .append(
			 * "  When ifnull(Length("+tabName+"."+colName+"), 0) = 4 Then ")
			 * .append("   date_add(str_to_date(concat("+tabName+"."+colName+
			 * ",'0101'), '%Y%m%d'), INTERVAL "+months+" MONTH) ") .append(
			 * "  Else ") .append("   Null ") .append(" END) ");
			 */
			sql.append(
					" date_add(strtodate(" + tabName + "." + colName + ", '%Y%m%d'), INTERVAL " + months + " MONTH)");
		} else {
			/*
			 * sql.append(" (CASE   ") .append("  When Nvl(Length("
			 * +tabName+"."+colName+"), 0) = 8 Then ") .append(
			 * "   Add_Months(To_Date("+tabName+"."+colName+", 'yyyyMMdd'), "
			 * +months+") ") .append("  When Nvl(Length("+tabName+"."+colName+
			 * "), 0) = 6 Then ") .append("   Add_Months(To_Date("
			 * +tabName+"."+colName+", 'yyyyMM'), "+months+") ") .append(
			 * "  When Nvl(Length("+tabName+"."+colName+"), 0) = 4 Then ")
			 * .append("   Add_Months(To_Date(concat("+tabName+"."+colName+
			 * ",'01'), 'yyyyMM'), "+months+") ") .append("  Else ") .append(
			 * "   Null ") .append(" END) ");
			 */
			sql.append(" Add_Months(strtodate(" + tabName + "." + colName + ", 'yyyyMMdd'), " + months + ") ");
		}

		return sql.toString();
	}

	/**
	 * SQL拼接使用：某表某列添加N天
	 * 
	 * @param tabName
	 * @param colName
	 * @param months
	 * @return
	 */
	public static String sqlStrToDateAddDays(String tabName, String colName, Long days) {
		StringBuffer sql = new StringBuffer();
		if (DBUtil.getDBType() == DBType.MYSQL) {
			/*
			 * sql.append(" (CASE   ") .append("  When ifnull(Length("
			 * +tabName+"."+colName+"), 0) = 8 Then ") .append(
			 * "   date_add(str_to_date("+tabName+"."+colName+
			 * ", '%Y%m%d'), INTERVAL "+days+" DAY) ") .append(
			 * "  When ifnull(Length("+tabName+"."+colName+"), 0) = 6 Then ")
			 * .append("   date_add(str_to_date(concat("+tabName+"."+colName+
			 * ",'01'), '%Y%m%d'), INTERVAL "+days+" DAY) ") .append(
			 * "  When ifnull(Length("+tabName+"."+colName+"), 0) = 4 Then ")
			 * .append("   date_add(str_to_date(concat("+tabName+"."+colName+
			 * ",'0101'), '%Y%m%d'), INTERVAL "+days+" DAY) ") .append("  Else "
			 * ) .append("   Null ") .append(" END) ");
			 */
			sql.append(" date_add(strtodate(" + tabName + "." + colName + ", '%Y%m%d'), INTERVAL " + days + " DAY) ");
		} else {
			/*
			 * sql.append(" (CASE   ") .append("  When Nvl(Length("
			 * +tabName+"."+colName+"), 0) = 8 Then ") .append("     To_Date("
			 * +tabName+"."+colName+", 'yyyyMMdd') + "+days+" ") .append(
			 * "  When Nvl(Length("+tabName+"."+colName+"), 0) = 6 Then ")
			 * .append("     To_Date("+tabName+"."+colName+", 'yyyyMM') +"+days+
			 * " ") .append("  When Nvl(Length("+tabName+"."+colName+
			 * "), 0) = 4 Then ") .append("     To_Date(concat("
			 * +tabName+"."+colName+",'01'), 'yyyyMM') + "+days+" ") .append(
			 * "  Else ") .append("   Null ") .append(" END) ");
			 */
			sql.append(" date_add(strtodate(" + tabName + "." + colName + ", 'yyyyMMdd'), INTERVAL " + days + " DAY) ");
		}

		return sql.toString();
	}

	/**
	 * SQL拼接使用：某表某列转为date
	 * 
	 * @param tabName
	 * @param colName
	 * @param months
	 * @return
	 */
	public static String sqlStrToDate(String tabName, String colName) {
		StringBuffer sql = new StringBuffer();
		if (DBUtil.getDBType() == DBType.MYSQL) {
			/*
			 * sql.append(" (CASE   ") .append("  When ifnull(Length("
			 * +tabName+"."+colName+"), 0) = 8 Then ") .append(
			 * "    str_to_date("+tabName+"."+colName+", '%Y%m%d') ") .append(
			 * "  When ifnull(Length("+tabName+"."+colName+"), 0) = 6 Then ")
			 * .append("    str_to_date(concat("+tabName+"."+colName+
			 * ",'01'), '%Y%m%d') ") .append("  When ifnull(Length("
			 * +tabName+"."+colName+"), 0) = 4 Then ") .append(
			 * "    str_to_date(concat("+tabName+"."+colName+
			 * ",'0101'), '%Y%m%d') ") .append("  Else ") .append("   Null ")
			 * .append(" END) ");
			 */
			sql.append("   strtodate(" + tabName + "." + colName + ", '%Y%m%d') ");
		} else {
			/*
			 * sql.append(" (CASE   ") .append("  When Nvl(Length("
			 * +tabName+"."+colName+"), 0) = 8 Then ") .append("    To_Date("
			 * +tabName+"."+colName+", 'yyyyMMdd') ") .append(
			 * "  When Nvl(Length("+tabName+"."+colName+"), 0) = 6 Then ")
			 * .append("    To_Date("+tabName+"."+colName+", 'yyyyMM') ")
			 * .append("  When Nvl(Length("+tabName+"."+colName+
			 * "), 0) = 4 Then ") .append("    To_Date(concat("
			 * +tabName+"."+colName+",'01'), 'yyyyMM') ") .append("  Else ")
			 * .append("   Null ") .append(" END) ");
			 */
			sql.append("   strtodate(" + tabName + "." + colName + ", 'yyyyMMdd') ");
		}

		return sql.toString();
	}

	/**
	 * SQL拼接使用：字符转日期
	 * 
	 * @param Str
	 * @return
	 */
	public static String sqlStrToDate(String Str) {
		StringBuffer sql = new StringBuffer();
		if (!StringUtil.isEmpty(Str)) {
			if (DBUtil.getDBType() == DBType.MYSQL) {
				/*
				 * sql.append(" (CASE   ") .append("  When ifnull(Length('"+Str+
				 * "'), 0) = 8 Then ") .append("    str_to_date('"+Str+
				 * "', '%Y%m%d') ") .append("  When ifnull(Length('"+Str+
				 * "'), 0) = 6 Then ") .append("    str_to_date(concat('"+Str+
				 * "','01'), '%Y%m%d') ") .append("  When ifnull(Length('"+Str+
				 * "'), 0) = 4 Then ") .append("    str_to_date(concat('"+Str+
				 * "','0101'), '%Y%m%d') ") .append("  Else ") .append(
				 * "   Null ") .append(" END) ");
				 */
				sql.append("     strtodate('" + Str + "', '%Y%m%d')  ");

			} else {
				/*
				 * sql.append(" (CASE   ") .append("  When Nvl(Length('"+Str+
				 * "'), 0) = 8 Then ") .append("    To_Date('"+Str+
				 * "', 'yyyyMMdd') ") .append("  When Nvl(Length('"+Str+
				 * "'), 0) = 6 Then ") .append("    To_Date('"+Str+
				 * "', 'yyyyMM') ") .append("  When Nvl(Length('"+Str+
				 * "'), 0) = 4 Then ") .append("    To_Date(concat('"+Str+
				 * "','01'), 'yyyyMM') ") .append("  Else ") .append("   Null ")
				 * .append(" END) ");
				 */
				sql.append("     strtodate('" + Str + "', 'yyyyMMdd')  ");
			}

		} else {
			return null;
		}

		return sql.toString();
	}

	/**
	 * 检查sql是否能够正常执行
	 * 
	 * @param sql
	 * @return
	 */
	public static String testSql(String sql) {
		String errorInfo = null;
		HBSession sess = HBUtil.getHBSession();
		Connection conn = sess.connection();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.execute();
		} catch (SQLException e) {
			errorInfo = "查询语句有误,请检查配置:" + e.getMessage();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return errorInfo;
	}

	/**
	 * 更新信息校验方案（Scheme）的名称，备注。 注：是否是基础默认校验方案仅admin用户可修改
	 * 
	 * @param schemeGrid
	 * @param allUpdate
	 *            是否更新全部的grid行：true-更新全部行，false-更新勾选行
	 * @return 更新成功与否信息
	 * @throws AppException
	 */
	public static String saveUpdateSchemeGrid(List<HashMap<String, Object>> schemeGrid, boolean allUpdate)
			throws AppException {
		StringBuffer msg = new StringBuffer("保存成功！"); // 返回信息
		int checkCount = 0; // 勾选数量
		int vsc007Count = 0; // 基础默认方案为True的数量
		VerifyScheme vs = null; // 校验方案
		VerifySchemeDTO vsDTO = null; // 记录日志使用数据传输对象
		String userid = SysUtil.getCacheCurrentUser().getUserVO().getLoginname(); // 当前用户信息
		if (schemeGrid == null || schemeGrid.size() < 1) {
			throw new AppException("保存失败：没有需要保存的数据！");
		}
		HBSession sess = HBUtil.getHBSession();

		try {
			for (HashMap<String, Object> map : schemeGrid) {
				Boolean mcheck = (map.get("mcheck") != null && map.get("mcheck").toString().equalsIgnoreCase("true"))
						? true : false;
				if (mcheck || allUpdate) {
					checkCount++;
					String vsc001 = (String) map.get("vsc001");
					vs = (VerifyScheme) sess.get(VerifyScheme.class, vsc001);

					if (vs != null) {
						if ("1".equals(vs.getVsc007()) && !userid.equalsIgnoreCase("admin")) {
							throw new AppException("基础默认方案仅超级管理（admin）可以修改！");
						}
						vsDTO = new VerifySchemeDTO();
						BeanUtil.copy(vs, vsDTO);
						vs.setVsc002((String) map.get("vsc002"));
						vs.setVsc009((String) map.get("vsc009"));
						String vsc007 = (String) map.get("vsc007");
						if (!StringUtil.isEmpty(userid) && userid.equalsIgnoreCase("admin")
								&& !StringUtil.isEmpty(vsc007)) {
							vs.setVsc007(vsc007);
							if ("1".equals(vsc007)) {
								// 默认基础方案只能有一条
								String sql = "update verify_scheme  set  vsc007 = '0' where vsc001 <> '" + vsc001 + "'";
								sess.createSQLQuery(sql).executeUpdate();
								vsc007Count++;
							}
						}
						sess.update(vs);

						List<String[]> list = Map2Temp.getLogInfo(vsDTO, vs);
						if (list == null || list.size() < 1) {
							msg.append("\n【" + vs.getVsc002() + "】方案未作任何修改。");
						} else {
							// 记录日志
							try {
								if (vs != null) {
									new LogUtil().createLog("633", "VERIFY_SCHEME", vsc001, vs.getVsc002(), null, list);
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}

					} // end not null
				} // end check
			} // end for
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("保存失败！异常信息：" + e.getMessage());
		}

		if (!allUpdate && checkCount < 1) {
			throw new AppException("保存失败！请勾选要修改的信息！");
		}

		if (vsc007Count > 1) {
			throw new AppException("保存失败！基础默认方案数量不能多于一条！");
		}

		return msg.toString();
	}

	/**
	 * 停用校验方案
	 * 
	 * @param vsc001s
	 * @throws AppException
	 */
	public static void stopSchemeByVsc001(String vsc001) throws AppException {
		/*
		 * if(vsc001s==null || vsc001s.size()<1){ throw new
		 * AppException("获取校验方案为空！"); }
		 */
		if (StringUtil.isEmpty(vsc001)) {
			throw new AppException("获取校验方案为空！");
		}
		HBSession sess = HBUtil.getHBSession();
		/*
		 * for(String vsc001 :vsc001s){ VerifyScheme vs = (VerifyScheme)
		 * sess.get(VerifyScheme.class, vsc001); if(vs==null ||
		 * "0".equals(vs.getVsc003())){ throw new AppException("请选择已启用的方案！"); }
		 * }
		 */
		/* for(String vsc001 :vsc001s){ */
		// sess.createQuery("update VerifyRule a set vru007 = '0' where a.vsc001
		// ='"+vsc001+"'").executeUpdate();
		VerifyScheme vs = (VerifyScheme) sess.get(VerifyScheme.class, vsc001);
		if (vs != null) {
			VerifySchemeDTO vsDTO = new VerifySchemeDTO();
			BeanUtil.copy(vs, vsDTO);

			vs.setVsc003("0");
			sess.update(vs);

			// 记录日志
			try {
				new LogUtil().createLog("636", "VERIFY_SCHEME", vsc001, vs.getVsc002(), null,
						Map2Temp.getLogInfo(vsDTO, vs));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/* } */

	}

	/**
	 * @author 01322 2016/11/9 统计关系所在单位比较a0201
	 * @param a01Name
	 * @return
	 */
	public static String sqlA0195CompareToA0201(String a01Name) {
		return " A01_temp.a0195 <> all " + "(select a02_temp.a0201b" + "        from a02_temp" + "        where"
				+ "        a02_temp.a0000 = a01_temp.a0000)  and a01_temp.a0163='1'";

	}

	/**
	 * @author 01322 2016/11/20 进入本单位变动类别为调入，并且公开遴选，公开选调同时为空校验。
	 * @param a01tabname
	 * @return
	 */
	public static String sqlA2911Compare(String a01tabname) {

		return " select  count(1) from a29_temp  " + "left join a62_temp  on a29_temp.a0000 = a62_temp.a0000 "
				+ "left join a63_temp  on a29_temp.a0000 = a63_temp.a0000 " + "where a29_temp.a2911 like '1%' "
				+ "and (a62_temp.a2950 is null or a62_temp.a2950='') "
				+ "and (a63_temp.a2951 is null or a63_temp.a2951='') " + "AND A29_temp.A0000 = :A0000 ";
	}

	/**
	 * 职务层次高于任职机构级别（提醒）
	 * 
	 * @author zhao
	 * @param a01tabname
	 * @return
	 */
	public static String a0207CompareToA0148(String a01tabname) {

		return "select count(1) from A01_temp,A02_temp,B01_temp WHERE 1=1  "
				+ "AND  A01_temp.A0000 = :A0000 AND A01_temp.A0000 = A02_temp.A0000 AND A01_temp.A0148 = A02_temp.A0221 AND B01_temp.B0111 = A02_temp.A0201b AND( "
				+ "case A01_temp.A0148 " + "when '0101' then 1 " + "when '0102' then 2 " + "when '0111' then 3 "
				+ "when '0112' then 4 " + "when '0121' then 5 " + "when '0122' then 6 " + "when '0131' then 7 "
				+ "when '0132' then 8 " + "when '0141' then 9 " + "when '0142' then 10 " + "else null " + "end " + "> "
				+ "case B01_temp.B0127 " + "when '101' then 1 " + "when '102' then 2 " + "when '103A' then 3 "
				+ "when '104A' then 3 " + "when '103B' then 4 " + "when '104B' then 4 " + "when '105' then 5 "
				+ "when '106' then 6 " + "when '107' then 7 " + "when '108' then 8 " + "when '109' then 9 "
				+ "when '110' then 10 " + "else null " + "end ";
	}

	public static String sqlA0281Exist(String a01TabName) {
		return a01TabName + ".a0000 not in (select a02_temp.a0000 from a02_temp where a02_temp.a0281='true')";
	}

	public static String sqlA0279Exist(String a01TabName) {

		return a01TabName
				+ ".a0000 not in (select a02_temp.a0000 from a02_temp where a02_temp.a0279='1' and a02_temp.a0281='true' group by a02_temp.a0000 having count(a02_temp.a0000)=1) and "
				+ a01TabName + ".a0163='1'";
	}

	// 家庭成员无父母
	public static String haveParent() {
		return " (SELECT count(*) FROM a36_temp WHERE a3604A = '父亲' AND a36_temp.a0000 = a01_temp.a0000) < 1 or (SELECT count(*) FROM a36_temp WHERE a3604A = '母亲' AND a36_temp.a0000 = a01_temp.a0000) < 1 ";

	}

	// 公务员参公无对应职务
	public static String a0160error() {
		return " a0160 in ('1','2','3','5','6') and (select count(*) from a02_temp where a02_temp.a0000 = a01_temp.a0000 and a0222 = '01') < 1 ";
	}

	// 机构编码重复
	public static String b0114echo(String b01Name) {
		return " b0114 in(select b0114 from " + b01Name + " group by b0114 having count(b0114)>1)";
	}

	// 统计关系所在单位为内设机构
	public static String a0195isThree() {
		return "select count(1) from A01_temp,B01_temp WHERE 1=1 AND  A01_temp.A0000 = :A0000 AND  A01_temp.a0195=B01_temp.b0111 and B01_temp.b0194='2'";
	}

	public static String a0837NOONE() {
		return "a0000 not in (select a0000 from a08_temp where a0837='1') ";
	}

	public static String b0150error() {
		return "  b0150<>b0183+b0185 and b0194='2'";

	}

	/**
	 * 以Map形式返回所有b01机构对象
	 * 
	 * @author mengl
	 * @return
	 */
	public static Map<String, String> getAllB01Map() {
		Map<String, String> map = new TreeMap<String, String>();
		@SuppressWarnings("unchecked")
		List<B01> list = HBUtil.getHBSession().createQuery("from B01 order by b0111").list();
		if (list != null && list.size() > 0) {
			for (B01 b01 : list) {
				// if(!"拟任免信息集".equals(codeTable.getTableName())){
				map.put(b01.getB0111(), b01.getB0101());
				// }
			}
		}
		return map;
	}

	public static String baohanSPK(String vsl004) {// 包含数字,英文,括号
		if (DBUtil.getDBType() == DBType.MYSQL) {
			return " ( (REPLACE (REPLACE (REPLACE (" + vsl004 + ", ' ', ''),'　',''),'·','') regexp '[0-9]')"
					+ "OR  (REPLACE (REPLACE (REPLACE (" + vsl004 + ", ' ', ''),'　',''),'·','') regexp '[a-zA-Z]')"
					+ "	OR REPLACE (REPLACE (REPLACE (" + vsl004 + ", ' ', ''),'　',''),'·','') LIKE '%（%'"
					+ "	OR REPLACE (REPLACE (REPLACE (" + vsl004 + ", ' ', ''),'　',''),'·','') LIKE '%）%')";
		} else if (DBUtil.getDBType() == DBType.ORACLE) {

			return " (regexp_like(replace(replace(replace(" + vsl004 + ",' ',''),'　',''),'·',''),'[0-9]') "
					+ "or regexp_like(replace(replace(replace(" + vsl004 + ",' ',''),'　',''),'·',''),'[a-zA-Z]') "
					+ "or replace(replace(replace(" + vsl004 + ",' ',''),'　',''),'·','') like '%（%' "
					+ "or replace(replace(replace(" + vsl004 + ",' ',''),'　',''),'·','') like '%）%')";
		} else {
			return null;
		}

	}

	public static String unmapCode(String vsl004) {// 不能映射至代码表

		return vsl004 + " IS NOT NULL " + "AND " + vsl004 + " NOT IN (SELECT code_value FROM code_value "
				+ "WHERE code_type in (select code_type from code_table_col where col_code = '" + vsl004 + "')) ";
	}

	public static String Timewrong(String vsl004) {// 时间格式错误

		return vsl004 + " IS NOT NULL AND isdate (" + vsl004 + ") = 0";
	}

	public static String lessTTCC(String vsl004) {// 小于等于三个汉字
		return "length(" + vsl004 + ")<4 and " + vsl004 + " not like '%北京%' and " + vsl004 + " not like '%天津%' "
				+ "and " + vsl004 + " not like '%上海%' and " + vsl004 + " not like '%重庆%' ";
	}

	public static String nowZWCC(String vsl004) {// 现职务层次-现职务层次选择错误
		return vsl004 + " IN ( SELECT code_value FROM code_value WHERE code_type IN "
				+ "( SELECT code_type FROM code_table_col WHERE col_code = '" + vsl004 + "' ) "
				+ "AND ( sub_code_value = '-1' OR sub_code_value = '1' ) )";
	}

	public static String notGWY(String vsl004) {// 现职务层次-出现非公务员职务层次
		return " ("+vsl004 + " like '9%' or " + vsl004 + " like 'C%' or " + vsl004 + " like 'D%' or "
				+ vsl004 + " like 'E%' or " + vsl004 + " like 'F%' or " + vsl004 + " like 'G%')";
	}

	public static String duiyingRZ(String vsl004) {// 现职务层次-请检查任免审批表(一)右侧职务层次与该职务层次明细是否对应
		return vsl004 + " is null and a0000  in (select a0000 from a05 where a0531='0' and a0524='1')";
	}

	public static String duiyingRZJ(String vsl004) {// 现职务层次-请检查任免审批表(一)右侧职务层次与该职务层次明细是否对应(2)
		return vsl004 + " is null and a0000  in (select a0000 from a05 where a0531='1' and a0524='1')";
	}

	public static String duiyingRZ1(String vsl004) {// 现职务层次-请检查任免审批表(一)右侧职务层次与该职务层次明细是否对应
		return vsl004 + " is not  null and a0000 not in (select a0000 from a05 where a0531='0' and a0524='1')";
	}

	public static String duiyingRZJ1(String vsl004) {// 现职务层次-请检查任免审批表(一)右侧职务层次与该职务层次明细是否对应(2)
		return vsl004 + " is not  null and a0000 not in (select a0000 from a05 where a0531='1' and a0524='1')";
	}

	public static String gaoyuZCJ(String vsl004) {// 现职级-现职级应高于现职务层次(正处级)
		return vsl004 + " in ('25','24','23','22') and " + vsl004 + " in ('1A32','21')";
	}

	public static String gaoyuFCJ(String vsl004) {// 现职级-现职级应高于现职务层次(副处级)
		return vsl004 + " in ('25','24','23') and " + vsl004 + " in ('1A41','22')";
	}

	public static String gaoyuZKJ(String vsl004) {// 现职级-现职级应高于现职务层次(正科级)
		return vsl004 + " in ('25','24') and " + vsl004 + " in ('1A42','23','24')";
	}

	public static String gaoyuFKJ(String vsl004) {// 现职级-现职级应高于现职务层次(副科级)
		return vsl004 + "='25' and " + vsl004 + " in ('1A50','25')";
	}

	public static String wuXZJ(String vsl004) {// 现职级-现职务层次正处级以上应无现职级（试点省份除外）
		return vsl004 + " is not null and " + vsl004 + " in ('1A01','1A02','1A11','1A12','1A22','1A21','1A31','20')";
	}

	public static String weiTXZZW() {// 简历-主职务任职时间在简历中未体现
		return "select count(1) from A01_temp,A02_temp where A01_temp.A0000 = :A0000"
				+ " and A02_temp.A0000=A01_temp.A0000 and A02_temp.a0279='1' and (A02_temp.A0255='1' or A02_temp.A0281='true') "
				+ "and A01_temp.a1701 not like concat(concat('%',concat(concat(substr(A02_temp.a0243,1,4),'.'),substr(A02_temp.a0243,5,2))),'%')";
	}

	public static String tongjiBSFR() {// 统计关系所在单位-不是法人单位
		return "select count(1) from A01_temp,B01_temp where A01_temp.A0000 = :A0000 AND A01_temp.A0195=B01_temp.B0111 and B0194!='1'";
	}

	public static String tongjiBCZ(String vsl004) {// 统计关系所在单位-单位不存在
		return vsl004 + " is not null and " + vsl004 + " not in (select b0111 from B01_temp)";
	}

	public static String baohanYK(String vsl004) {// 包含英文或空格
		return "(" + vsl004 + " like '% %' or " + vsl004 + " like '%　%' or(" + vsl004 + " regexp '[a-zA-Z]'))";
	}

	public static String SCZW() {// 任职机构-输出职务的任职机构在机构树不存在
		return "not exists " + "(select 1 from a02_temp,a01_temp where a02_temp.a0000=a01_temp.a0000 and a0281='true' "
				+ "and a0201b in(select b0111 from b01_temp where b0111!='-1') )";
	}

	public static String ZWRZ() {// 任职时间-最晚任职时间早于职务层次时间
		return "SELECT count(1) FROM a01_temp, " + "( SELECT a02_temp.A0000, max(a02_temp.a0243) a0243 FROM  a02_temp "
				+ "WHERE A02_temp.A0000 = :A0000  AND a02_temp.a0255 = '1' GROUP BY a02_temp.A0000 ) a02_temp "
				+ "WHERE  a01_temp.A0000 = a02_temp.A0000 AND substr(a01_temp.A0288, 1, 6) > substr(a02_temp.a0243, 1, 6)";
	}

	public static String ZWCZ() {// 职务层次(职级)-不能映射至代码表(职务层次)
		return "select count(1) from A01_temp,A05_temp where A05_temp.A0000 = :A0000 AND A01_temp.A0000=A05_temp.A0000 "
				+ "and A0531='0' and A0501b is not null and A0501b not in (select code_value from code_value where code_type='ZB09')";
	}

	public static String ZWCZ1() {// 职务层次(职级)-不能映射至代码表(职级)
		return "select count(1) from A01_temp,A05_temp where A05_temp.A0000 = :A0000 AND A01_temp.A0000=A05_temp.A0000 "
				+ "and A0531='1' and A0501b is not null and A0501b not in (select code_value from code_value where code_type='ZB148')";
	}

	public static String CXLG() {// 职务层次(职级)-出现两个或以上在任的职务层次
		return "  A05_temp.a0000 in (select a0000 from a05_temp where A0531='0' and A0524='1' group by a0000 having count(a0000)>1)";
	}

	public static String a0834NOONE() {// 无最高学历
		return " a0000 not in (select a0000 from a08_temp where a0834='1') ";
	}

	public static String ND2014() {// 考核年度-2014年考核结果未填写
		if (DBUtil.getDBType() == DBType.MYSQL) {
			return " STRTODATE(a2949,'%Y%m%d')<=STRTODATE('20141231','%Y%m%d') "
					+ "AND A15Z101 not like '%2014%' and a0165='09'";
		} else {
			return " STRTODATE(a2949,'yyMMdd')<=STRTODATE('20141231','yyMMdd') "
					+ "AND A15Z101 not like '%2014%' and a0165='09'";
		}
	}

	public static String ND2015() {// 考核年度-2015年考核结果未填写
		if (DBUtil.getDBType() == DBType.MYSQL) {
			return " STRTODATE(a2949,'%Y%m%d')<=STRTODATE('20151231','%Y%m%d') "
					+ "AND A15Z101 not like '%2015%' and a0165='09'";
		} else {
			return " STRTODATE(a2949,'yyMMdd')<=STRTODATE('20151231','yyMMdd') "
					+ "AND A15Z101 not like '%2015%' and a0165='09'";
		}
	}

	public static String ND2016() {// 考核年度-2016年考核结果未填写
		if (DBUtil.getDBType() == DBType.MYSQL) {
			return /*
					 * "select count(1) from A01_temp WHERE 1=1 AND  A01_temp.A0000 = :A0000 "
					 * + "AND
					 */" STRTODATE(a2949,'%Y%m%d')<=STRTODATE('20161231','%Y%m%d') "
					+ "AND A15Z101 not like '%2016%' and a0165='09'";
		} else {
			return " STRTODATE(a2949,'yyMMdd')<=STRTODATE('20161231','yyMMdd') "
					+ "AND A15Z101 not like '%2016%' and a0165='09'";
		}
	}

	public static String queshaoFM() {// 家庭成员称谓-缺少父亲或母亲信息
		return /* "select count(1) from A01_temp where A01_temp.A0000 = :A0000 AND" */
		"((select count(*) from A36_temp where A3604A in ('父亲','继父','养父') and A01_temp.A0000=A36_temp.A0000)<1 "
				+ "or (select count(*) from A36_temp where A3604A in ('母亲','继母','养母') and A01_temp.A0000=A36_temp.A0000)<1)";
	}

	public static String chuxianDGFM() {// 家庭成员称谓-出现多个父亲或母亲信息
		return /* "select count(1) from A01_temp where A01_temp.A0000 = :A0000 AND " */
		"((select count(*) from A36_temp where A3604A ='父亲' and A01_temp.A0000=A36_temp.A0000)>1 "
				+ "or (select count(*) from A36_temp where A3604A ='母亲' and A01_temp.A0000=A36_temp.A0000)>1)";
	}

	public static String buFHBM() {// 机构编码-不符合编码要求
		if (DBUtil.getDBType() == DBType.MYSQL) {
			return "B0111!='-1' and not (trim(B0114) regexp '^[0-9a-zA-Z]{3}(.{1}[0-9a-zA-Z]{3}){0,}$')";
		} else {
			return "B0111!='-1' and not regexp_like(trim(B0114),'^[0-9a-zA-Z]{3}(.{1}[0-9a-zA-Z]{3}){0,}$')";
		}
	}

	public static String feiZXS() {// 单位所在政区-非直辖市地区隶属关系出现直辖市辖区
		return " B0117 not like '110%' "
				+ "and B0117 not like '120%' and B0117 not like '310%' and B0117 not like '500%' and B0124='5'";
	}

	public static String feiFSJ() {// 单位所在政区-无副省级市地区隶属关系出现副省级市
		return "(B0117 not like '2301%' AND B0117 not like '2201%' AND B0117 not like '2101%' "
				+ "AND B0117 not like '2102%' AND B0117 not like '3701%' AND B0117 not like '3702%' "
				+ "AND B0117 not like '3201%' AND B0117 not like '3301%' AND B0117 not like '3302%' "
				+ "AND B0117 not like '3502%' AND B0117 not like '4401%' AND B0117 not like '4403%' "
				+ "AND B0117 not like '4201%' AND B0117 not like '5101%' AND B0117 not like '6101%') AND B0124 like '3%'";
	}

	public static String bushiGWY() {// 机构类别-不是公务员或参照单位的机构
		return "B0194='1' and b0111!='-1' and B0131 not like '1%' and B0131 not like '3%' and B0131 not like '4%'";
	}

	public static String baohanRZJG() {// 职务名称-职务名称包含任职机构
		return "select count(1) from a01_temp,a02_temp where A02_temp.A0000 = :A0000 AND a01_temp.A0000=a02_temp.A0000 "
				+ " and a02_temp.a0215a like concat(concat('%',a02_temp.a0201a),'%') "
				+ "and (A02_temp.A0255='1' or A02_temp.A0281='true')";
	}

	public static String xiaoyu(String vsl004, String vsl007) {//小于n个字
		if (DBUtil.getDBType() == DBType.MYSQL) {
			return " char_length(" + vsl004 + ")<=" + vsl007 + " and length(" + vsl004 + ")>0 ";
		} else {
			return " length(" + vsl004 + ")<=" + vsl007 + " and length(" + vsl004 + ")>0 ";
		}
	}
}
