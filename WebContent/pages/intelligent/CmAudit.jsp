<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1"> 
	<odin:fill></odin:fill> 
	<ss:opLogButtonForToolBar/>	
	<ss:doQueryBtn></ss:doQueryBtn>
	<ss:doSaveBtn text="���"></ss:doSaveBtn>
	<ss:resetBtn></ss:resetBtn>

</ss:toolBar>
<ss:hlistDiv id="div_1" >
	
	<ss:dateEdit property="begtime" label="���쿪ʼʱ��" p="E"></ss:dateEdit>
	<ss:dateEdit property="endtime" label="�������ʱ��" p="E"></ss:dateEdit>
	<ss:select property="suser" label="������" codeType="USER"  isPageSelect="true" p="E"></ss:select>
	<ss:textEdit property="digest" label="ժҪ��Ϣ" p="E"></ss:textEdit>
	<ss:select property="ywmk" isPageSelect="true" label="ҵ��ģ��" pageSize="20"  minChars="2"  canOutSelectList="true" codeType="FUNCTIONID" p="E"></ss:select>
	<ss:empeyTD empeyTDCount="2">
			<td colspan="2" align="right">
				<ss:checkbox property="auditall"  label="�����������"></ss:checkbox>
			</td>			
	</ss:empeyTD>
	<ss:textEdit property="printrow" p="H"></ss:textEdit>
</ss:hlistDiv>
<ss:editgrid property="div_2"  title="�����Ϣ"  bbarId="pageToolBar" pageSize="50"  afteredit="radow.cm.afteredit"  isFirstLoadData="false" url="/" height="-80,1" autoFill="false" rowDbClick="true">
		<ss:gridColModel>
			<ss:gridCol header="selectall" width="50" name="checked" editor="checkbox"   p="E"/>
			<ss:gridCol header="�Ƿ�ͨ��" name="tbgbz" width="70" editor="select"  codeType="TBGBZ" p="R"></ss:gridCol>
			<ss:gridCol header="ҵ�����" name="opseno1" editor="number" width="80" renderer="renderClick"></ss:gridCol>
			<ss:gridCol header="���ݽ���" name="opseno2" editor="number" width="80" renderer="renderClick"></ss:gridCol>
			<ss:gridCol header="��������" name="ruleid1" editor="number" width="80" renderer="renderClick" p="H"></ss:gridCol>
			<ss:gridCol header="ָ��" name="zhipai" editor="number" width="50" renderer="renderClick"></ss:gridCol>
			<ss:gridCol header="ҵ����־�������ԭ��" name="opseno" editor="number" width="160" p="H"></ss:gridCol>
			<ss:gridCol header="����id" editor="number" name="ruleid" width="180" p="H" maxLength="20"></ss:gridCol>
			<ss:gridCol header="ҵ��id" name="seno" width="300" editor="number" p="H"></ss:gridCol>
			<ss:gridCol header="ģ������" name="funtitle" width="200" editor="text"/>			
			<ss:gridCol header="ժҪ" name="digest" width="350" editor="text"/>	
			<ss:gridCol header="������" name="username" width="80" editor="text"/>	
			<ss:gridCol header="����ʱ��" name="aae036" width="80" editor="date"/>		
			<ss:gridCol header="��˼���" name="levelcount" width="100" editor="select" codeType="SHJS"></ss:gridCol>
			<ss:gridCol header="��ǰ��˼�" name="aulevel" width="120" editor="select" codeType="SHJS"></ss:gridCol>
			<ss:gridCol header="��˱�ע" name="desc" width="300" editor="text" p="E" maxLength="100"></ss:gridCol>
			<ss:gridCol header="����id" name="levelid" width="130" editor="text" p="H"></ss:gridCol>
			<ss:gridCol header="�����id" name="auditid" width="130" editor="text" p="H"></ss:gridCol>
			<ss:gridCol header="��Աid" name="aac001" width="70" editor="text" p="H"></ss:gridCol>
			<ss:gridCol header="��λid" name="aaz001" width="70" editor="text" p="H"></ss:gridCol>
			<ss:gridCol header="functionid" name="functionid" width="130" editor="text" p="H"></ss:gridCol>
			<ss:gridCol header="����opseno" name="ruleopseno" width="130" editor="text" p="H"></ss:gridCol>
		</ss:gridColModel>
</ss:editgrid>
<odin:window src="/" modal="true" id="grantWindow" width="335" height="450"></odin:window>