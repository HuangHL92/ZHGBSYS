<%@page import="java.util.Date,java.util.Calendar"%><%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%><%
	Date nowDate = Calendar.getInstance().getTime();
	long temp = nowDate.getTime()-session.getLastAccessedTime();
	long nextInterval = session.getMaxInactiveInterval()*1000-temp-60000;
	out.print(nextInterval);	
%>