/***
 * **************************
 * ������¶���������޹�˾
 * ����Radow��ܼ���commform�ĺ���js�ļ�
 * auther:jinwei
 * date:2013-2-25
 * version:6.0
 * version:6.0.1 �����˶�IMG��ǩsetDisable��֧�ֺ���֮����ʽ�仯���ɱ༭�Ͳ��ɱ༭������ƶ���ʽ���� jinwei 2013.7.18
 * version:6.0.2 �޸�������ϵͳǶ�뵽����ϵͳʱF9�ȿ�ݷ�ʽ�򿪸����ڻ�������� -- jinwei 2013.7.24
 * version:6.0.3 �޸�doGridCheck,afteredit����û����pageParam�������currentValue����ǰ������,currentOriginalValue���༭ǰ�������������� -- ljd 2013.7.26
 * version:6.0.4 �޸���Щ��PageModel��BS��ʽҳ��������JSʱ��page_url_paramsΪ������� -- jinwei 2013.8.14
 * version:6.0.5  doFailMsgAndFail�������滻mainMessage������\'Ϊ'-- ljd 2013.8.15
 * version:6.0.6 �޸���������������ʱ����������ͬ���������⣨ĩβ���У� -- jinwei 2013.8.16
 * version:6.0.7 �޸�����selectall�ڱ��쳣ʱ������ȷ�ص�֮ǰ״̬������ѡ�д������� -- jinwei 2013.8.19
 * version:6.0.8 ����F12ͨ�ò��Դ���Ϊ��� -- ljd 2013.8.19
 * version:6.0.9 �޸������docheck�����ı���������������С�ȱ���ᵼ�±��������ʾ��ʵ���������������ֶ������������ -- jinwei 2013.8.20
 * version:6.1.0 ���������д��gridȫѡ�����һ���޷�ѡ������ -- ljd 2013.8.23
 * version 6.1.1 �����Զ����ñ��߶ȼ��Ա��ÿҳ��ʾ�����������ܵ��� -- jinwei 2013.8.23
 * version 6.1.2 �޸���ǩ����Ȩ�ޣ�����opItemAttrbute -- gwf 2013.8.26
 * version:6.1.3 �޸��Զ��尴ť����ʾ��Ϣ��Ч������ʾ���˵��� -- jinwei 2013.9.3
 * version:6.1.4 �޸���grid������������ -- gwf 2013.9.3
 * version:6.1.5 �޸��Ǳ��Ԫ���׳��쳣��ֵ�޷���ԭ���⣨��ԭ���ϣ���Ϊ�޸�select���׳��쳣���ɫ������ʾ����ʾ�� -- jinwei 2013.9.4
 * version:6.1.6 ͨ�õ����޸�doCommimp,grid����0�͡�����ɺ�� -- gwf 2013.9.8
 * version:6.1.7 �޸�docheck������ʹ�õ�ǰԪ��JS��У��δͨ��ʱ����������̨ͳһУ���¼� -- jinwei 2013.9.10
 * version:6.1.8 �޸�doOnChange�����ڸ��˹���̨���水�س��޷������¼������� -- ljd 2013.9.13
 * version:6.1.9 �޸����˹���̨��cpquery��ѯ����ѭ������ -- jinwei 2013.9.23
 * version:6.1.10 �޸�����cpquery��required��Ч���������á��Զ�������һ�������뵥Ԫ��ȵ�һ������ -- jinwei 2013.9.25
 * version:6.1.11 ���Ӳ���table��Ԫ�ص�style���Թ��� -- jinwei 2013.9.26
 * version:6.1.12 �޸��Զ���������һ���ɱ༭�񷽷�bug���Զ��������ݽ���ı��水ť -- jinwei 2013.10.10
 * version:6.1.13 ���Ӵ���ҳ���js -- gwf 2013.10.14
 * version:6.1.14 ����ͨ����ť�򿪲�����־���ں��� -- jinwei 2013.10.15
 * version:6.1.15 �޸�reflush������ʹ����������¼�Ϊsave��reflushAfterSaveΪfalseʱ��ˢ��ҳ�� -- jinwei 2013.10.17
 * version:6.1.16 ͳһ����logPrint����ʾ������ -- jinwei 2013.10.24
 * version:6.1.17 �޸������Ǳߴ�����ת����ϸ��񰴻س����񲻶Լ��س������޷�ѡ -- jinwei 2013.10.24
 * version:6.1.18 ���ҳ�����idΪconfirmcount��Ԫ�أ�valueΪ0������ÿconfirmһ���Զ�ֵ��һ -- jinwei 2013.10.29
 * version:6.1.19 ����doGridEnterAddRow���������س�ʱ�Զ�ģ�ⴥ���Զ��尴ť�¼�����������������һ��ʧЧ���� -- jinwei 2013.10.30
 * version:6.1.20 ����querytag���������ӱ��뷽�� -- gwf 2013.12.02
 * version:6.2.0  �޸�opItemVisible������ҳ����ͬ��Ԫ��ʱ���޷����ر��������е����� -- jinwei 2014.1.9
 * version:6.2.1  ���setReflushAfterSaveMsg���� -- ljd 2014.1.13 
 * version:6.2.2  docheck�������������radow.cm.pageParam.currentDiv  -- ljd 2014.2.20 
 * version:6.2.3  �޸�autoGridNextCell����������grid�����ѡ��ʱ����  -- ljd 2014.2.24
 * version:6.2.4  ��grid�в�ѯ�ٶ����Ż� -- ljd 2014.3.14
 * version:6.2.5  docheck�������������radow.cm.pageParam.currentValue  -- ljd 2014.4.1
 * version:6.2.6  �޸�setGridData�������޸�gridͳ��ҳ�����쳣  -- ljd 2014.5.21
 * version:6.2.7  �޸�setBillPrint2�������޸�ˢ��ҳ���reload�ĺ�����ʱ����  -- cx 2014.6.12
 * version:6.2.8  �޸�renderDate����  �������ڸ�ʽ����----ljd 2014.10.24
 * version:6.2.9  �޸�doSuccess����  ����rowClick�¼�ʱ������----ljd 2014.10.30 
 * version:6.3.0  �޸�doGridCheck����  ����radow.cm.pageParam.currentCol=col;----ljd 2014.1.21 
 * version:6.3.1  �޸�����doSave���׳��쳣ʱ�ᵼ��setValid������������ -- jinwei 2015.3.23
 * version:6.3.2  ����scrollToRow���� -- ljd 2015.4.3
 * *****************************
 */
