package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.business.entity.Param;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;

public class DeleteSingle {
	private static Map<String, String> tableMap = new HashMap<String, String>();
	static{
		String[] table = {"a01","a02","a06","a08","a11","a14","a15","a29","a30","a31","a36","a37","a41","a53","a57","a60","a61","a62","a63","a64"};
		for (int i = 0; i < table.length; i++){
			tableMap.put(""+i+1, table[i]);
		}
	}
	
	public static String delete(List<Param> params,Connection conn) throws SQLException{
		String a0000 = "";
		for (Param param : params){
			//判断是否存在该人员信息
			if (param.getName().equals("A0184") && !"".equals(param.getValue())){
				List<A01> list = HBUtil.getHBSession().createQuery(
						"from A01 where a0184='"+param.getValue()+"'  ").list();
				if(list==null||list.size()==0){
					return "错误：系统中不存在该人员信息！";
				} else if(list.size()>1){
					return "错误：系统中存在多条该人员信息！";
				} else{
					a0000 = list.get(0).getA0000();					
				}
			}
			//非空校验
			if(param.getName().equals("TABLE") && "".equals(param.getValue())){
				return "错误：信息集代码不能为空！";
			}
			if(param.getName().equals("TABLE") && !"".equals(param.getValue())){
				if(Integer.parseInt(param.getValue()) > tableMap.size() || Integer.parseInt(param.getValue()) < 2){
					return "错误：不存在对应的代码值！";
				}
				//删除培训信息
				if(param.getValue().equals("5")){
					try {					
						List<A11> a1100 = HBUtil.getHBSession().createSQLQuery("select a1100 from A11 where a0000='"+a0000+"'").list();
						for (A11 a11 : a1100) {
							HBUtil.executeUpdate("delete from a41 where a1100='"+a11.getA1100()+"'");
						}
						HBUtil.executeUpdate("delete from a11 where a0000='"+a0000+"'");
					} catch (AppException e) {
						e.printStackTrace();
					}
				}
				//删除照片
				else if(param.getValue().equals("15")){
					try {
						HBUtil.executeUpdate("delete from a57 where a0000='"+a0000+"'");
						PhotosUtil.deletePhoto2(a0000);	
					} catch (AppException e) {
						e.printStackTrace();
					}
				}
				else{
					if(delmethod(a0000, conn, tableMap.get(param.getValue()))==0){
						return "错误：执行失败！";
					}
					
				} 

			}
		}
		conn.commit();
		return "select 1 from dual";
	}

	private static int delmethod(String a0000, Connection conn, String table) throws SQLException {
		PreparedStatement pst = null;
		try {
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("delete from "+ table +" where a0000=?");
			pst.setString(1, a0000);
			pst.executeUpdate();
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
			return 0;
		}
	}
	
}
