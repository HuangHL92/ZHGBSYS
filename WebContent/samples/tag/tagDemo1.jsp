<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%
	String ctxpath = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>常见布局1</title>
<odin:head/>
</head>
<body>

<odin:base>
<p>&nbsp</p>

<odin:simplePanel title="简单form" width="807" bodyWidth="777">
<odin:groupBox title="基本信息">
<table border="0" id="myform" align="center" width="100%"  cellpadding="0" cellspacing="0">
   <tr>
     <odin:textEdit property="worker0" label="工作" size="20"/> 
	 <odin:textEdit property="worker1" label="工作" size="20"/>
	 <odin:textEdit property="worker2" label="工作" size="20"/>
   </tr>
  <tr>
     <odin:textEdit property="dwbm" label="单位编码" size="20"/>
	 <odin:textEdit property="name" label="单位名称" colspan="4" size="40"/>
   </tr>
   <tr>
      <odin:textEdit property="xming" label="性命" size="20"/>
	  <odin:select property="zjlx" editor="true" data="['1', '身份证'],['2', '毕业证'],['3', '军官正'],['4', '其他证件']" label="证件类型" colspan="4"></odin:select>

   </tr>	 
 </table>
 </odin:groupBox>
 <br><br>
 <odin:groupBox title="基本信息">
<table border="0" id="myform" width="100%"  cellpadding="0" cellspacing="0">
   <tr>
     <odin:textEdit property="worker10" label="工作"/> 
	 <odin:textEdit property="worker11" label="工作"/>
	 <odin:textEdit property="worker12" label="工作"/>  
   </tr>
  <tr>
     <odin:textEdit property="dwbm1" label="单位编码" size="20"/>
	 <odin:textEdit property="name1" label="单位名称" colspan="4" size="50"/>
   </tr>
   <tr>
      <odin:textEdit property="xming1" label="性命" size="20"/>
	  <odin:select property="zjlx1" editor="true" data="['1', '身份证'],['2', '毕业证'],['3', '军官正'],['4', '其他证件']" label="证件类型" colspan="4"></odin:select>

   </tr>	 
 </table>
 </odin:groupBox> 
 <br><br>
 <table border="0" id="myform" width="100%" align="center"  cellpadding="0" cellspacing="0">
   <tr>
     <odin:textEdit property="worker110" label="工作" size="20"/> 
	 <odin:textEdit property="worker111" label="工作" size="20"/>
	 <odin:dateEdit property="aab1007" size="15" label="单位名称" ></odin:dateEdit>
   </tr>
  <tr>
     <odin:textEdit property="dwbm11" label="单位编码" size="20"/>
	 <odin:textEdit property="name11" label="单位名称" colspan="4" size="50"/>
   </tr>
   <tr>
      <odin:textEdit property="xming11" label="性命" size="20"/>
	  <odin:select property="zjlx11" editor="true" data="['1', '身份证'],['2', '毕业证'],['3', '军官正'],['4', '其他证件']" label="证件类型"></odin:select>
	  <td nowrap="nowrap"><odin:checkbox property="workplace" label="工作地点1" /></td>
	  <td>
	  <odin:checkbox property="workplace2" label="工作地点2" />
	  </td>

   </tr>	 
 </table>   
</odin:simplePanel> 

<table>
  <tr>
   <odin:select property="aac009" editor="true" label="单位名称" colspan="4" size="80">
['1', '身份证'],['2', '毕业证'],['3', '军官正'],['4', '其他证件']
   </odin:select>
<odin:numberEdit property="aab010" required="true" decimalPrecision="2" label="单位名称" size="30"></odin:numberEdit>
 </tr>
 <tr>
   <odin:textarea property="aab011" label="单位名称" value="var data = Ext.getCmp('grid1').store.data;
   alert(data.itemAt(0).get('company'));
   alert(data.itemAt(0).get('price'));
   alert(data.itemAt(0).get('change'));
   alert(data.itemAt(0).get('lastChange'));
   alert(data.length);

alert(Ext.getCmp('grid6').store.data.length);
alert(Ext.getCmp('grid6').store.data.itemAt(0).get('price'));

Ext.getCmp('grid6').store.data.itemAt(0).set('price',40);" colspan="5" cols="80" rows="10">

    </odin:textarea>
 </tr>
</table>
</odin:base>  
</body>
</html>