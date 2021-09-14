package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.Param;

public class SaveA11 {
	public static String save(List<Param> params, Connection conn) {
		StringBuffer column = new StringBuffer();
		StringBuffer value = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		// column��value����������������ֵ
		column.append("A1100,");
		String uuid = UUID.randomUUID().toString();
		String a0000 = "";
		value.append("'" + uuid + "',");
		sql.append("insert into a11 (");
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
						a0000 = list.get(0).getA0000();
						value.append("'" + a0000 + "',");
					}
				}
			} else if (param.getName().equals("A1131")) {
				// 2����ѵ������
				if (param.getValue().equals("")) {
					return "������ѵ�����Ʋ���Ϊ��";
				} else {
					column.append(param.getName() + ",");
					value.append("'" + param.getValue() + "',");
				}

			} else if (param.getName().equals("A1101")) {
				// 3 ��ѵ���
				if (!param.getValue().equals("")) {
					List<CodeValue> list = HBUtil.getHBSession().createQuery(
							"from CodeValue where code_name='"
									+ param.getValue()
									+ "' and code_type='ZB29'").list();
					if (list != null && list.size() != 1) {
						return "����ϵͳ�в�������ѵ���Ĵ���ֵ:" + param.getValue();
					} else {
						column.append("A1101,");
						value.append("'" + list.get(0).getCodeValue() + "',");
					}
				}
			} else if (param.getName().equals("A1127")) {
				// 4 ��ѵ�������
				if (!param.getValue().equals("")) {
					List<CodeValue> list = HBUtil.getHBSession().createQuery(
							"from CodeValue where code_name='"
									+ param.getValue()
									+ "' and code_type='ZB27'").list();
					if (list != null && list.size() != 1) {
						return "����ϵͳ�в�������ѵ��������Ӧ�Ĵ���ֵ:" + param.getValue();
					} else {
						column.append("A1127,");
						value.append("'" + list.get(0).getCodeValue() + "',");
					}
				}
			} else if (param.getName().equals("A1104")) {
				// 5 ��ѵ���״̬
				if (!param.getValue().equals("")) {
					List<CodeValue> list = HBUtil.getHBSession().createQuery(
							"from CodeValue where code_name='"
									+ param.getValue()
									+ "' and code_type='ZB30'").list();
					if (list != null && list.size() != 1) {
						return "����ϵͳ�в�������ѵ���״̬��Ӧ�Ĵ���ֵ:" + param.getValue();
					} else {
						column.append("A1104,");
						value.append("'" + list.get(0).getCodeValue() + "',");
					}
				}
			} else if (param.getName().equals("A1151")) {
				// 6 ��������������ѵ��ʶ
				if (!param.getValue().equals("")) {
					if (!param.getValue().equals("��")
							&& !param.getValue().equals("��")) {
						return "���󣺳�������������ѵ��ʶ����ȷ:" + param.getValue();
					} else {
						column.append("A1151,");
						if(param.getValue().equals("��")){
							value.append("'1',");
						}else{
							value.append("'0',");
						}
						
					}
				}
			} else {
				column.append(param.getName() + ",");
				value.append("'" + param.getValue() + "',");
			}
		}

		// ���������a41
		String a41sql = "insert into a41(A4100,A0000,A1100)values('"
				+ UUID.randomUUID().toString() + "','" + a0000 + "','" + uuid
				+ "')";
		PreparedStatement pst = null;
		try {
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(a41sql);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pst != null)
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		column.deleteCharAt(column.length() - 1);
		value.deleteCharAt(value.length() - 1);
		sql.append(column).append(") values (").append(value);
		sql.append(")");
		return sql.toString();
	}
}
