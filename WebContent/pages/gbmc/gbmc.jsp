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
  <th style="FONT-SIZE: 15pt; BORDER-TOP: medium none; FONT-FAMILY: ����; BORDER-RIGHT: medium none; BORDER-BOTTOM: medium none; FONT-WEIGHT: 600; TEXT-ALIGN: left; BORDER-LEFT: medium none" colspan="15">&nbsp; </th>
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
  <th class="lign" style="WIDTH: 1%; DISPLAY: none" rowspan="2">��<br />��</th>
  <th class="lign aligh_sensc" style="FONT-SIZE: 11pt; WIDTH: 79px; PADDING-BOTTOM: 9px !important; PADDING-TOP: 9px !important; PADDING-LEFT: 9px !important; PADDING-RIGHT: 9px !important" rowspan="2">��&nbsp;�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 226px;text-align:center" rowspan="2">����ְ�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="2">��<br />�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="2">��<br />�� </th>
  
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 101px" rowspan="2">����<br />���� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;TEXT-ALIGN: center;" rowspan="2">��&nbsp;�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;"  rowspan="2">�μӹ�<br>��ʱ�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px" rowspan="2">�뵳<br />ʱ�� </th>
  
  
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" colspan="2">ȫ���ƽ��� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" colspan="2">��ְ���� </th>
  
     
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" rowspan="2">ְ�� </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;" rowspan="2">����ְ<br />ʱ&nbsp;&nbsp;�� </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 154px;" rowspan="2">����ְ��<br />���ʱ�� </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;" rowspan="2">����ְ<br />��ʱ�� </th>
  <th class="lign aligh_sensc" style="FONT-SIZE: 11pt; WIDTH: 80px;" rowspan="2">��&nbsp;ע </th>
 </tr>
 <tr>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 79px; TEXT-ALIGN: center; ">ѧ��ѧλ</th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 104px;TEXT-ALIGN: center;  BACKGROUND-COLOR: white">��ҵԺУ<br />��&nbsp;ר&nbsp;ҵ </th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 79px; TEXT-ALIGN: center;">ѧ��ѧλ</th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 104px; TEXT-ALIGN: center; BACKGROUND-COLOR: white">��ҵԺУ<br />��&nbsp;ר&nbsp;ҵ </th>
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
	Integer rows = 5; //�̶�����
	Integer index = 0;//ҳ��
	  
	    //�õ�λ��ҳ��
	    int index1 = 0;
		  for (int i = 0 ; i<list.size() ; i++) {
			  Gbmc gbmc = list.get(i);
			  if(i+1>rows*index1||i==0){//ÿ��������,����Ϊÿ�������ĵ�һ��,�����ͷ�ͻ�������
				  index +=1;
				  index1 +=1;
%>
<table id="coordTable1" class="coordTable A3" style="WIDTH: 1380px" cellspacing="0" sizcache="26" sizset="0">
 <tbody sizcache="26" sizset="0">
<!--��������  -->
 <tr>
<%--   <th style="FONT-SIZE: 15pt; BORDER-TOP: medium none; FONT-FAMILY: ����; BORDER-RIGHT: medium none; BORDER-BOTTOM: medium none; FONT-WEIGHT: 600; TEXT-ALIGN: left; BORDER-LEFT: medium none" colspan="15"><%=dept %></th>
 </tr> --%>
<!--��ͷ  -->
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
  <!-- <th class="lign" style="WIDTH: 1%; DISPLAY: none" rowspan="2">��<br />��</th>
  <th class="lign aligh_sensc" style="FONT-SIZE: 11pt;  PADDING-BOTTOM: 9px !important; PADDING-TOP: 9px !important; PADDING-LEFT: 9px !important; PADDING-RIGHT: 9px !important" rowspan="2">��&nbsp;�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 226px; text-align:center" rowspan="2">����ְ�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px;" rowspan="2">��<br />�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px;" rowspan="2">��<br />�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 51px;" rowspan="2" style="word-break:keep-all;">���� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" colspan="2">ȫ���ƽ��� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" colspan="2">��ְ���� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 101px" rowspan="2">����<br />���� </th>
     <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;"  rowspan="2">�μӹ�<br>��ʱ�� </th>
     <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;" rowspan="2">�뵳<br />ʱ�� </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;" rowspan="2">����ְ<br />ʱ&nbsp;&nbsp;�� </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;" rowspan="2">����ְ<br />��ʱ�� </th>
  <th class="lign aligh_sensc" style="FONT-SIZE: 11pt; WIDTH: 80px;" rowspan="2">��&nbsp;ע </th> -->
  
  <th class="lign" style="WIDTH: 1%; DISPLAY: none" rowspan="2">��<br />��</th>
  <th class="lign aligh_sensc" style="FONT-SIZE: 11pt; WIDTH: 79px; PADDING-BOTTOM: 9px !important; PADDING-TOP: 9px !important; PADDING-LEFT: 9px !important; PADDING-RIGHT: 9px !important" rowspan="2">��&nbsp;�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 226px;text-align:center" rowspan="2">����ְ�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="2">��<br />�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="2">��<br />�� </th>
  
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 101px" rowspan="2">����<br />���� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;TEXT-ALIGN: center;"  rowspan="2">��&nbsp;�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;"  rowspan="2">�μӹ�<br>��ʱ�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px" rowspan="2">�뵳<br />ʱ�� </th>
  
  
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" colspan="2">ȫ���ƽ��� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" colspan="2">��ְ���� </th>
  
     
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important" rowspan="2">ְ�� </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;" rowspan="2">����ְ<br />ʱ&nbsp;&nbsp;�� </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 154px;" rowspan="2">����ְ��<br />���ʱ�� </th>
  <th style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 104px;" rowspan="2">����ְ<br />��ʱ�� </th>
  <th class="lign aligh_sensc" style="FONT-SIZE: 11pt; WIDTH: 80px;" rowspan="2">��&nbsp;ע </th>
 </tr>
 
 <tr>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 104px; TEXT-ALIGN: center; ">ѧ��ѧλ </th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 104px; TEXT-ALIGN: center;  BACKGROUND-COLOR: white">��ҵԺУ<br />��&nbsp;ר&nbsp;ҵ </th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 104px; TEXT-ALIGN: center;">ѧ��ѧλ</th>
  <th class="lign" style="FONT-SIZE: 11pt; WIDTH: 104px; TEXT-ALIGN: center; BACKGROUND-COLOR: white">��ҵԺУ<br />��&nbsp;ר&nbsp;ҵ </th>
 </tr>
<%	
			  }
			  //��������
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
    	 //���ѭ������ĩβ,�ж��Ƿ�����̶�����,����������
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
				//�����в���ҳ��
%>
 </tbody>
</table>
<div id='br_"+<%=index.toString() %>+"' class='noprint' style='text-align:center;valign:center' ><br><br><span>..........................................����(<%=index.toString() %>)ҳ��..........................................</span><br><br></div>
<%
			  }
	   }

}
%>
</body>
</html>


