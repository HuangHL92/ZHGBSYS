var g_isNewCategory = false;

$(function(){
	$(".panel-title").css("text-align","center");
	
	init();
});

function init() {
	initCategoryTree();
}


// -------- 分类
function initCategoryTree() {
	var url = System.rootPath + '/query/personquery/person-query!getCategoryTree.action?lazyLoading=true&includeQuery=true';
	var editSetting = {
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false,
			drag: {
				isCopy: false,
				isMove: true,
				prev: false, // 不允许移动到前一节点
				next: false, // 不允许移动到后一节点
				inner: true, // 允许成为子节点
				autoExpandTrigger: true
			}
		};
		
	var setting = {
		url: url,
		data: {
			key : {
				name: 'text'
			}
		},
		callback: {
			onClick: onCategory,
			onRightClick: onCategoryRightClick,
			beforeDrag: onBeforeDrag,
			beforeDrop: onBeforeDrop
		},
		edit: editSetting,
		async: {
			enable: true,
			url: url,
			autoParam:["id=parentCategoryId", "classno=categoryType"]
		}
	};
	
	$.fn.zTree.init($("#ct"), setting);
}

function onCategory(event, treeId, treeNode) {
	if (treeNode.classno == '') {
		var url = System.rootPath + '/query/personquery/person-query!showQuery.action?queryId=' + treeNode.id;
		$("#queryIframe").attr('src', url);
	}
}

function onCategoryRightClick(event, treeId, treeNode) {
	var tree = $.fn.zTree.getZTreeObj('ct');
	tree.selectNode(treeNode);
	$("#categoryRightMenu").menu('show', {
		left: event.clientX, top: event.clientY
	});
}

function onCategoryRightMenuClick(item) {
	if (item.name == 'add') onAddCategory();
	else if (item.name == 'edit') onEditCategory();
	else if (item.name == 'delete') onDeleteCategory();
	else if (item.name == 'order') onOrderCategory();
	else if (item.name == 'reload') onRefreshCategoryTree();
	else if (item.name == 'reloadnode') onRefreshSelectedNode();
	else if (item.name == 'addquery') onAddQuery();
	else if (item.name == 'addquerymanual') onAddQueryManual();
	else if (item.name == 'updatequerymanual') onEditQueryManual();
	else if (item.name == 'deletequery') onDeleteQuery();
	else if (item.name == 'orderquery') onOrderQuery();
}

function onRefreshCategoryTree() {
	initCategoryTree();
}

function onRefreshSelectedNode() {
	var tree = $.fn.zTree.getZTreeObj('ct');
	var nodes = tree.getSelectedNodes();
	if (nodes.length > 0) {
		tree.reAsyncChildNodes(nodes[0], 'refresh');
	}
}

function getSelectedCategory() {
	var tree = $.fn.zTree.getZTreeObj('ct');
	var nodes = tree.getSelectedNodes();
	var node = null;
	if (nodes.length > 0) node = nodes[0];
	
	if (node != null) {
		if (node.classno != '') return node;
	}
	return null;
}

function onAddCategory() {
	var category = getSelectedCategory();
	if (category == null) {
		System.showErrorMsg('请选择一个分类！');
		return;
	}
	
	g_isNewCategory = true;
	openCategoryDialog({ category_type : category.classno, category_id: '', parent_category_id: category.id});
}

function onEditCategory() {
	var category = getSelectedCategory();
	if (category == null) {
		System.showErrorMsg('请选择一个分类！');
		return;
	}
	
	g_isNewCategory = false;
	var cat = {
		category_type: category.classno,
		category_id: category.id,
		category_name: category.text
	};
	
	openCategoryDialog(cat);
}

function onDeleteCategory() {
	var category = getSelectedCategory();
	if (category == null) {
		System.showErrorMsg('请选择一个分类！');
		return;
	}
	
	deleteCategory(category);
}

