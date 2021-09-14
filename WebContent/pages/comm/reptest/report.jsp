<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="java.net.URLDecoder"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>报表显示</title>
</head>
<body Leftmargin="0" topmargin="0"  rightmargin="0" bottommargin="0">
<%
String param = request.getParameter("repParam");
if(param!=null){
	param = URLDecoder.decode(param,"utf-8");
}
%>
<OBJECT width ="100%"  height="100%"  hspace=0 vspace=0
      classid="clsid:569FCF6D-D079-47AC-902C-DCE4398DA4FE"
      codebase="<%=request.getParameter("repOcxAddr")%>/CXDY.cab#version=1,0,0,15"              > <!-- ACTIVE FORM报表显示插件地址 -->
      <PARAM NAME="D_REP_MODE"      VALUE="<%=request.getParameter("repModel")%>"				> <!-- 报表调用模式 -->
      <PARAM NAME="D_HTTP_SERVER"   VALUE="<%=request.getParameter("repSrvAddr")%>"          	> <!-- 报表服务地址 -->
      <PARAM NAME="D_SERVER_DIR"    VALUE="<%=request.getParameter("repSrvName")%>"             > <!-- 报表服务名称 -->
      <PARAM NAME="D_REPID"         VALUE="<%=request.getParameter("repCode")%>"                > <!-- 报表编码 -->
      <PARAM NAME="D_QUERYNAME"     VALUE="<%=request.getParameter("queryCode")%>"              > <!-- 查询编码 -->
      <PARAM NAME="D_USERID"        VALUE="<%=request.getParameter("loginName")%>"              > <!-- 用户编码 -->
      <PARAM NAME="D_PASSWORD"      VALUE="0000"                                                > <!-- 用户密码 -->
      <PARAM NAME="D_USERNAME"      VALUE="<%=request.getParameter("realName")%>"               > <!-- 用户名称 -->
      <PARAM NAME="D_DEP_ID"        VALUE="01"                                                  > <!-- 部门编码 -->
      <PARAM NAME="D_DEP_NAME"      VALUE="计算机中心"                                            > <!-- 部门名称 -->
      <PARAM NAME="D_PARAMSTR"      VALUE="<%=param%>"                                          > <!-- 参数串 -->
      <PARAM NAME="D_R_DATA"        VALUE=""                                                    > <!-- 远程数据地址 -->
      <PARAM NAME="D_COLOR_BORDER"  VALUE="9394438"                                             > <!-- 插件边框颜色 -->
      <PARAM NAME="D_COLOR_BACK"    VALUE="15326925"                                            > <!-- 插件背景颜色 -->
      <PARAM NAME="D_APPLY_LEVEL"   VALUE="standard"                                            > <!-- 报表插件应用模式 -->
      <PARAM NAME="D_DEF_PRINTER"   VALUE=""                                                    > <!-- 缺省打印机名称 -->
</OBJECT>
																			       <!-- Ocx插件测试时的真实参数值参考 -->
<Test___OBJECT width ="100%"  height="100%" align ="center" hspace=0 vspace=0   
      classid="clsid:569FCF6D-D079-47AC-902C-DCE4398DA4FE"
      codebase="http://192.168.70.138:9188/CXDY.cab#version=1,0,0,15">		       <!-- ACTIVE FORM报表显示插件地址 -->
			<PARAM NAME="D_REP_MODE" VALUE="1">                                    <!-- 报表调用模式 -->
			<PARAM NAME="D_HTTP_SERVER" VALUE="http://192.168.70.138:9188;">       <!-- 报表服务地址 -->
			<PARAM NAME="D_SERVER_DIR" VALUE="inhis_rep">                          <!-- 报表服务名称 -->
			<PARAM NAME="D_REPID" VALUE="DEQ0001">                                 <!-- 报表编码:"DEQ0001" 报表名称:"医疗费用结算单" -->
			<PARAM NAME="D_QUERYNAME" VALUE="Q001">                                <!-- 查询编码:"Q001" 查询名称:"医疗费用结算单-子查询001" -->
			<PARAM NAME="D_USERID" VALUE="0000">                                   <!-- 用户编码 :[rybm] -->
			<PARAM NAME="D_PASSWORD" VALUE="0000">                                 <!-- 用户密码:[rymc] -->
			<PARAM NAME="D_USERNAME" VALUE="龚明伟">							       <!-- 用户名称 :[rydp] -->
			<PARAM NAME="D_DEP_ID" VALUE="0001">                                   <!-- 部门编码 :[rydpmc] -->
			<PARAM NAME="D_DEP_NAME" VALUE="数据业务部">                             <!-- 部门名称 -->
			<PARAM NAME="D_PARAMSTR" VALUE="">                                     <!-- 参数串 -->
			<PARAM NAME="D_R_DATA" VALUE="">                                       <!-- 远程数据地址 -->
			<PARAM NAME="D_COLOR_BORDER" VALUE="11711154">                         <!-- 插件边框颜色 --> 
			<PARAM NAME="D_COLOR_BACK" VALUE="-16777201">                          <!-- 插件背景颜色 -->
			<PARAM NAME="D_APPLY_LEVEL" VALUE="standard">                          <!-- 报表插件应用模式 -->
</Test___OBJECT>
</body>
</html>