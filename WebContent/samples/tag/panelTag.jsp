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
	alert('��ť�����¼�������');     
}
</script>
<odin:toolBar property="myToolBar1">
  <odin:buttonForToolBar text="����" iconCls="add"  icon="../../basejs/ext/resources/images/default/dd/drop-no.gif" cls="x-btn-text-icon" tooltip="����������"></odin:buttonForToolBar>
  <odin:separator />
  <odin:textForToolBar text="����"></odin:textForToolBar>
  <odin:fill></odin:fill>
  <odin:separator />
  <odin:buttonForToolBar isLast="true" text="����" iconCls="add" icon="../../basejs/ext/resources/images/default/dd/drop-no.gif" cls="x-btn-text-icon" tooltip="����������"></odin:buttonForToolBar>
</odin:toolBar>
<div id="panel_content">
<odin:groupBox title="������Ϣ">
<table border="0" id="myform" align="center" width="100%"  cellpadding="0" cellspacing="0">
   <tr>
     <odin:textEdit property="worker0" label="����"/> 
	 <odin:textEdit property="worker1" label="����"/>
	 <odin:textEdit property="worker2" label="����"/>
   </tr>
  <tr>
     <odin:textEdit property="dwbm" label="��λ����"/>
	 <odin:textEdit property="name" label="��λ����" colspan="4"/>
   </tr>
   <tr>
      <odin:textEdit property="xming" label="����"/>
	  <odin:select property="zjlx" editor="true" data="['1', '���֤'],['2', '��ҵ֤'],['3', '������'],['4', '����֤��']" label="֤������" colspan="4"></odin:select>

   </tr>	 
 </table>
 </odin:groupBox>
 <odin:groupBox title="������Ϣ">
<table border="0" id="myform" width="100%"  cellpadding="0" cellspacing="0">
   <tr>
     <odin:textEdit property="worker10" label="����"/> 
	 <odin:textEdit property="worker11" label="����"/>
	 <odin:textEdit property="worker12" label="����"/>  
   </tr>
  <tr>
     <odin:textEdit property="dwbm1" label="��λ����"/>
	 <odin:textEdit property="name1" label="��λ����" colspan="4"/>
   </tr>
   <tr>
      <odin:textEdit property="xming1" label="����"/>
	  <odin:select property="zjlx1" editor="true" data="['1', '���֤'],['2', '��ҵ֤'],['3', '������'],['4', '����֤��']" label="֤������" colspan="4"></odin:select>
   </tr>	 
 </table>
 </odin:groupBox> 
</div>
<div id="content"></div>
<table>
<tr><td>
<odin:panel  property="mypanel" width="650" height="300" title="��Panel" collapsible="true" contentEl="panel_content"/> 
</td><td><odin:panel topBarId="myToolBar1" width="150" height="300" property="mypanel2" title="��Panel2" collapsible="true" contentEl="content"/> </td></tr>
</table>
</odin:base>
</body>
</html>