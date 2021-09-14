<%@include file="/commform/basejs/loading/loading.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@page import="com.lbs.commons.GlobalNames"%>
<%@page import="com.insigma.odin.framework.util.commform.SysConst"%>
<%@page import="com.insigma.odin.framework.util.commform.ObjectUtil"%>
<%
	request.getSession().setAttribute("LoginScene","sce:VERFYCODE;USER_PASS"); //根据pattern.xml进行配置
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<script src="<%=request.getContextPath()%>/commform/basejs/md5.js"> </script>

<% 
	String title=SysConst.getServerTitle(); 
	if(ObjectUtil.equals(title,"")){
		title="全国公务员管理信息系统";
	}
%>
<title><%=title%><%=SysConst.getServerNumber()%></title>
<style type="text/css">
<!--
body{
 text-align: center;margin:0px; padding:0px;
 font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif,宋体; background:url(<%=request.getContextPath()%>/commform/img/login/bady.gif) repeat-x top;
 background-color: #D4F4FF; 
}
.maintext{
color:#024c7e;font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif,宋体; 
}
#header{
 margin:0px auto;width:1003px; height:auto;
}
#header_top{ 
  float:left;width:1003px; height:167px; background:url(<%=request.getContextPath()%>/commform/img/login/header.gif) repeat-x;
}
/*核心中间部分内容*/
#center{
 margin:0px auto;width:1003px; height:auto;
}
#center_left{
 float:left;width:533px; height:282px; background:url(<%=request.getContextPath()%>/commform/img/login/login_02.jpg) no-repeat;
}
#center_right{
 float:right;width:470px; height:282px; background:url(<%=request.getContextPath()%>/commform/img/login/login_03.jpg) no-repeat;
}
#center_right #text{
 float:left; padding:20px 0px 0px 5px; width:400px; height:200px; 
}
#center_right #text ul{
 float:left;list-style:none; margin:0px; padding:0px;
}
#center_right #text ul li{ 
 float:left; text-align:left; color:#024c7e; font-size:12px; width:300px; line-height:25px;margin:0px 0px 5px 0px;
}
#foot{
 margin:0px auto;width:1003px;
}
#foot_bady{
width:1003px; height:127px; background:url(<%=request.getContextPath()%>/commform/img/login/foot.jpg) repeat-x top; background-color:#D4F4FF;
}
#foot_bady span{ 
  font-size:12px; color:#024c7e; text-align:center;
}
.btn{ 
 width:170px; border:1px #B2B2B2 solid; height:22px; 
}
.btn2{ 
 width:130px; border:1px #B2B2B2 solid; height:22px; 
}
-->
</style>
<odin:commformhead/>
<script language="JavaScript">
try{
Ext.get(document.body).mask("", 'x-mask-loading');//无实际意义，仅解决chrome加载图标不显示的问题
Ext.get(document.body).unmask();
}catch(e){}
function page_init(){
	window.onresize = fitScreen;
	fitScreen();
	document.getElementById("username").focus();
	checkLogon(1); //检查是否已经登陆
};


function submitLoginForm(){
  var username = document.logonForm.username.value;
  var passwd = document.logonForm.passwd.value;
  var verifycode = document.logonForm.verifycode.value;
  
  if (username ==null || username==""){
    alert('请输入用户名');
    document.logonForm.username.focus();
    return false;
  }
  if (verifycode ==null || verifycode==""){
    alert('请输入验证码');
    document.logonForm.verifycode.focus();
    return false;
  }
  
  document.logonForm.username.value = username; 
  document.logonForm.passwd.value = hex_md5(passwd);
  var params = document.getElementById("params");
  params.value = "{'username':'"+username+"','password':'"+document.logonForm.passwd.value+"'}";
  if(checkLogon(2)==false){ //检查是否已经登陆
  	return false;
  }
  Ext.get(document.body).mask("登录中...", 'x-mask-loading'); // 加上loading阴影
  document.activeElement.blur();
  return true;
};

