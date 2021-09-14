package com.insigma.siis.local.epsoft.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;



/**
 * �ַ�������
 * @author 
 *
 */
public class StringUtil {
 
	
	/**
	 * �滻ָ����ʶ��Ϊ�س����з�
	 * @param str
	 * @return
	 */
	public static String newLineDecoder(String str){
		if(str==null){
			return null;
		}
		
		String base64ColumnValue = str.replace("[���б�ʶ��]", "\r\n"); // �滻�ػ��з�
		base64ColumnValue = base64ColumnValue.replace("[�س���]", "\r");  
		base64ColumnValue = base64ColumnValue.replace("[���з�]", "\n");
		return base64ColumnValue;
	}
	
	/**
	 * �滻�س����з�Ϊָ����ʶ��
	 * @param str
	 * @return
	 */
	public static String newLineEncoder(String str){
		
		if(str==null){
			return null;
		}
		
		String base64ColumnValue = str.replace("\r\n","[���б�ʶ��]"); // �滻�ػ��з�
		base64ColumnValue = base64ColumnValue.replace("\r","[�س���]");  
		base64ColumnValue = base64ColumnValue.replace("\n","[���з�]" );
		return base64ColumnValue;
	}
	
	/**
	 * ������base64���벢�滻���еĻس����з�
	 * @param is ��
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
	 * ����BASE64���봮ת����
	 * @param is ��
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
	  * �滻һ���ַ����е�ĳЩָ���ַ� 
	  * @param strData String ԭʼ�ַ��� 
	  * @param regex String Ҫ�滻���ַ��� 
	  * @param replacement String ����ַ��� 
	  * @return String �滻����ַ��� 
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
	 * ��ԭ�ַ����������ַ� 
	 * @param str
	 * @return
	 */
	public static String decodeXML(String xmlString){
		//&lt; <  С�ں�
		//&gt; > ���ں�
		//&amp; &  ��
		//&apos; ' ������
		//&quot; " ˫����
		if(xmlString==null && "".equals(xmlString)){
			return xmlString;
		}
		xmlString = replaceString(xmlString, "&lt;", "<");  
		xmlString = replaceString(xmlString, "&gt;", ">");  
		xmlString = replaceString(xmlString, "&apos;", "'");  
		xmlString = replaceString(xmlString, "&quot;", "\"");  
		xmlString = replaceString(xmlString, "&amp;", "&");
		xmlString = xmlString.replace("[���б�ʶ��]", "\r\n"); // �滻�ػ��з�
		xmlString = xmlString.replace("[�س���]", "\r");  
		xmlString = xmlString.replace("[���з�]", "\n");
		xmlString = xmlString.replace("[�ո��]", " ");
		xmlString = xmlString.replace("br", "\n");
	    return xmlString;
	}

	/**
	 * ����xml�������ַ�
	 * @param val
	 * @return
	 */
	public static String encodeXML(String xmlString) {
		//&lt; <  С�ں�
		//&gt; > ���ں�
		//&amp; &  ��
		//&apos; ' ������
		//&quot; " ˫����
		if(xmlString==null || "".equals(xmlString)){
			return xmlString;
		}
		xmlString = xmlString.replaceAll("&lt;", "<");
		xmlString = xmlString.replaceAll("&gt;", ">");
		xmlString = xmlString.replaceAll("&amp;", "&");
		xmlString = xmlString.replaceAll("&apos;", "'");
		xmlString = xmlString.replaceAll("&quot;", "\"");
		xmlString = xmlString.replace("\r\n","[���б�ʶ��]"); // �滻�ػ��з�
		xmlString = xmlString.replace("\r","[�س���]");  
		xmlString = xmlString.replace("\n","[���з�]" );
		xmlString = xmlString.replace(" ", "[�ո��]");
		

		char ch;
		String str;
		StringBuffer buf = new StringBuffer();
		//���������ַ�
		for (int i = 0; i < xmlString.length(); i++) {
			ch = xmlString.charAt(i);
			if (!isValidChar(ch)) { //����Ƿ������ַ���
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
	 * �Ƿ������ַ�
	 * @param cn
	 * @return
	 */
	public static boolean isValidChar(char ch) {
		int hightByte;
		int lowByte;
		byte[] bytes = (String.valueOf(ch)).getBytes();
		if (bytes.length > 2) { // ����
			return false;
		}
		if (bytes.length == 1) { // Ӣ���ַ�
			hightByte = bytes[0];
			if ((hightByte >= 32) && (hightByte <= 126)) {
				return true;
			} else {
				return false;
			}
		}
		if (bytes.length == 2) { // �����ַ�
			hightByte = bytes[0] & 0xff;
			lowByte = bytes[1] & 0xff;
			if ((hightByte >= 129 && hightByte <= 254)
					&& (lowByte >= 64 && lowByte <= 254)) { //81 40 - FE FE ת��asciiΪ�� 129 64 - 254 254
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
}

