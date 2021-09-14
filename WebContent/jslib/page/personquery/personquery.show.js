var g_filterPanelShowing = false;
var g_tables = '';
var g_sql = '';

$(function(){
	$(".panel-title").css("text-align","center");
	
	init();
});

function init() {
	$("#mc").datagrid({
		fit: true,
		border: false,
		rownumbers:true,
		singleSelect:false,
		pagination:true, 
		nowrap: false,
		pageSize : 20,
		idField:'id',
		sortName:'xingm',
		sortOrder:'asc',
		frozenColumns: PERSON_FROZEN_COLUMNS,
		columns: PERSON_COLUMNS,
		toolbar: [{
			id:'btn_printAll',
			text:'<input type=\"checkbox\" id=\"chk_printAll\"/> 打印全部',
		    handler:function(){
				var rows = $("#mc").datagrid('getRows');
				if(rows.length==0){
					$("#chk_printAll").attr('checked', false);
					$.messager.alert('提示', '没有数据打印!');
				}
			}
		}, {
			id: 'print'
		},{
			iconCls: 'icon-filter',
			text:'添加筛选条件',
		    handler:function(){
				if ($("#queryType").val() == '1') {
					System.showErrorMsg('当前查询为快照查询，无法添加筛选条件！');
					return;
				}
				
				if (g_filterPanelShowing) {
					g_filterPanelShowing = false;
					$("#mainLayout").layout('setRegionSize', { region: 'north', value: 1 });
					$("#condTable").hide();
				}
				else {
					g_filterPanelShowing = true;
					$("#mainLayout").layout('setRegionSize', { region: 'north', value: 160 });
					$("#condTable").show();
				}
				return;
			}
		},{
			iconCls: 'icon-refresh',
			text:'重置筛选条件',
		    handler:function(){
				if ($("#queryType").val() == '1') {
					return;
				}
				
				onReset();
			}
		},{
			iconCls: 'icon-save',
			text:'保存',
		    handler:function(){
				var query = { 
					category_id: $("#queryCategoryId").val(), 
					category_name: $("#queryCategoryName").val(),
					query_id: $("#queryId").val(), 
					query_type: $("#queryType").val(), 
					query_name: $("#queryName").val(),
					query_remarks: $("#queryRemarks").val(),
					query_tables: g_tables, 
					query_sql: g_sql
				};
				var ctrl = new PersonQuery(query, onSaved);
				ctrl.show();
			}
		},{
			iconCls: 'icon-save',
			text:'另存为',
		    handler:function(){
				if ($("#queryType").val() == '1') {
					System.showErrorMsg('当前查询为快照查询，无法另存为！');
					return;
				}
				
				var query = { 
					category_id: '', 
					category_name: '',
					query_id: '', 
					query_type: 0, 
					query_name: '',
					query_remarks: '',
					query_tables: g_tables, 
					query_sql: g_sql
				};
				var ctrl = new PersonQuery(query, onSaved);
				ctrl.show();
			}
		},{
			iconCls: 'icon-send',
			text:'发送',
		    handler:function(){
				var options = {
					title: $("#queryName").val(),
					content: $("#queryRemarks").val(),
					url: System.rootPath + '/query/personquery/person-query!preparePushData.action?queryId=' + $("#queryId").val()
				};
				
				var ctrl = new PlatPush(options);
				ctrl.show();
			}
		},{
			iconCls: 'icon-fav',
			text:'收藏',
		    handler:function(){
				var options = {
					title: $("#queryName").val(),
					url: System.rootPath + '/query/personquery/person-query!preparePushData.action?queryId=' + $("#queryId").val()
				};
				
				var ctrl = new PlatFav(options);
				ctrl.addFav();
			}
		},{
			iconCls: 'icon-remove',
			text:'删除',
		    handler:function(){
				onDelete();
			}
		}],
		onLoadSuccess:function(data){
			$("#mc").datagrid('clearSelections');
			setTimeout(function(){
				//if ($("#mm").length == 0) {
					System.report.menu("#print", printData, false, false);
				//}
			},10);
		}
	});
	
	if ($("#queryId").val() != '') {
		doQuery($("#tables").val(), $("#sql").val());
	}
}

function doQuery(tables, sql) {
	// 获取当前查询参数
	var url = System.rootPath + '/query/personquery/person-query!queryPersons.action';
	var opts = $("#mc").datagrid('options');
	var params = {
		tables: tables,
		sql: sql,
		queryId: $("#queryId").val()
	};
	
	g_tables = tables;
	g_sql = sql;
	
	opts.url = url;
	opts.queryParams = params;
	$("#mc").datagrid(opts);
}

