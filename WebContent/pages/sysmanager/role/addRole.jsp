<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

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
		    <odin:select2 property="OwnSystem" label="用户类型"  required="true" value="1" data="['1','管理员'],['2','普通用户']"  ></odin:select2>
		</tr>
		<%--<tr>
		    <odin:select2 property="OwnSystem" label="角色所属系统" data="['1','汇总版系统'],['2','统计系统']"  onchange="displayRoleID"></odin:select2>
		</tr>
		 <tr id="roleidTR" style="display:none;">
	    	<odin:textEdit property="roleid" label="角色编码" ></odin:textEdit>
		</tr> --%>
		<tr>
			<td height="20" colspan="4"></td>
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

<script type="text/javascript">
<!--

//-->
	//当角色所属系统选择‘统计系统’时，显示角色编码输入框
	function displayRoleID(){
		var OwnSystem = document.getElementById("OwnSystem").value;
		if(OwnSystem == '2'){
			document.getElementById("roleidTR").style.display='block';
		}
		if(OwnSystem == '1'){
			document.getElementById("roleidTR").style.display='none';
		}
	}
</script>
