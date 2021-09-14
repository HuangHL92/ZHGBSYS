package com.insigma.siis.local.pagemodel.gbmc.tpl;
public class NumberFormatUtil {
  
    static String[] units = {"","ʮ","��","ǧ","��","ʮ��","����","ǧ��","��","ʮ��","����","ǧ��","����" };  
    static char[] numArray = {'��','һ','��','��','��','��','��','��','��','��'};  

    /**
     * ������ת���ɺ�������
     * @param num ��Ҫת��������
     * @return ת����ĺ���
     */
    public static String formatInteger(int num) {  
        char[] val = String.valueOf(num).toCharArray();  
        int len = val.length;  
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < len; i++) {  
            String m = val[i] + "";  
            int n = Integer.valueOf(m);  
            boolean isZero = n == 0;  
            String unit = units[(len - 1) - i];  
            if (isZero) {  
                if ('0' == val[i - 1]) {  
                    continue;  
                } else {  
                    sb.append(numArray[n]);  
                }  
            } else {  
                sb.append(numArray[n]);  
                sb.append(unit);  
            }  
        }  
        return sb.toString();  
    }  
    /**
     * ��С��ת���ɺ�������
     * @param decimal ��Ҫת��������
     * @return ת����ĺ���
     */
    public static String formatDecimal(double decimal) {  
        String decimals = String.valueOf(decimal);  
        int decIndex = decimals.indexOf(".");  
        int integ = Integer.valueOf(decimals.substring(0, decIndex));  
        int dec = Integer.valueOf(decimals.substring(decIndex + 1));  
        String result = formatInteger(integ) + "." + formatFractionalPart(dec);  
        return result;  
    }

    /**
     * ��ʽ��С�����ֵ�����
     * @param decimal ��Ҫת��������
     * @return ת����ĺ���
     */
    public static String formatFractionalPart(int decimal) {
        char[] val = String.valueOf(decimal).toCharArray();  
        int len = val.length;  
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < len; i++) {  
            int n = Integer.valueOf(val[i] + "");  
            sb.append(numArray[n]);  
        }  
        return sb.toString();  
    }  
} 