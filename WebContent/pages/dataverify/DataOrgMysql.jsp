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
	<odin:buttonForToolBar text="导出" id="expbtn" icon="images/icon/exp.png" handler="exp" isLast="true"/>
</odin:toolBar>
<div id="panel_content" align="center">
		<div id="toolDiv"></div>
		<odin:groupBox property="s10" title="路径配置">
			<table cellspacing="2" width="98%" align="center">
				<tr>
					<odin:textEdit property="mysqlPass" label="MYSQL压缩包保存路径" value="C:/DATAS" colspan="4" size="78" maxlength="300" required="true" ></odin:textEdit>
				</tr>
				<tr>
					<td height="20px"></td>
				</tr>
				
				<tr>
					<td colspan="4">
						<label id="bz1" style="font-size: 12;color: red">&nbsp;&nbsp;&nbsp;注：建议使用administrator用户。</label><br>
						<label id="bz1" style="font-size: 12;color: red">&nbsp;&nbsp;&nbsp;注：此功能只允许SYSTEM系统管理员上报使用。</label><br>
						<label id="bz1" style="font-size: 12;color: red">&nbsp;&nbsp;&nbsp;注：此功能将上报数据库所有机构、人员数据。</label><br>
						<label id="bz1" style="font-size: 12;color: red">&nbsp;&nbsp;&nbsp;注：路径以反斜杠“/”作为分隔符（如：c：/）。</label><br>
					</td>
				</tr>
			</table>
		</odin:groupBox>
		<odin:groupBox property="s11" title="选填">
			<table cellspacing="2" width="98%" align="center">
				<tr>
					<odin:textEdit property="linkpsn" colspan="1" width="180" label="联系人"/>
					<odin:textEdit property="linktel" width="180" colspan="1" label="联系电话"/>
				</tr>
				<tr>
					<odin:textarea property="remark" colspan="4" cols="4" style="width:540px;" label="备 注"/>
				</tr>
			</table>
		</odin:groupBox>
		
</div>

<script type="text/javascript">
Ext.onReady(function() {
	//页面调整
	document.getElementById("panel_content").style.width = document.body.clientWidth + "px";
	document.getElementById("toolDiv").style.width = document.body.clientWidth + "px";
	
});

function exp(){
	var path = document.getElementById('mysqlPass').value;
	var reg = new RegExp("\\\\")
	if(path.match(reg)){
		alert("路径以反斜杠“/”作为分隔符");
		return;
	}
	var id = uuid();
	$h.openWin('refreshWin','pages.dataverify.RefreshOrgExp','导出详情',600,300,id,'<%=request.getContextPath()%>');
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