	/***********************/
	var isAddOrEdit = 1; //0Ϊ������1Ϊ�޸�
	/**
	 * ���ؽ�ɫ���ݣ��Ͳ�ѯ���ã�
	 */
	function loadRoleGridData(){
		var roleName = document.all('roleQName').value.replace(/%/gi,''); //��ɫ����
		var roleDesc = document.all('roleQDesc').value.replace(/%/gi,''); //��ɫ����
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
	 * �༭֮��Ĳ��������ɫ�����ظ����жϣ��粻�ظ��ͱ��棬���б����ɫ������
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
			if (rn == "") { //�༭��ɫ��������ʱ���ж��Ƿ�Ϊ��,Ϊ�վͲ��ñ���
				//��ɫ���Ʋ���Ϊ��
				record.set('status', '3');
				isSubmit = false;
			}
			else { //��Ϊ��
				if (record.get('roleid') != "") { //˵���Ǳ༭
					isAddOrEdit = 1;
					if (record.get('oldrolename') == rn) { //������ɫ���Ƿ������޸�
					//û�޸�
					}
					else 
						if (rn != "") {
							//Ψһ��У��
							var params = {};
							var sql = " select count(*) as total from sysrole where rolename='" + rn + "' ";
							params.querySQL = sql;
							params.sqlType = "SQL";
							var req = odin.commonQuery(params, odin.ajaxSuccessFunc, null, false, false);
							var data = odin.ext.decode(req.responseText).data.data;
							//alert(data[0].total);
							if (data[0].total != "0") {
								//˵�������Ѿ�����
								record.set('status', '1'); //status=1 ��ʾ�������ظ����޸�ʧ��
								isSubmit = false;
							}
						}
				}else{
					//����
					record.set('status', '4');
					isAddOrEdit = 0;
				}
			}

		}
		if(field=='roledesc'){ //
		 	isAddOrEdit = 1;
			if(record.get('roleid')==""){
				//��ɫ���Ʋ���Ϊ��
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
		//����ɹ�
		var grid = odin.ext.getCmp('rolegrid');
		grid.store.getAt(cueCell.rowIndex).set('status','2'); // 2 �����޸ĳɹ�
		if(isAddOrEdit==0){
			//alert(response.data);
			grid.store.getAt(cueCell.rowIndex).set('roleid',response.data);
		}	
	}
	/**
	 * ����״̬��Ϣ
	 * @param {Object} value
	 * @param {Object} params
	 * @param {Object} record
	 * @param {Object} rowIndex
	 * @param {Object} colIndex
	 * @param {Object} ds
	 */
	function renderStatus(value, params, record, rowIndex, colIndex, ds){
		if(value=='1'){
			return "<img src='../img/wrong.gif' title='��ɫ�������ظ����޸�ʧ�ܣ�'>";
		}else if(value=='2'){
			return "<img src='../img/right.gif' title='�޸Ľ�ɫ�ɹ���'>";
		}else if(value=='3'){
			return "<img src='../img/wrong.gif' title='��ɫ���Ʋ�����Ϊ�գ�'>";
		}else if(value=='4'){
			return "<img src='../img/right.gif' title='������ɫ�ɹ���'>";
		}else{
			return '';
		}
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
		return "<a href='#' onclick='showTipForDeleteRole(\""+value+"\")'>ɾ��</a>";
	}
	/**
	 * ����Ȩ�еĴ���
	 * @param {Object} value
	 * @param {Object} params
	 * @param {Object} record
	 * @param {Object} rowIndex
	 * @param {Object} colIndex
	 * @param {Object} ds
	 */
	function renderOpFun(value, params, record, rowIndex, colIndex, ds){
		return "<img src='../images/icon/icon_photodesk.gif'  onclick='openFunWin(\""+value+"\")' title='���˽�ɫ��Ȩ'>";
	}
	/**
	 * ��ָ��CA������
	 */
	function openFuncForCA(){
		odin.showWindowWithSrc('funcCAWindow',contextPath+'/sys/assignCAFuncAction.do');
	}
	/**
	 * ����Ȩ����
	 * @param {Object} roleid
	 */
	function openFunWin(roleid){
		//����Ȩ����
		cueRoleId = roleid;
		//odin.ext.getCmp('funcWindow').show(odin.ext.getCmp('funcWindow'));
		odin.showWindowWithSrc('funcWindow',contextPath+'/sys/assignRoleFuncAction.do');
	}
	/**
	 * ����һ�У�����༭
	 */
	function addRowForNewUser(){
		odin.addGridRowData('rolegrid',{roleid:'',roledesc:'',oldrolename:'',rolename:''});   
	}
	/**************************************/
	
	var cueRoleId  = "";//ɾ��ʱ�ĵ�ǰ��ɫid
	/**
	 * ɾ��������ɫǰ������
	 * @param {Object} userid
	 */
	function showTipForDeleteRole(roleid){
		cueRoleId = roleid;
		odin.confirm('��ȷ��Ҫɾ���ý�ɫ��',doDeleteRole);
	}
	function doDeleteRole(btn){
		if(btn=='ok'){
			//����ɾ������
			deleteRole(cueRoleId);
		}
	}
	/**
	 * ɾ��idΪ�˵Ľ�ɫ
	 * @param {Object} id
	 */
	function deleteRole(id){
		document.roleForm.method.value = 'deleteRole';
		document.roleForm.roleid.value = id;
		odin.submit(document.roleForm,deleteSucccess);
	}
	/**
	 * ɾ���ɹ������ʾ
	 * @param {Object} response
	 */
	function deleteSucccess(response){
		odin.alert(response.mainMessage);
		//ˢ�½�ɫ�������
		odin.ext.getCmp('rolegrid').store.reload();
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
	/**********************/
