<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<html>
<head>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script src="<sicp3:rewrite forward='md5'/>"> </script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<script type="text/javascript"src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>

<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>导入文件</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
<style type="text/css">
#tablef{width:430px;position:relative;left:8px;}
</style>
</head>
<%-- <%@include file="/comOpenWinInit.jsp" %> --%>
<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>
<form name="excelForm" id="excelForm" method="post" action="<%=request.getContextPath()%>/TagFileServlet?method=addKcclfjFile" enctype="multipart/form-data" >	
<odin:hidden  property="id" />
<table id="tablef"  >
	<tr>
		<odin:textEdit width="720" inputType="file" colspan="4"  property="excelFile" label="选择文件" ></odin:textEdit> 
	</tr>
	<tr>
		<odin:textEdit property="note" label="备注" width="720" maxlength="100"/>
	</tr>
	<tr>
		<td colspan="4" align="center">
			<odin:button text="&nbsp;&nbsp;新&nbsp;增&nbsp;&nbsp;" property="impBtn" handler="formSubmit" />
		</td>
	</tr>
</table>
	
</form>

<script>
function doCloseWin(){
	parent.odin.ext.getCmp('addWin').close();
}

function formSubmit(){
	var file = document.getElementById('excelFile').value;
	var a0000 = parent.document.getElementById('a0000').value;
	
	if(file !=""){
		var note = encodeURI(document.getElementById('note').value);
		odin.ext.Ajax.request({
			url: '<%=request.getContextPath()%>/TagFileServlet?method=addKcclfjFile&a0000='+a0000+'&note='+encodeURI(note),
			isUpload: true,
			method: 'post',
			fileUpload: true,
			form: 'excelForm',
			success: function(){
				/* parent.odin.alert("新增附件成功!"); */
				parent.updateRmbFresh();
				parent.odin.ext.getCmp('tagKcclfjGrid').store.reload();
			}
		});
	}else{
		odin.info('文件必须上传！');
	}
}

</script>
</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>