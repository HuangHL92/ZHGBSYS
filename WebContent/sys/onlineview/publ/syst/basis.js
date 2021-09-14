// JavaScript Document
var publ;
if (publ == null || publ == undefined)
	publ = {};
else if (typeof publ != object)
	throw new exception("系统异常，实体类型错误！");

var syst = publ.syst;
if (syst == null || syst == undefined)
	publ.syst = {};
else if (typeof syst != "object")
	throw new exception("系统异常，实体类型错误！");

// var $ = document.getElementById.bind(document);
// function $(id){ return document.getElementById(id); }
// 修复document.getElementById
// document.getElementById = (function(fn){
// return function(){
// return fn.apply(document,arguments);
// };
// })(document.getElementById);

// 修复后赋值给$，$可正常使用了
// var $ = document.getElementById;

/* 根据id属性获取元素对象 */
publ.syst.getEleObj = function(/* 元素id */id) {
	var element;
	if (document.getElementById)
		element = document.getElementById(id);
	else if (document.all)
		element = document.all.id;
	else
		throw new exception("元素获取失败，系统不支持此方法！");

	// element=document.getElementById.apply(document,arguments);

	return element;
};

/* 页面初始化事件 */
publ.syst.doInit = function(init) {
	if (window.addEventListener) {
		// alert("支持addEventListener");
		// 谷歌浏览器，进入此方法，支持w3c Dom
		// 最好采用此方法，若用document.getElementById方法，有可能取到null值
		window.addEventListener("load", init, false);
	} else if (window.attachEvent) {
		// alert("支持attachEvent方法");
		// qq浏览器，进入此方法,支持IE dom
		window.attachEvent("onload", init);
	} else {
		// alert("采用1级DOM方式注册句柄");
		window.onload = init;
	}
};

/* 获取xmlhttprequest对象 */
/*
 * publ.syst.getAjaxObj = function() { var xmlReq; if (window.XMLHttpRequest)
 * xmlReq = new XMLHttpRequest(); else xmlReq = new
 * ActiveXObject("Microsoft.XMLHTTP");
 * 
 * return xmlReq; };
 */

/* 异步请求发送 */
publ.syst.sendReqAsync = function(/* 请求对象 *//* 请求方式 */type,/* 处理请求的url */
url,/* 发送内容 */contInfo,/* 请求结果处理函数 */func) {

	var xmlreq;

	if (window.XMLHttpRequest)
		xmlreq = new XMLHttpRequest();
	else
		xmlreq = new ActiveXObject("Microsoft.XMLHTTP");

	if (xmlreq == null || xmlreq == undefined)
		throw new exception("系统异常，请求对象为空！");

	// alert(xmlreq);

	xmlreq.onreadystatechange = function() {
		// alert(""+xmlreq.readyState+","+xmlreq.status);
		if (xmlreq.readyState == 4
				&& (xmlreq.status == 200 || xmlreq.status == 0)){
			// alert(xmlreq.responseText);
			
				if(func)
					func(xmlreq.responseText);
			}
		
	};

	xmlreq.open(type, url, true);
	xmlreq.send(contInfo);

};