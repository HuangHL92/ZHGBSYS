var g_refreshPersonNl = false; // 是否需要在关闭能力项目对话框后刷新能力列表
var g_nlTypeList = null; // 缓存的能力类型列表
var g_renderedNlSection = false; // 是否已经渲染过能力项目对话框中的能力评分区域

var setting = {
	/*
	check: {
		enable: true,
		nocheckInherit: false,
		chkboxType: { "Y" : "s", "N" : "s" } // 勾选不影响父节点，取消影响子节点
	},*/
	data: {
		key : {
			name: 'text'
		}
	},
	callback: {
		onClick: onUnitClick
	},
	async: {
		enable: true,
		url:  System.rootPath + "/common/unit-tree!getAccessiableUnitTree.action?treeType=1",
		autoParam:["id"]
	}
};

var setting2 = {
	data: {
		key : {
			name: 'text'
		}
	},
	callback: {
		onClick: onUnitClick
	}
};

$(function(){
	$(".panel-title").css("text-align","center");
	initUnitTree();
	
	initPersonNlTable();
});

function initUnitTree() {
	$.fn.zTree.init($("#tt"), setting);
}

function onUnitClick(event, treeId, node) {
	$("#currentUnit").val(node.id);
	$("#currentUnit").data("snodeid", node.id);
	$("#currentUnitName").val(node.text);
	$("#querykey").val('');
	$("#unitName").html(node.text);
	
	loadPersonNlData();
}

function searchUnit(value, name) {
	value = value.Trim();
	
	if (value != "" && value.length < 2){
		$.messager.alert("提示","请最少输入两个关键字进行搜索！");				
	}
	else if (value == "") {
		// 重新载入单位树
		initUnitTree();
	}
	else {			
		System.openLoadMask($("#cc"),"正在进行查询，请稍候......");			
		var url = System.rootPath + "/common/unit-tree!getAccessiableUnitTree.action?treeType=9&queryKey=" + encodeURI(value);
		$.get(url, function(data) {
			System.closeLoadMask($("#cc"));
			if (data.length == 0) {
				$.messager.alert("提示","没有找到符合条件的单位！");
				//$.fn.zTree.init($("#tt"), setting2, data);		
			}
			else {
				$.fn.zTree.init($("#tt"), setting2, data);		
			}
		});				
	}				
}

// ---------- 能力相关 ------------------
function refreshNlData() {
	if ($("#searchMode").val() == '0') {
		loadPersonNlData();
	}
	else {
		execSearch($("#queryword").searchbox('getValue'), null);
	}
}

function execSearch(value, name) {
	value = value.Trim();
	
	searchPersonNlData(value);
}

// 按姓名搜索能力数据
function searchPersonNlData(name) {
	$("#searchMode").val('1');
	var url = System.rootPath + "/nl/nl!getPersonNlListByName.action"
	
	$.ajax({
		type: 'post',
		url: url,
		data: {
			querykey: name
		},
		beforeSend: function(XMLHttpRequest) {
			System.openLoadMask($("#contentPanel"), "正在搜索...")
		},
		success: function(data, textStatus) {
			$("#personNl").datagrid('loadData', data);
		},
		complete: function(XMLHttpRequest, textStatus) {
			System.closeLoadMask($("#contentPanel"))
		},
		error: function() {
			$.messager.alert("提示", "查询出现错误,请重试!", "error")
		}
	});
}

function loadPersonNlData() {
	$("#searchMode").val('0');
	var url = System.rootPath + "/nl/nl!getPersonNlListByUnit.action"
	
	var params = $("#pageParames").serialize();
	params = decodeURIComponent(params, true);
	$.ajax({
		type: 'post',
		url: url,
		data: encodeURI(params),
		beforeSend: function(XMLHttpRequest) {
			System.openLoadMask($("#contentPanel"), "正在查询...");
		},
		success: function(data, textStatus) {
			$("#personNl").datagrid('loadData', data);
		},
		complete: function(XMLHttpRequest, textStatus) {
			System.closeLoadMask($("#contentPanel"))
		},
		error: function() {
			$.messager.alert("提示", "查询出现错误,请重试!", "error");
		}
	});
}

