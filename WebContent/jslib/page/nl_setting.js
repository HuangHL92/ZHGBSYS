$(function() {
	$(".panel-title").css("text-align","center");
	
	initNlTypeTable();
	initNlFromTypeTable();
});

// 初始化能力类型表格
function initNlTypeTable() {
	var url = System.rootPath + "/nl/nl!queryPersonNlTypeList.action";
	
	$("#nlTypeTable").datagrid({
		url: url,
		fit: true,
		rownumbers: true,
		singleSelect: true,
		pagination: false,
		idField: 'id',
		columns: [[
			{ field: 'id', hidden: true },
			{ field: 'sorder', title: '序号', align: "center", width: 60 },
			{ field: 'name', title: '名称', align: "center", width: 200	}
		]],
		toolbar: [{
			id: 'btnNlTypeAdd',
			iconCls: 'icon-add',
			text: '新增',
			handler: function() {
				showNlTypeDialog(null)
			}
		},
		'-',{
			id: 'btnNlTypeEdit',
			iconCls: 'icon-edit',
			text: '编辑',
			handler: function() {
				var row = $("#nlTypeTable").datagrid('getSelected');
				if (row) {
					showNlTypeDialog(row);
				}
			}
		},
		'-', {
			id: 'btnNlTypeRemove',
			iconCls: 'icon-remove',
			text: '删除',
			handler: function() {
				var row = $("#nlTypeTable").datagrid('getSelected');
				if (row) {
					deleteNlType(row);
				}
			}
		}],
		onDblClickRow: function(index, row) {
			if (row) {
				showNlTypeDialog(row);
			}
		}
	});
}

// 初始化能力采集来源表格
function initNlFromTypeTable() {
	var url = System.rootPath + "/nl/nl!queryPersonNlFromTypeList.action";
	
	$("#nlFromTypeTable").datagrid({
		url: url,
		fit: true,
		rownumbers: true,
		singleSelect: true,
		pagination: false,
		idField: 'id',
		columns: [[
			{ field: 'id', hidden: true },
			{ field: 'sorder', title: '序号', align: "center", width: 60 },
			{ field: 'name', title: '名称', align: "center", width: 200	},
			{ field: 'score_type', title: '打分类型', align: "center", width: 100, 
				formatter: function(value, row, index) {
					if (row.score_type == '0') {
						return '下拉选择';
					}
					else if (row.score_type == '1') {
						return '勾选';
					}
					else {
						return '';
					}
				}
			},
			{ field: 'default_score', title: '默认分数', align: "center", width: 60	},
			{ field: 'weight', title: '权重值', align: "center", width: 60	}
		]],
		toolbar: [{
			id: 'btnNlFromTypeAdd',
			iconCls: 'icon-add',
			text: '新增',
			handler: function() {
				showNlFromTypeDialog(null);
			}
		},
		'-',{
			id: 'btnNlFromTypeEdit',
			iconCls: 'icon-edit',
			text: '编辑',
			handler: function() {
				var row = $("#nlFromTypeTable").datagrid('getSelected');
				if (row) {
					showNlFromTypeDialog(row);
				}
			}
		},
		'-', {
			id: 'btnNlFromTypeRemove',
			iconCls: 'icon-remove',
			text: '删除',
			handler: function() {
				var row = $("#nlFromTypeTable").datagrid('getSelected');
				if (row) {
					deleteNlFromType(row);
				}
			}
		}],
		onDblClickRow: function(index, row) {
			if (row) {
				showNlFromTypeDialog(row);
			}
		}
	});
}

// 显示能力类型对话框
function showNlTypeDialog(row) {
	$("#nlTypeDialog").show();
	
	$("#nlTypeDialog").dialog({
		shadow:true,
		modal :true,
		resizable:true,
		width: 320,
		height: 200,
		title: row == null ? '新增能力类型' : '编辑能力类型',
		buttons:[{
			text:'确定',
			iconCls:'icon-ok',
			handler:function(){
				var nlType = getNlTypeData();
				if (nlType.name == '') {
					$.messager.alert("提示", "请输入名称！");
					return;
				}
				
				saveNlType(nlType);
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$('#nlTypeDialog').dialog('close');
			}
		}],
		onBeforeOpen : function () {
			$(".nlTypeItems").val('');
			
			if (row != null) {
				$("#nlTypeSorder").val(row.sorder);
				$("#nlTypeName").val(row.name);
				$("#nlTypeId").val(row.id);
			}
			else {
				$("#nlTypeId").val('');
			}
		}
	});
}

