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
 *  ʵ�ָɲ���������� 2019-08-20
 * @author zouzhilin
 *
 */
public class TPBPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");

		// ����������

		return EventRtnType.NORMAL_SUCCESS;
	}

	/***
	 *  ҳ���ʼ�� 2019-08-20
	 *  ����:ynId������ID
	 *         yntype���������ͣ��С�TPHJ1���ɲ�һ����TPHJ2��ʡί��ί��ɲ����һ����TPHJ3��ʡί���ר�����ɲ����һ����
	 *         IsGDCL���Ƿ�鵵����   2019-09-16 ����
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("initX")
	public int initX() throws RadowException, AppException {
		
		// openWindow
		String id = this.getPageElement("subWinIdBussessId").getValue();	/***�Ӵ򿪴�����subWinIdBussessId��ȡ����****/
		
		String ynId = this.getPageElement("ynId").getValue();						/***�ӽ����ȡ��������ynId****/
		String yn_type = this.getPageElement("yntype").getValue();				/***�ӽ����ȡ��������yntype****/
		String IsGDCL = this.getPageElement("IsGDCL").getValue(); 				/***�ӽ����Ƿ�鵵����IsGDCL****/
		if (ynId==null || yn_type==null || "".equals(ynId) ||
				"".equals(ynId)) {																		/***�ж��Ƿ�Odin�Ƿ���****/
			String[] idtype=id.split(",");															/***�Ӵ򿪴�����subWinIdBussessId��ID��ȡ����****/
			if (idtype.length<2) {
				return EventRtnType.NORMAL_SUCCESS;
			}
			ynId=idtype[0];																				/***��һ����������������ynId****/
			yn_type =idtype[1]; 																		/***�ڶ�����������������yntype****/
		} 
		Js2Yntp rb = (Js2Yntp) HBUtil.getHBSession().get(Js2Yntp.class, ynId); /***������������ynId�Ӷ�ȡER����****/
		String tb_unit_id = "";																		/***��λ����ID****/
		String cal_age_year = "";																	/***�����������****/
		if (rb != null) {																					
			tb_unit_id = rb.getTbUnit();
			if (tb_unit_id==null) {
				tb_unit_id = "";
			}
			cal_age_year = rb.getCalAgeYear();											/***�����ݿ��ȡ�������εļ������****/
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
			this.setMainMessage("��������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}		
		//====================================== ***/ 
		this.getPageElement("rmburl").setValue("");														/***��ҳ��Ԫ������������URL****/
		this.getPageElement("ynId").setValue(ynId);														/***��ҳ��Ԫ����������κ�****/
		this.getExecuteSG().addExecuteCode("$('#yn_type').val('"+yn_type+"')");			/***JQuery��ҳ��Ԫ������ı�����****/
		this.getExecuteSG().addExecuteCode("$('#tb_unit').val('"+tb_unit_id+"')");			/***JQuery��ҳ��Ԫ������ĵ�λ****/
		this.getExecuteSG().addExecuteCode("fillCalAgeYear('"+cal_age_year+"')");		/***JQuery����ҳ��Ԫ��fillCalAgeYear****/
		
		//this.getPageElement("yn_type").setValue(yn_type);
		this.getPageElement("yntype").setValue(yn_type);												/***��ҳ��Ԫ�����ñ�����****/
		
//		Js2Yntp rb = (Js2Yntp) HBUtil.getHBSession().get(Js2Yntp.class, ynId);
		if (rb == null) {
			this.setMainMessage("��������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//=================begin �����ݿ��ȡ���ε�HTMLҳ�滺��===================
		//1��������ҪĿ���ǽ�������û��޸���һ�������ʽ�磺�����ֺš������С����ɫ�����뷽ʽ��===
		//2�����ټ��㣬�Ż��ٶ�
		//3��ע�⣺ǰ�˴������ı��HTML�Ǿ���Base64���м��ܵ��ַ���
		//              ���ҷ��ص�ǰ��HTMLҲ��Base64���ܵ��ַ���
		//              ���ܡ����ܶ�����base64.js��ʵ�ִ����
		String htmlContent = "";
		String TPHJ1Html = "select htmlcontent from TPHJ1Html where yn_id='"+ynId+"' and yn_type='"+yn_type+"'";
																							/***
																							  * YN_ID						����ID
																							  *	YN_TYPE					��������
																							  *	HTMLCONTENT 		����洢������
																							  */
		if ("1".equals(IsGDCL)) {
			TPHJ1Html = "select htmlcontent from TPHJ1Html_GD where yn_id='"+ynId+"' and yn_type='"+yn_type+"'";
		}
		CommQuery htmlbs = new CommQuery();
		List<HashMap<String, Object>> listhtml = htmlbs.getListBySQL(TPHJ1Html);
		if (listhtml.size()>0) {
			HashMap<String, Object> map = listhtml.get(0);					/**���ؽ�����Ҳֻ��һ����ȡ��һ����¼****/
			htmlContent = (String)map.get("htmlcontent");						/**����htmlContent������****/
			if (htmlContent == null || "".equals(htmlContent)) {
				htmlContent = ""; 
			}
		}
		this.getPageElement("coordTableHtmlContent").setValue(htmlContent);
		//=============================end=================================
		
//		String sql = "select tp0100, a0000, type, tp0101, tp0102, tp0103, tp0104,"
//				+ " tp0105, tp0106, tp0107,tp0108,tp0109,tp0110, tp0111,tp0112,tp0113,tp0114,tp0115,nvl2((select jsa00 from tpb_att where js0100=TPHJ1.a0000 and rb_id=TPHJ1.yn_id),'kcclclass','default_color') kcclclass from TPHJ1 where yn_id='"+ynId+"' and tp0116='"+yn_type+"' order by sortnum asc ";

		//==========================Begin ��ȡ���价�ڱ�洢����Ϣ===================
		String sql = "select tp0100, a0000, type, tp0101,tp0117,tp0106,tp0107,tp0118,tp0119,tp0120,tp0102,tp0121,tp0122,tp0123,tp0103,tp0104,tp0124,tp0125,tp0126,tp0127,tp0128,nvl2((select jsa00 from tpb_att where rownum<2 and js0100=TPHJ1.a0000 and rb_id=TPHJ1.yn_id and rownum<2),'kcclclass','default_color') kcclclass from TPHJ1 where yn_id='"
				+ ynId + "' and tp0116='" + yn_type + "' order by sortnum asc "; /*** 
																													 *	TP0100				id
																													 *	A0000				��Աid
																													 *	TYPE			        1һ������2��������3����
																													 *	TP0101  ����
																													 *	TP0117  ��Ƭ       
																													 *	TP0106  ����ְ��
																													 *	TP0107  ������ְ��
																													 *	TP0118  �Ա�
																													 *	TP0119  ����
																													 *	TP0120  ����
																													 *	TP0102  �������� 
																													 *	TP0121  ����
																													 *	TP0122  �뵳ʱ��
																													 *	TP0124  ѧ��
																													 *	TP0123  ��ҵԺУ��רҵ
																													 *	TP0103 	����ְʱ��
																													 *	P0104 	ְͬ��ʱ�� 	        	
																													 *	TP0128  ��ע
																													 *	TP0125  ̸�������Ƽ�
																													 *	TP0126  �����Ƽ�
																													 *	TP0127  �ص㿼��
																													 *	TP0128  ��ע					
				 																									  **/
		String js2_yntp_info = "select info0y, info0m, info0d from js2_yntp_info where yn_id='" + ynId
				+ "' and info01='" + yn_type + "'";

		if ("1".equals(IsGDCL)) {
			sql = "select tp0100, a0000, type, tp0101,tp0117,tp0106,tp0107,tp0118,tp0119,tp0120,tp0102,tp0121,tp0122,tp0123,tp0103,tp0104,tp0124,tp0125,tp0126,tp0127,tp0128,nvl2((select jsa00 from tpb_att where  rownum<2 and js0100=TPHJ1_GD.a0000 and rb_id=TPHJ1_GD.yn_id),'kcclclass','default_color') kcclclass from TPHJ1_GD where yn_id='"
					+ ynId + "' and tp0116='" + yn_type + "' order by sortnum asc "; /*** 
																														 *	TP0100				id
																														 *	A0000				��Աid
																														 *	TYPE			        1һ������2��������3����
																														 *	TP0101  ����
																														 *	TP0117  ��Ƭ       
																														 *	TP0106  ����ְ��
																														 *	TP0107  ������ְ��
																														 *	TP0118  �Ա�
																														 *	TP0119  ����
																														 *	TP0120  ����
																														 *	TP0102  �������� 
																														 *	TP0121  ����
																														 *	TP0122  �뵳ʱ��
																														 *	TP0124  ѧ��
																														 *	TP0123  ��ҵԺУ��רҵ
																														 *	TP0103 	����ְʱ��
																														 *	P0104 	ְͬ��ʱ�� 	        	
																														 *	TP0128  ��ע
																														 *	TP0125  ̸�������Ƽ�
																														 *	TP0126  �����Ƽ�
																														 *	TP0127  �ص㿼��
																														 *	TP0128  ��ע					
																															  **/
		}
		try {
			CommQuery cqbs = new CommQuery();																/***������ѯ����****/
			List<HashMap<String, Object>> listCode = cqbs.getListBySQL(sql.toString());		/***SQL��ѯ�����ҷ���List<HashMap<String, Object>>����****/
			JSONArray updateunDataStoreObject = JSONArray.fromObject(listCode);				/***Java.util.List����ת��ΪJSON����****/
			// System.out.println(updateunDataStoreObject.toString());
			this.getExecuteSG().addExecuteCode("TIME_INIT.changeTableType('" + yn_type + "');");/***���õ��������****/
			this.getExecuteSG().addExecuteCode("doAddPerson ." +
                    "addPerson(" + updateunDataStoreObject.toString() + ");");							/***����ǰ�˵�doAddPerson.addPerson��䵽ҳ��****/
			
			String groupName = getDeptName();																	/***��ȡ��������****/
			if (groupName != null && !"".equals(groupName)) {												/***���****/
				this.getExecuteSG().addExecuteCode("if ($('#tb_unit').val() =='') { $('#tb_unit').val('"+groupName+"')};");
			}
			// ����ʱ��
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
			this.setMainMessage("��ѯʧ�ܣ�");
			e.printStackTrace();
		}

		return EventRtnType.NORMAL_SUCCESS;
	}

	/***
	 * �õ���������
	 * @return
	 */
	private String getDeptName() { 
		String userId = SysManagerUtils.getUserId();
		String deptName = "";
		String sql = "select usergroupname from smt_usergroup where id in (select dept from smt_user where userid = '"+userId+"')";
					/***
					 * smt_user					    ��ϵͳ�û���
					 * smt_usergroup             ���û����
					 * ID									Ψһ����
					 * USERGROUPNAME		�û�������
					* SID									�ϼ��û���ID
					* SORTID							�����ֶ�
					 */
		try {
			
			CommQuery cqbs = new CommQuery();																/***������ѯ����****/
			List<HashMap<String, Object>> listCode = cqbs.getListBySQL(sql.toString());		/***SQL��ѯ�����ҷ���List<HashMap<String, Object>>����****/
			 
			if (listCode.size()>0) {																							/***�ж��Ƿ�Ϊ��****/
				deptName = (String)((Map)listCode.get(0)).get("deptName");							/***��ȡ�����������NULL������ʾ���ַ���****/
				if (deptName == null) {
					deptName = "";
				}
			}
		} catch (Exception e) {
			// this.setMainMessage("��ѯʧ�ܣ�");
			e.printStackTrace();  
		}
		return deptName;  																									/***��������ֵ�����NULL���򷵻ؿ��ַ���****/
	}
	
	/***
	 * ������Ա�����жϵ��价�ڱ�TPHJ1�Ƿ���ڸ���Ա
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("hasSaved")
	public int hasSaved() throws RadowException, AppException {
		String tp0100 = this.request.getParameter("tp0100");								/***FORM����ȡ��Ա����****/
		String sql = "select 1 from TPHJ1 where tp0100='" + tp0100 + "'";			/***����SQL��ѯ���****/

		try {
			List list = HBUtil.getHBSession().createSQLQuery(sql).list();					/***��ѯ����List����****/
			if (list.size() > 0) {																					/***�ж�List�Ƿ�Ϊ��****/
				return EventRtnType.NORMAL_SUCCESS;											/**���سɹ�****/
			} else {
				return EventRtnType.FAILD;
			}
		} catch (Exception e) {
			this.setMainMessage("��ѯʧ�ܣ�");														/***������****/
			e.printStackTrace();
			return EventRtnType.FAILD;																	/**���ش���****/
		}

	}

	/****
	 * �ж�A01_TPHJ �Ƿ���ڸ���Ա
	 * A01_TPHJ����һ��������ʽ����м�����Ա��ͼ
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("hasPerson")
	public int hasPerson() throws RadowException, AppException {
		String tp0100 = this.request.getParameter("a0000");											/***FORM����ȡ��Ա����****/
		String sql = "select 1 from A01_TPHJ where a0000='" + tp0100 + "'";				/***������ѯSQL****/

		try {
			List list = HBUtil.getHBSession().createSQLQuery(sql).list();							/***��ѯ����List����****/
			if (list.size() > 0) {																							/***�ж�List�Ƿ�Ϊ��****/
				return EventRtnType.NORMAL_SUCCESS;													/**���سɹ�****/
			} else {
				return EventRtnType.FAILD;																		/**���ش���****/
			}
		} catch (Exception e) {
			// this.setMainMessage("��ѯʧ�ܣ�");
			e.printStackTrace();
			return EventRtnType.FAILD;																			/**���ش���****/
		}

	}

	/***
	 * �õ�������¼��URL
	 * ���ؽ����1����ʽ��������URL
	 * 					2���м��������URL
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("getRMBLoginURL")
	public int getRMBLoginURL() throws RadowException {
		String a0000 = this.request.getParameter("a0000");													/***FORM����ȡ��Ա����****/
		String sql = "select remoteserver from A01_TPHJ where a0000='" + a0000 + "'";		/***������ѯSQL****/

		String RemoteServer = "";																							/***Զ�̷��������****/
		try {
			
			CommQuery cqbs = new CommQuery();																/***������ѯ����****/
			List<HashMap<String, Object>> listCode = cqbs.getListBySQL(sql.toString());		/***��ѯ����List����****/
			 
			if (listCode.size()>0) {																							/***�ж��Ƿ�Ϊ��****/
				RemoteServer = (String)((Map)listCode.get(0)).get("remoteserver");					/***���ظ���ԱԶ�̷�������URL****/
			} else {
				return EventRtnType.NORMAL_SUCCESS;															/**Event���سɹ�****/
			}
		} catch (Exception e) {
			// this.setMainMessage("��ѯʧ�ܣ�");
			e.printStackTrace(); 
			return EventRtnType.FAILD;																					/**Event���ش���****/
		}
		
		
		//===================Begin  ���������¼��ʽ====================================
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
	 * ��ȡԶ�̷�����URL����
	 * @return
	 * @throws RadowException
	 */
	public static String getRemoteServer() throws RadowException { 
		String sql = "select RemoteServer() RemoteServer from dual ";					/***������ѯSQL****/

		String RemoteServer = "";																			/***��������RemoteServer****/
		try {
			
			CommQuery cqbs = new CommQuery();												/***������ѯ����****/
			List<HashMap<String, Object>> listCode = cqbs.getListBySQL(sql.toString());/***��ѯ����List����****/
			 
			if (listCode.size()>0) {
				RemoteServer = (String)((Map)listCode.get(0)).get("remoteserver"); /***��ȡֵremoteserver****/
			}
		} catch (Exception e) {
			RemoteServer = ""; 
		}
		return RemoteServer;
	}
	
	/****
	 * ������ʱ���ݿ⡾�����ɲ��⡿��URL
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("getTemplateDBURL")
	public int getTemplateDBURL() throws RadowException {  

		String RemoteServer = getRemoteServer();			//ͨ������getRemoteServer��ȡURL
		 
		
		//===================Begin ��ϵͳ�����¼��ʽ=================
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
	 * �ɲ������ĵ��롢�������뵥���¼ģʽ
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
			// this.setMainMessage("��ѯʧ�ܣ�");
			e.printStackTrace(); 
			return EventRtnType.FAILD;
		}
		
		/////===================ͨ�������¼��ʽ�����û������ܵ�UUID����ҳ��==========
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
			//��������ҳ��
			this.setSelfResponseFunc(RemoteServer+"/pages/publicServantManage/ImportLrms2.jsp?businessClass=com.picCut.servlet.SaveLrmFile&vid="+vid);			
		}else {
			//����ҳ��
			this.setSelfResponseFunc(RemoteServer+"/pages/publicServantManage/ImportLrm2.jsp?businessClass=com.picCut.servlet.SaveLrmFile&vid="+vid);
		}
		
		//this.setSelfResponseFunc(RemoteServer+"busiAction.do?method=zhgbrmb&a0000="+a0000+"&vid="+vid+"");
		//this.getPageElement("rmburl").setValue( RemoteServer+"rmb/ZHGBrmb.jsp?FromModules=1&a0000="+a0000+"&vid="+vid);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 *  ��IDs��ѯ��Ա��Ϣ
	 *  A01_TPHJ��������ʽ����м�⡾�����ɲ���Ա���ݿ⡿����Ϣ
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
		
		//���� TPHJ1Html
		String TPHJ1HtmlSql = "delete from TPHJ1Html where yn_id=?";
		PreparedStatement ps = sess.connection().prepareStatement(TPHJ1HtmlSql);
		ps.setString(1, ynId);
		ps.executeUpdate();
		sess.connection().commit();
		if (ps != null) ps.close(); 
		if (listStr != null && listStr.indexOf("TPHJ") >= 0) {// ѡ����������������
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
				 
				
				//=============listCode �������ų�ҳ����ڵ�====================
				// 1��ѭ��
				// 2���ȽϽ����Ƿ�ID_ROWINFOMap
				List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
				StringBuffer buffer = new StringBuffer();
				listStr = listStr.substring(1, listStr.length() - 1);
				List<String> list = Arrays.asList(listStr.split(","));
				for (String sendYnId : list) { 
					for (HashMap<String, Object> rowPerson:listCode) {
						boolean isExists = false;
						if (sendYnId.equals(rowPerson.get("yn_id"))) {
							//��ͬ���δ��ڵ���Ա
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
					this.setMainMessage("ѡ�����Ա��<BR/>&nbsp;&nbsp;&nbsp;&nbsp;"+buffer.toString()+"<BR/>�Ѿ���ҳ����أ�");
				}
				// ��ȡ�������
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
					//======================������Ϣ=================
					for (TpbAtt tpbAtt : tpbattList) {
						TpbAtt tat = new TpbAtt();
						tat.setJs0100(tpbAtt.getJs0100());// ��Ա��Ϣ
						tat.setJsa00(UUID.randomUUID().toString());// ����
						tat.setRbId(ynId);// ����id
						tat.setJsa05(SysManagerUtils.getUserId());// �û�id
						tat.setJsa06(tpbAtt.getJsa06());// �ϴ�ʱ��
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
				this.setMainMessage("��ѯʧ�ܣ�");
				e.printStackTrace();
			}
		} else if (listStr != null && listStr.length() > 2) {// ������ѡ����Ա
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
			sql.append("        A0104 as tp0118,  ---�Ա�    \n ");
			sql.append("        A0117 as tp0119,  ----����    \n");
			sql.append("        A0111A as tp0120,  ----��������    \n ");
			sql.append("        NL as tp0121, ---- ����   \n ");
			sql.append("        A0144 as tp0122,  --------- �뵳ʱ��  \n");
			sql.append("        QRZXLXX as tp0123,    ---------- ��ҵԺУ��רҵ   \n");
			sql.append("        GET_TPBXUELI2(t.qrzxl, t.zzxl, '', '') as tp0124,	 	----------ѧ��   \n");  //sql.append("        GET_TPBXUELI2(t.qrzxl, t.zzxl, t.qrzxw, t.zzxw) as tp0124,	 	----------ѧ��   \n");
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
					// �������´���
					String text = this.getFTime(m.get("tp0102"));
					m.put("tp0102", text);

					if (text != null && text.length()>0) {
						int age = getAge(parse(text));
						if (age>0) {
							m.put("tp0121", String.valueOf(age));
						}
					}
					
					// ����ְʱ�䴦��
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

					// ����ְʱ�䴦��
//					String jianli = m.get("tp0103") == null ? "" : m.get("tp0103").toString();
//					String[] jianliArray = jianli.split("\r\n|\r|\n");
//					if (jianliArray.length > 0) {
//						String jl = jianliArray[jianliArray.length - 1].trim();
//						// boolean b =
//						// jl.matches("[0-9]{4}\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]{2}.*");//\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]
//						Pattern pattern = Pattern
//								.compile("[0-9| ]{4}[\\.| |��][0-9| ]{2}[\\-|��|-]{1,2}[0-9| ]{4}[\\.| |��][0-9| ]{2}");
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
					this.setMainMessage("ѡ�����Ա��<BR/>&nbsp;&nbsp;&nbsp;&nbsp;"+buffer.toString()+"<BR/>�Ѿ���ҳ����أ�");
				}
				
			} catch (Exception e) {
				this.setMainMessage("��ѯʧ�ܣ�");
				e.printStackTrace();
			}
		} else {
			this.setMainMessage("�޷���ѯ������Ա��");
			return EventRtnType.NORMAL_SUCCESS;
		}

		return EventRtnType.NORMAL_SUCCESS;

	}

	/***
	 * Format Time��ʽ��ʱ�亯��
	 * ���أ�YYYY.MM��ʽ
	 * @param tex
	 * @return
	 */
	private String getFTime(Object tex) {
		String text = tex==null?"":tex.toString();
		text = text.replaceAll("\\(", "");
		text = text.replaceAll("\\)", "");
		text = text.replaceAll("\\-", ".");
		if (text != null && text.length()>=6) { 
//			text = text.replaceAll("��", "\\n");
//			text = text.replaceAll("��", "\\n");			 
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
	 * �������亯��
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
			this.setMainMessage("��������Ϣ��");
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
			// ����������Ϣ
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
			//this.setMainMessage("������ɣ�");
			this.getExecuteSG().addExecuteCode("window.alert('�������,��ˢ��ҳ�棡');location.reload(true);");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (conn != null)
					conn.rollback();
				if (conn != null)
					conn.close();
			} catch (Exception e1) {
				this.setMainMessage("����ʧ�ܣ�");
				e.printStackTrace();
			}
			this.setMainMessage("����ʧ�ܣ�");
			e.printStackTrace();
			return EventRtnType.NORMAL_SUCCESS;
		}	
		return EventRtnType.NORMAL_SUCCESS;
	}
	// ����
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
			this.setMainMessage("��������Ϣ��");
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
			// ����������Ϣ
			ps = conn.prepareStatement(updateJs2_yntp_info);
			ps.setString(1, tpy);  
			ps.setString(2, tpm);  
			ps.setString(3, tpd);  
			ps.setString(4, ynId); 
			ps.setString(5, yn_type); 
			ps.executeUpdate();
			// ɾ��
			ps = conn.prepareStatement("delete from TPHJ1 where yn_id=? and tp0116=?");
			ps.setString(1, ynId);
			ps.setString(2, yn_type);
			ps.executeUpdate();
			// ���µ�ǰ����
			ps = conn.prepareStatement("update js2_yntp set yn_type=? where yn_id=?");
			ps.setString(1, yn_type);
			ps.setString(2, ynId);
			ps.executeUpdate();
			// ����
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

			// ɾ������
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

			//���� TPHJ1Html
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
				this.setMainMessage("����ʧ�ܣ�");
				e.printStackTrace();
			}
			if (e.getMessage() != null && e.getMessage().indexOf("ORA-00001") >= 0) {
				this.setMainMessage("����ʧ�ܣ���ȷ�϶�����û���ظ���Ա");
			} else {
				this.setMainMessage("����ʧ�ܣ�");
			}

			e.printStackTrace();
			return EventRtnType.NORMAL_SUCCESS;
		}

		if ("updateNRM".equals(a)) {
			this.setNextEventName("updateNRM");
		} else if ("savefirst".equals(a)) {
			this.getExecuteSG().addExecuteCode("uploadKCCL.openUploadKcclWin()");
		} else {
			this.getExecuteSG().addExecuteCode("Ext.example.msg('','����ɹ���',1);");
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
	
	// ����
	@SuppressWarnings("unchecked")
	@PageEvent("saveGD")
	public int saveGD(String text) throws Exception {
		String ynId = this.getPageElement("ynId").getValue();
		String yn_type = this.getPageElement("yntype").getValue();

		Js2Yntp rb = (Js2Yntp) HBUtil.getHBSession().get(Js2Yntp.class, ynId);
		if (rb == null) {
			this.setMainMessage("��������Ϣ��");
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
			// ����
			ps = conn.prepareStatement(JS2_YNTP_gdsql);
			ps.setString(1, yn_type);
			ps.setString(2, yn_gd_id);
			ps.setString(3, text);
			ps.setString(4, ynId);
			ps.executeUpdate();

			// ����
			ps = conn.prepareStatement(sql);
			ps.setString(1, yn_gd_id);
			ps.setString(2, ynId);
			ps.setString(3, yn_type);
			ps.executeUpdate();

			//���� TPHJ1Html
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
				this.setMainMessage("�浵ʧ�ܣ�");
				e.printStackTrace();
			}
			this.setMainMessage("�浵ʧ�ܣ�");
			e.printStackTrace();
			return EventRtnType.NORMAL_SUCCESS;
		}

		this.getExecuteSG().addExecuteCode("Ext.example.msg('','�浵�ɹ���',1);");

		return EventRtnType.NORMAL_SUCCESS;
	}

	// ����������
	@SuppressWarnings("unchecked")
	@PageEvent("updateNRM")
	public int updateNRM(String a0000p) throws Exception {
		String ynId = this.getPageElement("ynId").getValue();
		String yn_type = this.getPageElement("yntype").getValue();

		Js2Yntp rb = (Js2Yntp) HBUtil.getHBSession().get(Js2Yntp.class, ynId);
		if (rb == null) {
			this.setMainMessage("��������Ϣ��");
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
			this.setMainMessage("������������Ϣʧ�ܣ�");
			e.printStackTrace();
		}

		this.setMainMessage("������������Ϣ�ɹ���");

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
	 * ��Ϣ����
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("ExpTPB")
	public int ExpTPB(String param) throws Exception {
		try {

			YNTPFileExportBS fileBS = new YNTPFileExportBS(null);
			// String rbId = this.getPageElement("rbId").getValue();
			String ynId = this.request.getParameter("ynId");// ����
			String yntype = this.request.getParameter("yntype");// ����
			// String expType = this.request.getParameter("expType");//��������

			fileBS.setOutputpath(YNTPFileExportBS.HZBPATH + "zhgboutputfiles/" + ynId + "/");
			File f = new File(fileBS.getOutputpath());

			if (f.isDirectory()) {// ��������Ŀ¼
				JSGLBS.deleteDirectory(fileBS.getOutputpath());
			}

			fileBS.exportGBTP(ynId, yntype);

			List<String> fns = fileBS.getOutputFileNameList();
			String downloadPath = "";
			String downloadName = "";
			if (fns.size() == 1) {// һ���ļ�ֱ������
				downloadPath = fileBS.getOutputpath() + fns.get(0);
				downloadName = fns.get(0);
			} else if (fns.size() > 1) {// ѹ��
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
			this.setMainMessage("����ʧ��" + e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ���������word
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
			// String expType = this.request.getParameter("expType");//��������

			Js2Yntp rb = (Js2Yntp) HBUtil.getHBSession().get(Js2Yntp.class, ynId);
			if (rb == null) {
				this.setMainMessage("��������Ϣ��");
				return EventRtnType.NORMAL_SUCCESS;
			}

			fileBS.setOutputpath(YNTPFileExportBS.HZBPATH + "zhgboutputfiles/" + ynId + "/");
			File f = new File(fileBS.getOutputpath());

			if (f.isDirectory()) {// ��������Ŀ¼
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
			this.setMainMessage("����ʧ��" + e.getMessage());
		}
		this.setMainMessage("��ӡ���");
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
		if (cal.before(birthDay)) { // �����������ڵ�ǰʱ�䣬�޷�����
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}
		int yearNow = cal.get(Calendar.YEAR); // ��ǰ���
		int monthNow = cal.get(Calendar.MONTH); // ��ǰ�·�
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); // ��ǰ����
		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		int age = yearNow - yearBirth; // ����������
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth)
					age--;// ��ǰ����������֮ǰ�������һ
			} else {
				age--;// ��ǰ�·�������֮ǰ�������һ
			}
		}
		return age;
	}

	public static void main(String[] args) {
//        try {
//          int  age = getAge(parse("1990.09.27"));           //�ɳ������ڻ������***
//          System.out.println("age:"+age);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
		System.out.println("{/n}".replaceAll("��", "\n"));
    }
	
	@PageEvent("ExpTPBExcel")
	public int ExpTPBExcel(String param) throws Exception{
		System.out.println("�ļ���ʼ!"+(new Date()).toLocaleString());
		try { 
			YNTPFileExportBS fileBS = new YNTPFileExportBS(null);
			String ynId = this.request.getParameter("ynId");//����
			String yntype = this.request.getParameter("yntype");//����
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
			this.setMainMessage("�����ɹ���");
		}catch(Exception e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ��"+e.getMessage());
		} 
		System.out.println("�ļ����ɳɹ�!"+(new Date()).toLocaleString());
		return EventRtnType.NORMAL_SUCCESS;
	}
}