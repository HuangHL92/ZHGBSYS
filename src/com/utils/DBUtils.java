package com.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;

public class DBUtils {
	/**
	 * CLOBת����String
	 * @param clob
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public static String ClobToString(Clob clob) throws SQLException, IOException {
		String reString = "";
		Reader is = clob.getCharacterStream();
		BufferedReader br = new BufferedReader(is);
		String s = br.readLine();
		StringBuffer sb = new StringBuffer();
		while (s != null) {
			sb.append(s);
			s = br.readLine();
		}
		reString = sb.toString();
		if (br != null) {
			br.close();
		}
		if (is != null) {
			is.close();
		}
		return reString;
	}

	/**
	 * ��ȡ�ֵ���ֵ
	 * @param code_value
	 * @param code_type
	 * @param name_num
	 * @return
	 */
	public static String getCodeName(String code_value, String code_type, Integer name_num) {
		HBSession session = HBUtil.getHBSession();
		String code_name = "";
		switch (name_num) {
			case 1:
				code_name = "code_name";
				break;
			case 2:
				code_name = "code_name2";
				break;
			case 3:
				code_name = "code_name3";
				break;
			default:
				break;
		}
		
		String sql = "select "+code_name+" from code_value where code_type = :code_type and code_value=:code_value";
		SQLQuery query =session.createSQLQuery(sql);
		query.setString("code_type", code_type);
		query.setString("code_value", code_value);
		return query.list().get(0).toString();
	}
	
	/**
	 * ��һ������ƽ���ֳ�n��
	 *
	 * @param source Ҫ���������Դ
	 * @param n      ƽ���ֳ�n��
	 * @param <T>
	 * @return
	 */
	public static <T> List<List<T>> averageAssign(List<T> source, int n) {
	    List<List<T>> result = new ArrayList<List<T>>();
	    int remainder = source.size() % n;  //(�ȼ��������)
	    int number = source.size() / n;  //Ȼ������
	    int offset = 0;//ƫ����
	    for (int i = 0; i < n; i++) {
	        List<T> value = null;
	        if (remainder > 0) {
	            value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
	            remainder--;
	            offset++;
	        } else {
	            value = source.subList(i * number + offset, (i + 1) * number + offset);
	        }
	        result.add(value);
	    }
	    return result;
	}

	/**
	 * �Ƿ����
	 * @param a0000 ��ԱID
	 * @return true:��
	 */
	public static boolean isAudit(String a0000) {
		HBSession session = HBUtil.getHBSession();
		String sql = "select A0000 from A01 where A0000=:a0000 and A0189='1' and A0190='1' ";
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("a0000", a0000);
		return query.list().size()==0?false:true;
	}
	
	/**
	 * �Ƿ��иɲ�����ȹ���Ȩ��
	 * @param userid �û�ID
	 * @return true:��
	 */
	public static boolean isNoGbmc(String userid) {
		HBSession session = HBUtil.getHBSession();
		String sql = "select s.userid from smt_act s where s.roleid = '3e20818a728c688a01728cd78310015a'";
		List<Object> list = session.createSQLQuery(sql).list();
		if(list.contains(userid)) {
			return false;
		}else {
			return true;
		}
	}
}
