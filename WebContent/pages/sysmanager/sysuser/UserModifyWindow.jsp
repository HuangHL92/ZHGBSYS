<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.lbs.cp.util.SysManagerUtil"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<odin:groupBox title="�û�������Ϣ" width="300">
	<table border="0" id="myform" align="center" width="100%"
		cellpadding="0" cellspacing="0">
		<tr>
			<odin:textEdit property="loginname" label="�û���¼��" maxlength="21"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="username" label="����"></odin:textEdit>
		</tr>
		<tr>
			<tags:PublicTextIconEdit3 property="ssjg" label="������Χ" readonly="true" codetype="orgTreeJsonData" required="true"></tags:PublicTextIconEdit3>
		</tr>
		<tr>
			<odin:textEdit property="work" label="&nbsp;������λ"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="mobile" label="&nbsp;�ֻ�"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="tel" label="&nbsp;�칫�绰"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="email" label="&nbsp;�ʼ�"></odin:textEdit>
		</tr>
		<tr>
			<odin:select property="useful" label="&nbsp;�û�״̬"></odin:select>
		</tr>
		<tr>
			<odin:select property="usertype" label="&nbsp;�û�����" data="['1','ϵͳ����Ա'],['3','��ȫ����Ա'],['4','��ȫ���Ա'],['2','��ͨ�û�']" readonly="true" disabled="true"></odin:select>
		</tr>
		<tr>
			<td align="right" colspan="6">
				<odin:button text="����"	property="savebut"></odin:button>
			</td>
		</tr>
	</table>
</odin:groupBox>
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
			<odin:gridEditColumn header="��ɫid" width="30" dataIndex="roleid"
						edited="false" editor="text" hidden="true" />
			<odin:gridEditColumn editor="text" dataIndex="dispatchauth"
						edited="false" header="�Ƿ�ɷ���Ȩ��" align="center" renderer="radow.commUserfulForGrid"/>
			<odin:gridEditColumn header="��ɫ����" align="center" width="20" 
						dataIndex="rolename" editor="text" edited="false" />
			<odin:gridEditColumn header="��ɫ����" dataIndex="roledesc" align="center" 
						edited="false" editor="text" width="40" />
			<odin:gridEditColumn header="״̬" align="center" editor="text"
						edited="false" renderer="radow.commUserfulForGrid" width="10"
						dataIndex="status" isLast="true" />
		</odin:gridColumnModel>
	</odin:editgrid>
