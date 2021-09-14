/***
 * �޸ļ�¼
 * cueversion:6.0.1 �޸�grid������������ɫ�Ĺ���
 * cueversion:6.0.2 ���ӵ���firereport�Ĵ�ӡ����
 * cueversion:6.0.3 setBillPrint2�Ĵ�ӡ�����޸�
 */
odin.ext.namespace('radow.renderer'); 
radow.renderer = {
	/**
	 * ͨ��ɾ��JS ����dogriddelete�Զ����¼�
	 * @param {Object} value
	 * @param {Object} params
	 * @param {Object} record
	 * @param {Object} rowIndex
	 * @param {Object} colIndex
	 * @param {Object} ds
	 */
	commGridColDelete:function(value, params, record, rowIndex, colIndex, ds){
		//alert(value);
		return "<img src='"+contextPath+"/images/qinkong.gif' title='ɾ����' onclick=\"radow.doEvent('dogriddelete','"+value+"');\">";
	},
	commUserfulForGrid:function(value, params, record, rowIndex, colIndex, ds){
		if(value=='1'){
			return "<img src='"+contextPath+"/images/right1.gif'>";
		}else if(value=='0'){
			return "<img src='"+contextPath+"/images/wrong.gif'>";
		}
	},
	/**
	 * ͨ���޸�JS ����dogridedit�Զ����¼�
	 * @param {Object} value
	 * @param {Object} params
	 * @param {Object} record
	 * @param {Object} rowIndex
	 * @param {Object} colIndex
	 * @param {Object} ds
	*/
	commEditForGrid:function(value, params, record, rowIndex, colIndex, ds){
		return "<img src='"+contextPath+"/images/update.gif' title='�޸ģ�' onclick=\"radow.doEvent('dogridedit','"+value+"');\">";
	},
	/**
	 * ͨ��ɾ��JS ����dogridgrant�Զ����¼�
	 * @param {Object} value
	 * @param {Object} params
	 * @param {Object} record
	 * @param {Object} rowIndex
	 * @param {Object} colIndex
	 * @param {Object} ds
	 */
	commGrantForGrid:function(value, params, record, rowIndex, colIndex, ds){
		return "<img src='"+contextPath+"/images/icon_photodesk.gif'  onclick=\"radow.doEvent('dogridgrant','"+value+"');\">";
	},
	/**
	 * ���������ʾ��Ч��
	 */
	/*renderAlt:function(value) {
		if (value == null) {
			return;
		}
		return "<a title='" + value + "'>" + value + "</a>";
	},*/
	/**
	 * ���������ʾ��Ч��
	 * @param {} value
	 * @param {} params
	 * @param {} record
	 * @param {} rowIndex
	 * @param {} colIndex
	 * @param {} ds
	 * @param {} colorExp
	 */
	renderAlt:function(value, params, record, rowIndex, colIndex, ds, colorExp) {
		if (value == null) {
			return;
		}
		value = value.toString();
		if (value.indexOf("</") != -1 || value.indexOf("/>") != -1 || value.indexOf("<img") != -1) {
			return value;
		}
		var value2 = value;
		if (colorExp != null && colorExp != '') {
			value2 = radow.renderer.renderColor(value, params, record, rowIndex, colIndex, ds, colorExp);
		}
		return '<span title=\'' + value.replace(/'/g, '&#39;') + '\'>' + value2 + '</span>';
	},
	/**
	 * ��ɫ��Ⱦ
	 * @param {} value
	 * @param {} params
	 * @param {} record
	 * @param {} rowIndex
	 * @param {} colIndex
	 * @param {} ds
	 * @param {} colorExp ��ɫ���ʽ����(aaa001==1)?'red':(aaa001==2)?'rgb(0,0,0)':''
	 * @return {}
	 */
	renderColor:function(value, params, record, rowIndex, colIndex, ds, colorExp) {
		if (!ds || colorExp=='' || colorExp=='null') {// dsΪ�գ�������
			return value;
		}
		var gridId = ds.baseParams.div;
		if(!gridId){
			gridId=ds.baseParams.cueGridId;
		}
		var gridColumnModel = Ext.getCmp(gridId).getColumnModel();
		var field = gridColumnModel.getDataIndex(colIndex);
		for (var i = 0; i < gridColumnModel.getColumnCount(); i++) {
			var dataIndex = gridColumnModel.getDataIndex(i);
			if (dataIndex != null && dataIndex != '') {
				eval("var " + dataIndex + " = record.get('" + dataIndex + "')");
			}
		}
		var color = eval(colorExp);
		return '<span style="color:' + color + ';">' + value + '</span>';
	},
	/**
	 * ��ʽ��Ǯ������
	 * @param {} value
	 * @return {}
	 */
	renderMoney:function(value) {
		if (value == 'undefined' || value == null) {
			value = 0;
		}
		value = Number(new String(value).replace(/,/g, ""));
		return radow.renderer.formatMoney(new String(parseFloat(value).toFixed(2)));
	},
	/**
	 * ��ʽ��Ǯ
	 */
	formatMoney:function(s) {
		s = s.toString();
		var flag = "";
		if (s.substring(0, 1) == "-") {
			flag = "-";
			s = s.substring(1);
		}
		if (/[^0-9\.]/.test(s))
			return "invalid value:" + s;
		s = s.replace(/^(\d*)$/, "$1.");
		s = (s + "00").replace(/(\d*\.\d\d)\d*/, "$1");
		s = s.replace(".", ",");
		var re = /(\d)(\d{3},)/;
		while (re.test(s))
			s = s.replace(re, "$1,$2");
		s = s.replace(/,(\d\d)$/, ".$1");
		s = s.replace(/^\./, "0.");
		s = flag + s;
		return s;
	},
	/**
	 * У�����ֵ�����
	 */
	isYM:function(value) {
		value = value.toString();
		if (value.length != 6) {
			return "���ȱ���Ϊ6λ��";
		}
		if (value.substr(4) > 12) {
			return "�·ݲ��ܴ���12��";
		}
		if (value.substr(4) == 0) {
			return "�·ݲ���Ϊ00��";
		}
		return true;
	},
	/**
	 * ��ʾ�� �ٷֱȻ�ǰٷֱ� <1��>-1Ϊ�ٷֱȣ�>1��<-1Ϊ�ǰٷֱ�
	 */
	renderPercentOrNot:function(value, params, record, rowIndex, colIndex, ds) {
		if (value < 1 && value > -1) {
			return renderPercent(value, params, record, rowIndex, colIndex, ds);
		} else {
			return value;
		}
	}
};
/**
 * ��ʾ�� �ٷֱ�
 */
function renderPercent(value, params, record, rowIndex, colIndex, ds) {
	if (value == null) {
		return "0%";
	}
	try {
		return accMul(value, 100) + "%";
	} catch (e) {
		return "invalid value:" + value;
	}
}
/**
 * �˷������������õ���ȷ�ĳ˷���� ˵����javascript�ĳ˷������������������������˵�ʱ���Ƚ����ԡ�����������ؽ�Ϊ��ȷ�ĳ˷������
 * ���ã�accMul(arg1,arg2) ����ֵ��arg1����arg2�ľ�ȷ���
 */
function accMul(arg1, arg2) {
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length
	} catch (e) {
	}
	try {
		m += s2.split(".")[1].length
	} catch (e) {
	}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
}
/**
 * ���ݴ�ӡ2

function setBillPrint2(reportlet, params, setup, printmode,preview) {
	// odin.billPrint(repid,queryName,param,preview);
	if(typeof preview != 'undefined' &&(preview==true || preview =='true' || preview=='1')){
		preview = "1";
	}else{
		preview = "0";
	}
	var url = contextPath + "/common/billPrint2Action.do?reportlet=" + reportlet + "&params=" + params.replace(/%/g, "��") + "&setup=" + setup + "&printmode=" + printmode +"&preview="+preview;
	var windowId = "win_pup";	
	var pupWindow = odin.ext.getCmp(windowId);
	pupWindow.setTitle("��ӡ״̬"); // ����
	if(preview=="1"){
		pupWindow.setSize(document.body.clientWidth*0.96,document.body.clientHeight*0.9);
	}else{
		pupWindow.setSize(280, 160); // ��� �߶�
	}
	odin.showWindowWithSrc(windowId, url, null);
	//if (reflushAfterSavePrint != null && reflushAfterSavePrint != "") { // �����Ĵ�ӡ����ӡ���ͺ��ˢ��
	//	reflush();
	//}
	// showWindowWithSrc(windowId, url, null);
	// Ext.getCmp(windowId).hide();
}
*/


