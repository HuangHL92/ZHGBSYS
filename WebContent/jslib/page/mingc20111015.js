$(document).ready(function(){
	$("#mc").datagrid({
		height : 440,
		rownumbers:true,
		singleSelect:false,
		pagination:true, 
		pageSize : 10,
		pageList : [10,20,30],
		idField:'id',
		sortName:'xingm',
		sortOrder:'asc',
		view: detailview,  
		detailFormatter:function(index,row){  
			return "<div id='modify-"+index+"' class='ddv' style='padding:5px 0;'></div>";  
		}, 
		frozenColumns:[[
			{field:'id',hidden:true},
			{field:'xingm',title:'姓名',align:"center",width:60,resizable:true,
				formatter:function(value,rowData,rowIndex){
					var sexCode= rowData.sexCode;
					if(sexCode == "2"){
						return value+"<br/>(女)";
					}else{
						return value == undefined ? "" : value;
					}
				}
			}
		]],
		columns:[[
			{field:'zhiw',title:'职务',align:"left",rowspan:2,width:100,resizable:true},
			{field:'minz',title:'民族',align:"center",rowspan:2,width:60,resizable:true},
			{field:'birthday',title:'出生年月',align:"center",rowspan:2,width:80,resizable:true},
			
			{field:'jig',title:'籍贯',rowspan:2,align:"center",width:80,resizable:true},
			{field:'chusd',title:'出生地',rowspan:2,align:"center",width:80,resizable:true},
			{title:'全日制教育',colspan:2,align:"center"},
			{title:'在职教育',colspan:2,align:"center"},
			{field:'gongzsj',title:'工作时间',rowspan:2,width:80},
			{field:'rudsj',title:'入党时间',rowspan:2,width:80,
				formatter:function(value,rowData,rowIndex){
					var zzmmCode= rowData.zzmmCode;
					if(zzmmCode != "01"){
						if(rowData.zzmm != undefined && rowData.zzmm != ""){
							return value+"<br/>("+rowData.zzmm+")";
						}else{
							return value;
						}
					}else{
						return value == undefined ? "" : value;
					}
				}
			},
			{field:'xianzsj',title:'现职时间',rowspan:2,width:80}
		],[
			{field:'qrzxl',title:'学历学位',width:100,align:"center",
				formatter:function(value,rowData,rowIndex){
					value = "";
					if(rowData.qrzxl != undefined && rowData.qrzxl != ""){
						value += rowData.qrzxl;
					}
					if(rowData.qrzxw != undefined && rowData.qrzxw != ""){
						value += "<br/>"+rowData.qrzxw;
					}
					return value;
				}
			},
			{field:'qrzbyyx',title:'毕业院校及专业',width:120,align:"center",
				formatter:function(value,rowData,rowIndex){
					value = "";
					if(rowData.qrzbyyx != undefined && rowData.qrzbyyx != ""){
						value += rowData.qrzbyyx;
					}
					if(rowData.qrzzy != undefined && rowData.qrzzy != ""){
						value += "<br/>"+rowData.qrzzy;
					}
					return value;
				}
			},
			{field:'zzxl',title:'学历学位',width:100,align:"center",
				formatter:function(value,rowData,rowIndex){
					value = "";
					if(rowData.zzxl != undefined && rowData.zzxl != ""){
						value += rowData.zzxl;
					}
					if(rowData.zzxw != undefined && rowData.zzxw != ""){
						value += "<br/>"+rowData.zzxw;
					}
					return value;
				}
			},
			{field:'zzbyyx',title:'毕业院校及专业',width:120,align:"center",
				formatter:function(value,rowData,rowIndex){
					value = "";
					if(rowData.zzbyyx != undefined && rowData.zzbyyx != ""){
						value += rowData.zzbyyx;
					}
					if(rowData.zzzy != undefined && rowData.zzzy != ""){
						value += "<br/>"+rowData.zzzy;
					}
					return value;
				}
			}
		]],
		onExpandRow: function(index,row){  
	        $('#modify-'+index).panel({  
	            border:false,
	            cache:true,
	            href:System.rootPath+"/mingc/mingc-modify-show?personcode="+row.personcode,// 访问mingc/mingc-modify-show.jsp
	            onLoad:function(){
	                $('#mc').datagrid('fixDetailRowHeight',index);  
	                $('#mc').datagrid('selectRow',index);  
	                $('#mc').datagrid('getRowDetail',index).find('form').form('load',row);
	                
	                $("#rowIndex_"+row.personcode).val(index);
	                $("#id_"+row.personcode).val(row.personcode);
	                fullSelect(row);
	            }
	        });  
	        $('#mc').datagrid('fixDetailRowHeight',index); 
	    },
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
	    	var obj = $("#printData").data("pageSelects");
	    	if(obj){
	    		var pageNo = $("#page").val();
	    		var pageSelect = System.getElement(obj,"pageNo",pageNo);
	    		if(pageSelect){
	    			var selecteds = pageSelect.selecteds;
	    			if(selecteds){
	    				selecteds = System.removeElement(selecteds,"rowIndex",rowIndex);
	    				pageSelect.selecteds = selecteds;
	    				obj.push(pageSelect);
	    				$("#printData").data("pageSelects",obj);
	    			}
		    	}
	    	}
	    },
	    onLoadSuccess:function(data){
	    	$("#mc").datagrid('unselectAll');
			var options = $("#mc").datagrid('options');
			$("#page").val(options.pageNumber);
			$("#rows").val(options.pageSize);
			setTimeout("initSelect()",10);
			System.button.isView($("#serviceid").val(),$("#currentUnit").val(),function(back){
				if(!back.isView){
					$("#btn_add").linkbutton("enable");
					$("#btn_remove").linkbutton("enable");
				}else{
					$("#btn_add").linkbutton("disable");
					$("#btn_remove").linkbutton("disable");
				}
			});
		},
		toolbar:[{
			id:'btn_add',
			iconCls:'icon-add',
			text:'添加',
			disabled:true,
		    handler:function(){
				newItem();
			}
		},'-',{
			id:'btn_remove',
			iconCls:'icon-remove',
			text:'删除',
			disabled:true,
		    handler:function(){
				destroyItem();
			}
		}]
	});
});
function loadMcData(){
	clearSelect();
	var queryParam = $("#mc").datagrid('options').queryParams;
	queryParam.serviceid = $("#serviceid").val();
	queryParam.currentUnit = $("#currentUnit").val();
	$("#mc").datagrid('options').url=System.rootPath+"/mingc/mingc!queryByUnit.action";
	$("#mc").datagrid('reload');
}
function loadMcDataByQuerykey(){
	var queryParam = $("#mc").datagrid('options').queryParams;
	queryParam.serviceid = $("#serviceid").val();
	queryParam.querykey = encodeURI($("#querykey").val().trim());
	$("#mc").datagrid('options').url=System.rootPath+"/mingc/mingc!queryByQuerykey.action";
	$("#mc").datagrid('reload');
	clearSelect();
	$("#btn_add").linkbutton("disable");
	$("#btn_remove").linkbutton("disable");
}
function loadMcDataByLevelQuery(condition){
	if(condition){
		var queryParam = $("#mc").datagrid('options').queryParams;
		queryParam.serviceid = $("#serviceid").val();
		queryParam.group = condition.conditionGroupkey;
		queryParam.tables = condition.conditionTablekey;
		queryParam.querySQL = condition.conditionQuerySQL;
		$("#mc").datagrid('options').url=System.rootPath+"/query/level-query!execSearch.action";
		$("#mc").datagrid('reload');
		clearSelect();
		$("#btn_add").linkbutton("disable");
		$("#btn_remove").linkbutton("disable");
	}
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
	$("#printData").data("pageSelects",[]);
}

