var base = {
	/**
	 * ��ʾ���غ���
	 * @param {Object} objId Ҫ�����Ķ���ID
	 * @param {Object} isDisplay ���Ϊtrue����ʾ��Ϊfalse�����أ����δ����������෴����
	 */
    toggleDisplay: function(objId,isDisplay){
		var obj;
		if (typeof objId == 'string') {
			obj = document.getElementById(objId);
		}else{
			obj = objId;
		}
		if (typeof isDisplay == "undefined") {
			var display = obj.style.display;
			if (display != "none") {
				display = "none";
			}
			else {
				display = "";
			}
		}else if(isDisplay == true){
			display = "";
		}else if(isDisplay == false){
			display = "none";
		}
        obj.style.display = display;
    },
	/**
	 * ��һ�����������toggleDisplay����
	 * @param {Object} arrayObj
	 * @param {Object} isDisplay
	 */
	toggleArrayDisplay:function(arrayObj,isDisplay){
		if(typeof arrayObj == 'object' && arrayObj instanceof Array){
			for(i=0;i<arrayObj.length;i++){
				base.toggleDisplay(arrayObj[i],isDisplay);
			}
		}else{
			return false;
		}
	},
	/***
	 * ������ʽ�����Զ��л�
	 * @param {Object} objId
	 * @param {Object} class1
	 * @param {Object} class2
	 */
	toggleClass:function(objId,class1,class2){
		var obj;
		if (typeof objId == 'string') {
			obj = document.getElementById(objId);
		}else{
			obj = objId;
		}
		if(obj.className == class1){
			obj.className = class2;
		}else{
			obj.className = class1;
		}
	},
	/**
	 * �رպ͵�ǰdiv���ڵ��¸�div
	 * @param {Object} object
	 */
	closeNextDiv:function(object){
		var objEle = odin.ext.get(object);
		var ele = objEle.findParent(".box-content-area");
		ele.style.display = "none";
	},
	/**
	 * ��ʼ����ߵ��۵��¼�
	 */
	initToggleFunc:function(){
		var elements = odin.ext.query(".toggleFunc");
		for(i = 0; i<elements.length;i++){
			var ele = elements[i];
			ele.attachEvent?ele.attachEvent("onclick",base.toggleFunc):ele.addEventListener("onclick",base.toggleFunc,"");
		}
	},
	/**
	 * �����۵�����
	 */
	toggleFunc:function(){
		var eveObj = event.srcElement;
		var obj = odin.ext.get(eveObj);
		var p = obj.findParent(".box-func-area-title");
		var toggleObj = odin.ext.get(p).next(".box-func-body");
		base.toggleDisplay(toggleObj);
		base.toggleClass(eveObj,'toggleFunc','toggleFunc2')
	},
	/**
	 * ʹ��js���window.open
	 * @param {Object} cfg
	 */
	open:function(cfg){
		var win = window.open(cfg.url,cfg.title,'height='+(typeof cfg.height=='undefined'?'1000':cfg.height)+', width='+(typeof cfg.width=='undefined'?'800':cfg.width)+', top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');
	},
	query:function(url,params){
		var res = odin.Ajax.request(url,params,odin.ajaxSuccessFunc,odin.ajaxSuccessFunc,false,false);
		var resObj = odin.ext.decode(res.responseText);
		return resObj;
	},
	getCodeText:function(codeType,codeValue){
		//�������ȡ������Ϣ
		var url = contextPath + "/public/publicAction.do?method=getCodeText";
		var params = {'code':codeValue,'codeType':codeType};
		var resObj = base.query(url,params);
		if(resObj.messageCode=="0"){
			return  resObj.data;
		}
	},
	/**
	 * ��data�е����ݷŵ�form��ȥ
	 * @param {Object} fm
	 * @param {Object} dataItemArray
	 * @param {Object} data
	 */
	initFormData:function(fm,dataItemArray,data){
		for(index=0;index<dataItemArray.length;index++){
			var item = dataItemArray[index];
			if(item.type=='select'){
				odin.setSelectValue(item.name,eval('data.'+item.name));
			}else{
				eval('fm.'+item.name).value = eval('data.'+item.name);
			}
		}
	},
    /**
	 * �ҵ�radio��nameΪradioName��ֵΪnewValue�Ķ��󣬲�����ѡ��
	 * ����������Ϊ��ѡ��
	 * @param radioName
	 * @param newValue
	 */
	setCheckedValue: function(radioName, newValue){
		if (!radioName) 
			return;
		var radios = document.getElementsByName(radioName);
		for (var i = 0; i < radios.length; i++) {
			radios[i].checked = false;
			if (radios[i].value == newValue.toString()) {
				radios[i].checked = true;
			}
		}
	},
	/**
	 * ��ȡ����ΪradioName��radio��ѡ�е�ֵ
	 * @param radioName
	 */
	getRadioValue: function(radioName){//�õ�radio��ֵ      
		var obj = document.getElementsByName(radioName);
		for (var i = 0; i < obj.length; i++) {
			if (obj[i].checked) {
				return obj[i].value;
			}
		}
	},
	/**
	 * ����ɹ����ͨ�ú���������������ҳ�����ҳ�����
	 * @param {Object} res
	 */
	saveSuccess:function(res){
		parent.odin.alert(res.mainMessage);	
		parent.base.refreshAccountInfo(parent.base.accountId);
		parent.odin.ext.getCmp('win').hide();
	},
	/**
	 * ͨ�õĻ�ȡ���������������Ϣ�����objId��Ϊ�գ���������������������򷵻��������������
	 * @param {Object} params
	 * @param {Object} objId
	 */
	getSelectData:function(params,objId){
		var req = odin.commonQuery(params,odin.ajaxSuccessFunc,null,false,false);  //�����ݿ���в�ѯ��λ���
		var data = odin.ext.decode(req.responseText).data.data;
		var selData="[";   
		for(var i=0;i<data.length;i++){
			selData=selData+"{key:'"+data[i].key+"',value:'"+data[i].value+"'}";
			if(i!=data.length-1)
				selData=selData+",";
		}	
		selData +="]";
		var myObject = eval('(' + selData + ')');     //��json�ַ���ת��Ϊjson����
		if(typeof objId != "undefined" && objId!=""){
			odin.reSetSelectData(objId,myObject);
		}else{
			return myObject; 
		}
	},
	/**
	 * ��text���г����ϵĿ��ƣ�ʹ�����len����������ʱ���н�ȡ������<a href=javascript:void(0) title="text">lentext</a>
	 * @param {Object} text
	 * @param {Object} len
	 */
	getLenTextHtml:function(text,len){
		if(text.length<=len){
			return text;
		}else{
			return '<a href="javascript:void(0)" title="'+text+'">'+text.substr(0,len)+'</a>';
		}
	},
	/**
	 * ��ȡ��ǰ�û��Ƿ���Ȩ���ʴ˹���
	 * @param {Object} url
	 */
	isPermission:function(url){
		var url = contextPath + "/public/publicAction.do?method=isPermission";
		var params = {'url':url};
		res = odin.Ajax.request(url,params,odin.ajaxSuccessFunc,odin.ajaxSuccessFunc,false,false);
		var resObj = odin.ext.decode(res.responseText);
		if (resObj.messageCode == '0') {
			return true;
		}else{
			return false;
		}
	}
};
String.prototype.trim = function() {   //ȥ���ҿո�
	return this.replace(/(^\s*)|(\s*$)/g, "");   
}
