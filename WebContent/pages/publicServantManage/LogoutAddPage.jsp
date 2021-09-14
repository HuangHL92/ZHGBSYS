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

<%-- <odin:hidden property="orgid" title="历史离退人员所在的机构id" ></odin:hidden> --%>

<odin:toolBar property="toolBar1" applyTo="tol1">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="保存" id="save"  handler="saveLogout"  icon="images/save.gif" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
<div id="main" style="border: 1px solid #99bbe8; padding: 0px;margin: 0px;" >
<div id="tol1"></div>

	<table cellspacing="2" width="440" align="left" style="margin-top: 40px">
		<tr>
			<tags:PublicTextIconEdit property="a3001" label="退出管理方式" codetype="ZB78" readonly="true" onchange="a3001change" required="true"></tags:PublicTextIconEdit>	
		</tr>
		<tr>
			<tags:PublicTextIconEdit  property="a3007a" label="调往单位" readonly="true" codetype="orgTreeJsonData" ></tags:PublicTextIconEdit>
		</tr>
		<tr>
			<%-- <tags:PublicTextIconEdit  property="orgid" label="退出单位" readonly="true" codetype="orgTreeJsonData" ></tags:PublicTextIconEdit> --%>
			<odin:select2 property="orgid" label="退出单位"></odin:select2>
		</tr>
		<tr>		
			<odin:NewDateEditTag property="a3004" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;退出管理时间" isCheck="true" maxlength="8"></odin:NewDateEditTag>
		</tr>
		<tr>
			<odin:textEdit property="a3034" label="备注" validator="a3034Length"></odin:textEdit>
		</tr>
	</table>

</div>


<script type="text/javascript">
Ext.onReady(function(){
	
	$h.fieldsDisabled(realParent.fieldsDisabled);
	if(realParent.buttonDisabled){
		document.getElementById('cover_wrap1').className = "divcover_wrap";
		$h.setDisabled($h.disabledButtons.a30);
	}

});



function a3001change(rs){
	if(rs.data.key.substring(0,1) != "1" && rs.data.key.substring(0,1) != "2"){
		odin.ext.getCmp('a3007a_combo').disable();
//		odin.ext.getCmp('a3007a_combo').allowBlank = true;
		Ext.query("#a3007a_combo+img")[0].onclick=null;
		document.getElementById('a3007a_combo').value='';
//		document.getElementById('orgid').value='';
		odin.ext.getCmp('orgid_combo').enable();
		//Ext.query('#orgid_combo+img')[0].onclick=openDiseaseInfoCommonQueryorgid;
		return;
	}
//	odin.ext.getCmp('a3007a_combo').allowBlank = false;
	odin.ext.getCmp('a3007a_combo').enable();
	Ext.query("#a3007a_combo+img")[0].onclick=openDiseaseInfoCommonQuerya3007a;
	odin.ext.getCmp('orgid_combo').disable();
	//Ext.query('#orgid_combo+img')[0].onclick=null;
	document.getElementById('orgid').value='';
	document.getElementById('orgid_combo').value='';
//	var codeType = "orgTreeJsonData";
//	var codename = "code_name";
//    var winId = "winId"+Math.round(Math.random()*10000);
//    var label = "选择调出单位";
//	var url = "pages.sysorg.org.PublicWindow&property=abc&codetype="+codeType+"&closewin="+winId+"&codename="+codename+"&nsjg=0";
 <%-- $h.openWin(winId,url,label,270,415,null,'<%=ctxPath%>'); --%>
}

 function returnwinabc(rs){
	if(rs!=null){
		var rss = rs.split(",");
		document.getElementById('orgid').value=rss[0];
//		document.getElementById('a3007a').value=rss[1];
	}
} 

function setOrgId(){
	
}
function setParentA0163(a0163){
	realParent.radow.doEvent("setA0163Value",a0163);
}

Ext.onReady(function(){
	document.getElementById('tol1').style.width = document.body.clientWidth - 2 ;	
});
</script>

<div id="cover_wrap1"></div>