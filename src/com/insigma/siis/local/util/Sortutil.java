package com.insigma.siis.local.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A36;

public class Sortutil {
	
	/**
	 * Add chenys
	   *   根据家庭成员称谓计算排序序号
	 * @param list
	 * @return 各个称谓对应序号的HashMap
	 */
	public static List<BigDecimal> CreateSortId(List<String> list) {
		//初始化比较
		String tempa36_1= "丈夫,妻子";
		HashSet<String> a36set_1 = new HashSet<String>(Arrays.asList(tempa36_1.split(",")));
		String tempa36_2= "儿子,女儿,长子,次子,三子,四子,五子,养子,继子,继女,长女,次女,三女,四女,五女,养女,其他女儿";
		HashSet<String> a36set_2 = new HashSet<String>(Arrays.asList(tempa36_2.split(",")));
		String tempa36_3= "父亲,母亲";
		HashSet<String> a36set_3 = new HashSet<String>(Arrays.asList(tempa36_3.split(",")));
		//初始化各称谓序号
		Integer sort = 1;
		Integer sort_2=1;
		Integer sort_3=1;
		Integer sort_4=1;
		//存放各类称谓的A36
		HashMap<String,List<A36>> map = new HashMap<String,List<A36>>();
		map.put("配偶",new ArrayList<A36>());
		map.put("子女",new ArrayList<A36>());
		map.put("父母",new ArrayList<A36>());
		map.put("其他",new ArrayList<A36>());
		//循环
		for(int j=0;j<list.size();j++) {
			A36 a36 = new A36();
			//如果符合排序条件 则将称谓值以及当前序号存入hashMap中 
			a36.setA3604a(list.get(j).toString());
			if(tempa36_1.contains(list.get(j).toString())) { 
				a36.setSortid(new BigDecimal(sort));
				map.get("配偶").add(a36);
				 sort++;
				 continue;
			}else if(tempa36_2.contains(list.get(j).toString())) {
				a36.setSortid(new BigDecimal(sort_2));
				map.get("子女").add(a36);
					sort_2++;
				 continue;
			}else if(tempa36_3.contains(list.get(j).toString())) {
				a36.setSortid(new BigDecimal(sort_3));
				map.get("父母").add(a36);
					sort_3++;
				 continue;
			}else{
				a36.setSortid(new BigDecimal(sort_4));
				map.get("其他").add(a36);
					sort_4++;
				 continue;
			}
		}
		List<A36> a36Index = new ArrayList<A36>();
		Set<Entry<String, List<A36>>> a = map.entrySet();
		for (Entry<String, List<A36>> entry : a) {
			int i;
			List<A36> value = entry.getValue();
			if(entry.getKey().equals("子女")) {
				i= sort;
				for (A36 v : value) {
					v.setSortid(new BigDecimal(v.getSortid().intValue()+i));
				}
			}
			if(entry.getKey().equals("父母")) {
				i= sort+sort_2;
				for (A36 v : value) {
					v.setSortid(new BigDecimal(v.getSortid().intValue()+i));
				}
			}
			if(entry.getKey().equals("其他")) {
				i= sort+sort_2+sort_3;
				for (A36 v : value) {
					v.setSortid(new BigDecimal(v.getSortid().intValue()+i));
				}
			}
		}
		a36Index.addAll(map.get("配偶"));
		a36Index.addAll(map.get("子女"));
		a36Index.addAll(map.get("父母"));
		a36Index.addAll(map.get("其他"));
		//按照任免表称谓读取顺序 生成对应的排序号list
		List<BigDecimal> sortLst = new ArrayList<BigDecimal>();
		for(Object str: list) {
			for (int j=0;j<a36Index.size();j++) {
				if(str.toString().equals(a36Index.get(j).getA3604a())) {
					sortLst.add(a36Index.get(j).getSortid());
					a36Index.remove(j);
					break;
				};
			}
		}
		return sortLst;
	}
	/**
	 * Add chenys
	 * 更新家庭成员表中的排序
	 * @param tableName
	 */
	public static void updateA36Sort(String tableName) {
		 HBSession sess = HBUtil.getHBSession();
		//更新临时表中家庭成员排序
		String updateSql = "BEGIN\r\n" + 
				"  FOR A36ID IN (SELECT distinct a0000 FROM "+tableName+") LOOP\r\n" + 
				"      update "+tableName+" a1 set sortid=\r\n" + 
				"  (select  row1  from (select row_number() over( order by \r\n" + 
				"       case when A3604A  in ('丈夫','妻子') then \r\n" + 
				"                 DECODE(A3604A,A3604A,0,updated)\r\n" + 
				"            when  A3604A in ('儿子','女儿','长子','次子','三子','四子','五子','养子','继子','继女','长女','次女','三女','四女','五女','养女','其他女儿') then \r\n" + 
				"                 DECODE(A3604A,A3604A,1,updated)\r\n" + 
				"        end,updated\r\n" + 
				"         )  as  row1 ,a3600 from "+tableName+" a2 where a2.a0000=A36ID.a0000) a2\r\n" + 
				"    where a2.a3600 = a1.a3600)\r\n" + 
				"where a0000=A36ID.a0000;\r\n" + 
				"END LOOP;END;";
		sess.createSQLQuery(updateSql).executeUpdate();
	}
	/**
	 * Add chenys
	 * 更新家庭成员表中人员类别
	 * @param tableName
	 */
	public static void updateA36Updated(String tableName) {
		 HBSession sess = HBUtil.getHBSession();
		//增加家庭成员类别值
		String updateUpdated = "update "+tableName+" a set updated = (select distinct c.sub_code_value from code_value c where c.code_Type='GB4761' and sub_code_value != -1 and c. code_value=a.a3604a)" ; 
		sess.createSQLQuery(updateUpdated).executeUpdate();
	}
	
	
}
