<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ include file="/comOpenWinInit2.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<table width="100%">
		<tr align="center">
	    	<td colspan="4"><label><input type="radio" id='r1' name="radio_1" value="1" onclick="updateMC()" >添加到现有名单</label>
	    	 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label><input type="radio"  name="radio_1" value="2" onclick="updateMC()" >添加到新名单</label><td>
		</tr>
		<tr height="20">
			<td colspan="4"></td>
		</tr>
		<tr id='tr1' align="left">
			<odin:select2  property="mdtype" colspan="4" label="现有名单名称" width="400" ></odin:select2>
		</tr>
		<tr id="tr2" align="left">
			<odin:textEdit property="mdmc" colspan="4" label="新名单名称" width="400" ></odin:textEdit>
		</tr>
		<%-- <tr id="tr3">
			<tags:ComBoxWithTree property="mnur01" label="选择用户" width="400" readonly="true" ischecked="true" codetype="USER" listHeight="300" />
		</tr> --%>
		<tr align="left">
			<odin:select2 property="mdtype2" colspan="4" label="名单类型" codeType="GZMD02" width="400"  onchange="updateBq()"></odin:select2>
		</tr>
		<tr align="left">
			<odin:select2 property="bqtype" colspan="4" label="标签类型" codeType="GZMD01" width="400" ></odin:select2>
		</tr>
		<tr height="10">
			<td colspan="4"></td>
		</tr>
		<tr align="center">
			<td colspan="4"><odin:button text="保存"  handler="save"></odin:button></td>
		</tr>
</table>	
<script type="text/javascript">
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	//document.getElementById("tr1").style.display="none";
	document.getElementById("r1").checked='true';
	document.getElementById("tr2").style.display="none";
	
	
	Ext.getCmp('mdtype_combo').on('select',function(){
		radow.doEvent('setMnur01');
	})
	
});


function updateMC(){
	var r1=$("input[name='radio_1']:checked").val();
	if(r1=='1'){
		document.getElementById("tr1").style.display="";
		document.getElementById("tr2").style.display="none";
		document.getElementById("mdmc").value="";
		odin.setSelectValue("mdtype", '');
		
	}else if(r1=='2'){
		document.getElementById("tr1").style.display="none";
		document.getElementById("tr2").style.display="";
		//$('#mnur01_combotree').val('');
		//$('#mnur01').val('');
		//Ext.getCmp('mnur01_combotree').clearCheck();
	}
	odin.setSelectValue("mdtype2", '');
	odin.setSelectValue("bqtype", '');
	radow.doEvent('setDisable',r1);
	//$('#tr3').show();
}

function save(){
	var r1=$("input[name='radio_1']:checked").val();
	if(r1=='1'){
		var mdtype=document.getElementById("mdtype").value;
		if(mdtype==""){
			alert('请先选择导入到的人员名单！');
	        return;
		}
		radow.doEvent('save1',mdtype);
	}else if(r1=='2'){
		var mdmane=document.getElementById("mdmc").value;
		if(mdmane==null){
			alert('请先输入新的名单名称！');
	        return;
		}
		radow.doEvent('save2',mdmane);
	}
}

function updateBq(){
	var mdtype2=document.getElementById("mdtype2").value;
	if(mdtype2=='03'){
		radow.doEvent('setDisable','3');
	}else{
		radow.doEvent('setDisable','4');
	}
	odin.setSelectValue("bqtype", '');
}

function saveCallBack(){
	window.close();
}
</script>
