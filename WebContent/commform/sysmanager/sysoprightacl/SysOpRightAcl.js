	
	var rowNum= null;
	var canSave = false;
	
	function doReady(e){
		e.cancel = true;
	}
	
	function doClick(grid,rowIndex,e){
		canSave = true;
		rowNum = rowIndex;  
		
		var benefitListStore = Ext.getCmp("queryList").store;
		var username = benefitListStore.data.itemAt(rowNum).get('username');
		var operatorname = benefitListStore.data.itemAt(rowNum).get('operatorname');
		
		document.all.username.value = username;
		document.all.operatorname.value = operatorname;
		
		var params = {};
		params.username = username;
		
		var url = contextPath + "/sys/sysoprightacl/SysOpRightAclAction.do?method=loadOwnRights";
		odin.Ajax.request(url, {_params:odin.encode(params)}, loadOwnRightsSucc, null, false, false);
		
	}
	
	function loadOwnRightsSucc(response){
		var arrayStore=Ext.getCmp("list").store;
    	var listData=new Array();
  		for(i=0;i<response.data.length;i++){
  			listData[i]=new Ext.data.Record({});
  			if(response.data[i].aaaa == "1"){
  				listData[i].data.check = true;
  			}else{
  				listData[i].data.check = false;
  			}
		  	listData[i].data.oprightcode=response.data[i].oprightcode;
		  	listData[i].data.oprightdesc=response.data[i].oprightdesc;
  		}  	
		arrayStore.removeAll();
  		arrayStore.add(listData);	
	}
	
	function doSave(){
		if(!canSave){
			alert("请先选择用户！");
			return;
		}	
		odin.getGridJsonData("list");
		odin.submit(sysOpRightAclForm,doSuccess,null);
	}
	
	function doSuccess(){
		alert("保存成功！");	
		//odin.reset();	
	}
	
	function doQuery(){
		canSave = false;
		var user = document.all.user.value;
		var usern = document.all.usern.value;
		odin.loadPageGridWithQueryParams("queryList",{username:user,name:usern});
		var arrayStore=Ext.getCmp("list").store;
		arrayStore.removeAll();
	}	
	
	