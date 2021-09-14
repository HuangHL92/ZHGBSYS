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
	 * 获取有效校验方案，优先基础默认方案
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
	 * 默认取校验方案
	 * 
	 * @param imprecordid
	 * @return
	 * @throws SQLException
	 * @throws AppException
	 */
	public static boolean dataVerifyByImprecordidNew(String imprecordid) throws Exception {
		String vsc001 = getBaseVerifyScheme();
		if (StringUtil.isEmpty(vsc001)) {
			throw new AppException("未查找到有效校验方案！");
		}
		return dataVerifyByImprecordidVsc001(imprecordid, vsc001);
	}

	/**
	 * 通过存储过程校验导入信息
	 * 
	 * @param imprecordid
	 * @param vsc001
	 * @return
	 * @throws AppException
	 */
	public static boolean dataVerifyProcedureByImprecordidVsc001(String imprecordid, String vsc001)
			throws AppException {

		boolean flag = true; // 默认为true 通过

		int rFlag = 0; // -1 校验程序异常 ；0 校验未通过； 1 校验通过
		String errorCode = "";
		String msg = "";

		CommonQueryBS.systemOut("     ------>" + imprecordid + "@" + vsc001);
		CommonQueryBS.systemOut("校验开始------>" + DateUtil.dateToString(new Date(), "yyyyMMdd hh:mm:ss"));
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
			CommonQueryBS.systemOut("    校验信息-->" + msg);
			if (cstmt != null)
				cstmt.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("数据库处理异常！", e);
		}
		CommonQueryBS.systemOut("校验结束------>" + DateUtil.dateToString(new Date(), "yyyyMMdd hh:mm:ss"));
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
	 * 通过存储过程校验导入信息
	 * 
	 * @param imprecordid
	 * @param vsc001
	 * @return
	 * @throws AppException
	 */
	public static boolean dataVerifyProcedureByB0111Vsc001(String b0111, String vsc001) throws AppException {

		boolean flag = true; // 默认为true 通过

		int rFlag = 0; // -1 校验程序异常 ；0 校验未通过； 1 校验通过
		String errorCode = "";
		String msg = "";

		CommonQueryBS.systemOut("     ------>" + b0111 + "@" + vsc001);
		CommonQueryBS.systemOut("校验开始------>" + DateUtil.dateToString(new Date(), "yyyyMMdd hh:mm:ss"));
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
			CommonQueryBS.systemOut("    校验信息-->" + msg);
			if (cstmt != null)
				cstmt.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("数据库处理异常！", e);
		}
		CommonQueryBS.systemOut("校验结束------>" + DateUtil.dateToString(new Date(), "yyyyMMdd hh:mm:ss"));
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
	 * 根据导入批次ID,校验方案ID 校验该批次导入信息
	 * 
	 * 0.删除之前该批次校验生成的数据 1.查询校验方案中需要校验的表信息 2.查询出该批次中所需要校验表数据
	 * 3.循环人员表信息,循环校验规则（VerifyRule）：修改校验信息，写入校验错误信息（VerifyError,VerifyErrorDetail）
	 * 4.删除 没有对应 verify_error_datail信息的verify_error表 5.回写导入记录表：是否校验，校验方案
	 * 
	 * @param imprecordid 导入批次ID
	 * @param vsc001      校验方案ID
	 * @throws SQLException
	 * @throws AppException
	 */
	public static boolean dataVerifyByImprecordidVsc001(String imprecordid, String vsc001, VerifyProcess vp)
			throws Exception {

		// return dataVerifyProcedureByImprecordidVsc001(imprecordid,vsc001); //存储过程

		boolean flag = true; // 默认为true 通过
		boolean vsc001NullFlag = false; // vsc001 是否为空标记
		String vel001Bf = " sys_guid()  "; // 生成VEL001主键（UUID）的函数
		String vel005Bf = " '2' "; // VEL005业务类型:: 1-机构校验（包含下级机构）；2-导入校验

		if (DBUtil.getDBType() == DBType.MYSQL) {
			vel001Bf = " UUID() ";
		}

		HBSession sess = HBUtil.getHBSession();

		if (StringUtil.isEmpty(vsc001)) {
			vsc001 = "";
			vsc001NullFlag = true;
		}

		// 1.删除之前该批次校验生成的数据
		try {

			String delSqlErrorList = "delete from verify_error_list  where vel004 ='" + imprecordid
					+ "' and  vel005 ='2' ";
			try {
				sess.createSQLQuery(delSqlErrorList).executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 2.查询需要校验的数据
			StringBuffer dataExiestSql = new StringBuffer(" SELECT  ( ").append("     (SELECT  ")
					.append("       COUNT(1)  ").append("     FROM ").append("       a01_temp a  ")
					.append("     WHERE imprecordid = '" + imprecordid + "') +  ").append("     (SELECT  ")
					.append("       COUNT(1)  ").append("     FROM ").append("       b01_temp a  ")
					.append("     WHERE imprecordid = '" + imprecordid + "') ").append("   )   ")
					.append("   FROM DUAL  ");
			List<Object> list = sess.createSQLQuery(dataExiestSql.toString()).list();

			if (list == null || list.size() < 1 || Long.parseLong(list.get(0).toString()) < 1) {
				throw new Exception("未查找到需要校验的数据！");
			}
			String totalDataNum = list.get(0).toString();
			// 3.循环校验规则（VerifyRule）：修改校验信息，写入校验错误信息（VerifyErrorList）
			// 校验规则取选择的已启用校验方案和所有启用的默认基础校验方案
			String verifyRuleHql = "from VerifyRule b where vru007 = '1' ";
			if (!vsc001NullFlag) {
				verifyRuleHql = verifyRuleHql
						+ " and   (vsc001 = :vsc001 and  exists (select 1 from VerifyScheme a where a.vsc001 = b.vsc001 and a.vsc003='1'))  ";
			}
//			注释掉 默认基础校验方案
//			verifyRuleHql = verifyRuleHql
//					+ " or  exists (select 1 from VerifyScheme a where a.vsc001 = b.vsc001 and a.vsc003='1' and a.vsc007='1')";
			Query queryvr = sess.createQuery(verifyRuleHql);
			if (!vsc001NullFlag) {
				queryvr = queryvr.setString("vsc001", vsc001);
			}
			@SuppressWarnings("unchecked")
			List<VerifyRule> vrList = queryvr.list();
			if (vrList == null || vrList.isEmpty()) {
				throw new Exception("该校验方案没有配置对应校验规则或没有基础校验方案！");
			}

			StringBuffer insertSqlBf = new StringBuffer(
					"INSERT INTO VERIFY_ERROR_LIST ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010) ");
			String b01SqlTemp = VerificationSchemeConfPageModel.b01TabName + ".imprecordid = '" + imprecordid + "' "; // 用于替换
																														// 【=
																														// :B0111】

			String a01ParamSql = ".A0000" + VerificationSchemeConfPageModel.EQUAL
					+ VerificationSchemeConfPageModel.A01_PARAM;
			String b01ParamSql = VerificationSchemeConfPageModel.b01TabName + ".B0111"
					+ VerificationSchemeConfPageModel.EQUAL + VerificationSchemeConfPageModel.B01_PARAM;
			String sqlVerify = null;

			if (vp != null) {
				vp.setTotalNum(Long.parseLong(vrList.size() + ""));
				sess.update(vp);
			}

			// 特殊校验：导入数据与正式库中人员身份证号重复的人员:异常类型（vru003）-导入前异常（9），校验规则为空
			String moreA0148 = " select a01_temp.a0000 vel002,'9' vru003,'1' vel003,'" + imprecordid + "' vel004,"
					+ vel005Bf
					+ " vel005,'待导入临时数据与正式库人员比较身份证重复人员！' vel006,'A01' vel007,'A0184' vel008,'' vel009,'' vel010 from a01_temp where imprecordid='"
					+ imprecordid
					+ "' and EXISTS (SELECT 1 FROM a01 a WHERE a.A0184 = a01_temp.A0184 and a01_temp.A0184 is not null and length(a01_temp.A0184)>0) ";
			moreA0148 = insertSqlBf + "select " + vel001Bf + "  vel001,tt.* from (" + moreA0148 + ") tt";
			CommonQueryBS.systemOut("xx--->" + moreA0148);
			sess.createSQLQuery(moreA0148).executeUpdate();

			long currentNum = 0;
			// 循环校验规则
			for (VerifyRule vr : vrList) {
				if (vp != null) {
					vp.setCurrentNum(++currentNum);
					vp.setProcessMsg("共" + totalDataNum + "条数据、" + vp.getTotalNum() + "条校验规则，正在校验第" + currentNum
							+ "个规则【" + vr.getVru002() + "】");
					sess.update(vp);
					sess.flush();
				}
				String vel006 = vr.getVru008();
				if (DBUtil.getDBType() == DBType.MYSQL) {
					vel006 = "CAST('" + vel006 + "' AS CHAR CHARACTER SET utf8)";
				}

				String selectSqlTemp = "";
				String a01SqlTemp = vr.getVru006() + ".imprecordid = '" + imprecordid + "' ";// 用于替换 【xx.A0000 = :A0000】

				sqlVerify = vr.getVru009();
				if (StringUtil.isEmpty(sqlVerify) || "0".equals(vr.getVru007())) {
					continue;
				}

				// zxw 注意来源sql部分 :B0000与:A0000 是区分大小写的;并且SQL部分必须包含“:A0000”、“:B0111”部分
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

			} // end for 循环校验规则

			String checkFlag = "select count(1) from verify_error_list where vel005 = '2' and  vel004 ='" + imprecordid
					+ "' and vel009 is not null and vru003 != '9'";// 排除
																	// 特殊校验：导入数据与正式库中人员身份证号重复的人员:异常类型（vru003）-导入前异常（9），校验规则为空
			List checkFlagList = sess.createSQLQuery(checkFlag).list();
			if (checkFlagList != null && !checkFlagList.isEmpty()
					&& Long.parseLong(checkFlagList.get(0).toString()) > 0) {
				flag = false;
			}

			// 6.回写导入记录表：是否校验，校验方案
			Imprecord imprecord = (Imprecord) sess.get(Imprecord.class, imprecordid);
			imprecord.setIsvirety("1");// 已校验
			imprecord.setVsc001(vrList.get(0).getVsc001());// 校验方案
			sess.update(imprecord);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("校验发生异常：" + e.getMessage());
		}

		return flag;
	}

	public static boolean dataVerifyByBSType(String b0111, String vsc001, String bsType) throws Exception {
		return dataVerifyByBSType(b0111, vsc001, bsType, null, null, null, null);
	}

	/**
	 * 通过不同业务类型调用不同校验方法
	 * 
	 * @param b0111
	 * @param vsc001
	 * @param bsType 1-常规正式库单位校验（包含下级单位）；2-导入校验
	 * @return
	 * @throws AppException
	 * @throws SQLException
	 */
	public static boolean dataVerifyByBSType(String b0111, String ruleids, String bsType, VerifyProcess vp, UserVO user,
			String a0163, String needverifyid) throws Exception {
		if (StringUtil.isEmpty(bsType)) {
			throw new AppException("校验业务类型为空！");
		}
		HBSession sess = HBUtil.getHBSession();
		String tabName = ""; // 操作表名 ，日志使用
		String opObjName = ""; // 操作对象名，日志使用
		String opComment = ""; // 操作备注，日志使用
		boolean flag = true;
		if ("1".equals(bsType)) {
			flag = dataVerifyByB0111VRu001(b0111, ruleids, vp, user, needverifyid);
			tabName = "B01";
			opComment = "选择单位校验：";
			B01 b01 = (B01) sess.get(B01.class, b0111);
			if (b01 != null) {
				opObjName = b01.getB0101();
			}

		} else if ("2".equals(bsType)) {
			Imprecord imprecord = (Imprecord) sess.get(Imprecord.class, b0111);
			flag = dataVerifyByImprecordidVsc001_Create(b0111, ruleids, vp, imprecord.getImptemptable(), user);
			tabName = "IMP_RECORD";
			opComment = "导入信息校验：";
			if (imprecord != null) {
				opObjName = imprecord.getFilename();
			}
		} else if ("3".equals(bsType)) {
			flag = dataVerifyByRulePeroleid(b0111, ruleids, vp, needverifyid, user);
		} else if ("4".equals(bsType)) {
			flag = dataVerifyByB0111Vsc001(b0111, ruleids, vp, a0163, user);
		} else {
			throw new AppException("不支持的校验业务类型！");

		}

		// 记录日志
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
	 * 根据组织机构编码,校验方案ID 校验 信息
	 * 
	 * @param b0111  组织机构编码
	 * @param vsc001 校验方案ID
	 * @throws SQLException
	 * @throws AppException
	 */
	public static boolean dataVerifyByB0111Vsc001(String b0111, String ruleids, VerifyProcess vp, String a0163,
			UserVO user) throws SQLException, AppException {
//		return dataVerifyProcedureByB0111Vsc001(b0111,vsc001);  //调用存储过程
//		int i = 0 ;
		HBSession sess = HBUtil.getHBSession();
		boolean flag = true; // 默认为true 通过
		boolean vsc001NullFlag = false; // vsc001 是否为空标记
		String vel001Bf = " sys_guid()  "; // 生成VEL001主键（UUID）的函数
		String vel005Bf = " '4' "; // VEL005业务类型:: 1-机构校验（包含下级机构）；2-导入校验 3人员校验 4 整库校验
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
				System.out.println("执行“SET GLOBAL group_concat_max_len”失败！");
			}
		}
		String userid = user.getId();
		String detailID = "";
		int ii = 0;
		String tableLog = "";
		String limitNum = "0";
		// 0.删除之前该批次校验生成的数据
		try {
			DataVerifyBS dvbs = new DataVerifyBS();

			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("CHECK_DA_SEQUENCE", ++ii + "");
			map1.put("DSA_INFO_ID", "ZWHZYQ");
			map1.put("CHECK_COND_ID", UUID.randomUUID().toString().replaceAll("-", ""));
			map1.put("TARGET_TAB_CODE", "--");
			map1.put("CHECK_COND_SQL", "校核脚本-删除之前该批次校验生成的数据");
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
				CommonQueryBS.systemOut(new Date().toString() + "校验sql:" + deletesql.toString());
				CommonQueryBS.systemOut(new Date().toString() + "校验sql:" + delSqlErrorList.toString());
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
			map2.put("CHECK_COND_SQL", "校核脚本-查询需要校验的数据");
			map2.put("CHECK_COUNT", limitNum);
			map2.put("CHECK_DA_BEGIN_DATE", "");
			map2.put("IS_CREATE_FILE", "NO");
			map2.put("CHECK_TOTAL", "0");
			detailID = LargetaskLog.DetailLog(map2, detailID);
			String apsql = "";
			// 2.查询需要校验的数据
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
			CommonQueryBS.systemOut(new Date().toString() + "校验sql:" + dataExiestSql.toString());
			List<Object> list = sess.createSQLQuery(dataExiestSql.toString()).list();
			if (list == null || list.size() < 1 || Long.parseLong(list.get(0).toString()) < 1) {
				throw new AppException("未查找到需要校验的数据！");
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
				 * try { //校验前先删除垃圾数据 for(String table :tables){ String sql = "DELETE FROM '"
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
				// 循环校验规则

				for (String rule : ruleid) {
					
					VerifyRule vr = (VerifyRule) sess.get(VerifyRule.class, rule);
					VSC001 = vr.getVsc001();
					String vru002Id = vr.getVru002();
					vriList.add(vr);
					if (vp != null) {
						vp.setCurrentNum(++currentNum);
						vp.setProcessMsg("共" + totalDataNum + "条数据、" + vp.getTotalNum() + "条校验规则，正在校验第" + currentNum
								+ "个规则【" + vr.getVru002() + "】");
						PhotosUtil.saveLog("共" + totalDataNum + "条数据、" + vp.getTotalNum() + "条校验规则，正在校验第" + currentNum
								+ "个规则【" + vr.getVru002() + "】");
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
					map.put("CHECK_COND_SQL", "校核脚本");
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
						/// 2017.04.17 yinl 通过a01表过滤，a02表该条件可能为空
						ap2sql = " and  EXISTS(select 1 from a01 a01 where a01.A0000 = x.A0000 and a01.A0163 = '1' and a01.STATUS <>'4')  ";

					}
					/**
					 * else{ ap2sql = " and x.A0201B is not null "; }
					 **/
//					String a01SqlTemp = " EXISTS (SELECT 1 FROM A02  x WHERE x.a0000 = "+vr.getVru006()+".A0000 AND x.a0201b LIKE  '"+b0111+"%' "+ap2sql+") ";//用于替换 【xx.A0000 = :A0000】
					String a01SqlTemp = "";
					if (DBUtil.getDBType() == DBType.MYSQL) {
						a01SqlTemp = " " + vr.getVru006()
								+ ".A0000 IN (SELECT x.A0000 FROM A02  x WHERE  x.a0201b LIKE  '" + b0111 + "%' "
								+ ap2sql + ") ";// 用于替换 【xx.A0000 = :A0000】
					} else {
						a01SqlTemp = " EXISTS (SELECT 1 FROM A02  x WHERE x.a0000 = " + vr.getVru006()
								+ ".A0000 AND x.a0201b LIKE  '" + b0111 + "%' " + ap2sql + ") ";// 用于替换 【xx.A0000 =
																								// :A0000】
					}

					sqlVerify = vr.getVru009();
					if (StringUtil.isEmpty(sqlVerify) || "0".equals(vr.getVru007())) {
						continue;
					}

					// zxw 注意来源sql部分 :B0000与:A0000 是区分大小写的;并且SQL部分必须包含“:A0000”、“:B0111”部分
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
					// 简历判空做特殊处理
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
							if ("学历信息-无全日制学历".equals(vru002Id)) {

								String sql = "create table A08q_" + suffix
										+ " as select a0000,a0837 from A08 WHERE a0837 = '1'";

								sess.createSQLQuery(sql).executeUpdate();
								sess.createSQLQuery(
										"ALTER TABLE a08q_" + suffix + " add index a08q_" + suffix + "_1(a0000)")
										.executeUpdate();
								sqlVerify = sqlVerify.replace("A08_" + suffix, "A08q_" + suffix);
								sqlVerify = sqlVerify.replace("a08_" + suffix, "A08q_" + suffix);
							}
							if ("家庭成员称谓-缺少父亲或母亲信息".equals(vru002Id)) {
								String sql = "create table A36f_" + suffix
										+ " as select a0000 from A36 WHERE a3604a='父亲' or  a3604a='继父' or  a3604a='养父'";
								String sql1 = "create table A36m_" + suffix
										+ " as select a0000 from A36 WHERE a3604a='母亲' or  a3604a='继母' or  a3604a='养母'";

								sess.createSQLQuery(sql).executeUpdate();
								sess.createSQLQuery(sql1).executeUpdate();
								sqlVerify = sqlVerify.replace("A36f_temp", "A36f_" + suffix);
								sqlVerify = sqlVerify.replace("a36f_temp", "A36f_" + suffix);
								sqlVerify = sqlVerify.replace("A36m_temp", "A36m_" + suffix);
								sqlVerify = sqlVerify.replace("a36m_temp", "A36m_" + suffix);
							}
						} else {
							if ("学历信息-无全日制学历".equals(vru002Id)) {

								String sql = "create table A08q_" + suffix
										+ " as select a0000 from A08 WHERE a0837 = '1'";
								sess.createSQLQuery(sql).executeUpdate();

								sess.createSQLQuery("create index A08q_" + suffix + "_1 on a08q_" + suffix + " (a0000)")
										.executeUpdate();
								sqlVerify = sqlVerify.replace("A08_" + suffix, "A08q_" + suffix);
								sqlVerify = sqlVerify.replace("a08_" + suffix, "A08q_" + suffix);
							}
							if ("家庭成员称谓-缺少父亲或母亲信息".equals(vru002Id)) {
								String sql = "create table A36f_" + suffix
										+ " as select a0000 from A36 WHERE a3604a='父亲' or  a3604a='继父' or  a3604a='养父'";
								String sql1 = "create table A36m_" + suffix
										+ " as select a0000 from A36 WHERE a3604a='母亲' or  a3604a='继母' or  a3604a='养母'";

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
						System.out.println("整库校验出错的校核sql-----------" + sqlVerify.replace("_temp", ""));
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
//						CommonQueryBS.systemOut(new Date().toString()+"校验出错语句:"+sqlVerify);
						String sql = " insert into special_verify values(" + vel001Bf + ",'" + vru001 + "','" + vru002
								+ "','4','" + userid + "','')";
						sess.createSQLQuery(sql).executeUpdate();
						continue;
					}
					
				} // end for 循环校验规则
					// 控制 - 整库校核实现校核结果统计功能
				if ("1".equals(HBUtil.getValueFromTab("count(1)", "aa01", "aaa001 = 'CHECK_WORD' and aaa005 = 'ON'"))) {
					dvbs.tjCheckInfo(tables, vriList, suffix, b0111, userid, VSC001);
				} else {
					// 否则情况统计表
					HBUtil.executeUpdate("delete from check_tj ");
				}

				// 整库校核实现结果集合并操作
				dvbs.updatevelbyb0111(b0111, "4", userid);

				String checkFlag = "select count(1) from verify_error_list where vel005 = '4' and  vel004 = '" + b0111
						+ "' and vel010='" + userid + "'";

				List checkFlagList = sess.createSQLQuery(checkFlag).list();
				if (checkFlagList != null && !checkFlagList.isEmpty()
						&& Long.parseLong(checkFlagList.get(0).toString()) > 0) {
					flag = false;
				}
				System.out.println("到此为止！");
			} else {

				// 建立临时表
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
				map3.put("CHECK_COND_SQL", "校核脚本-建立临时表");
				map3.put("CHECK_COUNT", limitNum);
				map3.put("CHECK_DA_BEGIN_DATE", "");
				map3.put("IS_CREATE_FILE", "NO");
				map3.put("CHECK_TOTAL", "0");
				detailID = LargetaskLog.DetailLog(map3, detailID);
				for (String table : tables) {
					String CreateSQL = "";
					/**
					 * 抽数的标准： 人员职务所挂职单位节点下， 是否包含非在职有用户判断
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
					CommonQueryBS.systemOut("建表语句sql:" + CreateSQL);

					// CommonQueryBS.systemOut(table+"执行完毕时间：---"+w.elapsedTime());
					sess.createSQLQuery(CreateSQL).executeUpdate();

				}
				Map<String, String> map3End = new HashMap<String, String>();
				map3End.put("CHECK_DA_END_TIME", "");
				LargetaskLog.DetailLog(map3End, detailID);
				detailID = "";
				String b01_verify = "create table b01_L_" + suffix + " as select * from B01 WHERE B01.b0111 LIKE '"
						+ b0111 + "%' and (B01.delFlag <> '0' or B01.delflag is null)";
				CommonQueryBS.systemOut(new Date().toString() + "校验sql:" + b01_verify);
				sess.createSQLQuery(b01_verify).executeUpdate();
				Map<String, String> map4 = new HashMap<String, String>();
				map4.put("CHECK_DA_SEQUENCE", ++ii + "");
				map4.put("CHECK_COND_ID", UUID.randomUUID().toString().replaceAll("-", ""));
				map4.put("DSA_INFO_ID", "ZWHZYQ");
				map4.put("TARGET_TAB_CODE", "--");
				map4.put("CHECK_COND_SQL", "校核脚本-建立临时表索引");
				map4.put("CHECK_COUNT", limitNum);
				map4.put("CHECK_DA_BEGIN_DATE", "");
				map4.put("IS_CREATE_FILE", "NO");
				map4.put("CHECK_TOTAL", "0");
				detailID = LargetaskLog.DetailLog(map4, detailID);
				ImpmodelThread.checkIndex("_L_" + suffix, tables);// 建立临时表索引
				Map<String, String> map4End = new HashMap<String, String>();
				map4End.put("CHECK_DA_END_TIME", "");
				LargetaskLog.DetailLog(map4End, detailID);
				detailID = "";
				StringBuffer insertSqlBf = new StringBuffer(
						"INSERT INTO verify_error_list_mirror ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010) ");
				String b01SqlTemp = " like '" + b0111 + "%' "; // 用于替换 【= :B0111】

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
				// 循环校验规则
				for (String rule : ruleid) {
					/*
					 * try { //校验前先删除垃圾数据 for(String table :tables){ String sql = "DELETE FROM "
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
						vp.setProcessMsg("共" + totalDataNum + "条数据、" + vp.getTotalNum() + "条校验规则，正在校验第" + currentNum
								+ "个规则【" + vr.getVru002() + "】");
						PhotosUtil.saveLog("共" + totalDataNum + "条数据、" + vp.getTotalNum() + "条校验规则，正在校验第" + currentNum
								+ "个规则【" + vr.getVru002() + "】");
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
					map.put("CHECK_COND_SQL", "校核脚本");
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
						/// 2017.04.17 yinl 通过a01表过滤，a02表该条件可能为空
						ap2sql = " and  EXISTS(select 1 from a01 a01 where a01.A0000 = x.A0000 and a01.A0163 = '1' and a01.STATUS <>'4')  ";

					}
					/**
					 * else{ ap2sql = " and x.A0201B is not null "; }
					 **/
//				String a01SqlTemp = " EXISTS (SELECT 1 FROM A02  x WHERE x.a0000 = "+vr.getVru006()+".A0000 AND x.a0201b LIKE  '"+b0111+"%' "+ap2sql+") ";//用于替换 【xx.A0000 = :A0000】
					String a01SqlTemp = "";
					if (DBUtil.getDBType() == DBType.MYSQL) {
						a01SqlTemp = " " + vr.getVru006()
								+ ".A0000 IN (SELECT x.A0000 FROM A02  x WHERE  x.a0201b LIKE  '" + b0111 + "%' "
								+ ap2sql + ")  main";// 用于替换 【xx.A0000 = :A0000】
					} else {
						a01SqlTemp = " EXISTS (SELECT 1 FROM A02  x WHERE x.a0000 = " + vr.getVru006()
								+ ".A0000 AND x.a0201b LIKE  '" + b0111 + "%' " + ap2sql + ") ";// 用于替换 【xx.A0000 =
																								// :A0000】
					}

					sqlVerify = vr.getVru009();
					if (StringUtil.isEmpty(sqlVerify) || "0".equals(vr.getVru007())) {
						continue;
					}

					// zxw 注意来源sql部分 :B0000与:A0000 是区分大小写的;并且SQL部分必须包含“:A0000”、“:B0111”部分
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

					// 去掉临时表标记
					sqlVerify = sqlVerify.replace(VerificationSchemeConfPageModel.TEMP_PREFIX, "_L_" + suffix);

					sqlVerify = insertSqlBf + "select " + vel001Bf + "  vel001,tt.* from (" + sqlVerify + ") tt";
//				System.out.println("sql====>"+sqlVerify);
//				System.out.println("%  ====>"+currentNum/(float)vp.getTotalNum());
					// 简历判空做特殊处理
					if (sqlVerify.contains("A1701")) {
						if (DBUtil.getDBType() == DBType.MYSQL) {
							sqlVerify = sqlVerify.replace("A01_L_" + suffix + ".A1701 is null",
									"replace(replace(A01_L_" + suffix + ".A1701,char(13),''),char(10),'') is null");
						} else {
							sqlVerify = sqlVerify.replace("A01_L_" + suffix + ".A1701 is null",
									"replace(replace(A01_L_" + suffix + ".A1701,chr(13),''),chr(10),'') is null");
						}
					}
					PhotosUtil.saveLog("正在执行的sql--> " + sqlVerify);
					try {
						if (DBUtil.getDBType() == DBType.MYSQL) {
							if ("学历信息-无全日制学历".equals(vru002Id)) {
								String sql = "create table A08q_L_" + suffix
										+ " as select a0000,a0837 from A08 WHERE a0837 = '1'";

								sess.createSQLQuery(sql).executeUpdate();
								sess.createSQLQuery(
										"ALTER TABLE a08q_L_" + suffix + " add index a08q_L_" + suffix + "_1(a0000)")
										.executeUpdate();
								sqlVerify = sqlVerify.replace("A08_L_" + suffix, "A08q_L_" + suffix);
								sqlVerify = sqlVerify.replace("a08_L_" + suffix, "A08q_L_" + suffix);
							}
							if ("家庭成员称谓-缺少父亲或母亲信息".equals(vru002Id)) {
								String sql = "create table A36f_L_" + suffix
										+ " as select a0000 from A36 WHERE a3604a='父亲' or  a3604a='继父' or  a3604a='养父'";
								String sql1 = "create table A36m_L_" + suffix
										+ " as select a0000 from A36 WHERE a3604a='母亲' or  a3604a='继母' or  a3604a='养母'";

								sess.createSQLQuery(sql).executeUpdate();
								sess.createSQLQuery(sql1).executeUpdate();
								sqlVerify = sqlVerify.replace("A36f_temp", "A36f_L_" + suffix);
								sqlVerify = sqlVerify.replace("a36f_temp", "A36f_L_" + suffix);
								sqlVerify = sqlVerify.replace("A36m_temp", "A36m_L_" + suffix);
								sqlVerify = sqlVerify.replace("a36m_temp", "A36m_L_" + suffix);
							}
						} else {
							if ("学历信息-无全日制学历".equals(vru002Id)) {

								String sql = "create table A08q_L_" + suffix
										+ " as select a0000 from A08 WHERE a0837 = '1'";
								sess.createSQLQuery(sql).executeUpdate();

								sess.createSQLQuery("create index A08q_L_" + suffix + "_1 on a08q_L_" + suffix + " (a0000)")
										.executeUpdate();
								sqlVerify = sqlVerify.replace("A08_L_" + suffix, "A08q_L_" + suffix);
								sqlVerify = sqlVerify.replace("a08_L_" + suffix, "A08q_L_" + suffix);
							}
							if ("家庭成员称谓-缺少父亲或母亲信息".equals(vru002Id)) {
								String sql = "create table A36f_L_" + suffix
										+ " as select a0000 from A36 WHERE a3604a='父亲' or  a3604a='继父' or  a3604a='养父'";
								String sql1 = "create table A36m_L_" + suffix
										+ " as select a0000 from A36 WHERE a3604a='母亲' or  a3604a='继母' or  a3604a='养母'";

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
						System.out.println("出错的校核sql-----------" + sqlVerify);
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
//					CommonQueryBS.systemOut(new Date().toString()+"校验出错语句:"+sqlVerify);
						String sql = " insert into special_verify values(" + vel001Bf + ",'" + vru001 + "','" + vru002
								+ "','4','" + userid + "','')";
						sess.createSQLQuery(sql).executeUpdate();
						continue;
					}

				} // end for 循环校验规则
					// 控制 - 整库校核实现校核结果统计功能
				if ("1".equals(HBUtil.getValueFromTab("count(1)", "aa01", "aaa001 = 'CHECK_WORD' and aaa005 = 'ON'"))) {
					dvbs.tjCheckInfo(tables, vriList, suffix, b0111, userid, VSC001);
				} else {
					// 否则情况统计表
					HBUtil.executeUpdate("delete from check_tj ");
				}
				Map<String, String> map5 = new HashMap<String, String>();
				map5.put("CHECK_DA_SEQUENCE", ++ii + "");
				map5.put("CHECK_COND_ID", UUID.randomUUID().toString().replaceAll("-", ""));
				map5.put("DSA_INFO_ID", "ZWHZYQ");
				map5.put("TARGET_TAB_CODE", "--");
				map5.put("CHECK_COND_SQL", "校核脚本-整库校核合并");
				map5.put("CHECK_COUNT", limitNum);
				map5.put("CHECK_DA_BEGIN_DATE", "");
				map5.put("IS_CREATE_FILE", "NO");
				map5.put("CHECK_TOTAL", "0");
				detailID = LargetaskLog.DetailLog(map5, detailID);
				// 整库校核实现结果集合并操作
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
			throw new AppException("校验发生异常：" + e.getMessage());
		} finally {
			Map<String, String> map5 = new HashMap<String, String>();
			map5.put("CHECK_DA_SEQUENCE", ++ii + "");
			map5.put("CHECK_COND_ID", UUID.randomUUID().toString().replaceAll("-", ""));
			map5.put("DSA_INFO_ID", "ZWHZYQ");
			map5.put("TARGET_TAB_CODE", "--");
			map5.put("CHECK_COND_SQL", "校核脚本-删除临时表");
			map5.put("CHECK_COUNT", limitNum);
			map5.put("CHECK_DA_BEGIN_DATE", "");
			map5.put("IS_CREATE_FILE", "NO");
			map5.put("CHECK_TOTAL", "0");
			detailID = LargetaskLog.DetailLog(map5, detailID);
			// 删除临时表
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
//		System.out.println("共执行"+i);
		return flag;
	}

	/**
	 * 获取某校验批次某人的错误字段
	 * 
	 * @return
	 */
	public static String getErrorInfo(String vel002, String vel004, String vel005) {
		String jsonStr = "[]";
		// 排除特殊校验
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
	 * 从V_aa10 视图中获取相应值
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
	 * 根据导入批次ID,校验方案ID 校验该批次导入信息
	 * 
	 * 0.删除之前该批次校验生成的数据 1.查询校验方案中需要校验的表信息 2.查询出该批次中所需要校验表数据
	 * 3.循环人员表信息,循环校验规则（VerifyRule）：修改校验信息，写入校验错误信息（VerifyError,VerifyErrorDetail）
	 * 4.删除 没有对应 verify_error_datail信息的verify_error表 5.回写导入记录表：是否校验，校验方案
	 * 
	 * @param imprecordid 导入批次ID
	 * @param vsc001      校验方案ID
	 * @throws SQLException
	 * @throws AppException
	 */
	public static boolean dataVerifyByImprecordidVsc001_Create(String imprecordid, String ruleids, VerifyProcess vp,
			String imptemptable, UserVO user) throws Exception {

		// return dataVerifyProcedureByImprecordidVsc001(imprecordid,vsc001); //存储过程

		boolean flag = true; // 默认为true 通过
		boolean vsc001NullFlag = false; // vsc001 是否为空标记
		String vel001Bf = " sys_guid()  "; // 生成VEL001主键（UUID）的函数
		String vel005Bf = " '2' "; // VEL005业务类型:: 1-机构校验（包含下级机构）；2-导入校验
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

		// 1.删除之前该批次校验生成的数据
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

			// 2.查询需要校验的数据
			StringBuffer dataExiestSql = new StringBuffer(" SELECT  ( ").append("     (SELECT  ")
					.append("       COUNT(1)  ").append("     FROM ").append("       a01" + imptemptable + " a  ")
					.append("     ) +  ").append("     (SELECT  ").append("       COUNT(1)  ").append("     FROM ")
					.append("       b01" + imptemptable + " a  ").append("     ) ").append("   )   ")
					.append("   FROM DUAL  ");
			List<Object> list = sess.createSQLQuery(dataExiestSql.toString()).list();

			if (list == null || list.size() < 1 || Long.parseLong(list.get(0).toString()) < 1) {
				throw new Exception("未查找到需要校验的数据！");
			}
			String totalDataNum = list.get(0).toString();
			// 3.循环校验规则（VerifyRule）：修改校验信息，写入校验错误信息（VerifyErrorList）
			// 校验规则取选择的已启用校验方案和所有启用的默认基础校验方案
			/*
			 * String verifyRuleHql = "from VerifyRule b where vru007 = '1' "; if
			 * (!vsc001NullFlag) { verifyRuleHql = verifyRuleHql +
			 * " and   (vsc001 = :vsc001 and  exists (select 1 from VerifyScheme a where a.vsc001 = b.vsc001 and a.vsc003='1'))  "
			 * ; }
			 */
//			注释掉 默认基础校验方案
//			verifyRuleHql = verifyRuleHql
//					+ " or  exists (select 1 from VerifyScheme a where a.vsc001 = b.vsc001 and a.vsc003='1' and a.vsc007='1')";
			/*
			 * Query queryvr = sess.createQuery(verifyRuleHql); if (!vsc001NullFlag) {
			 * queryvr = queryvr.setString("vsc001", vsc001); }
			 * 
			 * @SuppressWarnings("unchecked") List<VerifyRule> vrList = queryvr.list(); if
			 * (vrList == null || vrList.isEmpty()) { throw new
			 * Exception("该校验方案没有配置对应校验规则或没有基础校验方案！"); }
			 */
			String[] ruleid = ruleids.split(",");

			StringBuffer insertSqlBf = new StringBuffer(
					"INSERT INTO VERIFY_ERROR_LIST ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010) ");
			String b01SqlTemp = VerificationSchemeConfPageModel.b01TabName + ".imprecordid = '" + imprecordid + "' "; // 用于替换
																														// 【=
																														// :B0111】

			String a01ParamSql = ".A0000" + VerificationSchemeConfPageModel.EQUAL
					+ VerificationSchemeConfPageModel.A01_PARAM;
			String b01ParamSql = VerificationSchemeConfPageModel.b01TabName + ".B0111"
					+ VerificationSchemeConfPageModel.EQUAL + VerificationSchemeConfPageModel.B01_PARAM;
			String sqlVerify = null;

			if (vp != null) {
				vp.setTotalNum(Long.parseLong(ruleid.length + ""));
				sess.update(vp);
			}

			// 特殊校验：导入数据与正式库中人员身份证号重复的人员:异常类型（vru003）-导入前异常（9），校验规则为空
			/*
			 * String moreA0148 = " select a01" + imptemptable +
			 * ".a0000 vel002,'9' vru003,'1' vel003,'"+imprecordid+"' vel004,"
			 * +vel005Bf+" vel005,'待导入临时数据与正式库人员比较身份证重复人员！' vel006,'A01' vel007,'A0184' vel008,'' vel009,'"
			 * +userid+"' vel010 from a01" + imptemptable +
			 * " where EXISTS (SELECT 1 FROM a01 a WHERE a.A0184 = a01" + imptemptable +
			 * ".A0184 and a01" + imptemptable + ".A0184 is not null and length(a01" +
			 * imptemptable + ".A0184)>0) "; moreA0148 = insertSqlBf + "select " + vel001Bf
			 * + "  vel001,tt.* from (" + moreA0148 + ") tt";
			 * sess.createSQLQuery(moreA0148).executeUpdate();
			 */

			long currentNum = 0;
			// 循环校验规则
			for (String ruid : ruleid) {
				VerifyRule vr = (VerifyRule) sess.get(VerifyRule.class, ruid);

				if (vp != null) {
					vp.setCurrentNum(++currentNum);
					vp.setProcessMsg("共" + totalDataNum + "条数据、" + vp.getTotalNum() + "条校验规则，正在校验第" + currentNum
							+ "个规则【" + vr.getVru002() + "】");
					PhotosUtil.saveLog("共" + totalDataNum + "条数据、" + vp.getTotalNum() + "条校验规则，正在校验第" + currentNum
							+ "个规则【" + vr.getVru002() + "】");
					PhotosUtil.saveLog("规则开始时间" + new Date().toString());
					sess.update(vp);
					sess.flush();
				}
				String vel006 = vr.getVru008();
				String vru002Id = vr.getVru002();
				if (DBUtil.getDBType() == DBType.MYSQL) {
					vel006 = "CAST('" + vel006 + "' AS CHAR CHARACTER SET utf8)";
				}

				String selectSqlTemp = "";
				String a01SqlTemp = vr.getVru006() + ".imprecordid = '" + imprecordid + "' ";// 用于替换 【xx.A0000 = :A0000】

				sqlVerify = vr.getVru009();
				if (StringUtil.isEmpty(sqlVerify) || "0".equals(vr.getVru007())) {
					continue;
				}

				// zxw 注意来源sql部分 :B0000与:A0000 是区分大小写的;并且SQL部分必须包含“:A0000”、“:B0111”部分
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
				PhotosUtil.saveLog("正在执行的sql--> " + sqlVerify);
				try {
//					if(sqlVerify.contains("机构编码-J-不符合编码规范")){
					CommonQueryBS.systemOut("导入校核SQL--> " + sqlVerify);
//					}
					if (DBUtil.getDBType() == DBType.MYSQL) {

						if ("家庭成员称谓-缺少父亲或母亲信息".equals(vru002Id)) {
							String sql = "create table A36f_" + suffix + " as select a0000 from A36" + imptemptable
									+ " WHERE a3604a='父亲' or  a3604a='继父' or  a3604a='养父'";
							String sql1 = "create table A36m_" + suffix + " as select a0000 from A36" + imptemptable
									+ " WHERE a3604a='母亲' or  a3604a='继母' or  a3604a='养母'";

							sess.createSQLQuery(sql).executeUpdate();
							sess.createSQLQuery(sql1).executeUpdate();
							sqlVerify = sqlVerify.replace("A36f" + imptemptable, "A36f_" + suffix);
							sqlVerify = sqlVerify.replace("a36f" + imptemptable, "A36f_" + suffix);
							sqlVerify = sqlVerify.replace("A36m" + imptemptable, "A36m_" + suffix);
							sqlVerify = sqlVerify.replace("a36m" + imptemptable, "A36m_" + suffix);
						}
					} else {

						if ("家庭成员称谓-缺少父亲或母亲信息".equals(vru002Id)) {

							String sql = "create table A36f_" + suffix + " as select a0000 from A36" + imptemptable
									+ " WHERE a3604a='父亲' or  a3604a='继父' or  a3604a='养父'";
							String sql1 = "create table A36m_" + suffix + " as select a0000 from A36" + imptemptable
									+ " WHERE a3604a='母亲' or  a3604a='继母' or  a3604a='养母'";

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
					// 删除临时表

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
					+ "' and vel009 is not null and vru003 != '9' and vel010='" + userid + "'";// 排除
																								// 特殊校验：导入数据与正式库中人员身份证号重复的人员:异常类型（vru003）-导入前异常（9），校验规则为空
			List checkFlagList = sess.createSQLQuery(checkFlag).list();
			if (checkFlagList != null && !checkFlagList.isEmpty()
					&& Long.parseLong(checkFlagList.get(0).toString()) > 0) {
				flag = false;
			}
			PhotosUtil.saveLog("整合错误开始时间" + new Date().toString());
			dvbs.updatevelbyimpid(imprecordid, "2", userid);
			PhotosUtil.saveLog("整合错误结束时间" + new Date().toString());

			// 6.回写导入记录表：是否校验，校验方案
			Imprecord imprecord = (Imprecord) sess.get(Imprecord.class, imprecordid);
			imprecord.setIsvirety("1");// 已校验
			// imprecord.setVsc001(vrList.get(0).getVsc001());// 校验方案
			sess.update(imprecord);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("校验发生异常：" + e.getMessage());
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
		// 插入人员校验结果统计信息
		String insert1 = "INSERT INTO ERRCOUNT";
		insert1 = insert1 + " SELECT VEL002, A.A0101, 1 ETYPE, COUNT(1) ERRNUM, NULL, 1";
		insert1 = insert1 + " FROM VERIFY_ERROR_LIST C, A01 A";
		insert1 = insert1 + " WHERE VEL003 = 1";
		insert1 = insert1 + " AND A.A0000 = C.VEL002";
		insert1 = insert1 + " AND C.VEL005 = '1'";
		insert1 = insert1 + " AND C.VEL004 LIKE '" + b0111 + "%'";
		insert1 = insert1 + " AND C.vru003 != '9'";
		insert1 = insert1 + " GROUP BY VEL002, A.A0101";
		// 插入机构校验结果统计信息
		String insert2 = "INSERT INTO ERRCOUNT";
		insert2 = insert2 + " SELECT VEL002, B.B0101 A0101, 2 ETYPE, COUNT(1) ERRNUM, VEL002, 1";
		insert2 = insert2 + " FROM VERIFY_ERROR_LIST C, B01 B";
		insert2 = insert2 + " WHERE VEL003 = 2";
		insert2 = insert2 + " AND B.B0111 = C.VEL002";
		insert2 = insert2 + " AND C.VEL005 = '1'";
		insert2 = insert2 + " AND C.VEL004 LIKE '" + b0111 + "%'";
		insert2 = insert2 + " AND C.vru003 != '9'";
		insert2 = insert2 + " GROUP BY VEL002, B.B0101";

		CommonQueryBS.systemOut("+++机构校验+++" + delete);
		CommonQueryBS.systemOut("+++机构校验+++" + insert1);
		CommonQueryBS.systemOut("+++机构校验+++" + insert2);

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
			// 更新ERRCOUNT表人员统计信息的B0111-mysql
			String update1 = "UPDATE VERIFY_ERROR_LIST E,";
			update1 = update1 + " (SELECT A.A0000, MIN(A.A0201B) A0201B";
			update1 = update1 + " FROM  A02 A";
			update1 = update1 + " WHERE A.A0201B LIKE '" + b0111 + "%'";
			update1 = update1 + a0163sql;
			update1 = update1 + " GROUP BY A.A0000) A";
			update1 = update1 + " SET E.VEL004 = A.A0201B";
			update1 = update1 + " WHERE A.A0000 = E.VEL002";
			update1 = update1 + " AND E.VEL005='4' and E.VEL010='" + userid + "'";
			CommonQueryBS.systemOut(new Date().toString() + "校验sql:" + update1);
			sess.createSQLQuery(update1).executeUpdate();

		} else if (DBType.ORACLE == DBUtil.getDBType()) {
			// 更新ERRCOUNT表人员统计信息的B0111-oracle
			String update1 = "UPDATE VERIFY_ERROR_LIST E SET VEL004 =";
			update1 = update1 + " (SELECT A.A0201B FROM A02 A WHERE A.A0000 = E.VEL002 " + a0163sql;
			update1 = update1 + " AND A.A0201B LIKE '" + b0111 + "%' AND ROWNUM = 1)";
			update1 = update1 + " WHERE  VEL005='4' AND E.VEL004 LIKE '" + b0111 + "%' and VEL010='" + userid
					+ "' and EXISTS( SELECT A.A0201B FROM A02 A WHERE A.A0000 = E.VEL002 " + a0163sql
					+ " AND A.A0201B LIKE '" + b0111 + "%'  AND ROWNUM = 1)";

			CommonQueryBS.systemOut(new Date().toString() + "校验sql:" + update1);

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

		// 删除校验统计表中已有数据
		String delete = "delete from errcount where vel005= '2' and b0111 like '" + b0111 + "%'";
		// 插入人员校验结果统计信息
		String insert1 = "INSERT INTO ERRCOUNT";
		insert1 = insert1 + " SELECT VEL002, A.A0101, 1 ETYPE, COUNT(1) ERRNUM, '" + imprecordid + "', 2";
		insert1 = insert1 + " FROM VERIFY_ERROR_LIST C, A01" + tmpt + " A";
		insert1 = insert1 + " WHERE VEL003 = 1";
		insert1 = insert1 + " AND A.A0000 = C.VEL002";
		insert1 = insert1 + " AND C.VEL005 = '2'";
		insert1 = insert1 + " AND C.VEL004 = '" + imprecordid + "'";
		insert1 = insert1 + " AND C.VRU003 != '9'";
		insert1 = insert1 + " GROUP BY VEL002, A.A0101";
		// 插入机构校验结果统计信息
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
		boolean flag = true; // 默认为true 通过
		boolean vsc001NullFlag = false; // vsc001 是否为空标记
		String vel001Bf = " sys_guid()  "; // 生成VEL001主键（UUID）的函数
		String vel005Bf = " '1' "; // VEL005业务类型:: 1-机构校验（包含下级机构）；2-导入校验
		String userid = user.getId();
		if (DBUtil.getDBType() == DBType.MYSQL) {
			vel001Bf = " UUID() ";
			try {
				sess.createSQLQuery("SET GLOBAL group_concat_max_len = 102400").executeUpdate();
				sess.createSQLQuery("SET SESSION group_concat_max_len = 102400").executeUpdate();
			} catch (Exception e) {
				System.out.println("执行“SET GLOBAL group_concat_max_len”失败！");
			}
		}
		String vel004Bf = "";
		if (needverifyid.contains("'") || needverifyid.contains("select")) {
			vel004Bf = " in (" + needverifyid + ")";
		} else {
			vel004Bf = " like '" + needverifyid + "%'";
		}
		// 0.删除之前该批次校验生成的数据
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
				throw new AppException("未查找到需要校验的数据！");
			}
			String totalDataNum = list.get(0).toString();

			String[] ruleid = ruleids.split(",");

			StringBuffer insertSqlBf = new StringBuffer(
					"INSERT INTO VERIFY_ERROR_LIST ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010) ");
			String b01SqlTemp = " like '" + b0111 + "%' "; // 用于替换 【= :B0111】

			String a01ParamSql = ".A0000" + VerificationSchemeConfPageModel.EQUAL
					+ VerificationSchemeConfPageModel.A01_PARAM;
			String b01ParamSql = VerificationSchemeConfPageModel.EQUAL + VerificationSchemeConfPageModel.B01_PARAM;
			String sqlVerify = null;
			long currentNum = 0;
			if (vp != null) {
				vp.setTotalNum(Long.parseLong(ruleid.length + ""));
				sess.update(vp);
			}
			// 循环校验规则
			for (String ruid : ruleid) {
				VerifyRule vr = (VerifyRule) sess.get(VerifyRule.class, ruid);
				if (vp != null) {
					vp.setCurrentNum(++currentNum);
					vp.setProcessMsg("共" + totalDataNum + "条数据、" + vp.getTotalNum() + "条校验规则，正在校验第" + currentNum
							+ "个规则【" + vr.getVru002() + "】");
					PhotosUtil.saveLog("共" + totalDataNum + "条数据、" + vp.getTotalNum() + "条校验规则，正在校验第" + currentNum
							+ "个规则【" + vr.getVru002() + "】");
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
				 * yinl 通过a01表过滤，a02表该条件可能为空 ap2sql =
				 * " and  EXISTS(select 1 from a01 a01 where a01.A0000 = x.A0000 and a01.A0163 = '1')  "
				 * ;
				 * 
				 * }
				 */
				/**
				 * else{ ap2sql = " and x.A0201B is not null "; }
				 **/
//						String a01SqlTemp = " EXISTS (SELECT 1 FROM A02  x WHERE x.a0000 = "+vr.getVru006()+".A0000 AND x.a0201b LIKE  '"+b0111+"%' "+ap2sql+") ";//用于替换 【xx.A0000 = :A0000】
				String a01SqlTemp = "";
				/*
				 * if(DBUtil.getDBType() == DBType.MYSQL){ a01SqlTemp = " "+vr.getVru006()
				 * +".A0000 IN (SELECT x.A0000 FROM A02  x WHERE  x.a0201b LIKE  '"+b0111+"%' "
				 * +ap2sql+") ";//用于替换 【xx.A0000 = :A0000】 }else { a01SqlTemp =
				 * " EXISTS (SELECT 1 FROM A02  x WHERE x.a0000 = "+vr.getVru006()
				 * +".A0000 AND x.a0201b LIKE  '"+b0111+"%' "+ap2sql+") ";//用于替换 【xx.A0000 =
				 * :A0000】 }
				 */
				sqlVerify = vr.getVru009();
				if (StringUtil.isEmpty(sqlVerify) || "0".equals(vr.getVru007())) {
					continue;
				}

				// zxw 注意来源sql部分 :B0000与:A0000 是区分大小写的
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

				// 去掉临时表标记
				sqlVerify = sqlVerify.replace(VerificationSchemeConfPageModel.TEMP_PREFIX, "");

				sqlVerify = insertSqlBf + "select " + vel001Bf + "  vel001,tt.* from (" + sqlVerify + ") tt";
//						System.out.println("sql====>"+sqlVerify);
//						System.out.println("%  ====>"+currentNum/(float)vp.getTotalNum());
				// 简历判空做特殊处理
				/*
				 * if(sqlVerify.contains("A1701")){ if(DBUtil.getDBType() == DBType.MYSQL){
				 * sqlVerify = sqlVerify.replace("A01_"+suffix+".A1701 is null",
				 * "replace(replace(A01_"+suffix+".A1701,char(13),''),char(10),'') is null");
				 * }else{ sqlVerify = sqlVerify.replace("A01_"+suffix+".A1701 is null",
				 * "replace(replace(A01_"+suffix+".A1701,chr(13),''),chr(10),'') is null"); } }
				 */
				PhotosUtil.saveLog("正在执行的sql--> " + sqlVerify);
				// System.out.println(sqlVerify);
				try {
//							if(sqlVerify.contains("机构编码-J-不符合编码规范")){
					CommonQueryBS.systemOut("机构信息校核SQL--> " + sqlVerify);
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

			} // end for 循环校验规则
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
			throw new AppException("校验发生异常：" + e.getMessage());
		}
		return flag;
	}

	public static boolean dataVerifyByRulePeroleid(String b0111, String ruleids, VerifyProcess vp, String peopleid,
			UserVO user) throws AppException {

//		return dataVerifyProcedureByB0111Vsc001(b0111,vsc001);  //调用存储过程

		HBSession sess = HBUtil.getHBSession();
		boolean flag = true; // 默认为true 通过
		boolean vsc001NullFlag = false; // vsc001 是否为空标记
		String vel001Bf = " sys_guid()  "; // 生成VEL001主键（UUID）的函数
		String vel005Bf = " '3' "; // VEL005业务类型:: 1-机构校验（包含下级机构）；2-导入校验
		String[] tables = { "A01", "A02", "A05", "A06", "A08", "A11", "A14", "A15", "A17", "A30", "A36", "A99Z1" };
		String suffix = String.valueOf((long) (Math.random() * 1000000000000000L));
		if (DBUtil.getDBType() == DBType.MYSQL) {
			vel001Bf = " UUID() ";
			try {
				sess.createSQLQuery("SET GLOBAL group_concat_max_len = 102400").executeUpdate();
				sess.createSQLQuery("SET SESSION group_concat_max_len = 102400").executeUpdate();
			} catch (Exception e) {
				System.out.println("执行“SET GLOBAL group_concat_max_len”失败！");
			}
		}
		String userid = user.getId();
		// 0.删除之前该批次校验生成的数据
		try {
			DataVerifyBS dvbs = new DataVerifyBS();
			String delSqlErrorList = "delete from verify_error_list_mirror  where vel004 in (" + peopleid
					+ ") and  vel005 ='3' and vel010='" + userid + "'";
			String delSql = "delete from verify_error_list  where vel004 in (" + peopleid
					+ ") and  vel005 ='3' and vel010='" + userid + "'";
			try {
				// sess.createSQLQuery(delete11).executeUpdate();
				String deletesql = "delete from special_verify where sv004='3' and sv005='" + userid + "'";
				CommonQueryBS.systemOut(new Date().toString() + " 校验sql" + deletesql);
				CommonQueryBS.systemOut(new Date().toString() + " 校验sql" + delSqlErrorList);
				sess.createSQLQuery(delSql).executeUpdate();
				sess.createSQLQuery(deletesql).executeUpdate();
				sess.createSQLQuery(delSqlErrorList).executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String apsql = "";

			String dataExiestSql = "select count(1) from a01 where a0000 in (" + peopleid + ")";
			CommonQueryBS.systemOut(new Date().toString() + " 校验sql" + dataExiestSql);
			List<Object> list = sess.createSQLQuery(dataExiestSql).list();

			if (list == null || list.size() < 1 || Long.parseLong(list.get(0).toString()) < 1) {
				throw new AppException("未查找到需要校验的数据！");
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
				 * 抽数的标准： 人员职务所挂职单位节点下， 是否包含非在职有用户判断
				 */
//				if(tables.equals("A02")){
//					CreateSQL="create table "+table+"_"+suffix+" as select * from "+table+" WHERE "+table+".a0000 in ("+peopleid+") and (x.a0255='1' or x.a0281='true') ";
//				}else{
				CreateSQL = "create table " + table + "_L_" + suffix + " as select * from " + table + " WHERE " + table
						+ ".a0000 in (" + peopleid + ")";

//				}
				CommonQueryBS.systemOut("建表语句sql" + CreateSQL);
				sess.createSQLQuery(CreateSQL).executeUpdate();
			}
			String b01_verify = "create table b01_L_" + suffix + " as select * from B01 WHERE B01.b0111 LIKE '" + b0111
					+ "%'";
			CommonQueryBS.systemOut("机构建表语句sql" + b01_verify);
			sess.createSQLQuery(b01_verify).executeUpdate();
			ImpmodelThread.checkIndex("_L_" + suffix, tables);
			String[] ruleid = ruleids.split(",");
			StringBuffer insertSqlBf = new StringBuffer("INSERT INTO VERIFY_ERROR_LIST_mirror (vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010)");
			String b01SqlTemp = " like '" + b0111 + "%' "; // 用于替换 【= :B0111】

			String a01ParamSql = ".A0000" + VerificationSchemeConfPageModel.EQUAL
					+ VerificationSchemeConfPageModel.A01_PARAM;
			String b01ParamSql = VerificationSchemeConfPageModel.EQUAL + VerificationSchemeConfPageModel.B01_PARAM;
		    String sqlVerify = null;
			long currentNum = 0;
			if (vp != null) {
				vp.setTotalNum(Long.parseLong(ruleid.length + ""));
				sess.update(vp);
			}
			// 循环校验规则
			for (String rule : ruleid) {
				VerifyRule vr = (VerifyRule) sess.get(VerifyRule.class, rule);
				String vru002Id = vr.getVru002();
				if (vp != null) {
					vp.setCurrentNum(++currentNum);
					vp.setProcessMsg("共" + totalDataNum + "条数据、" + vp.getTotalNum() + "条校验规则，正在校验第" + currentNum
							+ "个规则【" + vr.getVru002() + "】");
					PhotosUtil.saveLog("共" + totalDataNum + "条数据、" + vp.getTotalNum() + "条校验规则，正在校验第" + currentNum
							+ "个规则【" + vr.getVru002() + "】");
					PhotosUtil.saveLog("规则开始时间:" + new Date().toString());
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
				 * yinl 通过a01表过滤，a02表该条件可能为空 ap2sql =
				 * " and  EXISTS(select 1 from a01 a01 where a01.A0000 = x.A0000 and a01.A0163 = '1')  "
				 * ;
				 * 
				 * }
				 */
				/**
				 * else{ ap2sql = " and x.A0201B is not null "; }
				 **/
//				String a01SqlTemp = " EXISTS (SELECT 1 FROM A02  x WHERE x.a0000 = "+vr.getVru006()+".A0000 AND x.a0201b LIKE  '"+b0111+"%' "+ap2sql+") ";//用于替换 【xx.A0000 = :A0000】
				String a01SqlTemp = "";
				/*
				 * if(DBUtil.getDBType() == DBType.MYSQL){ a01SqlTemp = " "+vr.getVru006()
				 * +".A0000 IN (SELECT x.A0000 FROM A02  x WHERE  x.a0201b LIKE  '"+b0111+"%' "
				 * +ap2sql+") ";//用于替换 【xx.A0000 = :A0000】 }else { a01SqlTemp =
				 * " EXISTS (SELECT 1 FROM A02  x WHERE x.a0000 = "+vr.getVru006()
				 * +".A0000 AND x.a0201b LIKE  '"+b0111+"%' "+ap2sql+") ";//用于替换 【xx.A0000 =
				 * :A0000】 }
				 */
			    sqlVerify = vr.getVru009();
				if (StringUtil.isEmpty(sqlVerify) || "0".equals(vr.getVru007())) {
					continue;
				}
				// zxw 注意来源sql部分 :B0000与:A0000 是区分大小写的;并且SQL部分必须包含“:A0000”、“:B0111”部分
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
				// 去掉临时表标记
				sqlVerify = sqlVerify.replace(VerificationSchemeConfPageModel.TEMP_PREFIX,"_L_"+suffix);
				sqlVerify = insertSqlBf + "select "+vel001Bf+" vel001,tt.* from (" +sqlVerify +") tt";
				System.out.println("sql====>"+sqlVerify);
//				System.out.println("%  ====>"+currentNum/(float)vp.getTotalNum());
				// 简历判空做特殊处理
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
					if ("学历信息-无全日制学历".equals(vru002Id)) {
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
							CommonQueryBS.sysOutRtn("a08q_L_临时表创建失败");
						}

					}
					if ("家庭成员称谓-缺少父亲或母亲信息".equals(vru002Id)) {
						try {
							String sql = "create table A36f_L_" + suffix
									+ " as select a0000 from A36 WHERE a3604a='父亲' or  a3604a='继父' or  a3604a='养父'";
							String sql1 = "create table A36m_L_" + suffix
									+ " as select a0000 from A36 WHERE a3604a='母亲' or  a3604a='继母' or  a3604a='养母'";

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
							CommonQueryBS.sysOutRtn("A36f_L_、A36m_L_临时表创建失败");
						}

					}
				} else {
					if ("学历信息-无全日制学历".equals(vru002Id)) {
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
							CommonQueryBS.sysOutRtn("A08q_L_临时表创建失败");
						}

					}
					if ("家庭成员称谓-缺少父亲或母亲信息".equals(vru002Id)) {
						try {
							String sql = "create table A36f_L_" + suffix
									+ " as select a0000 from A36 WHERE a3604a='父亲' or  a3604a='继父' or  a3604a='养父'";
							String sql1 = "create table A36m_L_" + suffix
									+ " as select a0000 from A36 WHERE a3604a='母亲' or  a3604a='继母' or  a3604a='养母'";

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
							CommonQueryBS.sysOutRtn("A36f_L_、A36m_L_临时表创建失败");
						}

					}

				}

				PhotosUtil.saveLog("正在执行的sql--> "+sqlVerify);
				//PhotosUtil.saveLog("正在执行的sql--> " + personSqlVerify);
				// System.out.println(sqlVerify);
				try {
//					if(sqlVerify.contains("机构编码-J-不符合编码规范")){
					CommonQueryBS.systemOut("人员信息校核SQL--> "+sqlVerify);
					//CommonQueryBS.systemOut("人员信息校核SQL--> " + personSqlVerify);
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
			} // end for 循环校验规则
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
			throw new AppException("校验发生异常：" + e.getMessage());
		} finally {
			
		}
		String dropb01 = "drop table B01_L_"+suffix+" ";
		// 删除临时表
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
		String deleteSql = "delete from verify_error_list where vel010='" + userid + "'";//清除历史校核记录
		sess.createSQLQuery(deleteSql).executeUpdate();
		String insersql = "insert into verify_error_list ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010)  select "
				+ vel001Bf + " vel001,tt.* from "
				+ " (select vel002,vru003,vel003,vel004,vel005,replace(to_char(wm_concat(vel006)),',','、'),vel007,vel008,vel009,vel010 from "
				+ " (select  vel002,'5' vru003,vel003,vel004,vel005,concat((case when vru003='1' then '错误:' else '提示:' end ),to_char(wm_concat(vel006))) vel006,'' vel007,''vel008,'' vel009,vel010"
				+ " from verify_error_list_mirror where vel005 = '" + bstype + "' and vel004 = '" + b0111
				+ "' and vel010='" + userid
				+ "' and vel001 not in (SELECT a.vel001 from verify_error_list_mirror a ,verify_rule_detail b where b.VEL002=a.VEL002 and b.VEL009=a.VEL009 and a.vel010=b.vel010 and a.vel010='"
				+ userid + "') group by vel002,vel003,vel004,vel005,vel010,vru003 " + "  ) vel"
				+ " group by vel002,vru003,vel003,vel004,vel005,vel007,vel008,vel009,vel010) tt";
		if (DBUtil.getDBType() == DBType.MYSQL) {
			insersql = insersql.replace("to_char(wm_concat(vel006))", "group_concat(vel006  order by vel006 desc)");
		}

		CommonQueryBS.systemOut(new Date().toString() + "校验sql:" + insersql);
		sess.createSQLQuery(insersql).executeUpdate();
		String deletesql = "delete from verify_error_list where vel004 = '" + b0111 + "' and vru003<>'5' and vel005='"
				+ bstype + "' and vel010='" + userid + "'";
		CommonQueryBS.systemOut(new Date().toString() + "校验sql:" + deletesql);
		sess.createSQLQuery(deletesql).executeUpdate();

	}

	public void updatevelbyimpid(String imprecordid, String bstype, String userid) {

		String vel001Bf = " sys_guid()  ";
		if (DBUtil.getDBType() == DBType.MYSQL) {
			vel001Bf = " UUID() ";
		}
		long startTime1 = System.currentTimeMillis(); // 获取开始时间
		String insersql = "";
		insersql = "insert into verify_error_list ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010)  select "
				+ vel001Bf + " vel001,tt.* from"
				+ "(select vel002,vru003,vel003,vel004,vel005,replace(to_char(wm_concat(vel006)),',','；'),vel007,vel008,vel009,vel010 from "
				+ "(select  vel002,'5' vru003,vel003,vel004,vel005,concat((case when vru003='1' then '错误:' else '提示:' end ),to_char(wm_concat(vel006))) vel006,'' vel007,''vel008,'' vel009,vel010"
				+ " from verify_error_list  where vel005 = '" + bstype + "' and vel004 = '" + imprecordid
				+ "' and vel010='" + userid + "' group by vel002,vel003,vel004,vel005,vel010,vru003" + ") vel"
				+ " group by vel002,vru003,vel003,vel004,vel005,vel007,vel008,vel009,vel010) tt";
		if (DBUtil.getDBType() == DBType.MYSQL) {
			insersql = insersql.replace("to_char(wm_concat(vel006))", "group_concat(vel006 order by vel006 desc)");
		}
		HBSession sess = HBUtil.getHBSession();
		sess.createSQLQuery(insersql).executeUpdate();
		long endTime1 = System.currentTimeMillis(); // 获取结束时间
		System.out.println("整合时间" + (endTime1 - startTime1) + "ms"); // 输出程序运行时间
		System.out.println("整合sql" + insersql);
		long startTime2 = System.currentTimeMillis(); // 获取开始时间
		String deletesql = "delete from verify_error_list where vel004 = '" + imprecordid
				+ "' and (vru003<>'5' and vru003<>'9')and vel005='" + bstype + "' and vel010='" + userid + "'";
		sess.createSQLQuery(deletesql).executeUpdate();
		long endTime2 = System.currentTimeMillis(); // 获取结束时间
		System.out.println("删除时间" + (endTime2 - startTime2) + "ms"); // 输出程序运行时间
		System.out.println("删除sql" + deletesql);

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
				+ " (select vel002,vru003,vel003,vel004,vel005,replace(to_char(wm_concat(vel006)),',','；'),vel007,vel008,vel009,vel010 from "
				+ " (select  vel002,'5' vru003,vel003,vel004,vel005,concat((case when vru003='1' then '错误:' else '提示:' end ),to_char(wm_concat(vel006))) vel006,'' vel007,''vel008,'' vel009,vel010"
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
				+ " (select vel002,vru003,vel003,vel004,vel005,replace(to_char(wm_concat(vel006)),',','；'),vel007,vel008,vel009,vel010 from "
				+ " (select  vel002,'5' vru003,vel003,vel004,vel005,concat((case when vru003='1' then '错误:' else '提示:' end ),to_char(wm_concat(vel006))) vel006,'' vel007,''vel008,'' vel009,vel010"
				+ " from verify_error_list_mirror  where vel005 = '" + bstype
				+ "' and vel004 in (select a0000 from a01_L_" + suffix + ") and vel010='" + userid+"' and vel001 not in (SELECT a.vel001 from verify_error_list_mirror a ,verify_rule_detail b where b.VEL002=a.VEL002 and b.VEL009=a.VEL009) "
				+ " group by vel002,vel003,vel004,vel005,vel010,vru003" + " ) vel"
				+ "  group by vel002,vru003,vel003,vel004,vel005,vel007,vel008,vel009,vel010) tt";
		if (DBUtil.getDBType() == DBType.MYSQL) {
			insersql = insersql.replace("to_char(wm_concat(vel006))", "group_concat(vel006  order by vel006 desc)");
		}
		HBSession sess = HBUtil.getHBSession();
		CommonQueryBS.systemOut(new Date().toString() + "校验sql:" + insersql);
		sess.createSQLQuery(insersql).executeUpdate();
		String deletesql = "delete from verify_error_list where vel004 in (select a0000 from a01_L_" + suffix
				+ ") and vru003<>'5' and vel005='" + bstype + "' and vel010='" + userid + "'";
		CommonQueryBS.systemOut(new Date().toString() + "校验sql:" + deletesql);
		sess.createSQLQuery(deletesql).executeUpdate();

	}

	// 实现对整库校核数据统计功能
	private void tjCheckInfo(String[] tables, List<VerifyRule> vriList, String suffix, String b0111, String userid,
			String VSC001) throws AppException {
		/**
		 * 控制只能会审校核才能进行,报告的输出
		 */
		// 按照用户删除
		HBUtil.executeUpdate("delete from CHECK_TJ where userid = '" + userid + "' ");
		// 按照规则删除
//		HBUtil.executeUpdate("delete from CHECK_TJ where userid = '"+userid+"' and check_vsc001='"+VSC001+"' ");
		// 注意：这是校核报告处理的关键位置，因为功能只限制在会审使用。注意修改这边对应额校核方案的代码
		if (!"240AC5C1EABB4729BA04E47062B40D94".equals(VSC001)) {
			return;
		}
		SimpleDateFormat aDate = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		String date = aDate.format(new Date());
		HBSession sess = HBUtil.getHBSession();
		Connection connection = sess.connection();
		PreparedStatement prestate = null;
		CommonQueryBS.systemOut("开始进行校核信息统计...");
		try {
			connection.setAutoCommit(false);
			// 获取查询总数量
			HashMap<String, String> map = new HashMap<String, String>();
			for (String table : tables) {
				map.put(table.toUpperCase(), HBUtil.getValueFromTab("count(1)", table + "_" + suffix, "1=1"));
			}
			map.put("B01", HBUtil.getValueFromTab("count(1)", "B01_" + suffix, "1=1"));

			prestate = connection.prepareStatement("insert into CHECK_TJ(" + "CHECKID,"// UUID1
					+ "USERID,"// 操作用户2
					+ "CODE_TABLE,"// 信息集3
					+ "COL_CODE,"// 信息项4
					+ "CHECK_ALL,"// 校核总数5
					+ "CHECK_NULL,"// 校核空6
					+ "CHECK_ERROR,"// 校核错误7
					+ "CHECK_B0111,"// 校核范围8
					+ "CHECK_PHOTO,"// 照片类型9
					+ "CHECK_TIME,"// 时间10
					+ "CHECK_VRU002,"// 规则描述11
					+ "CHECK_VSC001,"// 校核方案12
					+ "CODE_TABLE_NAME,"// 信息集3
					+ "COL_NAME"// 信息项4
					+ ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			String table_code, num;
			Map<String, String> columnAndName = getColumnAndName();
			CommonQueryBS.systemOut("进行统计校核规则条数：" + vriList.size());
			// 循环校验规则
			for (VerifyRule vr : vriList) {
				prestate.setObject(1, UUID.randomUUID().toString().replaceAll("-", ""));
				prestate.setObject(2, userid);
				table_code = vr.getVru004().toUpperCase();
				// 将信息插入到校核信息集
				prestate.setObject(3, table_code);
				// 将信息插入到校核信息项
				prestate.setObject(4, vr.getVru005().toUpperCase());
				prestate.setObject(5, map.get(table_code));
				num = HBUtil.getValueFromTab("count(1)", "verify_error_list",
						"vel009='" + vr.getVru001() + "' and vel010='" + userid + "' and vel004='" + b0111 + "'");
				if (vr.getVru008().contains("-字段为空")) {// 判断是否为 - 信息项为空数量
					prestate.setObject(6, num);
					prestate.setObject(7, "0");
				} else {// 否则为 - 信息项错误数量
					prestate.setObject(6, "0");
					prestate.setObject(7, num);
				}
				prestate.setObject(8, b0111);
				if ("A57".equals(table_code)) {// 判断照片类型
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
			 * finally{ //关闭照片 try { prestate.close(); } catch (SQLException e) {
			 * e.printStackTrace(); } try { connection.close(); } catch (SQLException e) {
			 * e.printStackTrace(); } }
			 */
		CommonQueryBS.systemOut("校核信息统计...结束!");
	}

	public Map<String, String> getColumnAndName() {
		CommQuery commQuery = new CommQuery();
		List<HashMap<String, Object>> listBySQL;
		Map<String, String> map = new LinkedHashMap<String, String>();
		try {
			listBySQL = commQuery.getListBySQL("select" + " TABLE_NAME,"// 姓名
					+ " COL_CODE,"// A0101
					+ " CODE_TABLE,"// A01
					+ " CODE_NAME"// 基本情况信息集
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
			CommonQueryBS.systemOut("获取字段信息时，错误！");
		}
		return map;
	}

}