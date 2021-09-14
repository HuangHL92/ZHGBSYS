<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<style>
<%=FontConfigPageModel.getFontConfig()%>
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/customquery.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<%String ctxPath = request.getContextPath(); 

%>


<odin:toolBar property="toolBar1" applyTo="tol1">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="保存" id="save" handler="saveEntry" icon="images/save.gif" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
<div id="main" style="border: 1px solid #99bbe8; padding: 0px;margin: 0px;" >
<div id="tol1"></div>

	<table cellspacing="2" width="440" align="left" style="margin-top: 40px">
						
		<tr>
			<td>
		 	<odin:NewDateEditTag property="a2907"  label="进入本单位日期" isCheck="true" maxlength="8"></odin:NewDateEditTag>	
			</td>
			<td>
			<tags:PublicTextIconEdit property="a2911" label="进入本单位变动类别"  codetype="ZB77" readonly="true" required="true"></tags:PublicTextIconEdit>		
			</td>
		</tr>
		<tr>
			<td>
			<odin:textEdit property="a2941" label="在原单位职务"  validator="a2941Length"></odin:textEdit>
			</td>
			<td>
			<%-- <odin:textEdit property="a2944" label="在原单位职务层次" validator="a2944Length"></odin:textEdit> --%>
			<tags:PublicTextIconEdit property="a2944s" label="在原单位职务层次" codetype="ZB09"></tags:PublicTextIconEdit>
			</td>
		</tr>
		<tr>
			<td>
			<odin:textEdit property="a2921a" label="进入本单位前工作单位名称" validator="a2921aLength"></odin:textEdit>
			</td>
			<td>
			<odin:NewDateEditTag property="a2949" label="公务员登记时间" isCheck="true" maxlength="8"></odin:NewDateEditTag>	
			</td>
		</tr>
	</table>

</div>


<script type="text/javascript">
Ext.onReady(function(){
	
	$h.fieldsDisabled(realParent.fieldsDisabled);
	if(realParent.buttonDisabled){
		document.getElementById('cover_wrap1').className = "divcover_wrap";
	}

	$h.applyFontConfig($h.spFeildAll.a29);
	if(realParent.buttonDisabled){
		$h.setDisabled($h.disabledButtons.a29);
	}
	
	document.getElementById('tol1').style.width = document.body.clientWidth - 2 ;	
});



</script>

<div id="cover_wrap1"></div>