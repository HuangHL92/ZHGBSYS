<%@ page contentType="text/html; charset=GBK" language="java" %>

<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3" %>
<html>
<head>
<title>
角色列表
</title>
</head>
<link href="<sicp3:rewrite forward="css"/>" rel="stylesheet" type="text/css">
<body>
<table width="100%">
  <tr>
    <td>
        <table class="divTable" border="0" cellPadding="0" cellSpacing="0" width="100%">
          <tr>
              <td width="46%"><font  face=Verdana color="#ffffff"><strong>使用说明</strong></FONT></td>
              <td width="27%" class="tableHead"></td>
              <td width="27%" class="tableHead"></td>
          </tr>
        </table>
    </td>
  </tr>
  <tr>
  <td>
  <br/>
  <ol><li>在左边的"角色列表"中选择想要操作的角色对象</li><br/>
  <li>点击该角色</li><br/>
  <li>然后在本页面将出现一棵功能资源树，该展示了该角色当前拥有的功能资源，<br/>您可以通过点击树上的校验框来修改它，从而完成对该角色的授权操作</li><br/>
  </ol>
  </td>
  </tr>
</table>
<sicp3:errors/>
</body>
</html>
