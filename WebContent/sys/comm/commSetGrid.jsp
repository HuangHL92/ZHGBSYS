<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin-commform.tld" prefix="odin"%>
<%@page import="com.insigma.odin.framework.util.commform.BuildUtil.ItemValue"%>
<html>
<%
String pageSize = "";
if(session.getAttribute("pageSize") != null && !session.getAttribute("pageSize").equals("")){
	 pageSize = session.getAttribute("pageSize").toString(); 				//判断是否设置了自定义每页数量，如果设置了使用自定义
}

%>
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
<odin:textEdit property="pagesize" label="每页显示条数" value="<%=pageSize %>" ></odin:textEdit>
</tr>
<tr >
<td height="10">
</td>
</tr>
<tr>
	<td align="center" colspan="3">
		<input type="button" style="cursor:hand;" onclick="doSetPageSize()" value="&nbsp;确定&nbsp;&nbsp;">
	</td>
</tr>
</table>


</body>

<script type="text/javascript">
	var maxPageSize = 2000;//所设页数大于最大允许页数200条
	function doSetPageSize(){
		var re = /^[1-9]+[0-9]*]*$/;	
		var pagesize = document.getElementById("pagesize").value;
		if(pagesize < 1 || !re.test(pagesize)){
			
			Ext.Msg.alert("系统提示","页数必须是大于1的整数！");
			return;
		}
		if(pagesize==null||pagesize==""){
			//alert("请选择每页显示条数!");
			Ext.Msg.alert("系统提示","请选择每页显示条数!");
			return;
		}else if(pagesize>maxPageSize){
			//alert("所设每页条数大于最大允许条数("+maxPageSize+"条)！\n请重新输入！");
			Ext.Msg.alert("系统提示","每页条数大于最大允许条数("+maxPageSize+"条)！");
			return;
		}else{
			var gridId = parent.gridIdForSeting;
			
			
			pageingToolbar = (parent.Ext.getCmp(gridId).getBottomToolbar() || parent.Ext.getCmp(gridId).getTopToolbar());
			pageingToolbar.pageSize = Number(pagesize);
			var s = parent.Ext.getCmp(gridId).store;
			s.baseParams.limit = pagesize;
			if(s.lastOptions && s.lastOptions.params){
				
				s.lastOptions.params.limit = pagesize;
				s.lastOptions.params.start = 0;
				
			}
			//将设置的页数传递到后台，存入session 
			window.location="<%=request.getContextPath()%>/PublishFileServlet?method=customPageSize&pageSize="+pagesize;
			parent.Ext.getCmp(gridId).store.reload();
	        parent.doHiddenPupWin();
		}
	}
	Ext.onReady(
		function(){
			var gridId = parent.gridIdForSeting;
			pageingToolbar = (parent.Ext.getCmp(gridId).getBottomToolbar() || parent.Ext.getCmp(gridId).getTopToolbar());
			
			var pagesize = document.getElementById("pagesize").value;
			if(pagesize == null || pagesize == ""){
				document.getElementById("pagesize").value = String(pageingToolbar.pageSize);
			}
			//odin.setSelectValue("pagesize", String(pageingToolbar.pageSize));
			
	    }
	);
	

</script>

</html>