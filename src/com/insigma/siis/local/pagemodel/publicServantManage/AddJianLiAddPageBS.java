package com.insigma.siis.local.pagemodel.publicServantManage;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A17;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.DateUtil;

public class AddJianLiAddPageBS {

	private String userid = SysUtil.getCacheCurrentUser().getId();
	private HBSession session = HBUtil.getHBSession(); //���ݿ�Ự����

	public String getMainGridStr(String a0000) {
		return "select a0000,a1700,a1701,a1702,a1703,a1704,a1705,a1706,a1707,a1708,case when " + DBUtil.getColumnIsNull("a1701") + " and " + DBUtil.getColumnIsNull("a1702") + " then '1' else '0' end as a1798,a1799,a0221,a0192e,complete " + "from hz_a17 where a0000 = '" + a0000 + "' and a1709 = '2' order by to_number(a1799)"; //��ȡ��¼¼���걨��ѯ���								//��ȡ��¼¼���걨��ѯ���
	}

	/**
	 *====================================================================================================
	 * ��������:haveA17Info ��ȡ�Ƿ��Ѵ��ڼ����ָ���Ϣ ����Ч��<br>
	 * ������������:2019��07��19��<br>
	 * ����������Ա:zhangxw<br>
	 * ��������޸�����:2019��07��25��<br>
	 * ��������޸���Ա:zhangxw<br>
	 * ������������:<br>
	 *====================================================================================================
	 * @throws AppException 
	 */
	public boolean haveA17Info(String a0000) throws AppException {
		/**
		 * �·�����Ϊ���ж��Ƿ���ڣ�����ڲ����в�֣�������
		 */
		String countA17ByA0000 = HBUtil.getValueFromTab("count(1)", "hz_a17", "a0000 = '" + a0000 + "'");
		if ("0".equals(countA17ByA0000)) {
			return true;
		} else {
			return false;
		}
	}

	public String insertA17(String a0000, String a1701) throws AppException {
		this.rmoveA17ByA0000(userid, a0000);
		if (a1701 == null || "".equals(a1701))
			return "0";
		String[] a1701Array = a1701.replaceAll("\\r", "").split("\n");
		String a1701s = "", a1701d = "", indexOf1, indexOf2;
		A17 a17;
		int indexOf;
		LinkedList<String> list = new LinkedList<String>();
		/**
		 * ��Forѭ�� Ϊ�˽�����һ����޸ĳ�һ����Ϣ
		 */
		for (int i = a1701Array.length - 1; i >= 0; i--) {
			a1701s = a1701Array[i].trim(); //��ѯ�ַ����Ƿ������ֿ�ʼ
			if (Character.isDigit(a1701s.charAt(0))) {
				a1701d = a1701s + a1701d;
				list.add(a1701d);
				a1701d = "";
				a1701s = "";
				continue;
			} else {
				a1701d = a1701d + a1701s;
			}
		}
		/**
		 * ʵ�ּ�����Ϣ���
		 */
		for (int i = 0, n = list.size(); i < n; i++) {
			a1701d = list.get(i);
			//ʹ��HTML ������ʽ�Ѹ�ʽ�������Է������Խ�ֹ������ɾ��
			//a1701d = a1701d.replaceAll("����", "--").replaceAll("��", "--").replaceAll("��", "--");					//��ȡ�洢������Ϣ
			//a1701d = a1701d.replaceAll("����", "--").replaceAll("��", "--");											//��ȡ�洢������Ϣ
			a1701s = a1701d.replaceAll(" ", "").replaceAll("\\.", ""); //��ȡʱ���ַ�����Ϣ
			indexOf = a1701s.indexOf("--"); //��ȡ��־λ����Ϣ
			if (indexOf == -1 || indexOf > 15) {
				a1701s = a1701s.replaceAll("-", "--");
				indexOf = a1701s.indexOf("--");
				if (indexOf == -1 || indexOf > 15) {
					continue;
				}
			}

			a17 = new A17();
			a17.setA0000(a0000);
			indexOf1 = a1701s.substring(indexOf - 6, indexOf);
			if (!Character.isDigit(indexOf1.charAt(0))) {
				continue;
			} else {
				a17.setA1701(indexOf1);
			}
			if (a1701s.indexOf("--1") == -1 && a1701s.indexOf("--2") == -1) {
				a17.setA1702("");
			} else {
				indexOf2 = a1701s.substring(indexOf + 2, indexOf + 8);
				if (!Character.isDigit(indexOf2.charAt(0))) {
					a17.setA1702("");
				} else {
					a17.setA1702(indexOf2);
				}
			}
			indexOf1 = DateUtil.dataStrFormart(a17.getA1701(), ".", "", "");
			indexOf2 = DateUtil.dataStrFormart(a17.getA1702(), ".", "", "");
			a1701d = a1701d.replace(indexOf1 + "--" + indexOf2, "").trim();
			a17.setA1703(a1701d.trim());
			a17.setUserid(userid);
			a17.setA1799(i + "");
			session.save(a17);
		}
		session.flush();
		return HBUtil.getValueFromTab("count(1)", "hz_a17", "a0000 = '" + a0000 + "'");
	}

