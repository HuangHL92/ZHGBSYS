<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.lbs.leaf.util.SysUtil"%>
<%@page import="com.lbs.commons.GlobalNames"%>
<%@page import="com.insigma.siis.util.SysConst"%>
<html >
<head> 
<odin:head></odin:head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<script src="<sicp3:rewrite forward='md5'/>"> </script>  
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/signature.js"> </script>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>��ᱣ�������걨ϵͳ----- �û���¼���<%=SysConst.getServerNumber()%></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ext/resources/css/ext-all.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/login.css">
<OBJECT id="oCAPICOM" codeBase="capicom.cab#version=2,0,0,3"
	classid="clsid:A996E48C-D3DC-4244-89F7-AFA33EC60679"></OBJECT>
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
<form name="logonForm" action="<%=request.getContextPath()%>/logonAction.do" method="post">
<dl class=form_list_intro>
   <dd onMouseOver="this.className='li_color';" onMouseOut="this.className='';">
    ��֤���ţ�<input type="text" name="caid" style="width:140px" tabindex="1"></dd>
   <dd onMouseOver="this.className='li_color';" onMouseOut="this.className='';">
    ��֤�룺&nbsp;&nbsp;<input type="text" name="verifycode" style="width:95px"  tabindex="3"><span><img src="verifyCode"></span></dd>
   <dd onMouseOver="this.className='li_color';" onMouseOut="this.className='';">
   <dd><input type="checkbox" style="margin-left:60px;" name="isRemberCaid" value="checkbox" checked="checked">��ס����</dd>
   <odin:hidden property="username"/>	
   <odin:hidden property="passwd"/>	
   <odin:hidden property="pkcs7"/> 
</dl> 

<dd class=form_button><INPUT type=button onclick="check()"  tabindex="4" class=inp_L2 value=""onMouseOver="this.className='inp_L1';" onMouseOut="this.className='inp_L2';" >   

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
	document.all("caid").value = cookieObj.get('caid','');
    document.all('caid').select();
	document.all("caid").focus();
	checkLogon(1); //����Ƿ��Ѿ���¼
}

function onLogin() {
  if(checkLogon(2)==false){ //����Ƿ��Ѿ���¼
  	return;
  }
  if(document.all.username.value==null || document.all.username.value==''){
  	alert("����Ŀ���������");
    return;
  }
  if(document.logonForm.isRemberCaid.checked==true){
      cookieObj.set('caid',document.logonForm.caid.value);
  }else{
      cookieObj.clear('caid');
  }
  Ext.get(document.body).mask("��¼��...", odin.msgCls); // ����loading��Ӱ
  document.logonForm.submit();
}
function checkKey()
{
  if(13 == window.event.keyCode){
    if(document.activeElement.name == "verifycode"){
    	
    	check();
    }else{
    	window.event.keyCode = 9;
    }
  }
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
		//window.top.location.href = '<%=request.getContextPath()%>' + '/sslLogonDialog.jsp';
	}
	return true;
}
function caManage(oldData,pkcs7){
	var url = "<%=request.getContextPath()%>"+"/auth/CaManageServlet";
	Ext.Ajax.timeout=600000;
	return Ext.Ajax.request({
				url: url,
				params:{oldData:odin.encode(oldData)},
				success: Success
			});	
}
function getRn1(sync){
	var url = "<%=request.getContextPath()%>"+"/auth/RN1CodeServlet"; 
	Ext.Ajax.timeout=600000;
	return Ext.Ajax.request({
				url: url,
				success: doSuccess,
				asynchronous: sync
			});	
}
function doSuccess(request){
	var username=document.all("caid").value;
	var rn1 = request.responseText;
	var oldData = "{'userinput':'"+username+"','RN1':'"+rn1+"'}";
	var pkcs7,cert;
	  try{
	  	cert = signature.openSelect();
		var signed = signature.sign(cert,oldData);
		document.all('pkcs7').value=signed;
		pkcs7=signed;
	  }catch(e){
	  	alert(e);
	  	return;
	  }
	  var  data = {};
	  data.userinput=username;
	  data.RN1=rn1;
	  data.pkcs7=pkcs7;
	  caManage(data,pkcs7);
}
//��ȡ�����
function check(){
  if(document.all.caid.value==""){
  	  alert('��֤���Ų���Ϊ��!');
	  document.logonForm.caid.focus();
      return;
	}else if(document.all.verifycode.value == ""){
      alert('У���벻��Ϊ��!');
      document.all.logonForm.focus();
      return;
  }
	
	getRn1(false); 

}
//��¼
function Success(request){
	var param=request.responseText;
	document.logonForm.username.value=param.substring(0,param.indexOf(","));
	document.logonForm.passwd.value=param.substring(param.indexOf(",")+1);
	onLogin();
}
</script>

</body>
</html>