// 初始化能力表格
function initPersonNlTable() {
	$("#personNl").datagrid({
		fit: true,
		rownumbers: true,
		singleSelect: true,
		pagination: true,
		pageSize: 20,
		nowrap: false,
		idField: 'id',
		frozenColumns: [[
			{ field: 'id', hidden: true },
			{ field: 'xingm', title: '姓名', align: "center", width: 80 },
			{ field: 'zhiw', title: '职务',	align: "left", width: 200 }			
		]],
		columns: g_columns,
		toolbar: [{
			id: 'btnDetails',
			iconCls: 'icon-details',
			text: '详细',
			handler: function() {
				var row = $("#personNl").datagrid('getSelected');
				if (row) {
					showNlItemMasterDetails(row);
				}
			}
		}],
		onDblClickRow: function(index, row) {
			if (row) {
				showNlItemMasterDetails(row);
			}
		}/*,
		rowStyler: function(index, row) {
			if (row.itemMasterCount == 2) {
				//return 'background-color:#e0e0e0;';
				return 'background-color:#e0e0e0;';
			}
			else if (row.itemMasterCount > 2) {
				return 'background-color:#ffee00;';
			}
			else {
				return '';
			}
		}*/
	});
}

// 显示能力详细记录
function showNlItemMasterDetails(row) {
	g_refreshPersonNl = false;
	
	$("#personcode").val(row.personcode);
	
	$("#nlItemMasterListDialog").show();
	
	$("#nlItemMasterListDialog").dialog({
		shadow:true,
		modal :true,
		resizable:true,
		width: 1200,
		height: 480,
		title: row.xingm + ' - 能力采集记录列表',
		onBeforeOpen : function () {
			initnlItemMasterListTable(row);
		},
		onClose: function() {
			if (g_refreshPersonNl) {
				// 刷新能力列表
				refreshNlData();
			}
		}
	});
}

// 初始化能力项目主表表格
function initnlItemMasterListTable(row) {
	var url = System.rootPath + "/nl/nl!getPersonNlItemMasterList.action";
	url = url + "?personcode=" + row.personcode;
	
	$("#nlItemMasterListTable").datagrid({
		url: url,
		fit: true,
		rownumbers: true,
		singleSelect: true,
		pagination: true,
		pageNumber: 1, 
		pageSize: 10,
		idField: 'id',
		frozenColumns: [[
			{ field: 'id', hidden: true },
			{ field: 'gather_date', title: '采集时间', align: "center", width: 100 },
			{ field: 'from_type', title: '采集来源', align: "center", width: 130, 
				formatter: function(value, row, index) {
					return row.from_type_name;
				}			
			}
		]],
		columns: g_columns2,
		toolbar: [{
			id: 'btnPersonNlAdd',
			iconCls: 'icon-add',
			text: '新增',
			handler: function() {
				showNlDialog(null);
			}
		},
		'-',{
			id: 'btnPersonNlEdit',
			iconCls: 'icon-edit',
			text: '编辑',
			handler: function() {
				var row = $("#nlItemMasterListTable").datagrid('getSelected');
				if (row) {
					showNlDialog(row);
				}
			}
		},
		'-', {
			id: 'btnPersonNlRemove',
			iconCls: 'icon-remove',
			text: '删除',
			handler: function() {
				var row = $("#nlItemMasterListTable").datagrid('getSelected');
				if (row) {
					deletePersonNlItemMaster(row);
				}
			}
		}],
		onDblClickRow: function(index, row) {
			if (row) {
				showNlDialog(row);
			}
		}
	});
}

// 显示能力对话框
function showNlDialog(row) {
	$("#nlDialog").show();
	
	$("#nlDialog").dialog({
		shadow:true,
		modal :true,
		resizable:true,
		width: 800,
		height: 480,
		title: row == null ? '新增能力评价' : '编辑能力评价',
		buttons:[{
			text:'确定',
			iconCls:'icon-ok',
			handler:function(){
				var itemMaster = getNlItemMasterData(g_nlTypeList);	
				if (itemMaster.gather_date == '') {
					$.messager.alert("提示", "请选择采集时间！");
					return;
				}
				
				if (itemMaster.from_type == '') {
					$.messager.alert("提示", "请选择采集来源！");
					return;
				}
				
				for (var i = 0; i < itemMaster.itemList.length; i++) {
					if (!validScore(itemMaster.itemList[i].score)) {
						$.messager.alert("提示", "能力分值只能在0到10之间！");
						return;
					}
				}
				
				saveNlItemMaster(itemMaster);
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$('#nlDialog').dialog('close');
			}
		}],
		onBeforeOpen : function () {
			renderNlDialogControls(row);
		}
	});
}

