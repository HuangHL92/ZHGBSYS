<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.lbs.cp.util.SysManagerUtil"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>

<odin:groupBox title="�û�������Ϣ" width="500" >
	<table border="0" id="myform" align="center" width="100%"
		cellpadding="0" cellspacing="0">
		<tr>
			<odin:textEdit property="loginname" label="�û���¼��" required="true" maxlength="21"></odin:textEdit>
		</tr>
		<tr>
		 	<tags:PublicTextIconEdit3 property="ssjg" label="������Χ" readonly="true" codetype="orgTreeJsonData" required="true"></tags:PublicTextIconEdit3>
		</tr>
		<tr>
			<odin:textEdit property="username" label="����" required="true"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="password" inputType="password" label="&nbsp;����" required="true"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="surepassword" inputType="password" label="&nbsp;ȷ������" required="true"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="work" label="&nbsp;������λ"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="mobile" label="&nbsp;�ֻ�"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="tel" label="&nbsp;�칫�绰"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="emial" label="&nbsp;�ʼ�"></odin:textEdit>
		</tr>
		<tr>
			<odin:select property="useful" label="&nbsp;�û�״̬" required="true"></odin:select>
		</tr>
		<tr>
			<odin:select property="usertype" label="&nbsp;�û�����" required="true" data="['1','ϵͳ����Ա'],['3','��ȫ����Ա'],['4','��ȫ���Ա'],['2','��ͨ�û�']"></odin:select>
		</tr>
		<tr> 
			<td></td>
			<td align="center">
				<odin:button text="����" property="savebut"></odin:button>
			</td>
		</tr>
	</table>
</odin:groupBox>
