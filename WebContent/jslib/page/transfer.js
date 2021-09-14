var setting = {
	data: {
		key : {
			name: 'text'
		}
	},
	check: {
		enable : true,
		nocheckInherit: false,
		chkboxType: { "Y" : "s", "N" : "s" }
	},
	callback: {
		onCheck: onUnitCheck
	},
	async: {
		enable: true,
		url: System.rootPath + "/common/unit-tree!getAccessiableUnitTree.action?treeType=1",
		autoParam:["id"]
	}
};
var setting3 = {
		data: {
			key : {
				name: 'text'
			}
		},
		check: {
			enable : true,
			nocheckInherit: false,
			chkboxType: { "Y" : "", "N" : "" }
		},
		callback: {
			onCheck: onUnitCheck2
		},
		async: {
			enable: true,
			url: System.rootPath + "/common/unit-tree!getAccessiableUnitTree.action?treeType=7",
			autoParam:["id"]
		}
	};
 

$(document).ready(function()	{
	$("#mc").datagrid({
		fit: true,
		rownumbers:true,
		singleSelect:false,
		pagination:true, 
		nowrap: false,
		pageSize : 20,
		idField:'id',
		sortName:'xingm',
		sortOrder:'asc',
		//frozenColumns: PERSON_FROZEN_COLUMNS,
		columns: [[
		   		{
					field: 'name',
					title: '姓名',
					align: "center",
					width: 80,
					resizable: true
//					formatter: function(value, rowData, rowIndex) {
						//return "<a href='javascript:void(0);' onclick='openUpdate("+rowData.personcode+");'> "+value+"</a>"						
						
//						if(sexCode == "2"){
						//	return "<a href='javascript:void(0);' onclick='System.leaderQuery.showRemoval("+rowData.personcode+");'>"+value+"</a><br/>(女)";
//						}else{
//							return value == undefined ? "" : "<a href='javascript:void(0);' onclick='System.leaderQuery.showRemoval("+rowData.personcode+")'>"+value+"</a>";
//						}
//					}
				},{
				    field: 'unit_show',
				  	title: '接受单位',
				  	align: "center",
				  	width: 180,
				  	resizable: true
				},{
					field: 'status',
				  	title: '状态',
				  	align: "center",
				  	width: 80,
				  	resizable: true,
				  	formatter: function(value, rowData, rowIndex) {
						var code = rowData.status;
						if(code == "0"){
							return "等待接受 ";
						}else if (code == "1") {
							return "已接收";
						}else if (code == "2") {
							return "已拒绝";
						}
					}
				},{
					field: 'remark',
				  	title: '备注',
				  	align: "center",
				  	width: 200,
				  	resizable: true
				},
//				{
//					field: 'created_by',
//				  	title: '创建人',
//				  	align: "center",
//				  	width: 80,
//				  	resizable: true
//				},
//				{
//					field: 'created_datetime',
//				  	title: '创建时间',
//				  	align: "center",
//				  	width: 150,
//				  	resizable: true
////				  	formatter: function(value, row, index) {
////						return 
////					}
//				},
//				{
//					field: 'updated_by',
//				  	title: '更新人',
//				  	align: "center",
//				  	width: 80,
//				  	resizable: true
//				},
//				{
//					field: 'updated_datetime',
//				  	title: '更新时间',
//				  	align: "center",
//				  	width: 150,
//				  	resizable: true
////				  	formatter: function(value, row, index) {
////						return Utils.toDate(value);
////					}
//				},
				{
					field: 'received_by',
				  	title: '接受人',
				  	align: "center",
				  	width: 80,
				  	resizable: true
				},
				{
					field: 'received_datetime',
				  	title: '接受时间',
				  	align: "center",
				  	width: 150,
				  	resizable: true
				},
				{
					field: 'rejected_by',
				  	title: '拒绝人',
				  	align: "center",
				  	width: 80,
				  	resizable: true
				},
				{
					field: 'rejected_datetime',
				  	title: '拒绝时间',
				  	align: "center",
				  	width: 150,
				  	resizable: true
				},{
					field: 'rejected_remark',
				  	title: '拒绝理由',
				  	align: "center",
				  	width: 200,
				  	resizable: true
				}
				]],
		onDblClickRow: function(index, row) {
			openDialog(row);
		},
		onLoadSuccess:function(data){
			$("#mc").datagrid('clearSelections');
		}
		
	});
	// 绑定document的click时间，用于隐藏单位选择层
	$(document).bind("click",function(e){ 
		var target = $(e.target); 
		if (target.closest("#unitTreeDiv").length == 0 && !$("#unitTreeDiv").is(":hidden")){
			getSelectedUnits();
			$("#unitTreeDiv").hide();
		} 
		if (target.closest("#unitTreeDiv2").length == 0 && !$("#unitTreeDiv2").is(":hidden")){
			getSelectedUnits2();
			$("#unitTreeDiv2").hide();
		} 
	});
	
	$("#unitName").bind("click", function(e) {
		e?e.stopPropagation():event.cancelBubble = true;
		if ($("#unitTreeDiv").is(":hidden")) {
			getSelectedUnits();
			pickupUnit();
		}
	});
	
	initUnitName();
	
	// 绑定document的click时间，用于隐藏单位选择层
	
	$("#unitName2").bind("click", function(e) {
		e?e.stopPropagation():event.cancelBubble = true;
		if ($("#unitTreeDiv2").is(":hidden")) {
			getSelectedUnits2();
			pickupUnit2();
		}
	});
	
	initUnitName2();
});
//获取转移干部列表
function initPersonList(row){
	$("#personList").datagrid({
		width: 450,
		height: 321,
		idField:'personcode',
		singleSelect: false,
		frozenColumns: [[
			{field:'ck',checkbox:true}
		]],
		columns:[[
			{field:'name',title:'姓名',width:60, align: 'center'},					
			{field:'zhiw',title:'现任职务',width:300, align: 'left'}
		]],
		toolbar:[{
			iconCls:'icon-add',
			text:'添加人员',
			handler:function(){
				var url = System.rootPath + '/meeting/meeting!pickPerson.action?pickFromType=ac';
				System.openPopupWindow(url, '选择调出人员', 960, 600, function() {
					top.System.Params.selectedPersons = [];
				}, function() {
					if (top.System.Params.selectedPersons.length > 0) {
						onSelectedPerson(top.System.Params.selectedPersons);
					}
				}, 'acPickPersonDlg');
			}
		},'-',{
			iconCls:'icon-remove',
			text:'删除选中人员',
			handler:function(){
				var rows = $("#personList").datagrid('getSelections');
				if (rows.length > 0) {
					System.showConfirmMsg('确定移除选中人员？', function() {
						$(rows).each(function(i, r) {
							var index = $("#personList").datagrid('getRowIndex', r);
							$("#personList").datagrid('deleteRow', index);
						});
					});
				}
			}
		}]
	});
}
//添加选中
function onSelectedPerson(persons) {
	var newPersons = [];
	var rows = $("#personList").datagrid('getRows');
	for (var i = 0; i < persons.length; i++) {
		var person = persons[i];
		var existed = false;
		for (var j = 0; j < rows.length; j++) {
			var row = rows[j];
			if (person.personcode == row.personcode) {
				existed = true;
				break;
			}
		}
		
		if (!existed) {
			newPersons.push({
				personcode: person.personcode,
				name: person.xingm,
				zhiw: person.zhiw
			});
		}
	}
	
	for (var i = newPersons.length - 1; i >=0 ; i--) {
		$('#personList').datagrid('insertRow', { index: 0, row: newPersons[i]});
	}
}
function openDialog(data) {
	$("#updateTransferDlg").show();
	$('#updateTransferDlg').dialog({
		title: '更新干部调入调出信息',
		width: 430,
		height: 300,
		modal: true,
		buttons: getDialogButtons(data),
		onOpen : function () {
			$("#updatename").val(data.name);
			$("#updateunitname").val(data.unit_show);
			$("#updateremark").val(data.remark);
			
		},
		onClose: function() {
			$.each($("input[type='checkbox']"),function(i, item){
				$(this).attr('checked', false);
			});
			
			$.each($("input[type='text']"),function(i, item){
				$(this).val('');
			});
			$("#personList").datagrid('loadData',{total:0,rows:[]});
			
		}
	});
	 
}
function openNewDialog(data) {
	
	$("#newTransferDlg").show();
	$('#newTransferDlg').dialog({
		title: '新增干部调入调出信息',
		width: 835,
		height: 445,
		modal: true,
		buttons: getDialogButtons(data),
		onOpen : function () {
			initPersonList(data);
			$("#newTransferDlgForm").form('reset');
			//$("#newTrainBclb").val($("#newTrainBclb").combotree('getValue'));
		},
		onClose: function() {
//			$.each($("input[type='checkbox']"),function(i, item){
//				$(this).attr('checked', false);
//			});
//			
//			$.each($("input[type='text']"),function(i, item){
//				$(this).val('');
//			});
//			$.each($("textarea"),function(i, item){
//				$(this).val('');
//			})
			$("#personList").datagrid('loadData',{total:0,rows:[]});
		}
	});
}
var type;
function getDialogButtons(data){
	var buttons = [];
	if (data == null) {
		buttons.push({
			iconCls:'icon-save',
			text:'保存',
			handler:function(){
				onSave(data);
			}
		});
		return buttons;
	}else {
		var url = System.rootPath + '/transfer/transfer!show.action';
		$.ajax({
			type: "POST",
			url: url,
			async: false,
			data: { 
				personcode : data.personcode,
			},
			success:function(json){
				type = json;
			}
		});	
		if (type == null) {
			alert(type);
		}
		if (type == '3' || type == '1') {
			//创建者
			if (data.status == '0') {
				buttons.push(
				{
					iconCls:'icon-save',
					text:'保存',
					handler:function(){
						onSave(data);
					}
				},{
					iconCls:'icon-no',
					text:'删除',
					handler:function(){
						onDelete(data);
					}
				});
			}else if (data.status == '2') {
				buttons.push(
				{
					iconCls:'icon-no',
					text:'删除',
					handler:function(){
						onDelete(data);
					}
				}		
				);
			}
		}
		if (type == '3' || type == '2') {
			//接受者
			if (data.status == '0') {
				buttons.push(
				{
					iconCls:'icon-ok',
					text:'接受',
					handler:function(){
						onReceive(data);
					}
				},{
					iconCls:'icon-undo',
					text:'拒绝',
					handler:function(){
						onRejected(data);
					}
				}
				);
			}
		}
	}
	buttons.push({
		iconCls:'icon-cancel',
		text:'关闭',
		handler:function(){
			$("#updateTransferDlg").dialog('close');
		}
	});
	return buttons;
}
function getRejectedButtons(data){
	var buttons = [];
	buttons.push(
		{
			iconCls:'icon-save',
			text:'确定',
			handler:function(){
				onSaevRejectRemark(data);
				$("#updateTransferDlg ").dialog('close');
				
			}
		},{
			iconCls:'icon-cancel',
			text:'关闭',
			handler:function(){
			$("#rejectedRemarkDlg").dialog('close');
		}
	});
	return buttons;
}
function onSaevRejectRemark(data) {
	var rejectedRemark = $("#rejectedRemark").val();
	System.showConfirmMsg("确定拒绝<span style='color:red'>"+data.name+"</span>干部调入调出请求吗？", function() {
		var url = System.rootPath + '/transfer/transfer!rejected.action';
		System.post(url,{'id':data.id,
						'rejectedRemark': rejectedRemark
						}, function(data) {
			System.showInfoMsg('拒绝成功！');
			setTimeout("$('#mc').datagrid('reload');",500);
			$("#rejectedRemarkDlg").dialog('close');
		});
		
	});
}
function onRejected(data) {
	$("#rejectedRemarkDlg").show();
	$('#rejectedRemarkDlg').dialog({
		title: '拒绝理由',
		width: 400,
		height: 215,
		modal: true,
		buttons: getRejectedButtons(data),
		onOpen : function () {
			$("#rejectedRemarkDlgForm").form('reset');
		},
		onClose: function() {
			$.each($("textarea"),function(i, item){
				$(this).val('');
			})
			//setTimeout("$('#mc').datagrid('reload');",500);
		}
	});
}

