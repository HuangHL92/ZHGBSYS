<%@ page contentType="text/html; charset=GBK" language="java" pageEncoding="gbk"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3" %>
<script src="<sicp3:rewrite forward="leaftree"/>"></script>
<sicp3:base/>
<script language="javascript">
function Confirm(){
 document.dataPopedomForm.action="<sicp3:rewrite page='/dataPopedomAction.do?method=savePepedomMapping'/>";
 document.dataPopedomForm.submit();
}
</script>
<html>
<head>
<link href="<sicp3:rewrite forward="css"/>" rel="stylesheet" type="text/css">

<sicp3:body>

<%
    String popedomname =(String)request.getAttribute("popedomname");
	String title = "当前角色为:"+popedomname;
%>

  <sicp3:form action="/dataPopedomAction.do?method=savePepedomMapping" method="post">
  <sicp3:tabletitle title="<%=title%>" />

  <table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="tableInput">
  <tr>
    <td width="20%" height="0" align="center">地&nbsp;&nbsp;&nbsp;&nbsp;区：&nbsp;&nbsp;<br></td>
	<td width="45%"><sicp3:text property="aaa021" style="cursor:hand"
					styleClass="text"
					onclick="setRegionnewTree(this,document.all.aaa021,document.all.aaa020)"
					readonly="true" />
	</td>
	  <sicp3:hidden property="aaa020" />
    <td>
    <sicp3:checkbox property="aaa020flag" value = "1" > &nbsp;&nbsp;&nbsp;是否包含下级&nbsp;&nbsp;&nbsp;&nbsp;</sicp3:checkbox>
    </td>
  </tr>
  <tr>
    <sicp3:codeselecteditor property="aae140"  label="险种类型：&nbsp;&nbsp;"  redisplay="true"/>
    <td>
    <sicp3:checkbox property="aae140flag" value="1" >&nbsp;&nbsp;&nbsp;是否包含下级&nbsp;&nbsp;&nbsp;&nbsp;</sicp3:checkbox>
    </td>
  </tr>
  <tr>
  	<sicp3:codeselecteditor property="aab019"  label="单位类型：&nbsp;&nbsp;"  redisplay="true"/>
  	<td>&nbsp;
  	</td>
  </tr>
  <tr>
  <td>
  &nbsp;
  </td>
  <td>
  &nbsp;
  </td>
  <td>
  <sicp3:checkbox property="rwflag" value="0" >&nbsp;&nbsp;&nbsp;&nbsp;是&nbsp;否&nbsp;只&nbsp;读&nbsp;&nbsp;&nbsp;&nbsp;</sicp3:checkbox>
  </td>
  </tr>
  <sicp3:hidden property="popedomid"/>
   <sicp3:buttons  buttonMeta="button">
   <tr>
			<td align="right"><sicp3:button value="保 存[S]" onclick="Confirm()" />
			</td>
	</tr>
   </sicp3:buttons>
   </table>
  </sicp3:form>
<sicp3:errors/>
</sicp3:body>
</html>
