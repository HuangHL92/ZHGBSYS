/**********************/
/**
 * ҳ���ʼ����
 */
function doInitWin(){
	var fm = document.userForm;
	if(parent.isAddOrEdit==1){
		//�޸�
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
		fm.username.readOnly = true; //��ֹ�༭�û�ID
		fm.userid.value = data.userid;
		fm.sysid.value = data.sysid;
		//�����ɫ
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
		//�������ݽ�ɫ
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
 * ������Ŀ������� �����û�id
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
 * ���ؽ�ɫ����
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
 * �������ݽ�ɫ����
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
 * У���û�����Ψһ��
 * @param {Object} value
 */
function validateNameOnly(value){
	if (parent.isAddOrEdit == 0) { //�����Ž����û����ļ��飨�޸�ʱ�û������ɱ༭��
		//alert(value);
		value = value.toLowerCase();
		var params = {};
		var sql = " select count(*) as total from sysuser where lower(username)='" + value + "' ";
		params.querySQL = sql;
		params.sqlType = "SQL";
		var req = odin.commonQuery(params, odin.ajaxSuccessFunc, null, false, false);
		var data = odin.ext.decode(req.responseText).data.data;
		if (data[0].total != "0") {
			//˵�����û����Ѿ�����
			odin.cueCheckObj = document.all('username');
			odin.cueCheckObj.value = '';
			odin.info("���û����Ѿ����ڣ�", odin.doFocus);
		}
		else {
			odin.cueCheckObj = document.all('username');
			odin.cueCheckObj.value = value;			
		}
	}
}
/**
 * �����û�
 */
function addUser(){
	//�����ɫ��ѡ��
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
	if (parent.isAddOrEdit == 0) { //ֻ������ʱ�Ŷ��������md5���ܣ�����Ļ���Ϊ���뱾��Ͳ��ɱ༭ ����Ҳ�Ͳ���Ҫ��ʲô�ˡ�
		document.userForm.passwd.value = hex_md5(document.userForm.passwd.value);
	}
	odin.submit(document.userForm,addSuccess,null);
}
/**
 * ���ӻ��޸��û��ɹ���Ĵ���
 * @param {Object} response
 */
function addSuccess(response){
	odin.cueCheckObj = document.userForm.username;
	//�������������رյ�ǰ����
	clearForm(document.userForm);
	parent.odin.ext.getCmp('usergrid').store.reload(); //ˢ���û��������
	if(parent.isAddOrEdit==1){
		//������޸ģ����޸���󣬹رմ���
		parent.odin.ext.getCmp('userWindow').hide();
		parent.odin.alert('�޸��û���Ϣ'+response.mainMessage);
	}else{
		odin.alert(response.mainMessage,odin.doFocus);
	}
}
/**********************/
