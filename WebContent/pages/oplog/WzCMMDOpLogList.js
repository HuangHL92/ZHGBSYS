var WzquerySQLSim = "";
Ext.onReady(function() {
	var dataCols = [];
	var showCols = [];
	if (parent.wzColsInfo) {
		showCols.push(new Ext.grid.RowNumberer({}));
		var colsInfo = parent.wzColsInfo;
		for (var i = 0; i < colsInfo.length; i++) {
			var c = colsInfo[i];
			dataCols.push({name:c.column_name.toLowerCase()});
			var showCol = {
				sortable : true,
				hidden : false,
				editable : null
			};
			showCol.dataIndex = c.column_name.toLowerCase();
			var tempWidth = parseInt(c.data_length)*8;
			showCol.width = tempWidth>300?300:tempWidth;
			showCol.header = c.comments==null?showCol.dataIndex:c.comments;
			if(showCol.dataIndex=='opseno'){
				showCol.renderer = function(value, params, record, rowIndex, colIndex, ds){
					return "<a href='javascript:rollback(\""+value+"\",\""+record.get("aaa103")+"\","+rowIndex+")'>回退</a>"
				};
			}
			if(c.data_type=='DATE'){
				showCol.renderer = function(value, params, record, rowIndex, colIndex, ds){
					if(value==null){
						value = "";
					}
					return value.substr(0,10);
				};
			}
			showCols.push(showCol);
		}
	}
	console.log(colsInfo);
	var reader = new Ext.data.JsonReader({
				root : 'data',
				totalProperty : 'totalCount',
				id : 'id'
			}, dataCols);
	var colModel = new Ext.grid.ColumnModel(showCols);
	var ds = new Ext.data.Store({
				reader : reader,
				baseParams : {
					cueGridId : 'opLogList'
				},
				url : contextPath+'/common/pageQueryAction.do?method=query',
				remoteSort : false
			});
	var grid_opLogList = new Ext.grid.GridPanel({
				store : ds,
				cm : colModel,
				loadMask : true,
				id : 'div_2',
				viewConfig : {
					forceFit : false,
					autoFill : false
				},
				monitorResize : true,
				doLayout : function() {
					var el = Ext.get('forView_div_2').dom;
					if (Ext.isIE) {
						this.setWidth(0)
					};
					var width = el.offsetWidth;
					while (width <= 0) {
						if (el.parentElement) {
							el = el.parentElement;
							width = el.offsetWidth - 2
						} else {
							width = 787
						}
					};
					this.setWidth(width);
					Ext.grid.EditorGridPanel.prototype.doLayout
							.call(this);
				},
				height : 490,
				title : '操作日志',
				bbar : new Ext.PagingToolbar({
							pageSize : 50,
							store : ds,
							displayInfo : true
						}),
				tbar : null,
				collapsible : false,
				collapsed : false,
				renderTo : gridDiv_div_2
			});
	Ext.getCmp('div_2').on('contextmenu', function(e){odin.grid.menu.gridContextmenu(e,this,'true')});
	grid_opLogList.on("rowdblclick",oplogGridDbClick)		
	var querySQL = 	parent.wzOpSql;	
	WzquerySQLSim=parent.wzOpSql;
	//window.setTimeout(odin.loadPageGridWithQueryParams("div_1",{querySQL:querySQL,sqlType:"SQL"}),5000);
	//odin.loadPageGridWithQueryParams("div_1",{querySQL:querySQL,sqlType:"SQL"});
});
/**
 * 行双击事件
 * @param {} grid
 * @param {} row
 * @param {} e
 */
function oplogGridDbClick(grid,row,e){
	var opseno= grid.store.getAt(row).get('opseno');
	radow.cm.doGridClick("source1click",opseno,row);
}
/**
 * 链接方式样例，即可作为流水号，点击打开某个页面
 * @param {} id
 */
function openWin(id,aaa103,rowIndex){
	alert(id+"——"+aaa103);
	if(typeof radow!='undefined' && radow.cm){
		radow.cm.doGridClick("rollconfirm",id,rowIndex);
	}
}


function rollback(id,aaa103,rowIndex){

	if(typeof radow!='undefined' && radow.cm){
		odin.confirm('确认要回退吗?',function(btn){if(btn=='ok') {
			radow.cm.doGridClick("rollconfirm",id,rowIndex);
			}else{
				
			}},'');
		
	}
}