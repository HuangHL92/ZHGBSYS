<%@page import="com.insigma.odin.framework.util.commform.SysConst"%>
<%@page import="com.insigma.odin.framework.util.commform.ObjectUtil"%>
<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%
	if(request.getSession().getAttribute(GlobalNames.CURRENT_USER)!=null){ //ԭ���ѵ�¼
		request.getSession().invalidate(); //ʹԭ��SessionʧЧ
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