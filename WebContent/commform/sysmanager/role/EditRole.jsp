<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<html>
<head>
<title>角色信息</title>
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
  	alert("角色名称不能为空！");
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
	<!--录入部分3-->

	<sicp3:title title="修改角色" />
	<sicp3:tabletitle title="角色信息" />
	<%
	String stringData = request.getParameter("stringData");
	%>

	<sicp3:form action="/roleAction.do?method=update">
		<table border="0" align="center" cellpadding="3" cellspacing="0"
			class="tableInput">
			<tr>
				<td width="8%" height="0" align="right" nowrap><font
					color="#FF0000">*</font>角色名称</td>
				<td width="87%" height="0"><sicp3:text property="roleName"
					disable="false" maxlength="64" /></td>
			</tr>
			<tr>
				<td width="8%" height="0" align="right" nowrap>角色描述</td>
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
					styleClass="buttonGray">更新</sicp3:submit> <sicp3:button value="返 回"
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
	       var t=confirm("角色信息修改成功,是否继续给该角色授权，点击［确定］继续！");
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
