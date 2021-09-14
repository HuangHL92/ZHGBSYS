package com.insigma.siis.local.pagemodel.pps;

import java.util.HashMap;
import java.util.Map;

public class StaticData2 {
	public HashMap<String,int[]> mapAll = new HashMap<String, int[]>(); 
	public HashMap<String,int[]> initMap(int number,String type) {
		int falg = (int) (number*0.25);
		int falg1 = (int) (number*0.5);
		int sum = 0;
		
		//地方
		if(type.equals("1")) {
			int temp = (int) (falg+falg+falg1);
			sum += (number-temp);
			int b = 0;
			if(sum==0) {
				b=0;
			}else {
				sum--;
				b=1;
			}
			mapAll.put("100", new int[] {falg+b,0});//厅
			if(sum==0) {
				b=0;
			}else {
				sum--;
				b=1;
			}
			mapAll.put("101", new int[] {falg+b,0});//处
			if(sum==0) {
				b=0;
			}else {
				sum--;
				b=1;
			}
			mapAll.put("102", new int[] {falg1+b,0});//科以下
			
			
//			temp = (int) (falg1+falg1);
//			sum += (number-temp);
//			b=0;
//			if(sum==0) {
//				b=0;
//			}else {
//				sum--;
//				b=1;
//			}
//			mapAll.put("103", new int[] {falg1+b,0});//领导
//			if(sum==0) {
//				b=0;
//			}else {
//				sum--;
//				b=1;
//			}
//			mapAll.put("104", new int[] {falg1+b,0});//非领导
//			temp = (int) (falg+falg+falg+falg);
//			sum += (number-temp);
//			b=0;
//			if(sum==0) {
//				b=0;
//			}else {
//				sum--;
//				b=1;
//			}
//			mapAll.put("105", new int[] {falg+b,0});//省
//			if(sum==0) {
//				b=0;
//			}else {
//				sum--;
//				b=1;
//			}
//			mapAll.put("106", new int[] {falg+b,0});//市
//			if(sum==0) {
//				b=0;
//			}else {
//				sum--;
//				b=1;
//			}
//			mapAll.put("107", new int[] {falg+b,0});//县
//			if(sum==0) {
//				b=0;
//			}else {
//				sum--;
//				b=1;
//			}
//			mapAll.put("108", new int[] {falg+b,0});//乡
		}else {
			int temp = (int) (falg+falg+falg1);
			sum = number-temp;
			int b = 0;
			if(sum==0) {
				b=0;
			}else {
				sum--;
				b=1;
			}
			mapAll.put("100", new int[] {falg+b,0});//厅
			if(sum==0) {
				b=0;
			}else {
				sum--;
				b=1;
			}
			mapAll.put("101", new int[] {falg+b,0});//处
			if(sum==0) {
				b=0;
			}else {
				sum--;
				b=1;
			}
			mapAll.put("102", new int[] {falg1+b,0});//科以下
			
//			temp = (int) (falg1+falg1);
//			sum += (number-temp);
//			b=0;
//			if(sum==0) {
//				b=0;
//			}else {
//				sum--;
//				b=1;
//			}
//			mapAll.put("103", new int[] {falg1+b,0});//领导
//			if(sum==0) {
//				b=0;
//			}else {
//				sum--;
//				b=1;
//			}
//			mapAll.put("104", new int[] {falg1+b,0});//非领导
//			mapAll.put("109", new int[] {number,0});//中央
		}
		for (Map.Entry<String, int[]> entry : mapAll.entrySet()) {
           System.out.println(entry.getKey() +"------"+ 
                   + entry.getValue()[0]);
        }
		return mapAll;
	}
	
