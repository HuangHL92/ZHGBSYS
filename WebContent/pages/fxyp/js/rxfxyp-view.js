var doAddPersonView = (function(){//增加人员
	return {
		addPerson:function(plist,rowIndex){//按姓名查询 插入人员
			if(!rowIndex){
				rowIndex = 0;
			}
			var trCount =  $("#coordTable2 tr").length;
			
			//拟免人员
   			var NianMianZhiWu = {}
   			$.each(plist,function (i, item){
   				var fxyp07 = item["fxyp07"]
   		  		if(fxyp07=='-1'){
   		  			if(NianMianZhiWu[item["a0000"]]){
   		  				NianMianZhiWu[item["a0000"]].push(item["fxyp02"]);
   		  			}else{
   		  				NianMianZhiWu[item["a0000"]]=[item["fxyp02"]];
   		  			}
   				}
   			});
   			
			
       		if(rowIndex>0){
       			var fxyp00Prev = '';
       			var insertTR = $("#coordTable2 tr:nth-child("+(rowIndex+1)+")");
       			
       			
				$.each(plist,function (i, item){
					var countf = item["countf"];
					var fxyp00Cur = item["fxyp00"];
					var isHeBing = false;
					if(fxyp00Cur!=fxyp00Prev){
						fxyp00Prev = fxyp00Cur
						if(countf>0){
							isHeBing = true;
						}
					}
					
					var $tr = createRowView(rowIndex+i,item["tp0100"],item,isHeBing,countf,i,trCount,NianMianZhiWu[item["a0000"]]);
					$tr.insertBefore(insertTR);
					
          		});
      		}else if(rowIndex==0){//追加
      			var fxyp00Prev = '';
      			
      			
      			
				$.each(plist,function (i, item){
					var countf = item["countf"];
					var fxyp00Cur = item["fxyp00"];
					var isHeBing = false;
					if(fxyp00Cur!=fxyp00Prev){
						fxyp00Prev = fxyp00Cur
						if(countf>0){
							isHeBing = true;
						}
					}
					
					
					if(item["fxyp07"]=='1'){//拟免拟任合并的  拟免就不显示了
						var $tr = createRowView(rowIndex,item["tp0100"],item,isHeBing,countf,i,trCount,NianMianZhiWu[item["a0000"]]||false);
	          			$("#coordTable2").append($tr);
	          			delete NianMianZhiWu[item["a0000"]];
					}else if(NianMianZhiWu[item["a0000"]]){
						var $tr = createRowView(rowIndex,item["tp0100"],item,isHeBing,countf,i,trCount,NianMianZhiWu[item["a0000"]]||false);
	          			$("#coordTable2").append($tr);
					}
					
          		});
      		}
			
			complateView();
		}
	}
})();


