package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.Param;

public class SaveA11 {
	public static String save(List<Param> params, Connection conn) {
		StringBuffer column = new StringBuffer();
		StringBuffer value = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		// column和value加上主键名和主键值
		column.append("A1100,");
		String uuid = UUID.randomUUID().toString();
		String a0000 = "";
		value.append("'" + uuid + "',");
		sql.append("insert into a11 (");
		// 遍历入参
		for (Param param : params) {
			if (param.getName().equals("A0184")) {
				// 1、身份证号码校验
				if (param.getValue().equals("")) {
					return "错误：身份证号码不能为空";
				} else {
					List<A01> list = HBUtil.getHBSession()
							.createQuery(
									"from A01 where a0184='" + param.getValue()
											+ "'  ").list();
					if(list==null||list.size()==0){
						return "错误：系统中不存在该人员信息:"+param.getValue()+"，请先在人员基本信息中插入";
					}else if(list.size()>1){
						return "错误：系统中存在多条该人员信息:"+param.getValue()+"，无法加入";
					} else {
						column.append("A0000,");
						a0000 = list.get(0).getA0000();
						value.append("'" + a0000 + "',");
					}
				}
			} else if (param.getName().equals("A1131")) {
				// 2、培训班名称
				if (param.getValue().equals("")) {
					return "错误：培训班名称不能为空";
				} else {
					column.append(param.getName() + ",");
					value.append("'" + param.getValue() + "',");
				}

			} else if (param.getName().equals("A1101")) {
				// 3 培训类别
				if (!param.getValue().equals("")) {
					List<CodeValue> list = HBUtil.getHBSession().createQuery(
							"from CodeValue where code_name='"
									+ param.getValue()
									+ "' and code_type='ZB29'").list();
					if (list != null && list.size() != 1) {
						return "错误：系统中不存在培训类别的代码值:" + param.getValue();
					} else {
						column.append("A1101,");
						value.append("'" + list.get(0).getCodeValue() + "',");
					}
				}
			} else if (param.getName().equals("A1127")) {
				// 4 培训机构类别
				if (!param.getValue().equals("")) {
					List<CodeValue> list = HBUtil.getHBSession().createQuery(
							"from CodeValue where code_name='"
									+ param.getValue()
									+ "' and code_type='ZB27'").list();
					if (list != null && list.size() != 1) {
						return "错误：系统中不存在培训机构类别对应的代码值:" + param.getValue();
					} else {
						column.append("A1127,");
						value.append("'" + list.get(0).getCodeValue() + "',");
					}
				}
			} else if (param.getName().equals("A1104")) {
				// 5 培训离岗状态
				if (!param.getValue().equals("")) {
					List<CodeValue> list = HBUtil.getHBSession().createQuery(
							"from CodeValue where code_name='"
									+ param.getValue()
									+ "' and code_type='ZB30'").list();
					if (list != null && list.size() != 1) {
						return "错误：系统中不存在培训离岗状态对应的代码值:" + param.getValue();
					} else {
						column.append("A1104,");
						value.append("'" + list.get(0).getCodeValue() + "',");
					}
				}
			} else if (param.getName().equals("A1151")) {
				// 6 出国（出境）培训标识
				if (!param.getValue().equals("")) {
					if (!param.getValue().equals("是")
							&& !param.getValue().equals("否")) {
						return "错误：出国（出境）培训标识不正确:" + param.getValue();
					} else {
						column.append("A1151,");
						if(param.getValue().equals("是")){
							value.append("'1',");
						}else{
							value.append("'0',");
						}
						
					}
				}
			} else {
				column.append(param.getName() + ",");
				value.append("'" + param.getValue() + "',");
			}
		}

		// 插入关联表a41
		String a41sql = "insert into a41(A4100,A0000,A1100)values('"
				+ UUID.randomUUID().toString() + "','" + a0000 + "','" + uuid
				+ "')";
		PreparedStatement pst = null;
		try {
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(a41sql);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pst != null)
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		column.deleteCharAt(column.length() - 1);
		value.deleteCharAt(value.length() - 1);
		sql.append(column).append(") values (").append(value);
		sql.append(")");
		return sql.toString();
	}
}
