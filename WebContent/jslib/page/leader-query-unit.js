function execQuery(){
	clearSelect();
	var options = $("#mc").datagrid('options');
	var queryParam = options.queryParams;
	options.pageNumber = 1;
	queryParam.serviceid = $("#serviceid").val();
	queryParam.currentUnit = $("#currentUnit").val();
	//queryParam.rows = 999999;
	options.url=System.rootPath+"/mingc/mingc!queryByUnit.action";
	$("#mc").datagrid(options);	
	addPrintButton();
}

function gotoPage(pageno) {
    var pageno = parseInt(pageno, 10);
    if (isNaN(pageno)) {
        pageno = 1
    } else {
        if (pageno <= 0) {
            pageno = 1
        }
    }
    $("#page").val(pageno);
	var options = $("#mc").datagrid('options');
	options.pageNumber = pageno;
    options.url=System.rootPath+"/mingc/mingc!queryByUnit.action";
	$("#mc").datagrid(options);
	addPrintButton();
}

$(document).ready(function(){
	$("#mc").datagrid({
		fit: true,
		nowrap: false,
		rownumbers:true,
		singleSelect:false,
		pagination:true, 
		pageSize : 20,
		pageList: [20, 30, 50],
		idField:'id',
		frozenColumns: PERSON_FROZEN_COLUMNS_WITH_CHECKBOX_2,
		columns: PERSON_COLUMNS,
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
	    	
	    },
		onLoadSuccess:function(data){
			$("#mc").datagrid('unselectAll');
			var options = $("#mc").datagrid('options');
			$("#page").val(options.pageNumber);
			$("#rows").val(options.pageSize);
			
			renderPhotoList(data);
		}
	});
	
	addPrintButton();
});

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

function printData(){
	var personcodes = getSelectedPersons();
	if (personcodes != '') {
		System.report.print(personcodes);
	}
}
function PrintAll(){
	$.messager.confirm('提示','是否打印全部?',function(b){
		if(b){
			$.ajax({
				url:System.rootPath+'/common/exec-common-method!createPrintSessionkey.action',
				data:{'serviceid':$("#serviceid").val(),'currentUnit':$("#currentUnit").val(),'queryType':1},
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

function renderPhotoList(data) {
	//alert("render phtoto Count: " + data.rows.length);
	$("#personPhotoList").empty();
	$(".pageFoot").show();
	
    if (data.rows && data.rows.length == 0) {
		$("#pageDataRows").show();
        $("#pageDataRows").html('没有数据！');
    } else {
		$("#pageDataRows").hide();
        var dataRowsHtml = "<ul class='image-grid'>";
        $.each(data.rows, 
        function(i, v) {
			/*
            var desc = "&nbsp;&nbsp;<a href=\"javascript:void(0);\" onclick=\"detail(" + v.personcode + ",'" + v.xingm + "');\">" + v.xingm + "</a>，" + v.sex + "，" + v.minz + "，" + v.birthday + "出生于" + v.chusd + "，籍贯:" + v.jig + "。";
            desc += v.gongzsj + "参加工作," + v.rudsj + "加入" + v.zzmm + ",现任" + v.zhiw + "。<br/>";
            desc = desc.replaceAll("undefined", "");
            dataRowsHtml += "<tr>";
            dataRowsHtml += "<td width=\"12%\" height=\"100\" valign=\"middle\" align=\"center\" class=\"line_b\"><span><img onclick=\"detail(" + v.personcode + ",'" + v.xingm + "');\" src=\"" + System.rootPath + "/lob/photo.action?personcode=" + v.personcode + "\" width=\"80\" height=\"100\" /></span></td>";
            dataRowsHtml += "<td width=\"88%\" class=\"line_b\" valign=\"top\">" + desc + "</td>";
            dataRowsHtml += "</tr>";
            if (i == (data.rows.length - 1)) {
                $("#pageDataRows").empty().append(dataRowsHtml)
            }
			*/
			
			var desc = "<li>";
			desc += "<a href=\"javascript:void(0);\" onclick=\"System.leaderQuery.showRemoval(" + v.personcode + ");\">";
			desc += "<img border=\"0\" src=\"" + System.rootPath + "/lob/photo.action?rand=" + Math.random() + "&personcode=" + v.personcode + "&zoom=true&width=100&height=133\" width=\"100\" height=\"133\" />";
			desc += "</a>";
			desc += "<span>" + v.xingm;
			if (v.sexCode == '2') {
				desc += "(女)";
			}
			
			desc += "<br/>";
			if (v.zhiw) desc += v.zhiw;
			desc += "</span>";
			desc += "</li>";
			dataRowsHtml += desc;
        })
		
		dataRowsHtml += '</ul>';		
		$("#personPhotoList").empty().append(dataRowsHtml);
    }
    var pageNo = parseInt($("#page").val(), 10);
    var pageSize = parseInt($("#rows").val(), 10);
	
	$("#pageNo").html(pageNo);
    $("#pageFirst").html(parseInt(((pageNo - 1) * pageSize) + 1, 10));
    $("#pageEnd").html(pageNo * pageSize);
    $("#totalCount").html(data.total);
	
    var totalPage = parseInt(data.total / pageSize, 10);
    if (data.total % pageSize) {
        totalPage += 1
    }
	$("#totalPages").html(totalPage);
    var footHtml = "<a href=\"javascript:void(0);\" class=\"page_btn blacklink\" onclick=\"gotoPage(1);\">[首 页]</a>";
    if (pageNo - 1 >= 1) {
        footHtml += "<a href=\"javascript:void(0);\" class=\"page_btn blacklink\" onclick=\"gotoPage(" + (pageNo > 1 ? (pageNo - 1) : 1) + ");\">[上一页]</a>"
    }
    if (pageNo + 1 <= totalPage) {
        footHtml += "<a href=\"javascript:void(0);\" class=\"page_btn blacklink\" onclick=\"gotoPage(" + ((pageNo + 1) > totalPage ? totalPage: (pageNo + 1)) + ");\">[下一页]</a>"
    }
    footHtml += "<a href=\"javascript:void(0);\" class=\"page_btn blacklink\" onclick=\"gotoPage(" + totalPage + ");\">[尾 页]</a>";
	
	//var footHtml = "总共有" + data.total + "条记录";
    $("#pageDataFoot").empty().append(footHtml);
	//$("#pageDataFootMc").empty().append(footHtml);
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