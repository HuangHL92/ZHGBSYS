<%@page import="com.insigma.siis.local.business.comm.VerWindowBS"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.insigma.odin.framework.util.SysUtil" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3" %>
<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%@page import="com.insigma.odin.framework.safe.SafeControlCenter"%>
<%@page import="com.insigma.odin.framework.safe.util.SafeConst"%>
<%@page import="com.insigma.siis.local.epsoft.util.LogUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.odin.framework.db.DBUtil"%>
<%@page import="com.insigma.siis.local.lrmx.ExpRar"%>
<%
	String token = (String)request.getSession().getAttribute("token");
	if(token == null){
		token =	UUID.randomUUID().toString();
		request.getSession().setAttribute("token",token);
	}
	LogonInfoServlet.doLogonAuth(token);
	SafeControlCenter.getInstance(SafeConst.PDT_INSIIS).safeValidate(request,SafeConst.VT_APPCONTEXT,SafeConst.PDT_INSIIS_COMP_ODIN);
	SafeControlCenter.getInstance().safeValidate(SafeConst.VT_LOGINCOUNT,SafeConst.PDT_INSIIS_COMP_ODIN);
	String data = CodeType2js.getCodeTypeJS(request);
	String downloadfile=ExpRar.getExeclPath()+"\\汇总版1.0用户操作手册.doc";
	downloadfile= downloadfile.replace("\\", "/");
%>
<%@page import="com.insigma.siis.local.business.helperUtil.CodeType2js"%>
<%@page import="com.insigma.odin.framework.sys.auth.DologonHzbAction"%>
<%@page import="java.util.UUID"%>
<%@page import="com.insigma.odin.framework.logonInfo.LogonInfoServlet"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>全国公务员管理信息系统</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<link href="main/css/style.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src='js/dropdown.js'></script>
<link rel="stylesheet" type="text/css" href="css/css.css">
<ul id="dropmenu3" class="dropMenu">
    <li><a target="_blank" href="#" onclick="downLoadbook();return false;" style="color: #385e8d"><img src="images/help.png" width="14" height="14" />&nbsp;系统帮助</a></li>
    <li><a href="#" onclick="openChangePasswordWin3();return false;"  style="color: #385e8d" target="_blank"><img src="images/user.png" width="14" height="14" />&nbsp;修改密码</a></li>
    <li><a href="#" onclick="logout();return false;" target="_blank" style="color: #385e8d" ><img src="images/exit.png" width="14" height="14" />&nbsp;退出系统</a></li>
</ul>
<script type="text/javascript">
	var g_contextpath='<%=request.getContextPath()%>';
	var g_username='<%=SysUtil.getCacheCurrentUser().getName()%>';
	var dbType='<%=DBUtil.getDBType() == DBUtil.DBType.MYSQL?"MySQL":DBUtil.getDBType() == DBUtil.DBType.ORACLE?"Oracle":""%>';
	var currentLoginName = '<%=SysUtil.getCacheCurrentUser().getLoginname()%>';
	var currentUserid = '<%=SysUtil.getCacheCurrentUser().getId()%>';
	function text(){
		<%
		List list = new ArrayList();
		new LogUtil().createLog("11", "","","", "", list);
		%>
	}
	function logout(){
	  if (confirm('确定要退出系统吗?')){
	  	//http://ip:端口 /项目名称/UserLoginAction.do?method=LoginOut
	  	odin.ext.Ajax.request({
			url : '<%=GlobalNames.sysConfig.get("ZZBHRLOGOUT")%>',
			params : {},
			success : function(response){
			}
		});	
	  	window.top.location.href=g_contextpath+'/mainLogOff.jsp';//'/logoffAction.do';
	  } 
	    
	    //var name = encodeURI(encodeURI(currentLoginName));
		//var id = encodeURI(encodeURI(currentUserid));
		
	}
	function openChangePasswordWin(){
		var win = window.open('<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.sysmanager.user.PsWindow','修改密码','status=no,toolbar=no,height=180,width=450');
		win.focus();		
	}
	function openChangePasswordWin2(){
		var rtn = window.showModalDialog('<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.sysmanager.user.PsWindow',null,'help:no;status:no;dialogWidth:280px;dialogHeight:160px');
	}
	function openChangePasswordWin3(){
		radow.util.openWindow('pswin','pages.sysmanager.user.PsWindow');
	}
	function openGetVersionWin(){
		radow.util.openWindow('verwin','pages.comm.VerWindow');
	}
	function downLoadbook(){
		var downfile = '<%=downloadfile %>';
		w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile)));
		setTimeout(cc,3000);
	}
	function cc(){
		w.close();
	}
