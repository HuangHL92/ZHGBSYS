(function (factory) {
    if (typeof define === "function" && (define.amd || define.cmd) && !jQuery) {
        define([ "jquery" ],factory);
    } else if (typeof module === 'object' && module.exports) {
        module.exports = function( root, jQuery ) {
            if ( jQuery === undefined ) {
                if ( typeof window !== 'undefined' ) {
                    jQuery = require('jquery');
                } else {
                    jQuery = require('jquery')(root);
                }
            }
            factory(jQuery);
            return jQuery;
        };
    } else {
        factory(jQuery);
    }
}(function ($) {

	var defaults = {
		totalData: 0,		
		showData: 0,			
		pageCount: 9,
		current: 1,
		prevCls: 'prev',
		nextCls: 'next',
		prevContent: '<',
		nextContent: '>',
		activeCls: 'active',
		coping: false,
		isHide: false,
		homePage: '',
		endPage: '',
		keepShowPN: false,
		count: 3,
		jump: false,
		jumpIptCls: 'jump-ipt',
		jumpBtnCls: 'jump-btn',	
		jumpBtn: '跳转',		
		callback: function(){}
	};

	var Pagination = function(element,options){
		var opts = options,
			current,
			$document = $(document),
			$obj = $(element);

		this.setPageCount = function(page){
			return opts.pageCount = page;
		};

		this.getPageCount = function(){
			return opts.totalData || opts.showData ? Math.ceil(parseInt(opts.totalData) / opts.showData) : opts.pageCount;
		};

		this.getCurrent = function(){
			return current;
		};

		/**
		 * @param int index
		 */
		this.filling = function(index){
			var html = '';
			current = index || opts.current;
			var pageCount = this.getPageCount();
			var totalDiv = 0;
			if(opts.keepShowPN || current > 1){
				totalDiv += 1;
				html += '<a href="javascript:;" class="'+opts.prevCls+'">'+opts.prevContent+'</a>';
			}
			if(current >= opts.count + 2 && current != 1 && pageCount != opts.count){
				var home = opts.coping && opts.homePage ? opts.homePage : '1';
				if(opts.coping) {
					totalDiv += 2;
					html += '<a href="javascript:;" data-page="1">'+home+'</a><span>...</span>';
				}
			}
			var end = current + opts.count;
			var start = '';
			start = current === pageCount ? current - opts.count - 2 : current - opts.count;
			((start > 1 && current < opts.count) || current == 1) && end++;
			(current > pageCount - opts.count && current >= pageCount) && start++;
			if(start < 1){
				start = 1;
			}
			for (;start <= end; start++) {
				if(start <= pageCount && start >= 1){
					totalDiv += 1;
					if(start != current){
						html += '<a href="javascript:;" data-page="'+start+'">'+ start +'</a>';
					}else{
						html += '<span class="'+opts.activeCls+'">'+start+'</span>';
					}
				}
			}
			if(current + opts.count < pageCount && current >= 1 && pageCount > opts.count){
				var end = opts.coping && opts.endPage ? opts.endPage : pageCount;
				if(opts.coping){
					totalDiv += 2;
					html += '<span>...</span><a href="javascript:;" data-page="'+pageCount+'">'+end+'</a>';
				}
			}
			if(opts.keepShowPN || current < pageCount){
				totalDiv += 1;
				html += '<a href="javascript:;" class="'+opts.nextCls+'">'+opts.nextContent+'</a>';
			}
			if(opts.jump){
				totalDiv += 1;
				html += '<input type="text" class="'+opts.jumpIptCls+'"><a href="javascript:;" class="'+opts.jumpBtnCls+'">'+opts.jumpBtn+'</a>';
			}
			$obj.empty().html(html);
			if(totalDiv == 1) {
				$obj.css('padding-left','50%');
			} else {
				var totalWidth = document.body.clientWidth;
				if(totalWidth < 1000) {
					if(totalDiv <= 10){
						$obj.css('padding-left','30%');
					} else {
						$obj.css('padding-left','15%');
					}
				} else {
					if(totalDiv <= 5){
						$obj.css('padding-left','45%');
					} else {
						$obj.css('padding-left','35%');
					}
				}
			}
		};
		this.eventBind = function(){
			var that = this;
			var pageCount = that.getPageCount();
			var index = 1;
			$obj.off().on('click','a',function(){
				if($(this).hasClass(opts.nextCls)){
					if($obj.find('.'+opts.activeCls).text() >= pageCount){
						$(this).addClass('disabled');
						return false;
					}else{
						index = parseInt($obj.find('.'+opts.activeCls).text()) + 1;
					}
				}else if($(this).hasClass(opts.prevCls)){
					if($obj.find('.'+opts.activeCls).text() <= 1){
						$(this).addClass('disabled');
						return false;
					}else{
						index = parseInt($obj.find('.'+opts.activeCls).text()) - 1;
					}
				}else if($(this).hasClass(opts.jumpBtnCls)){
					if($obj.find('.'+opts.jumpIptCls).val() !== ''){
						index = parseInt($obj.find('.'+opts.jumpIptCls).val());
					}else{
						return;
					}
				}else{
					index = parseInt($(this).data('page'));
				}
				that.filling(index);
				typeof opts.callback === 'function' && opts.callback(that);
			});
			$obj.on('input propertychange','.'+opts.jumpIptCls,function(){
				var $this = $(this);
				var val = $this.val();
				var reg = /[^\d]/g;
	            if (reg.test(val)) {
	                $this.val(val.replace(reg, ''));
	            }
	            (parseInt(val) > pageCount) && $this.val(pageCount);
	            if(parseInt(val) === 0){
	            	$this.val(1);
	            }
			});
			$document.keydown(function(e){
		        if(e.keyCode == 13 && $obj.find('.'+opts.jumpIptCls).val()){
		        	var index = parseInt($obj.find('.'+opts.jumpIptCls).val());
		            that.filling(index);
					typeof opts.callback === 'function' && opts.callback(that);
		        }
		    });
		};
		this.init = function(){
			this.filling(opts.current);
			this.eventBind();
		};
		this.init();
	};

	$.fn.pagination = function(parameter,callback){
		if(typeof parameter == 'function'){
			callback = parameter;
			parameter = {};
		}else{
			parameter = parameter || {};
			callback = callback || function(){};
		}
		var options = $.extend({},defaults,parameter);
		return this.each(function(){
			var pagination = new Pagination(this, options);
			callback(pagination);
		});
	};
}));