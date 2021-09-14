var isNew = false;
$(function(){
	$(".panel-title").css("text-align","center");
	
	init();
});
			
function init() {
	$("#templateList").datagrid({
		url: System.rootPath + '/elementtemplate/element-template!getTemplateList.action',
		fit: true,
		rownumbers: true,
		pagination: false,
		singleSelect: true,
		border: true,
		idField: 'template_id',
		columns:[[
			{field:'template_name', title:'模板名称', sortable:false, width:150},
			{field:'template_status',title:'模板状态', sortable:false, align:'center', width:60,
				formatter: function(value, row, index) {
					if (value == '0') return '启用';
					else return '禁用';
				}
			}
		]],
		toolbar: [{
			iconCls: 'icon-add',
			text: '添加',
			//disabled: !canCreate,
			handler: function(){
				openTemplateDialog(null);
			}
		},'-',{
			iconCls: 'icon-edit',
			text: '编辑',
			//disabled: !canUpdate,
			handler: function(){
				var row = $("#templateList").datagrid('getSelected');
				if (row != null) {
					openTemplateDialog(row);
				}
			}
		},'-',{
			iconCls: 'icon-remove',
			text: '删除',
			//disabled: !canDelete,
			handler: function(){
				var row = $("#templateList").datagrid('getSelected');
				if (row != null) {
					deleteTemplate(row);
				}
			}
		},'-',{
			iconCls: 'icon-sort',
			text: '排序',
			//disabled: !canOrder,
			handler: function(){
				showTemplateOrderDialog();
			}
		}],
		onLoadSuccess: function(data) {
			$("#templateList").datagrid('clearSelections');
			loadTables();
		},
		onSelect: function(index, row) {
			loadTables();
		}
	});
	
	$("#tableGrid").datagrid({
		fit: true,
		idField:'table_name',
		singleSelect: true,
		columns:[[
			{field:'table_name_cn',title:'信息集名称',width:300},					
			{field:'can_read',title:'浏览',width:50, 
				formatter:function(value,rowData,rowIndex) {
					var id = "tableRead" + rowIndex;
					var html = "<input ";
					if (rowData.readable == 1) {
						html += " checked='true' ";
					}
					html += " onclick='onTableRead(\"" + rowIndex + "\");' class='tableRead' table='" + rowData.table_name + "' type='checkbox' name='tableType' id='" + id + "'/>";
					return html;
				}
			},
			{field:'can_edit',title:'维护',width:50, 
				formatter:function(value,rowData,rowIndex) {
					var id = "tableEdit" + rowIndex;
					html = "<input ";
					if (rowData.editable == 1) {
						html += " checked='true' ";
					}
					html += "onclick='onTableEdit(\"" + rowIndex + "\");' class='tableEdit' table='" + rowData.table_name + "' type='checkbox' name='tableType' id='" + id + "'/>";
					return html;
				}
			}
		]],
		toolbar:[{
			iconCls:'icon-add',
			text:'全选',
			handler:function(){
				$(".tableRead").prop('checked', true);
				$(".tableEdit").prop('checked', true);
			}
		},'-',{
			iconCls:'icon-remove',
			text:'取消选择',
			handler:function(){
				$(".tableRead").prop('checked', false);
				$(".tableEdit").prop('checked', false);
			}
		}],
		onSelect: function(rowIndex, rowData) {
			// 屏蔽选择
			$("#tableGrid").datagrid('unselectRow', rowIndex);
		}
	});
	
	$("#fieldGrid").datagrid({
		fit: true,
		idField:'id',
		singleSelect: true,
		columns:[[
			{field:'table_name_cn',title:'信息集名称',width:300},
			{field:'field_name_cn',title:'字段名称',width:300},
			{field:'can_read',title:'浏览',width:50, 
				formatter:function(value,rowData,rowIndex) {
					var id = "fieldRead" + rowIndex;
					var html = "<input ";
					if (rowData.readable == 1) {
						html += " checked='true' ";
					}
					html += " onclick='onFieldRead(\"" + rowIndex + "\");' class='fieldRead' table='" + rowData.table_name + "' field='" + rowData.field_dbname + "' type='checkbox' name='fieldType' id='" + id + "'/>";
					return html;
				}
			},
			{field:'can_edit',title:'维护',width:50, 
				formatter:function(value,rowData,rowIndex) {
					if (rowData.table_editable == 1) {
						var id = "fieldEdit" + rowIndex;
						var html = "<input ";
						if (rowData.editable == 1) {
							html += " checked='true' ";
						}
						html += " onclick='onFieldEdit(\"" + rowIndex + "\");' class='fieldEdit' table='" + rowData.table_name + "' field='" + rowData.field_dbname + "' type='checkbox' name='fieldType' id='" + id + "'/>";
						return html;
					}else {
					   return '';
					}
				}
			}
		]],
		toolbar:[{
			iconCls:'icon-add',
			text:'全选',
			handler:function(){
				$(".fieldRead").prop('checked', true);
				$(".fieldEdit").prop('checked', true);
			}
		},'-',{
			iconCls:'icon-remove',
			text:'取消选择',
			handler:function(){
				$(".fieldRead").prop('checked', false);
				$(".fieldEdit").prop('checked', false);
			}
		}],
		onSelect: function(rowIndex, rowData) {
			// 屏蔽选择
			$("#fieldGrid").datagrid('unselectRow', rowIndex);
		}
	});
	
	$("#contentTabs").tabs({
		fit: true,
		onSelect: function(title, index) {
			// 切换到信息项标签，永远重新获取数据
			if (index == 1) {
				loadFields();
			}
		}
	});
}

