<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:toolBar property="bar1" applyTo="floatToolDiv">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar  id="doSave"  text="���� "  cls="x-btn-text-icon" icon="images/save.gif" isLast="true"/>
</odin:toolBar>
<odin:floatDiv property="floatToolDiv"></odin:floatDiv>
<odin:toolBar property="bar2">
	<odin:textForToolBar text="������������ӡ�"></odin:textForToolBar>
	<odin:fill></odin:fill>
	<odin:buttonForToolBar  id="addRow"  text="��������" cls="x-btn-text-icon" icon="images/add.gif" handler="addRow"/>
</odin:toolBar>

<br>
<br>
<odin:groupBox title="��������">
	<table width="100%">
		<tr>
			<td colspan="2">
				<odin:textEdit property="rulename" label="��������" maxlength="20"></odin:textEdit>
			</td>
			<td>
				<odin:numberEdit property="rlevelc" label="��˼���"></odin:numberEdit>
			</td>
		</tr>
	</table>
</odin:groupBox>
<odin:editgrid property="grid1" pageSize="150" topBarId="bar2"  bbarId="pageToolBar" isFirstLoadData="false" url="/" title=""  height="200" autoFill="false">
	<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="check"/>
		<odin:gridDataCol name="conditionname"/>
		<odin:gridDataCol name="target"/>
		<odin:gridDataCol name="operater"/>
		<odin:gridDataCol name="condition"/>
		<odin:gridDataCol name="delrow" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn header="" dataIndex="check" gridName="grid" editor="checkbox"
			edited="true" width="40" />
		<odin:gridColumn header="��������" dataIndex="conditionname" width="150" edited="true" editor="text"/>
		<odin:gridColumn header="�ֶ�" dataIndex="target" width="120" edited="true" editor="text"/>
		<odin:gridColumn header="�����" dataIndex="operater" width="120" edited="true" editor="text"/>
		<odin:gridColumn header="����" dataIndex="condition" editor="number" edited="true" width="120" edited="true"/>
		<odin:gridColumn header="ɾ��" dataIndex="delrow" width="120" isLast="true" renderer="renderClick" edited="true"/>
	</odin:gridColumnModel>
	<odin:gridJsonData>
			{data:[]}
		</odin:gridJsonData>
</odin:editgrid>
<script type="text/javascript">
	function addRow(){
		radow.addGridEmptyRow('grid1');
	}
	function renderClick(value, params, record, rowIndex, colIndex, ds) {
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
			} else { // ����Ҫȷ�ϵİ�ť
				return "<a href='javascript:void(0)' class='render' onclick='radow.cm.doGridClick(\"" + clickId + "\",\"" + Ext.encode(clickParams).replace(/"/g, "\\\"") + "\"," + rowIndex + ")'>" + showValue + "</a>";
			}
		} else {
			return "<font color=#CCCCCC>" + showValue + "</font>";
		}

	}
</script>