function fitScreen(){
  /*var height = document.body.clientHeight;
  height = height - 576 ;
  if (height < 0 )
    height = 0;
  //alert(height);
  if (height>0){
  	document.getElementById("bottom").style.top = "1000";
    document.getElementById("bottom").style.height = height;
    document.getElementById("bottom").style.visibility = 'visible';
  }
  else{
    document.getElementById("bottom").style.height = 0;
    document.getElementById("bottom").style.visibility = 'hidden';
  };*/
};

function checkKey(e) 
{
  var event = e || window.event;// 获取event对象
  var obj = event.target || event.srcElement;// 获取事件源
  var type = obj.type || obj.getAttribute('type');// 获取事件源类型
  var keyCode = event.keyCode || event.which || event.charCode;

  if(13 == keyCode){
    if(document.activeElement.name == "verifycode"){
    	
    }else{
    	if (!e) {
			event.keyCode = 9;
		} else {
			var el = getNextElement(obj);
			if (el) {
				if (el.type != ' hidden ') {
					el.focus();
				} else {
					while (el.type == ' hidden ') {
						el = getNextElement(el);
					}
					el.focus();
				}
			}
			event.preventDefault();
			event.stopPropagation();
			return;
		}
    }
    
  }
}
/** 取得下一个元素 */
function getNextElement(field) {
	var form = field.form;
	if (!form) {
		return;
	}
	for (var e = 0; e < form.elements.length; e++) {
		if (field == form.elements[e])
			break;
	}
	return form.elements[++e % form.elements.length];
}
function checkLogon(flag){
	var currentUser="<%=request.getSession().getAttribute(GlobalNames.CURRENT_USER)%>";
	if(currentUser!="null"){
		var ok=confirm("上次登陆未\"正常退出\"！现在退出并重新登陆？\n\n注：\"正常退出\"方法为点击右上角的退出按钮进行退出。");
		if(ok){
			window.top.location.href = '<%=request.getContextPath()%>' + '/logoffAction.do';
		}else{
			if(flag==2){ //登陆
				alert("必须退出上次登陆，才能进行本次登陆！");
			}
		}
		if(flag==2){ //登陆
			return false;
		}
	}
	return true;
}
document.onkeydown=checkKey;
</script>
</head>
<body onload="javascript:page_init();" >

	<div id="header">
	<div id="header_top"></div>
	</div>
	<!--中间 center-->
	<div id="center">
	<div id="center_left"></div>
	<div id="center_right">
	<div id="text">
	<form name="logonForm"
		action="<%=request.getContextPath()%>/logonAction.do" method="post" onsubmit="return submitLoginForm()">
	
	<table border="0" align="left" class="maintext">
	<tr>
		<td >
		用户名：
		</td>
		<td>
		<input name="username" id="username" type="text" class="btn" tabindex="1">
		</td>
	</tr>
	<tr>
		<td>
		密&nbsp;&nbsp;&nbsp;码：
		</td>
		<td>
		<input name="passwd"  type="password" class="btn"  tabindex="2">
		</td>
	</tr>
	<tr>
		<td>
		验证码:
		</td>
		<td>
		<input name="verifycode" type="text" size="8" tabindex="3">
		<img src="<%=request.getContextPath()%>/verifyCode" style="vertical-align: middle"/>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
		<div style="margin:8px 0px 0px 10px; width:200px; float:left;">
		  <input name="submitbtn" type="image" src="<%=request.getContextPath()%>/commform/img/login/btn.gif" tabindex="4">
		</div>
		</td>
	</tr>
	</table>
	<!--
	<ul>
	<li>用户名：<input name="username" id="username" type="text" class="btn" tabindex="1"></li>
	<li>密&nbsp;&nbsp;&nbsp;码：<input name="passwd"  type="password" class="btn"  tabindex="2"></li>
	<li>验证码：<input type="text" class="btn2"  tabindex="3" readonly="readonly"> <b>2345</b>
	</li>
	</ul>
	<div style="margin:8px 0px 0px 10px; width:200px; float:left;"><input name="" type="image" src="img/login/btn.gif" tabindex="4"></div>
	  -->
	<input type="hidden" name="params" id="params" value=""/>
	</form>
	</div>
	</div>
	</div>
	<div id="foot">
	<div id="foot_bady">
	<span>技术支持：浙江网新恩普软件有限公司宁波分公司</span>
	</div>
	</div>
</body>
<sicp3:errors />
</html>