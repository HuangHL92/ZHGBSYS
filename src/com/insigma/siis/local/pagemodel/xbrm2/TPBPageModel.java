package com.insigma.siis.local.pagemodel.xbrm2;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.safe.SysSafeException;
import com.insigma.odin.framework.safe.util.RsaUtil;
import com.insigma.odin.framework.util.IDUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.demo.TestAspose2Pdf;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.Js2Yntp;
import com.insigma.siis.local.business.entity.TpbAtt;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.utils.SQLiteUtil;
import com.insigma.siis.local.pagemodel.cadremgn.comm.GenericCodeItem;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;
import com.utils.CommonQueryBS;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/***
 *  实现干部调配酝酿表 2019-08-20
 * @author zouzhilin
 *
 */
public class TPBPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");

		// 设置下拉框

		return EventRtnType.NORMAL_SUCCESS;
	}

	/***
	 *  页面初始化 2019-08-20
	 *  参数:ynId：批次ID
	 *         yntype：批次类型，有【TPHJ1：干部一览表，TPHJ2：省委常委会干部情况一览表，TPHJ3：省委书记专题会议干部情况一览表】
	 *         IsGDCL：是否归档材料   2019-09-16 增加
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("initX")
	public int initX() throws RadowException, AppException {
		
		// openWindow
		String id = this.getPageElement("subWinIdBussessId").getValue();	/***从打开窗体中subWinIdBussessId获取参数****/
		
		String ynId = this.getPageElement("ynId").getValue();						/***从界面获取酝酿批次ynId****/
		String yn_type = this.getPageElement("yntype").getValue();				/***从界面获取酝酿类型yntype****/
		String IsGDCL = this.getPageElement("IsGDCL").getValue(); 				/***从界面是否归档材料IsGDCL****/
		if (ynId==null || yn_type==null || "".equals(ynId) ||
				"".equals(ynId)) {																		/***判断是否Odin是否传入****/
			String[] idtype=id.split(",");															/***从打开窗体中subWinIdBussessId中ID获取参数****/
			if (idtype.length<2) {
				return EventRtnType.NORMAL_SUCCESS;
			}
			ynId=idtype[0];																				/***第一个参数：酝酿批次ynId****/
			yn_type =idtype[1]; 																		/***第二个参数：酝酿类型yntype****/
		} 
		Js2Yntp rb = (Js2Yntp) HBUtil.getHBSession().get(Js2Yntp.class, ynId); /***根据酝酿批次ynId从读取ER对象****/
		String tb_unit_id = "";																		/***单位编码ID****/
		String cal_age_year = "";																	/***计算年龄年度****/
		if (rb != null) {																					
			tb_unit_id = rb.getTbUnit();
			if (tb_unit_id==null) {
				tb_unit_id = "";
			}
			cal_age_year = rb.getCalAgeYear();											/***从数据库读取酝酿批次的计算年度****/
			if (cal_age_year == null) {
				cal_age_year = "";
			}
		}
		/***
		//showModelDialog begin ==========
		String ynId = this.getPageElement("ynId").getValue();
		String yn_type = this.getPageElement("yntype").getValue();
		Js2Yntp rb = (Js2Yntp) HBUtil.getHBSession().get(Js2Yntp.class, ynId);
		if (rb == null) {
			this.setMainMessage("无批次信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}		
		//====================================== ***/ 
		this.getPageElement("rmburl").setValue("");														/***给页面元素设置任免表的URL****/
		this.getPageElement("ynId").setValue(ynId);														/***给页面元素酝酿的批次号****/
		this.getExecuteSG().addExecuteCode("$('#yn_type').val('"+yn_type+"')");			/***JQuery给页面元素酝酿的表格类别****/
		this.getExecuteSG().addExecuteCode("$('#tb_unit').val('"+tb_unit_id+"')");			/***JQuery给页面元素酝酿的单位****/
		this.getExecuteSG().addExecuteCode("fillCalAgeYear('"+cal_age_year+"')");		/***JQuery调用页面元素fillCalAgeYear****/
		
		//this.getPageElement("yn_type").setValue(yn_type);
		this.getPageElement("yntype").setValue(yn_type);												/***给页面元素设置表格类别****/
		
