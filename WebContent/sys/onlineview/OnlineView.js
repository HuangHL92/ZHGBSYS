/**
 * 
 */

(function() {

	var ajaxObj;// ajax对象

	var TANGER_OCX_OBJ;
	var syst = publ.syst;

	// throw new exception("系统异常，实体类型错误！");

	if (syst == null || syst == undefined) {
		throw new exception("对象未加载!");
	}
	if (typeof syst != "object") {
		throw new exception("系统异常，实体类型错误！");
	}

	if (window.addEventListener) {
		// alert("支持addEventListener");
		// 谷歌浏览器，进入此方法，支持w3c Dom
		//最好采用此方法，若用document.getElementById方法，有可能取到null值
		window.addEventListener("load", init, false);
	} else if (window.attachEvent) {
		// alert("支持attachEvent方法");
		// qq浏览器，进入此方法,支持IE dom
		window.attachEvent("onload", init);
	} else {
		// alert("采用1级DOM方式注册句柄");
		window.onload = init;
	}

	function init() {
		// alert(syst.getEleObj("toCompu"));
		// qq浏览器不支持getElementById方法
		// document.getElementById("toCompu").onclick=toCompuCick;
		// document.getElementById("toOrcl").onclick=toOrclCick;
		
		
		TANGER_OCX_OBJ = publ.syst.getEleObj("TANGER_OCX");// office控件对象
		TANGER_OCX_OBJ.BorderStyle="1";
		TANGER_OCX_OBJ.BorderColor="14402205";
		TANGER_OCX_OBJ.Titlebar="false";
		TANGER_OCX_OBJ.TitlebarColor="14402205";
		TANGER_OCX_OBJ.TitlebarTextColor="0";
		TANGER_OCX_OBJ.MenubarColor="14402205";
		TANGER_OCX_OBJ.MenuBarStyle="3";
		TANGER_OCX_OBJ.MenuButtonStyle="7";
		TANGER_OCX_OBJ.filenew="false";
		TANGER_OCX_OBJ.fileopen="false";
		TANGER_OCX_OBJ.fileclose="true";
		TANGER_OCX_OBJ.CustomMenuCaption="我的菜单";
		TANGER_OCX_OBJ.filesave="false";
		TANGER_OCX_OBJ.filesaveas="true";
		TANGER_OCX_OBJ.ToolBars="true";
		TANGER_OCX_OBJ.setDocUser="手写批注";
		TANGER_OCX_OBJ.MakerCaption="浙大网新";
		TANGER_OCX_OBJ.MakerKey="A4DA5FAE3156885B0CD5A2F880394E271E16A766";
		TANGER_OCX_OBJ.ProductCaption="浙大网新用户";
		TANGER_OCX_OBJ.ProductKey="19C1B656938BB467F7C12F827BA81A6F393788F0";
		
		
		
		
		//TANGER_OCX_OBJ=document.getElementById("TANGER_OCX");
		//var ddd=document.getElementById("fileForm");
		//TANGER_OCX_OBJ.fileClose=true;无用
		//TANGER_OCX_OBJ.addCustomMenuItem("关闭文档",false,true,2);//在param属性CustomMenuCaption中，添加自定义菜单选项,此方法已过期
		//TANGER_OCX_OBJ.AddCustomMenu2(0,"我的菜单1");
		//TANGER_OCX_OBJ.AddCustomMenuItem2(0,0,-1,false,"关闭文档",false,2);
		// alert(TANGER_OCX_OBJ);

		syst.getEleObj('toCompu').onclick = toCompuCick;
		syst.getEleObj('toOrcl').onclick = toOrclCick;
		syst.getEleObj('toLook').onclick = toLook;// 本地预览
		syst.getEleObj('toLook1').onclick = toLook1;// 服务端预览
		
		//FTP文件上传、预览
		syst.getEleObj("toFTP").onclick=toFTPClick;
		syst.getEleObj("toLookFromFTP").onclick=toLookFromFTP;

		var formObj = syst.getEleObj("fileForm");
		//formObj.action = "OnlineViewServlet?method=toCompu";
		//formObj.acceptCharset="UTF-8";
		formObj.enctype = formObj.encoding = "multipart/form-data";// 表单文件上传，需设置此项属性，关键一步
		// alert("hei");

	}

	// 上传到服务器
	function toCompuCick() {

		var ajax = getAjax();

		var form = syst.getEleObj("fileForm");
		var formFile = form.file.value;
		var formFileEncry = strEncryByUnicode(formFile);

		var content = "{fileName:" + formFileEncry + "}";

		// alert(ajax);
		requestAsync("post", "/primarymath/page/onlineview/OnlineViewServlet?method=toCompuVali", succFun,
				"toCompuVali", content);
		// var form = syst.getEleObj("fileForm");
		// form.submit();
	}
	
	//上传到ftp服务器
	function toFTPClick(){
		var form = syst.getEleObj("fileForm");
		var formFile = form.file.value;
		var formFileEncry = strEncryByUnicode(formFile);

		var content = "{fileName:" + formFileEncry + "}";
		
		requestAsync("post","OnlineViewServlet?method=toCompuVali",succFTPUpload,"toFTP",content);
	}

	function toOrclCick() {
	}

	// 本地预览
	function toLook() {
		var fileName = syst.getEleObj("fileForm").file.value;
		alert(fileName);
		if (fileName == "" || fileName == undefined) {
			alert("请先选择文档");
			return;
		} else {
			TANGER_OCX_OBJ.OpenLocalFile(fileName);
			// TANGER_OCX_OBJ.OpenFromURL(fileName);
			// TANGER_OCX_OpenDocFile(fileName);
		}
	}

	// 服务端预览
	function toLook1() {
		var name = "E:\\primarymathFile\\Insiis6系统探析.ppt";
		
		var fileInServer = "{fileName:" + strEncryByUnicode(name) + "}";

		
		requestAsync("post", "/primarymath/page/onlineview/OnlineViewServlet?method=lookFromServer",
				lookFromServer, "lookFromServer", fileInServer);
	}
	
	
	//ftp预览
	function toLookFromFTP(){
		var name="Insiis6系统探析.ppt";
		var ftpFile="{fileName:" + strEncryByUnicode(name) + "}";
		
		requestAsync("post", "OnlineViewServlet?method=lookFromFTP",
				lookFromFTP, "lookFromFTP", ftpFile);
	}

	// 服务端预览
	function lookFromServer(responseText) {
		var fileName = responseText;
		alert(fileName);

		if (fileName != undefined && fileName != "") {
			TANGER_OCX_OBJ.OpenLocalFile(fileName);
			
			//服务端预览，关闭文档时调用，预览结束后，删除本地临时文档,无用
			TANGER_OCX_OBJ.OnDocumentClosed=function(){
				requestAsync("post", "/primarymath/page/onlineview/OnlineViewServlet?method=closeDocOnServer",
						null, "closeDocOnServer", fileName);
			}
		}

	}

	//FTP服务端预览
	function lookFromFTP(responseText){
		var fileName = responseText;
		alert(fileName);

		if (fileName != undefined && fileName != "") {
			TANGER_OCX_OBJ.OpenLocalFile(fileName);
			
			//服务端预览，关闭文档时调用，预览结束后，删除本地临时文档,无用
			TANGER_OCX_OBJ.OnDocumentClosed=function(){
				requestAsync("post", "OnlineViewServlet?method=closeDocOnServer",
						null, "closeDocOnServer", fileName);
			}
		}
	}
	
	
	// 获取Ajax对象
	function getAjax() {
		if (ajaxObj == undefined || ajaxObj == null)
			ajaxObj = new XMLHttpRequest();

		if (ajaxObj == undefined || ajaxObj == null) {
			alert("采用ActiveXObject方式构建ajax对象");
			ajaxObj = new ActiveXObject("Microsoft.XMLHTTP");
		}

		if (ajaxObj == undefined || ajaxObj == null)
			throw new exception("系统不支持创建ajax对象");

		return ajaxObj;
	}

	// 异步请求
	function requestAsync(method, url, callBack,
			type/* 请求标记，用于区分不同功能的ajax请求 */, content/* 请求内容,采用json格式 */) {

		// formFile.replace("\\","/");
		// alert(formFile);
		// alert(form);
		var ajax = getAjax();
		// ajax.onreadystatechange=ajaxStateChange;
		ajax.onreadystatechange = function() {
			// alert(ajax.status);
			if (ajax.readyState == 4
					&& (ajax.status == 200 || ajax.status == 0)) {

				if (callBack) {
					// alert("我操");
					callBack(ajax.responseText);
					if (type == "toCompuVali") {
						var form = syst.getEleObj("fileForm");
						var formFile = form.file.value;
						var formFileEncry = strEncryByUnicode(formFile);
						form.action = "/primarymath/page/onlineview/OnlineViewServlet?method=toCompu&file="+formFileEncry;
						form.submit();
					}// 表单验证提交请求提交
					
					if(type=="toFTP"){
						var form = syst.getEleObj("fileForm");
						var formFile = form.file.value;
						var formFileEncry = strEncryByUnicode(formFile);
						form.action = "OnlineViewServlet?method=toFTP&file="+formFileEncry;
						form.submit();
					}
					
					
				}
			}
		}
		ajax.open(method, url, true);

		ajax.send(content);
	}

	function succFun(responseText) {
		syst.getEleObj("fileContentArea").style.visibility = "visible";
		syst.getEleObj("fileName").value = responseText;
		// alert(responseText);
	}
	
	function succFTPUpload(responseText){
		syst.getEleObj("fileContentArea").style.visibility = "visible";
		syst.getEleObj("fileName").value = responseText;
	}
	/*
	 * function ajaxStateChange(){ var ajax=getAjaxObj(); if(ajax.readyState==4 &&
	 * (ajax.status==200 || ajax.status==0)){ } }
	 */
	// 字符串编码,unicode
	function strEncryByUnicode(source) {
		var str = "";

		if (source == "" || source == undefined || source == null)
			return "";
		else {
			for (var i = 0; i < source.length; i++) {
				if (i == source.length - 1)
					str = str + source.charCodeAt(i);
				else
					str = str + source.charCodeAt(i) + ";";
			}
		}
		return str;
	}

})();
