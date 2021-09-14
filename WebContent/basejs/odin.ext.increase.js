/**
 * 修正ext的bug--grid的中文排序
 */
Ext.data.Store.prototype.applySort = function() { // 重载 applySort
	if (this.sortInfo && !this.remoteSort) {
		var s = this.sortInfo, f = s.field;
		var st = this.fields.get(f).sortType;
		var fn = function(r1, r2) {
			var v1 = st(r1.data[f]), v2 = st(r2.data[f]);
			// 添加:修复汉字排序异常的Bug
			if (typeof(v1) == "string") { // 若为字符串，
				// 则用 localeCompare 比较汉字字符串, Firefox 与IE 均支持
				return v1.localeCompare(v2);
			}
			// 添加结束
			return v1 > v2 ? 1 : (v1 < v2 ? -1 : 0);
		};
		this.data.sort(s.direction, fn);
		if (this.snapshot && this.snapshot != this.data) {
			this.snapshot.sort(s.direction, fn);
		}
	}
};
/**
 * grid列值不能复制bug修复
 */
if (!Ext.grid.GridView.prototype.templates) {
	Ext.grid.GridView.prototype.templates = {};
}
Ext.grid.GridView.prototype.templates.cell = new Ext.Template('<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} x-selectable {css}" style="{style}" tabIndex="0" {cellAttr}>', '<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>', '</td>');

/**
 * 增加查询组件QueryField
 */
Ext.form.QueryField = Ext.extend(Ext.form.TriggerField, {
			initEvents : function() {
				Ext.form.TriggerField.superclass.initEvents.call(this);
				this.keyNav = new Ext.KeyNav(this.el, {
							"enter" : function(e) {
								this.triggerBlur();
								return true;
							},
							"tab" : function(e) {
								this.triggerBlur();
								return true;
							},
							scope : this,
							doRelay : function(foo, bar, hname) {
								if ((hname == 'enter' || hname == 'tab')) {
									return Ext.KeyNav.prototype.doRelay.apply(this, arguments);
								}
								return true;
							},
							forceKeyDown : true
						});
			}
		});
Ext.reg('queryfield', Ext.form.QueryField);
/**
 * 继承自numberField，可以实现格式化金额数字
 * @class Ext.form.MoneyField
 * @extends Ext.form.NumberField
 */
Ext.form.MoneyField = Ext.extend(Ext.form.NumberField, {
			// 运行输入的字符集
			baseChars : "0123456789,",
			// private
			initEvents : function() {
				Ext.form.NumberField.superclass.initEvents.call(this);
				var allowed = this.baseChars + '';
				if (this.allowDecimals) {
					allowed += this.decimalSeparator;
				}
				if (this.allowNegative) {
					allowed += "-";
				}
				this.stripCharsRe = new RegExp('[^' + allowed + ']', 'gi');
				var keyPress = function(e) {
					var k = e.getKey();
					if (!Ext.isIE && (e.isSpecialKey() || k == e.BACKSPACE || k == e.DELETE)) {
						return;
					}
					var c = e.getCharCode();
					if (allowed.indexOf(String.fromCharCode(c)) === -1) {
						e.stopEvent();
					}
				};
				this.el.on("keypress", keyPress, this);
			},

			// private
			validateValue : function(value) {
				if (!Ext.form.NumberField.superclass.validateValue.call(this, value)) {
					return false;
				}
				if (value.length < 1) { // if it's blank and textfield didn't
					// flag it then it's valid
					return true;
				}
				var re = /,/g;
				value = String(value).replace(re, '');
				value = String(value).replace(this.decimalSeparator, ".");
				if (isNaN(value)) {
					this.markInvalid(String.format(this.nanText, value));
					return false;
				}
				var num = this.parseValue(value);
				if (num < this.minValue) {
					this.markInvalid(String.format(this.minText, this.minValue));
					return false;
				}
				if (num > this.maxValue) {
					this.markInvalid(String.format(this.maxText, this.maxValue));
					return false;
				}
				return true;
			},

			getValue : function() {
				return this.parseValue(Ext.form.NumberField.superclass.getValue.call(this));
			},

			setValue : function(v) {
				Ext.form.NumberField.superclass.setValue.call(this, v);
			},

			// private
			parseValue : function(value) {
				var re = /,/g;
				value = String(value).replace(re, '');
				value = parseFloat(String(value).replace(this.decimalSeparator, "."));
				return value;
			},

			// private
			fixPrecision : function(value) {
				var nan = isNaN(value);
				if (!this.allowDecimals || this.decimalPrecision == -1 || nan || !value) {
					return nan ? '' : value;
				}
				return parseFloat(parseFloat(value).toFixed(this.decimalPrecision));
			},

			beforeBlur : function() {
				var v = this.getRawValue();
				v = this.parseValue(v);
				v = this.formatMoney(v);
				if (v) {
					this.setValue(v);
				}
			},
			formatMoney : function(s) {
				s = s.toString();
				var flag = "";
				if (s.substring(0, 1) == "-") {
					flag = "-";
					s = s.substring(1);
				}
				if (/[^0-9\.]/.test(s))
					return "invalid value";
				var p = Math.pow(10, this.decimalPrecision);
				s = Math.round(s * p) / p;
				s = s.toString();
				var right = p.toString().substring(1);
				var index = s.indexOf(".");
				if (index != -1) {
					right = s.substring(s.indexOf(".") + 1);
					right = right + p.toString().substring(right.length + 1);
				}
				s = s.replace(/^(\d*)$/, "$1.");
				s = (s + "00").replace(/(\d*\.\d\d)\d*/, "$1");
				s = s.replace(".", ",");
				var re = /(\d)(\d{3},)/;
				while (re.test(s))
					s = s.replace(re, "$1,$2");
				s = s.replace(/,(\d\d)$/, ".$1");
				s = s.substring(0, s.indexOf(".") + 1) + right;
				s = s.replace(/^\./, "0.");
				s = flag + s;
				return s;
			}
		});
