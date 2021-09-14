package com.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CollectionsUtil {
	/**
	 * ʹ�� Map��value��������,Map<T2, T> ,flag Ϊtrue �������� ��֮����
	 * 
	 * @param oriMap
	 * @param flag
	 * @return
	 */
	public static <T2, T extends Number> Map<T2, T> sortMapByValue(Map<T2, T> oriMap, final boolean flag) {
		if (oriMap == null || oriMap.isEmpty()) {
			return null;
		}
		Map<T2, T> sortedMap = new LinkedHashMap<T2, T>();
		List<Map.Entry<T2, T>> entryList = new ArrayList<Map.Entry<T2, T>>(oriMap.entrySet());
		Collections.sort(entryList, new Comparator<Map.Entry<T2, T>>() {
			@Override
			public int compare(Entry<T2, T> me1, Entry<T2, T> me2) {
				// ���մӴ�С��˳�����У���������� ����me1 me2��λ��,ȫ����'��'ת�ͷ�ֹ�������
				Double t1 = me1.getValue().doubleValue();

				Double t2 = me2.getValue().doubleValue();
				if (flag)
					return t1.compareTo(t2);
				else
					return t2.compareTo(t1);
			}
		});
		Iterator<Map.Entry<T2, T>> iter = entryList.iterator();
		Map.Entry<T2, T> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
		}
		return sortedMap;
	}

	/**
	 * ȥ��Map��valueΪ�����
	 * 
	 * @param oriMap
	 * @return
	 */
	public static <T2, T extends Number> void deleteMapIfZero(Map<T2, T> oriMap) {
		if (oriMap == null || oriMap.isEmpty()) {
			return;
		}
		Set<Entry<T2, T>> entrySet = oriMap.entrySet();
		Iterator<Entry<T2, T>> it = entrySet.iterator();
		while(it.hasNext())
		{
			
			if(it.next().getValue().doubleValue()==0)
				it.remove();
		}

	}

	/**
	 * ���������ĳ�ʼ����
	 * 
	 * @param size
	 * @return
	 */
	public static int countInitTotal(int size) {
		int count = size / 3 * 4 + 1;
		if (count % 2 != 0)
			count++;
		return count;
	}


	/**
	 * ��ȡmapǰsize�����뵽t��
	 * @param <T1> map Key����
	 * @param <T2> map Value����
	 * @param <T> ����map����
	 * @param map ����ȡ��map
	 * @param size ��ȡǰ����
	 * @param t �����map
	 * @return
	 */
	public static <T1, T2,T extends Map<T1, T2>> Map<T1, T2> subMap(Map<T1, T2> map, int size,T  t) {
		if(t==null)
			return t;
		Map<T1, T2> tempMap = new LinkedHashMap<T1, T2>(countInitTotal(size));
		Set<Entry<T1,T2>> entrySet = map.entrySet();
		for (Entry<T1, T2> entry : entrySet) {
			if(size<=0)
				break;
			tempMap.put(entry.getKey(), entry.getValue());
			size--;
		}
		
		t.putAll(tempMap);

		return t;
	}
}