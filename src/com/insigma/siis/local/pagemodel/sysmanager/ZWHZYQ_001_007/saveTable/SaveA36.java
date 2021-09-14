package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable;

import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.Param;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity.CodeTypeUtil;

public class SaveA36 {

	public static String save(List<Param> params) {
		StringBuffer column = new StringBuffer();
		StringBuffer value = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into A36 (");
		for (Param param : params) {
			// �ж�A01����û����������
			if (param.getName().equals("A0184") && !"".equals(param.getValue())) {

				List<A01> list = HBUtil.getHBSession().createQuery(
						"from A01 where a0184='"+param.getValue()+"'  ").list();
				if(list==null||list.size()==0){
					return "����ϵͳ�в����ڸ���Ա��Ϣ:"+param.getValue()+"����������Ա������Ϣ�в���";
				}else if(list.size()>1){
					return "����ϵͳ�д��ڶ�������Ա��Ϣ:"+param.getValue()+"���޷�����";
				}
				// Ĭ����
				column.append("SORTID,");
				value.append("'" + sortId(param.getValue()) + "',");
				column.append("a3600,");
				value.append("'" + UUID.randomUUID() + "',");
				column.append("a0000,");
				value.append("'" + list.get(0).getA0000() + "',");
				continue;
			}
			// �ǿ�У��

			if (param.getName().equals("A0184") && param.getValue().equals("")) {
				return "��������֤���벻��Ϊ��";
			}
			// code_valueУ��
			if (CodeTypeUtil.getCodeValue(param) == null || CodeTypeUtil.getCodeValue(param).equals("")) {
				return "����" + param.getName() + "ֵ���벻�Ϸ���";
			}
			
			
			column.append(param.getName() + ",");
			value.append("'" + param.getValue() + "',");

		}


		column.deleteCharAt(column.length() - 1);
		value.deleteCharAt(value.length() - 1);
		sql.append(column).append(") values (").append(value);
		sql.append(")");
		return sql.toString();
	}

	public static int sortId(String id) {
		String sql = "select a36.SORTID  from A36 a36  where a36.A0000=(select a01.A0000 from A01 a01 where a01.A0184='"
				+ id + "') order by a36.SORTID desc";

		List<String> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		String sortid = "1";
		if (list.size() == 0 || list.get(0) == null) {
			sortid = "1";
		} else {
			sortid = Integer.toString(Integer.parseInt(list.get(0).toString())+1);
		}
		return Integer.parseInt(sortid);
	}
}