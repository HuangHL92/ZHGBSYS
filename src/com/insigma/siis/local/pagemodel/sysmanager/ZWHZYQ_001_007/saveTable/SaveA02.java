package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.Param;
import com.insigma.siis.local.business.helperUtil.IdCardManageUtil;
import com.insigma.siis.local.business.utils.ChineseSpelling;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity.CodeTypeUtil;

public class SaveA02 {
	private static String idCard = "A0184"; //身份证号 
	private static String required = "A0184,A0222,A0255,A0216A"; //必输项： 身份证号，岗位类别，任职状态,任职机构名称,职务名称
	private static Map<String, String> defaultField = new HashMap<String, String>();//默认项
	{
		defaultField.put("A0201B", "-1");
	}
	private static Map<String, String> generatorField = new HashMap<String, String>();//生成项
	{
	}
	public static String save(List<Param> params,Connection conn) {
		StringBuffer column = new StringBuffer();
		StringBuffer value = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		String a0000 = "";
		String a0148 = "";
		sql.append("insert into a02 (");
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
				}else{//加入外键
					column.append("a0000,");
					value.append("'"+list.get(0).getA0000()+"',");
					a0000 = list.get(0).getA0000();
				}
			}else{
				//非空校验
				if(required.indexOf(param.getName())!=-1 && "".equals(param.getValue())){
					return "错误："+param.getName()+" "+param.getDesc()+"不能为空";
				}
				//职务层次
				if(param.getName().equals("A0221") && !param.getValue().trim().equals("")){
					a0148 = param.getValue();
				}
				
				//默认项
				if(defaultField.get(param.getName())!=null){//默认项 为传入的数据
					if(!"".equals(param.getValue())){
						defaultField.put(param.getName(), param.getValue());
					}
				}else{//非默认项
					column.append(param.getName()+",");
					value.append("'"+param.getValue()+"',");
				}
			}
		}
		
		//默认项
		for(String key : defaultField.keySet()){
			column.append(key+",");
			value.append("'"+defaultField.get(key)+"',");
		}
		
		//主键
		column.append("a0200,");
		value.append("'"+UUID.randomUUID()+"',");
		
		
		 //更新a01 a0148职务层次
		PreparedStatement pst = null;
		try {
			HBSession sess = HBUtil.getHBSession();
			String sql2="select a0221 from A02 a where a0000='"+a0000+"' and a0255='1' order by a0221";
			List<String> list = sess.createSQLQuery(sql2).list();
			if(list!=null&&list.size()>0){
				if(a0148.compareTo(list.get(0).toString()) == -1){
					a0148 = list.get(0);
				}
			}
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("update A01 a01 set a01.a0148 =? where a01.a0000 = ?");
			pst.setString(1, a0148);
			pst.setString(2, a0000);
			pst.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		column.deleteCharAt(column.length()-1);
		value.deleteCharAt(value.length()-1);
		sql.append(column).append(") values (").append(value);
		sql.append(")");
		init();
		return sql.toString();
	}
	private static void init(){
		defaultField.put("A0201B", "-1");
	}
}
