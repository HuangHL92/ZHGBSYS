<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>导出excel文件</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form name="excelForm" method="post"  action="<%=request.getContextPath()%>/excel/excelServlet?method=expExcelFile"  target="expFrame">	
<table align="center" width="96%">
	<tr>
		<td height="20" colspan="2"></td>
	</tr>
	<tr>
		<odin:textEdit property="fileName" required="true" label="导出文件名" width="390" title="可以带‘.xls’或不带，不带时系统会自动在末端加上excel文件后缀。"></odin:textEdit>
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
	<input type="hidden" name="helpClassName" value="<%=(request.getParameter("helpClassName")==null?"":request.getParameter("helpClassName"))%>"/>
	<input type="hidden" name="parames" value="" />
</form>

<iframe id="expFrame" name="expFrame" width="0" height="0"></iframe>

<script>

/** 首次载入列表数据开始 */
Ext.onReady(function(){
	if(typeof(parent.defaultExcelFileName)!='undefined'){
		document.excelForm.fileName.value = parent.defaultExcelFileName;
	}
	if(typeof(parent.excelQueryParames)!='undefined' && parent.excelQueryParames!=""){
		document.excelForm.parames.value = parent.excelQueryParames;
	}
});

function openChooseFileWin(){
	document.getElementById("excelFile").click();
}

function formSubmit(){
	if(document.getElementById('fileName').value!=""){
		//alert(document.getElementById('excelFile').value);
		//odin.ext.get(document.body).mask('正在导出数据中......', odin.msgCls);
		document.excelForm.submit();
		parent.odin.ext.getCmp('expWin').hide();
	}else{
		odin.info('请输入文件名！');
	}
}

function doCloseWin(){
	odin.ext.get(document.body).mask('正在刷新页面......', odin.msgCls);
	parent.odin.ext.getCmp('impWin').hide();
}

</script>

</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>