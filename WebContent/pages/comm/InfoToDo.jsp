<%@page import="com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page isELIgnored="false" %>
<%@include file="/comOpenWinInit2.jsp" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/xbrm/jquery-ui-12.1.css">
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery-ui1.10.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>

<div>
<odin:groupBox title="选择部门">
	<table style="left: 20px;">
		<tr>
			<tags:PublicTextIconEdit3 property="itdr007" label="办理部门" width="200" codetype="userGroupTreeData" readonly="true"/>
		</tr>
		<tr> <td colspan="2" style="height: 3px;"></td></tr>
		<tr>
			<td colspan="2" align="center"> <odin:button text="&nbsp;发&nbsp;送&nbsp;" property="send"> </odin:button> </td>
		</tr>
		<tr> <td colspan="2" style="height: 5px;"></td></tr>
	</table>
</odin:groupBox>
</div>
<odin:hidden property="itdr004"/>
<odin:hidden property="itdr005"/>
<odin:hidden property="itdr006"/>
<odin:hidden property="itdr013"/>
<odin:hidden property="itdr014"/>

<odin:hidden property="ckfileid"/>
<script type="text/javascript">

Ext.onReady(function() {
	document.getElementById('itdr004').value = parentParam.type;
	document.getElementById('itdr005').value = parentParam.rid;
	document.getElementById('itdr006').value = parentParam.rname;
	document.getElementById('itdr013').value = parentParam.tid;
	document.getElementById('itdr014').value = parentParam.tname;
	
});
</script>
