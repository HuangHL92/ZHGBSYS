package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.Param;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;

/**
 * �������֤����ɾ��������Ա��Ϣ
 * @author zhaoyd
 *
 */
public class DeleteInfo {
	public static String delete(List<Param> params,Connection conn) throws SQLException{
		String a0000 = "";
		for (Param param : params){
			//�ж��Ƿ���ڸ���Ա��Ϣ
			if (param.getName().equals("A0184") && !"".equals(param.getValue())){
				List<A01> list = HBUtil.getHBSession().createQuery(
						"from A01 where a0184='"+param.getValue()+"'  ").list();
				if(list==null||list.size()==0){
					return "����ϵͳ�в����ڸ���Ա��Ϣ��";
				} else if(list.size()>1){
					return "����ϵͳ�д��ڶ�������Ա��Ϣ��";
				} else{
					//ɾ��
					a0000 = list.get(0).getA0000();
					String[] table = {"a01","a02","a06","a08","a11","a14","a15","a29","a30","a31","a36","a37","a41","a53","a57","a60","a61","a62","a63","a64"};
					for (int i = 0; i < table.length; i++) {
						if(delmethod(a0000, conn, table[i])==0){
							return "����ִ��ʧ�ܣ�";
						}
					}
				}
			}
		}
		conn.commit();
		PhotosUtil.deletePhoto2(a0000);
		return "select 1 from dual";
	}
	
	public static int delmethod(String a0000,Connection conn,String table) throws SQLException{
		PreparedStatement pst = null;
		try {
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("delete from "+ table +" where a0000=?");
			pst.setString(1, a0000);
			pst.executeUpdate();
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
			return 0;
		}
	}
}
