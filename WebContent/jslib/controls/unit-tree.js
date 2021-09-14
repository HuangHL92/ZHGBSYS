/**
* 单位树控件
*/

$.widget("bits.UnitTree", {
	// 缺省参数
	options: {
		url: '', // 这个参数不需要设置
		searchBoxId: '', // 搜索ID
		treeType: 1, 
		showPersonCount: 0,
		showFzaiUser: 0,
		seeUnitTree: 0,
		checkSetting: { }, // checkbox设置
		editSetting: {}, // 编辑设置
		onSelect: function() {}, // 选中单位事件
		onBeforeCheck: null, 
		onCheck: function() {}, // check单位事件，checkSetting必须的enable必须设置为true
		onDblClick: function() {}, // 双击事件
		onRightClick: function() {}, // 右键事件,
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
		
		$.fn.zTree.init($("#" + id), setting);
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
		var treeType = this.options["treeType"];
		var showPersonCount = this.options["showPersonCount"];
		var showFzaiUser = this.options["showFzaiUser"];
		var seeUnitTree = this.options["seeUnitTree"];
		
		// 获取URL
		var url = System.rootPath + '/common/unit-tree!getAccessiableUnitTree.action';
		
		url += '?seeUnitTree=' + seeUnitTree;
		url += '&showPersonCount=' + showPersonCount;
		url += '&showFzaiUser=' + showFzaiUser;
		
		var urlSearch = url;
		
		url += '&treeType=' + treeType;
		if (treeType == '1') {
			urlSearch += '&treeType=9';
		}
		else if (treeType == '2') {
			urlSearch += '&treeType=10';
		}
		if (treeType == '101') {
			urlSearch += '&treeType=900';
		}
		
		
		setting.url = url;
		setting.urlSearch = urlSearch;
		
		// 勾选框
		setting.check = this.options["checkSetting"];
		
		// 编辑设置
		setting.edit = this.options["editSetting"];
		
		// 事件
		setting.callback = {
			onClick: this.options["onSelect"],
			beforeCheck: this.options["onBeforeCheck"],
			onCheck: this.options["onCheck"],
			onDblClick: this.options["onDblClick"],
			onRightClick: this.options["onRightClick"],
			beforeDrag: this.options["beforeDrag"],
			beforeDrop: this.options["beforeDrop"]
		};
		
		// 不是查询，看是不是异步加载		
		if (!isSearching) {
			//if (lazyLoading) {
				setting.async = {
					enable: true,
					url: url,
					autoParam:["id"]
				};
			//}
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
			$.get(setting.urlSearch, { queryKey : value }, function(data) {
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