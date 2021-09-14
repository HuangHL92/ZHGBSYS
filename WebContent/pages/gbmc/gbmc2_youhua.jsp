<%@page import="com.insigma.siis.local.pagemodel.gbmc.expexcel.ExpTPB"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.insigma.siis.local.pagemodel.gbmc.pojo.Gbmc2"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin" %>
<%
String sql = (String)session.getAttribute("sql");

List<Gbmc2> list = ExpTPB.querybyid_gbmc2(sql);
%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/gbmc/gbtp.css">

<html>
<head>
 <meta charset="utf-8">
<script type="text/javascript">
	$(document).ready(function () {
	    $("#BiaoTouTitle").html("会议名册"); 
	}); 
	//增加表格
	function addNewTable(index,o){ 
		var objTable = $("#coordTable"+index); 
		$(objTable).find("tr td").each(function(){
			SetTDtext(this,"","");
		});
		
		
		//=================根据PageHtml快速复制多个页面================
		var pageSize = Math.ceil(o.length / 5.0);
		var allPageHtml = "";
		if (o.length % 5.0 ==0){
			for (var i=0;i<pageSize;i++){
				allPageHtml+=$("#tbodyPage").html();
			} 
		}else{
			for (var i=1;i<pageSize;i++){
				allPageHtml+=$("#tbodyPage").html();
			} 
			var remainderRows = o.length % 5.0;
			var trHtml = $("#tbodyPage").find("tr:eq(0)")[0].outerHTML;
			for (var i=0;i<remainderRows;i++){
				allPageHtml+=trHtml;
			}
			
		}
		$("#tbodyPage").html(allPageHtml);
		//======================================================
			
		
		
		$.each(o,function (i, rowData){
			var tr = $(objTable).find("tr:eq("+(i+3)+")");
			var tds = $("td:nth-child(n+2)", tr);
	        SetTDtext(tds[0],"a0101", rowData["a0101"]);  //   姓名
	        SetTDtext(tds[1],"a0192a", rowData["a0192a"]);  //   现工作单位及职务全称
	        SetTDtext(tds[2],"a0104",rowData["a0104"]);  //   性别
	        SetTDtext(tds[3],"a0117", rowData["a0117"]);  //   民族
	        SetTDtext(tds[4],"a0111a", rowData["a0111a"]);  //   籍贯
	        SetTDtext(tds[5],"qrzxl", rowData["qrzxl"]);  //   最高全日制学历
	        SetTDtext(tds[6],"qrzxlxx", rowData["qrzxlxx"]);  //   院校系专业（最高全日制学历）
	        SetTDtext(tds[7],"zzxl", rowData["zzxl"]);  //   最高在职学历
	        SetTDtext(tds[8],"zzxlxx", rowData["zzxlxx"]);  //   院校系专业（最高在职学历）
            SetTDtext(tds[9],"a0107", rowData["a0107"]);  //   出生日期
            SetTDtext(tds[10],"a0134", rowData["a0134"]);  //   参加工作时间
            SetTDtext(tds[11],"a0140", rowData["a0140"]);  //   入党时间
            SetTDtext(tds[12],"a0288", rowData["a0288"]);  //   任现职务层次时间
	        SetTDtext(tds[13],"a0192c", rowData["a0192c"]);  //   任该职级时间
	        SetTDtext(tds[14],"comments", rowData["comments"]);  //   最高学位，专业技术
		});		 
        
	}

  
    function printTable() {

        var tableToPrint = document.getElementById('coordTable');//将要被打印的表格
        var newWin = window.open("<%=request.getContextPath()%>/pages/gbmc/printpreview.jsp", 'newwindow', 'height=' + screen.height + ',width=' + screen.width + ',top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no');//新打开一个空窗口
 
    }
  

