package com.insigma.siis.local.pagemodel.publicServantManage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.A41;
import com.insigma.siis.local.business.entity.A99Z1;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.lbs.leaf.persistence.HibernateHelper;

public class betchModifyPageModel extends PageModel {

	public static String sid = "";

	/**
	 * ˽������ ids ��ҳ������������
	 */
	public static int type = 0;
	private LogUtil applog = new LogUtil();

	public betchModifyPageModel() {

	}

	// ҳ���ʼ��
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {

		sid = this.request.getSession().getId();

		this.getExecuteSG().addExecuteCode("getTabletype();");
		HBSession sess = HBUtil.getHBSession();
		// String value = this.getRadow_parent_data();
		String value = this.getPageElement("subWinIdBussessId").getValue();
		String[] values = value.split("@");
		if (values.length > 1) {
			String sql = values[0];
			StringBuffer sb = new StringBuffer();
			sql = sql.replaceAll("\\$", "\\'");
			String newsql = sql.replace("*", "a0000");
			List allSelect = sess.createSQLQuery(newsql).list();
			if (allSelect.size() > 0) {
				for (int i = 0; i < allSelect.size(); i++) {
					// �ж��Ƿ���ɾ��Ȩ�ޡ�c.type������Ȩ������(0�������1��ά��)
					String a0000 = allSelect.get(i).toString();
					A01 a01 = (A01) sess.get(A01.class, a0000);
					String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and "
							+ " b.a0201b=c.b0111 and a.a0000='" + a0000 + "' and c.userid='"
							+ SysManagerUtils.getUserId() + "' "
							+ " and c.type='0' and b.a0255='1' and a.status='1' and a.a0163='1'";
					String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and "
							+ " b.a0201b=c.b0111 and a.a0000='" + a0000 + "' and c.userid='"
							+ SysManagerUtils.getUserId() + "' "
							+ " and c.type='1' and b.a0255='1' and a.status='1' and a.a0163='1'";
					List elist = sess.createSQLQuery(editableSQL).list();
					List elist2 = sess.createSQLQuery(editableSQL2).list();
					/*
					 * �жϸ���Ա�Ĺ���������Ȩ��---------------------------------------------------------------
					 * ---------------------------------------
					 */
					String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
					if (type == null || !type.contains("'")) {
						type = "'zz'";// �滻��������
					}
					List elist3 = sess
							.createSQLQuery(
									"select 1 from a01 where a01.a0000='" + a0000 + "' and a01.a0165 in (" + type + ")")
							.list();
					if (elist3.size() > 0) {// �޹������ά��Ȩ��,����Ա��Ϣ���ɱ༭
						continue;
					}
					if (elist2 == null || elist2.size() == 0) {// ά��Ȩ��
						if (elist != null && elist.size() > 0) {// �����Ȩ��
							continue;
						} else {
							// ���������������ְ��Ա��������ְ��Ա������ְ��Աֻ�ܲ鿴�����ܱ༭��������ְ��Ա�ɲ鿴���ɱ༭
							if (a01.getA0163() != null && !a01.getA0163().equals("1")) { // ����ְ��Ա
								continue;
							} else {
								sb.append("'").append(a0000).append("',");
							}

						}
					} else {
						sb.append("'").append(a0000).append("',");
					}
				}
				if (sb.length() == 0) {
					this.setMainMessage("��ѡ��Ա���ɲ�����");
					return EventRtnType.FAILD;
				}
				String ids = sb.substring(0, sb.length() - 1);
				this.getPageElement("ids").setValue(ids);
			} else {
				this.setMainMessage("���Ƚ�����Ա��ѯ��");
				return EventRtnType.FAILD;
			}
		} else {
			String[] id_arr = value.split(",");
			String ids_temp = "";
			for (int i = 0; i < id_arr.length; i++) {
				ids_temp += "'" + id_arr[i] + "',";
			}
			String ids = ids_temp.substring(0, ids_temp.length() - 1);
			this.getPageElement("ids").setValue(ids);
		}
		Calendar c = new GregorianCalendar();
		int year = c.get(Calendar.YEAR);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for (int i = 0; i < 80; i++) {
			map.put("" + (year - i), year - i);
		}
		((Combo) this.getPageElement("a1521")).setValueListForSelect(map);
		// String[] idArray = ids.split(",");

		this.createPageElement("a15z101", ElementType.TEXT, false).setDisabled(true);
		this.createPageElement("a1521", ElementType.SELECT, false).setDisabled(false);
		this.getPageElement("a15z101").setValue("");

		this.setNextEventName("peopleInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("change")
	public int change() throws RadowException, AppException {
		String a1151Type = this.getPageElement("a1151Type").getValue();

		if (a1151Type.equals("0")) {
			this.createPageElement("a15z101", ElementType.TEXT, false).setDisabled(true);
			this.createPageElement("a1521", ElementType.SELECT, false).setDisabled(false);
			this.getPageElement("a15z101").setValue("");
		} else {
			this.createPageElement("a1521", ElementType.SELECT, false).setDisabled(true);
			this.createPageElement("a15z101", ElementType.TEXT, false).setDisabled(false);
			this.getPageElement("a1521").setValue("");
			this.getPageElement("a1517").setValue("");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	public String ChangeName(String value, String type) {
		HBSession sess = HBUtil.getHBSession();
		String sql = "select code_name from code_value where code_value = '" + value + "' and code_type = '" + type
				+ "' ";
		List list = sess.createSQLQuery(sql).list();
		String name = "";
		if (list.size() > 0) {
			name = list.get(0).toString();
		}
		return name;
	}

	/**
	 * ��Ա������Ϣ����
	 * 
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("saveA01")
	@Transaction
	@Synchronous(true)
	public int save() throws RadowException, AppException {
		HBSession sess = null;
		getCheckPeople();
		try {

			String a0197 = this.getPageElement("a0197").getValue(); // �����������ϻ��㹤������
			String a99z101 = this.getPageElement("a99z101").getValue(); // �Ƿ�¼
			String a99z102 = this.getPageElement("a99z102").getValue(); // ¼��ʱ��

			String a99z103 = this.getPageElement("a99z103").getValue(); // �Ƿ�ѡ����
			String a99z104 = this.getPageElement("a99z104").getValue(); // ����ѡ����ʱ��
			/** ����ְ����ʱ�� */
			String A0288 = this.getPageElement("A0288").getValue();
			/** �������� */
			String A0107 = this.getPageElement("A0107").getValue();
			/** ���� */
			String A0111A = this.getPageElement("A0111A").getValue();
			/** ������ */
			String A0114A = this.getPageElement("A0114A").getValue();
			/** �ɳ��� */
			String A0115A = this.getPageElement("A0115A").getValue();
			/** ���� */
			String A0117 = this.getPageElement("A0117").getValue();
			/** ������� */
			String A0128 = this.getPageElement("A0128").getValue();
			/** �μӹ���ʱ�� */
			String A0134 = this.getPageElement("A0134").getValue();
			/** �뵳ʱ������ */
			String A0140 = this.getPageElement("A0140").getValue();
			/** �ֹ�����λ��ְ���� */
			String A0192 = this.getPageElement("A0192").getValue();
			/** �ֹ�����λ��ְ��ȫ�� */
			String A0192A = this.getPageElement("A0192A").getValue();
			/** ���ȫ����ѧ�� */
			String QRZXL = this.getPageElement("QRZXL").getValue();
			/** ԺУϵרҵ�����ȫ����ѧ���� */
			String QRZXLXX = this.getPageElement("QRZXLXX").getValue();
			/** ���ȫ����ѧλ */
			String QRZXW = this.getPageElement("QRZXW").getValue();
			/** ԺУϵרҵ�����ȫ����ѧλ���� */
			String QRZXWXX = this.getPageElement("QRZXWXX").getValue();
			/** ְ���� */
			String A0221 = this.getPageElement("A0221").getValue();
			/** ְ�� */
			String A0192E = this.getPageElement("A0192E").getValue();

			//�Ƿ�����ɲ�
			String fkbs = this.getPageElement("fkbs").getValue();
			
			
			sess = HBUtil.getHBSession();
			Connection conn = sess.connection();

			// Ŀǰ�˹������֧��500�������޸�
			String ids = this.getPageElement("checkIds").getValue();
			String[] a0000s = ids.split(",");

			int count = 0;
			// ������Ϣ
			String a0160 = this.getPageElement("a0160").getValue();
			// String a0104 = this.getPageElement("a0104").getValue(); //�Ա�
			/*
			 * String a0104a = ""; if (!StringUtil.isEmpty(a0104)) { a0104a =
			 * ChangeName(a0104, "GB2261"); } else { a0104a = ""; }
			 */
			// String a0117 = this.getPageElement("a0117").getValue(); //����
			/*
			 * String a0117a = ""; if (!StringUtil.isEmpty(a0117)) { a0117a =
			 * ChangeName(a0117, "GB3304"); } else { a0117a = ""; }
			 */
			// String a0111 = this.getPageElement("a0111").getValue(); //����
			// String a0111a = "";
			// String a0114a = "";
			/*
			 * if (!StringUtil.isEmpty(a0111)) { a0111a = sess .createSQLQuery(
			 * "select t.code_name3 from code_value t where t.code_type = 'ZB01' and t.code_value = '"
			 * + a0111 + "'").uniqueResult() .toString(); } else { a0111a = ""; }
			 */
			// String a0114 = this.getPageElement("a0114").getValue();
			/*
			 * if (!StringUtil.isEmpty(a0114)) { a0114a = sess .createSQLQuery(
			 * "select t.code_name3 from code_value t where t.code_type = 'ZB01' and t.code_value = '"
			 * + a0114 + "'").uniqueResult() .toString(); } else { a0114a = ""; }
			 */

			// ͳ�ƹ�ϵ���ڵ�λ������ѡ���������
			String a0195 = this.getPageElement("a0195").getValue();

			B01 b01 = (B01) sess.get(B01.class, a0195);

			if (b01 != null) {

				String b0194 = b01.getB0194();// 1�����˵�λ��2�����������3���������顣

				if ("2".equals(b0194)) {// 2���������
					this.setMainMessage("ͳ�ƹ�ϵ���ڵ�λ������ѡ���������");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}

			String a0165 = this.getPageElement("a0165").getValue();
			// String a0192d = this.getPageElement("a0192d").getValue(); //ְ��
			// String a0120 = this.getPageElement("a0120").getValue(); //����
			String a0121 = this.getPageElement("a0121").getValue();
			// String a0163 = this.getPageElement("a0163").getValue(); //��Ա����״̬
			// String a0128 = this.getPageElement("a0128").getValue(); //����״̬
			// String a2970 = this.getPageElement("a2970").getValue();//lzy-add
			// �п�
			if (!StringUtil.isEmpty(a0160) /* || !StringUtil.isEmpty(a0104) */
					/* || !StringUtil.isEmpty(a0117) */ /*
														 * || !StringUtil.isEmpty(a0111) || !StringUtil.isEmpty(a0114)
														 */ || !StringUtil.isEmpty(a0195) || !StringUtil.isEmpty(a0165)
					/*
					 * || !StringUtil.isEmpty(a0192d) || !StringUtil.isEmpty(a0120)
					 */ || !StringUtil.isEmpty(a0121)
					/* || !StringUtil.isEmpty(a0163) || !StringUtil.isEmpty(a0128) */
					/* || !StringUtil.isEmpty(a2970) */ || !StringUtil.isEmpty(a0197) || !StringUtil.isEmpty(a99z101)
					|| !StringUtil.isEmpty(a99z102) || !StringUtil.isEmpty(a99z103) || !StringUtil.isEmpty(a99z104)
					|| !StringUtil.isEmpty(A0288) || !StringUtil.isEmpty(A0107) || !StringUtil.isEmpty(A0111A)
					|| !StringUtil.isEmpty(A0114A) || !StringUtil.isEmpty(A0115A) || !StringUtil.isEmpty(A0117)
					|| !StringUtil.isEmpty(A0128) || !StringUtil.isEmpty(A0134) || !StringUtil.isEmpty(A0140)
					|| !StringUtil.isEmpty(A0192) || !StringUtil.isEmpty(A0192A) || !StringUtil.isEmpty(QRZXL)
					|| !StringUtil.isEmpty(QRZXLXX) || !StringUtil.isEmpty(QRZXW) || !StringUtil.isEmpty(QRZXWXX)
					|| !StringUtil.isEmpty(A0221) || !StringUtil.isEmpty(A0192E)||!StringUtil.isEmpty(fkbs)) {
				if (a0000s.length > 0) {
					StringBuffer sb = new StringBuffer();
					StringBuffer sbTwo = new StringBuffer();
					Map<String, String> map = new LinkedHashMap();
					Map<String, String> mapTwo = new LinkedHashMap();
					if (!StringUtil.isEmpty(a0160)) {
						map.put("a0160", a0160);
						sb = sb.append("a0160 = ?,");
					}
					/*
					 * if(!StringUtil.isEmpty(a0104)){ map.put("a0104", a0104); map.put("a0104a",
					 * a0104a); sb = sb.append("a0104 = ?,a0104a = ?,"); }
					 */
					/*
					 * if(!StringUtil.isEmpty(a0117)){ map.put("a0117", a0117); map.put("a0117a",
					 * a0117a); sb = sb.append("a0117 = ?,a0117a = ?,"); }
					 */
					/*
					 * if(!StringUtil.isEmpty(a0111)){ map.put("a0111", a0111); map.put("a0111a",
					 * a0111a); sb = sb.append("a0111 = ?,a0111a = ?,"); }
					 * if(!StringUtil.isEmpty(a0114)){ map.put("a0114", a0114); map.put("a0114a",
					 * a0114a); sb = sb.append("a0114 = ?,a0114a = ?,"); }
					 */
					if (!StringUtil.isEmpty(fkbs)) {
						map.put("fkbs", fkbs);
						sb = sb.append("fkbs = ?,");
					}
					
					
					if (!StringUtil.isEmpty(a0195)) {
						map.put("a0195", a0195);
						sb = sb.append("a0195 = ?,");
					}

					if (!StringUtil.isEmpty(a0197)) { // �����������ϻ��㹤������
						map.put("a0197", a0197);
						sb = sb.append("a0197 = ?,");
					}
					if (!StringUtil.isEmpty(a0165)) {
						map.put("a0165", a0165);
						sb = sb.append("a0165 = ?,");
					}
					/*
					 * if(!StringUtil.isEmpty(a0192d)){ map.put("a0192d", a0192d); sb =
					 * sb.append("a0192d = ?,"); }
					 */
					/*
					 * if(!StringUtil.isEmpty(a0160)){ map.put("a0120", a0160); sb =
					 * sb.append("a0120 = ?,"); }
					 */
					if (!StringUtil.isEmpty(a0121)) {
						map.put("a0121", a0121);
						sb = sb.append("a0121 = ?,");
					}
					/** ����ְ����ʱ�� */
					if (!StringUtil.isEmpty(A0288)) {
						map.put("A0288", A0288);
						sb = sb.append("A0288 = ?,");
					}
					/** �������� */
					if (!StringUtil.isEmpty(A0107)) {
						map.put("A0107", A0107);
						sb = sb.append("A0107 = ?,");
					}
					/** ���� */
					if (!StringUtil.isEmpty(A0111A)) {
						map.put("A0111A", A0111A);
						sb = sb.append("A0111A = ?,");
					}
					/** ������ */
					if (!StringUtil.isEmpty(A0114A)) {
						map.put("A0114A", A0114A);
						sb = sb.append("A0114A = ?,");
					}
					/** �ɳ��� */
					if (!StringUtil.isEmpty(A0115A)) {
						map.put("A0115A", A0115A);
						sb = sb.append("A0115A = ?,");
					}
					/** ���� */
					if (!StringUtil.isEmpty(A0117)) {
						map.put("A0117", A0117);
						sb = sb.append("A0117 = ?,");
					}
					/** ������� */
					if (!StringUtil.isEmpty(A0128)) {
						map.put("A0128", A0128);
						sb = sb.append("A0128 = ?,");
					}
					/** �μӹ���ʱ�� */
					if (!StringUtil.isEmpty(A0134)) {
						map.put("A0134", A0134);
						sb = sb.append("A0134 = ?,");
					}
					/** �뵳ʱ������ */
					if (!StringUtil.isEmpty(A0140)) {
						map.put("A0140", A0140);
						sb = sb.append("A0140 = ?,");
					}
					/** �ֹ�����λ��ְ���� */
					if (!StringUtil.isEmpty(A0192)) {
						map.put("A0192", A0192);
						sb = sb.append("A0192 = ?,");
					}
					/** �ֹ�����λ��ְ��ȫ�� */
					if (!StringUtil.isEmpty(A0192A)) {
						map.put("A0192A", A0192A);
						sb = sb.append("A0192A = ?,");
					}
					/** ���ȫ����ѧ�� */
					if (!StringUtil.isEmpty(QRZXL)) {
						map.put("QRZXL", QRZXL);
						sb = sb.append("QRZXL = ?,");
					}
					/** ԺУϵרҵ�����ȫ����ѧ���� */
					if (!StringUtil.isEmpty(QRZXLXX)) {
						map.put("QRZXLXX", QRZXLXX);
						sb = sb.append("QRZXLXX = ?,");
					}
					/** ���ȫ����ѧλ */
					if (!StringUtil.isEmpty(QRZXW)) {
						map.put("QRZXW", QRZXW);
						sb = sb.append("QRZXW = ?,");
					}
					/** ԺУϵרҵ�����ȫ����ѧλ���� */
					if (!StringUtil.isEmpty(QRZXWXX)) {
						map.put("QRZXWXX", QRZXWXX);
						sb = sb.append("QRZXWXX = ?,");
					}
					/** ��ְ���� */
					if (!StringUtil.isEmpty(A0221)) {
						map.put("A0221", A0221);
						sb = sb.append("A0221 = ?,");
						// ����ְ���α�
						//this.setNextEventName("UpdateA05");
						//this.request.setAttribute("UpdateA05Flag", "true");
						// UpdateA05(A0221,a0000s,true);
						//if (!com.insigma.siis.local.util.StringUtil.isEmpty(this.getMainMessage())) {
						//	return EventRtnType.NORMAL_SUCCESS;
						//}
					}
					/** ְ�� */
					if (!StringUtil.isEmpty(A0192E)) {
						map.put("A0192E", A0192E);
						sb = sb.append("A0192E = ?,");
						// ����ְ���α�
						// UpdateA05(A0192E,a0000s,false);
						//this.setNextEventName("UpdateA05");
						//this.request.setAttribute("UpdateA05Flag", "false");
						//if (!com.insigma.siis.local.util.StringUtil.isEmpty(this.getMainMessage())) {
						//	return EventRtnType.NORMAL_SUCCESS;
						//}
					}

					/*
					 * if(!StringUtil.isEmpty(a0163)){//lzy-add map.put("a0163", a0163); sb =
					 * sb.append("a0163 = ?,"); } if(!StringUtil.isEmpty(a0128)){//lzy-add
					 * map.put("a0128b", a0128); sb = sb.append("a0128b = ?,"); }
					 */
					/*
					 * if(!StringUtil.isEmpty(a2970)){//lzy-add PreparedStatement ps;
					 * if(DBUtil.getDBType() == DBType.MYSQL){ ps = conn
					 * .prepareStatement("replace into a61(a0000,a2970) values(?,?)"); for (int i =
					 * 0; i < a0000s.length; i++) { ps.setString(1, a0000s[i]); ps.setString(2,
					 * a2970); ps.addBatch(); count++; if (count >= 500) { ps.executeBatch();
					 * ps.clearParameters(); // conn.commit(); count = 0; } } if (count > 0) {
					 * ps.executeBatch(); // conn.commit(); count = 0; } if (ps != null) {
					 * ps.close(); } }else{ ps = conn
					 * .prepareStatement("MERGE INTO a61 b USING a01 a ON (b.a0000=a.a0000) WHEN MATCHED THEN UPDATE SET b.a2970='"
					 * +a2970+"' WHERE a.a0000 = ? WHEN NOT MATCHED THEN INSERT VALUES(?,'"
					 * +a2970+"',null,null,null,null,null,null,null,null,null,null,null,null,null,null) WHERE a.a0000 = ?"
					 * ); for (int i = 0; i < a0000s.length; i++) { int j = 1; ps.setString(j,
					 * a0000s[i]); ps.setString(j+1, a0000s[i]); ps.setString(j+2, a0000s[i]);
					 * ps.addBatch(); count++; if (count >= 500) { ps.executeBatch();
					 * ps.clearParameters(); // conn.commit(); count = 0; } } if (count > 0) {
					 * ps.executeBatch(); // conn.commit(); count = 0; } if (ps != null) {
					 * ps.close(); } } }
					 */

					String sql1 = "";
					if (sb.length() > 0) {
						sql1 = sb.substring(0, sb.length() - 1);

					}

					PreparedStatement ps = conn.prepareStatement("UPDATE a01 set " + sql1 + " where a0000 = ?");
					for (int i = 0; i < a0000s.length; i++) {

						// ͨ��a0000��ѯ����������Ϣ����A99Z1����,��A99Z1��Ϣ���д���
						A99Z1 a99Z1 = new A99Z1();
						this.copyElementsValueToObj(a99Z1, this);

						// ͨ��a000ȥ����a99z1
						String sql = "select A99Z100 from a99z1 where A0000 = '" + a0000s[i] + "'";

						List A99Z100S = sess.createSQLQuery(sql).list();

						String a99Z100 = "";
						if (A99Z100S.size() > 0) {
							a99Z100 = A99Z100S.get(0).toString();
						}

						a99Z1.setA99Z100(a99Z100);

						// �ж�¼��ʱ�䣺��������ڽ��бȽϣ�һ��Ӧ����18���ꡣ
						// String a0107 = a01.getA0107();//��������
						/*
						 * String a99z102 = a99Z1.getA99z102();//¼��ʱ��
						 * if(a0107!=null&&!"".equals(a0107)&&a99z102!=null&&!"".equals(a99z102)){ int
						 * age = getAgeNew(a99z102,a0107); if(age<18){
						 * this.setMainMessage("¼��ʱ����������ڽ��бȽϣ�Ӧ����18���꣡"); return EventRtnType.FAILD; } }
						 */

						// �жϽ���ѡ����ʱ�䣺��������ڽ��бȽϣ�һ��Ӧ����18���ꡣ

						/*
						 * String a99z104 = a99Z1.getA99z104();//����ѡ����ʱ��
						 * if(a0107!=null&&!"".equals(a0107)&&a99z104!=null&&!"".equals(a99z104)){ int
						 * age = getAgeNew(a99z104,a0107); if(age<18){
						 * this.setMainMessage("����ѡ����ʱ����������ڽ��бȽϣ�Ӧ����18���꣡"); return EventRtnType.FAILD;
						 * } }
						 */
						A01 a01_old = (A01) sess.get(A01.class, a0000s[i]);

						a99Z1.setA0000(a0000s[i]);
						A99Z1 a99Z1_old = null;
						if ("".equals(a99Z1.getA99Z100())) {
							a99Z1.setA99Z100(null);
							a99Z1_old = new A99Z1();

							String id = UUID.randomUUID().toString();// �����������ݿ������id
							// a99Z1_old.setA99Z100(id);
							a99Z1_old.setA0000(a0000s[i]);
							// applog.createLog("3531", "A99Z1", a0000s[i], a01_old.getA0101(), "������¼", new
							// Map2Temp().getLogInfo(a99Z1_old,a99Z1));

							applog.createLogNew("3A99Z12", "ѡ��������¼��Ϣ����", "ѡ��������¼��Ϣ��", a0000s[i], a01_old.getA0101(),
									new Map2Temp().getLogInfo(new A99Z1(), a99Z1));
						} else {
							a99Z1_old = (A99Z1) sess.get(A99Z1.class, a99Z1.getA99Z100());
							// applog.createLog("3532", "A53", a0000s[i], a01_old.getA0101(), "�޸ļ�¼", new
							// Map2Temp().getLogInfo(a99Z1_old,a99Z1));
							applog.createLogNew("3A99Z12", "ѡ��������¼��Ϣ�޸�", "ѡ��������¼��Ϣ��", a0000s[i], a01_old.getA0101(),
									new Map2Temp().getLogInfo(a99Z1_old, a99Z1));
						}

						// ��ֵ
						if (a99z101 != null && !a99z101.equals("")) {
							a99Z1_old.setA99z101(a99z101);
							a99Z1_old.setA99z102(a99z102);
						}

						if (a99z103 != null && !a99z103.equals("")) {
							a99Z1_old.setA99z103(a99z103);
							a99Z1_old.setA99z104(a99z104);
						}

						sess.saveOrUpdate(a99Z1_old);

						int j = 1;
						for (String key : map.keySet()) {
							ps.setString(j, map.get(key));
							j++;
						}
						ps.setString(j, a0000s[i]);
						ps.addBatch();
						count++;
						if (count >= 500 && sb.length() > 0) {
							ps.executeBatch();
							ps.clearParameters();
							// conn.commit();
							count = 0;
						}

						A01 a01 = new A01();

						PropertyUtils.copyProperties(a01, a01_old);

						// ��ֵ���µ���Ϣ
						if (!StringUtil.isEmpty(a0160)) { // ��Ա���
							a01.setA0160(a0160);
						}

						if (!StringUtil.isEmpty(a0195)) { // ͳ�ƹ�ϵ���ڵ�λ
							a01.setA0195(a0195);
						}
						if (!StringUtil.isEmpty(a0165)) { // �������
							a01.setA0165(a0165);
						}

						if (!StringUtil.isEmpty(a0121)) { // ��������
							a01.setA0121(a0121);
						}

						if (!StringUtil.isEmpty(a0197)) { // �����������ϻ��㹤������
							a01.setA0197(a0197);
						}
						
						if (!StringUtil.isEmpty(fkbs)) { // �Ƿ�����ɲ�
							a01.setFkbs(fkbs);
						}

						// ��¼��־
						applog.createLog("32", "A01", a01.getA0000(), a01.getA0101(), "�޸ļ�¼",
								new Map2Temp().getLogInfo(a01_old, a01));

					}
					if (count > 0 && sb.length() > 0) {
						ps.executeBatch();
						// conn.commit();
						count = 0;
					}
					if (ps != null) {
						ps.close();
					}
					// ��¼��־
					/*
					 * List list = new ArrayList();
					 * 
					 * if (!StringUtil.isEmpty(a0160)) { String[] arr = { "A0160", "", "", "��Ա���" };
					 * list.add(arr); }
					 * 
					 * if (!StringUtil.isEmpty(a0195)) { String[] arr = { "A0195", "", "",
					 * "ͳ�ƹ�ϵ���ڵ�λ" }; list.add(arr); } if (!StringUtil.isEmpty(a0165)) { String[] arr
					 * = { "A0165", "", "", "�������" }; list.add(arr); }
					 * 
					 * if (!StringUtil.isEmpty(a0121)) { String[] arr = { "A0121", "", "", "��������" };
					 * list.add(arr); }
					 * 
					 * if (!StringUtil.isEmpty(a0197)) { //�����������ϻ��㹤������ String[] arr = { "a0197",
					 * "", "", "�����������ϻ��㹤������" }; list.add(arr); }
					 */

					// applog.createLog("3704", "A01", "", "", "��Ա������Ϣ�����޸�", list);

				}
			} else {
				this.setMainMessage("û��Ҫ�޸ĵ���");
				return EventRtnType.NORMAL_SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.setMainMessage("����ɹ�");
		String tableType = this.getPageElement("type").getValue();
		reloadCustomQuery(tableType);

		return EventRtnType.NORMAL_SUCCESS;
	}

	@Transaction
	@PageEvent("save")
	public int UpdateA05() throws RadowException, AppException {
		// Ŀǰ�˹������֧��500�������޸�
		String ids = this.getPageElement("checkIds").getValue();
		String[] a0000s = ids.split(",");
		String A0221 = this.getPageElement("A0221").getValue();
		String A0288 = this.getPageElement("A0288").getValue();
		String A0192E = this.getPageElement("A0192E").getValue();
		
		String userName = "";
		String ZJINFO = "";
		int ZJerrornum=0;
		String ZJerrorId="";
		String ZWCCINFO="";
		String ZWCCerrorId="";
		int ZWCCerrornum=0;
		HBSession sess = HBUtil.getHBSession();
			for (int i = 0; i < a0000s.length; i++) {
				String sql = "";
				/** ��ְ���� */
				if (!StringUtil.isEmpty(A0221)) {
					// ����ְ���α�
					sql = "select * from a05 where a0000='" + a0000s[i] + "' and a0531='0' and a0524='1' ";
					ArrayList a05List = (ArrayList) sess.createSQLQuery(sql).list();
					if (a05List.size() > 0) {
						sql = "UPDATE a05 set A0501B='" + A0221 + "',a0504='"+A0288+"' where a0000 = '" + a0000s[i] + "' and a0531='0' and a0524='1'";
						sess.createSQLQuery(sql).executeUpdate();
						sess.flush();
					} else {
						sql = "select A0101 from a01 where a0000='" + a0000s[i] + "'";
						userName = sess.createSQLQuery(sql).uniqueResult().toString();
						ZWCCINFO += userName+",";
						ZWCCerrorId+=a0000s[i]+",";
						ZWCCerrornum++;
					}
				}
				if (!StringUtil.isEmpty(A0192E)) {
					sql = "select * from a05 where a0000='" + a0000s[i] + "' and a0531='1' and a0524='1'";
					ArrayList a05List = (ArrayList) sess.createSQLQuery(sql).list();
					if (a05List.size() > 0) {
						sql = "UPDATE a05 set A0501B='" + A0192E + "' where a0000 = '" + a0000s[i] + "' and a0531='1' and a0524='1'";
						sess.createSQLQuery(sql).executeUpdate();
						sess.flush();

					} else {
						sql = "select A0101 from a01 where a0000='" + a0000s[i] + "'";
						userName = sess.createSQLQuery(sql).uniqueResult().toString();
						ZJINFO += userName+",";
						ZJerrorId+=a0000s[i]+",";
						ZJerrornum++;
					}
				}

			}
		this.getPageElement("ZJerrorId").setValue(ZJerrorId);
		this.getPageElement("ZWCCerrorId").setValue(ZWCCerrorId);
		//���ְ��ְ����Ϣ����,ֱ�ӽ��뱣����Ա��Ϣ,С��10����ʾ����,����10�� ��ʾ����  ȷ������Ϣ��Ա�Ƿ��޸�
		if(ZJerrornum==0&&ZWCCerrornum==0) {
			this.setNextEventName("saveA01");
		}else if(ZWCCerrornum==0&&ZJerrornum>0) {
			if(ZJerrornum<=10) {
				ZJINFO=ZJINFO.substring(0, ZJINFO.length()-1);
				ZJINFO+="û��ְ�����ݣ��Ƿ��޸�?";
				this.getExecuteSG().addExecuteCode("saveZWZJConfirm('"+ZJINFO+"')");
			}else {
				this.getExecuteSG().addExecuteCode("saveZWZJConfirm('"+ZJerrornum+"��û��ְ�������Ƿ��޸�')");
			}
			
		}else if(ZWCCerrornum>0&&ZJerrornum==0) {
			if(ZWCCerrornum<=10) {
				ZWCCINFO=ZWCCINFO.substring(0, ZWCCINFO.length()-1);
				ZWCCINFO+="û��ְ�������ݣ��Ƿ��޸�?";
				this.getExecuteSG().addExecuteCode("saveZWZJConfirm('"+ZWCCINFO+"')");
			}else {
				this.getExecuteSG().addExecuteCode("saveZWZJConfirm('"+ZWCCerrornum+"��û��ְ���������Ƿ��޸�')");
			}
		}else if(ZWCCerrornum>0&&ZJerrornum>0) {
			String INFO="";
			if(ZWCCerrornum<=10) {
				ZWCCINFO=ZWCCINFO.substring(0, ZWCCINFO.length()-1);
				INFO+=ZWCCINFO+"û��ְ�������ݣ�";
			}else {
				INFO+=ZWCCerrornum+"��û��ְ�������ݣ�";
			}
			if(ZJerrornum<=10) {
				ZJINFO=ZJINFO.substring(0, ZJINFO.length()-1);
				INFO+=ZJINFO+"û��ְ�����ݣ��Ƿ��޸�";
			}else {
				INFO+=ZJerrornum+"��û��ְ�����ݣ��Ƿ��޸�";
			}
			this.getExecuteSG().addExecuteCode("saveZWZJConfirm('"+INFO+"')");
		}

		return EventRtnType.NORMAL_SUCCESS;
	}

	//ȷ�Ϻ󱣴��ְ��ְ����Ա����Ϣ
	@Transaction
	@PageEvent("saveZWZJConfirm")
	public int saveZWZJConfirm() throws RadowException, AppException {
		
		String ZWCCerrorId = this.getPageElement("ZWCCerrorId").getValue();
		String ZJerrorId = this.getPageElement("ZJerrorId").getValue();
		String[] ZJa0000s = ZJerrorId.split(",");
		String[] ZWCCa0000s=ZWCCerrorId.split(",");
		String A0221 = this.getPageElement("A0221").getValue();
		String A0192E = this.getPageElement("A0192E").getValue();
		String A0288 = this.getPageElement("A0288").getValue();

		HBSession sess = HBUtil.getHBSession();
			for (int i = 0; i < ZJa0000s.length; i++) {
				if (!StringUtil.isEmpty(A0192E)) {
					//����ְ����Ϣ
					String sql="insert into a05 (a0000,a0500,a0531,a0501b,a0524,a0525) values ('"+ZJa0000s[i]+"',sys_guid(),'1','"+A0192E+"','1','1')";
					sess.createSQLQuery(sql).executeUpdate();
					sess.flush();
				}

			}
			for(int i=0;i<ZWCCa0000s.length;i++) {
				/** ��ְ���� */
				if (!StringUtil.isEmpty(A0221)) {
					//����ְ������Ϣ
					String sql="insert into a05 (a0000,a0500,a0531,a0501b,a0524,a0525,a0504) values ('"+ZJa0000s[i]+"',sys_guid(),'1','"+A0192E+"','1','1','"+A0288+"')";
					sess.createSQLQuery(sql).executeUpdate();
					sess.flush();
				}
			}
	
		this.setNextEventName("saveA01");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
	
	// סַͨѶ���� id���ɷ�ʽΪassigned
	@PageEvent("save3")
	@Transaction
	@Synchronous(true)
	public int save3() throws RadowException, AppException {
		HBSession sess = HBUtil.getHBSession();
		Connection conn = sess.connection();
		int count = 0;
		String ids = this.getPageElement("checkIds").getValue();
		String[] a0000s = ids.split(",");
		try {
			String a3701 = this.getPageElement("a3701").getValue();
			String a3707a = this.getPageElement("a3707a").getValue();
			String a3707e = this.getPageElement("a3707e").getValue();
			String a3711 = this.getPageElement("a3711").getValue();
			String a3714 = this.getPageElement("a3714").getValue();
			if (!StringUtil.isEmpty(a3701) || !StringUtil.isEmpty(a3707a) || !StringUtil.isEmpty(a3707e)
					|| !StringUtil.isEmpty(a3711) || !StringUtil.isEmpty(a3714)) {
				if (a0000s.length > 0) {
					StringBuffer sb = new StringBuffer();
					Map<String, String> map = new LinkedHashMap();
					if (!StringUtil.isEmpty(a3701)) {
						map.put("a3701", a3701);
						sb = sb.append("a3701 = ?,");
					}
					if (!StringUtil.isEmpty(a3707a)) {
						map.put("a3707a", a3707a);
						sb = sb.append("a3707a = ?,");
					}
					if (!StringUtil.isEmpty(a3707e)) {
						map.put("a3707e", a3707e);
						sb = sb.append("a3707e = ?,");
					}
					if (!StringUtil.isEmpty(a3711)) {
						map.put("a3711", a3711);
						sb = sb.append("a3711 = ?,");
					}
					if (!StringUtil.isEmpty(a3714)) {
						map.put("a3714", a3714);
						sb = sb.append("a3714 = ?,");
					}
					String sql1 = sb.substring(0, sb.length() - 1);
					PreparedStatement ps = conn.prepareStatement("UPDATE a37 set " + sql1 + " where a0000 = ?");

					for (int i = 0; i < a0000s.length; i++) {
						int j = 1;
						for (String key : map.keySet()) {
							ps.setString(j, map.get(key));
							j++;
						}
						ps.setString(j, a0000s[i]);
						ps.addBatch();
						count++;
						if (count >= 500) {
							ps.executeBatch();
							ps.clearParameters();
							// conn.commit();
							count = 0;
						}
					}
					if (count > 0) {
						ps.executeBatch();
						// conn.commit();
						count = 0;
					}
					if (ps != null) {
						ps.close();
					}
					// ��¼��־
					List list = new ArrayList();

					if (!StringUtil.isEmpty(a3701)) {
						String[] arr = { "A3701", "", "", "�칫��ַ" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a3707a)) {
						String[] arr = { "A3707a", "", "", "�칫�绰" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a3707e)) {
						String[] arr = { "A3707e", "", "", "����绰" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a3711)) {
						String[] arr = { "A3711", "", "", "��ͥסַ" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a3714)) {
						String[] arr = { "A3714", "", "", "סַ�ʱ�" };
						list.add(arr);
					}
					applog.createLog("3706", "A37", "", "", "��ԱסַͨѶ�����޸�", list);
				}

			} else {
				this.setMainMessage("û��Ҫ�޸ĵ���");
				return EventRtnType.NORMAL_SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ�ܣ��쳣��Ϣ��" + e.getMessage());
			return EventRtnType.FAILD;
		}
		this.setMainMessage("����ɹ�");
		String tableType = this.getPageElement("type").getValue();
		reloadCustomQuery(tableType);
		return EventRtnType.NORMAL_SUCCESS;
	}

	// ְ��
	@PageEvent("save4")
	@Transaction
	@Synchronous(true)
	public int save4() throws RadowException, AppException {
		HBSession sess = HBUtil.getHBSession();
		Connection conn = sess.connection();
		String ids = this.getPageElement("checkIds").getValue();
		String[] a0000s = ids.split(",");
		try {
			String a0222 = this.getPageElement("a0222").getValue();// ��λ���
			String a0201b = this.getPageElement("a0201b").getValue();// ��ְ��������
			String a0215a = this.getPageElement("a0215a").getValue();// ְ������
			String a0216a = this.getPageElement("a0216a").getValue();// ְ����
			String a0201e = this.getPageElement("a0201e").getValue();// ��Ա���
			String a0201d = this.getPageElement("a0201d").getValue();// ���ӳ�Ա��ʶ
			String a0221 = this.getPageElement("a0221").getValue();// ְ����
			String a0229 = this.getPageElement("a0229").getValue();// �ֹܣ����£�����
			String a0247 = this.getPageElement("a0247").getValue();// ѡ�����÷�ʽ
			String a0251 = this.getPageElement("a0251").getValue();// ��ְ�䶯����
			String a0245 = this.getPageElement("a0245").getValue();// ��������׼��ְ���ĺ�
			String a0219 = this.getPageElement("a0219").getValue();// ְ�����
			String a0243 = this.getPageElement("a0243").getValue();// ��������׼��ְ��ʱ��
			String a0288 = this.getPageElement("a0288").getValue();// ��ְ����ʱ��
			String a0255 = "1";
			if (!StringUtil.isEmpty(a0222) || !StringUtil.isEmpty(a0201b) || !StringUtil.isEmpty(a0215a)
					|| !StringUtil.isEmpty(a0201e) || !StringUtil.isEmpty(a0201d) || !StringUtil.isEmpty(a0221)
					|| !StringUtil.isEmpty(a0229) || !StringUtil.isEmpty(a0247) || !StringUtil.isEmpty(a0251)
					|| !StringUtil.isEmpty(a0245) || !StringUtil.isEmpty(a0219) || !StringUtil.isEmpty(a0243)
					|| !StringUtil.isEmpty(a0288) || !StringUtil.isEmpty(a0216a)) {
				if (StringUtil.isEmpty(a0222)) {
					this.setMainMessage("��λ�����Ϊ�գ�");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if (StringUtil.isEmpty(a0201b)) {
					this.setMainMessage("��ְ����/�������� ����Ϊ�գ�");
					return EventRtnType.NORMAL_SUCCESS;
				}
				B01 b01 = (B01) sess.get(B01.class, a0201b);
				String a0201a = b01.getB0101();
				String a0201c = b01.getB0104();
				// ������У���ʶ��Ϊ0
				CreateSysOrgBS.updateB01UpdatedWithZero(b01.getB0111());
				PreparedStatement ps = conn
						.prepareStatement("INSERT INTO a02(a0000,a0222,a0201a,a0201b,a0201c,a0215a,a0201e,a0201d,a0221"
								+ ",a0229,a0247,a0251,a0245,a0219,a0243,a0288,a0255,a0200,a0216a) VALUES("
								+ "?, ?, ?, ? ,? ,? ,? ,?, ?, ?, ?, ? ,? ,? ,? ,? ,?, ?, ?)");
				int count = 0;
				if (a0000s.length > 0) {

					for (int i = 0; i < a0000s.length; i++) {
						String uuid = UUID.randomUUID().toString();
						ps.setString(1, a0000s[i]);
						ps.setString(2, a0222.trim());
						ps.setString(3, a0201a);
						ps.setString(4, a0201b.trim());
						ps.setString(5, a0201c);
						ps.setString(6, a0215a.trim());
						ps.setString(7, a0201e.trim());
						ps.setString(8, a0201d.trim());
						ps.setString(9, a0221.trim());
						ps.setString(10, a0229.trim());
						ps.setString(11, a0247.trim());
						ps.setString(12, a0251.trim());
						ps.setString(13, a0245.trim());
						ps.setString(14, a0219.trim());
						ps.setString(15, a0243.trim());
						ps.setString(16, a0288.trim());
						ps.setString(17, a0255.trim());
						ps.setString(18, uuid);
						ps.setString(19, a0216a.trim());
						ps.addBatch();
						count++;
						if (count >= 500) {
							ps.executeBatch();
							ps.clearParameters();
							// conn.commit();
							count = 0;
						}
					}
					if (count > 0) {
						ps.executeBatch();
						// conn.commit();
						count = 0;
					}
					if (ps != null) {
						ps.close();
					}
					// ��¼��־
					List list = new ArrayList();

					if (!StringUtil.isEmpty(a0222)) {
						String[] arr = { "a0222", "", "", "��λ���" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0201b)) {
						String[] arr = { "a0201b", "", "", "��ְ�������� " };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0215a)) {
						String[] arr = { "a0215a", "", "", "ְ������" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0201e)) {
						String[] arr = { "a0201e", "", "", "��Ա���" };
						list.add(arr);
					}

					if (!StringUtil.isEmpty(a0201d)) {
						String[] arr = { "a0201d", "", "", "���ӳ�Ա��ʶ" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0221)) {
						String[] arr = { "a0221", "", "", "ְ����" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0229)) {
						String[] arr = { "a0229", "", "", "�ֹܣ����£�����" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0247)) {
						String[] arr = { "a0247", "", "", "ѡ�����÷�ʽ" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0251)) {
						String[] arr = { "a0251", "", "", "��ְ�䶯����" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0245)) {
						String[] arr = { "a0245", "", "", "��������׼��ְ���ĺ�" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0219)) {
						String[] arr = { "a0219", "", "", "ְ�����" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0243)) {
						String[] arr = { "a0243", "", "", "��������׼��ְ��ʱ��" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0288)) {
						String[] arr = { "a0288", "", "", "��ְ����ʱ��" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0216a)) {
						String[] arr = { "a0216a", "", "", "ְ����" };
						list.add(arr);
					}
					applog.createLog("3707", "A02", "", "", "��Աְ����Ϣ��������", list);
				}

			} else {
				this.setMainMessage("û��Ҫ�޸ĵ���");
				return EventRtnType.NORMAL_SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.setMainMessage("����ɹ�");
		String tableType = this.getPageElement("type").getValue();
		reloadCustomQuery(tableType);
		return EventRtnType.NORMAL_SUCCESS;
	}

	// ������Ϣ
	@PageEvent("save2")
	@Transaction
	@Synchronous(true)
	public int save2() throws RadowException, AppException {

		getCheckPeople();

		String ids = this.getPageElement("checkIds").getValue();
		String[] a0000s = ids.split(",");
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			Connection conn = sess.connection();
			String a1151Type = this.getPageElement("a1151Type").getValue(); // �޸ķ�ʽ
			String a1521s = this.getPageElement("a1521").getValue(); // ����(��ʼ)���
			String a1517 = this.getPageElement("a1517").getValue(); // ���˽��
			String a15z101 = this.getPageElement("a15z101").getValue(); // ��ȿ�������
			int count = 0;
			int count1 = 0;
			if (!StringUtil.isEmpty(a1151Type) || !StringUtil.isEmpty(a1521s) || !StringUtil.isEmpty(a1517)
					|| !StringUtil.isEmpty(a15z101)) {
				if (StringUtil.isEmpty(a1151Type)) {
					this.setMainMessage("��ѡ�񿼺���Ϣ�޸ķ�ʽ��");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if (StringUtil.isEmpty(a1521s) && a1151Type.equals("0")) {
					this.setMainMessage("����д���ˣ���ʼ����ȣ�");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if (StringUtil.isEmpty(a1517) && a1151Type.equals("0")) {
					this.setMainMessage("����д���˽����");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if (StringUtil.isEmpty(a15z101) && a1151Type.equals("1")) {
					this.setMainMessage("����д��ȿ����");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if (a1151Type.equals("1") && !StringUtil.isEmpty(a1517)) {
					this.setMainMessage("�޸ķ�ʽΪֱ���޸�����(�����б����)��������д���˽��");
					return EventRtnType.NORMAL_SUCCESS;
				}
				
				String a1527 = this.getPageElement("a1527").getValue();

					if (a1527 == null || "".equals(a1527)) {
					this.setMainMessage("������������������Ϊ�գ�");
					return EventRtnType.NORMAL_SUCCESS;
				}

				
				
				String[] years = a1521s.split(",");
				//String a1527 = years.length + "";
				if (a0000s.length > 0) {
					if (!StringUtil.isEmpty(a15z101)) { // �޸ķ�ʽ: ֱ���޸�����(�����б����)
						PreparedStatement ps = conn.prepareStatement("UPDATE a01 set a15z101 = ? where a0000 = ?");
						// StringBuffer sql = new StringBuffer();
						for (int i = 0; i < a0000s.length; i++) {
							ps.setString(1, a15z101.trim());
							ps.setString(2, a0000s[i]);
							ps.addBatch();
							count++;
							if (count >= 500) {
								ps.executeBatch();
								ps.clearParameters();
								// conn.commit();
								count = 0;
							}
						}
						if (count > 0) {
							ps.executeBatch();
							// conn.commit();
							count = 0;
						}
						if (ps != null) {
							ps.close();
						}
					} else { // �޸ķ�ʽ: ������޸�(�Զ���������)
						PreparedStatement ps = conn
								.prepareStatement("select A1521 from a15 where a1521 = ? and a0000 = ?");
						PreparedStatement ps1 = conn
								.prepareStatement("select a1500 from a15 where a1521 = ? and a0000 = ?");
						PreparedStatement ps2 = conn.prepareStatement("update a15 set A1517 = ?, A1527 = ?  where a1500 = ?");
						PreparedStatement ps3 = conn.prepareStatement(
								"INSERT INTO a15(a0000,a1500,A1517,A1521,A1527) VALUES(" + "?, ?, ?, ? ,?)");
						for (int i = 0; i < a0000s.length; i++) {
							for (int j = 0; j < years.length; j++) {
								ps.setString(1, years[j]);
								ps.setString(2, a0000s[i]);
								ResultSet rs = ps.executeQuery();
								// List list =
								// sess.createSQLQuery("select A1521 from a15 where a1521 = '"+years[j]+"' and
								// a0000 = '"+a0000s[i]+"'").list();
								if (rs.next()) {
									ps1.setString(1, years[j]);
									ps1.setString(2, a0000s[i]);
									ResultSet rs1 = ps1.executeQuery();
									while (rs1.next()) {
										String a1500 = rs1.getString(1);
										ps2.setString(1, a1517.trim());
										ps2.setString(2, a1527);
										ps2.setString(3, a1500);
										ps2.addBatch();
										count++;
										if (count >= 500) {
											ps2.executeBatch();
											ps2.clearParameters();
											// conn.commit();
											count = 0;
										}
									}
									if (rs != null) {
										rs.close();
									}
									if (rs1 != null) {
										rs1.close();
									}
								} else {
									String uuid = UUID.randomUUID().toString();
									ps3.setString(1, a0000s[i]);
									ps3.setString(2, uuid);
									ps3.setString(3, a1517.trim());
									ps3.setString(4, years[j]);
									ps3.setString(5, a1527);
									ps3.addBatch();
									count1++;
									if (count1 >= 500) {
										ps3.executeBatch();
										ps3.clearParameters();
										// conn.commit();
										count1 = 0;
									}
								}
								
								
							}
						}
						if (count > 0) {
							ps2.executeBatch();
							// conn.commit();
							count = 0;
						}
						if (count1 > 0) {
							ps3.executeBatch();
							// conn.commit();
							count1 = 0;
						}
						if (ps != null) {
							ps.close();
						}
						if (ps1 != null) {
							ps1.close();
						}
						if (ps2 != null) {
							ps2.close();
						}
						if (ps3 != null) {
							ps3.close();
						}

						String sqlOne = "";
						if (DBUtil.getDBType() == DBType.ORACLE) {
							sqlOne = "select * from (select a1521,a1517 from A15 where a0000 = ?  order by a1521 desc) where rownum<= ?";
						} else {
							sqlOne = "select a1521,a1517 from A15 where a0000 = ? order by a1521 desc limit ?";
						}

						PreparedStatement ps4 = conn.prepareStatement(sqlOne);
						PreparedStatement ps5 = conn.prepareStatement("UPDATE a01 set A15z101 = ? where a0000 = ?");
						for (int i = 0; i < a0000s.length; i++) {
							StringBuffer desc = new StringBuffer("");
							ps4.setString(1, a0000s[i]);
							// �ж�������꿼��
							String sql = "select a1527 from A15 where a0000 = '" + a0000s[i] + "'";
							List lista36 = sess.createQuery(sql).list();

							// ���lista36����Ԫ��Ϊ�գ�����ָ���쳣����Ҫ�ж�
							Object a1527Ob = lista36.get(0);

							int a1527F = 3; // ѡ����ȸ�����Ĭ��Ϊ3
							if (a1527Ob != null && !a1527Ob.toString().equals("")) {
								a1527F = Integer.parseInt(lista36.get(0).toString());
							}

							ps4.setInt(2, a1527F);

							ResultSet rs = ps4.executeQuery();
							while (rs.next()) {
								String a1521 = rs.getString(1);// �������
								String a1517s = rs.getString(2);// ���˽��
								String a1517Name = HBUtil.getCodeName("ZB18", a1517s);
								// desc.append(a1521 + "����ȿ���" + a1517Name + "��");
								if(a1517Name.equals("�����ȴ�")) {
									desc.insert(0,a1521+"����ȿ���"+a1517Name+"��");
								}else {
									desc.insert(0,a1521+"����ȿ���"+a1517Name+"��");
								}
							}
							if (rs != null) {
								rs.close();
							}
							if (desc.length() > 0) {
								desc.replace(desc.length() - 1, desc.length(), "��");
							}
							ps5.setString(1, desc.toString());
							ps5.setString(2, a0000s[i]);
							ps5.addBatch();
							count++;
							if (count >= 500) {
								ps5.executeBatch();
								ps5.clearParameters();
								// conn.commit();
								count = 0;
							}

						}
						if (count > 0) {
							ps5.executeBatch();
							// conn.commit();
							count = 0;
						}
						if (ps4 != null) {
							ps4.close();
						}
						if (ps5 != null) {
							ps5.close();
						}
					}
					// ��¼��־
					List list = new ArrayList();

					if (!StringUtil.isEmpty(a1521s)) {
						String[] arr = { "a1521", "", "", "�������" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a1517)) {
						String[] arr = { "a1517", "", "", "���˽��" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a15z101)) {
						String[] arr = { "a15z101", "", "", "��ȿ��˽������" };
						list.add(arr);
					}
					applog.createLog("3705", "A15", "", "", "��Ա������Ϣ�����޸�", list);
				}

			} else {
				this.setMainMessage("û��Ҫ�޸ĵ���");
				return EventRtnType.NORMAL_SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.setMainMessage("����ɹ�");
		String tableType = this.getPageElement("type").getValue();
		reloadCustomQuery(tableType);
		return EventRtnType.NORMAL_SUCCESS;
	}

	// ����
	@PageEvent("save5")
	@Transaction
	@Synchronous(true)
	public int save5() throws RadowException, AppException {

		getCheckPeople();

		String ids = this.getPageElement("checkIds").getValue();
		String[] a0000s = ids.split(",");
		HBSession sess = HBUtil.getHBSession();
		Connection conn = sess.connection();
		try {
			String a1404b = this.getPageElement("a1404b").getValue();
			String a1404a = this.getPageElement("a1404a").getValue();
			String a1414 = this.getPageElement("a1414").getValue();
			String a1428 = this.getPageElement("a1428").getValue();
			String a1407 = this.getPageElement("a1407").getValue();
			String a1424 = this.getPageElement("a1424").getValue();
			String a1411a = this.getPageElement("a1411a").getValue();
			String a1415 = this.getPageElement("a1415").getValue(); // �ܽ���ʱְ����

			if (!StringUtil.isEmpty(a1404b) || !StringUtil.isEmpty(a1404a) || !StringUtil.isEmpty(a1414)
					|| !StringUtil.isEmpty(a1428) || !StringUtil.isEmpty(a1407) || !StringUtil.isEmpty(a1424)
					|| !StringUtil.isEmpty(a1411a) || !StringUtil.isEmpty(a1415)) {
				if (StringUtil.isEmpty(a1404b)) {
					this.setMainMessage("����д�������ƴ��룡");
					return EventRtnType.NORMAL_SUCCESS;
				}
				PreparedStatement ps = conn.prepareStatement(
						"INSERT INTO a14(a0000,a1400,a1404a,a1404b,a1407,a1411a,a1414,a1424,a1428,a1415) VALUES("
								+ "?, ?, ?, ? ,? ,? ,? ,? ,?,?)");
				int count = 0;
				for (int i = 0; i < a0000s.length; i++) {
					String uuid = UUID.randomUUID().toString();
					ps.setString(1, a0000s[i]);
					ps.setString(2, uuid);
					ps.setString(3, a1404a.trim());
					ps.setString(4, a1404b.trim());
					ps.setString(5, a1407.trim());
					ps.setString(6, a1411a.trim());
					ps.setString(7, a1414.trim());
					ps.setString(8, a1424.trim());
					ps.setString(9, a1428.trim());
					ps.setString(10, a1415.trim());
					ps.addBatch();
					count++;
					if (count >= 500) {
						ps.executeBatch();
						ps.clearParameters();
						// conn.commit();
						count = 0;
					}

					A01 a01_old = (A01) sess.get(A01.class, a0000s[i]);

					A14 a14 = new A14();

					if (!StringUtil.isEmpty(a1404b)) { // ���ʹ���
						a14.setA1404b(a1404b);
					}
					if (!StringUtil.isEmpty(a1404a)) { // ��������

						a14.setA1404a(a1404a);
					}
					if (!StringUtil.isEmpty(a1414)) { // ��׼���ؼ���
						a14.setA1414(a1414);
					}
					if (!StringUtil.isEmpty(a1415)) { // �ܽ���ʱְ����
						a14.setA1415(a1415);
					}
					if (!StringUtil.isEmpty(a1428)) { // ��׼��������
						a14.setA1428(a1428);
					}
					if (!StringUtil.isEmpty(a1407)) { // ������׼ʱ��
						a14.setA1407(a1407);
					}
					if (!StringUtil.isEmpty(a1424)) { // ���ͳ���ʱ��
						a14.setA1424(a1424);
					}
					if (!StringUtil.isEmpty(a1411a)) { // ��׼����
						a14.setA1411a(a1411a);
					}

					applog.createLog("3141", "A14", a01_old.getA0000(), a01_old.getA0101(), "������¼",
							new Map2Temp().getLogInfo(new A14(), a14));

				}
				if (count > 0) {
					ps.executeBatch();
					// conn.commit();
					count = 0;
				}
				if (ps != null) {
					ps.close();
				}
				PreparedStatement ps1 = conn.prepareStatement("select A1411a,A1404b,A1404a from A14 where a0000 = ? ");
				PreparedStatement ps2 = conn.prepareStatement("UPDATE a01 set A14z101 = ? where a0000 = ?");
				for (int i = 0; i < a0000s.length; i++) {
					StringBuffer desc2 = new StringBuffer();
					ps1.setString(1, a0000s[i]);
					ResultSet rs = ps1.executeQuery();

					while (rs.next()) {
						String a1411as = rs.getString(1);// ��׼����
						String a1404bs = rs.getString(2);// ���ʹ���
						String a1404as = rs.getString(3);// ��������
						if (a1404bs.startsWith("01")) {// ��
							desc2.append("��" + (a1411as != null ? a1411as : "") + "��׼��").append(a1404as + ";");
						} else {// ��
							desc2.append("��" + (a1411as != null ? a1411as : "") + "��׼��").append("��" + a1404as + "����;");
						}
					}
					if (rs != null) {
						rs.close();
					}

					if (desc2.length() > 0) {
						desc2.replace(desc2.length() - 1, desc2.length(), "��");
					}

					ps2.setString(1, desc2.toString());
					ps2.setString(2, a0000s[i]);
					ps2.addBatch();
					count++;
					if (count >= 500) {
						ps2.executeBatch();
						ps2.clearParameters();
						// conn.commit();
						count = 0;
					}

				}
				if (count > 0) {
					ps2.executeBatch();
					// conn.commit();
					count = 0;
				}
				if (ps1 != null) {
					ps1.close();
				}
				if (ps2 != null) {
					ps2.close();
				}
				// ��¼��־
				/*
				 * List list = new ArrayList(); if (!StringUtil.isEmpty(a1404b)) { String[] arr
				 * = { "a1404b", "", "", "���ʹ���" }; list.add(arr); } if
				 * (!StringUtil.isEmpty(a1404a)) { String[] arr = { "a1404a", "", "", "��������" };
				 * list.add(arr); } if (!StringUtil.isEmpty(a1414)) { String[] arr = { "a1414",
				 * "", "", "��׼���ؼ���" }; list.add(arr); } if (!StringUtil.isEmpty(a1415)) {
				 * String[] arr = { "a1415", "", "", "�ܽ���ʱְ����" }; list.add(arr); } if
				 * (!StringUtil.isEmpty(a1428)) { String[] arr = { "a1428", "", "", "��׼��������" };
				 * list.add(arr); } if (!StringUtil.isEmpty(a1407)) { String[] arr = { "a1407",
				 * "", "", "������׼ʱ��" }; list.add(arr); } if (!StringUtil.isEmpty(a1424)) {
				 * String[] arr = { "a1424", "", "", "���ͳ���ʱ��" }; list.add(arr); } if
				 * (!StringUtil.isEmpty(a1411a)) { String[] arr = { "a1411a", "", "", "��׼����" };
				 * list.add(arr); }
				 */
				// applog.createLog("3708", "A14", "", "", "��Ա������Ϣ��������", list);
			} else {
				this.setMainMessage("û��Ҫ�޸ĵ���");
				return EventRtnType.NORMAL_SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.setMainMessage("����ɹ�");
		String tableType = this.getPageElement("type").getValue();
		reloadCustomQuery(tableType);
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("save6")
	@Transaction
	@Synchronous(true)
	public int save6() throws RadowException, AppException {
		String ids = this.getPageElement("checkIds").getValue();
		String[] a0000s = ids.split(",");
		int count = 0;
		HBSession sess = HBUtil.getHBSession();
		Connection conn = sess.connection();
		try {
			String a2907 = this.getPageElement("a2907").getValue();// ���뱾��λ����
			String a2911 = this.getPageElement("a2911").getValue();// ���뱾��λ�䶯���
			String a2941 = this.getPageElement("a2941").getValue();// ��ԭ��λְ��
			String a2944s = this.getPageElement("a2944s_combo").getValue();// ��ԭ��λְ����
			String a2921a = this.getPageElement("a2921a").getValue();// ���뱾��λǰ������λ����
			String a2949 = this.getPageElement("a2949").getValue();// ����Ա�Ǽ�ʱ��
			if (!StringUtil.isEmpty(a2907) || !StringUtil.isEmpty(a2911) || !StringUtil.isEmpty(a2941)
					|| !StringUtil.isEmpty(a2944s) || !StringUtil.isEmpty(a2921a) || !StringUtil.isEmpty(a2949)) {
				if (a0000s.length > 0) {
					StringBuffer sb = new StringBuffer();
					Map<String, String> map = new LinkedHashMap();
					if (!StringUtil.isEmpty(a2907)) {
						map.put("a2907", a2907);
						sb = sb.append("a2907 = ?,");
					}
					if (!StringUtil.isEmpty(a2911)) {
						map.put("a2911", a2911);
						sb = sb.append("a2911 = ?,");
					}
					if (!StringUtil.isEmpty(a2941)) {
						map.put("a2941", a2941);
						sb = sb.append("a2941 = ?,");
					}
					if (!StringUtil.isEmpty(a2944s)) {
						map.put("a2944", a2944s);
						sb = sb.append("a2944 = ?,");
					}
					if (!StringUtil.isEmpty(a2921a)) {
						map.put("a2921a", a2921a);
						sb = sb.append("a2921a = ?,");
					}
					if (!StringUtil.isEmpty(a2949)) {
						map.put("a2949", a2949);
						sb = sb.append("a2949 = ?,");
					}
					String sql1 = sb.substring(0, sb.length() - 1);
					PreparedStatement ps = conn.prepareStatement("UPDATE a29 set " + sql1 + " where a0000 = ?");

					for (int i = 0; i < a0000s.length; i++) {
						int j = 1;
						for (String key : map.keySet()) {
							ps.setString(j, map.get(key));
							j++;
						}
						ps.setString(j, a0000s[i]);
						ps.addBatch();
						count++;
						if (count >= 500) {
							ps.executeBatch();
							ps.clearParameters();
							// conn.commit();
							count = 0;
						}
					}
					if (count > 0) {
						ps.executeBatch();
						// conn.commit();
						count = 0;
					}
					if (ps != null) {
						ps.close();
					}
					// ��¼��־
					List list = new ArrayList();

					if (!StringUtil.isEmpty(a2907)) {
						String[] arr = { "A2907", "", "", "���뱾��λ����" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a2911)) {
						String[] arr = { "A2911", "", "", "���뱾��λ�䶯���" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a2941)) {
						String[] arr = { "A2941", "", "", "��ԭ��λְ��" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a2944s)) {
						String[] arr = { "A2944", "", "", "��ԭ��λְ����" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a2921a)) {
						String[] arr = { "A2921a", "", "", "���뱾��λǰ������λ����" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a2949)) {
						String[] arr = { "A2949", "", "", "����Ա�Ǽ�ʱ��" };
						list.add(arr);
					}
					applog.createLog("3706", "A29", "", "", "������������޸�", list);
				}

			} else {
				this.setMainMessage("û��Ҫ�޸ĵ���");
				return EventRtnType.NORMAL_SUCCESS;
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.setMainMessage("����ɹ�");
		String tableType = this.getPageElement("type").getValue();
		reloadCustomQuery(tableType);

		return EventRtnType.NORMAL_SUCCESS;
	}

	/*
	 * // ��ѵ��
	 * 
	 * @PageEvent("save6")
	 * 
	 * @Transaction
	 * 
	 * @Synchronous(true) public int save6() throws RadowException, AppException {
	 * String[] a0000s = ids.split(","); HBSession sess = HBUtil.getHBSession();
	 * Connection conn = sess.connection(); try { String a1131 =
	 * this.getPageElement("a1131").getValue(); String a1101 =
	 * this.getPageElement("a1101").getValue(); String a1107 =
	 * this.getPageElement("a1107").getValue(); String a1111 =
	 * this.getPageElement("a1111").getValue(); String a1107c =
	 * this.getPageElement("a1107c").getValue(); String a1108 =
	 * this.getPageElement("a1108").getValue(); String a1114 =
	 * this.getPageElement("a1114").getValue(); String a1121a =
	 * this.getPageElement("a1121a").getValue(); String a1127 =
	 * this.getPageElement("a1127").getValue(); String a1104 =
	 * this.getPageElement("a1104").getValue(); String a1151 =
	 * this.getPageElement("a1151").getValue();
	 * 
	 * if (!StringUtil.isEmpty(a1131) || !StringUtil.isEmpty(a1131) ||
	 * !StringUtil.isEmpty(a1131) || !StringUtil.isEmpty(a1131) ||
	 * !StringUtil.isEmpty(a1131) || !StringUtil.isEmpty(a1131) ||
	 * !StringUtil.isEmpty(a1131) || !StringUtil.isEmpty(a1131) ||
	 * !StringUtil.isEmpty(a1131) || !StringUtil.isEmpty(a1131) ||
	 * !StringUtil.isEmpty(a1131) || !StringUtil.isEmpty(a1131) ||
	 * !StringUtil.isEmpty(a1131)) { PreparedStatement ps = conn
	 * .prepareStatement("INSERT INTO a11(a0000,a1100,a1131,a1101,a1107,a1111,a1107c,"
	 * + "a1108,a1114,a1121a,a1127,a1104,a1151,a1107a,a1107b) VALUES(" +
	 * "?, ?, ?, ? ,? ,? ,? ,? ,?, ? ,? ,? ,? ,? ,?)"); PreparedStatement ps1 = conn
	 * .prepareStatement("INSERT INTO a41(a0000,a1100,a4100) VALUES(" + "?, ?, ?)");
	 * int count = 0; int count1 = 0; for (int i = 0; i < a0000s.length; i++) { if
	 * (a1107 != null && !"".equals(a1107) && a1111 != null && !"".equals(a1111)) {
	 * if (a1107.length() == 6) { a1107 += "01"; } if (a1111.length() == 6) { a1111
	 * += "01"; } // ������ѵ�м��¼��졣 int days = DateUtil.getDaysBetween(
	 * DateUtil.stringToDate(a1107, "yyyymmdd"), DateUtil.stringToDate(a1111,
	 * "yyyymmdd")); int mounthA1107a = days / 31;// �� int dayA1107b = days % 31;//
	 * �� String uuid = UUID.randomUUID().toString(); String uuid1 =
	 * UUID.randomUUID().toString(); ps.setString(1, a0000s[i]); ps.setString(2,
	 * uuid); ps.setString(3, a1131.trim()); ps.setString(4, a1101.trim());
	 * ps.setString(5, a1107.trim()); ps.setString(6, a1111.trim()); if
	 * (!StringUtil.isEmpty(a1107c)) { ps.setLong(7, Long.parseLong(a1107c.trim()));
	 * } else { ps.setString(7, ""); } if (!StringUtil.isEmpty(a1108)) {
	 * ps.setLong(8, Long.parseLong(a1108.trim())); } else { ps.setString(8, ""); }
	 * 
	 * ps.setString(9, a1114.trim()); ps.setString(10, a1121a.trim());
	 * ps.setString(11, a1127.trim()); ps.setString(12, a1104.trim());
	 * ps.setString(13, a1151.trim()); ps.setLong(14, (long) mounthA1107a);
	 * ps.setLong(15, (long) dayA1107b); ps.addBatch();
	 * 
	 * ps1.setString(1, a0000s[i]); ps1.setString(2, uuid); ps1.setString(3, uuid1);
	 * ps1.addBatch(); count++; count1++; if (count >= 500) { ps.executeBatch();
	 * ps.clearParameters(); // conn.commit(); count = 0; } if (count1 >= 500) {
	 * ps1.executeBatch(); ps1.clearParameters(); // conn.commit(); count1 = 0; }
	 * 
	 * } else { String uuid = UUID.randomUUID().toString(); String uuid1 =
	 * UUID.randomUUID().toString(); ps.setString(1, a0000s[i]); ps.setString(2,
	 * uuid); ps.setString(3, a1131.trim()); ps.setString(4, a1101.trim());
	 * ps.setString(5, a1107.trim()); ps.setString(6, a1111.trim()); if
	 * (!StringUtil.isEmpty(a1107c)) { ps.setLong(7, Long.parseLong(a1107c.trim()));
	 * } else { ps.setString(7, ""); } if (!StringUtil.isEmpty(a1108)) {
	 * ps.setLong(8, Long.parseLong(a1108.trim())); } else { ps.setString(8, ""); }
	 * 
	 * ps.setString(9, a1114.trim()); ps.setString(10, a1121a.trim());
	 * ps.setString(11, a1127.trim()); ps.setString(12, a1104.trim());
	 * ps.setString(13, a1151.trim()); ps.setString(14, ""); ps.setString(15, "");
	 * ps.addBatch();
	 * 
	 * ps1.setString(1, a0000s[i]); ps1.setString(2, uuid); ps1.setString(3, uuid1);
	 * ps1.addBatch(); count++; count1++; if (count >= 500) { ps.executeBatch();
	 * ps.clearParameters(); // conn.commit(); count = 0; } if (count1 >= 500) {
	 * ps1.executeBatch(); ps1.clearParameters(); // conn.commit(); count1 = 0; } }
	 * } if (count > 0) { ps.executeBatch(); // conn.commit(); count = 0; } if
	 * (count1 > 0) { ps1.executeBatch(); // conn.commit(); count1 = 0; } if (ps !=
	 * null) { ps.close(); } if (ps1 != null) { ps1.close(); } // ��¼��־ List list =
	 * new ArrayList(); if (!StringUtil.isEmpty(a1131)) { String[] arr = { "a1131",
	 * "", "", "��ѵ����" }; list.add(arr); } if (!StringUtil.isEmpty(a1101)) { String[]
	 * arr = { "a1101", "", "", "��ѵ���" }; list.add(arr); } if
	 * (!StringUtil.isEmpty(a1107)) { String[] arr = { "a1107", "", "", "��ѵ��ʼ����" };
	 * list.add(arr); } if (!StringUtil.isEmpty(a1111)) { String[] arr = { "a1111",
	 * "", "", "��ѵ��������" }; list.add(arr); } if (!StringUtil.isEmpty(a1107c)) {
	 * String[] arr = { "a1107c", "", "", "��ѵʱ�����죩" }; list.add(arr); } if
	 * (!StringUtil.isEmpty(a1108)) { String[] arr = { "a1108", "", "", "ѧʱ" };
	 * list.add(arr); } if (!StringUtil.isEmpty(a1114)) { String[] arr = { "a1114",
	 * "", "", "��ѵ���쵥λ" }; list.add(arr); } if (!StringUtil.isEmpty(a1121a)) {
	 * String[] arr = { "a1121a", "", "", "��ѵ��������" }; list.add(arr); } if
	 * (!StringUtil.isEmpty(a1127)) { String[] arr = { "a1127", "", "", "��ѵ�������" };
	 * list.add(arr); } if (!StringUtil.isEmpty(a1104)) { String[] arr = { "a1104",
	 * "", "", "��ѵ���״̬" }; list.add(arr); } if (!StringUtil.isEmpty(a1151)) {
	 * String[] arr = { "a1151", "", "", "��������������ѵ��ʶ" }; list.add(arr); }
	 * applog.createLog("3709", "A11", "", "", "��Ա��ѵ����Ϣ��������", list);
	 * 
	 * } else { this.setMainMessage("û��Ҫ�޸ĵ���"); return EventRtnType.NORMAL_SUCCESS;
	 * } } catch (Exception e) { e.printStackTrace(); this.setMainMessage("����ʧ�ܣ�");
	 * return EventRtnType.FAILD; } this.setMainMessage("����ɹ�"); String tableType =
	 * this.getPageElement("type").getValue(); reloadCustomQuery(tableType); return
	 * EventRtnType.NORMAL_SUCCESS; }
	 */

	@PageEvent("count")
	public int count() throws RadowException, AppException {
		String a1107 = this.getPageElement("a1107").getValue();// ��ѵ��ʼʱ��
		String a1111 = this.getPageElement("a1111").getValue();// ��ѵ����ʱ��
		String a1107c = this.getPageElement("a1107c").getValue();
		String a1108 = this.getPageElement("a1108").getValue();
		if (a1107 != null && !"".equals(a1107) && a1111 != null && !"".equals(a1111)) {
			if (a1107.length() == 6) {
				a1107 += "01";
			}
			if (a1111.length() == 6) {
				a1111 += "01";
			}
			int start = Integer.valueOf(a1107);
			int end = Integer.valueOf(a1111);
			if (start > end) {
				this.setMainMessage("��ʼʱ�䲻�ܴ��ڽ���ʱ��");
				return EventRtnType.NORMAL_SUCCESS;
			}
			int days = DateUtil.getDaysBetween(DateUtil.stringToDate(a1107, "yyyymmdd"),
					DateUtil.stringToDate(a1111, "yyyymmdd"));
			int dayA1107c = days;// ������
			int hour = days * 8;// ѧʱ
			if (a1107c == null || "".equals(a1107c)) {
				this.getPageElement("a1107c").setValue("" + dayA1107c);
			}
			if (a1108 == null || "".equals(a1108)) {
				this.getPageElement("a1108").setValue("" + hour);
			}
		} else {
			this.getPageElement("a1107c").setValue("");
			this.getPageElement("a1108").setValue("");
		}

		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("count1")
	public int count1() throws RadowException, AppException {
		String a1107 = this.getPageElement("a1107").getValue();// ��ѵ��ʼʱ��
		String a1111 = this.getPageElement("a1111").getValue();// ��ѵ����ʱ��
		String a1107c = this.getPageElement("a1107c").getValue();
		String a1108 = this.getPageElement("a1108").getValue();
		if (a1107 != null && !"".equals(a1107) && a1111 != null && !"".equals(a1111)) {
			if (a1107.length() == 6) {
				a1107 += "01";
			}
			if (a1111.length() == 6) {
				a1111 += "01";
			}
			int start = Integer.valueOf(a1107);
			int end = Integer.valueOf(a1111);
			if (start > end) {
				this.setMainMessage("��ʼʱ�䲻�ܴ��ڽ���ʱ��");
				return EventRtnType.NORMAL_SUCCESS;
			}
			int days = DateUtil.getDaysBetween(DateUtil.stringToDate(a1107, "yyyymmdd"),
					DateUtil.stringToDate(a1111, "yyyymmdd"));
			int dayA1107c = days;// ������
			int hour = days * 8;// ѧʱ
			if (a1107c == null || "".equals(a1107c)) {
				this.getPageElement("a1107c").setValue("" + dayA1107c);
			}
			if (a1108 == null || "".equals(a1108)) {
				this.getPageElement("a1108").setValue("" + hour);
			}
		} else {
			this.getPageElement("a1107c").setValue("");
			this.getPageElement("a1108").setValue("");
		}

		return EventRtnType.NORMAL_SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@PageEvent("setZB08Code")
	@NoRequiredValidate
	@Transaction
	public int setZB08Code(String id) throws RadowException {

		try {
			String v = HBUtil.getValueFromTab("b0131", "B01", "b0111='" + id + "'");
			if (v != null && ("1001".equals(v) || "1002".equals(v) || "1003".equals(v) || "1004".equals(v)
					|| "1005".equals(v) || "1006".equals(v) || "1007".equals(v))) {
				this.getPageElement("ChangeValue").setValue(v);
				this.getExecuteSG().addExecuteCode("var combo = Ext.getCmp('a0215a_combo');" + "combo.show();");
			} else {
				this.getExecuteSG().addExecuteCode("var combo = Ext.getCmp('a0215a_combo');" + "combo.setValue('');"
						+ "document.getElementById('a0215a').value='';" + "combo.hide();");
			}
		} catch (AppException e) {
			e.printStackTrace();
		}

		return EventRtnType.NORMAL_SUCCESS;
	}

	public void reloadCustomQuery(String tableType) {
		// �б�
		if ("1".equals(tableType)) {
			if (type == 1) {
				this.getExecuteSG().addExecuteCode("realParent.Ext.getCmp('peopleInfoGrid').getStore().reload()");// ˢ����Ա�б�

			} else {
				this.getExecuteSG().addExecuteCode("realParent.Ext.getCmp('persongrid').getStore().reload()");// ˢ����Ա�б�

			}
			this.getExecuteSG().addExecuteCode("realParent.document.getElementById('checkList').value = '';");
		}
		// С����
		if ("2".equals(tableType)) {
			this.getExecuteSG().addExecuteCode("realParent.datashow();");
		}
		// ��Ƭ
		if ("3".equals(tableType)) {
			// this.closeCueWindow("betchModifyWin");
			this.getExecuteSG().addExecuteCode("realParent.picshow();");
		}

	}

	@PageEvent("peopleInfoGrid.dogridquery")
	public int queryById(int start, int limit) throws RadowException {

		String ids = this.getPageElement("ids").getValue();
		String sql = "select a01.a0000,a01.a0101,a01.a0192a from a01,A01SEARCHTEMP b where a01.a0000 in (" + ids
				+ ") and a01.a0000 = b.a0000 and sessionid = '" + sid + "' order by b.sort";
		System.out.println("----------------" + sql);
		this.pageQueryByAsynchronous(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}

	// ��ȡ��ѡ��Ա
	@PageEvent("getCheckPeople")
	public void getCheckPeople() throws RadowException {
		StringBuffer checkIds = new StringBuffer();
		PageElement pe = this.getPageElement("peopleInfoGrid");
		if (pe != null) {
			List<HashMap<String, Object>> list = pe.getValueList();
			for (int j = 0; j < list.size(); j++) {
				HashMap<String, Object> map = list.get(j);
				Object usercheck = map.get("personcheck");
				if (usercheck.equals(true)) {
					checkIds.append(map.get("a0000").toString()).append(",");
				}
			}
		}
		if (checkIds.length() == 0) {
			this.setMainMessage("�빴ѡ��Ҫ�޸ĵ���Ա��");
			return;
		}
		String cids = checkIds.toString();
		cids = cids.substring(0, cids.length() - 1);
		this.getPageElement("checkIds").setValue(cids);
	}

	// ���ÿ�������������
	@PageEvent("a1527Save")
	public int a1527Save() throws RadowException, AppException {
		String a1527 = this.getPageElement("a1527").getValue();

		if (a1527 == null || "".equals(a1527)) {
			this.setMainMessage("������������������Ϊ�գ�");
			return EventRtnType.NORMAL_SUCCESS;
		}

		HBUtil.executeUpdate("update a15 set a1527 ='" + a1527 + "'");

		this.setMainMessage("����ɹ�");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
