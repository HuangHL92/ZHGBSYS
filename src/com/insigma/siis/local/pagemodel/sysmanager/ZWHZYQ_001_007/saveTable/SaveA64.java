package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A60;
import com.insigma.siis.local.business.entity.A64;
import com.insigma.siis.local.business.entity.Param;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity.CodeTypeUtil;

public class SaveA64 {

	public static String save(List<Param> params,Connection conn) throws AppException{
		StringBuffer column = new StringBuffer();
		StringBuffer value = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		String a0000="";
		String type = "";
		sql.append("insert into A64 (");
		for (Param param : params){
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
				}else{//查询是否已存在a64信息，如果已存在，先删除，后新增，  注意开启事务
					a0000 = list.get(0).getA0000();
					
					column.append("a0000,");
					value.append("'"+list.get(0).getA0000()+"',");
					//主键
					column.append("a6400,");
					value.append("'"+UUID.randomUUID()+"',");
					continue;
			}
			}
			// 非空校验
			if (param.getName().equals("A0184") && param.getValue().equals("")) {
				return "错误：身份证号码不能为空";
			}
			
			// code_value校验
			if (CodeTypeUtil.getCodeValue(param) == null ) {
				return "错误：" + param.getName() + "值输入不合法！";
			}
			//获取类型
			if(param.getName().equals("A64TYPE")){
				if(param.getValue().equals("") || param.getValue() == null){
					return "错误："+ param.getName() + "值不能为空！";
				}else if(!param.getValue().equals("A60") && !param.getValue().equals("A61")){
					return "错误："+param.getName() + "值输入不合法！";
				}
				type = param.getValue();
			}
			column.append(param.getName() + ",");
			value.append("'" + param.getValue() + "',");
		}
		column.deleteCharAt(column.length() - 1);
		value.deleteCharAt(value.length() - 1);
		sql.append(column).append(") values (").append(value);
		sql.append(")");
		List<A64> a64s = HBUtil.getHBSession().createQuery(
				"from A64 where a0000='"+a0000+"'  ").list();
		
		if(a64s!=null&&a64s.size()>0){
			PreparedStatement pst = null;
			try {
				conn.setAutoCommit(false);
				pst = conn.prepareStatement("delete from a64 where a0000=? and a64type=?");
				pst.setString(1, a0000);
				pst.setString(2, type);
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
		return sql.toString();
		
	}
}
