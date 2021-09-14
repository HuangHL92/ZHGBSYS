var g_editable = false;
var g_valiationMsg = '';
		
var General = {
	getTables : function (container,group,clickCallback,callback){
		$.ajax({
			type:'post',
			// 限制信息集, by YZQ on 2012/10/28
			//url:System.rootPath+"/query/level-query!getTablesByGroupKey.action",
			//data:{groupkey:group},
			url: System.rootPath + "/security/access-right!getUserTablesByGroup.action",
			data: { groupKey: group },
			success: function(data, textStatus){
				var leftList = "<div id=\"leftlist\"><ul style=\"padding:0 10 10 10px;\">";
				$.each(data,function(i,v){
					leftList += "<li code='"+v.table_name+"' relationFlag='"+v.relation_flag+"' onmousemove=\"BusPersonList.addOverClass(this);\">"+v.table_name_cn+"</li>"; 
					if(i == (data.length - 1)){
						leftList += "</ul></div>";
						$(container).append(leftList);
						$("#leftlist li").click(function(){
							var oldTable = $("#table").val();
							var table = $(this).attr("code");
							if(oldTable != table){
								var relationFlag = $(this).attr("relationFlag");
								var tablecn = $(this).html();
								clickCallback(group,table,tablecn,relationFlag);
								$("#table").val(table);
								$("#sys_dateUpdateFields").data("fields",[]);
								$("#sys_blobUpdateFields").data("fields",[]);
								$("#sys_clobUpdateFields").data("fields",[]);
							}
						});
						BusPersonList.addMouserClass("#leftlist li");
						callback();
					}
				});
			},
			error: function(){
				$("#more").empty().append("出现错误了(>_<)，稍后在试试!");
			}
		});
	},
	clickLi : function (group,table,tablecn,relationFlag){
		$($("#cc2").layout("panel",'center')).panel("setTitle",tablecn);
		var url = "";
		//$("#table-body").height("443");
		//alert(group + "-" + table);
		//alert($("#personcode").val());
		if(relationFlag == "1"){
			$("#table").val(table);
			$("#contentBody").empty();
			$("#table-list").hide();
			$('#detailsLayout').layout('setRegionSize', { region: 'north', value: 1 });	
			url = System.rootPath + "/common/general!getSingleRowData.action";
			$.ajax({
				type:'post',
				url:url,
				data:{'groupkey':group,'tablekey':table,'personcode':$("#personcode").val()},
				success:function(rows){
					// 设置权限
					System.permission.checkPersonTablePermission($("#personcode").val(), group, table, function(flag){					
						$("#btn_save").linkbutton("disable");
						g_editable = flag.editable;
						if (g_editable){
							$("#btn_save").linkbutton("enable");
						}	

						General.renderPage(rows);					
					});					
				},
				error: function(){
					$("#contentBody").empty().append("出现错误了(>_<)，稍后在试试!");
				}
			});
		}else{
			$("#contentBody").empty();
			$("#table-list").show();
			try {
				$('#detailsLayout').layout('setRegionSize', { region: 'north', value: 225 });	
			}
			catch (e) {
				// 这里必须catch异常，否则第一个表不是A000，会抛异常
			}
			//var h1 = $("#table-list").height();
			//var h = $("#table-body").height();
			//$("#table-body").height(h-h1);
			$("#table").val(table);
			$("#id").val("");
			General.loadTopList(group,table);
		}
		
		// 设置"审核确认"权限, by YZQ on 2013/01/15
		System.permission.checkPersonTablePermission($("#personcode").val(), 'A', 'A000', function(flag){					
			if (flag.editable){
				$("#btn_confirm").linkbutton("enable");
			}		
			else {
				$("#btn_confirm").linkbutton("disable");
			}
		});
	},
	renderPage : function (rows){
		var content = "";
		var codeFields = $("#id").data("codeFields");
		var numberFields = $("#id").data("numberFields");
		// bugfix: 每次渲染页面，必须清空字段
		//if(!codeFields){
			codeFields = [];
		//}
		//if(!numberFields){
			numberFields = [];
		//}
		System.openLoadMask("#pageWin","正在获取数据...");
		updateZW(); // 更新职务
		var j = 0;
		$.each(rows,function(i,v){
			if(j%2==0 && v.property_flag != 1){
				if(j>0){
					content += "</tr>";
				}
				content += "<tr height='30px'>";
			}
			//if(v.field_dbname == 'A000_A0101'){
			//	$("#personname").html("【"+v.fieldValue+"】");
				// 增加职务, by YZQ on 2012/08/26
				//updateZW();
			//}
			if(v.dbtype_ref == 2 || v.dbtype_ref == 70){
				codeFields.push(v);
			}else if(v.dbtype_ref == 3 || v.dbtype_ref == 30){
				numberFields.push(v);
			}
			if(v.property_flag == 1){
				createWidget(v);
			}else{
				j ++;
				if(v.allownull == 1){
					content += "<td class='kv-label' style='word-break:break-all;'>"+v.field_name_cn+"<font color='red'>*</font></td><td class='kv-content' title='"+v.field_desc+"'>"+createWidget(v)+"</td>";
				}else{
					content += "<td class='kv-label' style='word-break:break-all;'>"+v.field_name_cn+"</td><td class='kv-content' title='"+v.field_desc+"'>"+createWidget(v)+"</td>";
				}
			}
			if(v.dbtype_ref == 4){
				var dateUpdateFields = $("#sys_dateUpdateFields").data("fields");
				if(!dateUpdateFields){
					dateUpdateFields = [];
				}
				dateUpdateFields.push(v.field_name);
				$("#sys_dateUpdateFields").data("fields",dateUpdateFields);
			}
			if(i == (rows.length - 1)){
				if(j%2==0){
					content += "</tr>";
				}else{
					content += "<td colspan='2' class='kv-content'>&nbsp;</td></tr>";
				}
				$("#contentBody").empty().append(content);
				$("#id").data("codeFields",codeFields);
				setTimeout(function(){
					$.parser.parse('#contentBody');
					//renderNumberFields(numberFields);
					renderCodeFields(codeFields, function() {}); // 渲染代码字段, by YZQ on 2012/10/15
					
					// 更新电子印章, by YZQ on 2013/01/16
					updateElecSeal();
					System.closeLoadMask("#pageWin");
				},1);
			}
		});
		
		function renderCodeFields(codeFields,callback){			
			$.each(codeFields,function(i,field){
				// 不渲染多选代码
				if (field.dbtype_ref == 70) return;
				if (field.editable == 0) return; 
				
				System.code.render("#"+field.field_dbname + "_code",field.link_name,field.fieldValue,field.showValue,160,function(node){
					//alert('onselectcallback');
						
					//$("#"+field.field_dbname+"_show").val(node.text);
					var obj = field.field_dbname;
					if(obj=="A001_A0201B"){
						var jgmc = $("#A001_A0201A");
						if(jgmc.length>0){
							if(jgmc.val().Trim()==0){
								jgmc.val(node.text);
							}
						}
						// 获取机构隶属关系
						var url = System.rootPath+"/common/unit!getUnitInfo.action?currentUnit=" + node.id;
						$.post(url, function (data) {
							//alert(data.b000_B0124);
							//alert(data.b000_B0124_SHOW);
							//alert($("#A001_A0205").length);
							//alert($("#A001_A0205_show").length);
							
							// 任职机构类别A001_A0201C
							$("#A001_A0201C").val(data.b000_B0101B);
							$("#A001_A0201C_show").val(data.b000_B0101B_SHOW);
							$("#A001_A0201C_code").combotree('setValue',data.b000_B0101B);
							$("#A001_A0201C_code").combotree('setText',data.b000_B0101B_SHOW);
							
							// 所在行政区 A001_A0203 B000_B0117
							$("#A001_A0203").val(data.b000_B0117);
							$("#A001_A0203_show").val(data.b000_B0117_SHOW);
							$("#A001_A0203_code").combotree('setValue',data.b000_B0117);
							$("#A001_A0203_code").combotree('setText',data.b000_B0117_SHOW);
					
							// 隶属关系
							$("#A001_A0205").val(data.b000_B0124);
							$("#A001_A0205_show").val(data.b000_B0124_SHOW);
							$("#A001_A0205_code").combotree('setValue',data.b000_B0124);
							$("#A001_A0205_code").combotree('setText',data.b000_B0124_SHOW);
							
							// 单位性质
							$("#A001_A0209").val(data.b000_B0131);
							$("#A001_A0209_show").val(data.b000_B0131_SHOW);
							$("#A001_A0209_code").combotree('setValue',data.b000_B0131);
							$("#A001_A0209_code").combotree('setText',data.b000_B0131_SHOW);
														
							// 单位级别
							$("#A001_A0207").val(data.b000_B0127);
							$("#A001_A0207_show").val(data.b000_B0127_SHOW);
							$("#A001_A0207_code").combotree('setValue',data.b000_B0127);
							$("#A001_A0207_code").combotree('setText',data.b000_B0127_SHOW);
							
							// 单位所属行业A001_A0211
							$("#A001_A0211").val(data.b000_B0134);
							$("#A001_A0211_show").val(data.b000_B0134_SHOW);
							$("#A001_A0211_code").combotree('setValue',data.b000_B0134);
							$("#A001_A0211_code").combotree('setText',data.b000_B0134_SHOW);
						});
					}
					$("#"+obj).val(node.id);
					$("#"+obj+"_show").val(node.text);		
					//alert(node.id + " - " + node.text);
				}, callback);
				/*
				if(i == (codeFields.length - 1)){
					callback();
				}
				*/
			});
		}
	
		function renderNumberFields(codeFields){
			$.each(codeFields,function(i,field){
				$("#"+field.field_dbname).numberbox({ width: 150 });
			});
		}
	},
	showCode : function(obj,linkname,fieldValue,showValue, multipleSelection){
		// bugfix: 我们应该取当前字段的值去初始化代码选择控件, by YZQ on 2012/09/09
		fieldValue = $("#" + obj ).val();
		showValue = $("#" + obj + "_show").val();
		//------------					
		
		Code.render("#"+obj+"_code",linkname,fieldValue,showValue,multipleSelection,function(node){
			if(obj=="A001_A0201B"){
				var jgmc = $("#A001_A0201A");
				if(jgmc.length>0){
					if(jgmc.val().Trim()==0){
						jgmc.val(node.text);
					}
				}
			}
			$("#"+obj).val(node.id);
			$("#"+obj+"_show").val(node.text);
		});		
	},
	showRemakerWin : function (obj){
		$("#remakerDialog").show();
		$("#remakerDialog").dialog({
			shadow:true,
			modal :true,
			resizable:true,
			width:530,
			height:400,
			title:$(obj).attr('cnname'),
			buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					$('#remakerDialog').dialog('close');
					if($.browser.msie){
						if(editor_a){
							editor_a.sync();
						}
						$(obj)[0].value=editor_a.getContentTxt();//.replaceAll("<br/>","\r\n").replaceAll("<p>","").replaceAll("</p>",""));
					}else{
						$(obj).val($("#remaker").val());
					}
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#remakerDialog').dialog('close');
				}
			}],
			onOpen : function () {
				var clobUpdateFields = $("#sys_clobUpdateFields").data("fields");
				if(!clobUpdateFields){
					clobUpdateFields = [];
				}
				clobUpdateFields.push($(obj).attr('id'));
				$("#sys_clobUpdateFields").data("fields",clobUpdateFields);
				if($.browser.msie){
					if(!editor_a){
						editor_a = new baidu.editor.ui.Editor({
						    textarea:'remaker',
						    iframeCssUrl: '../jslib/ueditor/iframe.css'
						});
					}
					var remarkV = $(obj)[0].value.replaceAll("\r\n","<br/>");
					editor_a.render('remaker');
					editor_a.setContent(remarkV);
					editor_a.setHeight(420);
				}else{
					$('#remaker').val($(obj).val());
				}
			}
		});
	},
	loadTopList : function (group,table){
		var editable = false;
		// 清除之前的选择, by YZQ on 2012/11/05
		try {
			$("#toplist").datagrid('clearSelections');			
		}
		catch (e) {
		}
		
		System.permission.checkPersonTablePermission($("#personcode").val(), group, table, function(flag){
			$("#btn_save").linkbutton("disable");
			g_editable = flag.editable;
			if (g_editable){
				$("#btn_save").linkbutton("enable");
			}			
			
			$.ajax({
				type:'post',
				//url:System.rootPath+"/query/level-query!getFiledsByTableKey.action",
				//data:{tablekey:table},
				url:System.rootPath+"/security/access-right!getUserQueryFieldsByTable.action",
				data:{tableKey:table},
				success: function(fields, textStatus){
					var topcolumns = [];
					//alert(fields.length);
					if(fields.length >0){
						var max = fields.length > 5 ? 5 : fields.length;
						if (table == 'A001' || table == 'A007' || table == 'A008') max = 6;
						
						var fieldcount = 0;
						for(var i=0;i<fields.length; i++){
							var fieldname = fields[i].field_dbname;
							
							// 特殊处理A001 & A007, by YZQ on 2012/08/23
							if (table == 'A001') {
								if (fieldname != 'A001_A0201A' && 
										fieldname != 'A001_A0201B' &&
										fieldname != 'A001_A0215' &&
										fieldname != 'A001_A0217' &&
										fieldname != 'A001_A0255' &&
										fieldname != 'A001_A0223') {
									continue;
								}
							}
							else if (table == 'A007') {
								if (fieldname != 'A007_A0801' && 
										fieldname != 'A007_A0804' &&
										fieldname != 'A007_A0807' &&
										fieldname != 'A007_A0811' &&
										fieldname != 'A007_A0814' && 
										fieldname != 'A007_A0901') {
									continue;
								}
							}
							
							if (fieldcount == max) break;
							fieldcount++;
							
							var flag = (fields[i].dbtype_ref == 2 || fields[i].dbtype_ref == 70);
							var isYesNo = fields[i].dbtype_ref == 5;
							var column = { 
									title:fields[i].field_name_cn,
									field:(flag ? fields[i].field_dbname+"_show": fields[i].field_dbname),
									width:100,
									align:'center',
									sortable:g_editable, 
									formatter: isYesNo ? function(value, row, index) {
										if (value == '1' || value == 'true') return '是';
										else return '否';
									} : function (value, row, index) {
										return value;
									},
									// 非在职职务，显示红色, by YZQ on 2012/08/06
									styler: function(value,row,index){
										if (typeof(row.A001_A0255) == 'undefined') {
											return '';
										}
										else {
											if (row.A001_A0255 != '1' && row.A001_A0255 != '2'){
												return 'color:red;';
											}
											return '';
										}
									}
							        // ---------------
							};							
							
							topcolumns.push(column);
						}						
					}
					
					$("#toplist").datagrid({
						//height:200,
						fit: true,
						url:System.rootPath+'/common/general!getListRowData.action?personcode='+$("#personcode").val()+"&groupkey="+group+"&tablekey="+table,
						sortOrder: 'asc',
						sortName:'id2',
						remoteSort: true,
						singleSelect:true,
						idField:'id',
						pagination:true,
						pageNumber: 1,
						rownumbers:true,
						onLoadSuccess:function(data){
							if($("#id").val() == ""){
								var rows = data.rows;
								if(rows.length>0){
									$("#id").val(rows[0].id);
								}
							}	
								// 默认选中第一项，如果不想显示，取消下面注释, by YZQ on 2013/03/12
								//$("#contentBody").empty(); // 如果不载入选项，那么清空内容。 by YZQ on 2012/11/05	
								//$("#btn_save").linkbutton("disable");								
							//}
							//else {
								$.ajax({
									type:'post',
									url:System.rootPath + "/common/general!getRowDataById.action?r="+Math.random(),
									data:{'groupkey':group,'tablekey':table,'personcode':$("#personcode").val(),'id':$("#id").val()},
									success:function(rows){
										General.renderPage(rows);
									},
									error: function(){
										$("#contentBody").empty().append("出现错误了(>_<)，稍后在试试!");
									}
								});
							//}							
						},
						onSelect : function(rowIndex,rowData){
							// 设置保存按钮, by YZQ on 2012/11/05
							if (g_editable) $("#btn_save").linkbutton("enable");
							else $("#btn_save").linkbutton("disable");
							
							$("#id").val(rowData.id);
							$.ajax({
								type:'post',
								url:System.rootPath + "/common/general!getRowDataById.action?r="+Math.random(),
								data:{'groupkey':group,'tablekey':table,'personcode':$("#personcode").val(),'id':$("#id").val()},
								success:function(rows){
									General.renderPage(rows);									
								},
								error: function(){
									$("#contentBody").empty().append("出现错误了(>_<)，稍后在试试!");
								}
							});
						},
						frozenColumns:[[
						    {field:'ck',checkbox:true}
						]],
						columns:[topcolumns],
						toolbar:[{
							id:'btnadd',
							text:'添加',
							iconCls:'icon-add',
							disabled:!g_editable,
							handler:function(){
								$("#btn_save").linkbutton("enable"); // 激活保存按钮, by YZQ on 2012/11/05
								$("#id").val("");
								$.ajax({
									type:'post',
									url:System.rootPath + "/common/general!preAddRowData.action?r="+Math.random(),
									data:{'groupkey':group,'tablekey':table,'personcode':$("#personcode").val()},
									success:function(rows){
										General.renderPage(rows);
									},
									error: function(){
										$("#contentBody").empty().append("出现错误了(>_<)，稍后在试试!");
									}
								});
							}
						},'-',{
							id:'btncut',
							text:'删除',
							iconCls:'icon-remove',
							disabled:!g_editable,
							handler:function(){
								var row = $("#toplist").datagrid('getSelected');
								if (row){
									$.messager.confirm("提示","确定要删除选中记录吗?",function(r){
										if(r){
											var deleteId = row.id;
											// 检查是否除当前职务，其他职务有任何一个机构代码可以在单位表中找到, by YZQ on 2013/03/14
											if (table == 'A001') {
												var isValid = "false";
												$.ajax({
													async: false, // 同步
													type: "POST",
													url: System.rootPath+ '/common/general!checkZhiwChange.action',
													data: { 
														id: deleteId,
														personcode: $("#personcode").val()
													},
													success:function(json){
														//alert(json);
														isValid = json;
													},
													error:function(XMLHttpRequest, textStatus, errorThrown){
														//alert(textStatus);
														isValid = "false";
													}
												});		

												if (isValid != "true") {
													$.messager.alert('提示', '无法删除该职务，必须至少保留一条在任并且任职机构代码有效的职务！');
													return;
												}
											}

											// 如果三龄一历已审核，不能删除之前已审核的记录，by YZQ on 2015/01/08
											if (table == 'A007' || table == 'A008') {
												var isValid = validXlxw(deleteId, table, 2);		

												if (isValid == "false") {
													var msg = table == 'A007' ? '学历' : '学位';
													$.messager.alert('提示', '该干部已经通过三龄一历审核认定，不能删除' + msg + '，如需删除，请联系管理员');
													return;
												}
											}
											
											System.openLoadMask($("body"), "正在删除...");
											$.ajax({
												type:'post',
												url:System.rootPath+"/common/general!removeById.action",
												data:{id:deleteId,'tablekey':table},
												success:function(json){
													if(json.code==1){
														var index = $("#toplist").datagrid('getRowIndex', row);
														$("#toplist").datagrid('deleteRow', index);
														if(deleteId == $("#id").val()){
															$("#id").val("");
															$.ajax({
																type:'post',
																url:System.rootPath + "/common/general!getRowDataById.action?r="+Math.random(),
																data:{'groupkey':group,'tablekey':table,'personcode':$("#personcode").val(),'id':$("#id").val()},
																success:function(rows){
																	General.renderPage(rows);
																	// 更新职务信息
																	if (table == 'A001') {
																		updateZW();
																	}
																},
																error: function(){
																	$("#contentBody").empty().append("出现错误了(>_<)，稍后在试试!");
																}
															});
														}
													}
												},
												complete :function(XMLHttpRequest, textStatus){
													System.closeLoadMask($("body"));
												},
												error : function(XMLHttpRequest, textStatus, errorThrown){
													$.messager.alert("error",textStatus + errorThrown);
												}
											});
										}
									});
								}
							}
						},'-',{
							id:'btnInfo',
							text:'<b><font color=\"red\">提示:点击列标题可实现快速排序！</font></b>',
							disabled:!g_editable,
							handler:function(){}
						},'-',{
							id:'btnorder',
							text:'排序',
							iconCls:'icon-sort',
							disabled:!g_editable,
							handler:function(){
								System.person.orderMutilRow($("#serviceid").val(), $("#personcode").val(), group, table);
							}
						}]
					});
				},
				error: function(){
					$.messager.alert("错误","获取列表出现错误了(>_<)，稍后再试试!","error");
				}
			});
		});
	},
	reloadTopList:function(){
		$("#toplist").datagrid('reload');
	},
	saveData : function(){
		General.execSave();
	},
	saveRemoval : function() {
		General.execSave(function() {
			$.ajax({
				url:System.rootPath+"/removal/removal!saveRemoval.action",
				data:{'personcode':$("#personcode").val()},
				success:function(json){
					var table = $("#table").val();
					if (table == 'A013' || table == 'A014' || table == 'A035') {
						var href = System.rootPath+"/removal/removal!getTab2.action?personcode="+$("#personcode").val();
						$("#removalTab2").load(href);
					}
					else {
						var href = System.rootPath+"/removal/removal!getTab1.action?personcode="+$("#personcode").val();
						$("#removalTab1").load(href);
					}
				}
			});
		});
	},
	checkAllowNull : function (){
		var allowStr = "";
		var j = 0;
		var codefield;
		$("#contentBody").find("*[allownull='1']").each(function(i,v){
			codefield = $(v).attr("codefield");
			if(codefield){
				if($("#"+codefield).val() == ''){
					if(j>0){
						allowStr +="<br/>";
					}
					//allowStr += $(v).attr("ref")+"不能为空!";
					allowStr += $(v).attr("ref");
					j +=1;
				}
			}else{
				if(v.value == ''){
					if(j>0){
						allowStr +="<br/>";
					}
					//allowStr += $(v).attr("ref")+"不能为空!";
					allowStr += $(v).attr("ref");
					j +=1;
				}
			}
		});
		
		return allowStr;
	},
	execSave : function (callback){
		// 检查必填字段是否填写,by YZQ on 2012/09/19
		g_valiationMsg = ''
		g_valiationMsg = General.checkAllowNull();	

        // 如果三龄一历已审核，不能更改记录，by YZQ on 2015/01/08
		if ($("#id").val() != '') {
			var table = $("#table").val();		
			if (table == 'A007' || table == 'A008') {
				var isValid = validXlxw($("#id").val(), table, 1);		

				if (isValid == "false") {
					var msg = table == 'A007' ? '学历' : '学位';
					$.messager.alert('提示', '该干部已经通过三龄一历审核认定，不能更改' + msg + '，如需更改，请联系管理员');
					return;
				}
			}		
		}
		
		if ($("#table").val() == 'A010') {
			if (g_valiationMsg != '') {
				$.messager.alert('提示', '请填写如下内容：<br/>' + g_valiationMsg);
				return;
			}
			else {
				var sj = $("#A010_pxsj").val();
				var sjDays = parseInt(sj);
				if (isNaN(sjDays)) {
					$.messager.alert('提示', '培训时间必须是整数！');
					return;
				}
				else if (sjDays <= 0) {
					$.messager.alert('提示', '培训时间必须大于0天！');
					return;
				}
			}
		}
		
		// 编辑A001信息集
		if ($("#id").val() != '' && $("#table").val() == 'A001' && $("#A001_A0255").val() != '1') {
			var isValid = "false";
			$.ajax({
				async: false, // 同步
				type: "POST",
				url: System.rootPath+ '/common/general!checkZhiwChange.action',
				data: { 
					id: $("#id").val(),
					personcode: $("#personcode").val()
				},
				success:function(json){
					//alert(json);
					isValid = json;
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					//alert(textStatus);
					isValid = "false";
				}
			});		

			if (isValid != "true") {
				$.messager.alert('提示', '无法保存该职务，必须至少保留一条在任并且任职机构代码有效的职务！');
				return;
			}
		}
		
		$("#sys_id").val($("#id").val());
		$("#sys_table").val($("#table").val());
		var blobUpdateFields = $("#sys_blobUpdateFields").data("fields");
		var clobUpdateFields = $("#sys_clobUpdateFields").data("fields");
		var dateUpdateFields = $("#sys_dateUpdateFields").data("fields");
		if(blobUpdateFields){
			$("#sys_blobUpdateFields").val($.unique(blobUpdateFields));
		}
		if(clobUpdateFields){
			$("#sys_clobUpdateFields").val($.unique(clobUpdateFields));
		}
		if(dateUpdateFields){
			$("#sys_dateUpdateFields").val($.unique(dateUpdateFields));
		}
		var params = jQuery("#pageData").serialize(); 
		params = decodeURIComponent(params,true);
		//alert(encodeURI(params));
		System.openLoadMask("#pageWin","正在保存...");
		$.ajax({
			type:'post',
			url:System.rootPath+'/common/general!saveData.action',
			data:{'entityData':encodeURI(params)},
			success:function(json){
				try {
					if (json.id == 'undefined') {
						$.messager.alert("提示", "保存失败，请重试！", "error");
					}
					// 如果有未填项，给提示。by YZQ on 2012/11/26
					else {
						if (g_valiationMsg != '') {
							$.messager.alert("提示", '保存成功！<br/><br/>请注意：下列信息是必填项，但是你没有填写。<br/>' + g_valiationMsg);
						}
						
						if (callback && typeof(callback)=="function") {
							callback();
						}
					}
				}
				catch (e) {
					$.messager.alert("提示", "保存失败，错误信息：" + e, "error");
				}
				
				if(json){
					$("#id").val(json.id);
					$("#groupUniqKey").val(json.groupUniqKey);
					$("#personcode").val(json.groupUniqKey);
					var msg = json.saveMsg;
					if(msg == undefined){ msg = "";}
				}else{
				}
				if(!$("#table-list").is(':hidden') ){
					$("#toplist").datagrid('reload');
				}
				$("#sys_blobUpdateFields").val('').removeData("fields");
				
				// 更新职务信息
				if ($("#table").val() == 'A001') {
					updateZW();
				}
				
				// 更新电子印章, by YZQ on 2013/01/16
				updateElecSeal();
			},
			error: function(){
				$.messager.alert("网络错误","出现错误了(>_<)，稍后再试试!","error");
			},
			complete:function(){
				System.closeLoadMask("#pageWin");
			}
		});
	}
};

