<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ page
	import="java.util.Map,java.util.LinkedHashMap"%>

<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3" %>
<html>
<head>
<title>��ʼ������ҵ���</title>
<sicp3:base/>
<script language="javascript">
<!--
function reinit() {
 if (confirm("Ҫ���³�ʼ�����ݿ�ҵ���Ļ�����Ϣ��")) {
 window.location.href="<sicp3:rewrite page='/rolInitAction.do?method=initBizTables'/>";
 }
}
//-->
</script>
</head>
<sicp3:body>
<sicp3:title title="��ʼ������ҵ���"/>
<%
Map buttons = new LinkedHashMap();
buttons.put("���³�ʼ��", "reinit()");
pageContext.setAttribute("button", buttons);

%>
<sicp3:buttons buttonMeta="button"/>
<table class="tableInput">
	<tr>
		<td>
		<label>�����ݿ��ṹ�޸�ʱ����Ҫ���³�ʼ������ҵ�����ʼ�����ܻ�����Ϊ����ҵ��������ڻ��˵Ĵ�������</label>
		</td>
	</tr>
</table>
<sicp3:errors />
</sicp3:body>
</html>
