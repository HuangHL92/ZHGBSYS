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
	 * 使用 Map按value进行排序,Map<T2, T> ,flag 为true 正序排序 反之倒叙
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
				// 按照从大到小的顺序排列，如果想正序 调换me1 me2的位置,全部向'上'转型防止数据溢出
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
	 * 去除Map中value为零的数
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
	 * 计算容器的初始容量
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
	 * 截取map前size条导入到t中
	 * @param <T1> map Key类型
	 * @param <T2> map Value类型
	 * @param <T> 导入map类型
	 * @param map 被截取的map
	 * @param size 截取前多少
	 * @param t 导入的map
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