odin.ext.namespace('radow.cm');
//ȫ�ֳ���
var reflushAfterSave = true; // ����֮����Զ�ˢ��
var reflushAfterSaveMsg = "";// ��������Ϣ
var reflushAfterSavePrint  = "";//������ӡ
//��������
var listeners={};
var fileNameSim = "";
var sheetNameSim = "";
var querySQLSim = "";
var headNamesSim = "";
var separatorSim = "";
var childfilenameSim = "";
var commParams = "";//ȫ�ֹ�������
var parentWin = parent;//������ȫ�ֱ���
var fromCommLink = false; // �Ƿ����Թ���������
var formData = null; //formData����������֮ǰ�Ľ����������Ϣ�������������ݽ���
/*var cm_eventDataCustomized = [{"aab001":"aab003,aab002,aab001"}];
var cue_eventDataCustomized = "aab003,aab002,aab001";*/
odin.ext.onReady(function(){
	if(typeof page_url_params!='undefined'){
		commParams.currentOpseno = page_url_params["opseno"];
	}
});
/**
 * @msgBoxParams msgBox�����Ĳ���
 * @msgBoxParams.msgArray ��Ϣ������
 * @msgBoxParams.i ��Ϣ����Ŀ�ʼ��ʾ����
 */
var msgBoxParams = {};
radow.cm = {
	/**
	 * �Ƿ�docheck�������֣�Ĭ��Ϊ��Ҫ����
	 * @type Boolean
	 */
	is_autoNoMask_docheck:false,
	/**
	 * ���һ���¼���Ϣ
	 * @type 
	 */
	cueEvent:{
		eventName:null,
		eventParam:null
	},
	pageParam:{/*�����¼�ʱ�ĵ�ǰ������Ϣ*/
		currentColumn:null,
		cueRow:null,
		cueCol:null,
		currentEvent:null,
		currentValue:null,
		clickParams :null
	},
	/**
	 * ���ҳʱ�ĵ�ǰҳ���津��
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
	 * ����ǰ�¼�
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
	 * ͨ�õ���ǰ�¼�
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
	 * �����
	 */
	doAfterImpForm:function(){
		radow.cm.pubEventCommParamsAccess();
		radow.cm.pageParam.currentEvent = "afterImp";
		radow.cm.pubEventReqBefore("doAfterImpForm");
		radow.doEvent("doAfterImpForm",odin.encode(radow.cm.pageParam));
	},
	/**
	 * �������д��ʱ��Ĺ����¼�
	 */
	doCommImp:function(){
		radow.cm.pubEventCommParamsAccess();
		radow.cm.pageParam.currentEvent = "commImp";
		radow.cm.pubEventReqBefore("doCommImp");
		radow.doEvent("doCommImp",odin.encode(radow.cm.pageParam));
	},
	/**
	 * �����¼�һ��ǰ�ô����Ԥ����
	 * @param {} btn
	 * @param {} e
	 * @param {} eventName  �����¼���
	 */
	pubEventBefore:function(btn,e,eventName){
		var cueEleId = btn.getId();
		radow.cm.pageParam.currentColumn = cueEleId;
		radow.cm.pageParam.currentEvent = eventName;
	},
	/**
	 * �����¼���������ǰ�Ĵ��������������һ���¼��������Ϣ
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
	 * ������commParams�����������Ե�radow.cm.pageParam������
	 */
	pubEventCommParamsAccess:function(){
		odin.ext.apply(radow.cm.pageParam,commParams);
	},
	/**
	 * �Զ����pageParam����
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
	 * �༭���¼�
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
	 * ����е��������¼�
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
	 * ����е��������¼�
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
		doOpenPupWin(contextPath + "/commform/sys/excel/commImpFileWindow.jsp", "��ѡ��Ҫ�ϴ����ļ�", 500, 160);
	},
	/**
	 * ����ɹ���Ӧ
	 * @param {} data
	 */
	doSuccess:function(data){
		//alert(odin.encode(data));
		if(radow.cueMessageCode==1){ //radow����ʧ��
			return;
		}
		reflushAfterSave = true;
		var theCommParams = Ext.util.JSON.decode(data.commParams)[0]; // �����첽ģʽ�������ڷ��ز�����ȡ�ع�������ֵ
		commParams = theCommParams;
		if("init,click,query,exp,doAfterImpForm".indexOf(theCommParams.currentEvent)>=0){ //��ʼ��
			setFormData(data);
			opPage(data.opPage);
		}
		if ("onchange,rowDbClick,rowClick".indexOf(theCommParams.currentEvent)>=0) { // onchange�¼�
//			if (radow.cm.checkValid(document.commForm) != true) { // ��У���ж�
//				if (theCommParams.currentColumn != null) {
//					if (commParams.currentEvent == "onchange") {
//						var cmp = getCmpByName(theCommParams.currentColumn);
//						cmp.checkSelectComplete = false;
//						cmp.setValue(cmp.startValue);
//					} 
//				}
//				return;
//			}
			radow.cm.clearInvalid(document.commForm); // �ɹ����Ƚ�����Ĵ�����Ϣ���������Ҫ��������������ʾ
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
			showMsgBox(data); // ��ʾ��Ϣ
		}
		if ("save,commImp,doCommImp".indexOf(theCommParams.currentEvent)>=0) { // �����
			setFormData(data);
			opPage(data.opPage); // ��ҳ����������ܶ�reflushAfterSave�����޸�
		}
		if (theCommParams.currentEvent == "save") {//��������grid���޸ı�־
			var altmsg = "����ɹ���";
			if (reflushAfterSaveMsg != null && reflushAfterSaveMsg != "") {
				odin.info(reflushAfterSaveMsg, function() {
							reflush();
						});
			}else if(reflushAfterSavePrint != null && reflushAfterSavePrint != "") {
				if (reflushAfterSavePrint == "#") { // ����ʾֱ�Ӵ�ӡ
					radow.cm.doPrint();
				} else {
					odin.confirm(reflushAfterSavePrint, function(btn) {
						if (btn == "ok") { // ȷ��
							radow.cm.doPrint();
						} else { // ȡ��
							reflush();
						}
					});
				}
			}else {
				odin.mask(altmsg); // ������Ӱ
				window.setTimeout("odin.unmask()", 2000);// ȥ����Ӱ
				window.setTimeout("reflush()", 1000); // ҳ��ˢ��
			}
		}
		if (theCommParams.currentEvent == "print"||theCommParams.currentEvent == "click") { //��ӡ
			opPage(data.billPrint);
		}
		if (theCommParams.currentEvent == "commImp") { // ͨ�õ���
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
	 * ����ȷ����Ϣ����ʾ�Ƿ����
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
	 * ����ȷ����Ϣ����ʾ�Ƿ����--���Ե������
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
	 * ���û����ĳ�е���ʾ��Ϣ
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
					// Ext.getCmp(column).invalidText = null; ����Ҫ����������У���������
					var cmp = Ext.getCmp(column);
					//if(cmp.errorIcon!=null && cmp.errorIcon.dom.qtip=='��������Ϊ������'){
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
	 * ����Ƿ��д��������
	 */
	clearInvalid:function(theForm) {
		var eles = theForm.elements;
		for (i = 0; i < eles.length; i++) {
			var obj = eles[i];
			radow.cm.setValid(obj.name, true);
		}
	},
	
	/**
 	* ����Ƿ��д��������
 	*/
   checkValid:function(theForm) {
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
			if (((obj.value != null && obj.value != "") || (obj.name != null && obj.name != "" && document.getElementById(obj.name + "_combo") && document.getElementById(obj.name + "_combo").value != null && document.getElementById(obj.name + "_combo").value != "" && document.getElementById(obj.name + "_combo").value != "����ѡ��...")) && (checkFlag == 0 || checkFlag == 2 || commParams.currentEvent == "save" || commParams.currentEvent == "query" || obj.name != commParams.currentColumn) && !(obj.name.indexOf("_combo") >= 0 && obj.value != getCmpByName(obj.name).defaultValue)) {
				var eObj = getCmpByName(obj.name);
				if (eObj) {
					if (!eObj.isValid(false)) {
						odin.error(errtitle + '��' + obj.getAttribute("label") + '���������ֵ������Ҫ�����������룡', odin.doFocus);
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
	 * ���ݽ��湦�ܴ�ģ��ʱ�ĳ�ʼ������ݲ���
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
	 * ��ȡ������־����Ľ���html��Ϣ
	 * ������
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
	 * ��ʼ����һЩ����
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
					if (parent.commParams.initParamsUsedOnce) { // һ���Ե���Ҫ���
						parent.commParams.initParams = null;
						parent.commParams.initParamsUsedBy = null;
						parent.commParams.initParamsUsedOnce = null;
					}
				}
			}
		//���ý��㵽��һ����
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
	 * �жϵ�ǰ��cm�����¼��Ƿ���Ҫ���б���У��
	 * true��Ϊ��Ҫ��false��Ϊ����Ҫ
	 * ��ʱû��ʵ��ʹ��
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
	 * ʧ�ܺ����Ϣ��ʾ������һЩ��ص�ʧ�ܴ���
	 * @param {} response ��Ӧ�ı�
	 * @param {} msgFuncCont ������Ҫ��ִ̬�еĽű�
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
 	* ҳ��ˢ��
 	*/
	pagesRoload:function(){
	 	odin.confirm("ȷ��Ҫˢ��ҳ�棿", function(btn) {
							if (btn == "ok") { // ȷ��
								location.reload();
							} else { // ȡ��
								return;
							}
						});
	 },
	 /**
	  * ҳ���ʼ��ʱ������Ȩ��ҳ���ʼ����
	  * @param {} cueUserDataInfo
	  */
	 initDataPermission:function(cueUserDataInfo){
	 	var aab301_data = []; //��������Ȩ�� 3
	 	var aaf015_data = []; //�ֵ�������Ȩ�� 4
	 	var aaf030_data = []; //����������Ȩ�� 5
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
	 		aab301.on("select",function(combo,newValue,oldValue){ //������ѡ���Զ�������Ȩ���ʵĽֵ���Ϣ
	 			radow.cm.dataFilter("aab301","aaf015")
	 		});
	 	}
	 	if(aaf015 && aaf030 && aaf015_data.length>1){
	 		aaf015.on("select",function(combo,newValue,oldValue){ //���ݽֵ�ѡ���Զ�������Ȩ���ʵ�������Ϣ
	 			radow.cm.dataFilter("aaf015","aaf030")
	 		});
	 	}
	 },
	 /**
	  * �Զ��������������Ľֵ�����������
	  * @param {} cueSelId ��ǰѡ���������id
	  * @param {} filterSelId �����˵�������id
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
	 		doOpenPupWin(contextPath + "/sys/img/impImgWindow.jsp?id="+id, "��ѡ��Ҫ�ϴ�����Ƭ", 500, 160);
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
	  * �Զ����ñ��߶ȣ��ǹ̶�ֵ
	  * @param {} grid ���ID
	  * @param {} hStr �߶��ַ��� �硰-200,0.3����-200��
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
 * ��ʾ��ʾ��Ϣ
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
 * �����ʽ��һ������Ϣ������ʾ
 * @param {Object} msgArray ��Ϣ����
 * @param {Object} i �ڼ���������ʼ��ʾ��Ĭ��Ϊ0
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
 * ���ø�����
 */
function setParentWin(parentWin1) {
	this.parentWin = parentWin1; 
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
			/*if (isInit) { // ��ʼ�����⴦��
				eval("comboSetFocusForInit_" + column + "_combo" + "=true"); // ������������ʾ��־
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
 * ʱ���ʽ��ת������
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


var tagNames = new Array("input", "textarea"); // Ҫȡ���ݵ�tagName
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
 * ����response��������������ֵ��Form��ȥ
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

                        if ( typeof value == "undefined") {// δ����
                            continue;
                        } else if (value == null) {
                            value = "";
                        }

                        if (value.toString() != eChildItem.value.toString()) {

                            tmpCmp = Ext.getCmp(inputName);

                            if (!tmpCmp && Ext.getCmp(inputName + "_combo")) {// ������
                                odin.setSelectValueReal(inputName, value);
                            } else if (tmpCmp && toType(tmpCmp.getXType()) == "date") {// ���ڸ�ʽ
                                var f = Ext.getCmp(inputName).format;
                                if (tmpCmp.format == "Y-m-d H:i:s") {// ����
                                    eChildItem.value = renderLongTime(value);
                                } else if (tmpCmp.format == "Ym") {
                                    eChildItem.value = renderDateYm(value);
                                } else {
                                    eChildItem.value = renderDate(value);
                                }
                            } else if (eChildItem.type == "checkbox") {// ��
                                if (((value == "true" || value == "1") ? true : false) != eChildItem.checked) {
                                    eChildItem.checked = (value == "true" || value == "1") ? true : false;
                                }
                            } else if (tmpCmp) {// ��ǩ�Ĵ���
                                if ( typeof (value) == "object") {// json��ʽ�����⴦��
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
    
    //��grid��ʽ������    
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
								odin.setSelectValueReal(inputName, value);
							} else if (getFieldType(inputName) == "date") { // ���ڸ�ʽ
								var f = Ext.getCmp(inputName).format;
								if (Ext.getCmp(inputName).format == "Y-m-d H:i:s") { // ����
									inputItem.value = renderDateTime(value);
								}else if(Ext.getCmp(inputName).format == "Ym") {
									inputItem.value = renderDateYm(value);
								}else {
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
	//��grid��ʽ������
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
 * ���ñ��ֵ
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
								//if (getGridColumn(gridId, dataIndex[k]).editor.field.format == 'Y-m-d H:i:s') {
								//value = value.replace("T"," ");2013415 ����genghhע�͵�
								if (odin.getGridColumn(gridId, dataIndex[k]).editor.format == 'Y-m-d H:i:s') {
									value = renderDateTime(value);
									//value = Date.parseDate(value, 'Y-m-d H:i:s');
								} else {
									value = renderDate(value);
									//value = Date.parseDate(value, 'Y-m-d');
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
            
           //����ת��
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
                      }else{// ��ֵ��δָ���������ݵĴ���                           
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
			// �޸�����
			var addRecord = [];
			for (var j = 0; j < jsonStr.length; j++) {
				if (store.getCount() > j) { // ���ڴ������޸Ĵ���
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
				} else { // �����ڴ�����������
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
 * ȡ��grid���е���������
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
 * ��ҳ��Ĳ���
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
 * html��ʽ��onchanged�¼�
 * ��λ����Ա��ѯ��ǩ����
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
//		isQuerying = true; // ���ڲ�ѯ����������������ʱʹ��
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
		if (name.indexOf("psquery") == 0) {// ��psquery��ͷ����Ա��ѯ
//				odin.mask("���ڲ�ѯ..."); // ������Ӱ
//				doOpenPupWin(contextPath+"/pages/commAction.do?method=PSQuery", "��Ա��ѯ -- ѡ��ú�˫����س�����ȷ�� -- ��ݼ����� �� �� �� Enter Esc", 0.9, (Ext.isIE ? 370 : 370), commParams.currentValue + "," + (MDParam == null || MDParam.uptype == "" ? "00" : MDParam.uptype) + "," + commParams.checkRate+","+commParams.isCpflag+","+commParams.aac031Filter);
//				doHiddenPupWin();// ������
//				onQueryPupWindowCloseClick();// �رհ�ť���¼�		
//				odin.autoNextElement(ele);
//				return;
				theMethod = "PSQuery";
				theTitle = "��Ա������� -- ѡ��ú�˫����س�����ȷ��,���رջ�Esc��������������� -- ��ݼ����� �� �� �� Enter Esc";
				theWidth = 0.9;
				theHeight =370;
		} else if (name.indexOf("cpquery") == 0) {// ��cpquery��ͷ�ĵ�λ��ѯ
			var str=document.getElementById(name+'_combo').value;
			if(str!=null && str!=''){			
				if(!odin.isWorkpf){
					document.getElementById(commParams.currentDiv).focus();
				}
//				odin.mask("���ڲ�ѯ..."); // ������Ӱ
//				doOpenPupWin(contextPath+"/pages/commAction.do?method=CPQuery", "��λ��ѯ -- ѡ��ú�˫����س�����ȷ�� -- ��ݼ����� �� �� �� Enter Esc", 0.9, (Ext.isIE ? 370 : 370), commParams.currentValue + "," + (MDParam == null || MDParam.uptype == "" ? "00" : MDParam.uptype) + "," + commParams.checkRate);
//				doHiddenPupWin();// ������
//				onQueryPupWindowCloseClick();// �رհ�ť���¼�
//				if(!odin.isWorkpf){
//					odin.autoNextElement(ele);
//				}			
//				return;
				theMethod = "CPQuery";
				theTitle = "��λ��ѯ -- ѡ��ú�˫����س�����ȷ�� -- ��ݼ����� �� �� �� Enter Esc";
				theWidth = 0.9;
				theHeight =370;
			}	
		}else if (name.indexOf("psidquery") == 0) {// ��cpquery��ͷ�ĵ�λ��ѯ
//			odin.mask("���ڲ�ѯ..."); // ������Ӱ
//			doOpenPupWin(contextPath+"/pages/commAction.do?method=PSIDQuery", "��Ա��ݲ�ѯ -- ѡ��ú�˫����س�����ȷ�� -- ��ݼ����� �� �� �� Enter Esc", 0.9, (Ext.isIE ? 370 : 370), commParams.currentValue + "," + (MDParam == null || MDParam.uptype == "" ? "00" : MDParam.uptype) + "," + commParams.checkRate);
//			doHiddenPupWin();// ������
//			onQueryPupWindowCloseClick();// �رհ�ť���¼�
//			odin.autoNextElement(ele);
//			return;
			theMethod = "PSIDQuery";
			theTitle = "��Ա��ݲ�ѯ -- ѡ��ú�˫����س�����ȷ�� -- ��ݼ����� �� �� �� Enter Esc";
			theWidth = 0.9;
			theHeight =370;
			
		} else if (name.indexOf("psidnew") == 0) {// ��psidnew��ͷ�ĵ�λ��ѯ
//			odin.mask("���ڲ�ѯ..."); // ������Ӱ
//			doOpenPupWin(contextPath+"/pages/commAction.do?method=PSIDNew", "��Ա������� -- ѡ��ú�˫����س�����ȷ�� -- ��ݼ����� �� �� �� Enter Esc", 0.9, (Ext.isIE ? 370 : 370), commParams.currentValue + "," + (MDParam == null || MDParam.uptype == "" ? "00" : MDParam.uptype) + "," + commParams.checkRate);
//			doHiddenPupWin();// ������
//			onQueryPupWindowCloseClick();// �رհ�ť���¼�
//			odin.autoNextElement(ele);
//			return;
			theMethod = "PSIDNew";
			theTitle = "��Ա������� -- ѡ��ú�˫����س�����ȷ��,���رջ�Esc��������������� -- ��ݼ����� �� �� �� Enter Esc";
			theWidth = 0.9;
			theHeight =370;
		} else if (name.indexOf("cpnew") == 0) {// ��psidnew��ͷ�ĵ�λ��ѯ
//			odin.mask("���ڲ�ѯ..."); // ������Ӱ
//			doOpenPupWin(contextPath+"/pages/commAction.do?method=CPNew", "��֯�������� -- ѡ��ú�˫����س�����ȷ�� -- ��ݼ����� �� �� �� Enter Esc", 0.9, (Ext.isIE ? 370 : 370), commParams.currentValue + "," + (MDParam == null || MDParam.uptype == "" ? "00" : MDParam.uptype) + "," + commParams.checkRate);
//			doHiddenPupWin();// ������
//			onQueryPupWindowCloseClick();// �رհ�ť���¼�
//			odin.autoNextElement(ele);
//			return;
			theMethod = "CPNew";
			theTitle = "��λ���� -- ѡ��ú�˫����س�����ȷ��,���رջ�Esc��������������� -- ��ݼ����� �� �� �� Enter Esc";
			theWidth = 0.9;
			theHeight =370;
		}
		var queryParams = {};
		queryParams.initParams =theInitParams;
		queryParams.bs = theMethod;
		var data = tagQuery(queryParams);
		if (data.totalCount == -1) { // ����
			doQuerySelect(data.data[0], false);
			odin.error(data.data[0].retErrorMsg, odin.doFocus);
			return;
		}else if (data.totalCount == 0) {// û������
			var xt = "����ϵͳ��";
			if (uptype == "00") {
				xt = "��ᱣ��ϵͳ��";
			} else if (uptype == "09") {
				xt = "���������ϱ���";
			} else if (uptype == "10") {
				xt = "ũ�����ϱ���ϵͳ��";
			} else if (uptype == "11") {
				xt = "��������������ϱ���";
			} else if (uptype == "20") {
				xt = "����ũ�����ϱ���";
			} else if (uptype == "21") {
				xt = "����������ϱ���ϵͳ��";
			} else if (uptype == "91") {
				xt = "����ϵͳ��";
			}
			var errmsg = xt + "û�з������������ݣ�\n����������ַ���Ϣ�������������²�ѯ��";
			if (theMethod == "PSQuery") {
				errmsg = xt + "û��Ҫ���ҵ���Ա��\n����������ַ���Ϣ�������������²�ѯ��";
			} else if (theMethod == "CPQuery") {
				errmsg = xt + "û��Ҫ���ҵĵ�λ��\n����������ַ���Ϣ�������������²�ѯ��";
			} else if (theMethod == "PSIDQuery") {
				errmsg = xt + "û��Ҫ���ҵ���Ա��ݣ�\n����������ַ���Ϣ�������������²�ѯ��";
			} else if (theMethod == "PSIDNew") { // psidnew�鲻������к�̨����
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
		} else if (data.totalCount == 1) { // ��ѯ��0����1��
			setValid(commParams.currentColumn, true);
			var jsonObj = data.data[0];
			doQuerySelect(jsonObj, true);
			return;
		} else {
			odin.mask("���ڲ�ѯ..."); // ������Ӱ
			theUrl = "/pages/commAction.do?method=" + theMethod;
			doOpenPupWin(theUrl, theTitle, theWidth, theHeight, cjkEncode(theInitParams));
			doHiddenPupWin();// ������
			onQueryPupWindowCloseClick();// �رհ�ť���¼�
			return;
		}
	}
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
 * ��ѯ�¼��Ĺرմ��ڲ���
 */
function closeQueryPupWindow() {
	radow.util.autoFillElementByLike(getIFrameWin("iframe_win_pup").getGridNullJson(getIFrameWin("iframe_win_pup").isQueryDiv),false);
	var triggerOnchange = false;
	if (document.getElementById("iframe_win_pup").src.indexOf("PSIDNew") != -1) {// ��Ա����������⴦��
		triggerOnchange = true;
	}
	doQuerySelect(getIFrameWin("iframe_win_pup").getGridNullJson(getIFrameWin("iframe_win_pup").isQueryDiv), triggerOnchange);

}
/**
 * ����IE��Firefox��iframe���ڻ�ȡ����
 */
function getIFrameWin(id) {
	return document.getElementById(id).contentWindow || document.frames[id];
}
/**
 * query��ǩ��丸ҳ��
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
 * ȡ��psquery�Ļ���ֵ
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
 * ����psquery�Ļ���ֵ
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
 * ȡ��cpquery�Ļ���ֵ
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
 * ����cpquery�Ļ���ֵ
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
 * ȡ��psidquery�Ļ���ֵ
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
 * ����psidquery�Ļ���ֵ
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
 * ��ѯ��ǩ����
 * @param msg ������Ϣ
 */
function doQueryException(msg){
	parent.odin.unmask();
	parent.odin.info(msg);
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
 * ȡ�ñ�input��Ŀ�ĸ�div
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
 * ����ָ���е�onchange�¼�
 * @param {} divId div����
 * @param {} itemId ������
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
 * ���ݴ�ӡ2
 */
function setBillPrint2(reportlet, params, setup, printmode) {
	var url = contextPath + "/common/billPrint2Action.do?reportlet=" + reportlet + "&params=" + (params.replace(/%/g, "��")).replace(/\+/g, "%2B") + "&setup=" + setup + "&printmode=" + printmode;
	if(odin.isWorkpf){
		if(qtobj){
			qtobj.openNewWindow(url,"��ӡ״̬",280,160,"bottomright");
			//window.showModalDialog(url,"��ӡ״̬",280,160);
		}
	}else{
		var windowId = "win_billprint";
		var pupWindow = top.frames[1].Ext.getCmp(windowId);
		pupWindow.setTitle("��ӡ״̬"); // ����
		pupWindow.setSize(280, 160); // ��� �߶�
		top.frames[1].showWindowWithSrc(windowId, url, null);
		var x = top.frames[1].document.body.clientWidth - 280 - 10;
		var y = top.frames[1].document.body.clientHeight - 160 - 10;
		pupWindow.setPosition(x, y);
	}
	if (reflushAfterSavePrint != null && reflushAfterSavePrint != "") { //�����Ĵ�ӡ����ӡ���ͺ��ˢ��
		reloadCurrWindow.defer(1000, window);
	}
}

reloadCurrWindow = function(){
    window.location.reload();
};
/**
 * ���������¼�ָ����radow�ĵ�����¼�������
 * @type 
 */
window.doAfterImp = function(){
	var value = commParams.currentValue;
	var event = commParams.currentEvent;
	commParams = {};
	if (event == "beforeCommImp") {
		commParams.currentEvent = "doCommImp";
		// ��ѯ������
		window.setTimeout('updateProgressbar()', 1000);
	} else {
		commParams.currentEvent = "doAfterImpForm";//afterImp  
	}
	radow.cm.pubEventCommParamsAccess();
	radow.cm.pubEventReqBefore(commParams.currentEvent);
	radow.doEvent(radow.cm.cueEvent.eventName,odin.encode(radow.cm.cueEvent.eventParam));
};
/**
 * ���½���
 */
function updateProgressbar(){
	//
}
/**
 * �򿪶���ĵ�������
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
 * ͨ����ť�򿪲�����־����
 */
function openOpLogWin(){
	if (MDParam == null) {
		return;
	}
	var src = contextPath;
	src += "/pages/commAction.do?method=OriRollback";
	// alert(src);
	var initParams = MDParam.functionid;
	if (MDParam.zyxx != "" && MDParam.zyxx != "{}") {// ����һ������Ա
		doOpenPupWinOnTop(src, "ҵ����־--" + MDParam.title, 0.9, 0.8, initParams);
	} else {
		odin.info("��<b>" + MDParam.title + "</b>��Ŀǰ����Ϊ��ҵ�񾭰�ģ�飬��ҵ����־��");
	}
}

radow.cm.onKeyDown = function(e){
	var event = e || window.event;// ��ȡevent����
	var obj = event.target || event.srcElement;// ��ȡ�¼�Դ
	var keyCode = event.keyCode || event.which || event.charCode;
	var altKey = event.altKey;
	var ctrlKey = event.ctrlKey;
	// ҵ����ˣ�F9��ť�Ĳ���
	if (keyCode == 120) {
		if (MDParam == null) {
			return;
		}
		var src = contextPath;
		src += "/pages/commAction.do?method=OriRollback";
		// alert(src);
		var initParams = MDParam.functionid;
		if (MDParam.zyxx != "" && MDParam.zyxx != "{}") {// ����һ������Ա
			doOpenPupWinOnTop(src, "ҵ����־--" + MDParam.title, 0.9, 0.8, initParams);
		} else {
			odin.info("��<b>" + MDParam.title + "</b>��Ŀǰ����Ϊ��ҵ�񾭰�ģ�飬��ҵ����־��");
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
	// ���Գ�����F12��ť�Ĳ���
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
			
		
		
		if (MDParam.zyxx != "" && MDParam.zyxx != "{}") {// ����һ������Ա
			doOpenPupWinOnTop(src, "���ܲ���--" + MDParam.title, 0.9, 0.9, initParams + ',' + uptype+','+allinputs);
		} else {
			odin.info("��<b>" + MDParam.title + "</b>��Ŀǰ����Ϊ��ҵ�񾭰�ģ�飬�������ò���������");
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
 * ���ҳʱ�ı���ͳһ�������
 * @param {} gridId
 */
function doGridSave(gridId){
	radow.cm.doGridSave(gridId);
}


/**
 * �޸ķ�ҳgrid�Ļ�����Ϣ
 */
function setSumMsg(divName, sumMsg) {
	opItemLabel('', divName + "_sumMsgText", sumMsg);
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
	if (odin.getGridColumn(div, item)) {// grid
		gridColumnModel = Ext.getCmp(div).getColumnModel();
		column = odin.getGridColumn(div, item);
		gridColumnModel.setColumnHeader(gridColumnModel.getIndexById(column.id), newLabel);
	}
}

function commClick(type,value){
	if(type=="eac001"){
		doOpenPupWin(contextPath+"/radowAction.do?method=doEvent&pageModel=cm&model=P0&bs=d1insurance.d00public.d51compositeqycnt.PersonCpsiteQy", "��Ա�ۺϲ�ѯ", 0.8, (Ext.isIE ? 450 : 450),value);
	}else{
		doOpenPupWin(contextPath+"/radowAction.do?method=doEvent&pageModel=cm&bs=d1insurance.d00public.d51compositeqycnt.OrganizationCpsiteQy", "��λ�ۺϲ�ѯ", 0.8, (Ext.isIE ? 450 : 450),value);
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
 * �԰�ť�Ĳ���
 * 
 * @param value��ʽΪ����ʾ������,�Ƿ�������(1����0������),��ť����,����1,����2... @
 *            �磺"����,1,click1,1212,tttt..."
 */
function renderClick(value, params, record, rowIndex, colIndex, ds) {
	if (value == null) {
		return;
	}
	//var colName = Ext.getCmp(ds.baseParams.cueGridId).getColumnModel().getDataIndex(colIndex);
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
			return "<a href='javascript:void(0)' class='render' onclick='odin.confirm(\"ȷ��Ҫ�Ա��н���" + showValue + "������\", function(btn) {" + " if (btn == \"ok\") {radow.cm.doGridClick(\"" + clickId + "\",\"" + Ext.encode(clickParams).replace(/"/g, "\\\"") + "\"," + rowIndex + ")}})'>" + showValue + "</a>";
		} else { // ����Ҫȷ�ϵİ�ť
			return "<a href='javascript:void(0)' class='render' onclick='radow.cm.doGridClick(\"" + clickId + "\",\"" + Ext.encode(clickParams).replace(/"/g, "\\\"") + "\"," + rowIndex + ")'>" + showValue + "</a>";
		}
	} else {
		return "<font color=#CCCCCC>" + showValue + "</font>";
	}

}


/**
 * ���湫������form�����¼�
 */
function doSaveCommScenForm() {
	commParams = {};
	commParams.currentEvent = "saveCommScenForm";
	radow.cm.pubEventCommParamsAccess();
	radow.cm.pubEventReqBefore("saveCommScenForm");
	radow.doEvent("saveCommScenForm",odin.encode(radow.cm.pageParam));
}
/**
 * Ϊselect store�������һ���Ĳ�ѯ�����͹����������¼������� 
 * @param {Object} objId Ҫ�������ݵ����id
 * @param {Object} aaa100 �������
 * @param {Object} aaa105 ��������
 * @param {Object} filter ��������
 * @param {Object} isRemoveAllBeforeAdd ����֮ǰ�Ƿ�Ҫ�����ǰ�����ݣ�Ĭ�����
 * @param {Object} isAddBeforeFirst �����������ݣ��Ƿ�ӵ���ǰ�棬Ĭ�ϲ�����ǰ�棬���ӵ������
 * @param {Object} isAddAllAsItem ��һ���Ƿ����ӡ�ȫ����ѡ��
 */
function setSelectFilterNew(objId, aaa100, aaa105, filter, isRemoveAllBeforeAdd, isAddBeforeFirst, isAddAllAsItem, isWithParams) {
	if (Ext.getCmp(objId + "_combo") == null && odin.getGridColumn(getParentDiv(objId), objId) == null) {
		return;
	}
	if (Ext.getCmp(objId + "_combo") && Ext.getCmp(objId + "_combo").mode == "remote") { // Զ�̹������⴦��
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
 * Ϊselect store������м�������
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
		if (isRemoveAllBeforeAdd == false ) { // �����
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
/*	if (comboObj.hasFocus) { // ����н����ˣ��򴥷�һ�µ���¼������µ���������߶�
		comboObj.onTriggerClick();;
	}*/
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
			store = odin.getGridColumn(getParentDiv(selectId), selectId).editor.store;
		}
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
 * ����ȫѡ
 */
function setSelectAll(gridId, item, value) {
	var obj = document.getElementById("selectall_" + gridId + "_" + item);
	if (obj) {
		obj.checked = value;
		odin.selectAllFuncForE3(gridId, obj, item);
	}
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
 * ����div����input��Ŀ�����ԣ���readonly���ԡ�required���Ե�
 */
function opItemAll(div, opstr, trueOrFalse) {
	eval("opItem" + opstr.substr(0, 1).toUpperCase() + opstr.substr(1).toLowerCase() + "All('" + div + "'," + trueOrFalse + ")");
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
	if (odin.getGridColumn(div, item)) { // grid,��grid������grid���grid��������
		var fuc = "listeners.set_" + div + "_" + item + "_Disabled";
		if (isDisabled == true) {
			eval("Ext.getCmp(div).removeListener('beforeedit'," + fuc + ")");
			eval(fuc + "=function(e){" + " if(e.field==\"" + item + "\"){e.cancle=true;return false;}else{return true;}};");
			eval("Ext.getCmp(div).addListener('beforeedit'," + fuc + ")");
		} else {
			eval("Ext.getCmp(div).removeListener('beforeedit'," + fuc + ")");
		}
		odin.getGridColumn(div, item).editable = !isDisabled;
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
		/*var width = cmp.width;
		cmp.setWidth(0);
		cmp.setWidth(width);*/
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
			/*var width = cmp.width;
			cmp.setWidth(0);
			cmp.setWidth(width);*/
		}
	} else if (document.getElementById(div) && getDivItem(div, item)) { // ����������checkbox
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
		getDivItem(div, item + "SpanId").innerHTML = getLabelSpan(getDivItem(div, (item=='cpquery'?'cpquery_combo':item)).getAttribute("label"), isRequired);
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
			opItemVisible(div, div, isVisible)//����grid
	} else {
		for (var n = 0; n < tagNames.length; n++) {
			var inputList = document.getElementById(div).getElementsByTagName(tagNames[n]);
			for (var j = 0; j < inputList.length; j++) {
				opItemVisible(div, inputList.item(j).name, isVisible);
			}
			opItemVisible(div, div, isVisible);//����div
		}
	}
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
	if (Ext.getCmp(objId + "_combo") == null && odin.getGridColumn(getParentDiv(objId), objId) == null) {
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
	var req = odin.commonQuery(params, odin.ajaxSuccessFunc, odin.ajaxSuccessFunc, false, false);
	var data = odin.ext.decode(req.responseText).data.data;
	setSelectData(objId, data, isRemoveAllBeforeAdd, isAddBeforeFirst, isAddAllAsItem, isWithParams);
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
 * ˢ��ҳ��
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
 * grid�к�̨У�鱨����������Ϊ��ֵ����ȡ����
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
 * ����sql����text�ĵ���
 */
function expText(querySQL, fileName, separator, headNames, withoutHead) {
	if (fileName == null || fileName == "" || fileName == "null") {
		fileNameSim = MDParam.title +  ".txt";
	} else if (fileName.indexOf(".") == 0) {// ��.��ͷ����ָֻ���˺�׺
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
 * ����sql����gzip��excel�ĵ���
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
    doOpenPupWin(contextPath + "/commform/sys/excel/commSimpleExpGZIPExclWindow.jsp", "�����ļ�", 500, 160);
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
 * ���ñ�����Ƿ������ʾ��ˢ��
 */
function setReflushAfterSave(flag) {
	reflushAfterSave = flag;
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
				var cmp = Ext.getCmp(column);
				//if(cmp.errorIcon!=null && cmp.errorIcon.dom.qtip=='��������Ϊ������'){
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
 * �򿪵������ڲ�����
 */
function doShowPupWin() {
	var windowId = "win_pup";
	var pupWindow = Ext.getCmp(windowId);
	pupWindow.show();
	pupWindow.focus();
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
function doQuerySelect(jsonStr, isDoOnChanged) {
//	fillParentData(jsonStr,commParams.currentColumn);
	unPupWindowCloseClick();// ȥ���رհ�ť�ĺ���
	var div = document.getElementById(commParams.currentDiv);
	var col = commParams.currentColumn;
	var queryn;
	if (isDoOnChanged == true) { // �����ɹ�
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
				if (typeof(value) == "undefined") {// δ����
					continue;
				} else if (value == null) {
					value = "";
				}
				if(jsonName.indexOf('cpquery')>=0 && jsonStr.cpcombo){
					setSelectData(jsonName,jsonStr.cpcombo);
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
	if(isDoOnChanged){
		radow.cm.doCheck(col);
	}
}
/**
 * ȥ�����ڵĹرհ�ť����
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
 * ��̨����ʧ�ܺ�Ķ���
 */
function doFailure(response, theCommParams) {

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
	if (document.getElementById("gridDiv_" + commParams.currentDiv)) {
		addmsg = "�����������룡";
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
	if (theCommParams && theCommParams.currentEvent == "saveCommScenForm") { // ���泡��Form���¼�
		if (parent && parent.doAfterSaveCommScenForm) {
			parent.doAfterSaveCommScenForm(errmsg);
		}
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
 * ������Ŀ������
 */
function opItemAttribute(div, item, attributeName, attributeValue) {
	if (document.getElementById(div) && document.getElementById(item)) {// ��ͨhtml
		document.getElementById(item).setAttribute(attributeName, attributeValue);
	} else if (document.getElementById(item)) {// �˵����ݵĲ���
		document.getElementById(item).setAttribute(attributeName, attributeValue);
	}
	if (odin.getGridColumn(div, item)) {// grid
		eval('odin.getGridColumn("' + div + '", "' + item + '").' + attributeName + '="' + attributeValue + '"');

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
 * ����item��style����
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
 * ��ǩ��ѯ��psquery֮��ģ���20131104���ӣ�
 */
function tagQuery(params) {
	var url = contextPath + "/common/commformTagQueryAction.do?method=query";
	var req = odin.Ajax.request(url, params, blankFunc, blankFunc, false, false);
	var data = odin.ext.decode(req.responseText).data;
	return data;
}
function blankFunc() { // �յĴ�����

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

/**
 * ���ñ���������ʾ����Ϣ
 */
function setReflushAfterSaveMsg(msg) {
	reflushAfterSaveMsg = msg;
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