function onReceive(data) {
	System.showConfirmMsg("确定接受<span style='color:red'>"+data.name+"</span>干部调入调出请求吗？", function() {
		var url = System.rootPath + '/transfer/transfer!receive.action';
		System.post(url,{
					'id':data.id,
					'unit':data.unit,
					'unit_show':data.unit_show,
					'personcode':data.personcode
			}, function(data) {
			System.showInfoMsg('接受成功！');
			setTimeout("$('#mc').datagrid('reload');",500);
		});
		$("#updateTransferDlg").dialog('close');
	});
}
function onDelete(data) {
	System.showConfirmMsg("确定删除<span style='color:red'>"+data.name+"</span>干部转移信息吗？", function() {
		var url = System.rootPath + '/transfer/transfer!delete.action';
		System.post(url,{'id':data.id}, function(data) {
			System.showInfoMsg('删除成功！');
			setTimeout("$('#mc').datagrid('reload');",500);
		});
		$("#updateTransferDlg").dialog('close');
	});
	
}

function onSave(data) {
	if (data == null) {
		var url = System.rootPath + '/transfer/transfer!create.action';
		$.ajax({
			type: "POST",
			url: url,
			data: { 
				unit : $("#unit2").val(),
				unit_show :$("#unitName2").val(),
				remark : $("#remark").val(),
				personcodes : getPersons(),
				names : getNames()
				
			},
			success:function(json){
				//System.closeLoadMask("#mainDiv");
				if (json.indexOf("保存成功") >= 0) {
					$.messager.alert('提示', '保存成功!');	
					setTimeout("$('#mc').datagrid('reload');",500);
				}
				else {
					$.messager.alert('提示', '保存失败!\n' + json);								
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				//System.closeLoadMask("#mainDiv");
				$.messager.alert('提示', "保存失败,请重试!"+textStatus+errorThrown);
			}
		});	
		$("#newTransferDlg").dialog('close');
	}else {
		//更新备注
		var url = System.rootPath + '/transfer/transfer!update.action';
		$.ajax({
			type: "POST",
			url: url,
			data: { 
				id : data.id,
				remark : $("#updateremark").val()
				
			},
			success:function(json){
				if (json.indexOf("保存成功") >= 0) {
					$.messager.alert('提示', '保存成功!');	
				}
				else {
					$.messager.alert('提示', '保存失败!\n' + json);								
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				$.messager.alert('提示', "保存失败,请重试!"+textStatus+errorThrown);
			}
		});	
		setTimeout("$('#mc').datagrid('reload');",500);
		$("#updateTransferDlg").dialog('close');
	}
	
}


function onUnitCheck(event, treeId, treeNode) {
	// 这里我们只处理取消，如果子节点取消，必须把父节点都取消
	if (!treeNode.checked) {
		Utils.cancelParentNodeChecked('unitTree', treeNode);
	}
}
function onUnitCheck2(event, treeId, treeNode) {
	// 这里我们只处理取消，如果子节点取消，必须把父节点都取消
	if (!treeNode.checked) {
		Utils.cancelParentNodeChecked('unitTree2', treeNode);
	}
}

function initUnitName() {
	$.fn.zTree.init($("#unitTree"), setting);
}
function initUnitName2() {
	$.fn.zTree.init($("#unitTree2"), setting3);
}

function getSelectedUnits() {
	var zTree = $.fn.zTree.getZTreeObj("unitTree");
	var changedNodes = zTree.getCheckedNodes(); 
	var ids="";
	var texts="";
	for ( var i=0 ; i < changedNodes.length ; i++ ){ 
	    var treeNode = changedNodes[i];
	    if (ids == "") ids = treeNode.id;
	    else ids = ids+","+treeNode.id;
	    if (texts == "") texts = treeNode.text;
	    else texts = texts+","+treeNode.text;
	    
	}
    
	$("#unit").val(ids);
	$("#unitName").val(texts);
}

function getSelectedUnits2() {
	var zTree = $.fn.zTree.getZTreeObj("unitTree2");
	var changedNodes = zTree.getCheckedNodes(); 
	var ids="";
	var texts="";
	for ( var i=0 ; i < changedNodes.length ; i++ ){ 
	    var treeNode = changedNodes[i];
	    if (ids == "") ids = treeNode.id;
	    else ids = ids+","+treeNode.id;
	    if (texts == "") texts = treeNode.text;
	    else texts = texts+","+treeNode.text;
	    
	}
    
	$("#unit2").val(ids);
	$("#unitName2").val(texts);
}

function pickupUnit() {
	var top = $("#unitName").position().top;  
	var left = $("#unitName").position().left;  
	$("#unitTreeDiv").css({position: "absolute",'top': top + 20, 'left': left, 'z-index':999});   
	$("#unitTreeDiv").show();
	$("#unitKeyword").focus();
}

function pickupUnit2() {
	var top = $("#unitName2").position().top;  
	var left = $("#unitName2").position().left;  
	$("#unitTreeDiv2").css({position: "absolute",'top': top + 20, 'left': left, 'z-index':999});   
	$("#unitTreeDiv2").show();
	$("#unitKeyword2").focus();
}


//function leaderAnalysis() {
//	var analysisAll = $("#chk_printAll").attr('checked');
//	
//	var cond = $("#mc").data("simpleCondition");
//	var personcodes = "";
//	if(!analysisAll){
//		var rows = $("#mc").datagrid("getSelections");
//			$.each(rows,function(index,row){
//			    if (personcodes=="") personcodes += row.personcode;
//			    else personcodes +=","+row.personcode;
//			  });
//		}
//	var url = System.rootPath+"/analysis/leader/leader-analysis!simpleAnalysis.action?cond="+cond+"&personcodes="+personcodes;
//	window.open(url);
//	$("#mc").datagrid("clearSelections");
//}
function printData(){
	var printAll = $("#chk_printAll").attr('checked');
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

//function PrintAll(){
//	$.messager.confirm('提示','是否打印全部?',function(b){
//		if(b){
//			var d = new Object();
//			d.serviceid = $("#serviceid").val();
//			d.simpleCondition = $("#mc").data("simpleCondition");
//			d.queryType = 5;
//			$.ajax({
//				url:System.rootPath+'/common/exec-common-method!createPrintSessionkey.action',
//				data:d,
//				success:function(sessionkey){
//					System.report.printAll(sessionkey);
//				},
//				error:function(){
//					$.messager.alert('错误','网络错误!','error');
//				},
//				complete:function(){}
//			});
//		}
//	});
//}

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
	var condition = getAllCondition();
	var options = $("#mc").datagrid('options');
	var queryParam = options.queryParams;
	options.pageNumber = parseInt($("#page").val());
	queryParam.serviceid = $("#serviceid").val();
	queryParam.transferCondition = encodeURI($.toJSON(condition));
	options.url=System.rootPath+"/transfer/transfer!query.action";
	$("#mc").datagrid(options);
	$("#mc").data("transferCondition",queryParam.transferCondition);
}

function getAllCondition(){
	var condition = new Condition();
	condition.xingm = $("#xingm").val();
	condition.unit = $("#unit").val();
	condition.status = $("input[name='status']:checked").val();
	condition.leix = $("input[name='leix']:checked").val();
	return condition;
}

function Condition(){
	this.xingm;
	this.unit;
	this.status;
	this.leix;
	
}