	public String insertA17WithSD(String a0000, String a1701) throws AppException {
		String userid = SysUtil.getCacheCurrentUser().getId();
		this.rmoveA17ByA0000(userid, a0000); //ĳ��Ա�������
		if (a1701 == null || "".equals(a1701))
			return "0";
		String[] split = a1701.split("\\$\\$");
		String a1701s = "", a1701d = "", indexOf1, indexOf2;
		A17 a17;
		int indexOf;
		for (int i = 0, n = split.length; i < n; i++) {
			a1701d = split[i];
			a1701s = a1701d.replaceAll(" ", "").replaceAll("\\.", ""); //��ȡʱ���ַ�����Ϣ
			a17 = new A17();
			a17.setA0000(a0000);
			if (a1701s.startsWith("1") || a1701s.startsWith("2")) {
				indexOf = a1701s.indexOf("--");
				if (indexOf < 6)
					continue;
				indexOf1 = a1701s.substring(indexOf - 6, indexOf);
				if (!Character.isDigit(indexOf1.charAt(0))) {
					continue;
				} else {
					a17.setA1701(indexOf1);
				}
				if (a1701s.indexOf("--1") == -1 && a1701s.indexOf("--2") == -1) {
					a17.setA1702("");
				} else {
					indexOf2 = a1701s.substring(indexOf + 2, indexOf + 8);
					if (!Character.isDigit(indexOf2.charAt(0))) {
						a17.setA1702("");
					} else {
						a17.setA1702(indexOf2);
					}
				}

				indexOf1 = DateUtil.dataStrFormart(a17.getA1701(), ".", "", "");
				indexOf2 = DateUtil.dataStrFormart(a17.getA1702(), ".", "", "");
				a1701d = a1701d.replace(indexOf1 + "--" + indexOf2, "").trim();
			}
			a17.setA1703(a1701d.trim());
			a17.setUserid(userid);
			a17.setA1799(i + "");
			session.save(a17);
		}
		session.flush();
		return HBUtil.getValueFromTab("count(1)", "hz_a17", "a0000 = '" + a0000 + "'");
	}

