package com.insigma.siis.local.pagemodel.sysmanager.photoconfig;

import java.util.HashMap;
import java.util.Map;

public class MakeTables {
	
	public Boolean hasTable(String[] args,String str) {
		if(args == null || args.length == 0){
			return false;
		}
		for (String s : args) {
            if (s.equals(str)){
                return true;
            }
        }
		return false;
	}
	
	/**
	 * 处理请求字符串为对应的key-value键值对
	 * 【推荐使用“:”、“_”】
	 * @param str 被处理字符串
	 * @param splitStr 分割标识符
	 * @param keyValue key和value分割标识符
	 * @return Map<String, String> 返回键值对
	 */
	public Map<String, String> makeStrToMap(String str,String splitStr,String keyValue) {
		if(str == null || "".equals(str) || 
				splitStr == null || "".equals(splitStr) ||
						keyValue == null || "".equals(keyValue) ||
				!str.contains(splitStr) || !str.contains(keyValue)){
			return null;
		}
		Map<String,String> map = new HashMap<String, String>();
		String[] split = str.split(splitStr);
		String keyAndValue = "";String[] splits = {"",""};
		for (int i = 0,ji = split.length; i < ji; i++) {
			keyAndValue = split[i];
			splits = keyAndValue.split(keyValue);
			map.put(splits[1], splits[0]);
		}
		return map;
	}
}
