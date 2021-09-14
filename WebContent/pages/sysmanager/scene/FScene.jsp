<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>


<odin:toolBar property="btnToolBar">
    <odin:textForToolBar text="<h3>��ѯ��</h3>"  />
	<odin:fill/>
	<odin:buttonForToolBar text="��ѯ" id="querySceneBtn" isLast="true" />
</odin:toolBar>
<div id="sceneQueryContent">
	<table  id="myform" width="790">
		<tr>
		<td colspan="6" height="10"></td>
		</tr>	 
		<tr>
			<odin:textEdit property="name" label="��������"/>
			<odin:textEdit property="description" label="��������"/>
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
	<odin:textForToolBar  text="���г����б�"/>
	<odin:fill/>
	<odin:buttonForToolBar text="����"  id="addSceneBtn"/>
	<odin:buttonForToolBar text="�޸ĳ���"  id="editSceneBtn"/>
  	<odin:buttonForToolBar text="��ԭ����"  id="reuseSceneBtn" icon="images/right1.gif"cls="x-btn-text-icon"/>
  	<odin:buttonForToolBar text="ע������" id="revokeSceneBtn" icon="images/wrong.gif"  cls="x-btn-text-icon"/>
  	<odin:buttonForToolBar text="ɾ������"  id="deleteSceneBtn" icon="images/qinkong.gif"
  	 cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<odin:editgrid property="scenegrid"  bbarId="pageToolBar"  isFirstLoadData="false" height="340" width="800"
					     url="/" pageSize="12" title="" topBarId="gridToolBar">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="sceneid" /> <!-- ����id -->
  <odin:gridDataCol name="checked" /> <!-- ����id -->
  <odin:gridDataCol name="name" /> <!-- �������� -->
  <odin:gridDataCol name="description"/>  <!-- ��������  -->
  <odin:gridDataCol name="owner"/>  <!-- ���������� -->
  <odin:gridDataCol name="status" isLast="true"/>   <!-- ����״̬ -->
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn></odin:gridRowNumColumn>
  <odin:gridColumn 	header="ѡ��"  gridName="scenegrid" edited="true" align="center" width="50" editor="checkbox" dataIndex="checked" />
  <odin:gridColumn  header="��������" width="150" align="center" dataIndex="name" />
  <odin:gridColumn  header="��������" width="400" align="center"  dataIndex="description" />
  <odin:gridColumn  header="����������" width="150" dataIndex="owner" hidden="true"/>
  <odin:gridColumn  header="����״̬" width="150" align="center" dataIndex="status" edited="false" renderer="radow.commUserfulForGrid" isLast="true"/>
</odin:gridColumnModel>		
</odin:editgrid>

<odin:window src="/blank.htm" id="addSceneWin" width="285" height="195" title="���ӳ�������"/>
<odin:window src="/blank.htm" id="updateSceneWin" width="285" height="195" title="�޸ĳ�������"/>
