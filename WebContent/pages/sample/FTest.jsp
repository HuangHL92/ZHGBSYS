<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<div id="panel_content">
<table border="0" id="myform" width="100%"  cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="6" height="10"></td>
	</tr>
   <tr>
	    <odin:textEdit property="loginname" label="��¼��" /> 
		<odin:textEdit property="username" label="�û���"/>
		<odin:textEdit property="opname" label="������Ա"/>
   </tr>
   <tr>
		<td colspan="6" height="10"></td>
	</tr>	 
 </table>
</div>
<odin:toolBar property="toolBar1">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="�޸�����" id="toolBarBtn3"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="�����û�" id="toolBarBtn2"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="��ѯ" id="toolBarBtn1" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>

<odin:panel  property="mypanel" width="800" height="80" topBarId="toolBar1" title=""
	 collapsible="true" contentEl="panel_content"/>

<odin:editgrid property="usergrid" bbarId="pageToolBar" isFirstLoadData="true" 
	url="/radowAction.do?method=doEvent&pageModel=pages.sample.FTest&eventNames=usergrid.dogridquery"
	 title="�ɼ����û�" width="795" height="400" pageSize="15">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount" >
  <odin:gridDataCol name="userid" />
  <odin:gridDataCol name="username"/>
  <odin:gridDataCol name="loginname"/>
  <odin:gridDataCol name="useful"/>
  <odin:gridDataCol name="owner"/>
  <odin:gridDataCol name="description" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <odin:gridEditColumn header="�û�id" width="45" dataIndex="userid" editor="text" hidden="true"/>
  <odin:gridEditColumn header="������" width="45" dataIndex="owner" editor="text"/>
  <odin:gridEditColumn header="��¼��" width="35" dataIndex="loginname" editor="text"/>
  <odin:gridEditColumn header="�û���" width="35" dataIndex="username" editor="text"/>
  <odin:gridEditColumn header="״̬" renderer="radow.commUserfulForGrid"  width="20" dataIndex="useful" editor="text"/>
  <odin:gridEditColumn  header="��Դ����" dataIndex="description" editor="text" width="30"/>
  <odin:gridEditColumn  header="����" renderer="radow.commGridColDelete"  dataIndex="userid" width="30" edited="false" editor="text" isLast="true"/>
</odin:gridColumnModel>		
</odin:editgrid>	

<odin:window src="/blank.htm" id="win1" width="500" height="350" title="���ڲ���">
</odin:window>
<odin:window src="/blank.htm" id="win2" width="500" height="350" title="���ڲ���">
</odin:window>
