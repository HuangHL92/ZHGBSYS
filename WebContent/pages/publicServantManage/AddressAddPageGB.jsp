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
<odin:hidden property="a0000" title="人员主键"/>
<table style="width:100%;margin-top: 50px;">
	<tr>
		<odin:textEdit property="a3701" label="工作单位通信地址" ></odin:textEdit>
		<odin:textEdit property="a3707a" label="办公电话" ></odin:textEdit>
					
	</tr>
	<tr>
		<td colspan="6"><div style="height: 5px"></div></td>
	</tr>
	<tr>
		<odin:numberEdit property="a3707b" label="联络员电话" ></odin:numberEdit>	
		<odin:numberEdit property="a3707c" label="移动电话" ></odin:numberEdit>	
	</tr>
	<tr>
		<td colspan="6"><div style="height: 5px"></div></td>
	</tr>
	<tr>
		<odin:textEdit property="a3711" label="家庭住址"></odin:textEdit>
		<odin:textEdit property="a3721" label="户籍地址"></odin:textEdit>
	</tr>
	<!-- <tr>
		<td><button onclick=" location=location ">刷新</button></td>
	</tr> -->
	
</table>
<div id="bottom_div01">
	<div align="center">
		<odin:button text="保&nbsp;&nbsp;存" property="save" />
	</div>		
</div> 
<script type="text/javascript">
var fieldsDisabled = <%=TableColInterface.getUpdateDataByTable("A37",sign)%>;
var selectDisabled = <%=TableColInterface.getSelectDataByTable("A37")%>;


Ext.onReady(function(){
	//对信息集明细的权限控制，是否可以维护 
	$h.fieldsDisabled(fieldsDisabled); 
	//对信息集明细的权限控制，是否可以查看
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



