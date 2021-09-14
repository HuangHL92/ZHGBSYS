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
	SafeControlCenter.getInstance(SafeConst.PDT_INSIIS).safeValidate(request,SafeConst.VT_APPCONTEXT,SafeConst.PDT_INSIIS_COMP_ODIN);
	SafeControlCenter.getInstance().safeValidate(SafeConst.VT_LOGINCOUNT,SafeConst.PDT_INSIIS_COMP_ODIN);
	String data = CodeType2js.getCodeTypeJS2(request);
	String downloadfile=ExpRar.getExeclPath()+"\\汇总版1.0用户操作手册.doc";
	downloadfile= downloadfile.replace("\\", "/");
%>
<%@page import="com.insigma.siis.local.business.helperUtil.CodeType2js"%>
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
    <!-- 
    <li><a href="#" onclick="openChangePasswordWin3();return false;"  style="color: #385e8d" target="_blank"><img src="images/user.png" width="14" height="14" />&nbsp;修改密码</a></li>
     -->
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
	  	window.top.opener='';
		window.top.close();
	  } 
	    ///window.top.location.href=g_contextpath+'/mainLogOff.jsp';//'/logoffAction.do';
	    ///var name = encodeURI(encodeURI(currentLoginName));
		///var id = encodeURI(encodeURI(currentUserid));
		<%
		List list1 = new ArrayList();
		new LogUtil().createLog("12", "","","", "", list1);
		%>
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
<script type="text/javascript" src="basejs/index.js"></script>
<script type="text/javascript" src="radow/corejs/radow.util.js"></script>
<link href="css/index.css" rel="stylesheet" type="text/css" id="indexCss"/>
</head>
<body onload="text();">
<odin:base></odin:base>
<odin:window src="/blank.htm" id="pswin"  width="385" height="240" title="成员信息修改窗口"></odin:window>
<odin:window src="/blank.htm" id="win3" width="830" height="575"></odin:window>
<odin:window src="/blank.htm" id="win_rep" width="830" height="675"></odin:window>
<input type="hidden" id="chooseSystem" name="chooseSystem" value="3"/>
<div id="header" style="display: none;">
<div id="header_top" >
  <div style="background-color: #1d8bd8;width:100%; height:67px; background:#1d8bd8; margin:0 auto;" id="topmenu"  >
  <table>
  <td><img src="image/xt_01.png" width="566" height="67" /> </td>
  <td width="90%" align="right" ><a id='AA' rel="dropmenu3" target="_blank" style="color: white;font-size: 13" >欢迎您，<%=SysUtil.getCacheCurrentUser().getName()%></a>
  <img src="image/iconfont-sanjiaoxing01.png" id="AB"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/><div id="name" style="font-size: 13px;color: rgb(255,251,240);width: 200px; text-align:center;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;版本：<%=VerWindowBS.getVersionName() %></div></td>
  </table>
  </div>
</div>	 	
</div>
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
</body>
 <sicp3:errors/>
 <script type="text/javascript">
 	
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
	}else if(data[i].text=='机构信息维护'||data[i].text=='人员信息维护'||data[i].text=='信息查询'){
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
