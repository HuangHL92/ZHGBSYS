var rootPath,imagePath;
var g_lastQueryParam = null;     // 上次搜索的参数
var g_isSearchInSearch = false; // 是否是二次搜索

function initRootPath(path,img){
	rootPath = path;
	imagePath = img;
}

function changeGroupKey(){
	var groupkey = $("#groupkey option:selected").val();
	$.post("level-query!getTablesByGroupKey.action",{'groupkey':groupkey},function(tables){
		$("#jsonData").data("tables",tables);
		var options = "";
		if(tables.length <=0){
			$("#tablekey").empty();
			changeTableKey();
		}else{
			$.each(tables,function(i,item){
				if(i==0){
					if($("#initTable").val() != "" && $("#groupkey").attr("disabled")){
						$("#tablekey").attr("disabled",true);
					}else{
						$("#initTable").val(item.table_name);
					}
					options += "<option value='"+item.table_name+"'>"+item.table_name_cn+"</option>";
				}else{
					options += "<option value='"+item.table_name+"'>"+item.table_name_cn+"</option>";
				}
				if(i == (tables.length-1)){
					$("#tablekey").empty().append(options);
					$("#tablekey").val($("#initTable").val());
					changeTableKey();
				}
			});
		}
	});
}

function changeTableKey(){
	var tablekey = $("#tablekey option:selected").val();
	$.post("level-query!getFiledsByTableKey.action",{'tablekey':tablekey},function(fields){
		$("#jsonData").data("fields",fields);
		var options = "";
		if(fields.length <=0){
			$("#fieldkey").empty();
			changeFieldKey();
			changeCompareType();
		}else{
			$.each(fields,function(i,item){
				if(i==0){
					if($("#initField").val() != "" && $("#tablekey").attr("disabled")){
						$("#fieldkey").attr("disabled",true);
					}else{
						$("#initField").val(item.field_dbname);
					}
					options += "<option value='"+item.field_dbname+"'>"+item.field_name_cn+"</option>";
				}else{
					options += "<option value='"+item.field_dbname+"'>"+item.field_name_cn+"</option>";
				}
				if(item.field_dbname == $("#initField").val()){
					setCurField(item);
				}
				if(i == (fields.length-1)){
					$("#fieldkey").empty().append(options);
					$("#fieldkey").val($("#initField").val());
					changeFieldKey();
					changeCompareType();
				}
			});
		}
	});
}

function changeFieldKey(){
	var chk = $("#fieldkey option:selected").val();
	var field = System.getElement($("#jsonData").data("fields"),"field_dbname",chk);
	setCurField(field);
	changeOperatorCreateHtmlElement();
	$("#posStart").val("0");
	$("#posLength").val("0");
}
function setCurField(field){
	if(field == undefined){
		$("#jsonData").removeData("curField");
	}else{
		$("#jsonData").data("curField",field);
	}
}

function changeQueryLogicType(){
	var chk = $("#queryLogicType option:selected").val();
	if(chk == "AND"){
		$("#queryLogic").val("并且").attr("disabled",true);
	}else if(chk == "OR"){
		$("#queryLogic").val("或者").attr("disabled",true);
	}else if(chk == "CUSTOM"){
		$("#queryLogic").val("").attr("disabled",false);
	}
}

function changeCompareType(){
	var chk = $("#fieldflag option:selected").val();
	if(chk == "0"){
		$("#compareValueSpan").show();
		$("#compareFieldSpan").hide();
		changeOperatorCreateHtmlElement();
	}else if(chk == "1"){
		$("#compareValueSpan").hide();
		$("#compareFieldSpan").show();
		$("#compareField").empty().append($("#fieldkey option").clone());
	}
}

function changeOperator(){
	changeOperatorCreateHtmlElement();
}