</script>
<odin:head/>
<script type="text/javascript" src="basejs/dateutil.js"></script>
<script type="text/javascript" src="basejs/index1.js"></script> 
<script type="text/javascript" src="radow/corejs/radow.util.js"></script>
<link href="css/index.css" rel="stylesheet" type="text/css" id="indexCss"/>
</head>
<body onload="text();">
<odin:base></odin:base>
<odin:window src="/blank.htm" id="pswin"  width="385" height="240" title="成员信息修改窗口"></odin:window>
<odin:window src="/blank.htm" id="win3" width="830" height="575"></odin:window>
<odin:window src="/blank.htm" id="win_rep" width="830" height="675"></odin:window>
<div id="header" style="display: none;">
<div id="header_top" >
  <div style="background-color: #1d8bd8;width:100%; height:67px; background:#1d8bd8; margin:0 auto;" id="topmenu"  >
  <table>
  <tr>
  <td><img src="image/xt_01.png" width="566" height="67" /> </td>
  <td width="90%" align="right" ><a id='AA' rel="dropmenu3" target="_blank" style="color: white;font-size: 13" >欢迎您，<%=SysUtil.getCacheCurrentUser().getName()%></a>
  <img src="image/iconfont-sanjiaoxing01.png" id="AB"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/><div id="name" style="font-size: 13px;color: rgb(255,251,240);width: 200px; text-align:center;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;版本：<%=VerWindowBS.getVersionName()%></div></td>
  </tr>
  </table>
  </div>
</div>	 	
</div>

<div class="centerbody" id="centerbody" style="width:100%; height:auto; margin:0 auto; background:#ecf5fa;">
<div style="width:100%; height:auto; margin:0 auto; background-image:url(images/bg-01.png); background-position:bottom; background-repeat:no-repeat;">
<div style="padding-top:120px; padding-bottom:220px;">
<table align="center" width="84%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><div align="center"><a href="javascript:void(0)" onClick="doTransHZB()"><img src="images/xz_09.png" width="220" height="205" /></a></div></td>
    <td>&nbsp;</td>
    <td><div align="center"><a href="javascript:void(0)" onClick="doTransSX()"><img src="images/xz_11.png" width="220" height="205" /></a></div></td>
    <!-- <td>&nbsp;</td>
    <td><div align="center"><a href="javascript:void(0)" onClick="doTransXTGL()"><img src="images/xz_13.png" width="220" height="205" /></a></div></td> -->
  </tr>
</table></div></div></div>

<!--头部菜单　header_nav-->
<div id="header_nav">

</div>
<div id="header_bady"></div>
<div id="bizarea"></div>
<div class="leftall" id="leftall" style="display: none;">
  <dl class="leftmenu" id="leftmenu" style="overflow:score;overFlow-x: hidden;">
  </dl>
</div>

<jsp:include page="/commform/commpages/common/commform.jsp"/>
<input type="hidden" id="token" value="<%=token%>" />
</body>
 <sicp3:errors/>
 <script type="text/javascript">
 var OpenWindow;
