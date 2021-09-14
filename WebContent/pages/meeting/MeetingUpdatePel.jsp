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
function init(){//��ʼ������
	var sh000=window.dialogArguments['param'];
	document.getElementById('sh000').value=sh000;
	document.getElementById("publish_id").value=parent.document.getElementById('publish_id').value;
	document.getElementById("title_id").value=parent.document.getElementById('title_id').value;
	document.getElementById("titlename").value=parent.document.getElementById('titlename').value;
	radow.doEvent('queryPerson',sh000);
}

</script>
<div align="center" id="addTitleContent">
<odin:groupBox property="group1" title="��Ա��Ϣ" >
	<table>
		<tr>
			<odin:textEdit property="a0101" label="����" width="100" ></odin:textEdit>
			<odin:select2 property="a0104" label="�Ա�" codeType="GB2261" width="145" ></odin:select2>
			<odin:NewDateEditTag property="a0107" labelSpanId="a0107SpanId" maxlength="8" label="��������" width="145"></odin:NewDateEditTag>
		</tr>
		<tr>
			<odin:select2 property="a0141" label="������ò" codeType="GB4762" width="100"></odin:select2>
			<odin:textEdit property="zgxl" label="���ѧ��"  width="145"></odin:textEdit>
			<odin:textEdit property="zgxw" label="���ѧλ"  width="145"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="a0192a" label="��ְ" colspan="4" width="315"></odin:textEdit>
			<odin:textEdit property="tp0125" label="��ְ��ע"  width="145" ></odin:textEdit>
		</tr>
		<%-- <tr>
			<odin:textEdit property="tp0111" label="����" colspan="6" width="415" readonly="true" ondblclick="openNR();"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="tp0112" label="����" colspan="6" width="415"  readonly="true" ondblclick="openNM();"></odin:textEdit>
		</tr>--%>
		<tr>
			<odin:select2 property="tp0121" label="��������" width="100" onchange="changeNR()" codeType="NIRENTYPE"></odin:select2>
			<odin:textEdit property="tp0111" label="����ְ��"  width="145" ></odin:textEdit>
			<odin:textEdit property="tp0123" label="���α�ע"  width="145" ></odin:textEdit>
		</tr>
		<tr>
			<odin:select2 property="tp0122" label="��������" width="100" onchange="changeNM()" codeType="NIMIANTYPE"></odin:select2>
			<odin:textEdit property="tp0112" label="����ְ��"  width="145" ></odin:textEdit>
			<odin:textEdit property="tp0124" label="���ⱸע"  width="145" ></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="tp0113" label="��������" colspan="6" width="515"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="tp0114" label="��ע˵��" colspan="6" width="515"></odin:textEdit>
		</tr>
		<tr>
			<odin:select2  property="tp0116" label="��ʾ��ְ" data="['0', '��'],['1', '��']"  width="100" value=""></odin:select2>
			<odin:select2  property="tp0117" label="��ʾ�����" data="['0', '��'],['1', '��']"   width="145" value=""></odin:select2>
			<odin:textEdit property="sh001" label="�����" width="145"></odin:textEdit>
		</tr>
		
	</table>
</odin:groupBox>
</div>
<odin:toolBar property="btnToolBar">
	<odin:fill/>
	<odin:buttonForToolBar id="btn" text="����"  icon="images/save.gif" cls="x-btn-text-icon" handler="personSave" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="addTitleContent" property="addTitlePanel" topBarId="btnToolBar"></odin:panel>


<script type="text/javascript">
function personSave(){
	if($('#sh000').val()==''){
		alert("����sh000Ϊ�գ�")
		return;
	}
	radow.doEvent("personSave");
}

/* //����ύ
function sc(){
	/* var oid = document.getElementById('sh000').value;//��ȡp0800��ֵ
	alert(oid);
	if (oid == null || oid =="" ) {
		odin.alert("����ѡ��,���ύ������");
		return;
	}
	if($('#sh000').val()==''){
		alert("����ѡ��,���ύ������")
		return;
	}
	var oid = document.getElementById('sh000').value;//��ȡp0800��ֵ
	alert(oid);
	window.frames['frame'].imp(oid);
	//frame.window.imp(pid);
} */
 
function fujSave(){
	 var sh000 = document.getElementById("sh000").value;
	if (sh000 == null || sh000 =="" ) {
		odin.alert("���ȱ���,���ύ������");
		return;
	} 
	$h.openWin("AppendixWin","pages.meeting.CadresPlanningAllocationAppendix", "�����б�", 760, 560, sh000,"<%=request.getContextPath()%>");
}
function download(id){
	var personPath = document.getElementById('personPath').value;
	//alert(personPath);
	window.location="ProblemDownServlet?method=downFile&prid="+personPath;
}

var g_contextpath = '<%= request.getContextPath() %>';

function openNR(){
	$h.openPageModeWin('meetingMove','pages.meeting.MeetingMove&type=1','������Ϣ',580,350,'',g_contextpath);
}

function openNM(){
	$h.openPageModeWin('meetingMove','pages.meeting.MeetingMove&type=2','������Ϣ',580,350,'',g_contextpath);
}

</script>
</html>