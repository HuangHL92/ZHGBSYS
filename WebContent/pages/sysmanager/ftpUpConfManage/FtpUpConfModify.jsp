<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:floatDiv property="tbv" position="up"></odin:floatDiv>
<odin:toolBar property="btnToolBar" applyTo="tbv">
	<odin:fill />
	<odin:buttonForToolBar id="btnSave" text="����" icon="images/save.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<br/><br/><br/>
<odin:groupBox title="FTP������Ϣ">
<table width="100%" cellpadding="0" cellspacing="0" >
	<tr>
		<odin:hidden property="id"/>
		<odin:hidden property="type"/>
		<odin:textEdit property="name" label="FTP����" required="true" />
	</tr>
	<tr>
		<odin:textEdit property="hostname" label="FTP������ַ" required="true" />
	</tr>
	<tr>
		<odin:numberEdit property="port" label="FTP�˿�" required="true" />
	</tr>
	<tr>
		<odin:textEdit property="username" label="FTP�û���" required="true" />
	</tr>
	<tr>
		<odin:textEdit property="password" inputType="password" label="FTP����" required="true" />
	</tr>
	<tr>
		<odin:select2 property="status" label="״̬"  codeType="USEFUL" required="true" value="1"></odin:select2>
	</tr>
</table>
</odin:groupBox>


