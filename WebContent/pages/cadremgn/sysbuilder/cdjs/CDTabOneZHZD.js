//�б������ɻص��¼�
function setMergeChecked(){//�����Ѿ������������ֶ� Ĭ��ѡ��
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
            //ѡ��Ĭ����
           // Ext.getCmp(gridid).getSelectionModel().selectRow(i,true);
            return;
        }
    } 
}
//�Ѿ������������ֶ�  ��ѡ���¼�
function defineFieldcheck(row,col,col_id,grid_id){
	setGridListChecked(row,col,col_id,grid_id);
	var grid=Ext.getCmp(grid_id);
	var orderStore = grid.getStore();
	if(orderStore.getAt(row).get(col_id)==true){//������������
		document.getElementById('showname').value=orderStore.getAt(row).get('showname');
		document.getElementById('ctci').value=orderStore.getAt(row).get('ctci');
		//���ҳ��������Ϣ
		searchObj=new Object();//���ָ����
		cleanInfo();//��� ����
		document.getElementById("tablesInfo").value='';
		radow.doEvent("loadAllData",orderStore.getAt(row).get('ctci'));
	}else{//�����������
		document.getElementById('showname').value='';
		document.getElementById('ctci').value='';
		//�������
		Ext.getCmp('contentList4').getStore().removeAll();
		Ext.getCmp('contentList3').getStore().removeAll();
		//��� ����
		cleanInfo();
		Ext.getCmp('contentList6').getStore().removeAll();
		//���ָ����
		searchObj=new Object();
		Ext.getCmp('contentList5').getStore().removeAll();
		Ext.getCmp('searchList').getStore().removeAll();
		//���ѡ����Ϣ��
		//cancelSelect('content2','change');
		Ext.getCmp('content2').getStore().removeAll();
		Ext.getCmp('contentList2').getStore().removeAll();
		radow.doEvent("clearRowRecordSet");
	}
}
