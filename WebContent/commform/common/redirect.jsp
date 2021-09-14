<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<sicp3:base />
<sicp3:body>
	<sicp3:errors />
	<%
	//  response.sendRedirect((String)request.getAttribute("rurl"));
	%>
	<script language="javascript">
function page_init() {
  location.href='<%=(String)request.getAttribute("url")%>';
}
</script>
</sicp3:body>
</html>
