<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.lbs.commons.GlobalNames"%>
<%@page import="com.insigma.odin.framework.util.commform.SysConst"%>
<html >
<head>
<odin:head></odin:head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<script src="<sicp3:rewrite forward='md5'/>"> </script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>��ᱣ�������걨ϵͳ----- �û���¼���<%=SysConst.getServerNumber()%></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ext/resources/css/ext-all.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/login.css">
</head>
<%
 Boolean isNeedSign=SysConst.getServerISNeedSign();
%> 
<script type="text/javascript">
var isNeedSign=<%=isNeedSign%>;
</script>
<body onload="javascript:page_init();">
<div id="head">
<div id="top_pic"></div>
</div>
<!--second-->
<div id="second">
<div id="second_left"></div>
<div id="second_center">
<div id="login">
<form name="logonForm" action="<%=request.getContextPath()%>/logonAction.do" method="post">
<dl class=form_list_intro>
   <dd onMouseOver="this.className='li_color';" onMouseOut="this.className='';">
    �û�����<input type="text" name="username" style="width:140px" tabindex="1"></dd>
   <dd onMouseOver="this.className='li_color';" onMouseOut="this.className='';">
    ��&nbsp;&nbsp; �룺<input type="password" name="passwd"  style="width:140px" tabindex="2"></dd>
   <dd onMouseOver="this.className='li_color';" onMouseOut="this.className='';">
    ��֤�룺<input type="text" name="verifycode" style="width:95px"  tabindex="3"><span><img src="verifyCode"></span></dd>
   <dd><input type="checkbox" style="margin-left:60px;" name="isRemberUserName" value="checkbox" checked="checked">��ס�û���</dd>	
</dl> 

<dd class=form_button><INPUT type=button onclick="onLogin()"  tabindex="4" class=inp_L2 value=""onMouseOver="this.className='inp_L1';" onMouseOut="this.className='inp_L2';" >
<INPUT   type="button"   tabindex="4" class=inp_L4 value=""onMouseOver="this.className='inp_L3';" onMouseOut="this.className='inp_L4';"  onClick="window.open('loginhelp.jsp','_blank')"></dd> 
<!-- dl ><dd style="margin-left:-60px;" ><font size="2px" >�״ε�¼������ </font></dd><dd></dd></dl>	 -->  

<input type="hidden" name="macAddr" />
</form>
</div>
</div>
<div id="second_right"></div>
<!--foot-->
<div id="foot">
<div id="foot_pic">
<div  class="foot_text"></div>
</div>
<div id="foot_center"></div>
</div>
</div>
<script language="JavaScript">
var cookieObj = new odin.ext.state.CookieProvider();
function page_init(){

		

	if(cookieObj == null){ //ǿ��ˢ��ʱ���ʼ��������ʱ�����¼���
		window.location.reload();
		return;
	}
    document.getElementById("username").value = cookieObj.get('username','');
    document.getElementById('username').select();
	document.getElementById("username").focus();
	if(isNeedSign){
		window.top.location.href = '<%=request.getContextPath()%>' + '/sslLogonDialog.jsp';
	}
	checkLogon(1); //����Ƿ��Ѿ���¼
}

function onLogin() {
  if(document.logonForm.username.value == ""){
      alert('�û�������Ϊ�գ�');
      document.logonForm.username.focus();
      return;
  }else if(document.logonForm.verifycode.value == ""){
      alert('У���벻��Ϊ��!');
      document.logonForm.verifycode.focus();
      return;
  }
  document.logonForm.passwd.value = hex_md5(document.logonForm.passwd.value);
  if(document.logonForm.isRemberUserName.checked==true){
      cookieObj.set('username',document.logonForm.username.value);
  }else{
      cookieObj.clear('username');
  }
  if(checkLogon(2)==false){ //����Ƿ��Ѿ���¼
  	return;
  }
  Ext.get(document.body).mask("��¼��...", odin.msgCls); // ����loading��Ӱ
  document.logonForm.submit();
}
/*function checkKey()
{
  if(13 == window.event.keyCode){
    if(document.activeElement.name == "verifycode"){
    	onLogin();
    }else{
    	window.event.keyCode = 9;
    }
  }
}*/
function checkKey(e) 
{
  var event = e || window.event;// ��ȡevent����
  var obj = event.target || event.srcElement;// ��ȡ�¼�Դ
  var type = obj.type || obj.getAttribute('type');// ��ȡ�¼�Դ����
  var keyCode = event.keyCode || event.which || event.charCode;

  if(13 == keyCode){
    if(document.activeElement.name == "verifycode"){
    	onLogin();
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
document.onkeydown=checkKey;
function checkLogon(flag){
	var currentUser="<%=request.getSession().getAttribute(GlobalNames.CURRENT_USER)%>";
	if(currentUser!="null"){
		var ok=confirm("�ϴε�¼δ\"�����˳�\"�������˳������µ�¼��\n\nע��\"�����˳�\"����Ϊ������Ͻǵ��˳���ť�����˳���");
		if(ok){
			window.top.location.href = '<%=request.getContextPath()%>' + '/logoffAction.do';
		}else{
			if(flag==2){ //��¼
				alert("�����˳��ϴε�¼�����ܽ��б��ε�¼��");
			}
		}
		if(flag==2){ //��¼
			return false;
		}
	}
	return true;
}

</script>

<!--<object id="macObj" style="display:none" classid="CLSID:C006A268-DFB6-4681-BFE6-36A6564C43F5" 
 codebase="GetMac.ocx"
width="32" height="32" title="��ȡ������ַ�ؼ�">
</object>
<script>
document.getElementById('macAddr').value = document.getElementById('macObj').GetMac();
</script>
--></body>
<sicp3:errors />
</html>
