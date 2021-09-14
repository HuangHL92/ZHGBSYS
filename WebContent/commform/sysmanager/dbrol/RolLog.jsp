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

<script type="text/javascript">

  function onCallbackError(excString)
  {
    alert("查询出错！");
  }

  function dbrol(rowid)
  {
  	if (confirm("要回退此业务吗？")) {
    var actionUrl="/sysmanager/rolLogAction.do?method=rol&opseno="+
	Grid1.Table.GetGridRow(rowid,null,"opseno");
	Grid1.Edit(Grid1.GetRowFromClientId(rowid));
    Grid1.EditComplete(1,actionUrl);
  	}
  }
  </script>


<html>
<sicp3:base />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<sicp3:body>
	<%
		String stringData = "";
		QueryInfo qi = (QueryInfo) pageContext
				.findAttribute(GlobalNames.QUERY_INFO);
		if (null != qi) {
			stringData = qi.getStringData();
		}
	%>

	<sicp3:title title="回退日志管理" />
	<sicp3:query action="/rolLogAction.do?method=queryRolLog"
		topic="回退日志查询" notitle="false" columSum="3">
		<tr>
		<sicp3:texteditor property="functionid" label="功能编号"></sicp3:texteditor>
		<sicp3:texteditor property="aae002s" label="业务年月始于" mask="date"></sicp3:texteditor>
		<sicp3:texteditor property="aae002e" label="业务年月止于" mask="date"></sicp3:texteditor>
		</tr>
		<tr>
		<sicp3:texteditor property="aae011" label="经办人"></sicp3:texteditor>
		<sicp3:texteditor property="aae036s" label="经办日期始于" mask="date"></sicp3:texteditor>
		<sicp3:texteditor property="aae036e" label="经办日期止于" mask="date"></sicp3:texteditor>
		</tr>
		<sicp3:texteditor property="eae024" label="回退标志"></sicp3:texteditor>
	</sicp3:query>
	<sicp3:hidden property="stringData" value="<%=stringData%>" />

	<sicp3:grid id="Grid1" tagTemplateId="grid" width="100%"
		requestURI="/sysmanager/rolLogAction.do?method=commonQuery"
		runningMode="callback" pagerImagesFolderUrl="images/pager/">
		<sicp3:gridlevels>
			<sicp3:gridlevel dataMember="querydata" dataKeyField="opseno"
				tagTemplateId="gridlevel"
				tableHeadingClientTemplateId="TableHeadingTemplate">

				<sicp3:gridcolumns>
					<sicp3:gridcolumn dataField="opseno" headingText="业务号"
						allowEditing="false" visible="true"></sicp3:gridcolumn>
					<sicp3:gridcolumn dataField="functionid" headingText="功能编号"></sicp3:gridcolumn>
					<sicp3:gridcolumn dataField="aae002" headingText="业务年月"></sicp3:gridcolumn>
					<sicp3:gridcolumn dataField="aac001" headingText="个人编号"></sicp3:gridcolumn>
					<sicp3:gridcolumn dataField="aab001" headingText="单位编号"></sicp3:gridcolumn>
					<sicp3:gridcolumn dataField="aae011" headingText="经办人"></sicp3:gridcolumn>
					<sicp3:gridcolumn dataField="aae036" headingText="经办日期"></sicp3:gridcolumn>
					<sicp3:gridcolumn dataField="aab034" headingText="社会保险经办机构编码"></sicp3:gridcolumn>
					<sicp3:gridcolumn dataField="aae013" headingText="备注"
						visible="false"></sicp3:gridcolumn>
					<sicp3:gridcolumn dataField="eae024" headingText="回退标志"></sicp3:gridcolumn>
					<sicp3:gridcolumn dataField="eae124" headingText="回退人"></sicp3:gridcolumn>
					<sicp3:gridcolumn dataField="eae125" headingText="回退日期"></sicp3:gridcolumn>
					<sicp3:gridcolumn headingText="操作"
						dataCellClientTemplateId="EditCommandTemplate"
						editControlType="EditCommand" width="100" align="Center"></sicp3:gridcolumn>
				</sicp3:gridcolumns>
			</sicp3:gridlevel>
		</sicp3:gridlevels>

		<sicp3:clienttemplate id="TableHeadingTemplate">
            回退日志查询结果:
          </sicp3:clienttemplate>

		<sicp3:clienttemplate id="EditCommandTemplate">
			##
			if(DataItem.GetMember("eae024").Value == "1")
			{
				'<a href="javascript:dbrol(\\'' + DataItem.ClientId + '\\' )"><div style="display: none">回退</div></a>'
			}else{
				'<a href="javascript:dbrol(\\'' + DataItem.ClientId + '\\' )">回退</a>'
			}
			##
			</sicp3:clienttemplate>

		<sicp3:clienttemplate id="LoadingFeedbackTemplate">
			<table cellspacing="0" cellpadding="0" border="0" class="ltable">
				<tr>
					<td style="font-size: 10px;">正在加载页面...&nbsp;</td>
					<td><img src="common/images/spinner.gif" width="16"
						height="16" border="0"></td>
				</tr>
			</table>
		</sicp3:clienttemplate>
	</sicp3:grid>
	<sicp3:bottom />
	<sicp3:errors />
</sicp3:body>
</html>
