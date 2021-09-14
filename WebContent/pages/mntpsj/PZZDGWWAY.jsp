<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ include file="/comOpenWinInit2.jsp" %>
<div id="addWayContent">
<table width="100%">
		<tr>
			<td height="20"  colspan="4"></td>
		</tr>
		<tr>
	    	<odin:textEdit property="wayname" label="方案名称" required="true" width="300"></odin:textEdit>
		</tr>
</table>	
</div>
<odin:hidden property="wayid"/>
<odin:hidden property="zdgwid"/>
<odin:toolBar property="btnToolBar">
	<odin:fill/>
	<odin:buttonForToolBar id="btnSave" text="保存"  icon="images/save.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="addWayContent" property="addWayPanel" topBarId="btnToolBar"></odin:panel>
<script type="text/javascript">
function saveCallBack(){
	parent.updateWay();
	window.open("","_self").close();
}
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	document.getElementById('wayid').value = parentParam.wayid;
	document.getElementById('zdgwid').value = parentParam.zdgwid;
});

</script>
