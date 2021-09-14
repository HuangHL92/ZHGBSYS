<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%@ page language="java" contentType="application/octet-stream; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.insigma.odin.framework.sys.file.CommonsFileDownLoadImp" %>
<%@ page import="com.insigma.odin.framework.sys.file.FileDownLoad" %><%
	String filename=request.getParameter("filename"); 
	String filepath=GlobalNames.sysConfig.get("CARDFILE_DOWN_PATH")+System.getProperty("file.separator")+filename;
	FileDownLoad d = new CommonsFileDownLoadImp();
	response.reset();
	d.downLoadFile(request,response,filepath,filename);
	out.clear();  
	out = pageContext.pushBody();  
%>
