<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<html>
<head>
	<script type="text/javascript">
		
		
	</script>
</head>
<odin:hidden property="id" title="����id" ></odin:hidden>
<odin:hidden property="a0000" title="��Աid" ></odin:hidden>
<odin:hidden property="parentId" title="���ļ���id" ></odin:hidden>
<body>
	<table style="margin: 10px 10px">
		<tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<td>
		</tr>
		<tr>
			<td>
				<odin:textEdit property="parentIdName" label="�ϼ��ļ���" width="213" maxlength="100" readonly="true"/>
			<td>
		</tr>
		<tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<td>
		</tr>
		<tr>
			<td>
				<odin:textEdit property="name" required="true" label="�ļ�������" width="213" maxlength="100"  invalidText="ע:���Ʋ���Ϊ��"/>
			<td>
		</tr>
		<tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<td>
		</tr>
		<tr>
			<td colspan="2" align="right">
				<odin:button text="&nbsp;ȷ&nbsp;��&nbsp;" property="save"/>
			</td>
			<td align="right">
				<odin:button text="&nbsp;ȡ&nbsp;��&nbsp;" property="cancel"/>
			</td>
		</tr>
		
	</table>
</body>
</html>