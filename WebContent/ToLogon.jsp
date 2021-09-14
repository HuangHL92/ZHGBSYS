<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=GBK"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公务员汇总一期登录页面</title>
<script src="basejs/md5.js"> </script>
<script src="basejs/jquery/jquery-1.7.2.min.js"> </script>
<script type="text/javascript">  
</script> 
</head>

<body>
    <form name="logonForm" action="http://192.168.1.100<%=request.getContextPath()%>/logonAction.do" 
    	method="post" onsubmit="return submitLoginForm()">
		<input class="product_thumb" name="username" id="username" type="hidden" value="" class="btn"   />
		<input class="product_thumb" name="passwd"  type="hidden"  value="" class="btn" />
		<input name="submitbtn" id="submitbtn" type="image"/>
        <input type="hidden" name="params" id="params" value=""/>
        
        <p align=center> 
        	<font color="#0066ff" size="6">正在跳转，请稍等</font>
        	<font color="#0066ff" size="6" face="Arial">...</font>
　       </p>
        
      </form>
<script language="JavaScript">

function submitLoginForm(){
  var username = document.logonForm.username.value;
  var passwd = document.logonForm.passwd.value;

  
  if (username ==null || username==""){
    alert('请输入用户名');
    document.all("username").focus();
    return false;
  }

  
  var params = document.getElementById("params");
  params.value = "{'username':'"+username+"','password':'"+document.logonForm.passwd.value+"','scene':''}";
  console.log("{'username':'"+username+"','password':'"+document.logonForm.passwd.value+"','scene':''}");
  
  return true;
};


window.document.body.onload = function(){
	document.getElementById("passwd").value = window.opener.passwd;
	document.getElementById("username").value = window.opener.username;
	document.getElementById("submitbtn").click();
}

</script>
</body>
</html>
