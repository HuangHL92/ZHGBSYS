<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.insigma.siis.local.business.entity.A36"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib  uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page isELIgnored="false" %>
<style>
html,body{width:100%; height:100%; margin: 0; padding: 0;}
body{width:100%; height:100%;margin: 0; padding: 0; font-family: "microsoft yahei"; font-size: 14px; color: #fff; background-image:url(../images/main-backgorund2.png); background-repeat: repeat; background-position: left top; background-size:100% 100%; overflow: hidden; }
.main-box{
	width: 100%; 
	height: 100%;
	 background-image: url(pages/amain/img/main-background.png);
	 background-repeat: no-repeat; 
	 background-position: bottom center; 
	 text-align: center;
	 
}
.main-block{
	width:178px;
	height:142px;
	display: inline-block;
	background-repeat: no-repeat;
	background-position: left top;
	background-size: 100%;
	border-radius: 5px;
	text-align: center;
	vertical-align: top;
	margin-left:30px;
	margin-right:30px;
	margin-top:15%;
	cursor: pointer;
	float: left;
}
.main-block:hover{opacity: 0.5;}
.main-block1-box{
	background-image: url(pages/amain/img/pbxz1.png);
	width:250px;
    height:250px;
}
.main-block2-box{
	background-image: url(pages/amain/img/gbtp1.png);
	width:250px;
    height:250px;
}
.main-block3-box{
	background-image: url(pages/amain/img/bztp1.png);
	width:250px;
    height:250px;
}
.main-block img {height:56px; text-align: center; margin-top:20px;}
.main-block p{ font-size: 16px; font-weight: bold; margin: 15px 0 0 0 ; padding: 0;}

</style>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
    <link rel="stylesheet" href="web/css/maincss.css" />
    <title></title>
    <odin:head></odin:head>
    <odin:base></odin:base>
    <script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
</head>
<body  style="width:100%;height:100%;overflow-x: hidden;overflow-y: auto;  ">
<%-- <button onclick="openNewWindow('<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.fxyp.RXFXYP','干部调配人选分析研判表')">人选分析研判</button>
<button onclick="openNewWindow('<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.xbrm2.YNTPGL','酝酿调配方案')">酝酿调配方案</button>
<button onclick="openNewWindow('<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.xbrm.JSGLNew','全称纪实管理')">全称纪实管理</button>
 --%><div class="main-box">
 	<div class="main-block main-block1-box" onclick="addTab3()" id="rxfxypDIV" style="margin-left: 100px;">

	</div>
	<div class="main-block main-block2-box" onclick="addTab4()" style="margin-left: 100px;">

	</div>
	<div class="main-block main-block3-box" onclick="addTab5()" style="margin-left: 100px;">

	</div>

	<%-- openNewWindow('<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.jsgl.JSGLNew','全称纪实管理') --%>
</div>
<script type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var w = (viewSize.width-1000)/2;
	$("#rxfxypDIV").css('margin-left',w);
});


function addTab3(){
	$h.openPageModeWin('TPQXXB','pages.fxyp.MNTPQXXB&type=1','模拟调配前信息表',1300,800,'',g_contextpath);
}
function addTab4(){
	//parent.addTab1('3e2081e47288909a017288daf4b90003','模拟调配','/radowAction.do?method=doEvent&pageModel=pages.fxyp.Mntp')
	//parent.addTab1('c788310a78f322dd0178f3564ec3000b','个别调配','/radowAction.do?method=doEvent&pageModel=pages.fxyp.gbtp.MNTPGBTP')
	parent.addTab1('c788310a78f7222d0178f86ebcb80015','个别调配','/radowAction.do?method=doEvent&pageModel=pages.mntpsj.MntpSJGBTP')
}
function addTab5(){
	//parent.addTab1('3e2081e47288909a017288daf4b90003','模拟调配','/radowAction.do?method=doEvent&pageModel=pages.fxyp.Mntp')
	parent.addTab1('8ab66e277801431e0178014c519f0003','单位调配','/radowAction.do?method=doEvent&pageModel=pages.mntpsj.MntpSJ')
}
</script>
</body>
</html>



