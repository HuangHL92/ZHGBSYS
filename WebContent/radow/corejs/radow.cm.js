/***
 * **************************
 * 浙大网新恩普软件有限公司
 * 核三Radow框架集成commform的核心js文件
 * auther:jinwei
 * date:2013-2-25
 * version:6.0
 * version:6.0.1 增加了对IMG标签setDisable的支持和随之的样式变化，可编辑和不可编辑的鼠标移动样式处理 jinwei 2013.7.18
 * version:6.0.2 修复当我们系统嵌入到其它系统时F9等快捷方式打开父窗口会出错问题 -- jinwei 2013.7.24
 * version:6.0.3 修复doGridCheck,afteredit方法没有在pageParam里面放入currentValue（当前参数）,currentOriginalValue（编辑前参数）两个参数 -- ljd 2013.7.26
 * version:6.0.4 修复有些非PageModel和BS方式页面引进该JS时报page_url_params为定义错误 -- jinwei 2013.8.14
 * version:6.0.5  doFailMsgAndFail方法中替换mainMessage中所有\'为'-- ljd 2013.8.15
 * version:6.0.6 修复向表格中增加数据时出现两行相同的数据问题（末尾两行） -- jinwei 2013.8.16
 * version:6.0.7 修复关于selectall在报异常时不能正确回到之前状态而出现选中错乱问题 -- jinwei 2013.8.19
 * version:6.0.8 调整F12通用测试窗口为最大 -- ljd 2013.8.19
 * version:6.0.9 修复表格在docheck做过改变后如果发生条数变小等变更会导致表格数据显示和实际期望不符，出现多出几条数据来 -- jinwei 2013.8.20
 * version:6.1.0 解决宁波老写法grid全选点击第一行无法选中问题 -- ljd 2013.8.23
 * version 6.1.1 增加自动设置表格高度及对表格每页显示条数做了智能调整 -- jinwei 2013.8.23
 * version 6.1.2 修复标签数据权限，增加opItemAttrbute -- gwf 2013.8.26
 * version:6.1.3 修复自定义按钮里提示信息无效及对提示做了调整 -- jinwei 2013.9.3
 * version:6.1.4 修复了grid联动过滤问题 -- gwf 2013.9.3
 * version:6.1.5 修复非表格元素抛出异常后值无法还原问题（还原作废，改为修复select在抛出异常后红色错误提示不显示） -- jinwei 2013.9.4
 * version:6.1.6 通用导盘修复doCommimp,grid设置0和“”变成恒等 -- gwf 2013.9.8
 * version:6.1.7 修改docheck方法，使得当前元素JS端校验未通过时不允许进入后台统一校验事件 -- jinwei 2013.9.10
 * version:6.1.8 修改doOnChange方法在个人工作台里面按回车无法触发事件的问题 -- ljd 2013.9.13
 * version:6.1.9 修复个人工作台下cpquery查询发生循环问题 -- jinwei 2013.9.23
 * version:6.1.10 修复设置cpquery的required无效、焦点设置、自动跳入下一个可输入单元格等等一批问题 -- jinwei 2013.9.25
 * version:6.1.11 增加操作table里元素的style属性功能 -- jinwei 2013.9.26
 * version:6.1.12 修复自动激活表格下一个可编辑格方法bug，自动禁用数据界面的保存按钮 -- jinwei 2013.10.10
 * version:6.1.13 增加打开新页面的js -- gwf 2013.10.14
 * version:6.1.14 增加通过按钮打开操作日志窗口函数 -- jinwei 2013.10.15
 * version:6.1.15 修改reflush方法，使得在最近次事件为save且reflushAfterSave为false时不刷新页面 -- jinwei 2013.10.17
 * version:6.1.16 统一处理logPrint的显示和隐藏 -- jinwei 2013.10.24
 * version:6.1.17 修复宁波那边大市内转入明细表格按回车跳格不对及回车下拉无法选 -- jinwei 2013.10.24
 * version:6.1.18 如果页面存在id为confirmcount的元素（value为0），则每confirm一次自动值加一 -- jinwei 2013.10.29
 * version:6.1.19 增加doGridEnterAddRow方法，当回车时自动模拟触发自定义按钮事件，并处理光标移入下一格失效问题 -- jinwei 2013.10.30
 * version:6.1.20 增加querytag方法、增加编码方法 -- gwf 2013.12.02
 * version:6.2.0  修复opItemVisible方法当页面有同名元素时，无法隐藏表格该名称列的问题 -- jinwei 2014.1.9
 * version:6.2.1  添加setReflushAfterSaveMsg方法 -- ljd 2014.1.13 
 * version:6.2.2  docheck方法中添加属性radow.cm.pageParam.currentDiv  -- ljd 2014.2.20 
 * version:6.2.3  修改autoGridNextCell方法，修正grid点击单选框时报错  -- ljd 2014.2.24
 * version:6.2.4  对grid中查询速度做优化 -- ljd 2014.3.14
 * version:6.2.5  docheck方法中添加属性radow.cm.pageParam.currentValue  -- ljd 2014.4.1
 * version:6.2.6  修改setGridData方法，修复grid统计页数的异常  -- ljd 2014.5.21
 * version:6.2.7  修改setBillPrint2方法，修改刷新页面的reload的函数延时调用  -- cx 2014.6.12
 * version:6.2.8  修改renderDate方法  处理日期格式问题----ljd 2014.10.24
 * version:6.2.9  修改doSuccess方法  处理rowClick事件时的问题----ljd 2014.10.30 
 * version:6.3.0  修改doGridCheck方法  增加radow.cm.pageParam.currentCol=col;----ljd 2014.1.21 
 * version:6.3.1  修复当在doSave里抛出异常时会导致setValid方法报错问题 -- jinwei 2015.3.23
 * version:6.3.2  增加scrollToRow方法 -- ljd 2015.4.3
 * *****************************
 */
odin.ext.namespace('radow.cm');
//全局常量
var reflushAfterSave = true; // 保存之后的自动刷新
var reflushAfterSaveMsg = "";// 保存后的信息
var reflushAfterSavePrint  = "";//保存后打印
//导出参数
var listeners={};
var fileNameSim = "";
var sheetNameSim = "";
var querySQLSim = "";
var headNamesSim = "";
var separatorSim = "";
var childfilenameSim = "";
var commParams = "";//全局公共参数
var parentWin = parent;//父窗口全局变量
var fromCommLink = false; // 是否来自公共超链接
var formData = null; //formData，用来保存之前的界面表单数据信息，用来处理数据界面
/*var cm_eventDataCustomized = [{"aab001":"aab003,aab002,aab001"}];
var cue_eventDataCustomized = "aab003,aab002,aab001";*/
odin.ext.onReady(function(){
	if(typeof page_url_params!='undefined'){
		commParams.currentOpseno = page_url_params["opseno"];
	}
});
/**
 * @msgBoxParams msgBox函数的参数
 * @msgBoxParams.msgArray 信息的数组
 * @msgBoxParams.i 信息数组的开始显示索引
 */
