<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<div id="addRoleContent">
<table width="60%">
		<tr>
			<td height="16"  colspan="4"></td>
		</tr>
		<tr>
	    	<odin:textEdit property="name" label="��ɫ����" required="true"></odin:textEdit>
	    	<odin:textEdit property="desc" label="��ɫ����"></odin:textEdit>
		</tr>
		<tr>
			<odin:select property="owner" label="��ɫ������" codeType="USER"></odin:select>
		</tr>
		<tr>
			<td height="26" colspan="4"></td>
		</tr>
</table>	
</div>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>�½���ɫ</h3>"/>
	<odin:fill/>
	<odin:buttonForToolBar id="btnSave"  text="����"  icon="images/save.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnClose" isLast="true" text="�ر�"  icon="images/back.gif" cls="x-btn-text-icon"/>
</odin:toolBar>
<odin:panel contentEl="addRoleContent" property="addRolePanel" topBarId="btnToolBar"></odin:panel>


