<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar2">
	<odin:textForToolBar text="添加请点击【增加】 "></odin:textForToolBar>
	<odin:fill></odin:fill>
	<ss:doClickBtn id="addrow" text="增加规则"  icon="images/add.gif"  handlerName="addrow"></ss:doClickBtn>
	<ss:doSaveBtn></ss:doSaveBtn>
</ss:toolBar>
<ss:editgrid property="div_2" pageSize="150" topBarId="bar2"  afteredit="radow.cm.afteredit"  isFirstLoadData="false" url="/" title="规则信息"  height="300" autoFill="false">
		<ss:gridColModel>
			<ss:gridCol header="规则id" name="ruleid" editor="text" p="H"></ss:gridCol>
			<ss:gridCol header="功能模块" editor="select" name="functionid" width="230" p="H"></ss:gridCol>
			<ss:gridCol header="规则名称" editor="text" name="rulename" width="180" p="E" maxLength="20"></ss:gridCol>
			<ss:gridCol header="条件配置" name="tjpz" width="130" editor="text" renderer="renderClick1"></ss:gridCol>
			<ss:gridCol header="审核配置" name="shpz" width="130" editor="text" renderer="renderClick1"></ss:gridCol>
			<ss:gridCol header="审核级数" name="oplevel" width="130" editor="number" p="H"></ss:gridCol>
			<ss:gridCol header="删除" name="delrow" width="130" editor="text" renderer="renderClick"></ss:gridCol>
		</ss:gridColModel>
</ss:editgrid>
<ss:hlistDiv id="div_1">
	<ss:textEdit property="title" label="模块名称" p="H"></ss:textEdit>
	<ss:textEdit property="functionid" label="模块id" p="H"></ss:textEdit>
</ss:hlistDiv>
<script type="text/javascript">
	function renderClick1(value, params, record, rowIndex, colIndex, ds) {
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
			} else if(clickId=='tjpz'){ // 不需要确认的按钮
				return "<a href='javascript:void(0)' class='render' onclick='addConditon(\"" + clickId + "\",\"" + clickParams+"\"," + rowIndex + ")'>" + showValue + "</a>";
			} else if(clickId=='shpz'){
				return "<a href='javascript:void(0)' class='render' onclick='addOpauditor(\"" + clickId + "\",\"" + clickParams+"\"," + rowIndex + ")'>" + showValue + "</a>";
			} 
		} else {
			return "<font color=#CCCCCC>" + showValue + "</font>";
		}

	}
	//添加条件
	function addConditon(clickId,initParams,rowIndex){
		var src = contextPath;
			src += "/radowAction.do?method=doEvent&pageModel=cm&bs=intelligent.CmRuleConf&initParams="+initParams;
			parent.odin.openWindow("pupWindowC1","条件配置",src,0.7,0.7);
	}
	//审核配置
	function addOpauditor(clickId,initParams,rowIndex){
		var src = contextPath;
			src += "/radowAction.do?method=doEvent&pageModel=cm&bs=intelligent.CmAuditorConf&initParams="+initParams;
			parent.odin.openWindow("pupWindowC2","审核配置",src,0.8,0.6);
	}
</script>