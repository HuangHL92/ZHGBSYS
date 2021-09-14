<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>����text�ļ�</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form name="excelForm" method="post"  action="<%=request.getContextPath()%>/text/textServlet?method=impTextAndProcess" enctype="multipart/form-data" target="impFrame">	
<table align="center" width="96%">
	<tr>
		<td height="20"></td>
	</tr>
	<tr>
		<td><div class="x-form-item">�ļ�ѡ��<input type="file" id="excelFile" name="excelFile" size="47"/></div></td>
	</tr>
	<tr>
		<td height="15"></td>
	</tr>
	<tr>
		<td align="right">
			<odin:button text="����" property="impBtn" handler="formSubmit"></odin:button>
			<!-- <img src="../../images/baocun.gif" onclick="formSubmit()"> -->
			<!-- &nbsp;&nbsp;<img src="../../images/qingkong.gif" onclick="document.all('excelFile').value='';"> -->
		</td>
	</tr>
	<tr>
		<td height="6"></td>
	</tr>
</table>
	
	<input type="hidden" name="businessClass" value="<%=(request.getParameter("businessClass")==null?"":request.getParameter("businessClass"))%>"/>
	<input type="hidden" name="businessParam" value="<%=(request.getParameter("businessParam")==null?"":request.getParameter("businessParam"))%>"/>
	
</form>

<iframe id="impFrame" name="impFrame" width="0" height="0"></iframe>

<script>
/** �״������б����ݿ�ʼ */
Ext.onReady(function(){
	var impBtnText = "<%=(request.getParameter("impBtnText")==null?"":request.getParameter("impBtnText"))%>";
	if(impBtnText!=""){
		odin.ext.getCmp('impBtn').setText(impBtnText);
	}
});
function openChooseFileWin(){
	document.all.excelFile.click();
}
function formSubmit(){
	if(document.getElementById('excelFile').value!=""){
		//alert(document.getElementById('excelFile').value);
		odin.ext.get(document.body).mask('�����ϴ����ݲ�������......', odin.msgCls);
		document.excelForm.submit();
	}else{
		odin.info('��ѡ���ļ�֮���������봦��');
	}
}
function info(type){
	document.getElementById('excelFile').value='';
	odin.ext.get(document.body).unmask();
	/*
	if(type==1){
		odin.info('�����ѳɹ��ϴ���������ϣ�',doCloseWin);
	}else if(type==2){
		odin.info('ʧ�ܣ�',doCloseWin);
	}else if(type==3){
		odin.info('ҵ�������쳣��',doCloseWin);
	}else if(type==4){
		odin.info('����ҵ�����쳣��',doCloseWin);
	}
	*/
	doCloseWin(type);
}

var businessData = "";
function doCloseWin(type){
	odin.ext.get(document.body).mask('����ˢ��ҳ��......', odin.msgCls);
	parent.odin.ext.getCmp('impTextWin').hide();
	if(businessData!=""&&type==1){
		if(businessData.indexOf("\n")>=0){
			parent.odin.alert("��������ʧ�ܣ����ݸ�ʽ�Ƿ���");
			return;
		}
		if(typeof parent.resFuncImpText != 'undefined'){
			parent.resFuncImpText(odin.ext.decode(businessData));
		}
	}
}
</script>

</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>