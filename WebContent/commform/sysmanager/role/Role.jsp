<%@ page contentType="text/html; charset=GBK" language="java"
	pageEncoding="gbk"%>
<%@ page
	import="com.lbs.apps.query.QueryInfo,com.lbs.commons.GlobalNames"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%
String ctxpath = request.getContextPath();
%>
<LINK href="<%=ctxpath%>/css/gridStyle.css" type=text/css rel=stylesheet>
<LINK href="<%=ctxpath%>/css/baseStyle.jsp" type=text/css rel=stylesheet>

<script language="javascript">

function editData(){
    var t = Grid1.Table.CheckSelectedRow();
    if(!t){
       alert("请选择相关的业务数据！");
       return false;
    }
  window.location.href="<sicp3:rewrite page='/roleAction.do?method=findByKey'/>" +"&" + Grid1.Table.GetSelectedRow();
}
//-->
</script>

<script type="text/javascript">
  function onDelete(item)
  {
      if (confirm("Delete record?"))
        return true;
      else
        return false;
  }

  function deleteRow(rowId,actionURI)
  {
    actionURI="/sysmanager/roleAction.do?method=delete" ;
    Grid1.Delete(Grid1.GetRowFromClientId(rowId),null,actionURI);
  }

  function onCallbackError(excString)
  {
    alert("查询出错！");
  }

  function onUpdate(item)
  {
      if (confirm("Update record?"))
        return true;
      else
        return false;
  }
  function editGrid(rowId)
  {
    Grid1.Edit(Grid1.GetRowFromClientId(rowId));
  }

  function editRow(actionUrl)
  {
    Grid1.EditComplete(null,actionUrl);
  }
  function ClientSideOnAfterCallback(paramStr)
  {
    var cstr="角色信息修改成功,是否继续给该角色授权，点击［确定］继续！";
    var r=Grid1.Response_Data;
    if(null==r){
       return ;
    }
    if(r.actiontype=="update"){
       cstr="角色信息修改成功,是否继续给该角色授权，点击［确定］继续！";
    }
    if(r.actiontype=="add"){
      cstr="角色信息添加成功,是否继续给该角色授权，点击［确定］继续！";
    }
      var roleid=r.roleid;
      if(roleid==null||""==roleid){
      }else{
        var t=confirm(cstr);
        if(t){
          window.location.href="<sicp3:rewrite page='/roleAction.do?method=findFunctionListByRoleid'/>&roleid="+roleid;
	    }
    }


  }
  function onInsert(item)
  {
      if (confirm("Insert record?"))
        return true;
      else
        return false;
  }
  function insertRow(actionUrl)
  {
    actionUrl="/sysmanager/roleAction.do?method=add";
    Grid1.EditComplete(null,actionUrl);
  }
  </script>


<html>
<sicp3:base />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<sicp3:body>
	<sicp3:title title="角色管理" />
	<sicp3:query action="/roleQueryAction.do?method=query" topic="角色查询"
		notitle="false">
		<tr>
			<sicp3:texteditor property="roleName" label="角色名称" disable="false"></sicp3:texteditor>
			<sicp3:texteditor property="roledesc" label="角色描述" disable="false"></sicp3:texteditor>
		</tr>
	</sicp3:query>
	<sicp3:grid id="Grid1" tagTemplateId="grid" width="100%"
		requestURI="/sysmanager/roleAction.do?method=commonQuery"
		runningMode="callback" pagerImagesFolderUrl="images/pager/">
		<sicp3:gridlevels>
			<sicp3:gridlevel dataMember="querydata" dataKeyField="userid"
				tagTemplateId="gridlevel"
				tableHeadingClientTemplateId="TableHeadingTemplate"
				insertCommandClientTemplateId="InsertCommandTemplate"
				editCommandClientTemplateId="EditCommandTemplate">

				<sicp3:gridcolumns>
					<sicp3:gridcolumn dataField="roleid" headingText="序号"
						allowEditing="false"></sicp3:gridcolumn>
					<sicp3:gridcolumn dataField="roleName" headingText="角色名称"></sicp3:gridcolumn>
					<sicp3:gridcolumn dataField="roledesc" headingText="角色描述"></sicp3:gridcolumn>
					<sicp3:gridcolumn headingText="操作"
						dataCellClientTemplateId="EditTemplate"
						editControlType="EditCommand" width="100" align="Center"></sicp3:gridcolumn>
				</sicp3:gridcolumns>
			</sicp3:gridlevel>
		</sicp3:gridlevels>

		<sicp3:clienttemplate id="EditTemplate">
			<a href="javascript:editGrid('## DataItem.ClientId ##');">编辑</a> | <a
				href="javascript:deleteRow('## DataItem.ClientId ##','/sysmanager/roleAction.do?method=delete')">删除</a>
		</sicp3:clienttemplate>

		<sicp3:clienttemplate id="EditCommandTemplate">
			<a
				href="javascript:editRow('/sysmanager/roleAction.do?method=update');">修改</a> | <a
				href="javascript:Grid1.EditCancel();">取消</a>
		</sicp3:clienttemplate>

		<sicp3:clienttemplate id="InsertCommandTemplate">
			<a
				href="javascript:insertRow('/sysmanager/roleAction.do?method=add&stringData=stringData');">插入</a> | <a
				href="javascript:Grid1.EditCancel();">取消</a>
		</sicp3:clienttemplate>
	</sicp3:grid>


	<sicp3:buttons>
		<tr>
			<td><sicp3:button value="增 加" onclick="Grid1.Table.AddRow()" />
			<sicp3:button value="关 闭" onclick="closeWindow('roleQueryForm')" /></td>
		</tr>
	</sicp3:buttons>
	<sicp3:bottom />
	<sicp3:errors />
</sicp3:body>
</html>
