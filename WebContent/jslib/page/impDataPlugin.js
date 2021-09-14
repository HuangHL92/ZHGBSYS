
var subReceivePlugin = {
	init: function(type,ulData,startCallback,removeCallback){
		var c = $("<div id='#progressCon'></div>").appendTo('body');
		c.load(System.rootPath+"/common/subplugin.jsp",{'type':type},function(){
			$("#startBtn").linkbutton({text:type==1?"开始上报":"开始接收"});
			if(ulData.length>0){
				var count = 0;
				$.each(ulData,function(i,item){
					var pageNo = item.pageNo;
					$.each(item.selecteds,function(j,v){
						count +=1;
						var w = 324 - v.keywordcn.length*12;
						$("#ulData").append("<li id=\""+v.keywordId+"\" rowIndex=\""+v.rowIndex+"\" pageNo=\""+pageNo+"\"><label class=\"xingm\" style=\"margin-right: "+w+"px;\">"+v.keywordcn+"</label><span style=\"padding: 0 0 10 20px;\"><a id=\"li_"+v.keywordId+"\" href=\"javascript:void(0);\">[移除]</a></span></li>");
						$("#li_"+v.keywordId).bind('click',function(){
							$(this).parent().hide('slow',function(){
								$(this).parent().remove();
								if($("#leftlist ul>li").length==0){
									$("#startBtn").linkbutton('disable');
									$("#startBtn").unbind('click');
								}
								var count2 = $("#personCount").html();
								$("#personCount").html(parseInt(count2)-1);
								removeCallback(v.keywordId,v.rowIndex,pageNo);
							});
						});
					});
					
				});
				$("#startBtn").bind('click',function(){
					var uploadData = [];
					var liObj = $("#ulData >li");
					var len = liObj.length;
					liObj.each(function(i,v){
						$(this).find("span").removeClass().addClass("loading");//loading
						if(i==0){
							$(this).find("a").html("等待处理").unbind('click');
						}else{
							$(this).find("a").html("正在处理").unbind('click');
						}
						var obj = new Object();
						obj.keywordId = $(this).attr("id");
						obj.keywordcn = $(this).find("label").html();
						obj.rowIndex = $(this).attr("rowIndex");
						obj.pageNo = $(this).attr("pageNo");
						uploadData.push(obj);
						//
						//var colName = $("#mc").datagrid("getColumnFields"); 
						var data = [];
						var rows = $("#mc").datagrid("getSelections");
						for(var j=0;j< rows.length;j++){
							data.push(rows[j]);
						}
						//alert(uploadData[0].keywordcn);
						if(i== len-1){
							$("#cls").linkbutton('disable');
							$("#startBtn").linkbutton('disable');
							$("#ulData").data("uploadData",uploadData);
							startCallback(uploadData[0],data[0]);
						}
					});
				});
				$("#personCount").html(count);
			}else{
				$("#startBtn").linkbutton('disable');
			}
		});
	},
	changeProgress : function (status){
		var value = $('#p').progressbar('getValue'); 
		var count = parseInt($("#personCount").html());
		var count2 = parseInt($("#personCount2").html());
		$("#personCount2").html(count2+1);
		//value += Math.ceil(1/count*100);
		value = parseInt(((count2 + 1) * 1.0 / count) * 100); // bugfix: 之前的计算进度有问题， by YZQ on 2013/03/02
		$('#p').progressbar('setValue', value);
		if(value>=100){
			$("#personstatus").css('color','red').html("已完成!");
			$("#cls").linkbutton('enable');
			$("#startBtn").linkbutton('disable');
		}
		var uploadData = $("#ulData").data("uploadData");
		var exsitsCount = $("#p").data('exsitsCount');
		var successCount = $("#p").data('successCount');
		if(!exsitsCount){
			exsitsCount = 0;
		}
		if(!successCount){
			successCount = 0;
		}
		if(status == "0"){
			$("#"+uploadData[count2].keywordId).find("span").removeClass().addClass("icon-ok");
			$("#"+uploadData[count2].keywordId).find("a").html("<font color='blue'>处理成功</font>").unbind('click');
			successCount = parseInt(successCount) +1 ;
		}else if(status == "1"){
			$("#"+uploadData[count2].keywordId).find("span").removeClass().addClass("icon-cancel");
			$("#"+uploadData[count2].keywordId).find("a").html("<font color='red'>系统中无此人信息</font>").unbind('click');
			exsitsCount = parseInt(exsitsCount) +1 ;
		}else if(status == "2"){
			$("#"+uploadData[count2].keywordId).find("span").removeClass().addClass("icon-cancel");
			$("#"+uploadData[count2].keywordId).find("a").html("<font color='red'>已经存在,请选择一个导入</font>").unbind('click');
		}
		else{
			$("#"+uploadData[count2].keywordId).find("span").removeClass().addClass("icon-cancel");
			$("#"+uploadData[count2].keywordId).find("a").html("<font color='red'>网络错误</font>").unbind('click');
		}
		$("#p").data('successCount',successCount);
		$("#p").data('exsitsCount',exsitsCount);
		return {v:value,next:uploadData[count2+1],'exsitsCount':exsitsCount,'successCount':successCount};
	},
	endProccess : function (packageName,text){
		$("#downloadLink").attr('href',System.rootPath+"/temp/"+packageName).html(text);
	}
};