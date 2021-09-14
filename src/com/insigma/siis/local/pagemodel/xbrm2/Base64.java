package com.insigma.siis.local.pagemodel.xbrm2;

import java.io.UnsupportedEncodingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64 {  
    // 加密  
    public static String encode(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s = new BASE64Encoder().encode(b);  
            //据RFC 822规定，每76个字符，还需要加上一个回车换行去掉换行符  
            s = s.replaceAll("[\\s*\t\n\r]", "");    
        }  
        return s;  
    }  
  
    // 解密  
    public static String decode(String s) {  
        byte[] b = null;  
        String result = null;  
        if (s != null) {  
            BASE64Decoder decoder = new BASE64Decoder();  
            try {  
                b = decoder.decodeBuffer(s);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }  
    public static void main(String[] args){  
        String s = "你好，The Word!";  
        //System.out.println(s.length());  
        String enStr = encode(s);  
        String deStr = decode(enStr);  
        System.out.println("原始数据："+s+"\n加密数据："+enStr+"\n解密数据："+deStr);  
    }  
} 
