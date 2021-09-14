/*
功能说明:
复制：复制选中行数据或者单元格的数据，如果没有选择则复制鼠标点击的单元格数据。
粘贴：如果复制的是单元格数据（没有“,”的数据表示单元格数据），那么粘贴的时候只修改选中的单元格；
如果复制的是行数据，那么：
1、粘贴时选中的是单元格，那么在选中的单元格的那行上面添加一行或多行，行数据为复制的数据；
2、粘贴的时候选中的是行，那么在选中行的上面添加新行，行数据为复制的数据，然后删除选中行。
剪贴：复制数据，如果选中（每个单元格都被选中）行，则删除选中行，如果没有则清空选中的单元格数据。
追加：总是在表格最后添加一个空行。
插入：在选中行或者鼠标点击的那行前面添加一个空白行。
删除：删除所选行，如果没有选择，则删除鼠标点击的那行。
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
            selector_row: 'tr:gt(0) td:nth-child(2)', //一个jquery选择器，表示可以点击选择行的区域
            selector_drag: 'tr:gt(0)', //一个jquery选择器，表示可以用鼠标拖拽的区域 td:nth-child(n+3)
            drag_pre_color: '#0ECA40',
            drag_color: "#000000", //鼠标拖拽后的背景颜色
            default_color: '#FFFFFF', //默认背景颜色
            create_row: false, //创建表格行的函数
            getValue: false, //获取值的函数，仅用于单元格
            setValue: false, //设置值的函数，仅用于单元格
            pasted: false, //粘贴后调用的函数
            deleted: false, //删除行后调用的函数
            cuted: false, //剪贴后调用的函数
            appended: false, //追加行后调用的函数
            inserted: false, //插入行后调用的函数
            cleared: false, //清空后执行的方法
            rowsInserted: false, //行插入后事件，（由插入或者粘贴触发）
            enableEdit: false, //开启编辑
            enableCopy: true, //开启复制
            enablePaste: true, //开启粘贴
            enableCut: true, //开启剪贴
            enableAppend: true, //开启追加
            enableInsert: true, //开启插入
            enableDelete: true, //开启删除
            enableClear: true,
            updateData:false,//剪切时保存对象 zoulei
            rowsPasted:false,//剪切是保存对象 zoulei
            rowsDelete:false,//删除行。
            selectTPRY:false,//选择人员
            updateNRM:false,
            saveGDCL:true,
            readGDCL:true,
            picupload:true,
            uploadKCCL: false//上传考察材料
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
            //获取单元格中显示的值
            getValue: function (obj) { 
                if (options.getValue) {
                    return options.getValue(obj);
                }
                else {
                    var ele = $('input[type="text"],select,textarea', obj).first();
                    if (ele.length == 0) {
                        return $(obj).text();
                    }
                    else {
                        return $(ele).val();
                    }
                }
            },
            //设置单元格中显示的值
            setValue: function (obj, value,rowIndex,colIndex) {
            	jQuery.tableEditer.setValue(obj, value,rowIndex,colIndex);
            },
            //复制
            copy: function (o) {
                var text = '';
                var rows = $(selector).parent().parent();
                if (rows.length > 0) {
                    var copyData = new Array();
                    $(rows).each(function () {
                        var item = new Array();
                        $('td ' + selector_div, $(this)).each(function () {
                            item.push(jQuery.coordTable.getValue($(this).parent()));
                        });
                        copyData.push(item);
                    });
                    for (var i = 0; i < copyData.length; i++) {
                        text = text + copyData[i].join(',');
                        if (i < copyData.length - 1) {
                            text = text + '\r\n';
                        }
                    }
                }
                else {
                    text = jQuery.coordTable.getValue(o);
                }
                copyToClipboard(text);
                return text;
            },
            //粘贴
            paste: function (o) {
                //选中行
                
                if (options.rowsPasted) {
                    options.rowsPasted(o);
                }
                
            },
            //粘贴时，设置td文本 type:1单个文本 2多行或者多列
            setText: function (data, o, row, type) {
                var _row = row;
                //单元格在当前行的位置，开始粘贴的位置
                var td_index = $(o).index();
                var insertRows = [];
                if (type == 1) {
                    this.setValue($(o), data.toString());
                    return;
                }
                for (var i = data.length - 1; i >= 0; i--) {
                    var newRow = this.createRow();
                    $('td', newRow).each(function (index, td) {
                        if (index >= td_index && data[i].length > index - td_index) {
                            var txt = data[i][index - td_index];
                            if (txt) {
                                jQuery.coordTable.setValue(td, txt);
                            }
                        }
                    });
                    $(newRow).insertBefore(_row);
                    _row = newRow;
                    insertRows.push(newRow);
                }
                if (options.rowsInserted) {
                    options.rowsInserted(insertRows);
                }
            },
            //删除
            del: function (o) {
                var rows = $(selector);
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
                var tips = '确认删除当前记录行的数据！';
                if (rows.length > 1) {
                    tips = '确认删除从当前选择的' + rows.length + '行数据';
                }
                
                if (confirm(tips)) {
                	if (options.rowsDelete) {
                        options.rowsDelete(rows);
                    }
                    
                }
            },
            //追加
            append: function (o) {
                //var tr = this.createRow();
                //$(o).append(tr);
                var rows = $(selector);
                var insertRows = [];
                if (rows.length > 1) {
                    alert('不能选定多行执行插入操作。');
                    return;
                }
                var type = "3";
                var tr = this.createRow(type,$(o).index());
                if($(o).index()==0){ 
                	$(o).parent().append(tr);
                }else{
                	  $(tr).insertBefore($(o));
                }
                insertRows.push(tr);
                if (options.rowsInserted) {
                    options.rowsInserted(insertRows);
                }            	
            },
            //插入
            insert: function (o,type) {
                var rows = $(selector);
                var insertRows = [];
                if (rows.length > 1) {
                    alert('不能选定多行执行插入操作。');
                    return;
                }
                var tr = this.createRow(type,$(o).index());
                if($(o).index()==0){//zoulei 追加
                	$(o).parent().append(tr);
                }else{
                	  $(tr).insertBefore($(o));
                }
                insertRows.push(tr);
                if (options.rowsInserted) {
                    options.rowsInserted(insertRows);
                }
            },
            //剪贴 删除剪切的tr，删除数据对象相关的数据，并保存，粘贴时插入保存的数据。 zoulei
            cut: function (o) {
                //jQuery.coordTable.copy(o);
                //需要删除的tr
                var rows = [];
                //选中的td selector,className .ui-selected
                var cols = $(selector);
                //每行列数-第一列（按钮列）
                if (cols.length == 0) {
                    //没有选择的时候剪贴 zoulei 剪切当前行
                	rows.push($(o));
                    //this.setValue(o, '');
                }
                else {
                   /* $('tr', $this).filter(function () {
                        return $('td', this).hasClass(className);
                    }).each(function () {
                        if ($('td', $(this)).length>0) {
                            rows.push(this);
                        }
                    });*/
                	rows = cols;
                    
                }
                if (rows.length > 0) {
                	if (rows.length == 1) {//zoulei 表头不剪切
	                	var rowIndex = $(rows[0]).index();
	        			if(rowIndex==0){//表头
	        				return;
	        			}
                	}
                    var tips = '确认剪切当前记录行的数据！';
                    if (rows.length > 1) {
                        tips = '确认剪切从当前选择的' + rows.length + '行数据';
                    }
                    if (confirm(tips)) {
                    	//更新数据对象；保存已剪切的数据对象 zoulei
                    	if (options.updateData) {
                    		options.updateData(rows);
                        }
                        $.each(rows, function () {
                            $(this).remove();
                        });
                    }
                }
            },
            //取消选中
            unSelect: function (ele) {
                var selected = ele ? ele : $(selector + ',.ui-selectee,.ui-selecting');
                $(selected)
                .removeClass("drag_pre_color drag_color")
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
            },
            //selector_drag: 'tr:gt(0) td:nth-child(n+3)', //一个jquery选择器，表示可以用鼠标拖拽的区域
            //selector_float = 'div.drag';
            appendDiv: function () {
                //删除浮动div
                //$('td ' + selector_float, $this).remove();
                /*$(options.selector_drag, $this).each(function () {
                    jQuery.coordTable.floatOn($(this));
                });*/
            },
            clear: function () {
            	$("#coordTableHtmlContent").val("");
                //$('tr:gt(0)', $this).remove();
    			$(".data").remove();
    			GLOBLE['ROWID']=[];
    			GLOBLE['ID_ROWINFO']={};
    			//情空缓存
        		GLOBLE['CUTEDROWID']=[];
        		GLOBLE['CUTEDDATA']={}
            }
        };

      //  return $(this).each(function () {

            //行取消选中
            $(options.selector_drag).live('click', function () {
            	selectedRow = null;
                jQuery.coordTable.unSelect();
                if (options.enableEdit) {
                    //可以编辑
                    $(this).find('input[type="text"]').focus();
                }
            });

            //点击选中行
            $(options.selector_row, this).live('click', function (e) {
            	selectedRow = $(this).parent();
                jQuery.coordTable.unSelect();
                $(this).parent()
                .addClass(className+" drag_color");
                
                $("#coordTable tr td").css("position","");
                
                return false;
            });

            //鼠标拖拽选中
            $(this).selectable({//selector_drag tr:gt(0) td:nth-child(n+3)
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
            });

            //右键菜单
            var contextMenu = $("tr", this).contextMenu('myMenu1', {
                bindings: {
                    'copy': function (o) {
                        if (options.enableCopy) {
                            jQuery.coordTable.copy(o);
                        }
                    },
                    'paste': function (o) {//粘贴
                        if (options.enablePaste) {
                            jQuery.coordTable.paste(o);
                            jQuery.coordTable.appendDiv();
                            if (options.pasted) {
                                options.pasted(o);
                            }
                        }
                    },
                    'append': function (o) {//追加
                        if (options.enableAppend) {
                            jQuery.coordTable.append(o);
                            jQuery.coordTable.appendDiv();
                            if (options.appended) {
                                options.appended(o);
                            }
                        }
                    },
                    'insert': function (o) {//插入正文
                    	//alert("建设中...")
                    	//return;
                        if (options.enableInsert) { 
                        	$("#coordTableHtmlContent").val("");
                        	var ID_ROWINFO = Ext.encode(GLOBLE['ID_ROWINFO']);
                        	var ROWID = Ext.encode(GLOBLE['ROWID']);
                        	$('#ID_ROWINFO').val(ID_ROWINFO);
                        	$('#ROWID').val(ROWID); 
                        	
                            if (options.selectTPRY) {//执行 complate 更新序号
                                options.selectTPRY(o);
                            }
                        }
                    },
                    'insertBiaoTi1': function (o) {//插入一级标题
                    	if (options.enableInsert) {
                    		jQuery.coordTable.insert(o,1);
                    		jQuery.coordTable.appendDiv();
                    		if (options.inserted) {//执行 complate 更新序号
                    			options.inserted(o);
                    		}
                    	}
                    },
                    'insertBiaoTi2': function (o) {//插入二级标题
                    	if (options.enableInsert) {
                    		jQuery.coordTable.insert(o,2);
                    		jQuery.coordTable.appendDiv();
                    		if (options.inserted) {//执行 complate 更新序号
                    			options.inserted(o);
                    		}
                    	}
                    },
                    'delete': function (o) {//删除
                        if (options.enableDelete) {
                            jQuery.coordTable.del(o);
                            jQuery.coordTable.appendDiv();
                            if (options.deleted) {
                                options.deleted(o);
                            }
                        }
                    },
                    'cut': function (o) {//剪贴
                        if (options.enableCut) {
                            jQuery.coordTable.cut(o);
                            jQuery.coordTable.appendDiv();
                            if (options.cuted) {
                                options.cuted(o);
                            }
                        }
                    },
                    'clear': function (o) {//清空
                        if (options.enableClear) {
                            jQuery.coordTable.clear();
                            jQuery.coordTable.appendDiv();
                            if (options.cleared) {
                                options.cleared(o);
                            }
                        }
                    },
                    'updateNRM': function (o) {//更新拟任免
                    	if (options.updateNRM) {//
                            options.updateNRM(o);
                        }
                    },
                    
                    'saveGDCL': function (o) {//保存归档信息
                    	if (options.saveGDCL) {//
                            options.saveGDCL(o);
                        }
                    },
                    
                    'readGDCL': function (o) {//读取存归档信息
                    	if (options.readGDCL) {//
                            options.readGDCL(o);
                        }
                    },
                    
                    
                    'picupload': function (o) {//上传考察材料
                    	if (options.picupload) {//
                    		options.picupload(o);
                    	}
                    },
                    
                    'uploadKCCL': function (o) {//上传考察材料
                    	if (options.uploadKCCL) {//
                    		options.uploadKCCL(o);
                    	}
                    }
                }
            });

            //添加默认菜单
            $('<div id="myMenu1" style="padding:10px!important">'
            + '<ul style="padding:10px">'
            + '  <li id="insertBiaoTi1">&nbsp;<img src="css/images/xtheme-blue/icon/btn_save.png">&nbsp;插入小标题</li>'
         //   + '  <li id="insertBiaoTi2">插入二级标题</li>'
            + '  <li id="insert">&nbsp;<img src="css/images/xtheme-blue/icon/btn_import.png">&nbsp;导入人员</li>'
              + '  <li id="append">&nbsp;<img src="css/images/xtheme-blue/icon/btn_add.png">&nbsp;新增空行</li>'
              + '  <li id="picupload">&nbsp;<img src="css/images/xtheme-blue/icon/btn_per_phote.png">&nbsp;更换照片</li>'
            + '  <li id="cut">&nbsp;<img src="css/images/xtheme-blue/icon/btn_org_append.png">&nbsp;剪切行</li>'
        //    + '  <li id="copy">复制</li>'
            + '  <li id="paste">&nbsp;<img src="css/images/xtheme-blue/icon/btn_per_bat_repair.png">&nbsp;粘贴行</li>'
            + '  <li id="clear">&nbsp;<img src="css/images/xtheme-blue/icon/btn_clean.png">&nbsp;清空</li>'
            + '  <li id="delete">&nbsp;<img src="css/images/xtheme-blue/icon/btn_delete.png">&nbsp;删除行</li>'
          //  + '  <li id="updateNRM">更新拟任免信息</li>'
         //   + '  <li id="saveGDCL">材料归档</li>'
          //  + '  <li id="readGDCL">读取归档材料</li>'
           
            + '  <li id="uploadKCCL">&nbsp;<img src="css/images/xtheme-blue/icon/btn_per_transfer.png">&nbsp;考察材料管理</li>' 
            + '</ul>'
            + '</div>').appendTo(document.body).hide();
            //在选中区域中，用div覆盖其他html元素
            jQuery.coordTable.appendDiv();
       // });
    }

})(jQuery);

function copyToClipboard(txt) {
    if (window.clipboardData) {
        window.clipboardData.clearData();
        window.clipboardData.setData("Text", txt);
    } else if (navigator.userAgent.indexOf("Opera") != -1) {
        window.location = txt;
    } else if (window.netscape) {
        try {
            netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
        } catch (e) {
            alert("被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'");
        }
        var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
        if (!clip)
            return;
        var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
        if (!trans)
            return;
        trans.addDataFlavor('text/unicode');
        var str = new Object();
        var len = new Object();
        var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
        var copytext = txt;
        str.data = copytext;
        trans.setTransferData("text/unicode", str, copytext.length * 2);
        var clipid = Components.interfaces.nsIClipboard;
        if (!clip)
            return false;
        clip.setData(trans, null, clipid.kGlobalClipboard);
    }
}


