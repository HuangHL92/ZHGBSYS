<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<odin:toolBar property="btnToolBar" applyTo="addRoleContent">
	<odin:textForToolBar text=""/>
	<odin:fill/>
	<odin:buttonForToolBar id="btnSave"  text="����"  icon="images/save.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnClose" isLast="true" text="�ر�"  icon="images/back.gif" cls="x-btn-text-icon"/>
</odin:toolBar>
<%-- <odin:panel contentEl="addRoleContent" property="addRolePanel" topBarId="btnToolBar" width="300px"/> --%>
<div id="addRoleContent">
<odin:hidden property="vsc001" /><!--	У�鷽������ -->
<odin:hidden property="vsc003" /><!--	��Ч��ʶĬ�ϣ�1��0-��Ч��1-��Ч -->
<odin:hidden property="vsc004" /><!--	������������������ -->
<odin:hidden property="vsc005" /><!--	������Աid -->
<odin:hidden property="vsc006" /><!--	����ʱ�� -->
<odin:hidden property="vsc008" /><!--	�Ƿ��ϼ��·�����0-���ǣ�1-���ϼ��·����� -->
<odin:hidden property="vsc010" /><!--	��������.�������¼��� -->
<odin:hidden property="vsc011" /><!--	�����ֶ� -->
</div>
<style>
.div-height{ width:300px; min-height:200px}
#addRoleContent{width:588px}
</style>
<div class="div-height"></div>
<odin:groupBox property="s2" title="У�鷽��������Ϣ">
	<table >
	<tr>
		<odin:textEdit property="vsc002" label="��������" required="true" size="50" maxlength="50"/> 
		<odin:select property="vsc007" label="&nbsp;Ĭ�ϻ���У���ʶ" codeType="VSC007" required="true" size="3" value="0" />
		<td></td>
		<td></td>
	</tr>
	<tr>
		<odin:textarea property="vsc009" label="������ע˵��" colspan="11"  rows="3" maxlength="200"></odin:textarea>
	</tr>
</table>
</odin:groupBox>

<script type="text/javascript"><!--

//-->
</script>
