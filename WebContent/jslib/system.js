var SUCCESS = 'success';

String.prototype.trim=function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
String.prototype.Trim=function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
String.prototype.ltrim=function(){
	return this.replace(/(^\s*)/g, "");
};
String.prototype.rtrim=function(){
	return this.replace(/(\s*$)/g, "");
};
String.prototype.replaceAll  = function(s1,s2){  
	return this.replace(new RegExp(s1,"gm"),s2);   
}; 


// 防止iframe内存泄漏
$.fn.panel.defaults.onBeforeDestroy = function() {
    /* 回收内存 */
	var frame = $('iframe', this);
	if (frame.length > 0) {
		frame[0].contentWindow.document.write('');
		frame[0].contentWindow.close();
		frame.remove();
		if ($.browser.msie) {
			CollectGarbage();
		}
	}
};

function getZTreeObj(treeId) {
	return $.fn.zTree.getZTreeObj(treeId);
}

var System = {
		openURL: function(title, url, loadingMsg, forceUpdate, iconCls){
			var content = '<iframe scrolling="auto" frameborder="0" src="' + url + '" style="width:100%;height:100%;display:block;"></iframe>';
			if (top.$('#mainTab').tabs('exists', title)) {
				if (forceUpdate) {
					var theTabe = top.$('#mainTab').tabs('getTab', title);
					top.$('#mainTab').tabs('select', title);	
					top.$('#mainTab').tabs('update', {
					    tab: theTabe, 
						options: {
							content: content,
							loadingMessage: loadingMsg ? loadingMsg : '载入数据中，请稍后...'
						}
					});					
				}
				else {
					top.$('#mainTab').tabs('select', title);				
				}
			} else {
				top.$('#mainTab').tabs('add', {
					title: title,
					content: content,
					iconCls: iconCls ? iconCls : null,
					loadingMessage: loadingMsg ? loadingMsg : '载入数据中，请稍后...',
					closable:true
				});
			}
		},
		
		closeCurrentTab: function() {
			try {
				window.parent.closeCurrentTab();				
			}
			catch (e) {
			}
		},
			
		openLoadMask:function openLoadMask(wrap,msg){
			if(msg == undefined){
				msg = "数据加载中,请稍等 ...";
			}
			var wrap = $(wrap);
			var zi = isNaN(parseInt(wrap.css('z-index'))) ? 10000:parseInt(wrap.css('z-index'));
			$('<div class="datagrid-mask"></div>').css({
					display:'block',
					top:wrap.offset().top,
					width: wrap.outerWidth()+10,
					height: wrap.outerHeight(),
					zIndex:zi++
				}).appendTo(wrap);
			$('<div class="datagrid-mask-msg"></div>')
					.html(msg)
					.appendTo(wrap)
					.css({
						display:'block',
						left:(wrap.width()-$('.datagrid-mask-msg',wrap).outerWidth())/2,
						top:(wrap.height()-$('.datagrid-mask-msg',wrap).outerHeight())/2
					});
		},
		closeLoadMask:function closeLoadMask(wrap){
			$(".datagrid-mask-msg",wrap).hide().remove();
			$(".datagrid-mask",wrap).hide().remove();
		},
		
		removeElement:function removeElement(arrPerson,objPropery,objValue){
		   return $.grep(arrPerson, function(cur,i){
						return cur[objPropery]!=objValue;
				  });
		},
		
		getElement:function getElement(arrPerson,objPropery,objValue){
			var arry = $.grep(arrPerson, function(cur,i){
				  return cur[objPropery]==objValue;
			     });
		   return arry[0];
		},
		insertAtCursor:function insertAtCursor(myField, myValue) { 
			if (document.selection){   
			    myField.focus();   
			    sel = document.selection.createRange();   
			    sel.text = myValue;   
			    sel.select();   
		    }else if (myField.selectionStart || myField.selectionStart == '0') { 

		        	var startPos = myField.selectionStart; 

		          var endPos = myField.selectionEnd; 

		          myField.innerHTML = myField.innerHTML.substring(0, startPos) 
		              + myValue 
		              + myField.innerHTML.substring(endPos, myField.innerHTML.length); 
		      }else{ 
		          myField.innerHTML += myValue; 
		      } 
		},
		random : function (){
			return String(Math.random()).replace(new RegExp("\\.","gm"),"_");
		},
		get : function(url, param, callback) {
			$.get(url, param, function(data) {
				try {
					if (data == '' || data == 'undefined' || data.code == null || data.code == 'undefined') {
						return;
					}
					
					callback(data);
				}
				catch (e) {
					// do nothing
				}
			});
		},
		postEx : function(url, param, showFailureMsg, successCallback, failureCallback) {
			$.post(url, param, function(data) {
				try { MaskUtil.unmask();} catch (e) {}
				
				var hasFailureCallback = (failureCallback && typeof(failureCallback)=="function");
				try {
					if (data == '' || data == 'undefined' || data.code == null || data.code == 'undefined') {
						if (showFailureMsg) {
							if (data.indexOf('正在进行登录...') > 0) {
								System.showErrorMsg('当前登录会话已经过期，请重新登录！');
							}
							else {
								System.showErrorMsg('无法执行本次操作！\r\n' + data);
							}
						}
						
						if (hasFailureCallback) {
							var ret;
							
							if (data.indexOf('正在进行登录...') > 0) {
								ret = { code: 'failed', message: '当前登录会话已经过期，请重新登录！'};
							}
							else {
								ret = { code: 'failed', message: '无法执行本次操作！\r\n' + data};
							}
							
							failureCallback(ret);
						}
						
						return;
					}
					
					if (data.code == SUCCESS) {
						successCallback(data);
					}
					else {
						if (showFailureMsg) {
							System.showErrorMsg(data.message);
						}
							
						if (hasFailureCallback) {
							failureCallback(data);
						}
					}
				}
				catch (e) {
					if (showFailureMsg) {
						System.showErrorMsg('无法执行本次操作！\r\n' + e);
					}
					
					if (hasFailureCallback) {
						var ret = { code: 'failed', message: '无法执行本次操作！\r\n' + e};
						failureCallback(ret);
					}
				}
			});
		},
		post : function(url, param, successCallback, failureCallback) {
			System.postEx(url, param, true, successCallback, failureCallback);
		},
		post2 : function(url, param, successCallback, failureCallback) {
			System.postEx(url, param, false, successCallback, failureCallback);
		},
		showInfoMsg : function(message) {
			$.messager.alert('系统提示', message, 'info');
		},
		showErrorMsg : function(message) {
			$.messager.alert('系统提示', message, 'error');
		},
		showProgressMsg: function(msg) {
			$.messager.progress( { text : msg }); 
		},
		closeProgressMsg: function() {
			$.messager.progress('close');
		},
		showConfirmMsg: function(message, callback) {
			$.messager.confirm('操作确认', message, function(r) {
				if (r) {
					if (callback && typeof(callback)=="function") {
						callback();
					}
				}
			});
		},
		openPopupWindow: function(url, title, width, height, onOpenCallback, onCloseCallback, dialogId, maxShow) {
			//var isTopWin = window.top == window.self;
			//if (!isTopWin) {
				var id = top.createPopupDialog(dialogId);
				var divId = "popupWin" + id;
				var iframeId = "popupWinIframe" + id;
				
				top.$("#" + divId).dialog({
					title: title,
					width: width,
					height: height,
					modal: true,
					collapsible: false,
					minimizable: false,
					maximizable: true,
					maximized: maxShow == 'undefined' ? false : maxShow,
					resizable: true,
					onOpen: function() {
						top.$("#" + iframeId).attr('src', url);
						if (onOpenCallback && typeof(onOpenCallback) == "function") {
							onOpenCallback();
						}
					},
					onClose: function() {
						top.$("#" + iframeId).attr('src', '');	
						try {
							if (onCloseCallback && typeof(onCloseCallback) == "function") {
								onCloseCallback();
							}		
						}
						catch (e) {
							
						}

						top.$("#" + divId).dialog('destroy');
						top.removePopupDialog(id);
					}
				});
			//}
			
			return id;
		},
		
		closePopupWindow: function(dialogId) {
			//var isTopWin = window.top == window.self;
			//if (!isTopWin) {
				var divId = "popupWin" + dialogId;
				top.$("#" + divId).dialog('close');
			//}
		}
};

