<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

	<table>
		<tr>
			<td height="10"></td>
		</tr>
	   <tr>
	   		<td width="30"></td>
		     <odin:textEdit property="groupname" label="��֯��"/> 
		     <td width="60"></td>
		     <td><odin:button property="searchBtn" text="��ѯ"/></td>
	   </tr>
	   <tr>
			<td height="5"></td>
		</tr> 
		<tr>
		<odin:hidden property="selectGroupname" />
	</tr>
	 </table>
<odin:editgrid property="groupgrid" bbarId="pageToolBar" isFirstLoadData="false" url="/"
				pageSize="10" title="�û����б�" width="470" height="310">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="id" />
  <odin:gridDataCol name="name" />
  <odin:gridDataCol name="desc"/>
  <odin:gridDataCol name="parentid"/>
  <odin:gridDataCol name="shortname" isLast="true"/>
  
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <odin:gridColumn  header="�û�����" dataIndex="name"/>
  <odin:gridColumn header="����" dataIndex="desc"/>
  <odin:gridColumn header="���" dataIndex="shortname"/>
  <odin:gridColumn header="������" dataIndex="parentid"/>
  <odin:gridColumn  header="�鿴������Ϣ" renderer="radow.commGrantForGrid" align="center" dataIndex="id" isLast="true"/>
</odin:gridColumnModel>		
</odin:editgrid>