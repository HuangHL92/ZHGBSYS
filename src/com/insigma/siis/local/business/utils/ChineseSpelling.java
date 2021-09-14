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
     * ����main����
     * @param args
	 * @throws BadHanyuPinyinOutputFormatCombination 
     */
    public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {
    	HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat(); 
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        defaultFormat.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        System.out.println(ToFirstChar("�޽�쿳�").toUpperCase()); //תΪ����ĸ��д
        System.out.println(ToPinyin("�޺���ת��Ϊƴ��")); 
        System.out.println(ToPinyin("��")); 
        System.out.println(Arrays.toString(PinyinHelper.toHanyuPinyinStringArray('��', defaultFormat))); 
        
        String n = "�¡�ΰ";
        n = n.replaceAll("\r\n|\r|\n|\\s|��|", "");
        System.out.println(n);
    }
	/**
	 * ����תƴ����д
	 * 
	 * @param str
	 *            Ҫת���ĺ����ַ���
	 * @return String ƴ����д
	 */
	public String getPYString(String str) {
		str = str.replaceAll("\r\n|\r|\n|\\s|��| ", "");
		return ToFirstChar(str);
		/*String tempStr = "";
		str = str.replaceAll("\r\n|\r|\n|\\s", "");
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= 33 && c <= 126) {// ��ĸ�ͷ���ԭ������
				tempStr += String.valueOf(c);
			} else {// �ۼ�ƴ����ĸ
				tempStr += getPYChar(String.valueOf(c));
			}
		}
		return tempStr;*/
	}

	/**
	 * ȡ�����ַ���ƴ����ĸ
	 * 
	 * @param c
	 *            //Ҫת���ĵ�������
	 * @return String ƴ����ĸ
	 */
	public String getPYChar(String c) {
		
		//���ԡ������֣���Ҫ���⴦��
		if(c.equals("��")){
			return "Z";
		}
		
		//�����޷�ʶ����Ҫ���⴦��
		if(c.equals("��")){
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
     * ��ȡ�ַ���ƴ���ĵ�һ����ĸ
     * @param chinese
     * @return
     */
    public static String ToFirstChar(String chinese){         
        String pinyinStr = "";  
        char[] newChar = chinese.toCharArray();  //תΪ�����ַ�
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
     * ����תΪƴ��
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