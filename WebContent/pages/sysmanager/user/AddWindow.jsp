<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:groupBox title="�û�������Ϣ" width="300">
	<table border="0" id="myform" align="center" width="100%"
		cellpadding="0" cellspacing="0">
		<tr>
			<td><font color="red">*</font></td>
			<odin:textEdit property="loginname" label="�û���¼��"></odin:textEdit>
			<odin:textEdit property="description" label="&nbsp����"></odin:textEdit>
		</tr>
		<!-- 
		<tr>
			<odin:textEdit property="password" inputType="password"
				required="true" label="����"></odin:textEdit>
		</tr>
		 -->
		<tr>
			<td><font color="red">*</font></td>
			<odin:textEdit property="username" label="����"></odin:textEdit>
		</tr>
		<tr>
			<td align="right" colspan="6"><odin:button text="����"
				property="savebut"></odin:button>
			</td>
		</tr>
	</table>
</odin:groupBox>
<odin:gridSelectColJs name="isCan" codeType="YESNO" ></odin:gridSelectColJs>
<odin:editgrid property="rolegrid" isFirstLoadData="false" url="/" title="��Ȩ��Ϣ" width="447" height="238">
	<odin:gridJsonDataModel id="gridid" root="data"
		totalProperty="totalCount">
		<odin:gridDataCol name="rolecheck" />
		<odin:gridDataCol name="id"/>
		<odin:gridDataCol name="name" />
		<odin:gridDataCol name="status" />
		<odin:gridDataCol  name="isCan"/>
		<odin:gridDataCol name="desc" isLast="true" />
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn header="selectall" gridName="rolegrid" align="center" width="7"
			editor="checkbox" edited="true" dataIndex="rolecheck" />
		<odin:gridEditColumn header="��ɫid" dataIndex="id"
			edited="false" editor="text" hidden="true" />
		<odin:gridEditColumn2 editor="select" edited="true" header="�Ƿ�ɷ���Ȩ��"
		 	dataIndex="isCan" renderer="radow.commUserfulForGrid"
		 	codeType="YESNO" width="100" align="center" />
		<odin:gridEditColumn header="��ɫ��" align="center" width="80" 
			dataIndex="name" editor="text" edited="false" />
		<odin:gridEditColumn header="��Դ����" dataIndex="desc" align="center" 
			edited="false" editor="text" width="110" />
		<odin:gridEditColumn header="״̬" align="center" editor="text"
			edited="false" renderer="radow.commUserfulForGrid" width="50"
			dataIndex="status" isLast="true" />
	</odin:gridColumnModel>
</odin:editgrid>
