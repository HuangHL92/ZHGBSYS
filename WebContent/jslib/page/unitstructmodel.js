var setting = {
				check: {
					enable: true,
					nocheckInherit: true,
					chkboxType: { "Y" : "", "N" : "" } 
				},
				data: {
					key : {
						name: 'text'
					}
				}
			};
			
var editor_controls = "bold italic underline strikethrough | font size " +
			"style | color highlight removeformat | bullets numbering | outdent " +
			"indent | alignleft center alignright justify | undo redo | cut copy paste pastetext | source";
var editor_fonts = "宋体,仿宋,黑体,楷体,Arial,Arial Black,Comic Sans MS,Courier New,Narrow,Garamond," +
			"Georgia,Impact,Sans Serif,Serif,Tahoma,Trebuchet MS,Verdana";

var const_struct_type_grid_columns = [[
	{field:'sorder',title:'序号',width:50, sortable:true},
	{field:'struct_desc',title:'项目',width:150},
	{field:'amount',title:'数量',width:100},
	{field:'one_ticket',title:'一票否决',width:100, formatter: function(value,rowData,rowIndex) {
		if (value == "1") return "是"; else return "否";
	}}
]];

var const_struct_type_grid_columns_zs = [[
	{field:'sorder',title:'序号',width:50, sortable:true},
	{field:'struct_desc',title:'项目',width:100},
	{field:'tag1',title:'职务类别',width:100, 
		formatter: function(value,rowData,rowIndex) {
			if (value == "1") return "领导职务正职";
			else if (value == "2") return "领导职务副职";
			else if (value == "3") return "非领导职务";
			else return "";
		}
	},
	{field:'tag2',title:'职务职级',width:150, 
		formatter: function(value,rowData,rowIndex) {
			if (value == "1") return "正部级";
			else if (value == "2") return "副部级";
			else if (value == "3") return "部级";
			
			else if (value == "4") return "正厅级";
			else if (value == "5") return "副厅级";
			else if (value == "6") return "厅级";
			
			else if (value == "7") return "正处级";
			else if (value == "8") return "副处级";
			else if (value == "9") return "处级";
			else return "";
		}
	},
	{field:'amount',title:'数量',width:100},
	{field:'struct_remark',title:'备注',width:100},
	{field:'one_ticket',title:'一票否决',width:100, formatter: function(value,rowData,rowIndex) {
		if (value == "1") return "是"; else return "否";
	}}
]];

var const_position_grid_columns = [[
	{field:'sorder',title:'序号',width:50, sortable:true},
	{field:'position_name',title:'职位名称',width:150},
	{field:'position_work',title:'职位分工',width:150},
	//{field:'responsibility',title:'主要工作责任',width:150},
	//{field:'ability_requirement',title:'能力要求',width:150},
	{field:'condition',title:'资格条件',width:150},
	{field:'personnames',title:'配备情况', width:150}
]];

var const_condition_grid_columns = [[
	{field:'sorder',title:'序号',width:50, sortable:true},
	{field:'condition_name',title:'条件名称',width:120},
	{field:'condition_querysqlcn',title:'条件描述',width:300}	
]];

var const_person_grid_columns = [[
    {field:'ck',checkbox:true},
	{field:'xingm',title:'姓名',width:100},
	{field:'zhiw',title:'职务',width:300}	
]];


var g_structTypes = [0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 11, 4, 12, 13]; // 所有支持的自然结构
var g_structTypesTabs = [0, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]; // 自然结构所在的标签页索引
var g_levels = [-1, 0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 11, 4, 12, 13]; // 结构调整项目
//var g_structTypeTabIndex = 3; // 自然结构tab的起始索引

var g_totalAjax = g_structTypes.length + 1; // 总共有多少个Ajax调用，保存的时候，必须判断此值是否为0，如果不为0则说明有ajax调用不成功，此时如果保存，可能会丢失数据。
var g_zwmcData; // 职务名称本地缓存

$(function(){
	$(".panel-title").css("text-align","center");
	initEditors();
	initPositionGrid();
	initStructGrids();
});

// 初始化各个富文本字段编辑器
function initEditors() {
	var responsibilityEditor = $('#responsibility').cleditor({
		width: 630, 
		height: 150, 
		controls: editor_controls,
		fonts: editor_fonts
	});
	
	var assessmentEditor = $('#assessment').cleditor({
		width: 630, 
		height: 150, 
		controls: editor_controls,
		fonts: editor_fonts
	});
	
	var suggestionEditor = $('#suggestion').cleditor({
		width: 630, 
		height: 150, 
		controls: editor_controls,
		fonts: editor_fonts
	});
}

// ----------- 职位说明 开始
// 初始化职位说明表格
function initPositionGrid() {
	var url = System.rootPath + "/unitstructmodel/unit-struct-model!queryPositions.action?unitCode=" + unitCode;
		
	$("#positionGrid").datagrid({
		fit: true,
		nowrap: true,
		singleSelect:true,
		remoteSort: false,
		url: url,
		idField: 'id',	
		columns: const_position_grid_columns,		
		toolbar:[
		{
			iconCls:'icon-add',
			text:'添加',
			handler:function(){
				openPositionDialog(null);
			}
		},'-',
		{
			iconCls:'icon-edit',
			text:'编辑',
			handler:function(){
				var row = $("#positionGrid").datagrid('getSelected');
				if (row != null) {
					openPositionDialog(row);
				}
			}
		},'-',
		{
			iconCls:'icon-remove',
			text:'删除',
			handler:function(){
				var row = $("#positionGrid").datagrid('getSelected');
				if (row != null) {
					$.messager.confirm('确认', '确定删除职位:' + row.position_name + '?', function(isOk) {
						if (isOk) {
							var index = $("#positionGrid").datagrid('getRowIndex', row);
							$("#positionGrid").datagrid('deleteRow', index);
						}
					});
				}
			}
		}
		],
		onLoadSuccess: function() {
			g_totalAjax--;
			if (g_totalAjax <= 0) parent.closeMask();	
		},
		onDblClickRow: function(index, row) {
			if (row) {
				openPositionDialog(row);
			}
		}		
	});
}

