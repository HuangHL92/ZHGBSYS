<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>

<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script  type="text/javascript">

</script>
<odin:hidden property="cvo000"/>
<odin:hidden property="cvo011"/>
<div id="tbar_div"></div>
<odin:groupBox title="������Ϣ" >
<table style="width: 100%; right: 40px;">
	
	<tr>
		<odin:textEdit property="cvo001" label="����" required="true"/>
		<odin:select2 property="cvo002c" label="�Ա�" canOutSelectList="false" codeType="GB2261" required="true"/>
	</tr>
	<tr>
		<odin:NewDateEditTag property="cvo003" label="��������" maxlength="8" />
		<odin:NewDateEditTag property="cvo004" label="�뵳ʱ��" maxlength="8" />
	</tr>
	<tr>
		<odin:textEdit property="cvo005" label="����ְ��" width="422" colspan="4" required="true"/>
	</tr>
	<tr>
		<odin:textEdit property="cvo006" colspan="4" label="ѧ��" width="422" />
	</tr>
	<tr>
		<odin:textEdit property="cvo007" colspan="4"  width="422" label="��ҵԺУ��רҵ" />
	</tr>
	<tr>
		<odin:textEdit property="cvo008" label="ѡƸ���" />
		<odin:textEdit property="cvo009" label="�������" />
	</tr>
	<tr>
		<odin:textarea property="cvo010" label="��ע" cols="75" colspan="4" rows="3"/>
	</tr>
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
	document.getElementById('cvo000').value = parentParam.cvo000;
});
function saveCallBack(t){
		parent.Ext.getCmp('memberGrid').getStore().reload();
		window.close();
}
</script>