function uploadCallback(backData){
	$.messager.alert("返回信息","clientPath:"+backData.clientPath + " responseData : " + backData.responseData);
}

function createWidget(v){
	var temp = "";
	switch (v.dbtype_ref) {
		case 2: 
		{
			//temp = "<input id='"+v.field_dbname+"_code' value='"+v.showValue+"' class='codeSelect' onclick='General.showCode(\""+v.field_dbname+"\",\""+v.link_name+"\",\""+v.fieldValue+"\",\""+v.showValue+"\");' ref='"+v.field_name_cn+"' allownull='"+v.allownull+"' codefield='"+v.field_dbname+"'/><input type='hidden' id='"+v.field_dbname+"' name='"+v.field_dbname+"'  value='"+v.fieldValue+"'/><input type='hidden' id='"+v.field_dbname+"_show' name='"+v.field_dbname+"_show' value='"+v.showValue+"'/>";
			// 不使用弹出框, by YZQ on 2012/10/15
			temp = "<div style='display:inline;white-space:nowrap;'>";
			temp += "<input ";
			if (v.editable == 0) {
				temp += " class='easyui-textbox' style='width:160px;' readonly='true' ";
			}
			
			temp += " id='"+v.field_dbname+"_code' value='"+v.showValue+"' ref='"+v.field_name_cn+"' allownull='"+v.allownull+"' codefield='"+v.field_dbname+"'/><input type='hidden' id='"+v.field_dbname+"' name='"+v.field_dbname+"'  value='"+v.fieldValue+"'/><input type='hidden' id='"+v.field_dbname+"_show' name='"+v.field_dbname+"_show' value='"+v.showValue+"'/>";
			
			// 添加删除图标, by YZQ on 2012/12/25, Merry Christmas :)
			if (v.editable == 1) {
				temp += "&nbsp;<img class='removeCode' src='/govhr/style/default/images/icons/code_delete.png' title='点击清除" + v.field_desc + "' onclick='clearCode(\"" + v.field_dbname + "\");'/>";	
			}
			temp += "</div>";
		}
			break;
		case 70: // 多选代码， by YZQ on 2012/09/09
		{
			temp = "<div style='display:inline;white-space:nowrap;'>";
			temp += "<input id='"+v.field_dbname+"_code' value='"+v.showValue+"' class='codeSelect' ";
			if (v.editable == 1) {
				temp += " onclick='General.showCode(\""+v.field_dbname+"\",\""+v.link_name+"\",\""+v.fieldValue+"\",\""+v.showValue+"\",true);' ";
			}
			
			temp += " ref='"+v.field_name_cn+"' allownull='"+v.allownull+"' codefield='"+v.field_dbname+"'/><input type='hidden' id='"+v.field_dbname+"' name='"+v.field_dbname+"'  value='"+v.fieldValue+"'/><input type='hidden' id='"+v.field_dbname+"_show' name='"+v.field_dbname+"_show' value='"+v.showValue+"'/>";
			
			// 添加删除图标, by YZQ on 2014/12/13
			if (v.editable == 1) {
				temp += "&nbsp;<img class='removeCode' src='/govhr/style/default/images/icons/code_delete.png' title='点击清除" + v.field_desc + "' onclick='clearCode(\"" + v.field_dbname + "\");'/>";	
			}
			temp += "</div>";
		}
			break;		
		case 3:
			temp = "<input style='width:160px;' class='easyui-numberbox' id='"+v.field_dbname+"' name='"+v.field_dbname+"' value='"+v.fieldValue+"' min='0' max='10000' ref='"+v.field_name_cn+"' allownull='"+v.allownull+ "' " + (v.editable == 0 ? "readonly='true'" : "") + "/>";
			break;
		case 30:
			temp = "<input style='width:160px;' class='easyui-numberbox' id='"+v.field_dbname+"' name='"+v.field_dbname+"' value='"+v.fieldValue+"' precision='" + v.field_scale + "' ref='"+v.field_name_cn+"' allownull='"+v.allownull+v.allownull+ "' " + (v.editable == 0 ? "readonly='true'" : "") + "/>";
			break;
		case 4:
			temp = "<input type='text' id='"+v.field_dbname+"' name='"+v.field_dbname+"' value='"+v.fieldValue+"' ref='"+v.field_name_cn+"' allownull='"+v.allownull+v.allownull+ "' " + (v.editable == 0 ? " style='width:160px;' class='easyui-textbox' readonly='true'" : " style='width:138px;' class='Wdate' onclick='setday("+v.field_precision+")')") + "/>";
			temp += "<div id='" + v.field_dbname + "Div'></div>";
			break;
		case 5:
			temp = "<select ";
			if (v.editable == 0) {
				temp += " readonly='true' ";
			}
			temp += " class='easyui-combobox' editabled='false' style='width:160px;' id='"+v.field_dbname+"' name='"+v.field_dbname+"' value='"+v.fieldValue+"' ref='"+v.field_name_cn+"' allownull='"+v.allownull+"'>";
			if(v.fieldValue == '1' || v.fieldValue == 'true'){
				temp+="<option value=\"\"></option><option value=\"1\" selected>是</option><option value=\"0\">否</option>";
			}else if(v.fieldValue == '0' || v.fieldValue == 'false'){
				temp+="<option value=\"\"></option><option value=\"1\">是</option><option value=\"0\" selected>否</option>";
			}else{
				temp+="<option value=\"\" selected></option><option value=\"1\">是</option><option value=\"0\">否</option>";
			}
			temp +="</select>";
			break;
		case 6:
		case 60:
			// 显示照片, by YZQ on 2017/06/27
			if (v.field_dbname == 'A000_PHOTO') {
				temp = "<img id=\"personPhoto\" border=\"0\" src=\"" + System.rootPath + "/lob/photo.action?personcode=" + $("#personcode").val() + "&zoom=true&width=100&height=133\" width=\"100\" height=\"133\" />";
			}
			if (v.editable == 1) {
				temp += "<input type='button' value='上传"+v.field_name_cn+"' onclick='ImagePlugin.initUpload(this,\""+v.field_dbname+"\",\""+v.dbtype_ref+"\");'/><input type='hidden' id='"+v.field_dbname+"' name='"+v.field_dbname+"' value='"+v.fieldValue+"' />";
				// 添加清除功能, by YZQ on 2012/11/06
				temp += "<input type='button' value='清除"+v.field_name_cn+"' onclick='clearImage(\"" + v.field_name_cn + "\", \"" + v.field_dbname +"\");'/>";
			}
			
			break;
		case 600:
			if (v.editable == 1) {
				temp = "<input type='button' value='上传文件' onclick='UploadPlugin.initUpload(this,\""+v.field_dbname+"\");'/><input type='hidden' id='"+v.field_dbname+"' name='"+v.field_dbname+"' value='"+v.fieldValue+"' />";
			}
			
			if(v.fieldValue != null){
				temp += "<input id='"+v.field_dbname+"_download' type='button' value='下载' onclick='javascript:UploadPlugin.download(\""+v.field_dbname+"\");' />";
				if($("#hiddenIfr").length<=0){
					temp += "<iframe id=\"hiddenIfr\" name=\"hiddenIfr\" style=\"display:none\" ></iframe>";
				}
			}
			break;
		case 7:
		    {
				// 特殊处理简历字段, by YZQ on 2012/10/30
				if (v.field_dbname == 'A016_A1701') {
					temp = "<textarea type='text' id='"+v.field_dbname+"' name='"+v.field_dbname+"'  cnname='"+v.field_name_cn+"' style='border:none;font-family:宋体;width:500px;height:480px;overflow:auto;' ref='"+v.field_name_cn+"' allownull='"+v.allownull;
					temp += "'";
					if (v.editable == 0) {
						temp += " readonly='readonly' ";
					}
					
					temp += ">"+v.fieldValue+"</textarea>";
				}
				else {
					temp = "<textarea type='text' id='"+v.field_dbname+"' name='"+v.field_dbname+"'  cnname='"+v.field_name_cn+"' readonly=\"readonly\" style='width:138px;height:28px;overflow:hidden;line-height:28px;border: 1px solid #ccc;padding-left:10px;padding-right:10px;' ";
					if (v.editable == 1) {
						temp += " onClick='General.showRemakerWin(this);' ";
					}
					
					temp += " ref='"+v.field_name_cn+"' allownull='"+v.allownull+"'>"+v.fieldValue+"</textarea>";
				}
			}
			break;
		default:
			if(v.property_flag == 1){
				if($("#"+v.field_dbname).length==0){
					temp = "<input type='hidden' id='"+v.field_dbname+"' name='"+v.field_dbname+"' value='"+v.fieldValue+"'/>";
				}else{
					$("#"+v.field_dbname).val(v.fieldValue);
				}
			}else{
				//if(v.field_dbname == 'A000_A0184'){
				//	temp = "<input type='text' id='"+v.field_dbname+"' name='"+v.field_dbname+"' value='"+v.fieldValue+"' ref='"+v.field_name_cn+"' allownull='"+v.allownull+"' onblur=\"javascript:if(isNaN(Utils.isIdCard(this.value))){alert(Utils.isIdCard(this.value));return false;}else{document.getElementById('A000_A0107').value=Utils.getBirthdayBy(this.value);};\"/>";
				//}else{
					temp = "<input style='width:160px;' class='easyui-textbox' type='text' id='"+v.field_dbname+"' name='"+v.field_dbname+"' value='"+v.fieldValue+"' ref='"+v.field_name_cn+"' allownull='"+v.allownull+v.allownull+ "' " + (v.editable == 0 ? "readonly='true'" : "") + "/>";
				//}
			}
			break;
	}
	return temp;
}

