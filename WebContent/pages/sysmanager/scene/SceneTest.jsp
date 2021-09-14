<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>


		<odin:toolBar property="btnToolBar">
			<odin:textForToolBar text="<h3>��ѯ��</h3>"/>
			<odin:fill/>
			<odin:buttonForToolBar text="��ѯ" id="toolBarBtn1" />
			<odin:buttonForToolBar isLast="true" id="toolBarBtn2"text="���"/>
		</odin:toolBar>

		<div id="sceneQueryContent">
			<table width="790">
				<tr>
					<td>
						<odin:textEdit property="sceneName" label="��������"></odin:textEdit>
					</td>
					<td>
						<odin:textEdit property="sceneDesc" label="��������"></odin:textEdit>
					</td>
				</tr>
			</table>
		</div>
		<div>
			<odin:panel contentEl="sceneQueryContent" property="sceneInfoQueryPanel" topBarId="btnToolBar" 
			width="800"></odin:panel>
		</div>
		<odin:toolBar property="gridToolBar">
			<odin:textForToolBar text="&nbsp;���г����б�"></odin:textForToolBar>
			<odin:fill></odin:fill>
			<odin:buttonForToolBar text="����" id="addScene"/>
			<odin:buttonForToolBar text="�޸ĳ���" id="updateSccene" />
			<odin:buttonForToolBar text="��������" id="reuseScene"/>
			<odin:buttonForToolBar text="ע������" isLast="true" id="revokeScene"/>
		</odin:toolBar>
	
		<odin:gridWithPagingTool isFirstLoadData="true" url="/"
			property="grid5" pageSize="12" title="��������" width="800" height="340" topToolBar="gridToolBar">
			<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
				<odin:gridDataCol name="sceneid"/>
				<odin:gridDataCol name="name"/><!--�������� -->
				<odin:gridDataCol name="description"/><!--��������  -->
				<odin:gridDataCol name="owner"/><!--�����ĳ�����  -->
				<odin:gridDataCol name="status" isLast="true"/><!-- ����״̬��־λ -->
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn/>
				<odin:gridColumn header="����ID" hidden="false" dataIndex="sceneid" editor="checkbox" id="id"/>
				<odin:gridColumn header="��������" width="150" dataIndex="name"/>
				<odin:gridColumn header="��������" width="322" dataIndex="description"/>
				<odin:gridColumn header="�����ĳ�����" width="150" dataIndex="owner"/>
				<odin:gridColumn header="����״̬��־λ" width="150" dataIndex="status" isLast="true"/>
			</odin:gridColumnModel>
		</odin:gridWithPagingTool>

	<odin:window id="saveScene" src="pages.sysmanager.scene.AddScene.jsp"/>	
	<odin:window id="updateScene" src="pages.sysmanager.scene.UpdateScene.jsp"/>

