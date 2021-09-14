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
	tabtype = initParams.split(",")[1];//tab类别
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
<%-- 		<odin:textEdit property="project" readonly="false" label="&nbsp;&nbsp;&nbsp;&nbsp;项目" >(直接输入职务名称或职务说明)</odin:textEdit> --%>
   <%if(tabtype.equals("nl")){ %> <!-- 年龄 -->
<%--    <odin:select2 property="project" label="&nbsp;&nbsp;&nbsp;项目 " readonly="false" width="120" codeType="ZB64" required="true"></odin:select2> --%>
&nbsp;&nbsp;&nbsp;&nbsp;<odin:select2 property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;年龄" width="120" readonly="false"  data="['35岁及以下','35岁及以下'],['36-40岁','36-40岁'],['41-45岁','41-45岁'],['46-50岁','46-50岁'],['51-55岁','51-55岁'],['56岁及以下','56岁及以下']" required="true"></odin:select2>
   <%}if(tabtype.equals("xl")){ %> <!-- 学历 -->
<%--    <odin:select2 property="project" label="&nbsp;&nbsp;&nbsp;项目 " readonly="false" width="120" codeType="ZB64" required="true"></odin:select2> --%>
<tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;学历" required="true" codetype="ZB64" readonly="true"></tags:PublicTextIconEdit>
   <%}if(tabtype.equals("zy")){ %> <!-- 专业 -->
<%--    <odin:select2 property="project" label="&nbsp;&nbsp;&nbsp;项目 " readonly="false" width="120" codeType="GB16835" required="true"></odin:select2> --%>
<tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;专业" required="true" codetype="GB16835" readonly="true"></tags:PublicTextIconEdit>
   <%}if(tabtype.equals("xb")){ %> <!-- 性别 -->
<%--    <odin:select2 property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;性别 " readonly="false" width="120" codeType="GB2261" required="true"></odin:select2>
 --%><tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;性别" required="true" codetype="GB2261" readonly="true"></tags:PublicTextIconEdit>
   <%}if(tabtype.equals("dp")){ %> <!-- 党派 -->
<%--    <odin:select2 property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;项目 " readonly="false" width="120" codeType="GB4762" required="true"></odin:select2> --%>
<tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;党派" required="true" codetype="GB4762" readonly="true"></tags:PublicTextIconEdit>
   <%}if(tabtype.equals("mz")){ %> <!-- 民族 -->
<%--    <odin:select2 property="project" label="&nbsp;&nbsp;&nbsp;项目 " readonly="false" width="120" codeType="GB3304" required="true"></odin:select2> --%>
<tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;民族" required="true" codetype="GB3304" readonly="true"></tags:PublicTextIconEdit>
    <%}if(tabtype.equals("sxly")){%><!-- 专长 -->
<%--    <odin:select2 property="project" label="&nbsp;&nbsp;&nbsp;项目 " readonly="false" width="120" codeType="ZB72" required="true"></odin:select2> --%>
<tags:PublicTextIconEdit property="project" label="熟悉领域" required="true" codetype="SXLY" readonly="true"></tags:PublicTextIconEdit>
    <%}if(tabtype.equals("dy")){ %><!-- 地域 -->
<%--     <tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;地域" required="true" codetype="DYJG" readonly="true"></tags:PublicTextIconEdit>--%>   
	<odin:textEdit property="project" value="出生地与成长地相同" label="&nbsp;&nbsp;&nbsp;&nbsp;地域" required="true" readonly="true"></odin:textEdit>
   <%}if(tabtype.equals("nel")){ %><!-- 能力 -->
    <tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;能力" required="true" codetype="NLJG" readonly="true"></tags:PublicTextIconEdit>
    <%}if(tabtype.equals("jy")){ %><!-- 经验 -->
    <tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;经验" required="true" codetype="JYJG" readonly="true"></tags:PublicTextIconEdit>
   <%}if(tabtype.equals("jl")){ %><!-- 经历 -->
    <tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;经历" required="true" codetype="TAGZB131" readonly="true"></tags:PublicTextIconEdit>
    <%}if(tabtype.equals("ly")){ %><!-- 来源 -->
    <tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;项目" required="true" codetype="LYJG" readonly="true"></tags:PublicTextIconEdit>
     <%}if(tabtype.equals("rqz")){ %><!-- 来源 -->
    <tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;项目" required="true" codetype="RQZJG" readonly="true"></tags:PublicTextIconEdit>
    <%} if(tabtype.equals("banzi")){ %><!-- 来源 -->
    <%-- <tags:PublicTextIconEdit property="project" label="&nbsp;&nbsp;&nbsp;&nbsp;项目" required="true" codetype="RQZJG" readonly="true"></tags:PublicTextIconEdit> --%>
    	<odin:textEdit property="project" value="人数" label="班子人数" required="true" readonly="true"></odin:textEdit>
    <%} %>
		
	</tr>
	</table>
	<table id="ass" class="tableStyle" >
	 <tr>
	 <odin:select2 property="quantity1" label="&nbsp;&nbsp;&nbsp;&nbsp;数量" width="60"  data="['>=','>='],['<=','<=']" required="true"></odin:select2>
<!-- 	<td style="text-align:left; top:510px;"><input style="width:50px;margin-top: -5px;" type="text"  name="" id="" value=""/><a style="font-size:12px;font-weight: bold;">（平均年龄用XX-YY格式）</a> </td>  -->
	<td style="text-align:left; top:510px;"><odin:textEdit property="quantity" readonly="false"  width="50" required="true" onkeyup="if (this.value != this.value.replace(/[^{0-9}\$]+/,'')) this.value=this.value.replace(/[^{0-9}\$]+/,'');"></odin:textEdit> </td>
	 </tr>
	 </table>
	<table class="tableStyle">
	  <tr>
	  <odin:select2 property="one_ticket_veto" label="一票否决" width="60"  data="['0','是'],['1','否']" required="true"></odin:select2>
	 </tr>
	 <tr>
	 <odin:textEdit property="order_number" readonly="false" width="60" label="&nbsp;&nbsp;&nbsp;&nbsp;序号" required="true" onkeyup="if (this.value != this.value.replace(/[^{0-9}\$]+/,'')) this.value=this.value.replace(/[^{0-9}\$]+/,'');"></odin:textEdit>
	 </tr>
	</table>
	<div style=" text-align:center;margin-top: 40px">
	<odin:button text="&nbsp保&nbsp&nbsp存&nbsp"  property="subm4"></odin:button>
	</div>

</body>
</html>