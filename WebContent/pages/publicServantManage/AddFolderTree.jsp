<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<html>
<head>
	<script type="text/javascript">
		
		
	</script>
</head>
<odin:hidden property="id" title="主键id" ></odin:hidden>
<odin:hidden property="a0000" title="人员id" ></odin:hidden>
<odin:hidden property="parentId" title="父文件夹id" ></odin:hidden>
<body>
	<table style="margin: 10px 10px">
		<tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<td>
		</tr>
		<tr>
			<td>
				<odin:textEdit property="parentIdName" label="上级文件夹" width="213" maxlength="100" readonly="true"/>
			<td>
		</tr>
		<tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<td>
		</tr>
		<tr>
			<td>
				<odin:textEdit property="name" required="true" label="文件夹名称" width="213" maxlength="100"  invalidText="注:名称不能为空"/>
			<td>
		</tr>
		<tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<td>
		</tr>
		<tr>
			<td colspan="2" align="right">
				<odin:button text="&nbsp;确&nbsp;定&nbsp;" property="save"/>
			</td>
			<td align="right">
				<odin:button text="&nbsp;取&nbsp;消&nbsp;" property="cancel"/>
			</td>
		</tr>
		
	</table>
</body>
</html>