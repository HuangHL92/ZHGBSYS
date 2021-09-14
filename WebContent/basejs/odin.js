/***
 * **************************
 * 浙大网新恩普软件有限公司
 * 核三框架标签库的核心js文件
 * version:2.0
 * version:2.0.1  增加了更新表格某行数据的通用方法（2010.3.9）
 * version:2.0.2  更新了setGridData和更新某行数据的方法，使其支持回车换行，但不建议出现该类字符
 * version:2.0.3  更新了操作日志的正则表达式，因其有时会引起它执行起来慢，即该正则表达式慢(2010.7.12)
 * version:2.0.4  增加了对自动启动编辑表格某单元格的编辑模式的支持(2010.7.15)
 * version:2.0.5  修改了select标签在设置为空字符串时的bug（2010.8.17）
 * version:2.0.6  修改了和报表相关的普通报表和打印时模式对话框的宽和高 （2010.9.7）
 * version:2.0.7  修改了打开系统功能tab页的方法，增加了当非强制时还自动刷新页面的功能（2010.11.24）
 * version:2.0.8  修改了某些不兼容的js写法(2011.1.7)
 * version:2.0.9  修改了统一的提交之前的全局校验函数，使其在非IE下也能正常工作(2011.2.18)
 * version:2.1.0  增加了对某个tab页（系统的多tab页，非模块内的）是否是处于激活状态的判断（2011.5.23）
 * version:2.1.1  修改了下拉选择标签对valueNotFoundText这个属性的支持，当其存在且为false时则不做提示，否则要么默认提示，要么按照valueNotFoundText这个值来提示（2011.5.26）
 * version:2.1.2  修改了对onkeydown的处理，使的发生异常时依旧能执行（2011.6.1）
 * version:4.0.0  升级到extjs3而做的一些调整（2011.8）
 * version:4.0.1  修改checkValue方法，兼容chrome8以后的如13浏览器（2011.8.24）
 * version:4.0.2  修改了获取原始界面的方法、打开操作日志时的方法、判断某个tab页是否是处于激活状态的方法，以兼容firefox（2011.9.13）
 * version:4.0.3  修改了日期控件的只读方法以适应新的标签库(2011.9.19)
 * version:4.0.4  特殊处理了非IE下的回车变TAB功能，并解决了自动跳过下个元素是隐藏域、按钮、fieldset、被disable等(2011.9.21)
 * version:4.0.5  增加了自动跳转下个元素的方法，只针对全页面一个表单方式，方便如智能搜索在选中后回车调用而定位到下个元素中(2011.9.22)
 * version:4.0.6  修改了打开报表窗口的方式，不使用IE独有的模式窗口方式(2011.9.26)
 * version:4.0.7  增加了对代码延迟加载的支持（2012.5.3）
 * version:4.0.8  更新了setGridData方法，使其在没值时不removeAll和add(2012.5.7)
 * version:5.0.0  更新了浮动工具栏相关的获取滚动高度函数，修复了其在firefox和chrome下显示异常和不随之滚动的问题（2012.7.3）
 * version:5.0.1  增加了表格按照中文排序
 * version:5.0.2  修复了表格单元格不能复制问题
 * version:6.0.0  整合cm基本稳定
 * version:6.0.1  修复在非IE下取操作日志时抓取到的界面html内容中input没有value值问题，以及修复combo类框在tab下切换时变长 jinwei 2013.7.15
 * version:6.0.2  修复分页下拉框PageToolbar显示异常而调整了setListWidth方法 -- jinwei 2013.7.29
 * version:6.0.3  调整F8智能审核弹出窗口长宽 -- ljd 2013.8.16
 * version:6.0.4  修改info、question等提示信息窗口和alert、error效果一致，弹出不会那么难看 -- jinwei 2013.8.20
 * version:6.0.5  修复下拉框输入一个不存在的值后提示错误，然后再选一个正确值错误还在问题 -- jinwei 2013.8.26
 * version:6.0.6  修复上次更新没有考虑到非cm方式页面下拉框，导致这种情况出现严重bug -- jinwei 2013.8.28
 * version:6.0.7  修复分页下拉框无法正确设置值问题 -- jinwei 2013.8.29
 * version:6.0.8  修复使用义乌方式或核三一版开发时下拉框填值出现JS错误 -- jinwei 2013.9.3
 * version:6.0.9  调整select的失去焦点事件清楚valid机制 -- jinwei 2013.9.4
 * version:6.1.0  根据格式化字符串进行掩码输入控制，调整下拉框输入匹配方式，改为模糊匹配 -- jinwei 2013.9.9
 * version:6.1.1  修复tab里的表格在页面发生缩放后宽度无法自动伸缩 -- jinwei 2013.9.12
 * version:6.1.2  调整loadPageInTab方法，使其可以支持个人工作台下的打开功能页面 -- jinwei 2013.9.17
 * version:6.1.3  修复autoNextElement方法在设置焦点时没有跳过通过setOpItem设置不可见的元素 -- jinwei 2013.9.22
 * version:6.1.4  增加表格里某列按回车后自动在下一行增加一行空行功能 -- jinwei 2013.10.10
 * version:6.1.5  协助新版个人工作台处理按回车后自动跳到“没有访问权限”的页面问题 -- jinwei 2013.10.14
 * version:6.1.6  修复在个人工作台下最后一个元素回车不会触发onchange问题 -- jinwei 2013.10.28
 * version:6.1.7  将表格按回车自动增加空行改成自动触发点击事件（当前值为gridEnterAddRow）-- jinwei 2013.10.30
 * version:6.1.8  修复延迟加载下拉选项时增加全选会导致JS错误而无法显示下拉选项 -- jinwei 2013.11.1
 * version:6.1.9  对小键盘回车不做特殊处理 -- jinwei 2013.11.6
 * version:6.2.0  修复tab下带图标下拉框出错后错误红色图标和trriger图标重叠 -- jinwei 2013.11.22
 * version:6.2.1  解决按backspace键会导致后退到登录页面问题 -- jinwei 2013.11.28
 * version:6.2.2  解决下拉框选择后清空，取到的仍然是原来的值的问题 -- ljd 2013.12.3
 * version:6.2.3  增加getSysdate方法 -- ljd 2013.12.13
 * version:6.2.4  修复下拉框点击后面的图标而不做任何选择时会出现传到后台的下拉框值为未定义 -- jinwei 2014.1.3
 * version:6.2.5  修改request方法使grid编辑后事件只遮罩grid自身，解决afteredit和query不能同时触发的问题 -- ljd 2014.2.20
 * version:6.2.6  对grid中查询速度做优化 -- ljd 2014.3.14
 * version:6.2.7  修复tab下表单控件无width属性时导致界面显示异常bug -- jinwei 2014.3.24
 * version:6.2.8  修复Chrome下，打开窗口需要打开2次的BUG -- ljd 2014.4.15
 * version:6.2.9  修复特殊复杂页面下有操作日志时会导致浏览器处于假死状态 -- jinwei 2014.7.1
 * version:6.3.0  修复多选框必填项非空校验BUG -- ljd 2014.8.7
 * version:6.3.1  修復readonly是按Backspace建回到登錄頁面的問題-- ljd 2014.10.8
 * version:6.3.1  修改doAccOnEditorKey  增加是否按回车自动进入下一格控制-- ljd 2014.10.17
 * version:6.3.1  修改error  增加遮罩移除控制-- ljd 2014.10.21
 * version:6.3.2  临时处理个人工作台下下拉框小键盘回车无法选中问题 -- jinwei 2014.10.31
 * version:6.3.3  修复下拉框只可输时回车焦点跳过问题 -- jinwei 2014.11.3
 * version:6.3.4  修改checkValue，对获取组件类型进行异常捕捉，防止不用框架开发时出现的异常 -- ljd 2014.11.11
 * version:6.3.5  修改onkeydown方法 ，修复复选框无法按回车触发onchange -- ljd 2014.12.24
 * version:6.3.6  修复自动跳转到下一元素bug -- jinwei 2015.4.7
 * version:6.3.7  增加根据表格ID、列dataIndex和下拉列的key值获取Value值 -- jinwei 2015.6.8
 * version:6.3.7  表单提交前校验弹出格式为alert 提示具体的弹出内容 -- zoulei 2016.5.31
 * version:6.3.8  odin.openWindow添加参数thisWin 打开子页面的当前窗口对象,isTopParentWin 是否以最顶级页面作为父页面,modal 父页面是否遮罩，。 -- zoulei 2016.12.22
 * **************************
 */
