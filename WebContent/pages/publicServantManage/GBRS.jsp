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
<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/helperUtil.js"></script>
<style>
body {
	background-color: rgb(214,227,243);
}
</style>
</head>

<body style="overflow-y:hidden;overflow-x:hidden;" >
<odin:base>
<odin:hidden property="a0000"/>
<odin:hidden property="type" value="kccl"/>
<odin:hidden property="downfile" />
<form></form>
<form name="kcclForm" id="kcclForm" method="post"  action="<%=request.getContextPath()%>/servlet/UpLoadGBRSServlet" enctype="multipart/form-data" target="impFrame">	
<table align="center" width="50%">
	<tr>
		<td height="40"></td>
	</tr>
	<tr>
			<odin:textEdit inputType="file" property="kcclFile"  name="kcclFile" label="&nbsp&nbsp&nbsp考察材料" size="47" ></odin:textEdit>
			
		<td id="td1" align="center" colspan="2" style="position: relative;"> 
			<odin:button text="&nbsp;&nbsp;上&nbsp;&nbsp;传&nbsp;&nbsp;" property="impBtn1" handler="formSubmit1"></odin:button>
		 </td> 
		 <td id="td1_1" align="center" colspan="2" style="position: relative;"> 
			<odin:button text="&nbsp;&nbsp;查看记录&nbsp;&nbsp;" property="serachBtn1" handler="search1"></odin:button>
		 </td>
	</tr>
</table>
</form>

<form name="daForm" id="daForm" method="post"  action="<%=request.getContextPath()%>/servlet/UpLoadGBRSServlet" enctype="multipart/form-data" target="impFrame">	
<table align="center" width="50%">
	<tr>
		<td height="40"></td>
	</tr>
	<tr >
			<odin:textEdit inputType="file" property="daFile"  name="daFile" label="&nbsp&nbsp&nbsp档案专审表" size="47" ></odin:textEdit>
		<td id="td2" align="center" colspan="2" style="position: relative;"> 
			<odin:button text="&nbsp;&nbsp;上&nbsp;&nbsp;传&nbsp;&nbsp;" property="impBtn2" handler="formSubmit2"></odin:button>
		 </td> 
	<td id="td2_1" align="center" colspan="2" style="position: relative;"> 
			<odin:button text="&nbsp;&nbsp;查看记录&nbsp;&nbsp;" property="serachBtn2" handler="search2"></odin:button>
		 </td>
	</tr>
</table>
</form>


<form name="ndkhForm" id="ndkhForm" method="post"  action="<%=request.getContextPath()%>/servlet/UpLoadGBRSServlet" enctype="multipart/form-data" target="impFrame">	
<table align="center" width="50%">
	<tr>
		<td height="40"></td>
	</tr>
	<tr >
			<odin:textEdit inputType="file" property="ndkhFile"  name="ndkhFile" label="年度考核专审表" size="47" ></odin:textEdit>
	
		<td id="td3" align="left" style="position: relative;"> 
			<odin:button text="&nbsp;&nbsp;上&nbsp;&nbsp;传&nbsp;&nbsp;" property="impBtn3" handler="formSubmit3"></odin:button>
		 </td> 
		 <td id="td3_1" align="center" colspan="2" style="position: relative;"> 
			<odin:button text="&nbsp;&nbsp;查看记录&nbsp;&nbsp;" property="serachBtn3" handler="search3"></odin:button>
		 </td>
	</tr>
</table>
</form>
<odin:editgrid2 property="gridMain" title="考察材料信息集" height="360" isFirstLoadData="false"
		clicksToEdit="false" bbarId="pageToolBar" autoFill="true" pageSize="20">
		<odin:gridJsonDataModel>
			<odin:gridDataCol name="fileid" />
			<odin:gridDataCol name="username" />
			<odin:gridDataCol name="filename" />
			<odin:gridDataCol name="createon" />
			<odin:gridDataCol name="delete" />
			<odin:gridDataCol name="comm" isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn />
			<odin:gridEditColumn2 dataIndex="fileid" header="ID" menuDisabled="true"
				edited="false" editor="text" align="center" hidden="true" />
			<odin:gridEditColumn2 dataIndex="username" header="操作人" menuDisabled="true"
				edited="false" editor="text" align="center"  />
			<odin:gridEditColumn2 dataIndex="filename" header="文件名" menuDisabled="true"
				edited="false" editor="text" align="center"  renderer="filenameRenderer"/>
			<odin:gridEditColumn2 dataIndex="createon" header="上传时间" menuDisabled="true"
				edited="false" editor="text" align="center" />
				<odin:gridEditColumn2 dataIndex="createon" header="操作" menuDisabled="true"
				edited="false" editor="text" align="center" renderer="deleteRowRenderer"/>
				<odin:gridEditColumn2 dataIndex="createon" header="下载" menuDisabled="true"
				edited="false" editor="text" align="center" renderer="showRenderer" isLast="true"  />
		</odin:gridColumnModel>
		<odin:gridJsonData>
			{
		        data:[]
		    }
		</odin:gridJsonData>
	</odin:editgrid2>

