<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%
String mainXdbid = request.getParameter("mainXdbid");
%>
<odin:hidden property="mainXdbid" value="<%=mainXdbid %>"/>
