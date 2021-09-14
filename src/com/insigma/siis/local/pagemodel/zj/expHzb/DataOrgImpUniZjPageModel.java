package com.insigma.siis.local.pagemodel.zj.expHzb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A31;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.A41;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.sysrule.SysRuleBS;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.business.utils.Xml4HZBUtil;
import com.insigma.siis.local.business.utils.Xml4Zb3Util;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;

public class DataOrgImpUniZjPageModel extends PageModel {

    @PageEvent("expbtn.onclick")
    public int expbtn(String name) throws RadowException {
	String tabimp = this.getPageElement("tabimp").getValue();
	try {
	    if (tabimp == null || tabimp.equals("") || tabimp.equals("1")) {
		String gjgs = this.getPageElement("gjgs").getValue();
		if (gjgs != null && gjgs.equals("1")) {
		    expbtn2(name);
		} else {
		    this.setMainMessage("数据不全");
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return EventRtnType.NORMAL_SUCCESS;
    }

    private void expbtn2(String name) throws RadowException {
	try {
	    String id = UUID.randomUUID().toString().replace("-", "");
	    String tabimp = this.getPageElement("tabimp").getValue();
	    CommonQueryBS.systemOut(DateUtil.getTime());
	    if (tabimp == null || tabimp.equals("") || tabimp.equals("1")) {
		String gjgs = this.getPageElement("gjgs").getValue();
		String fxzry = this.getPageElement("fxzry").getValue();
		String zdcjg = this.getPageElement("zdcjg").getValue();
		String gzlbry = this.getPageElement("gzlbry").getValue();
		String gllbry = this.getPageElement("gllbry").getValue();
		String searchDeptid = this.getPageElement("searchDeptid").getValue();
		String linkpsn = this.getPageElement("linkpsn").getValue();
		String linktel = this.getPageElement("linktel").getValue();
		String remark = this.getPageElement("remark").getValue();
		// 通用格式标记
		String sign = "zip";// 7z
		if (searchDeptid == null || "".equals(searchDeptid.trim())) {
		    this.setMainMessage("机构不能为空!");
		    return;
		}
		String B0114Sql = "SELECT B01.B0114 FROM B01 WHERE B01.B0111 = '" + searchDeptid + "'";
		Object b0114 = HBUtil.getHBSession().createSQLQuery(B0114Sql).uniqueResult();
		if (b0114 == null || "".equals(b0114.toString().trim())) {
		    this.setMainMessage("导出机构的机构编码不能为空!");
		    return;
		}

		String gz_lb = "";
		String gl_lb = "";
		List list = HBUtil.getHBSession().createQuery(" from CodeValue t where  codeType='ZB125' order by codeValue").list();
		if (list != null && list.size() > 0) {
		    for (int i = 0; i < list.size(); i++) {
			CodeValue code = (CodeValue) list.get(i);
			int k = i + 1;
			String gzlb = "gz_" + k;
			String gl_index = this.getPageElement(gzlb).getValue();
			gz_lb = gz_lb + (gl_index.equals("0") ? "" : "'" + code.getCodeValue() + "',");
		    }
		}
		List list2 = HBUtil.getHBSession().createQuery(" from CodeValue t where  codeType='ZB130' order by codeValue").list();
		if (list2 != null && list2.size() > 0) {
		    for (int i = 0; i < list2.size(); i++) {
			CodeValue code = (CodeValue) list2.get(i);
			int k = i + 1;
			String gllb = "gl_" + k;
			String gl_index = this.getPageElement(gllb).getValue();
			gl_lb = gl_lb + (gl_index.equals("0") ? "" : "'" + code.getCodeValue() + "',");
		    }
		}

		if (zdcjg.equals("0")) {
		    if (gz_lb.equals("")) {
			this.setMainMessage("至少设置一个工作人员类别!");
			return;
		    }
		    if (gl_lb.equals("")) {
			this.setMainMessage("至少设置一个管理人员类别!");
			return;
		    }
		}
		CurrentUser user = SysUtil.getCacheCurrentUser(); // 获取当前执行导入的操作人员信息
		KingbsconfigBS.saveImpDetailInit3(id);
		UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
		ZjDataOrgExpNewThread thr = new ZjDataOrgExpNewThread(id, user, gjgs, fxzry, gzlbry, gllbry, searchDeptid, linkpsn, linktel, remark, gz_lb, gl_lb, tabimp, zdcjg, userVo, sign);
		new Thread(thr, "数据导出线程1").start();
		this.setRadow_parent_data(id);
		this.getExecuteSG().addExecuteCode("$h.openWin('refreshWin','pages.dataverify.RefreshOrgExp','导出详情',600,300,'" + id + "','" + request.getContextPath() + "');");
		this.getExecuteSG().addExecuteCode("parent.Ext.getCmp(subWinId).close(); ");
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    @PageEvent("expContinue")
    public int expContinue(String id) throws RadowException {
	String gjgs = this.getPageElement("gjgs").getValue();
	String fxzry = this.getPageElement("fxzry").getValue();
	String tabimp = this.getPageElement("tabimp").getValue();
	String zdcjg = this.getPageElement("zdcjg").getValue();
	String gzlbry = this.getPageElement("gzlbry").getValue();
	String gllbry = this.getPageElement("gllbry").getValue();
	String searchDeptid = this.getPageElement("searchDeptid").getValue();
	String linkpsn = this.getPageElement("linkpsn").getValue();
	String linktel = this.getPageElement("linktel").getValue();
	String remark = this.getPageElement("remark").getValue();

	// 通用格式标记
	String sign = "TYGS";

	try {
	    String gz_lb = "";
	    String gl_lb = "";
	    List list = HBUtil.getHBSession().createQuery(" from CodeValue t where  codeType='ZB125' order by codeValue").list();
	    if (list != null && list.size() > 0) {
		for (int i = 0; i < list.size(); i++) {
		    CodeValue code = (CodeValue) list.get(i);
		    int k = i + 1;
		    String gzlb = "gz_" + k;
		    String gl_index = this.getPageElement(gzlb).getValue();
		    gz_lb = gz_lb + (gl_index.equals("0") ? "" : "'" + code.getCodeValue() + "',");
		}
	    }
	    List list2 = HBUtil.getHBSession().createQuery(" from CodeValue t where  codeType='ZB130' order by codeValue").list();
	    if (list2 != null && list2.size() > 0) {
		for (int i = 0; i < list2.size(); i++) {
		    CodeValue code = (CodeValue) list2.get(i);
		    int k = i + 1;
		    String gllb = "gl_" + k;
		    String gl_index = this.getPageElement(gllb).getValue();
		    gl_lb = gl_lb + (gl_index.equals("0") ? "" : "'" + code.getCodeValue() + "',");
		}
	    }
	    CurrentUser user = SysUtil.getCacheCurrentUser(); // 获取当前执行导入的操作人员信息
	    KingbsconfigBS.saveImpDetailInit3(id);
	    UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
	    ZjDataOrgExpNewThread thr = new ZjDataOrgExpNewThread(id, user, gjgs, fxzry, gzlbry, gllbry, searchDeptid, linkpsn, linktel, remark, gz_lb, gl_lb, tabimp, zdcjg, userVo, sign);
	    new Thread(thr, "数据导出线程1").start();
	    this.setRadow_parent_data(id);
	    this.getExecuteSG().addExecuteCode("$h.openWin('refreshWin','pages.dataverify.RefreshOrgExp','导出详情',600,150,'" + id + "','" + request.getContextPath() + "');");
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return EventRtnType.NORMAL_SUCCESS;
    }

    private String getTempPath() {
	String localp = AppConfig.LOCAL_FILE_BASEURL;
	try {
	    localp = URLDecoder.decode(localp, "GBK");
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}
	String uuid = UUID.randomUUID().toString().replace("-", "");
	String rootPath = "";

	// 上传路径
	String upload_file = rootPath + "/temp/";
	try {
	    File file = new File(upload_file);
	    // 如果文件夹不存在则创建
	    if (!file.exists() && !file.isDirectory()) {
		file.mkdirs();
	    }
	} catch (Exception e1) {
	    e1.printStackTrace();
	}
	String tempp = upload_file + uuid + "/";
	return tempp;
    }

    @PageEvent("expbtn1.onclick")
    public int expbtn1(String name) throws RadowException {
	String tabimp = this.getPageElement("tabimp").getValue();
	Map<String, String> map = new HashMap<String, String>();
	String infile = "";
	try {
	    CommonQueryBS.systemOut(DateUtil.getTime());
	    if (tabimp == null || tabimp.equals("") || tabimp.equals("1")) {
		String gjgs = this.getPageElement("gjgs").getValue();
		String ltry = this.getPageElement("ltry").getValue();

		String zdcjg = this.getPageElement("zdcjg").getValue();
		String gzlbry = this.getPageElement("gzlbry").getValue();
		String gllbry = this.getPageElement("gllbry").getValue();
		String searchDeptid = this.getPageElement("searchDeptid").getValue();
		String linkpsn = this.getPageElement("linkpsn").getValue();
		String linktel = this.getPageElement("linktel").getValue();
		String remark = this.getPageElement("remark").getValue();
		String gz_1 = this.getPageElement("gz_1").getValue();
		String gz_2 = this.getPageElement("gz_2").getValue();
		String gz_3 = this.getPageElement("gz_3").getValue();
		String gz_4 = this.getPageElement("gz_4").getValue();
		String gz_5 = this.getPageElement("gz_5").getValue();
		String gz_6 = this.getPageElement("gz_6").getValue();
		String gz_7 = this.getPageElement("gz_7").getValue();
		String gz_8 = this.getPageElement("gz_8").getValue();
		String gz_9 = this.getPageElement("gz_9").getValue();
		String gz_10 = this.getPageElement("gz_10").getValue();
		String gz_11 = this.getPageElement("gz_11").getValue();
		String gz_12 = this.getPageElement("gz_12").getValue();
		String gz_13 = this.getPageElement("gz_13").getValue();

		String gl_1 = this.getPageElement("gl_1").getValue();
		String gl_2 = this.getPageElement("gl_2").getValue();
		String gl_3 = this.getPageElement("gl_3").getValue();
		String gl_4 = this.getPageElement("gl_4").getValue();
		String gl_5 = this.getPageElement("gl_5").getValue();

		String gz_lb = "" + (gz_1.equals("0") ? "" : "'1',") + (gz_2.equals("0") ? "" : "'2',") + (gz_3.equals("0") ? "" : "'3',") + (gz_4.equals("0") ? "" : "'5',") + (gz_5.equals("0") ? "" : "'6',") + (gz_6.equals("0") ? "" : "'7',") + (gz_7.equals("0") ? "" : "'8',") + (gz_8.equals("0") ? "" : "'A4',") + (gz_9.equals("0") ? "" : "'A5',") + (gz_10.equals("0") ? "" : "'A6',") + (gz_11.equals("0") ? "" : "'A0',") + (gz_12.equals("0") ? "" : "'A1',") + (gz_13.equals("0") ? "" : "'A2',");
		String gl_lb = "" + (gl_1.equals("0") ? "" : "'01',") + (gl_2.equals("0") ? "" : "'02',") + (gl_3.equals("0") ? "" : "'03',") + (gl_4.equals("0") ? "" : "'04',") + (gl_5.equals("0") ? "" : "'09',");
		if (zdcjg.equals("0")) {
		    if (gz_lb.equals("")) {
			this.setMainMessage("至少设置一个工作类别!");
			return EventRtnType.NORMAL_SUCCESS;
		    }
		    if (gl_lb.equals("")) {
			this.setMainMessage("至少设置一个管理类别!");
			return EventRtnType.NORMAL_SUCCESS;
		    }
		}
		HBSession sess = HBUtil.getHBSession();
		B01 b01 = (B01) sess.get(B01.class, searchDeptid);
		// ------------ -------------//
		StringBuilder b = new StringBuilder();
		if (DBUtil.getDBType().equals(DBType.MYSQL)) {
		    b.append("select a1.a0000 from a01 a1 LEFT JOIN a02 a2 on a1.a0000=a2.a0000 where 1=1");
		    if (ltry.equals("0")) {
			b.append(" and a1.status<>'3'");
		    }
		    if (!gz_lb.equals("")) {
			if (gzlbry.equals("0")) {
			    b.append(" and a1.a0160 in (" + gz_lb.substring(0, gz_lb.length() - 1) + ")");
			}
		    }
		    if (!gl_lb.equals("")) {
			if (gllbry.equals("0")) {
			    b.append(" and a1.a0165 in (" + gl_lb.substring(0, gl_lb.length() - 1) + ")");
			}
		    }
		    b.append(" and a2.a0201b in (select mt.b0111 from (select b0111 from b01 where b0111 like '" + searchDeptid + "%') mt)");
		    CommonQueryBS.systemOut(b.toString());
		} else if (DBUtil.getDBType().equals(DBType.ORACLE)) {
		    b.append("select a1.a0000 from a01 a1,a02 a2 where a1.a0000=a2.a0000(+)");
		    if (ltry.equals("0")) {
			b.append(" and a1.status<>'3'");
		    }
		    if (!gz_lb.equals("")) {
			b.append(" and a1.a0160 in (" + gz_lb.substring(0, gz_lb.length() - 1) + ")");
		    }
		    if (!gl_lb.equals("")) {
			b.append(" and a1.a0165 in (" + gl_lb.substring(0, gl_lb.length() - 1) + ")");
		    }
		    b.append(" and a2.a0201b in (select b0111 from b01 start with b0111='" + searchDeptid + "' connect by prior b0111= b0121)");
		}
		List<B01> list16 = null;
		if (DBUtil.getDBType().equals(DBType.MYSQL)) {
		    list16 = sess.createSQLQuery("select * from b01 where b0111 like '" + searchDeptid + "%'").addEntity(B01.class).list();
		} else if (DBUtil.getDBType().equals(DBType.ORACLE)) {
		    list16 = sess.createSQLQuery("select * from b01 start with b0111='" + searchDeptid + "' connect by prior b0111= b0121").addEntity(B01.class).list();
		}
		List<A01> list = sess.createSQLQuery("select * from a01 where a0000 in(" + b.toString() + ")").addEntity(A01.class).list();
		List<A02> list2 = sess.createSQLQuery("select * from A02 where a0000 in(" + b.toString() + ")").addEntity(A02.class).list();
		List<A06> list3 = (!zdcjg.equals("0")) ? new ArrayList<A06>() : sess.createSQLQuery("select * from A06 where a0000 in(" + b.toString() + ")").addEntity(A06.class).list();
		List<A08> list4 = (!zdcjg.equals("0")) ? new ArrayList<A08>() : sess.createSQLQuery("select * from A08 where a0000 in(" + b.toString() + ")").addEntity(A08.class).list();
		List<A11> list5 = (!zdcjg.equals("0")) ? new ArrayList<A11>() : sess.createSQLQuery("select * from A11 where a0000 in(" + b.toString() + ")").addEntity(A11.class).list();

		List<A14> list6 = (!zdcjg.equals("0")) ? new ArrayList<A14>() : sess.createSQLQuery("select * from A14 where a0000 in(" + b.toString() + ")").addEntity(A14.class).list();
		List<A15> list7 = (!zdcjg.equals("0")) ? new ArrayList<A15>() : sess.createSQLQuery("select * from A15 where a0000 in(" + b.toString() + ")").addEntity(A15.class).list();
		List<A29> list8 = (!zdcjg.equals("0")) ? new ArrayList<A29>() : sess.createSQLQuery("select * from A29 where a0000 in(" + b.toString() + ")").addEntity(A29.class).list();
		List<A30> list9 = (!zdcjg.equals("0")) ? new ArrayList<A30>() : sess.createSQLQuery("select * from A30 where a0000 in(" + b.toString() + ")").addEntity(A30.class).list();
		List<A31> list10 = (!zdcjg.equals("0")) ? new ArrayList<A31>() : sess.createSQLQuery("select * from A31 where a0000 in(" + b.toString() + ")").addEntity(A31.class).list();

		List<A36> list11 = (!zdcjg.equals("0")) ? new ArrayList<A36>() : sess.createSQLQuery("select * from A36 where a0000 in(" + b.toString() + ")").addEntity(A36.class).list();
		List<A37> list12 = (!zdcjg.equals("0")) ? new ArrayList<A37>() : sess.createSQLQuery("select * from A37 where a0000 in(" + b.toString() + ")").addEntity(A37.class).list();
		List<A41> list13 = (!zdcjg.equals("0")) ? new ArrayList<A41>() : sess.createSQLQuery("select * from A41 where a0000 in(" + b.toString() + ")").addEntity(A41.class).list();
		List<A53> list14 = (!zdcjg.equals("0")) ? new ArrayList<A53>() : sess.createSQLQuery("select * from A53 where a0000 in(" + b.toString() + ")").addEntity(A53.class).list();
		List<A57> list15 = (!zdcjg.equals("0")) ? new ArrayList<A57>() : sess.createSQLQuery("select * from A57 where a0000 in(" + b.toString() + ")").addEntity(A57.class).list();
		List<Map> list17 = new ArrayList<Map>();
		if (zdcjg.equals("0")) {
		    map.put("type", "21"); // 导出全数据
		} else {
		    map.put("type", "22"); // 导出机构数据
		}
		map.put("time", DateUtil.timeToString(DateUtil.getTimestamp()));
		// ??
		map.put("dataversion", "20121221");
		map.put("psncount", (list != null && list.size() > 0) ? (list.size() + "") : "");
		map.put("photodir", "Photos");
		map.put("B0101", b01.getB0101());
		map.put("B0111", b01.getB0111());
		map.put("B0114", b01.getB0114());
		map.put("B0194", b01.getB0194());
		map.put("linkpsn", linkpsn);
		map.put("linktel", linktel);
		map.put("remark", remark);
		list17.add(map);
		String path = getPath();
		String zippath = path + "按机构导出文件_" + b01.getB0111() + "_" + b01.getB0101() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") + "/";
		File file = new File(zippath);
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
		    file.mkdirs();
		}
		String zippathtable = path + "按机构导出文件_" + b01.getB0111() + "_" + b01.getB0101() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") + "/Table/";
		File file1 = new File(zippathtable);
		// 如果文件夹不存在则创建
		if (!file1.exists() && !file1.isDirectory()) {
		    file1.mkdirs();
		}
		String zipfile = path + "按机构导出文件_" + b01.getB0111() + "_" + b01.getB0101() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") + ".7z";
		if (gjgs != null && gjgs.equals("1")) {
		    zipfile = path + "按机构导出文件_" + b01.getB0114() + "_" + b01.getB0101() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") + ".hzb";
		    Xml4HZBUtil.List2Xml(list, "A01", zippath);
		    Xml4HZBUtil.List2Xml(list2, "A02", zippath);
		    Xml4HZBUtil.List2Xml(list3, "A06", zippath);
		    Xml4HZBUtil.List2Xml(list4, "A08", zippath);
		    Xml4HZBUtil.List2Xml(list5, "A11", zippath);

		    Xml4HZBUtil.List2Xml(list6, "A14", zippath);
		    Xml4HZBUtil.List2Xml(list7, "A15", zippath);
		    Xml4HZBUtil.List2Xml(list8, "A29", zippath);
		    Xml4HZBUtil.List2Xml(list9, "A30", zippath);
		    Xml4HZBUtil.List2Xml(list10, "A31", zippath);

		    Xml4HZBUtil.List2Xml(list11, "A36", zippath);
		    Xml4HZBUtil.List2Xml(list12, "A37", zippath);
		    Xml4HZBUtil.List2Xml(list13, "A41", zippath);
		    Xml4HZBUtil.List2Xml(list14, "A53", zippath);
		    Xml4HZBUtil.List2Xml(list15, "A57", zippath);

		    Xml4HZBUtil.List2Xml(list16, "B01", zippath);
		    Xml4HZBUtil.List2Xml(list17, "info", zippath);
		} else if (gjgs != null && gjgs.equals("2")) {
		    zipfile = path + "按机构导出文件_" + b01.getB0114() + "_" + b01.getB0101() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") + ".zb3";
		    Xml4Zb3Util.List2Xml(list, "A01", zippath);
		    Xml4Zb3Util.List2Xml(list2, "A02", zippath);
		    Xml4Zb3Util.List2Xml(list3, "A06", zippath);
		    Xml4Zb3Util.List2Xml(list4, "A08", zippath);
		    Xml4Zb3Util.List2Xml(list5, "A11", zippath);

		    Xml4Zb3Util.List2Xml(list6, "A14", zippath);
		    Xml4Zb3Util.List2Xml(list7, "A15", zippath);
		    Xml4Zb3Util.List2Xml(list8, "A29", zippath);
		    Xml4Zb3Util.List2Xml(list9, "A30", zippath);
		    Xml4Zb3Util.List2Xml(list10, "A31", zippath);

		    Xml4Zb3Util.List2Xml(list11, "A36", zippath);
		    Xml4Zb3Util.List2Xml(list12, "A37", zippath);
		    Xml4Zb3Util.List2Xml(list13, "A41", zippath);
		    Xml4Zb3Util.List2Xml(list14, "A53", zippath);
		    Xml4Zb3Util.List2Xml(list15, "A57", zippath);

		    Xml4Zb3Util.List2Xml(list16, "B01", zippath);
		    Xml4Zb3Util.List2Xml(list17, "info", zippath);
		    String frompath = getRootPath() + "/uploud/wlhxml/";
		    UploadHelpFileServlet.copyDirectiory(frompath, zippathtable);

		}
		if (list15 != null && list15.size() > 0) {
		    String photopath = zippath + "Photos/";
		    File file2 = new File(photopath);
		    // 如果文件夹不存在则创建
		    if (!file2.exists() && !file2.isDirectory()) {
			file2.mkdirs();
		    }
		    for (int i = 0; i < list15.size(); i++) {
			A57 a57 = list15.get(i);
			if (a57.getA0000() != null && !a57.getA0000().equals("") && a57.getPhotodata() != null) {
			    File f = new File(photopath + a57.getA0000() + ".jpg");
			    FileOutputStream fos = new FileOutputStream(f);
			    InputStream is = a57.getPhotodata().getBinaryStream();// 读出数据后转换为二进制流
			    byte[] data = new byte[1024];
			    while (is.read(data) != -1) {
				fos.write(data);
			    }
			    fos.close();
			    is.close();
			}
		    }
		}
		infile = zipfile;
		SevenZipUtil.zip7z(zippath, zipfile, ((gjgs != null && gjgs.equals("2")) ? "1234" : "1234"));
		if (gjgs != null && gjgs.equals("2")) {
		    new LogUtil().createLog("421", "IMP_RECORD", "", "", "数据导出", new ArrayList());
		} else {
		    new LogUtil().createLog("422", "IMP_RECORD", "", "", "数据导出", new ArrayList());
		}
	    }
	    CommonQueryBS.systemOut(DateUtil.getTime());
	    this.getPageElement("downfile").setValue(infile.replace("\\", "/"));

	    this.getExecuteSG().addExecuteCode("window.reloadTree()");
	} catch (Exception e) {
	    this.setMainMessage("导出失败，请重试！");
	    e.printStackTrace();
	}
	return EventRtnType.NORMAL_SUCCESS;
    }

    @PageEvent("exppackagebtn.onclick")
    public int exppackagebtn(String name) throws RadowException {
	String tabimp = this.getPageElement("tabimp").getValue();
	Map<String, String> map = new HashMap<String, String>();
	String infile = "";
	int packcount = 20000;
	try {
	    if (tabimp == null || tabimp.equals("") || tabimp.equals("1")) {
		String searchDeptid = this.getPageElement("searchDeptid").getValue();
		String linkpsn = this.getPageElement("linkpsn").getValue();
		String linktel = this.getPageElement("linktel").getValue();
		String remark = this.getPageElement("remark").getValue();
		String gz_1 = this.getPageElement("gz_1").getValue();
		String gz_2 = this.getPageElement("gz_2").getValue();
		String gz_3 = this.getPageElement("gz_3").getValue();
		String gz_4 = this.getPageElement("gz_4").getValue();
		String gz_5 = this.getPageElement("gz_5").getValue();
		String gz_6 = this.getPageElement("gz_6").getValue();
		String gz_7 = this.getPageElement("gz_7").getValue();
		String gz_8 = this.getPageElement("gz_8").getValue();
		String gz_9 = this.getPageElement("gz_9").getValue();
		String gz_10 = this.getPageElement("gz_10").getValue();
		String gz_11 = this.getPageElement("gz_11").getValue();
		String gz_12 = this.getPageElement("gz_12").getValue();
		String gz_13 = this.getPageElement("gz_13").getValue();

		String gl_1 = this.getPageElement("gl_1").getValue();
		String gl_2 = this.getPageElement("gl_2").getValue();
		String gl_3 = this.getPageElement("gl_3").getValue();
		String gl_4 = this.getPageElement("gl_4").getValue();
		String gl_5 = this.getPageElement("gl_5").getValue();

		String gz_lb = "" + (gz_1.equals("0") ? "" : "'1',") + (gz_2.equals("0") ? "" : "'2',") + (gz_3.equals("0") ? "" : "'3',") + (gz_4.equals("0") ? "" : "'5',") + (gz_5.equals("0") ? "" : "'6',") + (gz_6.equals("0") ? "" : "'7',") + (gz_7.equals("0") ? "" : "'8',") + (gz_8.equals("0") ? "" : "'A4',") + (gz_9.equals("0") ? "" : "'A5',") + (gz_10.equals("0") ? "" : "'A6',") + (gz_11.equals("0") ? "" : "'A0',") + (gz_12.equals("0") ? "" : "'A1',") + (gz_13.equals("0") ? "" : "'A2',");
		String gl_lb = "" + (gl_1.equals("0") ? "" : "'01',") + (gl_2.equals("0") ? "" : "'02',") + (gl_3.equals("0") ? "" : "'03',") + (gl_4.equals("0") ? "" : "'04',") + (gl_5.equals("0") ? "" : "'09',");

		String ltry = this.getPageElement("ltry").getValue();
		String gjgs = this.getPageElement("gjgs").getValue();
		if (gjgs != null && gjgs.equals("1")) {

		} else {
		    this.setMainMessage("只有HZB格式支持分包导出。");
		    return EventRtnType.NORMAL_SUCCESS;
		}
		B01 b01 = (B01) HBUtil.getHBSession().get(B01.class, searchDeptid);
		// ------------ -------------//
		StringBuilder b = new StringBuilder();
		b.append("select distinct a1.a0000 a0000,rownum rn from a01 a1,a02 a2 where a1.a0000=a2.a0000(+)");
		if (ltry.equals("0")) {
		    b.append(" and a1.status<>'3'");
		}
		if (!gz_lb.equals("")) {
		    b.append(" and a1.a0160 in (" + gz_lb.substring(0, gz_lb.length() - 1) + ")");
		}
		if (!gl_lb.equals("")) {
		    b.append(" and a1.a0165 in (" + gl_lb.substring(0, gl_lb.length() - 1) + ")");
		}
		b.append(" and a2.a0201b in (select b.b0111 from b01 b start with b0111='" + searchDeptid + "' connect by prior b0111=b0121)");
		Object obj = HBUtil.getHBSession().createSQLQuery("select count(a0000) from (" + b.toString() + ")").uniqueResult();
		int count = 1;
		if (obj != null) {
		    count = ((BigDecimal) obj).intValue() / packcount + (((BigDecimal) obj).intValue() % packcount != 0 ? 1 : 0);
		}
		for (int i = 1; i <= count; i++) {
		    StringBuilder a01sql = new StringBuilder();
		    a01sql.append(" select a0000 from(");
		    a01sql.append(b);
		    a01sql.append(") where rn >=" + ((i - 1) * packcount + 1));
		    a01sql.append(" and rn <=" + (i * packcount));
		    List<B01> list16 = null;
		    if (i == 1) {
			list16 = HBUtil.getHBSession().createSQLQuery("select * from b01 start with b0111='" + searchDeptid + "' connect by prior b0111=b0121").addEntity(B01.class).list();
		    }

		    List<A01> list = HBUtil.getHBSession().createSQLQuery("select * from a01 where a0000 in(" + a01sql.toString() + ")").addEntity(A01.class).list();
		    List<A02> list2 = HBUtil.getHBSession().createSQLQuery("select * from A02 where a0000 in(" + a01sql.toString() + ")").addEntity(A02.class).list();
		    List<A06> list3 = HBUtil.getHBSession().createSQLQuery("select * from A06 where a0000 in(" + a01sql.toString() + ")").addEntity(A06.class).list();
		    List<A08> list4 = HBUtil.getHBSession().createSQLQuery("select * from A08 where a0000 in(" + a01sql.toString() + ")").addEntity(A08.class).list();
		    List<A11> list5 = HBUtil.getHBSession().createSQLQuery("select * from A11 where a0000 in(" + a01sql.toString() + ")").addEntity(A11.class).list();

		    List<A14> list6 = HBUtil.getHBSession().createSQLQuery("select * from A14 where a0000 in(" + a01sql.toString() + ")").addEntity(A14.class).list();
		    List<A15> list7 = HBUtil.getHBSession().createSQLQuery("select * from A15 where a0000 in(" + a01sql.toString() + ")").addEntity(A15.class).list();
		    List<A29> list8 = HBUtil.getHBSession().createSQLQuery("select * from A29 where a0000 in(" + a01sql.toString() + ")").addEntity(A29.class).list();
		    List<A30> list9 = HBUtil.getHBSession().createSQLQuery("select * from A30 where a0000 in(" + a01sql.toString() + ")").addEntity(A30.class).list();
		    List<A31> list10 = HBUtil.getHBSession().createSQLQuery("select * from A31 where a0000 in(" + a01sql.toString() + ")").addEntity(A31.class).list();

		    List<A36> list11 = HBUtil.getHBSession().createSQLQuery("select * from A36 where a0000 in(" + a01sql.toString() + ")").addEntity(A36.class).list();
		    List<A37> list12 = HBUtil.getHBSession().createSQLQuery("select * from A37 where a0000 in(" + a01sql.toString() + ")").addEntity(A37.class).list();
		    List<A41> list13 = HBUtil.getHBSession().createSQLQuery("select * from A41 where a0000 in(" + a01sql.toString() + ")").addEntity(A41.class).list();
		    List<A53> list14 = HBUtil.getHBSession().createSQLQuery("select * from A53 where a0000 in(" + a01sql.toString() + ")").addEntity(A53.class).list();
		    List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000 in(" + a01sql.toString() + ")").addEntity(A57.class).list();
		    List<Map> list17 = new ArrayList<Map>();
		    map.put("type", "2");
		    map.put("time", DateUtil.timeToString(DateUtil.getTimestamp()));
		    // ??
		    map.put("dataversion", "20121221");
		    map.put("psncount", (list != null && list.size() > 0) ? (list.size() + "") : "");
		    //
		    map.put("photodir", "Photos");
		    map.put("B0101", b01.getB0101());
		    map.put("B0111", b01.getB0111());
		    map.put("B0114", b01.getB0114());
		    map.put("B0194", b01.getB0194());
		    map.put("linkpsn", linkpsn);
		    map.put("linktel", linktel);
		    map.put("remark", remark);
		    list17.add(map);
		    String path = getPath();
		    String number = ("000" + i).substring(("000" + i).length() - 3);
		    String zippath = path + "按机构导出文件_" + b01.getB0111() + "_" + b01.getB0101() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") + "/" + number + "/";
		    File file = new File(zippath);
		    // 如果文件夹不存在则创建
		    if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		    }
		    String zippathtable = path + "按机构导出文件_" + b01.getB0111() + "_" + b01.getB0101() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") + "/" + number + "/Table/";
		    File file1 = new File(zippathtable);
		    // 如果文件夹不存在则创建
		    if (!file1.exists() && !file1.isDirectory()) {
			file1.mkdirs();
		    }
		    String zipfile = path + "按机构导出文件_" + b01.getB0111() + "_" + b01.getB0101() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") + "_" + number + ".7z";
		    if (gjgs != null && gjgs.equals("1")) {
			zipfile = path + "按机构导出文件_" + b01.getB0111() + "_" + b01.getB0101() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") + "_" + number + ".hzb";
			Xml4HZBUtil.List2Xml(list, "A01", zippath);
			Xml4HZBUtil.List2Xml(list2, "A02", zippath);
			Xml4HZBUtil.List2Xml(list3, "A06", zippath);
			Xml4HZBUtil.List2Xml(list4, "A08", zippath);
			Xml4HZBUtil.List2Xml(list5, "A11", zippath);

			Xml4HZBUtil.List2Xml(list6, "A14", zippath);
			Xml4HZBUtil.List2Xml(list7, "A15", zippath);
			Xml4HZBUtil.List2Xml(list8, "A29", zippath);
			Xml4HZBUtil.List2Xml(list9, "A30", zippath);
			Xml4HZBUtil.List2Xml(list10, "A31", zippath);

			Xml4HZBUtil.List2Xml(list11, "A36", zippath);
			Xml4HZBUtil.List2Xml(list12, "A37", zippath);
			Xml4HZBUtil.List2Xml(list13, "A41", zippath);
			Xml4HZBUtil.List2Xml(list14, "A53", zippath);
			Xml4HZBUtil.List2Xml(list15, "A57", zippath);

			Xml4HZBUtil.List2Xml(list16, "B01", zippath);
			Xml4HZBUtil.List2Xml(list17, "info", zippath);
		    }
		    if (list15 != null && list15.size() > 0) {
			String photopath = zippath + "Photos/";
			File file2 = new File(photopath);
			// 如果文件夹不存在则创建
			if (!file2.exists() && !file2.isDirectory()) {
			    file2.mkdirs();
			}
			for (int j = 0; j < list15.size(); j++) {
			    A57 a57 = list15.get(j);
			    if (a57.getPhotoname() != null && !a57.getPhotoname().equals("") && a57.getPhotodata() != null) {
				File f = new File(photopath + a57.getA0000() + ".jpg");
				FileOutputStream fos = new FileOutputStream(f);
				InputStream is = a57.getPhotodata().getBinaryStream();// 读出数据后转换为二进制流
				byte[] data = new byte[1024];
				while (is.read(data) != -1) {
				    fos.write(data);
				}
				fos.close();
				is.close();
			    }
			}
		    }
		    infile = zipfile;
		    SevenZipUtil.zip7z(zippath, zipfile, ((gjgs != null && gjgs.equals("2")) ? "1234" : "1234"));
		    // String cmd="cmd /c \"c:\\Program Files (x86)\\7-Zip\\7z.exe\" a "+zipfile+"
		    // -p" + ((gjgs!=null && gjgs.equals("2"))?"1234":"1234") + " -mhe
		    // "+zippath+"\\*";
		    // Process p = Runtime.getRuntime().exec(cmd);
		    this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
		    // this.getExecuteSG().addExecuteCode("window.reloadTree()");
		    this.getExecuteSG().addExecuteCode("var w=window.open('ProblemDownServlet?method=downFile&prid=" + URLEncoder.encode(URLEncoder.encode(infile.replace("\\", "/"), "UTF-8"), "UTF-8") + "');  setTimeout(cc,600); function cc(){w.close();}");
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return EventRtnType.NORMAL_SUCCESS;
    }

    @PageEvent("reset.onclick")
    @NoRequiredValidate
    public int resetbtn(String name) throws RadowException {
	this.getPageElement("searchDeptBtn").setValue("");
	this.getPageElement("searchDeptid").setValue("");
	this.getPageElement("linkpsn").setValue("");
	this.getPageElement("linktel").setValue("");
	this.getPageElement("remark").setValue("");

	this.getPageElement("gzlbry").setValue("0");
	this.getPageElement("gllbry").setValue("0");
	this.getPageElement("zdcjg").setValue("0");

	// this.getPageElement("ltry").setValue("0");
	// this.getPageElement("lsry").setValue("0");
	this.getPageElement("fxzry").setValue("0");
	this.getExecuteSG().addExecuteCode("onzdcjg();ongzlb();ongllb();");
	return EventRtnType.NORMAL_SUCCESS;
    }

    private String getPath() {
	String classPath = getClass().getClassLoader().getResource("/").getPath();
	try {
	    classPath = URLDecoder.decode(classPath, "GBK");
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}
	String uuid = UUID.randomUUID().toString().replace("-", "");
	String rootPath = "";

	// windows下
	if ("\\".equals(File.separator)) {
	    rootPath = classPath.substring(1, classPath.indexOf("WEB-INF/classes"));
	    rootPath = rootPath.replace("/", "\\");
	}
	// linux下
	if ("/".equals(File.separator)) {
	    rootPath = classPath.substring(0, classPath.indexOf("WEB-INF/classes"));
	    rootPath = rootPath.replace("\\", "/");
	}
	// 上传路径
	String upload_file = rootPath + "zipload/";
	try {
	    File file = new File(upload_file);
	    // 如果文件夹不存在则创建
	    if (!file.exists() && !file.isDirectory()) {
		file.mkdirs();
	    }
	} catch (Exception e1) {
	    e1.printStackTrace();
	}
	// 解压路径
	String zip = upload_file + uuid + "/";
	return zip;
    }

    private String getRootPath() {
	String classPath = getClass().getClassLoader().getResource("/").getPath();
	try {
	    classPath = URLDecoder.decode(classPath, "GBK");
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}
	String rootPath = "";

	// windows下
	if ("\\".equals(File.separator)) {
	    rootPath = classPath.substring(1, classPath.indexOf("WEB-INF/classes"));
	    rootPath = rootPath.replace("/", "\\");
	}
	// linux下
	if ("/".equals(File.separator)) {
	    rootPath = classPath.substring(0, classPath.indexOf("WEB-INF/classes"));
	    rootPath = rootPath.replace("\\", "/");
	}
	return rootPath;
    }

    @PageEvent("searchDeptBtn.ontriggerclick")
    @NoRequiredValidate
    public int searchDept(String name) throws RadowException {
	this.openWindow("deptWin", "pages.dataverify.DeptWindow");
	return EventRtnType.NORMAL_SUCCESS;
    }

    /**
     * 对选中机构进行数据校验
     * 
     * @return
     * @throws RadowException
     */
    @NoRequiredValidate
    @PageEvent("dataVerify.onclick")
    public int dataVerify() throws RadowException {
	String searchDeptid = this.getPageElement("searchDeptid").getValue();
	if (StringUtil.isEmpty(searchDeptid)) {
	    throw new RadowException("请选择要校验的机构!");
	}
	if (!SysRuleBS.havaRule(searchDeptid)) {
	    throw new RadowException("您无此权限!");
	}
	// this.setRadow_parent_data("1@"+searchDeptid);
	// this.openWindow("dataVerifyWin",
	// "pages.sysorg.org.orgdataverify.OrgDataVerify");
	this.getExecuteSG().addExecuteCode("$h.openWin('dataVerifyWin','pages.sysorg.org.orgdataverify.OrgDataVerify','校验窗口',1251,599, '1@" + searchDeptid + "','/hzb');");
	return EventRtnType.NORMAL_SUCCESS;
    }

    @Override
    public int doInit() throws RadowException {
	this.setNextEventName("initX");
	// HBSession sess = HBUtil.getHBSession();
	// List list = sess.createQuery(" from CodeValue t where " +
	// " codeType='ZB125'").list();
	// Grid grid = (Grid)this.createPageElement("GZgrid", ElementType.GRID, false);
	// grid.setValueList(list);
	// List list2 = sess.createQuery(" from CodeValue t where " +
	// " codeType='ZB130'").list();
	// Grid grid2 = (Grid)this.createPageElement("GLgrid", ElementType.GRID, false);
	// grid2.setValueList(list2);
	// String
	// reString="['1','综合管理类管理员'],['2','专业技术类管理员']['1','行政管理类管理员']['1','事业单位管理人员']";
	// request.setAttribute("a00", reString);
	return EventRtnType.NORMAL_SUCCESS;
    }

    @PageEvent("initX")
    public int initX() {
	return EventRtnType.NORMAL_SUCCESS;
    }

    /*
     * public String expByPsnbtn(String ids) throws RadowException{ String infile =
     * ""; try { CommonQueryBS.systemOut(DateUtil.getTime()); Map<String, String>
     * map = new HashMap<String, String>(); if(ids != null || !ids.equals("")){
     * HBSession sess = HBUtil.getHBSession();
     * 
     * List<A01> list = sess.createSQLQuery("select * from a01 where a0000 in(" +
     * ids + ")").addEntity(A01.class).list(); List<A57> list15 =
     * sess.createSQLQuery("select * from A57 where a0000 in(" + ids +
     * ")").addEntity(A57.class).list(); List<Map> list17 = new ArrayList<Map>();
     * map.put("type", "31"); //按人员导入数据 map.put("time",
     * DateUtil.timeToString(DateUtil.getTimestamp())); // ?? map.put("dataversion",
     * "20121221"); map.put("psncount", (list!=null&&list.size()>0)?
     * (list.size()+"") :"" ); map.put("photodir", "Photos"); map.put("B0101", "");
     * map.put("B0111", ""); map.put("B0114", ""); map.put("B0194", "");
     * map.put("linkpsn", ""); map.put("linktel", ""); map.put("remark", "包含人员为"
     * +list.size() + "。"); list17.add(map); String path = getPath(); String zippath
     * = path + "按人员导出文件_" + DateUtil.timeToString(DateUtil.getTimestamp(),
     * "yyyyMMddHHmmss") +"/"; File file =new File(zippath); //如果文件夹不存在则创建 if
     * (!file.exists()){ file.mkdirs(); } String zippathtable = zippath +"Table/";
     * File file1 =new File(zippathtable); //如果文件夹不存在则创建 if(!file1.exists()){ file1
     * .mkdirs(); } String zipfile = path + "按人员导出文件_" +
     * DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".7z";
     * zipfile = path + "按人员导出文件_" + DateUtil.timeToString(DateUtil.getTimestamp(),
     * "yyyyMMddHHmmss") +".hzb";
     * 
     * Connection conn = sess.connection(); Statement stmt = null; stmt =
     * conn.createStatement(); ResultSet rs_a01 =
     * stmt.executeQuery("select * from a01 where a0000 in(" + ids + ")");
     * Xml4HZBUtil2.List2Xml(rs_a01, "A01", zippath); rs_a01.close(); stmt.close();
     * 
     * stmt = conn.createStatement(); ResultSet rs_a02 =
     * stmt.executeQuery("select * from A02 where a0000 in(" + ids + ")");
     * Xml4HZBUtil2.List2Xml(rs_a02, "A02", zippath); rs_a02.close(); stmt.close();
     * 
     * stmt = conn.createStatement(); ResultSet rs_a06 =
     * stmt.executeQuery("select * from A06 where a0000 in(" + ids + ")");
     * Xml4HZBUtil2.List2Xml(rs_a06, "A06", zippath); rs_a06.close(); stmt.close();
     * 
     * stmt = conn.createStatement(); ResultSet rs_a08 =
     * stmt.executeQuery("select * from A08 where a0000 in(" + ids + ")");
     * Xml4HZBUtil2.List2Xml(rs_a08, "A08", zippath); rs_a08.close(); stmt.close();
     * 
     * stmt = conn.createStatement(); ResultSet rs_a11 =
     * stmt.executeQuery("select * from A11 where a0000 in(" + ids + ")");
     * Xml4HZBUtil2.List2Xml(rs_a11, "A11", zippath); rs_a11.close(); stmt.close();
     * 
     * stmt = conn.createStatement(); ResultSet rs_a14 =
     * stmt.executeQuery("select * from A14 where a0000 in(" + ids + ")");
     * Xml4HZBUtil2.List2Xml(rs_a14, "A14", zippath); rs_a14.close(); stmt.close();
     * 
     * stmt = conn.createStatement(); ResultSet rs_a15 =
     * stmt.executeQuery("select * from A15 where a0000 in(" + ids + ")");
     * Xml4HZBUtil2.List2Xml(rs_a15, "A15", zippath); rs_a15.close(); stmt.close();
     * 
     * stmt = conn.createStatement(); ResultSet rs_A29 =
     * stmt.executeQuery("select * from A29 where a0000 in(" + ids + ")");
     * Xml4HZBUtil2.List2Xml(rs_A29, "A29", zippath); rs_A29.close(); stmt.close();
     * 
     * stmt = conn.createStatement(); ResultSet rs_A30 =
     * stmt.executeQuery("select * from A30 where a0000 in(" + ids + ")");
     * Xml4HZBUtil2.List2Xml(rs_A30, "A30", zippath); rs_A30.close(); stmt.close();
     * 
     * stmt = conn.createStatement(); ResultSet rs_A31 =
     * stmt.executeQuery("select * from A31 where a0000 in(" + ids + ")");
     * Xml4HZBUtil2.List2Xml(rs_A31, "A31", zippath); rs_A31.close(); stmt.close();
     * 
     * stmt = conn.createStatement(); ResultSet rs_A36 =
     * stmt.executeQuery("select * from A36 where a0000 in(" + ids + ")");
     * Xml4HZBUtil2.List2Xml(rs_A36, "A36", zippath); rs_A36.close(); stmt.close();
     * 
     * stmt = conn.createStatement(); ResultSet rs_A37 =
     * stmt.executeQuery("select * from A37 where a0000 in(" + ids + ")");
     * Xml4HZBUtil2.List2Xml(rs_A37, "A37", zippath); rs_A37.close(); stmt.close();
     * 
     * stmt = conn.createStatement(); ResultSet rs_A41 =
     * stmt.executeQuery("select * from A41 where a0000 in(" + ids + ")");
     * Xml4HZBUtil2.List2Xml(rs_A41, "A41", zippath); rs_A41.close(); stmt.close();
     * 
     * stmt = conn.createStatement(); ResultSet rs_A53 =
     * stmt.executeQuery("select * from A53 where a0000 in(" + ids + ")");
     * Xml4HZBUtil2.List2Xml(rs_A53, "A53", zippath); rs_A53.close(); stmt.close();
     * 
     * stmt = conn.createStatement(); ResultSet rs_A57 =
     * stmt.executeQuery("select * from A57 where a0000 in(" + ids + ")");
     * Xml4HZBUtil2.List2Xml(rs_A57, "A57", zippath); rs_A57.close(); stmt.close();
     * 
     * Xml4HZBUtil.List2Xml(new ArrayList(), "B01", zippath);
     * Xml4HZBUtil.List2Xml(list17, "info", zippath); if(list15 != null &&
     * list15.size()>0){ String photopath = zippath + "Photos/"; File file2 =new
     * File(photopath); //如果文件夹不存在则创建 if (!file2 .exists() && !file2 .isDirectory())
     * { file2 .mkdirs(); } for (int i = 0; i < list15.size(); i++) { A57 a57 =
     * list15.get(i); if(a57.getA0000()!=null && !a57.getA0000().equals("") &&
     * a57.getPhotodata()!=null){ File f = new File(photopath + a57.getA0000() +"."
     * + "jpg"); FileOutputStream fos = new FileOutputStream(f); InputStream is =
     * a57.getPhotodata().getBinaryStream();// 读出数据后转换为二进制流 byte[] data = new
     * byte[1024]; while (is.read(data) != -1) { fos.write(data); } fos.close();
     * is.close(); } } } infile = zipfile; NewSevenZipUtil.zip7zNew(zippath,
     * zipfile, "1"); new LogUtil().createLog("421", "IMP_RECORD", "", "", "数据导出",
     * new ArrayList()); } CommonQueryBS.systemOut(DateUtil.getTime()); //
     * this.getPageElement("downfile").setValue(infile.replace("\\", "/")); //
     * this.getExecuteSG().
     * addExecuteCode("var w=window.open('ProblemDownServlet?method=downFile&prid="+
     * URLEncoder.encode(URLEncoder.encode(infile.replace("\\", "/"),"UTF-8"),"UTF-
     * 8")+"'); setTimeout(cc,600); function cc(){w.close();}"); //
     * this.getExecuteSG().addExecuteCode("window.reloadTree()"); } catch (Exception
     * e) { this.setMainMessage("导出失败，请重试！"); e.printStackTrace(); } return infile;
     * }
     */

    /**
     * 照片检测
     * 
     * @return
     * @throws RadowException
     */
    @NoRequiredValidate
    @PageEvent("imgVerify.onclick")
    public int imgVerify() throws RadowException {
	this.getExecuteSG().addExecuteCode("addTab('照片检测','','" + request.getContextPath() + "/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.orgdataverify.OrgPersonImgVerify',false,false)");
	return EventRtnType.NORMAL_SUCCESS;
    }

}
