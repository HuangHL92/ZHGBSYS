
var tabCount = 5; // ��ϵͳ������򿪵����tabҳ��
var commParams = {};
/** *******macaddr��һ�ε�½�Ĵ���********** */
// macAddr �����һ�ε�¼�ɹ����mac��ַ���������Ϊ��ʱ��˵���ǵ�һ�ε�¼
function isFirstLogin(macAddr) {
	if (macAddr != "" && macAddr.length > 0) {
		odin.confirm("һ�ҵ�λֻ����ʹ��һ̨�̶��ļ�������������걨��ϵͳ���ڵ�һ�ε�¼��ϵͳ�ļ����MAC��ַ�����������ַ�����а󶨣��Դ���Ϊ����걨�ļ���������걨���õļ����MAC��ַ�����������ַ����" + macAddr + "��ȷ���밴��ȷ���������򰴡�ȡ�����˳�ϵͳ", firstLoginConfirm)
	}
}

function firstLoginConfirm(btn) {
	if (btn != 'ok') {
		window.top.location.href = contextPath + '/logoffAction.do'; // �˳�
	}
}

/** *******macaddr��һ�ε�½�Ĵ��� end********** */

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
		 * , items:{ title:'��ӭ', closable:false, id:'homepage',
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
 * �����û���¼����ȡ��Ȩ�޵Ĺ����б����Ƿ��е�һ�ξʹ򿪵�ҳ�� ����У������
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
	if (menudata.length == 0) { // ���û��Ĭ�ϴ򿪵����Ĭ�ϵĻ�ӭ����
		/*
		 * tabs.add({ title: '��ӭ', closable: false, id: 'welcome', iconCls:
		 * 'iconwelcome', html: '<Iframe id="iframe_welcome"
		 * name="iframe_welcome" width="100%" height="100%" scrolling="no"
		 * frameborder="0"
		 * style="clear:both;margin-left:0px;margin-right:0px;float:right;"
		 * src="' + contextPath + '/Welcome.jsp' + '" ></Iframe>' });
		 * tabs.add({ title: '�ݸ���', closable: false, id: 'homepage', iconCls:
		 * 'iconwelcome', html: '<Iframe id="iframe_homepage"
		 * name="iframe_homepage" width="100%" height="100%" scrolling="no"
		 * frameborder="0"
		 * style="clear:both;margin-left:0px;margin-right:0px;float:right;"
		 * src="' + contextPath + '/draft.jsp' + '" ></Iframe>' });
		 */
	} else {
		var closable = false;
		var data = menudata[0];
		if (data.focanclose == '1') { // 1 ����ر�
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
					odin.info('ϵͳ�������ͬʱ��5�����ܣ����ȹر�������ʱ���õĹ��ܡ�');
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
				odin.info('ϵͳ�������ͬʱ��5�����ܣ����ȹر�������ʱ���õĹ��ܡ�');
			}
		}

	}
};
/**
 * �´�һ��tabҳ
 * 
 * @param {Object}
 *            atitle tabҳ����
 * @param {Object}
 *            aid tabҳid
 * @param {Object}
 *            src tabҳ��ַ
 */
// function addTab(atitle, aid, src) {
// /** **********��ѯҵ������*********** */
//
// var parampa = {};
// // �ж��Ƿ����¶Ƚ���
// parampa.sqlType = "SQL";
// parampa.querySQL =
// "@_sAnQMaRvSynW39bUIcNmBeshw+te+Ee+pj12zpk8VLCowt19i9kO0Q==_@";
// var reqpa = odin.commonQuery(parampa, odin.ajaxSuccessFunc, null, false,
// false);
// var datapa = odin.ext.decode(reqpa.responseText).data.data;
// if (datapa[0].a == '0' || datapa[0].a == '3') {
// odin.info('ϵͳ������ĩͳ���У����Ժ����...');
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
// /** *********��ѯģ���Ƿ񳬳����������********************* */
// var username = getLoginUsername();
// parampa.querySQL = "@_OzvAUCeqM5WB3xi4wuS88AlbUIYtZLVk_@" + param1 +
// "@_j5Deho3wJ+E=_@" + username + "@_q9Hd2cCXhSPvMzh7twPNkQ==_@";
// // parampa.querySQL = " select '0' a from dual";
// reqpa = odin.commonQuery(parampa, odin.ajaxSuccessFunc, null, false, false);
// datapa = odin.ext.decode(reqpa.responseText).data.data;
// if (datapa[0].a == '0' || param1 == '') { // /����ʱ�䷶Χ��ʾ ģ�鲻���ÿ���ע�͵����if/else
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
// odin.info('ϵͳ�������ͬʱ��5�����ܣ����ȹر�������ʱ���õĹ��ܡ�');
// }
// } else {
// /*
// * aab001 = document.getElementById('aaaa').value; ///��ȡ��λ����
// * //alert("aab001="+aab001); //���ݵ�λ��Ų鵥λ���� var params = {};
// * params.sqlType = "SQL"; params.querySQL = " select
// * aab019,aae006,aac047,aae045,aae004,aae005,aae285,aab031,aab032,aae007
// * from net_ab01 where aab001='"+aab001+"'"; var req =
// * odin.commonQuery(params,odin.ajaxSuccessFunc,null,false,false);
// * var data = odin.ext.decode(req.responseText).data.data;
// * //alert(data[0].aae285); //�жϵ�λ�Ļ�����Ϣ�Ƿ���ȫ
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
// * odin.info('ϵͳ�������ͬʱ��5�����ܣ����ȹر�������ʱ���õĹ��ܡ�'); } }else{
// * //�жϵ�λ�Ļ�����Ϣ�Ƿ���ȫ params.sqlType = "SQL"; params.querySQL = "
// * select count(aac001) as total from net_ae01 ae01 where
// * ae01.aac001='"+aab001+"' and ae01.cae004='1' and cae002='41' and
// * (ae01.cae006 = '01' or ae01.cae006 = '02' or ae01.cae006 = '11'
// * or ae01.cae006 = '21' or ae01.cae006 = '30' or ae01.cae006 = '31'
// * or ae01.cae006 = '33')"; req =
// * odin.commonQuery(params,odin.ajaxSuccessFunc,null,false,false);
// * var dataNUM = odin.ext.decode(req.responseText).data.data;
// * if(dataNUM[0].total>0){ ////�жϲݸ����Ƿ���״̬��ȷ������ var count =
// * tabs.items.getCount(); if (count < tabCount) { var tab =
// * tabs.getItem(aid); if (tab) { tabs.activate(tab); } else {
// * tabs.add({ title: (atitle), id: aid, html: '<Iframe width="100%"
// * id="iframe_'+aid+'" height="100%" scrolling="auto"
// * frameborder="0" src="' + src + '"
// *
// style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
// * closable: true }).show(); } }else{
// * odin.info('ϵͳ�������ͬʱ��5�����ܣ����ȹر�������ʱ���õĹ��ܡ�'); } }else{
// * odin.info('��Ϊ���ĵ�λ������Ϣ����ȫ�������򿪵�λ������Ϣ���ҳ������걨��'); } }
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
// odin.info('ϵͳ�������ͬʱ��5�����ܣ����ȹر�������ʱ���õĹ��ܡ�');
// }
//
// }
// } else {
// odin.info(datapa[0].a); // /����ʱ�䷶Χ��ʾ ģ�鲻���ÿ���ע�͵����if/else
// }
//
// }
Ext.onReady(function() {
			tabs.render('content_right_tab');
		});