	/*//清空
	public static HashMap mapReset;
	public static HashMap<String,HashMap<String,int[]>> map = new HashMap<String, HashMap<String,int[]>>();
	public static HashMap<String,int[]> mapSpot = new HashMap<String,int[]>();
	public static Map initMap() {
	//抽数 150人
		mapSpot.put("100", new int[]{38,0});
		mapSpot.put("101", new int[]{37,0});
		mapSpot.put("102", new int[]{75,0});
		mapSpot.put("103", new int[]{75,0});
		mapSpot.put("104", new int[]{75,0});
		mapSpot.put("105", new int[]{38,0});
		mapSpot.put("106", new int[]{38,0});
		mapSpot.put("107", new int[]{37,0});
		mapSpot.put("108", new int[]{37,0});
		
		//广东省
		map.put("D49", mapSpot);
		//四川省
		map.put("E19", mapSpot);
		//山东省
		map.put("C20", mapSpot);
		//江苏省
		map.put("C29",mapSpot);
		//河南省
		map.put("D69", mapSpot);
		//河北省
		map.put("A39", mapSpot);
		//湖南省
		map.put("D39", mapSpot);
		//浙江省
		map.put("C39", mapSpot);
		//云南省
		map.put("E39.A01.001.10", mapSpot);
		//辽宁省
		map.put("B19", mapSpot);
		//湖北省
		map.put("D29", mapSpot);
		mapSpot.clear();
		//国税总局(属于本级)
		mapSpot.put("100", new int[]{38,0});
		mapSpot.put("101", new int[]{37,0});
		mapSpot.put("102", new int[]{75,0});
		mapSpot.put("103", new int[]{75,0});
		mapSpot.put("104", new int[]{75,0});
		map.put("513", mapSpot);
		mapSpot.clear();
	//抽数 100人
		mapSpot.put("100", new int[]{25,0});
		mapSpot.put("101", new int[]{25,0});
		mapSpot.put("102", new int[]{50,0});
		mapSpot.put("103", new int[]{50,0});
		mapSpot.put("104", new int[]{50,0});
		mapSpot.put("105", new int[]{25,0});
		mapSpot.put("106", new int[]{25,0});
		mapSpot.put("107", new int[]{25,0});
		mapSpot.put("108", new int[]{25,0});
		//广西壮族自治区
		map.put("D59", mapSpot);
		//安徽省
		map.put("C49", mapSpot);
		//黑龙江省
		map.put("B39", mapSpot);
		//陕西省
		map.put("F19", mapSpot);
		//新疆维吾尔自治区
		map.put("F59", mapSpot);
		//江西省
		map.put("C69", mapSpot);
		//福建省
		map.put("C59", mapSpot);
		//山西省
		map.put("A49", mapSpot);
		//内蒙古自治区
		map.put("A59", mapSpot);
		//贵州省
		map.put("E29", mapSpot);
		//甘肃省
		map.put("F29", mapSpot);
		//吉林省	
		map.put("B29", mapSpot);
		mapSpot.clear();
	//抽数 50人
		//直辖市  没有省，县，乡
		mapSpot.put("100", new int[]{13,0});
		mapSpot.put("101", new int[]{12,0});
		mapSpot.put("102", new int[]{25,0});
		mapSpot.put("103", new int[]{25,0});
		mapSpot.put("104", new int[]{25,0});
		mapSpot.put("106", new int[]{50,0});
		//重庆市
		map.put("E09", mapSpot);
		//北京市
		map.put("A19", mapSpot);
		//上海市
		map.put("C19", mapSpot);
		//天津
		map.put("A29", mapSpot);
		mapSpot.clear();
		
		mapSpot.put("100", new int[]{13,0});
		mapSpot.put("101", new int[]{12,0});
		mapSpot.put("102", new int[]{25,0});
		mapSpot.put("103", new int[]{25,0});
		mapSpot.put("104", new int[]{25,0});
		mapSpot.put("105", new int[]{13,0});
		mapSpot.put("106", new int[]{13,0});
		mapSpot.put("107", new int[]{12,0});
		mapSpot.put("108", new int[]{12,0});
		//西藏自治区
		map.put("E49", mapSpot);
		//青海省
		map.put("E39", mapSpot);
		//宁夏回族自治区
		map.put("F39", mapSpot);
		//海南省
		map.put("D69", mapSpot);
		//海关总署
		map.put("419", mapSpot);
		//质检总局
		map.put("517", mapSpot);
		//银监会
		map.put("587", mapSpot);
		//气象局
		map.put("585", mapSpot);
		mapSpot.clear();
		//铁路公安局(属于本级以下部分)
		mapSpot.put("100", new int[]{13,0});
		mapSpot.put("101", new int[]{12,0});
		mapSpot.put("102", new int[]{25,0});
		mapSpot.put("103", new int[]{25,0});
		mapSpot.put("104", new int[]{25,0});
		map.put("645", mapSpot);
		//新疆兵团
		map.put("419", mapSpot);
		//交通部
		map.put("449", mapSpot);
		//531.V02统计局
		map.put("531.V02", mapSpot);
		//427公安局
		map.put("427", mapSpot);
		mapSpot.clear();
		
	//抽取30人
		//本级

		return map;
	}
	
*/
}
