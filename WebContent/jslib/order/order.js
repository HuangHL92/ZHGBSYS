var g_canSort = false; // 是否可以排序

function addOverClass(obj){
	$(obj).addClass('listLi-over');
}

function changeButtonStatue(){
	if (g_canSort) {
		$("#btnOk").linkbutton("enable");
		$("#btnUndo").linkbutton("enable");
	}
}

function changeButtonStatue2(){
	$("#btnOk").linkbutton("enable");
	$("#btnUndo").linkbutton("enable");
}

function stopDrag() {
	$("#btnOk").linkbutton("enable");
	$("#btnUndo").linkbutton("enable");      
	
	//var sorder = 1;
	//$(".sorder").each(function(i) {
	//	$(this).html(sorder++);
	//});
}

function activeDrag() {
	$("#sortBody").sortable({
		scroll : true,
		scrollSensitivity: 100, 
		scrollSpeed: 100,
		//placeholder: "listLi-selected",
		stop:function(event, ui){				
			stopDrag();
		}
	}).disableSelection();
}

var PersonalOrder = {
	list:function(serviceid,unitcode){
		System.openLoadMask($("#pageWin"), "正在加载...");
		$.ajax({
			type:'post',
			url:System.rootPath+'/common/order!getPersonListByUnitcode.action?serviceid='+serviceid,
			data:{'currentUnit':unitcode},
			success:function(back){
				$("#emptyTr").remove();
				var trHtml = "",fixTrHtml = "";
				if(back && back.length>0){
					var sorder = 0;//,fixSorder = 0;
					$.each(back,function(i,v){
						//if(v.a0201b == unitcode){
							trHtml +="<tr height=\"30px;\" id=\""+v.personcode+"\" " +
									"><td class=\"sorder\" width=\"60px;\" align=\"center\">"+(sorder+1)+"</td>";
							if (v.isConfirmed == '1') {
								var img = "<img src='/govhr/style/default/images/seal.png' border='0' title='三龄一历已审核' style='vertical-middle: middle;'/>";
								trHtml +="<td width=\"32px;\" align=\"center\">"+img+"</td>";
							}
							else {
								var img = "<img src='/govhr/style/default/images/unseal.png' border='0' title='三龄一历未审核' style='vertical-middle: middle;'/>";
								trHtml +="<td width=\"32px;\" align=\"center\">"+img+"</td>";
							}
							
							trHtml +="<td width=\"80px;\" align=\"center\"><a href='javascript:void(0);' onclick='System.leaderQuery.showRemoval("+v.personcode+")'>"+v.xingm+"</a></td>";
							trHtml +="<td width=\"60px;\" align=\"center\">"+v.sex+"</td>";
							trHtml +="<td width=\"120px;\">"+(v.jig == null ? "": v.jig)+"</td>";
							trHtml +="<td width=\"345px;\">"+ (v.zhiw == null ? "" : v.zhiw)+"</td>";
							trHtml +="<td width=\"100px;\" align=\"center\"><a class='a_up' href='###' onclick='javascript:changeButtonStatue();moveUp(this);'>上移</a>&nbsp;&nbsp;<a class='a_down' href='###' onclick='changeButtonStatue();moveDown(this);'>下移</a></td>";
							trHtml +="</tr>";
							sorder +=1;
						//}else{
						//	fixTrHtml +="<tr height=\"30px;\" id=\"'"+v.personcode+"\"' " +
						//			"><td width=\"60px;\" align=\"center\">"+(fixSorder+1)+"</td>";
						//	fixTrHtml +="<td width=\"80px;\" align=\"center\"><a href='javascript:void(0);' onclick='System.leaderQuery.showRemoval("+v.personcode+")'>"+v.xingm+"</a></td>";
						//	fixTrHtml +="<td width=\"60px;\" align=\"center\">"+v.sex+"</td>";
						//	fixTrHtml +="<td width=\"120px;\">"+(v.jig == null ? "": v.jig)+"</td>";
						//	fixTrHtml +="<td width=\"345px;\">"+v.zhiw+"</td>";
						//	fixTrHtml +="<td width=\"100px;\" align=\"center\"><font color='gray'>上移</font>&nbsp;&nbsp;<font color='gray'>下移</font></td>";
						//	fixTrHtml +="</tr>";
						//	fixSorder +=1;
						//}
					});
				}
				$("#listBody").empty().append("<tbody id='sortBody'>" + trHtml + "</tbody>");
				//$("#listFixBody").empty().append(fixTrHtml);
				
				$("#listBody tr").on({
					mouseover:function(){
						$(this).addClass('listLi-over');
					},
					mouseout:function(){
						$(this).removeClass('listLi-over');
					},
					click:function(){
						if(!$(this).is(".listLi-selected")){
							$("#btnMoveUp").linkbutton("enable");
							$("#btnMoveDown").linkbutton("enable");
							$(this).addClass('listLi-selected').siblings().removeClass('listLi-selected');
						}
					}
				});
		
				activeDrag();
			},
			complete :function(XMLHttpRequest, textStatus){
				System.closeLoadMask($("#pageWin"));
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				$.messager.alert("error",textStatus + errorThrown);
			}
		});
		$("#btnMoveUp").linkbutton("disable");
		$("#btnMoveDown").linkbutton("disable");
		$("#btnOk").linkbutton("disable");
		$("#btnUndo").linkbutton("disable");
	},
	saveOrder : function(){
		$.messager.confirm("提示","确定要保存排序结果吗?",function(r){
			if(r){
				var orderitem="";
				var otable=document.getElementById("listBody");
				var len=otable.rows.length;
				var Obj=otable.rows;
				for(var i=0;i<len;i++){
					if(i==0){
					  orderitem=Obj[i].id;
					}else{
					  orderitem=orderitem+","+Obj[i].id;
					}
				}
				System.openLoadMask($("#pageWin"), "正在更新顺序...");
				$.ajax({
					type:'post',
					url:System.rootPath+'/common/order!savePersonOrder.action?serviceid='+$("#serviceid").val(),
					data:{'orderitem':orderitem,'currentUnit':$("#currentUnit").val()},
					success:function(back){
						$.messager.alert("提示",back);
						PersonalOrder.list($("#serviceid").val(),$("#currentUnit").val());
						$("#btnOk").linkbutton("disable");
						$("#btnUndo").linkbutton("disable");
					},
					complete :function(XMLHttpRequest, textStatus){
						System.closeLoadMask($("#pageWin"));
					},
					error : function(XMLHttpRequest, textStatus, errorThrown){
						$.messager.alert("error",textStatus + errorThrown);
					}
				});
			}
		});
	},
	undo : function(){
		$.messager.confirm("提示","确定要取消本次排序吗?",function(r){
			if(r){
				PersonalOrder.list($("#serviceid").val(),$("#currentUnit").val());
			}
		});
	}
};

