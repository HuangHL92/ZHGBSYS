<%@page import="java.util.ArrayList"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.insigma.siis.local.business.entity.A36"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib  uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page isELIgnored="false" %>

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
<%-- <button onclick="openNewWindow('<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.fxyp.RXFXYP','�ɲ�������ѡ�������б�')">��ѡ��������</button>
<button onclick="openNewWindow('<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.xbrm2.YNTPGL','������䷽��')">������䷽��</button>
<button onclick="openNewWindow('<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.xbrm.JSGLNew','ȫ�Ƽ�ʵ����')">ȫ�Ƽ�ʵ����</button>
 --%>
 <div class="main-box">
 	<div class="main-block main-block1-box" onclick="addTab3()" id="rxfxypDIV" style="margin-left: 100px;">
	</div>
 	<div class="main-block main-block2-box" onclick="addTab6()" style="margin-left: 100px;">
	</div>
	<div class="main-block main-block3-box" onclick="addTab5()" style="margin-left: 100px;">
	</div>
	
	<!-- <div class="main-block main-block1-box" onclick="addTab1()">
		<img src="web/images/main-block1icon.png"  />
		<p>�������</p>
	</div>
	<div class="main-block main-block2-box" onclick="addTab2()">
		<img src="web/images/main-block2icon.png"  />
		<p>���鷽��</p>
	</div> -->
	<%--<div class="main-block main-block3-box" onclick="openNewWindow('<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.jsgl.JSGLNew','ȫ�Ƽ�ʵ����')">
		<img src="web/images/main-block3icon.png"  />
		<p>ȫ�̼�ʵ����</p>
	</div> --%>
	<!-- <div class="main-block main-block3-box" onclick="addTab4()">
		<img src="web/images/main-block3icon.png"  />
		<p>��������</p>
	</div> -->
	<%-- openNewWindow('<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.jsgl.JSGLNew','ȫ�Ƽ�ʵ����') --%>
</div>
<script type="text/javascript">
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var w = (viewSize.width-1150)/2;
	$("#rxfxypDIV").css('margin-left',w);
});


function addTab4(){
	parent.addTab1('3e2081ee7645143f0176451834a80003','��������','/radowAction.do?method=doEvent&pageModel=pages.fxyp.MNMDGL')
}

function addTab3(){
	$h.openPageModeWin('TPQXXB','pages.fxyp.MNTPQXXB&type=1','ģ�����ǰ��Ϣ��',1300,800,'',g_contextpath);
}
var CodeTypeTree = parent.CodeTypeTree.tree.children;
var gbgltitle = "";
for(var i=0;i<CodeTypeTree.length;i++){
	if(CodeTypeTree[i].id=='3e2081e4728392a5017283bb71330003'){
		gbgltitle = CodeTypeTree[i].title.replace('�ɲ�����','');
		break;
	}
}
function addTab6(){
	
	parent.addTab1('c788310a78f7222d0178f86ebcb80015','�������'+gbgltitle,'/radowAction.do?method=doEvent&pageModel=pages.mntpsj.MntpSJGBTP')
	//$h.openPageModeWin('MNTPGBTP','pages.fxyp.gbtp.MNTPGBTP','�������',1000,650,'',g_contextpath);
}
function addTab5(){
	//parent.addTab1('3e2081e47288909a017288daf4b90003','ģ�����','/radowAction.do?method=doEvent&pageModel=pages.fxyp.Mntp')
	parent.addTab1('8ab66e277801431e0178014c519f0003','��λ����'+gbgltitle,'/radowAction.do?method=doEvent&pageModel=pages.mntpsj.MntpSJ')
}
function addTab1(){
	parent.addTab1('3e2081e47288909a017288daf4b90003','ģ�����','/radowAction.do?method=doEvent&pageModel=pages.fxyp.Mntp')
}
function addTab2(){
	parent.addTab1('3e2081e4728392a5017283cd01ef000e','���鷽��','/radowAction.do?method=doEvent&pageModel=pages.yntp.YNTPGL')
}
var g_contextpath = '<%= request.getContextPath() %>';
function openRXFXYP(){
	$h.openPageModeWin('rxfxyp','pages.fxyp.RXFXYP','�ɲ�������ѡ�������б�',1150,800,
			{scroll:"scroll:yes;"},g_contextpath);
}


function openNewWindow(url,name){
	var fulls = "left=0,screenX=0,top=0,screenY=0,scrollbars=1";    //���嵯�����ڵĲ���
	if (window.screen) {
	   var ah = screen.availHeight - 40;
	   var aw = screen.availWidth - 10;
	   fulls += ",height=" + ah;
	   fulls += ",innerHeight=" + ah;
	   fulls += ",width=" + aw;
	   fulls += ",innerWidth=" + aw;
	   fulls += ",resizable"
	} else {
	   fulls += ",resizable"; // ���ڲ�֧��screen���Ե�������������ֹ�������󻯡� manually
	}
	window.open(url,name,fulls);
}
function openNewWindow2(url,name){
	var fulls = "screenX=0,top=0,screenY=0,scrollbars=1";    //���嵯�����ڵĲ���
	if (window.screen) {
	   var ah = screen.availHeight - 35;
	   var aw = screen.availWidth;
	   fulls += ",height=" + ah;
	   fulls += ",innerHeight=" + ah;
	   fulls += ",width=" + 1160;
	   fulls += ",innerWidth=" + aw;
	   fulls += ",left=" + (aw-1160)/2;
	   //fulls += ",resizable"
	} else {
	   fulls += ",resizable"; // ���ڲ�֧��screen���Ե�������������ֹ�������󻯡� manually
	}
	window.open(url,name,fulls);
}
</script>
</body>
</html>



