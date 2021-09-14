<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<style>
<%=FontConfigPageModel.getFontConfig()%>
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<%String ctxPath = request.getContextPath(); 

%>


<odin:toolBar property="toolBar1" applyTo="tol1">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="保存" id="save" icon="images/save.gif" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
<div id="main" style="border: 1px solid #99bbe8; padding: 0px;margin: 0px;" >
<div id="tol1"></div>
	<odin:hidden property="a5399" title="填报人id" />
	<table cellspacing="2" width="440" align="left" style="margin-top: 40px">
						
		<tr>
			<odin:textEdit property="a5304" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟任职务" validator="a5304Length"></odin:textEdit>
			<odin:textEdit property="a5315" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟免职务" validator="a5315Length"></odin:textEdit>
			<odin:textEdit property="a5317" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;任免理由" validator="a5317Length"></odin:textEdit>
		</tr>
		<tr>
			<odin:hidden property="a5300" title="id(a5300" ></odin:hidden>
			<odin:NewDateEditTag property="a5321" label="计算年龄时间" isCheck="true" maxlength="8"></odin:NewDateEditTag>
			<odin:NewDateEditTag property="a5323" label="填表时间" isCheck="true" maxlength="8"></odin:NewDateEditTag>
			<odin:textEdit property="a5327" label="填表人" validator="a5327Length"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="a5319" label="呈报单位" validator="a5319Length"></odin:textEdit>
		</tr>
	</table>

</div>


<script type="text/javascript">
Ext.onReady(function(){
	
	$h.fieldsDisabled(realParent.fieldsDisabled);
	if(realParent.buttonDisabled){
		document.getElementById('cover_wrap1').className = "divcover_wrap";
		$h.setDisabled($h.disabledButtons.a53);
	}

	document.getElementById('tol1').style.width = document.body.clientWidth - 2 ;
});



</script>

<div id="cover_wrap1"></div>