// 初始化职位资格条件表格
function initConditionGrid(positionData) {
	$("#conditionGrid").datagrid({
		fit: true,
		nowrap: true,
		singleSelect:true,
		remoteSort: false,
		idField: 'id',	
		data: positionData == null ? [] : positionData.conditions,
		columns: const_condition_grid_columns,		
		toolbar:[
		{
			iconCls:'icon-add',
			text:'添加',
			handler:function(){
				openConditionDialog(null);
			}
		},'-',
		{
			iconCls:'icon-edit',
			text:'编辑',
			handler:function(){
				var row = $("#conditionGrid").datagrid('getSelected');
				if (row != null) {	
					openConditionDialog(row);
				}
			}
		},'-',
		{
			iconCls:'icon-remove',
			text:'删除',
			handler:function(){
				var row = $("#conditionGrid").datagrid('getSelected');
				if (row != null) {
					$.messager.confirm('确认', '确定删除条件:' + row.condition_name + '?', function(isOk) {
						if (isOk) {
							var index = $("#conditionGrid").datagrid('getRowIndex', row);
							$("#conditionGrid").datagrid('deleteRow', index);
						}
					});
				}
			}
		}
		],
		onDblClickRow: function(index, row) {
			if (row) {
				openConditionDialog(row);
			}
		}		
	});
	
	// 必须在初始化后载入本地数据...
	try {
		$("#conditionGrid").datagrid('loadData', positionData == null ? [] : positionData.conditions);
	}
	catch (e) {
	}
}

// 打开职位设定对话框
function openPositionDialog(rowData) {
	$("#positionDialog").show();
	
	$("#positionDialog").dialog({
		shadow:true,
		modal :true,
		resizable:true,
		width: 1124,
		height: 560,
		title: rowData == null ? '添加职位设定' : '编辑职位设定',
		buttons:[{
			text:'确定',
			iconCls:'icon-ok',
			handler:function(){
				var position = getPositionObject(rowData);
				if (position.position_name == '') {
					$.messager.alert('提示', '请输入职务名称！');
					return;
				}
				
				if (!validQuickConditions(position.quickConditions)) {
					return;
				}
				
				if (rowData == null) {
					$("#positionGrid").datagrid('appendRow', position);
				}
				else {
					// 更新
					var row = $("#positionGrid").datagrid('getSelected');
					var index = $("#positionGrid").datagrid('getRowIndex', row);
					$("#positionGrid").datagrid('updateRow', {
						index: index,
						row: position
					});
				}
				
				$('#positionDialog').dialog('close');							
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$('#positionDialog').dialog('close');
			}
		}],
		onBeforeOpen : function () {
			initQuickConditions(rowData);
			$(".posItem").val('');
			
			if (rowData != null) {
				$("#posSort").val(rowData.sorder);
				$("#posPositionName").val(rowData.position_name);
				$("#posPositionWork").val(rowData.position_work);
				$("#posCondition").val(rowData.condition);
				$("#posPersonnames").val(rowData.personnames);
				$("#posPersons").val(getPersoncodes(rowData.persons));
			}
			
			initConditionGrid(rowData);
			$("#posPositionName").focus();
			
		}
	});
}

// 获取职位对话框中获取职位说明对象
function getPositionObject(rowData) {
	var position = {};
	if (rowData == null) {
		position.id = Math.ceil(Math.random() * 100000) * -1; // 产生负数id
	}
	else {
		position.id = rowData.id;
	}
	
	position.unit_code = unitCode;
	position.sorder = $("#posSort").val();
	position.position_name = $("#posPositionName").val();
	position.position_work = $("#posPositionWork").val();
	position.condition = $("#posCondition").val();
	position.personnames = $("#posPersonnames").val();
	if (isNaN(position.sorder) || position.sorder == '') {
		position.sorder = getMaxSorder('positionGrid');
	}
	
	// 获取特殊条件
	var conditions = $("#conditionGrid").datagrid('getRows');
	position.conditions = conditions;
	
	// 获取快速条件
	position.quickConditions = getQuickConditions();
	
	// 获取配备状况
	position.persons = [];
	var personcodes = $("#posPersons").val();
	if (personcodes != null) {
		var ids = personcodes.split(',');
		for (var i = 0; i < ids.length; i++) {
			var person = {};
			person.sorder = i + 1;
			person.position_id = position.id;
			person.personcode = ids[i];
			position.persons.push(person);
		}
	}
	
	//alert(position.persons.length);
	return position;
}

