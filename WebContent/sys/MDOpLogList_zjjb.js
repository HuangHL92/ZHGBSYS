	var SQL="select  a.opseno,d.opseno oriopseno,a.functionid,nvl(b.title,a.functiontitle) functiontitle,a.aae002,a.aac001,a.aab001,a.digest,a.prcol1,a.prcol2,a.prcol3,a.prcol4,a.prcol5,a.prcol6,a.prcol7,a.prcol8,nvl(c.username,a.aae011) aae011,to_char(a.aae036,'yyyy-mm-dd hh24:mi:ss') aae036,a.eae024,b.rbflag ";
	SQL=SQL+"from sbds_userlog a,smt_function b,smt_user c,opaudit d where a.functionid=b.functionid(+) and a.aaa027='"+aaa027+"' and a.aae011=c.loginname(+) and a.opseno=d.auopseno(+)";
	
	
	var fileNameSim = "操作日志导出"+ uuid;
	var sheetNameSim = "操作日志导出";
	var querySQLSim = "";
	var expQuerySQL = "";
	
	function simpleQuery(){
		var where=" and a.eae024='0'";
		//var querydate=document.all.querydate.value;
		//if(querydate!=""){
		//	where=where+" and a.aae036>=to_date('"+querydate+"','yyyy-mm-dd') and a.aae036<to_date('"+querydate+"','yyyy-mm-dd')+1";
		//}
		where=where+" and a.aae002="+opperiod;
		queryLog(where);
	}
	
	function simpleQueryByDate(){
		var where=" and a.eae024='0'";
		var querydate=document.all.querydate.value;
		if(querydate!=""){
			where=where+" and a.aae036>=to_date('"+querydate+"','yyyy-mm-dd') and a.aae036<to_date('"+querydate+"','yyyy-mm-dd')+1";
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
			querySQL=querySQL+" and a.functionid='"+functionid+"'";
		querySQL=querySQL+" order by a.functionid,a.aae036 desc";
		//alert(querySQL);
		expQuerySQL = "select t.opseno 业务流水号,t.functiontitle 业务名称,t.digest 摘要信息,t.aae011 经办人,t.aae036 经办日期 from (" + querySQL + ") t" ;
		odin.loadPageGridWithQueryParams("opLogList",{querySQL:querySQL,sqlType:"SQL"});
	}
	
	function doDbClick(grid,rowIndex,e){
		//alert(window.dialogArguments.top.SysParam.sysdate);
		var ds=grid.store.data;
		var opseno=ds.itemAt(rowIndex).get("opseno");
		var oriopseno=ds.itemAt(rowIndex).get("oriopseno");
		//var url="<%=request.getContextPath()%>/sys/OpLogAction.do?method=getOriOpLog&opseno="+opseno;
		//window.showModalDialog(url,window,"help:no;status:no;dialogWidth:500;dialogHeight:200");
		//location.href=url;
		showOriOpLog(oriopseno?oriopseno:opseno);
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
		var tabs=top.frames[1].tabs;
		var aid="orioplog";
		var atitle="界面还原";
		var tab=tabs.getItem(aid);
		if (tab){tabs.remove(tab);}
		top.frames[1].addTab(atitle,aid,src);
	}
	
	function opsenoToDetailUrl(opseno,params,record){
		//var url="<%=request.getContextPath()%>/sys/OpLogAction.do?method=getOriOpLog&opseno="+opseno;
		//return "<a href='"+url+"'>"+opseno+"</a>";
		return "<a href='javascript:showOriOpLog("+(record.data.oriopseno?record.data.oriopseno:opseno)+")'>"+opseno+"</a>";
	}
	
	function renderDigest(value, params, record, rowIndex, colIndex, ds){
    	return "<font color='#009933'><a style='text-decoration:none' href='javascript:void(0);' title='"+value+"'>"+value+"</a></font>";
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
			return "<img src=\""+contextPath+"/images/chexiao.jpg\">";
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
	
	
	
	function expPersonInfo(){
	    querySQLSim	= expQuerySQL;
		odin.showWindowWithSrc('simpleExpWin',contextPath+"/sys/excel/simpleExpExcelWindow.jsp");
	}
