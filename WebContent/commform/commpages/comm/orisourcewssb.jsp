<%@include file="/commform/basejs/loading/loading.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin-commform.tld" prefix="odin"%>
<%@page import="com.insigma.odin.framework.commform.hibernate.HList"%>


<%
	Long opseno = Long.valueOf(request.getParameter("opseno"));
	HList sbdnYwsbxx = new HList();
	sbdnYwsbxx.setSql("select orisource from wssb_sbdn_ywsbxx where opseno=:opseno");
	sbdnYwsbxx.setSqlParam("opseno", opseno);
	sbdnYwsbxx.retrieve();
	String orisource = null;
	if (sbdnYwsbxx != null) {
		orisource = sbdnYwsbxx.getString("orisource");
		orisource = orisource.replace("/sionline", "/insiis");
		orisource = StringUtil.unzipString(orisource); //解压，未压缩也不影响
	}
	if (orisource == null) {
		orisource = "此笔业务无原始界面！<br><br>注：可能为导盘等特殊业务。";
	}
%>

<%=orisource%>
<%@page import="com.insigma.odin.framework.util.commform.StringUtil"%>
<SCRIPT LANGUAGE="JavaScript">
	var buttons=document.getElementsByTagName("button");
	for(var i=0;i<buttons.length;i++){
		buttons[i].setAttribute("disabled","true");
		//buttons[i].parentNode.removeChild(buttons[i]);
		//alert("ok");
	}
	
	var edits=document.getElementsByTagName("input");
	for(var i=0;i<edits.length;i++){
		if(edits[i].type=="checkbox"){
			//edits[i].onclick="";
			edits[i].setAttribute("disabled","true");
		}else{
			//edits[i].setAttribute("disabled","true");
			edits[i].setAttribute("readOnly","true");		
			edits[i].setAttribute("onchange","");
		}
	}
	
	var a=document.getElementsByTagName("a");
	for(var i=0;i<a.length;i++){
		a[i].setAttribute("disabled","true");
		a[i].setAttribute("onclick","");		
		a[i].setAttribute("href","#");
	}
	var iframe=document.getElementsByTagName('iframe');
	for(var i=0;i<iframe.length;i++){
		iframe[i].src="";
	}
</SCRIPT>

