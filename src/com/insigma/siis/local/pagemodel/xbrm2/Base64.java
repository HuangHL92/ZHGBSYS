package com.insigma.siis.local.pagemodel.xbrm2;

import java.io.UnsupportedEncodingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64 {  
    // ����  
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
            //��RFC 822�涨��ÿ76���ַ�������Ҫ����һ���س�����ȥ�����з�  
            s = s.replaceAll("[\\s*\t\n\r]", "");    
        }  
        return s;  
    }  
  
    // ����  
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
        String s = "��ã�The Word!";  
        //System.out.println(s.length());  
        String enStr = encode(s);  
        String deStr = decode(enStr);  
        System.out.println("ԭʼ���ݣ�"+s+"\n�������ݣ�"+enStr+"\n�������ݣ�"+deStr);  
    }  
} 