function saveCategory() {
	var url;
	if (g_isNewCategory) url = System.rootPath + '/query/personquery/create-person-query-category.action';
	else url = System.rootPath + '/query/personquery/update-person-query-category.action';
	
	var isValid = $("#categoryDlgForm").form('validate');
	if (!isValid) {
		return;
	}
	
	System.post(url, $("#categoryDlgForm").serialize(), function(data) {
		$("#categoryDlg").dialog('close');
		
		var selectedCategory = getSelectedCategory();
		
		// 添加节点
		if (g_isNewCategory) {
			var newCategory = data.attributes;
			var tree = $.fn.zTree.getZTreeObj('ct');
			var newNode = { 
				text: newCategory.category_name, 
				id: newCategory.category_id, 
				classno: newCategory.category_type
			};
			tree.addNodes(selectedCategory, newNode);
		} else {
			var name = $("#categoryName").val();
			var tree =$.fn.zTree.getZTreeObj('ct');
			selectedCategory.text = name;
			tree.updateNode(selectedCategory);
		}
	});
}

function deleteCategory(category) {
	System.showConfirmMsg("确定要删除分类：" + category.text + "？", function(){
		System.post(
		System.rootPath + '/query/personquery/delete-person-query-category.action',
			{ 'categoryId': category.id },
			function(data){
				var tree = $.fn.zTree.getZTreeObj('ct');
				tree.removeNode(category);
			}
		);
	});
}

function openCategoryDialog(category) {
	var title = g_isNewCategory ? '新建分类' : '编辑分类';
	if (g_isNewCategory) {
		var parentCategory = getSelectedCategory();
		title += ' - 上级分类：' + parentCategory.text;
	}
	
	$("#categoryDlg").show();
	$('#categoryDlg').dialog({
		title: title,
		width: 480,
		height: 160,
		modal: true,
		buttons:[{
			iconCls:'icon-ok',
			text:'确定',
			handler:function(){
				saveCategory();
			}
		},{
			iconCls:'icon-cancel',
			text:'取消',
			handler:function(){	
				$("#categoryDlg").dialog('close');
			}
		}],
		onOpen : function () {
			$("#categoryDlgForm").form('reset');
			$("#categoryDlgForm").form('load', Utils.cloneObject(category, 'category'));
		}
	});
}

function onOrderCategory() {
	var category = getSelectedCategory();
	if (category == null || category.classno == '') {
		System.showErrorMsg('请选择一个分类！');
		return;
	}
	
	var url = System.rootPath + '/query/personquery/order-person-query-category.action?categoryId=' + category.id + '&categoryType=' + category.classno;
	$("#orderDlg").show();
	$("#orderDlg").dialog({
		modal: true,
		title: '分类排序 - ' + category.text,
		iconCls: 'icon-sort',
		width: 600,
		height: 500,
		onOpen: function() {
			$("#orderIframe").attr('src', url);
		},
		onClose: function() {
			// 刷新选中节点
			var tree = $.fn.zTree.getZTreeObj('ct');
			tree.reAsyncChildNodes(category, 'refresh');
			$("#orderIframe").attr('src', '');
		}
	});
}

function onBeforeDrag(treeId, treeNodes) {
	return true;
}

function onBeforeDrop(treeId, treeNodes, targetNode, moveType) {
	if (treeNodes != null && treeNodes.length > 0 && targetNode) {	
		if (treeNodes[0].classno != targetNode.classno) {
			System.showErrorMsg('只能在类型相同的节点中进行移动！');
			return false;
		}
	
		var msg = '确定移动\"' + treeNodes[0].text + '\"及其下级节点到\"' + targetNode.text + '\"？';
		System.showConfirmMsg(msg, function() {
			if (treeNodes[0].classno != '') {
				moveCategory(treeNodes[0].classno, treeNodes[0].id, targetNode.id);
			}
			else {
			}
		});
	}
	
	return false;
}

function moveCategory(categoryType, categoryId, parentCategoryId) {
	System.post(
		System.rootPath+ '/query/personquery/move-person-query-category.action',
		{
			categoryType: categoryType,
			categoryId: categoryId,
			parentCategoryId: parentCategoryId
		},
		function(data) {
			onRefreshCategoryTree();
		});
}

