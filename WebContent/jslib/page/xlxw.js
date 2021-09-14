var Xl = {
	updateRow:function updateRow(row,rowIndex){
			 var editors = $("#list").datagrid('getEditors',rowIndex);
			 row.cbbs = editors[0].target.val();
			 row.rxdate =  editors[2].target.val();
			 row.bysj =  editors[3].target.val();
			 row.school =  editors[4].target.val();
			 return row;
	}
};
var Xw = {
	updateRow:function updateRow(row,rowIndex){
		 var editors = $("#list").datagrid('getEditors',rowIndex);
		 row.cbbs = editors[0].target.val();
		 row.xwsysj =  editors[2].target.val();
		 row.xwsydw =  editors[3].target.val();
		 row.sxzymc =  editors[4].target.val();
		 return row;
	}	
};

var Xlxw={
	updateCbbs:function updateCbbs(containerId,id){
		var rowDatas = $(containerId).datagrid('getData');
	    var rows = rowDatas.rows;
	    var newRows = [];
	    $.each(rows,function(i,v){
		    if(v.id != id){
				v.cbbs = '0';
		    }
			newRows.push(v);
		});
		rowDatas.rows = newRows;
		$(containerId).data('datagrid').data = rowDatas;
	}
}