$(document).ready(function(){
	$("#mc").datagrid({
		//height : 424,
		//width : $('body').outerWidth(true)-340,
		fit: true,
		nowrap: false,
		rownumbers:true,
		singleSelect:false,
		pagination:true, 
		pageSize : 10,
		pageList : [10,20,30],
		idField:'id',
		sortOrder:'asc',
		frozenColumns:[[
			{field:'id',hidden:true},
			{field:'xingm',title:'姓名',align:"center",width:60,
				formatter:function(value,rowData,rowIndex){
					var sexCode= rowData.sexCode;
					if(sexCode == "2"){
						return value == undefined ? "" : "<a href=\"javascript:void(0);\" onclick=\"System.commonEditor.editorSingePerson("+rowData.personcode+");\">"+value+"</a>"+"<br/>(女)";
					}else{
						return value == undefined ? "" : "<a href=\"javascript:void(0);\" onclick=\"System.commonEditor.editorSingePerson("+rowData.personcode+");\">"+value+"</a>";
					}
				}
			}
		]],
		columns:[[
			{field:'zhiw',title:'职务',align:"left",rowspan:2,width:300,resizable:true},//,rowspan:2
			{field:'minz',title:'民族',align:"center",rowspan:2,width:60,resizable:true},//,rowspan:2
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
							return value == undefined ? "" : value;
						}
					}else{
						return value == undefined ? "" : value;
					}
				}
			},
			
			{field:'xianjsj',title:'任现职级时间',rowspan:2,width:80},
			{field:'xianzsj',title:'任现职时间',rowspan:2,width:80},
			{field:'remark',title:'备注',rowspan:2,width:150}
		],[
			{field:'qrzjy_xlxw',title:'学历学位',width:100,align:"center",
			},
			{field:'qrzjy_yxjzy',title:'毕业院校及专业',width:120,align:"center",
			},
			{field:'zzjy_xlxw',title:'学历学位',width:100,align:"center",
			},
			{field:'zzjy_yxjzy',title:'毕业院校及专业',width:120,align:"center",
			}
		]],
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
			
			checkPersonPermission();
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
	    				//obj.push(pageSelect);
	    				$("#printData").data("pageSelects",obj);
	    			}
		    	}
	    	}
			
			// 如果没有选择任何记录，那么需要disable 某些按钮, by YZQ on 2012/11/03
            var rows = $("#mc").datagrid("getSelections");
            if (rows == null || rows.length <= 0) {
            	$("#btn_remove").linkbutton("disable");                
            }  
			else {
				checkPersonPermission();
			}
	    },
	    onLoadSuccess:function(data){
			disableButtons();	
			
	    	$("#mc").datagrid('unselectAll');
			var options = $("#mc").datagrid('options');
			$("#page").val(options.pageNumber);
			$("#rows").val(options.pageSize);
			
			var title = $("#contentTabs").tabs('getSelected').panel('options').title;
			//if(title == "名册"){
				renderPhotoList(data);
			//}
			setTimeout("initSelect()",10);
		},
		toolbar:[
		{
			id:'btn_remove',
			iconCls:'icon-remove',
			text:'删除',
		    handler:function(){
				destroyItem();
			}
		},'-',{
			id:'btn_printAll',
			text:'<input type=\"checkbox\" id=\"chk_printAll\"/> 打印全部',
		    handler:function(){
				var rows = $("#mc").datagrid('getRows');
				if(rows.length==0){
					$("#chk_printAll").attr('checked',false);
					$.messager.alert('提示','没有数据打印!');
				}
			}
		},'-',{
			id :'xxx'
		}],
		onLoadSuccess:function(data){
			$("#mc").datagrid('clearSelections');
			 addmenu("#xxx");
		}
		
	});
 	$('#mc').pagination({
 		onSelectPage:function(pageNumber,pageSize){
 			var options = $("#mc").datagrid('options');
 			options.pageNumber = pageNumber;
 			options.pageSize = pageSize;
 			$('#mc').datagrid(options);
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
//默认名册	
function changeMC1() {
	if (flag) {
		$("#mc").datagrid({
			columns:[[
						{field:'zhiw',title:'职务',align:"left",rowspan:2,width:300,resizable:true},//,rowspan:2
						{field:'minz',title:'民族',align:"center",rowspan:2,width:60,resizable:true},//,rowspan:2
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
										return value == undefined ? "" : value;
									}
								}else{
									return value == undefined ? "" : value;
								}
							}
						},
						
						{field:'xianjsj',title:'任现职级时间',rowspan:2,width:80},
						{field:'xianzsj',title:'任现职时间',rowspan:2,width:80},
						{field:'remark',title:'备注',rowspan:2,width:150}
					],[
						{field:'qrzjy_xlxw',title:'学历学位',width:100,align:"center",
						},
						{field:'qrzjy_yxjzy',title:'毕业院校及专业',width:120,align:"center",
						},
						{field:'zzjy_xlxw',title:'学历学位',width:100,align:"center",
						},
						{field:'zzjy_yxjzy',title:'毕业院校及专业',width:120,align:"center",
						}
					]]
		});
		flag = false;
	}
}
//中组部名册
function changeMC2() {
	if (!flag) {
		 $("#mc").datagrid({
		        columns: PERSON_ZHBGBKCMC2
		    });
		 flag = true;
	}
}
function printData(){
	var reportid = $("#mm").data('selectReportId');
	if (reportid == '-1') {
		var selectedUnit = $("#tt").tree('getSelected');
		if (selectedUnit == null) {
			$.messager.alert('提示', '请选择一个单位!');
		}
		else {
			$.ajax({
				type:'post',
				url:System.rootPath+"/common/exec-common-method!createPrintLeadySessionKey.action",
				data:{'units':selectedUnit.id},
				success:function(sessionkey){
					System.report.printLeaderBySessionKey(sessionkey, "1"); // 必须是非在职，by YZQ on 2012/09/23
				}
			});
		}
	}
	else {
		var printAll = $("#chk_printAll").attr('checked');
		if(printAll){
			PrintAll(reportid);
		}else{
			var obj = $("#printData").data("pageSelects");
			var keys = "";
			var count = 0;
			if(obj){
				$.each(obj,function(i,v){
					//var selecteds = v.selecteds;
					$.each(v.selecteds,function(j,s){
						if(count>0){
							keys +=",";
						}
						keys += s.keywordId;
						count ++;
					});
				});
				
				System.report.print(keys);
			}
		}
	}
}

