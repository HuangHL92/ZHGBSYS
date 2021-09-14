<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ page
	import="java.util.Map,java.util.LinkedHashMap"%>

<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3" %>
<html>
<head>
<title>初始化回退业务表</title>
<sicp3:base/>
<script language="javascript">
<!--
function reinit() {
 if (confirm("要重新初始化数据库业务表的回退信息吗？")) {
 window.location.href="<sicp3:rewrite page='/rolInitAction.do?method=initBizTables'/>";
 }
}
//-->
</script>
</head>
<sicp3:body>
<sicp3:title title="初始化回退业务表"/>
<%
Map buttons = new LinkedHashMap();
buttons.put("重新初始化", "reinit()");
pageContext.setAttribute("button", buttons);

%>
<sicp3:buttons buttonMeta="button"/>
<table class="tableInput">
	<tr>
		<td>
		<label>当数据库表结构修改时，需要重新初始化回退业务表。初始化功能会重新为所有业务表创建用于回退的触发器。</label>
		</td>
	</tr>
</table>
<sicp3:errors />
</sicp3:body>
</html>
