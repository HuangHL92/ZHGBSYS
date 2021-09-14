var Utils = {
	// 判断一个对象是不是数组
	isArray : function(o) {
        return Object.prototype.toString.call(o) === '[object Array]';
    },
	// 克隆一个对象，如果转递了newPropertyPrefix参数，把其作为属性前缀
	cloneObject: function(obj, newPropertyPrefix) {
		if (obj == null) return null;
		if (typeof obj === "object") {
			if (Utils.isArray(obj)) {
				var newArr = [];
				for (var i = 0; i < obj.length; i++) newArr.push(obj[i]);
				return newArr;
			} else {
				var newObj = {};
				for (var key in obj) {
					var newKey = newPropertyPrefix ? newPropertyPrefix + '.' + key : key;
					newObj[newKey] = this.cloneObject(obj[key]);
				}
				return newObj;
			}
		} else {
			return obj;
		}
	},
	toVarString: function (str) {
		if (!str) return '';
		
		str = str.replace(/\'/g, "''");
		str = str.replace(/\"/g, "\"\"");
		str = str.replace(/\n/g, "");
		str = str.replace(/\r/g, "");
		return str;
	},	
	/**
	 * 编码字符串
	 * @param v
	 * @return
	 */
	encode:function encode(v){//如果包含中文就escape,避免重复escape)
	   return escape(v).replace(/\+/g, '%2B').replace(/\"/g,'%22').replace(/\'/g, '%27').replace(/\//g,'%2F');
	},
	/**
	 * 解码字符串
	 * @param v
	 * @return
	 */
	decode:function decode(v){//如果包含中文就escape,避免重复escape)
	   return unescape(v);
	},
	/**
	 * 重设窗体满屏
	 * @return
	 */
	resize:function resize(){
		window.moveTo(0,0);
		window.resizeTo(window.screen.availWidth,window.screen.availHeight);
	},
	/**
	 * 打开窗体
	 * @param url 路径
	 * @param name 新窗体名册
	 * @param iWidth 新窗体宽度
	 * @param iHeight 新窗体高度
	 * @return
	 */
	openwindow:function openwindow(url,name,iWidth,iHeight){
		var url; //转向网页的地址;
		var name; //网页名称，可为空;
		var iWidth; //弹出窗口的宽度;
		var iHeight; //弹出窗口的高度;
		var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
		window.open(url,name,'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
	},
	/**
	 * 判断是否包含中文（也包含日文和韩文） 
	 */ 
	isChineseChar:function isChineseChar(str){        
		var reg = /[\u4E00-\u9FA5\uF900-\uFA2D]/;     
		return reg.test(str);   
	},
	/**
	 * 同理，是否还有全角符号的函数   
	 */ 	  
	isFullwidthChar:function isFullwidthChar(str){      
		var reg = /[\uFF00-\uFFEF]/;      
		return reg.test(str);   
	} ,
	/**
	 *  任意深度转向本页面
	 *  用途:避免在其他窗口里出现登录窗口
	 */ 
	toLogin : function(){
		if(window.top != window.self){
			window.top.location = window.self.location;
		}
	},
	//处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外  
	banBackSpace : function (e){     
	    var ev = e || window.event;//获取event对象     
	    var obj = ev.target || ev.srcElement;//获取事件源     
	      
	    var t = obj.type || obj.getAttribute('type');//获取事件源类型    
	      
	    //获取作为判断条件的事件类型  
	    var vReadOnly = obj.getAttribute('readonly');  
	    var vEnabled = obj.getAttribute('enabled');  
	    //处理null值情况  
	    vReadOnly = (vReadOnly == null) ? false : vReadOnly;  
	    vEnabled = (vEnabled == null) ? true : vEnabled;  
	      
	    //当敲Backspace键时，事件源类型为密码或单行、多行文本的，  
	    //并且readonly属性为true或enabled属性为false的，则退格键失效  
	    var flag1=(ev.keyCode == 8 && (t=="password" || t=="text" || t=="textarea")   
	                && (vReadOnly==true || vEnabled!=true))?true:false;  
	     
	    //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效  
	    var flag2=(ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea")  
	                ?true:false;          
	      
	    //判断  
	    if(flag2){  
	        return false;  
	    }  
	    if(flag1){     
	        return false;     
	    }     
	} ,
	isIdCard:function(pId){
		//检查身份证号码 Go_Rush(阿舜) from http://ashun.cnblogs.com
		var arrVerifyCode = [1,0,'x',9,8,7,6,5,4,3,2];
		var Wi = [7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2];
		var Checker = [1,9,8,7,6,5,4,3,2,1,1];
		if(pId.length != 15 && pId.length != 18)    return "身份证号共有 15 码或18位";
		var Ai=pId.length==18 ?  pId.substring(0,17)   :   pId.slice(0,6)+"19"+pId.slice(6,16);
		if (!/^\d+$/.test(Ai))  return "身份证除最后一位外，必须为数字！";
		var yyyy=Ai.slice(6,10) ,  mm=Ai.slice(10,12)-1  ,  dd=Ai.slice(12,14);
		var d=new Date(yyyy,mm,dd) ,  now=new Date();
		var year=d.getFullYear() ,  mon=d.getMonth() , day=d.getDate();
		if (year!=yyyy || mon!=mm || day!=dd || d>now || year<1940) return "出生日期输入错误！";
		// 2012-03-13 注释开始
		//for(var i=0,ret=0;i<17;i++)  ret+=Ai.charAt(i)*Wi[i];    
		//Ai+=arrVerifyCode[ret %=11];     
		//return (pId.length ==18 && pId != Ai) ? "身份证输入错误！": Ai ;   
		// 2012-03-13 注释结束
		//return pId.length;// 2012-03-13修改
		return pId;
	},
	getBirthdayBy:function(idCard){
	    if (isNaN(this.isIdCard(idCard))) return "错误的身份证号码";   
	    var id=String(idCard);
	    //var birthday=(new Date(id.slice(6,10) , id.slice(10,12)-1 , id.slice(12,14))).toLocaleDateString(); 
	   // return birthday;
	    if(id.length == 15){
	    	var tmpStr=id.substring(6,12);
    	    tmpStr= "19" + tmpStr;
    	    tmpStr= tmpStr.substring(0,4) + "." + tmpStr.substring(4,6);// + "." + tmpStr.substring(6);
	    	tmpStr.substring(0,4) + "-" + tmpStr.substring(4,6);
	    }else if(id.length == 18){
	    	return (id.slice(6,10)) +"."+ (id.slice(10,12));// +"."+ (id.slice(12,14));
	    }else{
	    	return "";
	    }
	    /*
	     * var arr=[null,null,null,null,null,null,null,null,null,null,null,"北京","天津","河北","山西","内蒙古"
	             ,null,null,null,null,null,"辽宁","吉林","黑龙江",null,null,null,null,null,null,null,"上海"
	             ,"江苏","浙江","安微","福建","江西","山东",null,null,null,"河南","湖北","湖南","广东","广西","海南"
	             ,null,null,null,"重庆","四川","贵州","云南","西藏",null,null,null,null,null,null,"陕西","甘肃"
	             ,"青海","宁夏","新疆",null,null,null,null,null,"台湾",null,null,null,null,null,null,null,null
	             ,null,"香港","澳门",null,null,null,null,null,null,null,null,"国外"];
	       prov=arr[id.slice(0,2)];// 获取省份
	       sex=id.slice(14,17)%2? "男" : "女" // 获取性别
	     */
	},
	setNullToEmpty:function(obj){
		if(obj==null || obj =="null"){
			if(typeof(obj) == "string"){
				return "";
			}else if(typeof(obj) == "number"){
				return 0;
			}else if(typeof(obj) == "boolean"){
				return false;
			}else if(typeof obj == "object" || typeof obj == "function" || typeof obj == "undefined"){
				return "";
			}
		}
		return obj;
	},
	htmlEncode: function(str) {	
		if (str == null) return "";
		var s = "";   
		if (str.length == 0) return "";   
		s = str.replace(/&/g, "&gt;");   
		s = s.replace(/</g, "&lt;");   
		s = s.replace(/>/g, "&gt;");   
		s = s.replace(/ /g, "&nbsp;");   
		s = s.replace(/\'/g, "&#39;");   
		s = s.replace(/\"/g, "&quot;");   
		s = s.replace(/(\r|\n)/g, "<br/>");   
		return s;   
	} 
	,toShortDate: function(str) {
		if (str) return str.substr(0, 11);
		else return str;
	},
	toDate: function(str) {
		if (str == null) return "";
		var year = str.substr(0, 4);
		var month = str.substr(4, 2);
		if (str.length > 6) {
			var day = str.substr(6, 2);
			return year + '.' + month + '.' + day;
		}
		else {
			return year + '.' + month;
		}
	},
	getNowShortDate: function() {
		var now = new Date();
		var year = now.getFullYear();
		var mon = now.getMonth() + 1;
		var month = mon < 10 ? '0' + mon : '' + mon;
		var day = now.getDate() < 10 ? '0' + now.getDate() : '' + now.getDate();
		return year + '-' + month + '-' + day;
	},
	getCurrentYear: function() {
		var now = new Date();
		var year = now.getFullYear();
		return year;
	},
    getDayDiff: function (fromDate, toDate) {
		var diff = parseInt((toDate - fromDate)  /  1000  /  60  /  60  / 24);
		if (diff < 0) diff--;
		else diff++;				
		return diff;
	}
	,
	getSeasonFirstDay: function(year, season) {
		if (season == 1) {
			return new Date(year, 0, 1);
		}
		else if (season == 2) {
			return new Date(year, 3, 1);
		}
		else if (season == 3) {
			return new Date(year, 6, 1);
		}
		else {
			return new Date(year, 9, 1);
		}
	},
	iframeLoaded: function() {
		try {
            var strControl = ' <iframe id="endIeStatus" src="" width="100%" height="100%" style="border: 0px; display:none" frameborder="0" ></iframe>';
            if (!$("#endIeStatus")[0]) {
                $(strControl).appendTo($(window.document.body));
            }
            $("#endIeStatus_Cosunet", window.document).attr("src", "#");
        }
        catch (e) {
        }
	},
	bindTextboxEvent: function(textboxId, eventName, callback) {
		var t = $('#' + textboxId).textbox('textbox');
		if (t) {
			t.unbind(eventName).bind(eventName, callback);
		}
	},
	cancelParentNodeChecked: function(treeId, node){
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		if (node.getParentNode()) {
			zTree.checkNode(node.getParentNode(), false, false);
			Utils.cancelParentNodeChecked(treeId, node.getParentNode());
		}
	}
};
  
//禁止后退键 作用于Firefox、Opera  
document.onkeypress=Utils.banBackSpace;  
//禁止后退键  作用于IE、Chrome  
document.onkeydown=Utils.banBackSpace;  
//event.which == (1 = 鼠标左键 left; 2 = 鼠标中键; 3 = 鼠标右键)
/*
//tab 替代 entrer 事件
document.onkeydown = function nextElement(){
	if (event.keyCode==13){
		event.keyCode=9;
	}
};
*/