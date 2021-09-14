<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>����excel�ļ�</title>
<odin:commformhead/>
<odin:commformMDParam></odin:commformMDParam>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form name="excelForm" method="post"  action="<%=request.getContextPath()%>/excel/commformexcelServlet?method=impExcelAndProcess" enctype="multipart/form-data" target="impFrame">	
<table align="center" width="96%">
	<tr>
		<td height="20"></td>
	</tr>
	<tr>
		<td><div class="x-form-item">�ļ�ѡ��<input type="file" name="excelFile" size="47"/></div></td>
	</tr>
	<tr>
		<td height="15"></td>
	</tr>
	<tr>
		<td align="center">
			<input style="visibility:hidden" id="doSub1" type="button" style="cursor:hand;" onclick="formSubmit(1)" value="���뵽����"></input>
			<input style="visibility:hidden" id="doSub2" type="button" style="cursor:hand;" onclick="formSubmit(2)" value="����ȷ��"></input>
			<!-- &nbsp;&nbsp;<img src="../../images/qingkong.gif" onclick="document.all('excelFile').value='';"> -->
		</td>
	</tr>
	<tr>
		<td height="6"></td>
	</tr>
</table>
	
	<input type="hidden" name="businessClass" value="<%=(request.getParameter("businessClass")==null?"":request.getParameter("businessClass"))%>"/>
</form>

<iframe id="impFrame" name="impFrame" width="0" height="0"></iframe>

<script>
/** �״������б����ݿ�ʼ */
Ext.onReady(function(){
	//
	if(parent.commParams.currentValue=="0"){
		document.getElementById("doSub1").style.visibility="visible";
		document.getElementById("doSub2").style.visibility="visible";
	}else if(parent.commParams.currentValue=="1"){
		document.getElementById("doSub1").style.visibility="visible";
		document.getElementById("doSub2").style.visibility="hidden";
		document.getElementById("doSub2").style.width=0;
	}else if(parent.commParams.currentValue=="2"){
		document.getElementById("doSub1").style.visibility="hidden";
		document.getElementById("doSub2").style.visibility="visible";
		document.getElementById("doSub1").style.width=0;
	}
});
function openChooseFileWin(){
	document.all.excelFile.click();
}
function formSubmit(type){
	var fileName = document.all('excelFile').value;
	if(fileName!="" && fileName.substr(fileName.length-4).toLowerCase()!=".xls"){
		odin.info('�ļ����Ͳ��ԣ���ѡ��Excel�ļ���');
	}else if(fileName!=""){
		
		//alert(document.all('excelFile').value);
		odin.ext.get(document.body).mask('�ϴ�������...', odin.msgCls);
		parent.commParams.currentValue=type;
		document.excelForm.submit();
	}else{
		odin.info('��ѡ���ļ�֮���������봦��');
	}
}
function info(type){
	document.all('excelFile').value='';
	odin.ext.get(document.body).unmask();
	if(type==1){
		//odin.info('�����ѳɹ��ϴ���������ϣ�',doCloseWin);
		parent.doAfterImp();
	}else if(type==2){
		odin.info('ʧ�ܣ�',doCloseWin);
	}else if(type==3){
		odin.info('ҵ�������쳣��',doCloseWin);
	}else if(type==4){
		odin.info('����ҵ�����쳣��',doCloseWin);
	}
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