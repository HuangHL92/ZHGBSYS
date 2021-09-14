<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@page import="com.insigma.odin.framework.privilege.PrivilegeManager"%>
<%@page import="com.insigma.odin.framework.privilege.vo.UserVO"%>
<%
String g_contextpath = request.getContextPath();
String userid = SysManagerUtils.getUserId();
UserVO user = PrivilegeManager.getInstance().getCueLoginUser();

if("U000".equals(userid)||"1".equals(user.getUsertype())){
	%>
	<title>用户平台授权</title>
	<link rel="icon" href="<%=request.getContextPath()%>/image/logo_wx.png" type="image/x-icon" />
	<link rel="shortcut icon" href="<%=request.getContextPath()%>/logo_wx.png" type="image/x-icon" />
	<body leftmargin="0" topmargin="0">
		<Iframe id="2c90973d6cd70f64016d0494cf6500ba" width="100%" height="100%" scrolling="auto" frameborder="no"  border="0"  marginwidth="0"  marginheight="0" src="<%=g_contextpath %>/radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysmanager.authority.UsersRoot"
	 	style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>
	</body>
	<%
}else{
	String sign = (String)request.getSession().getAttribute("gbSign");
	System.out.println("sign----------:"+sign);
	if("ganbu".equals(sign)||"xitong".equals(sign)||"renmian".equals(sign)||"zhonghechaxun".equals(sign)||"banzi".equals(sign)||"Family".equals(sign)||"huiyi".equals(sign)){
		%>
		<%@include file="MainX.jsp" %>
	<%
	}else{
		%>
		<%@include file="sysIndex.jsp" %>
		
		<%
	}
	
}
%>

<%-- <%@include file="MainPage.jsp"%>  --%>


<script type="text/javascript">
var ht = document.documentElement.clientHeight;
function setIframeHeight(iframe) {
	if (iframe) {
		var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
		if (iframeWin.document.body) {
			iframe.height = ht;
		}
	}
};

	window.onload = function () {
		if(document.getElementById('2c90973d6cd70f64016d0494cf6500ba')){
			setIframeHeight(document.getElementById('2c90973d6cd70f64016d0494cf6500ba'));
		}
	};
</script>