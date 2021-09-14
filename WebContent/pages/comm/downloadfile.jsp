<%@page import="com.insigma.odin.framework.util.GlobalNames"%><%@ page language="java" contentType="application/octet-stream; charset=GBK" pageEncoding="GBK"%><%@ page import="com.insigma.odin.framework.sys.file.CommonsFileDownLoadImp" %><%@ page import="com.insigma.odin.framework.sys.file.FileDownLoad" %><%
	FileDownLoad d = new CommonsFileDownLoadImp();
	d.downLoadFile(request,response,GlobalNames.sysConfig.get("DBF_PATH")+"/data.dbf","data.dbf");
%>