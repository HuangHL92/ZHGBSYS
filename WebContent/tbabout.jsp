<%@page import="com.insigma.siis.local.business.comm.VerWindowBS"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.insigma.odin.framework.util.SysUtil" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3" %>
<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%@page import="com.insigma.odin.framework.safe.SafeControlCenter"%>
<%@page import="com.insigma.odin.framework.safe.util.SafeConst"%>
<%@page import="com.insigma.siis.local.epsoft.util.LogUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.odin.framework.db.DBUtil"%>
<%@page import="com.insigma.siis.local.lrmx.ExpRar"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.user.PsWindowPageModel"%>
<%@page import="com.insigma.siis.local.business.helperUtil.CodeType2js"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统信息</title>
<style type="text/css">
<!--
body {
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	margin-left: 0px;
	font-size: 9pt;
}
-->
</style>
</head>
<body>
	
<TABLE width="100%">
<TR>
	<TD><br></TD>
</TR>
<TR>
	<TD><CENTER><b>版本信息</b></CENTER></TD>
</TR>
<TR>
	<TD><br></TD>
</TR>
<TR>
	<TD><hr></TD>	
</TR>
<TR>
	<TD>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;版本号:&nbsp;&nbsp;<%=VerWindowBS.getVersionName() %></TD>	
</TR>
<TR>
	<TD>&nbsp;&nbsp;&nbsp;&nbsp;程序版本:&nbsp;&nbsp;<%=VerWindowBS.getSoftwareVersionName() %></TD>	
</TR>
<TR>
	<TD>&nbsp;&nbsp;数据库版本:&nbsp;&nbsp;<%=VerWindowBS.getDBVersionName() %><br></TD>	
</TR>

</TABLE>
</body>
</html>