<%@page import="com.insigma.siis.local.pagemodel.gbmc.TPBPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SydwSysOrgPageModel"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/gbmc/bzzs.css">
<%
	String UNITID = (String) session.getAttribute("UNITID");
	List<HashMap<String, Object>> listCode = new ArrayList<HashMap<String, Object>>();
	if(UNITID != null && !UNITID.equals("")) {
	listCode = (List<HashMap<String, Object>>) SydwSysOrgPageModel.querybyid_Bzzs(UNITID);
	}
%>
<html>
<head>
 <meta charset="utf-8">
</head>
<body>
<div style="width:1230px; height:470px; overflow:scroll; ">
<%
	if(listCode.size()<1){
		%>
		<table id="coordTable1" class="coordTable A3" style="table-layout: fixed;WIDTH: 1380px" cellspacing="0" sizcache="26" sizset="0">
		 <tbody sizcache="26" sizset="0">
		 <tr id="insertadd">
		  <th class="lign" style="WIDTH: 1%; DISPLAY: none" rowspan="3">��<br />��</th>
		  <th class="lign aligh_sensc" style="FONT-SIZE: 11pt; WIDTH: 120px; PADDING-BOTTOM: 9px !important; PADDING-TOP: 9px !important; PADDING-LEFT: 9px !important; PADDING-RIGHT: 9px !important" rowspan="3">��������</th>
		  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 80px;text-align:center" rowspan="3">��׼�ĺ� </th>
		  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 80px" rowspan="3">����</th>
		  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 60px" rowspan="3">������</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 90px" colspan="3">������ʽ</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 120px" colspan="4">��ҵ���ƽṹ</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 180px" colspan="6">��λ�쵼ְ�� </th>
		  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 30px" rowspan="3">��<br/>��<br/>��<br/>��<br/>ְ<br/>�� </th>
		  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="3">��׼<br/>����<br/>����<br/>��</th>
		  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="3">��׼<br/>����<br/>����<br/>����<br/>�� </th>
		  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="3">����<br/>����<br/>�쵼<br/>��ְ</th>
		  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="3">����<br/>����<br/>�쵼<br/>��ְ</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 160px" colspan="5">�ͼ����쵼ְ��</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 240px" colspan="8">���쵼ְ��</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 390px" colspan="13">�ۺϹ����๫��Աְ��</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 60px" colspan="2">ʡ����</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 60px" colspan="2">���ּ�</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 60px" colspan="2">�ش���</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 60px" colspan="2">��Ƽ�</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 60px" colspan="2">�ɼ�</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px" rowspan="3">����<br/>ְ��</th>
		 </tr>
		 <tr>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">ȫ<br/>��<br/>��<br/>��</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��<br/>��<br/>��</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��<br/>��<br/>��</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��<br/>��<br/>Ա</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">ר<br/>ҵ<br/>��<br/>��<br/>��Ա</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��<br/>��<br/>Ա</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��<br/>��<br/>��</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ί<br/>��<br/>��</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��<br/>��<br/>ְ<br/>��<br/>��<br/></th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ί<br/>��<br/>��<br/>�� </th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��<br/>��<br/>ְ<br/>��<br/>��</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">ר<br/>ҵ<br/>��<br/>��<br/>��<br/>��</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 60px; " rowspan="2">�ͼ�<br/>�鳤<br/>����<br/>ί��<br/>�ǡ�<br/>רְ<br/>�ͼ�<br/>���<br/>Ա</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">�ͼ�<br/>����<br/>����<br/>��ί<br/>����<br/>��</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��<br/>��<br/>��<br/>��</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��<br/>��<br/>��</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��<br/>��<br/>��<br/>��</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">����<br/>����<br/>����<br/>��</th>
		  <th style="FONT-SIZE: 11pt; TEXT-ALIGN: center; HEIGHT:12px; line-height:28px;"  colspan="6">����(��ְ��)</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">һ��<br/>Ѳ��<br/>Ա</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">����<br/>Ѳ��<br/>Ա</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">һ��<br/>����<br/>Ա</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">����<br/>����<br/>Ա</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">����<br/>����<br/>Ա</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">�ļ�<br/>����<br/>Ա</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">һ��<br/>����<br/>��Ա</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">����<br/>����<br/>��Ա</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">����<br/>����<br/>��Ա</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">�ļ�<br/>����<br/>��Ա</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">һ��<br/>��Ա</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">����<br/>��Ա</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ְ</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ְ</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ְ</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ְ</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ְ</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ְ</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ְ</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ְ</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ְ</th>
		  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ְ</th>
		 </tr> 
		 <tr>
		    <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 25px;" >Ѳ<br/>��<br/>Ա</th>
		    <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 25px;">��<br/>Ѳ<br/>��<br/>Ա</th>
		    <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 25px;">��<br/>��<br/>Ա</th>
		    <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 25px;">��<br/>��<br/>��<br/>Ա</th>
		    <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 25px;">��<br/>��<br/>��<br/>Ա</th>
		    <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 25px;">��<br/>��<br/>��<br/>��<br/>Ա</th>
		 </tr>	
	


<%
	}
	else{ 
		for(int i = 0 ; i<listCode.size() ; i++){
		 HashMap<String,Object> map = listCode.get(i);	
		 if(i==0){
		 
%>
<table id="coordTable1" class="coordTable A3" style="table-layout: fixed;WIDTH: 1380px" cellspacing="0" sizcache="26" sizset="0">
 <tbody sizcache="26" sizset="0">
 <tr id="insertadd">
  <th class="lign" style="WIDTH: 1%; DISPLAY: none" rowspan="3">��<br />��</th>
  <th class="lign aligh_sensc" style="FONT-SIZE: 11pt; WIDTH: 120px; PADDING-BOTTOM: 9px !important; PADDING-TOP: 9px !important; PADDING-LEFT: 9px !important; PADDING-RIGHT: 9px !important" rowspan="3">��������</th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 80px;text-align:center" rowspan="3">��׼�ĺ� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 80px" rowspan="3">����</th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 60px" rowspan="3">������</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 90px" colspan="3">������ʽ</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 120px" colspan="4">��ҵ���ƽṹ</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 180px" colspan="6">��λ�쵼ְ�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 30px" rowspan="3">��<br/>��<br/>��<br/>��<br/>ְ<br/>�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="3">��׼<br/>����<br/>����<br/>��</th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="3">��׼<br/>����<br/>����<br/>����<br/>�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="3">����<br/>����<br/>�쵼<br/>��ְ</th>
  <th class="lign" style="FONT-SIZE: 11pt; HEIGHT: 50px !important; WIDTH: 35px" rowspan="3">����<br/>����<br/>�쵼<br/>��ְ</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 160px" colspan="5">�ͼ����쵼ְ��</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 240px" colspan="8">���쵼ְ��</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 390px" colspan="13">�ۺϹ����๫��Աְ��</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 60px" colspan="2">ʡ����</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 60px" colspan="2">���ּ�</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 60px" colspan="2">�ش���</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 60px" colspan="2">��Ƽ�</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 60px" colspan="2">�ɼ�</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px" rowspan="3">����<br/>ְ��</th>
 </tr>
 <tr>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">ȫ<br/>��<br/>��<br/>��</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��<br/>��<br/>��</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��<br/>��<br/>��</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��<br/>��<br/>Ա</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">ר<br/>ҵ<br/>��<br/>��<br/>��Ա</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��<br/>��<br/>Ա</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��<br/>��<br/>��</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ί<br/>��<br/>��</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��<br/>��<br/>ְ<br/>��<br/>��<br/></th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ί<br/>��<br/>��<br/>�� </th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��<br/>��<br/>ְ<br/>��<br/>��</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">ר<br/>ҵ<br/>��<br/>��<br/>��<br/>��</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 60px; " rowspan="2">�ͼ�<br/>�鳤<br/>����<br/>ί��<br/>�ǡ�<br/>רְ<br/>�ͼ�<br/>���<br/>Ա</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">�ͼ�<br/>����<br/>����<br/>��ί<br/>����<br/>��</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��<br/>��<br/>��<br/>��</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��<br/>��<br/>��</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��<br/>��<br/>��<br/>��</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">����<br/>����<br/>����<br/>��</th>
  <th style="FONT-SIZE: 11pt; TEXT-ALIGN: center; HEIGHT:12px;line-height:28px;"  colspan="6" >����(��ְ��)</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">һ��<br/>Ѳ��<br/>Ա</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">����<br/>Ѳ��<br/>Ա</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">һ��<br/>����<br/>Ա</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">����<br/>����<br/>Ա</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">����<br/>����<br/>Ա</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">�ļ�<br/>����<br/>Ա</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">һ��<br/>����<br/>��Ա</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">����<br/>����<br/>��Ա</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">����<br/>����<br/>��Ա</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">�ļ�<br/>����<br/>��Ա</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">һ��<br/>��Ա</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">����<br/>��Ա</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>��</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ְ</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ְ</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ְ</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ְ</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ְ</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ְ</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ְ</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ְ</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ְ</th>
  <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 30px;"  rowspan="2">��<br/>ְ</th>
 </tr> 
 <tr>
    <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 25px;" >Ѳ<br/>��<br/>Ա</th>
    <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 25px;">��<br/>Ѳ<br/>��<br/>Ա</th>
    <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 25px;">��<br/>��<br/>Ա</th>
    <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 25px;">��<br/>��<br/>��<br/>Ա</th>
    <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 25px;">��<br/>��<br/>��<br/>Ա</th>
    <th class="lign" style="FONT-SIZE: 11pt; TEXT-ALIGN: center; WIDTH: 25px;">��<br/>��<br/>��<br/>��<br/>Ա</th>
 </tr>
 <%
		}
 %>
 <tr class="data" sizcache="62" sizset="0">
 <td class="rownum default_color aligh_sensc" style="DISPLAY: none" nodeindex="1">1</td>
  <td class="default_color " sizcache="61" sizset="2" nodeindex="2">
  <%=(String)map.get("b0101")==null?"":(String)map.get("b0101") %>
  </td>
  <td class="default_color " sizcache="61" sizset="3" nodeindex="3">
  <%=(String)map.get("spwnum")==null?"":(String)map.get("spwnum") %>
  </td>
  <td class="default_color " sizcache="61" sizset="4" nodeindex="4">
  <%=(String)map.get("updatetime")==null?"":(String)map.get("updatetime") %>
  </td>
  <td class="default_color " sizcache="61" sizset="5" nodeindex="5">
  <%=(String)map.get("s1")==null?"":(String)map.get("s1") %>
  </td>
  <td class="default_color " sizcache="61" sizset="6" nodeindex="6">
  <%=(String)map.get("s62")==null?"":(String)map.get("s62") %>
  </td>
  <td class="default_color " sizcache="61" sizset="7" nodeindex="7">
  <%=(String)map.get("s63")==null?"":(String)map.get("s63") %>
  </td>
  <td class="default_color " sizcache="61" sizset="8" nodeindex="8">
  <%=(String)map.get("s64")==null?"":(String)map.get("s64") %>
  </td>
  <td class="default_color " sizcache="61" sizset="9" nodeindex="9">
  <%=(String)map.get("s84")==null?"":(String)map.get("s84") %>
  </td>
  <td class="default_color " sizcache="61" sizset="10" nodeindex="10">
  <%=(String)map.get("s85")==null?"":(String)map.get("s85") %>
  </td>
  <td class="default_color " sizcache="61" sizset="11" nodeindex="11">
  <%=(String)map.get("s86")==null?"":(String)map.get("s86") %>
  </td>
  <td class="default_color " sizcache="61" sizset="12" nodeindex="12">
  <%=(String)map.get("s87")==null?"":(String)map.get("s87") %>
  </td>
  <td class="default_color " sizcache="61" sizset="13" nodeindex="13">
  <%=(String)map.get("sum1")==null?"":(String)map.get("sum1") %><!-- �ϼ� -->
  </td>
  <td class="default_color " sizcache="61" sizset="14" nodeindex="14">
  <%=(String)map.get("s23")==null?"":(String)map.get("s23") %>
  </td>
  <td class="default_color " sizcache="61" sizset="15" nodeindex="15">
  <%=(String)map.get("s20")==null?"":(String)map.get("s20") %><!-- ������ְ�쵼 -->
  </td>
  <td class="default_color " sizcache="61" sizset="16" nodeindex="16">
  <%=(String)map.get("s24")==null?"":(String)map.get("s24") %>
  </td>
  <td class="default_color " sizcache="61" sizset="17" nodeindex="17">
  <%=(String)map.get("s21")==null?"":(String)map.get("s21") %><!-- ������ְ�쵼 -->
  </td>
  <td class="default_color " sizcache="61" sizset="18" nodeindex="18">
  <%=(String)map.get("s25")==null?"":(String)map.get("s25") %>
  </td>
  <td class="default_color " sizcache="61" sizset="19" nodeindex="19">
  <%=(String)map.get("s27")==null?"":(String)map.get("s27") %>
  </td>
  <td class="default_color " sizcache="61" sizset="20" nodeindex="20">
  <%=(String)map.get("s66")==null?"":(String)map.get("s66") %>
  </td>
  <td class="default_color " sizcache="61" sizset="21" nodeindex="21">
  <%=(String)map.get("s74")==null?"":(String)map.get("s74") %>
  </td>
  <td class="default_color " sizcache="61" sizset="22" nodeindex="22">
  <%=(String)map.get("s35")==null?"":(String)map.get("s35") %>
  </td>
  <td class="default_color " sizcache="61" sizset="23" nodeindex="23">
  <%=(String)map.get("s73")==null?"":(String)map.get("s73") %>
  </td>
  <td class="default_color " sizcache="61" sizset="24" nodeindex="24">
  <%=(String)map.get("sum2")==null?"":(String)map.get("sum2") %><!-- �ϼ� -->
  </td>
  <td class="default_color " sizcache="61" sizset="25" nodeindex="25">
  <%=(String)map.get("sum3")==null?"":(String)map.get("sum3") %><!-- �ͼ��鳤����ί��� -->
  </td>
  <td class="default_color " sizcache="61" sizset="26" nodeindex="26">
  <%=(String)map.get("sum4")==null?"":(String)map.get("sum4") %><!-- �ͼ츱�鳤����ί����� -->
  </td>
  <td class="default_color " sizcache="61" sizset="27" nodeindex="27">
  <%=(String)map.get("s33")==null?"":(String)map.get("s33") %><!-- ��������� -->
  </td>
  <td class="default_color " sizcache="61" sizset="28" nodeindex="28">
  <%=(String)map.get("s34")==null?"":(String)map.get("s34") %>
  </td>
  <td class="default_color " sizcache="61" sizset="29" nodeindex="29">
  <%=(String)map.get("s68")==null?"":(String)map.get("s68") %><!-- ���ŷ��쵼 -->
  </td>
  <td class="default_color " sizcache="61" sizset="30" nodeindex="30">
  <%=(String)map.get("s36")==null?"":(String)map.get("s36") %>
  </td>
  <td class="default_color " sizcache="61" sizset="31" nodeindex="31">
  <%=(String)map.get("s90")==null?"":(String)map.get("s90") %>
  </td>
  <td class="default_color " sizcache="61" sizset="32" nodeindex="32">
  <%=(String)map.get("s91")==null?"":(String)map.get("s91") %>
  </td>
  <td class="default_color " sizcache="61" sizset="33" nodeindex="33">
  <%=(String)map.get("s92")==null?"":(String)map.get("s92") %>
  </td>
  <td class="default_color " sizcache="61" sizset="34" nodeindex="34">
  <%=(String)map.get("s93")==null?"":(String)map.get("s93") %>
  </td>
  <td class="default_color " sizcache="61" sizset="35" nodeindex="35">
  <%=(String)map.get("s94")==null?"":(String)map.get("s94") %>
  </td>
  <td class="default_color " sizcache="61" sizset="36" nodeindex="36">
  <%=(String)map.get("s95")==null?"":(String)map.get("s95") %><!-- �����ο�Ա -->
  </td>
  <td class="default_color " sizcache="61" sizset="37" nodeindex="37">
  <%=(String)map.get("s113")==null?"":(String)map.get("s113") %><!-- һ��Ѳ��Ա -->
  </td>
  <td class="default_color " sizcache="61" sizset="38" nodeindex="38">
  <%=(String)map.get("s114")==null?"":(String)map.get("s114") %>
  </td>
  <td class="default_color " sizcache="61" sizset="39" nodeindex="39">
  <%=(String)map.get("s115")==null?"":(String)map.get("s115") %>
  </td>
  <td class="default_color " sizcache="61" sizset="40" nodeindex="40">
  <%=(String)map.get("s116")==null?"":(String)map.get("s116") %>
  </td>
  <td class="default_color " sizcache="61" sizset="41" nodeindex="41">
  <%=(String)map.get("s117")==null?"":(String)map.get("s117") %>
  </td>
  <td class="default_color " sizcache="61" sizset="42" nodeindex="42">
  <%=(String)map.get("s118")==null?"":(String)map.get("s118") %>
  </td>
  <td class="default_color " sizcache="61" sizset="43" nodeindex="43">
  <%=(String)map.get("s119")==null?"":(String)map.get("s119") %>
  </td>
  <td class="default_color " sizcache="61" sizset="44" nodeindex="44">
  <%=(String)map.get("s120")==null?"":(String)map.get("s120") %>
  </td>
  <td class="default_color " sizcache="61" sizset="45" nodeindex="45">
  <%=(String)map.get("s121")==null?"":(String)map.get("s121") %>
  </td>
  <td class="default_color " sizcache="61" sizset="46" nodeindex="46">
  <%=(String)map.get("s122")==null?"":(String)map.get("s122") %>
  </td>
  <td class="default_color " sizcache="61" sizset="47" nodeindex="47">
  <%=(String)map.get("s123")==null?"":(String)map.get("s123") %>
  </td>
  <td class="default_color " sizcache="61" sizset="48" nodeindex="48">
  <%=(String)map.get("s124")==null?"":(String)map.get("s124") %>
  </td>
  <td class="default_color " sizcache="61" sizset="49" nodeindex="49">
  <%=(String)map.get("s125")==null?"":(String)map.get("s125") %>
  </td>
  <td class="default_color " sizcache="61" sizset="50" nodeindex="50">
  <%=(String)map.get("s96")==null?"":(String)map.get("s96") %>
  </td>
  <td class="default_color " sizcache="61" sizset="51" nodeindex="51">
  <%=(String)map.get("s97")==null?"":(String)map.get("s97") %>
  </td>
  <td class="default_color " sizcache="61" sizset="52" nodeindex="52">
  <%=(String)map.get("s98")==null?"":(String)map.get("s98") %>
  </td>
  <td class="default_color " sizcache="61" sizset="53" nodeindex="53">
  <%=(String)map.get("s99")==null?"":(String)map.get("s99") %>
  </td>
  <td class="default_color " sizcache="61" sizset="54" nodeindex="54">
  <%=(String)map.get("s100")==null?"":(String)map.get("s100") %>
  </td>
  <td class="default_color " sizcache="61" sizset="55" nodeindex="55">
  <%=(String)map.get("s101")==null?"":(String)map.get("s101") %>
  </td>
  <td class="default_color " sizcache="61" sizset="56" nodeindex="56">
  <%=(String)map.get("s102")==null?"":(String)map.get("s102") %>
  </td>
  <td class="default_color " sizcache="61" sizset="57" nodeindex="57">
  <%=(String)map.get("s103")==null?"":(String)map.get("s103") %>
  </td>
  <td class="default_color " sizcache="61" sizset="58" nodeindex="58">
  <%=(String)map.get("s104")==null?"":(String)map.get("s104") %>
  </td>
  <td class="default_color " sizcache="61" sizset="59" nodeindex="59">
  <%=(String)map.get("s105")==null?"":(String)map.get("s105") %>
  </td>
  <td class="default_color " sizcache="61" sizset="60" nodeindex="60">
  <%=(String)map.get("s110")==null?"":(String)map.get("s110") %><!-- ����ְ�� -->
  </td>
 </tr>
 <% 
 	if(i==listCode.size()-1){
 %>
 </tbody>
 </table>
 <%
 	}
 	}
	}
 %>
 </div>
</body>
</html>