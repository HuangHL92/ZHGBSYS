<%@page import="com.insigma.odin.framework.security.SHA1"%>
<%@page import="com.insigma.odin.framework.privilege.util.HashCodeUtil"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%
	out.println("<h3>“我”字节长度："+"我".getBytes().length+"</h3>");
	out.println("<h3>file.encoding编码为："+System.getProperty("file.encoding")+"</h3>");
	String dv1_self = "089575cfd2c7c48666025e88b5bffcd2641d7316";
	String dv1 = HashCodeUtil.getObjectHashCode("我");
	String dv2 = new SHA1().getDigestOfString("我".getBytes()).toLowerCase();
	out.println("<h3>散列算法对“我”散列后应该是："+dv1_self+"</h3>");
	out.println("<h3>当前1是："+dv1+"</h3>");
	out.println("<h3>当前2是："+dv2+"</h3>");
%>