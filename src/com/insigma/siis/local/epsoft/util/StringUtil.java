package com.insigma.siis.local.epsoft.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;



/**
 * 字符处理类
 * @author 
 *
 */
public class StringUtil {
 
	
	/**
	 * 替换指定标识符为回车换行符
	 * @param str
	 * @return
	 */
	public static String newLineDecoder(String str){
		if(str==null){
			return null;
		}
		
		String base64ColumnValue = str.replace("[换行标识符]", "\r\n"); // 替换回换行符
		base64ColumnValue = base64ColumnValue.replace("[回车符]", "\r");  
		base64ColumnValue = base64ColumnValue.replace("[换行符]", "\n");
		return base64ColumnValue;
	}
	
	/**
	 * 替换回车换行符为指定标识符
	 * @param str
	 * @return
	 */
	public static String newLineEncoder(String str){
		
		if(str==null){
			return null;
		}
		
		String base64ColumnValue = str.replace("\r\n","[换行标识符]"); // 替换回换行符
		base64ColumnValue = base64ColumnValue.replace("\r","[回车符]");  
		base64ColumnValue = base64ColumnValue.replace("\n","[换行符]" );
		return base64ColumnValue;
	}
	
	/**
	 * 流进行base64编码并替换其中的回车换行符
	 * @param is 流
	 * @return
	 * @throws Exception
	 */
	public static String BASE64Encoder(InputStream is) throws Exception{
		String base64Str = "";
		if(is!=null){
			
			try{
				ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
				
				BufferedInputStream inBuff=new BufferedInputStream(is);  
				int buf_size = 1024;  
				byte[] buffer = new byte[buf_size];  
				int len = 0;  
				while(-1!=(len = inBuff.read(buffer,0,buf_size))){  
					bos.write(buffer,0,len);  
					
				}  
				base64Str=new BASE64Encoder().encodeBuffer(bos.toByteArray());
			base64Str = StringUtil.newLineEncoder(base64Str);
			}catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		return base64Str;
		
	}
	
	/**
	 * 将流BASE64编码串转回流
	 * @param is 流
	 * @return
	 * @throws Exception
	 */
	public static InputStream BASE64Decoder(String base64Str) throws Exception{
		base64Str = StringUtil.newLineDecoder(base64Str);
		try{
			byte[] dataByte = new BASE64Decoder().decodeBuffer(base64Str);
			ByteArrayInputStream is = new ByteArrayInputStream(dataByte);
			return is;
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	
	/** 
	  * 替换一个字符串中的某些指定字符 
	  * @param strData String 原始字符串 
	  * @param regex String 要替换的字符串 
	  * @param replacement String 替代字符串 
	  * @return String 替换后的字符串 
	  */  
	 public static String replaceString(String strData, String regex,  
	         String replacement)  
	 {  
	     if (strData == null)  
	     {  
	         return null;  
	     }  
	     int index;  
	     index = strData.indexOf(regex);  
	     String strNew = "";  
	     if (index >= 0)  
	     {  
	         while (index >= 0)  
	         {  
	             strNew += strData.substring(0, index) + replacement;  
	             strData = strData.substring(index + regex.length());  
	             index = strData.indexOf(regex);  
	         }  
	         strNew += strData;  
	         return strNew;  
	     }  
	     return strData;  
	 }  
	
	
	/**
	 * 还原字符串中特殊字符 
	 * @param str
	 * @return
	 */
	public static String decodeXML(String xmlString){
		//&lt; <  小于号
		//&gt; > 大于号
		//&amp; &  和
		//&apos; ' 单引号
		//&quot; " 双引号
		if(xmlString==null && "".equals(xmlString)){
			return xmlString;
		}
		xmlString = replaceString(xmlString, "&lt;", "<");  
		xmlString = replaceString(xmlString, "&gt;", ">");  
		xmlString = replaceString(xmlString, "&apos;", "'");  
		xmlString = replaceString(xmlString, "&quot;", "\"");  
		xmlString = replaceString(xmlString, "&amp;", "&");
		xmlString = xmlString.replace("[换行标识符]", "\r\n"); // 替换回换行符
		xmlString = xmlString.replace("[回车符]", "\r");  
		xmlString = xmlString.replace("[换行符]", "\n");
		xmlString = xmlString.replace("[空格符]", " ");
		xmlString = xmlString.replace("br", "\n");
	    return xmlString;
	}

	/**
	 * 过滤xml的特殊字符
	 * @param val
	 * @return
	 */
	public static String encodeXML(String xmlString) {
		//&lt; <  小于号
		//&gt; > 大于号
		//&amp; &  和
		//&apos; ' 单引号
		//&quot; " 双引号
		if(xmlString==null || "".equals(xmlString)){
			return xmlString;
		}
		xmlString = xmlString.replaceAll("&lt;", "<");
		xmlString = xmlString.replaceAll("&gt;", ">");
		xmlString = xmlString.replaceAll("&amp;", "&");
		xmlString = xmlString.replaceAll("&apos;", "'");
		xmlString = xmlString.replaceAll("&quot;", "\"");
		xmlString = xmlString.replace("\r\n","[换行标识符]"); // 替换回换行符
		xmlString = xmlString.replace("\r","[回车符]");  
		xmlString = xmlString.replace("\n","[换行符]" );
		xmlString = xmlString.replace(" ", "[空格符]");
		

		char ch;
		String str;
		StringBuffer buf = new StringBuffer();
		//其他特殊字符
		for (int i = 0; i < xmlString.length(); i++) {
			ch = xmlString.charAt(i);
			if (!isValidChar(ch)) { //检查是否特殊字符串
				continue;
			}
			//str = String.valueOf(ch);
			str = Character.toString(ch);
			if ('<' == ch) {
				str = "&lt;";
			}
			if ('>' == ch) {
				str = "&gt;";
			}
			if ('&' == ch) {
				str = "&amp;";
			}
			if ('\'' == ch) {
				str = "&apos;";
			}
			if ('"' == ch) {
				str = "&quot;";
			}
			buf.append(str);
		}
		return buf.toString();
	}
	
	/**
	 * 是否特殊字符
	 * @param cn
	 * @return
	 */
	public static boolean isValidChar(char ch) {
		int hightByte;
		int lowByte;
		byte[] bytes = (String.valueOf(ch)).getBytes();
		if (bytes.length > 2) { // 错误
			return false;
		}
		if (bytes.length == 1) { // 英文字符
			hightByte = bytes[0];
			if ((hightByte >= 32) && (hightByte <= 126)) {
				return true;
			} else {
				return false;
			}
		}
		if (bytes.length == 2) { // 中文字符
			hightByte = bytes[0] & 0xff;
			lowByte = bytes[1] & 0xff;
			if ((hightByte >= 129 && hightByte <= 254)
					&& (lowByte >= 64 && lowByte <= 254)) { //81 40 - FE FE 转成ascii为： 129 64 - 254 254
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
}

