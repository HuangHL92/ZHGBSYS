<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<table align="center" width="96%">
	<tr>
		<td height="6" colspan="4"></td>
	</tr>
	<tr>
		<odin:textEdit property="userloginname" label="�û���¼��"></odin:textEdit>		
		<td><odin:button property="queryBtn" text="��ѯ"></odin:button></td>
	</tr>
	<tr>
	</tr>
</table>
<table align="center" width="92%">
	<tr>
		<td>
		<odin:groupBox title="��ѡ�û�" >
			<odin:editgrid property="userGrid" autoFill="false" width="400" height="285" bbarId="pageToolBar" pageSize="10" isFirstLoadData="false" url="/">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="logchecked"/>
					<odin:gridDataCol name="id" />
					<odin:gridDataCol name="loginname" />
					<odin:gridDataCol name="name" />
					<odin:gridDataCol name="desc" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridColumn header="" width="25" editor="checkbox" dataIndex="logchecked" edited="true"/>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn dataIndex="loginname" header="�û���¼��" width="110" align="center"/>
					<odin:gridColumn dataIndex="name" header="����" width="110" align="center"/>
					<odin:gridColumn dataIndex="desc" header="����"  width="120" align="center" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
			<div  align="right">
				<table>
				  <tr>
					<td><odin:button property="addUserBtn" text="�����û�����"></odin:button>
					</td>
				  </tr>
				</table>
			</div>
		</odin:groupBox>
		</td>
	</tr>
	
	
</table>			

