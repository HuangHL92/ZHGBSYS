var g_sysUnitData = null;

var Unit = {
		addUnit:function(){
			$("#unitForm").form('reset');
			$("#uid").val('');
			$("#btn_remove").linkbutton("disable");
			$("#btn_add").linkbutton("disable");
            $("#btn_save").linkbutton("enable");
		},
		addSysUnit: function() {
			if ($('#currentUnitId').val() != '0') {
				showSysUnitDialog();
			}
			else {
				System.showErrorMsg('请选择一个不是根节点的单位进行添加！');
			}
		},
		removeData : function(){
			System.showConfirmMsg("确定要删除单位："+$("#name").val()+"吗？<br/><b>注意：所有下级单位将一并删除！</b>", function(){
				System.post(
					System.rootPath + '/unit/unit-custom!delete.action',
					{ 'unitId': $('#currentUnitId').val() },
					function(data){
						var tree =$.fn.zTree.getZTreeObj('tt');
						var nodes = tree.getNodesByParam("id", $('#currentUnitId').val());
						if (nodes.length > 0) {
							tree.removeNode(nodes[0]);
							$('#name').textbox('setValue', '');
							$("#btn_save").linkbutton("disable");
							$("#btn_remove").linkbutton("disable");
							$("#btn_add").linkbutton("disable");
							$("#btn_order").linkbutton("disable");
						};
					}
				);
			});
		},
		init:function(){
			if ($('#currentUnitId').val() == '0') {
				$("#unitForm").form('reset');
				$("#btn_remove").linkbutton("disable");
				$("#btn_add").linkbutton("enable");
				$("#btn_addSysUnit").linkbutton("disable");
				$("#btn_save").linkbutton("disable");
			}
			else {
				System.openLoadMask("#pageWin");
				$.ajax({
					url : System.rootPath + '/unit/unit-custom!getUnitById.action',
					type : 'post',
					dataType : 'json',
					data : {'unitId' : $('#currentUnitId').val()},
					success:function(unit){
						if (unit != null) {
							$("#btn_add").linkbutton("enable");
							$("#btn_addSysUnit").linkbutton("enable");
							$("#btn_save").linkbutton("enable");
							$("#btn_remove").linkbutton("enable");
						
							$("#unitForm").form('load', Utils.cloneObject(unit, 'unit'));
						}
						else {
							System.showErrorMsg('单位不存在，请刷新页面后重试！');
						}
					},
					error:function(){
						$.messager.alert("错误","网络错误!","error");
					},
					complete:function(){
						System.closeLoadMask("#pageWin");
					}
				});
			}
		},
		saveData : function(){
			if (getSelectedNode() == null) {
				System.showErrorMsg('请选择单位！');
				return;
			}
		
			var isNew = ($("#uid").val() == "");
			if (!$("#unitForm").form('validate')) {
				return;
			}
			
			var url;
			if (isNew) {
				$("#parentUnitCode").val($("#currentUnitCode").val());
				url = System.rootPath + '/unit/unit-custom!create.action';
			}
			else {
				url = System.rootPath + '/unit/unit-custom!update.action';
			}
			
			System.openLoadMask("#pageWin", '保存中...');
			System.post(
				url,
				$("#unitForm").serialize(),
				function(data) {
					System.closeLoadMask("#pageWin");
					var node = getSelectedNode();
					if (isNew) {
						var newUnit = data.attributes;
						var id = newUnit.id; // 新ID
						var tree = $.fn.zTree.getZTreeObj('tt');
						var newNode = { 
							text: newUnit.name, 
							id: id,
							code: newUnit.code,
							fatherCode: newUnit.parent_code,
							attributes: {
								unitType: newUnit.unit_type,
								sysUnitCode: null
							}
						};
						tree.addNodes(node, newNode);
						$("#unitForm").form('reset');
					} else {
						var name = $("#name").val();
						var tree =$.fn.zTree.getZTreeObj('tt');
						node.text = name;
						tree.updateNode(node);
					}
				},
				function(data) {
					System.closeLoadMask("#pageWin");
				}
			);
		},
		
		orderUnit : function(){
			var treeNode = getSelectedNode();
			if (treeNode != null) {
				var url = System.rootPath + '/unit/unit-custom!order.action?parentCode=' + treeNode.code;
				$("#orderDlg").show();
				$("#orderDlg").dialog({
					modal: true,
					title: '单位排序 - ' + treeNode.text,
					width: 600,
					height: 500,
					onOpen: function() {
						$("#orderIframe").attr('src', url);
					},
					onClose: function() {
						initUnitTree();
						$("#orderIframe").attr('src', '');
					}
				});
			}
			else {
				System.showErrorMsg('请选择一个单位！');
			}
		}
};

$(function(){
	$(".panel-title").css("text-align","center");
	initUnitTree();
	$("#btn_remove").linkbutton("disable");
	$("#btn_save").linkbutton("disable");
	$("#btn_add").linkbutton("disable");
	$("#btn_addSysUnit").linkbutton("disable");
	$("#btn_order").linkbutton("disable");
});

function onUnitClick(event, treeId, node) {
	$("#btn_add").linkbutton("enable");
	$("#btn_order").linkbutton("enable");
	
	if (node.id != '') {
		$("#currentUnitId").val(node.id);
		$("#currentUnitCode").val(node.code);
		Unit.init();
	} 
}

