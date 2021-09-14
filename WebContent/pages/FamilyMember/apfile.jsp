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
		<table style="margin-left: -80px">
			<tr>
			<odin:textEdit property="impfile" inputType="file" label="&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp附件上传" size="30"></odin:textEdit> 
			</tr>
		</table>
					
				
	</div>	
	</form>
</body>
<script type="text/javascript">
function imp(oid){
	var file=document.getElementById('impfile').value;
	var index1=file.lastIndexOf(".");
	var index2=file.length;
	var suffix=file.substring(index1+1,index2);
	
	
	//alert(file)
	if(file ==""||suffix=="pdf"){
		var path = '<%=request.getContextPath()%>/addFamServlet?method=upload&a0000='+oid;
		path=encodeURI(encodeURI(path));
		//alert(path)
		odin.ext.Ajax.request({
			url:path,
			isUpload:true,
			method:'get',
			fileUpload:true,
			form:'data',
			success:function(data){
					var result = data.responseText;
					if(result=="success"){
						alert("提交成功");
						parent.clearFile();
					}else{
						alert("附件上传失败");
					}
						
				},
			failure : function(){
				alert("上传附件失败");
			}
		});
	}else if(suffix!="pdf"&&file !=""){
		alert("请上传pdf文件");
	}
}	

function clear(){

	var file=document.getElementById('impfile');
	//alert(file);
	file.value='';
}


</script>