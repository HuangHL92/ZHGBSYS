<%@include file="/commform/basejs/loading/loading.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin-commform.tld" prefix="odin"%>

<%@page import="com.insigma.odin.framework.util.commform.BuildUtil.ItemValue"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title></title>
<odin:commformhead/>
<odin:commformMDParam></odin:commformMDParam>
</head>
<body>

<table>
<tr >
<td height="20">
</td>
</tr>
<tr>
<td width="10">
</td>
<odin:select property="pagesize" label="ÿҳ��ʾ����" canOutSelectList="true" tpl="<%=ItemValue.tpl_Value%>" data="['10','10'],['20','20'],['50','50'],['100','100'],['200','200']"></odin:select>
</tr>
<tr >
<td height="10">
</td>
</tr>
<tr>
	<td align="center" colspan="3">
		<input type="button" style="cursor:hand;" onclick="doSetPageSize()" value="&nbsp;&nbsp;ȷ��&nbsp;&nbsp;">
	</td>
</tr>
</table>


</body>

<script type="text/javascript">
	var maxPageSize = 200;
	function doSetPageSize(){
		var pagesize = document.getElementById("pagesize").value;
		if(pagesize==null||pagesize==""){
			odin.error("��ѡ��ÿҳ��ʾ����!");
			return;
		}else if(pagesize>maxPageSize){
			odin.error("����ҳ�������������ҳ��("+maxPageSize+")��\n���������룡");
			return;
		}else{
			var gridId = parent.gridIdForSeting;
			pageingToolbar = (parent.Ext.getCmp(gridId).getBottomToolbar() || parent.Ext.getCmp(gridId).getTopToolbar());
			pageingToolbar.pageSize = Number(pagesize);
			parent.Ext.getCmp(gridId).store.baseParams.limit = pagesize;
			parent.Ext.getCmp(gridId).store.load();
	        parent.doHiddenPupWin();
		}
	}
	Ext.onReady(
		function(){
			var gridId = parent.gridIdForSeting;
			pageingToolbar = (parent.Ext.getCmp(gridId).getBottomToolbar() || parent.Ext.getCmp(gridId).getTopToolbar());
			odin.setSelectValue("pagesize", String(pageingToolbar.pageSize));
	    }
	);
</script>

</html>