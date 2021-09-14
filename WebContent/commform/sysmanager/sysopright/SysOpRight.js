	
	var rowNum = null;
	var selectAdd = false;
	
	Ext.onReady(function(){   
		
		document.getElementById("aaa").style.display="none";
		document.getElementById("bbb").style.display="none"; 
		
		var params = {};
		var url = contextPath + "/sys/sysopright/SysOpRightAction.do?method=loadAllRights";
		odin.Ajax.request(url, params, loadAllRightsSucc, null, false, false);

	
	});
	
	function loadAllRightsSucc(response){
		var benefitListStore=Ext.getCmp("list").store;
    	var rs=new Array(response.data.length);
  		for(i=0;i<response.data.length;i++){
    		rs[i] = new Ext.data.Record(response.data[i]);
    	}
		benefitListStore.removeAll();
  		benefitListStore.add(rs);	
	}
	
	function doReady(e){
		rowNum = e.row;
		e.cancel = true;
	}
	
	function doModify(){
		document.getElementById("aaa").style.display="none";
		document.getElementById("bbb").style.display=""; 
		
		var benefitListStore = Ext.getCmp("list").store;
    	document.all.oprightcode.value = benefitListStore.data.itemAt(rowNum).get('oprightcode');
    	document.all.oprightdesc.value = benefitListStore.data.itemAt(rowNum).get('oprightdesc');
	
		selectAdd = false;	
	}
	
	function doDelete(){
		var benefitListStore = Ext.getCmp("list").store;
		
		var dto={};
		dto.oprightcode = benefitListStore.data.itemAt(rowNum).get('oprightcode');
		dto.oprightdesc = benefitListStore.data.itemAt(rowNum).get('oprightdesc');
		
		var params = {};
		params.dto = dto;
		
		var oprightcode = dto.oprightcode;
		if(!confirm("确定要删除编号为“"+oprightcode+"”的权限？")){
			return;
		}
		
		
		var url = contextPath + "/sys/sysopright/SysOpRightAction.do?method=deleteRight";
		odin.Ajax.request(url, {_params:odin.encode(params)}, deleteRightSucc, null, false, false);
	}
	
	function deleteRightSucc(){
		alert("删除成功！");
		odin.reset();
	}
	
	function doAdd(){
		document.getElementById("aaa").style.display="";
		document.getElementById("bbb").style.display=""; 
		
		document.all.oprightcode.value = "";
    	document.all.oprightdesc.value = "";
    	
    	selectAdd = true;
	}
	
	function doSave(){
		var oprightcode = document.all.oprightcode.value;
    	var oprightdesc = document.all.oprightdesc.value;
		
		if(oprightcode==null||oprightcode==""){
			alert("“操作权限编号”不能为空！");
			return;
		}
		if(oprightdesc==null||oprightdesc==""){
			alert("“操作权限描述”不能为空！");
			return;
		}
		
		//新增情况
		if(selectAdd){
			var benefitListStore=Ext.getCmp("list").store;
  			for(i=0;i<benefitListStore.data.length;i++){ 
    			var temp = benefitListStore.data.itemAt(i).get('oprightcode');
    			if(temp == oprightcode ){
    				alert("新增的“操作权限编号”已经存在！");
    				return;
    			}
    		}
		}
		
		var dto={};
		dto.oprightcode = document.all.oprightcode.value;
		dto.oprightdesc = document.all.oprightdesc.value;
		
		var params = {};
		params.dto = dto;
		
		var url = contextPath + "/sys/sysopright/SysOpRightAction.do?method=saveOrUpdateRight";
		odin.Ajax.request(url, {_params:odin.encode(params)}, saveOrUpdateSucc, null, false, false);
		
	}
	
	function saveOrUpdateSucc(response){
		alert("保存成功！");
		odin.reset();	
	}
	
