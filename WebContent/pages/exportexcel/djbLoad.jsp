<%@include file="/commform/basejs/loading/loading.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin-commform.tld" prefix="odin"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>����excel�ļ�</title>
<odin:head/>
</head>
<%--  <%   String a0101=(java.net.URLDecoder.decode(request.getParameter("a0101"), "UTF-8")==null?"":java.net.URLDecoder.decode(request.getParameter("a0101"), "UTF-8")) ;
	request.getSession().setAttribute("a0101",a0101);
%> --%>
<body >
<odin:base>

<form name="simpleExcelForm" method="post"  action="<%=request.getContextPath()%>/FiledownServlet?downLoad=true"  target="simpleExpFrame">
<odin:groupBox title="�����ļ���Ϣ">
<table align="center" width="96%">
	<tr>
		<odin:select property="excelType" label="�������" codeType="EXCELTYPE" editor="false" value="1"  data="['1', '����Ա�ǼǱ�'],['2', '���չ���Ա��������أ���λ��������Ա�ǼǱ�']" onchange="excelType"></odin:select>
	</tr>
	<tr>
		<odin:textEdit property="fileName" required="true" label="�����ļ���" width="386" title="Ҫ������excel�ļ�������"></odin:textEdit>
	</tr>
	<tr>
		<td height="10" colspan="2"></td>
	</tr>
	<tr>
		<td align="right" colspan="2">
			<input type="button" style="cursor:hand;"  onclick="formSubmit()" value="�����ļ�">
		</td>
	</tr>
	<tr>
		<td height="6" colspan="2"></td>
	</tr>
</table>
</odin:groupBox>	
	<input type="hidden" name="a0000" id="a0000" >
	<input type="hidden" name="a0101" id="a0101">
	<input type="hidden" name="djbname" id="djbname" value="<%=(java.net.URLDecoder.decode(request
			.getParameter("filename"), "UTF-8")==null?"":java.net.URLDecoder.decode(request
					.getParameter("filename"), "UTF-8"))%>">
</form>

<iframe id="simpleExpFrame" name="simpleExpFrame" width="0" height="0" style="display:none;" ></iframe>

<iframe id="waitForEndFrame" name="waitForEndFrame" width="0" height="0" style="display:none;" ></iframe>

<script>

/** �״������б����ݿ�ʼ */
Ext.onReady(function(){
	var PersonId = parent.getPersonIdForDj();
	document.getElementById("a0000").value = PersonId;
	var PersonName = parent.getPersonNameForDj();
	document.getElementById("a0101").value = PersonName;
	var a0101=document.getElementById("a0101").value;
	var djbname=document.getElementById("djbname").value;
	if(typeof(a0101)!='undefined'){
	    var excelType=document.getElementById("excelType").value;
	    var filename="";
	    if(excelType=='1'){
	    	filename="����Ա�ǼǱ�"
	    }else if(excelType=='2'){
	        filename="����������"
	    }
		document.simpleExcelForm.fileName.value = djbname+'_'+filename+'.doc';
		//parent.fileNameSim = undefined;
	}
	//formSubmit();
});
function excelType(){
    checkSelect('excelType');
	var a0101=document.getElementById("a0101").value;
	var djbname=document.getElementById("djbname").value;
	var excelType=document.getElementById("excelType").value;
	var filename="";
    if(excelType=='1'){
    	filename="����Ա�ǼǱ�"
    }else if(excelType=='2'){
        filename="���չ���Ա��������أ���λ��������Ա�ǼǱ�"
    }
	document.simpleExcelForm.fileName.value =  djbname+'_'+filename+'.doc';
}
function openChooseFileWin(){
	document.getElementById("excelFile").click();
}

function formSubmit(){
	if(document.getElementById('fileName').value!=""){
		parent.fileNameSim = undefined;
		odin.ext.get(document.body).mask('����������...', odin.msgCls);
		document.simpleExcelForm.submit();
		doCloseWin();
		//doWaitingForEnd();
	}else{
		parent.odin.info('�������ļ�����',doCloseWin);
	}
}

function doWaitingForEnd(){
	document.getElementById("waitForEndFrame").src=contextPath + "/sys/WaitForEnd.jsp";
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