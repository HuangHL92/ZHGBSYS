<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript">
function sure(){
	var a0000 = parent.document.getElementById("a0000").value;
	var a0255 = $("input[name='a0255']:checked").val();

	if("2"==a0255){
		parent.parent.document.getElementById("printPdf").value = "pdf1.1";
	}
	parent.parent.radow.doEvent('printViewNew',a0000+',true');
	$h.openPageModeWin('pdfViewWinNew','pages.publicServantManage.PdfView','打印任免表',700,500,1,'<%=request.getContextPath()%>');
	window.close();
}
</script>	
<odin:hidden property="a0163Odl"/>
<odin:hidden property="msg"/>
<odin:hidden property="type"/>
<div style="display:block;margin-left: 40" id="group">
<table>	
<tr>
	<td>&nbsp;</td>
</tr>
<tr>
	<td>&nbsp;</td>
</tr>	
<tr>
<td>
	<label style="font-size: 12px;"><input align="middle" type="radio" name="a0255" id="a02551" checked="checked" value="1" class="radioItem" />
	干部任免审批表</label>					
</td>
</tr>
<tr>
<td>
	<label style="font-size: 12px;"><input align="middle" type="radio" name="a0255" id="a02550" value="2" class="radioItem"/>
	干部任免审批表(审核用)</label>
</td>
</tr>
<tr>
</tr>
<tr>
	<td>&nbsp;</td>
</tr>
</table>
</div>


<div>
<table align="center">
<tr>
<td><odin:button text="确定(Y)" property="yesBtn" handler="sure"></odin:button></td>
<td width="20"></td>
<%-- <td><odin:button text="取消(C)" property="cancelBtn"></odin:button></td> --%>
</tr>
</table>
</div>