function getSelectedPersons() {
	var personcodes = '';
	var rows = $("#mc").datagrid("getSelections");
	if (rows) {
		$(rows).each(function(index, row){
			if (personcodes == '') personcodes = row.personcode;
			else personcodes = personcodes + "," + row.personcode;
		});
	}
	
	return personcodes;
}

function printData(){
	var isPrintAll = $("#chk_printAll").is(':checked'); // jQuery 1.6以上，不能用attr获取checked状态
	if (isPrintAll) {
		printAll();
	} else{
		var personcodes = getSelectedPersons();
		if (personcodes != '') {
			System.report.print(personcodes);
		}
	}
}

function printAll(){
	$.messager.confirm('提示','是否打印全部?',function(b){
		if(b){
			var d = new Object();
			d.serviceid = $("#serviceid").val();
			d.simpleCondition = $("#mc").data("simpleCondition");
			d.queryType = 5;
			$.ajax({
				url:System.rootPath+'/common/exec-common-method!createPrintSessionkey.action',
				data:d,
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

function Condition(){
	this.xingm;
	this.sex;
	this.age1;
	this.age2;
	this.age3;
	this.age4;
	this.zj = []; 
	this.zjYear1;
	this.zjYear2;
	this.zjYear3;
	this.zjYear4;
	this.mingz; 
	this.zzmm;
	this.zzYear1;
	this.zzYear2;
	this.zzYear3;
	this.zzYear4;
	this.gzYear1;
	this.gzYear2;
	this.gzYear3;
	this.gzYear4;
	this.xuelxuew = [];
	this.xuelType;
	this.xuewType;
	//this.rzqk;
	this.zyjzc;
	
	this.xzYear1;
	this.xzYear2;
	this.xzYear3;
	this.xzYear4;
	
	this.sxzy;
	this.bmjl;
	this.gzjl;
	this.zwjl;
}


function getAllCondition(){
	var condition = new Condition();
	condition.xingm = '';	
	condition.sex = $("input[name='sex']:checked").val();
	condition.mingz = $("input[name='mingz']:checked").val();
	condition.zzmm = $("input[name='zzmm']:checked").val();
	condition.zyjzc = '';
	
	condition.age1 = $("#age1").val();
	condition.age2 = $("#age2").val();
	condition.age3 = $("#age3").val();
	condition.age4 = $("#age4").val();
	
	var zj = [];
	$.each($("input[name='zj']:checked"),function(i,item){
		zj.push($(item).val());
    });
	condition.zj = zj;
	
	condition.zzYear1 = $("#zzYear1").val();
	condition.zzYear2 = $("#zzYear2").val();
	condition.zzYear3 = $("#zzYear3").val();
	condition.zzYear4 = $("#zzYear4").val();
	
	condition.gzYear1 = $("#gzYear1").val();
	condition.gzYear2 = $("#gzYear2").val();
	condition.gzYear3 = $("#gzYear3").val();
	condition.gzYear4 = $("#gzYear4").val();
	
	condition.zjYear1 = $("#zjYear1").val();
	condition.zjYear2 = $("#zjYear2").val();
	condition.zjYear3 = $("#zjYear3").val();
	condition.zjYear4 = $("#zjYear4").val();
	
	condition.xzYear1 = $("#xzYear1").val();
	condition.xzYear2 = $("#xzYear2").val();
	condition.xzYear3 = $("#xzYear3").val();
	condition.xzYear4 = $("#xzYear4").val();
	
	
	var xuelxuew = [];
	$.each($("input[name='xuelxuew']:checked"),function(i,item){
		xuelxuew.push($(item).val());
	});
	condition.xuelxuew = xuelxuew;
	condition.xuelType = $("#xuelType").val();
	condition.xuewType = $("#xuewType").val();
		
	//condition.rzqk = $("#rzqk").val();
	condition.sxzy = '';
	condition.gzjl = '';
	condition.bmjl = '';
	condition.zwjl = '';
	return condition;
}

function onDelete() {
	System.showConfirmMsg('确定删除此查询？', function() {
		var url = System.rootPath + '/query/personquery/person-query!deleteQuery.action';
		System.post(url, { queryId: $("#queryId").val() }, function(data) {
			window.location.href = System.rootPath + '/query/personquery/person-query!queryDeleted.action';
		});
	});
}

function onSaved(query) {
	
	if ($("#queryId").val() == '') {
		var url = System.rootPath + '/query/personquery/person-query!showQuery.action?queryId=' + query.query_id;
		window.location.href = url;
	}
	else {
		$("#queryCategoryName").val(query.category_name);
		$("#queryName").val(query.query_name);
		$("#queryId").val(query.query_id);
		$("#queryCategoryId").val(query.category_id);
		$("#queryType").val(query.query_type);
		$("#queryHeader").html(query.query_name);
	}
}