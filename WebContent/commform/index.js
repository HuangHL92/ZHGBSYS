window.history.forward(1);
var tabCount;
if (tabCount == null) {
	tabCount = 10; // 本系统所允许打开的最大tab页数
}
var commParams = {};
var psqueryBuffer = "";
var cpqueryBuffer = "";
var psidqueryBuffer = "";
tabCount++; // 由于有1个自带的，所以要加1处理
Ext.ux.TabCloseMenu = function() {
	var tabs, menu, ctxItem;
	this.init = function(tp) {
		tabs = tp;
		tabs.on('contextmenu', onContextMenu);
	}
	function onContextMenu(ts, item, e) {
		if (!menu) { // create context menu on first right click
			menu = new Ext.menu.Menu([{
						id : tabs.id + '-close',
						text : '关闭本页',
						handler : function() {
							tabs.remove(ctxItem);
						}
					}, {
						id : tabs.id + '-close-others',
						text : '关闭其他页',
						handler : function() {
							tabs.items.each(function(item) {
										if (item.closable && item != ctxItem) {
											tabs.remove(item);
										}
									});
						}
					}]);
		}
		ctxItem = item;
		var items = menu.items;
		items.get(tabs.id + '-close').setDisabled(!item.closable);
		var disableOthers = true;
		tabs.items.each(function() {
					if (this != item && this.closable) {
						disableOthers = false;
						return false;
					}
				});
		items.get(tabs.id + '-close-others').setDisabled(disableOthers);
		menu.showAt(e.getPoint());
	}
};

var todaystr = YYMMDD();
var weekdaystr = weekday();
// var today2str=solarDay2();
var today3str = solarDay3().replace(/(^\s*)|(\s*$)/g, "");

var tabs = new Ext.TabPanel({
			region : 'center',
			deferredRender : false,
			frame : true,
			minTabWidth : 30,
			tabWidth : 135,
			enableTabScroll : true,
			defaults : {
				autoScroll : false,
				autoWidth : true
			},
			plugins : new Ext.ux.TabCloseMenu(),
			activeItem : 0,
			items : {
				title : '欢迎',
				closable : false,
				id : 'homepage',
				iconCls : 'iconwelcome',
				html : '<Iframe width="100%" height="100%" scrolling="auto" frameborder="0" src="' + contextPath + '/commform/Welcome.jsp' + '" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>'
			}
		});

function loadPage(treenode, url) {
	// alert(url);
	addTab(treenode.text, treenode.id, contextPath + url, false);
};

function addTab(atitle, aid, src, forced, autoRefresh) {
	var tab = tabs.getItem(aid);
	if (forced)
		aid = 'R' + (Math.random() * Math.random() * 100000000);
	if (tab && !forced) {
		tabs.activate(tab);
		
		if(typeof autoRefresh!='undefined' && autoRefresh){
            document.getElementById('I'+aid).src = src;
        }
	} else {
		if (getSysType() == 'sionline') {// 网上申报在打开页面的时候就校验
			var url = contextPath + "/pages/mdParamsAction.do?method=doOpctrl";
			var params = {};
			params.functionid = aid;
			var req = odin.Ajax.request(url, params, odin.ajaxSuccessFunc, null, false, false);
			var ret = odin.ext.decode(req.responseText).data;
			if (ret != "") {
				odin.error(ret);
				return;
			}
		}
		var count = tabs.items.getCount();
		if (count < tabCount) {
			tabs.add({
						title : (atitle),
						id : aid,
						html : '<Iframe width="100%" height="100%" scrolling="auto" id="I'+aid+'"  frameborder="0" src="' + src + '" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
						closable : true
					}).show();
		} else {
			odin.info('系统最多允许同时打开' + (tabCount - 1) + '个功能，请先关闭其他暂时不用的功能。');
		}

	}
};

Ext.onReady(function() {

	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

	var hd = new Ext.Panel({
				border : false,
				layout : 'anchor',
				region : 'north',
				height : 65,
				items : [{
					xtype : 'box',
					el : 'header',
					border : false
						// anchor: 'none -25'
					}
				/*
				 * new Ext.Toolbar({ items:[ ' ', '<img
				 * src="'+g_contextpath+'/img/icon/currentuser.gif" height=16
				 * width=16>', '当前用户:'+g_username , '-', '', todaystr,
				 * weekdaystr, '-', today3str, '系统版本:'+odin.version, '->', new
				 * Ext.Toolbar.Button({ text:'帮助',
				 * icon:g_contextpath+'/img/icon/help.gif',
				 * cls:'x-btn-text-icon', tooltip :'显示帮助信息' }), new
				 * Ext.Toolbar.Button({ text:'退出', cls:'x-btn-text-icon',
				 * icon:g_contextpath+'/img/icon/rss_load.gif', tooltip :'退出系统',
				 * handler :function(b, pressed){ //alert('logout'); if
				 * (confirm('确定要退出系统吗?'))
				 * window.top.location.href=g_contextpath+'/logoffAction.do';} }) ] })
				 */
				]
			});

	var leftnav = new Ext.Panel({
				region : 'west',
				id : 'west-panel',
				title : '导航栏',
				split : false,
				width : 214,
				minSize : 214,
				maxSize : 214,
				collapsible : true,
				margins : '0 1 0 1',
				layout : 'accordion',
				layoutConfig : {
					animate : true
				},
				items : [{
							contentEl : 'mainmenu2',
							title : menuroot.text,
							// icon:g_contextpath+'/img/icon/folder_go.png',
							// cls:'x-btn-text-icon',
							iconCls : 'iconnav',
							border : false,
							listeners : {
								expand : function() {
									setTimeout('tree.doLayout()');
								}
							}
						}
				/*
				 * ,{ title:'搜索中心', html:'<p>某些信息</p>',
				 * iconCls:'iconsearch', border:false } ,{ title:'我的日志', html:'<p>日志信息</p>',
				 * iconCls:'iconlog', border:false }
				 */
				]
			});
	var i = 0;
	while (true) {
		i++;
		var menurooti = eval('typeof(menuroot' + i + ')=="undefined"?null:menuroot' + i);
		if (menurooti) {
			var elId = 'mainmenudiv' + i;
			leftnav.add({
						contentEl : elId,
						title : menurooti.text,
						iconCls : 'iconnav',
						border : false,
						listeners : {
							expand : function() {
								setTimeout('tree' + (this.contentEl.substring(this.contentEl.length - 1)) + '.doLayout()');
							}
						}
					});
		} else {
			break;
		}
	}
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [hd, leftnav, tabs]
			});
		// tree.render();
	});

