<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
	<ss:doSaveBtn></ss:doSaveBtn>
	<ss:doClickBtn handlerName="doFailAudit" text="��˲�ͨ��" icon="images/save.gif"></ss:doClickBtn>
</ss:toolBar>
<ss:hlistDiv id="div_1" >
	<ss:textEdit property="ywmk" label="ҵ��ģ��" p="E"></ss:textEdit>
</ss:hlistDiv>
<ss:editgrid property="div_2"  title="�����Ϣ"  bbarId="pageToolBar" pageSize="150"  afteredit="radow.cm.afteredit"  isFirstLoadData="false" url="/" height="500" autoFill="false" rowDbClick="true">
		<ss:gridColModel>
			<ss:gridCol header="selectall" width="50" name="check" editor="checkbox"  checkBoxClick="radow.cm.doGridCheck" checkBoxSelectAllClick="radow.cm.doCheck" p="E"/>
			<ss:gridCol header="��־id111" name="opseno1" editor="number" width="100" renderer="renderClick"></ss:gridCol>
			<ss:gridCol header="��������" name="ruleid1" editor="number" width="120" renderer="renderClick"></ss:gridCol>
			<ss:gridCol header="ָ��" name="zhipai" editor="number" width="80" renderer="renderClick"></ss:gridCol>
			<ss:gridCol header="ҵ����־�������ԭ��" name="opseno" editor="number" width="160" p="H"></ss:gridCol>
			<ss:gridCol header="����id" editor="number" name="ruleid" width="180" p="H" maxLength="20"></ss:gridCol>
			<ss:gridCol header="ҵ��id" name="seno" width="300" editor="number" p="H"></ss:gridCol>
			<ss:gridCol header="ģ������" name="funtitle" width="200" editor="text"/>
			<ss:gridCol header="��˼���" name="levelcount" width="100" editor="select" codeType="SHJS"></ss:gridCol>
			<ss:gridCol header="��ǰ��˼�" name="aulevel" width="120" editor="select" codeType="SHJS"></ss:gridCol>
			<ss:gridCol header="��˱�ע" name="desc" width="300" editor="text" p="E" maxLength="100"></ss:gridCol>
			<ss:gridCol header="����id" name="levelid" width="130" editor="text" p="H"></ss:gridCol>
			<ss:gridCol header="�����id" name="auditid" width="130" editor="text" p="H"></ss:gridCol>
			<ss:gridCol header="����opseno" name="ruleopseno" width="130" editor="text" p="H"></ss:gridCol>
		</ss:gridColModel>
</ss:editgrid>
<odin:window src="/" modal="true" id="grantWindow" width="320" height="300"></odin:window>