<%@ page contentType="text/html; charset=GBK" language="java" pageEncoding="gbk"%>
<%@ page import="com.lbs.apps.query.QueryInfo,com.lbs.commons.GlobalNames"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%
String ctxpath = request.getContextPath();
%>
<link href="<%=ctxpath+"/css/baseStyle.jsp" %>" type="text/css" rel="stylesheet" />
<link href="<%=ctxpath+"/css/gridStyle.css"%>" type="text/css" rel="stylesheet" />
<script language="javascript">
function editData(){
    var t = Grid1.Table.CheckSelectedRow();
    if(!t){
       alert("请选择相关的业务数据！");
       return false;
    }
  window.location.href="<sicp3:rewrite page='/dataPopedomAction.do?method=findByKey'/>&stringData=" +
    document.all("stringData").value + "&" + Grid1.Table.GetSelectedRow();
}
</script>
<script type="text/javascript">
function onDelete(item)
  {
      if (confirm("确定删除吗?"))
        return true;
      else
        return false;
  }

  function deleteRow(rowId,actionURI)
  {
    actionURI="/sysmanager/dataPopedomAction.do?method=deletePopedom&stringData=" +document.all("stringData").value;
    Grid1.Delete(Grid1.GetRowFromClientId(rowId),null,actionURI);
  }

  function onCallbackError(excString)
  {
    alert("查询出错！");
  }

  function onUpdate(item)
  {
      if (confirm("确认修改吗?"))
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
      var popedomid=r.popedomid;
      if(popedomid==null||""==popedomid){
      }else{
        var t=confirm(cstr);
        if(t){
          window.location.href="<sicp3:rewrite page='/dataPopedomAction.do?method=findFunctionListByPopedomid'/>&popedomid="+popedomid;
	    }
    }


  }
  function onInsert(item)
  {
      if (confirm("确认新增吗?"))
        return true;
      else
        return false;
  }
  function insertRow(actionUrl)
  {
    actionUrl="/sysmanager/dataPopedomAction.do?method=addPopedom&stringData="+document.all("stringData").value;
    Grid1.EditComplete(null,actionUrl);
  }
</script>
<html><sicp3:base/>
<meta http-equiv="Content-Type" content="text/html; charset=gbk"><sicp3:body>
<%
	String stringData = "";
	QueryInfo qi = (QueryInfo)pageContext.findAttribute(GlobalNames.QUERY_INFO);
    if(null != qi){
    	stringData = qi.getStringData();
    }

	//String addRole = "window.location.href=\"" + request.getContextPath() + "/sysmanager/roleAction.do?method=addLink&stringData="+stringData+"\"";
%>
<sicp3:title title="数据权限角色管理"/>
<sicp3:query action="/dataPopedomQueryAction.do?method=popedomQuery"  topic="数据权限查询" notitle="false">
		<tr>
			<sicp3:texteditor property="popedomname" label="数据权限名称" disable="false"></sicp3:texteditor>
			<sicp3:texteditor property="popedomdesc" label="数据权限描述" disable="false"></sicp3:texteditor>
		</tr>
</sicp3:query>
<sicp3:hidden property="stringData" value="<%=stringData%>"/>
<sicp3:grid id="Grid1" tagTemplateId="grid"
	runningMode="Callback" pagerStyle="Numbered"
	pagerTextCssClass="PagerText"
	requestURI="/sysmanager/dataPopedomAction.do?method=commonQuery">
	<sicp3:gridlevels>
		<sicp3:gridlevel dataMember="querydata"
			dataKeyField="popedomid"
			tagTemplateId="gridlevel"
			editCommandClientTemplateId="EditCommandTemplate"
			insertCommandClientTemplateId="InsertCommandTemplate"
        	tableHeadingClientTemplateId="TableHeadingTemplate">
			<sicp3:gridcolumns>
				<sicp3:gridcolumn dataField="popedomid" headingText="序号" allowEditing="false"></sicp3:gridcolumn>
				<sicp3:gridcolumn dataField="popedomname" headingText="数据权限名称"></sicp3:gridcolumn>
				<sicp3:gridcolumn dataField="popedomdesc" headingText="数据权限描述"></sicp3:gridcolumn>
				<sicp3:gridcolumn headingText="操 作" dataCellClientTemplateId="EditTemplate"	editControlType="EditCommand" width="100" align="Center"></sicp3:gridcolumn>
			</sicp3:gridcolumns>
		</sicp3:gridlevel>
	</sicp3:gridlevels>
		<sicp3:clienttemplate id="EditTemplate">
		  <a href="javascript:editGrid('## DataItem.ClientId ##');">编辑</a> | <a href="javascript:deleteRow('## DataItem.ClientId ##','/sysmanager/dataPopedomAction.do?method=deletePopedom')">删除</a>
		</sicp3:clienttemplate>

		<sicp3:clienttemplate id="EditCommandTemplate">
			<a href="javascript:editRow('/sysmanager/dataPopedomAction.do?method=updatePopedom');">确认</a> | <a href="javascript:Grid1.EditCancel();">取消</a>
		</sicp3:clienttemplate>

		<sicp3:clienttemplate id="InsertCommandTemplate">
			<a href="javascript:insertRow('/sysmanager/dataPopedomAction.do?method=addPopedom&stringData=stringData');">新增</a> | <a href="javascript:Grid1.EditCancel();">取消</a>
		</sicp3:clienttemplate>

		<sicp3:clienttemplate id="LoadingFeedbackTemplate">
			<table cellspacing="0" cellpadding="0" border="0" class="ltable">
				<tr>
					<td style="font-size:10px;">Loading...&nbsp;</td>
					<td><img src="common/images/spinner.gif" width="16" height="16"
						border="0"></td>
				</tr>
			</table>
		</sicp3:clienttemplate>

</sicp3:grid>
<sicp3:buttons buttonMeta="button">
	<tr>
			<td align="right"><sicp3:button value="增 加" onclick="Grid1.Table.AddRow()" />
			<sicp3:button value="关 闭" onclick="closeWindow('roleQueryForm')" /></td>
	</tr>
</sicp3:buttons>
<sicp3:bottom/>
<sicp3:errors/></sicp3:body></html>