var odin = {
	version:'1.0', /**系统版本号**/
    defaultTitle:'系统提示',
    msg : '正在处理中...',
    msgCls : 'x-mask-loading',  
	ajaxaSynchronous:false,/*****ajax默认的同步异步标志，false为同步，true为异步*******/
	isWorkpf : (navigator.userAgent.indexOf("Workpf") != -1), // 是否为个人工作台
	workpfVer : (navigator.userAgent.indexOf("Workpf") != -1 ? navigator.userAgent.substring(navigator.userAgent.indexOf("Workpf") + 7) : 0),// 个人工作台版本号
    confirm:function(info,fun,title){
    	var boxtitle = odin.defaultTitle;
    	if(title){
    	   boxtitle = title;
    	}
    	info = odin.toHtmlString(info);
	     Ext.MessageBox.show({
           title: boxtitle,
           msg: info,
           minWidth:200,
           buttons: Ext.MessageBox.OKCANCEL,
           multiline: false,
           fn: fun,
           icon : Ext.MessageBox.QUESTION
       });
    },
    alert:function(info,fun,title,width){
        var boxtitle = odin.defaultTitle;
    	if(title){
    	   boxtitle = title;
    	}
    	info = odin.toHtmlString(info);
	    Ext.MessageBox.show({
           title: boxtitle,
           msg: info,
           minWidth:200,
           buttons: Ext.MessageBox.OK,
           multiline: false,
           width:width,
           fn: fun
       });
    },
    prompt:function(info,fun,title){
        var boxtitle = odin.defaultTitle;
    	if(title){
    	   boxtitle = title;
    	}
    	info = odin.toHtmlString(info);
        Ext.MessageBox.prompt(boxtitle,info,fun);
    },
    promptWithMul:function(info,fun,title){
    	var boxtitle = odin.defaultTitle;
    	if(title){
    	   boxtitle = title;
    	}
    	info = odin.toHtmlString(info);
	    Ext.MessageBox.show({
           title: boxtitle,
           msg: info,
           minWidth:300,
           buttons: Ext.MessageBox.OKCANCEL,
           multiline: true,
           fn: fun
       });
    },
    error:function(info,fun,title){
        var boxtitle = odin.defaultTitle;
    	if(title){
    	   boxtitle = title;
    	}
    	info = odin.toHtmlString(info);
		info = "<font color=red>" + info + "</font>";
		try{
			if(typeof loading != 'undefined'){
				loading.remove();
			}
		}catch(exception){
			
		}
		Ext.MessageBox.show({
           title: boxtitle,
           msg: info,
           minWidth:200,
           buttons: Ext.MessageBox.OK,
           multiline: false,
           fn: fun,
           icon:Ext.MessageBox.ERROR
       });
    },
	question:function(info,fun,title){
        var boxtitle = odin.defaultTitle;
    	if(title){
    	   boxtitle = title;
    	}
    	info = odin.toHtmlString(info);
	    Ext.MessageBox.show({
           title: boxtitle,
           msg: info,
           minWidth:200,
           buttons: Ext.MessageBox.OKCANCEL,
           multiline: false,
           fn: fun,
           icon:Ext.MessageBox.QUESTION
       });
    },
	warning:function(info,fun,title){
        var boxtitle = odin.defaultTitle;
    	if(title){
    	   boxtitle = title;
    	}
    	info = odin.toHtmlString(info);
	    Ext.MessageBox.show({
           title: boxtitle,
           msg: info,
           minWidth:200,
           buttons: Ext.MessageBox.OK,
           multiline: false,
           fn: fun,
           icon:Ext.MessageBox.WARNING
       });
    },
    info:function(info,fun,title){
        var boxtitle = odin.defaultTitle;
    	if(title){
    	   boxtitle = title;
    	}
    	info = odin.toHtmlString(info);
	    Ext.MessageBox.show({
           title: boxtitle,
           msg: info,
           minWidth:200,
           buttons: Ext.MessageBox.OK,
           multiline: false,
           fn: fun,
           icon:Ext.MessageBox.INFO
       });
    },
    progress : function(progressPecent, progressText, info, title) {
		if (odin.msgShow == false || odin.msgShow_progress == false) {
			return;
		}
		var boxtitle = "请耐心等待...";
		if (title) {
			boxtitle = title;
		}
		info = odin.toHtmlString(info);
		Ext.MessageBox.show({
					title : boxtitle,
					msg : info,
					width : 320,
					progress : true,
					closable : false
				});
		Ext.MessageBox.updateProgress(progressPecent, progressText);
	},
    
    onFilterCode:function(){ 
		var obj = event.srcElement; 
		if(obj.value=="")
		{
		    Ext.get(obj).prev().value = '';
		}else{
		    var reg = /^\d+/;
		    if(obj.value.match(reg))
		    {
		        if(obj.value.replace(/\d+/,'').length>4)
		        {
		            Ext.get(obj).prev().value = obj.value.match(reg);
		            obj.value = obj.value.replace(/\d+/,'').substring(4);
		        }else{
		           obj.value = '';
		           Ext.get(obj).prev().value = '';
		        }
		    }else{
		        if(Ext.get(obj).prev().value == '')
		        {
		            obj.value = '';
		        }else{
		            //
		        }
		    }
		}
    },
    changeCodeText:function(field){
        Ext.get(field.getId()).next().value = Ext.get(field.getId()).next().value.replace(/\d+/,'').substring(4);
        if(Ext.get(field.getId()).next().value=="")
        {
            field.setValue('');
        }
    },
    setSelectValue:function(id,newvalue){
    	if(typeof radow_select_info != 'undefined'){
    		if(typeof radow_select_default == 'undefined'){
    			radow_select_default = [];
    		}
    		radow_select_default.push({'id':id,'newvalue':newvalue});
    		return;
    	}
    	odin.setSelectValueReal(id,newvalue);
    },
    setSelectValueReal:function(id,newvalue){
    	var value = document.getElementById(id).value;
		if(newvalue==null) newvalue = "";
		if(typeof newvalue == 'undefined') newvalue = "";
        value = newvalue;
        document.getElementById(id).value = value;
		//alert("value:"+value);
        var combo = odin.ext.getCmp(id+"_combo");
        if(!combo){
        	return;
        }
		if(combo.getXType()=='lovcombo'){
			combo.setValue(value,'key');
			return;
		}
        if(value!=null&&value!=""){ 
	        var store = Ext.getCmp(id+"_combo").store;
	        var length = store.getCount();
	        if(combo.mode=='remote' && store.url!=""){
	        	var param = store.baseParams;
				param.query = value;
				var req = odin.Ajax.request(store.url, param, odin.ajaxSuccessFunc, odin.ajaxSuccessFunc, false, false);
				var data = Ext.decode(req.responseText).data;
				if (data != null && data.length == 1) {
					document.getElementById(id).value = data[0].key;
					Ext.getCmp(id + "_combo").setValue(data[0].value);
				} else {
					document.getElementById(id).value = value;
					Ext.getCmp(id + "_combo").setValue(value);
				}
	        }else{
		        var isExsist = false;
		        for(i=0;i<length;i++)
		        {
		            var rs = store.getAt(i);
		            if(rs.get('key')==value)
		            {
		                var newValue = rs.get('value');
		                if(newValue.indexOf('&nbsp;')>0){
			                newValue = newValue.replace(/\d+/,'').replace(/&nbsp;/g,'');
		                }
		                Ext.getCmp(id+"_combo").setValue(newValue);
		                isExsist = true
		                break;
		            }
		        }
		        if(!isExsist){
		            Ext.getCmp(id+"_combo").setValue(value);
		        }
	        }
        }else{
			Ext.getCmp(id+"_combo").setValue('');
			document.getElementById(id).value = "";
		}
    },
    doAccForSelect:function(field){
        var comboId = field.getId();
        var id = comboId.substring(0,comboId.lastIndexOf("_combo"));
        var store = Ext.getCmp(comboId).store;
        var canOutSelectList = Ext.getCmp(comboId).canOutSelectList;
        var length = store.getCount();
        var isExsist = false;
		for(i=0;i<length;i++)
        {
            var rs = store.getAt(i);
            if(rs.get('value')==field.getValue()){
                isExsist = true;
                Ext.get(id).value = rs.get('key');
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
			Ext.get(id).value="";
		}
        if(!isExsist){
        	if(field.getValue()!=""){
				var msg = "您输入的值没有对应的匹配代码信息！请重新输入。";
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
						radow.cm.setValid(id,false,msg);
					}else{
						odin.alert(msg, odin.doFocus);
					}
				}
        	}
            Ext.get(id).value = field.getValue();
        }else{
        	if(typeof radow!='undefined' && radow && radow.cm){
        		if(true != field.noClearValid){
        			radow.cm.setValid(id,true,'');
        		}
        	}
        }
        if(typeof Ext.get(id).value == 'undefined'){
        	Ext.get(id).value = "";
        }
        document.getElementById(id).value = Ext.get(id).value;
        //alert(Ext.get(id).value);
        if(odin.isWorkpf){
        	odin.autoNextElement(document.getElementById(comboId));
        }
    },
    setHiddenTextValue:function(combo,record,index ){
        var comboId = combo.getId();
        var value = record.get('key');
        var id = comboId.substring(0,comboId.indexOf("_combo"));
        Ext.get(id).value = value;
        var newValue = record.get('value');
        if(newValue.indexOf('&nbsp;')>0){
	        newValue = newValue.replace(/\d+/,'').replace(/&nbsp;/g,'');
        } 
        combo.setValue(newValue);
        document.getElementById(id).value = Ext.get(id).value;
        //alert(id+"||"+Ext.get(id).value);
    },
    fillLeft:function(oldstr,ch,len){
    	var newstr=oldstr;
    	for(var i=oldstr.length;i<len;i++){
    		newstr=ch+newstr;
    	}
    	return newstr;
    },
    fillRight:function(oldstr,ch,len){
    	var newstr=oldstr;
    	for(var i=oldstr.length;i<len;i++){
    		newstr=newstr+ch;
    	}
    	return newstr;
    },
    Ajax:{
		/**
		 * ajax
		 * @param {Object} reqUrl
		 * @param {Object} reqParams
		 * @param {Object} successFun
		 * @param {Object} failureFun
		 * @param {Object} asynchronous 同步异步标志，true为异步，false为同步
		 * @param {Object} isMask 是否要做mask遮罩处理
		 */
    	request:function(reqUrl,reqParams,successFun,failureFun,asynchronous,isMask){
    		Ext.Ajax.timeout=30000000;
    		var el = null;             
            if(reqParams != null && reqParams.tmpEl != null){
                el = reqParams.tmpEl;
                delete  reqParams.tmpEl;
            }else{
                el = Ext.get(document.body);
            }
             
			if (isMask!=null&&isMask==false) { 
				//参数存在且为false
				//alert('参数存在且为false');
			}else{
				el.mask(odin.msg, odin.msgCls);
			}
			if (asynchronous == null) {
				asynchronous = odin.ajaxaSynchronous;
			}
			if (asynchronous!=false) {
				asynchronous = true;
			}
			return	Ext.Ajax.request({
					url: reqUrl,
					success: doSuccess,
					failure: doFailure,
					params: reqParams,
					asynchronous: asynchronous
				});
			function doSuccess(request){
				el.unmask();
				var response=null;
				try{
					response=eval('('+request.responseText+')');
				}catch(err){
					//debugger;
					if(request.responseText.indexOf("有登录或超时，请重新登录")>0){
						alert("没有登录或超时，请重新登录！");
					}else{
						alert("没有登录或超时，请重新登录！");
						//alert("Ajax请求返回的数据非法！");
					}
					top.parent.location.href = 'logonAction.do';
					throw err;
				}
				if(response.messageCode=="0"){//请求成功
					if(successFun){//有成功回调函数
						successFun(response);
					}else{
						alert(response.mainMessage);
					}
				}else{//请求失败
					if(failureFun){//有失败回调函数
						failureFun(response);
					}else{
						var errmsg=response.mainMessage;
						if(response.detailMessage!=""){
							errmsg=errmsg+"\n详细信息:"+response.detailMessage;
						}
						alert(errmsg);
					}
				}
			}
			
			function doFailure(request){
				//console.log(request.responseText);
				el.unmask();
				//alert("Ajax请求失败，请与系统管理员联系！");
				if(request.responseText.indexOf("有登录或超时，请重新登录")>0){
					alert("没有登录或超时，请重新登录！");
				}else{
					alert("没有登录或超时，请重新登录！");
					//alert("Ajax请求返回的数据非法！");
				}
				top.parent.location.href = 'logonAction.do';
			}	
	    },
	    formatDate:function(jsonDate){
			if(!jsonDate) return null;

	    	var year=String(jsonDate.year+1900);
	    	var month=String(jsonDate.month+1);
	    	var date=String(jsonDate.date);
	    	month=odin.fillLeft(month,"0",2);
	    	date=odin.fillLeft(date,"0",2);
	    	var theDate=year+"-"+month+"-"+date;
	    	return theDate;
	    },
	    formatDateTime:function(jsonDate){
			if(!jsonDate) return null;

	    	var year=String(jsonDate.year+1900);
	    	var month=String(jsonDate.month+1);
	    	var date=String(jsonDate.date);
	    	var hour=String(jsonDate.hours);
	    	var minute=String(jsonDate.minutes);
	    	var second=String(jsonDate.seconds);
	    	month=odin.fillLeft(month,"0",2);
	    	date=odin.fillLeft(date,"0",2);
	    	hour=odin.fillLeft(hour,"0",2);
	    	minute=odin.fillLeft(minute,"0",2);
	    	second=odin.fillLeft(second,"0",2);
	    	var theDateTime=year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
	    	return theDateTime;
	    }
    },
    enterToTab:function(){
    	//alert("ok");
    	var e=window.event.srcElement;
    	var type=e.type;
    	if(type!='button'&&type!='textarea'&&event.keyCode==13){
    		event.keyCode=9;
    	}
    },
    doOpLog:function(theForm){
    	//操作日志处理
    	var prsource;
    	try{
    		prsource=eval(MDParam.prsource);
    	}catch(e){
			return null;
		}
		if(prsource){
	    	/*var digest=new Array(10);
	    	for(var i=0;i<10;i++){
	    		digest[i]="";
	    	}
	    	//获取操作日志摘要信息
	    	var len=Math.min(prsource.length,10);
	    	for(var i=0;i<len;i++){
	    		var prop=prsource[i].property;
	    		if(prop=="") continue;
	    		
	    		var el=document.getElementById(prop);
	    		if(el){
	    			digest[i]=el.value;
	    		}
	    	}
	    	*/
	    	var len=prsource.length;
	    	var items=new Array(len);
	    	for(var i=0;i<len;i++){
	    		items[i]="";
	    	}
	    	for(var i=0;i<len;i++){
	    		var prop=prsource[i].property;
	    		if(prop=="") continue;
	    		
	    		var type=prsource[i].type;
	    		if(type&&type=="select"){
	    			prop=prop+"_combo";
	    		}
	    		var el=document.getElementById(prop);
	    		if(el){
	    			if(type&&type.toLowerCase()=="text"){
	    				items[i]=el.innerHTML;
	    			}else{
	    				items[i]=el.value;
	    			}
	    		}
	    	}
	    	var digest="";
	    	for(var i=2;i<len;i++){
	    		if(items[i]=="") continue;
	    		
	    		digest=digest+","+prsource[i].label+":"+items[i];
	    	}
	    	if(digest!=""){
	    		digest=digest.substring(1);
	    	}
	    	//获取原始界面信息
	    	//var oriSource="<html>"+document.documentElement.innerHTML+"</html>";
	    	var oriSource="";
	    	if(!Ext.isIE){
				var inputs = document.getElementsByTagName("input");
				for(var i=0;i<inputs.length;i++){
					inputs[i].setAttribute("value",inputs[i].value);
				}
			}
			if(document.documentElement.outerHTML){
				oriSource = document.documentElement.outerHTML;
			}else{ //firefox 2011.9.13
				oriSource = document.documentElement.innerHTML
			}
	    	//alert(oriSource);
	    	//去掉javascript内容
	    	var oriSource=oriSource.replace(/<script([^>]*?>([^<]*(<[^\/]*)*))<\/script>/gi,"");
	    	
	    	/*var userlog={functionid:MDParam.functionid,
	    				 aac001:digest[0],
	    				 aab001:digest[1],
	    				 prcol1:digest[2],
	    				 prcol2:digest[3],
	    				 prcol3:digest[4],
	    				 prcol4:digest[5],
	    				 prcol5:digest[6],
	    				 prcol6:digest[7],
	    				 prcol7:digest[8],
	    				 prcol8:digest[9],
	    				 orisource:oriSource
	    				};*/
	    	var userlog={functionid:MDParam.functionid,
	    				 aac001:items[0],
	    				 aab001:items[1],
	    				 digest:digest,
	    				 prcol1:"",
	    				 prcol2:"",
	    				 prcol3:"",
	    				 prcol4:"",
	    				 prcol5:"",
	    				 prcol6:"",
	    				 prcol7:"",
	    				 prcol8:"",
	    				 orisource:oriSource
	    				};
	    	//var srtUserlog=JSON.stringify(userlog);
	    	var srtUserlog=Ext.util.JSON.encode(userlog);
	    	//alert(srtUserlog);
	    	return srtUserlog;
	    }else{
	    	return null;
	    }
    },
    /*submit:function(theForm){//表单方式提交保存
    	//操作日志处理
    	var srtUserlog=this.doOpLog(theForm);
    	//alert(srtUserlog);
	    if(srtUserlog){
	    	//向表单中插入操作日志隐藏字段    	
	    	var userlogNode=document.getElementById("userlog");
	    	if(!userlogNode){
	    		userlogNode=document.createElement("input");
	    		userlogNode.setAttribute("name","userlog");
	    		userlogNode.setAttribute("type","hidden");	    		
	    		theForm.appendChild(userlogNode);
	    	}
	    	userlogNode.setAttribute("value",String(srtUserlog));	
	    }	
	    
	    theForm.submit();		 
    },*/
    submit:function(theForm,successFun,failureFun){//Ajax方式提交保存
    	var params={};
    	//将表单域中的数据转换成参数对象
    	var elList=theForm.getElementsByTagName("input");
		//var areaList = theForm.getElementsByTagName("textarea");
		//if (areaList.length > 0) {
		//	elList = elList.concat(areaList);
		//}
    	for(var i=0;i<elList.length;i++){ 
    		if(elList.item(i).name&&elList.item(i).name.indexOf("-")==-1){
	    		var exp="params."+elList.item(i).name+"="+Ext.util.JSON.encode(elList.item(i).value);
	    		//alert(exp);
	    		eval(exp);
    		}
    	}

		elList=theForm.getElementsByTagName("textarea");
    	for(var i=0;i<elList.length;i++){ 
    		if(elList.item(i).name&&elList.item(i).name.indexOf("-")==-1){
	    		var exp="params."+elList.item(i).name+"="+Ext.util.JSON.encode(elList.item(i).value);
	    		//alert(exp);
	    		eval(exp);
    		}
    	} 

    	//操作日志处理
    	var srtUserlog=this.doOpLog(theForm);
    	if(srtUserlog){
    		params.userlog=String(srtUserlog);
    	}
    	//设置模块functionid 2009-05-20 zhangy
    	if(MDParam){
    		params._modulesysfunctionid=MDParam.functionid;
    	}
    	if (odin.checkValue(theForm) == true) { //做通一校验判断
			this.Ajax.request(theForm.action, params, successFun, failureFun,false,true); //同步 遮罩
		}
    },
    formClear:function(theForm){
    	var elList=theForm.getElementsByTagName("input");
    	for(var i=0;i<elList.length;i++){
    		//elList.item(i).setAttribute("value","");
    		elList.item(i).value = "";
    	}
    },
    reset:function(){
    	//location.reload();
    	location.href=contextPath+MDParam.location;
    },
    loadPageGridWithQueryParams:function(gridId,params){
        var store = Ext.getCmp(gridId).getStore(); 
        if(Ext.getCmp(gridId).getTopToolbar() && Ext.getCmp(gridId).getTopToolbar().pageSize){
        	params.limit = Ext.getCmp(gridId).getTopToolbar().pageSize;
        }else if(Ext.getCmp(gridId).getBottomToolbar() && Ext.getCmp(gridId).getBottomToolbar().pageSize){
            params.limit = Ext.getCmp(gridId).getBottomToolbar().pageSize;
        }else{
            params.limit = sysDefaultPageSize;
        }  
        store.on('beforeload', function(ds) {
        	var limit = ds.baseParams.limit;
        	var lastParams = ds.lastOptions.params;
        	odin.ext.apply(ds.baseParams,params);
        	if(typeof lastParams=='undefined' || lastParams.start==0){
		    	//ds.baseParams = params;
        	}else{
        		if(typeof radow!='undefined' && radow && radow.cm){
	        		delete ds.baseParams.rc;
	        		delete ds.baseParams.radow_parent_data;
        		}
        	}
        	if(typeof limit!='undefined'){
        		ds.baseParams.limit = limit;
        	}
        	ds.baseParams.cueGridId = gridId;

        });     
        store.load();
    },
    loadGridData:function(gridId,params,beforeloadFun){
        var store = Ext.getCmp(gridId).getStore(); 
        store.on('beforeload', function(ds) {
            if(beforeloadFun){beforeloadFun(ds)};
		    var limit = ds.baseParams.limit;
        	var lastParams = ds.lastOptions.params;
        	odin.ext.apply(ds.baseParams,params);
        	if(typeof lastParams=='undefined' || lastParams.start==0){
		    	//ds.baseParams = params;
        	}else{
        		if(typeof radow!='undefined' && radow && radow.cm){
	        		delete ds.baseParams.rc;
	        		delete ds.baseParams.radow_parent_data;
        		}
        	}
        	if(typeof limit!='undefined'){
        		ds.baseParams.limit = limit;
        	}
        	ds.baseParams.cueGridId = gridId;

        });     
        store.load();
    },
    showWindowWithSrc:function(windowId,newSrc){
		if (document.getElementById("iframe_" + windowId) != null) {
			document.getElementById("iframe_" + windowId).src = newSrc;
			window.setTimeout('Ext.getCmp(\"'+windowId+'\").show(Ext.getCmp(\"'+windowId+'\"))',200);
		}
		else {		
			Ext.getCmp(windowId).html = "<iframe style=\"background:white;border:none;\" width=\"100%\" height=\"100%\" id=\"iframe_" + windowId + "\" name=\"iframe_" + windowId + (onload == null ? "" : "\" onload=\"" + onload) + "\" src=\"" + newSrc + "\"></iframe>";
			window.setTimeout('Ext.getCmp(\"'+windowId+'\").show(Ext.getCmp(\"'+windowId+'\"))',200);
		}     
    },
    changeToUrl:function(value, params, record,rowIndex,colIndex,ds){
        return "<a href='www.baidu.com?test="+record.data.price+"&test2="+record.data.change+"'>"+value+"</a>";
    },
    gridCellClick:function(grid,rowIndex,colIndex,event){
        //alert(colIndex);
        if(colIndex%2==0){
            grid.getColumnModel().setEditable(colIndex,false);
        }else if(colIndex!=3){
            grid.getColumnModel().setEditable(colIndex,true);
        }
    },
    afterEditForEditGrid:function(e){
        var grid = e.grid;
        var record = e.record;
        var field = e.field;
        var originalValue = e.originalValue;
        var value = e.value;
        var row = e.row;
        var column = e.column;
        var sel_data;
		if (odin.cueSelectArrayData.length > 0) {
			eval("window." + field + "_select = odin.cueSelectArrayData; ");
		}
		//alert(value+":value||field:"+field);
		var find = false;
        if(eval("window."+field+"_select")){
            sel_data  = eval(field+"_select");
            for(i=0;i<sel_data.length;i++){
				//alert(sel_data[i][1]+"||valuee:"+value);
                if(sel_data[i][1] == value){
                    grid.store.getAt(row).set(field,sel_data[i][0]);
                    find = true;
					break;
                }
            }
            if(!find){
            	for(i=0;i<sel_data.length;i++){
                if(sel_data[i][0] == value){
                    find = true;
					break;
                }
            }
            }
            if(!find && sel_data.length>0 && sel_data[0].length>0 && record.get(field)!=''){
            	odin.alert("您输入的值没有对应的匹配代码信息！系统将默认给您选中第一项。");
            	record.set(field,sel_data[0][0]);
            }
        }
        //alert(field);
        //alert(row+"|"+column);
        //var editor = grid.getColumnModel().getCellEditor(column,row);
        //alert(grid.store.getAt(row).get(field));
        //alert(typeof(editor));
        //if(editor instanceof Ext.grid.GridEditor){
             //alert(1);
        //}
        //alert(editor.getValue());
    },
    doGridSelectAcc:function(value, params, record,rowIndex,colIndex,ds){
	    var selectColumnName = "pctChange";
	    var sel_data = eval(selectColumnName+"_select");
	    for(i=0;i<sel_data.length;i++){
	        if(sel_data[i][0] == value){
	              value = sel_data[i][1];
	              break;
	        }
	    }
	    return value;
	},
	renderDate:function(dateVal){
	    if(!dateVal||dateVal==""){
        	return "";
	    }else{
	     	if(typeof dateVal == 'string'){
	    		dateVal = Date.parseDate(dateVal,'Y-m-d');
	    	}
	    }
    	return Ext.util.Format.date(dateVal,'Y-m-d');
    },
    billPrint:function(repid,queryName,param,preview){
    	//var strpreview="true";
    	//if(!preview){
    	//	strpreview="false";
    	//}
    	var repmode="3";
    	if(!preview){
    		repmode="2";
    	}
    	var url=contextPath+"/common/billPrintAction.do?repid="+repid+"&queryname="+queryName+"&param="+encodeURIComponent(encodeURIComponent(param))+"&repmode="+repmode;
    	window.showModalDialog(url,null,"dialogWidth=800px;dialogHeight=600px");
		//window.open(url,"billWin","status=no,toolbar=no,height=600,width=800");
    },
    reportPrint:function(repid,queryName,param,repmode){
    	if(!repmode){
    		repmode="5";
    	}
    	var url=contextPath+"/common/billPrintAction.do?repid="+repid+"&queryname="+queryName+"&param="+encodeURIComponent(encodeURIComponent(param))+"&repmode="+repmode;
    	window.showModalDialog(url,null,"dialogWidth=800px;dialogHeight=600px");
		//window.open(url,"repWin","status=no,toolbar=no,height=600,width=800");
    },
    getGridJsonData:function(gridId,inputName){
        if(!inputName){
            inputName = gridId+"Data";
        }
        var grid = Ext.getCmp(gridId);
        var store = grid.store;
        var dataArray = new Array(store.data.length);
        for(i=0;i<store.data.length;i++){
            dataArray[i] = store.getAt(i).data; //store.data.length-(i+1)
        }
        var gridJsonStr = Ext.util.JSON.encode(dataArray);
        document.getElementById(inputName).value = gridJsonStr;
        //alert(document.getElementById(inputName).value);
        return gridJsonStr;
    },
    setGridJsonData:function(gridId,inputName){
        if(!inputName){
            inputName = gridId+"Data";
        }
        if(document.getElementById(inputName).value!=null&&document.getElementById(inputName).value!=""){
	        var jsonStr = Ext.util.JSON.decode(document.getElementById(inputName).value);
	        Ext.getCmp(gridId).store.removeAll();
	        if(jsonStr!=null&&jsonStr!=""){
		        var grid = Ext.getCmp(gridId);
		        var store = grid.store;
		        var rsData = new Array(jsonStr.length);
		        for(i=0;i<jsonStr.length;i++){
		            //alert(Ext.util.JSON.encode(jsonStr[i]));
		            rsData[i] = new Ext.data.Record(jsonStr[i]);
		        }
		        store.add(rsData);
	        }
        }
    },
    setGridData:function(gridId,strJsonData,isRemoveAll){
    	strJsonData = strJsonData.replace(/\r/gi,"");
    	strJsonData = strJsonData.replace(/\n/gi,"\\n");
    	var grid = odin.ext.getCmp(gridId);
    	var store = grid.store;
    	if(isRemoveAll && store.getCount()>0){
    		store.removeAll();
    	}
    	var data = odin.ext.decode(strJsonData);
    	if(data.length>0){
    		var rsData = new Array(data.length);
//	    	for(i=0;i<data.length;i++){
//	    		 if( typeof store.reader.meta.id != "undefined"){
//		    	        rsData[i] = new Ext.data.Record(data[i],data[i][store.reader.meta.id]);
//		    	    }else{
//		    	        rsData[i] = new Ext.data.Record(data[i]);
//		    	    }
//
//	        }
//	    	store.add(rsData);
	    	 var readRecords = {};
	          readRecords[store.reader.meta.root] = data;        
	          readRecords[store.reader.meta.totalProperty] = data.length;                     	                   
	          store.loadData(readRecords, false);
	        
    	}
    },
    /**
    *更新表格一行数据
    **/
    updateGridRowData:function(gridId,rowIndex,rowJsonData){
    	rowJsonData = rowJsonData.replace(/\r/gi,"");
    	rowJsonData = rowJsonData.replace(/\n/gi,"\\n");
    	var data = odin.ext.decode(rowJsonData);
    	var store = odin.ext.getCmp(gridId).store;
    	var rd = store.getAt(rowIndex);
    	for(o in data){
    		rd.set(o,eval('data.'+o));
    	}
    },
    addGridRowData:function(gridId,dataObj,rowCount){
        var grid = Ext.getCmp(gridId);
        var store = grid.store;
        if(!rowCount){
           rowCount = 1;
        }
        var rsData = new Array(rowCount);
        for(i=0;i<rsData.length;i++){
			if (!dataObj) {
				rsData[i] = store.getAt(store.data.length - 1).copy(store.data.length);
			}else{
				rsData[i] = new store.reader.recordType(dataObj);
			}
        }
        store.insert(store.getCount(),rsData);
    },
    openOpLogList:function(){
    	var aid="oplog";
    	var atitle="操作日志中心";
    	//2009-05-20 zhangy 
    	//var src=contextPath+"/sys/MDOpLogListAction.do?functionid="+MDParam.functionid+"&location="+MDParam.location;
    	var src=contextPath+"/sys/MDOpLogListAction.do?functionid="+MDParam.functionid;
    	if(!odin.isWorkpf){
    		var tabs=top.frames[1].tabs;
    		var tab=tabs.getItem(aid);
    		if (tab){tabs.remove(tab);}
    		top.frames[1].addTab(atitle,aid,src);
    	}else{
    		var win = qtobj.openNewTab(src,atitle);
    	}
    },
    openWzOpLogList:function(sql,colsInfo){
    	parent.wzOpSql = sql;
    	parent.wzColsInfo = colsInfo;
    	var aid="oplog";
    	var atitle="操作日志中心";
    	//var src=contextPath+"/sys/WzMDOpLogListAction.do?functionid="+MDParam.functionid;
    	var src=contextPath+"/radowAction.do?method=doEvent&pageModel=cm&bs=oplog.WzCMOpLogList&initParams="+sql;
    	if(!odin.isWorkpf){
    		var tabs=top.frames[1].tabs;
	    	var tab=tabs.getItem(aid);
	    	if (tab){tabs.remove(tab);}
	    	top.frames[1].addTab(atitle,aid,src);
    	}else{
    		var win = qtobj.openNewTab(src,atitle);
    	}
    },
    openPMWzOpLogList:function(sql,colsInfo){
    	parent.wzOpSql = sql;
    	parent.wzColsInfo = colsInfo;
    	var aid="oplog";
    	var atitle="操作日志中心";
    	var src=contextPath+"/sys/WzPMMDOpLogListAction.do?functionid="+MDParam.functionid;
    	if(!odin.isWorkpf){
    		var tabs=top.frames[1].tabs;
	    	var tab=tabs.getItem(aid);
	    	if (tab){tabs.remove(tab);}
	    	top.frames[1].addTab(atitle,aid,src);
    	}else{
    		var win = qtobj.openNewTab(src,atitle);
    	}
    },
    accCheckboxCol:function(value, params, record,rowIndex,colIndex,ds){
        var rtn = "";
        rtn += '<div class=\"x-grid-editor\">';
		rtn += '<div class=\"x-form-check-wrap\">';
        if(value==true){
            rtn += "<div ><input type='checkbox' alowCheck='true' name='col"+rowIndex+colIndex+"' onclick='odin.accChecked(this,"+rowIndex+","+colIndex+",\"change\")' checked />";
        }else{
            rtn += "<input type='checkbox' alowCheck='true' name='col"+rowIndex+colIndex+"' onclick='odin.accChecked(this,"+rowIndex+","+colIndex+",\"change\")' />";
        }
        rtn += '</div></div>';
		for(obj in record){
			alert(obj);
		}
        odin.checkboxds = ds;
        return rtn;
    },
    accChecked:function(obj,rowIndex,colIndex,colName,gridId){
		odin.cueCheckBoxChecked = obj.checked;
        if(obj.alowCheck=="false"){
            obj.checked=!obj.checked;
            return;
        }

        if(obj.checked){
			if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
				odin.checkboxds.getAt(rowIndex).set(colName, true);
			}else{
				odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, true);
			}
        }else{
			if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
				odin.checkboxds.getAt(rowIndex).set(colName, false);
			}else{
				odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, false);
			}
        }
		odin.checked = obj.cueCheckBoxChecked;
    },
	accCheckedForE3:function(obj,rowIndex,colIndex,colName,gridId){
        if(obj.getAttribute('alowCheck')=="false"){
            return;
        }

        if(obj.className=='x-grid3-check-col'){
			if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
				odin.checkboxds.getAt(rowIndex).set(colName, true);
			}else{
				odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, true);
			}
			obj.className = 'x-grid3-check-col-on';
        }else{
			if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
				odin.checkboxds.getAt(rowIndex).set(colName, false);
			}else{
				odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, false);
				if(document.getElementById("selectall_"+gridId+"_"+colName)!=null){
					document.getElementById("selectall_"+gridId+"_"+colName).value='false';
					document.getElementById("selectall_"+gridId+"_"+colName).className='x-grid3-check-col';
				}	
			}
			obj.className = 'x-grid3-check-col';
        }
    },
    doFilterGridCueData:function(grid,filterFunc){
    	grid.store.filterBy(filterFunc);
    },
    doClearFilter:function(grid){
    	grid.store.clearFilter(false);
    },
    showErrorMessage:function(response){
    	var errmsg=response.mainMessage;
		if(response.detailMessage!=""){
			errmsg=errmsg+"\n详细信息:"+response.detailMessage;
		}
		alert(errmsg);
    },
	encode:Ext.encode,
	beforeedit:function(e){
		var grid = e.grid;
        var record = e.record;
        var field = e.field;
        var originalValue = e.originalValue;
        var value = e.value;
        var row = e.row;
        var column = e.column;
		var cancel = e.cancel;
		//通过下面这句可以禁止当前单元格的编辑
		//e.cancel = true; 
	},
	/***************给select重新设置数据**************************/
	reSetSelectData:function(selectId,jsonData){
		var store = null;
		var combo = null;
		try{
			combo = odin.ext.getCmp(selectId+"_combo");
			store = combo.store;
		}catch(e){
			combo = odin.ext.getCmp(selectId);
			store = combo.store;
		}
		try{
			document.getElementById(selectId).value = "";
		}catch(e){
		}
		var count = store.getCount();
		//store.removeAll(); 使用它会有问题，当第一次对同一个对象使用此方法没问题，但以后就会报js出错
		//所以这里通过一条一条来remove数据
		for(i=0;i<count;i++){ 
			store.remove(store.getAt(0));
		}
		//处理重新加载数据后的all选项 jinwei 2013.3.26
		var allAsItem = combo.allAsItem;
		if(typeof allAsItem != 'undefined' && allAsItem == true){
			store.insert(0, new odin.ext.data.Record({key:"all",value:"全部",params:""})); 
		}
		if(jsonData!=null && jsonData.length>0){
			var data = new Array(jsonData.length);
			for(i=0;i<jsonData.length;i++){
				data[i] = new odin.ext.data.Record(jsonData[i]);
			}
			store.add(data);
		}
	},
	/***********统一的提交之前的全局校验函数，用来判断非空的是否有空的存在，不空的是否符合业务校验规则*******************/
	checkValue:function(userTestForm){
		var errtitle = "<b>请先修正以下问题后再进行本操作：</b><br>";
		var eles = userTestForm.elements;
		for(i=0;i<eles.length;i++){
			var obj = eles[i];
			if(obj.tagName=='OBJECT'||obj.tagName=='object'){//zoulei 2018年4月16日 
				continue;
			}
			odin.cueCheckObj = obj;	
			if(obj.getAttribute("required")=='true' || (!obj.getAttribute("required") && obj.required)){	//为了兼容IE和非IE，IE下为“true”，而chrom不支持，而getAttribute两者都支持
				var type ="";
				try{
					type =radow.findElementTypeByID(obj.id);
				}catch(exception){
					
				}				
				var value="";
				if(type == 'lovcombo'){
					value=odin.ext.getCmp(obj.id+"_combo").getCheckedValue('key');
				}else{
					value=obj.value;
				}
				if(value==""){ //非空判断
					//将以前的obj.label改为现在这种方式可支持非IE，所以做了改动
				 	//odin.alert(obj.getAttribute("label")+"不能为空！",odin.doFocus);
					odin.alert(errtitle + obj.getAttribute("label") + "不能为空！", odin.doFocus);
					return false;
				}
			}
			if(obj.value!=null&&obj.value!=""){
				//oracle输入框不得大于4000 by zoul 2016-05-25 16:38:56
				if(odin.getStringByteLength(obj.value)>4000){
					/*if(parent.$h)
					parent.$h.alert('系统提示',obj.getAttribute("label") + " 值不能大于4000！", odin.doFocus);
					else
					odin.alert(obj.getAttribute("label") + " 值不能大于4000！", odin.doFocus,null,400);
					return false;*/
				}
				var eObj = odin.getCmpByName(obj.name);
				if(eObj){
					if (!eObj.isValid(false)) {
						//odin.alert(eObj.invalidText,odin.doFocus);
						var spanId = obj.name.replace("_combo","");
						//alert(Ext.query('#'+spanId+'+div')[0].qtip);
						//odin.error(errtitle + '“' + odin.ext.get(spanId).dom.getAttribute("label") + '”输入项的值不符合要求，请重新输入！', odin.doFocus);
						if(Ext.query('#'+spanId+'+div').length>0){
							if(parent.$h)
								if((!window.dialogArguments)||typeof(realParent)=='undefined')
									parent.$h.alert('系统提示',odin.ext.get(spanId).dom.getAttribute("label")+":"+Ext.query('#'+spanId+'+div')[0].qtip, odin.doFocus,400);
								else
									$h.alert('系统提示',(obj.getAttribute("titleLabel")==null?odin.ext.get(spanId).dom.getAttribute("label"):obj.getAttribute("titleLabel"))+":"+Ext.query('#'+spanId+'+div')[0].qtip, odin.doFocus,400);
							else
							odin.alert(odin.ext.get(spanId).dom.getAttribute("label")+":"+Ext.query('#'+spanId+'+div')[0].qtip, odin.doFocus,null,400);
						}else if(obj.title){
							if(parent.$h)
							parent.$h.alert('系统提示',odin.ext.get(spanId).dom.getAttribute("label")+":"+obj.title, odin.doFocus,400);
							else
							odin.alert(odin.ext.get(spanId).dom.getAttribute("label")+":"+obj.title, odin.doFocus,null,400);
						}else{
							odin.alert(errtitle + '“' + odin.ext.get(spanId).dom.getAttribute("label") + '”输入项的值不符合要求，请重新输入！', odin.doFocus,null,400);
						}
						
						return false;
					}
				}
			}
		}
		return true;
	},
	/**
	 * 获取字符串字节长度
	 */
	getStringByteLength: function(val){

     var Zhlength=0;// 全角
     var Enlength=0;// 半角
    
     for(var i=0;i<val.length;i++){
         if(val.substring(i, i + 1).match(/[^\x00-\xff]/ig) != null)
        Zhlength+=1;
        else
        Enlength+=1;
     }
     // 返回当前字符串字节长度
     return (Zhlength*2)+Enlength;
   },
	/**
	 * 通过名字取得对象，支持combo直接取
	 */
	getCmpByName:function(name) {
		var obj = null;
		obj = Ext.getCmp(name);
		if (obj == null) {
			obj = Ext.getCmp(name + "_combo");
		}
		return obj;
	},
	cueCheckObj:null,
	isSelectText:true,
	setInvalidMsg:function(id , nowMsg){
		var obj = odin.ext.getCmp(id);
		if(obj){
			obj.invalidText = nowMsg;
		}else{
			obj = odin.ext.getCmp(id+"_combo");
			obj.invalidText = nowMsg;
		}
	},
	doFocus:function (){
		if(odin.cueCheckObj!=null&&odin.cueCheckObj){
			try{
				if (odin.isSelectText==true) {
					odin.ext.getCmp(odin.cueCheckObj.name).focus(true);
				}
				else {
					odin.cueCheckObj.focus();
				}
				odin.cueCheckObj = null;
			}catch(e){
				var comboName = odin.cueCheckObj.name+"_combo";
				if(odin.ext.getCmp(comboName)){
					if(odin.isSelectText==true){
						odin.ext.getCmp(comboName).focus(true);
					}else{
						document.getElementById(comboName).focus();
					}
				}
			}
		}
	}
	/******************************统一校验结束****************************************/
	,
	/*********select标签的两个ftl模版*************/
	showSelectCodeFtl:new Ext.XTemplate( /**显示代码的同时显示值的ftl文件，主要给下拉框用**/
		'<tpl for="."><div class="x-combo-list-item">',
		'{key}&nbsp;&nbsp;{value}',
		'</div>',
		'</tpl>'
	),
	showValueAndFilterCodeFtl:new Ext.XTemplate( /**只显示值的同时要根据输入的代码来智能选择值的ftl文件，主要给下拉框用**/
		'<tpl for="."><div class="x-combo-list-item">',
		'{value}',
		'</div>',
		'</tpl>'
	)
	/***********end  ftl****************/
	,
	getSelectDataToArray:function(selectId){ /***提取select组件的数据，将其转化成需要的数组型数据******/
		var store = odin.ext.getCmp(selectId).store;
		var length = store.getCount();
		var arrayData = new Array(length);
		for(i=0;i<length;i++){
			var temp = new Array(2);
			temp[0] = store.getAt(i).get('key');
			temp[1] = store.getAt(i).get('value');
			arrayData[i] = temp;
		}
		odin.cueSelectArrayData = arrayData;
	},
	cueSelectArrayData:new Array(0),
	accEditGridSelectColSelEve: function(record,index){ /***对编辑表格的下拉编辑时选择事件的默认处理***/
		if(this.fireEvent('beforeselect', this, record, index) !== false){
            this.setValue(record.data[this.valueField || this.displayField]);
            this.collapse();
            this.fireEvent('select', this, record, index);
        }
		odin.getSelectDataToArray(this.getId());
	},
	/**
	 * 普通通用查询
	 * @param {Object} params(为json对象，有两项，分别为querySQL即查询的sql或hql，还有就是sqlType，其表示查询的方式，可以为"SQL"或"HQL")
	 * @param {Object} succFunc 查询成功后要执行的函数
	 * @param {Object} failFunc 查询失败后要执行的函数
	 * @param {Object} sync 布尔类型，true则为同步，默认为false，即异步操作
	 * @param {Object} isMask 是否遮罩
	 */
	commonQuery:function(params,succFunc,failFunc,sync,isMask){ 
		var url = contextPath+"/common/commQueryAction.do?method=query";
		var req = odin.Ajax.request(url,params,succFunc,failFunc,sync,isMask);
		return req;
	},
	/**
	 * 为select store对象根据一定的查询条件和过滤条件重新加载数据
	 * @param {Object} objId 要加载数据的组件id
	 * @param {Object} aaa100 代码类别
	 * @param {Object} aaa105 参数分类
	 * @param {Object} filter 过滤条件
	 * @param {Object} isRemoveAllBeforeAdd 加载之前是否要清除以前的数据，默认清除（暂时没用，即都清除以前数据）
	 */
	loadDataForSelectStore:function(objId,aaa100,aaa105,filter,isRemoveAllBeforeAdd){
		var params = {};
		/*---------  现在统一走SysCodeAction来查询了   
		params.querySQL = " select * from v_aa10 where aaa100='"+aaa100+"' ";
		if(aaa105!=null&&aaa105!=""){
			params.querySQL += " and aaa105='"+aaa105+"' ";
		}
		if(filter!=null&&filter!=""){
			params.querySQL += " and ("+filter+") ";
		}
		params.querySQL=params.querySQL+" order by aaa102";
		params.sqlType = "SQL";
		var req = odin.commonQuery(params,odin.ajaxSuccessFunc,null,false,false);
		var data = odin.ext.decode(req.responseText).data.data;
		var selectData = null;
		if(data!=null&&data.length>0){
			selectData = new Array(data.length);
			for(i=0;i<data.length;i++){
				selectData[i] = {};
				selectData[i].key = data[i].aaa102;
				selectData[i].value = data[i].aaa103;
			}
		}
		//if (selectData != null) {
			if (isRemoveAllBeforeAdd == false) {
			//
			}
			else {
				odin.reSetSelectData(objId, selectData);
			}
		//}
		*/
		//通过SysCodeAction查询
		params.aaa100 = aaa100;
		params.aaa105 = aaa105;
		params.filter = filter;
		var req = odin.Ajax.request(contextPath+"/common/sysCodeAction.do?method=querySelectCodeValues",params,odin.ajaxSuccessFunc,null,false,false);
		var data = odin.ext.decode(req.responseText).data;
		var selectData = null;
		if(data!=null&&data.length>0){
			selectData = new Array(data.length);
			for(i=0;i<data.length;i++){
				selectData[i] = {};
				if(data[i].id){
					selectData[i].key = data[i].id.aaa102;
					selectData[i].value = data[i].id.aaa103;
				}else{
					selectData[i].key = data[i].aaa102;
					selectData[i].value = data[i].aaa102;
				}
				selectData[i].params = data[i].eaa101;
			}
		}
		odin.reSetSelectData(objId, selectData);
	},/**
	 * 为select store对象根据一定的查询条件和过滤条件重新加载数据 ——  采用异步方式
	 * @param {Object} objId 要加载数据的组件id
	 * @param {Object} aaa100 代码类别
	 * @param {Object} aaa105 参数分类
	 * @param {Object} filter 过滤条件
	 * @param {Object} isRemoveAllBeforeAdd 加载之前是否要清除以前的数据，默认清除（暂时没用，即都清除以前数据）
	 */
	loadDataForSelectStore2:function(objId,aaa100,aaa105,filter,isRemoveAllBeforeAdd){
		var params = {};
		params.aaa100 = aaa100;
		params.aaa105 = aaa105;
		params.filter = filter;
		var req = odin.Ajax.request(contextPath+"/common/sysCodeAction.do?method=querySelectCodeValues",params,function(res){
			var data = res.data;
			var selectData = null;
			if(data!=null&&data.length>0){
				selectData = new Array(data.length);
				for(i=0;i<data.length;i++){
					selectData[i] = {};
					if(data[i].id){
						selectData[i].key = data[i].id.aaa102;
						selectData[i].value = data[i].id.aaa103;
					}else{
						selectData[i].key = data[i].aaa102;
						selectData[i].value = data[i].aaa103;
					}
					selectData[i].params = data[i].eaa101;
				}
			}
			odin.reSetSelectData(objId, selectData);
		},null,true,false);
	},
	/**
	 * 一次性异步加载参数如下的所有相对应的下拉框选项信息
	 * 如：[{id:"AAC005_combo",aaa100:"AAC005",aaa105:"",filter:"",isRemoveAll:true},
	 * {id:"AKB011_combo",aaa100:"AKB011",aaa105:"",filter:"",isRemoveAll:true}]
	 * @param {} selectCodeInfo
	 */
	loadDataForSelectStoreBatch:function(selectCodeInfo,loadFinishFunc){
		var params = {};
		params.codeInfo = odin.encode(selectCodeInfo);
		var req = odin.Ajax.request(contextPath+"/common/sysCodeAction.do?method=querySelectCodeValuesBatch",params,function(res){
			var data = res.data;
			for(var key in data){
				var selectData = null;
				if(data[key]!=null&&data[key].length>0){
					selectData = new Array(data[key].length);
					for(i=0;i<data[key].length;i++){
						selectData[i] = {};
						if(data[key][i].id){
							selectData[i].key = data[key][i].id.aaa102;
							selectData[i].value = data[key][i].id.aaa103;
						}else{
							selectData[i].key = data[key][i].aaa102;
							selectData[i].value = data[key][i].aaa103;
						}
						selectData[i].params = data[key][i].eaa101;
					}
				}
				odin.reSetSelectData(key, selectData);
				//odin.setListWidth(odin.ext.getCmp(key+"_combo"));
			}
			if(typeof radow_select_default != 'undefined'){
				for(var i=0;i<radow_select_default.length;i++){
					var temp = radow_select_default[i];
					odin.setSelectValueReal(temp.id,temp.newvalue);
				}
			}
			if(loadFinishFunc){
				loadFinishFunc();
			}
		},null,true,false);
	},
	ajaxSuccessFunc:function(responseTxt){ //空的ajax调用成功的处理函数
		//
	},/****对时间控件的一个readonly处理*****/
	setDateReadOnly:function(id,isReadOnly){
		document.getElementById(id).readOnly = isReadOnly;
		if(isReadOnly==false){
			odin.ext.getCmp(id).onSelect = odin.dateCanSetValue;
		}else{
			odin.ext.getCmp(id).onSelect = odin.dateCanNotSetValue;
		}
	},
	dateCanSetValue:function(m,d){
		this.setValue(d);
		this.fireEvent('select', this, d);
		this.menu.hide();
		if(document.getElementById(this.getId()).onchange){
			document.getElementById(this.getId()).onchange();
		}
	},
	dateCanNotSetValue:function(m,d){
		//
	},
	/*********end 对时间控件的一个readonly处理********/
	/****对下拉控件的一个readonly处理*****/
	setComboReadOnly:function(id,isReadOnly){
		document.getElementById(id).readOnly = isReadOnly;
		if(isReadOnly==false){
			odin.ext.getCmp(id+'_combo').onSelect = odin.comboCanSetValue;
		}else{
			odin.ext.getCmp(id+'_combo').onSelect = odin.comboCanNotSetValue;
		}
	},
	comboCanSetValue: function(record, index){
		if (this.fireEvent('beforeselect', this, record, index) !== false) {
			this.setValue(record.data[this.valueField || this.displayField]);
			this.collapse();
			this.fireEvent('select', this, record, index);
		}
	},	
	hasMsgOrMask : function() {
		return (Ext.MessageBox && Ext.MessageBox.isVisible()) || Ext.get(document.body).isMasked();
	},
	comboFocus : function(combo) {
		try {
			if (eval("comboSetFocusForInit_" + combo.getId())) { // 下拉框不显示标志
				eval("comboSetFocusForInit_" + combo.getId() + "=false"); // 使用后还原
				return;
			}
		} catch (e) {
		}
		try {
			if (isQuerying) { // 正在查询，则不显示下拉框
				return;
			}
		} catch (e) {
		}
		if (odin.hasMsgOrMask()) {// 有遮罩，则不显示下拉框
			combo.triggerBlur();
			return;
		}
		combo.preValue = document.getElementById(combo.getId()).value;
		if (!(combo.list && combo.list.isVisible())) {
			combo.onTriggerClick();
		}
	},
	comboBlur : function(combo) {
		if (combo.list && combo.list.isVisible()) {
			combo.list.hide();
		}
	},
	
	comboCanNotSetValue:function(record, index){
		if(this.fireEvent('beforeselect', this, record, index) !== false){ }
	},
	/*****end 对下拉控件的一个readonly处理*****/
	/**
	 * loadPageInTab 在Tab中显示指定url的页面（注意，请在业务页面中的JS调用此喊出 ）
	 * @param {} aid    模块编码或者自定义字符串，如果需要保证只打开一个模块，请传入模块编码
	 * @param {} url    页面地址，不包含context， 例如 /page/.....
	 * @param {} forced 是否强制打开一个新tab
	 * @param {} text   标题，如果为空则取对应模块的名称
	 * @param {} autoRefresh 当forced为false，即不强制打开时，是否还自动刷新页面  jinwei add 2010.11.24
	 */
	loadPageInTab : function(aid, url, forced, text, autoRefresh) {
		if(odin.isWorkpf){
			qtobj.openNewTab(contextPath + url,text);
		}else{
			var treenode = parent.tree.getNodeById(aid);
			var title;
			if (treenode) {
				if (text)
					parent.addTab(text, treenode.id, contextPath + url, forced,autoRefresh);
				else
					parent.addTab(treenode.text, treenode.id, contextPath + url,forced, autoRefresh);
			} else {
				if (text)
					parent.addTab(text, aid, contextPath + url, forced,autoRefresh);
				else
					parent.addTab("", aid.id, contextPath + url, forced,autoRefresh);
			}
		}
	},
	doChangeDate: function(obj){ //对时间的一个处理，即当输入“Ymd”这种格式的时间时自动将其转化成“Y-m-d”这种日期格式的数据
		var date;
		if(obj.value.indexOf("-")==-1){
			date = Date.parseDate(obj.value,'Ymd');
		}else{
			date = Date.parseDate(obj.value,'Y-m-d');
		}
		obj.value = date.format('Y-m-d');
	},
	renderDate: function(dateVal){ //这个方法主要是为了解决编辑表格里的时间编辑列无法赋初值的问题，可根据需要修改下“Y-m”字符
		if (!dateVal || dateVal == "") {
			//dateVal = new Date();
			return "";
		}
		else {
			if (typeof dateVal == 'string') {
				dateVal = Date.parseDate(dateVal, 'Y-m-d');
			}
		}
		return Ext.util.Format.date(dateVal, 'Y-m-d');
	},
	/***********浮动div所使用的js**********/
	ClientWidth: function(){
		var theWidth = 0;
		if (window.innerWidth) {
			theWidth = window.innerWidth
		}else if (document.documentElement && document.documentElement.clientWidth) {
				theWidth = document.documentElement.clientWidth
		}else if (document.body) {
			theWidth = document.body.clientWidth
		}
		return theWidth;
	},
	ClientHeight: function(){
		var theHeight = 0;
		if (window.innerHeight) {
			theHeight = window.innerHeight
		}
		else 
			if (document.documentElement && document.documentElement.clientHeight) {
				theHeight = document.documentElement.clientHeight
			}
			else 
				if (document.body) {
					theHeight = document.body.clientHeight
				}
		return theHeight;
	},
	ScrollTop: function(){
		var theSTop = 0;
		if (document.body && odin.ext.isIE) {
			theSTop = document.body.scrollTop
		} else if (document.documentElement) {
			theSTop = document.documentElement.scrollTop + document.body.scrollTop;
		}
		return theSTop;
	},
	close_event: function(){
		control = true;
		document.getElementById('floatZc').style.display = "none";
	},
	/***********end 浮动div所使用的js**********/	
	onKeyDown: function(){ //禁止回退即backspace
		var  event= window.event||arguments[0];
		var ele = event.srcElement||event.target;
		var type = ele.type;
		var keyCode = event.keyCode || event.which || event.charCode;
		try {
			if (keyCode == 8 && ele.readOnly==true) {//处理readonly时  系统自动退出的问题
				event.returnValue = false;
			}
		}catch(e){
		}

		// 智能审核场景，F8按钮的操作
		if (keyCode == 119) {
			parent.curparent = window;
			if (MDParam == null) {
				return;
			}
			var src = contextPath;
			src += "/radowAction.do?method=doEvent&pageModel=cm&bs=intelligent.CmRuleConfig&initParams="+MDParam.functionid;
			var initParams = MDParam.functionid;
			// alert(Ext.util.JSON.encode(MDParam));
			var uptype = MDParam.uptype;
			if (MDParam.zyxx != "" && MDParam.zyxx != "{}") {// 增加一个管理员
				if(parent != this && parent.odin.openWindow){
					parent.odin.openWindow("pupWindowC0","智能审核配置--" + MDParam.title,src,0.95,0.95);
				}else{
					odin.openWindow("pupWindowC0","智能审核配置--" + MDParam.title,src,0.95,0.95);
				}
				/*doOpenPupWinOnTop(src, "智能审核配置--" + MDParam.title, 0.95,0.95, initParams);*/
			} else {
				odin.info("【<b>" + MDParam.title + "</b>】目前配置为非业务经办模块，不能配置智能审核！");
			}
			if (Ext.isIE) {
				event.keyCode = 0;
				event.returnValue = false;
			} else {
				event.preventDefault();
				event.stopPropagation();
			}
			return;
		}
		try {
			if ((event.keyCode == 8) && (type != "text" && type != "textarea" && type != "password")) {
				if (Ext.isIE) {
					event.keyCode = 0;
					event.returnValue = false;
				} else {
					event.preventDefault();
					event.stopPropagation();
				}
			}
			//将回车变成按下tab键
			if (type != 'button' && type != 'textarea' && event.keyCode == 13 ) {
				if(odin.isWorkpf) event.preventDefault();
				if (event.which) {
					 odin.autoNextElement(ele);
				}else{
					event.keyCode = 9;
				}
				var formElement = odin.ext.getCmp(ele.name);
				var xtype = formElement.getXType();
				if (xtype && (xtype == 'datefield' || xtype == 'combo' ||xtype=='lovcombo')) {
					formElement.fireEvent("change", formElement);
				}
			}
		}catch(e){
		}
	},
	autoNextElement:function(ele){ //jinwei 2011.9.22 自动跳转到下个元素去
		var childs = document.forms[0].elements;
        for (var i = 0; i < childs.length; i++) {
            var q = (i == childs.length - 1) ? 0 : i + 1;// if last element : if any other
            if (ele == childs[i]) {
                while (true) { //跳过隐藏域、button、fieldset等元素
                    var isFound = true;
                    if(childs[q].type == 'hidden' || childs[q].type == 'button' || childs[q].tagName == "FIELDSET" || childs[q].disabled == true/*|| childs[q].readOnly == true*/){
	                    isFound = false;
                    }else{
                    	var _id = childs[q].id;
                    	if(childs[q].readOnly == true){
                    		isFound = false;
                    	}
                    	if(typeof _id !='undefined'){
	                    	if(_id.indexOf("_combo")>0){
	                    		if(odin.ext.getCmp(_id).readOnly !== true){
	                    			isFound = true;
	                    		}
	                    		_id = _id.replace(/_combo/gi,'');
	                    	}
	                    	var _ele = document.getElementById(_id+"TdId");
	                    	if(_ele && _ele!='null'){
	                    		if(_ele.style.display=='none'){
	                    		isFound = false;
	                    	    }
	                    	}
	                    	
                    	}
                    }
                    if(!isFound){
                    	q++;
	                    if (q == (childs.length - 1)) {
	                        break;
	                    }
                    }else{
                    	break;
                    }
                }
                ele.blur();
                var nextel=childs[q];
                if(Ext.getCmp(ele.id) && Ext.getCmp(ele.id).nextElement){
                	nextel= document.getElementById(Ext.getCmp(ele.id).nextElement)
                }
                nextel.focus();
                var combo = document.getElementById(nextel.id + "_combo")
                if (combo) {
                    combo.focus();
                }
                break;
            }
        }
	},
	/*  
	*    round(Dight,How):数值格式化函数，Dight要  
	*    格式化的  数字，How要保留的小数位数。  
	*/  
	round: function(Dight, How){
		Dight = Math.round(Dight * Math.pow(10, How)) / Math.pow(10, How);
		return Dight;
	},
	/**
	 * 处理全选的问题
	 * @param {Object} gridId
	 * @param {Object} obj
	 * @param {Object} fieldName
	 */
	selectAllFunc:function(gridId,obj,fieldName){
		var store = odin.ext.getCmp(gridId).store;
		var value = false;
		if(obj.checked){
			value = true;
		}
		var length = store.getCount();
		for(index=0;index<length;index++){
			store.getAt(index).set(fieldName,value);
			//alert(index+":"+store.getCount());
		}
	},
	getSysdate : function() { // 取得数据库时间
		var params = {};
		params.sqlType = "SQL";
		params.querySQL = "@_sAnQMaRvSymKDwZt0lEyEqY9ds6ZPFSwqMLdfYvZDtE=_@";
		var req = odin.commonQuery(params, odin.blankFunc, odin.blankFunc, false, false);
		var data = odin.ext.decode(req.responseText).data.data[0];
		var value = data.sysdate;
		value = odin.Ajax.formatDate(value);
		value = Date.parseDate(value, 'Y-m-d');
		return value;
	},
	selectAllFuncForE3:function(gridId,obj,fieldName){
		if(obj.getAttribute('alowCheck')=="false"){
            return;
        }
		var store = odin.ext.getCmp(gridId).store;
		var value = false;
		if (obj.className == 'x-grid3-check-col-on') {
			obj.className = "x-grid3-check-col";
		}else{
			value = true;
			obj.className = 'x-grid3-check-col-on';
		}
		var length = store.getCount();
		for(index=0;index<length;index++){
			store.getAt(index).set(fieldName,value);
		}
	},
	doAccSpecialkey:function(e){
		if(e.getKey()==e.ENTER){
			e.keyCode = 9;
			if(this.getXType()=="combo"){
				var nowValue = e.target.value;
				var oldValue = this.getValue();
				if(nowValue!=oldValue){
					this.setValue(nowValue);
				}
			}
		}
		if(e.isSpecialKey()){
            this.fireEvent("specialkey", this, e);
        }
	},
	
	gridWalkRows : function(grid, row, col, step, fn, scope){     
        var cm = grid.colModel, clen = cm.getColumnCount();
        var ds = grid.store, rlen = ds.getCount();
        
        if(step < 0){
            if(row < 0){ 
                row = rlen - 1;                                               
                while(--col >= 0){                    
                    if(fn.call(scope || this, row, col, cm) === true){
                        return [row, col];
                    }
                }                               
            }else{                
                return [row, col];
            }           
        } else {                         
            if(row >=rlen){
                row = 0;
                while(++col < clen){                    
                    if(fn.call(scope || this, row, col, cm) === true){                        
                        return [row, col];
                    }
                }     
            }else{
               return [row, col];  
            }
                
        }
        return null;
	},
	
	doAccOnEditorKey : function(field, e) { // grid特殊按键的处理
		var k = e.getKey(), newCell, g = this.grid, ed = g.activeEditor, shiftKey = e.shiftKey, ctrlKey = e.ctrlKey;
		var isAutoNext=true;//默认回车自动进入下一格
		if (k == e.ENTER || k == e.TAB) {
			var cueRow = g.activeEditor.row;
			var cueCol = g.activeEditor.col;
			var colCfg = g.getColumnModel().config[cueCol];
			isAutoNext=colCfg.isAutoNext;
			if(true == colCfg.enterAutoAddRow){
				//radow.addGridEmptyRow(g.getId(),cueRow+1);
				radow.cm.doGridEnterAddRow(g.getId(),cueRow,cueCol,colCfg.dataIndex);
			}
			if (field.getXType() == "combo") { // 下拉框特殊处理
				field.onViewClick(false);
				field.triggerBlur();
			}
			if (k != e.TAB) { // tab键时如果处理则焦点会有问题
				e.stopEvent();
			}
			ed.completeEdit();
			if (shiftKey) {
			    if(g.moveWay !== undefined && g.moveWay == "cell"){
			       newCell = odin.gridWalkRows(g, ed.row -1, ed.col, -1, this.acceptsNav, this);
			    }else{
			       newCell = g.walkCells(ed.row, ed.col - 1, -1, this.acceptsNav, this); 
			    }				
			} else {
			    if(g.moveWay !== undefined && g.moveWay == "cell"){
                   newCell = odin.gridWalkRows(g, ed.row + 1, ed.col, 1, this.acceptsNav, this);
                }else{
				   newCell = g.walkCells(ed.row, ed.col + 1, 1, this.acceptsNav, this);
				}
			}
		} else if (ctrlKey && (k == e.UP || k == e.DOWN)) {
			e.stopEvent();
			ed.completeEdit();
			if (k == e.UP) {
				if(g.editswitch !== undefined && g.editswitch == "cell"){
                   newCell = odin.gridWalkRows(g, ed.row -1, ed.col, -1, this.acceptsNav, this);
                }else{
                   newCell = g.walkCells(ed.row, ed.col - 1, -1, this.acceptsNav, this); 
                }
			} else {
				if(g.editswitch !== undefined && g.editswitch == "cell"){
                   newCell = odin.gridWalkRows(g, ed.row + 1, ed.col, 1, this.acceptsNav, this);
                }else{
                   newCell = g.walkCells(ed.row, ed.col + 1, 1, this.acceptsNav, this);
                }
			}
		} else if (k == e.ESC) {
			ed.cancelEdit();
		}
		if (newCell) {
			try{
				if(isAutoNext!=false && isAutoNext!='false'){
					g.startEditing(newCell[0], newCell[1]);
				}
			}catch(exception){
				g.startEditing(newCell[0], newCell[1]);
			}
						
		}
	},
	/**
	 * 对于统计时的默认render函数，其所做的就是将统计结果保留小数点后两位
	 * @param {Object} v
	 * @param {Object} params
	 * @param {Object} data
	 */
	defaultSumRender:function(v, params, data){
		return odin.round(v,2);
	},
	/**
	 * 自动进入编辑表格某行某列的编辑模式
	 * @param {Object} gridId 表格ID或属性名
	 * @param {Object} row  行号
	 * @param {Object} col  列号
	 */
	startEditing:function(gridId,row,col){
		odin.ext.getCmp(gridId).startEditing(row,col);
	},
	/**
	 * 判断某个tab页是否是处于激活状态，这个tab页是整个系统的tab页，非某个功能模块里的tab页
	 * @param {Object} tabid  tab的id，可传可不传，不传时默认取的是functionid
	 */
	isActivedByTabId:function(tabid){
		if(tabid){
			return top.frames[1].tabs.getActiveTab().getId() == tabid;
		}else{
			return top.frames[1].tabs.getActiveTab().getId() == MDParam.functionid;
		}
	},
	toHtmlString : function(str) {// 转换成html的格式
		if (str == null) {
			str = "";
		}
		str = ""+str;
		// 空格符转换
		str = str.replace(/ /g, "&nbsp;");
		// 换行符转换
		str = str.replace(/\r\n/g, "<br>");
		str = str.replace(/\n\r/g, "<br>");
		str = str.replace(/\r/g, "<br>");
		str = str.replace(/\n/g, "<br>");
		if (str.indexOf("<font&nbsp;") != -1) { // 字体的操作还原
			str = str.replace("<font&nbsp;", "<font ");
		}
		return str;
	},	
	closeMsgBox : function() {
		Ext.MessageBox.hide();
	},
	/**
	 * 表格加载数据后的统一回调函数，目前用作统一处理没查出数据时的异常提示
	 * @author jinwei 2013.3.35
	 */
	doCommLoad:function(obj){
		var response = obj.reader.jsonData;
		if(response && response.messageCode=='1'){
			//即发生了查询异常，返回空数据
			odin.error(response.mainMessage);
		}
		//alert(odin.encode(response));
	},
	/**
	 * 公共的将表格下拉列的代码转成说明文字，或者说value，如01转成“单位A”
	 * @author jiwnei 2013.3.26
	 * @param {} name
	 * @param {} data
	 * @param {} value
	 * @param {} params
	 * @param {} record
	 * @param {} rowIndex
	 * @param {} colIndex
	 * @param {} ds
	 */
	doGridSelectColRender:function(gridId,name,value, params, record,rowIndex,colIndex,ds){
		if(gridId == null || gridId=="" || gridId=="null"){
			return value;
		}
		var selectCol = odin.getGridColumn(gridId, name);
		var comboObj = null;
		if (selectCol == null) {
			return value;
		} else {
			comboObj = selectCol.editor;
		}
		var store = comboObj.store;
		store.clearFilter();
        var length = store.getCount();
        for(var i=0;i<length;i++)
        {
            var rs = store.getAt(i);
            if(rs.get('key')==value){
                value = comboObj.displayField=='key'?rs.get('key'):rs.get('value');
                break;
            }
        }
		return value;
	},
	/**
	 * 获取表格下拉列的key对应的value值
	 * @param {} gridId
	 * @param {} name
	 * @param {} value
	 */
	getGridSelectColValue:function(gridId,name,value){
		if(gridId == null || gridId=="" || gridId=="null"){
			return value;
		}
		var selectCol = odin.getGridColumn(gridId, name);
		var comboObj = null;
		if (selectCol == null) {
			return value;
		} else {
			comboObj = selectCol.editor;
		}
		var store = comboObj.store;
		store.clearFilter();
        var length = store.getCount();
        for(var i=0;i<length;i++)
        {
            var rs = store.getAt(i);
            if(rs.get('key')==value){
                value = rs.get('value');
                break;
            }
        }
		return value;
	},
	createFilterFn:function(property, value, anyMatch, caseSensitive){
        if(Ext.isEmpty(value, false)){
            return false;
        }
        //value = this.data.createValueMatcher(value, anyMatch, caseSensitive);
        return function(r){
            //return value.test(r.data[property]);
        	if((r.get("key")+"@"+r.get("value")).match(new RegExp(value.replace(/ /gi,".*"),"gi"))!=null){
        		return true;
        	}
        	return false;
        };
    },
    /**
     * 启动运行名为pjobName的并发任务
     * @param {} pjobName 任务名
     * @param execParam -- 并发任务执行参数
     */
    startPJob:function(pjobName,execParam){
    	var url = contextPath+"/radowAction.do?method=doEvent&pageModel=pages.sysmanager.pjob.PJobManage";
    	var param = {};
    	param.eventParameter = pjobName;
    	if(typeof execParam!='undefined' && ""!=execParam){
    		param.eventParameter += "@"+execParam;
    	}
    	param.eventNames = "runPJob";
    	var req = odin.Ajax.request(url,param,odin.ajaxSuccessFunc,odin.ajaxSuccessFunc,null,false,false);
    	var res = odin.ext.decode(req.responseText);
		if(res.messageCode=='0'){
			odin.confirm("启动成功！点击确定后将自动进入任务执行进度监控界面，你确定需要进入吗？",function(btn){
				if(btn=='ok'){
					odin.monitorPJob(pjobName);
				}
			});
		}else{
			odin.error(res.mainMessage);
		}
    },
    /**
     * 打开定时任务监控窗口
     * @param {} name
     */
    monitorPJob:function(name){
		doOpenPupWin("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.pjob.PJobMonitor&initParams="+name,"并发任务监控",700,388);
	},
	/**
	 * 根据格式化字符串进行掩码输入控制
	 * @param {} input
	 * @param {} format
	 */
	commInputMask:function(input,format){
		var v = input.value;
		if ("Y-m-d" == format) {
			if (v.match(/^\d{4}$/) !== null) {
				input.value = v + '-';
			} else if (v.match(/^\d{4}\-\d{2}$/) !== null) {
				input.value = v + '-';
			} else if (v.match(/^\d{4}\-\d{2}\-\d{2,}$/) != null) {
				input.value = v.substr(0, 10);
			}
		}
	},
	/**
	 * 修复tab下带图标下拉框出错后错误红色图标和trriger图标重叠
	 * @param {} field
	 * @param {} msg
	 */
	trrigerCommInvalid:function(field,msg){
		var offsetLeft = field.errorIcon.dom.offsetLeft;
		var width = field.width;
		if(offsetLeft<width){
			field.errorIcon.dom.style.left = (offsetLeft+17)+"px";
		}
	}
};     
Ext.namespace('odin.ext');          
odin.ext = Ext;

