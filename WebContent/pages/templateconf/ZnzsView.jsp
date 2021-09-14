<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@include file="/comOpenWinInit.jsp" %>
<%-- <%@page import="com.insigma.siis.local.pagemodel.templateconf.WebOfficePageModel"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
 String gid = request.getParameter("initParams");
%>
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
   
Ext.onReady(function(){
	$("#jgname").val("");
	$("#jgname").val('<%=gid%>');
	
});


</script>
<body>
<odin:hidden property="id"/>
<table id="tablef"  >
	<tr>
		<td height="10px"></td>
	</tr>
	<tr>
		<odin:textEdit property="project"  readonly="false" label="项目" required="true">(直接输入职务名称或职务说明)</odin:textEdit>
	</tr>
	<tr>
	<odin:hidden property="jgname"/>
           <%-- <tags:PublicTextIconEdit property="duty_category" label="职务类别" required="true" codetype="ZB42" readonly="true"></tags:PublicTextIconEdit> --%>
        <odin:select2 property="duty_category" label="职务职级"  data="['1','领导职务'],['2','非领导职务']"></odin:select2>
	</tr>
	<tr>
<%-- 		<odin:select2 property="a0165" label="职务职级"  data="['0','正部级'],['1','副部级'],['2','非常领导职务']"></odin:select2> --%>
 	<tags:PublicTextIconEdit property="duty_rank" label="职务职级" required="true" codetype="ZB09" readonly="true"></tags:PublicTextIconEdit>
	</tr>
	</table>
	<table id="ass"  >
	 <tr>
<%-- 	 <odin:select2 property="quantity" label="&nbsp;&nbsp;&nbsp;&nbsp;数量" width="60"  data="['0','>='],['1','<=']"></odin:select2> --%>
	<td style="text-align:left; top:510px;"><odin:textEdit property="quantity" readonly="false" required="true" label="&nbsp;&nbsp;&nbsp;&nbsp;数量"  width="50" onkeyup="if (this.value != this.value.replace(/[^{0-9}\$]+/,'')) this.value=this.value.replace(/[^{0-9}\$]+/,'');"></odin:textEdit> </td> 
	 </tr>
	 </table>
	<table>
	  <tr>
	  <odin:select2 property="one_ticket_veto" label="一票否决" width="60"  readonly="false" data="['0','是'],['1','否']" required="true"></odin:select2>
	 </tr>
	 <tr>
	 <odin:textEdit property="order_number" readonly="false" width="60" label="序号" required="true" onkeyup="if (this.value != this.value.replace(/[^{0-9}\$]+/,'')) this.value=this.value.replace(/[^{0-9}\$]+/,'');"></odin:textEdit>
	 </tr>
	</table>
	<div style=" text-align:center; ">
	<odin:button text="&nbsp保&nbsp&nbsp存&nbsp"  property="subm"></odin:button>
	</div>
</body>
</html>