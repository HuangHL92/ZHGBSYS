var PersonQuery = function(query, callback) {
	var _self = this; // 保留自身，其他组件里面的事件，必须用_self代替this
	
	this.query = query; // 干部分类查询对象
	this.userTag = null;
	
	this.dialogId = 'personQueryDlg';
	this.callback = callback;
	
	// 初始化
	PersonQuery.prototype.init = function() {
		_self._init();
	};
	
	// 显示对话框
	PersonQuery.prototype.show = function() {
		_self._show();
	};
	
	// 初始化
	this._init = function() {
		_self._initControl();
	};
	
	// 保存查询
	this._save = function(pushQuery) {
		var isNewQuery = $("#pqdQueryId").val() == '';
		var url;
		if (isNewQuery) url = System.rootPath + '/query/personquery/person-query!createQuery.action';
		else url = System.rootPath + '/query/personquery/person-query!updateQuery.action';
		
		var isValid = $("#personQueryDlgForm").form('validate');
		if (!isValid) {
			System.showErrorMsg('请填写所有必填项目！');
			return;
		}
		
		if (!pushQuery) {
			$("#pqdRecipients").val('');
			$("#pqdPortalIds").val('');
		}
		else {
			var recipients = $("#pqdMsgRecipients").tags('getValues');
			var portalIds = $("#pqdPortal").combobox('getValue');
			
			var users = recipients.join();
			if (users == '' && portalIds == '') {
				System.showErrorMsg('请选择发送目标！');
				return;
			}
			
			$("#pqdRecipients").val(users);
			$("#pqdPortalIds").val(portalIds);
		}
		
		MaskUtil.mask();
		System.post(url, $("#personQueryDlgForm").serialize(), function(data) {
			alert(data.message);
			_self.dialog.dialog('close');
			
			if (_self.callback != null) {
				_self.callback(data.attributes);
			}
		});
	},
	
	// 分类选中事件
	this._onSelectCategory = function(event, treeId, treeNode) {
		$("#pqdCategoryId").val(treeNode.id);
		$("#pqdCategoryName").textbox('setValue', treeNode.text);
	},
	
	// 分类右键事件
	this._onCategoryRightClick = function(event, treeId, treeNode) {
		$("#pqdCategoryMenu").menu('show', {
			left: event.clientX, top: event.clientY
		});
	},
	
	// 分类右键菜单事件
	this._onCategoryMenuClick = function(item) {
		if (item.id == 'pqdCategoryMenuAdd') _self._onAddCategory();
		else if (item.id == 'pqdCategoryMenuEdit') _self._onEditCategory();
		else if (item.id == 'pqdCategoryMenuDelete') _self._onDeleteCategory();
		else if (item.id == 'pqdCategoryMenuRefresh') {
			_self._initCategoryTree();
		}
	},
	
	// 获取选中分类
	this._getSelectedCategory = function() {
		var tree = $.fn.zTree.getZTreeObj('pqdCategoryTree');
		var nodes = tree.getSelectedNodes();
		var node = null;
		if (nodes.length > 0) node = nodes[0];
		
		if (node != null) {
			if (node.classno != '') return node;
		}
		return null;
	},
	
	// 添加分类
	this._onAddCategory = function() {
		var category = _self._getSelectedCategory();
		if (category == null) {
			System.showErrorMsg('请选择一个分类！');
			return;
		}
		
		_self._openCategoryDialog({ 
			parent_category_name: category.text, 
			category_type : category.classno, 
			category_id: '', 
			parent_category_id: category.id
		});
	},
	
	// 编辑分类
	this._onEditCategory = function() {
		var category = _self._getSelectedCategory();
		if (category == null) {
			System.showErrorMsg('请选择一个分类！');
			return;
		}
		
		var cat = {
			category_type: category.classno,
			category_id: category.id,
			category_name: category.text
		};
		
		_self._openCategoryDialog(cat);
	},
	
	// 删除分类
	this._onDeleteCategory = function() {
		var category = _self._getSelectedCategory();
		if (category == null) {
			System.showErrorMsg('请选择一个分类！');
			return;
		}
	
		System.showConfirmMsg("确定要删除分类：" + category.text + "？", function(){
			System.post(
			System.rootPath + '/query/personquery/delete-person-query-category.action',
				{ 'categoryId': category.id },
				function(data){
					var tree = $.fn.zTree.getZTreeObj('pqdCategoryTree');
					tree.removeNode(category);
				}
			);
		});
	},
	
	// 保存分类
	this._saveCategory = function() {
		var isNewCategory = $("#pqdCatDlgCategoryId").val() == '';
		var url;
		if (isNewCategory) url = System.rootPath + '/query/personquery/create-person-query-category.action';
		else url = System.rootPath + '/query/personquery/update-person-query-category.action';
		
		var isValid = $("#pqdCategoryDlgForm").form('validate');
		if (!isValid) {
			return;
		}
		
		System.post(url, $("#pqdCategoryDlgForm").serialize(), function(data) {
			$("#pqdCategoryDlg").dialog('close');
			
			var selectedCategory = _self._getSelectedCategory();
			
			// 添加节点
			if (isNewCategory) {
				var newCategory = data.attributes;
				var tree = $.fn.zTree.getZTreeObj('pqdCategoryTree');
				var newNode = { 
					text: newCategory.category_name, 
					id: newCategory.category_id, 
					classno: newCategory.category_type
				};
				tree.addNodes(selectedCategory, newNode);
			} else {
				var name = $("#pqdCatDlgCategoryName").val();
				var tree =$.fn.zTree.getZTreeObj('pqdCategoryTree');
				selectedCategory.text = name;
				tree.updateNode(selectedCategory);
			}
		});
	}
	
	// 打开分类对话框
	this._openCategoryDialog = function(category) {
		var isNewCategory = category.category_id == '';
		var title = isNewCategory ? '新建分类' : '编辑分类';
		if (isNewCategory) title += ' - 上级分类：' + category.parent_category_name;
		
		$("#pqdCategoryDlg").show();
		$('#pqdCategoryDlg').dialog({
			title: title,
			width: 480,
			height: 160,
			modal: true,
			buttons:[{
				iconCls:'icon-ok',
				text:'确定',
				handler:function(){
					_self._saveCategory();
				}
			},{
				iconCls:'icon-cancel',
				text:'取消',
				handler:function(){	
					$("#pqdCategoryDlg").dialog('close');
				}
			}],
			onOpen : function () {
				$("#pqdCategoryDlgForm").form('reset');
				$("#pqdCategoryDlgForm").form('load', Utils.cloneObject(category, 'category'));
			}
		});
	},
	
	// -------------- 用户相关
	this._onAddUser = function() {
		_self._showUserDialog();
	},
	
	this._addSelectedUsers = function() {
		var tree = $.fn.zTree.getZTreeObj('padUserTree');
		var nodes = tree.getCheckedNodes();
		
		if (nodes != null && nodes.length > 0) {
			$(nodes).each(function(index, node) {
				if (node.attributes.nodeType == 'user') {
					_self.userTag.tags('addTag', node.text, node.id);
				}
			});
		}
		
		$("#pdgUserTreeDlg").dialog('close');
	},
	
	this._onClearUser = function() {
		_self.userTag.tags('removeAllTags');
	},
	
	this._showUserDialog = function() {
		$("#pdgUserTreeDlg").show();
		$("#pdgUserTreeDlg").dialog({
			shadow:true,
			modal :true,
			resizable:true,
			width: 320,
			height: 380,
			title: '用户选择',
			buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					_self._addSelectedUsers();					
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#pdgUserTreeDlg').dialog('close');
				}
			}],
			onOpen : function () {
				var plat_setting = {
					check: {
						enable: true,
						nocheckInherit: false,
						chkboxType: { "Y" : "s", "N" : "s" } // 勾选不影响父节点，取消影响子节点
					},
					data: {
						key : {
							name: 'text'
						}
					},
					async: {
						enable: true,
						url: System.rootPath + '/security/access-right!getPlatUnitUserTree.action?lazyLoading=true',
						autoParam:["code=parentUnitCode"]
					}
				};
				
				$.fn.zTree.init($("#padUserTree"), plat_setting);
			}
		});
	},
	
	// 弹出对话框
	this._show = function() {
		_self.dialog.dialog({  
			title: '保存查询',  
			width: 800,  
			height: 480,  
			content: _self.dialogContent,
			iconCls: 'icon-save',
			closed: true,
			modal: true,
			buttons:[{
				iconCls:'icon-save',
				text:'保存',
				handler:function(){
					_self._save(false);
				}
			},{
				iconCls:'icon-send',
				text:'保存并发送',
				handler:function(){
					_self._save(true);
				}
			},{
				iconCls:'icon-cancel',
				text:'取消',
				handler:function(){	
					_self.dialog.dialog('close');
				}
			}],
			onOpen: function() {
				_self._initCategoryTree();
				$("#pqdCategoryMenu").menu({
					onClick: _self._onCategoryMenuClick
				});
				// fxxk: 非得这里初始化tabs，否则第一个tab，对话框刚打开的时候，无法对齐
				$("#pqdTabs").tabs({
					fit: true
				});
				
				$("#pqdBtnAddUser").linkbutton({
					onClick: _self._onAddUser
				});
				
				$("#pqdBtnClearUser").linkbutton({
					onClick: _self._onClearUser
				});
				
				$("#pqdPortal").combobox({
					url: System.rootPath + '/plat/portal-list.action',
					textField: 'portal_name',
					valueField: 'portal_id',
					editable: false,
					width: 200
				});
				
				_self.userTag = $("#pqdMsgRecipients").tags();
				$("#personQueryDlgForm").form('reset');
				$("#personQueryDlgForm").form('load', Utils.cloneObject(_self.query, 'query'));
			}
		});
		
		_self.dialog.dialog('open');
	};
	
	// 初始化其他
	this._initControl = function() {
		// 创建对话框内容
		var content = '';
		content += "<div class='easyui-layout' fit='true'>";
		content += "	<div region='west' title='查询分类' border='false' split='true' style='width:200px;'>";
		content += "		<ul id='pqdCategoryTree' class='ztree'></ul>";
		content += '		<div id="pqdCategoryMenu" style="width:120px;display:hidden;">';
		content += '			<div id="pqdCategoryMenuAdd" iconCls="icon-add">新建分类</div>';
		content += '			<div id="pqdCategoryMenuEdit" iconCls="icon-edit">编辑分类</div>';
		content += '			<div id="pqdCategoryMenuDelete" iconCls="icon-remove">删除分类</div>';
		content += '			<div class="menu-sep"></div>';
		content += '			<div id="pqdCategoryMenuRefresh" iconCls="icon-reload">刷新</div>';
		content += '		</div>';
		content += '	</div>';
		
		content += "	<div region='center' border='false' style='overflow: hidden;'>";
		content += "		<div id='pqdTabs'>";
		content += "			<div title='查询信息' iconCls='icon-search'>";
		content += "				<form id='personQueryDlgForm' name='personQueryDlgForm'>";
		content += "					<input type='hidden' id='pqdCategoryId' name='query.category_id'/>";
		content += "					<input type='hidden' id='pqdQueryId' name='query.query_id'/>";
		content += "					<input type='hidden' id='pqdTables' name='query.query_tables'/>";
		content += "					<input type='hidden' id='pqdSql' name='query.query_sql'/>";
		content += "					<input type='hidden' id='pqdRecipients' name='pushRecipients'/>";
		content += "					<input type='hidden' id='pqdPortalIds' name='pushPortalIds'/>";
		content += "					<table class='inputtable'>";
		content += "						<tr><th>选中查询分类：</th><td><input id='pqdCategoryName' name='query.category_name' class='easyui-textbox' required='true' style='width:200px;'/></td></tr>";
		content += "						<tr><th width='100'>查询类型：</th>";
		content += "							<td><select id='pdqQueryType' name='query.query_type' class='easyui-combobox' required='true' editable='false' style='width:200px;'>";
		content += "								<option value='0'>实时查询</option>";
		content += "								<option value='1'>快照查询</option>";
		content += "							</select></td>";
		content += "						</tr>";
		content += "						<tr><th>查询名称：</th><td><input id='pqdQueryName' name='query.query_name' class='easyui-textbox' required='true' style='width:300px;'/></td></tr>";
		content += "						<tr><th>查询备注：</th><td><input id='pdqQueryRemarks' name='query.query_remarks' class='easyui-textbox' multiline='true' style='width:430px;height:200px;'></td></tr>";
		content += "					</table>";
		content += "				</form>";
		content += '				<div id="pqdCategoryDlg" style="display:none;" class="dialog">';
		content += '					<form id="pqdCategoryDlgForm" name="pqdCategoryDlgForm">';
		content += '						<input type="hidden" id="pqdCatDlgCategoryType" name="category.category_type"/>';
		content += '						<input type="hidden" id="pqdCatDlgCategoryId" name="category.category_id"/>';
		content += '						<input type="hidden" id="pqdCatDlgParentCategoryId" name="category.parent_category_id"/>';
		content += '						<table class="inputtable" width="100%">';
		content += '							<tr>';
		content += '								<th>分类名称</th>';
		content += '								<td><input id="pqdCatDlgCategoryName" name="category.category_name" class="easyui-textbox" required="true" style="width:250px;"></td>';
		content += '							</tr>';
		content += '						</table>';
		content += '					</form>';
		content += '				</div>';
		content += '			</div>';
		
		content += '			<div title="发送信息" iconCls="icon-send">';
		content += '				<table class="inputtable">';
		content += '					<tr>';
		content += '						<th width="100" rowspan="2">发送给用户</th>';
		content += '						<td><div id="pqdMsgRecipients" class="tags" style="width:430px;height:80px;"></td>';
		content += '					</tr>';
		content += '					<tr><td>';
		content += '						<a href="javascript:void(0);" id="pqdBtnAddUser" iconCls="icon-add">添加用户</a>';
		content += '						<a href="javascript:void(0);" id="pqdBtnClearUser" iconCls="icon-remove">清空用户</a>';
		content += '						</td>';
		content += '					</tr>';
		content += '					<tr><th>推送到门户</th>';
		content += '						<td><select id="pqdPortal"></select></td>';
		content += '					</tr>';
		content += '				</table>';
		content += '				<div class="alert-blue">温馨提示：<br/>1. 系统发送的数据为数据链接，一旦原始数据被删除，将无法访问；<br/>2. 推送到门户，需管理员审核后才能正式发布！</div>';
		content += '			</div>';
		content += '		</div>';
		content += '		<div id="pdgUserTreeDlg" style="display:none;">';
		content += '		<ul id="padUserTree" class="ztree"></ul>';
		content += '		</div>';
		content += '	</div>';
		content += '</div>';
		
		if ($('#' + _self.dialogId).length <= 0) {
			$("body").append("<div id='personQueryDlg'></div>");	
		}
		
		_self.dialogContent = content;
		_self.dialog = $('#' + _self.dialogId);
	};
	
	// 初始化分类树
	this._initCategoryTree = function() {
		var url = System.rootPath + '/query/personquery/person-query!getCategoryTree.action?lazyLoading=true&includeQuery=false';
		var setting = {
			url: url,
			data: {
				key : {
					name: 'text'
				}
			},
			callback: {
				onClick: _self._onSelectCategory,
				onRightClick: _self._onCategoryRightClick
			},
			async: {
				enable: true,
				url: url,
				autoParam:["id=parentCategoryId", "classno=categoryType"]
			}
		};
		
		$.fn.zTree.init($("#pqdCategoryTree"), setting);
	};
	
	// 执行初始化
	this.init();
};