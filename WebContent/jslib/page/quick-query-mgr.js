var g_isEditMaster = false;
var g_isEditItem = false;
$(function(){
	
	$("#itemList").datagrid({
		nowrap: true,
		singleSelect:true,					
		fit: true,
		idField:'id',
		columns:[[
            {field:'id',hidden:true},
            {field:'master_id',hidden:true},
			{field:'item_name', title:'项目名称',width:300},
			{field:'item_tables', title:'查询信息集',width:200},
			{field:'item_sql', title:'查询sql',width:350},
			{field:'enabled', title: '是否有效', width: 100,
				formatter: function(value, row, index) {
					if(value == true){
						return '是';
					}else if(value == false){
						return '否';
					}else {
						return '';
					}
				}},				
			{field:'order_no', title: '显示顺序', width: 100}						
		]],
		toolbar:[{
			iconCls:'icon-add',
			text:'新增',
			handler:function(){
				onAddItem();
			}
		},'-', {
			iconCls:'icon-edit',
			text:'编辑',
			handler:function(){
				onEditItem();
			}
		},'-',{
			iconCls:'icon-remove',
			text:'删除',
			handler:function(){
				var record = $('#itemList').treegrid('getSelected');
				if (record==null){
					$.messager.alert('提示', "请选择要删除的查询项目！");
					return;
				}							
				
				$.messager.confirm('确认', "是否确定要删除该查询项目? <br/><br/>查询项目名称：" + record.item_name, function (isOk) {
					if (isOk) {
						dropItem(record);
					}
				});
			}
		}],
		onLoadSuccess: function() {
			System.closeLoadMask('#pageWin');
		}
	});
	
	$('#list').datagrid({
		fit: true,
		nowrap: false,
		singleSelect:true,
		pagination:true,
		rownumbers:true,
		sortName: 'order_no',
		sortOrder: 'order_no',
		idField:'id',
		columns:[[
            {field:'id',hidden:true},
            {title:'分类名称',field:'name',width:320},
            {title:'是否有效',field:'enabled',width:60,
            	formatter: function(value, row, index) {
				if(value == true){
					return '是';
				}else if(value == false){
					return '否';
				}else {
					return '';
				}
			}},
			{field:'order_no', title: '显示顺序', width: 100}
		]],					
		toolbar:[{
			iconCls:'icon-add',
			id:'btnadd',
			text:'新增',
			handler:function(){							
				onAddMaster();
			}
		},'-',{
			iconCls:'icon-edit',
			id:'btnadd',
			text:'编辑',
			handler:function(){							
				onEditMaster();
			}
		},'-',{
			iconCls:'icon-remove',
			id:'btnsave',
			text:'删除',
			disabled:false,
			handler:function(){
				var record = $('#list').datagrid('getSelected');
				if (record==null){
					$.messager.alert('提示', "请选择要删除的查询类！");
					return;
				}							
				
				$.messager.confirm('确认', "是否确定要删除该查询? <br/><br/>分类名称：" + record.name, function(isOk) {
					if (isOk) {
						dropMaster(record);
					}
				});
			}
		}],
		onClickRow:function(rowIndex, rowData){
			System.openLoadMask('#pageWin', '正在载入数据...');
			var url = System.rootPath+'/query/quick-query-mgr!getItemList.action?masterId='+rowData.id;
		    var opts = $("#itemList").datagrid('options');
		    opts.url = url;
		    $("#itemList").datagrid(opts);
		}
	});
	initList();
});

function initList () {
    var url = System.rootPath+'/query/quick-query-mgr!getMasterList.action';
    var opts = $("#list").datagrid('options');
    //opts.pageNumber = parseInt($("#page").val(), 10);
    opts.pageSize = parseInt($("#rows").val(), 10);
    opts.pageList = [10, 20, 30];
	opts.url = url;
    $("#list").datagrid(opts);
}

function onAddMaster() {
	g_isEditMaster = false;
	$('#winMaster').window({title: '新增快速查询'});
	$('#winMaster').window('open');
	$('#name, #enabled').val('');
	$('#master_order_no').val(0);
}

