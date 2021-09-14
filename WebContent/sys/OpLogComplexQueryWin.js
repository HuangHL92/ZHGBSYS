	function init(){
		document.all.opperiods.value=parent.opperiod;
		document.all.opperiode.value=parent.opperiod;
	}
	
	function complexQuery(){
		var opperiods=document.all.opperiods.value;
		var opperiode=document.all.opperiode.value;
		var querydates=document.all.querydates.value;
		var querydatee=document.all.querydatee.value;
		var digest=document.all.digest.value;
		var functiontitle=document.all.functiontitle.value;
		var aae011=document.all.aae011.value;
		var where="";
		if(opperiods){
			where=where+" and a.aae002>="+opperiods;
		}
		if(opperiode){
			where=where+" and a.aae002<="+opperiode;
		}
		if(querydates){
			where=where+" and a.aae036>=to_date('"+querydates+"','yyyy-mm-dd')";
		}
		if(querydatee){
			where=where+" and a.aae036<to_date('"+querydatee+"','yyyy-mm-dd')+1";
		}
		if(functiontitle){
			where=where+" and b.title like '%"+functiontitle+"%'";
		}
		if(digest){
			where=where+" and a.digest like '%"+digest+"%'";
		}
		if(aae011){
			where=where+" and a.aae011='"+aae011+"'";
		}
		if(!document.all.rbflag.checked){
			where=where+" and a.eae024='0'";
		}
		//alert(where);
		//alert(parent.iframe_complex_win.document.documentElement.innerHTML);
		parent.win_complex_win.hide();
		parent.queryLog(where);	
	}
	
	function doClose(){
		parent.win_complex_win.hide();
	}