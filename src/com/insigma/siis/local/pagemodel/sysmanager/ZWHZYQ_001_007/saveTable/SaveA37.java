package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.Param;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity.CodeTypeUtil;

public class SaveA37 {
	public static String save(List<Param> params,Connection conn) {
		StringBuffer column = new StringBuffer();
		StringBuffer value = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into A37 (");
		String a0000="";
		for (Param param : params) {
			// 判断A01中有没有这条数据
			if (param.getName().equals("A0184") && !"".equals(param.getValue())) {

				List<A01> list = HBUtil.getHBSession().createQuery(
						"from A01 where a0184='"+param.getValue()+"'  ").list();
				if(list==null||list.size()==0){
					return "错误：系统中不存在该人员信息:"+param.getValue()+"，请先在人员基本信息中插入";
				}else if(list.size()>1){
					return "错误：系统中存在多条该人员信息:"+param.getValue()+"，无法加入";
				}
				else{//查询是否已存在a37信息，如果已存在，先删除，后新增，  注意开启事务
					a0000 = list.get(0).getA0000();
					List<A37> a37s = HBUtil.getHBSession().createQuery(
							"from A37 where a0000='"+a0000+"'  ").list();
					
					if(a37s!=null&&a37s.size()>0){
						PreparedStatement pst = null;
						try {
							conn.setAutoCommit(false);
							pst = conn.prepareStatement("delete from a37 where a0000=?");
							pst.setString(1, a0000);
							pst.executeUpdate();
						} catch (SQLException e) {
							e.printStackTrace();
						}finally{
							try {
								if(pst!=null)pst.close();
							} catch (SQLException e1) {
							}
						}	
					}
					column.append("a0000,");
					value.append("'"+list.get(0).getA0000()+"',");
					continue;
			}
			}
			// 非空校验

			if (param.getName().equals("A0184") && param.getValue().equals("")) {
				return "错误：身份证号码不能为空";
			}
			// code_value校验
			if (CodeTypeUtil.getCodeValue(param) == null || CodeTypeUtil.getCodeValue(param).equals("")) {
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
