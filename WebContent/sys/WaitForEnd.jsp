<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%
	String isEnd = (String) request.getSession().getAttribute("HTTP_SESSION_ATTRIBUTE_IS_EXP_FILE_END");
	if (isEnd != null) {
		request.getSession().removeAttribute("HTTP_SESSION_ATTRIBUTE_IS_EXP_FILE_END");
	}
%>
<script>
	function isEnd(){
		return <%=isEnd %>;
	}
	//alert(isEnd());
	if(isEnd()=="1"){
		parent.doCloseWin();
	}else{
		setTimeout(parent.doWaitingForEnd,500);
	}
</script>