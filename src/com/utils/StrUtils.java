package com.utils;


import com.fr.stable.StringUtils;

import java.sql.Array;

public class StrUtils {
	/**
	 * 处理简历的格式，为简历相似度匹配做前置准备
	 * @param s
	 * @return
	 */
	public static String replaceString(String s) {
		String[] split = StringUtils.isEmpty(s)?new String[0] :s.split("\\n");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < split.length; i++) {
			String[] split2 = split[i].split("  ");
			String temp = "";
			if(split2.length!=0)
			 temp = split2[split2.length - 1] + " ";
			sb.append(temp.replace("\r", ""));
		}
		return sb.toString();
	}

	/**
	 * 关键字处理
	 * @param a1701_key
	 * @param i
	 * @return
	 */
	public static String getKeys(String a1701_key, int i) {

		a1701_key = a1701_key.replace("[", "");

		a1701_key = a1701_key.replace("]", "");

		String[] split = a1701_key.split(",");
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < split.length&&j<i; j++) {
			sb.append(split[j]);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
}
