<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>


<odin:groupBox title="参数信息">
	<table border="0" id="myform" align="center" width="100%"
		cellpadding="0" cellspacing="0">
		<tr>
			<odin:hidden property="aaa104"/>
			<odin:hidden property="aaz499"/>
		</tr>
		<tr>
			<odin:textEdit property="aaa001" required="true" label="参数类别代码" readonly="true"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="aaa002" label="参数类别名称"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="aaa005" label="参数值"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="aaa105" label="参数值域说明"></odin:textEdit>
		</tr>
		<tr>
			<td align="right" colspan="4"><odin:button text="保存修改"
				property="saveBtn"></odin:button>
			<td>
		</tr>
	</table>
</odin:groupBox>
