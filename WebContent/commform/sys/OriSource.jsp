<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ page import="com.insigma.odin.framework.sys.batch.entity.SbdsUserlog" %>
<%
	SbdsUserlog userlog=(SbdsUserlog)request.getAttribute("userlog");
	out.println(userlog.getOrisource());
%>
<span style="position:absolute;top:100;left:200">
  <p class="rightup"><img src="<%=request.getContextPath()%>/commform/img/ywcd_fang.gif"></p>
</span>
<SCRIPT LANGUAGE="JavaScript">
	var buttons=document.getElementsByTagName("button");
	//alert(buttons.length);
	for(var i=0;i<buttons.length;i++){
		buttons[i].setAttribute("disabled","true");
		//buttons[i].parentNode.removeChild(buttons[i]);
		//alert("ok");
	}
	
	var edits=document.getElementsByTagName("input");
	for(var i=0;i<edits.length;i++){
		//edits[i].setAttribute("disabled","true");		
		edits[i].setAttribute("onchange","");
	}
</SCRIPT>