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
<h3>ȷ���Ի���ʾ��</h3>
�����鿴ʾ��<input type="button" value="confirm"  onclick="doClick()">
<h3>prompt�Ի���ʾ��</h3>
�����鿴ʾ��<input type="button" value="prompt"  onclick="odin.prompt('123',showResultText);">
<h3>prompt���жԻ���ʾ��</h3>
�����鿴ʾ��<input type="button" value="promptWithMul"  onclick="odin.promptWithMul('123',showResultText,'system');">
<h3>alert�Ի���ʾ��</h3>
�����鿴ʾ��<input type="button" value="alert"  onclick="odin.alert('123',showResult);">
<h3>error�Ի���ʾ��</h3>
�����鿴ʾ��<input type="button" value="error"  onclick="odin.error('123',showResult,'����');">
<h3>info�Ի���ʾ��</h3>
�����鿴ʾ��<input type="button" value="info"  onclick="odin.info('123',showResult);">


<h3>alert�Ի���ʾ��</h3>
�����鿴ʾ��<input type="button" value="alert"  onclick="doconfirm()">
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