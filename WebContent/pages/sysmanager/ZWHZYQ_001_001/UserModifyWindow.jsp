<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.lbs.cp.util.SysManagerUtil"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<odin:groupBox title="用户基本信息" width="300">
	<table border="0" id="myform" align="center" width="100%"
		cellpadding="0" cellspacing="0">
		<tr>
			<odin:textEdit property="loginname" label="用户登录名"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="username" label="姓名"></odin:textEdit>
		</tr>
		<tr>
			<tags:PublicTextIconEdit3 property="ssjg" label="所属范围" readonly="true" codetype="orgTreeJsonData" required="true"></tags:PublicTextIconEdit3>
		</tr>
		<tr>
			<odin:textEdit property="work" label="&nbsp;工作单位"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="mobile" label="&nbsp;手机"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="tel" label="&nbsp;办公电话"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="email" label="&nbsp;邮件"></odin:textEdit>
		</tr>		
		<tr>
			<odin:select2 property="useful" label="&nbsp;用户状态"></odin:select2>
		</tr>
		<tr>
			<odin:select2 property="isleader"  data="['1','是'],['0','否']" label="&nbsp;是否为管理员"></odin:select2></tr>
		<tr>
			<td align="right" colspan="6">
				<odin:button text="保存"	property="savebut"></odin:button>
			</td>
		</tr>
	</table>
</odin:groupBox>
<div style="display: none;">
<odin:editgrid property="rolegrid" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="" topBarId="btnToolBar" width="515" height="344" title="" pageSize="10">
	<odin:gridJsonDataModel id="gridid" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="roleid"/>
		<odin:gridDataCol name="rolename" />
		<odin:gridDataCol name="status" />
		<odin:gridDataCol  name="dispatchauth"/>
		<odin:gridDataCol name="roledesc" isLast="true" />
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridEditColumn header="角色id" width="30" dataIndex="roleid"
					edited="false" editor="text" hidden="true" />
		<odin:gridEditColumn editor="text" dataIndex="dispatchauth"
					edited="false" header="是否可分受权限" align="center" renderer="radow.commUserfulForGrid"/>
		<odin:gridEditColumn header="角色名称" align="center" width="20" 
					dataIndex="rolename" editor="text" edited="false" />
		<odin:gridEditColumn header="角色描述" dataIndex="roledesc" align="center" 
					edited="false" editor="text" width="40" />
		<odin:gridEditColumn header="状态" align="center" editor="text"
					edited="false" renderer="radow.commUserfulForGrid" width="10"
					dataIndex="status" isLast="true" />
	</odin:gridColumnModel>
</odin:editgrid>
</div>