	
	var SQL="@_PSItqiEKenNZrHvwts6L8jti7cqCTYqTp1pJs0597mu3oQ/UvtKlUklWhvagABUDasg7tA/le0X21Q/yT6J/4jYMQQjrlWpwXXfg6JXcVrMfFmLLMSEKTBlOTu/5OR4bkgY2SNVlp7Z0pkZxe8xjzTb6VeM5Q/wTUxazJD0xUWf7uUP4jcaIh4npkxGtGkr/XsMWWoJTfCQZPNtEcwuSWRVjA8eM5siyepHWS+UqTchS8KvSB5X/K2I6pZIkOdzDB4SfmAQjsy0aV9AUzshOIkz9FKpHUmm0VLPPBHoa5N3/3dxAWvrabr8gd/HQ4AyMCAcNH0AoL+2owt19i9kO0Q==_@";
	SQL=SQL+"@_5kGzdNdbr9DV2zeHkZ7yHTwiFOMPGAsRRUUOTijiOJwjjyeEg5HTjWN6Nc+iYwwvl5HkGDTZzhlLN9I4o+CVDCX4rCjJICVELpZQ2k9oXUTGCDz9QNyYj3QAOxlGtU1PiUjtln1/h3A=_@";
	
	function simpleQuery(){
		var where="@_5xF5m6bwEpggXKw6QPpjSZjMUEjvi3xc_@";
		var querydate=document.all.querydate.value;
		if(querydate!=""){
			where=where+"@_Y5VaCPyBx5kqKIZ+v54m+cgHZP+mn9VNqMLdfYvZDtE=_@"+querydate+"@_lyt3pTrxLRon9GFb8od7XOG3NN+lE0suKbQjFj9Cc2puj6Ad2/4zUQ==_@"+querydate+"@_lyt3pTrxLRonjKm8sDkpcBITk88J/HFe_@";
		}
		queryLog(where);
	}
	
	/*
	function complexQuery(){
		alert(document.all.complex_win.name);
		var querydates=document.all.complex_win.document.all.querydates.value;
		var querydatee=document.all.querydatee.value;
		var digest=document.all.digest.value;
		var where="";
		if(querydates!=""){
			where=where+" and a.aae036>=to_date('"+querydates+"','yyyy-mm-dd')";
		}
		if(querydatee!=""){
			where=where+" and a.aae036<to_date('"+querydatee+"','yyyy-mm-dd')+1";
		}
		if(digest!=""){
			where=where+" and a.digest like '%"+digest+"%'";
		}
		if(!document.all.rbflag.checked){
			where=where+" and a.eae024='0'";
		}
		//alert(where);
		//win.hide();
		queryLog(where);
		
	}*/
	
	function queryLog(where){
		var querySQL=SQL+where;
		if(functionid)
			querySQL=querySQL+"@_Oq/H7kAbuy0+w9x0XdMb4a6K9o7UvS8+_@"+functionid+"@_mMxQSO+LfFw=_@";
		var querySQL=querySQL+"@_0lLHxReFyQH2QAOq46bnX9fss2i7cjcN_@";
		//alert(querySQL);
		odin.loadPageGridWithQueryParams("opLogList",{querySQL:querySQL,sqlType:"SQL"});
	}
	
	function doDbClick(grid,rowIndex,e){
		//alert(window.dialogArguments.top.SysParam.sysdate);
		var ds=grid.store.data;
		var opseno=ds.itemAt(rowIndex).get("opseno");
		//var url="<%=request.getContextPath()%>/sys/OpLogAction.do?method=getOriOpLog&opseno="+opseno;
		//window.showModalDialog(url,window,"help:no;status:no;dialogWidth:500;dialogHeight:200");
		//location.href=url;
		showOriOpLog(opseno);
	}
	
	function doRollback(opseno){
		if(!confirm("是否确认回退？")) return;
		var url=contextPath+"/sys/OpLogAction.do?method=rollback";
		//alert(url);   
		odin.Ajax.request(url,{opseno:opseno},doRollbackSuccess,null);  
	}
	
	function doRollbackSuccess(rm){
		alert("回退成功");
		var grid=Ext.getCmp('opLogList');
		var record=grid.getSelectionModel().getSelections()[0];
		grid.store.remove(record);
	}
	
	function showOriOpLog(opseno){
		var src=contextPath+"/sys/OpLogAction.do?method=getOriOpLog&opseno="+opseno;
		var tabs=top.main.tabs;
		var aid="orioplog";
		var atitle="界面还原";
		var tab=tabs.getItem(aid);
		if (tab){tabs.remove(tab);}
		top.main.addTab(atitle,aid,src);
	}
	
	function opsenoToDetailUrl(opseno){
		//var url="<%=request.getContextPath()%>/sys/OpLogAction.do?method=getOriOpLog&opseno="+opseno;
		//return "<a href='"+url+"'>"+opseno+"</a>";
		return "<a href='javascript:showOriOpLog("+opseno+")'>"+opseno+"</a>";
	}
	
	function opsenoToRollbackUrl(opseno,params,record){
		var eae024=record.data.eae024;
		if(eae024=="0"){//未回退
			var rbflag=record.data.rbflag;
			if(rbflag=="1"){//允许回退
				return "<a href=\"javascript:doRollback('"+opseno+"');\">回退</a>";
			}else{
				return "";
			}
		}else{
			return "<img src=\""+contextPath+"/commform/img/chexiao.jpg\">";
		}
	}
	
	function opsenoToReprint(opseno){
		return "<a href='javascript:reprint("+opseno+")'>重新打印</a>";
	}
	
	function reprint(opseno){
		odin.Ajax.request(contextPath+"/sys/PrintAction.do?method=getPrintlog",
	  			{opseno:opseno},reprintSuccess);
	}
	
	function reprintSuccess(response){
		for(var i=0;i<response.data.length;i++){
			if(confirm("是否打印【"+response.data[i].title+"】？")){
				var preview=true;
				if(response.data[i].preview=="0"){
					preview=false;
				}
				odin.billPrint(response.data[i].repid,response.data[i].queryname,response.data[i].param,preview);				
			}
		}
	}
