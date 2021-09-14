<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page import="net.sf.json.JSONArray"%>

<%
String filetype =request.getParameter("filetype");
%>

<html>

<head>
<script type="text/javascript"src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script src="<sicp3:rewrite forward='md5'/>"> </script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<link rel="stylesheet" type="text/css" href="/hzb/css/odin.css"/>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<style type="text/css">
#daoru{position:relative;left:10px;width: 490px;} 
 
#tableid{position:relative;left:10px;width: 490px;} 
.td_2{border: 1px solid #b5b8c8;font-size: 12px;height: 30px;}
body{
font: normal 11px tahoma, arial, helvetica, sans-serif;
font-size: 11px;
}
</style>
<title>�����ļ�</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">

<form name="excelForm" id="excelForm" method="post"  action="<%=request.getContextPath()%>/RegCheckServlet?method=impPCFile" enctype="multipart/form-data" >	
<input id="fileid" name="fileid" type="hidden" value="<%=filetype %>"/>
<odin:groupBox title="ѡ���ļ�" property="daoru">
<table >
<tr>
	<odin:textEdit property="regname" label="��������" required="true"></odin:textEdit>
	<odin:textEdit property="regno" label="���α��" required="true"></odin:textEdit>
</tr>
<tr>
	<td style="height: 3px;" colspan="4"> </td>
</tr>
<tr>
	<odin:textEdit width="380" inputType="file" colspan="4"  property="excelFile" label="ѡ���ļ�:"></odin:textEdit> 
</tr>
<tr>
	<td style="height: 3px;" colspan="4"></td>
</tr>
<tr>
	<td colspan="3"></td>
	<td align="right" valign="middle">
		<odin:button text="&nbsp;��&nbsp;��&nbsp;" property="impwBtn" handler="formupload" /> 
		<%-- <img src="<%=request.getContextPath()%>/images/go1.jpg" onclick="formupload()" id="impwBtn"> --%>
	</td>
	
</tr>
<!-- <tr>
	<td colspan="3"></td>
	<td align="right" valign="middle">
		<input type="submit" name="submit" class="x-btn x-btn-noicon x-btn-text " value="&nbsp;��&nbsp;��&nbsp;" title="&nbsp;��&nbsp;��&nbsp;" />
	</td>
</tr> -->
</table>	
</odin:groupBox>
</form>
<%@include file="/comOpenWinInit.jsp" %>
<div style="display: none;">
<iframe src="" id='downloadframe' ></iframe>
</div>
<script>
$(document).ready(function () {
});



//ajax �ϴ�
function formupload(){
	var regname = odin.ext.get('regname').getValue();
	if(regname==''){
		alert("�������Ʋ���Ϊ�գ�");
		return;
	}
	var regno = odin.ext.get('regno').getValue();
	if(regno==''){
		alert("���α�Ų���Ϊ�գ�");
		return;
	}
	var fn = odin.ext.get('excelFile').getValue();
	if(fn==''){
		alert("��ѡ���ļ���");
		return;
	}
	var type = fn.substr(fn.lastIndexOf(".")+1);
	if(!(type.toUpperCase()=='XLS' || type.toUpperCase()=='XLSX')){
		alert("����excel��ʽ�ļ������ܵ��룡");
		return;
	}
	
	var p = '&regno='+regno+'&regname='+regname;
	parent.Ext.Msg.wait('�����ϴ������Ժ�...','ϵͳ��ʾ��');
	odin.ext.Ajax.request({
		url:'<%=request.getContextPath()%>/RegCheckServlet?method=impPCFile' + encodeURI(encodeURI(p)),
		isUpload:true,
		form:'excelForm',
		success:function(){
			parent.Ext.Msg.hide();
		},
		failure : function(){
			parent.Ext.Msg.hide();
		}
	});
}

</script>
<odin:response onSuccess="doSuccess"/>
</body>
</html>