<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="com.insigma.odin.framework.util.commform.SysConst"%>
<%@page import="com.insigma.odin.framework.util.commform.ObjectUtil"%>
<html>
<head>
	<% 
	String title=SysConst.getServerTitle(); 
	if(ObjectUtil.equals(title,"")){
		title="ȫ������Ա������Ϣϵͳ";
	}
%>
<title><%=title%><%=SysConst.getServerNumber()%></title>
</head>
<frameset frameborder="no" border="0" framespacing="0" >
  <frame id="main" src="<%=request.getContextPath()%>/commform/Main.jsp" />
</frameset>
</html>