<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<odin:head/>
<script type="text/javascript"  src="<%=request.getContextPath()%>/basejs/ext/portal/Portal.js"></script>
<script type="text/javascript"  src="<%=request.getContextPath()%>/basejs/ext/portal/PortalColumn.js"></script>
<script type="text/javascript"  src="<%=request.getContextPath()%>/basejs/ext/portal/Portlet.js"></script>
<!-- 
<script type="text/javascript"  src="<%=request.getContextPath()%>/basejs/desktop.js"></script>
 -->
</head>
<body style="overflow: hidden;background-color: #e5effb">
<table width="100%" height="100%" style="background:url(images/welcome.jpg) no-repeat;">
<tr>
<td>
<div id="desktop" style="width: 100%;height: 100%;"></div>
<odin:window id="deskConWin" src="blank.htm" title="桌面项显示配置窗口" modal="true" width="410" height="310" />

</td>
</tr>
</table>
<script>
var dt_totalcols_count = '<%=GlobalNames.sysConfig.get("DESKTOP_COLS_COUNT")==null?"3":GlobalNames.sysConfig.get("DESKTOP_COLS_COUNT")%>'; 
</script> 
</body>
</html>