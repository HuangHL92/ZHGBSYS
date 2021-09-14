/**
 * Odin Grid 右键通用菜单支撑JS
 * @author jinwei
 * @date 2013.1.11
 * @type
 * @description
 * version:6.0.1 修改右键通用菜单里的导出界面数据功能，增加根据当前JSP里是否有“var div_1_checkbox_dataindex = "check";”定义的某表格的选择列，如果有则只导出选中，否则导出界面所有数据
 * version:6.0.2 如果定义了某表格的checkbox数据列信息，则不管是导出当前页还是导出全部数据都自动跳过该列 -- jinwei 2013.12.16
 * version:6.0.3 增加表格可根据开发人员定义的“gridid_exp_dataindex”类似的全局变量来导出其中某几列数据，如“var div_1_exp_dataindex = "aaa001,aaa002"; //只导出aaa001和aaa002列” -- jinwei 2014.2.12
 * version:6.0.4 提供对表格右键导出可自动识别数字列的支撑 -- jinwei 2014.2.14
 */
sortParam = {}; //排序全局参数 
filterParam = {}; //过滤全局参数
Ext.namespace("odin.grid.menu");
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
	showWindowWithSrc(windowId, src, initParams);
	pupWindow.center(); // 居中
}
/**
 * 兼容IE、Firefox的iframe窗口获取函数
 */