var msgBoxParams = {};
radow.cm = {
	/**
	 * 是否docheck不做遮罩，默认为需要遮罩
	 * @type Boolean
	 */
	is_autoNoMask_docheck:false,
	/**
	 * 最近一次事件信息
	 * @type 
	 */
	cueEvent:{
		eventName:null,
		eventParam:null
	},
	pageParam:{/*触发事件时的当前参数信息*/
		currentColumn:null,
		cueRow:null,
		cueCol:null,
		currentEvent:null,
		currentValue:null,
		clickParams :null
	},
	/**
	 * 表格翻页时的当前页保存触发
	 * @param {} gridId
	 */
	doGridSave:function(gridId){
		var cueEleId = "";
		radow.cm.pageParam.currentColumn = "doSaveBtn";
		radow.cm.pageParam.currentEvent = "doSave";
		radow.cm.pubEventReqBefore("doSave");
		radow.doEvent("doSave",odin.encode(radow.cm.pageParam));
	},
	doSave:function(btn,e){
		radow.disableBtns.push(btn);
		btn.disable();
		if(page_url_params["opseno"]){
			commParams.currentOpseno = page_url_params["opseno"];
		}
		if (!Ext.isIE) {
			for (var n = 0; n < tagNames.length; n++) {
				var inputList = document.getElementsByTagName(tagNames[n]);
				for (var j = 0; j < inputList.length; j++) {
					var item = inputList.item(j);
					item.setAttribute("value", item.value);
				}
			}
		}
		radow.cm.pubEventCommParamsAccess();
		radow.cm.pubEventBefore(btn,e,"save");
		radow.cm.pubEventReqBefore("doSave");
		/*var pre = document.createElement("pre");
		pre.innerHTML = radow.cm.doOpLog();
		document.body.appendChild(pre);*/
		radow.doEvent("doSave",odin.encode(radow.cm.pageParam));
	},
	doClick:function(btn,paramStr,noRequiredValidate){
		radow.disableBtns.push(btn);
		btn.disable();
		if(noRequiredValidate && noRequiredValidate == 'false'){
			var result = odin.checkValue(document.forms.commForm);
			if(!result){
				radow.doBtnsEnable();
				return;
			}
		}
		radow.cm.pubEventCommParamsAccess();
		radow.cm.pageParam.currentColumn = "";
		radow.cm.pageParam.currentEvent = "click";
		radow.cm.pageParam.currentValue=paramStr;
		radow.cm.pubEventReqBefore("doClick");
		radow.doEvent("doClick",odin.encode(radow.cm.pageParam));
	},
	doGridClick:function(btn, clickParams, currentRow){
		radow.cm.pubEventCommParamsAccess();
		if (btn == null) {
			btn = "";
		}
		if (currentRow == null) {
			currentRow = 0;
		}
		if (clickParams != null) {
			radow.cm.pageParam.clickParams = clickParams;
		}
		radow.cm.pageParam.currentEvent = "click";
		radow.cm.pageParam.currentValue = btn;
		radow.cm.pageParam.currentRow = currentRow;
		radow.cm.pubEventReqBefore("doClick");
		radow.doEvent("doClick",odin.encode(radow.cm.pageParam));

	},
	doGridEnterAddRow:function(currentDiv,currentRow,currentCol,dataIndex){
		radow.cm.pubEventCommParamsAccess();
		radow.cm.pageParam.currentDiv = currentDiv;
		radow.cm.pageParam.currentEvent = "click";
		radow.cm.pageParam.currentValue = "gridEnterAddRow";
		radow.cm.pageParam.currentRow = currentRow;
		radow.cm.pageParam.currentCol = currentCol;
		radow.cm.pageParam.currentColumn = dataIndex;
		radow.cm.pubEventReqBefore("doClick");
		radow.doEvent("doClick",odin.encode(radow.cm.pageParam));
	},
	doCheck:function(id){
		var field = odin.ext.get(id);
		radow.cm.pageParam.currentRow=0;		
		radow.cm.pubEventReqBefore("doCheck");
		if(id.indexOf("div_")==0 && arguments.length>1){
			var dataIndex  = arguments[1];
			var g = odin.ext.getCmp(id);
			var gridColumnModel = g.getColumnModel();
			var colIndex = odin.getGridColumn(id,dataIndex).id;
			var s = g.store;
			var count  = s.getCount();
			for(var i=0;i<count;i++){
				radow.cm.doGridCheck(i,colIndex,dataIndex,id,"selectall_"+id+"_"+dataIndex);
			}
			return;
		}
		var cueEleId = id;
		radow.cueElement = document.getElementById(cueEleId);
		//alert(cueEleId);
		radow.cm.pubEventCommParamsAccess();
//		try{
//			var divList =document.getElementsByTagName("div");
//			for (var i = 0; i < divList.length; i++) {
//				var div = divList.item(i);
//				if (getDivItem(div, id) != null&&div.id.indexOf("div") != -1) {
//					radow.cm.pageParam.currentDiv =div.id;
//				}
//			}
//		} catch (e) {
			radow.cm.pageParam.currentDiv ="";
//		}
		var cmp = getCmpByName(cueEleId);
		try{
			radow.cm.pageParam.currentOriginalValue = cmp.startValue;
			cmp.noClearValid = false;
		}catch(exception){
			
		}
		
		radow.cm.pageParam.currentValue=document.getElementById(cueEleId).value;		
		radow.cm.pageParam.currentColumn = cueEleId;
		radow.cm.pageParam.currentEvent = "onchange";
		radow.cm.pubEventReqBefore("doCheck");
		if(field){
			if("true" != field.dom.getAttribute('isquery')){
				var rtn = radow.cm.checkValid(document.forms.commForm);
				if(!rtn) return;
			}
		}
		radow.doEvent("doCheck",odin.encode(radow.cm.pageParam));
	},
	doGridCheck:function(row,col,dataIndex,gridId,selectAllId){
		//var cueEleId = id;
		//radow.cueElement = document.getElementById(cueEleId);
		if(typeof selectAllId !='undefined'){
			radow.cm.pageParam.selectAll = selectAllId;
		}else{
			if(radow.cm.pageParam.selectAll){
				delete radow.cm.pageParam.selectAll;
			}
		}
		radow.cm.pubEventCommParamsAccess();
		radow.cm.pageParam.currentRow=row;
		radow.cm.pageParam.currentCol=col;
		radow.cm.pageParam.field=dataIndex;
		radow.cm.pageParam.currentColumn = dataIndex;
		radow.cm.pageParam.currentDiv = gridId;
		var currentValue=odin.ext.getCmp(gridId).store.getAt(row).get(dataIndex);
		radow.cm.pageParam.currentValue=currentValue;
		radow.cm.pageParam.originalValue = !currentValue;
		radow.cm.pageParam.currentOriginalValue=!currentValue;
		radow.cm.pageParam.currentEvent = "onchange";
		radow.cm.pubEventReqBefore("doCheck");
		radow.doEvent("doCheck",odin.encode(radow.cm.pageParam));	
	}
	,
	doQuery:function(btn,e,noRequiredValidate){
		if(noRequiredValidate && noRequiredValidate == 'false'){
			var result = odin.checkValue(document.forms.commForm);
			if(!result) return;
		}
		radow.cm.pubEventCommParamsAccess();
		radow.cm.pubEventBefore(btn,e,"query");
		radow.cm.pubEventReqBefore("doQuery");
		radow.doEvent("doQuery",odin.encode(radow.cm.pageParam));
	},	/**
	 * 导入前事件
	 * @param {} btn
	 * @param {} e
	 */
	doBeforeImpForm:function(btn,e,fileType,param){
		commParams = {};
		commParams.currentValue = "1";
		if (fileType == null || typeof fileType == 'undefined') {
			fileType = "1";
		}
		if (param) {
			window.fix = true;
		}
		commParams.currentFileType = fileType.toString();
		radow.cm.pubEventCommParamsAccess();
		radow.cm.pubEventBefore(btn,e,"beforeImp");
		radow.cm.pubEventReqBefore("doBeforeCommImpForm");
		radow.doEvent("doBeforeCommImpForm",odin.encode(radow.cm.pageParam));
	},
	/**
	 * 通用导入前事件
	 * @param {} btn
	 * @param {} e
	 */
	doBeforeCommImpForm:function(btn,e,fileType,param){
		commParams = {};
		commParams.currentValue = "1";
		if (fileType == null || typeof fileType == 'undefined') {
			fileType = "1";
		}
		if (param) {
			window.fix = true;
		}
		commParams.currentFileType = fileType.toString();
		radow.cm.pubEventCommParamsAccess();
		radow.cm.pubEventBefore(btn,e,"beforeCommImp");
		radow.cm.pubEventReqBefore("doBeforeCommImpForm");
		radow.doEvent("doBeforeCommImpForm",odin.encode(radow.cm.pageParam));
	},
	/**
	 * 导入后
	 */
	doAfterImpForm:function(){
		radow.cm.pubEventCommParamsAccess();
		radow.cm.pageParam.currentEvent = "afterImp";
		radow.cm.pubEventReqBefore("doAfterImpForm");
		radow.doEvent("doAfterImpForm",odin.encode(radow.cm.pageParam));
	},
	/**
	 * 导入具体写临时表的公共事件
	 */
	doCommImp:function(){
		radow.cm.pubEventCommParamsAccess();
		radow.cm.pageParam.currentEvent = "commImp";
		radow.cm.pubEventReqBefore("doCommImp");
		radow.doEvent("doCommImp",odin.encode(radow.cm.pageParam));
	},
	/**
	 * 公共事件一个前置处理或预处理
	 * @param {} btn
	 * @param {} e
	 * @param {} eventName  公共事件名
	 */
	pubEventBefore:function(btn,e,eventName){
		var cueEleId = btn.getId();
		radow.cm.pageParam.currentColumn = cueEleId;
		radow.cm.pageParam.currentEvent = eventName;
	},
	/**
	 * 公共事件发送请求前的处理，用来保存最近一次事件请求的信息
	 * @param {} eventName
	 */
	pubEventReqBefore:function(eventName){
		radow.cm.cueEvent.eventName = eventName;
		radow.cm.cueEvent.eventParam = radow.cm.pageParam;
		commParams.currentColumn = radow.cm.pageParam.currentColumn;
		commParams.currentEvent =  radow.cm.pageParam.currentEvent;
		commParams.currentRow = radow.cm.pageParam.currentRow;
		commParams.originalValue = radow.cm.pageParam.originalValue;
		radow.cm.pageParam.currentActionDisabled = currentActionDisabled;
	},
	/**
	 * 处理公共commParams，增加其属性到radow.cm.pageParam对象中
	 */
	pubEventCommParamsAccess:function(){
		odin.ext.apply(radow.cm.pageParam,commParams);
	},
	/**
	 * 自动清空pageParam参数
	 */
	clearPageParam:function(){
		radow.cm.pageParam = {};
		/*radow.cm.pageParam.cueCol = null;
		radow.cm.pageParam.cueElement = null;
		radow.cm.pageParam.cueRow = null;
		radow.cm.pageParam.currentEvent = null;
		radow.cm.pageParam.currentValue = null;*/
	},
	/**
	 * 编辑后事件
	 * @param {} e
	 */
	afteredit:function(e){
		var grid = e.grid;
        var record = e.record;
        var field = e.field;
        var originalValue = e.originalValue;
        var value = e.value;
        var row = e.row;
        var column = e.column;
        var id = grid.getId();
        var namePath = radow.findElementNamePathById(id);   
        eval("page_element_tree."+namePath+".cueRowIndex='"+row+"'");
        if(originalValue==null && value=='') return;
        odin.afterEditForEditGrid(e);
        radow.cm.pubEventCommParamsAccess();
        radow.cm.pageParam.currentDiv = id;
		radow.cm.pageParam.currentEvent = "onchange";
		radow.cm.pageParam.currentRow = row;
		radow.cm.pageParam.currentCol = column;
		radow.cm.pageParam.currentColumn = field;
		radow.cm.pageParam.originalValue = originalValue;
		radow.cm.pageParam.currentOriginalValue= originalValue;
		radow.cm.pageParam.currentValue=value;
		radow.cm.pubEventReqBefore("doCheck");
		radow.doEvent("doCheck",odin.encode(radow.cm.pageParam));
		grid.view.refresh(true);
	},
	/**
	 * 表格行单击公共事件
	 */
	doRowDbClick:function(grid,rowIndex,e){
		var id = grid.getId();
        var namePath = radow.findElementNamePathById(id);
		eval("page_element_tree."+namePath+".cueRowIndex='"+rowIndex+"'");
		radow.cm.pubEventCommParamsAccess();
        radow.cm.pageParam.currentDiv = id;
		radow.cm.pageParam.currentEvent = "rowDbClick";
		radow.cm.pageParam.currentRow = rowIndex;
		radow.cm.pubEventReqBefore("rowDbClick");
		radow.doEvent("rowDbClick",odin.encode(radow.cm.pageParam));
	},
	/**
	 * 表格行单击公共事件
	 */
	doRowClick:function(grid,rowIndex,e){
		var id = grid.getId();
        var namePath = radow.findElementNamePathById(id);
		eval("page_element_tree."+namePath+".cueRowIndex='"+rowIndex+"'");
		radow.cm.pubEventCommParamsAccess();
        radow.cm.pageParam.currentDiv = id;
		radow.cm.pageParam.currentEvent = "rowClick";
		radow.cm.pageParam.currentRow = rowIndex;
		radow.cm.pubEventReqBefore("rowClick");
		radow.doEvent("rowClick",odin.encode(radow.cm.pageParam));
	},
	doExp:function(btn){
		radow.cm.pubEventCommParamsAccess();
		radow.cm.pageParam.currentEvent = "exp";
		radow.cm.pubEventReqBefore("doExp");
		radow.doEvent("doExp",odin.encode(radow.cm.pageParam));
	},
	doPrint:function(btn){
		radow.cm.pubEventCommParamsAccess();
		radow.cm.pageParam.currentEvent = "print";
		radow.cm.pubEventReqBefore("doPrint");
		if (reflushAfterSavePrint != null && reflushAfterSavePrint != "") {
			radow.cm.pageParam.currentValue = "aftersave";
		}
		radow.doEvent("doPrint",odin.encode(radow.cm.pageParam));
	},
	doOpenCommImpWin:function(){
		doOpenPupWin(contextPath + "/commform/sys/excel/commImpFileWindow.jsp", "请选择要上传的文件", 500, 160);
	},
	/**
	 * 处理成功相应
	 * @param {} data
	 */
	doSuccess:function(data){
		//alert(odin.encode(data));
		if(radow.cueMessageCode==1){ //radow处理失败
			return;
		}
		reflushAfterSave = true;
		var theCommParams = Ext.util.JSON.decode(data.commParams)[0]; // 由于异步模式，所以在返回参数中取回公共参数值
		commParams = theCommParams;
		if("init,click,query,exp,doAfterImpForm".indexOf(theCommParams.currentEvent)>=0){ //初始化
			setFormData(data);
			opPage(data.opPage);
		}
		if ("onchange,rowDbClick,rowClick".indexOf(theCommParams.currentEvent)>=0) { // onchange事件
//			if (radow.cm.checkValid(document.commForm) != true) { // 做校验判断
//				if (theCommParams.currentColumn != null) {
//					if (commParams.currentEvent == "onchange") {
//						var cmp = getCmpByName(theCommParams.currentColumn);
//						cmp.checkSelectComplete = false;
//						cmp.setValue(cmp.startValue);
//					} 
//				}
//				return;
//			}
			radow.cm.clearInvalid(document.commForm); // 成功后先将界面的错误信息清除掉，主要清除必填项错误提示
			setFormData(data);
			opPage(data.opPage);
		}
		if("onchange,click,rowDbClick".indexOf(theCommParams.currentEvent)>=0){
			if((theCommParams.currentEvent == 'onchange' && document.getElementById("gridDiv_"+commParams.currentDiv)) || (theCommParams.currentValue=='gridEnterAddRow')){
				try {
					window.setTimeout(function(){radow.cm.autoGridNextCell();},100);
				} catch (e) {
				}
			}else{
				if("cpquery,psquery,psidquery,psidnew,cpnew".indexOf(commParams.currentColumn)>=0){
					if(commParams.currentColumn=='cpquery'){
						window.setTimeout(function(){window.focus();odin.autoNextElement(document.getElementById("cpquery_combo"));},10);
					}else{
						window.setTimeout(function(){window.focus();odin.autoNextElement(document.getElementById(commParams.currentColumn));},10);
					}
				}
			}
			showMsgBox(data); // 提示信息
		}
		if ("save,commImp,doCommImp".indexOf(theCommParams.currentEvent)>=0) { // 保存等
			setFormData(data);
			opPage(data.opPage); // 对页面操作，可能对reflushAfterSave进行修改
		}
		if (theCommParams.currentEvent == "save") {//保存后，清除grid的修改标志
			var altmsg = "保存成功！";
			if (reflushAfterSaveMsg != null && reflushAfterSaveMsg != "") {
				odin.info(reflushAfterSaveMsg, function() {
							reflush();
						});
			}else if(reflushAfterSavePrint != null && reflushAfterSavePrint != "") {
				if (reflushAfterSavePrint == "#") { // 不提示直接打印
					radow.cm.doPrint();
				} else {
					odin.confirm(reflushAfterSavePrint, function(btn) {
						if (btn == "ok") { // 确定
							radow.cm.doPrint();
						} else { // 取消
							reflush();
						}
					});
				}
			}else {
				odin.mask(altmsg); // 加上阴影
				window.setTimeout("odin.unmask()", 2000);// 去掉阴影
				window.setTimeout("reflush()", 1000); // 页面刷新
			}
		}
		if (theCommParams.currentEvent == "print"||theCommParams.currentEvent == "click") { //打印
			opPage(data.billPrint);
		}
		if (theCommParams.currentEvent == "commImp") { // 通用导入
			//getIFrameWin("iframe_win_pup").doCloseWin();
		}
	},
	autoGridNextCell:function(){
	    /**
		var g = odin.ext.getCmp(commParams.currentDiv);
		var currentColumn = commParams.currentColumn;
		var currentRow = commParams.currentRow;
		var current_col = radow.cm.pageParam.currentCol;
		var cm = g.getColumnModel();
		var colCount = cm.getColumnCount();
		var nextCol = current_col+1;
		if(current_col==(colCount-1)){
			nextCol = 0;
			if(currentRow == (g.store.getCount()-1)){
				return;
			}else{
				currentRow++
			}
		}
		while(true){
			var c = g.getColumnModel().config[nextCol];
			if(c){
				if(c.editable && true != c.hidden){
					odin.startEditing(commParams.currentDiv,currentRow, nextCol);
					break;
				}else{
					nextCol++;
					if(nextCol>=colCount){
						break;
					}
				}
			}else{
				break;
			}
			
		}**/
		
		var g = odin.ext.getCmp(commParams.currentDiv);
		var selectModel = g.selModel;
		var currentCol = radow.cm.pageParam.currentCol;
        var currentRow = commParams.currentRow;
		
		if(g.moveWay !== undefined && g.moveWay == "cell"){
           newCell = odin.gridWalkRows(g, currentRow + 1, currentCol, 1, selectModel.acceptsNav, selectModel);
        }else{
           newCell = g.walkCells(currentRow, currentCol + 1, 1, selectModel.acceptsNav, selectModel);
        }
        
        if(newCell){
            g.startEditing(newCell[0], newCell[1]);
        }
	},
	/**
	 * 弹出确定消息框，提示是否继续
	 * @param {} msg
	 * @param {} continuFlag
	 */
	confirm:function(msg,continuFlag){
		odin.confirm(msg,function(btn){
			radow.cm.cueEvent.eventParam.confirmRet = btn;
			if(continuFlag == null){
				radow.doEvent(radow.cm.cueEvent.eventName,odin.encode(radow.cm.cueEvent.eventParam));
			}else if(continuFlag == true && btn == 'ok'){
				radow.doEvent(radow.cm.cueEvent.eventName,odin.encode(radow.cm.cueEvent.eventParam));
			}else if(continuFlag == false && btn == 'cancel'){
				radow.doEvent(radow.cm.cueEvent.eventName,odin.encode(radow.cm.cueEvent.eventParam));
			}
			radow.cm.cueEvent.eventParam.confirmRet = "";
		});
	},
	/**
	 * 弹出确定消息框，提示是否继续--可以弹出多个
	 * @param {} msg
	 * @param {} continuFlag
	 */
	confirm__multi:function(msg,continuFlag){
		odin.confirm(msg,function(btn){
			var allmsg=document.getElementById("allmsg").value;
			if(allmsg==""){
				allmsg=btn+"@@"+msg;
			}else{
				allmsg=allmsg+"%%"+btn+"@@"+msg
			}
			document.getElementById("allmsg").value=allmsg;
			radow.cm.cueEvent.eventParam.confirmRet = allmsg;
			if(continuFlag == null){
				radow.doEvent(radow.cm.cueEvent.eventName,odin.encode(radow.cm.cueEvent.eventParam));
			}else if(continuFlag == true && btn == 'ok'){
				radow.doEvent(radow.cm.cueEvent.eventName,odin.encode(radow.cm.cueEvent.eventParam));
			}else if(continuFlag == false && btn == 'cancel'){
				radow.doEvent(radow.cm.cueEvent.eventName,odin.encode(radow.cm.cueEvent.eventParam));
			}
			radow.cm.cueEvent.eventParam.confirmRet = "";
		});
	},
	/**
	 * 设置或清除某列的提示信息
	 */
	setValid:function(column, isValid, errmsg) {
		try{
			if (column == null || column == "") {
				return;
			}
			if (!Ext.getCmp(column) && Ext.getCmp(column + "_combo")) {
				column = column + "_combo";
			}
			if (Ext.getCmp(column)) {
				if (isValid) {
					// Ext.getCmp(column).invalidText = null; 不需要，否则日期校验会有问题
					var cmp = Ext.getCmp(column);
					//if(cmp.errorIcon!=null && cmp.errorIcon.dom.qtip=='该输入项为必输项'){
						Ext.getCmp(column).validator = function(){return true;};
						Ext.getCmp(column).clearInvalid();
					//}
				} else {
					Ext.getCmp(column).invalidText = errmsg;
					Ext.getCmp(column).validator = function(){return false};
					Ext.getCmp(column).clearInvalid();
					Ext.getCmp(column).markInvalid();
				}
			}
		}catch(e){
		}
	},
	/**
	 * 检查是否有错误的数据
	 */
	clearInvalid:function(theForm) {
		var eles = theForm.elements;
		for (i = 0; i < eles.length; i++) {
			var obj = eles[i];
			radow.cm.setValid(obj.name, true);
		}
	},
	
	/**
 	* 检查是否有错误的数据
 	*/
   checkValid:function(theForm) {
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
			if (((obj.value != null && obj.value != "") || (obj.name != null && obj.name != "" && document.getElementById(obj.name + "_combo") && document.getElementById(obj.name + "_combo").value != null && document.getElementById(obj.name + "_combo").value != "" && document.getElementById(obj.name + "_combo").value != "请您选择...")) && (checkFlag == 0 || checkFlag == 2 || commParams.currentEvent == "save" || commParams.currentEvent == "query" || obj.name != commParams.currentColumn) && !(obj.name.indexOf("_combo") >= 0 && obj.value != getCmpByName(obj.name).defaultValue)) {
				var eObj = getCmpByName(obj.name);
				if (eObj) {
					if (!eObj.isValid(false)) {
						odin.error(errtitle + '“' + obj.getAttribute("label") + '”输入项的值不符合要求，请重新输入！', odin.doFocus);
						if (commParams.currentColumn != null) {
							if (commParams.currentEvent == "onchange") {
								var cmp = getCmpByName(commParams.currentColumn);
								cmp.setValue(cmp.startValue);
							} else if (commParams.currentEvent == "afteredit") {
								var record = Ext.getCmp(commParams.currentDiv).getStore().getAt(commParams.currentRow);
								record.set(commParams.currentColumn, commParams.currentOriginalValue == null ? '' : commParams.currentOriginalValue);
							}
							odin.unmask();
							radow.cm.setValid(commParams.currentColumn,true);
						}
						return false;
					}
				}
			}
		}
		return true;
	},
	/**
	 * 数据界面功能打开模块时的初始填充数据操作
	 * @param {} formData
	 */
	setFormData:function(formData){
		if(typeof formData!='undefined' && formData!=null){
			window.setFormData(formData);
		}else if(typeof window.formData!='undefined'){
			window.setFormData(window.formData);
		}
	},
	/**
	 * 获取操作日志所需的界面html信息
	 * 并返回
	 */
	doOpLog:function(){
		var oriSource="";
		if(document.documentElement.outerHTML){
			oriSource = document.documentElement.outerHTML;
		}else{
			oriSource = document.documentElement.innerHTML
		}
    	oriSource=oriSource.replace(/<\s*script[^>]*>((?!<\/?\s*script\s*)(\n|.))*<\/\s*script\s*>/gi, "");
    	return oriSource;
	},
	/**
	 * 初始化的一些动作
	 */
	doInit:function(){
		if("true" == page_disabled){
			odin.ext.getCmp('doSaveBtn').disable();
			opItem('','logPrint','visible',true);
		}else{
			opItem('','logPrint','visible',false);
		}
		if (parent && parent.commParams && parent.commParams.initParams) {
				if (parent.commParams.initParamsUsedBy == null) {
					parent.commParams.initParamsUsedBy = window.name;
				}
				if (parent.commParams.initParamsUsedBy == window.name) {
					page_initParams = parent.commParams.initParams;
					if (parent.commParams.initParamsUsedOnce) { // 一次性的需要清空
						parent.commParams.initParams = null;
						parent.commParams.initParamsUsedBy = null;
						parent.commParams.initParamsUsedOnce = null;
					}
				}
			}
		//设置焦点到第一列上
			var inputList = document.commForm.getElementsByTagName("input");
			for (var i = 0; i < inputList.length; i++) {
				var input = inputList.item(i);
				 //alert(input.name);
				if (setFocus(input.name, true) == true) {
					break;
				}
			}
	},
	/**
	 * 判断当前的cm公共事件是否需要进行必填校验
	 * true：为需要，false：为不需要
	 * 暂时没有实际使用
	 * @author jinw
	 */
	isRequiredValid:function(){
		var isValid = true;
		var btnId = radow.cm.pageParam.currentColumn;
		var event = radow.cm.pageParam.currentEvent;
		var param = radow.cm.pageParam.currentValue;
		if(event=='click'){
			//isValid = window[btnId+"no_valid"];
		}
		return isValid;
	},
	/**
	 * 失败后的消息提示处理，即一些相关的失败处理
	 * @param {} response 相应文本
	 * @param {} msgFuncCont 可能需要动态执行的脚本
	 */
	doFailMsgAndFail:function(response,msgFuncCont){
		var mainMessage=response.mainMessage;	
		mainMessage=mainMessage.replace(/\\'/g,'\'');
		response.mainMessage=mainMessage;
		odin.error(response.mainMessage,function(){
			radow.doFocus(response);
			if(msgFuncCont){
				eval(msgFuncCont);
			}
		});
		setValidRT(response.mainMessage);
	},
	/**
 	* 页面刷新
 	*/
	pagesRoload:function(){
	 	odin.confirm("确定要刷新页面？", function(btn) {
							if (btn == "ok") { // 确定
								location.reload();
							} else { // 取消
								return;
							}
						});
	 },
	 /**
	  * 页面初始化时的数据权限页面初始处理
	  * @param {} cueUserDataInfo
	  */
	 initDataPermission:function(cueUserDataInfo){
	 	var aab301_data = []; //区级数据权限 3
	 	var aaf015_data = []; //街道级数据权限 4
	 	var aaf030_data = []; //社区级数据权限 5
	 	var len = cueUserDataInfo.length;
	 	//console.log(cueUserDataInfo);
	 	window.cueUserDataInfo = {};
	 	for(var i=0;i<len;i++){
	 		var d = cueUserDataInfo[i];
	 		if(d.rate=='3'){
	 			aab301_data.push({"key":d.groupcode,"value":d.name});
	 		}else if(d.rate=='4'){
	 			aaf015_data.push({"key":d.groupcode,"value":d.name});
	 		}else if(d.rate=='5'){
	 			aaf030_data.push({"key":d.groupcode,"value":d.name});
	 		}
	 		window.cueUserDataInfo[d.groupcode] = d;
	 	}
	 	var aab301 = odin.ext.getCmp("aab301_combo");
	 	if(aab301){
	 		odin.reSetSelectData("aab301",aab301_data);
	 		if(aab301_data.length>0) odin.setSelectValueReal("aab301",aab301_data[0].key);
	 		if(aab301_data.length<=1){
	 			aab301.disable();
	 		}else{
	 			aab301.setEditable(false);
	 		}
	 	}
	 	var aaf015 = odin.ext.getCmp("aaf015_combo");
	 	if(aaf015){
	 		odin.reSetSelectData("aaf015",aaf015_data);
	 		if(aaf015_data.length>0) odin.setSelectValueReal("aaf015",aaf015_data[0].key);
	 		if(aaf015_data.length<=1){
	 			aaf015.disable();
	 		}else{
	 			aaf015.setEditable(false);
	 		}
	 	}
	 	var aaf030 = odin.ext.getCmp("aaf030_combo");
	 	if(aaf030){
	 		odin.reSetSelectData("aaf030",aaf030_data);
	 		if(aaf030_data.length>0) odin.setSelectValueReal("aaf030",aaf030_data[0].key);
	 		if(aaf030_data.length<=1){
	 			aaf030.disable();
	 		}else{
	 			aaf030.enable();
	 			aaf030.setEditable(false);
	 		}
	 	}
	 	if(aab301 && aaf015 && aab301_data.length>1){
	 		aab301.on("select",function(combo,newValue,oldValue){ //根据区选择自动过滤有权访问的街道信息
	 			radow.cm.dataFilter("aab301","aaf015")
	 		});
	 	}
	 	if(aaf015 && aaf030 && aaf015_data.length>1){
	 		aaf015.on("select",function(combo,newValue,oldValue){ //根据街道选择自动过滤有权访问的社区信息
	 			radow.cm.dataFilter("aaf015","aaf030")
	 		});
	 	}
	 },
	 /**
	  * 自动联动过滤下拉的街道、社区过滤
	  * @param {} cueSelId 当前选择的下拉框id
	  * @param {} filterSelId 待过滤的下拉框id
	  */
	 dataFilter:function(cueSelId,filterSelId){
	 	var cue_aab301 = document.getElementById(cueSelId).value;
		var cue_aab301_Id = window.cueUserDataInfo[cue_aab301].id;
		var aaf015_fdata = [];
	 	for(var d in window.cueUserDataInfo){
	 		var g = window.cueUserDataInfo[d];
	 		if(g.parent == cue_aab301_Id){
	 			aaf015_fdata.push({"key":g.groupcode,"value":g.name});
	 		}
	 	}
	 	odin.reSetSelectData(filterSelId,aaf015_fdata);
	 	if(aaf015_fdata.length>0){
			odin.setSelectValueReal(filterSelId,aaf015_fdata[0].key);
			if(aaf015_fdata.length<=1){
	 			odin.ext.getCmp(filterSelId+"_combo").disable();
	 		}else{
	 			odin.ext.getCmp(filterSelId+"_combo").enable();
	 			odin.ext.getCmp(filterSelId+"_combo").setEditable(false);
	 		}
	 	}else{
	 		odin.ext.getCmp(filterSelId+"_combo").setValue("");
	 		document.getElementById(filterSelId).value = "";
	 		odin.ext.getCmp(filterSelId+"_combo").disable();
	 	}
	 },
	 doOpenImgUploadWin:function(obj){
	 	var p = obj.getAttribute("p");
	 	if(p=="E"){
	 		var id = obj.id.replace("_img","");
	 		doOpenPupWin(contextPath + "/sys/img/impImgWindow.jsp?id="+id, "请选择要上传的照片", 500, 160);
	 	}
	 },
	 mouseOverImg:function(obj){
	 	var p = obj.getAttribute("p");
	 	if(p=="E" || p==null){
	 		obj.style.cursor="pointer";
	 	}else{
	 		obj.style.cursor="default";
	 	}
	 },
	 /**
	  * 自动设置表格高度，非固定值
	  * @param {} grid 表格ID
	  * @param {} hStr 高度字符串 如“-200,0.3”或“-200”
	  */
	 autoSetGridHeight:function(grid,hStr){
	 	var h = document.body.clientHeight;
	 	var g = odin.ext.getCmp(grid);
	 	var minusWidth = hStr;
	 	var scaleWidth = null;
	 	if(hStr.indexOf(",")){
	 		var t = hStr.split(",");
	 		minusWidth = t[0];
	 		scaleWidth = t[1];
	 	}
	 	var newHeight = h + parseInt(minusWidth,10);
	 	if(scaleWidth!=null){
	 		newHeight = newHeight*parseFloat(scaleWidth,10);
	 	}
	 	g.setHeight(newHeight);
	 	var temp = newHeight - 25;
	 	if(radow.isPageGridDiv(grid)){
	 		temp = temp - 27;
	 	}
	 	if(g.title!=''){
	 		temp = temp - 25;
	 	}
	 	var pageSize = ""+(temp/25);
	 	pageSize = parseInt(pageSize);
	 	if(temp%25>=20){
	 		pageSize += 1;
	 	}
	 	var pageingToolbar = (Ext.getCmp(grid).getBottomToolbar() || Ext.getCmp(grid).getTopToolbar());
	 	if(typeof pageingToolbar!='undefined'){
			pageingToolbar.pageSize = pageSize;
			var s = Ext.getCmp(grid).store;
			s.baseParams.limit = pageSize;
			if(s.lastOptions && s.lastOptions.params){
				s.lastOptions.params.limit = pageSize;
			}
	 	}
	 }
};

