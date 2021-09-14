var UmsPerson = function(zwlb, zwjb) {
	var _self = this;
	
	this.zwlb = zwlb;
	this.zwjb = zwjb;
	this.currentPage = 0;
	this.totalPage = 0;
	
	// 初始化
	UmsPerson.prototype.init = function() {
		_self._init();
	};
	
	// 到第一页
	UmsPerson.prototype.gotoPageFirst = function() {
		if (_self.totalPage > 1) {
			_self._initPerson(1);
		}
		else {
			System.showErrorMsg('已经是第一页！');
		}
	};
	
	// 到最后一页
	UmsPerson.prototype.gotoPageLast = function() {
		if (_self.currentPage < _self.totalPage) {
			_self._initPerson(_self.totalPage);
		}
		else {
			System.showErrorMsg('已经是最后一页！');
		}
	};
	
	// 到下一页
	UmsPerson.prototype.gotoPageNext = function() {
		if (_self.currentPage < _self.totalPage) {
			_self._initPerson(_self.currentPage + 1);
		}
		else {
			System.showErrorMsg('已经是最后一页！');
		}
	};
	
	// 到上一页
	UmsPerson.prototype.gotoPagePrev = function() {
		if (_self.currentPage > 1) {
			_self._initPerson(_self.currentPage - 1);
		}
		else {
			System.showErrorMsg('已经是第一页！');
		}
	};
	
	
	// 内部初始化
	this._init = function() {
		_self._initPerson(1);
	};
	
	this._renderPerson = function(result) {
		$("#personListTable").html("");
		var html = "<tr><th width='60'>姓名</th><th>现任职务</th></tr>";
		$(result.rows).each(function(i, v) {
			var name = v.xingm;
			if (v.sex == '女') name = name + '<br/>（女）';
			html += '<tr class="targetPersonRow">';
			html += '<td align="center" pname="' + v.xingm + '" pid="' + v.personcode + '">' + name + '</td>';
			html += '<td>' + v.zhiw + '</td>';
			html += '</tr>';
		});
		
		$("#personListTable").append(html);
		
		// 更新页脚
		var pageNo = _self.currentPage;
		var pageSize = 20;
		var totalCount = result.total;
		var totalPage = parseInt(totalCount / pageSize);
		if (totalCount * pageSize != totalCount) {
			totalPage++;
		}
		_self.totalPage = totalPage;
		
		if (totalCount == 0) {
			pageNo = 0;
			$("#currentPage").val("0");
			$("#pageMsg").html("共0记录");
		}
		else {
			var msg = _self.currentPage + "/" + _self.totalPage + "，共" + totalCount + "记录";
			$("#pageMsg").html(msg);
		}
		
		// 切换按钮
		_self._toggleButton("#btnPageFirst", pageNo != 1);
		_self._toggleButton("#btnPageLast", pageNo != totalPage);
		_self._toggleButton("#btnPagePrev", pageNo > 1);
		_self._toggleButton("#btnPageNext", pageNo < totalPage);
		
		// 支持拖动
		$(".targetPersonRow").draggable({
			revert: true,
			//handle: '.personList',
			proxy: function(source){
				var p = $('<div style="z-index:9999;text-align:center;background-color:#EFEFEF;width:52px;height:80px;padding:8px;font-size:12px;line-height:12px;display:block;"></div>');
				var tds = $(source).find('td');
				var personcode = $(tds[0]).attr('pid');
				var name = $(tds[0]).attr('pname');
				
				var html = '<img src="' + System.rootPath + '/lob/photo.action?personcode=' + 
						personcode + '&zoom=true&width=50&height=66" width="50" height="66" />';
				html += name;
				
				p.html(html).appendTo('body');
				return p;
			}
		});
	};
	
	this._toggleButton = function(btnId, enabled) {
		if (enabled) {
			$(btnId).removeClass('l-btn-disabled');
			$(btnId).removeClass('l-btn-plain-disabled');
		}
		else {
			$(btnId).addClass('l-btn-disabled');
			$(btnId).addClass('l-btn-plain-disabled');
		}
	};
	
	this._initPerson = function(page) {
		_self.currentPage = page;
		var condition = _self._getCondition();
		var url = System.rootPath + "/unitstructmodel/unit-struct-model-deduce!queryPerson.action";
		$.get(url, {
			group: 'A',
			tables: condition.tables,
			querySQL: condition.sql,
			rows: 20,
			page: page
		}, function(data) {
			_self._renderPerson(data);
		});
	};
	
	this._getCondition = function() {
		var conds = _self._getDefaultConditions();
		
		// 获取快速条件
		/*
		var quickConds = getQuickConditions();
		if (quickConds != null && quickConds.length != 0) {
			for (var i = 0; i < quickConds.length; i++) {
				conds.push(quickConds[i]);
			}
		}
		
		// 添加自定义条件
		var rows = $("#condList").datagrid('getRows');
		if (rows != null && rows.length != 0) {
			for (var i = 0; i < rows.length; i++) {
				conds.push(rows[i]);
			}
		}
		*/
		
		// 组合所有的条件！
		var condition = new Object();
		var tables = [];
		var sql = '';
		tables.push('A000');
		
		for (var i = 0; i < conds.length; i++) {
			var cond = conds[i];
			var t = cond.conditionTablekey.split(',');
			for (var n = 0; n < t.length; n++) {
				tables.push(t[n]);
			}
			
			if (sql == '') sql = cond.conditionQuerySQL;
			else sql = sql + ' AND ' + cond.conditionQuerySQL;
		}
		
		tables = $.unique(tables);
		condition.tables = '' + tables;
		condition.sql = sql;
		return condition;
	};	
	
	this._getDefaultConditions = function() {
		var conds = [];
				
		conds.push(_self._getStqkCond());
		conds.push(_self._getDefaultZjCond());
		
		return conds;
	};
	
	this._getDefaultZjCond = function() {
		var zj = '';
		if (_self.zwjb == '1') {
			zj = "'0111', '0112'";
		}
		else if (_self.zwjb == '2') {
			zj = "'0112', '0121'";
		}
		else if (_self.zwjb) {
			zj = "'0111', '0112', '0121'";
		}
		else if (_self.zwjb == '4') {
			zj = "'0121', '0122'";
		}
		else if (_self.zwjb == '5') {
			zj = "'0122', '0131'";
		}
		else if (_self.zwjb == '6') {
			zj = "'0121', '0122', '0131'";
		}
		else if (_self.zwjb == '7') {
			zj = "'0131', '0132'";
		}
		else if (_self.zwjb == '8') {
			zj = "'0132', '0141'";
		}
		else if (_self.zwjb == '9') {
			zj = "'0131', '0132', '0141'";
		}
		
		var cond = {
				conditionGroupkey: 'A',
				conditionTablekey: 'A004',
				conditionQueryLogic: 'AND',
				conditionQuerySQLCN: '职级',
				conditionQuerySQL: "A004_A0514='1' and A004_A0501 in (" + zj + ")"
			};
		return cond;
	};
	
	this._getStqkCond = function() {
		var cond = {
			conditionGroupkey: 'A',
			conditionTablekey: 'A000',
			conditionQueryLogic: 'AND',
			conditionQuerySQLCN: '身体健康',
			conditionQuerySQL: " A000_A0127 in ('1', '2') "
		};
		
		return cond;
	};
	
	// 执行初始化
	this.init();
};