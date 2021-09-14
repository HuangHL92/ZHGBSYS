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
	   *   ���ݼ�ͥ��Ա��ν�����������
	 * @param list
	 * @return ������ν��Ӧ��ŵ�HashMap
	 */
	public static List<BigDecimal> CreateSortId(List<String> list) {
		//��ʼ���Ƚ�
		String tempa36_1= "�ɷ�,����";
		HashSet<String> a36set_1 = new HashSet<String>(Arrays.asList(tempa36_1.split(",")));
		String tempa36_2= "����,Ů��,����,����,����,����,����,����,����,��Ů,��Ů,��Ů,��Ů,��Ů,��Ů,��Ů,����Ů��";
		HashSet<String> a36set_2 = new HashSet<String>(Arrays.asList(tempa36_2.split(",")));
		String tempa36_3= "����,ĸ��";
		HashSet<String> a36set_3 = new HashSet<String>(Arrays.asList(tempa36_3.split(",")));
		//��ʼ������ν���
		Integer sort = 1;
		Integer sort_2=1;
		Integer sort_3=1;
		Integer sort_4=1;
		//��Ÿ����ν��A36
		HashMap<String,List<A36>> map = new HashMap<String,List<A36>>();
		map.put("��ż",new ArrayList<A36>());
		map.put("��Ů",new ArrayList<A36>());
		map.put("��ĸ",new ArrayList<A36>());
		map.put("����",new ArrayList<A36>());
		//ѭ��
		for(int j=0;j<list.size();j++) {
			A36 a36 = new A36();
			//��������������� �򽫳�νֵ�Լ���ǰ��Ŵ���hashMap�� 
			a36.setA3604a(list.get(j).toString());
			if(tempa36_1.contains(list.get(j).toString())) { 
				a36.setSortid(new BigDecimal(sort));
				map.get("��ż").add(a36);
				 sort++;
				 continue;
			}else if(tempa36_2.contains(list.get(j).toString())) {
				a36.setSortid(new BigDecimal(sort_2));
				map.get("��Ů").add(a36);
					sort_2++;
				 continue;
			}else if(tempa36_3.contains(list.get(j).toString())) {
				a36.setSortid(new BigDecimal(sort_3));
				map.get("��ĸ").add(a36);
					sort_3++;
				 continue;
			}else{
				a36.setSortid(new BigDecimal(sort_4));
				map.get("����").add(a36);
					sort_4++;
				 continue;
			}
		}
		List<A36> a36Index = new ArrayList<A36>();
		Set<Entry<String, List<A36>>> a = map.entrySet();
		for (Entry<String, List<A36>> entry : a) {
			int i;
			List<A36> value = entry.getValue();
			if(entry.getKey().equals("��Ů")) {
				i= sort;
				for (A36 v : value) {
					v.setSortid(new BigDecimal(v.getSortid().intValue()+i));
				}
			}
			if(entry.getKey().equals("��ĸ")) {
				i= sort+sort_2;
				for (A36 v : value) {
					v.setSortid(new BigDecimal(v.getSortid().intValue()+i));
				}
			}
			if(entry.getKey().equals("����")) {
				i= sort+sort_2+sort_3;
				for (A36 v : value) {
					v.setSortid(new BigDecimal(v.getSortid().intValue()+i));
				}
			}
		}
		a36Index.addAll(map.get("��ż"));
		a36Index.addAll(map.get("��Ů"));
		a36Index.addAll(map.get("��ĸ"));
		a36Index.addAll(map.get("����"));
		//����������ν��ȡ˳�� ���ɶ�Ӧ�������list
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
	 * ���¼�ͥ��Ա���е�����
	 * @param tableName
	 */
	public static void updateA36Sort(String tableName) {
		 HBSession sess = HBUtil.getHBSession();
		//������ʱ���м�ͥ��Ա����
		String updateSql = "BEGIN\r\n" + 
				"  FOR A36ID IN (SELECT distinct a0000 FROM "+tableName+") LOOP\r\n" + 
				"      update "+tableName+" a1 set sortid=\r\n" + 
				"  (select  row1  from (select row_number() over( order by \r\n" + 
				"       case when A3604A  in ('�ɷ�','����') then \r\n" + 
				"                 DECODE(A3604A,A3604A,0,updated)\r\n" + 
				"            when  A3604A in ('����','Ů��','����','����','����','����','����','����','����','��Ů','��Ů','��Ů','��Ů','��Ů','��Ů','��Ů','����Ů��') then \r\n" + 
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
	 * ���¼�ͥ��Ա������Ա���
	 * @param tableName
	 */
	public static void updateA36Updated(String tableName) {
		 HBSession sess = HBUtil.getHBSession();
		//���Ӽ�ͥ��Ա���ֵ
		String updateUpdated = "update "+tableName+" a set updated = (select distinct c.sub_code_value from code_value c where c.code_Type='GB4761' and sub_code_value != -1 and c. code_value=a.a3604a)" ; 
		sess.createSQLQuery(updateUpdated).executeUpdate();
	}
	
	
}
