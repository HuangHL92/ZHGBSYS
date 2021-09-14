var Unit = {};
var isNew = false;
Unit.org = {
	initGrid :function (target){
		$(target).treegrid({
			url:System.rootPath+"/common/unit!getOrgList.action?serviceid="+$("#serviceid").val()+"&currentUnit="+$("#currentUnit").val(),
			width:$('body').outerWidth(true),
			height:365,
			nowrap: false,
			rownumbers: true,
			animate:true,
			collapsible:true,
			idField:'unitcode',
			treeField:'unitname',
			frozenColumns:[[
                {field:'unitname',title:'内设机构名称',width:350,resizable:true,
                	editor:{
                		type:'text',
                		options:{required:true}
                	}
                }
			]],
			columns:[[
				{field:'remark',title:'备注',width:150,resizable:true,
					editor:{
	            		type:'text'
	            	}
				}
			]],
			onClickRow : function(row){
				$("#nodefather").val(row.nodefather);
				$("#id").val(row.id);
				$("#orgcode").val(row.unitcode);
				$("#orgname").val(row.unitname);
				$("#remark").val(row.remark);
			},
			toolbar:[{
				id:'btnadd',
				text:'添加',
				iconCls:'icon-add',
				handler:function(){
					$("#newOrgForm").form('clear');
					var node = $(target).treegrid('getSelected');
					if(node){
						$("#nodefather").val(node.unitcode);
					}else{
						$("#nodefather").val($("#currentUnit").val());
					}
					isNew = true;
					$("#newOrg").dialog('open');
				}
			},'-',{
				id:'btnedit',
				text:'编辑',
				iconCls:'icon-edit',
				handler:function(){
					var node = $(target).treegrid('getSelected');
					if(node){
						$("#nodefather").val(node.nodefather);
						$("#id").val(node.id);
						$("#orgcode").val(node.unitcode);
						$("#orgname").val(node.unitname);
						$("#remark").val(node.remark);
						isNew = false;
						$("#newOrg").dialog({
							title:'编辑内设机构'
						});
					}else{
						$.messager.alert("提示","请选择内设机构!");
					}
				}
			},'-',{
				id:'btnremove',
				text:'删除',
				iconCls:'icon-remove',
				handler:function(){
					var node = $(target).treegrid('getSelected');
					if(node){
						$.post(System.rootPath+"/common/unit!removeOrg.action",{'orgEntity.orgcode':node.unitcode},function(){
							$(target).treegrid('remove',node.unitcode);
							$("#nodefather").val($("#currentUnit").val());
							$("#id").val("");
							$("#orgcode").val("");
							$("#orgname").val("");
							$("#remark").val("");
							$(target).treegrid('reload');
						});
					}else{
						$.messager.alert("提示","请选择要删除的内设机构!");
					}
				}
			},'-',{
				id:'btnprint',
				text:'打印',
				disabled:true,
				iconCls:'icon-print',
				handler:function(){
					
				}
			},'-',{
				id:'btnorder',
				text:'排序',
				disabled:true,
				iconCls:'icon-sum',
				handler:function(){
					System.unit.orderList($('#currentUnit').val(),$('#serviceid').val());
				}
			}]
		});
	},
	initDialog : function (target) {
		$("#newOrg").dialog({
			title:'添加内设机构',
			buttons:[{
				id:'btn_save',
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					$("#orgname").validatebox();
					$("#newOrgForm").form('submit',{
						url :System.rootPath+'/common/unit!saveOrg.action',
						onSubmit: function(){
							var sub = $(this).form('validate');
							if(sub){
								System.openLoadMask($("#newOrg"));
							}
							return sub;
						},
						success:function(data){
							System.closeLoadMask($("#newOrg"));
							if(data){
								var datas = data.split('||');
								if(datas.length >= 3){
									var id = datas[1];
									var orgcode = datas[2];
						            var node = $(target).treegrid('getSelected');
						            if(isNew){
										$(target).treegrid('append', {
											parent: (node?node.unitcode:null),
											data: [{
												id:id,
												unitcode:orgcode,
												unitname:$("#orgname").val(),
												remark:$("#remark").val(),
												nodefather:$("#nodefather").val()
											}]
										});
						            }else{
						            	$(target).treegrid('reload');
						            }
									$("#newOrg").dialog('close');
									$.messager.alert('提示',datas[0]);
								}else{
									$.messager.alert('提示',datas[0]);
								}
							}
						}
					});
				}
			},{
				id:'btn_cancel',
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$("#newOrg").dialog('close');
				}
			}]
		});
		$("#newOrg").dialog('close');
	}
};