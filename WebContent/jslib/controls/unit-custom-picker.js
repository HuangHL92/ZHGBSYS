var UnitCustomPicker = function(options) {
	var _self = this; // 保留自身，其他组件里面的事件，必须用_self代替this
	
	this.unitType = options.unitType;
	this.dialogId = 'pickUnitCustomDlg';
	this.unitTreeId = 'pickUnitCustomTree';
	this.unitSearchBox = 'pickUnitCustomSearchBox';
	this.unitTextField = options.textField;
	this.unitValueField = options.valueField;
	this.onSelect = options.onSelect;
	this.onClear = options.onClear;
	if (options.multiple == 'undefined') {
		this.multiple = false;
	}
	else {
		this.multiple = options.multiple;
	}
	
	// 初始化
	UnitCustomPicker.prototype.init = function() {
		_self._init();
	};
	
	// 选择单位
	UnitCustomPicker.prototype.pickUnit = function() {
		_self._pickUnit();
	};
	
	// 选择单位
	this._selectUnit = function(unit) {
		if (_self.onSelect) {
		//if (_self.onChange){
			_self.onSelect(unit);
		}
		else {
			if (!_self.multiple) {
				$("#" + _self.unitValueField).val(unit.id);
				_self.unitTextBox.val(unit.name);
			}
		}
		
		_self.unitDialog.dialog('close');
	};
	
	// 清除单位
	this._clearUnit = function() {	
		if (_self.onClear) {
			_self.onClear();
		}
		else {
			$("#" + _self.unitValueField).val('');
			_self.unitTextBox.textbox('setValue', '');
		}
	};
	
	// 选择单位
	this._pickUnit = function() {
		_self.unitDialog.dialog({  
			title: !_self.multiple ? '选择单个单位 - 双击选择' : '选择多个单位',  
			width: 540,  
			height: 320,  
			content: _self.dialogContent,
			closed: true,
			modal: true, 
			buttons: _self._getButtons(),
			onOpen: function() {
				_self._initUnitTree();
				
				if (!_self.multiple) {
					$("#" + _self.unitSearchBox).focus();
				}
			}
		});
		
		_self.unitDialog.dialog('open');
	};
	
	// 初始化
	this._init = function() {
		_self._initTextBox();
		_self._initPicker();
	};
	
	// 初始化用户textbox
	this._initTextBox = function() {
		var tb = $("#" + _self.unitTextField);
		
		if (tb.length > 0) {
			_self.unitTextBox = $("#" + _self.unitTextField);
			
			// 绑定click事件
			var t = _self.unitTextBox;
			t.unbind('click').bind('click', function() {
				_self._pickUnit();
			});
		}
	};
	
	// 初始化其他
	this._initPicker = function() {
		if ($('#' + _self.dialogId).length <= 0) {
			var content = "<div id='pickUnitCustomDlg'>";
			$("body").append(content);	
		}
		
		_self.unitDialog = $('#' + _self.dialogId);
		
		// 对话框内容
		var content = '';
		content += "<div class='easyui-layout' fit='true'>";
		if (!_self.multiple) {
			content += "<div region='north' border='false' style='height:40px; padding:1px;'>";
			content += "按单位名称快速搜索：<input id='pickUnitCustomSearchBox' iconCls='icon-search' style='width:350px;' prompt='输入姓名或者姓名首字母拼音进行搜索'/>";
			content += "</div>";
		}
		content += "<div region='center' border='false'>";
		content += "<ul id='pickUnitCustomTree' class='ztree'></ul> ";
		content += "</div>";
		content += "</div>";
		content += "</div>";
		_self.dialogContent = content;
	};
	
	// 获取对话框按钮
	_self._getButtons = function() {
		if (!_self.multiple) {
			return [{
				text: '确定',
				iconCls: 'icon-ok',
				handler: function(){
					var zTree = getZTreeObj('pickUnitCustomTree');
					var nodes = zTree.getSelectedNodes();
					if (nodes.length >= 1) {
						var unit = { id: nodes[0].code, name : nodes[0].text};
						_self._selectUnit(unit);
					}
				}
			}, {
				text: '取消',
				iconCls: 'icon-cancel',
				handler: function(){
					_self.unitDialog.dialog('close');
				}
			}];
		
		}
		else {
			return [{
				text: '取消选择',
				iconCls: 'icon-remove',
				handler: function(){
					var zTree = getZTreeObj(_self.unitTreeId);
					var nodes = zTree.getCheckedNodes();
					if (nodes) {
						$(nodes).each(function(index, node) {
							zTree.checkNode(node, false);
						});
					}
				}
			},
			{
				text: '展开全部',
				iconCls: 'icon-expand',
				handler: function(){
					var zTree = getZTreeObj(_self.unitTreeId);
					zTree.expandAll(true);
				}
			},{
				text: '选择选中单位',
				iconCls: 'icon-ok',
				handler: function(){
					var units = new Array();
					var zTree = getZTreeObj('pickUnitCustomTree');
					var nodes = zTree.getCheckedNodes();
					if (nodes) {
						$(nodes).each(function(index, node) {
							if (!node.isParent) {
							    var unit = { id: node.code, name: node.text };
							    units.push(unit);
							}
						});
						
						if (units.length == 0) {
							System.showErrorMsg("请至少选择一个单位！");
						}
						else {
							_self._selectUnit(units);
						}
					}
					else {
						System.showErrorMsg("请至少选择一个单位！");
					}
				}
			}];
		}
	};
	
	// 初始化单位树
	this._initUnitTree = function() {
		var checkSetting = {};
		if (_self.multiple) {
			checkSetting = {
				enable: true,
				nocheckInherit: false,
				chkboxType: { "Y" : "ps", "N" : "ps" } // 勾选节点，不影响父节点和子节点
			};
		}
		
		$("#" + _self.unitTreeId).UnitCustomTree({
			searchBoxId: _self.unitSearchBox,
			treeType: _self.unitType,
			checkSetting: checkSetting,
			onDblClick: function(event, treeId, node) {
				if (!_self.multiple) {
					if (node.id) {
						var unit = { id: node.code, name : node.text};
						_self._selectUnit(unit);
					}
				}
			}
		});
	};
	this.getUnitSearch = function(unit) {
		$("#" + _self.unitValueField).val(unit.code);
		_self.unitTextBox.textbox('setValue', unit.name);
	};
	// 执行初始化
	this.init();
};