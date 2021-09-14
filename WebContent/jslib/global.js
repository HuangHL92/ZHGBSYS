// 定义常量

// -- 干部表格，固定列，包括一个checkbox列
var PERSON_FROZEN_COLUMNS_WITH_CHECKBOX = [[
{
	field: 'id',
    hidden: true
},
{field:'ck',checkbox:true},
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
}]];

// -- 干部表格，固定列，包括一个checkbox列
var PERSON_FROZEN_COLUMNS_WITH_CHECKBOX_2 = [[
{
	field: 'id',
    hidden: true
},
{field:'ck',checkbox:true},
/*
{
	field: 'isConfirmed',
	title: ' ',
	align: "center",
	width: 32,
	formatter: function(value, rowData, rowIndex) {
		if (value == "1") {
			return "<img src='/govhr/style/default/images/seal.png' border='0' title='三龄一历已审核' style='vertical-middle: middle;'/>";
		} else {
			return "<img src='/govhr/style/default/images/unseal.png' border='0' title='三龄一历未审核' style='vertical-middle: middle;'/>";
		}
	}
},*/
{
	field: 'photo',
	title: '照片',
	align: "center",
	width: 60,
	formatter: function(value, rowData, rowIndex) {
		var content = "<a href='javascript:void(0);' onclick='System.leaderQuery.showRemoval("+rowData.personcode+");'>";
		content += '<img border="0" src="' + System.rootPath + '/lob/photo.action?rand=' + Math.random() + '&personcode=' + rowData.personcode + '&zoom=true&width=50&height=66" width="50" height="66" />';
		content += "</a>";
		return content;
	}
},
{
	field: 'xingm',
	title: '姓名',
	align: "center",
	width: 60,
	formatter: function(value, rowData, rowIndex) {
		var sexCode = rowData.sexCode;
		if(sexCode == "2"){
			return "<a href='javascript:void(0);' onclick='System.leaderQuery.showRemoval("+rowData.personcode+");'>"+value+"</a><br/>(女)";
		}else{
			return value == undefined ? "" : "<a href='javascript:void(0);' onclick='System.leaderQuery.showRemoval("+rowData.personcode+")'>"+value+"</a>";
		}
	}
}]];

// -- 干部表格，固定列
var PERSON_FROZEN_COLUMNS = [[
{
	field: 'id',
    hidden: true
},
/*
{
	field: 'isConfirmed',
	title: ' ',
	align: "center",
	width: 32,
	formatter: function(value, rowData, rowIndex) {
		if (value == "1") {
			return "<img src='/govhr/style/default/images/seal.png' border='0' title='三龄一历已审核' style='vertical-middle: middle;'/>";
		} else {
			return "<img src='/govhr/style/default/images/unseal.png' border='0' title='三龄一历未审核' style='vertical-middle: middle;'/>";
		}
	}
},*/
{
	field: 'photo',
	title: '照片',
	align: "center",
	width: 60,
	formatter: function(value, rowData, rowIndex) {
		var content = "<a href='javascript:void(0);' onclick='System.leaderQuery.showRemoval("+rowData.personcode+");'>";
		content += '<img border="0" src="' + System.rootPath + '/lob/photo.action?rand=' + Math.random() + '&personcode=' + rowData.personcode + '&zoom=true&width=50&height=66" width="50" height="66" />';
		content += "</a>";
		return content;
	}
},
{
	field: 'xingm',
	title: '姓名',
	align: "center",
	width: 60,
	formatter: function(value, rowData, rowIndex) {
		var sexCode = rowData.sexCode;
		if(sexCode == "2"){
			return "<a href='javascript:void(0);' onclick='System.leaderQuery.showRemoval("+rowData.personcode+");'>"+value+"</a><br/>(女)";
		}else{
			return value == undefined ? "" : "<a href='javascript:void(0);' onclick='System.leaderQuery.showRemoval("+rowData.personcode+")'>"+value+"</a>";
		}
	}
}]];
var PERSON_ZHBGBKCMC2 = [[{
	    field: 'sex',
	    title: '性别',
	    align: "center",
		rowspan: 2,
	    width: 40,
	    resizable: true
	},
	{
	    field: 'minz',
	    title: '民族',
		rowspan: 2,
	    align: "center",
	    width: 60,
	    resizable: true
	},
	{
	    field: 'zhiw',
	    title: '现任职务',
		rowspan: 2,
	    align: "left",
	    width: 150,
	    resizable: true
	},
//	{
//        field: '',
//        title: '原任职务',
//		rowspan: 2,
//        align: "left",
//        width: 150,
//        resizable: true
//    },
	{
	    field: 'birthday',
	    title: '出生年月',
		rowspan: 2,
	    align: "center",
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
	    field: 'rudsj',
	    title: '入党时间',
		rowspan: 2,
		align: "center",
	    width: 80
	},
	{
	    field: 'gongzsj',
	    title: '参加工作时间',
		rowspan: 2,
		align: "center",
	    width: 80
	},
	{
	    field: 'xianjsj',
	    title: '任现级别时间',
		rowspan: 2,
		align: "center",
	    width: 80
	},
	{
	    field: 'xianzsj',
	    title: '任现职时间',
		rowspan: 2,
		align: "center",
	    width: 80
	},
//	{
//        field: '',
//        title: '日常管理监督处理情况',
//		rowspan: 2,
//        width: 150,
//        align: "left",
//    },
	{
	    field: 'remark',
		rowspan: 2,
		align: 'center',
	    title: '备注',
	    width: 80
	}
	], [{
	    field: 'qrzjy_xlxw',
	    title: '学历<br/>&nbsp;学位',
	    width: 100,
	    align: "center",
	},
	{
	    field: 'qrzjy_yxjzy',
	    title: '毕业院校<br/>&nbsp;系及专业',
	    width: 120,
	    align: "center",
	},
	{
	    field: 'zzjy_xlxw',
	    title: '学历<br/>&nbsp;学位',
	    width: 100,
	    align: "center",
	},
	{
	    field: 'zzjy_yxjzy',
	    title: '毕业院校<br/>&nbsp;系及专业',
	    width: 120,
	    align: "center",
	}]];
