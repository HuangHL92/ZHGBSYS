<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<table>
	<table>
	<tr>
		<td height="10"></td>
	</tr>
	<tr>
		<odin:textEdit property="name" label="用户组名称" required="true" />
	</tr>
	<tr>
		<td width="50" height="50"></td>
		<td><odin:button text="保存" property="saveBtn"></odin:button></td>
	</tr>
</table>
	


