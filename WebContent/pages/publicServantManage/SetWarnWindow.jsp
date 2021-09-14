<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@include file="/comOpenWinInit.jsp" %>

<script type="text/javascript">
function saveSuccess(){
	odin.alert("保存成功",function(){
		radow.doEvent("closeBtn.onclick");
		realParent.getAffairJson();
	});
}
</script>

<odin:hidden property="userid" />

<table align="center" style="width: 100%">
	<tr>
		<td height="20"></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td width="40"></td>
		<td><label style="font-size: 12">退休年龄设置：</label></td>
	</tr>
</table>
<br>
<table>
	<tr>
		<td width="110"></td>
		<td><odin:numberEdit property="mage" label="男" size="6"
			value="60"></odin:numberEdit></td>
		<td width="30"></td>
		<td><odin:numberEdit property="fmage" label="女" size="6"
			value="55"></odin:numberEdit></td>
	</tr>
</table>
<br>
<table>
	<tr>
		<td width="40"></td>
		<td><label style="font-size: 12">试用期到期设置：</label></td>
	</tr>
</table>
<br>
<table>
	<tr>
		<td width="99"></td>
		<td><odin:numberEdit property="syday" label="天数" size="6"
			value="30"></odin:numberEdit></td>
	</tr>
</table>
<br>
<table>
	<tr>
		<td width="40"></td>
		<td><label style="font-size: 12">生日到期设置：</label></td>
	</tr>
</table>
<br>
<table>
	<tr>
		<td width="99"></td>
		<td><odin:numberEdit property="birthday" label="天数" size="6"
			value="30"></odin:numberEdit></td>
	</tr>
</table>
<br>
<table>
	<tr>
		<td width="160"></td>
		<td><odin:button text="保存" property="saveBtn"></odin:button></td>
		<td width="20"></td>
		<td><odin:button text="关闭" property="closeBtn"></odin:button></td>
	</tr>
</table>