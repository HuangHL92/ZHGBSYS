var g_canAddPerson = false; // 常量，是否可以添加人员
var g_canAddPx = false; // 是否可以批量添加培训信息
var g_pageSize = 20; // 每页数据量

$(document).ready(function() {
    $("#mc").datagrid({
        //height: 424,		
        //width: $('body').outerWidth(true) - 340,
		fit: true,
        rownumbers: true,
        singleSelect: false,
		nowrap: false,
		pagination: true,
        pageSize: g_pageSize,
        pageList: [g_pageSize, g_pageSize * 2, g_pageSize * 3],
        idField: 'id',
        sortOrder: 'asc',
        frozenColumns: [[{
            field: 'id',
            hidden: true
        },
		{field:'ck',checkbox:true, align: 'center'
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
            field: 'zhiw',
            title: '单位及职务',
            align: "left",
            rowspan: 2,
            width: 300,
            resizable: true
        },
        {
            field: 'sex',
            title: '性别',
            align: "center",
            rowspan: 2,
            width: 60,
            resizable: true
        },
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
            width: 64,
            resizable: true
        },
        {
            field: 'rudsj',
            title: '入党时间',
            align: "center",
            rowspan: 2,
            width: 64,
            resizable: true
        }, 
        {
            field: 'gongzsj',
            title: '工作时间',
            rowspan: 2,
			align: 'center',
            width: 64
        },
        {
            field: 'xianzsj',
            title: '任现职时间',
			align: 'center',
            rowspan: 2,
            width: 70
        },
        {
            title: '国内培训',
            colspan: 2,
            width: 70
        },
        {
            title: '出国（境）培训',
            colspan: 2,
            width: 70
        },
		{
            field: 'ckrmb',
            title: '',
            rowspan: 2,
			align: 'center',
            width: 80,
			formatter: function(value, rowData, rowIndex) {
				return '<a href="#" onclick="System.report.printTrainingDetails(' + rowData.personcode + ');">下载干部培训信息表</a>';
			}
        }],
        [{
        	field: 'gncs',
        	title: '累计次数',
            //rowspan: 2,
            align: 'center',
            width: 60
        },
        {
            field: 'gnts',
	        title: '累计天数',
	        //rowspan: 2,
	        align: 'center',
	        width: 60
	    },
	    {
	        field: 'cgcs',
		    title: '累计次数',
		    //rowspan: 2,
		    align: 'center',
		    width: 60
		},
		{
		    field: 'cgts',
			title: '累计天数',
			//rowspan: 2,
			align: 'center',
			width: 60
	   }]],
		onSelectAll: function(rows) {
			checkPersonPermission(); // 修正全选，未激活按钮问题，by YZQ on 2015/07/07
		},
		onUnselectAll: function(rows) {
			checkPersonPermission(); // 修正全选，未激活按钮问题，by YZQ on 2015/07/07
		},
        onSelect: function(rowIndex, rowData) {
        	checkPersonPermission();
        },
        onUnselect: function(rowIndex, rowData) {	
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
            
			disableButtons();
        },
        toolbar: [{
            id: 'btn_batch_add',
            iconCls: 'icon-add',
            text: '批量添加培训信息',
            disabled: true,
            handler: function() {
                batchOperation(2);
            }
        },
        {
            id: 'export_px',
            iconCls: 'icon-add',
            text: '导出培训名册',
            //disabled: true,
            handler: function() {
				/*
            	var obj = $("#mc").datagrid("getRows");
				var ulData=[];
				if(obj) ulData = obj;
				if(ulData.length<=0){
					$.messager.alert('提示','请选择需要导出的人员!','info');
					return ;
            }   */
				Bus.exportData();
        }
        }
		]
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
function batchOperation(type) {
	var personcodes = getSelectedPersons();
	if (personcodes == '') return;
	
    $('body').append("<div id=\"w\" style=\"padding:5px;\"></div>");
    var method = ("saveTjgbpx");
    $("#w").dialog({
        modal: true,
        title: '批量添加培训信息',
        width: 810,
        height: 300,
		closed: true,
        href: System.rootPath + "/common/batch-operation.action?type=" + type,
        buttons: [{
            text: '确定',
            iconCls: 'icon-ok',
            handler: function() {
                if ($("#batchDataForm").form('validate')) {
					if ($("#bcmc").val() == '') {
						$.messager.alert("错误", "请输入培训班名称！", "error");
						return;
					}
					
					if ($("#xuez").val() == '' || $("#xuez").val() <= 0) {
						$.messager.alert("错误", "学制必须大于0！", "error");
						return;
					}
					
					if ($("#cbjg").val() == '') {
						$.messager.alert("错误", "请输入承办机构！", "error");
						return;
					}
					
					if ($("#pxqsrq").val() == '') {
						$.messager.alert("错误", "请输入培训起始日期！", "error");
						return;
					}
					
					if ($("#pxjsrq").val() == '') {
						$.messager.alert("错误", "请输入培训结束日期！", "error");
						return;
					}
					
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
	var map = [];
	var rows = $("#mc").datagrid("getSelections");
	if (rows) {
		$(rows).each(function(index, row){
			var o = new Object();
			o.personcode = row.personcode;
			o.name = row.xingm;
			map.push(o);
		});
	}
	
	return $.toJSON(map);
}
function initSelect() {
    /*var obj = $("#printData").data("pageSelects");
    if (obj) {
        var pageNo = $("#page").val();
        var pageSelect = System.getElement(obj, "pageNo", pageNo);
        if (pageSelect) {
            $.each(pageSelect.selecteds, 
            function(i, v) {
                $("#mc").datagrid("selectRow", v.rowIndex)
            })
        }
    }*/
	$("#mc").datagrid("selectRow");
}
function clearSelect() {
    $("#page").val(1);
    //$("#printData").removeData("pageSelects");
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
        //renderPhotoList(data);
    },
    loadDataByLevelQuery: function(condition) {
        if (condition) {
            clearSelect();
			
			// 只包含厅级干部, by YZQ on 2015/07/07
			//condition.conditionQuerySQL += " and a000.personcode in (select personcode from a004 where A004_A0514='1' and A004_A0501 in ('0121','0122'))";
			
            $("#group").val(condition.conditionGroupkey);
            $("#tables").val(condition.conditionTablekey);
            $("#querySQL").val(condition.conditionQuerySQL);
            $("#querykey").val("");
			
			// 不清空当前选中的单位, by YZQ on 2013/03/25
            //$("#currentUnit").val("");
            //$("#currentUnitName").val("");
            var url = System.rootPath + "/query/level-query!execSearchByPx.action";
            $("#mc").data("queryType", 3);
            Bus.loadData(url)
        }
    },
    loadDataByQuerykey: function() {
        clearSelect();
        var url = System.rootPath + "/pxmc/pxmc!queryByQuerykey.action";
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
        var url = System.rootPath + "/pxmc/pxmc!queryByUnit.action";
        $("#group").val("");
        $("#tables").val("");
        $("#querySQL").val("");
        $("#querykey").val("");
        Bus.loadData(url);
        //$("#personOrder").linkbutton("enable");
        $("#mc").data("queryType", 1);
    },
    
    exportData: function() {
        clearSelect();
        var url;
        currentUnit = $("#currentUnit").val();
        querykey = $("#querykey").val();
        searchUnit = $("#searchUnit").val();
        
        if($("#mc").data("queryType")==1){
        	
         url = System.rootPath + "/pxmc/pxmc!exportByUnit.action?currentUnit="+currentUnit;
         
         window.open(url);
         
        }else if($("#mc").data("queryType")==2){
        	
         url = System.rootPath + "/pxmc/pxmc!exportByQuerykey.action?Querykey="+querykey+"&searchUnit="+searchUnit;
         window.open(url);
         
        }else if($("#mc").data("queryType")==3){
        	group = $("#group").val();
            tables = $("#tables").val();
            querySQL = $("#querySQL").val();
            url = System.rootPath + "/query/level-query!execSearchByExport.action?group="+group+"&tables="+tables+"&querySQL="+querySQL;
            window.open(url);
            
           }
        //$("#group").val("");
        //$("#tables").val("");
        //$("#querySQL").val("");
        //$("#querykey").val("");
        //Bus.loadData(url);
        //$("#personOrder").linkbutton("enable");
        //$("#mc").data("queryType", 1)
        
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
            url = System.rootPath + "/pxmc/pxmc!queryByUnit.action"
        }
		//$("#rows").val(999999);
		if ($("#rows").val() == '10') {
			$("#rows").val(20);
		}
		
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
	
	System.commonEditor.editorSingePerson(personcode);
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
        if (ture) {
            $("#btn_batch_add").linkbutton("disable");
        } else {
            if ($("#currentUnit").data("snodeid") == unitcode || $("#allowOperator").val() == "true") {
                $("#btn_batch_add").linkbutton("enable");
            } else {
                $("#btn_batch_add").linkbutton("disable");
            }
        }
    } else {
        System.button.isView($("#serviceid").val(), unitcode, 
        function(view) {
            if (view) {
                $("#btn_batch_add").linkbutton("disable");
            } else {
                $("#btn_batch_add").linkbutton("enable");
            }
        });
    }
}

function checkPersonPermission() {
	var personcodes = getSelectedPersons();
	if (personcodes != '') {
		System.permission.checkIsEditablePersons(personcodes, function(flag) {
			if (flag.editable) {
				if (g_canAddPx) {
					$("#btn_batch_add").linkbutton("enable");
				};	
			}
			else {
				$("#btn_batch_add").linkbutton("disable");
			}
		});
	}
	else {
		// 没有任何选择，屏蔽按钮，by YZQ on 2015/07/07
		$("#btn_remove").linkbutton("disable");
		$("#btn_batch_add,#btn_batch_add1").linkbutton("disable");
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