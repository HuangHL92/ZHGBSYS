/* Portal */

var Portal = function(options) {
	var _self = this; // 保留自身，其他组件里面的事件，必须用_self代替this
	this.defaultOptions = {
		portalId: '',
		widgets: [],
		onChange: function() {}
	};
	
	this.options = options; //$.extend(options, this.defaultOptions);
	
	this.widgets = options.widgets; // 初始化的widget
	this.portal = null;
	this.widgetDialog = null;
	
	// 初始化
	Portal.prototype.init = function() {
		_self._init();
	};
	
	// 重新设置大小
	Portal.prototype.resize = function() {
		_self.portal.portal('resize');
	};
	
	// 选择widget
	Portal.prototype.selectWidget = function() {
		if (_self.widgetDialog == null) {
			$(document.body).append('<div id="widgetDlg" style="display:none;"><table id="widgetList"></table></div>');
		}
		
		_self.widgetDialog = $("#widgetDlg").dialog({
			title: '添加部件',
			width: 640,
			height: 480,
			iconCls: 'icon-widget',
			modal: true,
			buttons:[{
				iconCls:'icon-ok',
				text:'确定',
				handler:function(){
					var widgets = $("#widgetList").datalist('getSelections');
					if (widgets.length > 0) {
						_self.addWidgets(widgets);
						$("#widgetDlg").dialog('close');
					}
				}
			},{
				iconCls:'icon-cancel',
				text:'取消',
				handler:function(){	
					$("#widgetDlg").dialog('close');
				}
			}],
			onOpen : function () {
				$("#widgetList").datalist({
					url: System.rootPath + '/plat/widget-list.action',
					loadMsg: '载入中...',
					checkbox: true,
					lines: true,
					rownumbers: true,
					border: false,
					singleSelect: false,
					textField: 'widget_name',
					valueField: 'widget_id',
					textFormatter: function(value, row, index){
						try {
							return "<b style='font-size:14px;'>" + row.widget_name + "</b><br/><span>" + row.widget_description + "</span>";
						}
						catch (e) {
						}
					}
				});
			}
		});
	};
	
	// 添加一批部件
	Portal.prototype.addWidgets = function(widgets) {
		for (var i = 0; i < widgets.length; i++) {
			_self._addOneWidget(widgets[i], false);
		}
		
		_self.portal.portal('resize');
		
		_self._onWidgetChanged();
	},
	
	// 添加部件
	this._addOneWidget = function(widget) {
		// 首先检测有没有添加该部件
		var panels = _self.portal.portal('getPanels');
		var existed = false;
		$(panels).each(function(index, v) {
			var ifr = $(this).find(".widgetIframe");
			if (ifr.attr('pid') == widget.widget_id) {
				existed = true;
				return;
			}
		});
		
		if (existed) return;
		
		// 把部件添加到最少部件的那一列去
		var columnIndex = 0;
		var first = _self.portal.portal('getPanels', 0).length;
		var second = _self.portal.portal('getPanels', 1).length;
		var three = _self.portal.portal('getPanels', 2).length;
		if (first < second && first < three) columnIndex = 0;
		else if (second < first && second < three) columnIndex = 1;
		else if (three < first && three < second) columnIndex = 2;
		
		_self._addWidget(widget, columnIndex);
	},
	
	// 内部初始化
	this._init = function() {
		_self.portal = $('#' + _self.options.portalId).portal({
			border:false,
			fit:true,
			onStateChange: _self._onWidgetChanged
		});
		
		// 创建portal
		if (_self.widgets != null) {
			for (var i = 0; i < _self.widgets.length; i++) {
				_self._addWidget(_self.widgets[i], _self.widgets[i].column_index);
			}
				
			_self.portal.portal('resize');
		}
	};
	
	// 添加部件到Portal
	this._addWidget = function(widget, columnIndex) {
		var url = '';
		if (widget.widget_url !="#") url = widget.app_url + widget.widget_url;
		
		var widgetId = widget.widget_id;
		widgetId = widgetId.replaceAll('-', '');
		
		$(document.body).append('<div id="' + widgetId + '"/>');
		var tools = [{
				iconCls:'icon-refresh',
				handler:function(){
					$("#iframe" + widgetId).attr('src', $("#iframe" + widgetId).attr('src'));
				}
			}];
			
		if (widget.widget_more_url != null && widget.widget_more_url != '#') {
			tools.push({
				iconCls:'icon-add',
				handler:function(){
					System.openURL(widget.widget_name, widget.app_url + widget.widget_more_url, null, false, widget.widget_icon_class);
				}
			});
		}
		
		var p = $("#" + widgetId).panel({
			title: widget.widget_name,
			iconCls: widget.widget_icon_class,
			height:250,
			content: '<iframe id="iframe' + widgetId + '" pid="' + widget.widget_id + '" class="widgetIframe" scrolling="auto" frameborder="0" style="width:100%;height:100%;display:block;" src="' + url + '"></iframe>',
			closable: widget.widget_type == 0,
			collapsible:false,
			cls: 'theme-border-radius',
			onDestroy: _self._onWidgetChanged,
			onBeforeClose: function() {
				if (confirm('你确定要移除此部件？')) {
					return true;
				}
				else {
					return false;
				}
			},
			onClose: function() {
				$("#" + widgetId).panel('destroy', true);
				
			}
			,tools: tools
		});
		
		$('#pp').portal('add', {
			panel:p,
			columnIndex: columnIndex
		});
	};
	
	// Portal更改事件
	this._onWidgetChanged = function() {
		var widgetIds = [];
		var positions = [];
		
		// 按列获取
		for (var i = 0; i < 3; i++) {
			var panels = _self.portal.portal('getPanels', i);
			$(panels).each(function(index, v) {
				var ifr = $(this).find(".widgetIframe");
				widgetIds.push(ifr.attr('pid'));
				positions.push({ row: index, column: i});
			});
		}
		
		var ids = widgetIds.join();
		var pos = '';
		for (var i = 0; i < positions.length; i++) {
			if (pos == '') pos = positions[i].row + ':' + positions[i].column;
			else pos = pos + ',' + positions[i].row + ':' + positions[i].column;
		}
		
		_self.options.onChange(ids, pos);
		
		//var url = System.rootPath + '/plat/save-widget.action';
		//System.post(url, { widgetIds: ids, positions: pos }, function(data) {
		//});
	};
	
	// 执行初始化
	this.init();
};