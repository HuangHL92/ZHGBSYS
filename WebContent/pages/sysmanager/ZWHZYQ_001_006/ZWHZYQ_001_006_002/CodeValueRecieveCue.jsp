<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery-2.1.4.js"></script>
</head>
<body>
	<form name="codeValueForm" id="codeValueForm" action="<%=request.getContextPath()%>/UploadServlet?type=codeType" enctype="multipart/form-data" method="post">
		<span style="font-family:'Microsoft YaHei' ;font-size: 13px;">请选择需要导入的文件:</span><br/><br/>
		<input type="file" name="file" id="upload">
		<input type="submit" name="upload" value="导入"/>
	</form>
</body>
</html>
