$(function(){
	$("#mc").datagrid({
		rownumbers:true,
		nowrap: true,
		fit: true,
		pagination:false, 
		idField:'id',
		sortName:'xingm',
		sortOrder:'asc',
		frozenColumns: PERSON_FROZEN_COLUMNS,
		columns: PERSON_COLUMNS,
		onLoadSuccess:function(data){
			$('#mc').datagrid('fixRowHeight');  
		}
	});
	
	$("#keyword").keyup(function() {
		if(event.keyCode == 13){
			onSearch();
		}
	});
	
	// 初始化打印按钮
	System.report.menu("#btnPrint", printData, false,false);
	
	$("#keyword").focus();
	
	if (g_autoSearch == 'true') {
		onSearch();
	}
});

function printData() {
	var personcodes = '';
	var rows = $("#mc").datagrid("getSelections");
	if (rows) {
		$(rows).each(function(index, row){
			if (personcodes == '') personcodes = row.personcode;
			else personcodes = personcodes + "," + row.personcode;
		});
	}
	
	if (personcodes != '') {
		System.report.print(personcodes);
	}
}

function onSearch() {
	var value = $("#keyword").val();
	if (value.length == 0) {
		System.showErrorMsg('请输入搜索关键字！');
		return;
	}
	
	doSearch(value, 1);
}

function doSearch(keyword, pageNo) {
	System.openLoadMask('#w', '正在执行搜索，请耐心等待...');
	var url = System.rootPath + '/fulltext/full-text-person-query!searchIndex.action';
	$.post(url, 
		{
			keyword: keyword,
			page: pageNo,
			rows: 10
		},
		function(result) {
			System.closeLoadMask('#w');
			if (result.error_msg != '') {
				if (result.error_msg.indexOf('索引不存在') >= 0) {
					initIndex();
				}
				else {
					System.showErrorMsg('无法执行搜索，错误信息：' + result.error_msg);
				}
			}
			else {
				renderPhoto(result);
			}
		}
	);
}

function initIndex() {
	System.openLoadMask('#w', '初次搜索，正在创建索引，可能需要几分钟时间，请耐心等待...');
	var url = System.rootPath + '/fulltext/full-text-person-query!buildIndex.action';
	$.post(url, 
		{
		},
		function(msg) {
			System.closeLoadMask('#w');
			if (msg != null && msg != '') {
				if (msg.length > 500) msg = msg.substring(0, 500);
				System.showErrorMsg('无法创建索引，错误信息：' + msg);
			}
			else {
				doSearch($("#keyword").val(), 1);
			}
		}
	);
}

function renderPhoto(result) {
	$("#photo").empty();
	
	var html = "";
	var data = [];
	
	if (result.score_docs && result.score_docs.length > 0) {
		$.each(result.score_docs, function(index, value) {
			data.push(value.mc);
			
			html += "<tr><td class='photo'>";
			
			var photoUrl = System.rootPath + "/lob/photo.action?rand=" + Math.random() + "&zoom=true&width=80&height=100&personcode=" + value.term_id;
			var url = "<a href='javascript:void(0)' onclick='System.leaderQuery.showRemoval(" + value.term_id + ");'>";
			url += "<img src='" + photoUrl + " width='80' height='100'></a>";
								
			html += url;
			html += "</td><td class='content'>" + value.text + "</td></tr>";
		});
	}
	else {
		html = "<tr><td class='content' colspan='2' style='text-align:center;'>未找到任何符合条件人员！</td></tr>";
	}
	
	$("#photo").append(html);
	
	// 更新页脚
	var pageNo = result.page_no;
	var pageSize = result.page_size;
	var totalCount = result.total_count;
	var totalPage = parseInt(totalCount / pageSize);
	if (totalCount * pageSize != totalCount) {
		totalPage++;
	}
	
	if (totalCount == 0) {
		$("#currentPage").val("0");
		$("#totalPageLabel").html("0");
		$("#pageMsg").html("共0记录，搜索共耗时" + (result.spend_time) + "毫秒");
	}
	else {
		var msg = "显示" + (pageNo  * pageSize - pageSize + 1) + "到" + (pageNo * pageSize) + "记录";
		msg += "，共" + totalCount + "记录";
		msg += "，搜索耗时" + (result.spend_time) + "毫秒";
		$("#currentPage").val(pageNo);
		$("#totalPageLabel").html(totalPage);
		$("#pageMsg").html(msg);
	}
	
	$("#totalPage").val(totalPage);
	
	// 切换按钮
	toggleButton("#btnPageFirst", pageNo != 1);
	toggleButton("#btnPageLast", pageNo != totalPage);
	toggleButton("#btnPagePrev", pageNo > 1);
	toggleButton("#btnPageNext", pageNo < totalPage);
	
	// 载入表格
	$("#mc").datagrid('loadData', data);
}

function toggleButton(btnId, enabled) {
	if (enabled) {
		$(btnId).removeClass('l-btn-disabled');
		$(btnId).removeClass('l-btn-plain-disabled');
	}
	else {
		$(btnId).addClass('l-btn-disabled');
		$(btnId).addClass('l-btn-plain-disabled');
	}
}

function onPageFirst() {
	doSearch($("#keyword").val(), 1);
}

function onPageLast() {
	doSearch($("#keyword").val(), parseInt($("#totalPage").val()));
}

function onPagePrev() {
	doSearch($("#keyword").val(), parseInt($("#currentPage").val()) - 1);
}

function onPageNext() {
	doSearch($("#keyword").val(), parseInt($("#currentPage").val()) + 1);
}

function onPageReload() {
	doSearch($("#keyword").val(), parseInt($("#currentPage").val()));
}

function addPrintButton() {
	// 在页脚添加分页
	var pager = $('#mc').datagrid().datagrid('getPager');	// get the pager of datagrid
	pager.pagination({
		buttons:[{
			id: 'print'
		}]
	});
	
	System.report.menu("#print", printData,false,false);
}
