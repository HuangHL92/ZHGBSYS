<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<div id="addMeetingContent" align="center">	
<table style="width: 70%;height: 92%;">
	<tr>
		<odin:textEdit property="condname" label="条件名称" width="400"></odin:textEdit>
	</tr>
	<tr>
		<odin:textEdit property="conddesc" label="岗位画像描述"  width="400"></odin:textEdit>
	</tr>
	<tr>
		<odin:select2 property="cs" label="关系"  width="400" data="['=','等于'],['>','大于'],['>=','大于等于'],['<','小于'],['<=','小于等于'],['in','包含'],['not in','不包含']"></odin:select2>
	</tr>
	<tr id="h1">
		<odin:textEdit property="cs2" label="值"  width="400"></odin:textEdit>
	</tr>
	<tr id="h2">
		<tags:ComBoxWithTree property="code" label="值" codetype="ZB09" readonly="true" ischecked="true" width="400"></tags:ComBoxWithTree>
	</tr>
	<tr id="h4">
		<odin:textarea property="code2" label="熟悉领域" colspan="2" rows="5" onclick="sxlyClick();"></odin:textarea>
	</tr>
	<tr id="h3">
		<odin:textEdit property="grade" label="权重分数"  width="400"></odin:textEdit>
	</tr>
</table>
</div>
<odin:hidden property="sxly"/>
<odin:hidden property="code_type"/>
<odin:hidden property="tjtype"/>
<odin:hidden property="query"/>
<odin:hidden property="type1"/>
<odin:hidden property="id"/>
<odin:hidden property="mxcs_p"/>
<odin:hidden property="mxsql_p"/>
<odin:toolBar property="btnToolBar">
	<odin:fill/>
	<odin:buttonForToolBar id="btnSave" text="保存"  icon="images/save.gif" cls="x-btn-text-icon" handler="save" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="addMeetingContent" property="addMeetingPanel" topBarId="btnToolBar"></odin:panel>
<script type="text/javascript">
Ext.onReady(function(){
	var type1=parentParam.type1;
	document.getElementById('type1').value = parentParam.type1;
	document.getElementById('id').value = parentParam.id;
	document.getElementById('query').value = parentParam.query;
	var cs=parentParam.cs;
	if(cs=='in'||cs=='not in'){
		document.getElementById('h1').style.display='none';
	}else{
		document.getElementById('h2').style.display='none';
		document.getElementById('h4').style.display='none';
	}
	if(type1=='zg'){
		document.getElementById('h3').style.display='none';
	}
});

function save(){
	radow.doEvent("save");
}

function sxlyClick(){
    var Id = document.getElementById("id").value;
    var type1 = document.getElementById("type1").value;
    var query = document.getElementById("query").value;
    if(type1=='zg'){
    	Id="ZDGW_ZGMX"+query+"##zgmxid='"+Id+"'";
    }else{
    	Id="ZDGW_QZMX"+query+"##qzmxid='"+Id+"'";
    }
    $h.openPageModeWin('zdgwsxly','pages.mntpsj.PZZDGWSXLY','熟悉领域',800,450,Id,"<%=request.getContextPath()%>");
}

</script>
