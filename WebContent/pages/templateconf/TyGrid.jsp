<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@include file="/comOpenWinInit.jsp" %>
<%
 String initParams = request.getParameter("initParams");
String gid ="";
String tabtype="";
if(initParams!= null &&!"".equals(initParams)){
	gid = initParams.split(",")[0];
	tabtype = initParams.split(",")[1];//tab���
}

 //String gid = initParams.split(",")[0];
 //String tabtype = initParams.split(",")[1];

%>
<%-- <%@page import="com.insigma.siis.local.pagemodel.templateconf.WebOfficePageModel"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Insert title here</title>

<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script src="<sicp3:rewrite forward='md5'/>"> </script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<script type="text/javascript"src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>

<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
</head>

<script type="text/javascript">
   var tabtype='<%=tabtype%>';
Ext.onReady(function(){
	$("#gid").val("");
	$("#tabtype").val("");
	$("#gid").val('<%=gid%>');
	$("#tabtype").val('<%=tabtype%>');
	
});


</script>

<style type="text/css">
.tableStyle {
	margin-left: 100px;
}
</style>

<body>
<odin:hidden property="id"/>
<table id="tablef" class="tableStyle">
	<tr>
		<td height="20px"></td>
	</tr>
	<tr>
		<odin:hidden property="gid"/>
		<odin:hidden property="tabtype"/>
<%-- 		<odin:textEdit property="project" readonly="false" label="&nbsp;&nbsp;&nbsp;&nbsp;��Ŀ" >(ֱ������ְ�����ƻ�ְ��˵��)</odin:textEdit> --%>
   <%if(tabtype.equals("nl")){ %> <!-- ���� -->
<%--    <odin:select2 property="project" label="&nbsp;&nbsp;&nbsp;��Ŀ " readonly="false" width="120" codeType="ZB64" required="true"></odin:select2> --%>
&nbsp;&nbsp;&nbsp;&nbsp;<odin:select2 property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;����" width="120" readonly="false"  data="['35�꼰����','35�꼰����'],['36-40��','36-40��'],['41-45��','41-45��'],['46-50��','46-50��'],['51-55��','51-55��'],['56�꼰����','56�꼰����']" required="true"></odin:select2>
   <%}if(tabtype.equals("xl")){ %> <!-- ѧ�� -->
<%--    <odin:select2 property="project" label="&nbsp;&nbsp;&nbsp;��Ŀ " readonly="false" width="120" codeType="ZB64" required="true"></odin:select2> --%>
<tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;ѧ��" required="true" codetype="ZB64" readonly="true"></tags:PublicTextIconEdit>
   <%}if(tabtype.equals("zy")){ %> <!-- רҵ -->
<%--    <odin:select2 property="project" label="&nbsp;&nbsp;&nbsp;��Ŀ " readonly="false" width="120" codeType="GB16835" required="true"></odin:select2> --%>
<tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;רҵ" required="true" codetype="GB16835" readonly="true"></tags:PublicTextIconEdit>
   <%}if(tabtype.equals("xb")){ %> <!-- �Ա� -->
<%--    <odin:select2 property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;�Ա� " readonly="false" width="120" codeType="GB2261" required="true"></odin:select2>
 --%><tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;�Ա�" required="true" codetype="GB2261" readonly="true"></tags:PublicTextIconEdit>
   <%}if(tabtype.equals("dp")){ %> <!-- ���� -->
<%--    <odin:select2 property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;��Ŀ " readonly="false" width="120" codeType="GB4762" required="true"></odin:select2> --%>
<tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;����" required="true" codetype="GB4762" readonly="true"></tags:PublicTextIconEdit>
   <%}if(tabtype.equals("mz")){ %> <!-- ���� -->
<%--    <odin:select2 property="project" label="&nbsp;&nbsp;&nbsp;��Ŀ " readonly="false" width="120" codeType="GB3304" required="true"></odin:select2> --%>
<tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;����" required="true" codetype="GB3304" readonly="true"></tags:PublicTextIconEdit>
    <%}if(tabtype.equals("sxly")){%><!-- ר�� -->
<%--    <odin:select2 property="project" label="&nbsp;&nbsp;&nbsp;��Ŀ " readonly="false" width="120" codeType="ZB72" required="true"></odin:select2> --%>
<tags:PublicTextIconEdit property="project" label="��Ϥ����" required="true" codetype="SXLY" readonly="true"></tags:PublicTextIconEdit>
    <%}if(tabtype.equals("dy")){ %><!-- ���� -->
<%--     <tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;����" required="true" codetype="DYJG" readonly="true"></tags:PublicTextIconEdit>--%>   
	<odin:textEdit property="project" value="��������ɳ�����ͬ" label="&nbsp;&nbsp;&nbsp;&nbsp;����" required="true" readonly="true"></odin:textEdit>
   <%}if(tabtype.equals("nel")){ %><!-- ���� -->
    <tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;����" required="true" codetype="NLJG" readonly="true"></tags:PublicTextIconEdit>
    <%}if(tabtype.equals("jy")){ %><!-- ���� -->
    <tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;����" required="true" codetype="JYJG" readonly="true"></tags:PublicTextIconEdit>
   <%}if(tabtype.equals("jl")){ %><!-- ���� -->
    <tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;����" required="true" codetype="TAGZB131" readonly="true"></tags:PublicTextIconEdit>
    <%}if(tabtype.equals("ly")){ %><!-- ��Դ -->
    <tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;��Ŀ" required="true" codetype="LYJG" readonly="true"></tags:PublicTextIconEdit>
     <%}if(tabtype.equals("rqz")){ %><!-- ��Դ -->
    <tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;��Ŀ" required="true" codetype="RQZJG" readonly="true"></tags:PublicTextIconEdit>
    <%} if(tabtype.equals("banzi")){ %><!-- ��Դ -->
    <%-- <tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;��Ŀ" required="true" codetype="RQZJG" readonly="true"></tags:PublicTextIconEdit> --%>
    	<odin:textEdit property="project" value="����" label="��������" required="true" readonly="true"></odin:textEdit>
    <%} %>
		
	</tr>
	</table>
	<table id="ass" class="tableStyle" >
	 <tr>
	 <odin:select2 property="quantity1" label="&nbsp;&nbsp;&nbsp;&nbsp;����" width="60"  data="['>=','>='],['<=','<=']" required="true"></odin:select2>
