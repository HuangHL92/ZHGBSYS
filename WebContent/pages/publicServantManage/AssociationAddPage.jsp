<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath(); 
%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>
<body>


<div id="btnToolBarDiv" style="width: 1019px;"></div>
<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="新增" id="TrainingInfoAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
				<odin:buttonForToolBar text="保存" id="save1" handler="saveTrain" icon="images/save.gif" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>
				<%-- <odin:buttonForToolBar text="&nbsp;&nbsp;删除" isLast="true" icon="images/back.gif" id="delete" handler="deleteRow"></odin:buttonForToolBar> --%>
</odin:toolBar>

<div id="border" >
<div id="tol2" align="left"></div>
<odin:hidden property="a0000" title="人员外键"></odin:hidden>
<odin:hidden property="st00" title="主键id"></odin:hidden>
<odin:hidden property="STJZ" title="社团兼职职务 "></odin:hidden>
<table cellspacing="2" width="900" align="center" style="width: 100%">
	<tr>
		<odin:textEdit property="stname"  width="160" label="社团名称" required="true" />
		<td>&nbsp;</td>
		<odin:textEdit property="stjob"  width="160" label="社团职务" required="true" />
		<td>&nbsp;</td>
		<odin:select2 property="stnature" label="社团性质" data="['社团','社团'],['基金会','基金会'],['行业协会','行业协会'],['其他','其他']" required="true"></odin:select2>
		<td>&nbsp;</td>
		<odin:select2 property="status" label="状&nbsp;&nbsp;态&nbsp;" data="['1','在任'],['0','已免']" onchange="setClosingDateDisabled" required="true"></odin:select2>
	</tr>
	<tr>
		<odin:NewDateEditTag property="startdate" isCheck="true" label="同意兼职期间 起始" maxlength="8"></odin:NewDateEditTag>
		<td>&nbsp;</td>
		<odin:NewDateEditTag property="closingdate" isCheck="true" label="同意兼职期间 终止" maxlength="8"></odin:NewDateEditTag>
		<td>&nbsp;</td>
		<odin:textEdit property="sessionsnum"  width="160" label="担任届数"  />
		<td>&nbsp;</td>
		<odin:select2 property="salary" label="是否取薪" data="['1','是'],['0','否']"  required="true"></odin:select2>
	</tr>
	<tr>
	<odin:NewDateEditTag property="approvaldate" isCheck="true" label="批准日期" maxlength="8" required="true" ></odin:NewDateEditTag>
	</tr>
	<tr>
		<td colspan="12">
			
			<odin:grid property="TrainingInfoGrid" topBarId="btnToolBar" sm="row"  isFirstLoadData="false" url="/"
			 height="330">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="st00" />
			  		<odin:gridDataCol name="stname" />
			  		<odin:gridDataCol name="stjob"/>
			  		<odin:gridDataCol name="stnature" />
			  		<odin:gridDataCol name="status"/>
			   		<odin:gridDataCol name="approvaldate"/>			  
			   		<odin:gridDataCol name="startdate"/>	
			   		<odin:gridDataCol name="closingdate"/>	
			   		<odin:gridDataCol name="sessionsnum"/>	
			   		<odin:gridDataCol name="salary"/>		 		
			   		<odin:gridDataCol name="delete" isLast="true"/>			   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <%-- <odin:gridEditColumn2 header="输出标识" width="50" editor="checkbox" dataIndex="a0525" required="true" checkBoxClick="a05checkBoxColClick"  edited="true"/> --%>
				  <odin:gridEditColumn2 header="社团名称" align="center" dataIndex="stname" editor="text" edited="false" width="300"/>
				  <odin:gridEditColumn2 header="社团职务" align="center" dataIndex="stjob" editor="text" edited="false" width="300"/>
				  <odin:gridEditColumn2 header="社团性质" align="center" dataIndex="stnature" editor="text" edited="false"  width="300"/>
				  <odin:gridEditColumn2 header="状态" align="center" dataIndex="status" editor="select" edited="false" selectData="['1','在任'],['0','已免']" width="300"/>
				  <odin:gridEditColumn2 header="批准日期" align="center" dataIndex="approvaldate" editor="text" edited="false" width="200"/>
				 <%--  <odin:gridEditColumn header="批准文号" align="center" dataIndex="a0511" editor="text" edited="false" width="100"/> --%>
				 <odin:gridEditColumn2 header="起始日期" align="center" dataIndex="startdate" editor="text" edited="false" width="200"/>
				  <odin:gridEditColumn2 header="终止日期" align="center" dataIndex="closingdate" editor="text" width="200" edited="false"/>
				  <%-- <odin:gridEditColumn header="状态" dataIndex="a0524" editor="text" edited="false" width="100"/> --%>
				  <odin:gridEditColumn2 header="担任届数" align="center" dataIndex="sessionsnum" editor="text" edited="false" width="300"/>
				  <odin:gridEditColumn2 header="是否取薪" align="center" dataIndex="salary" editor="select" edited="false" selectData="['1','是'],['0','否']" width="300"/>
				  <odin:gridEditColumn2 width="150" header="操作" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
		</td>
	</tr>