// ------------ 查询
function getSelectedQuery() {
	var tree = $.fn.zTree.getZTreeObj('ct');
	var nodes = tree.getSelectedNodes();
	var node = null;
	if (nodes.length > 0) node = nodes[0];
	
	if (node != null) {
		if (node.classno == '') return node;
	}
	return null;
}

function onAddQuery() {
	var url = System.rootPath + '/query/personquery/person-query!showQuery.action?newQuery=1';
	$("#queryIframe").attr('src', url);
}

function onAddQueryManual() {
	var category = getSelectedCategory();
	if (category == null) {
		System.showErrorMsg('请选择一个分类！');
		return;
	}
	
	openQueryDialog({ query_id: '', category_id: category.id });
}

function onEditQueryManual() {
	var query = getSelectedQuery();
	if (query == null) {
		System.showErrorMsg('请选择一个查询！');
		return;
	}
	
	var url = System.rootPath + '/query/personquery/person-query!getQueryData.action?queryId=' + query.id;
	$.get(url, function(data) {
		if (data.query_id == '') {
			System.showErrorMsg('查询记录不存在！');
		}
		else {
			openQueryDialog(data);
		}
	});
}

function openQueryDialog(query) {
	var title = query.query_id == '' ? '新建查询' : '编辑查询';
	
	$("#queryDlg").show();
	$('#queryDlg').dialog({
		title: title,
		width: 680,
		height: 380,
		modal: true,
		buttons:[{
			iconCls:'icon-ok',
			text:'确定',
			handler:function(){
				saveQueryByManual(query);
			}
		},{
			iconCls:'icon-cancel',
			text:'取消',
			handler:function(){	
				$("#queryDlg").dialog('close');
			}
		}],
		onOpen : function () {
			$("#queryDlgForm").form('reset');
			$("#queryDlgForm").form('load', Utils.cloneObject(query, 'query'));
		}
	});
}

function saveQueryByManual(query) {
	var url;
	if (query.query_id == '') url = System.rootPath + '/query/personquery/person-query!createQueryByManual.action';
	else url = System.rootPath + '/query/personquery/person-query!updateQueryByManual.action';
	
	var isValid = $("#queryDlgForm").form('validate');
	if (!isValid) {
		return;
	}
	
	System.post(url, $("#queryDlgForm").serialize(), function(data) {
		$("#queryDlg").dialog('close');
		
		if (query.query_id == '') {
			var selectedCategory = getSelectedCategory();
			var tree = $.fn.zTree.getZTreeObj('ct');
			tree.reAsyncChildNodes(selectedCategory, 'refresh');
		}
	});
}

function onDeleteQuery() {
	var query = getSelectedQuery();
	if (query == null) {
		System.showErrorMsg('请选择一个查询！');
		return;
	}
	
	System.showConfirmMsg('确定删除此查询：' + query.text + '？', function() {
		var url = System.rootPath + '/query/personquery/person-query!deleteQuery.action';
		System.post(url, { queryId: query.id }, function(data) {
			var tree = $.fn.zTree.getZTreeObj('ct');
			tree.removeNode(query);
		});
	});
}

function onOrderQuery() {
	var category = getSelectedCategory();
	if (category == null || category.classno == '') {
		System.showErrorMsg('请选择一个分类！');
		return;
	}
	
	var url = System.rootPath + '/query/personquery/person-query!orderQuery.action?categoryId=' + category.id + '&categoryType=' + category.classno;
	$("#orderDlg").show();
	$("#orderDlg").dialog({
		modal: true,
		title: '查询排序 - ' + category.text,
		iconCls: 'icon-sort',
		width: 600,
		height: 500,
		onOpen: function() {
			$("#orderIframe").attr('src', url);
		},
		onClose: function() {
			// 刷新选中节点
			var tree = $.fn.zTree.getZTreeObj('ct');
			tree.reAsyncChildNodes(category, 'refresh');
			$("#orderIframe").attr('src', '');
		}
	});
}