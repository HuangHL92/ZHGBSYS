function addOrderFld(){//���������ֶ�
	var grid_id3='contentList3';
	var grid3=Ext.getCmp(grid_id3);
	var store=grid3.getStore();//�������ݼ���
	var row = grid3.getSelectionModel().getSelections();
	var data=row[0];
	var dataIndex = store.indexOf(data);//��ǰѡ�е����� 
	if(row!=null&&row.length>0){
		var grid_id4='contentList4';
		var grid4=Ext.getCmp(grid_id4);
		var orderStore = grid4.getStore();
		var showcolname=data.get('showcolname');
		data.set('showcolname','����@  '+showcolname);
		orderStore.add(data);
		store.removeAt(dataIndex);
	}
}
function dbClickOrderFunc(grid,rowIndex,colIndex,event){//˫��������Ϣ��л������� 
    	var record = grid.store.getAt(rowIndex);//���˫���ĵ�ǰ�еļ�¼
    	var showcolname=record.get('showcolname');
    	var ordername=record.get('showcolname').substr(0,3).trim();
    	if('����@'==ordername){
    		showcolname='����@'+showcolname.substr(3,showcolname.length);
    	}
    	if('����@'==ordername){
    		showcolname='����@'+showcolname.substr(3,showcolname.length);
    	}
    	record.set('showcolname',showcolname);
    	//grid.store.removeAt(rowIndex);  
    	//grid.store.insert(rowIndex, record);  
}
function clearAllOrder(){//ȫ�����
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
	orderStore.removeAll();//������б������-����
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
function upmovefunc(){//�������� 
	var grid_id4='contentList4';
	upmove(grid_id4);
}
function dowmmoveofunc(){//��������
	var grid_id4='contentList4';
	 downmove(grid_id4);
}