<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin-commform.tld" prefix="odin"%>
<%@page import="com.insigma.odin.framework.util.commform.BuildUtil.ItemValue"%>
<html>
<%
String pageSize = "";
if(session.getAttribute("pageSize") != null && !session.getAttribute("pageSize").equals("")){
	 pageSize = session.getAttribute("pageSize").toString(); 				//�ж��Ƿ��������Զ���ÿҳ���������������ʹ���Զ���
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
<odin:textEdit property="pagesize" label="ÿҳ��ʾ����" value="<%=pageSize %>" ></odin:textEdit>
</tr>
<tr >
<td height="10">
</td>
</tr>
<tr>
	<td align="center" colspan="3">
		<input type="button" style="cursor:hand;" onclick="doSetPageSize()" value="&nbsp;ȷ��&nbsp;&nbsp;">
	</td>
</tr>
</table>


</body>

<script type="text/javascript">
	var maxPageSize = 2000;//����ҳ�������������ҳ��200��
	function doSetPageSize(){
		var re = /^[1-9]+[0-9]*]*$/;	
		var pagesize = document.getElementById("pagesize").value;
		if(pagesize < 1 || !re.test(pagesize)){
			
			Ext.Msg.alert("ϵͳ��ʾ","ҳ�������Ǵ���1��������");
			return;
		}
		if(pagesize==null||pagesize==""){
			//alert("��ѡ��ÿҳ��ʾ����!");
			Ext.Msg.alert("ϵͳ��ʾ","��ѡ��ÿҳ��ʾ����!");
			return;
		}else if(pagesize>maxPageSize){
			//alert("����ÿҳ�������������������("+maxPageSize+"��)��\n���������룡");
			Ext.Msg.alert("ϵͳ��ʾ","ÿҳ�������������������("+maxPageSize+"��)��");
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
			//�����õ�ҳ�����ݵ���̨������session 
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