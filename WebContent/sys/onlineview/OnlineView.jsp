
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<script type="text/javascript" src="<%=request.getContextPath()%>/page/publ/syst/basis.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/onlineview/OnlineView.js"></script>

<!-- 关闭文档事件 -->
<script language="JScript" for="TANGER_OCX" event="OnDocumentClosed()">
//文档关闭时，本地临时文件删除
	var TANGER_OCX_OBJ=publ.syst.getEleObj("TANGER_OCX");
	
	//alert(TANGER_OCX_OBJ.DocFileName);//获取当前打开的文档
	publ.syst.sendReqAsync("post", "OnlineViewServlet?method=closeDocOnServer",
			TANGER_OCX_OBJ.DocFileName,null);
	
	//TANGER_OCX_OBJ.close();
</script>

<!-- 点击自定义按钮事件 ,咱无法使用-->
<script language="JScript" for="TANGER_OCX" event="OnCustomMenuCmd2(menuPos,submenuPos,subsubmenuPos,menuCaption,menuID)">
	var TANGER_OCX_OBJ=publ.syst.getEleObj("TANGER_OCX");
	if(menuID==2){
		alert(menuCaption);
		TANGER_OCX_OBJ.close();
	}
		
</script>

<title>文档在线上传、预览</title>
</head>
<body style="background-color:#1E90FF">

	<!-- id="viewContent" style="position:relative;left:0;top:50%" -->
	<div style="position:absolute;height:50px;left:50%">
		<object id="TANGER_OCX" type="application/activex" classid="clsid:C9BC4DFF-4248-4a3c-8A49-63A7D317F404" 
		codebase="<%=request.getContextPath()%>/control/OfficeControl.cab#version=5,0,2,4"
		width="100%" height="100%">
        
	</object>
	</div>

	<form id="fileForm" name="fileForm" method="post" style="position:absolute;top:50%;left:50%" target="fileContentArea">
		请选择文件：
		<input type="file" id="file" name="file"></input><br>
		<input type="button" id="toCompu" name="toCompu" value="上传到服务器"/>&nbsp;
		<input type="button" id="toOrcl" name="toOrcl" value="上传到数据库"/>&nbsp;
		<input type="button" id="toLook" name="toLook" value="本地预览"/>&nbsp;
		<input type="button" id="toLook1" name="toLook1" value="服务端预览"/><br>
		<input type="button" id="toFTP" name="toFTP" value="上传到FTP"/>&nbsp;
		<input type="button" id="toLookFromFTP" name="toLookFromFTP" value="FTP预览"/>
		<input type="hidden" id="fileName" name="fileName"/>
	</form>
	
	<iframe id="fileContentArea" name="fileContentArea" style="visibility:hidden"></iframe>
	
</body>
</html>