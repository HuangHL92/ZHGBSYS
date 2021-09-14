<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ include file="/comOpenWinInit2.jsp" %>
<div id="addGbkhContent">
<table width="100%">
		<tr>
			<td height="20"  colspan="4"></td>
		</tr>
		<tr>
	    	<odin:textEdit property="year" label="年份" required="true"></odin:textEdit>
		</tr>
		<tr>
	    	<odin:textEdit property="grade" label="测评优秀率" ></odin:textEdit>
		</tr>
</table>	
</div>
<odin:toolBar property="btnToolBar">
	<odin:fill/>
	<odin:buttonForToolBar id="btnSave" text="保存"  icon="images/save.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="addGbkhContent" property="addGbkhPanel" topBarId="btnToolBar"></odin:panel>
<odin:hidden property="gbkhid"/>
<odin:hidden property="a0000"/>
<script type="text/javascript">
function saveCallBack(){
	parent.Ext.getCmp('gbkhGrid').getStore().reload();
	window.close();
}
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	document.getElementById('a0000').value = parent.document.getElementById('a0000').value;
	document.getElementById('gbkhid').value = parentParam.gbkh_id;
/* 	alert(parent.document.getElementById('a0000').value);
	alert(parentParam.gbkh_id); */
	
});
</script>
