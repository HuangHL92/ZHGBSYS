<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>

<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script  type="text/javascript">

</script>
<odin:hidden property="checkregid"/>
<odin:hidden property="userid"/>
<odin:hidden property="groupid"/>
<odin:hidden property="regstatus"/>
<odin:hidden property="regtype"/>
<odin:hidden property="regotherid"/>
<odin:hidden property="checkdate"/>

<odin:hidden property="fcxx"/>
<odin:hidden property="crjxx"/>
<odin:hidden property="gpjjxx"/>
<odin:hidden property="sybxxx"/>
<odin:hidden property="gsxx1"/>
<odin:hidden property="gsxx2"/>

			<!-- record_batch -->
<div id="tbar_div"></div>
<odin:groupBox title="������Ϣ" >
<table style="width: 100%;">
	<tr>
		<td colspan="4" height="10px"></td>
	</tr>
	<tr>
		<odin:textEdit property="regname" label="��������" required="true"></odin:textEdit>
		<odin:textEdit property="reguser" label="������" required="true"></odin:textEdit>
	</tr>
	<tr>
		<td colspan="4" height="5px"></td>
	</tr>
	<tr>
		<odin:textEdit property="regno" label="���α��" required="true"></odin:textEdit>
		<odin:textEdit property="groupname" label="�������" required="true"></odin:textEdit>
	</tr>
	<tr>
		<td colspan="4" height="20px"></td>
	</tr>
	<%-- <tr>
		<td align="right" colspan="4" style="padding-right: 30px;">
			<odin:button text="����"  property="save"></odin:button>
		</td>
	</tr> --%>
</table>
</odin:groupBox>
<odin:toolBar property="topbar" applyTo="tbar_div">
<odin:fill/>
<odin:separator/>
<odin:buttonForToolBar text="����" id="save"></odin:buttonForToolBar>
<odin:separator isLast="true"/>
</odin:toolBar>
<script type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	
	document.getElementById('checkregid').value = parentParam.checkregid;
	
	
});
function saveCallBack(t){
	//$h.alert('ϵͳ��ʾ', t, function(){
		parent.$('#checkregid').val($('#checkregid').val());
		parent.$('#regname').val($('#regname').val());
		parent.Ext.getCmp('memberGrid').getStore().reload();
		window.close();
		//var rbId = document.getElementById('checkregid').value;
		//parent.$h.openPageModeWin('qcjs','pages.xbrm.QCJS','ȫ�̼�ʵ',1150,800,{rb_id:rbId},g_contextpath);
		
	//});
}
</script>