function newItem(){
	$('#mc').datagrid('appendRow',{isNewRecord:true});
	var index = $('#mc').datagrid('getRows').length - 1;
	$('#mc').datagrid('expandRow', index);
	$('#mc').datagrid('selectRow', index);
}

function destroyItem(){
	var row = $('#mc').datagrid('getSelected');
	if (row){
		$.messager.confirm('提示','确定要删除吗?',function(r){
			if(r){
				var index = $('#mc').datagrid('getRowIndex',row);
				$.post(System.rootPath+'/mingc/mingc!remove.action',{'entity.id':row.personcode},function(){
					$('#mc').datagrid('deleteRow',index);
				});
			}
		});
	}
}

function editorCls(personcode){
	var index = $("#rowIndex_"+personcode).val();
	var row = $('#mc').datagrid('getRows')[index];
	if (row.isNewRecord){
		$('#mc').datagrid('deleteRow',index);
	} else {
		$('#mc').datagrid('collapseRow',index);
	}
}

function saveItem(personcode){
	var index = $("#rowIndex_"+personcode).val();
	var row = $('#mc').datagrid('getRows')[index];
	var entity = getRowDetail(personcode);
	var url = System.rootPath+'/mingc/mingc!modify.action?entity.id='+personcode;
	if(row.isNewRecord){
		url = System.rootPath+'/mingc/mingc!add.action';
	}
	System.openLoadMask("#mc","正在保存...");
	$.ajax({
		type: "POST",
		url: url,
		data:{'entityData':encodeURI($.toJSON(entity))},
		success: function(data){
			if(row.isNewRecord){
				row.isNewRecord = false;
				entity.isNewRecord = false;
			}
			updateRow(entity);
			$.messager.alert("info",data);
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			$.messager.alert("info","保存失败,请重试!");
		},
		complete:function(){
			System.closeLoadMask("#mc");
		}
	});
	
}

