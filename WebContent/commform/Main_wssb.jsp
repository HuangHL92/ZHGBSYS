<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.lbs.leaf.cp.util.SysUtil" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>������������걨ϵͳ</title>
<odin:head/>
<script type="text/javascript">
	var g_contextpath='<%=request.getContextPath()%>';
	var g_username='<%=SysUtil.getCacheCurrentUser().getUser().getOperatorname()%>';
</script>
<script type="text/javascript" src="commform/basejs/dateutil.js"></script>
<script type="text/javascript" src="commform/index_wssb.js"></script>
<link href="commform/css/index_wssb.css" rel="stylesheet" type="text/css" />
</head>
<body>

<odin:toolBar property="btnToolBar" applyTo="topToolBarDiv">
	<odin:textForToolBar text=""></odin:textForToolBar>
    <odin:buttonForToolBar text="��ǰ�û���"  icon="commform/images/currentuser.gif" cls="x-btn-text-icon"/>
    <odin:textForToolBar text="<%=SysUtil.getCacheCurrentUser().getUser().getOperatorname()%>"></odin:textForToolBar>
	<odin:fill/>
	<odin:buttonForToolBar text="�鿴��˽��" handler="doLookUpResult"  icon="commform/images/icon/icon(1).gif" cls="x-btn-text-icon"/>
	<odin:buttonForToolBar text="�޸�����" handler="doChangePWD"  icon="commform/images/modify.gif" cls="x-btn-text-icon"/>
	<odin:buttonForToolBar text="����"  icon="commform/img/icon/help.gif" cls="x-btn-text-icon"/>
	<odin:buttonForToolBar isLast="true" text="�˳�" handler="doLogOut" icon="commform/images/exit.gif" cls="x-btn-text-icon"/>
</odin:toolBar>
<div id="topToolBarDiv"></div>


<div>
 <table width="100%" height="511" cellspacing="0" cellpadding="0" border="0">
 <tr>
 	<td width="197" valign="top">
 	<!-- menu -->
 	<iframe frameborder="0" scrolling="no" src="LeftMenu.jsp" width="100%" height="506"></iframe>
 	<!-- end menu -->
    </td>
 	<td valign="top">
 	   <div id="rContDiv"></div>
 	</td>
 </tr>  
 </table>
</div>

</body>
</html>
