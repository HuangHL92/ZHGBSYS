<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath();%>
<%@page import="com.insigma.siis.local.pagemodel.templateconf.BackCheckPageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>
<%
 String sql1 = request.getParameter("initParams");
//System.out.println(sql1);
%>

<script type="text/javascript">
Ext.onReady(function() {
});
   
function save(ryid){
	 radow.doEvent('save',ryid);//ְλ˵��
}
 function huixian(ryid){
	 radow.doEvent('huixian',ryid);//ְλ˵��
	 document.getElementById("ryid").value=ryid;
} 
</script>
<odin:hidden property="ryid"/>
<odin:head></odin:head>
<odin:base></odin:base>
<odin:hidden property="sql" /> 
<div id="border">
<table align="center" bgcolor="white" width="100%" cellspacing="0px;" style='table-layout: fixed;'> 
<tr>
<tags:PublicTextIconEdit property="zc" label="&nbsp;&nbsp;&nbsp;ר���ṹ" codetype="ZCJG" readonly="true"></tags:PublicTextIconEdit>
<tags:PublicTextIconEdit property="dy" label="&nbsp;&nbsp;&nbsp;����ṹ"  codetype="DYJG" readonly="true"></tags:PublicTextIconEdit>
<tags:PublicTextIconEdit property="nel" label="&nbsp;&nbsp;&nbsp;�����ṹ"  codetype="NLJG" readonly="true"></tags:PublicTextIconEdit>

</tr> 
	
	<tr>
	<tags:PublicTextIconEdit property="jy" label="&nbsp;&nbsp;&nbsp;����ṹ"  codetype="JYJG" readonly="true"></tags:PublicTextIconEdit>
	<tags:PublicTextIconEdit property="jl" label="&nbsp;&nbsp;&nbsp;�����ṹ" codetype="JLJG" readonly="true"></tags:PublicTextIconEdit>
	<tags:PublicTextIconEdit property="ly" label="&nbsp;&nbsp;&nbsp;��Դ�ṹ"  codetype="LYJG" readonly="true"></tags:PublicTextIconEdit>
	</tr>
	<tr>
	<tags:PublicTextIconEdit property="rqz" label="&nbsp;&nbsp;&nbsp;������" codetype="RQZJG" readonly="true"></tags:PublicTextIconEdit>
	</tr>
	

</table>
</div>
<script type="text/javascript">

</script>
<style>

</style>
