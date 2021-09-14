/*

*/


(function ($) {

    $.fn.coordTable = function (options, prototype) {
        //当前对象
        var $this = this;
        //选中时的css名称
        var className = 'ui-selected';
        //选中时 jquery 选择器
        var selector = '.ui-selected';
        //浮动div jquery选择器
        var selector_float = 'div.drag';
        var selector_div = 'div.ui-selected';
        var id = $(this).attr('id');
        //可选参数
        options = $.extend({
            selecte_col_len: 2, //每行可以选中的列数
            selector_row: 'tr:gt(0) td:nth-child(1)', //一个jquery选择器，表示可以点击选择行的区域
            selector_drag: 'tr:gt(0) td:nth-child(n+3)', //一个jquery选择器，表示可以用鼠标拖拽的区域 td:nth-child(n+3)
            drag_pre_color: '#0ECA40',
            hasMenu: true,
            drag_color: "#000000", //鼠标拖拽后的背景颜色
            default_color: '#FFFFFF', //默认背景颜色
            create_row: false//创建表格行的函数
        }, options);

        jQuery.coordTable = {
            //创建空行
            createRow: function (type,rowIndex) {
                if (options.create_row) {
                    return options.create_row(type,rowIndex);
                }
                else {
                    var len = $('tr:eq(0) > th', $this).length;
                    if (len == 0) {
                        len = $('tr:eq(0) > td', $this).length;
                    }
                    var html = '<tr>';
                    for (var i = 0; i < len; i++) {
                        html += '<td></td>';
                    }
                    html += '</tr>';
                    return $(html);
                }
            },
            
            
           
            //删除
            del: function (o,delType) {
                var rows = [];//$(selector);
                if (rows.length == 0) {
                    rows = $(o);
                    var rowIndex = $(rows).index();
        			if(rowIndex==0){//表头
        				return;
        			}
                }
                if(rows.length==1){
                	var rowIndex = $(rows).index();
        			if(rowIndex==0){//表头
        				return;
        			}
                }
                if(delType=="ZD"){
                	options.setAppendInfo(null,null);
                	options.rowsDelete(rows,delType);
                }
                var delInfo = options.getDelInfo(rows,delType);
                if(delInfo==null){
                	return;
                }
                var tips = '确认删除当前'+delInfo+'！';
                if (rows.length > 1) {
                    tips = '确认删除从当前选择的' + rows.length + '行数据';
                }
                
                if (confirm(tips)) {
                	options.setAppendInfo(null,null);
                	options.rowsDelete(rows,delType);
                    
                }
            },
          //剪贴 删除剪切的tr，删除数据对象相关的数据，并保存，粘贴时插入保存的数据。 zoulei
            cut: function (o) {
            	var rows = [];//$(selector);
                if (rows.length == 0) {
                    rows = $(o);
                    var rowIndex = $(rows).index();
        			if(rowIndex==0){//表头
        				return;
        			}
                }
                if(rows.length==1){
                	var rowIndex = $(rows).index();
        			if(rowIndex==0){//表头
        				return;
        			}
                }
                if (rows.length > 0) {
                    var tips = '确认剪切当前记录行的数据！';
                    if (rows.length > 1) {
                        tips = '确认剪切从当前选择的' + rows.length + '行数据';
                    }
                    //if (confirm(tips)) {
                    	//更新数据对象；保存已剪切的数据对象 zoulei
                    	if (options.updateData) {
                    		options.updateData(rows);
                        }
                    //}
                }
            },
          //粘贴
            paste: function (o) {
                //选中行
                
                if (options.rowsPasted) {
                    options.rowsPasted(o);
                }
                
            },
            //追加
            append: function () {
                var tr = this.createRow();
                $($this).append(tr);
            },
            
            //取消选中
            unSelect: function (ele) {
                var selected = ele ? ele : $(selector + ',.ui-selectee,.ui-selecting');
                $(selected)
                .removeClass("drag_pre_color drag_color selectTag")
                .removeClass(className + ' ui-selectee ui-selecting');
            },
            floatOn: function (obj) {
                var width = $(obj).width() + 'px';
                /**zoulei 表格增加上下留白，覆盖的div增加2倍的padding的像素*******/
                var height = ($(obj).height()+10) + 'px';
                var left ="0px"; //$(obj).offset().left + 'px';
                var top ="0px"; //$(obj).offset().top + 'px';
               // alert($(obj).children(selector_float).length)
                $(obj).children(selector_float).remove()
                $(obj).append('<div class="drag" tabindex="0" style="z-index:50;width:' + width + ';height:' + height + ';\
                                        ;position:absolute;left:' + left + ';top:' + top + ';float:left;\
                                        opacity:0.4;filter:alpha(opacity=9);"></div>');
            }
        };

      //  return $(this).each(function () {

            //行取消选中
            /*$(options.selector_drag).live('click', function () {
                jQuery.coordTable.unSelect();
                    //可以编辑
                    $(this).find('input[type="text"]').focus();
            });*/

            //点击选中行
            $(options.selector_row, this).live('click', function (e) {
                //jQuery.coordTable.unSelect();
            	if($(this).parent().hasClass('selectTag')){
            		jQuery.coordTable.unSelect($(this).parent().children());
            		jQuery.coordTable.unSelect($(this).parent());
            		return;
            	}
            	if($(this).parent().children('td:nth-child(3)').attr('rowspan')===1){
            		$(this).parent().children('td:nth-child(n+1)')
                    .addClass(className+" drag_color");
            	}else{
            		$(this).parent().children('td:nth-child(n+4)')
                    .addClass(className+" drag_color");
            	}
            	$(this).parent().addClass('selectTag');
                return false;
            });

            //鼠标拖拽选中
            /*$(this).selectable({//selector_drag tr:gt(0) td:nth-child(n+3)
                filter: options.selector_drag,
                selecting: function (e, ui) {
                    $(ui.selecting).removeClass("drag_color").addClass("drag_pre_color");
                },
                selected: function (e, ui) {
                    $(ui.selected).removeClass("drag_pre_color").addClass('drag_color');
                },
                unselected: function (e, ui) {
                   // $(ui.unselected).css('background-color', options.default_color);
                	$(ui.unselected).addClass('ui-selected');//zoulei 原先选择的保留
                },
                start: function (e, ui) {
                    //$(selector_float, $this).show();
                },
                stop: function( e, ui ) {//把未选择的颜色还原 zoulei
                	$(options.selector_drag + ':not(.ui-selected)').removeClass("drag_pre_color drag_color");
                },
                distance: '2'//拖拽2象素后才选中
            });*/

            //右键菜单
            var contextMenu = $("tr", this).contextMenu('myMenu1', {
                bindings: {
                   
                   
                    'deleteP': function (o) {//删除人选
                        jQuery.coordTable.del(o,'P');
                        options.deleted(o);
                    },
                    'deleteGW': function (o) {//删除岗位
                    	jQuery.coordTable.del(o,'GW');
                    	options.deleted(o);
                    },
                    'ryzd': function (o) {//人员置顶
                    	jQuery.coordTable.del(o,'ZD');
                    	options.deleted(o);
                    },
                    'cut': function (o) {//剪贴
                    	jQuery.coordTable.cut(o);
                    },
                    'paste': function (o) {//粘贴
                    	jQuery.coordTable.paste(o);
                    },
                    
                    'szrxtj': function (o) {//设置人选条件
                    	options.szrxtj(o);
                    },
                    'czygrx': function (o) {//查找有关人选
                    	options.czygrx(o);
                    }
                }
            });
            if(options.hasMenu){
            	//添加默认菜单
                $('<div id="myMenu1">'
                + '<ul>'
                + '  <li id="ryzd">置顶 </li>'
               // + '  <li id="szrxtj">设置人选条件 </li>'
               // + '  <li id="czygrx">查找有关人选 </li>'
                + '  <li id="deleteP">删除人选</li>'
                + '  <li id="deleteGW">删除岗位</li>'
                + '  <li id="cut">剪切岗位</li>'
                + '  <li id="paste">粘贴岗位</li>'
                + '</ul>'
                + '</div>').appendTo(document.body).hide();
                //在选中区域中，用div覆盖其他html元素
            }
            
       // });
    }

})(jQuery);



