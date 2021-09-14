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
import com.insigma.siis.local.business.entity.Param;
import com.insigma.siis.local.business.helperUtil.IdCardManageUtil;
import com.insigma.siis.local.business.utils.ChineseSpelling;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity.CodeTypeUtil;

public class SaveA01 {
	private static String idCard = "A0184"; //身份证号 
	private static String required = "A0184,A0101"; //必输项： 身份证号，姓名
	private static Map<String, String> defaultField = new HashMap<String, String>();//默认项
	{
		defaultField.put("A0104", "1");//性别
		defaultField.put("A0128", "健康");//健康状态
		defaultField.put("A0163", "1");//默认现职人员
		defaultField.put("A14Z101", "无");//奖惩描述
		defaultField.put("STATUS", "1");//删除状态
	}
	private static Map<String, String> generatorField = new HashMap<String, String>();//生成项
	{
		//generatorField.put("A0102", "");//姓名简拼
		//generatorField.put("AGE","");
	}
	public static String save(List<Param> params,Connection conn) {
		StringBuffer column = new StringBuffer();
		StringBuffer value = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		String a0000 = null;
		//2、简称处理
		ChineseSpelling chineseSpelling = new ChineseSpelling();
		sql.append("insert into a01 (");
		//遍历入参
		for(Param param : params) {
			Param p2 = CodeTypeUtil.getCodeValue(param);
			if(p2==null){
				return "错误："+param.getName()+" "+param.getDesc()+" 不存在对应的代码值【"+param.getValue()+"】";
			}
			//人员信息新增校验
			if(idCard.equals(param.getName())){
				/*if(IdCardManageUtil.trueOrFalseIdCard(param.getValue())){
					generatorField.put("AGE", IdCardManageUtil.getAge(param.getValue())+"");
				}else{
					return "错误：身份证号错误:"+param.getValue();
				}*/
				List<A01> list = HBUtil.getHBSession().createQuery(
						"from A01 where a0184='"+param.getValue()+"'  ").list();
				if(list!=null&&list.size()==1){
					a0000 = list.get(0).getA0000();
					PreparedStatement pst = null;
					try {
						conn.setAutoCommit(false);
						pst = conn.prepareStatement("delete from a01 where a0000=?");
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
				}else if(list!=null&&list.size()>0){
					return "错误：系统中已存在该人员:"+param.getValue();
				}
			}
			
			/*if("A0101".equals(param.getName())){//姓名简拼
				generatorField.put("A0102", chineseSpelling.getPYString(param.getValue()));
			}*/
			
			//非空校验
			if(required.indexOf(param.getName())!=-1 && "".equals(param.getValue())){
				return "错误："+param.getName()+" "+param.getDesc()+"不能为空";
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
		//默认项
		for(String key : defaultField.keySet()){
			column.append(key+",");
			value.append("'"+defaultField.get(key)+"',");
		}
		/*//生成项
		for(String key : generatorField.keySet()){
			column.append(key+",");
			if(key.equals("AGE")){
				value.append(" "+generatorField.get(key)+" ,");
			}else{
				value.append("'"+generatorField.get(key)+"',");
			}
		}*/
		//主键
		column.append("a0000,");
		value.append("'"+(a0000==null?UUID.randomUUID():a0000)+"',");
		
		
		column.deleteCharAt(column.length()-1);
		value.deleteCharAt(value.length()-1);
		sql.append(column).append(") values (").append(value);
		sql.append(")");
		init();
		return sql.toString();
	}
	private static void init(){
		defaultField.put("A0104", "1");//性别
		defaultField.put("A0128", "健康");//健康状态
		defaultField.put("A0163", "1");//默认现职人员
		defaultField.put("A14Z101", "无");//奖惩描述
		defaultField.put("STATUS", "1");//删除状态
		/*generatorField.put("A0102", "");//姓名简拼
		generatorField.put("AGE","");*/
	}
}
