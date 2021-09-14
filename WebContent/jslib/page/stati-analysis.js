
$(document).ready(function(){
	$("#mc").datagrid({
		fit: true,
		rownumbers:true,
		singleSelect:true,
		pagination:true, 
		pageSize : 20,
		idField:'id',
		sortName:'xingm',
		sortOrder:'asc',
		//frozenColumns: PERSON_FROZEN_COLUMNS,
		columns: TRAIN__COLUMNS,
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
			setTimeout(function(){
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
			},10);
			setTimeout("initSelect()",10);
		}
	});
});

function detail(personcode, name) {
    $("#personcode").val(personcode);
	System.commonEditor.editorSingePerson(personcode);
}

/*function printData(){
	var printAll = $("#chk_printAll").attr('checked');
	if(printAll){
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
			d.simpleCondition = $("#mc").data("simpleCondition");
			d.queryType = 5;
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
}*/
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
	$("#page").val(1);
	$("#printData").removeData("pageSelects");
}
function loadMcData(){
	clearSelect();
	//$("#listPanel").show().siblings().hide();
	//$("#queryTab").tabs('select', 1);
	
	//$("#mc").datagrid();
	var condition = getAllCondition();
	var options = $("#mc").datagrid('options');
	var queryParam = options.queryParams;
	options.pageNumber = parseInt($("#page").val());
	queryParam.serviceid = $("#serviceid").val();
	queryParam.simpleCondition = encodeURI($.toJSON(condition));
	options.url=System.rootPath+"/pxfx/stati-analysis!searchBySimpleCondition.action";
	$("#mc").datagrid(options);
	$("#mc").data("simpleCondition",queryParam.simpleCondition);
}
//反向搜索
function loadMcDataByReSearch(){
	var condition = getAllCondition();
	var options = $("#mc").datagrid('options');
	var queryParam = options.queryParams;
	options.pageNumber = parseInt($("#page").val());
	queryParam.serviceid = $("#serviceid").val();
	queryParam.simpleCondition = encodeURI($.toJSON(condition));
	options.url=System.rootPath+"/pxfx/stati-analysis!reSearchBySimpleCondition.action";
	$("#mc").datagrid(options);
	$("#mc").data("simpleCondition",queryParam.simpleCondition);
}

function exportByCondition(){
	simpleCondition = $("#mc").data("simpleCondition");
	var url = System.rootPath+"/pxfx/stati-analysis!exportBySimpleCondition.action?simpleCondition="+simpleCondition;
	window.open(url);
}


function getAllCondition(){
	var condition = new Condition();
	condition.age1 = $("#age1").val();
	condition.age2 = $("#age2").val();
	condition.age3 = $("#age3").val();
	condition.age4 = $("#age4").val();
	condition.sex = $("input[name='sex']:checked").val();
	var zhij = [];
	$.each($("input[name='zhij']:checked"),function(i,item){
		zhij.push($(item).val());
    });
	condition.zhij = zhij;
	condition.bcmc = $("#bcmc").val();
	condition.zbdw = $("#zbdw").val();
	condition.cbjg = $("#cbjg").val();
	condition.ljxs1 = $("#ljxs1").val();
	condition.ljxs2 = $("#ljxs2").val();
	condition.ljxs3 = $("#ljxs3").val();// add by yhy
	condition.cxnf1 = $("#cxnf1").val();
	condition.cxnf2 = $("#cxnf2").val();
	condition.bclb = $("#bclb").combobox('getValue');
	condition.mingz = $("input[name='mingz']:checked").val();
	condition.zzmm = $("input[name='zzmm']:checked").val();
	return condition;
}

function Condition(){
	this.age1;
	this.age2;
	this.age3;
	this.age4;
	this.bcmc;
	this.zbdw;
	this.cbjg;
	this.ljxs1;
	this.ljxs2;
	this.ljxs3;
	this.cxnf1;
	this.cxnf2;
	this.sex;
	this.bclb;
	this.zhij = [];
	this.mingz; 
	this.zzmm;
}
