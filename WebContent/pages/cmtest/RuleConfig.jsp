<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:toolBar property="bar1" applyTo="floatToolDiv">
	<odin:textForToolBar text="添加请点击【增加】 "></odin:textForToolBar>
	<odin:fill></odin:fill>
	<odin:buttonForToolBar  id="addRule"  text="增加规则  " cls="x-btn-text-icon" icon="images/add.gif" handler="addRow"/>
	<odin:buttonForToolBar  id="doSave"  text="保存"  cls="x-btn-text-icon" icon="images/save.gif" isLast="true"/>
</odin:toolBar>
<odin:floatDiv property="floatToolDiv"></odin:floatDiv>
<br>
<br>
<odin:editgrid property="grid1" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title=""  height="200" autoFill="false">
	<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="check"/>
		<odin:gridDataCol name="rulename"/>
		<odin:gridDataCol name="tjpz"/>
		<odin:gridDataCol name="shpz"/>
		<odin:gridDataCol name="levelcount"/>
		<odin:gridDataCol name="delrow" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn header="" dataIndex="check" gridName="grid" editor="checkbox"
			edited="true" width="40" />
		<odin:gridColumn header="规则名称" dataIndex="rulename" width="150" edited="true" editor="text"/>
		<odin:gridColumn header="条件配置" dataIndex="tjpz" width="120" renderer="renderClick"/>
		<odin:gridColumn header="审核配置" dataIndex="shpz" width="120" renderer="renderClick"/>
		<odin:gridColumn header="审核级数" dataIndex="levelcount" editor="number" edited="true" width="120"/>
		<odin:gridColumn header="删除" dataIndex="delrow" width="120" isLast="true" renderer="renderClick"/>
	</odin:gridColumnModel>
	<odin:gridJsonData>
			{data:[]}
		</odin:gridJsonData>
</odin:editgrid>
<odin:hidden property="functionid"/>
<script type="text/javascript">
	function addRow(){
		var src = contextPath;
		var funid = document.getElementById('functionid').value;
		src += "/radowAction.do?method=doEvent&pageModel=pages.cmtest.RuleConf&initParams="+funid;
		parent.odin.openWindow("pupWindowC",MDParam.title,src,0.8,0.6);
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