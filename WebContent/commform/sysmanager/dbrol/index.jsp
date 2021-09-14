<%@ page contentType="text/html; charset=GBK" language="java"
	pageEncoding="gbk"%>
<%@ page
	import="java.util.List,com.lbs.apps.query.QueryInfo,com.lbs.commons.GlobalNames,com.lbs.cp.sysmanager.bizdigest.entity.BizDigestinfo"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%
String ctxpath = request.getContextPath();
%>
<LINK href="<%=ctxpath%>/css/gridStyle.css" type=text/css rel=stylesheet>
<LINK href="<%=ctxpath%>/css/baseStyle.jsp" type=text/css rel=stylesheet>
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
		List infolist = (List) pageContext
				.getAttribute("BizDigestInfoList");
		if (infolist == null) {
			infolist = (List) request.getAttribute("BizDigestInfoList");
		}
		//    pageContext.setAttribute("orderInPage",orderInPage);
	%>
	<sicp3:title title="摘要" />
	<xartui:hidden property="stringData" value="<%=stringData%>" />
	<sicp3:query action="/bizdigestAction.do?method=query"
		topic="数据摘要查询" notitle="false">
		<sicp3:texteditor property="opseno" label="业务编号"></sicp3:texteditor>
	</sicp3:query>
	<%
	if (infolist != null && infolist.size() > 0) {
	%>
	<sicp3:grid id="Grid1" tagTemplateId="grid" width="100%"
		requestURI="/rolLogAction.do?method=commonQuery"
		runningMode="callback" pagerImagesFolderUrl="images/pager/">
		<sicp3:gridlevels>
			<sicp3:gridlevel dataMember="querydata" dataKeyField="opseno"
				tagTemplateId="gridlevel"
				tableHeadingClientTemplateId="TableHeadingTemplate">
				<sicp3:gridcolumns>
					<%
								if (infolist != null || infolist.size() > 0) {
								for (int i = 0; i < infolist.size(); i++) {
							BizDigestinfo info = (BizDigestinfo) infolist.get(i);
							if ("1".equals(info.getVisible())) {
					%>
					<sicp3:gridcolumn dataField="<%=info.getFieldname()%>"
						headingText="<%=info.getFielddesc()%>"></sicp3:gridcolumn>
					<%
								}
								}
							}
					%>
				</sicp3:gridcolumns>
			</sicp3:gridlevel>
		</sicp3:gridlevels>
		<sicp3:clienttemplate id="TableHeadingTemplate">
            查询结果:
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
	<%
	}
	%>
	<sicp3:bottom />
	<sicp3:errors />
</sicp3:body>
</html>
