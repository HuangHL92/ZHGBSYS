function renderRow(imgbasepath,rowData){
	var content = "<table class='dataTable' style=\"width: 100%\">";
	var fiexdRow = $("#fixedTable tr").length;
	$.each(rowData,function(i,item){
		content += "<tr height=\"30px;\">";
		content += "	<td class='label' width=\"60\" align=\"center\">" + (i+1+fiexdRow) + "</td>";
		content += "	<td class='content'><a href=\"javascript:void(0);\" onclick=\"execQuery('"+item.conditionId+"','"+item.contentName+"')\">" +item.contentName+ "</a></td>";/*item.contentDesc*/
		content += "</tr>";
	});
	content += "</table>";
	$("#dynamic").empty().append(content);
}

function execQuery(queryid,msg){
	clearSelect();
	//$("#listPanel").show().siblings().hide();
	$("#queryTab").tabs('select', '查询结果');
	var options = $("#mc").datagrid('options');
	var queryParam = options.queryParams;
	options.pageNumber = parseInt($("#page").val());
	queryParam.serviceid = $("#serviceid").val();
	queryParam.queryId = queryid;
	options.url=System.rootPath+"/query/level-query!execQueryById.action";
	$("#mc").datagrid(options);
	
	System.report.menu("#print",printData);
	
	$("#msg").html(msg);
	
	$("#mc").data("queryType",6);
	$("#mc").data("queryId",queryid);
}

$(document).ready(function(){
	$("#mc").datagrid({
		//height : 455,
		fit: true,
		nowrap: false,
		rownumbers:true,
		singleSelect:false,
		pagination:true, 
		pageSize : 20,
		idField:'id',
		sortName:'xingm',
		sortOrder:'asc',
		frozenColumns: PERSON_FROZEN_COLUMNS,
		columns: PERSON_COLUMNS,
		toolbar: [{
			text:'返回',
		    iconCls:'icon-back',
		    handler:function(){
				//$("#general").show().siblings().hide();
				$("#queryTab").tabs('select', '查询条件');
			}
		},'-',{
			id:'btn_printAll',
			text:'<span id="msg" style="font-weight:bold;color:red;"></span>&nbsp;<input type=\"checkbox\" id=\"chk_printAll\"/> 打印全部',
		    handler:function(){
				var rows = $("#mc").datagrid('getRows');
				if(rows.length==0){
					$("#chk_printAll").attr('checked',false);
					$.messager.alert('提示','没有数据打印!');
				}
			}
		}, {
			id: 'print'
		}],
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
	    				$("#printData").data("pageSelects",obj);
	    			}
		    	}
	    	}
	    },
		onLoadSuccess:function(data){
			$("#mc").datagrid('unselectAll');
			$("#listPanel .pagination-info").append("<span>  "+data.otherMsg+"</span>");
			var options = $("#mc").datagrid('options');
			$("#page").val(options.pageNumber);
			$("#rows").val(options.pageSize);
			$("#mc").datagrid("getPager").pagination("options").showPageList=false;
			setTimeout("initSelect()",10);
		}
	});
});
function loadMcData(id,msg){
	clearSelect();
	$(".validatebox-tip").remove();
	$.each($("#fixedTable .validatebox-text"),function(i,v){
		$(v).removeClass("validatebox-invalid");
	});
	var parames = new Object();
	if(parseInt(id,10) == 2){
		$("#rz_2").validatebox({required:true});
		if(!$("#rz_2").validatebox('isValid')){return ;}
		parames.arg1 = $("#rz_2").combotree('getValue');
		parames.arg1show = $("#rz_2").combotree('getText');
		$("#year_2").validatebox({required:true});
		$("#age_2").validatebox({required:true});
		if($("#year_2").validatebox('isValid') && $("#age_2").validatebox('isValid')){
			parames.arg2 = $("#year_2").val();
			parames.arg3 = $("#age_2").val();
		}else{
			return;
		}
	}else if(parseInt(id,10) == 4){
		parames.arg1 = "";
		parames.arg2 = "";
		$("#age_4").validatebox({required:true});
		if($("#age_4").validatebox('isValid')){
			parames.arg3 = $("#age_4").val();
		}
	}else{
		if($("#rz_"+id).length > 0){
			$("#rz_"+id).validatebox({required:true});
			if(!$("#rz_"+id).validatebox('isValid')){return ;}
			parames.arg1 = $("#rz_"+id).combotree('getValue');
			parames.arg1show = $("#rz_"+id).combotree('getText');
		}else{
			parames.arg1 = "";
		}
		if($("#year_"+id).length > 0){
			$("#year_"+id).validatebox({required:true});
			if(!$("#year_"+id).validatebox('isValid')){return;}
			parames.arg2 = $("#year_"+id).val();
		}else{
			parames.arg2 = "";
		}
		parames.arg3 = "";
	}
	//$("#listPanel").show().siblings().hide();
	$("#queryTab").tabs('select', '查询结果');
	//$.messager.alert("提示",$.toJSON(parames));
	var options = $("#mc").datagrid('options');
	var queryParam = options.queryParams;
	options.pageNumber = parseInt($("#page").val());
	queryParam.serviceid = $("#serviceid").val();
	queryParam.queryNo = id;
	queryParam.dynamicQueryParames = encodeURI($.toJSON(parames));
	options.url=System.rootPath+"/leader/leader-query!executeGeneralQuery.action";
	$("#mc").datagrid(options);
	//$("#mc").datagrid();// 开始为隐藏，故必须要将表头显示出来
	System.report.menu("#print", printData);
	
	$("#msg").html(msg);
	
	$("#mc").data("queryType",4);
	$("#mc").data("queryNo",id);
	$("#mc").data("dynamicQueryParames",queryParam.dynamicQueryParames);
}
function printData(){
	var printAll = $("#chk_printAll").is(':checked');
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
			var d = new Object();
			d.serviceid = $("#serviceid").val();
			var queryType = $("#mc").data("queryType");
			if(queryType == 4){
				d.queryNo = $("#mc").data("queryNo");
				d.dynamicQueryParames = $("#mc").data("dynamicQueryParames");
			}else{
				d.queryId = $("#mc").data("queryId");
			}
			d.queryType = queryType;
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
}