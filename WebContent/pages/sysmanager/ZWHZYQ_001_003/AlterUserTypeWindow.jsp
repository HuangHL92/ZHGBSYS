<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.lbs.cp.util.SysManagerUtil"%>
<%@page import="com.insigma.odin.framework.privilege.PrivilegeManager"%>
<%@ page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003.MechanismComWindowPageModel"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
    <script type="text/javascript" src="basejs/ColumnNodeUI.js"></script>
    <script type="text/javascript" src="basejs/ext/ux/TreeCheckNodeUI.js"></script>
<%
	String ereaid = (String) (new MechanismComWindowPageModel().areaInfo.get("areaid"));

%>
<%@include file="/comOpenWinInit.jsp" %>
<link rel="stylesheet" type="text/css" href="basejs/ext/ux/css/column-tree.css" />
<style type="text/css">
fieldset {overflow: auto ;}
legend {font:bold 12px tahoma, arial, helvetica, sans-serif;color:#15428b}
#savebtn{font:bold 12px tahoma, arial, helvetica, sans-serif;color:#15428b}
.save {background: url('<%=request.getContextPath()%>/images/save.gif') left  no-repeat !important;}
#btnCancel{position: absolute;top:130px;left:115px;}
</style>
	<odin:toolBar property="btnToolBar" applyTo="toolBar">	
		<odin:textForToolBar text="当前操作用户:<span id=\"username\" ></span>"></odin:textForToolBar>	
		<odin:fill />
		<odin:buttonForToolBar text="保存" icon="./images/save.gif" id="savebut" tooltip="保存" isLast="true" handler="dogant"/>
	</odin:toolBar>
	<odin:hidden property="changeNode"/>
	<odin:hidden property="ryIds"/>


<div id="baseInfo" style="height:100px">
<div id="toolbar"></div>
<fieldset style="height: 100%">
	<table>
		<tr>
			<odin:select2 property="usertype" label="&nbsp;用户类型" codeType="UT24" editor="select"></odin:select2>			 
		</tr>
	</table>
</fieldset>
</div>
<%-- <div id='btnCancel'>
<odin:button text="关&nbsp;&nbsp;闭" handler="Cancel"></odin:button>
</div> --%>
<odin:hidden property="userid"/>
<script type="text/javascript">
function dogant(){
	radow.doEvent('savebut'); //机构，人员，模块
}
function Cancel(){
	radow.doEvent('Cancel');
}
</script>
