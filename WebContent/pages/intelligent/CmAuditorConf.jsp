<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>

<ss:toolBar property="bar2">
	<odin:textForToolBar text="������������ӡ�(���ɾ������Ҫ�������)"></odin:textForToolBar>
	<odin:fill></odin:fill>
	<ss:resetBtn></ss:resetBtn>
	<ss:doClickBtn id="addrow" text="����"  icon="images/add.gif"  handlerName="addrow"></ss:doClickBtn>
	<ss:doSaveBtn></ss:doSaveBtn>
</ss:toolBar>
<ss:editgrid property="div_2" pageSize="150" topBarId="bar2" title="��˼���Ϣ" afteredit="radow.cm.afteredit"  isFirstLoadData="false" url="/" height="-50,1" autoFill="false">
		<ss:gridColModel>
			<ss:gridCol header="��˼�id" name="levelid" editor="text" p="H"></ss:gridCol>
			<ss:gridCol header="��˼�" name="aulevel" editor="select" width="100" codeType="SHJ"></ss:gridCol>
			<ss:gridCol header="����ˣ��飩��Ϣ" name="userinfos" width="300" editor="text"></ss:gridCol>
			<ss:gridCol header="�û�����" name="grant1" width="130" editor="text" renderer="renderClick"></ss:gridCol>
			<ss:gridCol header="����" name="uprow" width="80" editor="text" renderer="renderClick"></ss:gridCol>
			<ss:gridCol header="����" name="downrow" width="80" editor="text" renderer="renderClick"></ss:gridCol>
			<ss:gridCol header="ɾ��" name="delrow" width="130" editor="text" renderer="renderClick"></ss:gridCol>
		</ss:gridColModel>
</ss:editgrid>
<ss:hlistDiv id="div_1">
	<ss:textEdit property="rulename" label="��������" p="H"></ss:textEdit>
	<ss:textEdit property="ruleid" label="����id" p="H"></ss:textEdit>
</ss:hlistDiv>
<odin:window src="/" modal="true" id="grantWindow" width="335" height="450"></odin:window>
 
<script type="text/javascript">
function setUser(users,currow){ 
	odin.ext.getCmp("div_2").store.data.itemAt(currow).set('userinfos',users);
}
</script>