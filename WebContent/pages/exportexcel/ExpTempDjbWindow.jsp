<%@include file="/commform/basejs/loading/loading.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin-commform.tld" prefix="odin"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>导出excel文件</title>
<odin:head/>
</head>

<body >
<odin:base>

<form name="simpleExcelForm" method="post"  action="<%=request.getContextPath()%>/FiledownServlet"  target="simpleExpFrame">
<odin:groupBox title="下载文件信息">
<table align="center" width="96%">
	<tr>
		<odin:select property="excelType" label="表册类型" codeType="EXCELTYPE" editor="false" value="1"  data="['1', '公务员登记表'],['2', '参照公务员法管理机关（单位）工作人员登记表']" onchange="excelType"></odin:select>
	</tr>
<!-- 	<tr> -->
<%-- 		<odin:textEdit property="fileName" required="true" label="导出文件名" width="386" title="要导出的excel文件的名称"></odin:textEdit> --%>
<!-- 	</tr> -->
<!-- 	<tr> -->
<!-- 		<td height="10" colspan="2"></td> -->
<!-- 	</tr> -->
	<tr>
		<td align="right" colspan="2">
			<input type="button" style="cursor:hand;"  onclick="formSubmit()" value="生成文件">
		</td>
	</tr>
	<tr>
		<td height="6" colspan="2"></td>
	</tr>
</table>
</odin:groupBox>	
	<input type="hidden" name="a0000" id="a0000"/>
	<input type="hidden" name="a0101" id="a0101"/>
<odin:groupBox title="补充信息">			
<table>
<tr>
<odin:textarea property="djhsrzw" label="登记后所任职务" rows="3" cols="69"></odin:textarea>
</tr>
<tr>
<odin:textarea property="djhsdjb" label="登记后所定级别" rows="3" cols="69"></odin:textarea>
</tr>
<tr>
<odin:textarea property="szjgyj" label="所在机关意见" rows="3" cols="69"></odin:textarea>
</tr>
<tr>
<odin:textarea property="shjgyj" label="审核机关意见" rows="3" cols="69"></odin:textarea>
</tr>
<tr>
<odin:textarea property="spjgyj" label="审批机关意见" rows="3" cols="69"></odin:textarea>
</tr>
<tr>
<odin:textarea property="bz" label="备注" rows="3" cols="69"></odin:textarea>
</tr>
</table>
</odin:groupBox>							
</form>

<iframe id="simpleExpFrame" name="simpleExpFrame" width="0" height="0" style="display:none;" ></iframe>

<iframe id="waitForEndFrame" name="waitForEndFrame" width="0" height="0" style="display:none;" ></iframe>

<script>

/** 首次载入列表数据开始 */
Ext.onReady(function(){
	var PersonId = parent.getPersonIdForDj();
	document.getElementById("a0000").value = PersonId;
	var PersonName = parent.getPersonNameForDj();
	document.getElementById("a0101").value = PersonName;
	var a0101=document.getElementById("a0101").value;
	if(typeof(a0101)!='undefined'){
	    var excelType=document.getElementById("excelType").value;
	    var filename="";
	    if(excelType=='1'){
	    	filename="公务员登记表"
	    }else if(excelType=='2'){
	        filename="任免审批表"
	    }
		document.simpleExcelForm.fileName.value = a0101+'_'+filename+'.doc';
		//parent.fileNameSim = undefined;
	}
	//formSubmit();
});
function excelType(){
    checkSelect('excelType');
	var a0101=document.getElementById("a0101").value;
	var excelType=document.getElementById("excelType").value;
	var filename="";
    if(excelType=='1'){
    	filename="公务员登记表"
    }else if(excelType=='2'){
        filename="参照公务员法管理机关（单位）工作人员登记表"
    }
	document.simpleExcelForm.fileName.value =  a0101+'_'+filename+'.doc';
}
function openChooseFileWin(){
	document.getElementById("excelFile").click();
}

function formSubmit(){

	/* var djhsrzw = document.getElementById("djhsrzw").value;
	var djhsdjb = document.getElementById("djhsdjb").value;
	if(djhsdjb == ""){
		alert("登记后所定级别不能为空！");
		return;
	}
	if(djhsrzw == ""){
		alert("登记后所任职务不能为空！");
		return;
	} */
	parent.fileNameSim = undefined;
	odin.ext.get(document.body).mask('导出数据中...', odin.msgCls);
	document.simpleExcelForm.submit();
	doCloseWin();

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