function doTransHZB(){
	
	odin.ext.Ajax.request({
		url : g_contextpath+'/dologonHzbAction.do?method=doTransCheck',
		params : {'system':'1'},
		success : function(response){
			if(!odin.ext.decode(response.responseText).data.boo){
				window.open(g_contextpath+'/dologonHzbAction.do?method=doTransHzb','_blank','width='+screen.width+',height='+screen.height+',top=0,left=0,toolbar=yes,menubar=yes,scrollbars=yes,resizable=yes,location=yes,status=yes,fullscreen=yes');
			} else {
				if(!odin.ext.decode(response.responseText).data.logon){
					window.top.location.href=g_contextpath+'/mainLogOff.jsp';//'/logoffAction.do';
				} else {
					alert("你不具备此系统权限！");
				}
				
			}
		},
		failure : function() {  
	          window.top.location.href=g_contextpath+'/mainLogOff.jsp';//'/logoffAction.do';
	     } 
	});	
}
function doTransXTGL(){
	
	odin.ext.Ajax.request({
		url : g_contextpath+'/dologonHzbAction.do?method=doTransCheck',
		params : {'system':'3'},
		success : function(response){
			if(!odin.ext.decode(response.responseText).data.boo){
				window.open(g_contextpath+'/dologonHzbAction.do?method=doTransXTGL','_blank','width='+screen.width+',height='+screen.height+',top=0,left=0,toolbar=yes,menubar=yes,scrollbars=yes,resizable=yes,location=yes,status=yes,fullscreen=yes');
			} else {
				if(!odin.ext.decode(response.responseText).data.logon){
					window.top.location.href=g_contextpath+'/mainLogOff.jsp';//'/logoffAction.do';
				} else {
					alert("你不具备此系统权限！");
				}
			}
		}
	});	
}
function doTransSX(){//统计系统。
	

	odin.ext.Ajax.request({
		url : g_contextpath+'/dologonHzbAction.do?method=doTransCheck',
		params : {'system':'2'},
		success : function(response){
			if(!odin.ext.decode(response.responseText).data.boo){
				var token = document.getElementById("token").value;
				OpenWindow = window.open('<%=GlobalNames.sysConfig.get("ZZBHRLOGIN")%>?params={\'username\':\'\',\'password\':\'\',\'scene\':\'\',\'token\':\''+token+'\'}','_blank','width='+screen.width+',height='+screen.height+',top=0,left=0,toolbar=yes,menubar=yes,scrollbars=yes,resizable=yes,location=yes,status=yes,fullscreen=yes');
				///OpenWindow = window.open('<%=GlobalNames.sysConfig.get("ZZBHRLOGIN")%>?token='+token,'_blank','width='+screen.width+',height='+screen.height+',top=0,left=0,toolbar=yes,menubar=yes,scrollbars=yes,resizable=yes,location=yes,status=yes,fullscreen=yes');
				
				/**
				*根据token获取用户名
				*/
				/*
				$.ajax( {  
				     url:'http://localhost:8088/hzb/Logon/Info',//校验的链接。
				     data:{
				     	token:document.getElementById("token").value
				     },  
				     type:'post',  
				     cache:false,  
				     dataType:'json',  
				     success:function(data) {  
				     	if(data.isLogon){
				     		alert(data.username);
					        
				     	}else{
				     		alert("跳转失败,用户可能未登录！");  
				     	}
				     	
				     },  
				     error : function() {  
				          alert("跳转失败,用户可能未登录！");  
				     }  
				});
				*/
				
				
			} else {
				if(!odin.ext.decode(response.responseText).data.logon){
					window.top.location.href=g_contextpath+'/mainLogOff.jsp';//'/logoffAction.do';
				} else {
					alert("你不具备此系统权限！");
				}
			}
		}
	});	
}
 	Ext.onReady(function(){	
	   var isDefault = "<%=GlobalNames.SYS_PLANAREA_CLASS%>";
	   odin.Ajax.request(contextPath+'/com/insigma/siis/local/comm/planarea/OverallAreaAction.do?method=selectOverallArea',null,succfun,failfun,false,false);
	   changeView();
	});
 	var rs;
var openWindowXX = function(id,title,url,width,height){
	    var winx = Ext.getCmp(id);
	    if(winx){
	    	winx.close();
	    	return;
	    }
	    winx = new Ext.Window({
	        html: "<iframe style=\"background:white;border:none;\" width=\"100%\" height=\"100%\" src=\"" + url + "\"></iframe>",
	        id:id,
	        title:title,
	        layout:'fit',
	        width:width,
	        height:height,
	        closeAction:'close',
	        collapsible:false,
	        plain: true,
	        modal : true,
	        resizable: false
	    });
	    winx.show();
	    winx.setWidth(width);
	    winx.setHeight(height);
	} 	
 	function succfun(response){
 		//构建一个数组
 		rs=new Array(response.data.length);
  		for(i=0;i<response.data.length;i++){
    		rs[i]= new Ext.data.Record(response.data[i]);
    	}
 		var num=response.data.length;
 		if(num>1){
 			openWindow(response);
 		}else{
 			//g_areaName=rs[0].data.name;
 			//document.getElementById('areaName').innerText = g_areaName;
 		}
 	}
 	function failfun(rm){
 		alert("操作失败");
 	}
 	function openWindow(response){
 		odin.showWindowWithSrc('win3','<%=request.getContextPath()%>/pages/comm/planarea/Control.jsp');
 	}
