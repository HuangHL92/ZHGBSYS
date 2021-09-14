<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@include file="/comOpenWinInit2.jsp" %>
<%@page import="net.sf.json.JSONArray"%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/xbrm/jquery-ui-12.1.css">
<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery-ui1.10.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">

<title>导入文件</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
</head>
<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css" />
<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>


<form name="data" method="post"  action="<%=request.getContextPath()%>/YearCheckServlet?method=Impfile" enctype="multipart/form-data" target="impFrame">
<!-- <form name="data" id="data" method="post"  enctype="multipart/form-data" > -->
<table>
	<tr><td height="15">&nbsp;</td></tr>
</table>

<table align="center" width="96%">	
     <tr>	
	      <odin:dateEdit property="time" label="时间" format="Ym" required="true"></odin:dateEdit>			
	</tr>
	<tr>
		<td height="15"></td>
	</tr>
	<tr>
	 <td colspan="2"> 
	       <iframe id="frame"  name="frame" height="33px" width="380px" src="<%=request.getContextPath() %>/pages/publicServantManage/adfile.jsp" frameborder=”no” border=”0″ marginwidth=”0″ marginheight=”0″ scrolling=”no” allowtransparency=”yes”></iframe>
			
		</td>	 
	</tr>
	<tr>
	<tr>
		<td height="30"></td>
	</tr>
		<td align="right" colspan="2">
			<odin:button text="&nbsp;&nbsp;导&nbsp;入&nbsp;&nbsp;" property="impBtn" handler="formSubmit()"></odin:button>
			<img src="<%=request.getContextPath()%>/images/go1.jpg" onclick="sc()">
			<!-- &nbsp;&nbsp;<img src="../../images/button/go1.jpg" onclick="document.all('excelFile').value='';"> -->
		</td>
		<td></td>
	</tr>
	<tr>
		<td height="6"></td>
	</tr>
</table>
		
	<odin:hidden property="a0000"/>
	<odin:hidden property="sort"/>

</form> 



<!-- <iframe id="impFrame" name="impFrame" width="0" height="0"></iframe> -->

<script>
Ext.onReady(function() {
	
	var para = parentParam.para;	
	//alert(para);
	document.getElementById('a0000').value =para.substr(0,para.length-1);
	document.getElementById('sort').value =para.substr(para.length-1,para.length);
	//alert(document.getElementById('a0000').value);	
});
function sc(){	
    var sort = document.getElementById('sort').value;
	var a0000 = document.getElementById('a0000').value;		
	var time = document.getElementById('time').value	
	if(time==null||time==""){
		alert("时间不可为空！");
	}else{
		frame.window.imp(sort,a0000,time);
	}
	
	/* if(sort==1){
		parent.odin.ext.getCmp('grid1').store.reload();
	}else if(sort==2){
		parent.odin.ext.getCmp('grid2').store.reload();
	}else if(sort==3){
		parent.odin.ext.getCmp('grid3').store.reload();
	}else if(sort==4){
		parent.odin.ext.getCmp('grid4').store.reload();
	} */
	
}

function restore(){
	parent.odin.ext.getCmp('grid1').store.reload();
	parent.odin.ext.getCmp('grid2').store.reload();
	parent.odin.ext.getCmp('grid3').store.reload();
	parent.odin.ext.getCmp('grid4').store.reload();
	window.close();
}

function formSubmit(){
	alert("0");
	var file = document.getElementById('abc').value;
    var sort = document.getElementById('sort').value
	var a0000 = document.getElementById('a0000').value	
	var time = document.getElementById('time').value	
	if(file!=""){
		
		var path = '<%=request.getContextPath()%>/YearCheckServlet?method=Impfile&a0000='+a0000+'&sort='+sort+'&time='+time;
		alert(path);
		odin.ext.Ajax.request({
			//Ext.Ajax.request({
			url:path,
			isUpload:true,
			method:'post',
			fileUpload:true,
			form:'data',
			success:function(data){
					var result = data.responseText;
					if(result=="success"){
					/* 	parent.parentgridquery(); */
					}else{
						alert("附件上传失败");
					}
						
				},
			failure : function(){
				alert("上传附件失败");
			}
		});
	}else{
		/* parent.radow.doEvent("appendfile"); */
		//parent.parentgridquery();
	}
}

<%-- function formSubmit(){
	var a0000 = document.getElementById('a0000').value;
	alert(a0000);
	var excelFile = document.all('excelFile').value;
	if(excelFile!=""){
		//var extStr = excelFile.substring(excelFile.lastIndexOf(".")+1);
		//if(extStr!="xls"){
		//	alert('请选择.xls文件做导入处理！');
		//	return ;
		//}else{
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
					url : "<%=request.getContextPath()%>/YearCheckServlet?method=Impfile",
					isUpload : true,
					form : "Form",
					
					success : function(response) {
						mk.hide(); //关闭  
						parent.odin.ext.getCmp('impExcelWin').hide();
						parent.radow.doEvent('impSuccess');

					}
				});
		//}
	}else{
		alert('请选择文件之后再做导入处理！');
	}
	
} --%>
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