function changeOperatorCreateHtmlElement(){
	var field = $("#jsonData").data("curField");
	if(field){
		var fieldType = field.dbtype_ref;
		var chk = $("#operator option:selected").val();
		var html ="";
		if(fieldType == "2" || fieldType == "70"){
			if(chk == "BETWEEN 'VALUE1' AND 'VALUE2'"){
				html += "<select id=\"compareValue1\" name=\"compareValue1\" class=\"easyui-combotree\" ></select>";
				html += "—<select id=\"compareValue2\" name=\"compareValue2\" class=\"easyui-combotree\" ></select>";
				$("#compareValueSpan").empty().append(html);
				$("#compareValue1").combotree({
					width : 150,
					url:rootPath+"/code/code.action?codeClass="+field.link_name,
					onBeforeExpand:function(node){
						$(this).tree("options").url=System.rootPath+"/code/code.action?codeClass="+node.attributes.codeClass+"&isclass="+node.attributes.isclass;
					}
				});
				$("#compareValue2").combotree({
					width : 150,
					url:rootPath+"/code/code.action?codeClass="+field.link_name,
					onBeforeExpand:function(node){
						$(this).tree("options").url=System.rootPath+"/code/code.action?codeClass="+node.attributes.codeClass+"&isclass="+node.attributes.isclass;
					}
				});
			}else{
				html += "<select id=\"compareValue\" class=\"easyui-combotree\" name=\"compareValue\"></select>";
				$("#compareValueSpan").empty().append(html);
				$("#compareValue").combotree({
					width : 150,
					url:rootPath+"/code/code.action?codeClass="+field.link_name,
					onBeforeExpand:function(node){
						$(this).tree("options").url=System.rootPath+"/code/code.action?codeClass="+node.attributes.codeClass+"&isclass="+node.attributes.isclass;
					}
				});
			}
		}else if(fieldType == "3"){
			if(chk == "BETWEEN 'VALUE1' AND 'VALUE2'"){
				html += "<input class=\"easyui-numberbox\" id=\"compareValue1\" name=\"compareValue1\" min=\"0\" max=\"10000\" style=\"width:80px;\" />—<input class=\"easyui-numberbox\" id=\"compareValue2\" name=\"compareValue2\" min=\"0\" max=\"10000\" style=\"width:80px;\" />";
				$("#compareValueSpan").empty().append(html);
				$("#compareValue1").numberbox();
				$("#compareValue2").numberbox();
			}else{
				html += "<input class=\"easyui-numberbox\" id=\"compareValue\" name=\"compareValue\" min=\"0\" max=\"10000\" style=\"width:80px;\" />";
				$("#compareValueSpan").empty().append(html);
				$("#compareValue").numberbox();
			}
		}else if(fieldType == "4"){
			if(chk == "BETWEEN 'VALUE1' AND 'VALUE2'"){
				html += "<input id=\"compareValue1\" name=\"compareValue1\" class=\"Wdate\" onFocus=\"WdatePicker({dateFmt: 'yyyy-MM',maxDate:'#F{$dp.$D(\\'compareValue2\\')||\\'2020-10-01\\'}'})\"/>—<input id=\"compareValue2\" name=\"compareValue2\"  class=\"Wdate\" onFocus=\"WdatePicker({dateFmt: 'yyyy-MM',minDate:'#F{$dp.$D(\\'compareValue1\\')}',maxDate:\'2020-10-01\'})\"/>";
			}else{ 
				html += "<input id=\"compareValue\" name=\"compareValue\" class=\"Wdate\" onClick=\"WdatePicker({dateFmt: 'yyyy-MM' })\"/>";
			}
			$("#compareValueSpan").empty().append(html);
		}else if(fieldType == "5"){
			if(chk == "BETWEEN 'VALUE1' AND 'VALUE2'"){
				html += "<select id=\"compareValue1\" name=\"compareValue1\"><option value=\"1\">是</option><option value=\"0\">否</option></select>";
				html += "—<select id=\"compareValue2\" name=\"compareValue2\"><option value=\"1\">是</option><option value=\"0\">否</option></select>";
			}else{
				html += "<select id=\"compareValue\" name=\"compareValue\"><option value=\"1\">是</option><option value=\"0\">否</option></select>";
			}
			$("#compareValueSpan").empty().append(html);
		}else{
			if(chk == "BETWEEN 'VALUE1' AND 'VALUE2'"){
				html += "<input id=\"compareValue1\" name=\"compareValue1\"/>—<input id=\"compareValue2\" name=\"compareValue2\"/>";
			}else{
				html += "<input id=\"compareValue\" name=\"compareValue\"/>";
			}
			$("#compareValueSpan").empty().append(html);
		}
	}else{
		$.messager.alert('错误','获取字段出现错误','error');
	}
}

function addCondition(){
	var trHtml = "";
	var condition = getCurCondition();
	if(condition){
		$("#emptyTr").remove();
		var trHtml ="<tr height=\"30px;\" id=\"tr_"+condition.sorder+"\"><td width=\"60px;\" align=\"center\">"+condition.sorder+"</td>";
			trHtml +="<td width=\"850px;\">"+condition.querySQLCN+"</td>";
			trHtml +="<td width=\"60px;\" align=\"center\">";
			trHtml +="<a href=\"javascript:void(0);\" onclick=\"deleteRow('"+condition.sorder+"')\"><img src=\""+imagePath+"/icons/cancel.png\" width=\"14\" height=\"14\" /></a>";
			trHtml +="</td></tr>";
		$("#conditionListBody").append(trHtml);
	}
}
function deleteRow(sorder){
	$("#tr_"+sorder).hide('slow',function(){$(this).remove();});
	var conditionList = $("#jsonData").data("conditionList");
	if(conditionList){
		conditionList = System.removeElement(conditionList,"sorder",sorder);
		$("#jsonData").data("conditionList",reorder(conditionList));
	}
}

