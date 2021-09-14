package com.insigma.siis.local.pagemodel.fxyp.base;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class MNTPUtil {
	//�����л�������  ����һ��
	public static final String QXS_B0111="001.001.004";
	
	
	
	/**
	 * ����a0200��ȡ��Ҫ���εĸ�λ��Ϣ
	 * @throws AppException 
	 */
	public static List<HashMap<String, Object>> getGWByA0200(String a0200) throws AppException{
		String sql = "select a02.a0000,a02.a0200,a02.a0201A,a02.a0201b,a02.a0201e,a0215a,a0225, "
				+ " (select b0131 from b01 b where b.b0111 = a02.a0201b) b0131 , "
				+ " (select b01id from b01 b where b.b0111 = a02.a0201b) b01id,(nvl(a02.a0201e,'Z')||'01') zrrx "
				+ " from a02 a02  where a02.a0200 in  "
				+ " ('"+(a0200.replaceAll(",", "','"))+"')";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> nmzwList = cqbs.getListBySQL(sql);
		return nmzwList;
	}
	
	/**
	 * ����a0200 ��ȡ�ø�λ���Ƿ���������ѡ�����У�������ѡ�Ƿ�����Լ�  ���򷵻�������¼
	 * @throws AppException 
	 */
	public static List<HashMap<String, Object>> getNRRXByA0200(String a0200,String mntp00) throws AppException{
		String sql = "select distinct g.zwqc00,g.fxyp00,g.a0000,r.tp0100 "
				+ " from GWZWREF g,v_mntp_gw_ry r "
				+ " where g.a0000=r.a0000 and g.mntp00=r.mntp00"
				+ " and r.zwqc00=g.zwqc00 and r.fxyp00 = g.fxyp00 and  "
				+ " g.mntp00='"+mntp00+"' and g.a0200='"+a0200+"'";

		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> nmzwList = cqbs.getListBySQL(sql);
		return nmzwList;
	}
	
	/**
	 * ����a0200 ��ȡ�ø�λ���Ƿ���������ѡ  ������ѡ��Ϣ
	 * @throws AppException 
	 */
	public static List<HashMap<String, Object>> getNRRXALLByA0200(String a0200,String mntp00) throws AppException{
		String sql = "select distinct g.zwqc00,g.fxyp00,g.a0000,r.tp0100 "
				+ " from GWZWREF g,v_mntp_gw_ry r "
				+ " where  g.mntp00=r.mntp00"
				+ " and r.zwqc00=g.zwqc00 and r.fxyp00 = g.fxyp00 and  "
				+ " g.mntp00='"+mntp00+"' and g.a0200='"+a0200+"'";

		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> nmzwList = cqbs.getListBySQL(sql);
		return nmzwList;
	}
	
	
	/**
	 * ����tp0100 ��ȡ����Ա���ڵ������λa0200���Ƿ���������ѡ  �� ����������ѡ��  �ж��Ƿ�����Լ������������������Ӹ�����Ϊ������ѡ
	 * @throws AppException 
	 */
	public static List<HashMap<String, Object>> getNMRXByTP0100(String tp0100,String mntp00) throws AppException{
		String sql = "select distinct r.a0000,r.a0200 "
				+ " from v_mntp_gw_ry r "
				+ " where r.fxyp07=-1"
				+ " and r.mntp00='"+mntp00+"' and r.tp0100='"+tp0100+"'";

		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> nmzwList = cqbs.getListBySQL(sql);
		return nmzwList;
	}
	
	
	
	
	/**
	 * ����a0200��ȡ��Ҫ���εĸ�λ��Ϣ   �����Ƿ񱻶���  �����ȫ��id ��λ��Ӧ�ĵ��䵥λ
	 * @throws AppException 
	 */
	public static List<HashMap<String, Object>> getGWNRInfoByA0200(String a0200,String mntp00) throws AppException{
		String sql = "select a02.a0000,a02.a0200,a02.a0201A,a02.a0201b,a02.a0201e,a0215a,a0225, "
				+ " (select b0131 from b01 b where b.b0111 = a02.a0201b) b0131 , "
				+ " (select b01id from b01 b where b.b0111 = a02.a0201b) b01id,"
				+ " (select b0101 from b01 b where b.b0111 = a02.a0201b) b0101,"
				+ " g.zwqc00,g.fxyp00 ,(nvl(a02.a0201e,'Z')||'01') zrrx"
				+ " from a02 a02 left join "
				+ " (select r.*,h.fxyp07 from GWZWREF r,hz_mntp_zwqc h "
				+ " where r.zwqc00=h.zwqc00 and r.mntp00='"+mntp00+"') g "
				+ " on a02.a0200=g.a0200  where a02.a0000 in  "
				+ " (select a0000 from a02 t where t.a0200='"+a0200+"') "
				+ " AND a02.a0281 = 'true' AND a02.a0255 = '1' and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> nmzwList = cqbs.getListBySQL(sql);
		return nmzwList;
	}
	
	
	
	/**
	 * ����fxyp00 ��ȡ��λ��Ϣ      ְ��ȫ�� �� ������λ��
	 * @throws AppException 
	 */
	public static List<HashMap<String, Object>> getGWXXByFXYP00(String fxyp00,String mntp00) throws AppException{
		String sql = "select distinct r.zwqc00,r.zwqc01,a0192a,fxyp07,b01id,fxyp00,fxyp02,fxyp06,a0201e,a0501b "
				+ " from hz_mntp_gw r "
				+ " where "
				+ " r.mntp00='"+mntp00+"' and r.zwqc00=(select zwqc00 from fxyp where fxyp00='"+fxyp00+"')";

		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> nmzwList = cqbs.getListBySQL(sql);
		return nmzwList;
	}
	
}
