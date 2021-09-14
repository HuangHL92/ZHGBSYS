<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.insigma.odin.framework.persistence.HBUtil" %>
<%@ page import="com.insigma.odin.framework.sys.SysfunctionManager" %>
<%@ page import="com.insigma.odin.framework.privilege.vo.FunctionVO" %>
<%@ page import="com.insigma.odin.framework.util.*" %>
<%@ page import="com.insigma.siis.local.pagemodel.comm.CommonQueryBS" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>���ݴ�ӡ</title>
</head>
<%
	String REP_SRV_ADDR=HBUtil.getValueFromTab("aaa005","aa01","aaa001='REP_SRV_ADDR'");
	String REP_SRV_NAME=HBUtil.getValueFromTab("aaa005","aa01","aaa001='REP_SRV_NAME'");
	String REP_OCX_ADDR=HBUtil.getValueFromTab("aaa005","aa01","aaa001='REP_OCX_ADDR'");
	
	//��ΰ��2009-12-28  �����˱���ǰ�ô���
	String REP_PROXY_WAY=HBUtil.getValueFromTab("aaa005","aa01","aaa001='REP_PROXY_WAY'"); 
	String REP_PROXY_ADDR = null;
	//0�������ô���
	//1�����ô���ʽ����������ַ �硰http://http://192.168.70.140:8082��)
	if(REP_PROXY_WAY!=null && !REP_PROXY_WAY.equals("0")){//���ô���ʽ
		String REP_PROXY_PORT=HBUtil.getValueFromTab("aaa005","aa01","aaa001='REP_PROXY_PORT'");
		if(REP_PROXY_WAY.equals("1")){
			REP_PROXY_ADDR = "http://"+request.getLocalAddr()+":"+REP_PROXY_PORT;
		}
		REP_SRV_ADDR = REP_PROXY_ADDR;
		REP_OCX_ADDR = REP_PROXY_ADDR;
		String isLog=HBUtil.getValueFromTab("aaa105","aa01","aaa001='REP_PROXY_PORT'");
		if(isLog!=null && isLog.equals("1")){
			CommonQueryBS.systemOut("REP_SRV_ADDR��"+REP_SRV_ADDR);
			CommonQueryBS.systemOut("REP_SRV_NAME��"+REP_SRV_NAME);
			CommonQueryBS.systemOut("REP_OCX_ADDR��"+REP_OCX_ADDR);
		}
	}
	// end  jinwei 
	
	
	FunctionVO moduleSysfunction=SysfunctionManager.getModuleSysfunction();
	String repid=moduleSysfunction.getParam1();
	String queryName=(moduleSysfunction.getParam2()==null?"":moduleSysfunction.getParam2());
	String param="";
	String repmode=moduleSysfunction.getRpflag();
	boolean isWorkpf = SysUtil.isWorkpf(request);
%>
<body Leftmargin="0" topmargin="0"  rightmargin="0" bottommargin="0">
<%
	if(isWorkpf){
%>
<script type="text/javascript">
function printReport(){
    var params = {
        "D_REP_MODE":"<%=repmode%>", // �������ģʽ 
        "D_HTTP_SERVER":"<%=REP_SRV_ADDR%>", //��������ַ
        "D_SERVER_DIR":"<%=REP_SRV_NAME%>", //�����������
        "D_REPID":"<%=repid%>", //�������
        "D_QUERYNAME":"<%=queryName%>", //��ѯ����
        "D_USERID":"<%=SysUtil.getCacheCurrentUser().getLoginname()%>", //�û�����
        "D_PASSWORD":"0000", //�û�����
        "D_USERNAME":"<%=SysUtil.getCacheCurrentUser().getName()%>", //�û�����
        "D_DEP_ID":"01", //���ű���
        "D_DEP_NAME":"���������", //��������
        "D_PARAMSTR":"<%=param%>", //������
        "D_R_DATA":"", //Զ�����ݵ�ַ
        "D_COLOR_BORDER":"9394438", //����߿���ɫ
        "D_COLOR_BACK":"15326925", //���������ɫ
        "D_APPLY_LEVEL":"standard", //������Ӧ��ģʽ
        "D_DEF_PRINTER":"", //ȱʡ��ӡ������
        "WIDTH":800,
        "HEIGHT":600
    };
    try{
        qtweb.printReport(params);
    }catch(e){
        alert(e);
    }
}
printReport();
</script>
<%		
	}else{
%>
<OBJECT width ="100%"  height="100%" align ="center" hspace=0 vspace=0
      classid="clsid:569FCF6D-D079-47AC-902C-DCE4398DA4FE"
      codebase="<%=REP_OCX_ADDR%>/CXDY.cab#version=1,0,0,15">		  <!-- ACTIVE FORM������ʾ�����ַ -->
      <PARAM NAME="D_REP_MODE"      VALUE="<%=repmode%>"				> <!-- �������ģʽ -->
      <PARAM NAME="D_HTTP_SERVER"   VALUE="<%=REP_SRV_ADDR%>"          	> <!-- ��������ַ -->
      <PARAM NAME="D_SERVER_DIR"    VALUE="<%=REP_SRV_NAME%>"           > <!-- ����������� -->
      <PARAM NAME="D_REPID"         VALUE="<%=repid%>"                  > <!-- ������� -->
      <PARAM NAME="D_QUERYNAME"     VALUE="<%=queryName%>"              > <!-- ��ѯ���� -->
      <PARAM NAME="D_USERID"        VALUE="<%=SysUtil.getCacheCurrentUser().getLoginname()%>"                        > <!-- �û����� -->
      <PARAM NAME="D_PASSWORD"      VALUE="0000"                        > <!-- �û����� -->
      <PARAM NAME="D_USERNAME"      VALUE="<%=SysUtil.getCacheCurrentUser().getName()%>"                    > <!-- �û����� -->
      <PARAM NAME="D_DEP_ID"        VALUE="01"                          > <!-- ���ű��� -->
      <PARAM NAME="D_DEP_NAME"      VALUE="���������"                    > <!-- �������� -->
      <PARAM NAME="D_PARAMSTR"      VALUE="<%=param%>"                  > <!-- ������ -->
      <PARAM NAME="D_R_DATA"        VALUE=""                            > <!-- Զ�����ݵ�ַ -->
      <PARAM NAME="D_COLOR_BORDER"  VALUE="9394438"                     > <!-- ����߿���ɫ -->
      <PARAM NAME="D_COLOR_BACK"    VALUE="15326925"                    > <!-- ���������ɫ -->
      <PARAM NAME="D_APPLY_LEVEL"   VALUE="standard"                    > <!-- ������Ӧ��ģʽ -->
      <PARAM NAME="D_DEF_PRINTER"   VALUE=""                            > <!-- ȱʡ��ӡ������ -->
    </OBJECT>
 <%
	}
 %>     
</body>
</html>