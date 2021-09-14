<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:toolBar property="bar1" applyTo="floatToolDiv">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar  id="doSave"  text="保存 "  cls="x-btn-text-icon" icon="images/save.gif" isLast="true"/>
</odin:toolBar>
<odin:floatDiv property="floatToolDiv"></odin:floatDiv>
<odin:toolBar property="bar2">
	<odin:textForToolBar text="添加请点击【增加】"></odin:textForToolBar>
	<odin:fill></odin:fill>
	<odin:buttonForToolBar  id="addRow"  text="增加条件" cls="x-btn-text-icon" icon="images/add.gif" handler="addRow"/>
</odin:toolBar>

<br>
<br>
<odin:groupBox title="规则配置">
	<table width="100%">
		<tr>
			<td colspan="2">
				<odin:textEdit property="rulename" label="规则名称" maxlength="20"></odin:textEdit>
			</td>
			<td>
				<odin:numberEdit property="rlevelc" label="审核级数"></odin:numberEdit>
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
		<odin:gridColumn header="条件名称" dataIndex="conditionname" width="150" edited="true" editor="text"/>
		<odin:gridColumn header="字段" dataIndex="target" width="120" edited="true" editor="text"/>
		<odin:gridColumn header="运算符" dataIndex="operater" width="120" edited="true" editor="text"/>
		<odin:gridColumn header="条件" dataIndex="condition" editor="number" edited="true" width="120" edited="true"/>
		<odin:gridColumn header="删除" dataIndex="delrow" width="120" isLast="true" renderer="renderClick" edited="true"/>
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
		var showValue = valueArray[0]; // 显示的内容
		var clickFlag = valueArray[1]; // 是否允许点击
		var clickId = null; // 按钮id
		if (valueArray.length > 2) {
			clickId = valueArray[2]; // 按钮id
		}
		if (valueArray.length > 3) {
			clickParams = new Array(valueArray.length - 3);
		}
		for (var i = 3; i < valueArray.length; i++) {
			clickParams[i - 3] = valueArray[i];
		}
		if (clickFlag == "1") {
			if (clickId.indexOf("confirm") != -1) { // 需要确认的按钮
				return "<a href='javascript:void(0)' class='render' onclick='odin.confirm(\"确定要对本行进行" + showValue + "操作？\", function(btn) {" + " if (btn == \"ok\") {radow.cm.doGridClick(\"" + clickId + "\",\"" + Ext.encode(clickParams).replace(/"/g, "\\\"") + "\"," + rowIndex + ")}})'>" + showValue + "</a>";
			} else { // 不需要确认的按钮
				return "<a href='javascript:void(0)' class='render' onclick='radow.cm.doGridClick(\"" + clickId + "\",\"" + Ext.encode(clickParams).replace(/"/g, "\\\"") + "\"," + rowIndex + ")'>" + showValue + "</a>";
			}
		} else {
			return "<font color=#CCCCCC>" + showValue + "</font>";
		}

	}
</script>