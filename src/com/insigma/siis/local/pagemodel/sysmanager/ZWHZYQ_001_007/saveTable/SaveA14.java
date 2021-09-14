package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.Param;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity.CodeTypeUtil;

public class SaveA14 {

	public static String save(List<Param> params) {
		StringBuffer column = new StringBuffer();
		StringBuffer value = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into A14 (");
		for (Param param : params) {

			// 判断A01中有没有这条数据
			if (param.getName().equals("A0184") && !"".equals(param.getValue())) {

				List<A01> list = HBUtil.getHBSession()
				.createQuery(
						"from A01 where a0184='" + param.getValue()
								+ "'  ").list();
				
				if(list==null||list.size()==0){
					return "错误：系统中不存在该人员信息:"+param.getValue()+"，请先在人员基本信息中插入";
				}else if(list.size()>1){
					return "错误：系统中存在多条该人员信息:"+param.getValue()+"，无法加入";
				}
				// 默认项
				column.append("SORTID,");
				value.append("'" + sortId(param.getValue()) + "',");
				column.append("a1400,");
				value.append("'" + UUID.randomUUID() + "',");
				column.append("a0000,");
				value.append("'" + list.get(0).getA0000() + "',");
				continue;
			}
			// 非空校验
			if (param.getName().equals("A1404B") && param.getValue().equals("")) {
				return "错误：奖惩名称代码不能为空";
			}
			if (param.getName().equals("A0184") && param.getValue().equals("")) {
				return "错误：身份证号码不能为空";
			}
			// code_value校验
			if (CodeTypeUtil.getCodeValue(param) == null ) {
				return "错误：" + param.getName() + "值输入不合法！";
			}
		
			column.append(param.getName() + ",");
			value.append("'" + param.getValue() + "',");

		}
		column.deleteCharAt(column.length() - 1);
		value.deleteCharAt(value.length() - 1);
		sql.append(column).append(") values (").append(value);
		sql.append(")");
		return sql.toString();
	}
/**
 * 生成排序字段
 * @param id
 * @return
 */
	public static int sortId(String id) {
		String sql = "select a14.SORTID  from A14 a14  where a14.A0000=(select a01.A0000 from A01 a01 where a01.A0184='"
				+ id + "') order by a14.SORTID desc";

		List list = HBUtil.getHBSession().createSQLQuery(sql).list();
		String sortid = "1";
		if (list.size() == 0 || list.get(0) == null) {
			sortid = "1";
		}
		else if (list.get(0).equals(1)) {
			sortid = "2";
		} else {
			sortid = Integer.toString(Integer.parseInt(list.get(0).toString())+1);
		}
		return Integer.parseInt(sortid);
	}



}