function loadTables() {
	// 清除信息集的选择
	$("[name = tableType]:checkbox").prop('checked', false);
	// 清除所有信息项
	$("#fieldGrid").datagrid('loadData', []);
	// 选中信息集标签
	$("#contentTabs").tabs('select', 0);
	
	var templateId = '*DUMMY*';
	var template = $("#templateList").datagrid('getSelected');
	if (template != null) {
		templateId = template.template_id;
	}
	
	// 载入
	var url = System.rootPath + '/elementtemplate/element-template!getTemplateTables.action?templateId=' + templateId;
	var opts = $("#tableGrid").datagrid('options');
	
	opts.url = url;
	$("#tableGrid").datagrid(opts);
}

function loadFields() {
	// 清除所有信息项
	$("#fieldGrid").datagrid('loadData', []);
	
	var templateId = '*DUMMY*';
	var template = $("#templateList").datagrid('getSelected');
	if (template != null) {
		$("#fieldSaveDiv").show();
		templateId = template.template_id;
	}
	else {
		$("#fieldSaveDiv").hide();
	}
	
	// 载入
	var url = System.rootPath + '/elementtemplate/element-template!getTemplateFields.action?templateId=' + templateId;
	var opts = $("#fieldGrid").datagrid('options');
	
	opts.url = url;
	$("#fieldGrid").datagrid(opts);
}

function openTemplateDialog(data) {
	$("#templateDlg").show();
	$('#templateDlg').dialog({
		title: data == null ? '添加模板' : '编辑模板',
		width: 400,
		height: 200,
		modal: true,
		buttons:[{
			iconCls:'icon-ok',
			text:'确定',
			handler:function(){
				saveTemplate();
			}
		},{
			iconCls:'icon-cancel',
			text:'取消',
			handler:function(){	
				$("#templateDlg").dialog('close');
			}
		}],
		onOpen : function () {
			if (data == null) {
				isNew = true;
				$("#templateDlgForm").form('reset');
			}
			else {
				isNew = false;
				$("#templateDlgForm").form('load', Utils.cloneObject(data, 'template'));
			}
		}
	});
}

function saveTemplate() {
	var url;
	if (isNew) {
		url = System.rootPath + '/elementtemplate/element-template!createTemplate.action';
	}
	else url = System.rootPath + '/elementtemplate/element-template!updateTemplate.action';
	
	var isValid = $("#templateDlgForm").form('validate');
	if (!isValid) {
		return;
	}
	
	System.post(url, $("#templateDlgForm").serialize(), function(data) {
		$("#templateDlg").dialog('close');
		$("#templateList").datagrid('reload');
	});
}

function deleteTemplate(data) {	
	System.showConfirmMsg('确定删除模板：' + data.template_name + '？', function() {
		var url = System.rootPath + '/elementtemplate/element-template!deleteTemplate.action';
		System.post(url, { templateId : data.template_id}, function(data) {
			$("#templateList").datagrid('reload');
		}, function(data) {
			$("#templateList").datagrid('reload');
		});	
	});
}

function showTemplateOrderDialog() {
	var url = System.rootPath + '/elementtemplate/element-template!orderTemplate.action';
	$("#orderDlg").show();
	$("#orderDlg").dialog({
		modal: true,
		title: '模板排序',
		width: 600,
		height: 500,
		onOpen: function() {
			$("#orderIframe").attr('src', url);
		},
		onClose: function() {
			$("#templateList").datagrid('reload');
			$("#orderIframe").attr('src', '');
		}
	});
}

function onTableRead(id) {
	if (!$("#tableRead" + id).prop('checked')) {
		// 同时取消维护
		$("#tableEdit" + id).prop('checked', false);
	}
}

function onTableEdit(id) {
	if ($("#tableEdit" + id).prop('checked')) {
		// 同时选择浏览
		$("#tableRead" + id).prop('checked', true);
	}
}

