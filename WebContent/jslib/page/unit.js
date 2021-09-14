var Unit = {
	addUnit:function(){
		//var node = $("#tt").tree('getSelected');
		var node = getSelectedNode();		
		var nodefather = "-1";
		if(node){
			nodefather = node.id;
		}
		$.get(System.rootPath+"/common/unit!preAdd.action",{'nodeFather':nodefather},function (entity){
			$("#btn_remove").linkbutton("disable");
			$("#btn_more").linkbutton("disable");
			$("#btnedit,#btnorder,#btnprint").unbind('click');
			$("#btnedit,#btnorder,#btnprint").linkbutton('disable');
			render(entity);
		});
	},
	editOrg : function (){
		var unitid = $("#sysid").val();
		$("#openWinDetail").show().dialog({
			title:'内设机构维护',
			height:400,
			width:800,
			modal : true
		});
		$("#openWinDetail").show().dialog('open');
		$("#pageParames").attr("action",$("#currentAction").val()+"!editOrg.action").submit();
	},
	init:function(){
		System.openLoadMask("#pageWin");
		$.ajax({
			url:System.rootPath+'/common/unit!getUnitInfo.action',
			type:'post',
			dataType:'json',
			data:{'currentUnit':$('#currentUnit').val()},
			success:function(entity){
				disableButtons();
				render(entity);
				
				// 检查是否可以维护该单位
				$("#btn_more").linkbutton("enable"); // 永远都可以看更多
				System.permission.checkUnitPermission($('#currentUnit').val(), function(flag) {
					if (flag.editable) {
						$("#btnadd").linkbutton("enable");
						$("#btnorder").linkbutton("enable");
						$("#btnedit").linkbutton('enable');
						$("#btnedit").unbind('click').click(function(){Unit.editOrg();});
						$("#btnorder").linkbutton('enable');
						$("#btnorder").unbind('click').click(function(){Unit.order();});
						$("#btn_save").linkbutton("enable");
						$("#btn_remove").linkbutton("enable");						
					}
				});
			},
			error:function(){
				$.messager.alert("错误","网络错误!","error");
			},
			complete:function(){
				System.closeLoadMask("#pageWin");
			}
		});
	},
	order : function(){
		System.unit.orderList($('#currentUnit').val(),$('#serviceid').val());
		//$("#tt").tree('reload');
		//var tree = getZTreeOjb('tt');
		//tree.refresh();
		//try {initUnitTree();} catch (e) {}
	},
	detail: function(){
		/*
		$("#openWinDetail").show().dialog({
			title:'详细',
			height:600,
			width:1000,
			modal : true
		});
		$("#openWinDetail").show().dialog('open');
		$("#pageParames").attr("action",$("#currentAction").val()+"!detail.action").submit();
		*/
		var url = $("#currentAction").val() + "!detail.action";
		var params = $("#pageParames").serialize();
		url = url + "?" + params;
		var name = $("#b000_unitname_show").val();
		//System.openURL(name + " - 信息维护", url);
		System.openPopupWindow(url, name + " - 信息维护", 900, 590);
	},
	saveData : function(){
		var isNew = ($("#b000_unitname").val() == "");
		var isValid = $("#pageData").form('validate');
		if (!isValid) {
			return;
		}
		
		MaskUtil.mask('正在保存...');
		var url = System.rootPath+'/common/unit!saveUnitInfo.action';
		$.post(url, $("#pageData").serialize(), function(data) {
			MaskUtil.unmask();
			var msg = data.split("||")[0];
			var status = data.split("||")[1];
			var b000_unitname = data.split("||")[2];
			if(status == "true"){
				$("#b000_unitname").val(b000_unitname);
				$("#b000_unitname_dis").val(b000_unitname);
				//var node = $('#tt').tree('getSelected');
				var node = getSelectedNode();
				if(isNew){
					//$('#tt').tree('append',{
					//	parent: (node?node.target:$('#tt2').tree('find','-1').target),
					//	data:[{
					//		id:b000_unitname,
					//		text:$("#b000_unitname_show").val()
					//	}]
					//});
					var tree = getZTreeObj('tt');
					var newNode = { text: $("#b000_unitname_show").val(), id: b000_unitname };
					tree.addNodes(node, newNode);
					var nodes = tree.getNodesByParam("id", b000_unitname); 
					if (nodes.length > 0) {
						tree.selectNode(nodes[0]);
					}
					
					$.messager.alert("提示",msg);						
				}else{
					//$('#tt').tree('update',{
					//	target: node.target,
					//	id:b000_unitname,
					//	text:$("#b000_unitname_show").val()
					//});
					var tree = getZTreeObj('tt');
					node.text = $("#b000_unitname_show").val();
					tree.updateNode(node);
						
					$.messager.alert("提示",msg);
				}
			}else{
				$.messager.alert("提示",msg);
			}
		});
	},
	removeData : function(){
		$.messager.confirm("提示","确定要删除本单位吗？",function(b){
			if(b){
				$.get(System.rootPath+"/common/unit!removeUnit.action",{'entity.b000_unitname':$("#b000_unitname").val()},function(data){
					if(data.status){
						//var node = $("#tt").tree('find',$("#currentUnit").val());
						//$("#tt").tree('remove',node.target);
						var tree = getZTreeObj('tt');
						var nodes = tree.getNodesByParam("id", $("#currentUnit").val());
						if (nodes.length > 0) {
							tree.removeNode(nodes[0]);
						}
						
						Unit.addUnit();
					}else{
						$.messager.alert("提示",data.msg,"info");
					}
				});
			}
		});
	}
};

