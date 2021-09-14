var g_firstLoad = true;

/**
 * 通用入口
 */
$(document).ready(function(){
	$("#mc").datagrid({
		nowrap: false,
		rownumbers:true,
		singleSelect:false,
		fit: true,
		pagination:true, 
		pageSize : 50,
		pageList : [10,20,30,50,100,200],
		idField:'personcode',
		sortName:'xingm',
		sortOrder:'asc',
		frozenColumns:[[
			{field:'personcode',hidden:true},
			{field:'ck',checkbox:true},
			{field:'xingm',title:'姓名',align:"center",width:60,
				formatter:function(value,rowData,rowIndex){
					var sexCode= rowData.sexCode;
					if(sexCode == "2"){
						return value == undefined ? "" : value + "<br/>(女)";
					}else{
						return value == undefined ? "" : value;
					}
				}
			}
		]],
		columns:[[
			{field:'zhiw',title:'职务',align:"left",rowspan:2,width:200,resizable:true},//,rowspan:2
			{field:'minz',title:'民族',align:"center",rowspan:2,width:60,resizable:true},//,rowspan:2
			
			{field:'jig',title:'籍贯',rowspan:2,align:"center",width:80,resizable:true},
			{field:'chusd',title:'出生地',rowspan:2,align:"center",width:80,resizable:true},
			{title:'全日制教育',colspan:2,align:"center"},
			{title:'在职教育',colspan:2,align:"center"},
			{field:'gongzsj',title:'工作时间',rowspan:2,width:80},
			{field:'rudsj',title:'入党时间',rowspan:2,width:80,
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
			{field:'xianjsj',title:'任现职级时间',rowspan:2,width:80}
		],[
			{field:'qrzxl',title:'学历学位',width:100,align:"center",
				formatter:function(value,rowData,rowIndex){
					value = "";
					if(rowData.qrzxl != undefined && rowData.qrzxl != ""){
						value += rowData.qrzxl;
					}
					if(rowData.qrzxw != undefined && rowData.qrzxw != ""){
						value += "<br/>"+rowData.qrzxw;
					}
					return value;
				}
			},
			{field:'qrzbyyx',title:'毕业院校及专业',width:120,align:"center",
				formatter:function(value,rowData,rowIndex){
					value = "";
					if(rowData.qrzbyyx != undefined && rowData.qrzbyyx != ""){
						value += rowData.qrzbyyx;
					}
					if(rowData.qrzzy != undefined && rowData.qrzzy != ""){
						value += "<br/>"+rowData.qrzzy;
					}
					return value;
				}
			},
			{field:'zzxl',title:'学历学位',width:100,align:"center",
				formatter:function(value,rowData,rowIndex){
					value = "";
					if(rowData.zzxl != undefined && rowData.zzxl != ""){
						value += rowData.zzxl;
					}
					if(rowData.zzxw != undefined && rowData.zzxw != ""){
						value += "<br/>"+rowData.zzxw;
					}
					return value;
				}
			},
			{field:'zzbyyx',title:'毕业院校及专业',width:120,align:"center",
				formatter:function(value,rowData,rowIndex){
					value = "";
					if(rowData.zzbyyx != undefined && rowData.zzbyyx != ""){
						value += rowData.zzbyyx;
					}
					if(rowData.zzzy != undefined && rowData.zzzy != ""){
						value += "<br/>"+rowData.zzzy;
					}
					return value;
				}
			}
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
	    	pageSelect.selecteds.push(selectRow);
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
	    	$("#mc").datagrid('unselectAll');
			var options = $("#mc").datagrid('options');
			$("#page").val(options.pageNumber);
			$("#rows").val(options.pageSize);
		}/*,
		toolbar:[{
			id:'btn_printAll',
			text:'<input type=\"checkbox\" id=\"chk_printAll\"/> 打印全部',
		    handler:function(){
				// 打印所有结果	
				var rows = $("#mc").datagrid('getRows');
				if(rows.length==0){
					$("#chk_printAll").attr('checked',false);
					$.messager.alert('提示','没有数据打印!');
				}
			}
		}]*/
	});
 	//$('#mc').pagination({
 	//	onSelectPage:function(pageNumber,pageSize){
 	//		var options = $("#mc").datagrid('options');
 	//		options.pageNumber = pageNumber;
 	//		options.pageSize = pageSize;
 	//		$('#mc').datagrid(options);
	//	}
 	//});
});

function printData(){
	var printAll = $("#chk_printAll").attr('checked');
	if(printAll){// 打印全部
		PrintAll();
	}else{
		var obj = $("#printData").data("pageSelects");
		var keys = "";
		var count = 0;
		if(obj){
			$.each(obj,function(i,v){
				var selecteds = v.selecteds;
				$.each(v.selecteds,function(j,s){
					if(count>0){
						keys +=",";
					}
					keys += s.keywordId;
					count ++;
				});
			});
			System.report.print(keys);
		}
	}
}
function PrintAll(){
	$.messager.confirm('提示','是否打印全部?',function(b){
		if(b){
			var d = $("#mc").data('queryParams');
			d.queryType = $("#mc").data('queryType');
			$.ajax({
				url:System.rootPath+'/common/exec-common-method!createPrintSessionkey.action',
				data:d,
				success:function(sessionkey){
					System.report.printAll(sessionkey);
				},
				error:function(){
					$.messager.alert('错误','网络错误!','error');
				},
				complete:function(){}
			});
		}
	});
}
//初始化上一次选中
function  initSelect(){
	var obj = $("#printData").data("pageSelects");
	if(obj){
    	var pageNo = $("#page").val();
    	var pageSelect = System.getElement(obj,"pageNo",pageNo);
    	if(pageSelect){
			$.each(pageSelect.selecteds,function(i,v){
				$("#mc").datagrid("selectRow",v.rowIndex);
			});
    	}
	}
}
/**
 * 切换单位或者姓名查询或者高级查询都需要清除选择
 * @return
 */
function clearSelect(){
	$("#page").val(1);
	$("#printData").removeData("pageSelects");
	$("#personOrder").linkbutton("disable");
}

var Bus = {
	showList : function (data,url){
		var opts = $("#mc").datagrid('options');
		opts.pageNumber = parseInt($("#page").val(), 10);
		opts.url = url;
		var params = opts.queryParams;
		params.serviceid = $("#serviceid").val();
		params.currentUnit = $("#currentUnit").val();
		params.querykey = $("#querykey").val();
		params.group = $("#group").val();
		params.tables = $("#tables").val();
		params.querySQL = $("#querySQL").val();
		
		opts.queryParams = params;
        //$("#mc").datagrid(opts);
		$("#mc").datagrid('loadData', data);
        $("#mc").data('queryParams', params);    
	},
	loadDataByLevelQuery:function(condition){
		if(condition){
			clearSelect();
			$("#group").val(condition.conditionGroupkey);
			$("#tables").val(condition.conditionTablekey);
			$("#querySQL").val(condition.conditionQuerySQL);
			$("#querykey").val("");
			// 不清空当前选中的单位, by YZQ on 2013/03/25
            //$("#currentUnit").val("");
            //$("#currentUnitName").val("");
			var url=System.rootPath+"/query/level-query!execSearch.action";
			$("#mc").data("queryType",3);
			Bus.loadData(url);
		}
	},
	loadDataByQuerykey:function(){
		clearSelect();
		var url = System.rootPath+"/mingc/mingc!queryByQuerykey.action";
		$("#group").val("");
		$("#tables").val("");
		$("#querySQL").val("");
		// 不清空当前选中的单位, by YZQ on 2013/03/25
		//$("#currentUnit").val("");
		//$("#currentUnitName").val("");
		$("#mc").data("queryType",2);
		Bus.loadData(url);
	},
	loadDataByUnit:function(){
		clearSelect();
		var url = System.rootPath+"/mingc/mingc!queryByUnit.action";
		$("#group").val("");
		$("#tables").val("");
		$("#querySQL").val("");
		$("#querykey").val("");
		Bus.loadData(url);
		//$("#personOrder").linkbutton("enable");
		$("#mc").data("queryType",1);
	},
	loadDataByManyQuery : function() {
		clearSelect();
		var url = System.rootPath
				+ "/mingc/mingc!queryForMany.action?fzai=false";
		$("#group").val("");
		$("#tables").val("");
		$("#querySQL").val("");
		$("#querykey").val("");
		// 不清空当前选中的单位, by YZQ on 2013/03/25
		//$("#currentUnit").val("");
		//$("#currentUnitName").val("");
		Bus.loadData(url);
		//$("#personOrder").linkbutton("enable");
		$("#mc").data("queryType", 7)
	},
	loadData : function (url){
		if(!url){
			url = System.rootPath+"/mingc/mingc!queryByUnit.action";//System.rootPath+'/common/exec-common-method!executeQuery.action';
		}
		
		// 如果是搜索名字，这里处理搜索方式, by YZQ on 2013/03/25
		if ($("#mc").data("queryType") == 2) {
			if ($("#searchMode").val() == 'searchAll') {
				$("#searchUnit").val('');
			}
			else {
				$("#searchUnit").val($("#currentUnit").val());				
			}
		}
		
		var params = null;
		if (g_firstLoad) {
			g_firstLoad = false;
			$("#rows").val(50);
		}
		
		params = $("#pageParames").serialize(); // http request parameters. 
		params = decodeURIComponent(params,true);
		$.ajax({
			type:'post',
			url:url,
			data:encodeURI(params),
			beforeSend: function(XMLHttpRequest){
				System.openLoadMask($("#contentPanel"),"正在查询...");
			},
			success: function(data, textStatus){
				// 根据选卡类型 包装显示结果
				Bus.showList(data,url);
			},
			complete: function(XMLHttpRequest, textStatus){
				System.closeLoadMask($("#contentPanel"));
			},
			error: function(){//请求出错处理
				$.messager.alert("提示","查询出现错误,请重试!","error");
			}
		});
	}
};

function gotoPage(pageno){
	var pageno = parseInt(pageno,10);
	if(isNaN(pageno)){
		pageno = 1;
	}else{
		if(pageno<=0){
			pageno = 1;
		}
	}
	$("#page").val(pageno);
	Bus.loadData($("#mc").datagrid('options').url);
}