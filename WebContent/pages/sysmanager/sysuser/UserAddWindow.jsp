<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.lbs.cp.util.SysManagerUtil"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>

<odin:groupBox title="用户基本信息" width="500" >
	<table border="0" id="myform" align="center" width="100%"
		cellpadding="0" cellspacing="0">
		<tr>
			<odin:textEdit property="loginname" label="用户登录名" required="true" maxlength="21"></odin:textEdit>
		</tr>
		<tr>
		 	<tags:PublicTextIconEdit3 property="ssjg" label="所属范围" readonly="true" codetype="orgTreeJsonData" required="true"></tags:PublicTextIconEdit3>
		</tr>
		<tr>
			<odin:textEdit property="username" label="姓名" required="true"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="password" inputType="password" label="&nbsp;密码" required="true"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="surepassword" inputType="password" label="&nbsp;确认密码" required="true"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="work" label="&nbsp;工作单位"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="mobile" label="&nbsp;手机"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="tel" label="&nbsp;办公电话"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="emial" label="&nbsp;邮件"></odin:textEdit>
		</tr>
		<tr>
			<odin:select property="useful" label="&nbsp;用户状态" required="true"></odin:select>
		</tr>
		<tr>
			<odin:select property="usertype" label="&nbsp;用户类型" required="true" data="['1','系统管理员'],['3','安全保密员'],['4','安全审计员'],['2','普通用户']"></odin:select>
		</tr>
		<tr> 
			<td></td>
			<td align="center">
				<odin:button text="保存" property="savebut"></odin:button>
			</td>
		</tr>
	</table>
</odin:groupBox>