// 显示能力采集来源对话框
function showNlFromTypeDialog(row) {
	$("#nlFromTypeDialog").show();
	
	$("#nlFromTypeDialog").dialog({
		shadow:true,
		modal :true,
		resizable:true,
		width: 320,
		height: 300,
		title: row == null ? '新增能力采集来源' : '编辑能力采集来源',
		buttons:[{
			text:'确定',
			iconCls:'icon-ok',
			handler:function(){
				var nlFromType = getNlFromTypeData();
				if (nlFromType.name == '') {
					$.messager.alert("提示", "请输入名称！");
					return;
				}
				
				if (!validScore(nlFromType.default_score)) {
					$.messager.alert("提示", "默认分数必须在0到10之间！");
					return;
				}
				else {
					if (nlFromType.score_type == '1' && nlFromType.default_score == 0) {
						$.messager.alert("提示", "打勾类型，默认分数必须大于0，小于等于10！");
						return;
					}
				}
				
				if (!validWeight(nlFromType.weight)) {
					$.messager.alert("提示", "权重值必须大于0！");
					return;
				}
				
				$.messager.confirm('确定保存', '确定要保存？' + '<br/><span style="font-weight:bold;color:red;">注意：如更改权重值或打分类型是打勾的缺省分数，请手动更新所有干部的能力值！</span>', function(isOk) {
					if (!isOk) return;
					
					saveNlFromType(nlFromType);
				});
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$('#nlFromTypeDialog').dialog('close');
			}
		}],
		onBeforeOpen : function () {
			$(".nlFromTypeItems").val('');
			
			if (row != null) {
				$("#nlFromTypeSorder").val(row.sorder);
				$("#nlFromTypeName").val(row.name);
				$("#nlFromTypeId").val(row.id);
				$("#nlFromTypeScoreType").val(row.score_type);
				$("#nlFromTypeDefaultScore").val(row.default_score);
				$("#nlFromTypeWeight").val(row.weight);
			}
			else {
				$("#nlFromTypeId").val('');
				$("#nlFromTypeScoreType").val('0');
			}
		}
	});
}

// 获取输入的能力类型数据
function getNlTypeData() {
	var data = {};
	var isCreate = $("#nlTypeId").val() == '';
	
	data.id = $("#nlTypeId").val();
	data.name = $("#nlTypeName").val();
	data.sorder = $("#nlTypeSorder").val();
	if (isNaN(data.sorder) || data.sorder == '' || data.sorder < 0) {
		data.sorder = getMaxSorder("nlTypeTable");
	}
	
	return data;
}

// 获取输入的能力采集来源数据
function getNlFromTypeData() {
	var data = {};
	var isCreate = $("#nlFromTypeId").val() == '';
	
	data.id = $("#nlFromTypeId").val();
	data.name = $("#nlFromTypeName").val();
	data.sorder = $("#nlFromTypeSorder").val();
	data.score_type = $("#nlFromTypeScoreType").val();
	data.default_score = $("#nlFromTypeDefaultScore").val();
	data.weight = $("#nlFromTypeWeight").val();
	
	if (isNaN(data.sorder) || data.sorder == '' || data.sorder < 0) {
		data.sorder = getMaxSorder("nlFromTypeTable");
	}
	
	if (isNaN(data.default_score) || data.default_score == '') {
		data.default_score = 0;
	}
	
	if (isNaN(data.weight) || data.weight == '') {
		data.weight = 0;
	}
	
	return data;
}

