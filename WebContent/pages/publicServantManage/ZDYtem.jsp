<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.ZDYtemPageModel"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin-commform.tld" prefix="odin"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title></title>
<odin:commformhead/>
<odin:commformMDParam></odin:commformMDParam>
</head>
<body>
<table>
<tr >
<td height="20">
</td>
</tr>
<tr>
<td width="50">
</td>
<td><label style="font-size: 12">模板名   </label></td>
<td width="5"></td>
<td>
<SELECT NAME="name" style="width:120px" id="personinfo" ></SELECT>
</td>
<td>
</td>
</tr>
<td>
</td>
<tr >

<td height="10">
</td>
</tr>
<tr>
	<td width="70"></td>
	<td align="center" colspan="3">
		<input type="button" style="cursor:hand;" onclick="saveToDb()" value="&nbsp;&nbsp;确定&nbsp;&nbsp;">
	</td>
</tr>
	
</table>


</body>
<%
	ZDYtemPageModel cho = new ZDYtemPageModel();
	String value = cho.init();
	out.write(value);
%>
<script type="text/javascript">
function saveToDb(){
	var value = document.getElementById('personinfo').value;
	parent.showtem(value);
	parent.doHiddenPupWin();
}
Ext.onReady(
		function(){
			pageinit();
	    }
	);
</script>

</html>