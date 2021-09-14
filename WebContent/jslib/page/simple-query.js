
$(document).ready(function(){
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
		frozenColumns: PERSON_FROZEN_COLUMNS,
		columns: PERSON_COLUMNS,
		toolbar: [{
			id:'btn_printAll',
			text:'<input type=\"checkbox\" id=\"chk_printAll\"/> 打印全部',
		    handler:function(){
				var rows = $("#mc").datagrid('getRows');
				if(rows.length==0){
					$("#chk_printAll").attr('checked', false);
					$.messager.alert('提示','没有数据打印!');
				}
			}
		}, {
			id: 'print'
		},{
			id: 'xxx'
		}],
		onLoadSuccess:function(data){
			addmenu("#xxx");
			$("#mc").datagrid('clearSelections');
			setTimeout(function(){
				//if ($("#mm").length == 0) {
					System.report.menu("#print", printData, false, false);
				//}
			},10);
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
	//$("#listPanel").show().siblings().hide();
	//$("#queryTab").tabs('select', 1);
	
	//$("#mc").datagrid();
	var condition = getAllCondition();
	var options = $("#mc").datagrid('options');
	var queryParam = options.queryParams;
	options.pageNumber = parseInt($("#page").val());
	queryParam.serviceid = $("#serviceid").val();
	queryParam.simpleCondition = encodeURI($.toJSON(condition));
	options.url=System.rootPath+"/leader/leader-query!searchBySimpleCondition.action";
	$("#mc").datagrid(options);
	$("#mc").data("simpleCondition",queryParam.simpleCondition);
}

function getAllCondition(){
	var condition = new Condition();
	condition.xingm = $("#xingm").val();	
	condition.sex = $("input[name='sex']:checked").val();
	condition.mingz = $("input[name='mingz']:checked").val();
	condition.zzmm = $("input[name='zzmm']:checked").val();
	condition.zyjzc = $("#zyjzc").val();
	
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
	
	condition.jg = $("#jg").val();
	condition.zhiw = $("#zhiw").val();
	condition.zc = $("#zc").val();
	condition.jl1 = $("#jl1").val();
	condition.jlopt1 = $("input[name='jlopt1']:checked").val();
	
	condition.jl2 = $("#jl2").val();
	condition.jlopt2 = $("input[name='jlopt2']:checked").val();
	
	condition.dw = $("#dw").val();
	condition.dwfl = $("#dwfl").val();
	var zwlb = [];
	$.each($("input[name='zwlb']:checked"),function(i,item){
		zwlb.push($(item).val());
    });
	condition.zwlb = zwlb;
	condition.nlsj = $("#nlsj").val();
	condition.zjsj = $("#zjsj").val();
	condition.zwsj = $("#zwsj").val();
	
	return condition;
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
	
	this.jg;
	this.zhiw;
	this.zc;
	this.jl1;
	this.jlopt1;
	this.jl2;
	this.jlopt2;
	this.dw = '';
	this.dwfl = '';
	this.ldzw = [];
	this.nlsj;
	this.zjsj;
	this.zwsj;
}

function onSave() {
	var data = $("#mc").datagrid('getData');
	if (data.rows.length == 0) {
		System.showErrorMsg('你还没有执行查询，或者当前查询没有任何结果，无法保存！');
		return;
	}
	
	var condition = getAllCondition();
	
	var url = System.rootPath + '/leader/leader-query!getSimpleConditionQuery.action';
	System.post(url, { 
		simpleCondition: encodeURI($.toJSON(condition)) 
		}, 
		function(data) {
			if (data.attributes.sql == '') {
				System.showErrorMsg('无法获取当前查询条件，无法保存！');
				return;
			}
			
			var query = { 
				catgory_id: '', 
				query_id: '', 
				query_type: 0, 
				query_tables: data.attributes.tables, 
				query_sql: data.attributes.sql 
			};
			var ctrl = new PersonQuery(query);
			ctrl.show();
	});
}
