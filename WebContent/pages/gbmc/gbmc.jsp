<%@page import="java.util.Set"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@page import="com.insigma.siis.local.pagemodel.gbmc.expexcel.ExpTPB"%>
<%@page import="com.insigma.siis.local.pagemodel.gbmc.pojo.Gbmc"%>
<%@page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@taglib uri="/WEB-INF/odin.tld" prefix="odin" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/gbmc/gbtp.css">
<%
String sql = (String)session.getAttribute("sql");

List<Gbmc> list = ExpTPB.querybyid_gbmc3(sql);

String gbmcName = (String)session.getAttribute("gbmcName");
%>
<html>
<head>
 <meta charset="utf-8">
 <script type="text/javascript">
 var gbmcName = '<%=gbmcName %>';
 $(document).ready(function () {
     $("#BiaoTouTitle").html(gbmcName); 
 });
 </script>
</head>
<body>
<% if(list==null){ %>
<table id="coordTable1" class="coordTable A3" style="WIDTH: 1380px" cellspacing="0" sizcache="26" sizset="0">
 <tbody sizcache="26" sizset="0">
 <tr>
  <th style="FONT-SIZE: 15pt; BORDER-TOP: medium none; FONT-FAMILY: 黑体; BORDER-RIGHT: medium none; BORDER-BOTTOM: medium none; FONT-WEIGHT: 600; TEXT-ALIGN: left; BORDER-LEFT: medium none" colspan="15">&nbsp; </th>
 </tr>
 <colgroup>
<col width="79"></col>
<col width="226"></col>
<col width="35"></col>
<col width="35"></col>
<col width="51"></col>
<col width=""></col>
<col width=""></col>
<col width="101"></col>
<col width="104"></col>
<col width="104"></col>
<col width="104"></col>
<col width="154"></col>
<col width="104"></col>
<col width="80"></col>
</colgroup>
 <tr id="insertadd">
  <th class="lign" style="WIDTH: 1%; DISPLAY: none" rowspan="2">序<br />号</th>
  <th class="lign aligh_sensc" style="FONT-SIZE: 11pt; WIDTH: 79px; PADDING-BOTTOM: 9px !important; PADDING-TOP: 9px !important; PADDING-LEFT: 9px !important; PADDING-RIGHT: 9px !important" rowspan="2">姓&nbsp;名 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 226px;text-align:center" rowspan="2">现任职务 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="2">性<br />别 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="2">民<br />族 </th>
  
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 101px" rowspan="2">出生<br />年月 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;TEXT-ALIGN: center;" rowspan="2">籍&nbsp;贯 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;"  rowspan="2">参加工<br>作时间 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px" rowspan="2">入党<br />时间 </th>
  
  
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" colspan="2">全日制教育 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" colspan="2">在职教育 </th>
  
     
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" rowspan="2">职称 </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;" rowspan="2">任现职<br />时&nbsp;&nbsp;间 </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 154px;" rowspan="2">任现职务<br />层次时间 </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;" rowspan="2">任现职<br />级时间 </th>
  <th class="lign aligh_sensc" style="FONT-SIZE: 11pt; WIDTH: 80px;" rowspan="2">备&nbsp;注 </th>
 </tr>
 <tr>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 79px; TEXT-ALIGN: center; ">学历学位</th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 104px;TEXT-ALIGN: center;  BACKGROUND-COLOR: white">毕业院校<br />及&nbsp;专&nbsp;业 </th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 79px; TEXT-ALIGN: center;">学历学位</th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 104px; TEXT-ALIGN: center; BACKGROUND-COLOR: white">毕业院校<br />及&nbsp;专&nbsp;业 </th>
 </tr>
 <tr class="data" sizcache="17" sizset="0">
  <td class="rownum default_color aligh_sensc" style="DISPLAY: none" nodeindex="1">1</td>
  <td class="default_color " sizcache="16" sizset="0" nodeindex="2">
  </td>
  <td sizcache="16" sizset="1" nodeindex="3" style="text-align: left">
  </td>
  <td  sizcache="16" sizset="2" nodeindex="4">
  </td>
  <td  sizcache="16" sizset="3" nodeindex="5">
  </td>
  <td  sizcache="16" sizset="4" nodeindex="6">
  </td>
  <td class="align-center" sizcache="16" sizset="5" nodeindex="7">
  </td>
  <td style="TEXT-ALIGN: left" sizcache="16" sizset="6" nodeindex="8" WIDTH = "100px" >
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="7" nodeindex="9">
  </td>
  <td style="TEXT-ALIGN: left" sizcache="16" sizset="8" nodeindex="10" WIDTH = "100px" >
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="9" nodeindex="11">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="10" nodeindex="12">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="10" nodeindex="12">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="14" nodeindex="16">
  </td>
 </tr>
 <tr class="data" sizcache="17" sizset="15">
  <td class="rownum default_color aligh_sensc" style="DISPLAY: none" nodeindex="1">1</td>
  <td class="default_color " sizcache="16" sizset="0" nodeindex="2">
  </td>
  <td sizcache="16" sizset="1" nodeindex="3" style="text-align: left">
  </td>
  <td  sizcache="16" sizset="2" nodeindex="4">
  </td>
  <td  sizcache="16" sizset="3" nodeindex="5">
  </td>
  <td  sizcache="16" sizset="4" nodeindex="6">
  </td>
  <td class="align-center" sizcache="16" sizset="5" nodeindex="7">
  </td>
  <td style="TEXT-ALIGN: left" sizcache="16" sizset="6" nodeindex="8" WIDTH = "100px" >
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="7" nodeindex="9">
  </td>
  <td style="TEXT-ALIGN: left" sizcache="16" sizset="8" nodeindex="10" WIDTH = "100px" >
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="9" nodeindex="11">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="10" nodeindex="12">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="10" nodeindex="12">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="14" nodeindex="16">
  </td>
 </tr>
 <tr class="data" sizcache="17" sizset="30">
  <td class="rownum default_color aligh_sensc" style="DISPLAY: none" nodeindex="1">1</td>
  <td class="default_color " sizcache="16" sizset="0" nodeindex="2">
  </td>
  <td sizcache="16" sizset="1" nodeindex="3" style="text-align: left">
  </td>
  <td  sizcache="16" sizset="2" nodeindex="4">
  </td>
  <td  sizcache="16" sizset="3" nodeindex="5">
  </td>
  <td  sizcache="16" sizset="4" nodeindex="6">
  </td>
  <td class="align-center" sizcache="16" sizset="5" nodeindex="7">
  </td>
  <td style="TEXT-ALIGN: left" sizcache="16" sizset="6" nodeindex="8" WIDTH = "100px" >
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="7" nodeindex="9">
  </td>
  <td style="TEXT-ALIGN: left" sizcache="16" sizset="8" nodeindex="10" WIDTH = "100px" >
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="9" nodeindex="11">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="10" nodeindex="12">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="10" nodeindex="12">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="14" nodeindex="16">
  </td>
 </tr>
 <tr class="data" sizcache="17" sizset="45">
  <td class="rownum default_color aligh_sensc" style="DISPLAY: none" nodeindex="1">1</td>
  <td class="default_color " sizcache="16" sizset="0" nodeindex="2">
  </td>
  <td sizcache="16" sizset="1" nodeindex="3" style="text-align: left">
  </td>
  <td  sizcache="16" sizset="2" nodeindex="4">
  </td>
  <td  sizcache="16" sizset="3" nodeindex="5">
  </td>
  <td  sizcache="16" sizset="4" nodeindex="6">
  </td>
  <td class="align-center" sizcache="16" sizset="5" nodeindex="7">
  </td>
  <td style="TEXT-ALIGN: left" sizcache="16" sizset="6" nodeindex="8" WIDTH = "100px" >
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="7" nodeindex="9">
  </td>
  <td style="TEXT-ALIGN: left" sizcache="16" sizset="8" nodeindex="10" WIDTH = "100px" >
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="9" nodeindex="11">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="10" nodeindex="12">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="10" nodeindex="12">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="14" nodeindex="16">
  </td>
 </tr>
 <tr class="data" sizcache="17" sizset="45">
  <td class="rownum default_color aligh_sensc" style="DISPLAY: none" nodeindex="1">1</td>
  <td class="default_color " sizcache="16" sizset="0" nodeindex="2">
  </td>
  <td sizcache="16" sizset="1" nodeindex="3" style="text-align: left">
  </td>
  <td  sizcache="16" sizset="2" nodeindex="4">
  </td>
  <td  sizcache="16" sizset="3" nodeindex="5">
  </td>
  <td  sizcache="16" sizset="4" nodeindex="6">
  </td>
  <td class="align-center" sizcache="16" sizset="5" nodeindex="7">
  </td>
  <td style="TEXT-ALIGN: left" sizcache="16" sizset="6" nodeindex="8" WIDTH = "100px" >
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="7" nodeindex="9">
  </td>
  <td style="TEXT-ALIGN: left" sizcache="16" sizset="8" nodeindex="10" WIDTH = "100px" >
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="9" nodeindex="11">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="10" nodeindex="12">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="10" nodeindex="12">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="14" nodeindex="16">
  </td>
 </tr>
 <tr class="data" sizcache="17" sizset="60">
 </tr>

 </tbody>
</table>
<% }else{ 
	Integer rows = 5; //固定行数
	Integer index = 0;//页码
	  
	    //该单位的页数
	    int index1 = 0;
		  for (int i = 0 ; i<list.size() ; i++) {
			  Gbmc gbmc = list.get(i);
			  if(i+1>rows*index1||i==0){//每插入五行,或者为每个机构的第一行,插入表头和机构名称
				  index +=1;
				  index1 +=1;
%>
<table id="coordTable1" class="coordTable A3" style="WIDTH: 1380px" cellspacing="0" sizcache="26" sizset="0">
 <tbody sizcache="26" sizset="0">
<!--机构名称  -->
 <tr>
<%--   <th style="FONT-SIZE: 15pt; BORDER-TOP: medium none; FONT-FAMILY: 黑体; BORDER-RIGHT: medium none; BORDER-BOTTOM: medium none; FONT-WEIGHT: 600; TEXT-ALIGN: left; BORDER-LEFT: medium none" colspan="15"><%=dept %></th>
 </tr> --%>
<!--表头  -->
<colgroup>
<col width="79"></col>
<col width="226"></col>
<col width="35"></col>
<col width="35"></col>
<col width="51"></col>
<col width=""></col>
<col width=""></col>
<col width="101"></col>
<col width="104"></col>
<col width="104"></col>
<col width="104"></col>
<col width="154"></col>
<col width="104"></col>
<col width="80"></col>
</colgroup>
<tr id="insertadd">
  <!-- <th class="lign" style="WIDTH: 1%; DISPLAY: none" rowspan="2">序<br />号</th>
  <th class="lign aligh_sensc" style="FONT-SIZE: 11pt;  PADDING-BOTTOM: 9px !important; PADDING-TOP: 9px !important; PADDING-LEFT: 9px !important; PADDING-RIGHT: 9px !important" rowspan="2">姓&nbsp;名 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 226px; text-align:center" rowspan="2">现任职务 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px;" rowspan="2">性<br />别 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px;" rowspan="2">民<br />族 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 51px;" rowspan="2" style="word-break:keep-all;">籍贯 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" colspan="2">全日制教育 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" colspan="2">在职教育 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 101px" rowspan="2">出生<br />年月 </th>
     <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;"  rowspan="2">参加工<br>作时间 </th>
     <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;" rowspan="2">入党<br />时间 </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;" rowspan="2">任现职<br />时&nbsp;&nbsp;间 </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;" rowspan="2">任现职<br />级时间 </th>
  <th class="lign aligh_sensc" style="FONT-SIZE: 11pt; WIDTH: 80px;" rowspan="2">备&nbsp;注 </th> -->
  
  <th class="lign" style="WIDTH: 1%; DISPLAY: none" rowspan="2">序<br />号</th>
  <th class="lign aligh_sensc" style="FONT-SIZE: 11pt; WIDTH: 79px; PADDING-BOTTOM: 9px !important; PADDING-TOP: 9px !important; PADDING-LEFT: 9px !important; PADDING-RIGHT: 9px !important" rowspan="2">姓&nbsp;名 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 226px;text-align:center" rowspan="2">现任职务 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="2">性<br />别 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="2">民<br />族 </th>
  
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 101px" rowspan="2">出生<br />年月 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;TEXT-ALIGN: center;"  rowspan="2">籍&nbsp;贯 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;"  rowspan="2">参加工<br>作时间 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px" rowspan="2">入党<br />时间 </th>
  
  
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" colspan="2">全日制教育 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" colspan="2">在职教育 </th>
  
     
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" rowspan="2">职称 </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;" rowspan="2">任现职<br />时&nbsp;&nbsp;间 </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 154px;" rowspan="2">任现职务<br />层次时间 </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;" rowspan="2">任现职<br />级时间 </th>
  <th class="lign aligh_sensc" style="FONT-SIZE: 11pt; WIDTH: 80px;" rowspan="2">备&nbsp;注 </th>
 </tr>
 
 <tr>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 104px; TEXT-ALIGN: center; ">学历学位 </th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 104px; TEXT-ALIGN: center;  BACKGROUND-COLOR: white">毕业院校<br />及&nbsp;专&nbsp;业 </th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 104px; TEXT-ALIGN: center;">学历学位</th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 104px; TEXT-ALIGN: center; BACKGROUND-COLOR: white">毕业院校<br />及&nbsp;专&nbsp;业 </th>
 </tr>
<%	
			  }
			  //插入数据
%>
<tr class="data" sizcache="17" sizset="0">
  <td class="rownum default_color aligh_sensc" style="DISPLAY: none" nodeindex="1">1</td>
  <td class="default_color "  sizcache="16" sizset="0" nodeindex="2">
   <%=gbmc.getA0101() %>
  </td>
  <td sizcache="16" sizset="1" nodeindex="3" style="text-align: left">
  <%=gbmc.getA0192a()%>
  </td>
  <td  sizcache="16" sizset="2" nodeindex="4">
  <%=gbmc.getA0104() %>
  </td>
  <td  sizcache="16" sizset="3" nodeindex="5">
  <%=gbmc.getA0117() %>
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="9" nodeindex="11">
   <%=gbmc.getA0107() %>
  </td>
  <td  sizcache="16" sizset="4" nodeindex="6">
  <%=gbmc.getA0111a() %>
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="10" nodeindex="12">
   <%=gbmc.getA0134() %>
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
    <%=gbmc.getA0140() %>
  </td>
  <td class="align-center" sizcache="16" sizset="5" nodeindex="7">
  <%=gbmc.getQrzxl() %>
  </td>
  <td style="TEXT-ALIGN: left" sizcache="16" sizset="6" nodeindex="8" WIDTH = "100px" >
  <%=gbmc.getQrzxlxx()  %>
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="7" nodeindex="9">
  <%=gbmc.getZzxl()  %>
  </td>
  <td style="TEXT-ALIGN: left" sizcache="16" sizset="8" nodeindex="10" WIDTH = "100px" >
  <%=gbmc.getZzxlxx() %>
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
   <%=gbmc.getA0196() %>
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="10" nodeindex="12">
    <%=gbmc.getA0192f() %>
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
   <%=gbmc.getA0288() %>
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
   <%=gbmc.getA0192c() %>
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="14" nodeindex="16">
   <%-- <%=gbmc.getComments() %> --%>
  </td>
 </tr>
<%
     if(i+1==list.size())
    	 //如果循环到了末尾,判断是否满足固定行数,不满足则补足
     {
    	 if(i+1<=rows*index1)
    	 {
    		
    		 for(int j = 0 ; j<rows*index1-list.size() ; i++,j++)
    		 {
%>
<tr class="data" sizcache="17" sizset="15">
  <td class="rownum default_color aligh_sensc" style="DISPLAY: none" nodeindex="1">1</td>
  <td class="default_color " sizcache="16" sizset="0" nodeindex="2">
  </td>
  <td sizcache="16" sizset="1" nodeindex="3" style="text-align: left">
  </td>
  <td  sizcache="16" sizset="2" nodeindex="4">
  </td>
  <td  sizcache="16" sizset="3" nodeindex="5">
  </td>
  <td  sizcache="16" sizset="4" nodeindex="6">
  </td>
  <td class="align-center" sizcache="16" sizset="5" nodeindex="7">
  </td>
  <td style="TEXT-ALIGN: left" sizcache="16" sizset="6" nodeindex="8" WIDTH = "100px" >
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="7" nodeindex="9">
  </td>
  <td style="TEXT-ALIGN: left" sizcache="16" sizset="8" nodeindex="10" WIDTH = "100px" >
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="9" nodeindex="11">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="10" nodeindex="12">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="10" nodeindex="12">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="14" nodeindex="16">
  </td>
 </tr>
<%
    		 }
    	 }
     }
			  if(i+1==rows*index1&& i!=0){
				//满五行插入页码
%>
 </tbody>
</table>
<div id='br_"+<%=index.toString() %>+"' class='noprint' style='text-align:center;valign:center' ><br><br><span>..........................................（第(<%=index.toString() %>)页）..........................................</span><br><br></div>
<%
			  }
	   }

}
%>
</body>
</html>


