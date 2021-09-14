<%@ page contentType="text/html; charset=GBK" language="java"%>

<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<sicp3:base/>
<sicp3:body>
<form name="ff" method="post" action="#">
<sicp3:userList property="userlist" onRbuttonclick="TagaddUser(document.forms[0].userlist)"/>
</form>
</sicp3:body>