function setBillPrint2(reportlet, params, setup, printmode,preview) {
	if(typeof preview != 'undefined' &&(preview==true || preview =='true' || preview=='1')){
		preview = "1";
	}else{
		preview = "0";
	}
	var url = contextPath + "/common/billPrint2Action.do?reportlet=" + reportlet + "&params=" + params.replace(/%/g, "��") + "&setup=" + setup + "&printmode=" + printmode +"&preview="+preview;
	var windowId = "win_pup";	
	var pupWindow = odin.ext.getCmp(windowId);
	pupWindow.setTitle("��ӡ״̬"); 
	var width="";
	var height="";
	if(preview=="1"){
		//pupWindow.setSize(document.body.clientWidth*0.96,document.body.clientHeight*0.9);
		width=document.body.clientWidth*0.96;
		height=document.body.clientHeight*0.9;
		//pupWindow.setSize(600, 400); 
	}else{
		//pupWindow.setSize(280, 160); 
		//pupWindow.setSize(500, 400); 
		width=500;
		height=400;
	}
	odin.showWindowWithSrc(windowId, url, null);
	
	//window.showModalDialog(url,null,"dialogWidth="+width+"px;dialogHeight="+height+"px");
}



function setBillPrint2_ontop(reportlet, params, setup, printmode,preview) {
	if(typeof preview != 'undefined' &&(preview==true || preview =='true' || preview=='1')){
		preview = "1";
	}else{
		preview = "0";
	}
	var url = contextPath + "/common/billPrint2Action.do?reportlet=" + reportlet + "&params=" + params.replace(/%/g, "��") + "&setup=" + setup + "&printmode=" + printmode +"&preview="+preview;
	//var windowId = "win_pup";	
	var windowId = "win_rep";	
	var pupWindow = parent.odin.ext.getCmp(windowId);
	pupWindow.setTitle("��ӡ״̬"); 
	var width="";
	var height="";
	if(preview=="1"){
		//pupWindow.setSize(document.body.clientWidth*0.96,document.body.clientHeight*0.9);
		width=document.body.clientWidth*0.96;
		height=document.body.clientHeight*0.9;
		//pupWindow.setSize(600, 400); 
	}else{
		//pupWindow.setSize(280, 160); 
		//pupWindow.setSize(500, 400); 
		width=500;
		height=400;
	}
	//parent.odin.openWindow(windowId,'�Ǽ���Ϣ¼��',url,width,height);
	
	parent.odin.showWindowWithSrc(windowId, url, null);
	
	//window.showModalDialog(url,null,"dialogWidth="+width+"px;dialogHeight="+height+"px");
}