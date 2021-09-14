<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<head>
	<style>  
		.grid table{ color:rgb(169,169,169);}
	</style>
</head>
<script type="text/javascript">
Ext.onReady(function(){
	gridAutoHeight('list', 42, true, false);
	setColor();
}, this, {delay : 500});

function setColor(){
	var grid = Ext.getCmp("list");
	grid.getView().getRowClass = function(record, rowIndex, rowParams, store){
		if(record.data.avai_state_id == '0'){
			return 'grid';
		}else{
			return '';
		}
    };
}

function gridAutoHeight(gridId, topHeight, haveTopBar, havePageToolBar){
	var grid = Ext.getCmp(gridId);
	grid.setHeight(document.body.clientHeight - topHeight);
	var suttleHeight = document.body.clientHeight - topHeight;
	if(haveTopBar){
		suttleHeight = suttleHeight - 23;
	}
	if(havePageToolBar){
		var pagesize = Math.floor((suttleHeight - 27)/55);
		var pageingToolbar = grid.getBottomToolbar();
		pageingToolbar.pageSize = Number(pagesize);
		var s = grid.store;
		s.baseParams.limit = pagesize;
		if(s.lastOptions && s.lastOptions.params){
			s.lastOptions.params.limit = pagesize;
		}
		grid.store.reload();
	}
	window.onresize = function(){
		grid.setHeight(document.body.clientHeight - topHeight);
	}
}

function refresh(){
	getSNode();
}

function getSNode(){
	var node = window.parent.getNode();
	if(node == null)
		document.all.nodeId.value = '';
	else
		document.all.nodeId.value = node.id; 
	var value = document.all.nodeId.value;
	if(value == '' || value == 'S000000')
		document.all.gridT.innerHTML = '';
	else
		radow.doEvent('disPlayDsa', value); 
}

function checkConditionHrefGridEditDelete(value, params, record, rowIndex, colIndex, ds){
	if(value == null){
		return;
	}
	return '<a href="javascript:void(0);" onclick="radow.doEvent(\'publish\',\'' + record.get('interface_config_id') + '\');">发布</a>'
	      +'&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="radow.doEvent(\'cutout\',\'' + record.get('interface_config_id') + '\');">终止</a>';
}

</script>
<odin:hidden property="nodeId" value=""/>
 
<span style="position:absolute;top:10;left:8;z-index:1000;font-size:12px;font-weight: bolder;font-family: Arial;width:770px" id="gridT" ></span>
	
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td><div id="btnToolBarDiv" style="height:42px;background-color: D1DFF0"></div></td>
	</tr>
	<tr>
		<td><div id="gridDiv_list"></div></td>
	</tr>
</table>
<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
	<odin:textForToolBar text=""/>
	<odin:fill/>
	<odin:buttonForToolBar text="刷新" handler="refresh" icon="images/icon/refresh.png" isLast="true"/>
</odin:toolBar>
<odin:editgrid property="list" isFirstLoadData="false" url="/" applyTo="gridDiv_list" remoteSort="true" 
	sm="checkbox" autoFill="true">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="interface_config_id"/>
		<odin:gridDataCol name="interface_config_name"/>
		<odin:gridDataCol name="availability_state_id"/>
		<odin:gridDataCol name="publish_state_id"/>
		<odin:gridDataCol name="interface_config_create_user"/>
		<odin:gridDataCol name="interface_config_create_date"/>
		<odin:gridDataCol name="op" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn/>
		<odin:gridColumn header="接口方案编码" dataIndex="interface_config_id" hidden="true"/>
		<odin:gridColumn header="接口方案名称" dataIndex="interface_config_name" edited="false" width="40" align="center"/>
		<odin:gridColumn header="接口方案有效性" dataIndex="availability_state_id" edited="false" width="34" align="center"/>
		<odin:gridColumn header="接口方案状态" dataIndex="publish_state_id" edited="false" width="30" align="center"/>
		<odin:gridColumn header="接口方案创建人" dataIndex="interface_config_create_user" edited="false" width="30" align="center"/>
		<odin:gridColumn header="接口方案创建时间" dataIndex="interface_config_create_date" edited="false" width="30" align="center"/>	
		<odin:gridColumn header="操作" dataIndex="op" edited="false" editor="text" width="20" align="center" renderer="checkConditionHrefGridEditDelete" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid>