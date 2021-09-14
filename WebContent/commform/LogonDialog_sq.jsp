<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@page import="com.lbs.commons.GlobalNames"%>
<%@page import="com.insigma.odin.framework.util.commform.SysConst"%>
<%@page import="com.insigma.odin.framework.util.commform.ObjectUtil"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<script src="basejs/md5.js"> </script>
<% 
	String title=SysConst.getServerTitle(); 
	if(ObjectUtil.equals(title,"")){
		title="ȫ������Ա������Ϣϵͳ";
	}
%>
<title><%=title%><%=SysConst.getServerNumber()%></title>
<style type="text/css">
<!--
body{
 text-align: center;margin:0px; padding:0px;background-color: #FFFFFF; 
 font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif,����; background:url(img/login/bady.gif) repeat-x top;
}
.maintext{
color:#024c7e;font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif,����; 
}
#header{
 margin:0px auto;width:1003px; height:auto;
}
#header_top{ 
  float:left;width:1003px; height:167px; background:url(img/login/header.gif) repeat-x;
}
/*�����м䲿������*/
#center{
 margin:0px auto;width:1003px; height:auto;
}
#center_left{
 float:left;width:533px; height:282px; background:url(img/login/login_02.jpg) no-repeat;
}
#center_right{
 float:right;width:470px; height:282px; background:url(img/login/login_03.jpg) no-repeat;
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
width:1003px; height:127px; background:url(img/login/foot.jpg) repeat-x top; background-color:#D4F4FF;
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
<script language="JavaScript">
function page_init(){
	window.onresize = fitScreen;
	fitScreen();
	document.getElementById("username").focus();
	checkLogon(1); //����Ƿ��Ѿ���½
};


function submitLoginForm(){
  var username = document.logonForm.username.value;
  var passwd = document.logonForm.passwd.value;
  var verifycode = document.logonForm.verifycode.value;
  
  if (username ==null || username==""){
    alert('�������û���');
    document.logonForm.username.focus();
    return false;
  }
  if (verifycode ==null || verifycode==""){
    alert('��������֤��');
    document.logonForm.verifycode.focus();
    return false;
  }
  
  document.logonForm.username.value = username.toLowerCase(); 
  document.logonForm.passwd.value = hex_md5(passwd);
  if(checkLogon(2)==false){ //����Ƿ��Ѿ���½
  	return false;
  }
  return true;
};

function fitScreen(){
  var height = document.body.clientHeight;
  height = height - 576 ;
  if (height < 0 )
    height = 0;
  //alert(height);
  if (height>0){
    document.getElementById("bottom").style.height = height;
    document.getElementById("bottom").style.visibility = 'visible';
  }
  else{
    document.getElementById("bottom").style.height = 0;
    document.getElementById("bottom").style.visibility = 'hidden';
  };
};

function checkKey(e) 
{
  var event = e || window.event;// ��ȡevent����
  var obj = event.target || event.srcElement;// ��ȡ�¼�Դ
  var type = obj.type || obj.getAttribute('type');// ��ȡ�¼�Դ����
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
/** ȡ����һ��Ԫ�� */
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
		var ok=confirm("�ϴε�½δ\"�����˳�\"�������˳������µ�½��\n\nע��\"�����˳�\"����Ϊ������Ͻǵ��˳���ť�����˳���");
		if(ok){
			window.top.location.href = '<%=request.getContextPath()%>' + '/logoffAction.do';
		}else{
			if(flag==2){ //��½
				alert("�����˳��ϴε�½�����ܽ��б��ε�½��");
			}
		}
		if(flag==2){ //��½
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
	<!--�м� center-->
	<div id="center">
	<div id="center_left"></div>
	<div id="center_right">
	<div id="text">
	<form name="logonForm"
		action="<%=request.getContextPath()%>/logonAction.do" method="post" onsubmit="return submitLoginForm()">
	
	<table border="0" align="left" class="maintext">
	<tr>
		<td >
		�û�����
		</td>
		<td>
		<input name="username" id="username" type="text" class="btn" tabindex="1">
		</td>
	</tr>
	<tr>
		<td>
		��&nbsp;&nbsp;&nbsp;�룺
		</td>
		<td>
		<input name="passwd"  type="password" class="btn"  tabindex="2">
		</td>
	</tr>
	<tr>
		<td>
		��֤��:
		</td>
		<td>
		<input name="verifycode" type="text" size="8" tabindex="3">
		<img src="<%=request.getContextPath()%>/verifyCode" style="vertical-align: middle"/>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
		<div style="margin:8px 0px 0px 10px; width:200px; float:left;">
		  <input name="submitbtn" type="image" src="img/login/btn.gif" tabindex="4">
		</div>
		</td>
	</tr>
	</table>
	<!--
	<ul>
	<li>�û�����<input name="username" id="username" type="text" class="btn" tabindex="1"></li>
	<li>��&nbsp;&nbsp;&nbsp;�룺<input name="passwd"  type="password" class="btn"  tabindex="2"></li>
	<li>��֤�룺<input type="text" class="btn2"  tabindex="3" readonly="readonly"> <b>2345</b>
	</li>
	</ul>
	<div style="margin:8px 0px 0px 10px; width:200px; float:left;"><input name="" type="image" src="img/login/btn.gif" tabindex="4"></div>
	  -->
	
	</form>
	</div>
	</div>
	</div>
	<div id="foot">
	<div id="foot_bady">
	<span>����֧�֣��㽭���¶���������޹�˾�����ֹ�˾</span>
	</div>
	</div>
<div id="bottom" style="background:url(img/login/bottom.jpg) repeat;"></div>
</body>
<sicp3:errors />
</html>