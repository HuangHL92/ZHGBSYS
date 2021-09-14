package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.Param;
import com.insigma.siis.local.business.helperUtil.IdCardManageUtil;
import com.insigma.siis.local.business.utils.ChineseSpelling;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity.CodeTypeUtil;
@SuppressWarnings("unchecked")
public class SaveA30 {
	private static String idCard = "A0184"; //身份证号 
	private static String required = "A0184,A3001"; //必输项： 身份证号，退出管理方式
	private static Map<String, String> defaultField = new HashMap<String, String>();//默认项
	{
	}
	private static Map<String, String> generatorField = new HashMap<String, String>();//生成项
	{
	}
	public static String save(List<Param> params, Connection conn) {
		StringBuffer column = new StringBuffer();
		StringBuffer value = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into a30 (");
		String a0000 = "";
		String a3001 = "";//退出管理方式
		//遍历入参
		for(Param param : params) {
			Param p2 = CodeTypeUtil.getCodeValue(param);
			if(p2==null){
				return "错误："+param.getName()+" "+param.getDesc()+" 不存在对应的代码值【"+param.getValue()+"】";
			}
			
			//人员信息新增校验
			if(idCard.equals(param.getName())){
				List<A01> list = HBUtil.getHBSession().createQuery(
						"from A01 where a0184='"+param.getValue()+"'  ").list();
				if(list==null||list.size()==0){
					return "错误：系统中不存在该人员信息:"+param.getValue()+"，请先在人员基本信息中插入";
				}else if(list.size()>1){
					return "错误：系统中存在多条该人员信息:"+param.getValue()+"，无法加入";
				}else{//查询是否已存在a30信息，如果已存在，先删除，后新增，  注意开启事务
					a0000 = list.get(0).getA0000();
					List<A30> a30s = HBUtil.getHBSession().createQuery(
							"from A30 where a0000='"+a0000+"'  ").list();
					
					if(a30s!=null&&a30s.size()>0){
						PreparedStatement pst = null;
						try {
							conn.setAutoCommit(false);
							pst = conn.prepareStatement("delete from a30 where a0000=?");
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
				}
			}else{
				//非空校验
				if(required.indexOf(param.getName())!=-1 && "".equals(param.getValue())){
					return "错误："+param.getName()+" "+param.getDesc()+"不能为空";
				}
				
				column.append(param.getName()+",");
				value.append("'"+param.getValue()+"',");
			}
			
			
			if("A3001".equals(param.getName())){
				a3001 = param.getValue();
			}
			
			
		}
		
		column.deleteCharAt(column.length()-1);
		value.deleteCharAt(value.length()-1);
		sql.append(column).append(") values (").append(value);
		sql.append(")");
		init();
		
		
		
		String a0163 = "";
		String status = "";
		String updatesql = "update a01 set a0163=?,status=? where a0000=?";
		//调出人员     历史库
		if(a3001.startsWith("1")||a3001.startsWith("2")){
			a0163 = "3";
			status = "2";
		}else if("35".equals(a3001)){//死亡  显示：已去世。       查询：历史人员
			a0163 = "4";
			status = "2";
		}else if("31".equals(a3001)){//离退休 显示：离退人员。     查询：离退人员
			a0163 = "2";
			status = "3";
		}else{//【因选举退出登记】【开除】【辞退】【辞去公职】【其它】 显示：其它人员。     查询：历史人员
			a0163 = "5";
			status = "2";
		}
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(updatesql);
			pst.setString(1, a0163);
			pst.setString(2, status);
			pst.setString(3, a0000);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(pst!=null)pst.close();
			} catch (SQLException e1) {
			}
		}
		
		
		return sql.toString();
	}
	private static void init(){
	}
}