function doLogOut() {
	odin.confirm('ȷ��Ҫ�˳�ϵͳ��', doOut);
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
 * �򿪵�������
 */
function doOpenPupWinOnTop(src, title, width, height, initParams, parentWin) {
	if (parentWin == null) {
		parentWin = this;
	}
	doOpenPopWindow(src, title, width, height, initParams, parentWin);
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
	popWindow.setSize(width, height); // ��� �߶�
	showWindowWithSrc(windowId, src, initParams, "doTopOnreadystatechange(this)");
	popWindow.center(); // ����
}

var tempParentWin;

/**
 * ���ݴ���ĵ�ַ��ʾ����
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
 * ��ʾ����״̬�ı䴥�����¼�
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
 * ȡ�õ�½�û���
 */
function getLoginUsername() {
	return document.all('currentUsername').value;
}

/** ****�޸�����******* */
function doChangePWD() {
	doOpenPopWindow("/commpages/common/changePassWord.jsp", "�����û�����", 400, 200);
	// addTab("�����û�����",'S000600',contextPath+"/switchAction.do?prefix=/sysmanager&amp;page=/user/AlterPassword.jsp");
	// addTab("�����û�����", 'S000600', contextPath +
	// "/commpages/common/changePassWord.jsp");
}

/** ****�鿴��˽��******* */
function doLookUpResult() {
	addTab("�鿴��˽��", 'lookUpResult', contextPath + "/switchAction.do?prefix=/sysmanager&amp;page=/user/AlterPassword.jsp");
}

function doHightShow(obj, width, height) {
	odin.ext.get(obj).scale(width, height, {
				easing : 'easeOut',
				duration : .5
			});
}

var draftClickedTimes = 0; // �ݸ���������
var welcomeClickedTimes = 0; // ��ӭ����������
/**
 * ��ʾ���棬�ڸ���ӭ���ݸ��䡢ҵ������ȵ�div���л�
 */
function doShowDiv(item) {
	var liId = item.parentElement.id;
	// liId.img='<img src=../img/index_05.jpg>';
	var divId = liId.substring(liId.indexOf("_") + 1);
	showDiv(divId);
}

/**
 * ��ʾ���棬�ڸ���ӭ���ݸ��䡢ҵ������ȵ�div���л�
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
		if (draftClickedTimes == 1) { // ��ʼ��
			// document.frames('iframe_draft').window.location.reload();
			document.getElementById("iframe_draft").src = "draft.jsp";
		} else { // �ݸ���ˢ��
			getFrame('iframe_draft').breakShale();
		}
	} else if (divId == "welcome") {
		welcomeClickedTimes++;
		if (welcomeClickedTimes > 0) {
			getFrame('iframe_welcome').getSbdnNoticeContent();// ��ȡ֪ͨ����
			getFrame('iframe_welcome').getCpSbdnNoticeContent();// ��ȡ֪ͨ����
		}
	}
}

function getFrame(id){
	return document.getElementById(id).contentWindow || document.frames[id];
}

function doOnreadystatechange(iframe) {
	// alert(iframe.id)
	if (iframe.readyState != 'complete') {
		Ext.getBody().mask("ҳ�������...", odin.msgCls); // ����loading��Ӱ
	} else if (iframe.readyState == 'complete') {
		Ext.getBody().unmask(); // ȥ����Ӱ
	}
}

/**
 * ȡ�õ�½�û���
 */
function getLoginUsername() {
	return document.all('currentUsername').value;
}

/**
 * �ж��Ƿ�����걨�û�
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
 * �����걨�õĲ������޸���welcome.js�������
 */
/*
 * function doJssbUserOperation(){ if(!isJssbUser()){ return; }
 * document.getElementById('helpHref').href="jssbhelp.doc";
 * //document.getElementById('helpLink').style.visibility = "true";
 * document.getElementById('li_draft').style.visibility = "hidden"; }
 */

function getSysType() {// sionline Ϊ�����걨�� insiis
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