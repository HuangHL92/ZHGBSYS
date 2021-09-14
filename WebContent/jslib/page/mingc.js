$(document).ready(function() {
    var lastIndex;
    var editIndex;
    $("#mc").datagrid({
        //height: 440,
        //width: $('body').outerWidth(true) - 340,
		fit: true,
        rownumbers: true,
        singleSelect: false,
        pagination: true,
        pageSize: 10,
        pageList: [10, 20, 30],
        idField: 'id',
        sortName: 'xingm',
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
            resizable: true,
            formatter: function(value, rowData, rowIndex) {
                var sexCode = rowData.sexCode;
                if (sexCode == "2") {
                    return value + "<br/>(女)"
                } else {
                    return value == undefined ? "": value
                }
            },
            editor: {
                type: "commonEdit",
                options: {
                    callback: function(target) {
                        myEdit.xingm(target, editIndex)
                    }
                }
            }
        }]],
        columns: [[{
            field: 'zhiw',
            title: '职务',
            align: "left",
            rowspan: 2,
            width: 100,
            resizable: true,
            editor: {
                type: "commonEdit",
                options: {
                    callback: function(target) {
                        myEdit.zhiw(target, editIndex)
                    }
                }
            }
        },
        {
            field: 'minz',
            title: '民族',
            align: "center",
            rowspan: 2,
            width: 100,
            resizable: true,
            editor: {
                type: 'combotree',
                options: {
                    valueField: 'code',
                    textField: 'name',
                    url: System.rootPath + "/code/code.action?codeClass=0004",
                    required: true,
                    editable: true,
                    onClick: function(node) {
                        var data = $("#mc").data('datagrid').data;
                        var rowData = $("#mc").datagrid('getRows')[editIndex];
                        rowData.minzCode = node.id;
                        rowData.minz = node.text;
                        var newrows = data.rows;
                        for (var i = 0; i < newrows.length; i++) {
                            if (newrows[i].id == rowData.id) {
                                newrows[i] = rowData;
                                break
                            }
                        }
                        data.rows = newrows;
                        $("#mc").data('datagrid').data = data
                    }
                }
            }
        },
        {
            field: 'birthday',
            title: '出生年月',
            align: "center",
            rowspan: 2,
            width: 90,
            resizable: true,
            editor: {
                type: 'data97',
                options: {
                    iLen: 6
                }
            }
        },
        {
            field: 'jig',
            title: '籍贯',
            rowspan: 2,
            align: "center",
            width: 120,
            resizable: true,
            editor: {
                type: 'combotree',
                options: {
                    valueField: 'code',
                    textField: 'name',
                    url: System.rootPath + "/code/code.action?codeClass=1001",
                    required: true,
                    editable: true,
                    onClick: function(node) {
                        var data = $("#mc").data('datagrid').data;
                        var rowData = $("#mc").datagrid('getRows')[editIndex];
                        rowData.jigCode = node.id;
                        rowData.jig = node.text;
                        var newrows = data.rows;
                        for (var i = 0; i < newrows.length; i++) {
                            if (newrows[i].id == rowData.id) {
                                newrows[i] = rowData;
                                break
                            }
                        }
                        data.rows = newrows;
                        $("#mc").data('datagrid').data = data
                    }
                }
            }
        },
        {
            field: 'chusd',
            title: '出生地',
            rowspan: 2,
            align: "center",
            width: 120,
            resizable: true,
            editor: {
                type: 'combotree',
                options: {
                    valueField: 'code',
                    textField: 'name',
                    url: System.rootPath + "/code/code.action?codeClass=1001",
                    required: true,
                    editable: true,
                    onClick: function(node) {
                        var data = $("#mc").data('datagrid').data;
                        var rowData = $("#mc").datagrid('getRows')[editIndex];
                        rowData.chusdCode = node.id;
                        rowData.chusd = node.text;
                        var newrows = data.rows;
                        for (var i = 0; i < newrows.length; i++) {
                            if (newrows[i].id == rowData.id) {
                                newrows[i] = rowData;
                                break
                            }
                        }
                        data.rows = newrows;
                        $("#mc").data('datagrid').data = data
                    }
                }
            }
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
            width: 80,
            editor: {
                type: 'data97',
                options: {
                    iLen: 6
                }
            }
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
                        return value
                    }
                } else {
                    return value == undefined ? "": value
                }
            },
            editor: {
                type: "commonEdit",
                options: {
                    callback: function(target) {
                        myEdit.rudsj(target, editIndex)
                    }
                }
            }
        },
        {
            field: 'xianjsj',
            title: '任现职级时间',
            rowspan: 2,
            width: 80,
            editor: {
                type: 'data97',
                options: {
                    iLen: 6
                }
            }
        },
        {
            field: 'xianzsj',
            title: '任现职时间',
            rowspan: 2,
            width: 80,
            editor: {
                type: 'readOnly'
            }
        },
        {
            field: 'remark',
            title: '备注',
            rowspan: 2,
            width: 150,
            editor: {
                type: "commonEdit",
                options: {
                    callback: function(target) {
                        myEdit.remark(target, editIndex)
                    }
                }
            }
        }], [{
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
            },
            editor: {
                type: "commonEdit",
                options: {
                    callback: function(target) {
                        myEdit.xlxw(target, editIndex, 1)
                    }
                }
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
            },
            editor: {
                type: "commonEdit",
                options: {
                    callback: function(target) {
                        myEdit.byyx_zy(target, editIndex, 1)
                    }
                }
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
            },
            editor: {
                type: "commonEdit",
                options: {
                    callback: function(target) {
                        myEdit.xlxw(target, editIndex, 2)
                    }
                }
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
            },
            editor: {
                type: "commonEdit",
                options: {
                    callback: function(target) {
                        myEdit.byyx_zy(target, editIndex, 2)
                    }
                }
            }
        }]],
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
            pageSelect.selecteds.push(selectRow);
            $("#printData").data("pageSelects", obj);
            lastIndex = rowIndex;
            getButton(rowData.unitcode, editIndex)
        },
        onUnselect: function(rowIndex, rowData) {
            if (editIndex == rowIndex) {
                $("#mc").datagrid('endEdit', editIndex);
                editIndex = undefined;
                $("#btn_edit").linkbutton("enable");
                $("#btn_ok").linkbutton("disable");
                $("#btn_cancel").linkbutton("disable")
            }
            var obj = $("#printData").data("pageSelects");
            if (obj) {
                var pageNo = $("#page").val();
                var pageSelect = System.getElement(obj, "pageNo", pageNo);
                if (pageSelect) {
                    var selecteds = pageSelect.selecteds;
                    if (selecteds) {
                        selecteds = System.removeElement(selecteds, "rowIndex", rowIndex);
                        pageSelect.selecteds = selecteds;
                        $("#printData").data("pageSelects", obj);
                        if (selecteds.length == 0) {
                            $("#btn_remove").linkbutton("disable");
                            $("#btn_edit").linkbutton("disable");
                            $("#btn_ok").linkbutton("disable");
                            $("#btn_cancel").linkbutton("disable")
                        }
                    }
                }
            }
        },
        onLoadSuccess: function(data) {
            editIndex = undefined;
            $("#mc").datagrid('unselectAll');
            var options = $("#mc").datagrid('options');
            $("#page").val(options.pageNumber);
            $("#rows").val(options.pageSize);
            $("#btn_remove").linkbutton("disable");
            if ($("#isView").val() == true) {
                $("#btn_add").linkbutton("disable")
            } else {
                $("#btn_add").linkbutton("enable")
            }
            setTimeout("initSelect()", 10);
            $("#btn_ok").linkbutton("disable");
            $("#btn_cancel").linkbutton("disable")
        },
        toolbar: [{
            id: 'btn_add',
            iconCls: 'icon-add',
            text: '添加',
            disabled: true,
            handler: function() {
                newItem()
            }
        },
        '-', {
            id: 'btn_remove',
            iconCls: 'icon-remove',
            text: '删除',
            disabled: true,
            handler: function() {
                destroyItem()
            }
        },
        '-', {
            id: 'btn_edit',
            iconCls: 'icon-edit',
            text: '编辑',
            disabled: true,
            handler: function() {
                if (editIndex) {
                    $("#mc").datagrid('endEdit', editIndex)
                }
                editIndex = lastIndex;
                $("#mc").datagrid('beginEdit', editIndex);
                $("#mc").datagrid('selectRow', editIndex);
                $("#btn_edit").linkbutton("disable");
                $("#btn_ok").linkbutton("enable");
                $("#btn_cancel").linkbutton("enable")
            }
        },
        '-', {
            id: 'btn_ok',
            iconCls: 'icon-ok',
            text: '完成',
            disabled: true,
            handler: function() {
                if (editIndex) {
                    var rowData = $("#mc").datagrid('getRows')[editIndex];
                    rowData = fullEditFields(rowData, editIndex);
                    saveItem(rowData, 
                    function() {
                        $("#mc").datagrid('cancelEdit', editIndex);
                        $("#btn_ok").linkbutton("disable");
                        $("#btn_cancel").linkbutton("disable");
                        $("#btn_edit").linkbutton("enable");
                        editIndex = undefined;
                        var data = $("#mc").datagrid('getData');
                        $("#mc").datagrid('loadData', data)
                    })
                }
            }
        },
        '-', {
            id: 'btn_cancel',
            iconCls: 'icon-cancel',
            text: '撤销',
            disabled: true,
            handler: function() {
                if (editIndex) {
                    $("#mc").datagrid('cancelEdit', editIndex);
                    $("#btn_cancel").linkbutton("disable");
                    $("#btn_ok").linkbutton("disable");
                    $("#btn_edit").linkbutton("enable");
                    editIndex = undefined
                }
            }
        },
        '-', {
            id: 'btn_printAll',
            text: '<input type=\"checkbox\" id=\"chk_printAll\"/> 打印全部',
            handler: function() {
                var rows = $("#mc").datagrid('getRows');
                if (rows.length == 0) {
                    $("#chk_printAll").attr('checked', false);
                    $.messager.alert('提示', '没有数据打印!')
                }
            }
        }],
        onAfterEdit: function(rowIndex, rowData, changes) {}
    })
});
function loadMcData() {
    clearSelect();
    $("#mc").data("queryType", 1);
    var options = $("#mc").datagrid('options');
    var queryParam = options.queryParams;
    options.pageNumber = parseInt($("#page").val());
    queryParam.serviceid = $("#serviceid").val();
    queryParam.currentUnit = $("#currentUnit").val();
    options.url = System.rootPath + "/mingc/mingc!queryByUnit.action";
    $("#mc").datagrid(options);
    $("#personOrder").linkbutton("enable");
    $('#mc').data('queryParam', queryParam)
}
function loadMcDataByQuerykey() {
    clearSelect();
    $("#mc").data("queryType", 2);
    var options = $("#mc").datagrid('options');
    var queryParam = options.queryParams;
    options.pageNumber = parseInt($("#page").val());
    queryParam.serviceid = $("#serviceid").val();
    queryParam.querykey = encodeURI($("#querykey").val().Trim());
    options.url = System.rootPath + "/mingc/mingc!queryByQuerykey.action";
    $("#mc").datagrid(options);
    $("#btn_add").linkbutton("disable");
    $("#btn_remove").linkbutton("disable");
    $('#mc').data('queryParam', queryParam)
}
function loadMcDataByLevelQuery(condition) {
    if (condition) {
        clearSelect();
        $("#mc").data("queryType", 3);
        var options = $("#mc").datagrid('options');
        var queryParam = options.queryParams;
        options.pageNumber = parseInt($("#page").val());
        queryParam.serviceid = $("#serviceid").val();
        queryParam.group = condition.conditionGroupkey;
        queryParam.tables = condition.conditionTablekey;
        queryParam.querySQL = condition.conditionQuerySQL;
        options.url = System.rootPath + "/query/level-query!execSearch.action";
        $("#mc").datagrid(options);
        $("#btn_add").linkbutton("disable");
        $("#btn_remove").linkbutton("disable");
        $('#mc').data('queryParam', queryParam)
    }
}
function printData() {
    var reportid = $("#mm").data('selectReportId');
    if (reportid == "-1") {
        var unitcode = $("#currentUnit").data("snodeid");
        if (unitcode != null && $.trim(unitcode).length > 0) {
            System.report.printLeader(unitcode)
        }
    } else {
        var printAll = $("#chk_printAll").attr('checked');
        if (printAll) {
            PrintAll()
        } else {
            var obj = $("#printData").data("pageSelects");
            var keys = "";
            var count = 0;
            if (obj) {
                $.each(obj, 
                function(i, v) {
                    var selecteds = v.selecteds;
                    $.each(v.selecteds, 
                    function(j, s) {
                        if (count > 0) {
                            keys += ","
                        }
                        keys += s.keywordId;
                        count++
                    })
                });
                System.report.print(keys)
            }
        }
    }
}
function PrintAll() {
    $.messager.confirm('提示', '是否打印全部?', 
    function(b) {
        if (b) {
            var queryParam = $('#mc').data('queryParam');
            queryParam.queryType = $("#mc").data("queryType");
            $.ajax({
                url: System.rootPath + '/common/exec-common-method!createPrintSessionkey.action',
                data: queryParam,
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
    $("#page").val('1');
    $("#printData").removeData("pageSelects");
    $("#personOrder").linkbutton("disable");
    $("#mc").datagrid("clearSelections");
}
function getButton(unitcode, editIndex) {
    if ($("#isView").val() == "true") {
        $("#btn_remove").linkbutton("disable");
        $("#btn_edit").linkbutton("disable")
    } else {
        if ($("#mc").data("queryType") == 1) {
            if ($("#isView").val() == "true") {
                $("#btn_remove").linkbutton("disable")
            } else {
                if ($("#currentUnit").val() == unitcode || $("#allowOperator").val() == "true") {
                    $("#btn_remove").linkbutton("enable");
                    if (!editIndex) {
                        $("#btn_edit").linkbutton("enable")
                    }
                } else {
                    $("#btn_remove").linkbutton("disable")
                }
            }
        } else {
            System.button.isView($("#serviceid").val(), unitcode, 
            function(data) {
                if (data.isView) {
                    $("#btn_remove").linkbutton("disable")
                } else {
                    $("#btn_remove").linkbutton("enable");
                    if (!editIndex) {
                        $("#btn_edit").linkbutton("enable")
                    }
                }
            })
        }
    }
}
var myEdit = {
    xingm: function(target, editIndex) {
        var rowData = $("#mc").datagrid('getRows')[editIndex];
        var value = rowData.xingm;
        var sex = rowData.sex;
        var sexCode = rowData.sexCode;
        var myEditId = 'myEdit_' + System.random();
        var html = "<div id='" + myEditId + "'></div>";
        $(target).parent().append(html);
        var contentHtml = "<form id='fm'><div class=\"fitem\">" + "<label>姓名:</label><input id='" + myEditId + "_xingm' class=\"easyui-validatebox\" required=\"true\" value='" + value + "'/></div>" + "<div class=\"fitem\"><label>性别:</label><input id='" + myEditId + "_sex' class='easyui-combotree' value='" + sexCode + "' url='" + System.rootPath + "/code/code.action?codeClass=0005'/></div>" + "</form>";
        $("#" + myEditId).dialog({
            modal: true,
            width: 400,
            height: 200,
            title: '编辑姓名、性别',
            content: contentHtml,
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function() {
                    rowData.xingm = $("#" + myEditId + "_xingm").val();
                    rowData.sex = $("#" + myEditId + "_sex").combotree('getText');
                    rowData.sexCode = $("#" + myEditId + "_sex").combotree('getValue');
                    rowData = fullEditFields(rowData, editIndex);
                    saveItem(rowData, 
                    function() {
                        var opts = $("#mc").datagrid('options');
                        var rowDatas = $("#mc").datagrid('getData');
                        var newrows = rowDatas.rows;
                        for (var i = 0; i < newrows.length; i++) {
                            if (newrows[i].id == rowData.id) {
                                newrows[i] = rowData;
                                break
                            }
                        }
                        rowDatas.rows = newrows;
                        $("#mc").datagrid('loadData', rowDatas)
                    });
                    $("#" + myEditId).dialog('destroy');
                    $("#" + myEditId).remove()
                }
            },
            {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function() {
                    $("#" + myEditId).dialog('destroy');
                    $("#" + myEditId).remove()
                }
            }]
        })
    },
    rudsj: function(target, editIndex) {
        var rowData = $("#mc").datagrid('getRows')[editIndex];
        var value = rowData.rudsj;
        var zzmm = rowData.zzmm;
        var zzmmCode = rowData.zzmmCode;
        var myEditId = 'myEdit_' + System.random();
        var html = "<div id='" + myEditId + "'></div>";
        $(target).parent().append(html);
        var contentHtml = "<form id='fm'><div class=\"fitem\">" + "<label>入党时间:</label><input id='" + myEditId + "_rudsj' class='Wdate' onClick='setday(6)' value='" + value + "'/></div>" + "<div class=\"fitem\"><label>政治面貌:</label><input id='" + myEditId + "_zzmm' class='easyui-combotree' value='" + zzmmCode + "' url='" + System.rootPath + "/code/code.action?codeClass=0010'/></div>" + "</form>";
        $("#" + myEditId).dialog({
            modal: true,
            width: 400,
            height: 200,
            title: '编辑入党时间、政治面貌',
            content: contentHtml,
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function() {
                    rowData.rudsj = $("#" + myEditId + "_rudsj").val();
                    rowData.zzmm = $("#" + myEditId + "_zzmm").combotree('getText');
                    rowData.zzmmCode = $("#" + myEditId + "_zzmm").combotree('getValue');
                    rowData = fullEditFields(rowData, editIndex);
                    saveItem(rowData, 
                    function() {
                        var opts = $("#mc").datagrid('options');
                        var rowDatas = $("#mc").datagrid('getData');
                        var newrows = rowDatas.rows;
                        for (var i = 0; i < newrows.length; i++) {
                            if (newrows[i].id == rowData.id) {
                                newrows[i] = rowData;
                                break
                            }
                        }
                        rowDatas.rows = newrows;
                        $("#mc").datagrid('loadData', rowDatas)
                    });
                    $("#" + myEditId).dialog('destroy');
                    $("#" + myEditId).remove()
                }
            },
            {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function() {
                    $("#" + myEditId).dialog('destroy');
                    $("#" + myEditId).remove()
                }
            }]
        })
    },
    remark: function(target) {
        var myEditId = 'myEdit_' + System.random();
        var html = "<div id='" + myEditId + "'><textarea id='" + myEditId + "_textarea' cols=\"54\" style=\"border:0;width:100%;height:100%;overflow: hidden;\"></textarea></div>";
        $(target).parent().append(html);
        $("#" + myEditId).dialog({
            shadow: true,
            modal: true,
            resizable: true,
            width: 450,
            height: 250,
            title: '编辑备注',
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function() {
                    $(target).val($('#' + myEditId + "_textarea").val());
                    $("#" + myEditId).dialog('destroy');
                    $("#" + myEditId).remove()
                }
            },
            {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function() {
                    $("#" + myEditId).dialog('destroy');
                    $("#" + myEditId).remove()
                }
            }],
            onOpen: function() {
                $("#" + myEditId + "_textarea").val($(target).val())
            }
        })
    },
    byyx_zy: function(target, editIndex, type) {
        var myEditId = 'myEdit_' + System.random();
        var rowData = $("#mc").datagrid('getRows')[editIndex];
        var byyx = rowData.rudsj;
        var zy = "";
        var title = "编辑全日制学历";
        if (type == 1) {
            byyx = rowData.qrzbyyx;
            zy = rowData.qrzzy
        } else {
            byyx = rowData.zzbyyx;
            zy = rowData.zzzy;
            title = "编辑在职教育学历"
        }
        var html = "<div id='" + myEditId + "'></div>";
        $(target).parent().append(html);
        contentHtml = "<form id='fm'><div class=\"fitem\">" + "<label>毕业院校:</label><input id='" + myEditId + "_byyx' value='" + byyx + "'/></div>" + "<div class=\"fitem\"><label>专业:</label><input id='" + myEditId + "_zy' value='" + zy + "'/></div>" + "</form>";
        $("#" + myEditId).dialog({
            shadow: true,
            modal: true,
            resizable: true,
            width: 400,
            height: 200,
            title: title + '毕业院校及专业',
            content: contentHtml,
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function() {
                    if (type == 1) {
                        rowData.qrzbyyx = $("#" + myEditId + "_byyx").val();
                        rowData.qrzzy = $("#" + myEditId + "_zy").val()
                    } else {
                        rowData.zzbyyx = $("#" + myEditId + "_byyx").val();
                        rowData.zzzy = $("#" + myEditId + "_zy").val()
                    }
                    rowData = fullEditFields(rowData, editIndex);
                    saveItem(rowData, 
                    function() {
                        var opts = $("#mc").datagrid('options');
                        var rowDatas = $("#mc").datagrid('getData');
                        var newrows = rowDatas.rows;
                        for (var i = 0; i < newrows.length; i++) {
                            if (newrows[i].id == rowData.id) {
                                newrows[i] = rowData;
                                break
                            }
                        }
                        rowDatas.rows = newrows;
                        $("#mc").datagrid('loadData', rowDatas)
                    });
                    $("#" + myEditId).dialog('destroy');
                    $("#" + myEditId).remove()
                }
            },
            {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function() {
                    $("#" + myEditId).dialog('destroy');
                    $("#" + myEditId).remove()
                }
            }]
        })
    },
    zhiw: function(target, editIndex) {
        var myEditId = 'myEdit_' + System.random();
        var html = "<div id='" + myEditId + "'></div>";
        $(target).parent().append(html);
        var rowData = $("#mc").datagrid('getRows')[editIndex];
        $("#" + myEditId).window({
            shadow: true,
            modal: true,
            resizable: true,
            collapsible: false,
            minimizable: false,
            maximizable: false,
            width: 470,
            height: 300,
            title: '编辑职务信息',
            content: "<iframe border='0' width='100%' height='100%' frameborder='0' src='" + System.rootPath + "/zw/zw-modify-show?personcode=" + rowData.personcode + "&controlId=" + myEditId + "&editIndex=" + editIndex + "'></iframe>",
            onClose: function() {
                $("#" + myEditId).dialog('destroy');
                $("#" + myEditId).remove()
            }
        })
    },
    xlxw: function(target, editIndex, type) {
        var myEditId = 'myEdit_' + System.random();
        var html = "<div id='" + myEditId + "'></div>";
        $(target).parent().append(html);
        var rowData = $("#mc").datagrid('getRows')[editIndex];
        var contentHtml = "<div class='easyui-layout' fit=true>" + "<div region=\"north\" style=\"height:240px;\"><iframe id='xl' name='xl' border='0' width='100%' height='100%' frameborder='0' src='" + System.rootPath + "/xlxw/xl-modify-show?personcode=" + rowData.personcode + "&xltype=" + type + "&controlId=" + myEditId + "&editIndex=" + editIndex + "'></iframe></div>" + "<div region=\"center\"><iframe id='xw' name='xw' border='0' width='100%' height='100%' frameborder='0' src='" + System.rootPath + "/xlxw/xw-modify-show?personcode=" + rowData.personcode + "&xltype=" + type + "&controlId=" + myEditId + "&editIndex=" + editIndex + "'></iframe></div>" + "</div>";
        $("#" + myEditId).window({
            shadow: true,
            modal: true,
            resizable: true,
            collapsible: false,
            minimizable: false,
            maximizable: false,
            width: 600,
            height: 500,
            title: '编辑学历学位信息',
            content: contentHtml,
            onClose: function() {
                $("#" + myEditId).dialog('destroy');
                $("#" + myEditId).remove()
            }
        })
    }
};
function fullEditFields(row, rowIndex) {
    var editors = $("#mc").datagrid('getEditor', {
        index: rowIndex,
        field: "birthday"
    });
    row.birthday = $("#mc").datagrid('getEditor', {
        index: rowIndex,
        field: "birthday"
    }).target.val();
    row.gongzsj = $("#mc").datagrid('getEditor', {
        index: rowIndex,
        field: "gongzsj"
    }).target.val();
    row.xianjsj = $("#mc").datagrid('getEditor', {
        index: rowIndex,
        field: "xianjsj"
    }).target.val();
    row.remark = $("#mc").datagrid('getEditor', {
        index: rowIndex,
        field: "remark"
    }).target.val();
    return row
}
function xlCallback(entity, type, editIndex, controlId) {
    if (entity) {
        var rowData = $("#mc").datagrid('getRows')[editIndex];
        if (type == 1) {
            rowData.qrzxl = entity.xlshow;
            rowData.qrzxlCode = entity.xl;
            rowData.qrzbyyx = entity.school;
            rowData.qrzzy = entity.sxzymc
        } else {
            rowData.zzxl = entity.xlshow;
            rowData.zzxlCode = entity.xl;
            rowData.zzbyyx = entity.school;
            rowData.zzzy = entity.sxzymc
        }
        var rowDatas = $("#mc").datagrid('getData');
        var newrows = rowDatas.rows;
        for (var i = 0; i < newrows.length; i++) {
            if (newrows[i].id == rowData.id) {
                newrows[i] = rowData;
                break
            }
        }
        rowDatas.rows = newrows;
        $("#mc").datagrid('loadData', rowDatas)
    }
}
function xwCallback(json, type, editIndex, controlId) {
    if (entity) {
        var rowData = $("#mc").datagrid('getRows')[editIndex];
        if (type == 1) {
            rowData.qrzxw = entity.xwshow;
            rowData.qrzxwCode = entity.xw
        } else {
            rowData.zzxw = entity.xwshow;
            rowData.zzxwCode = entity.xw
        }
        var opts = $("#mc").datagrid('options');
        var rowDatas = $("#mc").datagrid('getData');
        var newrows = rowDatas.rows;
        for (var i = 0; i < newrows.length; i++) {
            if (newrows[i].id == rowData.id) {
                newrows[i] = rowData;
                break
            }
        }
        rowDatas.rows = newrows;
        $("#mc").datagrid('loadData', rowDatas);
        $("#mc").datagrid("beginEdit", editIndex)
    }
}
function zhiwCallback(value, editIndex, controlId) {
    var rowData = $("#mc").datagrid('getRows')[editIndex];
    rowData.zhiw = value;
    var opts = $("#mc").datagrid('options');
    var rowDatas = $("#mc").datagrid('getData');
    var newrows = rowDatas.rows;
    for (var i = 0; i < newrows.length; i++) {
        if (newrows[i].id == rowData.id) {
            newrows[i] = rowData;
            break
        }
    }
    rowDatas.rows = newrows;
    $("#mc").datagrid('loadData', rowDatas);
    $("#mc").datagrid("beginEdit", editIndex)
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
        $.messager.confirm('提示', '确定要删除选中行吗?', 
        function(r) {
            if (r) {
                var deleteIndex = [];
                $.each(rows, 
                function(i, row) {
                    $.post(System.rootPath + '/mingc/mingc!remove.action', {
                        'entity.id': row.personcode
                    },
                    function() {
                        var index = $('#mc').datagrid('getRowIndex', row);
                        deleteIndex.push(index);
                        if (i == rows.length - 1) {
                            $.each(deleteIndex, 
                            function(j, d) {
                                $('#mc').datagrid('deleteRow', d)
                            })
                        }
                    })
                })
            }
        })
    }
}
function editorCls(index) {
    var row = $('#mc').datagrid('getRows')[index];
    if (row.isNewRecord) {
        $('#mc').datagrid('deleteRow', index)
    } else {
        $('#mc').datagrid('collapseRow', index)
    }
}
function saveItem(entity, callback) {
    var url = System.rootPath + '/mingc/mingc!modify.action?entity.id=' + entity.personcode;
    System.openLoadMask("#mc", "正在保存...");
    $.ajax({
        type: "POST",
        url: url,
        data: {
            'entityData': encodeURI($.toJSON(entity))
        },
        success: function(data) {
            callback()
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            $.messager.alert("提示", "修改失败,请重试!")
        },
        complete: function() {
            System.closeLoadMask("#mc")
        }
    })
}
function updateRow(entity) {
    var index = $("#rowIndex_" + entity.personcode).val();
    $('#mc').datagrid('updateRow', {
        index: index,
        row: entity
    })
}
function MingcEntity() {
    this.id;
    this.personcode;
    this.xingm;
    this.sex;
    this.sexCode;
    this.zhiw;
    this.minz;
    this.minzCode;
    this.birthday;
    this.jig;
    this.jigCode;
    this.chusd;
    this.chusdCode;
    this.qrzxlId;
    this.qrzxl;
    this.qrzxwId;
    this.qrzxw;
    this.qrzxlCode;
    this.qrzxwCode;
    this.qrzbyyx;
    this.qrzzy;
    this.zzxlId;
    this.zzxl;
    this.zzxwId;
    this.zzxw;
    this.zzxlCode;
    this.zzxwCode;
    this.zzbyyx;
    this.zzzy;
    this.gongzsj;
    this.rudsj;
    this.zzmm;
    this.zzmmCode;
    this.xianzsj;
    this.tongjsj;
    this.xianjsj;
    this.unitcode;
    this.unitname;
    this.reamrk;
    this.idcard
}