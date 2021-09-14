<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
	<ss:doQueryBtn></ss:doQueryBtn>
</ss:toolBar>
<ss:hlistDiv id="div_1" title="查询信息">
	<ss:textEdit property="ywmk" label="业务模块" p="E"></ss:textEdit>
	<ss:textEdit property="ywrz" label="业务日志ID" p="E"></ss:textEdit>
	<ss:textEdit property="auditor" label="审核人" p="E"></ss:textEdit>
</ss:hlistDiv>
<ss:editgrid property="div_2" pageSize="150" title="审核信息"  bbarId="pageToolBar"   afteredit="radow.cm.afteredit"  isFirstLoadData="false" url="/" height="430" autoFill="false">
		<ss:gridColModel>
			<ss:gridCol header="规则id" editor="number" name="ruleid" width="180" p="H" maxLength="20"></ss:gridCol>
			<ss:gridCol header="模块" name="functionid" width="180" editor="text" p="H"/>
			<ss:gridCol header="模块名称" name="funtitle" width="150" editor="text"/>
			<ss:gridCol header="业务日志ID" name="opseno" width="80" editor="number"/>
			<ss:gridCol header="点击还原" name="opseno1" width="100" editor="number" renderer="renderClick"/>
			<ss:gridCol header="审核级数" name="levelcount" width="100" editor="select" codeType="SHJS"></ss:gridCol>
			<ss:gridCol header="当前审核级" name="aulevel" width="100" editor="select" codeType="SHJS"></ss:gridCol>
			<ss:gridCol header="当前审核标志" name="auflag" width="100" editor="select" codeType="SHBZ"></ss:gridCol>
			<ss:gridCol header="审核结束标志" name="endflag" width="100" editor="select" codeType="SHJSBZ"></ss:gridCol>
			<ss:gridCol header="审核人" name="aae011" width="100" editor="text"></ss:gridCol>
			<ss:gridCol header="审核备注" name="aae013" width="200" editor="text"></ss:gridCol>
			<ss:gridCol header="审核时间" name="aae036" width="130" editor="date"></ss:gridCol>
		</ss:gridColModel>
</ss:editgrid>