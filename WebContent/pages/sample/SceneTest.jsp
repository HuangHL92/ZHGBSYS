<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>


		<odin:window src="" title="场景修改" modal="true" width="510" height="310" id="modifySceneWindow"></odin:window>
		<odin:window src="" title="新增场景" modal="true" width="510" height="310" id="addSceneWindow"></odin:window>
		<odin:toolBar property="btnToolBar">
			<odin:textForToolBar text="<h3>查询区</h3>"></odin:textForToolBar>
			<odin:fill/>
			<odin:buttonForToolBar text="查询" icon="" cls="" ></odin:buttonForToolBar>
			<odin:buttonForToolBar isLast="true" text="清空" icon="" cls=""></odin:buttonForToolBar>
		</odin:toolBar>
		<div style="margin-left: 15px;" id="sceneQueryContent">
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
		<div style="margin-left:2px;">
			<odin:panel contentEl="sceneQueryContent" property="sceneInfoQueryPanel" topBarId="btnToolBar" width="800"></odin:panel>
		</div>
		<odin:toolBar property="gridToolBar">
			<odin:textForToolBar text="&nbsp;现有场景列表"></odin:textForToolBar>
			<odin:fill></odin:fill>
			<odin:buttonForToolBar text="新增" cls="x-bin-text-icon" icon=""></odin:buttonForToolBar>
			<odin:buttonForToolBar text="修改场景" cls="" icon=""></odin:buttonForToolBar>
			<odin:buttonForToolBar text="场景重用" cls="" icon=""></odin:buttonForToolBar>
			<odin:buttonForToolBar text="注销场景" cls="" icon="" isLast="true"></odin:buttonForToolBar>
		</odin:toolBar>
		<odin:gridWithPagingTool autoFill="false" forceNoScroll="false" url="" isFirstLoadData="false"
			property="grid5" pageSize="12" title="" width="800" height="340" topToolBar="gridToolBar">
		</odin:gridWithPagingTool>
		<odin:gridWithPagingTool property="two">
			<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
				<odin:gridDataCol name="id"></odin:gridDataCol><!--场景id  -->
				<odin:gridDataCol name="name"></odin:gridDataCol><!--场景名称 -->
				<odin:gridDataCol name="description"></odin:gridDataCol><!--场景描述  -->
				<odin:gridDataCol name="owner"></odin:gridDataCol><!--场景的持有者  -->
				<odin:gridDataCol name="status" isLast="true"></odin:gridDataCol><!-- 场景状态标志位 -->
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn/>
				<odin:gridColumn width="150" dataIndex="name"></odin:gridColumn>
				<odin:gridColumn width="322" dataIndex="description"></odin:gridColumn>
				<odin:gridColumn width="150" dataIndex="owner"></odin:gridColumn>
				<odin:gridColumn width="150" dataIndex="status"></odin:gridColumn>
			</odin:gridColumnModel>
		</odin:gridWithPagingTool>


