
var tabCount = 5; // 本系统所允许打开的最大tab页数
var commParams = {};
/** *******macaddr第一次登陆的处理********** */
// macAddr 保存第一次登录成功后的mac地址，即这个不为空时则说明是第一次登录
function isFirstLogin(macAddr) {
	if (macAddr != "" && macAddr.length > 0) {
		odin.confirm("一家单位只允许使用一台固定的计算机进行网上申报。系统将于第一次登录本系统的计算机MAC地址（网卡物理地址）进行绑定，以此作为今后申报的计算机，您申报所用的计算机MAC地址（网卡物理地址）是" + macAddr + "，确认请按“确定”，否则按“取消”退出系统", firstLoginConfirm)
	}
}

function firstLoginConfirm(btn) {
	if (btn != 'ok') {
		window.top.location.href = contextPath + '/logoffAction.do'; // 退出
	}
}

/** *******macaddr第一次登陆的处理 end********** */

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

var tabs = new Ext.TabPanel({
	frame : true,
	minTabWidth : 30,
	tabWidth : 135,
	enableTabScroll : true,
	defaults : {
		autoScroll : false,
		autoWidth : true
	},
	plugins : new Ext.ux.TabCloseMenu(),
	width : 793,
	height : 445,
	activeItem : 0
		/*
		 * , items:{ title:'欢迎', closable:false, id:'homepage',
		 * iconCls:'iconwelcome', html:'<Iframe width="100%" height="100%"
		 * scrolling="no" frameborder="0" src="'+contextPath+'/Welcome.jsp'+'"
		 * style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>' }
		 */
	});
tabs.addListener('beforetabchange', beforeTabChange);

function beforeTabChange(tabPanel, newTab, currentTab) {
	if (currentTab != null) {
		if (newTab.getId() == 'homepage') {
			try {
				getFrame('iframe_homepage').doGetAllStatusCount();
				getFrame('iframe_homepage').doLoadGridData(null, 1);
			} catch (e) {
			}
		}
	}
}

/**
 * 根据用户登录名获取其权限的功能列表里是否有第一次就打开的页面 如果有，则将其打开
 * 
 * @auther jinwei
 */
/** ***********start************** */
function getFirstOpenInfo() {
	var userid = document.all('userid').value;
	var params = {};
	params.querySQL = "@_KdG6Wp3N1wc7DeRssaYWh2Y+e3SWp00XdAMw+VWTZEUdMVIma2S2o0uUa1+XiJK0sZp/2CLR93C17E5TK2RwRW1NM5lFqYdveW152/MZy3Q=_@";
	params.querySQL += "@_4FdrFheNKReuivaO1L0vPg==_@" + userid + "@_undKoAyfqrQ0LeHHCsWIgwgUYsydZ2x1h2GMbplNpJQ=_@";
	params.querySQL += "@_SzF6DC9T9zgl2zZIVg/+uXFDTUCJh6TrJ7JDAwy8PYM+w9x0XdMb4cKzfZXfDGfD1lKUQ8G0sLIBXQTuc2M6EWuEPon5fk7x3zz17pwOD1eowt19i9kO0Q==_@";
	params.sqlType = "SQL";
	var req = odin.commonQuery(params, odin.ajaxSuccessFunc, null, false, false);
	var menudata = odin.ext.decode(req.responseText).data.data;
	if (menudata.length == 0) { // 如果没有默认打开的则打开默认的欢迎界面
		/*
		 * tabs.add({ title: '欢迎', closable: false, id: 'welcome', iconCls:
		 * 'iconwelcome', html: '<Iframe id="iframe_welcome"
		 * name="iframe_welcome" width="100%" height="100%" scrolling="no"
		 * frameborder="0"
		 * style="clear:both;margin-left:0px;margin-right:0px;float:right;"
		 * src="' + contextPath + '/Welcome.jsp' + '" ></Iframe>' });
		 * tabs.add({ title: '草稿箱', closable: false, id: 'homepage', iconCls:
		 * 'iconwelcome', html: '<Iframe id="iframe_homepage"
		 * name="iframe_homepage" width="100%" height="100%" scrolling="no"
		 * frameborder="0"
		 * style="clear:both;margin-left:0px;margin-right:0px;float:right;"
		 * src="' + contextPath + '/draft.jsp' + '" ></Iframe>' });
		 */
	} else {
		var closable = false;
		var data = menudata[0];
		if (data.focanclose == '1') { // 1 允许关闭
			closable = true;
		}
		tabs.add({
					title : data.title,
					closable : closable,
					id : data.functionid,
					iconCls : 'iconwelcome',
					html : '<Iframe width="100%" height="100%" id="iframe_' + data.functionid + '" scrolling="no" style="clear:both;margin-left:0px;margin-right:0px;float:right;" frameborder="0"  src="' + contextPath + data.location + '" ></Iframe>'
				});
	}
}

