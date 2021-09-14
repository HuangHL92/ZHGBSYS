<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.insigma.odin.framework.sys.SysfunctionManager"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%
	String functionid = request.getParameter("functionid");
	if (functionid == null)
		functionid = "";
	SysfunctionManager.setModuleSysfunctionidCache(functionid);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>操作日志</title>
<odin:head />
<odin:sysParam />
<odin:MDParam></odin:MDParam>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.PageModeEngine.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.util.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.renderer.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.business.js"></script>

<script src="<%=request.getContextPath()%>/radow/corejs/radow.cm.js"></script>

<script src="<%=request.getContextPath()%>/basejs/odin.grid.menu.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/pages/oplog/WzPMMDOpLogList.js"></script>

</head>
<body>
<odin:base>

<div id='forView_div_1' style='width: 100%;'>
	<div id='gridDiv_div_1'></div>
</div>
<input type='hidden' style='display:none' name='div_1Data' id='div_1Data'  value='' >
</odin:base>
<odin:window id="win_pup" src="blank.htm" title="" modal="true" width="0" height="0" />
</body>
</html>