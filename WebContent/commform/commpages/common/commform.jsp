<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="com.insigma.odin.framework.commform.local.sys.LoginManager"%>
<html>
<head>
<%
  String aab301="";
  String aaa027="";
  try{
	  aab301=LoginManager.getCurrentAab301();
	  aaa027=LoginManager.getCurrentAaa027();
  }catch(Exception e){
	  
  }
%>
<script>
var psqueryBuffer = "";
function getCurrentAab301() {
	return document.getElementById('currentAab301').value;
}

function getCurrentAaa027() {
	return document.getElementById('currentAaa027').value;
}
Ext.onReady(
	function(){
		document.getElementById("currentAab301").value='<%=aab301%>';
		document.getElementById("currentAaa027").value='<%=aaa027%>';
    }
);
</script>
</head>
<body>
<input type="hidden" id="currentAab301" value="<%=aab301%>" />
<input type="hidden" id="currentAaa027" value="<%=aaa027%>" />
</body>
</html>
