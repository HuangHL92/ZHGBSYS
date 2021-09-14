package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.Param;

public class SaveA08 {
	public static String save(List<Param> params) {
		StringBuffer column = new StringBuffer();
		StringBuffer value = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		int i = 0;// 用于判断同时为空情况
		// column和value加上主键名和主键值
		column.append("A0800,");
		value.append("'" + UUID.randomUUID().toString() + "',");
		sql.append("insert into a08 (");
//		String a0000="";
//		String jylb="";
//		String yx_xl = "";//院系
//		String zy_xl = "";//专业	
//		String yx_xw = "";//院系
//		String zy_xw = "";//专业
//		String xl = "";	  //学历
//		String xw = "";	  //学位
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
						value.append("'" + list.get(0).getA0000() + "',");
//						a0000 = list.get(0).getA0000();
					}
				}
			} else if (param.getName().equals("A0837")) {
				// 2、教育类别
				if (param.getValue().equals("")) {
					return "错误：教育类别不能为空";
				} else {
					List<CodeValue> list = HBUtil.getHBSession().createQuery(
							"from CodeValue where code_name='"
									+ param.getValue()
									+ "' and code_type='ZB123'").list();
					if (list != null && list.size() != 1) {
						return "错误：系统中不存在教育类别对应的代码值:" + param.getValue();
					} else {
						column.append("A0837,");
						value.append("'" + list.get(0).getCodeValue() + "',");
//						jylb = list.get(0).getCodeValue();
					}
				}

			} else if (param.getName().equals("A0801B")) {
				// 3 学历代码
				if (!param.getValue().equals("")) {
					List<CodeValue> list = HBUtil.getHBSession().createQuery(
							"from CodeValue where code_name='"
									+ param.getValue()
									+ "' and code_type='ZB64'").list();
					if (list != null && list.size() != 1) {
						return "错误：系统中不存在学历对应的代码值:" + param.getValue();
					} else {
						column.append("A0801B,");
						value.append("'" + list.get(0).getCodeValue() + "',");
					}
				} else {
					i++;
				}
			} else if (param.getName().equals("A0901B")) {
				// 4 学位代码
				if (!param.getValue().equals("")) {
					List<CodeValue> list = HBUtil.getHBSession().createQuery(
							"from CodeValue where code_name='"
									+ param.getValue()
									+ "' and code_type='GB6864'").list();
					if (list != null && list.size() != 1) {
						return "错误：系统中不存在学位对应的代码值:" + param.getValue();
					} else {
						column.append("A0901B,");
						value.append("'" + list.get(0).getCodeValue() + "',");
					}
				} else {
					i++;
				}
			} else if (param.getName().equals("A0827")) {
				// 5 所学专业类别
				if (!param.getValue().equals("")) {
					List<CodeValue> list = HBUtil.getHBSession().createQuery(
							"from CodeValue where code_name='"
									+ param.getValue()
									+ "' and code_type='GB16835'").list();
					if (list != null && list.size() != 1) {
						return "错误：系统中不存在所学专业类别对应的代码值:" + param.getValue();
					} else {
						column.append("A0827,");
						value.append("'" + list.get(0).getCodeValue() + "',");
					}
				}
			} 
//			else if (param.getName().equals("A0814")) {
//				yx_xl = param.getValue();
//				zy_xl = param.getValue();
//			} else if (param.getName().equals("A0824")) {
//				yx_xw = param.getValue();
//				zy_xw = param.getValue();
//			} else if (param.getName().equals("A0801A")) {
//				xl = param.getValue();
//			}else if (param.getName().equals("A0901A")) {
//				xw = param.getValue();
//			}
			else {
				column.append(param.getName() + ",");
				value.append("'" + param.getValue() + "',");
			}
		}
		if (i > 1) {
			return "错误：学历学位不能同时为空";
		}
		
//			HBSession sess = HBUtil.getHBSession();
//			
//		
//			if(yx_xl==null){
//				yx_xl = "";
//			}
//			if(zy_xl==null){
//				zy_xl = "";
//			}
//			if(yx_xw==null){
//				yx_xw = "";
//			}
//			if(zy_xw==null){
//				zy_xw = "";
//			}
//			if(!"".equals(zy_xl)){
//				zy_xl += "专业";
//			}
//			if(!"".equals(zy_xw)){
//				zy_xw += "专业";
//			}
//			if("1".equals(jylb)){
//				String Qrzxlxx = yx_xl+zy_xl;
//				String Qrzxwxx = yx_xw+zy_xw;
//				try {
//					HBTransaction tx = sess.beginTransaction();
//					String hql = "update A01 a01 set a01.qrzxl='"+ xl +"',a01.qrzxw='"+ xw +"',a01.qrzxlxx ='"+ Qrzxlxx +"' ,a01.qrzxw='"+ Qrzxwxx +"' where a01.a0000 = '"+ a0000+"'";
//					Query query = sess.createQuery(hql);
//					query.executeUpdate();
//					tx.commit();
//				} catch (AppException e) {
//					
//					e.printStackTrace();
//				}
//
//			}else{//在职
//				String Zzxlxx = yx_xl+zy_xl;
//				String Zzxwxx = yx_xw+zy_xw;
//				try {
//					HBTransaction tx = sess.beginTransaction();
//					String hql = "update A01 a01 set a01.zzxl='"+ xl +"',a01.zzxw='"+ xw +"',a01.zzxlxx ='"+ Zzxlxx+"' ,a01.zzxwxx='"+ Zzxwxx+"' where a01.a0000 = '"+ a0000+"'";
//					Query query = sess.createQuery(hql);
//					query.executeUpdate();
//					tx.commit();
//				} catch (AppException e) {
//					
//					e.printStackTrace();
//				}
//
//			}

	
		column.deleteCharAt(column.length() - 1);
		value.deleteCharAt(value.length() - 1);
		sql.append(column).append(") values (").append(value);
		sql.append(")");
		return sql.toString();
	}
}
