<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.insigma.odin.framework.onlineview.comm.CommResponseResult"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	CommResponseResult crr = (CommResponseResult) request
			.getAttribute("CRR");

	int resCode=-1;
	String message="";
	String type="";
	if (crr != null) {
		resCode = crr.getCode();
		message = crr.getMessage();
		type=crr.getType();
	}else{
		//通过页面打开
		if(request.getParameter("resCode") != null)
			resCode=new Integer(request.getParameter("resCode")).intValue();
		message=request.getParameter("message");
		type=request.getParameter("type");
	}
%>
<!-- 此页面主要用于呈现预览信息，或者表单上传后服务器返回的处理结果 -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/commform/onlineview/publ/syst/basis.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/commform/onlineview/fileLookArea.js"></script>


</head>
<body>
	<div id="TANGER_OCX_Area" name="TANGER_OCX_Area"
		style="position: absolute; height: 50px; left: 50%; visibility: hidden">
		<object id="TANGER_OCX" type="application/activex"
			classid="clsid:C9BC4DFF-4248-4a3c-8A49-63A7D317F404"
			codebase="<%=request.getContextPath()%>/control/OfficeControl.cab#version=5,0,2,4"
			width="100%" height="100%"> </object>
	</div>
	<div id="memoinfo" name="memoinfo"></div>
	<input type="hidden" id="resCode" name="resCode" value="<%=resCode%>">
	<input type="hidden" id="message" name="message" value="<%=message%>">
	<input type="hidden" id="type" name="type" value="<%=type%>">
	<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>">
</body>
</html>