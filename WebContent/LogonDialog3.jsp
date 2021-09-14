<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@page import="com.insigma.odin.framework.safe.SafeControlCenter"%>
<%@page import="com.insigma.odin.framework.safe.util.SafeConst"%>
<%@page import="com.insigma.odin.framework.util.ExtGlobalNames"%>
<%@page import="com.insigma.odin.framework.util.SysUtil"%>
<%
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
<link rel="icon" href="<%=request.getContextPath()%>/image/logo_wx.png" type="image/x-icon" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/image/logo_wx.png" type="image/x-icon" />
<meta content="IE=8" http-equiv="X-UA-Compatible" />
<title>�ɲ�����</title>
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
  //�ж�������Ƿ�֧��placeholder����
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
    //������ַ���Ϊ��ɫ
    input.keydown(function(){
      $(this).removeClass("phcolor");
    });
  };
  
  //���������֧��placeholder����ʱ������placeholder����
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
<body id="bodyDiv">
<div id="all" class="title_style" style="height:90%;min-height:450px;">
  <form id="loginForm" name="logonForm" action="<%=request.getContextPath()%>/logonAction.do" method="post" onsubmit="return submitLoginForm()" style="height:45%;width:100%;padding-top:18%;">
    <div>
        <table width="80%" align="center" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="78%" height="30">
              <div align="center">
                ���� LOGIN SYSTEM ����
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
            		<button name="submitbtn" type="submit" id="submit">��&nbsp;¼&nbsp;ϵ&nbsp;ͳ</button>
            	</div>
            </td>
          </tr>
        </table>
     </div>
  </form>
</div>
<div id="foot" style="height:10%;display:table;">
  <div class="cell"><!-- �й�������֯��&nbsp;&nbsp;������Դ����ᱣ�ϲ�&nbsp;&nbsp;���ҹ���Ա��&nbsp;&nbsp;��������&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->�㽭���¶���������޹�˾&nbsp;&nbsp;����֧��</div>
</div>
<sicp3:errors />
<script language="JavaScript">
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
    alert('�������û���');
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