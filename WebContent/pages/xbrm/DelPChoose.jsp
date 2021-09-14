<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@include file="/comOpenWinInit2.jsp" %>

<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script  type="text/javascript">

</script>
<odin:hidden property="rbId"/>
<odin:hidden property="js0100s"/>

<odin:groupBox title="移除信息" >
<table style="width: 100%;">
	<tr>
		<td colspan="2" align="left">
			<span style="font-size: 12px;">
			点击【暂缓】、【超额】，将从该批次移除人员，但是保留人员操作记录；
			点击【终止】，将删除该人员的所有纪实记录以及附件，保留终止记录；
			点击【彻底删除】，将删除该人员的所有纪实记录以及附件，不保留任何信息。
			</span>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<table><tr>
			<td style="display: none;"><odin:radio property="dellx" value="0" label=""/></td>
			<td><odin:radio property="dellx" value="1" label="暂缓"/></td>
			<td><odin:radio property="dellx" value="2" label="终止" /></td>
			<td><odin:radio property="dellx" value="3" label="超额" /></td>
			<td><odin:radio property="dellx" value="4" label="彻底删除" /></td>
			</tr></table>
		</td>
	</tr>
	<tr>
		<odin:textarea colspan="2" rows="4" cols="60" label="说明" property="detxx"></odin:textarea>
	</tr>
	<tr>
		<td align="right" colspan="2" style="padding-right: 30px;">
			<odin:button text="确定"  property="set"></odin:button>
		</td>
	</tr>
</table>
</odin:groupBox>

<script type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';

Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	document.getElementById('rbId').value = parentParam.rb_id;
	document.getElementById('js0100s').value = parentParam.js0100s;
});

</script>


