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
			<%-- <tags:PublicTextIconEdit3 property="dept" label="选择部门" width="200" codetype="userGroupTreeData" readonly="true"/> --%>
			<odin:select2 property="dept" label="选择部门" width="200" canOutSelectList="false"></odin:select2>
		</tr>
		<tr> <td colspan="2" style="height: 3px;"></td></tr>
		<tr>
			<td colspan="2" align="center"> <odin:button text="&nbsp;设&nbsp;置&nbsp;" property="set"> </odin:button> </td>
		</tr>
		<tr> <td colspan="2" style="height: 5px;"></td></tr>
	</table>
</odin:groupBox>
</div>
<odin:hidden property="userid"/>
<script type="text/javascript">

Ext.onReady(function() {
	document.getElementById('userid').value = parentParam.userid;
	
});
</script>