// 从对话框中获取条件对象
function getConditionObject(rowData) {
	var condition = {};
	if (rowData == null) {
		condition.id = Math.ceil(Math.random() * 100000) * -1; // 产生负数id
	}
	else {
		condition.id = rowData.id;
	}
	
	condition.sorder = $("#condSort").val();
	condition.condition_name = $("#condName").val();
	condition.condition_groupkey = $("#condGroupKey").val();
	condition.condition_tablekey = $("#condTableKey").val();
	condition.condition_querylogic = $("#condQueryLogic").val();
	condition.condition_querysql = $("#condQuerySQL").val();
	condition.condition_querysqlcn = $("#condQuerySQLCN").val();
	
	if (isNaN(condition.sorder) || condition.sorder == '') {
		condition.sorder = getMaxSorder('conditionGrid');
	}
	
	return condition;
}

// 打开职位设定 - 条件对话框
function openConditionDialog(rowData) {
	$("#conditionDialog").show();
	
	$("#conditionDialog").dialog({
		shadow:true,
		modal :true,
		resizable:true,
		width: 480,
		height: 320,
		title: rowData == null ? '添加条件设定' : '编辑条件设定',
		buttons:[{
			text:'确定',
			iconCls:'icon-ok',
			handler:function(){
				var condition = getConditionObject(rowData);
				if (condition.condition_name == '') {
					$.messager.alert('提示', '请输入条件名称！');
					return;
				}
				
				if (condition.condition_querysqlcn == '') {
					$.messager.alert('提示', '请创建条件！');
					return;
				}
				
				if (rowData == null) {
					$("#conditionGrid").datagrid('appendRow', condition);
				}
				else {
					var row = $("#conditionGrid").datagrid('getSelected');
					var index = $("#conditionGrid").datagrid('getRowIndex', row);
					$("#conditionGrid").datagrid('updateRow', {
						index: index,
						row: condition
					});
				}
				
				$('#conditionDialog').dialog('close');							
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$('#conditionDialog').dialog('close');
			}
		}],
		onOpen : function () {
			$(".condItem").val('');
			$("#condName").focus();
			
			if (rowData != null) {
				$("#condSort").val(rowData.sorder);
				$("#condName").val(rowData.condition_name);
				$("#condQuerySQLCN").val(rowData.condition_querysqlcn);
				$("#condQuerySQL").val(rowData.condition_querysql);
				$("#condGroupKey").val(rowData.condition_groupkey);
				$("#condTableKey").val(rowData.condition_tablekey);
				$("#condQueryLogic").val(rowData.condition_querylogic);
			}
		}
	});
}

// 弹出条件创建对话框
function onBuildCondition() {
	System.LevelQuery.getCondition2('A','', '', true, function(condition){
		if (condition) {
			$("#condQuerySQLCN").val(condition.conditionQuerySQLCN);
			$("#condQuerySQL").val(condition.conditionQuerySQL);
			$("#condGroupKey").val(condition.conditionGroupkey);
			$("#condTableKey").val(condition.conditionTablekey);
			$("#condQueryLogic").val(condition.conditionQueryLogic);
		}
	});
}

function onSelectPerson() {
	$("#personDialog").show();
	
	$("#personDialog").dialog({
		shadow:true,
		modal :true,
		resizable:true,
		width: 480,
		height: 320,
		title: '选择干部',
		buttons:[{
			text:'确定',
			iconCls:'icon-ok',
			handler:function(){
				var rows = $("#personGrid").datagrid('getSelections');
				$("#posPersons").val(getPersoncodes(rows));
				$("#posPersonnames").val(getPersonnames(rows));
				
				$('#personDialog').dialog('close');							
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$('#personDialog').dialog('close');
			}
		}],
		onOpen : function () {
			initPersonGrid($("#posPersons").val());
		}
	});
}

// 从干部列表中获取personcodes
function getPersoncodes(persons) {
	if (persons == null || persons.length == 0) {
		return '';
	}
	
	var v = '';
	for (var i = 0; i < persons.length; i++) {
		if (v == '') v = persons[i].personcode;
		else v = v + "," + persons[i].personcode;
	}
	
	return v;
}

// 从干部列表中获取personnames
function getPersonnames(persons) {
	if (persons == null || persons.length == 0) {
		return '';
	}
	
	var v = '';
	for (var i = 0; i < persons.length; i++) {
		if (v == '') v = persons[i].xingm;
		else v = v + "," + persons[i].xingm;
	}
	
	return v;
}

// 初始化干部选择表格
function initPersonGrid(personcodes) {
	var url = System.rootPath + "/mingc/mingc!queryByUnit.action?currentUnit=" + $("#currentUnit").val();
        
	$("#personGrid").datagrid({
		fit: true,
		nowrap: true,
		singleSelect:false,
		remoteSort: false,
		url: url,
		idField: 'personcode',	
		columns: const_person_grid_columns,
		onLoadSuccess: function() {
			$("#personGrid").datagrid("clearSelections");
			if (personcodes != null) {
				var ids = personcodes.split(",");
				for (var i = 0; i < ids.length; i++) {
					//alert(ids[i]);
					$("#personGrid").datagrid("selectRecord", ids[i]);
				}
			}
		}		
	});
}

function getPositionData() {
	var positions = $("#positionGrid").datagrid('getRows');
	return encodeURI($.toJSON(positions));
}
// ----------- 职位说明 结束

