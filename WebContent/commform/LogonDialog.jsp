<%@page import="com.insigma.odin.framework.util.commform.SysConst"%>
<%@page import="com.insigma.odin.framework.util.commform.ObjectUtil"%>
<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%
	if(request.getSession().getAttribute(GlobalNames.CURRENT_USER)!=null){ //原来已登录
		request.getSession().invalidate(); //使原有Session失效
	}
%>
<%
	String systype = SysConst.getServerSystype();
	if (ObjectUtil.equals(systype, "")) {
		systype = "default";
	}
	String pageurl = "LogonDialog_" + systype + ".jsp";
%>
<jsp:include page="<%=pageurl%>" />