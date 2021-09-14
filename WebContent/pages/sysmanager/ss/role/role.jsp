<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>��ɫ��ѯ</h3>"/>
	<odin:fill/>
	<odin:buttonForToolBar id="clean"  text="����"  icon="images/sx.gif" cls="x-btn-text-icon"/>
	<odin:buttonForToolBar id="btn_query" isLast="true" text="��ѯ"  icon="images/search.gif" cls="x-btn-text-icon"/>
</odin:toolBar>

<div id="roleQueryContent">
	<table width="100%">
		<tr>
			<td height="16"  colspan="4"></td>
		</tr>
		<tr>
	    	<odin:textEdit property="roleQName" label="��ɫ����"></odin:textEdit>
	    	<odin:select property="roleOwner" label="��ɫ������" codeType="USER" ></odin:select>
	    	<odin:textEdit property="roleQDesc" label="��ɫ����"></odin:textEdit>
		</tr>
		<tr>
			<td height="16" colspan="4"></td>
		</tr>
	</table>		
</div>
<odin:panel contentEl="roleQueryContent" property="roleQueryPanel" topBarId="btnToolBar"></odin:panel>

<odin:toolBar property="pageTopBar">
	<odin:fill/>
	<odin:buttonForToolBar text="����" id="addRole" isLast="true" />
	
</odin:toolBar>
<odin:gridSelectColJs name="status" codeType="USEFUL"></odin:gridSelectColJs>
<odin:gridSelectColJs name="owner" codeType="USER1"></odin:gridSelectColJs>
<odin:editgrid property="grid6" topBarId="pageTopBar" bbarId="pageToolBar" url="/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ss.role.role&eventNames=grid6.dogridquery" title="��ɫ��Ϣ��" width="778" height="430">
<odin:gridJsonDataModel  id="roleid" root="data" totalProperty="totalCount">
	<odin:gridDataCol name="checked" />
  <odin:gridDataCol name="id" />
  <odin:gridDataCol name="desc" />
  <odin:gridDataCol name="owner" />
  <odin:gridDataCol name="oldrolename" />
  <odin:gridDataCol name="status" />
  <odin:gridDataCol name="name" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn ></odin:gridRowNumColumn>
 <odin:gridColumn  header="selectall"  gridName="grid6" dataIndex="checked" editor="checkbox" edited="true" />
  <odin:gridEditColumn header="��ɫid" hidden="true" dataIndex="id" editor="text" />
  <odin:gridEditColumn hidden="true" dataIndex="oldrolename" editor="text" />
  <odin:gridEditColumn header="��ɫ����" align="center" width="100" dataIndex="name" selectOnFocus="true" editor="text" edited="false"  />
  <odin:gridEditColumn header="��ɫ����" width="130" dataIndex="desc" editor="text" selectOnFocus="true" />
  <odin:gridEditColumn header="������"  width="80" dataIndex="owner" edited="false" editor="select" codeType="USER1"  />
  <odin:gridEditColumn header="״̬"  align="center" width="80" dataIndex="status" editor="select"  renderer="radow.commUserfulForGrid" codeType="USEFUL" />
  <odin:gridEditColumn  align="center" width="60" header="��Ȩ" dataIndex="id"  editor="text" edited="false" renderer="radow.commGrantForGrid"  />
  <odin:gridEditColumn  align="center" width="100" header="ɾ��" dataIndex="id" editor="text" edited="false" renderer="radow.commGridColDelete" isLast="true" />
 
</odin:gridColumnModel>			
</odin:editgrid>	


<odin:window src="/" modal="true" id="roleWindow" width="500" height="350"></odin:window>
<odin:window src="/" modal="true" id="grantWindow" width="320" height="480"></odin:window>