/** ***********end************** */

function loadPage(treenode, url) {
	// alert(url);
	// tabs.
	// addTab(treenode.text,treenode.id,contextPath+url);
}

function addTab(atitle, aid, src, forced) {
	var tab = tabs.getItem(aid);
	if (forced)
		aid = 'R' + (Math.random() * Math.random() * 100000000);
	if (tab && !forced) {
		tabs.activate(tab);
	} else {
		if (getSysType() == 'sionline') {
			var parampa = {};
			parampa.sqlType = "SQL";
		    parampa.querySQL = "@_c/foz3mA3/+opzgJsj4Vvaz1dEvGKal0yQFK99Rz0E3XmEI+BGK6w9ZSlEPBtLCyMp8EktVe5FE=_@" + aid + "@_mMxQSO+LfFw=_@";
     	    var reqpa = odin.commonQuery(parampa, odin.ajaxSuccessFunc, null, false, false);
     	    var datapa = odin.ext.decode(reqpa.responseText).data.data;
		    var param1 = datapa[0].ywlx;
		    var username = getLoginUsername();
		    parampa.querySQL = "@_OzvAUCeqM5WB3xi4wuS88AlbUIYtZLVk_@"+param1+"@_j5Deho3wJ+E=_@"+username+"@_q9Hd2cCXhSPvMzh7twPNkQ==_@";
    		reqpa = odin.commonQuery(parampa, odin.ajaxSuccessFunc, null, false, false);
    		datapa = odin.ext.decode(reqpa.responseText).data.data;
    		if (datapa[0].a == '0' || param1 == '') {
    			var count = tabs.items.getCount();
				if (count < tabCount) {
					tabs.add({
								title : (atitle),
								id : aid,
								html : '<Iframe width="100%" height="100%" scrolling="auto" frameborder="0" src="' + src + '" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
								closable : true
							}).show();
				} else {
					odin.info('系统最多允许同时打开5个功能，请先关闭其他暂时不用的功能。');
				}
    		}else{
    			odin.info(datapa[0].a);
    		}
		}else{
		    var count = tabs.items.getCount();
			if (count < tabCount) {
				tabs.add({
							title : (atitle),
							id : aid,
							html : '<Iframe width="100%" height="100%" scrolling="auto" frameborder="0" src="' + src + '" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
							closable : true
						}).show();
			} else {
				odin.info('系统最多允许同时打开5个功能，请先关闭其他暂时不用的功能。');
			}
		}

	}
};
/**
 * 新打开一个tab页
 * 
 * @param {Object}
 *            atitle tab页标题
 * @param {Object}
 *            aid tab页id
 * @param {Object}
 *            src tab页地址
 */
