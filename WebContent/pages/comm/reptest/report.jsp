<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="java.net.URLDecoder"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>������ʾ</title>
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
      codebase="<%=request.getParameter("repOcxAddr")%>/CXDY.cab#version=1,0,0,15"              > <!-- ACTIVE FORM������ʾ�����ַ -->
      <PARAM NAME="D_REP_MODE"      VALUE="<%=request.getParameter("repModel")%>"				> <!-- �������ģʽ -->
      <PARAM NAME="D_HTTP_SERVER"   VALUE="<%=request.getParameter("repSrvAddr")%>"          	> <!-- ��������ַ -->
      <PARAM NAME="D_SERVER_DIR"    VALUE="<%=request.getParameter("repSrvName")%>"             > <!-- ����������� -->
      <PARAM NAME="D_REPID"         VALUE="<%=request.getParameter("repCode")%>"                > <!-- ������� -->
      <PARAM NAME="D_QUERYNAME"     VALUE="<%=request.getParameter("queryCode")%>"              > <!-- ��ѯ���� -->
      <PARAM NAME="D_USERID"        VALUE="<%=request.getParameter("loginName")%>"              > <!-- �û����� -->
      <PARAM NAME="D_PASSWORD"      VALUE="0000"                                                > <!-- �û����� -->
      <PARAM NAME="D_USERNAME"      VALUE="<%=request.getParameter("realName")%>"               > <!-- �û����� -->
      <PARAM NAME="D_DEP_ID"        VALUE="01"                                                  > <!-- ���ű��� -->
      <PARAM NAME="D_DEP_NAME"      VALUE="���������"                                            > <!-- �������� -->
      <PARAM NAME="D_PARAMSTR"      VALUE="<%=param%>"                                          > <!-- ������ -->
      <PARAM NAME="D_R_DATA"        VALUE=""                                                    > <!-- Զ�����ݵ�ַ -->
      <PARAM NAME="D_COLOR_BORDER"  VALUE="9394438"                                             > <!-- ����߿���ɫ -->
      <PARAM NAME="D_COLOR_BACK"    VALUE="15326925"                                            > <!-- ���������ɫ -->
      <PARAM NAME="D_APPLY_LEVEL"   VALUE="standard"                                            > <!-- ������Ӧ��ģʽ -->
      <PARAM NAME="D_DEF_PRINTER"   VALUE=""                                                    > <!-- ȱʡ��ӡ������ -->
</OBJECT>
																			       <!-- Ocx�������ʱ����ʵ����ֵ�ο� -->
<Test___OBJECT width ="100%"  height="100%" align ="center" hspace=0 vspace=0   
      classid="clsid:569FCF6D-D079-47AC-902C-DCE4398DA4FE"
      codebase="http://192.168.70.138:9188/CXDY.cab#version=1,0,0,15">		       <!-- ACTIVE FORM������ʾ�����ַ -->
			<PARAM NAME="D_REP_MODE" VALUE="1">                                    <!-- �������ģʽ -->
			<PARAM NAME="D_HTTP_SERVER" VALUE="http://192.168.70.138:9188;">       <!-- ��������ַ -->
			<PARAM NAME="D_SERVER_DIR" VALUE="inhis_rep">                          <!-- ����������� -->
			<PARAM NAME="D_REPID" VALUE="DEQ0001">                                 <!-- �������:"DEQ0001" ��������:"ҽ�Ʒ��ý��㵥" -->
			<PARAM NAME="D_QUERYNAME" VALUE="Q001">                                <!-- ��ѯ����:"Q001" ��ѯ����:"ҽ�Ʒ��ý��㵥-�Ӳ�ѯ001" -->
			<PARAM NAME="D_USERID" VALUE="0000">                                   <!-- �û����� :[rybm] -->
			<PARAM NAME="D_PASSWORD" VALUE="0000">                                 <!-- �û�����:[rymc] -->
			<PARAM NAME="D_USERNAME" VALUE="����ΰ">							       <!-- �û����� :[rydp] -->
			<PARAM NAME="D_DEP_ID" VALUE="0001">                                   <!-- ���ű��� :[rydpmc] -->
			<PARAM NAME="D_DEP_NAME" VALUE="����ҵ��">                             <!-- �������� -->
			<PARAM NAME="D_PARAMSTR" VALUE="">                                     <!-- ������ -->
			<PARAM NAME="D_R_DATA" VALUE="">                                       <!-- Զ�����ݵ�ַ -->
			<PARAM NAME="D_COLOR_BORDER" VALUE="11711154">                         <!-- ����߿���ɫ --> 
			<PARAM NAME="D_COLOR_BACK" VALUE="-16777201">                          <!-- ���������ɫ -->
			<PARAM NAME="D_APPLY_LEVEL" VALUE="standard">                          <!-- ������Ӧ��ģʽ -->
</Test___OBJECT>
</body>
</html>