package com.insigma.siis.local.util;

import java.util.UUID;

public class StringUtil {
    
  //�ָ��ַ�����ģ����ѯ  ȫ����ģ��ƥ��     add by wupy 2016-12-23
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
    
    //��ȡUUID     add by wupy 2017-02-27
    public static String getUuid(){
        String str = UUID.randomUUID().toString();//�����������ݿ������id�ǳ�����..
        str = str.replace("-", "");
        return str;
    }
    /**
     * add zepeng 20191031 �ж��ַ����Ƿ�Ϊ��
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
