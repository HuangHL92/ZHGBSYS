var isNew = false;
$(function(){
	$(".panel-title").css("text-align","center");
	
	init();
});
			
function init() {
	$("#formList").datagrid({
		url: System.rootPath + '/customform/custom-form!getFormByPage.action',
		fit: true,
		rownumbers: true,
		pagination: true,
		singleSelect: true,
		border: true,
		idField: 'id',
		columns:[[
			{field:'name', title:'表单名称', sortable:true, width:300},
			{field:'status',title:'表单状态', sortable:true, align:'center', width:60,
				formatter: function(value, row, index) {
					if (value == '0') return '启用';
					else return '禁用';
				}
			},
			{field:'order_no', title:'排序序号', sortable:true, width:60}
			
		]],
		toolbar: [{
			iconCls: 'icon-add',
			text: '添加',
			handler: function(){
				openFormDialog(null);
			}
		},'-',{
			iconCls: 'icon-edit',
			text: '编辑',
			handler: function(){
				var row = $("#formList").datagrid('getSelected');
				if (row != null) {
					openFormDialog(row);
				}
			}
		},'-',{
			iconCls: 'icon-remove',
			text: '删除',
			handler: function(){
				var row = $("#formList").datagrid('getSelected');
				if (row != null) {
					deleteForm(row);
				}
			}
		},'-',{
			iconCls: 'icon-template',
			text: '表单设计',
			handler: function(){
				var row = $("#formList").datagrid('getSelected');
				if (row != null) {
					var url = System.rootPath + '/customform/custom-form!design.action?id=' + row.id;
					window.open(url);
				}
			}
		}],
		onLoadSuccess: function(data) {
			$("#formList").datagrid('clearSelections');
		}
	});
}


function openFormDialog(data) {
	$("#formDlg").show();
	$('#formDlg').dialog({
		title: data == null ? '添加表单' : '编辑表单',
		width: 500,
		height: 240,
		modal: true,
		buttons:[{
			iconCls:'icon-ok',
			text:'确定',
			handler:function(){
				saveForm();
			}
		},{
			iconCls:'icon-cancel',
			text:'取消',
			handler:function(){	
				$("#formDlg").dialog('close');
			}
		}],
		onOpen : function () {
			if (data == null) {
				isNew = true;
				$("#formDlgForm").form('reset');
			}
			else {
				isNew = false;
				$("#formDlgForm").form('load', Utils.cloneObject(data, 'form'));
			}
		}
	});
}

function saveForm() {
	var url;
	if (isNew) {
		url = System.rootPath + '/customform/custom-form!createForm.action';
	}
	else url = System.rootPath + '/customform/custom-form!updateForm.action';
	
	var isValid = $("#formDlgForm").form('validate');
	if (!isValid) {
		return;
	}
	
	System.post(url, $("#formDlgForm").serialize(), function(data) {
		$("#formDlg").dialog('close');
		$("#formList").datagrid('reload');
	});
}

function deleteForm(data) {	
	System.showConfirmMsg('确定删除表单：' + data.name + '？', function() {
		var url = System.rootPath + '/customform/custom-form!deleteForm.action';
		System.post(url, { id : data.id}, function(data) {
			$("#formList").datagrid('reload');
		}, function(data) {
			$("#formList").datagrid('reload');
		});	
	});
}