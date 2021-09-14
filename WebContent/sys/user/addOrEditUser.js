/**********************/
/**
 * 页面初始操作
 */
function doInitWin(){
	var fm = document.userForm;
	if(parent.isAddOrEdit==1){
		//修改
		var userid = parent.editUserId;
		fm.passwd.readOnly = true;
		var params = {};
		params.querySQL = "select * from sysuser where userid='"+userid+"'";
		params.sqlType = "SQL";
		var req = odin.commonQuery(params,odin.ajaxSuccessFunc,null,false,false);
		var data = odin.ext.decode(req.responseText).data.data[0];
		fm.username.value = data.username;
		fm.operatorname.value = data.operatorname;
		fm.passwd.value = data.passwd;
		odin.setSelectValue('useful',data.useful);
		//odin.setSelectValue('dept',data.dept);
		fm.dept.value = data.dept;;
		fm.description.value = data.description;
		fm.username.readOnly = true; //禁止编辑用户ID
		fm.userid.value = data.userid;
		fm.sysid.value = data.sysid;
		//处理角色
		params.querySQL = "select * from sysact where userid='"+userid+"'";
		req = odin.commonQuery(params,odin.ajaxSuccessFunc,null,false,false);
		data = odin.ext.decode(req.responseText).data.data;
		var grid = odin.ext.getCmp('roleGrid');
		var selectRow = new Array();
		var row = -1;
		for(i=0;i<data.length;i++){
			var j = 0;
			for(;j<grid.store.getCount();j++){
				var rs = grid.store.getAt(j);
				if(rs.get('roleid')==data[i].roleid){
					selectRow[(++row)] = j;//grid.getSelectionModel().selectRow(j);
					//grid.getSelectionModel().selectRow(j);
				}
			}
		}
		grid.getSelectionModel().selectRows(selectRow);
		//处理数据角色
		params.querySQL = "select * from sysudp where userid='"+userid+"'";
		req = odin.commonQuery(params,odin.ajaxSuccessFunc,null,false,false);
		data = odin.ext.decode(req.responseText).data.data;
		grid = odin.ext.getCmp('dataRoleGrid');
		selectRow = new Array();
		row = -1;
		for(i=0;i<data.length;i++){
			var j = 0;
			for(;j<grid.store.getCount();j++){
				var rs = grid.store.getAt(j);
				if(rs.get('roleid')==data[i].popedomid){
					selectRow[(++row)] = j;//grid.getSelectionModel().selectRow(j);
					//grid.getSelectionModel().selectRow(j);
				}
			}
		}
		grid.getSelectionModel().selectRows(selectRow);
	}else{
		clearForm(fm);
		fm.userid.value = '';
		fm.username.readOnly = false; 
		fm.passwd.readOnly = false;
	}
}
/**
 * 清除表单的可输入项 除了用户id
 * @param {Object} fm
 */
function clearForm(fm){
	fm.username.value = '';
	fm.operatorname.value = '';
	fm.passwd.value = '';
	odin.setSelectValue('useful','1');
	fm.dept.value = '';
	fm.description.value = '';
	odin.ext.getCmp('roleGrid').getSelectionModel().clearSelections();
	odin.ext.getCmp('dataRoleGrid').getSelectionModel().clearSelections();
}
/**
 * 加载角色数据
 */
function loadRoleData(){
	var params = {};
	var sql = " select * from sysrole ";
	if(parent.sysId!=""){
		sql += " where sysid = '"+parent.sysId+"'";
	}else{
		sql += " where sysid is null ";
	}
	params.querySQL = sql;
	params.sqlType = "SQL";
	var req = odin.commonQuery(params,odin.ajaxSuccessFunc,null,false,false);
	var data = odin.ext.decode(req.responseText).data.data;
	if(data.length>0){
		var rsArray = new Array(data.length);
		for(i=0;i<data.length;i++){
			rsArray[i] = {};
			rsArray[i].roleid = data[i].roleid;
			rsArray[i].rolename = data[i].rolename;
			rsArray[i] = new odin.ext.data.Record(rsArray[i]);
		}
		odin.ext.getCmp('roleGrid').store.add(rsArray);
		rsArray = null;
	}
}
/**
 * 加载数据角色数据
 */
