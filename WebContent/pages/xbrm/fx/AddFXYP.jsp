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
<odin:hidden property="fyId"/>
<odin:hidden property="fyUserid"/>
			<!-- record_batch -->
<div style="margin-top: 20px;"></div>
<odin:groupBox title="批次信息" >
<table style="width: 100%;">
	<tr>
		<odin:textEdit property="fyName" label="批次名称" required="true"></odin:textEdit>
		<odin:textEdit property="fyApplicant" label="申请人" required="true"></odin:textEdit>
	</tr>
	<tr>
		<odin:dateEdit property="fyDate" format="Ymd" label="申请日期" required="true"/>
		<odin:textEdit property="fyOrg" label="申请机构" required="true"></odin:textEdit>
	</tr>
	<tr>
		<!-- 调配类型 -->
		<odin:select2 property="fydeplytype" codeType="DPLX" label="调配类型" title="调配类型" queryDelay="false" required="true"></odin:select2>
		<!-- 会议类型 -->
		<odin:select2 property="fymeettype" codeType="HYLX" label="会议类型" title="会议类型" queryDelay="false" required="true"></odin:select2>
	</tr>
	<tr>
		<odin:textEdit property="fyNo" label="批次编号" required="true"></odin:textEdit>
		<odin:select2 onchange="approvechange()" property="fyapprove" label="是否需要事前报告" value='2' data="['1','是'],['2','否']" required="true"></odin:select2>
	</tr>
	<tr  style="display:none;" id="reportTr" colspan="2">
		<odin:select2 colspan="4" size="79" onchange="resonchange()" property="fyreportreson" codeType="BRYESREASON" label="报告事项" title="报告事项" queryDelay="false"></odin:select2>
	</tr>
	<tr style="display:none;width:100px;" id="reportvalueTr">
		<odin:textarea colspan="4" cols="83" rows="3" label="其他事项" property="fyreportvalue"></odin:textarea>
	</tr>
	<tr>
		<td align="right" colspan="4" style="padding-right: 30px;">
			<odin:button text="保存"  property="save"></odin:button>
		</td>
	</tr>
</table>
</odin:groupBox>

<script type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';

function approvechange(){
	var val=document.getElementById('fyapprove').value;
	if(val=='1'){
		$('#reportTr').css('display','block');
		document.getElementById('fyreportreson').value='';
	}else if(val=='2'){
		$('#reportTr').css('display','none');
		document.getElementById('fyreportreson_combo').value='';
		$('#reportvalueTr').css('display','none');
		document.getElementById('fyreportvalue').value='';
	}
}

function resonchange(){
	var val=document.getElementById('fyreportreson').value;
	if(val=='503'){
		$('#reportvalueTr').css('display','block');
	}else{
		$('#reportvalueTr').css('display','none');
		document.getElementById('fyreportvalue').value='';
	}
}

Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	
	document.getElementById('fyId').value = parentParam.fy_id;
	
	
});

function initselect(){
	var val=document.getElementById('fyapprove').value;
	if(val=='1'){
		$('#reportTr').css('display','block');
		var val1=document.getElementById('fyreportreson').value;
		if(val1=='503'){
			$('#reportvalueTr').css('display','block');
		}
	}
}

function saveCallBack(t){
	//$h.alert('系统提示', t, function(){
		parent.$('#fy_id').val($('#rbId').val());
		parent.$('#fy_name').val($('#rbName').val());
		parent.Ext.getCmp('memberGrid').getStore().reload();
		window.close();
		var fyId = document.getElementById('fyId').value;
		parent.$h.openPageModeWin('qcjs','pages.xbrm.fx.DealFXYP','分析研判',1150,800,{fy_id: fyId},g_contextpath);
		
	//});
}
</script>


