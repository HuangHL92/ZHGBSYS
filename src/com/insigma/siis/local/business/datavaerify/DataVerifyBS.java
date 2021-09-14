package com.insigma.siis.local.business.datavaerify;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Query;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.util.DateUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.entity.VerifyProcess;
import com.insigma.siis.local.business.entity.VerifyRule;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.comm.BusinessBSSupport;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.ImpmodelThread;
import com.insigma.siis.local.pagemodel.sysmanager.verificationschemeconf.VerificationSchemeConfPageModel;
import com.insigma.siis.local.util.LargetaskLog;

import net.sf.json.JSONArray;

public class DataVerifyBS extends BusinessBSSupport {

	/**
	 * ��ȡ��ЧУ�鷽�������Ȼ���Ĭ�Ϸ���
	 * 
	 * @return
	 */
	public static String getBaseVerifyScheme() {
		HBSession sess = HBUtil.getHBSession();
		List list = sess.createSQLQuery("SELECT vsc001 from  verify_scheme a WHERE a.vsc003 = '1' and  a.vsc007='1'  ")
				.list();
		if (list == null || list.size() == 0 || list.get(0) == null) {
			list = sess.createSQLQuery("SELECT vsc001 from  verify_scheme a WHERE  a.vsc003='1'  ").list();
		}
		if (list == null || list.size() < 1) {
			return "";
		}
		return (String) list.get(0);
	}

	/**
	 * Ĭ��ȡУ�鷽��
	 * 
	 * @param imprecordid
	 * @return
	 * @throws SQLException
	 * @throws AppException
	 */
	public static boolean dataVerifyByImprecordidNew(String imprecordid) throws Exception {
		String vsc001 = getBaseVerifyScheme();
		if (StringUtil.isEmpty(vsc001)) {
			throw new AppException("δ���ҵ���ЧУ�鷽����");
		}
		return dataVerifyByImprecordidVsc001(imprecordid, vsc001);
	}

	/**
	 * ͨ���洢����У�鵼����Ϣ
	 * 
	 * @param imprecordid
	 * @param vsc001
	 * @return
	 * @throws AppException
	 */
	public static boolean dataVerifyProcedureByImprecordidVsc001(String imprecordid, String vsc001)
			throws AppException {

		boolean flag = true; // Ĭ��Ϊtrue ͨ��

		int rFlag = 0; // -1 У������쳣 ��0 У��δͨ���� 1 У��ͨ��
		String errorCode = "";
		String msg = "";

		CommonQueryBS.systemOut("     ------>" + imprecordid + "@" + vsc001);
		CommonQueryBS.systemOut("У�鿪ʼ------>" + DateUtil.dateToString(new Date(), "yyyyMMdd hh:mm:ss"));
		HBSession hbsess = HBUtil.getHBSession();
		hbsess.flush();
		Connection conn = hbsess.connection();
		try {
			CallableStatement cstmt = conn.prepareCall("{call Data_Verify_By_Id_Vsc001(?,?,?,?,?)}");
			cstmt.setString(1, imprecordid);
			cstmt.setString(2, vsc001);
			cstmt.registerOutParameter(3, Types.INTEGER);
			cstmt.registerOutParameter(4, Types.VARCHAR);
			cstmt.registerOutParameter(5, Types.VARCHAR);
			cstmt.execute();
			rFlag = cstmt.getInt(3);
			errorCode = cstmt.getString(4);
			msg = cstmt.getString(5);
			CommonQueryBS.systemOut("    У����Ϣ-->" + msg);
			if (cstmt != null)
				cstmt.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("���ݿ⴦���쳣��", e);
		}
		CommonQueryBS.systemOut("У�����------>" + DateUtil.dateToString(new Date(), "yyyyMMdd hh:mm:ss"));
		if (rFlag < 0) {
			throw new AppException(msg);
		}

		if (rFlag == 0) {
			flag = false;
		} else if (rFlag == 1) {
			flag = true;
		}
		return flag;
	}

	/**
	 * ͨ���洢����У�鵼����Ϣ
	 * 
	 * @param imprecordid
	 * @param vsc001
	 * @return
	 * @throws AppException
	 */
	public static boolean dataVerifyProcedureByB0111Vsc001(String b0111, String vsc001) throws AppException {

		boolean flag = true; // Ĭ��Ϊtrue ͨ��

		int rFlag = 0; // -1 У������쳣 ��0 У��δͨ���� 1 У��ͨ��
		String errorCode = "";
		String msg = "";

		CommonQueryBS.systemOut("     ------>" + b0111 + "@" + vsc001);
		CommonQueryBS.systemOut("У�鿪ʼ------>" + DateUtil.dateToString(new Date(), "yyyyMMdd hh:mm:ss"));
		HBSession hbsess = HBUtil.getHBSession();
		hbsess.flush();
		Connection conn = hbsess.connection();
		try {
			CallableStatement cstmt = conn.prepareCall("{call Data_Verify_By_B0111_Vsc001(?,?,?,?,?)}");
			cstmt.setString(1, b0111);
			cstmt.setString(2, vsc001);
			cstmt.registerOutParameter(3, Types.INTEGER);
			cstmt.registerOutParameter(4, Types.VARCHAR);
			cstmt.registerOutParameter(5, Types.VARCHAR);
			cstmt.execute();
			rFlag = cstmt.getInt(3);
			errorCode = cstmt.getString(4);
			msg = cstmt.getString(5);
			CommonQueryBS.systemOut("    У����Ϣ-->" + msg);
			if (cstmt != null)
				cstmt.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("���ݿ⴦���쳣��", e);
		}
		CommonQueryBS.systemOut("У�����------>" + DateUtil.dateToString(new Date(), "yyyyMMdd hh:mm:ss"));
		if (rFlag < 0) {
			throw new AppException(msg);
		}

		if (rFlag == 0) {
			flag = false;
		} else if (rFlag == 1) {
			flag = true;
		}
		return flag;
	}

	public static boolean dataVerifyByImprecordidVsc001(String imprecordid, String vsc001) throws Exception {
		return dataVerifyByImprecordidVsc001(imprecordid, vsc001, null);
	}