var PERSON_ZHBGBKCMC = [[{
	    field: 'sex',
	    title: '性别',
	    align: "center",
		rowspan: 2,
	    width: 40,
	    resizable: true
	},
	{
	    field: 'minz',
	    title: '民族',
		rowspan: 2,
	    align: "center",
	    width: 60,
	    resizable: true
	},
	{
	    field: 'zhiw',
	    title: '现任职务',
		rowspan: 2,
	    align: "left",
	    width: 150,
	    resizable: true
	},
//	{
//        field: '',
//        title: '原任职务',
//		rowspan: 2,
//        align: "left",
//        width: 150,
//        resizable: true
//    },
	{
	    field: 'birthday',
	    title: '出生年月',
		rowspan: 2,
	    align: "center",
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
	    field: 'rudsj',
	    title: '入党时间',
		rowspan: 2,
		align: "center",
	    width: 80
	},
	{
	    field: 'gongzsj',
	    title: '参加工作时间',
		rowspan: 2,
		align: "center",
	    width: 80
	},
	{
	    field: 'xianjsj',
	    title: '任现级别时间',
		rowspan: 2,
		align: "center",
	    width: 80
	},
	{
	    field: 'xianzsj',
	    title: '任现职时间',
		rowspan: 2,
		align: "center",
	    width: 80
	},
//	{
//        field: '',
//        title: '日常管理监督处理情况',
//		rowspan: 2,
//        width: 150,
//        align: "left",
//    },
	{
	    field: 'remark',
		rowspan: 2,
		align: 'center',
	    title: '备注',
	    width: 80
	},
	{
	    field: 'ckrmb',
	    title: '',
	    rowspan: 2,
		align: 'center',
	    width: 80,
		formatter: function(value, rowData, rowIndex) {
			return '<a href="#" onclick="System.report.printRemoval(' + rowData.personcode + ');">导出任免表</a>';
		}
	}, 
	{
	    field: 'ckjyqkb',
	    title: '',
	    rowspan: 2,
		align: 'center',
	    width: 100,
		formatter: function(value, rowData, rowIndex) {
			return '<a href="#" onclick="System.report.printSimpleDetails(' + rowData.personcode + ');">导出基本情况表</a>';
		}
	}, 
	{
	    field: 'viewarchive',
	    title: '',
	    rowspan: 2,
	    align: 'center',
	    width: 80,
		formatter: function(value, rowData, rowIndex) {
			var html = "<a href=\"javascript:void(0);\" onclick=\"Archive.viewArchive(" + rowData.personcode + ",'" + Utils.toVarString(rowData.xingm) + "');\">查阅档案</a>";
			return html;
		}
	}], [{
	    field: 'qrzjy_xlxw',
	    title: '学历<br/>&nbsp;学位',
	    width: 100,
	    align: "center",
	},
	{
	    field: 'qrzjy_yxjzy',
	    title: '毕业院校<br/>&nbsp;系及专业',
	    width: 120,
	    align: "center",
	},
	{
	    field: 'zzjy_xlxw',
	    title: '学历<br/>&nbsp;学位',
	    width: 100,
	    align: "center",
	},
	{
	    field: 'zzjy_yxjzy',
	    title: '毕业院校<br/>&nbsp;系及专业',
	    width: 120,
	    align: "center",
	}]];
