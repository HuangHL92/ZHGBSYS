<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit.jsp"%>
<%String ctxPath = request.getContextPath(); 
%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<body>
<table cellspacing="2" width="400" align="left">
	<tr>
		<td>&nbsp;</td><odin:select2 property="Printer" label="ѡ���ӡ����" required="true" width="380" editor="false"></odin:select2>
	</tr>
	<tr>
		<td>&nbsp;</td><odin:textEdit property="Number"  label="��ӡ������" required="true" value="1"></odin:textEdit>
	</tr>
	<tr>
	<td>&nbsp;</td>
	</tr>
	<tr>
	<td align="center" colspan="3">
		<input type="button" style="cursor:hand;" onclick="doSet()" value="&nbsp;&nbsp;ȷ��&nbsp;&nbsp;">
	</td>
	</tr>
</table>
</body>
<script type="text/javascript">
function setPrinter(printer){
	var nt = new ActiveXObject("WScript.Network"); //��ȡ��� 
	nt.SetDefaultPrinter(printer);//�޸�Ĭ�ϴ�ӡ��
}
function doSet(){
	var printer = document.getElementById("Printer_combo").value;
	var printNum = document.getElementById("Number").value;
	if(printer==undefined||printer==''){
		$h.alert('ϵͳ��ʾ��','��ѡ���ӡ����',null,180);
	}
	if(printNum==undefined||printNum==''){
		$h.alert('ϵͳ��ʾ��','��ѡ���ӡ������',null,180);
	}
	var param = printer+"|@|"+printNum;
	Ext.Msg.wait('�ı��ӡ����...','ϵͳ��ʾ');
	radow.doEvent("doSet",param);
}
</script>
<style>
<%=FontConfigPageModel.getFontConfig()%>
.vfontConfig{
color: red;
}

#border {
	position: relative;
	left: 0px;
	top: 0px;
	width: 0px;
	border: 1px solid #99bbe8;
}

#toolBar8 {
	width: 690px !important;
}
</style>
