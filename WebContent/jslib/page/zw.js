var Zw = {
	updateRow:function(row,rowIndex){
		 var editors = $("#list").datagrid('getEditors',rowIndex);
		 row.pzrzsj =  editors[3].target.val();
		 row.pzrzwh =  editors[4].target.val();
		 row.dzwsx =  editors[5].target.val();
		 row.jtnsx =  editors[6].target.val();
		 return row;
	},
	getZwDes:function(personcode,callbackName){
		$.ajax({
			type: "POST",
			url: System.rootPath+'/zw/zw!getZw.action',
			data:{'personcode':personcode},
			success: function(json){
				if(json != ""){
					parent.zhiwCallback(json,$("#editIndex").val(),$("#controlId").val());
				}
			},
			error:function(){
				$.messager.alert("info","获取职务信息失败,请重试!");
			}
		});
	}
};

function ZwEntity(){
	this.id;
	this.personcode;
	this.rzjgmc;
	this.rzjgdm;
	this.rzjgdmshow;
	this.zwmc;
	this.zwsm;
	this.zj;
	this.zjCode;
	this.dzwsx;
	this.jtnsx;
	this.pzrzsj;
	this.pzrzwh;
	this.rzzt;
	this.rzztCode;
}