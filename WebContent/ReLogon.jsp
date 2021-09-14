<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3" %>
<%@page import="com.insigma.odin.framework.util.SysUtil"%>
<HTML>
<HEAD>
</HEAD>
<BODY>
<%
	if(!SysUtil.isWorkpf(request)){
%>
<sicp3:errors />
<%
	}
%>
<SCRIPT LANGUAGE=javascript>
var url = "/hzb";
top.parent.location.href = url
</SCRIPT>
</body>
</html>
