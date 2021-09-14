<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.insigma.odin.framework.persistence.HBUtil" %>
<%@ page import="com.insigma.odin.framework.util.SysUtil" %>
<%@ page import="com.insigma.siis.local.pagemodel.comm.CommonQueryBS" %> 
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
	
	
	String repid=request.getParameter("repid");
	String queryName=request.getParameter("queryname");
	String param=request.getParameter("param");
	param=java.net.URLDecoder.decode(param,"utf-8");
	//String preview=request.getParameter("preview");
	String repmode=request.getParameter("repmode");
	if(queryName==null){
		queryName="";
	}
	//if(preview==null){
	//	preview="true";
	//}
	//String repmode="3";
	//if(preview.equalsIgnoreCase("true")){
	//	repmode="3";
	//}else if(preview.equalsIgnoreCase("false")){
	//	repmode="2";
	//}
	boolean isWorkpf = SysUtil.isWorkpf(request);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title><%=(repmode.equals("2")?"AUTO CLOSE PRINT":"单据打印")%></title>
</head>
<body Leftmargin="0" topmargin="0"  rightmargin="0" bottommargin="0" onload="autoCloseWin()">
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
<OBJECT width ="100%"  height="100%" align =center hspace=0 vspace=0
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
 <script type="text/javascript">
 function autoCloseWin(){
 	var repmode = "<%=repmode%>";
	if(repmode == "2" || repmode == "3"){ //预览repmode=3和不预览repmode=2，都关闭那个窗口
		window.setTimeout("window.close();",1500);
	}
 }
 </script>   
</body>
</html>