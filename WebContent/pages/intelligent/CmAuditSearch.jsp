<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
	<ss:doQueryBtn></ss:doQueryBtn>
	<ss:resetBtn></ss:resetBtn>
</ss:toolBar>
<ss:hlistDiv id="div_1" title="��ѯ��Ϣ">
	<ss:dateEdit property="time_beg" label="��ʼʱ��" p="E"></ss:dateEdit>
	<ss:dateEdit property="time_end" label="����ʱ��" p="E"></ss:dateEdit>
	<ss:select property="ywmk" isPageSelect="true" label="ҵ��ģ��" pageSize="20"  minChars="2"  canOutSelectList="true" codeType="FUNCTIONID" p="E"></ss:select>
	<ss:select property="endflag" label="��˽�����־" p="E" ></ss:select>	
	<ss:select property="suser" label="�����" codeType="USER"   isPageSelect="true" p="E"></ss:select>
	<ss:select property="jbrid" label="������" codeType="USER"   isPageSelect="true" p="E"></ss:select>
	<ss:textEdit property="digest" label="ժҪ��Ϣ" p="E"></ss:textEdit>
	<ss:textEdit property="ywrz" label="ҵ����־ID" p="H"></ss:textEdit>
	
</ss:hlistDiv>
<ss:editgrid property="div_2" pageSize="150" title="�����Ϣ"  bbarId="pageToolBar" pageSize="30"     isFirstLoadData="false" url="/" height="-130,1" autoFill="false">
		<ss:gridColModel>
			<ss:gridCol header="����id" editor="number" name="ruleid" width="180" p="H" maxLength="20"></ss:gridCol>
			<ss:gridCol header="ģ������" name="funtitle" width="150" editor="text"/>
			<ss:gridCol header="ҵ����־ID" name="opseno" width="120" editor="number" p="H"/>
			<ss:gridCol header="functionid" name="functionid" width="120" editor="number" p="H"/>
			<ss:gridCol header="ҵ�����" name="opseno1" width="80" editor="number"  renderer="renderClick"/>
			<ss:gridCol header="���ݽ���" name="opseno2" width="80" editor="number"  renderer="renderClick"/>
			<ss:gridCol header="ժҪ" name="digest" width="200" editor="text"/>	
			<ss:gridCol header="��˼���" name="levelcount" width="80" editor="select" codeType="SHJS"></ss:gridCol>
			<ss:gridCol header="��ǰ��˼�" name="aulevel" width="80" editor="select" codeType="SHJS"></ss:gridCol>
			<ss:gridCol header="��˱�־" name="auflag" width="80" editor="select" codeType="AUFLAG_A"></ss:gridCol>
			<ss:gridCol header="��˽�����־" name="endflag" width="80" editor="select" codeType="SHJSBZ"></ss:gridCol>
			<ss:gridCol header="������" name="username" width="80" editor="text"/>
			<ss:gridCol header="����ʱ��" name="aae036" width="90" editor="date"></ss:gridCol>
			<ss:gridCol header="�����" name="usersinfo" width="120" editor="text"></ss:gridCol>
			<ss:gridCol header="��˼���id" name="levelid" width="120" p="H" editor="text"></ss:gridCol>
			<ss:gridCol header="��˽���" name="enddesc" width="200" editor="text"></ss:gridCol>			
			<ss:gridCol header="��˽���ʱ��" name="enddate" width="90" editor="date"></ss:gridCol>
		</ss:gridColModel>
</ss:editgrid>
<script type="text/javascript">
function renderClick1(value, params, record, rowIndex, colIndex, ds) {
		if (value == null) {
			return;
		}
		var clickParams;
		var valueArray = value.split(",");
		var showValue = valueArray[0]; // ��ʾ������ 
		var clickFlag = valueArray[1]; // �Ƿ������� 
		var clickId = null; // ��ťid
		if (valueArray.length > 2) {
			clickId = valueArray[2]; // ��ťid 
		}
		if (valueArray.length > 3) {
			clickParams = new Array(valueArray.length - 3);
		}
		for (var i = 3; i < valueArray.length; i++) {
			clickParams[i - 3] = valueArray[i];
		}
		if (clickFlag == "1") {
			if (clickId.indexOf("confirm") != -1) { // ��Ҫȷ�ϵİ�ť
				return "<a href='javascript:void(0)' class='render' onclick='odin.confirm(\"ȷ��Ҫ�Ա��н���" + showValue + "������\", function(btn) {" + " if (btn == \"ok\") {radow.cm.doGridClick(\"" + clickId + "\",\"" + Ext.encode(clickParams).replace(/"/g, "\\\"") + "\"," + rowIndex + ")}})'>" + showValue + "</a>";
			} else if(clickId=='opseno'){ // ����Ҫȷ�ϵİ�ť
				return "<a href='javascript:void(0)' class='render' onclick='showAudit(\"" + clickId + "\",\"" + clickParams+"\"," + rowIndex + ")'>" + showValue + "</a>";
			} 
		} else {
			return "<font >" + showValue + "</font>";
		}

	}
	//�����ϸ 
	function showAudit(clickId,initParams,rowIndex){
			var src = contextPath;
			src += "/radowAction.do?method=doEvent&pageModel=cm&bs=intelligent.CmAuditMx&initParams="+initParams;
			parent.odin.openWindow("pupWindowC3","�����ϸ",src,0.7,0.7);
	}
</script>