/**
 * 打开弹出窗口
 */
function doOpenPupWinOnTop(src, title, width, height, initParams, parentWin) {
	if (parentWin == null) {
		parentWin = this;
	}
	doOpenPopWindow(src, title, width, height, initParams, parentWin);
}

/**
 * 隐藏弹出窗口
 */
function doClosePupWinOnTop() {
	var windowId = "pupWindow";
	var pupWindow = Ext.getCmp(windowId);
	pupWindow.hide();
}

/**
 * 取得顶层窗口
 */
function getTopParent() {
	return this;
}

/**
 * 打开弹出窗口
 */
function doOpenPupWindow(src, title, width, height, initParams, parentWin) {
	if (parentWin == null) {
		parentWin = this;
	}
	doOpenPopWindow(src, title, width, height, initParams, parentWin);
}
/**
 * 打开弹出窗口
 */
function doOpenPopWindow(src, title, width, height, initParams, parentWin) {
	if (parentWin == null) {
		parentWin = this;
	}
	tempParentWin = parentWin;
	var windowId = "pupWindow";
	var popWindow = Ext.getCmp(windowId);
	popWindow.setTitle(title); // 标题
	if (width <= 1) {
		if (width >= 0) {// 小数
			width = document.body.clientWidth * width;
		} else { // 负数
			width = document.body.clientWidth + width;
		}
	}
	if (height <= 1) {
		if (height >= 0) {// 小数
			height = document.body.clientHeight * height;
		} else { // 负数
			height = document.body.clientHeight + height;
		}
	}
	popWindow.setSize(width, height); // 宽度 高度
	showWindowWithSrc(windowId, src, initParams, "doTopPupWinOnload(this)");
	popWindow.center(); // 居中
}

var tempParentWin;

/**
 * 根据传入的地址显示窗口
 */
function showWindowWithSrc(windowId, newSrc, initParams, onload) {
	if (newSrc.indexOf("http:") == -1 && newSrc.indexOf("www.") == -1 && newSrc.indexOf(contextPath) != 0) {
		newSrc = contextPath + newSrc;
	}
	if (document.all("iframe_" + windowId)) {
		document.all("iframe_" + windowId).src = newSrc;
	} else {
		Ext.getCmp(windowId).html = "<iframe style=\"background:white;border:none;\" width=\"100%\" height=\"100%\" id=\"iframe_" + windowId + "\" name=\"iframe_" + windowId + (onload == null ? "" : "\" onload=\"" + onload) + "\" src=\"" + newSrc + "\">";
	}
	if (initParams) {
		commParams.initParams = initParams;
		commParams.initParamsUsedBy = null;
	}
	Ext.getCmp(windowId).show(Ext.getCmp(windowId));
	Ext.getCmp(windowId).focus();
}

/**
 * 兼容IE、Firefox的iframe窗口获取函数
 */
function getIFrameWin(id) {
	return document.getElementById(id).contentWindow || document.frames[id];
}

/**
 * 显示窗口加载完成触发的事件
 */
function doTopPupWinOnload(iframe) {
	try {
		getIFrameWin(iframe.id).setParentWin(tempParentWin);
	} catch (e) {
	}
}
/**
 * 取得登陆用户名
 */
function getCurrentLoginName() {
	return document.getElementById('currentLoginName').value;
}

function getSysType() {
	return document.getElementById('systype').value;
}

function getCurrentAab301() {
	return document.getElementById('currentAab301').value;
}

function getCurrentAaa027() {
	return document.getElementById('currentAaa027').value;
}

function getPsqueryBuffer() {
	return psqueryBuffer;
}

function setPsqueryBuffer(psquery) {
	psqueryBuffer = psquery;
}

function getPsidqueryBuffer() {
	return psidqueryBuffer;
}

function setPsidqueryBuffer(psidquery) {
	psidqueryBuffer = psidquery;
}

function getCpqueryBuffer() {
	return cpqueryBuffer;
}

function setCpqueryBuffer(cpquery) {
	cpqueryBuffer = cpquery;
}
