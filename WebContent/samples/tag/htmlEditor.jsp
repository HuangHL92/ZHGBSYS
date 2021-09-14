<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%
	String ctxpath = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>标签测试</title>
<odin:head />
</head>

<body>
<odin:base>

</odin:base> 
 <table width="600"><tr>
<odin:htmlTextEdit property="aab011"  value="123" colspan="6" cols="50" rows="10">
</odin:htmlTextEdit>
</tr>
<tr>
<odin:htmlTextEdit property="a1ab011" label="描述信息"  value="123" colspan="6" cols="50" rows="10">
</odin:htmlTextEdit>
</tr>
</table>


<input type="button" value="debug"  onclick="Ext.log('Hello from the Ext console.');return false;"> 
</body>
</html>
