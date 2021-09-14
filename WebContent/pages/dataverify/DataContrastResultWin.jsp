<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<script type="text/javascript" src="basejs/helperUtil.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
	<script src="basejs/jquery-ui/jquery-1.10.2.js"></script>
	<script src="basejs/jquery-ui/ui/jquery.ui.core.js"></script>
	<script src="basejs/jquery-ui/ui/jquery.ui.widget.js"></script>
	<script src="basejs/jquery-ui/ui/jquery.ui.progressbar.js"></script>

</head>

<%
String subWinId = request.getParameter("subWinId");
String subWinIdBussessId = request.getParameter("subWinIdBussessId");
if(subWinIdBussessId!=null){
	subWinIdBussessId = new String(request.getParameter("subWinIdBussessId").getBytes("iso8859-1"),"utf8");
}
%>
<script type="text/javascript">
var subWinId = '<%=subWinId%>';
var realParent = parent.Ext.getCmp(subWinId).initialConfig.thisWin;
var params = parent.Ext.getCmp(subWinId).initialConfig.param;
</script>
<odin:hidden property="subWinIdBussessId" value="<%=subWinIdBussessId %>"/>
<odin:hidden property="subWinIdBussessId2" value=""/>

<script type="text/javascript">
	function updatebefore(){
		radow.doEvent("saveupdate");//调用后台事件               
	}
	
	
</script>
		<table id="basicTable">
			<tr>
				<td height="5"></td>
				<odin:hidden property="storeid"/>
			</tr>
			<tr>
				<odin:textEdit property="b0101_n" label="机构名称（内部）" colspan="4" width="427" readonly="true"></odin:textEdit>
				<td></td>
			</tr>
			<tr>
				<odin:textEdit property="b0114_n" label="机构编码（内部）" colspan="4" width="427" readonly="true"></odin:textEdit>
				<td></td>
			</tr>
			<tr>
				<odin:textEdit property="b0101_w" label="机构名称（外部）" colspan="4" width="427" readonly="true"></odin:textEdit>
				<td></td>
			</tr>
			<tr>
				<odin:textEdit property="b0114_w" label="机构编码（外部）" colspan="4" width="427" readonly="true"></odin:textEdit>
				<td></td>
			</tr>
			<tr>
				<odin:textarea property="comments" label="比对结果描述" cols="80" rows="10" colspan="4"></odin:textarea>
			</tr>
			<tr>
				<odin:select property="opptimetype" label="对比结果" data="['0','已删除'],['1','一致'],['2','新增'],['3','已修改']" readonly="true"></odin:select>
				<odin:textEdit property="opptime" label="操作时间" readonly="true"></odin:textEdit>
				<td></td>
			</tr>
		</table>
	
