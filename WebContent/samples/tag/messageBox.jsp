<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<html>
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Insert title here</title>
<odin:head/>
</head>
<body>         
<script type="text/javascript">
showResult = function(btn){     
	alert(btn);           
}
showResultText=function(btn,txt){
   alert(btn+"||"+txt);
}
doClick = function(){
   var obj = odin.confirm('123',showResult);
}
</script>
<h3>确定对话框示例</h3>
请点击查看示例<input type="button" value="confirm"  onclick="doClick()">
<h3>prompt对话框示例</h3>
请点击查看示例<input type="button" value="prompt"  onclick="odin.prompt('123',showResultText);">
<h3>prompt多行对话框示例</h3>
请点击查看示例<input type="button" value="promptWithMul"  onclick="odin.promptWithMul('123',showResultText,'system');">
<h3>alert对话框示例</h3>
请点击查看示例<input type="button" value="alert"  onclick="odin.alert('123',showResult);">
<h3>error对话框示例</h3>
请点击查看示例<input type="button" value="error"  onclick="odin.error('123',showResult,'错误');">
<h3>info对话框示例</h3>
请点击查看示例<input type="button" value="info"  onclick="odin.info('123',showResult);">


<h3>alert对话框示例</h3>
请点击查看示例<input type="button" value="alert"  onclick="doconfirm()">
<script>
function doconfirm(){
    odin.alert('123',showResult1);
    //alert(1);
}
function showResult1(){
    alert(1);
}
</script>
</body>
</html>