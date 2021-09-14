<%@ page contentType="text/html; charset=GBK" language="java" %>
<html>
<head>
<title></title>
</head>
<frameset cols="200,*" frameborder="no" border="0" framespacing="0">
  <frame src="<%=request.getContextPath()%>/sysmanager/groupAction.do?method=loadGroupTree" name="left"  marginwidth="0" marginheight="0" >
  <frame src="<%=request.getContextPath()%>/sysmanager/grpauthcookbook.do" name="right" marginwidth="0" marginheight="0">
</frameset>
</html>
