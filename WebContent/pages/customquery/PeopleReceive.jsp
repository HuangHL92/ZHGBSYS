<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<meta http-equiv="X-UA-Compatible"content="IE=8">

<style>
body {
	background-color: rgb(223,232,246);
}
span {
	font-family: "΢���ź�"��"����";
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
		odin.alert("��ѡ����ȷ�Ļ�����");
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
	odin.alert("��Ա���ճɹ���",function(){
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
	<odin:buttonForToolBar text="ȷ�Ͻ���" id="saveid" handler="receive" icon="images/icon/exp.png" cls="x-btn-text-icon" isLast="true"/>
	<%-- <odin:buttonForToolBar text="�ر�" id="closeWin" icon="images/wrong.gif" isLast="true" cls="x-btn-text-icon" /> --%>
</odin:toolBar>

<br>
&nbsp;&nbsp;<span>�ֽ�&nbsp;<font id="msg" style="color: red;"></font>&nbsp;�ĸ�����Ϣ��ת�뵽��λ</span>
<br><br>
<table>
	<tr>
		<td width="80"></td>
		<td><span>ѡ�������</span></td>
		<td><tags:PublicTextIconEdit3 codetype="orgTreeJsonData" label="" property="a0201b"/></td>
		<td><span id="exit"></span></td>
	</tr>
	<tr>
		<td height="25"></td>
	</tr>
</table>

<br>
<span style="color: red;font-size: 14px">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ע��ͳ�ƹ�ϵ���ڵ�λĬ����ѡ�����һ�£�����ѡ��λΪ���������ͳ�ƹ�ϵ���ڵ�λĬ���Զ�����Ϊ������������ϼ��ķ��˵�λ��</span>



