function reorder(conditionList){
	var trHtml = "";
	conditionList = $.map(conditionList,function(item,i){
						item.sorder = (i+1);
						trHtml +="<tr height=\"30px;\" id=\"tr_"+item.sorder+"\"><td width=\"60px;\" align=\"center\">"+item.sorder+"</td>";
						trHtml +="<td width=\"850px;\">"+item.querySQLCN+"</td>";
						trHtml +="<td width=\"60px;\" align=\"center\">";
						trHtml +="<a href=\"javascript:void(0);\" onclick=\"deleteRow('"+item.sorder+"')\"><img src=\""+imagePath+"/icons/cancel.png\" width=\"14\" height=\"14\" /></a>";
						trHtml +="</td></tr>";
						return item;
					});
	if(trHtml == ""){
		$("#conditionListBody").empty().append("<tr id=\"emptyTr\" height=\"30px\"><td colspan=\"3\" width=\"980\" align=\"center\">尚未添加任何条件，请先设置条件然后添加。。。</td></tr>");
	}else{
		$("#conditionListBody").empty().append(trHtml);
	}
	return conditionList;
}
function getCurCondition(){
	var groupkey = $("#groupkey option:selected").val();
	var tablekey = $("#tablekey option:selected").val();
	var field = $("#jsonData").data("curField");
	var operator = $("#operator option:selected").val();
	var operatorCn = $("#operator option:selected").text();
	var posStart = $("#posStart").val();
	var posLength = $("#posLength").val();
	var compareValue="",compareValue1="",compareValue2="",querySql="",querySqlCn="";
	var fieldDesc,fieldDescCn;
	if(field){
		if(posLength<=0) {
			fieldDesc = tablekey+"."+field.field_dbname;
			fieldDescCn = field.field_name_cn;
		}else{
			fieldDesc = "substring("+tablekey+"."+field.field_dbname+","+posStart+","+posLength+")";
			fieldDescCn = field.field_name_cn + " 从 "+posStart+"位截取"+posLength+"位";
		}
		
		var fieldType = field.dbtype_ref;
		var html ="";
		var flag = false,notStr = "";
		if(operator == "= 'VALUE1'" || operator == "<> 'VALUE1'"){
			flag = true;
			if(operator == "<> 'VALUE1'"){
				notStr = " NOT ";
			}
		}
		if(fieldType == "2" || fieldType == "70"){// 代码
			if(operator == "BETWEEN 'VALUE1' AND 'VALUE2'"){
				compareValue1 = $("#compareValue1").combotree('getValue');
				if (fieldType == "70" && compareValue1.length != 0) compareValue1 = "|" + compareValue1 + "|";
				var compareValue1Text = $("#compareValue1").combotree('getText');
				compareValue2 = $("#compareValue2").combotree('getValue');
				if (fieldType == "70" && compareValue2.length != 0) compareValue2 = "|" + compareValue2 + "|";
				var compareValue2Text = $("#compareValue2").combotree('getText');
				
				// 特殊处理任职机构代码查询, by YZQ on 2014/11/06
				if (isNeedToConvertUnitCode(field.link_name, operator)) {
					operator = operator.replace("'VALUE1'", "dbo.getUnitOrder('" + compareValue1 + "')");
					operator = operator.replace("'VALUE2'", "dbo.getUnitOrder('" + compareValue2 + "')");
					querySql = "(dbo.getUnitOrder(" + fieldDesc +") "+ operator + " )";	
				}
				else {
					operator = operator.replace("VALUE1",compareValue1);
					operator = operator.replace("VALUE2",compareValue2);
					querySql = "(" + fieldDesc +" "+ operator + " )";				
				}

				querySqlCn = "(" + fieldDescCn+"在 "+((compareValue1Text=='') ? "空值":compareValue1Text) +"和" + ((compareValue2Text=='') ? "空值":compareValue2Text)+"之间" + " )";
			}else{
				compareValue = $.trim($("#compareValue").combotree('getValue'));
				if (fieldType == "70" && compareValue.length != 0) compareValue = "|" + compareValue + "|";
				var compareValueText = $.trim($("#compareValue").combotree('getText'));
				if(flag && compareValue.length==0){
					compareValue = " ";
				}
				
				// 特殊处理任职机构代码查询, by YZQ on 2014/11/06
				if (operator.indexOf('LIKE') < 0 && isNeedToConvertUnitCode(field.link_name, operator)) {
					operator = operator.replace("'VALUE1'", "dbo.getUnitOrder('" + compareValue + "')");
					querySql = "(dbo.getUnitOrder(" + fieldDesc +") "+ operator + " )";				
				}
				else {
					operator = operator.replace("VALUE1",compareValue);
					if(compareValueText==" " && flag){
						querySql = "((" + fieldDesc +" "+ operator + " ) "+(notStr==""?"OR":"AND")+" (" + fieldDesc +" IS "+notStr+" NULL))";
					}else{
						querySql = "(" + fieldDesc +" "+ operator + " )";
					}
				}
				querySqlCn = "(" + fieldDescCn+" "+operatorCn +" " + ((compareValueText=='') ? "空值":compareValueText) + " )";
			}
		}else if(fieldType == "5"){
			if(operator == "BETWEEN 'VALUE1' AND 'VALUE2'"){
				compareValue1 = $("#compareValue1 option:selected").val();
				compareValue2 = $("#compareValue2 option:selected").val();
				var compareValue1Text = $("#compareValue1 option:selected").text();
				var compareValue2Text = $("#compareValue2 option:selected").text();
				
				operator = operator.replace("VALUE1",compareValue1);
				operator = operator.replace("VALUE2",compareValue2);
				querySql = "(" + fieldDesc +" "+ operator + " )";
				querySqlCn = "(" + fieldDescCn+"在 "+((compareValue1Text=='') ? "空值":compareValue1Text) +"和" + ((compareValue2Text=='') ? "空值":compareValue2Text)+"之间" + " )";
			}else{
				compareValue = $("#compareValue option:selected").val();
				var compareValueText = $("#compareValue option:selected").text();
				if(flag && compareValue.length==0){
					compareValue = " ";
				}
				operator = operator.replace("VALUE1",compareValue);
				
				if(compareValueText==" " && flag){
					querySql = "((" + fieldDesc +" "+ operator + " ) "+(notStr==""?"OR":"AND")+" (" + fieldDesc +" IS "+notStr+" NULL))";
				}else{
					querySql = "(" + fieldDesc +" "+ operator + " )";
				}
				querySqlCn = "(" + fieldDescCn+" "+operatorCn +" " + ((compareValueText=='') ? "空值":compareValueText) + " )";
			}
		}else{
			if(operator == "BETWEEN 'VALUE1' AND 'VALUE2'"){
				compareValue1 = $("#compareValue1").val().Trim();
				compareValue2 = $("#compareValue2").val().Trim();
				
				// 修正日期, by YZQ on 2013/03/16
				if (fieldType == 4) {
					compareValue1 = compareValue1.replaceAll("-", "");
					compareValue2 = compareValue2.replaceAll("-", "");
				}
				
				operator = operator.replace("VALUE1",compareValue1);
				operator = operator.replace("VALUE2",compareValue2);
				querySql = "(" + fieldDesc +" "+ operator + " )";
				querySqlCn = "(" + fieldDescCn+"在 "+((compareValue1=='') ? "空值":compareValue1) +"和" + ((compareValue2=='') ? "空值":compareValue2)+"之间" + " )";
			}else{
				compareValue = $("#compareValue").val().Trim();
				// 修正日期, by YZQ on 2013/03/16
				if (fieldType == 4) {
					compareValue = compareValue.replaceAll("-", "");					
				}
				
				if(flag && compareValue.length==0){
					compareValue = " ";
				}
				operator = operator.replace("VALUE1",compareValue);
				if(compareValue==" " && flag){
					querySql = "((" + fieldDesc +" "+ operator + " ) "+(notStr==""?"OR":"AND")+" (" + fieldDesc +" IS "+notStr+" NULL))";
				}else{
					querySql = "(" + fieldDesc +" "+ operator + " )";
				}
				querySqlCn = "(" + fieldDescCn+" "+operatorCn +" " + ((compareValue==' ') ? "空值":compareValue) + " )";
			}
		}
		var conditionList = $("#jsonData").data("conditionList");
		var curid=1;
		if(conditionList){
			curid = conditionList.length + 1;
		}else{
			conditionList = [];
			curid = 1;
		}
		var curCondition = new Condition();
		curCondition.id = -1;
		curCondition.sorder = curid;
		curCondition.groupkey = groupkey;
		curCondition.tablekey = tablekey;
		curCondition.querySQL = querySql;
		curCondition.querySQLCN = querySqlCn;
		conditionList.push(curCondition);
		$("#jsonData").data("conditionList",conditionList);
		return curCondition;
	}
	return false;
}

