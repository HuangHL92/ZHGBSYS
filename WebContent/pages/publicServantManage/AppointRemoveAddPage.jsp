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
				<odin:buttonForToolBar text="����" id="save" icon="images/save.gif" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
<div id="main" style="border: 1px solid #99bbe8; padding: 0px;margin: 0px;" >
<div id="tol1"></div>
	<odin:hidden property="a5399" title="���id" />
	<table cellspacing="2" width="440" align="left" style="margin-top: 40px">
						
		<tr>
			<odin:textEdit property="a5304" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ְ��" validator="a5304Length"></odin:textEdit>
			<odin:textEdit property="a5315" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ְ��" validator="a5315Length"></odin:textEdit>
			<odin:textEdit property="a5317" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��������" validator="a5317Length"></odin:textEdit>
		</tr>
		<tr>
			<odin:hidden property="a5300" title="id(a5300" ></odin:hidden>
			<odin:NewDateEditTag property="a5321" label="��������ʱ��" isCheck="true" maxlength="8"></odin:NewDateEditTag>
			<odin:NewDateEditTag property="a5323" label="���ʱ��" isCheck="true" maxlength="8"></odin:NewDateEditTag>
			<odin:textEdit property="a5327" label="�����" validator="a5327Length"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="a5319" label="�ʱ���λ" validator="a5319Length"></odin:textEdit>
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