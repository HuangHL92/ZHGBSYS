
<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page
	import="java.util.List,com.lbs.commons.GlobalNames,com.lbs.apps.query.QueryInfo,java.util.ArrayList,java.util.Map,java.util.LinkedHashMap,com.lbs.leaf.CurrentUser"%>
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
function passwdReset(rowId){
    var t=Grid1.Table.GetGridRow(rowId);
    if(!t){
       alert("��ѡ����ص�ҵ�����ݣ�");
       return ;
    }
    if(confirm("�˲������ܻ��ˣ�ȷ��Ҫ������ѡ�е��û�������")){
       window.location.href="<sicp3:rewrite page='/userAction.do?method=passwdReset'/>&stringData=" +
      	document.all("stringData").value + "&" + Grid1.Table.GetGridRow(rowId);;
    }else{
      return ;
    }
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
  function editData(rowId)
  {
    var t=Grid1.Table.GetGridRow(rowId);
    if(!t){
       alert("��ѡ����ص�ҵ�����ݣ�");
       return false;
    }
  window.location.href="<sicp3:rewrite page='/userAction.do?method=findByKey'/>&stringData=" +
    document.all("stringData").value + "&" + Grid1.Table.GetGridRow(rowId);
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
  window.location.href="/sicp3/sysmanager/userAction.do?method=addLinkcr&stringData="+
    document.all("stringData").value;
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
		String addUser = "window.location.href=\""
				+ request.getContextPath()
				+ "/sysmanager/userAction.do?method=addLinkcr&stringData="
				+ stringData + "\"";
		CurrentUser cu = (CurrentUser) pageContext
				.findAttribute(GlobalNames.CURRENT_USER);
		List header = new ArrayList();

		Map hidden = new LinkedHashMap();
		hidden.put("userid", "�û�id");
	%>
	<sicp3:title title="�û�����" />
	<sicp3:query action="/userQueryAction.do?method=querycr"
		topic="�û�����2122">
		<tr>
			<sicp3:texteditor property="username" label="�û�����" disable="false"></sicp3:texteditor>
			<sicp3:texteditor property="operatorname" label="����Ա����"
				disable="false"></sicp3:texteditor>
		</tr>
		<tr>
			<sicp3:texteditor property="isleader" label="�Ƿ��쵼" disable="false"></sicp3:texteditor>
			<sicp3:texteditor property="description" label="����" disable="false"></sicp3:texteditor>
			<sicp3:texteditor property="useful" label="��Ч��־" disable="false"></sicp3:texteditor>
		</tr>
	</sicp3:query>

	<sicp3:hidden property="stringData" value="<%=stringData%>" />

	<sicp3:grid id="Grid1" enableViewState="true"
		editOnClickSelectedItem="false" allowEditing="true" showHeader="False"
		cssClass="Grid" keyboardEnabled="false" footerCssClass="GridFooter"
		runningMode="Callback" pagerStyle="Numbered"
		pagerTextCssClass="PagerText" pageSize="10"
		autoCallBackOnInsert="true" autoCallBackOnUpdate="true"
		autoCallBackOnDelete="true" clientSideOnDelete="onDelete"
		clientSideOnCallbackError="onCallbackError" width="710" height="250"
		requestURI="/logonHistoryAction.do?method=commonQuery"
		loadingPanelClientTemplateId="LoadingFeedbackTemplate"
		loadingPanelPosition="MiddleCenter">
		<sicp3:gridlevels>
			<sicp3:gridlevel dataMember="querydata" dataKeyField="roleid"
				showTableHeading="true" tableHeadingCssClass="GridHeader"
				showSelectorCells="true" selectorCellCssClass="SelectorCell"
				selectorCellWidth="18" selectorImageUrl="common/images/selector.gif"
				selectorImageWidth="17" selectorImageHeight="15"
				headingSelectorCellCssClass="SelectorCell"
				headingCellCssClass="HeadingCell" headingRowCssClass="HeadingRow"
				headingTextCssClass="HeadingCellText" dataCellCssClass="DataCell"
				rowCssClass="Row" selectedRowCssClass="SelectedRow"
				sortAscendingImageUrl="common/images/asc.gif"
				sortDescendingImageUrl="common/images/desc.gif" sortImageWidth="10"
				sortImageHeight="10" editCellCssClass="EditDataCell"
				editFieldCssClass="EditDataField">
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
						editControlType="EditCommand" align="Center" width="120"></sicp3:gridcolumn>
				</sicp3:gridcolumns>
			</sicp3:gridlevel>
		</sicp3:gridlevels>
		<sicp3:clienttemplate id="EditTemplate">
			<a href="javascript:editData('## DataItem.ClientId ##')">�޸�</a> | <a
				href="javascript:deleteData('## DataItem.ClientId ##','/sysmanager/userAction.do?method=delete')">ɾ��</a> | <a
				href="javascript:passwdReset('## DataItem.ClientId ##')">�޸�����</a>
		</sicp3:clienttemplate>
		<sicp3:clienttemplate id="LoadingFeedbackTemplate">
			<table cellspacing="0" cellpadding="0" border="0" class="ltable">
				<tr>
					<td style="font-size:10px;align=right">Loading...&nbsp;</td>
					<td align="left"><img src="common/images/spinner.gif"
						width="16" height="16" border="0"></td>
				</tr>
			</table>
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