function getAllCondition(){
	var conditionList = $("#jsonData").data("conditionList");
	if(conditionList && conditionList.length>0){
		var queryLogic = "",querySql="",querySqlCn="";
		var chk = $("#queryLogicType option:selected").val();
		var tables = [],groups=[];
		var conditionChildren = [];
		if(chk == "AND"){
			queryLogic = "AND";
			conditionChildren = conditionList;
			$.each(conditionList,function(i,item){
				if(i==0){
					querySql = item.querySQL;
					querySqlCn = item.querySQLCN;
				}else{
					querySql += " AND "+ item.querySQL;
					querySqlCn += " 并且 " + item.querySQLCN;
				}
				tables.push(item.tablekey);
				groups.push(item.groupkey);
			});
		}else if(chk == "OR"){
			queryLogic = "OR";
			conditionChildren = conditionList;
			$.each(conditionList,function(i,item){
				if(i==0){
					querySql = item.querySQL;
					querySqlCn = item.querySQLCN;
				}else{
					querySql += " OR "+ item.querySQL;
					querySqlCn += " 或者 " + item.querySQLCN;
				}
				tables.push(item.tablekey);
				groups.push(item.groupkey);
			});
		}else if(chk == "CUSTOM"){
			queryLogic = $("#queryLogic").val();
			var allow = validateExp(conditionList.length,queryLogic);
			if(allow){
				// 替换数字成'@数字@'
				var newQueryLogic = queryLogic.replace(/(\d+)/g, "@$1@");
				querySql = newQueryLogic.replaceAll("\\+"," OR ").replaceAll("\\*"," AND ");
				querySqlCn = newQueryLogic.replaceAll("\\+"," 或者 ").replaceAll("\\*"," 并且 ").replaceAll("\\!"," 非  ");
				
				for (var i = 0; i < conditionList.length; i++) {
					var condition = conditionList[i];
					querySql = querySql.replaceAll("@" + (i + 1) + "@", condition.querySQL);
					querySqlCn = querySqlCn.replaceAll("@" + (i + 1) + "@", condition.querySQLCN);
					tables.push(condition.tablekey);
					groups.push(condition.groupkey);
				}
			}
		}
		
		tables = $.unique(tables);
		var newTables = "";
		for(var its=0;its<tables.length;its++)
		{
			if(newTables.indexOf(tables[its])>=0) continue;
			newTables += ","+tables[its];
		}
		newTables = newTables.substring(1,newTables.length);
		tables = newTables;
		
		groups = $.unique(groups);
		
		var allConditionData = new ConditionData();
		allConditionData.conditionGroupkey = "" + groups;
		allConditionData.conditionTablekey = "" + tables;
		allConditionData.conditionQuerySQL = querySql;
		allConditionData.conditionQuerySQLCN = querySqlCn;
		allConditionData.conditionQueryLogic = queryLogic;
		allConditionData.conditionChildren = conditionChildren;
		return allConditionData;
	}
	return false;
}

