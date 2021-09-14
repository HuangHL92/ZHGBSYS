<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<table>
	<tr>
		<odin:textEdit property="aab001" required="true" label="单位名称"></odin:textEdit>
		<odin:numberEdit property="aab002" label="单位号"></odin:numberEdit>
	</tr>
	<tr>
		<odin:dateEdit property="aab003" label="时间"></odin:dateEdit>
		<odin:select property="aab004" label="单位号" data="['11', '身份证'],['21', '毕业证']"></odin:select>
	</tr>
	<tr>
		<odin:textEdit property="aab007" label="单位地址"></odin:textEdit>
		<td>
			<odin:radio property="aab006" value="1" label="单选"></odin:radio>
		</td>
		<td>
			<odin:radio property="aab006" value="2" label="单选2"></odin:radio>
		</td>
	</tr>
	<tr>
		<odin:textEdit property="aab008" label="单位地址2"></odin:textEdit>
		<odin:checkbox property="aab005" label="复选" value="1"></odin:checkbox>
	</tr>
	<tr> 
		<td>
			<odin:button text="保存" property="btn1"></odin:button> 
		</td>
	</tr>
</table>

