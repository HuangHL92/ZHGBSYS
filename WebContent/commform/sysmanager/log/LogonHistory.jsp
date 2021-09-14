<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page
	import="com.lbs.commons.GlobalNames,com.lbs.apps.query.QueryInfo"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>

<%
String ctxpath = request.getContextPath();
%>
<LINK href="<%=ctxpath%>/css/gridStyle.css" type=text/css rel=stylesheet>
<LINK href="<%=ctxpath%>/css/baseStyle.jsp" type=text/css rel=stylesheet>

<html>
<sicp3:base />
<sicp3:body>
	<script language="javascript">

function exportData(){
	document.exportForm.target = "_blank";
    document.exportForm.action="<sicp3:rewrite page='/logonHistoryAction.do?method=export'/>&stringData=" +
      	document.all("stringData").value;
  	document.exportForm.submit();
}

function delAll(){
  if(confirm("此操作不能回退，确信要删除所有的日志吗？")){
   window.location.href="<sicp3:rewrite page='/logonHistoryAction.do?method=delAll'/>&stringData=" +
      	document.all("stringData").value;
  }else{
    return false;
  }
}
//-->
</script>


	<script type="text/javascript">
  function onCallbackError(excString)
  {
  }

  function onDelete(item)
  {
      if (confirm("Delete record?"))
        return true;
      else
        return false;
  }

  function deleteRow(rowId,actionURI)
  {
    Grid1.Delete(Grid1.GetRowFromClientId(rowId),null,actionURI);
  }

  </script>

	<%
		String stringData = "";
		QueryInfo qi = (QueryInfo) pageContext
				.findAttribute(GlobalNames.QUERY_INFO);
		if (null != qi) {
			stringData = qi.getStringData();
		}
	%>

	<sicp3:title title="登录日志管理" />
	<sicp3:hidden property="stringData" value="<%=stringData%>" />
	<sicp3:query action="/logonHistoryAction.do?method=query" topic="查询信息">
		<tr>
			<sicp3:texteditor property="userid" label="用户ID" disable="false"></sicp3:texteditor>
			<sicp3:texteditor property="ip" label="用户登录ip" disable="false"></sicp3:texteditor>
		</tr>
		<tr>
			<sicp3:texteditor property="logontime" label="登录时间" disable="false"></sicp3:texteditor>
			<sicp3:texteditor property="logofftime" label="下线时间" disable="false"></sicp3:texteditor>
		</tr>
	</sicp3:query>

	<sicp3:grid id="Grid1" tagTemplateId="grid" width="100%"
		requestURI="/sysmanager/logonHistoryAction.do?method=commonQuery"
		runningMode="callback" pagerImagesFolderUrl="images/pager/"
		pageSize="15">
		<sicp3:gridlevels>
			<sicp3:gridlevel dataMember="querydata" dataKeyField="userid"
				tagTemplateId="gridlevel"
				tableHeadingClientTemplateId="TableHeadingTemplate">

				<sicp3:gridcolumns>
					<sicp3:gridcolumn dataField="id" headingText="序号"
						allowEditing="false"></sicp3:gridcolumn>
					<sicp3:gridcolumn dataField="userid" headingText="用户名"></sicp3:gridcolumn>
					<sicp3:gridcolumn dataField="ip" headingText="登陆ip"></sicp3:gridcolumn>
					<sicp3:gridcolumn dataField="logontime" headingText="登陆时间"></sicp3:gridcolumn>
					<sicp3:gridcolumn headingText="操作"
						dataCellClientTemplateId="EditTemplate"
						editControlType="EditCommand" width="80" align="Center"></sicp3:gridcolumn>
				</sicp3:gridcolumns>
			</sicp3:gridlevel>
		</sicp3:gridlevels>

		<sicp3:clienttemplate id="EditTemplate">
			<a
				href="javascript:deleteRow('## DataItem.ClientId ##','/sysmanager/logonHistoryAction.do?method=delete')">删除</a>
		</sicp3:clienttemplate>
	</sicp3:grid>

	<sicp3:buttons>
		<tr>
			<td><sicp3:button value="全部删除" onclick="delAll()" /> <sicp3:button
				value=" 导  出 " onclick="exportData()" /> <sicp3:button
				value=" 关  闭 " onclick="closeWindow('logonHistoryForm')" /></td>
		</tr>
	</sicp3:buttons>
	<sicp3:bottom />
	<sicp3:errors />
	<form name="exportForm" method="POST"></form>
	<iframe name="hidden" width="0" height="0" /> </iframe>
</sicp3:body>
</html>
