/**
* 自定义单位树控件
*/

$.widget("bits.UnitCustomTree", {
	// 缺省参数
	options: {
		url: '', // 这个参数不需要设置
		searchBoxId: '', // 搜索ID
		showRoot: false, // 是否显示根节点
		lazyLoading: false, // 是否延迟加载
		editSetting: {}, // 编辑设置
		onSelect: function() {}, // 选中单位事件
		beforeDrag: function() {}, // 拖动前事件
		beforeDrop: function() {} // 拖动放下前事件
	},
	
	_create: function() {
	},
	
	// 初始化
	_init: function() {
		// 初始化查询
		if (this.options["searchBoxId"] != '') {
			$("#" + this.options["searchBoxId"]).searchbox({
				prompt: '输入单位名进行搜索，至少两个关键字',
				searcher: this._functionDelegate(this, this.onUnitSearch)
			});
		}
		
		this.loadTree();
	},
	
	//  函数调用委托，内部function调用，必须通过这个委托进行传递
	_functionDelegate: function(self, fn) {
		return function() {
			if ($.isFunction(fn)) {
				fn.apply(self, arguments);
			}
		};
	},
	
	loadTree: function() {
		var id = this.element.attr('id');
		var setting = this.getSetting(false);
		
		// 不是延迟加载，那么一次性载入
		if (!setting.lazyLoading) {
			$.get(setting.url, {}, function(data) {
				$.fn.zTree.init($("#" + id), setting, data);
			});		
		}
		else {
			$.fn.zTree.init($("#" + id), setting);
		}
	},
	
	// 获取单位树参数
	getSetting: function(isSearching) {
		var setting = {
			data: {
				key : {
					name: 'text'
				}
			}
		};
		
		// 构造单位树参数
		var lazyLoading = this.options["lazyLoading"];
		var showRoot = this.options["showRoot"];
		
		// 获取URL
		var url = System.rootPath + '/unit/unit-custom-tree.action';
		
		url += '?showRoot=';
		url += showRoot ? 'true' : 'false';
		url += '&lazyLoading=';
		url += lazyLoading ? 'true' : 'false';
		
		setting.url = url;
		
		// 编辑设置
		setting.edit = this.options["editSetting"];
		
		// 事件
		setting.callback = {
			onClick: this.options["onSelect"],
			beforeDrag: this.options["beforeDrag"],
			beforeDrop: this.options["beforeDrop"]
		};
		
		// 不是查询，看是不是异步加载		
		if (!isSearching) {
			if (lazyLoading) {
				setting.async = {
					enable: true,
					url: url,
					autoParam:["code=code"]
				};
			}
		}
		
		return setting;
	},
	
	// 获取单位树对象，方便自定义处理
	getUnitTreeObject: function() {
		return System.getZTreeObj(this.element.attr('id'));
	},
	
	onUnitSearch: function(value, name) {
		value = value.trim();
		var id = this.element.attr('id');
		
		if (value != "" && value.length < 2){
			System.showInfoMsg("请最少输入两个关键字进行搜索！");				
		}
		else if (value == "") {
			// 重新载入单位树
			this.loadTree();
		}
		else {		
			var setting = this.getSetting(true);
			$.get(setting.url, { unitName : value }, function(data) {
				if (data.length == 0) {
					System.showInfoMsg("没有找到符合条件的单位！");
				}
				else {
					$.fn.zTree.init($("#" + id), setting, data);		
				}
			});				
		}	
	}
});