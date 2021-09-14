<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit.jsp" %>
<style>

</style>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<div id="baseInfo">

	<table>
		<tr>
			<td>
				&nbsp;&nbsp;<odin:textEdit property="text1" label="输入部门名称：" maxlength="500" width="150"></odin:textEdit>
			</td>
		</tr>
	</table>
</div>

<odin:toolBar property="btnToolBar" >
	<odin:fill/>
	<odin:buttonForToolBar id="save"  text="保存" isLast="true"/>
</odin:toolBar>


<odin:panel contentEl="baseInfo" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>