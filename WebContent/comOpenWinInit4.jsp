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
var subWinId = '<%=subWinId%>';
// var realParent = parent.Ext.getCmp(subWinId).initialConfig.thisWin;
// var params = parent.Ext.getCmp(subWinId).initialConfig.param;

</script>
<odin:hidden property="subWinIdBussessId" value="<%=subWinIdBussessId %>"/>
<odin:hidden property="subWinIdBussessId2" value=""/>
<script type="text/javascript">
var isGDCL = '<%=IsGDCL%>';   //�Ƿ�鵵����
Ext.onReady(function(){
	// document.getElementById("subWinIdBussessId2").value=params;
})	;



</script>