// function addTab(atitle, aid, src) {
// /** **********查询业务类型*********** */
//
// var parampa = {};
// // 判断是否在月度结算
// parampa.sqlType = "SQL";
// parampa.querySQL =
// "@_sAnQMaRvSynW39bUIcNmBeshw+te+Ee+pj12zpk8VLCowt19i9kO0Q==_@";
// var reqpa = odin.commonQuery(parampa, odin.ajaxSuccessFunc, null, false,
// false);
// var datapa = odin.ext.decode(reqpa.responseText).data.data;
// if (datapa[0].a == '0' || datapa[0].a == '3') {
// odin.info('系统正在月末统算中，请稍候操作...');
// return;
// }
//
// parampa.sqlType = "SQL";
// parampa.querySQL =
// "@_OzvAUCeqM5Xz4xjzxzWXct5aeOW0kfW2RUUOTijiOJw8sUsh32DA0wswMB7JfKAXOCf8fV3rlRU=_@"
// + aid + "@_mMxQSO+LfFw=_@";
// var reqpa = odin.commonQuery(parampa, odin.ajaxSuccessFunc, null, false,
// false);
// var datapa = odin.ext.decode(reqpa.responseText).data.data;
//
// var param1 = datapa[0].param1;
// /** *********查询模块是否超出办理的日期********************* */
// var username = getLoginUsername();
// parampa.querySQL = "@_OzvAUCeqM5WB3xi4wuS88AlbUIYtZLVk_@" + param1 +
// "@_j5Deho3wJ+E=_@" + username + "@_q9Hd2cCXhSPvMzh7twPNkQ==_@";
// // parampa.querySQL = " select '0' a from dual";
// reqpa = odin.commonQuery(parampa, odin.ajaxSuccessFunc, null, false, false);
// datapa = odin.ext.decode(reqpa.responseText).data.data;
// if (datapa[0].a == '0' || param1 == '') { // /超出时间范围提示 模块不能用可以注释点这个if/else
// if (aid == '4028c71b196fba8f0119700353480009') {
// var count = tabs.items.getCount();
// if (count < tabCount) {
// var tab = tabs.getItem(aid);
// if (tab) {
// tabs.activate(tab);
// } else {
// tabs.add({
// title : (atitle),
// id : aid,
// html : '<Iframe width="100%" height="100%" id="iframe_' + aid + '"
// scrolling="auto" frameborder="0" src="' + src + '"
// style="clear:both;margin-left:0px;margin-right:0px;float:right;" ></Iframe>',
// closable : true
// }).show();
// }
// } else {
// odin.info('系统最多允许同时打开5个功能，请先关闭其他暂时不用的功能。');
// }
// } else {
// /*
// * aab001 = document.getElementById('aaaa').value; ///获取单位内码
// * //alert("aab001="+aab001); //根据单位编号查单位内码 var params = {};
// * params.sqlType = "SQL"; params.querySQL = " select
// * aab019,aae006,aac047,aae045,aae004,aae005,aae285,aab031,aab032,aae007
// * from net_ab01 where aab001='"+aab001+"'"; var req =
// * odin.commonQuery(params,odin.ajaxSuccessFunc,null,false,false);
// * var data = odin.ext.decode(req.responseText).data.data;
// * //alert(data[0].aae285); //判断单位的基本信息是否完全
// * if((data[0].aab019!=null&&data[0].aab019!="")&&
// * (data[0].aae006!=null&&data[0].aae006!="")&&
// * (data[0].aac047!=null&&data[0].aac047!="")&&
// * (data[0].aae045!=null&&data[0].aae045!="")&&
// * (data[0].aae004!=null&&data[0].aae004!="")&&
// * (data[0].aae005!=null&&data[0].aae005!="")&&
// * (data[0].aae285!=null&&data[0].aae285!="")&&
// * (data[0].aab031!=null&&data[0].aab031!="")&&
// * (data[0].aab032!=null&&data[0].aab032!="")&&
// * (data[0].aae007!=null&&data[0].aae007!="")){ var count =
// * tabs.items.getCount(); if (count < tabCount) { var tab =
// * tabs.getItem(aid); if (tab) { tabs.activate(tab); } else {
// * tabs.add({ title: (atitle), id: aid, html: '<Iframe width="100%"
// * id="iframe_'+aid+'" height="100%" scrolling="auto"
// * frameborder="0" src="' + src + '"
// *
// style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
// * closable: true }).show(); } }else{
// * odin.info('系统最多允许同时打开5个功能，请先关闭其他暂时不用的功能。'); } }else{
// * //判断单位的基本信息是否完全 params.sqlType = "SQL"; params.querySQL = "
// * select count(aac001) as total from net_ae01 ae01 where
// * ae01.aac001='"+aab001+"' and ae01.cae004='1' and cae002='41' and
// * (ae01.cae006 = '01' or ae01.cae006 = '02' or ae01.cae006 = '11'
// * or ae01.cae006 = '21' or ae01.cae006 = '30' or ae01.cae006 = '31'
// * or ae01.cae006 = '33')"; req =
// * odin.commonQuery(params,odin.ajaxSuccessFunc,null,false,false);
// * var dataNUM = odin.ext.decode(req.responseText).data.data;
// * if(dataNUM[0].total>0){ ////判断草稿箱是否有状态正确的数据 var count =
// * tabs.items.getCount(); if (count < tabCount) { var tab =
// * tabs.getItem(aid); if (tab) { tabs.activate(tab); } else {
// * tabs.add({ title: (atitle), id: aid, html: '<Iframe width="100%"
// * id="iframe_'+aid+'" height="100%" scrolling="auto"
// * frameborder="0" src="' + src + '"
// *
// style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
// * closable: true }).show(); } }else{
// * odin.info('系统最多允许同时打开5个功能，请先关闭其他暂时不用的功能。'); } }else{
// * odin.info('因为您的单位基本信息不完全，请您打开单位基本信息变更页面进行申报！'); } }
// */
// var count = tabs.items.getCount();
// var tab = tabs.getItem(aid);
// if (tab) {
// tabs.activate(tab);
// } else if (count < tabCount) {
// tabs.add({
// title : (atitle),
// id : aid,
// html : '<Iframe width="100%" id="iframe_' + aid + '" height="100%"
// scrolling="auto" frameborder="0" src="' + src + '"
// style="clear:both;margin-left:0px;margin-right:0px;float:right;" ></Iframe>',
// closable : true
// }).show();
//
// } else {
// odin.info('系统最多允许同时打开5个功能，请先关闭其他暂时不用的功能。');
// }
//
// }
// } else {
// odin.info(datapa[0].a); // /超出时间范围提示 模块不能用可以注释点这个if/else
// }
//
// }
Ext.onReady(function() {
			tabs.render('content_right_tab');
		});
