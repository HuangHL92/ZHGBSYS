<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3" %>
<html>
<head>
<title>上传权限列表文件</title>
<script language="javascript">
<!--
//设置页面元素的CSS
//eleName 页面元素名称
//className 要切换的CSS名称
function goBack() {
 window.location.href="<sicp3:rewrite page='/functionAction.do?method=goBack'/>&stringData=" +
      	document.all("stringData").value ;
}
function check(){
   var uploadfile = document.functionForm.uploadfile.value;
  if((null == uploadfile) ||( "" == uploadfile)){
  		alert("上传文件没有指定！");
      document.functionForm.uploadfile.focus();
    return false;
  }
	return true;
}
//-->
</script>
<sicp3:base/>
</head>

<sicp3:body>
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="10"></td>
  </tr>
</table>
<!--标题部分-->
<%
String stringData = request.getParameter("stringData");
%>
<sicp3:title title="上传权限列表文件"/>
<sicp3:tabletitle title="权限列表信息"/>
<table width="95%" border="0" align="center" cellpadding="3" cellspacing="0" class="tableInput">
  <sicp3:form action="/functionAction.do?method=upload" method="POST" enctype="multipart/form-data">
  <tr >
    <td  width="20%"><sicp3:colortxt value="*" />上传文件<sicp3:hidden property="stringData" value="<%=stringData%>"/><br></td>
    <td align="left" width="80%">
      <sicp3:file property="uploadfile" size="80%">
      </sicp3:file></td>
  </tr>
</table>
<table width="95%" height="35" border="0" align="center" cellspacing="0" class="tableInput">
  <tr>
    <td class="action">
      <sicp3:submit styleClass="buttonGray"  onclick="return check()">导 入</sicp3:submit>
      <input type="button" name="close" value="返 回" onclick="goBack()" class="buttonGray">
   </td>
  </tr>
</table>
</sicp3:form>
<sicp3:bottom/>
<sicp3:errors />
</sicp3:body>
</html>
