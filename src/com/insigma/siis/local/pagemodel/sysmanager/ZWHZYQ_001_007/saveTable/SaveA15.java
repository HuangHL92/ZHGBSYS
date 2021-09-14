package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.Param;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity.CodeTypeUtil;

public class SaveA15 {

	public static String save(List<Param> params) {
		StringBuffer column = new StringBuffer();
		StringBuffer value = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into A15 (");
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

				column.append("A1500,");
				value.append("'" + UUID.randomUUID() + "',");
				column.append("A0000,");
				value.append("'" + list.get(0).getA0000() + "',");
				continue;
			}
			// 非空校验
			if (param.getName().equals("A1521") && param.getValue().equals("")) {
				return "错误：考核年度不能为空";
			}
			if (param.getName().equals("A1517") && param.getValue().equals("")) {
				return "错误：考核结论类别不能为空";
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


}
