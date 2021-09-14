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
<odin:groupBox title="批次信息" >
<table style="width: 100%; right: 40px;">
	
	<tr>
		<odin:textEdit property="cvo001" label="姓名" required="true"/>
		<odin:select2 property="cvo002c" label="性别" canOutSelectList="false" codeType="GB2261" required="true"/>
	</tr>
	<tr>
		<odin:NewDateEditTag property="cvo003" label="出生年月" maxlength="8" />
		<odin:NewDateEditTag property="cvo004" label="入党时间" maxlength="8" />
	</tr>
	<tr>
		<odin:textEdit property="cvo005" label="现任职务" width="422" colspan="4" required="true"/>
	</tr>
	<tr>
		<odin:textEdit property="cvo006" colspan="4" label="学历" width="422" />
	</tr>
	<tr>
		<odin:textEdit property="cvo007" colspan="4"  width="422" label="毕业院校及专业" />
	</tr>
	<tr>
		<odin:textEdit property="cvo008" label="选聘年度" />
		<odin:textEdit property="cvo009" label="所属板块" />
	</tr>
	<tr>
		<odin:textarea property="cvo010" label="备注" cols="75" colspan="4" rows="3"/>
	</tr>
</table>
</odin:groupBox>
<odin:toolBar property="topbar" applyTo="tbar_div">
<odin:fill/>
<odin:separator/>
<odin:buttonForToolBar text="保存" id="save"></odin:buttonForToolBar>
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