	public String getA1701(String a0000) throws AppException {
		String a1701 = HBUtil.getValueFromTab("a1701", "a01", "a0000 = '" + a0000 + "'");
		if (a1701 == null || "".equals(a1701))
			a1701 = "";
		return a1701;
	}
	public String saveA17_1(List<HashMap<String, Object>> list, String a0000c) throws AppException {
		StringBuffer buffer = new StringBuffer();
		Object object = list.get(0).get("a0000");
		if (object == null || "".equals(object))
			object = a0000c;
		String a0000 = object + "";
		Object a1700,a1701,a1702,a1704,a1705,a1706,a1707,a1708;
		A17 a17;
		HashMap<String, Object> a172;


		/**
		 * ����ƴ��
		 */
		List<Object[]> lista17=session.createSQLQuery("select a1701,a1702,complete from hz_a17 where a0000 = '"+a0000+"' and a1709 = '2' order by to_number(a1799) ").list();
		if(lista17.size()>0) {
			for(int i = 0 ; i<lista17.size() ; i++) {
				Object[] aa17=lista17.get(i);
				buffer.append(DateUtil.dataStrFormart(aa17[0], ".", "", "") + "--");
				buffer.append(DateUtil.dataStrFormart(aa17[1], ".", "", "") + "  ");
				buffer.append(aa17[2]);
				buffer.append("\n");
			}
		}

		String a1701Str = buffer.toString();
		a1701Str = a1701Str.replace("--  ", "--         ");
		session.flush();
		return a1701Str;
	}
	public String saveA17(List<HashMap<String, Object>> list, String a0000c) throws AppException {
		StringBuffer buffer = new StringBuffer();
		Object object = list.get(0).get("a0000");
		if (object == null || "".equals(object))
			object = a0000c;
		String a0000 = object + "";
		Object a1700,a1701,a1702,a1704,a1705,a1706,a1707,a1708;
		A17 a17;
		HashMap<String, Object> a172;
		/**
		 * �����ݴ洢����
		 */
		int n = 0;
		for (HashMap<String, Object> map : list) {
			a1700 = map.get("a1700");
			if("".equals(a1700)||a1700==null) {
				continue;
			}
			a17=(A17) session.get(A17.class, a1700.toString());
			if(a17==null) {
				a17=new A17();
			}
			a17.setA1700(a1700+"");
			a1701 = map.get("a1701");
			a1702 = map.get("a1702");
			a1704 = map.get("a1704");
			a1705 = map.get("a1705");
			a1706 = map.get("a1706");
			a1707 = map.get("a1707");
			a1708 = map.get("a1708");
			a17.setA0000(a0000);
			if (a1701 == null)
				continue;
			a17.setA1701(a1701 + "");
			a17.setA1702(a1702 == null ? "" : a1702 + "");
			a17.setA1704(a1704 == null ? "" : a1704 + "");
			a17.setA1705(a1705 == null ? "" : a1705 + "");
			a17.setA1706(a1706 == null ? "" : a1706 + "");
			a17.setA1707(a1707 == null ? "" : a1707 + "");
			a17.setA1708(a1707 == null ? "" : a1708 + "");
			a17.setA1799("" + (n++));
			a17.setA1709("2");
			session.saveOrUpdate(a17);
		}
		session.createSQLQuery("delete from hz_a17 where a0000 = '"+a0000+"' and a1709 = '1' ").executeUpdate();
		session.flush();

		/**
		 * ����ƴ��
		 */
		List<Object[]> lista17=session.createSQLQuery("select a1701,a1702,complete from hz_a17 where a0000 = '"+a0000+"' and a1709 = '2' order by to_number(a1799) ").list();
		if(lista17.size()>0) {
			for(int i = 0 ; i<lista17.size() ; i++) {
				Object[] aa17=lista17.get(i);
				buffer.append(DateUtil.dataStrFormart(aa17[0], ".", "", "") + "--");
				buffer.append(DateUtil.dataStrFormart(aa17[1], ".", "", "") + "  ");
				buffer.append(aa17[2]);
				buffer.append("\n");
			}
		}
		/*
		 * String a17012, a17022, a17032, a17982; for (int i = 0; i < list.size(); i++)
		 * { a172 = list.get(i); a17012 = a172.get("a1701") + ""; a17022 =
		 * a172.get("a1702") + ""; a17982 = a172.get("a1798") + ""; a17032 =
		 * a172.get("a1703") + ""; if ("1".equals(a17982)) { buffer.append("\n");
		 * buffer.append(a17032); } else { if (i > 0) { //�����Ƿ�ӣ���䣺���򣨣������⣺
		 * //ȡ����ǰ��a17012 �� ǰһ���� a17022_pre �Ƚϣ� //���a17012ʱ��>=a17022_preʱ��,��ֱ���������
		 * //���a17012ʱ�� < a17012_preʱ��,�ٱȽ�a17022ʱ����a17022_preʱ�䡣��a17022 < a17022_pre,�����
		 * ���з�+����
		 * 
		 * //���a17012ʱ��
		 * <a17022_preʱ�䣬�ٱȽ�a17022ʱ����a17022_preʱ�䡣��a17022<=a17022_pre������ӣ���䣺��
		 * //���a17012ʱ�� <a17022_preʱ�䣬�ٱȽ�a17022ʱ����a17022_preʱ�䡣��a17022 > a17022_pre�������
		 * ���з�+����
		 * 
		 * //����������������Ȱ�ֱ��������Ӵ��� HashMap<String, Object> a172_pre = list.get(i - 1);
		 * String a17012_pre = a172_pre.get("a1701") + ""; String a17022_pre =
		 * a172_pre.get("a1702") + ""; if("".equals(a17022_pre)||a17022_pre==null ) {
		 * buffer.append("\n��"); buffer.append(DateUtil.dataStrFormart(a17012, ".", "",
		 * "") + "--"); buffer.append(DateUtil.dataStrFormart(a17022, ".", "", ""));
		 * buffer.append(a17032); buffer.append("��"); } else if
		 * (Integer.parseInt(a17012) >= Integer.parseInt(a17022_pre)) {
		 * buffer.append("\n"); buffer.append(DateUtil.dataStrFormart(a17012, ".", "",
		 * "") + "--"); buffer.append(DateUtil.dataStrFormart(a17022, ".", "", "") +
		 * "  "); buffer.append(a17032); } else if (Integer.parseInt(a17012) <
		 * Integer.parseInt(a17012_pre)) { if (Integer.parseInt(a17022) <
		 * Integer.parseInt(a17022_pre)) { buffer.append("\n��");
		 * buffer.append(DateUtil.dataStrFormart(a17012, ".", "", "") + "--");
		 * buffer.append(DateUtil.dataStrFormart(a17022, ".", "", ""));
		 * buffer.append(a17032); buffer.append("��"); } else { buffer.append("\n");
		 * buffer.append(DateUtil.dataStrFormart(a17012, ".", "", "") + "--");
		 * buffer.append(DateUtil.dataStrFormart(a17022, ".", "", "") + "  ");
		 * buffer.append(a17032); } } else if (Integer.parseInt(a17012) <
		 * Integer.parseInt(a17022_pre)) { if (Integer.parseInt(a17022) <=
		 * Integer.parseInt(a17022_pre)) { buffer.append("����䣺");
		 * buffer.append(DateUtil.dataStrFormart(a17012, ".", "", "") + "--");
		 * buffer.append(DateUtil.dataStrFormart(a17022, ".", "", ""));
		 * buffer.append(a17032); buffer.append("��"); } else if
		 * (Integer.parseInt(a17022) > Integer.parseInt(a17022_pre)) {
		 * buffer.append("\n��"); buffer.append(DateUtil.dataStrFormart(a17012, ".", "",
		 * "") + "--"); buffer.append(DateUtil.dataStrFormart(a17022, ".", "", ""));
		 * buffer.append(a17032); buffer.append("��"); } } else { buffer.append("\n");
		 * buffer.append(DateUtil.dataStrFormart(a17012, ".", "", "") + "--");
		 * buffer.append(DateUtil.dataStrFormart(a17022, ".", "", "") + "  ");
		 * buffer.append(a17032); } } else {
		 * buffer.append(DateUtil.dataStrFormart(a17012, ".", "", "") + "--");
		 * buffer.append(DateUtil.dataStrFormart(a17022, ".", "", "") + "  ");
		 * buffer.append(a17032); } } }
		 */
		String a1701Str = buffer.toString();
		a1701Str = a1701Str.replace("--  ", "--         ");
		session.flush();
		return a1701Str;
	}

