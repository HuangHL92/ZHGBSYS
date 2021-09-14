/**
 * @commParams 公共参数
 * @commParams.currentDiv; //当前div名
 * @commParams.currentEvent; //当前事件名
 * @commParams.currentRow; //当前行号
 * @commParams.currentColumn; //当前列名
 * @commParams.currentOriginalValue; //当前项目修改前的值
 * @commParams.currentValue; //当前项目修改后的值
 * @commparams.currentAaz001; //当前登陆的单位内码aaz001
 * @commparams.currentOpseno; //当前草稿箱的opseno
 * @commParams.initParams; //初始化参数数组，父窗口调用时或自定义按钮点击时会有，使用方式为commParams.initParams[0]等
 * @commParams.currentFilePath; //当前文件路径，在导入的时候有值
 * @commParams.currentLoginName; //当前登陆用户名，在后台进行校验，不作实际使用
 *  * getSysType方法返回值固定insiis  20130821 ljd
 */
var commParams = {};
var tempOriginalValue = ""; // 原始值
var listeners = {}; // 事件函数json
var tagNames = new Array("input", "textarea"); // 要取数据的tagName
var inParams = {}; // 传入的参数
var reflushAfterSave = true; // 保存之后的自动刷新
var reflushAfterSaveMsg = "";// 保存后的信息
var reflushAfterSavePrint = "";// 保存后打印的提示
var divsql = {}; // 保存div的sql
/** ****简单导出excel数据********** */
var fileNameSim = "";
var sheetNameSim = "";
var querySQLSim = "";
var headNamesSim = "";
var separatorSim = "";
var downFileUrl = "";
var sortParam = {}; // 排序的参数
var filterParam = {};// 过滤的参数
var isQuery = false; // 是否为query的操作
var currentActionDisabled;
var currentOpseno;
var currentLoginName;
var MDParam;
var tree;
var pagePaneOpstr = "";
var parentWin = parent;
var pageGridSql = {}; // 分页grid的查询sql
var isQuerying = false; // 是否正在进行psquery等标签的查询
var tempParentWin;
var psqueryBuffer = "";
var cpqueryBuffer = "";
var psidqueryBuffer = "";
var fromCommLink = false; // 是否来自公共超链接
var fix = false;// 文件扩展名
/**
 * 根据sql进行excel的导出
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
	doOpenPupWin(contextPath + "/commform/sys/excel/commSimpleExpExcelWindow.jsp", "下载文件", 500, 160);
}

/**
 * 根据grid进行excel的导出
 */
