(function() {
	var syst = publ.syst;

	// throw new exception("系统异常，实体类型错误！");

	if (syst == null || syst == undefined) {
		throw new exception("对象未加载!");
	}
	if (typeof syst != "object") {
		throw new exception("系统异常，实体类型错误！");
	}
	
	var TANGER_OCX_OBJ;
	var contextPath;

	function init() {
		
		contextPath=syst.getEleObj("contextPath").value;

		TANGER_OCX_OBJ = publ.syst.getEleObj("TANGER_OCX");// office控件对象
		TANGER_OCX_OBJ.BorderStyle = "1";
		TANGER_OCX_OBJ.BorderColor = "14402205";
		TANGER_OCX_OBJ.Titlebar = "false";
		TANGER_OCX_OBJ.TitlebarColor = "14402205";
		TANGER_OCX_OBJ.TitlebarTextColor = "0";
		TANGER_OCX_OBJ.MenubarColor = "14402205";
		TANGER_OCX_OBJ.MenuBarStyle = "3";
		TANGER_OCX_OBJ.MenuButtonStyle = "7";
		TANGER_OCX_OBJ.filenew = "false";
		TANGER_OCX_OBJ.fileopen = "false";
		TANGER_OCX_OBJ.fileclose = "true";
		TANGER_OCX_OBJ.CustomMenuCaption = "我的菜单";
		TANGER_OCX_OBJ.filesave = "false";
		TANGER_OCX_OBJ.filesaveas = "true";
		TANGER_OCX_OBJ.ToolBars = "true";
		TANGER_OCX_OBJ.setDocUser = "手写批注";
		TANGER_OCX_OBJ.MakerCaption = "浙大网新";
		TANGER_OCX_OBJ.MakerKey = "A4DA5FAE3156885B0CD5A2F880394E271E16A766";
		TANGER_OCX_OBJ.ProductCaption = "浙大网新用户";
		TANGER_OCX_OBJ.ProductKey = "19C1B656938BB467F7C12F827BA81A6F393788F0";

		var resCode = syst.getEleObj("resCode").value;
		var message = syst.getEleObj("message").value;
		var type = syst.getEleObj("type").value;

		
		if (resCode == "0") {
			if (type == "0") {
				syst.getEleObj("memoinfo").style.color = "blue";
				syst.getEleObj("memoinfo").innerHTML = message;
			} else {
				//预览
				syst.getEleObj("TANGER_OCX_Area").style.visibility = "visible";
				var fid=message;
				var url=contextPath+"/commform/onlineview/OnlineViewServlet?method=ylByFid&fid="+fid;
				syst.sendReqAsync("post", url, null, openFileFromServer);
				//TANGER_OCX_OBJ.OpenFromURL(contextPath+"/page/onlineview/OnlineViewServlet?method=ylByFid&fid="+fid);
			}

		} else if (resCode == "1") {
			syst.getEleObj("memoinfo").style.color = "red";
			syst.getEleObj("memoinfo").innerHTML = message;
		}
	}
	
	function openFileFromServer(responseText){
		TANGER_OCX_OBJ.OpenFromURL(responseText);
		
		//删除存放在服务器项目路径下的文件，节省资源
		var contextPath = syst.getEleObj("contextPath").value;
		
		var url=contextPath+"/commform/onlineview/OnlineViewServlet?method=deleteFile";
		syst.sendReqAsync("post", url, responseText, null);
	}

	syst.doInit(init);
})()