function validateExp(max,exp){
	var indexs = exp.match(/\d+/g);
	var flag = false;
	$.each(indexs,function(i,item){
		if(parseInt(item,10)>max){
			flag = true;
			return false;
		}
	});
	if(flag){
		$.messager.alert("错误",exp+"表达式不合法,组合条件不在范围内！","error");
		return false;
	}else{
		try{
			eval(exp);
			return true;
		}catch(e){
			$.messager.alert("错误",exp+":表达式不合法！","error");
			return false;
		}
	}
}

function clearCondition(){
	$("#jsonData").removeData("conditionList");
	$("#jsonData").removeData("readConditions");
	$("#readConditionId").val('');
	$("#conditionListBody").empty().append("<tr id=\"emptyTr\" height=\"30px\"><td colspan=\"3\" width=\"980\" align=\"center\">尚未添加任何条件，请先设置条件然后添加。。。</td></tr>");
}


function changeSelectCondtion(){
	var condition = getSelectCondition();
	$("#selectConditionDesc").val(condition.conditionQuerySQLCN);
}

function validateCondtionPermission(curUser,conditionCreate){
	if(conditionCreate){
		if(conditionCreate == curUser || "bits" == curUser){
			return true;
		}
	}else{
		var condition = getSelectCondition();
		if(condition && (condition.conditionCreate == curUser || "bits" == curUser)){
			return condition;
		}
	}
	return false;
}

