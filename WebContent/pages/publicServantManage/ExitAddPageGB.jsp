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
		<tags:PublicTextIconEdit property="a3001" label="退出本单位变动类别" codetype="ZB78" readonly="true" onchange="setA3954ADisabled" />
		<odin:NewDateEditTag property="a3004" isCheck="true" label="退出本单位日期" maxlength="8" ></odin:NewDateEditTag>
	</tr>
	<tr>
		<td colspan="6"><div style="height: 5px"></div></td>
	</tr>
	<tr>
		<odin:textEdit property="a3034" label="退出备注" ></odin:textEdit>
		<odin:textEdit property="a3007a" label="退出去向" ></odin:textEdit>
	</tr>
	<tr>
		<td colspan="6"><div style="height: 5px"></div></td>
	</tr>
	<tr>
		<odin:textEdit property="a3038" label="审批单位" ></odin:textEdit>
	</tr>
	<tr>
		<td colspan="6"><div style="height: 5px"></div></td>
	</tr>
	<tr style="display: none;">
		<odin:NewDateEditTag property="a3954a" isCheck="true" label="死亡时间" maxlength="8"></odin:NewDateEditTag>
		<odin:textEdit property="a3954b" label="死亡原因"></odin:textEdit>
		<!-- <td><button onclick=" location=location ">刷新</button></td> -->
	</tr>
</table>
<div id="bottom_div01">
	<div align="center">
		<odin:button text="保&nbsp;&nbsp;存" property="save" />
	</div>		
</div> 
<script type="text/javascript">
var fieldsDisabled = <%=TableColInterface.getUpdateDataByTable("A30",sign)%>;
var selectDisabled = <%=TableColInterface.getSelectDataByTable("A30")%>;

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


function setA3954ADisabled(){
	var value = document.getElementById("a3001").value;
	if(value=='31'||value=='32'){
		$h.dateEnable("a3954a");
		Ext.getCmp('a3954b').setDisabled(false);
	}else{
		$h.dateDisable("a3954a");
		Ext.getCmp('a3954b').setValue('');
		Ext.getCmp('a3954b').setDisabled(true);
	}
}

function reShowMsg(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
	radow.doEvent('initX');
}
function formcheck(){
	return odin.checkValue(document.forms.commForm);
}
</script>




