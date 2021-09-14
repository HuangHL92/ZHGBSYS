<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ page import="com.insigma.odin.framework.sys.entity.SbdsUserlog" %>
<%@ page import="com.insigma.odin.framework.sys.oplog.OpLogManager" %>
<%
	SbdsUserlog userlog=(SbdsUserlog)request.getAttribute("userlog");
	//if(userlog.getOrisourceb()==null){
	//	out.println(userlog.getOrisource());
	//}else{
		String s=OpLogManager.getOriSource(userlog.getOrisourceb());	
	 	out.println(s);
	//}
%>
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
	document.getElementById('floatZc').style.display = "none";
</SCRIPT>
