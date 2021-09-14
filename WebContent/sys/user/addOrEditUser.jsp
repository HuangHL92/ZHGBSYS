<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<% 
  response.setHeader("Pragma","No-cache");
  response.setHeader("Cache-Control","no-cache");
  response.setDateHeader("Expires", 0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�û�����</title>
<odin:head/>
<odin:MDParam/>
<script src="<%=request.getContextPath()%>/js/md5.jsp"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/user/addOrEditUser.js"></script>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form action="<%=request.getContextPath()%>/sys/userAction.do?method=addOrEditUser" method="post" name="userForm">
<table align="center" width="96%">
	<tr>
		<td height="6" colspan="4"></td>
	</tr>
	<tr>
		<odin:textEdit property="username" alt="���ڵ�¼ʱ������" label="�û���¼��"  onblur="validateNameOnly(this.value)" required="true"></odin:textEdit>
		<odin:textEdit property="operatorname" alt="��ʵ����" label="�û�����"></odin:textEdit>
	</tr>
	<tr>
		<odin:textEdit property="passwd" inputType="password" alt="�û�����" label="�û�����"></odin:textEdit>
		<odin:select property="useful" data="['1','��Ч'],['0','��Ч']" label="�Ƿ���Ч" value="1"></odin:select>
	</tr>
	<tr>
		<odin:textEdit property="dept" alt="" label="��������"></odin:textEdit>
		<odin:textEdit property="description" label="����"></odin:textEdit>
	</tr>
</table>
<table align="center" width="92%">
	<tr>
		<td>
        <odin:groupBox title="������ɫ">
			<odin:grid property="roleGrid" sm="checkbox" width="420" height="100" url="/common/pageQueryAction.do?method=query" isFirstLoadData="false">
				<odin:gridJsonDataModel  id="roleid" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="roleid" />
				    <odin:gridDataCol name="rolename" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridSmColumn></odin:gridSmColumn>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn dataIndex="rolename" header="��ɫ����" isLast="true"></odin:gridColumn>
				</odin:gridColumnModel>
			</odin:grid>	
		</odin:groupBox>
		<odin:groupBox title="���ݽ�ɫ">
			<odin:grid property="dataRoleGrid" sm="checkbox" width="420" height="100" url="/common/pageQueryAction.do?method=query" isFirstLoadData="false">
				<odin:gridJsonDataModel  id="roleid" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="roleid" />
				    <odin:gridDataCol name="rolename" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridSmColumn></odin:gridSmColumn>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn dataIndex="rolename" header="���ݽ�ɫ����" isLast="true"></odin:gridColumn>
				</odin:gridColumnModel>
			</odin:grid>	
		</odin:groupBox>
		</td>
	</tr>
	<tr>
		<td height="2"></td>
	</tr>
	<tr>
		<td align="right">
			<img src="../../images/baocun.gif" onclick="addUser()">&nbsp;&nbsp;
			<img src="../../images/qingkong.gif" onclick="clearForm(document.userForm);">
		</td>
	</tr>
</table>			
	<input type="hidden" name="userid">
	<input type="hidden" name="sysid">
	<input type="hidden" name="roleids">
	<input type="hidden" name="dataroleids">
</form>

<script>   
Ext.onReady(function(){
	document.userForm.sysid.value = parent.sysId;
	loadRoleData();
	loadDataRoleData();//�������ݽ�ɫ����
	doInitWin();
});
</script>

</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>