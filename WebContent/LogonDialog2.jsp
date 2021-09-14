<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.insigma.odin.framework.RSACoder"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@page import="com.insigma.odin.framework.safe.SafeControlCenter"%>
<%@page import="com.insigma.odin.framework.safe.util.SafeConst"%>
<%@page import="com.insigma.odin.framework.util.ExtGlobalNames"%>
<%@page import="com.insigma.odin.framework.util.SysUtil"%>
<%
	String usernamep = request.getParameter("u");
	String passwdp = request.getParameter("p");
	String privateKey = "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAlOtDa4oXXBvpJCa8yoKOGx3RZG9ZEr+euFiPmZncPRkcnaaoTKleHRdpzwauBPBsQXU0ooUnIpLtaXmMciShNQIDAQABAkAM5lg/v4WIgA5xCD4AWNDQfoO97HtNyXWJSHqf9pkCXcFXKoS59C4c0XRrGv0WAmHGAtAqufCnv47ODE/emC09AiEA4PKjfosEtFJcNNoIIAfXO/YeZBr/PUzzi5RKH7pLiDsCIQCped2x1FyN+AaA6mD8mtyuleYGnj7q+dLa1p980i5VTwIgaxVWm0DWhnjGiCpav9S7s0GgigsIAkiFj6aR+rSWjE0CIHoqeN7ZoCZOphGD4on08COBtqEKrXwgvhg2Ih2OPQwNAiBZwEw1/6MkvZn5Vtn68S0X8BuLq+5rcC4LrBxvcsk+0Q==";
	usernamep = usernamep.replace("%2B","+");
	byte[] decode4 = RSACoder.decryptByPrivateKey(
    		Base64.decodeBase64(usernamep.getBytes()),
    		Base64.decodeBase64(privateKey.getBytes()));
	String username = new String(decode4);
	
	passwdp = passwdp.replace("%2B","+");
	byte[] decode5 = RSACoder.decryptByPrivateKey(
    		Base64.decodeBase64(passwdp.getBytes()),
    		Base64.decodeBase64(privateKey.getBytes()));
	String passwd = new String(decode5);
	//request.getSession().setAttribute("LoginScene","sce:USERNAME;USER_PASS");
	if(SysUtil.isWorkpf(request)){
		out.println("<html>");
		out.println("<head></head>");
		out.println("<body>");
		out.println("<script language='JavaScript'>");
		out.println("qtweb.reLogin();");
		out.println("</script>");
		out.println("</body>");
		out.println("</html>");
	}else{
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="icon" href="<%=request.getContextPath()%>/image/logo_icon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/image/logo_icon.ico" type="image/x-icon" />
<meta content="IE=8" http-equiv="X-UA-Compatible" />
<title>干部大数据综合管理平台</title>
<!-- <link rel = "Shortcut Icon" href="icos/hzb.ico"> </link> -->
<script src="basejs/md5.js"> </script>
<script src="basejs/jquery/jquery-1.7.2.min.js"> </script>
<link href="css/all.css" type="text/css" rel="stylesheet"></link>
<link href="css/login.css" type="text/css" rel="stylesheet"></link>
<script type="text/javascript">
window.onload = function(){
 //document.getElementById('submit').focus();
}
$(function(){
  //$("#bodyDiv").css("filter","progid:DXImageTransform.Microsoft.AlphaImageLoader(src='image/login.png',sizingMethod='scale')");
  //判断浏览器是否支持placeholder属性
  supportPlaceholder='placeholder'in document.createElement('input'),
  placeholder=function(input){
    var text = input.attr('placeholder'),
    defaultValue = input.defaultValue;
    if(!defaultValue){
      input.val(text).addClass("phcolor");
    }
    input.focus(function(){
      if(input.val() == text){
        $(this).val("");
      }
    });
 
    input.blur(function(){
      if(input.val() == ""){
        $(this).val(text).addClass("phcolor");
      }
    });
    //输入的字符不为灰色
    input.keydown(function(){
      $(this).removeClass("phcolor");
    });
  };
  
  //当浏览器不支持placeholder属性时，调用placeholder函数
  if(!supportPlaceholder) {
    $('input').each(function(){
      text = $(this).attr("placeholder");
      if($(this).attr("type") == "text" || $(this).attr("type") == "password"){
        placeholder($(this));
      }
    });
  }
}); 
</script> 
</head>
<%
	SafeControlCenter.getInstance(SafeConst.PDT_INSIIS).safeValidate(request,SafeConst.VT_APPCONTEXT,SafeConst.PDT_INSIIS_COMP_ODIN);
	SafeControlCenter.getInstance().safeValidate(SafeConst.VT_LOGINCOUNT,SafeConst.PDT_INSIIS_COMP_ODIN);
	SafeControlCenter.getInstance().safeValidate(SafeConst.VT_APPSERVER,SafeConst.PDT_INSIIS_COMP_ODIN);
	SafeControlCenter.getInstance().safeValidate(SafeConst.VT_EXPIRATION,SafeConst.PDT_INSIIS_COMP_ODIN);
%>
<body id="bodyDiv" style="display: none;">
<div id="all" class="title_style" style="height:90%;min-height:450px;">
  <form id="loginForm" name="logonForm" action="<%=request.getContextPath()%>/logonAction.do" method="post" onsubmit="return submitLoginForm()" style="height:45%;width:100%;padding-top:18%;">
    <div>
        <table width="80%" align="center" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="78%" height="30">
              <div align="center">
                —— LOGIN SYSTEM ——
              </div>
             </td>
          </tr>
          <tr>
            <td width="78%" height="30"><div align="center">
                <input class="input11" id="username" name="username" type="text" />
              </div></td>
          </tr>
          <tr>
            <td height="30" width="78%">
              <div align="center">
                <input class="input11" id="passwd" type="password" name="passwd"/>
                <input type="hidden" name="params" id="params" value=""/>
              </div>
            </td>
          </tr>
          <tr>
            <td height="50" width="78%">
            	<div align="center">
            		<button name="submitbtn" type="submit" id="submit">登&nbsp;录&nbsp;系&nbsp;统</button>
            	</div>
            </td>
          </tr>
        </table>
     </div>
  </form>
</div>
<div id="foot" style="height:10%;display:table;">
  <div class="cell"><!-- 中共中央组织部&nbsp;&nbsp;人力资源和社会保障部&nbsp;&nbsp;国家公务员局&nbsp;&nbsp;联合研制&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->浙江网新恩普软件有限公司&nbsp;&nbsp;技术支持</div>
</div>
<sicp3:errors />
<script language="JavaScript">
$(function(){
	$('#username').val('<%=username%>');
	$('#passwd').val('<%=passwd%>');
	$('#submit').click();
});
function page_init(){
	autoHeight()
	$(window).resize(function(){autoHeight()});
	//fitScreen();
	//document.all("username").focus();
};

function autoHeight() {
	var totalHeight = document.documentElement.clientHeight;
	if(totalHeight < 500) {
		$("#loginForm").css("paddingTop","20%");
	} else if(totalHeight >= 500 && totalHeight <= 600) {
		$("#loginForm").css("paddingTop","20%");
	} else if(totalHeight > 600 && totalHeight <= 800) {
		$("#loginForm").css("paddingTop","20%");
	} else if(totalHeight > 800 && totalHeight < 1024) {
		$("#loginForm").css("paddingTop","22%");
	} else if(totalHeight >= 1024) {
		$("#loginForm").css("paddingTop","24%");
	}
}

function submitLoginForm(){
  var username = document.logonForm.username.value;
  var passwd = document.logonForm.passwd.value;
  if (username ==null || username==""){
    alert('请输入用户名');
    document.all("username").focus();
    return false;
  }
  document.logonForm.passwd.value = hex_md5(passwd);
  var params = document.getElementById("params");
  params.value = "{'username':'"+username+"','password':'"+document.logonForm.passwd.value+"','scene':''}";
  return true;
};

/*
function fitScreen(){
  var height = document.body.clientHeight;
  height = height - 576 ;
  if (height < 0 )
    height = 0;
  //alert(height);
  if (height>0){
    document.all.bottom.style.height = height;
    document.all.bottom.style.visibility = 'visible';
  }
  else{
    document.all.bottom.style.height = 0;
    document.all.bottom.style.visibility = 'hidden';
  };
};
*/
/*
function checkKey() 
{
  if(13 == window.event.keyCode){
    document.logonForm.submitbtn.click();
  }
};

document.onkeydown=checkKey;
*/
page_init();
</script>
</body>
</html>
<%
	}
%>