var MutilRowOrder = {
	initPage:function(){
		this.bindClick();
		this.disableButton();
		this.getList();
	},
	getList : function(){
		$.messager.alert("错误","请重写MutilRowOrder.getList 函数内容!","error");
	},
	reloadCallback : function(){
		$.messager.alert("错误","请重写MutilRowOrder.callback 函数内容!","error");
	},
	undo : function (){
		$.messager.confirm("提示","确定要取消本次排序吗?",function(r){
			if(r){
				MutilRowOrder.disableButton();
				MutilRowOrder.getList();
			}
		});
	},
	addOverClass : function (obj){
		$(obj).addClass('listLi-over');
	},
	changeButtonStatue : function (){
		$("#btnOk").linkbutton("enable");
		$("#btnUndo").linkbutton("enable");
	},
	bindClick:function(){
		$("#listBody tr").on({
			mouseover:function(){
				$(this).addClass('listLi-over');
			},
			mouseout:function(){
				$(this).removeClass('listLi-over');
			},click:function(){
				if(!$(this).is(".listLi-selected")){
					$("#btnMoveUp").linkbutton("enable");
					$("#btnMoveDown").linkbutton("enable");
					$(this).addClass('listLi-selected').siblings().removeClass('listLi-selected');
				}
			}
		});
	},
	disableButton:function(){
		$("#btnMoveUp").linkbutton("disable");
		$("#btnMoveDown").linkbutton("disable");
		$("#btnOk").linkbutton("disable");
		$("#btnUndo").linkbutton("disable");
	},
	MoveUp : function (){
		$("#listBody .listLi-selected").find(".a_up").click();
	},
	MoveDown : function MoveDown(){
		$("#listBody .listLi-selected").find(".a_down").click();
	},
	saveOrder : function(){
		// 必须更新多职务主次序号, by YZQ on 2012/08/21
		var tablekey = $("#tablekey").val();
		var orderFields = "id2";
		if (tablekey == 'A001') orderFields = "id2,A001_A0223";
		// --------------
		//alert($("#personcode").val());
		
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
				
				System.openLoadMask($("body"), "正在更新顺序...");
				$.ajax({
					type:'post',
					url:System.rootPath+'/common/exec-common-method!saveOrder.action?serviceid='+$("#serviceid").val(),
					data:{'idsByOrder':orderitem,'orderFileds':orderFields,'table':$("#tablekey").val(), 'personcode': $("#personcode").val()},
					success:function(back){
						if(back.status==1){
							MutilRowOrder.disableButton();
							MutilRowOrder.getList();
							MutilRowOrder.reloadCallback();
						}else{
							$.messager.alert("错误",back.msg,"error");
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
	},
	loadData:function(){
		var tablekey = $("#tablekey").val();
		System.openLoadMask($("body"), "正在加载...");
		$.ajax({
			type:'post',
			url:System.rootPath+"/query/level-query!getFiledsByTableKey.action",
			data:{"tablekey":$("#tablekey").val()},
			success:function(fields, textStatus){
				var topcolumns = [];
				var headTr = "<td class='label' width=\"35px;\">序号</td>";
				if(fields.length >0){
					var max = fields.length > 5 ? 5 : fields.length;					
					if (tablekey == 'A001' || tablekey == 'A007' || tablekey == 'A008') max = 6;
					
					var fieldcount = 0;
					for(var i=0;i<fields.length; i++){
						var fieldname = fields[i].field_dbname;
						
						// 特殊处理A001 & A007, by YZQ on 2012/08/25
						if (tablekey == 'A001') {
							if (fieldname != 'A001_A0201A' && 
									fieldname != 'A001_A0201B' &&
									fieldname != 'A001_A0215' &&
									fieldname != 'A001_A0217' &&
									fieldname != 'A001_A0255' && 
									fieldname != 'A001_A0223') {
								continue;
							}
						}
						else if (tablekey == 'A007') {
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
						
						var flag = (fields[i].dbtype_ref == 2);
						var column = {title:fields[i].field_name_cn,field:(flag ? fields[i].field_dbname+"_show": fields[i].field_dbname),width:90,align:'center',sortable:true};
						topcolumns.push(column);
						headTr += "<td class='label' width=\"88px;\">"+column.title+"";
					}
				}
				headTr +="<td class='label' width=\"33px;\">↑↓</td>";
				$("#topField").empty().append(headTr);
				$.ajax({
					type:'post',
					url:System.rootPath+"/common/general!getListRowData.action",
					//data:{"personcode":$("#personcode").val(),"groupkey":$("#groupkey").val(),"tablekey":$("#tablekey").val()},
					// bugfix: 我们应该获取所有的记录，否则只会获取前10条记录, by YZQ on 2012/08/21
					data:{"sort": "id2", "page":'1', "rows":'99999', "personcode":$("#personcode").val(),"groupkey":$("#groupkey").val(),"tablekey":$("#tablekey").val()},
					success:function(back){
						$("#emptyTr").remove();
						var trHtml = "";
						if(back && back.rows.length>0){
							var sorder = 0;
							$.each(back.rows,function(i,v){
								trHtml +="<tr class='sorder' height=\"30px;\" id=\""+v.id+"\" " +
								"><td width=\"35px;\" align=\"center\">"+(sorder+1)+"</td>";
								$(topcolumns).each(function(i,column){
									trHtml +="<td width='88px' align='center'>"+Utils.setNullToEmpty(eval("v."+column.field))+"&nbsp;</td>";
								});
								trHtml +="<td width='33px' align=\"center\"><a class='a_up' href='###' onclick='javascript:MutilRowOrder.changeButtonStatue();moveUp(this);'>↑</a>&nbsp;&nbsp;<a class='a_down' href='###' onclick='MutilRowOrder.changeButtonStatue();moveDown(this);'>↓</a></td>";
								trHtml +="</tr>";
								sorder +=1;
								//if(i==back.rows.length-1){
								//	$("#listBody").empty().append(trHtml);									
								//}
							});
						}
						
						$("#listBody").empty().append("<tbody id='sortBody'>" + trHtml + "</tbody>");
				
						MutilRowOrder.bindClick();
					},
					complete:function(){
						System.closeLoadMask($("body"));
					}
				});
			}
		});
	}
};