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
<table width="98%" align="center">
<tr><td>
<odin:groupBox title="������Ϣ">
<table border="0" id="myform" align="center" width="100%"  cellpadding="0" cellspacing="0">
<odin:tabLayOut />
   <tr>
     <odin:textEdit property="worker0" label="�籣����"/>   
	 <odin:textEdit property="worker1" label="��λ���"/>
	 <odin:textEdit property="worker2"/>
   </tr>
   <tr> 
	  <odin:select property="zjlx1" editor="true" data="['1', '���֤'],['2', '��ҵ֤'],['3', '������'],['4', '����֤��']" label="֤����"></odin:select>
      <odin:textEdit property="xming1" label="����"/> 
      <odin:select property="zjlx2" editor="false" data="['1', '��'],['2', 'Ů'],['3', '������'],['4', '����֤��']" label="�Ա�"></odin:select>
   </tr>
   <tr>
     <odin:dateEdit property="birthday" label="��������" /> 
     <odin:dateEdit property="startdate" label="��������" />
     <odin:select property="zjlx12" editor="true" data="['10', '10�����������֤'],['11', '11����������ҵ֤'],['12', '12��������������'],['90', '90������������֤��']" label="��������"></odin:select>
   </tr>
</table>
</odin:groupBox> 
</td>
</tr>
</table>
<table width="98%" align="center">
<tr><td>
<odin:groupBox title="�䶯��Ϣ">
<table border="0" id="myform" align="center" width="100%"  cellpadding="0" cellspacing="0">
<odin:tabLayOut />
   <tr>
     <odin:textEdit property="worker01" label="�䶯����" />   
	 <odin:textEdit property="worker11" label="�䶯ԭ��" />
	 <odin:dateEdit property="birthday1"  label="�䶯����"/>
   </tr>
   <tr> 
	 <odin:dateEdit property="birthday2"  label="��������" />
	 <odin:textEdit property="worker112"  label="�䶯��ע"/>
   </tr>
</table>
</odin:groupBox> 
</td></tr></table>
<table width="98%" align="center">
<tr><td>
<odin:groupBox title="ҵ����Ϣ">
<table border="0" id="myform" align="center" width="100%"  cellpadding="0" cellspacing="0">
<odin:tabLayOut />
   <tr>
     <odin:textEdit property="worker012" label="�ù�����" />   
	 <odin:textEdit property="worker1122" label="���⹤��" />
	 <odin:select property="zjl5x1" editor="true"  data="['1', '���֤'],['2', '��ҵ֤'],['3', '������'],['4', '����֤��']" label="��������"></odin:select>
   </tr>
   <tr>
     <odin:textEdit property="wo2rker012" label="ҽ������" />   
	 <odin:textEdit property="worke2r1122" label="�걨����"/>
	 <td></td>
	 <td nowrap="nowrap"><odin:checkbox property="workplace" label="�����������" /></td>
   </tr>
   <tr> 
	 <odin:textEdit property="worker1152" colspan="6" label="������ע"/>
   </tr>
</table>
</odin:groupBox> 
</td></tr></table>
</odin:base>
</body>
</html>