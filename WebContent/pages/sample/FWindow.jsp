<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<table>
	<tr>
		<odin:textEdit property="loginname" required="true" label="�û���¼��"></odin:textEdit>
		<odin:textEdit property="username" label="�û���"></odin:textEdit>
	</tr>
	<tr>
		<odin:textEdit property="password" inputType="password" label="����"></odin:textEdit>
		<odin:select property="status" label="״̬">['1','��Ч'],['0','��Ч']</odin:select>
	</tr>
	<tr> 
		<odin:textEdit property="description" label="����"></odin:textEdit>
		<td>
			<odin:button text="����" property="savebut"></odin:button> 
		<td>
	</tr>
</table>