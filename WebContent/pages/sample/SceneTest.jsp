<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>


		<odin:window src="" title="�����޸�" modal="true" width="510" height="310" id="modifySceneWindow"></odin:window>
		<odin:window src="" title="��������" modal="true" width="510" height="310" id="addSceneWindow"></odin:window>
		<odin:toolBar property="btnToolBar">
			<odin:textForToolBar text="<h3>��ѯ��</h3>"></odin:textForToolBar>
			<odin:fill/>
			<odin:buttonForToolBar text="��ѯ" icon="" cls="" ></odin:buttonForToolBar>
			<odin:buttonForToolBar isLast="true" text="���" icon="" cls=""></odin:buttonForToolBar>
		</odin:toolBar>
		<div style="margin-left: 15px;" id="sceneQueryContent">
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
		<div style="margin-left:2px;">
			<odin:panel contentEl="sceneQueryContent" property="sceneInfoQueryPanel" topBarId="btnToolBar" width="800"></odin:panel>
		</div>
		<odin:toolBar property="gridToolBar">
			<odin:textForToolBar text="&nbsp;���г����б�"></odin:textForToolBar>
			<odin:fill></odin:fill>
			<odin:buttonForToolBar text="����" cls="x-bin-text-icon" icon=""></odin:buttonForToolBar>
			<odin:buttonForToolBar text="�޸ĳ���" cls="" icon=""></odin:buttonForToolBar>
			<odin:buttonForToolBar text="��������" cls="" icon=""></odin:buttonForToolBar>
			<odin:buttonForToolBar text="ע������" cls="" icon="" isLast="true"></odin:buttonForToolBar>
		</odin:toolBar>
		<odin:gridWithPagingTool autoFill="false" forceNoScroll="false" url="" isFirstLoadData="false"
			property="grid5" pageSize="12" title="" width="800" height="340" topToolBar="gridToolBar">
		</odin:gridWithPagingTool>
		<odin:gridWithPagingTool property="two">
			<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
				<odin:gridDataCol name="id"></odin:gridDataCol><!--����id  -->
				<odin:gridDataCol name="name"></odin:gridDataCol><!--�������� -->
				<odin:gridDataCol name="description"></odin:gridDataCol><!--��������  -->
				<odin:gridDataCol name="owner"></odin:gridDataCol><!--�����ĳ�����  -->
				<odin:gridDataCol name="status" isLast="true"></odin:gridDataCol><!-- ����״̬��־λ -->
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn/>
				<odin:gridColumn width="150" dataIndex="name"></odin:gridColumn>
				<odin:gridColumn width="322" dataIndex="description"></odin:gridColumn>
				<odin:gridColumn width="150" dataIndex="owner"></odin:gridColumn>
				<odin:gridColumn width="150" dataIndex="status"></odin:gridColumn>
			</odin:gridColumnModel>
		</odin:gridWithPagingTool>