//将日期向后推n个月
Date.addMonth=function(d,n){
	var year=d.getYear();
	var month=d.getMonth()+n;
	var date=d.getDate();
	return new Date(year,month,date);
}	
//将日期转换成整型的年月
Date.getYM=function(d){
	var year=d.getYear();
	var month=d.getMonth()+1;
	return year*100+month;
}	
//将整型的年月向后推n个月
Date.addMonthYM=function(ym,n){
	var year=Math.floor(ym/100);
	var month=ym%100-1;
	var d=new Date(year,month,1);
	d=this.addMonth(d,n);
	return this.getYM(d);
}
document.onkeydown = odin.onKeyDown;
/**
 * 整合Commform而增加进来的JS —— jinwei 2013.3.5
 */
/**
 * 处理下拉框宽度自动设置问题
 * @param {} combo
 */
odin.setListWidth = function(combo) {
		var width = combo.list.getWidth();
		var comboElements = combo.view.all.elements;
		for (var i = 0; i < comboElements.length; i++) {
			var value = comboElements[i].outerText || comboElements[i].textContent;;
			if (value == null) {
				continue;
			}
			var valueWidth = value.replace(/[^\u0000-\u00ff]/g, "aa").length * 6.5 + 25;
			if (width < valueWidth) {
				width = valueWidth;
			}
		}
		if (combo.mode == "remote" && width < 260) {
			width = 260;
		}
		combo.list.setWidth(width);
		combo.innerList.setWidth('auto');
}
// 增加多选框MultiCombo
odin.doMultiSelectWithAll = function(combo, record, index) {
	if (record.get('key') == 'all') {
		var checked = record.get('checked');
		combo.deselectAll();
		record.set('checked', checked);
	} else {
		if (combo.store.getAt(0).get('key') == 'all' && combo.store.getAt(0).get('checked') == true) {
			combo.store.getAt(0).set('checked', false);
		}
	}
}
/**
 * 取得grid的列
 */
