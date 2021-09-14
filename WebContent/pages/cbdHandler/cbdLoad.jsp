<%@include file="/commform/basejs/loading/loading.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin-commform.tld" prefix="odin"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>导出excel文件</title>
<odin:head/>
</head>
<%   String cbd_id=(java.net.URLDecoder.decode(request.getParameter("cbd_id"), "UTF-8")==null?"":java.net.URLDecoder.decode(request.getParameter("cbd_id"), "UTF-8")) ;
	request.getSession().setAttribute("cbd_id",cbd_id);
	String cbd_type = request.getParameter("cbd_type");
	cbd_type = "1"+cbd_type;
%>
<body >
<odin:base>

<form name="simpleExcelForm" method="post"  action="<%=request.getContextPath()%>/CBDFiledownServlet?downLoad=true"  target="simpleExpFrame">
<odin:groupBox title="下载文件信息">
<table align="center" width="96%">
	<tr>
		<odin:select property="excelType1" label="表册类型" codeType="EXCELTYPE" editor="false" value="<%=cbd_type %>"  data="['11', '本级呈报单'],['12', '上报呈报单']" disabled="true"></odin:select>
	</tr>
	<tr>
		<odin:textEdit property="fileName" required="true" label="导出文件名" width="386" title="要导出的word文件的名称"></odin:textEdit>
	</tr>
	<tr>
		<td height="10" colspan="2"></td>
	</tr>
	<tr>
		<td align="right" colspan="2">
			<input type="button" style="cursor:hand;"  onclick="formSubmit()" value="下载文件">
		</td>
	</tr>
	<tr>
		<td height="6" colspan="2"></td>
	</tr>
</table>
</odin:groupBox>	
	<input type="hidden" name="cbd_id" value="<%=(request.getParameter("cbd_id")==null?"":request.getParameter("cbd_id")+"@"+(request.getParameter("flag")==null?"":request.getParameter("flag")))%>"/>
	<input type="hidden" name="cbd_name" id="cbd_name" value="<%=(java.net.URLDecoder.decode(request
			.getParameter("cbd_name"), "UTF-8")==null?"":java.net.URLDecoder.decode(request
					.getParameter("cbd_name"), "UTF-8"))%>"/>
	<input type="hidden" name="flag" value="<%=(request.getParameter("flag")==null?"":request.getParameter("flag")) %>">
	<odin:hidden property="excelType" value="<%=cbd_type %>"/>
</form>

<iframe id="simpleExpFrame" name="simpleExpFrame" width="0" height="0" style="display:none;" ></iframe>

<iframe id="waitForEndFrame" name="waitForEndFrame" width="0" height="0" style="display:none;" ></iframe>

<script>

/** 首次载入列表数据开始 */
Ext.onReady(function(){
	var cbd_name=document.getElementById("cbd_name").value;
	if(typeof(cbd_name)!='undefined'){
		document.simpleExcelForm.fileName.value = cbd_name+'.doc';
	}
});

function formSubmit(){
	if(document.getElementById('fileName').value!=""){
		parent.fileNameSim = undefined;
		odin.ext.get(document.body).mask('导出数据中...', odin.msgCls);
		document.simpleExcelForm.submit();
		doCloseWin();
		//doWaitingForEnd();
	}else{
		parent.odin.info('请输入文件名！',doCloseWin);
	}
}

function doWaitingForEnd(){
	document.getElementById("waitForEndFrame").src=contextPath + "/sys/WaitForEnd.jsp";
}

function doCloseWin(){
	odin.ext.get(document.body).mask('正在刷新页面......', odin.msgCls);
	parent.doHiddenPupWin();
}
</script>

</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>