<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>


<%@page import="net.sf.json.JSONArray"%>
<html>
<head>
<script type="text/javascript"src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<script src="<sicp3:rewrite forward='md5'/>"> </script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<link rel="stylesheet" type="text/css" href="/hzb/css/odin.css"/>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<style type="text/css">
 #daoru{position:relative;left:20px;width:290} 
</style>
<title>导入文件</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">
<%@include file="/comOpenWinInit.jsp" %>
<odin:base>

<form name="excelForm" id="excelForm" method="post"  action="<%=request.getContextPath()%>/UploadQueryServlet?method=zzbFile" enctype="multipart/form-data" >	
<odin:hidden  property="filename" value=""/>
<odin:groupBox title="数据导入" property="daoru">
<table>
<tr>
	<td>
	<odin:textEdit width="200" inputType="file" colspan="3"  property="excelFile" label="选择文件:" onchange="return FileUpload_onselect()" ></odin:textEdit> 
	</td>
	<%-- <td align="center" valign="middle">
	<odin:button text="上传" property="impwBtn" handler="formupload" /> 
	<img src="<%=request.getContextPath()%>/images/go1.jpg" onclick="formupload()" id="impwBtn">
	</td> --%>
</tr>
	
</table>	
</odin:groupBox>
	
	<input type="hidden" name="businessClass" value="<%=(request.getParameter("businessClass")==null?"":request.getParameter("businessClass"))%>"/>
	<input type="hidden" name="businessParam" value="<%=(request.getParameter("businessParam")==null?"":request.getParameter("businessParam"))%>"/>
	
</form>

<script>
/** 首次载入列表数据开始 */
function formSubmit2(){
	Ext.Ajax.request({
		url:'<%=request.getContextPath()%>/UploadQueryServlet?method=zzbFileImp',
		form : "excelForm",
		success:function(value){
			if(value.responseText=='ss'){
				alert("导入失败，该自定义方案已存在！");
				parent.doRenewal();
			}else{
				alert("导入成功！");
				parent.closeWin();
			}
			
		}
	});
}
function FileUpload_onselect(){   
    var path=document.getElementById('excelFile').value;
	document.getElementById('filename').value=path;
    path = path.substring(path.length-3,path.length);
    if(path!='xml'){
    	alert("文件格式不符合！");
    	parent.location.reload();
		return;
    }
}
</script>
</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>