function updateZW() {
	var zwUrl = System.rootPath+"/common/exec-common-method!getNameAndZW.action?personcode=" +$("#personcode").val(); 
	$.get(zwUrl, function (data) {
		if (data.zw) {
			$("#personname").html("【" + data.name + " " + data.zw + "】");
		}
		else {
			$("#personname").html("【" + data.name + "】");
		}
	});
}

function clearCode(fieldName) {	
	$("#" + fieldName).val('');
	$("#" + fieldName + "_code").val('');
	$("#" + fieldName + "_show").val('');
	System.code.clear("#" + fieldName + "_code");
}

function clearImage(fieldDesc, fieldName) {
	$.messager.confirm('确认', '你确认要清除' + fieldDesc + "吗？<br/>注意：请点击\"保存\"按钮使操作生效！", function (isOk) {
		if (isOk) {
			$("#" + fieldName).val('*DELETE_ACTION*');
			// 记录更新Blob字段
			var blobUpdateFields = $("#sys_blobUpdateFields").data("fields");
			if(!blobUpdateFields){
				blobUpdateFields = [];
			}
			blobUpdateFields.push(fieldName);
			$("#sys_blobUpdateFields").data("fields",blobUpdateFields);
		}
	});
}

function updateElecSeal() {
	var table = $("#table").val();
	if (table == 'A000') {
		if ($("#sealInfo").length != 0) {
			$("#sealInfo").remove();
		}
		getElecSeal(true);
	}
	else {
		if (table == 'A016') {
			getElecSeal(false);
		}
	}
}