function initRead(readConditions){
	var selectionOptions = "";
	$.each(readConditions,function(i,v){
		if(i==0){
			$("#selectConditionDesc").val(v.conditionQuerySQLCN);
			selectionOptions ="<option value='"+v.conditionId+"' selected='selected'>"+v.conditionName+"</option>";
		}else{
			selectionOptions +="<option value='"+v.conditionId+"'>"+v.conditionName+"</option>";
		}
		if(i == (readConditions.length-1)){
			$("#selectCondition").empty().append(selectionOptions);
		}
	});
	$("#jsonData").data("readConditions",readConditions);
}

function getSelectCondition(){
	var readConditions = $("#jsonData").data("readConditions");
	var selectCondition = $("#selectCondition option:selected").val();
	if(readConditions){
		return System.getElement(readConditions,"conditionId",selectCondition);
	}
	return ;
}

function removeSelectCondition(conditionId){
	var readConditions = $("#jsonData").data("readConditions");
	readConditions = System.removeElement(readConditions,"conditionId",conditionId);
	$("#jsonData").data("readConditions",readConditions);
	if(readConditions.length>0){
		return readConditions[0];
	}
	return false;
}

function readConditionToList(){
	var selectCondition = getSelectCondition();
	$("#readConditionId").val(selectCondition.conditionId);
	$("#saveConditionId").val(selectCondition.conditionId);
	$("#conditionName").val(selectCondition.conditionName);
	$("#conditionCreate").val(selectCondition.conditionCreate);
	$("#conditionType").val(selectCondition.conditionType);
	$("#sorder").val(selectCondition.sorder);
	var values;
	if(selectCondition.conditionDomain){
		values = selectCondition.conditionDomain.split(",");
	}else{
		values = ["all"];
	}
	$("#conditionDomain").combobox("setValues",values);
	$("#saveId").val(selectCondition.id);
	var conditions = selectCondition.conditionChildren;
	var conditionList = $("#jsonData").data("conditionList");
	var curCount = 0;
	if(!conditionList){
		conditionList = [];
	}
	curCount = conditionList.length;
	var trHtml = "";
	conditions = $.map(conditions,function(item,i){
		item.sorder = (i+1+curCount);
		trHtml +="<tr height=\"30px;\" id=\"tr_"+item.sorder+"\"><td width=\"60px;\" align=\"center\">"+item.sorder+"</td>";
		trHtml +="<td width=\"850px;\">"+item.querySQLCN+"</td>";
		trHtml +="<td width=\"60px;\" align=\"center\">";
		trHtml +="<a href=\"javascript:void(0);\" onclick=\"deleteRow('"+item.sorder+"')\"><img src=\""+imagePath+"/icons/cancel.png\" width=\"14\" height=\"14\" /></a>";
		trHtml +="</td></tr>";
		return item;
	});
	if(trHtml == ""){
		if(curCount == 0){
			$("#conditionListBody").empty().append("<tr id=\"emptyTr\" height=\"30px\"><td colspan=\"3\" width=\"980\" align=\"center\">尚未添加任何条件，请先设置条件然后添加。。。</td></tr>");
		}
	}else{
		if(curCount == 0){
			$("#conditionListBody").empty().append(trHtml);
		}else{
			$("#conditionListBody").append(trHtml);
		}
	}
	conditionList = conditionList.concat(conditions);
	$("#jsonData").data("conditionList",conditionList);
}

function Condition(){
	this.id;
	this.sorder;
	this.groupkey;
	this.tablekey;
	this.querySQL;
	this.querySQLCN;
}

function ConditionData(){
	this.id;
	this.sorder;
	this.conditionCreate;
	this.conditionId;
	this.conditionName;
	this.conditionType;
	this.conditionDomain;
	this.conditionGroupkey;
	this.conditionTablekey;
	this.conditionQuerySQL;
	this.conditionQuerySQLCN;
	this.conditionQueryLogic;
	this.conditionChildren=[];
}

