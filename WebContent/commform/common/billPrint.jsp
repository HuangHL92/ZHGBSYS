<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.insigma.odin.framework.persistence.HBUtil" %>
<%@ page import="com.lbs.leaf.cp.util.SysUtil" %>
<%
	String REP_SRV_ADDR=HBUtil.getValueFromTab("aaa005","aa01","aaa001='REP_SRV_ADDR'");
	String REP_SRV_NAME=HBUtil.getValueFromTab("aaa005","aa01","aaa001='REP_SRV_NAME'");
	String REP_OCX_ADDR=HBUtil.getValueFromTab("aaa005","aa01","aaa001='REP_OCX_ADDR'");
	String repid=request.getParameter("repid");
	String queryName=request.getParameter("queryname");
	String param=request.getParameter("param");
	String preview=request.getParameter("preview");
	if(queryName==null){
		queryName="";
	}
	if(preview==null){
		preview="true";
	}
	String repmode="3";
	if(preview.equalsIgnoreCase("true")){
		repmode="3";
	}else if(preview.equalsIgnoreCase("false")){
		repmode="2";
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title><%=(repmode.equals("2")?"AUTO CLOSE PRINT":"���ݴ�ӡ")%></title>
</head>
<body Leftmargin="0" topmargin="0"  rightmargin="0" bottommargin="0">
<OBJECT id="CXDYProj1" width ="100%"  height="100%" align =center hspace=0 vspace=0
      classid="clsid:569FCF6D-D079-47AC-902C-DCE4398DA4FE"
      codebase="<%=REP_OCX_ADDR%>/CXDYProj1.ocx#version=1,0,0,10">		  <!-- ACTIVE FORM������ʾ�����ַ -->
      <PARAM NAME="D_REP_MODE"      VALUE="<%=repmode%>"				> <!-- �������ģʽ -->
      <PARAM NAME="D_HTTP_SERVER"   VALUE="<%=REP_SRV_ADDR%>"          	> <!-- ��������ַ -->
      <PARAM NAME="D_SERVER_DIR"    VALUE="<%=REP_SRV_NAME%>"           > <!-- ����������� -->
      <PARAM NAME="D_REPID"         VALUE="<%=repid%>"                  > <!-- ������� -->
      <PARAM NAME="D_QUERYNAME"     VALUE="<%=queryName%>"              > <!-- ��ѯ���� -->
      <PARAM NAME="D_USERID"        VALUE="<%=SysUtil.getCacheCurrentUser().getUser().getUsername()%>"                        > <!-- �û����� -->
      <PARAM NAME="D_PASSWORD"      VALUE="0000"                        > <!-- �û����� -->
      <PARAM NAME="D_USERNAME"      VALUE="<%=SysUtil.getCacheCurrentUser().getUser().getOperatorname()%>"                    > <!-- �û����� -->
      <PARAM NAME="D_DEP_ID"        VALUE="01"                          > <!-- ���ű��� -->
      <PARAM NAME="D_DEP_NAME"      VALUE="���������"                    > <!-- �������� -->
      <PARAM NAME="D_PARAMSTR"      VALUE="<%=param%>"                  > <!-- ������ -->
      <PARAM NAME="D_R_DATA"        VALUE=""                            > <!-- Զ�����ݵ�ַ -->
      <PARAM NAME="D_COLOR_BORDER"  VALUE="9394438"                     > <!-- ����߿���ɫ -->
      <PARAM NAME="D_COLOR_BACK"    VALUE="15326925"                    > <!-- ���������ɫ -->
      <PARAM NAME="D_APPLY_LEVEL"   VALUE="standard"                    > <!-- ������Ӧ��ģʽ -->
      <PARAM NAME="D_DEF_PRINTER"   VALUE=""                            > <!-- ȱʡ��ӡ������ -->
    </OBJECT>
</body>
<script LANGUAGE="JavaScript">
	parent.billPrintNext();
	window.close();
</script>
</html>