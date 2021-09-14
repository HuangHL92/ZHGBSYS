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
<odin:textEdit property="templateName" label="模板名"></odin:textEdit>
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
<odin:hidden property="opentype"/>
<odin:window src="/blank.htm"  id="setWarnWin" width="450" height="350" title="提醒设置" modal="true"/>
<script type="text/javascript">
function saveToDb(){
	var optype = document.getElementById('opentype').value;
	var value = document.getElementById('templateName').value;
	if(value.length == 0){
		alert('请输入模板名!');
		return;
	} 
	 if(value.length >60){
		alert('输入的模板名过长!');
	} else{
		if(optype == 1){
			radow.doEvent('rename');
		}else{
			parent.Ext.getDom('tname').value=value;
			parent.dosave();
			parent.doHiddenPupWin();
		}
	}
	parent.refues();
	parent.odin.ext.getCmp('templateInfoGrid').store.reload();
	parent.odin.ext.getCmp('templateInfoGrid1').store.reload();
	parent.odin.ext.getCmp('templateInfoGrid2').store.reload();
}
</script>