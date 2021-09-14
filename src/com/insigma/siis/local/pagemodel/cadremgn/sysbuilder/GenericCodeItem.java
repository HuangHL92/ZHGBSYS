/*
 * Created on 2004-9-9
 *
 */
package com.insigma.siis.local.pagemodel.cadremgn.sysbuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;


/**
 * @author 昃鹏 加载代码项
 * 
 */
public class GenericCodeItem {

	//public static final String module = GenericCodeItem.class.getName();

	protected static Map codeItemCache = new HashMap();;

	protected Map codeItemMap = new HashMap();;


	/**
	 * 获得实例
	 * 
	 * @return 返回实例
	 */
	public static GenericCodeItem getGenericCodeItem() {
		GenericCodeItem codeItem = (GenericCodeItem) codeItemCache.get("default");
		if (codeItem == null) {
			System.out.println("############Warning################:!!!! CodeItem is reset new!");
			synchronized (GenericCodeItem.class) {
				try {
					codeItem = new GenericCodeItem();
				} catch (SQLException e) {
					System.out.println("Error creating delegator");
				}
				codeItemCache.put("default", codeItem);
			}
			System.out.println("############Warning################:!!!! CodeItem is reset Over!");
		}

		return codeItem;

	}
	/**
	 * 重构代码缓存
	 * @return
	 */
	public static GenericCodeItem reBuildGenericCodeItem() {
		codeItemCache.remove("default");
		return getGenericCodeItem();
	}

	protected GenericCodeItem() throws SQLException {
		HBSession hbsess = HBUtil.getHBSession();	
		Connection connection = hbsess.connection();
		Statement stmt = connection.createStatement(); 
		String viewSql = "select code_type,code_value,code_name,sub_code_value,code_name2,code_name3 from code_value "
				+ "union select 'B01' code_type, b0111 code_value,b0101 code_name,b0121 sub_code_value,b0104 code_name2,b0107 code_name3 from b01 "
				+ "union select 'SYSUSER' code_type,userid code_value,username code_name,userid sub_code_value,username code_name2,username code_name3 from smt_user"
				+ " order by code_type,code_value";
		ResultSet rs = stmt.executeQuery(viewSql);
		try {

		      while (rs.next()) {
		    	  String code_type = rs.getString("code_type");
		    	  String code_value = rs.getString("code_value");
		    	  String code_name = rs.getString("code_name");
		    	  String code_name2 = rs.getString("code_name2");
		    	  String code_name3 = rs.getString("code_name3");
		    	  String sub_code_value = rs.getString("sub_code_value");
					Object obj = codeItemMap.get(code_type);
					Map<String,Object> codeItem;
					if (obj != null) {
						codeItem =  (Map<String, Object>) obj;
					}else {
						codeItem = new HashMap();
					}
					Map<String,String> description = new HashMap();
					description.put("code_name", code_name);
					description.put("code_name2", code_name2);
					description.put("code_name3", code_name3);
					codeItem.put(code_value, description);
					if (code_name != null && !"".equals(code_name)) {
						codeItem.put(code_type + ":code_name" + code_name, code_value);
					}
					if (code_name2 != null && !"".equals(code_name2)) {
						codeItem.put(code_type + ":code_name2" + code_name2, code_value);
					}
					if (code_name3 != null && !"".equals(code_name3)) {
						codeItem.put(code_type + ":code_name3" + code_name3, code_value);
					}
					Map subCode;
					if(codeItem.containsKey(sub_code_value)){
						subCode = (Map) codeItem.get(sub_code_value);
					}else {
						subCode = new HashMap();
					}
					List<String> childs;
					if(subCode.containsKey("childs")) {
						childs = (List<String>) subCode.get("childs");
					}else {
						childs = new ArrayList<String>();
					}
					childs.add(code_value);
					subCode.put("childs",childs);
					codeItem.put(sub_code_value, subCode);
					codeItemMap.put(code_type,codeItem);
		      }

						Object obj = codeItemMap.get("tableOfCodeType");
						Map<String,Object> codeItem;
						if (obj != null) {
							codeItem =  (Map<String, Object>) obj;
						}else {
							codeItem = new HashMap();
						}
				viewSql = "select code_type,col_code,table_code from code_table_col where code_type is not null";
				rs = stmt.executeQuery(viewSql);

			      while (rs.next()) {
			    	  String code_type = rs.getString("code_type");
			    	  String col_code = rs.getString("col_code").toUpperCase();
			    	  String table_code = rs.getString("table_code").toUpperCase();
						codeItem.put(table_code+"."+col_code, code_type);
			      }
				codeItemMap.put("tableOfCodeType",codeItem);
		} catch (Exception e) {

		} finally {
			stmt.close();
			connection.close();
		}
	}

