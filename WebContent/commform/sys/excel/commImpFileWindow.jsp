<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin-commform.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�����ļ�</title>
<odin:commformhead/>
<odin:commformMDParam></odin:commformMDParam>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form name="fileForm" method="post"  action="<%=request.getContextPath()%>/excel/commformexcelServlet?method=impExcelAndProcess" enctype="multipart/form-data" target="impFrame">	
<table >
	<tr height="35">
		<odin:select property="fileType" label="�ļ�����" codeType="FILETYPE" editor="true"></odin:select>
	</tr>
	<tr>
		<div class="x-form-item"><td nowrap align='right'><span style='font-size:12px' >�ļ�ѡ��&nbsp;</span></td><td nowrap colspan="2"><input type="file" name="fileName" size="47"/></td></div>
	</tr>
	<tr>
		<td height="15"></td>
	</tr>
	<tr>
		<td align="center" colspan="2">
			<input style="visibility:hidden" id="doSub1" type="button" style="cursor:hand;" onclick="formSubmit(1)" value="����ȷ��" ></input> <!-- ���뵽���� -->
			<input style="visibility:hidden" id="doSub2" type="button" style="cursor:hand;" onclick="formSubmit(2)" value="����ȷ��" ></input> <!-- ���벢���� -->
		</td>
	</tr>
	<tr>
		<td height="6"></td>
	</tr>
</table>
	
	<input type="hidden" name="businessClass" value="<%=(request.getParameter("businessClass")==null?"":request.getParameter("businessClass"))%>"/>
	<input type="hidden" name="fix" value=""/>
</form>

<iframe id="impFrame" name="impFrame" width="0" height="0"></iframe>

<script>
/** �״������б����ݿ�ʼ */
Ext.onReady(function(){
	//
	if(parent.commParams.currentValue=="0"){
		document.getElementById("doSub1").style.visibility="visible";
		document.getElementById("doSub1").value="���뵽����";
		document.getElementById("doSub2").style.visibility="visible";
		document.getElementById("doSub2").value="���벢����";
	}else if(parent.commParams.currentValue=="1"){
		document.getElementById("doSub1").style.visibility="visible";
		document.getElementById("doSub2").style.visibility="hidden";
		document.getElementById("doSub2").style.width=0;
	}else if(parent.commParams.currentValue=="2"){
		document.getElementById("doSub1").style.visibility="hidden";
		document.getElementById("doSub2").style.visibility="visible";
		document.getElementById("doSub1").style.width=0;
	}
	if(parent.commParams.currentFileType!="0"){
		odin.setSelectValue("fileType",parent.commParams.currentFileType);
		opItemDisabled("","fileType","true");
	}
});
function openChooseFileWin(){
	document.all.fileName.click();
}
function formSubmit(type){
	var fileName = document.all('fileName').value;
	var fileType = document.all('fileType').value;
	var fix = parent.fix;
	if(fileType==null||fileType==""){
		odin.info('��ѡ���ļ����ͣ�');
	}
	if(fileType=="2"){
		document.fileForm.action = "<%=request.getContextPath()%>/text/textServlet?method=impTextAndProcess&fix="+fix;
	}
	if(fileType=="3"){
		document.fileForm.action = "<%=request.getContextPath()%>/dbf/dbfServlet?method=impDbfAndProcess";
	}
	if(fileName!="" && fileType=="1" && !(fileName.substr(fileName.indexOf(".")).toLowerCase()==".xls" || fileName.substr(fileName.indexOf(".")).toLowerCase()==".xlsx" && !fix)){
		odin.info('�ļ����Ͳ��ԣ���ѡ��Excel�ļ���');
	}else if(fileName!="" && fileType=="2" &&  !(fileName.substr(fileName.length-4).toLowerCase()!=".txt" || fileName.substr(fileName.length-4).toLowerCase()!=".csv" && !fix)){
		odin.info('�ļ����Ͳ��ԣ���ѡ��Text�ļ���');
	}else if(fileName!="" && fileType=="3" &&  fileName.substr(fileName.length-4).toLowerCase()!=".dbf" && !fix){
		odin.info('�ļ����Ͳ��ԣ���ѡ��Dbf�ļ���');
	}else if(fileName!=""){
		
		//alert(document.all('fileName').value);
		odin.ext.get(document.body).mask('�ϴ�������...', odin.msgCls);
		parent.commParams.currentValue=type;
		document.fileForm.submit();
	}else{
		odin.info('��ѡ���ļ�֮���������봦��');
	}
}
function info(type){
	document.all('fileName').value='';
	odin.ext.get(document.body).unmask();
	if(type==1){
		odin.info('�ļ��ѳɹ��ϴ���ϣ�',doCloseWin);
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