window.history.forward(1);
var tabCount;
if (tabCount == null) {
	tabCount = 10; // ��ϵͳ������򿪵����tabҳ��
}
var commParams = {};
var psqueryBuffer = "";
var cpqueryBuffer = "";
var psidqueryBuffer = "";
tabCount++; // ������1���Դ��ģ�����Ҫ��1����
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
						text : '�رձ�ҳ',
						handler : function() {
							tabs.remove(ctxItem);
						}
					}, {
						id : tabs.id + '-close-others',
						text : '�ر�����ҳ',
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
				title : '��ӭ',
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
		if (getSysType() == 'sionline') {// �����걨�ڴ�ҳ���ʱ���У��
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
			odin.info('ϵͳ�������ͬʱ��' + (tabCount - 1) + '�����ܣ����ȹر�������ʱ���õĹ��ܡ�');
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
				 * width=16>', '��ǰ�û�:'+g_username , '-', '', todaystr,
				 * weekdaystr, '-', today3str, 'ϵͳ�汾:'+odin.version, '->', new
				 * Ext.Toolbar.Button({ text:'����',
				 * icon:g_contextpath+'/img/icon/help.gif',
				 * cls:'x-btn-text-icon', tooltip :'��ʾ������Ϣ' }), new
				 * Ext.Toolbar.Button({ text:'�˳�', cls:'x-btn-text-icon',
				 * icon:g_contextpath+'/img/icon/rss_load.gif', tooltip :'�˳�ϵͳ',
				 * handler :function(b, pressed){ //alert('logout'); if
				 * (confirm('ȷ��Ҫ�˳�ϵͳ��?'))
				 * window.top.location.href=g_contextpath+'/logoffAction.do';} }) ] })
				 */
				]
			});

	var leftnav = new Ext.Panel({
				region : 'west',
				id : 'west-panel',
				title : '������',
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
				 * ,{ title:'��������', html:'<p>ĳЩ��Ϣ</p>',
				 * iconCls:'iconsearch', border:false } ,{ title:'�ҵ���־', html:'<p>��־��Ϣ</p>',
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
 * �򿪵�������
 */
function doOpenPupWinOnTop(src, title, width, height, initParams, parentWin) {
	if (parentWin == null) {
		parentWin = this;
	}
	doOpenPopWindow(src, title, width, height, initParams, parentWin);
}

/**
 * ���ص�������
 */
function doClosePupWinOnTop() {
	var windowId = "pupWindow";
	var pupWindow = Ext.getCmp(windowId);
	pupWindow.hide();
}

/**
 * ȡ�ö��㴰��
 */
function getTopParent() {
	return this;
}

/**
 * �򿪵�������
 */
function doOpenPupWindow(src, title, width, height, initParams, parentWin) {
	if (parentWin == null) {
		parentWin = this;
	}
	doOpenPopWindow(src, title, width, height, initParams, parentWin);
}
/**
 * �򿪵�������
 */
function doOpenPopWindow(src, title, width, height, initParams, parentWin) {
	if (parentWin == null) {
		parentWin = this;
	}
	tempParentWin = parentWin;
	var windowId = "pupWindow";
	var popWindow = Ext.getCmp(windowId);
	popWindow.setTitle(title); // ����
	if (width <= 1) {
		if (width >= 0) {// С��
			width = document.body.clientWidth * width;
		} else { // ����
			width = document.body.clientWidth + width;
		}
	}
	if (height <= 1) {
		if (height >= 0) {// С��
			height = document.body.clientHeight * height;
		} else { // ����
			height = document.body.clientHeight + height;
		}
	}
	popWindow.setSize(width, height); // ��� �߶�
	showWindowWithSrc(windowId, src, initParams, "doTopPupWinOnload(this)");
	popWindow.center(); // ����
}

var tempParentWin;

/**
 * ���ݴ���ĵ�ַ��ʾ����
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
 * ����IE��Firefox��iframe���ڻ�ȡ����
 */
function getIFrameWin(id) {
	return document.getElementById(id).contentWindow || document.frames[id];
}

/**
 * ��ʾ���ڼ�����ɴ������¼�
 */
function doTopPupWinOnload(iframe) {
	try {
		getIFrameWin(iframe.id).setParentWin(tempParentWin);
	} catch (e) {
	}
}
/**
 * ȡ�õ�½�û���
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
