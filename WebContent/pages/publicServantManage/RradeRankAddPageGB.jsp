<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath(); 
%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<style>
#btnAdd{position: absolute;top:550px;left:400px;}
#btnSave{position: absolute;top:550px;left:450px;}
#btnCancel{position: absolute;top:550px;left:500px;}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<body>
	


<div id="border">
<odin:hidden property="a0134" title="参加工作时间"/>
<odin:hidden property="a0500" title="主键id"></odin:hidden>
<odin:hidden property="a0000" title=""></odin:hidden>

<table cellspacing="2" width="960" align="center" style="width: 100%">
	<tr><div style="height: 20px"></div></tr>
	<tr>
		<td>
			<table>
				<tr>
					<td>
						<tags:PublicTextIconEdit property="a0501b" codetype="ZB148" width="160" label="职级" required="true" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td>
						<odin:select2 property="a0524" label="状&nbsp;&nbsp;态&nbsp;" codeType="ZB14" onchange="setA0517Disabled" required="true"></odin:select2>
					</td>
				</tr>
				<tr>
					<td>
						<odin:NewDateEditTag property="a0504" isCheck="true" label="批准日期" maxlength="8" required="true"></odin:NewDateEditTag>
					</td>
				</tr>
				<tr>
					<td>
						<odin:NewDateEditTag property="a0517" isCheck="true" label="&nbsp;&nbsp;终止日期" maxlength="8"></odin:NewDateEditTag>
					</td>
				</tr>
			</table>
		</td>
		<td>
			<div style="width:200px"></div>
		</td>
		<td>
			
			<odin:grid property="TrainingInfoGrid" topBarId="toolBar8" sm="row"  isFirstLoadData="false" url="/"
			 height="230">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<%-- <odin:gridDataCol name="a0525" /> --%>
					<odin:gridDataCol name="a0500" />
			  		<odin:gridDataCol name="a0501b" />
			  		<odin:gridDataCol name="a0504"/>
			  		<%-- <odin:gridDataCol name="a0511" /> --%>
			  		<odin:gridDataCol name="a0517" />
			   		<odin:gridDataCol name="a0524" isLast="true"/>			   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <%-- <odin:gridEditColumn2 header="输出标识" width="50" editor="checkbox" dataIndex="a0525" required="true" checkBoxClick="a05checkBoxColClick"  edited="true"/> --%>
				  <odin:gridEditColumn2 header="主键" dataIndex="a0500" editor="text" edited="false" hidden="true"/>
				  <odin:gridEditColumn2 header="职级" dataIndex="a0501b" editor="select" edited="false" codeType="ZB148" width="80"/>
				  <odin:gridEditColumn2 header="批准日期" dataIndex="a0504" editor="text" edited="false" width="80"/>
				 <%--  <odin:gridEditColumn header="批准文号" dataIndex="a0511" editor="text" edited="false" width="100"/> --%>
				  <odin:gridEditColumn2 header="终止日期" dataIndex="a0517" editor="text" width="80" edited="false"/>
				  <%-- <odin:gridEditColumn header="状态" dataIndex="a0524" editor="text" edited="false" width="100"/> --%>
				  <odin:gridEditColumn2 header="状态" dataIndex="a0524" editor="select" edited="false" selectData="['1','在任'],['0','已免']" width="80"/>
				   <odin:gridEditColumn2 width="45" header="操作" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
		</td>
	</tr>
	<%-- <tr>
		<odin:select2 property="a0501b" label="&nbsp;职&nbsp;&nbsp;级&nbsp;" codeType="ZB148" required="true"></odin:select2>
		<tags:PublicTextIconEdit property="a0501b" codetype="ZB148" width="160" label="职级" required="true" readonly="true"/>
		<td>&nbsp;</td>
		<odin:select2 property="a0524" label="状&nbsp;&nbsp;态&nbsp;" codeType="ZB14" onchange="setA0517Disabled" required="true"></odin:select2>
		<td>&nbsp;</td>
		<odin:NewDateEditTag property="a0504" isCheck="true" label="批准日期" maxlength="8" required="true"></odin:NewDateEditTag>
	</tr> --%>
	<%-- <tr>	
		<odin:NewDateEditTag property="a0517" isCheck="true" label="&nbsp;&nbsp;终止日期" maxlength="8"></odin:NewDateEditTag>
		<odin:textEdit property="a0511" label="&nbsp;&nbsp;批准文号" validator="a0511Length"></odin:textEdit>
	</tr> --%>

	<!-- <tr>
		
	</tr> -->
</table>
</div>
<div id='btnAdd'>
<odin:button text="新&nbsp;&nbsp;增" handler="AddBtn"></odin:button>
</div>
<div id='btnSave'>
<odin:button text="保&nbsp;&nbsp;存" handler="saveTrain"></odin:button>
</div>
<div id='btnCancel'>
<odin:button text="取&nbsp;&nbsp;消" handler="Cancel"></odin:button>
<odin:hidden property="a0531" value="0"/>
<odin:hidden property="a0525"/>
</body>
<script type="text/javascript">
function AddBtn(){
	radow.doEvent('TrainingInfoAddBtn.onclick');
}
function Cancel(){
	alert("demo");
}
function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var a0500 = record.data.a0500;
	/* if(realParent.buttonDisabled){
		return "删除";
	} */
	return "<a href=\"javascript:deleteRow2(&quot;"+a0500+"&quot;)\">删除</a>";
}
function deleteRow2(a0500){ 
	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',a0500);
		}else{
			return;
		}		
	});	
}

