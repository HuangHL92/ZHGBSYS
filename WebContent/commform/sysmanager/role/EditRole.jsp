<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<html>
<head>
<title>��ɫ��Ϣ</title>
<sicp3:base />
<script language="javascript">
<!--
function goBack() {
 window.location.href="<sicp3:rewrite page='/roleAction.do?method=goBack'/>&stringData=" +
      	document.all("stringData").value ;
}

function check(){
  var roleName = document.roleForm.roleName.value;
  if((null == roleName) ||( "" == roleName)){
  	alert("��ɫ���Ʋ���Ϊ�գ�");
       document.roleForm.roleName.focus();
    return false;
  }
    document.roleForm.userList.value = getSelectedData('roleForm','userListRight');
  document.roleForm.groupList.value = getSelectedData('roleForm','groupListRight');
  return true;
}
//-->
</script>
</head>
<sicp3:body>
	<table width="95%" border="0" align="center" cellpadding="0"
		cellspacing="0">
		<tr>
			<td height="10"></td>
		</tr>
	</table>
	<!--¼�벿��3-->

	<sicp3:title title="�޸Ľ�ɫ" />
	<sicp3:tabletitle title="��ɫ��Ϣ" />
	<%
	String stringData = request.getParameter("stringData");
	%>

	<sicp3:form action="/roleAction.do?method=update">
		<table border="0" align="center" cellpadding="3" cellspacing="0"
			class="tableInput">
			<tr>
				<td width="8%" height="0" align="right" nowrap><font
					color="#FF0000">*</font>��ɫ����</td>
				<td width="87%" height="0"><sicp3:text property="roleName"
					disable="false" maxlength="64" /></td>
			</tr>
			<tr>
				<td width="8%" height="0" align="right" nowrap>��ɫ����</td>
				<td width="87%" height="0"><sicp3:hidden property="roleid" />
				<sicp3:text property="roledesc" disable="false" maxlength="30" /> <sicp3:hidden
					property="userList" /> <sicp3:hidden property="groupList" /> <input
					type=hidden name="stringData" value="<%=stringData%>" /></td>
			</tr>
		</table>
		<table width="95%" height="35" border="0" align="center"
			cellspacing="0" class="tableInput">
			<tr>
				<td class="action"><sicp3:submit onclick="return check()"
					styleClass="buttonGray">����</sicp3:submit> <sicp3:button value="�� ��"
					onclick="goBack()" /></td>
			</tr>
		</table>
	</sicp3:form>
	<sicp3:bottom />
	<%
		String suc = (String) request.getAttribute("suc");
		if ("suc".equals(suc)) {
	%>
	<script language="javascript">
	       var t=confirm("��ɫ��Ϣ�޸ĳɹ�,�Ƿ�������ý�ɫ��Ȩ�������ȷ���ݼ�����");
	       if(t){
	       window.location.href="<sicp3:rewrite page='/roleAction.do?method=findFunctionListByRoleid'/>&roleid=<%=pageContext.findAttribute("roleid")%>";
	       }else{
	       window.location.href="<sicp3:rewrite page='/roleQueryAction.do?method=query'/>";
	       }
	   </script>
	<%
	}
	%>
	<sicp3:errors />
</sicp3:body>
</html>
