<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>用户管理</title>
<odin:head/>
<odin:MDParam/>
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/user/user.js"></script>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>用户查询</h3>"/>
	<odin:fill/>
	<odin:buttonForToolBar isLast="true" text="查询" handler="loadUserGridData" icon="../images/icon/i_2.gif" cls="x-btn-text-icon"/>
</odin:toolBar>


<div id="userQueryContent">
	<table width="100%">
		<tr>
			<td height="8"  colspan="6"></td>
		</tr>
		<tr>
	    	<odin:textIconEdit property="username" label="操作姓名"></odin:textIconEdit>
	    	<odin:textEdit property="userloginname" label="用户登录名"></odin:textEdit>
	        <odin:textEdit property="opname" label="操作员姓名"></odin:textEdit>
		</tr>
		<tr>
			<td height="4" colspan="6"></td>
		</tr>
	</table>		
</div>
<odin:panel contentEl="userQueryContent" property="userQueryPanel" topBarId="btnToolBar"></odin:panel>
<odin:hidden property="id"/>


<odin:toolBar property="gridToolBar">
	<odin:fill/>
	<odin:buttonForToolBar text="新增" handler="openAddUserWin"/>
	<odin:buttonForToolBar text="重置用户密码" handler="resetUserPasswd"/>
	<odin:buttonForToolBar text="删除" handler="deleteManyUser"  icon="../images/icon/i_2.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>

<odin:gridWithPagingTool collapsible="true" topToolBar="gridToolBar" sm="checkbox"   autoFill="false"  forceNoScroll="false" url="/common/pageQueryAction.do?method=query"
 property="usergrid" isFirstLoadData="false" pageSize="15" title="" width="803" height="440">
<odin:gridJsonDataModel  id="userid" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="userid" />
  <odin:gridDataCol name="username" />
  <odin:gridDataCol name="operatorname" />
  <odin:gridDataCol name="description" />
  <odin:gridDataCol name="useful"/>
  <odin:gridDataCol name="creator"/>
  <odin:gridDataCol name="dept" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridSmColumn />
  <odin:gridRowNumColumn></odin:gridRowNumColumn>
  <odin:gridColumn header="用户id" hidden="true" dataIndex="userid" />
  <odin:gridColumn header="用户登录名" align="center" width="100" dataIndex="username" />
  <odin:gridColumn header="操作姓名" align="center" width="100" dataIndex="operatorname" />
  <odin:gridColumn header="描述"  width="240" dataIndex="description" />
  <odin:gridColumn header="操作员姓名"  width="80" dataIndex="creator" />
  <odin:gridColumn renderer="renderUseful" header="是否有效" align="center" width="80" dataIndex="useful" />
  <odin:gridColumn renderer="renderOp" align="center" width="150" header="操作" dataIndex="userid" isLast="true"/>
</odin:gridColumnModel>		
</odin:gridWithPagingTool>

<odin:window src="/sys/user/addOrEditUser.jsp" modal="true" id="userWindow" width="500" height="470"></odin:window>

<form action="<%=request.getContextPath()%>/sys/userAction.do?method=delete" method="post" name="userForm">
	<input type="hidden" name="userId">
</form>

<script>
var sysId = "<%=(request.getParameter("sysId")==null?"":request.getParameter("sysId"))%>";
Ext.onReady(function(){
	loadUserGridData();
});
</script>

</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>