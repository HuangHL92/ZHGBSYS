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
<odin:groupBox title="������Ϣ" >
<table style="width: 100%;">
	<tr>
		<odin:textEdit property="ynName" label="��������" width="300" required="true"></odin:textEdit>
		<%-- <odin:select2 property="ynType" label="�������" value="TPHJ1" data="['TPHJ1','����'],['TPHJ2','��ί���ר������Ա����'],['TPHJ3','�������'],['TPHJ4','��ί���ר�����'],['TPHJ5','��ί��ί��']" required="true"></odin:select2>
		 --%>
		 <td><odin:hidden property="ynType" value="TPHJ1" /></td>
		 
	</tr>
	<tr>
		<td align="right" colspan="4" style="padding-right: 30px;">
			<odin:button text="����"  property="save"></odin:button>
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
	//$h.alert('ϵͳ��ʾ', t, function(){
		parent.$('#yn_id').val($('#ynId').val());
		parent.$('#yn_name').val($('#ynName').val());
		parent.Ext.getCmp('memberGrid').getStore().reload();
		window.close();
	//});
}
</script>


