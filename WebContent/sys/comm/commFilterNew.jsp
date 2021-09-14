<%@include file="/commform/basejs/loading/loading.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<%@page
	import="com.insigma.odin.framework.util.commform.BuildUtil.ItemValue"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title></title>
<odin:head />
<script src="<%=request.getContextPath()%>/radow/corejs/radow.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.PageModeEngine.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.util.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.renderer.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.business.js"></script>
<script src="<%=request.getContextPath()%>/basejs/odin.grid.menu.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.cm.js"></script>
<odin:MDParam></odin:MDParam>
</head>
<body>
<odin:toolBar property="filterToolBar">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="����" handler="doAddRow"
		icon="<%=request.getContextPath()%>/basejs/ext/resources/images/default/dd/drop-add.gif"
		cls="x-btn-text-icon"></odin:buttonForToolBar>
	<odin:buttonForToolBar isLast="true" text="ȷ��" handler="doCommFilter"
		icon="<%=request.getContextPath()%>/basejs/ext/resources/images/default/dd/drop-yes.gif"
		cls="x-btn-text-icon"></odin:buttonForToolBar>
</odin:toolBar>

<odin:form action="/pages/comm/commAction.do?method=doAction"
	method="post">

	<odin:gridSelectColJs name="col" selectData=""></odin:gridSelectColJs>

	<odin:gridSelectColJs name="type" selectData="['and','��'],['or','��']"></odin:gridSelectColJs>
	<odin:gridSelectColJs name="relation"
		selectData="['={v}','����'],['!={v}','������'],['>{v}','����'],['>={v}','���ڵ���'],['<{v}','С��'],['<={v}','С�ڵ���'],['like {v%}','ʼ��'],['not like {v%}','��ʼ��'],['like {%v}','ֹ��'],['not like {%v}','��ֹ��'],['like {%v%}','����'],['not like {%v%}','������']"></odin:gridSelectColJs>
	<odin:editgrid property="div_filter" sm="row" afteredit="doAfterEdit"
		beforeedit="doBeforeEdit" topBarId="filterToolBar"
		isFirstLoadData="false" width="620" height="340" autoFill="false">
		<odin:gridDataModel>
			<odin:gridDataCol name="khs" />
			<odin:gridDataCol name="col" />
			<odin:gridDataCol name="relation" />
			<odin:gridDataCol name="value" />
			<odin:gridDataCol name="valuetype" />
			<odin:gridDataCol name="khe" />
			<odin:gridDataCol name="type" />
			<odin:gridDataCol name="del" isLast="true" />
		</odin:gridDataModel>
		<odin:gridColumnModel>
			<odin:gridEditColumn header="����" editor="text" width="35"
				dataIndex="khs" sortable="false" />
			<odin:gridEditColumn header="����" editor="select" selectData=""
				width="220" dataIndex="col" sortable="false"
				tpl="<%=ItemValue.tpl_Value%>" />
			<odin:gridEditColumn header="��ϵ" editor="select"
				selectData="['={v}','����'],['!={v}','������'],['>{v}','����'],['>{v}','���ڵ���'],['<{v}','С��'],['<={v}','С�ڵ���'],['like {v%}','ʼ��'],['not like {v%}','��ʼ��'],['like {%v}','ֹ��'],['not like {%v}','��ֹ��'],['like {%v%}','����'],['not like {%v%}','������']"
				width="70" dataIndex="relation" sortable="false"
				tpl="<%=ItemValue.tpl_Value%>" />
			<odin:gridEditColumn header="ֵ" editor="text" width="150"
				dataIndex="value" sortable="false" />
			<odin:gridEditColumn header="ֵ����" editor="text" width="150"
				dataIndex="valuetype" sortable="false" hidden="true" />
			<odin:gridEditColumn header="����" editor="text" width="35"
				dataIndex="khe" sortable="false" />
			<odin:gridEditColumn header="�һ�" editor="select"
				selectData="['and','��'],['or','��']" width="40" dataIndex="type"
				sortable="false" tpl="<%=ItemValue.tpl_Value%>" />
			<odin:gridColumn header="����" renderer="renderDel" align="center"
				width="40" dataIndex="del" sortable="false" isLast="true" />
		</odin:gridColumnModel>
		<odin:griddata>
		</odin:griddata>
	</odin:editgrid>
</odin:form>

</body>

