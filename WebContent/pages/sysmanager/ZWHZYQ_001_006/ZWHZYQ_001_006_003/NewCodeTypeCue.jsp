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
				<odin:textEdit property="codeType" required="true" label="���뼯����" width="213" maxlength="100" validator="check" invalidText="ע:���뼯���������KZ��ͷ������ֻ��������ĸ����������"/>
			<td>
		</tr>
		<tr>
			<td>
				<odin:textEdit property="typeName" required="true" label="���뼯����" width="213" maxlength="100"  validator="check1" invalidText="ע:���Ʋ���Ϊ��"/>
			<td>
		</tr>
		<tr>
			<td>
				<odin:textarea property="codeDescription" label="���뼯����" cols="35" rows="5"/>
			<td>
		</tr>
		<tr>
			<td colspan="2" align="right">
				<odin:button text="&nbsp;ȷ&nbsp;��&nbsp;" property="save"/>
			</td>
			<td align="right">
				<odin:button text="&nbsp;ȡ&nbsp;��&nbsp;" property="cancel"/>
			</td>
		</tr>
		<tr>
			<td colspan="3" align="center">
				<span style="color:red;font-size:11px">ע:���뼯���������KZ��ͷ������ֻ��������ĸ����������</span>
			</td>
		</tr>
	</table>
</body>
</html>