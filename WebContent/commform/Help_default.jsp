<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>����</title>
</head>
<body>
	<% String helpurl="http://172.16.32.142:9902/pages/viewpage.action?pageId=9175775"; %>
	<div id="helptext" style="">
		<center>
		<font>��ʹ��<b>�û�����sicphelp�����룺sicphelp</b>�����е�¼���鿴����<br></font>
		<font><span id="time"></span>����Զ��������ϵͳ��<a href="javascript:clearGotoHelpurl()">ȡ���Զ�����</a>��<a href="javascript:gotoUrl()">ֱ�ӵ������</a></font>
		</center>
	</div>
	<script type="text/javascript">
		var timer=3;
		var timeout=null;
		function gotoHelpurl(){
			document.getElementById("time").innerHTML=timer--;
			if(timer<0){
				gotoUrl();
			}else{
				timeout=setTimeout("gotoHelpurl()",1000);
			}
		}
		function gotoUrl(){
			window.location.href="<%=helpurl%>";
		}
		gotoHelpurl();
		function clearGotoHelpurl(){
			if(timeout!=null){
				clearTimeout(timeout);
			}
		}
	</script>
</body>
 <sicp3:errors/>
</html>

