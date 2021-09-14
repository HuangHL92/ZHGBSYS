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
	 * 私有属性 ids 父页传过来的数据
	 */
	public static int type = 0;
	private LogUtil applog = new LogUtil();

	public betchModifyPageModel() {

	}

	// 页面初始化
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
					// 判断是否有删除权限。c.type：机构权限类型(0：浏览，1：维护)
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
					 * 判断该人员的管理类别浏览权限---------------------------------------------------------------
					 * ---------------------------------------
					 */
					String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
					if (type == null || !type.contains("'")) {
						type = "'zz'";// 替换垃圾数据
					}
					List elist3 = sess
							.createSQLQuery(
									"select 1 from a01 where a01.a0000='" + a0000 + "' and a01.a0165 in (" + type + ")")
							.list();
					if (elist3.size() > 0) {// 无管理类别维护权限,即人员信息不可编辑
						continue;
					}
					if (elist2 == null || elist2.size() == 0) {// 维护权限
						if (elist != null && elist.size() > 0) {// 有浏览权限
							continue;
						} else {
							// 有两种情况：非现职人员，其他现职人员。非现职人员只能查看，不能编辑；其他现职人员可查看，可编辑
							if (a01.getA0163() != null && !a01.getA0163().equals("1")) { // 非现职人员
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
					this.setMainMessage("所选人员不可操作！");
					return EventRtnType.FAILD;
				}
				String ids = sb.substring(0, sb.length() - 1);
				this.getPageElement("ids").setValue(ids);
			} else {
				this.setMainMessage("请先进行人员查询！");
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
	 * 人员基本信息保存
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

			String a0197 = this.getPageElement("a0197").getValue(); // 具有两年以上基层工作经历
			String a99z101 = this.getPageElement("a99z101").getValue(); // 是否考录
			String a99z102 = this.getPageElement("a99z102").getValue(); // 录用时间

			String a99z103 = this.getPageElement("a99z103").getValue(); // 是否选调生
			String a99z104 = this.getPageElement("a99z104").getValue(); // 进入选调生时间
			/** 任现职务层次时间 */
			String A0288 = this.getPageElement("A0288").getValue();
			/** 出生年月 */
			String A0107 = this.getPageElement("A0107").getValue();
			/** 籍贯 */
			String A0111A = this.getPageElement("A0111A").getValue();
			/** 出生地 */
			String A0114A = this.getPageElement("A0114A").getValue();
			/** 成长地 */
			String A0115A = this.getPageElement("A0115A").getValue();
			/** 民族 */
			String A0117 = this.getPageElement("A0117").getValue();
			/** 健康情况 */
			String A0128 = this.getPageElement("A0128").getValue();
			/** 参加工作时间 */
			String A0134 = this.getPageElement("A0134").getValue();
			/** 入党时间文字 */
			String A0140 = this.getPageElement("A0140").getValue();
			/** 现工作单位及职务简称 */
			String A0192 = this.getPageElement("A0192").getValue();
			/** 现工作单位及职务全称 */
			String A0192A = this.getPageElement("A0192A").getValue();
			/** 最高全日制学历 */
			String QRZXL = this.getPageElement("QRZXL").getValue();
			/** 院校系专业（最高全日制学历） */
			String QRZXLXX = this.getPageElement("QRZXLXX").getValue();
			/** 最高全日制学位 */
			String QRZXW = this.getPageElement("QRZXW").getValue();
			/** 院校系专业（最高全日制学位）） */
			String QRZXWXX = this.getPageElement("QRZXWXX").getValue();
			/** 职务层次 */
			String A0221 = this.getPageElement("A0221").getValue();
			/** 职级 */
			String A0192E = this.getPageElement("A0192E").getValue();

			//是否年轻干部
			String fkbs = this.getPageElement("fkbs").getValue();
			
			
			sess = HBUtil.getHBSession();
			Connection conn = sess.connection();

			// 目前此功能最多支持500人批量修改
			String ids = this.getPageElement("checkIds").getValue();
			String[] a0000s = ids.split(",");

			int count = 0;
			// 基本信息
			String a0160 = this.getPageElement("a0160").getValue();
			// String a0104 = this.getPageElement("a0104").getValue(); //性别
			/*
			 * String a0104a = ""; if (!StringUtil.isEmpty(a0104)) { a0104a =
			 * ChangeName(a0104, "GB2261"); } else { a0104a = ""; }
			 */
			// String a0117 = this.getPageElement("a0117").getValue(); //民族
			/*
			 * String a0117a = ""; if (!StringUtil.isEmpty(a0117)) { a0117a =
			 * ChangeName(a0117, "GB3304"); } else { a0117a = ""; }
			 */
			// String a0111 = this.getPageElement("a0111").getValue(); //籍贯
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

			// 统计关系所在单位，不可选择内设机构
			String a0195 = this.getPageElement("a0195").getValue();

			B01 b01 = (B01) sess.get(B01.class, a0195);

			if (b01 != null) {

				String b0194 = b01.getB0194();// 1—法人单位；2—内设机构；3—机构分组。

				if ("2".equals(b0194)) {// 2—内设机构
					this.setMainMessage("统计关系所在单位，不可选择内设机构");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}

			String a0165 = this.getPageElement("a0165").getValue();
			// String a0192d = this.getPageElement("a0192d").getValue(); //职级
			// String a0120 = this.getPageElement("a0120").getValue(); //级别
			String a0121 = this.getPageElement("a0121").getValue();
			// String a0163 = this.getPageElement("a0163").getValue(); //人员管理状态
			// String a0128 = this.getPageElement("a0128").getValue(); //健康状态
			// String a2970 = this.getPageElement("a2970").getValue();//lzy-add
			// 判空
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

					if (!StringUtil.isEmpty(a0197)) { // 具有两年以上基层工作经历
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
					/** 任现职务层次时间 */
					if (!StringUtil.isEmpty(A0288)) {
						map.put("A0288", A0288);
						sb = sb.append("A0288 = ?,");
					}
					/** 出生年月 */
					if (!StringUtil.isEmpty(A0107)) {
						map.put("A0107", A0107);
						sb = sb.append("A0107 = ?,");
					}
					/** 籍贯 */
					if (!StringUtil.isEmpty(A0111A)) {
						map.put("A0111A", A0111A);
						sb = sb.append("A0111A = ?,");
					}
					/** 出生地 */
					if (!StringUtil.isEmpty(A0114A)) {
						map.put("A0114A", A0114A);
						sb = sb.append("A0114A = ?,");
					}
					/** 成长地 */
					if (!StringUtil.isEmpty(A0115A)) {
						map.put("A0115A", A0115A);
						sb = sb.append("A0115A = ?,");
					}
					/** 民族 */
					if (!StringUtil.isEmpty(A0117)) {
						map.put("A0117", A0117);
						sb = sb.append("A0117 = ?,");
					}
					/** 健康情况 */
					if (!StringUtil.isEmpty(A0128)) {
						map.put("A0128", A0128);
						sb = sb.append("A0128 = ?,");
					}
					/** 参加工作时间 */
					if (!StringUtil.isEmpty(A0134)) {
						map.put("A0134", A0134);
						sb = sb.append("A0134 = ?,");
					}
					/** 入党时间文字 */
					if (!StringUtil.isEmpty(A0140)) {
						map.put("A0140", A0140);
						sb = sb.append("A0140 = ?,");
					}
					/** 现工作单位及职务简称 */
					if (!StringUtil.isEmpty(A0192)) {
						map.put("A0192", A0192);
						sb = sb.append("A0192 = ?,");
					}
					/** 现工作单位及职务全称 */
					if (!StringUtil.isEmpty(A0192A)) {
						map.put("A0192A", A0192A);
						sb = sb.append("A0192A = ?,");
					}
					/** 最高全日制学历 */
					if (!StringUtil.isEmpty(QRZXL)) {
						map.put("QRZXL", QRZXL);
						sb = sb.append("QRZXL = ?,");
					}
					/** 院校系专业（最高全日制学历） */
					if (!StringUtil.isEmpty(QRZXLXX)) {
						map.put("QRZXLXX", QRZXLXX);
						sb = sb.append("QRZXLXX = ?,");
					}
					/** 最高全日制学位 */
					if (!StringUtil.isEmpty(QRZXW)) {
						map.put("QRZXW", QRZXW);
						sb = sb.append("QRZXW = ?,");
					}
					/** 院校系专业（最高全日制学位）） */
					if (!StringUtil.isEmpty(QRZXWXX)) {
						map.put("QRZXWXX", QRZXWXX);
						sb = sb.append("QRZXWXX = ?,");
					}
					/** 现职务层次 */
					if (!StringUtil.isEmpty(A0221)) {
						map.put("A0221", A0221);
						sb = sb.append("A0221 = ?,");
						// 更新职务层次表
						//this.setNextEventName("UpdateA05");
						//this.request.setAttribute("UpdateA05Flag", "true");
						// UpdateA05(A0221,a0000s,true);
						//if (!com.insigma.siis.local.util.StringUtil.isEmpty(this.getMainMessage())) {
						//	return EventRtnType.NORMAL_SUCCESS;
						//}
					}
					/** 职级 */
					if (!StringUtil.isEmpty(A0192E)) {
						map.put("A0192E", A0192E);
						sb = sb.append("A0192E = ?,");
						// 更新职务层次表
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

						// 通过a0000查询出“补充信息集”A99Z1对象,对A99Z1信息进行处理
						A99Z1 a99Z1 = new A99Z1();
						this.copyElementsValueToObj(a99Z1, this);

						// 通过a000去查找a99z1
						String sql = "select A99Z100 from a99z1 where A0000 = '" + a0000s[i] + "'";

						List A99Z100S = sess.createSQLQuery(sql).list();

						String a99Z100 = "";
						if (A99Z100S.size() > 0) {
							a99Z100 = A99Z100S.get(0).toString();
						}

						a99Z1.setA99Z100(a99Z100);

						// 判断录用时间：与出生日期进行比较，一般应大于18周岁。
						// String a0107 = a01.getA0107();//出生年月
						/*
						 * String a99z102 = a99Z1.getA99z102();//录用时间
						 * if(a0107!=null&&!"".equals(a0107)&&a99z102!=null&&!"".equals(a99z102)){ int
						 * age = getAgeNew(a99z102,a0107); if(age<18){
						 * this.setMainMessage("录用时间与出生日期进行比较，应大于18周岁！"); return EventRtnType.FAILD; } }
						 */

						// 判断进入选调生时间：与出生日期进行比较，一般应大于18周岁。

						/*
						 * String a99z104 = a99Z1.getA99z104();//进入选调生时间
						 * if(a0107!=null&&!"".equals(a0107)&&a99z104!=null&&!"".equals(a99z104)){ int
						 * age = getAgeNew(a99z104,a0107); if(age<18){
						 * this.setMainMessage("进入选调生时间与出生日期进行比较，应大于18周岁！"); return EventRtnType.FAILD;
						 * } }
						 */
						A01 a01_old = (A01) sess.get(A01.class, a0000s[i]);

						a99Z1.setA0000(a0000s[i]);
						A99Z1 a99Z1_old = null;
						if ("".equals(a99Z1.getA99Z100())) {
							a99Z1.setA99Z100(null);
							a99Z1_old = new A99Z1();

							String id = UUID.randomUUID().toString();// 用来生成数据库的主键id
							// a99Z1_old.setA99Z100(id);
							a99Z1_old.setA0000(a0000s[i]);
							// applog.createLog("3531", "A99Z1", a0000s[i], a01_old.getA0101(), "新增记录", new
							// Map2Temp().getLogInfo(a99Z1_old,a99Z1));

							applog.createLogNew("3A99Z12", "选调生、考录信息新增", "选调生、考录信息集", a0000s[i], a01_old.getA0101(),
									new Map2Temp().getLogInfo(new A99Z1(), a99Z1));
						} else {
							a99Z1_old = (A99Z1) sess.get(A99Z1.class, a99Z1.getA99Z100());
							// applog.createLog("3532", "A53", a0000s[i], a01_old.getA0101(), "修改记录", new
							// Map2Temp().getLogInfo(a99Z1_old,a99Z1));
							applog.createLogNew("3A99Z12", "选调生、考录信息修改", "选调生、考录信息集", a0000s[i], a01_old.getA0101(),
									new Map2Temp().getLogInfo(a99Z1_old, a99Z1));
						}

						// 赋值
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

						// 赋值最新的信息
						if (!StringUtil.isEmpty(a0160)) { // 人员类别
							a01.setA0160(a0160);
						}

						if (!StringUtil.isEmpty(a0195)) { // 统计关系所在单位
							a01.setA0195(a0195);
						}
						if (!StringUtil.isEmpty(a0165)) { // 管理类别
							a01.setA0165(a0165);
						}

						if (!StringUtil.isEmpty(a0121)) { // 编制类型
							a01.setA0121(a0121);
						}

						if (!StringUtil.isEmpty(a0197)) { // 具有两年以上基层工作经历
							a01.setA0197(a0197);
						}
						
						if (!StringUtil.isEmpty(fkbs)) { // 是否年轻干部
							a01.setFkbs(fkbs);
						}

						// 记录日志
						applog.createLog("32", "A01", a01.getA0000(), a01.getA0101(), "修改记录",
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
					// 记录日志
					/*
					 * List list = new ArrayList();
					 * 
					 * if (!StringUtil.isEmpty(a0160)) { String[] arr = { "A0160", "", "", "人员类别" };
					 * list.add(arr); }
					 * 
					 * if (!StringUtil.isEmpty(a0195)) { String[] arr = { "A0195", "", "",
					 * "统计关系所在单位" }; list.add(arr); } if (!StringUtil.isEmpty(a0165)) { String[] arr
					 * = { "A0165", "", "", "管理类别" }; list.add(arr); }
					 * 
					 * if (!StringUtil.isEmpty(a0121)) { String[] arr = { "A0121", "", "", "编制类型" };
					 * list.add(arr); }
					 * 
					 * if (!StringUtil.isEmpty(a0197)) { //具有两年以上基层工作经历 String[] arr = { "a0197",
					 * "", "", "具有两年以上基层工作经历" }; list.add(arr); }
					 */

					// applog.createLog("3704", "A01", "", "", "人员基本信息批量修改", list);

				}
			} else {
				this.setMainMessage("没有要修改的项");
				return EventRtnType.NORMAL_SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.setMainMessage("保存成功");
		String tableType = this.getPageElement("type").getValue();
		reloadCustomQuery(tableType);

		return EventRtnType.NORMAL_SUCCESS;
	}

	@Transaction
	@PageEvent("save")
	public int UpdateA05() throws RadowException, AppException {
		// 目前此功能最多支持500人批量修改
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
				/** 现职务层次 */
				if (!StringUtil.isEmpty(A0221)) {
					// 更新职务层次表
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
		//如果职务职级信息都有,直接进入保存人员信息,小于10人提示人名,大于10人 提示数量  确认无信息人员是否修改
		if(ZJerrornum==0&&ZWCCerrornum==0) {
			this.setNextEventName("saveA01");
		}else if(ZWCCerrornum==0&&ZJerrornum>0) {
			if(ZJerrornum<=10) {
				ZJINFO=ZJINFO.substring(0, ZJINFO.length()-1);
				ZJINFO+="没有职级数据，是否修改?";
				this.getExecuteSG().addExecuteCode("saveZWZJConfirm('"+ZJINFO+"')");
			}else {
				this.getExecuteSG().addExecuteCode("saveZWZJConfirm('"+ZJerrornum+"人没有职级数据是否修改')");
			}
			
		}else if(ZWCCerrornum>0&&ZJerrornum==0) {
			if(ZWCCerrornum<=10) {
				ZWCCINFO=ZWCCINFO.substring(0, ZWCCINFO.length()-1);
				ZWCCINFO+="没有职务层次数据，是否修改?";
				this.getExecuteSG().addExecuteCode("saveZWZJConfirm('"+ZWCCINFO+"')");
			}else {
				this.getExecuteSG().addExecuteCode("saveZWZJConfirm('"+ZWCCerrornum+"人没有职务层次数据是否修改')");
			}
		}else if(ZWCCerrornum>0&&ZJerrornum>0) {
			String INFO="";
			if(ZWCCerrornum<=10) {
				ZWCCINFO=ZWCCINFO.substring(0, ZWCCINFO.length()-1);
				INFO+=ZWCCINFO+"没有职务层次数据，";
			}else {
				INFO+=ZWCCerrornum+"人没有职务层次数据，";
			}
			if(ZJerrornum<=10) {
				ZJINFO=ZJINFO.substring(0, ZJINFO.length()-1);
				INFO+=ZJINFO+"没有职级数据，是否修改";
			}else {
				INFO+=ZJerrornum+"人没有职级数据，是否修改";
			}
			this.getExecuteSG().addExecuteCode("saveZWZJConfirm('"+INFO+"')");
		}

		return EventRtnType.NORMAL_SUCCESS;
	}

	//确认后保存空职务职级人员的信息
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
					//插入职级信息
					String sql="insert into a05 (a0000,a0500,a0531,a0501b,a0524,a0525) values ('"+ZJa0000s[i]+"',sys_guid(),'1','"+A0192E+"','1','1')";
					sess.createSQLQuery(sql).executeUpdate();
					sess.flush();
				}

			}
			for(int i=0;i<ZWCCa0000s.length;i++) {
				/** 现职务层次 */
				if (!StringUtil.isEmpty(A0221)) {
					//插入职务层次信息
					String sql="insert into a05 (a0000,a0500,a0531,a0501b,a0524,a0525,a0504) values ('"+ZJa0000s[i]+"',sys_guid(),'1','"+A0192E+"','1','1','"+A0288+"')";
					sess.createSQLQuery(sql).executeUpdate();
					sess.flush();
				}
			}
	
		this.setNextEventName("saveA01");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
	
	// 住址通讯保存 id生成方式为assigned
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
					// 记录日志
					List list = new ArrayList();

					if (!StringUtil.isEmpty(a3701)) {
						String[] arr = { "A3701", "", "", "办公地址" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a3707a)) {
						String[] arr = { "A3707a", "", "", "办公电话" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a3707e)) {
						String[] arr = { "A3707e", "", "", "秘书电话" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a3711)) {
						String[] arr = { "A3711", "", "", "家庭住址" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a3714)) {
						String[] arr = { "A3714", "", "", "住址邮编" };
						list.add(arr);
					}
					applog.createLog("3706", "A37", "", "", "人员住址通讯批量修改", list);
				}

			} else {
				this.setMainMessage("没有要修改的项");
				return EventRtnType.NORMAL_SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！异常信息：" + e.getMessage());
			return EventRtnType.FAILD;
		}
		this.setMainMessage("保存成功");
		String tableType = this.getPageElement("type").getValue();
		reloadCustomQuery(tableType);
		return EventRtnType.NORMAL_SUCCESS;
	}

	// 职务
	@PageEvent("save4")
	@Transaction
	@Synchronous(true)
	public int save4() throws RadowException, AppException {
		HBSession sess = HBUtil.getHBSession();
		Connection conn = sess.connection();
		String ids = this.getPageElement("checkIds").getValue();
		String[] a0000s = ids.split(",");
		try {
			String a0222 = this.getPageElement("a0222").getValue();// 岗位类别
			String a0201b = this.getPageElement("a0201b").getValue();// 任职机构编码
			String a0215a = this.getPageElement("a0215a").getValue();// 职务名称
			String a0216a = this.getPageElement("a0216a").getValue();// 职务简称
			String a0201e = this.getPageElement("a0201e").getValue();// 成员类别
			String a0201d = this.getPageElement("a0201d").getValue();// 班子成员标识
			String a0221 = this.getPageElement("a0221").getValue();// 职务层次
			String a0229 = this.getPageElement("a0229").getValue();// 分管（从事）工作
			String a0247 = this.getPageElement("a0247").getValue();// 选拔任用方式
			String a0251 = this.getPageElement("a0251").getValue();// 任职变动类型
			String a0245 = this.getPageElement("a0245").getValue();// 决定或批准任职的文号
			String a0219 = this.getPageElement("a0219").getValue();// 职务类别
			String a0243 = this.getPageElement("a0243").getValue();// 决定或批准任职的时间
			String a0288 = this.getPageElement("a0288").getValue();// 任职务层次时间
			String a0255 = "1";
			if (!StringUtil.isEmpty(a0222) || !StringUtil.isEmpty(a0201b) || !StringUtil.isEmpty(a0215a)
					|| !StringUtil.isEmpty(a0201e) || !StringUtil.isEmpty(a0201d) || !StringUtil.isEmpty(a0221)
					|| !StringUtil.isEmpty(a0229) || !StringUtil.isEmpty(a0247) || !StringUtil.isEmpty(a0251)
					|| !StringUtil.isEmpty(a0245) || !StringUtil.isEmpty(a0219) || !StringUtil.isEmpty(a0243)
					|| !StringUtil.isEmpty(a0288) || !StringUtil.isEmpty(a0216a)) {
				if (StringUtil.isEmpty(a0222)) {
					this.setMainMessage("岗位类别不能为空！");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if (StringUtil.isEmpty(a0201b)) {
					this.setMainMessage("任职机构/工作机构 不能为空！");
					return EventRtnType.NORMAL_SUCCESS;
				}
				B01 b01 = (B01) sess.get(B01.class, a0201b);
				String a0201a = b01.getB0101();
				String a0201c = b01.getB0104();
				// 将机构校验标识设为0
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
					// 记录日志
					List list = new ArrayList();

					if (!StringUtil.isEmpty(a0222)) {
						String[] arr = { "a0222", "", "", "岗位类别" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0201b)) {
						String[] arr = { "a0201b", "", "", "任职机构编码 " };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0215a)) {
						String[] arr = { "a0215a", "", "", "职务名称" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0201e)) {
						String[] arr = { "a0201e", "", "", "成员类别" };
						list.add(arr);
					}

					if (!StringUtil.isEmpty(a0201d)) {
						String[] arr = { "a0201d", "", "", "班子成员标识" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0221)) {
						String[] arr = { "a0221", "", "", "职务层次" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0229)) {
						String[] arr = { "a0229", "", "", "分管（从事）工作" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0247)) {
						String[] arr = { "a0247", "", "", "选拔任用方式" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0251)) {
						String[] arr = { "a0251", "", "", "任职变动类型" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0245)) {
						String[] arr = { "a0245", "", "", "决定或批准任职的文号" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0219)) {
						String[] arr = { "a0219", "", "", "职务类别" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0243)) {
						String[] arr = { "a0243", "", "", "决定或批准任职的时间" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0288)) {
						String[] arr = { "a0288", "", "", "任职务层次时间" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a0216a)) {
						String[] arr = { "a0216a", "", "", "职务简称" };
						list.add(arr);
					}
					applog.createLog("3707", "A02", "", "", "人员职务信息批量增加", list);
				}

			} else {
				this.setMainMessage("没有要修改的项");
				return EventRtnType.NORMAL_SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.setMainMessage("保存成功");
		String tableType = this.getPageElement("type").getValue();
		reloadCustomQuery(tableType);
		return EventRtnType.NORMAL_SUCCESS;
	}

	// 考核信息
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
			String a1151Type = this.getPageElement("a1151Type").getValue(); // 修改方式
			String a1521s = this.getPageElement("a1521").getValue(); // 考核(开始)年度
			String a1517 = this.getPageElement("a1517").getValue(); // 考核结果
			String a15z101 = this.getPageElement("a15z101").getValue(); // 年度考核描述
			int count = 0;
			int count1 = 0;
			if (!StringUtil.isEmpty(a1151Type) || !StringUtil.isEmpty(a1521s) || !StringUtil.isEmpty(a1517)
					|| !StringUtil.isEmpty(a15z101)) {
				if (StringUtil.isEmpty(a1151Type)) {
					this.setMainMessage("请选择考核信息修改方式！");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if (StringUtil.isEmpty(a1521s) && a1151Type.equals("0")) {
					this.setMainMessage("请填写考核（开始）年度！");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if (StringUtil.isEmpty(a1517) && a1151Type.equals("0")) {
					this.setMainMessage("请填写考核结果！");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if (StringUtil.isEmpty(a15z101) && a1151Type.equals("1")) {
					this.setMainMessage("请填写年度考核项！");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if (a1151Type.equals("1") && !StringUtil.isEmpty(a1517)) {
					this.setMainMessage("修改方式为直接修改文字(不与列表关联)，无需填写考核结果");
					return EventRtnType.NORMAL_SUCCESS;
				}
				
				String a1527 = this.getPageElement("a1527").getValue();

					if (a1527 == null || "".equals(a1527)) {
					this.setMainMessage("考核年度输出个数不能为空！");
					return EventRtnType.NORMAL_SUCCESS;
				}

				
				
				String[] years = a1521s.split(",");
				//String a1527 = years.length + "";
				if (a0000s.length > 0) {
					if (!StringUtil.isEmpty(a15z101)) { // 修改方式: 直接修改文字(不与列表关联)
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
					} else { // 修改方式: 按年度修改(自动更新文字)
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
							// 判断输出几年考核
							String sql = "select a1527 from A15 where a0000 = '" + a0000s[i] + "'";
							List lista36 = sess.createQuery(sql).list();

							// 如果lista36中有元素为空，则会空指针异常，需要判断
							Object a1527Ob = lista36.get(0);

							int a1527F = 3; // 选择年度个数，默认为3
							if (a1527Ob != null && !a1527Ob.toString().equals("")) {
								a1527F = Integer.parseInt(lista36.get(0).toString());
							}

							ps4.setInt(2, a1527F);

							ResultSet rs = ps4.executeQuery();
							while (rs.next()) {
								String a1521 = rs.getString(1);// 考核年度
								String a1517s = rs.getString(2);// 考核结果
								String a1517Name = HBUtil.getCodeName("ZB18", a1517s);
								// desc.append(a1521 + "年年度考核" + a1517Name + "；");
								if(a1517Name.equals("不定等次")) {
									desc.insert(0,a1521+"年年度考核"+a1517Name+"；");
								}else {
									desc.insert(0,a1521+"年年度考核"+a1517Name+"；");
								}
							}
							if (rs != null) {
								rs.close();
							}
							if (desc.length() > 0) {
								desc.replace(desc.length() - 1, desc.length(), "。");
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
					// 记录日志
					List list = new ArrayList();

					if (!StringUtil.isEmpty(a1521s)) {
						String[] arr = { "a1521", "", "", "考核年度" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a1517)) {
						String[] arr = { "a1517", "", "", "考核结果" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a15z101)) {
						String[] arr = { "a15z101", "", "", "年度考核结果综述" };
						list.add(arr);
					}
					applog.createLog("3705", "A15", "", "", "人员考核信息批量修改", list);
				}

			} else {
				this.setMainMessage("没有要修改的项");
				return EventRtnType.NORMAL_SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.setMainMessage("保存成功");
		String tableType = this.getPageElement("type").getValue();
		reloadCustomQuery(tableType);
		return EventRtnType.NORMAL_SUCCESS;
	}

	// 奖惩
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
			String a1415 = this.getPageElement("a1415").getValue(); // 受奖惩时职务层次

			if (!StringUtil.isEmpty(a1404b) || !StringUtil.isEmpty(a1404a) || !StringUtil.isEmpty(a1414)
					|| !StringUtil.isEmpty(a1428) || !StringUtil.isEmpty(a1407) || !StringUtil.isEmpty(a1424)
					|| !StringUtil.isEmpty(a1411a) || !StringUtil.isEmpty(a1415)) {
				if (StringUtil.isEmpty(a1404b)) {
					this.setMainMessage("请填写奖惩名称代码！");
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

					if (!StringUtil.isEmpty(a1404b)) { // 奖惩代码
						a14.setA1404b(a1404b);
					}
					if (!StringUtil.isEmpty(a1404a)) { // 奖惩名称

						a14.setA1404a(a1404a);
					}
					if (!StringUtil.isEmpty(a1414)) { // 批准机关级别
						a14.setA1414(a1414);
					}
					if (!StringUtil.isEmpty(a1415)) { // 受奖惩时职务层次
						a14.setA1415(a1415);
					}
					if (!StringUtil.isEmpty(a1428)) { // 批准机关性质
						a14.setA1428(a1428);
					}
					if (!StringUtil.isEmpty(a1407)) { // 奖惩批准时间
						a14.setA1407(a1407);
					}
					if (!StringUtil.isEmpty(a1424)) { // 奖惩撤销时间
						a14.setA1424(a1424);
					}
					if (!StringUtil.isEmpty(a1411a)) { // 批准机关
						a14.setA1411a(a1411a);
					}

					applog.createLog("3141", "A14", a01_old.getA0000(), a01_old.getA0101(), "新增记录",
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
						String a1411as = rs.getString(1);// 批准机关
						String a1404bs = rs.getString(2);// 奖惩代码
						String a1404as = rs.getString(3);// 奖惩名称
						if (a1404bs.startsWith("01")) {// 奖
							desc2.append("经" + (a1411as != null ? a1411as : "") + "批准，").append(a1404as + ";");
						} else {// 惩
							desc2.append("经" + (a1411as != null ? a1411as : "") + "批准，").append("受" + a1404as + "处分;");
						}
					}
					if (rs != null) {
						rs.close();
					}

					if (desc2.length() > 0) {
						desc2.replace(desc2.length() - 1, desc2.length(), "。");
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
				// 记录日志
				/*
				 * List list = new ArrayList(); if (!StringUtil.isEmpty(a1404b)) { String[] arr
				 * = { "a1404b", "", "", "奖惩代码" }; list.add(arr); } if
				 * (!StringUtil.isEmpty(a1404a)) { String[] arr = { "a1404a", "", "", "奖惩名称" };
				 * list.add(arr); } if (!StringUtil.isEmpty(a1414)) { String[] arr = { "a1414",
				 * "", "", "批准机关级别" }; list.add(arr); } if (!StringUtil.isEmpty(a1415)) {
				 * String[] arr = { "a1415", "", "", "受奖惩时职务层次" }; list.add(arr); } if
				 * (!StringUtil.isEmpty(a1428)) { String[] arr = { "a1428", "", "", "批准机关性质" };
				 * list.add(arr); } if (!StringUtil.isEmpty(a1407)) { String[] arr = { "a1407",
				 * "", "", "奖惩批准时间" }; list.add(arr); } if (!StringUtil.isEmpty(a1424)) {
				 * String[] arr = { "a1424", "", "", "奖惩撤销时间" }; list.add(arr); } if
				 * (!StringUtil.isEmpty(a1411a)) { String[] arr = { "a1411a", "", "", "批准机关" };
				 * list.add(arr); }
				 */
				// applog.createLog("3708", "A14", "", "", "人员奖惩信息批量增加", list);
			} else {
				this.setMainMessage("没有要修改的项");
				return EventRtnType.NORMAL_SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.setMainMessage("保存成功");
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
			String a2907 = this.getPageElement("a2907").getValue();// 进入本单位日期
			String a2911 = this.getPageElement("a2911").getValue();// 进入本单位变动类别
			String a2941 = this.getPageElement("a2941").getValue();// 在原单位职务
			String a2944s = this.getPageElement("a2944s_combo").getValue();// 在原单位职务层次
			String a2921a = this.getPageElement("a2921a").getValue();// 进入本单位前工作单位名称
			String a2949 = this.getPageElement("a2949").getValue();// 公务员登记时间
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
					// 记录日志
					List list = new ArrayList();

					if (!StringUtil.isEmpty(a2907)) {
						String[] arr = { "A2907", "", "", "进入本单位日期" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a2911)) {
						String[] arr = { "A2911", "", "", "进入本单位变动类别" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a2941)) {
						String[] arr = { "A2941", "", "", "在原单位职务" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a2944s)) {
						String[] arr = { "A2944", "", "", "在原单位职务层次" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a2921a)) {
						String[] arr = { "A2921a", "", "", "进入本单位前工作单位名称" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(a2949)) {
						String[] arr = { "A2949", "", "", "公务员登记时间" };
						list.add(arr);
					}
					applog.createLog("3706", "A29", "", "", "进入管理批量修改", list);
				}

			} else {
				this.setMainMessage("没有要修改的项");
				return EventRtnType.NORMAL_SUCCESS;
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.setMainMessage("保存成功");
		String tableType = this.getPageElement("type").getValue();
		reloadCustomQuery(tableType);

		return EventRtnType.NORMAL_SUCCESS;
	}

	/*
	 * // 培训班
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
	 * += "01"; } // 计算培训有几月几天。 int days = DateUtil.getDaysBetween(
	 * DateUtil.stringToDate(a1107, "yyyymmdd"), DateUtil.stringToDate(a1111,
	 * "yyyymmdd")); int mounthA1107a = days / 31;// 月 int dayA1107b = days % 31;//
	 * 天 String uuid = UUID.randomUUID().toString(); String uuid1 =
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
	 * null) { ps.close(); } if (ps1 != null) { ps1.close(); } // 记录日志 List list =
	 * new ArrayList(); if (!StringUtil.isEmpty(a1131)) { String[] arr = { "a1131",
	 * "", "", "培训名称" }; list.add(arr); } if (!StringUtil.isEmpty(a1101)) { String[]
	 * arr = { "a1101", "", "", "培训类别" }; list.add(arr); } if
	 * (!StringUtil.isEmpty(a1107)) { String[] arr = { "a1107", "", "", "培训开始日期" };
	 * list.add(arr); } if (!StringUtil.isEmpty(a1111)) { String[] arr = { "a1111",
	 * "", "", "培训结束日期" }; list.add(arr); } if (!StringUtil.isEmpty(a1107c)) {
	 * String[] arr = { "a1107c", "", "", "培训时长（天）" }; list.add(arr); } if
	 * (!StringUtil.isEmpty(a1108)) { String[] arr = { "a1108", "", "", "学时" };
	 * list.add(arr); } if (!StringUtil.isEmpty(a1114)) { String[] arr = { "a1114",
	 * "", "", "培训主办单位" }; list.add(arr); } if (!StringUtil.isEmpty(a1121a)) {
	 * String[] arr = { "a1121a", "", "", "培训机构名称" }; list.add(arr); } if
	 * (!StringUtil.isEmpty(a1127)) { String[] arr = { "a1127", "", "", "培训机构类别" };
	 * list.add(arr); } if (!StringUtil.isEmpty(a1104)) { String[] arr = { "a1104",
	 * "", "", "培训离岗状态" }; list.add(arr); } if (!StringUtil.isEmpty(a1151)) {
	 * String[] arr = { "a1151", "", "", "出国（出境）培训标识" }; list.add(arr); }
	 * applog.createLog("3709", "A11", "", "", "人员培训班信息批量增加", list);
	 * 
	 * } else { this.setMainMessage("没有要修改的项"); return EventRtnType.NORMAL_SUCCESS;
	 * } } catch (Exception e) { e.printStackTrace(); this.setMainMessage("保存失败！");
	 * return EventRtnType.FAILD; } this.setMainMessage("保存成功"); String tableType =
	 * this.getPageElement("type").getValue(); reloadCustomQuery(tableType); return
	 * EventRtnType.NORMAL_SUCCESS; }
	 */

	@PageEvent("count")
	public int count() throws RadowException, AppException {
		String a1107 = this.getPageElement("a1107").getValue();// 培训开始时间
		String a1111 = this.getPageElement("a1111").getValue();// 培训结束时间
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
				this.setMainMessage("开始时间不能大于结束时间");
				return EventRtnType.NORMAL_SUCCESS;
			}
			int days = DateUtil.getDaysBetween(DateUtil.stringToDate(a1107, "yyyymmdd"),
					DateUtil.stringToDate(a1111, "yyyymmdd"));
			int dayA1107c = days;// 总天数
			int hour = days * 8;// 学时
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
		String a1107 = this.getPageElement("a1107").getValue();// 培训开始时间
		String a1111 = this.getPageElement("a1111").getValue();// 培训结束时间
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
				this.setMainMessage("开始时间不能大于结束时间");
				return EventRtnType.NORMAL_SUCCESS;
			}
			int days = DateUtil.getDaysBetween(DateUtil.stringToDate(a1107, "yyyymmdd"),
					DateUtil.stringToDate(a1111, "yyyymmdd"));
			int dayA1107c = days;// 总天数
			int hour = days * 8;// 学时
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
		// 列表
		if ("1".equals(tableType)) {
			if (type == 1) {
				this.getExecuteSG().addExecuteCode("realParent.Ext.getCmp('peopleInfoGrid').getStore().reload()");// 刷新人员列表

			} else {
				this.getExecuteSG().addExecuteCode("realParent.Ext.getCmp('persongrid').getStore().reload()");// 刷新人员列表

			}
			this.getExecuteSG().addExecuteCode("realParent.document.getElementById('checkList').value = '';");
		}
		// 小资料
		if ("2".equals(tableType)) {
			this.getExecuteSG().addExecuteCode("realParent.datashow();");
		}
		// 照片
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

	// 获取勾选人员
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
			this.setMainMessage("请勾选需要修改的人员！");
			return;
		}
		String cids = checkIds.toString();
		cids = cids.substring(0, cids.length() - 1);
		this.getPageElement("checkIds").setValue(cids);
	}

	// 设置考核年度输出个数
	@PageEvent("a1527Save")
	public int a1527Save() throws RadowException, AppException {
		String a1527 = this.getPageElement("a1527").getValue();

		if (a1527 == null || "".equals(a1527)) {
			this.setMainMessage("考核年度输出个数不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}

		HBUtil.executeUpdate("update a15 set a1527 ='" + a1527 + "'");

		this.setMainMessage("保存成功");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