	/**
	 * ���ݵ�������ID,У�鷽��ID У������ε�����Ϣ
	 * 
	 * 0.ɾ��֮ǰ������У�����ɵ����� 1.��ѯУ�鷽������ҪУ��ı���Ϣ 2.��ѯ��������������ҪУ�������
	 * 3.ѭ����Ա����Ϣ,ѭ��У�����VerifyRule�����޸�У����Ϣ��д��У�������Ϣ��VerifyError,VerifyErrorDetail��
	 * 4.ɾ�� û�ж�Ӧ verify_error_datail��Ϣ��verify_error�� 5.��д�����¼���Ƿ�У�飬У�鷽��
	 * 
	 * @param imprecordid ��������ID
	 * @param vsc001      У�鷽��ID
	 * @throws SQLException
	 * @throws AppException
	 */
	public static boolean dataVerifyByImprecordidVsc001(String imprecordid, String vsc001, VerifyProcess vp)
			throws Exception {

		// return dataVerifyProcedureByImprecordidVsc001(imprecordid,vsc001); //�洢����

		boolean flag = true; // Ĭ��Ϊtrue ͨ��
		boolean vsc001NullFlag = false; // vsc001 �Ƿ�Ϊ�ձ��
		String vel001Bf = " sys_guid()  "; // ����VEL001������UUID���ĺ���
		String vel005Bf = " '2' "; // VEL005ҵ������:: 1-����У�飨�����¼���������2-����У��

		if (DBUtil.getDBType() == DBType.MYSQL) {
			vel001Bf = " UUID() ";
		}

		HBSession sess = HBUtil.getHBSession();

		if (StringUtil.isEmpty(vsc001)) {
			vsc001 = "";
			vsc001NullFlag = true;
		}

		// 1.ɾ��֮ǰ������У�����ɵ�����
		try {

			String delSqlErrorList = "delete from verify_error_list  where vel004 ='" + imprecordid
					+ "' and  vel005 ='2' ";
			try {
				sess.createSQLQuery(delSqlErrorList).executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 2.��ѯ��ҪУ�������
			StringBuffer dataExiestSql = new StringBuffer(" SELECT  ( ").append("     (SELECT  ")
					.append("       COUNT(1)  ").append("     FROM ").append("       a01_temp a  ")
					.append("     WHERE imprecordid = '" + imprecordid + "') +  ").append("     (SELECT  ")
					.append("       COUNT(1)  ").append("     FROM ").append("       b01_temp a  ")
					.append("     WHERE imprecordid = '" + imprecordid + "') ").append("   )   ")
					.append("   FROM DUAL  ");
			List<Object> list = sess.createSQLQuery(dataExiestSql.toString()).list();

			if (list == null || list.size() < 1 || Long.parseLong(list.get(0).toString()) < 1) {
				throw new Exception("δ���ҵ���ҪУ������ݣ�");
			}
			String totalDataNum = list.get(0).toString();
			// 3.ѭ��У�����VerifyRule�����޸�У����Ϣ��д��У�������Ϣ��VerifyErrorList��
			// У�����ȡѡ���������У�鷽�����������õ�Ĭ�ϻ���У�鷽��
			String verifyRuleHql = "from VerifyRule b where vru007 = '1' ";
			if (!vsc001NullFlag) {
				verifyRuleHql = verifyRuleHql
						+ " and   (vsc001 = :vsc001 and  exists (select 1 from VerifyScheme a where a.vsc001 = b.vsc001 and a.vsc003='1'))  ";
			}
//			ע�͵� Ĭ�ϻ���У�鷽��
//			verifyRuleHql = verifyRuleHql
//					+ " or  exists (select 1 from VerifyScheme a where a.vsc001 = b.vsc001 and a.vsc003='1' and a.vsc007='1')";
			Query queryvr = sess.createQuery(verifyRuleHql);
			if (!vsc001NullFlag) {
				queryvr = queryvr.setString("vsc001", vsc001);
			}
			@SuppressWarnings("unchecked")
			List<VerifyRule> vrList = queryvr.list();
			if (vrList == null || vrList.isEmpty()) {
				throw new Exception("��У�鷽��û�����ö�ӦУ������û�л���У�鷽����");
			}

			StringBuffer insertSqlBf = new StringBuffer(
					"INSERT INTO VERIFY_ERROR_LIST ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010) ");
			String b01SqlTemp = VerificationSchemeConfPageModel.b01TabName + ".imprecordid = '" + imprecordid + "' "; // �����滻
																														// ��=
																														// :B0111��

			String a01ParamSql = ".A0000" + VerificationSchemeConfPageModel.EQUAL
					+ VerificationSchemeConfPageModel.A01_PARAM;
			String b01ParamSql = VerificationSchemeConfPageModel.b01TabName + ".B0111"
					+ VerificationSchemeConfPageModel.EQUAL + VerificationSchemeConfPageModel.B01_PARAM;
			String sqlVerify = null;

			if (vp != null) {
				vp.setTotalNum(Long.parseLong(vrList.size() + ""));
				sess.update(vp);
			}

			// ����У�飺������������ʽ������Ա���֤���ظ�����Ա:�쳣���ͣ�vru003��-����ǰ�쳣��9����У�����Ϊ��
			String moreA0148 = " select a01_temp.a0000 vel002,'9' vru003,'1' vel003,'" + imprecordid + "' vel004,"
					+ vel005Bf
					+ " vel005,'��������ʱ��������ʽ����Ա�Ƚ����֤�ظ���Ա��' vel006,'A01' vel007,'A0184' vel008,'' vel009,'' vel010 from a01_temp where imprecordid='"
					+ imprecordid
					+ "' and EXISTS (SELECT 1 FROM a01 a WHERE a.A0184 = a01_temp.A0184 and a01_temp.A0184 is not null and length(a01_temp.A0184)>0) ";
			moreA0148 = insertSqlBf + "select " + vel001Bf + "  vel001,tt.* from (" + moreA0148 + ") tt";
			CommonQueryBS.systemOut("xx--->" + moreA0148);
			sess.createSQLQuery(moreA0148).executeUpdate();

			long currentNum = 0;
			// ѭ��У�����
			for (VerifyRule vr : vrList) {
				if (vp != null) {
					vp.setCurrentNum(++currentNum);
					vp.setProcessMsg("��" + totalDataNum + "�����ݡ�" + vp.getTotalNum() + "��У���������У���" + currentNum
							+ "������" + vr.getVru002() + "��");
					sess.update(vp);
					sess.flush();
				}
				String vel006 = vr.getVru008();
				if (DBUtil.getDBType() == DBType.MYSQL) {
					vel006 = "CAST('" + vel006 + "' AS CHAR CHARACTER SET utf8)";
				}

				String selectSqlTemp = "";
				String a01SqlTemp = vr.getVru006() + ".imprecordid = '" + imprecordid + "' ";// �����滻 ��xx.A0000 = :A0000��

				sqlVerify = vr.getVru009();
				if (StringUtil.isEmpty(sqlVerify) || "0".equals(vr.getVru007())) {
					continue;
				}

				// zxw ע����Դsql���� :B0000��:A0000 �����ִ�Сд��;����SQL���ֱ��������:A0000������:B0111������
				if (sqlVerify.toUpperCase().contains(VerificationSchemeConfPageModel.A01_PARAM)) {
					selectSqlTemp = " distinct " + vr.getVru006() + ".A0000 as vel002,'" + vr.getVru003()
							+ "' as vru003,'1' as vel003,'" + imprecordid + "' as vel004," + vel005Bf + " as vel005,'"
							+ vr.getVru008() + "' as vel006,'" + vr.getVru004() + "' as vel007,'" + vr.getVru005()
							+ "' as vel008,'" + vr.getVru001() + "' as vel009,'' as vel010 ";
					sqlVerify = sqlVerify.replace(vr.getVru006() + a01ParamSql, a01SqlTemp);
				} else if (sqlVerify.toUpperCase().contains(VerificationSchemeConfPageModel.B01_PARAM)) {
					selectSqlTemp = " distinct " + vr.getVru006() + ".B0111 as vel002,'" + vr.getVru003()
							+ "' as vru003,'2' as vel003,'" + imprecordid + "' as vel004," + vel005Bf + " as vel005,'"
							+ vr.getVru008() + "' as vel006,'" + vr.getVru004() + "' as vel007,'" + vr.getVru005()
							+ "' as vel008,'" + vr.getVru001() + "' as vel009,'' as vel010 ";
					sqlVerify = sqlVerify.replace(b01ParamSql, b01SqlTemp);
				} else {
					continue;
				}
				sqlVerify = sqlVerify.replace(VerificationSchemeConfPageModel.COUNT_SQL, selectSqlTemp);

				sqlVerify = insertSqlBf + "select " + vel001Bf + "  vel001,tt.* from (" + sqlVerify + ") tt";
				CommonQueryBS.systemOut("sql====>" + sqlVerify);
//				 System.out.println("%  ====>"+currentNum/(float)vp.getTotalNum());
				sess.createSQLQuery(sqlVerify).executeUpdate();

			} // end for ѭ��У�����

			String checkFlag = "select count(1) from verify_error_list where vel005 = '2' and  vel004 ='" + imprecordid
					+ "' and vel009 is not null and vru003 != '9'";// �ų�
																	// ����У�飺������������ʽ������Ա���֤���ظ�����Ա:�쳣���ͣ�vru003��-����ǰ�쳣��9����У�����Ϊ��
			List checkFlagList = sess.createSQLQuery(checkFlag).list();
			if (checkFlagList != null && !checkFlagList.isEmpty()
					&& Long.parseLong(checkFlagList.get(0).toString()) > 0) {
				flag = false;
			}

			// 6.��д�����¼���Ƿ�У�飬У�鷽��
			Imprecord imprecord = (Imprecord) sess.get(Imprecord.class, imprecordid);
			imprecord.setIsvirety("1");// ��У��
			imprecord.setVsc001(vrList.get(0).getVsc001());// У�鷽��
			sess.update(imprecord);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("У�鷢���쳣��" + e.getMessage());
		}

		return flag;
	}

	public static boolean dataVerifyByBSType(String b0111, String vsc001, String bsType) throws Exception {
		return dataVerifyByBSType(b0111, vsc001, bsType, null, null, null, null);
	}

	/**
	 * ͨ����ͬҵ�����͵��ò�ͬУ�鷽��
	 * 
	 * @param b0111
	 * @param vsc001
	 * @param bsType 1-������ʽ�ⵥλУ�飨�����¼���λ����2-����У��
	 * @return
	 * @throws AppException
	 * @throws SQLException
	 */
	public static boolean dataVerifyByBSType(String b0111, String ruleids, String bsType, VerifyProcess vp, UserVO user,
			String a0163, String needverifyid) throws Exception {
		if (StringUtil.isEmpty(bsType)) {
			throw new AppException("У��ҵ������Ϊ�գ�");
		}
		HBSession sess = HBUtil.getHBSession();
		String tabName = ""; // �������� ����־ʹ��
		String opObjName = ""; // ��������������־ʹ��
		String opComment = ""; // ������ע����־ʹ��
		boolean flag = true;
		if ("1".equals(bsType)) {
			flag = dataVerifyByB0111VRu001(b0111, ruleids, vp, user, needverifyid);
			tabName = "B01";
			opComment = "ѡ��λУ�飺";
			B01 b01 = (B01) sess.get(B01.class, b0111);
			if (b01 != null) {
				opObjName = b01.getB0101();
			}

		} else if ("2".equals(bsType)) {
			Imprecord imprecord = (Imprecord) sess.get(Imprecord.class, b0111);
			flag = dataVerifyByImprecordidVsc001_Create(b0111, ruleids, vp, imprecord.getImptemptable(), user);
			tabName = "IMP_RECORD";
			opComment = "������ϢУ�飺";
			if (imprecord != null) {
				opObjName = imprecord.getFilename();
			}
		} else if ("3".equals(bsType)) {
			flag = dataVerifyByRulePeroleid(b0111, ruleids, vp, needverifyid, user);
		} else if ("4".equals(bsType)) {
			flag = dataVerifyByB0111Vsc001(b0111, ruleids, vp, a0163, user);
		} else {
			throw new AppException("��֧�ֵ�У��ҵ�����ͣ�");

		}

		// ��¼��־
		try {
			new LogUtil("642", tabName, b0111, opObjName, opComment + flag, null, user).start();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				new LogUtil().createLog("642", tabName, b0111, opObjName, opComment + flag, null);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		return flag;
	}

	public static boolean dataVerifyByB0111Vsc001(String b0111, String vsc001) throws SQLException, AppException {
		return dataVerifyByB0111Vsc001(b0111, vsc001, null, null, null);
	}

	/**
	 * ������֯��������,У�鷽��ID У�� ��Ϣ
	 * 
	 * @param b0111  ��֯��������
	 * @param vsc001 У�鷽��ID
	 * @throws SQLException
	 * @throws AppException
	 */
	public static boolean dataVerifyByB0111Vsc001(String b0111, String ruleids, VerifyProcess vp, String a0163,
			UserVO user) throws SQLException, AppException {
//		return dataVerifyProcedureByB0111Vsc001(b0111,vsc001);  //���ô洢����
//		int i = 0 ;
		HBSession sess = HBUtil.getHBSession();
		boolean flag = true; // Ĭ��Ϊtrue ͨ��
		boolean vsc001NullFlag = false; // vsc001 �Ƿ�Ϊ�ձ��
		String vel001Bf = " sys_guid()  "; // ����VEL001������UUID���ĺ���
		String vel005Bf = " '4' "; // VEL005ҵ������:: 1-����У�飨�����¼���������2-����У�� 3��ԱУ�� 4 ����У��
		// String[]
		// tables={"A01","A02","A06","A08","A11","A14","A15","A29","A30","A31","A36","A37","A41","A53","A60","A61","A62","A63","A64","A99Z1"};
		String[] tables = { "A01", "A02", "A05", "A06", "A08", "A14", "A15", "A17", "A30", "A31", "A36", "A99Z1" };
		String suffix = String.valueOf((int) (Math.random() * 100000));
		if (DBUtil.getDBType() == DBType.MYSQL) {
			vel001Bf = " UUID() ";
			try {
				sess.createSQLQuery("SET GLOBAL group_concat_max_len = 102400").executeUpdate();
				sess.createSQLQuery("SET SESSION group_concat_max_len = 102400").executeUpdate();
			} catch (Exception e) {
				System.out.println("ִ�С�SET GLOBAL group_concat_max_len��ʧ�ܣ�");
			}
		}
		String userid = user.getId();
		String detailID = "";
		int ii = 0;
		String tableLog = "";
		String limitNum = "0";
		// 0.ɾ��֮ǰ������У�����ɵ�����
		try {
			DataVerifyBS dvbs = new DataVerifyBS();

			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("CHECK_DA_SEQUENCE", ++ii + "");
			map1.put("DSA_INFO_ID", "ZWHZYQ");
			map1.put("CHECK_COND_ID", UUID.randomUUID().toString().replaceAll("-", ""));
			map1.put("TARGET_TAB_CODE", "--");
			map1.put("CHECK_COND_SQL", "У�˽ű�-ɾ��֮ǰ������У�����ɵ�����");
			map1.put("CHECK_COUNT", limitNum);
			map1.put("CHECK_DA_BEGIN_DATE", "");
			map1.put("IS_CREATE_FILE", "NO");
			map1.put("CHECK_TOTAL", "0");
			detailID = LargetaskLog.DetailLog(map1, detailID);
			String delSqlErrorList = "delete from verify_error_list_mirror  where vel004 like '" + b0111
					+ "%' and  vel005 ='4' and vel010='" + userid + "'";
			try {
				// sess.createSQLQuery(delete11).executeUpdate();
				String deletesql = "delete from special_verify where sv004='4' and sv005='" + userid + "'";
				CommonQueryBS.systemOut(new Date().toString() + "У��sql:" + deletesql.toString());
				CommonQueryBS.systemOut(new Date().toString() + "У��sql:" + delSqlErrorList.toString());
				sess.createSQLQuery(deletesql).executeUpdate();
				sess.createSQLQuery(delSqlErrorList).executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Map<String, String> map1End = new HashMap<String, String>();
			map1End.put("CHECK_DA_END_TIME", "");
			LargetaskLog.DetailLog(map1End, detailID);
			detailID = "";
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("CHECK_DA_SEQUENCE", ++ii + "");
			map2.put("DSA_INFO_ID", "ZWHZYQ");
			map2.put("CHECK_COND_ID", UUID.randomUUID().toString().replaceAll("-", ""));
			map2.put("TARGET_TAB_CODE", "--");
			map2.put("CHECK_COND_SQL", "У�˽ű�-��ѯ��ҪУ�������");
			map2.put("CHECK_COUNT", limitNum);
			map2.put("CHECK_DA_BEGIN_DATE", "");
			map2.put("IS_CREATE_FILE", "NO");
			map2.put("CHECK_TOTAL", "0");
			detailID = LargetaskLog.DetailLog(map2, detailID);
			String apsql = "";
			// 2.��ѯ��ҪУ�������
			if (a0163.equals("1")) {
				apsql = " and ( a02.a0255='1' or a02.a0281='true')";
			}
			StringBuffer dataExiestSql = new StringBuffer(" SELECT     ").append(" (    ").append("   (SELECT     ")
					.append("     COUNT(1)     ").append("   FROM    ").append("     a01 a     ")
					.append("   WHERE   EXISTS (SELECT 1 FROM a02 WHERE a02.A0000 = a.a0000 AND a02.A0201B LIKE '"
							+ b0111 + "%' " + apsql + ")) +     ")// "+apsql+"
					.append("   (SELECT     ").append("     COUNT(1)     ").append("   FROM    ")
					.append("     b01 a     ").append("   WHERE b0111 LIKE '" + b0111 + "%' )    ").append(" )     ")
					.append(" FROM    DUAL  ");
			if (DBUtil.getDBType() == DBType.MYSQL && !"001.001".equals(b0111)) {
				dataExiestSql = new StringBuffer(" SELECT     ").append(" (    ").append("   (SELECT     ")
						.append("     COUNT(1)     ").append("   FROM    ").append("     a01 a     ")
						.append("   WHERE   a.A0000 in (SELECT a02.A0000 FROM a02 WHERE  a02.A0201B LIKE '" + b0111
								+ "%' " + apsql + ")) +     ")// "+apsql+"
						.append("   (SELECT     ").append("     COUNT(1)     ").append("   FROM    ")
						.append("     b01 a     ").append("   WHERE b0111 LIKE '" + b0111 + "%' )    ")
						.append(" )     ").append(" FROM    DUAL  ");
			}
			CommonQueryBS.systemOut(new Date().toString() + "У��sql:" + dataExiestSql.toString());
			List<Object> list = sess.createSQLQuery(dataExiestSql.toString()).list();
			if (list == null || list.size() < 1 || Long.parseLong(list.get(0).toString()) < 1) {
				throw new AppException("δ���ҵ���ҪУ������ݣ�");
			}
			Map<String, String> map2End = new HashMap<String, String>();
			map2End.put("CHECK_DA_END_TIME", "");
			LargetaskLog.DetailLog(map2End, detailID);
			detailID = "";
			String totalDataNum = list.get(0).toString();

			if ("-1".equals(b0111)) {
				b0111 = HBUtil.getValueFromTab("OTHERINFO", "smt_user", "userid='" + userid + "' and USEFUL='1'");
			}

			if ("001.001".equals(b0111)) {
				/*
				 * try { //У��ǰ��ɾ���������� for(String table :tables){ String sql = "DELETE FROM '"
				 * +table+"' where A0000 in(SELECT b.A0000 from (SELECT a0000 FROM a01 WHERE STATUS = '4')b)"
				 * ; sess.createSQLQuery(sql).executeUpdate(); } } catch (Exception e) {
				 * 
				 * }
				 */

				StringBuffer insertSqlBf = new StringBuffer(
						"INSERT INTO verify_error_list_mirror ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010) ");
				String a01ParamSql = ".A0000" + VerificationSchemeConfPageModel.EQUAL
						+ VerificationSchemeConfPageModel.A01_PARAM;
				String b01ParamSql = VerificationSchemeConfPageModel.EQUAL + VerificationSchemeConfPageModel.B01_PARAM;
				String sqlVerify = null;
				long currentNum = 0;
				String[] ruleid = ruleids.split(",");
				if (vp != null) {
					vp.setTotalNum(Long.parseLong(ruleid.length + ""));
					sess.update(vp);
				}
				List<VerifyRule> vriList = new ArrayList<VerifyRule>();
				String VSC001 = "";
				// ѭ��У�����

				for (String rule : ruleid) {
					
					VerifyRule vr = (VerifyRule) sess.get(VerifyRule.class, rule);
					VSC001 = vr.getVsc001();
					String vru002Id = vr.getVru002();
					vriList.add(vr);
					if (vp != null) {
						vp.setCurrentNum(++currentNum);
						vp.setProcessMsg("��" + totalDataNum + "�����ݡ�" + vp.getTotalNum() + "��У���������У���" + currentNum
								+ "������" + vr.getVru002() + "��");
						PhotosUtil.saveLog("��" + totalDataNum + "�����ݡ�" + vp.getTotalNum() + "��У���������У���" + currentNum
								+ "������" + vr.getVru002() + "��");
						sess.update(vp);
						sess.flush();
					}
					if (!tableLog.equals(vr.getVru006().replace("_temp", ""))) {
						tableLog = vr.getVru006().replace("_temp", "");
						limitNum = sess.createSQLQuery("select count(*) from " + vr.getVru006().replace("_temp", ""))
								.uniqueResult().toString();

					}
					detailID = "";
					Map<String, String> map = new HashMap<String, String>();
					map.put("CHECK_DA_SEQUENCE", ++ii + "");
					map.put("CHECK_COND_ID", rule);
					map.put("DSA_INFO_ID", "ZWHZYQ");
					map.put("TARGET_TAB_CODE", vr.getVru006().replace("_temp", ""));
					map.put("TARGET_COL_CODE", vr.getVru005());
					map.put("CHECK_COND_SQL", "У�˽ű�");
					map.put("CHECK_COUNT", limitNum);
					map.put("CHECK_DA_BEGIN_DATE", "");
					map.put("IS_CREATE_FILE", "NO");
					map.put("CHECK_TOTAL", "0");
					detailID = LargetaskLog.DetailLog(map, detailID);
					String vel006 = vr.getVru008();
					if (DBUtil.getDBType() == DBType.MYSQL) {
						vel006 = "CAST('" + vel006 + "' AS CHAR CHARACTER SET utf8)";
					}

					String selectSqlTemp = "";
					String ap2sql = "";
					if (a0163.equals("1")) {
						// a01SqlTemp = a01SqlTemp+" and exists (select 1 from a01 v where v.a0000 =
						// "+vr.getVru006()+".A0000 and v.a0163 ='1') ";
						// ap2sql = " and x.a0255='1' ";
						/// 2017.04.17 yinl ͨ��a01����ˣ�a02�����������Ϊ��
						ap2sql = " and  EXISTS(select 1 from a01 a01 where a01.A0000 = x.A0000 and a01.A0163 = '1' and a01.STATUS <>'4')  ";

					}
					/**
					 * else{ ap2sql = " and x.A0201B is not null "; }
					 **/
//					String a01SqlTemp = " EXISTS (SELECT 1 FROM A02  x WHERE x.a0000 = "+vr.getVru006()+".A0000 AND x.a0201b LIKE  '"+b0111+"%' "+ap2sql+") ";//�����滻 ��xx.A0000 = :A0000��
					String a01SqlTemp = "";
					if (DBUtil.getDBType() == DBType.MYSQL) {
						a01SqlTemp = " " + vr.getVru006()
								+ ".A0000 IN (SELECT x.A0000 FROM A02  x WHERE  x.a0201b LIKE  '" + b0111 + "%' "
								+ ap2sql + ") ";// �����滻 ��xx.A0000 = :A0000��
					} else {
						a01SqlTemp = " EXISTS (SELECT 1 FROM A02  x WHERE x.a0000 = " + vr.getVru006()
								+ ".A0000 AND x.a0201b LIKE  '" + b0111 + "%' " + ap2sql + ") ";// �����滻 ��xx.A0000 =
																								// :A0000��
					}

					sqlVerify = vr.getVru009();
					if (StringUtil.isEmpty(sqlVerify) || "0".equals(vr.getVru007())) {
						continue;
					}

					// zxw ע����Դsql���� :B0000��:A0000 �����ִ�Сд��;����SQL���ֱ��������:A0000������:B0111������
					if (sqlVerify.toUpperCase().contains(VerificationSchemeConfPageModel.A01_PARAM)) {
						selectSqlTemp = " distinct " + vr.getVru006() + ".A0000 as vel002,'" + vr.getVru003()
								+ "' as vru003,'1' as vel003,'" + b0111 + "' as vel004," + vel005Bf + " as vel005,'"
								+ vr.getVru008() + "' as vel006,'" + vr.getVru004() + "' as vel007,'" + vr.getVru005()
								+ "' as vel008,'" + vr.getVru001() + "' as vel009,'" + userid + "' as vel010 ";
						// sqlVerify = sqlVerify.replace(vr.getVru006()+a01ParamSql, a01SqlTemp);
						sqlVerify = sqlVerify.replace(vr.getVru006() + a01ParamSql,
								"".equals(a01SqlTemp) ? " 1=1 " : " " + a01SqlTemp + " ");
					} else if (sqlVerify.toUpperCase().contains(VerificationSchemeConfPageModel.B01_PARAM)) {
						selectSqlTemp = " distinct " + vr.getVru006() + ".B0111 as vel002,'" + vr.getVru003()
								+ "' as vru003,'2' as vel003,'" + b0111 + "' as vel004," + vel005Bf + " as vel005,'"
								+ vr.getVru008() + "' as vel006,'" + vr.getVru004() + "' as vel007,'" + vr.getVru005()
								+ "' as vel008,'" + vr.getVru001() + "' as vel009,'" + userid + "' as vel010 ";
						// sqlVerify =sqlVerify.replace( b01ParamSql, b01SqlTemp);
						sqlVerify = sqlVerify.replace(vr.getVru006() + ".B0111" + b01ParamSql, " 1=1 ");
					} else {
						continue;
					}
					sqlVerify = sqlVerify.replace(VerificationSchemeConfPageModel.COUNT_SQL, selectSqlTemp);
					sqlVerify = insertSqlBf + "select " + vel001Bf + "  vel001,tt.* from (" + sqlVerify + ") tt";
					// �����п������⴦��
					if (sqlVerify.contains("A1701")) {
						if (DBUtil.getDBType() == DBType.MYSQL) {
							sqlVerify = sqlVerify.replace("A01.A1701 is null",
									"replace(replace(A01.A1701,char(13),''),char(10),'') is null");
						} else {
							sqlVerify = sqlVerify.replace("A01.A1701 is null",
									"replace(replace(A01.A1701,chr(13),''),chr(10),'') is null");
						}
					}

					try {
						if (DBUtil.getDBType() == DBType.MYSQL) {
							if ("ѧ����Ϣ-��ȫ����ѧ��".equals(vru002Id)) {

								String sql = "create table A08q_" + suffix
										+ " as select a0000,a0837 from A08 WHERE a0837 = '1'";

								sess.createSQLQuery(sql).executeUpdate();
								sess.createSQLQuery(
										"ALTER TABLE a08q_" + suffix + " add index a08q_" + suffix + "_1(a0000)")
										.executeUpdate();
								sqlVerify = sqlVerify.replace("A08_" + suffix, "A08q_" + suffix);
								sqlVerify = sqlVerify.replace("a08_" + suffix, "A08q_" + suffix);
							}
							if ("��ͥ��Ա��ν-ȱ�ٸ��׻�ĸ����Ϣ".equals(vru002Id)) {
								String sql = "create table A36f_" + suffix
										+ " as select a0000 from A36 WHERE a3604a='����' or  a3604a='�̸�' or  a3604a='����'";
								String sql1 = "create table A36m_" + suffix
										+ " as select a0000 from A36 WHERE a3604a='ĸ��' or  a3604a='��ĸ' or  a3604a='��ĸ'";

								sess.createSQLQuery(sql).executeUpdate();
								sess.createSQLQuery(sql1).executeUpdate();
								sqlVerify = sqlVerify.replace("A36f_temp", "A36f_" + suffix);
								sqlVerify = sqlVerify.replace("a36f_temp", "A36f_" + suffix);
								sqlVerify = sqlVerify.replace("A36m_temp", "A36m_" + suffix);
								sqlVerify = sqlVerify.replace("a36m_temp", "A36m_" + suffix);
							}
						} else {
							if ("ѧ����Ϣ-��ȫ����ѧ��".equals(vru002Id)) {

								String sql = "create table A08q_" + suffix
										+ " as select a0000 from A08 WHERE a0837 = '1'";
								sess.createSQLQuery(sql).executeUpdate();

								sess.createSQLQuery("create index A08q_" + suffix + "_1 on a08q_" + suffix + " (a0000)")
										.executeUpdate();
								sqlVerify = sqlVerify.replace("A08_" + suffix, "A08q_" + suffix);
								sqlVerify = sqlVerify.replace("a08_" + suffix, "A08q_" + suffix);
							}
							if ("��ͥ��Ա��ν-ȱ�ٸ��׻�ĸ����Ϣ".equals(vru002Id)) {
								String sql = "create table A36f_" + suffix
										+ " as select a0000 from A36 WHERE a3604a='����' or  a3604a='�̸�' or  a3604a='����'";
								String sql1 = "create table A36m_" + suffix
										+ " as select a0000 from A36 WHERE a3604a='ĸ��' or  a3604a='��ĸ' or  a3604a='��ĸ'";

								sess.createSQLQuery(sql).executeUpdate();
								sess.createSQLQuery(sql1).executeUpdate();
								sqlVerify = sqlVerify.replace("A36f_temp", "A36f_" + suffix);
								sqlVerify = sqlVerify.replace("a36f_temp", "A36f_" + suffix);
								sqlVerify = sqlVerify.replace("A36m_temp", "A36m_" + suffix);
								sqlVerify = sqlVerify.replace("a36m_temp", "A36m_" + suffix);
							}

						}
						sess.createSQLQuery(sqlVerify.replace("_temp", "")).executeUpdate();
						Map<String, String> mapEnd = new HashMap<String, String>();
						mapEnd.put("CHECK_DA_END_TIME", "");
						LargetaskLog.DetailLog(mapEnd, detailID);
						detailID = "";
					} catch (Exception e) {
						System.out.println("����У������У��sql-----------" + sqlVerify.replace("_temp", ""));
						e.printStackTrace();
						String vru001 = vr.getVru001();
						String vru002 = vr.getVru002();
						Map<String, String> mapEnd = new HashMap<String, String>();
						StringWriter sw = new StringWriter();
						PrintWriter pw = new PrintWriter(sw);
						e.printStackTrace(pw);
						String pwValue = sw.toString();
						if (pwValue.length() > 2000) {
							pwValue = pwValue.substring(0, 2000);
						}
						mapEnd.put("CHECK_DA_END_TIME", "");
						mapEnd.put("CHECK_SQL_ERROR", pwValue);
						LargetaskLog.DetailLog(mapEnd, detailID);
						detailID = "";
//						CommonQueryBS.systemOut(new Date().toString()+"У��������:"+sqlVerify);
						String sql = " insert into special_verify values(" + vel001Bf + ",'" + vru001 + "','" + vru002
								+ "','4','" + userid + "','')";
						sess.createSQLQuery(sql).executeUpdate();
						continue;
					}
					
				} // end for ѭ��У�����
					// ���� - ����У��ʵ��У�˽��ͳ�ƹ���
				if ("1".equals(HBUtil.getValueFromTab("count(1)", "aa01", "aaa001 = 'CHECK_WORD' and aaa005 = 'ON'"))) {
					dvbs.tjCheckInfo(tables, vriList, suffix, b0111, userid, VSC001);
				} else {
					// �������ͳ�Ʊ�
					HBUtil.executeUpdate("delete from check_tj ");
				}

				// ����У��ʵ�ֽ�����ϲ�����
				dvbs.updatevelbyb0111(b0111, "4", userid);

				String checkFlag = "select count(1) from verify_error_list where vel005 = '4' and  vel004 = '" + b0111
						+ "' and vel010='" + userid + "'";

				List checkFlagList = sess.createSQLQuery(checkFlag).list();
				if (checkFlagList != null && !checkFlagList.isEmpty()
						&& Long.parseLong(checkFlagList.get(0).toString()) > 0) {
					flag = false;
				}
				System.out.println("����Ϊֹ��");
			} else {

				// ������ʱ��
				String a0163sql = "";
				if ((a0163.equals("1"))) {
					a0163sql = " and  EXISTS(select 1 from a01 a01 where a01.A0000 = x.A0000 and a01.A0163 = '1' and a01.STATUS <>'4') ";
				}
				detailID = "";
				Map<String, String> map3 = new HashMap<String, String>();
				map3.put("CHECK_DA_SEQUENCE", ++ii + "");
				map3.put("CHECK_COND_ID", UUID.randomUUID().toString().replaceAll("-", ""));
				map3.put("DSA_INFO_ID", "ZWHZYQ");
				map3.put("TARGET_TAB_CODE", "--");
				map3.put("CHECK_COND_SQL", "У�˽ű�-������ʱ��");
				map3.put("CHECK_COUNT", limitNum);
				map3.put("CHECK_DA_BEGIN_DATE", "");
				map3.put("IS_CREATE_FILE", "NO");
				map3.put("CHECK_TOTAL", "0");
				detailID = LargetaskLog.DetailLog(map3, detailID);
				for (String table : tables) {
					String CreateSQL = "";
					/**
					 * �����ı�׼�� ��Աְ������ְ��λ�ڵ��£� �Ƿ��������ְ���û��ж�
					 */

					if ("A01".equals(table)) {
						if (DBUtil.getDBType() == DBType.ORACLE) {
							CreateSQL = "create table " + table + "_L_" + suffix + " as select * from " + table
									+ " WHERE EXISTS (SELECT 1 FROM A02 x WHERE x.a0000 = " + table
									+ ".A0000 AND x.a0201b LIKE '" + b0111 + "%'" + a0163sql + ") AND  " + table
									+ ".A0000 NOT IN(SELECT A0000 FROM a01 where status = '4')";
						} else {
							CreateSQL = "create table " + table + "_L_" + suffix + " as select * from " + table
									+ " WHERE EXISTS (SELECT 1 FROM A02 x WHERE x.a0000 = " + table
									+ ".A0000 AND x.a0201b LIKE '" + b0111 + "%'" + a0163sql + ") AND  " + table
									+ ".A0000 NOT IN(SELECT A0000 FROM a01 where status = '4')";
						}

					} else {
						CreateSQL = "create table " + table + "_L_" + suffix + " as select * from " + table
								+ " where EXISTS (select 1 from a01_L_" + suffix + " where a01_L_" + suffix + ".a0000 = "
								+ table + ".a0000) ";
					}
					CommonQueryBS.systemOut("�������sql:" + CreateSQL);

					// CommonQueryBS.systemOut(table+"ִ�����ʱ�䣺---"+w.elapsedTime());
					sess.createSQLQuery(CreateSQL).executeUpdate();

				}
				Map<String, String> map3End = new HashMap<String, String>();
				map3End.put("CHECK_DA_END_TIME", "");
				LargetaskLog.DetailLog(map3End, detailID);
				detailID = "";
				String b01_verify = "create table b01_L_" + suffix + " as select * from B01 WHERE B01.b0111 LIKE '"
						+ b0111 + "%' and (B01.delFlag <> '0' or B01.delflag is null)";
				CommonQueryBS.systemOut(new Date().toString() + "У��sql:" + b01_verify);
				sess.createSQLQuery(b01_verify).executeUpdate();
				Map<String, String> map4 = new HashMap<String, String>();
				map4.put("CHECK_DA_SEQUENCE", ++ii + "");
				map4.put("CHECK_COND_ID", UUID.randomUUID().toString().replaceAll("-", ""));
				map4.put("DSA_INFO_ID", "ZWHZYQ");
				map4.put("TARGET_TAB_CODE", "--");
				map4.put("CHECK_COND_SQL", "У�˽ű�-������ʱ������");
				map4.put("CHECK_COUNT", limitNum);
				map4.put("CHECK_DA_BEGIN_DATE", "");
				map4.put("IS_CREATE_FILE", "NO");
				map4.put("CHECK_TOTAL", "0");
				detailID = LargetaskLog.DetailLog(map4, detailID);
				ImpmodelThread.checkIndex("_L_" + suffix, tables);// ������ʱ������
				Map<String, String> map4End = new HashMap<String, String>();
				map4End.put("CHECK_DA_END_TIME", "");
				LargetaskLog.DetailLog(map4End, detailID);
				detailID = "";
				StringBuffer insertSqlBf = new StringBuffer(
						"INSERT INTO verify_error_list_mirror ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010) ");
				String b01SqlTemp = " like '" + b0111 + "%' "; // �����滻 ��= :B0111��

				String a01ParamSql = ".A0000" + VerificationSchemeConfPageModel.EQUAL
						+ VerificationSchemeConfPageModel.A01_PARAM;
				String b01ParamSql = VerificationSchemeConfPageModel.EQUAL + VerificationSchemeConfPageModel.B01_PARAM;
				String sqlVerify = null;
				long currentNum = 0;
				String[] ruleid = ruleids.split(",");
				if (vp != null) {
					vp.setTotalNum(Long.parseLong(ruleid.length + ""));
					sess.update(vp);
				}
				List<VerifyRule> vriList = new ArrayList<VerifyRule>();
				String VSC001 = "";
				// ѭ��У�����
				for (String rule : ruleid) {
					/*
					 * try { //У��ǰ��ɾ���������� for(String table :tables){ String sql = "DELETE FROM "
					 * +table+" where A0000 in(SELECT b.A0000 from (SELECT a0000 FROM a01 WHERE STATUS = '4')b)"
					 * ; sess.createSQLQuery(sql).executeUpdate(); } } catch (Exception e) {
					 * 
					 * }
					 */
					VerifyRule vr = (VerifyRule) sess.get(VerifyRule.class, rule);
					VSC001 = vr.getVsc001();
					String vru002Id = vr.getVru002();
					vriList.add(vr);
					if (vp != null) {
						vp.setCurrentNum(++currentNum);
						vp.setProcessMsg("��" + totalDataNum + "�����ݡ�" + vp.getTotalNum() + "��У���������У���" + currentNum
								+ "������" + vr.getVru002() + "��");
						PhotosUtil.saveLog("��" + totalDataNum + "�����ݡ�" + vp.getTotalNum() + "��У���������У���" + currentNum
								+ "������" + vr.getVru002() + "��");
						sess.update(vp);
						sess.flush();
					}
					detailID = "";
					Map<String, String> map = new HashMap<String, String>();
					map.put("CHECK_DA_SEQUENCE", ++ii + "");
					map.put("CHECK_COND_ID", rule);
					map.put("DSA_INFO_ID", "ZWHZYQ");
					map.put("TARGET_TAB_CODE", vr.getVru006().replace("_temp", ""));
					map.put("TARGET_COL_CODE", vr.getVru005());
					map.put("CHECK_COND_SQL", "У�˽ű�");
					map.put("CHECK_COUNT", limitNum);
					map.put("CHECK_DA_BEGIN_DATE", "");
					map.put("IS_CREATE_FILE", "NO");
					map.put("CHECK_TOTAL", "0");
					detailID = LargetaskLog.DetailLog(map, detailID);
					String vel006 = vr.getVru008();
					if (DBUtil.getDBType() == DBType.MYSQL) {
						vel006 = "CAST('" + vel006 + "' AS CHAR CHARACTER SET utf8)";
					}

					String selectSqlTemp = "";
					String ap2sql = "";
					if (a0163.equals("1")) {
						// a01SqlTemp = a01SqlTemp+" and exists (select 1 from a01 v where v.a0000 =
						// "+vr.getVru006()+".A0000 and v.a0163 ='1') ";
						// ap2sql = " and x.a0255='1' ";
						/// 2017.04.17 yinl ͨ��a01����ˣ�a02�����������Ϊ��
						ap2sql = " and  EXISTS(select 1 from a01 a01 where a01.A0000 = x.A0000 and a01.A0163 = '1' and a01.STATUS <>'4')  ";

					}
					/**
					 * else{ ap2sql = " and x.A0201B is not null "; }
					 **/
//				String a01SqlTemp = " EXISTS (SELECT 1 FROM A02  x WHERE x.a0000 = "+vr.getVru006()+".A0000 AND x.a0201b LIKE  '"+b0111+"%' "+ap2sql+") ";//�����滻 ��xx.A0000 = :A0000��
					String a01SqlTemp = "";
					if (DBUtil.getDBType() == DBType.MYSQL) {
						a01SqlTemp = " " + vr.getVru006()
								+ ".A0000 IN (SELECT x.A0000 FROM A02  x WHERE  x.a0201b LIKE  '" + b0111 + "%' "
								+ ap2sql + ")  main";// �����滻 ��xx.A0000 = :A0000��
					} else {
						a01SqlTemp = " EXISTS (SELECT 1 FROM A02  x WHERE x.a0000 = " + vr.getVru006()
								+ ".A0000 AND x.a0201b LIKE  '" + b0111 + "%' " + ap2sql + ") ";// �����滻 ��xx.A0000 =
																								// :A0000��
					}

					sqlVerify = vr.getVru009();
					if (StringUtil.isEmpty(sqlVerify) || "0".equals(vr.getVru007())) {
						continue;
					}

					// zxw ע����Դsql���� :B0000��:A0000 �����ִ�Сд��;����SQL���ֱ��������:A0000������:B0111������
					if (sqlVerify.toUpperCase().contains(VerificationSchemeConfPageModel.A01_PARAM)) {
						selectSqlTemp = " distinct " + vr.getVru006() + ".A0000 as vel002,'" + vr.getVru003()
								+ "' as vru003,'1' as vel003,'" + b0111 + "' as vel004," + vel005Bf + " as vel005,'"
								+ vr.getVru008() + "' as vel006,'" + vr.getVru004() + "' as vel007,'" + vr.getVru005()
								+ "' as vel008,'" + vr.getVru001() + "' as vel009,'" + userid + "' as vel010 ";
						// sqlVerify = sqlVerify.replace(vr.getVru006()+a01ParamSql, a01SqlTemp);
						sqlVerify = sqlVerify.replace(vr.getVru006() + a01ParamSql, " 1=1 ");
					} else if (sqlVerify.toUpperCase().contains(VerificationSchemeConfPageModel.B01_PARAM)) {
						selectSqlTemp = " distinct " + vr.getVru006() + ".B0111 as vel002,'" + vr.getVru003()
								+ "' as vru003,'2' as vel003,'" + b0111 + "' as vel004," + vel005Bf + " as vel005,'"
								+ vr.getVru008() + "' as vel006,'" + vr.getVru004() + "' as vel007,'" + vr.getVru005()
								+ "' as vel008,'" + vr.getVru001() + "' as vel009,'" + userid + "' as vel010 ";
						// sqlVerify =sqlVerify.replace( b01ParamSql, b01SqlTemp);
						sqlVerify = sqlVerify.replace(vr.getVru006() + ".B0111" + b01ParamSql, " 1=1 ");
					} else {
						continue;
					}
					sqlVerify = sqlVerify.replace(VerificationSchemeConfPageModel.COUNT_SQL, selectSqlTemp);

					// ȥ����ʱ����
					sqlVerify = sqlVerify.replace(VerificationSchemeConfPageModel.TEMP_PREFIX, "_L_" + suffix);

					sqlVerify = insertSqlBf + "select " + vel001Bf + "  vel001,tt.* from (" + sqlVerify + ") tt";
//				System.out.println("sql====>"+sqlVerify);
//				System.out.println("%  ====>"+currentNum/(float)vp.getTotalNum());
					// �����п������⴦��
					if (sqlVerify.contains("A1701")) {
						if (DBUtil.getDBType() == DBType.MYSQL) {
							sqlVerify = sqlVerify.replace("A01_L_" + suffix + ".A1701 is null",
									"replace(replace(A01_L_" + suffix + ".A1701,char(13),''),char(10),'') is null");
						} else {
							sqlVerify = sqlVerify.replace("A01_L_" + suffix + ".A1701 is null",
									"replace(replace(A01_L_" + suffix + ".A1701,chr(13),''),chr(10),'') is null");
						}
					}
					PhotosUtil.saveLog("����ִ�е�sql--> " + sqlVerify);
					try {
						if (DBUtil.getDBType() == DBType.MYSQL) {
							if ("ѧ����Ϣ-��ȫ����ѧ��".equals(vru002Id)) {
								String sql = "create table A08q_L_" + suffix
										+ " as select a0000,a0837 from A08 WHERE a0837 = '1'";

								sess.createSQLQuery(sql).executeUpdate();
								sess.createSQLQuery(
										"ALTER TABLE a08q_L_" + suffix + " add index a08q_L_" + suffix + "_1(a0000)")
										.executeUpdate();
								sqlVerify = sqlVerify.replace("A08_L_" + suffix, "A08q_L_" + suffix);
								sqlVerify = sqlVerify.replace("a08_L_" + suffix, "A08q_L_" + suffix);
							}
							if ("��ͥ��Ա��ν-ȱ�ٸ��׻�ĸ����Ϣ".equals(vru002Id)) {
								String sql = "create table A36f_L_" + suffix
										+ " as select a0000 from A36 WHERE a3604a='����' or  a3604a='�̸�' or  a3604a='����'";
								String sql1 = "create table A36m_L_" + suffix
										+ " as select a0000 from A36 WHERE a3604a='ĸ��' or  a3604a='��ĸ' or  a3604a='��ĸ'";

								sess.createSQLQuery(sql).executeUpdate();
								sess.createSQLQuery(sql1).executeUpdate();
								sqlVerify = sqlVerify.replace("A36f_temp", "A36f_L_" + suffix);
								sqlVerify = sqlVerify.replace("a36f_temp", "A36f_L_" + suffix);
								sqlVerify = sqlVerify.replace("A36m_temp", "A36m_L_" + suffix);
								sqlVerify = sqlVerify.replace("a36m_temp", "A36m_L_" + suffix);
							}
						} else {
							if ("ѧ����Ϣ-��ȫ����ѧ��".equals(vru002Id)) {

								String sql = "create table A08q_L_" + suffix
										+ " as select a0000 from A08 WHERE a0837 = '1'";
								sess.createSQLQuery(sql).executeUpdate();

								sess.createSQLQuery("create index A08q_L_" + suffix + "_1 on a08q_L_" + suffix + " (a0000)")
										.executeUpdate();
								sqlVerify = sqlVerify.replace("A08_L_" + suffix, "A08q_L_" + suffix);
								sqlVerify = sqlVerify.replace("a08_L_" + suffix, "A08q_L_" + suffix);
							}
							if ("��ͥ��Ա��ν-ȱ�ٸ��׻�ĸ����Ϣ".equals(vru002Id)) {
								String sql = "create table A36f_L_" + suffix
										+ " as select a0000 from A36 WHERE a3604a='����' or  a3604a='�̸�' or  a3604a='����'";
								String sql1 = "create table A36m_L_" + suffix
										+ " as select a0000 from A36 WHERE a3604a='ĸ��' or  a3604a='��ĸ' or  a3604a='��ĸ'";

								sess.createSQLQuery(sql).executeUpdate();
								sess.createSQLQuery(sql1).executeUpdate();
								sqlVerify = sqlVerify.replace("A36f_temp", "A36f_L_" + suffix);
								sqlVerify = sqlVerify.replace("a36f_temp", "A36f_L_" + suffix);
								sqlVerify = sqlVerify.replace("A36m_temp", "A36m_L_" + suffix);
								sqlVerify = sqlVerify.replace("a36m_temp", "A36m_L_" + suffix);
							}

						}
						sess.createSQLQuery(sqlVerify).executeUpdate();
						Map<String, String> mapEnd = new HashMap<String, String>();
						mapEnd.put("CHECK_DA_END_TIME", "");
						LargetaskLog.DetailLog(mapEnd, detailID);
						detailID = "";
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("�����У��sql-----------" + sqlVerify);
						String vru001 = vr.getVru001();
						String vru002 = vr.getVru002();
						Map<String, String> mapEnd = new HashMap<String, String>();
						StringWriter sw = new StringWriter();
						PrintWriter pw = new PrintWriter(sw);
						e.printStackTrace(pw);
						String pwValue = sw.toString();
						if (pwValue.length() > 2000) {
							pwValue = pwValue.substring(0, 2000);
						}
						mapEnd.put("CHECK_DA_END_TIME", "");
						mapEnd.put("CHECK_SQL_ERROR", pwValue);
						LargetaskLog.DetailLog(mapEnd, detailID);
						detailID = "";
//					CommonQueryBS.systemOut(new Date().toString()+"У��������:"+sqlVerify);
						String sql = " insert into special_verify values(" + vel001Bf + ",'" + vru001 + "','" + vru002
								+ "','4','" + userid + "','')";
						sess.createSQLQuery(sql).executeUpdate();
						continue;
					}

				} // end for ѭ��У�����
					// ���� - ����У��ʵ��У�˽��ͳ�ƹ���
				if ("1".equals(HBUtil.getValueFromTab("count(1)", "aa01", "aaa001 = 'CHECK_WORD' and aaa005 = 'ON'"))) {
					dvbs.tjCheckInfo(tables, vriList, suffix, b0111, userid, VSC001);
				} else {
					// �������ͳ�Ʊ�
					HBUtil.executeUpdate("delete from check_tj ");
				}
				Map<String, String> map5 = new HashMap<String, String>();
				map5.put("CHECK_DA_SEQUENCE", ++ii + "");
				map5.put("CHECK_COND_ID", UUID.randomUUID().toString().replaceAll("-", ""));
				map5.put("DSA_INFO_ID", "ZWHZYQ");
				map5.put("TARGET_TAB_CODE", "--");
				map5.put("CHECK_COND_SQL", "У�˽ű�-����У�˺ϲ�");
				map5.put("CHECK_COUNT", limitNum);
				map5.put("CHECK_DA_BEGIN_DATE", "");
				map5.put("IS_CREATE_FILE", "NO");
				map5.put("CHECK_TOTAL", "0");
				detailID = LargetaskLog.DetailLog(map5, detailID);
				// ����У��ʵ�ֽ�����ϲ�����
				dvbs.updatevelbyb0111(b0111, "4", userid);
				Map<String, String> map5End = new HashMap<String, String>();
				map5End.put("CHECK_DA_END_TIME", "");
				LargetaskLog.DetailLog(map5End, detailID);
				detailID = "";
				String checkFlag = "select count(1) from verify_error_list where vel005 = '4' and  vel004 = '" + b0111
						+ "' and vel010='" + userid + "'";
				List checkFlagList = sess.createSQLQuery(checkFlag).list();
				if (checkFlagList != null && !checkFlagList.isEmpty()
						&& Long.parseLong(checkFlagList.get(0).toString()) > 0) {
					flag = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, String> mapEnd = new HashMap<String, String>();
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String pwValue = sw.toString();
			if (pwValue.length() > 2000) {
				pwValue = pwValue.substring(0, 2000);
			}
			mapEnd.put("CHECK_DA_END_TIME", "");
			mapEnd.put("CHECK_SQL_ERROR", pwValue);
			LargetaskLog.DetailLog(mapEnd, detailID);
			detailID = "";
			throw new AppException("У�鷢���쳣��" + e.getMessage());
		} finally {
			Map<String, String> map5 = new HashMap<String, String>();
			map5.put("CHECK_DA_SEQUENCE", ++ii + "");
			map5.put("CHECK_COND_ID", UUID.randomUUID().toString().replaceAll("-", ""));
			map5.put("DSA_INFO_ID", "ZWHZYQ");
			map5.put("TARGET_TAB_CODE", "--");
			map5.put("CHECK_COND_SQL", "У�˽ű�-ɾ����ʱ��");
			map5.put("CHECK_COUNT", limitNum);
			map5.put("CHECK_DA_BEGIN_DATE", "");
			map5.put("IS_CREATE_FILE", "NO");
			map5.put("CHECK_TOTAL", "0");
			detailID = LargetaskLog.DetailLog(map5, detailID);
			// ɾ����ʱ��
			for (String table : tables) {
				String DropSQL = "drop table " + table + "_L_" + suffix;
				String dropSql = "drop table A08q_L_" + suffix + " ";
				String dropSql1 = "drop table  A36f_L_" + suffix + " ";
				String dropSql2 = "drop table A36m_L_" + suffix + " ";
				try {
					sess.createSQLQuery(DropSQL).executeUpdate();
					sess.createSQLQuery(dropSql).executeUpdate();
					sess.createSQLQuery(dropSql1).executeUpdate();
					sess.createSQLQuery(dropSql2).executeUpdate();
				} catch (Exception e2) {

				}
			}
			String DropB01 = "drop table B01_L_" + suffix;
			try {
				sess.createSQLQuery(DropB01).executeUpdate();
			} catch (Exception e2) {

			}
			Map<String, String> map5End = new HashMap<String, String>();
			map5End.put("CHECK_DA_END_TIME", "");
			LargetaskLog.DetailLog(map5End, detailID);
			detailID = "";

		}
//		System.out.println("��ִ��"+i);
		return flag;
	}

	/**
	 * ��ȡĳУ������ĳ�˵Ĵ����ֶ�
	 * 
	 * @return
	 */
	public static String getErrorInfo(String vel002, String vel004, String vel005) {
		String jsonStr = "[]";
		// �ų�����У��
		String hql = "select new map(vel007 as tableCode,vel008 as columnCode) from VerifyErrorList where vel002 ='"
				+ vel002 + "'  AND vel004 ='" + vel004 + "'  AND vel005 ='" + vel005 + "' and vru003!='9'";
		HBSession sess = HBUtil.getHBSession();
		List<Map> list = sess.createQuery(hql).list();
		if (list != null && !list.isEmpty()) {
			jsonStr = JSONArray.fromObject(list).toString();
		}

		return jsonStr;
	}

	/**
	 * ��V_aa10 ��ͼ�л�ȡ��Ӧֵ
	 * 
	 * @param aaa100
	 * @return
	 */
	public static List<Object> getValuesFromVaa10(String aaa100) {
		List<Object> list = new ArrayList<Object>();
		if (StringUtil.isEmpty(aaa100)) {
			return list;
		}

		String sql = "select aaa102 from v_aa10 where aaa100=:aaa100";
		list = HBUtil.getHBSession().createSQLQuery(sql).setString("aaa100", aaa100.trim().toUpperCase()).list();
		return list;
	}

	/**
	 * ���ݵ�������ID,У�鷽��ID У������ε�����Ϣ
	 * 
	 * 0.ɾ��֮ǰ������У�����ɵ����� 1.��ѯУ�鷽������ҪУ��ı���Ϣ 2.��ѯ��������������ҪУ�������
	 * 3.ѭ����Ա����Ϣ,ѭ��У�����VerifyRule�����޸�У����Ϣ��д��У�������Ϣ��VerifyError,VerifyErrorDetail��
	 * 4.ɾ�� û�ж�Ӧ verify_error_datail��Ϣ��verify_error�� 5.��д�����¼���Ƿ�У�飬У�鷽��
	 * 
	 * @param imprecordid ��������ID
	 * @param vsc001      У�鷽��ID
	 * @throws SQLException
	 * @throws AppException
	 */
	public static boolean dataVerifyByImprecordidVsc001_Create(String imprecordid, String ruleids, VerifyProcess vp,
			String imptemptable, UserVO user) throws Exception {

		// return dataVerifyProcedureByImprecordidVsc001(imprecordid,vsc001); //�洢����

		boolean flag = true; // Ĭ��Ϊtrue ͨ��
		boolean vsc001NullFlag = false; // vsc001 �Ƿ�Ϊ�ձ��
		String vel001Bf = " sys_guid()  "; // ����VEL001������UUID���ĺ���
		String vel005Bf = " '2' "; // VEL005ҵ������:: 1-����У�飨�����¼���������2-����У��
		String userid = user.getId();
		String suffix = String.valueOf((int) (Math.random() * 100000));

		if (DBUtil.getDBType() == DBType.MYSQL) {
			vel001Bf = " UUID() ";
		}
		String[] tables = { "A01", "A02", "A05", "A06", "A08", "A14", "A15", "A17", "A30", "A31", "A36", "A99Z1" };
		ImpmodelThread.checkIndex(imptemptable, tables);

		HBSession sess = HBUtil.getHBSession();

		/*
		 * if (StringUtil.isEmpty(vsc001)) { vsc001 = ""; vsc001NullFlag = true; }
		 */

		// 1.ɾ��֮ǰ������У�����ɵ�����
		try {
			DataVerifyBS dvbs = new DataVerifyBS();

			String delSqlErrorList = "delete from verify_error_list  where vel004 ='" + imprecordid
					+ "' and  vel005 ='2' and vel010='" + userid + "'";
			try {
				String deletesql = "delete from special_verify where sv004='2' and sv005='" + userid + "'";
				sess.createSQLQuery(deletesql).executeUpdate();
				sess.createSQLQuery(delSqlErrorList).executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 2.��ѯ��ҪУ�������
			StringBuffer dataExiestSql = new StringBuffer(" SELECT  ( ").append("     (SELECT  ")
					.append("       COUNT(1)  ").append("     FROM ").append("       a01" + imptemptable + " a  ")
					.append("     ) +  ").append("     (SELECT  ").append("       COUNT(1)  ").append("     FROM ")
					.append("       b01" + imptemptable + " a  ").append("     ) ").append("   )   ")
					.append("   FROM DUAL  ");
			List<Object> list = sess.createSQLQuery(dataExiestSql.toString()).list();

			if (list == null || list.size() < 1 || Long.parseLong(list.get(0).toString()) < 1) {
				throw new Exception("δ���ҵ���ҪУ������ݣ�");
			}
			String totalDataNum = list.get(0).toString();
			// 3.ѭ��У�����VerifyRule�����޸�У����Ϣ��д��У�������Ϣ��VerifyErrorList��
			// У�����ȡѡ���������У�鷽�����������õ�Ĭ�ϻ���У�鷽��
			/*
			 * String verifyRuleHql = "from VerifyRule b where vru007 = '1' "; if
			 * (!vsc001NullFlag) { verifyRuleHql = verifyRuleHql +
			 * " and   (vsc001 = :vsc001 and  exists (select 1 from VerifyScheme a where a.vsc001 = b.vsc001 and a.vsc003='1'))  "
			 * ; }
			 */
//			ע�͵� Ĭ�ϻ���У�鷽��
//			verifyRuleHql = verifyRuleHql
//					+ " or  exists (select 1 from VerifyScheme a where a.vsc001 = b.vsc001 and a.vsc003='1' and a.vsc007='1')";
			/*
			 * Query queryvr = sess.createQuery(verifyRuleHql); if (!vsc001NullFlag) {
			 * queryvr = queryvr.setString("vsc001", vsc001); }
			 * 
			 * @SuppressWarnings("unchecked") List<VerifyRule> vrList = queryvr.list(); if
			 * (vrList == null || vrList.isEmpty()) { throw new
			 * Exception("��У�鷽��û�����ö�ӦУ������û�л���У�鷽����"); }
			 */
			String[] ruleid = ruleids.split(",");

			StringBuffer insertSqlBf = new StringBuffer(
					"INSERT INTO VERIFY_ERROR_LIST ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010) ");
			String b01SqlTemp = VerificationSchemeConfPageModel.b01TabName + ".imprecordid = '" + imprecordid + "' "; // �����滻
																														// ��=
																														// :B0111��

			String a01ParamSql = ".A0000" + VerificationSchemeConfPageModel.EQUAL
					+ VerificationSchemeConfPageModel.A01_PARAM;
			String b01ParamSql = VerificationSchemeConfPageModel.b01TabName + ".B0111"
					+ VerificationSchemeConfPageModel.EQUAL + VerificationSchemeConfPageModel.B01_PARAM;
			String sqlVerify = null;

			if (vp != null) {
				vp.setTotalNum(Long.parseLong(ruleid.length + ""));
				sess.update(vp);
			}

			// ����У�飺������������ʽ������Ա���֤���ظ�����Ա:�쳣���ͣ�vru003��-����ǰ�쳣��9����У�����Ϊ��
			/*
			 * String moreA0148 = " select a01" + imptemptable +
			 * ".a0000 vel002,'9' vru003,'1' vel003,'"+imprecordid+"' vel004,"
			 * +vel005Bf+" vel005,'��������ʱ��������ʽ����Ա�Ƚ����֤�ظ���Ա��' vel006,'A01' vel007,'A0184' vel008,'' vel009,'"
			 * +userid+"' vel010 from a01" + imptemptable +
			 * " where EXISTS (SELECT 1 FROM a01 a WHERE a.A0184 = a01" + imptemptable +
			 * ".A0184 and a01" + imptemptable + ".A0184 is not null and length(a01" +
			 * imptemptable + ".A0184)>0) "; moreA0148 = insertSqlBf + "select " + vel001Bf
			 * + "  vel001,tt.* from (" + moreA0148 + ") tt";
			 * sess.createSQLQuery(moreA0148).executeUpdate();
			 */

			long currentNum = 0;
			// ѭ��У�����
			for (String ruid : ruleid) {
				VerifyRule vr = (VerifyRule) sess.get(VerifyRule.class, ruid);

				if (vp != null) {
					vp.setCurrentNum(++currentNum);
					vp.setProcessMsg("��" + totalDataNum + "�����ݡ�" + vp.getTotalNum() + "��У���������У���" + currentNum
							+ "������" + vr.getVru002() + "��");
					PhotosUtil.saveLog("��" + totalDataNum + "�����ݡ�" + vp.getTotalNum() + "��У���������У���" + currentNum
							+ "������" + vr.getVru002() + "��");
					PhotosUtil.saveLog("����ʼʱ��" + new Date().toString());
					sess.update(vp);
					sess.flush();
				}
				String vel006 = vr.getVru008();
				String vru002Id = vr.getVru002();
				if (DBUtil.getDBType() == DBType.MYSQL) {
					vel006 = "CAST('" + vel006 + "' AS CHAR CHARACTER SET utf8)";
				}

				String selectSqlTemp = "";
				String a01SqlTemp = vr.getVru006() + ".imprecordid = '" + imprecordid + "' ";// �����滻 ��xx.A0000 = :A0000��

				sqlVerify = vr.getVru009();
				if (StringUtil.isEmpty(sqlVerify) || "0".equals(vr.getVru007())) {
					continue;
				}

				// zxw ע����Դsql���� :B0000��:A0000 �����ִ�Сд��;����SQL���ֱ��������:A0000������:B0111������
				if (sqlVerify.toUpperCase().contains(VerificationSchemeConfPageModel.A01_PARAM)) {
					selectSqlTemp = " distinct " + vr.getVru006() + ".A0000 as vel002,'" + vr.getVru003()
							+ "' as vru003,'1' as vel003,'" + imprecordid + "' as vel004," + vel005Bf + " as vel005,'"
							+ vr.getVru008() + "' as vel006,'" + vr.getVru004() + "' as vel007,'" + vr.getVru005()
							+ "' as vel008,'" + vr.getVru001() + "' as vel009,'" + userid + "' as vel010 ";
					sqlVerify = sqlVerify.replace(vr.getVru006() + a01ParamSql, "1=1");
				} else if (sqlVerify.toUpperCase().contains(VerificationSchemeConfPageModel.B01_PARAM)) {
					selectSqlTemp = " distinct " + vr.getVru006() + ".B0111 as vel002,'" + vr.getVru003()
							+ "' as vru003,'2' as vel003,'" + imprecordid + "' as vel004," + vel005Bf + " as vel005,'"
							+ vr.getVru008() + "' as vel006,'" + vr.getVru004() + "' as vel007,'" + vr.getVru005()
							+ "' as vel008,'" + vr.getVru001() + "' as vel009,'" + userid + "' as vel010 ";
					sqlVerify = sqlVerify.replace(b01ParamSql, "1=1");
				} else {
					continue;
				}
				sqlVerify = sqlVerify.replace(VerificationSchemeConfPageModel.COUNT_SQL, selectSqlTemp);

				sqlVerify = sqlVerify.replace("_temp", imptemptable);

				sqlVerify = insertSqlBf + "select " + vel001Bf + "  vel001,tt.* from (" + sqlVerify + ") tt";
				PhotosUtil.saveLog("����ִ�е�sql--> " + sqlVerify);
				try {
//					if(sqlVerify.contains("��������-J-�����ϱ���淶")){
					CommonQueryBS.systemOut("����У��SQL--> " + sqlVerify);
//					}
					if (DBUtil.getDBType() == DBType.MYSQL) {

						if ("��ͥ��Ա��ν-ȱ�ٸ��׻�ĸ����Ϣ".equals(vru002Id)) {
							String sql = "create table A36f_" + suffix + " as select a0000 from A36" + imptemptable
									+ " WHERE a3604a='����' or  a3604a='�̸�' or  a3604a='����'";
							String sql1 = "create table A36m_" + suffix + " as select a0000 from A36" + imptemptable
									+ " WHERE a3604a='ĸ��' or  a3604a='��ĸ' or  a3604a='��ĸ'";

							sess.createSQLQuery(sql).executeUpdate();
							sess.createSQLQuery(sql1).executeUpdate();
							sqlVerify = sqlVerify.replace("A36f" + imptemptable, "A36f_" + suffix);
							sqlVerify = sqlVerify.replace("a36f" + imptemptable, "A36f_" + suffix);
							sqlVerify = sqlVerify.replace("A36m" + imptemptable, "A36m_" + suffix);
							sqlVerify = sqlVerify.replace("a36m" + imptemptable, "A36m_" + suffix);
						}
					} else {

						if ("��ͥ��Ա��ν-ȱ�ٸ��׻�ĸ����Ϣ".equals(vru002Id)) {

							String sql = "create table A36f_" + suffix + " as select a0000 from A36" + imptemptable
									+ " WHERE a3604a='����' or  a3604a='�̸�' or  a3604a='����'";
							String sql1 = "create table A36m_" + suffix + " as select a0000 from A36" + imptemptable
									+ " WHERE a3604a='ĸ��' or  a3604a='��ĸ' or  a3604a='��ĸ'";

							sess.createSQLQuery(sql).executeUpdate();
							sess.createSQLQuery(sql1).executeUpdate();
							sqlVerify = sqlVerify.replace("A36f" + imptemptable, "A36f_" + suffix);
							sqlVerify = sqlVerify.replace("a36f" + imptemptable, "A36f_" + suffix);
							sqlVerify = sqlVerify.replace("A36m" + imptemptable, "A36m_" + suffix);
							sqlVerify = sqlVerify.replace("a36m" + imptemptable, "A36m_" + suffix);
						}

					}
					sess.createSQLQuery(sqlVerify).executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
					String vru001 = vr.getVru001();
					String vru002 = vr.getVru002();
					String sql = " insert into special_verify values(" + vel001Bf + ",'" + vru001 + "','" + vru002
							+ "','2','" + userid + "','')";
					sess.createSQLQuery(sql).executeUpdate();
					continue;

				} finally {
					// ɾ����ʱ��

					String dropSql1 = "drop table  A36f_" + suffix + " ";
					String dropSql2 = "drop table A36m_" + suffix + " ";
					try {
						sess.createSQLQuery(dropSql1).executeUpdate();
						sess.createSQLQuery(dropSql2).executeUpdate();
					} catch (Exception e2) {

					}
				}
			}
			String checkFlag = "select count(1) from verify_error_list where vel005 = '2' and  vel004 ='" + imprecordid
					+ "' and vel009 is not null and vru003 != '9' and vel010='" + userid + "'";// �ų�
																								// ����У�飺������������ʽ������Ա���֤���ظ�����Ա:�쳣���ͣ�vru003��-����ǰ�쳣��9����У�����Ϊ��
			List checkFlagList = sess.createSQLQuery(checkFlag).list();
			if (checkFlagList != null && !checkFlagList.isEmpty()
					&& Long.parseLong(checkFlagList.get(0).toString()) > 0) {
				flag = false;
			}
			PhotosUtil.saveLog("���ϴ���ʼʱ��" + new Date().toString());
			dvbs.updatevelbyimpid(imprecordid, "2", userid);
			PhotosUtil.saveLog("���ϴ������ʱ��" + new Date().toString());

			// 6.��д�����¼���Ƿ�У�飬У�鷽��
			Imprecord imprecord = (Imprecord) sess.get(Imprecord.class, imprecordid);
			imprecord.setIsvirety("1");// ��У��
			// imprecord.setVsc001(vrList.get(0).getVsc001());// У�鷽��
			sess.update(imprecord);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("У�鷢���쳣��" + e.getMessage());
		}

		return flag;
	}

	/**
	 * 2017.04.26 yinl
	 * 
	 * @param b0111
	 */
	public void initErrcount1(String b0111) {
		HBSession sess = HBUtil.getHBSession();

		String delete = "delete from errcount where vel005= '1' and b0111 like '" + b0111 + "%'";
		// ������ԱУ����ͳ����Ϣ
		String insert1 = "INSERT INTO ERRCOUNT";
		insert1 = insert1 + " SELECT VEL002, A.A0101, 1 ETYPE, COUNT(1) ERRNUM, NULL, 1";
		insert1 = insert1 + " FROM VERIFY_ERROR_LIST C, A01 A";
		insert1 = insert1 + " WHERE VEL003 = 1";
		insert1 = insert1 + " AND A.A0000 = C.VEL002";
		insert1 = insert1 + " AND C.VEL005 = '1'";
		insert1 = insert1 + " AND C.VEL004 LIKE '" + b0111 + "%'";
		insert1 = insert1 + " AND C.vru003 != '9'";
		insert1 = insert1 + " GROUP BY VEL002, A.A0101";
		// �������У����ͳ����Ϣ
		String insert2 = "INSERT INTO ERRCOUNT";
		insert2 = insert2 + " SELECT VEL002, B.B0101 A0101, 2 ETYPE, COUNT(1) ERRNUM, VEL002, 1";
		insert2 = insert2 + " FROM VERIFY_ERROR_LIST C, B01 B";
		insert2 = insert2 + " WHERE VEL003 = 2";
		insert2 = insert2 + " AND B.B0111 = C.VEL002";
		insert2 = insert2 + " AND C.VEL005 = '1'";
		insert2 = insert2 + " AND C.VEL004 LIKE '" + b0111 + "%'";
		insert2 = insert2 + " AND C.vru003 != '9'";
		insert2 = insert2 + " GROUP BY VEL002, B.B0101";

		CommonQueryBS.systemOut("+++����У��+++" + delete);
		CommonQueryBS.systemOut("+++����У��+++" + insert1);
		CommonQueryBS.systemOut("+++����У��+++" + insert2);

		Connection conn = sess.getSession().connection();
		try {
			conn.setAutoCommit(false);
			Statement stm = conn.createStatement();
			stm.addBatch(delete);
			stm.addBatch(insert1);
			stm.addBatch(insert2);
			stm.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
			if (stm != null)
				stm.close();
			if (conn != null)
				conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// sess.createSQLQuery(delete).executeUpdate();
		// sess.createSQLQuery(insert1).executeUpdate();
		// sess.createSQLQuery(insert2).executeUpdate();
	}

	/**
	 * 2017.04.26 yinl
	 * 
	 * @param b0111
	 */
	public void updateErrcountAndList1(String b0111, String a0163, String userid) {
		HBSession sess = HBUtil.getHBSession();
		String a0163sql = "";
		if (a0163.equals("1")) {
			a0163sql = " AND A.A0255='1' and A.A0281='true'";
		}
		if (DBType.MYSQL == DBUtil.getDBType()) {
			// ����ERRCOUNT����Աͳ����Ϣ��B0111-mysql
			String update1 = "UPDATE VERIFY_ERROR_LIST E,";
			update1 = update1 + " (SELECT A.A0000, MIN(A.A0201B) A0201B";
			update1 = update1 + " FROM  A02 A";
			update1 = update1 + " WHERE A.A0201B LIKE '" + b0111 + "%'";
			update1 = update1 + a0163sql;
			update1 = update1 + " GROUP BY A.A0000) A";
			update1 = update1 + " SET E.VEL004 = A.A0201B";
			update1 = update1 + " WHERE A.A0000 = E.VEL002";
			update1 = update1 + " AND E.VEL005='4' and E.VEL010='" + userid + "'";
			CommonQueryBS.systemOut(new Date().toString() + "У��sql:" + update1);
			sess.createSQLQuery(update1).executeUpdate();

		} else if (DBType.ORACLE == DBUtil.getDBType()) {
			// ����ERRCOUNT����Աͳ����Ϣ��B0111-oracle
			String update1 = "UPDATE VERIFY_ERROR_LIST E SET VEL004 =";
			update1 = update1 + " (SELECT A.A0201B FROM A02 A WHERE A.A0000 = E.VEL002 " + a0163sql;
			update1 = update1 + " AND A.A0201B LIKE '" + b0111 + "%' AND ROWNUM = 1)";
			update1 = update1 + " WHERE  VEL005='4' AND E.VEL004 LIKE '" + b0111 + "%' and VEL010='" + userid
					+ "' and EXISTS( SELECT A.A0201B FROM A02 A WHERE A.A0000 = E.VEL002 " + a0163sql
					+ " AND A.A0201B LIKE '" + b0111 + "%'  AND ROWNUM = 1)";

			CommonQueryBS.systemOut(new Date().toString() + "У��sql:" + update1);

			Connection conn = sess.getSession().connection();
			try {
				conn.setAutoCommit(false);
				Statement stm = conn.createStatement();
				stm.addBatch(update1);
				stm.executeBatch();
				conn.commit();
				conn.setAutoCommit(true);
				if (stm != null)
					stm.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 2017.04.26 yinl
	 * 
	 * @param b0111
	 */
	public void initErrcount2(String b0111) {
		HBSession sess = HBUtil.getHBSession();
		Imprecord imprecord = (Imprecord) sess.get(Imprecord.class, b0111);
		String tmpt = imprecord.getImptemptable();
		String imprecordid = imprecord.getImprecordid();

		// ɾ��У��ͳ�Ʊ�����������
		String delete = "delete from errcount where vel005= '2' and b0111 like '" + b0111 + "%'";
		// ������ԱУ����ͳ����Ϣ
		String insert1 = "INSERT INTO ERRCOUNT";
		insert1 = insert1 + " SELECT VEL002, A.A0101, 1 ETYPE, COUNT(1) ERRNUM, '" + imprecordid + "', 2";
		insert1 = insert1 + " FROM VERIFY_ERROR_LIST C, A01" + tmpt + " A";
		insert1 = insert1 + " WHERE VEL003 = 1";
		insert1 = insert1 + " AND A.A0000 = C.VEL002";
		insert1 = insert1 + " AND C.VEL005 = '2'";
		insert1 = insert1 + " AND C.VEL004 = '" + imprecordid + "'";
		insert1 = insert1 + " AND C.VRU003 != '9'";
		insert1 = insert1 + " GROUP BY VEL002, A.A0101";
		// �������У����ͳ����Ϣ
		String insert2 = "INSERT INTO ERRCOUNT";
		insert2 = insert2 + " SELECT VEL002, B.B0101 A0101, 2 ETYPE, COUNT(1) ERRNUM, '" + imprecordid + "', 2";
		insert2 = insert2 + " FROM VERIFY_ERROR_LIST C, B01" + tmpt + " B";
		insert2 = insert2 + " WHERE VEL003 = 2";
		insert2 = insert2 + " AND B.B0111 = C.VEL002";
		insert2 = insert2 + " AND C.VEL005 = '2'";
		insert2 = insert2 + " AND C.VEL004 = '" + imprecordid + "'";
		insert2 = insert2 + " AND C.VRU003 != '9'";
		insert2 = insert2 + " GROUP BY VEL002, B.B0101";

		CommonQueryBS.systemOut("+++++++" + insert1);
		CommonQueryBS.systemOut("_______" + insert2);
		sess.createSQLQuery(delete).executeUpdate();
		sess.createSQLQuery(insert1).executeUpdate();
		sess.createSQLQuery(insert2).executeUpdate();
	}

	public static boolean dataVerifyByB0111VRu001(String b0111, String ruleids, VerifyProcess vp, UserVO user,
			String needverifyid) throws AppException {
		HBSession sess = HBUtil.getHBSession();
		boolean flag = true; // Ĭ��Ϊtrue ͨ��
		boolean vsc001NullFlag = false; // vsc001 �Ƿ�Ϊ�ձ��
		String vel001Bf = " sys_guid()  "; // ����VEL001������UUID���ĺ���
		String vel005Bf = " '1' "; // VEL005ҵ������:: 1-����У�飨�����¼���������2-����У��
		String userid = user.getId();
		if (DBUtil.getDBType() == DBType.MYSQL) {
			vel001Bf = " UUID() ";
			try {
				sess.createSQLQuery("SET GLOBAL group_concat_max_len = 102400").executeUpdate();
				sess.createSQLQuery("SET SESSION group_concat_max_len = 102400").executeUpdate();
			} catch (Exception e) {
				System.out.println("ִ�С�SET GLOBAL group_concat_max_len��ʧ�ܣ�");
			}
		}
		String vel004Bf = "";
		if (needverifyid.contains("'") || needverifyid.contains("select")) {
			vel004Bf = " in (" + needverifyid + ")";
		} else {
			vel004Bf = " like '" + needverifyid + "%'";
		}
		// 0.ɾ��֮ǰ������У�����ɵ�����
		try {
			DataVerifyBS dvbs = new DataVerifyBS();

			String delSqlErrorList = "delete from verify_error_list  where vel004 " + vel004Bf
					+ " and  vel005 ='1' and vel010='" + userid + "'";
			try {
				String deletesql = "delete from special_verify where sv004='1' and sv005='" + userid + "'";
				sess.createSQLQuery(deletesql).executeUpdate();
				sess.createSQLQuery(delSqlErrorList).executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String apsql = "";
			String dataExiestSql = "select count(1) from b01 where b0111 " + vel004Bf;
			List<Object> list = sess.createSQLQuery(dataExiestSql).list();

			if (list == null || list.size() < 1 || Long.parseLong(list.get(0).toString()) < 1) {
				throw new AppException("δ���ҵ���ҪУ������ݣ�");
			}
			String totalDataNum = list.get(0).toString();

			String[] ruleid = ruleids.split(",");

			StringBuffer insertSqlBf = new StringBuffer(
					"INSERT INTO VERIFY_ERROR_LIST ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010) ");
			String b01SqlTemp = " like '" + b0111 + "%' "; // �����滻 ��= :B0111��

			String a01ParamSql = ".A0000" + VerificationSchemeConfPageModel.EQUAL
					+ VerificationSchemeConfPageModel.A01_PARAM;
			String b01ParamSql = VerificationSchemeConfPageModel.EQUAL + VerificationSchemeConfPageModel.B01_PARAM;
			String sqlVerify = null;
			long currentNum = 0;
			if (vp != null) {
				vp.setTotalNum(Long.parseLong(ruleid.length + ""));
				sess.update(vp);
			}
			// ѭ��У�����
			for (String ruid : ruleid) {
				VerifyRule vr = (VerifyRule) sess.get(VerifyRule.class, ruid);
				if (vp != null) {
					vp.setCurrentNum(++currentNum);
					vp.setProcessMsg("��" + totalDataNum + "�����ݡ�" + vp.getTotalNum() + "��У���������У���" + currentNum
							+ "������" + vr.getVru002() + "��");
					PhotosUtil.saveLog("��" + totalDataNum + "�����ݡ�" + vp.getTotalNum() + "��У���������У���" + currentNum
							+ "������" + vr.getVru002() + "��");
					sess.update(vp);
					sess.flush();
				}

				String vel006 = vr.getVru008();
				if (DBUtil.getDBType() == DBType.MYSQL) {
					vel006 = "CAST('" + vel006 + "' AS CHAR CHARACTER SET utf8)";
				}

				String selectSqlTemp = "";
				String ap2sql = "";
				/*
				 * if(a0163.equals("1")){ //a01SqlTemp =
				 * a01SqlTemp+" and exists (select 1 from a01 v where v.a0000 = "+vr.getVru006()
				 * +".A0000 and v.a0163 ='1') "; //ap2sql = " and x.a0255='1'  "; ///2017.04.17
				 * yinl ͨ��a01����ˣ�a02�����������Ϊ�� ap2sql =
				 * " and  EXISTS(select 1 from a01 a01 where a01.A0000 = x.A0000 and a01.A0163 = '1')  "
				 * ;
				 * 
				 * }
				 */
				/**
				 * else{ ap2sql = " and x.A0201B is not null "; }
				 **/
//						String a01SqlTemp = " EXISTS (SELECT 1 FROM A02  x WHERE x.a0000 = "+vr.getVru006()+".A0000 AND x.a0201b LIKE  '"+b0111+"%' "+ap2sql+") ";//�����滻 ��xx.A0000 = :A0000��
				String a01SqlTemp = "";
				/*
				 * if(DBUtil.getDBType() == DBType.MYSQL){ a01SqlTemp = " "+vr.getVru006()
				 * +".A0000 IN (SELECT x.A0000 FROM A02  x WHERE  x.a0201b LIKE  '"+b0111+"%' "
				 * +ap2sql+") ";//�����滻 ��xx.A0000 = :A0000�� }else { a01SqlTemp =
				 * " EXISTS (SELECT 1 FROM A02  x WHERE x.a0000 = "+vr.getVru006()
				 * +".A0000 AND x.a0201b LIKE  '"+b0111+"%' "+ap2sql+") ";//�����滻 ��xx.A0000 =
				 * :A0000�� }
				 */
				sqlVerify = vr.getVru009();
				if (StringUtil.isEmpty(sqlVerify) || "0".equals(vr.getVru007())) {
					continue;
				}

				// zxw ע����Դsql���� :B0000��:A0000 �����ִ�Сд��
				if (sqlVerify.toUpperCase().contains(VerificationSchemeConfPageModel.B01_PARAM)) {
					selectSqlTemp = " distinct " + vr.getVru006() + ".B0111 as vel002,'" + vr.getVru003()
							+ "' as vru003,'2' as vel003," + vr.getVru006() + ".B0111 as vel004," + vel005Bf
							+ " as vel005,'" + vr.getVru008() + "' as vel006,'" + vr.getVru004() + "' as vel007,'"
							+ vr.getVru005() + "' as vel008,'" + vr.getVru001() + "' as vel009,'" + userid
							+ "' as vel010 ";
					sqlVerify = sqlVerify.replace(b01ParamSql, vel004Bf);
					// sqlVerify =sqlVerify.replace(vr.getVru006()+".B0111"+b01ParamSql, " 1=1 ");
				} else {
					continue;
				}
				sqlVerify = sqlVerify.replace(VerificationSchemeConfPageModel.COUNT_SQL, selectSqlTemp);

				// ȥ����ʱ����
				sqlVerify = sqlVerify.replace(VerificationSchemeConfPageModel.TEMP_PREFIX, "");

				sqlVerify = insertSqlBf + "select " + vel001Bf + "  vel001,tt.* from (" + sqlVerify + ") tt";
//						System.out.println("sql====>"+sqlVerify);
//						System.out.println("%  ====>"+currentNum/(float)vp.getTotalNum());
				// �����п������⴦��
				/*
				 * if(sqlVerify.contains("A1701")){ if(DBUtil.getDBType() == DBType.MYSQL){
				 * sqlVerify = sqlVerify.replace("A01_"+suffix+".A1701 is null",
				 * "replace(replace(A01_"+suffix+".A1701,char(13),''),char(10),'') is null");
				 * }else{ sqlVerify = sqlVerify.replace("A01_"+suffix+".A1701 is null",
				 * "replace(replace(A01_"+suffix+".A1701,chr(13),''),chr(10),'') is null"); } }
				 */
				PhotosUtil.saveLog("����ִ�е�sql--> " + sqlVerify);
				// System.out.println(sqlVerify);
				try {
//							if(sqlVerify.contains("��������-J-�����ϱ���淶")){
					CommonQueryBS.systemOut("������ϢУ��SQL--> " + sqlVerify);
//							}
					sess.createSQLQuery(sqlVerify).executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
					String vru001 = vr.getVru001();
					String vru002 = vr.getVru002();
					String sql = " insert into special_verify values(" + vel001Bf + ",'" + vru001 + "','" + vru002
							+ "','1','" + userid + "','')";
					sess.createSQLQuery(sql).executeUpdate();
					continue;
				}

			} // end for ѭ��У�����
			dvbs.updatevelbyid(needverifyid, "1", userid);

			//
			String checkFlag = "select count(1) from verify_error_list where vel005 = '1' and  vel004 " + vel004Bf
					+ " and vel010='" + userid + "'";
			List checkFlagList = sess.createSQLQuery(checkFlag).list();
			if (checkFlagList != null && !checkFlagList.isEmpty()
					&& Long.parseLong(checkFlagList.get(0).toString()) > 0) {
				flag = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("У�鷢���쳣��" + e.getMessage());
		}
		return flag;
	}

	public static boolean dataVerifyByRulePeroleid(String b0111, String ruleids, VerifyProcess vp, String peopleid,
			UserVO user) throws AppException {

//		return dataVerifyProcedureByB0111Vsc001(b0111,vsc001);  //���ô洢����

		HBSession sess = HBUtil.getHBSession();
		boolean flag = true; // Ĭ��Ϊtrue ͨ��
		boolean vsc001NullFlag = false; // vsc001 �Ƿ�Ϊ�ձ��
		String vel001Bf = " sys_guid()  "; // ����VEL001������UUID���ĺ���
		String vel005Bf = " '3' "; // VEL005ҵ������:: 1-����У�飨�����¼���������2-����У��
		String[] tables = { "A01", "A02", "A05", "A06", "A08", "A11", "A14", "A15", "A17", "A30", "A36", "A99Z1" };
		String suffix = String.valueOf((long) (Math.random() * 1000000000000000L));
		if (DBUtil.getDBType() == DBType.MYSQL) {
			vel001Bf = " UUID() ";
			try {
				sess.createSQLQuery("SET GLOBAL group_concat_max_len = 102400").executeUpdate();
				sess.createSQLQuery("SET SESSION group_concat_max_len = 102400").executeUpdate();
			} catch (Exception e) {
				System.out.println("ִ�С�SET GLOBAL group_concat_max_len��ʧ�ܣ�");
			}
		}
		String userid = user.getId();
		// 0.ɾ��֮ǰ������У�����ɵ�����
		try {
			DataVerifyBS dvbs = new DataVerifyBS();
			String delSqlErrorList = "delete from verify_error_list_mirror  where vel004 in (" + peopleid
					+ ") and  vel005 ='3' and vel010='" + userid + "'";
			String delSql = "delete from verify_error_list  where vel004 in (" + peopleid
					+ ") and  vel005 ='3' and vel010='" + userid + "'";
			try {
				// sess.createSQLQuery(delete11).executeUpdate();
				String deletesql = "delete from special_verify where sv004='3' and sv005='" + userid + "'";
				CommonQueryBS.systemOut(new Date().toString() + " У��sql" + deletesql);
				CommonQueryBS.systemOut(new Date().toString() + " У��sql" + delSqlErrorList);
				sess.createSQLQuery(delSql).executeUpdate();
				sess.createSQLQuery(deletesql).executeUpdate();
				sess.createSQLQuery(delSqlErrorList).executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String apsql = "";

			String dataExiestSql = "select count(1) from a01 where a0000 in (" + peopleid + ")";
			CommonQueryBS.systemOut(new Date().toString() + " У��sql" + dataExiestSql);
			List<Object> list = sess.createSQLQuery(dataExiestSql).list();

			if (list == null || list.size() < 1 || Long.parseLong(list.get(0).toString()) < 1) {
				throw new AppException("δ���ҵ���ҪУ������ݣ�");
			}
			String totalDataNum = list.get(0).toString();

			if (DBUtil.getDBType() == DBType.MYSQL) {
				sess.createSQLQuery("REPAIR TABLE A01SEARCHTEMP QUICK").executeUpdate();
			} else {
				sess.createSQLQuery(" alter index IDX_A01ST_SID rebuild ").executeUpdate();
			}
			for (String table : tables) {
				String CreateSQL = "";
				/**
				 * �����ı�׼�� ��Աְ������ְ��λ�ڵ��£� �Ƿ��������ְ���û��ж�
				 */
//				if(tables.equals("A02")){
//					CreateSQL="create table "+table+"_"+suffix+" as select * from "+table+" WHERE "+table+".a0000 in ("+peopleid+") and (x.a0255='1' or x.a0281='true') ";
//				}else{
				CreateSQL = "create table " + table + "_L_" + suffix + " as select * from " + table + " WHERE " + table
						+ ".a0000 in (" + peopleid + ")";

//				}
				CommonQueryBS.systemOut("�������sql" + CreateSQL);
				sess.createSQLQuery(CreateSQL).executeUpdate();
			}
			String b01_verify = "create table b01_L_" + suffix + " as select * from B01 WHERE B01.b0111 LIKE '" + b0111
					+ "%'";
			CommonQueryBS.systemOut("�����������sql" + b01_verify);
			sess.createSQLQuery(b01_verify).executeUpdate();
			ImpmodelThread.checkIndex("_L_" + suffix, tables);
			String[] ruleid = ruleids.split(",");
			StringBuffer insertSqlBf = new StringBuffer("INSERT INTO VERIFY_ERROR_LIST_mirror (vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010)");
			String b01SqlTemp = " like '" + b0111 + "%' "; // �����滻 ��= :B0111��

			String a01ParamSql = ".A0000" + VerificationSchemeConfPageModel.EQUAL
					+ VerificationSchemeConfPageModel.A01_PARAM;
			String b01ParamSql = VerificationSchemeConfPageModel.EQUAL + VerificationSchemeConfPageModel.B01_PARAM;
		    String sqlVerify = null;
			long currentNum = 0;
			if (vp != null) {
				vp.setTotalNum(Long.parseLong(ruleid.length + ""));
				sess.update(vp);
			}
			// ѭ��У�����
			for (String rule : ruleid) {
				VerifyRule vr = (VerifyRule) sess.get(VerifyRule.class, rule);
				String vru002Id = vr.getVru002();
				if (vp != null) {
					vp.setCurrentNum(++currentNum);
					vp.setProcessMsg("��" + totalDataNum + "�����ݡ�" + vp.getTotalNum() + "��У���������У���" + currentNum
							+ "������" + vr.getVru002() + "��");
					PhotosUtil.saveLog("��" + totalDataNum + "�����ݡ�" + vp.getTotalNum() + "��У���������У���" + currentNum
							+ "������" + vr.getVru002() + "��");
					PhotosUtil.saveLog("����ʼʱ��:" + new Date().toString());
					sess.update(vp);
					sess.flush();
				}

				String vel006 = vr.getVru008();
				if (DBUtil.getDBType() == DBType.MYSQL) {
					vel006 = "CAST('" + vel006 + "' AS CHAR CHARACTER SET utf8)";
				}

				String selectSqlTemp = "";
				String ap2sql = "";
				/*
				 * if(a0163.equals("1")){ //a01SqlTemp =
				 * a01SqlTemp+" and exists (select 1 from a01 v where v.a0000 = "+vr.getVru006()
				 * +".A0000 and v.a0163 ='1') "; //ap2sql = " and x.a0255='1'  "; ///2017.04.17
				 * yinl ͨ��a01����ˣ�a02�����������Ϊ�� ap2sql =
				 * " and  EXISTS(select 1 from a01 a01 where a01.A0000 = x.A0000 and a01.A0163 = '1')  "
				 * ;
				 * 
				 * }
				 */
				/**
				 * else{ ap2sql = " and x.A0201B is not null "; }
				 **/
//				String a01SqlTemp = " EXISTS (SELECT 1 FROM A02  x WHERE x.a0000 = "+vr.getVru006()+".A0000 AND x.a0201b LIKE  '"+b0111+"%' "+ap2sql+") ";//�����滻 ��xx.A0000 = :A0000��
				String a01SqlTemp = "";
				/*
				 * if(DBUtil.getDBType() == DBType.MYSQL){ a01SqlTemp = " "+vr.getVru006()
				 * +".A0000 IN (SELECT x.A0000 FROM A02  x WHERE  x.a0201b LIKE  '"+b0111+"%' "
				 * +ap2sql+") ";//�����滻 ��xx.A0000 = :A0000�� }else { a01SqlTemp =
				 * " EXISTS (SELECT 1 FROM A02  x WHERE x.a0000 = "+vr.getVru006()
				 * +".A0000 AND x.a0201b LIKE  '"+b0111+"%' "+ap2sql+") ";//�����滻 ��xx.A0000 =
				 * :A0000�� }
				 */
			    sqlVerify = vr.getVru009();
				if (StringUtil.isEmpty(sqlVerify) || "0".equals(vr.getVru007())) {
					continue;
				}
				// zxw ע����Դsql���� :B0000��:A0000 �����ִ�Сд��;����SQL���ֱ��������:A0000������:B0111������
				if (sqlVerify.toUpperCase().contains(VerificationSchemeConfPageModel.A01_PARAM)) {
					selectSqlTemp = " distinct " + vr.getVru006() + ".A0000 as vel002,'" + vr.getVru003()
							+ "' as vru003,'1' as vel003," + vr.getVru006() + ".A0000 as vel004," + vel005Bf
							+ " as vel005,'" + vr.getVru008() + "' as vel006,'" + vr.getVru004() + "' as vel007,'"
							+ vr.getVru005() + "' as vel008,'" + vr.getVru001() + "' as vel009,'" + userid
							+ "' as vel010 ";
					//sqlVerify = sqlVerify.replace(vr.getVru006()+a01ParamSql, a01SqlTemp);
					sqlVerify = sqlVerify.replace(vr.getVru006()+a01ParamSql, " 1=1 ");
				} else if (sqlVerify.toUpperCase().contains(VerificationSchemeConfPageModel.B01_PARAM)) {
					selectSqlTemp = " distinct " + vr.getVru006() + ".B0111 as vel002,'" + vr.getVru003()
							+ "' as vru003,'2' as vel003," + vr.getVru006() + ".B0111 as vel004," + vel005Bf
							+ " as vel005,'" + vr.getVru008() + "' as vel006,'" + vr.getVru004() + "' as vel007,'"
							+ vr.getVru005() + "' as vel008,'" + vr.getVru001() + "' as vel009,'" + userid
							+ "' as vel010 ";
					// sqlVerify =sqlVerify.replace( b01ParamSql, b01SqlTemp);
					sqlVerify =sqlVerify.replace(vr.getVru006()+".B0111"+b01ParamSql, " 1=1 ");
				} else {
					continue;
				}
				sqlVerify = sqlVerify.replace(VerificationSchemeConfPageModel.COUNT_SQL,selectSqlTemp);
				// ȥ����ʱ����
				sqlVerify = sqlVerify.replace(VerificationSchemeConfPageModel.TEMP_PREFIX,"_L_"+suffix);
				sqlVerify = insertSqlBf + "select "+vel001Bf+" vel001,tt.* from (" +sqlVerify +") tt";
				System.out.println("sql====>"+sqlVerify);
//				System.out.println("%  ====>"+currentNum/(float)vp.getTotalNum());
				// �����п������⴦��
				if (sqlVerify.contains("A1701")) {
					if (DBUtil.getDBType() == DBType.MYSQL) {
						sqlVerify = sqlVerify.replace("A01_L_"+suffix+".A1701 is null", "replace(replace(A01_L_"+suffix+".A1701,char(13),''),char(10),'') is null");
						//personSqlVerify = personSqlVerify.replace("A01_L_" + suffix + ".A1701 is null","replace(replace(A01_L_" + suffix + ".A1701,char(13),''),char(10),'') is null");
					} else {
						sqlVerify = sqlVerify.replace("A01_L_"+suffix+".A1701 is null","replace(replace(A01_L_"+suffix+".A1701,chr(13),''),chr(10),'') is null");
						//personSqlVerify = personSqlVerify.replace("A01_L_" + suffix + ".A1701 is null","replace(replace(A01_L_" + suffix + ".A1701,chr(13),''),chr(10),'') is null");
					}
				}
				if (DBUtil.getDBType() == DBType.MYSQL) {
					if ("ѧ����Ϣ-��ȫ����ѧ��".equals(vru002Id)) {
						try {
							String sql = "create table A08q_L_" + suffix
									+ " as select a0000,a0837 from A08 WHERE a0837 = '1'";

							sess.createSQLQuery(sql).executeUpdate();
							sess.createSQLQuery(
									"ALTER TABLE a08q_L_" + suffix + " add index a08q_L_" + suffix + "_1(a0000)")
									.executeUpdate();
							sqlVerify=sqlVerify.replace("A08_L_"+ suffix, "A08q_L_"+ suffix);
							//personSqlVerify = personSqlVerify.replace("A08_L_" + suffix, "A08q_L_" + suffix);
							sqlVerify=sqlVerify.replace("a08_L_"+ suffix, "A08q_L_"+ suffix);
							//personSqlVerify = personSqlVerify.replace("a08_L_" + suffix, "A08q_L_" + suffix);
						} catch (Exception e) {
							CommonQueryBS.sysOutRtn("a08q_L_��ʱ����ʧ��");
						}

					}
					if ("��ͥ��Ա��ν-ȱ�ٸ��׻�ĸ����Ϣ".equals(vru002Id)) {
						try {
							String sql = "create table A36f_L_" + suffix
									+ " as select a0000 from A36 WHERE a3604a='����' or  a3604a='�̸�' or  a3604a='����'";
							String sql1 = "create table A36m_L_" + suffix
									+ " as select a0000 from A36 WHERE a3604a='ĸ��' or  a3604a='��ĸ' or  a3604a='��ĸ'";

							sess.createSQLQuery(sql).executeUpdate();
							sess.createSQLQuery(sql1).executeUpdate();
							sqlVerify=sqlVerify.replace("A36f_temp", "A36f_L_"+ suffix);
							//personSqlVerify = personSqlVerify.replace("A36f_temp", "A36f_L_" + suffix);
							sqlVerify=sqlVerify.replace("a36f_temp", "A36f_L_"+ suffix);
							//personSqlVerify = personSqlVerify.replace("a36f_temp", "A36f_L_" + suffix);
							sqlVerify=sqlVerify.replace("A36m_temp", "A36m_L_"+ suffix);
							//personSqlVerify = personSqlVerify.replace("A36m_temp", "A36m_L_" + suffix);
							sqlVerify=sqlVerify.replace("a36m_temp", "A36m_L_"+ suffix);
							//personSqlVerify = personSqlVerify.replace("a36m_temp", "A36m_L_" + suffix);
						} catch (Exception e) {
							CommonQueryBS.sysOutRtn("A36f_L_��A36m_L_��ʱ����ʧ��");
						}

					}
				} else {
					if ("ѧ����Ϣ-��ȫ����ѧ��".equals(vru002Id)) {
						try {
							String sql = "create table A08q_L_" + suffix + " as select a0000 from A08 WHERE a0837 = '1'";
							sess.createSQLQuery(sql).executeUpdate();

							sess.createSQLQuery("create index A08q_L_" + suffix + "_1 on a08q_L_" + suffix + " (a0000)")
									.executeUpdate();
							sqlVerify=sqlVerify.replace("A08_L_"+ suffix, "A08q_L_"+ suffix);
							//personSqlVerify = personSqlVerify.replace("A08_L_" + suffix, "A08q_L_" + suffix);
							sqlVerify=sqlVerify.replace("a08_L_"+ suffix, "A08q_L_"+ suffix);
							//personSqlVerify = personSqlVerify.replace("a08_L_" + suffix, "A08q_L_" + suffix);
						} catch (Exception e) {
							CommonQueryBS.sysOutRtn("A08q_L_��ʱ����ʧ��");
						}

					}
					if ("��ͥ��Ա��ν-ȱ�ٸ��׻�ĸ����Ϣ".equals(vru002Id)) {
						try {
							String sql = "create table A36f_L_" + suffix
									+ " as select a0000 from A36 WHERE a3604a='����' or  a3604a='�̸�' or  a3604a='����'";
							String sql1 = "create table A36m_L_" + suffix
									+ " as select a0000 from A36 WHERE a3604a='ĸ��' or  a3604a='��ĸ' or  a3604a='��ĸ'";

							sess.createSQLQuery(sql).executeUpdate();
							sess.createSQLQuery(sql1).executeUpdate();
							sqlVerify=sqlVerify.replace("A36f_temp", "A36f_L_"+ suffix);
							//personSqlVerify = personSqlVerify.replace("A36f_temp", "A36f_L_" + suffix);
							sqlVerify=sqlVerify.replace("a36f_temp", "A36f_L_"+ suffix);
							//personSqlVerify = personSqlVerify.replace("a36f_temp", "A36f_L_" + suffix);
							sqlVerify=sqlVerify.replace("A36m_temp", "A36m_L_"+ suffix);
							//personSqlVerify = personSqlVerify.replace("A36m_temp", "A36m_L_" + suffix);
							sqlVerify=sqlVerify.replace("a36m_temp", "A36m_L_"+ suffix);
							//personSqlVerify = personSqlVerify.replace("a36m_temp", "A36m_L_" + suffix);
						} catch (Exception e) {
							CommonQueryBS.sysOutRtn("A36f_L_��A36m_L_��ʱ����ʧ��");
						}

					}

				}

				PhotosUtil.saveLog("����ִ�е�sql--> "+sqlVerify);
				//PhotosUtil.saveLog("����ִ�е�sql--> " + personSqlVerify);
				// System.out.println(sqlVerify);
				try {
//					if(sqlVerify.contains("��������-J-�����ϱ���淶")){
					CommonQueryBS.systemOut("��Ա��ϢУ��SQL--> "+sqlVerify);
					//CommonQueryBS.systemOut("��Ա��ϢУ��SQL--> " + personSqlVerify);
//					}
					sess.createSQLQuery(sqlVerify).executeUpdate();
					//sess.createSQLQuery(personSqlVerify).executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
					String vru001 = vr.getVru001();
					String vru002 = vr.getVru002();
					String sql = " insert into special_verify values(" + vel001Bf + ",'" + vru001 + "','" + vru002
							+ "','3','" + userid + "','')";
					sess.createSQLQuery(sql).executeUpdate();
					continue;
				}
			} // end for ѭ��У�����
			dvbs.updatevelbypid(suffix, "3", userid);

			//
			String checkFlag = "select count(1) from verify_error_list where vel005 = '3' and vel004 in ("+peopleid+") and vel010='"+userid+"'";
			//String checkPersonFlag = "select count(1) from verify_error_list_person where vel005 = '3' and  vel004 in ("+ peopleid + ")  and vel010='" + userid + "'";
			List checkFlagList = sess.createSQLQuery(checkFlag).list();
			//List checkPersonFlagList = sess.createSQLQuery(checkPersonFlag).list();
			if(checkFlagList!=null && !checkFlagList.isEmpty() && Long.parseLong(checkFlagList.get(0).toString())>0){
				flag = false;
			}
//			if (checkPersonFlagList != null && !checkPersonFlagList.isEmpty()
//					&& Long.parseLong(checkPersonFlagList.get(0).toString()) > 0) {
//				flag = false;
//			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("У�鷢���쳣��" + e.getMessage());
		} finally {
			
		}
		String dropb01 = "drop table B01_L_"+suffix+" ";
		// ɾ����ʱ��
		for (String table : tables) {
			String DropSQL = "drop table " + table + "_L_" + suffix;
			String dropSql = "drop table A08q_L_" + suffix + " ";
			String dropSql1 = "drop table  A36f_L_" + suffix + " ";
			String dropSql2 = "drop table A36m_L_" + suffix + " ";
			try {
				sess.createSQLQuery(DropSQL).executeUpdate();
				sess.createSQLQuery(dropSql).executeUpdate();
				sess.createSQLQuery(dropSql1).executeUpdate();
				sess.createSQLQuery(dropSql2).executeUpdate();
			} catch (Exception e2) {

			}
		}
		sess.createSQLQuery(dropb01).executeUpdate();
		return flag;

	}

	public void updatevelbyb0111(String b0111, String bstype, String userid) {
		String vel001Bf = " sys_guid()  ";
		if (DBUtil.getDBType() == DBType.MYSQL) {
			vel001Bf = " UUID() ";
		}
		HBSession sess = HBUtil.getHBSession();
		sess.createSQLQuery("delete from verify_error_list_mirror where vel002='-1'").executeUpdate();
		String deleteSql = "delete from verify_error_list where vel010='" + userid + "'";//�����ʷУ�˼�¼
		sess.createSQLQuery(deleteSql).executeUpdate();
		String insersql = "insert into verify_error_list ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010)  select "
				+ vel001Bf + " vel001,tt.* from "
				+ " (select vel002,vru003,vel003,vel004,vel005,replace(to_char(wm_concat(vel006)),',','��'),vel007,vel008,vel009,vel010 from "
				+ " (select  vel002,'5' vru003,vel003,vel004,vel005,concat((case when vru003='1' then '����:' else '��ʾ:' end ),to_char(wm_concat(vel006))) vel006,'' vel007,''vel008,'' vel009,vel010"
				+ " from verify_error_list_mirror where vel005 = '" + bstype + "' and vel004 = '" + b0111
				+ "' and vel010='" + userid
				+ "' and vel001 not in (SELECT a.vel001 from verify_error_list_mirror a ,verify_rule_detail b where b.VEL002=a.VEL002 and b.VEL009=a.VEL009 and a.vel010=b.vel010 and a.vel010='"
				+ userid + "') group by vel002,vel003,vel004,vel005,vel010,vru003 " + "  ) vel"
				+ " group by vel002,vru003,vel003,vel004,vel005,vel007,vel008,vel009,vel010) tt";
		if (DBUtil.getDBType() == DBType.MYSQL) {
			insersql = insersql.replace("to_char(wm_concat(vel006))", "group_concat(vel006  order by vel006 desc)");
		}

		CommonQueryBS.systemOut(new Date().toString() + "У��sql:" + insersql);
		sess.createSQLQuery(insersql).executeUpdate();
		String deletesql = "delete from verify_error_list where vel004 = '" + b0111 + "' and vru003<>'5' and vel005='"
				+ bstype + "' and vel010='" + userid + "'";
		CommonQueryBS.systemOut(new Date().toString() + "У��sql:" + deletesql);
		sess.createSQLQuery(deletesql).executeUpdate();

	}

	public void updatevelbyimpid(String imprecordid, String bstype, String userid) {

		String vel001Bf = " sys_guid()  ";
		if (DBUtil.getDBType() == DBType.MYSQL) {
			vel001Bf = " UUID() ";
		}
		long startTime1 = System.currentTimeMillis(); // ��ȡ��ʼʱ��
		String insersql = "";
		insersql = "insert into verify_error_list ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010)  select "
				+ vel001Bf + " vel001,tt.* from"
				+ "(select vel002,vru003,vel003,vel004,vel005,replace(to_char(wm_concat(vel006)),',','��'),vel007,vel008,vel009,vel010 from "
				+ "(select  vel002,'5' vru003,vel003,vel004,vel005,concat((case when vru003='1' then '����:' else '��ʾ:' end ),to_char(wm_concat(vel006))) vel006,'' vel007,''vel008,'' vel009,vel010"
				+ " from verify_error_list  where vel005 = '" + bstype + "' and vel004 = '" + imprecordid
				+ "' and vel010='" + userid + "' group by vel002,vel003,vel004,vel005,vel010,vru003" + ") vel"
				+ " group by vel002,vru003,vel003,vel004,vel005,vel007,vel008,vel009,vel010) tt";
		if (DBUtil.getDBType() == DBType.MYSQL) {
			insersql = insersql.replace("to_char(wm_concat(vel006))", "group_concat(vel006 order by vel006 desc)");
		}
		HBSession sess = HBUtil.getHBSession();
		sess.createSQLQuery(insersql).executeUpdate();
		long endTime1 = System.currentTimeMillis(); // ��ȡ����ʱ��
		System.out.println("����ʱ��" + (endTime1 - startTime1) + "ms"); // �����������ʱ��
		System.out.println("����sql" + insersql);
		long startTime2 = System.currentTimeMillis(); // ��ȡ��ʼʱ��
		String deletesql = "delete from verify_error_list where vel004 = '" + imprecordid
				+ "' and (vru003<>'5' and vru003<>'9')and vel005='" + bstype + "' and vel010='" + userid + "'";
		sess.createSQLQuery(deletesql).executeUpdate();
		long endTime2 = System.currentTimeMillis(); // ��ȡ����ʱ��
		System.out.println("ɾ��ʱ��" + (endTime2 - startTime2) + "ms"); // �����������ʱ��
		System.out.println("ɾ��sql" + deletesql);

	}

	public void updatevelbyid(String groupid, String bstype, String userid) {
		String vel001Bf = " sys_guid()  ";
		if (DBUtil.getDBType() == DBType.MYSQL) {
			vel001Bf = " UUID() ";
		}
		String vel004Bf = "";
		if (groupid.contains("'") || groupid.contains("select")) {
			vel004Bf = " in (" + groupid + ")";
		} else {
			vel004Bf = " like '" + groupid + "%'";
		}

		String insersql = "insert into verify_error_list ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010)  select "
				+ vel001Bf + " vel001,tt.* from "
				+ " (select vel002,vru003,vel003,vel004,vel005,replace(to_char(wm_concat(vel006)),',','��'),vel007,vel008,vel009,vel010 from "
				+ " (select  vel002,'5' vru003,vel003,vel004,vel005,concat((case when vru003='1' then '����:' else '��ʾ:' end ),to_char(wm_concat(vel006))) vel006,'' vel007,''vel008,'' vel009,vel010"
				+ " from verify_error_list where vel005 = '" + bstype + "' and vel004 " + vel004Bf + " and vel010='"
				+ userid + "' group by vel002,vel003,vel004,vel005,vel010,vru003" + " ) vel"
				+ " group by vel002,vru003,vel003,vel004,vel005,vel007,vel008,vel009,vel010) tt";
		if (DBUtil.getDBType() == DBType.MYSQL) {
			insersql = insersql.replace("to_char(wm_concat(vel006))", "group_concat(vel006  order by vel006 desc)");
		}
		HBSession sess = HBUtil.getHBSession();
		sess.createSQLQuery(insersql).executeUpdate();
		String deletesql = "delete from verify_error_list where vel004 " + vel004Bf + " and vru003<>'5' and vel005='"
				+ bstype + "' and vel010='" + userid + "'";
		sess.createSQLQuery(deletesql).executeUpdate();
	}

	public void updatevelbypid(String suffix, String bstype, String userid) {
		String vel001Bf = " sys_guid()  ";
		if (DBUtil.getDBType() == DBType.MYSQL) {
			vel001Bf = " UUID() ";
		}
		String insersql = "insert into verify_error_list ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010)  select "
				+ vel001Bf + " vel001,tt.* from"
				+ " (select vel002,vru003,vel003,vel004,vel005,replace(to_char(wm_concat(vel006)),',','��'),vel007,vel008,vel009,vel010 from "
				+ " (select  vel002,'5' vru003,vel003,vel004,vel005,concat((case when vru003='1' then '����:' else '��ʾ:' end ),to_char(wm_concat(vel006))) vel006,'' vel007,''vel008,'' vel009,vel010"
				+ " from verify_error_list_mirror  where vel005 = '" + bstype
				+ "' and vel004 in (select a0000 from a01_L_" + suffix + ") and vel010='" + userid+"' and vel001 not in (SELECT a.vel001 from verify_error_list_mirror a ,verify_rule_detail b where b.VEL002=a.VEL002 and b.VEL009=a.VEL009) "
				+ " group by vel002,vel003,vel004,vel005,vel010,vru003" + " ) vel"
				+ "  group by vel002,vru003,vel003,vel004,vel005,vel007,vel008,vel009,vel010) tt";
		if (DBUtil.getDBType() == DBType.MYSQL) {
			insersql = insersql.replace("to_char(wm_concat(vel006))", "group_concat(vel006  order by vel006 desc)");
		}
		HBSession sess = HBUtil.getHBSession();
		CommonQueryBS.systemOut(new Date().toString() + "У��sql:" + insersql);
		sess.createSQLQuery(insersql).executeUpdate();
		String deletesql = "delete from verify_error_list where vel004 in (select a0000 from a01_L_" + suffix
				+ ") and vru003<>'5' and vel005='" + bstype + "' and vel010='" + userid + "'";
		CommonQueryBS.systemOut(new Date().toString() + "У��sql:" + deletesql);
		sess.createSQLQuery(deletesql).executeUpdate();

	}

	// ʵ�ֶ�����У������ͳ�ƹ���
	private void tjCheckInfo(String[] tables, List<VerifyRule> vriList, String suffix, String b0111, String userid,
			String VSC001) throws AppException {
		/**
		 * ����ֻ�ܻ���У�˲��ܽ���,��������
		 */
		// �����û�ɾ��
		HBUtil.executeUpdate("delete from CHECK_TJ where userid = '" + userid + "' ");
		// ���չ���ɾ��
//		HBUtil.executeUpdate("delete from CHECK_TJ where userid = '"+userid+"' and check_vsc001='"+VSC001+"' ");
		// ע�⣺����У�˱��洦��Ĺؼ�λ�ã���Ϊ����ֻ�����ڻ���ʹ�á�ע���޸���߶�Ӧ��У�˷����Ĵ���
		if (!"240AC5C1EABB4729BA04E47062B40D94".equals(VSC001)) {
			return;
		}
		SimpleDateFormat aDate = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��");
		String date = aDate.format(new Date());
		HBSession sess = HBUtil.getHBSession();
		Connection connection = sess.connection();
		PreparedStatement prestate = null;
		CommonQueryBS.systemOut("��ʼ����У����Ϣͳ��...");
		try {
			connection.setAutoCommit(false);
			// ��ȡ��ѯ������
			HashMap<String, String> map = new HashMap<String, String>();
			for (String table : tables) {
				map.put(table.toUpperCase(), HBUtil.getValueFromTab("count(1)", table + "_" + suffix, "1=1"));
			}
			map.put("B01", HBUtil.getValueFromTab("count(1)", "B01_" + suffix, "1=1"));

			prestate = connection.prepareStatement("insert into CHECK_TJ(" + "CHECKID,"// UUID1
					+ "USERID,"// �����û�2
					+ "CODE_TABLE,"// ��Ϣ��3
					+ "COL_CODE,"// ��Ϣ��4
					+ "CHECK_ALL,"// У������5
					+ "CHECK_NULL,"// У�˿�6
					+ "CHECK_ERROR,"// У�˴���7
					+ "CHECK_B0111,"// У�˷�Χ8
					+ "CHECK_PHOTO,"// ��Ƭ����9
					+ "CHECK_TIME,"// ʱ��10
					+ "CHECK_VRU002,"// ��������11
					+ "CHECK_VSC001,"// У�˷���12
					+ "CODE_TABLE_NAME,"// ��Ϣ��3
					+ "COL_NAME"// ��Ϣ��4
					+ ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			String table_code, num;
			Map<String, String> columnAndName = getColumnAndName();
			CommonQueryBS.systemOut("����ͳ��У�˹���������" + vriList.size());
			// ѭ��У�����
			for (VerifyRule vr : vriList) {
				prestate.setObject(1, UUID.randomUUID().toString().replaceAll("-", ""));
				prestate.setObject(2, userid);
				table_code = vr.getVru004().toUpperCase();
				// ����Ϣ���뵽У����Ϣ��
				prestate.setObject(3, table_code);
				// ����Ϣ���뵽У����Ϣ��
				prestate.setObject(4, vr.getVru005().toUpperCase());
				prestate.setObject(5, map.get(table_code));
				num = HBUtil.getValueFromTab("count(1)", "verify_error_list",
						"vel009='" + vr.getVru001() + "' and vel010='" + userid + "' and vel004='" + b0111 + "'");
				if (vr.getVru008().contains("-�ֶ�Ϊ��")) {// �ж��Ƿ�Ϊ - ��Ϣ��Ϊ������
					prestate.setObject(6, num);
					prestate.setObject(7, "0");
				} else {// ����Ϊ - ��Ϣ���������
					prestate.setObject(6, "0");
					prestate.setObject(7, num);
				}
				prestate.setObject(8, b0111);
				if ("A57".equals(table_code)) {// �ж���Ƭ����
					prestate.setObject(9, "1");
				} else {
					prestate.setObject(9, "0");
				}
				;
				prestate.setObject(10, date);
				prestate.setObject(11, vr.getVru002());
				prestate.setObject(12, vr.getVsc001());
				prestate.setObject(13, columnAndName.get(table_code));
				prestate.setObject(14, columnAndName.get(vr.getVru005().toUpperCase()));
				prestate.addBatch();
			}
			prestate.executeBatch();
			connection.commit();
			prestate.clearBatch();
			connection.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
		} /*
			 * finally{ //�ر���Ƭ try { prestate.close(); } catch (SQLException e) {
			 * e.printStackTrace(); } try { connection.close(); } catch (SQLException e) {
			 * e.printStackTrace(); } }
			 */
		CommonQueryBS.systemOut("У����Ϣͳ��...����!");
	}

	public Map<String, String> getColumnAndName() {
		CommQuery commQuery = new CommQuery();
		List<HashMap<String, Object>> listBySQL;
		Map<String, String> map = new LinkedHashMap<String, String>();
		try {
			listBySQL = commQuery.getListBySQL("select" + " TABLE_NAME,"// ����
					+ " COL_CODE,"// A0101
					+ " CODE_TABLE,"// A01
					+ " CODE_NAME"// ���������Ϣ��
					+ " from check_code where isok='1' order by orderby ");
			HashMap<String, Object> hashMap;
			String table_code, col_code;
			for (int i = 0, num = listBySQL.size(); i < num; i++) {
				hashMap = listBySQL.get(i);
				table_code = hashMap.get("code_table") + "";
				col_code = hashMap.get("code_name") + "";
				map.put(table_code, col_code);
				table_code = hashMap.get("col_code") + "";
				col_code = hashMap.get("table_name") + "";
				map.put(table_code, col_code);
			}
		} catch (AppException e) {
			e.printStackTrace();
			CommonQueryBS.systemOut("��ȡ�ֶ���Ϣʱ������");
		}
		return map;
	}

}