// 初始化所有结构表格
function initStructGrids() {	
	for (var i = 0; i < g_structTypes.length; i++) {
		var obj = "#structGrid" + g_structTypes[i];		
		var url = System.rootPath + "/unitstructmodel/unit-struct-model!querySettings.action?unitCode=" + unitCode + "&structType=" + g_structTypes[i];
		
		$(obj).datagrid({
			fit: true,
			nowrap: true,
			singleSelect:true,
			remoteSort: false,
			url: url,
			idField: 'id',	
			columns: g_structTypes[i] == 0 ? const_struct_type_grid_columns_zs : const_struct_type_grid_columns,
			toolbar:[
			{
				iconCls:'icon-add',
				text:'添加',
				handler:function(){
					var structType = getStructTypeFromTab();
					openStructDialog(null, structType);
				}
			},'-',
			{
				iconCls:'icon-edit',
				text:'编辑',
				handler:function(){
					var structType = getStructTypeFromTab();
					var target = getStructGridId(structType);
					var row = $(target).datagrid('getSelected');
					if (row != null) {
						var structType = getStructTypeFromTab();
						openStructDialog(row, structType);
					}
				}
			},'-',
			{
				iconCls:'icon-remove',
				text:'删除',
				handler:function(){
					var structType = getStructTypeFromTab();
					var target = getStructGridId(structType);
					var row = $(target).datagrid('getSelected');
					if (row != null) {
						$.messager.confirm('确认', '确定删除:' + row.struct_desc + '的项目?', function(isOk) {
							if (isOk) {
								var index = $(target).datagrid('getRowIndex', row);
								$(target).datagrid('deleteRow', index);
							}
						});
					}
				}
			}
			],
			onLoadSuccess: function() {
				g_totalAjax--;
				if (g_totalAjax <= 0) parent.closeMask();	
			},
			onDblClickRow: function(index, row) {
				var structType = getStructTypeFromTab();
				var target = getStructGridId(structType);
				var row = $(target).datagrid('getSelected');
				if (row != null) {
					var structType = getStructTypeFromTab();
					openStructDialog(row, structType);
				}
			}				
		});
	}
}

function getStructTypeFromTab() {
	var tab = $('#contentTabs').tabs('getSelected');
	var index = $('#contentTabs').tabs('getTabIndex',tab);
	//return g_structTypes[index - g_structTypeTabIndex];
	
	for (var i = 0; i < g_structTypesTabs.length; i++) {
		if (g_structTypesTabs[i] == index) return g_structTypes[i];
	}
	
	return -1;
}

// 打开结构设定对话框
function openStructDialog(rowData, structType) {
	$("#structDialog").show();
	
	$("#structDialog").dialog({
		shadow:true,
		modal :true,
		resizable:true,
		width: 480,
		height: 320,
		title: rowData == null ? '添加结构设定' : '编辑结构设定',
		buttons:[{
			text:'确定',
			iconCls:'icon-ok',
			handler:function(){
				var struct = getStructObject(rowData, structType);
				if (!validateStructObject(struct, structType)) {
					return;
				}
				
				if (rowData == null) {
					addStructToGrid(struct, structType);
				}
				else {
					// 更新
					var obj = getStructGridId(structType);
					var row = $(obj).datagrid('getSelected');
					var index = $(obj).datagrid('getRowIndex', row);
					$(obj).datagrid('updateRow', {
						index: index,
						row: struct
					});
				}
				
				$('#structDialog').dialog('close');							
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$('#structDialog').dialog('close');
			}
		}],
		onOpen : function () {
			$(".structSettingItem").val('');
			$("#structSettingAmount").focus();
			
			var structCode = rowData == null ? '' : rowData.struct_code;
			initStructCodeField(structType, structCode);
			
			if (rowData == null) {
				//$("#structSettingCode").combotree('setValue', '');
			}
			else {
				//$("#structSettingCode").combotree('setValue', rowData.struct_code);
				$("#structSettingSort").val(rowData.sorder);
				var amount = rowData.amount;
				
				if (rowData.amount.indexOf(">=") >= 0) {
					$("#structSettingAmountCondition").val(">=");
					amount = amount.substr(2);
				}
				else if (rowData.amount.indexOf("<=") >= 0) {
					$("#structSettingAmountCondition").val("<=");
					amount = amount.substr(2);
				}				
				
				$("#structSettingAmount").val(amount);				
			}
			//alert(rowData.one_ticket);
			
			if (rowData == null || rowData.one_ticket != "1") $("#structSettingOneTicket").val("0");
			else $("#structSettingOneTicket").val(rowData.one_ticket);
			
			if (structType == 0) {
				$("#tag1").show();
				$("#tag2").show();
				if (rowData != null) {
					$("#structSettingTag1").val(rowData.tag1);
					$("#structSettingTag2").val(rowData.tag2);
				}
			}
			else {
				$("#tag1").hide();
				$("#tag2").hide();
			}
		}
	});
}

function getStructGridId(structType) {
	return '#structGrid' + structType;
}

// 添加结构设定数据到表格中
function addStructToGrid(struct, structType) {
	var obj = getStructGridId(structType);
	$(obj).datagrid('appendRow', struct);
}

// 检验结构设定数据
function validateStructObject(struct, structType) {
	if (struct.remark == '') {
		$.messager.alert('提示', '请输入名称！');
		return false;
	}
	
	if (struct.amount == '') {
		$.messager.alert('提示', '请输入数量！');
		return false;
	}
	
	if (struct.struct_code == '') {
		$.messager.alert('提示', '请选择项目或者输入项目名称！');
		return false;
	}
	
	if (structType == 0 && (struct.amount.indexOf('>') >=0 || struct.amount.indexOf('<') >= 0)) {
		$.messager.alert('提示', '对于职能职数，不支持>=和<=！');
		return false;
	}
	
	if (structType == 0) {
		if (struct.tag1 == '') {
			$.messager.alert('提示', '请选择职务类别！');
			return false;
		}
		if (struct.tag2 == '') {
			$.messager.alert('提示', '请选择职务职级！');
			return false;
		}
	}
	
	return true;
}