var UnitOrder = {
		list:function(serviceid,unitcode){
			System.openLoadMask($("#pageWin"), "正在加载...");
			$.ajax({
				type:'post',
				url:System.rootPath+'/common/order!getUnitListByUnitcode.action?serviceid='+serviceid,
				data:{'currentUnit':unitcode},
				success:function(back){
					$("#emptyTr").remove();
					var trHtml = "",fixTrHtml = "";
					if(back && back.length>0){
						$.each(back,function(i,v){
							trHtml +="<tr height=\"30px;\" id=\""+v.B000_unitname+"_"+v.org+"\" " +
									"><td class=\"sorder\" width=\"60px;\" align=\"center\">"+(i+1)+"</td>";
							trHtml +="<td width=\"640px;\">"+v.B000_unitname_show+"</td>";
							trHtml +="<td width=\"100px;\" align=\"center\"><a class='a_up' href='javascript:void(0);' onclick='changeButtonStatue();moveUp(this);'>上移</a>&nbsp;&nbsp;<a class='a_down' href='javascript:void(0);' onclick='changeButtonStatue();moveDown(this);'>下移</a></td>";
							trHtml +="</tr>";
						});
					}
					$("#listBody").empty().append("<tbody id='sortBody'>" + trHtml + "</tbody>");
					
					$("#listBody tr").on({
						mouseover:function(){
							$(this).addClass('listLi-over');
						},
						mouseout:function(){
							$(this).removeClass('listLi-over');
						},
						click:function(){
							if(!$(this).is(".listLi-selected")){
								$("#btnMoveUp").linkbutton("enable");
								$("#btnMoveDown").linkbutton("enable");
								$(this).addClass('listLi-selected').siblings().removeClass('listLi-selected');
							}
						}
					});
				
					activeDrag();
				},
				complete :function(XMLHttpRequest, textStatus){
					System.closeLoadMask($("#pageWin"));
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
					$.messager.alert("error",textStatus + errorThrown);
				}
			});
			$("#btnMoveUp").linkbutton("disable");
			$("#btnMoveDown").linkbutton("disable");
			$("#btnOk").linkbutton("disable");
			$("#btnUndo").linkbutton("disable");
		},
		saveOrder : function(reloadTree){
			$.messager.confirm("提示","确定要保存排序结果吗?",function(r){
				if(r){
					var orderitem="";
					var otable=document.getElementById("listBody");
					var len=otable.rows.length;
					var Obj=otable.rows;
					for(var i=0;i<len;i++){
						if(i==0){
						  orderitem=Obj[i].id;
						}else{
						  orderitem=orderitem+","+Obj[i].id;
						}
					}
					System.openLoadMask($("#pageWin"), "正在更新顺序...");
					$.ajax({
						type:'post',
						url:System.rootPath+'/common/order!saveUnitOrder.action?serviceid='+$("#serviceid").val(),
						data:{'orderitem':orderitem,'currentUnit':$("#currentUnit").val(),'parentSorder':$("#parentSorder").val()},
						success:function(back){
							if(reloadTree){
								//$("#tt").tree('reload');								
							}
							UnitOrder.list($("#serviceid").val(),$("#currentUnit").val());
							$.messager.alert("提示",back);
							$("#btnOk").linkbutton("disable");
							$("#btnUndo").linkbutton("disable");
						},
						complete :function(XMLHttpRequest, textStatus){
							System.closeLoadMask($("#pageWin"));
						},
						error : function(XMLHttpRequest, textStatus, errorThrown){
							$.messager.alert("error",textStatus + errorThrown);
						}
					});
				}
			});
		},
		undo : function(){
			$.messager.confirm("提示","确定要取消本次排序吗?",function(r){
				if(r){
					UnitOrder.list($("#serviceid").val(),$("#currentUnit").val());
				}
			});
		}
	};

