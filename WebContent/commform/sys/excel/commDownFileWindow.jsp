<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�����ļ�</title>
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
			<a id="downFile" href="#" TARGET="_blank" onclick="doCloseWin()">�������</a>
		</td>
	</tr>
	<tr>
		<td height="20" colspan="2"></td>
	</tr>
</table>




<script>

/** �״������б����ݿ�ʼ */
Ext.onReady(function(){
	if(parent.downFileUrl==""){
		odin.alert("�ļ���Ϊ��!");
	}else if(parent.downFileUrl.indexOf("://")>=0){
		document.getElementById("downFile").href = parent.downFileUrl;
	}else{
		document.getElementById("downFile").href = contextPath+parent.downFileUrl;
	}
});

function doCloseWin(){
	odin.ext.get(document.body).mask('����ˢ��ҳ��......', odin.msgCls);
	parent.doHiddenPupWin();
}

</script>

</odin:base>

</body>
</html>