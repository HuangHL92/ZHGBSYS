<%@page import="com.insigma.siis.local.pagemodel.search.ListOutPutPageModel"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.WarnWindowPageModel"%>

<script type="text/javascript">
 function check(){
    document.getElementById('choose').style.visibility='visible';
 }
 
 function check1(){
    document.getElementById('choose').style.visibility='hidden';
 }
</script>

<table>
<tr >
<td height="20">
</td>
</tr>
<tr>
<td width="10">
</td>
<odin:dateEdit property="jiezsj" label="年龄计算截止"  width="80" format="Ymd"></odin:dateEdit>
</tr>
<tr >
<td height="10">
</td>
</tr>
<tr>
	<td align="center" colspan="3">
		<input type="button" style="cursor:hand;" onclick="saveToDb()" value="&nbsp;确定&nbsp;&nbsp;">
	</td>
</tr>
</table>
<odin:window src="/blank.htm"  id="setWarnWin" width="450" height="350" title="提醒设置" modal="true"/>
<script type="text/javascript">
function saveToDb(){
	radow.doEvent('endtime');
}
</script>