function onEditMaster() {
	g_isEditMaster = true;
	//var text = null;
	var record = $('#list').datagrid('getSelected');
	if (record==null){
		$.messager.alert('提示', "请选择要编辑的查询类！");
		return;
	}	
	/*if (record.enabled == true)	text = "是";
	else text = "否";*/
	$('#winMaster').window({title: '编辑查询类',onOpen:function() {
		$("#masterForm").form('load', Utils.cloneObject(record,'masterEntity'));
		//$("#enabled").combobox('setValue',record.enabled);
		/*$("#enabled").combobox({
			valueField:record.enabled,
		    textField:text,
		    value:"请选择"
		});*/
	  }
	});
	$('#winMaster').window('open');
}

function addCondition() {
	top.System.LevelQuery.ok == "0";
	System.LevelQuery.getCondition3('A','', '', false, 
		function(){
			if (top.System.LevelQuery.ok == "1") {
				var condition = top.System.LevelQuery.condition;
				if(condition){
					$("#item_tables").val(condition.conditionTablekey);
					$("#item_sql").val(condition.conditionQuerySQL);
				}
		    }
				
	});
}

function onAddItem() {
	g_isEditItem = false;
	var record = $('#list').datagrid('getSelected');
	if (record==null){
		$.messager.alert('提示', "请选择所属查询项目！");
		return;
	}
	$('#item_masterId').val(record.id);
	$('#winItem').window({title: '新增查询项目'});
	$('#winItem').window('open');
	$("#item_name").val('');
	$("#item_enabled").val('');
	$('#item_order_no').val(0);
}

function onEditItem() {
	g_isEditItem = true;
	var record = $('#itemList').treegrid('getSelected');
	if (record==null){
		$.messager.alert('提示', "请选择要编辑的查询项目！");
		return;
	}
	$('#winItem').window({title: '编辑查询项目'});
	$('#winItem').window('open');
	$("#itemForm").form('load', Utils.cloneObject(record,'itemEntity'));
}

// 删除查询类
function dropMaster(record){
	var url = System.rootPath+'/query/quick-query-mgr!deleteMaster.action';
	$.post(url,{masterId:record.id},callBackMaster);
}

function callBackMaster(data,textStatus){
	if (textStatus=='success'){
		// 刷新查询类列表
		reloadMaster();
	}
}

function callBackItem(data,textStatus){
	if (textStatus=='success'){
		// 刷新查询项目
		reloadItem();
	}
}

function reloadMaster(){
	$('#list').datagrid('reload');
}

function reloadItem(){
	$('#itemList').datagrid('reload');
}

// 删除查询项
function dropItem(record){
	var url = System.rootPath+'/query/quick-query-mgr!deleteItem.action';
	$.post(url,{itemId:record.id},callBackItem);
}

function saveMaster(){
	var url;
	if (g_isEditMaster) url = System.rootPath+'/query/quick-query-mgr!updateMaster.action';
	else url = System.rootPath+'/query/quick-query-mgr!createMaster.action';
	var isValid = $("#masterForm").form('validate');
	if (!isValid) {
		System.showErrorMsg('请输入必填项目！');
		return;
	}
	$.post (url,$("#masterForm").serialize(),callBackSaveMaster);
}

function saveItem(){
	var url;
	if (g_isEditItem) url = System.rootPath+'/query/quick-query-mgr!updateItem.action';
	else url = System.rootPath+'/query/quick-query-mgr!createItem.action';
	var isValid = $("#itemForm").form('validate');
	if (!isValid) {
		System.showErrorMsg('请输入必填项目！');
		return;
	}
	$.post (url,$("#itemForm").serialize(),callBackSaveItem);
}

function callBackSaveMaster(data,textStatus){
	if (textStatus=='success'){
		    reloadMaster();
			winMasterClose();
		}else{
			$.messager.alert('提示', "保存失败!");
		}
}

function callBackSaveItem(data,textStatus){
	if (textStatus=='success'){
		    reloadItem();
			winItemClose();
		}else{
			$.messager.alert('提示', "保存失败!");
		}
}

function winMasterClose() {
	$('#winMaster').window('close');
}

function winItemClose() {
	$('#winItem').window('close');
	if (top.System.LevelQuery.ok == "1") {
	    $("#conditionTr").remove();
	}
}