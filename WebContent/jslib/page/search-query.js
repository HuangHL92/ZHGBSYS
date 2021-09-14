$(document).ready(function(){
	var lastIndex;
	$("#mc").datagrid({
		rownumbers:true,
		nowrap: false,
		//height : 420,
		fit: true,
		pagination:true, 
		idField:'id',
		sortName:'xingm',
		sortOrder:'asc',
		frozenColumns: PERSON_FROZEN_COLUMNS,
		columns: PERSON_COLUMNS,
		onLoadSuccess:function(data){
			$("#mc").datagrid('unselectAll');
			$("#cadresRegisterList .pagination-info").append("<span>  "+data.otherMsg+"</span>");
			var options = $("#mc").datagrid('options');
			$("#page").val(options.pageNumber);
			$("#rows").val(options.pageSize);
			$("#mc").datagrid("getPager").pagination("options").showPageList=false;
			setTimeout("initSelect()",10);
		},
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
	    	$("#printData").data("pageSelects",obj);;
	    	lastIndex = rowIndex;
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
	    }
	});
});

function addPrintButton() {
	// 在页脚添加分页
	var pager = $('#mc').datagrid().datagrid('getPager');	// get the pager of datagrid
	pager.pagination({
		buttons:[{
			id: 'print'
		}]
	});
	
	System.report.menu("#print", printData,false,false);
}

function loadMcData(){
	clearSelect();
	var options = $("#mc").datagrid('options');
	var queryParam = options.queryParams;
	queryParam.listType = $("#listType").val();
	queryParam.serviceid = $("#serviceid").val();
	queryParam.groupname = $("#groupname").val();
	queryParam.searchWord = $("#searchWord").val();
	options.url=System.rootPath+"/query/search-query!loadMc.action";
	$("#mc").datagrid(options);	
	addPrintButton();
}
function printData(){
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

function clearSelect(){
	var options = $("#mc").datagrid('options');
	options.pageNumber=1;
	$("#mc").datagrid(options);
	$("#printData").removeData("pageSelects");
}