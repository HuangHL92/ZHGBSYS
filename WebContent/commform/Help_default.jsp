<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>帮助</title>
</head>
<body>
	<% String helpurl="http://172.16.32.142:9902/pages/viewpage.action?pageId=9175775"; %>
	<div id="helptext" style="">
		<center>
		<font>请使用<b>用户名：sicphelp，密码：sicphelp</b>来进行登录并查看帮助<br></font>
		<font><span id="time"></span>秒后自动进入帮助系统，<a href="javascript:clearGotoHelpurl()">取消自动进入</a>，<a href="javascript:gotoUrl()">直接点击进入</a></font>
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

