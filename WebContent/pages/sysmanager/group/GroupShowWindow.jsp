<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<table>
	<tr>
		<td height="5"></td>
	</tr>
   <tr>
   		<td width="30"></td>
	     <odin:textEdit property="username" label="用户登录名"/> 
	     <td width="60"></td>
	     <td><odin:button property="searchUserBtn" text="查询"/></td>
   </tr>
   <tr>
		<td height="5"></td>
	</tr> 
	<tr>
		<odin:hidden property="selectUserid" />
		<odin:hidden property="selectUsername" />
	</tr>
 </table>
	
<odin:editgrid property="usergrid" bbarId="pageToolBar" pageSize="5" isFirstLoadData="false" url="/" title="用户列表" width="470" height="185">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="id" />
  <odin:gridDataCol name="loginname" />
  <odin:gridDataCol name="name"/>
  <odin:gridDataCol name="desc" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <odin:gridColumn header="用户登陆名" dataIndex="loginname"/>
  <odin:gridColumn header="姓名" dataIndex="name"/>
  <odin:gridColumn  header="描述" dataIndex="desc" isLast="true"/>
</odin:gridColumnModel>		
</odin:editgrid>
	 
<odin:editgrid property="groupgrid" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="该用户所在的组织" width="470" height="185">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="id" />
  <odin:gridDataCol name="name" />
  <odin:gridDataCol name="desc"/>
  <odin:gridDataCol name="shortname" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <odin:gridColumn  header="用户组名" dataIndex="name"/>
  <odin:gridColumn header="描述" dataIndex="desc"/>
  <odin:gridColumn header="简称" dataIndex="shortname"/>
  <odin:gridColumn  header="查看该组信息" renderer="radow.commGrantForGrid" align="center" dataIndex="id" isLast="true"/>
</odin:gridColumnModel>		
</odin:editgrid>	
	 
<odin:window src="/blank.htm" id="updateUserWin" width="285" height="230" title="成员信息修改窗口"></odin:window>
