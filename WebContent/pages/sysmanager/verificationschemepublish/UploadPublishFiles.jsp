<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ page import="com.insigma.siis.local.pagemodel.comm.CommonQueryBS" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"  %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	CommonQueryBS.systemOut(path+"\n"+basePath);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>Upload</title>

<!--装载文件-->
<link href="css/default.css" rel="stylesheet" type="text/css" />
<link href="css/uploadify.css" rel="stylesheet" type="text/css" />
<script src="basejs/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="basejs/swfobject.js"></script>
<script type="text/javascript" src="basejs/jquery/jquery.uploadify.min.js"></script>

<!--ready事件-->
<script type="text/javascript">
	$(document).ready(function() {
		$("#uploadify").uploadify({
			'swf' : "basejs/jquery/uploadify.swf",
			'script' : 'servlet/Upload',
			'uploader' : 'servlet/Upload',
			'cancelImg' : 'images/wrong.gif',
			'fileExt' : '*.rar;*.zip', 
			'auto' : false,
			'multi' : true,
			'buttonText' : 'BROWSE'
		});
	});
</script>
</head>

<body>
	<div id="fileQueue"></div>
	<input type="file" name="uploadify" id="uploadify" />
	<p>
		<a href="javascript:$('#uploadify').uploadify('upload', '*')">Upload the Files</a> | <a href="javascript:$('#uploadify').uploadify('stop')">Stop the Uploads!</a>
		
	</p>
</body>
</html>