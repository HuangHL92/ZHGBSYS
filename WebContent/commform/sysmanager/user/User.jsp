<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page
	import="com.lbs.commons.GlobalNames,com.lbs.apps.query.QueryInfo"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<script src="<sicp3:rewrite forward="leaftree"/>"></script>
<script src="<sicp3:rewrite forward="quickSelectBase"/>"></script>
<script src="<sicp3:rewrite forward="quickSelectMgr"/>"></script>
<script src="<sicp3:rewrite forward="select"/>"></script>
<script src="<sicp3:rewrite forward="quickSelect"/>"></script>
<%
String ctxpath = request.getContextPath();
%>
<LINK href="<%=ctxpath%>/commform/css/gridStyle.css" type=text/css rel=stylesheet>
<LINK href="<%=ctxpath%>/commform/css/baseStyle.jsp" type=text/css rel=stylesheet>
<script language="javascript">

function passwdReset(){
    var t = Grid1.Table.CheckSelectedRow();
    if(!t){
       alert("��ѡ����ص�ҵ�����ݣ�");
       return false;
    }
    if(confirm("�˲������ܻ��ˣ�ȷ��Ҫ������ѡ�е��û�������")){
       window.location.href="<sicp3:rewrite page='/userAction.do?method=passwdReset'/>&stringData=" +
      	document.all("stringData").value + "&" + Grid1.Table.GetSelectedRow();
    }else{
      return false;
    }
}
function editData(){
    var t = Grid1.Table.CheckSelectedRow();
    if(!t){
       alert("��ѡ����ص�ҵ�����ݣ�");
       return false;
    }
  window.location.href="<sicp3:rewrite page='/userAction.do?method=findByKey'/>&stringData=" +
      	document.all("stringData").value  +"&" + Grid1.Table.GetSelectedRow();
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
    Grid1.Delete(Grid1.GetRowFromClientId(rowId),null,actionURI);
  }

  function onCallbackError(excString)
  {
    alert("��ѯ����!");
  }
   function addUser(){
  window.location.href="/sicp3/sysmanager/userAction.do?method=addLink&stringData="+document.all("stringData").value;
  }
  </script>



<html>
<sicp3:base />
<sicp3:body>
	<%
		String stringData = "";
		QueryInfo qi = (QueryInfo) pageContext
				.findAttribute(GlobalNames.QUERY_INFO);
		if (null != qi) {
			stringData = qi.getStringData();
		}
	%>

	<sicp3:title title="�û�����" />
	<TABLE class=tableTitle>
		<TR>
			<TD style="word-break:keep-all"><img
				src='<%=request.getContextPath()%>/commform/images/new_image/dot2.gif'>&nbsp;��ѯ����</TD>
		</TR>
	</TABLE>
	<table width="95%" border="0" align="center" cellpadding="3"
		cellspacing="0" class="queryTableInput">
		<sicp3:form action="/userQueryAction.do?method=query" method="post"
			onsubmit="return checkValue(this);">
			<sicp3:editorlayout />
			<tr>
				<sicp3:texteditor property="username" disable="false" maxlength="8"
					label="�û�����" />
				<td height="0" align="right">��������<br>
				</td>
				<td height="0"><sicp3:text property="name" style="cursor:hand"
					styleClass="text" maxlength="16" disable="false"
					onclick="setRegionTree(this,document.all.name,document.all.groupid)"
					readonly="true" /> <sicp3:hidden property="groupid" /></td>
				<sicp3:texteditor property="operatorname" disable="false"
					maxlength="8" label="����Ա����" />
			</tr>
			<tr>
				<td colSpan=6><input type='submit' name='method' value='��ѯ'
					class='buttonGray' />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
				<td><input type='button' name='method' value='����[R]'
					class='buttonGray' onclick='resetForm(userQueryForm)' /></td>
			</tr>
		</sicp3:form>
	</table>
	<sicp3:hidden property="stringData" value="<%=stringData%>" />
	<sicp3:grid id="Grid1" tagTemplateId="grid" width="100%"
		requestURI="/logonHistoryAction.do?method=commonQuery"
		runningMode="callback" pagerImagesFolderUrl="images/pager/">
		<sicp3:gridlevels>
			<sicp3:gridlevel dataMember="querydata" dataKeyField="userid"
				tagTemplateId="gridlevel"
				tableHeadingClientTemplateId="TableHeadingTemplate">

				<sicp3:gridcolumns>
					<sicp3:gridcolumn dataField="userid" headingText="�û�id"
						allowEditing="false"></sicp3:gridcolumn>
					<sicp3:gridcolumn dataField="username" headingText="�û�����"></sicp3:gridcolumn>
					<sicp3:gridcolumn dataField="operatorname" headingText="��������"></sicp3:gridcolumn>
					<sicp3:gridcolumn dataField="isleader" headingText="�Ƿ��쵼"></sicp3:gridcolumn>
					<sicp3:gridcolumn dataField="description" headingText="����"></sicp3:gridcolumn>
					<sicp3:gridcolumn dataField="useful" headingText="��Ч��־"></sicp3:gridcolumn>
					<sicp3:gridcolumn headingText="����"
						dataCellClientTemplateId="EditTemplate"
						editControlType="EditCommand" width="100" align="Center"></sicp3:gridcolumn>
				</sicp3:gridcolumns>
			</sicp3:gridlevel>
		</sicp3:gridlevels>
		<sicp3:clienttemplate id="EditTemplate">
			<a
				href="javascript:deleteRow('## DataItem.ClientId ##','/sysmanager/userAction.do?method=delete')">ɾ��</a>
		</sicp3:clienttemplate>
	</sicp3:grid>
	<sicp3:buttons>
		<tr>
			<td><sicp3:button value="��������" onclick="passwdReset()" /> <sicp3:button
				value=" �� �� " onclick="addUser()" /> <sicp3:button value=" �� �� "
				onclick="editData()" /> <sicp3:button value=" �� ��"
				onclick="closeWindow('userQueryForm')" /></td>
		</tr>
	</sicp3:buttons>
	<sicp3:bottom />
	<sicp3:errors />
</sicp3:body>
</html>
