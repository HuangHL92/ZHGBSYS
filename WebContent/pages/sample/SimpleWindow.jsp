<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<table>
	<tr>
		<odin:textEdit property="aab001" required="true" label="��λ����"></odin:textEdit>
		<odin:numberEdit property="aab002" label="��λ��"></odin:numberEdit>
	</tr>
	<tr>
		<odin:dateEdit property="aab003" label="ʱ��"></odin:dateEdit>
		<odin:select property="aab004" label="��λ��" data="['11', '���֤'],['21', '��ҵ֤']"></odin:select>
	</tr>
	<tr>
		<odin:textEdit property="aab007" label="��λ��ַ"></odin:textEdit>
		<td>
			<odin:radio property="aab006" value="1" label="��ѡ"></odin:radio>
		</td>
		<td>
			<odin:radio property="aab006" value="2" label="��ѡ2"></odin:radio>
		</td>
	</tr>
	<tr>
		<odin:textEdit property="aab008" label="��λ��ַ2"></odin:textEdit>
		<odin:checkbox property="aab005" label="��ѡ" value="1"></odin:checkbox>
	</tr>
	<tr> 
		<td>
			<odin:button text="����" property="btn1"></odin:button> 
		</td>
	</tr>
</table>

