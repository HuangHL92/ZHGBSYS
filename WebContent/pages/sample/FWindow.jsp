<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<table>
	<tr>
		<odin:textEdit property="loginname" required="true" label="用户登录名"></odin:textEdit>
		<odin:textEdit property="username" label="用户名"></odin:textEdit>
	</tr>
	<tr>
		<odin:textEdit property="password" inputType="password" label="密码"></odin:textEdit>
		<odin:select property="status" label="状态">['1','有效'],['0','无效']</odin:select>
	</tr>
	<tr> 
		<odin:textEdit property="description" label="描述"></odin:textEdit>
		<td>
			<odin:button text="保存" property="savebut"></odin:button> 
		<td>
	</tr>
</table>