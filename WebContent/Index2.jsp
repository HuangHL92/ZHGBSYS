<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="com.insigma.odin.framework.util.ExtGlobalNames"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<html>
<head>
	<title><%=ExtGlobalNames.sysConfig.get("SYS_TITLE")==null?"ȫ������Ա������Ϣϵͳ":ExtGlobalNames.sysConfig.get("SYS_TITLE")%></title>
	<link rel="icon" href="<%=request.getContextPath()%>/images/favicon.ico" mce_href="<%=request.getContextPath()%>/images/favicon.ico" type="image/x-icon">  
	<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico" mce_href="<%=request.getContextPath()%>/images/favicon.ico" type="image/x-icon">
	<odin:head/>
</head>
<script type="text/javascript">
/*
var leave_message = '��ȷ���뿪��?';
	window.onbeforeunload = function(e){ //�����Main.jsp��iframe�����䶯Ҳ�ᴥ��
		odin.ext.Ajax.request({
					url : '<%=request.getContextPath()%>/logoffAction.do',
					success : null,
					failure : null,
					params : {},
					asynchronous : false
				});		
		//if (!e) e = window.event;
		//alert(e.target||e.srcElement);
		//e.cancelBubble = true;
		//e.returnValue = leave_message;
		//if (e.stopPropagation) {
		//	e.stopPropagation();
		//	e.preventDefault();
		//}
		//return leave_message;
	}
*/	
</script>
<frameset cols="0,*,0" frameborder="no" border="0" framespacing="0">
  <frame id="left" src="blank.htm"/>
  <frame id="main" src="mainxtgl.jsp" scrolling="auto"/>
  <frame id="right" src="blank.htm"/>
 </frameset> 
</html>