function doLogOut() {
	odin.confirm('确定要退出系统吗？', doOut);
}

function doOut(btn) {
	if (btn == "ok") {
		window.top.location.href = contextPath + '/logoffAction.do';
	}
}

function openUrl(title, id, src) {
	if (src == null || src == "") {

	} else if (src != null) {

		addTab(title, id, contextPath + "/" + src);
	}
}

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
	popWindow.setSize(width, height); // 宽度 高度
	showWindowWithSrc(windowId, src, initParams, "doTopOnreadystatechange(this)");
	popWindow.center(); // 居中
}

var tempParentWin;

/**
 * 根据传入的地址显示窗口
 */
function showWindowWithSrc(windowId, newSrc, initParams, onreadystatechange) {
	if (newSrc.indexOf("http:") == -1 && newSrc.indexOf("www.") == -1 && newSrc.indexOf(contextPath) != 0) {
		newSrc = contextPath + newSrc;
	}
	if (document.getElementById("iframe_" + windowId)) {
		document.getElementById("iframe_" + windowId).src = newSrc;
	} else {
		Ext.getCmp(windowId).html = "<iframe width=\"100%\" height=\"100%\" id=\"iframe_" + windowId + "\" name=\"iframe_" + windowId + (onreadystatechange == null ? "" : "\" onreadystatechange=\"" + onreadystatechange) + "\" src=\"" + newSrc + "\">";
	}
	if (initParams) {
		commParams.initParams = initParams;
		commParams.initParamsUsedBy = null;
	}
	Ext.getCmp(windowId).show(Ext.getCmp(windowId));
	Ext.getCmp(windowId).focus();
}

/**
 * 显示窗口状态改变触发的事件
 */
function doTopOnreadystatechange(iframe) {
	if (iframe.readyState == 'complete') {
		try {
			getFrame(iframe.id).setParentWin(tempParentWin);
		} catch (e) {
		}
	}
}
/**
 * 取得登陆用户名
 */
