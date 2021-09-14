package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.Param;

public class SaveA06 {
	public static String save(List<Param> params,Connection conn) {
		StringBuffer column = new StringBuffer();
		StringBuffer value = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		String a0000 = "";
		String a0196 = "";
		// column和value加上主键名和主键值
		column.append("A0600,");
		value.append("'" + UUID.randomUUID().toString() + "',");
		sql.append("insert into a06 (");
		// 遍历入参
		for (Param param : params) {
			if (param.getName().equals("A0184")) {
				// 1、身份证号码校验
				if (param.getValue().equals("")) {
					return "错误：身份证号码不能为空";
				} else {
					List<A01> list = HBUtil.getHBSession()
							.createQuery("from A01 where a0184='" + param.getValue() + "'  ").list();
					if(list==null||list.size()==0){
						return "错误：系统中不存在该人员信息:"+param.getValue()+"，请先在人员基本信息中插入";
					}else if(list.size()>1){
						return "错误：系统中存在多条该人员信息:"+param.getValue()+"，无法加入";
					} else {
						column.append("A0000,");
						value.append("'" + list.get(0).getA0000() + "',");
						a0000 = list.get(0).getA0000();
					}
				}
			} else if (param.getName().equals("A0601")) {
				// 2、专业技术资格代码
				if (param.getValue().equals("")) {
					return "错误：专业技术任职资格不能为空";
				} else {
					List<CodeValue> list = HBUtil.getHBSession()
							.createQuery(
									"from CodeValue where code_name='" + param.getValue() + "' and code_type='GB8561'")
							.list();
					if (list != null && list.size() != 1) {
						return "错误：系统中不存在专业技术任职资格对应的代码值:" + param.getValue();
					} else {
						column.append("A0601,");
						value.append("'" + list.get(0).getCodeValue() + "',");
					}
				}

			} else if (param.getName().equals("A0607")) {
				// 3 获取资格途径
				if (!param.getValue().equals("")) {
					List<CodeValue> list = HBUtil.getHBSession()
							.createQuery(
									"from CodeValue where code_name='" + param.getValue() + "' and code_type='ZB24'")
							.list();
					if (list != null && list.size() != 1) {
						return "错误：系统中不存在获取资格途径对应的代码值:" + param.getValue();
					} else {
						column.append("A0607,");
						value.append("'" + list.get(0).getCodeValue() + "',");
					}
				}
			} else if (param.getName().equals("A0602") && param.getName() != null) {
				a0196 = param.getValue();
			} else {
				column.append(param.getName() + ",");
				value.append("'" + param.getValue() + "',");
			}
		}

		
		 //更新a01
		PreparedStatement pst = null;
		try {
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("update A01 a01 set a01.a0196 =? where a01.a0000 = ?");
			pst.setString(1, a0196);
			pst.setString(2, a0000);
			pst.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
  
		column.deleteCharAt(column.length() - 1);
		value.deleteCharAt(value.length() - 1);
		sql.append(column).append(") values (").append(value);
		sql.append(")");
		return sql.toString();
	}
}
