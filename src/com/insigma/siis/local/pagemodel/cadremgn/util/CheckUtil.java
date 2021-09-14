package com.insigma.siis.local.pagemodel.cadremgn.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {

	/**
	 * �ж��Ƿ��������
	 * @param str
	 * @return
	 */
	// ���� ����trueΪ�������ģ�false������
	public static boolean isContainsChinese(String str) {
		Pattern pat = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher matcher = pat.matcher(str);
		boolean flg = false;
		if (matcher.find()) {
			flg = true;
		}
		return flg;
	}

	/** 
	* �ֻ�����֤ 
	* @param  str 
	* @return ��֤ͨ������true 
	*/  
	public static boolean isMobile(final String str) {  
	     Pattern p = null;  
	     Matcher m = null;  
	     boolean b = false;  
	     p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // ��֤�ֻ���  
	     m = p.matcher(str);  
	     b = m.matches();  
	     return b;  
	 }  
	 /** 
	  * �绰������֤ 
	  * @param  str 
	  * @return ��֤ͨ������true 
	  */  
	 public static boolean isPhone(final String str) {  
	     Pattern p1 = null, p2 = null;  
	     Matcher m = null;  
	     boolean b = false;  
	     p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // ��֤�����ŵ�  
	     p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // ��֤û�����ŵ�  
	     if (str.length() > 9) {  
	        m = p1.matcher(str);  
	        b = m.matches();  
	     } else {  
	         m = p2.matcher(str);  
	        b = m.matches();  
	    }  
	     return b;  
	}  

	 public static boolean checkEmaile(String emaile){
         /**
          *   ^ƥ�������ַ����Ŀ�ʼλ�� 
          *   $������λ��
          *   \ת���ַ� eg:\. ƥ��һ��. �ַ�  ���������ַ� ��ת��֮������ʧȥԭ�еĹ���
          *   \t�Ʊ��
          *   \n���з�
          *   \\wƥ���ַ���  eg:\w����ƥ�� ��Ϊת����
          *   \wƥ�������ĸ�����»��ߵ��κε����ַ�
          *   \s�����ո��Ʊ�����з�
          *   *ƥ��ǰ����ӱ��ʽ�����
          *   .С�������ƥ�������ַ�
          *   +���ʽ���ٳ���һ��
          *   ?���ʽ0�λ���1��
          *   {10}�ظ�10��
          *   {1,3}����1-3��
          *   {0,5}���5��
          *   {0,}����0�� �����ֻ��߳�������ζ����� ������*�Ŵ���
          *   {1,}����1��  һ����+������
          *   []�Զ��弯��     eg:[abcd]  abcd�����������ַ�
          *   [^abc]ȡ�� ��abc����������ַ�
          *   |  ������ƥ�����������߼����򡱣�Or������ 
          *   [1-9] 1��9 ʡ��123456789
          *    ����ƥ�� eg: ^[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\.){1,3}[a-zA-z\-]{1,}$ 
          *          
          */
         String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
         //������ʽ��ģʽ ����������ʽ
         Pattern p = Pattern.compile(RULE_EMAIL);
         //������ʽ��ƥ����
         Matcher m = p.matcher(emaile);
         //��������ƥ��\
         return m.matches();  
     }   

}