function getLoginUsername() {
	return document.all('currentUsername').value;
}

/** ****修改密码******* */
function doChangePWD() {
	doOpenPopWindow("/commpages/common/changePassWord.jsp", "更改用户密码", 400, 200);
	// addTab("更改用户密码",'S000600',contextPath+"/switchAction.do?prefix=/sysmanager&amp;page=/user/AlterPassword.jsp");
	// addTab("更改用户密码", 'S000600', contextPath +
	// "/commpages/common/changePassWord.jsp");
}

/** ****查看审核结果******* */
function doLookUpResult() {
	addTab("查看审核结果", 'lookUpResult', contextPath + "/switchAction.do?prefix=/sysmanager&amp;page=/user/AlterPassword.jsp");
}

function doHightShow(obj, width, height) {
	odin.ext.get(obj).scale(width, height, {
				easing : 'easeOut',
				duration : .5
			});
}

var draftClickedTimes = 0; // 草稿箱点击次数
var welcomeClickedTimes = 0; // 欢迎界面点击次数
/**
 * 显示界面，在各欢迎、草稿箱、业务操作等的div间切换
 */
function doShowDiv(item) {
	var liId = item.parentElement.id;
	// liId.img='<img src=../img/index_05.jpg>';
	var divId = liId.substring(liId.indexOf("_") + 1);
	showDiv(divId);
}

/**
 * 显示界面，在各欢迎、草稿箱、业务操作等的div间切换
 */
function showDiv(divId) {
	var divArray = new Array("welcome", "draft", "content");
	for (i = 0; i < divArray.length; i++) {
		if (divId == divArray[i]) {
			document.getElementById(divArray[i]).style.display = "";
			document.getElementById("li_" + divArray[i]).className = "hover";
		} else {
			document.getElementById(divArray[i]).style.display = "none";
			document.getElementById("li_" + divArray[i]).className = "";
		}
	}
	if (divId == "draft") {
		draftClickedTimes++;
		if (draftClickedTimes == 1) { // 初始化
			// document.frames('iframe_draft').window.location.reload();
			document.getElementById("iframe_draft").src = "draft.jsp";
		} else { // 草稿箱刷新
			getFrame('iframe_draft').breakShale();
		}
	} else if (divId == "welcome") {
		welcomeClickedTimes++;
		if (welcomeClickedTimes > 0) {
			getFrame('iframe_welcome').getSbdnNoticeContent();// 获取通知数据
			getFrame('iframe_welcome').getCpSbdnNoticeContent();// 获取通知数据
		}
	}
}

function getFrame(id){
	return document.getElementById(id).contentWindow || document.frames[id];
}

function doOnreadystatechange(iframe) {
	// alert(iframe.id)
	if (iframe.readyState != 'complete') {
		Ext.getBody().mask("页面加载中...", odin.msgCls); // 加上loading阴影
	} else if (iframe.readyState == 'complete') {
		Ext.getBody().unmask(); // 去掉阴影
	}
}

/**
 * 取得登陆用户名
 */
function getLoginUsername() {
	return document.all('currentUsername').value;
}

/**
 * 判断是否基数申报用户
 */
function isJssbUser() {
	var currentRoleFlag = document.all('currentRoleFlag').value;
	if (currentRoleFlag == "2") {
		return true;
	} else {
		return false;
	}
}
function isGrzhUser(){
    var currentRoleFlag = document.all('currentRoleFlag').value;
	if(currentRoleFlag=="3"){
		return true;
	}else{
		return false;
	}
}
/**
 * 基数申报用的操作，修改在welcome.js里面操作
 */
/*
 * function doJssbUserOperation(){ if(!isJssbUser()){ return; }
 * document.getElementById('helpHref').href="jssbhelp.doc";
 * //document.getElementById('helpLink').style.visibility = "true";
 * document.getElementById('li_draft').style.visibility = "hidden"; }
 */

function getSysType() {// sionline 为网上申报， insiis
	return document.all('systype').value;
}

function getCurrentAab301() {
	return document.all('currentAab301').value;
}

function getCurrentAaa027() {
	return document.all('currentAaa027').value;
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