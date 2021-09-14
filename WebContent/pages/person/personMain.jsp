<%@page import="com.insigma.odin.framework.sys.manager.sysmanager.comm.entity.Sysopright"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.Pagedata"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.lbs.cp.util.SysManagerUtil"%>
<%@page	import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@page	import="com.insigma.odin.framework.util.commform.BuildUtil.ItemValue"%>
<%@page import="javax.servlet.http.HttpSession"%>	
<%@page import ="java.util.*"%>
<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/bootstrap-3.3.7-dist/css/bootstrap.min.css">
 --%><script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js">
</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<%-- <script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery-2.1.4.js"></script> --%>
<%-- <script type="text/javascript"
	src="<%=request.getContextPath()%>/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script> --%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/xbrm/jquery-ui.min.css">
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery-ui1.10.4.min.js"></script>
<script type="text/javascript">
	
</script>
<style>
body{
margin: 1px;overflow: auto;
font-family:'宋体',Simsun;
word-break:break-all;
}
.ui-tabs .ui-tabs-panel{padding: 0px;padding-left: 3px;}
.ui-helper-reset{font-size: 12px;}

.GBx-fieldset .x-fieldset{padding-bottom: 0px;margin-bottom: -12px;margin-top: 12px}

.GBx-fieldset .x-fieldset-body{overflow-y: auto;}



.marginbottom0px .x-form-item{margin-bottom: 0px;}

.marginbottom0px table,.marginbottom0px table tr th,.marginbottom0px table tr td
{ border:1px solid #74A6CC; padding: 5px; border-right-width: 0px;  }

.marginbottom0px table,
{line-height: 25px; text-align: center; border-collapse: collapse;border-right-width: 1px; }
</style>
<div>
<odin:hidden property="a0000" title="人员id"></odin:hidden>
<script type="text/javascript">
Ext.onReady(function() {
	var a0000 = document.getElementById("subWinIdBussessId2").value;
	document.getElementById("a0000").value = a0000;
});
</script>
	<div id="tabs">
	  	<ul id="ulTitle" style="_width:100%;" style="display:none;">
		  	<li><a href="#tab1">基本信息</a></li>
		  	<li><a href="#tab2">工作单位</a></li>
		    <li><a href="#tab3">现职务层次</a></li>
		    <li><a href="#tab4">现职级</a></li>
		    <li><a href="#tab5">专业技术</a></li>
		    <li><a href="#tab6">学历学位</a></li>
		    <li><a href="#tab7">奖惩</a></li>
		    <li><a href="#tab8">年度考核</a></li>
		    <li><a href="#tab9">家庭成员</a></li>
		    <li><a href="#tab10">进入管理</a></li>
		    <li><a href="#tab11">公务员登记</a></li>
		    <li><a href="#tab12">退出管理</a></li>
		    <li><a href="#tab13">住址通讯</a></li>
		    <li><a href="#tab14">考试录用</a></li>
		    <li><a href="#tab15">选调生</a></li>
		    <li><a href="#tab16">公开遴选</a></li>
		    <li><a href="#tab17">公开选调</a></li>
		    <!-- <li><a href="#tab18">考试</a></li> -->
		    <li><a href="#tab19">培训</a></li>
	  </ul>
  	<div class="GBx-fieldset" id="tab1" style="display:none;">
		<iframe src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.BaseAddPageGB" id='BaseAddPage_GB' width="965" height="650"></iframe>
	</div>
	<div class="GBx-fieldset" id="tab2">
		<iframe src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.WorkUnitsAddPageGB" id='WorkUnitsAddPage_GB' width="965" height="650"></iframe>
	</div>
	<div class="GBx-fieldset" id="tab3">
		<iframe src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.RankAddPageGB" id='RankAddPage_GB' width="965" height="650"></iframe>
	</div>
	<div class="GBx-fieldset" id="tab4">
		<iframe src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.RradeRankAddPageGB" id='RradeRankAddPage_GB' width="965" height="650"></iframe>
	</div>
	<div class="GBx-fieldset" id="tab5">
		<iframe src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.ProfessSkillAddPageGB" id='ProfessSkillAddPage_GB' width="965" height="650"></iframe>
	</div>
	<div class="GBx-fieldset" id="tab6">
		<iframe src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.DegreesAddPageGB" id='DegreesAddPage_GB' width=965 height="650"></iframe>
	</div>
	<div class="GBx-fieldset" id="tab7">
		<iframe src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.RewardPunishAddPageGB" id='RewardPunishAddPage_GB' width=965 height="650"></iframe>
	</div>
	<div class="GBx-fieldset" id="tab8">
	<iframe src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AssessmentInfoAddPageGB" id='AssessmentInfoAddPage_GB' width=965 height="650"></iframe>
	</div>
	<div class="GBx-fieldset" id="tab9">
	<iframe src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.FamilyAddPageGB" id='FamilyAddPage_GB' width=965 height="650"></iframe>
	</div>
	<div class="GBx-fieldset" id="tab10">
		<iframe src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.EnterAddPageGB" id='EnterAddPage_GB' width=965 height="650"></iframe>
	</div>
	<div class="GBx-fieldset" id="tab11">
		<iframe src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.RegisterAddPageGB" id='RegisterAddPage_GB' width=965 height="650"></iframe>
	</div>
	<div class="GBx-fieldset" id="tab12">
		<iframe src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.ExitAddPageGB" id='ExitAddPage_GB' width=965 height="650"></iframe>
	</div>
	<div class="GBx-fieldset" id="tab13">
		<iframe src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddressAddPageGB" id='AddressAddPage_GB' width=965 height="650"></iframe>
	</div>
	<div class="GBx-fieldset" id="tab14">
		<iframe src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.ExaminationsAddPageGB" id='ExaminationsAddPage_GB' width=965 height="650"></iframe>
	</div>
	<div class="GBx-fieldset" id="tab15">
		<iframe src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.SelectPersonAddPageGB" id='SelectPersonAddPage_GB' width=965 height="650"></iframe>
	</div>
	<div class="GBx-fieldset" id="tab16">
		<iframe src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OpenSelectAddPageGB" id='OpenSelectAddPage_GB' width=965 height="650"></iframe>
	</div>
	<div class="GBx-fieldset" id="tab17">
		<iframe src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OpenTransferringAddPageGB" id='OpenTransferringAddPage_GB' width=965 height="650"></iframe>
	</div>
	<%-- <div class="GBx-fieldset" id="tab18">
		<iframe src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.ExamAddPageGB" id='ExamAddPage_GB' width=965 height="650"></iframe>
	</div> --%>
	<div class="GBx-fieldset" id="tab19">
		<iframe src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.TrainAddPageGB" id='TrainAddPage_GB' width=965 height="650"></iframe>
	</div>
	</div>
</div>
	
<script type="text/javascript">
Ext.onReady(function(){
	$(function() {
	    $( "#tabs" ).tabs();
	});
});
Ext.onReady(function() {
	$("#ulTitle").show();
	$("#tab1").show();
});
function a(){
	document.getElementById("BaseAddPage_GB").contentWindow.savePerson();
}
</script>
