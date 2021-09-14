<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="com.insigma.odin.framework.util.commform.SysConst"%>
<%@page import="com.insigma.odin.framework.util.commform.ObjectUtil"%>
<html>
<head>
	<% 
	String title=SysConst.getServerTitle(); 
	if(ObjectUtil.equals(title,"")){
		title="全国公务员管理信息系统";
	}
%>
<title><%=title%><%=SysConst.getServerNumber()%></title>
</head>
<frameset frameborder="no" border="0" framespacing="0" >
  <frame id="main" src="<%=request.getContextPath()%>/commform/Main.jsp" />
</frameset>
</html>