package com.insigma.siis.local.pagemodel.exportexcel;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;

import sun.misc.BASE64Encoder;

import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Rydjb;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.search.PreSubmitPageModel;
import com.picCut.teetaa.util.ImageHepler;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * @author lixy 表册导出数据查询组合类
 */
public class ExportDataBuilder {

	private Configuration configuration = null;

	public ExportDataBuilder() {
		configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
	}

	/**
	 * @$comment 返回要导出模板的数据
	 * @param request
	 * @return List<Map<String, Object>>
	 * @throws RadowException
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */
	public Map<String, Object> getTempData(String tempType,
			HttpServletRequest request) {
		Map<String, Object> tmpData = null;

		if ("1".equals(tempType) || "2".equals(tempType))
			tmpData = getDjbCollectionList(request);
		if ("3".equals(tempType) || "4".equals(tempType) || "13".equals(tempType) || "14".equals(tempType))
			tmpData = getBabCollectionList(request);
		if ("5".equals(tempType) || "6".equals(tempType) || "8".equals(tempType))
			tmpData = getMcList(request, tempType);
		if ("100".equals(tempType) || "101".equals(tempType))
			tmpData = getCBDexcel(request);
		if ("11".equals(tempType) || "12".equals(tempType)) {// 呈报单数据查询
			tmpData = getCBDList(request);
		}

		return tmpData;
	}

	private Map<String, Object> getCBDexcel(HttpServletRequest request) {
		String sql = null;
		String a0000 = (String) request.getAttribute("a00");
		a0000 = "'" + a0000.replaceAll(",", "','") + "'";
		Map<String, Object> tmpData = new HashMap<String, Object>();
		if (DBUtil.getDBType() == DBType.ORACLE)
			sql = "select rownum,a0101,(select code_name from code_value where code_type='GB2261' and code_value.code_value=a01.a0104) a0104,a0107,a0192,(select code_name from code_value where code_type='ZB134' and code_value.code_value=a01.a0120) a0120,a01.a0180,a01.a0111a,(select code_name from code_value where code_type='GB3304' and code_value.code_value=a01.a0117) a0117 ,(select code_name from code_value where code_type='GB4762' and code_value.code_value=a01.a0141) a0141,(select code_name from code_value where code_type='ZB125' and code_value.code_value = a01.a0160) a0160,a01.a0192a from A01 a01 where a0000 in ("
					+ a0000 + ")";
		else if (DBUtil.getDBType() == DBType.MYSQL)
			sql = "select (@rowNum:=@rowNum+1) as rownum,a0101,(select code_name from code_value where code_type='GB2261' and code_value.code_value=a01.a0104) a0104,a0107,a0192,(select code_name from code_value where code_type='ZB134' and code_value.code_value=a01.a0120) a0120,a01.a0180,a01.a0111a,(select code_name from code_value where code_type='GB3304' and code_value.code_value=a01.a0117) a0117,(select code_name from code_value where code_type='GB4762' and code_value.code_value=a01.a0141) a0141,(select code_name from code_value where code_type='ZB125' and code_value.code_value = a01.a0160) a0160,a01.a0192a from A01 a01,(Select (@rowNum :=0) ) b where a0000 in ("
					+ a0000 + ")";
		List<Map<String, String>> list = getListBySql(sql, false);
		tmpData.put("sbList", list);
		// TODO先不记录日志
		return tmpData;
	}

