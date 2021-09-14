<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ include file="/comOpenWinInit2.jsp" %>
<div id="addRoleContent">
<table width="100%">
		<tr>
			<td height="20"  colspan="4"></td>
		</tr>
		<tr>
	    	<odin:textEdit property="name" label="角色名称" required="true"></odin:textEdit>
	    	<odin:textEdit property="desc" label="角色描述"></odin:textEdit>
		</tr>
		<tr>
			<td height="20" colspan="4"></td>
		</tr>
</table>	
</div>
<odin:toolBar property="btnToolBar">
	<odin:fill/>
	<odin:buttonForToolBar id="btnSave" text="保存"  icon="images/save.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="addRoleContent" property="addRolePanel" topBarId="btnToolBar"></odin:panel>

<script type="text/javascript">
</script>
