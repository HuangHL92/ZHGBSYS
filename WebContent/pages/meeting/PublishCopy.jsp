<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ include file="/comOpenWinInit2.jsp" %>
<div id="addPublishContent">
<table width="100%">
		<tr>
			<td height="20"  colspan="2"></td>
		</tr>
		<tr>
			<odin:textEdit property="meetingname" label="����ǰ�Ļ�������"  disabled="true"></odin:textEdit>
	    </tr>
	    <tr>
	    	<odin:textEdit property="agendaname" label="����ǰ����������" disabled="true"></odin:textEdit>
		</tr>
		<tr>
			<td height="20"  colspan="2"></td>
		</tr>
		<tr>
			<odin:select2 property="xmeetingname"  label="���ƺ�������������" required="true"></odin:select2>
	    </tr>
	    <tr>
	    	<odin:textEdit property="xagendaname" label="���ƺ����������" required="true"></odin:textEdit>
		</tr>
</table>	
</div>
<odin:toolBar property="btnToolBar">
	<odin:fill/>
	<odin:buttonForToolBar id="btnSave" text="����"  icon="images/save.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="addPublishContent" property="addPublishPanel" topBarId="btnToolBar"></odin:panel>
<odin:hidden property="publishid"/>
<odin:hidden property="meetingid"/>
<script type="text/javascript">
function saveCallBack(){
	//parent.reloadSelData();
	window.close();
}
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	document.getElementById('meetingid').value = parent.document.getElementById('meetingid').value;
	document.getElementById('meetingname').value = parent.document.getElementById('meetingname_combo').value;
	document.getElementById('publishid').value = parentParam.publish_id;
});
</script>