Ext.reg('numberfield', Ext.form.MoneyField);

/**
 * 继承自numberField，可以实现格式化百分比
 * 
 * @class Ext.form.PercentField
 * @extends Ext.form.NumberField
 */
Ext.form.PercentField = Ext.extend(Ext.form.NumberField, {
			// 运行输入的字符集
			baseChars : "0123456789%",
			// private
			initEvents : function() {
				Ext.form.NumberField.superclass.initEvents.call(this);
				var allowed = this.baseChars + '';
				if (this.allowDecimals) {
					allowed += this.decimalSeparator;
				}
				if (this.allowNegative) {
					allowed += "-";
				}
				this.stripCharsRe = new RegExp('[^' + allowed + ']', 'gi');
				var keyPress = function(e) {
					var k = e.getKey();
					if (!Ext.isIE && (e.isSpecialKey() || k == e.BACKSPACE || k == e.DELETE)) {
						return;
					}
					var c = e.getCharCode();
					if (allowed.indexOf(String.fromCharCode(c)) === -1) {
						e.stopEvent();
					}
				};
				this.el.on("keypress", keyPress, this);
			},

			// private
			validateValue : function(value) {
				if (!Ext.form.NumberField.superclass.validateValue.call(this, value)) {
					return false;
				}
				if (value.length < 1) { // if it's blank and textfield didn't
					// flag it then it's valid
					return true;
				}
				var re = /%/g;
				value = String(value).replace(re, '');
				value = String(value).replace(this.decimalSeparator, ".");
				if (isNaN(value)) {
					this.markInvalid(String.format(this.nanText, value));
					return false;
				}
				var num = this.parseValue(value);
				if (num < this.minValue) {
					this.markInvalid(String.format(this.minText, this.minValue));
					return false;
				}
				if (num > this.maxValue) {
					this.markInvalid(String.format(this.maxText, this.maxValue));
					return false;
				}
				return true;
			},

			getValue : function() {
				return this.parseValue(Ext.form.NumberField.superclass.getValue.call(this));
			},

			setValue : function(v) {
				Ext.form.NumberField.superclass.setValue.call(this, v);
			},

			// private
			parseValue : function(value) {
				if (String(value).indexOf("%") != -1) {
					var re = /%/g;
					value = String(value).replace(re, '');
					value = accDiv(value, 100);
				}
				value = parseFloat(String(value).replace(this.decimalSeparator, "."));
				return value;
			},

			// private
			fixPrecision : function(value) {
				var nan = isNaN(value);
				if (!this.allowDecimals || this.decimalPrecision == -1 || nan || !value) {
					return nan ? '' : value;
				}
				return parseFloat(parseFloat(value).toFixed(this.decimalPrecision));
			},

			beforeBlur : function() {
				var v = this.getRawValue();
				v = this.parseValue(v);
				v = this.formatPercent(v);
				if (v) {
					this.setValue(v);
				}
			},
			formatPercent : function(s) {
				return renderPercent(s);
			}
		});
