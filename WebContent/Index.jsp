<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@page import="com.insigma.odin.framework.privilege.PrivilegeManager"%>
<%@page import="com.insigma.odin.framework.privilege.vo.UserVO"%>





<%
String g_contextpath = request.getContextPath();
String userid = SysManagerUtils.getUserId();
UserVO user = PrivilegeManager.getInstance().getCueLoginUser();

if("U000".equals(userid)||"1".equals(user.getUsertype())){
	%>
	<title>�û�ƽ̨��Ȩ</title>
	<link rel="icon" href="<%=request.getContextPath()%>/image/logo_wx.png" type="image/x-icon" />
	<link rel="shortcut icon" href="<%=request.getContextPath()%>/logo_wx.png" type="image/x-icon" />
	<body leftmargin="0" topmargin="0" style=" height: 1px; ">
		<Iframe id="2c90973d6cd70f64016d0494cf6500ba"  width="100%" height="100%" scrolling="auto" frameborder="no"  border="0"  marginwidth="0"  marginheight="0" src="<%=g_contextpath %>/radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysmanager.authority.UsersRoot"
	 	style="clear:both;margin-left:0px;margin-right:0px;float:right; height: 100%"></Iframe>
	
		<script type="text/javascript">
var ht = document.documentElement.clientHeight;
document.body.style.height=ht+"px";


	</script>
	</body>
	
	<%
}else{
	%>
		<%@include file="MainX.jsp" %>
		
		<script type="text/javascript">
	
	
	$(function(){
		setTimeout(function(){loginXBDJ()},100)
		
	});
	//��¼ѡ���㽫
	function loginXBDJ(){
		var username = '<%=SysManagerUtils.getUserloginName()%>';
		var passwd = '<%=SysManagerUtils.getUserPwd()%>';
		//http://71.8.177.189:1902/login/?username=user&password=psd
				
				
		$("#xbdjWidow").attr('src',"http://"+'<%=GlobalNames.sysConfig.get("XBDJ_IP")%>'+":"+'<%=GlobalNames.sysConfig.get("XBDJ_MAINPORT")%>'+"/login/?username="+username+"&password="+passwd);
		
		
		
		
		
		
		
		
		<%-- var param="/"+username + "/" + passwd;
		 $.ajax({
	        type: "GET",
	        url:"http://"+'<%=GlobalNames.sysConfig.get("XBDJ_IP")%>'+":"+'<%=GlobalNames.sysConfig.get("XBDJ_PORT")%>'+"/hzgb-api/v1/login"+param,
	        // contentType: 'application/json;charset=UTF-8',
			// dataType:'JSONP',
	        error: function(request) {
				//alert('ѡ���㽫��¼ʧ��');
	        },
	        success: function(data) {
	        	//console.log(data)
	        	  if(data&&data.data&&data.data.token){
	        		  setCookie('token',data.data.token,1)
	        	  }
				 
	        }
		});  --%>
		
	}
	/**
	 * ����cookie
	 *
	 * @param time    ��Чʱ�䣨��λ:ms��
	 */
	function setCookie(key, value, time) {
	    if (time == null) {
	        document.cookie = key + "=" + value;
	    } else {
	        // ��Чʱ�䣺Ĭ��һ��
	        time = 24 * 60 * 60 * 1000;
	        var expires = new Date();
	        expires.setTime(expires.getTime() + time)
	        document.cookie = key + "=" + value + ";expires=" + expires.toGMTString()+";domain="+'<%=GlobalNames.sysConfig.get("XBDJ_IP")%>'+";path=/";
	    }
	}
</script>
		
	<%
	
}
%>

	

