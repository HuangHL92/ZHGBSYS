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
			<odin:textEdit property="abc" inputType="file" label="&nbsp&nbsp附件上传" size="30"></odin:textEdit> 
			</tr>
		</table>
					
				
	</div>	
	</form>
</body>
<script type="text/javascript">
function imp(oid){
	var file=document.getElementById('abc').value;
	if(file !=""){
		var path = '<%=request.getContextPath()%>/YearCheckServlet?method=adsc1&oid='+oid;
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
					}/* else{
						alert("附件上传失败");
					} */
						
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