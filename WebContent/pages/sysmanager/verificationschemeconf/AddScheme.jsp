<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<odin:toolBar property="btnToolBar" applyTo="addRoleContent">
	<odin:textForToolBar text=""/>
	<odin:fill/>
	<odin:buttonForToolBar id="btnSave"  text="保存"  icon="images/save.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnClose" isLast="true" text="关闭"  icon="images/back.gif" cls="x-btn-text-icon"/>
</odin:toolBar>
<%-- <odin:panel contentEl="addRoleContent" property="addRolePanel" topBarId="btnToolBar" width="300px"/> --%>
<div id="addRoleContent">
<odin:hidden property="vsc001" /><!--	校验方案编码 -->
<odin:hidden property="vsc003" /><!--	有效标识默认：1。0-无效，1-有效 -->
<odin:hidden property="vsc004" /><!--	创建人所属机构编码 -->
<odin:hidden property="vsc005" /><!--	创建人员id -->
<odin:hidden property="vsc006" /><!--	创建时间 -->
<odin:hidden property="vsc008" /><!--	是否上级下发方案0-不是，1-是上级下发方案 -->
<odin:hidden property="vsc010" /><!--	行政区划.区分上下级？ -->
<odin:hidden property="vsc011" /><!--	备用字段 -->
</div>
<style>
.div-height{ width:300px; min-height:200px}
#addRoleContent{width:588px}
</style>
<div class="div-height"></div>
<odin:groupBox property="s2" title="校验方案基本信息">
	<table >
	<tr>
		<odin:textEdit property="vsc002" label="方案名称" required="true" size="50" maxlength="50"/> 
		<odin:select property="vsc007" label="&nbsp;默认基础校验标识" codeType="VSC007" required="true" size="3" value="0" />
		<td></td>
		<td></td>
	</tr>
	<tr>
		<odin:textarea property="vsc009" label="方案备注说明" colspan="11"  rows="3" maxlength="200"></odin:textarea>
	</tr>
</table>
</odin:groupBox>

<script type="text/javascript"><!--

//-->
</script>