function getElecSeal(appendHtml) {
	var url = System.rootPath+"/common/exec-common-method!getElecSeal.action?personcode=" +$("#personcode").val(); 
	$.get(url, function (data) {
		var content = "<tr id='sealInfo'>";
		if (data.person_confirmed == 'yes') {
			content += "<td colspan='2' align='center'><div class='personConfirmationSeal' style='margin: 0 auto;'><table width='100%'>";
			content += "<tr><td>三龄一历已审核</td></tr>";
			content += "<tr><td>审核单位：" + data.person_confirmed_by + "</td></tr>";
			content += "<tr><td>时间：" + data.person_confirmed_date + "</td></tr>";
			content += "</table></div></td>";
			// 让三龄一历字段只读
			disablePersonConfirmedArea();
		}
		else {
			content += "<td colspan='2'>&nbsp;</td>";
		}		
		
		if (data.log_existed == 'yes') {
			content += "<td colspan='2' align='center'><div class='tableModifyLogSeal' style='margin: 0 auto;'><table width='100%'>";
			content += "<tr><td>任免信息已维护</td></tr>";
			content += "<tr><td>维护单位：" + data.log_created_by + "</td></tr>";
			content += "<tr><td>时间：" + data.log_created_date + "</td></tr>";
			content += "</table></div></td>";
		}
		else {
			content += "<td colspan='2'>&nbsp;</td>";
		}
		
		content += "</tr>";
		
		if (appendHtml) {
			if (data.person_confirmed == 'yes' || data.log_existed == 'yes') {
				$("#contentBody").append(content);
			}
		}
	});
}

