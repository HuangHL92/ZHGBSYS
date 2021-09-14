<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin-commform.tld" prefix="odin"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>导出excel文件</title>
<odin:head/>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form name="simpleExcelForm" method="post"  action="<%=request.getContextPath()%>/excel/commformexcelServlet?method=simpleExpExcelFile"  target="simpleExpFrame">	
<table align="center" width="96%">
	<%-- <tr>
		<odin:select property="excelType" label="EXCEL类型" codeType="EXCELTYPE" editor="false" value="1"  data="['1', 'XLS格式'],['2', 'XLSX格式']" onchange="excelType"></odin:select>
	</tr> --%>
	<tr >
		<td style = "height:25px"/>
	</tr>
	<tr>
		<odin:textEdit property="fileName" required="true" label="导出文件名" width="390" title="要导出的excel文件的名称"></odin:textEdit>
	</tr>
	<tr>
		<td height="10" colspan="2"></td>
	</tr>
	<tr>
		<td align="center" colspan="2">
			<input type="button" style="cursor:hand;"  onclick="formSubmit()" value="下载文件">
		</td>
	</tr>
	<tr>
		<td height="6" colspan="2"></td>
	</tr>
</table>
	<input type="hidden" name="businessClass" value="<%=(request.getParameter("businessClass")==null?"":request.getParameter("businessClass"))%>"/>
	<input type="hidden" name="sheetName" value="" />
	<input type="hidden" name="querySQL" value="" />
	<input type="hidden" name="headNames" value="" />
	<input type="hidden" name="jsonData" value="" />
	<input type="hidden" name="withoutHead" value="" />
	<input type="hidden" name="decodeJson" value="" />
	<input type="hidden" name="jsonType" value="" />
</form>

<iframe id="simpleExpFrame" name="simpleExpFrame" width="0" height="0" style="display:none;" ></iframe>

<iframe id="waitForEndFrame" name="waitForEndFrame" width="0" height="0" style="display:none;" ></iframe>

<script>

/** 首次载入列表数据开始 */
Ext.onReady(function(){
	if(typeof(parent.fileNameSim)!='undefined'){
	    //var excelType=document.getElementById("excelType").value;
	    var filename="";
	    //if(excelType=='1'){
	    	filename=".xls"
	   /*  }else if(excelType=='2'){
	        filename=".xlsx"
	    } */
		document.simpleExcelForm.fileName.value = parent.fileNameSim+filename;
		//parent.fileNameSim = undefined;
	}
	if(typeof(parent.sheetNameSim)!='undefined'){
		document.simpleExcelForm.sheetName.value = parent.sheetNameSim;
		parent.sheetNameSim = undefined;
	}
	if(typeof(parent.querySQLSim)!='undefined'){
		document.simpleExcelForm.querySQL.value = parent.querySQLSim;
		parent.querySQLSim = undefined;
	}
	if(typeof(parent.headNamesSim)!='undefined'){
		document.simpleExcelForm.headNames.value = parent.headNamesSim;
		parent.headNamesSim = undefined;
	}
	if(typeof(parent.jsonDataSim)!='undefined'){
		document.simpleExcelForm.jsonData.value = parent.jsonDataSim;
		parent.jsonDataSim = undefined;
	}
	if(typeof(parent.withoutHeadSim)!='undefined'){
		document.simpleExcelForm.withoutHead.value = parent.withoutHeadSim;
		parent.withoutHeadSim = undefined;
	}
	if(typeof(parent.decodeJsonSim)!='undefined'){
		document.simpleExcelForm.decodeJson.value = parent.decodeJsonSim;
		parent.decodeJsonSim = undefined;
	}
	if(typeof(parent.jsonTypeSim)!='undefined'){
		document.simpleExcelForm.jsonType.value = parent.jsonTypeSim;
		parent.jsonTypeSim = undefined;
	}
	//formSubmit();
});
function excelType(){
    //checkSelect('excelType');
	//var excelType=document.getElementById("excelType").value;
	var filename="";
    //if(excelType=='1'){
    	filename=".xls"
    //}else if(excelType=='2'){
    //    filename=".xlsx"
    //}
	document.simpleExcelForm.fileName.value = document.simpleExcelForm.fileName.value.substr(0,document.simpleExcelForm.fileName.value.indexOf("."))+filename;
}
function openChooseFileWin(){
	document.getElementById("excelFile").click();
}

function formSubmit(){
	if(document.getElementById('fileName').value!=""){
		parent.fileNameSim = undefined;
		odin.ext.get(document.body).mask('导出数据中...', odin.msgCls);
		document.simpleExcelForm.submit();
		//doCloseWin();
		doWaitingForEnd();
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