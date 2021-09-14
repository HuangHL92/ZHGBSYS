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
	<odin:toolBar property="btnToolBar" >	
		<odin:textForToolBar text="当前操作用户:<span id=\"username\" ></span>"></odin:textForToolBar>	
		<odin:fill />
		<odin:buttonForToolBar text="保存" icon="./images/save.gif" id="savebut" tooltip="保存" isLast="true" handler="dogant"/>
	</odin:toolBar>
	<odin:hidden property="changeNode"/>
	<odin:hidden property="ryIds"/>


<div id="baseInfo" >
<fieldset style="height: 100%">
	<table>
		<tr>
			<odin:select2 property="usertype" label="&nbsp;用户类型" codeType="UT24" filter="code_value not in('1','3','4')" editor="select"></odin:select2>			 
		</tr>
		<tr>
			<odin:select2 property="sectype" label="&nbsp;用户密级" data="['011','非密'],['01','内部'],['0','秘密']" editor="select"></odin:select2>			 
		</tr>
	</table>
</fieldset>
</div>
<odin:panel contentEl="baseInfo" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>
<odin:hidden property="userid"/>
<script type="text/javascript">
function dogant(){
	radow.doEvent('savebut'); //机构，人员，模块
}
function Cancel(){
	radow.doEvent('Cancel');
}
</script>
