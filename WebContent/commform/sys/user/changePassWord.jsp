<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ page import="com.lbs.leaf.cp.util.SysUtil" %>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>修改当前用户密码</title>
<script src="<sicp3:rewrite forward='md5'/>"> </script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<odin:head/>
<odin:MDParam></odin:MDParam>

</head>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<odin:form action="/sys/userAction?method=alterPassword">
<table width="94%" align="center">
<tr>
<td>
	<odin:groupBox title="更改密码" >
	<table width="100%">
	<tr>
		<td colspan="2" height="10"></td>
	</tr>
	<tr>
		<td width="60%">
			<table width="100%">
				<tr>
					<odin:textEdit label="用户名称" readonly="true" property="username" value="<%=SysUtil.getCacheCurrentUser().getUser().getUsername()%>"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit label="旧密码" inputType="password" property="passwd" value="" tabindex="0"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit label="新密码" required="true" inputType="password" property="newpasswd" value=""></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit label="确认密码" required="true" inputType="password" property="confirm" value=""></odin:textEdit>
				</tr>
			</table>
		</td>
		<td width="40%">
			<table width="100%">
				<tr>
					<td><img src="../commform/images/xiugai.gif" onclick="ConfirmPassword()" style="cursor:hand"></td>
				</tr>
				<tr>
					<td height="4"></td>
				</tr>
				<tr>
					<td><img src="../commform/images/qingkong.gif" onclick="clearInput()" style="cursor:hand"></td>
				</tr>
			</table>		
		</td>
	</tr>
	<tr>
		<td colspan="2" height="12"></td>
	</tr>
	</table>	
	</odin:groupBox>	
</td>	
</tr>
</table>
</odin:form>
<script>
/** 首次载入列表数据开始 */
Ext.onReady(function(){
	//
});
function ConfirmPassword(){
	  var username = document.sysUserForm.username.value;
	  if((null == username) ||( "" == username)){
	  	odin.cueCheckObj = document.sysUserForm.username;
	  	odin.alert("用户ID不能为空！",odin.doFocus);
	    return false;
	  }
	  var pd = document.sysUserForm.newpasswd.value;
	  var confirm = document.sysUserForm.confirm.value;
	  if(pd != confirm){
	  	odin.cueCheckObj = document.sysUserForm.newpasswd;
	  	odin.alert("新密码和确认密码不一致！",odin.doFocus);
	    return false;
	  }
	  odin.submit(document.sysUserForm,successFun,successFun);
}
function successFun(response){
	var msg = response.mainMessage;
	if(msg.indexOf("用户")>=0){
		msg = msg.substring(msg.indexOf("用户"));
	}
    if(response.messageCode=='0'){ //修改成功
    	clearInput();
    }else{
    	document.sysUserForm.passwd.value = '';
       	odin.cueCheckObj = document.sysUserForm.passwd;
    }
    odin.alert(msg,odin.doFocus);
}
function clearInput(){
	document.sysUserForm.passwd.value='';
   	document.sysUserForm.newpasswd.value='';
   	document.sysUserForm.confirm.value='';
}
</script>

</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>