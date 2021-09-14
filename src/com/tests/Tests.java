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
		 * System.out.println(StringEscapeUtils.escapeXml("\"asdasŶdf\"<>"));
		 * System.out.println(StringEscapeUtils.unescapeXml("&UIŶ&quot;&lt;&gt;"));
		 */
	public static void A01_A15() {
		HBSession sess = HBUtil.getHBSession();
		List<Object[]> a15s = sess.createSQLQuery("select a01.a0000,a15z101 from a01 where a01.a15z101 is not null").list();
		for(Object[] vals : a15s) {
			String a0000 = vals[0].toString();
			String val = vals[1].toString();
			try{
				String khxx = val.replace("��", ";").replace("��", ";").replace(",", ";").trim();
				if(khxx.contains(";")){		//����
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
						if(khxx_str.contains("������ְ")){
							a1517 = "3";
						}else if(khxx_str.contains("����ְ")){
							a1517 = "4";
						}else if(khxx_str.contains("����")){
							a1517 = "1";
						}else if(khxx_str.contains("��ְ")){
							a1517 = "2";
						}else if(khxx_str.contains("�����ϸ�")){
							a1517 = "3B";
						}else if(khxx_str.contains("���ϸ�")){
							a1517 = "4B";
						}else if(khxx_str.contains("�ϸ�")){
							a1517 = "2B";
						}else if(khxx_str.contains("�����ȴ�")){
							a1517 = "5";
						}else if(khxx_str.contains("��¼����Ա��������ȿ��˲�ȷ���ȴ�")){
							a1517 = "5A";
						}else if(khxx_str.contains("������������δ�᰸��ȿ��˲�ȷ���ȴ�")){
							a1517 = "5B";
						}else if(khxx_str.contains("�����ʹ����ڼ���ȿ��˲�ȷ���ȴ�")){
							a1517 = "5C";
						}else if(khxx_str.contains("�����¼��ۼƳ���������Ȱ��겻���п���")){
							a1517 = "6A";
						}else if(khxx_str.contains("����ԭ�򲻽��п���")){
							a1517 = "6B";
						}else if(khxx_str.contains("�����п���")){
							a1517 = "6";
						}
						a15.setA1517(a1517);
						a15.setA1527("" + khxx_arr.length);
						sess.save(a15);
					}
				}else{				//����
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
						if(khxx.contains("������ְ")){
							a1517 = "3";
						}else if(khxx.contains("����ְ")){
							a1517 = "4";
						}else if(khxx.contains("����")){
							a1517 = "1";
						}else if(khxx.contains("��ְ")){
							a1517 = "2";
						}else if(khxx.contains("�����ϸ�")){
							a1517 = "3B";
						}else if(khxx.contains("���ϸ�")){
							a1517 = "4B";
						}else if(khxx.contains("�ϸ�")){
							a1517 = "2B";
						}else if(khxx.contains("�����ȴ�")){
							a1517 = "5";
						}else if(khxx.contains("��¼����Ա��������ȿ��˲�ȷ���ȴ�")){
							a1517 = "5A";
						}else if(khxx.contains("������������δ�᰸��ȿ��˲�ȷ���ȴ�")){
							a1517 = "5B";
						}else if(khxx.contains("�����ʹ����ڼ���ȿ��˲�ȷ���ȴ�")){
							a1517 = "5C";
						}else if(khxx.contains("�����¼��ۼƳ���������Ȱ��겻���п���")){
							a1517 = "6A";
						}else if(khxx.contains("����ԭ�򲻽��п���")){
							a1517 = "6B";
						}else if(khxx.contains("�����п���")){
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
		
		System.out.println("����ɹ�");
	}
}
