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
	
<odin:toolBar property="toolBar8" applyTo="tol2">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="新增" id="TrainingInfoAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
				<odin:buttonForToolBar text="保存" id="save1" icon="images/save.gif" handler="savecw" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>

<div id="border">
<div id="tol2" align="left" style="margin-bottom:10px;"></div>
<odin:hidden property="a0200" title="外键id"></odin:hidden>
<odin:hidden property="a8600" title="主键id" />
<table cellspacing="2" align="center" style="width: 100%">
	<tr>
		<odin:NewDateEditTag property="a8601" label="日期" maxlength="8"></odin:NewDateEditTag>
		<odin:textarea cols="60" rows="3" property="a0299" label="分管从事工作"></odin:textarea>
	</tr>
		
	<tr>
		<td colspan="8">
			<odin:grid property="TrainingInfoGrid" topBarId="toolBar8" sm="row" 
			 isFirstLoadData="false" url="/"
			 height="230" pageSize="50">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="a8600" />
					<odin:gridDataCol name="a8602" />
			  		<odin:gridDataCol name="a8601" />
			  		<odin:gridDataCol name="a0299"/>			   		
			   		<odin:gridDataCol name="delete" isLast="true"/>			   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn /><!--  -->
				  <odin:gridEditColumn2 width="20" header="主键" dataIndex="a8600"  editor="text" edited="false" hidden="true"/>
				  <odin:gridEditColumn2 width="20" header="输出" checkBoxClick="checkBoxColClick" editor="checkbox"  dataIndex="a8602" edited="true" />
				  <odin:gridEditColumn2 width="50" header="时间" align="center" dataIndex="a8601" editor="select" edited="false"/>
				  <odin:gridEditColumn2 width="100" header="分管从事工作" align="center" dataIndex="a0299" editor="text" edited="false" />
				  <odin:gridEditColumn2 width="50" header="操作" dataIndex="delete" renderer="deleteRowRenderer"  editor="text" edited="false" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
		</td>
	</tr>
</table>
</div>
<odin:hidden property="a0531" value="0"/>
<odin:hidden property="a0525"/>
</body>
<script type="text/javascript">

function checkBoxColClick(rowIndex,colIndex,dataIndex,gridName){
	if(realParent.buttonDisabled){
		return;
	}
	
	var sr = getGridSelected(gridName);
	
	if(!sr){
		return;
	}
	radow.doEvent('savecheck',sr.data.a8600);
}

function clear(){
	document.getElementById('a8601_1').value='';
	document.getElementById('a0299').value='';
}


function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var a8600 = record.data.a8600;
	if(realParent.buttonDisabled){
		return "删除";
	}
	return "<a href=\"javascript:deleteRow2(&quot;"+a8600+"&quot;)\">删除</a>";
	return "删除";
}

function deleteRow2(a8600){
	Ext.Msg.confirm("系统提示","是否确定删除?",function(id){
		if("yes"==id){
			radow.doEvent('deleteRow',a8600);
		}else{
			return;
		}
	})
}


Ext.onReady(function(){
	var heightClient=document.body.clientHeight;//窗口高度
	var gird_dom=Ext.getCmp('TrainingInfoGrid');//获取grid对象
	gird_dom.setHeight(heightClient-100);
	
});

function savecw(){
	var a8601=document.getElementById('a8601').value;
	var a0299=document.getElementById('a0299').value;
	if(!a8601){
		$h.alert('系统提示','日期不能为空',null,200);	
		return false;
	}
	if(!a0299){
		$h.alert('系统提示','分工情况不能为空',null,200);
		return false;
	}
	radow.doEvent("saveAll");
}

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
	border: 1px solid #99bbe8;
}

#toolBar8 {
	width: 630px !important;
}
</style>