//		Js2Yntp rb = (Js2Yntp) HBUtil.getHBSession().get(Js2Yntp.class, ynId);
		if (rb == null) {
			this.setMainMessage("无批次信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//=================begin 从数据库读取批次的HTML页面缓存===================
		//1、缓存主要目的是解决操作用户修改了一览表的样式如：文字字号、字体大小、颜色、对齐方式等===
		//2、减少计算，优化速度
		//3、注意：前端传过来的表格HTML是经过Base64进行加密的字符串
		//              并且返回到前端HTML也是Base64加密的字符串
		//              加密、解密都是由base64.js来实现处理的
		String htmlContent = "";
		String TPHJ1Html = "select htmlcontent from TPHJ1Html where yn_id='"+ynId+"' and yn_type='"+yn_type+"'";
																							/***
																							  * YN_ID						酝酿ID
																							  *	YN_TYPE					酝酿类型
																							  *	HTMLCONTENT 		酝酿存储的内容
																							  */
		if ("1".equals(IsGDCL)) {
			TPHJ1Html = "select htmlcontent from TPHJ1Html_GD where yn_id='"+ynId+"' and yn_type='"+yn_type+"'";
		}
		CommQuery htmlbs = new CommQuery();
		List<HashMap<String, Object>> listhtml = htmlbs.getListBySQL(TPHJ1Html);
		if (listhtml.size()>0) {
			HashMap<String, Object> map = listhtml.get(0);					/**返回结果最多也只有一条，取第一条记录****/
			htmlContent = (String)map.get("htmlcontent");						/**读出htmlContent的内容****/
			if (htmlContent == null || "".equals(htmlContent)) {
				htmlContent = ""; 
			}
		}
		this.getPageElement("coordTableHtmlContent").setValue(htmlContent);
		//=============================end=================================
		
//		String sql = "select tp0100, a0000, type, tp0101, tp0102, tp0103, tp0104,"
//				+ " tp0105, tp0106, tp0107,tp0108,tp0109,tp0110, tp0111,tp0112,tp0113,tp0114,tp0115,nvl2((select jsa00 from tpb_att where js0100=TPHJ1.a0000 and rb_id=TPHJ1.yn_id),'kcclclass','default_color') kcclclass from TPHJ1 where yn_id='"+ynId+"' and tp0116='"+yn_type+"' order by sortnum asc ";

		//==========================Begin 读取调配环节表存储的信息===================
		String sql = "select tp0100, a0000, type, tp0101,tp0117,tp0106,tp0107,tp0118,tp0119,tp0120,tp0102,tp0121,tp0122,tp0123,tp0103,tp0104,tp0124,tp0125,tp0126,tp0127,tp0128,nvl2((select jsa00 from tpb_att where rownum<2 and js0100=TPHJ1.a0000 and rb_id=TPHJ1.yn_id and rownum<2),'kcclclass','default_color') kcclclass from TPHJ1 where yn_id='"
				+ ynId + "' and tp0116='" + yn_type + "' order by sortnum asc "; /*** 
																													 *	TP0100				id
																													 *	A0000				人员id
																													 *	TYPE			        1一级标题2二级标题3正文
																													 *	TP0101  姓名
																													 *	TP0117  照片       
																													 *	TP0106  现任职务
																													 *	TP0107  拟任免职务
																													 *	TP0118  性别
																													 *	TP0119  民族
																													 *	TP0120  籍贯
																													 *	TP0102  出生年月 
																													 *	TP0121  年龄
																													 *	TP0122  入党时间
																													 *	TP0124  学历
																													 *	TP0123  毕业院校及专业
																													 *	TP0103 	任现职时间
																													 *	P0104 	同职级时间 	        	
																													 *	TP0128  备注
																													 *	TP0125  谈话调研推荐
																													 *	TP0126  会议推荐
																													 *	TP0127  重点考察
																													 *	TP0128  备注					
				 																									  **/
		String js2_yntp_info = "select info0y, info0m, info0d from js2_yntp_info where yn_id='" + ynId
				+ "' and info01='" + yn_type + "'";

		if ("1".equals(IsGDCL)) {
			sql = "select tp0100, a0000, type, tp0101,tp0117,tp0106,tp0107,tp0118,tp0119,tp0120,tp0102,tp0121,tp0122,tp0123,tp0103,tp0104,tp0124,tp0125,tp0126,tp0127,tp0128,nvl2((select jsa00 from tpb_att where  rownum<2 and js0100=TPHJ1_GD.a0000 and rb_id=TPHJ1_GD.yn_id),'kcclclass','default_color') kcclclass from TPHJ1_GD where yn_id='"
					+ ynId + "' and tp0116='" + yn_type + "' order by sortnum asc "; /*** 
																														 *	TP0100				id
																														 *	A0000				人员id
																														 *	TYPE			        1一级标题2二级标题3正文
																														 *	TP0101  姓名
																														 *	TP0117  照片       
																														 *	TP0106  现任职务
																														 *	TP0107  拟任免职务
																														 *	TP0118  性别
																														 *	TP0119  民族
																														 *	TP0120  籍贯
																														 *	TP0102  出生年月 
																														 *	TP0121  年龄
																														 *	TP0122  入党时间
																														 *	TP0124  学历
																														 *	TP0123  毕业院校及专业
																														 *	TP0103 	任现职时间
																														 *	P0104 	同职级时间 	        	
																														 *	TP0128  备注
																														 *	TP0125  谈话调研推荐
																														 *	TP0126  会议推荐
																														 *	TP0127  重点考察
																														 *	TP0128  备注					
																															  **/
		}
		try {
			CommQuery cqbs = new CommQuery();																/***创建查询对象****/
			List<HashMap<String, Object>> listCode = cqbs.getListBySQL(sql.toString());		/***SQL查询，并且返回List<HashMap<String, Object>>对象****/
			JSONArray updateunDataStoreObject = JSONArray.fromObject(listCode);				/***Java.util.List对象转化为JSON数组****/
			// System.out.println(updateunDataStoreObject.toString());
			this.getExecuteSG().addExecuteCode("TIME_INIT.changeTableType('" + yn_type + "');");/***设置到表格类型****/
			this.getExecuteSG().addExecuteCode("doAddPerson ." +
                    "addPerson(" + updateunDataStoreObject.toString() + ");");							/***调用前端的doAddPerson.addPerson填充到页面****/
			
			String groupName = getDeptName();																	/***读取处室名称****/
			if (groupName != null && !"".equals(groupName)) {												/***填充****/
				this.getExecuteSG().addExecuteCode("if ($('#tb_unit').val() =='') { $('#tb_unit').val('"+groupName+"')};");
			}
			// 会议时间
			listCode = cqbs.getListBySQL(js2_yntp_info);
			if (listCode.size() > 0) {
				Map<String, Object> map = listCode.get(0);
				String y = map.get("info0y") == null ? "" : map.get("info0y").toString();
				String m = map.get("info0m") == null ? "" : map.get("info0m").toString();
				String d = map.get("info0d") == null ? "" : map.get("info0d").toString();
				this.getExecuteSG()
						.addExecuteCode("TIME_INIT.setTime('" + y + "','" + m + "','" + d + "','" + yn_type + "');");
			}
			//=============================end=================================
			
			
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}

		return EventRtnType.NORMAL_SUCCESS;
	}

	/***
	 * 得到机构名称
	 * @return
	 */
	private String getDeptName() { 
		String userId = SysManagerUtils.getUserId();
		String deptName = "";
		String sql = "select usergroupname from smt_usergroup where id in (select dept from smt_user where userid = '"+userId+"')";
					/***
					 * smt_user					    表【系统用户表】
					 * smt_usergroup             表【用户组表】
					 * ID									唯一主键
					 * USERGROUPNAME		用户组名称
					* SID									上级用户组ID
					* SORTID							排序字段
					 */
		try {
			
			CommQuery cqbs = new CommQuery();																/***创建查询对象****/
			List<HashMap<String, Object>> listCode = cqbs.getListBySQL(sql.toString());		/***SQL查询，并且返回List<HashMap<String, Object>>对象****/
			 
			if (listCode.size()>0) {																							/***判断是否为空****/
				deptName = (String)((Map)listCode.get(0)).get("deptName");							/***读取机构名，如果NULL，则显示空字符串****/
				if (deptName == null) {
					deptName = "";
				}
			}
		} catch (Exception e) {
			// this.setMainMessage("查询失败！");
			e.printStackTrace();  
		}
		return deptName;  																									/***函数返回值：如果NULL，则返回空字符串****/
	}
	
	/***
	 * 根据人员编码判断调配环节表TPHJ1是否存在该人员
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("hasSaved")
	public int hasSaved() throws RadowException, AppException {
		String tp0100 = this.request.getParameter("tp0100");								/***FORM表单获取人员编码****/
		String sql = "select 1 from TPHJ1 where tp0100='" + tp0100 + "'";			/***构建SQL查询语句****/

		try {
			List list = HBUtil.getHBSession().createSQLQuery(sql).list();					/***查询返回List对象****/
			if (list.size() > 0) {																					/***判断List是否为空****/
				return EventRtnType.NORMAL_SUCCESS;											/**返回成功****/
			} else {
				return EventRtnType.FAILD;
			}
		} catch (Exception e) {
			this.setMainMessage("查询失败！");														/***错误处理****/
			e.printStackTrace();
			return EventRtnType.FAILD;																	/**返回错误****/
		}

	}

	/****
	 * 判断A01_TPHJ 是否存在该人员
	 * A01_TPHJ：是一个包含正式库和中间库的人员视图
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("hasPerson")
	public int hasPerson() throws RadowException, AppException {
		String tp0100 = this.request.getParameter("a0000");											/***FORM表单获取人员编码****/
		String sql = "select 1 from A01_TPHJ where a0000='" + tp0100 + "'";				/***构建查询SQL****/

		try {
			List list = HBUtil.getHBSession().createSQLQuery(sql).list();							/***查询返回List对象****/
			if (list.size() > 0) {																							/***判断List是否为空****/
				return EventRtnType.NORMAL_SUCCESS;													/**返回成功****/
			} else {
				return EventRtnType.FAILD;																		/**返回错误****/
			}
		} catch (Exception e) {
			// this.setMainMessage("查询失败！");
			e.printStackTrace();
			return EventRtnType.FAILD;																			/**返回错误****/
		}

	}

	/***
	 * 得到任免表登录的URL
	 * 返回结果：1、正式库的任免表URL
	 * 					2、中间库的任免表URL
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("getRMBLoginURL")
	public int getRMBLoginURL() throws RadowException {
		String a0000 = this.request.getParameter("a0000");													/***FORM表单获取人员编码****/
		String sql = "select remoteserver from A01_TPHJ where a0000='" + a0000 + "'";		/***构建查询SQL****/

		String RemoteServer = "";																							/***远程服务的连接****/
		try {
			
			CommQuery cqbs = new CommQuery();																/***创建查询对象****/
			List<HashMap<String, Object>> listCode = cqbs.getListBySQL(sql.toString());		/***查询返回List对象****/
			 
			if (listCode.size()>0) {																							/***判断是否为空****/
				RemoteServer = (String)((Map)listCode.get(0)).get("remoteserver");					/***返回该人员远程服务器的URL****/
			} else {
				return EventRtnType.NORMAL_SUCCESS;															/**Event返回成功****/
			}
		} catch (Exception e) {
			// this.setMainMessage("查询失败！");
			e.printStackTrace(); 
			return EventRtnType.FAILD;																					/**Event返回错误****/
		}
		
		
		//===================Begin  构建单点登录方式====================================
		String userId = "system";
		JSONObject json = new JSONObject(); 
		json.put("loginname", userId);
		json.put("rand", IDUtil.generateUUID());
		String vid = "";
		try {
			vid = RsaUtil.encryption(json.toString());
		} catch (SysSafeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		this.setSelfResponseFunc(RemoteServer+"/rmb/ZHGBrmb.jsp?FromModules=1&a0000="+a0000);
		//===========================End=======================================
		 return EventRtnType.NORMAL_SUCCESS;
	}
	//http://172.16.30.157:8080/hzb/pages/publicServantManage/ImportLrm.jsp?businessClass=com.picCut.servlet.SaveLrmFile
	
	
	/*****
	 * 读取远程服务器URL函数
	 * @return
	 * @throws RadowException
	 */
	public static String getRemoteServer() throws RadowException { 
		String sql = "select RemoteServer() RemoteServer from dual ";					/***构建查询SQL****/

		String RemoteServer = "";																			/***声明变量RemoteServer****/
		try {
			
			CommQuery cqbs = new CommQuery();												/***创建查询对象****/
			List<HashMap<String, Object>> listCode = cqbs.getListBySQL(sql.toString());/***查询返回List对象****/
			 
			if (listCode.size()>0) {
				RemoteServer = (String)((Map)listCode.get(0)).get("remoteserver"); /***读取值remoteserver****/
			}
		} catch (Exception e) {
			RemoteServer = ""; 
		}
		return RemoteServer;
	}
	
	/****
	 * 返回临时数据库【处级干部库】的URL
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("getTemplateDBURL")
	public int getTemplateDBURL() throws RadowException {  

		String RemoteServer = getRemoteServer();			//通过函数getRemoteServer获取URL
		 
		
		//===================Begin 外系统单点登录方式=================
		String userId = "system";
		JSONObject json = new JSONObject(); 
		json.put("loginname", userId);
		json.put("rand", IDUtil.generateUUID());
		String vid = "";
		try {
			vid = RsaUtil.encryption(json.toString());
		} catch (SysSafeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//==================END==============================
		
		//request.getSession().setAttribute("URL", RemoteServer+"/radowAction.do?method=doEvent&pageModel=pages.customquery.CustomQuery?vid="+vid);
	
		this.setSelfResponseFunc(RemoteServer+"/radowAction.do?method=doEvent&pageModel=pages.customquery.CustomQuery&IsTemplateDB=1&vid="+vid);
		//this.getPageElement("rmburl").setValue( RemoteServer+"rmb/ZHGBrmb.jsp?FromModules=1&a0000="+a0000+"&vid="+vid);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/*****
	 * 干部任免表的导入、批量导入单点登录模式
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("getPublicServantManage")
	public int getPublicServantManage() throws RadowException { 
		String sql = "select RemoteServer() RemoteServer from dual ";

		String RemoteServer = "";
		try {
			
			CommQuery cqbs = new CommQuery();
			List<HashMap<String, Object>> listCode = cqbs.getListBySQL(sql.toString());
			 
			if (listCode.size()>0) {
				RemoteServer = (String)((Map)listCode.get(0)).get("remoteserver");
			} else {
				return EventRtnType.NORMAL_SUCCESS;
			}
		} catch (Exception e) {
			// this.setMainMessage("查询失败！");
			e.printStackTrace(); 
			return EventRtnType.FAILD;
		}
		
		/////===================通过单点登录方式传入用户、加密的UUID整合页面==========
		String userId = "system";
		JSONObject json = new JSONObject(); 
		json.put("loginname", userId);
		json.put("rand", IDUtil.generateUUID());
		String vid = "";
		try {
			vid = RsaUtil.encryption(json.toString());
		} catch (SysSafeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//this.setSelfResponseFunc("aaaa");
		//this.setSelfResponseFunc(RemoteServer+"/radowAction.do?method=doEvent&pageModel=pages.xbrm2.Zhgbrmb&a0000="+a0000+"&vid="+vid+"");
		if ("1".equals(request.getParameter("IsBatch"))) {
			//批量导入页面
			this.setSelfResponseFunc(RemoteServer+"/pages/publicServantManage/ImportLrms2.jsp?businessClass=com.picCut.servlet.SaveLrmFile&vid="+vid);			
		}else {
			//导入页面
			this.setSelfResponseFunc(RemoteServer+"/pages/publicServantManage/ImportLrm2.jsp?businessClass=com.picCut.servlet.SaveLrmFile&vid="+vid);
		}
		
		//this.setSelfResponseFunc(RemoteServer+"busiAction.do?method=zhgbrmb&a0000="+a0000+"&vid="+vid+"");
		//this.getPageElement("rmburl").setValue( RemoteServer+"rmb/ZHGBrmb.jsp?FromModules=1&a0000="+a0000+"&vid="+vid);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 *  按IDs查询人员信息
	 *  A01_TPHJ：包含正式库和中间库【处级干部人员数据库】的信息
	 * @param listStr
	 * @return
	 * @throws Exception
	 */
	@PageEvent("queryByNameAndIDS")
	public int queryByNameAndIDS(String listStr) throws Exception {
		// System.out.println(listStr);
		HBSession sess = HBUtil.getHBSession();
		String ynId = this.getPageElement("ynId").getValue();
		String yn_type = this.getPageElement("yntype").getValue();
		StringBuffer sql = new StringBuffer();
		
		String ID_ROWINFO = this.getPageElement("ID_ROWINFO").getValue().replace("\n", "{/n}");
		JSONObject ID_ROWINFOObject = JSONObject.fromObject(ID_ROWINFO);
		Map<String, Map<String, String>> ID_ROWINFOMap = (Map<String, Map<String, String>>) ID_ROWINFOObject;
		
		//保存 TPHJ1Html
		String TPHJ1HtmlSql = "delete from TPHJ1Html where yn_id=?";
		PreparedStatement ps = sess.connection().prepareStatement(TPHJ1HtmlSql);
		ps.setString(1, ynId);
		ps.executeUpdate();
		sess.connection().commit();
		if (ps != null) ps.close(); 
		if (listStr != null && listStr.indexOf("TPHJ") >= 0) {// 选择了其它会议类型
			String[] params = listStr.split("@@");
			String ortherYnid = "";
			if (params.length == 2) {
				ortherYnid = params[0];
				listStr = params[1];
			} else {
				ortherYnid = ynId;
			}

//			sql.append("select sys_guid() tp0100, a0000, type, tp0101, tp0102, tp0103, tp0104,"
//					+ " tp0105, tp0106, tp0107,tp0108,tp0109,tp0110, tp0111,tp0112,tp0113,tp0114,tp0115,nvl2((select jsa00 from tpb_att where js0100=t.a0000 and rb_id=t.yn_id),'kcclclass','default_color') kcclclass from TPHJ1 t where yn_id='"+ortherYnid+"' and tp0116='"+listStr+"' ");
			if (listStr.contains("@@")) {
				sql.append(
					"select yn_id,sys_guid() tp0100, a0000, type, tp0101,tp0117,tp0106,tp0107,tp0118,tp0119,tp0120,tp0102,tp0121,tp0122,tp0123,tp0103,tp0104,tp0124,tp0125,tp0126,tp0127,tp0128,nvl2((select jsa00 from tpb_att where js0100=t.a0000 and rb_id=t.yn_id),'kcclclass','default_color') kcclclass from TPHJ1 t where yn_id='"
							+ ortherYnid + "' and tp0116='" + listStr + "' ");
			}else {
				listStr = listStr.replaceAll("\\[", "");
				listStr = listStr.replaceAll("\\]", "");
				listStr = ","+listStr+",";
				sql.append(
						"select yn_id,sys_guid() tp0100, a0000, type, tp0101,tp0117,tp0106,tp0107,tp0118,tp0119,tp0120,tp0102,tp0121,tp0122,tp0123,tp0103,tp0104,tp0124,tp0125,tp0126,tp0127,tp0128,nvl2((select jsa00 from tpb_att where js0100=t.a0000 and rb_id=t.yn_id),'kcclclass','default_color') kcclclass from TPHJ1 t where ");
				sql.append(" '"+listStr+"' like '%,' || yn_id || ',%' ");
				
			}
//			sql.append(" and not exists (select 1 from TPHJ1 p where p.a0000=t.a0000");
//			sql.append(" and yn_id='" + ynId + "' and tp0116='" + yn_type + "')  order by sortnum asc ");
			sql.append(" order by sortnum asc ");
			try {
				CommQuery cqbs = new CommQuery();
				List<HashMap<String, Object>> listCode = cqbs.getListBySQL(sql.toString());
				 
				
				//=============listCode 排序并且排除页面存在的====================
				// 1、循环
				// 2、比较界面是否：ID_ROWINFOMap
				List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
				StringBuffer buffer = new StringBuffer();
				listStr = listStr.substring(1, listStr.length() - 1);
				List<String> list = Arrays.asList(listStr.split(","));
				for (String sendYnId : list) { 
					for (HashMap<String, Object> rowPerson:listCode) {
						boolean isExists = false;
						if (sendYnId.equals(rowPerson.get("yn_id"))) {
							//相同批次存在的人员
							for (Map<String, String> mapId:ID_ROWINFOMap.values()) {
								if (rowPerson.get("a0000").toString().trim().equals(mapId.get("a0000"))) {
									buffer.append(","+mapId.get("tp0101"));
									isExists = true;
									break;
								}
							}
						}else {
							isExists = true;
						}
						if (isExists == false) {
							result.add(rowPerson);
						}
					}
				}
				JSONArray updateunDataStoreObject = JSONArray.fromObject(result);
				//===============================================================
				
				//JSONArray updateunDataStoreObject = JSONArray.fromObject(listCode);
				// System.out.println(updateunDataStoreObject.toString());
				this.getExecuteSG().addExecuteCode("doAddPerson.addPerson(" + updateunDataStoreObject.toString() + ")");
				if (buffer.length()>0) {
					buffer.delete(0, 1);
					this.setMainMessage("选择的人员：<BR/>&nbsp;&nbsp;&nbsp;&nbsp;"+buffer.toString()+"<BR/>已经在页面加载！");
				}
				// 提取考察材料
				if (params.length == 2) {

					String kcclsql = "select * from tpb_att where rb_id='" + ortherYnid
							+ "' and js0100 in (select a0000 from TPHJ1 t " + " where yn_id='" + ortherYnid
							+ "' and tp0116='" + listStr + "' "
							+ " and not exists (select 1 from TPHJ1 p where p.a0000=t.a0000" + " and yn_id='" + ynId
							+ "' and tp0116='" + yn_type + "'))";
					List<TpbAtt> tpbattList = sess.createSQLQuery(kcclsql).addEntity(TpbAtt.class).list();
					String directory = "zhgbuploadfiles" + File.separator + ynId + File.separator;
					String disk = YNTPFileExportBS.HZBPATH;
					File f = new File(disk + directory);
					if (!f.isDirectory()) {
						f.mkdirs();
					}
					//======================附件信息=================
					for (TpbAtt tpbAtt : tpbattList) {
						TpbAtt tat = new TpbAtt();
						tat.setJs0100(tpbAtt.getJs0100());// 人员信息
						tat.setJsa00(UUID.randomUUID().toString());// 主键
						tat.setRbId(ynId);// 批次id
						tat.setJsa05(SysManagerUtils.getUserId());// 用户id
						tat.setJsa06(tpbAtt.getJsa06());// 上传时间
						tat.setJsa04(tpbAtt.getJsa04());

						String targetPath = disk + directory + tat.getJsa00();
						String sourcePath = disk + tpbAtt.getJsa07() + tpbAtt.getJsa00();
						SQLiteUtil.copyFileByPath(sourcePath, targetPath);

						tat.setJsa07(directory);
						tat.setJsa08(tpbAtt.getJsa08());
						sess.save(tat);

					}
				}
				//==================================================
				
				sess.flush();
			} catch (Exception e) {
				this.setMainMessage("查询失败！");
				e.printStackTrace();
			}
		} else if (listStr != null && listStr.length() > 2) {// 基础库选择人员
//			sql.append("select  sys_guid() tp0100, t.a0000 a0000,"
//					+ " '3' type,GET_tpbXingming(t.a0101,t.a0104,t.a0117,t.a0141) tp0101,t.a0107 tp0102,t.a1701 tp0103, t.a0288 tp0104,"
//					+ " GET_TPBXUELI2(t.qrzxl,t.zzxl,t.qrzxw,t.zzxw) tp0105,"
//					+ " t.a0192a tp0106,'' tp0107,'' tp0108,'' tp0109,'' tp0110,"
//					+ " '' tp0111,'' tp0112,'' tp0113,'' tp0114,'' tp0115,'default_color' kcclclass from A01_TPHJ t");

			sql.append("select distinct \n");  //sys_guid() tp0100,
					sql.append("       t.a0000 a0000,  \n");
			sql.append("       '3' type,  \n");
			sql.append("      a0101 tp0101, \n");  // GET_tpbXingming(t.a0101, t.a0104, t.a0117, t.a0141)
			sql.append("       t.a0107 tp0102, \n");
			sql.append("       t.A0192F tp0103, \n");
			sql.append("       nvl(t.a0288,A0192C) tp0104,  \n");
			sql.append("       t.a0192a tp0106, \n");
			sql.append("       '' tp0107, \n");
			sql.append("       '' tp0108, \n");
			sql.append("       '' tp0109, \n");
			sql.append("        '' tp0110, \n");
			sql.append("        '' tp0111, \n");
			sql.append("        '' tp0112, \n");
			sql.append("        '' tp0113, \n");
			sql.append("        '' tp0114, \n");
			sql.append("        '' tp0115, \n");
			sql.append("        'default_color' kcclclass, \n");
			sql.append("        A0104 as tp0118,  ---性别    \n ");
			sql.append("        A0117 as tp0119,  ----民族    \n");
			sql.append("        A0111A as tp0120,  ----籍贯名称    \n ");
			sql.append("        NL as tp0121, ---- 年龄   \n ");
			sql.append("        A0144 as tp0122,  --------- 入党时间  \n");
			sql.append("        QRZXLXX as tp0123,    ---------- 毕业院校及专业   \n");
			sql.append("        GET_TPBXUELI2(t.qrzxl, t.zzxl, '', '') as tp0124,	 	----------学历   \n");  //sql.append("        GET_TPBXUELI2(t.qrzxl, t.zzxl, t.qrzxw, t.zzxw) as tp0124,	 	----------学历   \n");
			sql.append("        '' as tp0125,    \n");
			sql.append("        '' as tp0126,    \n");
			sql.append("        '' as tp0127,    \n");
			sql.append("        '' as tp0128  \n");
			sql.append("   from A01_TPHJ t \n");
			sql.append(" where t.a0000 in ('-1'");
			listStr = listStr.substring(1, listStr.length() - 1);
			List<String> list = Arrays.asList(listStr.split(","));
			 
			List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
			StringBuffer buffer = new StringBuffer();
			for (String id : list) {
				boolean isExists = false;
				for (Map<String, String> mapId:ID_ROWINFOMap.values()) {
					if (id.trim().equals(mapId.get("a0000"))) {
						buffer.append(","+mapId.get("tp0101"));
						isExists = true;
						break;
					}
				}
				if (!isExists) {
					sql.append(",'" + id.trim() + "'");
				}
			}
			
//			for (String id : list) {
//				sql.append(",'" + id.trim() + "'");
//
//			}
			sql.append(") ");
			//sql.append(" and not exists (select 1 from TPHJ1 p where p.a0000=t.a0000");
//			sql.append(" and yn_id='" + ynId + "' and tp0116='" + yn_type + "')");
			try {
				CommQuery cqbs = new CommQuery();
				List<HashMap<String, Object>> listCode = cqbs.getListBySQL(sql.toString());
				GenericCodeItem codeItem = GenericCodeItem.getGenericCodeItem();

				for (HashMap m : listCode) {
					m.put("tp0100", UUID.randomUUID().toString());
					// 出生年月处理
					String text = this.getFTime(m.get("tp0102"));
					m.put("tp0102", text);

					if (text != null && text.length()>0) {
						int age = getAge(parse(text));
						if (age>0) {
							m.put("tp0121", String.valueOf(age));
						}
					}
					
					// 任现职时间处理
					text = this.getFTime(m.get("tp0104"));
					m.put("tp0104", text);
					
					text = this.getFTime(m.get("tp0103"));
					m.put("tp0103", text);
					
					
					text = this.getFTime(m.get("tp0122"));
					m.put("tp0122", text);
					
					text = (String) m.get("tp0118");
					Map map = codeItem.getCodeItem("GB2261");
					if (map != null) {
						map = (Map) map.get(text);
						if (map != null) {
							text = (String) map.get("code_name");
							m.put("tp0118", text);
						}
					}

					text = (String) m.get("tp0119");
					map = codeItem.getCodeItem("GB3304");
					if (map != null) {
						map = (Map) map.get(text);
						if (map != null) {
							text = (String) map.get("code_name");
							m.put("tp0119", text);
						}
					}

					// 任现职时间处理
//					String jianli = m.get("tp0103") == null ? "" : m.get("tp0103").toString();
//					String[] jianliArray = jianli.split("\r\n|\r|\n");
//					if (jianliArray.length > 0) {
//						String jl = jianliArray[jianliArray.length - 1].trim();
//						// boolean b =
//						// jl.matches("[0-9]{4}\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]{2}.*");//\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]
//						Pattern pattern = Pattern
//								.compile("[0-9| ]{4}[\\.| |．][0-9| ]{2}[\\-|─|-]{1,2}[0-9| ]{4}[\\.| |．][0-9| ]{2}");
//						Matcher matcher = pattern.matcher(jl);
//						if (matcher.find()) {
//							String line1 = matcher.group(0);
//							m.put("tp0103", line1.substring(0, 7));
//						} else {
//							m.put("tp0103", null);
//						}
//					} else {
//						m.put("tp0103", null);
//					}

				}
				this.getPageElement("coordTableHtmlContent").setValue("");
				JSONArray updateunDataStoreObject = JSONArray.fromObject(listCode);
				// System.out.println(updateunDataStoreObject.toString());
				this.getExecuteSG().addExecuteCode("doAddPerson.addPerson(" + updateunDataStoreObject.toString() + ")");

				if (buffer.length()>0) {
					buffer.delete(0, 1);
					this.setMainMessage("选择的人员：<BR/>&nbsp;&nbsp;&nbsp;&nbsp;"+buffer.toString()+"<BR/>已经在页面加载！");
				}
				
			} catch (Exception e) {
				this.setMainMessage("查询失败！");
				e.printStackTrace();
			}
		} else {
			this.setMainMessage("无法查询到该人员！");
			return EventRtnType.NORMAL_SUCCESS;
		}

		return EventRtnType.NORMAL_SUCCESS;

	}

	/***
	 * Format Time格式化时间函数
	 * 返回：YYYY.MM格式
	 * @param tex
	 * @return
	 */
	private String getFTime(Object tex) {
		String text = tex==null?"":tex.toString();
		text = text.replaceAll("\\(", "");
		text = text.replaceAll("\\)", "");
		text = text.replaceAll("\\-", ".");
		if (text != null && text.length()>=6) { 
//			text = text.replaceAll("、", "\\n");
//			text = text.replaceAll("，", "\\n");			 
			if (text.indexOf(".")>=0 && text.length() >= 6 && text.length() <= 7) {
				return text.substring(0, 4) + "." + text.substring(5);
			} else if (text.indexOf(".")>=0 && text.length() >= 8) {  
				if (text.charAt(6)>='0' && text.charAt(6)>='9') {
					return text.substring(0, 4) + "." + text.substring(5,7);
				} else {
					return text.substring(0, 4) + "." + text.substring(5,6);
				}
				
			}else {
				return text.substring(0, 4) + "." + text.substring(4,6);
			} 
		} else {
			return null;
		}
	}

	/****
	 * 计算年龄函数
	 * @param a
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@PageEvent("calNL")
	public int calNL(String a) throws Exception {
		String nlJZDate = this.getPageElement("cal_age_year").getValue();
		String ynId = this.getPageElement("ynId").getValue();
		String yn_type = this.getPageElement("yntype").getValue();
		String tpy = this.getPageElement("tpy").getValue();
		String tpm = this.getPageElement("tpm").getValue();
		String tpd = this.getPageElement("tpd").getValue();
		 
		Js2Yntp rb = (Js2Yntp) HBUtil.getHBSession().get(Js2Yntp.class, ynId);
		if (rb == null) {
			this.setMainMessage("无批次信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String sql1 = "Update TPHJ1 SET tp0121 = '19' || tp0102 where tp0102 like '__.__' ";
		String sql2 = "Update TPHJ1 Set tp0121 = GET_YEARS_OF_AGE( to_date(TP0102,'YYYY.MM'), to_date('"+nlJZDate+"','YYYY-MM-DD')) " + 
				" Where yn_Id='"+ynId+"' and TP0116 = '"+yn_type+"' and TP0102 LIKE '____.__' ";
		String sql3 = "Update TPHJ1 Set tp0121 = GET_YEARS_OF_AGE( to_date(TP0102,'YYYY.MM.DD'), to_date('"+nlJZDate+"','YYYY-MM-DD')) " + 
				" Where yn_Id='"+ynId+"' and TP0116 = '"+yn_type+"' and TP0102 LIKE '____.__.__' ";
		HBSession sess = null;
		PreparedStatement ps = null;
		Statement stat = null;
		Connection conn = null;
		try {
			sess = HBUtil.getHBSession();
			conn = sess.connection();
			conn.setAutoCommit(false);
			// 更新批次信息
			ps = conn.prepareStatement(sql1);
			ps.executeUpdate();
			
			ps = conn.prepareStatement(sql2);
			ps.executeUpdate();
			ps = conn.prepareStatement(sql3);
			ps.executeUpdate();
			
			String TPHJ1HtmlSql = "delete from TPHJ1Html where yn_id=?";
			ps = sess.connection().prepareStatement(TPHJ1HtmlSql);
			ps.setString(1, ynId);
			ps.executeUpdate();
			
			ps.close();
			conn.commit();
			//this.setMainMessage("计算完成！");
			this.getExecuteSG().addExecuteCode("window.alert('计算完成,将刷新页面！');location.reload(true);");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (conn != null)
					conn.rollback();
				if (conn != null)
					conn.close();
			} catch (Exception e1) {
				this.setMainMessage("计算失败！");
				e.printStackTrace();
			}
			this.setMainMessage("计算失败！");
			e.printStackTrace();
			return EventRtnType.NORMAL_SUCCESS;
		}	
		return EventRtnType.NORMAL_SUCCESS;
	}
	// 保存
	@SuppressWarnings("unchecked")
	@PageEvent("save")
	public int save(String a) throws Exception {
		String ynId = this.getPageElement("ynId").getValue();
		String yn_type = this.getPageElement("yntype").getValue();
		String tpy = this.getPageElement("tpy").getValue();
		String tpm = this.getPageElement("tpm").getValue();
		String tpd = this.getPageElement("tpd").getValue();

		String tb_unit_id = this.getPageElement("tb_unit_id").getValue();
		String cal_age_year = this.getPageElement("cal_age_year").getValue();
		Js2Yntp rb = (Js2Yntp) HBUtil.getHBSession().get(Js2Yntp.class, ynId);
		if (rb == null) {
			this.setMainMessage("无批次信息！");
			return EventRtnType.NORMAL_SUCCESS;
		} else {
			rb.setCalAgeYear(cal_age_year);
			rb.setTbUnit(tb_unit_id);
			HBUtil.getHBSession().save(rb);
			HBUtil.getHBSession().flush();
		}
		
		String ID_ROWINFO = this.getPageElement("ID_ROWINFO").getValue().replace("\n", "{/n}");
		String ROWID = this.getPageElement("ROWID").getValue();
		
		ID_ROWINFO = ID_ROWINFO.replaceAll("\\n", "");
		JSONObject ID_ROWINFOObject = JSONObject.fromObject(ID_ROWINFO);

		Map<String, Map<String, String>> ID_ROWINFOMap = (Map<String, Map<String, String>>) ID_ROWINFOObject;
		JSONArray ROWIDObject = JSONArray.fromObject(ROWID);
		List<String> ROWIDList = (List<String>) ROWIDObject;

		HBSession sess = null;
		PreparedStatement ps = null;
		Statement stat = null;
		Connection conn = null;
//		String sql = "insert into TPHJ1(tp0100, a0000, type, tp0101, tp0102, tp0103, tp0104, tp0105, tp0106, tp0107, sortnum, yn_id,tp0116,tp0111,tp0112,tp0115 )"
//				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sql = "insert into TPHJ1(tp0100, a0000, type, tp0101,tp0117,tp0106,tp0107,tp0118,tp0119,tp0120,tp0102,tp0121,tp0122,tp0123,tp0103,tp0104,tp0124,tp0125,tp0126,tp0127,tp0128, sortnum, yn_id,tp0116 )"
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		String updateJs2_yntp_info = "update Js2_yntp_info set info0y=?, info0m=?, info0d=? where yn_id=? and info01=?";
		try {
			sess = HBUtil.getHBSession();
			conn = sess.connection();
			conn.setAutoCommit(false);
			// 更新批次信息
			ps = conn.prepareStatement(updateJs2_yntp_info);
			ps.setString(1, tpy);  
			ps.setString(2, tpm);  
			ps.setString(3, tpd);  
			ps.setString(4, ynId); 
			ps.setString(5, yn_type); 
			ps.executeUpdate();
			// 删除
			ps = conn.prepareStatement("delete from TPHJ1 where yn_id=? and tp0116=?");
			ps.setString(1, ynId);
			ps.setString(2, yn_type);
			ps.executeUpdate();
			// 更新当前环节
			ps = conn.prepareStatement("update js2_yntp set yn_type=? where yn_id=?");
			ps.setString(1, yn_type);
			ps.setString(2, ynId);
			ps.executeUpdate();
			// 插入
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < ROWIDList.size(); i++) {
				String id = ROWIDList.get(i);
				Map<String, String> rowData = ID_ROWINFOMap.get(id);
				ps.setString(1, rowData.get("tp0100")); //1 tp0100
				//2  a0000
				if (textFormat(rowData.get("a0000"))!= null && !"".equals(rowData.get("a0000"))) {
					ps.setString(2, textFormat(rowData.get("a0000")));
				} else {
					ps.setString(2, "ZZZZ"+textFormat(rowData.get("tp0100")));
				} 
				ps.setString(3, rowData.get("type")==null?"1":rowData.get("type").toString()); //3 type
				ps.setString(4, textFormat(rowData.get("tp0101"))); //4 tp0101

				ps.setString(5, textFormat(rowData.get("tp0117"))); //5 tp0117
				ps.setString(6, textFormat(rowData.get("tp0106"))); //6 tp0106
				ps.setString(7, textFormat(rowData.get("tp0107")));  //7 tp0107
				ps.setString(8, textFormat(rowData.get("tp0118")));  //8 tp0118
				ps.setString(9, textFormat(rowData.get("tp0119")));  //9 tp0119
				ps.setString(10, textFormat(rowData.get("tp0120"))); //10 tp0120
				ps.setString(11, textFormat(rowData.get("tp0102"))); //11 tp0102
				
				String tp0121 = null;
				try {
					tp0121 = rowData.get("tp0121");
				}catch(Exception e) {
					
				}
				
				if (tp0121 != null && isNumeric(tp0121)) {
					ps.setInt(12, Integer.parseInt("0"+tp0121));  //12 tp0121
				} else {
					ps.setInt(12, 0);
				}
				ps.setString(13, textFormat(rowData.get("tp0122"))); //13 tp0122
				ps.setString(14, textFormat(rowData.get("tp0123"))); //14 tp0123
				ps.setString(15, textFormat(rowData.get("tp0103"))); //15 tp0103
				ps.setString(16, textFormat(rowData.get("tp0104"))); //16 tp0104
				ps.setString(17, textFormat(rowData.get("tp0124"))); //17 tp0124
				ps.setString(18, textFormat(rowData.get("tp0125"))); //18 tp0125
				ps.setString(19, textFormat(rowData.get("tp0126"))); //19 tp0126
				ps.setString(20, textFormat(rowData.get("tp0127"))); //20 tp0127
				ps.setString(21, textFormat(rowData.get("tp0128"))); //21 tp0128
				ps.setInt(22, i + 1);    //22 sortnum
				ps.setString(23, ynId);  //23 yn_id
				ps.setString(24, yn_type); //24 tp0116
				ps.addBatch();
			}
			ps.executeBatch();
			conn.commit();

			// 删除附件
			String tp0100SQL = "select jsa00,jsa07 from TPB_ATT a where rb_id=? and"
					+ " not exists(select 1 from TPHJ1 b where b.a0000=a.js0100 and yn_id=?) ";
			ps = conn.prepareStatement(tp0100SQL);
			ps.setString(1, ynId);
			ps.setString(2, ynId);
			ResultSet rst = ps.executeQuery();
			while (rst.next()) {
				String jsa00 = rst.getString(1);
				String jsa07 = rst.getString(2);
				String directory = YNTPFileExportBS.HZBPATH + jsa07;
				File f = new File(directory + jsa00);
				if (f.isFile()) {
					f.delete();
				}
			}
			rst.close();
			ps = conn.prepareStatement(
					"delete from TPB_ATT where rb_id=? and jsa00 in(select jsa00 from TPB_ATT a where "
							+ " not exists(select 1 from TPHJ1 b where b.a0000=a.js0100 and yn_id=?))");
			ps.setString(1, ynId);
			ps.setString(2, ynId);
			ps.executeUpdate();

			//保存 TPHJ1Html
			String TPHJ1HtmlSql = "delete from TPHJ1Html where yn_id=?";
			ps = conn.prepareStatement(TPHJ1HtmlSql);
			ps.setString(1, ynId);
			ps.executeUpdate();
			
			TPHJ1HtmlSql = "Insert Into TPHJ1Html(yn_id,yn_type,htmlContent) "+
					" values (?,?,?)";
			ps = conn.prepareStatement(TPHJ1HtmlSql);
			ps.setString(1, ynId);
			ps.setString(2, yn_type);
			ps.setString(3, this.getPageElement("coordTableHtmlContent").getValue());
			ps.executeUpdate();
			
			ps.close();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (conn != null)
					conn.rollback();
				if (conn != null)
					conn.close();
			} catch (Exception e1) {
				this.setMainMessage("保存失败！");
				e.printStackTrace();
			}
			if (e.getMessage() != null && e.getMessage().indexOf("ORA-00001") >= 0) {
				this.setMainMessage("保存失败！请确认读档后没有重复人员");
			} else {
				this.setMainMessage("保存失败！");
			}

			e.printStackTrace();
			return EventRtnType.NORMAL_SUCCESS;
		}

		if ("updateNRM".equals(a)) {
			this.setNextEventName("updateNRM");
		} else if ("savefirst".equals(a)) {
			this.getExecuteSG().addExecuteCode("uploadKCCL.openUploadKcclWin()");
		} else {
			this.getExecuteSG().addExecuteCode("Ext.example.msg('','保存成功。',1);");
		}

		return EventRtnType.NORMAL_SUCCESS;
	}

	public static boolean isNumeric(String str){    
        if(str == null){    
            return false;    
        }    
        for (int i = str.length();--i>=0;){    
            if (!Character.isDigit(str.charAt(i))){    
                return false;    
            }    
        }    
        return true;    
    }    
	
	// 保存
	@SuppressWarnings("unchecked")
	@PageEvent("saveGD")
	public int saveGD(String text) throws Exception {
		String ynId = this.getPageElement("ynId").getValue();
		String yn_type = this.getPageElement("yntype").getValue();

		Js2Yntp rb = (Js2Yntp) HBUtil.getHBSession().get(Js2Yntp.class, ynId);
		if (rb == null) {
			this.setMainMessage("无批次信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String yn_gd_id = UUID.randomUUID().toString();
		String JS2_YNTP_gdsql = "insert into JS2_YNTP_gd(yn_id,yn_name,yn_type,yn_userid,yn_org,yn_sysno,yn_date,"
				+ "yn_gd_id,yn_gd_desc )" + " (select yn_id,yn_name,?,yn_userid,yn_org,yn_sysno,sysdate,"
				+ "?,? from JS2_YNTP where yn_id=?)";

		HBSession sess = null;
		PreparedStatement ps = null;
		Connection conn = null;
		String sql = "insert into TPHJ1_gd(tp0100,a0000,type,tp0101,tp0117,tp0106,tp0118,tp0119,tp0120,tp0102,tp0121,tp0122,tp0123,tp0103,tp0104,tp0124,tp0125,tp0126,tp0127,tp0128,sortnum,yn_id,tp0116,"
				+ "yn_gd_id )"
				+ "                (select tp0100,a0000,type,tp0101,tp0117,tp0106,tp0118,tp0119,tp0120,tp0102,tp0121,tp0122,tp0123,tp0103,tp0104,tp0124,tp0125,tp0126,tp0127,tp0128,sortnum,yn_id,tp0116,"
				+ "? from TPHJ1 where yn_id=? and tp0116=?)";

		try {
			sess = HBUtil.getHBSession();
			conn = sess.connection();
			conn.setAutoCommit(false);
			// 插入
			ps = conn.prepareStatement(JS2_YNTP_gdsql);
			ps.setString(1, yn_type);
			ps.setString(2, yn_gd_id);
			ps.setString(3, text);
			ps.setString(4, ynId);
			ps.executeUpdate();

			// 插入
			ps = conn.prepareStatement(sql);
			ps.setString(1, yn_gd_id);
			ps.setString(2, ynId);
			ps.setString(3, yn_type);
			ps.executeUpdate();

			//保存 TPHJ1Html
			String TPHJ1HtmlSql = "delete from TPHJ1Html_gd where yn_id=?";
			ps = conn.prepareStatement(TPHJ1HtmlSql);
			ps.setString(1, ynId);
			ps.executeUpdate();
			
			TPHJ1HtmlSql = "Insert Into TPHJ1Html_GD(yn_id,yn_type,htmlContent) "+
					" values (?,?,?)";
			ps = conn.prepareStatement(TPHJ1HtmlSql);
			ps.setString(1, ynId);
			ps.setString(2, yn_type);
			ps.setString(3, this.getPageElement("coordTableHtmlContent").getValue());
			ps.executeUpdate(); 
			
			ps.close();
			
			
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (conn != null)
					conn.rollback();
				if (conn != null)
					conn.close();
			} catch (Exception e1) {
				this.setMainMessage("存档失败！");
				e.printStackTrace();
			}
			this.setMainMessage("存档失败！");
			e.printStackTrace();
			return EventRtnType.NORMAL_SUCCESS;
		}

		this.getExecuteSG().addExecuteCode("Ext.example.msg('','存档成功。',1);");

		return EventRtnType.NORMAL_SUCCESS;
	}

	// 更新拟任免
	@SuppressWarnings("unchecked")
	@PageEvent("updateNRM")
	public int updateNRM(String a0000p) throws Exception {
		String ynId = this.getPageElement("ynId").getValue();
		String yn_type = this.getPageElement("yntype").getValue();

		Js2Yntp rb = (Js2Yntp) HBUtil.getHBSession().get(Js2Yntp.class, ynId);
		if (rb == null) {
			this.setMainMessage("无批次信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String sql = "";
		if (a0000p == null || "".equals(a0000p)) {
			sql = "select a0000,tp0111,tp0112 from TPHJ1 where yn_id='" + ynId + "' and tp0116='" + yn_type
					+ "' and a0000 is not null";
		} else {
			sql = "select a0000,tp0111,tp0112 from TPHJ1 where yn_id='" + ynId + "' and tp0116='" + yn_type
					+ "' and  a0000='" + a0000p + "'";
		}
		HBSession sess = HBUtil.getHBSession();

		try {
			List<Object[]> list = sess.createSQLQuery(sql).list();
			String userid = SysUtil.getCacheCurrentUser().getId();

			for (int i = 0; i < list.size(); i++) {
				Object[] info = list.get(i);
				String a0000 = info[0] == null ? "" : info[0].toString();
				String sqla53 = "from A53 where a0000='" + a0000 + "' and a5399='" + userid + "'";
				List<A53> a53list = sess.createQuery(sqla53).list();
				if (a53list.size() > 0) {
					A53 a53 = a53list.get(0);
					a53.setA5304(info[1] == null ? "" : info[1].toString());
					a53.setA5315(info[2] == null ? "" : info[2].toString());
					sess.update(a53);
				} else {
					A53 a53 = new A53();
					a53.setA0000(a0000);
					a53.setA5399(userid);
					a53.setA5304(info[1] == null ? "" : info[1].toString());
					a53.setA5315(info[2] == null ? "" : info[2].toString());
					String date = DateUtil.getcurdate();
					a53.setA5321(date);
					a53.setA5323(date);
					a53.setA5327(SysManagerUtils.getUserName());
					sess.save(a53);
				}

				sess.flush();
			}
		} catch (Exception e) {
			this.setMainMessage("更新拟任免信息失败！");
			e.printStackTrace();
		}

		this.setMainMessage("更新拟任免信息成功！");

		return EventRtnType.NORMAL_SUCCESS;
	}

	private String textFormat(Object v) {
		String value = null;
		if (v != null && !"".equals(value)) {
			if ("null".equals(v.toString())) {
				return null;
			}
			if (v!=null && v.toString().contains("&")){
				value = v.toString().replaceAll("&nbsp;", "");
				value = v.toString().replaceAll("&NBSP;", "");
			}
			value = v.toString().replaceAll("[{]/n[}]", "\n");
			value = value.toString().replaceAll("\\u00a0", ""); 
		}
		return value;
	}

	/**
	 * 信息导出
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("ExpTPB")
	public int ExpTPB(String param) throws Exception {
		try {

			YNTPFileExportBS fileBS = new YNTPFileExportBS(null);
			// String rbId = this.getPageElement("rbId").getValue();
			String ynId = this.request.getParameter("ynId");// 批次
			String yntype = this.request.getParameter("yntype");// 环节
			// String expType = this.request.getParameter("expType");//导出类型

			fileBS.setOutputpath(YNTPFileExportBS.HZBPATH + "zhgboutputfiles/" + ynId + "/");
			File f = new File(fileBS.getOutputpath());

			if (f.isDirectory()) {// 先清空输出目录
				JSGLBS.deleteDirectory(fileBS.getOutputpath());
			}

			fileBS.exportGBTP(ynId, yntype);

			List<String> fns = fileBS.getOutputFileNameList();
			String downloadPath = "";
			String downloadName = "";
			if (fns.size() == 1) {// 一个文件直接下载
				downloadPath = fileBS.getOutputpath() + fns.get(0);
				downloadName = fns.get(0);
			} else if (fns.size() > 1) {// 压缩
				// downloadPath = fileBS.getOutputpath()+fileBS.getSheetName()+".zip";
				// downloadName = fileBS.getSheetName()+".zip";
				// Zip7z.zip7Z(fileBS.getOutputpath(), downloadPath, null);

			} else {
				return EventRtnType.NORMAL_SUCCESS;
			}
			String downloadUUID = UUID.randomUUID().toString();
			request.getSession().setAttribute(downloadUUID, new String[] { downloadPath, downloadName });
			this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='" + downloadUUID + "';");
			this.getExecuteSG().addExecuteCode("downloadByUUID();");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("导出失败" + e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 导出任免表word
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("expWord")
	public int expWord(String param) throws Exception {
		try {

			YNTPFileExportBS fileBS = new YNTPFileExportBS(null);
			// String rbId = this.getPageElement("rbId").getValue();
			String ynId = this.getPageElement("ynId").getValue();
			String yntype = this.getPageElement("yntype").getValue();
			// String expType = this.request.getParameter("expType");//导出类型

			Js2Yntp rb = (Js2Yntp) HBUtil.getHBSession().get(Js2Yntp.class, ynId);
			if (rb == null) {
				this.setMainMessage("无批次信息！");
				return EventRtnType.NORMAL_SUCCESS;
			}

			fileBS.setOutputpath(YNTPFileExportBS.HZBPATH + "zhgboutputfiles/" + ynId + "/");
			File f = new File(fileBS.getOutputpath());

			if (f.isDirectory()) {// 先清空输出目录
				JSGLBS.deleteDirectory(fileBS.getOutputpath());
			}
			fileBS.NORM = param;
			fileBS.expWord(ynId, yntype);
			File fa[] = f.listFiles();

			for (int i = 0; i < fa.length; i++) {
				File fs = fa[i];
				if (fs.isFile()) {

					String filename = fs.getName();
					String downloadUUID = UUID.randomUUID().toString();
					request.getSession().setAttribute(downloadUUID,
							new String[] { fileBS.getOutputpath() + filename, filename });
					this.getExecuteSG().addExecuteCode(
							"Print.startPrint('" + downloadUUID + "','" + request.getSession().getId() + "');");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("导出失败" + e.getMessage());
		}
		this.setMainMessage("打印完成");
		return EventRtnType.NORMAL_SUCCESS;
	}

	public static Date parse(String strDate) throws ParseException {
		SimpleDateFormat sdf = null;
		strDate = strDate.replaceAll("[.]", "-");
		if (strDate.indexOf("-")>0 && strDate.length()==10) {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}else if (strDate.indexOf("-")>0 && strDate.length()==7) {
			sdf = new SimpleDateFormat("yyyy-MM");
		}else if (strDate.indexOf("-")<0 && strDate.length()==8) {
			sdf = new SimpleDateFormat("yyyyMMdd");
		}else if (strDate.indexOf("-")<0 && strDate.length()==6) {
			sdf = new SimpleDateFormat("yyyyMM");
		}else {
			sdf = new SimpleDateFormat("yyyyMMdd");
		}
		return sdf.parse(strDate);
	}

	public static int getAge(Date birthDay) throws Exception {
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthDay)) { // 出生日期晚于当前时间，无法计算
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}
		int yearNow = cal.get(Calendar.YEAR); // 当前年份
		int monthNow = cal.get(Calendar.MONTH); // 当前月份
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); // 当前日期
		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		int age = yearNow - yearBirth; // 计算整岁数
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth)
					age--;// 当前日期在生日之前，年龄减一
			} else {
				age--;// 当前月份在生日之前，年龄减一
			}
		}
		return age;
	}

	public static void main(String[] args) {
//        try {
//          int  age = getAge(parse("1990.09.27"));           //由出生日期获得年龄***
//          System.out.println("age:"+age);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
		System.out.println("{/n}".replaceAll("？", "\n"));
    }
	
	@PageEvent("ExpTPBExcel")
	public int ExpTPBExcel(String param) throws Exception{
		System.out.println("文件开始!"+(new Date()).toLocaleString());
		try { 
			YNTPFileExportBS fileBS = new YNTPFileExportBS(null);
			String ynId = this.request.getParameter("ynId");//批次
			String yntype = this.request.getParameter("yntype");//环节
			String yn_gd_id = this.request.getParameter("yn_gd_id");
			
			String sql = null;
			if(yn_gd_id==null||"".equals(yn_gd_id)){
				sql = "select 0 as xh,tphj1.* from tphj1 where yn_id='"+ynId+"' order by sortnum asc ";
			} else {
				sql = "select 0 as xh,tphj1_gd.* from tphj1_gd where yn_gd_id='"+yn_gd_id+"' and order by sortnum asc ";
			}
			  
			List<HashMap<String, Object>> listValues = CommonQueryBS.getListBySQL(sql);
			
			com.insigma.siis.local.pagemodel.xbrm2.expexcel.ExpTPB objExpTPB = new com.insigma.siis.local.pagemodel.xbrm2.expexcel.ExpTPB();
			
			String downloadUUID = UUID.randomUUID().toString().replaceAll("\\-", "");
			List<String> fns = fileBS.getOutputFileNameList();
			String downloadName = "gbynrmb_"+downloadUUID+".xlsx"; 
			String downloadPath = YNTPFileExportBS.HZBPATH+"zhgboutputfiles/"+ynId+"/"+downloadName;
			 
			request.getSession().setAttribute(downloadUUID, new String[]{downloadPath,downloadName});
			this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='"+downloadUUID+"';");
			this.getExecuteSG().addExecuteCode("downloadByUUID();");
			
			String path = this.getClass().getClassLoader().getResource("/").getPath();
			
			String filePath = path+"template/gbynrmb.xlsx";
			
			if ("TPHJ2".equals(yntype)) {
				filePath = path+"template/gbcwhrmb.xlsx";
			}else if ("TPHJ3".equals(yntype)) {
				filePath = path+"template/gbswsjrmb.xlsx";
			} else {
				filePath = path+"template/gbynrmb.xlsx";
			}
			String desFilepath = downloadPath;
			objExpTPB.copyFile(filePath, desFilepath);
			List<String> titles = objExpTPB.getTitles();
			List<HashMap<String, Object>> values = listValues;
			objExpTPB.writeExcel(desFilepath, "Sheet1", titles, values);
			this.setMainMessage("操作成功！");
		}catch(Exception e) {
			e.printStackTrace();
			this.setMainMessage("导出失败"+e.getMessage());
		} 
		System.out.println("文件生成成功!"+(new Date()).toLocaleString());
		return EventRtnType.NORMAL_SUCCESS;
	}
}