</table>
</div>
</body>
<script type="text/javascript">

//删除行
function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var st00 = record.data.st00;
	if(realParent.buttonDisabled){
		return "删除";
	}
	return "<a href=\"javascript:deleteRow2(&quot;"+st00+"&quot;)\">删除</a>";
}
function deleteRow2(st00){ 
	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',st00);
		}else{
			return;
		}		
	});	
}



//通过状态确定有无终止日期
function setClosingDateDisabled(){
	var value = document.getElementById("status").value;
	if("0"==value || value==''){
		document.getElementById("closingdate_1").readOnly=false;
		document.getElementById("closingdate_1").disabled=false;
		document.getElementById("closingdate_1").style.backgroundColor="#fff";
		document.getElementById("closingdate_1").style.backgroundImage="url(<%=ctxPath%>/commform/basejs/ext/resources/images/default/form/text-bg.gif)";
		
	}else if("1"==value){
		document.getElementById("closingdate_1").readOnly=true;
		document.getElementById("closingdate_1").disabled=true;
		document.getElementById("closingdate_1").style.backgroundColor="#EBEBE4";
		document.getElementById("closingdate_1").style.backgroundImage="none";
		document.getElementById("closingdate").value="";
		document.getElementById("closingdate_1").value="";	
	}
}


function deleteRow(){ 
	var sm = Ext.getCmp("TrainingInfoGrid").getSelectionModel();
	if(!sm.hasSelection()){
		$h.alert("系统提示","请选择一行数据！");
		return;
	}
	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',sm.lastActive+'');
		}else{
			return;
		}		
	});	
}

//数据储存
function saveTrain(){
	var status = document.getElementById("status").value;//状态
	var approvaldate= document.getElementById("approvaldate").value;//批准日期
	var closingdate= document.getElementById('closingdate').value;//结束日期
	var approvaldate_1 = document.getElementById("approvaldate_1").value;		//批准日期页面显示值
	var closingdate_1 = document.getElementById("closingdate_1").value;		//结束日期页面显示值 
	var stname = document.getElementById("stname").value;
	
	//批准日期、状态必填 （后台有校验）
	if(!status){
		$h.alert('系统提示','状态不能为空！', null,200);
		return false;
	}
	if(!approvaldate_1){
		
		$h.alert('系统提示','批准日期不能为空！', null,200);
		return false;
	}
	
	if("0"==approvaldate && !closingdate_1){
		$h.alert('系统提示','终止日期不能为空！', null,200);
		return false;
	}	
	radow.doEvent('saveAssociation.onclick');
}



function lockINFO(){
	Ext.getCmp("TrainingInfoAddBtn").disable(); 
	Ext.getCmp("save1").disable(); 
	Ext.getCmp("TrainingInfoGrid").getColumnModel().setHidden(6,true); 
}
</script>
<style>
<%=FontConfigPageModel.getFontConfig()%>
</style>