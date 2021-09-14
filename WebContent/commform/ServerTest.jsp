<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="com.insigma.odin.framework.persistence.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>INSIIS Server Test Page</title>
</head>
<body>
  Server is running, status ok.<br>
 <%
 	Map envmap = System.getenv();
 	Set envkeyset = envmap.keySet();
 	Iterator it = envkeyset.iterator();
 	while (it.hasNext()){
 		Object key = it.next();
 		String strvalue = envmap.get(key).toString();
 		String strkey = key.toString();
 %>
 <%=strkey %>=<%= strvalue %><br>
 <%
 	}
 %>
 <br>
 <%
 	try{
 		HBSession hbsess = HBUtil.getHBSession();
 		%>
 		Sysfunction count:
 		<%= hbsess.createQuery("select count(*) from Sysfunction").uniqueResult().toString()%>
 		<br>
 		<%
 	}
 	catch(Exception e){
 		%>
 		Error while performing Hibernate test.
 		<%= e.getMessage() %>
 		<%
 	}
 %>
 
</body>
</html>