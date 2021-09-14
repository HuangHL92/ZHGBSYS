	/***********************/
	var isAddOrEdit = 0; //0Ϊ������1Ϊ�޸�
	
	/**
	 * �����û�����
	 * 2008-07-22
	 */
	function resetUserPasswd(){
		var selectObj = Ext.getCmp('usergrid').getSelectionModel().getSelections();
		if(selectObj.length>1){
			odin.alert('��������ʱ������ѡ����û���');
		}else if(selectObj.length==1){
			var userid = selectObj[0].get('userid');
			//����ͬ��ģʽ
			var req = odin.Ajax.request(contextPath+'/sys/userAction.do?method=resetUserPassword',{'userid':userid},odin.ajaxSuccessFunc,odin.ajaxSuccessFunc,false,true);
			var response = odin.ext.decode(req.responseText);
			if(response.messageCode=='0'){
				//��������ɹ�
				odin.alert('�����û�����ɹ���');
			}else{
				odin.alert('�����û�����ʧ�ܣ�');
			}
		}else{
			odin.alert('��ѡ��Ҫ�����������õ��û���');
		}
	}
	
	/**
	 * �����û����ݣ��Ͳ�ѯ���ã�
	 */
	function loadUserGridData(){
		var username = document.all('username').value.replace(/%/gi,''); //�û�id
		var userloginname = document.all('userloginname').value.replace(/%/gi,''); //�û�����
		var opname = document.all('opname').value.replace(/%/gi,''); //����Ա����
		var params = {};
		var sql = " select * from sysuser where sysid ";
		if(sysId == ""){
			sql += " is null ";
		}else{
			sql += " ='"+sysId+"' ";
		}
		if(username!=""){
			sql += " and operatorname like '%"+username+"%' ";
		}
		if(userloginname!=""){
			sql += " and username like '%"+userloginname+"%' ";
		}
		if(opname!=""){
			sql += " and creator like '%"+opname+"%' ";
		}
		sql += " order by userid ";
		params.querySQL = sql;
		params.sqlType = "SQL";
		odin.loadPageGridWithQueryParams('usergrid',params);
	}
	/**
	 * �Բ����еĴ���
	 * @param {Object} value
	 * @param {Object} params
	 * @param {Object} record
	 * @param {Object} rowIndex
	 * @param {Object} colIndex
	 * @param {Object} ds
	 */
	function renderOp(value, params, record, rowIndex, colIndex, ds){
		return "<a href='#' onclick='doEditUser(\""+value+"\")'>�޸�</a>&nbsp;&nbsp;/&nbsp;&nbsp;<a href='#' onclick='showTipForDeleteOne(\""+value+"\")'>ɾ��</a>"
	}
	var editUserId = "";
	/**
	 * ���޸��û��Ĵ��ڣ����޸Ĵ���
	 * @param {Object} userid
	 */
	function doEditUser(userid){
		isAddOrEdit = 1;
		editUserId = userid;
		try{
			document.frames('iframe_userWindow').doInitWin();
		}catch(e){
			//alert(e.message);
		}
		odin.ext.getCmp('userWindow').show(odin.ext.getCmp('userWindow'));
		try{
			document.frames('iframe_userWindow').userForm.username.focus();
			document.frames('iframe_userWindow').userForm.operatorname.focus();
		}catch(e){}		
	}
	/**
	 * ���Ƿ���Ч�еĴ���
	 * @param {Object} value
	 * @param {Object} params
	 * @param {Object} record
	 * @param {Object} rowIndex
	 * @param {Object} colIndex
	 * @param {Object} ds
	 */
	function renderUseful(value, params, record, rowIndex, colIndex, ds){
		if(value=="0"){
			return "<img src='../img/wrong.gif' alt='��Ч' />";
		}else if(value=="1"){
			return "<img src='../img/right.gif' alt='��Ч' />";
		}
	}
	/**
	 * ɾ�������û�ǰ������
	 * @param {Object} userid
	 */
	function showTipForDeleteOne(userid){
		cueUserId = userid;
		odin.confirm('��ȷ��Ҫɾ�����û���',doDeleteUser);
	}
	var cueUserId = ""; //�����޸Ļ�ɾ��ʱ�ĵ�ǰ�û�id
	function doDeleteUser(btn){
		if(btn=='ok'){
			//����ɾ������
			deleteUser(cueUserId);
		}
	}
	/**
	 * ɾ��idΪ�˵��û�
	 * @param {Object} id
	 */
	function deleteUser(id){
		document.userForm.userId.value = id;
		odin.submit(document.userForm,deleteSucccess);
	}
	/**
	 * ɾ���ɹ������ʾ
	 * @param {Object} response
	 */
	function deleteSucccess(response){
		odin.alert(response.mainMessage);
		//ˢ���û��������
		odin.ext.getCmp('usergrid').store.reload();
	}
	/**
	 * ɾ��ѡ�е������û�(����ɾ��)
	 */
	function deleteManyUser(){
		var selectObj = Ext.getCmp('usergrid').getSelectionModel().getSelections();
		if (selectObj.length > 0) {
			var id = "";
			for (i = 0; i < selectObj.length; i++) {
				if (i == 0) {
					id += selectObj[i].get('userid');
				}
				else {
					id += "," + selectObj[i].get('userid');
				}
				showTipForDeleteOne(id);
			}
			
		}
		else {
			odin.info('����ѡ����Ա�ٽ���ɾ����');
		}
	}
	/**
	 * �������û�����
	 */
	function openAddUserWin(){
		isAddOrEdit = 0;
		try{
			document.frames('iframe_userWindow').doInitWin();
		}catch(e){
			//
		}
		odin.ext.getCmp('userWindow').show(odin.ext.getCmp('userWindow'));
		try{
			document.frames('iframe_userWindow').userForm.username.focus();
		}catch(e){}	
	}
	/**********************/