	/**
	 * ���û��ͱ������û�ɾ��ԭ�м�¼
	 * @param userid
	 * @param a0000
	 */
	private void rmoveA17ByA0000(String userid, String a0000) {
		session.createSQLQuery("delete from hz_a17 where a0000 = '" + a0000 + "'").executeUpdate();
		session.flush();
	}

	/**
	 * ���ɼ���
	 */
	@SuppressWarnings("unchecked")
	@PageEvent("genResume")
	@NoRequiredValidate
	public String genResume(String a0000) throws RadowException {
		//�Զ����ɼ���
		String sqlA02 = "from A02 where a0000='" + a0000 + "' order by a0223";
		List<A02> listA02 = session.createQuery(sqlA02).list();
		Collections.sort(listA02, new Comparator<A02>() {
			@Override
			public int compare(A02 o1, A02 o2) {
				String o1sj = o1.getA0243() == null ? "" : o1.getA0243();//��ְʱ��
				String o2sj = o2.getA0243() == null ? "" : o2.getA0243();//��ְʱ��
				if ("".equals(o1sj)) {
					return -1;
				} else if ("".equals(o2sj)) {
					return 1;
				} else {
					if (o1sj.length() >= 6) {
						String d1 = o1sj.substring(0, 6);
						String d2 = o2sj.substring(0, 6);
						return d1.compareTo(d2);
					}
				}
				return 0;
			}
		});
		StringBuffer sb = new StringBuffer("");
		if (listA02 != null && listA02.size() > 0) {
			A02 a02 = null;
			String a0201a, a03015, a0203, a0255, a0265, a0203Next;
			B01 b01 = null;
			for (int i = 0; i < listA02.size(); i++) {
				a02 = listA02.get(i);
				a0201a = a02.getA0201a() == null ? "" : a02.getA0201a();//��ְ����
				a03015 = a02.getA0215a() == null ? "" : a02.getA0215a();//ְ������
				a0203 = a02.getA0243() == null ? "" : a02.getA0243();//��ְʱ��
				a0255 = a02.getA0255() == null ? "" : a02.getA0255();//��ְ״̬
				a0265 = a02.getA0265() == null ? "" : a02.getA0265();//��ְʱ��
				if (a02.getA0201b() != null) {
					b01 = (B01) session.get(B01.class, a02.getA0201b());
				}
				if (b01 != null) {//����ƴ�ӹ��� �� ������λ��ְ��ȫ�� ���� ƴ��һ��
					String b0194 = b01.getB0194();//1�����˵�λ��2�����������3���������顣
					if ("2".equals(b0194)) {//2���������
						while (true) {
							b01 = (B01) session.get(B01.class, b01.getB0121());
							if (b01 == null) {
								break;
							} else {
								b0194 = b01.getB0194();
								if ("2".equals(b0194)) {//2���������
									a0201a = b01.getB0101() + a0201a;
								} else if ("3".equals(b0194)) {//3����������
									continue;
								} else if ("1".equals(b0194)) {//1�����˵�λ
									a0201a = b01.getB0101() + a0201a;
									break;
								} else {
									break;
								}
							}
						}
					}
				}
				sb.append("\nְ��״̬��");
				sb.append("1".equals(a0255) ? "����" : "����");
				sb.append("\n��ʼ��");
				if ("1".equals(a0255))
					a0203Next = "";
				else
					a0203Next = a0265;
				if ("".equals(a0203))
					sb.append("��");
				else
					sb.append(DateUtil.dataStrFormart(a0203, "", "", ""));
				sb.append("\n������");
				if ("".equals(a0203Next))
					sb.append("��");
				else
					sb.append(DateUtil.dataStrFormart(a0203Next, "", "", ""));
				sb.append("\nְ��������");
				sb.append(a0201a + a03015 + "\n");
			}
		}
		return sb.toString().replaceAll("'", "\\\\'");
	}