odin.getGridColumn = function(gridName, fieldName) {
	if (gridName == null || !Ext.getCmp(gridName)) {
		return;
	}
	
	var gridColumnModel = null;
	
	try {
		gridColumnModel = Ext.getCmp(gridName).getColumnModel();
	} catch (e) {
		return;
	}
	if (!gridColumnModel) {
		return;
	}
	
	var column = null;
	for (var j = 0; j < gridColumnModel.getColumnCount(); j++) {
		if (gridColumnModel.getDataIndex(j) == fieldName) {
			column = gridColumnModel.getColumnById(gridColumnModel.getColumnId(j));
			break;
		}
	}
	return column;
};
/**
 * 编辑框的效果
 */
odin.renderEdit = function(value, params, record, rowIndex, colIndex, ds) {
	if (!ds) {// ds为空，分组行
		return value;
	}
	var e = {};
	var gridId = ds.baseParams.cueGridId;
	e.grid = Ext.getCmp(gridId);
	e.record = record;
	e.field = Ext.getCmp(gridId).getColumnModel().getDataIndex(colIndex);
	e.originalValue = value;
	e.value = value;
	e.row = rowIndex;
	e.column = colIndex;
	var col = odin.getGridColumn(gridId, e.field);
	if (col.editor && col.editable==true) {
		var type = col.editor.getXType();
		if (type == "numberfield" || type == "textfield" || type == "datefield" || type == "combo" || type == "textarea" || type == "timefield"||type=="ComboBoxWidthTree") {// 只操作字符串、数字、日期、下拉框格式的列
			params.css = "x-grid3-cell-enable";
		}
	}else{
		params.css = "x-grid3-cell-disable";
	}
	return value;
}
/**
 * tabpanel的布局调整，修正下拉框图标及grid滚动条bug
 * @param {} tab
 */
