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
	 * �����������JSON�����������HashMap<String, String>����
	 * @param paramsStr �������JSON��
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
	 * �����������JSON�����������HashMap<String, String>����
	 * @param paramsStr �������JSON��
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
	 * HashMap<String, String>תJSON�ַ���
	 * @param map ����ԴHashMap
	 * @return JSON�ַ���
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
	  * ���ݷ����ӿڲ�������JSON�����������List<Param>����
	  * @param paramsStr  �ӿڷ�������JSON��
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
			 throw new Exception("�������ݷ��ʽӿڷ�������ʧ��:"+e.getMessage());
		 }
		 
		 return Params;
	 }
}