<script>
Ext.onReady(function(){  
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
}); 

function search1(){
	document.getElementById('type').value='kccl';
	radow.doEvent("gridMain.dogridquery");
	document.getElementById('ext-gen35').innerHTML='考察材料信息集';
}
function search2(){
	document.getElementById('type').value='dazs';
	radow.doEvent("gridMain.dogridquery");
	document.getElementById('ext-gen35').innerHTML='档案专审表信息集';
}
function search3(){
	document.getElementById('type').value='ndkh';
	radow.doEvent("gridMain.dogridquery");
	document.getElementById('ext-gen35').innerHTML='年度考核专审表信息集';
}
function formSubmit1(){
	var File =document.getElementById('kcclFile').value;
	model(File,1);
	var temp=document.getElementById('kcclFile')
	temp.outerHTML = temp.outerHTML;
	document.getElementById('type').value='kccl';
	radow.doEvent("gridMain.dogridquery");
	document.getElementById('ext-gen35').innerHTML='考察材料信息集';
}
function formSubmit2(){
	var File =document.getElementById('daFile').value;
	model(File,2);
	var temp=document.getElementById('daFile')
	temp.outerHTML = temp.outerHTML;
	document.getElementById('type').value='dazs';
	radow.doEvent("gridMain.dogridquery");
	document.getElementById('ext-gen35').innerHTML='档案专审表信息集';
}
function formSubmit3(){
	var File =document.getElementById('ndkhFile').value;
	model(File,3);
	var temp=document.getElementById('ndkhFile')
	temp.outerHTML = temp.outerHTML;
	document.getElementById('type').value='ndkh';
	radow.doEvent("gridMain.dogridquery");
	document.getElementById('ext-gen35').innerHTML='年度考核专审表信息集';
}

function model(File,dateFlag){
	var pdfOrword=File;
	var formDate;
	var temp;
	var uploadURL;
	var a0000=document.getElementById('a0000').value;
	if(pdfOrword.length<1){
		odin.alert("请选择导入数据");
		return;
	}
	if(pdfOrword.length>1){
		if(dateFlag=='1'){
			temp='考察材料';
			formDate='kcclForm';
			uploadURL="<%=request.getContextPath()%>/servlet/UpLoadGBRSServlet?logo=kccl&MODULETYPE=GBRS&a0000="+a0000;
		}
		else if(dateFlag=='2'){
			temp='档案专审表';
			formDate='daForm';
			uploadURL="<%=request.getContextPath()%>/servlet/UpLoadGBRSServlet?logo=dazs&MODULETYPE=GBRS&a0000="+a0000;
		}
		else if(dateFlag=='3'){
			temp='年度考核专审表';
			formDate='ndkhForm';
			uploadURL="<%=request.getContextPath()%>/servlet/UpLoadGBRSServlet?logo=ndkh&MODULETYPE=GBRS&a0000="+a0000;
		}
		pdfOrword=pdfOrword.substr(pdfOrword.length-4,pdfOrword.length);
		if(pdfOrword=='.pdf'||pdfOrword=='.doc'||pdfOrword=='docx'){
		}else {
			odin.alert(temp+"仅支持pdf或word文件格式导入！");
			return;
		}
	}
//	Ext.Msg.wait("正在导入，请稍后...","系统提示");
	if(File!=""){
		debugger;
			Ext.Ajax.request({  
	            url : uploadURL,  
	            isUpload : true,  
	            form : formDate,  
	            success : function(response) {  
	                eval(response.responseText);  
	            }  
	        });
		}
	
}
function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var fileid = record.data.fileid; 
	return "<a href=\"javascript:deleteRow2(&quot;"+fileid+"&quot;)\">删除</a>";
}
function deleteRow2(fileid){ 
	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',fileid);
		}else{
			return;
		}		
	});	
}
function showRenderer(value, params, record,rowIndex,colIndex,ds){
	var fileid = record.data.fileid;
	return "<a href=\"javascript:printView(&quot;"+fileid+"&quot;)\">下载</a>";
}
function filenameRenderer(value, params, record,rowIndex,colIndex,ds){
	var filename = record.data.filename;
	filename=filename.substring(37);
	return "<span title='"+filename+"'>"+filename+"</span>";
}
function printView(fileid){
	radow.doEvent("downBtn",fileid);
}
function reloadTree(){
	setTimeout(xx,1000);
}
function xx(){
	var downfile = document.getElementById('downfile').value;
	/* w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile))); */
	window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
	ShowCellCover("","温馨提示","下载成功！");
	setTimeout(cc,3000);
}
</script>
</odin:base>
<odin:response onSuccess="doSuccess"/>

</body>
</html>