package com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fr.report.core.A.s;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.IRoleControl;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtAcl;
import com.insigma.odin.framework.privilege.entity.SmtAct;
import com.insigma.odin.framework.privilege.entity.SmtFunction;
import com.insigma.odin.framework.privilege.entity.SmtRole;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.util.BeanUtil;
import com.insigma.odin.framework.privilege.util.PrivilegeUtil;
import com.insigma.odin.framework.privilege.vo.FunctionVO;
import com.insigma.odin.framework.privilege.vo.RoleVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.tree.CheckTreeNode;
import com.insigma.odin.framework.tree.TreeNode;
import com.insigma.odin.framework.tree.TreeUtil;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.epsoft.util.Node;
import com.insigma.siis.local.epsoft.util.NodeUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.sysmanager.resource.resourcePageModel;
import com.insigma.siis.local.pagemodel.sysmanager.user.PsWindowPageModel;

import net.sf.json.JSONArray;

public class PersonFuncPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		this.setNextEventName("initX");
		return 0;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		String userid = this.getPageElement("subWinIdBussessId").getValue();
		if(userid == null||"".equals(userid)){
			this.setMainMessage("请先选择用户");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("userid").setValue(userid);
		HBSession sess = HBUtil.getHBSession();
		Object obj = sess.createSQLQuery("select t.username from smt_user t where t.userid = '"+userid+"' and t.useful = '1'").uniqueResult();
		if(obj!=null){
			this.getExecuteSG().addExecuteCode("document.getElementById('text11').innerText = '"+"当前用户 : "+obj+"';");
			this.getPageElement("username").setValue(obj.toString());
		}
		/*this.setNextEventName("contentList.dogridquery");
		this.setNextEventName("contentList2.dogridquery");*/
		this.setNextEventName("grid6.dogridquery");
		return 0;
	}
	
