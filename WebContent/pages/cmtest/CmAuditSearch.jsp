<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
	<ss:doQueryBtn></ss:doQueryBtn>
</ss:toolBar>
<ss:hlistDiv id="div_1" title="��ѯ��Ϣ">
	<ss:textEdit property="ywmk" label="ҵ��ģ��" p="E"></ss:textEdit>
	<ss:textEdit property="ywrz" label="ҵ����־ID" p="E"></ss:textEdit>
	<ss:textEdit property="auditor" label="�����" p="E"></ss:textEdit>
</ss:hlistDiv>
<ss:editgrid property="div_2" pageSize="150" title="�����Ϣ"  bbarId="pageToolBar"   afteredit="radow.cm.afteredit"  isFirstLoadData="false" url="/" height="430" autoFill="false">
		<ss:gridColModel>
			<ss:gridCol header="����id" editor="number" name="ruleid" width="180" p="H" maxLength="20"></ss:gridCol>
			<ss:gridCol header="ģ��" name="functionid" width="180" editor="text" p="H"/>
			<ss:gridCol header="ģ������" name="funtitle" width="150" editor="text"/>
			<ss:gridCol header="ҵ����־ID" name="opseno" width="80" editor="number"/>
			<ss:gridCol header="�����ԭ" name="opseno1" width="100" editor="number" renderer="renderClick"/>
			<ss:gridCol header="��˼���" name="levelcount" width="100" editor="select" codeType="SHJS"></ss:gridCol>
			<ss:gridCol header="��ǰ��˼�" name="aulevel" width="100" editor="select" codeType="SHJS"></ss:gridCol>
			<ss:gridCol header="��ǰ��˱�־" name="auflag" width="100" editor="select" codeType="SHBZ"></ss:gridCol>
			<ss:gridCol header="��˽�����־" name="endflag" width="100" editor="select" codeType="SHJSBZ"></ss:gridCol>
			<ss:gridCol header="�����" name="aae011" width="100" editor="text"></ss:gridCol>
			<ss:gridCol header="��˱�ע" name="aae013" width="200" editor="text"></ss:gridCol>
			<ss:gridCol header="���ʱ��" name="aae036" width="130" editor="date"></ss:gridCol>
		</ss:gridColModel>
</ss:editgrid>