$(document).ready(function(){
	$("#mc").datagrid({
		//height : 455,
		fit: true,
		nowrap: false,
		rownumbers:true,
		singleSelect:false,
		pagination:true, 
		pageSize : 20,
		idField:'id',
		sortName:'xingm',
		sortOrder:'asc',
		frozenColumns: PERSON_FROZEN_COLUMNS,
		columns: PERSON_COLUMNS,
		toolbar: [{
			text:'返回',
		    iconCls:'icon-back',
		    handler:function(){
				//$("#setPanel").show().siblings().hide();
				$("#queryTab").tabs('select', 0);
			}
		},'-',{
			id:'btn_printAll',
			text:'<input type=\"checkbox\" id=\"chk_printAll\"/> 打印全部',
		    handler:function(){
				var rows = $("#mc").datagrid('getRows');
				if(rows.length==0){
					$("#chk_printAll").attr('checked',false);
					$.messager.alert('提示','没有数据打印!');
				}
		}
		}, {
			id: 'print'
		},{
			id: 'xxx'
		}],
		onSelect: function(rowIndex,rowData){
	    	var obj = $("#printData").data("pageSelects");
	    	if(obj == undefined){
	    		obj = [];
	    	}
	    	var pageNo = $("#page").val();
	    	var pageSelect = System.getElement(obj,"pageNo",pageNo);
	    	if(pageSelect == undefined){
	    		pageSelect = new PageSelect();
	    		pageSelect.pageNo = pageNo;
	    		obj.push(pageSelect);
	    	}
	    	var selectRow = new SelectRow();
	    	selectRow.rowIndex = rowIndex;
	    	selectRow.keywordId = rowData.personcode;
	    	pageSelect.selecteds.push(selectRow);
	    	$("#printData").data("pageSelects",obj);
	    },
	    onUnselect: function(rowIndex,rowData){
	    	var obj = $("#printData").data("pageSelects");
	    	if(obj){
	    		var pageNo = $("#page").val();
	    		var pageSelect = System.getElement(obj,"pageNo",pageNo);
	    		if(pageSelect){
	    			var selecteds = pageSelect.selecteds;
	    			if(selecteds){
	    				selecteds = System.removeElement(selecteds,"rowIndex",rowIndex);
	    				pageSelect.selecteds = selecteds;
	    				$("#printData").data("pageSelects",obj);
	    			}
		    	}
	    	}
	    },
		onLoadSuccess:function(data){
			addmenu("#xxx");
	    	$("#mc").datagrid('unselectAll');
			$("#listPanel .pagination-info").append("<span>  "+data.otherMsg+"</span>");
			var options = $("#mc").datagrid('options');
			$("#page").val(options.pageNumber);
			$("#rows").val(options.pageSize);
			$("#mc").datagrid("getPager").pagination("options").showPageList=false;
			setTimeout(function(){
				// bugfix: 只初始化一次打印按钮，by YZQ on 2015/10/10
				if ($("#mm").length == 0) {
					System.report.menu("#print",printData);
				}
			},10);
			setTimeout("initSelect()",10);
		}
	});
});
function addmenu(obj){
	var menuHtml = "<div id=\"xx\" >";
			menuHtml +="<div>";
		    menuHtml +="<span><a herf='#' onclick='changeMC1()'>默认名册</a></span>";
		    menuHtml +="</div>";
		    menuHtml +="<div>";
		    menuHtml +="<span><a herf='#' onclick='changeMC2()'>中组部干部考察名册</a></span>";
		    menuHtml +="</div>";
	menuHtml +="</div>";
	$(obj + "span").remove(); // EasyUI 1.4.5后，会多出一个下拉箭头，去掉
	$(obj).parent().find("#xx").remove();
	$(obj).parent().append(menuHtml);
	$(obj).menubutton({
	    menu: '#xx',
		text: '切换名册',
	});
}
var flag = false;
function changeMC1() {
	if (flag) {
		 $("#mc").datagrid({
			 columns : PERSON_COLUMNS
		 });
		 addmenu("#xxx");
		 flag = false;
	}
}
function changeMC2() {
	if (!flag) {
		 $("#mc").datagrid({
			 columns : PERSON_ZHBGBKCMC
		 });
		 addmenu("#xxx");
		 flag = true;
	}
}
function printData(){
	var printAll = $("#chk_printAll").is(':checked');
	if(printAll){
		PrintAll();
	}else{
		var obj = $("#printData").data("pageSelects");
		var keys = "";
		var count = 0;
		if(obj){
			$.each(obj,function(i,v){
				var selecteds = v.selecteds;
				$.each(v.selecteds,function(j,s){
					if(count>0){
						keys +=",";
					}
					keys += s.keywordId;
					count ++;
				});
			});
			System.report.print(keys);
		}
	}
}
function PrintAll(){
	$.messager.confirm('提示','是否打印全部?',function(b){
		if(b){
			var queryParam = $("#mc").data("queryParam");
			queryParam.queryType = 3;
			$.ajax({
				url:System.rootPath+'/common/exec-common-method!createPrintSessionkey.action',
				data:queryParam,
				success:function(sessionkey){
					System.report.printAll(sessionkey);
				},
				error:function(){
					$.messager.alert('错误','网络错误!','error');
				},
				complete:function(){}
			});
		}
	});
}