/*	@PageEvent("contentList.dogridquery")
	@NoRequiredValidate         
	public int contentList10(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String userid = this.getPageElement("userid").getValue();
		//获取上级管理员id
		String overUserid = NewRangePageModel.getOverUserID(userid);
		String sql = "";
		if("40288103556cc97701556d629135000f".equals(overUserid)){
			sql ="select f.functionid fid,f.title fname from Smt_Function f,Smt_Resource r where f.resourceid = r.resourceid and r.status='1' "
					+ "and r.parent = '40287681624b5ada01624b6cabb7000b' and r.type = '0' order by f.parent,f.orderno";
		}else{
			sql ="select f.functionid fid,f.title fname from Smt_Function f,Smt_Resource r, Competence_Usersmtbusiness c where f.resourceid = r.resourceid and r.status='1' "
					+ "and r.parent = '40287681624b5ada01624b6cabb7000b' and r.type = '0' and f.functionid = c.businessid and c.userid = '"+overUserid+"' order by f.parent,f.orderno";
		}
		CommonQueryBS.systemOut("contentList------->"+sql);
		this.pageQuery(sql, "SQL", start, limit); //基本不做翻页
		return EventRtnType.SPE_SUCCESS;
	}*/
	
	@PageEvent("contentList2.dogridquery")
	@NoRequiredValidate         
	public int contentList11(int start,int limit) throws RadowException, AppException{
		String userid = this.getPageElement("userid").getValue();
		String sql ="select s.functionid fid,s.title fname from COMPETENCE_USERSMTBUSINESS c,smt_function s where c.userid = '"+userid+"' and c.businessid = s.functionid";
		CommonQueryBS.systemOut("contentList2------->"+sql);
		this.pageQuery(sql, "SQL", start, limit); //基本不做翻页
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("contentList.rowclick")
	@GridDataRange(GridData.cuerow)//范围一行
	public int selectContent() throws RadowException{
		String subid = (String)this.getPageElement("contentList").getValue("fid");	
		String subname = (String)this.getPageElement("contentList").getValue("fname");
		this.getPageElement("fid").setValue(subid);
		this.getPageElement("fname").setValue(subname);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//添加
	@PageEvent("rigthB")
	public int rigthB() throws RadowException, AppException {
		String fid = this.getPageElement("fid").getValue();
		if (fid == null || "".equals(fid)) {
			return EventRtnType.NORMAL_SUCCESS;
		}
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("contentList");
		List<HashMap<String, Object>> list = pe.getValueList();// 查询人员列表
		List<HashMap<String, Object>> listSelect = this.getPageElement("contentList2").getValueList();// 选择人员列表
		for (HashMap<String, Object> hm : list) {
			if(fid.equals(hm.get("fid"))){
				addlist.add(hm);
			}
		}
		if(listSelect!=null && listSelect.size()>0){
			for (HashMap<String, Object> sel : listSelect) {
				if (fid.equals(sel.get("fid"))) {
					addlist.remove(sel);
				}
			}
		}
		//将数据添加到登记人员列表
		listSelect.addAll(addlist);
		String data= JSONArray.fromObject(listSelect).toString();
 		this.getPageElement("contentList2").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//全部增加
	@PageEvent("rigthBAll")
	public int rigthBAll() throws RadowException, AppException {
		List<HashMap<String, Object>> listSelect = this.getPageElement("contentList").getValueList();// 选择人员列表
		String data = JSONArray.fromObject(listSelect).toString();
		this.getPageElement("contentList2").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("contentList2.rowclick")
	@GridDataRange(GridData.cuerow)//范围一行
	public int contentList11() throws RadowException{
		String subid = (String)this.getPageElement("contentList2").getValue("fid");	
		String subname = (String)this.getPageElement("contentList2").getValue("fname");
		this.getPageElement("fid2").setValue(subid);
		this.getPageElement("fname2").setValue(subname);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//删除
	@PageEvent("liftB")
	public int liftB() throws RadowException, AppException {
		String subid = this.getPageElement("fid2").getValue();
		if (subid == null || "".equals(subid)) {
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		List<HashMap<String, Object>> removelist=new LinkedList<HashMap<String,Object>>();
		List<HashMap<String, Object>> listSelect = this.getPageElement("contentList2").getValueList();// 选择人员列表
		if (listSelect != null && listSelect.size() > 0) {
			for (HashMap<String, Object> sel : listSelect) {
				if (subid.equals(sel.get("fid"))) {
					removelist.add(sel);
				}
			}
		}
		//将数据添加到登记人员列表
		listSelect.removeAll(removelist);
		String data = JSONArray.fromObject(listSelect).toString();
		this.getPageElement("contentList2").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//全部删除
	@PageEvent("liftBAll")
	public int liftBAll() throws RadowException, AppException {
		List<HashMap<String, Object>> listSelect = this.getPageElement("contentList2").getValueList();// 选择人员列表
		listSelect.clear();
		String data = JSONArray.fromObject(listSelect).toString();
		this.getPageElement("contentList2").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//通用Word模板
	@PageEvent("webOfficJBMC")
	public int webOfficJBMC() throws RadowException, PrivilegeException {
		HBSession sess = HBUtil.getHBSession();
		String userid = this.getParameter("userid");
		//List<Object> objs = sess.createSQLQuery("select t.officeid from COMPETENCE_USERWEBOFFICE t where t.userid = '"+userid+"' and t.OFFICETYPE = '1' and t.type = '1'").list();

		StringBuffer jsonStr = new StringBuffer();
		List<Object[]> list = sess.createSQLQuery("select t.id,t.filename from WEBOFFICE t where t.type = '1'").list();
		if (list != null && list.size() > 0) {
			jsonStr.append("[{\"text\" :\"通用Word模板输出\" ,\"id\" :\"jbmc\" ,\"cls\" :\"folder\" ,\"checked\" :true");
			jsonStr.append(",\"children\" :[");
			for(Object[] obj : list){
				String id = ""+obj[0];
				String fileName = ""+obj[1];
				//System.out.println(objs.contains(id));
				jsonStr.append("{\"text\" :\""
						+ fileName
						+ "\" ,\"id\" :\""
						+ id
						+ "\" ,\"leaf\" :\"true\" ,\"cls\" :\"folder\" ,\"checked\" : true},");
			}
			jsonStr.deleteCharAt(jsonStr.length()-1);
			jsonStr.append("]}]");
		} else {
			jsonStr.append("{}");
		}
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}

	// 通用Excel模板
	@PageEvent("webOfficDWMC")
	public int webOfficDWMC() throws RadowException, PrivilegeException {
		HBSession sess = HBUtil.getHBSession();
		String userid = this.getParameter("userid");
		//List<Object> objs = sess.createSQLQuery("select t.officeid from COMPETENCE_USERWEBOFFICE t where t.userid = '"+userid+"' and t.OFFICETYPE = '2' and t.type = '1'").list();

		StringBuffer jsonStr = new StringBuffer();
		List<Object[]> list = sess.createSQLQuery(
				"select t.id,t.filename from WEBOFFICE t where t.type = '2'")
				.list();
		if (list != null && list.size() > 0) {
			jsonStr.append("[{\"text\" :\"通用Excel模板输出\" ,\"id\" :\"dwmc\" ,\"cls\" :\"folder\" ,\"checked\" :true");
			jsonStr.append(",\"children\" :[");
			for (Object[] obj : list) {
				String id = "" + obj[0];
				String fileName = "" + obj[1];

				jsonStr.append("{\"text\" :\""
						+ fileName
						+ "\" ,\"id\" :\""
						+ id
						+ "\" ,\"leaf\" :\"true\" ,\"cls\" :\"folder\" ,\"checked\" : true},");
			}
			jsonStr.deleteCharAt(jsonStr.length() - 1);
			jsonStr.append("]}]");
		} else {
			jsonStr.append("{}");
		}
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}

	// WORD
	@PageEvent("webOfficWORD")
	public int webOfficWORD() throws RadowException, PrivilegeException {
		HBSession sess = HBUtil.getHBSession();
		String userid = this.getParameter("userid");
		List<Object> objs = sess.createSQLQuery("select t.officeid from COMPETENCE_USERWEBOFFICE t where t.userid = '"+userid+"' and t.OFFICETYPE = '3' and t.type = '1'").list();

		UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
		String userVoId = userVo.getId();
		StringBuffer jsonStr = new StringBuffer();
		//List<Object[]> list = sess.createSQLQuery("select t.id,t.filename from WEBOFFICE t where t.type = '3'").list();
		String sql = "";
		if("40288103556cc97701556d629135000f".equals(userVoId)){
			sql = "select w.id,w.filename from WEBOFFICE w where w.type = '3' and w.userid = '40288103556cc97701556d629135000f'";
		}else{
			sql = "select w.id,w.filename from WEBOFFICE w where w.type = '3' and exists "
					+ "(select 1 from COMPETENCE_USERWEBOFFICE t where t.userid = '"+userVoId+"' "
					+ "and t.OFFICETYPE = '3' and t.type = '1' and w.id = t.officeid)";
		}
		List<Object[]> list = sess.createSQLQuery(sql).list();
		if (list != null && list.size() > 0) {
			jsonStr.append("[{\"text\" :\"自定义word表格输出\" ,\"id\" :\"word\" ,\"cls\" :\"folder\" ,\"checked\" :"+((objs==null)||(objs!=null&&objs.size()==0)?false:true)+"");
			jsonStr.append(",\"children\" :[");
			for (Object[] obj : list) {
				String id = "" + obj[0];
				String fileName = "" + obj[1];

				jsonStr.append("{\"text\" :\""
						+ fileName
						+ "\" ,\"id\" :\""
						+ id
						+ "\" ,\"leaf\" :\"true\" ,\"cls\" :\"folder\" ,\"checked\" : "+((objs.contains(id))?true:false)+"},");
			}
			jsonStr.deleteCharAt(jsonStr.length() - 1);
			jsonStr.append("]}]");
		} else {
			jsonStr.append("{}");
		}
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
	// Excel
	@PageEvent("webOfficExcel")
	public int webOfficExcel() throws RadowException, PrivilegeException {
		HBSession sess = HBUtil.getHBSession();
		String userid = this.getParameter("userid");
		List<Object> objs = sess
				.createSQLQuery(
						"select t.officeid from COMPETENCE_USERWEBOFFICE t where t.userid = '"
								+ userid + "' and t.OFFICETYPE = '4' and t.type = '1'").list();

		UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
		String userVoId = userVo.getId();
		StringBuffer jsonStr = new StringBuffer();
		//List<Object[]> list = sess.createSQLQuery("select t.id,t.filename from WEBOFFICE t where t.type = '4'").list();
		String sql = "";
		if("40288103556cc97701556d629135000f".equals(userVoId)){
			sql = "select w.id,w.filename from WEBOFFICE w where w.type = '4' and w.userid = '40288103556cc97701556d629135000f'";
		}else{
			sql = "select w.id,w.filename from WEBOFFICE w where w.type = '4' and exists "
					+ "(select 1 from COMPETENCE_USERWEBOFFICE t where t.userid = '"+userVoId+"' "
					+ "and t.OFFICETYPE = '4' and t.type = '1' and w.id = t.officeid)";
		}
		List<Object[]> list = sess.createSQLQuery(sql).list();
		if (list != null && list.size() > 0) {
			jsonStr.append("[{\"text\" :\"自定义excel表格输出\" ,\"id\" :\"excel\" ,\"cls\" :\"folder\" ,\"checked\" :"+((objs==null)||(objs!=null&&objs.size()==0)?false:true)+"");
			jsonStr.append(",\"children\" :[");
			for (Object[] obj : list) {
				String id = "" + obj[0];
				String fileName = "" + obj[1];

				jsonStr.append("{\"text\" :\""
						+ fileName
						+ "\" ,\"id\" :\""
						+ id
						+ "\" ,\"leaf\" :\"true\" ,\"cls\" :\"folder\" ,\"checked\" : "
						+ ((objs.contains(id)) ? true : false) + "},");
			}
			jsonStr.deleteCharAt(jsonStr.length() - 1);
			jsonStr.append("]}]");
		} else {
			jsonStr.append("{}");
		}
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
	/*// 视图
	@PageEvent("qryview")
	public int qryview() throws RadowException, PrivilegeException {
		HBSession sess = HBUtil.getHBSession();
		String userid = this.getParameter("userid");
		List<Object> objs = sess.createSQLQuery("select t.viewid from COMPETENCE_USERQRYVIEW t where t.userid = '"+userid+"' and t.type = '1'").list();
		
		StringBuffer jsonStr = new StringBuffer();
		jsonStr.append("[");
		//新增对 人员管理 各种查询方式的权限管理
		List<String> li = new ArrayList<String>();
		
		//获取上级管理员id
		String overUserid = NewRangePageModel.getOverUserID(userid);
		if("40288103556cc97701556d629135000f".equals(overUserid)){
			li.add("常用条件查询");li.add("姓名/身份证查询");li.add("按机构查询");li.add("组合查询");
			li.add("自定义查询");li.add("查询结果列表");li.add("SQL查询");
			
			if (li != null && li.size() > 0) {
				jsonStr.append("{\"text\" :\"人员查询\" ,\"id\" :\"rycx\" ,\"cls\" :\"folder\" ,\"checked\" :"+((objs==null)||(objs!=null&&objs.size()==0)?false:true)+"");
				jsonStr.append(",\"children\" :[");
				for (int i=0;i<li.size();i++) {
					jsonStr.append("{\"text\" :\""
							+ li.get(i)
							+ "\" ,\"id\" :\""
							+ "CX0" + (i+1)
							+ "\" ,\"leaf\" :\"true\" ,\"cls\" :\"folder\" ,\"checked\" : "+((objs.contains("CX0" + (i+1)))?true:false)+"},");
				}
				jsonStr.deleteCharAt(jsonStr.length() - 1);
				jsonStr.append("]},");
			}
		}else{			
			List<Object> overObjs = sess.createSQLQuery("select t.viewid from COMPETENCE_USERQRYVIEW t where t.userid = '"+overUserid+"' and t.type = '1'").list();

			Map<String, String> map = getMap();
			if(overObjs!=null&&overObjs.size()>0){
				if(overObjs.contains("CX01")){
					li.add("常用条件查询");
				}
				if(overObjs.contains("CX02")){
					li.add("姓名/身份证查询");
				}
				if(overObjs.contains("CX03")){
					li.add("按机构查询");
				}
				if(overObjs.contains("CX04")){
					li.add("组合查询");
				}
				if(overObjs.contains("CX05")){
					li.add("自定义查询");
				}
				if(overObjs.contains("CX06")){
					li.add("查询结果列表");
				}
				if(overObjs.contains("CX07")){
					li.add("SQL查询");
				}
				
				if (li != null && li.size() > 0) {
					jsonStr.append("{\"text\" :\"人员查询\" ,\"id\" :\"rycx\" ,\"cls\" :\"folder\" ,\"checked\" :"+((objs==null)||(objs!=null&&objs.size()==0)?false:true)+"");
					jsonStr.append(",\"children\" :[");
					for (int i=0;i<li.size();i++) {
						jsonStr.append("{\"text\" :\""
								+ li.get(i)
								+ "\" ,\"id\" :\""
								+ map.get(li.get(i))
								+ "\" ,\"leaf\" :\"true\" ,\"cls\" :\"folder\" ,\"checked\" : "+((objs.contains((map.get(li.get(i)))))?true:false)+"},");
					}
					jsonStr.deleteCharAt(jsonStr.length() - 1);
					jsonStr.append("]},");
				}
			}
		}
		
		
		//自定义视图
		String sql = "";
		if("40288103556cc97701556d629135000f".equals(overUserid)){
			sql = "select q.qvid,q.chinesename from qryview q where q.type = '1'";
		}else{
			sql = "select q.qvid,q.chinesename from qryview q,competence_userqryview c "
					+ "where q.qvid = c.viewid and c.userid = '"+overUserid+"' and q.type = '1'";
		}
		List<Object[]> list = sess.createSQLQuery(sql).list();
		if (list != null && list.size() > 0) {
			jsonStr.append("{\"text\" :\"自定义视图\" ,\"id\" :\"zdyview\" ,\"cls\" :\"folder\" ,\"checked\" :"+((objs==null)||(objs!=null&&objs.size()==0)?false:true)+"");
			jsonStr.append(",\"children\" :[");
			for (Object[] obj : list) {
				String id = "" + obj[0];
				String chinesename = "" + obj[1];

				jsonStr.append("{\"text\" :\""
						+ chinesename
						+ "\" ,\"id\" :\""
						+ id
						+ "\" ,\"leaf\" :\"true\" ,\"cls\" :\"folder\" ,\"checked\" : "+((objs.contains(id))?true:false)+"},");
			}
			jsonStr.deleteCharAt(jsonStr.length() - 1);
			jsonStr.append("]},");
		} 
		// 组合视图
		List<Object[]> l = sess
				.createSQLQuery(
						"select q.qvid,q.chinesename from qryview q where q.type = '2'")
				.list();
		if (l != null && l.size() > 0) {
			jsonStr.append("{\"text\" :\"组合视图\" ,\"id\" :\"zhview\" ,\"cls\" :\"folder\" ,\"checked\" :"+((objs==null)||(objs!=null&&objs.size()==0)?false:true)+"");
			jsonStr.append(",\"children\" :[");
			for (Object[] obj : l) {
				String id = "" + obj[0];
				String chinesename = "" + obj[1];

				jsonStr.append("{\"text\" :\""
						+ chinesename
						+ "\" ,\"id\" :\""
						+ id
						+ "\" ,\"leaf\" :\"true\" ,\"cls\" :\"folder\" ,\"checked\" : "+((objs.contains(id))?true:false)+"},");
			}
			jsonStr.deleteCharAt(jsonStr.length() - 1);
			jsonStr.append("]},");
		}
		jsonStr.deleteCharAt(jsonStr.length() - 1);
		jsonStr.append("]");

		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
	private Map<String, String> getMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("常用条件查询", "CX01");
		map.put("姓名/身份证查询", "CX02");
		map.put("按机构查询", "CX03");
		map.put("组合查询", "CX04");
		map.put("自定义查询", "CX05");
		map.put("查询结果列表", "CX06");
		map.put("SQL查询", "CX07");
		return map;
	}

		// statistics
		@PageEvent("statistics")
		public int statistics() throws RadowException, PrivilegeException {
			HBSession sess = HBUtil.getHBSession();
			String userid = this.getParameter("userid");
			List<Object> objs = sess.createSQLQuery("select t.tableid from COMPETENCE_USERTABLEINFO t where t.userid = '"+userid+"' and t.type = '1'").list();
			
			//获取上级管理员id
			String overUserid = NewRangePageModel.getOverUserID(userid);
			String sql = "";
			if("40288103556cc97701556d629135000f".equals(overUserid)){
				sql = "select r.rptid,r.rptname,(select count(*) from tableinfo t where r.rptid = t.rptid and t.tstatus = '1') sum"
					+ " from rptinfo r where r.userid = '40288103556cc97701556d629135000f' order by r.sorts";
			}else{
				sql = "select r.rptid,r.rptname,(select count(*) from tableinfo t,competence_usertableinfo c"
						+ " where r.rptid = t.rptid and t.tstatus = '1'"
						+ " and c.tableid = t.tableid and c.userid = '"+overUserid+"') sum from rptinfo r order by r.sorts";
			}
			
			StringBuffer jsonStr = new StringBuffer();
			List<Object[]> list = sess.createSQLQuery(sql).list();
			if (list != null && list.size() > 0) {
				jsonStr.append("[");
				for (Object[] obj : list) {
					String rptid = ""+obj[0];
					String rptname = ""+obj[1];
					int n = Integer.parseInt(""+obj[2]);
					jsonStr.append("{\"text\" :\""+rptname+"\" ,\"id\" :\""+rptid+"\" ,\"cls\" :\"folder\" ,\"checked\" :true"+(n>0?"":",\"leaf\" :\"true\""));
					if(n>0){//有下级节点
						jsonStr.append(",\"children\" :[");
						String sql2 = "";
						if("40288103556cc97701556d629135000f".equals(overUserid)){
							sql2 = "select t.tableid,t.tablename from tableinfo t where t.rptid = '"+rptid+"' and t.tstatus = '1'";
						}else{
							sql2 = "select t.tableid,t.tablename from tableinfo t,competence_usertableinfo c where t.rptid = '"+rptid+"' and t.tstatus = '1' "
									+ " and c.tableid = t.tableid and c.userid = '"+overUserid+"'";
						}
						List<Object[]> l = sess.createSQLQuery(sql2).list();
						for(Object[] o : l){
							String tableid = ""+o[0];
							String tablename = ""+o[1];
							
							jsonStr.append("{\"text\" :\""
									+ tablename
									+ "\" ,\"id\" :\""
									+ tableid
									+ "\" ,\"leaf\" :\"true\" ,\"cls\" :\"folder\" ,\"checked\" : "+((objs.contains(tableid))?true:false)+"},");
						}
						jsonStr.deleteCharAt(jsonStr.length() - 1);
						jsonStr.append("]},");
					}else{
						jsonStr.append("},");
					}
				}
				jsonStr.deleteCharAt(jsonStr.length() - 1);
				jsonStr.append("]");
			} else {
				jsonStr.append("{}");
			}
			this.setSelfDefResData(jsonStr.toString());
			return EventRtnType.XML_SUCCESS;
		}*/

		
	@SuppressWarnings("unchecked")
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws RadowException, PrivilegeException {
		String curuserid = SysManagerUtils.getUserId();
		String node = this.getParameter("node");
		Object object = request.getSession().getAttribute("roleid");
		String roleid = "";
		if(object!=null){
			roleid = object.toString();
		}
		//String roleid =this.getParameter("roleid");
		//String roleid = this.getPageElement("roleId").getValue();
		List functionlist = new ArrayList();
		functionlist = getUserFunctions(SysUtil.getCacheCurrentUser().getUserVO(), SysUtil.getCacheCurrentUser().getSceneVO().getSceneid(), false);
		for(int i=0;i<functionlist.size();i++){
			FunctionVO function =(FunctionVO) functionlist.get(i);
			String parent = function.getParent();
			String name = function.getTitle();
			if(function.getType().equals("2")){
				functionlist.remove(i);
				i--;
			}else{
				//系统管理菜单不可见，不可授权
				if(parent!=null){
					if(parent.equals("S010000")&&!"40288103556cc97701556d629135000f".equals(curuserid)){
						functionlist.remove(i);
						i--;
					}
				}
				if(function.getFunctionid().equals("S010000")&&!"40288103556cc97701556d629135000f".equals(curuserid)){
					functionlist.remove(i);
					i--;
				}
				if(name!=null){
//					if(name.equals("系统管理")){
//						functionlist.remove(i);
//						i--;
//					}
				}
				
				
				//“模拟调配、换届选举、工作督办”模块标为内部
				if("01".equals(resourcePageModel.mmbs.get(function.getFunctionid()))){
					function.setTitle(function.getTitle()+"（内部）");
				}else if("0".equals(resourcePageModel.mmbs.get(function.getFunctionid()))){
					function.setTitle(function.getTitle()+"（秘密）");
				}
				
			}
		}
		List<TreeNode> root = PrivilegeManager.getInstance().getIResourceControl().getUserFunctionsTree(functionlist,roleid, node, true);
		this.setSelfDefResData(JSONArray.fromObject(root));
		return EventRtnType.XML_SUCCESS;
		/*String node = this.getParameter("node");
		String userid = this.getParameter("userid");
		//获取上级管理员id
		String overUserid = NewRangePageModel.getOverUserID(userid);
		//List functionlist2 = PrivilegeManager.getInstance().getIResourceControl().getUserFunctions(SysUtil.getCacheCurrentUser().getUserVO(), SysUtil.getCacheCurrentUser().getSceneVO().getSceneid(), false);//SysUtil.getCacheCurrentUser().getFunctionList();//PrivilegeManager.getInstance().getIResourceControl().findByParentId(node);
		UserVO user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(userid);
		List functionlist = new ArrayList();
		functionlist = getUserFunctions(user,overUserid);
		for(int i=0;i<functionlist.size();i++){
			FunctionVO function =(FunctionVO) functionlist.get(i);
			String parent = function.getParent();
			String name = function.getTitle();
			if(function.getType().equals("2")){
				functionlist.remove(i);
				i--;
			}
		}
		//List<TreeNode> root = PrivilegeManager.getInstance().getIResourceControl().getUserFunctionsTree(functionlist,userid, node, true);
		List<TreeNode> root = getUserFunctionsTree(functionlist,userid, node, true);
		this.setSelfDefResData(JSONArray.fromObject(root));
		return EventRtnType.XML_SUCCESS;*/
	}
	
	@PageEvent("dogrant")
	@Transaction
	public void save(String value) throws RadowException {
		String userid = this.getPageElement("userid").getValue();
		HBSession sess = HBUtil.getHBSession();
		
		//6.功能权限关联
		//获得该user已经有的功能权限
		List<Object> list = sess.createSQLQuery("select t.resourceid from smt_userfunction t where t.userid = '"+userid+"'").list();
		//将已经授权的list保留
		List<Object> oldList = new ArrayList<Object>();
		oldList.addAll(list);
		
		String[] nodes = null;
		HashMap<String,String> nodemap = new HashMap<String,String>();
		if(value !=null) {
			nodes = value.split(",");
			for(int i=0;i<nodes.length;i++) {
				String functionid = nodes[i].split(":")[0];
				Object o = sess.createSQLQuery("select t.type from smt_function t where t.functionid = '"+functionid+"'").uniqueResult();
				if("true".equals(nodes[i].split(":")[1])){
					if("1".equals(o)){//节点
						//如果是节点，将节点下的所有按钮权限加入list中
						List<Object> ids = sess.createSQLQuery("select t.functionid from smt_function t,smt_resource r where t.resourceid = r.resourceid "
						+ "and r.status = '1' and t.type = '2' and t.parent = '"+functionid+"'").list();
						if(ids!=null&&ids.size()>0){
							for(Object obj : ids){
								list.add(""+obj);
							}
						}
					}
					if("0".equals(o)){//叶子
						saveFunction(sess,list,functionid,"add");
					}
					//添加节点本身
					list.add(functionid);
				}else{
					if("1".equals(o)){//节点
						//如果是节点，将节点下的所有按钮权限从list中去除
						List<Object> ids = sess.createSQLQuery("select t.functionid from smt_function t,smt_resource r where t.resourceid = r.resourceid "
						+ "and r.status = '1' and t.type = '2' and t.parent = '"+functionid+"'").list();
						if(ids!=null&&ids.size()>0){
							for(Object obj : ids){
								list.remove(""+obj);
							}
						}
					}
					if("0".equals(o)){
						saveFunction(sess,list,functionid,"del");
					}
					list.remove(functionid);
				}
			}
		}
		//新增公共管理的内容  GG00000
		saveFunction(sess,list,"GG00000","add");
		
		list.add("S000000");//业务菜单
		//list去重
		List newList = new ArrayList(new TreeSet(list)); 
		
		//取出oldList有的，newList里没有的元素
		oldList.removeAll(newList);
		
		if(newList!=null&&newList.size()>0){
			//先删除smt_smt_userfunction下的全部授权
			sess.createSQLQuery("delete from smt_userfunction where userid = '"+userid+"'").executeUpdate();
			//再重新添加
			for(Object obj : newList){
				sess.createSQLQuery("insert into smt_userfunction values (sys_guid() ,'"+userid+"' ,'"+obj+"' ,null)").executeUpdate();
			}
		}
		if(oldList!=null&&oldList.size()>0){
			Object obj = sess.createSQLQuery("select s.isleader from smt_user s where s.userid = '"+userid+"'").uniqueResult();
			if(obj!=null&&"1".equals(obj)){
				NewRangePageModel np = new NewRangePageModel();
				//去除下级的菜单权限
				np.minusQX2(sess, userid, "权限", "smt_userfunction", "RESOURCEID",oldList);
			}
		}
		this.setMainMessage("授权成功！");
	}

	public static void saveFunction(HBSession sess, List<Object> list, String functionid,String type) {
		// TODO Auto-generated method stub
		if("add".equals(type)){
			list.add(functionid);
		}else if("del".equals(type)){
			list.remove(functionid);
		}
		List<Object> ids = sess.createSQLQuery("select t.functionid from smt_function t,smt_resource r where t.resourceid = r.resourceid "
				+ "and r.status = '1' and t.parent = '"+functionid+"'").list();
		if(ids!=null&&ids.size()>0){
			for(Object obj : ids){
				saveFunction(sess,list,""+obj,type);
			}
		}
	}

	/**
	 * 	查询用户可以操作的授权
	 * @param paramUserVO
	 * @param paramString
	 * @param paramBoolean
	 * @return
	 * @throws PrivilegeException
	 */
	public List<Object> getUserFunctions(UserVO paramUserVO,String overUserId) throws PrivilegeException{
		HBSession sess = HBUtil.getHBSession();
	    SmtUser localSmtUser = (SmtUser)sess.get(SmtUser.class, paramUserVO.getId());
	    UserVO localUserVO = new UserVO();
	    BeanUtil.propertyCopy(localSmtUser, localUserVO);
	    String sql = "";
	    Object localObject = new ArrayList();
	    if("40288103556cc97701556d629135000f".equals(overUserId)){
	    	if("2".equals(localUserVO.getUsertype())){//普通用户,没有  系统管理  这一节点
	    		sql = "select f from SmtFunction f,SmtResource r where f.resourceid = r.id and r.status='1' and f.functionid <> 'S010000' order by f.parent,f.orderno";
	    	}else{
		    	sql = "select f from SmtFunction f,SmtResource r where f.resourceid = r.id and r.status='1' order by f.parent,f.orderno";
	    	}
	    }else{
	    	/*if("2".equals(localUserVO.getUsertype())){//普通用户,没有  系统管理  这一节点
	    		sql = "select f from SmtFunction f,SmtResource r,SmtUserfunction s where f.resourceid = r.id and r.status='1' and f.functionid <> 'S010000'"
		    			+ " and f.resourceid = s.resourceid and s.userid = '"+overUserId+"' order by f.parent,f.orderno";
	    	}else{*/
	    		sql = "select f from SmtFunction f,SmtResource r,SmtUserfunction s where f.resourceid = r.id and r.status='1' and f.functionid <> 'S010000'"
		    			+ " and f.resourceid = s.resourceid and s.userid = '"+overUserId+"' order by f.parent,f.orderno";
	    	/*}*/
	    }
    	localObject = sess.createQuery(sql).list();
	    return ((List<Object>)PrivilegeUtil.getVOList((List)localObject, FunctionVO.class));
	}
	
	@SuppressWarnings("unchecked")
	public List<TreeNode> getUserFunctionsTree(List<Object> funcs,String roleid,String nodeId,boolean isLoadAll){
		Collections.sort(funcs,new Comparator(){
			public int compare(Object arg0, Object arg1) {
				if(arg0 instanceof FunctionVO && arg1 instanceof FunctionVO) {
					FunctionVO func1 =  (FunctionVO) arg0;
					FunctionVO func2 =  (FunctionVO) arg1;
					int fn1=func1.getOrderno().intValue();
					int fn2=func2.getOrderno().intValue();
					int num=0;
					
					if(fn1>fn2){
						num=1;
					}
					if(fn1<fn2){
						num=-1;
					}
					if(fn1==fn2){
						num=0;
					}
					return num;
				}
				return 0;
		}});
		/*String acl = "select s.resourceid from SMT_USERFUNCTION s where s.userid='"+userid+"'";
		HBSession sess = HBUtil.getHBSession();
		List aclList = sess.createSQLQuery(acl).list();*/
		String acl = "select a.function from smt_role r left join smt_acl a on r.roleid = a.roleid where r.roleid='402881ff5e7a074c015e7a5481300579'";
		HBSession sess = HBUtil.getHBSession();
		List aclList = sess.createSQLQuery(acl).list();
		List<TreeNode> root = transData(funcs,aclList,isLoadAll);
		return root;
	}
	
	private List<TreeNode> transData(List<Object> list ,List aclList, boolean isLoadAll){
		List<TreeNode>  treeNode = new ArrayList<TreeNode>();
		for(Iterator<Object> it = list.iterator();it.hasNext();){
			FunctionVO g = (FunctionVO) it.next();
			CheckTreeNode n = new CheckTreeNode();
			n.setId(g.getFunctionid());
			n.setText(g.getTitle());
			n.setParent(g.getParent());
			n.setChecked(false);
			if(aclList!=null){
				for(int i=0;i<aclList.size();i++) {
					if(g.getFunctionid().equals(aclList.get(i))){
						n.setChecked(true);
						//break;
					}
				}
			}
			
			if(!isLoadAll){
				for(Iterator<Object> it2 = list.iterator();it2.hasNext();){
					FunctionVO f = (FunctionVO) it2.next();
					if(g.getFunctionid().equals(f.getParent())){
						n.setLeaf(false);
						break;
					}
				}
			}
			treeNode.add(n);
		}
		if(isLoadAll){
			treeNode = TreeUtil.buildTree(treeNode);
		}
		return treeNode;
	}
	
	
	
	//------------------------------------sucf--------------------
	
	/**
	 * 角色增加
	 */
	@PageEvent("addRole")
	public int addRole() throws RadowException {
		this.openWindow("roleWindow", "pages.cadremgn.sysmanager.authority.NewRole");
		this.setRadow_parent_data("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("grid6.dogridquery")
//	@EventDataCustomized("roleQName,roleOwner,roleQDesc")
	public int dogrid6Query(int start, int limit) throws RadowException{
		String userid = this.getPageElement("userid").getValue();
		String curuserid = SysManagerUtils.getUserId();
		boolean ck = PsWindowPageModel.get3YCheck();
//		String sql = "  select nvl2(sa.actid,'true','false') rolecheck,sr.roleid, sr.roledesc,sr.rolename from smt_role sr "+
//					"left join smt_act sa "+
//					"on sr.roleid=sa.roleid and sa.userid='"+userid+"'";
		String sql ="select a.* "+
  "from (select nvl2(sa.actid, 'true', 'false') rolecheck,"+
              " sr.roleid,"+
               "sr.roledesc,"+
               "sr.rolename,"+
               "sr.sortid "+
          "from smt_role sr "+
          "left join smt_act sa "+
            "on sr.roleid = sa.roleid "+
           "and sa.userid = '"+userid+"') a "+
 "where a.roleid not in ("+
 					   " '402881e456498d9601564a2ccde004c0',"+//全部普通用户模块
                       " '4028c60c25335f86012533767f3c0002',"+//系统管理员
                       " '402881f35e79fa19015e79ff43f1000b',"+//机构信息模块
                       " '402881f35e79fa19015e79ff85ef000d',"+//人员信息模块
                       " '402881f35e79fa19015e79ffcc74000f',"+//模块管理模块
                       " '402881f35e79fa19015e7a000cd70011',"+//信息校核模块
                       " '402881f35e79fa19015e7a00b4190015',"+//系统管理模块
                       " '402881ff5e7a074c015e7a48748a02f6',"+//数据接收
                       " '402881ff5e7a074c015e7a4cb9cf0482',"+//数据上报
                       " '402881ff5e7a074c015e7a51053304f5',"+//ZZB3数据接收
                       " '402881ff5e7a074c015e7a53bddd04f7',"+//管理员
                       " '402881ff5e7a074c015e7a5481300579',"+//管理员
                       " '402881ff5e7a074c015e7a54d8ae057b',"+//管理员
                       
                       
("40288103556cc97701556d629135000f".equals(curuserid)?"":" '402882846f6a49c9016f6a9529cd0031',"+//安全审计员(三元)
" '402882846f6a49c9016f6a945a9c002d',"+//系统管理员(三元)
" '402882846f6a49c9016f6a94cc46002f',"+//安全管理员(三元)
" '297e9b3367154ab201671653bd9c057b',"//所有权限
)
                       
                   +    
                       " '402881ff5e7a074c015e7a5553ab05cc')"+
 "order by a.sortid";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 	查询用户可以操作的授权
	 * @param paramUserVO
	 * @param paramString
	 * @param paramBoolean
	 * @return
	 * @throws PrivilegeException
	 */
	public List<Object> getUserFunctions(UserVO paramUserVO, String paramString, boolean paramBoolean) throws PrivilegeException{
		
		HBSession sess = HBUtil.getHBSession();
		PrivilegeManager localPrivilegeManager = PrivilegeManager.getInstance();
	    SmtUser localSmtUser = (SmtUser)sess.get(SmtUser.class, paramUserVO.getId());
	    UserVO localUserVO = new UserVO();
	    BeanUtil.propertyCopy(localSmtUser, localUserVO);
	    String sql = "";
	    HashMap<String, Object> hashmap = new HashMap();
	    
	    if (localUserVO.getId().equals(GlobalNames.sysConfig.get("SUPER_ID")))
	    {
	      localUserVO.setLoginname(paramUserVO.getLoginname());
	      localUserVO.setPasswd(paramUserVO.getPasswd());
	    }
	    Object localObject = new ArrayList();
	    String str = "select t from SmtAct t,SmtAcl l where (t.objectid='" + localUserVO.getId() + "' or t.objectid in (select ug.groupid from SmtUsergroupref ug where ug.userid='" + localUserVO.getId() + "')) and  t.roleid=l.roleid and l.resourceid='RESOURCE_ALL'";
	    int i = 0;
	    List list_smtact = sess.createQuery(str).list();
	    if (list_smtact.size() > 0)
	      i = 1;
	    if ((!(localPrivilegeManager.getIPermission().isSuperManager(localUserVO))) && (!(localPrivilegeManager.getIPermission().isSysPermission(localUserVO, "RESOURCE_ALL"))) && (i == 0))
	    {
	      List localList = localPrivilegeManager.getIGroupControl().getGroupsByUserId(paramUserVO.getId());
	      if ((localList == null) || (localList.size() == 0))
	    	  sql = "select distinct f from SmtFunction f,SmtResource r,SmtAct t where f.resourceid = r.id and r.status='1' and t.objectid=:userID order by f.parent,f.orderno";
	      else
	    	  sql = "select distinct f from SmtFunction f,SmtResource r,SmtAct t,SmtUsergroupref ug where f.resourceid = r.id and r.status='1'  and (t.objectid=:userID or (ug.userid=:userID and t.objectid = ug.groupid) ) order by f.parent,f.orderno";
	      hashmap.put("userID", localUserVO.getId());
	      localObject = sess.createQuery(sql).setParameter("userID", localUserVO.getId()).list();
	    }
	    else
	    {
	    	sql = "select f from SmtFunction f,SmtResource r where f.resourceid = r.id and r.status='1' order by f.parent,f.orderno";
	      localObject = sess.createQuery(sql).list();
	    }
	    return ((List<Object>)PrivilegeUtil.getVOList((List)localObject, FunctionVO.class));
	}
	
	@PageEvent("grid6.rowclick")
	@GridDataRange(GridData.cuerow)
	public int gridClick() throws RadowException {
		Grid grid6 = (Grid) this.getPageElement("grid6");
		String roleid = grid6.getValue("roleid").toString();
		request.getSession().setAttribute("roleid", roleid);
		this.getPageElement("roleId").setValue(roleid);
		this.getExecuteSG().addExecuteCode("Treeload()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("save")
	@Transaction
	public int save_f(String value) throws Exception{
		String saveparams = value;
		String[] saveparam = value.split("@");
		String roleid = saveparam[1];
		value = saveparam[0];
		
		String[] nodes = null;
		HashMap<String,String> nodemap = new HashMap<String,String>();
		if(value !=null) {
			nodes = value.split(",");
			for(int i=0;i<nodes.length;i++) {
				nodemap.put(nodes[i].split(":")[0], nodes[i].split(":")[1]);
			}
		}
		StringBuffer addresourceIds = new StringBuffer();
		StringBuffer removeresourceIds = new StringBuffer();
		for(String node :nodemap.keySet()) {
			if(nodemap.get(node).equals("true")) {
				addresourceIds.append(node+",");
			}else if(nodemap.get(node).equals("false")) {
				removeresourceIds.append(node+",");
			}
		}
		/*List<HashMap<String,Object>> functionlist = this.getPageElement("resourcegrid").getValueList();
		for(int i=0;i<functionlist.size();i++) {
			if(functionlist.get(i).get("logchecked").equals(true)) {
				addresourceIds.append(functionlist.get(i).get("functionid")+",");
			}else {
				removeresourceIds.append(functionlist.get(i).get("functionid")+",");
			}
		}*/
		
		addresourceIds.append("S000000");
		String add = addresourceIds.toString();
		String remove = removeresourceIds.toString();
		//System.out.println("-------------"+add);
		//System.out.println("-------------"+remove);
		//System.out.println(remove);
		addResource(roleid,add);
		removeResource(roleid,remove);
		/*try {
			PrivilegeManager.getInstance().getIRoleControl().addResousesToRole(roleid, addresourceIds.toString());
			PrivilegeManager.getInstance().getIRoleControl().removeResourcesFromRole(roleid, removeresourceIds.toString());
		} catch (PrivilegeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		HBSession sess = HBUtil.getHBSession();
		String hql = "From SmtRole S where S.id = '"+roleid+"'";
		SmtRole user =(SmtRole) sess.createQuery(hql).list().get(0);
		List<String[]> list = new ArrayList<String[]>();
		String[] arr1 = {"roleids", "保存时参数", saveparams, "roleids"};
		list.add(arr1);
		try {
			new LogUtil().createLogNew(roleid,"角色功能授权","SMT_ACL",roleid,user.getName(), list);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getExecuteSG().addExecuteCode("Treeload()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	private IRoleControl rc = PrivilegeManager.getInstance().getIRoleControl();
	/**
	 *  角色管理页面数据项修改
	 * @return
	 * @throws RadowException
	 * @throws SQLException
	 */
	@PageEvent("grid6.afteredit")
	@GridDataRange(GridData.cuerow)
	@Transaction
	@AutoNoMask
	public int dogrid6AfterEdit() throws RadowException, SQLException {

		//是否有字段值改变判断标志
		boolean isChange = false;
		//获取页面数据
		Grid grid6 = (Grid) this.getPageElement("grid6");
		RoleVO role = null;
		try {
			role = rc.getById((String) grid6.getValue("roleid"));
		} catch (PrivilegeException e) {
			e.printStackTrace();
			this.setMainMessage(e.getMessage());
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//获取状态的原值
		String status = role.getStatus();
		
		grid6.setValueToObj(role);
		
		//获取页面修改的角色描述的值
		String roledesc = (String) grid6.getValue("roledesc");
		//获取页面修改的角色名称的值
		String rolename = (String) grid6.getValue("rolename");
		//判断，当页面中的角色描述的值与数据库中的值不同，将页面中的值赋给值对象role
		if(role.getDesc() == null){
			if(!roledesc.equals("")){
				role.setDesc(roledesc);
				isChange = true;
			}
		}else{
			if(roledesc == ""){
				role.setDesc(roledesc);
				isChange = true;
			}else if(!roledesc.equals(role.getDesc())){
				role.setDesc(roledesc);
				isChange = true;
			}
		} 
		
		if(role.getName() == null){
			if(!rolename.equals("")){
				role.setName(rolename);
				isChange = true;
			}
		}else{
			if(rolename == ""){
				role.setName(rolename);
				isChange = true;
			}else if(!rolename.equals(role.getName())){
				role.setName(rolename);
				isChange = true;
			}
		} 
			
		//判断角色的状态值是否改变
		if(!status.equals(role.getStatus())){
			isChange = true;
		}
		if(isChange){
			//更新角色数据
			try {
				rc.updateRole(role);
			} catch (PrivilegeException e) {
				this.isShowMsg = true;
				this.setMainMessage(e.getMessage());
				grid6.reload();
				e.printStackTrace();
				return EventRtnType.NORMAL_SUCCESS;
			}
			this.setMainMessage("角色信息修改成功！");
		}
		grid6.reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("saveUser")
	@Transaction
	public int save_u() throws Exception{
		StringBuffer checkIds = new StringBuffer();
		StringBuffer checkNames = new StringBuffer(this.getPageElement("username").getValue()+"(");
		PageElement pe = this.getPageElement("grid6");
		if(pe!=null){
			List<HashMap<String, Object>> list = pe.getValueList();
			for(int j=0;j<list.size();j++){
				HashMap<String, Object> map = list.get(j);
				Object usercheck = map.get("rolecheck");
				if(usercheck.toString().equals("true")){
					checkIds.append(map.get("roleid").toString()).append(",");
					checkNames.append(map.get("rolename").toString()).append(",");
				}
			}
		}
		if(checkIds.length() == 0){
			this.setMainMessage("请一个勾选需要赋予用户的角色！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String roleids = checkIds.toString();
		roleids = roleids.substring(0, roleids.length()-1);
		checkNames.deleteCharAt(checkNames.length()-1).append(")");
		HBSession session = HBUtil.getHBSession();
		String userid = this.getPageElement("userid").getValue();
		
		try {
			new LogUtil().createLogNew(userid,"用户角色授权","SMT_ACT",userid,checkNames.toString(), new ArrayList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//先删除掉此用户已有角色，之后重新赋角色
		session.createSQLQuery("delete from smt_act where userid='"+userid+"'").executeUpdate();
		roleids = roleids.replaceAll(",+", ",");
		String[] split = roleids.split(",");
		for(String roleid:split){
			SmtAct sAct = new SmtAct();
			sAct.setRoleid(roleid);
			sAct.setUserid(userid);
			sAct.setObjectid(userid);
			sAct.setObjecttype("0");
			session.save(sAct);
			session.flush();
		}
		this.setMainMessage("保存成功");
		return EventRtnType.NORMAL_SUCCESS;
	}
	public static void main(String[] args) {
	}
	
	@PageEvent("roleDelete")
	public int delete1(String id) throws Exception {
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES); // 当点击消息框的是确定时触发的下次事件
		ne.setNextEventName("delete2");
		ne.setNextEventParameter(id);
		this.addNextEvent(ne);
		NextEvent nec = new NextEvent();
		nec.setNextEventValue(NextEventValue.CANNEL);// 当点击消息框的是取消时触发的下次事件
		this.addNextEvent(nec);
		this.setMessageType(EventMessageType.CONFIRM); // 消息框类型，即confirm类型窗口
		this.setMainMessage("您确实要删除该记录？"); // 窗口提示信息
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("delete2")
	@Transaction
	public int delete2(String id) throws RadowException{
		HBSession session = HBUtil.getHBSession();
		List<SmtAcl> list_db = session.createQuery("from SmtAcl where roleid = '"+id+"'").list();
		if(list_db.size()!=0){//表明此角色存在关联用户
			NextEvent ne = new NextEvent();
			ne.setNextEventValue(NextEventValue.YES); // 当点击消息框的是确定时触发的下次事件
			ne.setNextEventName("delete3");
			ne.setNextEventParameter(id);
			this.addNextEvent(ne);
			NextEvent nec = new NextEvent();
			nec.setNextEventValue(NextEventValue.CANNEL);// 当点击消息框的是取消时触发的下次事件
			this.addNextEvent(nec);
			this.setMessageType(EventMessageType.CONFIRM); // 消息框类型，即confirm类型窗口
			this.setMainMessage("检测到该角色已被角色关联，是否继续删除？"); // 窗口提示信息
		}else{//进行删除操作
			realdelete(id);
			this.setMainMessage("删除成功");
			this.getPageElement("grid6").reload();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("delete3")
	@Transaction
	public int delete3(String id) throws RadowException{//进行删除操作
		realdelete(id);
		this.setMainMessage("删除成功");
		this.getPageElement("grid6").reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public void realdelete(String id){
		HBSession session = HBUtil.getHBSession();
		try {
			new LogUtil().createLogNew(id,"角色删除","SMT_ROLE",id,HBUtil.getValueFromTab("rolename", "SMT_ROLE", "roleid='"+id+"'"), new ArrayList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//删除SMT_ACL中的信息
		session.createSQLQuery("delete from SMT_ACL where roleid='"+id+"'").executeUpdate();
		//删除SMT_ACT中的信息
		session.createSQLQuery("delete from SMT_ACT where roleid='"+id+"'").executeUpdate();
		//删除SMT_ROLE中的信息
		session.createSQLQuery("delete from SMT_ROLE where roleid='"+id+"'").executeUpdate();
		
	}
	@PageEvent("removeSession")
	public int removeSession() {
		request.getSession().removeAttribute("roleid");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public void addResource(String roleid,String addids){
		List<Node> nodeList = new ArrayList<Node>();
		HBSession sess = HBUtil.getHBSession();
		 String hql1 = "From SmtFunction";
		 List list2 = sess.createQuery(hql1).list();
		 for(int i=0;i<list2.size();i++){
			 SmtFunction sf = (SmtFunction)list2.get(i);
			 Node node = new Node(sf.getFunctionid(),sf.getTitle(),sf.getParent());
			 node.setType(sf.getType());
			 nodeList.add(node); 
		 }
		String[] ids = addids.split(",");
		List<SmtFunction> list = new ArrayList<SmtFunction>();
		for(int i=0;i<ids.length;i++){
			String hql="from SmtFunction t where t.functionid = '"+ids[i]+"'";
			List list1 = sess.createQuery(hql).list();
			if(list1.size()==1){
			SmtFunction s = (SmtFunction)list1.get(0);
			list.add(s);
			}
		}
		String hql="from SmtFunction t where t.functionid = 'GG00000'";
		List list1 = sess.createQuery(hql).list();
		if(list1.size()==1){
			SmtFunction s = (SmtFunction)list1.get(0);
			list.add(s);
		}
		//List<SmtFunction> parent = new ArrayList<SmtFunction>();
		//List<SmtFunction> child = new ArrayList<SmtFunction>();
		/*for(int i=0;i<list.size();i++){
			for(int j=0;j<list.size();j++){
				if(list.get(j).getFunctionid().equals("S000000")){
					continue;
				}
				if(list.get(i).getFunctionid().equals(list.get(j).getParent())){
					//parent.add(list.get(i));
					list.remove(i);
					i--;
					break;
				}
			}
		}*/
		 
		//System.out.println(mt.getChildNodes(nodeList, "402881fe538436cc0153843a10eb0002")); 
		for(int i=0;i<list.size();i++){
			if(list.get(i).getFunctionid().equals("S000000")){
				continue;
			}
			NodeUtil mt = new NodeUtil();
			String str = mt.getChildNodes(nodeList,list.get(i).getFunctionid());
			if(str.contains(",")){
			//str = str.substring(1+list.get(i).getFunctionid().length()+1,str.length()-1);
			addids+=","+str;
			}else{
				continue;
			}
			
		}
		addids = addids.replaceAll(" |\\[|\\]","");
		String[] resourceids = addids.split(",");
		for(int i=0;i<resourceids.length;i++){
			String hql3 = "from SmtAcl t where t.resourceid='"+resourceids[i]+"' and t.roleid='"+roleid+"'";
			List list3 = sess.createQuery(hql3).list();
			if(list3.size()==0){
				SmtAcl acl = new SmtAcl();
				acl.setResourceid(resourceids[i]);
				if(resourceids[i].equals("")){
					
				}
				acl.setRoleid(roleid);
				sess.save(acl);
				sess.flush();
			}
		}
		//System.out.println(addids);
	}
	public void removeResource(String roleid,String removeids){
		List<Node> nodeList = new ArrayList<Node>();
		HBSession sess = HBUtil.getHBSession();
		 String hql1 = "From SmtFunction";
		 List list2 = sess.createQuery(hql1).list();
		 for(int i=0;i<list2.size();i++){
			 SmtFunction sf = (SmtFunction)list2.get(i);
			 Node node = new Node(sf.getFunctionid(),sf.getTitle(),sf.getParent());
			 nodeList.add(node); 
		 }
		 String[] ids = removeids.split(",");
			List<SmtFunction> list = new ArrayList<SmtFunction>();
			for(int i=0;i<ids.length;i++){
				String hql="from SmtFunction t where t.functionid = '"+ids[i]+"'";
				List list1 = sess.createQuery(hql).list();
				if(list1.size()==1){
				SmtFunction s = (SmtFunction)list1.get(0);
				list.add(s);
				}
			}
			for(int i=0;i<list.size();i++){
				if(list.get(i).getFunctionid().equals("S000000")){
					continue;
				}
				for(int j=0;j<list.size();j++){
					if(list.get(i).getParent().equals(list.get(j).getFunctionid())){
						//parent.add(list.get(i));
						list.remove(i);
						i--;
						break;
					}
				}
			}
			 
			for(int i=0;i<list.size();i++){
				NodeUtil mt = new NodeUtil();
				String str = mt.getChildNodes(nodeList,list.get(i).getFunctionid());
				if(str.contains(",")){
				str = str.substring(1+list.get(i).getFunctionid().length()+1,str.length()-1);
				removeids+=str+",";
				}else{
					continue;
				}
				
			}
			removeids = removeids.replaceAll(" ","");
			String[] removeresourceids = removeids.split(",");
			for(int i=0;i<removeresourceids.length;i++){
				String hql3 = "from SmtAcl t where t.resourceid='"+removeresourceids[i]+"' and t.roleid='"+roleid+"'";
				List list3 = sess.createQuery(hql3).list();
				if(list3.size()==1){
					SmtAcl acl = (SmtAcl)list3.get(0);
					sess.delete(acl);
					sess.flush();
				}
			}
			//System.out.println(removeids);
			
	}
	
	@PageEvent("rolesort")
	@Transaction
	public int publishsort() throws RadowException {
		List<HashMap<String, String>> list = this.getPageElement("grid6").getStringValueList();
		HBSession sess = HBUtil.getHBSession();
		Connection con = null;
		try {
			con = sess.connection();
			con.setAutoCommit(false);
			String sql = "update smt_role set sortid = ? where roleid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			int i = 1;
			for (HashMap<String, String> m : list) {
				String roleid = m.get("roleid");
				ps.setInt(1, i);
				ps.setString(2, roleid);
				ps.addBatch();
				i++;
			}
			ps.executeBatch();
			con.commit();
			ps.close();
			con.close();
		} catch (Exception e) {
			try {
				con.rollback();
				if (con != null) {
					con.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		this.toastmessage("排序已保存！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
