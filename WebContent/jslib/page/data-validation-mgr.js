var g_isEdit = false;
$(function(){
	
	$("#mgrList").datagrid({
		nowrap: true,
		singleSelect:true,					
		fit: true,
		idField:'id',
		columns:[[
            {field:'id',hidden:true},
			{field:'name', title:'项目名称',width:300},
			{field:'sql', title:'检校sql',width:350},
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
				onAdd();
			}
		},'-', {
			iconCls:'icon-edit',
			text:'编辑',
			handler:function(){
				onEdit();
			}
		},'-',{
			iconCls:'icon-remove',
			text:'删除',
			handler:function(){
				var record = $('#mgrList').treegrid('getSelected');
				if (record==null){
					$.messager.alert('提示', "请选择要删除的检校项目！");
					return;
				}							
				
				$.messager.confirm('确认', "是否确定要删除该检校项目? <br/><br/>检校项目名称：" + record.name, function (isOk) {
					if (isOk) {
						drop(record);
					}
				});
			}
		}],
		onLoadSuccess: function() {
			System.closeLoadMask('#pageWin');
		}
	});
	initList();
});

function initList () {
    var url = System.rootPath+'/validation/data-validation-mgr!getList.action';
    var opts = $("#mgrList").datagrid('options');
    //opts.pageNumber = parseInt($("#page").val(), 10);
    opts.pageSize = parseInt($("#rows").val(), 10);
    opts.pageList = [10, 20, 30];
	opts.url = url;
    $("#mgrList").datagrid(opts);
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

function onAdd() {
	g_isEdit = false;
	$('#winItem').window({title: '新增检校项目'});
	$('#winItem').window('open');
	//$("#name").val('');
	$("#name").textbox("setValue", "");
	$("#enabled").val('true');
	$('#order_no').val(1);
	$("#sql").textbox("setValue", "");
}

function onEdit() {
	g_isEdit = true;
	var record = $('#mgrList').treegrid('getSelected');
	if (record==null){
		$.messager.alert('提示', "请选择要编辑的检校项目！");
		return;
	}
	$('#winItem').window({title: '编辑检校项目'});
	$('#winItem').window('open');
	$("#itemForm").form('load', Utils.cloneObject(record,'entity'));
	$("#enabled").combobox('setValue',record.enabled.toString());
}

function callBack(data,textStatus){
	if (textStatus=='success'){
		// 刷新查询项目
		reload();
	}
}

function reload(){
	$('#mgrList').datagrid('reload');
}

// 删除检校项目
function drop(record){
	var url = System.rootPath+'/validation/data-validation-mgr!delete.action';
	$.post(url,{id:record.id},callBack);
}

function save(){
	var url;
	if (g_isEdit) url = System.rootPath+'/validation/data-validation-mgr!update.action';
	else url = System.rootPath+'/validation/data-validation-mgr!create.action';
	var isValid = $("#itemForm").form('validate');
	if (!isValid) {
		System.showErrorMsg('请输入必填项目！');
		return;
	}
	$.post (url,$("#itemForm").serialize(),callBackSave);
}

function callBackSave(data,textStatus){
	if (textStatus=='success'){
		    reload();
			winClose();
		}else{
			$.messager.alert('提示', "保存失败!");
		}
}

function winClose() {
	$('#winItem').window('close');
}