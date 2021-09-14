<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:floatDiv property="tbv" position="up"></odin:floatDiv>
<odin:toolBar property="btnToolBar" applyTo="tbv">
	<odin:fill />
	<odin:buttonForToolBar id="btnSave" text="保存" icon="images/save.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<br/><br/><br/>
<odin:groupBox title="FTP配置信息">
<table width="100%" cellpadding="0" cellspacing="0" >
	<tr>
		<odin:hidden property="id"/>
		<odin:hidden property="type"/>
		<odin:textEdit property="name" label="FTP名称" required="true" />
	</tr>
	<tr>
		<odin:textEdit property="hostname" label="FTP主机地址" required="true" />
	</tr>
	<tr>
		<odin:numberEdit property="port" label="FTP端口" required="true" />
	</tr>
	<tr>
		<odin:textEdit property="username" label="FTP用户名" required="true" />
	</tr>
	<tr>
		<odin:textEdit property="password" inputType="password" label="FTP密码" required="true" />
	</tr>
	<tr>
		<odin:select2 property="status" label="状态"  codeType="USEFUL" required="true" value="1"></odin:select2>
	</tr>
</table>
</odin:groupBox>


