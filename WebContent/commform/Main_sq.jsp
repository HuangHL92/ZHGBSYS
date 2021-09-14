<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.lbs.leaf.cp.util.SysUtil" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3" %>
<%@page import="com.insigma.odin.framework.commform.local.sys.CalendarManager"%>
<%@page import="com.insigma.odin.framework.commform.local.sys.LoginManager"%>
<%@page import="com.insigma.odin.framework.util.commform.SysConst"%>
<%@page import="com.insigma.odin.framework.util.commform.ObjectUtil"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<script type="text/javascript" src="basejs/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="basejs/ext/ext-all.js"></script>
<% String rate=LoginManager.getCurrentRate();%>
<title>全国公务员管理信息系统</title>
<script type="text/javascript">
	var helpwin = null;
	var g_contextpath='<%=request.getContextPath()%>';
	var g_username='<%=SysUtil.getCacheCurrentUser().getUser().getOperatorname()%>';
	function logout(){
	  odin.confirm('确定要退出系统吗?',
	  function(btn){
		  if(btn=='ok'){
		  	window.top.location.href=g_contextpath+'/logoffAction.do';
		  	}
	  });
	}
	
	function openHelpWin(){
		if (helpwin){
			try{
				helpwin.focus();
				return;
			}
			catch(e){
				helpwin = null;
			}
		}
		var doc='qj.doc';
		if(<%=rate%>=='5'){
			doc='jd.doc'
		}
		helpwin = window.open(doc,'帮助',''+"height=" + (screen.availHeight-150) +
	",width=" + (screen.availWidth-100)+",top=0,left=0");
		helpwin.focus();
	}
	
	function openChangePasswordWin(){
		doOpenPupWindow('/sys/changePWAction.do','修改密码',420,220);
		//var win = window.open('/sys/changePWAction.do','修改密码','status=no,toolbar=no,height=180,width=450');
		//win.focus();
	}
	Ext.onReady(function(){
	//延时加载草稿箱，为了加快欢迎界面的加载速度(修改为在第一次按钮后加载)
		var doc='qj.doc';
		if(<%=rate%>=='5'){
			doc='jd.doc'
		}
		document.getElementById('qjjd').href = doc;
	}); 
</script>
<odin:head/>
<script type="text/javascript" src="basejs/dateutil.js"></script>
<script type="text/javascript" src="index.js"></script>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<odin:treemenu/>

</head>
<body>
<script type="text/javascript">

</script>
<div id="header">
<div id="header_top"> 
<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="<%=request.getContextPath()%>/sys/activex/swflash.cab#version=10,0,32,18" width="100%" height="34" title="浙大网新社会保险信息管理系统">
  <param name="movie" value="img/top2.swf" />
  <param name="quality" value="high" />
  <param name="scale" value="exactfit" />
</object>
</div>
<!--头部菜单　header_nav-->
<div id="header_nav">
<div id="header_logo"></div>
<div id="header_bady">
<table width="100%" height="33" border="0" align="right" cellpadding="0" cellspacing="0">
  <tr>
    <td align="right" class="td">欢迎您,<b><script type="text/javascript">document.write(g_username);</script></b>&nbsp;</td>
    <td width="10" valign="bottom"><img src="img/index/nav.gif" ></td>
	<td width="20"><img src="img/index/time.gif" width="15" height="15"></td>
	<td width="105" class="td" nowrap>
          <%=CalendarManager.getChineseDate()%>
    </td>
	<td width="40" class="td">
		<%=CalendarManager.getChineseWeekday()%>
	</td>
    <td width="10" valign="bottom"><img src="img/index/nav.gif" ></td>
    <td width="20"><a href="#"><img src="img/index/keyedit.gif" width="14" height="15" border="0"></a></td>
    <td width="55" class="td"><a href="javascript:openChangePasswordWin()">修改密码</a></td> 
    <td width="10" valign="bottom"><img src="img/index/nav.gif"></td>
    <td width="20"><a href="#"><img src="img/index/help.gif" width="14" height="15" border="0"></a></td>
    <td width="30" class="td"><a id="qjjd" href="qj.doc" TARGET="_blank">帮助</a></td> 
    <td width="10" valign="bottom"><img src="img/index/nav.gif"></td>
    <td width="20"><a href="javascript:logout()"><img src="img/index/back.gif" width="15" height="15" border="0"></a></td>
    <td width="30" class="td"><a href="javascript:logout()">退出</a></td>
  </tr>
</table>
</div>
</div>
</div>

<div id="mainmenu2">
 <table border="0" cellpadding="0" width="100%" height="100%" cellspacing="0" style="clear:both">
 	<tr>
 	<td id="mainmenu">
 	</td>
 	</tr>
 </table></div>
<div id="bizarea"></div>


<input type="hidden" id="currentUsername" value="<%=SysUtil.getCacheCurrentUser().getUser().getUsername()%>" />
<input type="hidden" id="currentAab301" value="<%=LoginManager.getCurrentAab301()%>" />
<input type="hidden" id="currentAaa027" value="<%=LoginManager.getCurrentAaa027()%>" />

<%String systype = SysConst.getServerSystype();
  if(ObjectUtil.nvl(systype,"").startsWith("wssb")){
	  systype = "sionline";
  }else{
	  systype = "insiis";
  }
%>

<input type="hidden" id="systype" value="<%=systype%>" />

<odin:window src="/blank.htm" id="pupWindow" title="" width="400" height="200"></odin:window>
<odin:window src="/blank.htm" id="win_billprint" title="" width="280" height="30"></odin:window>


</body>
 <sicp3:errors/>
</html>
