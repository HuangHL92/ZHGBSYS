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


<div id="baseInfo" >
<div id="toolbar"></div>
<fieldset style="height: 80%">
<legend>用户基本信息</legend>
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
           <%-- <odin:select2 property="kg"  label="密码有效期开关" required="true" readonly="true" value="ON" data="['ON','开'],['OFF','关']"   onchange="ff(this.value)"></odin:select2>	
			<odin:select2 property="validity" codeType="VALIDITY" value="validity01"  readonly="true" required="true"  label="密码有效期" />	 --%>		
			<odin:hidden property="kg" value="OFF"/>
			<odin:hidden property="validity" />	
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
			<odin:select2 property="dept" label="&nbsp;所属部门"></odin:select2>
		</tr>
		<%-- <tr>
			<odin:select2 property="usertype" required="true" label="用户类型" codeType="UT24" editor="select"></odin:select2>	
		</tr> --%>
		<%-- <tr>
			<odin:select2 property="useful" label="&nbsp;用户状态" required="true"></odin:select2>			 
		</tr> --%>
		<tr>
			<td></td>
<%-- 			<td align="center">
				<odin:button text="保存" property="savebut"></odin:button>
			</td> --%>
		</tr>
	</table>
</fieldset>
</div>
<odin:panel contentEl="baseInfo" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>
<script type="text/javascript">
function ff(v){
	if(v=="开"){	
		radow.doEvent("qy");	
	}else if(v=="关"){	
		radow.doEvent("jy");		
	}
	
}
</script>