function onFieldRead(id) {
	if (!$("#fieldRead" + id).prop('checked')) {
		// 同时取消维护
		$("#fieldEdit" + id).prop('checked', false);
	}
}

function onFieldEdit(id) {
	if ($("#fieldEdit" + id).prop('checked')) {
		// 同时选择浏览
		$("#fieldRead" + id).prop('checked', true);
	}
}

//获取选中的信息集
function getSelectedTables() {
	var tableName= '';
	var arrReadable='';
	var arrEditable='';
	$(".tableRead").each(function() {
		if ($(this).prop('checked')) {
			if (tableName == '') tableName = $(this).attr('table');
			else tableName = tableName + "," + $(this).attr('table');
			if (arrReadable == '') arrReadable = '1';
			else arrReadable = arrReadable + "," + '1';
		}
	});
	$("[name = tableType]:checkbox").each(function(i){
		if ($("#tableRead" + i).prop('checked') && $("#tableEdit" + i).prop('checked')) {
			if (arrEditable == '') arrEditable = '1';
			else arrEditable = arrEditable + "," + '1';
		}else if ($("#tableRead" + i).prop('checked') && !$("#tableEdit" + i).prop('checked')){
			if (arrEditable == '') arrEditable = '0';
			else arrEditable = arrEditable + "," + '0';
		}
	});
	
	return { tableName: tableName, arrReadable: arrReadable, arrEditable: arrEditable};
}

//获取选中的字段
function getSelectedFields() {
	var tableName='';
	var fieldName= '';
	var arrReadable='';
	var arrEditable='';
	$(".fieldRead").each(function() {
		if ($(this).prop('checked')) {
			if (tableName == '') tableName = $(this).attr('table');
			else tableName = tableName + "," + $(this).attr('table');
			if (fieldName == '') fieldName = $(this).attr('field');
			else fieldName = fieldName + "," + $(this).attr('field');
			if (arrReadable == '') arrReadable = '1';
			else arrReadable = arrReadable + "," + '1';
		}
	});
	$("[name = fieldType]:checkbox").each(function(i){
		if ($("#fieldRead" + i).prop('checked') && $("#fieldEdit" + i).prop('checked')) {
			if (arrEditable == '') arrEditable = '1';
			else arrEditable = arrEditable + "," + '1';
		}else if ($("#fieldRead" + i).prop('checked') && !$("#fieldEdit" + i).prop('checked')){
			if (arrEditable == '') arrEditable = '0';
			else arrEditable = arrEditable + "," + '0';
		}
	});
	
	return { tableName: tableName, fieldName: fieldName, arrReadable: arrReadable, arrEditable: arrEditable};
}

function onTableSave() {
	var selectedTables = getSelectedTables();
	var selectTemplate = $("#templateList").datagrid('getSelected');
	var url = System.rootPath + '/elementtemplate/element-template!createTemplateTable.action';
	if (selectTemplate != null) {
		$.messager.confirm('确认',  '确定保存' + selectTemplate.template_name + '的信息集配置?', function(isOk) {
			if (isOk) {
				System.openLoadMask("#mainDiv", "正在保存...");
				System.post(url, 
						{ "table.template_id": selectTemplate.template_id,
					      "table.table_name": selectedTables.tableName,
					      "table.arrReadable": selectedTables.arrReadable,
					      "table.arrEditable": selectedTables.arrEditable}, 
				   function(data) {
						System.showInfoMsg('保存成功！');
						System.closeLoadMask("#mainDiv");
				}, function(data) {
						System.closeLoadMask("#mainDiv");
				});
			}
		});
	}else {
		$.messager.alert("操作提示", "请选择模板");
	}
}

function onFieldSave() {
	var selectedFields = getSelectedFields();
	var selectTemplate = $("#templateList").datagrid('getSelected');
	var url = System.rootPath + '/elementtemplate/element-template!createTemplateField.action';
	if (selectTemplate != null) {
		$.messager.confirm('确认',  '确定保存' + selectTemplate.template_name + '的信息项配置?', function(isOk) {
			if (isOk) {
				System.openLoadMask("#mainDiv", "正在保存...");
				System.post(url, 
						{ "field.template_id": selectTemplate.template_id,
					      "field.field_name": selectedFields.fieldName,
						  "field.table_name": selectedFields.tableName,
					      "field.arrReadable": selectedFields.arrReadable,
					      "field.arrEditable": selectedFields.arrEditable}, 
				   function(data) {
					   System.showInfoMsg('保存成功！');
					   System.closeLoadMask("#mainDiv");
				}, function(data) {
					   System.closeLoadMask("#mainDiv");
				});
			}
		});
	}else {
		$.messager.alert("操作提示", "请选择模板");
	}
}