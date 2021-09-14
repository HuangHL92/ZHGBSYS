/*
* 标签控件
* 2016-11-22 v1.0 by YZQ: 最初版本，只提供addTag, removeAllTags, getValues方法
*/

$.widget("bits.tags", {
	// 缺省参数
	options: {
		tagName: 'tags' // 注意：所有的标签名字，都是一样，要获取每个标签控件的所有标签，请使用getValues()方法获取。
	},
	
	_create: function() {
	},
	
	// 初始化
	_init: function() {
		
	},
	
	//  函数调用委托，内部function调用，必须通过这个委托进行传递
	_functionDelegate: function(self, fn) {
		return function() {
			if ($.isFunction(fn)) {
				fn.apply(self, arguments);
			}
		};
	},
	
	// 公开函数，获取所有标签的值，结果为value数组
	getValues: function() {
		var tagName = this.options["tagName"];
		var values = [];
		var inputTags = this.element.find("input[name^='" + tagName + "']");
		$(inputTags).each(function(index, inputTag) {
			values.push(inputTag.value);
		});
		
		return values;
	},
	
	// 公开函数，删除所有标签
	removeAllTags: function() {
		this.element.html('');
	},
	
	// 公开函数，添加一个标签
	addTag: function(tagText, tagValue) {
		var tagName = this.options["tagName"];
		
		// 不添加相同值的标签
		var tags = this.element.find("input[name^='" + tagName + "']");
		var findTag = false;
		$(tags).each(function(index, tag) {
			if (tag.value == tagValue) {
				findTag = true;
				return;
			}
		});
		
		if (findTag) {
			return;
		}
		
		var html = '<div class="tag"><span>' + tagText + '</span><input type="hidden" name="' +
                   tagName + '[]" value="' + 
				   tagValue + '">' + 
				   '<a title="点击移除" href="javascript:void(0);" class="tagButton">×</a>' + 
				   '</div>';
		this.element.append(html);
		
		// 绑定按钮事件
		var buttons = this.element.find(".tagButton");
		buttons.unbind('click').bind('click', function() {
			$(this).parent().remove();
		});
	}
});