var FieldOrder = {
	list:function(tableName){
		System.openLoadMask($("#pageWin"), "正在加载...");
		$.ajax({
			type:'POST',
			url: System.rootPath + '/table/table!queryAllFields.action?tablename=' + tableName,				
			success:function(back){
				$("#emptyTr").remove();
				var trHtml = "";
				if(back && back.rows.length>0){
					var sorder = 0;
					$.each(back.rows,function(i,v){
						var fieldType = '';
						if (v.property_flag == 1) fieldType = '系统字段';
						else if (v.property_flag != 255) fieldType = '可维护字段';
						else fieldType = '不可维护字段';
						
						trHtml +="<tr height=\"30px;\" id=\""+v.id+"\">";
						trHtml +="<td class=\"sorder\" width=\"60px;\" align=\"center\">"+(sorder+1)+"</td>";
						trHtml +="<td width=\"200px;\" align=\"center\">" + v.field_name_cn + "</td>";
						trHtml +="<td width=\"80px;\" align=\"center\">"+ fieldType +"</td>";
						trHtml +="<td width=\"100px;\" align=\"center\"><a class='a_up' href='###' onclick='javascript:changeButtonStatue2();moveUp(this);'>上移</a>&nbsp;&nbsp;<a class='a_down' href='###' onclick='changeButtonStatue2();moveDown(this);'>下移</a></td>";
						trHtml +="</tr>";
						sorder +=1;						
					});
				}
				$("#listBody").empty().append("<tbody id='sortBody'>" + trHtml + "</tbody>");	
				
				$("#listBody tr").on({
					mouseover:function(){
						$(this).addClass('listLi-over');
					},
					mouseout:function(){
						$(this).removeClass('listLi-over');
					},
					click:function(){
						if(!$(this).is(".listLi-selected")){
							$("#btnMoveUp").linkbutton("enable");
							$("#btnMoveDown").linkbutton("enable");
							$(this).addClass('listLi-selected').siblings().removeClass('listLi-selected');
						}
					}
				});
				
				activeDrag();
			},
			complete :function(XMLHttpRequest, textStatus){
				System.closeLoadMask($("#pageWin"));
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				$.messager.alert("error",textStatus + errorThrown);
			}
		});
		$("#btnMoveUp").linkbutton("disable");
		$("#btnMoveDown").linkbutton("disable");
		$("#btnOk").linkbutton("disable");
		$("#btnUndo").linkbutton("disable");
	},
	saveOrder : function(){
		$.messager.confirm("提示","确定要保存排序结果吗?",function(r){
			if(r){
				var orderitem="";
				var otable=document.getElementById("listBody");
				var len=otable.rows.length;
				var Obj=otable.rows;
				for(var i=0;i<len;i++){
					if(i==0){
					  orderitem=Obj[i].id;
					}else{
					  orderitem=orderitem+","+Obj[i].id;
					}
				}
				//alert(orderitem);
				System.openLoadMask($("#pageWin"), "正在更新顺序...");
				$.ajax({
					type:'POST',
					url:System.rootPath+'/table/table!saveFieldOrder.action?tablename='+$("#tablename").val(),
					data:{'orderitem':orderitem},
					success:function(back){
						$.messager.alert("提示",back);
						FieldOrder.list($("#tablename").val());
						$("#btnOk").linkbutton("disable");
						$("#btnUndo").linkbutton("disable");
					},
					complete :function(XMLHttpRequest, textStatus){
						System.closeLoadMask($("#pageWin"));
					},
					error : function(XMLHttpRequest, textStatus, errorThrown){
						$.messager.alert("error",textStatus + errorThrown);
					}
				});
			}
		});
	},
	undo : function(){
		$.messager.confirm("提示","确定要取消本次排序吗?",function(r){
			if(r){
				FieldOrder.list($("#tablename").val());
			}
		});
	}
};

