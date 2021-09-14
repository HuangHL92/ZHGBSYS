<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<odin:floatDiv property="tbv" position="up"></odin:floatDiv>
<odin:toolBar property="btnToolBar" applyTo="tbv">
	<odin:fill />
	<odin:buttonForToolBar id="btnSave" text="����" icon="images/save.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<br/><br/>
<odin:groupBox title="FTP�û���Ϣ">
<table width="100%" cellpadding="0" cellspacing="0" >
	<tr>
		<tags:PublicTextIconEdit3  codetype="orgTreeJsonData" required="true" label="��������" property="depict" readonly="true"/>
	</tr>
	<tr>
		<odin:textEdit property="userid" label="�û���" required="true" maxlength="64"/>
	</tr>
	<tr>
		<odin:textEdit property="userpassword" inputType="password" label="����" required="true" maxlength="64"/>
	</tr>
	<tr>
		<odin:select2 property="enableflag" label="����״̬"  codeType="HAS" required="true" value="true"></odin:select2>
	</tr>
	<tr>
		<odin:select2 property="writepermission" label="��дȨ��"  codeType="HAS" required="true" value="true"></odin:select2>
	</tr>
	<tr>
		<odin:numberEdit property="idletime"  label="����ʱ��(S)" required="true" value="300" maxlength="6"/>
	</tr>
	<tr>
		<odin:numberEdit property="uploadrate"  label="�ϴ�����(K/S)" required="true" value="0" maxlength="6"/>
	</tr>
	<tr>
		<odin:numberEdit property="downloadrate"  label="��������(K/S)" required="true" value="0" maxlength="6"/>
	</tr>
		<tr>
		<odin:numberEdit property="maxloginnumber"  label="�û�����¼��" required="true" value="0" maxlength="6"/>
	</tr>
	<tr>
		<odin:numberEdit property="maxloginperip"  label="��IP����¼��" required="true" value="0" />
	</tr>
</table>
</odin:groupBox>


