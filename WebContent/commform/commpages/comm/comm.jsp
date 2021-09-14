<%@include file="/commform/basejs/loading/loading.jsp"%>
<%@page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@taglib uri="/WEB-INF/odin-commform.tld" prefix="odin"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="java.io.InputStream"%>
<%@page import="com.insigma.odin.framework.util.commform.CertUtil"%>
<%@page import="com.insigma.odin.framework.util.commform.ObjectUtil"%>
<%@page import="com.insigma.odin.framework.commform.local.sys.LoginManager"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title></title>

</head>
<body>

<odin:commformhead />
<odin:commformMDParam></odin:commformMDParam>
<%
	Boolean isNeedSign = CertUtil.isNeedSign();
%>
<link rel="STYLESHEET" type="text/css" href="<%=request.getContextPath()%>/commform/basejs/tree/dhtmlxtree.css">	
<script  src="<%=request.getContextPath()%>/commform/basejs/tree/dhtmlxcommon.js"></script>
<script  src="<%=request.getContextPath()%>/commform/basejs/tree/dhtmlxtree.js"></script>
<script type="text/javascript">
 var isNeedSign=<%=isNeedSign%>;
</script>
<odin:base>
	<odin:form action="/pages/comm/commAction.do?method=doAction"
		method="post">
		<%
			int n = (request.getParameter("method").indexOf("-") >= 0 ? request.getParameter("method").indexOf("-") : request.getParameter("method").length());
					String pageurl = request.getParameter("method").substring(0, n).replace(".", "/").toLowerCase() + ".jsp";
					if (pageurl.equals("psquery.jsp") || pageurl.equals("cpquery.jsp") || pageurl.equals("psidquery.jsp") || pageurl.equals("psidnew.jsp") || pageurl.equals("orirollback.jsp") || pageurl.equals("oriaudit.jsp") || pageurl.equals("commimp.jsp")) {//页面特殊处理
						pageurl = "/commform/commpages/comm/" + pageurl;
					} else {
						pageurl = "/commform/pages/" + pageurl;
					}
					String repurl = ""; //报表路径
					if (pageurl.indexOf("/pages/rep/") >= 0) { //报表模式
						String HTMLREP_SRV_ADDR = HBUtil.getValueFromTab("aaa005", "aa01", "aaa001='HTMLREP_SRV_ADDR'");
						String HTMLREP_SRV_NAME = HBUtil.getValueFromTab("aaa005", "aa01", "aaa001='HTMLREP_SRV_NAME'");
						String httpPath = (HTMLREP_SRV_ADDR == null ? "http://" + request.getServerName() + ":" + request.getServerPort() : HTMLREP_SRV_ADDR);
						String serverName = (HTMLREP_SRV_NAME == null ? "FIRSTONE_REP" : HTMLREP_SRV_NAME);
						String repName = pageurl.substring(pageurl.indexOf("/pages/rep/") + 11);
						repurl = httpPath + "/" + serverName + "/PARAM/" + repName.substring(0, repName.indexOf(".")) + "/" + repName;
					}
					InputStream inputStream = request.getSession().getServletContext().getResourceAsStream(pageurl);
					if (inputStream != null) { //有文件
						inputStream.close();
		%>
		<jsp:include page="<%=pageurl%>" />
		<%
			} else if (!repurl.equals("")) { //报表模式
		%>
		<div id="div_1"></div>
		<%
			} else {
		%>
		<odin:dynamic></odin:dynamic>
		<%
			}
					if (!repurl.equals("")) { //报表模式
						//System.out.println(repurl);
		%>
		<!-- HTML报表打印窗口（仅支持IE） -->
		<iframe src="/blank.htm" id="printIframe" frameborder="0" width="790"
			height="0" onload="doRepOnload(this)"
			onreadystatechange="doRepOnreadystatechange(this)"></iframe>
		<%
			}
		%>
		<script>
	var repurl = "<%=repurl%>"; //设置变量给comm.js使用
</script>
		<!-- 控件单据打印窗口 -->
		<odin:commformwindow id="win_billprint" src="/blank.htm" title="" modal="true"
			width="0" height="0" />
		<!-- 弹出窗口 -->
		<odin:commformwindow id="win_pup" src="/blank.htm" title="" modal="true"
			width="0" height="0" />

	</odin:form>

	<script>
	var currentOpseno = <%=request.getParameter("opseno")%>;
	var currentLoginName = "<%=LoginManager.getCurrentLoginName()%>";
	var currentAab301 = "<%=LoginManager.getCurrentAab301()%>";
	var currentAaa027 = "<%=LoginManager.getCurrentAaa027()%>";
	function logoff(){
		window.top.location.href = '<%=request.getContextPath()%>' + '/logoffAction.do'
	}
	Ext.onReady(
		function(){
			if(currentLoginName != getCurrentLoginName()){
				odin.error("当前操作的用户与登陆的用户不一致！请重新登陆！",logoff);
				return;
			}
			doInit();
	    }
	);
</script>

	<%
		if (ObjectUtil.equals(request.getParameter("disabled"), "true")) {
	%>
	<script>
		currentActionDisabled = true;
	</script>
	<%
		}
	%>

<jsp:include page='/radow/local.jsp'/>
<script>
	odin.ext.onReady(
		function(){
			try{
				if(odin.isWorkpf && typeof getWpfNavBarSetInfo!='undefined'){
					qtweb.setWebInfo(getWpfNavBarSetInfo());
				}
			}catch(e){}
		}
	)
</script>
</odin:base>
</body>

</html>