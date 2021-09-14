<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
	<ss:doQueryBtn></ss:doQueryBtn>
	<ss:resetBtn></ss:resetBtn>
</ss:toolBar>
<ss:hlistDiv id="div_1" title="查询信息">
	<ss:dateEdit property="time_beg" label="开始时间" p="E"></ss:dateEdit>
	<ss:dateEdit property="time_end" label="结束时间" p="E"></ss:dateEdit>
	<ss:select property="ywmk" isPageSelect="true" label="业务模块" pageSize="20"  minChars="2"  canOutSelectList="true" codeType="FUNCTIONID" p="E"></ss:select>
	<ss:select property="endflag" label="审核结束标志" p="E" ></ss:select>	
	<ss:select property="suser" label="审核人" codeType="USER"   isPageSelect="true" p="E"></ss:select>
	<ss:select property="jbrid" label="经办人" codeType="USER"   isPageSelect="true" p="E"></ss:select>
	<ss:textEdit property="digest" label="摘要信息" p="E"></ss:textEdit>
	<ss:textEdit property="ywrz" label="业务日志ID" p="H"></ss:textEdit>
	
</ss:hlistDiv>
<ss:editgrid property="div_2" pageSize="150" title="审核信息"  bbarId="pageToolBar" pageSize="30"     isFirstLoadData="false" url="/" height="-130,1" autoFill="false">
		<ss:gridColModel>
			<ss:gridCol header="规则id" editor="number" name="ruleid" width="180" p="H" maxLength="20"></ss:gridCol>
			<ss:gridCol header="模块名称" name="funtitle" width="150" editor="text"/>
			<ss:gridCol header="业务日志ID" name="opseno" width="120" editor="number" p="H"/>
			<ss:gridCol header="functionid" name="functionid" width="120" editor="number" p="H"/>
			<ss:gridCol header="业务界面" name="opseno1" width="80" editor="number"  renderer="renderClick"/>
			<ss:gridCol header="数据界面" name="opseno2" width="80" editor="number"  renderer="renderClick"/>
			<ss:gridCol header="摘要" name="digest" width="200" editor="text"/>	
			<ss:gridCol header="审核级数" name="levelcount" width="80" editor="select" codeType="SHJS"></ss:gridCol>
			<ss:gridCol header="当前审核级" name="aulevel" width="80" editor="select" codeType="SHJS"></ss:gridCol>
			<ss:gridCol header="审核标志" name="auflag" width="80" editor="select" codeType="AUFLAG_A"></ss:gridCol>
			<ss:gridCol header="审核结束标志" name="endflag" width="80" editor="select" codeType="SHJSBZ"></ss:gridCol>
			<ss:gridCol header="经办人" name="username" width="80" editor="text"/>
			<ss:gridCol header="经办时间" name="aae036" width="90" editor="date"></ss:gridCol>
			<ss:gridCol header="审核人" name="usersinfo" width="120" editor="text"></ss:gridCol>
			<ss:gridCol header="审核级别id" name="levelid" width="120" p="H" editor="text"></ss:gridCol>
			<ss:gridCol header="审核结论" name="enddesc" width="200" editor="text"></ss:gridCol>			
			<ss:gridCol header="审核结束时间" name="enddate" width="90" editor="date"></ss:gridCol>
		</ss:gridColModel>
</ss:editgrid>
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
			} else if(clickId=='opseno'){ // 不需要确认的按钮
				return "<a href='javascript:void(0)' class='render' onclick='showAudit(\"" + clickId + "\",\"" + clickParams+"\"," + rowIndex + ")'>" + showValue + "</a>";
			} 
		} else {
			return "<font >" + showValue + "</font>";
		}

	}
	//审核明细 
	function showAudit(clickId,initParams,rowIndex){
			var src = contextPath;
			src += "/radowAction.do?method=doEvent&pageModel=cm&bs=intelligent.CmAuditMx&initParams="+initParams;
			parent.odin.openWindow("pupWindowC3","审核明细",src,0.7,0.7);
	}
</script>