//创建行
//岗位下有多人  岗位需要行合并 isHeBing 及 需要合并几行countf
var createRowView = function (rowIndex,guid,rowData,isHeBing,countf,rowCount,trCount,niMianZhiWu) {//splice(index, 0, val);
	var borderTop = '';
	if(rowCount>0&&(countf==0||isHeBing)){//首次加载
		borderTop = 'borderTop';
	}
	if(rowIndex>1&&(countf==0||isHeBing)){//插入
		borderTop = 'borderTop';
	}
	if(rowIndex==0&&trCount>1&&(countf==0||isHeBing)){//追加
		borderTop = 'borderTop';
	}
	if(!guid){
		guid = GUID();
	}
	var tr;
	var rowd;
	
	tr = $('<tr class="data">'+
			'<td class="rownum default_color"></td>'+//查看
			//'<td class="TNR"></td>'+//序号
			(countf==0?'<td gw="true" class="TNR"></td>':(isHeBing?'<td gw="true" rowSpan="'+countf+'" class="TNR"></td>':''))+
			(countf==0?'<td class="align-left '+borderTop+'">&nbsp;</td>':(isHeBing?'<td rowSpan="'+countf+'" class="align-left  '+borderTop+'">&nbsp;</td>':''))+//岗位名称
			'<td class="TNR  '+borderTop+'"></td>'+//序号
			'<td class=" '+borderTop+'">&nbsp;</td>'+//姓名
			'<td class="TNR '+borderTop+'">&nbsp;</td>'+//出生年月
			'<td class="TNR '+borderTop+'">&nbsp;</td>'+//任现职时间
			'<td class="TNR '+borderTop+'">&nbsp;</td>'+//任同级时间
			'<td class=" '+borderTop+'">&nbsp;</td>'+//学历职称
			'<td class="align-left '+borderTop+'">&nbsp;</td>'+//任现职务
			'<td class="align-left '+borderTop+'">&nbsp;</td>'+
			'</tr>');
	
	if(rowData){
		var tsIndex = 0;
		var tds = $("td:nth-child(n+3)", tr);
	  	if(isHeBing||countf==0){
	  		var fxyp07 = rowData["fxyp07"]
	  		if(fxyp07=='1'){
	      		 rm = "任"
	      	}else if(fxyp07=='-1'){
	      		 rm = "免"
			}
	  		var v = rowData["fxyp02"];
	  		SetTDtext(tds[tsIndex++],(v==""||v==null||v=="null")?"":(rm+v));
	  		tsIndex++
	  	}
	  	
	  	SetTDtext(tds[tsIndex++],rowData["tp0101"]);
	  	SetTDtext(tds[tsIndex++],rowData["tp0102"]);
	  	SetTDtext(tds[tsIndex++],rowData["tp0103"]);
	  	SetTDtext(tds[tsIndex++],rowData["tp0104"]);
	  	SetTDtext(tds[tsIndex++],rowData["tp0105"]);
	  	SetTDtext(tds[tsIndex++],rowData["tp0106"]);
	  	if(niMianZhiWu){
	  		var nmzw = '免';
	  		$.each(niMianZhiWu,function (i, item){
	  			nmzw +=item+"，";
	  		});
	  		SetTDtext(tds[tsIndex++],nmzw.substr(0,nmzw.length-1));
	  	}
	  	rowd=rowData;
	}else{
		rowd={"fxyp07":"","tp0100":guid,"fxyp02":"","tp0101":"","tp0102":"","tp0103":"","tp0104":"","tp0105":"","tp0106":"","tp0107":"","tp0108":"","tp0109":"","tp0110":"","tp0111":"","tp0112":"","tp0113":"","tp0114":"","tp0115":""};
	}
	//更新数据对象
	/*if(rowIndex>0){
		GLOBLE['ROWID'].splice(rowIndex-GLOBLE.rowOffset, 0, guid);
		GLOBLE['ID_ROWINFO'][guid]=rowd;
	}else if(rowIndex==0){//在表头上表示追加
		GLOBLE['ROWID'].push(guid);
		GLOBLE['ID_ROWINFO'][guid]=rowd;
	}*/
	return tr;
}



//更新行后更新序号和行号。
var complateView = function () {
    $('#coordTable2 tr:gt(0) td:nth-child(1)').each(function (i, item) {
        $(this).text(i + 1);
    });
    var k = 1;
    $('#coordTable2 tr:gt(0) td:nth-child(2)').each(function (i, item) {
    	if($(this).attr('gw')=='true'){
    		$(this).text(k++);
    	}
        
    });
    k = 1;
    $('#coordTable2 tr:gt(0) td:nth-child(4)').each(function (i, item) {
    	if($("td:nth-child(2)", $(this).parent()).attr('gw')=='true'){
    		k = 1;
    		$(this).text(k++);
    	}else{
    		$("td:nth-child(2)", $(this).parent()).text(k++);
    	}
    });
}



function bianji(){
	$('.toggle').css('margin-left','667px');
	$('.bianji').css('display','none');
	$('.chakan').css('display','inline');
	$('#coordTable').css('display','block');
	$('#coordTable2').css('display','none');
}
function chakan(){
	$('.toggle').css('margin-left','824px');
	$('.chakan').css('display','none')
	$('.bianji').css('display','inline');
	$('#coordTable').css('display','none');
	$('#coordTable2').css('display','block');
}