// 保存能力类型记录
function saveNlType(nlType) {
	var nlTypeData = encodeURI($.toJSON([nlType]));
				
	System.openLoadMask("#contentPanel", "正在保存...");

	$.ajax({
		type: "POST",
		url: System.rootPath+ '/nl/nl!savePersonNlType.action',
		data: { 
			paramNlTypeData: nlTypeData
		},
		success:function(json){
			System.closeLoadMask("#contentPanel");
			
			try {
				if (json.indexOf("保存成功") >= 0) {							
					$.messager.alert('提示', '保存成功!');	
					
					// 刷新数据
					$("#nlTypeTable").datagrid('reload');
					
					$('#nlTypeDialog').dialog('close');
				}
				else {
					alert('保存失败，错误信息：' + json);						
				}
			}
			catch (ex) {
				alert('提示', '保存失败，错误信息：' + ex);						
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			System.closeLoadMask("#contentPanel");
			$.messager.alert('提示', "保存失败,请重试!"+textStatus+errorThrown);
		}
	});	
}

// 删除能力类型
function deleteNlType(row) {
	$.messager.confirm('确定删除', '确定要删除选中的记录？<br/>名称：' + row.name, function(isOk) {
		if (!isOk) return;
		
		System.openLoadMask("#contentPanel", "正在删除，请稍后...");
		
		$.ajax({
			type: "POST",
			url: System.rootPath+ '/nl/nl!deletePersonNlType.action',
			data: { 
				id: row.id
			},
			success:function(json){
				System.closeLoadMask("#contentPanel");
				
				try {
					if (json.indexOf("删除成功") >= 0) {							
						$.messager.alert('提示', '删除成功!');	
						
						// 刷新数据
						$("#nlTypeTable").datagrid('reload');					
					}
					else {
						alert('删除失败，错误信息：' + json);						
					}
				}
				catch (ex) {
					alert('提示', '删除失败，错误信息：' + ex);						
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				System.closeLoadMask("#contentPanel");
				$.messager.alert('提示', "删除失败,请重试!"+textStatus+errorThrown);
			}
		});		
	});
}

// 保存能力采集来源记录
function saveNlFromType(nlFromType) {
	var nlFromTypeData = encodeURI($.toJSON([nlFromType]));
				
	System.openLoadMask("#contentPanel", "正在保存...");

	$.ajax({
		type: "POST",
		url: System.rootPath+ '/nl/nl!savePersonNlFromType.action',
		data: { 
			paramNlFromTypeData: nlFromTypeData
		},
		success:function(json){
			System.closeLoadMask("#contentPanel");
			
			try {
				if (json.indexOf("保存成功") >= 0) {							
					$.messager.alert('提示', '保存成功!');	
					
					// 刷新数据
					$("#nlFromTypeTable").datagrid('reload');
					
					$('#nlFromTypeDialog').dialog('close');
				}
				else {
					alert('保存失败，错误信息：' + json);						
				}
			}
			catch (ex) {
				alert('提示', '保存失败，错误信息：' + ex);						
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			System.closeLoadMask("#contentPanel");
			$.messager.alert('提示', "保存失败,请重试!"+textStatus+errorThrown);
		}
	});	
}

// 删除能力采集来源
function deleteNlFromType(row) {
	$.messager.confirm('确定删除', '确定要删除选中的记录？<br/>名称：' + row.name + '<br/><span style="font-weight:bold;color:red;">注意：删除后，必须请手动更新所有干部的能力值！</span>', function(isOk) {
		if (!isOk) return;
		
		System.openLoadMask("#contentPanel", "正在删除，请稍后...");
		
		$.ajax({
			type: "POST",
			url: System.rootPath+ '/nl/nl!deletePersonNlFromType.action',
			data: { 
				id: row.id
			},
			success:function(json){
				System.closeLoadMask("#contentPanel");
				
				try {
					if (json.indexOf("删除成功") >= 0) {							
						$.messager.alert('提示', '删除成功!');	
						
						// 刷新数据
						$("#nlFromTypeTable").datagrid('reload');					
					}
					else {
						alert('删除失败，错误信息：' + json);						
					}
				}
				catch (ex) {
					alert('提示', '删除失败，错误信息：' + ex);						
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				System.closeLoadMask("#contentPanel");
				$.messager.alert('提示', "删除失败,请重试!"+textStatus+errorThrown);
			}
		});		
	});
}

function getMaxSorder(id) {
	var rows = $("#" + id).datagrid('getRows');
	if (rows != null) {
		var max = 0;
		for (var i = 0; i < rows.length; i++) {
			if (rows[i].sorder > max) max = rows[i].sorder;
		}
		
		return max + 1;
	}
	else {
		return 1;
	}
}

function validScore(score) {
	if (score >= 0 && score <= 10) {
		return true;
	}
	else {
		return false;
	}
}

function validWeight(weight) {
	return weight > 0;
}