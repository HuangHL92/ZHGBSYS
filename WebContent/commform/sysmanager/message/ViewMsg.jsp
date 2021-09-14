<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<html>
<head>
<title>系统消息</title>
</head>
<script language="javascript">
function k(){
   document.forms["0"].action ="messageAction.do?method=hasKown";
   document.forms["0"].submit();
}
function d(){
   document.forms["0"].action="messageAction.do?method=delete";
   document.forms["0"].submit();
   self.close();
}
</script>
<sicp3:base />
<sicp3:body>
	<table width="95%" border="0" align="center" cellpadding="0"
		cellspacing="0">
		<tr>
			<td height="10"></td>
		</tr>
	</table>
	<!--录入部分3-->

	<table width="95%" border="0" align="center" cellpadding="0"
		cellspacing="0">
		<tr>
			<td height="10">
			<button name="k" onclick="k()" class="buttonGray">我知道了</button>
			</td>
			<td height="10">
			<button name="d" onclick="d()" class="buttonGray">删除信息</button>
			</td>
			<td height="10">&nbsp;</td>
			<td height="10">&nbsp;</td>
		</tr>
	</table>
	<sicp3:title title="添加用户" />
	<sicp3:tabletitle title="用户信息" />
	<sicp3:form action="/messageAction.do?method=view">
		<table width="95%" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tableInput">
			<tr>
				<td width="9%" height="0" align="right"><font color="#FF0000">*</font>发送人<br>
				</td>
				<td width="86%" height="0"><sicp3:text property="msgSender"
					required="true" maxlength="16" disable="false" /></td>
				<sicp3:hidden property="msgrecvrId" />
			</tr>
			<tr>
				<td height="0" align="right" nowrap>发送时间</td>
				<td width="86%" height="0"><sicp3:text property="msgSendtime"
					required="true" maxlength="16" disable="false" /></td>
			</tr>
			<tr>
				<td height="0" align="right" nowrap>主题</td>
				<td width="86%" height="0"><sicp3:text property="msgSubject"
					required="true" maxlength="16" disable="false" /></td>
			</tr>
			<tr>
				<td height="0" align="right" nowrap>内容</td>
				<td width="86%" height="0">&nbsp;</td>
			</tr>
			<tr>
				<td height="10">&nbsp;</td>
				<td><sicp3:textarea property="msgMessage"
					style="word-break:break-all;overflow-y:scroll" rows="5" cols="12" /></td>
			</tr>
		</table>
	</sicp3:form>
	<sicp3:bottom />
	<sicp3:errors />
</sicp3:body>
</html>
<script language="javascript">
TimeStart=5;
function closeit(){
  if(TimeStart==0){
    window.close();
  }
  TimeStart=TimeStart-1;
var timer=window.setTimeout("closeit()",1000);
}
closeit();
</script>
