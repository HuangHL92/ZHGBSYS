<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<div id="panel_content">
<table border="0" id="myform" width="100%"  cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="6" height="10"></td>
	</tr>
   <tr>
	    <odin:textEdit property="loginname" label="登录名" /> 
		<odin:textEdit property="name" label="姓名"/>
   </tr>
   <tr>
		<td colspan="6" height="10"></td>
	</tr>	 
 </table>
</div>
<odin:toolBar property="toolBar1">
	<odin:textForToolBar text="<h3>用户查询</h3>"  />
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="清除" id="clearBtn" icon="images/sx.gif"  cls="x-btn-text-icon" ></odin:buttonForToolBar>
	<odin:buttonForToolBar text="查询" id="toolBarBtn1" icon="images/search.gif"  cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>

<odin:toolBar property="opration">
	<odin:textForToolBar  text="现有用户列表"/>
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="新增用户" id="toolBarBtn2" icon="images/add.gif"  cls="x-btn-text-icon"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="修改用户" id="updateBnt"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="注销用户" id="revokeUserBnt"  icon="images/wrong.gif"  cls="x-btn-text-icon"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="删除"  id="deleteBnt" isLast="true" icon="images/qinkong.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
</odin:toolBar>

<odin:window src="/blank.htm" id="createUserWin" width="470" height="400" title="创建成员用户窗口"></odin:window>

<odin:panel property="mypanel" width="790" height="80" topBarId="toolBar1" title=""
	 collapsible="true" contentEl="panel_content"/>
	 
<odin:editgrid property="usergrid" bbarId="pageToolBar"  isFirstLoadData="false" 
			   url="/" topBarId="opration" title="" width="788" height="410" 
			   pageSize="14">
<odin:gridJsonDataModel  id="gridid" root="data" totalProperty="totalCount" >
  <odin:gridDataCol name="check1"/>
  <odin:gridDataCol name="id"/>
  <odin:gridDataCol name="name"/>
  <odin:gridDataCol name="loginname"/>
  <odin:gridDataCol name="status" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn></odin:gridRowNumColumn>
  <odin:gridColumn 		header="selectall"  gridName="usergrid"  align="center" width="10" editor="checkbox"  edited="true" dataIndex="check1"/>
  <odin:gridEditColumn 	header="用户id" width="30" dataIndex="id" edited="false" editor="text"  hidden="true"/>
  <odin:gridEditColumn 	header="登录名" align="center" width="40" dataIndex="loginname" editor="text" edited="false"/>
  <odin:gridEditColumn 	header="姓名" align="center" edited="false" width="45" dataIndex="name" editor="text"/>
  <odin:gridEditColumn 	hidden="true" header="持有者" align="center" edited="false" width="55" dataIndex="ownerId" editor="text"/>
  <odin:gridEditColumn 	header="状态" align="center" editor="text" edited="false" renderer="radow.commUserfulForGrid" width="20" isLast="true" dataIndex="status" />
</odin:gridColumnModel>		
</odin:editgrid>	

<odin:window src="/blank.htm" id="win1" width="280" height="215" title="信息修改窗口">
</odin:window>
<odin:window src="/blank.htm" id="win2" width="280" height="200" title="密码修改窗口">
</odin:window>
<odin:window src="/blank.htm" id="win3" width="280" height="215" title="增加用户窗口">
</odin:window>
