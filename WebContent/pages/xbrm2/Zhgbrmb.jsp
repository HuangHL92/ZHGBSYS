<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page isELIgnored="false" %> 

<meta http-equiv="X-UA-Compatible" content="IE=8" /> 
 
<script type="text/javascript">
<!-- 
	var g_ContextPath = "<%=request.getContextPath()%>";
	var a0000 = "<%=request.getParameter("a0000")%>";
	var vid = "<%=request.getParameter("vid")%>";
	var url = g_ContextPath + "/rmb/ZHGBrmb.jsp?FromModules=1&a0000="+a0000+"&vid="+vid;
	alert("<%=com.insigma.odin.framework.util.SysUtil.getCacheCurrentUser().getId()%>");
	window.location.href = url;
//-->
</script>