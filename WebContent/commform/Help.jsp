<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@page import="com.insigma.odin.framework.util.commform.SysConst"%>
<%@page import="com.insigma.odin.framework.util.commform.ObjectUtil"%>
<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%
	if (request.getSession().getAttribute(GlobalNames.CURRENT_USER) == null) { // δ��½
		String errorMsg = "û�е�¼��ʱ�������µ�¼��";
		request.getSession().setAttribute(GlobalNames.EXCEPTION_KEY, new String[] { errorMsg });
		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + GlobalNames.LOGON_DIALOG_PAGE));
		return;
	}
	String systype = SysConst.getServerSystype();
	if (ObjectUtil.equals(systype, "")) {
		systype = "default";
	}
	String pageurl = "Help_" + systype + ".jsp";
%>
<jsp:include page="<%=pageurl%>" />