odin.doTabPanelLayout = function(tab) { // tabpanel的布局调整，修正下拉框图标及grid滚动条bug
//		var divList = tab.getEl().dom.getElementsByTagName('div');
//		for (var i = 0; i < divList.length; i++) {
//			var divId = divList.item(i).id;
//			if (document.getElementById('gridDiv_' + divId)) {
//				Ext.getCmp(divId).doLayout();
//				Ext.getCmp(divId).view.refresh(true);
//			} else{
//				var inputList = divList.item(i).getElementsByTagName('input');
//				for (var j = 0; j < inputList.length; j++) {
//					var inputItem = inputList.item(j);
//					var cmp = Ext.getCmp(inputItem.name);
//					if (cmp && typeof(cmp.hideTrigger) != "undefined" && !cmp.hideTrigger) {
//						var width = cmp.width;
//						cmp.setWidth(0);
//						if(Ext.isChrome){
//							width = width-17;
//						}
//						cmp.setWidth(width);
//						// cmp.trigger.setLeft(width-(Ext.isIE?3:0));
//					}
//				}
//			}
//		}
		var itab = !tab.getActiveTab ? tab : tab.getActiveTab();
	    var eList = Ext.DomQuery.select("div[@id^=gridDiv_],input[@id]", itab.getEl().dom);
	    var etmp = null;
	    var etagtmp = null;
	    var cmptmp = null;
	    var widthtmp = 0;
	    var girdPefix = "gridDiv_";
	    var inputList = [];
	    for (var i = 0; i < eList.length; i++) {
	        etmp = eList[i];
	        etagtmp = etmp.tagName.toLowerCase();	        
	        if (etagtmp == "div") {
	            cmptmp = Ext.getCmp(etmp.id.substr(girdPefix.length));	            
	            if(!cmptmp){
	                continue;
	            }	            
	            //if(!cmptmp.store || cmptmp.store.getCount()==0){
	                cmptmp.doLayout();
	                cmptmp.view.refresh(true);
	           // }	                           
	        } else if (etagtmp == "input" && etmp.type.toLowerCase() != "hidden") {
	            inputList.push(etmp);
	        }
	    }	
	    for (var j = 0; j < inputList.length; j++) {
			etmp = inputList[j];
			cmptmp = Ext.getCmp(etmp.name);
			if (cmptmp && typeof(cmptmp.hideTrigger) != "undefined"
					&& !cmptmp.hideTrigger) {
				widthtmp = cmptmp.width;
				if(typeof widthtmp == 'undefined') break;
				if (!Ext.isIE || !cmptmp.disabled) {
					widthtmp = widthtmp - 17;
				}
				etmp.style.width = widthtmp + "px";
			}
		}    
	};
	
