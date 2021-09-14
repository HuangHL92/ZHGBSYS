<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<table id="basicTable">
		<tr>
			<td height="10"></td>
			<odin:hidden property="appid"/>
		</tr>
		<tr>
			<odin:textEdit property="appName" label="应用名称" required="true" />
			<odin:textEdit property="appCode" label="应用编码" />
		</tr>
		<tr>
			<odin:textEdit property="appTitle" label="应用标签" />
			<odin:textEdit property="appType" label="应用类别" />
		</tr>
		<tr>
			<odin:select property="parent" codeType="PROXY" label="父应用或者叫主应用"/>
			<odin:textEdit property="appDesc" label="描述" />
		</tr>
		<tr>
			<td width="50" height="50"></td>
			<td align="right" colspan="3"><odin:button property="save" text="保存"></odin:button></td>
		</tr>
</table>