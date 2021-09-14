<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>角色管理</title>
<odin:head/>
<odin:MDParam/>
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/role/role.js"></script>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>角色查询</h3>"/>
	<odin:fill/>
	<odin:buttonForToolBar isLast="true" text="查询" handler="loadRoleGridData" icon="../images/icon/i_2.gif" cls="x-btn-text-icon"/>
</odin:toolBar>


<div id="roleQueryContent">
	<table width="100%">
		<tr>
			<td height="8"  colspan="4"></td>
		</tr>
		<tr>
	    	<odin:textIconEdit property="roleQName" label="角色名称"></odin:textIconEdit>
	    	<odin:textEdit property="roleQDesc" label="角色描述"></odin:textEdit>
		</tr>
		<tr>
			<td height="4" colspan="4"></td>
		</tr>
	</table>		
</div>
<odin:panel contentEl="roleQueryContent" property="roleQueryPanel" topBarId="btnToolBar"></odin:panel>


<odin:toolBar property="gridToolBar">
	<odin:fill/>
	<odin:buttonForToolBar text="指定CA功能" handler="openFuncForCA"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="新增" handler="addRowForNewUser" isLast="true"/>
</odin:toolBar>


<odin:editgrid collapsible="true" bbarId="pagetoolbar" afteredit="doAfterEdit"  topBarId="gridToolBar"  autoFill="false"  forceNoScroll="false" url="/common/pageQueryAction.do?method=query" property="rolegrid" isFirstLoadData="false" pageSize="15" title="" width="803" height="440">
<odin:gridJsonDataModel  id="roleid" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="roleid" />
  <odin:gridDataCol name="roledesc" />
  <odin:gridDataCol name="creator" />
  <odin:gridDataCol name="oldrolename" />
  <odin:gridDataCol name="status" />
  <odin:gridDataCol name="rolename" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn></odin:gridRowNumColumn>
  <odin:gridColumn header="角色id" hidden="true" dataIndex="roleid" />
  <odin:gridColumn hidden="true" dataIndex="oldrolename" />
  <odin:gridColumn header="角色名称" align="center" width="100" dataIndex="rolename" selectOnFocus="true" editor="text" edited="true"/>
  <odin:gridColumn header="角色描述" width="300" dataIndex="roledesc" editor="text" selectOnFocus="true" edited="true"/>
  <odin:gridColumn header="状态"  align="center" width="80" renderer="renderStatus" dataIndex="status" />
  <odin:gridColumn header="操作员姓名"  width="80" dataIndex="creator" />
  <odin:gridColumn renderer="renderOp" align="center" width="150" header="操作" dataIndex="roleid"/>
  <odin:gridColumn renderer="renderOpFun" align="center" width="60" header="授权" dataIndex="roleid" isLast="true"/>
</odin:gridColumnModel>		
</odin:editgrid>

<odin:window src="/sys/user/addOrEditUser.jsp" modal="true" id="userWindow" width="500" height="350"></odin:window>

<odin:window src="/sys/assignRoleFuncAction.do" modal="true" id="funcWindow" width="300" height="440"></odin:window>
<odin:window src="/sys/assignCAFuncAction.do" modal="true" id="funcCAWindow" width="300" height="440"></odin:window>
  

<!-- role form -->
<form action="<%=request.getContextPath()%>/sys/roleAction.do" method="post" name="roleForm">
	<input type="hidden" name="method">
	<input type="hidden" name="roleid">
	<input type="hidden" name="roledesc">
	<input type="hidden" name="rolename">
	<input type="hidden" name="sysid">
</form>
<script>
var sysId = "<%=(request.getParameter("sysId")==null?"":request.getParameter("sysId"))%>";
Ext.onReady(function(){
    document.forms[0].sysid.value = sysId;
	loadRoleGridData();
});
</script>

</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>