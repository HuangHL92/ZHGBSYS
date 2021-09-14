<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>导出excel文件</title>
<odin:commformhead/>
<odin:commformMDParam></odin:commformMDParam>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form name="simpleExcelForm" method="post"  action="<%=request.getContextPath()%>/excel/commformexcelServlet?method=simpleExpExcelFile"  target="simpleExpFrame">	
<table align="center" width="96%">
	<tr>
		<td height="20" colspan="2"></td>
	</tr>
	<tr>
		<odin:textEdit property="fileName" required="true" label="导出文件名" width="390" title="要导出的excel文件的名称"></odin:textEdit>
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

<script>

/** 首次载入列表数据开始 */
Ext.onReady(function(){
	if(typeof(parent.fileNameSim)!='undefined'){
		document.simpleExcelForm.fileName.value = parent.fileNameSim;
	}
	if(typeof(parent.sheetNameSim)!='undefined'){
		document.simpleExcelForm.sheetName.value = parent.sheetNameSim;
	}
	if(typeof(parent.querySQLSim)!='undefined'){
		document.simpleExcelForm.querySQL.value = parent.querySQLSim;
	}
});

function openChooseFileWin(){
	//document.all.excelFile.click();
}

function formSubmit(){
	if(document.all('fileName').value!=""){
		//alert(document.all('excelFile').value);
		//odin.ext.get(document.body).mask('正在导出数据中......', odin.msgCls);
		document.simpleExcelForm.submit();
		parent.odin.ext.getCmp('simpleExpWin').hide();
	}else{
		odin.info('请输入文件名！');
	}
}


</script>

</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>