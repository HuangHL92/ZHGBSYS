<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath(); 
String sign = request.getParameter("sign");
%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.TableColInterface"%>
<odin:hidden property="a0000" title="��Ա����"/>
<style>
body {
	background-color: rgb(214,227,243);
}
/* #one {
	position: absolute;
	left: 200px;
	top: 100px;
	width: 20px;
} */
</style>
<script  type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';
</script>
<table style="width:100%;margin-top: 50px;" >
	<tr>
		<tags:PublicTextIconEdit property="a6506" label="�׸ĺ�ְ��" codetype="ZB148"  readonly="true" />
		<tags:PublicTextIconEdit property="a0120" codetype="ZB134" label="����"  readonly="true"/>
	</tr>
</table>
<div id="bottom_div01">
	<div align="center">
		<odin:button text="��&nbsp;&nbsp;��" property="save" />
	</div>		
</div> 
<%-- <div id="one"><img src="<%=request.getContextPath()%>/image/quanxian1.png"> </div> --%>

<script type="text/javascript">
var fieldsDisabled = <%=TableColInterface.getUpdateDataByTable("A01",sign)%>;
var selectDisabled = <%=TableColInterface.getSelectDataByTable("A01")%>;


Ext.onReady(function(){
	var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
	//����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ����ά�� 
	$h.fieldsDisabled(fieldsDisabled); 
	//����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ���Բ鿴
	$h.selectDisabled(selectDisabled,imgdata); 

});
Ext.onReady(function(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
});
/* function reShowMsg(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
	radow.doEvent('initX');
} */

/* function save(){
	radow.doEvent('save');
} */
function formcheck(){
	return odin.checkValue(document.forms.commForm);
}


</script>