/**
 * 显示提示信息
 */
function showMsgBox(data) {
	var json = data.msgBox;
	if (json == null) {
		return;
	}
	var msgArray = Ext.util.JSON.decode(json);
	msgBox(msgArray);
}
/**
 * 数组格式的一条条信息进行提示
 * @param {Object} msgArray 信息数组
 * @param {Object} i 第几个索引开始显示，默认为0
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
		//setFocus(commParams.currentColumn);
		odin.autoNextElement(document.getElementById(commParams.currentColumn));
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
 * 设置父窗口
 */
function setParentWin(parentWin1) {
	this.parentWin = parentWin1; 
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
			/*if (isInit) { // 初始化特殊处理
				eval("comboSetFocusForInit_" + column + "_combo" + "=true"); // 设置下拉框不显示标志
			}*/
			var combo = Ext.getCmp(column + "_combo");
			combo.focus();
			if (combo.list && combo.list.isVisible()) {
				combo.list.hide();
			}
			return true;
		}
		if (typeof(column) == "string") {
			if (Ext.isIE) {
				document.getElementById(column).focus();
			} else {
				if (document.getElementById(column).style.display != 'none' && !document.getElementById(column).disabled) {
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

function  renderDate(dateVal) {
    if(!dateVal||dateVal==""){
    	//dateVal = new Date();
    	dateVal = null;
    }else if(typeof dateVal == 'string'){
    	dateVal = dateVal.substr(0,10);
    	if(dateVal.length==8){
    		dateVal = Date.parseDate(dateVal,'Ymd');
    	}else if(dateVal.length==6){
    		dateVal = Date.parseDate(dateVal,'Ym');
    	}else if(dateVal.length==10){
    		dateVal = Date.parseDate(dateVal,'Y-m-d');
    	}else if(dateVal.length==7){
    		dateVal = Date.parseDate(dateVal,'Y-m');
    	}
     	
    }else if(typeof dateVal == 'object'){
    	if (Ext.util.JSON.encode(dateVal).indexOf("{") >= 0 && Ext.util.JSON.encode(dateVal).indexOf("}") >= 0) {
			return odin.Ajax.formatDate(dateVal);
		}
    }
	return Ext.util.Format.date(dateVal,'Y-m-d');
}

function  renderDateYm(dateVal) {
    if(!dateVal||dateVal==""){
    	//dateVal = new Date();
    	dateVal = null;
    }else if(typeof dateVal == 'string'){
    	dateVal = dateVal.substr(0,10);
     	dateVal = Date.parseDate(dateVal,'Ym');
    }else if(typeof dateVal == 'object'){
    	//return odin.Ajax.formatDate(dateVal);
    	var year=String(dateVal.year+1900);
	    var month=String(dateVal.month+1);
	    month=odin.fillLeft(month,"0",2);
	    var theDate=year+""+month;
	    return theDate;
    }
	return Ext.util.Format.date(dateVal,'Ym');
}

function renderLongTime(value, params, record, rowIndex, colIndex, ds) {
    if (value == null || value == "") {
        return "";
    }
    if (typeof(value) == "string") {
        value = value.replace("T", " ");
        if (value.indexOf("-") >= 0) {
            if(value.length>10){
                value = value.substr(0, 19);
                value = Date.parseDate(value, 'Y-m-d H:i:s');
            }else{
                value = Date.parseDate(value, 'Y-m-d');
            }
        } else {
            if(value.length>8){
                value = value.substr(0, 14);
                value = Date.parseDate(value, 'YmdHis');
            }else{
                value = Date.parseDate(value, 'Ymd');
            }
        }
    } else if (typeof(value) == 'object') {
        if (Ext.util.JSON.encode(value).indexOf("{") >= 0 && Ext.util.JSON.encode(value).indexOf("}") >= 0) {
            value = odin.Ajax.formatDateTime(value);
            value = Date.parseDate(value, 'Y-m-d H:i:s');
        }
    }
    
    if(typeof value == 'object' && value.format){
        return value.format('Y-m-d');
    }else{
        return value;
    }
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
			if(value.length>10){
				value = value.substr(0, 19);
				value = Date.parseDate(value, 'Y-m-d H:i:s');
			}else{
				value = Date.parseDate(value, 'Y-m-d');
			}
		} else {
			if(value.length>8){
				value = value.substr(0, 14);
				value = Date.parseDate(value, 'YmdHis');
			}else{
				value = Date.parseDate(value, 'Ymd');
			}
		}
	} else if (typeof(value) == 'object') {
		if (Ext.util.JSON.encode(value).indexOf("{") >= 0 && Ext.util.JSON.encode(value).indexOf("}") >= 0) {
			value = odin.Ajax.formatDateTime(value);
			value = Date.parseDate(value, 'Y-m-d H:i:s');
		}
	}
	
//	try{
//		var gridName = ds.baseParams.cueGridId;
//		var colName = Ext.getCmp(gridName).getColumnModel().getDataIndex(colIndex);
//		if (record != null && colName != null) {
//			var type = getGridFieldType(gridName, colName);
//			if (type == 'date') {
//				record.set(colName,value);
//			}
//		}
//	}catch(e){}
	if(typeof value == 'object' && value.format){
		return value.format('Y-m-d');
	}else{
		return value;
	}
}


var tagNames = new Array("input", "textarea"); // 要取数据的tagName
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
 * 根据response传回来的数据设值到Form上去
 */

function setFormData(resdata) {
    if (resdata == null)
        return;

    var json = resdata;
    var eItem = null, eId = null, eChildItem = null, tmpCmp = null;
    var eList = Ext.DomQuery.select("div[@id^=gridDiv_],div[@id^=div_]", document.commForm);
    var eChildList = null;
    var tempJson = null;
    var eGridList = [];
    var girdPefix = "gridDiv_";

    for (var i = 0; i < eList.length; i++) {
        eItem = eList[i];
        etagtmp = eItem.tagName.toLowerCase();
        eId = eItem.id;

        if (etagtmp == 'div') {
            if (eId.indexOf("div_") == 0) {
                tempJson = json[eId];
                eChildList = Ext.DomQuery.select("input[@id],textarea[@id]", eItem);

                if (tempJson == null || tempJson == "" || tempJson == "[]") {
                    for (var n = 0; n < eChildList.length; n++) {
                        eChildList[n].value = "";
                    }
                    continue;
                }

                tempJson = tempJson.substr(1, tempJson.length - 2);
                tempJson = Ext.util.JSON.decode(tempJson);

                for (var n = 0; n < eChildList.length; n++) {
                    eChildItem = eChildList[n];

                    if (eChildItem.name && eChildItem.name.indexOf("-") == -1 && eChildItem.name.indexOf("_combo") == -1) {

                        var inputName = eChildItem.name;
                        var value = tempJson[inputName];

                        if ( typeof value == "undefined") {// 未定义
                            continue;
                        } else if (value == null) {
                            value = "";
                        }

                        if (value.toString() != eChildItem.value.toString()) {

                            tmpCmp = Ext.getCmp(inputName);

                            if (!tmpCmp && Ext.getCmp(inputName + "_combo")) {// 下拉框
                                odin.setSelectValueReal(inputName, value);
                            } else if (tmpCmp && toType(tmpCmp.getXType()) == "date") {// 日期格式
                                var f = Ext.getCmp(inputName).format;
                                if (tmpCmp.format == "Y-m-d H:i:s") {// 到秒
                                    eChildItem.value = renderLongTime(value);
                                } else if (tmpCmp.format == "Ym") {
                                    eChildItem.value = renderDateYm(value);
                                } else {
                                    eChildItem.value = renderDate(value);
                                }
                            } else if (eChildItem.type == "checkbox") {// 打勾
                                if (((value == "true" || value == "1") ? true : false) != eChildItem.checked) {
                                    eChildItem.checked = (value == "true" || value == "1") ? true : false;
                                }
                            } else if (tmpCmp) {// 标签的处理
                                if ( typeof (value) == "object") {// json格式的特殊处理
                                    value = Ext.util.JSON.encode(value);
                                }

                                tmpCmp.setValue(value);
                                tmpCmp.beforeBlur();
                            } else {
                                var imgObj = odin.ext.get(inputName + "_img");
                                if (imgObj && imgObj.dom.tagName == 'IMG') {
                                    imgObj.dom.src = contextPath + value;
                                    eChildItem.value = "";
                                } else {
                                    eChildItem.value = value;
                                }
                            }
                        }
                    }
                }

            } else if(eId.indexOf("gridDiv_") == 0) {
               eGridList.push(eItem);
            }
        }       

    }
    
    //设grid格式的数据    
    for (var i = 0; i < eGridList.length; i++) {
        eItem = eGridList[i];               
        eId = eItem.id.substr(girdPefix.length);
        tmpCmp = Ext.getCmp(eId);
        
        if(tmpCmp){
            if (odin.getGridJsonData(eId) == json[eId]) {
                continue;
            }
            
          eChildItem = eItem.ownerDocument.getElementById(eId+"Data");
          eChildItem.value = json[eId];
          
          if (eChildItem.value == "[{}]" || eChildItem.value == "[]" || eChildItem.value == "" || eChildItem.value == null) {
                eChildItem.value = "";
               tmpCmp.store.removeAll();
            } else {
                setGridData(eId);
            }
        }
    }

}

function setFormData_bak(resdata) {
	var json = resdata;
	if(resdata==null) return;
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
								odin.setSelectValueReal(inputName, value);
							} else if (getFieldType(inputName) == "date") { // 日期格式
								var f = Ext.getCmp(inputName).format;
								if (Ext.getCmp(inputName).format == "Y-m-d H:i:s") { // 到秒
									inputItem.value = renderDateTime(value);
								}else if(Ext.getCmp(inputName).format == "Ym") {
									inputItem.value = renderDateYm(value);
								}else {
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
								var imgObj = odin.ext.get(inputName+"_img");
								if(imgObj && imgObj.dom.tagName=='IMG'){
									imgObj.dom.src = contextPath+value;
									inputItem.value = "";
								}else{
									inputItem.value = value;
								}
							}
						}
					}
				}
			}
		}
	}
	//设grid格式的数据
	var elList = document.commForm.getElementsByTagName("input");
	for (var i = 0; i < elList.length; i++) {
		if (elList.item(i).name && elList.item(i).name.indexOf("-") == -1) {
			if (elList.item(i).name.indexOf("Data") > 0 && document.getElementById("gridDiv_" + elList.item(i).name.substr(0, elList.item(i).name.length - 4)) != null) {
				var grid = elList.item(i).name.substr(0, elList.item(i).name.length - 4);
				//if (eval("elList.item(i).value == json." + grid)) {
				if(odin.getGridJsonData(grid) == json.grid){
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
 * 设置表格值
 * @param {} gridId
 */
function setGridData(gridId) {
	inputName = gridId + "Data";
	
	/**
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
								//if (getGridColumn(gridId, dataIndex[k]).editor.field.format == 'Y-m-d H:i:s') {
								//value = value.replace("T"," ");2013415 该行genghh注释掉
								if (odin.getGridColumn(gridId, dataIndex[k]).editor.format == 'Y-m-d H:i:s') {
									value = renderDateTime(value);
									//value = Date.parseDate(value, 'Y-m-d H:i:s');
								} else {
									value = renderDate(value);
									//value = Date.parseDate(value, 'Y-m-d');
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
			*/
			
	var domJson = Ext.getDom(inputName).value;			
			
	if(domJson != null && domJson!=""){
	    var jsonStr = Ext.util.JSON.decode(domJson);
		
		if(jsonStr != null && jsonStr.length > 0){
		    
		    var grid = Ext.getCmp(gridId);            
            var store = grid.store;
            var gridColumnModel = grid.getColumnModel(); 
            
            var needChangeCol = [];
            var tmpColModel = gridColumnModel.getColumnsBy(function(c, i){
                var xtype = null;
                
                if(c.dataIndex == null || c.dataIndex == ""){
                    return false;
                }
                
                if(c && c.editor) {
                    xtype = toType(c.editor.getXType());
                    
                    if(xtype == "date"){
                        c.tmpXType = true;
                        return true;
                    }                
                }  
                
                return true;                 
            });
            
           //数据转换
           var value = null;           
           if(tmpColModel != null && tmpColModel.length > 0){
              for (var j = 0; j < jsonStr.length; j++) {                  
                  
                  for (var k = 0; k < tmpColModel.length; k++) {
                      value = jsonStr[j][tmpColModel[k].dataIndex];
                      
                      if ( value !== undefined && value != null ) {
                          if (tmpColModel[k].tmpXType) {
                             
                                if (tmpColModel[k].editor.format == 'Y-m-d H:i:s') {
                                    value = renderLongTime(value);                                    
                                } else {
                                    value = renderDate(value);                                    
                                }
                                
                                jsonStr[j][tmpColModel[k].dataIndex] = value;
                            }
                      }else{// 空值或未指定此列数据的处理                           
                          jsonStr[j][tmpColModel[k].dataIndex] = "";                                                    
                      }                   
                  } 
             }
		  }
		  
		  var readRecords = {};
		  //store.reader;
		  readRecords[store.reader.meta.root] = jsonStr;		
		  readRecords[store.reader.meta.totalProperty] = store.getTotalCount() ;      
            		
		  store.removeAll();			
		  store.loadData(readRecords, false);
          //grid.view.refresh();  
			/**
			// 修改数据
			var addRecord = [];
			for (var j = 0; j < jsonStr.length; j++) {
				if (store.getCount() > j) { // 存在此行则修改此行
					for (var k = 0; k < dataIndex.length; k++) {
						if (dataIndex[k]) {
							var value;
							eval("value = jsonStr[" + j + "]." + dataIndex[k] + ";");
							var record = store.getAt(j);
							oldValue = record.get(dataIndex[k]);
							if (value == null) {
								value = "";
							}
							if (oldValue == null) {
								oldValue = "";
							}
							if (value !== oldValue) {
								record.set(dataIndex[k], value);
							}
						}
					}
				} else { // 不存在此行则增加行
					addRecord.push(new Ext.data.Record(jsonStr[j]));
				}				
			}
			var storeCount = store.getCount();
			if(j<storeCount){
				for(;j<storeCount;storeCount--){
					store.removeAt(storeCount-1);
				}
			}
			if(addRecord.length>0){
				store.add(addRecord);
			}
			grid.view.refresh();
			 */         
            
		} else {
			Ext.getCmp(gridId).store.removeAll();
		}
	}  
}
/**
 * 取得grid的列的数据类型
 */
function getGridFieldType(gridName, fieldName) {
	var column = odin.getGridColumn(gridName, fieldName);
	if (column && column.editor) {
		//var xtype = column.editor.field.getXType();
		var xtype = column.editor.getXType();
	}
	return toType(xtype);
}
/**
 * 对页面的操作
 */
function opPage(jsonDataArrayStr) {
	var json = jsonDataArrayStr;
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
/**
 * html格式的onchanged事件
 * 单位、人员查询标签触发
 */
function doOnChange(item,e) {
	var  event= window.event||arguments[0];
	var ele = event.srcElement||event.target;
	var name = item.name;
	if (!name) {
		name = item.getId();
		item = document.getElementById(name);
	}
	if(name=='cpquery_combo' && odin.isWorkpf){
		var cp = odin.ext.getCmp(name);
		//alert(cp.preValue+"||"+cp.lastQuery+"||"+cp.value);
		if(typeof cp.preValue != 'undefined'){
			if(cp.preValue == cp.value && cp.lastQuery==''){
				return;
			}
		}
	}
	if(name.indexOf('cpquery')==0 && name.indexOf('_combo')>0){
		name=name.substring(0,name.indexOf('_combo'));
	}
	var value = item.value;
	if(value==""){
		try{
			if (name.indexOf("psquery") == 0) {// 
				document.getElementById("aac001"+name.substring(7)).value="";
			}
			if (name.indexOf("cpquery") == 0) {// 
				document.getElementById("aaz001"+name.substring(7)).value="";
			}
			if (name.indexOf("psidquery") == 0) {// 
				document.getElementById("aaz030"+name.substring(9)).value="";
			}
			if (name.indexOf("psidnew"+name.substring(7)) == 0) {// 
				document.getElementById("aaz030").value="";
			}
			if (name.indexOf("cpnew"+name.substring(5)) == 0) {// 
				document.getElementById("raz001").value="";
			}
		}catch(exception){			
		}		
		return;
	} 
	commParams = {};
	commParams.currentDiv = getParentDiv(name);
	commParams.currentEvent = "onchange";
	commParams.currentColumn = name;
	commParams.currentValue = value;
	commParams.isQuery = (item.getAttribute("isQuery") == "true" ? "true" : "false");
	commParams.isCpflag = (item.getAttribute("isCpflag") == "false" ? "false" : "true");
	commParams.aac031Filter = (item.getAttribute("aac031Filter") == "" ? "-1":item.getAttribute("aac031Filter"));
	commParams.checkRate = (item.getAttribute("checkRate") == null ? "-1" : item.getAttribute("checkRate"));
	commParams.checkDraft = (item.getAttribute("checkDraft") == "true");
	if(commParams.isQuery){
//		isQuerying = true; // 正在查询，其他程序做控制时使用
		var searchText = commParams.currentValue;
		var uptype = (MDParam == null || MDParam.uptype == "" ? "00" : MDParam.uptype);
		commParams.uptype=uptype;
		var checkRate = commParams.checkRate;
		var iscpflag = commParams.isCpflag;
		var aac031filter = commParams.aac031Filter;
		var theInitParams = searchText + "," + uptype + "," + checkRate + "," + iscpflag + "," + aac031filter;
		if (name.indexOf("cpquery") != 0) {
			document.getElementById(commParams.currentDiv).focus();
		}
		if (name.indexOf("psquery") == 0) {// 以psquery开头的人员查询
//				odin.mask("正在查询..."); // 加上阴影
//				doOpenPupWin(contextPath+"/pages/commAction.do?method=PSQuery", "人员查询 -- 选择好后双击或回车进行确定 -- 快捷键：← ↑ ↓ → Enter Esc", 0.9, (Ext.isIE ? 370 : 370), commParams.currentValue + "," + (MDParam == null || MDParam.uptype == "" ? "00" : MDParam.uptype) + "," + commParams.checkRate+","+commParams.isCpflag+","+commParams.aac031Filter);
//				doHiddenPupWin();// 先隐藏
//				onQueryPupWindowCloseClick();// 关闭按钮的事件		
//				odin.autoNextElement(ele);
//				return;
				theMethod = "PSQuery";
				theTitle = "人员身份新增 -- 选择好后双击或回车进行确定,按关闭或Esc进行另外身份新增 -- 快捷键：← ↑ ↓ → Enter Esc";
				theWidth = 0.9;
				theHeight =370;
		} else if (name.indexOf("cpquery") == 0) {// 以cpquery开头的单位查询
			var str=document.getElementById(name+'_combo').value;
			if(str!=null && str!=''){			
				if(!odin.isWorkpf){
					document.getElementById(commParams.currentDiv).focus();
				}
//				odin.mask("正在查询..."); // 加上阴影
//				doOpenPupWin(contextPath+"/pages/commAction.do?method=CPQuery", "单位查询 -- 选择好后双击或回车进行确定 -- 快捷键：← ↑ ↓ → Enter Esc", 0.9, (Ext.isIE ? 370 : 370), commParams.currentValue + "," + (MDParam == null || MDParam.uptype == "" ? "00" : MDParam.uptype) + "," + commParams.checkRate);
//				doHiddenPupWin();// 先隐藏
//				onQueryPupWindowCloseClick();// 关闭按钮的事件
//				if(!odin.isWorkpf){
//					odin.autoNextElement(ele);
//				}			
//				return;
				theMethod = "CPQuery";
				theTitle = "单位查询 -- 选择好后双击或回车进行确定 -- 快捷键：← ↑ ↓ → Enter Esc";
				theWidth = 0.9;
				theHeight =370;
			}	
		}else if (name.indexOf("psidquery") == 0) {// 以cpquery开头的单位查询
//			odin.mask("正在查询..."); // 加上阴影
//			doOpenPupWin(contextPath+"/pages/commAction.do?method=PSIDQuery", "人员身份查询 -- 选择好后双击或回车进行确定 -- 快捷键：← ↑ ↓ → Enter Esc", 0.9, (Ext.isIE ? 370 : 370), commParams.currentValue + "," + (MDParam == null || MDParam.uptype == "" ? "00" : MDParam.uptype) + "," + commParams.checkRate);
//			doHiddenPupWin();// 先隐藏
//			onQueryPupWindowCloseClick();// 关闭按钮的事件
//			odin.autoNextElement(ele);
//			return;
			theMethod = "PSIDQuery";
			theTitle = "人员身份查询 -- 选择好后双击或回车进行确定 -- 快捷键：← ↑ ↓ → Enter Esc";
			theWidth = 0.9;
			theHeight =370;
			
		} else if (name.indexOf("psidnew") == 0) {// 以psidnew开头的单位查询
//			odin.mask("正在查询..."); // 加上阴影
//			doOpenPupWin(contextPath+"/pages/commAction.do?method=PSIDNew", "人员身份新增 -- 选择好后双击或回车进行确定 -- 快捷键：← ↑ ↓ → Enter Esc", 0.9, (Ext.isIE ? 370 : 370), commParams.currentValue + "," + (MDParam == null || MDParam.uptype == "" ? "00" : MDParam.uptype) + "," + commParams.checkRate);
//			doHiddenPupWin();// 先隐藏
//			onQueryPupWindowCloseClick();// 关闭按钮的事件
//			odin.autoNextElement(ele);
//			return;
			theMethod = "PSIDNew";
			theTitle = "人员身份新增 -- 选择好后双击或回车进行确定,按关闭或Esc进行另外身份新增 -- 快捷键：← ↑ ↓ → Enter Esc";
			theWidth = 0.9;
			theHeight =370;
		} else if (name.indexOf("cpnew") == 0) {// 以psidnew开头的单位查询
//			odin.mask("正在查询..."); // 加上阴影
//			doOpenPupWin(contextPath+"/pages/commAction.do?method=CPNew", "组织机构新增 -- 选择好后双击或回车进行确定 -- 快捷键：← ↑ ↓ → Enter Esc", 0.9, (Ext.isIE ? 370 : 370), commParams.currentValue + "," + (MDParam == null || MDParam.uptype == "" ? "00" : MDParam.uptype) + "," + commParams.checkRate);
//			doHiddenPupWin();// 先隐藏
//			onQueryPupWindowCloseClick();// 关闭按钮的事件
//			odin.autoNextElement(ele);
//			return;
			theMethod = "CPNew";
			theTitle = "单位新增 -- 选择好后双击或回车进行确定,按关闭或Esc进行另外身份新增 -- 快捷键：← ↑ ↓ → Enter Esc";
			theWidth = 0.9;
			theHeight =370;
		}
		var queryParams = {};
		queryParams.initParams =theInitParams;
		queryParams.bs = theMethod;
		var data = tagQuery(queryParams);
		if (data.totalCount == -1) { // 出错
			doQuerySelect(data.data[0], false);
			odin.error(data.data[0].retErrorMsg, odin.doFocus);
			return;
		}else if (data.totalCount == 0) {// 没有数据
			var xt = "整个系统内";
			if (uptype == "00") {
				xt = "社会保险系统内";
			} else if (uptype == "09") {
				xt = "被征地养老保障";
			} else if (uptype == "10") {
				xt = "农村养老保障系统内";
			} else if (uptype == "11") {
				xt = "城镇老年居民养老保障";
			} else if (uptype == "20") {
				xt = "新型农村养老保障";
			} else if (uptype == "21") {
				xt = "城乡居民养老保险系统内";
			} else if (uptype == "91") {
				xt = "社区系统内";
			}
			var errmsg = xt + "没有符合条件的数据！\n请检查输入的字符信息，修正后再重新查询。";
			if (theMethod == "PSQuery") {
				errmsg = xt + "没有要查找的人员！\n请检查输入的字符信息，修正后再重新查询。";
			} else if (theMethod == "CPQuery") {
				errmsg = xt + "没有要查找的单位！\n请检查输入的字符信息，修正后再重新查询。";
			} else if (theMethod == "PSIDQuery") {
				errmsg = xt + "没有要查找的人员身份！\n请检查输入的字符信息，修正后再重新查询。";
			} else if (theMethod == "PSIDNew") { // psidnew查不到则进行后台触发
				doQuerySelect(data.data[0], true);
				return;
			} else if (theMethod == "CPNew") {
				doQuerySelect(data.data[0], true);
				return;
			}
			doQuerySelect(data.data[0]);
			setValid(commParams.currentColumn, false, errmsg);
			odin.error(errmsg, odin.doFocus);
			return;
		} else if (data.totalCount == 1) { // 查询到0条或1条
			setValid(commParams.currentColumn, true);
			var jsonObj = data.data[0];
			doQuerySelect(jsonObj, true);
			return;
		} else {
			odin.mask("正在查询..."); // 加上阴影
			theUrl = "/pages/commAction.do?method=" + theMethod;
			doOpenPupWin(theUrl, theTitle, theWidth, theHeight, cjkEncode(theInitParams));
			doHiddenPupWin();// 先隐藏
			onQueryPupWindowCloseClick();// 关闭按钮的事件
			return;
		}
	}
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
 * 查询事件的关闭窗口操作
 */
function closeQueryPupWindow() {
	radow.util.autoFillElementByLike(getIFrameWin("iframe_win_pup").getGridNullJson(getIFrameWin("iframe_win_pup").isQueryDiv),false);
	var triggerOnchange = false;
	if (document.getElementById("iframe_win_pup").src.indexOf("PSIDNew") != -1) {// 人员身份新增特殊处理
		triggerOnchange = true;
	}
	doQuerySelect(getIFrameWin("iframe_win_pup").getGridNullJson(getIFrameWin("iframe_win_pup").isQueryDiv), triggerOnchange);

}
/**
 * 兼容IE、Firefox的iframe窗口获取函数
 */
function getIFrameWin(id) {
	return document.getElementById(id).contentWindow || document.frames[id];
}
/**
 * query标签填充父页面
 * @param data
 * @param queryname
 */
function fillParentData(data,queryname){
	parent.radow.util.autoFillElementByLike(data,false,parent.commParams.currentDiv);
	var query=parent.document.getElementById(queryname);
	var queryn;
	queryn = queryname.substr(0,queryname.indexOf ('_'))==""?queryname:queryname.substr(0,queryname.indexOf ('_'));
	switch (queryn) {
	case 'cpquery':
		query.value = data.aab001;
		if (!fromCommLink) {
			setCpqueryBuffer(data.aab001);
		}
		break;
	case 'psquery':
		query.value = data.aae135;
		if (!fromCommLink) {
			setPsqueryBuffer(data.eac001);
		}		
		break;
	case 'psidquery':
		query.value = data.aae135;
		if (!fromCommLink) {
			setPsidqueryBuffer(data.aae135);
		}
		break;
	case 'psidnew':
		query.value = data.aae135;
		break;	
	}
	parent.radow.cm.doCheck(queryname);
	var windowId = "win_pup";
	var pupWindow = parent.Ext.getCmp(windowId);
	pupWindow.hide();
}
/**
 * 取得psquery的缓存值
 */
function getPsqueryBuffer() {
	if (parent != this) {
		return parent.parent.psqueryBuffer;
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
	if (parent != this) {
		parent.parent.psqueryBuffer=psquery;
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
	if (parent != this) {
		return parent.parent.cpqueryBuffer;
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
	if (parent != this ) {
		parent.parent.cpqueryBuffer=cpquery;
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
	if (parent != this) {
		return parent.parent.psidqueryBuffer;
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
	if (parent != this) {
		parent.parent.psidqueryBuffer=psidquery;
	} else if (odin.isWorkpf) {
		qtobj.setCache("psidqueryBuffer", psidquery);
	} else {
		psidqueryBuffer = psidquery;
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
 * 查询标签报错
 * @param msg 错误信息
 */
function doQueryException(msg){
	parent.odin.unmask();
	parent.odin.info(msg);
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
 * 取得本input项目的父div
 */
function getParentDiv(inputName) {
	var divList = document.getElementsByTagName("div");
	for (var i = 0; i < divList.length; i++) {
		var div = divList.item(i);
		if(div.id != null && div.id.indexOf("div_")<0){
			continue;
		}
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
 * 触发指定列的onchange事件
 * @param {} divId div名称
 * @param {} itemId 列名称
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
		var obj_combo=getDivItem(divId, itemId+"_combo")
		if (obj.onchange) {
			obj.onchange();
		} else if(obj_combo.onchange){
			obj_combo.onchange();
		}else {
			obj.fireEvent("onchange");
		}
	}
}
//////
var maskint = null;
odin.mask = function(msg) {
	Ext.get(document.body).mask(msg, odin.msgCls);
	maskInterval();
	if (maskint != null) {
		clearInterval(maskint);
	}
	maskint = setInterval(maskInterval, 100);
}
function maskInterval() {
	try {
		if (document.hasFocus() && msgint == null && maskint != null && Ext.get(document.body).isMasked()) {
			if (document.activeElement) {
				document.activeElement.blur();
			}
		}
	} catch (e) {
	}
}
odin.unmask = function() {
	Ext.get(document.body).unmask();
	clearInterval(maskint);
	maskint = null;
}
/**
 * 单据打印2
 */
function setBillPrint2(reportlet, params, setup, printmode) {
	var url = contextPath + "/common/billPrint2Action.do?reportlet=" + reportlet + "&params=" + (params.replace(/%/g, "％")).replace(/\+/g, "%2B") + "&setup=" + setup + "&printmode=" + printmode;
	if(odin.isWorkpf){
		if(qtobj){
			qtobj.openNewWindow(url,"打印状态",280,160,"bottomright");
			//window.showModalDialog(url,"打印状态",280,160);
		}
	}else{
		var windowId = "win_billprint";
		var pupWindow = top.frames[1].Ext.getCmp(windowId);
		pupWindow.setTitle("打印状态"); // 标题
		pupWindow.setSize(280, 160); // 宽度 高度
		top.frames[1].showWindowWithSrc(windowId, url, null);
		var x = top.frames[1].document.body.clientWidth - 280 - 10;
		var y = top.frames[1].document.body.clientHeight - 160 - 10;
		pupWindow.setPosition(x, y);
	}
	if (reflushAfterSavePrint != null && reflushAfterSavePrint != "") { //保存后的打印，打印发送后就刷新
		reloadCurrWindow.defer(1000, window);
	}
}

reloadCurrWindow = function(){
    window.location.reload();
};
/**
 * 将导入后的事件指定到radow的导入后事件函数中
 * @type 
 */
window.doAfterImp = function(){
	var value = commParams.currentValue;
	var event = commParams.currentEvent;
	commParams = {};
	if (event == "beforeCommImp") {
		commParams.currentEvent = "doCommImp";
		// 查询进度条
		window.setTimeout('updateProgressbar()', 1000);
	} else {
		commParams.currentEvent = "doAfterImpForm";//afterImp  
	}
	radow.cm.pubEventCommParamsAccess();
	radow.cm.pubEventReqBefore(commParams.currentEvent);
	radow.doEvent(radow.cm.cueEvent.eventName,odin.encode(radow.cm.cueEvent.eventParam));
};
/**
 * 更新进度
 */
function updateProgressbar(){
	//
}
/**
 * 打开顶层的弹出窗口
 */
function doOpenPupWinOnTop(src, title, width, height, initParams, parentWin) {
	try{
		if (parentWin == null) {
			parentWin = this;
		}
		if (parent != this && parent.doOpenPupWinOnTop) {
			parent.doOpenPupWinOnTop(src, title, width, height, initParams, parentWin);
		} else {
			doOpenPupWin(src, title, width, height, initParams, parentWin);
		}
	}catch(e){
		doOpenPupWin(src, title, width, height, initParams, parentWin);
	}	
}
/**
 * 通过按钮打开操作日志窗口
 */
function openOpLogWin(){
	if (MDParam == null) {
		return;
	}
	var src = contextPath;
	src += "/pages/commAction.do?method=OriRollback";
	// alert(src);
	var initParams = MDParam.functionid;
	if (MDParam.zyxx != "" && MDParam.zyxx != "{}") {// 增加一个管理员
		doOpenPupWinOnTop(src, "业务日志--" + MDParam.title, 0.9, 0.8, initParams);
	} else {
		odin.info("【<b>" + MDParam.title + "</b>】目前配置为非业务经办模块，无业务日志！");
	}
}

radow.cm.onKeyDown = function(e){
	var event = e || window.event;// 获取event对象
	var obj = event.target || event.srcElement;// 获取事件源
	var keyCode = event.keyCode || event.which || event.charCode;
	var altKey = event.altKey;
	var ctrlKey = event.ctrlKey;
	// 业务回退，F9按钮的操作
	if (keyCode == 120) {
		if (MDParam == null) {
			return;
		}
		var src = contextPath;
		src += "/pages/commAction.do?method=OriRollback";
		// alert(src);
		var initParams = MDParam.functionid;
		if (MDParam.zyxx != "" && MDParam.zyxx != "{}") {// 增加一个管理员
			doOpenPupWinOnTop(src, "业务日志--" + MDParam.title, 0.9, 0.8, initParams);
		} else {
			odin.info("【<b>" + MDParam.title + "</b>】目前配置为非业务经办模块，无业务日志！");
		}
		if (Ext.isIE) {
			event.keyCode = 0;
			event.returnValue = false;
		} else {
			event.preventDefault();
			event.stopPropagation();
		}
		return;
	}
	// 测试场景，F12按钮的操作
	if (keyCode == 123) {
		if (MDParam == null) {
			return;
		}
		var src = contextPath;
		src += "/pages/commAction.do?method=sys.autoverify.autoordeal.CommScen&functionid=" + MDParam.functionid;
		var initParams = MDParam.functionid;
		// alert(Ext.util.JSON.encode(MDParam));
		var uptype = MDParam.uptype;
		
		var allinputs='';
			var divList = document.commForm.getElementsByTagName("div");

			for (var i = 0; i < divList.length; i++) {
				if (Ext.util.JSON.encode(divList.item(i).id).indexOf("div") > 0) {
					var inputList = document.commForm.getElementsByTagName("input");
			
					for (var j = 0; j < inputList.length; j++) {
						var input = inputList.item(j);
						 if(allinputs==''){
						 	allinputs=divList.item(i).id+'.'+input.name
						 }else{
						 	allinputs=allinputs+';'+divList.item(i).id+'.'+input.name
						 }
						
					}
				}
			}
			
		
		
		if (MDParam.zyxx != "" && MDParam.zyxx != "{}") {// 增加一个管理员
			doOpenPupWinOnTop(src, "功能测试--" + MDParam.title, 0.9, 0.9, initParams + ',' + uptype+','+allinputs);
		} else {
			odin.info("【<b>" + MDParam.title + "</b>】目前配置为非业务经办模块，不能设置测试用例！");
		}
		if (Ext.isIE) {
			event.keyCode = 0;
			event.returnValue = false;
		} else {
			event.preventDefault();
			event.stopPropagation();
		}
		return;
	}
	odin.onKeyDown();
};
Ext.onReady(
	function(){
		document.onkeydown = radow.cm.onKeyDown;
    }
);
/**
 * 表格翻页时的保存统一处理入口
 * @param {} gridId
 */
function doGridSave(gridId){
	radow.cm.doGridSave(gridId);
}


/**
 * 修改分页grid的汇总信息
 */
function setSumMsg(divName, sumMsg) {
	opItemLabel('', divName + "_sumMsgText", sumMsg);
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
	if (odin.getGridColumn(div, item)) {// grid
		gridColumnModel = Ext.getCmp(div).getColumnModel();
		column = odin.getGridColumn(div, item);
		gridColumnModel.setColumnHeader(gridColumnModel.getIndexById(column.id), newLabel);
	}
}

function commClick(type,value){
	if(type=="eac001"){
		doOpenPupWin(contextPath+"/radowAction.do?method=doEvent&pageModel=cm&model=P0&bs=d1insurance.d00public.d51compositeqycnt.PersonCpsiteQy", "人员综合查询", 0.8, (Ext.isIE ? 450 : 450),value);
	}else{
		doOpenPupWin(contextPath+"/radowAction.do?method=doEvent&pageModel=cm&bs=d1insurance.d00public.d51compositeqycnt.OrganizationCpsiteQy", "单位综合查询", 0.8, (Ext.isIE ? 450 : 450),value);
	}
}

function renderClickEac001(value, params, record, rowIndex, colIndex, ds) {
	if (value == null) {
		return;
	}else{
		return "<a href='javascript:void(0)' class='render' onclick='commClick(\"eac001\",\""+value+"\")'>" + value + "</a>";
	}

}

function renderClickAab001(value, params, record, rowIndex, colIndex, ds) {
	if (value == null) {
		return;
	}else{
		return "<a href='javascript:void(0)' class='render' onclick='commClick(\"aab001\",\""+value+"\")'>" + value + "</a>";
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
	//var colName = Ext.getCmp(ds.baseParams.cueGridId).getColumnModel().getDataIndex(colIndex);
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
			return "<a href='javascript:void(0)' class='render' onclick='odin.confirm(\"确定要对本行进行" + showValue + "操作？\", function(btn) {" + " if (btn == \"ok\") {radow.cm.doGridClick(\"" + clickId + "\",\"" + Ext.encode(clickParams).replace(/"/g, "\\\"") + "\"," + rowIndex + ")}})'>" + showValue + "</a>";
		} else { // 不需要确认的按钮
			return "<a href='javascript:void(0)' class='render' onclick='radow.cm.doGridClick(\"" + clickId + "\",\"" + Ext.encode(clickParams).replace(/"/g, "\\\"") + "\"," + rowIndex + ")'>" + showValue + "</a>";
		}
	} else {
		return "<font color=#CCCCCC>" + showValue + "</font>";
	}

}


/**
 * 保存公共场景form触发事件
 */
function doSaveCommScenForm() {
	commParams = {};
	commParams.currentEvent = "saveCommScenForm";
	radow.cm.pubEventCommParamsAccess();
	radow.cm.pubEventReqBefore("saveCommScenForm");
	radow.doEvent("saveCommScenForm",odin.encode(radow.cm.pageParam));
}
/**
 * 为select store对象根据一定的查询条件和过滤条件重新加载数据 
 * @param {Object} objId 要加载数据的组件id
 * @param {Object} aaa100 代码类别
 * @param {Object} aaa105 参数分类
 * @param {Object} filter 过滤条件
 * @param {Object} isRemoveAllBeforeAdd 加载之前是否要清除以前的数据，默认清除
 * @param {Object} isAddBeforeFirst 如果不清除数据，是否加到最前面，默认不加最前面，即加到最后面
 * @param {Object} isAddAllAsItem 第一行是否增加“全部”选项
 */
function setSelectFilterNew(objId, aaa100, aaa105, filter, isRemoveAllBeforeAdd, isAddBeforeFirst, isAddAllAsItem, isWithParams) {
	if (Ext.getCmp(objId + "_combo") == null && odin.getGridColumn(getParentDiv(objId), objId) == null) {
		return;
	}
	if (Ext.getCmp(objId + "_combo") && Ext.getCmp(objId + "_combo").mode == "remote") { // 远程过滤特殊处理
		var baseParams = Ext.getCmp(objId + "_combo").store.baseParams;
		baseParams.codeType = aaa100;
		if (filter == null) {
			filter = "";
		}
		if (aaa105 != null && aaa105 != "") {
			baseParams.aaa105 = aaa105;
		}
		baseParams.filter = filter;
		return;
	}
	var params = {};
	params.aaa100 = aaa100;
	params.aaa105 = aaa105;
	params.filter = filter;
	var req = odin.Ajax.request(contextPath+"/common/sysCodeAction.do?method=querySelectCodeValues",params,odin.ajaxSuccessFunc,null,false,false);
	var data = odin.ext.decode(req.responseText).data;
	var selectData = null;
	if(data!=null&&data.length>0){
		selectData = new Array(data.length);
		for(i=0;i<data.length;i++){
			selectData[i] = {};
			selectData[i].aaa102 = data[i].id.aaa102;
			selectData[i].aaa103 = data[i].id.aaa103;
		}
	}
	setSelectData(objId, selectData, isRemoveAllBeforeAdd, isAddBeforeFirst, isAddAllAsItem, isWithParams);
}
/**
 * 为select store对象进行加载数据
 * @param {} objId
 * @param {} data
 * @param {} isRemoveAllBeforeAdd
 * @param {} isAddBeforeFirst
 * @param {} isAddAllAsItem
 */
function setSelectData(objId, data, isRemoveAllBeforeAdd, isAddBeforeFirst, isAddAllAsItem, isWithParams) {
	var comboObj = Ext.getCmp(objId + "_combo");
	if (comboObj == null) {
		if (odin.getGridColumn(getParentDiv(objId), objId) == null) {
			return;
		} else {
			comboObj = odin.getGridColumn(getParentDiv(objId), objId).editor;
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
		if (isRemoveAllBeforeAdd == false ) { // 不清除
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
/*	if (comboObj.hasFocus) { // 如果有焦点了，则触发一下点击事件来重新调整下拉框高度
		comboObj.onTriggerClick();;
	}*/
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
			store = odin.getGridColumn(getParentDiv(selectId), selectId).editor.store;
		}
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
 * 设置全选
 */
function setSelectAll(gridId, item, value) {
	var obj = document.getElementById("selectall_" + gridId + "_" + item);
	if (obj) {
		obj.checked = value;
		odin.selectAllFuncForE3(gridId, obj, item);
	}
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
 * 操作div所有input项目的属性，如readonly属性、required属性等
 */
function opItemAll(div, opstr, trueOrFalse) {
	eval("opItem" + opstr.substr(0, 1).toUpperCase() + opstr.substr(1).toLowerCase() + "All('" + div + "'," + trueOrFalse + ")");
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
	} else if (odin.getGridColumn(div, item)) { // grid
		var fuc = "listeners.set_" + div + "_" + item + "_Disabled";
		if (isDisabled == true) {
			eval("Ext.getCmp(div).removeListener('beforeedit'," + fuc + ")");
			eval(fuc + "=function(e){" + "	if(e.field==\"" + item + "\"){e.cancle=true;return false;}else{return true;}};");
			eval("Ext.getCmp(div).addListener('beforeedit'," + fuc + ")");
		} else {
			eval("Ext.getCmp(div).removeListener('beforeedit'," + fuc + ")");
		}
		odin.getGridColumn(div, item).editable = !isDisabled;
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
	if (odin.getGridColumn(div, item)) { // grid,先grid，否则grid与非grid会有问题
		var fuc = "listeners.set_" + div + "_" + item + "_Disabled";
		if (isDisabled == true) {
			eval("Ext.getCmp(div).removeListener('beforeedit'," + fuc + ")");
			eval(fuc + "=function(e){" + " if(e.field==\"" + item + "\"){e.cancle=true;return false;}else{return true;}};");
			eval("Ext.getCmp(div).addListener('beforeedit'," + fuc + ")");
		} else {
			eval("Ext.getCmp(div).removeListener('beforeedit'," + fuc + ")");
		}
		odin.getGridColumn(div, item).editable = !isDisabled;
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
		/*var width = cmp.width;
		cmp.setWidth(0);
		cmp.setWidth(width);*/
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
			/*var width = cmp.width;
			cmp.setWidth(0);
			cmp.setWidth(width);*/
		}
	} else if (document.getElementById(div) && getDivItem(div, item)) { // 其他，包括checkbox
		var imgObj = odin.ext.get(item+"_img");
		if(imgObj && imgObj.dom.tagName=='IMG'){
			var p = "E";
			var borderStyle = "border:solid 1px #19B904;";
			if(isDisabled){
				p = "D";
				borderStyle = "border:solid 1px #D3D0D0;";
			}
			imgObj.set({"p":p,"style":borderStyle});
		}else{
			getDivItem(div, item).disabled = isDisabled;
		}
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
		getDivItem(div, item + "SpanId").innerHTML = getLabelSpan(getDivItem(div, (item=='cpquery'?'cpquery_combo':item)).getAttribute("label"), isRequired);
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
			opItemVisible(div, div, isVisible)//隐藏grid
	} else {
		for (var n = 0; n < tagNames.length; n++) {
			var inputList = document.getElementById(div).getElementsByTagName(tagNames[n]);
			for (var j = 0; j < inputList.length; j++) {
				opItemVisible(div, inputList.item(j).name, isVisible);
			}
			opItemVisible(div, div, isVisible);//隐藏div
		}
	}
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
	if (Ext.getCmp(objId + "_combo") == null && odin.getGridColumn(getParentDiv(objId), objId) == null) {
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
	var req = odin.commonQuery(params, odin.ajaxSuccessFunc, odin.ajaxSuccessFunc, false, false);
	var data = odin.ext.decode(req.responseText).data.data;
	setSelectData(objId, data, isRemoveAllBeforeAdd, isAddBeforeFirst, isAddAllAsItem, isWithParams);
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
			try{
				store = odin.getGridColumn(getParentDiv(objId), objId).editor.store;
			}catch(e){return;}
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
			getDivItem(div, item + "SpanId").style.display = display;
			if (getDivItem(div, item + "SpanId" + "TdId")) {
				getDivItem(div, item + "SpanId" + "TdId").style.display = display;
			}
		}
	} else if (odin.getGridColumn(div, item)) {// grid
		gridColumnModel = Ext.getCmp(div).getColumnModel();
		column = odin.getGridColumn(div, item);
		gridColumnModel.setHidden(gridColumnModel.getIndexById(column.id),!isVisible);
		//gridColumnModel.setHidden(column.id, !isVisible);
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
 * 刷新页面
 */
function reflush(url, initParams) {
	if(reflushAfterSave == false && commParams.currentEvent=='save'){
		return;
	}
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
 * grid列后台校验报错，回填设置为空值并获取焦点
 */
function setValidRT(msg){
	var current_div=radow.cm.pageParam.currentDiv;
	if (document.getElementById("gridDiv_" + current_div)) {
		var current_row=radow.cm.pageParam.currentRow;
		var current_field=radow.cm.pageParam.currentColumn;
		var current_col=radow.cm.pageParam.currentCol;
		if(typeof radow.cm.pageParam.selectAll!='undefined'){
			var store = Ext.getCmp(current_div).store;
			var length = store.getCount();
			for(index=0;index<length;index++){
				store.getAt(index).set(current_field,radow.cm.pageParam.currentOriginalValue);
			}
			var obj = document.getElementById(radow.cm.pageParam.selectAll);
			if (obj.className == 'x-grid3-check-col-on') {
				obj.className = "x-grid3-check-col";
			}else{
				obj.className = 'x-grid3-check-col-on';
			}
			delete radow.cm.pageParam.selectAll;
		}else{
			Ext.getCmp(current_div).store.getAt(current_row).set(current_field,radow.cm.pageParam.currentOriginalValue);
			if(odin.getGridColumn(current_div, current_field).editable){
				odin.startEditing(current_div,current_row, current_col);
			}
		}
	}else{
		var current_column=radow.cm.pageParam.currentColumn;
		if(current_column){
			/*var cueCmp = getCmpByName(current_column);
			if(radow.cm.pageParam.currentEvent=='onchange'){
				if(cueCmp.getXType()=='combo'){
					cueCmp.setValue(radow.cm.pageParam.currentOriginalValue);
					odin.doAccForSelect(cueCmp);
				}else{
					document.getElementById(current_column).value = radow.cm.pageParam.currentOriginalValue;
				}
			}*/
			getCmpByName(current_column).noClearValid = true;
			radow.cm.setValid(current_column,false,msg);
		}
	}
}
/**
 * 根据sql进行text的导出
 */
function expText(querySQL, fileName, separator, headNames, withoutHead) {
	if (fileName == null || fileName == "" || fileName == "null") {
		fileNameSim = MDParam.title +  ".txt";
	} else if (fileName.indexOf(".") == 0) {// 以.开头，即只指定了后缀
		fileNameSim = MDParam.title +  fileName;
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
 * 根据sql进行gzip的excel的导出
 */
function expGZIPExcel(querySQL, fileName, headNames,sheetName, childfilename) {
    
    if (fileName == null || fileName == "" || fileName == "null") {
        fileNameSim = MDParam.title + Ext.util.Format.date(odin.getSysdate(), "Ymd");
    } else {
        fileNameSim = fileName;
    }   
    
    sheetNameSim = sheetName;
    childfilenameSim = childfilename;    
    querySQLSim = querySQL;
    headNamesSim = headNames;
    doOpenPupWin(contextPath + "/commform/sys/excel/commSimpleExpGZIPExclWindow.jsp", "下载文件", 500, 160);
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
 * 设置保存后是否进行提示并刷新
 */
function setReflushAfterSave(flag) {
	reflushAfterSave = flag;
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
				var cmp = Ext.getCmp(column);
				//if(cmp.errorIcon!=null && cmp.errorIcon.dom.qtip=='该输入项为必输项'){
					Ext.getCmp(column).validator = function(){return true;};
					Ext.getCmp(column).clearInvalid();
				//}
			} else {
				Ext.getCmp(column).invalidText = errmsg;
				Ext.getCmp(column).validator = function(){return false};
				Ext.getCmp(column).clearInvalid();
				Ext.getCmp(column).markInvalid();
			}
		}
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
 * 打开弹出窗口并隐藏
 */
function doShowPupWin() {
	var windowId = "win_pup";
	var pupWindow = Ext.getCmp(windowId);
	pupWindow.show();
	pupWindow.focus();
}
function doQuerySelect(jsonStr, isDoOnChanged) {
//	fillParentData(jsonStr,commParams.currentColumn);
	unPupWindowCloseClick();// 去掉关闭按钮的函数
	var div = document.getElementById(commParams.currentDiv);
	var col = commParams.currentColumn;
	var queryn;
	if (isDoOnChanged == true) { // 搜索成功
		queryn = col.substr(0,col.indexOf ('_'))==""?col:col.substr(0,col.indexOf ('_'));
		switch (queryn) {
		case 'cpquery':
			Ext.getCmp(col + "_combo").setValue(jsonStr.aab001);
			getDivItem(div, col).value = jsonStr.aab001;
			if (!fromCommLink) {
				setCpqueryBuffer(jsonStr.aab001);
			}
			break;
		case 'psquery':
			getDivItem(div, col).value = jsonStr.aae135;
			if (!fromCommLink) {
				setPsqueryBuffer(jsonStr.eac001);
			}		
			if(jsonStr.aaz001 && jsonStr.cpcombo==""){
				var params = {};
				params.querySQL = "select aab001 aaa102,aab004 aaa103,aaz001 from sbdv_acg8 a where aac001="+ jsonStr.aac001+"" ;
				params.sqlType = "SQL";
				var req = odin.commonQuery(params, odin.ajaxSuccessFunc, null, false, false);
				var data = odin.ext.decode(req.responseText).data.data;
				if(data.length>0){
					jsonStr.cpcombo=odin.ext.encode(data);
				}
			}
			break;
		case 'psidquery':
			getDivItem(div, col).value = jsonStr.aae135;
			if (!fromCommLink) {
				setPsidqueryBuffer(jsonStr.aae135);
			}
			break;
		case 'psidnew':
			if (jsonStr.aae135 != null && jsonStr.aae135 != "") {
				getDivItem(div, col).value = jsonStr.aae135;	
			}
			break;
		case 'cpnew':
		   	if (jsonStr.aab001 != null && jsonStr.aab001 != "") {
				getDivItem(div, col).value = jsonStr.aab001;	
			}
			break;
		}
	}
	var windowId = "win_pup";
	var pupWindow = Ext.getCmp(windowId);
	pupWindow.hide();
	var div = document.getElementById(commParams.currentDiv);
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
				if(jsonName.indexOf('cpquery')>=0 && jsonStr.cpcombo){
					setSelectData(jsonName,jsonStr.cpcombo);
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
	if(isDoOnChanged){
		radow.cm.doCheck(col);
	}
}
/**
 * 去掉窗口的关闭按钮函数
 */
function unPupWindowCloseClick() {
	try {
		var windowId = "win_pup";
		var pupWindow = Ext.getCmp(windowId);
		pupWindow.tools.close.dom.onclick = null;
	} catch (e) {

	}
}
/**
 * 后台操作失败后的动作
 */
function doFailure(response, theCommParams) {

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
	if (document.getElementById("gridDiv_" + commParams.currentDiv)) {
		addmsg = "，请重新输入！";
		Ext.getCmp(commParams.currentDiv).stopEditing();
	}
	odin.error(errmsg + addmsg, function() {
				if (document.getElementById("gridDiv_" + commParams.currentDiv)) {
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
 * 操作项目的属性
 */
function opItemAttribute(div, item, attributeName, attributeValue) {
	if (document.getElementById(div) && document.getElementById(item)) {// 普通html
		document.getElementById(item).setAttribute(attributeName, attributeValue);
	} else if (document.getElementById(item)) {// 菜单内容的操作
		document.getElementById(item).setAttribute(attributeName, attributeValue);
	}
	if (odin.getGridColumn(div, item)) {// grid
		eval('odin.getGridColumn("' + div + '", "' + item + '").' + attributeName + '="' + attributeValue + '"');

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
 * 操作item的style属性
 * @param {} div
 * @param {} item
 * @param {} styleAttributeName
 * @param {} styleAttributeValue
 */
function opItemStyleAttribute(div, item, styleAttributeName, styleAttributeValue) {
	if (document.getElementById(div) && document.getElementById(item)) {
		document.getElementById(item)['style'][styleAttributeName] = styleAttributeValue;
	} else if (document.getElementById(item)) {
		document.getElementById(item)['style'][styleAttributeName] = styleAttributeValue;
	}
}
function loadPageTab(aid, url, forced, text, autoRefresh){
	odin.loadPageInTab(aid, url, forced, text, autoRefresh);
}

function saveOnclick(){
	document.getElementById('doSaveBtn').click();
}	
/**
 * 标签查询（psquery之类的，南20131104增加）
 */
function tagQuery(params) {
	var url = contextPath + "/common/commformTagQueryAction.do?method=query";
	var req = odin.Ajax.request(url, params, blankFunc, blankFunc, false, false);
	var data = odin.ext.decode(req.responseText).data;
	return data;
}
function blankFunc() { // 空的处理函数

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

/**
 * 设置保存后进行提示的信息
 */
function setReflushAfterSaveMsg(msg) {
	reflushAfterSaveMsg = msg;
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