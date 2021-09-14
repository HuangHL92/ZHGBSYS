<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
	<ss:doSaveBtn></ss:doSaveBtn>
	<ss:doClickBtn handlerName="doFailAudit" text="审核不通过" icon="images/save.gif"></ss:doClickBtn>
</ss:toolBar>
<ss:hlistDiv id="div_1" >
	<ss:textEdit property="ywmk" label="业务模块" p="E"></ss:textEdit>
</ss:hlistDiv>
<ss:editgrid property="div_2"  title="审核信息"  bbarId="pageToolBar" pageSize="150"  afteredit="radow.cm.afteredit"  isFirstLoadData="false" url="/" height="500" autoFill="false" rowDbClick="true">
		<ss:gridColModel>
			<ss:gridCol header="selectall" width="50" name="check" editor="checkbox"  checkBoxClick="radow.cm.doGridCheck" checkBoxSelectAllClick="radow.cm.doCheck" p="E"/>
			<ss:gridCol header="日志id111" name="opseno1" editor="number" width="100" renderer="renderClick"></ss:gridCol>
			<ss:gridCol header="规则条件" name="ruleid1" editor="number" width="120" renderer="renderClick"></ss:gridCol>
			<ss:gridCol header="指派" name="zhipai" editor="number" width="80" renderer="renderClick"></ss:gridCol>
			<ss:gridCol header="业务日志（点击还原）" name="opseno" editor="number" width="160" p="H"></ss:gridCol>
			<ss:gridCol header="规则id" editor="number" name="ruleid" width="180" p="H" maxLength="20"></ss:gridCol>
			<ss:gridCol header="业务id" name="seno" width="300" editor="number" p="H"></ss:gridCol>
			<ss:gridCol header="模块名称" name="funtitle" width="200" editor="text"/>
			<ss:gridCol header="审核级数" name="levelcount" width="100" editor="select" codeType="SHJS"></ss:gridCol>
			<ss:gridCol header="当前审核级" name="aulevel" width="120" editor="select" codeType="SHJS"></ss:gridCol>
			<ss:gridCol header="审核备注" name="desc" width="300" editor="text" p="E" maxLength="100"></ss:gridCol>
			<ss:gridCol header="级别id" name="levelid" width="130" editor="text" p="H"></ss:gridCol>
			<ss:gridCol header="审核人id" name="auditid" width="130" editor="text" p="H"></ss:gridCol>
			<ss:gridCol header="规则opseno" name="ruleopseno" width="130" editor="text" p="H"></ss:gridCol>
		</ss:gridColModel>
</ss:editgrid>
<odin:window src="/" modal="true" id="grantWindow" width="320" height="300"></odin:window>