// -- 干部表格，非固定列
var PERSON_COLUMNS = [[{
            field: 'birthday',
            title: '出生年月',
            align: "center",
            //rowspan: 2,
            width: 80,
            resizable: true
        },{
            field: 'zhiw',
            title: '职务',
            //rowspan: 2,
            width: 400,
            resizable: true
        }/*,
        {
        	field: 'zhiji',
        	title: '职级',
        	align: "left",
        	//rowspan:2,
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
        }*/,
        {
            field: 'xianjsj',
            title: '任现职级时间',
            //rowspan: 2,
			align: 'center',
            width: 80
        },
        {
            field: 'xianzsj',
            title: '任现职时间',
            //rowspan: 2,
			align: 'center',
            width: 70
        }, 
		{
            field: 'ckrmb',
            title: '',
            //rowspan: 2,
			align: 'center',
            width: 80,
			formatter: function(value, rowData, rowIndex) {
				return '<a href="#" onclick="System.report.printRemoval(' + rowData.personcode + ');">导出任免表</a>';
			}
        }, 
		{
            field: 'ckjyqkb',
            title: '',
            //rowspan: 2,
			align: 'center',
            width: 100,
			formatter: function(value, rowData, rowIndex) {
				return '<a href="#" onclick="System.report.printSimpleDetails(' + rowData.personcode + ');">导出基本情况表</a>';
			}
        },{
            field: 'viewarchive',
            title: '',
            align: 'center',
            width: 80,
			formatter: function(value, rowData, rowIndex) {
				var html = "<a href=\"javascript:void(0);\" onclick=\"Archive.viewArchive(" + rowData.personcode + ",'" + Utils.toVarString(rowData.xingm) + "');\">查阅档案</a>";
				return html;
			}
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
        }]*/];

//--厅级干部培训表格
var TRAIN__COLUMNS = [[
{
    field: 'xingm',
	title: '姓名',
	align: "center",
	rowspan:2,
	width: 60,
	formatter: function(value, rowData, rowIndex) {
		var sexCode = rowData.sexCode;
		if (sexCode == "2") {
			return value == undefined ? "": "<a href=\"javascript:void(0);\" onclick=\"detail(" + rowData.personcode + ",'" + Utils.toVarString(rowData.xingm) + "');\">" + value + "</a>" + "<br/>(女)"
		} else {
			return value == undefined ? "": "<a href=\"javascript:void(0);\" onclick=\"detail(" + rowData.personcode + ",'" + Utils.toVarString(rowData.xingm) + "');\">" + value + "</a>"
		}
	}
},
{
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
    width: 80,
    resizable: true
},
{
    field: 'rudsj',
    title: '入党时间',
    align: "center",
    rowspan: 2,
    width: 80,
    resizable: true
}, 
{
    field: 'gongzsj',
    title: '工作时间',
	align: "center",
    rowspan: 2,
    width: 80
},
{
    field: 'xianzsj',
    title: '任现职时间',
	align: "center",
    rowspan: 2,
    width: 70, 
	formatter: function (value, row, rowIndex) {
		if (value != null) {
			return value.replaceAll('\\r\\n', '<br/>');
		}
		else {
			return '';
		}
	}
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
    width: 120,
	formatter: function(value, rowData, rowIndex) {
		return '<a href="#" onclick="System.report.printTrainingDetails(' + rowData.personcode + ');">下载干部培训信息表</a>';
	}
}],
[{
	field: 'gncs',
	title: '累计次数',
    //rowspan: 2,
    align: 'center',
    width: 80
},
{
    field: 'gnts',
    title: '累计天数',
    //rowspan: 2,
    align: 'center',
    width: 80
},
{
    field: 'cgcs',
    title: '累计次数',
    //rowspan: 2,
    align: 'center',
    width: 80
},
{
    field: 'cgts',
	title: '累计天数',
	//rowspan: 2,
	align: 'center',
	width: 80
}]];


var TRAIN_MANAGE_COLUMNS = [[                  
{	
	field: 'id',
    hidden: true
},{
	field: 'direct_unit',
	title: '主办单位',
	align: 'center',
	width: 80
},{
	field: 'organization',
	title: '承办机构',
	align: 'center',
	width: 80
},{
	field: 'class_name',
	title: '班次名称',
	align: 'center',
	width: 80
},{
	field: 'start_date',
	title: '培训开始时间',
	align: 'center',
	width: 80,
	formatter: function(value, row, index) {
		return Utils.toDate(value);
	}
	
},{
	field: 'end_date',
	title: '培训结束时间',
	align: 'center',
	width: 80,
	formatter: function(value, row, index) {
		return Utils.toDate(value);
	}
},{
	field: 'days',
	title: '学制',
	align: 'center',
	width: 80
}]];
		
// --- 反查表格列
var PERSON_COLUMNS_FOR_VIEW = [[
		                {field:'personcode',width:20,hidden:true},
		                {title:'姓名',field:'A000_A0101',width:70, sortable: true,formatter:function(value,rowData,rowIndex){ 
		                	return "<a href='javascript:void(0)' onclick='System.leaderQuery.showRemoval(" + rowData.personcode + ")'>" + value + "</a>";
		                	}
		                },
		                {title:'性别',field:'A000_A0104_SHOW',width:40, sortable: true},
		                {title:'出生日期',field:'A000_A0107',width:60, sortable: true},
		                {title:'出生地',field:'A000_A0114_SHOW',width:65, sortable: true},
		                {title:'籍贯',field:'A000_A0111_SHOW',width:65, sortable: true},
		                {title:'职级',field:'zhij',width:50, sortable: true},
		                {title:'职务',field:'zhiw',width:500, sortable: true}
					]];