function updateRow(entity){
	var index = $("#rowIndex_"+entity.personcode).val();
	$('#mc').datagrid('collapseRow',index);  
    $('#mc').datagrid('updateRow',{  
        index: index,  
        row: entity
    }); 
}

function fullSelect(row){
	
	System.code.delay("#sexCode_"+row.personcode,$("#sexCode_"+row.personcode).attr("codeClass"),$("#sexCode_"+row.personcode).val(),
			$("#sex_"+row.personcode).val(),150,function(node){
		$("#sexCode_"+row.personcode).val(node.id);
		$("#sex_"+row.personcode).val(node.text);
	});
	
	System.code.delay("#minzCode_"+row.personcode,$("#minzCode_"+row.personcode).attr("codeClass"),$("#minzCode_"+row.personcode).val(),
			$("#minz_"+row.personcode).val(),150,function(node){
		$("#minzCode_"+row.personcode).val(node.id);
		$("#minz_"+row.personcode).val(node.text);
	});
	
	System.code.delay("#jigCode_"+row.personcode,$("#jigCode_"+row.personcode).attr("codeClass"),$("#jigCode_"+row.personcode).val(),
			$("#jig_"+row.personcode).val(),150,function(node){
		$("#jigCode_"+row.personcode).val(node.id);
		$("#jigCode_"+row.personcode).val(node.text);
	});
	
	System.code.delay("#chusdCode_"+row.personcode,$("#chusdCode_"+row.personcode).attr("codeClass"),$("#chusdCode_"+row.personcode).val(),
			$("#chusd_"+row.personcode).val(),150,function(node){
		$("#chusdCode_"+row.personcode).val(node.id);
		$("#chusd_"+row.personcode).val(node.text);
	});
	
	System.code.delay("#zzmmCode_"+row.personcode,$("#zzmmCode_"+row.personcode).attr("codeClass"),$("#zzmmCode_"+row.personcode).val(),
			$("#zzmm_"+row.personcode).val(),150,function(node){
		$("#zzmmCode_"+row.personcode).val(node.id);
		$("#zzmm_"+row.personcode).val(node.text);
	});
	
	$("#qrzbyyx_"+row.personcode).attr("disabled",true);
	$("#qrzzy_"+row.personcode).attr("disabled",true);
	$("#zzbyyx_"+row.personcode).attr("disabled",true);
	$("#zzzy_"+row.personcode).attr("disabled",true);
	
	if(row.isNewRecord){
		$("#zhiw_"+row.personcode).attr("disabled",true);
		$("#qrzxl_"+row.personcode).attr("disabled",true);
		$("#qrzxw_"+row.personcode).attr("disabled",true);
		$("#zzxlCode_"+row.personcode).attr("disabled",true);
		$("#zzxwCode_"+row.personcode).attr("disabled",true);
	}else{
		System.combogrid.init("#zhiw_"+row.personcode,System.rootPath+"/zw/zw-modify-show?personcode="+row.personcode,function(data){
			$("#zhiw_"+row.personcode).val(data);
		});
		System.combogrid.init("#qrzxl_"+row.personcode,System.rootPath+"/xlxw/xl-modify-show?personcode="+row.personcode+"&xltype=1",function(data){
			$("#qrzxlCode_"+row.personcode).val(data.xl);
			$("#qrzxl_"+row.personcode).val(data.xlshow);
			$("#qrzbyyx_"+row.personcode).val(data.school);
			$("#qrzzy_"+row.personcode).val(data.sxzymc);
		});
		System.combogrid.init("#qrzxw_"+row.personcode,System.rootPath+"/xlxw/xw-modify-show?personcode="+row.personcode+"&xwtype=1",function(data){
			$("#qrzxwCode_"+row.personcode).val(data.xw);
			$("#qrzxw_"+row.personcode).val(data.xwshow);
		});
		System.combogrid.init("#zzxl_"+row.personcode,System.rootPath+"/xlxw/xl-modify-show?personcode="+row.personcode+"&xltype=2",function(data){
			$("#zzxlCode_"+row.personcode).val(data.xl);
			$("#zzxl_"+row.personcode).val(data.xlshow);
			$("#zzbyyx_"+row.personcode).val(data.school);
			$("#zzzy_"+row.personcode).val(data.sxzymc);
		});
		System.combogrid.init("#zzxw_"+row.personcode,System.rootPath+"/xlxw/xw-modify-show?personcode="+row.personcode+"&xwtype=2",function(data){
			$("#zzxwCode_"+row.personcode).val(data.xw);
			$("#zzxw_"+row.personcode).val(data.xwshow);
		});
	}
}