Ext.reg('numberfield', Ext.form.PercentField);
/**
 * 继承自numberField，可以实现格式化百分比或数字
 * @class Ext.form.PercentOrNotField
 * @extends Ext.form.NumberField
 */
Ext.form.PercentOrNotField = Ext.extend(Ext.form.NumberField, {
			// 运行输入的字符集
			baseChars : "0123456789%",
			// private
			initEvents : function() {
				Ext.form.NumberField.superclass.initEvents.call(this);
				var allowed = this.baseChars + '';
				if (this.allowDecimals) {
					allowed += this.decimalSeparator;
				}
				if (this.allowNegative) {
					allowed += "-";
				}
				this.stripCharsRe = new RegExp('[^' + allowed + ']', 'gi');
				var keyPress = function(e) {
					var k = e.getKey();
					if (!Ext.isIE && (e.isSpecialKey() || k == e.BACKSPACE || k == e.DELETE)) {
						return;
					}
					var c = e.getCharCode();
					if (allowed.indexOf(String.fromCharCode(c)) === -1) {
						e.stopEvent();
					}
				};
				this.el.on("keypress", keyPress, this);
			},

			// private
			validateValue : function(value) {
				if (!Ext.form.NumberField.superclass.validateValue.call(this, value)) {
					return false;
				}
				if (value.length < 1) { // if it's blank and textfield didn't
					// flag it then it's valid
					return true;
				}
				var re = /%/g;
				value = String(value).replace(re, '');
				value = String(value).replace(this.decimalSeparator, ".");
				if (isNaN(value)) {
					this.markInvalid(String.format(this.nanText, value));
					return false;
				}
				var num = this.parseValue(value);
				if (num < this.minValue) {
					this.markInvalid(String.format(this.minText, this.minValue));
					return false;
				}
				if (num > this.maxValue) {
					this.markInvalid(String.format(this.maxText, this.maxValue));
					return false;
				}
				return true;
			},

			getValue : function() {
				return this.parseValue(Ext.form.NumberField.superclass.getValue.call(this));
			},

			setValue : function(v) {
				Ext.form.NumberField.superclass.setValue.call(this, v);
			},

			// private
			parseValue : function(value) {
				if (String(value).indexOf("%") != -1) {
					var re = /%/g;
					value = String(value).replace(re, '');
					value = accDiv(value, 100);
				}
				value = parseFloat(String(value).replace(this.decimalSeparator, "."));
				return value;
			},

			// private
			fixPrecision : function(value) {
				var nan = isNaN(value);
				if (!this.allowDecimals || this.decimalPrecision == -1 || nan || !value) {
					return nan ? '' : value;
				}
				return parseFloat(parseFloat(value).toFixed(this.decimalPrecision));
			},

			beforeBlur : function() {
				var v = this.getRawValue();
				v = this.parseValue(v);
				v = this.formatPercentOrNot(v);
				if (v) {
					this.setValue(v);
				}
			},
			formatPercentOrNot : function(s) {
				return radow.renderPercentOrNot(s);
			}
		});
Ext.reg('numberfield', Ext.form.PercentOrNotField);
/**
 * 除法函数，用来得到精确的除法结果 说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
 * 调用：accDiv(arg1,arg2) 返回值：arg1除以arg2的精确结果
 */
function accDiv(arg1, arg2) {
	var t1 = 0, t2 = 0, r1, r2;
	try {
		t1 = arg1.toString().split(".")[1].length
	} catch (e) {
	}
	try {
		t2 = arg2.toString().split(".")[1].length
	} catch (e) {
	}
	with (Math) {
		r1 = Number(arg1.toString().replace(".", ""))
		r2 = Number(arg2.toString().replace(".", ""))
		return (r1 / r2) * pow(10, t2 - t1);
	}
}

