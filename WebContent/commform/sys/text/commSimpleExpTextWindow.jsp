<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>����Text�ļ�</title>
<odin:commformhead/>
<odin:commformMDParam></odin:commformMDParam>
</head>


<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form name="simpleTextForm" method="post"  action="<%=request.getContextPath()%>/text/textServlet?method=simpleExpTextFile"  target="simpleExpFrame">	
<table align="center" width="96%">
	<tr>
		<td height="20" colspan="2"></td>
	</tr>
	<tr>
		<odin:textEdit property="fileName" required="true" label="�����ļ���" width="390" title="Ҫ������text�ļ�������"></odin:textEdit>
	</tr>
	<tr>
		<td height="2" colspan="2"></td>
	</tr>
	<tr>
		<td align="center" colspan="2">
			<input type="button" style="cursor:hand;"  onclick="formSubmit()" value="�����ļ�" />
			<!-- &nbsp;&nbsp;<img src="../../images/qingkong.gif" onclick="document.all('excelFile').value='';"> -->
		</td>
	</tr>
	<tr>
		<td height="6" colspan="2"></td>
	</tr>
</table>
	<input type="hidden" name="businessClass" value="<%=(request.getParameter("businessClass")==null?"":request.getParameter("businessClass"))%>"/>
	<input type="hidden" name="separator" value="" />
	<input type="hidden" name="querySQL" value="" />
	<input type="hidden" name="headNames" value="" />
	<input type="hidden" name="withoutHead" value="" />
</form>

<iframe id="simpleExpFrame" name="simpleExpFrame" width="0" height="0"  style="display:none;" ></iframe>

<iframe id="waitForEndFrame" name="waitForEndFrame" width="0" height="0" style="display:none;" ></iframe>
<script>

/** �״������б����ݿ�ʼ */
Ext.onReady(function(){
	if(typeof(parent.fileNameSim)!='undefined'){
		document.simpleTextForm.fileName.value = parent.fileNameSim;
	}
	if(typeof(parent.separatorSim)!='undefined'){
		document.simpleTextForm.separator.value = parent.separatorSim;
	}
	if(typeof(parent.querySQLSim)!='undefined'){
		document.simpleTextForm.querySQL.value = parent.querySQLSim;
	}
	if(typeof(parent.headNamesSim)!='undefined'){
		document.simpleTextForm.headNames.value = parent.headNamesSim;
	}
	if(typeof(parent.withoutHeadSim)!='undefined'){
		document.simpleTextForm.withoutHead.value = parent.withoutHeadSim;
	}
});

function openChooseFileWin(){
	//document.all.excelFile.click();
}

function formSubmit(){
	if(document.getElementById('fileName').value!=""){
		odin.ext.get(document.body).mask('����������...', odin.msgCls);
		document.simpleTextForm.submit();
		doWaitingForEnd();
	}else{
		odin.info('�������ļ�����');
	}
}
function doWaitingForEnd(){
	document.getElementById("waitForEndFrame").src=contextPath + "/commform/sys/WaitForEnd.jsp";
}
function doCloseWin(){
	odin.ext.get(document.body).mask('����ˢ��ҳ��......', odin.msgCls);
	parent.doHiddenPupWin();
}


</script>

</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>