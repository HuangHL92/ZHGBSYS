<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="��Ա������Ϣ" description="��Ա������Ϣ" %>
 <%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<% 
String ctxpathemp = request.getContextPath();
%>
<table width="98%" align="center">
<tr><td>
<odin:groupBox title="������Ϣ">
<table border="0" id="myform4" align="center" width="100%"  cellpadding="0" cellspacing="0">
<odin:tabLayOut />
   <tr>
     <odin:textEdit property="EAC012" label="�籣���" required="true" width="144"/> 
     <odin:textEdit property="AAC003" label="����" required="false" disabled="true" width="144"/>   
	 <odin:textEdit property="EAC109" label="���֤����"   required="false" disabled="true" width="144"/>     
   </tr>
   <tr> 
     <odin:textEdit property="AAB001" label="��λ���"   required="false" disabled="true" width="144"/>
	 <odin:textEdit property="AAB004" label="��λ����" colspan="4" width="396" required="false" disabled="true"/>    
   </tr>
   <tr>
	 <odin:select property="AAC004" editor="false" data="['1', '��'],['2', 'Ů'],['9', 'δ˵��']" label="�Ա�" required="false" disabled="true" width="144"></odin:select> 
     <odin:dateEdit property="AAC006" label="��������"   required="false" disabled="true" width="144"/>
	 <odin:textEdit property="EA0144" label="��&nbsp&nbsp��"  required="false" disabled="true" width="144"/> 
   </tr>
   <tr>
	 <odin:select property="EABJD" editor="false" data="['1', '����·'],['2', '����·'],['3', '��һ·']" label="����ֵ�" required="false" disabled="true" width="144"></odin:select>
     <odin:dateEdit property="AAC030" label="�״βα�����" required="false" disabled="true" width="144"/>
   </tr>
   <tr>
   </tr>
</table>
</odin:groupBox> 
</td>
</tr></table>
