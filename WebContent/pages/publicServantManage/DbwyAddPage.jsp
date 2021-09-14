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
<odin:hidden property="id" title="主键id"></odin:hidden>
<table cellspacing="2" width="900" align="center" style="width: 100%">
	<tr>
		<odin:select2  property="rank" label="级别"  data="['1', '中央'],['2', '省'],['3', '市'],['4', '区县市']" ></odin:select2>
        <odin:select2  property="dbwy" label="代表委员"  data="['1', '市委委员'],['2', '人大常委'],['3', '人大代表'],['4', '政协委员'],['5', '党代表'],['6', '纪委委员']" ></odin:select2>
		<odin:textEdit property="xqjb"  label="选区/届别"  />
	</tr>
	<tr>
		<odin:NewDateEditTag property="rzsj" isCheck="true" label="任职时间"/>
        <odin:NewDateEditTag property="mzsj" isCheck="true" label="免职时间"/>
	</tr>
	<tr>
		<td colspan="12">
			<odin:grid property="TrainingInfoGrid" topBarId="btnToolBar" sm="row"  isFirstLoadData="false" url="/"
			 height="330">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="id" />
			  		<odin:gridDataCol name="rank" />
			  		<odin:gridDataCol name="dbwy"/>
			  		<odin:gridDataCol name="xqjb" />
			  		<odin:gridDataCol name="rzsj"/>
			   		<odin:gridDataCol name="mzsj" />	
			   		<odin:gridDataCol name="delete" isLast="true"/>			  		   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <%-- <odin:gridEditColumn2 header="输出标识" width="50" editor="checkbox" dataIndex="a0525" required="true" checkBoxClick="a05checkBoxColClick"  edited="true"/> --%>
				  <odin:gridEditColumn2 header="级别" align="center" dataIndex="rank" editor="select" edited="false" width="300" selectData="['1', '中央'],['2', '省'],['3', '市'],['4', '区县市']"/>
				  <odin:gridEditColumn2 header="代表委员" align="center" dataIndex="dbwy" editor="select" edited="false" width="300" selectData="['1', '市委委员'],['2', '人大常委'],['3', '人大代表'],['4', '政协委员'],['5', '党代表'],['6', '纪委委员']" />
				  <odin:gridEditColumn2 header="选区/届别" align="center" dataIndex="xqjb" editor="text" edited="false"  width="300"/>
				  <odin:gridEditColumn2 header="任职时间" align="center" dataIndex="rzsj" editor="text" edited="false" width="200"/>
				  <odin:gridEditColumn2 header="免职时间" align="center" dataIndex="mzsj" editor="text" width="200" edited="false"/>
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
	var id1 = record.data.id;
	if(realParent.buttonDisabled){
		return "删除";
	}
	return "<a href=\"javascript:deleteRow2(&quot;"+id1+"&quot;)\">删除</a>";
}
function deleteRow2(id1){ 
	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',id1);
		}else{
			return;
		}		
	});	
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
	var rank = document.getElementById("rank").value;//级别
	var dbwy = document.getElementById("dbwy").value;//代表委员
	var rzsj= document.getElementById("rzsj").value;//任职时间

	
	//批准日期、状态必填 （后台有校验）
	if(!rank){
		$h.alert('系统提示','级别不能为空！', null,200);
		return false;
	}
	if(!dbwy){
		
		$h.alert('系统提示',' 不能为空！', null,200);
		return false;
	}
	
	if("0"==rzsj && !rzsj){
		$h.alert('系统提示','任职时间不能为空！', null,200);
		return false;
	}	
	radow.doEvent('saveDbwy.onclick');
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