<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3" %>
<html>
<head>
<title>�ϴ�Ȩ���б��ļ�</title>
<script language="javascript">
<!--
//����ҳ��Ԫ�ص�CSS
//eleName ҳ��Ԫ������
//className Ҫ�л���CSS����
function goBack() {
 window.location.href="<sicp3:rewrite page='/functionAction.do?method=goBack'/>&stringData=" +
      	document.all("stringData").value ;
}
function check(){
   var uploadfile = document.functionForm.uploadfile.value;
  if((null == uploadfile) ||( "" == uploadfile)){
  		alert("�ϴ��ļ�û��ָ����");
      document.functionForm.uploadfile.focus();
    return false;
  }
	return true;
}
//-->
</script>
<sicp3:base/>
</head>

<sicp3:body>
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="10"></td>
  </tr>
</table>
<!--���ⲿ��-->
<%
String stringData = request.getParameter("stringData");
%>
<sicp3:title title="�ϴ�Ȩ���б��ļ�"/>
<sicp3:tabletitle title="Ȩ���б���Ϣ"/>
<table width="95%" border="0" align="center" cellpadding="3" cellspacing="0" class="tableInput">
  <sicp3:form action="/functionAction.do?method=upload" method="POST" enctype="multipart/form-data">
  <tr >
    <td  width="20%"><sicp3:colortxt value="*" />�ϴ��ļ�<sicp3:hidden property="stringData" value="<%=stringData%>"/><br></td>
    <td align="left" width="80%">
      <sicp3:file property="uploadfile" size="80%">
      </sicp3:file></td>
  </tr>
</table>
<table width="95%" height="35" border="0" align="center" cellspacing="0" class="tableInput">
  <tr>
    <td class="action">
      <sicp3:submit styleClass="buttonGray"  onclick="return check()">�� ��</sicp3:submit>
      <input type="button" name="close" value="�� ��" onclick="goBack()" class="buttonGray">
   </td>
  </tr>
</table>
</sicp3:form>
<sicp3:bottom/>
<sicp3:errors />
</sicp3:body>
</html>
