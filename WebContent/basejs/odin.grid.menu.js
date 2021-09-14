/**
 * Odin Grid �Ҽ�ͨ�ò˵�֧��JS
 * @author jinwei
 * @date 2013.1.11
 * @type
 * @description
 * version:6.0.1 �޸��Ҽ�ͨ�ò˵���ĵ����������ݹ��ܣ����Ӹ��ݵ�ǰJSP���Ƿ��С�var div_1_checkbox_dataindex = "check";�������ĳ����ѡ���У��������ֻ����ѡ�У����򵼳�������������
 * version:6.0.2 ���������ĳ����checkbox��������Ϣ���򲻹��ǵ�����ǰҳ���ǵ���ȫ�����ݶ��Զ��������� -- jinwei 2013.12.16
 * version:6.0.3 ���ӱ��ɸ��ݿ�����Ա����ġ�gridid_exp_dataindex�����Ƶ�ȫ�ֱ�������������ĳ�������ݣ��硰var div_1_exp_dataindex = "aaa001,aaa002"; //ֻ����aaa001��aaa002�С� -- jinwei 2014.2.12
 * version:6.0.4 �ṩ�Ա���Ҽ��������Զ�ʶ�������е�֧�� -- jinwei 2014.2.14
 */
sortParam = {}; //����ȫ�ֲ��� 
filterParam = {}; //����ȫ�ֲ���
Ext.namespace("odin.grid.menu");
/**
 * ���ӵ�������
 */
function doOpenPupWin(src, title, width, height, initParams, parentWin) {
	if (parentWin == null) {
		parentWin = this;
	}
	tempParentWin = parentWin;
	var windowId = "win_pup";
	var pupWindow = Ext.getCmp(windowId);
	expReadyState = ""; // ����ʱ��ʼ��
	pupWindow.setTitle(title); // ����
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
	pupWindow.setSize(width, height); // ��� �߶�
	showWindowWithSrc(windowId, src, initParams);
	pupWindow.center(); // ����
}
/**
 * ����IE��Firefox��iframe���ڻ�ȡ����
 */
function getIFrameWin(id) {
	return document.getElementById(id).contentWindow || document.frames[id];
}
/**
 * ���ݴ���ĵ�ַ��ʾ����
 */
function showWindowWithSrc(windowId, newSrc, initParams) {
	if (newSrc.substring(0, 1) == "&") {
		newSrc = addUrlParam(window.location.href, newSrc);
	}
	if (newSrc.indexOf("http:") == -1 && newSrc.indexOf("www.") == -1 && newSrc.indexOf(contextPath) != 0) {
		newSrc = contextPath + newSrc;
	}
	if (initParams != null) {
		newSrc = addUrlParam(newSrc, "initParams=" + encodeURIComponent(initParams));
		commParams.initParams = initParams;
		commParams.initParamsUsedBy = null;
	}
	newSrc = addUrlParam(newSrc, "clientDate=" + Ext.util.Format.date(new Date(), "YmdHis"));// ʹÿ�����󶼲�һ�����������һ���򲻿�������
	// alert(newSrc);
	if (document.getElementById("iframe_" + windowId)) {
		try {
			getIFrameWin("iframe_" + windowId).odin.mask("����ˢ��...");
		} catch (e) {
		}
		document.getElementById("iframe_" + windowId).src = newSrc;
	} else {
		Ext.getCmp(windowId).html = "<iframe style=\"background:white;border:none;\" width=\"100%\" height=\"100%\" id=\"iframe_" + windowId + "\" name=\"iframe_" + windowId + (onload == null ? "" : "\" onload=\"" + onload) + "\" src=\"" + newSrc + "\"></iframe>";
	}
	Ext.getCmp(windowId).show(Ext.getCmp(windowId));
	Ext.getCmp(windowId).focus();
}
/**
 * ��url�Ӳ���
 * @param {} url ԭʼurl
 * @param {} paramUrl ����url���� &a=1&b=2
 * @return {} ����ֵ
 */