	public String havaA17ByA0000(String a0000) throws AppException {
		return HBUtil.getValueFromTab("count(1)", "hz_a17", "a0000='" + a0000 + "'");
	}

	public int saveA01(String a1701, String a0000) {
		A01 a01 = (A01) session.get(A01.class, a0000);
		if (a01 == null)
			return -4;
		a01.setA1701(a1701);
		session.update(a01);
		session.flush();
		return 1;
	}
	public String saveZZA17(List<HashMap<String, Object>> list,String a0000,A17 belongToA17) {
		String buffer = new String();
		buffer=belongToA17.getA1703();
		String belong_to_a1700=belongToA17.getA1700();
		String belongToA1701=belongToA17.getA1701();
		String belongToA1702=belongToA17.getA1702();
		A17 a17;
		Object a1700,a1701,a1702,a1703;
		/**
		 * �����ݴ洢����
		 */
		int n = 0;
		if(list.size()==0||list==null) {
			return buffer;
		}
		for (HashMap<String, Object> map : list) {
			a1700 = map.get("a1700");
			if("".equals(a1700)||a1700==null) {
				a1700=UUID.randomUUID().toString().replaceAll("-", "");
			}
			a1701 = map.get("a1701");
			a1702 = map.get("a1702");
			a1703 = map.get("a1703");
			a17=(A17) session.get(A17.class, a1700.toString());
			if(a17==null) {
				a17=new A17();
			}
			a17.setA1700(a1700+"");
			a17.setA0000(a0000);
			if (a1701 == null)
				continue;
			a17.setA1701(a1701 + "");
			a17.setA1702(a1702 == null ? "" : a1702 + "");
			a17.setA1703(a1703 == null ? "" : a1703 + "");
			a17.setUserid(userid);
			a17.setA1799("" + (n++));
			a17.setBelongToA1700(belong_to_a1700);
			a17.setComplete(a1703 == null ? "" : a1703 + "");
			if("".equals(a1701)||Integer.parseInt(belongToA1701)<=Integer.parseInt(a1701.toString())) {
				if(buffer.indexOf("\n��")!=-1||buffer.indexOf("���")!=-1) {
					if(StringUtils.isEmpty(a1701.toString())){
						buffer=buffer.substring(0, buffer.length()-1)+"��"+DateUtil.dataStrFormart(a1702.toString(), ".", "", "") +a1703.toString()+"��";
					}else{
						if(StringUtils.isEmpty(a1702.toString())){
							buffer=buffer.substring(0, buffer.length()-1)+"��"+DateUtil.dataStrFormart(a1701.toString(), ".", "", "")+"��" +a1703.toString()+"��";
						}else{
							buffer=buffer.substring(0, buffer.length()-1)+"��"+DateUtil.dataStrFormart(a1701.toString(), ".", "", "")+"--"+DateUtil.dataStrFormart(a1702.toString(), ".", "", "")+""+a1703.toString()+"��";
						}
					}
				}/*else if(buffer.indexOf("���")!=-1) {
					buffer=buffer.substring(0, buffer.length()-1)+"��"+DateUtil.dataStrFormart(a1701.toString(), ".", "", "")+"--"+DateUtil.dataStrFormart(a1702.toString(), ".", "", "")+"  "+a1703.toString()+"��";
				}*/else {
					if(StringUtils.isEmpty(a1701.toString())){
						buffer=buffer+"����䣺"+DateUtil.dataStrFormart(a1702.toString(), ".", "", "")+a1703.toString()+"��";

					}else{
						if(StringUtils.isEmpty(a1702.toString())){
							buffer=buffer+"����䣺"+DateUtil.dataStrFormart(a1701.toString(), ".", "", "")+"��"+a1703.toString()+"��";
						}else{
							buffer=buffer+"����䣺"+DateUtil.dataStrFormart(a1701.toString(), ".", "", "")+"--"+DateUtil.dataStrFormart(a1702.toString(), ".", "", "")+""+a1703.toString()+"��";

						}
					}
				}
				a17.setA1709("3");
			}else if (Integer.parseInt(belongToA1701)>Integer.parseInt(a1701.toString())) {
				//buffer=buffer.replace("����䣺", "\n��");
				if(buffer.indexOf("\n��")!=-1) {
					if(StringUtils.isEmpty(a1702.toString())){
						buffer=buffer.substring(0, buffer.length()-1)+"��"+DateUtil.dataStrFormart(a1701.toString(), ".", "", "")+"��" +a1703.toString()+"��";
					}else if(StringUtils.isEmpty(a1701.toString())){
						buffer=buffer.substring(0, buffer.length()-1)+"��"+DateUtil.dataStrFormart(a1702.toString(), ".", "", "") +a1703.toString()+"��";
					}else{
						
						buffer=buffer.substring(0, buffer.length()-1)+"��"+DateUtil.dataStrFormart(a1701.toString(), ".", "", "")+"--"+DateUtil.dataStrFormart(a1702.toString(), ".", "", "")+""+a1703.toString()+"��";
					}
				}else {
					if(StringUtils.isEmpty(a1702.toString())){
						buffer=buffer+"\n��"+DateUtil.dataStrFormart(a1701.toString(), ".", "", "")+"��"+a1703.toString()+"��";
					}else if(StringUtils.isEmpty(a1701.toString())){
						buffer=buffer+"\n��"+DateUtil.dataStrFormart(a1702.toString(), ".", "", "") +a1703.toString()+"��";
					}else{
						buffer=buffer+"\n��"+DateUtil.dataStrFormart(a1701.toString(), ".", "", "")+"--"+DateUtil.dataStrFormart(a1702.toString(), ".", "", "")+""+a1703.toString()+"��";
					}
				}
				a17.setA1709("4");
			}
			session.saveOrUpdate(a17);
		}
		return buffer;
	}
	public int pjComplete(String a1700) {
		A17 a17=(A17) session.get(A17.class, a1700.toString());
		String buffer=a17.getA1703();
		if(a17!=null) {
			List<Object[]> a17zz=session.createSQLQuery("select a1701,a1702,a1703,a1709 from hz_a17 where belong_to_a1700= '"+a1700+"' and a1709 in ('3','4') order by to_number(a1799)").list();
			if(a17zz!=null && a17zz.size()>0) {
				Object a1701,a1702,a1703,a1709;
				for(int i=0;i<a17zz.size();i++) {
					Object[] objs=a17zz.get(i);
					a1701=objs[0];
					a1702=objs[1];
					if(a1702==null) {
						a1702="";
					}
					a1703=objs[2];
					a1709=objs[3];
					if(Integer.parseInt(a17.getA1701())<=Integer.parseInt(a1701.toString())) {
						if(buffer.indexOf("\n��")!=-1) {
							buffer=buffer.substring(0, buffer.length()-1)+"��"+DateUtil.dataStrFormart(a1701.toString(), ".", "", "")+"--"+DateUtil.dataStrFormart(a1702.toString(), ".", "", "")+"  "+a1703.toString()+"��";
						}
						else if(buffer.indexOf("���")!=-1) {
							buffer=buffer.substring(0, buffer.length()-1)+"��"+DateUtil.dataStrFormart(a1701.toString(), ".", "", "")+"--"+DateUtil.dataStrFormart(a1702.toString(), ".", "", "")+"  "+a1703.toString()+"��";;
						}else {
							buffer=buffer+"����䣺"+DateUtil.dataStrFormart(a1701.toString(), ".", "", "")+"--"+DateUtil.dataStrFormart(a1702.toString(), ".", "", "")+"	"+a1703.toString()+"��";
						}	
					}else if (Integer.parseInt(a17.getA1701())>Integer.parseInt(a1701.toString())) {
						buffer=buffer.replace("����䣺", "\n��");
						if(buffer.indexOf("\n��")!=-1) {
							buffer=buffer.substring(0, buffer.length()-1)+"��"+DateUtil.dataStrFormart(a1701.toString(), ".", "", "")+"--"+DateUtil.dataStrFormart(a1702.toString(), ".", "", "")+"  "+a1703.toString()+"��";
						}else {
							buffer=buffer+"\n��"+DateUtil.dataStrFormart(a1701.toString(), ".", "", "")+"--"+DateUtil.dataStrFormart(a1702.toString(), ".", "", "")+"  "+a1703.toString()+"��";
						}
					}
				}
			}
			a17.setComplete(buffer);
			session.saveOrUpdate(a17);
			session.flush();
			return 1;
		}else {
			return 0;
		}
		
		
	}
	public int pjA1701(String a0000) {
		A01 a01=(A01) session.get(A01.class, a0000);
		StringBuffer buffer=new StringBuffer();
		if(a01!=null) {
			List<A17> a17List=session.createSQLQuery("select * from hz_a17 where a0000= '"+a0000+"' and a1709='2' order by to_number(a1701)").addEntity(A17.class).list();
			if(a17List!=null && a17List.size()>0) {
				Object a1701,a1702,complete;
				for(int i=0;i<a17List.size();i++) {
					A17 a17=a17List.get(i);
					a1701=a17.getA1701();
					a1702=a17.getA1702();
					if(a1702==null) {
						a1702="";
					}
					complete=a17.getComplete();
					buffer.append(DateUtil.dataStrFormart(a1701, ".", "", "") + "--");
					buffer.append(DateUtil.dataStrFormart(a1702, ".", "", "") + "  ");
					buffer.append(complete);
					buffer.append("\n");
					a17.setA1799(i+"");
					session.saveOrUpdate(a17);
				}
			}
			String a1701Str = buffer.toString();
			a1701Str = a1701Str.replace("--  ", "--         ");
			List a171=session.createSQLQuery("select a1703 from hz_a17 where a0000= '"+a0000+"' and a1709='1' order by to_number(a1799)").list();
			if(a171!=null && a171.size()>0) {
				for(int i=0;i<a171.size();i++) {
					Object a1703=a171.get(i);
					a1701Str=a1701Str+"--��Ҫְ����Ҫ������"+a1703+"\n";
				}
			}
			a01.setA1701(a1701Str);
			session.saveOrUpdate(a01);
			session.flush();
			return 1;
		}else {
			return 0;
		}
	}
}
