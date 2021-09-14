	function doSave(){
		//odin.submit(document.employeeForm);
		//document.employeeForm.submit();
		odin.getGridJsonData("ac02");
		odin.submit(document.employeeForm,doSuccess,null);     
	}
	
	function getSource(){
		//alert(top.main.tabs);
		var tabs=top.main.tabs;
		var aid="oplog";
		var atitle="操作日志中心";
		var src=contextPath+"/sys/MDOpLogListAction.do?functionid="+MDParam.functionid;		
		var tab=tabs.getItem(aid);
     	if (tab){
     		//alert(tab.html);
        	tabs.remove(tab);
      	}
      	top.main.addTab(atitle,aid,src);
      
		//var url="<%=request.getContextPath()%>/sys/MDOpLogListAction.do?functionid="+MDParam.functionid;
		//window.showModalDialog(url,window,"help:no;status:no;dialogWidth:59;dialogHeight:38");
		//alert(MDParam.prsource);     
	}
	
	function doSuccess(rm){
  		//alert(rm.data.aac001);
  		alert("保存成功,流水号："+rm.data);
  		print(rm.data);
  		//odin.formClear(document.employeeForm);
  		odin.reset();
  	}
  	
  	function doFailure(rm){
  		//alert(rm.mainMessage);
  		//alert(rm.detailMessage);
  	}
  	
  	function getEmployeeInfo(){
  		var aac001=document.all.aac001.value;
  		odin.formClear(document.employeeForm);
  		Ext.getCmp("ac02").store.removeAll();
  		document.all.aac001.value=aac001;
  		if(aac001!=""){
	  		odin.Ajax.request(contextPath+"/pages/demo/employeeModifyAction.do?method=getEmployeeInfo",
	  			{aac001:aac001,aac003:"张三四"},getEmployeeInfoSuccess,null);
	  	}
  	}
  	
  	function getEmployeeInfoSuccess(response){
  		debugger;
  		//alert(odin.Ajax.formatDateTime(response.data.aac006));
  		document.all.aac002.value=response.data.aac002;
  		document.all.aac003.value=response.data.aac003;
  		odin.setSelectValue("aac004",response.data.aac004);
  		document.all.aac006.value=odin.Ajax.formatDate(response.data.aac006);
  		
  		var ac02Store=Ext.getCmp("ac02").store;
  		var ac02Data=new Array(response.data.ac02Set.length);
  		for(var i=0;i<response.data.ac02Set.length;i++){
  			ac02Data[i]=new Ext.data.Record({});
  			ac02Data[i].data.check=true;
  			ac02Data[i].data.aae140=response.data.ac02Set[i].aae140;
  			ac02Data[i].data.eac018=response.data.ac02Set[i].eac018;
  		}  		
  		//ac02Data[0]=new Ext.data.Record(response.data.ac02Set[0]);
  		//ac02Data[1]=new Ext.data.Record(response.data.ac02Set[1]);
  		ac02Store.removeAll();
  		ac02Store.add(ac02Data);
  	}
  	
  	function getEmployeeInfoFailure(response){
  		var aac001="asdf";
  	}
  	
  	function print(aae074){
  		if(confirm("是否打印单据？")){
  			odin.billPrint("1249270000","","HDYWLSH 1593528",true);
  		}
  	}
  	
  	function ajaxtest(){
  		/*var o={aac001:"123434",aac003:"张三四"};
  		var param="";
  		for(var key in o){
  			var ov=o[key];
  			param=param+"&"+key+"="+ov;
  		}
  		alert(param);		
  		return;*/
  		
	  	var request = false;
		try {
		  request = new XMLHttpRequest();
		} catch (trymicrosoft) {
		  try {
		    request = new ActiveXObject("Msxml2.XMLHTTP");
		  } catch (othermicrosoft) {
		    try {
		      request = new ActiveXObject("Microsoft.XMLHTTP");
		    } catch (failed) {
		      request = false;
		    }
		  }
		}
		if (!request)
		  alert("Error initializing XMLHttpRequest!");
		  
		var url=contextPath+"/pages/demo/employeeModifyAction.do?method=getEmployeeInfo";
		request.open("POST", url, true);
		request.setRequestHeader("Content-type","application/x-www-form-urlencoded; charset=GBK");
		request.setRequestHeader("Request-Type","Ajax");
		var data="aac001=124321&aac003="+encodeURIComponent(encodeURIComponent("张三四"));
		request.send("aac001=314123&aac003=张三四asd");
		//request.send(data);
  	}