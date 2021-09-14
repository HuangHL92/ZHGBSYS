<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath(); 
String sign = request.getParameter("sign");
%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.TableColInterface"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>

<style>
body {
	background-color: rgb(214,227,243);
}

</style>

<odin:hidden property="a0000" title="人员主键"/>
<table style="width:100%;margin-top: 50px;" >
	<tr>
		<odin:select2 property="a3101" label="离退类别"  codeType="ZB132"></odin:select2>
		<odin:NewDateEditTag property="a3104" label="离退批准日期" isCheck="true" maxlength="8"></odin:NewDateEditTag>
	</tr>
	<tr>
		<td colspan="4"><div style="height: 5px"></div></td>
	</tr>
	<tr>		
		<tags:PublicTextIconEdit property="a3107" label="离退前级别" codetype="XZ22"  readonly="true"/> 
		<odin:textEdit property="a3118" label="曾任最高职务" ></odin:textEdit>
	</tr>
	<tr>
		<td colspan="4"><div style="height: 5px"></div></td>
	</tr>
	<tr>		
		<odin:textEdit property="a3117a" label="离退后管理单位" ></odin:textEdit>		
		<odin:textEdit property="a3137" label="离退批准文号" ></odin:textEdit>		
	</tr>
	<tr>
		<td colspan="4"><div style="height: 5px"></div></td>
	</tr>
	<!-- <tr>
		
		<td><button onclick=" location=location ">刷新</button></td>
	</tr> -->
	

</table>


<script type="text/javascript">
var fieldsDisabled = <%=TableColInterface.getUpdateDataByTable("A31",sign)%>;
var selectDisabled = <%=TableColInterface.getSelectDataByTable("A31")%>;
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
function reShowMsg(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
	radow.doEvent('initX');
}

function save(){
 	radow.doEvent('save'); 
}
function formcheck(){
	return odin.checkValue(document.forms.commForm);
}
</script>



