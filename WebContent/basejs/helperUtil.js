/* ͳһconsole������Ч���� */
window.console = window.console || (function () {
    var c = {}; c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile
        = c.clear = c.exception = c.trace = c.assert = function () { };
    return c;
})();
// ��ȡ��ַ������
function GetQueryString(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}

//�ж�val�Ƿ�Ϊnull
var feildIsNull = function (field) {
    return typeof field === 'undefined' || field == null || field.toString().trim() == '';
}
/*
window.showModalDialog = function (url, arg, feature) {

    var opFeature = feature.split(";");
    var featuresArray = new Array()
    if (document.all) {
        for (var i = 0; i < opFeature.length - 1; i++) {
            var f = opFeature[i].split("=");
            featuresArray[f[0]] = f[1];
        }
    }
    else {

        for (var i = 0; i < opFeature.length - 1; i++) {
            var f = opFeature[i].split(":");
            // featuresArray[f[0].toString().trim().toLowerCase()] = f[1].toString().trim();
        }
    }
    console.info(feature);
    var h = "670px", w = "1060", l = "100px", t = "100px", r = "yes", c = "yes", s = "no";
    if (featuresArray["dialogheight"]) h = featuresArray["dialogheight"];
    if (featuresArray["dialogwidth"]) w = featuresArray["dialogwidth"];
    if (featuresArray["dialogleft"]) l = featuresArray["dialogleft"];
    if (featuresArray["dialogtop"]) t = featuresArray["dialogtop"];
    if (featuresArray["resizable"]) r = featuresArray["resizable"];
    if (featuresArray["center"]) c = featuresArray["center"];
    if (featuresArray["status"]) s = featuresArray["status"];
    var modelFeature = "height = " + h + ",width = " + w + ",left=" + l + ",top=" + t + ",model=yes,alwaysRaised=yes" + ",resizable= " + r + ",celter=" + c + ",status=" + s;

    var model = window.open(url, "", modelFeature, null);

    model.dialogArguments = arg;
}
*/
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
			return false;
		}
		if(document.activeElement.type != "password" && document.activeElement.type != "text"
			&& document.activeElement.type != "textarea"){
			return false;
		}

	}else if(event.keyCode == 27){
        return false;
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
							if (ereg.test(_v)){
								//�ж����֤�����ڶ�λ�Ƿ���Ա�һ��
								var sex = _v.substr((_v.length-2), 1);
								var a0104 = document.getElementById('a0104').value;		//�Ա�

								var sexA0104 = sex%2;		//ȡ����

								if(sexA0104 == 0){
									sexA0104 = 2;
								}

								if(sexA0104 != a0104){
									this.IDCardText = "���֤���뵹���ڶ�λ�����Ա�һ��";
									return false;
								}

								return true;
							}
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
								ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;// ����������ڵĺϷ���������ʽ
							} else {
								ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;// ƽ��������ڵĺϷ���������ʽ
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
								if (M == idcard_array[17].toUpperCase()) {
									//�ж����֤�����ڶ�λ�Ƿ���Ա�һ��
									var sex = _v.substr((_v.length-2), 1);
									var a0104 = document.getElementById('a0104').value;		//�Ա�

									var sexA0104 = sex%2;		//ȡ����

									if(sexA0104 == 0){
										sexA0104 = 2;
									}

									if(sexA0104 != a0104){
										this.IDCardText = "���֤���뵹���ڶ�λ�����Ա�һ��";
										return false;
									}


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
	var cfg_ = {id:'',title:'',width:600,height:380,codetype:'',modal : false,src:'Index.jsp',maximized:false,
				maximizable:false,parentWinObj:null,thisWin:null,closeAction:'hide',param:'',draggable:true,
				modal:true,collapsed:false,collapsible:false,titleCollapse:false,
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
	var window_cfg={
			html : '<iframe width="100%" scrolling="no" frameborder="0" id="iframe_'+cfg.id+'" name="iframe_'+cfg.id+'" height="100%" src="'+cfg.src+'"></iframe>',
			title : cfg.title,
			layout : 'fit',
			width : cfg.width,
			height : cfg.height,
			closeAction : cfg.closeAction,
			closable : true,
			codetype:cfg.codetype,
			minimizable : false,
			maximizable : false,
			modal : cfg.modal,
			maximized:cfg.maximized,
			id : cfg.id,
			param : cfg.param,
			thisWin: cfg.thisWin,
			collapsed:cfg.collapsed,
			collapsible:cfg.collapsible,
			bodyStyle : 'background-color:#FFFFFF',
			plain : false,
			border  : false,
			bodyBorder   : false,
			titleCollapse:cfg.titleCollapse,
			draggable:cfg.draggable,
			listeners:cfg.listeners
		};
	Ext.apply(window_cfg, cfg);
	window_cfg.border=false;
	return new myExt.Window(window_cfg);
}

function newWinMin(cfg){
	var cfg_ = {id:'',title:'',width:600,height:380,codetype:'',src:'Index.jsp',
				parentWinObj:null,thisWin:null,param:'',closable:true,closeAction:'hide',
				modal:false,collapsed:true,collapsible:true,titleCollapse:false,
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
	var window_cfg={
			html : '<iframe width="100%" frameborder="0" id="iframe_'+cfg.id+'" name="iframe_'+cfg.id+'" height="100%" src="'+cfg.src+'"></iframe>',
			title : cfg.title,
			layout : 'fit',
			width : cfg.width,
			height : cfg.height,
			closeAction : cfg.closeAction,
			closable : true,
			codetype:cfg.codetype,
			modal : false,
			id : cfg.id,
			param : cfg.param,
			thisWin: cfg.thisWin,
			collapsed:true,
			collapsible:true,
			bodyStyle : 'background-color:#FFFFFF',
			plain : true,
			titleCollapse:cfg.titleCollapse,
			listeners:cfg.listeners
		};
	Ext.apply(window_cfg, cfg);
	return new myExt.Window(window_cfg);
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
	if(1800>(parseInt(year,10))){
		return '��ݱ������1800��';
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
			Ext.Msg.buttonText.cancel='ȡ������';
			Ext.Msg.show({
						title : title,
						msg : msg,
						width : width,
						buttons : Ext.MessageBox.YESNOCANCEL,
						fn : fn
					});
	},
	'confirm' : function(title, msg, width, fn) {
		Ext.Msg.buttonText.ok='��������';
		Ext.Msg.buttonText.cancel='ȡ������';
		Ext.Msg.show({
					title : title,
					msg : msg,
					width : width,
					buttons : Ext.MessageBox.OKCANCEL,
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
		'progress' : function(title, msg, progressText,width){
			return Ext.Msg.show({
                title : title,
                msg : msg,
                buttons: false,
                width: width,
                progress:true,
                closable:false,
                minWidth: this.minProgressWidth,
                progressText: progressText
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
		'dateFTime' : function(_v,isY){	//isY ����ֻ����
			if(isY){
				return dateValidateBeforeTadyYF(_v);
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
		//'spFeildAll' : {'a01':['a0101','a0104','a0107','a0111','a0114','a0117','a0128','a0134','a0140','a0160','a0163','a0165','a0184','a0187a','a0192','a0192a','a0195','a0196','a1701','a14z101','a15z101','a0192d','a0120','a0121','a0122','a0141','a3921','a0144'],'a02':['a0201a','a0201b','a0201d','a0215a','a0219','a0221','a0222','a0223','a0225','a0229','a0243','a0247','a0251b','a0255','a0265','a0288','a0221a','a0291','a0292'],'a08':['a0801a','a0901a','a0804','a0807','a0904','a0814','a0824','a0837'],'a11':['a1101','a1104','a1107','a1111'],'a29':['a2911','a2921a','a2921b','a2921c','a2921d','a2947','a2949','a2970','a2970a','a2970b','a2970c','a2947a'],'a36':['a3601','a3604a','a3607','a3611','a3627'],'a57':['a5714'],'b01':['b0127','b0183','b0185','b0188','b0189','b0190','b0191a','b0192','b0193','b0194','b0227','b0232','b0233','b0238','b0239'],'a30':['a3001'],'a06':[],'a14':[]},

    'spFeildAll' : {'a01':['a0197','a0221','a0192e','a0101','a0104','a0107','a0111','a0114','a0117','a0128','a0134','a0140','a0160','a0163','a0165','a0184','a0187a','a0192','a0192a','a0195','a0196','a1701','a14z101','a15z101','a0192d','a0120','a0121','a0122','a0141','a3921','a0144'],'a02':['a0201e','a0201a','a0201b','a0201d','a0215a','a0216a','a0219','a0221','a0222','a0223','a0225','a0243','a0247','a0251b','a0251bf','a0255','a0265','a0288','a0221a','a0291','a0292'],'a08':['a0801a','a0901a','a0814','a0824','a0837'],'a11':[],'a29':['a2947','a2949','a2947a'],'a36':['a3601','a3604a','a3607','a3611','a3627','a3684'],'a57':['a5714'],'b01':['b0127','b0183','b0185','b0188','b0189','b0190','b0191a','b0192','b0193','b0194','b0227','b0232','b0233','b0238','b0239'],'a30':['a3001'],'a06':[],'a14':[]},
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
		'disabledButtons':{'a01':['saveid'],'orthers':['save1','TrainingInfoAddBtn','delete'],'a02':['UpBtn','DownBtn','saveSortBtn','sortUseTimeS','WorkUnitsAddBtn','delete1','save','UpdateTitleBtn','personGRIDSORT'],'a06':['professSkillAddBtn','delete','save'],'a08':['degreesAddBtn','save'],'a14':['RewardPunishAddBtn','delete','save','append','addAll'],'a15':['save','delete','AssessmentInfoAddBtn'],'a29':['save'],'a30':['save'],'a53':['save'],'xzwcc':['save1','TrainingInfoAddBtn'],'xzj':['save1','TrainingInfoAddBtn']},
		'setDisabled':function(pageButtons){
			for (var i_ = 0; i_ < pageButtons.length; i_++) {
				if (Ext.getCmp(pageButtons[i_])) {
					Ext.getCmp(pageButtons[i_]).setDisabled(true);
				}
			}
		},
    //��Ϣ��Ȩ�� ����� 1�ɻ��о����ı������3������4�ɻ��о��е����ؼ���5�������ڣ� 0��ͨ������������
    //��ͨҳ�棺 6��ͨ����� 7�����ؼ� 8��������� 3������  9checkbox  10radio  11comboxArea�汾�ĵ�������  12rmbSelect������
    'inputType' : {a0101:13, a0104:12, a0107:13, a0117:12, a0111:14, a0114:14,
        a0140:13,a0134:13,a0128:12,a0196:13,a0187a:13,qrzxl:0,qrzxlxx:0,qrzxw:0,qrzxwxx:0,zzxl:0,zzxlxx:0,zzxw:0,zzxwxx:0,a0192a:0,a08os:0,a1701:15,
        a0184:0,a0165:12,a0160:12,a0121:12,a0163:11,
        a0192c:8,a0192e:11,a0195:11,a0115a:11,a2949:8,
        //����������һҳ
        a3604a:3,a3601:1,a3607:1,a3611:1,a3627:3,a14z101:0,a15z101:0,a0180:0,
        //���������ڶ�ҳ
        a0192:6,a0222:3,a0255:10,a0201b:7,a0201d:3,a0215a:6,a0216a:6,a0201e:3,a0221:11,a0221a:7,a0229:6,a0251:3,a0219:3,a0243:8,a0247:7,a0288:8,a0245:6,
        a0201a:6,a0265:8,a0267:6,a0219:9,a0201d:9,a0251b:9,a0279:9,a0197:9,a0272:0,
        //���Ϲ�����λ��ְ��
        a0837:3,a0801b:7,a0901b:7,a0811:6,a0801a:6,a0901a:6,a0814:6,a0827:7,a0804:8,a0807:8,a0824:6,a0904:8,
        //������ѧ��ѧλ
        a0601:7,a0602:6,a0607:3,a0611:6,a0604:8,
        //������רҵ����ְ��
        A14_a1404b:7,A14_a1404a:6,A14_a1415:7,A14_a1414:3,A14_a1428:7,A14_a1411a:6,A14_a1407:8,A14_a1424:8,
        //�����ǽ�����Ϣ
        A15_a1521:3,A15_a1517:7,//a0191:checkbox
        //��������ȿ�����Ϣ
        A29_a2921a:6,A29_a2941:6,A29_a2907:8,A29_a2911:7,A29_a2944:7,A29_a2947a:6,/*,a2921b:7,a2947:8,a2970:3,a2950:3,a2949:8,a2947a:6,a2947b:6,a2951:3,a2921c:3,a2921d:3,*/
        //�����ǽ������
        a1131:6,a1101:3,a1114:6,a1121a:6,a1107:8,a1111:8,a1107c:6,a1108:6,a1107a:6,a1107b:6,a1127:3,a1104:3,a1151:3,
        //��������ѵ��Ϣ
        a3101:3,a3104:8,a3137:6,a3107:7,a3118:6,a3117a:6,a3140:7,a3141:7,
        //����������
        /*a3001:7,a3007a:6,a3004:8,a3034:7,*/
        A30_a3001:7,A30_a3004:8,A30_a3034:6,A30_a3954a:8,A30_a3954b:6,A30_a3007a:6,A30_a3038:6,
        //�������˳�����
        a5304:6,a5315:6,a5317:6,a5319:6,a5321:8,a5323:8,a5327:6,
        //������������
        A37_a3701:6,A37_a3707a:6,A37_a3707b:6,A37_a3707c:6,A37_a3711:6,A37_a3721:6,
        /*a3701:6,a3707a:6,a3707c:6,a3707b:6,a3708:6,a3707e:6,a3711:6,a3714:6,*/
        //������סַͨѶ
        a0141:3,a0144:8,a3921:3,a3927:3,
        //�������뵳ʱ��
        a99z101:9,a99z102:8,a99z103:9,a99z104:8,
        //�����ǲ�����Ϣ�� 2017-11-29
        A80_a29314:3,A80_a03033:8,A80_a29321:3,A80_a29337:7,A80_a29324b:7,A80_a29324a:6,A80_a29327b:7,A80_a29327a:6,A80_a39061:3,A80_a39064:3,A80_a39067:3,A80_a39071:3,A80_a44031:3,A80_a39084:6,A80_a44027:3,A80_a39077:6,A80_a03011:6,A80_a03021:6,A80_a03095:6,A80_a03027:6,A80_a03014:6,A80_a03017:6,A80_a03018:6,A80_a03024:6,
        //�����ǿ���¼����Ϣ��
        A81_g02001:3,A81_a29341:8,A81_a29344:3,A81_a29071:3,A81_a29347b:7,A81_a29347a:6,A81_a29351b:7,A81_a29347c:6,A81_a29072:6,A81_a39067:3,A81_a44031:3,A81_a39084:6,A81_a44027:3,A81_a39077:6,A81_a03011:6,A81_a03021:6,A81_a03095:6,A81_a03027:6,A81_a03014:6,A81_a03017:6,A81_a03018:6,A81_a03024:6,
        //������ѡ������Ϣ��
        A83_a02192:3,A83_a29311:3,A83_g02002:3,A83_a29044:3,A83_a29041:3,A83_a29354:8,A83_a44031:3,A83_a39084:6,A83_a44027:3,A83_a39077:6,
        //�����ǹ�����ѡ��Ϣ��
        A11_a1101:3,A11_a1104:3,A11_a1114:6,A11_a1107:8,A11_a1111:8,A11_a1107c:6,A11_a1108:6,A11_g02003:3,A11_a1151:3,A11_a1131:6,A11_a1121a:6,A11_a1127:3,A11_a1108a:6,A11_a1108b:3,
        A11_g11010:6,A11_g11003:6,A11_g11004:6,A11_g11006:6,A11_g11007:3,A11_g11011:6,A11_g11008:6,A11_g11009:3,A11_g11005:6,A11_g11015:6,A11_g11013:6,A11_g11012:8,A11_g11014:6,
        A11_g11020:6,A11_g11021:3,A11_g11022:6,A11_g11023:3,A11_g11024:6,A11_g11025:6,
        //��������ѵ��Ա��Ϣ��
        A82_a02191:3,A82_a29301:3,A82_a29304:3,A82_a29044:3,A82_a29307:8,
        //�����ǹ�����ѡ��Ϣ��
        A31_a3101:3,A31_a3104:8,A31_a3107:7,A31_a3118:6,A31_a3117a:6,A31_a3137:6,
        //������������Ա��Ϣ��
        SUPERVISION_position:6,SUPERVISION_informationtype:3,SUPERVISION_matter:6,SUPERVISION_startdate:6,SUPERVISION_dealorg:6,SUPERVISION_result:3,SUPERVISION_influencetime:6,SUPERVISION_filenumber:6,
        //�����Ǽල��Ϣ��
        a3604a:36,a3601:36,a3607:36,a3627:36,a3611:36,a3684:36,
        //�����Ǽ�ͥ��Ա��Ϣ�� 2017-11-29
        a0122:11,a0120:11,a0192d:3,//������Ϣ

        //��ǩ��Ϣ��
        a0193z:0,a0194z:0,a0194c:0,tagsbjysjlzs:0,tagrclxzs:0,tagcjlxzs:0,

        A01_a6506:7,A01_a0120:7
    },



    'fieldsDisabled':function(formfields){
        var formobj = null;
        for(var i=0; i<formfields.length; i++){
            var formfield = formfields[i];
            if($h.inputType[formfield]==1){//�ɻ��о����ı������
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                if('a3601'==formfield||'a3607'==formfield||'a3611'==formfield||'a3684'==formfield){
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
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                if('a3604a'==formfield||'a3627'==formfield){
                    var tff = formfield;
                    for(var m=1;m<=30;m++){
                        formfield=tff+'_'+m;
                        if(formobj = Ext.getCmp(formfield+'_combo')){
                            // formobj.disable();
                            $('#'+formfield+'_combo').parent().removeClass('x-item-disabled');
                            $('#'+formfield+'_combo').addClass('bgclor');
                        }
                    }
                }else{
                    if(formobj = Ext.getCmp(formfield+'_combo')){
                        var img = Ext.query("#"+formfield+"_combo+img")[0];
                        //img.style.display="none";
                        // formobj.disable();
                        $('#'+formfield+'_combo').parent().removeClass('x-item-disabled');
                        $('#'+formfield+'_combo').addClass('bgclor');
                    }
                }
            }else if($h.inputType[formfield]==4){//�ɻ��о��е�������
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
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
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                if(formobj = document.getElementById(formfield)){
                    formobj.ondblclick=null;
                    formobj.onkeypress=null;
                    $(formobj).addClass('bgclor');
                }
            }else if($h.inputType[formfield]==0){//�������ͨ�����
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                if(formfield=='a08os'){
                    try
                    {
                        document.getElementById("qrzxl").readOnly=true;
                        document.getElementById("qrzxlxx").readOnly=true;
                        document.getElementById("qrzxw").readOnly=true;
                        document.getElementById("qrzxwxx").readOnly=true;
                        document.getElementById("zzxlxx").readOnly=true;
                        document.getElementById("zzxl").readOnly=true;
                        document.getElementById("zzxw").readOnly=true;
                        document.getElementById("zzxwxx").readOnly=true;
                        $("#qrzxl").addClass('bgclor');
                        $("#qrzxlxx").addClass('bgclor');
                        $("#qrzxw").addClass('bgclor');
                        $("#qrzxwxx").addClass('bgclor');
                        $("#zzxl").addClass('bgclor');
                        $("#zzxlxx").addClass('bgclor');
                        $("#zzxw").addClass('bgclor');
                        $("#zzxwxx").addClass('bgclor');
                        document.getElementById("qrzxl").ondblclick=null;
                        document.getElementById("qrzxlxx").ondblclick=null;
                        document.getElementById("qrzxw").ondblclick=null;
                        document.getElementById("qrzxwxx").ondblclick=null;
                        document.getElementById("zzxl").ondblclick=null;
                        document.getElementById("zzxlxx").ondblclick=null;
                        document.getElementById("zzxw").ondblclick=null;
                        document.getElementById("zzxwxx").ondblclick=null;
                    }
                    catch(err)
                    {
                        continue;
                    }

                }
                if(formobj =document.getElementById(formfield)){
                    formobj.readOnly=true;
                    $(formobj).addClass('bgclor');

                    //ȥ���������¼�
                    formobj.ondblclick=null;
                }

            }else if($h.inputType[formfield]==6){//��ͨ�����
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                if(formobj = Ext.getCmp(formfield)){
                    formobj.disable();
                    $('#'+formfield).addClass('bgclor');
                }
            }else if($h.inputType[formfield]==7){//�����ؼ�
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                if(formobj = Ext.getCmp(formfield+'_combo')){
                    formobj.disable();
                    var img = Ext.query("#"+formfield+"_combo+img")[0];
                    //img.style.display="none";
                    img.onclick=null;
                    $('#'+formfield+'_combo').parent().removeClass('x-item-disabled');
                    $('#'+formfield+'_combo').addClass('bgclor');
                }
            }else if($h.inputType[formfield]==8){//���������
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                if(formobj = document.getElementById(formfield+'_1')){
                    formobj.readOnly=true;
                    $(formobj).addClass('bgclor');

                    //����ְ����ʱ�䡢����ְ��ʱ�������onclick�¼�����Ҫȥ��
                    formobj.ondblclick=null;

                }
            }else if($h.inputType[formfield]==9){					//checkbox
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                if(formobj = document.getElementById(formfield)){

                    //formobj.disabled = "disabled";
                    $(formobj).addClass('bgclor');
                    formobj.onclick = function(){return false;};

                }
            }else if($h.inputType[formfield] == 10){					//radio
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                if(formobj = document.getElementById(formfield)){

                    var formobj1 = document.getElementById(formfield + '1');
                    var formobj0 = document.getElementById(formfield + '0');

                    formobj1.disabled = "disabled";
                    $(formobj1).addClass('bgclor');

                    formobj0.disabled = "disabled";
                    $(formobj0).addClass('bgclor');

                }
            }else if($h.inputType[formfield] == 11){					//comboxArea�汾�ĵ�������
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                if(formobj = document.getElementById("comboxArea_"+formfield)){
                    formobj.disabled = "disabled";
                    $(formobj).addClass('bgclor');
					document.getElementById("comboxArea_"+formfield).ondblclick=null;
					
                    var formobjImg = document.getElementById("comboxImg_"+formfield);	//���������Ҳ�ķŴ�ͼƬ��onclickȥ��
                    formobjImg.onclick=null;
                }
            }else if($h.inputType[formfield] == 12){					//rmbSelect������
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                if(formobj = document.getElementById(formfield+ "Text")){
                    formobj.disabled = "disabled";
                    $(formobj).addClass('bgclor');
                }

                if(formobj = document.getElementById(formfield+ "Menu")){
                    formobj.disabled = "disabled";
                }
                if(formobj = document.getElementById(formfield+ "Arrow")){
                    formobj.disabled = "disabled";
                }

            }else if($h.inputType[formfield] == 36){					//��ͥ��Ա��Ϣ������,��ѭС���������������
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                //���ƣ����ƣ�ɾ��������ť����
                if(formobj = document.getElementById("upb")){
                    formobj.disabled = "disabled";
                    $(formobj).addClass('bgclor');
                    formobj.onclick=null;
                }

                if(formobj = document.getElementById("downb")){
                    formobj.disabled = "disabled";
                    $(formobj).addClass('bgclor');
                    formobj.onclick=null;
                }

                if(formobj = document.getElementById("addrowBtn")){
                    formobj.disabled = "disabled";
                    $(formobj).addClass('bgclor');
                    formobj.onclick=null;
                }

                if((formfield == "a3604a") || (formfield == "a3627")){		//��ν��������ò������

                    for (var f = 1; f <= 10; f++) {

                        var ichar = f+97;
                        var kk = String.fromCharCode(ichar);

                        if(formobj = document.getElementById(formfield+ "_"+ kk+"Text")){
                            //formobj.readOnly=true;
                            formobj.disabled = "disabled";
                            $(formobj).addClass('bgclor');

                        }
                        if(formobj = document.getElementById(formfield+ "_"+ kk+ "Menu")){
                            formobj.disabled = "disabled";
                        }
                        if(formobj = document.getElementById(formfield+ "_"+ kk+ "Arrow")){
                            formobj.disabled = "disabled";
                        }
                    }

                }

                if((formfield == "a3601") || (formfield == "a3611") || (formfield == "a3684")){		//���ơ�������λ��ְ�������
                    for (var f = 1; f <= 10; f++) {

                        var ichar = f+97;
                        var kk = String.fromCharCode(ichar);

                        //��ʾ��divҲ��ҪʧЧ
                        if(formobj = document.getElementById("wrapdiv_"+formfield+ "_"+ kk)){
                            // formobj.disabled = "disabled";
                            $(formobj).addClass('bgclor');
                            formobj.onclick=null;
                        }

                        if(formobj = document.getElementById(formfield+ "_"+ kk)){
                            formobj.readOnly=true;
                            //formobj.disabled = "disabled";
                            $(formobj).addClass('bgclor');
                        }
                    }
                }

                if(formfield == "a3607"){		//������д��

                    for (var f = 1; f <= 10; f++) {

                        var ichar = f+97;
                        var kk = String.fromCharCode(ichar);

                        //��ʾ��divҲ��ҪʧЧ
                        if(formobj = document.getElementById("wrapdiv_"+formfield+ "_"+ kk)){
                            // formobj.disabled = "disabled";
                            formobj.readOnly=true;
                            $(formobj).addClass('bgclor');
                            formobj.onclick=null;
                        }

                        if(formobj = document.getElementById(formfield+ "_"+ kk)){

                            formobj.readOnly=true;
                            //formobj.disabled = "disabled";
                            $(formobj).addClass('bgclor');
                        }
                    }
                }

            }else if($h.inputType[formfield] == 13){					//�����rmbNormalInput�����
                //��ʾ��divҲ��ҪʧЧ
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                if(formobj = document.getElementById("wrapdiv_"+formfield)){
                    // formobj.disabled = "disabled";
                    formobj.readOnly=true;
                    $(formobj).addClass('bgclor');
                    //formobjImg.onclick=null;
                }

                if(formobj = document.getElementById(formfield)){
                    //formobj.disabled = "disabled";
                    formobj.readOnly=true;
                    $(formobj).addClass('bgclor');
                }

            }else if($h.inputType[formfield] == 14){					//�����rmbPopWinInput������(���ᡢ������)
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                //formfield = formfield.substring(0,formfield.length-1);
                //��ʾ��divҲ��ҪʧЧ
                if(formobj = document.getElementById("wrapdiv_"+formfield)){
                    // formobj.disabled = "disabled";
                    formobj.readOnly=true;
                    $(formobj).addClass('bgclor');
                    //formobjImg.onclick=null;
                }
                if(formobj = document.getElementById("comboxArea_"+formfield)){

                    formobj.readOnly=true;
                    formobj.disabled = "disabled";
                    document.getElementById("comboxArea_"+formfield).ondblclick=null;
                   // $(formobj).addClass('bgclor');
                }

            }else if($h.inputType[formfield] == 15){					//���������������ҳ������������

            }



            formobj = null;
        }
    },


    //��Ϣ��Ȩ��2// ����� 1�ɻ��о����ı������3������4�ɻ��о��е����ؼ���5�������ڣ� 0��ͨ������������
    //��ͨҳ�棺 6��ͨ����� 7�����ؼ� 8��������� 3������  9checkbox  10radio  11comboxArea�汾�ĵ�������  12rmbSelect������
    'inputType2' : {a0101:13, a0104:12, a0107:13, a0117:12, a0111:14, a0114:14,
        a0140:13,a0134:13,a0128:12,a0196:13,a0187a:13,qrzxl:0,qrzxlxx:0,qrzxw:0,qrzxwxx:0,zzxl:0,zzxlxx:0,zzxw:0,zzxwxx:0,a0192a:0,a08os:0,a1701:15,
        a0184:0,a0165:12,a0160:12,a0121:12,
        a0192c:8,a0192e:11,a0195:11,a0115a:11,a2949:8,
        //����������һҳ
        a3604a:3,a3601:1,a3607:1,a3611:1,a3627:3,a14z101:0,a15z101:0,a0180:0,
        //���������ڶ�ҳ
        a0192:6,a0222:3,a0255:10,a0201b:7,a0201d:3,a0215a:6,a0216a:6,a0201e:3,a0221:11,a0221a:7,a0229:6,a0251:3,a0219:3,a0243:8,a0247:7,a0288:8,a0245:6,
        a0201a:6,a0265:8,a0267:6,a0219:9,a0201d:9,a0251b:9,a0279:9,a0197:9,a0272:0,
        //���Ϲ�����λ��ְ��
        a0837:3,a0801b:7,a0901b:7,a0811:6,a0801a:6,a0901a:6,a0814:6,a0827:7,a0804:8,a0807:8,a0824:6,a0904:8,
        //������ѧ��ѧλ
        a0601:7,a0602:6,a0607:3,a0611:6,a0604:8,
        //������רҵ����ְ��
        A14_a1404b:7,A14_a1404a:6,A14_a1415:7,A14_a1414:3,A14_a1428:7,A14_a1411a:6,A14_a1407:8,A14_a1424:8,
        //�����ǽ�����Ϣ
        A15_a1521:3,A15_a1517:7,//a0191:checkbox
        //��������ȿ�����Ϣ
        A29_a2921a:6,A29_a2941:6,A29_a2907:8,A29_a2911:7,A29_a2944:7,A29_a2947a:6,/*,a2921b:7,a2947:8,a2970:3,a2950:3,a2949:8,a2947a:6,a2947b:6,a2951:3,a2921c:3,a2921d:3,*/
        //�����ǽ������
        a1131:6,a1101:3,a1114:6,a1121a:6,a1107:8,a1111:8,a1107c:6,a1108:6,a1107a:6,a1107b:6,a1127:3,a1104:3,a1151:3,
        //��������ѵ��Ϣ
        a3101:3,a3104:8,a3137:6,a3107:7,a3118:6,a3117a:6,a3140:7,a3141:7,
        //����������
        /*a3001:7,a3007a:6,a3004:8,a3034:7,*/
        A30_a3001:7,A30_a3004:8,A30_a3034:6,A30_a3954a:8,A30_a3954b:6,A30_a3007a:6,A30_a3038:6,
        //�������˳�����
        a5304:6,a5315:6,a5317:6,a5319:6,a5321:8,a5323:8,a5327:6,
        //������������
        A37_a3701:6,A37_a3707a:6,A37_a3707b:6,A37_a3707c:6,A37_a3711:6,A37_a3721:6,
        /*a3701:6,a3707a:6,a3707c:6,a3707b:6,a3708:6,a3707e:6,a3711:6,a3714:6,*/
        //������סַͨѶ
        a0141:3,a0144:8,a3921:3,a3927:3,
        //�������뵳ʱ��
        a99z101:9,a99z102:8,a99z103:9,a99z104:8,
        //�����ǲ�����Ϣ�� 2017-11-29
        A80_a29314:3,A80_a03033:8,A80_a29321:3,A80_a29337:7,A80_a29324b:7,A80_a29324a:6,A80_a29327b:7,A80_a29327a:6,A80_a39061:3,A80_a39064:3,A80_a39067:3,A80_a39071:3,A80_a44031:3,A80_a39084:6,A80_a44027:3,A80_a39077:6,A80_a03011:6,A80_a03021:6,A80_a03095:6,A80_a03027:6,A80_a03014:6,A80_a03017:6,A80_a03018:6,A80_a03024:6,
        //�����ǿ���¼����Ϣ��
        A81_g02001:3,A81_a29341:8,A81_a29344:3,A81_a29071:3,A81_a29347b:7,A81_a29347a:6,A81_a29351b:7,A81_a29347c:6,A81_a29072:6,A81_a39067:3,A81_a44031:3,A81_a39084:6,A81_a44027:3,A81_a39077:6,A81_a03011:6,A81_a03021:6,A81_a03095:6,A81_a03027:6,A81_a03014:6,A81_a03017:6,A81_a03018:6,A81_a03024:6,
        //������ѡ������Ϣ��
        A83_a02192:3,A83_a29311:3,A83_g02002:3,A83_a29044:3,A83_a29041:3,A83_a29354:8,A83_a44031:3,A83_a39084:6,A83_a44027:3,A83_a39077:6,
        //�����ǹ�����ѡ��Ϣ��
        A11_a1101:3,A11_a1104:3,A11_a1114:6,A11_a1107:8,A11_a1111:8,A11_a1107c:6,A11_a1108:6,A11_g02003:3,A11_a1151:3,A11_a1131:6,A11_a1121a:6,A11_a1127:3,A11_a1108a:6,A11_a1108b:3,
        A11_g11010:6,A11_g11003:6,A11_g11004:6,A11_g11006:6,A11_g11007:3,A11_g11011:6,A11_g11008:6,A11_g11009:3,A11_g11005:6,A11_g11015:6,A11_g11013:6,A11_g11012:8,A11_g11014:6,
        A11_g11020:6,A11_g11021:3,A11_g11022:6,A11_g11023:3,A11_g11024:6,A11_g11025:6,
        //��������ѵ��Ա��Ϣ��
        A82_a02191:3,A82_a29301:3,A82_a29304:3,A82_a29044:3,A82_a29307:8,
        //������������Ա��Ϣ��
        SUPERVISION_position:6,SUPERVISION_informationtype:3,SUPERVISION_matter:6,SUPERVISION_startdate:6,SUPERVISION_dealorg:6,SUPERVISION_result:3,SUPERVISION_influencetime:6,SUPERVISION_filenumber:6,
        //�����ǹ�����ѡ��Ϣ��
        A31_a3101:3,A31_a3104:8,A31_a3107:7,A31_a3118:6,A31_a3117a:6,A31_a3137:6,
        //������������Ա��Ϣ��
        a3604a:36,a3601:36,a3607:36,a3627:36,a3611:36,a3684:36,
        //�����Ǽ�ͥ��Ա��Ϣ�� 2017-11-29
        a0122:11,a0120:11,a0192d:3,//������Ϣ

        //��ǩ��Ϣ��
        a0193z:0,a0194z:0,a0194c:0,tagsbjysjlzs:0,tagrclxzs:0,tagcjlxzs:0
    },






    'selectDisabled':function(formfields,imgdata){
        var formobj = null;
        for(var i=0; i<formfields.length; i++){
            var formfield = formfields[i];
            if($h.inputType2[formfield]==1){//�ɻ��о����ı������
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                if('a3601'==formfield||'a3607'==formfield||'a3611'==formfield||'a3684'==formfield){
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


            }else if($h.inputType2[formfield]==3){//������
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                if('a3604a'==formfield||'a3627'==formfield){
                    var tff = formfield;
                    for(var m=1;m<=30;m++){
                        formfield=tff+'_'+m;
                        if(formobj = Ext.getCmp(formfield+'_combo')){
                            var div = document.createElement("div");
                            var pNode = document.getElementById(formfield+'_combo').parentNode;
                            pNode.style.position = "relative";
                            div.style.border="1px solid rgb(192,192,192)";
                            div.style.width = document.getElementById(formfield+'_combo').offsetWidth;
                            div.style.height = document.getElementById(formfield+'_combo').offsetHeight;
                            div.style.position = "absolute";
                            div.style.left = '0px';
                            div.style.top = '0px';
                            div.style.backgroundImage = imgdata;
                            div.style.backgroundRepeat = "no-repeat";
                            div.style.backgroundColor = "white";
                            div.style.backgroundPosition = "center";
                            pNode.appendChild(div);
                            formobj.disable();
                            $('#'+formfield+'_combo').parent().removeClass('x-item-disabled');
                            $('#'+formfield+'_combo').addClass('bgclor');
                        }
                    }
                }else{
                    if(formobj = Ext.getCmp(formfield+'_combo')){
                        var div = document.createElement("div");
                        var pNode = document.getElementById(formfield+'_combo').parentNode;
                        pNode.style.position = "relative";
                        div.style.border="1px solid rgb(192,192,192)";
                        div.style.width = document.getElementById(formfield+'_combo').offsetWidth;
                        div.style.height = document.getElementById(formfield+'_combo').offsetHeight;
                        div.style.position = "absolute";
                        div.style.left = '0px';
                        div.style.top = '0px';
                        div.style.backgroundImage = imgdata;
                        div.style.backgroundRepeat = "no-repeat";
                        div.style.backgroundColor = "white";
                        div.style.backgroundPosition = "center";
                        pNode.appendChild(div);
                        var img = Ext.query("#"+formfield+"_combo+img")[0];
                        img.style.display="none";
                        formobj.disable();
                        $('#'+formfield+'_combo').parent().removeClass('x-item-disabled');
                        $('#'+formfield+'_combo').addClass('bgclor');
                    }
                }
            }else if($h.inputType2[formfield]==4){//�ɻ��о��е�������
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
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
            }else if($h.inputType2[formfield]==5){//��������
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                if(formobj = document.getElementById(formfield)){
                    formobj.ondblclick=null;
                    formobj.onkeypress=null;
                    $(formobj).addClass('bgclor');
                }
            }else if($h.inputType2[formfield]==0){//�������ͨ�����
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                if(formfield=='a08os'){
                    try{
                        var a08field = ["qrzxl","qrzxlxx","qrzxw","qrzxwxx","zzxlxx","zzxl","zzxw","zzxwxx"];
                        for(var z=0;z<a08field.length;z++){
                            var div = document.createElement("div");
                            var pNode = document.getElementById(a08field[z]).parentNode;
                            pNode.style.position = "relative";
                            div.style.width = document.getElementById(a08field[z]).offsetWidth;
                            div.style.height = document.getElementById(a08field[z]).offsetHeight;
                            div.style.position = "absolute";
                            div.style.left = $('#'+a08field[z]).position().left;
                            div.style.top = $('#'+a08field[z]).position().top;
                            div.style.backgroundImage = imgdata;
                            div.style.backgroundRepeat = "no-repeat";
                            div.style.backgroundColor = "white";
                            div.style.backgroundPosition = "center";

                            pNode.appendChild(div);
                        }
                        document.getElementById("qrzxl").readOnly=true;
                        document.getElementById("qrzxlxx").readOnly=true;
                        document.getElementById("qrzxw").readOnly=true;
                        document.getElementById("qrzxwxx").readOnly=true;
                        document.getElementById("zzxlxx").readOnly=true;
                        document.getElementById("zzxl").readOnly=true;
                        document.getElementById("zzxw").readOnly=true;
                        document.getElementById("zzxwxx").readOnly=true;
                        $("#qrzxl").addClass('bgclor');
                        $("#qrzxlxx").addClass('bgclor');
                        $("#qrzxw").addClass('bgclor');
                        $("#qrzxwxx").addClass('bgclor');
                        $("#zzxl").addClass('bgclor');
                        $("#zzxlxx").addClass('bgclor');
                        $("#zzxw").addClass('bgclor');
                        $("#zzxwxx").addClass('bgclor');
                        document.getElementById("qrzxl").ondblclick=null;
                        document.getElementById("qrzxlxx").ondblclick=null;
                        document.getElementById("qrzxw").ondblclick=null;
                        document.getElementById("qrzxwxx").ondblclick=null;
                        document.getElementById("zzxl").ondblclick=null;
                        document.getElementById("zzxlxx").ondblclick=null;
                        document.getElementById("zzxw").ondblclick=null;
                        document.getElementById("zzxwxx").ondblclick=null;
                    }catch(err){

                    }


                }
                if(formobj =document.getElementById(formfield)){
                    var div = document.createElement("div");
                    var pNode = document.getElementById(formfield).parentNode;
                    pNode.style.position = "relative";
                    div.style.width = document.getElementById(formfield).offsetWidth;
                    div.style.height = document.getElementById(formfield).offsetHeight;
                    div.style.position = "absolute";
                    div.style.left = $('#'+formfield).position().left;
                    div.style.top = $('#'+formfield).position().top;
                    div.style.backgroundImage = imgdata;
                    div.style.backgroundRepeat = "no-repeat";
                    div.style.backgroundColor = "white";
                    div.style.backgroundPosition = "center";
                    pNode.appendChild(div);
                    formobj.readOnly=true;
                    $(formobj).addClass('bgclor');

                    //ȥ���������¼�
                    formobj.ondblclick=null;
                }

            }else if($h.inputType2[formfield]==6){//��ͨ�����
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                //һЩ������ֶ�ҳ�洦��
                if(formfield!="a2947a"){
                    if(formobj = Ext.getCmp(formfield)){
                        var div = document.createElement("div");
                        var pNode = document.getElementById(formfield).parentNode;
                        pNode.style.position = "relative";
                        div.style.border="1px solid rgb(192,192,192)";
                        div.style.width = document.getElementById(formfield).offsetWidth;
                        div.style.height = document.getElementById(formfield).offsetHeight;
                        div.style.position = "absolute";
                        div.style.left = '0px';
                        div.style.top = '0px';
                        div.style.backgroundImage = imgdata;
                        div.style.backgroundRepeat = "no-repeat";
                        div.style.backgroundColor = "white";
                        div.style.backgroundPosition = "center";
                        pNode.appendChild(div);
                        /*var div = document.createElement("div");
                        div.style.width = document.getElementById(formfield).clientWidth;
                        div.style.height = document.getElementById(formfield).clientHeight;
                        div.style.position = "absolute";
                        div.style.left = $('#'+formfield).offset().left+'px';
                        div.style.top = $('#'+formfield).offset().top+'px';
                ��������div.innerHTML = imgdata;
                ��������document.body.appendChild(div);*/
                        formobj.disable();
                        $('#'+formfield).addClass('bgclor');
                    }
                }
            }else if($h.inputType2[formfield]==7){//�����ؼ�
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                if(formobj = Ext.getCmp(formfield+'_combo')){
                    var div = document.createElement("div");
                    var pNode = document.getElementById(formfield+'_combo').parentNode;
                    pNode.style.position = "relative";
                    div.style.border="1px solid rgb(192,192,192)";
                    div.style.width = document.getElementById(formfield+'_combo').offsetWidth;
                    div.style.height = document.getElementById(formfield+'_combo').offsetHeight;
                    div.style.position = "absolute";
                    div.style.left = '0px';
                    div.style.top = '0px';
                    div.style.backgroundImage = imgdata;
                    div.style.backgroundRepeat = "no-repeat";
                    div.style.backgroundColor = "white";
                    div.style.backgroundPosition = "center";
                    pNode.appendChild(div);

                    formobj.disable();
                    var img = Ext.query("#"+formfield+"_combo+img")[0];
                    //img.style.display="none";
                    img.onclick=null;
                    $('#'+formfield+'_combo').parent().removeClass('x-item-disabled');
                    $('#'+formfield+'_combo').addClass('bgclor');
                }
            }else if($h.inputType2[formfield]==8){//���������
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                if(formobj = document.getElementById(formfield+'_1')){
                    var div = document.createElement("div");
                    var pNode = document.getElementById(formfield+'_1').parentNode;
                    pNode.style.position = "relative";
                    div.style.border="1px solid rgb(192,192,192)";
                    div.style.width = document.getElementById(formfield+'_1').offsetWidth;
                    div.style.height = document.getElementById(formfield+'_1').offsetHeight;
                    div.style.position = "absolute";
                    div.style.left = $('#'+formfield+'_1').position().left;
                    div.style.top = $('#'+formfield+'_1').position().top;
                    div.style.backgroundImage = imgdata;
                    div.style.backgroundRepeat = "no-repeat";
                    div.style.backgroundColor = "white";
                    div.style.backgroundPosition = "center";
                    pNode.appendChild(div);
                    formobj.readOnly=true;
                    $(formobj).addClass('bgclor');

                    //����ְ����ʱ�䡢����ְ��ʱ�������onclick�¼�����Ҫȥ��
                    formobj.ondblclick=null;

                }
            }else if($h.inputType2[formfield]==9){					//checkbox
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                if(formobj = document.getElementById(formfield)){
                    var div = document.createElement("div");
                    var pNode = document.getElementById(formfield).parentNode;
                    pNode.style.position = "relative";
                    div.style.border="1px solid rgb(192,192,192)";
                    div.style.width = document.getElementById(formfield).offsetWidth;
                    div.style.height = document.getElementById(formfield).offsetHeight;
                    div.style.position = "absolute";
                    div.style.left = $('#'+formfield).position().left;
                    div.style.top = $('#'+formfield).position().top;
                    div.style.backgroundImage = imgdata;
                    div.style.backgroundRepeat = "no-repeat";
                    div.style.backgroundColor = "white";
                    div.style.backgroundPosition = "center";
                    pNode.appendChild(div);
                    //formobj.disabled = "disabled";
                    $(formobj).addClass('bgclor');
                    formobj.onclick = function(){return false;};

                }
            }else if($h.inputType2[formfield] == 10){					//radio
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                if(formobj = document.getElementById(formfield)){
                    var div = document.createElement("div");
                    var pNode = document.getElementById(formfield + '1').parentNode;
                    pNode.style.position = "relative";
                    div.style.border="1px solid rgb(192,192,192)";
                    div.style.width = document.getElementById(formfield + '1').offsetWidth;
                    div.style.height = document.getElementById(formfield + '1').offsetHeight;
                    div.style.position = "absolute";
                    div.style.left = $('#'+formfield + '1').position().left;
                    div.style.top = $('#'+formfield + '1').position().top;
                    div.style.backgroundImage = imgdata;
                    div.style.backgroundRepeat = "no-repeat";
                    div.style.backgroundColor = "white";
                    div.style.backgroundPosition = "center";
                    pNode.appendChild(div);
                    var div = document.createElement("div");
                    var pNode = document.getElementById(formfield + '0').parentNode;
                    pNode.style.position = "relative";
                    div.style.border="1px solid rgb(192,192,192)";
                    div.style.width = document.getElementById(formfield + '0').offsetWidth;
                    div.style.height = document.getElementById(formfield + '0').offsetHeight;
                    div.style.position = "absolute";
                    div.style.left = $('#'+formfield + '0').position().left;
                    div.style.top = $('#'+formfield + '0').position().top;
                    div.style.backgroundImage = imgdata;
                    div.style.backgroundRepeat = "no-repeat";
                    div.style.backgroundColor = "white";
                    div.style.backgroundPosition = "center";
                    pNode.appendChild(div);
                    var formobj1 = document.getElementById(formfield + '1');
                    var formobj0 = document.getElementById(formfield + '0');

                     formobj1.disabled = "disabled";
                    $(formobj1).addClass('bgclor');

                     formobj0.disabled = "disabled";
                    $(formobj0).addClass('bgclor');

                }
            }else if($h.inputType2[formfield] == 11){					//comboxArea�汾�ĵ�������
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                if(formobj = document.getElementById("comboxArea_"+formfield)){
                    var div = document.createElement("div");
                    var pNode = document.getElementById("comboxArea_"+formfield).parentNode;
                    pNode.style.position = "relative";
                    div.style.width = document.getElementById("comboxArea_"+formfield).offsetWidth;
                    div.style.height = document.getElementById("comboxArea_"+formfield).offsetHeight;
                    div.style.position = "absolute";
                    div.style.left = $('#'+"comboxArea_"+formfield).position().left;
                    div.style.top = $('#'+"comboxArea_"+formfield).position().top;
                    div.style.backgroundImage = imgdata;
                    div.style.backgroundRepeat = "no-repeat";
                    div.style.backgroundColor = "white";
                    div.style.backgroundPosition = "center";
                    pNode.appendChild(div);
                     formobj.disabled = "disabled";
                    $(formobj).addClass('bgclor');
					document.getElementById("comboxArea_"+formfield).ondblclick=null;
                    var formobjImg = document.getElementById("comboxImg_"+formfield);	//���������Ҳ�ķŴ�ͼƬ��onclickȥ��
                    formobjImg.onclick=null;
                }
            }else if($h.inputType2[formfield] == 12){					//rmbSelect������
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                if(formobj = document.getElementById(formfield+ "Text")){
                    var div = document.createElement("div");
                    var pNode = document.getElementById(formfield+'Text').parentNode;
                    pNode.style.position = "relative";
                    div.style.width = document.getElementById(formfield+'Text').offsetWidth;
                    div.style.height = document.getElementById(formfield+'Text').offsetHeight;
                    div.style.position = "absolute";
                    div.style.left = $('#'+formfield+'Text').position().left;
                    div.style.top = $('#'+formfield+'Text').position().top;
                    div.style.backgroundImage = imgdata;
                    div.style.backgroundRepeat = "no-repeat";
                    div.style.backgroundColor = "white";
                    div.style.backgroundPosition = "center";
                    pNode.appendChild(div);
                     formobj.disabled = "disabled";
                    $(formobj).addClass('bgclor');
                }

                if(formobj = document.getElementById(formfield+ "Menu")){
                    var div = document.createElement("div");
                    var pNode = document.getElementById(formfield+'Text').parentNode;
                    pNode.style.position = "relative";
                    div.style.width = document.getElementById(formfield+'Text').offsetWidth;
                    div.style.height = document.getElementById(formfield+'Text').offsetHeight;
                    div.style.position = "absolute";
                    div.style.left = $('#'+formfield+'Text').position().left;
                    div.style.top = $('#'+formfield+'Text').position().top;
                    div.style.backgroundImage = imgdata;
                    div.style.backgroundRepeat = "no-repeat";
                    div.style.backgroundColor = "white";
                    div.style.backgroundPosition = "center";
                    pNode.appendChild(div);
                    // formobj.disabled = "disabled";
                }
                if(formobj = document.getElementById(formfield+ "Arrow")){
                    var div = document.createElement("div");
                    var pNode = document.getElementById(formfield+'Text').parentNode;
                    pNode.style.position = "relative";
                    div.style.width = document.getElementById(formfield+'Text').offsetWidth;
                    div.style.height = document.getElementById(formfield+'Text').offsetHeight;
                    div.style.position = "absolute";
                    div.style.left = $('#'+formfield+'Text').position().left;
                    div.style.top = $('#'+formfield+'Text').position().top;
                    div.style.backgroundImage = imgdata;
                    div.style.backgroundRepeat = "no-repeat";
                    div.style.backgroundColor = "white";
                    div.style.backgroundPosition = "center";
                    pNode.appendChild(div);
                    formobj.disabled = "disabled";
                }

            }else if($h.inputType2[formfield] == 36){					//��ͥ��Ա��Ϣ������,��ѭС���������������
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                //���ƣ����ƣ�ɾ��������ť����
                if(formobj = document.getElementById("upb")){
                    formobj.disabled = "disabled";
                    $(formobj).addClass('bgclor');
                    formobj.onclick=null;
                }

                if(formobj = document.getElementById("downb")){
                    formobj.disabled = "disabled";
                    $(formobj).addClass('bgclor');
                    formobj.onclick=null;
                }

                if(formobj = document.getElementById("addrowBtn")){
                    formobj.disabled = "disabled";
                    $(formobj).addClass('bgclor');
                    formobj.onclick=null;
                }

                if((formfield == "a3604a") || (formfield == "a3627")){		//��ν��������ò������

                    for (var f = 1; f <= 10; f++) {

                        var ichar = f+97;
                        var kk = String.fromCharCode(ichar);

                        if(formobj = document.getElementById(formfield+ "_"+ kk+"Text")){
                            var div = document.createElement("div");
                            var pNode = document.getElementById(formfield+ "_"+ kk+"Text").parentNode;
                            pNode.style.position = "relative";
                            div.style.width = document.getElementById(formfield+ "_"+ kk+"Text").offsetWidth;
                            div.style.height = document.getElementById(formfield+ "_"+ kk+"Text").offsetHeight;
                            div.style.position = "absolute";
                            div.style.left = $('#'+formfield+ "_"+ kk+"Text").position().left;
                            div.style.top = $('#'+formfield+ "_"+ kk+"Text").position().top;
                            div.style.backgroundImage = imgdata;
                            div.style.backgroundRepeat = "no-repeat";
                            div.style.backgroundColor = "white";
                            div.style.backgroundPosition = "center";
                            pNode.appendChild(div);


                            //formobj.readOnly=true;
                            formobj.disabled = "disabled";
                            $(formobj).addClass('bgclor');

                        }
                        if(formobj = document.getElementById(formfield+ "_"+ kk+ "Menu")){
                            formobj.disabled = "disabled";
                        }
                        if(formobj = document.getElementById(formfield+ "_"+ kk+ "Arrow")){
                            formobj.disabled = "disabled";
                        }
                    }

                }

                if((formfield == "a3601") || (formfield == "a3611") || (formfield == "a3684")){		//���ơ�������λ��ְ�������
                    for (var f = 1; f <= 10; f++) {

                        var ichar = f+97;
                        var kk = String.fromCharCode(ichar);
                        //��ʾ��divҲ��ҪʧЧ
                        if(formobj = document.getElementById("wrapdiv_"+formfield+ "_"+ kk)){
                            var div = document.createElement("div");
                            var pNode = document.getElementById("wrapdiv_"+formfield+ "_"+ kk).parentNode;
                            pNode.style.position = "relative";
                            div.style.width = document.getElementById("wrapdiv_"+formfield+ "_"+ kk).offsetWidth;
                            div.style.height = document.getElementById("wrapdiv_"+formfield+ "_"+ kk).offsetHeight;
                            div.style.position = "absolute";
                            div.style.left = '0px';
                            div.style.top = '0px';
                            div.style.backgroundImage = imgdata;
                            div.style.backgroundRepeat = "no-repeat";
                            div.style.backgroundColor = "white";
                            div.style.backgroundPosition = "center";
                            pNode.appendChild(div);
                            formobj.readOnly=true;
                            // formobj.disabled = "disabled";
                            $(formobj).addClass('bgclor');
                            formobj.onclick=null;
                        }

                        if(formobj = document.getElementById(formfield+ "_"+ kk)){
                            var div = document.createElement("div");
                            var pNode = document.getElementById("wrapdiv_"+formfield+ "_"+ kk).parentNode;
                            pNode.style.position = "relative";
                            div.style.width = document.getElementById("wrapdiv_"+formfield+ "_"+ kk).offsetWidth;
                            div.style.height = document.getElementById("wrapdiv_"+formfield+ "_"+ kk).offsetHeight;
                            div.style.position = "absolute";
                            div.style.left = '0px';
                            div.style.top = '0px';
                            div.style.backgroundImage = imgdata;
                            div.style.backgroundRepeat = "no-repeat";
                            div.style.backgroundColor = "white";
                            div.style.backgroundPosition = "center";
                            pNode.appendChild(div);

                            formobj.readOnly=true;
                            //formobj.disabled = "disabled";
                            $(formobj).addClass('bgclor');
                        }
                    }
                }

                if(formfield == "a3607"){		//������д��

                    for (var f = 1; f <= 10; f++) {

                        var ichar = f+97;
                        var kk = String.fromCharCode(ichar);

                        //��ʾ��divҲ��ҪʧЧ
                        if(formobj = document.getElementById("wrapdiv_"+formfield+ "_"+ kk)){
                            var div = document.createElement("div");
                            var pNode = document.getElementById("wrapdiv_"+formfield+ "_"+ kk).parentNode;
                            pNode.style.position = "relative";
                            div.style.width = document.getElementById("wrapdiv_"+formfield+ "_"+ kk).offsetWidth;
                            div.style.height = document.getElementById("wrapdiv_"+formfield+ "_"+ kk).offsetHeight;
                            div.style.position = "absolute";
                            div.style.left = '0px';
                            div.style.top = '0px';
                            div.style.backgroundImage = imgdata;
                            div.style.backgroundRepeat = "no-repeat";
                            div.style.backgroundColor = "white";
                            div.style.backgroundPosition = "center";
                            pNode.appendChild(div);
                            formobj.readOnly=true;
                            // formobj.disabled = "disabled";
                            $(formobj).addClass('bgclor');
                            formobj.onclick=null;
                        }

                        if(formobj = document.getElementById(formfield+ "_"+ kk)){
                            var div = document.createElement("div");
                            var pNode = document.getElementById(formfield+ "_"+ kk).parentNode;
                            pNode.style.position = "relative";
                            div.style.width = document.getElementById(formfield+ "_"+ kk).offsetWidth;
                            div.style.height = document.getElementById(formfield+ "_"+ kk).offsetHeight;
                            div.style.position = "absolute";
                            div.style.left = '0px';
                            div.style.top = '0px';
                            div.style.backgroundImage = imgdata;
                            div.style.backgroundRepeat = "no-repeat";
                            div.style.backgroundColor = "white";
                            div.style.backgroundPosition = "center";
                            pNode.appendChild(div);

                            formobj.readOnly=true;
                            //formobj.disabled = "disabled";
                            $(formobj).addClass('bgclor');
                        }
                    }
                }

            }else if($h.inputType2[formfield] == 13){					//�����rmbNormalInput�����
                //��ʾ��divҲ��ҪʧЧ
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                if(formobj = document.getElementById("wrapdiv_"+formfield)){
                    var div = document.createElement("div");
                    var pNode = document.getElementById("wrapdiv_"+formfield).parentNode;
                    pNode.style.position = "relative";
                    div.style.width = document.getElementById("wrapdiv_"+formfield).offsetWidth;
                    div.style.height = document.getElementById("wrapdiv_"+formfield).offsetHeight;
                    div.style.position = "absolute";
                    div.style.left = $('#'+"wrapdiv_"+formfield).position().left;
                    div.style.top = $('#'+"wrapdiv_"+formfield).position().top;
                    div.style.backgroundImage = imgdata;
                    div.style.backgroundRepeat = "no-repeat";
                    div.style.backgroundColor = "white";
                    div.style.backgroundPosition = "center";
                    pNode.appendChild(div);
                    // formobj.disabled = "disabled";
                    $(formobj).addClass('bgclor');
                    formobj.readOnly=true;
                    //formobjImg.onclick=null;
                }

                if(formobj = document.getElementById(formfield)){
                    //formobj.disabled = "disabled";
                    var div = document.createElement("div");
                    var pNode = document.getElementById("wrapdiv_"+formfield).parentNode;
                    pNode.style.position = "relative";
                    div.style.width = document.getElementById("wrapdiv_"+formfield).offsetWidth;
                    div.style.height = document.getElementById("wrapdiv_"+formfield).offsetHeight;
                    div.style.position = "absolute";
                    div.style.left = $('#'+"wrapdiv_"+formfield).position().left;
                    div.style.top = $('#'+"wrapdiv_"+formfield).position().top;
                    div.style.backgroundImage = imgdata;
                    div.style.backgroundRepeat = "no-repeat";
                    div.style.backgroundColor = "white";
                    div.style.backgroundPosition = "center";
                    pNode.appendChild(div);
                    formobj.readOnly=true;
                    $(formobj).addClass('bgclor');
                }

            }else if($h.inputType2[formfield] == 14){					//�����rmbPopWinInput������(���ᡢ������)
                if(formfield.indexOf("_")!=-1){
                    formfield = formfield.split("_")[1];
                }
                //formfield = formfield.substring(0,formfield.length-1);
                //��ʾ��divҲ��ҪʧЧ
                if(formobj = document.getElementById("wrapdiv_"+formfield)){
                    var div = document.createElement("div");
                    var pNode = document.getElementById("wrapdiv_"+formfield).parentNode;
                    pNode.style.position = "relative";
                    div.style.width = document.getElementById("wrapdiv_"+formfield).offsetWidth;
                    div.style.height = document.getElementById("wrapdiv_"+formfield).offsetHeight;
                    div.style.position = "absolute";
                    div.style.left = $('#'+"wrapdiv_"+formfield).position().left;
                    div.style.top = $('#'+"wrapdiv_"+formfield).position().top;
                    div.style.backgroundImage = imgdata;
                    div.style.backgroundRepeat = "no-repeat";
                    div.style.backgroundColor = "white";
                    div.style.backgroundPosition = "center";
                    pNode.appendChild(div);
                     formobj.disabled = "disabled";
                    $(formobj).addClass('bgclor');
                    formobjImg.onclick=null;
                }
                if(formobj = document.getElementById("comboxArea_"+formfield)){
                    var div = document.createElement("div");
                    var pNode = document.getElementById("comboxArea_"+formfield).parentNode;
                    pNode.style.position = "relative";
                    div.style.width = document.getElementById("comboxArea_"+formfield).offsetWidth;
                    div.style.height = document.getElementById("comboxArea_"+formfield).offsetHeight;
                    div.style.position = "absolute";
                    div.style.left = $('#'+"comboxArea_"+formfield).position().left;
                    div.style.top = $('#'+"comboxArea_"+formfield).position().top;
                    div.style.backgroundImage = imgdata;
                    div.style.backgroundRepeat = "no-repeat";
                    div.style.backgroundColor = "white";
                    div.style.backgroundPosition = "center";
                    pNode.appendChild(div);
					document.getElementById("comboxArea_"+formfield).ondblclick=null;
                    formobj.readOnly=true;
                    formobj.disabled = "disabled";
                    $(formobj).addClass('bgclor');
                }

            }else if($h.inputType2[formfield] == 15){					//���������������ҳ������������

            }



            formobj = null;
        }
    },
    //��ȡ������ҳ��
    'getTopParent' : function(){
        parentWinScope = window;
        while(true){
            if(parentWinScope==parentWinScope.parent){
                return parentWinScope;
            }else{
                parentWinScope = parentWinScope.parent;
            }
        }
    },

    'openPageModeWin' : function(id,url,title,width,height,param,ctx,parentScope,wincfg,isrmb){
        var pWidth = screen.availWidth;
        var pHeigth = screen.availHeight;

        if(!width||pWidth<width){
            width = pWidth;
        }
        if(!height||pHeigth<height){
            height = pHeigth;
        }
        var wtop = (pHeigth-height)/2;
        var wleft = (pWidth-width)/2;
        url = ctx+'/radowAction.do?method=doEvent&pageModel='+url+"&aa="+Math.random();
			var obj = {window: window, wincfg: wincfg, title: title, param:param};
	        if (window.showModalDialog) {
	        	var bodyscroll;
		        if(param["scroll"]){
		        	bodyscroll=param["scroll"];
		        }else{
		        	bodyscroll="scroll:no;"
		        }
				var p = "dialogWidth="+width+"px;dialogHeight="+height+"px;dialogLeft="+wleft+"px;dialogTop="+wtop+"px;help=no;resizable=no;status=no;center=yes;scroll:no;location:no;toolbar:no;menubar:no;titlebar:no;"+bodyscroll;
				return window.showModalDialog(url,obj,p);
			} else {
				var p = "top="+wtop+",left="+wleft+",width="+width+",height="+height+",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no";
				var opener = window.open(url,title,p);
				opener.dialogArguments = obj;
				return opener;
			}
    },
    'showModalDialog':function(id,url,title,width,height,parentScope,_cfg,isrmb){
        var pWidth = screen.availWidth;
        var pHeigth = screen.availHeight;

        if(!width||pWidth<width){
            width = pWidth;
        }
        if(!height||pHeigth<height){
            height = pHeigth;
        }
        url = encodeURI(encodeURI(url));
			var wtop = (pHeigth-height)/2;
			var wleft = (pWidth-width)/2;
			var obj = {window: window, wincfg: _cfg, title: title};
	        if (window.showModalDialog) {
				var p = "dialogWidth="+width+"px;dialogHeight="+height+"px;dialogLeft="+wleft+"px;dialogTop="+wtop+"px;help=no;resizable=no;status=no;center=yes;scroll:no";
        		return window.showModalDialog(url,obj,p);
			} else {
				var p = "top="+wtop+",left="+wleft+",width="+width+",height="+height+",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no";
				var opener = window.open(url,title,p);
				if(opener==null){
					alert("360��������������������ڣ�����->�߼�����->��ҳ����->�������κ���վ��ʾ����ʽ���ڡ�ȥ��")
				}
				opener.dialogArguments = obj;
				return opener;
			}
    },

    'openWin' : function(id,url,title,width,height,param,ctx,parentScope,wincfg,isrmb){
        //var cfg = {modal:true,collapsed:false,collapsible:false};
        //Ext.applyIf(wincfg,cfg );
        if(!parentScope){
            parentScope = $h.getTopParent();
        }

        var pWidth = parentScope.Ext.getBody().getViewSize().width;
        var pHeigth = parentScope.Ext.getBody().getViewSize().height

        if(!width||pWidth<width){
            if(isrmb){
                width = pWidth-4;
            }else{
                width = parseInt(pWidth*0.6);
            }

        }
        if(!height||pHeigth<height){
            if(isrmb){
                height = pHeigth;
            }else{
                height = parseInt(pHeigth*0.8);
            }

        }
        var wtop = (pHeigth-height)/2;
        var wleft = (pWidth-width)/2;
        if(wincfg&&wincfg.top){
            wtop = wtop>wincfg.top?wincfg.top:wtop;
        }
        var p = Ext.urlEncode({'subWinId':id,'subWinIdBussessId':param});//'&subWinId='+id+'&subWinIdBussessId='+param;
        url = ctx+'/radowAction.do?method=doEvent&pageModel='+url+'&'+p;
        var pjson = {id:id,title:title,maximizable:false, src:url,width:width,parentWinObj:parentScope,
            height:height,closeAction:'close',thisWin:window,param:param,border :false}
        
        Ext.apply(pjson,wincfg);
        
        var newWin_ = newWin(pjson);
        newWin_.show();
        if(!wincfg||(wincfg&&!wincfg.maximized)){//�������
        	
            newWin_.setWidth(width);
            newWin_.setHeight(height);
            newWin_.setPosition(wleft,wtop);
        }

        return newWin_;
    },
    'openWinNomal' : function(id,url,title,width,height,param,ctx,parentScope,wincfg){
		if(!parentScope){
			parentScope = $h.getTopParent();
		}
		
		var pWidth = parentScope.Ext.getBody().getViewSize().width;
		var pHeigth = parentScope.Ext.getBody().getViewSize().height
		
		var wtop = (pHeigth-height)/2;
		var wleft = (pWidth-width)/2;
		
		var p = Ext.urlEncode({'subWinId':id,'subWinIdBussessId':param});//'&subWinId='+id+'&subWinIdBussessId='+param;
		url = ctx+'/radowAction.do?method=doEvent&pageModel='+url+'&'+p;
		var pjson = {id:id,title:title,maximizable:false, src:url,width:width,parentWinObj:parentScope,
				height:height,closeAction:'close',thisWin:window,param:param}
		Ext.apply(pjson,wincfg);
		var newWin_ = newWin(pjson);
		newWin_.show();
		if(!wincfg||(wincfg&&!wincfg.maximized)){//�������
			newWin_.setWidth(width);
			newWin_.setHeight(height);
			newWin_.setPosition(wleft,wtop);
		}
		
		return newWin_;
	},
    'openWinMin' : function(id,url,title,width,height,param,ctx,parentScope,wincfg,isrmb){
        //var cfg = {modal:true,collapsed:false,collapsible:false};
        //Ext.applyIf(wincfg,cfg );
        if(!parentScope){
            parentScope = $h.getTopParent();
        }

        var pWidth = parentScope.Ext.getBody().getViewSize().width;
        var pHeigth = parentScope.Ext.getBody().getViewSize().height

        if(!width||pWidth<width){
            if(isrmb){
                width = pWidth;
            }else{
                width = parseInt(pWidth*0.6);
            }

        }
        if(!height||pHeigth<height){
            if(isrmb){
                height = pHeigth;
            }else{
                height = parseInt(pHeigth*0.8);
            }

        }
        var wtop = (pHeigth-height)/2;
        var wleft = (pWidth-width)/2;
        if(wincfg&&wincfg.top){
            wtop = wtop>wincfg.top?wincfg.top:wtop;
        }
        var p = Ext.urlEncode({'subWinId':id,'subWinIdBussessId':param});//'&subWinId='+id+'&subWinIdBussessId='+param;
        url = ctx+'/radowAction.do?method=doEvent&pageModel='+url+'&'+p;
        var pjson = {id:id,title:title, src:url,width:width,parentWinObj:parentScope,
            height:height,thisWin:window,param:param}
        Ext.apply(pjson,wincfg);
        var newWin_ = newWinMin(pjson);
        newWin_.show();
        if(!wincfg||(wincfg&&!wincfg.maximized)){//�������
            newWin_.setWidth(width);
            newWin_.setHeight(height);
            newWin_.setPosition(wleft,wtop);
        }

        return newWin_;
    },
    'showWindowWithSrc':function(id,url,title,width,height,parentScope,_cfg,isrmb,isnotencode){
    	if(!isnotencode){
    		url = encodeURI(encodeURI(url));
    	}else{
    		url = encodeURI(url);
    	}
        
        if(!parentScope){
            parentScope = $h.getTopParent();
        }
        var p = '&subWinId='+id;
        var pWidth = parentScope.Ext.getBody().getViewSize().width;
        var pHeigth = parentScope.Ext.getBody().getViewSize().height

        if(!width||pWidth<width){
            if(isrmb){
                width = pWidth;
            }else{
                width = parseInt(pWidth*0.6);
            }

        }
        if(!height||pHeigth<height){
            if(isrmb){
                height = pHeigth;
            }else{
                height = parseInt(pHeigth*0.8);
            }

        }
        var wtop = (pHeigth-height)/2;
        var wleft = (pWidth-width)/2;
        var cfg = {id:id,title:title,modal:true,maximizable:true,
            src:url+p,width:width,parentWinObj:parentScope,
            height:height,closeAction:'close',thisWin:window}
        if(_cfg)
            Ext.apply(cfg,_cfg);
        var newWin_ = newWin(cfg);
        newWin_.show();
        newWin_.setWidth(width);
        newWin_.setHeight(height);
        newWin_.setPosition(wleft,wtop);
        return newWin_;
    },
    'showWindowWithSrc2':function(id,url,title,width,height,parentScope,_cfg,isrmb){
        url = encodeURI(encodeURI(url));
        if(!parentScope){
            parentScope = $h.getTopParent();
        }
        var pWidth = parentScope.Ext.getBody().getViewSize().width;
        var pHeigth = parentScope.Ext.getBody().getViewSize().height

        if(!width||pWidth<width){
            if(isrmb){
                width = pWidth;
            }else{
                width = parseInt(pWidth*0.6);
            }

        }
        if(!height||pHeigth<height){
            if(isrmb){
                height = pHeigth;
            }else{
                height = parseInt(pHeigth*0.8);
            }

        }
        var wtop = (pHeigth-height)/2;
        var wleft = (pWidth-width)/2;
        var cfg = {id:id,title:title,modal:true,maximizable:true,
            src:url,width:width,parentWinObj:parentScope,
            height:height,closeAction:'close',thisWin:window}
        if(_cfg)
            Ext.apply(cfg,_cfg);
        var newWin_ = newWin(cfg);
        newWin_.show();
        newWin_.setWidth(width);
        newWin_.setHeight(height);
        newWin_.setPosition(wleft,wtop);
    },
    'showWindowWithSrc3':function(id,url,title,width,height,param,parentScope,_cfg,isrmb){
        url = encodeURI(encodeURI(url));
        if(!parentScope){
            parentScope = $h.getTopParent();
        }
        var p = '?subWinId='+param;
        var pWidth = parentScope.Ext.getBody().getViewSize().width;
        var pHeigth = parentScope.Ext.getBody().getViewSize().height

        if(!width||pWidth<width){
            if(isrmb){
                width = pWidth;
            }else{
                width = parseInt(pWidth*0.6);
            }

        }
        if(!height||pHeigth<height){
            if(isrmb){
                height = pHeigth;
            }else{
                height = parseInt(pHeigth*0.8);
            }

        }
        var wtop = (pHeigth-height)/2;
        var wleft = (pWidth-width)/2;
        var cfg = {id:id,title:title,modal:true,maximizable:true,
            src:url+p,width:width,parentWinObj:parentScope,
            height:height,closeAction:'close',thisWin:window}
        if(_cfg)
            Ext.apply(cfg,_cfg);
        var newWin_ = newWin(cfg);
        newWin_.show();
        newWin_.setWidth(width);
        newWin_.setHeight(height);
        newWin_.setPosition(wleft,wtop);
    },
    'showWindowWithSrcMin':function(id,url,title,width,height,parentScope,_cfg,isrmb){ //tangxj
        if(!parentScope){
            parentScope = $h.getTopParent();
        }
        var p = '&subWinId='+id;
        var pWidth = parentScope.Ext.getBody().getViewSize().width;
        var pHeigth = parentScope.Ext.getBody().getViewSize().height

        if(!width||pWidth<width){
            if(isrmb){
                width = pWidth;
            }else{
                width = parseInt(pWidth*0.6);
            }

        }
        if(!height||pHeigth<height){
            if(isrmb){
                height = pHeigth;
            }else{
                height = parseInt(pHeigth*0.8);
            }

        }
        var wtop = (pHeigth-height)/2;
        var wleft = (pWidth-width)/2;
        var cfg = {id:id,title:title,modal:false,
            src:url+p,width:width,parentWinObj:parentScope,
            height:height,closeAction:'close',thisWin:window}
        if(_cfg)
            Ext.apply(cfg,_cfg);
        var newWin_ = newWinMin(cfg);
        newWin_.show();
        newWin_.setWidth(width);
        newWin_.setHeight(height);
        newWin_.setPosition(wleft,wtop);
    },
    //������ҳ���λ��
    'pos' : function (obj){
		obj=feildIsNull(obj)?{}:obj;
        var tt = feildIsNull(obj.offsetTop)?0:obj.offsetTop;
        var ll = feildIsNull(obj.offsetLeft)?0:obj.offsetLeft;
        while(true){
            if(obj.offsetParent){
                obj = obj.offsetParent;
                tt+=obj.offsetTop;
                ll+=obj.offsetLeft;
            }else{
                return {top:tt,left:ll};
            }
        }
        return tt;
    },

    //�л����༭��
    'changeGridEditor' : function (cfg){
        //cfg  {gridid:'gridid',colIndex:2,dataArray:dataArray}
        var grid = odin.ext.getCmp(cfg.gridid);
        var cm = grid.getColumnModel();
        var headername = cm.getDataIndex(cfg.colIndex);
        //alert(headername);
        eval(headername+'_select=cfg.dataArray');
        var w = cm.getColumnWidth(cfg.colIndex);//17
        grid.getColumnModel().setEditor( cfg.colIndex, $h.newEditor(cfg.dataArray,headername,w,cfg.canOutSelectList,cfg.allowBlank) );

        //alert(w);
        cm.setRenderer( cfg.colIndex, $h.rowRenderer(cfg.gridid,headername) );
    },

    'newEditor' : function (dataArray,header,headerw,canOutSelectList,allowBlank){
        return new Ext.form.ComboBox({
            store : new Ext.data.SimpleStore({
                fields : [ 'key', 'value' ],
                data : dataArray,
                createFilterFn : odin.createFilterFn
            }),
            id:'comboEdit_'+header+'_combo',
            displayField : 'value',
            typeAhead : false,
            mode : 'local',
            triggerAction : 'all',
            editable : true,
            canOutSelectList:canOutSelectList||"false",
            selectOnFocus : true,
            hideTrigger : false,
            expand : function() {
                odin.setListWidth(this);
                Ext.form.ComboBox.prototype.expand.call(this);
            },
            listeners :{
                'focus':function(e,b,c){
                    //alert(e.getWidth());
                    //e.setWidth(headerw-6-17)
                    document.getElementById('comboEdit_'+header+'_combo').style.width=headerw-6-17;//����ڻ���������򱻸������⡣
                    //alert(document.getElementById('comboEdit_'+header).style.width);
                },
                'blur':function(record, index){
                    var field=this;
                    var comboId = field.getId();
                    //var id = comboId.substring(0,comboId.lastIndexOf("_combo"));
                    //alert(id);
                    var store = Ext.getCmp(comboId).store;
                    var canOutSelectList = Ext.getCmp(comboId).canOutSelectList;
                    var length = store.getCount();
                    var isExsist = false;
                    for(i=0;i<length;i++)
                    {
                        var rs = store.getAt(i);
                        if(rs.get('value')==field.getValue()){
                            isExsist = true;
                            //Ext.get(id).value = rs.get('key');
                            var newValue = field.getValue();
                            if(newValue.indexOf('&nbsp;')>0){
                                newValue = newValue.replace(/\d+/,'').replace(/&nbsp;/g,'');
                                Ext.getCmp(comboId).setValue(newValue);
                            }
                            break;
                        }
                        if(rs.get('key')==field.getValue()){
                            document.getElementById(comboId).value = rs.get("value");
                            isExsist = true;
                            break;
                        }
                    }
                    //alert("isExsist:"+isExsist);
                    if(field.getValue()==''){
                        isExsist=true;
                        //Ext.get(id).value="";
                    }
                    if(!isExsist){
                        if(field.getValue()!=""){
                            var msg = "�������ֵû�ж�Ӧ��ƥ�������Ϣ�����������롣";
                            if(field.valueNotFoundText && field.valueNotFoundText!="false"){
                                msg = field.valueNotFoundText;
                            }else if(field.valueNotFoundText && field.valueNotFoundText=="false"){
                                msg = "";
                            }else if(field.canOutSelectList=="true"){
                                msg = "";
                            }
                            odin.cueCheckObj = document.getElementById(comboId);
                            if (msg != "") {
                                if(typeof radow!='undefined' && radow && radow.cm){
                                    //radow.cm.setValid(id,false,msg);
                                }else{
                                    odin.alert(msg);
                                    return false;
                                }
                            }
                        }
                        //Ext.get(id).value = field.getValue();
                    }else{
                        if(typeof radow!='undefined' && radow && radow.cm){
                            if(true != field.noClearValid){
                                //radow.cm.setValid(id,true,'');
                            }
                        }
                    }
                    if(odin.isWorkpf){
                        odin.autoNextElement(document.getElementById(comboId));
                    }
                }

            },
            allowBlank : allowBlank||false,
            fireKey : odin.doAccSpecialkey
        });
    },
    'rowRenderer' : function (gridid,headername) {
        return function(a, b, c, d, e, f){
            var v = odin.doGridSelectColRender(gridid, headername, a, b, c, d, e, f);
            odin.renderEdit(v, b, c, d, e, f);
            return radow.renderAlt(v, b, c, d, e, f, "");
        }

    },
    'getYear' : function(startdate, enddate){
        if (startdate == "" || startdate == null || typeof (startdate) == "undefined") {
            return "";
        }
        if (startdate == "" || startdate == null || typeof (startdate) == "undefined") {
            return "";
        }

        var sd = parseInt(startdate,10);
        var ed = parseInt(enddate,10);

        if(isNaN(sd)||isNaN(ed)){
            return "";
        }
        if(sd.toString().length!=6&&sd.toString().length!=8){
            return "";
        }
        if(ed.toString().length!=6&&ed.toString().length!=8){
            return "";
        }
        if(sd.toString().length==6){
            sd = parseInt(sd.toString() + "01");
        }
        if(ed.toString().length==6){
            ed = parseInt(ed.toString() + "01");
        }
        var btyear = parseInt(ed/10000-sd/10000);
        if(btyear>0){
            return btyear.toString();
        }else{
            return "";
        }


    },
    'changeComboStore' : function(cfg){
        //cfg  {gridid:'gridid',colIndex:2,dataArray:dataArray}
        var pgrid = Ext.getCmp(cfg.gridid);
        var cm = pgrid.getColumnModel();
        var headername = cm.getDataIndex(cfg.colIndex);
        //alert(headername);
        eval(headername+'_select=cfg.dataArray');
        var store = pgrid.initialConfig.cm.config[cfg.colIndex].editor.getStore();
        store.removeAll();
        var length = cfg.dataArray.length;
        for(var i=0;i<length;i++){
            store.add(new Ext.data.Record({key:cfg.dataArray[i][0],value:cfg.dataArray[i][1]}));
        }
    },

    'initGridSort':function(gridId,callback){
        var pgrid = Ext.getCmp(gridId);
        var dstore = pgrid.getStore();
        var ddrow = new Ext.dd.DropTarget(pgrid.container,{
            ddGroup : 'GridDD',
            copy : false,
            notifyDrop : function(dd,e,data){
                //ѡ���˶�����
                var rows = data.selections;
                //�϶����ڼ���
                var index = dd.getDragData(e).rowIndex;
                if (typeof(index) == "undefined"){
                    return;
                }
                var derictup;
                if(rows.length>0){
                    var findex = dstore.indexOf(rows[0]);
                    var lindex = dstore.indexOf(rows[rows.length-1]);
                    if(index>lindex&&index>findex){
                        derictup = false;
                        //alert("����");
                    }else if(index<lindex&&index<findex){
                        derictup = true;
                        //alert("����");
                    }else{
                        return;
                    }
                    //
                }
                //�޸�store

                var selectflag=false;
                for ( i=0; i<rows.length; i++){
                    var rowData = rows[i];
                    //if (!this.copy) dstore.remove(rowData);
                    dstore.insert(index, rowData);
                    if(i>0){
                        selectflag=true;
                    }
                    pgrid.getSelectionModel().selectRow(index,selectflag);
                    if(derictup){
                        index++;
                    }
                }

                pgrid.view.refresh();
                if(typeof callback=='function'){
                    callback(pgrid);
                }

            }
        });
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
    if(disObj.isValid()||disObj.getValue()==''){
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
        if(this.node.attributes.code_leaf==1){//������˫��ѡ��
            document.getElementById(this.node.attributes.formproperty).value=this.node.id;
            document.getElementById(this.node.attributes.formproperty+'_combotree').value=this.node.attributes.selectText
            Ext.getCmp(this.node.attributes.formproperty+'_combotree').collapse();
        }
        this.fireEvent("dblclick", this.node, e);
    }
});
Ext.override(Ext.PagingToolbar, {
    moveFirst : function(){
        this.doLoad(0,true);
    },

    /**
     * Move to the previous page, has the same effect as clicking the 'previous' button.
     */
    movePrevious : function(){
        this.doLoad(Math.max(0, this.cursor-this.pageSize),true);
    },
    moveNext : function(){
        this.doLoad(this.cursor+this.pageSize,true);
    },
    doLoad : function(start,isPageTurning){
        var o = {}, pn = this.getParams();
        if(isPageTurning){
            o["isPageTurning"] = true;

        }
        o[pn.start] = start;
        o[pn.limit] = this.pageSize;
        if(this.fireEvent('beforechange', this, o) !== false){
            this.store.load({params:o});
        }
        delete o["isPageTurning"];
    }
});


function getAgeRender(value) {
    var returnAge;
    if (value == "" || value == null || typeof (value) == "undefined") {
        return returnAge;
    } else {
        var birthYear = value.toString().substring(0, 4);
        var birthMonth = value.toString().substring(4, 6);
        var birthDay = value.toString().substring(6, 8);
        if (birthDay == "" || birthDay == null
            || typeof (birthDay) == "undefined") {
            birthDay = "01";
        }
        d = new Date();
        var nowYear = d.getFullYear();
        var nowMonth = d.getMonth() + 1;
        var nowDay = d.getDate();
        if (nowYear == birthYear) {
            returnAge = 0; // ͬ�귵��0��
        } else {
            var ageDiff = nowYear - birthYear; // ��ֻ��
            if (ageDiff > 0) {
                if (nowMonth == birthMonth) {
                    var dayDiff = nowDay - birthDay;// ��֮��
                    if (dayDiff < 0) {
                        returnAge = ageDiff - 1;
                    } else {
                        returnAge = ageDiff;
                    }
                } else {
                    var monthDiff = nowMonth - birthMonth;// ��֮��
                    if (monthDiff < 0) {
                        returnAge = ageDiff - 1;
                    } else {
                        returnAge = ageDiff;
                    }
                }
            } else {
                returnAge = -1;//�������ڴ��� ���ڽ���
            }
        }
        return returnAge;

    }
}


//���س�������
function getAgeNew(value) {
    var returnAge;
    if (value == "" || value == null || typeof (value) == "undefined") {
        return returnAge;
    } else {
        var birthYear = value.toString().substring(0, 4);
        var birthMonth = value.toString().substring(4, 6);
        var birthDay = value.toString().substring(6, 8);
        if (birthDay == "" || birthDay == null
            || typeof (birthDay) == "undefined") {
            birthDay = "01";
        }
        d = new Date();
        var nowYear = d.getFullYear();
        var nowMonth = d.getMonth() + 1;
        var nowDay = d.getDate();
        if (nowYear == birthYear) {
            returnAge = 0; // ͬ�귵��0��
        } else {
            var ageDiff = nowYear - birthYear; // ��ֻ��
            if (ageDiff > 0) {
                if (nowMonth == birthMonth) {
                    var dayDiff = nowDay - birthDay;// ��֮��
                    if (dayDiff < 0) {
                        returnAge = ageDiff - 1;
                    } else {
                        returnAge = ageDiff;
                    }
                } else {
                    var monthDiff = nowMonth - birthMonth;// ��֮��
                    if (monthDiff < 0) {
                        returnAge = ageDiff - 1;
                    } else {
                        returnAge = ageDiff;
                    }
                }
            } else {
                returnAge = -1;//�������ڴ��� ���ڽ���
            }
        }

        var msg = value.toString().substring(0, 4)+"."+value.toString().substring(4, 6)+"("+returnAge.toString()+"��)";
        return msg;

    }
}


//ģ������б��н���ģ�����idתΪ����
function getTptypeName(value) {
    var tptypeName;
    if (value == "" || value == null || typeof (value) == "undefined") {
        return tptypeName;
    } else {

        if(value == "1"){
            tptypeName = "���";
        }

        if(value == "2"){
            tptypeName = "��׼����";
        }

        if(value == "3"){
            tptypeName = "������";
        }

    }

    return tptypeName;
}


//����ʱ���ʽ�������2001.01
function getTime(value) {
    var returnTime;
    if (value == "" || value == null || typeof (value) == "undefined") {
        returnTime = "";
        return returnTime;
    } else {
        returnTime = value.toString().substring(0, 4)+"."+value.toString().substring(4, 6);
        return returnTime;

    }
}

/**
 * ����У�� 4λ6λ��8λ���������ڸ�ʽ�������Դ��ڵ�ǰ����
 * @param {} value
 * @return {Boolean}
 */
function dateValidateBeforeTadyYF(value){
    /*var bh = value.substring(4,5)
    if(bh == "."){
        value = value.replace(".", "");
    }*/

    var length = value.length;
    if(length==0){
        return true;
    }else if(length==4){
        value = value + "01";
    }
    var rtn = dateValidateF(value);
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
 * ����У�� 6λ��8λ���������ڸ�ʽ�����Դ��ڵ�ǰ����
 * @param {} value
 * @return {Boolean}
 */
function dateValidateF(value){

    var length = value.length;
    if(length==0){
        return true;
    }else if(length!=6&&length!=8){
        return '���ڸ�ʽ����ȷ��ֻ������4λ��6λ��8λ����Ч���ڣ���ȷ��ʽΪ��2008��200808 �� 20080804';
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


var $helper = function(){
    //��ȡָ��form�е����е�<input>����
    function getElements(formId) {
        var form = document.getElementById(formId);
        var elements = new Array();
        var tagElements = form.getElementsByTagName('input');
        for (var j = 0; j < tagElements.length; j++){
            elements.push(tagElements[j]);

        }
        return elements;
    }

    //��ȡ����input�еġ�name,value������
    function inputSelector(element) {
        if (element.checked)
            return [element.name, element.value];
    }

    function input(element) {
        switch (element.type.toLowerCase()) {
            case 'submit':
            case 'hidden':
            case 'password':
            case 'text':
                return [element.name, element.value];
            case 'checkbox':
            case 'radio':
                return inputSelector(element);
        }
        return false;
    }

    //���URL
    function serializeElement(element) {

        var parameter = input(element);

        if (parameter) {
            return parameter;
        }
        return null;
    }

    //���÷���
    function serializeForm(formId) {
        var elements = getElements(formId);
        var queryComponents = {};

        for (var i = 0; i < elements.length; i++) {
            var queryComponent = serializeElement(elements[i]);
            if (queryComponent)
                queryComponents[queryComponent[0]]=queryComponent[1];
        }

        return queryComponents;
    }

    return {
        getFormInfo :function (id){
            var params = serializeForm(id);
            return params;
        },


        'openPageModeWin' : function(url,title,width,height,param){
            var pWidth = screen.availWidth;
            var pHeigth = screen.availHeight;

            if(!width||pWidth<width){
                width = pWidth;
            }
            if(!height||pHeigth<height){
                height = pHeigth;
            }
            var wtop = (pHeigth-height)/2;
            var wleft = (pWidth-width)/2;
            url = contextPath+'/radowAction.do?method=doEvent&pageModel='+url+"&aa="+Math.random();
            var obj = new Object();
            obj['window']=window;
            obj['param']=param;
            obj['title']=title;
            var p = "dialogWidth="+width+"px;dialogHeight="+height+"px;help=no;resizable=no;status=no;center=yes;scroll:no;location:no;toolbar:no;menubar:no;titlebar:no;";
            return window.showModalDialog(url,obj,p);
            //var p = "top="+wtop+",left="+wleft+",width="+width+",height="+height+",menubar=no,scrollbars=no,toolbar=no,status=no,location=no"
            //var neww = window.open(url,title,p);
            //return neww;
        },

        'pos' : function (obj){
            var tt = obj.offsetTop;
            var ll = obj.offsetLeft;
            while(true){
                if(obj.offsetParent){
                    obj = obj.offsetParent;
                    tt+=obj.offsetTop;
                    ll+=obj.offsetLeft;
                }else{
                    return {top:tt,left:ll};
                }
            }
            return tt;
        }




    }
}

var $helper = function(){
    //��ȡָ��form�е����е�<input>����
    function getElements(formId) {
        var form = document.getElementById(formId);
        var elements = new Array();
        var tagElements = form.getElementsByTagName('input');
        for (var j = 0; j < tagElements.length; j++){
            elements.push(tagElements[j]);

        }
        return elements;
    }

    //��ȡ����input�еġ�name,value������
    function inputSelector(element) {
        if (element.checked)
            return [element.name, element.value];
    }

    function input(element) {
        switch (element.type.toLowerCase()) {
            case 'submit':
            case 'hidden':
            case 'password':
            case 'text':
                return [element.name, element.value];
            case 'checkbox':
            case 'radio':
                return inputSelector(element);
        }
        return false;
    }

    //���URL
    function serializeElement(element) {

        var parameter = input(element);

        if (parameter) {
            return parameter;
        }
        return null;
    }

    //���÷���
    function serializeForm(formId) {
        var elements = getElements(formId);
        var queryComponents = {};

        for (var i = 0; i < elements.length; i++) {
            var queryComponent = serializeElement(elements[i]);
            if (queryComponent)
                queryComponents[queryComponent[0]]=queryComponent[1];
        }

        return queryComponents;
    }

    return {
        getFormInfo :function (id){
            var params = serializeForm(id);
            return params;
        },


        'openPageModeWin' : function(url,title,width,height,param){
            var pWidth = screen.availWidth;
            var pHeigth = screen.availHeight;

            if(!width||pWidth<width){
                width = pWidth;
            }
            if(!height||pHeigth<height){
                height = pHeigth;
            }
            var wtop = (pHeigth-height)/2;
            var wleft = (pWidth-width)/2;
            url = contextPath+'/radowAction.do?method=doEvent&pageModel='+url+"&aa="+Math.random();
            var obj = new Object();
            obj['window']=window;
            obj['param']=param;
            obj['title']=title;
            var p = "dialogWidth="+width+"px;dialogHeight="+height+"px;help=no;resizable=no;status=no;center=yes;scroll:no;location:no;toolbar:no;menubar:no;titlebar:no;";
            return window.showModalDialog(url,obj,p);
            //var p = "top="+wtop+",left="+wleft+",width="+width+",height="+height+",menubar=no,scrollbars=no,toolbar=no,status=no,location=no"
            //var neww = window.open(url,title,p);
            //return neww;
        },

        'pos' : function (obj){
            var tt = obj.offsetTop;
            var ll = obj.offsetLeft;
            while(true){
                if(obj.offsetParent){
                    obj = obj.offsetParent;
                    tt+=obj.offsetTop;
                    ll+=obj.offsetLeft;
                }else{
                    return {top:tt,left:ll};
                }
            }
            return tt;
        }




    }
    
    function getOs()  
    {  
        var OsObject = "";  
       if(navigator.userAgent.indexOf("MSIE")>0) {  
            return "MSIE";  
       }  
       if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){  
            return "Firefox";  
       }  
       if(isSafari=navigator.userAgent.indexOf("Safari")>0) {  
            return "Safari";  
       }   
       if(isCamino=navigator.userAgent.indexOf("Camino")>0){  
            return "Camino";  
       }  
       if(isMozilla=navigator.userAgent.indexOf("Gecko/")>0){  
            return "Gecko";  
       }  
         
    }  
    function alertActiveX(){
    	if(this.getOs()=="MSIE"){
    	    alert("�޷�����Office������ȷ�����Ļ����Ѱ�װ��Office���ѽ���ϵͳ��վ�������뵽IE������վ���б��У�"); 
    	    return; 
    	}
    }
    
    
    
   
}


function ShowCellCover(elementId, titles, msgs)
{	
	Ext.MessageBox.buttonText.ok = "�ر�";
	if(elementId.indexOf("start") != -1){
	
		Ext.MessageBox.show({
			title:titles,
			msg:msgs,
			width:300,
	        height:300,
			closable:false,
			modal:true,
			progress:true,
			wait:true,
			animEl: 'elId',
			increment:5, 
			waitConfig: {interval:150}
		});
	}else if(elementId.indexOf("success") != -1){
			Ext.MessageBox.show({
				title:titles,
				msg:msgs,
				width:300,
		        height:300,
		        modal:true,
				closable:true,
				buttons: Ext.MessageBox.OK
			});
	}else if(elementId.indexOf("failure") != -1){
			Ext.MessageBox.show({
				title:titles,
				msg:msgs,
				width:300,
				modal:true,
		        height:300,
				closable:true,
				buttons: Ext.MessageBox.OK		
			});
	}else {
			Ext.MessageBox.show({
				title:titles,
				msg:msgs,
				width:300,
				modal:true,
		        height:300,
				closable:true,
				buttons: Ext.MessageBox.OK		
			});
		}
}
