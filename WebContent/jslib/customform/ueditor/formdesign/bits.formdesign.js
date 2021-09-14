/*
* 设计器私有的配置说明 
* 一
* UE.bitsFormDesignUrl  插件路径
* 
* 二
*UE.getEditor('myFormDesign',{
*          toolleipi:true,//是否显示，设计器的清单 tool
*/
UE.bitsFormDesignUrl = 'formdesign';

// ---------- 自定义控件 开始 -----------------
/**
 * 单记录信息集字段控件
 * @command bitsfield
 * @method execCommand
 * @param { String } cmd 命令字符串
 * @example
 * ```javascript
 * editor.execCommand( 'bitsfield');
 * ```
 */
UE.plugins['bitsfield'] = function () {
	var me = this;
	var thePlugins = 'bitsfield';
	me.commands[thePlugins] = {
		execCommand:function () {
			var dialog = new UE.ui.Dialog({
				iframeUrl:this.options.UEDITOR_HOME_URL + UE.bitsFormDesignUrl+'/bitsfield.html?root=' + System.rootPath,
				name:thePlugins,
				editor:this,
				title: '单记录信息集字段控件',
				cssRules:"width:600px;height:310px;",
				buttons:[
				{
					className:'edui-okbutton',
					label:'确定',
					onclick:function () {
						dialog.close(true);
					}
				},
				{
					className:'edui-cancelbutton',
					label:'取消',
					onclick:function () {
						dialog.close(false);
					}
				}]
			});
			dialog.render();
			dialog.open();
		}
	};
	var popup = new baidu.editor.ui.Popup( {
		editor:this,
		content: '',
		className: 'edui-bubble',
		_edit: function () {
			  baidu.editor.plugins[thePlugins].editdom = popup.anchorEl;
			  me.execCommand(thePlugins);
			  this.hide();
		},
		_delete:function(){
			if( window.confirm('确认删除该单记录信息集字段控件吗？') ) {
				baidu.editor.dom.domUtils.remove(this.anchorEl,false);
			}
			this.hide();
		}
	} );
	popup.render();
	me.addListener( 'mouseover', function( t, evt ) {
		evt = evt || window.event;
		var el = evt.target || evt.srcElement;
        var bitsPlugins = el.getAttribute('bitsplugins');
		var title = el.getAttribute('orgtablename') + ' - ' + el.getAttribute('orgfieldname');
		if (bitsPlugins==thePlugins) {
			var html = popup.formatHtml(
				'<nobr>' + title + '&nbsp;&nbsp;<span onclick=$$._edit() class="edui-clickable">编辑</span>&nbsp;&nbsp;<span onclick=$$._delete() class="edui-clickable">删除</span></nobr>' );
			if ( html ) {
				popup.getDom( 'content' ).innerHTML = html;
				popup.anchorEl = el;
				popup.showAnchor( popup.anchorEl );
			} else {
				popup.hide();
			}
		}
	});
};

/**
 * 列表控件
 * @command bitslist
 * @method execCommand
 * @param { String } cmd 命令字符串
 * @example
 * ```javascript
 * editor.execCommand( 'bitslist');
 * ```
 */
UE.plugins['bitslist'] = function () {
    var me = this,thePlugins = 'bitslist';
    me.commands[thePlugins] = {
        execCommand:function () {
            var dialog = new UE.ui.Dialog({
                iframeUrl:this.options.UEDITOR_HOME_URL + UE.bitsFormDesignUrl+'/bitslist.html?root=' + System.rootPath,
                name:thePlugins,
                editor:this,
                title: '列表控件',
                cssRules:"width:800px;height:400px;",
                buttons:[
                {
                    className:'edui-okbutton',
                    label:'确定',
                    onclick:function () {
                        dialog.close(true);
                    }
                },
                {
                    className:'edui-cancelbutton',
                    label:'取消',
                    onclick:function () {
                        dialog.close(false);
                    }
                }]
            });
            dialog.render();
            dialog.open();
        }
    };
    var popup = new baidu.editor.ui.Popup( {
        editor:this,
        content: '',
        className: 'edui-bubble',
        _edit: function () {
              baidu.editor.plugins[thePlugins].editdom = popup.anchorEl;
              me.execCommand(thePlugins);
              this.hide();
        },
        _delete:function(){
            if( window.confirm('确认删除该列表控件吗？') ) {
                baidu.editor.dom.domUtils.remove(this.anchorEl,false);
            }
            this.hide();
        }
    } );
    popup.render();
    me.addListener( 'mouseover', function( t, evt ) {
		evt = evt || window.event;
		var el = evt.target || evt.srcElement;
        var bitsPlugins = el.getAttribute('bitsplugins');
		var title = el.getAttribute('orgtablename');
		if (bitsPlugins==thePlugins) {
			var html = popup.formatHtml(
				'<nobr>' + title + '&nbsp;&nbsp;<span onclick=$$._edit() class="edui-clickable">编辑</span>&nbsp;&nbsp;<span onclick=$$._delete() class="edui-clickable">删除</span></nobr>' );
			if ( html ) {
				popup.getDom( 'content' ).innerHTML = html;
				popup.anchorEl = el;
				popup.showAnchor( popup.anchorEl );
			} else {
				popup.hide();
			}
		}
	});
};

UE.plugins['error'] = function () {
    var me = this,thePlugins = 'error';
    me.commands[thePlugins] = {
        execCommand:function () {
            var dialog = new UE.ui.Dialog({
                iframeUrl:this.options.UEDITOR_HOME_URL + UE.bitsFormDesignUrl+'/error.html',
                name:thePlugins,
                editor:this,
                title: '异常提示',
                cssRules:"width:400px;height:130px;",
                buttons:[
                {
                    className:'edui-okbutton',
                    label:'确定',
                    onclick:function () {
                        dialog.close(true);
                    }
                }]
            });
            dialog.render();
            dialog.open();
        }
    };
};

UE.registerUI('btn_bits_addfield',function(editor,uiName){
    if(!this.options.toolbits)
    {
        return false;
    }
    //注册按钮执行时的command命令，使用命令默认就会带有回退操作
    editor.registerCommand(uiName,{
        execCommand:function(){
            editor.execCommand('bitsfield');
        }
    });
    //创建一个button
    var btn = new UE.ui.Button({
        //按钮的名字
        name:uiName,
        //提示
        title:"添加单记录信息集字段控件",
        //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
        cssRules :'background-position: -401px -40px;',
        //点击时执行的命令
        onclick:function () {
            //这里可以不用执行命令,做你自己的操作也可
           editor.execCommand(uiName);
        }
    });
	
    //因为你是添加button,所以需要返回这个button
    return btn;
});

UE.registerUI('btn_bits_addlist',function(editor,uiName){
    if(!this.options.toolbits)
    {
        return false;
    }
    //注册按钮执行时的command命令，使用命令默认就会带有回退操作
    editor.registerCommand(uiName,{
        execCommand:function(){
            editor.execCommand('bitslist');
        }
    });
    //创建一个button
    var btn = new UE.ui.Button({
        //按钮的名字
        name:uiName,
        //提示
        title:"添加列表控件",
        //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
        cssRules :'background-position: -339px -40px;',
        //点击时执行的命令
        onclick:function () {
            //这里可以不用执行命令,做你自己的操作也可
           editor.execCommand(uiName);
        }
    });

    //因为你是添加button,所以需要返回这个button
    return btn;
});

// ---------- 自定义控件 结束 -----------------
 