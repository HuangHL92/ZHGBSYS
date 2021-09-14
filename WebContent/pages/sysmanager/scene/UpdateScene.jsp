<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<div id="updateSceneContent">
<table width="60%">
		<tr>
			<td height="16"  colspan="4"></td>
		</tr>
		<tr>
		  	<odin:textEdit property="id" label="场景ID" readonly="true"></odin:textEdit>
	    	<odin:textEdit property="name" label="角色名称" required="true"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="description" label="场景描述"></odin:textEdit>
			<odin:textEdit property="owner" label="场景持有者"></odin:textEdit>
		</tr>
		<tr>
	    	<odin:select property="status" label="状态" data="['1','有效'],['0','无效']" value="1"></odin:select>
		</tr>
		<tr>
			<odin:dateEdit property="createDate"  label="创建时间" ></odin:dateEdit>
		</tr>
		<tr>
			<td height="16" colspan="4"></td>
		</tr>
</table>	
</div>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>修改场景</h3>"/>
	<odin:fill/>
	<odin:buttonForToolBar id="btnUpdate"  text="修改"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnClose" isLast="true" text="取消"/>
</odin:toolBar>
<odin:panel contentEl="updateSceneContent" property="updatescenePanel" topBarId="btnToolBar"></odin:panel>
