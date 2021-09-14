
$(document).ready(function(){
	$("#mc").datagrid({
		url:System.rootPath + '/train/train!search.action',
		fit: true,
		rownumbers:true,
		singleSelect:true,
		pagination:true, 
		pageSize : 20,
		idField:'id',
		sortOrder:'asc',
		//frozenColumns: PERSON_FROZEN_COLUMNS,
		columns: TRAIN_MANAGE_COLUMNS,
		onDblClickRow: function(index, row) {
			openDialog(row);
		},
		onLoadSuccess: function(data) {
			$("#mc").datagrid('clearSelections');
		}
	});
});

function onSearch() {
	var params = $("#paramForm").serializeJSON(); 
	var opts = $("#mc").datagrid('options');
	
	opts.url = System.rootPath + '/train/train!search.action';
	opts.queryParams = params;
	$("#mc").datagrid(opts);
}

function onReset() {
	$("#paramForm").form('reset');
}

function onAdd() {
	openNewDialog(null);
}

function openNewDialog(data) {
	$("#newTrainDlg").show();
	$('#newTrainDlg').dialog({
		title: '新增培训信息',
		width: 900,
		height: 250,
		modal: true,
		buttons: getDialogButtons(data),
		onOpen : function () {
			$("#newTrainDlgForm").form('reset');
			//$("#newTrainBclb").val($("#newTrainBclb").combotree('getValue'));
		},
		onClose: function() {
			$("#mc").datagrid('reload');
		}
	});
}

function openDialog(data) {
	$("#trainDlg").show();
	$('#trainDlg').dialog({
		title: '编辑培训信息',
		width: 900,
		height: 500,
		modal: true,
		buttons: getDialogButtons(data),
		onOpen : function () {
			initPersonList(data);
		    $("#trainDlgForm").form('load', Utils.cloneObject(data, 'train'));
		    //$("#trainBclb").val($("#trainBclb").combotree('getValue'));
			$("#trainBclbShow").val($("#trainBclb").combotree('getText'));
		},
		onClose: function() {
			$("#mc").datagrid('reload');
		}
	});
}

function getDialogButtons(data){
	var buttons = [];
	
	if (data == null) {
		buttons.push({
			iconCls:'icon-save',
			text:'保存',
			handler:function(){
				onSave(false);
			}
		});
		return buttons;
	}else {
	
			buttons.push({
				iconCls:'icon-save',
				text:'保存',
				handler:function(){
					onSave(true);
				}
			},{
				iconCls:'icon-no',
				text:'删除',
				handler:function(){
					onDelete(data);
				}
			},{
				iconCls:'icon-update',
				text:'同步到培训信息集',
				handler:function(){
					gotoA010();
				}
			});
		
	}
	buttons.push({
		iconCls:'icon-cancel',
		text:'关闭',
		handler:function(){
			$("#trainDlg").dialog('close');
		}
	});
	return buttons;
}

//获取培训干部列表
function initPersonList(row){
	$("#personList").datagrid({
		url:System.rootPath + '/train/train!getPersonList.action',
		queryParams: row == null ? {id:0} : {id:row.id},
		fit: true,
		singleSelect: true,
		border: true,
		nowrap: false,
		idField: 'id',
		toolbar:[{
			iconCls:'icon-add',
			text:'新增干部',
			handler:function(){
				if ($("#trainId").val()=='') {
					System.showErrorMsg('请先保存培训信息');
				}else {
					top.System.Meeting.ok = "0";
					System.Meeting.pickUpPerson(function(selectedPersons) {
						if (top.System.Meeting.ok == "1") {
							if (top.System.Meeting.selectedPersons != null) {
								onSelectedPerson(top.System.Meeting.selectedPersons,row.id);
							}
						}
					});
				}
			}
		},{
			iconCls:'icon-remove',
			text:'删除干部',
			handler:function(){
				var row = $("#personList").datagrid('getSelected');
				if (row) {
					System.showConfirmMsg('确定删除该干部？', function() {
						var rowIndex = $('#personList').datagrid('getRowIndex', row);
				         $('#personList').datagrid('deleteRow', rowIndex);
					});
				}
			}
		}],
		columns:[[
			{field:'name', title:'姓名', align:'center',sortable:true, width:60},
			{field:'position', title:'职务', sortable:true, width:340}
		]],
		onDblClickRow: function(index, row) {
			//ldpsDialog(row);
		},
		onLoadSuccess: function(data) {
			$("#personList").datagrid('clearSelections');
		}
	});
}

