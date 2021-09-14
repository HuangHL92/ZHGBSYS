<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@include file="/comOpenWinInit.jsp" %>

<% String ctxPath = request.getContextPath();%>

<link rel="stylesheet" type="text/css" href="/hzb/basejs/ext/resources/css/ext-all.css"/>
<link rel="stylesheet" type="text/css" href="/hzb/css/odin.css"/>
<link rel="stylesheet" type="text/css" href="/hzb/basejs/ext/ux/css/Ext.ux.form.LovCombo.css"/>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<style>
 #tableid1{position:relative;left:10px;top: 10px;} 
</style>

<odin:toolBar property="btnToolBar" applyTo="toolDiv">
	<odin:textForToolBar text=""/>
	<odin:fill/>
	<odin:buttonForToolBar text="����" id="expbtn" icon="images/icon/exp.png" handler="exp" isLast="true"/>
</odin:toolBar>
<div id="panel_content" align="center">
		<div id="toolDiv"></div>
		<odin:groupBox property="s10" title="·������">
			<table cellspacing="2" width="98%" align="center">
				<tr>
					<odin:textEdit property="mysqlPass" label="MYSQLѹ��������·��" value="C:/DATAS" colspan="4" size="78" maxlength="300" required="true" ></odin:textEdit>
				</tr>
				<tr>
					<td height="20px"></td>
				</tr>
				
				<tr>
					<td colspan="4">
						<label id="bz1" style="font-size: 12;color: red">&nbsp;&nbsp;&nbsp;ע������ʹ��administrator�û���</label><br>
						<label id="bz1" style="font-size: 12;color: red">&nbsp;&nbsp;&nbsp;ע���˹���ֻ����SYSTEMϵͳ����Ա�ϱ�ʹ�á�</label><br>
						<label id="bz1" style="font-size: 12;color: red">&nbsp;&nbsp;&nbsp;ע���˹��ܽ��ϱ����ݿ����л�������Ա���ݡ�</label><br>
						<label id="bz1" style="font-size: 12;color: red">&nbsp;&nbsp;&nbsp;ע��·���Է�б�ܡ�/����Ϊ�ָ������磺c��/����</label><br>
					</td>
				</tr>
			</table>
		</odin:groupBox>
		<odin:groupBox property="s11" title="ѡ��">
			<table cellspacing="2" width="98%" align="center">
				<tr>
					<odin:textEdit property="linkpsn" colspan="1" width="180" label="��ϵ��"/>
					<odin:textEdit property="linktel" width="180" colspan="1" label="��ϵ�绰"/>
				</tr>
				<tr>
					<odin:textarea property="remark" colspan="4" cols="4" style="width:540px;" label="�� ע"/>
				</tr>
			</table>
		</odin:groupBox>
		
</div>

<script type="text/javascript">
Ext.onReady(function() {
	//ҳ�����
	document.getElementById("panel_content").style.width = document.body.clientWidth + "px";
	document.getElementById("toolDiv").style.width = document.body.clientWidth + "px";
	
});

function exp(){
	var path = document.getElementById('mysqlPass').value;
	var reg = new RegExp("\\\\")
	if(path.match(reg)){
		alert("·���Է�б�ܡ�/����Ϊ�ָ���");
		return;
	}
	var id = uuid();
	$h.openWin('refreshWin','pages.dataverify.RefreshOrgExp','��������',600,300,id,'<%=request.getContextPath()%>');
	parent.Ext.getCmp(subWinId).close();
	if(!parent.Ext.getCmp(subWinId)){
		radow.doEvent('exp',id);
	}
}


function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "";
    var uuid = s.join("");
    return uuid;
}
</script>