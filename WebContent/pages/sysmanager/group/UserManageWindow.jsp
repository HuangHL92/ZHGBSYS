<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<table align="center" width="96%">
	<tr>
		<td height="6" colspan="4"></td>
	</tr>
	<tr>
		<odin:textEdit property="userloginname" label="用户登录名"></odin:textEdit>		
		<td><odin:button property="queryBtn" text="查询"></odin:button></td>
	</tr>
	<tr>
	</tr>
</table>
<table align="center" width="92%">
	<tr>
		<td>
		<odin:groupBox title="可选用户" >
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
					<odin:gridColumn dataIndex="loginname" header="用户登录名" width="110" align="center"/>
					<odin:gridColumn dataIndex="name" header="姓名" width="110" align="center"/>
					<odin:gridColumn dataIndex="desc" header="描述"  width="120" align="center" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
			<div  align="right">
				<table>
				  <tr>
					<td><odin:button property="addUserBtn" text="增加用户到组"></odin:button>
					</td>
				  </tr>
				</table>
			</div>
		</odin:groupBox>
		</td>
	</tr>
	
	
</table>			

