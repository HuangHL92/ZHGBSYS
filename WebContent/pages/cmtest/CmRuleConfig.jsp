<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar2">
	<odin:textForToolBar text="������������ӡ� "></odin:textForToolBar>
	<odin:fill></odin:fill>
	<ss:doClickBtn id="addrow" text="���ӹ���"  icon="images/add.gif"  handlerName="addrow"></ss:doClickBtn>
	<ss:doSaveBtn></ss:doSaveBtn>
</ss:toolBar>
<ss:editgrid property="div_2" pageSize="150" topBarId="bar2"  afteredit="radow.cm.afteredit"  isFirstLoadData="false" url="/" title="������Ϣ"  height="300" autoFill="false">
		<ss:gridColModel>
			<ss:gridCol header="����id" name="ruleid" editor="text" p="H"></ss:gridCol>
			<ss:gridCol header="����ģ��" editor="select" name="functionid" width="230" p="H"></ss:gridCol>
			<ss:gridCol header="��������" editor="text" name="rulename" width="180" p="E" maxLength="20"></ss:gridCol>
			<ss:gridCol header="��������" name="tjpz" width="130" editor="text" renderer="renderClick1"></ss:gridCol>
			<ss:gridCol header="�������" name="shpz" width="130" editor="text" renderer="renderClick1"></ss:gridCol>
			<ss:gridCol header="��˼���" name="oplevel" width="130" editor="number" p="H"></ss:gridCol>
			<ss:gridCol header="ɾ��" name="delrow" width="130" editor="text" renderer="renderClick"></ss:gridCol>
		</ss:gridColModel>
</ss:editgrid>
<ss:hlistDiv id="div_1">
	<ss:textEdit property="title" label="ģ������" p="H"></ss:textEdit>
	<ss:textEdit property="functionid" label="ģ��id" p="H"></ss:textEdit>
</ss:hlistDiv>
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
			} else if(clickId=='tjpz'){ // ����Ҫȷ�ϵİ�ť
				return "<a href='javascript:void(0)' class='render' onclick='addConditon(\"" + clickId + "\",\"" + clickParams+"\"," + rowIndex + ")'>" + showValue + "</a>";
			} else if(clickId=='shpz'){
				return "<a href='javascript:void(0)' class='render' onclick='addOpauditor(\"" + clickId + "\",\"" + clickParams+"\"," + rowIndex + ")'>" + showValue + "</a>";
			} 
		} else {
			return "<font color=#CCCCCC>" + showValue + "</font>";
		}

	}
	//�������
	function addConditon(clickId,initParams,rowIndex){
		var src = contextPath;
			src += "/radowAction.do?method=doEvent&pageModel=cm&bs=intelligent.CmRuleConf&initParams="+initParams;
			parent.odin.openWindow("pupWindowC1","��������",src,0.7,0.7);
	}
	//�������
	function addOpauditor(clickId,initParams,rowIndex){
		var src = contextPath;
			src += "/radowAction.do?method=doEvent&pageModel=cm&bs=intelligent.CmAuditorConf&initParams="+initParams;
			parent.odin.openWindow("pupWindowC2","�������",src,0.8,0.6);
	}
</script>