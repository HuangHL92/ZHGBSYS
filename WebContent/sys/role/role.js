	/***********************/
	var isAddOrEdit = 1; //0为新增，1为修改
	/**
	 * 加载角色数据（和查询共用）
	 */
	function loadRoleGridData(){
		var roleName = document.all('roleQName').value.replace(/%/gi,''); //角色名字
		var roleDesc = document.all('roleQDesc').value.replace(/%/gi,''); //角色描述
		var params = {};
		var sql = " select roleid,roledesc,creator,rolename,rolename  as oldrolename from sysrole where sysid ";
		if(sysId == ""){
			sql += " is null ";
		}else{
			sql += " ='"+sysId+"' ";
		}
		if(roleName!=""){
			sql += " and rolename like '%"+roleName+"%' ";
		}
		if(roleDesc!=""){
			sql += " and roledesc like '%"+roleDesc+"%' ";
		}

		sql += " order by roleid ";
		params.querySQL = sql;
		params.sqlType = "SQL";
		odin.loadPageGridWithQueryParams('rolegrid',params);
	}
	var cueCell = {};
	/**
	 * 编辑之后的操作，如角色名称重复的判断，如不重复就保存，还有保存角色描述等
	 * @param {Object} e
	 */
	function doAfterEdit(e){
		odin.afterEditForEditGrid(e);
		//--------------------------
		var grid = e.grid;
        var record = e.record;
        var field = e.field;
        var originalValue = e.originalValue;
        var value = e.value;
        var row = e.row;
        var column = e.column;
		cueCell.rowIndex = row;
		cueCell.colIndex = column;
		cueCell.field = field;
		var isSubmit = true;
		if(field=='rolename'){
			var rn = record.get('rolename');
			if (rn == "") { //编辑角色名称这列时先判断是否为空,为空就不用保存
				//角色名称不能为空
				record.set('status', '3');
				isSubmit = false;
			}
			else { //不为空
				if (record.get('roleid') != "") { //说明是编辑
					isAddOrEdit = 1;
					if (record.get('oldrolename') == rn) { //看看角色名是否做过修改
					//没修改
					}
					else 
						if (rn != "") {
							//唯一性校验
							var params = {};
							var sql = " select count(*) as total from sysrole where rolename='" + rn + "' ";
							params.querySQL = sql;
							params.sqlType = "SQL";
							var req = odin.commonQuery(params, odin.ajaxSuccessFunc, null, false, false);
							var data = odin.ext.decode(req.responseText).data.data;
							//alert(data[0].total);
							if (data[0].total != "0") {
								//说明名字已经存在
								record.set('status', '1'); //status=1 表示名称有重复，修改失败
								isSubmit = false;
							}
						}
				}else{
					//新增
					record.set('status', '4');
					isAddOrEdit = 0;
				}
			}

		}
		if(field=='roledesc'){ //
		 	isAddOrEdit = 1;
			if(record.get('roleid')==""){
				//角色名称不能为空
				record.set('status', '3');
				isSubmit = false;
			}
		}
		if(isSubmit){
			var fm = document.roleForm;
			fm.method.value = 'addOrEditRole';
			fm.roleid.value = record.get('roleid');
			fm.roledesc.value = record.get('roledesc');
			fm.rolename.value = record.get('rolename');
			odin.submit(fm,addOrEditSuccess,null);
		}
		
	}
	/**
	 * 
	 * @param {Object} response
	 */
	function addOrEditSuccess(response){
		//保存成功
		var grid = odin.ext.getCmp('rolegrid');
		grid.store.getAt(cueCell.rowIndex).set('status','2'); // 2 代表修改成功
		if(isAddOrEdit==0){
			//alert(response.data);
			grid.store.getAt(cueCell.rowIndex).set('roleid',response.data);
		}	
	}
	/**
	 * 处理状态信息
	 * @param {Object} value
	 * @param {Object} params
	 * @param {Object} record
	 * @param {Object} rowIndex
	 * @param {Object} colIndex
	 * @param {Object} ds
	 */
	function renderStatus(value, params, record, rowIndex, colIndex, ds){
		if(value=='1'){
			return "<img src='../img/wrong.gif' title='角色名称有重复，修改失败！'>";
		}else if(value=='2'){
			return "<img src='../img/right.gif' title='修改角色成功！'>";
		}else if(value=='3'){
			return "<img src='../img/wrong.gif' title='角色名称不允许为空！'>";
		}else if(value=='4'){
			return "<img src='../img/right.gif' title='新增角色成功！'>";
		}else{
			return '';
		}
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
		return "<a href='#' onclick='showTipForDeleteRole(\""+value+"\")'>删除</a>";
	}
	/**
	 * 对授权列的处理
	 * @param {Object} value
	 * @param {Object} params
	 * @param {Object} record
	 * @param {Object} rowIndex
	 * @param {Object} colIndex
	 * @param {Object} ds
	 */
	function renderOpFun(value, params, record, rowIndex, colIndex, ds){
		return "<img src='../images/icon/icon_photodesk.gif'  onclick='openFunWin(\""+value+"\")' title='给此角色授权'>";
	}
	/**
	 * 打开指定CA功能树
	 */
	function openFuncForCA(){
		odin.showWindowWithSrc('funcCAWindow',contextPath+'/sys/assignCAFuncAction.do');
	}
	/**
	 * 打开授权窗口
	 * @param {Object} roleid
	 */
	function openFunWin(roleid){
		//打开授权窗口
		cueRoleId = roleid;
		//odin.ext.getCmp('funcWindow').show(odin.ext.getCmp('funcWindow'));
		odin.showWindowWithSrc('funcWindow',contextPath+'/sys/assignRoleFuncAction.do');
	}
	/**
	 * 增加一行，方便编辑
	 */
	function addRowForNewUser(){
		odin.addGridRowData('rolegrid',{roleid:'',roledesc:'',oldrolename:'',rolename:''});   
	}
	/**************************************/
	
	var cueRoleId  = "";//删除时的当前角色id
	/**
	 * 删除单个角色前的提醒
	 * @param {Object} userid
	 */
	function showTipForDeleteRole(roleid){
		cueRoleId = roleid;
		odin.confirm('您确定要删除该角色吗？',doDeleteRole);
	}
	function doDeleteRole(btn){
		if(btn=='ok'){
			//调用删除函数
			deleteRole(cueRoleId);
		}
	}
	/**
	 * 删除id为此的角色
	 * @param {Object} id
	 */
	function deleteRole(id){
		document.roleForm.method.value = 'deleteRole';
		document.roleForm.roleid.value = id;
		odin.submit(document.roleForm,deleteSucccess);
	}
	/**
	 * 删除成功后的提示
	 * @param {Object} response
	 */
	function deleteSucccess(response){
		odin.alert(response.mainMessage);
		//刷新角色表的数据
		odin.ext.getCmp('rolegrid').store.reload();
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
	/**********************/