function getIFrameWin(id) {
	return document.getElementById(id).contentWindow || document.frames[id];
}
/**
 * 根据传入的地址显示窗口
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
	newSrc = addUrlParam(newSrc, "clientDate=" + Ext.util.Format.date(new Date(), "YmdHis"));// 使每次请求都不一样，解决请求一样打不开的问题
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
 * 给url加参数
 * @param {} url 原始url
 * @param {} paramUrl 参数url，如 &a=1&b=2
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
			var url = contextPath + "/sys/comm/commSetGrid.jsp";
			doOpenPupWin(url, "设置每页条数", 300, 200);
		} else {
			odin.error("非分页grid不能使用此功能！");
			return;
		}
	},
	gridContextmenu : function(e, grid, hasAllRightMenu) {
		return;//项目经理说表格右键菜单都不要。
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
						odin.grid.menu.expExcelFromGrid(grid.id, null, null,
								null, true)
					}
				});
		var expAllMenuItem = new Ext.menu.Item({
					id : 'expAll',
					text : '导出全部数据',
					handler : function() {
						odin.grid.menu.expExcelFromGrid(grid.id, null, null,
								null, false)
					}
				});
		var setPageSizeMenuItem = new Ext.menu.Item({
					id : 'setPageSize',
					text : '设置每页条数',
					handler : function() {
						odin.grid.menu.setPageSize(grid.id)
					}
				});
		var doSortAllMenuItem = new Ext.menu.Item({
					id : 'doSortAll',
					text : '全部数据排序',
					handler : function() {
						odin.grid.menu.doSortAll(grid.id)
					}
				});
		var doFilterAllMenuItem = new Ext.menu.Item({
					id : 'doFilterAll',
					text : '全部数据过滤',
					handler : function() {
						odin.grid.menu.doFilterAll(grid.id)
					}
				});
		var printMenuItem = new Ext.menu.Item({
					id : 'print',
					text : '打印界面数据',
					handler : function() {
						odin.grid.menu.doPrint(grid.id,false);
					}
				});
		var printAllMenuItem = new Ext.menu.Item({
					id : 'printAll',
					text : '打印全部数据',
					handler : function() {
						odin.grid.menu.doPrint(grid.id,true);
					}
				});			
		var rightMenu = new Ext.menu.Menu();

		rightMenu.addItem(expMenuItem);
		// if (isPageGridDiv(grid.id)) {
		rightMenu.addItem(expAllMenuItem);
		//rightMenu.addItem(printMenuItem);//打印去掉
		if (hasAllRightMenu == "true") {
			rightMenu.addSeparator();
			rightMenu.addItem(doSortAllMenuItem);
			rightMenu.addItem(doFilterAllMenuItem);
			rightMenu.addSeparator();
			rightMenu.addItem(setPageSizeMenuItem);
			rightMenu.addSeparator();
			//rightMenu.addItem(printAllMenuItem);//打印去掉
		}
		// }
		e.preventDefault();// 屏蔽默认事件
		document.oncontextmenu = function() { // 屏蔽默认右键
			return false;
		}
		rightMenu.on('beforehide', function() { // 在右键菜单关闭时启用默认右键
					rightMenu.destroy();
					document.oncontextmenu = function() {
						return true;
					}
				});
		rightMenu.showAt(e.getXY());
	},
	/**
	 * 导出Excel
	 * @param {} gridId
	 * @param {} fileName
	 * @param {} sheetName
	 * @param {} headNames
	 * @param {} isFromInterface
	 */
	expExcelFromGrid : function(gridId, fileName, sheetName, headNames,
			isFromInterface) {
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
					/<[^>]+>/g, "").replace(/,/g, "，");
			index++;
		}
		
		querySQLSim = "Radow$"+MDParam.functionid + "," + gridId;
		
		if (isFromInterface != null && isFromInterface != true
				&& odin.grid.menu.isPageGridDiv(gridId)) {// 分页grid处理全部页
			// var querySQL = eval("pageGridSql." + gridId);
			
			if (headNames == null || headNames == "" || headNames == "null") { // 表头
				headNames = {};
				for (var i = 0; i < dataIndex.length; i++) {
					eval("headNames." + dataIndex[i] + "="
							+ Ext.encode(columnHeader[i]));
				}
				headNames = Ext.encode(headNames);
			}
			var decodeJson; // decode转换
			for (var i = 0; i < dataIndex.length; i++) {
				var col = odin.getGridColumn(gridId, dataIndex[i]);
				if (col.editor) {
					var type = "";
					try{
						type = col.editor.field.getXType();
					}catch(e){
						//
					}
					if (type == "combo") {// 只操作下拉框格式的列
						var decodeStrArray = eval(dataIndex[i] + "_select");
						var decodeStr = "";
						for (var j = 0; j < decodeStrArray.length; j++) {
							decodeStr += "," + decodeStrArray[j][0] + ","
									+ decodeStrArray[j][1];
						}
						decodeStr = decodeStr.substring(1);
						if (decodeStr != "") {// 不为空才操作
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
		} else { // 不分页grid处理界面信息
			if (headNames == null || headNames == "" || headNames == "null") { // 表头
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
								// 修正renderAlt造成的特殊字符
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
			fileNameSim = MDParam.title /*+ "_" + gridId.replace("div_", "区域")*/
					+ "_" + Ext.util.Format.date(new Date(), "Ymd");
		} else {
			fileNameSim = fileName;
		}
		if (sheetName == null || sheetName == "" || sheetName == "null") {
			sheetNameSim = MDParam.title /*+ "_" + gridId.replace("div_", "区域");*/
		} else {
			sheetNameSim = sheetName;
		}
		window.jsonTypeSim = odin.encode(jsonType);
		headNamesSim = headNames;
		doOpenPupWin(contextPath + "/sys/excel/commSimpleExpExcelWindow.jsp",
				"下载文件", 500, 160);
		
		//$h.openWin('dow','sys.excel.commSimpleExpExcelWindow','选择模板',500,160,'传参',contextPath);
	},
	/**
	 * 判断是否为分页grid
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
	 * 取得grid的列索引
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
	 * 排序
	 * 
	 * @param {Object}
	 *            divName 要排序的div名称
	 * @param {Object}
	 *            colsName 允许排序的列名，多个用逗号隔开，如"aae135,aac003",不传则默认界面所有允许排序的列
	 */
	doSortAll:function (divName, colsName) {
		if (Ext.getCmp(divName).store.baseParams.cueGridId == null) {
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
		doOpenPupWin(contextPath + "/sys/comm/commSort.jsp", "排序窗口", 420, 300);
	},
	/**
	 * 过滤
	 * 
	 * @param {Object}
	 *            divName 要过滤的div名称
	 * @param {Object}
	 *            colsName 允许过滤的列名，多个用逗号隔开，如"aae135,aac003"
	 */
	doFilterAll:function(divName, colsName) {
		if (Ext.getCmp(divName).store.baseParams.cueGridId == null) {
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
		doOpenPupWin(contextPath + "/sys/comm/commFilter.jsp", "过滤窗口", 650, 400);
	},
	doPrint:function(gridId,isPrintAll){
		if (Ext.getCmp(gridId).store.getCount() == 0) {
			odin.alert("表格没有数据可供打印或者没有查询！");
			return;
		}
		doOpenPupWin(contextPath + "/sys/comm/commPrint.jsp?gridId="+gridId+"&isPrintAll="+isPrintAll+"&functionid="+MDParam.functionid, "表格打印", 0.9, 0.8);
		
	}
};