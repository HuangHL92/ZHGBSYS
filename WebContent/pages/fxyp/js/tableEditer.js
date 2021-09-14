
$.fn.setCursorPosition = function(position){
    if(this.lengh == 0) return this;
    return $(this).setSelection(position, position);
}

$.fn.setSelection = function(selectionStart, selectionEnd) {
    if(this.lengh == 0) return this;
    input = this[0];

    if (input.createTextRange) {
        var range = input.createTextRange();
        range.collapse(true);
        range.moveEnd('character', selectionEnd);
        range.moveStart('character', selectionStart);
        range.select();
    } else if (input.setSelectionRange) {
        input.focus();
        input.setSelectionRange(selectionStart, selectionEnd);
    }

    return this;
}

$.fn.focusEnd = function(){
    this.setCursorPosition(this.val().length);
}


$.fn.tableEditer = function (options) {

    var txtSelector = '.txt_editer';

    options = $.extend({
        selector: 'td',
        align: 'left',
        border: 'solid 1px #000000',
        css: false,
        preUpdate: false,
        updated: false
    }, options);

    return $(options.selector, this).live('click', function (event) {
        var td = $(this);
        var isGangGei = $("td:nth-child(2)", td.parent()).attr('gw');
		
        //行号 zoulei
        var rowIndex = td.parent().index();
        //列号zoulei
        var colIndex = td.index();
        if(isGangGei!=='true'){
			colIndex = colIndex + 2;//因为有两列进行了行合并
			
		}
        if(colIndex>=3&&colIndex<=9){//序号列 不需编辑
			return;
		}
        var tabIndex = td.parent().index() + '' + td.index();
        //改成textarea
        if (td.children("textarea").length > 0) {
            return false;
        }
        
        var vObj = options.getValueObj(rowIndex,colIndex,td);
        var fxyp07 = vObj["fxyp07"];
		if(colIndex==2&&fxyp07=='-1'){
			return;
      	}
        	//根据数据对象模型获取数据 zoulei
        var text = options.getValue(rowIndex,colIndex,td);
        //清空td里面的内容   
        //td.empty();
        
        var width = td.width() - 6;
        var height = td.height() - 6+14;
        
    	//建立文本框，也就是input的节点   
        var div = $('<div style="position:absolute;top:0px;left:0px;">');
        var input = $('<textarea style="height:' + height + 'px;width:' + width + 'px;">');
        div.append(input);
        //var input = $('<input type="text" style="height:' + height + ';width:' + width + ';">');
        //将文本内容加入td   
        td.append(div);
        //设置文本框值，即保存的文本内容   
        input.attr('value', text);
        input.css('border', options.border);
        input.css('overflow', 'hidden');
        input.css('text-align', options.align);
        //作为jquery选择的标志
        input.addClass('txt_editer');
        if (options.css) {
            input.addClass(options.css);
        }
        input.click(function () { return false; });
        input.focusout(function (e) {
        	//zoulei 输入框点击位置如果是自己，就保留输入框。防止点到输入框空白位置也会触发该事件
        	if(e.offsetX<width&&e.offsetY<height&&e.offsetY>0&&e.offsetX>0){
        		return;
        	}
        	//更新数据对象
        	options.setValue(td, $(this).val(),rowIndex,colIndex);
            
        });
        input.keypress(function (event) {
          
        });
        input.trigger("focus").focusEnd();
    
        
    });
}