	/**
	 * 获得中文描述
	 * 
	 * @param codeID
	 *            代码
	 * @param colValue
	 *            值
	 * @return 返回描述
	 */
	public String getDescription(String codeTypeOrColumn, String colValue, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String code_name = (String)session.getAttribute("code_name");
		if (code_name == null || "".equals(code_name)) {
			code_name = "code_name";
		}
		if(!codeItemMap.containsKey(codeTypeOrColumn)&&codeItemMap.containsKey("tableOfCodeType")) {
			Map tableOfCodeType = (Map) codeItemMap.get("tableOfCodeType");
			codeTypeOrColumn = (String) tableOfCodeType.get(codeTypeOrColumn.toUpperCase());
		}
		if (codeItemMap.get(codeTypeOrColumn) == null) {
			return colValue;
		}
		Map<String, Map<String, String>> map = (Map) codeItemMap.get(codeTypeOrColumn);
		if (map.get(colValue) == null) {
			return colValue;
		} else {
			Map<String, String> codeDescs = (Map<String, String>) map.get(colValue);
			String description = codeDescs.get(code_name);
			if (description == null) {
				description = codeDescs.get("code_name");
			}
			if (description == null) {
				description = "";
			}
			return description;
		}
	}

	/**
	 * 获得code 
	 * 
	 * @param codeID
	 *            代码
	 * @param colValue
	 *            值
	 * @return 返回描述
	 */
	public Object getCode(String codeTypeOrColumn, Object colDescValue,HttpServletRequest request) {
		HttpSession session = request.getSession();
		String code_name = (String)session.getAttribute("code_name");
		if (code_name == null || "".equals(code_name)) {
			code_name = "code_name";
		}
		String flag = "";
		if(!codeItemMap.containsKey(codeTypeOrColumn)&&codeItemMap.containsKey("tableOfCodeType")) {
			flag = codeTypeOrColumn;
			Map tableOfCodeType = (Map) codeItemMap.get("tableOfCodeType");
			codeTypeOrColumn = (String) tableOfCodeType.get(codeTypeOrColumn.toUpperCase());
		}else {
			return colDescValue;
		}
		if(codeTypeOrColumn == null ||flag.equals(codeTypeOrColumn)) {
			return colDescValue;
		}
		if (codeItemMap.get(codeTypeOrColumn) == null) {
			return "";
		}
		Map c = (Map) codeItemMap.get(codeTypeOrColumn);
		return (c.containsKey(codeTypeOrColumn + ":" + code_name + colDescValue))?(String)c.get(codeTypeOrColumn + ":" + code_name + colDescValue):"";
	}

	/**
	 * 根据代码获得所有值
	 * 
	 * @param codeID
	 *            代码
	 * @return 返回值
	 */
	public Map getCodeItem(String codeID) {
		String code = codeID;
		Map map = (Map) codeItemMap.get(codeID);
		return map;
	}
/*
	public static Collection getChildCode(Map<String, String> childs, String sortDirection) {
		Collection collection = new ArrayList();
		for (Map.Entry<String, String> child : childs.entrySet()) {
			Map mapValue = new HashMap();
			CodePojo codePojo = codePojoCache.get(child.getKey());
			String description = codePojo.getDescription();
			String codeId = codePojo.getCodeId();
			String code = codePojo.getCode();
			String hide = codePojo.getHide();
			Map<String, String> mapChilds = codePojo.getChilds();
			String viewSerials = code;// codePojo.getCodeOrder();

			String cptr = "0";
			if (mapChilds.size() > 0) {
				cptr = "1";
			}
			mapValue.put("description", description);
			mapValue.put("codeId", codeId);
			mapValue.put("code", code);
			mapValue.put("cptr", cptr);
			mapValue.put("hide", hide);
			// mapValue.put("viewSerials", String.format("%03d",
			// Integer.valueOf(viewSerials)));//如果有专用排序号的话
			mapValue.put("viewSerials", viewSerials);
			collection.add(mapValue);
		}
		if (collection.size() > 0) {
			// 按viewSerials排序
			collection = UtilMisc.sortMaps(UtilMisc.toList(collection), UtilMisc.toList(sortDirection + "viewSerials"));
		}
		return collection;
	}*/
}