function b(d) {
	var c = Array.prototype.slice.call(arguments, 1);
	return d.replace(/\{(\d+)\}/g, function (e, g) {
		return c[g]
	})
}
var a = Date.formatCodeToRegex;
/**
 * 修正日期为31号的时候会出现的BUG
 * 
 */
Ext.apply(Date, {
	createParser : function () {
		var c = ["var dt, y, m, d, h, i, s, ms, o, z, zz, u, v,", "def = Date.defaults,", "results = String(input).match(Date.parseRegexes[{0}]);", "if(results){", "{1}", "if(u != null){", "v = new Date(u * 1000);", "}else{", "dt = (new Date()).clearTime();", "y = y >= 0? y : Ext.num(def.y, dt.getFullYear());", "m = m >= 0? m : Ext.num(def.m - 1, dt.getMonth());", "d = d >= 0? d : Ext.num(def.d, dt.getDate());", "h  = h || Ext.num(def.h, dt.getHours());", "i  = i || Ext.num(def.i, dt.getMinutes());", "s  = s || Ext.num(def.s, dt.getSeconds());", "ms = ms || Ext.num(def.ms, dt.getMilliseconds());", "if(z >= 0 && y >= 0){", "v = new Date(y, 0, 1, h, i, s, ms);", "v = !strict? v : (strict === true && (z <= 364 || (v.isLeapYear() && z <= 365))? v.add(Date.DAY, z) : null);", "}else if(strict === true && !Date.isValid(y, m + 1, d, h, i, s, ms)){", "v = null;", "}else{", "v = new Date(y, m, d, h, i, s, ms);", "}", "}", "}", "if(v){", "if(zz != null){", "v = v.add(Date.SECOND, -v.getTimezoneOffset() * 60 - zz);", "}else if(o){", "v = v.add(Date.MINUTE, -v.getTimezoneOffset() + (sn == '+'? -1 : 1) * (hr * 60 + mn));", "}", "}", "return v;"].join("\n");
		return function (m) {
			var e = Date.parseRegexes.length,
			n = 1,
			g = [],
			l = [],
			k = false,
			d = "";
			for (var j = 0; j < m.length; ++j) {
				d = m.charAt(j);
				if (!k && d == "\\") {
					k = true
				} else {
					if (k) {
						k = false;
						l.push(String.escape(d));
					} else {
						var h = a(d, n);
						n += h.g;
						l.push(h.s);
						if (h.g && h.c) {
							g.push(h.c);
							if(m.indexOf('d')<0 &&j==m.length-1){
								g.push("d = 1;");
							}
						}
					}
				}
			}
			Date.parseRegexes[e] = new RegExp("^" + l.join("") + "$", "i");
			Date.parseFunctions[m] = new Function("input", "strict", b(c, e, g.join("")));
		};
	}
	()
});


/***
 * 改变GRID编辑时只计算编辑框的width, 设置为all
 */
Ext.override(Ext.grid.GridEditor, {    
    autoSize: "all",
    doAutoSize : function(){
        if(this.autoSize){
            var sz = this.boundEl.getSize();
            var v = this.field.getValue();
            
            switch(this.autoSize){
                case "width":
                    this.setSize(sz.width,  "");
                break;
                case "height":
                    this.setSize("",  sz.height);
                break;
                default:
                    if(this.field.height === undefined || this.field.height == ""){                        
                        this.setSize(sz.width,  "");
                    }else{
                       sz.height = this.field.height > sz.height? this.field.height:sz.height;
                       this.setSize(sz.width,  sz.height); 
                    }
                    
            }
        }
    }
});

/**
 * 改变 Readonly的样式
 */ 
 
Ext.override(Ext.form.TextField, {
     afterRender: function(){
        Ext.form.TextField.superclass.afterRender.call(this);
              
        if(this.readOnly){
            if(this.getActionEl() !== undefined){                
                this.getActionEl().addClass(this.disabledClass); 
            } 
        }else if(this.el.dom.readOnly){
            if(this.getActionEl() !== undefined){
                this.getActionEl().addClass(this.disabledClass);
            }
        }
        
    }
});