</script> 
</head>
<body>
<%if(list==null) {%>
<table id="coordTable1" class="coordTable A3" style="WIDTH: 1380px" cellspacing="0" sizcache="26" sizset="0">
 <tbody sizcache="26" sizset="0">
 <tr>
  <th style="FONT-SIZE: 15pt; BORDER-TOP: medium none; FONT-FAMILY: 黑体; BORDER-RIGHT: medium none; BORDER-BOTTOM: medium none; FONT-WEIGHT: 600; TEXT-ALIGN: left; BORDER-LEFT: medium none" colspan="15">&nbsp; </th>
 </tr>
 <tr id="insertadd">
  <th class="lign" style="WIDTH: 1%; DISPLAY: none" rowspan="2">序<br />号</th>
  <th class="lign aligh_sensc" style="FONT-SIZE: 11pt; WIDTH: 79px; PADDING-BOTTOM: 9px !important; PADDING-TOP: 9px !important; PADDING-LEFT: 9px !important; PADDING-RIGHT: 9px !important" rowspan="2">姓&nbsp;名 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 226px;text-align:center" rowspan="2">现任职务 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="2">性<br />别 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="2">民<br />族 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 51px" rowspan="2">籍贯 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" colspan="2">全日制教育 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" colspan="2">在职教育 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 101px" rowspan="2">出生<br />年月 </th>
     <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;"  rowspan="2">参加工<br>作时间 </th>
     <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px" rowspan="2">入党<br />时间 </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;" rowspan="2">任现职<br />时&nbsp;&nbsp;间 </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;" rowspan="2">任现职<br />级时间 </th>
  <th class="lign aligh_sensc" style="FONT-SIZE: 11pt; WIDTH: 80px;" rowspan="2">备&nbsp;注 </th>
 </tr>
 <tr>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 90px; TEXT-ALIGN: center; ">学历 </th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 100px;TEXT-ALIGN: center;  BACKGROUND-COLOR: white">毕业院校<br />及&nbsp;专&nbsp;业 </th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 90px; TEXT-ALIGN: center;">学历 </th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 100px; TEXT-ALIGN: center; BACKGROUND-COLOR: white">毕业院校<br />及&nbsp;专&nbsp;业 </th>
 </tr> 
 </thead>
 <tbody id="tbodyPage">
  <tr class="data" sizcache="17" sizset="0">
  <td class="rownum default_color aligh_sensc" style="DISPLAY: none" nodeindex="1">1</td>
  <td class="default_color "  sizcache="16" sizset="0" nodeindex="2">
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
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="14" nodeindex="16">
  </td>
 </tr>
 </tbody>
</table>
<BR>
<%} else{


	 for (int i = 0 ; i<list.size() ; i++) {
		 Gbmc2 gbmc = list.get(i);
		 if(i==0){//每插入五行,或者为第一行,插入表头和机构名称
			  
%> 
<table id="coordTable1" class="coordTable A3" style="WIDTH: 1380px" cellspacing="0" sizcache="26" sizset="0">
 <tbody sizcache="26" sizset="0">
 <tr>
  <th style="FONT-SIZE: 15pt; BORDER-TOP: medium none; FONT-FAMILY: 黑体; BORDER-RIGHT: medium none; BORDER-BOTTOM: medium none; FONT-WEIGHT: 600; TEXT-ALIGN: left; BORDER-LEFT: medium none" colspan="15">&nbsp; </th>
 </tr>
 <tr id="insertadd">
  <th class="lign" style="WIDTH: 1%; DISPLAY: none" rowspan="2">序<br />号</th>
  <th class="lign aligh_sensc" style="FONT-SIZE: 11pt; WIDTH: 79px; PADDING-BOTTOM: 9px !important; PADDING-TOP: 9px !important; PADDING-LEFT: 9px !important; PADDING-RIGHT: 9px !important" rowspan="2">姓&nbsp;名 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 226px;text-align:center" rowspan="2">现任职务 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="2">性<br />别 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="2">民<br />族 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 51px" rowspan="2">籍贯 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" colspan="2">全日制教育 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" colspan="2">在职教育 </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 101px" rowspan="2">出生<br />年月 </th>
     <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;"  rowspan="2">参加工<br>作时间 </th>
     <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px" rowspan="2">入党<br />时间 </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;" rowspan="2">任现职<br />时&nbsp;&nbsp;间 </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;" rowspan="2">任现职<br />级时间 </th>
  <th class="lign aligh_sensc" style="FONT-SIZE: 11pt; WIDTH: 80px;" rowspan="2">备&nbsp;注 </th>
 </tr>
 <tr>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 90px; TEXT-ALIGN: center; ">学历 </th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 100px;TEXT-ALIGN: center;  BACKGROUND-COLOR: white">毕业院校<br />及&nbsp;专&nbsp;业 </th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 90px; TEXT-ALIGN: center;">学历 </th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 100px; TEXT-ALIGN: center; BACKGROUND-COLOR: white">毕业院校<br />及&nbsp;专&nbsp;业 </th>
 </tr> 
 </thead>
  <tbody id="tbodyPage">
	<% }%>
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
  <td  sizcache="16" sizset="4" nodeindex="6">
  <%=gbmc.getA0111a() %>
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
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="9" nodeindex="11">
   <%=gbmc.getA0107() %>
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="10" nodeindex="12">
   <%=gbmc.getA0134() %>
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
    <%=gbmc.getA0140() %>
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="10" nodeindex="12">
    <%=gbmc.getA0192f() %>
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="11" nodeindex="13">
   <%=gbmc.getA0192c() %>
  </td>
  <td style="TEXT-ALIGN: center" sizcache="16" sizset="14" nodeindex="16">
   <%=gbmc.getComments() %>
  </td>
 </tr>
 
 <%
     if(i+1==list.size())
    	
     {
    	
%>
 </tbody>
</table>

<%}}} %>
</body>
</html>