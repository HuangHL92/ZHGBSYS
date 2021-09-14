var g_canAddPerson = false; // 常量，是否可以添加人员
var g_canAddPx = false; // 是否可以批量添加培训信息
var g_canAddKh = false; // 是否可以批量添加考核信息

$(document).ready(function() {
    $("#mc").datagrid({
        //height: 424,		
        //width: $('body').outerWidth(true) - 340,
		fit: true,
        rownumbers: true,
        singleSelect: false,
		nowrap: false,
		//pagination: true,
        //pageSize: 999999,
        //pageList: [10, 20, 30],
        idField: 'id',
        sortOrder: 'asc',
        frozenColumns: [[{
            field: 'id',
            hidden: true
        },
		
        {
            field: 'xingm',
            title: '姓名',
            align: "center",
            width: 60,
            formatter: function(value, rowData, rowIndex) {
                var sexCode = rowData.sexCode;
                if (sexCode == "2") {
					return value == undefined ? "": "<a href=\"javascript:void(0);\" onclick=\"detail(" + rowData.personcode + ",'" + Utils.toVarString(rowData.xingm) + "');\">" + value + "</a>" + "<br/>(女)"
                } else {
                    return value == undefined ? "": "<a href=\"javascript:void(0);\" onclick=\"detail(" + rowData.personcode + ",'" + Utils.toVarString(rowData.xingm) + "');\">" + value + "</a>"
                }
            }
        }]],
        columns: [[{
            field: 'birthday',
            title: '出生年月',
            align: "center",
            //rowspan: 2,
            width: 80,
            resizable: true
        },{
            field: 'zhiw',
            title: '职务',
            align: "left",
            //rowspan: 2,
            width: 400,
            resizable: true
        },
		/*
        {
            field: 'minz',
            title: '民族',
            align: "center",
            rowspan: 2,
            width: 60,
            resizable: true
        },
        {
            field: 'birthday',
            title: '出生年月',
            align: "center",
            rowspan: 2,
            width: 80,
            resizable: true
        },
        {
            field: 'jig',
            title: '籍贯',
            rowspan: 2,
            align: "center",
            width: 80,
            resizable: true
        },
        {
            field: 'chusd',
            title: '出生地',
            rowspan: 2,
            align: "center",
            width: 80,
            resizable: true
        },
        {
            title: '全日制教育',
            colspan: 2,
            align: "center"
        },
        {
            title: '在职教育',
            colspan: 2,
            align: "center"
        },
        {
            field: 'gongzsj',
            title: '工作时间',
            rowspan: 2,
            width: 80
        },
        {
            field: 'rudsj',
            title: '入党时间',
            rowspan: 2,
            width: 80,
            formatter: function(value, rowData, rowIndex) {
                var zzmmCode = rowData.zzmmCode;
                if (zzmmCode != "01") {
                    if (rowData.zzmm != undefined && rowData.zzmm != "") {
                        return value + "<br/>(" + rowData.zzmm + ")"
                    } else {
                        return value == undefined ? "": value
                    }
                } else {
                    return value == undefined ? "": value
                }
            }
        },
		*/
        {
            field: 'xianjsj',
            title: '任现职级时间',
            //rowspan: 2,
            width: 80
        },
        {
            field: 'xianzsj',
            title: '任现职时间',
            //rowspan: 2,
            width: 70
        }/*,
        {
            field: 'remark',
            title: '备注',
            rowspan: 2,
            width: 150
        }*/]/*, [{
            field: 'qrzxl',
            title: '学历学位',
            width: 100,
            align: "center",
            formatter: function(value, rowData, rowIndex) {
                value = "";
                if (rowData.qrzxl != undefined && rowData.qrzxl != "") {
                    value += rowData.qrzxl
                }
                if (rowData.qrzxw != undefined && rowData.qrzxw != "") {
                    value += "<br/>" + rowData.qrzxw
                }
                return value
            }
        },
        {
            field: 'qrzbyyx',
            title: '毕业院校及专业',
            width: 120,
            align: "center",
            formatter: function(value, rowData, rowIndex) {
                value = "";
                if (rowData.qrzbyyx != undefined && rowData.qrzbyyx != "") {
                    value += rowData.qrzbyyx
                }
                if (rowData.qrzzy != undefined && rowData.qrzzy != "") {
                    value += "<br/>" + rowData.qrzzy
                }
                return value
            }
        },
        {
            field: 'zzxl',
            title: '学历学位',
            width: 100,
            align: "center",
            formatter: function(value, rowData, rowIndex) {
                value = "";
                if (rowData.zzxl != undefined && rowData.zzxl != "") {
                    value += rowData.zzxl
                }
                if (rowData.zzxw != undefined && rowData.zzxw != "") {
                    value += "<br/>" + rowData.zzxw
                }
                return value
            }
        },
        {
            field: 'zzbyyx',
            title: '毕业院校及专业',
            width: 120,
            align: "center",
            formatter: function(value, rowData, rowIndex) {
                value = "";
                if (rowData.zzbyyx != undefined && rowData.zzbyyx != "") {
                    value += rowData.zzbyyx
                }
                if (rowData.zzzy != undefined && rowData.zzzy != "") {
                    value += "<br/>" + rowData.zzzy
                }
                return value
            }
        }]*/],
        onSelect: function(rowIndex, rowData) {
            var obj = $("#printData").data("pageSelects");
            if (obj == undefined) {
                obj = []
            }
            var pageNo = $("#page").val();
            var pageSelect = System.getElement(obj, "pageNo", pageNo);
            if (pageSelect == undefined) {
                pageSelect = new PageSelect();
                pageSelect.pageNo = pageNo;
                obj.push(pageSelect)
            }
            var selectRow = new SelectRow();
            selectRow.rowIndex = rowIndex;
            selectRow.keywordId = rowData.personcode;
            selectRow.keywordcn = rowData.xingm;
            pageSelect.selecteds.push(selectRow);
            $("#printData").data("pageSelects", obj);
			
			checkPersonPermission();
        },
        onUnselect: function(rowIndex, rowData) {
            var obj = $("#printData").data("pageSelects");
            if (obj) {
                var pageNo = $("#page").val();
                var pageSelect = System.getElement(obj, "pageNo", pageNo);
                if (pageSelect) {
                    var selecteds = pageSelect.selecteds;
                    if (selecteds) {
                        selecteds = System.removeElement(selecteds, "rowIndex", rowIndex);
                        pageSelect.selecteds = selecteds;
                        $("#printData").data("pageSelects", obj)
                    }
                }
            }
			
			// 如果没有选择任何记录，那么需要disable 某些按钮, by YZQ on 2012/09/19
            var rows = $("#mc").datagrid("getSelections");
            if (rows == null || rows.length <= 0) {
            	$("#btn_remove").linkbutton("disable");
                $("#btn_batch_add,#btn_batch_add1").linkbutton("disable");
            }  
			else {
				checkPersonPermission();
			}
        },
        onLoadSuccess: function(data) {
            $("#mc").datagrid('unselectAll');
            var options = $("#mc").datagrid('options');
            $("#page").val(options.pageNumber);
            $("#rows").val(options.pageSize);
            var title = $("#contentTabs").tabs('getSelected').panel('options').title;
            if (title == "名册") {
                //renderPhotoList(data)
            }
            
			disableButtons();			

			// 只有按单位显示时，才激活按钮！
			if ($("#mc").data("queryType") == 1) {
				System.permission.checkUnitPermission($("#currentUnit").val(), function(flag) {
					if (flag.editable) {
						$("#personOrder").linkbutton('enable');
						$("#btn_add").linkbutton('enable');	
						g_canAddPerson = true;
					}
					
					setTimeout("initSelect()", 10);
				});
			}
			else {
				setTimeout("initSelect()", 10);
			}
			
			getLeaderRequirement();
        }
    });
	/*
    $('#mc').pagination({
        onSelectPage: function(pageNumber, pageSize) {
            var options = $("#mc").datagrid('options');
            options.pageNumber = pageNumber;
            options.pageSize = pageSize;
            $('#mc').datagrid(options);
        }
    })
	*/
});
function printData() {
    var reportid = $("#mm").data('selectReportId');
    if (reportid == "-1") {
        //var nodes = $("#tt").tree('getChecked');
		var nodes = getZTreeOjb('tt').getCheckedNodes();
		if(nodes == null || nodes.length==0){
			$.messager.alert('提示',"请勾选需要打印的单位!");
			return ;
		}else{
			var units="";
			var count = 0;
			$(nodes).each(function(i,v){
				//var target = v.target;
				//if(target.children == null || target.children == undefined || target.children.length==0){
				//	return true;
				//}
				if(count > 0) {
					units +=",";
				}
				units += v.id;
				count +=1;
				if(i== (nodes.length-1)){
					$.ajax({
						type:'post',
						url:System.rootPath+"/common/exec-common-method!createPrintLeadySessionKey.action",
						data:{'units':units},
						success:function(sessionkey){
							System.report.printLeaderBySessionKey(sessionkey);
						}
					});
				}
			});
		}
/*		
		var unitcode = $("#currentUnit").data("snodeid");
		if(unitcode !=null && $.trim(unitcode).length>0){
			System.report.printLeader(unitcode);
		}
*/
    } else {
		var personcodes = getSelectedPersons();
		if (personcodes != '') {
			System.report.print(personcodes);
		}
    }
}
function PrintAll() {
    $.messager.confirm('提示', '是否打印全部?', 
    function(b) {
        if (b) {
            var d = $("#mc").data('queryParams');
            d.queryType = $("#mc").data('queryType');
            $.ajax({
                url: System.rootPath + '/common/exec-common-method!createPrintSessionkey.action',
                data: d,
                success: function(sessionkey) {
                    System.report.printAll(sessionkey)
                },
                error: function() {
                    $.messager.alert('错误', '网络错误!', 'error')
                },
                complete: function() {}
            })
        }
    })
}
function batchOperation(type) {
	var personcodes = getSelectedPersons();
	if (personcodes == '') return;
	
    $('body').append("<div id=\"w\" style=\"padding:5px;background: #fafafa;\"></div>");
    var method = (type == 1 ? "saveNdkh": "saveJypx");
    $("#w").dialog({
        modal: true,
        title: '批量新增<font color=\"red\">' + (type == 1 ? "考核检查": "培训") + "</font>信息",
        width: 810,
        height: 240,
		closed: true,
        href: System.rootPath + "/common/batch-operation.action?type=" + type,
        buttons: [{
            text: '确定',
            iconCls: 'icon-ok',
            handler: function() {
                if ($("#batchDataForm").form('validate')) {
                    System.openLoadMask($("#w"), "正在提交...");
                    $.ajax({
                        type: 'post',
                        url: System.rootPath + '/common/batch-operation!' + method + '.action',
                        data: {
                            'entityData': encodeURI(encodeURI(getData())),
                            'personJsons': encodeURI(encodeURI(getSPersonsMap()))
                        },
                        success: function(json) {
                            if (json.status) {
                                $("#w").dialog('close');
                                $("#w").dialog('destroy');
                                $("#w").remove()
                            } else {
                                $.messager.alert("网络错误", "出现错误了(>_<)，稍后再试试!", "error")
                            }
                        },
                        error: function(XMLHttpRequest, textStatus, errorThrown) {
                            $.messager.alert("网络错误", "出现错误了(>_<)，稍后再试试!", "error")
                        },
                        complete: function(XMLHttpRequest, textStatus) {
                            System.closeLoadMask($("#w"))
                        }
                    });
                    return true
                } else {
                    return false
                }
            }
        },
        {
            text: '取消',
            iconCls: 'icon-cancel',
            handler: function() {
                $("#w").dialog('close');
                $("#w").dialog('destroy');
                $("#w").remove()
            }
        }],
		onLoad: function() {
			// fxxk, easyui 1.2.6 has a bug to initialize the combotree?? add a empty onLoad() function seems to can fix it??
		},
        onClose: function() {
            $("#w").dialog('destroy');
            $("#w").remove()
        }
    });
	
	$("#w").dialog('open');
}
function getSPersonsMap() {
    var obj = $("#printData").data("pageSelects");
    var map = [];
    if (obj) {
        $.each(obj, 
        function(i, v) {
            var selecteds = v.selecteds;
            $.each(v.selecteds, 
            function(j, s) {
                var o = new Object();
                o.personcode = s.keywordId;
                o.name = s.keywordcn;
                map.push(o)
            })
        })
    }
    return $.toJSON(map)
}
function initSelect() {
    var obj = $("#printData").data("pageSelects");
    if (obj) {
        var pageNo = $("#page").val();
        var pageSelect = System.getElement(obj, "pageNo", pageNo);
        if (pageSelect) {
            $.each(pageSelect.selecteds, 
            function(i, v) {
                $("#mc").datagrid("selectRow", v.rowIndex)
            })
        }
    }
}
function clearSelect() {
    $("#page").val(1);
    $("#printData").removeData("pageSelects");
    //$("#personOrder").linkbutton("disable");
    $("#mc").datagrid("clearSelections");
}
var Bus = {
    showList: function(data, url) {
        var opts = $("#mc").datagrid('options');
        opts.pageNumber = parseInt($("#page").val(), 10);
        opts.pageSize = parseInt($("#rows").val(), 10);
		opts.url = url;
        var params = opts.queryParams;
        params.serviceid = $("#serviceid").val();
        params.currentUnit = $("#currentUnit").val();
        params.querykey = $("#querykey").val();
        params.group = $("#group").val();
        params.tables = $("#tables").val();
        params.querySQL = $("#querySQL").val();
        opts.queryParams = params;
        //$("#mc").datagrid(opts);
		$("#mc").datagrid('loadData', data);
        $("#mc").data('queryParams', params);
        renderPhotoList(data)
    },
    loadDataByLevelQuery: function(condition) {
        if (condition) {
            clearSelect();
            $("#group").val(condition.conditionGroupkey);
            $("#tables").val(condition.conditionTablekey);
            $("#querySQL").val(condition.conditionQuerySQL);
            $("#querykey").val("");
            // 不清空当前选中的单位, by YZQ on 2013/03/25
            //$("#currentUnit").val("");
            //$("#currentUnitName").val("");
            var url = System.rootPath + "/query/level-query!execSearch.action";
            $("#mc").data("queryType", 3);
            Bus.loadData(url)
        }
    },
    loadDataByQuerykey: function() {
        clearSelect();
        var url = System.rootPath + "/mingc/mingc!queryByQuerykey.action";
        $("#group").val("");
        $("#tables").val("");
        $("#querySQL").val("");
        // 不清空当前选中的单位, by YZQ on 2013/03/25
		//$("#currentUnit").val("");
		//$("#currentUnitName").val("");
        $("#mc").data("queryType", 2);
        Bus.loadData(url)
    },
    loadDataByUnit: function() {
        clearSelect();
        var url = System.rootPath + "/mingc/mingc!queryByUnit.action";
        $("#group").val("");
        $("#tables").val("");
        $("#querySQL").val("");
        $("#querykey").val("");
        Bus.loadData(url);
        //$("#personOrder").linkbutton("enable");
        $("#mc").data("queryType", 1)
    },
    loadDataByManyQuery: function() {
        clearSelect();
        var url = System.rootPath + "/mingc/mingc!queryForMany.action?fzai=false";
        $("#group").val("");
        $("#tables").val("");
        $("#querySQL").val("");
        $("#querykey").val("");
        // 不清空当前选中的单位, by YZQ on 2013/03/25
		//$("#currentUnit").val("");
		//$("#currentUnitName").val("");
        Bus.loadData(url);
        //$("#personOrder").linkbutton("enable");
        $("#mc").data("queryType", 7)
    },
    loadData: function(url) {
        if (!url) {
            url = System.rootPath + "/mingc/mingc!queryByUnit.action"
        }
		$("#rows").val(999999);
		
		// 如果是搜索名字，这里处理搜索方式, by YZQ on 2013/03/25
		if ($("#mc").data("queryType") == 2) {
			if ($("#searchMode").val() == 'searchAll') {
				$("#searchUnit").val('');
			}
			else {
				$("#searchUnit").val($("#currentUnit").val());
			}
		}
		
		var params = $("#pageParames").serialize();
		params = decodeURIComponent(params, true);
        $.ajax({
            type: 'post',
            url: url,
            data: encodeURI(params),
            beforeSend: function(XMLHttpRequest) {
                System.openLoadMask($("#contentPanel"), "正在查询...")
            },
            success: function(data, textStatus) {
				//alert('query');
                Bus.showList(data, url)
            },
            complete: function(XMLHttpRequest, textStatus) {
                System.closeLoadMask($("#contentPanel"))
            },
            error: function() {
                $.messager.alert("提示", "查询出现错误,请重试!", "error")
            }
        })
    }
};
function detail(personcode, name) {
    $("#personcode").val(personcode);
	/*
    $("#openWinDetail").show().dialog({
        title: '详细',
        height: 610,
        width: 1010,
        top: 20,
        modal: true
    });
    $("#openWinDetail").show().dialog('open');
    $("#pageParames").attr("action", $("#currentAction").val() + "!detail.action").submit()
	*/
	
	//var url = $("#currentAction").val() + "!detail.action";
	//var params = $("#pageParames").serialize();
	//url = url + "?" + params;
	//System.openURL(name + " - 信息维护", url);
	
	//System.commonEditor.editorSingePerson(personcode);
	var url = '/govhr/demo/demo!ndkh.action?pageIndex=2&personName=' + encodeURI(name);
	System.openURL(name + " - 年度考核", url);
	//window.showModalDialog(url,window,"dialogWidth=800px;dialogHeight=590px");
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
    Bus.loadData($("#mc").datagrid('options').url)
}
function renderPhotoList(data) {
	//alert("render phtoto Count: " + data.rows.length);
	$("#personPhotoList").empty();
	
    if (data.rows && data.rows.length == 0) {
		$("#pageDataRows").show();
        $("#pageDataRows").empty().append("<tr><td width=\"12%\" height=\"100\" align=\"center\" class=\"line_b\" colspan=\"2\">没有数据!</td></tr>")
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
			desc += "<a href=\"javascript:void(0);\" onclick=\"detail(" + v.personcode + ",'" + Utils.toVarString(v.xingm) + "');\">";
			desc += "<img border=\"0\" src=\"" + System.rootPath + "/lob/photo.action?personcode=" + v.personcode + "&zoom=true&width=100&height=133\" width=\"100\" height=\"133\" />";
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
	/*
    $("#pageNo").html(pageNo);
    $("#pageFirst").html(parseInt(((pageNo - 1) * pageSize) + 1, 10));
    $("#pageEnd").html(pageNo * pageSize);
    $("#totalCount").html(data.total);
	*/
	
    var totalPage = parseInt(data.total / pageSize, 10);
    if (data.total % pageSize) {
        totalPage += 1
    }
	/*
    $("#totalPages").html(totalPage);
    var footHtml = "<a href=\"javascript:void(0);\" class=\"page_btn blacklink\" onclick=\"gotoPage(1);\">[首 页]</a>";
    if (pageNo - 1 >= 1) {
        footHtml += "<a href=\"javascript:void(0);\" class=\"page_btn blacklink\" onclick=\"gotoPage(" + (pageNo > 1 ? (pageNo - 1) : 1) + ");\">[上一页]</a>"
    }
    if (pageNo + 1 <= totalPage) {
        footHtml += "<a href=\"javascript:void(0);\" class=\"page_btn blacklink\" onclick=\"gotoPage(" + ((pageNo + 1) > totalPage ? totalPage: (pageNo + 1)) + ");\">[下一页]</a>"
    }
    footHtml += "<a href=\"javascript:void(0);\" class=\"page_btn blacklink\" onclick=\"gotoPage(" + totalPage + ");\">[尾 页]</a>";
	*/
	var footHtml = "总共有" + data.total + "条记录";
    $("#pageDataFoot").empty().append(footHtml);
	$("#pageDataFootMc").empty().append(footHtml);
}
function newItem() {
    System.person.add("#mc", $("#currentUnit").val(), $("#currentUnitName").val(), 
    function(entity) {
        $('#mc').datagrid('appendRow', entity);
        var index = $('#mc').datagrid('getRows').length - 1;
        $('#mc').datagrid('selectRow', index)
    })
}
function destroyItem() {
    var rows = $('#mc').datagrid('getSelections');
    if (rows.length > 0) {
        $.messager.confirm('提示', '确定要删除选中行吗?', function(isOk) {
            if (isOk) {
                $.each(rows, function(i, row) {
					$.post(
						System.rootPath + '/mingc/mingc!remove.action', 
						{
							'entity.id': row.personcode
						},
						function() {
							var index = $('#mc').datagrid('getRowIndex', row);
							$('#mc').datagrid('deleteRow', index);                            
						}
					);
                });
            }
        });
    }
}
function getButton(unitcode) {
    if ($("#mc").data("queryType") == 1) {
        if ($("#btn_add").data("isView")) {
            $("#btn_remove").linkbutton("disable");
            $("#btn_batch_add,#btn_batch_add1").linkbutton("disable")
        } else {
            if ($("#currentUnit").data("snodeid") == unitcode || $("#allowOperator").val() == "true") {
                $("#btn_remove").linkbutton("enable");
                $("#btn_batch_add,#btn_batch_add1").linkbutton("enable")
            } else {
                $("#btn_remove").linkbutton("disable");
                $("#btn_batch_add,#btn_batch_add1").linkbutton("disable")
            }
        }
    } else {
        System.button.isView($("#serviceid").val(), unitcode, 
        function(view) {
            if (view) {
                $("#btn_remove").linkbutton("disable");
                $("#btn_batch_add,#btn_batch_add1").linkbutton("disable")
            } else {
                $("#btn_remove").linkbutton("enable");
                $("#btn_batch_add,#btn_batch_add1").linkbutton("enable")
            }
        })
    }
}

function checkPersonPermission() {
	var personcodes = getSelectedPersons();
	if (personcodes != '') {
		System.permission.checkIsEditablePersons(personcodes, function(flag) {
			if (flag.editable) {
				$("#btn_remove").linkbutton("enable");
				if (g_canAddPx) {
					$("#btn_batch_add").linkbutton("enable")
				}
				if (g_canAddKh) {
					$("#btn_batch_add1").linkbutton("enable")
				}
			}
			else {
				$("#btn_remove").linkbutton("disable");
				$("#btn_batch_add,#btn_batch_add1").linkbutton("disable");
			}
		});
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

// -------------- 领导班子职数相关, by YZQ on 2012/12/16
function getLeaderRequirement() {	
	var unitcode = $("#currentUnit").val();
	if (unitcode == '') return;
	
	var url = System.rootPath + "/analysiscitygov/analysis!leaderRequirementAnalysis.action?unit=" + unitcode;
	$("#summaryTable").hide();
	$.ajax({
		type: 'post',
		url: url,
		success: function(data, textStatus) {
			showLeaderRequirement(data);
		},
		error: function() {
			$.messager.alert("提示", "无法获取领导职数,请重试!", "error")
		}
    });	
}

function showLeaderRequirement(data) {
	$("#summayUnit").html(data.unitname);
	
	for (var i = 0; i < data.bzzs.length; i++) {
		var bzzs = data.bzzs[i];
		var bzzs_req = data.bzzs_req[i];
		var bzzs_cp = bzzs - bzzs_req; // 超配
		if (bzzs_cp < 0) bzzs_cp = 0;
		else if (bzzs_cp > 0 && bzzs_req == 0) bzzs_cp = 0;
		
		var bzzs_qp = bzzs_req - bzzs; // 缺配
		if (bzzs_qp < 0) bzzs_qp = 0;
		else if (bzzs_qp > 0 && bzzs_req == 0) bzzs_qp = 0;
		
		$("#bzzs_req_" + i).html(getLeaderRequirementCountHtml(bzzs_req));
		$("#bzzs_" + i).html(getLeaderRequirementLinkHtml(bzzs, "bzzs_view_" + (i + 1), "bzzs_req_" + i));
		$("#bzzs_cp_" + i).html(getLeaderRequirementCountHtml(bzzs_cp));
		$("#bzzs_qp_" + i).html(getLeaderRequirementCountHtml(bzzs_qp));
	}
	
	$("#summaryTable").show();		
}

function getLeaderRequirementCountHtml(count) {
	if (count == 0) return "";
	else return "" + count;
}
			
function getLeaderRequirementLinkHtml(count, key, reqId) {
	var content = "";
	if (count <= 0) {
		if ($("#" + reqId).html() != '') {
			content = content + count;
		}
	}
	else {
		content = '<span class="numb"><a href="#" onclick="viewLeaderRequirementPerson(' + "'" + key + "'" + ');">' + count + "<a/></span>";
	}
	
	return content;
}

function viewLeaderRequirementPerson(key){				
	$('#leaderReqList').datagrid({
		nowrap: true,
		striped: false,
		collapsible:false,
		fit: true,
		remoteSort: false,					
		url: System.rootPath + '/analysiscitygov/analysis!leaderSummaryView.action?key=' + key,
		idField:'personcode',
		columns: [[
					{field:'personcode',width:20,hidden:true},
					{title:'姓名',field:'A000_A0101',width:70, sortable: true,formatter:function(value,rowData,rowIndex){ 
							return "<a href='javascript:void(0)' onclick='System.leaderQuery.showRemoval(" + rowData.personcode + ")'>" + value + "</a>";
						}
					},
					{title:'性别',field:'A000_A0104_SHOW',width:40, sortable: true},
					{title:'出生日期',field:'A000_A0107',width:60, sortable: true},
					{title:'出生地',field:'A000_A0114_SHOW',width:65, sortable: true},
					{title:'籍贯',field:'A000_A0111_SHOW',width:65, sortable: true},
					{title:'职务',field:'zhiw',width:500, sortable: true}
				]],
		pagination:false
	});	
	
	$("#leaderReqList").datagrid('clearSelections');
	System.report.menu("#leaderReqPrint", printLeaderReqData);
	$('#leaderReqWin').window('open');	
}

function printLeaderReqData() {
	var reportid = $("#leaderReqPrint").data('selectReportId');
	if (reportid == "-1") {
		$.messager.alert('提示',"本模块不支持输出领导名册！");					
	} else {
		var rows = $("#leaderReqList").datagrid('getSelections');
		//alert(rows.length);
		var keys = "";
		if (rows != null && rows.length > 0) {
			for (var i = 0; i < rows.length; i++) {
				if (keys == "") keys = rows[i].personcode;
				else keys = keys + "," + rows[i].personcode;
			}
			
			// 添加输出Excel名单, by YZQ on 2012/08/11
			sysPrintReport(reportid, keys);
		}
	}
}