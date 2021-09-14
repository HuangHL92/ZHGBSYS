package com.insigma.siis.local.business.utils;

import java.util.Arrays;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class ChineseSpelling {
	
	
	
	
	/**
     * 测试main方法
     * @param args
	 * @throws BadHanyuPinyinOutputFormatCombination 
     */
    public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {
    	HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat(); 
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        defaultFormat.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        System.out.println(ToFirstChar("樲金炜臣").toUpperCase()); //转为首字母大写
        System.out.println(ToPinyin("樲汉字转换为拼音")); 
        System.out.println(ToPinyin("行")); 
        System.out.println(Arrays.toString(PinyinHelper.toHanyuPinyinStringArray('行', defaultFormat))); 
        
        String n = "陈　伟";
        n = n.replaceAll("\r\n|\r|\n|\\s|　|", "");
        System.out.println(n);
    }
	/**
	 * 汉字转拼音缩写
	 * 
	 * @param str
	 *            要转换的汉字字符串
	 * @return String 拼音缩写
	 */
	public String getPYString(String str) {
		str = str.replaceAll("\r\n|\r|\n|\\s|　| ", "");
		return ToFirstChar(str);
		/*String tempStr = "";
		str = str.replaceAll("\r\n|\r|\n|\\s", "");
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= 33 && c <= 126) {// 字母和符号原样保留
				tempStr += String.valueOf(c);
			} else {// 累加拼音声母
				tempStr += getPYChar(String.valueOf(c));
			}
		}
		return tempStr;*/
	}

	/**
	 * 取单个字符的拼音声母
	 * 
	 * @param c
	 *            //要转换的单个汉字
	 * @return String 拼音声母
	 */
	public String getPYChar(String c) {
		
		//“翟”多音字，需要特殊处理
		if(c.equals("翟")){
			return "Z";
		}
		
		//“泸”无法识别，需要特殊处理
		if(c.equals("泸")){
			return "L";
		}
		
		byte[] array = new byte[2];
		array = String.valueOf(c).getBytes();
		int i = (short) (array[0] - '\0' + 256) * 256 + ((short) (array[1] - '\0' + 256));
		if (i < 0xB0A1)
			return "*";
		if (i < 0xB0C5)
			return "A";
		if (i < 0xB2C1)
			return "B";
		if (i < 0xB4EE)
			return "C";
		if (i < 0xB6EA)
			return "D";
		if (i < 0xB7A2)
			return "E";
		if (i < 0xB8C1)
			return "F";
		if (i < 0xB9FE)
			return "G";
		if (i < 0xBBF7)
			return "H";
		if (i < 0xBFA6)
			return "J";
		if (i < 0xC0AC)
			return "K";
		if (i < 0xC2E8)
			return "L";
		if (i < 0xC4C3)
			return "M";
		if (i < 0xC5B6)
			return "N";
		if (i < 0xC5BE)
			return "O";
		if (i < 0xC6DA)
			return "P";
		if (i < 0xC8BB)
			return "Q";
		if (i < 0xC8F6)
			return "R";
		if (i < 0xCBFA)
			return "S";
		if (i < 0xCDDA)
			return "T";
		if (i < 0xCEF4)
			return "W";
		if (i < 0xD1B9)
			return "X";
		if (i < 0xD4D1)
			return "Y";
		if (i < 0xD7FA)
			return "Z";
		return "*";
	}
	
	
	
	/**
     * 获取字符串拼音的第一个字母
     * @param chinese
     * @return
     */
    public static String ToFirstChar(String chinese){         
        String pinyinStr = "";  
        char[] newChar = chinese.toCharArray();  //转为单个字符
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat(); 
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);  
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        for (int i = 0; i < newChar.length; i++) {  
            if (newChar[i] > 128) {  
                try {  
                	//System.out.println(Arrays.toString(PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)));
                    pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0].charAt(0);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }else{  
                pinyinStr += newChar[i];  
            }  
        }  
        return pinyinStr;  
    }  
   
    /**
     * 汉字转为拼音
     * @param chinese
     * @return
     */
    public static String ToPinyin(String chinese){          
        String pinyinStr = "";  
        char[] newChar = chinese.toCharArray();  
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        for (int i = 0; i < newChar.length; i++) {  
            if (newChar[i] > 128) {  
                try {  
                    pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0];  
                } catch (BadHanyuPinyinOutputFormatCombination e) {  
                    e.printStackTrace();  
                }  
            }else{  
                pinyinStr += newChar[i];  
            }  
        }  
        return pinyinStr;  
    }  
	
	
	
}