// 渲染能力对话框控件
function renderNlDialogControls(row) {
	if (row != null) {
		$("#item_master_id").val(row.id);
		$("#score_type").val(row.score_type);
		$("#default_score").val(row.default_score);
		$("#remark").val(row.remark);
	}
	else {
		$("#item_master_id").val('');
		$("#score_type").val('');
		$("#default_score").val('0');
		$("#remark").val('');
	}
	
	$("#gather_date").datebox({
		required: true,
		width: 120
	});
	
	$("#gather_date").datebox('setValue', row == null ? '' : row.gather_date);
	
	var url = System.rootPath + "/nl/nl!getPersonNlFromTypeList.action";
	
	$("#from_type").combobox({
		url: url,
		width: 200,
		valueField: 'id',
		textField: 'name',
		editable: false,
		required: true,
		readonly: row == null ? false : true, 
		hasDownArrow: row == null ? true : false,
		onSelect: function(data) {
			$("#score_type").val(data.score_type);
			$("#default_score").val(data.default_score);
			//System.openLoadMask($("#nlDialog"), "载入中...");
			refreshNlSection(row);
		}
	});
	
	$("#from_type").combobox('setValue', row == null ? '' : row.from_type);
		
	
	// 同步获取能力列表
	refreshNlSection(row);
}

// 刷新能力区域
function refreshNlSection(row) {
	if (g_nlTypeList == null) {
		$.ajax({
			async: false,
			type: 'post',
			url: System.rootPath + "/nl/nl!getPersonNlTypeList.action",
			success: function(data, textStatus) {
				g_nlTypeList = data;
				renderNlSection(row, g_nlTypeList);
			},
			error: function() {
				$.messager.alert("提示", "无法获取能力类型列表，请重试!", "error")
			}
		});
	}
	else {
		renderNlSection(row, g_nlTypeList);
	}
}

// 渲染能力区域
function renderNlSection(row, data) {
	var html = '';
	var scoreType = $("#score_type").val();
	var defaultScore = $("#default_score").val();
	
	if (!g_renderedNlSection) {
		var numOfRow = 3;
		var totalRows = parseInt(data.length / numOfRow);
		if (totalRows * numOfRow != data.length) {
			totalRows++;
		}
		var p = parseInt(100 / (numOfRow * 2) + 0.5);
		
		//alert(totalRows);
		for (var i = 0; i < totalRows; i++) {
			html += "<tr>";
			
			for (var n = 0; n < numOfRow; n++) {
				var index = i * numOfRow + n;
				if (index >= data.length) break;
				
				html += "<td width='" + p + "%'>";
				html += data[index].name;
				
				html += "<td width='" + p + "%'>";
				// 下拉选择或输入
				//if (scoreType == '0') {
					html += "<select style='width:60px;' class='nlType easyui-combobox' id='selNl" + data[index].id + "' nlType='" + data[index].id + "'>";
					for (var s = 10 ; s >= 1; s-=1) {
						html += "<option value='" + s + "'";
						html += ">" + s + "</option>";
					}
					html += "<option value='0'>无</option>";
					html += "</select>";
				//}
				// 打勾
				//else if (scoreType == '1') {
					html += "<input class='nlType' type='checkbox' id='chkNl" + data[index].id + "' nlType='" + data[index].id + "' value='" + defaultScore + "'/>";
				//}
				//else {
				//	html += "&nbsp;";
				//}
				
				html += "</td>";
			}
			
			html += "</tr>";
		}
	}
	
	if (!g_renderedNlSection) {
		$("#nlSectionTable").html(html);
		$.parser.parse("#nlSectionTable");
		g_renderedNlSection = true;
	}
	
	if (scoreType == '0') {
		// 设置缺省值
		for (var i = 0; i < data.length; i++) {
			$("#chkNl" + data[i].id).hide();
			showCombobox('selNl' + data[i].id);
			
			if (row == null) {
				$("#selNl" + data[i].id).combobox('setValue', defaultScore);
			}
			else {
				var findNlType = false;
				for (var n = 0; n < row.nlItemList.length; n++) {
					if (row.nlItemList[n].nl_type == data[i].id) {
						findNlType = true;
						$("#selNl" + data[i].id).combobox('setValue', row.nlItemList[n].score);
						break;
					}
				}
				// 当前保存的能力项目不包括当前能力类型，那么设置为0
				if (!findNlType) {
					$("#selNl" + data[i].id).combobox('setValue', '0');
				}
			}
		}
	}
	else if (scoreType == '1'){
		for (var i = 0; i < data.length; i++) {
			$("#chkNl" + data[i].id).show();
			hideCombobox('selNl' + data[i].id);
			
			if (row != null) {
				for (var n = 0; n < row.nlItemList.length; n++) {
					if (row.nlItemList[n].nl_type == data[i].id) {
						$("#chkNl" + data[i].id).attr('checked', row.nlItemList[n].score != 0);
						continue;
					}
				}
			}
			else {
				$("#chkNl" + data[i].id).attr('checked', false);
			}
		}
	}
	else {
		for (var i = 0; i < data.length; i++) {
			$("#chkNl" + data[i].id).hide();
			hideCombobox('selNl' + data[i].id);
		}
	}
	
	//System.closeLoadMask($("#nlDialog"));
}

