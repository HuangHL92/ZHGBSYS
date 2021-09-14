package com.utils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A01;
import com.rxl.control.CountWordsSimilarity;

public class CountCSBySql {
	
	private CountWordsSimilarity cws;// �ı����ƶȼ�����

	private String sql;// ��ѯ��Ա��SQL

	private int limit = 20; // ������Ա�����ƶ�������ǰ limit ��

    
    public Map<String, keyWordSet> keywords;//�洢����ؼ��ּ���




	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}


	public CountCSBySql(String sql, int limit) {
		this.cws = new CountWordsSimilarity();
		this.sql = sql;

		this.limit = limit;
	}
    public class keyWordSet {
    	public String a1701_key;
    }
	/**
	 * �Ա���Ա���ƶ�
	 * ���ص�����Ա�����ƶ�������ǰ limit ����Ա����List
	 * @param a0000 �Ա���Ա����Ա����
	 * @return ��Ա����-���ƶ� ��ֵ��
	 */
	public Map<String, Double> get_Man_Similarity_Map(String a0000){
		HBSession session = HBUtil.getHBSession();
		//ȡ����Ա����
		A01 a01=(A01) session.get(A01.class, a0000);
		if(a01 ==null) {
			 
			return new HashMap<String, Double>(limit);
		}
		@SuppressWarnings("unchecked")
		List<A01> list = session.createSQLQuery(sql).addEntity(A01.class).list();
		
	
		
		return 	get_Man_Similarity_Map(a01,list);

		
	}

	public Map<String, Double> get_Man_Similarity_Map(A01 a01, List<A01> list) {
		Map<String, Double> map = new HashMap<String, Double>(CollectionsUtil.countInitTotal(list.size()));
		keywords = new HashMap<String, keyWordSet>(CollectionsUtil.countInitTotal(list.size()));
		for (A01 temp : list) {
			//�����Լ����Ա�
			if(a01.getA0000().equals(temp.getA0000()))
				continue;
			double Similarity = 0;
			keyWordSet kws = new keyWordSet();
			//ѧ��ѧλ�Ա�
			
			//�����Ա�
			Similarity+=cws.countSimilarityBySegmentation(StrUtils.replaceString(a01.getA1701()), StrUtils.replaceString(temp.getA1701()));
			
			CollectionsUtil.deleteMapIfZero(cws.getWordHZ(2));

			kws.a1701_key = cws.getWordHZ(2).keySet().toString();
		    
			map.put(temp.getA0000(), Similarity);
			keywords.put(temp.getA0000(), kws);
			
		}
		//��������map
		map = CollectionsUtil.sortMapByValue(map, false);
		//���ؽ�ȡǰlimit
		map = CollectionsUtil.subMap(map, limit, new LinkedHashMap<String, Double>());
		
		return map;
	}

	

    


}
