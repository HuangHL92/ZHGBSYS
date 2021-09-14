var base = {
	/**
	 * 显示隐藏函数
	 * @param {Object} objId 要操作的对象ID
	 * @param {Object} isDisplay 如果为true则显示，为false则隐藏，如果未定义则进行相反操作
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
	 * 对一个数组对象做toggleDisplay操作
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
	 * 两个样式进行自动切换
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
	 * 关闭和当前div相邻的下个div
	 * @param {Object} object
	 */
	closeNextDiv:function(object){
		var objEle = odin.ext.get(object);
		var ele = objEle.findParent(".box-content-area");
		ele.style.display = "none";
	},
	/**
	 * 初始化左边的折叠事件
	 */
	initToggleFunc:function(){
		var elements = odin.ext.query(".toggleFunc");
		for(i = 0; i<elements.length;i++){
			var ele = elements[i];
			ele.attachEvent?ele.attachEvent("onclick",base.toggleFunc):ele.addEventListener("onclick",base.toggleFunc,"");
		}
	},
	/**
	 * 处理折叠功能
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
	 * 使用js里的window.open
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
		//查代码表获取代码信息
		var url = contextPath + "/public/publicAction.do?method=getCodeText";
		var params = {'code':codeValue,'codeType':codeType};
		var resObj = base.query(url,params);
		if(resObj.messageCode=="0"){
			return  resObj.data;
		}
	},
	/**
	 * 将data中的数据放到form里去
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
	 * 找到radio其name为radioName，值为newValue的对象，并将其选中
	 * 其它对象设为不选中
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
	 * 获取名字为radioName的radio且选中的值
	 * @param radioName
	 */
	getRadioValue: function(radioName){//得到radio的值      
		var obj = document.getElementsByName(radioName);
		for (var i = 0; i < obj.length; i++) {
			if (obj[i].checked) {
				return obj[i].value;
			}
		}
	},
	/**
	 * 保存成功后的通用函数，其必须是最顶层页面的子页面调用
	 * @param {Object} res
	 */
	saveSuccess:function(res){
		parent.odin.alert(res.mainMessage);	
		parent.base.refreshAccountInfo(parent.base.accountId);
		parent.odin.ext.getCmp('win').hide();
	},
	/**
	 * 通用的获取下拉所需的数据信息，如果objId不为空，则将数据填到该下拉组件里，否则返回下拉所需的数据
	 * @param {Object} params
	 * @param {Object} objId
	 */
	getSelectData:function(params,objId){
		var req = odin.commonQuery(params,odin.ajaxSuccessFunc,null,false,false);  //从数据库表中查询单位类表
		var data = odin.ext.decode(req.responseText).data.data;
		var selData="[";   
		for(var i=0;i<data.length;i++){
			selData=selData+"{key:'"+data[i].key+"',value:'"+data[i].value+"'}";
			if(i!=data.length-1)
				selData=selData+",";
		}	
		selData +="]";
		var myObject = eval('(' + selData + ')');     //将json字符串转换为json对象
		if(typeof objId != "undefined" && objId!=""){
			odin.reSetSelectData(objId,myObject);
		}else{
			return myObject; 
		}
	},
	/**
	 * 对text进行长度上的控制，使其最多len长，当超出时进行截取，返回<a href=javascript:void(0) title="text">lentext</a>
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
	 * 获取当前用户是否有权访问此功能
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
String.prototype.trim = function() {   //去左右空格
	return this.replace(/(^\s*)|(\s*$)/g, "");   
}