<script type="text/javascript">
	var afterEditCheck = new Array();
	var selectData = "";
	var col_select = "";
	var value_select = "";
	function doAddRow(){
		var store = Ext.getCmp("div_filter").store;
		var jsonrecord = {khs:'',col:'',relation:'={v}',value:'',valuetype:'string',khe:'',type:'and'};
		store.add(new Ext.data.Record(jsonrecord));
	}
	function doDelRow(rowIndex){
		var store = Ext.getCmp("div_filter").store;
		store.remove(store.getAt(rowIndex));
		for(var i=0;i<store.getCount();i++){
			store.getAt(i).set("del",i);
		}
	}
	function renderDel(value, params, record,rowIndex,colIndex,ds){
		return "<a href='#' onclick='doDelRow("+rowIndex+")'>ɾ��</a>";
	}
	function renderShowValue(value, params, record, rowIndex, colIndex, ds){
		try{
			if(value!=null && value!=''){
				value = Ext.getCmp('div_filter').getView().getCell(rowIndex, colIndex).outerText;
			}
		}catch(e){
		}
		return renderTextValue(value,params, record, rowIndex, colIndex, ds);
	}
	function doAfterEdit(e){
		var i = e.column;
		if(e.field=='value'){
			e.grid.getColumnModel().setRenderer(e.column,renderShowValue);
		}
		if(e.field=='col'){
			e.record.set('value','');
		}
		if(e.field=='khs'){
			if(e.value.replace(/\(/g,'')!=''){
				e.record.set('khs',e.originalValue);
				odin.error('��ʼ���ű���Ϊ�ջ�һ������������"("');
				return;
			}
		}
		if(e.field=='khe'){
			if(e.value.replace(/\)/g,'')!=''){
				e.record.set('khe',e.originalValue);
				odin.error('�������ű���Ϊ�ջ�һ������������")"');
				return;
			}
		}
		afterEditCheck[i] = odin.afterEditForEditGrid(e);
		if(afterEditCheck[i] != null && afterEditCheck[i] != ""){
			odin.error(afterEditCheck[i]);
			return;
		}
	}
	function doCommFilter(){
		window.setTimeout("commFilter()",200);//Ϊ����afteredit�����
	}
	function commFilter(){
		for(var i=0;i<afterEditCheck.length;i++){
			if(afterEditCheck[i] != null && afterEditCheck[i] != ""){
				odin.error(afterEditCheck[i]);
				return;
			}
		}
		var store = Ext.getCmp("div_filter").store;
		var filterString = "";
		var divName = parent.filterParam.divName;
		if(store.getCount()>0){
			for(var i=0;i<store.getCount();i++){
				var record = store.getAt(i);
				if(record.get("col")!="" && record.get("relation")!=""){
					if(filterString!=""){
						var filterType = store.getAt(i-1).get("type");
						filterString = filterString +" " + filterType + " ";
					}
					var col = record.get("col");
					var relation = record.get("relation");
					var value = record.get("value");
					var valuetype = record.get("valuetype");
					if(value==null || value==''){
						if(relation == "={v}"){ //����
							relation = " is null";
						}else if(relation == "!={v}"){ //������
							relation = " is not null";
						}else{
							relation = relation.replace("{","'").replace("}","'").replace("v",value);
						}
					}else{
						if(relation.indexOf("like")!=-1){
							if(valuetype=="date"){
								col = "to_char("+col+",'yyyy-mm-dd')";
							}else if(valuetype=="datetime"){
								col = "to_char("+col+",'yyyy-mm-dd hh24:mi:ss')";
							}
						}
						if(relation.indexOf("like")==-1){
							if(valuetype=="date"){
								value = "to_date('"+renderDate(value)+"','yyyy-mm-dd')";
							}else if(valuetype=="datetime"){
								value = "to_date('"+renderDateTime(value)+"','yyyy-mm-dd hh24:mi:ss')";;
							}
						}
						if(valuetype=="string"){
							value = value.replace(/'/g,"''");
						}
						if((valuetype=="number" || valuetype=="date" || valuetype=="datetime") && relation.indexOf("like")==-1){
							relation = relation.replace("{","").replace("}","").replace("v",value);
						}else{
							relation = relation.replace("{","'").replace("}","'").replace("v",value);
						}
					}
					filterString = filterString + record.get("khs") + col + " " + relation + record.get("khe");
				}
			}
			if(filterString==""){
				odin.error("���������������");
				return;
			}
			if(filterString.split('(').length!=filterString.split(")").length){
				odin.error("����������������Ӧ��");
				return;
			}
			filterString = "/*flt-start*/ where " + filterString + " /*flt-end*/";
			//���¿�ʼ��ѯ
			var gstore = parent.Ext.getCmp(divName).store;
			var param = gstore.baseParams;
			param.filterStr = filterString;
			parent.odin.loadPageGridWithQueryParams(divName,param);
			gstore.load();
			
			eval('parent.filterParam.filterStore_'+divName+'=odin.getGridJsonData("div_filter")');
	        parent.doHiddenPupWin();
	    }else{
	    	odin.error("���������������");
	    } 
	}
	function doBeforeEdit(e) {
		var grid = e.grid;
		var record = e.record;
		var field = e.field;
		var originalValue = e.originalValue;
		var value = e.value;
		var row = e.row;
		var column = e.column;
		if(field=='value'){ //����value�в�����
			var divName = parent.filterParam.divName;
			var colName = record.get('col');
			if(colName==''){
				if(e.msgShow!=false){
					odin.error('�������롰��������',e);
				}
				return false;
			}
			try{
				var parentField = parent.getGridColumn(divName,colName).editor.field;
				var xtype = parentField.getXType();
			}catch(e){
			}
			if(xtype == "combo"){
				var editor = new Ext.grid.GridEditor(new Ext.form.ComboBox({store: new Ext.data.SimpleStore({fields: ['key', 'value'],data:[]}),displayField:'value',valueField:'key',typeAhead: false,mode: 'local',triggerAction: 'all',editable:true,selectOnFocus:true,hideTrigger:false,expand:function(){odin.setListWidth(this);Ext.form.ComboBox.prototype.expand.call(this);},allowBlank:true ,validationDelay:1000,validationEvent:false,validateOnBlur:true,fireKey:odin.doAccSpecialkey,markInvalid:function(msg){odin.error(msg+',����������.');}}));
				var store = parentField.store;
				var length = store.getCount();
				var arrayData = new Array(length);
				for (i = 0; i < length; i++) {
					var temp = new Array(2);
					temp[0] = store.getAt(i).get('key');
					temp[1] = store.getAt(i).get('value');
					arrayData[i] = temp;
				}
				editor.field.store = store;
				value_select = arrayData;
				grid.getColumnModel().setRenderer(column,renderComboValue);
				record.set('valuetype','string');
			}else if(xtype == "numberfield"){
				var editor = new Ext.grid.GridEditor(new Ext.form.NumberField({allowBlank:true ,validationDelay:1000,validationEvent:false,validateOnBlur:true,fireKey:odin.doAccSpecialkey,markInvalid:function(msg){odin.error(msg+',����������.');}}));
				editor.field.store = null;
				value_select = "";
				grid.getColumnModel().setRenderer(column,renderNumberValue);
				record.set('valuetype','number');
			}else if(xtype == "datefield"){
				var formatStr = parentField.format;
				var editor = new Ext.grid.GridEditor(new Ext.form.DateField({format:formatStr, allowBlank:true ,validationDelay:1000,validationEvent:false,validateOnBlur:true,fireKey:odin.doAccSpecialkey,markInvalid:function(msg){odin.error(msg+',����������.');}}));
				//parent.Ext.getCmp(divName).getColumnModel().getRenderer(parent.getGridColumnIndex(divName,colName));
				editor.field.store = null;
				value_select = "";
				grid.getColumnModel().setRenderer(column,(formatStr=='Y-m-d H:i:s'?renderDateTimeValue:renderDateValue));
				record.set('valuetype',(formatStr=='Y-m-d H:i:s'?'datetime':'date'));
			}else{
				var editor = new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank:true ,validationDelay:1000,validationEvent:false,validateOnBlur:true,fireKey:odin.doAccSpecialkey,markInvalid:function(msg){odin.error(msg+',����������.');}}));
				editor.field.store = null;
				value_select = "";
				grid.getColumnModel().setRenderer(column,renderTextValue);
				record.set('valuetype','string');
			}
			//Ext.applyIf(getGridColumn(grid.id,field).editor,parent.getGridColumn(divName,colName).editor);
			grid.getColumnModel().setEditor(column,editor);
			//getGridColumn(grid.id,field).editor = new Ext.form.TextField({allowBlank:true,fireKey:odin.doAccSpecialkey});
			//getGridColumn(grid.id,field).editor = parent.getGridColumn(divName,colName).editor;
		}else{
			//Ext.getCmp('div_filter').getColumnModel().setRenderer(getGridColumnIndex("div_filter", 'value'),renderShowValue);//ȥ���������������ʼ����bug
		}
	}
	function doGridvalueColSelect(value, params, record,rowIndex,colIndex,ds){var sel_data = eval("value_select");for(i=0;i<sel_data.length;i++){if(sel_data[i][0] == value){value = sel_data[i][1];break;}}return value;}
	function renderComboValue(a,b,c,d,e,f){v=doGridvalueColSelect(a,b,c,d,e,f);renderEdit(v,b,c,d,e,f);return radow.renderAlt(v,b,c,d,e,f)}
	function renderTextValue(v,b,c,d,e,f){odin.renderEdit(v,b,c,d,e,f);return radow.renderAlt(v,b,c,d,e,f)}
	function renderNumberValue(v,b,c,d,e,f){odin.renderEdit(v,b,c,d,e,f);return radow.renderAlt(v,b,c,d,e,f)}
	function renderDateValue(a,b,c,d,e,f){v=renderDate(a,b,c,d,e,f);odin.renderEdit(v,b,c,d,e,f);return radow.renderAlt(v,b,c,d,e,f)}
	function renderDateTimeValue(a,b,c,d,e,f){v=renderDateTime(a,b,c,d,e,f);odin.renderEdit(v,b,c,d,e,f);return radow.renderAlt(v,b,c,d,e,f)}
	function setFilterGridData(gridId) {
		inputName = gridId + "Data";
		if (document.all(inputName).value != null && document.all(inputName).value != "") {
			var jsonStr = Ext.util.JSON.decode(document.all(inputName).value);
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
				var e = {};
				e.grid = Ext.getCmp("div_filter");
				e.field = 'value';
				e.originalValue = '';
				e.column = odin.grid.menu.getGridColumnIndex("div_filter", 'value');
				// һ���в�������
				for (var j = 0; j < jsonStr.length; j++) {
					if (store.getCount() > j) { // ���ڴ������޸Ĵ���
						for (var k = 0; k < dataIndex.length; k++) {
							if (dataIndex[k]) {
								var value;
								eval("value = jsonStr[" + j + "]." + dataIndex[k] + ";");
								var record = store.getAt(j);
								oldValue = record.get(dataIndex[k]);
								// alert(value);
								// alert(oldValue);
								if (value != null && value != oldValue) {
									record.set(dataIndex[k], value);
								}
							}
						}
					} else { // �����ڴ�����������
						store.add(new Ext.data.Record(jsonStr[j]));
						e.record = e.grid.getStore().getAt(j);
						e.value = e.record.get('value');
						e.row = j;
						e.field = 'value';
						//e.grid.fireEvent('beforeedit', e);
						e.record.set('value','');
						e.record.set('value',e.value);
						//e.grid.fireEvent('afteredit', e);
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
	radow.radowInit();
	var page_initParams = '<%=request.getParameter("initParams")==null?"":request.getParameter("initParams")%>';
	var page_url_params = odin.ext.urlDecode(location.search.substr(1));
	if(page_initParams!=""){
		page_initParams = page_url_params["initParams"];
	}
	Ext.onReady(
		function(){
			var filterParam = parent.filterParam;
			var divName = filterParam.divName;
			var colsName = filterParam.colsName;
			eval('filterStore=filterParam.filterStore_'+divName);
			var colsNameArray = colsName.split(",");
			var gridColumnModel = parent.Ext.getCmp(divName).getColumnModel();
			for(var i=0;i<colsNameArray.length;i++){
				for (var j = 0; j < gridColumnModel.getColumnCount(); j++) {
					var dataIndex = gridColumnModel.getDataIndex(j);
					if(dataIndex == colsNameArray[i]){
						var colHearderName = gridColumnModel.getColumnHeader(j).replace(/<[^>]+>/g, "").replace(/,/g, "��");
						selectData = selectData+",['"+colsNameArray[i]+"','"+colHearderName+"']";
						break;
					}
				}
			}
			selectData = "["+selectData.substring(1)+"]";
			//�޸���������¼�����Ҫ�ı���
			col_select = eval(selectData);
			//�޸�������ʾ���б�
		    var comboData = new Array(col_select.length);
		    for (i = 0; i < col_select.length; i++) {
	            comboData[i] = {};
	            comboData[i].key = col_select[i][0];
	            comboData[i].value = col_select[i][1];
	        }
	        var data = new Array(comboData.length);
			for(i=0;i<comboData.length;i++){
				data[i] = new Ext.data.Record(comboData[i]);
			}
			gridColumnModel = Ext.getCmp("div_filter").getColumnModel();
			for (var j = 0; j < gridColumnModel.getColumnCount(); j++) {
				var dataIndex = gridColumnModel.getDataIndex(j);
				if(dataIndex == "col"){
					var column = gridColumnModel.getColumnById(gridColumnModel.getColumnId(j));
					var comboboxStore = column.editor.store;
					comboboxStore.add(data);
					break;
				}
			}
			if(filterStore!=null){
				document.getElementById("div_filterData").value = filterStore;
				setFilterGridData("div_filter");
			}
			var store = Ext.getCmp("div_filter").store;
			if(store.getCount()==0){
				doAddRow();
			}
			
			//Ext.getCmp("div_filter").store.getAt(0).set("col",col_select[0][0]);
			//Ext.getCmp("div_filter").on("beforeedit",doBeforeEdit);
	    }
	);
</script>

</html>