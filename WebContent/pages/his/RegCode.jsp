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
	<odin:buttonForToolBar text="ע��������" id="regBtn" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
<odin:groupBox property="g1" title="ע���������Ϣ��" width="700">
<table border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="2" height="10"></td>
	</tr>
	<tr style="height: 40px">
		<td style="font-size: 12px;" align="right">ѡ������&nbsp;&nbsp;</td>
		<td>
			<self:IntelligentSeach property="S1" minChars="1" onselect="doSelect" width="200" listWidth="250" pageSize="5" showColNames="areacode,areaname" dataColNames="areacode,areaname" displayField="areaname" queryClass="com.insigma.siis.local.pagemodel.his.RegCodePageModel"/>
			<input type="hidden" name="areacode" id="areacode"/>
		</td>
	</tr>
	<tr>
		<odin:select property="type" codeType="HISROLETYPE" label="ע�����" required="true" value="10"></odin:select>
	</tr>
	<tr>
		<odin:numberEdit property="daycount" label="��Ч����" required="true" value="30"></odin:numberEdit>
	</tr>
	<tr>
		<odin:select property="version" codeType="HISVERSION" label="ע�������ɰ汾" value="1"></odin:select>
	</tr>
	<tr>
		<odin:numberEdit property="count" label="һ�����ɸ���"  value="1"></odin:numberEdit>
	</tr>
	<tr>
		<odin:textarea property="regcode" label="ע������Ϣ" cols="70" rows="10"></odin:textarea>
	</tr>
	<tr>
		<td colspan="2" height="10"></td>
	</tr>
</table>
</odin:groupBox>	 
