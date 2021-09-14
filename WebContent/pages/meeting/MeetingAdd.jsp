<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ include file="/comOpenWinInit2.jsp" %>
<div id="addMeetingContent">
<table width="100%">
		<tr>
			<td height="20"  colspan="4"></td>
		</tr>
		<tr>
	    	<odin:textEdit property="meetingname" label="��������" required="true"></odin:textEdit>
		</tr>
		<tr>
			<odin:select2 property="meetingtype" label="��������" codeType="meettype" required="true" onchange="changetype"></odin:select2>
		</tr>
		<tr>
			<odin:dateEdit property="time" label="��������" format="Ymd" required="true"/>
		</tr>
		<tr id="tr1">
			<odin:textEdit property="meetingjc" label="���" ></odin:textEdit>
		</tr>
		<tr id="tr2" >
			<odin:textEdit property="meetingpc" label="����" ></odin:textEdit>
		</tr>
</table>	
</div>
<odin:hidden property="userid"/>
<odin:toolBar property="btnToolBar">
	<odin:fill/>
	<odin:buttonForToolBar id="btnSave" text="����"  icon="images/save.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="addMeetingContent" property="addMeetingPanel" topBarId="btnToolBar"></odin:panel>
<odin:hidden property="meetingid"/>
<script type="text/javascript">
function saveCallBack(){
	parent.Ext.getCmp('meetingGrid').getStore().reload();
	window.close();
}
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	document.getElementById("tr1").style.display="none";
	document.getElementById("tr2").style.display="none";
	document.getElementById('meetingid').value = parentParam.meeting_id;

});

function changetype(){
	var meetingtype=document.getElementById('meetingtype').value;
	if(meetingtype=='3'){
		document.getElementById("tr1").style.display="";
		document.getElementById("tr2").style.display="";
	}else{
		document.getElementById("meetingjc").value="";
		document.getElementById("meetingpc").value="";
		document.getElementById("tr1").style.display="none";
		document.getElementById("tr2").style.display="none";
	}
}
</script>
