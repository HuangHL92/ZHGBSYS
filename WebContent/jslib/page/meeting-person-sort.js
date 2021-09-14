function onSortPerson() {
	endPersonEdit();
	
	var allRows = $("#personList").datagrid('getRows');
	
	if (allRows != null && allRows.length != 0) {
		//allRows.sort(comparePerson);
		//$('#personList').datagrid('loadData', {total:allRows.length, rows: allRows});
		var newRows = new Array();
		for (var i = 0; i < allRows.length; i++) newRows[i] = copyPerson(allRows[i]);
		
		$("#sortPersonDialog").show();
	
		$("#sortPersonDialog").dialog({
			shadow:true,
			modal :true,
			resizable:true,
			width: 640,
			height: 400,
			title: '干部排序',
			buttons:[
			{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					sortPerson();
					$('#sortPersonDialog').dialog('close');
				}
			},{
				text:'取消',
				iconCls:'icon-undo',
				handler:function(){					
					$.messager.confirm('确认',  '确定要取消本次排序吗?', function(isOk) {
						if (isOk) {
							renderSortRow(newRows);
						}
					});
				}
			}],
			onOpen : function () {
				renderSortRow(newRows);
				activeDrag();
			}
		});
	}
	else {
		$.messager.alert('提示', '请选择干部!');
	}
}

function renderSortRow(rowData) {
	$("#emptyTr").remove();
	var trHtml = "",fixTrHtml = "";
	if(rowData && rowData.length>0){
		var sorder = 0;
		$.each(rowData,function(i,v){
			trHtml +="<tr id=\""+v.personcode+"\" " +
						"><td class=\"sorder\" width=\"60\" align=\"center\">"+(sorder+1)+"</td>";
			trHtml +="<td width=\"80\" align=\"center\">"+v.xingm+"</td>";
			trHtml +="<td width=\"100\" align=\"center\">"+v.birthday+"</td>";
			trHtml +="<td width=\"100\">"+(v.xlxw == null ? "": v.xlxw)+"</td>";
			trHtml +="<td width=\"160\">"+ (v.zhiw == null ? "" : v.zhiw)+"</td>";
			trHtml +="<td width=\"90\" align=\"center\"><a class='a_up' href='###' onclick='moveUp(this);'>上移</a>&nbsp;&nbsp;<a class='a_down' href='###' onclick='moveDown(this);'>下移</a></td>";
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
				$(this).addClass('listLi-selected').siblings().removeClass('listLi-selected');
			}
		}
	});	
}

function activeDrag() {
	$("#sortBody").sortable({
		scroll : true,
		scrollSensitivity: 100, 
		scrollSpeed: 100,
		//placeholder: "listLi-selected",
		stop:function(event, ui){				
			//stopDrag();
		}
	}).disableSelection();
}

function sortPerson() {
	var sortRows = [];
	var otable=document.getElementById("listBody");
	var len=otable.rows.length;
	var obj=otable.rows;
	for(var i=0;i<len;i++){
		var row = {
			personcode : obj[i].id
		};
				
		sortRows.push(row);
	}
	
	var curRows = $("#personList").datagrid("getRows");
	for (var i = 0; i < sortRows.length; i++) {
		for (var j = 0; j < curRows.length; j++) {
			var cur = curRows[j];
			var sort = sortRows[i];
			
			if (sort.personcode == cur.personcode) {
				cur.pcnsort = i + 1;
			}
		}
	}
	
	// update
	curRows.sort(comparePerson);
	$("#personList").datagrid("loadData", { total: 0, rows: []});
	$("#personList").datagrid("loadData", { total: curRows.length, rows: curRows});
}

function comparePerson(a, b) {
	var no1 = parseInt(a.pcnsort);
	var no2 = parseInt(b.pcnsort);				
	return no1 - no2;
}

function copyPerson(o) {
	var person = {
					pcbh: o.pcbh,
					personcode: o.personcode,
					birthday: o.birthday,
					pcnsort: o.pcnsort,
					xingm: o.xingm,
					xlxw: o.xlxw,
					zhiw: o.zhiw
				};
				
	return person;
}
			