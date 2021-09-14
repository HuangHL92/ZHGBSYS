<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath(); 
%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.TableColInterface"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<style>
body {
	background-color: rgb(214,227,243);
}

</style>
<odin:hidden property="a0000" title="人员主键"/>

<table style="width:100%;margin-top: 50px;">
	<tr>
		<odin:select2 property="a02192" label="是否公开选调" codeType="XZ09" onchange="Seta29354"></odin:select2>
		<odin:select2 property="a29311" label="公开选调类别" codeType="ZB142"></odin:select2>
	</tr>
	<tr>
		<td colspan="6"><div style="height: 5px"></div></td>
	</tr>
	<tr>
		<odin:select2 property="g02002" label="原单位类别" codeType="ZB144"></odin:select2>
		<odin:select2 property="a29044" label="原单位层次" codeType="ZB141"></odin:select2>
	</tr>
	<tr>
		<td colspan="6"><div style="height: 5px"></div></td>
	</tr>
	<tr>
		<odin:select2 property="a29041" label="原单位职称或职务" codeType="ZB145"></odin:select2>
		<odin:NewDateEditTag property="a29354" label="公开选调时间" maxlength="8"></odin:NewDateEditTag>
	</tr>
	<tr>
		<td colspan="6"><div style="height: 5px"></div></td>
	</tr>
	<tr>
		<odin:select2 property="a44031" label="是否有海外工作经历" codeType="XZ09" onchange="Seta39084"></odin:select2>
		<odin:numberEdit property="a39084" label="海外工作年限"></odin:numberEdit>
	</tr>
	<tr>
		<td colspan="6"><div style="height: 5px"></div></td>
	</tr>
	<tr>
		<odin:select2 property="a44027" label="是否有海外留学经历" codeType="XZ09" onchange="Seta39077"></odin:select2>
		<odin:numberEdit property="a39077" label="留学年限"></odin:numberEdit>
	</tr>
	<tr>
		<!-- <td><button onclick=" location=location ">刷新</button></td> -->
	</tr>
	
</table>
<script type="text/javascript">
var fieldsDisabled = <%=TableColInterface.getUpdateDataByTable("A83")%>;
var selectDisabled = <%=TableColInterface.getSelectDataByTable("A83")%>;
Ext.onReady(function(){
	//对信息集明细的权限控制，是否可以维护 
	$h.fieldsDisabled(fieldsDisabled); 
	//对信息集明细的权限控制，是否可以查看
	//var imgdata = "<img src='<%=request.getContextPath()%>/image/quanxian1.png' />";
	var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
	$h.selectDisabled(selectDisabled,imgdata); 
});
function save(){
	radow.doEvent('save');
}
Ext.onReady(function(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
});
function reShowMsg(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
	radow.doEvent('initX');
}
function Seta39077(){
	if(document.getElementById('a44027').value=="1"){
		Ext.getCmp('a39077').setDisabled(false);
	}else{
		Ext.getCmp('a39077').setValue('');
		Ext.getCmp('a39077').setDisabled(true);
	}
	
}



function Seta39084(){
	if(document.getElementById('a44031').value=="1"){
		Ext.getCmp('a39084').setDisabled(false);
	}else{
		Ext.getCmp('a39084').setValue('');
		Ext.getCmp('a39084').setDisabled(true);
	}
	
}

function Seta29354(){
	if(document.getElementById('a02192').value=="1"){
		$h.dateEnable("a29354");
	}else{
		$h.dateDisable("a29354");
	}
}
function formcheck(){
	return odin.checkValue(document.forms.commForm);
}
</script>