Ext.override(Ext.form.TriggerField, {
     afterRender: function(){
                 
        Ext.form.TriggerField.superclass.afterRender.call(this);
        var y;
        if(Ext.isIE && !this.hideTrigger && this.el.getY() != (y = this.trigger.getY())){
            this.el.position();
            this.el.setY(y);
        }
        
        if(this.readOnly&&this.wrap){
            this.wrap.addClass(this.disabledClass);
            this.el.removeClass(this.disabledClass);
            
            this.mun(this.el, 'focus', this.onFocus, this);
            //this.mun(this.el, 'click', this.onTriggerClick, this);
            this.mun(this.el, 'keyup', this.filterValidation, this);
            this.mun(this.el, 'keyup', this.onKeyUp, this);
            this.mun(this.el, 'keydown', this.onKeyDown, this);
            this.mun(this.el, 'keypress', this.onKeyPress, this);
            
            if(this.trigger){
                this.mun(this.trigger, 'click', this.onTriggerClick, this);
                this.trigger.removeClass('x-form-trigger-over');
                this.trigger.removeClass('x-form-trigger-click');        
            }
            
            //this.onDestroy();
        }
        
    }
});

Ext.override(Ext.form.ComboBox, {
    onTriggerClick : function(){
        if(this.disabled || this.readOnly === true){
            return;
        }
        if(this.isExpanded()){
            this.collapse();
            this.el.focus();
        }else {
            this.onFocus({});
            if(this.triggerAction == 'all') {
                this.doQuery(this.allQuery, true);
            } else {
                this.doQuery(this.getRawValue());
            }
            this.el.focus();
        }
    }
});



Ext.override(Ext.grid.RowSelectionModel,{
    initEvents : function(){

        if(!this.grid.enableDragDrop && !this.grid.enableDrag){
            this.grid.on("rowmousedown", this.handleMouseDown, this);
        }else{ // allow click to work like normal
            this.grid.on("rowclick", function(grid, rowIndex, e) {
                if(e.button === 0 && !e.shiftKey && !e.ctrlKey) {
                    this.selectRow(rowIndex, false);
                    grid.view.focusRow(rowIndex);
                }
            }, this);
        }

        this.rowNav = new Ext.KeyNav(this.grid.getGridEl(), {
            "up" : function(e){
                if(!e.shiftKey || this.singleSelect){
                    this.selectPrevious(false);
                }else if(this.last !== false && this.lastActive !== false){
                    var last = this.last;
                    this.selectRange(this.last,  this.lastActive-1);
                    this.grid.getView().focusRow(this.lastActive);
                    if(last !== false){
                        this.last = last;
                    }
                }else{
                    this.selectFirstRow();
                }
            },
            "down" : function(e){
                if(!e.shiftKey || this.singleSelect){
                    this.selectNext(false);
                }else if(this.last !== false && this.lastActive !== false){
                    var last = this.last;
                    this.selectRange(this.last,  this.lastActive+1);
                    this.grid.getView().focusRow(this.lastActive);
                    if(last !== false){
                        this.last = last;
                    }
                }else{
                    this.selectFirstRow();
                }
            },
            scope: this
        });

        var view = this.grid.view;
        view.on("refresh", this.onRefresh, this);
        view.on("rowupdated", this.onRowUpdated, this);
        view.on("rowremoved", this.onRemove, this);
        
        if(this.grid.isEditor){
            this.grid.on("beforeedit", this.beforeEdit,  this);
        }
    },
    
    //private
    beforeEdit : function(e){
        var rowIndex = e.row,colIndex=e.column;
        var v = this.grid.getView();
        this.selectRow(rowIndex, false);
        this.grid.view.focusCell(rowIndex,colIndex);       
    }    
    
});


/**
 * 处理grid中向下方向键无法让滚动条滚动的问题
 */ 