System.rootPath = "/";
System.archiveWeb = "http://localhost:8080/bitsda";

// 设置默认的日期格式
$.fn.datebox.defaults.formatter = function(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	if (m < 10) m = "0" + m;
	var d = date.getDate();
	if (d < 10) d = "0" + d;
	return y + '-' + m + '-' + d;
}

function createPopupDialog(dialogId) {
	var id = Math.floor(Math.random() * (10000 + 1));
	if (dialogId && typeof(dialogId) != 'undefined') {
		id = dialogId;
	}
	
	var divId = "popupWin" + id;
	var iframeId = "popupWinIframe" + id;
	var html = 	'<div id="' + divId + '">';
	html += '<iframe id="' + iframeId + '" name="' + iframeId + '" src="" style="width:100%;height:100%;display:block;" scrolling="auto" frameborder="0"></iframe></div>';
	if ($("#" + divId).length == 0) {
		$(document.body).append(html);
	}
	
	return id;
}

function removePopupDialog(id) {
	var divId = "popupWin" + id;
	$("#" + divId).remove();
}


var MaskUtil = (function(){  
    var $mask,$maskMsg;  
    var defMsg = '正在处理，请稍候...';  
    function init(){  
        if(!$mask){  
            $mask = $("<div class=\"datagrid-mask\"></div>").appendTo("body");  
        }  
        if(!$maskMsg){  
            $maskMsg = $("<div class=\"datagrid-mask-msg\">"+defMsg+"</div>").appendTo("body").css({'font-size':'12px', 'zIndex' : 9999});  
        }  
          
        $mask.css({zIndex: 9998, top: 0, left: 0, width:"100%",height:$(document).height()});  
          
        var scrollTop = $(document.body).scrollTop();  
          
        $maskMsg.css({  
            left:( $(document.body).outerWidth(true) - 100) / 2  
            ,top:( ($(window).height() - 45) / 2 ) + scrollTop  
        });   
                  
    }  
      
    return {  
        mask:function(msg){  
            init();  
            $mask.show();  
            $maskMsg.html(msg||defMsg).show();  
        }  
        ,unmask:function(){  
            $mask.hide();  
            $maskMsg.hide();  
        }  
    } 
}());  
System.Params = {};