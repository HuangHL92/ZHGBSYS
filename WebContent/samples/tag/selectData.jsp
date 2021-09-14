<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ page import="com.insigma.siis.local.pagemodel.comm.CommonQueryBS" %>
 <%
     CommonQueryBS.systemOut("query:"+request.getParameter("query"));
 	 CommonQueryBS.systemOut("start:"+request.getParameter("start"));
 	 CommonQueryBS.systemOut("limit:"+request.getParameter("limit"));
 %>   
{
	'totalCount':5,
	'data':[
		{'key':'1','value':'军人'},
		{'key':'2','value':'农民'},
		{'key':'3','value':'医生'},
		{'key':'4','value':'无业者'},
		{'key':'5','value':'软件'}
	]
}