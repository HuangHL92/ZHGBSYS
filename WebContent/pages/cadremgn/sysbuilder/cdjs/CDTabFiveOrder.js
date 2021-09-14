function addOrderFld(){//增加排序字段
	var grid_id3='contentList3';
	var grid3=Ext.getCmp(grid_id3);
	var store=grid3.getStore();//所有数据集合
	var row = grid3.getSelectionModel().getSelections();
	var data=row[0];
	var dataIndex = store.indexOf(data);//当前选中的行数 
	if(row!=null&&row.length>0){
		var grid_id4='contentList4';
		var grid4=Ext.getCmp(grid_id4);
		var orderStore = grid4.getStore();
		var showcolname=data.get('showcolname');
		data.set('showcolname','升序@  '+showcolname);
		orderStore.add(data);
		store.removeAt(dataIndex);
	}
}
function dbClickOrderFunc(grid,rowIndex,colIndex,event){//双击排序信息项，切换升序降序 
    	var record = grid.store.getAt(rowIndex);//获得双击的当前行的记录
    	var showcolname=record.get('showcolname');
    	var ordername=record.get('showcolname').substr(0,3).trim();
    	if('升序@'==ordername){
    		showcolname='降序@'+showcolname.substr(3,showcolname.length);
    	}
    	if('降序@'==ordername){
    		showcolname='升序@'+showcolname.substr(3,showcolname.length);
    	}
    	record.set('showcolname',showcolname);
    	//grid.store.removeAt(rowIndex);  
    	//grid.store.insert(rowIndex, record);  
}
function clearAllOrder(){//全部清除
	var grid_id4='contentList4';
	var grid4=Ext.getCmp(grid_id4);
	var orderStore = grid4.getStore();
	var total=orderStore.getCount();
	
	var grid_id3='contentList3'; 
	var grid3=Ext.getCmp(grid_id3);
	var store = grid3.getStore();
	for(var i=0;i<total;i++){
		var record=orderStore.getAt(i);
		var rownum=record.get("ordernum");
		rownum=parseInt(rownum)-1;
		var showcolname=record.get('showcolname');
		showcolname=showcolname.substr(3,showcolname.length).trim();
		record.set('showcolname',showcolname);
		store.insert(rownum,record);
	}
	orderStore.removeAll();//清空左列表的数据-排序
}
function removeRow(){
	var grid_id4='contentList4';
	var grid4=Ext.getCmp(grid_id4);
	var store = grid4.getStore();
	var selections = grid4.getSelectionModel().getSelections();
	var selectData = selections[0];
	var rownum=selectData.get("ordernum");
	var dataIndex = store.indexOf(selectData);
	store.removeAt(dataIndex);
	rownum=parseInt(rownum)-1;
	var showcolname=selectData.get('showcolname');
	showcolname=showcolname.substr(3,showcolname.length).trim();
	selectData.set('showcolname',showcolname);
	Ext.getCmp('contentList3').getStore().insert(parseInt(rownum),selectData);
}
function upmovefunc(){//排序上移 
	var grid_id4='contentList4';
	upmove(grid_id4);
}
function dowmmoveofunc(){//排序下移
	var grid_id4='contentList4';
	 downmove(grid_id4);
}