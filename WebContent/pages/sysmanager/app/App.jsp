<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>Ӧ�ò�ѯ</h3>"/>
	<odin:fill/>
	<odin:buttonForToolBar id="btn_query"  text="��ѯ"  icon="images/search.gif" cls="x-btn-text-icon"/>
	<odin:buttonForToolBar id="btn_create"  text="����" icon="images/add.gif" cls="x-btn-text-icon"/>
	<odin:buttonForToolBar id="clean"  text="ˢ��"  icon="images/sx.gif" isLast="true" cls="x-btn-text-icon"/>
</odin:toolBar>

<div id="QueryContent">
	<table width="100%">
		<tr>
			<td height="16"  colspan="4"></td>
		</tr>
		<tr>
	    	<odin:textEdit property="appName"  label="Ӧ������"/>
	    	<odin:textEdit property="otherMess"  label="�ؼ���Ϣ"/>
		</tr>
		<tr>
			<td height="16" colspan="4"></td>
		</tr>
	</table>		
</div>
<odin:panel contentEl="QueryContent" property="QueryPanel" topBarId="btnToolBar"></odin:panel>
<odin:gridSelectColJs name="parent" codeType="PROXY"/>
<odin:editgrid property="appgrid" title="Ӧ�ù���" autoFill="false" width="771" height="394" topBarId="" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  id="appid" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="appid" />
					<odin:gridDataCol name="appName"/>
					<odin:gridDataCol name="appCode"/>
					<odin:gridDataCol name="appTitle" />
					<odin:gridDataCol name="appType"/>
					<odin:gridDataCol name="parent" />
					<odin:gridDataCol name="reqrandom"/>
					<odin:gridDataCol name="appDesc"  isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn dataIndex="appName" header="Ӧ������" align="center" width="80" />
					<odin:gridColumn dataIndex="appCode" header="Ӧ�ñ���" align="center" width="80"/>
					<odin:gridColumn dataIndex="appTitle" header="Ӧ�ñ�ǩ" align="center" width="80"/>
					<odin:gridColumn dataIndex="appType" header="Ӧ�����" align="center" width="80"/>
					<odin:gridColumn dataIndex="parent" header="��Ӧ�û��߽���Ӧ��" align="center" width="125" editor="select" codeType="PROXY"/>
					<odin:gridColumn dataIndex="reqrandom" header="����ʱ�������" align="center" width="115"/>
					<odin:gridColumn dataIndex="appDesc" header="����" align="center" width="100"/>
					<odin:gridColumn dataIndex="appid" header="�޸�" renderer="radow.commGrantForGrid" align="center" width="40"/>
					<odin:gridColumn dataIndex="appid" header="ɾ��" renderer="radow.commGridColDelete" align="center" width="40" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>

<odin:window src="/blank.htm" id="operateWin" width="520" height="200" title="Ӧ�ò�������"></odin:window>