function expExcelFromGrid(gridId, fileName, sheetName, headNames, isFromInterface) {
	if (!Ext.getCmp(gridId)) {
		odin.error("要导出的grid不存在！gridId=" + gridId);
		return;
	}
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	if (store.getCount() == 0) {
		odin.error("没有要导出的数据！");
		return;
	}
	var gridColumnModel = grid.getColumnModel();
	var colsArray = new Array();
	var dataIndex = new Array();
	var columnHeader = new Array();
	var index = 0;
	var noHeaderIndex = 0;
	var jsonType = {};  //用来记录要导出列的类型
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
		columnHeader[index] = gridColumnModel.getColumnHeader(i).replace(/<[^>]+>/g, "").replace(/,/g, "，");
		index++;
	}
	if (isFromInterface != null && isFromInterface != true && isPageGridDiv(gridId)) {// 分页grid处理全部页
		var querySQL = eval("pageGridSql." + gridId);
		if (querySQL == null) {
			odin.error("只有查询出的数据才能导出！");
			return;
		}
		querySQLSim = querySQL;
		if (headNames == null || headNames == "" || headNames == "null") { // 表头
			headNames = {};
			for (var i = 0; i < dataIndex.length; i++) {
				eval("headNames." + dataIndex[i] + "=" + Ext.encode(columnHeader[i]));
			}
			headNames = Ext.encode(headNames);
		}
		var decodeJson; // decode转换
		for (var i = 0; i < dataIndex.length; i++) {
			var col = getGridColumn(gridId, dataIndex[i]);
			if (col.editor) {
				var type = col.editor.field.getXType();
				if (type == "combo") {// 只操作下拉框格式的列
					var decodeStrArray = eval(dataIndex[i] + "_select");
					var decodeStr = "";
					for (var j = 0; j < decodeStrArray.length; j++) {
						decodeStr += "," + decodeStrArray[j][0] + "," + decodeStrArray[j][1];
					}
					decodeStr = decodeStr.substring(1);
					if (decodeStr != "") {// 不为空才操作
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
	} else { // 不分页grid处理界面信息
		if (headNames == null || headNames == "" || headNames == "null") { // 表头
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
					// 修正renderAlt造成的特殊字符
					value = value.replace(/<[^>]+>/g, "");
				}
				eval("json." + dataIndex[j] + "=" + Ext.encode(value));
			}
			jsonArray[i] = json;
		}
		jsonDataSim = Ext.encode(jsonArray);
	}
	if (fileName == null || fileName == "" || fileName == "null") {
		fileNameSim = MDParam.title + "_" + gridId.replace("div_", "区域") + "_" + Ext.util.Format.date(odin.getSysdate(), "Ymd");
	} else {
		fileNameSim = fileName;
	}
	if (sheetName == null || sheetName == "" || sheetName == "null") {
		sheetNameSim = MDParam.title + "_" + gridId.replace("div_", "区域");
	} else {
		sheetNameSim = sheetName;
	}
	headNamesSim = headNames;
	window.jsonTypeSim = odin.encode(jsonType);
	doOpenPupWin(contextPath + "/commform/sys/excel/commSimpleExpExcelWindow.jsp", "下载文件", 500, 160);
}

/**
 * 根据grid进行excel的导出
 */
function expExcelFromGridByRep(gridId, fileName, headNames) {
	var dealType = "exp";
	dealGridByRep(gridId, fileName, headNames, dealType);
}

/**
 * 根据grid进行打印
 */
function printGridByRep(gridId, headNames) {
	var dealType = "query";
	dealGridByRep(gridId, null, headNames, dealType);
}

/**
 * 根据grid进行处理成报表格式，可打印、导出等操作
 */
function dealGridByRep(gridId, fileName, headNames, dealType) {
	var dealName = (dealType == "exp" ? "导出" : "打印");
	if (!Ext.getCmp(gridId)) {
		odin.error("要" + dealName + "的grid不存在！gridId=" + gridId);
		return;
	}
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	if (store.getCount() == 0) {
		odin.error("没有要" + dealName + "的数据！");
		return;
	}
	if (fileName == null || fileName == "" || fileName == "null") {
		fileName = MDParam.title + "_" + gridId.replace("div_", "区域") + "_" + Ext.util.Format.date(odin.getSysdate(), "Ymd");
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
		var header = gridColumnModel.getColumnHeader(i).replace(/<[^>]+>/g, "").replace(/,/g, "，");
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
				// 修正renderAlt造成的特殊字符
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
		doOpenPupWin(url, "打印预览--" + fileName, 0.8, 0.8);
	}
}

/**
 * 根据sql进行text的导出
 */
function expText(querySQL, fileName, separator, headNames, withoutHead) {
	if (fileName == null || fileName == "" || fileName == "null") {
		fileNameSim = MDParam.title + Ext.util.Format.date(odin.getSysdate(), "Ymd") + ".txt";
	} else if (fileName.indexOf(".") == 0) {// 以.开头，即只指定了后缀
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
	doOpenPupWin(contextPath + "/commform/sys/text/commSimpleExpTextWindow.jsp", "下载文件", 500, 160);
}
/**
 * 根据sql进行dbf的导出
 */
function expDbf(querySQL, fileName, headNames) {
	if (fileName == null || fileName == "" || fileName == "null") {
		fileNameSim = MDParam.title + Ext.util.Format.date(odin.getSysdate(), "Ymd") + ".dbf";
	} else if (fileName.indexOf(".") == 0) {// 以.开头，即只指定了后缀
		fileNameSim = MDParam.title + Ext.util.Format.date(odin.getSysdate(), "Ymd") + fileName;
	} else {
		fileNameSim = fileName;
	}
	querySQLSim = querySQL;
	headNamesSim = headNames;
	doOpenPupWin(contextPath + "/commform/sys/dbf/commSimpleExpDbfWindow.jsp", "下载文件", 500, 160);
}

/**
 * 下载文件
 * 
 * fileUrl 可以是相对路径，如/help.doc
 * 也可以是绝对路径,如http://60.190.57.183:8080/sionline/help.doc
 */
function downFile(fileUrl) {
	downFileUrl = fileUrl;
	doOpenPupWin(contextPath + "/commform/sys/excel/commDownFileWindow.jsp", "下载文件", 500, 160);
}

/**
 * 找到已指定字符串开头的信息，包括ret.divName和ret.colName
 */
function findColumnStartWith(startStr) {
	var divList = document.commForm.getElementsByTagName("div");
	for (var i = 0; i < divList.length; i++) {
		divId = divList.item(i).id;
		if ((divId.indexOf("div_") == 0) && (document.getElementById("gridDiv_" + divId) == null)) { // div_开头且不是grid
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
 * 身份证读卡
 * 
 * fileUrl 可以是相对路径，如/help.doc
 * 也可以是绝对路径,如http://60.190.57.183:8080/sionline/help.doc
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
	odin.mask("正在读卡...");
}

/**
 * grid右键菜单
 */
function gridContextmenu(e, grid, hasAllRightMenu) {
	var selectText;
	try {// IE
		selectText = document.selection.createRange().text;
	} catch (ex) { // 非IE
		selectText = document.getSelection();
	}
	if (selectText != "") { // 选择了文本就用默认的右键菜单
		return;
	}
	var expMenuItem = new Ext.menu.Item({
				id : 'exp',
				text : '导出界面数据',
				xtype : 'button',
				iconCls : 'user',
				handler : function() {
					expExcelFromGrid(grid.id, null, null, null, true)
				}
			});
	var expAllMenuItem = new Ext.menu.Item({
				id : 'expAll',
				text : '导出全部数据',
				handler : function() {
					expExcelFromGrid(grid.id, null, null, null, false)
				}
			});
	var setPageSizeMenuItem = new Ext.menu.Item({
				id : 'setPageSize',
				text : '设置每页条数',
				handler : function() {
					setPageSize(grid.id)
				}
			});
	var doSortAllMenuItem = new Ext.menu.Item({
				id : 'doSortAll',
				text : '全部数据排序',
				handler : function() {
					doSortAll(grid.id)
				}
			});
	var doFilterAllMenuItem = new Ext.menu.Item({
				id : 'doFilterAll',
				text : '全部数据过滤',
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
	e.preventDefault();// 屏蔽默认事件
	document.oncontextmenu = function() { // 屏蔽默认右键
		return false;
	}
	rightMenu.on('beforehide', function() { // 在右键菜单关闭时启用默认右键
				document.oncontextmenu = function() {
					return true;
				}
			});
	rightMenu.showAt(e.getXY());
}

var gridIdForSeting;// 要设置的grid的id
/**
 * 每页条数设置
 */
function setPageSize(gridId) {
	if (!Ext.getCmp(gridId)) {
		odin.error("要导出的grid不存在！gridId=" + gridId);
		return;
	}
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	if (store.getCount() == 0) {
		odin.error("请先查询出数据后再进行本操作！");
		return;
	}
	var pageingToolbar = (grid.getBottomToolbar() || grid.getTopToolbar());
	if (pageingToolbar && pageingToolbar.pageSize) {
		gridIdForSeting = gridId;
		var url = contextPath + "/commform/commpages/comm/commSetGrid.jsp";
		doOpenPupWin(url, "设置每页条数", 300, 200);
	} else {
		odin.error("非分页grid不能使用此功能！");
		return;
	}
}

/**
 * 界面初始化事件
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
			if (parent.commParams.initParamsUsedOnce) { // 一次性的需要清空
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
 * 查询按钮的事件
 */
function doQuery() {
	if (repurl != "" && getRepType() == "1") { // 单张打印报表特殊处理
		doRepQuery();
	} else {
		commParams = {};
		commParams.currentEvent = "query";
		doSubmit(document.commForm, doSuccess, doFailure);
	}
}

/**
 * 点击自定义按钮
 * 
 * @param {}
 *            btn 按钮名称
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
 * 点击自定义按钮,提交前会进行非空、符合条件的校验
 * 
 * @param {}
 *            btn 按钮名称
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
	if (checkValid(checkFlag) != true) { // 做校验判断
		doSubmitStatus = {};
		return;
	}
	doSubmit(document.commForm, doSuccess, doFailure);
}

/**
 * 点击批量按钮
 * 
 */
function doBatch() {
	commParams = {};
	commParams.currentEvent = "batch";
	doSubmit(document.commForm, doSuccess, doFailure);
}

/**
 * 点击批量按钮
 * 
 */
function doAfterBatch() {
	commParams = {};
	commParams.currentEvent = "afterBatch";
	// 初始化query的commParams
	initQueryCommParams();
	doSubmit(document.commForm, doSuccess, doFailure);
}

/**
 * 隐藏子弹出窗口触发事件
 */
function doSubhidden(clickParams) {
	commParams = {};
	commParams.currentEvent = "subhidden";
	commParams.clickParams = clickParams;
	doSubmit(document.commForm, doSuccess, doFailure);
}

/**
 * 保存公共场景form触发事件
 */
function doSaveCommScenForm() {
	commParams = {};
	commParams.currentEvent = "saveCommScenForm";
	// 初始化query的commParams
	initQueryCommParams();
	doSubmit(document.commForm, doSuccess, doFailure);
}

/**
 * 初始化query字段的commparams
 */
function initQueryCommParams() {
	// 设isQuery属性,如commParams.isQuery_psquery='false'
	var divList = document.commForm.getElementsByTagName("div");
	for (var i = 0; i < divList.length; i++) {
		divId = divList.item(i).id;
		if ((divId.indexOf("div_") == 0) && (document.getElementById("gridDiv_" + divId) == null)) { // div_开头且不是grid
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
 * 排序
 * 
 * @param {Object}
 *            divName 要排序的div名称
 * @param {Object}
 *            colsName 允许排序的列名，多个用逗号隔开，如"aae135,aac003",不传则默认界面所有允许排序的列
 */
function doSortAll(divName, colsName) {
	if (Ext.getCmp(divName).store.baseParams.querySQL == null) {
		odin.alert("请先查询出结果再进行排序！");
		return;
	}
	sortParam.divName = divName;
	if (colsName == null) { // 如果为空，则取所有允许排序的列
		var gridColumnModel = Ext.getCmp(divName).getColumnModel();
		if (!gridColumnModel) {
			odin.alert("没有可排序的列！");
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
	doOpenPupWin(contextPath + "/commform/commpages/comm/commSort.jsp", "排序窗口", 420, 300);
}
/**
 * 过滤
 * 
 * @param {Object}
 *            divName 要过滤的div名称
 * @param {Object}
 *            colsName 允许过滤的列名，多个用逗号隔开，如"aae135,aac003"
 */
function doFilterAll(divName, colsName) {
	if (Ext.getCmp(divName).store.baseParams.querySQL == null) {
		odin.alert("请先查询出结果再进行过滤！");
		return;
	}
	filterParam.divName = divName;
	if (colsName == null) { // 如果为空，则取所有允许排序的列
		var gridColumnModel = Ext.getCmp(divName).getColumnModel();
		if (!gridColumnModel) {
			odin.alert("没有可过滤的列！");
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
	doOpenPupWin(contextPath + "/commform/commpages/comm/commFilter.jsp", "过滤窗口", 650, 400);
}
/**
 * 报表打印
 */
function doPrint() {
	if (repurl != "") { // 报表特殊处理
		if (getRepType() == "1") { // 单张
			doRepPrint();
		} else if (getRepType() == "2") { // 批量
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
 * 导出按钮的事件
 */
function doExp() {
	commParams = {};
	commParams.currentEvent = "exp";
	doSubmit(document.commForm, doSuccess, doFailure);
}

/**
 * 通用导入操作
 */
function doCommImp(fileType, param) {
	if (currentOpseno != null) { // 草稿箱不允许操作
		odin.error('该页面不允许进行本操作！');
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
 * 导入之前事件（成功后进行文件上传）
 */
function doImp(fileType, param) {
	if (currentOpseno != null) { // 草稿箱不允许操作
		odin.error('该页面不允许进行本操作！');
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
 * 导入到页面的导入之前事件（成功后进行文件上传）
 */
function doImp1(fileType, param) {
	if (currentOpseno != null) { // 草稿箱不允许操作
		odin.error('该页面不允许进行本操作！');
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
 * 导入并处理的导入之前事件（成功后进行文件上传）
 */
function doImp2(fileType, param) {
	if (currentOpseno != null) { // 草稿箱不允许操作
		odin.error('该页面不允许进行本操作！');
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
 * 文件上传
 */
function doUpload() {
	doSubmitFromWaiting(); // 提交等待的事件
	doOpenPupWin(contextPath + "/commform/sys/excel/commImpFileWindow.jsp", "请选择要上传的文件", 500, 160);
}

/**
 * 导入之后事件
 */
function doAfterImp() {
	var value = commParams.currentValue;
	var event = commParams.currentEvent;
	commParams = {};
	if (event == "beforeCommImp") {
		commParams.currentEvent = "commImp";
		// 初始化query的commParams
		initQueryCommParams();
		// 查询进度条
		window.setTimeout('updateProgressbar()', 1000);
	} else {
		commParams.currentEvent = "afterImp";
	}
	commParams.currentValue = value;
	doSubmit(document.commForm, doSuccess, doFailure);
}

/**
 * 导入并处理完成后
 */
function doImpSuccess() {
	var altmsg = "";
	if (commParams.currentValue == "1") {
		altmsg = "数据已成功导入到界面，请核对！";
	} else if (commParams.currentValue == "2") {
		altmsg = "数据已成功导入并保存！";
		if (getSysType() == "sionline") {
			altmsg = altmsg + "请到草稿箱核对后进行申报！";
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
 * 更新进度条信息
 */
function updateProgressbar() {
	doSubmitFromWaiting(); // 提交等待的事件
	commParams = {};
	commParams.currentEvent = "updateProgressbar";
	doSubmit(document.commForm, doSuccessForUpdateProgressbar, null);

}
/**
 * 查询成功后的处理
 */
function doSuccessForUpdateProgressbar(response) {
	var progress = Ext.decode(response.data.hashMap.progress)[0];
	var percent = progress.percent;
	var msg = progress.msg;
	msg = odin.toHtmlString(msg);
	if (percent < 1) {
		parent.odin.progress(percent, round(percent * 100, 1) + "%已完成！", msg);
		// 未结束一直轮询
		window.setTimeout('updateProgressbar()', 500);
	} else {// 结束
		parent.odin.closeMsgBox();
	}
	doSubmitFromWaiting(); // 提交等待的事件
}

/**
 * 清空按钮所做的事情
 */
function doClear() {
	odin.formClear(document.commForm);
}

/**
 * 清空Div里面的内容
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
 * 触发指定列的onchange事件
 * 
 * @param {}
 *            divId div名称
 * @param {}
 *            itemId 列名称
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
 * 非公共change的触发事件
 */
function doOnChangeDateOther(item) {
	if (!doOnChangeCheckDate(item)) {
		return;
	}
	var name = item.name || item.getId();
	// setFocusNext(name);
}
/**
 * 检查日期格式是否正确
 */
function doOnChangeCheckDate(item) {
	var name = item.name;
	if (!name) {
		name = item.getId();
		item = document.getElementById(name);
	}
	// date格式的特殊处理(在标签处理之前先处理，否则yyyyMMdd格式的会出错)
	if (getFieldType(name) == "date") {
		if (Ext.getCmp(name).format == "Y-m-d H:i:s") { // 到秒
			if (checkDateTime(name) == false) {
				return false;
			}
		} else { // 到日
			if (checkDate(name) == false) {
				return false;
			}
		}
	}
	return true;
}

/**
 * html格式的onchanged事件
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
	if (Ext.getCmp(name)) { // 标签处理
		var validator = false;
		try {
			validator = Ext.getCmp(name).validator();
		} catch (e) {
			validator = true;
		}
		if (!Ext.getCmp(name).isValid() && validator && name.indexOf("psquery") != 0 && name.indexOf("cpquery") != 0 && name.indexOf("psidquery") != 0 && name.indexOf("psidnew") != 0) {// 标签校验通不过且非后台校验且不是cpquery,psquery,psidquery,psidnew
			return;
		}
		// date格式的特殊处理(在标签处理之前先处理，否则yyyyMMdd格式的会出错)
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
	// select格式的特殊处理
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
	// checkbox格式的特殊处理
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
	if (commParams.isQuery == "true") { // 需要查询时才进行查询
		isQuerying = true; // 正在查询，其他程序做控制时使用
		// query的处理
		if (name.indexOf("psquery") == 0) {// 以psquery开头的人员查询
			odin.mask("正在查询..."); // 加上阴影
			doOpenPupWin("/pages/commAction.do?method=PSQuery", "人员查询 -- 选择好后双击或回车进行确定 -- 快捷键：← ↑ ↓ → Enter Esc", 0.8, (Ext.isIE ? 323 : 313), commParams.currentValue + "," + (MDParam == null || MDParam.uptype == "" ? "00" : MDParam.uptype) + "," + commParams.checkRate);
			doHiddenPupWin();// 先隐藏
			onQueryPupWindowCloseClick();// 关闭按钮的事件
			return;
		} else if (name.indexOf("cpquery") == 0) {// 以cpquery开头的单位查询
			odin.mask("正在查询..."); // 加上阴影
			doOpenPupWin("/pages/commAction.do?method=CPQuery", "单位查询 -- 选择好后双击或回车进行确定 -- 快捷键：← ↑ ↓ → Enter Esc", 0.8, (Ext.isIE ? 323 : 313), commParams.currentValue + "," + (MDParam == null || MDParam.uptype == "" ? "00" : MDParam.uptype) + "," + commParams.checkRate);
			doHiddenPupWin();// 先隐藏
			onQueryPupWindowCloseClick();// 关闭按钮的事件
			return;
		} else if (name.indexOf("psidquery") == 0) {// 以psidquery开头的人员身份查询
			odin.mask("正在查询..."); // 加上阴影
			doOpenPupWin("/pages/commAction.do?method=PSIDQuery", "人员身份查询 -- 选择好后双击或回车进行确定 -- 快捷键：← ↑ ↓ → Enter Esc", 0.8, (Ext.isIE ? 323 : 313), commParams.currentValue + "," + (MDParam == null || MDParam.uptype == "" ? "00" : MDParam.uptype) + "," + commParams.checkRate);
			doHiddenPupWin();// 先隐藏
			onQueryPupWindowCloseClick();// 关闭按钮的事件
			return;
		} else if (name.indexOf("psidnew") == 0) {// 以psidnew开头的人员身份新增
			odin.mask("正在查询..."); // 加上阴影
			doOpenPupWin("/pages/commAction.do?method=PSIDNew", "人员身份新增 -- 选择好后双击或回车进行确定,按关闭或Esc进行另外身份新增 -- 快捷键：← ↑ ↓ → Enter Esc", 0.8, (Ext.isIE ? 323 : 313), commParams.currentValue + "," + (MDParam == null || MDParam.uptype == "" ? "00" : MDParam.uptype) + "," + commParams.checkRate);
			doHiddenPupWin();// 先隐藏
			onQueryPupWindowCloseClick();// 关闭按钮的事件
			return;
		}
	}
	doSubmit(document.commForm, doSuccess, doFailure);
}

/**
 * 加上查询窗口的关闭按钮函数
 */
function onQueryPupWindowCloseClick() {
	var windowId = "win_pup";
	var pupWindow = Ext.getCmp(windowId);
	pupWindow.tools.close.dom.onclick = closeQueryPupWindow;
}
/**
 * 去掉窗口的关闭按钮函数
 */
function unPupWindowCloseClick() {
	var windowId = "win_pup";
	var pupWindow = Ext.getCmp(windowId);
	pupWindow.tools.close.dom.onclick = null;
}

/**
 * 人员搜索后执行的动作
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
 * 检查输入的项是否为下拉框里面
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
			if (combo.checkSelectComplete == null || combo.checkSelectComplete == true) {// 已经在执行了则不再执行
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
 * 检查输入项是否在下拉框里面,如不在则设为空
 */
function checkAndSetSelectValue(objId) {
	var ret = checkSelectValueInList(objId);
	if (ret != true) {
		odin.setSelectValue(objId, "");
	}
}
/**
 * 检查输入的项是否为下拉框里面
 */
function checkSelectValueInList(objId) {
	if (!document.getElementById(objId + "_combo")) {
		return true;
	}
	if (document.getElementById(objId + "_combo").getAttribute("canOutSelectList") == "true") {
		var value = document.getElementById(objId + "_combo").value;
		if (value == null || value == "" || value == "请选择...") {
			odin.setSelectValue(objId, "");
		} else {
			odin.setSelectValue(objId, value);
		}
		return true;
	}
	var value = document.getElementById(objId + "_combo").value;
	if (value == null || value == "" || value == "请选择...") {
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
		var errmsg = "您输入的【" + value + "】不在列表中！";
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
				var errmsg = "您输入的【" + checkValue + "】不在列表中！";
				return errmsg;
			}
		}
	}
	value = valueArray.join(',');
	odin.setSelectValue(objId, value);
	return true;
}

/**
 * 日期格式到天的校验
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
 * 日期格式到天的值校验
 */
// TODO 20121207 由于目前ext自动将值进行了替换，未真正实现校验月、日的格式，只要parseDate不出错就会没问题，等以后修改
function checkDateValue(value) {
	if (value == null) {
		return true;
	}
	value = value.toString();
	if (value.length != 8 && value.length != 10) {
		return "长度必须为8位或10位，如20080808或2008-08-08";
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
		return "年份输入有误,请重新输入!";
	} else if (Number(year) < 1900 || Number(year) > 2100) {
		return "年份应该在1900-2100之间!";
	}
	if (isNaN(Number(month))) {
		return "月份输入有误,请重新输入!";
	} else if (Number(month) < 1 || Number(month) > 12) {
		return "月份应该在1-12之间!";
	}
	var daysOfMonth = new Date(year, month, 0).getDate();
	if (isNaN(Number(date))) {
		return "日期输入有误,请重新输入!";
	} else if (Number(date) < 1 || Number(date) > daysOfMonth) {
		return "日期应该在1-" + daysOfMonth + "之间!";
	}
	return true;
}

/**
 * 日期格式到秒的校验
 */
function checkDateTime(objId) {
	var value = document.getElementById(objId).value;
	if (value.length != 14 && value.length != 19) {
		errmsg = "长度必须为14位或19位，如20080808080808或2008-08-08 08:08:08"
		setValid(objId, false, errmsg);
		return false;
	}
	document.getElementById(objId).value = renderDateTime(value);
	setValid(objId, true);
	return true;
}

/**
 * html格式的项目onfocus事件，暂时只为取项目原始值commParams.currentOriginalValue使用
 */
function doOnFocus(item) {
	var name = item.name;
	var value = item.value;
	if (!name) {
		name = item.getId();
	}
	// select格式的特殊处理
	if (name.indexOf("_combo") >= 0) {
		name = name.substr(0, name.indexOf("_combo"));
	}
	if (Ext.getCmp(name + "_combo")) {
		value = document.getElementById(name).value;
	}
	// date格式的特殊处理
	if (getFieldType(name) == "date") {

	}
	// checkbox格式的特殊处理
	if (item.type == "checkbox") {
		value = item.checked;
	}
	tempOriginalValue = value;
}

/**
 * 触发afteredit事件
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
 * grid格式的afteredit事件
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
 * grid格式的rowDbClick事件
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
 * 保存操作
 */
function doSave() {
	commParams = {};
	commParams.currentEvent = "save";
	doSubmit(document.commForm, doSuccess, doFailure);
}

/**
 * 数据加载之后操作
 */
function doQueryLoad(ds) {
	var gridId = ds.baseParams.div;
	parent.odin.unmask(); // 去掉
	if (ds.getTotalCount() == 0) {// 没有数据
		var xt = "整个系统内";
		if (parent.MDParam == null || parent.MDParam.uptype == "" || parent.MDParam.uptype == "00") {
			xt = "社会保险系统内";
		} else if (parent.MDParam.uptype == "09") {
			xt = "被征地养老保障";
		} else if (parent.MDParam.uptype == "10") {
			xt = "农村养老保障系统内";
		} else if (parent.MDParam.uptype == "11") {
			xt = "城镇老年居民养老保障";
		} else if (parent.MDParam.uptype == "20") {
			xt = "新型农村养老保障";
		} else if (parent.MDParam.uptype == "21") {
			xt = "城乡居民养老保险系统内";
		} else if (parent.MDParam.uptype == "91") {
			xt = "社区系统内";
		}
		var errmsg = xt + "没有符合条件的数据！\n请检查输入的字符信息，修正后再重新查询。";
		if (MDParam.location.indexOf("PSQuery") != -1) {
			errmsg = xt + "没有要查找的人员！\n请检查输入的字符信息，修正后再重新查询。";
		} else if (MDParam.location.indexOf("CPQuery") != -1) {
			errmsg = xt + "没有要查找的单位！\n请检查输入的字符信息，修正后再重新查询。";
		} else if (MDParam.location.indexOf("PSIDQuery") != -1) {
			errmsg = xt + "没有要查找的人员身份！\n请检查输入的字符信息，修正后再重新查询。";
		} else if (MDParam.location.indexOf("PSIDNew") != -1) { // psidnew查不到则进行后台触发
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
	} else if (ds.getTotalCount() == 1) {// 一条数据
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
	} else {// 多条数据
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
 * 查询事件的关闭窗口操作
 */
function closeQueryPupWindow() {
	var triggerOnchange = false;
	if (document.getElementById("iframe_win_pup").src.indexOf("PSIDNew") != -1) {// 人员身份新增特殊处理
		triggerOnchange = true;
	}
	doQuerySelect(getIFrameWin("iframe_win_pup").getGridNullJson(getIFrameWin("iframe_win_pup").isQueryDiv), triggerOnchange);
}
/**
 * 查询事件的按键操作
 */
function doQueryKeydown(e) {
	if (e.keyCode == e.ENTER) {// 回车
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
	} else if (e.keyCode == e.ESC) {// 退出
		var triggerOnchange = false;
		if (window.location.href.indexOf("PSIDNew") != -1) {// 人员身份新增特殊处理
			triggerOnchange = true;
		}
		parent.doQuerySelect(getGridNullJson(this.id), triggerOnchange);
		parent.doHiddenPupWin();
	} else if (e.ctrlKey && (e.keyCode == e.LEFT || e.keyCode == e.PAGEUP)) {// ctrl+左键或上页--第一页
		doLoadFirstPage(this.id);
	} else if (e.keyCode == e.LEFT || e.keyCode == e.PAGEUP) {// 左键或上页--上一页
		doLoadPrevPage(this.id);
	} else if (e.ctrlKey && (e.keyCode == e.RIGHT || e.keyCode == e.PAGEDOWN)) {// ctrl+右键或下页--下一页
		doLoadLastPage(this.id);
	} else if (e.keyCode == e.RIGHT || e.keyCode == e.PAGEDOWN) {// 右键或下页--下一页
		doLoadNextPage(this.id);
	} else if (e.keyCode == e.DOWN) {// 下键--下一条
		if (!Ext.isIE && !Ext.isGecko) { // 非IE、Firefox才需要，IE、Firefox默认有此功能
			Ext.getCmp(this.id).getSelectionModel().selectNext();
		}
	} else if (e.keyCode == e.UP) {// 上键--上一条
		if (!Ext.isIE && !Ext.isGecko) { // 非IE、Firefox才需要，IE、Firefox默认有此功能
			Ext.getCmp(this.id).getSelectionModel().selectPrevious();
		}
	}
}

/**
 * 取得空行的grid的json
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
 * 分页grid的第一页
 */
function doLoadFirstPage(gridId) {
	pageingToolbar = Ext.getCmp(gridId).getBottomToolbar();
	if (pageingToolbar.cursor == 0) {// 第一页不操作
		return;
	}
	pageingToolbar.onClick("first");
}
/**
 * 分页grid的上一页
 */
function doLoadPrevPage(gridId) {
	pageingToolbar = Ext.getCmp(gridId).getBottomToolbar();
	if (pageingToolbar.cursor == 0) {// 第一页不操作
		return;
	}
	pageingToolbar.onClick("prev");
}
/**
 * 分页grid的当前页刷新
 */
function doLoadCurrentPage(gridId) {
	pageingToolbar = Ext.getCmp(gridId).getBottomToolbar();
	pageingToolbar.onClick("refresh");
}
/**
 * 分页grid的下一页
 */
function doLoadNextPage(gridId) {
	pageingToolbar = Ext.getCmp(gridId).getBottomToolbar();
	if (pageingToolbar.cursor + pageingToolbar.pageSize >= pageingToolbar.store.getTotalCount()) {// 最后一页不操作
		return;
	}
	pageingToolbar.onClick("next");
}
/**
 * 分页grid的最后一页
 */
function doLoadLastPage(gridId) {
	pageingToolbar = Ext.getCmp(gridId).getBottomToolbar();
	if (pageingToolbar.cursor + pageingToolbar.pageSize >= pageingToolbar.store.getTotalCount()) {// 最后一页不操作
		return;
	}
	pageingToolbar.onClick("last");
}
/**
 * 查询事件的鼠标双击操作
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
 function successFunc(responseTxt) { // 空的ajax调用成功的处理函数
	//
}
/**
 * 选择操作
 */
function doQuerySelect(jsonStr, isDoOnChanged) {
	unPupWindowCloseClick();// 去掉关闭按钮的函数
	isQuerying = false;// 查询结束
	var div = document.getElementById(commParams.currentDiv);
	var col = commParams.currentColumn;
	if (isDoOnChanged == true) { // 搜索成功
		if (col.indexOf("psquery") == 0) { // 人员查询
			getDivItem(div, col).value = jsonStr.aae135;
			if (!fromCommLink) {
				setPsqueryBuffer(jsonStr.eac001);
			}
		} else if (col.indexOf("cpquery") == 0) { // 单位查询
			getDivItem(div, col).value = jsonStr.aab001;
			if (!fromCommLink) {
				setCpqueryBuffer(jsonStr.aab001);
			}
		} else if (col.indexOf("psidquery") == 0) { // 人员身份查询
			getDivItem(div, col).value = jsonStr.aae135;
			if (!fromCommLink) {
				setPsidqueryBuffer(jsonStr.aae135);
			}
		} else if (col.indexOf("psidnew") == 0) { // 人员身份新增
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
				if (typeof(value) == "undefined") {// 未定义
					continue;
				} else if (value == null) {
					value = "";
				}
				if (value == inputList.item(j).value) {// 相同则不处理
					continue;
				}
				if (value == "" && input.initValue) {// 空且有initValue的话设成initValue，解决权限控制的问题
					value = input.initValue;
				}
				if (Ext.getCmp(inputList.item(j).name + "_combo")) { // 下拉框
					odin.setSelectValue(inputList.item(j).name, value);
				} else if (getFieldType(inputList.item(j).name) == "date") { // 日期格式
					if (Ext.getCmp(inputList.item(j).name).format == "Y-m-d H:i:s") { // 到秒
						inputList.item(j).value = renderDateTime(value);
					} else {
						inputList.item(j).value = renderDate(value);
					}
				} else if (inputList.item(j).type == "checkbox") { // 打勾
					if (((value == "true" || value == "1") ? true : false) != inputList.item(j).checked) {
						inputList.item(j).checked = (value == "true" || value == "1") ? true : false;
					}
				} else {
					inputList.item(j).value = value;
				}
			}
		}
	}
	if (isDoOnChanged == true) {// 搜索成功，进行提交
		// 选择好后，进行onchange的提交操作
		doSubmit(document.commForm, doSuccess, doFailure);
	}
}

/**
 * 在psquery或cpquery输入框双击时的操作
 */
function doQueryDblClick(item) {
	if (item.getAttribute("isQuery") == "true") { // 要查询的项
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
 * 分页grid的按键操作
 */
function doPageGridKeyDown(e) {
	if (e.ctrlKey && e.keyCode == e.PAGEUP) {// ctrl+上页键--第一页
		doLoadFirstPage(this.id);
	} else if (e.keyCode == e.PAGEUP) {// 上页键--上一页
		doLoadPrevPage(this.id);
	} else if (e.ctrlKey && e.keyCode == e.PAGEDOWN) {// ctrl+下页--下一页
		doLoadLastPage(this.id);
	} else if (e.keyCode == e.PAGEDOWN) {// 下页--下一页
		doLoadNextPage(this.id);
	} else if (e.keyCode == e.DOWN) {// 下键--下一条
		if (!Ext.isIE && !Ext.isGecko) { // 非IE、Firefox才需要，IE、Firefox默认有此功能
			Ext.getCmp(this.id).getSelectionModel().selectNext();
		}
	} else if (e.keyCode == e.UP) {// 上键--上一条
		if (!Ext.isIE && !Ext.isGecko) { // 非IE、Firefox才需要，IE、Firefox默认有此功能
			Ext.getCmp(this.id).getSelectionModel().selectPrevious();
		}
	}
}

/**
 * 不分页grid的按键操作
 */
function doNotPageGridKeyDown(e) {
	if (e.keyCode == e.DOWN) {// 下键--下一条
		if (!Ext.isIE && !Ext.isGecko) { // 非IE、Firefox才需要，IE、Firefox默认有此功能
			Ext.getCmp(this.id).getSelectionModel().selectNext();
		}
	} else if (e.keyCode == e.UP) {// 上键--上一条
		if (!Ext.isIE && !Ext.isGecko) { // 非IE、Firefox才需要，IE、Firefox默认有此功能
			Ext.getCmp(this.id).getSelectionModel().selectPrevious();
		}
	}
}
/**
 * 判断是否允许编辑
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
 * 判断grid的列是否允许编辑
 */
function canEditCell(gridId, fieldName) {
	return getGridColumn(gridId, fieldName).editable == true ? true : false;
}

/**
 * 编辑框的效果
 */
function renderEdit(value, params, record, rowIndex, colIndex, ds) {
	if (!ds) {// ds为空，分组行
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
		if (type == "numberfield" || type == "textfield" || type == "datefield" || type == "combo" || type == "textarea" || type == "timefield") {// 只操作字符串、数字、日期、下拉框格式的列
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
 * 时间格式的显示转换到日
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
 * 时间格式的转换到秒
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
 * 鼠标移上显示的效果
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
 * 颜色渲染
 * 
 * @param {}
 *            colorExp 颜色表达式，如(aaa001==1)?'red':(aaa001==2)?'rgb(0,0,0)':''
 */
function renderColor(value, params, record, rowIndex, colIndex, ds, colorExp) {
	if (!ds) {// ds为空，分组行
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
 * grid格式打勾控件的操作
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
 * 单位编码、人员编码超链接
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
 * grid格式打勾控件的操作的辅助操作
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
 * 对弹出页面的操作
 * 
 * @param value格式为：显示的内容,是否允许点击(1允许0不允许),显示的窗口id,参数1,参数2... @
 *            如："测试,1,win_1,1212,tttt..."
 */
function renderWindow(value, params, record, rowIndex, colIndex, ds) {
	if (value == null) {
		return;
	}
	var colName = Ext.getCmp(ds.baseParams.div).getColumnModel().getDataIndex(colIndex);
	var initParams;
	var valueArray = value.split(",");
	var showValue = valueArray[0]; // 显示的内容
	var clickFlag = valueArray[1]; // 是否允许点击
	var windowId = valueArray[2]; // 显示的窗口id
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
 * 对按钮的操作
 * 
 * @param value格式为：显示的内容,是否允许点击(1允许0不允许),按钮名称,参数1,参数2... @
 *            如："测试,1,click1,1212,tttt..."
 */
function renderClick(value, params, record, rowIndex, colIndex, ds) {
	if (value == null) {
		return;
	}
	var colName = Ext.getCmp(ds.baseParams.div).getColumnModel().getDataIndex(colIndex);
	var clickParams;
	var valueArray = value.split(",");
	var showValue = valueArray[0]; // 显示的内容
	var clickFlag = valueArray[1]; // 是否允许点击
	var clickId = null; // 按钮id
	if (valueArray.length > 2) {
		clickId = valueArray[2]; // 按钮id
	}
	if (valueArray.length > 3) {
		clickParams = new Array(valueArray.length - 3);
	}
	for (var i = 3; i < valueArray.length; i++) {
		clickParams[i - 3] = valueArray[i];
	}
	if (clickFlag == "1") {
		if (clickId.indexOf("confirm") != -1) { // 需要确认的按钮
			return "<a href='javascript:void(0)' class='render' onclick='odin.confirm(\"确定要对本行进行" + showValue + "操作？\", function(btn) {" + " if (btn == \"ok\") {doClick(\"" + clickId + "\",\"" + Ext.encode(clickParams).replace(/"/g, "\\\"") + "\"," + rowIndex + ")}})'>" + showValue + "</a>";
		} else { // 不需要确认的按钮
			return "<a href='javascript:void(0)' class='render' onclick='doClick(\"" + clickId + "\",\"" + Ext.encode(clickParams).replace(/"/g, "\\\"") + "\"," + rowIndex + ")'>" + showValue + "</a>";
		}

	} else {
		return "<font color=#CCCCCC>" + showValue + "</font>";
	}

}

/**
 * 对按钮的操作,提交前会进行非空、符合条件的校验
 * 
 * @param value格式为：显示的内容,是否允许点击(1允许0不允许),按钮名称,参数1,参数2... @
 *            如："测试,1,click1,1212,tttt..."
 */
function renderClick2(value, params, record, rowIndex, colIndex, ds) {
	if (value == null) {
		return;
	}
	var colName = Ext.getCmp(ds.baseParams.div).getColumnModel().getDataIndex(colIndex);
	var clickParams;
	var valueArray = value.split(",");
	var showValue = valueArray[0]; // 显示的内容
	var clickFlag = valueArray[1]; // 是否允许点击
	var clickId = null; // 按钮id
	if (valueArray.length > 2) {
		clickId = valueArray[2]; // 按钮id
	}
	if (valueArray.length > 3) {
		clickParams = new Array(valueArray.length - 3);
	}
	for (var i = 3; i < valueArray.length; i++) {
		clickParams[i - 3] = valueArray[i];
	}
	if (clickFlag == "1") {
		if (clickId.indexOf("confirm") != -1) { // 需要确认的按钮
			return "<a href='#' class='render' onclick='odin.confirm(\"确定要对本行进行" + showValue + "操作？\", function(btn) {" + " if (btn == \"ok\") {doClick2(\"" + clickId + "\",\"" + Ext.encode(clickParams).replace(/"/g, "\\\"") + "\",null," + rowIndex + ")}})'>" + showValue + "</a>";
		} else { // 不需要确认的按钮
			return "<a href='#' class='render' onclick='doClick2(\"" + clickId + "\",\"" + Ext.encode(clickParams).replace(/"/g, "\\\"") + "\",null," + rowIndex + ")'>" + showValue + "</a>";
		}
	} else {
		return "<font color=#CCCCCC>" + showValue + "</font>";
	}
}

/**
 * 显示成 百分比
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
 * 显示成 百分比或非百分比 <1且>-1为百分比，>1或<-1为非百分比
 */
function renderPercentOrNot(value, params, record, rowIndex, colIndex, ds) {
	if (value < 1 && value > -1) {
		return renderPercent(value, params, record, rowIndex, colIndex, ds);
	} else {
		return value;
	}
}

/**
 * 显示窗口
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
 * 根据传入的地址显示窗口
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
	newSrc = addUrlParam(newSrc, "clientDate=" + Ext.util.Format.date(new Date(), "YmdHis"));// 使每次请求都不一样，解决请求一样打不开的问题
	newSrc = encodeURI(newSrc);
	// alert(newSrc);
	if (document.getElementById("iframe_" + windowId)) {
		try {
			getIFrameWin("iframe_" + windowId).odin.mask("正在刷新...");
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
 * 打开顶层的弹出窗口
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
 * 隐藏顶层的弹出窗口
 */
function doClosePupWinOnTop() {
	if (parent != this && parent.doClosePupWinOnTop) {
		parent.doClosePupWinOnTop();
	} else {
		doHiddenPupWin();
	}

}
/**
 * 取得顶层窗口
 */
function getTopParent() {
	if (parent != this && parent.getTopParent) {
		return parent.getTopParent();
	} else {
		return this;
	}
}
/**
 * 打开子弹出窗口
 */
function doOpenPupWin(src, title, width, height, initParams, parentWin) {
	if (parentWin == null) {
		parentWin = this;
	}
	tempParentWin = parentWin;
	var windowId = "win_pup";
	var pupWindow = Ext.getCmp(windowId);
	expReadyState = ""; // 导出时初始化
	pupWindow.setTitle(title); // 标题
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
	pupWindow.setSize(width, height); // 宽度 高度
	showWindowWithSrc(windowId, src, initParams, "doPupWinOnload(this)");
	pupWindow.center(); // 居中
}
/**
 * 显示窗口加载完成触发的事件
 */
function doPupWinOnload(iframe) {
	try {
		getIFrameWin(iframe.id).setParentWin(tempParentWin);
	} catch (e) {
	}
}
/**
 * 打开本弹出窗口
 */
function doOpenThisPupWin(src, title, width, height, initParams) {
	var windowId = "win_pup";
	var pupWindow = parent.Ext.getCmp(windowId);
	expReadyState = ""; // 导出时初始化
	pupWindow.setTitle(title); // 标题
	if (width < 1) {
		if (width >= 0) {// 小数
			width = window.screen.availWidth * width;
		} else { // 负数
			width = window.screen.availWidth + width;
		}
	}
	if (height < 1) {
		if (height >= 0) {// 小数
			height = window.screen.availHeight * height;
		} else { // 负数
			height = window.screen.availHeight + height;
		}
	}
	pupWindow.setSize(width, height); // 宽度 高度
	parent.showWindowWithSrc(windowId, src, initParams);
	pupWindow.center(); // 居中
}

/**
 * 打开弹出窗口并隐藏
 */
function doOpenPupWinHidden(src, initParams) {
	var windowId = "win_pup";
	var pupWindow = Ext.getCmp(windowId);
	showWindowWithSrc(windowId, src, initParams);
	pupWindow.hide();
}

/**
 * 隐藏子弹出窗口
 */
function doHiddenPupWin() {
	var windowId = "win_pup";
	var pupWindow = Ext.getCmp(windowId);
	pupWindow.hide();
}

/**
 * 隐藏本弹出窗口
 */
function doHiddenThisPupWin() {
	var windowId = "win_pup";
	var pupWindow = parent.Ext.getCmp(windowId);
	pupWindow.hide();
}

/**
 * 打开弹出窗口并隐藏
 */
function doShowPupWin() {
	var windowId = "win_pup";
	var pupWindow = Ext.getCmp(windowId);
	pupWindow.show();
	pupWindow.focus();
}

/**
 * 设置或清除某列的提示信息
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
			// Ext.getCmp(column).invalidText = null; 不需要，否则日期校验会有问题
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
 * 刷新
 */
function doReset() {
	odin.confirm("确定要刷新界面？", function(clicked) {
				if (clicked == "ok") {
					reflush();
				}
			});
}

/**
 * 给url加参数
 * 
 * @param {}
 *            url 原始url
 * @param {}
 *            paramUrl 参数url，如 &a=1&b=2
 * @return {} 返回值
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
 * 刷新页面
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
 * 刷新页面
 */
function reflushThis(initParams) {
	reflush(null, initParams);
}

/**
 * 设置保存后是否进行提示并刷新
 */
function setReflushAfterSave(flag) {
	reflushAfterSave = flag;
}

/**
 * 设置保存后进行提示的信息
 */
function setReflushAfterSaveMsg(msg) {
	reflushAfterSaveMsg = msg;
}

/**
 * 设置保存后是否进行提示并刷新
 */
function setReflushAfterSavePrint(msg) {
	reflushAfterSavePrint = msg;
}

/**
 * 取得系统类型
 * 
 * @return {}
 */
function getSysType() {// sionline 为网上申报， insiis
	return 'insiis';
}

/**
 * grid的修改数据提交
 */
function doGridCommitChanges() {
	var theForm = document.commForm;
	// 取grid格式的数据
	var elList = theForm.getElementsByTagName("input");
	for (var i = 0; i < elList.length; i++) {
		if (elList.item(i).name && elList.item(i).name.indexOf("-") == -1) {
			if (elList.item(i).name.indexOf("Data") > 0 && document.getElementById("gridDiv_" + elList.item(i).name.substr(0, elList.item(i).name.length - 4)) != null) {
				var gridId = elList.item(i).name.substr(0, elList.item(i).name.length - 4);
				try {
					Ext.getCmp(gridId).store.commitChanges(); // 提交
				} catch (e) {

				}
			}
		}
	}
}

/**
 * 后台操作成功后的动作
 */
function doSuccess(response) {
	// 初始化
	reflushAfterSave == true;
	var theCommParams = Ext.util.JSON.decode(response.data.hashMap.commParams)[0]; // 由于异步模式，所以在返回参数中取回公共参数值
	commParams = theCommParams;
	if (theCommParams.currentEvent == "onchange") { // onchange事件
		clearInvalid(document.commForm); // 成功后先将界面的错误信息清除掉，主要清除必填项错误提示
	}
	if (theCommParams.currentEvent == "save") { // 保存
		opPage(response); // 对页面操作，可能对reflushAfterSave进行修改
	}
	if (theCommParams.currentEvent == "save" && reflushAfterSave == false) {// 保存后，清除grid的修改标志
		doGridCommitChanges();
	}
	if (theCommParams.currentEvent == "commImp") { // 通用导入
		getIFrameWin("iframe_win_pup").doCloseWin();
	}
	if (theCommParams.currentEvent == "save" && reflushAfterSave == true) {// 保存并刷新界面
		if (getSysType() == "sionline") { // 网上申报
			var altmsg = "保存成功！待所有输入完成后，请到草稿箱进行申报！";
			if (reflushAfterSaveMsg != null && reflushAfterSaveMsg != "") {
				altmsg = reflushAfterSaveMsg;
			}
			odin.info(altmsg, function() {
						reflush();
					});
		} else { // 业务系统
			var altmsg = "保存成功！";
			if (reflushAfterSaveMsg != null && reflushAfterSaveMsg != "") {
				odin.info(reflushAfterSaveMsg, function() {
							reflush();
						});
			} else if (reflushAfterSavePrint != null && reflushAfterSavePrint != "") {
				if (reflushAfterSavePrint == "#") { // 不提示直接打印
					setFormData(response); // 设值
					doPrint(); // 打印
					doSubmitFromWaiting(); // 提交等待的事件
				} else {
					odin.confirm(reflushAfterSavePrint, function(btn) {
								if (btn == "ok") { // 确定
									setFormData(response); // 设值
									doPrint(); // 打印
									doSubmitFromWaiting(); // 提交等待的事件
								} else { // 取消
									reflush();
								}
							});
				}
			} else {
				odin.mask(altmsg); // 加上阴影
				window.setTimeout("odin.unmask()", 2000);// 去掉阴影
				window.setTimeout("reflush()", 1000); // 页面刷新
			}
		}
	} else {// 不是保存并刷新的情况
		if (response.data.hashMap.divsql) {
			divsql = Ext.util.JSON.decode(response.data.hashMap.divsql)[0]; // 保存返回的sql
		}
		setFormData(response); // 设值
		if (currentOpseno == null) { // 草稿箱不操作
			setPageGridStore(response); // 设grid格式的值
		}
		if (theCommParams.currentEvent != "save") { // 保存已在前面操作
			opPage(response); // 对页面操作
		}
		showMsgBox(response); // 提示信息
		if (document.getElementById(theCommParams.currentColumn) != null) {
			setValid(theCommParams.currentColumn, true);// 叹号提示信息清除
		}
		// 设置焦点
		if (!commParams.hasSetFocus) {
			if (odin.lastEvent && (odin.lastEvent.keyCode == 9 || odin.lastEvent.keyCode == 13)) {// tab或回车
				if (document.getElementById(theCommParams.currentColumn) != null) {// 设置当前焦点
					setFocusNext(theCommParams.currentColumn); // 设置当前焦点
				} else if (isGridDiv(theCommParams.currentDiv)) { // grid设置当前焦点
					var editor = getGridCellEditor(theCommParams.currentDiv, theCommParams.currentColumn, theCommParams.currentRow);
					var grid = Ext.getCmp(theCommParams.currentDiv);
					var newCell = grid.walkCells(theCommParams.currentRow, editor.col + 1, 1, grid.getSelectionModel().acceptsNav, editor);
					if (newCell != null) {
						if (grid.activeEditor) {
							field = grid.activeEditor.field;
							if (field.getXType() == "combo") { // 下拉框特殊处理
								field.triggerBlur();
							}
						}
						grid.startEditing(newCell[0], newCell[1]);
					}
				}
			}
			commParams.hasSetFocus = false; // 用完后清空
		}
		// if (document.activeElement != null && document.activeElement.name !=
		// null && document.activeElement.name != "") { // 设置当前焦点
		// setFocus(document.activeElement.name);
		// }
		if (theCommParams.currentEvent == "afterImp") { // 导入后的操作
			doImpSuccess();
		}
		doBillPrint(response); // 单据打印
		doSubmitFromWaiting(); // 提交等待的事件
		if (theCommParams.currentEvent == "init") { // 初始化
			if (parent && parent._autoSaveCommScenForm && parent._autoSaveCommScenForm()) { // 自动保存场景Form
				doSaveCommScenForm();
			} else { // 普通
				// 设置焦点，可以按F9快捷键
				focus();
				// 设置焦点到第一列上
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
		} else if (theCommParams.currentEvent == "query" && MDParam.location.indexOf("commRep2Action.do") != -1) {// 报表工具查询
			pagePaneLoad();
		} else if ((theCommParams.currentEvent == "print" || theCommParams.currentEvent == "exp") && MDParam.location.indexOf("commRep2Action.do") != -1) {// 报表工具处理
			eval(pagePaneOpstr);
			
		} else if (theCommParams.currentEvent == "saveCommScenForm") { // 保存场景Form的事件
			if (parent && parent.doAfterSaveCommScenForm) {
				parent.doAfterSaveCommScenForm('场景初始化成功！');
			}
		}
	}
}

/**
 * 后台操作失败后的动作
 */
function doFailure(response, theCommParams) {
	try {
		if (theCommParams != null) {
			commParams = theCommParams;
		} else if (doSubmitStatus != null && doSubmitStatus.submiting != null && doSubmitStatus.submiting.commParams != null) {
			commParams = doSubmitStatus.submiting.commParams;
		}
		// query的特殊处理
		if (isQuery) {
			parent.odin.unmask(); // 去掉阴影
			parent.doQuerySelect(getGridNullJson(isQueryDiv));
			return parent.doFailure(response, parent.commParams);
		}
		var errmsg = response.mainMessage;
		if (errmsg.indexOf("doConfirm") == 0) { // 确认的请求
			eval(odin.toHtmlString(errmsg));
			return;
		}
		if (response.detailMessage != "") {
			errmsg = errmsg + "\n详细信息：" + response.detailMessage;
		}
		if (commParams.currentColumn != null && commParams.currentColumn != "searchText") { // 提示出错信息
			setValid(commParams.currentColumn, false, errmsg);
		}
		var addmsg = "";
		if (isGridDiv(commParams.currentDiv)) {
			addmsg = "，请重新输入！";
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
		if (theCommParams && theCommParams.currentEvent == "saveCommScenForm") { // 保存场景Form的事件
			if (parent && parent.doAfterSaveCommScenForm) {
				parent.doAfterSaveCommScenForm(errmsg);
			}
		}
	} finally {
		doSubmitStatus.waiting = null; // 清空等待的事件
		doSubmitStatus.isSubmiting = false;
	}
}

/**
 * 需要确认的提示信息
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
 * 设置焦点
 */
function setFocus(column, isInit) {
	if (document.activeElement != null && document.activeElement.name != null && document.activeElement.name != "") { // 去掉当前焦点
		if (document.activeElement.name.indexOf("_combo") != -1) {// 下拉框特殊处理
			Ext.getCmp(document.activeElement.name).triggerBlur();
		}
	}
	if (column == null || column == "") {
		return;
	}
	try {
		if (Ext.getCmp(column + "_combo")) { // 下拉框特殊处理
			if (isInit) { // 初始化特殊处理
				eval("comboSetFocusForInit_" + column + "_combo" + "=true"); // 设置下拉框不显示标志
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
 * 设置焦点到下一个对象
 */
function setFocusNext(column) {
	try {
		var obj = document.getElementById(column);
		var el = odin.getNextElement(obj);
		if (document.getElementById(column + "_combo")) {// 下拉框特殊处理
			obj = el;
			el = odin.getNextElement(obj);
		}
		if (el) {
			if (document.getElementById(column + "_combo") && Ext.getCmp(column + "_combo").hasFocus) {// 下拉框特殊处理
				Ext.getCmp(column + "_combo").triggerBlur();
			} else if (Ext.getCmp(column) && Ext.getCmp(column).getXType().indexOf("date") != -1 && Ext.getCmp(column).hasFocus) { // 日期框特殊处理
				Ext.getCmp(column).triggerBlur();
			}
			try {
				if (el.name.indexOf("_combo") != -1) { // 下拉框特殊处理，解决一些bug
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
 * 得到false的函数
 */
function getFalse() {
	return false;
}

/**
 * 得到true的函数
 */
function getTrue() {
	return true;
}

/**
 * 取得项目的数据类型
 */
function getFieldType(fieldName) {
	if (Ext.getCmp(fieldName) == null) {
		return "string";
	}
	var xtype = Ext.getCmp(fieldName).getXType();
	return toType(xtype);
}

/**
 * 取项目的编辑类型
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
 * 取项目的grid编辑类型
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
 * 取得grid的列的数据类型
 */
function getGridFieldType(gridName, fieldName) {
	var column = getGridColumn(gridName, fieldName);
	if (column && column.editor) {
		var xtype = column.editor.field.getXType();
	}
	return toType(xtype);
}

/**
 * 取得grid的列
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
 * 取得grid的列索引
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
 * 取得grid某单元格的编辑器
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
 * Ext的xtype转换成type：number、date、string
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
 * 通过名字取得对象，支持combo直接取
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
 * 检查是否有错误的数据
 */
function checkValid(theForm) {
	var checkFlag = -1;
	if (theForm == null || theForm == 0 || theForm == 1 || theForm == 2) {
		checkFlag = (theForm == null ? 0 : theForm);
		theForm = document.commForm;
	}
	var eles = theForm.elements;
	var errtitle = "<b>请先修正以下问题后再进行本操作：</b><br>";
	for (i = 0; i < eles.length; i++) {
		var obj = eles[i];
		odin.cueCheckObj = obj;
		// 校验非空
		if (obj.getAttribute("required") == "true" && (checkFlag == 0 || checkFlag == 1 || commParams.currentEvent == "save" || commParams.currentEvent == "query")) {
			if (Ext.getCmp(obj.name)) { // 解决 ckeditor取值问题
				Ext.getCmp(obj.name).getValue();
			}
			if (obj.value == "") { // 非空判断
				odin.error(errtitle + obj.getAttribute("label") + "不能为空！", odin.doFocus);
				return false;
			}
		}
		// 校验输入项是否符合要求
		// 非空或下拉框
		// 保存或非当前列
		// 下拉框不为初始值，主要解决"请您选择..."的问题
		if (((obj.value != null && obj.value != "") || (obj.name != null && obj.name != "" && document.getElementById(obj.name + "_combo") && document.getElementById(obj.name + "_combo").value != null && document.getElementById(obj.name + "_combo").value != "")) && (checkFlag == 0 || checkFlag == 2 || commParams.currentEvent == "save" || commParams.currentEvent == "query" || obj.name != commParams.currentColumn) && !(obj.name.indexOf("_combo") >= 0 && obj.value != getCmpByName(obj.name).defaultValue)) {
			var eObj = getCmpByName(obj.name);
			if (eObj) {
				if (!eObj.isValid(false)) {
					odin.error(errtitle + '“' + obj.getAttribute("label") + '”输入项的值不符合要求，请重新输入！', odin.doFocus);
					return false;
				}
			}
		}
	}
	return true;
}

/**
 * 检查是否有错误的数据
 */
function clearInvalid(theForm) {
	var eles = theForm.elements;
	for (i = 0; i < eles.length; i++) {
		var obj = eles[i];
		setValid(obj.name, true);
	}
}

// 提交的队列，目前参数有isSubmiting、submiting和waiting，为了解决输入项输入后未按回车就点保存按钮出现的问题
var doSubmitStatus = {};
/**
 * 从等待的队列事件里进行提交
 */
function doSubmitFromWaiting() {
	doSubmitStatus.isSubmiting = false;
	if (doSubmitStatus.waiting != null && doSubmitStatus.waiting.length > 0) {
		if (doSubmitStatus.submiting != null && doSubmitStatus.submiting.commParams.currentEvent == "save") { // 上一次为保存
			while (doSubmitStatus.waiting[0].commParams.currentEvent == "save") {// 按了两次保存按钮
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
 * 进行提交发送操作
 */
function doSubmit(theForm, successFun, failureFun) {// Ajax方式提交保存
	// doSubmitQueue(commParams, theForm, successFun, failureFun);
	if (doSubmitStatus.isSubmiting == null || doSubmitStatus.isSubmiting == false) { // 没有正在提交的事件
		doSubmitStatus.isSubmiting = true;
		var submiting = {};
		submiting.commParams = commParams;
		submiting.theForm = theForm;
		submiting.successFun = successFun;
		submiting.failureFun = failureFun;
		doSubmitStatus.submiting = submiting;
	} else {// 已有正在提交的事件，则等待
		if (commParams.currentEvent == "save") { // 控制不允许按两次保存按钮
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
	// 先校验
	if (commParams.currentEvent != "init" && commParams.currentEvent != "click") { // 校验处理
		if (checkValid(theForm) != true) { // 做校验判断
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

	// 将表单域中的数据转换成参数对象
	// 取非grid格式的数据
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
						if (Ext.getCmp(inputItem.name)) { // 标签处理
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
	// 取grid格式的数据
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
				// 取grid列类型
				var gridColumnModel = Ext.getCmp(gridId).getColumnModel();
				for (var j = 0; j < gridColumnModel.getColumnCount(); j++) {
					var dataIndex = gridColumnModel.getDataIndex(j);
					var column = gridColumnModel.getColumnById(gridColumnModel.getColumnId(j));
					if (column.editor) {
						tempTypeExp = tempTypeExp + ",\"" + dataIndex + "\":\"" + toType(column.editor.field.getXType()) + "\"";
						tempColNameExp = tempColNameExp + ",\"" + dataIndex + "\":\"" + (gridColumnModel.getColumnHeader(j) == null ? "" : gridColumnModel.getColumnHeader(j).replace(/"/g, '\\\"').replace(/,/g, "，")) + "\"";
						tempREDHExp = tempREDHExp + ",\"" + dataIndex + "\":\"" + gridRED(column) + "\"";
					} else if (dataIndex != "") {
						tempTypeExp = tempTypeExp + ",\"" + dataIndex + "\":\"" + "string" + "\"";
						tempColNameExp = tempColNameExp + ",\"" + dataIndex + "\":\"" + (gridColumnModel.getColumnHeader(j) == null ? "" : gridColumnModel.getColumnHeader(j).replace(/"/g, '\\\"').replace(/,/g, "，")) + "\"";
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
	// 取得当前事件相关信息
	if (commParams != null) {
		exp = exp + ",'divForStrust(commParams)':'[" + Ext.util.JSON.encode(commParams).replace(/'/g, '\\\'') + "]'";
	}
	// 取菜单参数
	if (MDParam != null) {
		exp = exp + ",'divForStrust(MDParams)':'[" + Ext.util.JSON.encode(MDParam).replace(/'/g, '\\\'') + "]'";
	}
	// 取div的sql
	if (divsql != null && Ext.encode(divsql) != "{}") {
		exp = exp + ",'divForStrust(divsql)':'[" + Ext.util.JSON.encode(divsql).replace(/'/g, '\\\'') + "]'";
	}
	// 取div的分页类型
	var divList = theForm.getElementsByTagName("div");
	var tempPagetypeExp = "";
	for (var i = 0; i < divList.length; i++) {
		var divId = divList.item(i).id;
		if (divId.indexOf("div") == 0) {
			var divType = getDivType(divId);
			tempPagetypeExp = tempPagetypeExp + ",\"" + divId + "\":\"" + divType + "\"";
			if (divType == "1") {// 分页grid，南20130309增加分页信息
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
		exp = exp.replace(/\\/g, "\\\\"); // 将字符串中的转移符\替换成\\转义符
		exp = exp.replace(/\\\'/g, '\''); // 将字符串中的转移符\'替换成'转义符(上面将\'替换成了\\'，现替换\\'回来为\')
		eval("params = {" + exp + "}");
	}
	if (commParams.currentEvent == "save") { // 保存特殊处理
		if (!Ext.isIE) {// 非IE需要进一步处理
			// 修正取值问题
			for (var n = 0; n < tagNames.length; n++) {
				var inputList = document.getElementsByTagName(tagNames[n]);
				for (var j = 0; j < inputList.length; j++) {
					var item = inputList.item(j);
					item.setAttribute("value", item.value);
				}
			}
		}
		// 界面还原处理
		var orisource = document.documentElement.outerHTML;
		// 去掉javascript内容
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
		maskMsg = "正在初始化...";
	} else if (commParams.currentEvent == "onchange" || commParams.currentEvent == "afteredit") {
		maskMsg = "正在校验...";
	} else if (commParams.currentEvent == "query") {
		maskMsg = "正在查询...";
	} else if (commParams.currentEvent == "print") {
		maskMsg = "正在打印...";
	} else if (commParams.currentEvent == "save") {
		maskMsg = "正在保存...";
	}
	// 为支持一次性触发两个事件需要用setTimeout，即onchange和save的一次性触发
	window.setTimeout("maskSubmit('" + maskMsg + "');", 100)
	window.setTimeout("unmaskSubmit();", 100);
}
/**
 * 角色管理
 */
function roleSub() {
	if (isOnceLoad == "1") {
		var list = tree.getAllCheckedBranches();// 获取所有选中和处于半选状态的id
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
	if (doSubmitStatus.isSubmiting == null || doSubmitStatus.isSubmiting == false) { // 没有正在提交的事件

	} else {// 有正在提交的事件
		odin.mask(maskMsg);
	}
}

function unmaskSubmit() {
	if (doSubmitStatus.isSubmiting == null || doSubmitStatus.isSubmiting == false) { // 没有正在提交的事件
		odin.unmask();
	}
}

/**
 * 根据response传回来的数据设值到Form上去
 */
function setFormData(response) {
	var data = response.data;
	var json = data.hashMap;
	var divId = "";
	var params = {};
	var exp = "";
	// 将返回表单数据填入表单域中
	// 设非grid格式的数据
	var divList = document.commForm.getElementsByTagName("div");
	for (var i = 0; i < divList.length; i++) {
		divId = divList.item(i).id;
		if ((divId.indexOf("div_") == 0) && (document.getElementById("gridDiv_" + divId) == null)) { // div_开头且不是grid

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
						if (typeof(value) == "undefined") {// 未定义
							continue;
						} else if (value == null) {
							value = "";
						}
						if (value.toString() != inputItem.value.toString()) {
							if (Ext.getCmp(inputName + "_combo")) { // 下拉框
								odin.setSelectValue(inputName, value);
							} else if (getFieldType(inputName) == "date") { // 日期格式
								if (Ext.getCmp(inputName).format == "Y-m-d H:i:s") { // 到秒
									inputItem.value = renderDateTime(value);
								} else {
									inputItem.value = renderDate(value);
								}
							} else if (inputItem.type == "checkbox") { // 打勾
								if (((value == "true" || value == "1") ? true : false) != inputItem.checked) {
									inputItem.checked = (value == "true" || value == "1") ? true : false;
								}
							} else if (Ext.getCmp(inputName)) { // 标签的处理
								if (typeof(value) == "object") { // json格式的特殊处理
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
	// 设grid格式的数据
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
			// 修正特殊类型的数据
			for (var j = 0; j < jsonStr.length; j++) {
				for (var k = 0; k < dataIndex.length; k++) {
					if (dataIndex[k]) {
						var value;
						eval("value = jsonStr[" + j + "]." + dataIndex[k] + ";");
						if (value != null) {
							var type = getGridFieldType(gridId, dataIndex[k]);
							// date类型数据要转换成date型的数据
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
						} else { // 空值或未指定此列数据的处理
							if (store.getCount() <= j) { // 不存在此行即新增此行数据时要处理
								eval("jsonStr[" + j + "]." + dataIndex[k] + " = '';");
							}
						}
					}
				}
			}
			// 修改数据
			for (var j = 0; j < jsonStr.length; j++) {
				if (store.getCount() > j) { // 存在此行则修改此行
					for (var k = 0; k < dataIndex.length; k++) {
						if (dataIndex[k]) {
							var value;
							eval("value = jsonStr[" + j + "]." + dataIndex[k] + ";");
							var record = store.getAt(j);
							oldValue = record.get(dataIndex[k]);
							// 南20130309修改，SICP-982 老数据未更新
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
				} else { // 不存在此行则增加行
					store.add(new Ext.data.Record(jsonStr[j]));
				}
			}
			while (store.getCount() > jsonStr.length) { // 多余的行删除
				store.remove(store.getAt(store.getCount() - 1));
			}
		} else {
			Ext.getCmp(gridId).store.removeAll();
		}
	}
}

/**
 * 分页grid的load事件处理
 */
function onPageGridStoreLoad(ds) {
	if (ds.getCount() == 0 && eval("isAlertMsgAfterPageLoadNoResult_" + ds.baseParams.div) == true) {
		odin.alert("没有要查找的数据！");
		return;
	}
	var gridId = ds.baseParams.div;
	var gridColumnModel = Ext.getCmp(gridId).getColumnModel();
	try {
		// 清除由于renderDate造成的数据被修改的标记
		Ext.getCmp(gridId).store.commitChanges();
	} catch (e) {

	}
	// 清空全选的勾
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
 * 根据response传回来的pageGridParams分页参数信息设值到grid的store里，点击下一页等按钮使用
 * pageGridParams数组中的每个对象必须包括以下几个参数
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
 * 显示提示信息
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
 * 报表打印
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
 * grid报表批量查询并自动打印
 */
function billPrintNext() {
	if (billArray == null) {
		return;
	}
	// 打印
	if (printRowIndex < billArray.length) {
		if (billArray.length == 1) {
			printmsg = "正在打印..."
		} else {
			printmsg = "正在打印 " + (printRowIndex + 1) + " / " + billArray.length + " ...";
		}
		eval(billArray[printRowIndex]);
		printRowIndex++;
	} else if (printRowIndex == billArray.length) { // 打印完成，重新初始化
		printRowIndex = 0;
		billArray = null;
		odin.unmask(); // 去掉阴影
	}
}
/**
 * @msgBoxParams msgBox函数的参数
 * @msgBoxParams.msgArray 信息的数组
 * @msgBoxParams.i 信息数组的开始显示索引
 */
var msgBoxParams = {};

/**
 * 数组格式的一条条信息进行提示
 * 
 * @param {Object}
 *            msgArray 信息数组
 * @param {Object}
 *            i 第几个索引开始显示，默认为0
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
 * 按msgBox填好的数组的格式的一条条信息进行提示
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
 * 对页面的操作
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
 * 设置全选
 */
function setSelectAll(gridId, item, value) {
	var obj = document.getElementById("selectall_" + gridId + "_" + item);
	if (obj) {
		obj.checked = value;
		odin.selectAllFunc(gridId, obj, item);
	}
}

/**
 * grid排序操作
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
 * 取得本input项目的父div
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
 * 单据打印
 */
function setBillPrint(repid, queryName, param, preview) {
	var strpreview = "true";
	if (!preview) {
		strpreview = "false";
	}
	var url = contextPath + "/common/billPrintAction.do?repid=" + repid + "&queryname=" + queryName + "&param=" + param + "&preview=" + strpreview;
	var windowId = "win_billprint";
	if (printmsg == "") {
		printmsg = "正在打印...";
	}
	if (preview == true) {
		printmsg = printmsg + "<br>如要继续，请先关闭预览打印界面。";
	}
	odin.mask(printmsg); // 加上阴影
	showWindowWithSrc(windowId, url, null);
	Ext.getCmp(windowId).hide();
}
/**
 * 单据打印2
 */
function setBillPrint2(reportlet, params, setup, printmode) {
	var url = contextPath + "/common/billPrint2Action.do?reportlet=" + reportlet + "&params=" + (params.replace(/%/g, "％")).replace(/\+/g, "%2B") + "&setup=" + setup + "&printmode=" + printmode;
	var windowId = "win_billprint";
	if(odin.isWorkpf){
		if(qtobj){
			qtobj.openNewWindow(url,"打印状态",280,160);
			//window.showModalDialog(url,"打印状态",280,160);
		}
	}else{
		var top = getTopParent();
		var pupWindow = top.Ext.getCmp(windowId);
		pupWindow.setTitle("打印状态"); // 标题
		pupWindow.setSize(280, 160); // 宽度 高度
		top.showWindowWithSrc(windowId, cjkEncode(url), null);
		var x = top.document.body.clientWidth - 280 - 10;
		var y = top.document.body.clientHeight - 160 - 10;
		pupWindow.setPosition(x, y);
	}
	if (reflushAfterSavePrint != null && reflushAfterSavePrint != "") { // 保存后的打印，打印发送后就刷新
		reflush();
	}
}

// 报表中文字符转换
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
// 报表中文字符解析
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
 * 根据值取得下拉框的名称
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
 * 带参数的下拉框过滤
 */
function setSelectFilterWithParams(gridId, objId, aaa100, aaa105, filter, isRemoveAllBeforeAdd, isAddBeforeFirst, isAddAllAsItem) {
	Ext.getCmp(gridId).addListener('beforeedit', function(e) {
				if (e.msgShow == false) {// 检查是否允许编辑
					return true;
				}
				if (e.field == objId) {
					var record = e.record; // 返回Record对象
					var theFilter = filter;
					while (theFilter.indexOf("[") != -1) {// 去掉参数
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
 * 为select store对象根据一定的查询条件和过滤条件重新加载数据
 * 
 * @param {Object}
 *            objId 要加载数据的组件id
 * @param {Object}
 *            aaa100 代码类别
 * @param {Object}
 *            aaa105 参数分类
 * @param {Object}
 *            filter 过滤条件
 * @param {Object}
 *            isRemoveAllBeforeAdd 加载之前是否要清除以前的数据，默认清除
 * @param {Object}
 *            isAddBeforeFirst 如果不清除数据，是否加到最前面，默认不加最前面，即加到最后面
 * @param {Object}
 *            isAddAllAsItem 第一行是否增加“全部”选项
 */
function setSelectFilter(objId, aaa100, aaa105, filter, isRemoveAllBeforeAdd, isAddBeforeFirst, isAddAllAsItem, isWithParams) {
	if (Ext.getCmp(objId + "_combo") == null && getGridColumn(getParentDiv(objId), objId) == null) {
		return;
	}
	if (Ext.getCmp(objId + "_combo") && Ext.getCmp(objId + "_combo").mode == "remote") { // 远程过滤特殊处理
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
	if (filter != null && filter != "" && filter.trim().indexOf("select") == 0) { // select开头
		params.querySQL = filter;
	}
	params.sqlType = "SQL";
	var req = odin.commonQuery(params, odin.blankFunc, odin.blankFunc, false, false);
	var data = odin.ext.decode(req.responseText).data.data;
	setSelectData(objId, data, isRemoveAllBeforeAdd, isAddBeforeFirst, isAddAllAsItem, isWithParams);
}

/**
 * 为select store对象进行加载数据
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
		if (isRemoveAllBeforeAdd == false) { // 不清除
			var selectData = new Array(jsonData.length);
			for (i = 0; i < jsonData.length; i++) {
				selectData[i] = new odin.ext.data.Record(jsonData[i]);
			}
			var store = comboObj.store;
			if (isAddBeforeFirst) { // 在最前面
				store.insert(0, selectData);
			} else { // 在最后面
				store.add(selectData);
			}
		} else { // 清除
			reSetSelectData(objId, jsonData);
		}
	} else {// 清除
		reSetSelectData(objId, jsonData);
	}
	var isAllAsItemForSelect2 = isAllAsItemForSelect(objId);
	if (isAllAsItemForSelect2 == false && (isAddAllAsItem == true || isAllAsItemForSelect1 == true)) { // 增加“全部”选项
		addAllAsItemForSelect(objId);
	}
	if (document.getElementById(objId) != null) {// 设置非grid下拉框combo值
		value = document.getElementById(objId).value;
		if (Ext.getCmp(objId + "_combo") && value != null && value != "") {
			odin.setSelectValue(objId, value);
		}
	} else { // grid的特殊处理
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
	if (comboObj.hasFocus) { // 如果有焦点了，则触发一下点击事件来重新调整下拉框高度
		comboObj.onTriggerClick();;
	}
}

/**
 * 增加“全部”下拉选项
 */
function addAllAsItemForSelect(selectId) {
	var store = odin.ext.getCmp(selectId + "_combo").store;
	var selectData = new odin.ext.data.Record({
				"key" : "all",
				"value" : "全部"
			});
	store.insert(0, selectData);
}

/**
 * 是否有“全部”下拉选项(仅判断第一行)
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
 * 将select下拉数据清除掉，此功能不清除输入项的值，odin的功能会清除输入项的值
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
	if (typeof(store) == "undefined") {// 无combo的情况
		return;
	}
	var count = store.getCount();
	// store.removeAll(); 使用它会有问题，当第一次对同一个对象使用此方法没问题，但以后就会报js出错
	// 所以这里通过一条一条来remove数据
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
 * 为select store对象选择默认值
 * 
 * @param {Object}
 *            objId 要加载数据的组件id
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
	if (isGrid == false) { // 非grid
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
 * 取得label的span信息
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
 * 对grid滚动到某一行并选中
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
 * 判断是否为grid
 */
function isGridDiv(divId) {
	if (document.getElementById("gridDiv_" + divId) == null) { // 非grid
		return false;
	} else {
		return true;
	}
}

/**
 * 判断是否为分页grid
 */
function isPageGridDiv(divId) {
	if (Ext.getCmp(divId) && ((Ext.getCmp(divId).getTopToolbar() && Ext.getCmp(divId).getTopToolbar().pageSize) || (Ext.getCmp(divId).getBottomToolbar() && Ext.getCmp(divId).getBottomToolbar().pageSize))) {
		return true;
	} else {
		return false;
	}
}

/**
 * 取得div类型：：0不分页Grid 1分页Grid 2非Grid
 */
function getDivType(divId) {
	var divType = "0"; // 0不分页Grid 1分页Grid 2非Grid
	if (Ext.getCmp(divId) && ((Ext.getCmp(divId).getTopToolbar() && Ext.getCmp(divId).getTopToolbar().pageSize) || (Ext.getCmp(divId).getBottomToolbar() && Ext.getCmp(divId).getBottomToolbar().pageSize))) {
		divType = "1";
	} else if (document.getElementById("gridDiv_" + divId) == null) { // 非grid
		divType = "2";
	}
	return divType;
}

/**
 * 将焦点放到某个可以编辑的项目里，支持grid和table
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
		} else { // 非grid
			setFocus(colName);
		}
	} catch (e) {
	}
}

/**
 * 操作div所有input项目的属性，如readonly属性、required属性等
 */
function opItemAll(div, opstr, trueOrFalse) {
	eval("opItem" + opstr.substr(0, 1).toUpperCase() + opstr.substr(1).toLowerCase() + "All('" + div + "'," + trueOrFalse + ")");
}

/**
 * 操作input项目的属性，如readonly属性、required属性等
 */
function opItem(div, items, opstr, trueOrFalse) {
	var itemArray = items.split(",");
	for (var i = 0; i < itemArray.length; i++) {
		var item = itemArray[i];
		eval("opItem" + opstr.substr(0, 1).toUpperCase() + opstr.substr(1).toLowerCase() + "('" + div + "','" + item + "'," + trueOrFalse + ")");
	}

}

/**
 * 操作div所有项目的readonly属性，即是否只读
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
 * 操作项目的readonly属性，即是否只读
 */
function opItemReadonly(div, item, isReadOnly) {
	if (Ext.getCmp(item + "_combo")) { // 下拉框
		odin.setComboReadOnly(item, isReadOnly);
	} else if (getFieldType(item) == "date") { // 日期控件
		odin.setDateReadOnly(item, isReadOnly);
	} else if (getDivItem(div, item)) { // 普通
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
		Ext.getCmp(div).getView().refresh();// 重新触发各列的render事件
	}
}

/**
 * 操作div所有项目的disabled属性，即是否不能编辑
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
 * 操作项目的disabled属性，即是否不能编辑
 */
function opItemDisabled(div, item, isDisabled) {
	if (getGridColumn(div, item)) { // grid,先grid，否则grid与非grid会有问题
		var fuc = "listeners.set_" + div + "_" + item + "_Disabled";
		if (isDisabled == true) {
			eval("Ext.getCmp(div).removeListener('beforeedit'," + fuc + ")");
			eval(fuc + "=function(e){" + " if(e.field==\"" + item + "\"){e.cancle=true;return false;}else{return true;}};");
			eval("Ext.getCmp(div).addListener('beforeedit'," + fuc + ")");
		} else {
			eval("Ext.getCmp(div).removeListener('beforeedit'," + fuc + ")");
		}
		getGridColumn(div, item).editable = !isDisabled;
		Ext.getCmp(div).getView().refresh();// 重新触发各列的render事件
	} else if (Ext.getCmp(item + "_combo")) { // 下拉框
		var cmp = Ext.getCmp(item + "_combo");
		cmp.setDisabled(isDisabled);
		if (cmp.list) {
			cmp.list.hide();
		}
		var hideTrigger = isDisabled;
		cmp.hideTrigger = hideTrigger;
		cmp.trigger.setDisplayed(!hideTrigger);
		// 重新设置宽度
		var width = cmp.width;
		cmp.setWidth(0);
		cmp.setWidth(width);
	} else if (Ext.getCmp(item)) { // 普通
		Ext.getCmp(item).setDisabled(isDisabled);
		var cmp = Ext.getCmp(item);
		if (typeof(cmp.hideTrigger) != "undefined") {
			var hideTrigger = isDisabled;
			if (document.getElementById(item).getAttribute("isQuery") == "true") {
				hideTrigger = true;
			}
			cmp.hideTrigger = hideTrigger;
			cmp.trigger.setDisplayed(!hideTrigger);
			// 重新设置宽度
			var width = cmp.width;
			cmp.setWidth(0);
			cmp.setWidth(width);
		}
	} else if (document.getElementById(div) && getDivItem(div, item)) { // 其他，包括checkbox
		getDivItem(div, item).disabled = isDisabled;
	} else if (document.getElementById(item)) { // 其他，菜单按钮
		document.getElementById(item).disabled = isDisabled;
	}
}

/**
 * 操作div所有项目的required属性，即是否必填
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
 * 操作项目的required属性，即是否必填
 */
function opItemRequired(div, item, isRequired) {
	var isAllowBlank = ((isRequired == "true" || isRequired == true) ? false : true);
	eval("getDivItem(div,item).setAttribute(\"required\", \"" + isRequired + "\")");
	if (getDivItem(div, item + "SpanId")) {
		getDivItem(div, item + "SpanId").innerHTML = getLabelSpan(getDivItem(div, item).getAttribute("label"), isRequired);
	}
	if (Ext.getCmp(item + "_combo")) { // 下拉框
		Ext.getCmp(item + "_combo").allowBlank = isAllowBlank;
	} else if (Ext.getCmp(item)) {
		Ext.getCmp(item).allowBlank = isAllowBlank;
	}
}

/**
 * 操作div所有项目的visible属性，即是否可见
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
 * 操作项目的visible属性，即是否可见
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
 * 自动调整table行是否可见
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
 * 自动操作table的行是否可见
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
 * 操作项目的标签Label显示信息
 */
function opItemLabel(div, item, newLabel) {
	if (document.getElementById(div) && getDivItem(div, item)) {// 普通html
		if (getDivItem(div, item).getAttribute("label")) { // 修改label
			getDivItem(div, item).setAttribute("label", newLabel);
		}
		if (getDivItem(div, item + "SpanId")) { // 修改显示的labelSpan
			getDivItem(div, item + "SpanId").innerHTML = getLabelSpan(newLabel, getDivItem(div, item).getAttribute("required"));
		}
	} else if (Ext.getCmp(div) && Ext.getCmp(div).items && Ext.getCmp(div).items.get(item)) {// 菜单按钮内容的操作
		Ext.getCmp(div).items.get(item).setText(newLabel);
	} else if (document.getElementById(item)) {// 菜单内容的操作
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
 * 操作项目的属性
 */
function opItemAttribute(div, item, attributeName, attributeValue) {
	if (document.getElementById(div) && document.getElementById(item)) {// 普通html
		document.getElementById(item).setAttribute(attributeName, attributeValue);
	} else if (document.getElementById(item)) {// 菜单内容的操作
		document.getElementById(item).setAttribute(attributeName, attributeValue);
	}
	if (getGridColumn(div, item)) {// grid
		eval('getGridColumn("' + div + '", "' + item + '").' + attributeName + '="' + attributeValue + '"');

	}
	// isQuery特殊处理，去掉或显示查询按钮
	if (attributeName == "isQuery" && (item.indexOf("psquery") != -1 || item.indexOf("cpquery") != -1 || item.indexOf("psidquery") != -1 || item.indexOf("psidnew") != -1)) {
		if (Ext.getCmp(item)) {
			var cmp = Ext.getCmp(item);
			var hideTrigger = false;
			if (attributeValue == "false") {
				hideTrigger = true;
			}
			cmp.hideTrigger = hideTrigger;
			cmp.trigger.setDisplayed(!hideTrigger);
			// 重新设置宽度
			var width = cmp.width;
			cmp.setWidth(0);
			cmp.setWidth(width);
		}
	}
}

/**
 * 修改分页grid的汇总信息
 */
function setSumMsg(divName, sumMsg) {
	opItemLabel('', divName + "_sumMsgText", sumMsg);
}

/**
 * 打印div
 */
function doPrintDiv(div) {
	if (div == null) {
		div = "printDiv";
	}
	printDiv(div);
}

/**
 * 打印指定div
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
 * 打印当前窗口
 */
function doPrintWindow() {
	window.print();
}

var lastIframeSrc = ""; // 上次链接，因为报表都是自动查询，所以会有两次相同的链接
var printmsg = ""; // 打印显示的提示信息，批量打印时使用
/**
 * 状态改变触发的事件（仅IE使用）
 */
function doRepOnreadystatechange(iframe) {
	if (iframe.readyState == 'loading') {
		if (getRepType() != "1") {// 批量打印，备注：非批量打印如果加阴影，则有些ie会由于未加载页面完成出问题
			if (printmsg != "") { // 有需要显示的信息才加阴影
				odin.mask(printmsg); // 加上loading阴影
			}
		}
	} else if (iframe.readyState == 'complete') {
		odin.unmask(); // 去掉阴影
		if (lastIframeSrc != "" && lastIframeSrc != contextPath + "/blank.htm" && lastIframeSrc == iframe.src) {
			lastIframeSrc = contextPath + "/blank.htm";
			if (getRepCount() == 0) {
				odin.alert("没有要打印的数据！");
				return;
			}
			if (getRepType() == "0") {// 隐藏打印
				getIFrameWin(iframe.id).document.execCommand('print', false, null);
			} else if (getRepType() == "2") {// 批量打印
				getIFrameWin(iframe.id).document.execCommand('print', false, null);
				repsPrintNext("printIframe", "div_2");
			}
		} else {
			lastIframeSrc = iframe.src;
		}
	}
}

/**
 * 导出时状态改变触发的事件
 */
var expReadyState = "";
function doExpOnreadystatechange(iframe) {
	if (iframe.readyState == 'interactive' && expReadyState == "") {
		// 加上loading阴影
		expReadyState = iframe.readyState;
	} else if (iframe.readyState == 'complete') {
		odin.unmask(); // 去掉阴影
		expReadyState = iframe.readyState;
	} else if (iframe.readyState == 'interactive' && expReadyState != "") {
		odin.unmask(); // 去掉阴影
		expReadyState = "";
		getIFrameWin("iframe_win_pup").doCloseWin();
	}
}
/**
 * 导出时状态改变触发的事件
 */
function doExpOnload(iframe) {
	if (iframe.src == null || iframe.src == contextPath + "/blank.htm") {
		return;
	}
	// if (iframe.readyState == 'complete') {
	odin.unmask(); // 去掉阴影
	// expReadyState = iframe.readyState;
	// }else if (iframe.readyState == 'interactive' && expReadyState ==
	// "complete") {
	// odin.unmask(); // 去掉阴影
	// expReadyState = "";
	// getIFrameWin("iframe_win_pup").doCloseWin();
	// }
}
/**
 * 报表批量打印
 */
function doRepsPrint() {
	repsPrintNext("printIframe", "div_2");
}

/**
 * 报表打印
 */
function doRepPrint() {
	doPrintIframe();
}

/**
 * 打印名称为printIframe的iframe
 */
function doPrintIframe() {
	printIframe("printIframe");
}

/**
 * 打印指定iframe
 */
function printIframe(iframe) {
	getIFrameWin(iframe).focus();
	getIFrameWin(iframe).document.execCommand('print', false, null);
}

/**
 * 报表查询
 */
function doRepQuery() {
	repQuery("printIframe", "div_1");
}

/**
 * 隐藏报表查询并打印
 */
function doHiddenRepPrint() {
	repType = "0"; // 隐藏模式
	repQuery("printIframe", "div_2");
	repType = ""; // 还原
}

/**
 * grid报表批量查询并自动打印
 */
var printRowIndex = 0;
function repsPrintNext(iframe, div) {
	var repIframe = document.getElementById(iframe);
	var src = repurl;
	if (src.indexOf("?") >= 0) {
		src = src.substr(0, src.indexOf("?"));
	}
	src = src + "?&";
	src = src + "REPQUERYNAMEE='Q001'&"; // 默认值，报表名称
	src = src + "SHOWMODLE=REPORT&"; // 默认值，显示模式
	src = src + "CHARTID=ALL&"; // 默认值，图表ID
	src = src + "AUTO_RUN=YES&"; // 默认值，自动运行
	var grid = Ext.getCmp(div);
	var store = grid.store;
	var gridColumnModel = grid.getColumnModel();
	var dataIndex = new Array(gridColumnModel.getColumnCount());
	for (var i = 0; i < gridColumnModel.getColumnCount(); i++) {
		dataIndex[i] = gridColumnModel.getDataIndex(i);
	}
	var selectionModel = grid.getSelectionModel();
	if (selectionModel.getCount() == 0) {
		odin.alert("请选择要打印的数据！");
		return;
	}
	for (var j = printRowIndex; j < store.getCount(); j++) { // 打印一行，下有break
		if (!selectionModel.isSelected(j)) {// 未选中
			continue;
		}
		printmsg = "正在打印第" + (j + 1) + "行信息...";
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
		repIframe.src = src; // 页面换掉
		printRowIndex = j + 1; // 下次打印开始行
		break;
	}
	if (j == store.getCount()) { // 打印完成，重新初始化
		printRowIndex = 0;
	}
}

/**
 * 报表查询
 */
function repQuery(iframe, div) {
	var repIframe = document.getElementById(iframe);
	var src = repurl;
	var queryDiv = document.getElementById(div);
	if (src.indexOf("?") >= 0) {
		src = src.substr(0, src.indexOf("?"));
	}
	src = src + "?&";
	src = src + "REPQUERYNAMEE='Q001'&"; // 默认值，报表名称
	src = src + "SHOWMODLE=REPORT&"; // 默认值，显示模式
	src = src + "CHARTID=ALL&"; // 默认值，图表ID
	src = src + "AUTO_RUN=YES&"; // 默认值，自动运行
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
	repIframe.src = src; // 页面换掉
}

/**
 * 报表iframe的onload事件操作
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
 * iframe自动调整位置
 */
function autoResize(item, div) {
	item.style.height = 415 - document.getElementById(div).offsetHeight;
}

/**
 * 报表界面清空
 */
function doRepClear() {
	reflush();
}

var repType = ""; // 报表类型::0隐藏打印，1单张打印，2批量打印
/**
 * 取报表类型::1单张打印，2批量打印
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
 * 取报表的结果行数
 */
function getRepCount() {
	return getIFrameWin("printIframe").getCount();
}

function round(Dight, How) {
	Dight = Math.round(Dight * Math.pow(10, How)) / Math.pow(10, How);
	return Dight;
}

/**
 * 格式化数据
 */
function renderInt(value) {
	if (value == 'undefined' || value == null) {
		value = 0;
	}
	var str = formatMoney(new String(parseFloat(value).toFixed(0)));
	return str.substr(0, str.indexOf('.'));
}
/**
 * 格式化数据
 */
function renderMoney(value) {
	if (value == 'undefined' || value == null) {
		value = 0;
	}
	value = Number(new String(value).replace(/,/g, ""));
	return formatMoney(new String(parseFloat(value).toFixed(2)));
}

/**
 * 格式化钱
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
 * 校验数字的年月
 */
function isYM(value) {
	value = value.toString();
	if (value.length != 6) {
		return "长度必须为6位！";
	}
	if (value.substr(4) > 12) {
		return "月份不能大于12！";
	}
	if (value.substr(4) == 0) {
		return "月份不能为00！";
	}
	return true;
}

/**
 * 校验日期的格式
 */
function isBeforeSysdate(value) {
	if (renderDate(value) > renderDate(odin.getSysdate())) {
		return "输入的日期不能大于当前日期！";
	}
	return true;
}

/**
 * 根据div拼装sql的where条件，放到后台
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
 * inputItemName.indexOf("psidnew") == 0) {// 特殊字段不处理 continue; } if
 * (inputItemName.indexOf("nonem") != -1 || inputItemName.indexOf("_s") != -1 ||
 * inputItemName.indexOf("_combo") != -1) { continue; } var value1 =
 * inputItem.value; var isSelectValue = (Ext.getCmp(inputItemName + "_combo") !=
 * null); if (inputItem.type == "checkbox") { value1 = inputItem.checked; } if
 * (Ext.getCmp(inputItem.name)) { // 标签处理 value1 =
 * Ext.getCmp(inputItem.name).getValue(); } if (isSelectValue) { value1 = "'" +
 * value1 + "'"; } // 下拉框all为全部，不加where条件 if (isSelectValue && value1 != null &&
 * value1.toString().toLowerCase() == "'all'") { value1 = "''"; } var value2 =
 * value1; // 取value2 if (document.getElementById(inputItemName + "_s")) { var
 * inputItem2 = document.getElementById(inputItemName + "_s"); value2 =
 * inputItem2.value; if (inputItem2.type == "checkbox") { value2 =
 * inputItem2.checked; } if (Ext.getCmp(inputItem2.name)) { // 标签处理 value2 =
 * Ext.getCmp(inputItem2.name).getValue(); } if (isSelectValue) { value2 = "'" +
 * value2 + "'"; } // 下拉框all为全部，不加where条件 if (isSelectValue && value2 != null &&
 * value2.toString().toLowerCase() == "all") { value2 = "''"; } } else if
 * (document.getElementById(inputItemName + "_S")) { var inputItem2 =
 * document.getElementById(inputItemName + "_S"); value2 = inputItem2.value; if
 * (inputItem2.type == "checkbox") { value2 = inputItem2.checked; } if
 * (Ext.getCmp(inputItem2.name)) { // 标签处理 value2 =
 * Ext.getCmp(inputItem2.name).getValue(); } if (isSelectValue) { value2 = "'" +
 * value2 + "'"; } // 下拉框all为全部，不加where条件 if (isSelectValue && value2 != null &&
 * value2.toString().toLowerCase() == "all") { value2 = "''"; } }
 * 
 * var col = inputItemName; var index = inputItemName.indexOf("_"); if (index !=
 * -1) { col = inputItemName.substring(index + 1) + "." +
 * inputItemName.substring(0, index); } retStr = retStr + sqlWhere(col, value1,
 * value2); } } } return retStr; }
 */
/**
 * 根据列拼装sql的where条件，放到后台
 */
/*
 * function sqlWhere(col, value1, value2) { if (value1 != null && (value1 == "" ||
 * value1 == "''")) { value1 = null; } if (value2 != null && (value2 == "" ||
 * value2 == "''")) { value2 = null; } if (value1 == null && value2 == null) {
 * return ""; } var stringValue1 = ""; var stringValue2 = ""; var field = col;
 * var index = col.indexOf("."); if (index != -1) { field = col.substring(index +
 * 1) + "_" + col.substring(0, index); } var type = getFieldType(field); var
 * retStr = ""; var isSelectValue = false; // 转换成String类型 if (value1 != null) {
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
 * if (isSelectValue) { // 下拉框特殊处理 '1'为='1' '1,2'为in('1','2') if
 * (stringValue1.indexOf(",") == -1) { // 单个，则用= retStr = "@_WPcbvFuwN1Y=_@" +
 * col + "@_tcNkNHzlSVk=_@" + stringValue1 + "@_rHmXV9CvNJA=_@" + retStr; } else
 * {// 多个，则用in retStr = "@_WPcbvFuwN1Y=_@" + col + "@_j3CplyExdf0=_@" +
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
 * grid增加一行
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
		Ext.getCmp(gridId).getView().refresh();// 重新触发各列的render事件
	}
}
/**
 * grid在指定行前插入一行
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
 * 删除指定行
 */
function doDeleteRow(gridId, rowIndex) {
	var store = Ext.getCmp(gridId).store;
	store.remove(store.getAt(rowIndex));
}

function doClickDeleteRow(btn, clickParams, currentRow, colName) {
	var tmp = btn;
	odin.confirm("确定要删除本行？", function(btn) {
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
 * 删除本行的render操作
 */

function renderDeleteRow(value, params, record, rowIndex, colIndex, ds) {
	var clickParams;
	var valueArray;
	var clickId = null; // 按钮id
	if (value != null && value != 'null' || value != "") {
		value = new String(value);
	}
	valueArray = value.split(",");
	if (valueArray.length > 2) {
		clickId = valueArray[2]; // 按钮id
	}
	if (valueArray.length > 3) {
		clickParams = new Array(valueArray.length - 3);
	}
	for (var i = 3; i < valueArray.length; i++) {
		clickParams[i - 3] = valueArray[i];
	}

	if (value == "false") {
		return "<a href='#' style='color:#CCCCCC'>删除</a>";
	} else {
		return "<a href='#' class='render' onclick='doClickDeleteRow(\"" + clickId + "\",\"" + Ext.encode(clickParams).replace(/"/g, "\\\"") + "\"," + rowIndex + ",\"" + Ext.getCmp(ds.baseParams.div).getColumnModel().getDataIndex(colIndex) + "\")'>删除</a>";
	}
}

/**
 * 取得登陆用户名
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
 * 取得当前辖区
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
 * 取得当前统筹区
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
 * 取得psquery的缓存值
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
 * 设置psquery的缓存值
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
 * 取得cpquery的缓存值
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
 * 设置cpquery的缓存值
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
 * 取得psidquery的缓存值
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
 * 设置psidquery的缓存值
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
 * 除法函数，用来得到精确的除法结果 说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
 * 调用：accDiv(arg1,arg2) 返回值：arg1除以arg2的精确结果
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
 * 乘法函数，用来得到精确的乘法结果 说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
 * 调用：accMul(arg1,arg2) 返回值：arg1乘以arg2的精确结果
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
 * 加法函数，用来得到精确的加法结果 说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
 * 调用：accAdd(arg1,arg2) 返回值：arg1加上arg2的精确结果
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
 * 减法函数，用来得到精确的减法结果 说明：javascript的减法结果会有误差，在两个浮点数减法的时候会比较明显。这个函数返回较为精确的减法结果。
 * 调用：accSub(arg1,arg2) 返回值：arg1减去arg2的精确结果
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
	// 动态控制精度长度
	n = (r1 >= r2) ? r1 : r2;
	return ((arg1 * m - arg2 * m) / m).toFixed(n);
}

/**
 * 设置父窗口
 */
function setParentWin(parentWin) {
	this.parentWin = parentWin;
}

/**
 * 取得div的item
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
 * 兼容IE、Firefox的iframe窗口获取函数
 */
function getIFrameWin(id) {
	return document.getElementById(id).contentWindow || document.frames[id];
}
/**
 * 兼容firefox的 outerHTML 使用以下代码后，firefox可以使用element.outerHTML
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
 * 设置ajax请求超时时间
 * 
 * @param n
 *            超时时间，单位为秒
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
//jinwei add for 单据打印 -- 2013.8.22
function opsenoToReprint(opseno){
	return "<a href='javascript:reprint("+opseno+")'>重新打印</a>";
}

function reprint(opseno){
	odin.Ajax.request(contextPath+"/sys/PrintAction.do?method=getPrintlog",
  			{opseno:opseno},reprintSuccess);
}

function reprintSuccess(response){
	for(var i=0;i<response.data.length;i++){
		if(confirm("是否打印【"+response.data[i].title+"】？")){
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
// jinwei add for 单据打印 end
