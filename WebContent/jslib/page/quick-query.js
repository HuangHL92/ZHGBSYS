var cond1;
var cond2;
var cond3;
var cond4;
var cond5;

$(function(){
	cond1 = $("#cond1").tags();
	cond2 = $("#cond2").tags();
	cond3 = $("#cond3").tags();
	cond4 = $("#cond4").tags();
	cond5 = $("#cond5").tags();
	
	$(".tags").bind('dblclick', function() {
		onCondictionClick($(this).attr('id'));
	});
	
	// 支持拖动
	$(".tags").sortable({
		scroll : true,
		scrollSensitivity: 100, 
		scrollSpeed: 100,
		connectWith: ".tags"
	}).disableSelection();
	
	// 初始化标签
	var hasItem = $("#itemName").length != 0;
	if (hasItem) {
		$("#cond1").tags('addTag', $("#itemName").val(), $("#itemId").val());
	}
	
	$("#mc").datagrid({
		fit: true,
		rownumbers:true,
		singleSelect:false,
		pagination:true, 
		nowrap: false,
		pageSize : 20,
		idField:'id',
		sortName:'xingm',
		sortOrder:'asc',
		frozenColumns: PERSON_FROZEN_COLUMNS,
		columns: PERSON_COLUMNS,
		toolbar: [{
			id:'btn_printAll',
			text:'<input type=\"checkbox\" id=\"chk_printAll\"/> 打印全部',
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
		onLoadSuccess:function(data){
			$("#mc").datagrid('clearSelections');
			setTimeout(function(){
				//if ($("#mm").length == 0) {
					System.report.menu("#print", printData, false, false);
				//}
				
			},10);
		}
	});
	
	if (hasItem) {
		onSearch();
	}
});

function onCondictionClick(id) {
	clearSelection();
	var cond;
	if (id == "cond1") cond = cond1;
	else if (id == "cond2") cond = cond2;
	else if (id == "cond3") cond = cond3;
	else if (id == "cond4") cond = cond4;
	else if (id == "cond5") cond = cond5;
	
	openConditionSelectionDialog(id, cond);
}

function clearSelection() { 
    if (document.selection && document.selection.empty) { 
        document.selection.empty(); 
    } 
    else if(window.getSelection) { 
        var sel = window.getSelection(); 
        sel.removeAllRanges(); 
    } 
} 

function printData(){
	var isPrintAll = $("#chk_printAll").is(':checked');
	if (isPrintAll) {
		printAll();
	} else{
		var personcodes = getSelectedPersons();
		if (personcodes != '') {
			System.report.print(personcodes);
		}
	}
}

function printAll(){
	$.messager.confirm('提示','是否打印全部?',function(b){
		if(b){
			// 获取当前条件
			var options = $("#mc").datagrid('options');
			options.queryParams["queryType"] = 90;
			
			$.ajax({
				url: System.rootPath+'/common/exec-common-method!createPrintSessionkey.action',
				data: options.queryParams,
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

function getSelectedPersons() {
	var personcodes = '';
	var rows = $("#mc").datagrid("getSelections");
	if (rows) {
		$(rows).each(function(index, row){
			if (personcodes == '') personcodes = row.personcode;
			else personcodes = personcodes + "," + row.personcode;
		});
	}
	
	return personcodes;
}

function onSearch() {
	var cond1Values = cond1.tags('getValues');
	var cond2Values = cond2.tags('getValues');
	var cond3Values = cond3.tags('getValues');
	var cond4Values = cond4.tags('getValues');
	var cond5Values = cond5.tags('getValues');
	
	var total = cond1Values.length + cond2Values.length + 
	            cond3Values.length + cond4Values.length + 
				cond5Values.length;
				
	if (total == 0) {
		System.showErrorMsg('请添加至少一个条件！');
		return;
	}
	
	// 构造查询参数
	var conditions = {};
	if (cond1Values.length > 0) {
		conditions["conditions[0].query_item_ids"] = cond1Values.join(',');
		conditions["conditions[0].query_logic"] = 0;
	}
	
	if (cond2Values.length > 0) {
		conditions["conditions[1].query_item_ids"] = cond2Values.join(',');
		conditions["conditions[1].query_logic"] = 1;
	}
	
	if (cond3Values.length > 0) {
		conditions["conditions[2].query_item_ids"] = cond3Values.join(',');
		conditions["conditions[2].query_logic"] = 1;
	}
	
	if (cond4Values.length > 0) {
		conditions["conditions[3].query_item_ids"] = cond4Values.join(',');
		conditions["conditions[3].query_logic"] = 1;
	}
	
	if (cond5Values.length > 0) {
		conditions["conditions[4].query_item_ids"] = cond5Values.join(',');
		conditions["conditions[4].query_logic"] = 1;
	}
	
	// 执行查询
	var options = $("#mc").datagrid('options');
	options.queryParams = conditions;
	$("#page").val(1);
	options.pageNumber = 1;
	options.url = System.rootPath + "/query/quick-query!search.action";
	$("#mc").datagrid(options);
}

function onReset() {
	cond1.tags('removeAllTags');
	cond2.tags('removeAllTags');
	cond3.tags('removeAllTags');
	cond4.tags('removeAllTags');
	cond5.tags('removeAllTags');
}

function openConditionSelectionDialog(id, cond) {
	var h4 = $("#" + id).parent().find('h4');
	var title = h4.html();
	
	$("#dlg").show();
	$('#dlg').dialog({
		title: title,
		width: 320,
		height: 480,
		modal: true,
		buttons:[{
			iconCls:'icon-ok',
			text:'选择',
			handler:function(){
				var rows = $("#itemList").datagrid('getSelections');
				if (rows) {
					$(rows).each(function(i, v) {
						cond.tags('addTag', v.item_name, v.id);
					});
					
					onSearch();
				}
				$("#dlg").dialog('close');
			}
		}/*,{
			iconCls:'icon-ok',
			text:'选择并关闭',
			handler:function(){
				var rows = $("#itemList").datagrid('getSelections');
				if (rows) {
					$(rows).each(function(i, v) {
						cond.tags('addTag', v.item_name, v.id);
					});
				}
				$("#dlg").dialog('close');
			}
		}*/,{
			iconCls:'icon-cancel',
			text:'关闭',
			handler:function(){	
				$("#dlg").dialog('close');
			}
		}],
		onBeforeOpen : function () {
			$("#itemList").datagrid({
				fit: true,
				rownumbers:false,
				singleSelect:false,
				pagination:false, 
				nowrap: false,
				idField:'id',
				columns: [[
					{ field:'ck', checkbox:true },
					{ field: 'item_name', title: '条件名称', width: 240 }
				]],
				onLoadSuccess:function(data){
					$("#itemList").datagrid('clearSelections');
				}
			});
	
			$('#itemType').combobox({
				url: System.rootPath + '/query/quick-query!getEnabledMasterList.action',
				valueField: 'id',
				textField: 'name',
				editable: false,
				width: 220,
				onLoadSuccess: function() {
					// 默认选中第一个选项
					var datas = $("#itemType").combobox('getData');
					if (datas && datas.length > 0) {
						$("#itemType").combobox('select', datas[0].id);
					}
				},
				onSelect: function(record) {
					var options = $("#itemList").datagrid('options');
					options.queryParams = {
						masterId: record.id
					};
					options.url = System.rootPath + "/query/quick-query!getEnabledItemList.action";
					$("#itemList").datagrid(options);
				}
			});
		}
	});
}