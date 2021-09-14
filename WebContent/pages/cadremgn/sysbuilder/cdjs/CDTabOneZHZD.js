//列表加载完成回调事件
function setMergeChecked(){//设置已经过定义的组合字段 默认选中
	var ctci=document.getElementById("ctci").value;
	var gridid='contentList';
	var grid=Ext.getCmp(gridid);
	var orderStore = grid.getStore();
    var rowCount = orderStore.getCount();

    for(var i=0;i<rowCount;i++) {
        if(orderStore.getAt(i).get("ctci") == ctci) {
        	var record=orderStore.getAt(i);
			record.set('change',true);
			orderStore.removeAt(i);  
			orderStore.insert(i, record); 
			//grid.getSelectionModel().select(record);
            //选中默认行
           // Ext.getCmp(gridid).getSelectionModel().selectRow(i,true);
            return;
        }
    } 
}
//已经过定义的组合字段  复选框事件
function defineFieldcheck(row,col,col_id,grid_id){
	setGridListChecked(row,col,col_id,grid_id);
	var grid=Ext.getCmp(grid_id);
	var orderStore = grid.getStore();
	if(orderStore.getAt(row).get(col_id)==true){//加载所有数据
		document.getElementById('showname').value=orderStore.getAt(row).get('showname');
		document.getElementById('ctci').value=orderStore.getAt(row).get('ctci');
		//清空页面隐藏信息
		searchObj=new Object();//结果指标项
		cleanInfo();//清空 条件
		document.getElementById("tablesInfo").value='';
		radow.doEvent("loadAllData",orderStore.getAt(row).get('ctci'));
	}else{//清空所有数据
		document.getElementById('showname').value='';
		document.getElementById('ctci').value='';
		//清空排序
		Ext.getCmp('contentList4').getStore().removeAll();
		Ext.getCmp('contentList3').getStore().removeAll();
		//清空 条件
		cleanInfo();
		Ext.getCmp('contentList6').getStore().removeAll();
		//结果指标项
		searchObj=new Object();
		Ext.getCmp('contentList5').getStore().removeAll();
		Ext.getCmp('searchList').getStore().removeAll();
		//清空选择信息集
		//cancelSelect('content2','change');
		Ext.getCmp('content2').getStore().removeAll();
		Ext.getCmp('contentList2').getStore().removeAll();
		radow.doEvent("clearRowRecordSet");
	}
}
