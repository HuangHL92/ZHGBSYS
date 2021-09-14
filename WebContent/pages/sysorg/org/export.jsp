<%@ page contentType="text/html; charset=GBK"%>
<%
  String context = request.getParameter("Content");
  response.setHeader("Content-type","application/vnd.ms-xls");
  response.setHeader("Content-Disposition","attachment;filename=excel"+System.currentTimeMillis()+".xls");
  response.setHeader("Expires","0");
  response.setHeader("Cache-Control", "no-cache, must-revalidate");
  response.setHeader("Pragma", "no-cache");
  out.print(context);
%>