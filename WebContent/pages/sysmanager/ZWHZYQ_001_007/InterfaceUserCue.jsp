<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<html>
<head>
</head>
<body>
	<table style="margin: 10px 10px">
		<tr>
			<td>
				<odin:textEdit property="userName" required="true" label="�û���" width="213" maxlength="100" validator="check" invalidText="ע:ֻ��������ĸ,���������ֺ��»���"/>
			<td>
		</tr>
		<tr>
			<td>
				<odin:textEdit property="password" inputType="password" required="true" label="����" width="213" maxlength="100"/>
			<td>
		</tr>
		<tr>
			<td>
				<odin:textEdit property="realName" required="true" label="����" width="213" maxlength="100"/>
			<td>
		</tr>
		<tr>
			<td colspan="2" align="right">
				<odin:button text="&nbsp;ȷ&nbsp;��&nbsp;" property="save"/>
			</td>
			<td align="right">
				<odin:button text="&nbsp;ȡ&nbsp;��&nbsp;" property="cancel"/>
			</td>
		</tr>
	</table>
</body>
<script type="text/javascript">
   	function check(){
		var userName = document.all.userName.value; 
		var pattern = /^([A-Z|a-z|0-9|\_]+)*$/;
		flag = pattern.test(userName);
		if(!flag){
			return false;
		}else{
			return true;
		}
	}
</script>
</html>