	/***********************/
	var isAddOrEdit = 0; //0为新增，1为修改
	
	/**
	 * 重置用户密码
	 * 2008-07-22
	 */
	function resetUserPasswd(){
		var selectObj = Ext.getCmp('usergrid').getSelectionModel().getSelections();
		if(selectObj.length>1){
			odin.alert('重置密码时不允许选多个用户！');
		}else if(selectObj.length==1){
			var userid = selectObj[0].get('userid');
			//采用同步模式
			var req = odin.Ajax.request(contextPath+'/sys/userAction.do?method=resetUserPassword',{'userid':userid},odin.ajaxSuccessFunc,odin.ajaxSuccessFunc,false,true);
			var response = odin.ext.decode(req.responseText);
			if(response.messageCode=='0'){
				//重置密码成功
				odin.alert('重置用户密码成功！');
			}else{
				odin.alert('重置用户密码失败！');
			}
		}else{
			odin.alert('请选择要进行密码重置的用户！');
		}
	}
	
	/**
	 * 加载用户数据（和查询共用）
	 */
	function loadUserGridData(){
		var username = document.all('username').value.replace(/%/gi,''); //用户id
		var userloginname = document.all('userloginname').value.replace(/%/gi,''); //用户姓名
		var opname = document.all('opname').value.replace(/%/gi,''); //操作员名字
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
	 * 对操作列的处理
	 * @param {Object} value
	 * @param {Object} params
	 * @param {Object} record
	 * @param {Object} rowIndex
	 * @param {Object} colIndex
	 * @param {Object} ds
	 */
	function renderOp(value, params, record, rowIndex, colIndex, ds){
		return "<a href='#' onclick='doEditUser(\""+value+"\")'>修改</a>&nbsp;&nbsp;/&nbsp;&nbsp;<a href='#' onclick='showTipForDeleteOne(\""+value+"\")'>删除</a>"
	}
	var editUserId = "";
	/**
	 * 打开修改用户的窗口，做修改处理
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
	 * 对是否有效列的处理
	 * @param {Object} value
	 * @param {Object} params
	 * @param {Object} record
	 * @param {Object} rowIndex
	 * @param {Object} colIndex
	 * @param {Object} ds
	 */
	function renderUseful(value, params, record, rowIndex, colIndex, ds){
		if(value=="0"){
			return "<img src='../img/wrong.gif' alt='无效' />";
		}else if(value=="1"){
			return "<img src='../img/right.gif' alt='有效' />";
		}
	}
	/**
	 * 删除单个用户前的提醒
	 * @param {Object} userid
	 */
	function showTipForDeleteOne(userid){
		cueUserId = userid;
		odin.confirm('您确定要删除该用户吗？',doDeleteUser);
	}
	var cueUserId = ""; //当点修改或删除时的当前用户id
	function doDeleteUser(btn){
		if(btn=='ok'){
			//调用删除函数
			deleteUser(cueUserId);
		}
	}
	/**
	 * 删除id为此的用户
	 * @param {Object} id
	 */
	function deleteUser(id){
		document.userForm.userId.value = id;
		odin.submit(document.userForm,deleteSucccess);
	}
	/**
	 * 删除成功后的提示
	 * @param {Object} response
	 */
	function deleteSucccess(response){
		odin.alert(response.mainMessage);
		//刷新用户表的数据
		odin.ext.getCmp('usergrid').store.reload();
	}
	/**
	 * 删除选中的所有用户(批量删除)
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
			odin.info('请先选好人员再进行删除！');
		}
	}
	/**
	 * 打开新增用户窗口
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
