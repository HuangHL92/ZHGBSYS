<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page import="com.insigma.odin.framework.util.commform.SysConst"%>
<%@page import="com.insigma.odin.framework.commform.local.sys.LoginManager"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��ᱣ�������걨ϵͳ<%=SysConst.getServerNumber()%></title>
<odin:head/>
<link href="css/index_wssb.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="index_wssb.js"></script>
<script type="text/javascript" src="basejs/flash.js"></script>
</head>
<body style="text-align:center;overflow-y:hidden;overflow-x:hidden" >
<!--headerͷ�� style="overflow-y:hidden;overflow-x:hidden"-->
<!--headerͷ��-->
<div id="header"> 
  <script type="text/javascript" language="javascript"> flash('img/topflash.swf','1003','65')</script>
</div>
<!--nav_һ��������-->
<div id="nav">
<div id="nav_bady">

<div id="nav_nav">
<table width="281" height="33" border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <td width="91"  id="li_welcome" class="hover">&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="white" onclick="doShowDiv(this)"><img src="images/welcome_2.gif" width="13" height="14" border="0"> ��ӭ</a> </td>
    <td width="4" ><img src="images/index3_03.gif" width="4" height="32"></td>
    <td width="91" id="li_draft"  >&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="white" onclick="doShowDiv(this)"><img src="images/zgx.gif" width="15" height="16" border="0">�ҵ��ĵ�</a></td>
	<td width="4"><img src="images/index3_03.gif" width="4" height="32"></td>
    <td width="91" id="li_content" >&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="white" onclick="doShowDiv(this)"><img src="images/yewu.gif" width="15" height="16" border="0">ҵ�����</a></td>
 	<td width="4"><img src="images/index3_03.gif" width="4" height="32"></td>
  </tr>
</table>
</div>
<!--  li id="li_welcome" class="hot"><a href="#" onclick="doShowDiv(this)" >&nbsp;&nbsp;&nbsp;&nbsp;��ӭ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
<li id="li_draft"><a href="#" onclick="doShowDiv(this)">&nbsp;&nbsp;&nbsp;&nbsp;�ݸ���&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
<li id="li_content"><a href="#" onclick="doShowDiv(this)">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ҵ�����&nbsp;&nbsp;</a></li>
-->
<div id="nav_user">
<ul>
<li id="helpLink"><a id="helpHref" href="qj.doc" TARGET="_blank"><img src="img/help_icq.gif" border="0"> ����</a></li>
<li><a href="#" onclick="doLogOut()"><img src="img/back.gif" border="0"> �˳�</a></li>
<li><a href="#" onclick="doChangePWD()"><img src="img/password.gif" border="0"> �޸�����</a></li>
<li>��ӭ����<b><span id='userChName'></span></b></li>
</ul>
</div>
</div>
</div>

<!-- ��ӭ����--start -->
<div id="welcome">
	<iframe id="iframe_welcome" frameborder="0" scrolling="no" src="Welcome.jsp" width="1003" height="474" align="left" onload="doOnload(this)"></iframe>
</div>
<!-- ��ӭ����--end -->

<!-- �ݸ���--start -->
<div id="draft" style="display:none">
	<iframe id="iframe_draft" frameborder="0" scrolling="no" src="blank.htm" width="1003" height="474" align="left" onload="doOnload(this)"></iframe>
</div>
<!-- �ݸ���--end -->

<!--content_�м䲿������-->
<div id="content" style="display:none">
<!-- left menu -->
<iframe id="iframe_leftmenu" frameborder="0" scrolling="no" src="LeftMenu.jsp" width="210" height="474" align="left"></iframe>
<!-- end menu -->

<div id="content_right">
<div id="content_right_tab"></div>
</div>

<!--content_���ù����� end of-->

</div>


<input type="hidden" name="userid" value="" />
<input type="hidden" name="currentUsername" value="" />
<input type="hidden" name="currentRoleFlag" value="" />
<input type="hidden" name="currentAab301" value="" />
<input type="hidden" name="currentAaa027" value="" />
<input type="hidden" name="systype" value="" />
<% 
String rate=LoginManager.getCurrentRate();
%>
<script>
odin.ext.onReady(function(){
	var doc='qj.doc';
	if('<%=rate%>'=='5'){
		doc='jd.doc'
	}
	document.getElementById('helpHref').href = doc;
	//��ʱ���زݸ��䣬Ϊ�˼ӿ컶ӭ����ļ����ٶ�(�޸�Ϊ�ڵ�һ�ΰ�ť�����)
	//window.setTimeout('document.getElementById("iframe_draft").src = "draft.jsp"',200);
}); 
</script>
<iframe scrolling="no" width="0" height="0" src="commform/commpages/common/cueUserInfo.jsp"></iframe>

<odin:window src="blank.htm" id="pupWindow" title="" width="400" height="200"></odin:window>
<odin:window src="/blank.htm" id="win_billprint" title="" width="280" height="30"></odin:window>

</body>
</html>
