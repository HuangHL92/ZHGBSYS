<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ page import="com.lbs.commons.GlobalNames"%>
<html>
<head>
<title> Frame Testing </title>
</head>

<frameset cols="200,200,*"  >
        <frame src="<%=request.getContextPath()%>/sysmanager/roleAction.do?method=findAllRoles&type=role" name="rolelist" marginwidth="0" marginheight="0" >
        <frame src="<%=request.getContextPath()%>/sysmanager/moduleList.do" name="modulelist" marginwidth="0" marginheight="0">        
        <frame src="<%=request.getContextPath()%>/sysmanager/rightTree.do" name="right" marginwidth="0" marginheight="0">
</frameset>
<noframes></noframes>
</html>
