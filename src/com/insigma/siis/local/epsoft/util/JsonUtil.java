package com.insigma.siis.local.epsoft.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.insigma.siis.local.business.entity.Param;

public class JsonUtil {
	/**
	 * 根据任务参数JSON串，解析获得HashMap<String, String>对象
	 * @param paramsStr 任务参数JSON串
	 * @return
	 */
	public static  HashMap<String, String> getTaskParams(String paramsStr){
		HashMap<String, String> hm=new HashMap<String, String>();
		if(!"".equals(paramsStr) && null!=paramsStr){
			JSONArray json=JSONArray.fromObject(paramsStr);
			for (int i = 0; i < json.size(); i++) {
				 JSONObject jsonObj = JSONObject.fromObject(json.getString(i));
				 hm.put(jsonObj.getString("paramBM"), jsonObj.getString("paramValue"));
			}
		}
		return hm;
	}
	
	
	/**
	 * 根据任务参数JSON串，解析获得HashMap<String, String>对象
	 * @param paramsStr 任务参数JSON串
	 * @return
	 */
	public ArrayList getTaskParams01(String paramsStr){
		HashMap<String, String> hm=new HashMap<String, String>();
		ArrayList list = new ArrayList();
		if(!"".equals(paramsStr) && null!=paramsStr){
			JSONArray json=JSONArray.fromObject(paramsStr);
			for (int i = 0; i < json.size(); i++) {
				 JSONObject jsonObj = JSONObject.fromObject(json.getString(i));
				 list.add(jsonObj.getString("paramValue"));
			}
		}
		 Object[] objs = list.toArray();
		return list;
	}
	
	
	/**
	 * HashMap<String, String>转JSON字符串
	 * @param map 数据源HashMap
	 * @return JSON字符串
	 */
	 public static String hashMapToJson(HashMap<String, String> map) {  
		 String jsonStr="";
		 if(map!=null){
			 if(!map.isEmpty()){
		        jsonStr+="[{";  
		        for (Iterator<Entry<String, String>> it = map.entrySet().iterator(); it.hasNext();) {  
		            Entry<String, String> e = it.next();  
		            jsonStr+=e.getKey()+":";  
		            jsonStr+="'" + e.getValue() + "',";  
		        }  
		        jsonStr = jsonStr.substring(0, jsonStr.lastIndexOf(","));  
		        jsonStr += "}]";  
			 }
		 }
	        return jsonStr;  
	    } 
	 
	 /**
	  * 根据方案接口参数参数JSON串，解析获得List<Param>对象
	  * @param paramsStr  接口方案参数JSON串
	  * @return
	 * @throws Exception 
	  */
	 public static List<Param> JsonToParam(String paramsStr) throws Exception{
		 List<Param> Params = new ArrayList<Param>();
		 try{
				if(!"".equals(paramsStr) && null!=paramsStr){
					JSONArray json=JSONArray.fromObject(paramsStr);
					for (int i = 0; i < json.size(); i++) {
						 JSONObject jsonObj = JSONObject.fromObject(json.getString(i));
						 Param param = new Param();
						 param.setName(jsonObj.getString("paramBM"));
						 param.setType(jsonObj.getString("paramType"));
						 param.setValue(jsonObj.getString("paramValue"));
						 param.setDesc(jsonObj.getString("paramMC"));
						 Params.add(param);
					}
				}
		 }catch(Exception e){
			 throw new Exception("解析数据访问接口方案参数失败:"+e.getMessage());
		 }
		 
		 return Params;
	 }
}
