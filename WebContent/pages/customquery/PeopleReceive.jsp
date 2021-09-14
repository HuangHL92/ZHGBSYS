<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<meta http-equiv="X-UA-Compatible"content="IE=8">

<style>
body {
	background-color: rgb(223,232,246);
}
span {
	font-family: "微软雅黑"、"黑体";
	font-size: 15px;
	color: rgb(21,66,139);
}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript">
function setAllValue(a0101){
	document.getElementById('msg').innerHTML = a0101;
}
function receive(){
	var a0201b = document.getElementById('a0201b').value;
	if(!a0201b){
		odin.alert("请选择正确的机构！");
		return;
	}
	var a0000 = document.getElementById('a0000').value;
	var a0201a = document.getElementById('a0201b_combo').value;
	var param = a0000 + "," + a0201a + "," + a0201b
	if(a0000){
		radow.doEvent("receive", param);
	}
}
function receiveSuccess(){
	odin.alert("人员接收成功！",function(){
		radow.doEvent("closeWin.onclick");
		parent.Ext.getCmp('persongrid1').store.reload();
		parent.realParent.getAffairJson();
	});
}
</script>

<odin:hidden property="a0000"/>
<odin:hidden property="a0101"/>

<div id="toolDiv" style="width: 433px;"></div>
<odin:toolBar property="floatToolBar" applyTo="toolDiv">
	<odin:fill/>
	<odin:buttonForToolBar text="确认接收" id="saveid" handler="receive" icon="images/icon/exp.png" cls="x-btn-text-icon" isLast="true"/>
	<%-- <odin:buttonForToolBar text="关闭" id="closeWin" icon="images/wrong.gif" isLast="true" cls="x-btn-text-icon" /> --%>
</odin:toolBar>

<br>
&nbsp;&nbsp;<span>现将&nbsp;<font id="msg" style="color: red;"></font>&nbsp;的个人信息调转入到单位</span>
<br><br>
<table>
	<tr>
		<td width="80"></td>
		<td><span>选择机构：</span></td>
		<td><tags:PublicTextIconEdit3 codetype="orgTreeJsonData" label="" property="a0201b"/></td>
		<td><span id="exit"></span></td>
	</tr>
	<tr>
		<td height="25"></td>
	</tr>
</table>

<br>
<span style="color: red;font-size: 14px">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：统计关系所在单位默认与选择机构一致；但若选择单位为内设机构，统计关系所在单位默认自动保存为内设机构所属上级的法人单位。</span>



















