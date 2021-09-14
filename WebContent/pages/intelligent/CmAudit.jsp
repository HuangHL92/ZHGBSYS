<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1"> 
	<odin:fill></odin:fill> 
	<ss:opLogButtonForToolBar/>	
	<ss:doQueryBtn></ss:doQueryBtn>
	<ss:doSaveBtn text="审核"></ss:doSaveBtn>
	<ss:resetBtn></ss:resetBtn>

</ss:toolBar>
<ss:hlistDiv id="div_1" >
	
	<ss:dateEdit property="begtime" label="经办开始时间" p="E"></ss:dateEdit>
	<ss:dateEdit property="endtime" label="经办结束时间" p="E"></ss:dateEdit>
	<ss:select property="suser" label="经办人" codeType="USER"  isPageSelect="true" p="E"></ss:select>
	<ss:textEdit property="digest" label="摘要信息" p="E"></ss:textEdit>
	<ss:select property="ywmk" isPageSelect="true" label="业务模块" pageSize="20"  minChars="2"  canOutSelectList="true" codeType="FUNCTIONID" p="E"></ss:select>
	<ss:empeyTD empeyTDCount="2">
			<td colspan="2" align="right">
				<ss:checkbox property="auditall"  label="审核所有数据"></ss:checkbox>
			</td>			
	</ss:empeyTD>
	<ss:textEdit property="printrow" p="H"></ss:textEdit>
</ss:hlistDiv>
<ss:editgrid property="div_2"  title="审核信息"  bbarId="pageToolBar" pageSize="50"  afteredit="radow.cm.afteredit"  isFirstLoadData="false" url="/" height="-80,1" autoFill="false" rowDbClick="true">
		<ss:gridColModel>
			<ss:gridCol header="selectall" width="50" name="checked" editor="checkbox"   p="E"/>
			<ss:gridCol header="是否通过" name="tbgbz" width="70" editor="select"  codeType="TBGBZ" p="R"></ss:gridCol>
			<ss:gridCol header="业务界面" name="opseno1" editor="number" width="80" renderer="renderClick"></ss:gridCol>
			<ss:gridCol header="数据界面" name="opseno2" editor="number" width="80" renderer="renderClick"></ss:gridCol>
			<ss:gridCol header="规则条件" name="ruleid1" editor="number" width="80" renderer="renderClick" p="H"></ss:gridCol>
			<ss:gridCol header="指派" name="zhipai" editor="number" width="50" renderer="renderClick"></ss:gridCol>
			<ss:gridCol header="业务日志（点击还原）" name="opseno" editor="number" width="160" p="H"></ss:gridCol>
			<ss:gridCol header="规则id" editor="number" name="ruleid" width="180" p="H" maxLength="20"></ss:gridCol>
			<ss:gridCol header="业务id" name="seno" width="300" editor="number" p="H"></ss:gridCol>
			<ss:gridCol header="模块名称" name="funtitle" width="200" editor="text"/>			
			<ss:gridCol header="摘要" name="digest" width="350" editor="text"/>	
			<ss:gridCol header="经办人" name="username" width="80" editor="text"/>	
			<ss:gridCol header="经办时间" name="aae036" width="80" editor="date"/>		
			<ss:gridCol header="审核级数" name="levelcount" width="100" editor="select" codeType="SHJS"></ss:gridCol>
			<ss:gridCol header="当前审核级" name="aulevel" width="120" editor="select" codeType="SHJS"></ss:gridCol>
			<ss:gridCol header="审核备注" name="desc" width="300" editor="text" p="E" maxLength="100"></ss:gridCol>
			<ss:gridCol header="级别id" name="levelid" width="130" editor="text" p="H"></ss:gridCol>
			<ss:gridCol header="审核人id" name="auditid" width="130" editor="text" p="H"></ss:gridCol>
			<ss:gridCol header="人员id" name="aac001" width="70" editor="text" p="H"></ss:gridCol>
			<ss:gridCol header="单位id" name="aaz001" width="70" editor="text" p="H"></ss:gridCol>
			<ss:gridCol header="functionid" name="functionid" width="130" editor="text" p="H"></ss:gridCol>
			<ss:gridCol header="规则opseno" name="ruleopseno" width="130" editor="text" p="H"></ss:gridCol>
		</ss:gridColModel>
</ss:editgrid>
<odin:window src="/" modal="true" id="grantWindow" width="335" height="450"></odin:window>