	/**
	 * @$comment 获取公务员备案表信息
	 * @param request
	 * @return List<Map<String, Object>>
	 * @throws RadowException
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */
	public Map<String, Object> getMcList(HttpServletRequest request,
			String tempType) {
		Map<String, Object> tmpData = null;
		String dw = request.getParameter("dw");
		String dwbm = request.getParameter("dwbm");
		String checkList = request.getParameter("List");
		String viewType=request.getParameter("viewType")==null?request.getParameter("viewType2"):request.getParameter("viewType");//名册展现方式
		List<Map<String, String>> list = null;
		String sql ="select a01.a0000,                                               "  
			       +"a01.a0101,                                                      "
			       +"c1.code_name a0117,                                             "
			       +"a01.a0111a,                                                     "
			       +"c2.code_name a0104,                                             "
			       +"a01.a0107,                                                      "
			       +"a02.a0216a,                                                     "
			       +"a01.QRZXL,                                                      "
			       +"a01.ZZXL,                                                       "
			       +"a01.A0144,                                                      "
			       +"a02.a0221 a0148,                                                      "
			       +"a01.a0192,                                                      "
			       +"(select code_name "
                   +"from code_value "
                   +"where code_type = 'ZB09' "
                   +"and code_value = a02.a0221) a0149,                              "
			       +"a01.A0134,                                                      "
			       +"a02.A0243,                                                      "
			       +"a02.A0288,                                                      "
			       +"a02.A0201A,                                                     "
			       +"a02.A0201b,                                                     "
			       +"a01.a0180,                                                      "
			       +"a02.a0223                                                       "
			       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2 "
			       +"where a02.a0000 = a01.a0000                                     "
			       +"and a02.A0255 = '1'                                             "
			       +"and a01.status = '1'                                            "
			       +"AND c1.code_type = 'GB3304'                                     "
			       +"and c1.code_value = a01.a0117                                   "
			       +"AND c2.code_type = 'GB2261'                                     "
			       +"and c2.code_value = a01.a0104                                   "
			       +"and a01.a0000 in ("+ checkList.replace("|", "'").replace("@", ",") + ") ";

		String sql2 = "select a.a0201a from (" 
		        + "select A0201A,A0201B "
				+ "from a02 " 
		        + "where a0000 in ("+ checkList.replace("|", "'").replace("@", ",") + ") "
				+ "GROUP BY A0201A,A0201B) a " 
				+ "order by a.a0201b";

		// 记录日志
		String[] checkLists = checkList.replace("|", "").replace("@", ",")
				.split(",");
		HBSession sess = HBUtil.getHBSession();
		for (int i = 0; i < checkLists.length; i++) {
			A01 a01 = (A01) sess.get(A01.class, checkLists[i]);
			A01 a01log = new A01();
			try {
				new LogUtil().createLog("53", "A01", a01.getA0000(),
						a01.getA0101(), "导出表册",
						new Map2Temp().getLogInfo(new A01(), a01log));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// if(dwbm!=null&&!dwbm.equals(""))
		// sql=sql+ " and a02.a0201b in ("+dwbm.replace("|", "'").replace("@",
		// ",")+")";
		// 不分组无机构名称列
		if ("1".equals(viewType)) {
			sql = sql + " order by a0223 desc";
			list = getListBySqlforMc(sql, true, tempType);
			Map<String, Object> um = new HashMap<String, Object>();
			um.put("tag", "");
			um.put("datas", list);
			List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
			l.add(um);
			tmpData = new HashMap<String, Object>();
			tmpData.put("mc", l);
		}// 不分组有机构名称列
		else if ("2".equals(viewType)) {
			sql = sql + " order by a0223 desc";
			list = getListBySqlforMc(sql, true, tempType);
			Map<String, Object> um = new HashMap<String, Object>();
			um.put("tag", dw);
			um.put("datas", list);
			List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
			l.add(um);
			tmpData = new HashMap<String, Object>();
			tmpData.put("mc", l);
			// 按机构分组
		} else if ("3".equals(viewType)) {
			sql = sql + "  order by length(a0201b),a0201b ,a0223 desc";
			list = getListBySqlforMc(sql, true, tempType);
			tmpData = new HashMap<String, Object>();
			tmpData.put("mc", groupListByCol(list, "a0201a"));
			
			// 按职务层次分组
		} else if ("4".equals(viewType)) {
			sql = sql + " order by a0148,a0223 desc";
			list = getListBySqlforMc(sql, true, tempType);
			tmpData = new HashMap<String, Object>();
			tmpData.put("mc", groupListByCol(list, "a0149"));
			// 按职务层次分组，按机构排序
		} else if ("5".equals(viewType)) {
			sql = sql + " order by a0148,length(a0201b),a0201b";
			list = getListBySqlforMc(sql, true, tempType);
			tmpData = new HashMap<String, Object>();
			tmpData.put("mc", groupListByCol(list, "a0149"));
		}

		return tmpData;
	}

	/**
	 * @$comment 对集合中数据进行分组
	 * @param list
	 *            需要分组的列表,groupCol 根据分组的列
	 * @return List<Map<String, Object>> Map中的tag 为分组名称，datas组内数据
	 * @throws RadowException
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */
	public List<Map<String, Object>> groupListByCol(List<Map<String, String>> list, String groupCol) {
		List<Map<String, Object>> groupList = new ArrayList<Map<String, Object>>();
		String tag = null;// 分组字段值(同组标志字段值)

		List<Map<String, String>> groupsList = null;// 分组list
		List<Map<String, String>> nullList = new ArrayList<Map<String, String>>();// 排序字段为空的数据分组
		for (Map<String, String> gm : list) {
			if (gm.get(groupCol) != null) {
				if (!gm.get(groupCol).toString().equals(tag)) {
					// 如果同组标志字段值不为空，则加入最终分组列表
					if (tag != null) {
						Map<String, Object> fm = new HashMap<String, Object>();
						fm.put("tag", tag);
						fm.put("datas", groupsList);
						groupList.add(fm);
					}
					groupsList = new ArrayList<Map<String, String>>();
				}

				groupsList.add(gm);
				// 设置分组标志值
				tag = gm.get(groupCol).toString();

			} else {
				// 添加分组字段为空的列表
				nullList.add(gm);
			}

		}

		// 将最后一个分组加入列表
		Map<String, Object> fm = new HashMap<String, Object>();
		fm.put("tag", tag);
		fm.put("datas", groupsList);
		groupList.add(fm);

		if (nullList.size() > 0) {
			// 添加分组字段为空的列表到分组后列表
			Map<String, Object> um = new HashMap<String, Object>();
			um.put("tag", "无法分组");
			um.put("datas", nullList);
			groupList.add(um);
		}

		return groupList;
	}

    public List<Map<String, Object>> groupListByCol2(List<Map<String, String>> list,String groupCol,List list2){
    	String tag=null;//分组字段值(同组标志字段值)
    	List<Map<String, Object>> groupsList=new ArrayList<Map<String,Object>>();//分组list
    	if(list2.size()>0){
    		for(int i = 0;i<list2.size();i++){//循环名称
    			List<Map<String, String>> groupList=new ArrayList<Map<String,String>>();
    			tag = list2.get(i).toString();
    			for(int j=list.size()-1;j>=0;j--){//循环人员信息
    				Map<String, String> gm = list.get(j);
    				if(gm.get(groupCol).toString().equals(tag)){
    					groupList.add(gm);
    					list.remove(j);//统计过的人员移除从list中移除
    				}
    			}
    			Map<String, Object> fm=new HashMap<String, Object>();
    			fm.put("tag", tag);
    			fm.put("datas",groupList);
    			groupsList.add(fm);
    		}
    		if(list.size()>0){
    			Map<String, Object> fm1=new HashMap<String, Object>();
    			fm1.put("tag", "无职务层次");
    			fm1.put("datas", list);
    			groupsList.add(fm1);
    		}
    	}else{
    		Map<String, Object> fm2=new HashMap<String, Object>();
    		fm2.put("tag", "无职务层次");
    		fm2.put("datas", list);
    		groupsList.add(fm2);
    	}
    	
    	return groupsList;
    }

	/**
	 * @$comment 获取公务员备案表信息
	 * @param request
	 * @return List<Map<String, Object>>
	 * @throws RadowException
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */
	public Map<String, Object> getBabCollectionList(HttpServletRequest request) {
		Map<String, Object> tmpData = new HashMap<String, Object>();
		String dw = (String) request.getParameter("dw");
		String gbs = (String) request.getParameter("gbs");
		String sys = (String) request.getParameter("sys");
		String djs = (String) request.getParameter("djs");
		String zhs = (String) request.getParameter("zhs");
		String djgridString = (String) request.getParameter("djgridString");
		String zhgridString = (String) request.getParameter("zhgridString");

		tmpData.put("dw", dw);
		tmpData.put("gbs", gbs);
		tmpData.put("sys", sys);
		tmpData.put("djs", djs);
		tmpData.put("zhs", zhs);
		String sql = null;
		if (DBUtil.getDBType() == DBType.ORACLE)
			sql = "select rownum,a0101,(select code_name from code_value where code_type='GB2261' and code_value.code_value=a01.a0104) a0104,a0107,a0192,(select code_name from code_value where code_type='ZB134' and code_value.code_value=a01.a0120) a0120,a01.a0180 from A01 a01";
		else if (DBUtil.getDBType() == DBType.MYSQL)
			sql = "select (@rowNum:=@rowNum+1) as rownum,a0101,(select code_name from code_value where code_type='GB2261' and code_value.code_value=a01.a0104) a0104,a0107,a0192,(select code_name from code_value where code_type='ZB134' and code_value.code_value=a01.a0120) a0120,a01.a0180 from A01 a01,(Select (@rowNum :=0) ) b ";
		if (djgridString != null && !"".equals(djgridString)) {
			djgridString = djgridString.replace("|", "'").replace("@", ",");
			String dsql = sql + " where a0000 in (" + djgridString + ")";
			List<Map<String, String>> list = getListBySql(dsql, false);
			tmpData.put("djgrid", list);
		} else {
			tmpData.put("zhgrid", new ArrayList<Map<String, String>>());
		}

		if (zhgridString != null && !"".equals(zhgridString)) {
			zhgridString = zhgridString.replace("|", "'").replace("@", ",");
			String zsql = sql + " where a0000 in (" + zhgridString + ")";
			List<Map<String, String>> list = getListBySql(zsql, false);
			tmpData.put("zhgrid", list);
		} else {
			tmpData.put("zhgrid", new ArrayList<Map<String, String>>());
		}
		if (djgridString != null && !"".equals(djgridString)) {
			// 记录日志
			String[] checkLists = djgridString.replace("'", "").split(",");
			HBSession sess = HBUtil.getHBSession();
			for (int i = 0; i < checkLists.length; i++) {
				A01 a01 = (A01) sess.get(A01.class, checkLists[i]);
				A01 a01log = new A01();
				try {
					new LogUtil().createLog("54", "A01", a01.getA0000(),
							a01.getA0101(), "导出备案表",
							new Map2Temp().getLogInfo(new A01(), a01log));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (zhgridString != null && !"".equals(zhgridString)) {
			// 记录日志
			String[] checkLists = zhgridString.replace("'", "").split(",");
			HBSession sess = HBUtil.getHBSession();
			for (int i = 0; i < checkLists.length; i++) {
				A01 a01 = (A01) sess.get(A01.class, checkLists[i]);
				A01 a01log = new A01();
				try {
					new LogUtil().createLog("54", "A01", a01.getA0000(),
							a01.getA0101(), "导出备案表",
							new Map2Temp().getLogInfo(new A01(), a01log));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return tmpData;
	}

	/**
	 * @$comment 获取表册信息
	 * @param a0000
	 *            人员iD,
	 * @return Map<String, Object>
	 * @throws RadowException
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */
	public Map<String, Object> getDjbMap(String a0000) {
		Map<String, Object> tmpData = null;
		List list = getListBySql("select * from Rydjb where a0000='" + a0000
				+ "'", true);
		if (list != null && list.size() > 0)
			tmpData = (Map<String, Object>) list.get(0);
		return tmpData;
	}

	/**
	 * @$comment 获取表册信息
	 * @param a0000
	 *            人员iD,tempType 模板类型
	 * @return List<Map<String, Object>>
	 * @throws RadowException
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */
	public Map<String, Object> getDjbCollectionList(HttpServletRequest request) {
		Map<String, Object> tmpData = null;

		// String a0000=(String)request.getParameter("a0000");
		String a0000 = (String) request.getAttribute("a0000");
		String djhsrzw = (String) request.getParameter("djhsrzw");
		String djhsdjb = (String) request.getParameter("djhsdjb");
		String szjgyj = (String) request.getParameter("szjgyj");
		String shjgyj = (String) request.getParameter("shjgyj");
		String spjgyj = (String) request.getParameter("spjgyj");
		String bz = (String) request.getParameter("bz");
		DBType cueDBType = DBUtil.getDBType();
		String sql = "";

		if (cueDBType == DBType.MYSQL) {
			sql = "select a01.A0101,a01.A0184,"
					+ // --姓名
					"  (select code_name from code_value where code_type='GB2261' and code_value.code_value=a01.a0104) A0104,"
					+ // --性别
					"  a01.A0107,"
					+ // --出生日期
					// "  a01.Age,"+//年龄
					"  (select code_name from code_value where code_type='GB3304' and code_value.code_value=a01.a0117) A0117,"
					+ // --民族
					// "  (select code_name3"+
					// "  from code_value"+
					// "  where code_type='ZB01'"+
					// "  and code_value = a01.a0111) A0111,"+
					"  a01.A0111a,"
					+ // --籍贯
					"  (select code_name from code_value where code_type = 'ZB01' and code_value.code_value = a01.A0114) A0114,"
					+ // --出生地
					"  (select code_name from code_value where code_type='ZB09' and code_value.code_value=a01.a0148) A0148,"
					+ // --现任职务层次
					"  (select code_name from code_value where code_type='GB4762' and code_value.code_value=a01.a0141) A0141," + 
					// --政治面貌
					"  a01.A0144," + // --入党时间
					"  a01.A0134," + // --参加工作时间
					" a01.A0128,  " + // --健康状况
					" a01.A0196,  " + // --专业技术职务
					" a01.QRZXL,  " + // --最高全日制学历
					" a01.QRZXW,  " + // --最高全日制学位
					" a01.QRZXLXX," + // --院校系专业（最高全日制学历）
					" a01.QRZXWXX," + // --院校系专业（最高全日制学位）
					" a01.ZZXL,  " + // --最高在职学历
					" a01.ZZXW,  " + // --最高在职学位
					" a01.ZZXLXX," + // --院校系专业（最高在职学历）
					" a01.ZZXWXX,"+  //--院校系专业（最高在职学位）
					" a01.A0192," + // --工作单位及职务
					" a01.NRZW," + // -- 拟任职务
					" a01.NMZW," + // -- 拟免职务
					" a01.A1701," + // --简历
					" a01.A14z101," + // --奖惩情况
					" a01.A15Z101," + // -- 对该人年度考核结果的汉字描述
					" a01.RMLY" + // --任免理由
					" from A01 a01 where a01.a0000='" + a0000 + "'";
		} else {
			sql = "select a01.A0101,a01.A0184,"
					+ // --姓名
					"  (select code_name from code_value where code_type='GB2261' and code_value.code_value=a01.a0104) A0104,"
					+ // --性别
					"  a01.A0107,"
					+ // --出生日期
					// "  a01.Age,"+//年龄
					"  (select code_name from code_value where code_type='GB3304' and code_value.code_value=a01.a0117) A0117,"
					+ // --民族
					// "  (select code_name3"+
					// "  from code_value"+
					// "  where code_type='ZB01'"+
					// "  and code_value = a01.a0111) A0111,"+
					"  a01.A0111a,"
					+ // --籍贯
					"  (select code_name from code_value where code_type = 'ZB01' and code_value.code_value = a01.A0114) A0114,"
					+ // --出生地
					"  (select code_name from code_value where code_type='ZB09' and code_value.code_value=a01.a0148) A0148,"
					+ // --现任职务层次
					"  (select code_name from code_value where code_type='GB4762' and code_value.code_value=a01.a0141) A0141," + // --政治面貌
					"  a01.A0144," + // --入党时间
					"  a01.A0134," + // --参加工作时间
					" a01.A0128,  " + // --健康状况
					" a01.A0196,  " + // --专业技术职务
					" a01.QRZXL,  " + // --最高全日制学历
					" a01.QRZXW,  " + // --最高全日制学位
					" a01.QRZXLXX," + // --院校系专业（最高全日制学历）
					" a01.QRZXWXX," + // --院校系专业（最高全日制学位）
					" a01.ZZXL,  " + // --最高在职学历
					" a01.ZZXW,  " + // --最高在职学位
					" a01.ZZXLXX," + // --院校系专业（最高在职学历）
					" a01.ZZXWXX,"+  //--院校系专业（最高在职学位）
					" a01.A0192," + // --工作单位及职务
					" a01.NRZW," + // -- 拟任职务
					" a01.NMZW," + // -- 拟免职务
					" to_char(a01.A1701) as a1701," + // --简历
					" a01.A14z101," + // --奖惩情况
					" a01.A15Z101," + // -- 对该人年度考核结果的汉字描述
					" a01.RMLY" + // --任免理由
					" from A01 a01 where a01.a0000='" + a0000 + "'";
		}

		Map<String, Object> tmp = null;
		try {
			HBSession session = HBUtil.getHBSession();
			CommonQueryBS query = new CommonQueryBS();

			query.setConnection(session.connection());

			query.setQuerySQL(sql);
			Vector<?> vector = query.query();
			Iterator<?> iterator = vector.iterator();
			if (iterator.hasNext()) {

				while (iterator.hasNext()) {

					tmp = (Map<String, Object>) iterator.next();

				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (tmp != null) {
			tmpData = tmp;

			// 添加手写信息
			tmpData.put("djhsrzw", djhsrzw);
			tmpData.put("djhsdjb", djhsdjb);
			tmpData.put("szjgyj", szjgyj);
			tmpData.put("shjgyj", shjgyj);
			tmpData.put("spjgyj", spjgyj);
			tmpData.put("bz", bz);

		}

		Rydjb rydjb = new Rydjb();
		try {
			com.insigma.odin.framework.util.BeanUtil.copyHashMap(
					(HashMap) tmpData, rydjb);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		rydjb.setA0000(a0000);
		HBUtil.getHBSession().saveOrUpdate(rydjb);
		HBUtil.getHBSession().flush();

		return tmpData;
	}

	/**
	 * @$comment 获取表册模板
	 * @param tempType
	 *            模板类型
	 * @return Template
	 * @throws IOException
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */
	public Template getTemplate(String tempType, ServletContext event,
			String viewType) throws IOException {
		Template template = null;
		String tempName = null;

		if ("1".equals(tempType))
			tempName = "gwydjb_2.xml";
		if ("2".equals(tempType))
			tempName = "czgwyfgljgzzrydjb_1.xml";
		if ("3".equals(tempType))
			tempName = "gwybadjb_1.xml";
		if ("4".equals(tempType))
			tempName = "gwybadjb2_1.xml";
		if ("5".equals(tempType)) {
			if ("1".equals(viewType) || "3".equals(viewType)
					|| "4".equals(viewType)) {
				tempName = "bzmc_3.xml";
			} else {
				tempName = "bzmc_2.xml";
			}
		}
		if ("6".equals(tempType))
			tempName = "zpmc.xml";
		if ("7".equals(tempType))
			tempName = "gwydjbahzb.xml";
		if ("8".equals(tempType))
			tempName = "zpmc_2.xml";
		if ("11".equals(tempType)) {
			tempName = "cbdmb_1.xml";
		}
		if ("12".equals(tempType)) {
			tempName = "cbdmb_up.xml";
		}
		if ("100".equals(tempType)) {
			tempName = "cbdexp.xml";
		}
		if ("13".equals(tempType)) {
			tempName = "gwybadjb_2.xml";
		}
		if ("14".equals(tempType)) {
			tempName = "gwybadjb2_2.xml";
		}
		if ("101".equals(tempType)) {
			tempName = "cmxx.xml";
		}
		if ("44".equals(tempType)){
			tempName = "gbrmspb_1.xml";
		}
		if ("46".equals(tempType)){
			tempName = "gbrmspb_2.xml";
		}
		if ("45".equals(tempType)){
			tempName = "gbrmspb_3.xml";
		}
		if ("47".equals(tempType)){
			tempName = "gbrmspb_4.xml";
		}
		// 从什么地方加载freemarker模板文件
		configuration.setDirectoryForTemplateLoading(new File(event
				.getRealPath("pages/exportexcel/")));

		// 设置对象包装器
		configuration.setObjectWrapper(new DefaultObjectWrapper());
		// 设置异常处理器
		configuration
				.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		// 定义Template对象
		template = configuration.getTemplate(tempName);

		return template;
	}

	/**
	 * @$comment 根据人员Id查询人员照片信息
	 * @param a0000
	 * @return String
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */

	public String getImageStr(String a0000) throws RadowException {
		// 从数据库获取文件输入流

		BASE64Encoder encoder = new BASE64Encoder();
		// 压缩照片
		try {

			A57 a57 = (A57) HBUtil.getHBSession().get(A57.class, a0000);
			if (a57 != null) {
				String imageurl = PhotosUtil.PHOTO_PATH + a57.getPhotopath()
						+ a57.getPhotoname();
				File pfile = new File(imageurl);
				byte[] data = null;
				if (pfile.isFile()) {
					if (pfile.length() > 80000) {
						Rectangle rec = new Rectangle(0, 0, 272, 340);
						data = ImageHepler.cut(imageurl, "", 0, 0, rec, a0000,
								null);
					} else {
						data = PhotosUtil.getPhotoData(a57);
					}

					if (data != null) {

						return encoder.encode(data);
					}
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encoder.encode(new byte[1]);
	}
	
	/**
	 * @$comment 返回查询信息集
	 * @param
	 * @return String
	 * @throws AppException
	 * @throws
	 * @author wuh
	 */
	public String getStringByB0111(String a0201b,
			String isContain) {
	 String sql = "";
			if ("1".equals(isContain)) {
				sql = "select a01.a0000,"+
			               "a0101,"+
			               "(select code_name "+
			               " from code_value "+
			               "where code_type = 'GB2261' "+
			               "and code_value.code_value = a01.a0104) a0104, "+
			               "(select code_name "+
			               "from code_value "+
			               "where code_type = 'ZB134' "+
			               "and code_value.code_value = a01.a0120) a0120,a01.a0180,a0192,a0107,a0148,status "+
						"  from (select a01.a0000,                                                            "+
						"               a0101,                                                                "+
						"a0104, "+
						"a0120, "+
						"               a01.a0180,                                                            "+
						"               a0192,                                                                "+
						"               a0107,                                                                "+
						"               a0148,                                                                "+
						"               status                                                                "+
						"          from A01 a01,a02 a02,COMPETENCE_USERDEPT cu  "+
						"         where 1 = 1                                                                 "+
						"           and a01.a0000 = a02.a0000                                                 "+
						"           and a02.a0201b like '"+a0201b+"%'                                            "+
						"           and a02.a0255 = '1'                                                       "+
						"           and a01.status = '1'                                                      "+
						"           and a02.A0201B=cu.b0111                                                   "+
						"           and cu.userid = '"+SysManagerUtils.getUserId()+"'                        "+
						"           group by a01.a0000,a0101,a0104,a0120,a01.a0180,a0192,a0107,a0148,status) a01 "+
						" where 1 = 1                                                                         "+
						"   and not exists                                                                    "+
						" (select 1                                                                           "+
						"          from COMPETENCE_USERPERSON cu                                              "+
						"         where cu.a0000 = a01.a0000                                                  "+
						"           and cu.userid = '"+SysManagerUtils.getUserId()+"')                       "+
						" order by a01.a0148,                                                                 "+
						"          (select max(a0225)                                                         "+
						"             from b01, a02                                                           "+
						"            where a01.a0000 = a02.a0000                                              "+
						"              and a02.a0201b = b01.b0111                                             "+
						"              and a02.a0255 = '1')                                                   ";
			} else {
				sql = "select a01.a0000,"+
               "a0101,"+
               "(select code_name "+
               " from code_value "+
               "where code_type = 'GB2261' "+
               "and code_value.code_value = a01.a0104) a0104, "+
               "(select code_name "+
               "from code_value "+
               "where code_type = 'ZB134' "+
               "and code_value.code_value = a01.a0120) a0120,a01.a0180,a0192,a0107,a0148,status "+
						"  from (select a01.a0000,                                                            "+
						"               a0101,                                                                "+
						" a0104, "+
						"a0120, "+
						"               a01.a0180,                                                            "+
						"               a0192,                                                                "+
						"               a0107,                                                                "+
						"               a0148,                                                                "+
						"               status                                                                "+
						"          from A01 a01,a02 a02,COMPETENCE_USERDEPT cu  "+
						"         where 1 = 1                                                                 "+
						"           and a01.a0000 = a02.a0000                                                 "+
						"           and a02.a0201b = '"+a0201b+"'                                            "+
						"           and a02.a0255 = '1'                                                       "+
						"           and a01.status = '1'                                                      "+
						"           and a02.A0201B=cu.b0111                                                   "+
						"           and cu.userid = '"+SysManagerUtils.getUserId()+"'                        "+
						"           group by a01.a0000,a0101,a0104,a0120,a01.a0180,a0192,a0107,a0148,status) a01 "+
						" where 1 = 1                                                                         "+
						"   and not exists                                                                    "+
						" (select 1                                                                           "+
						"          from COMPETENCE_USERPERSON cu                                              "+
						"         where cu.a0000 = a01.a0000                                                  "+
						"           and cu.userid = '"+SysManagerUtils.getUserId()+"')                       "+
						" order by a01.a0148,                                                                 "+
						"          (select max(a0225)                                                         "+
						"             from b01, a02                                                           "+
						"            where a01.a0000 = a02.a0000                                              "+
						"              and a02.a0201b = b01.b0111                                             "+
						"              and a02.a0255 = '1')                                                   ";
			}

		return sql;
	}

	/**
	 * @$comment 返回查询信息集
	 * @param
	 * @return List<Map<String, String>>
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */
	public List<Map<String, String>> getListByB0111(String a0201b,
			String isContain) {
		List<Map<String, String>> list = null;
		try {
			HBSession session = HBUtil.getHBSession();
			CommonQueryBS query = new CommonQueryBS();
			query.setConnection(session.connection());
			String sql = "";
			if ("1".equals(isContain)) {
				sql = "select a01.a0000,"+
			               "a0101,"+
			               "(select code_name "+
			               " from code_value "+
			               "where code_type = 'GB2261' "+
			               "and code_value.code_value = a01.a0104) a0104, "+
			               "(select code_name "+
			               "from code_value "+
			               "where code_type = 'ZB134' "+
			               "and code_value.code_value = a01.a0120) a0120,a01.a0180,a0192,a0107,a0148,status "+
						"  from (select a01.a0000,                                                            "+
						"               a0101,                                                                "+
						"a0104, "+
						"a0120, "+
						"               a01.a0180,                                                            "+
						"               a0192,                                                                "+
						"               a0107,                                                                "+
						"               a0148,                                                                "+
						"               status                                                                "+
						"          from A01 a01,a02 a02,COMPETENCE_USERDEPT cu  "+
						"         where 1 = 1                                                                 "+
						"           and a01.a0000 = a02.a0000                                                 "+
						"           and a02.a0201b like '"+a0201b+"%'                                            "+
						"           and a02.a0255 = '1'                                                       "+
						"           and a01.status = '1'                                                      "+
						"           and a02.A0201B=cu.b0111                                                   "+
						"           and cu.userid = '"+SysManagerUtils.getUserId()+"'                        "+
						"           group by a01.a0000,a0101,a0104,a0120,a01.a0180,a0192,a0107,a0148,status) a01 "+
						" where 1 = 1                                                                         "+
						"   and not exists                                                                    "+
						" (select 1                                                                           "+
						"          from COMPETENCE_USERPERSON cu                                              "+
						"         where cu.a0000 = a01.a0000                                                  "+
						"           and cu.userid = '"+SysManagerUtils.getUserId()+"')                       "+
						" order by a01.a0148,                                                                 "+
						"          (select max(a0225)                                                         "+
						"             from b01, a02                                                           "+
						"            where a01.a0000 = a02.a0000                                              "+
						"              and a02.a0201b = b01.b0111                                             "+
						"              and a02.a0255 = '1')                                                   ";
			} else {
				sql = "select a01.a0000,"+
               "a0101,"+
               "(select code_name "+
               " from code_value "+
               "where code_type = 'GB2261' "+
               "and code_value.code_value = a01.a0104) a0104, "+
               "(select code_name "+
               "from code_value "+
               "where code_type = 'ZB134' "+
               "and code_value.code_value = a01.a0120) a0120,a01.a0180,a0192,a0107,a0148,status "+
						"  from (select a01.a0000,                                                            "+
						"               a0101,                                                                "+
						" a0104, "+
						"a0120, "+
						"               a01.a0180,                                                            "+
						"               a0192,                                                                "+
						"               a0107,                                                                "+
						"               a0148,                                                                "+
						"               status                                                                "+
						"          from A01 a01,a02 a02,COMPETENCE_USERDEPT cu  "+
						"         where 1 = 1                                                                 "+
						"           and a01.a0000 = a02.a0000                                                 "+
						"           and a02.a0201b = '"+a0201b+"'                                            "+
						"           and a02.a0255 = '1'                                                       "+
						"           and a01.status = '1'                                                      "+
						"           and a02.A0201B=cu.b0111                                                   "+
						"           and cu.userid = '"+SysManagerUtils.getUserId()+"'                        "+
						"           group by a01.a0000,a0101,a0104,a0120,a01.a0180,a0192,a0107,a0148,status) a01 "+
						" where 1 = 1                                                                         "+
						"   and not exists                                                                    "+
						" (select 1                                                                           "+
						"          from COMPETENCE_USERPERSON cu                                              "+
						"         where cu.a0000 = a01.a0000                                                  "+
						"           and cu.userid = '"+SysManagerUtils.getUserId()+"')                       "+
						" order by a01.a0148,                                                                 "+
						"          (select max(a0225)                                                         "+
						"             from b01, a02                                                           "+
						"            where a01.a0000 = a02.a0000                                              "+
						"              and a02.a0201b = b01.b0111                                             "+
						"              and a02.a0255 = '1')                                                   ";
			}

			query.setQuerySQL(sql);
			Vector<?> vector = query.query();
			Iterator<?> iterator = vector.iterator();
			if (iterator.hasNext()) {
				list = new ArrayList<Map<String, String>>();
				while (iterator.hasNext()) {

					Map<String, String> tmp = (Map<String, String>) iterator
							.next();
					list.add(tmp);

				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	/**
	 * @$comment 根据传入SQL返回查询信息集
	 * @param SQL
	 *            ,isHasPic是否获取照片
	 * @return List<Map<String, String>>
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */
	public List<Map<String, String>> getListBySql(String sql, Boolean isHasPic) {
		List<Map<String, String>> list = null;
		try {
			HBSession session = HBUtil.getHBSession();
			CommonQueryBS query = new CommonQueryBS();
			query.setConnection(session.connection());
			query.setQuerySQL(sql);
			Vector<?> vector = query.query();
			Iterator<?> iterator = vector.iterator();
			if (iterator.hasNext()) {
				list = new ArrayList<Map<String, String>>();
				while (iterator.hasNext()) {

					Map<String, String> tmp = (Map<String, String>) iterator
							.next();
					// 如果需要照片数据且人员id不为空则获取照片数据
					if (isHasPic && !"".equals(tmp.get("a0000"))
							&& tmp.get("a0000") != null) {
						tmp.put("image", getImageStr(tmp.get("a0000")));
					}

					list.add(tmp);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return list;
	}

	/**
	 * @$comment 根据传入SQL返回查询信息集并拼接指定字段
	 * @param SQL
	 *            ,isHasPic是否获取照片
	 * @return List<Map<String, String>>
	 * @throws AppException
	 * @throws
	 * @author wuh
	 */
	public List<Map<String, String>> getListBySqlforMc(String sql,
			Boolean isHasPic, String tempType) {
		List<Map<String, String>> list = null;
		try {
			HBSession session = HBUtil.getHBSession();
			CommonQueryBS query = new CommonQueryBS();
			query.setConnection(session.connection());
			query.setQuerySQL(sql);
			Vector<?> vector = query.query();
			Iterator<?> iterator = vector.iterator();
			if ("8".equals(tempType)) {
				if (iterator.hasNext()) {
					list = new ArrayList<Map<String, String>>();
					while (iterator.hasNext()) {

						Map<String, String> tmp = (Map<String, String>) iterator
								.next();
						// 如果需要照片数据且人员id不为空则获取照片数据
						if (isHasPic && !"".equals(tmp.get("a0000"))
								&& tmp.get("a0000") != null) {
							tmp.put("image", getImageStr(tmp.get("a0000")));
						}
						tmp.put("a0111a",
								getA0111aStr(tmp.get("a0111a"),
										tmp.get("a0107"), tmp.get("qrzxl"),
										tmp.get("a0144"), tmp.get("a0134"),
										tmp.get("a0243")));
						tmp.put("a0107",
								getA0107aStr(tmp.get("a0107"),
										tmp.get("qrzxl"), tmp.get("a0144"),
										tmp.get("a0134"), tmp.get("a0243")));
						tmp.put("qrzxl",
								getQrzxlStr(tmp.get("qrzxl"), tmp.get("a0144"),
										tmp.get("a0134"), tmp.get("a0243")));
						tmp.put("a0144",
								getA0144Str(tmp.get("a0144"), tmp.get("a0134"),
										tmp.get("a0243")));
						tmp.put("a0134",
								getA0134Str(tmp.get("a0134"), tmp.get("a0243")));
						tmp.put("a0243", getA0243Str(tmp.get("a0243")));
						list.add(tmp);
					}
				}
			} else {
				if (iterator.hasNext()) {
					list = new ArrayList<Map<String, String>>();
					while (iterator.hasNext()) {

						Map<String, String> tmp = (Map<String, String>) iterator
								.next();
						// 如果需要照片数据且人员id不为空则获取照片数据
						if (isHasPic && !"".equals(tmp.get("a0000"))
								&& tmp.get("a0000") != null) {
							tmp.put("image", getImageStr(tmp.get("a0000")));
						}
						list.add(tmp);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return list;
	}

	/**
	 * 拼接籍贯信息
	 * 
	 */
	public String getA0111aStr(String a0111a, String a0107, String qrzxl,
			String a0144, String a0134, String a0243) {
		if (!StringUtil.isEmpty(a0111a)) {
			if (StringUtil.isEmpty(a0107) && StringUtil.isEmpty(qrzxl)
					&& StringUtil.isEmpty(a0144) && StringUtil.isEmpty(a0134)
					&& StringUtil.isEmpty(a0243)) {
				a0111a = a0111a + "人。";
			} else {
				a0111a = a0111a + "人,";
			}
		} else {
			a0111a = "";
		}
		return a0111a;
	}

	/**
	 * 拼接出生日期信息
	 * 
	 */
	public String getA0107aStr(String a0107, String qrzxl, String a0144,
			String a0134, String a0243) {
		if (!StringUtil.isEmpty(a0107)) {
			if (StringUtil.isEmpty(qrzxl) && StringUtil.isEmpty(a0144)
					&& StringUtil.isEmpty(a0134) && StringUtil.isEmpty(a0243)) {
				a0107 = a0107.substring(0, 4) + "." + a0107.substring(4, 6)
						+ "生。";
			} else {
				a0107 = a0107.substring(0, 4) + "." + a0107.substring(4, 6)
						+ "生，";
			}
		} else {
			a0107 = "";
		}
		return a0107;
	}

	/**
	 * 拼接学历信息
	 * 
	 */
	public String getQrzxlStr(String qrzxl, String a0144, String a0134,
			String a0243) {
		if (!StringUtil.isEmpty(qrzxl)) {
			if (StringUtil.isEmpty(a0144) && StringUtil.isEmpty(a0134)
					&& StringUtil.isEmpty(a0243)) {
				qrzxl = qrzxl + "。";
			} else {
				qrzxl = qrzxl + "，";
			}
		} else {
			qrzxl = "";
		}
		return qrzxl;
	}

	/**
	 * 拼接入党日期信息
	 * 
	 */
	public String getA0144Str(String a0144, String a0134, String a0243) {
		if (!StringUtil.isEmpty(a0144)) {
			if (StringUtil.isEmpty(a0134) && StringUtil.isEmpty(a0243)) {
				a0144 = a0144.substring(0, 4) + "." + a0144.substring(4, 6)
						+ "入党。";
			} else {
				a0144 = a0144.substring(0, 4) + "." + a0144.substring(4, 6)
						+ "入党，";
			}
		} else {
			a0144 = "";
		}
		return a0144;
	}

	/**
	 * 拼接参加工作日期信息
	 * 
	 */
	public String getA0134Str(String a0134, String a0243) {
		if (!StringUtil.isEmpty(a0134)) {
			if (StringUtil.isEmpty(a0243)) {
				a0134 = a0134.substring(0, 4) + "." + a0134.substring(4, 6)
						+ "参加工作。";
			} else {
				a0134 = a0134.substring(0, 4) + "." + a0134.substring(4, 6)
						+ "参加工作，";
			}
		} else {
			a0134 = "";
		}
		return a0134;
	}

	/**
	 * 拼接任现职日期信息
	 * 
	 */
	public String getA0243Str(String a0243) {
		if (!StringUtil.isEmpty(a0243)) {
			a0243 = a0243.substring(0, 4) + "." + a0243.substring(4, 6)
					+ "任现职。";

		} else {
			a0243 = "";
		}
		return a0243;
	}

	/**
	 * @$comment 获取呈报单信息
	 * @param request
	 * @return List<Map<String, Object>>
	 * @throws RadowException
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */
	public Map<String, Object> getCBDList(HttpServletRequest request) {

		String cbd_id = (String) request.getParameter("cbd_id");
		Map<String, Object> tmpData = null;
		String[] cbd_ids = cbd_id.split("@");

		StringBuffer sql = new StringBuffer();
		if (DBType.ORACLE == DBUtil.getDBType()) {

			sql.append("select ci.cbd_id,"
					+ "ci.cbd_name,"
					+ "ci.cbd_path,"
					+ "ci.cbd_date,"
					+ "ci.cdb_word_year_no,"
					+
					// "ci.cbd_word," +
					// "ci.cbd_year," +
					// "ci.cbd_no," +
					"ci.cbd_leader,"
					+ "substr(ci.cbd_date1,0,4) || '年' || substr(ci.cbd_date1,6,2) || '月' || substr(ci.cbd_date1,9,2) || '日' as cbd_date1,"
					+ "ci.cbd_organ," + "ci.cbd_text," + "ci.cbd_personid,"
					+ "ci.cbd_personname," + "ci.cbd_userid,"
					+ "ci.cbd_username," + "ci.objectno, " + "ci.cbd_cbr "
					+ "from cbd_info ci " + "where 1=1 ");
		} else if (DBType.MYSQL == DBUtil.getDBType()) {
			sql.append("select ci.cbd_id,"
					+ "ci.cbd_name,"
					+ "ci.cbd_path,"
					+ "ci.cbd_date,"
					+ "ci.cdb_word_year_no,"
					+
					// "ci.cbd_word," +
					// "ci.cbd_year," +
					// "ci.cbd_no," +
					"ci.cbd_leader,"
					+ "CONCAT (SUBSTR(ci.cbd_date1, 1, 4),'年',cast(substr(ci.CBD_DATE1,6,2) as unsigned int) ,'月' ,cast(substr(ci.CBD_DATE1,9,2) as unsigned int),'日' ) as cbd_date1 ,"
					+ "ci.cbd_organ," + "ci.cbd_text," + "ci.cbd_personid,"
					+ "ci.cbd_personname," + "ci.cbd_userid,"
					+ "ci.cbd_username," + "ci.objectno, " + "ci.cbd_cbr "
					+ "from cbd_info ci " + "where 1=1 ");
		}
		if ("u".equals(cbd_ids[1])) {
			sql.append(" and ci.cbd_id = '" + cbd_ids[0] + "'");
		} else if ("gu".equals(cbd_ids[1])) {
			sql.append(" and ci.objectno = '" + cbd_ids[0]
					+ "' and ci.cbd_path = '2'");
		} else if ("gg".equals(cbd_ids[1])) {
			sql.append(" and ci.objectno = '" + cbd_ids[0]
					+ "' and ci.cbd_path = '3'");
		}

		Map<String, Object> tmp = null;
		try {
			HBSession session = HBUtil.getHBSession();
			CommonQueryBS query = new CommonQueryBS();

			query.setConnection(session.connection());

			query.setQuerySQL(sql.toString());
			Vector<?> vector = query.query();
			Iterator<?> iterator = vector.iterator();
			if (iterator.hasNext()) {

				while (iterator.hasNext()) {

					tmp = (Map<String, Object>) iterator.next();

				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (tmp != null) {
			tmpData = tmp;
		}

		return tmpData;
	}

	/**
	 * @$comment 获取备案汇总表信息
	 * @param request
	 * @return
	 */
	Map<String, Object> getbaList(HttpServletRequest request, String a) {

		Map<String, Object> tmp = null;
		String a0000 = (String) request.getAttribute("a0000");

		String sql = "select a01.A0101, "
				+ // --姓名
				"  (select code_name from code_value where code_type='GB2261' and code_value.code_value=a01.a0104) A0104,"
				+ // --性别
				"  a01.A0107,"
				+ // --出生日期
				"  (select code_name from code_value where code_type='GB3304' and code_value.code_value=a01.a0117) A0117,"
				+ // --民族
				"  (select code_name from code_value where code_type='GB4762' and code_value.code_value=a01.a0141) A0141,"
				+ // --政治面貌
				" a01.QRZXL,  "
				+ // --最高全日制学历
				" a01.QRZXW,  "
				+ // --最高全日制学
				" a01.A0192,"
				+ // --工作单位及职务
				" (select code_name from code_value where code_type='ZB09' and code_value.code_value = a01.a0148) a0148,"
				+ // 职务层次
				" (select code_name from code_value where code_type='ZB134' and code_value.code_value = a01.a0120) a0120,"
				+ // 级别
				" a29.A2947,"
				+ // 进入公务员队伍时间
				" (select code_name from code_value where code_type='ZB77' and code_value.code_value = a29.a2911) a2911"
				+ // 进入公务员队伍方式
				" from A01 a01 left join A29 a29 on a01.a0000=a29.a0000 where a01.A0000='"
				+ a0000 + "'";

		try {
			HBSession session = HBUtil.getHBSession();
			CommonQueryBS query = new CommonQueryBS();

			query.setConnection(session.connection());

			query.setQuerySQL(sql);

			Vector<?> vector = query.query();
			Iterator<?> iterator = vector.iterator();
			if (iterator.hasNext()) {

				while (iterator.hasNext()) {

					tmp = (Map<String, Object>) iterator.next();

				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		tmp.put("jgmc", getBysql(a0000));

		return tmp;
	}

	/**
	 * 查询单位
	 * 
	 * @param a0000
	 * @return
	 */
	private String getBysql(String a0000) {
		// 开始获取最麻烦的工作单位
		HBSession sess = HBUtil.getHBSession();
		String hql = " from A02  where a0000='" + a0000 + "' and a0281='true' ";
		List<A02> list = sess.createQuery(hql).list();
		String jgmc = "";
		if (list != null && list.size() > 0) {
			Collections.sort(list, new Comparator<Object>() {

				@Override
				public int compare(Object o1, Object o2) {
					A02 a1 = (A02) o1;
					A02 a2 = (A02) o2;
					if (a1.getA0221() == null || "".equals(a1.getA0221())) {
						return 1;
					}
					if (a2.getA0221() == null || "".equals(a2.getA0221())) {
						return -1;
					}
					return a1.getA0221().compareTo(a2.getA0221());
				}

			});

			A02 a02 = list.get(0);
			String jgbm = a02.getA0201b();// 机构编码
			String zwmc = a02.getA0216a() == null ? "" : " " + a02.getA0216a();// 职务名称
			B01 b01 = null;
			if (jgbm != null && !"".equals(jgbm)) {
				b01 = (B01) sess.get(B01.class, jgbm);
			}
			if (b01 != null) {
				String b0194 = b01.getB0194();// 1—法人单位；2—内设机构；3—机构分组。
				if ("2".equals(b0194)) {
					jgmc = b01.getB0101() + jgmc;
					while (true) {
						b01 = (B01) sess.get(B01.class, b01.getB0121()); // 获取上级机构，进行各种各样的比较
						if (b01 == null) {
							break;
						} else {
							b0194 = b01.getB0194();
							if ("1".equals(b0194)) {// 法人单位
								jgmc = b01.getB0101() + jgmc;
								return jgmc + zwmc;

							} else if ("3".equals(b0194)) {// 3—机构分组
								continue;
							} else if ("2".equals(b0194)) { // 内设机构

								if (b01 != null) {

									jgmc = b01.getB0101() + jgmc;

								}
							}
						}
					}
				} else {
					jgmc = b01.getB0101() + jgmc;
					return jgmc + zwmc;
				}
				// tmp = new HashMap<String, Object>();
				// tmp.put("jgmc", jgmcList.get(0));
			}
		}
		return jgmc;

	}

	/**
	 * 获取填表时间的方法 2016/8/11
	 * 
	 * @return date
	 */
	public String getDate() {
		Calendar now = Calendar.getInstance();
		Date date = now.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

		return sdf.format(date);
	}
	//--------------------以上是吴皓写的，以下是改动的-------------------------------------------------------
	
	
	//--------------;lqh
	/**
	 * @$comment 获取干部任免审批表
	 * @param a0000
	 *            人员iD,
	 * @return Map<String, Object>
	 * @throws RadowException
	 * @throws AppException
	 * @throws
	 * @author lqh
	 */
	public Map<String, Object> getGbrmspbMap(String a0000) {
		Map<String, Object> tmpData = null;
		DBType cueDBType = DBUtil.getDBType();
		String sql = "";
		if (cueDBType == DBType.MYSQL) {
			sql = "select a01.a0000,a01.A0101,a01.A0184,"
					+ // --姓名
					"  (select code_name from code_value where code_type='GB2261' and code_value.code_value=a01.a0104) A0104,"
					+ // --性别
					"  substr(a01.a0107,0,6) as a0107,"
					+ // --出生日期
					"  GET_BIRTHDAY(a01.a0107,'"+DateUtil.getcurdate()+"') age,"
					+ // --年龄
					"  (select code_name from code_value where code_type='GB3304' and code_value.code_value=a01.a0117) A0117,"
					+ // --民族
					"  a01.A0111a,"
					+ // --籍贯
					"  a01.A0114a,"
					+ // --出生地
					"  (select code_name from code_value where code_type='ZB09' and code_value.code_value=a01.a0148) A0148,"
					+ // --现任职务层次
					"  (select code_name from code_value where code_type='GB4762' and code_value.code_value=a01.a0141) A0141," + 
					// --政治面貌
					"  a01.A0140," + // --入党时间
					"  substr(a01.A0134,0,6) as A0134," + // --参加工作时间
					" a01.A0128,  " + // --健康状况
					" a01.A0196,  " + // --专业技术职务
					" a01.A0187A,  " + // --专业专长
					" a01.QRZXL,  " + // --最高全日制学历
					" a01.QRZXW,  " + // --最高全日制学位
					" a01.QRZXLXX," + // --院校系专业（最高全日制学历）
					" a01.QRZXWXX," + // --院校系专业（最高全日制学位）
					" a01.ZZXL,  " + // --最高在职学历
					" a01.ZZXW,  " + // --最高在职学位
					" a01.ZZXLXX," + // --院校系专业（最高在职学历）
					" a01.ZZXWXX,"+  //--院校系专业（最高在职学位）
					" a01.A0192a," + // --工作单位及职务
					" a01.A1701," + // --简历
					" (case a01.A14z101  when '无' then '(无)' else isnull(a01.A14z101,'(无)') end) as A14z101," + // --奖惩情况
					" isnull(a01.A15Z101,'(无)') as A15Z101 " + // -- 对该人年度考核结果的汉字描述
					" from A01 a01 where a01.a0000='" + a0000 + "'";
		} else {
			sql = "select a01.a0000,a01.A0101,a01.A0184,"
					+ // --姓名
					"  (select code_name from code_value where code_type='GB2261' and code_value.code_value=a01.a0104) A0104,"
					+ // --性别
					"  substr(a01.a0107,0,6) as a0107,"
					+ // --出生日期
					"  GET_BIRTHDAY(a01.a0107,'"+DateUtil.getcurdate()+"') age,"
					+ // --年龄
					"  (select code_name from code_value where code_type='GB3304' and code_value.code_value=a01.a0117) A0117,"
					+ // --民族
					"  a01.A0111a,"
					+ // --籍贯
					"  a01.A0114a,"
					+ // --出生地
					"  (select code_name from code_value where code_type='ZB09' and code_value.code_value=a01.a0148) A0148,"
					+ // --现任职务层次
					"  (select code_name from code_value where code_type='GB4762' and code_value.code_value=a01.a0141) A0141," + // --政治面貌
					"  a01.A0140," + // --入党时间
					" substr(a01.A0134,0,6) as A0134," + // --参加工作时间
					" a01.A0128,  " + // --健康状况
					" a01.A0196,  " + // --专业技术职务
					" a01.A0187A,  " + // --专业专长
					" a01.QRZXL,  " + // --最高全日制学历
					" a01.QRZXW,  " + // --最高全日制学位
					" a01.QRZXLXX," + // --院校系专业（最高全日制学历）
					" a01.QRZXWXX," + // --院校系专业（最高全日制学位）
					" a01.ZZXL,  " + // --最高在职学历
					" a01.ZZXW,  " + // --最高在职学位
					" a01.ZZXLXX," + // --院校系专业（最高在职学历）
					" a01.ZZXWXX,"+  //--院校系专业（最高在职学位）
					" a01.A0192a," + // --工作单位及职务
					" to_char(a01.A1701) as a1701," + // --简历
					" (case a01.A14z101  when '无' then '(无)' else nvl(a01.A14z101,'(无)') end) as A14z101," + // --奖惩情况
					" nvl(a01.A15Z101,'(无)') as A15Z101 " + // -- 对该人年度考核结果的汉字描述
					" from A01 a01 where a01.a0000='" + a0000 + "'";
		}
		
		List list = getListBySql(sql,a0000,true);
		if (list != null && list.size() > 0)
			tmpData = (Map<String, Object>) list.get(0);
		return tmpData; 
	}
	
	public Map<String, Object> getGbrmspbMap(String a0000,String js0122) {
		Map<String, Object> tmpData = null;
		DBType cueDBType = DBUtil.getDBType();
		String sql = "";
		String table= "A01";
		if(js0122!=null && (js0122.equals("2") || js0122.equals("3")|| js0122.equals("4"))) {
			table= "v_js_A01";
		}
		if (cueDBType == DBType.MYSQL) {
			sql = "select a01.a0000,a01.A0101,a01.A0184,"
					+ // --姓名
					"  (select code_name from code_value where code_type='GB2261' and code_value.code_value=a01.a0104) A0104,"
					+ // --性别
					"  a01.A0107,"
					+ // --出生日期
					"  GET_BIRTHDAY(a01.a0107,'"+DateUtil.getcurdate()+"') age,"
					+ // --年龄
					"  (select code_name from code_value where code_type='GB3304' and code_value.code_value=a01.a0117) A0117,"
					+ // --民族
					"  a01.A0111a,"
					+ // --籍贯
					"  a01.A0114a,"
					+ // --出生地
					"  (select code_name from code_value where code_type='ZB09' and code_value.code_value=a01.a0148) A0148,"
					+ // --现任职务层次
					"  (select code_name from code_value where code_type='GB4762' and code_value.code_value=a01.a0141) A0141," + 
					// --政治面貌
					"  a01.A0140," + // --入党时间
					"  a01.A0134," + // --参加工作时间
					" a01.A0128,  " + // --健康状况
					" a01.A0196,  " + // --专业技术职务
					" a01.A0187A,  " + // --专业专长
					" a01.QRZXL,  " + // --最高全日制学历
					" a01.QRZXW,  " + // --最高全日制学位
					" a01.QRZXLXX," + // --院校系专业（最高全日制学历）
					" a01.QRZXWXX," + // --院校系专业（最高全日制学位）
					" a01.ZZXL,  " + // --最高在职学历
					" a01.ZZXW,  " + // --最高在职学位
					" a01.ZZXLXX," + // --院校系专业（最高在职学历）
					" a01.ZZXWXX,"+  //--院校系专业（最高在职学位）
					" a01.A0192a," + // --工作单位及职务
					" to_char(a01.A1701) as a1701," + // --简历
					" a01.A14z101," + // --奖惩情况
					" a01.A15Z101 " + // -- 对该人年度考核结果的汉字描述
					" from "+table+" a01 where a01.a0000='" + a0000 + "'";
		} else {
			sql = "select a01.a0000,a01.A0101,a01.A0184,"
					+ // --姓名
					"  (select code_name from code_value where code_type='GB2261' and code_value.code_value=a01.a0104) A0104,"
					+ // --性别
					"  a01.A0107,"
					+ // --出生日期
					"  GET_BIRTHDAY(a01.a0107,'"+DateUtil.getcurdate()+"') age,"
					+ // --年龄
					"  (select code_name from code_value where code_type='GB3304' and code_value.code_value=a01.a0117) A0117,"
					+ // --民族
					"  a01.A0111a,"
					+ // --籍贯
					"  a01.A0114a,"
					+ // --出生地
					"  (select code_name from code_value where code_type='ZB09' and code_value.code_value=a01.a0148) A0148,"
					+ // --现任职务层次
					"  (select code_name from code_value where code_type='GB4762' and code_value.code_value=a01.a0141) A0141," + // --政治面貌
					"  a01.A0140," + // --入党时间
					"  a01.A0134," + // --参加工作时间
					" a01.A0128,  " + // --健康状况
					" a01.A0196,  " + // --专业技术职务
					" a01.A0187A,  " + // --专业专长
					" a01.QRZXL,  " + // --最高全日制学历
					" a01.QRZXW,  " + // --最高全日制学位
					" a01.QRZXLXX," + // --院校系专业（最高全日制学历）
					" a01.QRZXWXX," + // --院校系专业（最高全日制学位）
					" a01.ZZXL,  " + // --最高在职学历
					" a01.ZZXW,  " + // --最高在职学位
					" a01.ZZXLXX," + // --院校系专业（最高在职学历）
					" a01.ZZXWXX,"+  //--院校系专业（最高在职学位）
					" a01.A0192a," + // --工作单位及职务
					" to_char(a01.A1701) as a1701," + // --简历
					" a01.A14z101," + // --奖惩情况
					" a01.A15Z101 " + // -- 对该人年度考核结果的汉字描述
					" from "+table+" a01 where a01.a0000='" + a0000 + "'";
		}
		
		List list = getListBySql(sql,a0000,true, js0122);
		if (list != null && list.size() > 0)
			tmpData = (Map<String, Object>) list.get(0);
		return tmpData; 
	}
	
	/**
	 * @$comment 获取登记备案表
	 * @param a0000
	 *            人员iD,
	 * @return Map<String, Object>
	 * @throws RadowException
	 * @throws AppException
	 * @throws
	 * @author wuh
	 */
	public Map<String, Object> getGwydjbMap(String a0000) {
		Map<String, Object> tmpData = null;
		DBType cueDBType = DBUtil.getDBType();
		String sql = "";
		if (cueDBType == DBType.MYSQL) {
			sql = "select a01.a0000,a01.A0101,a01.A0184,"
					+ // --姓名
					"  (select code_name from code_value where code_type='GB2261' and code_value.code_value=a01.a0104) A0104,"
					+ // --性别
					"  a01.A0107,"
					+ // --出生日期
					"  (select code_name from code_value where code_type='GB3304' and code_value.code_value=a01.a0117) A0117,"
					+ // --民族
					"  a01.A0111a,"
					+ // --籍贯
					//"  (select code_name from code_value where code_type='ZB09' and code_value.code_value=a01.a0148) A0148,"
					"  (select code_name from code_value where code_type='ZB09' and code_value.code_value=a01.a0221) A0221,"
					+ // --现任职务层次
					"  (select code_name from code_value where code_type='GB4762' and code_value.code_value=a01.a0141) A0141," + 
					// --政治面貌
					"  a01.A0140," + // --入党时间
					"  a01.A0134," + // --参加工作时间
					" a01.A0128,  " + // --健康状况
					" a01.QRZXL,  " + // --最高全日制学历
					" a01.QRZXW,  " + // --最高全日制学位
					" a01.QRZXLXX," + // --院校系专业（最高全日制学历）
					" a01.QRZXWXX," + // --院校系专业（最高全日制学位）
					" a01.ZZXL,  " + // --最高在职学历
					" a01.ZZXW,  " + // --最高在职学位
					" a01.ZZXLXX," + // --院校系专业（最高在职学历）
					" a01.ZZXWXX,"+  //--院校系专业（最高在职学位）
					" a01.A0192a," + // --工作单位及职务
					//" substr(a01.a0192a,1,instr(a01.a0192a,(select GROUP_CONCAT(a02.a0215a) from a02 a02 where a02.a0000 = 'id' and a0255 = '1'))-1) "
					" (select code_name from code_value where code_type='ZB134' and code_value.code_value=a01.a0120) A0120,"+
					// --登记后所定级别
					" a01.A1701," + // --简历
					" a01.A14z101" + // --奖惩情况
					" from A01 a01 where a01.a0000='" + a0000 + "'";
		} else {
			sql = "select a01.a0000,a01.A0101,a01.A0184,"
					+ // --姓名
					"  (select code_name from code_value where code_type='GB2261' and code_value.code_value=a01.a0104) A0104,"
					+ // --性别
					"  a01.A0107,"
					+ // --出生日期
					"  (select code_name from code_value where code_type='GB3304' and code_value.code_value=a01.a0117) A0117,"
					+ // --民族
					"  a01.A0111a,"
					+ // --籍贯
					//"  (select code_name from code_value where code_type='ZB09' and code_value.code_value=a01.a0148) A0148,"
					"  (select code_name from code_value where code_type='ZB09' and code_value.code_value=a01.a0221) A0221,"
					+ // --现任职务层次
					"  (select code_name from code_value where code_type='GB4762' and code_value.code_value=a01.a0141) A0141," + // --政治面貌
					"  a01.A0140," + // --入党时间
					"  a01.A0134," + // --参加工作时间
					" a01.A0128,  " + // --健康状况
					" a01.A0196,  " + // --专业技术职务
					" a01.QRZXL,  " + // --最高全日制学历
					" a01.QRZXW,  " + // --最高全日制学位
					" a01.QRZXLXX," + // --院校系专业（最高全日制学历）
					" a01.QRZXWXX," + // --院校系专业（最高全日制学位）
					" a01.ZZXL,  " + // --最高在职学历
					" a01.ZZXW,  " + // --最高在职学位
					" a01.ZZXLXX," + // --院校系专业（最高在职学历）
					" a01.ZZXWXX,"+  //--院校系专业（最高在职学位）
					" a01.A0192a," + // --工作单位及职务
					//"substr(a01.a0192a,1,instr(a01.a0192a,(select wm_concat(a02.a0215a) from a02 a02 where a02.a0000 = 'id' and a0255 = '1'),1,1)-1)";
					" (select code_name from code_value where code_type='ZB134' and code_value.code_value=a01.a0120) A0120,"+
					// --登记后所定级别
					" to_char(a01.A1701) as a1701," + // --简历
					" a01.A14z101" + // --奖惩情况
					" from A01 a01 where a01.a0000='" + a0000 + "'";
		}
		
		List list = getListBySql(sql,true);
		if (list != null && list.size() > 0)
			tmpData = (Map<String, Object>) list.get(0);
		return tmpData; 
	}
	
	/**
	 * @$comment 获取年度考核登记表
	 * @param a0000
	 *            人员iD,
	 * @return Map<String, Object>
	 * @throws RadowException
	 * @throws AppException
	 * @throws
	 * @author wuh
	 */
	public Map<String, Object> getNdkhdjb(String a0000) {
		Map<String, Object> tmpData = null;
		DBType cueDBType = DBUtil.getDBType();
		String sql = "";
		if (cueDBType == DBType.MYSQL) {
			sql = "select a01.a0000,                                        "+
					"       a01.A0101,                                        "+//姓名
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB2261'                      "+
					"           and code_value.code_value = a01.a0104) A0104, "+//性别
					"       a01.A0107,                                        "+//出生日期
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB4762'                      "+
					"           and code_value.code_value = a01.a0141) A0141, "+//政治面貌
					"       CONCAT_WS('.',                                    "+
					"                 substring(a02.a0243, 1, 4),             "+
					"                 substring(a02.a0243, 5, 2)) a0243,      "+//任职时间
					"       a01.A0192a                                        "+//工作单位及职务
					"  from A01 a01, A02 a02                                  "+
					" where a01.a0000 = a02.a0000                             "+
					"   and a02.a0279 = '1'                                   "+
					"   and a01.a0000 = '"+a0000+"'";

		}else{
			sql = "select a01.a0000,                                                       "+
					"       a01.A0101,                                                       "+
					"       (select code_name                                                "+
					"          from code_value                                               "+
					"         where code_type = 'GB2261'                                     "+
					"           and code_value.code_value = a01.a0104) A0104,                "+
					"       a01.A0107,                                                       "+
					"       (select code_name                                                "+
					"          from code_value                                               "+
					"         where code_type = 'GB4762'                                     "+
					"           and code_value.code_value = a01.a0141) A0141,                "+
					"       substr(a02.a0243, 1, 4) || '.' || substr(a02.a0243, 5, 2) a0243, "+
					"       a01.A0192a                                                       "+
					"  from A01 a01, A02 a02                                                 "+
					" where a01.a0000 = a02.a0000                                            "+
					"   and a02.a0279 = '1'                                                  "+
					"   and a01.a0000 = '"+a0000+"'               ";
		}
		
		List list = getListBySql(sql,false);
		if (list != null && list.size() > 0)
			tmpData = (Map<String, Object>) list.get(0);
		return tmpData; 
	}
	
	/**
	 * @$comment 获取年公务员奖励审批表
	 * @param a0000
	 *            人员iD,
	 * @return Map<String, Object>
	 * @throws RadowException
	 * @throws AppException
	 * @throws
	 * @author wuh
	 */
	public Map<String, Object> getGwyjlspb(String a0000) {
		Map<String, Object> tmpData = null;
		DBType cueDBType = DBUtil.getDBType();
		String sql = "";
		if (cueDBType == DBType.MYSQL) {
			sql = "select a01.a0000,                                        "+
					"       a01.A0101,                                        "+//姓名
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB2261'                      "+
					"           and code_value.code_value = a01.a0104) A0104, "+//性别
					"       a01.A0107,                                        "+//出生日期
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB3304'                      "+
					"           and code_value.code_value = a01.a0117) A0117, "+//民族
					"       a01.A0111a,                                       "+//籍贯
					"       a01.A0114a,                                       "+//出生地
					"       a01.A0184,                                        "+//身份证
					"       a01.A0134,                                        "+//参加工作时间
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB4762'                      "+
					"           and code_value.code_value = a01.a0141) A0141, "+//政治面貌
					"       (select GROUP_CONCAT(a08.a0801a)                  "+
					"          from a08                                       "+
					"         where a01.a0000 = a08.a0000                     "+
					"           and a08.a0834 = '1'                           "+
					"           and a08.a0899 = 'true') a0801a,               "+//学历
					"       (select GROUP_CONCAT(a08.a0901a)                  "+
					"          from a08                                       "+
					"         where a01.a0000 = a08.a0000                     "+
					"           and a08.a0835 = '1'                          "+
					"           and a08.a0899 = 'true') a0901a,               "+//学位
					"       a02.A0201A,                                       "+//工作单位
					"       a02.A0215A,                                       "+//职务
					"       a01.A14z101,                                      "+//奖惩情况
					"       a01.A1701                                         "+//简历
					"  from A01 a01, A02 a02                                  "+
					" where a01.a0000 = a02.a0000                             "+
					"   and a02.a0279 = '1'                                   "+
					"   and a01.a0000 = '"+a0000+"'";

		}else{
			sql = "select a01.a0000,                                        "+
					"       a01.A0101,                                        "+//姓名
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB2261'                      "+
					"           and code_value.code_value = a01.a0104) A0104, "+//性别
					"       a01.A0107,                                        "+//出生日期
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB3304'                      "+
					"           and code_value.code_value = a01.a0117) A0117, "+//民族
					"       a01.A0111a,                                       "+//籍贯
					"       a01.A0114a,                                       "+//出生地
					"       a01.A0184,                                        "+//身份证
					"       a01.A0134,                                        "+//参加工作时间
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB4762'                      "+
					"           and code_value.code_value = a01.a0141) A0141, "+//政治面貌
					"       (select wm_concat(a08.a0801a)                     "+
					"          from a08                                       "+
					"         where a01.a0000 = a08.a0000                     "+
					"           and a08.a0834 = '1'                           "+
					"           and a08.a0899 = 'true') a0801a,               "+//学历
					"       (select wm_concat(a08.a0901a)                     "+
					"          from a08                                       "+
					"         where a01.a0000 = a08.a0000                     "+
					"           and a08.a0835 = '1'                          "+
					"           and a08.a0899 = 'true') a0901a,               "+//学位
					"       a02.A0201A,                                       "+//工作单位
					"       a02.A0215A,                                       "+//职务
					"       a01.A14z101,                                      "+//奖惩情况
					"       to_char(a01.A1701) as a1701                       "+//简历
					"  from A01 a01, A02 a02                                  "+
					" where a01.a0000 = a02.a0000                             "+
					"   and a02.a0279 = '1'                                   "+
					"   and a01.a0000 = '"+a0000+"'";

		}
		
		List list = getListBySql(sql,true);
		if (list != null && list.size() > 0)
			tmpData = (Map<String, Object>) list.get(0);
		return tmpData; 
	}
	
	/**
	 * @$comment 获取公务员录用审批表
	 * @param a0000
	 *            人员iD,
	 * @return Map<String, Object>
	 * @throws RadowException
	 * @throws AppException
	 * @throws
	 * @author wuh
	 */
	public Map<String, Object> getGwylyspb(String a0000) {
		Map<String, Object> tmpData = null;
		DBType cueDBType = DBUtil.getDBType();
		String sql = "";
		if (cueDBType == DBType.MYSQL) {
			sql = "select a01.a0000,                                        "+
					"       a01.A0101,                                        "+//姓名
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB2261'                      "+
					"           and code_value.code_value = a01.a0104) A0104, "+//性别
					"       a01.A0107,                                        "+//出生日期
					"       a01.A0187A,                                       "+//专长
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB3304'                      "+
					"           and code_value.code_value = a01.a0117) A0117, "+//民族
					"       a01.A0111a,                                       "+//籍贯
					"       a01.A0184,                                        "+//身份证
					"       a01.A0134,                                        "+//参加工作时间
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB4762'                      "+
					"           and code_value.code_value = a01.a0141) A0141, "+//政治面貌
					"       (select GROUP_CONCAT(a08.A0814)                  "+
					"          from a08                                       "+
					"         where a01.a0000 = a08.a0000                     "+
					"           and a08.a0834 = '1'                           "+
					"           and a08.a0899 = 'true') a0814,               "+//毕业院校
					"       (select GROUP_CONCAT(a08.A0824)                  "+
					"          from a08                                       "+
					"         where a01.a0000 = a08.a0000                     "+
					"           and a08.a0834 = '1'                           "+
					"           and a08.a0899 = 'true') a0824,               "+//所学专业
					"       (select GROUP_CONCAT(a08.a0801a)                  "+
					"          from a08                                       "+
					"         where a01.a0000 = a08.a0000                     "+
					"           and a08.a0834 = '1'                           "+
					"           and a08.a0899 = 'true') a0801a,               "+//学历
					"       (select GROUP_CONCAT(a08.a0901a)                  "+
					"          from a08                                       "+
					"         where a01.a0000 = a08.a0000                     "+
					"           and a08.a0835 = '1'                          "+
					"           and a08.a0899 = 'true') a0901a,               "+//学位
					"       a02.A0201A,                                       "+//工作单位
					"       a02.A0215A,                                       "+//职务
					"       a01.A14z101,                                      "+//奖惩情况
					"       a01.A1701                                         "+//简历
					"  from A01 a01, A02 a02                                  "+
					" where a01.a0000 = a02.a0000                             "+
					"   and a02.a0279 = '1'                                   "+
					"   and a01.a0000 = '"+a0000+"'";

		}else{
			sql = "select a01.a0000,                                        "+
					"       a01.A0101,                                        "+//姓名
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB2261'                      "+
					"           and code_value.code_value = a01.a0104) A0104, "+//性别
					"       a01.A0107,                                        "+//出生日期
					"       a01.A0187A,                                       "+//专长
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB3304'                      "+
					"           and code_value.code_value = a01.a0117) A0117, "+//民族
					"       a01.A0111a,                                       "+//籍贯
					"       a01.A0184,                                        "+//身份证
					"       a01.A0134,                                        "+//参加工作时间
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB4762'                      "+
					"           and code_value.code_value = a01.a0141) A0141, "+//政治面貌
					"       (select wm_concat(a08.A0814)                  "+
					"          from a08                                       "+
					"         where a01.a0000 = a08.a0000                     "+
					"           and a08.a0834 = '1'                           "+
					"           and a08.a0899 = 'true') a0814,               "+//毕业院校
					"       (select wm_concat(a08.A0824)                  "+
					"          from a08                                       "+
					"         where a01.a0000 = a08.a0000                     "+
					"           and a08.a0834 = '1'                           "+
					"           and a08.a0899 = 'true') a0824,               "+//所学专业
					"       (select wm_concat(a08.a0801a)                     "+
					"          from a08                                       "+
					"         where a01.a0000 = a08.a0000                     "+
					"           and a08.a0834 = '1'                           "+
					"           and a08.a0899 = 'true') a0801a,               "+//学历
					"       (select wm_concat(a08.a0901a)                     "+
					"          from a08                                       "+
					"         where a01.a0000 = a08.a0000                     "+
					"           and a08.a0835 = '1'                          "+
					"           and a08.a0899 = 'true') a0901a,               "+//学位
					"       a02.A0201A,                                       "+//工作单位
					"       a02.A0215A,                                       "+//职务
					"       a01.A14z101,                                      "+//奖惩情况
					"       to_char(a01.A1701) as a1701                       "+//简历
					"  from A01 a01, A02 a02                                  "+
					" where a01.a0000 = a02.a0000                             "+
					"   and a02.a0279 = '1'                                   "+
					"   and a01.a0000 = '"+a0000+"'";

		}
		
		List list = getListBySql(sql,a0000,true);
		if (list != null && list.size() > 0)
			tmpData = (Map<String, Object>) list.get(0);
		return tmpData; 
	}
	
	/**
	 * @$comment 获取公务员录用表
	 * @param a0000
	 *            人员iD,
	 * @return Map<String, Object>
	 * @throws RadowException
	 * @throws AppException
	 * @throws
	 * @author wuh
	 */
	public Map<String, Object> getGwylyb(String a0000) {
		Map<String, Object> tmpData = null;
		DBType cueDBType = DBUtil.getDBType();
		String sql = "";
		if (cueDBType == DBType.MYSQL) {
			sql = "select a01.a0000,                                        "+
					"       a01.A0101,                                        "+//姓名
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB2261'                      "+
					"           and code_value.code_value = a01.a0104) A0104, "+//性别
					"       a01.A0107,                                        "+//出生日期
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB3304'                      "+
					"           and code_value.code_value = a01.a0117) A0117, "+//民族
					"       a01.A0111a,                                       "+//籍贯
					"       a01.A0140,                                        "+//入党时间
					"       a01.A0184,                                        "+//身份证
					"       a01.A0134,                                        "+//参加工作时间
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB4762'                      "+
					"           and code_value.code_value = a01.a0141) A0141, "+//政治面貌
					"       a01.QRZXL,                                        "+//最高全日制学历
					"       a01.QRZXW,                                        "+//最高全日制学位
					"       a01.QRZXLXX,                                      "+//院校系专业（最高全日制学历）
					"       a01.QRZXWXX,                                      "+//院校系专业（最高全日制学位）
					"       a01.ZZXL,                                         "+//最高在职学历
					"       a01.ZZXW,                                         "+//最高在职学位
					"       a01.ZZXLXX,                                       "+//院校系专业（最高在职学历）
					"       a01.ZZXWXX,                                       "+//院校系专业（最高在职学位）
					"       a02.A0201A,                                       "+//工作单位
					"       a02.A0215A,                                       "+//职务
					"       a01.A14z101,                                      "+//奖惩情况
					"       a01.A1701                                         "+//简历
					"  from A01 a01, A02 a02                                  "+
					" where a01.a0000 = a02.a0000                             "+
					"   and a02.a0279 = '1'                                   "+
					"   and a01.a0000 = '"+a0000+"'";

		}else{
			sql = "select a01.a0000,                                        "+
					"       a01.A0101,                                        "+//姓名
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB2261'                      "+
					"           and code_value.code_value = a01.a0104) A0104, "+//性别
					"       a01.A0107,                                        "+//出生日期
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB3304'                      "+
					"           and code_value.code_value = a01.a0117) A0117, "+//民族
					"       a01.A0111a,                                       "+//籍贯
					"       a01.A0184,                                        "+//身份证
					"       a01.A0140,                                        "+
					"       a01.A0134,                                        "+//参加工作时间
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB4762'                      "+
					"           and code_value.code_value = a01.a0141) A0141, "+//政治面貌
					"       a01.QRZXL,                                        "+//最高全日制学历
					"       a01.QRZXW,                                        "+//最高全日制学位
					"       a01.QRZXLXX,                                      "+//院校系专业（最高全日制学历）
					"       a01.QRZXWXX,                                      "+//院校系专业（最高全日制学位）
					"       a01.ZZXL,                                         "+//最高在职学历
					"       a01.ZZXW,                                         "+//最高在职学位
					"       a01.ZZXLXX,                                       "+//院校系专业（最高在职学历）
					"       a01.ZZXWXX,                                       "+//院校系专业（最高在职学位）
					"       a02.A0201A,                                       "+//工作单位
					"       a02.A0215A,                                       "+//职务
					"       a01.A14z101,                                      "+//奖惩情况
					"       to_char(a01.A1701) as a1701                       "+//简历
					"  from A01 a01, A02 a02                                  "+
					" where a01.a0000 = a02.a0000                             "+
					"   and a02.a0279 = '1'                                   "+
					"   and a01.a0000 = '"+a0000+"'";

		}
		
		List list = getListBySql(sql,a0000,true);
		if (list != null && list.size() > 0)
			tmpData = (Map<String, Object>) list.get(0);
		return tmpData; 
	}
	
	/**
	 * @$comment 获取公务员录用表
	 * @param a0000
	 *            人员iD,
	 * @return Map<String, Object>
	 * @throws RadowException
	 * @throws AppException
	 * @throws
	 * @author wuh
	 */
	public Map<String, Object> getGwydrspb(String a0000) {
		Map<String, Object> tmpData = null;
		DBType cueDBType = DBUtil.getDBType();
		String sql = "";
		if (cueDBType == DBType.MYSQL) {
			sql = "select a01.a0000,                                        "+
					"       a01.A0101,                                        "+//姓名
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB2261'                      "+
					"           and code_value.code_value = a01.a0104) A0104, "+//性别
					"       a01.A0107,                                        "+//出生日期
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB3304'                      "+
					"           and code_value.code_value = a01.a0117) A0117, "+//民族
					"       a01.A0111a,                                       "+//籍贯
					"       a01.A0114A,                                       "+//出生地
					"       a01.A0134,                                        "+//参加工作时间
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB4762'                      "+
					"           and code_value.code_value = a01.a0141) A0141, "+//政治面貌
					"       a01.A0196,                                        "+//专业技术职务
					"       a01.A0128,                                        "+//健康状况
					"       a01.QRZXL,                                        "+//最高全日制学历
					"       a01.QRZXW,                                        "+//最高全日制学位
					"       a01.QRZXLXX,                                      "+//院校系专业（最高全日制学历）
					"       a01.QRZXWXX,                                      "+//院校系专业（最高全日制学位）
					"       a01.ZZXL,                                         "+//最高在职学历
					"       a01.ZZXW,                                         "+//最高在职学位
					"       a01.ZZXLXX,                                       "+//院校系专业（最高在职学历）
					"       a01.ZZXWXX,                                       "+//院校系专业（最高在职学位）
					"       a01.A0192A,                                       "+//工作单位及职务
					"       a01.A14z101,                                      "+//奖惩情况
					"       a01.A1701                                         "+//简历
					"  from A01 a01, A02 a02                                  "+
					" where a01.a0000 = a02.a0000                             "+
					"   and a02.a0279 = '1'                                   "+
					"   and a01.a0000 = '"+a0000+"'";

		}else{
			sql = "select a01.a0000,                                        "+
					"       a01.A0101,                                        "+//姓名
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB2261'                      "+
					"           and code_value.code_value = a01.a0104) A0104, "+//性别
					"       a01.A0107,                                        "+//出生日期
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB3304'                      "+
					"           and code_value.code_value = a01.a0117) A0117, "+//民族
					"       a01.A0111a,                                       "+//籍贯
					"       a01.A0114A,                                       "+//出生地
					"       a01.A0134,                                        "+//参加工作时间
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB4762'                      "+
					"           and code_value.code_value = a01.a0141) A0141, "+//政治面貌
					"       a01.A0128,                                        "+//健康状况
					"       a01.A0196,                                        "+//专业技术职务
					"       a01.QRZXL,                                        "+//最高全日制学历
					"       a01.QRZXW,                                        "+//最高全日制学位
					"       a01.QRZXLXX,                                      "+//院校系专业（最高全日制学历）
					"       a01.QRZXWXX,                                      "+//院校系专业（最高全日制学位）
					"       a01.ZZXL,                                         "+//最高在职学历
					"       a01.ZZXW,                                         "+//最高在职学位
					"       a01.ZZXLXX,                                       "+//院校系专业（最高在职学历）
					"       a01.ZZXWXX,                                       "+//院校系专业（最高在职学位）
					"       a01.A0192A,                                       "+//工作单位及职务
					"       a01.A14z101,                                      "+//奖惩情况
					"       to_char(a01.A1701) as a1701                       "+//简历
					"  from A01 a01, A02 a02                                  "+
					" where a01.a0000 = a02.a0000                             "+
					"   and a02.a0279 = '1'                                   "+
					"   and a01.a0000 = '"+a0000+"'";

		}
		
		List list = getListBySql_drb(sql,a0000,true);
		if (list != null && list.size() > 0)
			tmpData = (Map<String, Object>) list.get(0);
		return tmpData; 
	}
	/**
	 * @$comment 公务员职级套转表
	 * @param a0000
	 * @return Map<String,Object>
	 * @author laizl
	 */
	public Map<String,Object> getGwyzjtgb(String a0000){
		Map<String, Object> tmpData = null;
		DBType cueDBType = DBUtil.getDBType();
		String sql = "";
		if(cueDBType == DBType.MYSQL){
			sql = "select a01.a0000,                                        "+
					"       a01.A0101,                                        "+//姓名
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB2261'                      "+
					"           and code_value.code_value = a01.a0104) A0104, "+//性别
					"       a01.A0107,                                        "+//出生日期
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB3304'                      "+
					"           and code_value.code_value = a01.a0117) A0117, "+//民族
					"       a01.A0111A,                                       "+//籍贯
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB4762'                      "+
					"           and code_value.code_value = a01.a0141) A0141, "+//政治面貌
					"       a01.A0184,                                        "+//身份证号码
					"       a01.QRZXL,                                        "+//最高全日制学历
					"       a01.QRZXW,                                        "+//最高全日制学位
					"       a01.QRZXLXX,                                      "+//院校系专业（最高全日制学历）
					"       a01.QRZXWXX,                                      "+//院校系专业（最高全日制学位）
					"       a01.ZZXL,                                         "+//最高在职学历
					"       a01.ZZXW,                                         "+//最高在职学位
					"       a01.ZZXLXX,                                       "+//院校系专业（最高在职学历）
					"       a01.ZZXWXX,                                       "+//院校系专业（最高在职学位）
					"       a01.A0192A,                                       "+//工作单位及职务
					"       a01.A2949,                                        "+//公务员登记时间
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'ZB09'                        "+
					"           and code_value.code_value = a01.a0221) A0148, "+//任现职务层次
					"       a01.A0288,                                        "+//任现职务层次时间
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'ZB148'                       "+
					"          and code_value.code_value = a01.A0192E) A0192D,"+//任现职级
					"       a01.A0192C A0192T,                                "+//任现职级时间
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'ZB148'                       "+
					"          and code_value.code_value = a01.A6506) A6506,"  +//套转后职级
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'ZB134'                       "+
					"          and code_value.code_value = a01.A0120) A0120 "  +//级别
					"  from A01 a01, A02 a02                                  "+
					" where a01.a0000 = a02.a0000                             "+
					"   and a02.a0279 = '1'                                   "+
					"   and a01.a0000 = '"+a0000+"'";
		}else{
			sql = "select a01.a0000,                                        "+
					"       a01.A0101,                                        "+//姓名
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB2261'                      "+
					"           and code_value.code_value = a01.a0104) A0104, "+//性别
					"       a01.A0107,                                        "+//出生日期
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB3304'                      "+
					"           and code_value.code_value = a01.a0117) A0117, "+//民族
					"       a01.A0111a,                                       "+//籍贯
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'GB4762'                      "+
					"           and code_value.code_value = a01.a0141) A0141, "+//政治面貌
					"       a01.A0184,                                        "+//身份证号码
					"       a01.QRZXL,                                        "+//最高全日制学历
					"       a01.QRZXW,                                        "+//最高全日制学位
					"       a01.QRZXLXX,                                      "+//院校系专业（最高全日制学历）
					"       a01.QRZXWXX,                                      "+//院校系专业（最高全日制学位）
					"       a01.ZZXL,                                         "+//最高在职学历
					"       a01.ZZXW,                                         "+//最高在职学位
					"       a01.ZZXLXX,                                       "+//院校系专业（最高在职学历）
					"       a01.ZZXWXX,                                       "+//院校系专业（最高在职学位）
					"       a01.A0192A,                                       "+//工作单位及职务
					"       a01.A2949,                                        "+//公务员登记时间
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'ZB09'                        "+
					"           and code_value.code_value = a01.a0221) A0148, "+//任现职务层次
					"       a01.A0288,                                        "+//任现职务层次时间
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'ZB148'                       "+
					"          and code_value.code_value = a01.A0192E) A0192D,"+//任现职级
					"       a01.A0192C A0192T,                                "+//任现职级时间
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'ZB148'                       "+
					"          and code_value.code_value = a01.A6506) A6506,"  +//套转后职级
					"       (select code_name                                 "+
					"          from code_value                                "+
					"         where code_type = 'ZB134'                       "+
					"          and code_value.code_value = a01.A0120) A0120 "  +//级别
					"  from A01 a01, A02 a02                                  "+
					" where a01.a0000 = a02.a0000                             "+
					"   and a02.a0279 = '1'                                   "+
					"   and a01.a0000 = '"+a0000+"'";
		}
		List list = getListBySql_drb(sql,a0000,true);
		if (list != null && list.size() > 0)
			tmpData = (Map<String, Object>) list.get(0);
		return tmpData;
	}
	
	/**
	 * @$comment 根据传入SQL返回查询信息集
	 * @param SQL
	 *            ,isHasPic是否获取照片
	 * @return List<Map<String, String>>
	 * @throws AppException
	 * @throws
	 * @author wuh
	 */
	public List<Map<String, Object>> getListBySql_drb(String sql1,String a0000,Boolean isHasPic) {
		List<Map<String, Object>> list = null;
		try {
			HBSession session = HBUtil.getHBSession();
			CommonQueryBS query = new CommonQueryBS();
			query.setConnection(session.connection());
			query.setQuerySQL(sql1);
			Vector<?> vector = query.query();
			Iterator<?> iterator = vector.iterator();
			String sql = "";
			DBType cueDBType = DBUtil.getDBType();
			if(cueDBType == cueDBType.MYSQL){
				sql = "select a3604a a3604,"
					     + "a3601,"
					     + "CONCAT_WS('.',substr(a36.a3607, 1, 4),substr(a36.a3607, 5, 2)) as a3607,"
					     + "a3627,"
					     + "a3611,a3604a from A36 a36 where a0000 = '"+a0000+"' order by SORTID";
			}else{
				sql = "select a3604a a3604,"
					     + "a3601,"
					     + "substr(a36.a3607, 1, 4) || '.' || substr(a36.a3607, 5, 2) as a3607,"
					     + "a3627,"
					     + "a3611,a3604a from A36 a36 where a0000 = '"+a0000+"' order by SORTID";
			}
			 
			if (iterator.hasNext()) {
				list = new ArrayList<Map<String, Object>>();
				while (iterator.hasNext()) {

					Map<String, Object> tmp = (Map<String, Object>) iterator
							.next();
					// 如果需要照片数据且人员id不为空则获取照片数据
					if (!"".equals(tmp.get("a0000"))
							&& tmp.get("a0000") != null) {
						tmp.put("image", getImageStr((String) tmp.get("a0000")));
					}
					List<Object[]> list2 = session.createSQLQuery(sql).list();//查询家庭成员
					Object[] obj=new Object[5];
					int size = list2.size();
					if(size<7){
						//家庭成员少于7个用空字符串补齐
						int count = 7-size;
						for(int i=0;i<count;i++){
							list2.add(obj);
						}
					}
					int cy = 1;//区分第几个家庭成员
					for(int j=0;j<7;j++){
						//最多只显示7个家庭成员
						Object[] obj2 = list2.get(j);
						tmp.put("cw"+cy, obj2[0]==null?"":obj2[0]);
						tmp.put("xm"+cy, obj2[1]==null?"":obj2[1]);
						tmp.put("nl"+cy, obj2[2]==null?"":obj2[2]);
						tmp.put("zz"+cy, obj2[3]==null?"":obj2[3]);
						tmp.put("gz"+cy, obj2[4]==null?"":obj2[4]);
						cy++;
					}
					list.add(tmp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	/**
	 * @$comment 根据传入SQL返回查询信息集
	 * @param SQL
	 *            ,isHasPic是否获取照片
	 * @return List<Map<String, String>>
	 * @throws AppException
	 * @throws
	 * @author wuh
	 */
	public List<Map<String, Object>> getListBySql(String sql1,String a0000,Boolean isHasPic) {
		List<Map<String, Object>> list = null;
		try {
			HBSession session = HBUtil.getHBSession();
			CommonQueryBS query = new CommonQueryBS();
			query.setConnection(session.connection());
			query.setQuerySQL(sql1);
			Vector<?> vector = query.query();
			Iterator<?> iterator = vector.iterator();
			String sql = "select a3604a a3604,"
					     + "a3601,"
					     + "GET_BIRTHDAY(a36.a3607,'"+DateUtil.getcurdate()+"') "
					     + "a3607,"
					     + "a3627,"
					     + "a3611,a3604a,"
					     + "substr(a36.a3607,1,4)||'.'||substr(a36.a3607,5,2) a3607sr  from A36 a36 where a0000 = '"+a0000+"' order by SORTID";
			if (iterator.hasNext()) {
				list = new ArrayList<Map<String, Object>>();
				while (iterator.hasNext()) {

					Map<String, Object> tmp = (Map<String, Object>) iterator
							.next();
					// 如果需要照片数据且人员id不为空则获取照片数据
					if (!"".equals(tmp.get("a0000"))
							&& tmp.get("a0000") != null) {
						tmp.put("image", getImageStr((String) tmp.get("a0000")));
					}
					List<Object[]> list2 = session.createSQLQuery(sql).list();//查询家庭成员
					Object[] obj=new Object[7];
					int size = list2.size();
					if(size<7){
						//家庭成员少于7个用空字符串补齐
						int count = 7-size;
						for(int i=0;i<count;i++){
							list2.add(obj);
						}
					}
					int cy = 1;//区分第几个家庭成员
					for(int j=0;j<7;j++){
						//最多只显示7个家庭成员
						Object[] obj2 = list2.get(j);
						String gz = obj2[4]+"";
						tmp.put("cw"+cy, obj2[0]==null?"":obj2[0]);
						tmp.put("xm"+cy, obj2[1]==null?"":obj2[1]);
						if(gz.contains("去世")||gz.contains("已故")||gz.contains("离世")||gz.contains("死")){
							//家庭成员去世的则年龄为空
							tmp.put("nl"+cy, "");
							tmp.put("csny"+cy, "");
						}else{
							tmp.put("nl"+cy, obj2[2]==null?"":obj2[2]);
							tmp.put("csny"+cy, obj2[6]==null?"":obj2[6]);
						}
						tmp.put("zz"+cy, obj2[3]==null?"":obj2[3]);
						tmp.put("gz"+cy, obj2[4]==null?"":obj2[4]);
						cy++;
					}
					list.add(tmp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return list;
	}
	public List<Map<String, Object>> getListBySql(String sql1,String a0000,Boolean isHasPic, String js0122) {
		List<Map<String, Object>> list = null;
		try {
			String tableExt= "";
			if(js0122!=null && (js0122.equals("2") || js0122.equals("3")|| js0122.equals("4"))) {
				tableExt= "v_js_";
			}
			HBSession session = HBUtil.getHBSession();
			CommonQueryBS query = new CommonQueryBS();
			query.setConnection(session.connection());
			query.setQuerySQL(sql1);
			Vector<?> vector = query.query();
			Iterator<?> iterator = vector.iterator();
			String sql = "select a3604a a3604,"
					     + "a3601,"
					     + "GET_BIRTHDAY(a36.a3607,'"+DateUtil.getcurdate()+"') "
					     + "a3607,"
					     + "a3627,"
					     + "a3611,a3604a from "+tableExt+"A36 a36 where a0000 = '"+a0000+"' order by SORTID";
			if (iterator.hasNext()) {
				list = new ArrayList<Map<String, Object>>();
				while (iterator.hasNext()) {

					Map<String, Object> tmp = (Map<String, Object>) iterator
							.next();
					// 如果需要照片数据且人员id不为空则获取照片数据
					if (!"".equals(tmp.get("a0000"))
							&& tmp.get("a0000") != null) {
						tmp.put("image", getImageStr((String) tmp.get("a0000")));
					}
					List<Object[]> list2 = session.createSQLQuery(sql).list();//查询家庭成员
					Object[] obj=new Object[5];
					int size = list2.size();
					if(size<7){
						//家庭成员少于7个用空字符串补齐
						int count = 7-size;
						for(int i=0;i<count;i++){
							list2.add(obj);
						}
					}
					int cy = 1;//区分第几个家庭成员
					for(int j=0;j<7;j++){
						//最多只显示7个家庭成员
						Object[] obj2 = list2.get(j);
						String gz = obj2[4]+"";
						tmp.put("cw"+cy, obj2[0]==null?"":obj2[0]);
						tmp.put("xm"+cy, obj2[1]==null?"":obj2[1]);
						if(gz.contains("去世")||gz.contains("已故")||gz.contains("离世")||gz.contains("死")){
							//家庭成员去世的则年龄为空
							tmp.put("nl"+cy, "");
						}else{
							tmp.put("nl"+cy, obj2[2]==null?"":obj2[2]);
						}
						tmp.put("zz"+cy, obj2[3]==null?"":obj2[3]);
						tmp.put("gz"+cy, obj2[4]==null?"":obj2[4]);
						cy++;
					}
					list.add(tmp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return list;
	}
	/**
	 * 自定义导出处理
	 * 只针对A01数据单个处理
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getGbrmspbMapw(String a0000,List list1,String queryCondition,String rad) {
		Map<String, Object> tmpData = null;
		Map<String, Object> tmpData1 = null;
		Map<String, Object> tmpData2 = null;
		DBType cueDBType = DBUtil.getDBType();
		String field="";
		TreeSet<String> table=new TreeSet<String>();
		HBSession sess = HBUtil.getHBSession();
		String tablestr="";
		for( int i=0;i<list1.size();i++){
			String str = (String) list1.get(i);
			String[] split = str.split("_");
			field+=split[0]+"."+split[1]+" "+split[0]+"_"+split[1]+",";
			table.add(split[0]);
		}
		 field=field.substring(0, field.length()-1);
		 String[] field1 = field.split(",");
		 String fieldA01="";
		 for (String str : field1) {
			if(str.split(" ")[0].split("\\.")[0].equals("A01")){
				fieldA01+=str+",";
			}
		}
		 fieldA01=fieldA01.substring(0, fieldA01.length()-1);//取所有字段
		
		String[] fieldArr = fieldA01.split(",");//遍历所有字段
		String newField="";
		for (String str : fieldArr) {//遍历所有字段
			String[] split = str.split(" ");
			List<String> temp1  = Arrays.asList(split);
			String[] split2 = split[0].split("\\.");
			List<String> tempList  = Arrays.asList(split2);
			String sql1="";
			if(rad.equals("yes")||rad.equals("undefined")){//代码转换
				if(tempList.get(1).contains("yy")){
					String bm=tempList.get(0);
					String zid=tempList.get(1);
					String zidm=zid.substring(0, zid.indexOf("#"));
					 sql1="SELECT CODE_TYPE FROM CODE_TABLE_COL WHERE TABLE_CODE='"+bm+"' AND COL_CODE='"+zidm+"'";
				}else{
					 sql1="SELECT CODE_TYPE FROM CODE_TABLE_COL WHERE TABLE_CODE='"+tempList.get(0)+"' AND COL_CODE='"+tempList.get(1)+"'";
				}
				
				//System.out.println("sql============================"+sql1);
				String codetable = (String) sess.createSQLQuery(sql1).uniqueResult();
				if(!StringUtil.isEmpty(codetable)){
				/*	if("ZB01".equals(codetable.toUpperCase())){
						String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId().trim();//当前用户ID
						sql1=" select CODEVALUE from CODELEVEL where USERID='"+cueUserid+"'";
						List<String> list2 = HBUtil.getHBSession().createSQLQuery(sql1).list();
						if(list2!=null&&list2.size()>0){
							String value2 = list2.get(0);
							if("3".equals(value2)){
								str="(select code_name2 from code_value where code_type='"+codetable+"' and code_value.code_value="+temp1.get(0)+")  "+temp1.get(1)+"";
							}else{
								str="(select code_name3 from code_value where code_type='"+codetable+"' and code_value.code_value="+temp1.get(0)+")  "+temp1.get(1)+"";
							}
						}else{
							str="(select code_name3 from code_value where code_type='"+codetable+"' and code_value.code_value="+temp1.get(0)+")  "+temp1.get(1)+"";
						}
					}else{*/
						str="(select code_name from code_value where code_type='"+codetable+"' and code_value.code_value="+temp1.get(0)+")  "+temp1.get(1)+"";
					//}
					//System.out.println("str=================================="+str);
				}
			}else{
				if(tempList.get(1).contains("yy")){
					String bm=tempList.get(0);
					String zid=tempList.get(1);
					String zidm=zid.substring(0, zid.indexOf("#"));
					 sql1="SELECT CODE_TYPE FROM CODE_TABLE_COL WHERE TABLE_CODE='"+bm+"' AND COL_CODE='"+zidm+"'";
				}else{
					 sql1="SELECT CODE_TYPE FROM CODE_TABLE_COL WHERE TABLE_CODE='"+tempList.get(0)+"' AND COL_CODE='"+tempList.get(1)+"'";
				}
				
				//System.out.println("sql============================"+sql1);
				String codetable = (String) sess.createSQLQuery(sql1).uniqueResult();
			}
			
			if(tempList.get(1).contains("yy")){
				String zid=tempList.get(1);
				String zidm=zid.substring(0, zid.indexOf("#"));
				String tem=temp1.get(0);
				String tem1=tem.substring(tem.indexOf("#")+1);
				String sql3="";
				if(tem1.equals("yyyy.mm")){
					if (cueDBType == DBType.MYSQL) {
						sql3=" CONCAT(SUBSTR("+zidm+",1,4),'.',SUBSTR("+zidm+",-4,2))";
						//CONCAT(substr(A0107, 1, 4)  ,'.' ,substr(A0107 ,- 4, 2))
					}else{
						 sql3=" (SUBSTR( "+zidm+",1,4))||'.'||(SUBSTR("+zidm+",-4,2))";	
					}
					
				}else if(tem1.equals("yy-mm-dd")){
					if (cueDBType == DBType.MYSQL) {
						sql3="CONCAT(SUBSTR( "+zidm+",1,4),'-',SUBSTR("+zidm+",-4,2),'-',SUBSTR("+zidm+",-2,2))";
					}else{
						sql3="(SUBSTR( "+zidm+",1,4))||'-'||(SUBSTR("+zidm+",-4,2)||'-'||(SUBSTR("+zidm+",-2,2)))";
					}
				}else if(tem1.equals("yy.mm")){
					if (cueDBType == DBType.MYSQL) {
						sql3 ="CONCAT(SUBSTR("+zidm+",3,2),'.',SUBSTR("+zidm+",-4,2))";
					}else{
						sql3 ="(SUBSTR( "+zidm+",3,2))||'.'||(SUBSTR("+zidm+",-4,2))";
					}
				}else {
					sql3=zidm;
				}
				
				newField+=(sql3+"\""+temp1.get(1))+"\",";
				//newField+=(zidm+"  "+temp1.get(1))+",";
				//newField+=str+",";
			}else{
				newField+=str+",";
			}
			
		}
		
		newField=newField.substring(0, newField.length()-1).replace("A01.A1701", "to_char(A01.A1701)").replace("A01.A0187BQ", "to_char(A01.A0187BQ)").replace("A01.S0143Q", "to_char(A01.S0143Q)");
		 String[] s2 = new String[table.size()];
	        
        for (int i = 0; i < s2.length; i++) {  
            s2[i] = table.pollFirst();
       
        }  
        for (int i = 0; i < s2.length; i++) {  
        	tablestr+=s2[i]+",";
       
        } 
       
        tablestr=tablestr.substring(0, tablestr.length()-1);//A01, A02, A36
        String[] tablestrArr = tablestr.split(",");//A01, A02, A36
        String newtablestr="";
        for (String str : tablestrArr) {
			if(str.contains("A01")){
				newtablestr+=str+",";
			}
		}
        newtablestr=newtablestr.substring(0, newtablestr.length()-1);//去逗号A01 A01
        newtablestr=newtablestr+" "+newtablestr;//A01 A01
        

	    //System.out.println("ffffffffffffffffffffffff"+newField);//A01.A0101 A01_A0101,(select code_name from code_value where code_type='GB2261' and code_value.code_value=A01.A0104) A01_A0104,(select code_name from code_value where code_type='ZB01' and code_value.code_value=A01.A0111) A01_A0111,(select code_name from code_value where code_type='ZB01' and code_value.code_value=A01.A0114) A01_A0114

	    //System.out.println("tttttttttttttttttttttttttttt"+newtablestr);//A01 A01
		String sql = "";
			sql = "select "+"a01.a0000"+" "+","+" "
					+newField+
					
					" from  "+newtablestr+"  where a01.a0000='" + a0000 + "'";
		
		//System.out.println("=================================value"+sql);
	  if("A01".equals(tablestr)){
		  List list=this.getListBySqlA01(sql, a0000, true);
		  tmpData=(Map<String, Object>) list.get(0);
	  }else{
		  List list = getListBySqlw(sql,a0000,true,list1,queryCondition,rad);
			if (list != null && list.size() > 0)
				tmpData1 = (Map<String, Object>) list.get(0);
			
			 List list2 =getListBySqlz(sql,a0000,true,list1,queryCondition,rad);
			 if (list2 != null && list2.size() > 0)
					tmpData2 = (Map<String, Object>) list2.get(0);
			 
			 //tmpData.put(tmpData);
			 			 
			 List<Map<String, Object>>  listsum = new ArrayList();
				if(list==null||list.size()<0){
					listsum.addAll(list2);			 
				  }else if(list2==null||list2.size()<0){
					  listsum.addAll(list);
				  }else{
					  list2.addAll(list);
					  listsum.addAll(list2); 
				  }
				
			tmpData = new HashMap();
			for(Map m:listsum){
				tmpData.putAll(m);
			}
	  }
	 
		
		return tmpData; 
	}
	
	public int getListIndex(int size,int num){
		if(num>=0){
			return num;
		}else{//-1:size -2:size-1 -3:size-2
			return size + (num+1);
		}
	}
	/**
	 * @$comment 根据传入SQL返回查询信息集
	 * @param SQL
	 *            ,isHasPic是否获取照片
	 * @return List<Map<String, String>>
	 * @throws AppException
	 * 所有多条数据处理方法,如果没有以空代替
	 * 倒序负值处理方式
	 */
	public List<Map<String, Object>> getListBySqlw(String sql1,String a0000,Boolean isHasPic,List list1,String queryCondition,String rad) {
		DBType cueDBType = DBUtil.getDBType();
		if(queryCondition==null){
			queryCondition="" ;
		}
		HBSession sess = HBUtil.getHBSession();
		List<Map<String, Object>> list = null;
		try {
			HBSession session = HBUtil.getHBSession();
			CommonQueryBS query = new CommonQueryBS();
			query.setConnection(session.connection());
			query.setQuerySQL(sql1);
			TreeSet<String> table=new TreeSet<String>();
			String field="";
			for( int i=0;i<list1.size();i++){//取所有表名
				String str = (String) list1.get(i);
				String[] split = str.split("_");
				field+=split[0]+"."+split[1]+" "+split[0]+"_"+split[1]+",";
				table.add(split[0]);
			}
			
			//去掉所有A01字段
		List<String> list1Tmp = new ArrayList<String>();
			List<String> s2Tmp = new ArrayList<String>();
			for( int i=0;i<list1.size();i++){
				String str = (String) list1.get(i);
				if(!str.split("_")[0].equals("A01")){
					list1Tmp.add(str);
				}
			}
			String[] s2 = new String[table.size()];
	        
	        for (int i = 0; i < s2.length; i++) {  
	            s2[i] = table.pollFirst();
	       
	        }  
	        for (int i = 0; i < s2.length; i++) {  
	            if(!s2[i].equals("A01")){
	            	s2Tmp.add(s2[i]);
	            }
	       
	        }  
	        Map<String, String> map=new HashMap<String, String>();
	        for (String stt : s2Tmp) {
	        	String aa="";
	        	String bb="";
				for(int i=0;i<list1Tmp.size();i++){
					String fi= list1Tmp.get(i);
				    if(fi.contains(stt)){
						String[] split = fi.split("#");
						bb+=split[1]+",";
						fi=split[0];				
						aa+=fi+",";
					}
				}
				bb=bb.substring(0,bb.length()-1);
				String[] bb2 = bb.split(",");
				String zheng ="";
				String fu ="";
				for (String string : bb2) {
					if(string.contains("-")){
						fu+=string+",";
					}else{
						zheng+=string+",";
					}
				}
				if(fu==""||fu==null)
					return list;
				fu=fu.substring(0,fu.length()-1);
				String fu1=fu.replace("-", "");
				String[] bbArr = fu1.split(",");
				Integer maxfu=0;
				for(int i=0;i<bbArr.length;i++){
					if(maxfu<=Integer.parseInt(bbArr[i])){
						maxfu=Integer.parseInt(bbArr[i]);
						//maxfu=maxfu*-1;
					}
				}
				maxfu=maxfu*-1;
				Integer maxf=0;//-5
				if(maxfu<0){
					maxf= maxfu;
					aa=aa.substring(0,aa.length()-1);
					aa=aa+","+maxf;
					map.put(stt, aa);
				}
				
			}
	        
	        Map<String, String> map1=new HashMap<String, String>();
	        
	        for (String stt : s2Tmp) {
	        	String aa="";
	        	String bb="";
				for(int i=0;i<list1Tmp.size();i++){
					String fi= list1Tmp.get(i);
				    if(fi.contains(stt)){
						String[] split = fi.split("#");
						bb+=split[1]+",";
						fi=split[0];				
						aa+=fi+",";
					}
				}
				bb=bb.substring(0,bb.length()-1);
				String[] bb2 = bb.split(",");
				String zheng ="";
				String fu ="";
				for (String string : bb2) {
					if(string.contains("-")){
						fu+=string+",";
					}
				}
				fu=fu.substring(0,fu.length()-1);
				String fu1=fu.replace("-", "");
				String[] bbArr = fu1.split(",");
				Integer maxfu=0;
				for(int i=0;i<bbArr.length;i++){
					if(maxfu<=Integer.parseInt(bbArr[i])){
						maxfu=Integer.parseInt(bbArr[i]);
						//maxfu=maxfu*-1;
					}
				}
				maxfu=maxfu*-1;
				Integer maxf=0;//-5
				
				
			}
	       // map.remove("A01");
	        
	        
	        Vector<?> vector = query.query();
			Iterator<?> iterator = vector.iterator();
			
	if (iterator.hasNext()) {
		while (iterator.hasNext()) {
			Map<String, Object> tmp = (Map<String, Object>) iterator.next();
			if (!"".equals(tmp.get("a0000"))
					&& tmp.get("a0000") != null) {
				tmp.put("image", getImageStr((String) tmp.get("a0000")));
			}
			list = new ArrayList<Map<String, Object>>();
	        for(Map.Entry<String, String> e : map.entrySet()){
	        	String key=e.getKey();
	            String value=e.getValue();
	            String[] valueArr = value.split(",");
	            value=value.replace(","+valueArr[valueArr.length-1], "");
	            TreeSet<String> valueSet = new TreeSet<String>();
	            String[] valueArrhh = value.split(",");
	            for (String string : valueArrhh) {
	            	valueSet.add(string);
				}
	            
	            String[] s3 = new String[valueSet.size()];  
		        
		        for (int i = 0; i < s3.length; i++) {  
		            s3[i] = valueSet.pollFirst();
		       
		        } 
		        String queryValue="";
		        for (String string : s3) {
		        	queryValue+=string+",";
				}
		        String mapqueryValue=queryValue.substring(0, queryValue.length()-1);
		        queryValue=queryValue.substring(0, queryValue.length()-1).replace("_", ".");
		        String[] split = queryValue.split(",");
		        String  newQueryValue="";
		        for (String string : split) {
		        	String tableNmae=string.split("\\.")[0];
		        	String colNmae=string.split("\\.")[1];
		        	if(rad.equals("yes")||rad.equals("undefined")){
		        		if(colNmae.contains("yy")){
			        		colNmae=colNmae.substring(0, colNmae.indexOf("@"));
			        	}
			        	 String	sql="SELECT CODE_TYPE FROM CODE_TABLE_COL WHERE TABLE_CODE='"+tableNmae+"' AND COL_CODE='"+colNmae+"'";
			        	
			        	//System.out.println("sql================================"+sql);
			        	String codetable = (String) sess.createSQLQuery(sql).uniqueResult();
			        	if(!StringUtil.isEmpty(codetable)){
			        		/*if("ZB01".equals(codetable.toUpperCase())){
								String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId().trim();//当前用户ID
								sql=" select CODEVALUE from CODELEVEL where USERID='"+cueUserid+"'";
								List<String> list2 = HBUtil.getHBSession().createSQLQuery(sql).list();
								if(list2!=null&&list2.size()>0){
									String value2 = list2.get(0);
									if("3".equals(value2)){
										string="(select code_name2 from code_value where code_type='"+codetable+"' and code_value.code_value="+string+") "+string.replace(".", "_");
									}else{
										string="(select code_name3 from code_value where code_type='"+codetable+"' and code_value.code_value="+string+") "+string.replace(".", "_");
									}
								}else{
									string="(select code_name3 from code_value where code_type='"+codetable+"' and code_value.code_value="+string+") "+string.replace(".", "_");
								}
							}else{*/
								string="(select code_name from code_value where code_type='"+codetable+"' and code_value.code_value="+string+") "+string.replace(".", "_");
							//}
			        		//string="(select code_name from code_value where code_type='"+codetable+"' and code_value.code_value="+string+") "+string.replace(".", "_");
			        		//System.out.println("string========================"+string);
			        	}else{
			        		if("A36.A3602C".equals(string)){//家庭成员年龄
			        			string="GET_BIRTHDAY(A36.A3607,'"+DateUtil.getcurdate()+"')";
			        		}else if(string.contains("@yyyy.mm")){//格式化家庭成员出生年月
			        			if (cueDBType == DBType.MYSQL) {
			        				//CONCAT(substr(A0107, 1, 4)  ,'.' ,substr(A0107 ,- 4, 2))
			        				string="CONCAT(SUBSTR("+colNmae+",1,4),'.',SUBSTRING("+colNmae+",-4,2))";
			        			}else{
			        				string="(SUBSTR( "+colNmae+",1,4))||'.'||(SUBSTR( "+colNmae+",-4,2))";
			        			}
			        		}else if(string.contains("@yy-mm-dd")){
			        			if (cueDBType == DBType.MYSQL) {
			        				string="CONCAT(SUBSTR("+colNmae+",1,4),'-',SUBSTR("+colNmae+",-4,2),'-',SUBSTR("+colNmae+",-2,2))";
			        			}else{
			        				string="(SUBSTR("+colNmae+",1,4))||'-'||(SUBSTR( "+colNmae+",-4,2)||'-'||(SUBSTR("+colNmae+",-2,2)))";
			        			}
			        		
			        		}else if(string.contains("@yy.mm")){
			        			if (cueDBType == DBType.MYSQL) {
			        				string ="CONCAT(SUBSTR("+colNmae+",3,2),'.',SUBSTR("+colNmae+",-4,2))";
			        			}else{
			        				string ="(SUBSTR("+colNmae+",3,2))||'.'||(SUBSTR("+colNmae+",-4,2))";
			        			}
			        		    
			        		}else if(string.contains("@yymmdd")){
			        			string = string.substring(0, string.indexOf("@"));;
			        		}else if("A36.A3607".equals(string)){
			        			if (cueDBType == DBType.MYSQL) {
			        				string="CONCAT(SUBSTR("+colNmae+",1,4),'.',SUBSTR("+colNmae+",-4,2))";
			        			}else{
			        				string="(SUBSTR("+colNmae+",1,4))||'.'||(SUBSTR("+colNmae+",-4,2))";
			        			}
			        			
			        		}else{
			        			string=string+" "+string.replace(".", "_");
			        		}
			        		
			        	}
		        	}else{
		        		if(colNmae.contains("yy")){
			        		colNmae=colNmae.substring(0, colNmae.indexOf("@"));
			        	}
			        	 String	sql="SELECT CODE_TYPE FROM CODE_TABLE_COL WHERE TABLE_CODE='"+tableNmae+"' AND COL_CODE='"+colNmae+"'";
			        	
			        	//System.out.println("sql================================"+sql);
			        	String codetable = (String) sess.createSQLQuery(sql).uniqueResult();
			        	if("A36.A3602C".equals(string)){//家庭成员年龄
		        			string="GET_BIRTHDAY(A36.A3607,'"+DateUtil.getcurdate()+"')";
		        		}else if(string.contains("@yyyy.mm")){//格式化家庭成员出生年月
		        			if (cueDBType == DBType.MYSQL) {
		        				string="CONCAT(SUBSTR("+colNmae+",1,4),'.',SUBSTR("+colNmae+",-4,2))";
		        			}else{
		        				string="(SUBSTR("+colNmae+",1,4))||'.'||(SUBSTR("+colNmae+",-4,2))";
		        			}
		        			
		        		}else if(string.contains("@yy-mm-dd")){
		        			if (cueDBType == DBType.MYSQL) {
		        				string="CONCAT(SUBSTR("+colNmae+",1,4),'-',SUBSTR("+colNmae+",-4,2),'-',SUBSTR("+colNmae+",-2,2))";
		        			}else{
		        				string=" (SUBSTR("+colNmae+",1,4))||'-'||(SUBSTR("+colNmae+",-4,2)||'-'||(SUBSTR("+colNmae+",-2,2)))";
		        			}
		        			
		        		
		        		}else if(string.contains("@yy.mm")){
		        			if (cueDBType == DBType.MYSQL) {
		        				string ="CONCAT(SUBSTR("+colNmae+",3,2),'.',SUBSTR("+colNmae+",-4,2))";
		        			}else{
		        				string ="(SUBSTR("+colNmae+",3,2))||'.'||(SUBSTR("+colNmae+",-4,2))";
		        			}
		        		    
		        		}else if(string.contains("@yymmdd")){
		        			string = string.substring(0, string.indexOf("@"));;
		        		}else if("A36.A3607".equals(string)){
		        			if (cueDBType == DBType.MYSQL) {
		        				string="CONCAT(SUBSTR("+colNmae+",1,4),'.',SUBSTR("+colNmae+",-4,2))";
		        			}else{
		        				string="(SUBSTR("+colNmae+",1,4))||'.'||(SUBSTR("+colNmae+",-4,2))";
		        			}
		        			
		        		}else{
		        			string=string+" "+string.replace(".", "_");
		        		}
		        	}
		        	
		        	
		        	newQueryValue+=string+",";
		        	
				}
		        queryValue=newQueryValue.substring(0,newQueryValue.length()-1);
	            
	            Integer max=Integer.parseInt(valueArr[valueArr.length-1]);
	           // Integer max1 = maxz;
	            String sql="";
	            	if(key.equals("A36")&&max<0){
	            	 sql = "select "+queryValue
								+ " from  "+key+" "+key+"  where a0000 = '"+a0000+"' order by SORTID desc";
	            }else if(key.equals("CAPPOINTC")){
	            	 sql = "select "+queryValue
							+ " from  "+key+" "+key+"  where a0000 = '"+a0000+"'  "+queryCondition ;
	            	// System.out.println("sssssssssssssssssssssssssss2"+sql);
	            }else{
	            	sql = "select "+queryValue
							+ " from  "+key+" "+key+"  where a0000 = '"+a0000+"' ";
	            }
				
						Object[] obj1=new Object[s3.length];
						List<Object[]> list2 = session.createSQLQuery(sql).list();
						//System.out.println("list2====================="+list2.size());
						int size1=list2.size();
						int size = list2.size();
						Object[] obj=new Object[s3.length];
						for (int i = 0; i < size1-size; i++) {
							list2.add(obj);
						}
						
						
						int finalsize=list2.size();
						if(max<0){
							max=Math.abs(max);
							if(finalsize<max){
								//用空字符串补齐
								int count = max-finalsize;
								for(int i=0;i<count;i++){
									list2.add(obj);
								}
							}
						}
						int cy2=-1;//倒序区分几条信息
						Integer max1=Integer.parseInt(valueArr[valueArr.length-1]);
						if(max1<0){
							for (int j =0; j > max1; j++) {
								//Object obj2 = list2.get(j);
								
								Object object = list2.get(j);
								
								Object[] objects = null;
	                			if(!(object instanceof Object[])){
	                				objects = new Object[1];
	                				objects[0]=object;
	                			}else{
	                				Object[] objectaa = list2.get(j);
	                				objects=objectaa;
	                			}

								
								
								 String[] valueArr2 = mapqueryValue.split(",");
								 String fiels="";
								 for (String str : valueArr2) {
									 fiels+=str+"#"+cy2+",";
								}
								 fiels=fiels.substring(0,fiels.length()-1);
								 String[] fielsArr = fiels.split(",");
								for(int i=0;i<fielsArr.length;i++){
									tmp.put(fielsArr[i], objects==null||objects[i]==null?"":objects[i]);
								}
								if(cy2==max1){
									 break ;
								 }
								 cy2--;
							}
						}
						
						list.add(tmp);
				}
		}
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	/**
	 * 正序正值处理方式
	 * */
	public List<Map<String, Object>> getListBySqlz(String sql1,String a0000,Boolean isHasPic,List list1,String queryCondition,String rad) {
		DBType cueDBType = DBUtil.getDBType();
		if(queryCondition==null){
			queryCondition="" ;
		}
		HBSession sess = HBUtil.getHBSession();
		List<Map<String, Object>> list = null;
		try {
			HBSession session = HBUtil.getHBSession();
			CommonQueryBS query = new CommonQueryBS();
			query.setConnection(session.connection());
			query.setQuerySQL(sql1);
			TreeSet<String> table=new TreeSet<String>();
			String field="";
			for( int i=0;i<list1.size();i++){//取所有表名
				String str = (String) list1.get(i);
				String[] split = str.split("_");
				field+=split[0]+"."+split[1]+" "+split[0]+"_"+split[1]+",";
				table.add(split[0]);
			}
			
			//去掉所有A01字段
		List<String> list1Tmp = new ArrayList<String>();
			List<String> s2Tmp = new ArrayList<String>();
			for( int i=0;i<list1.size();i++){
				String str = (String) list1.get(i);

				if(!str.split("_")[0].equals("A01")){


					list1Tmp.add(str);
				}
			}
			
			String[] s2 = new String[table.size()];
	        
	        for (int i = 0; i < s2.length; i++) {  
	            s2[i] = table.pollFirst();
	       
	        }  
	        
	        for (int i = 0; i < s2.length; i++) {  
	            if(!s2[i].equals("A01")){
	            	s2Tmp.add(s2[i]);
	            }
	       
	        }  
	        
	        
	        Map<String, String> map=new HashMap<String, String>();
	        for (String stt : s2Tmp) {
	        	String aa="";
	        	String bb="";
				for(int i=0;i<list1Tmp.size();i++){
					String fi= list1Tmp.get(i);//[A36_A3604A#1, A36_A3601#1, A36_A3604A#2, A36_A3601#2]
				    if(fi.contains(stt)){
						String[] split = fi.split("#");//[A36_A3604A, 1]
						
							bb+=split[1]+",";//1,
							fi=split[0];//A36_A3604A				
							aa+=fi+",";//A36_A3604A,
						
						
					}
				}
				bb=bb.substring(0,bb.length()-1);//1,1,2,2,
				String[] bb2 = bb.split(",");
				String zheng ="";
				String fu ="";
				for (String string : bb2) {
					if(string.contains("-")){
						fu+=string+",";
					}else{
						zheng+=string+",";
					}
				}
				if(zheng==""||zheng==null)
					return list;
				zheng=zheng.substring(0,zheng.length()-1);
				Integer maxz=0;//3
				String[] bbArr1 = zheng.split(",");
				for(int i=0;i<bbArr1.length;i++){
					if(maxz<=Integer.parseInt(bbArr1[i])){
						maxz=Integer.parseInt(bbArr1[i]);
					}
				}
				if(maxz>0){
					
					aa=aa.substring(0,aa.length()-1);
					aa=aa+","+maxz;
					map.put(stt, aa);
				}
				
				
			}
	        
	        
	       // map.remove("A01");
	        
	        
	        Vector<?> vector = query.query();
			Iterator<?> iterator = vector.iterator();
			
	if (iterator.hasNext()) {
		while (iterator.hasNext()) {
			Map<String, Object> tmp = (Map<String, Object>) iterator.next();
			if (!"".equals(tmp.get("a0000"))
					&& tmp.get("a0000") != null) {
				tmp.put("image", getImageStr((String) tmp.get("a0000")));
			}
			list = new ArrayList<Map<String, Object>>();
	        for(Map.Entry<String, String> e : map.entrySet()){
	        	String key=e.getKey();
	            String value=e.getValue();//A36_A3604A,A36_A3601,A36_A3604A,A36_A3601,A36_A3604A,A36_A3601,A36_A3604A,A36_A3601,A36_A3604A,A36_A3601,A36_A3604A,A36_A3601,-5
	            String[] valueArr = value.split(",");
	            value=value.replace(","+valueArr[valueArr.length-1], "");
	            TreeSet<String> valueSet = new TreeSet<String>();
	            String[] valueArrhh = value.split(",");
	            for (String string : valueArrhh) {
	            	valueSet.add(string);
				}
	            
	            String[] s3 = new String[valueSet.size()];  
		        
		        for (int i = 0; i < s3.length; i++) {  
		            s3[i] = valueSet.pollFirst();
		       
		        } 
		        String queryValue="";
		        for (String string : s3) {
		        	queryValue+=string+",";
				}
		        String mapqueryValue=queryValue.substring(0, queryValue.length()-1);
		        queryValue=queryValue.substring(0, queryValue.length()-1).replace("_", ".");
		        String[] split = queryValue.split(",");
		        String  newQueryValue="";
		        for (String string : split) {
		        	String tableNmae=string.split("\\.")[0];
		        	String colNmae=string.split("\\.")[1];
		        	if(rad.equals("yes")||rad.equals("undefined")){
		        		if(colNmae.contains("yy")){
			        		colNmae=colNmae.substring(0, colNmae.indexOf("@"));
			        	}
			        	 String	sql="SELECT CODE_TYPE FROM CODE_TABLE_COL WHERE TABLE_CODE='"+tableNmae+"' AND COL_CODE='"+colNmae+"'";
			        	
			        	//System.out.println("sql================================"+sql);
			        	String codetable = (String) sess.createSQLQuery(sql).uniqueResult();
			        	if(!StringUtil.isEmpty(codetable)){
			        		/*if("ZB01".equals(codetable.toUpperCase())){
								String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId().trim();//当前用户ID
								sql=" select CODEVALUE from CODELEVEL where USERID='"+cueUserid+"'";
								List<String> list2 = HBUtil.getHBSession().createSQLQuery(sql).list();
								if(list2!=null&&list2.size()>0){
									String value2 = list2.get(0);
									if("3".equals(value2)){
										string="(select code_name2 from code_value where code_type='"+codetable+"' and code_value.code_value="+string+") "+string.replace(".", "_");
									}else{
										string="(select code_name3 from code_value where code_type='"+codetable+"' and code_value.code_value="+string+") "+string.replace(".", "_");
									}
								}else{
									string="(select code_name3 from code_value where code_type='"+codetable+"' and code_value.code_value="+string+") "+string.replace(".", "_");
								}
							}else{*/
								string="(select code_name from code_value where code_type='"+codetable+"' and code_value.code_value="+string+") "+string.replace(".", "_");
							//}
			        		//System.out.println("string========================"+string);
			        	}else{
			        		if("A36.A3602C".equals(string)){//家庭成员年龄
			        			string="GET_BIRTHDAY(A36.A3607,'"+DateUtil.getcurdate()+"')";
			        		}else if(string.contains("@yyyy.mm")){//格式化家庭成员出生年月
			        			if (cueDBType == DBType.MYSQL) {
			        				//CONCAT(substr(A0107, 1, 4)  ,'.' ,substr(A0107 ,- 4, 2))
			        				string="CONCAT(SUBSTR("+colNmae+",1,4),'.',SUBSTRING("+colNmae+",-4,2))";
			        			}else{
			        				string="(SUBSTR( "+colNmae+",1,4))||'.'||(SUBSTR( "+colNmae+",-4,2))";
			        			}
			        		}else if(string.contains("@yy-mm-dd")){
			        			if (cueDBType == DBType.MYSQL) {
			        				string="CONCAT(SUBSTR("+colNmae+",1,4),'-',SUBSTR("+colNmae+",-4,2),'-',SUBSTR("+colNmae+",-2,2))";
			        			}else{
			        				string="(SUBSTR("+colNmae+",1,4))||'-'||(SUBSTR( "+colNmae+",-4,2)||'-'||(SUBSTR("+colNmae+",-2,2)))";
			        			}
			        		
			        		}else if(string.contains("@yy.mm")){
			        			if (cueDBType == DBType.MYSQL) {
			        				string ="CONCAT(SUBSTR("+colNmae+",3,2),'.',SUBSTR("+colNmae+",-4,2))";
			        			}else{
			        				string ="(SUBSTR("+colNmae+",3,2))||'.'||(SUBSTR("+colNmae+",-4,2))";
			        			}
			        		    
			        		}else if(string.contains("@yymmdd")){
			        			string = string.substring(0, string.indexOf("@"));;
			        		}else if("A36.A3607".equals(string)){
			        			if (cueDBType == DBType.MYSQL) {
			        				string="CONCAT(SUBSTR("+colNmae+",1,4),'.',SUBSTR("+colNmae+",-4,2))";
			        			}else{
			        				string="(SUBSTR("+colNmae+",1,4))||'.'||(SUBSTR("+colNmae+",-4,2))";
			        			}
			        			
			        		}else{
			        			string=string+" "+string.replace(".", "_");
			        		}
			        		
			        	}
		        	}else{
		        		if(colNmae.contains("yy")){
			        		colNmae=colNmae.substring(0, colNmae.indexOf("@"));
			        	}
			        	 String	sql="SELECT CODE_TYPE FROM CODE_TABLE_COL WHERE TABLE_CODE='"+tableNmae+"' AND COL_CODE='"+colNmae+"'";
			        	
			        	//System.out.println("sql================================"+sql);
			        	String codetable = (String) sess.createSQLQuery(sql).uniqueResult();
			        	if("A36.A3602C".equals(string)){//家庭成员年龄
		        			string="GET_BIRTHDAY(A36.A3607,'"+DateUtil.getcurdate()+"')";
		        		}else if(string.contains("@yyyy.mm")){//格式化家庭成员出生年月
		        			if (cueDBType == DBType.MYSQL) {
		        				string="CONCAT(SUBSTR("+colNmae+",1,4),'.',SUBSTR("+colNmae+",-4,2))";
		        			}else{
		        				string="(SUBSTR("+colNmae+",1,4))||'.'||(SUBSTR("+colNmae+",-4,2))";
		        			}
		        			
		        		}else if(string.contains("@yy-mm-dd")){
		        			if (cueDBType == DBType.MYSQL) {
		        				string="CONCAT(SUBSTR("+colNmae+",1,4),'-',SUBSTR("+colNmae+",-4,2),'-',SUBSTR("+colNmae+",-2,2))";
		        			}else{
		        				string=" (SUBSTR("+colNmae+",1,4))||'-'||(SUBSTR("+colNmae+",-4,2)||'-'||(SUBSTR("+colNmae+",-2,2)))";
		        			}
		        			
		        		
		        		}else if(string.contains("@yy.mm")){
		        			if (cueDBType == DBType.MYSQL) {
		        				string ="CONCAT(SUBSTR("+colNmae+",3,2),'.',SUBSTR("+colNmae+",-4,2))";
		        			}else{
		        				string ="(SUBSTR("+colNmae+",3,2))||'.'||(SUBSTR("+colNmae+",-4,2))";
		        			}
		        		    
		        		}else if(string.contains("@yymmdd")){
		        			string = string.substring(0, string.indexOf("@"));;
		        		}else if("A36.A3607".equals(string)){
		        			if (cueDBType == DBType.MYSQL) {
		        				string="CONCAT(SUBSTR("+colNmae+",1,4),'.',SUBSTR("+colNmae+",-4,2))";
		        			}else{
		        				string="(SUBSTR("+colNmae+",1,4))||'.'||(SUBSTR("+colNmae+",-4,2))";
		        			}
		        			
		        		}else{
		        			string=string+" "+string.replace(".", "_");
		        		}
		        	}
		        	
		        	newQueryValue+=string+",";
		        	
				}
		        queryValue=newQueryValue.substring(0,newQueryValue.length()-1).replace("A17Q.A1701", "to_char(A17Q.A1701)");
	            
	            Integer max=Integer.parseInt(valueArr[valueArr.length-1]);
	           // Integer max1 = maxz;
	            String sql="";
	            if(key.equals("A36")&&max>0){
	            	 sql = "select "+queryValue
							+ " from  "+key+" "+key+"  where a0000 = '"+a0000+"' order by SORTID";
	            	// System.out.println("sssssssssssssssssssssssssss1"+sql);
	            }
	            else if(key.equals("CAPPOINTC")){
	            	 sql = "select "+queryValue
							+ " from  "+key+" "+key+"  where a0000 = '"+a0000+"' "+queryCondition;
	            	// System.out.println("sssssssssssssssssssssssssss2"+sql);
	            }else{
	            	sql = "select "+queryValue
							+ " from  "+key+" "+key+"  where a0000 = '"+a0000+"' ";
	            }
				
	           
				
						//int cont=0;
						//ArrayList<Integer> arrayList = new ArrayList<Integer>();
						Object[] obj1=new Object[s3.length];
						List<Object[]> list2 = session.createSQLQuery(sql).list();
						//System.out.println("list2====================="+list2.size());
						int size1=list2.size();
						int size = list2.size();
						Object[] obj=new Object[s3.length];
						for (int i = 0; i < size1-size; i++) {
							list2.add(obj);
						}
						
						
						int finalsize=list2.size();
							if(finalsize<max){
								//用空字符串补齐
								int count = max-finalsize;
								for(int i=0;i<count;i++){
									list2.add(obj);
								}
							}
						int cy = 1;//正序区分几条信息
						//int cy2=-1;//倒序区分几条信息
						Integer max1=Integer.parseInt(valueArr[valueArr.length-1]);
							for(int j=0;j<max1;j++){
								Object object = list2.get(j);
								
								Object[] objects = null;
	                			if(!(object instanceof Object[])){
	                				objects = new Object[1];
	                				objects[0]=object;
	                			}else{
	                				Object[] objectaa = list2.get(j);
	                				objects=objectaa;
	                			}

								
								
								
								 String[] valueArr2 = mapqueryValue.split(",");
								 String fiels="";
								 for (String str : valueArr2) {
									 fiels+=str+"#"+cy+",";
								}
								 fiels=fiels.substring(0,fiels.length()-1);
								 String[] fielsArr = fiels.split(",");
								for(int i=0;i<fielsArr.length;i++){
									tmp.put(fielsArr[i], objects==null||objects[i]==null?"":objects[i]);
								}
								 cy++;
							}
						}
						
						list.add(tmp);
				}
		}
//	        }
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	
	
	
	public List<Map<String, Object>> getListBySqlA01(String sql1,String a0000,Boolean isHasPic) {
		List<Map<String, Object>> list = null;
		try {
			HBSession session = HBUtil.getHBSession();
			CommonQueryBS query = new CommonQueryBS();
			query.setConnection(session.connection());
			query.setQuerySQL(sql1);
			Vector<?> vector = query.query();
			Iterator<?> iterator = vector.iterator();
			/*String sql = "select a3604a a3604,"
					     + "a3601,"
					     + "GET_BIRTHDAY(a36.a3607,'"+DateUtil.getcurdate()+"') "
					     + "a3607,"
					     + "a3627,"
					     + "a3611,a3604a from A36 a36 where a0000 = '"+a0000+"' order by SORTID";*/
			if (iterator.hasNext()) {
				list = new ArrayList<Map<String, Object>>();
				while (iterator.hasNext()) {

					Map<String, Object> tmp = (Map<String, Object>) iterator
							.next();
					// 如果需要照片数据且人员id不为空则获取照片数据
					if (!"".equals(tmp.get("a0000"))
							&& tmp.get("a0000") != null) {
						tmp.put("image", getImageStr((String) tmp.get("a0000")));
					}
					list.add(tmp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return list;
	}
}
