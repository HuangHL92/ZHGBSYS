<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%@page import="com.insigma.odin.framework.RSACoder"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@page import="com.insigma.odin.framework.safe.SafeControlCenter"%>
<%@page import="com.insigma.odin.framework.safe.util.SafeConst"%>
<%@page import="com.insigma.odin.framework.util.ExtGlobalNames"%>
<%@page import="com.insigma.odin.framework.util.SysUtil"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>干部大数据综合管理平台</title>
<!-- <link rel = "Shortcut Icon" href="icos/hzb.ico"> </link> -->
<script src="basejs/md5.js"> </script>
<script src="basejs/jquery/jquery-1.7.2.min.js"> </script>

</head>
<body>
<!-- <iframe src="http://localhost:8889/hzbtj/LogonDialogXBDJ.jsp"></iframe> -->
<script language="JavaScript">





$(function(){
	
	//alert(getCookie('JSESSIONID'))
	loginXBDJ()
});
//登录干部系统
function loginXBDJ(){
	var username = '<%=request.getParameter("username")%>';
	var passwd = '<%=request.getParameter("password")%>';
	var param={"username":username,"password":passwd,params:"{'username':'"+username+"','password':'"+passwd+"','scene':''}"};
	 $.ajax({
        type: "POST",
        url:"/hzb/logonAction.do",
        data: param,
        // contentType: 'application/json;charset=UTF-8',
		// dataType:'JSONP',
        error: function(request) {
        	alert("登入失败！")
        },
        success: function(request) {
        	//window.location.href="/hzb/Index.jsp";
			
        },
        complete: function( xhr ){
        	//setCookie('JSESSIONID',xhr.getResponseHeader('sid'),1)
        }
	}); 
	
}
/**
 * 设置cookie
 *
 * @param time    有效时间（单位:ms）
 */
function setCookie(key, value, time) {
    if (time == null) {
        document.cookie = key + "=" + value;
    } else {
        // 有效时间：默认一天
        time = 24 * 60 * 60 * 1000;
        var expires = new Date();
        expires.setTime(expires.getTime() + time)
        document.cookie = key + "=" + value + ";expires=" + expires.toGMTString()+";domain='';path=/";
    }
}



function getCookie(key) {
    var arr, reg = new RegExp("(^| )" + key + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg)) {
        return unescape(arr[2]);
    } else {
        return null;
    }
}






</script>
</body>
</html>
