package com.insigma.siis.local.pagemodel.cadremgn.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {

	/**
	 * 判断是否包含中文
	 * @param str
	 * @return
	 */
	// 方法 返回true为包含中文；false不包含
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
	* 手机号验证 
	* @param  str 
	* @return 验证通过返回true 
	*/  
	public static boolean isMobile(final String str) {  
	     Pattern p = null;  
	     Matcher m = null;  
	     boolean b = false;  
	     p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号  
	     m = p.matcher(str);  
	     b = m.matches();  
	     return b;  
	 }  
	 /** 
	  * 电话号码验证 
	  * @param  str 
	  * @return 验证通过返回true 
	  */  
	 public static boolean isPhone(final String str) {  
	     Pattern p1 = null, p2 = null;  
	     Matcher m = null;  
	     boolean b = false;  
	     p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的  
	     p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的  
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
          *   ^匹配输入字符串的开始位置 
          *   $结束的位置
          *   \转义字符 eg:\. 匹配一个. 字符  不是任意字符 ，转义之后让他失去原有的功能
          *   \t制表符
          *   \n换行符
          *   \\w匹配字符串  eg:\w不能匹配 因为转义了
          *   \w匹配包括字母数字下划线的任何单词字符
          *   \s包括空格制表符换行符
          *   *匹配前面的子表达式任意次
          *   .小数点可以匹配任意字符
          *   +表达式至少出现一次
          *   ?表达式0次或者1次
          *   {10}重复10次
          *   {1,3}至少1-3次
          *   {0,5}最多5次
          *   {0,}至少0次 不出现或者出现任意次都可以 可以用*号代替
          *   {1,}至少1次  一般用+来代替
          *   []自定义集合     eg:[abcd]  abcd集合里任意字符
          *   [^abc]取非 除abc以外的任意字符
          *   |  将两个匹配条件进行逻辑“或”（Or）运算 
          *   [1-9] 1到9 省略123456789
          *    邮箱匹配 eg: ^[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\.){1,3}[a-zA-z\-]{1,}$ 
          *          
          */
         String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
         //正则表达式的模式 编译正则表达式
         Pattern p = Pattern.compile(RULE_EMAIL);
         //正则表达式的匹配器
         Matcher m = p.matcher(emaile);
         //进行正则匹配\
         return m.matches();  
     }   

}
