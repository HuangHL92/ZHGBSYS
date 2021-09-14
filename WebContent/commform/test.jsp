<%@page import="com.insigma.siis.util.SysConst"%>
<%@page import="com.insigma.siis.util.ObjectUtil"%>
<%
	String systype = SysConst.getServerSystype();
	if (ObjectUtil.equals(systype, "")) {
		systype = "default";
	}
	String pageurl = "draft_" + systype + ".jsp";
%>
<jsp:include page="<%=pageurl%>" />