<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3" %>
<html>
<head>
<title>
数据权限角色列表
</title>
</head>
<link href="<sicp3:rewrite forward="css"/>" rel="stylesheet" type="text/css">
<body>
<table width="100%">
  <tr>
    <td>
        <table class="divTable" border="0" cellPadding="0" cellSpacing="0" width="100%">
          <tr>
              <td align="center"><font  face=Verdana color="#ffffff"><strong>数据权限角色列表</strong></FONT></td>


          </tr>
        </table>
    </td>
  </tr>
<tr><td>
<br/>
<ol>
<logic:iterate id="role" name="allPopedomRoles">
	<li>
   	<sicp3:link target="right" styleClass="SLink" page="/dataPopedomAction.do?method=findFunctionListByPopedomid" paramId="popedomid" paramName="role" paramProperty="popedomid">
   		<bean:write name="role" property="popedomname"/>
   	</sicp3:link>
   	</li><br/>
</logic:iterate>
</ol></td>
</tr>
</table>
<sicp3:errors/>
</body>
</html>
