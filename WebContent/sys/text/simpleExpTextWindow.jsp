<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>导出Text文件</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
</head>

<script type="text/javascript">
var uuid="<%=java.util.UUID.randomUUID().toString()%>";
</script>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form name="simpleTextForm" method="post"  action="<%=request.getContextPath()%>/text/textServlet?method=simpleExpTextFile"  target="simpleExpFrame">	
<table align="center" width="96%">
	<tr>
		<td height="20" colspan="2"></td>
	</tr>
	<tr>
		<odin:textEdit property="fileName" required="true" label="导出文件名" width="390" title="要导出的txt文件的名称"></odin:textEdit>
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
	<input type="hidden" name="separator" value="" />
	<input type="hidden" name="querySQL" value="" />
	<input type="hidden" name="withoutHead" value="" />
</form>

<iframe id="simpleExpFrame" name="simpleExpFrame" width="0" height="0"></iframe>
<iframe id="waitForEndFrame" name="waitForEndFrame" width="0" height="0" style="display:none;" ></iframe>
<script>

/** 首次载入列表数据开始 */
Ext.onReady(function(){
	if(typeof(parent.fileNameSim)!='undefined'){
		document.simpleTextForm.fileName.value = parent.fileNameSim+uuid+".txt";
	}
	if(typeof(parent.separator)!='undefined'){
		document.simpleTextForm.separator.value = parent.separator;
	}
	if(typeof(parent.withoutHead)!='undefined'){
		document.simpleTextForm.withoutHead.value = parent.withoutHead;
	}
	if(typeof(parent.querySQLSim)!='undefined'){
		document.simpleTextForm.querySQL.value = parent.querySQLSim;
	}
});

function openChooseFileWin(){
	//document.getElementById("excelFile").click();
}

function formSubmit(){
	if(document.getElementById('fileName').value!=""){
		odin.ext.get(document.body).mask('导出数据中...', odin.msgCls);
		document.simpleTextForm.submit();
		doWaitingForEnd();
	}else{
		odin.info('请输入文件名！');
	}
}

function doWaitingForEnd(){
	document.getElementById("waitForEndFrame").src=contextPath + "/sys/WaitForEnd.jsp";
}
function doCloseWin(){
	odin.ext.get(document.body).mask('正在刷新页面......', odin.msgCls);
	parent.odin.ext.getCmp('simpleTextExpWin').hide();
}
</script>

</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>