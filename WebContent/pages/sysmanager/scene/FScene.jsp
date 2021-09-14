<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>


<odin:toolBar property="btnToolBar">
    <odin:textForToolBar text="<h3>查询区</h3>"  />
	<odin:fill/>
	<odin:buttonForToolBar text="查询" id="querySceneBtn" isLast="true" />
</odin:toolBar>
<div id="sceneQueryContent">
	<table  id="myform" width="790">
		<tr>
		<td colspan="6" height="10"></td>
		</tr>	 
		<tr>
			<odin:textEdit property="name" label="场景名称"/>
			<odin:textEdit property="description" label="场景描述"/>
		</tr>
		<tr>
		<td colspan="6" height="10"></td>
		</tr>
	</table>
</div>
<div>
<odin:panel contentEl="sceneQueryContent" property="panel_content" topBarId="btnToolBar" width="800"></odin:panel>
</div>
<odin:toolBar property="gridToolBar">
	<odin:textForToolBar  text="现有场景列表"/>
	<odin:fill/>
	<odin:buttonForToolBar text="新增"  id="addSceneBtn"/>
	<odin:buttonForToolBar text="修改场景"  id="editSceneBtn"/>
  	<odin:buttonForToolBar text="还原场景"  id="reuseSceneBtn" icon="images/right1.gif"cls="x-btn-text-icon"/>
  	<odin:buttonForToolBar text="注销场景" id="revokeSceneBtn" icon="images/wrong.gif"  cls="x-btn-text-icon"/>
  	<odin:buttonForToolBar text="删除场景"  id="deleteSceneBtn" icon="images/qinkong.gif"
  	 cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<odin:editgrid property="scenegrid"  bbarId="pageToolBar"  isFirstLoadData="false" height="340" width="800"
					     url="/" pageSize="12" title="" topBarId="gridToolBar">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="sceneid" /> <!-- 场景id -->
  <odin:gridDataCol name="checked" /> <!-- 场景id -->
  <odin:gridDataCol name="name" /> <!-- 场景名称 -->
  <odin:gridDataCol name="description"/>  <!-- 场景描述  -->
  <odin:gridDataCol name="owner"/>  <!-- 场景创建者 -->
  <odin:gridDataCol name="status" isLast="true"/>   <!-- 场景状态 -->
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn></odin:gridRowNumColumn>
  <odin:gridColumn 	header="选择"  gridName="scenegrid" edited="true" align="center" width="50" editor="checkbox" dataIndex="checked" />
  <odin:gridColumn  header="场景名称" width="150" align="center" dataIndex="name" />
  <odin:gridColumn  header="场景描述" width="400" align="center"  dataIndex="description" />
  <odin:gridColumn  header="场景创建者" width="150" dataIndex="owner" hidden="true"/>
  <odin:gridColumn  header="场景状态" width="150" align="center" dataIndex="status" edited="false" renderer="radow.commUserfulForGrid" isLast="true"/>
</odin:gridColumnModel>		
</odin:editgrid>

<odin:window src="/blank.htm" id="addSceneWin" width="285" height="195" title="增加场景窗口"/>
<odin:window src="/blank.htm" id="updateSceneWin" width="285" height="195" title="修改场景窗口"/>