Unit.list = {
	initGrid :function (target){
		$(target).treegrid({
			width:$('#pageWin').outerWidth(true)-265,
			height:493,
			nowrap: false,
			rownumbers: true,
			animate:true,
			collapsible:true,
			idField:'unitcode',
			treeField:'unitname',
			frozenColumns:[[
                {title:'单位名称',field:'unitname',width:350,
	                formatter:function(value,rowData){
                		if(rowData.org == 2){
                			return value + '(<span style="color:red">内设机构</span>)';
                		}else{
                			return value;
                		}
	                }
                }
			]],
			columns:[[
				{field:'unitlevelshow',title:'单位级别',width:150},
				{field:'unitsubshow',title:'单位隶属关系',width:220,rowspan:2},
				{field:'unittypeshow',title:'单位性质',width:150,rowspan:2}
			]],
			onBeforeLoad:function(row,param){
			},
			toolbar:[{
				id:'btnadd',
				text:'添加单位',
				iconCls:'icon-edit',
				handler:function(){
					System.unit.orderList($('#currentUnit').val(),$('#serviceid').val());
				}
			},'-',{
				id:'btnedit',
				text:'维护内设机构',
				disabled:true,
				iconCls:'icon-edit',
				handler:function(){
					System.unit.orderList($('#currentUnit').val(),$('#serviceid').val());
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
	loadDataByUnit:function(target){
		$(target).treegrid('options').url = System.rootPath+"/common/unit!getUnitList.action?serviceid="+$("#serviceid").val()+"&currentUnit="+$("#currentUnit").val();
		$(target).treegrid('reload');
	}
};

function render(entity){
	$("#b000_B0101A").val(entity.b000_B0101A);
	
	System.code.render("#b000_B0101B_code","1041",entity.b000_B0101B,entity.b000_B0101B_SHOW,150,function(node){
		$("#b000_B0101B").val(node.id);
		$("#b000_B0101B_SHOW").val(node.text);
		
	},function(){});
	$("#b000_B0101B").val(entity.b000_B0101B);
	$("#b000_B0101B_SHOW").val(entity.b000_B0101B_SHOW);
	
	$("#b000_B0104").textbox('setValue', entity.b000_B0104);
	$("#b000_B0107").textbox('setValue', entity.b000_B0107);
	$("#b000_B0114").textbox('setValue', entity.b000_B0114);
	
	System.code.render("#b000_B0117_code","9001",entity.b000_B0117,entity.b000_B0117_SHOW,150,function(node){
		$("#b000_B0117").val(node.id);
		$("#b000_B0117_SHOW").val(node.text);
	},function(){});
	$("#b000_B0117").val(entity.b000_B0117);
	$("#b000_B0117_SHOW").val(entity.b000_B0117_SHOW);
	
	System.code.render("#b000_B0124_code","0003",entity.b000_B0124,entity.b000_B0124_SHOW,150,function(node){
		$("#b000_B0124").val(node.id);
		$("#b000_B0124_SHOW").val(node.text);
	},function(){});
	$("#b000_B0124").val(entity.b000_B0124);
	$("#b000_B0124_SHOW").val(entity.b000_B0124_SHOW);
	
	System.code.render("#b000_B0127_code","1003",entity.b000_B0127,entity.b000_B0127_SHOW,150,function(node){
		$("#b000_B0127").val(node.id);
		$("#b000_B0127_SHOW").val(node.text);
	},function(){});
	$("#b000_B0127").val(entity.b000_B0127);
	$("#b000_B0127_SHOW").val(entity.b000_B0127_SHOW);
	
	System.code.render("#b000_B0131_code","1004",entity.b000_B0131,entity.b000_B0131_SHOW,150,function(node){
		$("#b000_B0131").val(node.id);
		$("#b000_B0131_SHOW").val(node.text);
	},function(){});
	$("#b000_B0131").val(entity.b000_B0131);
	$("#b000_B0131_SHOW").val(entity.b000_B0131_SHOW);
	
	System.code.render("#b000_B0134_code","0020",entity.b000_B0134,entity.b000_B0134_SHOW,150,function(node){
		$("#b000_B0134").val(node.id);
		$("#b000_B0134_SHOW").val(node.text);
	},function(){});
	$("#b000_B0134").val(entity.b000_B0134);
	$("#b000_B0134_SHOW").val(entity.b000_B0134_SHOW);
	
	$("#b000_B0151A").textbox('setValue', entity.b000_B0151A);
	$("#b000_B0154A").textbox('setValue', entity.b000_B0154A);
	$("#b000_B0157A").textbox('setValue', entity.b000_B0157A);
	$("#b000_B0161").textbox('setValue', entity.b000_B0161);
	$("#b000_B0164").textbox('setValue', entity.b000_B0164);
	$("#b000_B0167").textbox('setValue', entity.b000_B0167);
	$("#b000_B0171A").textbox('setValue', entity.b000_B0171A);
	
	$("#b000_unitname").val(entity.b000_unitname);
	$("#b000_unitname_dis").textbox('setValue', entity.b000_unitname);
	$("#b000_unitname_show").textbox('setValue', entity.b000_unitname_show);
	System.code.render("#b000_Y003_code","3001",entity.b000_Y003,entity.b000_Y003_SHOW,150,function(node){
		$("#b000_Y003").val(node.id);
		$("#b000_Y003_SHOW").val(node.text);
	},function(){});
	$("#b000_Y003").val(entity.b000_Y003);
	$("#b000_Y003_SHOW").val(entity.b000_Y003_SHOW);
	
	System.code.render("#b000_jglx_code","JGLX",entity.b000_jglx,entity.b000_jglx_show,150,function(node){
		$("#b000_jglx").val(node.id);
		$("#b000_jglx_show").val(node.text);
	},function(){});
	$("#b000_jglx").val(entity.b000_jglx);
	$("#b000_jglx_show").val(entity.b000_jglx_show);
	
	$("#id").val(entity.id);
	$("#sysid").val(entity.id);
	$("#unitcode").val(entity.unitcode);
	$("#sysunitcode").val(entity.unitcode);
	$("#node_father").val(entity.node_father);
	$("#sysnode_father").val(entity.node_father);
	$("#syssorder").val(entity.sorder);
	
}

function getSelectedNode() {
	var tree = getZTreeObj('tt');
	var nodes = tree.getSelectedNodes();
	var node = null;
	if (nodes.length > 0) node = nodes[0];
	return node;
}

function disableButtons() {
	$("#btnadd").linkbutton("disable");
	$("#btnorder").linkbutton("disable");
	$("#btn_save").linkbutton("disable");
	$("#btn_remove").linkbutton("disable");
	$("#btn_more").linkbutton("disable");
}