function getRowDetail(personcode){
	var entity = new MingcEntity();
	entity.id = $("#id_"+personcode).val();
	entity.personcode = personcode;
	entity.xingm = $("#xingm_"+personcode).val().trim();
	entity.sex = $("#sexCode_"+personcode).combotree('getText');
	entity.sexCode = $("#sexCode_"+personcode).combotree('getValue');
	entity.zhiw = $("#zhiw_"+personcode).val();
	entity.minz = $("#minzCode_"+personcode).combotree('getText');
	entity.minzCode = $("#minzCode_"+personcode).combotree('getValue');
	entity.birthday = $("#birthday_"+personcode).val();
	entity.jig = $("#jigCode_"+personcode).combotree('getText');
	entity.jigCode = $("#jigCode_"+personcode).combotree('getValue');
	entity.chusd = $("#chusdCode_"+personcode).combotree('getText');
	entity.chusdCode = $("#chusdCode_"+personcode).combotree('getValue');
	entity.qrzbyyx = $("#qrzbyyx_"+personcode).val().trim();
	entity.qrzzy = $("#qrzzy_"+personcode).val().trim();
	entity.zzbyyx = $("#zzbyyx_"+personcode).val().trim();
	entity.zzzy = $("#zzzy_"+personcode).val().trim();
	entity.gongzsj = $("#gongzsj_"+personcode).val();
	entity.rudsj = $("#rudsj_"+personcode).val();
	entity.zzmm = $("#zzmmCode_"+personcode).combotree('getText');
	entity.zzmmCode = $("#zzmmCode_"+personcode).combotree('getValue');
	entity.tongjsj = $("#tongjsj_"+personcode).val();
	entity.xianzsj = $("#xianzsj_"+personcode).val();
	return entity;
}

function MingcEntity(){
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
	this.tongjsj;
	this.xianzsj;
}