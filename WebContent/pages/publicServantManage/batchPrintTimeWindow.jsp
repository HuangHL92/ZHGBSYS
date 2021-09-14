<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<odin:groupBox property="s10" title="请填写打印时间">
	<table>
		<tr>
			<td><odin:NewDateEditTag property="time" label="打印时间"
					maxlength="8" isCheck="true"></odin:NewDateEditTag></td>
			<td width="40px"></td>
			<td><label for="choose" style="font-size: 12px;">是否打印拟任免信息</label>
				<input align="middle" checked="checked" type="checkbox"
				name="choose" id="choose" /></td>
		</tr>
	</table>
</odin:groupBox>
<table>
	    <tr>
		<td width="400px"></td>
		<td>
		<odin:button text="确定" property="sureBtn"></odin:button>
		</td>
		</tr>
</table>