// 获取结构设定数据
function getStructObject(rowData, structType) {
	var struct = {};
	if (rowData == null) {
		struct.id = Math.ceil(Math.random() * 100000) * -1; // 产生负数id
	}
	else {
		struct.id = rowData.id;
	}
	struct.unit_code = unitCode;
	struct.struct_type = structType;
	struct.sorder = $("#structSettingSort").val();
	var condition = $("#structSettingAmountCondition").val();	
	struct.amount = condition + $("#structSettingAmount").val();
	struct.one_ticket = $("#structSettingOneTicket").val();
	//alert(struct.one_ticket);
	if (isNaN(struct.sorder) || struct.sorder == '') {
		struct.sorder = getMaxSorder('structGrid' + structType);
	}
		
	// 获取选择项目
	if (structType != 0) {
		var t = $('#structSettingCode').combotree('tree');	// get the tree object
		var n = t.tree('getSelected');		// get selected node	
		if (n != null) {
			struct.struct_desc = n.text;
			struct.struct_code = n.id;
		}
		else {
			struct.struct_desc = '';
			struct.struct_code = '';
		}
		
		struct.tag1 = null;
		struct.tag2 = null;
	}
	else {
		struct.struct_desc = $("#structSettingValue").val();
		struct.struct_code = $("#structSettingValue").val();
		
		struct.tag1 = $("#structSettingTag1").val();
		struct.tag2 = $("#structSettingTag2").val();
	}
		
	return struct;
}

// 初始化结构设定项目树
function initStructCodeField(structType, value) {	
	var code = structType <= 9 ? 'U00' + structType : 'U0' + structType;
	//if (structType == 9) code = '0004'; // 民族结构
    if (structType == 0) code = '9004'; // 职数职能
	else if (structType == 10) code = 'VTNL'; // 能力
	
	var url = System.rootPath + '/code/code!getTreeBy.action?treeType=5&codeClass=' + code + '&serviceid=-1';
	
	if (structType != 0) {
		$("#structSettingValue").hide();
		$("#structSettingValueMsg").hide();
		
		$("#structSettingCode").combotree({
			url: url,
			width: 150,
			editable: false,
			mode: 'remote',
			value: value
		});
	}
	else {
		$("#structSettingValue").show();
		$("#structSettingValueMsg").show();
		$("#structSettingValue").val(value);
		$("#structSettingCode").hide();
		$('#structSettingCode').next(".combo").hide();
		// 职能职数直接输入，by YZQ on 2014/11/27
		/*
		if (g_zwmcData == null) {
			$.get(url, function(data) {
				g_zwmcData = data;
				initStructSettingCodeForZsjg(value);
			});
		}
		else {
			initStructSettingCodeForZsjg(value);
		}
		*/
	}
}

function initStructSettingCodeForZsjg(value) {
	$("#structSettingCode").combotree({
		url: null,
		width: 150,
		editable: true,
		value: value,
		keyHandler:{
			up: function(e){},
			down: function(e){},
			left: function(e){},
			right: function(e){},
			enter: function(e){alert('x');},
			query:function(q, e) {
				if (q != '') {
					if (q.length >= 2) {// 小于2个字符不执行查询
						// 清空数据先
						$("#structSettingCode").combotree('setValue', '');
						$.ajax({
							type:'post',
							url:System.rootPath + "/code/code!queryCode.action",
							dataType:'json',
							data:{'querykey':q, 'codeClass':'9004'},
							success:function(json){								
								$("#structSettingCode").combotree('tree').tree('loadData', json);
							},
							error:function(){
								$("#structSettingCode").combotree('tree').tree('loadData',[]);
							}
						});
					}
				}
			}
		}
	});
	
	$("#structSettingCode").combotree('tree').tree('loadData', g_zwmcData);
}

