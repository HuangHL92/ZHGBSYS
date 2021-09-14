<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<div id="panel_content">
<table border="0" id="myform" width="100%"  cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="6" height="10"></td>
	</tr>
   <tr>
	    <odin:textEdit property="loginname" label="登录名" /> 
		<odin:textEdit property="username" label="用户名"/>
		<odin:textEdit property="opname" label="操作人员"/>
   </tr>
   <tr>
		<td colspan="6" height="10"></td>
	</tr>	 
 </table>
</div>
<odin:toolBar property="toolBar1">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="修改密码" id="toolBarBtn3"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="增加用户" id="toolBarBtn2"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="查询" id="toolBarBtn1" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>

<odin:panel  property="mypanel" width="800" height="80" topBarId="toolBar1" title=""
	 collapsible="true" contentEl="panel_content"/>

<odin:editgrid property="usergrid" bbarId="pageToolBar" isFirstLoadData="true" 
	url="/radowAction.do?method=doEvent&pageModel=pages.sample.FTest&eventNames=usergrid.dogridquery"
	 title="可见的用户" width="795" height="400" pageSize="15">
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
  <odin:gridEditColumn header="用户id" width="45" dataIndex="userid" editor="text" hidden="true"/>
  <odin:gridEditColumn header="持有者" width="45" dataIndex="owner" editor="text"/>
  <odin:gridEditColumn header="登录名" width="35" dataIndex="loginname" editor="text"/>
  <odin:gridEditColumn header="用户名" width="35" dataIndex="username" editor="text"/>
  <odin:gridEditColumn header="状态" renderer="radow.commUserfulForGrid"  width="20" dataIndex="useful" editor="text"/>
  <odin:gridEditColumn  header="资源描述" dataIndex="description" editor="text" width="30"/>
  <odin:gridEditColumn  header="操作" renderer="radow.commGridColDelete"  dataIndex="userid" width="30" edited="false" editor="text" isLast="true"/>
</odin:gridColumnModel>		
</odin:editgrid>	

<odin:window src="/blank.htm" id="win1" width="500" height="350" title="窗口测试">
</odin:window>
<odin:window src="/blank.htm" id="win2" width="500" height="350" title="窗口测试">
</odin:window>
