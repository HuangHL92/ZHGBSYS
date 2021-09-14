<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ include file="/comOpenWinInit2.jsp" %>
<div id="addWayContent">
<table width="100%">
		<tr>
			<td height="20"  colspan="4"></td>
		</tr>
		<tr>
	    	<odin:textEdit property="zdgwname" label="重点岗位名称" required="true" width="380"></odin:textEdit>
		</tr>
		<tr>
			<odin:select2 property="dwtype" label="岗位类型" required="true"  codeType="ZDGWTYPE" width="380" ></odin:select2>
		</tr>
		<tr>
			<odin:select2 property="zdgwtype" label="成员类别" required="true"  data="['1','正职'],['3','副职']" width="380" ></odin:select2>
		</tr>
		<tr>
			<odin:textarea property="zdgwdesc" label="重点岗位说明" rows="12" colspan="2"></odin:textarea>
		</tr>
</table>	
</div>
<odin:hidden property="zdgwid"/>
<odin:toolBar property="btnToolBar">
	<odin:fill/>
	<odin:buttonForToolBar id="btnSave" text="保存"  icon="images/save.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="addWayContent" property="addWayPanel" topBarId="btnToolBar"></odin:panel>
<script type="text/javascript">
function saveCallBack(){
	parent.updateZdgw();
	window.open("","_self").close();
}
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	document.getElementById('zdgwid').value = parentParam.zdgwid;
});

</script>
