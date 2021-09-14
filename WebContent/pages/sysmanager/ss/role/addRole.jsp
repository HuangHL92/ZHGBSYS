<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<div id="addRoleContent">
<table width="60%">
		<tr>
			<td height="16"  colspan="4"></td>
		</tr>
		<tr>
	    	<odin:textEdit property="name" label="角色名称" required="true"></odin:textEdit>
	    	<odin:textEdit property="desc" label="角色描述"></odin:textEdit>
		</tr>
		<tr>
			<odin:select property="owner" label="角色持有者" codeType="USER"></odin:select>
		</tr>
		<tr>
			<td height="26" colspan="4"></td>
		</tr>
</table>	
</div>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>新建角色</h3>"/>
	<odin:fill/>
	<odin:buttonForToolBar id="btnSave"  text="保存"  icon="images/save.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnClose" isLast="true" text="关闭"  icon="images/back.gif" cls="x-btn-text-icon"/>
</odin:toolBar>
<odin:panel contentEl="addRoleContent" property="addRolePanel" topBarId="btnToolBar"></odin:panel>


