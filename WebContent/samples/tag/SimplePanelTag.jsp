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
<table align="center" width="100%">
<tr><td>
<odin:simplePanel title="��Panel" width="765" bodyWidth="740">
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
 <br><br>
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
 <br><br>
 <table border="0" id="myform" width="100%" align="center"  cellpadding="0" cellspacing="0">
   <tr>
     <odin:textEdit property="worker110" label="����"/> 
	 <odin:textEdit property="worker111" label="����"/>
	 <odin:dateEdit property="aab1007" label="��λ����" ></odin:dateEdit>
   </tr>
  <tr>
     <odin:textEdit property="dwbm11" label="��λ����"/>
	 <odin:textEdit property="name11" label="��λ����" colspan="4"/>
   </tr>
   <tr>
      <odin:textEdit property="xming11" label="����"/>
	  <odin:select property="zjlx11" editor="true" data="['1', '���֤'],['2', '��ҵ֤'],['3', '������'],['4', '����֤��']" label="֤������"></odin:select>
	  <td nowrap="nowrap"><odin:checkbox property="workplace" label="�����ص�1" /></td>
	  <td>
	  <odin:checkbox property="workplace2" label="�����ص�2" />
	  </td>
   </tr>
   <tr><td height="120"></td></tr>	 
 </table>   
</odin:simplePanel> 
</td></tr>
</table>
</odin:base>
</body>
</html>