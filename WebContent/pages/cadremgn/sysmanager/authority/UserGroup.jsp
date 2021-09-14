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
</style>


<odin:toolBar property="btnToolBar" >		
	<odin:fill />
	<odin:buttonForToolBar text="保存" icon="./images/save.gif" id="savebut" isLast="true"/>
</odin:toolBar>
	<odin:hidden property="changeNode"/>
	<odin:hidden property="ryIds"/>


<div id="baseInfo"> 

<fieldset style="height: 80% " >
<legend>部门基本信息</legend>
	<table>
		<tr align="center">
			<%-- <td>
				<odin:textEdit property="text1" label="部门名称：" maxlength="500" width="150"></odin:textEdit>
			</td> --%>
			<odin:textEdit property="text1" label="&nbsp;&nbsp;部门名称" maxlength="50" required="true"></odin:textEdit>
		</tr>
	</table>
</fieldset>
<br>



<%-- <fieldset style="height: 78%">
<legend>管理员基本信息</legend>
	<table  id="myform" >
		<tr>
			<odin:textEdit property="loginname" label="用户登录名" required="true"></odin:textEdit>
			<odin:textEdit property="username" label="姓名" required="true"></odin:textEdit>
		</tr>

		<tr>
			<odin:textEdit property="password" inputType="password" label="&nbsp;密码" required="true"></odin:textEdit>
			<odin:textEdit property="surepassword" inputType="password" label="&nbsp;确认密码" required="true"></odin:textEdit>
		</tr>

		<tr>
			<odin:textEdit property="work" label="&nbsp;工作单位"></odin:textEdit>
			<odin:textEdit property="mobile" label="&nbsp;手机"></odin:textEdit>
		</tr>

		<tr>
			<odin:textEdit property="tel" label="&nbsp;办公电话"></odin:textEdit>
			<odin:textEdit property="email" label="&nbsp;邮件"></odin:textEdit>
		</tr>
		<tr>
			<odin:select2 property="useful" label="&nbsp;用户状态" required="true"></odin:select2>			 
		</tr>
		<tr>
			<td></td>
			<td align="center">
				<odin:button text="保存" property="savebut"></odin:button>
			</td>
		</tr>
	</table>
</fieldset> --%>
</div>
<odin:panel contentEl="baseInfo" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>

<script type="text/javascript">
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var btnToolBar = Ext.getCmp('btnToolBar');
	btnToolBar.setWidth(viewSize.width);
});
</script>