/**
 * 打开子窗口
 */	
odin.openWindow = function(id,title,url,width,height,thisWin,isTopParentWin,modal){
	    var win = Ext.get(id);
	    //var onload = "doWinOnload()";
	    if (win) {
	        win.close();
	        return;
	    }
	    if (width <= 1) {
			if (width >= 0) {// 小数
				width = document.body.clientWidth * width;
			} else { // 负数
				width = document.body.clientWidth + width;
			}
		}
		if (height <= 1) {
			if (height >= 0) {// 小数
				height = document.body.clientHeight * height;
			} else { // 负数
				height = document.body.clientHeight + height;
			}
		}
		var thisw = thisWin;//当前窗口
		var parentWinScope = window;//父级窗口
		
		while(true&&isTopParentWin){//顶级父页面
			if(parentWinScope==parentWinScope.parent){
				break;
			}else{
				parentWinScope = parentWinScope.parent;
			}
		}
		if(modal){
			modal = true;
		}else{
			modal = false;
		}
	    win = new parentWinScope.Ext.Window({
	        id:id,
	        title:title,
	        layout:'fit',
	        width:width,
	        height:height,
	        closeAction:'close',
	        collapsible:true,
	        plain: false,
	        resizable: true,
	        thisWin: thisw,
	        modal: modal,
	        html: "<iframe style=\"background:white;border:none;\" id=\"iframe_"+id+"\" width=\"100%\" height=\"100%\" "+(onload == null ? "" : "onload=\"" + onload) + "\" src=\"" + url + "\"></iframe>"
	    });
	    win.show();
	}

