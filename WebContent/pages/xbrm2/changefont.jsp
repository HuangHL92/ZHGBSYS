<%@page import="com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page isELIgnored="false" %> 
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<title></title>
		<style>
			form {
				margin: 0;
			}
			textarea {
				display: block;
			}
		</style> 
		<script src="<%=request.getContextPath()%>/pages/xbrm2/js/jquery-1.4.4.min.js" type="text/javascript"></script>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/themes/default/default.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/plugins/code/prettify.css" />
		<script charset="utf-8" src="<%=request.getContextPath()%>/assets/kindeditor-all.js"></script>
		<script charset="utf-8" src="<%=request.getContextPath()%>/assets/lang/zh-CN.js"></script>
		<script charset="utf-8" src="<%=request.getContextPath()%>/assets/plugins/code/prettify.js"></script>
			
		<script>
			var editor;
			KindEditor.ready(function(K) {
				editor = K.create('textarea[name="content"]', {
					resizeType : 1,
					allowPreviewEmoticons : false,
					allowImageUpload : false,
					items : [
						'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
						'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
						'insertunorderedlist', '|', 'emoticons']
				});
			});
			
			function getHtml(){
				var text = editor.html();
				$(parent.objTD).html(text);
				//alert(parent.$("#yntype").attr("id")); 
				
				//text = editor.text();
				parent.closeChangFontWindow(text);
			}
			 
			$(document).ready(function () {  
				var html = $(parent.objTD).html();
				editor.html(html);
				//editor.html('<%= new String (request.getParameter("text").getBytes("ISO-8859-1"),"UTF-8")%>');
			});
		</script>
	</head>
	<body> 
	 
		<form>
			<textarea name="content" style="width:100%;height:120px;visibility:hidden;"></textarea>
		</form>
		<p align="center"><input type="button" value="ÉèÖÃ"  onclick="getHtml()"/></p>
	</body>
</html>
