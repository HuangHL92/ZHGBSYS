<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page import="net.sf.json.JSONArray"%>

<%
String checkregid =request.getParameter("checkregid");
//System.out.println("222222222222---------" + checkregid);
String filetype =request.getParameter("filetype");
//System.out.println("222222222222---------" + filetype);
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

<form name="excelForm" id="excelForm" method="post"  action="<%=request.getContextPath()%>/UploadFileServlet?method=zzbFile" enctype="multipart/form-data" >	
<input id="checkregid" name="checkregid" type="hidden" value="<%=checkregid %>"/>
<input id="fileid" name="fileid" type="hidden" value="<%=filetype %>"/>
<odin:groupBox title="�ļ�����" property="daoru">
<table >
<tr>
	<odin:textEdit width="380" inputType="file" colspan="3"  property="excelFile" label="ѡ���ļ�:"></odin:textEdit> 
</tr>
<tr>
	<td></td>
	<td align="right" valign="middle">
		<odin:button text="&nbsp;��&nbsp;��&nbsp;" property="impwBtn" handler="formupload" /> 
		<%-- <img src="<%=request.getContextPath()%>/images/go1.jpg" onclick="formupload()" id="impwBtn"> --%>
	</td>
</tr>
	
</table>	
</odin:groupBox>
<div>
	<span style="font-size:11px;color:red;"></span>
</div>
<table id="tableid" style="" cellPadding="0" cellSpacing="0" >
<tr>
	<td width="100%"  class="td_2">&nbsp;&nbsp;&nbsp;�ļ����ƣ�<span id="span_id"></span></td>
</tr>
</table>
</form>
<div style="display: none;">
<iframe src="" id='downloadframe' ></iframe>
</div>
<script>
$(document).ready(function () {
	var id = document.getElementById('checkregid').value;
	var fileid = document.getElementById('fileid').value;
	Ext.Ajax.request({
		url:'<%=request.getContextPath()%>/RegCheckServlet?method=getXX&checkregid='+id+'&fileid='+fileid,
		isUpload: false,
		form:'excelForm',
		//params: {checkregid: id},
		success:function(){
			parent.Ext.Msg.hide();
		},
		failure : function(){
			parent.Ext.Msg.hide();
		}
	});
});

function setXX(id, name){
	var text = '<a href="javascript:void();" onclick="download(\''+id+'\')">'+name+'<a>' +' | &nbsp;'
		+ '<a href="javascript:void();" onclick="deletefj(\''+id+'\')">ɾ���ļ�<a>';
	$('#span_id').html(text);
	odin.ext.getCmp('impwBtn').disable();
}

function download(id){
	//���ظ��� downloadframe
	//encodeURI��������urlת�룬������Ĵ����������� ����̨���յ�ʱ�������ת�봦��ת������
	document.getElementById('downloadframe').src="<%=request.getContextPath()%>/PublishFileServlet?method=downloadFile&checkregfileid="+encodeURI(encodeURI(id));
	
}
function deletefj(id){
	if(confirm("��ȷ��Ҫɾ�����ϴ��ĸ�����")){
		//$('#span_id').html("&nbsp;");
		odin.ext.Ajax.request({
			url:'<%=request.getContextPath()%>/RegCheckServlet?method=deleteFile&fid='+id,
			isUpload:true,
			form:'excelForm',
			//params : {'fid': id},
			success:function(){
				parent.Ext.Msg.hide();
			},
			failure : function(){
				parent.Ext.Msg.hide();
			}
		});
	}
}

//ajax �ϴ�
function formupload(){
	var fn = odin.ext.get('excelFile').getValue();
	var type = fn.substr(fn.lastIndexOf(".")+1);
	if(!(type.toUpperCase()=='XLS' || type.toUpperCase()=='XLSX')){
		alert("����excel��ʽ�ļ������ܵ���");
		//parent.location.reload();
		return;
	}
	var checkregid = document.getElementById('checkregid').value;
	var fileid = document.getElementById('fileid').value;
	parent.Ext.Msg.wait('�����ϴ������Ժ�...','ϵͳ��ʾ��');
	odin.ext.Ajax.request({
		url:'<%=request.getContextPath()%>/RegCheckServlet?method=impFK&checkregid='+checkregid+'&fileid='+fileid,
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