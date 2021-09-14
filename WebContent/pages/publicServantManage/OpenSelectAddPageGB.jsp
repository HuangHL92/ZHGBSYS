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
		<odin:select2 property="a02191" label="公开遴选" codeType="XZ09" onchange="Seta29307"></odin:select2>
		<odin:select2 property="a29301" label="遴选类别" codeType="ZB142"></odin:select2>
	</tr>
	<tr>
		<td colspan="6"><div style="height: 5px"></div></td>
	</tr>
	<tr>
		<odin:select2 property="a29304" label="遴选方式" codeType="ZB143"></odin:select2>
		<odin:select2 property="a29044" label="原单位层次" codeType="ZB141"></odin:select2>
	</tr>
	<tr>
		<td colspan="6"><div style="height: 5px"></div></td>
	</tr>
	<tr>
		<odin:NewDateEditTag property="a29307" label="遴选时间" maxlength="8"></odin:NewDateEditTag>
		<!-- <td><button onclick=" location=location ">刷新</button></td> -->
	</tr>
</table>	
<script type="text/javascript">	
var fieldsDisabled = <%=TableColInterface.getUpdateDataByTable("A82")%>;
var selectDisabled = <%=TableColInterface.getSelectDataByTable("A82")%>;
Ext.onReady(function(){
	//对信息集明细的权限控制，是否可以维护 
	$h.fieldsDisabled(fieldsDisabled); 
	//对信息集明细的权限控制，是否可以查看
	//var imgdata = "<img src='<%=request.getContextPath()%>/image/quanxian1.png' />";
	var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
	$h.selectDisabled(selectDisabled,imgdata); 
});
Ext.onReady(function(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
});
function save(){
	radow.doEvent('save');
}
function reShowMsg(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
	radow.doEvent('initX');
}
function Seta29307(){
	if(document.getElementById('a02191').value=='1'){
		$h.dateEnable("a29307");
	}else{
		$h.dateDisable("a29307");
	}
}
function formcheck(){
	return odin.checkValue(document.forms.commForm);
}
</script>