$(function(){	
var html='';
<%=data%>;
var data = CodeTypeTree.tree;
var data = data.children;
for(var i=0;i<data.length;i++){
	if(data[i].text=='公务员登记'){
			html += '<dd style="width:135%">'+
				'<div class="title"> <span><img src="'+data[i].icon+'" /></span>'+data[i].text+'</div>';
			if(!data[i].leaf){
				addChildren(data[i].children);
			}
			html += '</dd>';
	}else if(data[i].text=='机构信息'||data[i].text=='人员信息'||data[i].text=='信息查询'){
			html += '<dd style="width:135%;background:url(./main/images/images/01_10.png) no-repeat;line-height:45px;font-weight:bold;font-size:14px;">'+
				'<div class="title"> <a style="color:#4f759e;" href="javascript:void(0)" onclick="addTab1(&quot;'+data[i].id+'&quot;,&quot;'+data[i].text+'&quot;,&quot;'+data[i].location+'&quot;)"><span><img src="'+data[i].icon+'" /></span>'+data[i].text+' </a></div>';
			html += '</dd>';
	}else {
			html += '<dd style="width:135%">'+
				'<div class="title"> <span><img src="'+data[i].icon+'" /></span>'+data[i].text+'</div>';
			if(!data[i].leaf){
				addChildren(data[i].children);
			}
			html += '</dd>';
	}
	
}
document.getElementById("leftmenu").innerHTML= html;
function addChildren(data){
	html += '<ul class="menuson">';
	for(var j=0;j<data.length;j++){
		if(!data[j].leaf){
			html += '<li>';
			html +=	'<a href="javascript:void(0)" ><cite class="liplus"></cite>'+data[j].text+'</a>';
			addChildren(data[j].children);
			html +=	'</li>';
		}else{	
			if('/radowAction.do?method=doEvent&pageModel=pages.sysmanager.dbInit.DbInit'==data[j].location){
				html += '<li class="leafli">';
				html +=	'<a class="leafa leafspan" href="javascript:void(0)" onclick="openWindowXX(&quot;'+data[j].id+'&quot;,&quot;'+data[j].text+'&quot;,&quot;'+contextPath+data[j].location+'&quot;,350,150)" >'
					+' <img class="leafimg" src="./main/image/leaf.gif" />'+data[j].text+'</a>'+
				'</li>';
			}else{
				html += '<li class="leafli">';
				html +=	'<a class="leafa leafspan" href="javascript:void(0)" onclick="addTab1(&quot;'+data[j].id+'&quot;,&quot;'+data[j].text+'&quot;,&quot;'+data[j].location+'&quot;)" >'
					+' <img class="leafimg" src="./main/image/leaf.gif" />'+data[j].text+'</a>'+
				'</li>';
			}
		}

	}
	html +=	 '</ul>';	
}


var os =function(){  
	document.getElementById('leftmenu').style.height = document.body.clientHeight-100;
}
    
document.body.onresize= os;   
os();

	$('.title').click(function(event){
		event.stopPropagation();		
		var $ul = $(this).next('ul');

		if($ul.is(':visible')){
			$(this).next('ul').slideUp();
		}else{
			$(this).next('ul').slideDown();
		}
	});
	 
	$('li').click(function(event){
		event.stopPropagation();
		var obj = event.srcElement || event.target; 
		//alert(obj.nodeName);
		if($(this).is(':has(ul)')&&(obj.nodeName=='A'||obj.nodeName=='CITE')){
			if($(this).children('ul').is(':visible')){
				$(this).children('ul').slideUp();
				$(this).children('a').children('cite').addClass('liplus');
				$(this).children('a').children('cite').removeClass('liminus');
			}else{
				$(this).children('ul').slideDown();
				$(this).children('a').children('cite').addClass('liminus');
				$(this).children('a').children('cite').removeClass('liplus');
			}
		
		}
	
	});
	

});
	function addTab1(id,text,location){  
    	addTab(text,id,g_contextpath+location,'','')
    }
    
	window.onload=myfun;
	function myfun(){
	    var menuitems = document.getElementById("AA") ;
		menuitems.onmouseover=function(e){
		var event=typeof e!="undefined"? e : window.event
			cssdropdown.dropit(this,event,this.getAttribute("rel"))
		}
	}
 </script>

</html>
