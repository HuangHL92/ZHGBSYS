	  
	var selectedRow = null;
	var selectedRow2 = null;
	var canSave = false;
	var canDo = false;
	
	var deleteData = new Array(); 
	var num = 0;
	
	Ext.onReady(function(){
		loadBasicMessage(succfun,failfun);		
	});
	
	function succfun(response){
		var benefitListStore=Ext.getCmp("list1").store;
    	var rs=new Array(response.data.length);
  		for(i=0;i<response.data.length;i++){
    		rs[i] = new Ext.data.Record(response.data[i]);
    	}
		benefitListStore.removeAll();
  		benefitListStore.add(rs);	
	}
	
	function failfun(response){
		odin.showErrorMessage(response);
	}  
	  
    function doReset(){
    	odin.reset();
    }
    
    function doAdd1(response){
    	//odin.addGridRowData('list1',{aaa100:'',aaa101:'',aaa104:'1'});
    	var rs = window.showModalDialog(contextPath+"/pages/comm/codeparameter/AddParameter.jsp",response.data,"help:no;status:no;dialogWidth:17;dialogHeight:10");
    	if(rs){
			odin.reset();
		}
    }
    
    function doDelete1(){
    	
    	if(selectedRow == null){
			alert("请先选择要删除的行！");
			return;
		}
		
		var benefitListStore=Ext.getCmp("list1").store;
		var aaa100 = benefitListStore.data.itemAt(selectedRow).get('aaa100');
		deleteParameter(aaa100,deleteSucc,deleteFail);
    }
    
    function deleteSucc(){
    	alert("删除成功！");
  		odin.reset();
    }
    
    function deleteFail(){
    	odin.showErrorMessage(response);
    }
    
    function doReady(e){
    	var grid = e.grid;
		var row = e.row;
		e.cancel = true; 
		if(selectedRow != row){
			selectedRow = row;
			var rowData = grid.store.getAt(row);
			var aaa100 = rowData.get('aaa100');
			loadParameterMessage(aaa100,loadParameterSucc,loadParameterFail);
		}
	}
	
    function loadParameterSucc(response){
    	var benefitListStore=Ext.getCmp("list2").store;
    	var rs=new Array(response.data.length);
  		for(i=0;i<response.data.length;i++){
    		rs[i] = new Ext.data.Record(response.data[i]);
    	}
		benefitListStore.removeAll();
  		benefitListStore.add(rs);
  		selectedRow2 = null;
  		canDo = true;
    }
    
    function loadParameterFail(response){
    	odin.showErrorMessage(response);
    }
    
    
    //第二个grid的方法
    function doAdd2(){
    	if(!canDo){
    		alert("请先在“代码表”中选定一项！");
    		return;	
    	}
     	var benefitListStore=Ext.getCmp("list1").store;
		var aaa100 = benefitListStore.data.itemAt(selectedRow).get('aaa100');
    	odin.addGridRowData('list2',{aaa100:''+aaa100,aaa102:'',aaa103:'',aaa105:'',aae100:'1',aaa104:'1',aaz093:-1,flag:1});
   		canSave = true;
    }
    
    function doReady2(e){
		var row = e.row;
		var column = e.column;
		if(column == 1){
			e.cancel = true; 
		}else{
			if(selectedRow2 != row){
				selectedRow2 = row;
			}
		} 
    }
    
    function doEdit2(e){
    	var grid = e.grid;
    	var row = e.row;
		var column = e.column;
		var rowData = grid.store.getAt(row);
		
		var benefitListStore=Ext.getCmp("list2").store;
	
    	var flag = rowData.get('flag');
		if(flag == 0){
			benefitListStore.data.itemAt(row).set("flag",2);
		}   
		canSave = true; 
    }
   
    function doDelete2(){
    	
    	if(!canDo){
    		alert("请先在“代码表”中选定一项！");
    		return;	
    	}
   
    	if(selectedRow2 == null){
			alert("请先选择要删除的行！");
			return;
		}
		
		var benefitListStore=Ext.getCmp("list2").store;
		var listData=new Array();
	
		deleteData[num] = benefitListStore.getAt(selectedRow2).data; 
		num++;
	
		var j=0;   
 		for(var i=0;i<benefitListStore.data.length;i++){  
	  		if(i != selectedRow2){
	  			listData[j]=new Ext.data.Record({});
	  			listData[j].data.aaz093=benefitListStore.data.itemAt(i).get('aaz093');
		  		listData[j].data.aaa100=benefitListStore.data.itemAt(i).get('aaa100');
		  		listData[j].data.aaa102=benefitListStore.data.itemAt(i).get('aaa102');
		  		listData[j].data.aaa103=benefitListStore.data.itemAt(i).get('aaa103');
		  		listData[j].data.aaa105=benefitListStore.data.itemAt(i).get('aaa105');
		  		listData[j].data.aae100=benefitListStore.data.itemAt(i).get('aae100');
		  		listData[j].data.aaa104=benefitListStore.data.itemAt(i).get('aaa104');	
		  		listData[j].data.flag=benefitListStore.data.itemAt(i).get('flag');	
		  		j++;		
		  	}	
		}
		
		benefitListStore.removeAll();
	    benefitListStore.add(listData); 		
		
		canSave = true;
		selectedRow2 = null;
		
    }
 	
    function save(){
    	if(!canSave){
    		alert("没有进行过操作！不用保存！");
    		return;
    	}
    	
    	odin.getGridJsonData("list2");
        var gridJsonStr = Ext.util.JSON.encode(deleteData);
        
        document.all.deleteList.value = gridJsonStr;
        odin.submit(codeParameterForm,saveSucc,saveFail);  
        
    }
    
    function saveSucc(){
    	alert("保存成功！");
  		odin.reset();
    }
    
    function saveFail(response){
    	odin.showErrorMessage(response);
    }