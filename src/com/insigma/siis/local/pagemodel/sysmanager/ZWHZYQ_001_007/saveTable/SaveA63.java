package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A60;
import com.insigma.siis.local.business.entity.A63;
import com.insigma.siis.local.business.entity.Param;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity.CodeTypeUtil;

public class SaveA63 {

	public static String save(List<Param> params,Connection conn) throws AppException{
		StringBuffer column = new StringBuffer();
		StringBuffer value = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		String a0000="";
		sql.append("insert into A63 (");
		for (Param param : params){
			// �ж�A01����û����������
			if (param.getName().equals("A0184") && !"".equals(param.getValue())) {

				List<A01> list = HBUtil.getHBSession()
				.createQuery(
						"from A01 where a0184='" + param.getValue()
								+ "'  ").list();
				
				if(list==null||list.size()==0){
					return "����ϵͳ�в����ڸ���Ա��Ϣ:"+param.getValue()+"����������Ա������Ϣ�в���";
				}else if(list.size()>1){
					return "����ϵͳ�д��ڶ�������Ա��Ϣ:"+param.getValue()+"���޷�����";
				}else{//��ѯ�Ƿ��Ѵ���a60��Ϣ������Ѵ��ڣ���ɾ������������  ע�⿪������
					a0000 = list.get(0).getA0000();
					List<A63> a63s = HBUtil.getHBSession().createQuery(
							"from A63 where a0000='"+a0000+"'  ").list();
					
					if(a63s!=null&&a63s.size()>0){
						PreparedStatement pst = null;
						try {
							conn.setAutoCommit(false);
							pst = conn.prepareStatement("delete from a63 where a0000=?");
							pst.setString(1, a0000);
							pst.executeUpdate();
						} catch (SQLException e) {
							e.printStackTrace();
						}finally{
							try {
								if(pst!=null)pst.close();
							} catch (SQLException e1) {
							}
						}	
					}
					column.append("a0000,");
					value.append("'"+list.get(0).getA0000()+"',");
					continue;
			}
			}
			// �ǿ�У��
			if (param.getName().equals("A0184") && param.getValue().equals("")) {
				return "��������֤���벻��Ϊ��";
			}
			
			// code_valueУ��
			if (CodeTypeUtil.getCodeValue(param) == null ) {
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
}