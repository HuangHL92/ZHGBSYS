<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<LINK href="/sicp3/css/default.css" type=text/css rel=stylesheet>
<LINK href="/sicp3/css/gridStyle.css" type=text/css rel=stylesheet>
<LINK href="/sicp3/css/baseStyle.jsp" type=text/css rel=stylesheet>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>Insert title here</title>
</head>
<script type="text/javascript">
  function onInsert(item)
  {
      if (confirm("Insert record?"))
        return true;
      else
        return false;
  }

  function onUpdate(item)
  {
      if (confirm("Update record?"))
        return true;
      else
        return false;
  }

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

  function editGrid(rowId)
  {
    Grid1.Edit(Grid1.GetRowFromClientId(rowId));
  }

  function editRow()
  {
    Grid1.EditComplete();
  }

  function insertRow()
  {
    Grid1.EditComplete();
  }

  function deleteRow(rowId)
  {
    Grid1.Delete(Grid1.GetRowFromClientId(rowId));
  }

      function Picker1_OnDateChange()
      {
        var fromDate = Grid1_EditTemplate_0_3_Picker1.GetSelectedDate();
        Grid1_EditTemplate_0_3_Calendar1.SetSelectedDate(fromDate);
      }

      function Calendar1_OnChange()
      {
        var fromDate = Grid1_EditTemplate_0_3_Calendar1.GetSelectedDate();
        Grid1_EditTemplate_0_3_Picker1.SetSelectedDate(fromDate);
      }

      function Button_OnClick(button)
      {
        if (Grid1_EditTemplate_0_3_Calendar1.PopUpObjectShowing)
        {
          Grid1_EditTemplate_0_3_Calendar1.Hide();
        }
        else
        {
          Grid1_EditTemplate_0_3_Calendar1.SetSelectedDate(Grid1_EditTemplate_0_3_Picker1.GetSelectedDate());
          Grid1_EditTemplate_0_3_Calendar1.Show(button);
        }
      }

      function Button_OnMouseUp()
      {
        if (Grid1_EditTemplate_0_3_Calendar1.PopUpObjectShowing)
        {
          event.cancelBubble=true;
          event.returnValue=false;
          return false;
        }
        else
        {
          return true;
        }
      }

  </script>
<body>
<sicp3:base/>
<sicp3:body>
<%
%>
<sicp3:title title="快捷键维护"/>
<sicp3:query action="/shortcutkeyAction.do?method=query"  topic="查询信息">
<tr>
<sicp3:texteditor property="parameterid" label="参数ID" disable="false"></sicp3:texteditor>
<sicp3:texteditor property="parametername" label="参数名" disable="false"></sicp3:texteditor>
</tr>
<tr>
<sicp3:texteditor property="parametertype" label="参数类型" disable="false"></sicp3:texteditor>
<sicp3:texteditor property="parametervalue" label="参数值" disable="false"></sicp3:texteditor>
</tr>
</sicp3:query>
<sicp3:grid id="Grid1" enableViewState="true"
	editOnClickSelectedItem="false" allowEditing="true" showHeader="False"
	cssClass="Grid" keyboardEnabled="false" footerCssClass="GridFooter"
	runningMode="Callback" pagerStyle="Numbered"
	pagerTextCssClass="PagerText" pageSize="10" autoCallBackOnInsert="true"
	autoCallBackOnUpdate="true" autoCallBackOnDelete="true"
	clientSideOnDelete="onDelete"
	clientSideOnCallbackError="onCallbackError" width="710" height="250"
	requestURI="/sysmanager/shortcutkeyAction.do?method=commonQuery"
	loadingPanelClientTemplateId="LoadingFeedbackTemplate"
	loadingPanelPosition="MiddleCenter">
	<sicp3:gridlevels>
		<sicp3:gridlevel dataMember="querydata"
			dataKeyField="id" showTableHeading="true"
			tableHeadingCssClass="GridHeader"
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
			editFieldCssClass="EditDataField"
        	tableHeadingClientTemplateId="TableHeadingTemplate"
        	editCommandClientTemplateId="EditCommandTemplate"
			insertCommandClientTemplateId="InsertCommandTemplate">
			<sicp3:gridcolumns>
				<sicp3:gridcolumn dataField="parameterid" headingText="参数ID" width="100"  allowEditing="false"></sicp3:gridcolumn>
				<sicp3:gridcolumn dataField="parametername" headingText="参数名"></sicp3:gridcolumn>
				<sicp3:gridcolumn dataField="parametertype" headingText="参数类型"></sicp3:gridcolumn>
				<sicp3:gridcolumn dataField="parametervalue" headingText="参数值"></sicp3:gridcolumn>
				<sicp3:gridcolumn dataField="parameterdesc" headingText="参数描述"></sicp3:gridcolumn>
				<sicp3:gridcolumn headingText="编辑 命令" dataCellClientTemplateId="EditTemplate"	editControlType="EditCommand" width="100" align="Center"></sicp3:gridcolumn>
			</sicp3:gridcolumns>
		</sicp3:gridlevel>
	</sicp3:gridlevels>
	<sicp3:gridClienttemplates>

		<sicp3:gridClienttemplate id="EditTemplate" >
			<a href="javascript:editGrid('## DataItem.ClientId ##');">编辑</a> | <a
				href="javascript:deleteRow('## DataItem.ClientId ##')">删除</a>
		</sicp3:gridClienttemplate>
		<sicp3:gridClienttemplate id="EditCommandTemplate">
			<a href="javascript:editRow();">更新</a> | <a
				href="javascript:Grid1.EditCancel();">取消</a>
		</sicp3:gridClienttemplate>
		<sicp3:gridClienttemplate id="InsertCommandTemplate">
			<a href="javascript:insertRow();">插入</a> | <a
				href="javascript:Grid1.EditCancel();">取消</a>
		</sicp3:gridClienttemplate>

		<sicp3:gridClienttemplate id="TableHeadingTemplate">
            查询结果:
          </sicp3:gridClienttemplate>



		<sicp3:gridClienttemplate id="LoadingFeedbackTemplate">
			<table cellspacing="0" cellpadding="0" border="0" class="ltable">
				<tr>
					<td style="font-size:10px;">加载...&nbsp;</td>
					<td><img src="common/images/spinner.gif" width="16" height="16"
						border="0"></td>
				</tr>
			</table>
		</sicp3:gridClienttemplate>
	</sicp3:gridClienttemplates>
</sicp3:grid>
</sicp3:body><sicp3:shortkey/>
</body>
</html>