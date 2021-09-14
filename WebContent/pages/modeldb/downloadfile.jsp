<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%@ page language="java" contentType="application/octet-stream; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.insigma.odin.framework.sys.file.CommonsFileDownLoadImp" %>
<%@ page import="com.insigma.odin.framework.sys.file.FileDownLoad" %><%
	response.setContentType("text/html");
	response.setCharacterEncoding("GBK");
    String filename = request.getParameter("filename"); 
    filename = new String(filename.getBytes("iso8859-1"),"GBK");
	String sys = System.getProperty("os.name").toUpperCase();
	String dir = GlobalNames.sysConfig.get("DOWNLOAD_PATH");
	if(sys.startsWith("WIN")){
		dir = GlobalNames.sysConfig.get("DOWNLOAD_PATH_WIN");
     }
	String filepath=dir+System.getProperty("file.separator")+filename;
	//String filepath=dir+System.getProperty("file.separator")+filename+".json";
	
	FileDownLoad d = new CommonsFileDownLoadImp();
	response.reset();
	d.downLoadFile(request,response,filepath,filename);
	//d.downLoadFile(request,response,filepath,filename+".json");
	out.clear();  
	out = pageContext.pushBody();  
%>
