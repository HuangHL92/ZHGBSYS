<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<table>
	<tr>
		<td height="5"></td>
	</tr>
   <tr>
   		<td width="30"></td>
	     <odin:textEdit property="username" label="�û���¼��"/> 
	     <td width="60"></td>
	     <td><odin:button property="searchUserBtn" text="��ѯ"/></td>
   </tr>
   <tr>
		<td height="5"></td>
	</tr> 
	<tr>
		<odin:hidden property="selectUserid" />
		<odin:hidden property="selectUsername" />
	</tr>
 </table>
	
<odin:editgrid property="usergrid" bbarId="pageToolBar" pageSize="5" isFirstLoadData="false" url="/" title="�û��б�" width="470" height="185">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="id" />
  <odin:gridDataCol name="loginname" />
  <odin:gridDataCol name="name"/>
  <odin:gridDataCol name="desc" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <odin:gridColumn header="�û���½��" dataIndex="loginname"/>
  <odin:gridColumn header="����" dataIndex="name"/>
  <odin:gridColumn  header="����" dataIndex="desc" isLast="true"/>
</odin:gridColumnModel>		
</odin:editgrid>
	 
<odin:editgrid property="groupgrid" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="���û����ڵ���֯" width="470" height="185">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="id" />
  <odin:gridDataCol name="name" />
  <odin:gridDataCol name="desc"/>
  <odin:gridDataCol name="shortname" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <odin:gridColumn  header="�û�����" dataIndex="name"/>
  <odin:gridColumn header="����" dataIndex="desc"/>
  <odin:gridColumn header="���" dataIndex="shortname"/>
  <odin:gridColumn  header="�鿴������Ϣ" renderer="radow.commGrantForGrid" align="center" dataIndex="id" isLast="true"/>
</odin:gridColumnModel>		
</odin:editgrid>	
	 
<odin:window src="/blank.htm" id="updateUserWin" width="285" height="230" title="��Ա��Ϣ�޸Ĵ���"></odin:window>
