<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>下载文件</title>
<odin:commformhead/>

</head>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<table align="center" width="96%">
	<tr>
		<td height="40" colspan="2"></td>
	</tr>
	<tr>
		<td align="center" colspan="2">
			<a id="downFile" href="#" TARGET="_blank" onclick="doCloseWin()">点击下载</a>
		</td>
	</tr>
	<tr>
		<td height="20" colspan="2"></td>
	</tr>
</table>




<script>

/** 首次载入列表数据开始 */
Ext.onReady(function(){
	if(parent.downFileUrl==""){
		odin.alert("文件名为空!");
	}else if(parent.downFileUrl.indexOf("://")>=0){
		document.getElementById("downFile").href = parent.downFileUrl;
	}else{
		document.getElementById("downFile").href = contextPath+parent.downFileUrl;
	}
});

function doCloseWin(){
	odin.ext.get(document.body).mask('正在刷新页面......', odin.msgCls);
	parent.doHiddenPupWin();
}

</script>

</odin:base>

</body>
</html>