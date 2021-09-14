<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3" %>
<html>
<head>
<title>角色授权</title>
<script src="<sicp3:rewrite forward="leaf_Checkbox"/>"></script>
<script src="<sicp3:rewrite forward="leaf_Misc"/>"></script>
<script src="<sicp3:rewrite forward="xtree"/>"></script>
<script src="<sicp3:rewrite forward="xmlextras"/>"></script>
<script src="<sicp3:rewrite forward="leaf_TreeElement"/>"></script>
<script src="<sicp3:rewrite forward="leaf_Tree"/>"></script>
<link href='<sicp3:rewrite forward="treecss"/>' rel='stylesheet' type='text/css'>
<sicp3:base/>
</head>
<body>
<table width="100%">
  <tr>
    <td>
        <table class="divTable" border="0" cellPadding="0" cellSpacing="0" width="100%">
          <tr>
              <td width="46%"><font  face=Verdana color="#ffffff">
              <strong>当前角色为:<%=request.getAttribute("rolename") %>
              </strong></font>
              </td>
              <td width="27%" class="tableHead"></td>
              <td width="27%" class="tableHead"></td>
          </tr>
        </table>
    </td>
  </tr>
  <tr>
  <td>
<sicp3:form action="/roleAction.do?method=saveFunctionList" method="post">
<div id="treediv">
</div>
<script type="text/javascript">
   	webFXTreeConfig.loadingText = "加载中...";
	webFXTreeConfig.loadErrorTextTemplate = "Error loading \"%1%\"";
	webFXTreeConfig.emptyErrorTextTemplate = "Error \"%1%\" does not contain any tree items";
	var tree = new WebFXTree("功能资源树");
	<%=request.getAttribute("rightjs")%>
	document.all["treediv"].innerHTML=tree;
</script>
<input type="hidden" value=<%=request.getParameter("roleid")%> name="roleid"/>
<input type='submit' value='保存[S]' style="height:18pt;width:55;"/>
</sicp3:form>
</td>
   </tr>
</table>
<sicp3:errors/>
</body>
</html>