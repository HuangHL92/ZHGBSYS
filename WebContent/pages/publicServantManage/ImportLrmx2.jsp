<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>导入文件</title>
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
		<td ><label id="rzjg" style="font-size: 12" >文件选择</label></td>
		<td ><input type="file" id="normalFile" name="normalFile" multiple="multiple"  size="47" accept=".Lrmx"/></td>
	</tr>
	<tr></tr>
	<tr>
		<td height="15" colspan="2"><label id="rzjg" style="font-size: 12" ><font color="red">注：不选择机构则导入的人员显示在机构树其他现职人员下</font></label></td>
	</tr>
	<tr id = "tr1" align="left">
	       <td><label id="rzjg" style="font-size: 12" >导入机构</label></td>
	       <tags:PublicTextIconEdit3 property="a0201b" codetype="orgTreeJsonData"/>
	</tr>
	<tr>
		<td></td>
		<td align="right">
			<odin:button text="导入" property="impBtn" handler="formSubmit"></odin:button>
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
/** 首次载入列表数据开始 */
Ext.onReady(function(){
	var impBtnText = "<%=(request.getParameter("impBtnText")==null?"":request.getParameter("impBtnText"))%>";
	if(impBtnText!=""){
		odin.ext.getCmp('impBtn').setText(impBtnText);
	}
});
function formSubmit(){
	if(document.getElementById('normalFile').value!=""){
		//alert(document.getElementById('excelFile').value);
		odin.ext.get(document.body).mask('正在上传数据并处理中......', odin.msgCls);
		document.excelForm.submit();
	}else{
		odin.info('请选择文件之后再做导入处理！');
	}
}

</script>

</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>