<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<div id="updateSceneContent">
<table width="60%">
		<tr>
			<td height="16"  colspan="4"></td>
		</tr>
		<tr>
		  	<odin:textEdit property="id" label="����ID" readonly="true"></odin:textEdit>
	    	<odin:textEdit property="name" label="��ɫ����" required="true"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="description" label="��������"></odin:textEdit>
			<odin:textEdit property="owner" label="����������"></odin:textEdit>
		</tr>
		<tr>
	    	<odin:select property="status" label="״̬" data="['1','��Ч'],['0','��Ч']" value="1"></odin:select>
		</tr>
		<tr>
			<odin:dateEdit property="createDate"  label="����ʱ��" ></odin:dateEdit>
		</tr>
		<tr>
			<td height="16" colspan="4"></td>
		</tr>
</table>	
</div>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>�޸ĳ���</h3>"/>
	<odin:fill/>
	<odin:buttonForToolBar id="btnUpdate"  text="�޸�"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnClose" isLast="true" text="ȡ��"/>
</odin:toolBar>
<odin:panel contentEl="updateSceneContent" property="updatescenePanel" topBarId="btnToolBar"></odin:panel>
