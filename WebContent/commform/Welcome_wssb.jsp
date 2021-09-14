<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ page import="com.lbs.leaf.cp.util.SysUtil" %>
<%@page import="com.insigma.odin.framework.commform.local.sys.LoginManager"%>
<%@page import="com.insigma.odin.framework.util.commform.SysConst"%>
<html>
<head>
<style type="text/css" media="screen">
@import url("css/welcome.css");
</style>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<odin:head/>
<link href="css/index_wssb.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="basejs/welcome.js"></script>
<script type="text/javascript" src="basejs/sionline_sys.js"></script>
<odin:MDParam/>
<% 
String username=SysUtil.getCacheCurrentUser().getUser().getUsername();
String aab301=LoginManager.getCurrentAab301();
String rate=LoginManager.getCurrentRate();
String business_xt=SysConst.getBusinessXt();
%>
<script type="text/javascript">
var username="<%=username%>";
var aab301="<%=aab301%>";
var rate="<%=rate%>";
var business_xt = "<%=business_xt%>";
</script>
</head>
<body>
<div id="content_welcomepic">
	<div id="font"><img src="<%=request.getContextPath()%>/img/ganggao.gif"> 欢迎使用网上申报系统　
		<div id="font_help">
			<div id="font_left">
			</div>
			<div id="font_right">
				<div id="font_right_pic">
				</div>
				<div id="font_right_bady">
					<div id="font_right_bady2">
					  <div id="font_right_bady3"> 
					    
					  </div>
				   </div>
			 	</div>
			</div>
			<div  id="font_right_pic2">
			</div>
		</div>
		<div id="correlation">
			<div id="c_left">
				<div id="c_left_title">公共通知<span style="margin-left:350px;" ><a style="font-size:12px;text-decoration: none;" href="#" onclick="doOpenMoreSbdnNotice();">更多</a></span>
				</div>
				<div id="c_left_content">
				</div> 
			</div> 
		
			<div id="c_right">
				<div id="c_right_tiltle">私有通知<span style="margin-left:350px;" ><a style="font-size:12px;text-decoration: none;" href="#" onclick="doOpenMoreCpSbdnNotice();">更多</a></span>
				</div>
				<div id="c_right_content">
				</div>
			</div>
		</div>
	
		<div id="func" >
			<div class="fuctions">
				<iframe id="funcFrame" scrolling="no" frameborder="0" height="100" width="955" align="middle" style="margin-top:2px;margin-left:2px;" src="homepage.jsp"></iframe>
			</div>
		</div>
	</div> 
</div>


<odin:window src="/blank.htm" id="SbdnNoticeWindow" maximizable="true" width="500" height="350"></odin:window>
<odin:window src="/blank.htm" id="moreSbdnNoticeWindow" title="所有通知" maximizable="true" width="840" height="434"></odin:window>

<script>
<odin:template name="SbdnNoticeTpl">
	'<ul>',
	'<tpl for=".">',
	'<li><a href="#" onclick="doShowSbdnNotice(\'{id}\')" title="{alltitle}">{isnewimg}{title}</a><span>{date}</span></li>',
	'</tpl>',
	'</ul>'
</odin:template>
Ext.onReady(function(){
	getSbdnNoticeContent();//获取通知数据
	getCpSbdnNoticeContent();//获取通知数据
	doWelcomeAab301Operation();//辖区不同所用的操作
	doWelcomeJssbUserOperation();//基数申报用的操作
	//doGetAllStatusCount();
});  
</script>
</body>
</html>