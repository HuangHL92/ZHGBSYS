<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�����ļ�</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form name="excelForm" method="post"  action="<%=request.getContextPath()%>/servlet/UpLoadLrmServlet" enctype="multipart/form-data" target="impFrame">	
<table align="center" width="96%">
	<tr>
		<td height="20"></td>
	</tr>
	<tr>
		<td ><label id="rzjg" style="font-size: 12" >�ļ�ѡ��</label></td>
		<td ><input type="file" id="normalFile" name="normalFile" multiple="multiple"  size="47" accept=".Lrmx"/></td>
	</tr>
	<tr></tr>
	<tr>
		<td height="15" colspan="2"><label id="rzjg" style="font-size: 12" ><font color="red">ע����ѡ������������Ա��ʾ�ڻ�����������ְ��Ա��</font></label></td>
	</tr>
	<tr id = "tr1" align="left">
	       <td><label id="rzjg" style="font-size: 12" >�������</label></td>
	       <tags:PublicTextIconEdit3 property="a0201b" codetype="orgTreeJsonData"/>
	</tr>
	<tr>
		<td></td>
		<td align="right">
			<odin:button text="����" property="impBtn" handler="formSubmit"></odin:button>
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
function formSubmit(){
	if(document.getElementById('normalFile').value!=""){
		//alert(document.getElementById('excelFile').value);
		odin.ext.get(document.body).mask('�����ϴ����ݲ�������......', odin.msgCls);
		document.excelForm.submit();
	}else{
		odin.info('��ѡ���ļ�֮���������봦��');
	}
}

</script>

</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>