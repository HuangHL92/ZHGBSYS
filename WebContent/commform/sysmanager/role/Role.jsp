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
       alert("��ѡ����ص�ҵ�����ݣ�");
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
    alert("��ѯ����");
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
    var cstr="��ɫ��Ϣ�޸ĳɹ�,�Ƿ�������ý�ɫ��Ȩ�������ȷ���ݼ�����";
    var r=Grid1.Response_Data;
    if(null==r){
       return ;
    }
    if(r.actiontype=="update"){
       cstr="��ɫ��Ϣ�޸ĳɹ�,�Ƿ�������ý�ɫ��Ȩ�������ȷ���ݼ�����";
    }
    if(r.actiontype=="add"){
      cstr="��ɫ��Ϣ��ӳɹ�,�Ƿ�������ý�ɫ��Ȩ�������ȷ���ݼ�����";
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
	<sicp3:title title="��ɫ����" />
	<sicp3:query action="/roleQueryAction.do?method=query" topic="��ɫ��ѯ"
		notitle="false">
		<tr>
			<sicp3:texteditor property="roleName" label="��ɫ����" disable="false"></sicp3:texteditor>
			<sicp3:texteditor property="roledesc" label="��ɫ����" disable="false"></sicp3:texteditor>
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
					<sicp3:gridcolumn dataField="roleid" headingText="���"
						allowEditing="false"></sicp3:gridcolumn>
					<sicp3:gridcolumn dataField="roleName" headingText="��ɫ����"></sicp3:gridcolumn>
					<sicp3:gridcolumn dataField="roledesc" headingText="��ɫ����"></sicp3:gridcolumn>
					<sicp3:gridcolumn headingText="����"
						dataCellClientTemplateId="EditTemplate"
						editControlType="EditCommand" width="100" align="Center"></sicp3:gridcolumn>
				</sicp3:gridcolumns>
			</sicp3:gridlevel>
		</sicp3:gridlevels>

		<sicp3:clienttemplate id="EditTemplate">
			<a href="javascript:editGrid('## DataItem.ClientId ##');">�༭</a> | <a
				href="javascript:deleteRow('## DataItem.ClientId ##','/sysmanager/roleAction.do?method=delete')">ɾ��</a>
		</sicp3:clienttemplate>

		<sicp3:clienttemplate id="EditCommandTemplate">
			<a
				href="javascript:editRow('/sysmanager/roleAction.do?method=update');">�޸�</a> | <a
				href="javascript:Grid1.EditCancel();">ȡ��</a>
		</sicp3:clienttemplate>

		<sicp3:clienttemplate id="InsertCommandTemplate">
			<a
				href="javascript:insertRow('/sysmanager/roleAction.do?method=add&stringData=stringData');">����</a> | <a
				href="javascript:Grid1.EditCancel();">ȡ��</a>
		</sicp3:clienttemplate>
	</sicp3:grid>


	<sicp3:buttons>
		<tr>
			<td><sicp3:button value="�� ��" onclick="Grid1.Table.AddRow()" />
			<sicp3:button value="�� ��" onclick="closeWindow('roleQueryForm')" /></td>
		</tr>
	</sicp3:buttons>
	<sicp3:bottom />
	<sicp3:errors />
</sicp3:body>
</html>
