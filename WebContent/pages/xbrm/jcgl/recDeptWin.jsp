<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page import="net.sf.json.JSONArray"%>

<%
String checkregid =request.getParameter("checkregid");
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
#daoru{position:relative;left:0px;width: 300px;} 

/* #tableid{position:relative;left:2px;width: 490px;}  */
.td_2{border: 1px solid #b5b8c8;font-size: 12px;height: 30px;}
body{
font: normal 11px tahoma, arial, helvetica, sans-serif;
font-size: 11px;
}
</style>
<title>选择&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
</head>
<%@include file="/comOpenWinInit2.jsp" %>
<body style="overflow-y:hidden;overflow-x:hidden">

<form name="excelForm" id="excelForm" method="post"  action="<%=request.getContextPath()%>/UploadFileServlet?method=zzbFile" enctype="multipart/form-data" >	
<input id="checkregid" name="checkregid" type="hidden" value="<%=checkregid %>"/>
<odin:groupBox title="选择发送部门" property="daoru">
<table style="width: 100%">
<tr>
	<td colspan="2">
		<odin:checkBoxGroup  property="tqyjcheck" countFRow="3" data="['1', '公安'],['2', '住建局'],['3', '工商']"/>
	</td>
</tr>
<tr>
	<td></td>
	<td align="center" valign="middle">
		<odin:button text="&nbsp;确&nbsp;定&nbsp;" property="impwBtn" handler="formupload" /> 
		<%-- <img src="<%=request.getContextPath()%>/images/go1.jpg" onclick="formupload()" id="impwBtn"> --%>
	</td>
</tr>
	
</table>	
</odin:groupBox>

</form>
<div style="display: none;">
<iframe src="" id='downloadframe' ></iframe>
</div>
<script>
$(document).ready(function () {
	var id = document.getElementById('checkregid').value;
	
});


//ajax 上传
function formupload(){
	
	var checkregid = document.getElementById('checkregid').value;
	var xq = '';
	var c = 0;
	$("input[name='tqyjcheck']").each(function(){
		var ischeck=$(this).is(":checked");
		var v=$(this).val();
		if(ischeck==false){
			//data.push("□");
		}else{
			//data.push("√");
			xq = xq + v +",";
			c ++;
		}
	});
	if(c==0){
		alert('至少选择一个部门。');
		return;
	}
	//alert(checkregid);
	//parent.Ext.Msg.wait('正在上传，请稍候...','系统提示：');
	odin.ext.Ajax.request({
		url:'<%=request.getContextPath()%>/RegCheckServlet?method=sendMsg&checkregid='+checkregid+'&xq='+xq,
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