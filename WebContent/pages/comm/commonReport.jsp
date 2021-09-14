<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.insigma.odin.framework.persistence.HBUtil" %>
<%@ page import="com.insigma.odin.framework.sys.SysfunctionManager" %>
<%@ page import="com.insigma.odin.framework.privilege.vo.FunctionVO" %>
<%@ page import="com.insigma.odin.framework.util.*" %>
<%@ page import="com.insigma.siis.local.pagemodel.comm.CommonQueryBS" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>单据打印</title>
</head>
<%
	String REP_SRV_ADDR=HBUtil.getValueFromTab("aaa005","aa01","aaa001='REP_SRV_ADDR'");
	String REP_SRV_NAME=HBUtil.getValueFromTab("aaa005","aa01","aaa001='REP_SRV_NAME'");
	String REP_OCX_ADDR=HBUtil.getValueFromTab("aaa005","aa01","aaa001='REP_OCX_ADDR'");
	
	//金伟：2009-12-28  增加了报表前置代理
	String REP_PROXY_WAY=HBUtil.getValueFromTab("aaa005","aa01","aaa001='REP_PROXY_WAY'"); 
	String REP_PROXY_ADDR = null;
	//0：不采用代理
	//1：采用代理方式（代理服务地址 如“http://http://192.168.70.140:8082”)
	if(REP_PROXY_WAY!=null && !REP_PROXY_WAY.equals("0")){//采用代理方式
		String REP_PROXY_PORT=HBUtil.getValueFromTab("aaa005","aa01","aaa001='REP_PROXY_PORT'");
		if(REP_PROXY_WAY.equals("1")){
			REP_PROXY_ADDR = "http://"+request.getLocalAddr()+":"+REP_PROXY_PORT;
		}
		REP_SRV_ADDR = REP_PROXY_ADDR;
		REP_OCX_ADDR = REP_PROXY_ADDR;
		String isLog=HBUtil.getValueFromTab("aaa105","aa01","aaa001='REP_PROXY_PORT'");
		if(isLog!=null && isLog.equals("1")){
			CommonQueryBS.systemOut("REP_SRV_ADDR："+REP_SRV_ADDR);
			CommonQueryBS.systemOut("REP_SRV_NAME："+REP_SRV_NAME);
			CommonQueryBS.systemOut("REP_OCX_ADDR："+REP_OCX_ADDR);
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
        "D_REP_MODE":"<%=repmode%>", // 报表调用模式 
        "D_HTTP_SERVER":"<%=REP_SRV_ADDR%>", //报表服务地址
        "D_SERVER_DIR":"<%=REP_SRV_NAME%>", //报表服务名称
        "D_REPID":"<%=repid%>", //报表编码
        "D_QUERYNAME":"<%=queryName%>", //查询编码
        "D_USERID":"<%=SysUtil.getCacheCurrentUser().getLoginname()%>", //用户编码
        "D_PASSWORD":"0000", //用户密码
        "D_USERNAME":"<%=SysUtil.getCacheCurrentUser().getName()%>", //用户名称
        "D_DEP_ID":"01", //部门编码
        "D_DEP_NAME":"计算机中心", //部门名称
        "D_PARAMSTR":"<%=param%>", //参数串
        "D_R_DATA":"", //远程数据地址
        "D_COLOR_BORDER":"9394438", //插件边框颜色
        "D_COLOR_BACK":"15326925", //插件背景颜色
        "D_APPLY_LEVEL":"standard", //报表插件应用模式
        "D_DEF_PRINTER":"", //缺省打印机名称
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
      codebase="<%=REP_OCX_ADDR%>/CXDY.cab#version=1,0,0,15">		  <!-- ACTIVE FORM报表显示插件地址 -->
      <PARAM NAME="D_REP_MODE"      VALUE="<%=repmode%>"				> <!-- 报表调用模式 -->
      <PARAM NAME="D_HTTP_SERVER"   VALUE="<%=REP_SRV_ADDR%>"          	> <!-- 报表服务地址 -->
      <PARAM NAME="D_SERVER_DIR"    VALUE="<%=REP_SRV_NAME%>"           > <!-- 报表服务名称 -->
      <PARAM NAME="D_REPID"         VALUE="<%=repid%>"                  > <!-- 报表编码 -->
      <PARAM NAME="D_QUERYNAME"     VALUE="<%=queryName%>"              > <!-- 查询编码 -->
      <PARAM NAME="D_USERID"        VALUE="<%=SysUtil.getCacheCurrentUser().getLoginname()%>"                        > <!-- 用户编码 -->
      <PARAM NAME="D_PASSWORD"      VALUE="0000"                        > <!-- 用户密码 -->
      <PARAM NAME="D_USERNAME"      VALUE="<%=SysUtil.getCacheCurrentUser().getName()%>"                    > <!-- 用户名称 -->
      <PARAM NAME="D_DEP_ID"        VALUE="01"                          > <!-- 部门编码 -->
      <PARAM NAME="D_DEP_NAME"      VALUE="计算机中心"                    > <!-- 部门名称 -->
      <PARAM NAME="D_PARAMSTR"      VALUE="<%=param%>"                  > <!-- 参数串 -->
      <PARAM NAME="D_R_DATA"        VALUE=""                            > <!-- 远程数据地址 -->
      <PARAM NAME="D_COLOR_BORDER"  VALUE="9394438"                     > <!-- 插件边框颜色 -->
      <PARAM NAME="D_COLOR_BACK"    VALUE="15326925"                    > <!-- 插件背景颜色 -->
      <PARAM NAME="D_APPLY_LEVEL"   VALUE="standard"                    > <!-- 报表插件应用模式 -->
      <PARAM NAME="D_DEF_PRINTER"   VALUE=""                            > <!-- 缺省打印机名称 -->
    </OBJECT>
 <%
	}
 %>     
</body>
</html>