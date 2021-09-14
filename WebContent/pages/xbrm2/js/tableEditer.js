
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
        updated: false,
        getValue: false,
        setValue: false
    }, options);

    jQuery.tableEditer = {
        setValue: function (obj, value,rowIndex,colIndex) {
            if (options.setValue) {
            	////更新数据对象
                options.setValue(obj, value,rowIndex,colIndex);
                if ($(obj).parent().children("td").length<4){ 
                	options.setValue(obj, value,rowIndex,colIndex+1);
                }
            }
            else if ($(obj).children().length == 0) {
                return $(obj).text(value);
            }
            else {
                var ele = $('input[type="text"]:not(' + txtSelector + '),select,textarea:not(' + txtSelector + ')', obj).first();
                if (ele.length == 0) {
                    $(obj).text(value);
                }
                else {
                    $(ele).val(value);
                }
            }
        },
        getValue: function (obj) {alert("getValue")
            if (options.getValue) {
                return options.getValue(obj);
            }
            else if ($(obj).children().length == 0) {
                return $(obj).text();
            }
            else {
                var ele = $('input[type="text"]:not(' + txtSelector + '),select,textarea', obj).first();
                if (ele.length == 0) {
                    return $(obj).text();
                }
                else {
                    return $(ele).val();
                }
            }
        }
    };
    return $(options.selector, this).live('click', function (event) {
        if ($("#fontCheckBox").val()=="1"){  
        	return false;
        }
        
        var td = $(this);
        //行号 zoulei
        var rowIndex = td.parent().index();
        //列号zoulei
        var colIndex = td.index();
        var tabIndex = td.parent().index() + '' + td.index();
        //改成textarea
        if (td.children("textarea").length > 0) {
        	//文本域不能直接编辑
            return false;
        }else if (td.children("img").length > 0 || colIndex==3) {
        	//相片不能编辑
            return false;
        } else if (isGDCL == '1'){
        	//显示归档材料不能编辑
        	return false;
        }
         
        if (colIndex==1 && td.parent().children("td").length>4){
        	return false;
        }
        
        var text="";
        try{
	        //取出当前td的文本内容保存起来   
	        text = td.text();
	        if (colIndex==1){
		        if (td.parent().children("td").length>4){
		        	return false;
		        }else{
		        	if (td.parent().children("td").length<4){
		        		text=($(td).parent().children("td:eq(1)").html());
		        		if (text=='&nbsp;'){
		        			text = "";
		        		}
		        	}
		        }
	        }else{
		        if (options.getValue) {
		        	//根据数据对象模型获取数据
		        	/*if ($("#coordTable tr:eq(1) th").length>0){
		        		text = options.getValue(rowIndex-1,colIndex);
		        	}else{
		        		text = options.getValue(rowIndex,colIndex);
		        	} */
		        	text = $(td).text();
		        }  
	        }
        }catch(e){
        	//alert(e);
        }
        
        if ($("#fontCheckBox").val()=="3"){
        	changFont(td); 
        	return false;
        }
        
        $(td).css("position","relative");
        
        //清空td里面的内容   
        //td.empty();
        
        var width = td.width() - 6;
        var height = td.height() - 6+6; //6+14
        
        
        if(text instanceof Array){//拟任免
        	//建立文本框，也就是input的节点   
            //设置文本框值，即保存的文本内容   
            var $NiRenMianBianJi = $('.NiRenMianBianJi');
            $NiRenMianBianJi.css('left',$h.pos(this).left);
            $NiRenMianBianJi.css('top',$h.pos(this).top);
            $NiRenMianBianJi.css('width',width);
            
            if(height<100){
            	height = 100;
            }
            $NiRenMianBianJi.css('height',height);
            $('.Ren textarea').val(text[1]);
            $('.Mian textarea').val(text[2]);
            $('.QiTa textarea').val(text[3]);
            $NiRenMianBianJi.attr("rowIndex",rowIndex);
            $NiRenMianBianJi.attr("colIndex",colIndex);
            $NiRenMianBianJi.css('display','block');
        }else{ 
        	//建立文本框，也就是input的节点   
            var div = $('<div  style="position:absolute;top:0px;left:0px;border-style:none;">');
            var input = $('<textarea style="height:' + height + 'px;width:' + width + 'px;border-width:0px;">');
            div.append(input);
            //var input = $('<input type="text" style="height:' + height + ';width:' + width + ';">');
            //将文本内容加入td   
            td.append(div);
            //设置文本框值，即保存的文本内容   
            input.attr('value', text);
            input.css('border', options.border);
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
                jQuery.tableEditer.setValue(td, $(this).val(),rowIndex,colIndex);
                
            });
            input.keypress(function (event) {
                /*//获取当前用户按下的键值   
                //截取不同浏览器获取事件对象的差异   
                var keycode = event.which;
                var value = $(this).val();
                //判断是否是回车按下   
                if (keycode == 13) {
                    //jQuery.tableEditer.setValue(td, value);
                }
                //处理esc的情况 firefox esc的keycode等于0
                if (keycode == 27 || keycode == 0) {
                    //将td中的内容还原成text 
                    jQuery.tableEditer.setValue(td, text);
                }
               */
            });
            input.trigger("focus").focusEnd();
        }
        
    });
}

