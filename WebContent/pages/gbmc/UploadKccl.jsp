<%@page import="com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page isELIgnored="false" %>
<%@include file="/comOpenWinInit2.jsp" %>


<script src="<%=request.getContextPath()%>/jqueryUpload/jquery-1.11.3.js"></script>
<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<style>
</style>
<odin:hidden property="ynId"/>
<odin:hidden property="a0000"/>
<odin:hidden property="tp0116"/>

<script type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';
	Ext.onReady(function(){
		$('#tp0116').val(parentParam?parentParam.tp0116:"d4535b4b-7374-4279-b6ea-cbd3df29aed4");
		$('#ynId').val(parentParam?parentParam.ynId:"d4535b4b-7374-4279-b6ea-cbd3df29aed4");
		$('#a0000').val(parentParam?parentParam.a0000:"d4535b4b-7374-4279-b6ea-cbd3df29aed4");
	});
</script>


<table id="ukccl" style="width: 100%">
 <tr style="height: 120px">
  <tags:JUpload2 property="file5" label="选择文件" fileTypeDesc="文件类型"  colspan="2"
  uploadLimit="1" width="80" fileSizeLimit="20MB" fileTypeExts="*.xlsx;*.doc;*.docx;*.pdf" labelTdcls="titleTd"/>
 </tr>
 <tr>
	<td colspan="2" align="right" style="padding-right: 20px;">
		<odin:button text="上传" property="impBtn" />
	</td>
 </tr>
</table>

<script type="text/javascript">
function onUploadSuccess(){//上传后  
	if($('#file5').data('uploadify').queueData.queueLength<=1){
		parentParam.$tr.children("td:nth-child(2)").removeClass("default_color").addClass("kcclclass")
		window.close();
	}	
}

function onUploadSuccess_new(isClose){//上传后  
	if($('#file5').data('uploadify').queueData.queueLength<=1 || isClose){
		//parentParam.$tr.children("td:nth-child(2)").removeClass("default_color").addClass("kcclclass")
		window.close();
	}	
}

function afterDelete(fileID){//删除后
	parentParam.$tr.children("td:nth-child(2)").addClass("default_color").removeClass("kcclclass")
	window.close();
}
function wait(){
	if($('#file5').data('uploadify').queueData.queueLength>=1){
		if ($('#a0000').val()==""){
			Ext.MessageBox.wait('正在上传EXCEL文件并且导入，请稍后。。。');
		} else {
			Ext.MessageBox.wait('正在上传和PDF转换，请稍后。。。');
		}
	}
	
}


</script>
