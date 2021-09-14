<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>����ZIP�ļ�</title>
<odin:commformhead/>
<odin:commformMDParam></odin:commformMDParam>
</head>


<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form name="simpleExcelForm" method="post"  action="<%=request.getContextPath()%>/excel/commformexcelServlet?method=simpleExpGZIPExclFile"  target="simpleExpFrame">	
<table align="center" width="96%">	
	<tr>
		<odin:select property="excelType" label="EXCEL����" codeType="EXCELTYPE" editor="false" value="1"  onchange="excelType"></odin:select>
	</tr>
	<tr>
		<odin:textEdit property="fileName" required="true" label="�����ļ���" width="390" title="Ҫ������zip�ļ�������"></odin:textEdit>
	</tr>
	<tr>
		<td height="2" colspan="2"></td>
	</tr>
	<tr>
		<td align="center" colspan="2">
			<input type="button" style="cursor:hand;"  onclick="formSubmit()" value="�����ļ�" />
		</td>
	</tr>
	<tr>
		<td height="6" colspan="2"></td>
	</tr>
</table>
	<input type="hidden" name="businessClass" value="<%=(request.getParameter("businessClass")==null?"":request.getParameter("businessClass"))%>"/>
	<input type="hidden" name="querySQL" value="" />
	<input type="hidden" name="headNames" value="" />
	<input type="hidden" name="sheetName" value="" />
	<input type="hidden" name="childfilename" value="" />
</form>

<iframe id="simpleExpFrame" name="simpleExpFrame" width="0" height="0" style="display:none;" ></iframe>


<iframe id="waitForEndFrame" name="waitForEndFrame" width="0" height="0" style="display:none;" ></iframe>
<script>

/** �״������б����ݿ�ʼ */
Ext.onReady(function(){
	if(typeof(parent.fileNameSim)!='undefined'){
		document.simpleExcelForm.fileName.value = parent.fileNameSim;
	}
	if(typeof(parent.querySQLSim)!='undefined'){
		document.simpleExcelForm.querySQL.value = parent.querySQLSim;
	}
	if(typeof(parent.headNamesSim)!='undefined'){
		document.simpleExcelForm.headNames.value = parent.headNamesSim;
	}
	
	if(typeof(parent.sheetNameSim)!='undefined'){
		document.simpleExcelForm.sheetName.value = parent.sheetNameSim;
	}
	
	if(typeof(parent.childfilenameSim)!='undefined'){
		 var excelType=document.getElementById("excelType").value;
		 var filename="";
		 if(excelType=='1'){
		     filename=".xls"
		 }else if(excelType=='2'){
		     filename=".xlsx"
		 }
		document.simpleExcelForm.childfilename.value = parent.childfilenameSim + filename;
	}	
	
});

function excelType(){
    checkSelect('excelType');
	var excelType=document.getElementById("excelType").value;
	var filename="";
    if(excelType=='1'){
    	filename=".xls"
    }else if(excelType=='2'){
        filename=".xlsx"
    }
	document.simpleExcelForm.childfilename.value = parent.childfilenameSim + filename;
}

function openChooseFileWin(){
	//document.all.excelFile.click();
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