<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<html>
<head>
<title>�û���Ϣ</title>
<sicp3:base />

<script language="javascript">
<!--

//У���û�id
  function IsDigit(cCheck) { return (('0'<=cCheck) && (cCheck<='9')); }
	function IsAlpha(cCheck) { return ((('a'<=cCheck) && (cCheck<='z')) || (('A'<=cCheck) && (cCheck<='Z'))) }
	function VerifyInput(strUserID){
	    if (strUserID == ""){
	        alert("�����������û���");
	        document.frmUserInfo.username.focus();
	        return false;
	    }
	    for (nIndex=0; nIndex<strUserID.length; nIndex++){
	        cCheck = strUserID.charAt(nIndex);
	        if ( nIndex==0 && ( cCheck =='-' || cCheck =='_') ){
	            alert("�û������ַ�����Ϊ��ĸ������");
	            document.userForm.username.focus();
	            return false;
	        }

	        if (!(IsDigit(cCheck) || IsAlpha(cCheck) || cCheck=='-' || cCheck=='_' )){
	            alert("�û���ֻ��ʹ��Ӣ����ĸ�������Լ�-��_���������ַ�����Ϊ��ĸ������");
	            document.userForm.username.focus();
	            return false;
	        }
	    }
	    return true;
	}
function ConfirmPassword(){
	 var username = document.userForm.username.value;
  if((null == username) ||( "" == username)){
  	alert("�û�ID����Ϊ�գ�");
  	document.userForm.username.focus();
    return false;
  }

	if(!VerifyInput(username))
		return false;
  var pd = document.userForm.newpasswd.value;
  var confirm = document.userForm.confirm.value;
  if(pd != confirm){
  	alert("�������ȷ�����벻һ�£�");
  	document.userForm.newpasswd.focus();
    return false;
  }
  return true;
}
//-->
</script>
</head>
<body leftmargin="0" topmargin="0">
<table width="95%" border="0" align="center" cellpadding="0"
	cellspacing="0">
	<tr>
		<td height="10"></td>
	</tr>
</table>
<!--¼�벿��3-->

<sicp3:title title="�����û�����" />
<sicp3:tabletitle title="������Ϣ" />
<%
String stringData = request.getParameter("stringData");
%>
<sicp3:form action="/userAction.do?method=alterPassword" target="hidden">
	<table width="95%" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tableInput">

		<tr>
			<td width="9%" height="0" align="right" nowrap><font
				color="#FF0000">*</font>�û�����<br>
			</td>
			<td width="86%" height="0"><input name="username" type="text"
				maxlength="255" class="text" size="0"></td>
			<td width="5%">&nbsp;</td>
		</tr>
		<tr>
			<td height="0" align="right" nowrap>������</td>
			<td height="0" align="center"><input name="passwd"
				type="password" class="text" size="20"></td>
			<td align="center">&nbsp;</td>
		</tr>
		<tr>
			<td height="0" align="right" nowrap>������</td>
			<td height="0" align="center"><input name="newpasswd"
				type="password" class="text" size="20"></td>
			<td align="center">&nbsp;</td>
		</tr>
		<tr>
			<td height="0" align="right" nowrap>ȷ������</td>
			<td height="0" align="center"><input name="confirm"
				type="password" class="text" size="20"></td>
			<td align="center">&nbsp;</td>
		</tr>
	</table>
	<table width="95%" height="35" border="0" align="center"
		cellspacing="0" class="tableInput">
		<tr>
			<td class="action"><sicp3:submit
				onclick="return ConfirmPassword()" styleClass="buttonGray">�޸�</sicp3:submit>
			</td>
		</tr>

	</table>
</sicp3:form>
<sicp3:bottom />
<iframe name="hidden" height="0" width="0"> </iframe>
<sicp3:errors />
</body>
</html>
