//��ʼ��tab3ҳ����Ϣ
var searchObj=new Object();//�洢ѡ����ֶ���Ϣ
function initHiddenShCL(){//
	var setfld=document.getElementById("setfld").value;
	var setfldarr='';
	if(setfld!=null&&setfld!=''){
		setfldarr=setfld.split("$");
	}
	var grid_id3='searchList';
	var grid3=Ext.getCmp(grid_id3);
	var store=grid3.getStore();//�������ݼ���
	var num=store.getCount();
	for(var j=0;j<num;j++){
		var arrfld=setfldarr[j].split("@");
		var arr=[];
		for(var i=0;i<8;i++){
			arr.push(arrfld[i]);
		}
		var record=store.getAt(j);
		var id=record.get('table_code')+"@"+record.get('col_code');
		searchObj["'"+id+"'"]=arr;
	}
}
function cleanFldInfo(){//��ղ�ѯ���ָ������Ϣ
	searchObj=new Object();
	var grid4=Ext.getCmp('searchList');
	var orderStore = grid4.getStore();
	orderStore.removeAll();
}
function addIndexTermFld(){//���
	var grid_id3='contentList5';
	var grid3=Ext.getCmp(grid_id3);
	var store=grid3.getStore();//�������ݼ���
	var row = grid3.getSelectionModel().getSelections();
	var data=row[0];
	var dataIndex = store.indexOf(data);//��ǰѡ�е����� 
	if(row!=null&&row.length>0){
		var grid_id4='searchList';
		var grid4=Ext.getCmp(grid_id4);
		var orderStore = grid4.getStore();
		orderStore.add(data);
		
		var arr=[];
		for(var i=0;i<8;i++){
			arr.push('');
		}
		var record=store.getAt(dataIndex);
		var id=record.get('table_code')+"@"+record.get('col_code');
		searchObj["'"+id+"'"]=arr;
		
		store.removeAt(dataIndex);
	}
}
function searchAddAll(){//ȫ�����
	var grid_id4='contentList5';
	var grid4=Ext.getCmp(grid_id4);
	var orderStore = grid4.getStore();
	var total=orderStore.getCount();
	
	var grid_id3='searchList'; 
	var grid3=Ext.getCmp(grid_id3);
	var store = grid3.getStore();
	for(var i=0;i<total;i++){
		var record=orderStore.getAt(i);
		store.add(record);
		
		var arr=[];
		for(var j=0;j<8;j++){
			arr.push('');
		}
		var id=record.get('table_code')+"@"+record.get('col_code');
		searchObj["'"+id+"'"]=arr;
		
		//store.insert(i,record);
		
	}
	grid3.getView().refresh(); 
	orderStore.removeAll();//������б������-����
}
function indexTermRemove(){//ɾ��
	var grid_id4='searchList';
	var grid4=Ext.getCmp(grid_id4);
	var store = grid4.getStore();
	var selections = grid4.getSelectionModel().getSelections();
	if(selections[0]){
		var selectData = selections[0];
		var dataIndex = store.indexOf(selectData);
		var id=selectData.get('table_code')+"@"+selectData.get('col_code');
		searchObj["'"+id+"'"]=undefined;
		store.removeAt(dataIndex);
	}
}
function indexTermUpMove(){//��ѯ������� 
	var grid_id4='searchList';
	upmove(grid_id4);
}
function indexTermDownMove(){//��ѯ�������
	var grid_id4='searchList';
	 downmove(grid_id4);
}
function searchclickfunc(grid,rowIndex,e){//��ѯ�ṹָ���� onclick
	var records = grid.getSelectionModel().getSelections();
	var record = records[0];
	var table_code=record.get("table_code");
	var col_code=record.get("col_code");//
	var id=table_code+"@"+col_code;
	var arr=searchObj["'"+id+"'"];
	var strarr=arr[0]+"@"+arr[1]+"@"+arr[2]+"@"+arr[3]+"@"
	+arr[4]+"@"+arr[5]+"@"+arr[6]+"@"+arr[7]+"@";
	
	var record = grid.store.getAt(rowIndex);//���˫���ĵ�ǰ�еļ�¼
	var col_name1=record.get("showcolname");//ָ������
	var col_name=record.get("col_name");//ָ������
	
	var col_data_type=record.get("col_data_type");//����������
	var col_data_type_should=record.get("col_data_type_should");//ָ���������2
	var code_type=record.get("code_type");//ָ���������
	if(col_data_type==null||col_data_type=="null"){
		col_data_type="";
	}else{
		col_data_type=col_data_type.toLowerCase();
	}
	if(code_type!=null){
		code_type=code_type.trim();
	}
	if(col_data_type_should!=null){
		col_data_type_should=col_data_type_should.trim().toLowerCase();
	}
	if(col_data_type_should=='date'||col_data_type=='t'){//date Count �����¼�� Max   �������ֵ Min   ������Сֵ
		radow.doEvent('select14Method','t'+"@"+strarr);
	}else if(col_data_type_should=='number'||col_data_type=='n'){//number all
		radow.doEvent('select14Method','n'+"@"+strarr);
	}else if(code_type!=null&&code_type!=''){
		radow.doEvent('select14Method','s'+"@"+strarr);
	}else{//varchar or code_type: Count �����¼�� 
		radow.doEvent('select14Method','c'+"@"+strarr);
	}
	
}
function check2Func(obj){
	//var check2=document.getElementById("check2").value;
	if(obj.checked==true){//ѡ��
		//�ɱ༭
		radow.doEvent("setDisabled",'true');
	}else{
		//���ɱ༭
		radow.doEvent("setDisabled",'false');
	}
}
function onmouseoutFunc(obj,index){
	if(obj.value==null||obj.value.trim()==''){
		outMOArr(index);
	}
}
function outMOArr(index){
	var obj=document.getElementById("check2");
	if(obj.checked==false){
		var records = Ext.getCmp('searchList').getSelectionModel().getSelections();
		if(records[0]){
			var record=records[0];
			var id=record.get('table_code')+"@"+record.get('col_code');
			var arr=searchObj["'"+id+"'"];
			arr.splice(index,1,'');  
		}
	}
}
function grpFldAttributeFunc1(objs,num){
	modifyObjectArr(objs,0);
}
function grpFldAttributeFunc2(objs,num){
	modifyObjectArr(objs,1);
}
function grpFldAttributeFunc3(objs,num){
	modifyObjectArr(objs,2);
}
function grpFldAttributeFunc4(objs,num){
	modifyObjectArr(objs,3);
}
function grpFldAttributeFunc5(objs,num){
	modifyObjectArr(objs,4);
}
function grpFldAttributeFunc6(objs,num){
	modifyObjectArr(objs,5);
}
function modifyObjectArr(objs,index){
	var obj=document.getElementById("check2");
	if(obj.checked==false){
		var records = Ext.getCmp('searchList').getSelectionModel().getSelections();
		if(records[0]){
			var record=records[0];
			var id=record.get('table_code')+"@"+record.get('col_code');
			var arr=searchObj["'"+id+"'"];
			arr.splice(index,1,objs.data.key);  
		}
	}
}
function check4Func(objs){
	var obj=document.getElementById("check2");
	if(obj.checked==false){
		var records = Ext.getCmp('searchList').getSelectionModel().getSelections();
		if(records[0]){
			var record=records[0];
			var id=record.get('table_code')+"@"+record.get('col_code');
			var arr=searchObj["'"+id+"'"];
			arr.splice(6,1,objs.checked);  
		}
	}
}
function check3Func(objs){
	var obj=document.getElementById("check2");
	if(obj.checked==false){
		var records = Ext.getCmp('searchList').getSelectionModel().getSelections();
		if(records[0]){
			var record=records[0];
			var id=record.get('table_code')+"@"+record.get('col_code');
			var arr=searchObj["'"+id+"'"];
			arr.splice(7,1,objs.checked);  
		}
	}
}