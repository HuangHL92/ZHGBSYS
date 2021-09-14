<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>


		<odin:toolBar property="btnToolBar">
			<odin:textForToolBar text="<h3>查询区</h3>"/>
			<odin:fill/>
			<odin:buttonForToolBar text="查询" id="toolBarBtn1" />
			<odin:buttonForToolBar isLast="true" id="toolBarBtn2"text="清空"/>
		</odin:toolBar>

		<div id="sceneQueryContent">
			<table width="790">
				<tr>
					<td>
						<odin:textEdit property="sceneName" label="场景名称"></odin:textEdit>
					</td>
					<td>
						<odin:textEdit property="sceneDesc" label="场景描述"></odin:textEdit>
					</td>
				</tr>
			</table>
		</div>
		<div>
			<odin:panel contentEl="sceneQueryContent" property="sceneInfoQueryPanel" topBarId="btnToolBar" 
			width="800"></odin:panel>
		</div>
		<odin:toolBar property="gridToolBar">
			<odin:textForToolBar text="&nbsp;现有场景列表"></odin:textForToolBar>
			<odin:fill></odin:fill>
			<odin:buttonForToolBar text="新增" id="addScene"/>
			<odin:buttonForToolBar text="修改场景" id="updateSccene" />
			<odin:buttonForToolBar text="场景重用" id="reuseScene"/>
			<odin:buttonForToolBar text="注销场景" isLast="true" id="revokeScene"/>
		</odin:toolBar>
	
		<odin:gridWithPagingTool isFirstLoadData="true" url="/"
			property="grid5" pageSize="12" title="场景管理" width="800" height="340" topToolBar="gridToolBar">
			<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
				<odin:gridDataCol name="sceneid"/>
				<odin:gridDataCol name="name"/><!--场景名称 -->
				<odin:gridDataCol name="description"/><!--场景描述  -->
				<odin:gridDataCol name="owner"/><!--场景的持有者  -->
				<odin:gridDataCol name="status" isLast="true"/><!-- 场景状态标志位 -->
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn/>
				<odin:gridColumn header="场景ID" hidden="false" dataIndex="sceneid" editor="checkbox" id="id"/>
				<odin:gridColumn header="场景名称" width="150" dataIndex="name"/>
				<odin:gridColumn header="场景描述" width="322" dataIndex="description"/>
				<odin:gridColumn header="场景的持有者" width="150" dataIndex="owner"/>
				<odin:gridColumn header="场景状态标志位" width="150" dataIndex="status" isLast="true"/>
			</odin:gridColumnModel>
		</odin:gridWithPagingTool>

	<odin:window id="saveScene" src="pages.sysmanager.scene.AddScene.jsp"/>	
	<odin:window id="updateScene" src="pages.sysmanager.scene.UpdateScene.jsp"/>

