<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%
String subWinId = request.getParameter("subWinId");
String subWinIdBussessId = request.getParameter("subWinIdBussessId");
if(subWinIdBussessId!=null){
	subWinIdBussessId = new String(request.getParameter("subWinIdBussessId").getBytes("iso8859-1"),"utf8");
}
%>
<script type="text/javascript">
var lgparent = parent;
var realParent;
var parent;
var parentParam;
var parentParams;
try{

	realParent = window.dialogArguments.window;
	parent = window.dialogArguments.window;
	parentParam = window.dialogArguments.param;
	parentParams = window.dialogArguments.param;
	
}catch(e){}
</script>
<odin:hidden property="subWinIdBussessId" value=""/>
<odin:hidden property="subWinIdBussessId2" value=""/>
<script type="text/javascript">
Ext.onReady(function(){
	try{
		document.getElementById("subWinIdBussessId2").value=window.dialogArguments.param; 
		document.getElementById("subWinIdBussessId").value=window.dialogArguments.param;
		document.title = window.dialogArguments.title+document.title;
	}catch(e){}
})	;



</script>