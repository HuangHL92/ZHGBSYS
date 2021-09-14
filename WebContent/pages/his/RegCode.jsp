<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="self" %>
<script type="text/javascript">
<!--
function doSelect(data){
	document.getElementById("areacode").value = data.areacode;
}
//-->
</script>
<div id="panel_content"></div>
<odin:toolBar property="toolBar1" applyTo="panel_content">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="注册码生成" id="regBtn" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
<odin:groupBox property="g1" title="注册码基本信息项" width="700">
<table border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="2" height="10"></td>
	</tr>
	<tr style="height: 40px">
		<td style="font-size: 12px;" align="right">选择区域&nbsp;&nbsp;</td>
		<td>
			<self:IntelligentSeach property="S1" minChars="1" onselect="doSelect" width="200" listWidth="250" pageSize="5" showColNames="areacode,areaname" dataColNames="areacode,areaname" displayField="areaname" queryClass="com.insigma.siis.local.pagemodel.his.RegCodePageModel"/>
			<input type="hidden" name="areacode" id="areacode"/>
		</td>
	</tr>
	<tr>
		<odin:select property="type" codeType="HISROLETYPE" label="注册类别" required="true" value="10"></odin:select>
	</tr>
	<tr>
		<odin:numberEdit property="daycount" label="有效天数" required="true" value="30"></odin:numberEdit>
	</tr>
	<tr>
		<odin:select property="version" codeType="HISVERSION" label="注册码生成版本" value="1"></odin:select>
	</tr>
	<tr>
		<odin:numberEdit property="count" label="一次生成个数"  value="1"></odin:numberEdit>
	</tr>
	<tr>
		<odin:textarea property="regcode" label="注册码信息" cols="70" rows="10"></odin:textarea>
	</tr>
	<tr>
		<td colspan="2" height="10"></td>
	</tr>
</table>
</odin:groupBox>	 
