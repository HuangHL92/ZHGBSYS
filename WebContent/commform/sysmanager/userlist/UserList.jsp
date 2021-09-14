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
				<td width="46%">机构列表</td>
			</tr>
			<tr>
				<td valign="top" height="360"><sicp3:tree property='group'
					condition="null" /></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
<script>
   function editgroup(name,groupid){
    var right = parent.document.all("right");
    right.src="userListAction.do?method=findByKey&key="+groupid;
   }
</script>