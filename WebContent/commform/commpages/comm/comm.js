/**
 * @commParams ��������
 * @commParams.currentDiv; //��ǰdiv��
 * @commParams.currentEvent; //��ǰ�¼���
 * @commParams.currentRow; //��ǰ�к�
 * @commParams.currentColumn; //��ǰ����
 * @commParams.currentOriginalValue; //��ǰ��Ŀ�޸�ǰ��ֵ
 * @commParams.currentValue; //��ǰ��Ŀ�޸ĺ��ֵ
 * @commparams.currentAaz001; //��ǰ��½�ĵ�λ����aaz001
 * @commparams.currentOpseno; //��ǰ�ݸ����opseno
 * @commParams.initParams; //��ʼ���������飬�����ڵ���ʱ���Զ��尴ť���ʱ���У�ʹ�÷�ʽΪcommParams.initParams[0]��
 * @commParams.currentFilePath; //��ǰ�ļ�·�����ڵ����ʱ����ֵ
 * @commParams.currentLoginName; //��ǰ��½�û������ں�̨����У�飬����ʵ��ʹ��
 *  * getSysType��������ֵ�̶�insiis  20130821 ljd
 */
var commParams = {};
var tempOriginalValue = ""; // ԭʼֵ
var listeners = {}; // �¼�����json
var tagNames = new Array("input", "textarea"); // Ҫȡ���ݵ�tagName
var inParams = {}; // ����Ĳ���
var reflushAfterSave = true; // ����֮����Զ�ˢ��
var reflushAfterSaveMsg = "";// ��������Ϣ
var reflushAfterSavePrint = "";// ������ӡ����ʾ
var divsql = {}; // ����div��sql
/** ****�򵥵���excel����********** */
var fileNameSim = "";
var sheetNameSim = "";
var querySQLSim = "";
var headNamesSim = "";
var separatorSim = "";
var downFileUrl = "";
var sortParam = {}; // ����Ĳ���
var filterParam = {};// ���˵Ĳ���
var isQuery = false; // �Ƿ�Ϊquery�Ĳ���
var currentActionDisabled;
var currentOpseno;
var currentLoginName;
var MDParam;
var tree;
var pagePaneOpstr = "";
var parentWin = parent;
var pageGridSql = {}; // ��ҳgrid�Ĳ�ѯsql
var isQuerying = false; // �Ƿ����ڽ���psquery�ȱ�ǩ�Ĳ�ѯ
var tempParentWin;
var psqueryBuffer = "";
var cpqueryBuffer = "";
var psidqueryBuffer = "";
var fromCommLink = false; // �Ƿ����Թ���������
var fix = false;// �ļ���չ��
/**
 * ����sql����excel�ĵ���
 */
function expExcel(querySQL, fileName, sheetName, headNames, withoutHead, decodeJson) {
	if (fileName == null || fileName == "" || fileName == "null") {
		fileNameSim = MDParam.title + Ext.util.Format.date(odin.getSysdate(), "Ymd");
	} else {
		fileNameSim = fileName;
	}
	if (sheetName == null || sheetName == "" || sheetName == "null") {
		sheetNameSim = MDParam.title;
	} else {
		sheetNameSim = sheetName;
	}
	if (withoutHead == true || withoutHead == "true") {
		withoutHeadSim = true;
	} else {
		withoutHeadSim = false;
	}
	querySQLSim = querySQL;
	headNamesSim = headNames;
	decodeJsonSim = decodeJson;
	doOpenPupWin(contextPath + "/commform/sys/excel/commSimpleExpExcelWindow.jsp", "�����ļ�", 500, 160);
}

/**
 * ����grid����excel�ĵ���
 */
function expExcelFromGrid(gridId, fileName, sheetName, headNames, isFromInterface) {
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
	for (var i = 0; i < gridColumnModel.getColumnCount(); i++) {
		if (gridColumnModel.isHidden(i) || gridColumnModel.getDataIndex(i) == "") {
			continue;
		}
		colsArray[index] = i;
		dataIndex[index] = gridColumnModel.getDataIndex(i);
		var col = odin.getGridColumn(gridId, gridColumnModel.getDataIndex(i));
		if(col.editor.field.getXType() == "numberfield"){
			jsonType[dataIndex[index]] = "number";
		}
		columnHeader[index] = gridColumnModel.getColumnHeader(i).replace(/<[^>]+>/g, "").replace(/,/g, "��");
		index++;
	}
	if (isFromInterface != null && isFromInterface != true && isPageGridDiv(gridId)) {// ��ҳgrid����ȫ��ҳ
		var querySQL = eval("pageGridSql." + gridId);
		if (querySQL == null) {
			odin.error("ֻ�в�ѯ�������ݲ��ܵ�����");
			return;
		}
		querySQLSim = querySQL;
		if (headNames == null || headNames == "" || headNames == "null") { // ��ͷ
			headNames = {};
			for (var i = 0; i < dataIndex.length; i++) {
				eval("headNames." + dataIndex[i] + "=" + Ext.encode(columnHeader[i]));
			}
			headNames = Ext.encode(headNames);
		}
		var decodeJson; // decodeת��
		for (var i = 0; i < dataIndex.length; i++) {
			var col = getGridColumn(gridId, dataIndex[i]);
			if (col.editor) {
				var type = col.editor.field.getXType();
				if (type == "combo") {// ֻ�����������ʽ����
					var decodeStrArray = eval(dataIndex[i] + "_select");
					var decodeStr = "";
					for (var j = 0; j < decodeStrArray.length; j++) {
						decodeStr += "," + decodeStrArray[j][0] + "," + decodeStrArray[j][1];
					}
					decodeStr = decodeStr.substring(1);
					if (decodeStr != "") {// ��Ϊ�ղŲ���
						// decodeStr = decodeStr.substring(3, decodeStr.length -
						// 3).replace(/\"\],\[\"/g, ',').replace(/\",\"/g, ',');
						if (decodeJson == null) {
							decodeJson = {};
						}
						eval("decodeJson." + dataIndex[i] + "=" + Ext.encode(decodeStr));
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
			jsonArray[i] = json;
		}
		jsonDataSim = Ext.encode(jsonArray);
	}
	if (fileName == null || fileName == "" || fileName == "null") {
		fileNameSim = MDParam.title + "_" + gridId.replace("div_", "����") + "_" + Ext.util.Format.date(odin.getSysdate(), "Ymd");
	} else {
		fileNameSim = fileName;
	}
	if (sheetName == null || sheetName == "" || sheetName == "null") {
		sheetNameSim = MDParam.title + "_" + gridId.replace("div_", "����");
	} else {
		sheetNameSim = sheetName;
	}
	headNamesSim = headNames;
	window.jsonTypeSim = odin.encode(jsonType);
	doOpenPupWin(contextPath + "/commform/sys/excel/commSimpleExpExcelWindow.jsp", "�����ļ�", 500, 160);
}

/**
 * ����grid����excel�ĵ���
 */
function expExcelFromGridByRep(gridId, fileName, headNames) {
	var dealType = "exp";
	dealGridByRep(gridId, fileName, headNames, dealType);
}

/**
 * ����grid���д�ӡ
 */
function printGridByRep(gridId, headNames) {
	var dealType = "query";
	dealGridByRep(gridId, null, headNames, dealType);
}

/**
 * ����grid���д���ɱ����ʽ���ɴ�ӡ�������Ȳ���
 */
function dealGridByRep(gridId, fileName, headNames, dealType) {
	var dealName = (dealType == "exp" ? "����" : "��ӡ");
	if (!Ext.getCmp(gridId)) {
		odin.error("Ҫ" + dealName + "��grid�����ڣ�gridId=" + gridId);
		return;
	}
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	if (store.getCount() == 0) {
		odin.error("û��Ҫ" + dealName + "�����ݣ�");
		return;
	}
	if (fileName == null || fileName == "" || fileName == "null") {
		fileName = MDParam.title + "_" + gridId.replace("div_", "����") + "_" + Ext.util.Format.date(odin.getSysdate(), "Ymd");
	}
	var gridColumnModel = grid.getColumnModel();
	var colsArray = new Array();
	var columnHeader = new Array();
	var index = 0;
	for (var i = 0; i < gridColumnModel.getColumnCount(); i++) {
		if (gridColumnModel.isHidden(i) || gridColumnModel.getDataIndex(i) == "") {
			continue;
		}
		colsArray[index] = i;
		var header = gridColumnModel.getColumnHeader(i).replace(/<[^>]+>/g, "").replace(/,/g, "��");
		columnHeader[index] = header;
		index++;
	}
	var colsname = columnHeader.join(",");
	if (headNames != null) {
		colsname = headNames;
	}
	var cols = "C" + colsArray.join(",C");
	var sql = "select '' C" + colsArray.join(",'' C") + " from dual where rownum=0";
	sql = sql + " union all select '" + columnHeader.join("','") + "' from dual";
	for (i = 0; i < store.getCount(); i++) {
		recode = store.getAt(i);
		var valuesArray = new Array(colsArray.length);
		for (j = 0; j < colsArray.length; j++) {
			var cell = grid.getView().getCell(i, colsArray[j]);
			var value = cell.outerText || cell.textContent;
			if (value == null) {
				value = "";
			} else {
				// ����renderAlt��ɵ������ַ�
				value = value.replace(/<[^>]+>/g, "");
			}
			valuesArray[j] = value;
		}
		sql = sql + " union all select '" + valuesArray.join("','") + "' from dual";
	}
	sql = cjkDecode(sql);
	var url = "/pages/commRep2Action.do?method=sys.dynrep";
	url = url + "&" + dealType + "=true";
	url = url + "&fr_filename=" + fileName;
	url = url + "&sql=" + sql;
	url = url + "&cols=" + cols;
	url = url + "&colsname=" + colsname;
	if (dealType == "exp") {
		doOpenPupWinHidden(url);
	} else {
		doOpenPupWin(url, "��ӡԤ��--" + fileName, 0.8, 0.8);
	}
}

/**
 * ����sql����text�ĵ���
 */
function expText(querySQL, fileName, separator, headNames, withoutHead) {
	if (fileName == null || fileName == "" || fileName == "null") {
		fileNameSim = MDParam.title + Ext.util.Format.date(odin.getSysdate(), "Ymd") + ".txt";
	} else if (fileName.indexOf(".") == 0) {// ��.��ͷ����ָֻ���˺�׺
		fileNameSim = MDParam.title + Ext.util.Format.date(odin.getSysdate(), "Ymd") + fileName;
	} else {
		fileNameSim = fileName;
	}
	if (separator == null || separator == "" || separator == "null") {
		separatorSim = "";
	} else {
		separatorSim = separator;
	}
	if (withoutHead == true || withoutHead == "true") {
		withoutHeadSim = true;
	} else {
		withoutHeadSim = false;
	}
	querySQLSim = querySQL;
	headNamesSim = headNames;
	doOpenPupWin(contextPath + "/commform/sys/text/commSimpleExpTextWindow.jsp", "�����ļ�", 500, 160);
}
/**
 * ����sql����dbf�ĵ���
 */
function expDbf(querySQL, fileName, headNames) {
	if (fileName == null || fileName == "" || fileName == "null") {
		fileNameSim = MDParam.title + Ext.util.Format.date(odin.getSysdate(), "Ymd") + ".dbf";
	} else if (fileName.indexOf(".") == 0) {// ��.��ͷ����ָֻ���˺�׺
		fileNameSim = MDParam.title + Ext.util.Format.date(odin.getSysdate(), "Ymd") + fileName;
	} else {
		fileNameSim = fileName;
	}
	querySQLSim = querySQL;
	headNamesSim = headNames;
	doOpenPupWin(contextPath + "/commform/sys/dbf/commSimpleExpDbfWindow.jsp", "�����ļ�", 500, 160);
}

/**
 * �����ļ�
 * 
 * fileUrl ���������·������/help.doc
 * Ҳ�����Ǿ���·��,��http://60.190.57.183:8080/sionline/help.doc
 */
function downFile(fileUrl) {
	downFileUrl = fileUrl;
	doOpenPupWin(contextPath + "/commform/sys/excel/commDownFileWindow.jsp", "�����ļ�", 500, 160);
}

/**
 * �ҵ���ָ���ַ�����ͷ����Ϣ������ret.divName��ret.colName
 */
function findColumnStartWith(startStr) {
	var divList = document.commForm.getElementsByTagName("div");
	for (var i = 0; i < divList.length; i++) {
		divId = divList.item(i).id;
		if ((divId.indexOf("div_") == 0) && (document.getElementById("gridDiv_" + divId) == null)) { // div_��ͷ�Ҳ���grid
			for (var n = 0; n < tagNames.length; n++) {
				var inputList = divList.item(i).getElementsByTagName(tagNames[n]);
				for (var j = 0; j < inputList.length; j++) {
					if (inputList.item(j).name && inputList.item(j).name.indexOf("-") == -1 && inputList.item(j).name.indexOf("_combo") == -1) {
						var inputName = inputList.item(j).name;
						if (inputName.indexOf(startStr) != -1) {
							var ret = {};
							ret.divName = divId;
							ret.colName = inputName;
							return ret;
						}
					}
				}
			}
		}
	}
	return {};
}

/**
 * ���֤����
 * 
 * fileUrl ���������·������/help.doc
 * Ҳ�����Ǿ���·��,��http://60.190.57.183:8080/sionline/help.doc
 */
function doReadCard() {
	var ret = findColumnStartWith("psquery");
	var divName = ret.divName;
	var colName = ret.colName;
	if (divName == null) {
		divName = "div_1";
	}
	var param = new Array(2);
	param[0] = divName;
	param[1] = colName;
	commParams = {};
	commParams.currentDiv = divName;
	commParams.currentColumn = colName;
	doOpenPupWinHidden(contextPath + "/commform/commpages/comm/readcard.jsp", param);
	odin.mask("���ڶ���...");
}

/**
 * grid�Ҽ��˵�
 */
function gridContextmenu(e, grid, hasAllRightMenu) {
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
					expExcelFromGrid(grid.id, null, null, null, true)
				}
			});
	var expAllMenuItem = new Ext.menu.Item({
				id : 'expAll',
				text : '����ȫ������',
				handler : function() {
					expExcelFromGrid(grid.id, null, null, null, false)
				}
			});
	var setPageSizeMenuItem = new Ext.menu.Item({
				id : 'setPageSize',
				text : '����ÿҳ����',
				handler : function() {
					setPageSize(grid.id)
				}
			});
	var doSortAllMenuItem = new Ext.menu.Item({
				id : 'doSortAll',
				text : 'ȫ����������',
				handler : function() {
					doSortAll(grid.id)
				}
			});
	var doFilterAllMenuItem = new Ext.menu.Item({
				id : 'doFilterAll',
				text : 'ȫ�����ݹ���',
				handler : function() {
					doFilterAll(grid.id)
				}
			});
	var rightMenu = new Ext.menu.Menu();
	rightMenu.addItem(expMenuItem);
	if (isPageGridDiv(grid.id)) {
		rightMenu.addItem(expAllMenuItem);
		if (hasAllRightMenu == "true") {
			rightMenu.addSeparator();
			rightMenu.addItem(doSortAllMenuItem);
			rightMenu.addItem(doFilterAllMenuItem);
			rightMenu.addSeparator();
			rightMenu.addItem(setPageSizeMenuItem);
		}
	}
	e.preventDefault();// ����Ĭ���¼�
	document.oncontextmenu = function() { // ����Ĭ���Ҽ�
		return false;
	}
	rightMenu.on('beforehide', function() { // ���Ҽ��˵��ر�ʱ����Ĭ���Ҽ�
				document.oncontextmenu = function() {
					return true;
				}
			});
	rightMenu.showAt(e.getXY());
}

var gridIdForSeting;// Ҫ���õ�grid��id
/**
 * ÿҳ��������
 */
function setPageSize(gridId) {
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
		var url = contextPath + "/commform/commpages/comm/commSetGrid.jsp";
		doOpenPupWin(url, "����ÿҳ����", 300, 200);
	} else {
		odin.error("�Ƿ�ҳgrid����ʹ�ô˹��ܣ�");
		return;
	}
}

/**
 * �����ʼ���¼�
 */
function doInit() {
	var initParams = commParams.initParams;
	commParams = {};
	commParams.currentEvent = "init";
	if (parent && parent.commParams && parent.commParams.initParams) {
		if (parent.commParams.initParamsUsedBy == null || parent.commParams.initParamsUsedBy =='') {
			parent.commParams.initParamsUsedBy = window.name;
		}
		if (parent.commParams.initParamsUsedBy == window.name) {
			commParams.initParams = parent.commParams.initParams;
			if (parent.commParams.initParamsUsedOnce) { // һ���Ե���Ҫ���
				parent.commParams.initParams = null;
				parent.commParams.initParamsUsedBy = null;
				parent.commParams.initParamsUsedOnce = null;
			}
		}
	}
	if(currentActionDisabled){
		if("true" == currentActionDisabled ||currentActionDisabled==true){			
			if(odin.ext.getCmp('doSave')){
				odin.ext.getCmp('doSave').disable();
			}
		}
	}
	
	if (initParams != null) {
		commParams.initParams = initParams;
	}
	doSubmit(document.commForm, doSuccess, doFailure);
}

/**
 * ��ѯ��ť���¼�
 */
function doQuery() {
	if (repurl != "" && getRepType() == "1") { // ���Ŵ�ӡ�������⴦��
		doRepQuery();
	} else {
		commParams = {};
		commParams.currentEvent = "query";
		doSubmit(document.commForm, doSuccess, doFailure);
	}
}

/**
 * ����Զ��尴ť
 * 
 * @param {}
 *            btn ��ť����
 */
function doClick(btn, clickParams, currentRow) {
	if (btn == null) {
		btn = "";
	}
	if (currentRow == null) {
		currentRow = 0;
	}
	commParams = {};
	if (clickParams != null) {
		commParams.clickParams = clickParams;
	}
	commParams.currentEvent = "click";
	commParams.currentValue = btn;
	commParams.currentRow = currentRow;
	doSubmit(document.commForm, doSuccess, doFailure);
}

/**
 * ����Զ��尴ť,�ύǰ����зǿա�����������У��
 * 
 * @param {}
 *            btn ��ť����
 */
function doClick2(btn, clickParams, checkFlag, currentRow) {
	if (btn == null) {
		btn = "";
	}
	if (currentRow == null) {
		currentRow = 0;
	}
	commParams = {};
	if (clickParams != null) {
		commParams.clickParams = clickParams;
	}
	commParams.currentEvent = "click";
	commParams.currentValue = btn;
	commParams.currentRow = currentRow;
	if (checkValid(checkFlag) != true) { // ��У���ж�
		doSubmitStatus = {};
		return;
	}
	doSubmit(document.commForm, doSuccess, doFailure);
}

/**
 * ���������ť
 * 
 */
function doBatch() {
	commParams = {};
	commParams.currentEvent = "batch";
	doSubmit(document.commForm, doSuccess, doFailure);
}

/**
 * ���������ť
 * 
 */
function doAfterBatch() {
	commParams = {};
	commParams.currentEvent = "afterBatch";
	// ��ʼ��query��commParams
	initQueryCommParams();
	doSubmit(document.commForm, doSuccess, doFailure);
}

/**
 * �����ӵ������ڴ����¼�
 */
function doSubhidden(clickParams) {
	commParams = {};
	commParams.currentEvent = "subhidden";
	commParams.clickParams = clickParams;
	doSubmit(document.commForm, doSuccess, doFailure);
}

/**
 * ���湫������form�����¼�
 */
function doSaveCommScenForm() {
	commParams = {};
	commParams.currentEvent = "saveCommScenForm";
	// ��ʼ��query��commParams
	initQueryCommParams();
	doSubmit(document.commForm, doSuccess, doFailure);
}

/**
 * ��ʼ��query�ֶε�commparams
 */
function initQueryCommParams() {
	// ��isQuery����,��commParams.isQuery_psquery='false'
	var divList = document.commForm.getElementsByTagName("div");
	for (var i = 0; i < divList.length; i++) {
		divId = divList.item(i).id;
		if ((divId.indexOf("div_") == 0) && (document.getElementById("gridDiv_" + divId) == null)) { // div_��ͷ�Ҳ���grid
			for (var n = 0; n < tagNames.length; n++) {
				var inputList = divList.item(i).getElementsByTagName(tagNames[n]);
				for (var j = 0; j < inputList.length; j++) {
					if (inputList.item(j).name && inputList.item(j).name.indexOf("-") == -1 && inputList.item(j).name.indexOf("_combo") == -1) {
						var inputName = inputList.item(j).name;
						if (inputList.item(j).getAttribute("isQuery") != null) {
							eval("commParams.isQuery_" + inputName + "='" + inputList.item(j).getAttribute("isQuery") + "'");
						}
						if (inputList.item(j).checkRate != null) {
							eval("commParams.checkRate_" + inputName + "='" + inputList.item(j).getAttribute("checkRate") + "'");
						}
						if (inputList.item(j).checkDraft != null) {
							eval("commParams.checkDraft_" + inputName + "='" + inputList.item(j).getAttribute("checkDraft") + "'");
						}
					}
				}
			}
		}
	}
}

/**
 * ����
 * 
 * @param {Object}
 *            divName Ҫ�����div����
 * @param {Object}
 *            colsName �������������������ö��Ÿ�������"aae135,aac003",������Ĭ�Ͻ������������������
 */
