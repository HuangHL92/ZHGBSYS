<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>


<odin:groupBox title="������Ϣ">
	<table border="0" id="myform" align="center" width="100%"
		cellpadding="0" cellspacing="0">
		<tr>
			<odin:hidden property="aaa104"/>
			<odin:hidden property="aaz499"/>
		</tr>
		<tr>
			<odin:textEdit property="aaa001" required="true" label="����������" readonly="true"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="aaa002" label="�����������"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="aaa005" label="����ֵ"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="aaa105" label="����ֵ��˵��"></odin:textEdit>
		</tr>
		<tr>
			<td align="right" colspan="4"><odin:button text="�����޸�"
				property="saveBtn"></odin:button>
			<td>
		</tr>
	</table>
</odin:groupBox>
