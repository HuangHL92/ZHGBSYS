<%@page import="com.insigma.odin.framework.privilege.PrivilegeManager"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.insigma.odin.framework.persistence.HBUtil" %>
<%
	HBUtil.getHBSession().beginTransaction();
	PrivilegeManager.getInstance(HBUtil.getHBSession().getSession()).reSetHashCodeByTabName("");
	HBUtil.getHBSession().getTransaction().commit();
%>