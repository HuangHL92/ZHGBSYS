<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<html>
<head>
<style type="text/css">
</style>
</head>
<body style="margin:0;padding:0">
<odin:toolBar property="btnToolBar" applyTo="toolDiv">
	<odin:fill />
	<odin:buttonForToolBar id="btnAdd" text="新增" icon="images/add.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<!-- 
<odin:panel contentEl="gridDiv" property="ManagePanel" topBarId="btnToolBar" /> -->
<div id="gridDiv">	
<div id="toolDiv"></div>
<odin:editgrid property="list" title="接口用户" isFirstLoadData="false" url="/" height="380" bbarId="pageToolBar" remoteSort="true" pageSize="20"
autoFill="true">
<odin:gridJsonDataModel>
		<odin:gridDataCol name="user_id" />
		<odin:gridDataCol name="user_name"/>
		<odin:gridDataCol name="password"/>
		<odin:gridDataCol name="real_name"/>
		<odin:gridDataCol name="user_id" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn dataIndex="user_id" hidden="true" header="接口用户编码"/>
		<odin:gridColumn dataIndex="user_name" width="110" header="用户名" align="center"/>
		<odin:gridColumn dataIndex="password" hidden="true" header="接口方案名称" align="center"/>
		<odin:gridColumn dataIndex="real_name" width="110" header="姓名" align="center"/>
		<odin:gridColumn dataIndex="user_id" width="130" header="操作列" renderer="editorRender" align="center" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid>
<odin:window src="" modal="true" id="NewInterfaceUser" width="350" height="180" title="新建用户" closable="true" maximizable="false"></odin:window>
<odin:window src="" modal="true" id="EditInterfaceUser" width="350" height="180" title="修改用户" closable="true" maximizable="false"></odin:window>
</div>
<script type="text/javascript">

	function editorRender(value, params, rs, rowIndex, colIndex, ds){
		return "<a href=\"javascript:edit('"+value+"')\">修改</a> | <a href=\"javascript:del('"+value+"')\">删除</a> ";
	}
	
	function edit(id){
		radow.doEvent('edit',id);
	}

	function del(id){
		radow.doEvent('del',id);
	}
	
	function refresh() {
		radow.doEvent('list.dogridquery');
	}
	
	function exportAll(){
	    odin.grid.menu.expExcelFromGrid('list', null, null,null, false);
    }
	Ext.onReady(function() {
		//页面调整
		Ext.getCmp('list').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_list'))[0]-4);
		Ext.getCmp('list').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_list'))[1]-2); 
		document.getElementById("gridDiv").style.width = Ext.getCmp('list').getWidth() + "px";
		document.getElementById("toolDiv").style.width = Ext.getCmp('list').getWidth()-1 + "px";
	});
	function objTop(obj){
	    var tt = obj.offsetTop;
	    var ll = obj.offsetLeft;
	    while(true){
	    	if(obj.offsetParent){
	    		obj = obj.offsetParent;
	    		tt+=obj.offsetTop;
	    		ll+=obj.offsetLeft;
	    	}else{
	    		return [tt,ll];
	    	}
		}
	    return tt;  
	}
</script>
</body>	
</html>