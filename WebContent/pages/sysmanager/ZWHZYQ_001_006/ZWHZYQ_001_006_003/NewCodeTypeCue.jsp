<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<html>
<head>
	<script type="text/javascript">
		function check(){
			var codeType = document.all.codeType.value; 
			var pattern = /^(KZ[A-Z|a-z|0-9|\_]+)*$/;
			flag = pattern.test(codeType);
			if(!flag){
				return false;
			}else{
				return true;
			}
		}
		function check1() {
			var typename = document.all.typeName.value;
			if(""==typename.trim()) {
				return false;
			} else {
				return true;
			}
		}
	</script>
</head>
<body>
	<table style="margin: 10px 10px">
		<tr>
			<td>
				<odin:textEdit property="codeType" required="true" label="代码集编码" width="213" maxlength="100" validator="check" invalidText="注:代码集编码必须以KZ开头，并且只能输入字母或阿拉伯数字"/>
			<td>
		</tr>
		<tr>
			<td>
				<odin:textEdit property="typeName" required="true" label="代码集名称" width="213" maxlength="100"  validator="check1" invalidText="注:名称不能为空"/>
			<td>
		</tr>
		<tr>
			<td>
				<odin:textarea property="codeDescription" label="代码集描述" cols="35" rows="5"/>
			<td>
		</tr>
		<tr>
			<td colspan="2" align="right">
				<odin:button text="&nbsp;确&nbsp;定&nbsp;" property="save"/>
			</td>
			<td align="right">
				<odin:button text="&nbsp;取&nbsp;消&nbsp;" property="cancel"/>
			</td>
		</tr>
		<tr>
			<td colspan="3" align="center">
				<span style="color:red;font-size:11px">注:代码集编码必须以KZ开头，并且只能输入字母或阿拉伯数字</span>
			</td>
		</tr>
	</table>
</body>
</html>