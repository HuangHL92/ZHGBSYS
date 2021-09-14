function init(){
	$("#contentTabs").tabs({
		onLoad : function (panel) {
			var personcode = $("#personcode").val();
			var tab = $('#contentTabs').tabs('getSelected');
			if(tab.panel('options').title == '任免表（一）'){
				$("#unitcode").val($("#_unitcode").val());
				$("#remark").unbind('keyup');
				$("#remark").bind('keyup',function(event){  
				     var k = event.which;  
				     if(event.keyCode=="13"){
				    	System.insertAtCursor($("#remark").get(0),"                  ");//"                  "
				     }
				});
				System.report.menu("#btnPrint",function(){
					System.report.print($("#personcode").val());
				},'b');
				if(System.button.editable($("#isView").val(),$("#allowOperator").val(),$("#currentUnit").val(),$("#unitcode").val())){
					$("#btnOk").linkbutton("enable");
				}else{
					$("#btnOk").linkbutton("disable");
					$("#qrzxl").attr("disabled",true);
					$("#qrzxw").attr("disabled",true);
					$("#zzxl").attr("disabled",true);
					$("#zzxw").attr("disabled",true);
					$("#zhiw").attr("disabled",true);
				}
	    		$("#photo").css({
	    			"top":$("#link_photo").offset().top-40,
	    			"left" :$("#link_photo").offset().left-223,
	    			"z-index":"1000"});
	    		
			}else{
				$.get(System.rootPath+"/removal/removal!getUnitcodeByPersoncode.action?id="+$("#personcode").val(),function(data){
					$("#unitcode").val(data);
					if(!System.button.editable($("#isView").val(),$("#allowOperator").val(),
							$("#currentUnit").val(),data)){
						$("#jianglqk").attr("disabled",true);
						$("#niandkh").attr("disabled",true);	
					}
				});
				getKhInfo();
				getJcInfo();
				var lastIndex;
				var editIndex;
				$("#jtcy").datagrid({
					height:258,
					border: false,
					url:System.rootPath+"/removal/removal!getJtcy.action?personcode="+$("#personcode").val(),
					singleSelect:true,
					idField:'id',
					frozenColumns:[[
		                {title:'称&nbsp;&nbsp;谓',field:'chengw',width:89,align:'center',sortable:true,
			                editor:{
			                	type:"combotree",
			           			options:{
				                	valueField:'code',
									textField:'name',
				        			url:System.rootPath+"/code/code.action?codeClass=0026",
				        			required:true,
				        			editable:true,
				        			onClick:function(node){
					                	var data = $("#jtcy").data('datagrid').data;
										var rowData = $("#jtcy").datagrid('getRows')[editIndex];
										rowData.chengwCode = node.id;
										rowData.chengw = node.text;
										var newrows = data.rows;
				        			    for(var i = 0;i<newrows.length;i++){
				        			    	if(newrows[i].id == rowData.id){
				        			    		newrows[i] = rowData;
				        			    		break;
				        			    	}
				        			    }
				        			    data.rows = newrows;
				        			    console.log(newrows);
										$("#jtcy").data('datagrid').data = data;
									}
		                		}
		                	}
				        }
					]],
					columns:[[
				        {title:'姓&nbsp;&nbsp;名',field:'xingm',width:89,align:'center',editor:"text"},
				        {title:'出生日期',field:'birthday',width:90,align:'center',
				        	formatter:function(value,rowData){
					        	if(rowData.age == null || rowData.age == ""){
					        		return value;
					        	}else{
					        		return value+"<br/>（"+rowData.age+"岁）";
					        	}
							},
							editor:{
			        			type:'data97',
			        			options:{iLen:6}
							}
						},
				        {title:'政治面貌',field:'zzmm',width:95,align:'center',
							editor:{
			                	type:"combotree",
			           			options:{
				                	valueField:'code',
									textField:'name',
									editable:true,
				        			url:System.rootPath+"/code/code.action?codeClass=0010",
				        			onClick:function(node){
										var data = $("#jtcy").data('datagrid').data;
										var rowData = $("#jtcy").datagrid('getRows')[editIndex];
										rowData.zzmmCode = node.id;
										rowData.zzmm = node.text;
										var newrows = data.rows;
				        			    for(var i = 0;i<newrows.length;i++){
				        			    	if(newrows[i].id == rowData.id){
				        			    		newrows[i] = rowData;
				        			    		break;
				        			    	}
				        			    }
				        			    data.rows = newrows;
										$("#jtcy").data('datagrid').data = data;
									}
		                		}
		                	}
						},
				        {title:'工作单位',field:'gongzdanw',width:130,align:'center',
					        editor:"text"
					    },
				        {title:'职务',field:'zhiw',width:90,align:'center',
					        editor:"text"
					    }
					]],
					pagination:false,
					rownumbers:true,
					toolbar:[{
						id:'btnadd',
						text:'添加',
						disabled:true,
						iconCls:'icon-add',
						handler:function(){
							$("#jtcy").datagrid('endEdit', editIndex);
				    		$("#jtcy").datagrid('appendRow',{id:0,age:0,personcode:$("#personcode").val(),isNewRecode:true});
							lastIndex = $("#jtcy").datagrid('getRows').length-1;
							$("#jtcy").datagrid('selectRow', lastIndex);
							$("#jtcy").datagrid('beginEdit', lastIndex);
							editIndex = lastIndex;
						}
					},'-',{
						id:'btncut',
						text:'删除',
						disabled:true,
						iconCls:'icon-cut',
						handler:function(){
							var row = $("#jtcy").datagrid('getSelected');
							if (row){
								$.messager.confirm('提示','确定要删除吗?',function(r){
									if(r){
										var index = $("#jtcy").datagrid('getRowIndex',row);
										if(editIndex == index) editIndex = undefined;
										if(row.isNewRecode && row.isNewRecode == true){
											$("#jtcy").datagrid('deleteRow',index);
										}else{
											$.post(System.rootPath+'/removal/removal!removeJtcy.action?',{'id':row.id},function(){
												$("#jtcy").datagrid('deleteRow',index);
											});
										}
									}
								});
							}
						}
					},'-',{
						id:'btn_edit',
						text:'编辑',
						iconCls:'icon-edit',
						disabled:true,
						handler:function(){
							var row  = $("#jtcy").datagrid("getSelected");
							if(row){
								var rowIndex = $("#jtcy").datagrid("getRowIndex",row);
								if (editIndex && editIndex != rowIndex){
									var editRow = $("#jtcy").datagrid("getRows")[editIndex];
									if(editRow.isNewRecode && editRow.isNewRecode == true){
										$("#jtcy").datagrid('deleteRow',editIndex);
									}else{
										$("#jtcy").datagrid('cancelEdit', lastIndex);
									}
								}
								editIndex = rowIndex;
								$("#jtcy").datagrid('beginEdit', rowIndex);
							}
						}
					},'-',{
						id:'btn_undo',
						text:'撤销',
						iconCls:'icon-undo',
						disabled:true,
						handler:function(){
							if(editIndex != undefined){
								var row  = $("#jtcy").datagrid("getSelected");
								if(row.isNewRecode && row.isNewRecode == true){
									$("#jtcy").datagrid('deleteRow',editIndex);
								}else{
									$("#jtcy").datagrid('cancelEdit', editIndex);
								}
								editIndex = undefined;
							}
						}
					},'-',{
						id:'btnsave',
						text:'保存',
						iconCls:'icon-save',
						disabled:true,
						handler:function(){
							// 保存数据
							if(editIndex != undefined){
								if($("#jtcy").datagrid("validateRow",editIndex)){
									var rowData = $("#jtcy").datagrid('getRows')[editIndex];
									rowData.xingm =  $("#jtcy").datagrid('getEditor',{index:editIndex,field:"xingm"}).target.val();
									rowData.birthday =  $("#jtcy").datagrid('getEditor',{index:editIndex,field:"birthday"}).target.val();
									rowData.gongzdanw =  $("#jtcy").datagrid('getEditor',{index:editIndex,field:"gongzdanw"}).target.val();
									rowData.zhiw =  $("#jtcy").datagrid('getEditor',{index:editIndex,field:"zhiw"}).target.val();
									var rows = [rowData];
									$.ajax({
										type: "POST",
										url: System.rootPath+'/removal/removal!saveJtcy.action',
										data:{'entityData':encodeURI($.toJSON(rows))},
										success: function(data){
											$("#jtcy").datagrid('reload');
											editIndex = undefined;
										},
										error:function(XMLHttpRequest, textStatus, errorThrown){
											$.messager.alert("info","保存失败,请重试!"+textStatus+errorThrown);
										}
									});
								}
							}
						}
					}],
					onLoadSuccess:function(data){
						if(System.button.editable($("#isView").val(),$("#allowOperator").val(),
							$("#currentUnit").val(),data)){
							$("#btnadd").linkbutton("enable");
							$("#btncut").linkbutton("enable");
							$("#btnsave").linkbutton("enable");
							$("#btn_edit").linkbutton("enable");
							$("#btn_undo").linkbutton("enable");
						}else{
							$("#btnadd").linkbutton("disable");
							$("#btncut").linkbutton("disable");
							$("#btnsave").linkbutton("disable");
							$("#btn_edit").linkbutton("disable");
							$("#btn_undo").linkbutton("disable");
						}
					},
					onClickRow:function(rowIndex){
						lastIndex = rowIndex;
					}
				});
			}	
		}
	});
}
var Removal = {
	renderCode:function (obj,linkname,fieldValue,showValue,w){
		if(!w){
			w = $("#"+obj+"_Code").width();
		}
		Code.render("#"+obj+"_Code",linkname,fieldValue,showValue,function(node){
			$("#"+obj+"Code").val(node.id);
			$("#"+obj).val(node.text);
		},w);
	},
	renderPage : function(personcode){
		var oldcode = $("#personcode").val();
		if(oldcode != personcode){
			$("#personcode").val(personcode);
			var tabs = $("#contentTabs").tabs('tabs');	
			$.each(tabs,function(i,tab){
				$('#contentTabs').tabs('update',{
					tab: tab,
					options:{
						cache : false,
						href : System.rootPath+"/removal/removal!getTab"+(i+1)+".action?personcode="+personcode
					}
				});
			});
		}
	},
	xl:function (target,type){
		var personcode = $("#personcode").val();
		$("#w").window({
			shadow:true,
			modal :true,
			resizable:true,
			collapsible:false,
			minimizable:false,
			maximizable:false,
			width:800,
			height:300,
			title:'编辑学历信息',
			content:"<iframe border='0' width='100%' height='100%' frameborder='0' src='"+System.rootPath+"/xlxw/xl-modify-show?personcode="+personcode+"&xltype="+type+"'></iframe>"
		});
	},
	xw:function (target,type){
		var personcode = $("#personcode").val();
		$("#w").window({
			shadow:true,
			modal :true,
			resizable:true,
			collapsible:false,
			minimizable:false,
			maximizable:false,
			width:800,
			height:300,
			title:'编辑学位信息',
			content:"<iframe border='0' width='100%' height='100%' frameborder='0' src='"+System.rootPath+"/xlxw/xw-modify-show?personcode="+personcode+"&xltype="+type+"'></iframe>"
		});
	},
	zhiw:function(target){
		var personcode = $("#personcode").val();
		$("#w").window({
			shadow:true,
			modal :true,
			resizable:true,
			collapsible:false,
			minimizable:false,
			maximizable:false,
			width:800,
			height:300,
			title:'编辑职务信息',
			content:"<iframe border='0' width='100%' height='100%' frameborder='0' src='"+System.rootPath+"/zw/zw-modify-show?personcode="+personcode+"'></iframe>"
		});
	},
	kh:function (target){
		var personcode = $("#personcode").val();
		$("#w").window({
			shadow:true,
			modal :true,
			resizable:true,
			collapsible:false,
			minimizable:false,
			maximizable:false,
			width:600,
			height:400,
			title:'编辑年度考核结果',
			content:"<iframe border='0' width='100%' height='100%' frameborder='0' src='"+System.rootPath+"/kh/kh-modify-show?personcode="+personcode+"'></iframe>",
			onClose : function (){
				getKhInfo();
			}
		});
	},
	jc:function (target){
		var personcode = $("#personcode").val();
		$("#w").window({
			shadow:true,
			modal :true,
			resizable:true,
			collapsible:false,
			minimizable:false,
			maximizable:false,
			width:800,
			height:300,
			title:'编辑奖惩情况',
			content:"<iframe border='0' width='100%' height='100%' frameborder='0' src='"+System.rootPath+"/jc/jc-modify-show?personcode="+personcode+"'></iframe>",
			onClose : function (){
				getJcInfo();
			}
		});
	}
};
function getKhInfo(){
	var url = System.rootPath+"/common/exec-common-method!getKhInfo.action";
	$.post(url,{personcode:$("#personcode").val()},function(data){
		$("#niandkh").val(data);
	});
}
function getJcInfo(){
	var url = System.rootPath+"/common/exec-common-method!getJcInfo.action";
	$.post(url,{personcode:$("#personcode").val()},function(data){
		$("#jianglqk").val(data);
	});
}

function xlCallback(entity,type){
	if(entity){
		if(type==1){
			$("#qrzxl").val(entity.xlshow);
			$("#qrzxlCode").val(entity.xl);
		}else{
			$("#zzxl").val(entity.xlshow);
			$("#zzxlCode").val(entity.xl);
		}
	}
}
function xwCallback(entity,type){
	if(entity){
		if(type==1){
			$("#qrzxw").val(entity.xwshow);
			$("#qrzxwCode").val(entity.xw);
			$("#qrzbyyx").val(entity.xwsydw);
			$("#qrzzy").val(entity.sxzymc);
		}else{
			$("#zzxw").val(entity.xwshow);
			$("#zzxwCode").val(entity.xw);
			$("#zzbyyx").val(entity.xwsydw);
			$("#zzzy").val(entity.sxzymc);
		}
	}
}

function zhiwCallback(value){
	$("#zhiw").val(value);
}