<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath(); 
String sign = request.getParameter("sign");
%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.TableColInterface"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<style>
body {
	background-color: rgb(214,227,243);
}

</style>
<odin:hidden property="a0000" title="��Ա����"/>
<table style="width:100%;margin-top: 50px;">
	<tr>
		<odin:textEdit property="a3701" label="������λͨ�ŵ�ַ" ></odin:textEdit>
		<odin:textEdit property="a3707a" label="�칫�绰" ></odin:textEdit>
					
	</tr>
	<tr>
		<td colspan="6"><div style="height: 5px"></div></td>
	</tr>
	<tr>
		<odin:numberEdit property="a3707b" label="����Ա�绰" ></odin:numberEdit>	
		<odin:numberEdit property="a3707c" label="�ƶ��绰" ></odin:numberEdit>	
	</tr>
	<tr>
		<td colspan="6"><div style="height: 5px"></div></td>
	</tr>
	<tr>
		<odin:textEdit property="a3711" label="��ͥסַ"></odin:textEdit>
		<odin:textEdit property="a3721" label="������ַ"></odin:textEdit>
	</tr>
	<!-- <tr>
		<td><button onclick=" location=location ">ˢ��</button></td>
	</tr> -->
	
</table>
<div id="bottom_div01">
	<div align="center">
		<odin:button text="��&nbsp;&nbsp;��" property="save" />
	</div>		
</div> 
<script type="text/javascript">
var fieldsDisabled = <%=TableColInterface.getUpdateDataByTable("A37",sign)%>;
var selectDisabled = <%=TableColInterface.getSelectDataByTable("A37")%>;


Ext.onReady(function(){
	//����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ����ά�� 
	$h.fieldsDisabled(fieldsDisabled); 
	//����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ���Բ鿴
	//var imgdata = "<img src='<%=request.getContextPath()%>/image/quanxian1.png' />";
	var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
	$h.selectDisabled(selectDisabled,imgdata); 
	
});
/* function save(){
	radow.doEvent('save');
} */
Ext.onReady(function(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
});
function reShowMsg(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
	radow.doEvent('initX');
}
function formcheck(){
	return odin.checkValue(document.forms.commForm);
}
</script>



