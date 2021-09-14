<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%
String subWinId = request.getParameter("subWinId");
String subWinIdBussessId = request.getParameter("subWinIdBussessId");
if(subWinIdBussessId!=null){
	subWinIdBussessId = new String(request.getParameter("subWinIdBussessId").getBytes("iso8859-1"),"utf8");
}

String IsGDCL = request.getParameter("IsGDCL");
   
%>
<script type="text/javascript">
var subWinId = '<%=subWinId==null?"":subWinId%>';
var realParent = parent.Ext.getCmp(subWinId).initialConfig.thisWin;
var params = parent.Ext.getCmp(subWinId).initialConfig.param;
var parentParams = parent.Ext.getCmp(subWinId).initialConfig;
var parentParam = parent.Ext.getCmp(subWinId).initialConfig;
</script>
<odin:hidden property="subWinIdBussessId" value="<%=subWinIdBussessId %>"/>
<odin:hidden property="subWinIdBussessId2" value=""/>
<script type="text/javascript">
var isGDCL = '<%=IsGDCL%>';   //ÊÇ·ñ¹éµµ²ÄÁÏ
Ext.onReady(function(){
	document.getElementById("subWinIdBussessId2").value=params; 
})	;



</script>