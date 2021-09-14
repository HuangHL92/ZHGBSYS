<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<html>
<head>
<link href="<sicp3:rewrite forward="treecss"/>" rel="stylesheet"
	type="text/css">
<link href="<sicp3:rewrite forward="css"/>" rel="stylesheet"
	type="text/css">
<script src="<sicp3:rewrite forward='xtree'/>"></script>
<script src="<sicp3:rewrite forward='xmlextras'/>"></script>
<script src="<sicp3:rewrite forward='xloadtree'/>"></script>
<script src="<sicp3:rewrite forward="globals"/>"></script>
<script src="<sicp3:rewrite forward="leaftree"/>"></script>
</head>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
   padding:0px 0px 0% 0px;
}
-->
</style>
<body style="overflow-y:scroll;overflow-x:hidden;">
<table border="1" cellPadding="1" cellSpacing="1" bordercolor="#FFFFFF"
	width="100%">
	<tr>
		<td>
		<table width="100%">
			<tr>
				<td>
				<table class="divTable" border="0" cellPadding="0" cellSpacing="0"
					width="100%">
					<tr>
						<td width="46%"><font face=Verdana color="#ffffff"><strong>机构列表</strong></FONT></td>
						<td width="27%" class="tableHead"><font face=Verdana
							color="#ffffff"><span onclick="close_region_Frame()"
							style="cursor: hand">关闭</span></FONT></td>
						<td width="27%" class="tableHead"><font face=Verdana
							color="#ffffff"><span onclick="region_clearvalue_tree()"
							style="cursor: hand" align="right">清空</span></FONT></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td>
				<table class="divClass" bgcolor="#ffffff" width="100%">
					<%
						String condition = request.getParameter("condition");
						if (null == condition)
							condition = "open";
					%>
					<tr>
						<td valign="top" height="360"><sicp3:tree property='group'
							condition="<%=condition%>" /></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
