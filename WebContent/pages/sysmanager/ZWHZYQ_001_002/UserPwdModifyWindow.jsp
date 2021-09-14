<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<odin:groupBox title="用户密码修改" width="300">
	<table border="0" id="myform" align="center" width="100%"
		cellpadding="0" cellspacing="0">
		<tr>
			<td></td>
			<odin:textEdit property="oldPwd" label="原密码" inputType="password"></odin:textEdit>
		</tr>
		<tr>
			<td></td>
		<odin:textEdit property="newPwd" label="&nbsp;新密码" inputType="password"></odin:textEdit></tr>
		<tr>
		<tr>
			<td></td>
		<odin:textEdit property="newPwd1" label="&nbsp;确认密码" inputType="password"></odin:textEdit></tr>
		<tr>
			<td align="right" colspan="6"><odin:button text="保存"
				property="savebut"></odin:button>
			</td>
		</tr>
	</table>
</odin:groupBox>
