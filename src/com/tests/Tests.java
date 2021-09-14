package com.tests;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A15;

public class Tests {

	//public static void main(String[] args) {  
		/*
		 * System.out.println(Integer.MAX_VALUE);
		 * System.out.println(StringEscapeUtils.escapeXml("\"asdas哦df\"<>"));
		 * System.out.println(StringEscapeUtils.unescapeXml("&UI哦&quot;&lt;&gt;"));
		 */
	public static void A01_A15() {
		HBSession sess = HBUtil.getHBSession();
		List<Object[]> a15s = sess.createSQLQuery("select a01.a0000,a15z101 from a01 where a01.a15z101 is not null").list();
		for(Object[] vals : a15s) {
			String a0000 = vals[0].toString();
			String val = vals[1].toString();
			try{
				String khxx = val.replace("；", ";").replace("，", ";").replace(",", ";").trim();
				if(khxx.contains(";")){		//多条
					String[] khxx_arr = khxx.split(";");
					for(int i = 0; i < khxx_arr.length; i++){
						String khxx_str = khxx_arr[i].trim();
						String regEx="[^0-9]";   
						Pattern p = Pattern.compile(regEx);   
						Matcher m = p.matcher(khxx_str);
						String a1521 = m.replaceAll("").trim();
						if(a1521.length() > 4 || a1521.length() < 4){
							continue;
						}
						A15 a15 = new A15();
						a15.setA0000(a0000);
						a15.setA1521(a1521);
						String a1517 = "";
						if(khxx_str.contains("基本称职")){
							a1517 = "3";
						}else if(khxx_str.contains("不称职")){
							a1517 = "4";
						}else if(khxx_str.contains("优秀")){
							a1517 = "1";
						}else if(khxx_str.contains("称职")){
							a1517 = "2";
						}else if(khxx_str.contains("基本合格")){
							a1517 = "3B";
						}else if(khxx_str.contains("不合格")){
							a1517 = "4B";
						}else if(khxx_str.contains("合格")){
							a1517 = "2B";
						}else if(khxx_str.contains("不定等次")){
							a1517 = "5";
						}else if(khxx_str.contains("新录用人员试用期年度考核不确定等次")){
							a1517 = "5A";
						}else if(khxx_str.contains("被立案调查尚未结案年度考核不确定等次")){
							a1517 = "5B";
						}else if(khxx_str.contains("受政纪处分期间年度考核不确定等次")){
							a1517 = "5C";
						}else if(khxx_str.contains("病、事假累计超过考核年度半年不进行考核")){
							a1517 = "6A";
						}else if(khxx_str.contains("其他原因不进行考核")){
							a1517 = "6B";
						}else if(khxx_str.contains("不进行考核")){
							a1517 = "6";
						}
						a15.setA1517(a1517);
						a15.setA1527("" + khxx_arr.length);
						sess.save(a15);
					}
				}else{				//单条
					String regEx="[^0-9]";   
					Pattern p = Pattern.compile(regEx);   
					Matcher m = p.matcher(khxx);
					String a1521 = m.replaceAll("").trim();
					if(a1521.length() > 4 || a1521.length() < 4){
						
					}else{
						A15 a15 = new A15();
						a15.setA0000(a0000);
						a15.setA1521(a1521);
						String a1517 = "";
						if(khxx.contains("基本称职")){
							a1517 = "3";
						}else if(khxx.contains("不称职")){
							a1517 = "4";
						}else if(khxx.contains("优秀")){
							a1517 = "1";
						}else if(khxx.contains("称职")){
							a1517 = "2";
						}else if(khxx.contains("基本合格")){
							a1517 = "3B";
						}else if(khxx.contains("不合格")){
							a1517 = "4B";
						}else if(khxx.contains("合格")){
							a1517 = "2B";
						}else if(khxx.contains("不定等次")){
							a1517 = "5";
						}else if(khxx.contains("新录用人员试用期年度考核不确定等次")){
							a1517 = "5A";
						}else if(khxx.contains("被立案调查尚未结案年度考核不确定等次")){
							a1517 = "5B";
						}else if(khxx.contains("受政纪处分期间年度考核不确定等次")){
							a1517 = "5C";
						}else if(khxx.contains("病、事假累计超过考核年度半年不进行考核")){
							a1517 = "6A";
						}else if(khxx.contains("其他原因不进行考核")){
							a1517 = "6B";
						}else if(khxx.contains("不进行考核")){
							a1517 = "6";
						}
						a15.setA1517(a1517);
						a15.setA1527("1");
						sess.save(a15);
					}
				}
			}catch(Exception e){
				
			}
		}
		
		System.out.println("保存成功");
	}
}
