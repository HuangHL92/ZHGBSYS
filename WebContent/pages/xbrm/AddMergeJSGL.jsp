<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@include file="/comOpenWinInit2.jsp" %>

<style>
	.trh{
		display:none;
	}
</style>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script  type="text/javascript">

</script>
<odin:hidden property="rbId"/>
<odin:hidden property="rbUserid"/>
<odin:hidden property="rbmDept" />
			<!-- record_batch -->
<div style="margin-top: 20px;"></div>
<odin:groupBox title="������Ϣ" >
<table style="width: 100%;">
	<tr>
		<odin:textEdit property="rbmName" label="��������" required="true"></odin:textEdit>
		<odin:textEdit property="rbmApplicant" label="������" required="true"></odin:textEdit>
	</tr>
	<tr>
		<odin:dateEdit property="rbmDate" format="Ymd" label="��������" required="true"/>
		<odin:textEdit property="rbmOrg" label="�������" required="true"></odin:textEdit>
	</tr>
	<tr style="display: none">
		<!-- �������� -->
		<odin:select2 property="rbmDeplytype" codeType="DPLX" label="��������" title="��������" queryDelay="false" required="false"></odin:select2>
		<!-- �������� -->
		<odin:select2 property="rbmMeettype" codeType="HYLX" label="��������" title="��������" queryDelay="false"></odin:select2>
	</tr>
	<tr style="display: none">
		<odin:textEdit property="rbmNo" label="���α��" required="false"></odin:textEdit>
	</tr>
	<tr>
		<td align="center" colspan="4" style="padding-right: 30px;">
			<odin:button text="����"  property="save"></odin:button>
		</td>
	</tr>
</table>
</odin:groupBox>

<script type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';

function approvechange(){
	var val=document.getElementById('rbapprove').value;
	if(val=='1'){
		$('#reportTr').css('display','block');
		document.getElementById('rbreportreson').value='';
	}else if(val=='2'){
		$('#reportTr').css('display','none');
		document.getElementById('rbreportreson_combo').value='';
		$('#reportvalueTr').css('display','none');
		document.getElementById('rbreportvalue').value='';
	}
}

function resonchange(){
	var val=document.getElementById('rbreportreson').value;
	if(val=='503'){
		$('#reportvalueTr').css('display','block');
	}else{
		$('#reportvalueTr').css('display','none');
		document.getElementById('rbreportvalue').value='';
	}
}

Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	
	document.getElementById('rbId').value = parentParam.rb_id;
	document.getElementById('rbmDept').value = parent.document.getElementById('rbmDept').value;
});

function initselect(){
	var val=document.getElementById('rbapprove').value;
	if(val=='1'){
		$('#reportTr').css('display','block');
		var val1=document.getElementById('rbreportreson').value;
		if(val1=='503'){
			$('#reportvalueTr').css('display','block');
		}
	}
}

function saveCallBack(t){
	//$h.alert('ϵͳ��ʾ', t, function(){
		parent.$('#rb_id').val($('#rbId').val());
		parent.$('#rb_name').val($('#rbName').val());
		parent.Ext.getCmp('memberGrid').getStore().reload();
		window.close();
		var rbId = document.getElementById('rbId').value;
		parent.$h.openPageModeWin('qcjs','pages.xbrm.QCJS','ȫ�̼�ʵ',1150,800,{rb_id:rbId},g_contextpath);
		
	//});
}
</script>


