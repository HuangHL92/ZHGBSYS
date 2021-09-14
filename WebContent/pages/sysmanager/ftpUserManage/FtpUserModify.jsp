<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<odin:floatDiv property="tbv" position="up"></odin:floatDiv>
<odin:toolBar property="btnToolBar" applyTo="tbv">
	<odin:fill />
	<odin:buttonForToolBar id="btnSave" text="保存" icon="images/save.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<br/><br/>
<odin:groupBox title="FTP用户信息">
<table width="100%" cellpadding="0" cellspacing="0" >
	<tr>
		<tags:PublicTextIconEdit3  codetype="orgTreeJsonData" required="true" label="机构名称" property="depict" readonly="true"/>
	</tr>
	<tr>
		<odin:textEdit property="userid" label="用户名" required="true" maxlength="64"/>
	</tr>
	<tr>
		<odin:textEdit property="userpassword" inputType="password" label="密码" required="true" maxlength="64"/>
	</tr>
	<tr>
		<odin:select2 property="enableflag" label="启用状态"  codeType="HAS" required="true" value="true"></odin:select2>
	</tr>
	<tr>
		<odin:select2 property="writepermission" label="读写权限"  codeType="HAS" required="true" value="true"></odin:select2>
	</tr>
	<tr>
		<odin:numberEdit property="idletime"  label="空闲时间(S)" required="true" value="300" maxlength="6"/>
	</tr>
	<tr>
		<odin:numberEdit property="uploadrate"  label="上传速率(K/S)" required="true" value="0" maxlength="6"/>
	</tr>
	<tr>
		<odin:numberEdit property="downloadrate"  label="下载速率(K/S)" required="true" value="0" maxlength="6"/>
	</tr>
		<tr>
		<odin:numberEdit property="maxloginnumber"  label="用户最大登录数" required="true" value="0" maxlength="6"/>
	</tr>
	<tr>
		<odin:numberEdit property="maxloginperip"  label="单IP最大登录数" required="true" value="0" />
	</tr>
</table>
</odin:groupBox>


