//��Ϣ���б������ɻص��¼�
function setTableInfoSelected(){//����tab2 ��Ϣ��ѡ��
	var tableinfo=document.getElementById("tablesInfo").value;
	var gridid='content2';
	var grid=Ext.getCmp(gridid);
	var orderStore = grid.getStore();
    var rowCount = orderStore.getCount();
    var table_code='';
	if(tableinfo!=''){
		var arr=tableinfo.split(',');
		for(var i=0;i<rowCount;i++) {
	    	for(var j=0;j<arr.length;j++){
	    		if(orderStore.getAt(i).get("table_code").toLowerCase() == arr[j].toLowerCase()) {
		        	table_code=table_code+"'"+orderStore.getAt(i).get("table_code").toLowerCase()+"',";
		        	var record=orderStore.getAt(i);
					record.set('change',true);
					orderStore.removeAt(i);  
					orderStore.insert(i, record); 
					//grid.getSelectionModel().select(record);
		        }
	    	}
	    } 
	}
	if(table_code!=''){
		table_code=table_code.substring(0,table_code.length-1);
	    radow.doEvent("copyToTableInfo",table_code);
	}
}
//ѡ����Ϣ����������Ϣ��
function infoTableCheck(){//��ʾָ����
	radow.doEvent('loadFieldGridList');
}