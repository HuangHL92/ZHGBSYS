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
     <odin:textEdit property="EAC012" label="�籣���" required="true" /> 
     <odin:textEdit property="AAC003" label="����" required="false" disabled="true"/>   
	 <odin:select property="AAC004" editor="false" data="['1', '��'],['2', 'Ů'],['9', 'δ˵��']" label="�Ա�" required="false" width="127" disabled="true" ></odin:select> 
   </tr>
   <tr>
	 <odin:textEdit property="EAC109" label="���֤����"   required="false" disabled="true"/>     
	 <odin:textEdit property="EA0127" label="����"  required="false" disabled="true"/> 
     <odin:dateEdit property="AAC030" label="�μӹ�������" required="false" disabled="true" width="127"/>
   </tr>
   <tr> 
     <odin:textEdit property="AAB001" label="��λ���"   required="false" disabled="true"/>
	 <odin:textEdit property="AAB004" label="��λ����" colspan="4" width="379" required="false" disabled="true"/>    
   </tr>
</table>
</odin:groupBox> 
</td>
</tr></table>
