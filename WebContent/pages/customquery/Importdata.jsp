<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>


<%@page import="net.sf.json.JSONArray"%>
<html>
<head>
<link rel="icon" href="/hzb/images/favicon.ico" mce_href="/hzb/images/favicon.ico" type="image/x-icon">  
	<link rel="shortcut icon" href="/hzb/images/favicon.ico" mce_href="/hzb/images/favicon.ico" type="image/x-icon">
	<link rel="stylesheet" type="text/css" href="/hzb/basejs/ext/resources/css/ext-all.css"/>




<script type="text/javascript"src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>basejs/helperUtil.js"></script>
<script src="<sicp3:rewrite forward='md5'/>"> </script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/odin.css"/>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>导入文件</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
</head>
<style type = "text/css">
#addResourcePanel{  width:820;height:5px};
/* #pipei{position:relative;left:30px;top: -20px;} */
#addResourceContent{position:relative;left:15px;}
 #infos{width:800} 
#guanli{position:relative;left:20px;}
#span{position:relative;left:263px;top:68px;}
</style>

<script type="text/javascript">
</script>
<body>
<%@include file="/comOpenWinInit.jsp" %>
<odin:hidden property="filename"/>
<div id="allpanel">
<div id="addResourcePanel"></div>
<iframe width="525" height="85px" src="<%=request.getContextPath() %>/pages/customquery/Importdata2.jsp" id=iframe1></iframe>

<div id="addResourceContent">
</div>
</div>
<odin:toolBar property="btnToolBar" >
	<odin:fill />
	<odin:buttonForToolBar id="btnNew1" handler="formSubmit2" text="自定义方案导入" icon="images/i_2.gif" isLast="true"
		cls="x-btn-text-icon" />
	<%-- <odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnClose" handler="closeWin" isLast="true"  text="关闭"
		icon="images/save.gif" cls="x-btn-text-icon" /> --%>
</odin:toolBar>
<odin:panel contentEl="allpanel" width="330" topBarId="btnToolBar" property="ssszzzzss"></odin:panel>

<script>
	
function formSubmit2(){
	var filename=window.frames["iframe1"];
	if(filename.document.getElementById("filename").value==''||filename.document.getElementById("filename").value==undefined){
		alert("不能上传空文件！");
		location.reload();
		return;
	}
	filename.formSubmit2();
	<%-- var form1=document.getElementById("formId");
	Ext.Ajax.request({
		url:'<%=request.getContextPath()%>/UploadQueryServlet?method=zzbFileImp',
		params : {'filename':filename},
		success:function(){
			alert();
			//realParent.odin.ext.getCmp('simpleExpWin').hide();
			//parent.Ext.getCmp(subWinId).close(); 
		}
	}); --%>
}
/* function doCloseWin1(type){
	parent.odin.ext.getCmp('simpleExpWin11111').close();
} */

/* //关闭窗口
function closeWin(){
	parent.odin.ext.getCmp('impNewHzbORGWin').close();
} */
function closeWin(){
	realParent.location.reload();
	parent.odin.ext.getCmp('Importdata').close();
}
function doRenewal(){
	location.reload();
}
</script>
<odin:response onSuccess="doSuccess"/>
</body>
</html>