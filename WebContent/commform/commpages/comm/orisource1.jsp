<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@page import="com.insigma.odin.framework.commform.hibernate.HList"%>
<%@page import="com.insigma.odin.framework.util.commform.StringUtil"%>
<head>
<odin:commformhead />
</head>

<%
	Long rcode = Long.valueOf(request.getParameter("rcode"));
	HList hl=new HList("select rcontent from sbdn_notice where rcode="+rcode+"");
	String orisource="";
	try{
		orisource=hl.getString("rcontent");
		orisource = StringUtil.unzipString(orisource); //��ѹ��δѹ��Ҳ��Ӱ��
	}catch(Exception e){		
	}
	
%>

<%=orisource%>

