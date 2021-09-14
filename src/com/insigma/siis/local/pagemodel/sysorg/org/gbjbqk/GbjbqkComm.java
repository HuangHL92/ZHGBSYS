package com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk;

import java.util.HashMap;
import java.util.List;

public class GbjbqkComm {

	public GbjbqkComm() {
	}

	/**
	 * List<HashMap<String, Object> to JsonString
	 * @return
	 */
	public StringBuffer toJson(List<HashMap<String, Object>> list){
		StringBuffer ss=new StringBuffer();
		if(list==null||list.size()==0){
			return ss;
		}
		ss.append("[");
		for(HashMap<String,Object> map:list){
			ss.append("{");
	        for (String key : map.keySet()) {
	        	ss.append("\"").append(key).append("\":\"").append(map.get(key))
	                    .append("\"").append(",");
	        }
	        ss.deleteCharAt(ss.length() - 1);
	        ss.append("},");
		}
		ss.deleteCharAt(ss.length() - 1);
		ss.append("]");
		return ss;
	}
	
}
