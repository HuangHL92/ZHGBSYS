<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ page import="java.util.Map,java.util.Iterator,com.lbs.commons.GlobalNames"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3" %>
<html>
	<head>
        <link href="<sicp3:rewrite forward="css"/>" rel="stylesheet" type="text/css">
        <script src="<sicp3:rewrite forward="globals"/>"></script>
        <script src="<sicp3:rewrite forward="maillist"/>"></script>

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
<script language="javascript">
<!--
function aaValue(dispaly_value,code_value){
	alert(code_value);
	if(undefined == code_value)
		return;
	alert(parent.outObj2.value);
	if(undefined != parent.outObj2.value){

		if(-1 != parent.outObj2.value.indexOf(code_value))
			return;
	}
    parent.outObj1.value=parent.outObj1.value+","+dispaly_value;
    parent.outObj2.value=parent.outObj2.value+","+code_value;
    //parent.closeMailList()
}
//-->
</script>

<body style="overflow-y:scroll;overflow-x:hidden;">
<table border="1" cellPadding="1" cellSpacing="1"  bordercolor="#FFFFFF" width="100%">
<tr>
<td>
<table width="100%">
  <tr>
    <td>
        <table class="divTable" border="0" cellPadding="0" cellSpacing="0" width="100%">
          <tr>
              <td width="40%"><font  face=Verdana color="#ffffff"><strong>收件者列表</strong></FONT></td>
              <td width="20%" class="tableHead"><font  face=Verdana color="#ffffff"><span onclick="close_MailList_Frame()" style="cursor: hand">关闭</span></FONT></td>
              <td width="20%" class="tableHead"><font  face=Verdana color="#ffffff"><span onclick="selectAll()" style="cursor: hand" align="right">全选</span></FONT></td>
              <td width="20%" class="tableHead"><font  face=Verdana color="#ffffff"><span onclick="clearMailList()" style="cursor: hand" align="right">清空</span></FONT></td>
          </tr>
        </table>
    </td>
  </tr>
  <tr>
    <td>
        <table class="divClass" bgcolor="#ffffff" width="100%">
            <tr><td valign="top" height="360">
             <select name="mailList" size="23"  onclick="setValue(this)" ondblclick="parent.closeMailList()">
             <option value="">______________________________</option>
              <%
              Map map = (Map)pageContext.findAttribute(GlobalNames.MAIL_LIST_DATA);
              if(null != map){
	              for(Iterator itKey = map.keySet().iterator();itKey.hasNext();){
	              Object key = itKey.next();%>
	                 <option value="<%=key%>"><%=map.get(key)%></option>
	              <%}
              }
              pageContext.removeAttribute(GlobalNames.MAIL_LIST_DATA,PageContext.SESSION_SCOPE);
              %>
              </select>
            </td></tr>
        </table>
    </td>
  </tr>
</table>
</td>
</tr>
</table>
</body>
</html>