// 创建版本
function createVersion() {
	$.messager.confirm('确定创建版本', '确定要创建新版本(将保存结构模型分析结果、花名册、结构要素名册)？<br/><br/>注：系统将使用当前保存的结构模型设置进行统计分析并创建新版本！', function(isOk) {
		if (!isOk) return;
		
		System.openLoadMask("#versionWin", "正在创建版本，请稍后...");
		
		$.ajax({
			type: "POST",
			url: System.rootPath+ '/unitstructmodel/unit-struct-model!createVersion.action',
			data: { 
				currentUnit: $("#currentUnit").val()
			},
			success:function(json){
				System.closeLoadMask("#versionWin");
				
				try {
					if (json.indexOf("创建成功") >= 0) {							
						$.messager.alert('提示', '创建成功!');	
						$('#versionList').datagrid('reload');
					}
					else {
						alert('创建版本失败，错误信息：' + json);						
					}
				}
				catch (ex) {
					alert('提示', '创建版本失败，错误信息：' + ex);						
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				System.closeLoadMask("#versionWin");
				$.messager.alert('提示', "创建版本失败,请重试!"+textStatus+errorThrown);
			}
		});		
	});
}

// 删除版本
function deleteVersion(row) {
	$.messager.confirm('确定删除版本', '确定要删除选中的版本？<br/><br/><br/>创建时间：' + row.short_created_date + "<br/>&nbsp;&nbsp;创建者：" + row.created_by, function(isOk) {
		if (!isOk) return;
		
		System.openLoadMask("#versionWin", "正在删除版本，请稍后...");
		
		$.ajax({
			type: "POST",
			url: System.rootPath+ '/unitstructmodel/unit-struct-model!deleteVersion.action',
			data: { 
				versionId: row.id
			},
			success:function(json){
				System.closeLoadMask("#versionWin");
				
				try {
					if (json.indexOf("删除成功") >= 0) {							
						$.messager.alert('提示', '删除成功!');	
						$('#versionList').datagrid('clearSelections');						
						$('#versionList').datagrid('reload');						
					}
					else {
						alert('删除版本失败，错误信息：' + json);						
					}
				}
				catch (ex) {
					alert('提示', '删除版本失败，错误信息：' + ex);						
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				System.closeLoadMask("#versionWin");
				$.messager.alert('提示', "删除版本失败,请重试!"+textStatus+errorThrown);
			}
		});		
	});
}

// 查看版本
function onViewVersion() {
	$('#versionList').datagrid({
		nowrap: true,
		rownumbers: true,
		striped: false,
		collapsible:false,
		singleSelect: true,
		fit: true,
		remoteSort: false,		
		pagination:false,		
		url: System.rootPath + '/unitstructmodel/unit-struct-model!queryVersions.action?unitCode=' + unitCode,
		idField:'id',
		columns: [[
			{field:'id',width:20,hidden:true},
			{title:'创建时间',field:'short_created_date',width:150, sortable: true },
			{title:'创建者',field:'created_by',width:50, sortable: true}			
		]],		
		toolbar: [{
            id: 'btnAdd',
            iconCls: 'icon-add',
            text: '创建新版本',
            handler: function() {
                createVersion();
            }
        },
        '-', {
            id: 'btnRemove',
            iconCls: 'icon-remove',
            text: '删除版本',
            handler: function() {
                var row = $("#versionList").datagrid("getSelected");
				if (row) {
					deleteVersion(row);
				}
            }
        },
		'-', {
            id: 'btnView',
            iconCls: 'icon-report',
            text: '查看版本',
            handler: function() {
                var row = $("#versionList").datagrid("getSelected");
				if (row) {
					var url = System.rootPath + "/unitstructmodel/unit-struct-model!analysisProcess.action?currentUnit=" + $("#currentUnit").val() + "&versionId=" + row.id;
					window.open(url, '结构模型预览');
				}
            }
        }]
	});	
	
	$("#versionList").datagrid('clearSelections');
	$('#versionWin').window('open');	
}

// 保存所有设定
function onSave(preview) {	
	//alert(g_totalAjax);
	if (g_totalAjax > 0) {	
		$.messager.alert('提示', g_totalAjax + '个项目还没有完全载入，请稍后重试!');
		return;
	}
	
	var master = getMasterData();
	if (!validateMaster(master)) return;
	var masterData = getEncodeMasterData(master);
	var structData = getStructData();
	if (structData == null) return;
	var levelData = getLevelData();
	var positionData = getPositionData();
		
	System.openLoadMask("#mainPanel", "正在保存...");
	
	$.ajax({
		type: "POST",
		url: System.rootPath+ '/unitstructmodel/unit-struct-model!save.action',
		data: { 
			paramMasterData: masterData,
			paramSettingData: structData,
			paramLevelData: levelData,
			paramPositionData: positionData
		},
		success:function(json){
			System.closeLoadMask("#mainPanel");
			
			try {
				if (json.indexOf("保存成功") >= 0) {							
					$.messager.alert('提示', '保存成功!');	
					if (preview == 1) {
						var url = System.rootPath + "/unitstructmodel/unit-struct-model!analysisProcess.action?currentUnit=" + $("#currentUnit").val();
						window.open(url, '结构模型预览');
					}
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
			System.closeLoadMask("#mainPanel");
			$.messager.alert('提示', "保存失败,请重试!"+textStatus+errorThrown);
		}
	});		
}

// 检验主表数据
function validateMaster(masterData) {
	return true;
}

// 检验结构数据
function validateStructData(structData) {
	// 必须设置职能职数
	for (var i = 0; i < structData.length; i++) {
		if (structData[i].struct_type == 0) {
			return true;
		}
	}
	
	$.messager.alert('提示', '职能职数必须填写至少一个项目！');			
	return false;
}

// 获取综合设置对象
function getEncodeMasterData() {
	var master = getMasterData();
	var masterRow = [master];
	return encodeURI($.toJSON(masterRow));
}

// 获取主表数据
function getMasterData() {
	var editor = $("#responsibility").cleditor()[0];
	editor.updateTextArea();
	
	editor = $("#assessment").cleditor()[0];
	editor.updateTextArea();
	
	editor = $("#suggestion").cleditor()[0];
	editor.updateTextArea();
				
	var master = {
		unit_code: unitCode,
		form_type: $('input[name="formType"]:checked').val(),
		responsibility: $("#responsibility").val(),
		assessment: $("#assessment").val(),
		suggestion: $("#suggestion").val()
	}
	
	return master;
}

// 获取自然结构数据
function getStructData() {
	var structRow = [];
	
	for (var n = 0; n < g_structTypes.length; n++) {
		var structType = g_structTypes[n];
		var obj = getStructGridId(structType);
		var rows = $(obj).datagrid('getRows');
		if (rows != null) {
			for (var i = 0; i < rows.length; i++) {
				structRow.push(rows[i]);
			}
		}
	}
	
	if (!validateStructData(structRow)) {
		return null;
	}
	
	return encodeURI($.toJSON(structRow));
}

// 获取结构调整数据
function getLevelData() {
	var levelRow = [];
	
	for (var i = 0; i < g_levels.length; i++) {
		var n = g_levels[i];
		var level = {
			unit_code : unitCode,
			struct_type : n,
			sorder : $("#levelOrder" + n).val(),
			manual_struct_level : $("#level" + n).val(),
			manual_struct_level_remark : $("#levelRemark" + n).val()
		};
		
		//alert(level.sorder);
		//alert(level.manual_struct_level);
		//alert(level.manual_struct_level_remark);
		
		levelRow.push(level);
	}	
	
	return encodeURI($.toJSON(levelRow));
}

// 判断checkbox是否选中
function checkboxIsSelected(checkboxId) {
	if ($('#' + checkboxId).prop('checked') == true) {
		return true;
	}
	else {
		return false;
	}
}

// 选择代码，多选
function pickupCode(fieldName, codeClassNo) {
	General.showCode(fieldName, codeClassNo, '', '', true);
}

// 初始化快速条件
function initQuickConditions(row) {
	$(".chkCondition").unbind('click'); // 解除绑定先
	$(".chkCondition").bind('click', function() {
		var divName = $(this).val();
		if ($(this).prop('checked')) {
			$("#" + divName).show();
		}
		else {
			$("#" + divName).hide();
		}
	});
	
	if (row == null) {
		$(".chkCondition").each(function() {
			$(this).prop('checked', false);
			var divName = $(this).val();
			$("#" + divName).hide();
			$("#" + divName + " input[type='hidden']").val('');
			$("#" + divName + " input[type='text']").val('');
		});
	}
	else {
		// 初始化条件设置
		for (var i = 0; i < row.quickConditions.length; i++) {
			var cond = row.quickConditions[i];
			
			// 年龄
			if (cond.condition_type == 1) {
				$("#chkNl").prop('checked', true);
				$("#divNl").show();
				$("#nlOpt").val(cond.v1);
				$("#nlValue").val(cond.v2);
			}
			// 性别
			else if (cond.condition_type == 2) {
				$("#chkXb").prop('checked', true);
				$("#divXb").show();
				checkedRadio("radXb", cond.v1);
			}
			// 学历
			else if (cond.condition_type == 3) {
				$("#chkXl").prop('checked', true);
				$("#divXl").show();
				checkedRadio("radXl", cond.v1);
				$("#selXlType").val(cond.v2);
			}
			// 专业
			else if (cond.condition_type == 4) {
				$("#chkZy").prop('checked', true);
				$("#divZy").show();
				$("#txtZy").val(cond.v1);
				$("#txtZy_show").val(cond.v1_show);
				$("#txtZy_code").val(cond.v1_show);
			}
			// 职称
			else if (cond.condition_type == 5) {
				$("#chkZc").prop('checked', true);
				$("#divZc").show();
				$("#txtZc").val(cond.v1);
				$("#txtZc_show").val(cond.v1_show);
				$("#txtZc_code").val(cond.v1_show);
			}
			// 党派
			else if (cond.condition_type == 6) {
				$("#chkDp").prop('checked', true);
				$("#divDp").show();
				checkedRadio("radDp", cond.v1);
			}
			// 地域
			else if (cond.condition_type == 7) {
				$("#chkDy").prop('checked', true);
				$("#divDy").show();
				checkedRadio("radDy", cond.v1);
			}
			// 工作经历
			else if (cond.condition_type == 8) {
				$("#chkGzjl").prop('checked', true);
				$("#divGzjl").show();
				
				$("#txtGzjl_fg").val(cond.v1);
				$("#txtGzjl_fg_show").val(cond.v1_show);
				$("#txtGzjl_fg_code").val(cond.v1_show);
				$("#txtGzjl_fg_year").val(cond.v2);
				
				$("#txtGzjl_jbcc_opt").val(cond.v3);
				
				$("#txtGzjl_jbcc").val(cond.v4);
				$("#txtGzjl_jbcc_show").val(cond.v4_show);
				$("#txtGzjl_jbcc_code").val(cond.v4_show);
				$("#txtGzjl_jbcc_year").val(cond.v5);
				
				$("#txtGzjl_zwcc").val(cond.v6);
				$("#txtGzjl_zwcc_show").val(cond.v6_show);
				$("#txtGzjl_zwcc_code").val(cond.v6_show);
				
				$("#txtGzjl_lb").val(cond.v7);
				$("#txtGzjl_lb_show").val(cond.v7_show);
				$("#txtGzjl_lb_code").val(cond.v7_show);
				$("#txtGzjl_lb_year").val(cond.v8);
			}
			// 能力要求
			else if (cond.condition_type == 9) {
				$("#chkNlyq").prop('checked', true);
				$("#divNlyq").show();
				$("#txtNlyq").val(cond.v1);
				$("#txtNlyq_show").val(cond.v1_show);
				$("#txtNlyq_code").val(cond.v1_show);
			}
		}
	}
}

// 获取快速条件
function getQuickConditions() {
	var conditions = [];
	
	// 年龄
	if ($("#chkNl").prop('checked')) {
		var cond = {};
		cond.condition_type = 1;
		cond.v1 = $("#nlOpt").val();
		cond.v2 = $("#nlValue").val();
		if (isNaN(cond.v2)) {
			cond.v2 = 0;
		}
		
		conditions.push(cond);
	}
	
	// 性别
	if ($("#chkXb").prop('checked')) {
		var cond = {};
		cond.condition_type = 2;
		cond.v1 = $("input[name='radXb']:checked").val();
		
		conditions.push(cond);
	}
	
	// 学历
	if ($("#chkXl").prop('checked')) {
		var cond = {};
		cond.condition_type = 3;
		cond.v1 = $("input[name='radXl']:checked").val();
		cond.v2 = $("#selXlType").val();
		conditions.push(cond);
	}
	
	// 专业
	if ($("#chkZy").prop('checked')) {
		var cond = {};
		cond.condition_type = 4;
		cond.v1 = $("#txtZy").val();
		cond.v1_show = $("#txtZy_show").val();
		conditions.push(cond);
	}
	
	// 职称
	if ($("#chkZc").prop('checked')) {
		var cond = {};
		cond.condition_type = 5;
		cond.v1 = $("#txtZc").val();
		cond.v1_show = $("#txtZc_show").val();
		conditions.push(cond);
	}
	
	// 党派
	if ($("#chkDp").prop('checked')) {
		var cond = {};
		cond.condition_type = 6;
		cond.v1 = $("input[name='radDp']:checked").val();
		conditions.push(cond);
	}
	
	// 地域
	if ($("#chkDy").prop('checked')) {
		var cond = {};
		cond.condition_type = 7;
		cond.v1 = $("input[name='radDy']:checked").val();
		conditions.push(cond);
	}
	
	// 工作经历
	if ($("#chkGzjl").prop('checked')) {
		var cond = {};
		cond.condition_type = 8;
		cond.v1 = $("#txtGzjl_fg").val();
		cond.v1_show = $("#txtGzjl_fg_show").val();
		cond.v2 = $("#txtGzjl_fg_year").val();
		if (isNaN(cond.v2)) {
			cond.v2 = 0;
		}
		
		cond.v3 = $("#txtGzjl_jbcc_opt").val();
		
		cond.v4 = $("#txtGzjl_jbcc").val();
		cond.v4_show = $("#txtGzjl_jbcc_show").val();
		cond.v5 = $("#txtGzjl_jbcc_year").val();
		if (isNaN(cond.v5)) {
			cond.v5 = 0;
		}
		
		cond.v6 = $("#txtGzjl_zwcc").val();
		cond.v6_show = $("#txtGzjl_zwcc_show").val();
		
		cond.v7 = $("#txtGzjl_lb").val();
		cond.v7_show = $("#txtGzjl_lb_show").val();
		cond.v8 = $("#txtGzjl_lb_year").val();
		if (isNaN(cond.v8)) {
			cond.v8 = 0;
		}
		
		conditions.push(cond);
	}
	
	// 能力要求
	if ($("#chkZc").prop('checked')) {
		var cond = {};
		cond.condition_type = 9;
		cond.v1 = $("#txtNlyq").val();
		cond.v1_show = $("#txtNlyq_show").val();
		conditions.push(cond);
	}
	
	return conditions;
}

// 检验快速资格条件
function validQuickConditions(conditions) {
	for (var i = 0; i < conditions.length; i++) {
		var cond = conditions[i];
		
		// 年龄
		if (cond.condition_type == 1) {
			if (cond.v2 <= 0) {
				$.messager.alert('提示', '年龄必须大于0岁！');
				return false;
			}
		}
		// 专业
		else if (cond.condition_type == 4) {
			if (cond.v1 == '') {
				$.messager.alert('提示', '请选择专业！');
				return false;
			}
		}
		// 职称
		else if (cond.condition_type == 5) {
			if (cond.v1 == '') {
				$.messager.alert('提示', '请选择职称！');
				return false;
			}
		}
		// 工作经历
		else if (cond.condition_type == 8) {
			$("#chkGzjl").prop('checked', true);
			$("#divGzjl").show();
			
			if (cond.v1 == '' && cond.v2 != 0) {
				$.messager.alert('提示', '请选择从事或分管工作！');
				return false;
			}
			
			if (cond.v2 < 0) {
				$.messager.alert('提示', '从事或分管工作年限必须大于等于0年！');
				return false;
			}
			
			if (cond.v4 == '' && cond.v6 == '' && cond.v5 != 0) {
				$.messager.alert('提示', '请选择级别层次或者职务层次！');
				return false;
			}
			
			if (cond.v5 < 0) {
				$.messager.alert('提示', '级别层次/职务层次年限必须大于等于0年！');
				return false;
			}
			
			if (cond.v7 == '' && cond.v8 != 0) {
				$.messager.alert('提示', '请选择类别！');
				return false;
			}
			
			if (cond.v8 < 0) {
				$.messager.alert('提示', '类别年限必须大于等于0年！');
				return false;
			}
			
			if (cond.v1 == '' && cond.v2 == '' && 
			    cond.v4 == '' && cond.v5 == '' && cond.v6 == '' && 
				cond.v7 == '' && cond.v8 == '') {
				$.messager.alert('提示', '请选择工作经历条件！');
				return false;
			}
		}
		// 能力要求
		else if (cond.condition_type == 9) {
			if (cond.v1 == '') {
				$.messager.alert('提示', '请选择能力！');
				return false;
			}
		}
	}
	
	return true;
}

// 选中radio控件
function checkedRadio(obj, value) {
	$("input[name='" + obj + "']").each(function() {
		if ($(this).val() == value) {
			$(this).prop('checked', true);
		}
	});
}

// 获取最大的sorder
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