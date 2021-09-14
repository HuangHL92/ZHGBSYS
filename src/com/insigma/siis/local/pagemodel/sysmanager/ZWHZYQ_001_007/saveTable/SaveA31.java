package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A31;
import com.insigma.siis.local.business.entity.Param;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity.CodeTypeUtil;

public class SaveA31 {

	public static String save(List<Param> params, Connection conn) {
		StringBuffer column = new StringBuffer();
		StringBuffer value = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into A31 (");
		String a0000="";
		String a0163="";	//人员管理状态
		String status="";
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
				else{//查询是否已存在a31信息，如果已存在，先删除，后新增，  注意开启事务
					a0000 = list.get(0).getA0000();
					List<A31> a31s = HBUtil.getHBSession().createQuery(
							"from A31 where a0000='"+a0000+"'  ").list();
					
					if(a31s!=null&&a31s.size()>0){
						PreparedStatement pst = null;
						try {
							conn.setAutoCommit(false);
							pst = conn.prepareStatement("delete from a31 where a0000=?");
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
					column.append("A0000,");
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
			//离退人员状态
			if (param.getName().equals("A3101")){
				if(!param.getValue().equals("")){
					a0163="2";
					status="3";
					//更新a01
					PreparedStatement pst = null;
					try {
						conn.setAutoCommit(false);
						pst = conn.prepareStatement("update A01 a01 set a01.a0163=?,a01.status=?  where a01.a0163='1'and a01.status='1' a01.a0000 = ?");
						pst.setString(1, a0163);
						pst.setString(2, status);
						pst.setString(3, a0000);
						pst.executeUpdate();
					} catch (SQLException e) {
						
						e.printStackTrace();
					}

				}
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
