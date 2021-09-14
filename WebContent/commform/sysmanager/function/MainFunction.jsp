<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>Insert title here</title>

</head>
<frameset cols="200,*"  frameborder="no" border="0" framespacing="0">
        <frame src=<sicp3:rewrite page="/functionTree.do"/> name="left" marginwidth="0" marginheight="0"  marginwidth="0" marginheight="0" >
        <frame src=<sicp3:rewrite page="/funauthcookbook.do"/> name="right" marginwidth="0" marginheight="0">
</frameset>
<noframes></noframes>
</html>