var TableOrder = {
	list:function(groupName){
		System.openLoadMask($("#pageWin"), "正在加载...");
		$.ajax({
			type:'POST',
			url: System.rootPath + '/table/table!queryAllTables.action?groupname=' + groupName,				
			success:function(back){
				$("#emptyTr").remove();
				var trHtml = "";
				if(back && back.rows.length>0){
					var sorder = 0;
					$.each(back.rows,function(i,v){
						var tableType = '';
						if (v.property_flag != 255) tableType = '可维护';
						else tableType = '不可维护';
						
						trHtml +="<tr height=\"30px;\" id=\""+v.id+"\">";
						trHtml +="<td class=\"sorder\" width=\"60px;\" align=\"center\">"+(sorder+1)+"</td>";
						trHtml +="<td width=\"200px;\" align=\"center\">" + v.table_name_cn + "</td>";
						trHtml +="<td width=\"80px;\" align=\"center\">" + v.table_name + "</td>";
						trHtml +="<td width=\"80px;\" align=\"center\">"+ tableType +"</td>";
						trHtml +="<td width=\"100px;\" align=\"center\"><a class='a_up' href='###' onclick='javascript:changeButtonStatue2();moveUp(this);'>上移</a>&nbsp;&nbsp;<a class='a_down' href='###' onclick='changeButtonStatue2();moveDown(this);'>下移</a></td>";
						trHtml +="</tr>";
						sorder +=1;						
					});
				}
				$("#listBody").empty().append("<tbody id='sortBody'>" + trHtml  + "</tbody>");

				$("#listBody tr").on({
					mouseover:function(){
						$(this).addClass('listLi-over');
					},
					mouseout:function(){
						$(this).removeClass('listLi-over');
					},
					click:function(){
						if(!$(this).is(".listLi-selected")){
							$("#btnMoveUp").linkbutton("enable");
							$("#btnMoveDown").linkbutton("enable");
							$(this).addClass('listLi-selected').siblings().removeClass('listLi-selected');
						}
					}
				});
				
				activeDrag();
			},
			complete :function(XMLHttpRequest, textStatus){
				System.closeLoadMask($("#pageWin"));
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				$.messager.alert("error",textStatus + errorThrown);
			}
		});
		$("#btnMoveUp").linkbutton("disable");
		$("#btnMoveDown").linkbutton("disable");
		$("#btnOk").linkbutton("disable");
		$("#btnUndo").linkbutton("disable");
	},
	saveOrder : function(){
		$.messager.confirm("提示","确定要保存排序结果吗?",function(r){
			if(r){
				var orderitem="";
				var otable=document.getElementById("listBody");
				var len=otable.rows.length;
				var Obj=otable.rows;
				for(var i=0;i<len;i++){
					if(i==0){
					  orderitem=Obj[i].id;
					}else{
					  orderitem=orderitem+","+Obj[i].id;
					}
				}
				//alert(orderitem);
				System.openLoadMask($("#pageWin"), "正在更新顺序...");
				$.ajax({
					type:'POST',
					url:System.rootPath+'/table/table!saveTableOrder.action?groupname='+$("#groupname").val(),
					data:{'orderitem':orderitem},
					success:function(back){
						$.messager.alert("提示",back);
						TableOrder.list($("#groupname").val());
						$("#btnOk").linkbutton("disable");
						$("#btnUndo").linkbutton("disable");
					},
					complete :function(XMLHttpRequest, textStatus){
						System.closeLoadMask($("#pageWin"));
					},
					error : function(XMLHttpRequest, textStatus, errorThrown){
						$.messager.alert("error",textStatus + errorThrown);
					}
				});
			}
		});
	},
	undo : function(){
		$.messager.confirm("提示","确定要取消本次排序吗?",function(r){
			if(r){
				TableOrder.list($("#groupname").val());
			}
		});
	}
};