// 获取输入的能力数据
function getNlItemMasterData(nlTypeList) {
	var itemMaster = {};
	var isCreate = $("#item_master_id").val() == '';
	
	itemMaster.id = $("#item_master_id").val();
	itemMaster.personcode = $("#personcode").val();
	itemMaster.gather_date = $("#gather_date").datebox('getValue');
	itemMaster.remark = $("#remark").val();
	itemMaster.from_type = $("#from_type").combobox('getValue');
	itemMaster.itemList = [];
	
	
	for (var i = 0; i < nlTypeList.length; i++) {
		var score = 0;
		if ($("#score_type").val() == '0') {
			score = parseFloat($("#selNl" + nlTypeList[i].id).combobox('getValue'));
		}
		else {
			if ($("#chkNl" + nlTypeList[i].id).prop('checked')) {
				score = parseFloat($("#default_score").val()); // 这里必须取缺省值
				//alert(score);
			}
		}
		
		if (isNaN(score)) score = 0;
		//alert(score);
		
		var item = {};
		item.nl_type = g_nlTypeList[i].id;
		item.score = score;
		
		itemMaster.itemList.push(item);
	};
	
	return itemMaster;
}

// 保存能力记录
function saveNlItemMaster(itemMaster) {
	var itemMasterData = encodeURI($.toJSON([itemMaster]));
				
	System.openLoadMask("#contentPanel", "正在保存...");

	$.ajax({
		type: "POST",
		url: System.rootPath+ '/nl/nl!savePersonNlItemMaster.action',
		data: { 
			paramPersonNlItemMasterData: itemMasterData
		},
		success:function(json){
			System.closeLoadMask("#contentPanel");
			
			try {
				if (json.indexOf("保存成功") >= 0) {							
					$.messager.alert('提示', '保存成功!');	
					
					// 刷新数据
					$("#nlItemMasterListTable").datagrid('reload');
					
					g_refreshPersonNl = true;
					
					$('#nlDialog').dialog('close');
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

// 删除能力主表和项目列表
function deletePersonNlItemMaster(row) {
	$.messager.confirm('确定删除', '确定要删除选中的记录？<br/>采集时间：' + row.gather_date + "<br/>采集来源：" + row.from_type_name, function(isOk) {
		if (!isOk) return;
		
		System.openLoadMask("#contentPanel", "正在删除，请稍后...");
		
		$.ajax({
			type: "POST",
			url: System.rootPath+ '/nl/nl!deletePersonNlItemMaster.action',
			data: { 
				personcode: row.personcode,
				id: row.id
			},
			success:function(json){
				System.closeLoadMask("#contentPanel");
				
				try {
					if (json.indexOf("删除成功") >= 0) {							
						$.messager.alert('提示', '删除成功!');	
						
						g_refreshPersonNl = true;
						
						// 刷新数据
						$("#nlItemMasterListTable").datagrid('reload');					
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

function formatScore(score) {
	if (!score || score == 0) {
		return "";
	}
	else {
		return score;
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

function hideCombobox(id){
	$("#" + id + " + .combo").hide();
}

function showCombobox(id){
	 $("#" + id + " + .combo").show();
}