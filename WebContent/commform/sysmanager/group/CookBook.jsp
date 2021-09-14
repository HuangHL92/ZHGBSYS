<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3" %>
<html>
<head>
<title>
机构列表
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
  <ol><li>在左边的"机构列表"中选择想要操作的机构对象</li><br/>
  <li>你可以左键点击该机构，也可以选择右键菜单</li><br/>
  <li>若使用左键，则页面右边会出现该机构的信息，你可以选择底下的操作按钮操作</li><br/>
  <li>若使用右键，直接选择操作，则页面右边会出现对应的操作内容信息</li><br/>
  </ol>
  </td>
  </tr>
</table>
<sicp3:errors/>
</body>
</html>