function onSave(update) {
	var url;
	if (!update) {
		url = System.rootPath + '/train/train!create.action';
		System.post(url, $("#newTrainDlgForm").serialize(), function(data) {
			if ($("#newTrainId").val()=='') {
				$("#newTrainId").val(data.object);
			}
			System.showInfoMsg('保存成功！');
		});
	}
	else {
		
		url = System.rootPath + '/train/train!update.action';
		var isValid = $("#trainDlgForm").form('validate');
		if (!isValid) {
			System.showErrorMsg('请输入必填项目！');
			return;
		}
		
		System.post(url, $("#trainDlgForm").serialize(), function(data) {
			if ($("#trainId").val()=='') {
				$("#trainId").val(data.object);
			}
			
			var urlDeletePerson = System.rootPath + '/train/train!deletePerson.action';
			System.post(urlDeletePerson, { 'id':$("#trainId").val()
	        }, function(data) {
	        });
			
			var urlPerson = System.rootPath + '/train/train!createPerson.action';
			var rows = $("#personList").datagrid('getRows');
			if (rows.length != 0) {
				$(rows).each(function(index, row){
					System.post(urlPerson, { 'trainPerson.training_id':row.training_id,
						               'trainPerson.personcode':row.personcode,
						               'trainPerson.name':row.name,
						               'trainPerson.position':row.position
						}, function(data) {
						
					});
				});
			}
			System.showInfoMsg('保存成功！');
		});
	}
}

function gotoA010() {
	var url = System.rootPath + '/common/batch-operation!savePxtoA010.action';
	var param = $("#trainDlgForm").serialize()+'&'+$.param({'personJsons':encodeURI(encodeURI(getSPersonsMap()))});
	 $.ajax({
         type: 'post',
         url: url,
         data: param,
         success: function(json) {
             if (json.status) {
            	 System.showInfoMsg('同步成功！');
             } else {
                 $.messager.alert("网络错误", "出现错误了(>_<)，稍后再试试!", "error");
             }
         },
         error: function(XMLHttpRequest, textStatus, errorThrown) {
             $.messager.alert("网络错误", "出现错误了(>_<)，稍后再试试!", "error");
         }
     });
};

function getSPersonsMap() {
	var map = [];
	var rows = $("#personList").datagrid("getRows");
	if (rows) {
		$(rows).each(function(index, row){
			var o = new Object();
			o.personcode = row.personcode;
			o.name = row.name;
			map.push(o);
		});
	}
	
	return $.toJSON(map);
}

function onSelectedPerson(selectedPerson,trainId) {
	//System.openLoadMask($('#meetingPanel'), '正在添加干部，请稍后...');
	
	//$("#personWindow").window('close');
	// 移除已添加的
	var newPersons = new Array();
	var currentSelectedPerson = $("#personList").datagrid('getRows');
	if (currentSelectedPerson == null || currentSelectedPerson.length == 0) {
		var index = 0;
		for (var i = 0; i < selectedPerson.length; i++) {
			newPersons[index++] = convertPerson(selectedPerson[i],trainId);
		}
	}
	else {
		var index = 0;
		for (var i = 0; i < selectedPerson.length; i++) {
			var isNewPerson = true;
			for (var j = 0; j < currentSelectedPerson.length; j++) {
				if (currentSelectedPerson[j].personcode == selectedPerson[i].personcode) {
					isNewPerson = false;
					break;
				}
			}
			
			if (isNewPerson) {
				newPersons[index++] = convertPerson(selectedPerson[i],trainId);
			}
		}
	}
	
	// 提高性能，直接reload数据, by YZQ on 2013/06/15
	var newRowData = newPersons.concat(currentSelectedPerson);
	/*for (var i = 0; i < newRowData.length; i++) {
		newRowData[i].sorder = i + 1;
	}*/
	
	$('#personList').datagrid('loadData', {total:newRowData.length, rows: newRowData});
}

function convertPerson(fromPersonSelection,trainId) {
	var person = {
			        training_id: trainId,
					personcode: getSafeData(fromPersonSelection.personcode),
					//sorder: '1',
					name: getSafeData(fromPersonSelection.xingm),
					position: getSafeData(fromPersonSelection.zhiw)
				};
	return person;
}

function getSafeData(value) {
	if (value == null || value == 'undefined') return '';
	else return value;
}

function onDelete(data) {
	System.showConfirmMsg("确定删除<span style='color:red'>"+data.class_name+"</span>培训班信息吗？", function() {
		var url = System.rootPath + '/train/train!delete.action';
		System.post(url,{'id':data.id}, function(data) {
			System.showInfoMsg('删除成功！');
		});
	});
}

function detail(personcode, name) {
    $("#personcode").val(personcode);
	System.commonEditor.editorSingePerson(personcode);
}

function exportByCondition(){
	simpleCondition = $("#mc").data("simpleCondition");
	var url = System.rootPath+"/pxfx/stati-analysis!exportBySimpleCondition.action?simpleCondition="+simpleCondition;
	window.open(url);
}
