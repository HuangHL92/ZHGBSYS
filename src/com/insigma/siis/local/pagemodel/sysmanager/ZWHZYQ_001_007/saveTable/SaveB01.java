package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Param;
import com.insigma.siis.local.business.helperUtil.IdCardManageUtil;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.utils.ChineseSpelling;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity.CodeTypeUtil;

public class SaveB01 {
	private static String required = "B0101,B0104,B0114,B0121,B0194"; //必输项： 
	private static Map<String, String> defaultField = new HashMap<String, String>();//默认项
	{
		defaultField.put("UPDATED","0");//未校验
		defaultField.put("STATUS", "1");//删除状态
	}
	private static Map<String, String> generatorField = new HashMap<String, String>();//生成项
	{
		generatorField.put("SORTID","");//排序
		generatorField.put("B0107","");//简拼
		generatorField.put("B0111","");//主键
	}
	public static String save(List<Param> params) {
		StringBuffer column = new StringBuffer();
		StringBuffer value = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		Map map = new HashMap();
		//2、简称处理
		ChineseSpelling chineseSpelling = new ChineseSpelling();
		sql.append("insert into b01 (");
		//遍历入参
		for(Param param : params) {
			map.put(param.getName(), param.getValue());
			Param p2 = CodeTypeUtil.getCodeValue(param);
			if(p2==null){
				return "错误："+param.getName()+" "+param.getDesc()+" 不存在对应的代码值【"+param.getValue()+"】";
			}
			
			if("B0101".equals(param.getName())){//姓名简拼
				generatorField.put("B0107", chineseSpelling.getPYString(param.getValue()));
			}
			if("B0121".equals(param.getName())){// 主键，排序
				try {
					generatorField.put("B0111", CreateSysOrgBS.selectB0111BySubId(param.getValue()));
				} catch (AppException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RadowException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				generatorField.put("SORTID", String.valueOf(CreateSysOrgBS.insertSortId(param.getValue())));
			}
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
		//生成项
		for(String key : generatorField.keySet()){
			column.append(key+",");
			if(key.equals("SORTID")){
				value.append(" "+generatorField.get(key)+" ,");
			}else{
				value.append("'"+generatorField.get(key)+"',");
			}
		}
		
		column.deleteCharAt(column.length()-1);
		value.deleteCharAt(value.length()-1);
		sql.append(column).append(") values (").append(value);
		sql.append(")");
		init();
		String str =  validation(sql.toString(),map);
		//上级机构编码和机构名称效验
		return str;
	}
	private static void init(){
		defaultField.put("UPDATED","0");//未校验
		defaultField.put("STATUS", "1");//删除状态
		generatorField.put("SORTID","");//排序
		generatorField.put("B0107","");//简拼
		generatorField.put("B0111","");//主键
	}
	
	public static String validation(String str,Map map){
		try {
			CreateSysOrgBS.selectSubListByName(map.get("B0101").toString(), map.get("B0121").toString());
		} catch (RadowException e) {
			return "错误：当前机构名称库中已存在，请重新输入!";
		}
		B01 b01 = CreateSysOrgBS.LoadB01(map.get("B0121").toString());
		if(b01.getB0194().equals("2")){
			String b0194 = map.get("B0194").toString();
			if(!b0194.equals("内设机构")){
				return "错误：内设机构下不允许新建法人单位或机构分组！";
			}
		}
		return str;
	}
}
