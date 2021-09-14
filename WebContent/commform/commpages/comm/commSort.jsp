<%@include file="/commform/basejs/loading/loading.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin-commform.tld" prefix="odin"%>

<%@page import="com.insigma.odin.framework.util.commform.BuildUtil.ItemValue"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title></title>
<odin:commformhead/>
<odin:commformMDParam></odin:commformMDParam>
</head>
<body>
<odin:toolBar property="sortToolBar">
  <odin:fill></odin:fill>
  <odin:buttonForToolBar text="增加" handler="doAddRow" icon="/commform/basejs/ext/resources/images/default/dd/drop-add.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
  <odin:buttonForToolBar isLast="true" text="确定" handler="doCommSort" icon="/basejs/ext/resources/images/default/dd/drop-yes.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
</odin:toolBar>


<odin:gridSelectColJs name="col" selectData=""></odin:gridSelectColJs>

<odin:gridSelectColJs name="type" selectData="['asc','升'],['desc','降']"></odin:gridSelectColJs>
<odin:editgrid property="div_sort" sm="row" afteredit="doAfterEdit" topBarId="sortToolBar" isFirstLoadData="false" width="400" height="240" autoFill="false" >
	<odin:gridDataModel>
		<odin:gridDataCol name="col" /> 
		<odin:gridDataCol name="type"/> 
		<odin:gridDataCol name="del" isLast="true"/> 
	</odin:gridDataModel>
	<odin:gridColumnModel>
		<odin:gridEditColumn header="列名" editor="select" selectData=""  width="270" dataIndex="col" sortable="false" tpl="<%=ItemValue.tpl_Value%>" />
	  	<odin:gridEditColumn header="排序方式" editor="select" selectData="['asc','升'],['desc','降']" width="60" dataIndex="type" sortable="false" tpl="<%=ItemValue.tpl_Value%>" />
		<odin:gridColumn  header="操作" renderer="renderDel" align="center" width="40" dataIndex="del" sortable="false" isLast="true"/>
	</odin:gridColumnModel>	
	<odin:griddata>
	</odin:griddata>
</odin:editgrid>

</body>

<script type="text/javascript">
	var afterEditCheck = new Array(2);
	var selectData = "";
	var col_select = "";
	function doAddRow(){
		var store = Ext.getCmp("div_sort").store;
		var jsonrecord = {col:'',type:'asc'};
		store.add(new Ext.data.Record(jsonrecord));
	}
	function doDelRow(rowIndex){
		var store = Ext.getCmp("div_sort").store;
		store.remove(store.getAt(rowIndex));
		for(var i=0;i<store.getCount();i++){
			store.getAt(i).set("del",i);
		}
	}
	function renderDel(value, params, record,rowIndex,colIndex,ds){
		return "<a href='#' onclick='doDelRow("+rowIndex+")'>删除</a>";
	}
	function doAfterEdit(e){
		var i = e.field=="col"?0:1;
		afterEditCheck[i] = odin.afterEditForEditGrid(e);
		if(afterEditCheck[i] != null && afterEditCheck[i] != ""){
			odin.error(afterEditCheck[i]);
			return;
		}
	}
	function doCommSort(){
		window.setTimeout("commSort()",200);//为了让afteredit先完成
	}
	function commSort(){
		for(var i=0;i<afterEditCheck.length;i++){
			if(afterEditCheck[i] != null && afterEditCheck[i] != ""){
				odin.error(afterEditCheck[i]);
				return;
			}
		}
		var store = Ext.getCmp("div_sort").store;
		
		var sortString = "";
		var doSortStr = "";
		var divName = parent.sortParam.divName;
		if(store.getCount()>0){
			for(var i=0;i<store.getCount();i++){
				var record = store.getAt(i);
				if(record.get("col")!=""){
					sortString = sortString + "," + record.get("col") + " " + record.get("type");
				}
			}
			if(sortString==""){
				odin.error("请输入排序条件！");
				return;
			}
			doSortStr = sortString.substr(1);
			sortString = "/*ord-start*/ order by " + doSortStr + " /*ord-end*/";
			//排序修改sql语句
			var querySQL = parent.Ext.getCmp(divName).store.baseParams.querySQL;
			var sqlType = parent.Ext.getCmp(divName).store.baseParams.sqlType;
			if(querySQL.indexOf("/*ord*/")>=0){ //要加入排序语句的地方有注释（不能用/*order-start*/,hibernate会出错）
				querySQL = querySQL.replace("/*ord*/", sortString);
			}else if(querySQL.indexOf("/*ord-start*/")>=0){//原已经排序必须在排序的地方加/*ord-start*/和/*ord-end*/包围的注释
				var orderStart = querySQL.indexOf("/*ord-start*/");
				var orderEnd = querySQL.indexOf("/*ord-end*/")+11;
				var repStr = querySQL.substring(orderStart, orderEnd);
				querySQL = querySQL.replace(repStr, sortString);
			}else{ //未加注释
				if(sqlType.toUpperCase() == "HQL"){
					querySQL = querySQL + sortString;
				}else{
					querySQL = "@_76tJBvkT+4WvW8Ix/e6qDg==_@" + querySQL + "@_7y/+a0omhqA=_@"+sortString;
				}
			}
			var store = parent.Ext.getCmp(divName).store;
			store.baseParams.querySQL = querySQL;
			parent.doSort(divName,doSortStr); //界面也进行排序
			store.load();
			eval('parent.sortParam.sortStore_'+divName+'=odin.getGridJsonData("div_sort")');
	        parent.doHiddenPupWin();
	    }else{
	    	odin.error("请输入排序条件！");
	    } 
	}
	Ext.onReady(
		function(){
			var sortParam = parent.sortParam;
			var divName = sortParam.divName;
			var colsName = sortParam.colsName;
			eval('sortStore=sortParam.sortStore_'+divName);
			var colsNameArray = colsName.split(",");
			var gridColumnModel = parent.Ext.getCmp(divName).getColumnModel();
			for(var i=0;i<colsNameArray.length;i++){
				for (var j = 0; j < gridColumnModel.getColumnCount(); j++) {
					var dataIndex = gridColumnModel.getDataIndex(j);
					if(dataIndex == colsNameArray[i]){
						var colHearderName = gridColumnModel.getColumnHeader(j);
						selectData = selectData+",['"+colsNameArray[i]+"','"+colHearderName+"']";
						break;
					}
				}
			}
			selectData = "["+selectData.substring(1)+"]";
			//修改下拉后的事件所需要的变量
			col_select = eval(selectData);
			//修改下拉显示的列表
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
			gridColumnModel = Ext.getCmp("div_sort").getColumnModel();
			for (var j = 0; j < gridColumnModel.getColumnCount(); j++) {
				var dataIndex = gridColumnModel.getDataIndex(j);
				if(dataIndex == "col"){
					var column = gridColumnModel.getColumnById(gridColumnModel.getColumnId(j));
					var comboboxStore = column.editor.field.store;
					comboboxStore.add(data);
					break;
				}
			}
			if(sortStore!=null){
				document.getElementById("div_sortData").value = sortStore;
				setGridData("div_sort");
			}
			if(Ext.getCmp("div_sort").store.getCount()==0){
				doAddRow();
			}
			//Ext.getCmp("div_sort").store.getAt(0).set("col",col_select[0][0]);
	    }
	);
</script>

</html>