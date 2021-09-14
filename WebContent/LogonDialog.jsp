<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%@page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
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
		
		request.getSession().setAttribute("noPwd","");
		
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" > 
<link rel="icon" href="<%=request.getContextPath()%>/image/logo-hz.png" type="image/x-icon" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/image/logo-hz.png" type="image/x-icon" />
<!-- <title>智慧党建综合大数据平台</title>-->
<title>干部综合管理系统</title>
<!-- <link rel = "Shortcut Icon" href="icos/hzb.ico"> </link> -->
<script src="basejs/md5.js"> </script>
<script src="basejs/jquery/jquery-1.7.2.min.js"> </script>
<!-- <link href="css/all.css" type="text/css" rel="stylesheet"></link> -->
<!-- <link href="css/login.css" type="text/css" rel="stylesheet"></link> -->
<link  href="<%=request.getContextPath()%>/css/newview/djlogin.css" type="text/css" rel="stylesheet"/>
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
<body onload="getFormValue()">
<div class="login-top" style="position: relative;">
			<img src="<%=request.getContextPath()%>/images/newview/min-logo-name-hz.png" style="margin-left: 0px;width: 90%;height: 70%" />
<%-- 			<img src="<%=request.getContextPath()%>/images/newview/mm.svg" style="height: 33px;position: absolute;right: 20px;top: 6px" />
 --%></div>

<div class="login-box" >
<div class="login-left">			
</div>
<div class="login-right">
 <div class="login-content">
  <form id="loginForm" name="logonForm" action="<%=request.getContextPath()%>/logonAction.do" method="post" onsubmit="return submitLoginForm()" style="height:45%;width:100%;padding-top:8%;">
    <div>
    <h2 class="login-title">用户登录</h2>
    <div class="form-group has-feedback">
    		
  			<input type="text"  name="username" class="form-control login-user required" style="color: #999;font-size: 18px;line-height: 45px;padding-left: 55px;"
			autocomplete="off" required="required" /> 
    </div>
    <div class="form-group has-feedback">
	<input type="password" name="password" class="form-control login-password required" style="color: #999;font-size: 18px;line-height: 45px;padding-left: 55px;"
		 autocomplete="off" required="required" />
		 <input type="hidden" name="params" id="params" value=""/>
		<!--  <input type="text" name="token" id="token" /> -->
	</div>
    
		<div class="form-group">
				<button type="submit" class="btn btn-primary btn-block btn-flat login-but"
					id="btnSubmit" 
					>立即登录</button>
		</div>
    
    
    
        <!-- <table width="80%" align="center" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="78%" height="30">
              <div align="center">
               
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
        </table> -->
     </div>
  </form>
  	
  </div>
</div>
</div>
<div class="login-footer">主办：中共杭州市委组织部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 技术支持：浙江网新恩普软件有限公司</div>
<sicp3:errors />
<script language="JavaScript">
function getFormValue() {
	if ('placeholder' in document.createElement('input')) {
		$('#username').attr("placeholder", "请输入用户名");
		$('#passwd').attr("placeholder", "请输入密码");
	}

	
}
function submitLoginForm(){
	  var username = document.logonForm.username.value;
	  var passwd = document.logonForm.password.value;
	  if (username ==null || username==""){
	    alert('请输入用户名');
	    document.all("username").focus();
	    return false;
	  }
	 // loginXBDJ();
	  document.logonForm.password.value = hex_md5(passwd);
	  var params = document.getElementById("params");
	  params.value = "{'username':'"+username+"','password':'"+document.logonForm.password.value+"','scene':''}";
	  return true;
	}



	


</script>
</body>
</html>
<%
	}
%>