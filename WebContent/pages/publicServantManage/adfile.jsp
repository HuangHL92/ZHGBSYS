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
			<td  colspan="2"><span style="font-size: 12">&nbsp&nbsp&nbsp</span></td>	
			<odin:textEdit property="abc" inputType="file" label="材料上传" size="30" required="true"></odin:textEdit> 
			</tr>
		</table>
					
				
	</div>	
	</form>
</body>
<script type="text/javascript">
function imp(sort,a0000,time){
	var file=document.getElementById('abc').value;
	if(file !=""){
		var path = '<%=request.getContextPath()%>/YearCheckServlet?method=Impfile&sort='+sort+'&a0000='+a0000+'&time='+time;
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
							alert("材料上传成功");
							parent.window.restore();						    
						}else{
							alert("附件上传失败");
						}
							
					},
				failure : function(){
					alert("上传附件失败");
				}
			});
	}else{
		alert("请选择一个文件！");
		/* parent.radow.doEvent("appendfile"); */
		//parent.parentgridquery();
	}
}	


</script>