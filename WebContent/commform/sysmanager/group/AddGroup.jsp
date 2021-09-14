<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page
	import="java.util.Iterator"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<html>
<head>
<title>机构信息</title>
<sicp3:base/>
<script src="<sicp3:rewrite forward="leaftree"/>"></script>
<script src="<sicp3:rewrite forward="quickSelectBase"/>"></script>
<script src="<sicp3:rewrite forward="quickSelectMgr"/>"></script>
<script src="<sicp3:rewrite forward="select"/>"></script>
<script src="<sicp3:rewrite forward="quickSelect"/>"></script>
<script language="javascript">
<!--
//设置页面元素的CSS
//eleName 页面元素名称
//className 要切换的CSS名称


function check(){
var t = checkValue(groupForm);
	if(!t)
		return t;

  var name = document.groupForm.name.value;
  if((null == name) ||( "" == name)){
  	alert("机构名称不能为空！");
    return false;
  }

  var districtcode = document.groupForm.districtcode.value;
  if((null == districtcode) ||( "" == districtcode)){
  	alert("行政区划代码不能为空！");
    return false;
  }
  document.groupForm.userList.value = getSelectedData('groupForm','userListRight');
  document.groupForm.roleList.value = getSelectedData('groupForm','roleListRight');

  return true;
}
function page_init(){
   eapObjsMgr.onReady('groupForm');
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

	<sicp3:title title="增加机构" />
	<sicp3:tabletitle title="机构信息" />
	<%
			Iterator usersIter = (Iterator) pageContext
			.findAttribute("usersIter");
			Iterator freeUsersIter = (Iterator) pageContext
			.findAttribute("freeUsersIter");
			Iterator rolesIter = (Iterator) pageContext
			.findAttribute("rolesIter");
			Iterator freeRolesIter = (Iterator) pageContext
			.findAttribute("freeRolesIter");
	%>
	<sicp3:form action="/groupAction.do?method=add">
		<table width="95%" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tableInput">
			<tr>
				<td width="8%" height="0" align="right" nowrap><font
					color="#FF0000">*</font>机构名称<br>
				</td>
				<td width="20%" height="0"><sicp3:text property="name"
					required="true" disable="false" label="机构名称" /> <sicp3:hidden
					property="treeid" /> <sicp3:hidden property="groupid" /> <sicp3:hidden
					property="userList" /> <sicp3:hidden property="roleList" /> <sicp3:hidden
					property="parentid" /></td>
				<td width="8%" height="0" align="right" nowrap><font
					color="#FF0000">*</font>机构编号<br>
				</td>
				<td width="20%" height="0"><sicp3:text mask="________"
					property="org" required="true" disable="false" label="机构编号" /></td>
			</tr>
			<tr>
				<td width="8%" height="0" align="right" nowrap><font
					color="#FF0000">*</font>机构描述<br>
				</td>
				<td width="39%" height="0"><sicp3:text property="description"
					disable="false" required="true" label="机构描述" maxlength="50" /></td>

				<td width="8%" height="0" align="right" nowrap>机构负责人<br>
				</td>
				<td width="40%" height="0"><sicp3:text property="principal"
					required="false" disable="false" maxlength="30" /></td>
			</tr>
		</table>
		<table width="95%" height="35" border="0" align="center"
			cellspacing="0" class="tableInput">
			<tr>
				<td class="action"><sicp3:submit styleClass="buttonGray"
					onclick="return check();">
					<bean:message key="button.update" />
				</sicp3:submit></td>
			</tr>
		</table>
	</sicp3:form>
	<sicp3:bottom />
	<sicp3:errors />
</sicp3:body>
</html>
