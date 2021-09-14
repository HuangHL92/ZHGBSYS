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
	    $("#BiaoTouTitle").html("��������"); 
	}); 
	//���ӱ��
	function addNewTable(index,o){ 
		var objTable = $("#coordTable"+index); 
		$(objTable).find("tr td").each(function(){
			SetTDtext(this,"","");
		});
		
		
		//=================����PageHtml���ٸ��ƶ��ҳ��================
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
	        SetTDtext(tds[0],"a0101", rowData["a0101"]);  //   ����
	        SetTDtext(tds[1],"a0192a", rowData["a0192a"]);  //   �ֹ�����λ��ְ��ȫ��
	        SetTDtext(tds[2],"a0104",rowData["a0104"]);  //   �Ա�
	        SetTDtext(tds[3],"a0117", rowData["a0117"]);  //   ����
	        SetTDtext(tds[4],"a0111a", rowData["a0111a"]);  //   ����
	        SetTDtext(tds[5],"qrzxl", rowData["qrzxl"]);  //   ���ȫ����ѧ��
	        SetTDtext(tds[6],"qrzxlxx", rowData["qrzxlxx"]);  //   ԺУϵרҵ�����ȫ����ѧ����
	        SetTDtext(tds[7],"zzxl", rowData["zzxl"]);  //   �����ְѧ��
	        SetTDtext(tds[8],"zzxlxx", rowData["zzxlxx"]);  //   ԺУϵרҵ�������ְѧ����
            SetTDtext(tds[9],"a0107", rowData["a0107"]);  //   ��������
            SetTDtext(tds[10],"a0134", rowData["a0134"]);  //   �μӹ���ʱ��
            SetTDtext(tds[11],"a0140", rowData["a0140"]);  //   �뵳ʱ��
            SetTDtext(tds[12],"a0288", rowData["a0288"]);  //   ����ְ����ʱ��
	        SetTDtext(tds[13],"a0192c", rowData["a0192c"]);  //   �θ�ְ��ʱ��
	        SetTDtext(tds[14],"comments", rowData["comments"]);  //   ���ѧλ��רҵ����
		});		 
        
	}

  
    function printTable() {

        var tableToPrint = document.getElementById('coordTable');//��Ҫ����ӡ�ı��
        var newWin = window.open("<%=request.getContextPath()%>/pages/gbmc/printpreview.jsp", 'newwindow', 'height=' + screen.height + ',width=' + screen.width + ',top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no');//�´�һ���մ���
 
    }
  

</script> 
</head>
<body>
<%if(list==null) {%>
<table id="coordTable1" class="coordTable A3" style="WIDTH: 1380px" cellspacing="0" sizcache="26" sizset="0">
 <tbody sizcache="26" sizset="0">
 <tr>
  <th style="FONT-SIZE: 15pt; BORDER-TOP: medium none; FONT-FAMILY: ����; BORDER-RIGHT: medium none; BORDER-BOTTOM: medium none; FONT-WEIGHT: 600; TEXT-ALIGN: left; BORDER-LEFT: medium none" colspan="15">&nbsp; </th>
 </tr>
 <tr id="insertadd">
  <th class="lign" style="WIDTH: 1%; DISPLAY: none" rowspan="2">��<br />��</th>
  <th class="lign aligh_sensc" style="FONT-SIZE: 11pt; WIDTH: 79px; PADDING-BOTTOM: 9px !important; PADDING-TOP: 9px !important; PADDING-LEFT: 9px !important; PADDING-RIGHT: 9px !important" rowspan="2">��&nbsp;�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 226px;text-align:center" rowspan="2">����ְ�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="2">��<br />�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="2">��<br />�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 51px" rowspan="2">���� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" colspan="2">ȫ���ƽ��� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" colspan="2">��ְ���� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 101px" rowspan="2">����<br />���� </th>
     <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;"  rowspan="2">�μӹ�<br>��ʱ�� </th>
     <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px" rowspan="2">�뵳<br />ʱ�� </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;" rowspan="2">����ְ<br />ʱ&nbsp;&nbsp;�� </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;" rowspan="2">����ְ<br />��ʱ�� </th>
  <th class="lign aligh_sensc" style="FONT-SIZE: 11pt; WIDTH: 80px;" rowspan="2">��&nbsp;ע </th>
 </tr>
 <tr>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 90px; TEXT-ALIGN: center; ">ѧ�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 100px;TEXT-ALIGN: center;  BACKGROUND-COLOR: white">��ҵԺУ<br />��&nbsp;ר&nbsp;ҵ </th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 90px; TEXT-ALIGN: center;">ѧ�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 100px; TEXT-ALIGN: center; BACKGROUND-COLOR: white">��ҵԺУ<br />��&nbsp;ר&nbsp;ҵ </th>
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
		 if(i==0){//ÿ��������,����Ϊ��һ��,�����ͷ�ͻ�������
			  
%> 
<table id="coordTable1" class="coordTable A3" style="WIDTH: 1380px" cellspacing="0" sizcache="26" sizset="0">
 <tbody sizcache="26" sizset="0">
 <tr>
  <th style="FONT-SIZE: 15pt; BORDER-TOP: medium none; FONT-FAMILY: ����; BORDER-RIGHT: medium none; BORDER-BOTTOM: medium none; FONT-WEIGHT: 600; TEXT-ALIGN: left; BORDER-LEFT: medium none" colspan="15">&nbsp; </th>
 </tr>
 <tr id="insertadd">
  <th class="lign" style="WIDTH: 1%; DISPLAY: none" rowspan="2">��<br />��</th>
  <th class="lign aligh_sensc" style="FONT-SIZE: 11pt; WIDTH: 79px; PADDING-BOTTOM: 9px !important; PADDING-TOP: 9px !important; PADDING-LEFT: 9px !important; PADDING-RIGHT: 9px !important" rowspan="2">��&nbsp;�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 226px;text-align:center" rowspan="2">����ְ�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="2">��<br />�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="2">��<br />�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 51px" rowspan="2">���� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" colspan="2">ȫ���ƽ��� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" colspan="2">��ְ���� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 101px" rowspan="2">����<br />���� </th>
     <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;"  rowspan="2">�μӹ�<br>��ʱ�� </th>
     <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px" rowspan="2">�뵳<br />ʱ�� </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;" rowspan="2">����ְ<br />ʱ&nbsp;&nbsp;�� </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;" rowspan="2">����ְ<br />��ʱ�� </th>
  <th class="lign aligh_sensc" style="FONT-SIZE: 11pt; WIDTH: 80px;" rowspan="2">��&nbsp;ע </th>
 </tr>
 <tr>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 90px; TEXT-ALIGN: center; ">ѧ�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 100px;TEXT-ALIGN: center;  BACKGROUND-COLOR: white">��ҵԺУ<br />��&nbsp;ר&nbsp;ҵ </th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 90px; TEXT-ALIGN: center;">ѧ�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 100px; TEXT-ALIGN: center; BACKGROUND-COLOR: white">��ҵԺУ<br />��&nbsp;ר&nbsp;ҵ </th>
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