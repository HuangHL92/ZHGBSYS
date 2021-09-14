<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@page import="com.insigma.odin.framework.comm.query.PageQueryData"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.insigma.odin.framework.sys.comm.CommQueryBS"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.insigma.siis.local.epsoft.util.LogUtil"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>系统退出</title>
</head>
<body>

<%

	List list1 = new ArrayList();
	new LogUtil().createLog("12", "", "", "", "", list1);

	CommQueryBS bs = new CommQueryBS();
	PageQueryData data = bs.query("select t.appname from smt_app t,smt_function f where f.isproxy='1' and f.appid=t.appid group by t.appname","SQL",-1,10);
	List<HashMap<Object, Object>> localApps = (List<HashMap<Object, Object>>)data.getData();
	if(localApps!=null && localApps.size()>0){
		for(int i=0;i<localApps.size();i++){
			HashMap<Object, Object> map = localApps.get(i);
			out.println("<iframe scrolling=no width=0 height=0 src=\"/"+map.get("appname")+"/logoff.jsp\"></iframe>");
		}
	
	}
	
	
	request.getSession().invalidate();
	out.println("<script>window.top.location.href='"+request.getContextPath()+"/';</script>");
%>


</body>
</html>