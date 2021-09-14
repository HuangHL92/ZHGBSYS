<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%
	String ctxpath = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Insert title here</title>
<odin:head/>
</head>
<body>
<odin:base>
<script>
		      function doSave()
			  {
			      alert('按钮单击事件触发！');     
			  }
</script>


<table width="98%" align="center">
<tr><td>
<odin:groupBox title="基本信息">
<table border="0" id="myform" align="center" width="100%"  cellpadding="0" cellspacing="0">
<odin:tabLayOut />
<tr>
     <odin:textEdit property="worker0" label="工作"/>   
	 <odin:numberEdit decimalPrecision="2" property="worker1" label="工作"/>
	 <odin:textEdit property="worker2" label="工作"/>
   </tr>
   <tr>
     <odin:textEdit property="dwbm1" label="单位编码" required="true"/>
	 <odin:textEdit property="name1" label="单位名称" colspan="4"/>
   </tr>
   <tr>
      <odin:textEdit property="xming1" label="姓名" />   
	  <odin:select property="zjlx1" editor="true" data="['1', '身份证'],['2', '毕业证'],['3', '军官正'],['4', '其他证件']" label="证件类型" colspan="4"></odin:select>
   </tr>
</table>
</odin:groupBox> 
</td>
</tr>
</table>
<div style="padding-left:10px;">
<odin:tab id="tab">
   <odin:tabModel>
       <odin:tabItem title="基本信息" id="tab1"></odin:tabItem>
       <odin:tabItem title="相关信息" id="tab2" isLast="true"></odin:tabItem>
   </odin:tabModel>
   <odin:tabCont itemIndex="tab1" className="tab">
       <table border="0" id="myform" align="center" width="100%"  cellpadding="0" cellspacing="0">
       <odin:tabLayOut />
       <tr>
     <odin:textEdit property="worker0r" label="工作"/>   
	 <odin:textEdit property="worker1r" label="工作"/>
	 <odin:textEdit property="worker2r" label="工作"/>
   </tr>
   <tr>
     <odin:textEdit property="dwbm1r" label="单位编码" required="true"/>
	 <odin:textEdit property="name1r" label="单位名称"/>
	 <odin:textIconEdit property="dwxx" label="单位信息" iconId="btn"></odin:textIconEdit>
   </tr>
   <tr>
      <odin:textEdit property="xming1r" label="姓名" />   
	  <odin:select property="zjlx1r" editor="true" data="['1', '身份证'],['2', '毕业证'],['3', '军官正'],['4', '其他证件']" label="证件类型"></odin:select>
   </tr>
   </table>
   </odin:tabCont>
   <odin:tabCont itemIndex="tab2">
      <table><tr><td><div id="gridDiv_grid1"></div></td></tr></table>
   </odin:tabCont>  
</odin:tab>
</div>
<odin:grid property="grid1" applyTo="gridDiv_grid1"  title="我的表格" width="738" height="400">
<odin:gridDataModel>
  <odin:gridDataCol name="company" />
  <odin:gridDataCol name="price" type="float"/>
  <odin:gridDataCol name="change" type="float"/>   
  <odin:gridDataCol name="pctChange" type="float"/>
  <odin:gridDataCol name="lastChange" isLast="true"/>
</odin:gridDataModel>
<odin:gridColumnModel>
  <odin:gridColumn id="yes" header="公司" width="160" dataIndex="company" />
  <odin:gridColumn  header="价格" dataIndex="price" />  
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  dataIndex="lastChange" isLast="true"/>
</odin:gridColumnModel>                                  
<odin:griddata>
        ['3m Co',71.72,0.02,0.03,'9/1 12:00am'],
        ['Alcoa Inc',29.01,0.42,1.47,'9/1 12:00am'],
        ['Altria Group Inc',83.81,0.28,0.34,'9/1 12:00am'],
        ['American Express Company',52.55,0.01,0.02,'9/1 12:00am'],
        ['American International Group, Inc.',64.13,0.31,0.49,'9/1 12:00am'],
        ['AT&T Inc.',31.61,-0.48,-1.54,'9/1 12:00am'],
        ['Boeing Co.',75.43,0.53,0.71,'9/1 12:00am'],
        ['Caterpillar Inc.',67.27,0.92,1.39,'9/1 12:00am'],
        ['Citigroup, Inc.',49.37,0.02,0.04,'9/1 12:00am'],
        ['E.I. du Pont de Nemours and Company',40.48,0.51,1.28,'9/1 12:00am'],
        ['Exxon Mobil Corp',68.1,-0.43,-0.64,'9/1 12:00am'],
        ['General Electric Company',34.14,-0.08,-0.23,'9/1 12:00am'],
        ['General Motors Corporation',30.27,1.09,3.74,'9/1 12:00am'],
        ['Hewlett-Packard Co.',36.53,-0.03,-0.08,'9/1 12:00am'],
        ['Honeywell Intl Inc',38.77,0.05,0.13,'9/1 12:00am'],
        ['Intel Corporation',19.88,0.31,1.58,'9/1 12:00am'],
        ['International Business Machines',81.41,0.44,0.54,'9/1 12:00am'],
        ['Johnson & Johnson',64.72,0.06,0.09,'9/1 12:00am'],
        ['JP Morgan & Chase & Co',45.73,0.07,0.15,'9/1 12:00am'],
        ['McDonald\'s Corporation',36.76,0.86,2.40,'9/1 12:00am'],
        ['Merck & Co., Inc.',40.96,0.41,1.01,'9/1 12:00am'],
        ['Microsoft Corporation',25.84,0.14,0.54,'9/1 12:00am'],
        ['Pfizer Inc',27.96,0.4,1.45,'9/1 12:00am'],
        ['The Coca-Cola Company',45.07,0.26,0.58,'9/1 12:00am'],
        ['The Home Depot, Inc.',34.64,0.35,1.02,'9/1 12:00am'],
        ['The Procter & Gamble Company',61.91,0.01,0.02,'9/1 12:00am'],
        ['United Technologies Corporation',63.26,0.55,0.88,'9/1 12:00am'],
        ['Verizon Communications',35.57,0.39,1.11,'9/1 12:00am'],
        ['Wal-Mart Stores, Inc.',45.45,0.73,1.63,'9/1 12:00am']
</odin:griddata>		
</odin:grid>
<table border="0" id="myform" align="center" width="100%"  cellpadding="0" cellspacing="0">
   <tr>
    <td colspan="4" height="40" width="600"></td>
    <td align="center"><odin:button text="保存" handler ="doSave" /></td>
    <td align="left"><odin:button text="取消" handler ="doSave" /></td>
   <tr>
</table>  

<odin:window buttonId="btn" id="window" src="/samples/tag/propertyGridTag.jsp"></odin:window>

</odin:base>
</body>
</html>