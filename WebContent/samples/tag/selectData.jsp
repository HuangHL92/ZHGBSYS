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
		{'key':'1','value':'����'},
		{'key':'2','value':'ũ��'},
		{'key':'3','value':'ҽ��'},
		{'key':'4','value':'��ҵ��'},
		{'key':'5','value':'���'}
	]
}