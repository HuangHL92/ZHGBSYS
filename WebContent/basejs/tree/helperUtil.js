//�ؼ� �ı�Ϊreadonly �������
document.onkeydown=function() {
	if (event.keyCode == 8) { 
		if ((document.activeElement.type == "text"||document.activeElement.type == "textarea")&&document.activeElement.readOnly == true) {
			
			var id = document.activeElement.id;
			var index = id.indexOf('_combo');
			var index2 = id.indexOf('comboxArea_');
			if(index!=-1){
				var realid =  id.substring(0,index);
				document.getElementById(realid).value='';
				document.getElementById(id).value='';
				onkeydownfn(realid);
				return false;
			}else if(index2!=-1){
				var realid =  id.substring(11,id.length);
				document.getElementById(realid).value='';
				document.getElementById(id).value='';
				onkeydownfn(realid);
				return false;
			}
			
		} 
	}
};
//���ؼ� ɾ��ʱ ִ�еĺ������ھ���ҳ������д��
function onkeydownfn(id){}

Ext.apply(Ext.form.VTypes, {
				//�������ֵ,������2�ַ�
				maxlength : function(str, field) {
					var cArr = str.match(/[^\x00-\xff]/ig);
					if (str.length + (cArr == null ? 0 : cArr.length) > field.maxLength) {
						this.maxlengthText = '���ȳ���,���������������󳤶�Ϊ'+field.maxLength+",���������������ַ�";
						return false;
					} else {
						return true;
					}
				},
				maxlengthText : '���ȹ���',
				//�û���
				"username":function(_v){
					return true;
				},
				'usernameText': "�û�������ĸ�����ּ��»�����ɣ���",
				'usernameMask' :/[a-zA-Z0-9,_]/,
				// ����
				"num" : function(_v) {
					if (/^\d+$/.test(_v)) {
						return true;
					} else
						return false;
				},
				'numText' : '��������ֻ����Ϊ0-9������',
				'numMask' : /[0-9,-]/i,
				"num2" : function(_v) {return true;
					if (/^[0-9]+(\.[0-9]+)?$/.test(_v)) {
						return true;
					} else
						return false;
				},
				'num2Text' : '��������ֻ����Ϊ0-9������',
				'num2Mask' : /[0-9]/i,
				
				// ����
				"number" : function(_v) {
					if (/^-?\d+$/.test(_v)) {
						return true;
					} else
						return false;
				},
				'numberText' : '��������ֻ����Ϊ����',
				//'numberMask' : /[0-9\-]/i,
				// ����
				"age" : function(_v) {
					if (/^\d+$/.test(_v)) {
						var _age = parseInt(_v,10);
						if (_age < 200)
							return true;
					} else
						return false;
				},
				'ageText' : '�����ʽ��������ʽ���磺20',
				'ageMask' : /[0-9]/i,
				// ������֤
				"repassword" : function(_v, field) {
					if (field.confirmTo) {
						var psw = Ext.get(field.confirmTo);
						if(psw){
							return (_v == psw.getValue());
						}
					}
					return true;
				},
				"repasswordText" : "������������벻һ�£���",
				"repasswordMask" : /[a-zA-Z0-9,!,@,#,$,%,^,&,*,?,_,~]/,
				// ��������
				"postcode" : function(_v) {
					return /^[0-9]\d{5}$/.test(_v);
				},
				"postcodeText" : "��������Ŀ���������������ʽ�����磺226001",
				"postcodeMask" : /[0-9]/i,

				// IP��ַ��֤
				"ip" : function(_v) {
					return /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
							.test(_v);

				},
				"ipText" : "��������Ŀ������IP��ַ��ʽ�����磺222.192.42.12",
				"ipMask" : /[0-9\.]/i,
				// �̶��绰��С��ͨ
				"telephone" : function(_v) {
					return /(^\d{3}\-\d{7,8}$)|(^\d{4}\-\d{7,8}$)|(^\d{3}\d{7,8}$)|(^\d{4}\d{7,8}$)|(^\d{7,8}$)/
							.test(_v);
				},
				"telephoneText" : "��������Ŀ�����ǵ绰�����ʽ�����磺0513-89500414,051389500414,89500414",
				"telephoneMask" : /[0-9\-]/i,
				// �ֻ�
				"mobile" : function(_v) {
					return /^1[35][0-9]\d{8}$/.test(_v);
				},
				"mobileText" : "��������Ŀ�������ֻ������ʽ�����磺13485135075",
				"mobileMask" : /[0-9]/i,
				// ���֤
				"IDCard" : function(_v) {
					// return
					// /(^[0-9]{17}([0-9]|[Xx])$)|(^[0-9]{17}$)/.test(_v);
					var area = {
						11 : "����",
						12 : "���",
						13 : "�ӱ�",
						14 : "ɽ��",
						15 : "���ɹ�",
						21 : "����",
						22 : "����",
						23 : "������",
						31 : "�Ϻ�",
						32 : "����",
						33 : "�㽭",
						34 : "����",
						35 : "����",
						36 : "����",
						37 : "ɽ��",
						41 : "����",
						42 : "����",
						43 : "����",
						44 : "�㶫",
						45 : "����",
						46 : "����",
						50 : "����",
						51 : "�Ĵ�",
						52 : "����",
						53 : "����",
						54 : "����",
						61 : "����",
						62 : "����",
						63 : "�ຣ",
						64 : "����",
						65 : "�½�",
						71 : "̨��",
						81 : "���",
						82 : "����",
						91 : "����"
					}
					var Y, JYM;
					var S, M;
					var idcard_array = new Array();
					idcard_array = _v.split("");
					// ��������
					if (area[parseInt(_v.substr(0, 2),10)] == null) {
						this.IDCardText = "���֤��������Ƿ�!!,��ʽ����:32";
						return false;
					}
					// ��ݺ���λ������ʽ����
					switch (_v.length) {
						case 15 :
							if ((parseInt(_v.substr(6, 2),10) + 1900) % 4 == 0
									|| ((parseInt(_v.substr(6, 2),10) + 1900)
											% 100 == 0 && (parseInt(_v.substr(
											6, 2),10) + 1900)
											% 4 == 0)) {
								ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;// ���Գ������ڵĺϷ���
							} else {
								ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;// ���Գ������ڵĺϷ���
							}
							if (ereg.test(_v))
								return true;
							else {
								this.IDCardText = "���֤����������ڳ�����Χ,��ʽ����:19860817";
								return false;
							}
							break;
						case 18 :
							// 18λ��ݺ�����
							// �������ڵĺϷ��Լ��
							// ��������:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
							// ƽ������:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
							if (parseInt(_v.substr(6, 4),10) % 4 == 0
									|| (parseInt(_v.substr(6, 4),10) % 100 == 0 && parseInt(_v
											.substr(6, 4),10)
											% 4 == 0)) {
								ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;// ����������ڵĺϷ���������ʽ
							} else {
								ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;// ƽ��������ڵĺϷ���������ʽ
							}
							if (ereg.test(_v)) {// ���Գ������ڵĺϷ���
								// ����У��λ
								S = (parseInt(idcard_array[0],10) + parseInt(idcard_array[10],10))
										* 7
										+ (parseInt(idcard_array[1],10) + parseInt(idcard_array[11],10))
										* 9
										+ (parseInt(idcard_array[2],10) + parseInt(idcard_array[12],10))
										* 10
										+ (parseInt(idcard_array[3],10) + parseInt(idcard_array[13],10))
										* 5
										+ (parseInt(idcard_array[4],10) + parseInt(idcard_array[14],10))
										* 8
										+ (parseInt(idcard_array[5],10) + parseInt(idcard_array[15],10))
										* 4
										+ (parseInt(idcard_array[6],10) + parseInt(idcard_array[16],10))
										* 2
										+ parseInt(idcard_array[7],10)
										* 1
										+ parseInt(idcard_array[8],10)
										* 6
										+ parseInt(idcard_array[9],10) * 3;
								Y = S % 11;
								M = "F";
								JYM = "10X98765432";
								M = JYM.substr(Y, 1);// �ж�У��λ
								// alert(idcard_array[17]);
								if (M == idcard_array[17]) {
									return true; // ���ID��У��λ
								} else {
									this.IDCardText = "���֤����ĩλУ��λУ�����,��ȷ��У����Ϊ��"+M;
									return false;
								}
							} else {
								this.IDCardText = "���֤����������ڳ�����Χ,��ʽ����:19860817";
								return false;
							}
							break;
						default :
							this.IDCardText = "���֤����λ������,Ӧ��Ϊ15λ����18λ";
							return false;
							break;
					}
				},
				"IDCardText" : "��������Ŀ���������֤�����ʽ�����磺32082919860817201X",
				"IDCardMask" : /[0-9xX]/i,
				
				// ����У�� ���ܴ��ڵ�ǰʱ��
				"dateBT" : function(_v) {
					if (/^-?\d+$/.test(_v)) {
						var rtn = dateValidateBeforeTady(_v);
						if(rtn===true){
							return true;
						}else{
							this.dateBTText=rtn;
							return false;
						}
					} else{
						this.dateBTText='��������ֻ����Ϊ����';
						return false;
					}
				},
				'dateBTText' : '��������ֻ����Ϊ����',
				
				
				// ����У�� ���ܴ��ڵ�ǰʱ��
				"dateBTY" : function(_v) {
					if (/^-?\d+$/.test(_v)) {
						var rtn = dateValidateBeforeTadyY(_v);
						if(rtn===true){
							return true;
						}else{
							this.dateBTYText=rtn;
							return false;
						}
					} else{
						this.dateBTYText='��������ֻ����Ϊ����';
						return false;
					}
				},
				'dateBTYText' : '��������ֻ����Ϊ����',
				
				
				"dynamic" : function (vtype) {
					this.invalidText = Ext.form.VTypes[vtype + 'Text'];
					var rval = Ext.form.VTypes[vtype].call(this, this
							.getValue());
					if (rval) {
						return true;
					} else {
						this.invalidText = this[vtype + 'Text'];
						return false;
					}
				}
			});

function newWin(cfg){
	var cfg_ = {id:'',title:'',width:600,height:380,codetype:'',modal : false,src:'<%=request.getContextPath()%>/Index.jsp',maximized:false,
				maximizable:false,parentWinObj:null,
				listeners:{ 
					"drag":function(){ 
								return false;
						   }
 						} 						
 				}
	Ext.applyIf(cfg, cfg_);
	var myExt = null;
	if(cfg.parentWinObj!==null){
		myExt = cfg.parentWinObj.Ext;
	}else{
		myExt = Ext;
	}
	return new myExt.Window({
		html : '<iframe width="100%" frameborder="0" id="iframe_'+cfg.id+'" name="iframe_'+cfg.id+'" height="100%" src="'+cfg.src+'"></iframe>',
		title : cfg.title,
		layout : 'fit',
		width : cfg.width,
		height : cfg.height,
		closeAction : 'hide',
		closable : true,
		codetype:cfg.codetype,
		minimizable : false,
		maximizable : cfg.maximizable,
		modal : cfg.modal,
		maximized:cfg.maximized,
		id : cfg.id,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		listeners:cfg.listeners
	});
}

/**
 * ���gridѡ����(����,��һ��)
 *
 * @param {string}
 *            gridId
 * @return {Array/false} ��ѡ��ʱ����ѡ����,��ѡ��ʱ����false
 */
function getGridSelected(gridId) {
	var sm = Ext.getCmp(gridId).getSelectionModel();
	if (sm.hasSelection()) {
		return sm.getSelected();
	} else {
		return false;
	}
}

/**
 * ���������� disable
 *
 * @param {string}
 *           
 * @return 
 */
function selecteDisable(selectId) {
	var combo = Ext.getCmp(selectId+'_combo');//
	if($('#'+selectId+'_combo.bgclor').length==0){//�����Ϣ��û���޸�Ȩ�� ��ִ��enable
		combo.setValue('');
		document.getElementById(selectId).value='';
		combo.disable();
	}
}
/**
 * ���������� enable
 *
 * @param {string}
 *           
 * @return 
 */
function selecteEnable(selectId,defaultValue) {
	var combo = Ext.getCmp(selectId+'_combo');//
	if($('#'+selectId+'_combo.bgclor').length==0){//�����Ϣ��û���޸�Ȩ�� ��ִ��enable
		if(defaultValue&&document.getElementById(selectId).value==''){
			odin.setSelectValue(selectId, defaultValue);
		}
		combo.enable();
	}
	
}

/**
 * �����ı� disable
 *
 * @param {string}
 *           
 * @return 
 */
function textDisable(selectId) {
	var combo = Ext.getCmp(selectId);//
	combo.setValue('');
	combo.disable();
}
/**
 * �����ı��� enable
 *
 * @param {string}
 *           
 * @return 
 */
function textEnable(selectId) {
	var combo = Ext.getCmp(selectId);//
	combo.enable();
}

/**
 * ������ؼ� disable
 *
 * @param {string}
 *           
 * @return 
 */
function selecteWinDisable(selectId) {
	var combo = Ext.getCmp(selectId+'_combo');//
	if($('#'+selectId+'_combo.bgclor').length==0){
		combo.setValue('');
		document.getElementById(selectId).value='';
		combo.disable();
		var img = Ext.query("#"+selectId+"_combo+img")[0];
	    img.onclick=null;
	}
}
/**
 * ������ؼ� enable
 *
 * @param {string}
 *           
 * @return 
 */
function selecteWinEnable(selectId) {
	var combo = Ext.getCmp(selectId+'_combo');//
	if($('#'+selectId+'_combo.bgclor').length==0){
		combo.enable();
		var img = Ext.query("#"+selectId+"_combo+img")[0];
		img.onclick=eval('openDiseaseInfoCommonQuery'+selectId);
	}
}
/**
 * ����У�� 6λ��8λ���������ڸ�ʽ�����Դ��ڵ�ǰ����
 * @param {} value
 * @return {Boolean}
 */
function dateValidate(value){
//var d1= new Date(2006,0,12);
//return d1.getFullYear()+','+d1.getMonth()+','+d1.getDate();




	var length = value.length;
	if(length==0){
		return true;
	}else if(length!=6&&length!=8){
		return '���ڸ�ʽ����ȷ��ֻ������6λ��8λ����Ч���ڣ���ȷ��ʽΪ��200808 �� 20080804';
	}
	
	var year = value.substring(0,4);
	if(1900>(parseInt(year,10))){
		return '��ݱ������1900��';
	}
	var month = value.substring(4,6);
	var day;
	if(length==8){
		day = value.substring(6,8);
	}else{
		day = '01';
	}
	year = parseInt(year,10); month = parseInt(month,10); day = parseInt(day,10);
	if(0==month){
		return '"��" ��ʽ����ȷ';
	}
	if(0==day){
		return '"��" ��ʽ����ȷ';
	}
	var d= new Date(year, month-1,day); 
	if(d.getFullYear()!=year)return '���ڸ�ʽ����ȷ��ֻ������6λ��8λ����Ч���ڣ���ȷ��ʽΪ��200808 �� 20080804';  
	if(d.getMonth()!=month-1)return '���ڸ�ʽ����ȷ��ֻ������6λ��8λ����Ч���ڣ���ȷ��ʽΪ��200808 �� 20080804';  
	if(d.getDate()!=day)return '���ڸ�ʽ����ȷ��ֻ������6λ��8λ����Ч���ڣ���ȷ��ʽΪ��200808 �� 20080804'; 
	return true;
}
/**
 * ����У�� 6λ��8λ���������ڸ�ʽ�������Դ��ڵ�ǰ����
 * @param {} value
 * @return {Boolean}
 */
function dateValidateBeforeTady(value){
	var rtn = dateValidate(value);
	if(rtn===true){
		date1=new Date();
		var length = value.length;
		var year = value.substring(0,4);
		var month = value.substring(4,6);
		var day;
		if(length==8){
			day = value.substring(6,8);
		}else{
			day = '01';
		}
		year = parseInt(year,10); month = parseInt(month,10); day = parseInt(day,10);
		date2=new Date(year, month-1,day)
		if(Date.parse(date2)>Date.parse(date1)){
			return '�������ڵ�ǰ����';
		}
		return true;
	}else{
		return rtn;
	}
	
}

/**
 * ����У�� 4λ6λ��8λ���������ڸ�ʽ�������Դ��ڵ�ǰ����
 * @param {} value
 * @return {Boolean}
 */
function dateValidateBeforeTadyY(value){
	var length = value.length;
	if(length==0){
		return true;
	}else if(length==4){
		value = value + "01";
	}
	var rtn = dateValidate(value);
	if(rtn===true){
		date1=new Date();
		var length = value.length;
		var year = value.substring(0,4);
		var month = value.substring(4,6);
		var day;
		if(length==8){
			day = value.substring(6,8);
		}else{
			day = '01';
		}
		year = parseInt(year,10); month = parseInt(month,10); day = parseInt(day,10);
		date2=new Date(year, month-1,day)
		if(Date.parse(date2)>Date.parse(date1)){
			return '�������ڵ�ǰ����';
		}
		return true;
	}else{
		return rtn;
	}
	
}
// ��������
function postcode(_v) {
	if(_v=='')return true;
	if (/^[0-9]\d{5}$/.test(_v)) {
		return true;
	} else {
		return "��������Ŀ���������������ʽ�����磺226001";
	}
}


var $h1 = {
	'confirm4btn' : function(title, msg, width, fn) {
			Ext.Msg.buttonText.cancel='��ֹ����';
			Ext.Msg.show({
						title : title,
						msg : msg,
						width : width,
						buttons : Ext.MessageBox.YESNOCANCEL,
						fn : fn
					});
		}
	
}

var $h =  {
		'alert' : function(title, msg, fn,width, scope) {
			Ext.MessageBox.show({
						title : title,
						msg : msg,
						buttons : Ext.MessageBox.OK,
						fn : fn,
						width : width,
						scope : scope
					});
		},
		'confirm' : function(title, msg, width, fn) {
			Ext.Msg.show({
						title : title,
						msg : msg,
						width : width,
						buttons : Ext.MessageBox.OKCANCEL,
						fn : fn,
						icon: this.QUESTION,  
      					minWidth: this.minWidth  
					});
		},
		
		'confirm3btn' : function(title, msg, width, fn) {
			Ext.Msg.show({
						title : title,
						msg : msg,
						width : width,
						buttons : Ext.MessageBox.YESNOCANCEL,
						
						fn : fn
					});
		},
		
		'IDCard' : function(_v){	
			if(_v==''){
				return true;
			}
			if(!Ext.form.VTypes.IDCard(_v)){
				return Ext.form.VTypes.IDCardText;
			}else{
				return true;
			}
		},
		'date' : function(_v,isY){	//isY ����ֻ����
			if(isY){
				return dateValidateBeforeTadyY(_v);
			}else{
				return dateValidateBeforeTady(_v);
			}
		},
		'email' : function(_v) {
			if(_v=='')return true;
			var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
			if (!pattern.test(_v)) {
				return "��������ȷ�������ַ";
			}
			return true;
		},
		'selectHide' : function(id){
			Ext.getCmp(id+'_combo').hide();
			document.getElementById(id+'SpanId').style.visibility="hidden";
		},
		'textHide' : function(id){
			Ext.getCmp(id).hide();
			document.getElementById(id+'SpanId').style.visibility="hidden";
		},
		'selectShow' : function(id){
			Ext.getCmp(id+'_combo').show();
			document.getElementById(id+'SpanId').style.visibility="visible";
		},
		'textShow' : function(id){
			Ext.getCmp(id).show();
			document.getElementById(id+'SpanId').style.visibility="visible";
		},
		
		'selecteDisable' : function(selectId){
			selecteDisable(selectId);
		},
		
		'selecteEnable' : function(selectId,defaultValue){
			selecteEnable(selectId,defaultValue);
		},
		
		'textDisable' : function(id){
			textDisable(id);
		},
		
		'textEnable' : function(id){
			textEnable(id);
		},
		
		'selecteWinDisable' : function(id){
			selecteWinDisable(id);
		},
		
		'selecteWinEnable' : function(id){
			selecteWinEnable(id);
		},
		
		'selectGridRow' : function(gridid, index){
			var grid = Ext.getCmp(gridid);
			var len = grid.getStore().data.length;
			if( len > index ){//Ĭ��ѡ���һ�����ݡ�
				grid.getSelectionModel().selectRow(index,true);
				radow.doEvent(gridid + '.rowclick');
			}
		},
		
		'dateDisable' : function(id){
			Ext.getCmp(id+'_1').setValue('');
	 		document.getElementById(id).value='';
	 		Ext.getCmp(id+'_1').setDisabled(true);
		},
		'dateEnable' : function(id){
			Ext.getCmp(id+'_1').setDisabled(false);
		},
		//����ָ�� ������ʾ���ֶ�
		'spFeildAll' : {'a01':['a0101','a0104','a0107','a0111','a0114','a0117','a0128','a0134','a0140','a0160','a0163','a0165','a0184','a0187a','a0192','a0192a','a0195','a0196','a1701','a14z101','a15z101','a0192d','a0120','a0121','a0122','a0141','a3921','a0144'],'a02':['a0201a','a0201b','a0201d','a0215a','a0219','a0221','a0222','a0223','a0225','a0229','a0243','a0247','a0251b','a0255','a0265','a0288','a0221a','a0291','a0292'],'a08':['a0801a','a0901a','a0804','a0807','a0904','a0814','a0824','a0837'],'a11':['a1101','a1104','a1107','a1111'],'a29':['a2911','a2921a','a2921b','a2921c','a2921d','a2947','a2949','a2970','a2970a','a2970b','a2970c','a2947a'],'a36':['a3601','a3604a','a3607','a3611','a3627'],'a57':['a5714'],'b01':['b0127','b0183','b0185','b0188','b0189','b0190','b0191a','b0192','b0193','b0194','b0227','b0232','b0233','b0238','b0239'],'a30':['a3001'],'a06':[],'a14':[]},
		//������ʾ����
		'applyFontConfig' : function(spFeild) {
			var cls = 'fontConfig';
			for (var i_ = 0; i_ < spFeild.length; i_++) {
				if (document.getElementById(spFeild[i_] + 'SpanId_s')) {
					$('#' + spFeild[i_] + 'SpanId_s').addClass(cls);
				} else if (document.getElementById(spFeild[i_] + 'SpanId')) {
					$('#' + spFeild[i_] + 'SpanId').addClass(cls);
				}
	
			}
		},
		//��Ȩ�޵�button���� �û�
		'disabledButtons':{'a01':['saveid'],'orthers':['save1','TrainingInfoAddBtn','delete'],'a02':['UpBtn','DownBtn','saveSortBtn','sortUseTimeS','WorkUnitsAddBtn','delete1','save','UpdateTitleBtn','personGRIDSORT'],'a06':['professSkillAddBtn','delete','save'],'a08':['degreesAddBtn','save'],'a14':['RewardPunishAddBtn','delete','save','append','addAll'],'a15':['save','delete']},
		'setDisabled':function(pageButtons){
			for (var i_ = 0; i_ < pageButtons.length; i_++) {
				if (Ext.getCmp(pageButtons[i_])) {
					Ext.getCmp(pageButtons[i_]).setDisabled(true);
				} 
			}
		},
		//��Ϣ��Ȩ�� ����� 1�ɻ��о����ı������3������4�ɻ��о��е����ؼ���5�������ڣ� 0��ͨ������������
		//��ͨҳ�棺 6��ͨ����� 7�����ؼ� 8��������� 3������
		'inputType' : {a0101:1, a0104:3, a0107:1, a0117:3, a0111:4, a0114:4,
						a0140:1,a0134:1,a0128:1,a0196:5,a0187a:1,qrzxl:6,qrzxlxx:6,qrzxw:6,qrzxwxx:6,zzxl:6,zzxlxx:6,zzxw:6,zzxwxx:6,a0192a:6,a1701:6,
						a0184:6,a0165:3,a0160:3,a0121:3,
						//����������һҳ
						a3604a:3,a3601:1,a3607:1,a3611:1,a3627:3,a14z101:6,a15z101:6,a0180:6,
						//���������ڶ�ҳ
						a0192:6,a0222:3,a0255:3,a0201b:7,a0201d:3,a0215a:7,a0216a:6,a0201e:3,a0221:7,a0221a:7,a0229:6,a0251:3,a0219:3,a0243:8,a0247:7,a0288:8,a0245:6,
						//���Ϲ�����λ��ְ��
						a0837:3,a0801b:7,a0901b:7,a0811:6,a0801a:6,a0901a:6,a0814:6,a0827:7,a0804:8,a0807:8,a0824:6,a0904:8,
						//������ѧ��ѧλ
						a0601:7,a0602:6,a0607:3,a0611:6,a0604:8,a0196:6,
						//������רҵ����ְ��
						a1404b:7,a1404a:6,a1415:7,a1414:3,a1428:7,a1411a:6,a1407:8,a1424:8,
						//�����ǽ�����Ϣ
						a1527:6,a1521:3,a1517:7,//a0191:checkbox
						//��������ȿ�����Ϣ
						a2921a:6,a2941:6,a2944:6,a2907:8,a2911:7,a2921b:7,a2947:8,a2970:3,a2950:3,a2949:8,a2947a:6,a2947b:6,a2951:3,a2921c:3,a2921d:3,
						//�����ǽ������
						a1131:6,a1101:3,a1114:6,a1121a:6,a1107:8,a1111:8,a1107c:6,a1108:6,a1107a:6,a1107b:6,a1127:3,a1104:3,a1151:3,
						//��������ѵ��Ϣ
						a3101:3,a3104:8,a3137:6,a3107:7,a3118:6,a3117a:6,a3140:7,a3141:7,
						//����������
						a3001:7,a3007a:6,a3004:8,a3034:7,
						//�������˳�����
						a5304:6,a5315:6,a5317:6,a5319:6,a5321:8,a5323:8,a5327:6,
						//������������
						a3701:6,a3707a:6,a3707c:6,a3707b:6,a3708:6,a3707e:6,a3711:6,a3714:6,
						//������סַͨѶ
						a0141:3,a0144:8,a3921:3,a3927:3,
						//�������뵳ʱ��
						a0122:7,a0120:7,a0192d:3//������Ϣ
						},
		
		'fieldsDisabled':function(formfields){
			var formobj = null;
			for(var i=0; i<formfields.length; i++){
				var formfield = formfields[i];
				if($h.inputType[formfield]==1){//�ɻ��о����ı������
					if('a3601'==formfield||'a3607'==formfield||'a3611'==formfield){
						var tff = formfield;
						for(var m=1;m<=30;m++){
							formfield=tff+'_'+m;
							if(formobj = document.getElementById("wrapdiv_"+formfield)){
								if(window.attachEvent){
								    formobj.detachEvent('onclick',eval('wrapdiv_'+formfield+'onclick') );            
								}
								if(window.addEventListener){
								 	formobj.removeEventListener('click',eval('wrapdiv_'+formfield+'onclick'),false ); 	
								}
								$(formobj).addClass('bgclor');
							}
						}
					}else{
						if(formobj = document.getElementById("wrapdiv_"+formfield)){
							if(window.attachEvent){
							    formobj.detachEvent('onclick',eval('wrapdiv_'+formfield+'onclick') );            
							}
							if(window.addEventListener){
							 	formobj.removeEventListener('click',eval('wrapdiv_'+formfield+'onclick'),false ); 	
							}
							$(formobj).addClass('bgclor');
						}
					}
					
					
				}else if($h.inputType[formfield]==3){//������
					if('a3604a'==formfield||'a3627'==formfield){
						var tff = formfield;
						for(var m=1;m<=30;m++){
							formfield=tff+'_'+m;
							if(formobj = Ext.getCmp(formfield+'_combo')){
								formobj.disable();
								$('#'+formfield+'_combo').parent().removeClass('x-item-disabled');
								$('#'+formfield+'_combo').addClass('bgclor');
							}
						}
					}else{
						if(formobj = Ext.getCmp(formfield+'_combo')){
							formobj.disable();
							$('#'+formfield+'_combo').parent().removeClass('x-item-disabled');
							$('#'+formfield+'_combo').addClass('bgclor');
						}
					}
				}else if($h.inputType[formfield]==4){//�ɻ��о��е�������
					if(formobj = document.getElementById("wrapdiv_"+formfield)){
						if(window.attachEvent){
					    	formobj.detachEvent('onclick',eval('wrapdiv_'+formfield+'onclick') );            
						}
						if(window.addEventListener){
						 	formobj.removeEventListener('click',eval('wrapdiv_'+formfield+'onclick'),false ); 	
						}
						$(formobj).addClass('bgclor');
						var img = document.getElementById(formfield+"_img");
						img.onclick=null;//ȥ��ͼƬ����������ڿؼ��¼�
						img.style.cursor='default';
					}
				}else if($h.inputType[formfield]==5){//��������
					if(formobj = document.getElementById(formfield)){
						formobj.ondblclick=null;
						formobj.onkeypress=null;
						$(formobj).addClass('bgclor');
					}
				}else if($h.inputType[formfield]==0){//�������ͨ�����
					if(formobj =document.getElementById(formfield)){
						formobj.readOnly=true;
						$(formobj).addClass('bgclor');
					}
				}else if($h.inputType[formfield]==6){//��ͨ�����
					if(formobj = Ext.getCmp(formfield)){
						formobj.disable();
						$('#'+formfield).addClass('bgclor');
					}
				}else if($h.inputType[formfield]==7){//�����ؼ�
					if(formobj = Ext.getCmp(formfield+'_combo')){
						formobj.disable();
						var img = Ext.query("#"+formfield+"_combo+img")[0];
    					img.onclick=null;
    					$('#'+formfield+'_combo').parent().removeClass('x-item-disabled');
						$('#'+formfield+'_combo').addClass('bgclor');
					}
				}else if($h.inputType[formfield]==8){//���������
					if(formobj = document.getElementById(formfield+'_1')){
						formobj.readOnly=true;
						$(formobj).addClass('bgclor');
					}
				}
				formobj = null;
			}
		}
		
	};


// ���������ʽ onblur
function blurDate_bj(hideid,ischeck,isblur) 
{
	var id = hideid + "_1";
	var disObj = Ext.getCmp(id);
	var val = disObj.getValue()+'';
	if(val==''){
		disObj.validator = function(){return true;};
		if(isblur){
			document.getElementById(hideid).value = val;
		}
		Ext.getCmp(id).validate();
		return;
	}
	var rtn;
	if(ischeck){
		rtn = dateValidate(val);
	}else{
		rtn = dateValidateBeforeTady(val);
	}
	
	if(rtn!==true){
		disObj.validator = function(){return rtn;};
		//����У��
		Ext.getCmp(id).validate();
		return;
	}else{
		disObj.validator = function(){return true;};
	}
	if(isblur){
		document.getElementById(hideid).value = val;
		Ext.getCmp(id).setValue(val.substr(0,4)+"."+val.substr(4,2));;
	}
	
}


//�۽��ľ������
function restoreDate(hid) {
	var id = hid + "_1";
	var disObj = Ext.getCmp(id);
	if(disObj.isValid()){
		var hval = document.getElementById(hid).value;
		if (hval!=null && hval!="") {
			Ext.getCmp(id).setValue(hval);
			var obj = document.getElementById(id);
			var len = hval.length;
            if (document.selection) {
                var sel = obj.createTextRange();
                sel.moveStart('character', len);
                sel.collapse();
                sel.select();
            } else if (typeof obj.selectionStart == 'number'
            && typeof obj.selectionEnd == 'number') {
                obj.selectionStart = obj.selectionEnd = len;
            }
		}
	}else{
	
	}
	

}








Ext.override(Ext.tree.TreeNodeUI, {
	onDblClick : function(e) {
		e.preventDefault();
        if(this.disabled){
            return;
        }
        if(!this.animating && this.node.hasChildNodes() && !this.node.attributes.dblclick){
            this.node.toggle();
        }
        if(this.node.disabled){//�ڵ�disabled ���ٷ������¼�
            return;
        }
        if(this.checkbox){
            //this.toggleCheck();
        }
        if(this.node.attributes.dblclick){
        	eval(this.node.attributes.dblclick);//��Ա��ѯ������ѯ�ĳ�˫����ѯ
        }
		this.fireEvent("dblclick", this.node, e);
	}
});

function getAgeRender(value){
	var returnAge;
	if (value==""||value==null||typeof(value)=="undefined") {
		return returnAge;
	}else{
	var birthYear = value.toString().substring(0,4);
	var birthMonth = value.toString().substring(4,6);
	var birthDay = value.toString().substring(6,8);
	if (birthDay==""||birthDay==null||typeof(birthDay)=="undefined") {
		birthDay = "01";
	}
	d = new Date();
	var nowYear = d.getFullYear();
	var nowMonth = d.getMonth()+1;
	var nowDay = d.getDate();
	if (nowYear == birthYear) {
		returnAge = 0; //ͬ�귵��0��
	}else{
		var ageDiff = nowYear - birthYear; //��ֻ��
		if (ageDiff > 0) {
			if (nowMonth == birthMonth) {
				var dayDiff = nowDay - birthDay;//��֮��
				if (dayDiff < 0) {
					returnAge = ageDiff - 1;
				}else{
					returnAge = ageDiff;
				}
			}else{
				var monthDiff = nowMonth - birthMonth;//��֮��
				if (monthDiff < 0) {
					returnAge = ageDiff - 1;
				}else{
					returnAge = ageDiff;
				}
			}
		}else{
			returnAge = -1;//�������ڴ��� ���ڽ���
		}
	}
	return returnAge;
}
}