function personConfirmation() {
	$.messager.confirm('确认', '你确认该干部的三龄一历已经通过审核？', function (isOk) {
		if (isOk) {
			$.ajax({
				type: "POST",
				url: System.rootPath+ '/common/exec-common-method!personConfirmation.action',
				data: { 
					personcode: $("#personcode").val()
				},
				success:function(json){
					if (json.indexOf("审核成功") >= 0) {
						alert('审核成功!');	
						updateElecSeal();
					}
					else {
						$.messager.alert('提示', '审核失败!');								
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					$.messager.alert('提示', "审核失败,请重试!"+textStatus+errorThrown);
				}
			});		
		}
	});
}

function validXlxw(id, table, changeType) {
	var isValid = "false";
	$.ajax({
		async: false, // 同步
		type: "POST",
		url: System.rootPath+ '/common/general!checkXlxwChange.action',
		data: { 
			id: id,
			tablekey: table,
			personcode: $("#personcode").val(),
			changeType: changeType
		},
		success:function(json){
			isValid = json;
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			isValid = "false";
		}
	});
	
	return isValid;
}

function disablePersonConfirmedArea() {
	if ($("#allowUpdateAfterSlylConfirm").val()=='1') return;
	
	var table = $("#table").val();
	if (table == 'A000') {
		$("#A000_A0107").hide();
		$("#A000_A0107Div").html("<input type='text' readonly='readonly' style='background-color:#fafafa;width:138px;' id='A000_A0107_tmp' value='" + $("#A000_A0107").val() + "'/>");
		
		$("#A000_A0108").numberbox('readonly', true);
		
		$("#A000_A0134").hide();
		$("#A000_A0134Div").html("<input type='text' readonly='readonly' style='background-color:#fafafa;width:138px;' id='A000_A0134_tmp' value='" + $("#A000_A0134").val() + "'/>");
		
		$("#A000_A0144").hide();
		$("#A000_A0144Div").html("<input type='text' readonly='readonly' style='background-color:#fafafa;width:138px;' id='A000_A0144_tmp' value='" + $("#A000_A0144").val() + "'/>");
	}
	else if (table == 'A016') {
		// 允许可以更改经历
		//$("#A016_A1701").attr('readonly', 'readonly');
		//$("#A016_A1701").css('background-color', '#cccccc');
	}
}