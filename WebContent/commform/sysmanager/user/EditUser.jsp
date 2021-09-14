<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page
	import="java.util.Iterator,com.lbs.cp.sysmanager.entity.SysGroup,com.lbs.cp.sysmanager.entity.SysRole,com.lbs.cp.sysmanager.entity.Sysdatapopedom"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<script src="<sicp3:rewrite forward="leaftree"/>"></script>
<script src="<sicp3:rewrite forward="quickSelectBase"/>"></script>
<script src="<sicp3:rewrite forward="quickSelectMgr"/>"></script>
<script src="<sicp3:rewrite forward="select"/>"></script>
<script src="<sicp3:rewrite forward="quickSelect"/>"></script>
<html>
<head>
<title>用户信息</title>
<sicp3:base />
<script language="javascript">
<!--

function goBack() {
 window.location.href="<sicp3:rewrite page='/userAction.do?method=goBack'/>&stringData=" +
      	document.all("stringData").value ;
}

function check(){
   var userid = document.userForm.username.value;
  if((null == userid) ||( "" == userid)){
  	alert("用户ID不能为空！");
  	document.userForm.userid.focus();
    return false;
  }

   var operatorname = document.userForm.operatorname.value;
  if((null == operatorname) ||( "" == operatorname)){
  	alert("用户名称不能为空！");
  	document.userForm.operatorname.focus();
    return false;
  }

  document.userForm.roleList.value = getSelectedData('userForm','roleListRight');
  document.userForm.roleDPPDList.value = getSelectedData('userForm','roleDPPDListRight');
  document.userForm.groupList.value = getSelectedData('userForm','groupListRight');
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

	<sicp3:title title="修改用户" />
	<sicp3:tabletitle title="用户信息" />
	<%
				Iterator rolesIter = (Iterator) pageContext
				.findAttribute("rolesIter");
		Iterator freeRolesIter = (Iterator) pageContext
				.findAttribute("freeRolesIter");
		Iterator DPPDIter = (Iterator) pageContext
				.findAttribute("DPPDIter");
		Iterator freeDPPDRolesIter = (Iterator) pageContext
				.findAttribute("freeDPPDRolesIter");
		Iterator groupsIter = (Iterator) pageContext
				.findAttribute("groupsIter");
		Iterator freeGroupsIter = (Iterator) pageContext
				.findAttribute("freeGroupsIter");
		String stringData = request.getParameter("stringData");
	%>
	<sicp3:form action="/userAction.do?method=update">
		<table width="95%" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tableInput">
			<sicp3:editorlayout />
			<tr>
				<sicp3:hidden property="passwd" />
				<sicp3:hidden property="roleList" />
				<sicp3:hidden property="roleDPPDList" />
				<sicp3:hidden property="groupList" />
				<sicp3:hidden property="trainOrg" />
				<sicp3:hidden property="id" />
				<sicp3:hidden property="userid" />
				<input type=hidden name="stringData" value="<%=stringData%>" />
				<td width="9%" height="0" align="right" nowrap><font
					color="#FF0000">*</font>用户ID<br>
				</td>
				<td width="86%" height="0"><sicp3:text property="username"
					disable="false">
				</sicp3:text></td>
			</tr>
			<tr>
				<td width="9%" height="0" align="right" nowrap><font
					color="#FF0000">*</font>用户名称</td>
				<td width="86%" height="0"><sicp3:text property="operatorname"
					disable="false" maxlength="32" /></td>
			</tr>

			<tr>
				<td height="25" align="right" nowrap>描述</td>
				<td height="25" align="center"><sicp3:text
					property="description" disable="false" maxlength="50" /></td>
			</tr>
			<tr>
				<td width="9%" height="0" align="right">隶属机构<br>
				</td>
				<td height="0"><sicp3:text property="name" style="cursor:hand"
					styleClass="text" disable="false"
					onclick="setRegionTree(this,document.all.name,document.all.groupid)"
					readonly="true" /></td>
				<sicp3:hidden property="groupid" />
			</tr>
			<tr>
				<td height="0" align="right" nowrap>隶属角色</td>
				<td>
				<table cellspacing="0" cellpadding="0"
					style="width:400px;height:100px" align="left">
					<tr>
						<td align="center">可选角色</td>
						<td align="center" style="width:10px"></td>
						<td align="center">已选角色</td>
					</tr>
					<tr>
						<td align="center"><select name="roleListLeft" multiple
							size="18"
							ondblclick="javascript:moveRight('userForm','roleListLeft','roleListRight',false)">
							<%
									while ((null != freeRolesIter) && (freeRolesIter.hasNext())) {
									SysRole sr = (SysRole) freeRolesIter.next();
							%>
							<option value="<%=sr.getRoleid()%>"><%=sr.getRoleName()%></option>
							<%
							}
							%>
						</select></td>
						<td style="width:10px" align="center">
						<table>
							<tr>
								<td><a class="ALink"
									href="javascript:moveRight('userForm','roleListLeft','roleListRight',false)">&#62;</a></td>
							</tr>
							<tr>
								<td><a class="ALink"
									href="javascript:moveRight('userForm','roleListLeft','roleListRight',true)">&#62;&#62;</a></td>
							</tr>
							<tr>
								<td><a class="ALink"
									href="javascript:moveLeft('userForm','roleListLeft','roleListRight',false)">&#60;</a></td>
							</tr>
							<tr>
								<td><a class="ALink"
									href="javascript:moveLeft('userForm','roleListLeft','roleListRight',true)">&#60;&#60;</a></td>
							</tr>
						</table>
						</td>
						<td align="left"><select name="roleListRight" multiple
							size="18"
							ondblclick="javascript:moveLeft('userForm','roleListLeft','roleListRight',false)">
							<%
									while ((null != rolesIter) && (rolesIter.hasNext())) {
									SysRole sr = (SysRole) rolesIter.next();
							%>
							<option value="<%=sr.getRoleid()%>"><%=sr.getRoleName()%></option>
							<%
							}
							%>
						</select></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td height="0" align="right" nowrap>数据权限角色</td>
				<td>
				<table cellspacing="0" cellpadding="0"
					style="width:400px;height:100px" align="left">
					<tr>
						<td align="center">可选角色</td>
						<td align="center" style="width:10px"></td>
						<td align="center">已选角色</td>
					</tr>
					<tr>
						<td align="center"><select name="roleDPPDListLeft" multiple
							size="18"
							ondblclick="javascript:moveRight('userForm','roleDPPDListLeft','roleDPPDListRight',false)">
							<%
									while ((null != freeDPPDRolesIter) && (freeDPPDRolesIter.hasNext())) {
									Sysdatapopedom sr = (Sysdatapopedom) freeDPPDRolesIter.next();
							%>
							<option value="<%=sr.getPopedomid()%>"><%=sr.getPopedomname()%></option>
							<%
							}
							%>
						</select></td>
						<td style="width:10px" align="center">
						<table>
							<tr>
								<td><a class="ALink"
									href="javascript:moveRight('userForm','roleDPPDListLeft','roleDPPDListRight',false)">&#62;</a></td>
							</tr>
							<tr>
								<td><a class="ALink"
									href="javascript:moveRight('userForm','roleDPPDListLeft','roleDPPDListRight',true)">&#62;&#62;</a></td>
							</tr>
							<tr>
								<td><a class="ALink"
									href="javascript:moveLeft('userForm','roleDPPDListLeft','roleDPPDListRight',false)">&#60;</a></td>
							</tr>
							<tr>
								<td><a class="ALink"
									href="javascript:moveLeft('userForm','roleDPPDListLeft','roleDPPDListRight',true)">&#60;&#60;</a></td>
							</tr>
						</table>
						</td>
						<td align="left"><select name="roleDPPDListRight" multiple
							size="18"
							ondblclick="javascript:moveLeft('userForm','roleDPPDListLeft','roleDPPDListRight',false)">
							<%
									while ((null != DPPDIter) && (DPPDIter.hasNext())) {
									Sysdatapopedom sr = (Sysdatapopedom) DPPDIter.next();
							%>
							<option value="<%=sr.getPopedomid()%>"><%=sr.getPopedomname()%></option>
							<%
							}
							%>
						</select></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		<table width="95%" height="35" border="0" align="center"
			cellspacing="0" class='tableInput'>
			<tr>
				<td width="80%"></td>
				<td class="action"><sicp3:submit styleClass="buttonGray"
					onclick="return check()">更新</sicp3:submit><sicp3:button value="返 回"
					onclick="goBack()" /></td>
			</tr>
		</table>
	</sicp3:form>
	<sicp3:bottom />
	<sicp3:errors />
</sicp3:body>
</html>
