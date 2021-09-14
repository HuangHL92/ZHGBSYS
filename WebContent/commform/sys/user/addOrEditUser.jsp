<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<% 
  response.setHeader("Pragma","No-cache");
  response.setHeader("Cache-Control","no-cache");
  response.setDateHeader("Expires", 0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>用户管理</title>
<odin:head/>
<odin:MDParam/>
<script src="<%=request.getContextPath()%>/js/md5.jsp"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/commform/sys/user/addOrEditUser.js"></script>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form action="<%=request.getContextPath()%>/sys/userAction.do?method=addOrEditUser" method="post" name="userForm">
<table align="center" width="96%">
	<tr>
		<td height="6" colspan="4"></td>
	</tr>
	<tr>
		<odin:textEdit property="username" alt="用于登录时的名称" label="用户登录名"  onblur="validateNameOnly(this.value)" required="true"></odin:textEdit>
		<odin:textEdit property="operatorname" alt="真实姓名" label="用户姓名"></odin:textEdit>
	</tr>
	<tr>
		<odin:textEdit property="passwd" inputType="password" alt="" label="用户密码"></odin:textEdit>
		<odin:select property="useful" data="['1','有效'],['0','无效']" label="是否有效" value="1"></odin:select>
	</tr>
	<tr>
		<odin:select property="aab301" label="所属辖区" codeType="AAB301" maxHeight="200" ></odin:select>
		<odin:textEdit property="description" label="描述"></odin:textEdit>
	</tr>
</table>
<table align="center" width="100%">
	<tr>
		<td>
        <odin:groupBox title="所属角色">
			<odin:grid property="roleGrid" sm="checkbox" width="440" height="140" url="/common/pageQueryAction.do?method=query" isFirstLoadData="false">
				<odin:gridJsonDataModel  id="roleid" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="roleid" />
				    <odin:gridDataCol name="rolename" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridSmColumn></odin:gridSmColumn>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn dataIndex="rolename" header="角色名称" isLast="true"></odin:gridColumn>
				</odin:gridColumnModel>
			</odin:grid>	
		</odin:groupBox>
		</td>
	</tr>
	<tr>
		<td height="6"></td>
	</tr>
	<tr>
		<td align="right">
			<img src="../../commform/images/baocun.gif" onclick="addUser()">&nbsp;&nbsp;
			<img src="../../commform/images/qingkong.gif" onclick="clearForm(document.userForm);">
		</td>
	</tr>
</table>			
	<input type="hidden" name="userid">
	<input type="hidden" name="sysid">
	<input type="hidden" name="roleids">
</form>

<script>   
Ext.onReady(function(){
	document.userForm.sysid.value = parent.sysId;
	loadRoleData();
	doInitWin();
});
</script>

</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>