// JavaScript Document
(function() {
	var syst = publ.syst;

	// throw new exception("系统异常，实体类型错误！");

	if (syst == null || syst == undefined) {
		throw new exception("对象未加载!");
	}
	if (typeof syst != "object") {
		throw new exception("系统异常，实体类型错误！");
	}

	// 页面初始化句柄函数
	function init() {
		var formObj = syst.getEleObj("upform");
		formObj.onsubmit = checkForm;

		// 获取附件信息
		var routePath = syst.getEleObj("routePath").value;
		var mid = syst.getEleObj("mid").value;
		var bid = syst.getEleObj("bid").value;
		var max_size = syst.getEleObj("max_size").value;
		var userName = syst.getEleObj("userName").value;

		/*var content = "{routePath:" + routePath + ",mid:" + mid + ",bid:" + bid
				+ ",max_size:" + max_size + ",userName:" + userName + "}";
		var url = syst.getEleObj("contextPath").value
				+ "/commform/onlineview/OnlineViewServlet?method=getAttachments";
		syst.sendReqAsync("post", url, content, getAttachments);*/
		
		var url = syst.getEleObj("contextPath").value
		+ "/commform/onlineview/OnlineViewServlet?method=getAttachments1&routePath="+routePath+"&mid="+mid+"&bid="+bid+"&max_size="+max_size+"&userName="+userName;
		
		//var attachGrid=syst.getEleObj("attachGrid");
		//var attachGrid=Ext.getCmp("attachGrid");
		//attachGrid.url=url;

	}

	// 服务端预览
	function yulan() {
		var fid = this.id;
		var contextPath = syst.getEleObj("contextPath").value;

		//预览
		var url = contextPath
				+ "/commform/onlineview/OnlineViewServlet?method=yulan&fid=" + fid;		

		//syst.sendReqAsync("post", url, null, lookInFrame);
		window.open(url,"fileContentArea","location=no;memubar=no;titlebar=no;toolbar=no;",false);

	}
	
	function lookInFrame(responseText){
		window.open(responseText,"","menubar=yes,location=no",false);
		
		
	}

	// 呈现附件信息
	function getAttachments(jsonStr) {

		var jsonInfo = eval("(" + jsonStr + ")");// 转换成json对象
		var num = jsonInfo.num;// 附件数

		var attachArea = syst.getEleObj("attachArea");

		var attachInfo = "";// 附件呈现内容

		var info = jsonInfo.contents;// json数组
		if (info != null && info != undefined) {
			if (info.length <= 2) {
				for (i = 0; i < info.length; i++) {
					var fileName = info[i].filename;
					var fid = info[i].fid;

					// 点击附件名，预览附件
					attachInfo += "<a id='" + fid + "' name='" + fid
							+ "' href='#'>" + fileName + "</a>&nbsp;";
				}
			} else {
				for (i = 0; i < 3; i++) {
					var fileName = info[i].filename;
					var fid = info[i].fid;

					attachInfo += "<a id='" + fid + "' name='" + fid
							+ "' href='#'>" + fileName + "</a>&nbsp;";
				}
				attachInfo += "<a href='#'>...更多附件</a>";
			}
		}

		attachArea.innerHTML = attachInfo;

		var attachArea = syst.getEleObj("attachArea");

		var nodes = attachArea.childNodes;

		for (var i = 0; i < nodes.length; i++) {
			var ele = nodes[i];
			//ele.onclick = yulan;//预览
			ele.onclick=downloadAtta;//下载
		}
	}

	// 下载附件
	function downloadAtta() {
		var contextPath = syst.getEleObj("contextPath").value;

		var url = contextPath
				+ "/commform/onlineview/OnlineViewServlet?method=downloadAtta&fid="
				+ this.id;
		
		this.href=url;

		//syst.sendReqAsync("post", url, null, null);

	}

	// 文件上传校验函数
	function checkForm() {
		var fileValue = syst.getEleObj("upfile").value;
		if (fileValue == null || fileValue == undefined || fileValue == "") {
			alert("请选择上传文件！");
			return false;
		}
		return true;
	}

	syst.doInit(init);
})()