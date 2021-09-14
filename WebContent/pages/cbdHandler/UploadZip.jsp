<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="net.sf.json.JSONArray"%>
<%
	String flag=request.getParameter("flag");
	
%>
<html>
<head>
<script src="<sicp3:rewrite forward='md5'/>"> </script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/signature.js"> </script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>导入文件</title>
<odin:head/>
<odin:MDParam></odin:MDParam>

<link rel="stylesheet" type="text/css" href="../../resources/css/ext-all.css" />

    <!-- GC -->
 	<!-- LIBS -->
 	<script type="text/javascript" src="../../adapter/ext/ext-base.js"></script>
 	<!-- ENDLIBS -->

    <script type="text/javascript" src="../../ext-all.js"></script>

    <script type="text/javascript" src="basejs/ColumnNodeUI.js"></script>

    <!-- Common Styles for the examples -->
    <link rel="stylesheet" type="text/css" href="../examples.css" />

    <link rel="stylesheet" type="text/css" href="basejs/ext/ux/css/column-tree.css" />
<script  type="text/javascript" src="basejs/jquery.js"></script>
<script  type="text/javascript" src="basejs/jquery.min.js"></script>


</head>

<body style="overflow-y:hidden;overflow-x:hidden">

<odin:base>

<form name="excelForm" id="excelForm" method="post"  action="<%=request.getContextPath()%>/UploadFileServlet?method=uploadAttachment" enctype="multipart/form-data" >	

<table width="96%" id="v_standard2">
	<tr>
		<td style="{font :12px}" align="right">文件选择  </td>
		<td ><div class="x-form-item"><input type="file" id="filename" name="filename" size="47" /></div></td>
	</tr>
	<tr align="center">
		<td colspan="2">
			<input type="button" value="导入" id="impwBtn" onclick="formupload()" />
		</td>
	</tr>
</table>
<odin:hidden  property="flag" value="<%=flag %>"/>
</form>

<script>

	
	//上传附件
	function formupload(){
		if(document.getElementById('filename').value!=""){
			
			var flag = odin.ext.get('flag').getValue();
			
			odin.ext.get(document.body).mask('正在上传数据并处理中......', odin.msgCls);
			odin.ext.Ajax.request({
				url:'<%=request.getContextPath()%>/UploadFileServlet?method=uploadZip&flag='+flag,
				isUpload:true,
				form:'excelForm',
				success:function(){
				}
			});
			parent.odin.ext.getCmp('simpleExpWin').hide();
		}else{
			odin.info('请选择文件之后再做导入处理！');
		}
	}

</script>
</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>