function addUrlParam(url, paramUrl) {
	var paramArray = paramUrl.split("&");
	var newUrl = url.replace("#", "");
	for (var i = 0; i < paramArray.length; i++) {
		var param = paramArray[i];
		if (param.indexOf("=") != -1) {
			var paramName = param.substring(0, param.indexOf("=")).trim();
			if (newUrl.indexOf(paramName + "=") != -1) {
				var re = eval('/(' + paramName + '=)([^&]*)/gi');
				newUrl = newUrl.replace(re, param);
			} else {
				if (newUrl.indexOf("?") != -1) {
					newUrl = newUrl + "&" + param;
				} else {
					newUrl = newUrl + "?" + param;
				}
			}
		}
	}
	return newUrl;
}
function doHiddenPupWin() {
	var windowId = "win_pup";
	var pupWindow = Ext.getCmp(windowId);
	pupWindow.hide();
}
odin.grid.menu = {
	setPageSize : function(gridId) {
		if (!Ext.getCmp(gridId)) {
			odin.error("Ҫ������grid�����ڣ�gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("���Ȳ�ѯ�����ݺ��ٽ��б�������");
			return;
		}
		var pageingToolbar = (grid.getBottomToolbar() || grid.getTopToolbar());
		if (pageingToolbar && pageingToolbar.pageSize) {
			gridIdForSeting = gridId;
			var url = contextPath + "/sys/comm/commSetGrid.jsp";
			doOpenPupWin(url, "����ÿҳ����", 300, 200);
		} else {
			odin.error("�Ƿ�ҳgrid����ʹ�ô˹��ܣ�");
			return;
		}
	},
	gridContextmenu : function(e, grid, hasAllRightMenu) {
		return;//��Ŀ����˵����Ҽ��˵�����Ҫ��
		var selectText;
		try {// IE
			selectText = document.selection.createRange().text;
		} catch (ex) { // ��IE
			selectText = document.getSelection();
		}
		if (selectText != "") { // ѡ�����ı�����Ĭ�ϵ��Ҽ��˵�
			return;
		}
		var expMenuItem = new Ext.menu.Item({
					id : 'exp',
					text : '������������',
					xtype : 'button',
					iconCls : 'user',
					handler : function() {
						odin.grid.menu.expExcelFromGrid(grid.id, null, null,
								null, true)
					}
				});
		var expAllMenuItem = new Ext.menu.Item({
					id : 'expAll',
					text : '����ȫ������',
					handler : function() {
						odin.grid.menu.expExcelFromGrid(grid.id, null, null,
								null, false)
					}
				});
		var setPageSizeMenuItem = new Ext.menu.Item({
					id : 'setPageSize',
					text : '����ÿҳ����',
					handler : function() {
						odin.grid.menu.setPageSize(grid.id)
					}
				});
		var doSortAllMenuItem = new Ext.menu.Item({
					id : 'doSortAll',
					text : 'ȫ����������',
					handler : function() {
						odin.grid.menu.doSortAll(grid.id)
					}
				});
		var doFilterAllMenuItem = new Ext.menu.Item({
					id : 'doFilterAll',
					text : 'ȫ�����ݹ���',
					handler : function() {
						odin.grid.menu.doFilterAll(grid.id)
					}
				});
		var printMenuItem = new Ext.menu.Item({
					id : 'print',
					text : '��ӡ��������',
					handler : function() {
						odin.grid.menu.doPrint(grid.id,false);
					}
				});
		var printAllMenuItem = new Ext.menu.Item({
					id : 'printAll',
					text : '��ӡȫ������',
					handler : function() {
						odin.grid.menu.doPrint(grid.id,true);
					}
				});			
		var rightMenu = new Ext.menu.Menu();

		rightMenu.addItem(expMenuItem);
		// if (isPageGridDiv(grid.id)) {
		rightMenu.addItem(expAllMenuItem);
		//rightMenu.addItem(printMenuItem);//��ӡȥ��
		if (hasAllRightMenu == "true") {
			rightMenu.addSeparator();
			rightMenu.addItem(doSortAllMenuItem);
			rightMenu.addItem(doFilterAllMenuItem);
			rightMenu.addSeparator();
			rightMenu.addItem(setPageSizeMenuItem);
			rightMenu.addSeparator();
			//rightMenu.addItem(printAllMenuItem);//��ӡȥ��
		}
		// }
		e.preventDefault();// ����Ĭ���¼�
		document.oncontextmenu = function() { // ����Ĭ���Ҽ�
			return false;
		}
		rightMenu.on('beforehide', function() { // ���Ҽ��˵��ر�ʱ����Ĭ���Ҽ�
					rightMenu.destroy();
					document.oncontextmenu = function() {
						return true;
					}
				});
		rightMenu.showAt(e.getXY());
	},
	/**
	 * ����Excel
	 * @param {} gridId
	 * @param {} fileName
	 * @param {} sheetName
	 * @param {} headNames
	 * @param {} isFromInterface
	 */
	expExcelFromGrid : function(gridId, fileName, sheetName, headNames,
			isFromInterface) {
		if (!Ext.getCmp(gridId)) {
			odin.error("Ҫ������grid�����ڣ�gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("û��Ҫ���������ݣ�");
			return;
		}
		var gridColumnModel = grid.getColumnModel();
		var colsArray = new Array();
		var dataIndex = new Array();
		var columnHeader = new Array();
		var index = 0;
		var noHeaderIndex = 0;
		var jsonType = {};  //������¼Ҫ�����е�����
		var gridCheckBoxDataIndex = window[gridId+"_checkbox_dataindex"];
		var gridExpDataIndex = window[gridId+"_exp_dataindex"];
		var gridExpDataIndexs = [];
		if(typeof gridExpDataIndex != 'undefined'){
			gridExpDataIndexs = gridExpDataIndex.split(",");
		}
		for (var i = 0; i < gridColumnModel.getColumnCount(); i++) {
			if (gridColumnModel.isHidden(i)
					|| gridColumnModel.getDataIndex(i) == "") {
				continue;
			}
			var col = odin.getGridColumn(gridId, gridColumnModel.getDataIndex(i));
			var head=col.header;
			if(head.indexOf('x-grid3-check-col-td')>0){
				continue;
			}
			if(typeof gridCheckBoxDataIndex != 'undefined'){
				if(gridCheckBoxDataIndex==gridColumnModel.getDataIndex(i)){
					continue;
				}
			}
			var isNeedExp = false;
			for(var dIndex in gridExpDataIndexs){
				var temp = gridExpDataIndexs[dIndex];
				if(temp == gridColumnModel.getDataIndex(i)){
					isNeedExp = true;
					break;
				}
			}
			if(gridExpDataIndexs.length>0 && isNeedExp==false){
				continue;
			}
			colsArray[index] = i;
			dataIndex[index] = gridColumnModel.getDataIndex(i);
			if(col.editor instanceof Ext.form.NumberField){
				jsonType[dataIndex[index]] = "number";
			}
			columnHeader[index] = gridColumnModel.getColumnHeader(i).replace(
					/<[^>]+>/g, "").replace(/,/g, "��");
			index++;
		}
		
		querySQLSim = "Radow$"+MDParam.functionid + "," + gridId;
		
		if (isFromInterface != null && isFromInterface != true
				&& odin.grid.menu.isPageGridDiv(gridId)) {// ��ҳgrid����ȫ��ҳ
			// var querySQL = eval("pageGridSql." + gridId);
			
			if (headNames == null || headNames == "" || headNames == "null") { // ��ͷ
				headNames = {};
				for (var i = 0; i < dataIndex.length; i++) {
					eval("headNames." + dataIndex[i] + "="
							+ Ext.encode(columnHeader[i]));
				}
				headNames = Ext.encode(headNames);
			}
			var decodeJson; // decodeת��
			for (var i = 0; i < dataIndex.length; i++) {
				var col = odin.getGridColumn(gridId, dataIndex[i]);
				if (col.editor) {
					var type = "";
					try{
						type = col.editor.field.getXType();
					}catch(e){
						//
					}
					if (type == "combo") {// ֻ�����������ʽ����
						var decodeStrArray = eval(dataIndex[i] + "_select");
						var decodeStr = "";
						for (var j = 0; j < decodeStrArray.length; j++) {
							decodeStr += "," + decodeStrArray[j][0] + ","
									+ decodeStrArray[j][1];
						}
						decodeStr = decodeStr.substring(1);
						if (decodeStr != "") {// ��Ϊ�ղŲ���
							// decodeStr = decodeStr.substring(3,
							// decodeStr.length -
							// 3).replace(/\"\],\[\"/g, ',').replace(/\",\"/g,
							// ',');
							if (decodeJson == null) {
								decodeJson = {};
							}
							eval("decodeJson." + dataIndex[i] + "="
									+ Ext.encode(decodeStr));
						}
					}
				}
			}
			if (decodeJson != null) {
				decodeJsonSim = Ext.encode(decodeJson);
			}
		} else { // ����ҳgrid���������Ϣ
			if (headNames == null || headNames == "" || headNames == "null") { // ��ͷ
				headNames = columnHeader.join(",");
			}
			var jsonArray = new Array(store.getCount());
			for (i = 0; i < store.getCount(); i++) {
				recode = store.getAt(i);
					var checkValue = recode.get(gridCheckBoxDataIndex);
					if((typeof checkValue=='boolean' && true == checkValue)||typeof gridCheckBoxDataIndex == 'undefined'){
						json = {};
						for (j = 0; j < colsArray.length; j++) {
							var cell = grid.getView().getCell(i, colsArray[j]);
							var value = cell.outerText || cell.textContent;
							if (value == null) {
								value = "";
							} else {
								// ����renderAlt��ɵ������ַ�
								value = value.replace(/<[^>]+>/g, "");
							}
							eval("json." + dataIndex[j] + "=" + Ext.encode(value));
						}
						jsonArray[jsonArray.length] = json;
					}
			}
			jsonDataSim = Ext.encode(jsonArray);
		}
		if (fileName == null || fileName == "" || fileName == "null") {
			fileNameSim = MDParam.title /*+ "_" + gridId.replace("div_", "����")*/
					+ "_" + Ext.util.Format.date(new Date(), "Ymd");
		} else {
			fileNameSim = fileName;
		}
		if (sheetName == null || sheetName == "" || sheetName == "null") {
			sheetNameSim = MDParam.title /*+ "_" + gridId.replace("div_", "����");*/
		} else {
			sheetNameSim = sheetName;
		}
		window.jsonTypeSim = odin.encode(jsonType);
		headNamesSim = headNames;
		doOpenPupWin(contextPath + "/sys/excel/commSimpleExpExcelWindow.jsp",
				"�����ļ�", 500, 160);
		
		//$h.openWin('dow','sys.excel.commSimpleExpExcelWindow','ѡ��ģ��',500,160,'����',contextPath);
	},
	/**
	 * �ж��Ƿ�Ϊ��ҳgrid
	 */
	isPageGridDiv : function(divId) {
		if (Ext.getCmp(divId)
				&& ((Ext.getCmp(divId).getTopToolbar() && Ext.getCmp(divId)
						.getTopToolbar().pageSize) || (Ext.getCmp(divId)
						.getBottomToolbar() && Ext.getCmp(divId)
						.getBottomToolbar().pageSize))) {
			return true;
		} else {
			return false;
		}
	},
	/**
	 * ȡ��grid��������
	 */
	getGridColumnIndex : function(gridName, fieldName) {
		if (gridName == null || !Ext.getCmp(gridName)) {
			return -1;
		}
		var gridColumnModel = Ext.getCmp(gridName).getColumnModel();
		if (!gridColumnModel) {
			return -1;
		}
		for (var j = 0; j < gridColumnModel.getColumnCount(); j++) {
			if (gridColumnModel.getDataIndex(j) == fieldName) {
				return j;
			}
		}
		return -1;
	},
	/**
	 * ����
	 * 
	 * @param {Object}
	 *            divName Ҫ�����div����
	 * @param {Object}
	 *            colsName �������������������ö��Ÿ�������"aae135,aac003",������Ĭ�Ͻ������������������
	 */
	doSortAll:function (divName, colsName) {
		if (Ext.getCmp(divName).store.baseParams.cueGridId == null) {
			odin.alert("���Ȳ�ѯ������ٽ�������");
			return;
		}
		sortParam.divName = divName;
		if (colsName == null) { // ���Ϊ�գ���ȡ���������������
			var gridColumnModel = Ext.getCmp(divName).getColumnModel();
			if (!gridColumnModel) {
				odin.alert("û�п�������У�");
				return;
			}
			for (var j = 0; j < gridColumnModel.getColumnCount(); j++) {
				var dataIndex = gridColumnModel.getDataIndex(j);
				var sortable = gridColumnModel.isSortable(j);
				if (dataIndex != "" && sortable == true) {
					if (colsName == null) {
						colsName = dataIndex;
					} else {
						colsName = colsName + "," + dataIndex;
					}
				}
			}
		}
		sortParam.colsName = colsName;
		doOpenPupWin(contextPath + "/sys/comm/commSort.jsp", "���򴰿�", 420, 300);
	},
	/**
	 * ����
	 * 
	 * @param {Object}
	 *            divName Ҫ���˵�div����
	 * @param {Object}
	 *            colsName ������˵�����������ö��Ÿ�������"aae135,aac003"
	 */
	doFilterAll:function(divName, colsName) {
		if (Ext.getCmp(divName).store.baseParams.cueGridId == null) {
			odin.alert("���Ȳ�ѯ������ٽ��й��ˣ�");
			return;
		}
		filterParam.divName = divName;
		if (colsName == null) { // ���Ϊ�գ���ȡ���������������
			var gridColumnModel = Ext.getCmp(divName).getColumnModel();
			if (!gridColumnModel) {
				odin.alert("û�пɹ��˵��У�");
				return;
			}
			for (var j = 0; j < gridColumnModel.getColumnCount(); j++) {
				var dataIndex = gridColumnModel.getDataIndex(j);
				if (dataIndex != "") {
					if (colsName == null) {
						colsName = dataIndex;
					} else {
						colsName = colsName + "," + dataIndex;
					}
				}
			}
		}
		filterParam.colsName = colsName;
		doOpenPupWin(contextPath + "/sys/comm/commFilter.jsp", "���˴���", 650, 400);
	},
	doPrint:function(gridId,isPrintAll){
		if (Ext.getCmp(gridId).store.getCount() == 0) {
			odin.alert("���û�����ݿɹ���ӡ����û�в�ѯ��");
			return;
		}
		doOpenPupWin(contextPath + "/sys/comm/commPrint.jsp?gridId="+gridId+"&isPrintAll="+isPrintAll+"&functionid="+MDParam.functionid, "����ӡ", 0.9, 0.8);
		
	}
};