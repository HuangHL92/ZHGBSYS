<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3" %>
<html>
<head>
<title>╫ги╚йзх╗</title>
</head>
<frameset cols="200,*"  frameborder="no" border="0" framespacing="0">
        <frame src=<sicp3:rewrite page="/dataPopedomAction.do?method=findAllPopedom"/> name="popedomlist" marginwidth="0" marginheight="0" >
        <frame src=<sicp3:rewrite page="/authcookbook.do"/> name="right" marginwidth="0" marginheight="0">
</frameset>
<noframes></noframes>
</html>