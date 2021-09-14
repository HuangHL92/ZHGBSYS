<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<%
//清除缓存
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
%>
<title>授权窗口</title>
<odin:head/>
<odin:MDParam/>
<link rel="STYLESHEET" type="text/css" href="<%=request.getContextPath()%>/basejs/tree/dhtmlxtree.css">	
<script  src="<%=request.getContextPath()%>/basejs/tree/dhtmlxcommon.js"></script>
<script  src="<%=request.getContextPath()%>/basejs/tree/dhtmlxtree.js"></script>
<script>
var isOnceLoad = "<%=com.insigma.odin.framework.util.GlobalNames.FUNC_TREE_ISONCELOAD%>";
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/role/assignCAFunc.js"></script>
</head>

<body onload="onLoad();" style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<div id="treeBox" style="width:284; height:360;background-color:#f5f5f5;border :1px solid Silver;">
</div>

<table width="96%" align="center">
	<tr>
		<td colspan="4" height="4"></td>
	</tr>
	<tr>
		<td width="150"></td>
		<td align="right"><img src="<%=request.getContextPath()%>/images/baocun.gif" onclick="submit()"></td>
		<td width="6"></td>
		<td><img src="<%=request.getContextPath()%>/images/quxiao.gif" onclick="parent.odin.ext.getCmp('funcCAWindow').hide()"></td>
	</tr>
	<tr>
		<td colspan="4" height="4"></td>
	</tr>
</table>

<form action="<%=request.getContextPath()%>/sys/roleAction.do" method="post" name="roleForm">
	<input type="hidden" name="method">
	<input type="hidden" name="funcids">
</form>

</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>