function PrintAll(){
	$.messager.confirm('提示','是否打印全部?',function(b){
		if(b){
			var d = $("#mc").data('queryParams');
			d.queryType = $("#mc").data('queryType');
			$.ajax({
				url:System.rootPath+'/fzz/common!createPrintSessionkey.action',
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
	$("#personOrder").linkbutton("disable");
	$("#mc").datagrid("clearSelections");
}

var Bus = {
	showList : function (data,url){
			var opts = $("#mc").datagrid('options');
			opts.pageNumber = parseInt($("#page").val(),10);
			opts.pageSize = parseInt($("#rows").val(),10);
			opts.url = url;
			var params = opts.queryParams;
			params.serviceid = $("#serviceid").val();
			params.currentUnit = $("#currentUnit").val();
			params.querykey = $("#querykey").val();
			params.group = $("#group").val();
			params.tables = $("#tables").val();
			params.querySQL = $("#querySQL").val();
			opts.queryParams = params;
			$("#mc").datagrid(opts);
			$("#mc").data('queryParams',params);
			renderPhotoList(data);
	},
	loadDataByLevelQuery:function(condition){
		if(condition){
			clearSelect();
			$("#group").val(condition.conditionGroupkey);
			$("#tables").val(condition.conditionTablekey);
			$("#querySQL").val(condition.conditionQuerySQL);
			$("#querykey").val("");
			// 不清空当前选中的单位, by YZQ on 2013/03/25
			//$("#currentUnit").val("");
			//$("#currentUnitName").val("");
			var url=System.rootPath+"/fzz/common!queryByLevel.action";
			$("#mc").data("queryType",3);
			Bus.loadData(url);
		}
	},
	loadDataByQuerykey:function(){
		clearSelect();
		var url = System.rootPath+"/fzz/common!queryByQuerykey.action";
		$("#group").val("");
		$("#tables").val("");
		$("#querySQL").val("");
		// 不清空当前选中的单位, by YZQ on 2013/03/25
		//$("#currentUnit").val("");
		//$("#currentUnitName").val("");
		$("#mc").data("queryType",2);
		Bus.loadData(url);
	},
	loadDataByUnit:function(){
		clearSelect();
		var url = System.rootPath+"/fzz/common!queryByUnit.action";
		$("#group").val("");
		$("#tables").val("");
		$("#querySQL").val("");
		$("#querykey").val("");
		Bus.loadData(url);
		$("#personOrder").linkbutton("enable");
		$("#mc").data("queryType",1);
	},
	loadDataByManyQuery:function(){
		clearSelect();
		var url = System.rootPath+"/mingc/mingc!queryForMany.action?fzai=true";
		$("#group").val("");
		$("#tables").val("");
		$("#querySQL").val("");
		$("#querykey").val("");
		// 不清空当前选中的单位, by YZQ on 2013/03/25
		//$("#currentUnit").val("");
		//$("#currentUnitName").val("");
		Bus.loadData(url);
		$("#personOrder").linkbutton("enable");
		$("#mc").data("queryType",7);
	},
	loadData : function (url){
		if(!url){
			url = System.rootPath+"/fzz/common!queryByUnit.action";
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
		params = decodeURIComponent(params,true);
		$.ajax({
			type:'post',
			url:url,
			data:encodeURI(params),
			beforeSend: function(XMLHttpRequest){
				System.openLoadMask($("#contentPanel"),"正在查询...");
			},
			success: function(data, textStatus){
				Bus.showList(data,url);
			},
			complete: function(XMLHttpRequest, textStatus){
				System.closeLoadMask($("#contentPanel"));
			},
			error: function(){
				$.messager.alert("提示","查询出现错误,请重试!","error");
			}
		});
	}
};

function gotoPage(pageno){
	var pageno = parseInt(pageno,10);
	if(isNaN(pageno)){
		pageno = 1;
	}else{
		if(pageno<=0){
			pageno = 1;
		}
	}
	$("#page").val(pageno);
	Bus.loadData($("#mc").datagrid('options').url);
}

function renderPhotoList(data){
	$("#personPhotoList").empty();
	$(".pageFoot").show();
	
    if (data.rows && data.rows.length == 0) {
		$("#pageDataRows").show();
        $("#pageDataRows").html('没有数据！');
    } else {
		$("#pageDataRows").hide();
        
		var dataRowsHtml = '';
		$.each(data.rows,function(i,v){
			var desc = "&nbsp;&nbsp;<a href=\"javascript:void(0);\" onclick=\"System.commonEditor.editorSingePerson("+v.personcode+");\">"+v.xingm+"</a>，"+v.sex+"，"+v.minz+"，"+v.birthday + "出生于"+v.chusd+"，籍贯:"+v.jig + "。";
			desc += v.gongzsj + "参加工作,"+v.rudsj+"加入"+v.zzmm+",现任"+v.zhiw+"。<br/>";
			
			desc = desc.replaceAll("undefined","");	
			dataRowsHtml += "<tr>";
			dataRowsHtml += "<td class='photo'><span><img onclick=\"System.commonEditor.editorSingePerson("+v.personcode+");\" src=\""+System.rootPath+"/lob/photo.action?rand=" + Math.random() + "&&zoom=true&width=80&height=100&personcode="+v.personcode+"\" width=\"80\" height=\"100\" /></span></td>";
			dataRowsHtml += "<td class='content'>"+desc+"</td>";
			dataRowsHtml += "</tr>";
		});
		
		$("#personPhotoList").empty().append(dataRowsHtml);
    }
	
	var pageNo = parseInt($("#page").val(),10);
	var pageSize = parseInt($("#rows").val(),10);
	$("#pageNo").html(pageNo);
	$("#pageFirst").html(parseInt(((pageNo - 1) * pageSize) + 1,10));
	$("#pageEnd").html(pageNo * pageSize);
	$("#totalCount").html(data.total);
	var totalPage = parseInt(data.total / pageSize,10);
	if(data.total % pageSize){
		totalPage += 1;
	}
	$("#totalPages").html(totalPage);
	var footHtml = "<a href=\"javascript:void(0);\" class=\"page_btn blacklink\" onclick=\"gotoPage(1);\">[首 页]</a>";
	if(pageNo - 1 >= 1){
		footHtml += "<a href=\"javascript:void(0);\" class=\"page_btn blacklink\" onclick=\"gotoPage("+( pageNo>1?(pageNo-1):1 )+");\">[上一页]</a>";
	}
	if(pageNo + 1 <= totalPage){
		footHtml += "<a href=\"javascript:void(0);\" class=\"page_btn blacklink\" onclick=\"gotoPage("+( (pageNo + 1)> totalPage ? totalPage : (pageNo + 1) )+");\">[下一页]</a>";
	}
	footHtml += "<a href=\"javascript:void(0);\" class=\"page_btn blacklink\" onclick=\"gotoPage("+totalPage+");\">[尾 页]</a>";
	$("#pageDataFoot").empty().append(footHtml);
}

function newItem(){
	 System.person.add("#mc",$("#currentUnit").val(),$("#currentUnitName").val(),function(entity){
		$('#mc').datagrid('appendRow',entity);
		var index = $('#mc').datagrid('getRows').length - 1;
		$('#mc').datagrid('selectRow', index);
	});
}

function destroyItem(){
	var rows = $('#mc').datagrid('getSelections');
	if (rows.length>0){
		$.messager.confirm('提示','确定要删除选中行吗?',function(r){
			if(r){
				var deleteIndex = [];
				$.each(rows,function(i,row){
					$.post(System.rootPath+'/mingc/mingc!remove.action',{'entity.id':row.personcode},function(msg){
						if (msg == '删除成功') {
							var index = $('#mc').datagrid('getRowIndex',row);
							deleteIndex.push(index);
							if(i==rows.length-1){
								$.each(deleteIndex,function(j,d){
									$('#mc').datagrid('deleteRow',d);
								});
							}
						}
						else {
							$.messager.alert("错误", msg, "error")
						}
					});
				});
			}
		});
	}
}

function checkPersonPermission() {
	var rows = $("#mc").datagrid("getSelections");
	if (rows) {
		var personcodes = '';
		$(rows).each(function(index, row){
			if (personcodes == '') personcodes = row.personcode;
			else personcodes = personcodes + "," + row.personcode;
		});
		
		System.permission.checkIsEditablePersons(personcodes, function(flag) {
			if (flag.editable) {
				$("#btn_remove").linkbutton("enable");				
			}
			else {
				$("#btn_remove").linkbutton("disable");
			}
		});
	} 
}