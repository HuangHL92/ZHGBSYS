<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<head>

<odin:head />
<script src="<%=request.getContextPath()%>/radow/corejs/radow.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.PageModeEngine.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.util.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.renderer.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.business.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
</head>
<body>
<form name="data" id="data" method="post"  enctype="multipart/form-data" >
	<div id="groupbox">
		<table>
			<tr>
			<odin:textEdit property="abc" inputType="file" label="&nbsp&nbsp历史沿革" size="30"></odin:textEdit> 
			<td align="left" id="fileList">
			</td>
			</tr>
			<tr>
		    <td height="2"></td>
	        </tr>
			<tr>
			<odin:textEdit property="sf" inputType="file" label="&nbsp&nbsp三定方案" size="30"></odin:textEdit> 
			<td align="left" id="fileList2">
			</td>
			</tr>
			<tr>
		    <td height="2"></td>
	        </tr>
			<tr>
			<odin:textEdit property="lf" inputType="file" label="&nbsp&nbsp历任领导" size="30"></odin:textEdit> 
			<td align="left" id="fileList3">
			</td>
			</tr>
		</table>									
	</div>	
	</form>
</body>
<script type="text/javascript">
function ht(htmlx,htmly,htmlz){
	//alert(htmlx);
	document.getElementById("fileList").innerHTML=htmlx;
	document.getElementById("fileList2").innerHTML=htmly;
	document.getElementById("fileList3").innerHTML=htmlz;
}

//文件下载
function download(id){
	//alert("111");
	
	//下载附件
	//encodeURI，用来做url转码，解决中文传输乱码问题 （后台接收的时候会再做转码处理，转回来）
	window.location="<%=request.getContextPath()%>/PublishFileServlet?method=downloadorgFile&id="+encodeURI(encodeURI(id))+"&table="+encodeURI(encodeURI("YearCheckFile"));
	
	//window.location="PublishFileServlet?method=downloadFile&filePath="+encodeURI(encodeURI(id));
}

function imp(xfoid,xfoid2,xfoid3){
	var file1=document.getElementById('abc').value;
	var file2=document.getElementById('sf').value;
	var file3=document.getElementById('lf').value;
	
	if(file1==""){
		xfoid="";
	} 
	if(file2==""){
		xfoid2="";
	}
	if(file3==""){
		xfoid3="";
	}
		
	if(file1 !=""||file2 !=""||file3 !=""){
		var path = '<%=request.getContextPath()%>/YearCheckServlet?method=historyfile&oid1='+xfoid+'&oid2='+xfoid2+'&oid3='+xfoid3;
		odin.ext.Ajax.request({
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


</script>