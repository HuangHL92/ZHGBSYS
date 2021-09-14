var impData = {
	init : function (target,t){
		$(target).datagrid({
			//height : 424,
			//width : $('body').outerWidth(true)-340,
			fit: true,
			checkbox:true,
			rownumbers:true,
			singleSelect:false,
			pagination:false, 
			//pageSize : 20,
			//pageList : [10,20,30],
			idField:'entityId',
			sortName:'xingm',
			sortOrder:'asc',
			columns:[[
                {field:'personcode',title:'人员ID',align:"center",width:80,resizable:true,hidden:true},
                {field:'xingm',title:'姓名',align:"center",width:60,resizable:true},     
                {field:'zhiw',title:'职务',align:"left",width:300,resizable:true},
				{field:'zbdw',title:'主办单位',align:"center",width:80,resizable:true},
				{field:'cbjg',title:'承办机构',align:"center",width:80,resizable:true},
				{field:'bcmc',title:'班次名称',align:"center",width:80,resizable:true},
				{field:'qssj',title:'培训起始时间',align:"center",width:80},
				{field:'jssj',title:'培训结束时间',align:"center",width:80,resizable:true},
				{field:'xuez',title:'学制',align:"center",width:80,resizable:true},
				{field:'bclbCode',title:'班次类别编码',align:"center",width:80,resizable:true,hidden:true},
				{field:'checkType',title:'检验信息',align:"center",width:150,resizable:true,
					formatter: function(value, rowData, rowIndex) {
						if (value == 1) return '平台存在多个同名干部';
						else if (value == 2) return '平台不存在该干部';
						else return '';
					}
				},
				{field:'matchZw',title:'匹配的平台干部及其职务',align:"center",width:200,resizable:true},
				{field:'action',title:'',align:"center",width:80,
					formatter: function(value, rowData, rowIndex) {
						if (rowData.checkType == 1) return "<a href=\"javascript:void(0);\" onclick=\"impData.receive.loadSameName('"+rowData.xingm+"','"+rowData.entityId+"');\">手工匹配</a>";
						else return '';
					}
				}
			]],
			/*rowStyler:function(index,row){
				if(row.checkType == 1){
					return 'background-color:#FF0000;';
				}
			},*/
		    onSelect: function(rowIndex,rowData){
		    	
		    },
		    onUnselect: function(rowIndex,rowData){
		    	
		    },
		    onLoadSuccess:function(data){
		    	$(target).datagrid('unselectAll');
				var options = $(target).datagrid('options');
				$("#page").val(options.pageNumber);
				$("#rows").val(options.pageSize);
				
			}
		});
	}
}; 


function matchSameName(xingm,zhiw,personcode,entityId) {
	//var dataRow = $("#sn").datagrid("getSelected");
	/*var dataRow = $("#sameNameData").data("pageSelects");
	var data = [];
	if (dataRow) data = dataRow;*/
	//alert(dataRow.xingm);
	//var rowIndex= $('#mc').datagrid('getRowIndex',$('#mc').datagrid('getSelected'));
	var rows = $("#mc").datagrid('getData').rows;
	var length = rows.length;
	var rowIndex;
	for (var i = 0; i < length; i++) {
	      if (rows[i].entityId == entityId) {
	    	  rowIndex = i;
	        break;  
	    }  
	}
	$.ajax({
		url:System.rootPath+"/train/import-bc!matchSameName.action",
		data:{'xingm':xingm,'zhiw':zhiw},
		success:function(data){
		   $("#mc").datagrid('updateRow', 
			   { index:rowIndex, row:  {
				   matchZw:data.xingm+' '+data.zhiw,
				   personcode:personcode
				   
			   } 
		     }
		   );
		   //alert(dataRow.personcode);
		   getPersoncode(entityId);
		},
		complete :function(XMLHttpRequest, textStatus){
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.messager.alert("error",textStatus + errorThrown);
			
		}
	});
}

function getPersoncode(entityId){
	var rows = $("#mc").datagrid('getData').rows;
	var length = rows.length;
	var rowindex;
	for (var i = 0; i < length; i++) {
	      if (rows[i].entityId == entityId) {
	        rowindex = i;
	        break;  
	    }  
	}
	$.ajax({
		url:System.rootPath+"/train/import-bc!getOnlyPersoncode.action",
		type:'post',
		data:{'entityId':entityId,'personcode':rows[rowindex].personcode}
    });
}

impData.receive = {
	
    loadSameName : function(xingm,entityId){
    	var c = $("<div id='"+entityId+"'></div>").appendTo('body');
    	  
		c.load(System.rootPath+"/common/samename.jsp",function(){
			$("#sn").datagrid({
				checkbox:true,
				rownumbers:true,
				idField:'personcode',
				pagination:false,
				singleSelect:true,
				url:System.rootPath+"/train/import-bc!loadSameName.action",
				columns:[[
                          {field:'personcode',hidden:true,resizable:true},
                          {field:'ck',checkbox:true, align:'center',resizable:true},
				          {field:'xingm',title:'姓名',width:60,resizable:true},
				          {field:'zhiw',title:'职务',width:250,resizable:true},
						  {field:'fzai',title:'非在职',width:50,resizable:true, 
							formatter: function(value, rowData, rowIndex){
							if (value == '1') return '是'; else return '否';
						  }}
				          ]],
			    queryParams:{"xingm":xingm},
			    onSelect: function(rowIndex,rowData){
			    	matchSameName(rowData.xingm,rowData.zhiw,rowData.personcode,entityId);
			    	
			    },
			    onUnselect: function(rowIndex,rowData){
			    	
			    }
			});
		});
		
	},
	
	loadData : function(target,packagename){
		$(target).datagrid({
			url:System.rootPath+"/train/import-bc!loadData.action",
			pageNumber:1,
			queryParams:{"packagename":packagename}
		});
	},

	receivePerson : function(){
		$.ajax({
			url:System.rootPath+'/train/import-bc!receivePerson.action',
			type:'post',
			success:function(data){
				$.messager.alert('提示', '导入成功，请重新进行干部排序！');
				$('#mc').datagrid('loadData',{total:0,rows:[]}); 
			}
		}); 
	}
	
};