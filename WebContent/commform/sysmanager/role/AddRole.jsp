<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page
	import="java.util.Iterator,com.lbs.cp.sysmanager.entity.SysUser,com.lbs.cp.sysmanager.entity.SysGroup,com.lbs.commons.GlobalNames"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<html>
<head>
<title>角色信息</title>
<sicp3:base />
<script language="javascript">
<!--
//设置页面元素的CSS
//eleName 页面元素名称
//className 要切换的CSS名称
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
	<sicp3:title title="添加角色" />
	<sicp3:tabletitle title="角色信息" />
	<%
				Iterator usersIter = (Iterator) pageContext
				.findAttribute("usersIter");
		Iterator freeUsersIter = (Iterator) pageContext
				.findAttribute("freeUsersIter");
		Iterator groupsIter = (Iterator) pageContext
				.findAttribute("groupsIter");
		Iterator freeGroupsIter = (Iterator) pageContext
				.findAttribute("freeGroupsIter");

		String stringData = request.getParameter("stringData");
	%>

	<sicp3:form action="/roleAction.do?method=add">
		<table border="0" align="center" cellpadding="3" cellspacing="0"
			class="tableInput">
			<tr>
				<td width="8%" height="0" align="right" nowrap><font
					color="#FF0000">*</font>角色名称<br>
				</td>
				<td width="87%" height="0"><sicp3:text property="roleName"
					disable="false" maxlength="64" /></td>
			</tr>
			<tr>
				<td width="8%" height="0" align="right" nowrap>角色描述<br>
				</td>
				<td width="87%" height="0"><sicp3:hidden property="roleid" />
				<sicp3:text property="roledesc" disable="false" maxlength="30" /></td>
				<sicp3:hidden property="userList" />
				<sicp3:hidden property="groupList" />
				<sicp3:hidden property="stringData" value="<%=stringData%>" />
			</tr>
		</table>
		<table width="95%" height="35" border="0" align="center"
			cellspacing="0" class="tableInput">
			<tr>
				<td class="action"><sicp3:submit onclick="return check()"
					styleClass="buttonGray">增加</sicp3:submit> <sicp3:button value="返 回"
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
	       var t=confirm("添加角色成功,是否修改该角色授权，点击［确定］继续！");
	       if(t){
	       window.location.href="<%=request.getContextPath()%>/sysmanager/roleAction.do?method=findFunctionListByRoleid&roleid=<%=pageContext.findAttribute("roleid")%>";
	       }else{
	       window.location.href="<%=request.getContextPath()%>/sysmanager/roleQueryAction.do?method=query";
	       }
	   </script>
	<%
	}
	%>
	<sicp3:errors />
</sicp3:body>
</html>