Ext.override(Ext.grid.GridView, {
	ensureVisible : function (t, g, e) {
		var r = this.resolveCell(t, g, e);
		if (!r || !r.row) {
			return
		}
		var k = r.row,
		h = r.cell,
		n = this.scroller.dom,
		s = 0,
		d = k,
		o = this.el.dom;
		while (d && d != o) {
			s += d.offsetTop;
			d = d.offsetParent
		}
		s -= this.mainHd.dom.offsetHeight;
		var q = s + k.offsetHeight,
		a = n.clientHeight,
		
		o = parseInt(n.scrollTop, 10);
		m = o + a;
		if (s < o) {
			n.scrollTop = s
		} else {
			if (q > m) {
				n.scrollTop = q - a
			}
		}
		if (e !== false) {
			var l = parseInt(h.offsetLeft, 10);
			var j = l + h.offsetWidth;
			var i = parseInt(n.scrollLeft, 10);
			var b = i + n.clientWidth;
			if (l < i) {
				n.scrollLeft = l
			} else {
				if (j > b) {
					n.scrollLeft = j - n.clientWidth
				}
			}
		}
		return this.getResolvedXY(r)
	}
});




Ext.ns('MyLib');
/** 
 * 右下角的小贴士窗口
 * @author tipx.javaeye.com
 * @params conf 参考Ext.Window
 *         conf中添加autoHide配置项, 默认3秒自动隐藏, 设置自动隐藏的时间(单位:秒), 不需要自动隐藏时设置为false
 * @注: 使用独立的window管理组(manager:new Ext.WindowGroup()), 达到总是显示在最前的效果
 */
; (function($)
{
    //新建window组，避免被其它window影响显示在最前的效果
    var tipsGroupMgr = new Ext.WindowGroup();
    tipsGroupMgr.zseed = 99999; //将小贴士窗口前置

    $.TipsWindow = Ext.extend(Ext.Window,
    {
        width: 250,
        height: 200,
        layout: 'fit',
        modal: false,
        plain: true,
        shadow: false,
        //去除阴影
        draggable: false,
        //默认不可拖拽
        resizable: false,
        closable: true,
        closeAction: 'hide',
        //默认关闭为隐藏
        autoHide: 3,
        //n秒后自动隐藏，为false时,不自动隐藏
        manager: tipsGroupMgr,
        //设置window所属的组
        constructor: function(conf)
        {
            $.TipsWindow.superclass.constructor.call(this, conf);
            this.initPosition(true);
        },
        initEvents: function()
        {
            $.TipsWindow.superclass.initEvents.call(this);
            //自动隐藏
            if (false !== this.autoHide)
            {
                var task = new Ext.util.DelayedTask(this.hide, this),
                second = (parseInt(this.autoHide) || 3) * 1000;
                this.on('beforeshow',
                function(self)
                {
                    task.delay(second);
                });
            }
            this.on('beforeshow', this.showTips);
            this.on('beforehide', this.hideTips);

            Ext.EventManager.onWindowResize(this.initPosition, this); //window大小改变时，重新设置坐标
            Ext.EventManager.on(window, 'scroll', this.initPosition, this); //window移动滚动条时，重新设置坐标
        },
        //参数: flag - true时强制更新位置
        initPosition: function(flag)
        {
            if (true !== flag && this.hidden)
            { //不可见时，不调整坐标
                return false;
            }
            var doc = document,
            bd = (doc.body || doc.documentElement);
            //ext取可视范围宽高(与上面方法取的值相同), 加上滚动坐标
            var left = bd.scrollLeft + Ext.lib.Dom.getViewWidth() - 4 - this.width;
            var top = bd.scrollTop + Ext.lib.Dom.getViewHeight() - 4 - this.height;
            this.setPosition(left, top);
        },
        showTips: function()
        {
            var self = this;
            if (!self.hidden)
            {
                return false;
            }

            self.initPosition(true); //初始化坐标
            self.el.slideIn('b',
            {
                callback: function()
                {
                    //显示完成后,手动触发show事件,并将hidden属性设置false,否则将不能触发hide事件
                    self.fireEvent('show', self);
                    self.hidden = false;
                }
            });
            return false; //不执行默认的show
        },
        hideTips: function()
        {
            var self = this;
            if (self.hidden)
            {
                return false;
            }

            self.el.slideOut('b',
            {
                callback: function()
                {
                    //渐隐动作执行完成时,手动触发hide事件,并将hidden属性设置true
                    self.fireEvent('hide', self);
                    self.hidden = true;
                }
            });
            return false; //不执行默认的hide
        }
    });
})(MyLib);