//修正弹出窗口的移动问题，移动到浏览器外面，被flash覆盖等问题
Ext.useShims = true; // 指明浏览器中运用垫片效果
Ext.override(Ext.Window, {
			listeners : {
				move : function(in_this, x, y) {
					// max window weight and width, -20 because we always want
					// to see at least small part
					var maxX = Ext.getBody().getViewSize().width - 20;
					var maxY = Ext.getBody().getViewSize().height - 20;
					// new position
					x = parseInt(x);
					y = parseInt(y);
					if (x < 0 || x > maxX || y < 0 || y > maxY) { // 解决移动的时候移到浏览器外面的问题
						// fix if moved too far on top/left
						if (y < 0)
							y = 0;
						if (x < 0)
							x = 0;
						// fix if moved too far on down/right
						if (y > maxY)
							y = maxY - in_this.getHeight();
						if (x > maxX)
							x = maxX - in_this.getWidth();
						// tries to show whole window, if it's too big it will
						// go to left/top corner

						// move window on new position
						in_this.setPosition(x, y);
					}
					if (in_this.el.shim) { // 解决移动的时候底部的遮罩层与窗口left和top不对齐，宽度和高度与窗口不统一问题
						in_this.el.shim.setLeftTop(in_this.el.getLeft(true), in_this.el.getTop(true));
						in_this.el.shim.setHeight(in_this.el.getHeight());
						in_this.el.shim.setWidth(in_this.el.getWidth());
					}
				}
			}
		})

	/**
	 * Ext代码重写
	 */
	Ext.override(Ext.form.VTypes = function() {
		var C = /^[a-zA-Z_\u4e00-\u9fa5]+$/;
		var D = /^[a-zA-Z0-9_\u4e00-\u9fa5]+$/;
		var B = /^([\w]+)(.[\w]+)*@([\w-]+\.){1,5}([A-Za-z]){2,4}$/;
		var A = /(((https?)|(ftp)):\/\/([\-\w]+\.)+\w{2,3}(\/[%\-\w]+(\.\w{2,})?)*(([\w\-\.\?\\\/+@&#;`~=%!]*)(\.\w{2,})?)*\/?)/i;
		return {
			"email" : function(E) {
				return B.test(E)
			},
			"emailText" : "This field should be an e-mail address in the format \"user@domain.com\"",
			"emailMask" : /[a-z0-9_\.\-@]/i,
			"url" : function(E) {
				return A.test(E)
			},
			"urlText" : "This field should be a URL in the format \"http:/" + "/www.domain.com\"",
			"alpha" : function(E) {
				return C.test(E)
			},
			"alphaText" : "This field should only contain letters and _",
			"alphaMask" : /[a-z_]/i,
			"lt_cn_nm" : function(E) {
				return /^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(E)
			},
			"lt_cn_nmText" : "该输入项只能是汉字、字母或数字",
			"lt_cn_nmMask" : /[a-z0-9\u4e00-\u9fa5]/i,
			"lt_cn" : function(E) {
				return /^[a-zA-Z\u4e00-\u9fa5]+$/.test(E)
			},
			"lt_cnText" : "该输入项只能是汉字或字母",
			"lt_cnMask" : /[a-z\u4e00-\u9fa5]/i,
			"lt_nm" : function(E) {
				return /^[a-zA-Z0-9]+$/.test(E)
			},
			"lt_nmText" : "该输入项只能是字母或数字",
			"lt_nmMask" : /[a-z0-9]/i,
			"cn" : function(E) {
				return /^[\u4e00-\u9fa5]+$/.test(E)
			},
			"cnText" : "该输入项只能是汉字",
			"cnMask" : /[\u4e00-\u9fa5]/i,
			"lt" : function(E) {
				return /^[a-zA-Z]+$/.test(E)
			},
			"ltText" : "该输入项只能是字母",
			"ltMask" : /[a-z]/i,
			"nm" : function(E) {
				return /^[0-9]+$/.test(E)
			},
			"nmText" : "该输入项只能是数字",
			"nmMask" : /[0-9]/i,
			"isBeforeSysdate" : function(E) { // 校验日期不能大于当前日期
				return renderDate(E) <= renderDate(odin.getSysdate())
			},
			"isBeforeSysdateText" : "输入的日期不能大于当前日期",
			"isBeforeSysdateMask" : /[0-9]/i,
			"isYM" : function(value) { // 校验数字的年月
				if (value.length != 6) {
					return false;
				}
				if (value.substr(4) > 12) {
					return false;
				}
				if (value.substr(4) == 0) {
					return false;
				}
				return true;
			},
			"isYMText" : "该输入项只能是6位年月数字，且月份不能大于12或为00，如200808",
			"isYMMask" : /[0-9]/i,
			"isBeforeSysYM" : function(value) { // 校验数字的年月
				if (value.length != 6) {
					return false;
				}
				if (value.substr(4) > 12) {
					return false;
				}
				if (value.substr(4) == 0) {
					return false;
				}
				if (value > Ext.util.Format.date(odin.getSysdate(), 'Ym')) {
					return false;
				}
				return true;
			},
			"isBeforeSysYMText" : "该输入项只能是6位年月数字，且月份不能大于12或为00，且输入的年月不能大于当前年月",
			"isBeforeSysYMMask" : /[0-9]/i,
			"alphanum" : function(E) {
				return D.test(E)
			},
			"alphanumText" : "This field should only contain letters, numbers and _",
			"alphanumMask" : /[a-z0-9_]/i
		}
	}())
	
	
Ext.apply(Ext.data.Record.prototype,{set : function(name, value){
    if(String(this.data[name]) == String(value)){
        return;
    }
    //this.dirty = true;
    if(!this.modified){
        this.modified = {};
    }
    if(typeof this.modified[name] == 'undefined'){
        this.modified[name] = this.data[name];
    }
    this.data[name] = value;
    if(!this.editing){
        this.store.afterEdit(this);
    }       
}})







/*Ext.override(Ext.grid.EditorGridPanel, {
	
			onEditComplete : function(ed, value, startValue) {
		        this.editing = false;
		        this.activeEditor = null;
		        ed.un("specialkey", this.selModel.onEditorKey, this.selModel);
				var r = ed.record;
		        var field = this.colModel.getDataIndex(ed.col);
		        value = this.postEditValue(value, startValue, r, field);
		        if(this.forceValidation === true || String(value) !== String(startValue)){
		            var e = {
		                grid: this,
		                record: r,
		                field: field,
		                originalValue: startValue,
		                value: value,
		                row: ed.row,
		                column: ed.col,
		                cancel:false
		            };
		            if(this.fireEvent("validateedit", e) !== false && !e.cancel && String(value) !== String(startValue)){
		                r.set(field, e.value);
		                delete e.cancel;
		                this.fireEvent("afteredit", e);
		            }
		        }
		        this.view.focusCell(ed.row, ed.col);
		    }
});*/



