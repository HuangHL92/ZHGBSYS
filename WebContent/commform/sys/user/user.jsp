<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�û�����</title>
<odin:head/>
<odin:MDParam/>
<script type="text/javascript" src="<%=request.getContextPath()%>/commform/sys/user/user.js"></script>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>�û���ѯ</h3>"/>
	<odin:fill/>
	<odin:buttonForToolBar isLast="true" text="��ѯ" handler="loadUserGridData" icon="../commform/images/icon/i_2.gif" cls="x-btn-text-icon"/>
</odin:toolBar>


<div id="userQueryContent">
	<table width="100%">
		<tr>
			<td height="8"  colspan="6"></td>
		</tr>
		<tr>
	    	<odin:textIconEdit property="username" label="��������"></odin:textIconEdit>
	    	<odin:textEdit property="userloginname" label="�û���¼��"></odin:textEdit>
	        <odin:textEdit property="opname" label="����Ա����"></odin:textEdit>
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
	<odin:buttonForToolBar text="����" handler="openAddUserWin"/>
	<odin:buttonForToolBar text="�����û�����" handler="resetUserPasswd"/>
	<odin:buttonForToolBar text="ɾ��" handler="deleteManyUser"  icon="../commform/images/icon/i_2.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>

<odin:gridSelectColJs name="aab301"  codeType="AAB301"></odin:gridSelectColJs>
<odin:gridWithPagingTool collapsible="true" topToolBar="gridToolBar" sm="checkbox"   autoFill="false"  forceNoScroll="false" url="/common/pageQueryAction.do?method=query" property="usergrid" isFirstLoadData="false" pageSize="15" title="" width="803" height="440">
<odin:gridJsonDataModel  id="userid" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="userid" />
  <odin:gridDataCol name="username" />
  <odin:gridDataCol name="operatorname" />
  <odin:gridDataCol name="description" />
  <odin:gridDataCol name="useful"/>
  <odin:gridDataCol name="creator"/>
  <odin:gridDataCol name="aab301"/>
  <odin:gridDataCol name="dept" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridSmColumn />
  <odin:gridRowNumColumn></odin:gridRowNumColumn>
  <odin:gridColumn header="�û�id" hidden="true" dataIndex="userid" />
  <odin:gridColumn header="�û���¼��" align="left" width="80" dataIndex="username" />
  <odin:gridColumn header="��������" align="left" width="80" dataIndex="operatorname" />
  <odin:gridColumn header="����Ա����"  width="70" dataIndex="creator" />
  <odin:gridColumn header="����Ͻ��"  width="150" dataIndex="aab301" editor="select" edited="false" codeType="AAB301" />
  <odin:gridColumn header="����"  width="150" dataIndex="description" />
  <odin:gridColumn renderer="renderUseful" header="�Ƿ���Ч" align="center" width="70" dataIndex="useful" />
  <odin:gridColumn renderer="renderOp" align="center" width="150" header="����" dataIndex="userid" isLast="true"/>
</odin:gridColumnModel>		
</odin:gridWithPagingTool>

<odin:window src="/commform/sys/user/addOrEditUser.jsp" modal="true" id="userWindow" width="500" height="400"></odin:window>

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