function  initSelect(){
	var obj = $("#printData").data("pageSelects");
	if(obj){
    	var pageNo = $("#page").val();
    	var pageSelect = System.getElement(obj,"pageNo",pageNo);
    	if(pageSelect){
			$.each(pageSelect.selecteds,function(i,v){
				$("#mc").datagrid("selectRow",v.rowIndex);
			});
    	}
	}
}

function clearSelect(){
	$("#page").val(1);
	$("#printData").removeData("pageSelects");
}
function loadMcData(){
	clearSelect();
	var conditionData = getAllCondition();
	var options = $("#mc").datagrid('options');
	var queryParam = options.queryParams;
	options.pageNumber = parseInt($("#page").val());
	queryParam.serviceid = $("#serviceid").val();
	queryParam.group = conditionData.conditionGroupkey;
	queryParam.tables = conditionData.conditionTablekey;
	queryParam.querySQL = conditionData.conditionQuerySQL;
	
	// 添加范围限制，by YZQ on 2013/03/26
	var range = getSearchRange();
	if (range == 'nounit') {
		$.messager.alert('提示', '请先选择一个单位！');
		return;
	}
	else {
		if (range != '') {
			queryParam.querySQL += range;
		}
	}
	
	// --------- 二次查询开始 by YZQ on 2015/05/20 --------------
	// 保存搜索参数，用户二次查询
	if (!g_isSearchInSearch) {
		g_lastQueryParam = {
			group: queryParam.group,
			tables: queryParam.tables,
			querySQL: queryParam.querySQL
		};
	}
	
	if (g_isSearchInSearch) {
		//alert(queryParam.querySQL);
		//alert(g_lastQueryParam.querySQL);
		if (queryParam.querySQL != g_lastQueryParam.querySQL) {
			// 如果前次查询的table不在本次查询中，那么添加
			var lastTables = g_lastQueryParam.tables.split(',');
			var nowTables = queryParam.tables.split(',');
			for (var i = 0; i < lastTables.length; i++) {
				var findTable = false;
				for (var j = 0; j < nowTables.length; j++) {
					if (lastTables[i] == nowTables[j]) {
						findTable = true;
						break;
					}
				}
				
				if (!findTable) {
					nowTables.push(lastTables[i]);
				}
			}
			
			// 更新tables
			var tables = '';
			for (var i = 0; i < nowTables.length; i++) {
				if (tables == '') tables = nowTables[i];
				else tables = tables + ',' + nowTables[i];
			}
			
			queryParam.tables = tables;
			queryParam.querySQL = queryParam.querySQL + ' AND (' + g_lastQueryParam.querySQL + ')';
			//alert(queryParam.querySQL);
			
			g_lastQueryParam = {
				group: queryParam.group,
				tables: queryParam.tables,
				querySQL: queryParam.querySQL
			};
		}
	}
	// --------- 二次查询结束 by YZQ on 2015/05/20 --------------
	
	options.url=rootPath+"/query/level-query!execSearch.action";
	$("#mc").datagrid(options);
	$("#mc").data("queryParam",queryParam);
}


function getSearchRange() {
	var searchMode = '2';
	$("#searchModeDiv :radio").each(function(index){
	    if (this.checked){
	         searchMode = this.value;
			 return;
	    }
	});
	
	if (searchMode == '2') {
		return '';
	}
	else {
		var currentUnit = $("#currentUnit").val();
		if (currentUnit == '') {
			return "nounit";
		}
		
		var sql = " and a000.personcode in (" +
		          "select personcode from a001 where a001_a0255='1' and A001_A0201B='" + currentUnit + "'" + 
		          ") ";
		return sql;
	}
}

// 判断是否需要转换单位代码, by YZQ on 2014/11/06
function isNeedToConvertUnitCode(linkname, operator) {
	if (linkname == '00A2') {
		return operator != "= 'VALUE1'" && 
		    operator != "<> 'VALUE1'" && 
			operator != " LIKE '%VALUE1'" && 
			operator != " LIKE 'VALUE1%'" &&
			operator != " LIKE '%VALUE1%'";
	}
	else {
		return false;
	}
}

function saveQuery() {
	var data = $("#mc").datagrid('getData');
	if (data.rows.length == 0) {
		System.showErrorMsg('你还没有执行查询，或者当前查询没有任何结果，无法保存！');
		return;
	}
	
	var condition = getAllCondition();
	
	var query = { 
		catgory_id: '', 
		query_id: '', 
		query_type: 0, 
		query_tables: condition.conditionTablekey, 
		query_sql: condition.conditionQuerySQL
	};
	var ctrl = new PersonQuery(query);
	ctrl.show();
}