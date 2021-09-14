/*******************************************************************************
 * ************************** 浙大网新恩普软件有限公司 核三框架标签库的核心js文件 version:1.0.4
 * **************************
 */
var odin = {
	version : '1.0',
	/** 系统版本号* */
	defaultTitle : '系统提示',
	msg : '正在处理中...',
	msgCls : 'x-mask-loading',
	ajaxaSynchronous : false,// ajax默认的同步异步标志，false为同步，true为异步
	ajaxTimeout : (8 * 3600 * 1000), // 默认ajax请求为8个小时
	msgShow : true, // 显示消息，false不显示
	msgShow_confirm : true, // 显示消息，false不显示
	msgShow_alert : true, // 显示消息，false不显示
	msgShow_error : true, // 显示消息，false不显示
	msgShow_info : true, // 显示消息，false不显示
	msgShow_prompt : true, // 显示消息，false不显示
	msgShow_promptWithMul : true, // 显示消息，false不显示
	msgShow_progress : true, // 显示消息，false不显示
	lastEvent : null, // 最近一次按键事件
	isWorkpf : (navigator.userAgent.indexOf("Workpf") != -1), // 是否为个人工作台
	workpfVer : (navigator.userAgent.indexOf("Workpf") != -1 ? navigator.userAgent.substring(navigator.userAgent.indexOf("Workpf") + 7) : 0),// 个人工作台版本号
	toHtmlString : function(str) {// 转换成html的格式
		if (str == null) {
			str = "";
		}
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
	confirm : function(info, fun, title) {
		if (odin.msgShow == false || odin.msgShow_confirm == false) {
			return;
		}
		var boxtitle = odin.defaultTitle;
		if (title) {
			boxtitle = title;
		}
		info = odin.toHtmlString(info);
		Ext.MessageBox.show({
					title : boxtitle,
					msg : info,
					minWidth : 200,
					buttons : Ext.MessageBox.OKCANCEL,
					multiline : false,
					fn : fun,
					icon : Ext.MessageBox.QUESTION
				});
	},
	alert : function(info, fun, title) {
		if (odin.msgShow == false || odin.msgShow_alert == false) {
			return;
		}
		var boxtitle = odin.defaultTitle;
		if (title) {
			boxtitle = title;
		}
		info = odin.toHtmlString(info);
		Ext.MessageBox.show({
					title : boxtitle,
					msg : info,
					minWidth : 200,
					buttons : Ext.MessageBox.OK,
					multiline : false,
					fn : fun
				});
	},
	prompt : function(info, fun, title) {
		if (odin.msgShow == false || odin.msgShow_prompt == false) {
			return;
		}
		var boxtitle = odin.defaultTitle;
		if (title) {
			boxtitle = title;
		}
		info = odin.toHtmlString(info);
		Ext.MessageBox.prompt(boxtitle, info, fun);
	},
	promptWithMul : function(info, fun, title) {
		if (odin.msgShow == false || odin.msgShow_promptWithMul == false) {
			return;
		}
		var boxtitle = odin.defaultTitle;
		if (title) {
			boxtitle = title;
		}
		info = odin.toHtmlString(info);
		Ext.MessageBox.show({
					title : boxtitle,
					msg : info,
					minWidth : 200,
					buttons : Ext.MessageBox.OKCANCEL,
					multiline : true,
					fn : fun
				});
	},
	error : function(info, fun, title) {
		if (odin.msgShow == false || odin.msgShow_error == false) {
			return;
		}
		var boxtitle = odin.defaultTitle;
		if (title) {
			boxtitle = title;
		}
		info = odin.toHtmlString(info);
		info = "<font color=red>" + info + "</font>";
		Ext.MessageBox.show({
					title : boxtitle,
					msg : info,
					minWidth : 200,
					buttons : Ext.MessageBox.OK,
					multiline : false,
					fn : fun,
					icon : Ext.MessageBox.ERROR
				});
	},
	info : function(info, fun, title) {
		if (odin.msgShow == false || odin.msgShow_info == false) {
			return;
		}
		var boxtitle = odin.defaultTitle;
		if (title) {
			boxtitle = title;
		}
		info = odin.toHtmlString(info);
		Ext.MessageBox.show({
					title : boxtitle,
					msg : info,
					minWidth : 200,
					buttons : Ext.MessageBox.OK,
					multiline : false,
					fn : fun,
					icon : Ext.MessageBox.INFO
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
	closeMsgBox : function() {
		Ext.MessageBox.hide();
	},
	mask : function(msg) {
		Ext.get(document.body).mask(msg, odin.msgCls);
		maskInterval();
		if (maskint != null) {
			clearInterval(maskint);
		}
		maskint = setInterval(maskInterval, 100);
	},
	unmask : function() {
		Ext.get(document.body).unmask();
		clearInterval(maskint);
		maskint = null;
	},
	hasMsgOrMask : function() {
		return (Ext.MessageBox && Ext.MessageBox.isVisible()) || Ext.get(document.body).isMasked();
	},
	onFilterCode : function() {
		var obj = event.srcElement;
		if (obj.value == "") {
			Ext.get(obj).prev().value = '';
		} else {
			var reg = /^\d+/;
			if (obj.value.match(reg)) {
				if (obj.value.replace(/\d+/, '').length > 4) {
					Ext.get(obj).prev().value = obj.value.match(reg);
					obj.value = obj.value.replace(/\d+/, '').substring(4);
				} else {
					obj.value = '';
					Ext.get(obj).prev().value = '';
				}
			} else {
				if (Ext.get(obj).prev().value == '') {
					obj.value = '';
				} else {
					//
				}
			}
		}
	},
	changeCodeText : function(field) {
		Ext.get(field.getId()).next().value = Ext.get(field.getId()).next().value.replace(/\d+/, '').substring(4);
		if (Ext.get(field.getId()).next().value == "") {
			field.setValue('');
		}
	},
	setSelectValue : function(id, newvalue) {
		if (newvalue == null) {
			newvalue = "";
		}
		var value = newvalue.toString();
		// alert("value:"+value);
		// 针对分页下拉的处理
		if (value != null && value != "") {
			var store = Ext.getCmp(id + "_combo").store;
			var length = store.getCount();
			if (store.url != null && store.url != "") {
				var param = store.baseParams;
				param.query = value;
				var req = odin.Ajax.request(store.url, param, odin.blankFunc, odin.blankFunc, false, false);
				var data = Ext.decode(req.responseText).data;
				if (data != null && data.length == 1) {
					document.getElementById(id).value = data[0].key;
					Ext.getCmp(id + "_combo").setValue(data[0].value);
				} else {
					document.getElementById(id).value = value;
					Ext.getCmp(id + "_combo").setValue(value);
				}
			} else {
				var valueArray_combo = value.split(";");
				var valueArray = value.split(",");
				for (var n = 0; n < valueArray.length; n++) {
					var checkValue = valueArray[n].trim();
					// alert("length:"+length);
					for (i = 0; i < length; i++) {
						var rs = store.getAt(i);
						if (rs.get('key') == checkValue || rs.get('value') == checkValue) {
							var newValue = rs.get('value');
							if (newValue.indexOf('&nbsp;') > 0) {
								newValue = newValue.replace(/\d+/, '').replace(/&nbsp;/g, '');
							}
							valueArray_combo[n] = newValue;
							valueArray[n] = rs.get('key');
							break;
						}
					}
				}
				Ext.getCmp(id + "_combo").setValue(valueArray_combo.join(';'));
				document.getElementById(id).value = valueArray.join(',');
			}
		} else {
			Ext.getCmp(id + "_combo").setValue('');
			document.getElementById(id).value = '';
		}
	},
	setSelectDefault : function(objId) {
		var store = Ext.getCmp(objId + "_combo").store;
		var length = store.getCount();
		if (length == 0) {
			return;
		}
		var rs = store.getAt(0);
		var value = rs.get('key');
		odin.setSelectValue(objId, value);
	},
	doAccForSelect : function(field) {
		var comboId = field.getId();
		var id = comboId.substring(0, comboId.indexOf("_combo"));
		// alert(id);
		var store = Ext.getCmp(comboId).store;
		var length = store.getCount();
		var isExsist = false;
		for (i = 0; i < length; i++) {
			var rs = store.getAt(i);
			if (rs.get('value') == field.getValue()) {
				isExsist = true;
				Ext.get(id).value = rs.get('key');
				var newValue = field.getValue();
				if (newValue.indexOf('&nbsp;') > 0) {
					newValue = newValue.replace(/\d+/, '').replace(/&nbsp;/g, '');
					Ext.getCmp(comboId).setValue(newValue);
				}
				break;
			}
			if (rs.get('key') == field.getValue()) {
				isExsist = true;
				break;
			}
		}
		if (!isExsist) {
			Ext.get(id).value = field.getValue();
		}
		document.getElementById(id).value = Ext.get(id).value;
		// alert(Ext.get(id).value);
	},
	setHiddenTextValue : function(combo, record, index) {
		var comboId = combo.getId();
		var value = record.get('key');
		var id = comboId.substring(0, comboId.indexOf("_combo"));
		Ext.get(id).value = value;
		var newValue = record.get('value');
		if (newValue.indexOf('&nbsp;') > 0) {
			newValue = newValue.replace(/\d+/, '').replace(/&nbsp;/g, '');
		}
		combo.setValue(newValue);
		document.getElementById(id).value = Ext.get(id).value;
		// alert(id+"||"+Ext.get(id).value);
	},
	setHiddenTextValueForMulti : function(combo, record, index) {
		var comboId = combo.getId();
		var value = combo.getCheckedValue('key');
		var id = comboId.substring(0, comboId.indexOf("_combo"));
		Ext.get(id).value = value;
		var newValue = combo.getCheckedValue('value');
		if (newValue.indexOf('&nbsp;') > 0) {
			newValue = newValue.replace(/\d+/, '').replace(/&nbsp;/g, '');
		}
		combo.setValue(newValue);
		document.getElementById(id).value = Ext.get(id).value;
		// alert(id+"||"+Ext.get(id).value);
	},
	doMultiComboSelect : function(combo, record, index) {
		var comboId = combo.getId();
		var value = record.get('key');
		var id = comboId.substring(0, comboId.indexOf("_combo"));
		Ext.get(id).value = value;
		document.getElementById(id).value = Ext.get(id).value;
		doOnChange(combo);
	},
	doTabPanelLayout : function(tab) { // tabpanel的布局调整，修正下拉框图标及grid滚动条bug
		var divList = tab.getEl().dom.getElementsByTagName('div');
		for (var i = 0; i < divList.length; i++) {
			var divId = divList.item(i).id;
			if ((divId.indexOf('div_') == 0) && (document.getElementById('gridDiv_' + divId))) {
				Ext.getCmp(divId).view.refresh(true);
			} else if (divId.indexOf('div_') == 0) {
				var inputList = divList.item(i).getElementsByTagName('input');
				for (var j = 0; j < inputList.length; j++) {
					var inputItem = inputList.item(j);
					var cmp = Ext.getCmp(inputItem.name);
					if (cmp && typeof(cmp.hideTrigger) != "undefined" && !cmp.hideTrigger) {
						var width = cmp.width;
						cmp.setWidth(0);
						cmp.setWidth(width);
						// cmp.trigger.setLeft(width-(Ext.isIE?3:0));
					}
				}
			}
		}
	},
	setListWidth : function(combo) {
		// var store = combo.store;
		// var length = store.getCount();
		// if (length == 0) {
		// return;
		// }
		var width = combo.list.getWidth();
		// var tpl = combo.tpl;
		// if (combo.getXType() == "multicombo") {// 下拉多选
		// tpl = tpl.substring(tpl.indexOf("{", tpl.indexOf("{") + 1),
		// tpl.lastIndexOf("}") + 1);
		// } else {
		// tpl = tpl.substring(tpl.indexOf("{"), tpl.lastIndexOf("}") + 1);
		// }
		// for (i = 0; i < length; i++) {
		// var record = store.getAt(i);
		// var value = '';
		// // value = record.get('key') + " - " + record.get('value') +
		// // record.get('params');
		// value = tpl.replace("{key}", record.get('key')).replace("{value}",
		// record.get('value')).replace("{params}", record.get('params'));
		//
		// var valueWidth = value.replace(/[^\u0000-\u00ff]/g, "aa").length * 6
		// + 26;
		// if (width < valueWidth) {
		// width = valueWidth;
		// }
		// }
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
		if (combo.mode == "remote" && width < 210) {
			width = 210;
		}
		combo.list.setWidth(width);
		combo.innerList.setWidth('auto');
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
	dateFocus : function(item) {
		// 自动弹出选择框有问题，暂时不做20121207
		// var combo = Ext.getCmp(item.id);
		// try {
		// if (eval("comboSetFocusForInit_" + combo.getId())) { // 下拉框不显示标志
		// eval("comboSetFocusForInit_" + combo.getId() + "=false"); // 使用后还原
		// return;
		// }
		// } catch (e) {
		// }
		// try {
		// if (isQuerying) { // 正在查询，则不显示下拉框
		// return;
		// }
		// } catch (e) {
		// }
		// if (odin.hasMsgOrMask()) {// 有遮罩，则不显示下拉框
		// combo.triggerBlur();
		// return;
		// }
		// if (!(combo.menu && combo.menu.isVisible())) {
		// combo.onTriggerClick();
		// combo.menu.doFocus();
		// combo.menu.tryActivate(0, 0);
		// }
	},
	dateBlur : function(item) {
		var combo = Ext.getCmp(item.id);
		if (combo.menu && combo.menu.isVisible()) {
			combo.menu.hide();
		}
	},
	doMultiSelectWithAll : function(combo, record, index) {
		if (record.get('key') == 'all') {
			var checked = record.get('checked');
			combo.deselectAll();
			record.set('checked', checked);
		} else {
			if (combo.store.getAt(0).get('key') == 'all' && combo.store.getAt(0).get('checked') == true) {
				combo.store.getAt(0).set('checked', false);
			}
		}
	},
	fillLeft : function(oldstr, ch, len) {
		var newstr = oldstr;
		for (var i = oldstr.length; i < len; i++) {
			newstr = ch + newstr;
		}
		return newstr;
	},
	fillRight : function(oldstr, ch, len) {
		var newstr = oldstr;
		for (var i = oldstr.length; i < len; i++) {
			newstr = newstr + ch;
		}
		return newstr;
	},
	Ajax : {
		/**
		 * ajax
		 * 
		 * @param {Object}
		 *            reqUrl
		 * @param {Object}
		 *            reqParams
		 * @param {Object}
		 *            successFun
		 * @param {Object}
		 *            failureFun
		 * @param {Object}
		 *            asynchronous 同步异步标志，true为异步，false为同步
		 * @param {Object}
		 *            isMask 是否要做mask遮罩处理
		 */
		request : function(reqUrl, reqParams, successFun, failureFun, asynchronous, isMask, maskMsg) {
			Ext.Ajax.timeout = odin.ajaxTimeout;
			if (isMask != null && isMask == false) {
				// 参数存在且为false
				// alert('参数存在且为false');
			} else {
				if (maskMsg == null) {
					maskMsg = odin.msg;
				}
				odin.mask(maskMsg);
			}
			if (asynchronous == null) {
				asynchronous = odin.ajaxaSynchronous;
			}
			if (asynchronous != false) {
				asynchronous = true;
			}
			return Ext.Ajax.request({
						url : reqUrl,
						success : doSuccess,
						failure : doFailure,
						params : reqParams,
						asynchronous : asynchronous
					});
			function doSuccess(request) {
				odin.unmask();
				var response = null;
				try {
					response = eval('(' + request.responseText + ')');
				} catch (err) {
					odin.error("连接超时，请重新登陆！");
					throw err;
				}
				if (response.messageCode == "0") {// 请求成功
					if (successFun) {// 有成功回调函数
						successFun(response);
					} else {
						odin.alert(response.mainMessage);
					}
				} else {// 请求失败
					if (failureFun) {// 有失败回调函数
						failureFun(response);
					} else {
						var errmsg = response.mainMessage;
						if (response.detailMessage != "") {
							errmsg = errmsg + "\n详细信息:" + response.detailMessage;
						}
						// alert(errmsg);
						odin.error(errmsg);
					}
				}
			}

			function doFailure(request) {
				Ext.get(document.body).unmask();
				// alert("Ajax请求失败，请与系统管理员联系！");
				//odin.error("Ajax请求失败，请重新登陆！");
				odin.error("会话超时，请重新登陆！");
			}
		},
		formatDate : function(jsonDate) {
			var year = String(jsonDate.year + 1900);
			var month = String(jsonDate.month + 1);
			var date = String(jsonDate.date);
			month = odin.fillLeft(month, "0", 2);
			date = odin.fillLeft(date, "0", 2);
			var theDate = year + "-" + month + "-" + date;
			return theDate;
		},
		formatDateTime : function(jsonDate) {
			var year = String(jsonDate.year + 1900);
			var month = String(jsonDate.month + 1);
			var date = String(jsonDate.date);
			var hour = String(jsonDate.hours);
			var minute = String(jsonDate.minutes);
			var second = String(jsonDate.seconds);
			month = odin.fillLeft(month, "0", 2);
			date = odin.fillLeft(date, "0", 2);
			hour = odin.fillLeft(hour, "0", 2);
			minute = odin.fillLeft(minute, "0", 2);
			second = odin.fillLeft(second, "0", 2);
			var theDateTime = year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
			return theDateTime;
		}
	},
	enterToTab : function() {
		// alert("ok");
		var e = window.event.srcElement;
		var type = e.type;
		if (type != 'button' && type != 'textarea' && event.keyCode == 13) {
			event.keyCode = 9;
		}
	},
	doOpLog : function(theForm) {
		// 操作日志处理
		var prsource = eval(MDParam.prsource);
		if (prsource) {
			/*
			 * var digest=new Array(10); for(var i=0;i<10;i++){ digest[i]=""; }
			 * //获取操作日志摘要信息 var len=Math.min(prsource.length,10); for(var i=0;i<len;i++){
			 * var prop=prsource[i].property; if(prop=="") continue;
			 * 
			 * var el=document.getElementById(prop); if(el){ digest[i]=el.value; } }
			 */
			var len = prsource.length;
			var items = new Array(len);
			for (var i = 0; i < len; i++) {
				items[i] = "";
			}
			for (var i = 0; i < len; i++) {
				var prop = prsource[i].property;
				if (prop == "")
					continue;

				var type = prsource[i].type;
				if (type && type == "select") {
					prop = prop + "_combo";
				}
				var el = document.getElementById(prop);
				if (el) {
					if (type && type.toLowerCase() == "text") {
						items[i] = el.innerHTML;
					} else {
						items[i] = el.value;
					}
				}
			}
			var digest = "";
			for (var i = 2; i < len; i++) {
				if (items[i] == "")
					continue;

				digest = digest + "," + prsource[i].getAttribute("label") + ":" + items[i];
			}
			if (digest != "") {
				digest = digest.substring(1);
			}
			// 获取原始界面信息
			// var
			// oriSource="<html>"+document.documentElement.innerHTML+"</html>";
			var oriSource = document.documentElement.outerHTML;
			// alert(oriSource);
			// 去掉javascript内容
			if (!theForm.paymentWageDeclare) {
				oriSource = oriSource.replace(/<script([^<]*?([^<]*(<[^\/]*)*))<\/script>/gi, "");
			}
			/*
			 * var userlog={functionid:MDParam.functionid, aac001:digest[0],
			 * aab001:digest[1], prcol1:digest[2], prcol2:digest[3],
			 * prcol3:digest[4], prcol4:digest[5], prcol5:digest[6],
			 * prcol6:digest[7], prcol7:digest[8], prcol8:digest[9],
			 * orisource:oriSource };
			 */
			var userlog = {
				functionid : MDParam.functionid,
				aac001 : items[0],
				aab001 : items[1],
				digest : digest,
				prcol1 : "",
				prcol2 : "",
				prcol3 : "",
				prcol4 : "",
				prcol5 : "",
				prcol6 : "",
				prcol7 : "",
				prcol8 : "",
				orisource : oriSource
			};
			// var srtUserlog=JSON.stringify(userlog);
			var srtUserlog = Ext.util.JSON.encode(userlog);
			// alert(srtUserlog);
			return srtUserlog;
		} else {
			return null;
		}
	},
	/*
	 * submit:function(theForm){//表单方式提交保存 //操作日志处理 var
	 * srtUserlog=this.doOpLog(theForm); //alert(srtUserlog); if(srtUserlog){
	 * //向表单中插入操作日志隐藏字段 var userlogNode=document.getElementById("userlog");
	 * if(!userlogNode){ userlogNode=document.createElement("input");
	 * userlogNode.setAttribute("name","userlog");
	 * userlogNode.setAttribute("type","hidden");
	 * theForm.appendChild(userlogNode); }
	 * userlogNode.setAttribute("value",String(srtUserlog)); }
	 * 
	 * theForm.submit(); },
	 */
	submit : function(theForm, successFun, failureFun) {// Ajax方式提交保存
		var params = {};
		// 将表单域中的数据转换成参数对象
		var elList = theForm.getElementsByTagName("input");
		for (var i = 0; i < elList.length; i++) {
			if (elList.item(i).name && elList.item(i).name.indexOf("-") == -1) {
				var exp = "params." + elList.item(i).name + "=" + Ext.util.JSON.encode(elList.item(i).value);
				// alert(exp);
				eval(exp);
			}
		}
		// 操作日志处理
		var srtUserlog = this.doOpLog(theForm);
		if (srtUserlog) {
			if (getsysType() && getsysType() == 'sionline') {
				params.declarelog = srtUserlog;
			} else {
				params.userlog = String(srtUserlog);
			}
		}
		if (odin.checkValue(theForm) == true) { // 做通一校验判断
			this.Ajax.request(theForm.action, params, successFun, failureFun);
		}
	},
	formClear : function(theForm) {
		var elList = theForm.getElementsByTagName("input");
		for (var i = 0; i < elList.length; i++) {
			elList.item(i).setAttribute("value", "");
		}
	},
	reset : function() {
		// location.reload();
		location.href = contextPath + MDParam.location;
	},
	loadPageGridWithQueryParams : function(gridId, params) {
		var store = Ext.getCmp(gridId).getStore();
		if (Ext.getCmp(gridId).getTopToolbar() && Ext.getCmp(gridId).getTopToolbar().pageSize) {
			params.limit = Ext.getCmp(gridId).getTopToolbar().pageSize;
		} else if (Ext.getCmp(gridId).getBottomToolbar() && Ext.getCmp(gridId).getBottomToolbar().pageSize) {
			params.limit = Ext.getCmp(gridId).getBottomToolbar().pageSize;
		} else {
			params.limit = sysDefaultPageSize;
		}
		params.div = gridId;
		store.on('beforeload', function(ds) {
					ds.baseParams = params;
				});
		store.load();
	},
	loadGridData : function(gridId, params, beforeloadFun) {
		params.div = gridId;
		var store = Ext.getCmp(gridId).getStore();
		store.on('beforeload', function(ds) {
					if (beforeloadFun) {
						beforeloadFun(ds)
					};
					ds.baseParams = params;
				});
		store.load();
	},
	showWindowWithSrc : function(windowId, newSrc) {
		if (document.getElementById("iframe_" + windowId) != null) {
			document.getElementById("iframe_" + windowId).src = newSrc;
			window.setTimeout('Ext.getCmp(\"' + windowId + '\").show(Ext.getCmp(\"' + windowId + '\"))', 200);
		} else {
			Ext.getCmp(windowId).show(Ext.getCmp(windowId));
		}
	},
	changeToUrl : function(value, params, record, rowIndex, colIndex, ds) {
		return "<a href='www.baidu.com?test=" + record.data.price + "&test2=" + record.data.change + "'>" + value + "</a>";
	},
	gridCellClick : function(grid, rowIndex, colIndex, event) {
		// alert(colIndex);
		if (colIndex % 2 == 0) {
			grid.getColumnModel().setEditable(colIndex, false);
		} else if (colIndex != 3) {
			grid.getColumnModel().setEditable(colIndex, true);
		}
	},
	doAfterEditForEditGridOther : function(e) { // 不到后台校验的afteredit
		if (odin.doAfterEditForEditGrid(e) == false) {
			return;
		}
		// 设置焦点
		var keyCode = (event != null ? (event.keyCode || event.which || event.charCode) : null);
		if (keyCode != 13 && keyCode != 9) {
			var grid = e.grid;
			var editor = grid.getColumnModel().getCellEditor(e.column, e.row);
			var newCell = grid.walkCells(e.row, e.column + 1, 1, grid.getSelectionModel().acceptsNav, editor);
			if (newCell != null) {
				grid.startEditing(newCell[0], newCell[1]);
			}
		}
	},
	doAfterEditForEditGrid : function(e) {
		var msg = odin.afterEditForEditGrid(e);
		if (msg != "") {
			odin.error(msg + "，请重新输入！", function() {
						e.record.set(e.field, e.originalValue);
						e.grid.startEditing(e.row, e.column);
					})
			return false;
		}
		return true;
	},
	afterEditForEditGrid : function(e) {
		var grid = e.grid;
		var record = e.record;
		var field = e.field;
		var originalValue = e.originalValue;
		var value = e.value;
		var row = e.row;
		var column = e.column;
		var sel_data;
		var rowstatus = record.get("rowstatus");
		if (rowstatus != "I") { // 行修改标志 I插入U更新
			record.set("rowstatus", "U");
		}
		// 下拉框校验
		if (odin.cueSelectArrayData.length > 0) {
			eval("window." + field + "_select = odin.cueSelectArrayData; ");
		}
		if (eval("window." + field + "_select")) {
			sel_data = eval(field + "_select");
			var sel_check = false;
			for (i = 0; i < sel_data.length; i++) {
				if (sel_data[i][1] == value || sel_data[i][0] == value) {
					grid.store.getAt(row).set(field, sel_data[i][0]);
					sel_check = true;
					break;
				}
			}
			if (sel_check == false) {
				return "您输入的【" + value + "】不在列表中！";
			}
		}
		if (document.getElementById("selectall_" + grid.id + "_" + field)) {
			document.getElementById("selectall_" + grid.id + "_" + field).checked = false;
		}
		// 自定义有效性validator校验
		var editorField = grid.getColumnModel().getCellEditor(column, row).field;
		if (editorField.myValidator) {
			var msg = editorField.myValidator(value);
			if (msg !== true) {
				return msg;
			}
		}
		return "";
		// alert(field);
		// alert(row+"|"+column);
		// var editor = grid.getColumnModel().getCellEditor(column,row);
		// alert(grid.store.getAt(row).get(field));
		// alert(typeof(editor));
		// if(editor instanceof Ext.grid.GridEditor){
		// alert(1);
		// }
		// alert(editor.getValue());
	},
	doGridSelectAcc : function(value, params, record, rowIndex, colIndex, ds) {
		var selectColumnName = "pctChange";
		var sel_data = eval(selectColumnName + "_select");
		for (i = 0; i < sel_data.length; i++) {
			if (sel_data[i][0] == value) {
				value = sel_data[i][1];
				break;
			}
		}
		return value;
	},
	doAccSpecialkey : function(e) { // grid按键处理
		odin.lastEvent = odin.eventClone(e);
		if (!e.isSpecialKey()) {
			return;
		}
		// alert('特殊按键');
		try {
			var obj = event.target || event.srcElement;// 获取事件源
			var type = obj.type || obj.getAttribute('type');// 获取事件源类型
		} catch (e) {
		}
		if (e.getKey() == e.ENTER) {
			if (type == 'textarea') {
				e.keyCode = 0;
			}
		}
		if (e.isSpecialKey()) {
			this.fireEvent("specialkey", this, e);
		}
	},
	doAccOnEditorKey : function(field, e) { // grid特殊按键的处理
		var k = e.getKey(), newCell, g = this.grid, ed = g.activeEditor, shiftKey = e.shiftKey, ctrlKey = e.ctrlKey;
		if (k == e.ENTER || k == e.TAB) {
			if (field.getXType() == "combo") { // 下拉框特殊处理
				field.onViewClick(false);
				field.triggerBlur();
			}
			if (k != e.TAB) { // tab键时如果处理则焦点会有问题
				e.stopEvent();
			}
			ed.completeEdit();
			if (shiftKey) {
				newCell = g.walkCells(ed.row, ed.col - 1, -1, this.acceptsNav, this);
			} else {
				newCell = g.walkCells(ed.row, ed.col + 1, 1, this.acceptsNav, this);
			}
		} else if (ctrlKey && (k == e.UP || k == e.DOWN)) {
			e.stopEvent();
			ed.completeEdit();
			if (k == e.UP) {
				newCell = g.walkCells(ed.row - 1, ed.col, -1, this.acceptsNav, this);
			} else {
				newCell = g.walkCells(ed.row + 1, ed.col, 1, this.acceptsNav, this);
			}
		} else if (k == e.ESC) {
			ed.cancelEdit();
		}
		if (newCell) {
			g.startEditing(newCell[0], newCell[1]);
		}
	},
	renderDate : function(dateVal) {
		if (!dateVal || dateVal == "") {
			// dateVal = new Date();
		}
		return Ext.util.Format.date(dateVal, 'Y-m-d');
	},
	billPrint : function(repid, queryName, param, preview) {
		var strpreview = "true";
		if (!preview) {
			strpreview = "false";
		}
		var url = contextPath + "/common/billPrintAction.do?repid=" + repid + "&queryname=" + queryName + "&param=" + param + "&preview=" + strpreview;
		window.open(url);
	},
	getGridJsonData : function(gridId, inputName) {
		if (!inputName) {
			inputName = gridId + "Data";
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.store;
		var dataArray = new Array(store.data.length);
		for (i = 0; i < store.data.length; i++) {
			dataArray[i] = store.getAt(i).data; // store.data.length-(i+1)
		}
		var gridJsonStr = Ext.util.JSON.encode(dataArray);
		document.getElementById(inputName).value = gridJsonStr;
		// alert(document.getElementById(inputName).value);
		return gridJsonStr;
	},
	setGridJsonData : function(gridId, inputName) {
		if (!inputName) {
			inputName = gridId + "Data";
		}
		if (document.getElementById(inputName).value != null && document.getElementById(inputName).value != "") {
			var jsonStr = Ext.util.JSON.decode(document.getElementById(inputName).value);
			Ext.getCmp(gridId).store.removeAll();
			if (jsonStr != null && jsonStr != "") {
				var grid = Ext.getCmp(gridId);
				var store = grid.store;
				var rsData = new Array(jsonStr.length);
				for (i = 0; i < jsonStr.length; i++) {
					// alert(Ext.util.JSON.encode(jsonStr[i]));
					rsData[i] = new Ext.data.Record(jsonStr[i]);
				}
				store.add(rsData);
			}
		}
	},
	addGridRowData : function(gridId, dataObj, rowCount) {
		var grid = Ext.getCmp(gridId);
		var store = grid.store;
		if (!rowCount) {
			rowCount = 1;
		}
		var rsData = new Array(rowCount);
		for (i = 0; i < rsData.length; i++) {
			if (!dataObj) {
				rsData[i] = store.getAt(store.data.length - 1).copy(store.data.length);
			} else {
				rsData[i] = new store.reader.recordType(dataObj);
			}
		}
		store.insert(store.getCount(), rsData);
	},
	openOpLogList : function() {
		var tabs = top.tabs;
		var aid = "oplog";
		var atitle = "操作日志中心";
		var src = contextPath + "/sys/MDOpLogListAction.do?functionid=" + MDParam.functionid;
		var tab = tabs.getItem(aid);
		if (tab) {
			tabs.remove(tab);
		}
		top.addTab(atitle, aid, src);
	},
	accCheckboxCol : function(value, params, record, rowIndex, colIndex, ds) {
		var rtn = "";
		rtn += '<div class=\"x-grid-editor\">';
		rtn += '<div class=\"x-form-check-wrap\">';
		if (value == true) {
			rtn += "<div ><input type='checkbox' alowCheck='true' name='col" + rowIndex + colIndex + "' onclick='odin.accChecked(this," + rowIndex + "," + colIndex + ",\"change\")' checked />";
		} else {
			rtn += "<input type='checkbox' alowCheck='true' name='col" + rowIndex + colIndex + "' onclick='odin.accChecked(this," + rowIndex + "," + colIndex + ",\"change\")' />";
		}
		rtn += '</div></div>';
		odin.checkboxds = ds;
		return rtn;
	},
	accChecked : function(obj, rowIndex, colIndex, colName) {
		if (obj.alowCheck == "false") {
			obj.checked = !obj.checked;
			return;
		}
		if (obj.checked) {
			odin.checkboxds.getAt(rowIndex).set(colName, true);
		} else {
			odin.checkboxds.getAt(rowIndex).set(colName, false);
		}
	},
	doFilterGridCueData : function(grid, filterFunc) {
		grid.store.filterBy(filterFunc);
	},
	doClearFilter : function(grid) {
		grid.store.clearFilter(false);
	},
	showErrorMessage : function(response) {
		var errmsg = response.mainMessage;
		if (response.detailMessage != "") {
			errmsg = errmsg + "\n详细信息:" + response.detailMessage;
		}
		odin.alert(errmsg);
	},
	encode : Ext.encode,
	beforeedit : function(e) {
		var grid = e.grid;
		var record = e.record;
		var field = e.field;
		var originalValue = e.originalValue;
		var value = e.value;
		var row = e.row;
		var column = e.column;
		var cancel = e.cancel;
		// 通过下面这句可以禁止当前单元格的编辑
		// e.cancel = true;
	},
	/** *************给select重新设置数据************************* */
	reSetSelectData : function(selectId, jsonData) {
		var store = null;
		try {
			store = odin.ext.getCmp(selectId + "_combo").store;
			document.getElementById(selectId).value = "";
		} catch (e) {
			store = odin.ext.getCmp(selectId).store;
		}
		var count = store.getCount();
		// store.removeAll(); 使用它会有问题，当第一次对同一个对象使用此方法没问题，但以后就会报js出错
		// 所以这里通过一条一条来remove数据
		for (i = 0; i < count; i++) {
			store.remove(store.getAt(0));
		}
		var data = new Array(jsonData.length);
		for (i = 0; i < jsonData.length; i++) {
			data[i] = new odin.ext.data.Record(jsonData[i]);
		}
		store.add(data);
	},
	/** *********统一的提交之前的全局校验函数，用来判断非空的是否有空的存在，不空的是否符合业务校验规则****************** */
	checkValue : function(userTestForm) {
		var eles = userTestForm.elements;
		for (i = 0; i < eles.length; i++) {
			var obj = eles[i];
			odin.cueCheckObj = obj;
			if (obj.getAttribute("required") == "true") {
				if (obj.value == "") { // 非空判断
					odin.error(obj.getAttribute("label") + "不能为空！", odin.doFocus);
					return false;
				}
			}
			if (obj.value != null && obj.value != "") {
				var eObj = odin.ext.getCmp(obj.name);
				if (eObj) {
					if (!eObj.isValid(false)) {
						odin.error(eObj.invalidText, odin.doFocus);
						return false;
					}
				}
				var eCObj = odin.ext.getCmp(obj.name + "_combo");
				if (eCObj) {
					if (!eCObj.isValid(false)) {
						odin.error(eCObj.invalidText, odin.doFocus);
						return false;
					}
				}
			}
		}
		return true;
	},
	cueCheckObj : null,
	isSelectText : true,
	setInvalidMsg : function(id, nowMsg) {
		var obj = odin.ext.getCmp(id);
		if (obj) {
			obj.invalidText = nowMsg;
		} else {
			obj = odin.ext.getCmp(id + "_combo");
			obj.invalidText = nowMsg;
		}
	},
	doFocus : function() {
		if (odin.cueCheckObj != null && odin.cueCheckObj) {
			try {
				if (odin.isSelectText == true) {
					odin.ext.getCmp(odin.cueCheckObj.name).focus(true);
				} else {
					odin.cueCheckObj.focus();
				}
				odin.cueCheckObj = null;
			} catch (e) {
				var comboName = odin.cueCheckObj.name + "_combo";
				if (odin.ext.getCmp(comboName)) {
					if (odin.isSelectText == true) {
						odin.ext.getCmp(comboName).focus(true);
					} else {
						document.getElementById(comboName).focus();
					}
				}
			}
		}
	}
	/** ****************************统一校验结束*************************************** */
	,
	/** *******select标签的两个ftl模版************ */
	showSelectCodeFtl : new Ext.XTemplate(
			/** 显示代码的同时显示值的ftl文件，主要给下拉框用* */
			'<tpl for="."><div class="x-combo-list-item">', '{key}&nbsp;&nbsp;{value}', '</div>', '</tpl>'),
	showValueAndFilterCodeFtl : new Ext.XTemplate(
			/** 只显示值的同时要根据输入的代码来智能选择值的ftl文件，主要给下拉框用* */
			'<tpl for="."><div class="x-combo-list-item">', '{value}', '</div>', '</tpl>')
	/** *********end ftl*************** */
	,
	getSelectDataToArray : function(selectId) {
		/** *提取select组件的数据，将其转化成需要的数组型数据***** */
		var store = odin.ext.getCmp(selectId).store;
		var length = store.getCount();
		var arrayData = new Array(length);
		for (i = 0; i < length; i++) {
			var temp = new Array(2);
			temp[0] = store.getAt(i).get('key');
			temp[1] = store.getAt(i).get('value');
			arrayData[i] = temp;
		}
		odin.cueSelectArrayData = arrayData;
	},
	cueSelectArrayData : new Array(0),
	accEditGridSelectColSelEve : function(record, index) {
		/** *对编辑表格的下拉编辑时选择事件的默认处理** */
		if (this.fireEvent('beforeselect', this, record, index) !== false) {
			this.setValue(record.data[this.valueField || this.displayField]);
			this.collapse();
			this.fireEvent('select', this, record, index);
		}
		odin.getSelectDataToArray(this.getId());
	},
	/**
	 * 普通通用查询
	 * 
	 * @param {Object}
	 *            params(为json对象，有两项，分别为querySQL即查询的sql或hql，还有就是sqlType，其表示查询的方式，可以为"SQL"或"HQL")
	 * @param {Object}
	 *            succFunc 查询成功后要执行的函数
	 * @param {Object}
	 *            failFunc 查询失败后要执行的函数
	 * @param {Object}
	 *            sync 布尔类型，true则为同步，默认为false，即异步操作
	 * @param {Object}
	 *            isMask 是否遮罩
	 */
	commonQuery : function(params, succFunc, failFunc, sync, isMask) {
		var url = contextPath + "/common/commformCommQueryAction.do?method=query";
		var req = odin.Ajax.request(url, params, succFunc, failFunc, sync, isMask);
		return req;
	},
	/**
	 * 为select store对象根据一定的查询条件和过滤条件重新加载数据
	 * 
	 * @param {Object}
	 *            objId 要加载数据的组件id
	 * @param {Object}
	 *            aaa100 代码类别
	 * @param {Object}
	 *            aaa105 参数分类
	 * @param {Object}
	 *            filter 过滤条件
	 * @param {Object}
	 *            isRemoveAllBeforeAdd 加载之前是否要清除以前的数据，默认清除（暂时没用，即都清除以前数据）
	 */
	loadDataForSelectStore : function(objId, aaa100, aaa105, filter, isRemoveAllBeforeAdd) {
		var params = {};
		params.querySQL = "@_76tJBvkT+4WYqiCJsa9cNje9JWlWLwAz2jzDqbK9jZdHExuQ01H6lw==_@" + aaa100 + "@_fqOngwdfJf8=_@";
		var aab301 = getCurrentAab301();
		params.querySQL = params.querySQL + "@_40gUbunXPFeuxOFXAIK8RtAp7o4erSrlJx35WEDjKgGowt19i9kO0Q==_@" + aab301 + "@_xk5zjeGiN8k=_@";
		if (aaa105 != null && aaa105 != "") {
			params.querySQL += "@_AU9fU5i/Yvo3Yob5/bL4Xw==_@" + aaa105 + "@_fqOngwdfJf8=_@";
		}
		if (filter != null && filter != "") {
			params.querySQL += "@_N+8qEp3HvPY=_@" + filter + "@_7y/+a0omhqA=_@";
		}
		params.querySQL += "@_0lLHxReFyQHqfVJjJcsCI4wE91IhYScZ_@";
		params.sqlType = "SQL";
		var req = odin.commonQuery(params, odin.blankFunc, odin.blankFunc, false, false);
		var data = odin.ext.decode(req.responseText).data.data;
		var selectData = null;
		if (data != null && data.length > 0) {
			selectData = new Array(data.length);
			for (i = 0; i < data.length; i++) {
				selectData[i] = {};
				selectData[i].key = data[i].aaa102;
				selectData[i].value = data[i].aaa103;
			}
		}
		if (selectData != null) {
			if (isRemoveAllBeforeAdd == false) {
				//
			} else {
				odin.reSetSelectData(objId, selectData);
			}
		}
	},
	ajaxSuccessFunc : function(responseTxt) { // 空的ajax调用成功的处理函数
		//
	},
	blankFunc : function() { // 空的处理函数

	},
	/** **对时间控件的一个readonly处理**** */
	setDateReadOnly : function(id, isReadOnly) {
		if (isReadOnly == false) {
			odin.ext.getCmp(id).menuListeners.select = odin.dateCanSetValue;
		} else {
			odin.ext.getCmp(id).menuListeners.select = odin.dateCanNotSetValue;
		}
	},
	dateCanSetValue : function(m, d) {
		this.setValue(d);
		if (document.getElementById(this.getId()).onchange) {
			document.getElementById(this.getId()).onchange();
		}
	},
	dateCanNotSetValue : function(m, d) {
		//
	},
	/** *******end 对时间控件的一个readonly处理******* */
	/** **对下拉控件的一个readonly处理**** */
	setComboReadOnly : function(id, isReadOnly) {
		if (isReadOnly == false) {
			odin.ext.getCmp(id + '_combo').onSelect = odin.comboCanSetValue;
		} else {
			// alert(odin.ext.getCmp(id+'_combo').onSelect);
			odin.ext.getCmp(id + '_combo').onSelect = odin.comboCanNotSetValue;
		}
	},
	comboCanSetValue : function(record, index) {
		if (this.fireEvent('beforeselect', this, record, index) !== false) {
			this.setValue(record.data[this.valueField || this.displayField]);
			this.collapse();
			this.fireEvent('select', this, record, index);
		}
	},
	comboCanNotSetValue : function(record, index) {
		if (this.fireEvent('beforeselect', this, record, index) !== false) {
		}
	},
	/** ***end 对下拉控件的一个readonly处理**** */

	/**
	 * loadPageInTab 在Tabl中显示指定url的页面 参数: aid:
	 * 模块编码或者自定义字符串，如果需要保证只打开一个模块，请传入模块编码 url: 页面地址，不包含context， 例如 /page/.....
	 * forced: 是否强制打开一个新tab text: 标题，如果为空则取对应模块的名称 注意，请在业务页面中的JS调用此喊出
	 */
	loadPageInTab : function(aid, url, forced, text, autoRefresh) {
		var treenode = parent.tree.getNodeById(aid);
		var title;
		if (treenode) {
			if (text)
				parent.addTab(text, treenode.id, parent.g_contextpath + url, forced, autoRefresh);
			else
				parent.addTab(treenode.text, treenode.id, parent.g_contextpath + url, forced, autoRefresh);
		} else {
			if (text)
				parent.addTab(text, aid, parent.g_contextpath + url, forced, autoRefresh);
			else
				parent.addTab("", aid.id, parent.g_contextpath + url, forced, autoRefresh);
		}
	},
	doChangeDate : function(obj) { // 对时间的一个处理，即当输入“Ymd”这种格式的时间时自动将其转化成“Y-m-d”这种日期格式的数据
		var date;
		if (obj.value.indexOf("-") == -1) {
			date = Date.parseDate(obj.value, 'Ymd');
		} else {
			date = Date.parseDate(obj.value, 'Y-m-d');
		}
		obj.value = date.format('Y-m-d');
	},
	renderDate : function(dateVal) { // 这个方法主要是为了解决编辑表格里的时间编辑列无法赋初值的问题，可根据需要修改下“Y-m”字符
		if (!dateVal || dateVal == "") {
			dateVal = new Date();
		} else {
			if (typeof dateVal == 'string') {
				dateVal = Date.parseDate(dateVal, 'Y-m-d');
			}
		}
		return Ext.util.Format.date(dateVal, 'Y-m-d');
	},
	/** *********浮动div所使用的js********* */
	ClientWidth : function() {
		var theWidth = 0;
		if (window.innerWidth) {
			theWidth = window.innerWidth
		} else if (document.documentElement && document.documentElement.clientWidth) {
			theWidth = document.documentElement.clientWidth
		} else if (document.body) {
			theWidth = document.body.clientWidth
		}
		return theWidth;
	},
	ClientHeight : function() {
		var theHeight = 0;
		if (window.innerHeight) {
			theHeight = window.innerHeight
		} else if (document.documentElement && document.documentElement.clientHeight) {
			theHeight = document.documentElement.clientHeight
		} else if (document.body) {
			theHeight = document.body.clientHeight
		}
		return theHeight;
	},
	ScrollTop : function() {
		var theSTop = document.documentElement.scrollTop + document.body.scrollTop;
		return theSTop;
	},
	close_event : function() {
		control = true;
		document.getElementById('floatZc').style.display = "none";
	},
	/** *********end 浮动div所使用的js********* */
	onMouseDown : function(e) { // 鼠标点击
		var e = e || window.event;
		odin.lastEvent = odin.eventClone(e);
	},
	onKeyDown : function(e) { // 键盘处理
		var event = e || window.event;// 获取event对象
		odin.lastEvent = odin.eventClone(event);
		var obj = event.target || event.srcElement;// 获取事件源
		var type = obj.type || obj.getAttribute('type');// 获取事件源类型
		var keyCode = event.keyCode || event.which || event.charCode;
		var altKey = event.altKey;
		var ctrlKey = event.ctrlKey;
		if ((keyCode == 8) && ((type != "text" && type != "textarea" && type != "password") || (obj.readOnly))) {
			if (Ext.isIE) {
				event.keyCode = 0;
				event.returnValue = false;
			} else {
				event.preventDefault();
				event.stopPropagation();
			}
			return;
		}
		if (type == 'button' && keyCode == 13 && !Ext.MessageBox.isVisible()) { // 非对话框的按钮按回车无效
			if (Ext.isIE) {
				event.keyCode = 0;
				event.returnValue = false;
			} else {
				event.preventDefault();
				event.stopPropagation();
			}
			return;
		}
		if (type != 'button' && type != 'textarea' && keyCode == 13) { // 回车处理
			if (Ext.isIE) {
				event.keyCode = 9;
			} else {
				var el = odin.getNextElement(obj);
				if (el) {
					obj.blur();
					try {
						if (el.name.indexOf("_combo") != -1) { // 下拉框特殊处理，解决一些bug
							var cmp = Ext.getCmp(el.name);
							if (cmp) {
								odin.comboFocus(cmp);
							}
						}
					} catch (e) {
					}
					el.focus();
				} else {
					obj.blur();
				}
				event.preventDefault();
				event.stopPropagation();
			}
			return;
		}
		if (keyCode == 27) { // 禁止esc按钮，否则会把form值清除
			if (Ext.isIE) {
				event.keyCode = 0;
				event.returnValue = false;
			} else {
				event.preventDefault();
				event.stopPropagation();
			}
			return;
		}
		// 帮助文档，F2按钮的操作
		if (keyCode == 113) {
			if (MDParam == null) {
				return;
			}
			var src = contextPath;
			src += "/pages/commAction.do?method=GotoHelp&functionid=" + MDParam.functionid;
			if (altKey) { // 按着alt则进行重新输入页面id
				src += "&reset=true";
			}
			doOpenPupWinOnTop(src, "帮助文档--" + MDParam.title, 0.85, 0.85, initParams);
			if (Ext.isIE) {
				event.keyCode = 0;
				event.returnValue = false;
			} else {
				event.preventDefault();
				event.stopPropagation();
			}
			return;
		}
		// 业务回退，F9按钮的操作
		if (keyCode == 120) {
			if (MDParam == null) {
				return;
			}
			var src = contextPath;
			src += "/pages/commAction.do?method=OriRollback";
			// alert(src);
			var initParams = MDParam.functionid;
			if (MDParam.zyxx != "" && MDParam.zyxx != "{}") {// 增加一个管理员
				doOpenPupWinOnTop(src, "业务日志--" + MDParam.title, 860, 450, initParams);
			} else {
				odin.info("【<b>" + MDParam.title + "</b>】目前配置为非业务经办模块，无业务日志！");
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
		// 测试场景，F12按钮的操作
		if (keyCode == 123) {
			if (MDParam == null) {
				return;
			}
			var src = contextPath;
			src += "/pages/commAction.do?method=sys.autoverify.autoordeal.CommScen&functionid=" + MDParam.functionid;
			var initParams = MDParam.functionid;
			// alert(Ext.util.JSON.encode(MDParam));
			var uptype = MDParam.uptype;
			if (MDParam.zyxx != "" && MDParam.zyxx != "{}") {// 增加一个管理员
				doOpenPupWinOnTop(src, "功能测试--" + MDParam.title, 0.98, 0.98, initParams + ',' + uptype);
			} else {
				odin.info("【<b>" + MDParam.title + "</b>】目前配置为非业务经办模块，不能设置测试用例！");
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
	},
	/** 取得下一个元素 */
	getNextElement : function(field) {
		var obj;
		var form = field.form;
		if (!form) {
			return;
		}
		for (var e = 0; e < form.elements.length; e++) {
			if (field == form.elements[e]) {
				break;
			}
		}
		for (e = e + 1; e < form.elements.length; e++) {
			var tempObj = form.elements[e];
			if (tempObj.type != 'hidden' && !tempObj.disabled) {
				obj = tempObj;
				break;
			}
		}
		return obj;
	},
	/** *********统一失去焦点的方法，如果值为空则不允许焦点移开************* */
	commonOnBlur : function(obj) {
		if (obj.value == "") {
			obj.focus();
		}
	},
	/*
	 * round(Dight,How):数值格式化函数，Dight要 格式化的 数字，How要保留的小数位数。
	 */
	round : function(Dight, How) {
		Dight = Math.round(Dight * Math.pow(10, How)) / Math.pow(10, How);
		return Dight;
	},
	/**
	 * 处理全选的问题
	 * 
	 * @param {Object}
	 *            gridId
	 * @param {Object}
	 *            obj
	 * @param {Object}
	 *            fieldName
	 */
	selectAllFunc : function(gridId, obj, fieldName) {
		var store = odin.ext.getCmp(gridId).store;
		var value = false;
		if (obj.checked) {
			value = true;
		}
		var length = store.getCount();
		if (length == 0) {
			obj.checked = false;
			return;
		}
		var canEditArray = new Array(length);
		for (index = 0; index < length; index++) {
			var id = "checkbox_" + gridId + "_" + fieldName + "_" + index;
			var canEdit = document.getElementById(id).getAttribute("alowCheck");
			if (canEdit) {
				canEditArray[index] = true;
				store.getAt(index).set(fieldName, value);
			} else {
				canEditArray[index] = false;
			}
		}
		for (index = 0; index < length; index++) {
			if (canEditArray[index]) {
				triggerAfterEdit(gridId, fieldName, index);
			}
		}
		obj.checked = value;
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
	/**
	 * 对于统计时的默认render函数，其所做的就是将统计结果保留小数点后两位
	 * 
	 * @param {Object}
	 *            v
	 * @param {Object}
	 *            params
	 * @param {Object}
	 *            data
	 */
	defaultSumRender : function(v, params, data) {
		return odin.round(v, 2);
	},
	eventClone : function(e) {
		var ev = {};
		ev.keyCode = e.keyCode;
		ev.button = e.button;
		if (!Ext.isIE && ev.button == 0) {// chrome改成跟ie一样
			ev.button = 1;
		}
		ev.altKey = e.altKey;
		ev.ctrlKey = e.ctrlKey;
		ev.shiftKey = e.shiftKey;
		return ev;
	}
};
Ext.namespace('odin.ext');
odin.ext = Ext;

// 将日期向后推n个月
Date.addMonth = function(d, n) {
	var year = d.getYear();
	var month = d.getMonth() + n;
	var date = d.getDate();
	return new Date(year, month, date);
}
// 将日期转换成整型的年月
Date.getYM = function(d) {
	var year = d.getYear();
	var month = d.getMonth() + 1;
	return year * 100 + month;
}
// 将整型的年月向后推n个月
Date.addMonthYM = function(ym, n) {
	var year = Math.floor(ym / 100);
	var month = ym % 100 - 1;
	var d = new Date(year, month, 1);
	d = this.addMonth(d, n);
	return this.getYM(d);
}

function getSysType() {// sionline 为网上申报， insiis
	if (parent) {
		return parent.getSysType();
	}
}
function getCurrentAab301() {// 取得当前辖区
	if (parent) {
		return parent.getCurrentAab301();
	}
}

/**
 * 定时去设置焦点到弹出窗口上
 */
function msgInterval() {
	try {
		if (document.hasFocus() && msgint != null && Ext.MessageBox.isVisible() && !(odin.lastEvent && (odin.lastEvent.button == 1 || odin.lastEvent.button == 2))) { // 当前页面获得焦点、有定时任务、弹出窗口显示、鼠标没按
			var dialog = Ext.MessageBox.getDialog();
			// if (document.activeElement) {
			// var element = document.activeElement;
			// while (true) {
			// if (element.parentElement) {
			// element = element.parentElement;
			// if (element.id == dialog.id) {
			// return;
			// }
			// } else {
			// break;
			// }
			// }
			// }
			dialog.toFront();
			dialog.focus();
		}
	} catch (e) {
	}
}
function maskInterval() {
	try {
		if (document.hasFocus() && msgint == null && maskint != null && Ext.get(document.body).isMasked()) {
			if (document.activeElement) {
				document.activeElement.blur();
			}
		}
	} catch (e) {
	}
}
var msgint = null;
var maskint = null;
Ext.onReady(function() {
			Ext.MessageBox.getDialog().on('show', function() {
						msgInterval();
						if (msgint != null) {
							clearInterval(msgint);
						}
						msgint = setInterval(msgInterval, 100);
					});
			Ext.MessageBox.getDialog().on('hide', function() {
						clearInterval(msgint);
						msgint = null;
					});
		});
/**
 * 修正ext的bug--grid的中文排序
 */
Ext.data.Store.prototype.applySort = function() { // 重载 applySort
	if (this.sortInfo && !this.remoteSort) {
		var s = this.sortInfo, f = s.field;
		var st = this.fields.get(f).sortType;
		var fn = function(r1, r2) {
			var v1 = st(r1.data[f]), v2 = st(r2.data[f]);
			// 添加:修复汉字排序异常的Bug
			if (typeof(v1) == "string") { // 若为字符串，
				// 则用 localeCompare 比较汉字字符串, Firefox 与IE 均支持
				return v1.localeCompare(v2);
			}
			// 添加结束
			return v1 > v2 ? 1 : (v1 < v2 ? -1 : 0);
		};
		this.data.sort(s.direction, fn);
		if (this.snapshot && this.snapshot != this.data) {
			this.snapshot.sort(s.direction, fn);
		}
	}
};
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

/**
 * 继承自numberField，可以实现格式化金额数字
 * 
 * @class Ext.form.MoneyField
 * @extends Ext.form.NumberField
 */
Ext.form.MoneyField = Ext.extend(Ext.form.NumberField, {
			// 运行输入的字符集
			baseChars : "0123456789,",
			// private
			initEvents : function() {
				Ext.form.NumberField.superclass.initEvents.call(this);
				var allowed = this.baseChars + '';
				if (this.allowDecimals) {
					allowed += this.decimalSeparator;
				}
				if (this.allowNegative) {
					allowed += "-";
				}
				this.stripCharsRe = new RegExp('[^' + allowed + ']', 'gi');
				var keyPress = function(e) {
					var k = e.getKey();
					if (!Ext.isIE && (e.isSpecialKey() || k == e.BACKSPACE || k == e.DELETE)) {
						return;
					}
					var c = e.getCharCode();
					if (allowed.indexOf(String.fromCharCode(c)) === -1) {
						e.stopEvent();
					}
				};
				this.el.on("keypress", keyPress, this);
			},

			// private
			validateValue : function(value) {
				if (!Ext.form.NumberField.superclass.validateValue.call(this, value)) {
					return false;
				}
				if (value.length < 1) { // if it's blank and textfield didn't
					// flag it then it's valid
					return true;
				}
				var re = /,/g;
				value = String(value).replace(re, '');
				value = String(value).replace(this.decimalSeparator, ".");
				if (isNaN(value)) {
					this.markInvalid(String.format(this.nanText, value));
					return false;
				}
				var num = this.parseValue(value);
				if (num < this.minValue) {
					this.markInvalid(String.format(this.minText, this.minValue));
					return false;
				}
				if (num > this.maxValue) {
					this.markInvalid(String.format(this.maxText, this.maxValue));
					return false;
				}
				return true;
			},

			getValue : function() {
				return this.parseValue(Ext.form.NumberField.superclass.getValue.call(this));
			},

			setValue : function(v) {
				Ext.form.NumberField.superclass.setValue.call(this, v);
			},

			// private
			parseValue : function(value) {
				var re = /,/g;
				value = String(value).replace(re, '');
				value = parseFloat(String(value).replace(this.decimalSeparator, "."));
				return value;
			},

			// private
			fixPrecision : function(value) {
				var nan = isNaN(value);
				if (!this.allowDecimals || this.decimalPrecision == -1 || nan || !value) {
					return nan ? '' : value;
				}
				return parseFloat(parseFloat(value).toFixed(this.decimalPrecision));
			},

			beforeBlur : function() {
				var v = this.getRawValue();
				v = this.parseValue(v);
				v = this.formatMoney(v);
				if (v) {
					this.setValue(v);
				}
			},
			formatMoney : function(s) {
				s = s.toString();
				var flag = "";
				if (s.substring(0, 1) == "-") {
					flag = "-";
					s = s.substring(1);
				}
				if (/[^0-9\.]/.test(s))
					return "invalid value";
				var p = Math.pow(10, this.decimalPrecision);
				s = Math.round(s * p) / p;
				s = s.toString();
				var right = p.toString().substring(1);
				var index = s.indexOf(".");
				if (index != -1) {
					right = s.substring(s.indexOf(".") + 1);
					right = right + p.toString().substring(right.length + 1);
				}
				s = s.replace(/^(\d*)$/, "$1.");
				s = (s + "00").replace(/(\d*\.\d\d)\d*/, "$1");
				s = s.replace(".", ",");
				var re = /(\d)(\d{3},)/;
				while (re.test(s))
					s = s.replace(re, "$1,$2");
				s = s.replace(/,(\d\d)$/, ".$1");
				s = s.substring(0, s.indexOf(".") + 1) + right;
				s = s.replace(/^\./, "0.");
				s = flag + s;
				return s;
			}
		});
Ext.reg('moneyfield', Ext.form.MoneyField);

/**
 * 继承自numberField，可以实现格式化百分比
 * 
 * @class Ext.form.PercentField
 * @extends Ext.form.NumberField
 */
Ext.form.PercentField = Ext.extend(Ext.form.NumberField, {
			// 运行输入的字符集
			baseChars : "0123456789%",
			// private
			initEvents : function() {
				Ext.form.NumberField.superclass.initEvents.call(this);
				var allowed = this.baseChars + '';
				if (this.allowDecimals) {
					allowed += this.decimalSeparator;
				}
				if (this.allowNegative) {
					allowed += "-";
				}
				this.stripCharsRe = new RegExp('[^' + allowed + ']', 'gi');
				var keyPress = function(e) {
					var k = e.getKey();
					if (!Ext.isIE && (e.isSpecialKey() || k == e.BACKSPACE || k == e.DELETE)) {
						return;
					}
					var c = e.getCharCode();
					if (allowed.indexOf(String.fromCharCode(c)) === -1) {
						e.stopEvent();
					}
				};
				this.el.on("keypress", keyPress, this);
			},

			// private
			validateValue : function(value) {
				if (!Ext.form.NumberField.superclass.validateValue.call(this, value)) {
					return false;
				}
				if (value.length < 1) { // if it's blank and textfield didn't
					// flag it then it's valid
					return true;
				}
				var re = /%/g;
				value = String(value).replace(re, '');
				value = String(value).replace(this.decimalSeparator, ".");
				if (isNaN(value)) {
					this.markInvalid(String.format(this.nanText, value));
					return false;
				}
				var num = this.parseValue(value);
				if (num < this.minValue) {
					this.markInvalid(String.format(this.minText, this.minValue));
					return false;
				}
				if (num > this.maxValue) {
					this.markInvalid(String.format(this.maxText, this.maxValue));
					return false;
				}
				return true;
			},

			getValue : function() {
				return this.parseValue(Ext.form.NumberField.superclass.getValue.call(this));
			},

			setValue : function(v) {
				Ext.form.NumberField.superclass.setValue.call(this, v);
			},

			// private
			parseValue : function(value) {
				if (String(value).indexOf("%") != -1) {
					var re = /%/g;
					value = String(value).replace(re, '');
					value = accDiv(value, 100);
				}
				value = parseFloat(String(value).replace(this.decimalSeparator, "."));
				return value;
			},

			// private
			fixPrecision : function(value) {
				var nan = isNaN(value);
				if (!this.allowDecimals || this.decimalPrecision == -1 || nan || !value) {
					return nan ? '' : value;
				}
				return parseFloat(parseFloat(value).toFixed(this.decimalPrecision));
			},

			beforeBlur : function() {
				var v = this.getRawValue();
				v = this.parseValue(v);
				v = this.formatPercent(v);
				if (v) {
					this.setValue(v);
				}
			},
			formatPercent : function(s) {
				return renderPercent(s);
			}
		});
Ext.reg('percentfield', Ext.form.PercentField);
/**
 * 继承自numberField，可以实现格式化百分比或数字
 * 
 * @class Ext.form.PercentOrNotField
 * @extends Ext.form.NumberField
 */
Ext.form.PercentOrNotField = Ext.extend(Ext.form.NumberField, {
			// 运行输入的字符集
			baseChars : "0123456789%",
			// private
			initEvents : function() {
				Ext.form.NumberField.superclass.initEvents.call(this);
				var allowed = this.baseChars + '';
				if (this.allowDecimals) {
					allowed += this.decimalSeparator;
				}
				if (this.allowNegative) {
					allowed += "-";
				}
				this.stripCharsRe = new RegExp('[^' + allowed + ']', 'gi');
				var keyPress = function(e) {
					var k = e.getKey();
					if (!Ext.isIE && (e.isSpecialKey() || k == e.BACKSPACE || k == e.DELETE)) {
						return;
					}
					var c = e.getCharCode();
					if (allowed.indexOf(String.fromCharCode(c)) === -1) {
						e.stopEvent();
					}
				};
				this.el.on("keypress", keyPress, this);
			},

			// private
			validateValue : function(value) {
				if (!Ext.form.NumberField.superclass.validateValue.call(this, value)) {
					return false;
				}
				if (value.length < 1) { // if it's blank and textfield didn't
					// flag it then it's valid
					return true;
				}
				var re = /%/g;
				value = String(value).replace(re, '');
				value = String(value).replace(this.decimalSeparator, ".");
				if (isNaN(value)) {
					this.markInvalid(String.format(this.nanText, value));
					return false;
				}
				var num = this.parseValue(value);
				if (num < this.minValue) {
					this.markInvalid(String.format(this.minText, this.minValue));
					return false;
				}
				if (num > this.maxValue) {
					this.markInvalid(String.format(this.maxText, this.maxValue));
					return false;
				}
				return true;
			},

			getValue : function() {
				return this.parseValue(Ext.form.NumberField.superclass.getValue.call(this));
			},

			setValue : function(v) {
				Ext.form.NumberField.superclass.setValue.call(this, v);
			},

			// private
			parseValue : function(value) {
				if (String(value).indexOf("%") != -1) {
					var re = /%/g;
					value = String(value).replace(re, '');
					value = accDiv(value, 100);
				}
				value = parseFloat(String(value).replace(this.decimalSeparator, "."));
				return value;
			},

			// private
			fixPrecision : function(value) {
				var nan = isNaN(value);
				if (!this.allowDecimals || this.decimalPrecision == -1 || nan || !value) {
					return nan ? '' : value;
				}
				return parseFloat(parseFloat(value).toFixed(this.decimalPrecision));
			},

			beforeBlur : function() {
				var v = this.getRawValue();
				v = this.parseValue(v);
				v = this.formatPercentOrNot(v);
				if (v) {
					this.setValue(v);
				}
			},
			formatPercentOrNot : function(s) {
				return renderPercentOrNot(s);
			}
		});
Ext.reg('percentornotfield', Ext.form.PercentOrNotField);

// 增加多选框MultiCombo
if ('function' !== typeof RegExp.escape) {
	RegExp.escape = function(s) {
		if ('string' !== typeof s) {
			return s;
		}
		return s.replace(/([.*+?\^=!:${}()|\[\]\/\\])/g, '\\$1');
	};
}

Ext.form.MultiCombo = Ext.extend(Ext.form.ComboBox, {
			checkField : 'checked',
			separator : ';',
			constructor : function(config) {
				config = config || {};
				config.listeners = config.listeners || {};
				Ext.applyIf(config.listeners, {
							scope : this,
							beforequery : this.onBeforeQuery,
							blur : this.onRealBlur
						});
				Ext.form.MultiCombo.superclass.constructor.call(this, config);
			},
			initComponent : function() {
				if (!this.tpl) {
					this.tpl = '<tpl for=".">' + '<div class="x-combo-list-item">' + '<img src="' + Ext.BLANK_IMAGE_URL + '" ' + 'class="ux-lovcombo-icon ux-lovcombo-icon-' + '{[values.' + this.checkField + '?"checked":"unchecked"' + ']}">' + '<div class="ux-lovcombo-item-text">{key} - {value}{params}</div>' + '</div>' + '</tpl>';
				}
				Ext.form.MultiCombo.superclass.initComponent.apply(this, arguments);
				this.onLoad = this.onLoad.createSequence(function() {
							if (this.el) {
								var v = this.el.dom.value;
								this.el.dom.value = '';
								this.el.dom.value = v;
							}
						});
			},
			initEvents : function() {
				Ext.form.MultiCombo.superclass.initEvents.apply(this, arguments);
				this.keyNav.tab = false;
			},
			clearValue : function() {
				this.value = '';
				this.setRawValue(this.value);
				this.store.clearFilter();
				this.store.each(function(r) {
							r.set(this.checkField, false);
						}, this);
				if (this.hiddenField) {
					this.hiddenField.value = '';
				}
				this.applyEmptyText();
			},
			getCheckedDisplay : function() {
				var re = new RegExp(this.separator, "g");
				return this.getCheckedValue(this.displayField).replace(re, this.separator + ' ');
			},
			getCheckedValue : function(field) {
				field = field || this.valueField;
				var c = [];
				var snapshot = this.store.snapshot || this.store.data;
				snapshot.each(function(r) {
							if (r.get(this.checkField)) {
								c.push(r.get(field));
							}
						}, this);
				return c.join(this.separator);
			},
			onBeforeQuery : function(qe) {
				qe.query = qe.query.replace(new RegExp(RegExp.escape(this.getCheckedDisplay()) + '[ ' + this.separator + ']*'), '');
			},
			onRealBlur : function() {
				this.list.hide();
				var rv = this.getRawValue();
				var rva = rv.split(new RegExp(RegExp.escape(this.separator) + ' *'));
				var va = [];
				var snapshot = this.store.snapshot || this.store.data;
				Ext.each(rva, function(v) {
							snapshot.each(function(r) {
										if (v === r.get(this.displayField)) {
											va.push(r.get(this.valueField));
										}
									}, this);
						}, this);
				this.setValue(va.join(this.separator));
				this.store.clearFilter();
			},
			onSelect : function(record, index) {
				if (this.fireEvent('beforeselect', this, record, index) !== false) {
					try {
						record.set(this.checkField, !record.get(this.checkField));
					} catch (e) {

					}
					if (this.store.isFiltered()) {
						this.doQuery(this.allQuery);
					}
					this.setValue(this.getCheckedValue());
					this.fireEvent('select', this, record, index);
				}
			},
			setValue : function(v) {
				if (v) {
					v = '' + v;
					if (this.valueField) {
						this.store.clearFilter();
						this.store.each(function(r) {
									var checked = !(!v.match('(^|' + this.separator + ')' + RegExp.escape(r.get(this.valueField)) + '(' + this.separator + '|$)'));
									r.set(this.checkField, checked);
								}, this);
						this.value = this.getCheckedValue();
						this.setRawValue(this.getCheckedDisplay());
						if (this.hiddenField) {
							this.hiddenField.value = this.value;
						}
					} else {
						this.value = v;
						this.setRawValue(v);
						if (this.hiddenField) {
							this.hiddenField.value = v;
						}
					}
					if (this.el) {
						this.el.removeClass(this.emptyClass);
					}
				} else {
					this.clearValue();
				}
			},
			selectAll : function() {
				this.store.each(function(record) {
							record.set(this.checkField, true);
						}, this);
				this.doQuery(this.allQuery);
				this.setValue(this.getCheckedValue());
			},
			deselectAll : function() {
				this.clearValue();
			}
		});
Ext.reg('multicombo', Ext.form.MultiCombo);

/**
 * grid最后一列不能修改bug修复
 */
Ext.override(Ext.grid.GridView, {
			getEditorParent : function() {
				return document.body;
			}
		});
/**
 * grid列值不能复制bug修复
 */
if (!Ext.grid.GridView.prototype.templates) {
	Ext.grid.GridView.prototype.templates = {};
}
Ext.grid.GridView.prototype.templates.cell = new Ext.Template('<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} x-selectable {css}" style="{style}" tabIndex="0" {cellAttr}>', '<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>', '</td>');

/**
 * CKEditor集成
 */
Ext.form.CKEditor = function(config) {
	this.config = config;
	Ext.form.CKEditor.superclass.constructor.call(this, config);
};
Ext.form.CKEditor.CKEDITOR_TOOLBAR = "Default";
Ext.extend(Ext.form.CKEditor, Ext.form.TextArea, {
			onRender : function(ct, position) {
				if (!this.el) {
					this.defaultAutoCreate = {
						tag : "textarea",
						autocomplete : "off"
					};
				}
				Ext.form.TextArea.superclass.onRender.call(this, ct, position);
				var config = {
					customConfig : contextPath + "/commform/basejs/ckeditor/config.js"
					// ,toolbar: Ext.form.CKEditor.CKEDITOR_TOOLBAR //
					// 如需要默认工具条设置，请去掉行前注释
				};
				Ext.apply(config, this.config.CKConfig);
				var editor = CKEDITOR.replace(this.id, config);
				// CKFinder.setupCKEditor(editor, './ckfinder/');
			},
			onDestroy : function() {
				if (CKEDITOR.instances[this.el.id]) {
					delete CKEDITOR.instances[this.el.id];
				}
			},
			setValue : function(value) {
				Ext.form.TextArea.superclass.setValue.apply(this, [value]);
				CKEDITOR.instances[this.el.id].setData(value);
			},
			getValue : function() {
				CKEDITOR.instances[this.el.id].updateElement();
				var value = CKEDITOR.instances[this.el.id].getData();
				Ext.form.TextArea.superclass.setValue.apply(this, [value]);
				return Ext.form.TextArea.superclass.getValue.apply(this);
			},
			getRawValue : function() {
				CKEDITOR.instances[this.el.id].updateElement();
				return Ext.form.TextArea.superclass.getRawValue.apply(this);
			},
			isDirty : function() {
				if (this.disabled) {
					return false;
				}
				var value = String(this.getValue()).replace(/\s/g, '');
				value = (value == "<br />" || value == "<br/>" ? "" : value);
				this.originalValue = this.originalValue || "";
				this.originalValue = this.originalValue.replace(/\s/g, '');
				return String(value) !== String(this.originalValue) ? String(value) !== "<p>" + String(this.originalValue) + "</p>" : false;
			}
		});
Ext.reg('ckeditor', Ext.form.CKEditor);

// 修正日期下拉框在非IE显示不正常
Ext.override(Ext.menu.DateMenu, {
			render : function() {
				Ext.menu.DateMenu.superclass.render.call(this);
				this.picker.el.dom.childNodes[0].style.width = '178px';
				this.picker.el.dom.style.width = '178px';
			}
		});

// 修正弹出窗口的移动问题，移动到浏览器外面，被flash覆盖等问题
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
// 修正Ext.KeyNav不支持空格键的问题
Ext.KeyNav.prototype.keyToHandler[32] = "space";
Ext.KeyNav.prototype.space = false;
/**
 * dateField的keyNav支持
 */
Ext.override(Ext.form.DateField, {
			initEvents : function() {
				Ext.form.DateField.superclass.initEvents.call(this);
				this.keyNav = new Ext.KeyNav(this.el, {
							"up" : function(e) {
								if (!(this.menu && this.menu.isVisible())) {
									this.onTriggerClick();
								}
								this.menu.doFocus();
								this.menu.tryActivate(0, 0);
							},
							"down" : function(e) {
								if (!(this.menu && this.menu.isVisible())) {
									this.onTriggerClick();
								}
								this.menu.doFocus();
								this.menu.tryActivate(0, 0);
							},
							"enter" : function(e) {
								this.triggerBlur();
								odin.dateBlur(this);
								return true;
							},
							"esc" : function(e) {
								odin.dateBlur(this);
							},
							"tab" : function(e) {
								this.triggerBlur();
								odin.dateBlur(this);
								return true;
							},
							scope : this,
							doRelay : function(foo, bar, hname) {
								if ((hname == 'down' || hname == 'up' || hname == 'esc' || hname == 'enter' || hname == 'tab')) {
									return Ext.KeyNav.prototype.doRelay.apply(this, arguments);
								}
								return true;
							},
							forceKeyDown : true
						});
			},
			beforeBlur : function() {
				doOnChangeCheckDate(this);
			}
		});
/**
 * 增加查询组件QueryField
 */
Ext.form.QueryField = Ext.extend(Ext.form.TriggerField, {
			initEvents : function() {
				Ext.form.TriggerField.superclass.initEvents.call(this);
				this.keyNav = new Ext.KeyNav(this.el, {
							"enter" : function(e) {
								this.triggerBlur();
								return true;
							},
							"tab" : function(e) {
								this.triggerBlur();
								return true;
							},
							scope : this,
							doRelay : function(foo, bar, hname) {
								if ((hname == 'enter' || hname == 'tab')) {
									return Ext.KeyNav.prototype.doRelay.apply(this, arguments);
								}
								return true;
							},
							forceKeyDown : true
						});
			}
		});
Ext.reg('queryfield', Ext.form.QueryField);

/**
 * EditorGridPanel的date格式列不会自动换焦点的bug修改
 */
Ext.override(Ext.grid.EditorGridPanel, {
			onEditComplete : function(ed, value, startValue) {
				this.editing = false;
				this.activeEditor = null;
				ed.un("specialkey", this.selModel.onEditorKey, this.selModel);
				if (String(value) !== String(startValue)) {
					var r = ed.record;
					var field = this.colModel.getDataIndex(ed.col);
					var e = {
						grid : this,
						record : r,
						field : field,
						originalValue : startValue,
						value : value,
						row : ed.row,
						column : ed.col,
						cancel : false
					};
					if (this.fireEvent("validateedit", e) !== false && !e.cancel) {
						r.set(field, e.value);
						delete e.cancel;
						this.fireEvent("afteredit", e);
					}
				}
				// 增加的内容，修改日期的焦点问题
				var keyCode = (event != null ? (event.keyCode || event.which || event.charCode) : null);
				if ((keyCode == 13 || keyCode == 9) && getGridFieldType(this.id, this.colModel.getDataIndex(ed.col)) == "date") {
					var r = ed.record;
					var field = this.colModel.getDataIndex(ed.col);
					var e = {
						grid : this,
						record : r,
						field : field,
						originalValue : startValue,
						value : value,
						row : ed.row,
						column : ed.col,
						cancel : false
					};
					var grid = e.grid;
					var editor = grid.getColumnModel().getCellEditor(e.column, e.row);
					var newCell = grid.walkCells(e.row, e.column + 1, 1, grid.getSelectionModel().acceptsNav, editor);
					if (newCell != null) {
						grid.startEditing(newCell[0], newCell[1]);
					}
				}
				this.view.focusCell(ed.row, ed.col);
			}
		});
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