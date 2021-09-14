<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.odin.framework.util.commform.SysConst"%>
<html>
<head>
<odin:head></odin:head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<script src="<sicp3:rewrite forward='md5'/>"> </script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/signature.js"> </script>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>首次登陆--用户注册<%=SysConst.getServerNumber()%></title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/basejs/ext/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/login.css">

</head>

<body onload="javascript:page_init();">
<div id="head">
<div id="top_pic"></div>
</div>
<!--second-->
<div id="second">
<div id="second_left"></div>
<div id="second_center">
<div id="login">
<form name="logonForm"
	action="<%=request.getContextPath()%>/logonAction.do" method="post">
<dl class=form_list_intro>
	<dd onMouseOver="this.className='li_color';"
		onMouseOut="this.className='';">个人编号：<input type="text"
		name="grbh" style="width: 140px" tabindex="1"></dd>
	<dd onMouseOver="this.className='li_color';"
		onMouseOut="this.className='';">身份证号：<input type="text"
		name="sfz" style="width: 140px" tabindex="1"></dd>
	<dd onMouseOver="this.className='li_color';"
		onMouseOut="this.className='';">验&nbsp;证&nbsp;码&nbsp;：<input
		type="text" name="verifycode" style="width: 95px" tabindex="3"><span><img
		src="verifyCode"></span></dd>
	<dd onMouseOver="this.className='li_color';"
		onMouseOut="this.className='';"><odin:hidden property="username" />
	<odin:hidden property="passwd" /> <odin:hidden property="pkcs7" />
</dl>

<dd class=form_button><INPUT type=button onclick="check()"
	tabindex="4" class=inp_L2 value=""
	onMouseOver="this.className='inp_L1';"
	onMouseOut="this.className='inp_L2';"> <input type="hidden"
	name="macAddr" />
</form>
</div>
</div>
<div id="second_right"></div>
<!--foot-->
<div id="foot">
<div id="foot_pic">
<div class="foot_text"></div>
</div>
<div id="foot_center"></div>
</div>
</div>

<script language="JavaScript">
function page_init(){
	document.getElementById('grbh').select();
	document.getElementById("grbh").focus();
}

function checkKey(){
  if(13 == window.event.keyCode){
    if(document.activeElement.name == "verifycode"){
    	check();
    }else{
    	window.event.keyCode = 9;
    }
  }
}
document.onkeydown=checkKey;

//注册验证
function check(){
	if(document.getElementById('grbh').value==""){
		alert('个人编号不能为空!');
		document.getElementById('grbh').focus();
		return;
	}else if(document.getElementById('sfz').value==""){
		alert('身份证号不能为空!');
		document.getElementById('sfz').focus();
		return;
	}else if(document.getElementById('verifycode').value==""){
		alert('验证码不能为空!');
		document.getElementById('verifycode').focus();
		return;
	}
	var inParams = {};
	inParams.grbh = document.getElementById('grbh').value;
	inParams.sfz = document.getElementById('sfz').value;
	inParams.verifycode = document.getElementById('verifycode').value;
	doRegister(inParams); 
}

function doRegister(inParams){
	var url = "<%=request.getContextPath()%>"+"/register/RegisterServlet"; 
	Ext.Ajax.timeout=600000;
	return Ext.Ajax.request({
				url: url,
				params: {inParams:Ext.encode(inParams)},
				success: doSuccess,
				asynchronous: false
			});	
}
function doSuccess(request){
	var ret = request.responseText;
	var retCode = ret.substring(0,1);
	var retMsg = ret.substring(1);
	if(retCode=="1"){
		odin.info(retMsg,function(){window.top.location.href = '<%=request.getContextPath()%>' + '/LogonDialog.jsp'});
	}else{
		odin.error(retMsg);
	}
}

</script>

</body>
</html>
