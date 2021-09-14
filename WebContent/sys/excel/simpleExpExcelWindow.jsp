<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>����excel�ļ�</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
</head>

<script type="text/javascript">
var uuid="<%=java.util.UUID.randomUUID().toString()%>";
</script>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form name="simpleExcelForm" method="post"  action="<%=request.getContextPath()%>/excel/excelServlet?method=simpleExpExcelFile"  target="simpleExpFrame">	
<table align="center" width="96%">
	<tr>
		<td height="20" colspan="2"></td>
	</tr>
	<tr>
		<odin:textEdit property="fileName" required="true" label="�����ļ���" width="390" title="Ҫ������excel�ļ�������"></odin:textEdit>
	</tr>
	<tr>
		<td height="2" colspan="2"></td>
	</tr>
	<tr>
		<td align="right" colspan="2">
			<img src="<%=request.getContextPath()%>/images/daochu.gif" onclick="formSubmit()">
			<!-- &nbsp;&nbsp;<img src="../../images/qingkong.gif" onclick="document.all('excelFile').value='';"> -->
		</td>
	</tr>
	<tr>
		<td height="6" colspan="2"></td>
	</tr>
</table>
	<input type="hidden" name="businessClass" value="<%=(request.getParameter("businessClass")==null?"":request.getParameter("businessClass"))%>"/>
	<input type="hidden" name="sheetName" value="" />
	<input type="hidden" name="querySQL" value="" />
</form>

<iframe id="simpleExpFrame" name="simpleExpFrame" width="0" height="0"></iframe>
<iframe id="waitForEndFrame" name="waitForEndFrame" width="0" height="0" style="display:none;" ></iframe>

<script>

/** �״������б����ݿ�ʼ */
Ext.onReady(function(){
	if(typeof(parent.fileNameSim)!='undefined'){
		document.simpleExcelForm.fileName.value = parent.fileNameSim+uuid+".xls";
	}
	if(typeof(parent.sheetNameSim)!='undefined'){
		document.simpleExcelForm.sheetName.value = parent.sheetNameSim;
	}
	if(typeof(parent.querySQLSim)!='undefined'){
		document.simpleExcelForm.querySQL.value = parent.querySQLSim;
	}
});

function openChooseFileWin(){
	document.getElementById("excelFile").click();
}

function formSubmit(){
	if(document.getElementById('fileName').value!=""){
		odin.ext.get(document.body).mask('����������...', odin.msgCls);
		document.simpleExcelForm.submit();
		doWaitingForEnd();	
	}else{
		odin.info('�������ļ�����');
	}
}

function doWaitingForEnd(){
	document.getElementById("waitForEndFrame").src=contextPath + "/sys/WaitForEnd.jsp";
}
function doCloseWin(){
	odin.ext.get(document.body).mask('����ˢ��ҳ��......', odin.msgCls);
	parent.odin.ext.getCmp('simpleExpWin').hide();
}
</script>

</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>