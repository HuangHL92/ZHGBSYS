<%@page import="com.insigma.siis.local.business.entity.extra.ExtraTagsOplog"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@page import="com.insigma.siis.local.business.entity.A01Oplog"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.AddZHGBRmbPageModel"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="com.insigma.siis.local.business.helperUtil.IdCardManageUtil"%>
<%@page import="com.insigma.siis.local.business.helperUtil.DateUtil"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.UUID"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@page import="com.insigma.siis.local.business.entity.LogMain"%>
<%@page import="com.insigma.siis.local.business.helperUtil.CodeType2js"%>
<%@page import="com.insigma.siis.local.business.entity.A01"%>
<%@page import="com.insigma.siis.local.business.entity.A99Z1"%>
<%@page import="com.insigma.siis.local.business.entity.extra.ExtraTags"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%!private String SV(String v){
	return v==null?"":StringEscapeUtils.escapeHtml(v);
}
%>
<%
	String ctxPath = request.getContextPath(); 
	String username = "";
	try{
		username = SysManagerUtils.getUserName(); 	//操作用户（此处为用户登录名）。
	}catch(Exception e){
		out.println("<script>alert('登录超时！');parent.window.location='"+ctxPath+"/LogonDialog.jsp'</script>");
	}
	
	
	String RrmbCodeType = (String)session.getAttribute("RrmbCodeType");
	//RrmbCodeType = CodeType2js.getRrmbCodeType();
	if(RrmbCodeType==null){
		RrmbCodeType = CodeType2js.getRrmbCodeType();
		session.setAttribute("RrmbCodeType",RrmbCodeType);
	}
	
	HBSession sess = HBUtil.getHBSession();
	String setTitle = "";
	
	//根据logid，获取日志对应的a0000
	String logid = request.getParameter("logid");
	A01Oplog log = (A01Oplog)sess.get(A01Oplog.class, logid);
	
	
	String fromModules = request.getParameter("FromModules");
	
	
	A01 a01 = new A01();
	PropertyUtils.copyProperties(a01, log);
				
	String a0000 = a01.getA0000();
	setTitle = AddZHGBRmbPageModel.setTitle(a01);

	
	String a0195Text = null;
	if(a01.getA0195()!=null){
		a0195Text = HBUtil.getValueFromTab("b0101", "b01", " b0111='"+a01.getA0195()+"'");
		
		if(a0195Text == null || a0195Text.equals("")){
			a0195Text = a01.getA0195();
		}
	}
	
	String a0163Text = null;
	if(a01.getA0163()!=null){
		a0163Text = HBUtil.getValueFromTab("code_name", "code_value", " code_type='ZB126' and code_value='"+a01.getA0163()+"'");
		if(a0163Text==null||"".equals(a0163Text)){
			a0163Text = a01.getA0163();
		}
	}
	
	//家庭成员
	String sqla36 = "from A36Oplog where oplogid='"+logid+"' order by SORTID";
	List lista36 = sess.createQuery(sqla36).list();
	int lista36Length = lista36.size();
	
	//补充信息集 
	String sqlaA99Z1 = "from A99Z1 where a0000='"+a0000+"'";
	List listA99Z1 = sess.createQuery(sqlaA99Z1).list();
	
	A99Z1 a99Z1 = null;
	if(listA99Z1==null||listA99Z1.size()==0){
		//a0000 = UUID.randomUUID().toString();
		a99Z1 = new A99Z1();
	}else{
		a99Z1 = (A99Z1) listA99Z1.get(0);
	}
	
	//补充标签信息集
	String sqlet = "from ExtraTagsOplog where oplogid='"+logid+"'";
	List listet = sess.createQuery(sqlet).list();
			
	ExtraTagsOplog extraTags = null;
	if(listet==null||listet.size()==0){
		extraTags = new ExtraTagsOplog();
	}else{
		extraTags = (ExtraTagsOplog) listet.get(0);
	}
	
	request.setAttribute("a01", a01);
%>
<script type="text/javascript">
var ctxPath = '<%=ctxPath%>';
<%=RrmbCodeType%>


var realParent = window.dialogArguments.window;
var parent = window.dialogArguments.window;
//var gridName = parent.Ext.getCmp("personInfoOP").initialConfig.gridName;
var gridName = window.dialogArguments.wincfg.gridName;

</script>