<!-- 	<td style="text-align:left; top:510px;"><input style="width:50px;margin-top: -5px;" type="text"  name="" id="" value=""/><a style="font-size:12px;font-weight: bold;">��ƽ��������XX-YY��ʽ��</a> </td>  -->
	<td style="text-align:left; top:510px;"><odin:textEdit property="quantity" readonly="false"  width="50" required="true" onkeyup="if (this.value != this.value.replace(/[^{0-9}\$]+/,'')) this.value=this.value.replace(/[^{0-9}\$]+/,'');"></odin:textEdit> </td>
	 </tr>
	 </table>
	<table class="tableStyle">
	  <tr>
	  <odin:select2 property="one_ticket_veto" label="һƱ���" width="60"  data="['0','��'],['1','��']" required="true"></odin:select2>
	 </tr>
	 <tr>
	 <odin:textEdit property="order_number" readonly="false" width="60" label="&nbsp;&nbsp;&nbsp;&nbsp;���" required="true" onkeyup="if (this.value != this.value.replace(/[^{0-9}\$]+/,'')) this.value=this.value.replace(/[^{0-9}\$]+/,'');"></odin:textEdit>
	 </tr>
	</table>
	<div style=" text-align:center;margin-top: 40px">
	<odin:button text="&nbsp��&nbsp&nbsp��&nbsp"  property="subm4"></odin:button>
	</div>

</body>
</html>