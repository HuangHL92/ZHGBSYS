package com.insigma.siis.local.util;

import java.util.UUID;

public class StringUtil {
    
  //分割字符便于模糊查询  全內容模糊匹配     add by wupy 2016-12-23
    public static String getStrToLikeSelectStr(String str){
        String likeSelectStr = "";
        StringBuffer textBuffer= new StringBuffer();
        if (str != null && !"".equals(str)) {
            textBuffer.append("%");
            for (int i = 0; i < str.length(); i++) {
                textBuffer.append((str.charAt(i)+"").trim()+"%");
            }
        }
        likeSelectStr = textBuffer.toString();
        return likeSelectStr;
    }
    
    //获取UUID     add by wupy 2017-02-27
    public static String getUuid(){
        String str = UUID.randomUUID().toString();//用来生成数据库的主键id非常不错..
        str = str.replace("-", "");
        return str;
    }
    /**
     * add zepeng 20191031 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
    	if(str==null||"".equals(str)) {
    		return true;
    	}
    	return false;
    }
}
