<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
	<ss:doQueryBtn></ss:doQueryBtn>
	<ss:doSaveBtn></ss:doSaveBtn>
	<ss:resetBtn></ss:resetBtn>
</ss:toolBar>
<ss:hlistDiv id="div_1" cols="4">
	<ss:select property="aab301_a" label="Ͻ��" p="E" codeType="AAB301"></ss:select>
	<ss:textEdit property="ylcpcode_a" label="���ϵ�λ����" p="E"></ss:textEdit>
	<ss:textEdit property="iscode_a" label="���֤��" p="E" onchange="true"></ss:textEdit>
	<ss:textEdit property="psname_a" label="����" p="E"></ss:textEdit>
</ss:hlistDiv>
<odin:toolBar property="bar2">
	<odin:textForToolBar text="������������ӡ�"></odin:textForToolBar>
	<odin:fill></odin:fill>
	<ss:doClickBtn icon="images/add.gif" handlerName="zj" text="����" />
</odin:toolBar>
<odin:gridSelectColJs name="aab301" codeType="AAB301" />
<odin:gridSelectColJs name="rylb" codeType="RYLB" />
<odin:gridSelectColJs name="psstatus" codeType="AAC008" />
<odin:gridSelectColJs name="pstype" codeType="EAC070" />
<odin:gridSelectColJs name="sex" codeType="AAC004" />
<odin:editgrid property="div_2" pageSize="150" topBarId="bar2" autoFill="false"
	bbarId="pageToolBar" isFirstLoadData="false" url="/" width="780" afteredit="radow.cm.afteredit"
	height="300">
	<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="iscode" />
		<odin:gridDataCol name="psname" />
		<odin:gridDataCol name="sex" />
		<odin:gridDataCol name="csrq" />
		<odin:gridDataCol name="rewage" />
		<odin:gridDataCol name="ybjfyfs" />
		<odin:gridDataCol name="fdtxny" />
		<odin:gridDataCol name="xjyfs" />
		<odin:gridDataCol name="xjqsyf" />
		<odin:gridDataCol name="pstype" />
		<odin:gridDataCol name="psstatus" />
		<odin:gridDataCol name="xjjsyf" />
		<odin:gridDataCol name="yljfyfs" />
		<odin:gridDataCol name="rylb" />
		<odin:gridDataCol name="aab301" />
		<odin:gridDataCol name="bz" />
		<odin:gridDataCol name="ylcpcode" />
		<odin:gridDataCol name="yldwmc" />
		<odin:gridDataCol name="prseno" isLast="true" />
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn />
		<ss:gridEditColumn editor="text" dataIndex="iscode" header="���֤��"
			p="D" width="150"></ss:gridEditColumn>
		<ss:gridEditColumn editor="text" dataIndex="psname" header="����" p="E"
			width="80"></ss:gridEditColumn>
		<ss:gridEditColumn editor="select" dataIndex="sex" header="�Ա�" p="E"
			width="80" codeType="AAC004"></ss:gridEditColumn>
		<ss:gridEditColumn editor="date" dataIndex="csrq" header="��������" p="E"
			width="150"></ss:gridEditColumn>
		<ss:gridEditColumn editor="number" dataIndex="rewage" header="�ɷѻ���"
			p="E" width="120"></ss:gridEditColumn>
		<ss:gridEditColumn editor="number" dataIndex="ybjfyfs"
			header="ҽ���ɷ��·���" p="E" width="100"></ss:gridEditColumn>
		<ss:gridEditColumn editor="number" dataIndex="fdtxny" header="������������"
			p="E" width="100"></ss:gridEditColumn>
		<ss:gridEditColumn editor="number" dataIndex="xjyfs" header="Э���·���"
			p="E" width="80"></ss:gridEditColumn>
		<ss:gridEditColumn editor="number" dataIndex="xjqsyf" header="Э����ʼ����"
			p="E" width="80"></ss:gridEditColumn>
		<ss:gridEditColumn editor="select" dataIndex="pstype" header="������ְ���"
			p="E" width="100" codeType="EAC070"></ss:gridEditColumn>
		<ss:gridEditColumn editor="select" dataIndex="psstatus"
			header="���ϲα�״̬" p="E" width="100" codeType="AAC008"></ss:gridEditColumn>
		<ss:gridEditColumn editor="number" dataIndex="xjjsyf" header="Э�ɽ�������"
			p="E" width="80"></ss:gridEditColumn>
		<ss:gridEditColumn editor="number" dataIndex="yljfyfs"
			header="���Ͻɷ��·���01-02��" p="E" width="150"></ss:gridEditColumn>
		<ss:gridEditColumn editor="select" dataIndex="rylb" header="۴����Ա���"
			p="E" width="100" codeType="RYLB"></ss:gridEditColumn>
		<ss:gridEditColumn editor="select" dataIndex="aab301" header="Ͻ��"
			p="E" width="100" codeType="AAB301"></ss:gridEditColumn>
		<ss:gridEditColumn editor="text" dataIndex="bz" header="��ע" p="E"
			width="80"></ss:gridEditColumn>
		<ss:gridEditColumn editor="text" dataIndex="ylcpcode" header="���ϵ�λ����"
			p="E" width="80"></ss:gridEditColumn>
		<ss:gridEditColumn editor="text" dataIndex="yldwmc" header="���ϵ�λ����"
			p="E" width="160"></ss:gridEditColumn>
		<ss:gridEditColumn editor="number" dataIndex="prseno" header="ҵ����ˮ��"
			p="H" width="60" isLast="true"></ss:gridEditColumn>
	</odin:gridColumnModel>
</odin:editgrid>