function setA0517Disabled(){
	var value = document.getElementById("a0524").value;
	if("0"==value || value==''){
		document.getElementById("a0517_1").readOnly=false;
		document.getElementById("a0517_1").disabled=false;
		document.getElementById("a0517_1").style.backgroundColor="#fff";
		document.getElementById("a0517_1").style.backgroundImage="url(<%=ctxPath%>/commform/basejs/ext/resources/images/default/form/text-bg.gif)";
		
<%-- 		document.getElementById("a0511").readOnly=false;
		document.getElementById("a0511").disabled=false;
		document.getElementById("a0511").style.backgroundColor="#fff";
		document.getElementById("a0511").style.backgroundImage="url(<%=ctxPath%>/commform/basejs/ext/resources/images/default/form/text-bg.gif)"; --%>
	}else if("1"==value){
		document.getElementById("a0517_1").readOnly=true;
		document.getElementById("a0517_1").disabled=true;
		document.getElementById("a0517_1").style.backgroundColor="#EBEBE4";
		document.getElementById("a0517_1").style.backgroundImage="none";
		document.getElementById("a0517").value="";
		document.getElementById("a0517_1").value="";
		
/* 		document.getElementById("a0511").readOnly=true;
		document.getElementById("a0511").disabled=true;
		document.getElementById("a0511").style.backgroundColor="#EBEBE4";
		document.getElementById("a0511").style.backgroundImage="none";
		document.getElementById("a0511").value=""; */
	}
}

function saveTrain(){
	document.getElementById("a0000").value = window.parent.frames["BaseAddPage_GB"].document.getElementById("a0000").value;
	var a0524 = document.getElementById("a0524").value;//状态
	var a0504 = document.getElementById("a0504").value;//批准日期
	var a0517 = document.getElementById('a0517').value;//结束日期
	var a0504_1 = document.getElementById("a0504_1").value;		//批准日期页面显示值
	var a0517_1 = document.getElementById("a0517_1").value;		//结束日期页面显示值 
	
	var a0501b = document.getElementById("a0501b").value;
	
	//批准日期、状态必填 （职务层次后台有校验）
	if(!a0524){
		$h.alert('系统提示','状态不能为空！', null,200);
		return false;
	}
	if(!a0504_1){
		
		$h.alert('系统提示','批准日期不能为空！', null,200);
		return false;
	}
	
	if("0"==a0524 && !a0517_1){
		$h.alert('系统提示','终止日期不能为空！', null,200);
		return false;
	}
	var now = new Date();
	
	var value = a0504;
	var length = value.length;
	var year = value.substring(0,4);
	var month = value.substring(4,6);
	var day;
	if(length==8){
		day = value.substring(6,8);
	}else{
		day = '01';
	}
	year = parseInt(year,10); month = parseInt(month,10); day = parseInt(day,10);
	date2=new Date(year, month-1,day)
	if(Date.parse(date2)>Date.parse(now)){
		odin.alert("批准日期不能晚于系统当前时间");
		return false;
	}
	
	value = a0517;
	var length = value.length;
	var year = value.substring(0,4);
	var month = value.substring(4,6);
	var day;
	if(length==8){
		day = value.substring(6,8);
	}else{
		day = '01';
	}
	year = parseInt(year,10); month = parseInt(month,10); day = parseInt(day,10);
	date2=new Date(year, month-1,day)
	
	if(Date.parse(date2)>Date.parse(now)){
		odin.alert("终止日期不能晚于系统当前时间");
		return false;
	}
	
	
	
	var text1 = dateValidateBeforeTady(a0504_1);
	var text2 = dateValidateBeforeTady(a0517_1);
	
	if(a0504_1.indexOf(".") > 0){
		text1 = dateValidateBeforeTady(a0504);
	}
	if(a0517_1.indexOf(".") > 0){
		text2 = dateValidateBeforeTady(a0517);
	}
	
	if(text1!==true){
		$h.alert('系统提示','批准日期' + text1, null,400);
		return false;
	}
	if(text2!==true){
		$h.alert('系统提示','终止日期' + text2, null,400);
		return false;
	}
	
	radow.doEvent('saveA11.onclick');
}
Ext.onReady(function(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
});
/* Ext.onReady(function(){
	
	document.getElementById("a0134").value=$("#a0134", realParent.document).val();
	
	//$h.fieldsDisabled(realParent.fieldsDisabled);
	if(realParent.buttonDisabled){
		$h.setDisabled($h.disabledButtons.xzj);
	}

}); */
/* function deleteRow(){ 
	var sm = Ext.getCmp("TrainingInfoGrid").getSelectionModel();
	if(!sm.hasSelection()){
		parent.$h.alert("系统提示","请选择一行数据！");
		return;
	}
	parent.Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',sm.lastActive+'');
		}else{
			return;
		}		
	});	
} */
/* function a05checkBoxColClick(rowIndex,colIndex,dataIndex,gridName){
	var a0525=Ext.getCmp(gridName).store.data.itemAt(rowIndex).get("a0525");
	var TrainingInfoGrid = Ext.getCmp("TrainingInfoGrid").store;
	for(var i=0;i<TrainingInfoGrid.data.length;i++){
		TrainingInfoGrid.data.itemAt(i).set("a0525",false);
	}
	if(a0525 == true){
		TrainingInfoGrid.data.itemAt(rowIndex).set("a0525",true);
		document.getElementById('a0525').value='1';
	}else{
		TrainingInfoGrid.data.itemAt(rowIndex).set("a0525",false);
		document.getElementById('a0525').value='0';
	}
	//radow.doEvent('TrainingInfoGrid.rowclick',sm.lastActive+'');
	radow.doEvent('selectFun.click',rowIndex+""+"_"+document.getElementById('a0525').value);
} */
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
}

#toolBar8 {
	width: 690px !important;
}
</style>