function loadDataRoleData(){
	var params = {};
	var sql = " select * from sysdatapopedom ";
	params.querySQL = sql;
	params.sqlType = "SQL";
	var req = odin.commonQuery(params,odin.ajaxSuccessFunc,null,false,false);
	var data = odin.ext.decode(req.responseText).data.data;
	if(data.length>0){
		var rsArray = new Array(data.length);
		for(i=0;i<data.length;i++){
			rsArray[i] = {};
			rsArray[i].roleid = data[i].popedomid;
			rsArray[i].rolename = data[i].popedomname;
			rsArray[i] = new odin.ext.data.Record(rsArray[i]);
		}
		odin.ext.getCmp('dataRoleGrid').store.add(rsArray);
		rsArray = null;
	}
}
/**
 * 校验用户名的唯一性
 * @param {Object} value
 */
function validateNameOnly(value){
	if (parent.isAddOrEdit == 0) { //新增才进行用户名的检验（修改时用户名不可编辑）
		//alert(value);
		value = value.toLowerCase();
		var params = {};
		var sql = " select count(*) as total from sysuser where lower(username)='" + value + "' ";
		params.querySQL = sql;
		params.sqlType = "SQL";
		var req = odin.commonQuery(params, odin.ajaxSuccessFunc, null, false, false);
		var data = odin.ext.decode(req.responseText).data.data;
		if (data[0].total != "0") {
			//说明此用户名已经存在
			odin.cueCheckObj = document.all('username');
			odin.cueCheckObj.value = '';
			odin.info("该用户名已经存在！", odin.doFocus);
		}
		else {
			odin.cueCheckObj = document.all('username');
			odin.cueCheckObj.value = value;			
		}
	}
}
/**
 * 增加用户
 */
function addUser(){
	//处理角色的选择
	var selRs = odin.ext.getCmp('roleGrid').getSelectionModel().getSelections();
	var roleIds = "";
	for(i=0;i<selRs.length;i++){
		if(roleIds==""){
			roleIds = selRs[i].get('roleid');
		}else{
			roleIds += "," + selRs[i].get('roleid');
		}
	}
	document.userForm.roleids.value = roleIds;
	var selDataRs = odin.ext.getCmp('dataRoleGrid').getSelectionModel().getSelections();
	var dataRoleIds = "";
	for(i=0;i<selDataRs.length;i++){
		if(dataRoleIds==""){
			dataRoleIds = selDataRs[i].get('roleid');
		}else{
			dataRoleIds += "," + selDataRs[i].get('roleid');
		}
	}
	document.userForm.dataroleids.value = dataRoleIds;
	document.userForm.username.value = document.userForm.username.value.toLowerCase();
	if (parent.isAddOrEdit == 0) { //只有新增时才对密码进行md5加密，否则的话因为密码本身就不可编辑 这里也就不需要做什么了。
		document.userForm.passwd.value = hex_md5(document.userForm.passwd.value);
	}
	odin.submit(document.userForm,addSuccess,null);
}
/**
 * 增加或修改用户成功后的处理
 * @param {Object} response
 */
function addSuccess(response){
	odin.cueCheckObj = document.userForm.username;
	//清屏处理，但不关闭当前窗口
	clearForm(document.userForm);
	parent.odin.ext.getCmp('usergrid').store.reload(); //刷新用户表的数据
	if(parent.isAddOrEdit==1){
		//如果是修改，则修改完后，关闭窗口
		parent.odin.ext.getCmp('userWindow').hide();
		parent.odin.alert('修改用户信息'+response.mainMessage);
	}else{
		odin.alert(response.mainMessage,odin.doFocus);
	}
}
/**********************/
