<%@page import="com.insigma.odin.framework.security.SHA1"%>
<%@page import="com.insigma.odin.framework.privilege.util.HashCodeUtil"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%
	out.println("<h3>���ҡ��ֽڳ��ȣ�"+"��".getBytes().length+"</h3>");
	out.println("<h3>file.encoding����Ϊ��"+System.getProperty("file.encoding")+"</h3>");
	String dv1_self = "089575cfd2c7c48666025e88b5bffcd2641d7316";
	String dv1 = HashCodeUtil.getObjectHashCode("��");
	String dv2 = new SHA1().getDigestOfString("��".getBytes()).toLowerCase();
	out.println("<h3>ɢ���㷨�ԡ��ҡ�ɢ�к�Ӧ���ǣ�"+dv1_self+"</h3>");
	out.println("<h3>��ǰ1�ǣ�"+dv1+"</h3>");
	out.println("<h3>��ǰ2�ǣ�"+dv2+"</h3>");
%>