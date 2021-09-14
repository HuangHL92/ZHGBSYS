<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>


<html class="ext-strict x-viewport">
<script src="<%=request.getContextPath()%>/pages/xbrm2/js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>
<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>
<style>
.x-panel-bwrap {
	height: 100%
}

.x-panel-body {
	height: 100%
}
.busy{
	height: 406px;

}
.picOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/companyOrgImg2.png")
		!important;
}

.picInnerOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/insideOrgImg1.png")
		!important;
}

.picGroupOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/groupOrgImg1.png")
		!important;
}
.x-grid3-scroller{
overflow-y: scroll;
}
</style>
<% 		String type=(String)request.getParameter("PersonType");
		
 %>
<script type="text/javascript" src="commform/basejs/json2.js"></script>
<odin:hidden property="sh000"/>
<odin:hidden property="p_type" value="1"/>
<odin:hidden property="publish_id"/>
<odin:hidden property="title_id"/>
<odin:hidden property="titlename"/>
<odin:hidden property="personPath"/>
<script type="text/javascript">
function init(){//初始化参数
	var sh000=window.dialogArguments['param'];
	document.getElementById('sh000').value=sh000;
	document.getElementById("publish_id").value=parent.document.getElementById('publish_id').value;
	document.getElementById("title_id").value=parent.document.getElementById('title_id').value;
	document.getElementById("titlename").value=parent.document.getElementById('titlename').value;
	radow.doEvent('queryPerson',sh000);
}

</script>
<div align="center" id="addTitleContent">
<odin:groupBox property="group1" title="人员信息" >
	<table>
		<tr>
			<odin:textEdit property="a0101" label="姓名" width="100" ></odin:textEdit>
			<odin:select2 property="a0104" label="性别" codeType="GB2261" width="145" ></odin:select2>
			<odin:NewDateEditTag property="a0107" labelSpanId="a0107SpanId" maxlength="8" label="出生日期" width="145"></odin:NewDateEditTag>
		</tr>
		<tr>
			<odin:select2 property="a0141" label="政治面貌" codeType="GB4762" width="100"></odin:select2>
			<odin:textEdit property="zgxl" label="最高学历"  width="145"></odin:textEdit>
			<odin:textEdit property="zgxw" label="最高学位"  width="145"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="a0192a" label="现职" colspan="4" width="315"></odin:textEdit>
			<odin:textEdit property="tp0125" label="现职备注"  width="145" ></odin:textEdit>
		</tr>
		<%-- <tr>
			<odin:textEdit property="tp0111" label="拟任" colspan="6" width="415" readonly="true" ondblclick="openNR();"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="tp0112" label="拟免" colspan="6" width="415"  readonly="true" ondblclick="openNM();"></odin:textEdit>
		</tr>--%>
		<tr>
			<odin:select2 property="tp0121" label="拟任类型" width="100" onchange="changeNR()" codeType="NIRENTYPE"></odin:select2>
			<odin:textEdit property="tp0111" label="拟任职务"  width="145" ></odin:textEdit>
			<odin:textEdit property="tp0123" label="拟任备注"  width="145" ></odin:textEdit>
		</tr>
		<tr>
			<odin:select2 property="tp0122" label="拟免类型" width="100" onchange="changeNM()" codeType="NIMIANTYPE"></odin:select2>
			<odin:textEdit property="tp0112" label="拟免职务"  width="145" ></odin:textEdit>
			<odin:textEdit property="tp0124" label="拟免备注"  width="145" ></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="tp0113" label="任免理由" colspan="6" width="515"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="tp0114" label="备注说明" colspan="6" width="515"></odin:textEdit>
		</tr>
		<tr>
			<odin:select2  property="tp0116" label="显示现职" data="['0', '否'],['1', '是']"  width="100" value=""></odin:select2>
			<odin:select2  property="tp0117" label="显示任免表" data="['0', '否'],['1', '是']"   width="145" value=""></odin:select2>
			<odin:textEdit property="sh001" label="排序号" width="145"></odin:textEdit>
		</tr>
		
	</table>
</odin:groupBox>
</div>
<odin:toolBar property="btnToolBar">
	<odin:fill/>
	<odin:buttonForToolBar id="btn" text="保存"  icon="images/save.gif" cls="x-btn-text-icon" handler="personSave" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="addTitleContent" property="addTitlePanel" topBarId="btnToolBar"></odin:panel>


<script type="text/javascript">
function personSave(){
	if($('#sh000').val()==''){
		alert("主键sh000为空！")
		return;
	}
	radow.doEvent("personSave");
}

/* //点击提交
function sc(){
	/* var oid = document.getElementById('sh000').value;//获取p0800的值
	alert(oid);
	if (oid == null || oid =="" ) {
		odin.alert("请先选人,再提交附件！");
		return;
	}
	if($('#sh000').val()==''){
		alert("请先选人,再提交附件！")
		return;
	}
	var oid = document.getElementById('sh000').value;//获取p0800的值
	alert(oid);
	window.frames['frame'].imp(oid);
	//frame.window.imp(pid);
} */
 
function fujSave(){
	 var sh000 = document.getElementById("sh000").value;
	if (sh000 == null || sh000 =="" ) {
		odin.alert("请先保存,再提交附件！");
		return;
	} 
	$h.openWin("AppendixWin","pages.meeting.CadresPlanningAllocationAppendix", "附件列表", 760, 560, sh000,"<%=request.getContextPath()%>");
}
function download(id){
	var personPath = document.getElementById('personPath').value;
	//alert(personPath);
	window.location="ProblemDownServlet?method=downFile&prid="+personPath;
}

var g_contextpath = '<%= request.getContextPath() %>';

function openNR(){
	$h.openPageModeWin('meetingMove','pages.meeting.MeetingMove&type=1','拟任信息',580,350,'',g_contextpath);
}

function openNM(){
	$h.openPageModeWin('meetingMove','pages.meeting.MeetingMove&type=2','拟免信息',580,350,'',g_contextpath);
}

</script>
</html>