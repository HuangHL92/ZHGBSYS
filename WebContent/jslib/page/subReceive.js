var subReceive = {
	init : function (target,t){
		$(target).datagrid({
			//height : 424,
			//width : $('body').outerWidth(true)-340,
			fit: true,
			checkbox:true,
			rownumbers:true,
			singleSelect:false,
			pagination:true, 
			pageSize : 20,
			pageList : [10,20,30],
			idField:'personcode',
			sortName:'xingm',
			sortOrder:'asc',
			frozenColumns:[[
				{field:'id',hidden:true},
				{
					field: 'isConfirmed',
					title: ' ',
					align: "center",
					width: 32,
					formatter: function(value, rowData, rowIndex) {
						if (value == "1") {
							return "<img src='/govhr/style/default/images/seal.png' border='0' title='三龄一历已审核' style='vertical-middle: middle;'/>";
						} else {
							return "<img src='/govhr/style/default/images/unseal.png' border='0' title='三龄一历未审核' style='vertical-middle: middle;'/>";
						}
					}
				},
				{field:'xingm',title:'姓名',align:"center",width:60,
					formatter:function(value,rowData,rowIndex){
						var sexCode= rowData.sexCode;
						if(sexCode == "2"){
							if(t==2){
								return value == undefined ? "" : "<a href=\"javascript:void(0);\" onclick=\"System.leaderQuery.showRemoval2('"+rowData.personcode+"','"+$("#packagename").val()+"')\">"+value+"</a>"+"<br/>(女)";
							}else{
								return value == undefined ? "" : "<a href=\"javascript:void(0);\" onclick=\"System.leaderQuery.showRemoval('"+rowData.personcode+"')\">"+value+"</a>"+"<br/>(女)";
							}
						}else{
							if(t==2){
								return value == undefined ? "" : "<a href=\"javascript:void(0);\" onclick=\"System.leaderQuery.showRemoval2('"+rowData.personcode+"','"+$("#packagename").val()+"')\">"+value+"</a>";
							}else{
								return value == undefined ? "" : "<a href=\"javascript:void(0);\" onclick=\"System.leaderQuery.showRemoval('"+rowData.personcode+"')\">"+value+"</a>";
							}
						}
					}
				}
			]],
			columns:[[
				{field:'minz',title:'民族',align:"center",width:60,resizable:true},
				{field:'birthday',title:'出生年月',align:"center",width:80,resizable:true},
				
				{field:'jig',title:'籍贯',align:"center",width:80,resizable:true},
				{field:'chusd',title:'出生地',align:"center",width:80,resizable:true},
				{field:'gongzsj',title:'工作时间',width:80},
				{field:'rudsj',title:'入党时间',width:80,
					formatter:function(value,rowData,rowIndex){
						var zzmmCode= rowData.zzmmCode;
						if(zzmmCode != "01"){
							if(rowData.zzmm != undefined && rowData.zzmm != ""){
								return value+"<br/>("+rowData.zzmm+")";
							}else{
								return value == undefined ? "" : value;
							}
						}else{
							return value == undefined ? "" : value;
						}
					}
				},
				{field:'zhiw',title:'职务',align:"left",width:300,resizable:true}
			]],
		    onSelect: function(rowIndex,rowData){
		    	var obj = $("#printData").data("pageSelects");
		    	if(obj == undefined){
		    		obj = [];
		    	}
		    	var pageNo = $("#page").val();
		    	var pageSelect = System.getElement(obj,"pageNo",pageNo);
		    	if(pageSelect == undefined){
		    		pageSelect = new PageSelect();
		    		pageSelect.pageNo = pageNo;
		    		obj.push(pageSelect);
		    	}
		    	var selectRow = new SelectRow();
		    	selectRow.rowIndex = rowIndex;
		    	selectRow.keywordId = rowData.personcode;
		    	selectRow.keywordcn = rowData.xingm;
		    	
		    	// bugfix: 如果已经包含了该人员，不能被选中了！！by YZQ on 2015/09/23
				var existed = false;
				for (var i = 0; i < pageSelect.selecteds.length; i++) {
					if (pageSelect.selecteds[i].keywordId == rowData.personcode) {
						existed = true;
						break;
					}
				}
				
				if (!existed) {
					pageSelect.selecteds.push(selectRow);
				}
				
		    	$("#printData").data("pageSelects",obj);
		    },
		    onUnselect: function(rowIndex,rowData){
		    	var obj = $("#printData").data("pageSelects");
		    	if(obj){
		    		var pageNo = $("#page").val();
		    		var pageSelect = System.getElement(obj,"pageNo",pageNo);
		    		if(pageSelect){
		    			var selecteds = pageSelect.selecteds;
		    			if(selecteds){
		    				selecteds = System.removeElement(selecteds,"rowIndex",rowIndex);
		    				pageSelect.selecteds = selecteds;
		    				//obj.push(pageSelect);
		    				$("#printData").data("pageSelects",obj);
		    			}
			    	}
		    	}
		    },
		    onLoadSuccess:function(data){
		    	$(target).datagrid('unselectAll');
				var options = $(target).datagrid('options');
				$("#page").val(options.pageNumber);
				$("#rows").val(options.pageSize);
				setTimeout(function(){
					var obj = $("#printData").data("pageSelects");
					if(obj){
				    	var pageNo = $("#page").val();
				    	var pageSelect = System.getElement(obj,"pageNo",pageNo);
				    	if(pageSelect){
							$.each(pageSelect.selecteds,function(i,v){
								$(target).datagrid("selectRow",v.rowIndex);
							});
				    	}
					}
				},10);
			}
		});
	},
	removeCallbak : function(personcode,rowIndex,pageNo){
		if($("#page").val() == pageNo){
			$("#mc").datagrid("unselectRow",rowIndex);
		}else{
			var pageSelect = System.getElement(obj,"pageNo",pageNo);
    		if(pageSelect){
    			var selecteds = pageSelect.selecteds;
    			if(selecteds){
    				selecteds = System.removeElement(selecteds,"rowIndex",rowIndex);
    				pageSelect.selecteds = selecteds;
    				$("#printData").data("pageSelects",obj);
    			}
	    	}
		}
	}
}; 

