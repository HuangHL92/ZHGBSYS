<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<table id="basicTable">
		<tr>
			<td height="10"></td>
			<odin:hidden property="appid"/>
		</tr>
		<tr>
			<odin:textEdit property="appName" label="Ӧ������" required="true" />
			<odin:textEdit property="appCode" label="Ӧ�ñ���" />
		</tr>
		<tr>
			<odin:textEdit property="appTitle" label="Ӧ�ñ�ǩ" />
			<odin:textEdit property="appType" label="Ӧ�����" />
		</tr>
		<tr>
			<odin:select property="parent" codeType="PROXY" label="��Ӧ�û��߽���Ӧ��"/>
			<odin:textEdit property="appDesc" label="����" />
		</tr>
		<tr>
			<td width="50" height="50"></td>
			<td align="right" colspan="3"><odin:button property="save" text="����"></odin:button></td>
		</tr>
</table>