function doSortAll(divName, colsName) {
	if (Ext.getCmp(divName).store.baseParams.querySQL == null) {
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
	doOpenPupWin(contextPath + "/commform/commpages/comm/commSort.jsp", "���򴰿�", 420, 300);
}
/**
 * ����
 * 
 * @param {Object}
 *            divName Ҫ���˵�div����
 * @param {Object}
 *            colsName ������˵�����������ö��Ÿ�������"aae135,aac003"
 */
function doFilterAll(divName, colsName) {
	if (Ext.getCmp(divName).store.baseParams.querySQL == null) {
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
	doOpenPupWin(contextPath + "/commform/commpages/comm/commFilter.jsp", "���˴���", 650, 400);
}
/**
 * �����ӡ
 */
function doPrint() {
	if (repurl != "") { // �������⴦��
		if (getRepType() == "1") { // ����
			doRepPrint();
		} else if (getRepType() == "2") { // ����
			doRepsPrint();
		}
	} else {
		commParams = {};
		commParams.currentEvent = "print";
		if (reflushAfterSavePrint != null && reflushAfterSavePrint != "") {
			commParams.currentValue = "aftersave";
		}
		doSubmit(document.commForm, doSuccess, doFailure);
	}
}

/**
 * ������ť���¼�
 */
function doExp() {
	commParams = {};
	commParams.currentEvent = "exp";
	doSubmit(document.commForm, doSuccess, doFailure);
}

/**
 * ͨ�õ������
 */
function doCommImp(fileType, param) {
	if (currentOpseno != null) { // �ݸ��䲻�������
		odin.error('��ҳ�治������б�������');
		return;
	}
	commParams = {};
	commParams.currentEvent = "beforeCommImp";
	commParams.currentValue = "1";
	if (fileType == null || fileType == this) {
		fileType = "1";
	}
	if (param) {
		fix = true;
	}
	commParams.currentFileType = fileType.toString();
	doSubmit(document.commForm, doUpload, doFailure);
}

/**
 * ����֮ǰ�¼����ɹ�������ļ��ϴ���
 */
function doImp(fileType, param) {
	if (currentOpseno != null) { // �ݸ��䲻�������
		odin.error('��ҳ�治������б�������');
		return;
	}
	commParams = {};
	commParams.currentEvent = "beforeImp";
	commParams.currentValue = "0";
	if (fileType == null || fileType == this) {
		fileType = "1";
	}
	if (param) {
		fix = true;
	}
	commParams.currentFileType = fileType.toString();
	doSubmit(document.commForm, doUpload, doFailure);
}

/**
 * ���뵽ҳ��ĵ���֮ǰ�¼����ɹ�������ļ��ϴ���
 */
function doImp1(fileType, param) {
	if (currentOpseno != null) { // �ݸ��䲻�������
		odin.error('��ҳ�治������б�������');
		return;
	}
	commParams = {};
	commParams.currentEvent = "beforeImp";
	commParams.currentValue = "1";
	if (fileType == null || fileType == this) {
		fileType = "1";
	}
	if (param) {
		fix = true;
	}
	commParams.currentFileType = fileType.toString();
	doSubmit(document.commForm, doUpload, doFailure);
}

/**
 * ���벢����ĵ���֮ǰ�¼����ɹ�������ļ��ϴ���
 */
function doImp2(fileType, param) {
	if (currentOpseno != null) { // �ݸ��䲻�������
		odin.error('��ҳ�治������б�������');
		return;
	}
	commParams = {};
	commParams.currentEvent = "beforeImp";
	commParams.currentValue = "2";
	if (fileType == null || fileType == this) {
		fileType = "1";
	}
	if (param) {
		fix = true;
	}
	commParams.currentFileType = fileType.toString();
	doSubmit(document.commForm, doUpload, doFailure);
}

/**
 * �ļ��ϴ�
 */
function doUpload() {
	doSubmitFromWaiting(); // �ύ�ȴ����¼�
	doOpenPupWin(contextPath + "/commform/sys/excel/commImpFileWindow.jsp", "��ѡ��Ҫ�ϴ����ļ�", 500, 160);
}

/**
 * ����֮���¼�
 */
function doAfterImp() {
	var value = commParams.currentValue;
	var event = commParams.currentEvent;
	commParams = {};
	if (event == "beforeCommImp") {
		commParams.currentEvent = "commImp";
		// ��ʼ��query��commParams
		initQueryCommParams();
		// ��ѯ������
		window.setTimeout('updateProgressbar()', 1000);
	} else {
		commParams.currentEvent = "afterImp";
	}
	commParams.currentValue = value;
	doSubmit(document.commForm, doSuccess, doFailure);
}

/**
 * ���벢������ɺ�
 */
function doImpSuccess() {
	var altmsg = "";
	if (commParams.currentValue == "1") {
		altmsg = "�����ѳɹ����뵽���棬��˶ԣ�";
	} else if (commParams.currentValue == "2") {
		altmsg = "�����ѳɹ����벢���棡";
		if (getSysType() == "sionline") {
			altmsg = altmsg + "�뵽�ݸ���˶Ժ�����걨��";
		}
	}
	getIFrameWin("iframe_win_pup").doCloseWin();
	if (reflushAfterSave == true) {
		if (reflushAfterSaveMsg != null && reflushAfterSaveMsg != "") {
			altmsg = reflushAfterSaveMsg;
		}
		odin.info(altmsg, function() {
					if (commParams.currentValue == "2") {
						reflush();
					}
				});
	}

}

/**
 * ���½�������Ϣ
 */
function updateProgressbar() {
	doSubmitFromWaiting(); // �ύ�ȴ����¼�
	commParams = {};
	commParams.currentEvent = "updateProgressbar";
	doSubmit(document.commForm, doSuccessForUpdateProgressbar, null);

}
/**
 * ��ѯ�ɹ���Ĵ���
 */
function doSuccessForUpdateProgressbar(response) {
	var progress = Ext.decode(response.data.hashMap.progress)[0];
	var percent = progress.percent;
	var msg = progress.msg;
	msg = odin.toHtmlString(msg);
	if (percent < 1) {
		parent.odin.progress(percent, round(percent * 100, 1) + "%����ɣ�", msg);
		// δ����һֱ��ѯ
		window.setTimeout('updateProgressbar()', 500);
	} else {// ����
		parent.odin.closeMsgBox();
	}
	doSubmitFromWaiting(); // �ύ�ȴ����¼�
}

/**
 * ��հ�ť����������
 */
function doClear() {
	odin.formClear(document.commForm);
}

/**
 * ���Div���������
 */
function divClear(divId) {
	for (var n = 0; n < tagNames.length; n++) {
		var elList = document.getElementById(divId).getElementsByTagName(tagNames[n]);
		for (var i = 0; i < elList.length; i++) {
			elList.item(i).setAttribute("value", "");
		}
	}
}

/**
 * ����ָ���е�onchange�¼�
 * 
 * @param {}
 *            divId div����
 * @param {}
 *            itemId ������
 */
function triggerOnChange(divId, itemId) {
	if (divId == null) {
		divId = commParams.currentDiv;
	}
	if (itemId == null) {
		itemId = commParams.currentColumn;
	}
	if (divId && itemId && document.getElementById(divId) && getDivItem(divId, itemId)) {
		var obj = getDivItem(divId, itemId);
		if (obj.onchange) {
			obj.onchange();
		} else {
			obj.fireEvent("onchange");
		}
	}
}
/**
 * �ǹ���change�Ĵ����¼�
 */
function doOnChangeDateOther(item) {
	if (!doOnChangeCheckDate(item)) {
		return;
	}
	var name = item.name || item.getId();
	// setFocusNext(name);
}
/**
 * ������ڸ�ʽ�Ƿ���ȷ
 */
function doOnChangeCheckDate(item) {
	var name = item.name;
	if (!name) {
		name = item.getId();
		item = document.getElementById(name);
	}
	// date��ʽ�����⴦��(�ڱ�ǩ����֮ǰ�ȴ�������yyyyMMdd��ʽ�Ļ����)
	if (getFieldType(name) == "date") {
		if (Ext.getCmp(name).format == "Y-m-d H:i:s") { // ����
			if (checkDateTime(name) == false) {
				return false;
			}
		} else { // ����
			if (checkDate(name) == false) {
				return false;
			}
		}
	}
	return true;
}

/**
 * html��ʽ��onchanged�¼�
 */
function doOnChange(item) {
	var name = item.name;
	if (!name) {
		name = item.getId();
		item = document.getElementById(name);
	}
	var value = item.value;
	if(odin.isWorkpf){
		if(value=="" && name.indexOf("cpquery")==0) return;
	}
	if (Ext.getCmp(name)) { // ��ǩ����
		var validator = false;
		try {
			validator = Ext.getCmp(name).validator();
		} catch (e) {
			validator = true;
		}
		if (!Ext.getCmp(name).isValid() && validator && name.indexOf("psquery") != 0 && name.indexOf("cpquery") != 0 && name.indexOf("psidquery") != 0 && name.indexOf("psidnew") != 0) {// ��ǩУ��ͨ�����ҷǺ�̨У���Ҳ���cpquery,psquery,psidquery,psidnew
			return;
		}
		// date��ʽ�����⴦��(�ڱ�ǩ����֮ǰ�ȴ�������yyyyMMdd��ʽ�Ļ����)
		if (getFieldType(name) == "date") {
			if (!doOnChangeCheckDate(item)) {
				return;
			}
		}
		Ext.getCmp(name).beforeBlur();
		if(value==""){
			value = Ext.getCmp(name).getValue();
		}
	}
	// select��ʽ�����⴦��
	if (name.indexOf("_combo") >= 0) {
		name = name.substr(0, name.indexOf("_combo"));
	}
	if (Ext.getCmp(name + "_combo")) {
		if (checkSelect(name) == false) {
			// alert(11);
			return;
		}
		if(value==""){
			value = document.getElementById(name).value;
		}
		// alert(value);
	}
	// checkbox��ʽ�����⴦��
	if (item.type == "checkbox") {
		value = item.checked;
	}
	commParams = {};
	commParams.currentDiv = getParentDiv(name);
	commParams.currentEvent = "onchange";
	commParams.currentRow = 0;
	commParams.currentColumn = name;
	commParams.currentOriginalValue = getCmpByName(commParams.currentColumn).startValue;
	if (commParams.currentOriginalValue == null || commParams.currentOriginalValue == "") {
		commParams.currentOriginalValue = tempOriginalValue;
	}
	commParams.currentValue = value;
	commParams.isQuery = (item.getAttribute("isQuery") == "true" ? "true" : "false");
	commParams.checkRate = (item.getAttribute("checkRate") == null ? "-1" : item.getAttribute("checkRate"));
	commParams.checkDraft = (item.getAttribute("checkDraft") == "true");
	if (commParams.isQuery == "true") { // ��Ҫ��ѯʱ�Ž��в�ѯ
		isQuerying = true; // ���ڲ�ѯ����������������ʱʹ��
		// query�Ĵ���
		if (name.indexOf("psquery") == 0) {// ��psquery��ͷ����Ա��ѯ
			odin.mask("���ڲ�ѯ..."); // ������Ӱ
			doOpenPupWin("/pages/commAction.do?method=PSQuery", "��Ա��ѯ -- ѡ��ú�˫����س�����ȷ�� -- ��ݼ����� �� �� �� Enter Esc", 0.8, (Ext.isIE ? 323 : 313), commParams.currentValue + "," + (MDParam == null || MDParam.uptype == "" ? "00" : MDParam.uptype) + "," + commParams.checkRate);
			doHiddenPupWin();// ������
			onQueryPupWindowCloseClick();// �رհ�ť���¼�
			return;
		} else if (name.indexOf("cpquery") == 0) {// ��cpquery��ͷ�ĵ�λ��ѯ
			odin.mask("���ڲ�ѯ..."); // ������Ӱ
			doOpenPupWin("/pages/commAction.do?method=CPQuery", "��λ��ѯ -- ѡ��ú�˫����س�����ȷ�� -- ��ݼ����� �� �� �� Enter Esc", 0.8, (Ext.isIE ? 323 : 313), commParams.currentValue + "," + (MDParam == null || MDParam.uptype == "" ? "00" : MDParam.uptype) + "," + commParams.checkRate);
			doHiddenPupWin();// ������
			onQueryPupWindowCloseClick();// �رհ�ť���¼�
			return;
		} else if (name.indexOf("psidquery") == 0) {// ��psidquery��ͷ����Ա��ݲ�ѯ
			odin.mask("���ڲ�ѯ..."); // ������Ӱ
			doOpenPupWin("/pages/commAction.do?method=PSIDQuery", "��Ա��ݲ�ѯ -- ѡ��ú�˫����س�����ȷ�� -- ��ݼ����� �� �� �� Enter Esc", 0.8, (Ext.isIE ? 323 : 313), commParams.currentValue + "," + (MDParam == null || MDParam.uptype == "" ? "00" : MDParam.uptype) + "," + commParams.checkRate);
			doHiddenPupWin();// ������
			onQueryPupWindowCloseClick();// �رհ�ť���¼�
			return;
		} else if (name.indexOf("psidnew") == 0) {// ��psidnew��ͷ����Ա�������
			odin.mask("���ڲ�ѯ..."); // ������Ӱ
			doOpenPupWin("/pages/commAction.do?method=PSIDNew", "��Ա������� -- ѡ��ú�˫����س�����ȷ��,���رջ�Esc��������������� -- ��ݼ����� �� �� �� Enter Esc", 0.8, (Ext.isIE ? 323 : 313), commParams.currentValue + "," + (MDParam == null || MDParam.uptype == "" ? "00" : MDParam.uptype) + "," + commParams.checkRate);
			doHiddenPupWin();// ������
			onQueryPupWindowCloseClick();// �رհ�ť���¼�
			return;
		}
	}
	doSubmit(document.commForm, doSuccess, doFailure);
}

/**
 * ���ϲ�ѯ���ڵĹرհ�ť����
 */
function onQueryPupWindowCloseClick() {
	var windowId = "win_pup";
	var pupWindow = Ext.getCmp(windowId);
	pupWindow.tools.close.dom.onclick = closeQueryPupWindow;
}
/**
 * ȥ�����ڵĹرհ�ť����
 */
function unPupWindowCloseClick() {
	var windowId = "win_pup";
	var pupWindow = Ext.getCmp(windowId);
	pupWindow.tools.close.dom.onclick = null;
}

/**
 * ��Ա������ִ�еĶ���
 */
function doOnPersonSelected(data) {
	commParams = {};
	commParams.currentDiv = "div_searchPerson";
	commParams.currentEvent = "onchange";
	commParams.currentRow = 0;
	commParams.currentColumn = "searchText";
	commParams.currentOriginalValue = "";
	commParams.currentValue = Ext.getCmp(commParams.currentColumn).getValue();
	doSubmit(document.commForm, doSuccess, doFailure);
}

/**
 * �����������Ƿ�Ϊ����������
 */
function checkSelect(objId) {
	var ret = checkSelectValueInList(objId);
	if (ret == true ||objId.indexOf('cpquery')==0) {
		Ext.getCmp(objId + "_combo").checkSelectComplete = true;
		setValid(objId, true);
		return true;
	} else {
		var combo = Ext.getCmp(objId + "_combo");
		var value = document.getElementById(objId + "_combo").value;
		if (combo.mode == "remote" && combo.lastQuery != value) {
			if (combo.checkSelectComplete == null || combo.checkSelectComplete == true) {// �Ѿ���ִ��������ִ��
				combo.store.on('load', storeRemoteOnLoad = function(ds) {
							combo.triggerBlur();
							combo.store.un("load", storeRemoteOnLoad);
						});
				window.setTimeout("Ext.getCmp('" + objId + "_combo').doQuery('" + value + "', true)");
			}
			combo.checkSelectComplete = false;
			return false;
		} else {
			combo.checkSelectComplete = true;
		}
		setValid(objId, false, ret);
		return false;
	}
}

/**
 * ����������Ƿ�������������,�粻������Ϊ��
 */
function checkAndSetSelectValue(objId) {
	var ret = checkSelectValueInList(objId);
	if (ret != true) {
		odin.setSelectValue(objId, "");
	}
}
/**
 * �����������Ƿ�Ϊ����������
 */
function checkSelectValueInList(objId) {
	if (!document.getElementById(objId + "_combo")) {
		return true;
	}
	if (document.getElementById(objId + "_combo").getAttribute("canOutSelectList") == "true") {
		var value = document.getElementById(objId + "_combo").value;
		if (value == null || value == "" || value == "��ѡ��...") {
			odin.setSelectValue(objId, "");
		} else {
			odin.setSelectValue(objId, value);
		}
		return true;
	}
	var value = document.getElementById(objId + "_combo").value;
	if (value == null || value == "" || value == "��ѡ��...") {
		odin.setSelectValue(objId, "");
		return true;
	}
	var combo = Ext.getCmp(objId + "_combo");
	var store = combo.store;
	if (store.isFiltered()) {
		combo.doQuery(combo.allQuery, true);
	}
	var length = store.getCount();
	if (length == 0) {
		var errmsg = "������ġ�" + value + "�������б��У�";
		return errmsg;
	}
	var valueArray = value.split(";");
	for (var n = 0; n < valueArray.length; n++) {
		var checkValue = valueArray[n].trim();
		for (var i = 0; i < length; i++) {
			var rs = store.getAt(i);
			var key = rs.get('key');
			var value1 = rs.get('value');
			if (checkValue == value1 || checkValue == key) {
				valueArray[n] = key;
				break;
			} else if (i == length - 1) {
				var errmsg = "������ġ�" + checkValue + "�������б��У�";
				return errmsg;
			}
		}
	}
	value = valueArray.join(',');
	odin.setSelectValue(objId, value);
	return true;
}

/**
 * ���ڸ�ʽ�����У��
 */
function checkDate(objId) {
	var value = document.getElementById(objId).value;
	var errmsg = checkDateValue(value);
	if (errmsg !== true) {
		setValid(objId, false, errmsg);
		return false;
	}
	document.getElementById(objId).value = renderDate(value);
	setValid(objId, true);
	return true;
}
/**
 * ���ڸ�ʽ�����ֵУ��
 */
// TODO 20121207 ����Ŀǰext�Զ���ֵ�������滻��δ����ʵ��У���¡��յĸ�ʽ��ֻҪparseDate������ͻ�û���⣬���Ժ��޸�
function checkDateValue(value) {
	if (value == null) {
		return true;
	}
	value = value.toString();
	if (value.length != 8 && value.length != 10) {
		return "���ȱ���Ϊ8λ��10λ����20080808��2008-08-08";
	}
	if (value.length == 8) {
		var year = value.substring(0, 4);
		var month = value.substring(4, 6);
		var date = value.substring(6, 8);
	} else {
		var year = value.substring(0, 4);
		var month = value.substring(5, 7);
		var date = value.substring(8, 10);
	}
	if (isNaN(Number(year))) {
		return "�����������,����������!";
	} else if (Number(year) < 1900 || Number(year) > 2100) {
		return "���Ӧ����1900-2100֮��!";
	}
	if (isNaN(Number(month))) {
		return "�·���������,����������!";
	} else if (Number(month) < 1 || Number(month) > 12) {
		return "�·�Ӧ����1-12֮��!";
	}
	var daysOfMonth = new Date(year, month, 0).getDate();
	if (isNaN(Number(date))) {
		return "������������,����������!";
	} else if (Number(date) < 1 || Number(date) > daysOfMonth) {
		return "����Ӧ����1-" + daysOfMonth + "֮��!";
	}
	return true;
}

/**
 * ���ڸ�ʽ�����У��
 */
function checkDateTime(objId) {
	var value = document.getElementById(objId).value;
	if (value.length != 14 && value.length != 19) {
		errmsg = "���ȱ���Ϊ14λ��19λ����20080808080808��2008-08-08 08:08:08"
		setValid(objId, false, errmsg);
		return false;
	}
	document.getElementById(objId).value = renderDateTime(value);
	setValid(objId, true);
	return true;
}

/**
 * html��ʽ����Ŀonfocus�¼�����ʱֻΪȡ��ĿԭʼֵcommParams.currentOriginalValueʹ��
 */
function doOnFocus(item) {
	var name = item.name;
	var value = item.value;
	if (!name) {
		name = item.getId();
	}
	// select��ʽ�����⴦��
	if (name.indexOf("_combo") >= 0) {
		name = name.substr(0, name.indexOf("_combo"));
	}
	if (Ext.getCmp(name + "_combo")) {
		value = document.getElementById(name).value;
	}
	// date��ʽ�����⴦��
	if (getFieldType(name) == "date") {

	}
	// checkbox��ʽ�����⴦��
	if (item.type == "checkbox") {
		value = item.checked;
	}
	tempOriginalValue = value;
}

/**
 * ����afteredit�¼�
 */
function triggerAfterEdit(gridId, itemId, rowIndex) {
	var e = {};
	e.grid = Ext.getCmp(gridId);
	e.record = e.grid.getStore().getAt(rowIndex);
	e.field = itemId;
	e.originalValue = '';
	e.value = e.record.get(itemId);
	e.row = rowIndex;
	e.column = getGridColumnIndex(gridId, itemId);
	e.grid.fireEvent('afteredit', e);
}

/**
 * grid��ʽ��afteredit�¼�
 */
function doAfterEdit(e) {
	if (odin.doAfterEditForEditGrid(e) == false) {
		return;
	}
	e.value = e.record.get(e.field);
	var grid = e.grid;
	var record = e.record;
	var field = e.field;
	var originalValue = e.originalValue;
	var value = e.value;
	var row = e.row;
	var column = e.column;
	commParams = {};
	commParams.currentDiv = grid.id;
	commParams.currentEvent = "afteredit";
	commParams.currentRow = row;
	commParams.currentColumn = field;
	commParams.currentOriginalValue = originalValue;
	commParams.currentValue = value;
	doSubmit(document.commForm, doSuccess, doFailure);
}

/**
 * grid��ʽ��rowDbClick�¼�
 */
function doRowDbClick(grid, rowIndex, event) {
	commParams = {};
	commParams.currentDiv = grid.id;
	commParams.currentEvent = "rowDbClick";
	commParams.currentRow = rowIndex;
	commParams.currentColumn = "";
	commParams.currentOriginalValue = "";
	commParams.currentValue = "";
	doSubmit(document.commForm, doSuccess, doFailure);
}

/**
 * �������
 */
function doSave() {
	commParams = {};
	commParams.currentEvent = "save";
	doSubmit(document.commForm, doSuccess, doFailure);
}

/**
 * ���ݼ���֮�����
 */
function doQueryLoad(ds) {
	var gridId = ds.baseParams.div;
	parent.odin.unmask(); // ȥ��
	if (ds.getTotalCount() == 0) {// û������
		var xt = "����ϵͳ��";
		if (parent.MDParam == null || parent.MDParam.uptype == "" || parent.MDParam.uptype == "00") {
			xt = "��ᱣ��ϵͳ��";
		} else if (parent.MDParam.uptype == "09") {
			xt = "���������ϱ���";
		} else if (parent.MDParam.uptype == "10") {
			xt = "ũ�����ϱ���ϵͳ��";
		} else if (parent.MDParam.uptype == "11") {
			xt = "��������������ϱ���";
		} else if (parent.MDParam.uptype == "20") {
			xt = "����ũ�����ϱ���";
		} else if (parent.MDParam.uptype == "21") {
			xt = "����������ϱ���ϵͳ��";
		} else if (parent.MDParam.uptype == "91") {
			xt = "����ϵͳ��";
		}
		var errmsg = xt + "û�з������������ݣ�\n����������ַ���Ϣ�������������²�ѯ��";
		if (MDParam.location.indexOf("PSQuery") != -1) {
			errmsg = xt + "û��Ҫ���ҵ���Ա��\n����������ַ���Ϣ�������������²�ѯ��";
		} else if (MDParam.location.indexOf("CPQuery") != -1) {
			errmsg = xt + "û��Ҫ���ҵĵ�λ��\n����������ַ���Ϣ�������������²�ѯ��";
		} else if (MDParam.location.indexOf("PSIDQuery") != -1) {
			errmsg = xt + "û��Ҫ���ҵ���Ա��ݣ�\n����������ַ���Ϣ�������������²�ѯ��";
		} else if (MDParam.location.indexOf("PSIDNew") != -1) { // psidnew�鲻������к�̨����
			parent.doQuerySelect(getGridNullJson(gridId), true);
			return;
		}else if(MDParam.location.indexOf("CPNew") != -1){
			parent.doQuerySelect(getGridNullJson(gridId), true);
			return;
		}
		parent.doQuerySelect(getGridNullJson(gridId));
		parent.setValid(parent.commParams.currentColumn, false, errmsg);
		parent.odin.error(errmsg, function() {
					parent.setFocus(parent.commParams.currentColumn);
				});
		return;
	} else if (ds.getTotalCount() == 1) {// һ������
		parent.setValid(parent.commParams.currentColumn, true);
		if(document.getElementById("iscpflag_nonem") && (document.getElementById("iscpflag_nonem").value== false || document.getElementById("iscpflag_nonem").value =='false' || document.getElementById("iscpflag_nonem").value=='')){
			var params = {};
			if(document.getElementById("aac031filter_nonem").value != ""){
				//alert(commParams.aac031Filter);
				params.querySQL = "select aab001 aaa102,aab004 aaa103,aaz001 from sbdv_acg8 a where aac001="+ ds.getAt(0).json.aac001+" and exists(select 1 from ac20 where aac001=a.aac001 and aac031='"+document.getElementById("aac031filter_nonem").value+"')" ;
			}else{
				params.querySQL = "select aab001 aaa102,aab004 aaa103,aaz001 from sbdv_acg8 a where aac001="+ ds.getAt(0).json.aac001+"" ;
			}
			params.sqlType = "SQL";
			var req = odin.commonQuery(params, successFunc, null, false, false);
			var data = odin.ext.decode(req.responseText).data.data;
			if(data.length>0){
				ds.getAt(0).json.aab001=data[0].aaa102;
				ds.getAt(0).json.cpquery=data[0].aaa102;
				ds.getAt(0).json.aab004=data[0].aaa103;
				ds.getAt(0).json.aaz001=data[0].aaz001;
				ds.getAt(0).json.cpcombo=odin.ext.encode(data);
			}
		}
		parent.doQuerySelect(ds.getAt(0).json, true);
		parent.doHiddenPupWin();
		return;
	} else {// ��������
		parent.setValid(parent.commParams.currentColumn, true);
		parent.doShowPupWin();
		window.setTimeout("grid=Ext.getCmp('" + gridId + "');grid.getView().getRow(0).focus();grid.getSelectionModel().selectFirstRow();grid.getView().focusRow(0)", 200);

		// Ext.getCmp(gridId).getView().getRow(0).focus();
		// Ext.getCmp(gridId).getSelectionModel().selectFirstRow();
		// Ext.getCmp(gridId).getView().focusRow(0);
		Ext.getCmp(gridId).store.un('load', doQueryLoad);
	}

}

/**
 * ��ѯ�¼��Ĺرմ��ڲ���
 */
function closeQueryPupWindow() {
	var triggerOnchange = false;
	if (document.getElementById("iframe_win_pup").src.indexOf("PSIDNew") != -1) {// ��Ա����������⴦��
		triggerOnchange = true;
	}
	doQuerySelect(getIFrameWin("iframe_win_pup").getGridNullJson(getIFrameWin("iframe_win_pup").isQueryDiv), triggerOnchange);
}
/**
 * ��ѯ�¼��İ�������
 */
function doQueryKeydown(e) {
	if (e.keyCode == e.ENTER) {// �س�
		if(document.getElementById("iscpflag_nonem") && (document.getElementById("iscpflag_nonem").value== false || document.getElementById("iscpflag_nonem").value =='false' || document.getElementById("iscpflag_nonem").value=='')){
			var params = {};
			if(document.getElementById("iscpflag_nonem").value != "" && document.getElementById("iscpflag_nonem").value != 'false'){
				//alert(commParams.aac031Filter);
				params.querySQL = "select aab001 aaa102,aab004 aaa103,aaz001 from sbdv_acg8 a where aac001="+ r.data.aac001+" and exists(select 1 from ac20 where aac001=a.aac001 and aac031='"+document.getElementById("aac031filter_nonem").value+"')" ;
			}else{
				params.querySQL = "select aab001 aaa102,aab004 aaa103,aaz001 from sbdv_acg8 a where aac001="+ r.data.aac001+"" ;
			}
			params.sqlType = "SQL";
			var req = odin.commonQuery(params, successFunc, null, false, false);
			var data = odin.ext.decode(req.responseText).data.data;
			if(data.length>0){
				this.getSelections()[0].json.aab001=data[0].aaa102;
				this.getSelections()[0].json.cpquery=data[0].aaa102;
				this.getSelections()[0].json.aab004=data[0].aaa103;
				this.getSelections()[0].json.aaz001=data[0].aaz001;
				this.getSelections()[0].json.cpcombo=odin.ext.encode(data);
			}
		
		}
		parent.doQuerySelect(this.getSelections()[0].json, true);
		parent.doHiddenPupWin();
	} else if (e.keyCode == e.ESC) {// �˳�
		var triggerOnchange = false;
		if (window.location.href.indexOf("PSIDNew") != -1) {// ��Ա����������⴦��
			triggerOnchange = true;
		}
		parent.doQuerySelect(getGridNullJson(this.id), triggerOnchange);
		parent.doHiddenPupWin();
	} else if (e.ctrlKey && (e.keyCode == e.LEFT || e.keyCode == e.PAGEUP)) {// ctrl+�������ҳ--��һҳ
		doLoadFirstPage(this.id);
	} else if (e.keyCode == e.LEFT || e.keyCode == e.PAGEUP) {// �������ҳ--��һҳ
		doLoadPrevPage(this.id);
	} else if (e.ctrlKey && (e.keyCode == e.RIGHT || e.keyCode == e.PAGEDOWN)) {// ctrl+�Ҽ�����ҳ--��һҳ
		doLoadLastPage(this.id);
	} else if (e.keyCode == e.RIGHT || e.keyCode == e.PAGEDOWN) {// �Ҽ�����ҳ--��һҳ
		doLoadNextPage(this.id);
	} else if (e.keyCode == e.DOWN) {// �¼�--��һ��
		if (!Ext.isIE && !Ext.isGecko) { // ��IE��Firefox����Ҫ��IE��FirefoxĬ���д˹���
			Ext.getCmp(this.id).getSelectionModel().selectNext();
		}
	} else if (e.keyCode == e.UP) {// �ϼ�--��һ��
		if (!Ext.isIE && !Ext.isGecko) { // ��IE��Firefox����Ҫ��IE��FirefoxĬ���д˹���
			Ext.getCmp(this.id).getSelectionModel().selectPrevious();
		}
	}
}

/**
 * ȡ�ÿ��е�grid��json
 */
function getGridNullJson(gridId) {
	jsonStr = {};
	if (gridId == null || !Ext.getCmp(gridId)) {
		return jsonStr;
	}
	var gridColumnModel = Ext.getCmp(gridId).getColumnModel();
	if (!gridColumnModel) {
		return jsonStr;
	}
	for (var j = 0; j < gridColumnModel.getColumnCount(); j++) {
		var dataIndex = gridColumnModel.getDataIndex(j)
		if (dataIndex != "") {
			eval("jsonStr." + dataIndex + "=''");
		}
	}
	return jsonStr;
}

/**
 * ��ҳgrid�ĵ�һҳ
 */
function doLoadFirstPage(gridId) {
	pageingToolbar = Ext.getCmp(gridId).getBottomToolbar();
	if (pageingToolbar.cursor == 0) {// ��һҳ������
		return;
	}
	pageingToolbar.onClick("first");
}
/**
 * ��ҳgrid����һҳ
 */
function doLoadPrevPage(gridId) {
	pageingToolbar = Ext.getCmp(gridId).getBottomToolbar();
	if (pageingToolbar.cursor == 0) {// ��һҳ������
		return;
	}
	pageingToolbar.onClick("prev");
}
/**
 * ��ҳgrid�ĵ�ǰҳˢ��
 */
function doLoadCurrentPage(gridId) {
	pageingToolbar = Ext.getCmp(gridId).getBottomToolbar();
	pageingToolbar.onClick("refresh");
}
/**
 * ��ҳgrid����һҳ
 */
function doLoadNextPage(gridId) {
	pageingToolbar = Ext.getCmp(gridId).getBottomToolbar();
	if (pageingToolbar.cursor + pageingToolbar.pageSize >= pageingToolbar.store.getTotalCount()) {// ���һҳ������
		return;
	}
	pageingToolbar.onClick("next");
}
/**
 * ��ҳgrid�����һҳ
 */
function doLoadLastPage(gridId) {
	pageingToolbar = Ext.getCmp(gridId).getBottomToolbar();
	if (pageingToolbar.cursor + pageingToolbar.pageSize >= pageingToolbar.store.getTotalCount()) {// ���һҳ������
		return;
	}
	pageingToolbar.onClick("last");
}
/**
 * ��ѯ�¼������˫������
 */
function doQueryRowDbClick(g,i) {
	var s=g.store;
	var r=s.getAt(i);
	if(document.getElementById("iscpflag_nonem") && (document.getElementById("iscpflag_nonem").value== false || document.getElementById("iscpflag_nonem").value =='false' || document.getElementById("iscpflag_nonem").value=='')){
		var params = {};
		if(document.getElementById("iscpflag_nonem").value != "" && document.getElementById("iscpflag_nonem").value != 'false'){
			//alert(commParams.aac031Filter);
			params.querySQL = "select aab001 aaa102,aab004 aaa103,aaz001 from sbdv_acg8 a where aac001="+ r.data.aac001+" and exists(select 1 from ac20 where aac001=a.aac001 and aac031='"+document.getElementById("aac031filter_nonem").value+"')" ;
		}else{
			params.querySQL = "select aab001 aaa102,aab004 aaa103,aaz001 from sbdv_acg8 a where aac001="+ r.data.aac001+"" ;
		}
		params.sqlType = "SQL";
		var req = odin.commonQuery(params, successFunc, null, false, false);
		var data = odin.ext.decode(req.responseText).data.data;
		if(data.length>0){
			r.set('aab001',data[0].aaa102);
			r.set('cpquery',data[0].aaa102);
			r.set('aab004',data[0].aaa103);
			r.set('aaz001',data[0].aaz001);
			r.set('cpcombo',odin.ext.encode(data));
		}
		
	}
	parent.doQuerySelect(r.data, true);
	parent.doHiddenPupWin();
}
 function successFunc(responseTxt) { // �յ�ajax���óɹ��Ĵ�����
	//
}
/**
 * ѡ�����
 */
function doQuerySelect(jsonStr, isDoOnChanged) {
	unPupWindowCloseClick();// ȥ���رհ�ť�ĺ���
	isQuerying = false;// ��ѯ����
	var div = document.getElementById(commParams.currentDiv);
	var col = commParams.currentColumn;
	if (isDoOnChanged == true) { // �����ɹ�
		if (col.indexOf("psquery") == 0) { // ��Ա��ѯ
			getDivItem(div, col).value = jsonStr.aae135;
			if (!fromCommLink) {
				setPsqueryBuffer(jsonStr.eac001);
			}
		} else if (col.indexOf("cpquery") == 0) { // ��λ��ѯ
			getDivItem(div, col).value = jsonStr.aab001;
			if (!fromCommLink) {
				setCpqueryBuffer(jsonStr.aab001);
			}
		} else if (col.indexOf("psidquery") == 0) { // ��Ա��ݲ�ѯ
			getDivItem(div, col).value = jsonStr.aae135;
			if (!fromCommLink) {
				setPsidqueryBuffer(jsonStr.aae135);
			}
		} else if (col.indexOf("psidnew") == 0) { // ��Ա�������
			if (jsonStr.aae135 != null && jsonStr.aae135 != "") {
				getDivItem(div, col).value = jsonStr.aae135;
			}
		}
	}
	// setFocus(col);
	for (var n = 0; n < tagNames.length; n++) {
		var inputList = div.getElementsByTagName(tagNames[n]);
		for (var j = 0; j < inputList.length; j++) {
			if (inputList.item(j).name && inputList.item(j).name.indexOf("-") == -1 && inputList.item(j).name.indexOf("_combo") == -1) {
				var input = inputList.item(j);
				var jsonName = input.name;
				if (jsonName.indexOf("_") != -1) {
					jsonName = jsonName.substring(0, jsonName.indexOf("_"));
				}
				if (jsonName.indexOf("nonem") != -1) {
					jsonName = jsonName.substring(0, jsonName.indexOf("nonem"));
				}
				var value = eval("jsonStr." + jsonName);
				if (typeof(value) == "undefined") {// δ����
					continue;
				} else if (value == null) {
					value = "";
				}
				if (value == inputList.item(j).value) {// ��ͬ�򲻴���
					continue;
				}
				if (value == "" && input.initValue) {// ������initValue�Ļ����initValue�����Ȩ�޿��Ƶ�����
					value = input.initValue;
				}
				if (Ext.getCmp(inputList.item(j).name + "_combo")) { // ������
					odin.setSelectValue(inputList.item(j).name, value);
				} else if (getFieldType(inputList.item(j).name) == "date") { // ���ڸ�ʽ
					if (Ext.getCmp(inputList.item(j).name).format == "Y-m-d H:i:s") { // ����
						inputList.item(j).value = renderDateTime(value);
					} else {
						inputList.item(j).value = renderDate(value);
					}
				} else if (inputList.item(j).type == "checkbox") { // ��
					if (((value == "true" || value == "1") ? true : false) != inputList.item(j).checked) {
						inputList.item(j).checked = (value == "true" || value == "1") ? true : false;
					}
				} else {
					inputList.item(j).value = value;
				}
			}
		}
	}
	if (isDoOnChanged == true) {// �����ɹ��������ύ
		// ѡ��ú󣬽���onchange���ύ����
		doSubmit(document.commForm, doSuccess, doFailure);
	}
}

/**
 * ��psquery��cpquery�����˫��ʱ�Ĳ���
 */
function doQueryDblClick(item) {
	if (item.getAttribute("isQuery") == "true") { // Ҫ��ѯ����
		var buffer = null;
		if (item.name.indexOf("cpquery") != -1) {
			buffer = getCpqueryBuffer();
		} else if (item.name.indexOf("psquery") != -1) {
			buffer = getPsqueryBuffer();
		} else if (item.name.indexOf("psidquery") != -1) {
			buffer = getPsidqueryBuffer();
		}
		if (buffer != null && buffer != "" && (item.value == null || item.value == "")) {
			item.value = buffer;
			doOnChange(item);
		}
	}
}

/**
 * ��ҳgrid�İ�������
 */
function doPageGridKeyDown(e) {
	if (e.ctrlKey && e.keyCode == e.PAGEUP) {// ctrl+��ҳ��--��һҳ
		doLoadFirstPage(this.id);
	} else if (e.keyCode == e.PAGEUP) {// ��ҳ��--��һҳ
		doLoadPrevPage(this.id);
	} else if (e.ctrlKey && e.keyCode == e.PAGEDOWN) {// ctrl+��ҳ--��һҳ
		doLoadLastPage(this.id);
	} else if (e.keyCode == e.PAGEDOWN) {// ��ҳ--��һҳ
		doLoadNextPage(this.id);
	} else if (e.keyCode == e.DOWN) {// �¼�--��һ��
		if (!Ext.isIE && !Ext.isGecko) { // ��IE��Firefox����Ҫ��IE��FirefoxĬ���д˹���
			Ext.getCmp(this.id).getSelectionModel().selectNext();
		}
	} else if (e.keyCode == e.UP) {// �ϼ�--��һ��
		if (!Ext.isIE && !Ext.isGecko) { // ��IE��Firefox����Ҫ��IE��FirefoxĬ���д˹���
			Ext.getCmp(this.id).getSelectionModel().selectPrevious();
		}
	}
}

/**
 * ����ҳgrid�İ�������
 */
function doNotPageGridKeyDown(e) {
	if (e.keyCode == e.DOWN) {// �¼�--��һ��
		if (!Ext.isIE && !Ext.isGecko) { // ��IE��Firefox����Ҫ��IE��FirefoxĬ���д˹���
			Ext.getCmp(this.id).getSelectionModel().selectNext();
		}
	} else if (e.keyCode == e.UP) {// �ϼ�--��һ��
		if (!Ext.isIE && !Ext.isGecko) { // ��IE��Firefox����Ҫ��IE��FirefoxĬ���д˹���
			Ext.getCmp(this.id).getSelectionModel().selectPrevious();
		}
	}
}
/**
 * �ж��Ƿ�����༭
 */
function canEdit(e) {
	e.msgShow = false;
	try {
		return canEditCell(e.grid.id, e.field) && Ext.getCmp(e.grid.id).fireEvent('beforeedit', e);
	} finally {
		e.msgShow = true;
	}
}

/**
 * �ж�grid�����Ƿ�����༭
 */
function canEditCell(gridId, fieldName) {
	return getGridColumn(gridId, fieldName).editable == true ? true : false;
}

/**
 * �༭���Ч��
 */
function renderEdit(value, params, record, rowIndex, colIndex, ds) {
	if (!ds) {// dsΪ�գ�������
		return value;
	}
	var e = {};
	var gridId = ds.baseParams.div;
	e.grid = Ext.getCmp(gridId);
	e.record = record;
	e.field = Ext.getCmp(ds.baseParams.div).getColumnModel().getDataIndex(colIndex);
	e.originalValue = value;
	e.value = value;
	e.row = rowIndex;
	e.column = colIndex;
	var col = getGridColumn(gridId, e.field);
	if (col.editor) {
		var type = col.editor.field.getXType();
		if (type == "numberfield" || type == "textfield" || type == "datefield" || type == "combo" || type == "textarea" || type == "timefield") {// ֻ�����ַ��������֡����ڡ��������ʽ����
			if (canEdit(e)) {
				// if (!Ext.isIE && !col.hasSetWidth) {
				// col.width = col.oriwidth - 2;
				// col.hasSetWidth = true;
				// }
				params.css = "x-grid-cell-enable";
			} else {
				// if (!Ext.isIE && !col.hasSetWidth) {
				// col.width = col.width - 1;
				// col.hasSetWidth = true;
				// }
				params.css = "x-grid-cell-disable";
			}
		}
	}
	return value;
}
/**
 * ʱ���ʽ����ʾת������
 */
function renderDate(value, params, record, rowIndex, colIndex, ds) {
	if (value == null || value == "") {
		return "";
	}
	if (typeof(value) == 'string') {
		if (value.indexOf("-") >= 0) {
			value = value.substr(0, 10);
			value = Date.parseDate(value, 'Y-m-d');
		} else {
			value = value.substr(0, 8);
			value = Date.parseDate(value, 'Ymd');
		}
	} else if (typeof(value) == 'object') {
		if (Ext.util.JSON.encode(value).indexOf("{") >= 0 && Ext.util.JSON.encode(value).indexOf("}") >= 0) {
			value = odin.Ajax.formatDate(value);
			value = Date.parseDate(value, 'Y-m-d');
		}
	}
	if (record != null && Ext.getCmp(ds.baseParams.div).getColumnModel().getDataIndex(colIndex) != null) {
		var gridName = ds.baseParams.div;
		var colName = Ext.getCmp(gridName).getColumnModel().getDataIndex(colIndex);
		var type = getGridFieldType(gridName, colName);
		if (type == 'date') {
			record.set(Ext.getCmp(ds.baseParams.div).getColumnModel().getDataIndex(colIndex), value);
		}
	}
	return Ext.util.Format.date(value, 'Y-m-d');
}

/**
 * ʱ���ʽ��ת������
 */
function renderDateTime(value, params, record, rowIndex, colIndex, ds) {
	if (value == null || value == "") {
		return "";
	}
	if (typeof(value) == "string") {
		value = value.replace("T", " ");
		if (value.indexOf("-") >= 0) {
			value = value.substr(0, 19);
			value = Date.parseDate(value, 'Y-m-d H:i:s');
		} else {
			value = value.substr(0, 14);
			value = Date.parseDate(value, 'YmdHis');
		}
	} else if (typeof(value) == 'object') {
		if (Ext.util.JSON.encode(value).indexOf("{") >= 0 && Ext.util.JSON.encode(value).indexOf("}") >= 0) {
			value = odin.Ajax.formatDateTime(value);
			value = Date.parseDate(value, 'Y-m-d H:i:s');
		}
	}
	if (record != null && Ext.getCmp(ds.baseParams.div).getColumnModel().getDataIndex(colIndex) != null) {
		var gridName = ds.baseParams.div;
		var colName = Ext.getCmp(gridName).getColumnModel().getDataIndex(colIndex);
		var type = getGridFieldType(gridName, colName);
		if (type == 'date') {
			record.set(Ext.getCmp(ds.baseParams.div).getColumnModel().getDataIndex(colIndex), value);
		}
	}
	return Ext.util.Format.date(value, 'Y-m-d H:i:s');
}

/**
 * ���������ʾ��Ч��
 */
function renderAlt(value, params, record, rowIndex, colIndex, ds, colorExp) {
	if (value == null) {
		return;
	}
	value = value.toString();
	if (value.indexOf("</") != -1 || value.indexOf("/>") != -1) {
		return value;
	}
	var value2 = value;
	if (colorExp != null && colorExp != '') {
		value2 = renderColor(value, params, record, rowIndex, colIndex, ds, colorExp);
	}
	return '<a title=\'' + value.replace(/'/g, '&#39;') + '\'>' + value2 + '</a>';
}

/**
 * ��ɫ��Ⱦ
 * 
 * @param {}
 *            colorExp ��ɫ���ʽ����(aaa001==1)?'red':(aaa001==2)?'rgb(0,0,0)':''
 */
function renderColor(value, params, record, rowIndex, colIndex, ds, colorExp) {
	if (!ds) {// dsΪ�գ�������
		return value;
	}
	var gridId = ds.baseParams.div;
	var gridColumnModel = Ext.getCmp(gridId).getColumnModel();
	var field = gridColumnModel.getDataIndex(colIndex);
	for (var i = 0; i < gridColumnModel.getColumnCount(); i++) {
		var dataIndex = gridColumnModel.getDataIndex(i);
		if (dataIndex != null && dataIndex != '') {
			eval("var " + dataIndex + " = record.get('" + dataIndex + "')");
		}
	}
	var color = eval(colorExp);
	return '<span style="color:' + color + ';">' + value + '</span>';
}

/**
 * grid��ʽ�򹴿ؼ��Ĳ���
 */
function renderCheck(value, params, record, rowIndex, colIndex, ds) {
	var colName = Ext.getCmp(ds.baseParams.div).getColumnModel().getDataIndex(colIndex);
	if (getGridColumn(ds.baseParams.div, colName).editor) {
		alowCheck = "true";
	} else {
		alowCheck = "false";
	}
	var rtn = "";
	rtn += '<div class=\"x-grid-editor\">';
	rtn += '<div class=\"x-form-check-wrap\">';
	var id = "checkbox_" + ds.baseParams.div + "_" + colName + "_" + rowIndex;
	if (value == true || value == "true" || value == "1") {
		rtn += "<input type='checkbox' id='" + id + "' alowCheck='" + alowCheck + "' style='height=11px;x-form-checkbox x-form-field' onclick='doCheck(this," + rowIndex + "," + colIndex + ",\"" + colName + "\")' checked />";
	} else {
		rtn += "<input type='checkbox' id='" + id + "' alowCheck='" + alowCheck + "' style='height=11px;x-form-checkbox x-form-field' onclick='doCheck(this," + rowIndex + "," + colIndex + ",\"" + colName + "\")' />";
	}
	rtn += '</div></div>';
	return rtn;
}
/**
 * ��λ���롢��Ա���볬����
 */
function renderCommLink(value, params, record, rowIndex, colIndex, ds) {
	if (value == null || value == "") {
		return "";
	}
	var gridName = ds.baseParams.div;
	var colName = Ext.getCmp(gridName).getColumnModel().getDataIndex(colIndex);
	value = "<a href='#' class='render' onclick='doClick(\"renderCommLink\",\"" + Ext.encode(colName + "," + value).replace(/"/g, "\\\"") + "\"," + rowIndex + ")'>" + value + "</a>";
	return value;
}
/**
 * grid��ʽ�򹴿ؼ��Ĳ����ĸ�������
 */
function doCheck(item, rowIndex, colIndex, colName) {
	if (item.alowCheck == "false") {
		item.checked = !item.checked;
		return;
	}
	var e = {};
	var gridId = getParentDiv(colName);
	e.grid = Ext.getCmp(gridId);
	e.record = Ext.getCmp(gridId).getStore().getAt(rowIndex);
	e.field = colName;
	e.originalValue = !item.checked;
	e.value = item.checked;
	e.row = rowIndex;
	e.column = colIndex;
	if (Ext.getCmp(gridId).fireEvent('beforeedit', e) == false) {
		item.checked = !item.checked;
		return;
	}
	if (item.checked) {
		Ext.getCmp(gridId).getStore().getAt(rowIndex).set(colName, true);
	} else {
		Ext.getCmp(gridId).getStore().getAt(rowIndex).set(colName, false);
	}
	Ext.getCmp(gridId).fireEvent('afteredit', e);

}

/**
 * �Ե���ҳ��Ĳ���
 * 
 * @param value��ʽΪ����ʾ������,�Ƿ�������(1����0������),��ʾ�Ĵ���id,����1,����2... @
 *            �磺"����,1,win_1,1212,tttt..."
 */
function renderWindow(value, params, record, rowIndex, colIndex, ds) {
	if (value == null) {
		return;
	}
	var colName = Ext.getCmp(ds.baseParams.div).getColumnModel().getDataIndex(colIndex);
	var initParams;
	var valueArray = value.split(",");
	var showValue = valueArray[0]; // ��ʾ������
	var clickFlag = valueArray[1]; // �Ƿ�������
	var windowId = valueArray[2]; // ��ʾ�Ĵ���id
	if (valueArray.length > 3) {
		initParams = new Array(valueArray.length - 3);
	}
	for (var i = 3; i < valueArray.length; i++) {
		initParams[i - 3] = valueArray[i];
	}
	if (clickFlag == "1") {
		return "<a href='#' class='render' onclick='showWindow(\"" + windowId + "\",\"" + Ext.encode(initParams).replace(/"/g, "\\\"") + "\")'>" + showValue + "</a>";
	} else {
		return "<font color=#CCCCCC>" + showValue + "</font>";
	}

}

/**
 * �԰�ť�Ĳ���
 * 
 * @param value��ʽΪ����ʾ������,�Ƿ�������(1����0������),��ť����,����1,����2... @
 *            �磺"����,1,click1,1212,tttt..."
 */
function renderClick(value, params, record, rowIndex, colIndex, ds) {
	if (value == null) {
		return;
	}
	var colName = Ext.getCmp(ds.baseParams.div).getColumnModel().getDataIndex(colIndex);
	var clickParams;
	var valueArray = value.split(",");
	var showValue = valueArray[0]; // ��ʾ������
	var clickFlag = valueArray[1]; // �Ƿ�������
	var clickId = null; // ��ťid
	if (valueArray.length > 2) {
		clickId = valueArray[2]; // ��ťid
	}
	if (valueArray.length > 3) {
		clickParams = new Array(valueArray.length - 3);
	}
	for (var i = 3; i < valueArray.length; i++) {
		clickParams[i - 3] = valueArray[i];
	}
	if (clickFlag == "1") {
		if (clickId.indexOf("confirm") != -1) { // ��Ҫȷ�ϵİ�ť
			return "<a href='javascript:void(0)' class='render' onclick='odin.confirm(\"ȷ��Ҫ�Ա��н���" + showValue + "������\", function(btn) {" + " if (btn == \"ok\") {doClick(\"" + clickId + "\",\"" + Ext.encode(clickParams).replace(/"/g, "\\\"") + "\"," + rowIndex + ")}})'>" + showValue + "</a>";
		} else { // ����Ҫȷ�ϵİ�ť
			return "<a href='javascript:void(0)' class='render' onclick='doClick(\"" + clickId + "\",\"" + Ext.encode(clickParams).replace(/"/g, "\\\"") + "\"," + rowIndex + ")'>" + showValue + "</a>";
		}

	} else {
		return "<font color=#CCCCCC>" + showValue + "</font>";
	}

}

/**
 * �԰�ť�Ĳ���,�ύǰ����зǿա�����������У��
 * 
 * @param value��ʽΪ����ʾ������,�Ƿ�������(1����0������),��ť����,����1,����2... @
 *            �磺"����,1,click1,1212,tttt..."
 */
function renderClick2(value, params, record, rowIndex, colIndex, ds) {
	if (value == null) {
		return;
	}
	var colName = Ext.getCmp(ds.baseParams.div).getColumnModel().getDataIndex(colIndex);
	var clickParams;
	var valueArray = value.split(",");
	var showValue = valueArray[0]; // ��ʾ������
	var clickFlag = valueArray[1]; // �Ƿ�������
	var clickId = null; // ��ťid
	if (valueArray.length > 2) {
		clickId = valueArray[2]; // ��ťid
	}
	if (valueArray.length > 3) {
		clickParams = new Array(valueArray.length - 3);
	}
	for (var i = 3; i < valueArray.length; i++) {
		clickParams[i - 3] = valueArray[i];
	}
	if (clickFlag == "1") {
		if (clickId.indexOf("confirm") != -1) { // ��Ҫȷ�ϵİ�ť
			return "<a href='#' class='render' onclick='odin.confirm(\"ȷ��Ҫ�Ա��н���" + showValue + "������\", function(btn) {" + " if (btn == \"ok\") {doClick2(\"" + clickId + "\",\"" + Ext.encode(clickParams).replace(/"/g, "\\\"") + "\",null," + rowIndex + ")}})'>" + showValue + "</a>";
		} else { // ����Ҫȷ�ϵİ�ť
			return "<a href='#' class='render' onclick='doClick2(\"" + clickId + "\",\"" + Ext.encode(clickParams).replace(/"/g, "\\\"") + "\",null," + rowIndex + ")'>" + showValue + "</a>";
		}
	} else {
		return "<font color=#CCCCCC>" + showValue + "</font>";
	}
}

/**
 * ��ʾ�� �ٷֱ�
 */
function renderPercent(value, params, record, rowIndex, colIndex, ds) {
	if (value == null) {
		return "0%";
	}
	try {
		return accMul(value, 100) + "%";
	} catch (e) {
		return "invalid value:" + value;
	}
}

/**
 * ��ʾ�� �ٷֱȻ�ǰٷֱ� <1��>-1Ϊ�ٷֱȣ�>1��<-1Ϊ�ǰٷֱ�
 */
function renderPercentOrNot(value, params, record, rowIndex, colIndex, ds) {
	if (value < 1 && value > -1) {
		return renderPercent(value, params, record, rowIndex, colIndex, ds);
	} else {
		return value;
	}
}

/**
 * ��ʾ����
 */
function showWindow(windowId, initParams) {
	if (document.getElementById("iframe_" + windowId)) {
		document.getElementById("iframe_" + windowId).src = document.getElementById("iframe_" + windowId).src;
	}
	if (initParams != null) {
		commParams.initParams = initParams;
		commParams.initParamsUsedBy = null;
	}
	window.setTimeout('Ext.getCmp(\"' + windowId + '\").show(Ext.getCmp(\"' + windowId + '\"))', 200);
}

/**
 * ���ݴ���ĵ�ַ��ʾ����
 */
function showWindowWithSrc(windowId, newSrc, initParams, onload) {
	if (newSrc.substring(0, 1) == "&") {
		newSrc = addUrlParam(window.location.href, newSrc);
	}
	if (newSrc.indexOf("http:") == -1 && newSrc.indexOf("www.") == -1 && newSrc.indexOf(contextPath) != 0) {
		newSrc = contextPath + newSrc;
	}
	if (initParams != null) {
		newSrc = addUrlParam(newSrc, "initParams=" + initParams);
		commParams.initParams = initParams;
		commParams.initParamsUsedBy = null;
	}
	newSrc = addUrlParam(newSrc, "clientDate=" + Ext.util.Format.date(new Date(), "YmdHis"));// ʹÿ�����󶼲�һ�����������һ���򲻿�������
	newSrc = encodeURI(newSrc);
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
 * �򿪶���ĵ�������
 */
function doOpenPupWinOnTop(src, title, width, height, initParams, parentWin) {
	if (parentWin == null) {
		parentWin = this;
	}
	if (parent != this && parent.doOpenPupWinOnTop) {
		parent.doOpenPupWinOnTop(src, title, width, height, initParams, parentWin);
	} else {
		doOpenPupWin(src, title, width, height, initParams, parentWin);
	}
}

/**
 * ���ض���ĵ�������
 */
function doClosePupWinOnTop() {
	if (parent != this && parent.doClosePupWinOnTop) {
		parent.doClosePupWinOnTop();
	} else {
		doHiddenPupWin();
	}

}
/**
 * ȡ�ö��㴰��
 */
function getTopParent() {
	if (parent != this && parent.getTopParent) {
		return parent.getTopParent();
	} else {
		return this;
	}
}
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
	showWindowWithSrc(windowId, src, initParams, "doPupWinOnload(this)");
	pupWindow.center(); // ����
}
/**
 * ��ʾ���ڼ�����ɴ������¼�
 */
function doPupWinOnload(iframe) {
	try {
		getIFrameWin(iframe.id).setParentWin(tempParentWin);
	} catch (e) {
	}
}
/**
 * �򿪱���������
 */
function doOpenThisPupWin(src, title, width, height, initParams) {
	var windowId = "win_pup";
	var pupWindow = parent.Ext.getCmp(windowId);
	expReadyState = ""; // ����ʱ��ʼ��
	pupWindow.setTitle(title); // ����
	if (width < 1) {
		if (width >= 0) {// С��
			width = window.screen.availWidth * width;
		} else { // ����
			width = window.screen.availWidth + width;
		}
	}
	if (height < 1) {
		if (height >= 0) {// С��
			height = window.screen.availHeight * height;
		} else { // ����
			height = window.screen.availHeight + height;
		}
	}
	pupWindow.setSize(width, height); // ��� �߶�
	parent.showWindowWithSrc(windowId, src, initParams);
	pupWindow.center(); // ����
}

/**
 * �򿪵������ڲ�����
 */
function doOpenPupWinHidden(src, initParams) {
	var windowId = "win_pup";
	var pupWindow = Ext.getCmp(windowId);
	showWindowWithSrc(windowId, src, initParams);
	pupWindow.hide();
}

/**
 * �����ӵ�������
 */
function doHiddenPupWin() {
	var windowId = "win_pup";
	var pupWindow = Ext.getCmp(windowId);
	pupWindow.hide();
}

/**
 * ���ر���������
 */
function doHiddenThisPupWin() {
	var windowId = "win_pup";
	var pupWindow = parent.Ext.getCmp(windowId);
	pupWindow.hide();
}

/**
 * �򿪵������ڲ�����
 */
function doShowPupWin() {
	var windowId = "win_pup";
	var pupWindow = Ext.getCmp(windowId);
	pupWindow.show();
	pupWindow.focus();
}

/**
 * ���û����ĳ�е���ʾ��Ϣ
 */
function setValid(column, isValid, errmsg) {
	if (column == null || column == "") {
		return;
	}
	if (!Ext.getCmp(column) && Ext.getCmp(column + "_combo")) {
		column = column + "_combo";
	}
	if (Ext.getCmp(column)) {
		if (isValid) {
			// Ext.getCmp(column).invalidText = null; ����Ҫ����������У���������
			Ext.getCmp(column).validator = getTrue;
			Ext.getCmp(column).clearInvalid();
		} else {
			Ext.getCmp(column).invalidText = errmsg;
			Ext.getCmp(column).validator = getFalse;
			Ext.getCmp(column).clearInvalid();
			Ext.getCmp(column).markInvalid();
		}
	}
}

/**
 * ˢ��
 */
function doReset() {
	odin.confirm("ȷ��Ҫˢ�½��棿", function(clicked) {
				if (clicked == "ok") {
					reflush();
				}
			});
}

/**
 * ��url�Ӳ���
 * 
 * @param {}
 *            url ԭʼurl
 * @param {}
 *            paramUrl ����url���� &a=1&b=2
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
	// alert(newUrl);
	return newUrl;
}
/**
 * ˢ��ҳ��
 */
function reflush(url, initParams) {
	if (initParams != null) {
		parent.commParams.initParams = initParams;
		parent.commParams.initParamsUsedBy = null;
		parent.commParams.initParamsUsedOnce = true;
	}
	if (url == null || url == "") {
		window.location.reload();
	} else {
		if (url.substring(0, 1) == "&") {
			url = addUrlParam(window.location.href, url);
		}
		window.location.href = url;
	}
	// odin.reset();
}

/**
 * ˢ��ҳ��
 */
function reflushThis(initParams) {
	reflush(null, initParams);
}

/**
 * ���ñ�����Ƿ������ʾ��ˢ��
 */
function setReflushAfterSave(flag) {
	reflushAfterSave = flag;
}

/**
 * ���ñ���������ʾ����Ϣ
 */
function setReflushAfterSaveMsg(msg) {
	reflushAfterSaveMsg = msg;
}

/**
 * ���ñ�����Ƿ������ʾ��ˢ��
 */
function setReflushAfterSavePrint(msg) {
	reflushAfterSavePrint = msg;
}

/**
 * ȡ��ϵͳ����
 * 
 * @return {}
 */
function getSysType() {// sionline Ϊ�����걨�� insiis
	return 'insiis';
}

/**
 * grid���޸������ύ
 */
function doGridCommitChanges() {
	var theForm = document.commForm;
	// ȡgrid��ʽ������
	var elList = theForm.getElementsByTagName("input");
	for (var i = 0; i < elList.length; i++) {
		if (elList.item(i).name && elList.item(i).name.indexOf("-") == -1) {
			if (elList.item(i).name.indexOf("Data") > 0 && document.getElementById("gridDiv_" + elList.item(i).name.substr(0, elList.item(i).name.length - 4)) != null) {
				var gridId = elList.item(i).name.substr(0, elList.item(i).name.length - 4);
				try {
					Ext.getCmp(gridId).store.commitChanges(); // �ύ
				} catch (e) {

				}
			}
		}
	}
}

/**
 * ��̨�����ɹ���Ķ���
 */
function doSuccess(response) {
	// ��ʼ��
	reflushAfterSave == true;
	var theCommParams = Ext.util.JSON.decode(response.data.hashMap.commParams)[0]; // �����첽ģʽ�������ڷ��ز�����ȡ�ع�������ֵ
	commParams = theCommParams;
	if (theCommParams.currentEvent == "onchange") { // onchange�¼�
		clearInvalid(document.commForm); // �ɹ����Ƚ�����Ĵ�����Ϣ���������Ҫ��������������ʾ
	}
	if (theCommParams.currentEvent == "save") { // ����
		opPage(response); // ��ҳ����������ܶ�reflushAfterSave�����޸�
	}
	if (theCommParams.currentEvent == "save" && reflushAfterSave == false) {// ��������grid���޸ı�־
		doGridCommitChanges();
	}
	if (theCommParams.currentEvent == "commImp") { // ͨ�õ���
		getIFrameWin("iframe_win_pup").doCloseWin();
	}
	if (theCommParams.currentEvent == "save" && reflushAfterSave == true) {// ���沢ˢ�½���
		if (getSysType() == "sionline") { // �����걨
			var altmsg = "����ɹ���������������ɺ��뵽�ݸ�������걨��";
			if (reflushAfterSaveMsg != null && reflushAfterSaveMsg != "") {
				altmsg = reflushAfterSaveMsg;
			}
			odin.info(altmsg, function() {
						reflush();
					});
		} else { // ҵ��ϵͳ
			var altmsg = "����ɹ���";
			if (reflushAfterSaveMsg != null && reflushAfterSaveMsg != "") {
				odin.info(reflushAfterSaveMsg, function() {
							reflush();
						});
			} else if (reflushAfterSavePrint != null && reflushAfterSavePrint != "") {
				if (reflushAfterSavePrint == "#") { // ����ʾֱ�Ӵ�ӡ
					setFormData(response); // ��ֵ
					doPrint(); // ��ӡ
					doSubmitFromWaiting(); // �ύ�ȴ����¼�
				} else {
					odin.confirm(reflushAfterSavePrint, function(btn) {
								if (btn == "ok") { // ȷ��
									setFormData(response); // ��ֵ
									doPrint(); // ��ӡ
									doSubmitFromWaiting(); // �ύ�ȴ����¼�
								} else { // ȡ��
									reflush();
								}
							});
				}
			} else {
				odin.mask(altmsg); // ������Ӱ
				window.setTimeout("odin.unmask()", 2000);// ȥ����Ӱ
				window.setTimeout("reflush()", 1000); // ҳ��ˢ��
			}
		}
	} else {// ���Ǳ��沢ˢ�µ����
		if (response.data.hashMap.divsql) {
			divsql = Ext.util.JSON.decode(response.data.hashMap.divsql)[0]; // ���淵�ص�sql
		}
		setFormData(response); // ��ֵ
		if (currentOpseno == null) { // �ݸ��䲻����
			setPageGridStore(response); // ��grid��ʽ��ֵ
		}
		if (theCommParams.currentEvent != "save") { // ��������ǰ�����
			opPage(response); // ��ҳ�����
		}
		showMsgBox(response); // ��ʾ��Ϣ
		if (document.getElementById(theCommParams.currentColumn) != null) {
			setValid(theCommParams.currentColumn, true);// ̾����ʾ��Ϣ���
		}
		// ���ý���
		if (!commParams.hasSetFocus) {
			if (odin.lastEvent && (odin.lastEvent.keyCode == 9 || odin.lastEvent.keyCode == 13)) {// tab��س�
				if (document.getElementById(theCommParams.currentColumn) != null) {// ���õ�ǰ����
					setFocusNext(theCommParams.currentColumn); // ���õ�ǰ����
				} else if (isGridDiv(theCommParams.currentDiv)) { // grid���õ�ǰ����
					var editor = getGridCellEditor(theCommParams.currentDiv, theCommParams.currentColumn, theCommParams.currentRow);
					var grid = Ext.getCmp(theCommParams.currentDiv);
					var newCell = grid.walkCells(theCommParams.currentRow, editor.col + 1, 1, grid.getSelectionModel().acceptsNav, editor);
					if (newCell != null) {
						if (grid.activeEditor) {
							field = grid.activeEditor.field;
							if (field.getXType() == "combo") { // ���������⴦��
								field.triggerBlur();
							}
						}
						grid.startEditing(newCell[0], newCell[1]);
					}
				}
			}
			commParams.hasSetFocus = false; // ��������
		}
		// if (document.activeElement != null && document.activeElement.name !=
		// null && document.activeElement.name != "") { // ���õ�ǰ����
		// setFocus(document.activeElement.name);
		// }
		if (theCommParams.currentEvent == "afterImp") { // �����Ĳ���
			doImpSuccess();
		}
		doBillPrint(response); // ���ݴ�ӡ
		doSubmitFromWaiting(); // �ύ�ȴ����¼�
		if (theCommParams.currentEvent == "init") { // ��ʼ��
			if (parent && parent._autoSaveCommScenForm && parent._autoSaveCommScenForm()) { // �Զ����泡��Form
				doSaveCommScenForm();
			} else { // ��ͨ
				// ���ý��㣬���԰�F9��ݼ�
				focus();
				// ���ý��㵽��һ����
				var inputList = document.commForm.getElementsByTagName("input");
				for (var i = 0; i < inputList.length; i++) {
					var input = inputList.item(i);
					// alert(input.name);
					if (setFocus(input.name, true) == true) {
						break;
					}
				}		
				if (currentOpseno != null) {
					doQuery();
				}
			}
		} else if (theCommParams.currentEvent == "query" && MDParam.location.indexOf("commRep2Action.do") != -1) {// �����߲�ѯ
			pagePaneLoad();
		} else if ((theCommParams.currentEvent == "print" || theCommParams.currentEvent == "exp") && MDParam.location.indexOf("commRep2Action.do") != -1) {// �����ߴ���
			eval(pagePaneOpstr);
			
		} else if (theCommParams.currentEvent == "saveCommScenForm") { // ���泡��Form���¼�
			if (parent && parent.doAfterSaveCommScenForm) {
				parent.doAfterSaveCommScenForm('������ʼ���ɹ���');
			}
		}
	}
}

/**
 * ��̨����ʧ�ܺ�Ķ���
 */
function doFailure(response, theCommParams) {
	try {
		if (theCommParams != null) {
			commParams = theCommParams;
		} else if (doSubmitStatus != null && doSubmitStatus.submiting != null && doSubmitStatus.submiting.commParams != null) {
			commParams = doSubmitStatus.submiting.commParams;
		}
		// query�����⴦��
		if (isQuery) {
			parent.odin.unmask(); // ȥ����Ӱ
			parent.doQuerySelect(getGridNullJson(isQueryDiv));
			return parent.doFailure(response, parent.commParams);
		}
		var errmsg = response.mainMessage;
		if (errmsg.indexOf("doConfirm") == 0) { // ȷ�ϵ�����
			eval(odin.toHtmlString(errmsg));
			return;
		}
		if (response.detailMessage != "") {
			errmsg = errmsg + "\n��ϸ��Ϣ��" + response.detailMessage;
		}
		if (commParams.currentColumn != null && commParams.currentColumn != "searchText") { // ��ʾ������Ϣ
			setValid(commParams.currentColumn, false, errmsg);
		}
		var addmsg = "";
		if (isGridDiv(commParams.currentDiv)) {
			addmsg = "�����������룡";
			Ext.getCmp(commParams.currentDiv).stopEditing();
		}
		odin.error(errmsg + addmsg, function() {
					if (isGridDiv(commParams.currentDiv)) {
						Ext.getCmp(commParams.currentDiv).store.getAt(commParams.currentRow).set(commParams.currentColumn, commParams.currentOriginalValue);
						startEditing(commParams.currentDiv, commParams.currentColumn, commParams.currentRow);
					} else {
						setFocus(commParams.currentColumn);
					}
				});
		if (theCommParams && theCommParams.currentEvent == "saveCommScenForm") { // ���泡��Form���¼�
			if (parent && parent.doAfterSaveCommScenForm) {
				parent.doAfterSaveCommScenForm(errmsg);
			}
		}
	} finally {
		doSubmitStatus.waiting = null; // ��յȴ����¼�
		doSubmitStatus.isSubmiting = false;
	}
}

/**
 * ��Ҫȷ�ϵ���ʾ��Ϣ
 * 
 * @param {}
 *            msg
 */
function doConfirm(key, msg, continueFlag) {
	if (key == null) {
		key = msg;
	}
	odin.confirm(msg, function(btn) {
				if (commParams.confirmRet == null) {
					commParams.confirmRet = {};
				}
				eval("commParams.confirmRet." + key + " = '" + btn + "'");
				if ((continueFlag == false && btn == "ok") || (continueFlag == true && btn == "cancel")) {
					return;
				}
				var submiting = doSubmitStatus.submiting;
				doSubmit(submiting.theForm, submiting.successFun, submiting.failureFun);
			});
}

/**
 * ���ý���
 */
function setFocus(column, isInit) {
	if (document.activeElement != null && document.activeElement.name != null && document.activeElement.name != "") { // ȥ����ǰ����
		if (document.activeElement.name.indexOf("_combo") != -1) {// ���������⴦��
			Ext.getCmp(document.activeElement.name).triggerBlur();
		}
	}
	if (column == null || column == "") {
		return;
	}
	try {
		if (Ext.getCmp(column + "_combo")) { // ���������⴦��
			if (isInit) { // ��ʼ�����⴦��
				eval("comboSetFocusForInit_" + column + "_combo" + "=true"); // ������������ʾ��־
			}
			Ext.getCmp(column + "_combo").focus();
		}
		if (typeof(column) == "string") {
			if (Ext.isIE) {
				document.getElementById(column).focus();
			} else {
				if (document.getElementById(column).type != 'hidden' && !document.getElementById(column).disabled) {
					document.getElementById(column).focus();
				} else {
					return false;
				}
			}
		} else {
			column.focus();
		}
		return true;
	} catch (e) {
		return false;
	}
}
/**
 * ���ý��㵽��һ������
 */
function setFocusNext(column) {
	try {
		var obj = document.getElementById(column);
		var el = odin.getNextElement(obj);
		if (document.getElementById(column + "_combo")) {// ���������⴦��
			obj = el;
			el = odin.getNextElement(obj);
		}
		if (el) {
			if (document.getElementById(column + "_combo") && Ext.getCmp(column + "_combo").hasFocus) {// ���������⴦��
				Ext.getCmp(column + "_combo").triggerBlur();
			} else if (Ext.getCmp(column) && Ext.getCmp(column).getXType().indexOf("date") != -1 && Ext.getCmp(column).hasFocus) { // ���ڿ����⴦��
				Ext.getCmp(column).triggerBlur();
			}
			try {
				if (el.name.indexOf("_combo") != -1) { // ���������⴦�����һЩbug
					var cmp = Ext.getCmp(el.name);
					if (cmp) {
						odin.comboFocus(cmp);
						// cmp.focus();
					}
				} else {

				}
			} catch (e) {
			}
			el.focus();
		} else {
			obj.blur();
		}
	} catch (e) {
		return false;
	}
}

/**
 * �õ�false�ĺ���
 */
function getFalse() {
	return false;
}

/**
 * �õ�true�ĺ���
 */
function getTrue() {
	return true;
}

/**
 * ȡ����Ŀ����������
 */
function getFieldType(fieldName) {
	if (Ext.getCmp(fieldName) == null) {
		return "string";
	}
	var xtype = Ext.getCmp(fieldName).getXType();
	return toType(xtype);
}

/**
 * ȡ��Ŀ�ı༭����
 */
function getREDType(fieldName) {
	var obj = Ext.getCmp(fieldName);
	var el = Ext.getDom(fieldName);
	if (fieldName.indexOf("_combo") != -1) {
		return "N";
	}
	if (obj || el) {
		if (obj && obj.getSize().height == 0 && obj.getSize().width == 0) {
			return "H";
		} else if (el.required && (el.required == 'true' || el.required == true)) {
			return "R";
		} else if (el.disabled && (el.disabled == 'true' || el.disabled == true)) {
			return "D";
		} else if (!el.disabled || (el.disabled == 'false')) {
			return "E";
		} else {
			return "N";
		}
	} else {
		return "N";
	}
}
/**
 * ȡ��Ŀ��grid�༭����
 */
function gridRED(column) {
	if (column.hidden && (column.hidden == 'true' || column.hidden == true)) {
		return "H";
	} else if (column.required && (column.required == 'true' || column.required == true)) {
		return "R";
	} else if (column.editable && (column.editable == 'true' || column.editable == true)) {
		return "E";
	} else if (!column.editable || column.editable == "false") {
		return "D";
	} else {
		return "N";
	}
}

/**
 * ȡ��grid���е���������
 */
function getGridFieldType(gridName, fieldName) {
	var column = getGridColumn(gridName, fieldName);
	if (column && column.editor) {
		var xtype = column.editor.field.getXType();
	}
	return toType(xtype);
}

/**
 * ȡ��grid����
 */
function getGridColumn(gridName, fieldName) {
	if (gridName == null || !Ext.getCmp(gridName)) {
		return;
	}
	try {
		var gridColumnModel = Ext.getCmp(gridName).getColumnModel();
	} catch (e) {
		return;
	}
	if (!gridColumnModel) {
		return;
	}
	for (var j = 0; j < gridColumnModel.getColumnCount(); j++) {
		if (gridColumnModel.getDataIndex(j) == fieldName) {
			var column = gridColumnModel.getColumnById(gridColumnModel.getColumnId(j));
			break;
		}
	}
	return column;
}

/**
 * ȡ��grid��������
 */
function getGridColumnIndex(gridName, fieldName) {
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
}

/**
 * ȡ��gridĳ��Ԫ��ı༭��
 */
function getGridCellEditor(gridName, fieldName, row) {
	if (gridName == null || !Ext.getCmp(gridName)) {
		return;
	}
	try {
		var gridColumnModel = Ext.getCmp(gridName).getColumnModel();
	} catch (e) {
		return;
	}
	if (!gridColumnModel) {
		return;
	}
	for (var j = 0; j < gridColumnModel.getColumnCount(); j++) {
		if (gridColumnModel.getDataIndex(j) == fieldName) {
			var column = gridColumnModel.getCellEditor(j, row)
			break;
		}
	}
	return column;
}

/**
 * Ext��xtypeת����type��number��date��string
 */
function toType(xtype) {
	if (xtype == null) {
		return "string";
	}
	if (xtype.indexOf("number") >= 0) {
		return "number";
	} else if (xtype.indexOf("date") >= 0) {
		return "date";
	} else {
		return "string";
	}
}

/**
 * ͨ������ȡ�ö���֧��comboֱ��ȡ
 */
function getCmpByName(name) {
	var obj = null;
	obj = Ext.getCmp(name);
	if (obj == null) {
		obj = Ext.getCmp(name + "_combo");
	}
	return obj;
}

/**
 * ����Ƿ��д��������
 */
function checkValid(theForm) {
	var checkFlag = -1;
	if (theForm == null || theForm == 0 || theForm == 1 || theForm == 2) {
		checkFlag = (theForm == null ? 0 : theForm);
		theForm = document.commForm;
	}
	var eles = theForm.elements;
	var errtitle = "<b>������������������ٽ��б�������</b><br>";
	for (i = 0; i < eles.length; i++) {
		var obj = eles[i];
		odin.cueCheckObj = obj;
		// У��ǿ�
		if (obj.getAttribute("required") == "true" && (checkFlag == 0 || checkFlag == 1 || commParams.currentEvent == "save" || commParams.currentEvent == "query")) {
			if (Ext.getCmp(obj.name)) { // ��� ckeditorȡֵ����
				Ext.getCmp(obj.name).getValue();
			}
			if (obj.value == "") { // �ǿ��ж�
				odin.error(errtitle + obj.getAttribute("label") + "����Ϊ�գ�", odin.doFocus);
				return false;
			}
		}
		// У���������Ƿ����Ҫ��
		// �ǿջ�������
		// �����ǵ�ǰ��
		// ������Ϊ��ʼֵ����Ҫ���"����ѡ��..."������
		if (((obj.value != null && obj.value != "") || (obj.name != null && obj.name != "" && document.getElementById(obj.name + "_combo") && document.getElementById(obj.name + "_combo").value != null && document.getElementById(obj.name + "_combo").value != "")) && (checkFlag == 0 || checkFlag == 2 || commParams.currentEvent == "save" || commParams.currentEvent == "query" || obj.name != commParams.currentColumn) && !(obj.name.indexOf("_combo") >= 0 && obj.value != getCmpByName(obj.name).defaultValue)) {
			var eObj = getCmpByName(obj.name);
			if (eObj) {
				if (!eObj.isValid(false)) {
					odin.error(errtitle + '��' + obj.getAttribute("label") + '���������ֵ������Ҫ�����������룡', odin.doFocus);
					return false;
				}
			}
		}
	}
	return true;
}

/**
 * ����Ƿ��д��������
 */
function clearInvalid(theForm) {
	var eles = theForm.elements;
	for (i = 0; i < eles.length; i++) {
		var obj = eles[i];
		setValid(obj.name, true);
	}
}

// �ύ�Ķ��У�Ŀǰ������isSubmiting��submiting��waiting��Ϊ�˽�������������δ���س��͵㱣�水ť���ֵ�����
var doSubmitStatus = {};
/**
 * �ӵȴ��Ķ����¼�������ύ
 */
function doSubmitFromWaiting() {
	doSubmitStatus.isSubmiting = false;
	if (doSubmitStatus.waiting != null && doSubmitStatus.waiting.length > 0) {
		if (doSubmitStatus.submiting != null && doSubmitStatus.submiting.commParams.currentEvent == "save") { // ��һ��Ϊ����
			while (doSubmitStatus.waiting[0].commParams.currentEvent == "save") {// �������α��水ť
				doSubmitStatus.waiting.shift(0);
				if (doSubmitStatus.waiting.length = 0) {
					return;
				}
			}
		}
		var submiting = doSubmitStatus.waiting[0];
		doSubmitStatus.waiting.shift(0);
		commParams = submiting.commParams;
		doSubmit(submiting.theForm, submiting.successFun, submiting.failureFun);
	}
}
/**
 * �����ύ���Ͳ���
 */
function doSubmit(theForm, successFun, failureFun) {// Ajax��ʽ�ύ����
	// doSubmitQueue(commParams, theForm, successFun, failureFun);
	if (doSubmitStatus.isSubmiting == null || doSubmitStatus.isSubmiting == false) { // û�������ύ���¼�
		doSubmitStatus.isSubmiting = true;
		var submiting = {};
		submiting.commParams = commParams;
		submiting.theForm = theForm;
		submiting.successFun = successFun;
		submiting.failureFun = failureFun;
		doSubmitStatus.submiting = submiting;
	} else {// ���������ύ���¼�����ȴ�
		if (commParams.currentEvent == "save") { // ���Ʋ��������α��水ť
			if (doSubmitStatus.submiting.commParams.currentEvent == "save") {
				return;
			}
			if (doSubmitStatus.waiting != null) {
				for (i = 0; i < doSubmitStatus.waiting.length; i++) {
					if (doSubmitStatus.waiting[i].commParams.currentEvent == "save") {
						return;
					}
				}
			}
		}
		var submiting = {};
		submiting.commParams = commParams;
		submiting.theForm = theForm;
		submiting.successFun = successFun;
		submiting.failureFun = failureFun;
		if (doSubmitStatus.waiting == null) {
			doSubmitStatus.waiting = new Array();
		}
		doSubmitStatus.waiting.push(submiting);
		return;
	}
	// alert('doSubmit:'+commParams.currentEvent);
	var params = {};
	var exp = "";
	var typeExp = "";
	// ��У��
	if (commParams.currentEvent != "init" && commParams.currentEvent != "click") { // У�鴦��
		if (checkValid(theForm) != true) { // ��У���ж�
			if (commParams.currentColumn != null) {
				if (commParams.currentEvent == "onchange") {
					var cmp = getCmpByName(commParams.currentColumn);
					cmp.checkSelectComplete = false;
					cmp.setValue(cmp.startValue);
				} else if (commParams.currentEvent == "afteredit") {
					var record = Ext.getCmp(commParams.currentDiv).getStore().getAt(commParams.currentRow);
					record.set(commParams.currentColumn, commParams.currentOriginalValue == null ? '' : commParams.currentOriginalValue);
				}
			}
			doSubmitStatus = {};
			return;
		}
	}

	// �������е�����ת���ɲ�������
	// ȡ��grid��ʽ������
	var divList = theForm.getElementsByTagName("div");

	for (var i = 0; i < divList.length; i++) {
		if (Ext.util.JSON.encode(divList.item(i).id).indexOf("div") > 0) {
			// alert(Ext.util.JSON.encode(divList.item(i).id));
			var tempExp = "";
			var tempTypeExp = "";
			var tempColNameExp = "";
			var tempREDHExp = "";
			for (var n = 0; n < tagNames.length; n++) {

				var inputList = divList.item(i).getElementsByTagName(tagNames[n]);
				for (var j = 0; j < inputList.length; j++) {
					if (inputList.item(j).name && inputList.item(j).name.indexOf("-") == -1) {
						var inputItem = inputList.item(j);
						var tempValue = inputItem.value;
						if (inputItem.type == "checkbox") {
							tempValue = inputItem.checked;
						}
						if (Ext.getCmp(inputItem.name)) { // ��ǩ����
							tempValue = Ext.getCmp(inputItem.name).getValue();
						}
						tempExp = tempExp + ",\"" + inputItem.name + "\":" + Ext.util.JSON.encode(tempValue);
						tempTypeExp = tempTypeExp + ",\"" + inputItem.name + "\":\"" + getFieldType(inputItem.name) + "\"";
						tempColNameExp = tempColNameExp + ",\"" + inputItem.name + "\":\"" + (inputItem.getAttribute("label") == null ? "" : inputItem.getAttribute("label")) + "\"";
						var redh = getREDType(inputItem.name);
						if (redh != 'N') {
							tempREDHExp = tempREDHExp + ",\"" + inputItem.name + "\":\"" + redh + "\"";
						}
					}
				}
				if (tempExp != "") {
					exp = exp + ",'divForStrust(" + divList.item(i).id.replace(/'/g, '\\\'') + ")':'[{" + tempExp.substr(1).replace(/'/g, '\\\'') + "}]'";
				}
				if (tempTypeExp != "") {
					typeExp = typeExp + ",'divForStrust(types_" + divList.item(i).id.replace(/'/g, '\\\'') + ")':'[{" + tempTypeExp.substr(1).replace(/'/g, '\\\'') + "}]'";
				}
				if (tempColNameExp != "") {
					typeExp = typeExp + ",'divForStrust(labels_" + divList.item(i).id.replace(/'/g, '\\\'') + ")':'[{" + tempColNameExp.substr(1).replace(/'/g, '\\\'') + "}]'";
				}
				if (tempREDHExp != "") {
					typeExp = typeExp + ",'divForStrust(REDH_" + divList.item(i).id.replace(/'/g, '\\\'') + ")':'[{" + tempREDHExp.substr(1).replace(/'/g, '\\\'') + "}]'";
				}
			}

		}
	}
	// ȡgrid��ʽ������
	var elList = theForm.getElementsByTagName("input");
	tempExp = "";
	var tempSelectAllExp = "";
	for (var i = 0; i < elList.length; i++) {
		tempTypeExp = "";
		tempSelectAllExp = "";
		tempColNameExp = "";
		tempREDHExp = "";
		if (elList.item(i).name && elList.item(i).name.indexOf("-") == -1) {
			if (elList.item(i).name.indexOf("Data") > 0 && document.getElementById("gridDiv_" + elList.item(i).name.substr(0, elList.item(i).name.length - 4)) != null) {
				var gridId = elList.item(i).name.substr(0, elList.item(i).name.length - 4);
				odin.getGridJsonData(gridId, elList.item(i).name);
				exp = exp + ",'divForStrust(" + gridId.replace(/'/g, '\\\'') + ")':'" + elList.item(i).value.replace(/'/g, '\\\'') + "'";
				// ȡgrid������
				var gridColumnModel = Ext.getCmp(gridId).getColumnModel();
				for (var j = 0; j < gridColumnModel.getColumnCount(); j++) {
					var dataIndex = gridColumnModel.getDataIndex(j);
					var column = gridColumnModel.getColumnById(gridColumnModel.getColumnId(j));
					if (column.editor) {
						tempTypeExp = tempTypeExp + ",\"" + dataIndex + "\":\"" + toType(column.editor.field.getXType()) + "\"";
						tempColNameExp = tempColNameExp + ",\"" + dataIndex + "\":\"" + (gridColumnModel.getColumnHeader(j) == null ? "" : gridColumnModel.getColumnHeader(j).replace(/"/g, '\\\"').replace(/,/g, "��")) + "\"";
						tempREDHExp = tempREDHExp + ",\"" + dataIndex + "\":\"" + gridRED(column) + "\"";
					} else if (dataIndex != "") {
						tempTypeExp = tempTypeExp + ",\"" + dataIndex + "\":\"" + "string" + "\"";
						tempColNameExp = tempColNameExp + ",\"" + dataIndex + "\":\"" + (gridColumnModel.getColumnHeader(j) == null ? "" : gridColumnModel.getColumnHeader(j).replace(/"/g, '\\\"').replace(/,/g, "��")) + "\"";
						tempREDHExp = tempREDHExp + ",\"" + dataIndex + "\":\"" + gridRED(column) + "\"";
					}
					var selectallcol = "selectall_" + gridId + "_" + dataIndex;
					if (document.getElementById(selectallcol)) {
						tempSelectAllExp = tempSelectAllExp + ",\"" + dataIndex + "\":" + document.getElementById(selectallcol).checked + "";
					}
				}
				if (tempTypeExp != "") {
					typeExp = typeExp + ",'divForStrust(types_" + gridId.replace(/'/g, '\\\'') + ")':'[{" + tempTypeExp.substr(1).replace(/'/g, '\\\'') + "}]'";
				}
				if (tempColNameExp != "") {
					typeExp = typeExp + ",'divForStrust(labels_" + gridId.replace(/'/g, '\\\'') + ")':'[{" + tempColNameExp.substr(1).replace(/'/g, '\\\'') + "}]'";
				}
				if (tempSelectAllExp != "") {
					typeExp = typeExp + ",'divForStrust(selectall_" + gridId + ")':'[{" + tempSelectAllExp.substr(1).replace(/'/g, '\\\'') + "}]'";
				}
				if (tempREDHExp != "") {
					typeExp = typeExp + ",'divForStrust(REDH_" + gridId + ")':'[{" + tempREDHExp.substr(1).replace(/'/g, '\\\'') + "}]'";
				}
			}
		}
	}
	commParams.currentOpseno = currentOpseno;
	commParams.currentLoginName = currentLoginName;
	commParams.currentActionDisabled = currentActionDisabled;
	// ȡ�õ�ǰ�¼������Ϣ
	if (commParams != null) {
		exp = exp + ",'divForStrust(commParams)':'[" + Ext.util.JSON.encode(commParams).replace(/'/g, '\\\'') + "]'";
	}
	// ȡ�˵�����
	if (MDParam != null) {
		exp = exp + ",'divForStrust(MDParams)':'[" + Ext.util.JSON.encode(MDParam).replace(/'/g, '\\\'') + "]'";
	}
	// ȡdiv��sql
	if (divsql != null && Ext.encode(divsql) != "{}") {
		exp = exp + ",'divForStrust(divsql)':'[" + Ext.util.JSON.encode(divsql).replace(/'/g, '\\\'') + "]'";
	}
	// ȡdiv�ķ�ҳ����
	var divList = theForm.getElementsByTagName("div");
	var tempPagetypeExp = "";
	for (var i = 0; i < divList.length; i++) {
		var divId = divList.item(i).id;
		if (divId.indexOf("div") == 0) {
			var divType = getDivType(divId);
			tempPagetypeExp = tempPagetypeExp + ",\"" + divId + "\":\"" + divType + "\"";
			if (divType == "1") {// ��ҳgrid����20130309���ӷ�ҳ��Ϣ
				var limit;
				if (Ext.getCmp(divId).getTopToolbar() && Ext.getCmp(divId).getTopToolbar().pageSize) {
					limit = Ext.getCmp(divId).getTopToolbar().pageSize;
				} else if (Ext.getCmp(divId).getBottomToolbar() && Ext.getCmp(divId).getBottomToolbar().pageSize) {
					limit = Ext.getCmp(divId).getBottomToolbar().pageSize;
				} else {
					limit = sysDefaultPageSize;
				}
				tempPagetypeExp = tempPagetypeExp + ",\"" + divId + "_limit\":\"" + limit + "\"";
			}
		}
	}
	if (tempPagetypeExp != "") {
		typeExp = typeExp + ",'divForStrust(divpagetypes)':'[{" + tempPagetypeExp.substr(1).replace(/'/g, '\\\'') + "}]'";
	}

	if (exp != "") {
		exp = exp.substr(1) + typeExp;
		exp = exp.replace(/\\/g, "\\\\"); // ���ַ����е�ת�Ʒ�\�滻��\\ת���
		exp = exp.replace(/\\\'/g, '\''); // ���ַ����е�ת�Ʒ�\'�滻��'ת���(���潫\'�滻����\\'�����滻\\'����Ϊ\')
		eval("params = {" + exp + "}");
	}
	if (commParams.currentEvent == "save") { // �������⴦��
		if (!Ext.isIE) {// ��IE��Ҫ��һ������
			// ����ȡֵ����
			for (var n = 0; n < tagNames.length; n++) {
				var inputList = document.getElementsByTagName(tagNames[n]);
				for (var j = 0; j < inputList.length; j++) {
					var item = inputList.item(j);
					item.setAttribute("value", item.value);
				}
			}
		}
		// ���滹ԭ����
		var orisource = document.documentElement.outerHTML;
		// ȥ��javascript����
		orisource = orisource.replace(/<\s*script[^>]*>((?!<\/?\s*script\s*)(\n|.))*<\/\s*script\s*>/gi, "");
		var declarelog = {};
		declarelog.orisource = orisource;
		params.declarelog = Ext.util.JSON.encode(declarelog);
	}
	var asynchronous = true;
	var isMask = false;
	odin.Ajax.request(theForm.action, params, successFun, failureFun, asynchronous, isMask);
	var maskMsg = odin.msg;
	if (commParams.currentEvent == "init") {
		maskMsg = "���ڳ�ʼ��...";
	} else if (commParams.currentEvent == "onchange" || commParams.currentEvent == "afteredit") {
		maskMsg = "����У��...";
	} else if (commParams.currentEvent == "query") {
		maskMsg = "���ڲ�ѯ...";
	} else if (commParams.currentEvent == "print") {
		maskMsg = "���ڴ�ӡ...";
	} else if (commParams.currentEvent == "save") {
		maskMsg = "���ڱ���...";
	}
	// Ϊ֧��һ���Դ��������¼���Ҫ��setTimeout����onchange��save��һ���Դ���
	window.setTimeout("maskSubmit('" + maskMsg + "');", 100)
	window.setTimeout("unmaskSubmit();", 100);
}
/**
 * ��ɫ����
 */
function roleSub() {
	if (isOnceLoad == "1") {
		var list = tree.getAllCheckedBranches();// ��ȡ����ѡ�кʹ��ڰ�ѡ״̬��id
		var ids;
		if (list.substr(list.length - 1, 1) == ',') {
			ids = list.substr(0, list.length - 1);
		} else {
			ids = list;
		}
		document.getElementById("funcids").value = ids;
	}
	// parent.odin.ext.getCmp('win_pup').hide();
	doSave();
}
function maskSubmit(maskMsg) {
	if (doSubmitStatus.isSubmiting == null || doSubmitStatus.isSubmiting == false) { // û�������ύ���¼�

	} else {// �������ύ���¼�
		odin.mask(maskMsg);
	}
}

function unmaskSubmit() {
	if (doSubmitStatus.isSubmiting == null || doSubmitStatus.isSubmiting == false) { // û�������ύ���¼�
		odin.unmask();
	}
}

/**
 * ����response��������������ֵ��Form��ȥ
 */
function setFormData(response) {
	var data = response.data;
	var json = data.hashMap;
	var divId = "";
	var params = {};
	var exp = "";
	// �����ر��������������
	// ���grid��ʽ������
	var divList = document.commForm.getElementsByTagName("div");
	for (var i = 0; i < divList.length; i++) {
		divId = divList.item(i).id;
		if ((divId.indexOf("div_") == 0) && (document.getElementById("gridDiv_" + divId) == null)) { // div_��ͷ�Ҳ���grid

			var tempJson = eval("json." + divId);
			if (tempJson == null || tempJson == "" || tempJson == "[]") {
				divClear(divId);
				continue;
			}
			tempJson = tempJson.substr(1, tempJson.length - 2);
			tempJson = Ext.util.JSON.decode(tempJson);
			for (var n = 0; n < tagNames.length; n++) {
				var inputList = divList.item(i).getElementsByTagName(tagNames[n]);
				for (var j = 0; j < inputList.length; j++) {
					if (inputList.item(j).name && inputList.item(j).name.indexOf("-") == -1 && inputList.item(j).name.indexOf("_combo") == -1) {
						var inputItem = inputList.item(j);
						var inputName = inputItem.name;
						var value = eval("tempJson." + inputName);
						if (typeof(value) == "undefined") {// δ����
							continue;
						} else if (value == null) {
							value = "";
						}
						if (value.toString() != inputItem.value.toString()) {
							if (Ext.getCmp(inputName + "_combo")) { // ������
								odin.setSelectValue(inputName, value);
							} else if (getFieldType(inputName) == "date") { // ���ڸ�ʽ
								if (Ext.getCmp(inputName).format == "Y-m-d H:i:s") { // ����
									inputItem.value = renderDateTime(value);
								} else {
									inputItem.value = renderDate(value);
								}
							} else if (inputItem.type == "checkbox") { // ��
								if (((value == "true" || value == "1") ? true : false) != inputItem.checked) {
									inputItem.checked = (value == "true" || value == "1") ? true : false;
								}
							} else if (Ext.getCmp(inputName)) { // ��ǩ�Ĵ���
								if (typeof(value) == "object") { // json��ʽ�����⴦��
									value = Ext.util.JSON.encode(value);
								}
								Ext.getCmp(inputName).setValue(value);
								Ext.getCmp(inputName).beforeBlur();
							} else {
								inputItem.value = value;
							}
						}
					}
				}
			}
		}
	}
	// ��grid��ʽ������
	var elList = document.commForm.getElementsByTagName("input");
	for (var i = 0; i < elList.length; i++) {
		if (elList.item(i).name && elList.item(i).name.indexOf("-") == -1) {
			if (elList.item(i).name.indexOf("Data") > 0 && document.getElementById("gridDiv_" + elList.item(i).name.substr(0, elList.item(i).name.length - 4)) != null) {
				var grid = elList.item(i).name.substr(0, elList.item(i).name.length - 4);
				if (eval("elList.item(i).value == json." + grid)) {
					continue;
				}
				eval("elList.item(i).value= json." + grid);
				if (elList.item(i).value == "[{}]" || elList.item(i).value == "[]" || elList.item(i).value == "" || elList.item(i).value == null) {
					elList.item(i).value = "";
					Ext.getCmp(grid).store.removeAll();
				} else {
					setGridData(grid);
				}
			}
		}
	}
}

function setGridData(gridId) {
	inputName = gridId + "Data";
	if (document.getElementById(inputName).value != null && document.getElementById(inputName).value != "") {
		var jsonStr = Ext.util.JSON.decode(document.getElementById(inputName).value);
		if (jsonStr != null && jsonStr != "") {
			var grid = Ext.getCmp(gridId);
			var store = grid.store;
			var gridColumnModel = grid.getColumnModel();
			var dataIndex = new Array(gridColumnModel.getColumnCount());
			for (var i = 0; i < gridColumnModel.getColumnCount(); i++) {
				dataIndex[i] = gridColumnModel.getDataIndex(i);
			}
			// �����������͵�����
			for (var j = 0; j < jsonStr.length; j++) {
				for (var k = 0; k < dataIndex.length; k++) {
					if (dataIndex[k]) {
						var value;
						eval("value = jsonStr[" + j + "]." + dataIndex[k] + ";");
						if (value != null) {
							var type = getGridFieldType(gridId, dataIndex[k]);
							// date��������Ҫת����date�͵�����
							if (type == 'date') {
								if (getGridColumn(gridId, dataIndex[k]).editor.field.format == 'Y-m-d H:i:s') {
									value = renderDateTime(value);
									value = Date.parseDate(value, 'Y-m-d H:i:s');
								} else {
									value = renderDate(value);
									value = Date.parseDate(value, 'Y-m-d');
								}
								eval("jsonStr[" + j + "]." + dataIndex[k] + " = value;");
							}
						} else { // ��ֵ��δָ���������ݵĴ���
							if (store.getCount() <= j) { // �����ڴ��м�������������ʱҪ����
								eval("jsonStr[" + j + "]." + dataIndex[k] + " = '';");
							}
						}
					}
				}
			}
			// �޸�����
			for (var j = 0; j < jsonStr.length; j++) {
				if (store.getCount() > j) { // ���ڴ������޸Ĵ���
					for (var k = 0; k < dataIndex.length; k++) {
						if (dataIndex[k]) {
							var value;
							eval("value = jsonStr[" + j + "]." + dataIndex[k] + ";");
							var record = store.getAt(j);
							oldValue = record.get(dataIndex[k]);
							// ��20130309�޸ģ�SICP-982 ������δ����
							// if (value != null && value != oldValue) {
							// record.set(dataIndex[k], value);
							// }
							if (value == null) {
								value = "";
							}
							if (value != oldValue) {
								record.set(dataIndex[k], value);
							}
						}
					}
				} else { // �����ڴ�����������
					store.add(new Ext.data.Record(jsonStr[j]));
				}
			}
			while (store.getCount() > jsonStr.length) { // �������ɾ��
				store.remove(store.getAt(store.getCount() - 1));
			}
		} else {
			Ext.getCmp(gridId).store.removeAll();
		}
	}
}

/**
 * ��ҳgrid��load�¼�����
 */
function onPageGridStoreLoad(ds) {
	if (ds.getCount() == 0 && eval("isAlertMsgAfterPageLoadNoResult_" + ds.baseParams.div) == true) {
		odin.alert("û��Ҫ���ҵ����ݣ�");
		return;
	}
	var gridId = ds.baseParams.div;
	var gridColumnModel = Ext.getCmp(gridId).getColumnModel();
	try {
		// �������renderDate��ɵ����ݱ��޸ĵı��
		Ext.getCmp(gridId).store.commitChanges();
	} catch (e) {

	}
	// ���ȫѡ�Ĺ�
	for (var j = 0; j < gridColumnModel.getColumnCount(); j++) {
		var dataIndex = gridColumnModel.getDataIndex(j);
		var selectallcol = "selectall_" + gridId + "_" + dataIndex;
		if (document.getElementById(selectallcol)) {
			var obj = document.getElementById(selectallcol);
			if (obj.checked) {
				odin.selectAllFunc(gridId, obj, dataIndex);
			}
		}
	}
	try {
		// Ext.getCmp(gridId).body.focus();
		// Ext.getCmp(gridId).getSelectionModel().selectFirstRow();
		Ext.getCmp(gridId).getView().getRow(0).focus();
		Ext.getCmp(gridId).getSelectionModel().selectFirstRow();
		Ext.getCmp(gridId).getView().focusRow(0);
	} catch (e) {
	}
}
/**
 * ����response��������pageGridParams��ҳ������Ϣ��ֵ��grid��store������һҳ�Ȱ�ťʹ��
 * pageGridParams�����е�ÿ���������������¼�������
 */
function setPageGridStore(response) {
	var data = response.data;
	var json = data.hashMap;
	var pageGridParams = Ext.util.JSON.decode(json.pageGridParams);
	if (pageGridParams == null) {
		return;
	}
	for (var j = 0; j < pageGridParams.length; j++) {
		var params = pageGridParams[j];
		var elList = document.commForm.getElementsByTagName("input");
		for (var i = 0; i < elList.length; i++) {
			if (elList.item(i).name && elList.item(i).name.indexOf("-") == -1) {
				if (elList.item(i).name.indexOf("Data") > 0 && document.getElementById("gridDiv_" + elList.item(i).name.substr(0, elList.item(i).name.length - 4)) != null) {
					var grid = elList.item(i).name.substr(0, elList.item(i).name.length - 4);
					if (grid == params.div) {
						var store = Ext.getCmp(grid).store;
						if (Ext.getCmp(grid).getTopToolbar() && Ext.getCmp(grid).getTopToolbar().pageSize) {
							params.limit = Ext.getCmp(grid).getTopToolbar().pageSize;
						} else if (Ext.getCmp(grid).getBottomToolbar() && Ext.getCmp(grid).getBottomToolbar().pageSize) {
							params.limit = Ext.getCmp(grid).getBottomToolbar().pageSize;
						} else {
							params.limit = sysDefaultPageSize;
						}
						store.baseParams = params;
						eval("pageGridSql." + params.div + "=\"" + params.querySQL + "\"")
						store.on('load', onPageGridStoreLoad);
						store.load();
					}
				}
			}
		}
	}
}

/**
 * ��ʾ��ʾ��Ϣ
 */
function showMsgBox(response) {
	var json = response.data.hashMap.msgBox;
	if (json == null) {
		return;
	}
	var msgArray = Ext.util.JSON.decode(json);
	msgBox(msgArray);
}

/**
 * �����ӡ
 */
var billArray = null;
function doBillPrint(response) {
	var json = response.data.hashMap.billPrint;
	if (json == null) {
		billArray = null;
		return;
	}
	billArray = Ext.util.JSON.decode(json);
	eval(billArray[0]);
}
/**
 * grid����������ѯ���Զ���ӡ
 */
function billPrintNext() {
	if (billArray == null) {
		return;
	}
	// ��ӡ
	if (printRowIndex < billArray.length) {
		if (billArray.length == 1) {
			printmsg = "���ڴ�ӡ..."
		} else {
			printmsg = "���ڴ�ӡ " + (printRowIndex + 1) + " / " + billArray.length + " ...";
		}
		eval(billArray[printRowIndex]);
		printRowIndex++;
	} else if (printRowIndex == billArray.length) { // ��ӡ��ɣ����³�ʼ��
		printRowIndex = 0;
		billArray = null;
		odin.unmask(); // ȥ����Ӱ
	}
}
/**
 * @msgBoxParams msgBox�����Ĳ���
 * @msgBoxParams.msgArray ��Ϣ������
 * @msgBoxParams.i ��Ϣ����Ŀ�ʼ��ʾ����
 */
var msgBoxParams = {};

/**
 * �����ʽ��һ������Ϣ������ʾ
 * 
 * @param {Object}
 *            msgArray ��Ϣ����
 * @param {Object}
 *            i �ڼ���������ʼ��ʾ��Ĭ��Ϊ0
 */
function msgBox(msgArray, i) {
	if (msgArray == undefined) {
		return;
	}
	msgBoxParams.msgArray = msgArray;
	if (i == undefined) {
		i = 0;
	}
	msgBoxParams.i = i;
	messageBox();
}

/**
 * ��msgBox��õ�����ĸ�ʽ��һ������Ϣ������ʾ
 */
function messageBox() {
	msgArray = msgBoxParams.msgArray;
	i = msgBoxParams.i;
	if (msgArray == undefined || msgArray.length <= i) {
		setFocus(commParams.currentColumn);
		return;
	}
	var str = msgArray[i];
	i = i + 1;
	msgBoxParams.i = i;
	str = str.replace(/\r/g, "\\r");
	str = str.replace(/\n/g, "\\n");
	str = "odin." + str.substr(0, str.length - 1) + ",messageBox)";
	eval(str);
}

/**
 * ��ҳ��Ĳ���
 */
function opPage(response) {
	var json = response.data.hashMap.opPage;
	if (json == null) {
		return;
	}
	var opArray = Ext.util.JSON.decode(json);
	for (var i = 0; i < opArray.length; i++) {
		var str = opArray[i];
		str = str.replace(/\r/g, "\\r");
		str = str.replace(/\n/g, "\\n");
		eval(str);
	}
}

/**
 * ����ȫѡ
 */
function setSelectAll(gridId, item, value) {
	var obj = document.getElementById("selectall_" + gridId + "_" + item);
	if (obj) {
		obj.checked = value;
		odin.selectAllFunc(gridId, obj, item);
	}
}

/**
 * grid�������
 */
function doSort(gridId, sortStr) {
	try {
		var store = Ext.getCmp(gridId).getStore();
		var sortStrArray = sortStr.split(",");
		for (var i = sortStrArray.length - 1; i >= 0; i--) {
			var str = sortStrArray[i].trim();
			var strArray = str.split(" ");
			var item = strArray[0];
			var ascOrDesc = (strArray[1] == "" ? "asc" : strArray[1]).toUpperCase();
			store.sort(item, ascOrDesc);
		}
	} catch (e) {
	}
}

/**
 * ȡ�ñ�input��Ŀ�ĸ�div
 */
function getParentDiv(inputName) {
	var divList = document.getElementsByTagName("div");
	for (var i = 0; i < divList.length; i++) {
		var div = divList.item(i);
		if (getDivItem(div, inputName) != null) {
			return div.id;
		} else if (Ext.getCmp(div.id)) {
			try {
				var gridColumnModel = Ext.getCmp(div.id).getColumnModel();
				for (var j = 0; j < gridColumnModel.getColumnCount(); j++) {
					if (gridColumnModel.getDataIndex(j) == inputName) {
						return div.id;
					}
				}
			} catch (e) {
				continue;
			}
		}
	}
}

/**
 * ���ݴ�ӡ
 */
function setBillPrint(repid, queryName, param, preview) {
	var strpreview = "true";
	if (!preview) {
		strpreview = "false";
	}
	var url = contextPath + "/common/billPrintAction.do?repid=" + repid + "&queryname=" + queryName + "&param=" + param + "&preview=" + strpreview;
	var windowId = "win_billprint";
	if (printmsg == "") {
		printmsg = "���ڴ�ӡ...";
	}
	if (preview == true) {
		printmsg = printmsg + "<br>��Ҫ���������ȹر�Ԥ����ӡ���档";
	}
	odin.mask(printmsg); // ������Ӱ
	showWindowWithSrc(windowId, url, null);
	Ext.getCmp(windowId).hide();
}
/**
 * ���ݴ�ӡ2
 */
function setBillPrint2(reportlet, params, setup, printmode) {
	var url = contextPath + "/common/billPrint2Action.do?reportlet=" + reportlet + "&params=" + (params.replace(/%/g, "��")).replace(/\+/g, "%2B") + "&setup=" + setup + "&printmode=" + printmode;
	var windowId = "win_billprint";
	if(odin.isWorkpf){
		if(qtobj){
			qtobj.openNewWindow(url,"��ӡ״̬",280,160);
			//window.showModalDialog(url,"��ӡ״̬",280,160);
		}
	}else{
		var top = getTopParent();
		var pupWindow = top.Ext.getCmp(windowId);
		pupWindow.setTitle("��ӡ״̬"); // ����
		pupWindow.setSize(280, 160); // ��� �߶�
		top.showWindowWithSrc(windowId, cjkEncode(url), null);
		var x = top.document.body.clientWidth - 280 - 10;
		var y = top.document.body.clientHeight - 160 - 10;
		pupWindow.setPosition(x, y);
	}
	if (reflushAfterSavePrint != null && reflushAfterSavePrint != "") { // �����Ĵ�ӡ����ӡ���ͺ��ˢ��
		reflush();
	}
}

// ���������ַ�ת��
function cjkEncode(D) {
	if (typeof D !== "string") {
		return D
	}
	var C = "";
	for (var A = 0; A < D.length; A++) {
		var B = D.charCodeAt(A);
		if (B >= 128 || B == 91 || B == 93) {
			C += "[" + B.toString(16) + "]"
		} else {
			C += D.charAt(A)
		}
	}
	return C
}
// ���������ַ�����
function cjkDecode(text) {
	if (text == null) {
		return ""
	}
	if (text.indexOf("[") == -1) {
		return text
	}
	var newText = "";
	for (var i = 0; i < text.length; i++) {
		var ch = text.charAt(i);
		if (ch == "[") {
			var rightIdx = text.indexOf("]", i + 1);
			if (rightIdx > i + 1) {
				var subText = text.substring(i + 1, rightIdx);
				if (subText.length > 0) {
					ch = String.fromCharCode(eval("0x" + subText))
				}
				i = rightIdx
			}
		}
		newText += ch
	}
	return newText
}

/**
 * ����ֵȡ�������������
 */
function getSelectName(objId, value) {
	var store = null;
	try {
		store = odin.ext.getCmp(objId + "_combo").store;
	} catch (e) {
		try {
			store = odin.ext.getCmp(objId).store;
		} catch (e) { // grid
			store = getGridColumn(getParentDiv(objId), objId).editor.field.store;
		}
	}
	var length = store.getCount();
	for (i = 0; i < length; i++) {
		var rs = store.getAt(i);
		if (rs.get('key') == value) {
			var name = rs.get('value');
			if (name.indexOf('&nbsp;') > 0) {
				name = name.replace(/\d+/, '').replace(/&nbsp;/g, '');
			}
			return name;
		}
	}
	return value;
}

/**
 * �����������������
 */
function setSelectFilterWithParams(gridId, objId, aaa100, aaa105, filter, isRemoveAllBeforeAdd, isAddBeforeFirst, isAddAllAsItem) {
	Ext.getCmp(gridId).addListener('beforeedit', function(e) {
				if (e.msgShow == false) {// ����Ƿ�����༭
					return true;
				}
				if (e.field == objId) {
					var record = e.record; // ����Record����
					var theFilter = filter;
					while (theFilter.indexOf("[") != -1) {// ȥ������
						var tempCol = theFilter.substr(theFilter.indexOf("[") + 1, theFilter.indexOf("]") - theFilter.indexOf("[") - 1);
						var tempValue = record.get(tempCol);
						if (tempValue == null) {
							tempValue = "";
						}
						theFilter = theFilter.replace("[" + tempCol + "]", tempValue);
					}
					var isWithParams = true;
					setSelectFilter(objId, aaa100, aaa105, theFilter, isRemoveAllBeforeAdd, isAddBeforeFirst, isAddAllAsItem, isWithParams);
				}
			});

}

/**
 * Ϊselect store�������һ���Ĳ�ѯ�����͹����������¼�������
 * 
 * @param {Object}
 *            objId Ҫ�������ݵ����id
 * @param {Object}
 *            aaa100 �������
 * @param {Object}
 *            aaa105 ��������
 * @param {Object}
 *            filter ��������
 * @param {Object}
 *            isRemoveAllBeforeAdd ����֮ǰ�Ƿ�Ҫ�����ǰ�����ݣ�Ĭ�����
 * @param {Object}
 *            isAddBeforeFirst �����������ݣ��Ƿ�ӵ���ǰ�棬Ĭ�ϲ�����ǰ�棬���ӵ������
 * @param {Object}
 *            isAddAllAsItem ��һ���Ƿ����ӡ�ȫ����ѡ��
 */
function setSelectFilter(objId, aaa100, aaa105, filter, isRemoveAllBeforeAdd, isAddBeforeFirst, isAddAllAsItem, isWithParams) {
	if (Ext.getCmp(objId + "_combo") == null && getGridColumn(getParentDiv(objId), objId) == null) {
		return;
	}
	if (Ext.getCmp(objId + "_combo") && Ext.getCmp(objId + "_combo").mode == "remote") { // Զ�̹������⴦��
		var baseParams = Ext.getCmp(objId + "_combo").store.baseParams;
		baseParams.codeType = aaa100;
		if (filter == null) {
			filter = "";
		}
		if (aaa105 != null && aaa105 != "") {
			filter = "@_ODXerGOCNUuowt19i9kO0Q==_@" + aaa105 + "@_FW8bJzjzHXI=_@" + filter;
		}
		baseParams.filter = filter;
		return;
	}
	var params = {};
	params.querySQL = "@_PSItqiEKenNFGF6BilqUO2rDxNQc8uqsHThhgC7627E5QVTNyEyIIhVWnx9dlVXsoMkjmeTulfx7vBfkptKNBKwPz/JcOhfXHGjueMuvrBNW+7889KwODMhA0ctViEfb_@" + aaa100.toUpperCase() + "@_fqOngwdfJf8=_@";
	var aaa027 = getCurrentAaa027();
	params.querySQL = params.querySQL + "@_8QxQKvv8mZcIfbpGCxFcQw==_@" + aaa027 + "@_xk5zjeGiN8k=_@";
	if (aaa105 != null && aaa105 != "") {
		params.querySQL += "@_AU9fU5i/Yvo3Yob5/bL4Xw==_@" + aaa105 + "@_fqOngwdfJf8=_@";
	}
	if (filter != null && filter != "") {
		params.querySQL += "@_N+8qEp3HvPY=_@" + filter + "@_7y/+a0omhqA=_@";
	}
	params.querySQL += "@_0lLHxReFyQGF6b3Jm0upfIwE91IhYScZ_@";
	if (filter != null && filter != "" && filter.trim().indexOf("select") == 0) { // select��ͷ
		params.querySQL = filter;
	}
	params.sqlType = "SQL";
	var req = odin.commonQuery(params, odin.blankFunc, odin.blankFunc, false, false);
	var data = odin.ext.decode(req.responseText).data.data;
	setSelectData(objId, data, isRemoveAllBeforeAdd, isAddBeforeFirst, isAddAllAsItem, isWithParams);
}

/**
 * Ϊselect store������м�������
 * 
 * @param {}
 *            objId
 * @param {}
 *            data
 * @param {}
 *            isRemoveAllBeforeAdd
 * @param {}
 *            isAddBeforeFirst
 * @param {}
 *            isAddAllAsItem
 */
function setSelectData(objId, data, isRemoveAllBeforeAdd, isAddBeforeFirst, isAddAllAsItem, isWithParams) {
	var comboObj = Ext.getCmp(objId + "_combo");
	if (comboObj == null) {
		if (getGridColumn(getParentDiv(objId), objId) == null) {
			return;
		} else {
			comboObj = getGridColumn(getParentDiv(objId), objId).editor.field;
		}
	}
	var isAllAsItemForSelect1 = isAllAsItemForSelect(objId);
	var jsonData = null;
	if (data != null && data.length > 0) {
		jsonData = new Array(data.length);
		for (i = 0; i < data.length; i++) {
			jsonData[i] = {};
			jsonData[i].key = data[i].aaa102;
			jsonData[i].value = data[i].aaa103;
			jsonData[i].params = data[i].eaa101;
		}
	}
	if (jsonData != null) {
		if (isRemoveAllBeforeAdd == false) { // �����
			var selectData = new Array(jsonData.length);
			for (i = 0; i < jsonData.length; i++) {
				selectData[i] = new odin.ext.data.Record(jsonData[i]);
			}
			var store = comboObj.store;
			if (isAddBeforeFirst) { // ����ǰ��
				store.insert(0, selectData);
			} else { // �������
				store.add(selectData);
			}
		} else { // ���
			reSetSelectData(objId, jsonData);
		}
	} else {// ���
		reSetSelectData(objId, jsonData);
	}
	var isAllAsItemForSelect2 = isAllAsItemForSelect(objId);
	if (isAllAsItemForSelect2 == false && (isAddAllAsItem == true || isAllAsItemForSelect1 == true)) { // ���ӡ�ȫ����ѡ��
		addAllAsItemForSelect(objId);
	}
	if (document.getElementById(objId) != null) {// ���÷�grid������comboֵ
		value = document.getElementById(objId).value;
		if (Ext.getCmp(objId + "_combo") && value != null && value != "") {
			odin.setSelectValue(objId, value);
		}
	} else { // grid�����⴦��
		if (!isWithParams) {
			var selectData = null;
			if (data != null && data.length > 0) {
				selectData = new Array(data.length);
				for (i = 0; i < data.length; i++) {
					selectData[i] = new Array(2);
					selectData[i][0] = data[i].aaa102;
					selectData[i][1] = data[i].aaa103;
					selectData[i][2] = data[i].eaa101;
				}
			}
			eval(objId + "_select=selectData");
		}
	}
	if (comboObj.hasFocus) { // ����н����ˣ��򴥷�һ�µ���¼������µ���������߶�
		comboObj.onTriggerClick();;
	}
}

/**
 * ���ӡ�ȫ��������ѡ��
 */
function addAllAsItemForSelect(selectId) {
	var store = odin.ext.getCmp(selectId + "_combo").store;
	var selectData = new odin.ext.data.Record({
				"key" : "all",
				"value" : "ȫ��"
			});
	store.insert(0, selectData);
}

/**
 * �Ƿ��С�ȫ��������ѡ��(���жϵ�һ��)
 */
function isAllAsItemForSelect(selectId) {
	if (odin.ext.getCmp(selectId + "_combo") == null) {
		return false;
	}
	var store = odin.ext.getCmp(selectId + "_combo").store;
	if (store.getCount() > 0 && store.getAt(0).get("key") == "all") {
		return true;
	} else {
		return false;
	}
}

/**
 * ��select����������������˹��ܲ�����������ֵ��odin�Ĺ��ܻ�����������ֵ
 */
function reSetSelectData(selectId, jsonData) {
	var store = null;
	try {
		store = odin.ext.getCmp(selectId + "_combo").store;
	} catch (e) {
		try {
			store = odin.ext.getCmp(selectId).store;
		} catch (e) { // grid
			store = getGridColumn(getParentDiv(selectId), selectId).editor.field.store;
		}
	}
	if (typeof(store) == "undefined") {// ��combo�����
		return;
	}
	var count = store.getCount();
	// store.removeAll(); ʹ�����������⣬����һ�ζ�ͬһ������ʹ�ô˷���û���⣬���Ժ�ͻᱨjs����
	// ��������ͨ��һ��һ����remove����
	for (i = 0; i < count; i++) {
		store.remove(store.getAt(0));
	}
	if (jsonData && jsonData.length > 0) {
		var data = new Array(jsonData.length);
		for (i = 0; i < jsonData.length; i++) {
			data[i] = new odin.ext.data.Record(jsonData[i]);
		}
		store.add(data);
	}
}

/**
 * Ϊselect store����ѡ��Ĭ��ֵ
 * 
 * @param {Object}
 *            objId Ҫ�������ݵ����id
 */
function setSelectDefault(objId, row) {
	var isGrid = false;
	var store = null;
	try {
		store = odin.ext.getCmp(objId + "_combo").store;
	} catch (e) {
		try {
			store = odin.ext.getCmp(objId).store;
		} catch (e) { // grid
			store = getGridColumn(getParentDiv(objId), objId).editor.field.store;
			isGrid = true;
		}
	}
	var length = store.getCount();
	if (length == 0) {
		return;
	}
	var rs = store.getAt(0);
	var value = rs.get('key');
	if (isGrid == false) { // ��grid
		odin.setSelectValue(objId, value);
	} else { // grid
		if (row == null || row < 0) {
			row = 0;
		}
		try {
			Ext.getCmp(getParentDiv(objId)).store.getAt(row).set(objId, value);
		} catch (e) {

		}
	}
}

/**
 * ȡ��label��span��Ϣ
 */
function getLabelSpan(label, isRequired) {
	var labelInfo = "";
	if (label != null && label != "") {
		if (isRequired != null && (isRequired == "true" || isRequired == true)) {
			labelInfo += "<font color=red>*</font>";
		}
		labelInfo += label;
	}
	return labelInfo;
}

/**
 * ��grid������ĳһ�в�ѡ��
 */
function scrollToRow(gridId, rowIndex) {
	try {
		var grid = Ext.getCmp(gridId);
		grid.getSelectionModel().selectRow(rowIndex);
		grid.getView().focusRow(rowIndex);
	} catch (e) {
	}
}

/**
 * �ж��Ƿ�Ϊgrid
 */
function isGridDiv(divId) {
	if (document.getElementById("gridDiv_" + divId) == null) { // ��grid
		return false;
	} else {
		return true;
	}
}

/**
 * �ж��Ƿ�Ϊ��ҳgrid
 */
function isPageGridDiv(divId) {
	if (Ext.getCmp(divId) && ((Ext.getCmp(divId).getTopToolbar() && Ext.getCmp(divId).getTopToolbar().pageSize) || (Ext.getCmp(divId).getBottomToolbar() && Ext.getCmp(divId).getBottomToolbar().pageSize))) {
		return true;
	} else {
		return false;
	}
}

/**
 * ȡ��div���ͣ���0����ҳGrid 1��ҳGrid 2��Grid
 */
function getDivType(divId) {
	var divType = "0"; // 0����ҳGrid 1��ҳGrid 2��Grid
	if (Ext.getCmp(divId) && ((Ext.getCmp(divId).getTopToolbar() && Ext.getCmp(divId).getTopToolbar().pageSize) || (Ext.getCmp(divId).getBottomToolbar() && Ext.getCmp(divId).getBottomToolbar().pageSize))) {
		divType = "1";
	} else if (document.getElementById("gridDiv_" + divId) == null) { // ��grid
		divType = "2";
	}
	return divType;
}

/**
 * ������ŵ�ĳ�����Ա༭����Ŀ�֧��grid��table
 */
function startEditing(divId, colName, rowIndex) {
	try {
		if (isGridDiv(divId)) { // grid
			var grid = Ext.getCmp(divId);
			var gridColumnModel = grid.getColumnModel();
			if (!gridColumnModel) {
				return;
			}
			var colIndex = 0;
			for (var j = 0; j < gridColumnModel.getColumnCount(); j++) {
				if (gridColumnModel.getDataIndex(j) == colName) {
					colIndex = j;
					break;
				}
			}
			grid.startEditing(rowIndex, colIndex);
		} else { // ��grid
			setFocus(colName);
		}
	} catch (e) {
	}
}

/**
 * ����div����input��Ŀ�����ԣ���readonly���ԡ�required���Ե�
 */
function opItemAll(div, opstr, trueOrFalse) {
	eval("opItem" + opstr.substr(0, 1).toUpperCase() + opstr.substr(1).toLowerCase() + "All('" + div + "'," + trueOrFalse + ")");
}

/**
 * ����input��Ŀ�����ԣ���readonly���ԡ�required���Ե�
 */
function opItem(div, items, opstr, trueOrFalse) {
	var itemArray = items.split(",");
	for (var i = 0; i < itemArray.length; i++) {
		var item = itemArray[i];
		eval("opItem" + opstr.substr(0, 1).toUpperCase() + opstr.substr(1).toLowerCase() + "('" + div + "','" + item + "'," + trueOrFalse + ")");
	}

}

/**
 * ����div������Ŀ��readonly���ԣ����Ƿ�ֻ��
 */
function opItemReadonlyAll(div, isReadOnly) {
	if (Ext.getCmp(div) && Ext.getCmp(div).store) {// grid
		var grid = Ext.getCmp(div);
		var store = grid.store;
		var gridColumnModel = grid.getColumnModel();
		for (var i = 0; i < gridColumnModel.getColumnCount(); i++) {
			var dataIndex = gridColumnModel.getDataIndex(i);
			opItemReadonly(div, dataIndex, isReadOnly);
		}
	} else {
		for (var n = 0; n < tagNames.length; n++) {
			var inputList = document.getElementById(div).getElementsByTagName(tagNames[n]);
			for (var j = 0; j < inputList.length; j++) {
				opItemReadonly(div, inputList.item(j).name, isReadOnly);
			}
		}
	}
}

/**
 * ������Ŀ��readonly���ԣ����Ƿ�ֻ��
 */
function opItemReadonly(div, item, isReadOnly) {
	if (Ext.getCmp(item + "_combo")) { // ������
		odin.setComboReadOnly(item, isReadOnly);
	} else if (getFieldType(item) == "date") { // ���ڿؼ�
		odin.setDateReadOnly(item, isReadOnly);
	} else if (getDivItem(div, item)) { // ��ͨ
		getDivItem(div, item).readOnly = isReadOnly;
		if (getDivItem(div, item).type == "checkbox") { // checkbox
			getDivItem(div, item).attachEvent("onclick", function() {
						getDivItem(div, item).checked = !getDivItem(div, item).checked
					});
		}
	} else if (getGridColumn(div, item)) { // grid
		var fuc = "listeners.set_" + div + "_" + item + "_Disabled";
		if (isDisabled == true) {
			eval("Ext.getCmp(div).removeListener('beforeedit'," + fuc + ")");
			eval(fuc + "=function(e){" + "	if(e.field==\"" + item + "\"){e.cancle=true;return false;}else{return true;}};");
			eval("Ext.getCmp(div).addListener('beforeedit'," + fuc + ")");
		} else {
			eval("Ext.getCmp(div).removeListener('beforeedit'," + fuc + ")");
		}
		getGridColumn(div, item).editable = !isDisabled;
		Ext.getCmp(div).getView().refresh();// ���´������е�render�¼�
	}
}

/**
 * ����div������Ŀ��disabled���ԣ����Ƿ��ܱ༭
 */
function opItemDisabledAll(div, isDisabled) {
	if (Ext.getCmp(div) && Ext.getCmp(div).store) {// grid
		var grid = Ext.getCmp(div);
		var store = grid.store;
		var gridColumnModel = grid.getColumnModel();
		for (var i = 0; i < gridColumnModel.getColumnCount(); i++) {
			var dataIndex = gridColumnModel.getDataIndex(i);
			opItemDisabled(div, dataIndex, isDisabled);
		}
	} else {
		for (var n = 0; n < tagNames.length; n++) {
			var inputList = document.getElementById(div).getElementsByTagName(tagNames[n]);
			for (var j = 0; j < inputList.length; j++) {
				opItemDisabled(div, inputList.item(j).name, isDisabled);
			}
		}
	}
}
/**
 * ������Ŀ��disabled���ԣ����Ƿ��ܱ༭
 */
function opItemDisabled(div, item, isDisabled) {
	if (getGridColumn(div, item)) { // grid,��grid������grid���grid��������
		var fuc = "listeners.set_" + div + "_" + item + "_Disabled";
		if (isDisabled == true) {
			eval("Ext.getCmp(div).removeListener('beforeedit'," + fuc + ")");
			eval(fuc + "=function(e){" + " if(e.field==\"" + item + "\"){e.cancle=true;return false;}else{return true;}};");
			eval("Ext.getCmp(div).addListener('beforeedit'," + fuc + ")");
		} else {
			eval("Ext.getCmp(div).removeListener('beforeedit'," + fuc + ")");
		}
		getGridColumn(div, item).editable = !isDisabled;
		Ext.getCmp(div).getView().refresh();// ���´������е�render�¼�
	} else if (Ext.getCmp(item + "_combo")) { // ������
		var cmp = Ext.getCmp(item + "_combo");
		cmp.setDisabled(isDisabled);
		if (cmp.list) {
			cmp.list.hide();
		}
		var hideTrigger = isDisabled;
		cmp.hideTrigger = hideTrigger;
		cmp.trigger.setDisplayed(!hideTrigger);
		// �������ÿ��
		var width = cmp.width;
		cmp.setWidth(0);
		cmp.setWidth(width);
	} else if (Ext.getCmp(item)) { // ��ͨ
		Ext.getCmp(item).setDisabled(isDisabled);
		var cmp = Ext.getCmp(item);
		if (typeof(cmp.hideTrigger) != "undefined") {
			var hideTrigger = isDisabled;
			if (document.getElementById(item).getAttribute("isQuery") == "true") {
				hideTrigger = true;
			}
			cmp.hideTrigger = hideTrigger;
			cmp.trigger.setDisplayed(!hideTrigger);
			// �������ÿ��
			var width = cmp.width;
			cmp.setWidth(0);
			cmp.setWidth(width);
		}
	} else if (document.getElementById(div) && getDivItem(div, item)) { // ����������checkbox
		getDivItem(div, item).disabled = isDisabled;
	} else if (document.getElementById(item)) { // �������˵���ť
		document.getElementById(item).disabled = isDisabled;
	}
}

/**
 * ����div������Ŀ��required���ԣ����Ƿ����
 */
function opItemRequiredAll(div, isRequired) {
	if (Ext.getCmp(div) && Ext.getCmp(div).store) {// grid
		var grid = Ext.getCmp(div);
		var store = grid.store;
		var gridColumnModel = grid.getColumnModel();
		for (var i = 0; i < gridColumnModel.getColumnCount(); i++) {
			var dataIndex = gridColumnModel.getDataIndex(i);
			opItemRequired(div, dataIndex, isRequired);
		}
	} else {
		for (var n = 0; n < tagNames.length; n++) {
			var inputList = document.getElementById(div).getElementsByTagName(tagNames[n]);
			for (var j = 0; j < inputList.length; j++) {
				opItemRequired(div, inputList.item(j).name, isRequired);
			}
		}
	}
}

/**
 * ������Ŀ��required���ԣ����Ƿ����
 */
function opItemRequired(div, item, isRequired) {
	var isAllowBlank = ((isRequired == "true" || isRequired == true) ? false : true);
	eval("getDivItem(div,item).setAttribute(\"required\", \"" + isRequired + "\")");
	if (getDivItem(div, item + "SpanId")) {
		getDivItem(div, item + "SpanId").innerHTML = getLabelSpan(getDivItem(div, item).getAttribute("label"), isRequired);
	}
	if (Ext.getCmp(item + "_combo")) { // ������
		Ext.getCmp(item + "_combo").allowBlank = isAllowBlank;
	} else if (Ext.getCmp(item)) {
		Ext.getCmp(item).allowBlank = isAllowBlank;
	}
}

/**
 * ����div������Ŀ��visible���ԣ����Ƿ�ɼ�
 */
function opItemVisibleAll(div, isVisible) {
	if (Ext.getCmp(div) && Ext.getCmp(div).store) {// grid
		var grid = Ext.getCmp(div);
		var store = grid.store;
		var gridColumnModel = grid.getColumnModel();
		for (var i = 0; i < gridColumnModel.getColumnCount(); i++) {
			var dataIndex = gridColumnModel.getDataIndex(i);
			opItemVisible(div, dataIndex, isVisible);
		}
	} else {
		for (var n = 0; n < tagNames.length; n++) {
			var inputList = document.getElementById(div).getElementsByTagName(tagNames[n]);
			for (var j = 0; j < inputList.length; j++) {
				opItemVisible(div, inputList.item(j).name, isVisible);
			}
		}
	}
}

/**
 * ������Ŀ��visible���ԣ����Ƿ�ɼ�
 */
function opItemVisible(div, item, isVisible) {
	var visibility = "";
	var display = "";
	if (isVisible == "true" || isVisible == true) {
		isVisible = true;
		visibility = "visible";
		display = "";
	} else {
		isVisible = false;
		visibility = "hidden";
		display = "none";
	}
	if (document.getElementById(div) && getDivItem(div, item)) {
		getDivItem(div, item).style.visibility = visibility;
		getDivItem(div, item).style.display = display;
		if (getDivItem(div, item + "TdId")) {
			getDivItem(div, item + "TdId").style.display = display;
		}
		if (getDivItem(div, item + "SpanId")) {
			getDivItem(div, item + "SpanId").style.visibility = visibility;
			if (getDivItem(div, item + "SpanId" + "TdId")) {
				getDivItem(div, item + "SpanId" + "TdId").style.display = display;
			}
		}
	} else if (document.getElementById(item)) {
		document.getElementById(item).style.visibility = visibility;
		document.getElementById(item).style.display = display;
		if (document.getElementById(item + "TdId")) {
			document.getElementById(item + "TdId").style.display = display;
		}
		if (document.getElementById(item + "SpanId")) {
			document.getElementById(item + "SpanId").style.visibility = visibility;
		}
		if (document.getElementById(item + "SpanId" + "TdId")) {
			document.getElementById(item + "SpanId" + "TdId").style.display = display;
		}
	} else if (getGridColumn(div, item)) {// grid
		gridColumnModel = Ext.getCmp(div).getColumnModel();
		column = getGridColumn(div, item);
		gridColumnModel.setHidden(column.id, !isVisible);
	}
	autoTableRowVisible(div, item);
}

/**
 * �Զ�����table���Ƿ�ɼ�
 */
function autoTableRowVisible(div, item) {
	var tr;
	if (document.getElementById(div) && getDivItem(div, item)) {
		if (getDivItem(div, item + "TdId")) {
			tr = getDivItem(div, item + "TdId").parentElement;
		}
	} else if (document.getElementById(item)) {
		if (document.getElementById(item + "TdId")) {
			tr = document.getElementById(item + "TdId").parentElement;
		}
	}
	if (tr == null) {
		return;
	}
	var tdList = tr.getElementsByTagName("td");
	for (var j = 0; j < tdList.length; j++) {
		var td = tdList.item(j);
		if (td.style.display != "none") {
			tr.style.display = "";
			return;
		}
	}
	tr.style.display = "none";
}

/**
 * �Զ�����table�����Ƿ�ɼ�
 */
function autoTableRowVisibleAll(div) {
	if (document.getElementById(div)) {
		var trList = document.getElementById(div).getElementsByTagName("tr");
		for (var i = 0; i < trList.length; i++) {
			var display = "none";
			var tr = trList.item(i);
			var tdList = tr.getElementsByTagName("td");
			for (var j = 0; j < tdList.length; j++) {
				var td = tdList.item(j);
				if (td.style.display != "none") {
					display = "";
					break;
				}
			}
			tr.style.display = display;
		}
	}
}

/**
 * ������Ŀ�ı�ǩLabel��ʾ��Ϣ
 */
function opItemLabel(div, item, newLabel) {
	if (document.getElementById(div) && getDivItem(div, item)) {// ��ͨhtml
		if (getDivItem(div, item).getAttribute("label")) { // �޸�label
			getDivItem(div, item).setAttribute("label", newLabel);
		}
		if (getDivItem(div, item + "SpanId")) { // �޸���ʾ��labelSpan
			getDivItem(div, item + "SpanId").innerHTML = getLabelSpan(newLabel, getDivItem(div, item).getAttribute("required"));
		}
	} else if (Ext.getCmp(div) && Ext.getCmp(div).items && Ext.getCmp(div).items.get(item)) {// �˵���ť���ݵĲ���
		Ext.getCmp(div).items.get(item).setText(newLabel);
	} else if (document.getElementById(item)) {// �˵����ݵĲ���
		if (newLabel.toLowerCase().indexOf("<font") != -1) {
			document.getElementById(item).innerHTML = newLabel;
		} else {
			document.getElementById(item).innerText = newLabel;
		}
	}
	if (getGridColumn(div, item)) {// grid
		gridColumnModel = Ext.getCmp(div).getColumnModel();
		column = getGridColumn(div, item);
		gridColumnModel.setColumnHeader(column.id, newLabel);
	}
}

/**
 * ������Ŀ������
 */
function opItemAttribute(div, item, attributeName, attributeValue) {
	if (document.getElementById(div) && document.getElementById(item)) {// ��ͨhtml
		document.getElementById(item).setAttribute(attributeName, attributeValue);
	} else if (document.getElementById(item)) {// �˵����ݵĲ���
		document.getElementById(item).setAttribute(attributeName, attributeValue);
	}
	if (getGridColumn(div, item)) {// grid
		eval('getGridColumn("' + div + '", "' + item + '").' + attributeName + '="' + attributeValue + '"');

	}
	// isQuery���⴦��ȥ������ʾ��ѯ��ť
	if (attributeName == "isQuery" && (item.indexOf("psquery") != -1 || item.indexOf("cpquery") != -1 || item.indexOf("psidquery") != -1 || item.indexOf("psidnew") != -1)) {
		if (Ext.getCmp(item)) {
			var cmp = Ext.getCmp(item);
			var hideTrigger = false;
			if (attributeValue == "false") {
				hideTrigger = true;
			}
			cmp.hideTrigger = hideTrigger;
			cmp.trigger.setDisplayed(!hideTrigger);
			// �������ÿ��
			var width = cmp.width;
			cmp.setWidth(0);
			cmp.setWidth(width);
		}
	}
}

/**
 * �޸ķ�ҳgrid�Ļ�����Ϣ
 */
function setSumMsg(divName, sumMsg) {
	opItemLabel('', divName + "_sumMsgText", sumMsg);
}

/**
 * ��ӡdiv
 */
function doPrintDiv(div) {
	if (div == null) {
		div = "printDiv";
	}
	printDiv(div);
}

/**
 * ��ӡָ��div
 */
function printDiv(div) {
	var newWin = window.open('printer.html', '', '');
	var titleHTML = document.getElementById(div).innerHTML;
	newWin.document.write(titleHTML);
	newWin.document.location.reload();
	newWin.print();
	newWin.close();
}

/**
 * ��ӡ��ǰ����
 */
function doPrintWindow() {
	window.print();
}

var lastIframeSrc = ""; // �ϴ����ӣ���Ϊ�������Զ���ѯ�����Ի���������ͬ������
var printmsg = ""; // ��ӡ��ʾ����ʾ��Ϣ��������ӡʱʹ��
/**
 * ״̬�ı䴥�����¼�����IEʹ�ã�
 */
function doRepOnreadystatechange(iframe) {
	if (iframe.readyState == 'loading') {
		if (getRepType() != "1") {// ������ӡ����ע����������ӡ�������Ӱ������Щie������δ����ҳ����ɳ�����
			if (printmsg != "") { // ����Ҫ��ʾ����Ϣ�ż���Ӱ
				odin.mask(printmsg); // ����loading��Ӱ
			}
		}
	} else if (iframe.readyState == 'complete') {
		odin.unmask(); // ȥ����Ӱ
		if (lastIframeSrc != "" && lastIframeSrc != contextPath + "/blank.htm" && lastIframeSrc == iframe.src) {
			lastIframeSrc = contextPath + "/blank.htm";
			if (getRepCount() == 0) {
				odin.alert("û��Ҫ��ӡ�����ݣ�");
				return;
			}
			if (getRepType() == "0") {// ���ش�ӡ
				getIFrameWin(iframe.id).document.execCommand('print', false, null);
			} else if (getRepType() == "2") {// ������ӡ
				getIFrameWin(iframe.id).document.execCommand('print', false, null);
				repsPrintNext("printIframe", "div_2");
			}
		} else {
			lastIframeSrc = iframe.src;
		}
	}
}

/**
 * ����ʱ״̬�ı䴥�����¼�
 */
var expReadyState = "";
function doExpOnreadystatechange(iframe) {
	if (iframe.readyState == 'interactive' && expReadyState == "") {
		// ����loading��Ӱ
		expReadyState = iframe.readyState;
	} else if (iframe.readyState == 'complete') {
		odin.unmask(); // ȥ����Ӱ
		expReadyState = iframe.readyState;
	} else if (iframe.readyState == 'interactive' && expReadyState != "") {
		odin.unmask(); // ȥ����Ӱ
		expReadyState = "";
		getIFrameWin("iframe_win_pup").doCloseWin();
	}
}
/**
 * ����ʱ״̬�ı䴥�����¼�
 */
function doExpOnload(iframe) {
	if (iframe.src == null || iframe.src == contextPath + "/blank.htm") {
		return;
	}
	// if (iframe.readyState == 'complete') {
	odin.unmask(); // ȥ����Ӱ
	// expReadyState = iframe.readyState;
	// }else if (iframe.readyState == 'interactive' && expReadyState ==
	// "complete") {
	// odin.unmask(); // ȥ����Ӱ
	// expReadyState = "";
	// getIFrameWin("iframe_win_pup").doCloseWin();
	// }
}
/**
 * ����������ӡ
 */
function doRepsPrint() {
	repsPrintNext("printIframe", "div_2");
}

/**
 * �����ӡ
 */
function doRepPrint() {
	doPrintIframe();
}

/**
 * ��ӡ����ΪprintIframe��iframe
 */
function doPrintIframe() {
	printIframe("printIframe");
}

/**
 * ��ӡָ��iframe
 */
function printIframe(iframe) {
	getIFrameWin(iframe).focus();
	getIFrameWin(iframe).document.execCommand('print', false, null);
}

/**
 * �����ѯ
 */
function doRepQuery() {
	repQuery("printIframe", "div_1");
}

/**
 * ���ر����ѯ����ӡ
 */
function doHiddenRepPrint() {
	repType = "0"; // ����ģʽ
	repQuery("printIframe", "div_2");
	repType = ""; // ��ԭ
}

/**
 * grid����������ѯ���Զ���ӡ
 */
var printRowIndex = 0;
function repsPrintNext(iframe, div) {
	var repIframe = document.getElementById(iframe);
	var src = repurl;
	if (src.indexOf("?") >= 0) {
		src = src.substr(0, src.indexOf("?"));
	}
	src = src + "?&";
	src = src + "REPQUERYNAMEE='Q001'&"; // Ĭ��ֵ����������
	src = src + "SHOWMODLE=REPORT&"; // Ĭ��ֵ����ʾģʽ
	src = src + "CHARTID=ALL&"; // Ĭ��ֵ��ͼ��ID
	src = src + "AUTO_RUN=YES&"; // Ĭ��ֵ���Զ�����
	var grid = Ext.getCmp(div);
	var store = grid.store;
	var gridColumnModel = grid.getColumnModel();
	var dataIndex = new Array(gridColumnModel.getColumnCount());
	for (var i = 0; i < gridColumnModel.getColumnCount(); i++) {
		dataIndex[i] = gridColumnModel.getDataIndex(i);
	}
	var selectionModel = grid.getSelectionModel();
	if (selectionModel.getCount() == 0) {
		odin.alert("��ѡ��Ҫ��ӡ�����ݣ�");
		return;
	}
	for (var j = printRowIndex; j < store.getCount(); j++) { // ��ӡһ�У�����break
		if (!selectionModel.isSelected(j)) {// δѡ��
			continue;
		}
		printmsg = "���ڴ�ӡ��" + (j + 1) + "����Ϣ...";
		var recode = store.getAt(j);
		for (var i = 0; i < gridColumnModel.getColumnCount(); i++) {
			var name = dataIndex[i];
			if (name == "") {
				continue;
			}
			var value = recode.get(name);
			var param = name + "=" + value + "&";
			var index = src.indexOf("&" + name + "=");
			if (index >= 0) {
				var repvalue = src.substr(index, src.indexOf("&", index + 1) - index + 1);
				src = src.replace(repvalue, param);
			} else {
				src = src + param;
			}
		}
		src = src.replace("?&", "?");
		src = src.substr(0, src.length - 1);
		getIFrameWin(iframe).focus();
		if (isNeedSign) {
			src = src.replace("http", "https");
		}
		repIframe.src = src; // ҳ�滻��
		printRowIndex = j + 1; // �´δ�ӡ��ʼ��
		break;
	}
	if (j == store.getCount()) { // ��ӡ��ɣ����³�ʼ��
		printRowIndex = 0;
	}
}

/**
 * �����ѯ
 */
function repQuery(iframe, div) {
	var repIframe = document.getElementById(iframe);
	var src = repurl;
	var queryDiv = document.getElementById(div);
	if (src.indexOf("?") >= 0) {
		src = src.substr(0, src.indexOf("?"));
	}
	src = src + "?&";
	src = src + "REPQUERYNAMEE='Q001'&"; // Ĭ��ֵ����������
	src = src + "SHOWMODLE=REPORT&"; // Ĭ��ֵ����ʾģʽ
	src = src + "CHARTID=ALL&"; // Ĭ��ֵ��ͼ��ID
	src = src + "AUTO_RUN=YES&"; // Ĭ��ֵ���Զ�����
	for (var n = 0; n < tagNames.length; n++) {
		var inputList = queryDiv.getElementsByTagName(tagNames[n]);
		for (var j = 0; j < inputList.length; j++) {
			var inputObj = inputList.item(j);
			var index = src.indexOf("&" + inputObj.name + "=");
			var name = inputObj.name;
			var value = inputObj.value;
			if (Ext.getCmp(name + "_combo")) {
				if (value == "all") {
					value = "";
				}
			}
			var param = name + "=" + value + "&";
			if (index >= 0) {
				var repvalue = src.substr(index, src.indexOf("&", index + 1) - index + 1);
				src = src.replace(repvalue, param);
			} else {
				src = src + param;
			}
		}
	}
	src = src.replace("?&", "?");
	src = src.substr(0, src.length - 1);
	getIFrameWin(iframe).focus();
	if (isNeedSign) {
		src = src.replace("http", "https");
	}
	repIframe.src = src; // ҳ�滻��
}

/**
 * ����iframe��onload�¼�����
 */
function doRepOnload(item) {
	if (item.src == contextPath + "/blank.htm") {
		return;
	}
	if (item.src.indexOf("?") == -1) {
		item.src = contextPath + "/blank.htm";
		return;
	}
	if (document.getElementById("div_2")) {
		item.style.heigth = 0;
		return;
	}
	autoResize(item, "div_1");
}

/**
 * iframe�Զ�����λ��
 */
function autoResize(item, div) {
	item.style.height = 415 - document.getElementById(div).offsetHeight;
}

/**
 * ����������
 */
function doRepClear() {
	reflush();
}

var repType = ""; // ��������::0���ش�ӡ��1���Ŵ�ӡ��2������ӡ
/**
 * ȡ��������::1���Ŵ�ӡ��2������ӡ
 */
function getRepType() {
	if (repType != "") {
		return repType;
	}
	if (document.getElementById("div_2")) {
		return "2";
	} else if (document.getElementById("div_1")) {
		return "1";
	}
}

/**
 * ȡ����Ľ������
 */
function getRepCount() {
	return getIFrameWin("printIframe").getCount();
}

function round(Dight, How) {
	Dight = Math.round(Dight * Math.pow(10, How)) / Math.pow(10, How);
	return Dight;
}

/**
 * ��ʽ������
 */
function renderInt(value) {
	if (value == 'undefined' || value == null) {
		value = 0;
	}
	var str = formatMoney(new String(parseFloat(value).toFixed(0)));
	return str.substr(0, str.indexOf('.'));
}
/**
 * ��ʽ������
 */
function renderMoney(value) {
	if (value == 'undefined' || value == null) {
		value = 0;
	}
	value = Number(new String(value).replace(/,/g, ""));
	return formatMoney(new String(parseFloat(value).toFixed(2)));
}

/**
 * ��ʽ��Ǯ
 * 
 */
function formatMoney(s) {
	s = s.toString();
	var flag = "";
	if (s.substring(0, 1) == "-") {
		flag = "-";
		s = s.substring(1);
	}
	if (/[^0-9\.]/.test(s))
		return "invalid value:" + s;
	s = s.replace(/^(\d*)$/, "$1.");
	s = (s + "00").replace(/(\d*\.\d\d)\d*/, "$1");
	s = s.replace(".", ",");
	var re = /(\d)(\d{3},)/;
	while (re.test(s))
		s = s.replace(re, "$1,$2");
	s = s.replace(/,(\d\d)$/, ".$1");
	s = s.replace(/^\./, "0.");
	s = flag + s;
	return s;
}

/**
 * У�����ֵ�����
 */
function isYM(value) {
	value = value.toString();
	if (value.length != 6) {
		return "���ȱ���Ϊ6λ��";
	}
	if (value.substr(4) > 12) {
		return "�·ݲ��ܴ���12��";
	}
	if (value.substr(4) == 0) {
		return "�·ݲ���Ϊ00��";
	}
	return true;
}

/**
 * У�����ڵĸ�ʽ
 */
function isBeforeSysdate(value) {
	if (renderDate(value) > renderDate(odin.getSysdate())) {
		return "��������ڲ��ܴ��ڵ�ǰ���ڣ�";
	}
	return true;
}

/**
 * ����divƴװsql��where�������ŵ���̨
 */
/*
 * function sqlWhereByDiv(div) { var retStr = ""; for (var n = 0; n <
 * tagNames.length; n++) { var inputList =
 * document.getElementById(div).getElementsByTagName(tagNames[n]); for (var j =
 * 0; j < inputList.length; j++) { if (inputList.item(j).name &&
 * inputList.item(j).name.indexOf("-") == -1) { var inputItem =
 * inputList.item(j); var inputItemName = inputItem.name.toLowerCase(); if
 * (inputItemName.indexOf("psquery") == 0 || inputItemName.indexOf("cpquery") ==
 * 0 || inputItemName.indexOf("psidquery") == 0 ||
 * inputItemName.indexOf("psidnew") == 0) {// �����ֶβ����� continue; } if
 * (inputItemName.indexOf("nonem") != -1 || inputItemName.indexOf("_s") != -1 ||
 * inputItemName.indexOf("_combo") != -1) { continue; } var value1 =
 * inputItem.value; var isSelectValue = (Ext.getCmp(inputItemName + "_combo") !=
 * null); if (inputItem.type == "checkbox") { value1 = inputItem.checked; } if
 * (Ext.getCmp(inputItem.name)) { // ��ǩ���� value1 =
 * Ext.getCmp(inputItem.name).getValue(); } if (isSelectValue) { value1 = "'" +
 * value1 + "'"; } // ������allΪȫ��������where���� if (isSelectValue && value1 != null &&
 * value1.toString().toLowerCase() == "'all'") { value1 = "''"; } var value2 =
 * value1; // ȡvalue2 if (document.getElementById(inputItemName + "_s")) { var
 * inputItem2 = document.getElementById(inputItemName + "_s"); value2 =
 * inputItem2.value; if (inputItem2.type == "checkbox") { value2 =
 * inputItem2.checked; } if (Ext.getCmp(inputItem2.name)) { // ��ǩ���� value2 =
 * Ext.getCmp(inputItem2.name).getValue(); } if (isSelectValue) { value2 = "'" +
 * value2 + "'"; } // ������allΪȫ��������where���� if (isSelectValue && value2 != null &&
 * value2.toString().toLowerCase() == "all") { value2 = "''"; } } else if
 * (document.getElementById(inputItemName + "_S")) { var inputItem2 =
 * document.getElementById(inputItemName + "_S"); value2 = inputItem2.value; if
 * (inputItem2.type == "checkbox") { value2 = inputItem2.checked; } if
 * (Ext.getCmp(inputItem2.name)) { // ��ǩ���� value2 =
 * Ext.getCmp(inputItem2.name).getValue(); } if (isSelectValue) { value2 = "'" +
 * value2 + "'"; } // ������allΪȫ��������where���� if (isSelectValue && value2 != null &&
 * value2.toString().toLowerCase() == "all") { value2 = "''"; } }
 * 
 * var col = inputItemName; var index = inputItemName.indexOf("_"); if (index !=
 * -1) { col = inputItemName.substring(index + 1) + "." +
 * inputItemName.substring(0, index); } retStr = retStr + sqlWhere(col, value1,
 * value2); } } } return retStr; }
 */
/**
 * ������ƴװsql��where�������ŵ���̨
 */
/*
 * function sqlWhere(col, value1, value2) { if (value1 != null && (value1 == "" ||
 * value1 == "''")) { value1 = null; } if (value2 != null && (value2 == "" ||
 * value2 == "''")) { value2 = null; } if (value1 == null && value2 == null) {
 * return ""; } var stringValue1 = ""; var stringValue2 = ""; var field = col;
 * var index = col.indexOf("."); if (index != -1) { field = col.substring(index +
 * 1) + "_" + col.substring(0, index); } var type = getFieldType(field); var
 * retStr = ""; var isSelectValue = false; // ת����String���� if (value1 != null) {
 * if (type == "string") { if (value1.toString().indexOf("'") != -1) {
 * isSelectValue = true; stringValue1 = value1.toString().trim(); } else {
 * stringValue1 = "'" + value1.toString().trim() + "'"; } } else if (type ==
 * "number") { stringValue1 = value1.toString(); } else if (type == "date") {
 * stringValue1 = "to_date('" + renderDate(value1) + "','yyyy-mm-dd')"; } } if
 * (value2 != null) { if (type == "string") { if (value2.toString().indexOf("'") !=
 * -1) { isSelectValue = true; stringValue2 = value2.toString().trim(); } else {
 * stringValue2 = "'" + value2.toString().trim() + "'"; } } else if (type ==
 * "number") { stringValue2 = value2.toString(); } else if (type == "date") {
 * stringValue2 = "to_date('" + renderDate(value2) + " 23:59:59','yyyy-mm-dd
 * hh24:mi:ss')"; } } if (stringValue1 == stringValue2) { if (type == "string") {
 * if (isSelectValue) { // ���������⴦�� '1'Ϊ='1' '1,2'Ϊin('1','2') if
 * (stringValue1.indexOf(",") == -1) { // ����������= retStr = "@_WPcbvFuwN1Y=_@" +
 * col + "@_tcNkNHzlSVk=_@" + stringValue1 + "@_rHmXV9CvNJA=_@" + retStr; } else
 * {// ���������in retStr = "@_WPcbvFuwN1Y=_@" + col + "@_j3CplyExdf0=_@" +
 * stringValue1.replace(",", "','") + "@_u54qiAZC3aI=_@" + retStr; } } else {
 * stringValue1 = stringValue1.substring(0, stringValue1.length - 1) +
 * "@_SWDwiDCBke8=_@"; retStr = "@_WPcbvFuwN1Y=_@" + col + "@_xXS9SHcqhWs=_@" +
 * stringValue1 + "@_rHmXV9CvNJA=_@" + retStr; } } else { retStr =
 * "@_WPcbvFuwN1Y=_@" + col + "@_tcNkNHzlSVk=_@" + stringValue1 +
 * "@_rHmXV9CvNJA=_@" + retStr; } } else if (stringValue2 == null ||
 * stringValue2 == "") { retStr = "@_WPcbvFuwN1Y=_@" + col + "@_EoiqPtx2gNw=_@" +
 * stringValue1 + "@_rHmXV9CvNJA=_@" + retStr; } else if (stringValue1 == null ||
 * stringValue1 == "") { retStr = "@_WPcbvFuwN1Y=_@" + col + "@_9NI97crh0Ec=_@" +
 * stringValue2 + "@_rHmXV9CvNJA=_@" + retStr; } else { retStr =
 * "@_WPcbvFuwN1Y=_@" + col + "@_Hkohqv9in/EromMRdtkOag==_@" + stringValue1 +
 * "@_6J6RoUYuJHM=_@" + stringValue2 + "@_rHmXV9CvNJA=_@" + retStr; } return
 * retStr; }
 */

/**
 * grid����һ��
 * 
 */
function doAddRow(gridId) {
	if (Ext.getCmp(gridId) != null && Ext.getCmp(gridId).store != null) {
		var store = Ext.getCmp(gridId).store;
		var rowIndex = store.getCount();
		var record = {};
		var gridColumnModel = Ext.getCmp(gridId).getColumnModel();
		for (var j = 0; j < gridColumnModel.getColumnCount(); j++) {
			var colName = gridColumnModel.getDataIndex(j);
			if (colName != null && colName != "") {
				eval("record." + colName + " = ''");
			}
		}
		record.rowstatus = "I";
		store.add(new Ext.data.Record(record));
		Ext.getCmp(gridId).getView().refresh();// ���´������е�render�¼�
	}
}
/**
 * grid��ָ����ǰ����һ��
 * 
 */
function doInsertRow(gridId, rowIndex) {
	if (Ext.getCmp(gridId) != null && Ext.getCmp(gridId).store != null) {
		if (rowIndex == null) {
			rowIndex = 0;
		}
		var store = Ext.getCmp(gridId).store;
		var record = {};
		var gridColumnModel = Ext.getCmp(gridId).getColumnModel();
		for (var j = 0; j < gridColumnModel.getColumnCount(); j++) {
			var colName = gridColumnModel.getDataIndex(j);
			if (colName == "rowstatus") {
				record.rowstatus = "I";
			} else if (colName != null && colName != "") {
				eval("record." + colName + " = ''");
			}
		}
		store.insert(rowIndex, new Ext.data.Record(record));
	}
}

/**
 * ɾ��ָ����
 */
function doDeleteRow(gridId, rowIndex) {
	var store = Ext.getCmp(gridId).store;
	store.remove(store.getAt(rowIndex));
}

function doClickDeleteRow(btn, clickParams, currentRow, colName) {
	var tmp = btn;
	odin.confirm("ȷ��Ҫɾ�����У�", function(btn) {
				if (btn == "ok") {
					var gridId = getParentDiv(colName);
					doDeleteRow(gridId, currentRow);
					var store = Ext.getCmp(gridId).store;
					for (var i = 0; i < store.getCount(); i++) {
						store.getAt(i).set("rowIndex", i);
					}
					if (tmp == null || tmp == 'null' || tmp == '') {
					} else {
						if (currentRow == null) {
							currentRow = 0;
						}
						commParams = {};
						if (clickParams != null) {
							commParams.clickParams = clickParams;
						}
						commParams.currentEvent = "click";
						commParams.currentValue = tmp;
						commParams.currentRow = currentRow;
						reflushAfterDelete = false;
						doSubmit(document.commForm, doSuccess, doFailure);
					}
				}
			});
}

/**
 * ɾ�����е�render����
 */

function renderDeleteRow(value, params, record, rowIndex, colIndex, ds) {
	var clickParams;
	var valueArray;
	var clickId = null; // ��ťid
	if (value != null && value != 'null' || value != "") {
		value = new String(value);
	}
	valueArray = value.split(",");
	if (valueArray.length > 2) {
		clickId = valueArray[2]; // ��ťid
	}
	if (valueArray.length > 3) {
		clickParams = new Array(valueArray.length - 3);
	}
	for (var i = 3; i < valueArray.length; i++) {
		clickParams[i - 3] = valueArray[i];
	}

	if (value == "false") {
		return "<a href='#' style='color:#CCCCCC'>ɾ��</a>";
	} else {
		return "<a href='#' class='render' onclick='doClickDeleteRow(\"" + clickId + "\",\"" + Ext.encode(clickParams).replace(/"/g, "\\\"") + "\"," + rowIndex + ",\"" + Ext.getCmp(ds.baseParams.div).getColumnModel().getDataIndex(colIndex) + "\")'>ɾ��</a>";
	}
}

/**
 * ȡ�õ�½�û���
 */
function getCurrentLoginName() {
	if (parent != this && parent.getCurrentLoginName) {
		return parent.getCurrentLoginName();
	} else if (odin.isWorkpf) {
	    if (typeof(qtobj) == "undefined") {
			return parent.qtobj.getCache("CURUSER").loginname;
		}else{
			return qtobj.getCache("CURUSER").loginname;
		}
	} else {
		return currentLoginName;
	}
}

/**
 * ȡ�õ�ǰϽ��
 */
function getCurrentAab301() {
	if (parent != this && parent.getCurrentAab301) {
		return parent.getCurrentAab301();
	} else if (odin.isWorkpf) {
		return qtobj.getCache("CURUSER").userOtherInfo.commform.currentAab301;
	} else {
		return currentAab301;
	}
}

/**
 * ȡ�õ�ǰͳ����
 */
function getCurrentAaa027() {
	if (parent != this && parent.getCurrentAaa027) {
		return parent.getCurrentAaa027();
	} else if (odin.isWorkpf) {
		return qtobj.getCache("CURUSER").userOtherInfo.commform.currentAaa027;
	} else {
		return currentAaa027;
	}
}

/**
 * ȡ��psquery�Ļ���ֵ
 */
function getPsqueryBuffer() {
	if (parent != this && parent.getPsqueryBuffer) {
		return parent.getPsqueryBuffer();
	} else if (odin.isWorkpf) {
		return qtobj.getCache("psqueryBuffer");
	} else {
		return psqueryBuffer;
	}
}
/**
 * ����psquery�Ļ���ֵ
 */
function setPsqueryBuffer(psquery) {
	if (parent != this && parent.setPsqueryBuffer) {
		parent.setPsqueryBuffer(psquery);
	} else if (odin.isWorkpf) {
		qtobj.setCache("psqueryBuffer", psquery);
	} else {
		psqueryBuffer = psquery;
	}
}
/**
 * ȡ��cpquery�Ļ���ֵ
 */
function getCpqueryBuffer() {
	if (parent != this && parent.getCpqueryBuffer) {
		return parent.getCpqueryBuffer();
	} else if (odin.isWorkpf) {
		return qtobj.getCache("cpqueryBuffer");
	} else {
		return cpqueryBuffer;
	}
}
/**
 * ����cpquery�Ļ���ֵ
 */
function setCpqueryBuffer(cpquery) {
	if (parent != this && parent.setCpqueryBuffer) {
		parent.setCpqueryBuffer(cpquery);
	} else if (odin.isWorkpf) {
		qtobj.setCache("cpqueryBuffer", cpquery);
	} else {
		cpqueryBuffer = cpquery;
	}
}

/**
 * ȡ��psidquery�Ļ���ֵ
 */
function getPsidqueryBuffer() {
	if (parent != this && parent.getPsidqueryBuffer) {
		return parent.getPsidqueryBuffer();
	} else if (odin.isWorkpf) {
		return qtobj.getCache("psidqueryBuffer");
	} else {
		return psidqueryBuffer;
	}
}
/**
 * ����psidquery�Ļ���ֵ
 */
function setPsidqueryBuffer(psidquery) {
	if (parent != this && parent.setPsidqueryBuffer) {
		parent.setPsidqueryBuffer(psidquery);
	} else if (odin.isWorkpf) {
		qtobj.setCache("psidqueryBuffer", psidquery);
	} else {
		psidqueryBuffer = psidquery;
	}
}

/**
 * ���������������õ���ȷ�ĳ������ ˵����javascript�ĳ�����������������������������ʱ���Ƚ����ԡ�����������ؽ�Ϊ��ȷ�ĳ��������
 * ���ã�accDiv(arg1,arg2) ����ֵ��arg1����arg2�ľ�ȷ���
 */
function accDiv(arg1, arg2) {
	var t1 = 0, t2 = 0, r1, r2;
	try {
		t1 = arg1.toString().split(".")[1].length
	} catch (e) {
	}
	try {
		t2 = arg2.toString().split(".")[1].length
	} catch (e) {
	}
	with (Math) {
		r1 = Number(arg1.toString().replace(".", ""))
		r2 = Number(arg2.toString().replace(".", ""))
		return (r1 / r2) * pow(10, t2 - t1);
	}
}
/**
 * �˷������������õ���ȷ�ĳ˷���� ˵����javascript�ĳ˷������������������������˵�ʱ���Ƚ����ԡ�����������ؽ�Ϊ��ȷ�ĳ˷������
 * ���ã�accMul(arg1,arg2) ����ֵ��arg1����arg2�ľ�ȷ���
 */
function accMul(arg1, arg2) {
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length
	} catch (e) {
	}
	try {
		m += s2.split(".")[1].length
	} catch (e) {
	}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
}
/**
 * �ӷ������������õ���ȷ�ļӷ���� ˵����javascript�ļӷ������������������������ӵ�ʱ���Ƚ����ԡ�����������ؽ�Ϊ��ȷ�ļӷ������
 * ���ã�accAdd(arg1,arg2) ����ֵ��arg1����arg2�ľ�ȷ���
 */
function accAdd(arg1, arg2) {
	var r1, r2, m;
	try {
		r1 = arg1.toString().split(".")[1].length
	} catch (e) {
		r1 = 0
	}
	try {
		r2 = arg2.toString().split(".")[1].length
	} catch (e) {
		r2 = 0
	}
	m = Math.pow(10, Math.max(r1, r2))
	return (arg1 * m + arg2 * m) / m
}
/**
 * ���������������õ���ȷ�ļ������ ˵����javascript�ļ������������������������������ʱ���Ƚ����ԡ�����������ؽ�Ϊ��ȷ�ļ��������
 * ���ã�accSub(arg1,arg2) ����ֵ��arg1��ȥarg2�ľ�ȷ���
 */
function accSub(arg1, arg2) {
	var r1, r2, m, n;
	try {
		r1 = arg1.toString().split(".")[1].length
	} catch (e) {
		r1 = 0
	}
	try {
		r2 = arg2.toString().split(".")[1].length
	} catch (e) {
		r2 = 0
	}
	m = Math.pow(10, Math.max(r1, r2));
	// ��̬���ƾ��ȳ���
	n = (r1 >= r2) ? r1 : r2;
	return ((arg1 * m - arg2 * m) / m).toFixed(n);
}

/**
 * ���ø�����
 */
function setParentWin(parentWin) {
	this.parentWin = parentWin;
}

/**
 * ȡ��div��item
 */
function getDivItem(div, item) {
	if (typeof(div) == 'string') {
		div = document.getElementById(div);
	}
	var items = div.getElementsByTagName("*");
	for (var i = 0; i < items.length; i++) {
		if (items[i].id == item) {
			return items[i];
		}
	}
}

/**
 * ����IE��Firefox��iframe���ڻ�ȡ����
 */
function getIFrameWin(id) {
	return document.getElementById(id).contentWindow || document.frames[id];
}
/**
 * ����firefox�� outerHTML ʹ�����´����firefox����ʹ��element.outerHTML
 */
if (window.HTMLElement) {
	HTMLElement.prototype.__defineSetter__("outerHTML", function(sHTML) {
				var r = this.ownerDocument.createRange();
				r.setStartBefore(this);
				var df = r.createContextualFragment(sHTML);
				this.parentNode.replaceChild(df, this);
				return sHTML;
			});

	HTMLElement.prototype.__defineGetter__("outerHTML", function() {
				var attr;
				var attrs = this.attributes;
				var str = "<" + this.tagName.toLowerCase();
				for (var i = 0; i < attrs.length; i++) {
					attr = attrs[i];
					if (attr.specified)
						str += " " + attr.name + '="' + attr.value + '"';
				}
				if (!this.canHaveChildren)
					return str + ">";
				return str + ">" + this.innerHTML + "</" + this.tagName.toLowerCase() + ">";
			});

	HTMLElement.prototype.__defineGetter__("canHaveChildren", function() {
				switch (this.tagName.toLowerCase()) {
					case "area" :
					case "base" :
					case "basefont" :
					case "col" :
					case "frame" :
					case "hr" :
					case "img" :
					case "br" :
					case "input" :
					case "isindex" :
					case "link" :
					case "meta" :
					case "param" :
						return false;
				}
				return true;
			});
}

/**
 * ����ajax����ʱʱ��
 * 
 * @param n
 *            ��ʱʱ�䣬��λΪ��
 */
function setAjaxTimeout(n) {
	odin.ajaxTimeout = n * 1000;
}
function cpqueryTriggerClick(e){
	if(this.disabled){
            return;
        }
        if(this.isExpanded()){
            this.collapse();
            this.el.focus();
        }else {
            this.onFocus({});
            if(this.triggerAction == 'all') {
                this.doQuery(this.allQuery, true);
            } else {
                this.doQuery(this.getRawValue());
            }
            this.el.focus();
        }
    if(e){    
		doOnChange(this,e)
    }
}
//jinwei add for ���ݴ�ӡ -- 2013.8.22
function opsenoToReprint(opseno){
	return "<a href='javascript:reprint("+opseno+")'>���´�ӡ</a>";
}

function reprint(opseno){
	odin.Ajax.request(contextPath+"/sys/PrintAction.do?method=getPrintlog",
  			{opseno:opseno},reprintSuccess);
}

function reprintSuccess(response){
	for(var i=0;i<response.data.length;i++){
		if(confirm("�Ƿ��ӡ��"+response.data[i].title+"����")){
			var preview=true;
			if(response.data[i].preview=="0"){
				preview=false;
			}
			var repid = response.data[i].repid;
			var queryName = response.data[i].queryname;
			var param = response.data[i].param;
			var repmode="3";
	    	if(!preview){
	    		repmode="2";
	    	}
	    	var url=contextPath+"/common/billPrintAction.do?repid="+repid+"&queryname="+queryName+"&param="+encodeURIComponent(encodeURIComponent(param))+"&repmode="+repmode;
	    	window.showModalDialog(url,null,"dialogWidth=800px;dialogHeight=600px");
	    	//window.open(url,"billWin","status=no,toolbar=no,height=600,width=800");
		}
	}
}
// jinwei add for ���ݴ�ӡ end