function initUnitTree() {
	var editSetting = {
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false,
			drag: {
				isCopy: false,
				isMove: canMoveUnit,
				prev: false, // 不允许移动到前一节点
				next: false, // 不允许移动到后一节点
				inner: true, // 允许成为子节点
				autoExpandTrigger: true
			}
		};
	
	$("#tt").UnitCustomTree({
		showRoot: true,
		lazyLoading: true,
		//editSetting: editSetting,
		onSelect: onUnitClick
		//beforeDrag: beforeUnitDrag,
		//beforeDrop: beforeUnitDrop
	});				
}

function getSelectedNode() {
	var tree = $.fn.zTree.getZTreeObj('tt');
	var nodes = tree.getSelectedNodes();
	var node = null;
	if (nodes.length > 0) node = nodes[0];
	return node;
}

function beforeUnitDrag(treeId, treeNodes) {
	return true;
}

function beforeUnitDrop(treeId, treeNodes, targetNode, moveType) {
	if (treeNodes != null && treeNodes.length > 0 && targetNode) {	
		var msg = '确定移动\"' + treeNodes[0].text + '\"及其下级组织机构到\"' + targetNode.text + '\"？';
		System.showConfirmMsg(msg, function() {
			moveUnit(treeNodes[0].id, targetNode.code);
		});
	}
	
	return false;
}

function moveUnit(unitId, parentCode) {
	System.post(
		System.rootPath+ '/unit/unit-custom!move.action',
		{
			unitId: unitId,
			parentUnitCode: parentCode
		},
		function(data) {
			initUnitTree();
		});
}

function showSysUnitDialog() {
	$("#unitDlg").show();
	$('#unitDlg').dialog({
		title: '添加系统单位',
		width: 640,
		height: 480,
		iconCls: 'icon-add',
		modal: true,
		buttons:[{
			iconCls:'icon-ok',
			text:'确定',
			handler:function(){
				saveSysUnit();
			}
		},{
			iconCls:'icon-cancel',
			text:'取消',
			handler:function(){	
				$("#unitDlg").dialog('close');
			}
		}],
		onOpen : function () {
			initSysUnitTree();
		}
	});
}

function initSysUnitTree() {
	var setting = {
		check: {
			enable: true,
			nocheckInherit: false,
			chkboxType: { "Y" : "s", "N" : "s" } // 勾选不影响父节点，取消影响子节点
		},
		callback: {
			onCheck: onUnitCheck
		},
		data: {
			key : {
				name: 'text'
			}
		}
	};
	
	var url = System.rootPath + "/common/unit-tree!getAccessiableUnitTree.action?treeType=3";
	if (g_sysUnitData == null) {
		$.get(url, function(data) {
			g_sysUnitData = data;
			$.fn.zTree.init($("#ut"), setting, g_sysUnitData);
		});
	}
}

function onUnitCheck(event, treeId, treeNode) {
	// 这里我们只处理取消，如果子节点取消，必须把父节点都取消
	if (!treeNode.checked) {
		Utils.cancelParentNodeChecked('ut', treeNode);
	}
}

function saveSysUnit() {
	var addType = $("#addSysUnitType").combobox('getValue');
	var sysUnitCodes = getSelectUnitCodes(addType);
	if (sysUnitCodes == '') {
		System.showErrorMsg('请选择至少一个系统单位！');
		return;
	}
	
	MaskUtil.mask('正在添加系统单位...');
	System.post(
		System.rootPath + '/unit/unit-custom!addSysUnits.action',
		{ 
			'unitId' : $("#currentUnitId").val(),
			'sysUnitCodes': sysUnitCodes,
			'addSysUnitType': addType
		},
		function(data){
			$("#unitDlg").dialog('close');
			initUnitTree();
		}
	);
}

function getSelectUnitCodes(addType) {
	var tree = getZTreeObj("ut");
	var nodes = tree.getCheckedNodes();
	
	if (addType == 1) {
		var sysUnitCodes = '';
		$(nodes).each(function(index, node) {
			if (sysUnitCodes == '') sysUnitCodes = node.id;
			else sysUnitCodes = sysUnitCodes + "," + node.id;
		});
		return sysUnitCodes;
	}
	
	// 获取每个节点下，最顶层选择节点即可
	// 因为本来选中的节点，就是用上往下遍历得来的
	var map = new Hashtable();
	var topUnitCodes = [];
	$(nodes).each(function(index, node) {
		var code = node.id;
		var codeLen = code.length;
		var step = codeLen / 3;
		
		// 先看父节点有没有被添加
		for (var i = 0; i < step - 1; i++) {
			var parentCode = code.substring(0, (i + 1) * 3);
			// 找到，那么跳过此节点
			if (map.containsKey(parentCode)) {
				return;
			}
		}
		
		// 没有找到，那么这个节点就是最顶层节点
		map.put(code, code);
		topUnitCodes.push(code); // 用个数组保存，这样将保持原来的顺序
	});
	
	var sysUnitCodes = '';
	$(topUnitCodes).each(function(index, key) {
		if (sysUnitCodes == '') sysUnitCodes = key;
		else sysUnitCodes = sysUnitCodes + "," + key;
	});
		
	return sysUnitCodes;
}