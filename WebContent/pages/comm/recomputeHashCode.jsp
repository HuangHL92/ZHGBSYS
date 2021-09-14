<%@page import="com.insigma.odin.framework.privilege.PrivilegeManager"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%
	HBUtil.getHBSession().getSession().beginTransaction();
	PrivilegeManager pm = PrivilegeManager.getInstance(HBUtil.getHBSession().getSession());
	pm.reSetHashCodeByTabName("SmtFunction,SmtResource,SmtRole,SmtUser");
	HBUtil.getHBSession().getSession().getTransaction().commit();
%>