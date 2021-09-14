<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>    
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
<odin:toolBar property="myToolBar1">
  <odin:buttonForToolBar text="增加" iconCls="add"  icon="../../basejs/ext/resources/images/default/dd/drop-no.gif" cls="x-btn-text-icon" tooltip="增加行数据"></odin:buttonForToolBar>
  <odin:separator />
  <odin:textForToolBar text="测试"></odin:textForToolBar>
  <odin:fill></odin:fill>
  <odin:separator />
  <odin:buttonForToolBar isLast="true" text="增加" iconCls="add" icon="../../basejs/ext/resources/images/default/dd/drop-no.gif" cls="x-btn-text-icon" tooltip="增加行数据"></odin:buttonForToolBar>
</odin:toolBar>
<div id="panel_content">
<odin:groupBox title="基本信息">
<table border="0" id="myform" align="center" width="100%"  cellpadding="0" cellspacing="0">
   <tr>
     <odin:textEdit property="worker0" label="工作"/> 
	 <odin:textEdit property="worker1" label="工作"/>
	 <odin:textEdit property="worker2" label="工作"/>
   </tr>
  <tr>
     <odin:textEdit property="dwbm" label="单位编码"/>
	 <odin:textEdit property="name" label="单位名称" colspan="4"/>
   </tr>
   <tr>
      <odin:textEdit property="xming" label="性命"/>
	  <odin:select property="zjlx" editor="true" data="['1', '身份证'],['2', '毕业证'],['3', '军官正'],['4', '其他证件']" label="证件类型" colspan="4"></odin:select>

   </tr>	 
 </table>
 </odin:groupBox>
 <odin:groupBox title="基本信息">
<table border="0" id="myform" width="100%"  cellpadding="0" cellspacing="0">
   <tr>
     <odin:textEdit property="worker10" label="工作"/> 
	 <odin:textEdit property="worker11" label="工作"/>
	 <odin:textEdit property="worker12" label="工作"/>  
   </tr>
  <tr>
     <odin:textEdit property="dwbm1" label="单位编码"/>
	 <odin:textEdit property="name1" label="单位名称" colspan="4"/>
   </tr>
   <tr>
      <odin:textEdit property="xming1" label="性命"/>
	  <odin:select property="zjlx1" editor="true" data="['1', '身份证'],['2', '毕业证'],['3', '军官正'],['4', '其他证件']" label="证件类型" colspan="4"></odin:select>
   </tr>	 
 </table>
 </odin:groupBox> 
</div>
<div id="content"></div>
<table>
<tr><td>
<odin:panel  property="mypanel" width="650" height="300" title="简单Panel" collapsible="true" contentEl="panel_content"/> 
</td><td><odin:panel topBarId="myToolBar1" width="150" height="300" property="mypanel2" title="简单Panel2" collapsible="true" contentEl="content"/> </td></tr>
</table>
</odin:base>
</body>
</html>