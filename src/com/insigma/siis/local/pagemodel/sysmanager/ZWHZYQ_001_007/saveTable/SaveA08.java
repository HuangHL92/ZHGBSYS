package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.Param;

public class SaveA08 {
	public static String save(List<Param> params) {
		StringBuffer column = new StringBuffer();
		StringBuffer value = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		int i = 0;// �����ж�ͬʱΪ�����
		// column��value����������������ֵ
		column.append("A0800,");
		value.append("'" + UUID.randomUUID().toString() + "',");
		sql.append("insert into a08 (");
//		String a0000="";
//		String jylb="";
//		String yx_xl = "";//Ժϵ
//		String zy_xl = "";//רҵ	
//		String yx_xw = "";//Ժϵ
//		String zy_xw = "";//רҵ
//		String xl = "";	  //ѧ��
//		String xw = "";	  //ѧλ
		// �������
		for (Param param : params) {
			if (param.getName().equals("A0184")) {
				// 1�����֤����У��
				if (param.getValue().equals("")) {
					return "�������֤���벻��Ϊ��";
				} else {
					List<A01> list = HBUtil.getHBSession()
							.createQuery(
									"from A01 where a0184='" + param.getValue()
											+ "'  ").list();
					if(list==null||list.size()==0){
						return "����ϵͳ�в����ڸ���Ա��Ϣ:"+param.getValue()+"����������Ա������Ϣ�в���";
					}else if(list.size()>1){
						return "����ϵͳ�д��ڶ�������Ա��Ϣ:"+param.getValue()+"���޷�����";
					} else {
						column.append("A0000,");
						value.append("'" + list.get(0).getA0000() + "',");
//						a0000 = list.get(0).getA0000();
					}
				}
			} else if (param.getName().equals("A0837")) {
				// 2���������
				if (param.getValue().equals("")) {
					return "���󣺽��������Ϊ��";
				} else {
					List<CodeValue> list = HBUtil.getHBSession().createQuery(
							"from CodeValue where code_name='"
									+ param.getValue()
									+ "' and code_type='ZB123'").list();
					if (list != null && list.size() != 1) {
						return "����ϵͳ�в����ڽ�������Ӧ�Ĵ���ֵ:" + param.getValue();
					} else {
						column.append("A0837,");
						value.append("'" + list.get(0).getCodeValue() + "',");
//						jylb = list.get(0).getCodeValue();
					}
				}

			} else if (param.getName().equals("A0801B")) {
				// 3 ѧ������
				if (!param.getValue().equals("")) {
					List<CodeValue> list = HBUtil.getHBSession().createQuery(
							"from CodeValue where code_name='"
									+ param.getValue()
									+ "' and code_type='ZB64'").list();
					if (list != null && list.size() != 1) {
						return "����ϵͳ�в�����ѧ����Ӧ�Ĵ���ֵ:" + param.getValue();
					} else {
						column.append("A0801B,");
						value.append("'" + list.get(0).getCodeValue() + "',");
					}
				} else {
					i++;
				}
			} else if (param.getName().equals("A0901B")) {
				// 4 ѧλ����
				if (!param.getValue().equals("")) {
					List<CodeValue> list = HBUtil.getHBSession().createQuery(
							"from CodeValue where code_name='"
									+ param.getValue()
									+ "' and code_type='GB6864'").list();
					if (list != null && list.size() != 1) {
						return "����ϵͳ�в�����ѧλ��Ӧ�Ĵ���ֵ:" + param.getValue();
					} else {
						column.append("A0901B,");
						value.append("'" + list.get(0).getCodeValue() + "',");
					}
				} else {
					i++;
				}
			} else if (param.getName().equals("A0827")) {
				// 5 ��ѧרҵ���
				if (!param.getValue().equals("")) {
					List<CodeValue> list = HBUtil.getHBSession().createQuery(
							"from CodeValue where code_name='"
									+ param.getValue()
									+ "' and code_type='GB16835'").list();
					if (list != null && list.size() != 1) {
						return "����ϵͳ�в�������ѧרҵ����Ӧ�Ĵ���ֵ:" + param.getValue();
					} else {
						column.append("A0827,");
						value.append("'" + list.get(0).getCodeValue() + "',");
					}
				}
			} 
//			else if (param.getName().equals("A0814")) {
//				yx_xl = param.getValue();
//				zy_xl = param.getValue();
//			} else if (param.getName().equals("A0824")) {
//				yx_xw = param.getValue();
//				zy_xw = param.getValue();
//			} else if (param.getName().equals("A0801A")) {
//				xl = param.getValue();
//			}else if (param.getName().equals("A0901A")) {
//				xw = param.getValue();
//			}
			else {
				column.append(param.getName() + ",");
				value.append("'" + param.getValue() + "',");
			}
		}
		if (i > 1) {
			return "����ѧ��ѧλ����ͬʱΪ��";
		}
		
//			HBSession sess = HBUtil.getHBSession();
//			
//		
//			if(yx_xl==null){
//				yx_xl = "";
//			}
//			if(zy_xl==null){
//				zy_xl = "";
//			}
//			if(yx_xw==null){
//				yx_xw = "";
//			}
//			if(zy_xw==null){
//				zy_xw = "";
//			}
//			if(!"".equals(zy_xl)){
//				zy_xl += "רҵ";
//			}
//			if(!"".equals(zy_xw)){
//				zy_xw += "רҵ";
//			}
//			if("1".equals(jylb)){
//				String Qrzxlxx = yx_xl+zy_xl;
//				String Qrzxwxx = yx_xw+zy_xw;
//				try {
//					HBTransaction tx = sess.beginTransaction();
//					String hql = "update A01 a01 set a01.qrzxl='"+ xl +"',a01.qrzxw='"+ xw +"',a01.qrzxlxx ='"+ Qrzxlxx +"' ,a01.qrzxw='"+ Qrzxwxx +"' where a01.a0000 = '"+ a0000+"'";
//					Query query = sess.createQuery(hql);
//					query.executeUpdate();
//					tx.commit();
//				} catch (AppException e) {
//					
//					e.printStackTrace();
//				}
//
//			}else{//��ְ
//				String Zzxlxx = yx_xl+zy_xl;
//				String Zzxwxx = yx_xw+zy_xw;
//				try {
//					HBTransaction tx = sess.beginTransaction();
//					String hql = "update A01 a01 set a01.zzxl='"+ xl +"',a01.zzxw='"+ xw +"',a01.zzxlxx ='"+ Zzxlxx+"' ,a01.zzxwxx='"+ Zzxwxx+"' where a01.a0000 = '"+ a0000+"'";
//					Query query = sess.createQuery(hql);
//					query.executeUpdate();
//					tx.commit();
//				} catch (AppException e) {
//					
//					e.printStackTrace();
//				}
//
//			}

	
		column.deleteCharAt(column.length() - 1);
		value.deleteCharAt(value.length() - 1);
		sql.append(column).append(") values (").append(value);
		sql.append(")");
		return sql.toString();
	}
}
