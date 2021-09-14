<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="net.sf.json.JSONArray"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导入excel文件</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form name="excelForm" method="post"  action="<%=request.getContextPath()%>/CustomExcelServlet?method=VerificationSchemeExcelImp" enctype="multipart/form-data" target="impFrame">	
<table>
	<tr><td height="15">&nbsp;</td></tr>
</table>



<table align="center" width="96%">	
	<tr>
		<td colspan="2"><div class="x-form-item">文件选择&nbsp;<input type="file" name="excelFile" size="40"/></div></td>
	</tr>
	<tr>
		<td align="right">
			<%-- <odin:button text="&nbsp;&nbsp;导&nbsp;入&nbsp;&nbsp;" property="impBtn" handler="formSubmit()"></odin:button> --%>
			<img src="<%=request.getContextPath()%>/images/go1.jpg" onclick="formSubmit()">
			<!-- &nbsp;&nbsp;<img src="../../images/button/go1.jpg" onclick="document.all('excelFile').value='';"> -->
		</td>
		<td></td>
	</tr>
	<tr>
		<td height="6"></td>
	</tr>
</table>
	
	<input type="hidden" name="businessClass" value="<%=(request.getParameter("businessClass")==null?"":request.getParameter("businessClass"))%>"/>
	<input type="hidden" name="businessParam" id="param" />
	<input type="hidden" name="businessParam2" id="param2" />
</form>



<iframe id="impFrame" name="impFrame" width="0" height="0"></iframe>

<script>
function fillValue(){
	document.all.param.value = document.all.aae008.value;
}
function fillValue2(){
	if (document.all.eae003.value==""){
	}else{
		document.all.param2.value = document.all.eae003.value;
	}
}
/** 首次载入列表数据开始 */
<%--  Ext.onReady(function(){
	var impBtnText = "<%=(request.getParameter("impBtnText")==null?"":request.getParameter("impBtnText"))%>";
	if(impBtnText!=""){
		odin.ext.getCmp('impBtn').setText(impBtnText);
	}
});  --%>
function openChooseFileWin(){
	document.all.excelFile.click();
}
function formSubmit(){
	var excelFile = document.all('excelFile').value;
	if(excelFile!=""){
		var extStr = excelFile.substring(excelFile.lastIndexOf(".")+1);
		if(extStr!="xls"){
			alert('请选择.xls文件做导入处理！');
			return ;
		}else{
			/* odin.ext.get(document.body).mask('正在上传数据并处理中......', odin.msgCls);
			document.excelForm.submit();
			parent.odin.ext.getCmp('impExcelWin').hide();
			parent.radow.doEvent('impSuccess'); */
			var mk = new Ext.LoadMask(document.body, {  
				msg: '正在导入，请稍候！',  
				removeMask: true //完成后移除  
				});  
				mk.show(); //显示  
			 Ext.Ajax.request({
					url : "<%=request.getContextPath()%>/CustomExcelServlet?method=VerificationSchemeExcelImp",
					isUpload : true,
					form : "excelForm",
					
					success : function(response) {
						mk.hide(); //关闭  
						parent.odin.ext.getCmp('impExcelWin').hide();
						parent.radow.doEvent('impSuccess');

					}
				});
		}
	}else{
		alert('请选择文件之后再做导入处理！');
	}
	
}
function info(type){
	document.all('excelFile').value='';
	odin.ext.get(document.body).unmask();
/*		
	if(type==1){
		odin.info('数据已成功上传！',doCloseWin);
	}else if(type==2){
		odin.info('失败！',doCloseWin);
	}else if(type==3){
		odin.info('业务处理发生异常！',doCloseWin);
	}else if(type==4){
		odin.info('调用业务类异常！',doCloseWin);
	}
*/	
		doCloseWin(type);
}

var businessData = "";
function doCloseWin(type){
	odin.ext.get(document.body).mask('正在刷新页面......', odin.msgCls);
	parent.odin.ext.getCmp('impWin').hide();
	if(businessData!="" && type==1){
		if(typeof parent.resFuncImpExcel != 'undefined'){
			parent.resFuncImpExcel(odin.ext.decode(businessData));
		}
	}
}
</script>

</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>