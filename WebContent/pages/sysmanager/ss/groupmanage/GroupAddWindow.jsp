<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<table>
	<tr>
		<td height="10"></td>
	</tr>
	<tr>
		<odin:textEdit property="name" label="�û�������" required="true" />
		<odin:textEdit property="shortname" label="���" />
	</tr>
	<tr>
		<odin:select property="status" label="״̬"
			data="['1', '��Ч'],['0', '��Ч']" required="true"></odin:select>
		<odin:textEdit property="districtcode" label="��������" required="true"/>
	</tr>
	<tr>
		<odin:select property="rate" label="����" codeType="RATE" required="true"></odin:select>
		<odin:select property="address" label="��ַ" codeType="AAB301"/>
	</tr>
	<tr>
		<odin:textEdit property="tel" label="�绰" />
		<odin:textEdit property="linkman" label="��ϵ��" />
	</tr>
	<tr>
		<odin:textEdit property="chargedept" label="���ܲ���" />
		<odin:textEdit property="org" label="ϵͳ��������" />
	</tr>
	<tr>
		<odin:textEdit property="desc" label="�û�������" width="246" colspan="4"/>
	</tr>
	<tr>
		<odin:textEdit property="otherinfo" label="������Ϣ"/>
		
		<odin:textEdit property="principal" label="����������" />
	</tr>
	<tr>
		<td width="50" height="50"></td>
		<td><odin:button text="����" property="saveBtn"></odin:button></td>
	</tr>
</table>