subReceive.sub = {
	loadByUnit : function(target){
		$("#page").val('1');
		$("#printData").removeData("pageSelects");
		var options = $(target).datagrid('options');
		var queryParam = options.queryParams;
		options.pageNumber = 1;
		queryParam.serviceid = $("#serviceid").val();
		queryParam.currentUnit = $("#currentUnit").val();
		options.url=System.rootPath+"/mingc/mingc!queryByUnit.action";
		$(target).datagrid(options);
	},
	loadDataByQuerykey: function(target) {
		$("#page").val('1');
		$("#printData").removeData("pageSelects");
		var options = $(target).datagrid('options');
		var queryParam = options.queryParams;
		options.pageNumber = 1;
		queryParam.serviceid = $("#serviceid").val();
		queryParam.currentUnit = $("#currentUnit").val();
		queryParam.querykey = $("#querykey").val();
        queryParam.group = "A";        
		options.url=System.rootPath+"/mingc/mingc!queryByQuerykey.action";
		$(target).datagrid(options);
	},
	subInit : function (firstUpload){
		$.ajax({
			url:System.rootPath+'/subreceive/sub!initPackageSub.action',
			type:'POST',
			dataType:'json',
			success:function(data){
				if(data.flag){
					$("#packagename").val(data.packagename);
					subReceive.sub.subPerson(firstUpload);
				}else{
					$.messager.alert('错误','网络错误! 错误信息：' + data,'error');
				}
			},
			error : function (XMLHttpRequest, textStatus, errorThrown){
				$.messager.alert('错误','网络错误! 错误信息：' + errorThrown ,'error');
			},
			complete:function(){
			}
		});
	},
	subPerson : function (uploadObj){
		if(uploadObj){
			$.ajax({
				url:System.rootPath+'/subreceive/sub!subPerson.action',
				type:'post',
				data:{'personcode':uploadObj.keywordId,'packagename':$("#packagename").val()},
				success:function(data){
					var obj = subReceivePlugin.changeProgress(data.flag ? "0":"3");
					if(data.flag){
						subReceive.removeCallbak(uploadObj.keywordId,uploadObj.rowIndex,uploadObj.pageNo);
					}
					if(obj.v>=100){
						if(obj.successCount>0){
							subReceivePlugin.endProccess($("#packagename").val()+".zip","下载上报包");
							$("#downloadForm").submit();
						}
					}else{
						subReceive.sub.subPerson(obj.next);
					}
				},
				error : function (XMLHttpRequest, textStatus, errorThrown){
					alert('导出时出现错误，错误信息：' + errorThrown);
					var obj = subReceivePlugin.changeProgress("3");
					if(obj.v>=100){
						subReceivePlugin.endProccess($("#packagename").val()+".zip","下载上报包");
					}else{
						subReceive.sub.subPerson(obj.next);
					}
				},
				complete:function(){
				}
			});
		}
	}
};
subReceive.receive = {
	loadData : function(target,packagename){
		$(target).datagrid({
			url:System.rootPath+"/subreceive/receive!loadData.action",
			pageNumber:1,
			queryParams:{"packagename":packagename}
		});
	},
	receivePerson : function (uploadObj){
		if(uploadObj){
			$.ajax({
				url:System.rootPath+'/subreceive/receive!receivePerson.action',
				type:'post',
				data:{'personcode':uploadObj.keywordId,'packagename':$("#packagename").val(),'currentUnit':$("#currentUnit").val(),'currentUnitName':$("#currentUnitName").val()},
				success:function(data){
					var obj = subReceivePlugin.changeProgress(data.flag ? "0":"1");
					if(data.flag){
						subReceive.removeCallbak(uploadObj.keywordId,uploadObj.rowIndex,uploadObj.pageNo);
						// bugfix, 必须先获取row index再从表格中删除记录, by YZQ on 2013/05/04
						try {
							// get the row index first
							var rowIndex = $("#mc").datagrid('getRowIndex', uploadObj.keywordId);
							$("#mc").datagrid('deleteRow', rowIndex);
						}
						catch (ex) {
							// 即使出错，仍然导入下一个
						}
					}
					if(obj.v>=100 && obj.exsitsCount > 0){
						subReceivePlugin.endProccess($("#packagename").val()+".txt","下载未接收成功人员名单");
					}
					else if (obj.v >= 100 && obj.exsitsCount == 0) {
						// 弹出排序提示，by YZQ on 2013/05/23			
						$.messager.alert('提示', '导入成功，请重新进行干部排序！');
					}
					else{
						subReceive.receive.receivePerson(obj.next);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
					alert('接收时出现错误，错误信息：' + errorThrown);
					var obj = subReceivePlugin.changeProgress("3");
					if(obj.v>=100 && obj.exsitsCount > 0){
						subReceivePlugin.endProccess($("#packagename").val()+".txt","下载未接收成功人员名单");
					}else{
						subReceive.receive.receivePerson(obj.next);
					}
				},
				complete : function(){}
			});
		}
	}
};