<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@include file="/comOpenWinInit2.jsp" %>

<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script  type="text/javascript">

</script>
<odin:hidden property="ynId"/>
<odin:hidden property="ynUserid"/>
			<!-- record_batch -->
<div style="margin-top: 20px;"></div>
<odin:groupBox title="批次信息" >
<table style="width: 100%;">
	<tr>
		<odin:textEdit property="ynName" label="表格名称" width="300" required="true"></odin:textEdit>
	</tr>
	<tr>	
		<odin:select2 property="ynType" label="表格类别" value="TPHJ1" data="['TPHJ1','动议干部情况一览表'],['TPHJ3','省委书记专题会议干部情况一览表'],['TPHJ2','省委常委会干部情况一览表']" required="true"></odin:select2>
		  <!-- 
		 <td><odin:hidden property="ynType" value="TPHJ1" /></td> -->
		 
	</tr>
	<tr>
		<td align="right" colspan="4" style="padding-right: 30px;">
			<odin:button text="保存"  property="save"></odin:button>
		</td>
	</tr>
</table>
</odin:groupBox>

<script type="text/javascript">
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	
	document.getElementById('ynId').value = parentParam.yn_id;
	
	
});
function saveCallBack(t){
	//$h.alert('系统提示', t, function(){
		parent.$('#yn_id').val($('#ynId').val());
		parent.$('#yn_name').val($('#ynName').val());
		parent.Ext.getCmp('memberGrid').getStore().reload();
		window.close();
	//});
}
</script>


