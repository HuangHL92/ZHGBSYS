package com.insigma.siis.local.pagemodel.pps;

import java.util.HashMap;
import java.util.Map;

public class StaticData2 {
	public HashMap<String,int[]> mapAll = new HashMap<String, int[]>(); 
	public HashMap<String,int[]> initMap(int number,String type) {
		int falg = (int) (number*0.25);
		int falg1 = (int) (number*0.5);
		int sum = 0;
		
		//�ط�
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
			mapAll.put("100", new int[] {falg+b,0});//��
			if(sum==0) {
				b=0;
			}else {
				sum--;
				b=1;
			}
			mapAll.put("101", new int[] {falg+b,0});//��
			if(sum==0) {
				b=0;
			}else {
				sum--;
				b=1;
			}
			mapAll.put("102", new int[] {falg1+b,0});//������
			
			
//			temp = (int) (falg1+falg1);
//			sum += (number-temp);
//			b=0;
//			if(sum==0) {
//				b=0;
//			}else {
//				sum--;
//				b=1;
//			}
//			mapAll.put("103", new int[] {falg1+b,0});//�쵼
//			if(sum==0) {
//				b=0;
//			}else {
//				sum--;
//				b=1;
//			}
//			mapAll.put("104", new int[] {falg1+b,0});//���쵼
//			temp = (int) (falg+falg+falg+falg);
//			sum += (number-temp);
//			b=0;
//			if(sum==0) {
//				b=0;
//			}else {
//				sum--;
//				b=1;
//			}
//			mapAll.put("105", new int[] {falg+b,0});//ʡ
//			if(sum==0) {
//				b=0;
//			}else {
//				sum--;
//				b=1;
//			}
//			mapAll.put("106", new int[] {falg+b,0});//��
//			if(sum==0) {
//				b=0;
//			}else {
//				sum--;
//				b=1;
//			}
//			mapAll.put("107", new int[] {falg+b,0});//��
//			if(sum==0) {
//				b=0;
//			}else {
//				sum--;
//				b=1;
//			}
//			mapAll.put("108", new int[] {falg+b,0});//��
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
			mapAll.put("100", new int[] {falg+b,0});//��
			if(sum==0) {
				b=0;
			}else {
				sum--;
				b=1;
			}
			mapAll.put("101", new int[] {falg+b,0});//��
			if(sum==0) {
				b=0;
			}else {
				sum--;
				b=1;
			}
			mapAll.put("102", new int[] {falg1+b,0});//������
			
//			temp = (int) (falg1+falg1);
//			sum += (number-temp);
//			b=0;
//			if(sum==0) {
//				b=0;
//			}else {
//				sum--;
//				b=1;
//			}
//			mapAll.put("103", new int[] {falg1+b,0});//�쵼
//			if(sum==0) {
//				b=0;
//			}else {
//				sum--;
//				b=1;
//			}
//			mapAll.put("104", new int[] {falg1+b,0});//���쵼
//			mapAll.put("109", new int[] {number,0});//����
		}
		for (Map.Entry<String, int[]> entry : mapAll.entrySet()) {
           System.out.println(entry.getKey() +"------"+ 
                   + entry.getValue()[0]);
        }
		return mapAll;
	}
	
	/*//���
	public static HashMap mapReset;
	public static HashMap<String,HashMap<String,int[]>> map = new HashMap<String, HashMap<String,int[]>>();
	public static HashMap<String,int[]> mapSpot = new HashMap<String,int[]>();
	public static Map initMap() {
	//���� 150��
		mapSpot.put("100", new int[]{38,0});
		mapSpot.put("101", new int[]{37,0});
		mapSpot.put("102", new int[]{75,0});
		mapSpot.put("103", new int[]{75,0});
		mapSpot.put("104", new int[]{75,0});
		mapSpot.put("105", new int[]{38,0});
		mapSpot.put("106", new int[]{38,0});
		mapSpot.put("107", new int[]{37,0});
		mapSpot.put("108", new int[]{37,0});
		
		//�㶫ʡ
		map.put("D49", mapSpot);
		//�Ĵ�ʡ
		map.put("E19", mapSpot);
		//ɽ��ʡ
		map.put("C20", mapSpot);
		//����ʡ
		map.put("C29",mapSpot);
		//����ʡ
		map.put("D69", mapSpot);
		//�ӱ�ʡ
		map.put("A39", mapSpot);
		//����ʡ
		map.put("D39", mapSpot);
		//�㽭ʡ
		map.put("C39", mapSpot);
		//����ʡ
		map.put("E39.A01.001.10", mapSpot);
		//����ʡ
		map.put("B19", mapSpot);
		//����ʡ
		map.put("D29", mapSpot);
		mapSpot.clear();
		//��˰�ܾ�(���ڱ���)
		mapSpot.put("100", new int[]{38,0});
		mapSpot.put("101", new int[]{37,0});
		mapSpot.put("102", new int[]{75,0});
		mapSpot.put("103", new int[]{75,0});
		mapSpot.put("104", new int[]{75,0});
		map.put("513", mapSpot);
		mapSpot.clear();
	//���� 100��
		mapSpot.put("100", new int[]{25,0});
		mapSpot.put("101", new int[]{25,0});
		mapSpot.put("102", new int[]{50,0});
		mapSpot.put("103", new int[]{50,0});
		mapSpot.put("104", new int[]{50,0});
		mapSpot.put("105", new int[]{25,0});
		mapSpot.put("106", new int[]{25,0});
		mapSpot.put("107", new int[]{25,0});
		mapSpot.put("108", new int[]{25,0});
		//����׳��������
		map.put("D59", mapSpot);
		//����ʡ
		map.put("C49", mapSpot);
		//������ʡ
		map.put("B39", mapSpot);
		//����ʡ
		map.put("F19", mapSpot);
		//�½�ά���������
		map.put("F59", mapSpot);
		//����ʡ
		map.put("C69", mapSpot);
		//����ʡ
		map.put("C59", mapSpot);
		//ɽ��ʡ
		map.put("A49", mapSpot);
		//���ɹ�������
		map.put("A59", mapSpot);
		//����ʡ
		map.put("E29", mapSpot);
		//����ʡ
		map.put("F29", mapSpot);
		//����ʡ	
		map.put("B29", mapSpot);
		mapSpot.clear();
	//���� 50��
		//ֱϽ��  û��ʡ���أ���
		mapSpot.put("100", new int[]{13,0});
		mapSpot.put("101", new int[]{12,0});
		mapSpot.put("102", new int[]{25,0});
		mapSpot.put("103", new int[]{25,0});
		mapSpot.put("104", new int[]{25,0});
		mapSpot.put("106", new int[]{50,0});
		//������
		map.put("E09", mapSpot);
		//������
		map.put("A19", mapSpot);
		//�Ϻ���
		map.put("C19", mapSpot);
		//���
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
		//����������
		map.put("E49", mapSpot);
		//�ຣʡ
		map.put("E39", mapSpot);
		//���Ļ���������
		map.put("F39", mapSpot);
		//����ʡ
		map.put("D69", mapSpot);
		//��������
		map.put("419", mapSpot);
		//�ʼ��ܾ�
		map.put("517", mapSpot);
		//�����
		map.put("587", mapSpot);
		//�����
		map.put("585", mapSpot);
		mapSpot.clear();
		//��·������(���ڱ������²���)
		mapSpot.put("100", new int[]{13,0});
		mapSpot.put("101", new int[]{12,0});
		mapSpot.put("102", new int[]{25,0});
		mapSpot.put("103", new int[]{25,0});
		mapSpot.put("104", new int[]{25,0});
		map.put("645", mapSpot);
		//�½�����
		map.put("419", mapSpot);
		//��ͨ��
		map.put("449", mapSpot);
		//531.V02ͳ�ƾ�
		map.put("531.V02", mapSpot);
		//427������
		map.put("427", mapSpot);
		mapSpot.clear();
		
	//��ȡ30��
		//����

		return map;
	}
	
*/
}
