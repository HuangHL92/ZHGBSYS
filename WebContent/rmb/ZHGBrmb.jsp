<%@page import="com.utils.DBUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%@page import="com.insigma.siis.local.business.entity.A36"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib  uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page isELIgnored="false" %>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.TableColInterface"%>
<%@include file="rmbZHGBServer.jsp" %>
<%
	//session.setAttribute("a1701FirstValue", SV(a01.getA1701()));
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
	<script src="jquery-1.7.2.min.js"> </script>

    <link href="css/main.css" rel="stylesheet">
    <title>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</title>
    <odin:head></odin:head>
    <odin:base></odin:base>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/shared/examples.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/shared/examples.js"></script>
    <script type="text/javascript" src="<%=ctxPath%>/basejs/helperUtil.js"></script>
    <script src="js/common.js"></script>
    <script type="text/javascript" charset="gbk" src="<%=ctxPath%>/ueditor/third-party/zeroclipboard/ZeroClipboard.min.js"></script>
    <script type="text/javascript" charset="gbk" src="<%=ctxPath%>/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="gbk" src="<%=ctxPath%>/ueditor/ueditor.all.js"> </script>
<!--�����ֶ��������ԣ�������ie����ʱ��Ϊ��������ʧ�ܵ��±༭������ʧ��-->
<!--������ص������ļ��Ḳ������������Ŀ����ӵ��������ͣ���������������Ŀ�����õ���Ӣ�ģ�������ص����ģ�������������-->
<script type="text/javascript" charset="gbk" src="<%=ctxPath%>/ueditor/lang/zh-cn/zh-cn.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/xbrm/jquery-ui.min.css">
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery-ui1.10.4.min.js"></script>
<style type="text/css">
.ui-tabs-vertical { width: 100%;height: 100%; }
.ui-tabs-vertical .ui-tabs-nav { padding: 1px,0px,1px,1px;; float: left; width: 150px;height: 100%; }
.ui-tabs-vertical .ui-tabs-nav li { clear: left; width: 100%; border-bottom-width: 1px !important; border-right-width: 1 !important; margin: 0 -1px 2px 0; }
.ui-tabs-vertical .ui-tabs-nav li a { display:block;width: 100%; }
.ui-tabs-vertical .ui-tabs-nav li.ui-tabs-active { padding-bottom: 0;   border-right-color:rgb(214,227,243)  !important; }
.ui-tabs-vertical .ui-tabs-nav li.ui-state-active a,.ui-tabs-vertical .ui-tabs-nav li.ui-state-active{background-color: rgb(214,227,243);}
.ui-tabs-vertical .ui-tabs-panel { padding: 20px; /* float: right; */ width:100%;}
.ui-tabs .ui-tabs-nav LI.ui-tabs-active{margin-bottom: 2px;}
.ui-tabs .ui-tabs-panel{padding: 0px;}
.ui-tabs{padding: 0px;background-color: rgb(214,227,243);}
.ui-helper-reset{font-size: 12px;}
.ui-widget-header{background-image: none;border-right-width: 0px;}
.ui-widget-content{background-image: none;}
.ui-widget INPUT{ font-family: tahoma, arial, helvetica, sans-serif;font-size: 12px;}
.others-info-title{width:490px!important;font-size: 14px!important;text-align: left!important;font-weight: bold;}
#tabs-0{display: block;}
.tabs-0{position: absolute;left: -1000}
.bgclor{
	color:black !important;
}
#a99z191 td{
	border-left:0px !important;
}
input[disabled]{
	border: 1px solid #DDD;
	background-color: #0e90d2 !important;
	color:red !important;
}
.remark tr{
	width:70px;
}
.remark textarea{
	width:500px;
}
#bzxxx textarea{
	width:400px;
}
.x-combo-list-inner{
text-align: left;
}
#a0194c td{
	border-left:0px !important;
}
input[disabled]{
	border: 1px solid #DDD;
	background-color: #0e90d2 !important;
	color:red !important;
	height:100px;
}
</style>

</head>

<body id="ZHGBrmb" style="width:100%;height:632px;overflow:hidden; border: 0px;margin: 0px;padding: 0px; visibility: hidden;">


<div id="rmbButton" style="width: 278px;position: absolute;z-index: 1; top: 555px;left: 760px;">
	<%
	if(!"look".equals(sign)){
	%>
	<div style="width:90%;height:40px;margin-top:10px;">
	<%
		if (fromModules == null || "".equals(fromModules)){
	%>
		<div class="top_btn_style" id="previous_person"  onclick="showNext('-1')">��һ��</div>
		<div class="top_btn_style" id="next_person"  onclick="showNext('1')" >��һ��</div>
	<%
		}
	%>
		<div class="top_btn_style" style="width:50px;" onclick="prtRmb_new()" id="prtRmb">Ԥ��</div>
		<div class="top_btn_style" style="width:100px;display: none;" onclick="prtTG()" id="prtTG">��ӡ�׸ı�</div>
		<!-- <div class="top_btn_style" style="width:50px;" onclick="PRmb()" id="ylRmb" >��ӡ</div> -->
	</div>

	<%
		if (fromModules == null || "".equals(fromModules)){
	%>
	<div style="width:100%;height:70px; ">
		<!-- <div class="top_btn_style" style="background-color:#3680C9;margin-left:13%;" onclick="peoplePortrait()">��Ա����</div>
		<!-- <div id="continueAdd" class="top_btn_style" style="background-color:#3680C9;margin-left:6px;width:100px;" onclick="addnew()">��������</div> ajaxSubmit('saveAdt');-->
		<div id="continueAdd" class="top_btn_style" style="background-color:#3680C9;margin-left:6px;width:80px;" onclick="peoplePortrait();">�ɲ�����</div>
		<div class="top_btn_style" style="background-color:#F08000;margin-left:10px;" onclick="savePerson()" >��&nbsp;&nbsp;��</div>
	</div>
	<%
		}
	}else{
	%>
	<div style="width:90%;height:40px;margin-top:10px;">
			<div class="top_btn_style" id="previous_person"  onclick="showNext('-1')">��һ��</div>
		<div class="top_btn_style" id="next_person" onclick="showNext('1')" >��һ��</div>
				<div class="top_btn_style" style="width:100px;" onclick="prtRmb_new()" id="prtRmb">Ԥ�������</div>
		<div class="top_btn_style" style="width:100px;display: none;" onclick="prtTG()" id="prtTG">��ӡ�׸ı�</div>
		<!-- <div class="top_btn_style" style="width:100px;" onclick="PRmb()" id="ylRmb">��ӡ�����</div> -->
			</div>
	<%} %>
</div>


<div id="tabs" >
	<odin:hidden property="pdfPath1"/>
  <ul id="ulTitle" style="display: none;">

  	<li  ><a href="#tabs-0" tabname="rmb" >�������</a></li>
  	<%-- <li><a href="#tabs-13" tabname="AttributeAddPage_GB" winid="AttributeAddPage_GB" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AttributeAddPageGB">
    		�ɲ�����</a></li> --%>
    <%-- <li  ><a href="#tabs-1" tabname="EnterAddPage_GB" winid="EnterAddPage_GB" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.EnterAddPageGB" >
    		�������</a></li> --%>
    <%-- <li  ><a href="#tabs-2" winid="RegisterAddPage_GB" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.RegisterAddPageGB" >
    		����Ա�Ǽ�</a></li> --%>

    <%-- <li  ><a href="#tabs-3" winid="ExitAddPage_GB" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.ExitAddPageGB">
    		�˳�����</a></li>
    <li  ><a href="#tabs-11" winid="LiTui_GB" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.LiTuiAddPageGB">
    		���˹���</a></li>
    <li  ><a href="#tabs-4" winid="AddressAddPage_GB" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddressAddPageGB" >
    		סַͨѶ</a></li> --%>

    <%-- <li><a href="#tabs-5" tabname="ExaminationsAddPage_GB" winid="ExaminationsAddPage_GB" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.ExaminationsAddPageGB" >
    		����¼��</a></li>
    <li><a href="#tabs-6" tabname="SelectPersonAddPage_GB" winid="SelectPersonAddPage_GB" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.SelectPersonAddPageGB">
    		ѡ����</a></li>
    <li><a href="#tabs-7" tabname="OpenSelectAddPage_GB" winid="OpenSelectAddPage_GB" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OpenSelectAddPageGB">
    		������ѡ</a></li>
    <li><a href="#tabs-8" tabname="OpenTransferringAddPage_GB" winid="OpenTransferringAddPage_GB" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OpenTransferringAddPageGB">
    		����ѡ��</a></li> --%>
    <li><a href="#tabs-9" tabname="TrainAddPage_GB" winid="TrainAddPage_GB" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.TrainAddPageGB&sign=<%=sign %>">
    		��ѵ</a></li>
    <%-- <li><a href="#tabs-10" tabname="NiRenMian_GB" winid="NiRenMian_GB" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.NiRenMianAddPageGB">
    		������</a></li> --%>
    <li><a href="#tabs-12" tabname="JdInformationAddPage_GB" winid="JdInformationAddPage_GB" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.JdInformationAddPageGB&a0000=<%=URLEncoder.encode(a0000,"UTF8") %>">
    		�ල��Ϣ</a></li>

    <li  ><a href="#tabs-3" tabname="ExitAddPage_GB" winid="ExitAddPage_GB" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.ExitAddPageGB&sign=<%=sign %>">
    		�˳�����</a></li>
<%--     <li  ><a href="#tabs-11" tabname="LiTui_GB" winid="LiTui_GB" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.LiTuiAddPageGB&sign=<%=sign %>">
    		���˹���</a></li> --%>
    <%-- <li  ><a href="#tabs-4" tabname="AddressAddPage_GB" winid="AddressAddPage_GB" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddressAddPageGB&sign=<%=sign %>" >
    		סַͨѶ</a></li> --%>
   <%--  <li  ><a href="#tabs-15" tabname="RankByRules_GB" winid="RankByRules_GB" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.RankByRulesGB&sign=<%=sign %>" >
    		��ת��ְ��</a></li> --%>
    <%-- <li><a href="#tabs-20" tabname="Multimedia_GB" winid="Multimedia_GB" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.MultimediaGB&sign=<%=sign %>">
    		��ý����Ϣ</a></li> --%>
    <!--���ݿͻ�Ҫ��ֻ����Ȩ�� ��ͥ��Ա ���û�������ʾ��ҳ  -->
    <%-- <%if(familySign){
    %>
    <li><a href="#tabs-21" tabname="FamilyMembers_GB" winid="FamilyMembers_GB" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.FamilyMembersGB">
    		��ͥ��Ա</a></li>
    <%
    }
    %> --%>

    <li><a href="#tabs-14" tabname="LogManger_GB" winid="LogManger_GB" winsrc="<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.LogManger">������¼</a></li>

	<%-- <li><a href="#tabs-22" tabname="GBRS_GB" winid="GBRS_GB" winsrc="<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.GBRS">�ɲ�����</a></li> --%>
	
	
    <li id="tabs23hid"><a href="#tabs-23" tabname="A0196TagsAddPage" winid="A0196TagsAddPage" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.A0196TagsAddPage&sign=<%=sign%>&a0000=<%=a0000%>">
    		�ɲ�����</a></li>
    <%-- <li id="tabs24hid"><a href="#tabs-24" tabname="AddJianLiAddPage" winid="AddJianLiAddPage" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.customquery.person.AddJianLiAddPage">
    		�������</a></li>
     <li id="tabs25hid"><a href="#tabs-25" tabname="ZZBQTags" winid="ZZBQTags" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.ZZBQTags&sign=<%=sign%>&a0000=<%=a0000%>">
    		��֯��ǩ</a></li>		 --%>
    <!--  <li  ><a href="#tabs-33" tabname="rmbqt" >������Ϣ</a></li> -->
    <%--  <li ><a href="#tabs-33" tabname="rmbqt" winid="rmbqt" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.Qita&sign=<%=sign%>&a0000=<%=a0000%>">
    		������Ϣ</a></li> --%>
    <li ><a href="#tabs-34" tabname="PersontiveFile" winid="PersontiveFile" winsrc="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.PersontiveFile&sign=<%=sign%>&a0000=<%=a0000%>&checkedgroupid=<%=checkedgroupid%>">
    		��ز���</a></li>
    
  </ul>
  <%-- <div id="tabs-33" class="GBx-fieldset" style="text-align: left;">
  <form action="" id="rmbform" name="rmbform" style="width:865px;height:430px;overflow: hidden;  ">
<input type="hidden"  name="tabIndex" value="1" id="tabIndex" alt="tabҳ��">
	<div class="main_div">

		<div class="inner_div left_div">
  
			<div class="top_div">
				<div style="width:100%;height:100%;">
					<!-- <div id="tabs1" class="top_tab_style active" onclick="tabSwitch('tabs','page',1)">����������(һ)</div>
					<div id="tabs2" class="top_tab_style" onclick="tabSwitch('tabs','page',2)">����������(��)</div>  -->
 				    <!-- <div id="tabs4" class="top_tab_style" style="width:70px;" onclick="tabSwitch('tabs','page',4)">������Ϣ</div>  -->

 					<div id="tabs3" class="top_tab_style" style="width:70px;" onclick="tabSwitch('tabs','page',3)">��ǩ��Ϣ</div>
 					 <!-- <div id="tabs6" class="top_tab_style" style="width:70px;" onclick="tabSwitch('tabs','page',6)">����ҵ��</div>   -->
				</div>
			</div>
	<div class="hzdbInfo1">
			
			
			<script>

			
			$("#page2 input[type='checkbox']").click(function () {
			    if(this.checked){
			        $(this).attr('value','1')
			    }else {
			    	$(this).attr('value','0')
			    }
			});
			</script>
			<script>
				Ext.onReady(function(){
					//tabSwitch('tabs','page',1);
					//����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ����ά��
					$h.fieldsDisabled(fieldsDisabled);
					//����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ���Բ鿴
					var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
					$h.selectDisabled(selectDisabled,imgdata);
				});
			</script>
			
			
			<div class="top_div" style="display: none;">
				<div style="width:100%;height:100%;">

 					<div id="tabs3tab" class="top_tab_style active" style="width:70px;" onclick="tabSwitch('tabs','page',1)">��ǩ��Ϣ</div>
				</div>
			</div>
			<div class="resume_div" id="page3" style="display:none;">
				<table class="remark tdtextclass" cellpadding="0" style="position: relative;">
					<!-- ������Ҫְ����Ҫ����  -->
					<tr>
						<td class="width-65 height-70 fontConfig label_color">
							<div class="width-65 height-69 fontConfig label_color middle-css">������<br/>Ҫְ��<br/>��Ҫ��<br/>����־</div>
						</td>
						<td class="height-70" colspan="6">
							<textarea id="a0193z" name="a0193z" rows="8" cols="6" class="height-70 maxWidth-500 font-left" readonly="readonly"
								ondblclick="$h.openPageModeWin('A0193ZTags','pages.zj.tags.A0193TagsAddPage','������Ҫְ����Ҫ������־',780,580,document.getElementById('a0000').value,ctxPath,null,{maximizable:false,resizable:false})" ><%=SV(extraTags.getA0193z()) %></textarea>
						</td>
					</tr>
					<!-- ��Ϥ����  -->
					<tr>
						<td class="width-65 height-70 fontConfig label_color">��&nbsp;Ϥ<br/>��&nbsp;��</td>
						<td class="height-70" colspan="6">
							<textarea id="a0194z" name="a0194z" rows="3" cols="6" class="height-70 maxWidth-500 font-left" readonly="readonly"
								ondblclick="$h.openPageModeWin('A0194ZTags','pages.zj.tags.A0194TagsAddPage','��Ϥ����',780,550,document.getElementById('a0000').value,ctxPath,null,{maximizable:false,resizable:false})" ><%=SV(extraTags.getA0194z()) %></textarea>
						</td>
					</tr>
					
					<!-- �Ƿ���ʡ�������Ͻ���  -->
					<tr>
						<td class="width-65 height-70 fontConfig label_color">�Ƿ���<br/>ʡ������<br/>�Ͻ���</td>
						<td class="height-70" colspan="6">
							<textarea id="tagsbjysjlzs" name="tagsbjysjlzs" rows="3" cols="6" class="height-70 maxWidth-500 font-left" readonly="readonly"
								ondblclick="$h.openPageModeWin('TagSbjysjlAddPage','pages.zj.tags.TagSbjysjlAddPage','ʡ�������Ͻ���',780,360,document.getElementById('a0000').value,ctxPath,null,{maximizable:false,resizable:false})" ><%=SV(extraTags.getTagsbjysjlzs()) %></textarea>
						</td>
					</tr>
					<!-- �˲�����  -->
					<tr>
						<td class="width-65 height-70 fontConfig label_color">�˲�����</td>
						<td class="height-70" colspan="6">
							<textarea id="tagrclxzs" name="tagrclxzs" rows="3" cols="6" class="height-70 maxWidth-500 font-left" readonly="readonly"
								ondblclick="$h.openPageModeWin('TagRclxAddPage','pages.zj.tags.TagRclxAddPage','�˲�����',800,330,document.getElementById('a0000').value,ctxPath,null,{maximizable:false,resizable:false})" ><%=SV(extraTags.getTagrclxzs()) %></textarea>
						</td>
					</tr>
					<!-- �ͽ�����  -->
					<tr>
						<td class="width-65 height-70 fontConfig label_color">�ͽ�����</td>
						<td class="height-70" colspan="6">
							<textarea id="tagcjlxzs" name="tagcjlxzs" rows="3" cols="6" class="height-70 maxWidth-500 font-left" readonly="readonly"
								ondblclick="$h.openPageModeWin('TagCjlxAddPage','pages.zj.tags.TagCjlxAddPage','�ͽ�����',800,330,document.getElementById('a0000').value,ctxPath,null,{maximizable:false,resizable:false})" ><%=SV(extraTags.getTagcjlxzs()) %></textarea>
						</td>
					</tr>
					<!-- �ɲ���Դ -->
					 <tr style="height:50px;">
					 		<td  class=" height-80 fontConfig label_color">��&nbsp;��<br/>��&nbsp;Դ</td>
							<odin:select2 property="a0194c" value="<%=SV(extraTags.getA0194c()) %>" size="20" colspan="2"  style= "height:50px"
							data="['1','����λ����'],['2','��ֱ���ؽ���'],['3','�����н���'],['4','��ʦְ��ת'],['5','����']"></odin:select2>
					</tr> 
				</table>
			</div>
			<div class="top_div" >
				<div style="width:100%;height:100%;">

 					<div id="tabs4tab" class="top_tab_style active" style="width:70px;" onclick="tabSwitch('tabs','page',1)">������Ϣ</div>
				</div>
			</div>
			<div class="resume_div" id="page4" style="display:block;">
				<table class="remark tdtextclass" cellpadding="0px;" style="position: relative;">

						<!-- ҳ���޸� -->
						<tr>
							<td class="width-60 fontConfig label_color" style="height:240px;">��<br/>��<br/>��<br/>Ϣ</td>
							<td style="height:230px;width:490px;">

								<div style="height:100%;width:100%;">


										<table align="center" bgcolor="white" width="100%" height="100%" cellspacing="0px;" style='table-layout: fixed;'>




												<tr style="height:50px;">
												<td>
													<table id="a99z191" align="left" bgcolor="white" width="90" height="100%" cellspacing="0px;">
													  <tr style="height:50px;">
														<odin:select property="a99z191" codeType="XZ91" label="��������" defaultValue="<%=SV(a99Z1.getA99z191()) %>" colspan="1"  styleClass="a99z191"></odin:select>
													  </tr>
													</table>
												</td>
													<td class="right_td1" align="center" style="padding-left:3%;width:47%;border-top:0px">
														<div style="width:100%;height:50px;line-height:50px;">
														<div style="float:left;width:60px;text-align:left;"><span style="font-size: 12px;">&nbsp;&nbsp;��ϵ��ʽ</span></div>
														<input type="text" name="a99z195" id="a99z195" value="<%=SV(a99Z1.getA99z195()) %>" class="right_input_btn" maxlength="18" style="margin-top:16px;width:150px;" >
														</div>
													</td>

												</tr>
												
												<tr style="height:50px;">
												<td>
													<table   align="left" bgcolor="white" width="90" height="100%" cellspacing="0px;">
													  <tr style="height:50px;">
														<odin:select2 property="a99z1301" codeType="PY02" width="140"  label="��������" multiSelect="true"  value="<%=a99Z1.getA99z1301() %>" colspan="1"  styleClass="a99z191"/>
													  </tr>
													</table>
												</td>
												<td>
													<table   align="left" bgcolor="white" width="90" height="100%" cellspacing="0px;">
													  <tr style="height:50px;">
														<odin:select property="a99z1302" codeType="PY01" width="120" label="������ʽ" multiSelect="true" value="<%=a99Z1.getA99z1302() %>" colspan="1"  styleClass="a99z191"></odin:select>
													  </tr>
													</table>
													<script type="text/javascript">
														Ext.onReady(function(){
															Ext.getCmp('a99z1301_combo').on('select',function(obj){
																$('#a99z1301').val(obj.getCheckedValue())
															});
															Ext.getCmp('a99z1302_combo').on('select',function(obj){
																$('#a99z1302').val(obj.getCheckedValue())
															});
														});
													
													</script>
												</td>

												</tr>
												<tr>
													<td>
													<table   >
													  <tr id="bzxxx" style="height:30px;">
														<odin:textarea property="a99z1303"   label="��������" value="<%=a99Z1.getA99z1303()  %>"     />
													  </tr>
													</table>
												</td>
												</tr>
												<tr>
													<td>
													<table   >
													  <tr id="bzxxx" style="height:30px;">
														<odin:textarea property="a99z1304"   label="��Ҫ����" value="<%=a99Z1.getA99z1304()  %>"     />
													  </tr>
													</table>
												</td>
												</tr>
											</table>
								</div>
							</td>
						</tr>

						<tr>
							<td class="width-60 fontConfig label_color" style="height:99px;">��ע</td>
							<td style="height:79px;width:490px;">
								<textarea id="a0180" name="a0180" rows="3" cols="6" label="��ע" style="width:100%;height:100%"><%=SV(a01.getA0180()) %></textarea>
							</td>
						</tr>


				</table>
			</div>
			

<!-- ʡ���ǩpage6 -->
			<div class="top_div" >
				<div style="width:100%;height:100%;">

 					<div id="tabs6tab" class="top_tab_style active" style="width:70px;" onclick="tabSwitch('tabs','page',1)">����ҵ��</div>
				</div>
			</div>
			<div class="resume_div" id="page6" style="display:block;">
				<table class=" tdtextclass" cellpadding="0px;" style="position: relative;">
					<tbody >
					<!-- ������Ҫְ����Ҫ����  -->
					<tr>
						<td  class="sz-label height-80 fontConfig label_color">����(������)��Ҫְ����Ҫ������־</td>
						<td class="height-76" colspan="6">
							<textarea id="sza0193z" name="sza0193z" rows="3" cols="6" class="editorArea font-left "  readonly="readonly" ondblclick="rzzyjlClick();" ><%=SV(szextraTags.getA0193z()) %></textarea>
						</td>
					</tr>
					<!-- ��Ϥ����  -->
					<tr>
						<td  class=" height-80 fontConfig label_color">��&nbsp;Ϥ<br/>��&nbsp;��</td>
						<td class="height-40" colspan="6">
							<textarea id="sza0194z" name="sza0194z" rows="3" cols="6" class="editorArea font-left" readonly="readonly" ondblclick="sxlyClick();" ><%=SV(szextraTags.getA0194z()) %></textarea>
						</td>
					</tr>
					<!-- ��Ϥ���� ��ע -->
					<tr>
						<td  class=" height-80 fontConfig label_color">��Ϥ��<br/>��ע</td>
						<td class="height-40"  colspan="6">
							<textarea id="sza0194c" name="sza0194c" rows="3" cols="6" class="editorArea font-left"><%=SV(szextraTags.getA0194c()) %></textarea>
						</td>
					</tr>
					</tbody>
				</table>
			</div>

 </div>
		</div>
		
	</div>
	<input type="hidden" value="<%=a0000 %>" name="a0000" id="a0000" alt="��Աid">
	<input type="hidden" value="<%=checkedgroupid %>" name="checkedgroupid" id="checkedgroupid" alt="��Աid">
	<input type="hidden" value="<%=SV(a01.getA0141()) %>" name="a0141" id="a0141" alt="������ò">
	<input type="hidden" value="<%=SV(a01.getA3921()) %>" name="a3921" id="a3921" alt="�ڶ�����">
	<input type="hidden" value="<%=SV(a01.getA3927()) %>" name="a3927" id="a3927" alt="��������">
	<input type="hidden" value="<%=SV(a01.getA0144()) %>" name="a0144" id="a0144" alt="�뵳ʱ��">
	<input type="hidden" value="<%=SV(a01.getA0192()) %>" name="a0192" id="a0192" alt="�ֹ�����λ��ְ����">
	<input type="hidden" value="<%=SV(a01.getA0163()) %>" name="a0163" id="a0163" alt="��Ա����״̬">
	<!-- 1�����޸�Ȩ�ޣ�2�����޸�Ȩ�� -->
	<input type="hidden" value="1" name="isUpdate" id="isUpdate" alt="�Ƿ����޸�Ȩ��">
	<input type="hidden" value="<%=SV(a01.getA0148()) %>" name="a0148" id="a0148" alt="ְ��㼶">
	<input type="hidden" value="<%=SV(a01.getA0221()) %>" name="a0221" id="a0221" alt="��ְ����">
	<input type="hidden" value="<%=SV(a01.getA0192e()) %>" name="a0192e" id="a0192e" alt="��ְ��">
	<input type="hidden" value="<%=SV(a01.getA1701()) %>" name="a1701" id="a1701" alt="����">
	<input type="hidden" value="<%=SV(a99Z1.getA99Z100()) %>" name="a99Z100" id="a99Z100" alt="id">
	<input type="hidden" id="pdfPath" alt="pdf·��">
	<input type="hidden" id="familyRowNum" alt="��ͥ��Ա���к�">
	<input type="hidden" value="<%=SV(extraTags.getA0194zcode()) %>" name="a0194zcode" id="a0194zcode" alt="��Ϥ�������">
	<input type="hidden" value="<%=SV(extraTags.getA0196zcode()) %>" name="a0196zcode" id="a0196zcode" alt="רҵ���ʹ���">
	<input type="hidden" value="<%=SV(a99Z1.getA99z101()) %>" name="a99z101F" id="a99z101F" alt="�Ƿ�¼">
	<input type="hidden" value="<%=SV(a99Z1.getA99z103()) %>" name="a99z103F" id="a99z103F" alt="�Ƿ�ѡ����">
	<input type="hidden" value="<%=SV(a01.getFkbs()) %>" name="fkbs" id="fkbs" alt="�ֿ��ʶ">
	<input type="hidden" value="<%=SV(a01.getFkly()) %>" name="fkly" id="fkly" alt="�ֿ���Դ">
</form>
</div> --%>
  
  
  
   
	      <div id="tabs-0" class="GBx-fieldset" style="text-align: left;">
<form action="" id="rmbform" name="rmbform" style="width:865px;height:630px;overflow: hidden;  ">
<input type="hidden"  name="tabIndex" value="1" id="tabIndex" alt="tabҳ��">
	<div class="main_div">

		<div class="inner_div left_div">
  
			<div class="top_div">
				<div style="width:100%;height:100%;">
					<div id="tabs1" class="top_tab_style active" onclick="tabSwitch('tabs','page',1)">����������(һ)</div>
					<div id="tabs2" class="top_tab_style" onclick="tabSwitch('tabs','page',2)">����������(��)</div> 
 				    <div id="tabs4" class="top_tab_style" style="width:70px;" onclick="tabSwitch('tabs','page',4)">������Ϣ</div> 

 					<%-- <div id="tabs3" class="top_tab_style" style="width:70px;" onclick="tabSwitch('tabs','page',3)">��ǩ��Ϣ</div> --%>
 					 <div id="tabs6" class="top_tab_style" style="width:70px;" onclick="tabSwitch('tabs','page',6)">����ҵ��</div>  
				</div>
			</div>
	<div class="hzdbInfo">
			<div class="resume_div" id="page1"  >
				<table class="person_resume"  cellpadding="0px;" style="position: relative;">
					<tbody >
						<tr>
							<td class="width-60 height-40 fontConfig label_color"><font color="red">*</font>��&nbsp;��</td>
							<td class="width-80 height-40">
								<tags:rmbNormalInput property="a0101" offsetTop="1" cls="width-80 height-40 no-y-scroll" label="����"   defaultValue="<%=SV(a01.getA0101()) %>"
title="�����������ܳ���18----" textareaStyle="display:none;text-align:center;" textareaCls="cellbgclor x-form-field"
onkeypress="namevalidator" type="true"/>
							</td>
							<td class="width-60 height-40 fontConfig label_color"><font color="red">*</font>��&nbsp;��</td>
							<td class="width-80 height-40 ">
								<tags:rmbSelect property="a0104" cls="height-40 input-text2" label="�Ա�" selectTDStyle="width:80px;" selectDivStyle="width:300px;"
textareaStyle="width:80px;line-height:40px !important;" codetype="GB2261" defaultValue="<%=SV(a01.getA0104()) %>"/>
							</td>
							<td class="width-60 height-40 fontConfig label_color"><font color="red">*</font>��&nbsp;��<br/>&nbsp;��&nbsp;��</td>
							<td class="width-80 height-40 ">
								<tags:rmbDateInput type="true" property="a0107" offsetTop="1" cls="width-80 height-40 no-y-scroll" label="��������"   onblur="validateYearM()"  defaultValue="<%=SV(a01.getA0107()) %>"
 textareaStyle="display:none;text-align:center;"  textareaCls="cellbgclor x-form-field"/>
							</td>
							<td class="width-110 height-140" rowspan="4">
								<img alt="��Ƭ" id="personImg" style="cursor:pointer;" onclick="showdialog()" width="120" height="100%" src="<%= request.getContextPath()%>/servlet/DownloadUserHeadImage?a0000=<%=URLEncoder.encode(URLEncoder.encode(a01.getA0000(),"UTF-8"),"UTF-8")%>">
							</td>

						</tr>
						<tr>
							<td class="width-60 height-40 fontConfig label_color" ><font color="red">*</font>��&nbsp;��</td>
							<td class="width-80 height-40 ">
								<tags:rmbSelect property="a0117" cls="height-40 input-text2" label="����"
textareaStyle="width:80px;line-height:40px !important;" codetype="GB3304" defaultValue="<%=SV(a01.getA0117()) %>"/>
							</td>

							<td class="width-60 height-40 fontConfig label_color"><font color="red">*</font>��&nbsp;��</td>
							<td class="width-80 height-40 ">
								<tags:rmbPopWinInput property="a0111" cls="width-80 height-40 no-y-scroll" label="����"
 textareaStyle="display:none;text-align:center;" textareaCls="cellbgclor x-form-field" codetype="ZB01" codename="code_name3"
 defaultValue="<%=SV(a01.getComboxArea_a0111()) %>" hiddenValue="<%=SV(a01.getA0111()) %>"/>
							</td>
							<td class="width-60 height-40 fontConfig label_color"><font color="red">*</font>������</td>
							<td class="width-80 height-40 ">
								<tags:rmbPopWinInput property="a0114" cls="width-80 height-40 no-y-scroll" label="������" onblur="restoreDefault()"
 textareaStyle="display:none;text-align:center;" textareaCls="cellbgclor x-form-field" codetype="ZB01" codename="code_name3"
 defaultValue="<%=SV(a01.getComboxArea_a0114()) %>" hiddenValue="<%=SV(a01.getA0114()) %>"/>
							</td>
						</tr>
						<tr>
							<td class="label-clor width-60 height-40 fontConfig label_color">��&nbsp;��ʱ&nbsp;��</td>
							<td class="label-clor width-80 height-40 " onkeypress="a0140Click2()">
								<tags:rmbNormalInput offsetTop="1" property="a0140" ondblclick="a0140Click()" cls="width-80 height-40 no-y-scroll" label="�뵳ʱ��" onblur="restoreDefault()"  readonly="true"
 textareaStyle="display:none;text-align:center;" textareaCls="cellbgclor x-form-field" divStyle="width:80px;text-align:center;" defaultValue="<%=SV(a01.getA0140()) %>"/>
							</td>
							<td class="label-clor width-60 height-40 fontConfig label_color"><font color="red">*</font>�μӹ�<br/>&nbsp;��ʱ��</td>
							<td class="label-clor width-80 height-40 ">
								<tags:rmbDateInput property="a0134" offsetTop="1" cls="width-80 height-40 no-y-scroll" label="�μӹ���ʱ��" onblur="restoreDefault()"
 textareaStyle="display:none;text-align:center;" textareaCls="cellbgclor x-form-field" defaultValue="<%=SV(a01.getA0134()) %>"/>
							</td>
							<td class="label-clor width-60 height-40 fontConfig label_color"><font color="red">*</font>��&nbsp;��<br/>&nbsp;״&nbsp;��</td>
							<td class="label-clor width-80 height-40 ">
								<tags:rmbSelect property="a0128" cls="height-40 input-text2" label="�������" selectDivStyle="width:560px;"
textareaStyle="width:80px;line-height:40px !important;" codetype="GB2261D" defaultValue="<%=SV(a01.getA0128()) %>"/>
							</td>
						</tr>
						<tr>
							<td class="label-clor width-60 height-40 fontConfig label_color">רҵ��<br/>��ְ��</td>
							<td class="label-clor height-40" colspan="2">
								<tags:rmbNormalInput offsetTop="1" property="a0196" cls="width-140 height-40 no-y-scroll" label="רҵ����ְ��"
 textareaStyle="display:none;text-align:center;" ondblclick="a0196Click()"
textareaCls="cellbgclor x-form-field" defaultValue="<%=SV(a01.getA0196()) %>"/>
							</td>
							<td class="label-clor  height-40 fontConfig label_color">��Ϥרҵ<br/>�к��س�</td>
							<td class="label-clor height-40" colspan="2">
								<tags:rmbNormalInput offsetTop="1" property="a0187a" cls="width-140 height-40 no-y-scroll" label="��Ϥרҵ�к��س�" onblur="restoreDefault()"
 textareaStyle="display:none;text-align:center;" textareaCls="cellbgclor x-form-field" defaultValue="<%=SV(a01.getA0187a()) %>"/>
							</td>
						</tr>
						<tr>
							<td class=" fontConfig label_color" rowspan="4">ѧ&nbsp;��<br/><br/>ѧ&nbsp;λ</td>
							<td class=" fontConfig label_color" rowspan="2">ȫ����<br/>��&nbsp;&nbsp;��</td>
							<td class="label-clor height-30" colspan="2">
								<input id="qrzxl" name="qrzxl" class="height-30 input-text2" label="ȫ���ƽ�����ѧ��"
									   readonly="readonly" value="<%=SV(a01.getQrzxl()) %>" ondblclick="xlxwClick()"
									   style="width:140px;">

							</td>
							<td class=" fontConfig label_color" rowspan="2">��ҵԺУϵ��רҵ</td>
							<td id="qrzxlxxTD" <% if ("".equals(SV(a01.getQrzxlxx())) ||"".equals(SV(a01.getQrzxwxx())) || SV(a01.getQrzxlxx()).equals(SV(a01.getQrzxwxx()))) {
								out.println(" rowspan=2 ");
							} %> class="label-clor height-30" colspan="2">
								<input id="qrzxlxx" type="hidden" name="qrzxlxx" class="height-30 input-text2"
									   label="ԺУϵ��רҵ(ѧ��)"
									   required="false" readonly="readonly" value="<%=SV(a01.getQrzxlxx()) %>"
									   ondblclick="xlxwClick()" style="width:200px;text-align:left;">
								<p id="qrzxlxx_p" name="qrzxlxx_p" title="<%=SV(a01.getQrzxlxx()==null?a01.getQrzxwxx():a01.getQrzxlxx()) %>"
								   class=" input-text2" ondblclick="xlxwClick()"
								   style="width:200px;height:25px;text-align:left;overflow: hidden;"><%=SV(a01.getQrzxlxx()==null?a01.getQrzxwxx():a01.getQrzxlxx()) %>
								</p>
							</td>
						</tr>
						<tr>
							<td class="label-clor height-30" colspan="2">
								<input id="qrzxw" name="qrzxw" class="height-30 input-text2" label="ȫ���ƽ�����ѧλ"
									   required="false" readonly="readonly" value="<%=SV(a01.getQrzxw()) %>"
									   ondblclick="xlxwClick()" style="width:140px;">
							</td>
							<td id="qrzxwxxTD" <% if ("".equals(SV(a01.getQrzxlxx())) ||"".equals(SV(a01.getQrzxwxx())) || SV(a01.getQrzxlxx()).equals(SV(a01.getQrzxwxx()))) {
								out.println(" style='display:none' ");
							} %> class="label-clor height-30" colspan="2">
								<input id="qrzxwxx" type="hidden" name="qrzxwxx" class="height-30 input-text2"
									   label="ԺУϵ��רҵ(ѧλ)"
									   required="false" readonly="readonly" value="<%=SV(a01.getQrzxwxx()) %>"
									   ondblclick="xlxwClick()" style="width:200px;text-align:left;">
								<p id="qrzxwxx_p" name="qrzxwxx_p" 
								   class="height-30 input-text2" ondblclick="xlxwClick()" title="<%=SV(a01.getQrzxwxx()) %>"
								   style="width:200px;text-align:left;"><%=SV(a01.getQrzxwxx()) %>
								</p>
							</td>
						</tr>
						<tr>
							<td class=" fontConfig label_color" rowspan="2">��&nbsp;&nbsp;ְ<br/>��&nbsp;&nbsp;��</td>
							<td class="label-clor height-30" colspan="2">
								<input id="zzxl" name="zzxl" class="height-30 input-text2" label="��ְ������ѧ��"
									   required="false" readonly="readonly" value="<%=SV(a01.getZzxl()) %>"
									   ondblclick="xlxwClick()" style="width:140px;">
							</td>
							<td class="width-60 fontConfig label_color" rowspan="2">��ҵԺУϵ��רҵ</td>
							<td id="zzxlxxTD" <% if ("".equals(SV(a01.getZzxlxx())) ||"".equals(SV(a01.getZzxwxx())) || SV(a01.getZzxlxx()).equals(SV(a01.getZzxwxx()))) {
								out.println(" rowspan=2 ");
							} %> class="label-clor height-30" colspan="2">
								<input id="zzxlxx" type="hidden" name="zzxlxx" class="height-30 input-text2"
									   label="ԺУϵ��רҵ(ѧ��)"
									   required="false" readonly="readonly" value="<%=SV(a01.getZzxlxx()) %>"
									   ondblclick="xlxwClick()" style="width:200px;text-align:left;">
								<p id="zzxlxx_p" name="zzxlxx_p" title="<%=SV(a01.getZzxlxx()==null?a01.getZzxwxx():a01.getZzxlxx()) %>" class=" input-text2"
								   ondblclick="xlxwClick()" 
								   style="width:200px;height: 25px ;text-align:left;  overflow: hidden;"><%=SV(a01.getZzxlxx()==null?a01.getZzxwxx():a01.getZzxlxx()) %>
								</p>
							</td>

						</tr>
						<tr>
							<td class="label-clor height-30" colspan="2">
								<input id="zzxw" name="zzxw" class="height-30 input-text2" label="��ְ������ѧλ"
									   required="false" label="��ְ������ѧ��" readonly="readonly"
									   value="<%=SV(a01.getZzxw()) %>" ondblclick="xlxwClick()"
									   style="width:140px;height: 25px ;">
							</td>
							<td id="zzxwxxTD" <% if ("".equals(SV(a01.getZzxlxx())) ||"".equals(SV(a01.getZzxwxx())) || SV(a01.getZzxlxx()).equals(SV(a01.getZzxwxx()))) {
								out.println(" style='display:none' ");
							} %> class="label-clor height-30" colspan="2">
								<input id="zzxwxx" type="hidden" name="zzxwxx" class="height-30 input-text2"
									   label="ԺУϵ��רҵ(ѧλ)"
									   required="false" readonly="readonly" value="<%=SV(a01.getZzxwxx()) %>"
									   ondblclick="xlxwClick()" style="width:200px;text-align:left;">
								<p id="zzxwxx_p" name="zzxwxx_p" class=" input-text2" ondblclick="xlxwClick()" title="<%=SV(a01.getZzxwxx()) %>"
								   style="width:200px;height: 25px ;text-align:left;    overflow: hidden;"><%=SV(a01.getZzxwxx()) %>
								</p>
							</td>
						</tr>
						<tr>
							<td class="height-30 fontConfig label_color" colspan="2"><font color="red">*</font>������λ��ְ��
							</td>
							<td class="height-30" colspan="5" id="a0192aTD">
								<input id="a0192a" type="hidden" name="a0192a" class="height-30 input-text2"
									   label="������λ��ְ��ȫ��"
									   required="false" readonly="readonly" ondblclick="a0192aClick()"
									   value="<%=SV(a01.getA0192a()) %>" style="width:405px;text-align:left;">
								<p id="a0192a_p" name="a0192a_p" title="<%=SV(a01.getA0192a()) %>"
								   class="height-30 input-text2"
								   style="width:405px;text-align:left;height: 27px;    overflow: hidden;"
								   ondblclick="a0192aClick()"><%=SV(a01.getA0192a()) %>
								</p>
							</td>
						</tr>
<%-- 						<tr>
							<td class="height-30 fontConfig label_color" colspan="2">���ż�ְ
							</td>
							<td class="height-30" colspan="5" id="a0192aTD">
								<input id="Association" type="hidden" name="Association" class="height-30 input-text2"
									   label="���ż�ְȫ��"
									   required="false" readonly="readonly" ondblclick="AssociationClick()"
									   value="<%=SV(a01.getSTJZ()) %>" style="width:405px;text-align:left;">
								<p id="Association_p" name="Association_p" title="<%=SV(a01.getSTJZ()) %>"
								   class="height-30 input-text2"
								   style="width:405px;text-align:left;height: 27px;    overflow: hidden;"
								   ondblclick="AssociationClick()"><%=SV(a01.getSTJZ()) %>
								</p>
							</td>
						</tr> --%>
						

						<tr>
							<td class="label_color width-60 height-330">
								<div style="width: 100%;height: 100%;position: relative;">
									<div id="create" onclick="showwin();" title="��������"
										 style="width:100%;height:20%;background:url(images/jl.png) no-repeat center 10px;cursor:pointer;"></div>
									<!-- <div id="create2" onclick="showwin2();" title="��������" style="width:100%;height:20%;background:url(images/jl.png) no-repeat center 10px;cursor:pointer;"></div> -->
									<span style="" class="fontConfig"><font color="red">*</font><br>��<br>��</span>

									<div id="formatting" onclick="a1701Format();" title="������ʽ��" style="width:100%;height:20%;background:url(images/cv.png) no-repeat center 10px;cursor:pointer;"></div>
								</div>
							</td>
							<td class="height-330" colspan="6">
								<div id="resume" class="height-330" style="width:100%; overflow-y: hidden;overflow-x: hidden;">
									<script id="editor" type="text/plain"  style="width:100%;  height: auto;"></script>
								</div>
							</td>
						</tr>
					</tbody>
					
				</table>
			</div>
			
			<div class="top_div" >
				<div style="width:100%;height:100%;">

 					<div id="tabs2tab" class="top_tab_style active" style="width:100px;" onclick="tabSwitch('tabs','page',1)">����������(��)</div>
				</div>
			</div>
			<div class="resume_div" id="page2"  >
				<table class="person_resume"  cellpadding="0px;" style="position: relative;">
					<tbody  style="display:block;">
						<tr>
							<td class="width-60 height-76 fontConfig label_color"><div style="margin:0 auto;width:15px">��������</div></td>
							<td class="height-76" colspan="7">
								<textarea id="a14z101" name="a14z101" style="" rows="3" cols="6" class="textareaMargin height-76 maxWidth-485 font-left" label="��������"
readonly="readonly" ondblclick="$h.openPageModeWin('rewardPunish','pages.publicServantManage.RewardPunishAddPage','�������',810,520,document.getElementById('a0000').value,ctxPath,null,{maximizable:false,resizable:false})" ><%=SV(a01.getA14z101()) %></textarea>
							</td>
						</tr>
						<tr>
							<td class="width-60 height-76 fontConfig label_color"><div style="margin:0 auto;width:30px">��ȿ��˽������</div></td>
							<td class="height-76" colspan="7">
								<textarea id="a15z101" name="a15z101" rows="3" cols="6" class="height-76 maxWidth-485 font-left" label="��ȿ��˽������"
readonly="readonly" ondblclick="$h.openPageModeWin('assessmentInfo','pages.publicServantManage.AssessmentInfoAddPage','��ȿ������',800,330,document.getElementById('a0000').value,ctxPath,null,{maximizable:false,resizable:false})" ><%=SV(a01.getA15z101()) %></textarea>
							</td>
						</tr>
<%-- 						<tr>
							<td class="width-60 height-76 fontConfig label_color"><div style="margin:0 auto;width:30px">����ίԱ����</div></td>
							<td class="height-76" colspan="7">
								<textarea id="Dbwy_p" name="dbwy_p" rows="3" cols="6" class="height-76 maxWidth-485 font-left" label="��ȿ��˽������"
readonly="readonly" ondblclick="$h.openPageModeWin('DbwyAddPage','pages.publicServantManage.DbwyAddPage','����ίԱ',800,440,document.getElementById('a0000').value,ctxPath,null,{maximizable:false,resizable:false})" ><%=SV(a01.getDbwy()) %></textarea>
							</td>
						</tr>  --%>
						<tr>
							<td class="width-60 fontConfig label_color" rowspan="11">
								<div class="height-340" style="width:100%;position:relative;">
									<div style="height:252px;padding-top:10px;">
										<span style="position:relative;vertical-align:middle;" class="fontConfig">��<br/>ͥ<br/>��<br/>Ҫ<br/>��<br/>Ա<br/>��<br/>��<br/>��<br/>��<br/>Ҫ<br/>��<br/>ϵ</span>
									</div>

									<div id="upb" style="margin-top:3px;margin-left:3px;position:relative;z-index:2;width:53px;height:20px;border-radius:5px;-moz-border-radius:5px;border:1px solid #7b9ebd;background:url(images/up.png) transparent no-repeat 3px center;cursor:pointer;" onclick="up(-1)">
										<span style="line-height:20px;padding-left:18px;font-size:13px;">����</span>
									</div>
									<div id="downb" style="margin-top:5px;margin-left:3px;margin-bottom:10px;position:relative;z-index:2;width:53px;height:20px;border-radius:5px;-moz-border-radius:5px;border:1px solid #7b9ebd;background:url(images/down.png) transparent no-repeat 3px center;cursor:pointer;" onclick="up(1)">
										<span style="line-height:20px;padding-left:18px;font-size:13px;">����</span>
									</div>
									<div id="addrowBtn"
										 style="margin-top:10px;position:relative;margin-left:3px;width:53px;border:1px solid #7b9ebd;background:url(images/delete.png) transparent no-repeat 1px center;border-radius:5px;-moz-border-radius:5px;cursor:pointer;"
										 onclick="addA36row()">
										<span style="line-height:20px;padding-left:18px;font-size:13px;">ɾ��</span>
									</div>
								</div>
							</td>
							
							</style>
							<td class="font_12 width-11 height-30-1 label_color fontConfig">��ν</td>
							<td style="width:80px" class="font_12 width-80 height-30-1 label_color fontConfig">����</td>
							<!-- <td class="font_12 height-30-1 label_color fontConfig">���֤��</td> -->
							<td class="font_12 width-80 height-30-1 label_color fontConfig">��������</td>
							<td class="font_12  width-80 height-30-1 label_color fontConfig">������ò</td>
							<td class="font_12 width-95 height-30-1 label_color fontConfig">������λ��ְ��</td>
							<!-- <td class="font_12 width-15 height-30-1 label_color fontConfig">��Ҫ��ʶ -->
								<script>
									//��λ�������

									Ext.onReady(function () {
										//tabSwitch('tabs', 'page', 2);
										//����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ����ά��
										$h.fieldsDisabled(fieldsDisabled);
										//����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ���Բ鿴
										//var imgdata = "<img height='100%' width='100%' src='<%=request.getContextPath()%>/image/quanxian1.png' />";
									var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
									$h.selectDisabled(selectDisabled_in,imgdata);
								});
							</script>
							</td>
						</tr>


					  <%
						int numFor=1;
						int fixedNum=0;
						String content1="�ɷ�,����";
						String content2="����,Ů��,����,����,����,����,����,����,����,��Ů,��Ů,��Ů,��Ů,��Ů,��Ů,��Ů,����Ů��";
						String content3="����,ĸ��,�̸�,��ĸ";
						String content4="����,����,����,��ĸ";
						  String content5 = "���,���,����,����,����,��ĸ,�ܵ�,��ϱ,��ϱ,���,�ø�,��ĸ,���,���,�˸�,��ĸ,�÷�,����,Ů��,ɩ��,��ĸ,�常,��Ů,����";
						  String content6 = "�õ�,�ý�,����,����,����,����Ů,����Ů,������,���游,����ĸ,����,��ĸ,�̷�,��ĸ,���游,����ĸ,ֶŮ,ֶ��,�游,��ĸ,��������";

						  for (Integer i = 1; i <= lista36Length; i++) {
							  if (numFor > 10) {
								  break;
							  }
							  A36 a36 = (A36) lista36.get(i - 1);
							  int ichar = numFor + 97;
							  String tr_i = "tr_" + numFor;
							  String a3604a_i = "a3604a_" + (char) ichar;
							  String a3601_i = "a3601_" + (char) ichar;
							  String a3607_iF = "a3607_" + (char) ichar + "F";
							  String a3607_i = "a3607_" + (char) ichar;
							  String a3627_i = "a3627_" + (char) ichar;
							  String a3611_i = "a3611_" + (char) ichar;
							  String a3600_i = "a3600_" + (char) ichar;
							  String a3684_i = "a3684_" + (char) ichar;
							  String mark_i = "mark_" + (char) ichar;
					  %>
						<tr id="<%=tr_i %>" onclick="getNum(<%=numFor %>)">
							<td class="width-80 height-30-1 ">
								<tags:rmbSelect property="<%=a3604a_i %>" codetype="GB4761"
												cls="height-30-1 input-text2 ischange" label="��ν" outSelect="true"
												selectDivStyle="width:560px;"
												textareaStyle="width:60px;line-height:36px !important;"
												defaultValue="<%=SV(a36.getA3604a()) %>"/>
							</td>
							 <%-- <td class="width-80 height-30-1">
								<tags:rmbNormalInput offsetTop="0" property="<%=a3601_i %>"
													 cls="width-80 height-30-1  ischange"
													 textareaStyle="display:none;text-align:center;width:40px;line-height:36px"
													 textareaCls="cellbgclor x-form-field" 
													 onkeypress="namevalidator"
													 defaultValue="<%=SV(a36.getA3601()) %>"/>
							</td> --%>
							<td class="font_12 width-80 height-30-1">
								<tags:rmbNormalInput offsetTop="0" property="<%=a3601_i %>"
													 cls="width-80 height-30-1  no-y-scroll"
													 textareaStyle="display:none;text-align:center;" 
													 textareaCls="cellbgclor x-form-field" 
													 defaultValue="<%=SV(a36.getA3601()) %>"/>
							</td>
							<%-- <td class="font_12 width-sfz height-30-1">
								<tags:rmbNormalInput offsetTop="0" property="<%=a3684_i %>"
													 cls="width-sfz height-30-1  no-y-scroll"
													 textareaStyle="display:none;text-align:center;" 
													 textareaCls="cellbgclor x-form-field" 
													 defaultValue="<%=SV(a36.getA0184gz()) %>"/>
							</td> --%>
							<td class="font_12 width-80 height-30-1">
								<tags:rmbDateInput offsetTop="5" property="<%=a3607_i %>"
													 cls="width-80 height-30-1  no-y-scroll ischange"  label="��ͥ��Ա����������"
													 textareaStyle="width:60px;display:none;text-align:center;" 
													 textareaCls="cellbgclor x-form-field" 
													 defaultValue="<%=SV(a36.getA3607()) %>"/>
							</td>
							<!-- <td class="width-80 height-30-1">
								<tags:rmbDateInput property="<%=a3607_i %>" offsetTop="5"
												   cls="width-80 height-30-1 no-y-scroll ischange" label="��ͥ��Ա����������"
												   textareaStyle="display:none;text-align:center;"
												   textareaCls="cellbgclor x-form-field"
												   defaultValue="<%=SV(a36.getA3607()) %>" />
							</td>-->
							<td class="font_12 width-80 height-30-1">
								<tags:rmbSelect property="<%=a3627_i %>" codetype="GB9999" cls="height-30-1 input-text2"
												outSelect="true"
												selectDivStyle="width:500px;"
												textareaStyle="width:80px;line-height:36px !important;"
												defaultValue="<%=SV(a36.getA3627()) %>"/>
							</td>
							<td class="font_12 width-95 height-30-1">
								<tags:rmbNormalInput offsetTop="0" property="<%=a3611_i %>"
													 cls="width-95 height-30-1  no-y-scroll" 
													 textareaStyle="display:none;text-align:center;"
													 textareaCls="cellbgclor x-form-field"
													 defaultValue="<%=SV(a36.getA3611()) %>" divStyle="width:156px;"/>
								<odin:hidden property="<%=a3600_i %>" value="<%=SV(a36.getA3600()) %>"/>
							</td>
							<%-- <td   class="height-30-1">
							<input type="checkbox" name="<%=mark_i %>"  id="<%=mark_i %>"  
							 <% if("1".equals(SV(a36.getMark()))){%> checked="checked"
							<% }%>
							  value="<%=SV(a36.getMark()) %>"  />
							</td> --%>
						</tr>
								<%
								numFor ++;

						}
						%>
						<%
						fixedNum=numFor;
						for(Integer n=1;n<=10;n++){
							if(numFor > 10){
								break;
							}
							A36 a36 = new A36();
							if( n <= lista36Length){
								a36=(A36)lista36.get(n-1);
							}

						String allContent=content1+content2+content3+content4+content5+content6;
						//String allContent=content1;
						if(!allContent.contains((a36.getA3604a()==null?"":a36.getA3604a())) || n>lista36Length){
							int ichar = numFor+97;
							String tr_i = "tr_"+numFor;
							String a3604a_i = "a3604a_"+(char)ichar;
							String a3601_i = "a3601_"+(char)ichar;
							String a3607_iF = "a3607_"+(char)ichar+"F";
							String a3607_i = "a3607_"+(char)ichar;
							String a3627_i = "a3627_"+(char)ichar;
							String a3611_i = "a3611_"+(char)ichar;
							String a3600_i = "a3600_"+(char)ichar;
							String a3684_i = "a3684_"+(char)ichar;
							String mark_i = "mark_"+(char)ichar;
							%>
						<tr id="<%=tr_i %>" onclick="getNum(<%=numFor %>)">
							<td style="font-size:12px;" class="height-30-1">
								<tags:rmbSelect property="<%=a3604a_i %>" codetype="GB4761" cls="height-30-1 input-text2" label="��ν" outSelect="true"
	selectDivStyle="width:560px;" textareaStyle="width:40px;line-height:36px !important;" defaultValue="<%=SV(a36.getA3604a()) %>"/>
							</td>
							<!-- <td style="width:8%;font-size:12px;" class="width-50 height-30-1">
								<tags:rmbNormalInput offsetTop="0" property="<%=a3601_i %>"  cls="width-50 height-30-1  no-y-scroll"
	textareaStyle="display:none;text-align:center;"  textareaCls="cellbgclor x-form-field" 	onkeypress="namevalidator"
	defaultValue="<%=SV(a36.getA3601()) %>"/>
							</td> -->
							<td  style="width:15%;font-size:12px;" class="width-80 height-30-1">
								<tags:rmbNormalInput offsetTop="0" property="<%=a3601_i %>"  cls="width-80 height-30-1  no-y-scroll"
	textareaStyle="display:none;text-align:center;"  textareaCls="cellbgclor x-form-field"  defaultValue="<%=SV(a36.getA3601()) %>"/>
							</td>
							<%-- <td  style="width:18%;font-size:12px;" class="width-sfz height-30-1">
								<tags:rmbNormalInput offsetTop="0" property="<%=a3684_i %>"  cls="width-sfz height-30-1  no-y-scroll"
	textareaStyle="display:none;text-align:center;"  textareaCls="cellbgclor x-form-field"  defaultValue="<%=SV(a36.getA3684()) %>"/>
							</td> --%>
							<td  style="width:15%;font-size:12px;" class="width-70 height-30-1">
								<tags:rmbDateInput offsetTop="5" property="<%=a3607_i %>"  cls="width-80 height-30-1  no-y-scroll" label="��ͥ��Ա����������"
	textareaStyle="width:60px;display:none;text-align:center;"  textareaCls="cellbgclor x-form-field"  defaultValue="<%=SV(a36.getA3607()) %>"/>
							</td>
							<!-- <td  style="width:10%;font-size:12px;" class="width-50 height-30-1" >
								<tags:rmbDateInput property="<%=a3607_i %>" offsetTop="5" cls="width-50 height-30-1 no-y-scroll" label="��ͥ��Ա����������"
	textareaStyle="display:none;text-align:center;" textareaCls="cellbgclor x-form-field" defaultValue="<%=SV(a36.getA3607()) %>" />
							</td>-->
							<td  style="width:15%;font-size:12px;" class="height-30-1">
								<tags:rmbSelect property="<%=a3627_i %>" codetype="GB9999" cls="width-60 height-30-1 input-text2" outSelect="true"
	selectDivStyle="width:500px;" textareaStyle="width:60px;line-height:36px !important;" defaultValue="<%=SV(a36.getA3627()) %>"/>
							</td>
							<td style="font-size:12px;" class="width-90 height-30-1">
								<tags:rmbNormalInput offsetTop="0" property="<%=a3611_i %>"  cls="width-90 height-30-1  no-y-scroll"  
	textareaStyle="display:none;text-align:center;" textareaCls="cellbgclor x-form-field" defaultValue="<%=SV(a36.getA3611()) %>" divStyle="width:156px;"	/>
								<odin:hidden property="<%=a3600_i %>" value="<%=SV(a36.getA3600()) %>"/>
							</td>
							<%-- <td   class="height-30-1">
							<input type="checkbox" name="<%=mark_i %>" id="<%=mark_i %>" value="<%=SV(a36.getMark()) %>"  />
							</td> --%>
						</tr>
						 		<%
								numFor++;
							}
						}
						%>
					</tbody>
					
				</table>
			
			</div>
			
			<script>

			
			$("#page2 input[type='checkbox']").click(function () {
			    if(this.checked){
			        $(this).attr('value','1')
			    }else {
			    	$(this).attr('value','0')
			    }
			});
			</script>
			<script>
				Ext.onReady(function(){
					//tabSwitch('tabs','page',1);
					//����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ����ά��
					$h.fieldsDisabled(fieldsDisabled);
					//����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ���Բ鿴
					var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
					$h.selectDisabled(selectDisabled,imgdata);
				});
			</script>
			
			
			<div class="top_div" style="display: none;">
				<div style="width:100%;height:100%;">

 					<div id="tabs3tab" class="top_tab_style active" style="width:70px;" onclick="tabSwitch('tabs','page',1)">��ǩ��Ϣ</div>
				</div>
			</div>
			<div class="resume_div" id="page3" style="display:none;">
				<table class="remark tdtextclass" cellpadding="0" style="position: relative;">
					<!-- ������Ҫְ����Ҫ����  -->
					<tr>
						<td class="width-65 height-70 fontConfig label_color">
							<div class="width-65 height-69 fontConfig label_color middle-css">������<br/>Ҫְ��<br/>��Ҫ��<br/>����־</div>
						</td>
						<td class="height-70" colspan="6">
							<textarea id="a0193z" name="a0193z" rows="8" cols="6" class="height-70 maxWidth-500 font-left" readonly="readonly"
								ondblclick="$h.openPageModeWin('A0193ZTags','pages.zj.tags.A0193TagsAddPage','������Ҫְ����Ҫ������־',780,580,document.getElementById('a0000').value,ctxPath,null,{maximizable:false,resizable:false})" ><%=SV(extraTags.getA0193z()) %></textarea>
						</td>
					</tr>
					<!-- ��Ϥ����  -->
					<tr>
						<td class="width-65 height-70 fontConfig label_color">��&nbsp;Ϥ<br/>��&nbsp;��</td>
						<td class="height-70" colspan="6">
							<textarea id="a0194z" name="a0194z" rows="3" cols="6" class="height-70 maxWidth-500 font-left" readonly="readonly"
								ondblclick="$h.openPageModeWin('A0194ZTags','pages.zj.tags.A0194TagsAddPage','��Ϥ����',780,550,document.getElementById('a0000').value,ctxPath,null,{maximizable:false,resizable:false})" ><%=SV(extraTags.getA0194z()) %></textarea>
						</td>
					</tr>
					
					<!-- �Ƿ���ʡ�������Ͻ���  -->
					<tr>
						<td class="width-65 height-70 fontConfig label_color">�Ƿ���<br/>ʡ������<br/>�Ͻ���</td>
						<td class="height-70" colspan="6">
							<textarea id="tagsbjysjlzs" name="tagsbjysjlzs" rows="3" cols="6" class="height-70 maxWidth-500 font-left" readonly="readonly"
								ondblclick="$h.openPageModeWin('TagSbjysjlAddPage','pages.zj.tags.TagSbjysjlAddPage','ʡ�������Ͻ���',780,360,document.getElementById('a0000').value,ctxPath,null,{maximizable:false,resizable:false})" ><%=SV(extraTags.getTagsbjysjlzs()) %></textarea>
						</td>
					</tr>
					<!-- �˲�����  -->
					<tr>
						<td class="width-65 height-70 fontConfig label_color">�˲�����</td>
						<td class="height-70" colspan="6">
							<textarea id="tagrclxzs" name="tagrclxzs" rows="3" cols="6" class="height-70 maxWidth-500 font-left" readonly="readonly"
								ondblclick="$h.openPageModeWin('TagRclxAddPage','pages.zj.tags.TagRclxAddPage','�˲�����',800,330,document.getElementById('a0000').value,ctxPath,null,{maximizable:false,resizable:false})" ><%=SV(extraTags.getTagrclxzs()) %></textarea>
						</td>
					</tr>
					<!-- �ͽ�����  -->
					<tr>
						<td class="width-65 height-70 fontConfig label_color">�ͽ�����</td>
						<td class="height-70" colspan="6">
							<textarea id="tagcjlxzs" name="tagcjlxzs" rows="3" cols="6" class="height-70 maxWidth-500 font-left" readonly="readonly"
								ondblclick="$h.openPageModeWin('TagCjlxAddPage','pages.zj.tags.TagCjlxAddPage','�ͽ�����',800,330,document.getElementById('a0000').value,ctxPath,null,{maximizable:false,resizable:false})" ><%=SV(extraTags.getTagcjlxzs()) %></textarea>
						</td>
					</tr>
					<!-- �ɲ���Դ -->
					 <tr style="height:50px;">
					 		<td  class=" height-80 fontConfig label_color">��&nbsp;��<br/>��&nbsp;Դ</td>
							<odin:select2 property="a0194c" value="<%=SV(extraTags.getA0194c()) %>" size="20" colspan="2"  style= "height:50px"
							data="['1','����λ����'],['2','��ֱ���ؽ���'],['3','�����н���'],['4','��ʦְ��ת'],['5','����']"></odin:select2>
					</tr> 
				</table>
			</div>
			<div class="top_div" >
				<div style="width:100%;height:100%;">

 					<div id="tabs4tab" class="top_tab_style active" style="width:70px;" onclick="tabSwitch('tabs','page',1)">������Ϣ</div>
				</div>
			</div>
			<div class="resume_div" id="page4" style="display:block;">
				<table class="remark tdtextclass" cellpadding="0px;" style="position: relative;">

						<!-- ҳ���޸� -->
						<tr>
							<td class="width-60 fontConfig label_color" style="height:300px;">��<br/>��<br/>��<br/>Ϣ</td>
							<td style="height:320px;width:490px;">

								<div style="height:100%;width:100%;">


										<table align="center" bgcolor="white" width="100%" height="100%" cellspacing="0px;" style='table-layout: fixed;'>

												<%-- <tr style="height:50px">

													<td bgcolor="white"  align="center" style="padding-left:3%;width:47%;border-left:0px;border-top:0px">

															<div style="width:100%;height:50px;line-height:50px;">
																	<div style="height:100%;float:left;width:70px;text-align:left;"><label style="font-size: 13px;">�Ƿ�¼</label></div>

																	<input type="checkbox" name="a99z101" id="a99z101" onclick="disableInput(this,'a99z102')" style="float:left;width:13px;margin-top:16px;margin-left:5px;" >
																</div>
													</td>
													<td bgcolor="white"  align="center" style="padding-left:3%;width:47%;border-top:0px">
															<div style="width:100%;height:50px;line-height:50px;">
															<div style="float:left;width:70px;text-align:left;"><span id="a99z102SpanId_s" style="font-size: 13px;">¼��ʱ��</span></div>
															<tags:rmbDateInput2  property="a99z102"  label="����ְ����ʱ��" defaultValue="<%=SV(a99Z1.getA99z102()) %>"
					 textareaStyle="width:150px;height:21px;float:left;margin-top:15px;margin-left:5px;" warnCls="float:left;margin-left:3px;" textareaCls="cright_input_btn linehack"/>
													</div>
													</td>
												</tr> --%>

												<%-- <tr style="height:50px;">

													<td bgcolor="white"  align="center" style="padding-left:3%;width:47%;border-left:0px">
														<div style="width:100%;height:50px;line-height:50px;">
															<div style="float:left;width:70px;text-align:left;"><label style="font-size: 13px;">�Ƿ�ѡ����</label></div>
															<input type="checkbox" name="a99z103" id="a99z103" onclick="disableInput(this,'a99z104')" style="float:left;width:13px;margin-top:16px;margin-left:5px;" >
													    </div>
													</td>
													<td bgcolor="white"  align="center" style="padding-left:3%;width:47%">
															<div style="width:100%;height:50px;line-height:50px;">
																	<div style="float:left;width:60px;text-align:left;"><span id="a99z104SpanId_s" style="font-size: 13px;line-height:25px;text-align:left;">����ѡ����&nbsp;&nbsp;ʱ&nbsp;��</span></div>
																	<tags:rmbDateInput2  property="a99z104"  label="����ѡ����ʱ��" defaultValue="<%=SV(a99Z1.getA99z104()) %>"
							 textareaStyle="width:150px;height:21px;float:left;margin-top:14px;margin-left:15px;" warnCls="float:left;margin-left:3px;" textareaCls="cright_input_btn linehack"/>
																</div>
													</td>
												</tr>

												<tr style="height:50px;">

													<td bgcolor="white"  align="center" style="padding-left:3%;width:47%; border-left:0px">
															<div style="width:100%;height:50px;line-height:50px;">
																	<div style="height:100%;float:left;width:50px;text-align:left;"><label style="font-size: 13px;">�ɳ���</label></div>
																	<tags:rmbPopWinInput2 property="a0115a"  label="�ɳ���"
							 textareaStyle="width:134px;float:left;margin-top:14px;margin-left:5px;height:21px;" textareaCls="right_input_btn linehack" codetype="ZB01" divStyle="margin-top:14px;"
							  hiddenValue="<%=SV(a01.getA0115a()) %>"/>
																</div>
													</td>
													<td bgcolor="white"  align="center" style="padding-left:3%;width:47%;">
															<div style="width:100%;height:50px;line-height:50px;">
																	<div style="height:100%;float:left;width:70px;text-align:left;"><span style="font-size: 13px;">&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;��</span></div>
																	<tags:rmbPopWinInput2 property="a0120"  label="����"
							 textareaStyle="width:134px;float:left;margin-top:14px;margin-left:5px;height:21px;" textareaCls="right_input_btn linehack" codetype="ZB134" divStyle="margin-top:14px;"
							  hiddenValue="<%=SV(a01.getA0120()) %>"/>
																										</div>

													</td>
												</tr>

												<tr style="height:50px;">

													<td bgcolor="white"  align="left" colspan="2" style='padding-left:3%;width:47%;border-left:0px;'>
															<div style="width:100%;height:50px;line-height:50px;">
																	<div style="height:100%;float:left;width:160px;text-align:left;"><span style="font-size: 13px;">רҵ�����๫��Ա��ְ�ʸ�</span></div>
																	<tags:rmbPopWinInput2 property="a0122"  label="רҵ�����๫��Ա��ְ�ʸ�"
																	 textareaStyle="width:134px;float:left;margin-top:14px;margin-left:5px;height:21px;" textareaCls="right_input_btn linehack" codetype="ZB139" divStyle="margin-top:14px;"
																	  hiddenValue="<%=SV(a01.getA0122()) %>"/>
																</div>
													</td>

												</tr> --%>
												<%-- <tr style="height:50px;">

 													<td bgcolor="white"  align="left" colspan="2" style='padding-left:3%;width:47%;border-left:0px;border-bottom: 1px solid #74A6CC'>
															<div style="width:100%;height:50px;line-height:50px;">
															<div style="float:left;width:80px;text-align:left;"><label style="font-size: 13px;">�Ƿ�����ɲ�</label></div>
															<input type="checkbox" name="fkbs" id="fkbs"  style="float:left;width:13px;margin-top:16px;margin-left:5px;" >
													    </div>
													</td> 
													<td>
														<table >
														  <tr style="height:50px;">
															<odin:select property="fkbs" data="['06','��������ɲ���ʡ��'],['16','��������ɲ�����ʡ�飩'],['17','��������ɲ���������']" label="����ɲ�" value="<%=SV(a01.getFkbs()) %>" multiSelect="true"></odin:select>
														  </tr>
														</table>
													</td>

												</tr> --%>

												<tr style="height:50px;">
												<td>
													<table id="a99z191" align="left" bgcolor="white" width="90" height="100%" cellspacing="0px;">
													  <tr style="height:50px;">
														<odin:select property="a99z191" codeType="XZ91" width="140" label="��������" defaultValue="<%=SV(a99Z1.getA99z191()) %>" colspan="1"  styleClass="a99z191"></odin:select>
													  </tr>
													</table>
												</td>
													<td class="right_td1" align="center" style="padding-left:3%;width:47%;border-top:0px">
														<div style="width:100%;height:50px;line-height:50px;text-align:left;">
														<div style="float:left;width:70px;text-align:left;"><span style="font-size: 13px;">&nbsp;&nbsp;��ϵ��ʽ</span></div>
														<input type="text" name="a99z195" id="a99z195" value="<%=SV(a99Z1.getA99z195()) %>" class="right_input_btn" maxlength="18" style="margin-top:16px;width:120px;" >
														</div>
													</td>

												</tr>
												
												<%-- <tr style="height:50px;">
												<td>
													<table   align="left" bgcolor="white" width="90" height="100%" cellspacing="0px;">
													  <tr style="height:50px;">
														<odin:select2 property="a99z1301" codeType="PY02" width="140"  label="��������" multiSelect="true"  value="<%=a99Z1.getA99z1301() %>" colspan="1"  styleClass="a99z191"/>
													  </tr>
													</table>
												</td>
												<td>
													<table   align="left" bgcolor="white" width="90" height="100%" cellspacing="0px;">
													  <tr style="height:50px;">
														<odin:select property="a99z1302" codeType="PY01" width="120" label="������ʽ" multiSelect="true" value="<%=a99Z1.getA99z1302() %>" colspan="1"  styleClass="a99z191"></odin:select>
													  </tr>
													</table>
													<script type="text/javascript">
														Ext.onReady(function(){
															Ext.getCmp('a99z1301_combo').on('select',function(obj){
																$('#a99z1301').val(obj.getCheckedValue())
															});
															Ext.getCmp('a99z1302_combo').on('select',function(obj){
																$('#a99z1302').val(obj.getCheckedValue())
															});
														});
													
													</script>
												</td>

												</tr> --%>
												<tr>
													<td>
													<table   >
													  <tr id="bzxxx" style="height:30px;">
														<odin:textarea property="a99z1303"   label="��������" value="<%=a99Z1.getA99z1303()  %>"     />
													  </tr>
													</table>
												</td>
												</tr>
												<tr>
													<td>
													<table   >
													  <tr id="bzxxx" style="height:30px;">
														<odin:textarea property="a99z1304"   label="��Ҫ����" value="<%=a99Z1.getA99z1304()  %>"     />
													  </tr>
													</table>
												</td>
												</tr>
											</table>
								</div>
							</td>
						</tr>

						<tr>
							<td class="width-60 fontConfig label_color" style="height:99px;">��ע</td>
							<td style="height:79px;width:490px;">
								<textarea id="a0180" name="a0180" rows="3" cols="6" label="��ע" style="width:100%;height:100%"><%=SV(a01.getA0180()) %></textarea>
							</td>
						</tr>


				</table>
			</div>
			

<!-- ʡ���ǩpage6 -->
			<div class="top_div" >
				<div style="width:100%;height:100%;">

 					<div id="tabs6tab" class="top_tab_style active" style="width:70px;" onclick="tabSwitch('tabs','page',1)">����ҵ��</div>
				</div>
			</div>
			<div class="resume_div" id="page6" style="display:block;">
				<table class=" tdtextclass" cellpadding="0px;" style="position: relative;">
					<tbody >
					<!-- ������Ҫְ����Ҫ����  -->
					<tr>
						<td  class="sz-label height-80 fontConfig label_color">����(������)��Ҫְ����Ҫ������־</td>
						<td class="height-76" colspan="6">
							<textarea id="sza0193z" name="sza0193z" rows="3" cols="6" class="editorArea font-left "  readonly="readonly" ondblclick="rzzyjlClick();" ><%=SV(szextraTags.getA0193z()) %></textarea>
						</td>
					</tr>
					<!-- ��Ϥ����  -->
					<tr>
						<td  class=" height-80 fontConfig label_color">��&nbsp;Ϥ<br/>��&nbsp;��</td>
						<td class="height-40" colspan="6">
							<textarea id="sza0194z" name="sza0194z" rows="3" cols="6" class="editorArea font-left" readonly="readonly" ondblclick="sxlyClick();" ><%=SV(szextraTags.getA0194z()) %></textarea>
						</td>
					</tr>
					<!-- ��Ϥ���� ��ע -->
					<tr>
						<td  class=" height-80 fontConfig label_color">��Ϥ��<br/>��ע</td>
						<td class="height-40"  colspan="6">
							<textarea id="sza0194c" name="sza0194c" rows="3" cols="6" class="editorArea font-left"><%=SV(szextraTags.getA0194c()) %></textarea>
						</td>
					</tr>
					<!-- �Ƿ���ʡ�������Ͻ���  -->
					<tr>
						<td  class=" height-80 fontConfig label_color">�Ƿ���ʡ�������Ͻ���</td>
						<td class="height-40" colspan="6">
							<textarea id="sztagsbjysjlzs" name="sztagsbjysjlzs" rows="3" cols="6" class="editorArea font-left" readonly="readonly" ondblclick="sbjysjlClick();" ><%=SV(szextraTags.getTagsbjysjlzs()) %></textarea>
						</td>
					</tr>
					<!-- �˲�����  -->
					<tr>
						<td  class=" height-80 fontConfig label_color">�˲�����</td>
						<td class="height-40" colspan="6">
							<textarea id="sztagrclxzs" name="sztagrclxzs" rows="1" cols="6" class="editorArea font-left" readonly="readonly"ondblclick="rclxClick();" ><%=SV(szextraTags.getTagrclxzs()) %></textarea>
						</td>
					</tr>
					<!-- �ͽ�����  -->
					<tr>
						<td  class=" height-80 fontConfig label_color">�ͽ�����</td>
						<td class="height-40" colspan="6">
							<textarea id="sztagcjlxzs" name="sztagcjlxzs" rows="1" cols="6" class="editorArea font-left" readonly="readonly"ondblclick="cjlxClick();" ><%=SV(szextraTags.getTagcjlxzs()) %></textarea>
						</td>
					</tr>
					<!-- ������ǩ -->
					<tr>
						<td rowspan="2" class="label_color  height-40 fontConfig  label_color">������ǩ</td>
						<td  class="label_color width-80 height-40 fontConfig  label_color">רҵ����</td>
						<td class="label-clor width-120 height-40 lebal-td"  colspan="2">
							<tags:rmbPopWinInput property="tagzybj" cls="width-120 height-40 no-y-scroll ischange" label="רҵ����"
							  readonly="true" textareaStyle="width:100%; text-align:center;"
							   textareaCls="cellbgclor x-form-field" codetype="TAGZYBJ" codename="code_name" defaultValue="<%=SV(szextraTags.getComboxArea_tagzybj()) %>" hiddenValue="<%=SV(szextraTags.getTagzybj()) %>"/>
						</td>
						<td class="label_color width-80 height-40 fontConfig  label_color" >ְ��</td>
						<td class="label-clor width-120 height-40 lebal-td" colspan="2" >
							<tags:rmbPopWinInput property="tagzc" cls="width-120 height-40 no-y-scroll ischange" label="ְ��"  readonly="false" textareaStyle="width:100%;text-align:center;" textareaCls="cellbgclor x-form-field" codetype="TAGZC" codename="code_name" defaultValue="<%=SV(szextraTags.getComboxArea_tagzc()) %>" hiddenValue="<%=SV(szextraTags.getTagzc()) %>"/>
						</td>
					</tr>

					<tr>
						<td class="label-clor  height-40 fontConfig" >�����Ⱦ�</td>
						<td class="label-clor  height-40"  colspan="2">
							<tags:rmbSelect property="a0195z" cls="height-40 input-text2 no-y-scroll ischange" label="�����Ⱦ�" selectTDStyle="width:108px;" selectDivStyle="width:300px;" readonly="true" textareaStyle="line-height:40px !important;" codetype="A0251B" defaultValue="<%=SV(szextraTags.getA0195z()) %>"/>
						</td>
						<td class="label-clor  height-40 fontConfig"  >��ȱרҵ</td>
						<td class="label-clor  height-40"  colspan="2" >
							<tags:rmbSelect property="jqzy" cls="height-40 input-text2 no-y-scroll ischange" label="��ȱרҵ" selectTDStyle="width:108px;" selectDivStyle="width:300px;" readonly="true" textareaStyle="line-height:40px !important;" codetype="A0251B" defaultValue="<%=SV(szextraTags.getJqzy()) %>"/>
						</td>
					</tr>
					<!-- �������  ���� -->
					<%-- <tr>
						<td  class=" height-54 fontConfig label_color">�������</td>
						<td id="tagKcclfj20" class=" height-54" colspan="2" valign="middle" align="center">
							<div class="height-52">
								<div class="height-38 fjfile-style"  id="tagKcclfj20id" style="display: none;">
									<div id="tagKcclfj20_div" class=" height-38 ">
										<p id="tagKcclfj20_filename" class="fjname-style"><%=SV(tagKcclfj20.getFilename()) %></p>
										<p id="tagKcclfj20_filesize">��<%=SV(tagKcclfj20.getFilesize()) %>��</p>
									</div>
								</div>
							</div>
						</td>
						<td id="tagKcclfj21" class=" height-54" colspan="2"  valign="middle" align="center">
							<div class=" height-52">
								<div class=" height-38 fjfile-style" id="tagKcclfj21id" style="display: none;">
									<div id="tagKcclfj21_div" class=" height-38">
										<p id="tagKcclfj21_filename" class=" fjname-style"><%=SV(tagKcclfj21.getFilename()) %></p>
										<p id="tagKcclfj21_filesize">��<%=SV(tagKcclfj21.getFilesize()) %>��</p>
									</div>
								</div>
							</div>
						</td>
						<td class="height-54" colspan="2" >
							<div class="fj_btn_style" onclick="kcclfjClick();" >�ϴ�����</div>
						</td>
					</tr>
					<!-- ��ȿ��˵ǼǱ�  ���� -->
					<tr>
						<td  class=" height-54 fontConfig label_color">��ȿ��˵ǼǱ�</td>
						<td id="tagNdkhdjbfj0" class=" height-54" colspan="2" valign="middle" align="center">
							<div class="height-52">
								<div class="height-38 fjfile-style"  id="tagNdkhdjbfj0id" style="display: none;">
									<div id="tagNdkhdjbfj0_div" class=" height-38">
										<p id="tagNdkhdjbfj0_filename" class="fjname-style"><%=SV(tagNdkhdjbfj0.getFilename()) %></p>
										<p id="tagNdkhdjbfj0_filesize">��<%=SV(tagNdkhdjbfj0.getFilesize()) %>��</p>
									</div>
								</div>
							</div>
						</td>
						<td id="tagNdkhdjbfj1" class=" height-54" colspan="2"  valign="middle" align="center">
							<div class=" height-52">
								<div class=" height-38 fjfile-style"  id="tagNdkhdjbfj1id" style="display: none;">
									<div id="tagNdkhdjbfj1_div" class=" height-38">
										<p id="tagNdkhdjbfj1_filename" class="fjname-style"><%=SV(tagNdkhdjbfj1.getFilename()) %></p>
										<p id="tagNdkhdjbfj1_filesize">��<%=SV(tagNdkhdjbfj1.getFilesize()) %>��</p>
									</div>
								</div>
							</div>
						</td>
						<td class="height-54" colspan="2" >
							<div class="fj_btn_style" onclick="ndkhdjfjClick();" >�ϴ�����</div>
						</td>
					</tr>
					<!-- ����ר���  ���� -->
					<tr>
						<td class=" height-54 fontConfig label_color">����ר���</td>
						<td id="tagDazsbfj0" class="height-54" colspan="2" valign="middle" align="center">
							<div class="height-52">
								<div class="height-38 fjfile-style" id="tagDazsbfj0id" style="display: none;">
									<div id="tagDazsbfj0_div" class="height-38">
										<p id="tagDazsbfj0_filename" class="fjname-style"><%=SV(tagDazsbfj0.getFilename()) %></p>
										<p id="tagDazsbfj0_filesize">��<%=SV(tagDazsbfj0.getFilesize()) %>��</p>
									</div>
								</div>
							</div>
						</td>
						<td id="tagDazsbfj1" class="height-54" colspan="2"valign="middle" align="center">
							<div class="height-52">
								<div class=" height-38 fjfile-style" id="tagDazsbfj1id" style="display: none;">
									<div id="tagDazsbfj1_div" class="height-38">
										<p id="tagDazsbfj1_filename" class="fjname-style"><%=SV(tagDazsbfj1.getFilename()) %></p>
										<p id="tagDazsbfj1_filesize">��<%=SV(tagDazsbfj1.getFilesize()) %>��</p>
									</div>
								</div>
							</div>
						</td>
						<td class="height-54" colspan="2">
							<div class="fj_btn_style" onclick="dazsfjClick();" >�ϴ�����</div>
						</td>
					</tr> --%>
					<tr style="background-color: rgb(250, 252, 255) !important;">
						<td colspan="6"></td>
					</tr>
					</tbody>
				</table>
			</div>

 </div>

		</div>
		<div class="inner_div right_div">
						<div class="top_div pic1" style="background-color: #92B3D4;height: 40px;">
							<span  id="a0163Text"> <script
									type="text/javascript">

function setA0163Text(value){
	var bs = '<%=SV(a01.getFkbs()) %>';
	var v = '';
	if(bs=='1'){
		v = '<����ɲ�>';
	}
	if(CodeTypeJson.ZB126[value]){
		document.getElementById("a0163Text").innerHTML=CodeTypeJson.ZB126[value]+v;
	} else {
		if(v!=''){
			document.getElementById("a0163Text").innerHTML=v;
		}
	}
}
<%-- Ext.onReady(function(){
	setA0163Text('<%=SV(a01.getA0163()) %>');
}); --%>

</script></span>
							<tr>
								<div id="personInfo"
									style="width: 248px !important; height: 32px !important; font-size: 11px; font: normal; overflow: hidden; text-align: center; padding-top: 5px; padding-left: 34px; z-index: 8000;"
									align="center" ></div>
							</tr>
						</div>
						<div class="right_table">
				<table style="height:90%;width:100%;padding-top:10px;position: relative;" id="leftTable">

					<tr>
						<td class="right_td1"><font color="red"></font>ͳ�ƹ�ϵ���ڵ�λ</td>
						<td class="right_td2">
							<tags:rmbPopWinInput2 property="a0195"  label="ͳ�ƹ�ϵ���ڵ�λ"
 textareaStyle="width:114px;float:left;" textareaCls="right_input_btn" codetype="orgTreeJsonData"
 defaultValue="<%=SV(a0195Text) %>" hiddenValue="<%=SV(a01.getA0195()) %>"/>
						</td>
					</tr>
					<tr>
						<td class="right_td1"><font color="red">*</font>���֤��</td>
						<td class="right_td2" ><input  onblur="validateYearMByIDCard()" type="text"  name="a0184" id="a0184" value="<%=SV(a01.getA0184()) %>" class="right_input_btn" maxlength="18"></td>
					</tr>
					<tr>
						<td class="right_td1"><font color="red">*</font>�������</td>
						<td class="right_td2" >
							<table><tr>
							<%
							String ZB130Filter = DBUtils.isNoGbmc(SysManagerUtils.getUserId())?"1=1 and code_status='1'  ":"inino>11 and inino<23 and code_status='1' ";
							%>
							<odin:select2 property="a0165" codeType="ZB130" filter="<%=ZB130Filter %>" width="130" value="<%=SV(a01.getA0165()) %>" multiSelect="true" />
							<tr></table>
							<%-- <tags:rmbSelect property="a0165" cls=" right_option" label="�������"
textareaStyle="height:23px;" codetype="ZB130" defaultValue="<%=SV(a01.getA0165()) %>" selectTDStyle="width:130px;" /> --%>
						</td>
					</tr>
					<tr>
						<td class="right_td1"><font color="red">*</font>����״̬</td>
						<td class="right_td2" >
							<tags:rmbPopWinInput2 property="a0163"   label="����״̬"
	 textareaStyle="width:114px;float:left;" textareaCls="right_input_btn" codetype="ZB126"
 	defaultValue="<%=SV(a0163Text) %>" hiddenValue="<%=SV(a01.getA0163()) %>"/>
						</td>
					</tr>
					<tr>
						<td class="right_td1"><font color="red">*</font>��Ա���</td>
						 <td class="right_td2">
							<tags:rmbPopWinInput2 property="a0160"  label="��Ա���"
 textareaStyle="width:114px;float:left;" textareaCls="right_input_btn" codetype="ZB125"
  hiddenValue="<%=SV(a01.getA0160()) %>"/>
						</td> 

						<%-- <td class="right_td2" >
							<tags:rmbSelect property="a0160" cls=" right_option" label="��Ա���"
textareaStyle="height:23px;" codetype="ZB125" defaultValue="<%=SV(a01.getA0160()) %>" selectTDStyle="width:170px;" selectMenuStyle="width:170px;"/>
						</td> --%>
					</tr>
					<tr>
						<td class="right_td1"><font color="red">*</font>��������</td>
						<td class="right_td2">
							<tags:rmbSelect property="a0121" cls=" right_option" label="��������"
textareaStyle="height:23px;" codetype="ZB135" defaultValue="<%=SV(a01.getA0121()) %>" selectTDStyle="width:130px;" />
						</td>
					</tr>
					<tr>
						<td class="right_td1">����ְʱ��</td>
						<td class="right_td2">
							<tags:rmbDateInput2 property="a0192f" cls="ischange" label="���൱���ְ��ְ��ʱ��" defaultValue="<%=SV(a01.getA0192f()) %>"   textareaCls="cright_input_btn"/>
						</td>
					</tr>
					<tr>
						<td class="right_td1">��ְ����</td>
						<td class="right_td2">
							<input type="text" class="right_input_btn" id="comboxArea_a0221" ondblclick="a0221Click()" readonly="true" style="width:114px;float:left;">
							<div id = "comboxImg_a0221" class="right_qry_div"  onclick="a0221Click()"></div>
						</td>
					</tr>
					
					<tr>
						<td class="right_td1">����ְ����ʱ��</td>
						<td class="right_td2">
							<tags:rmbDateInput2  property="a0288"  label="����ְ����ʱ��" readonly="true" ondblclick="a0221Click()"
defaultValue="<%=SV(a01.getA0288()) %>"
  textareaCls="cright_input_btn"/>

						</td>
					</tr>
					<tr>
						<td class="right_td1">��ְ��</td>
						<td class="right_td2">
							<input type="text" id="comboxArea_a0192e" class="right_input_btn" ondblclick="a0192eClick()" readonly="true" style="width:114px;float:left;">
							<div id = "comboxImg_a0192e" class="right_qry_div" style="null" onclick="a0192eClick()"></div>
						</td>
					</tr>
					<tr>
						<td class="right_td1">����ְ��ʱ��</td>
						<td class="right_td2">
							<tags:rmbDateInput2  property="a0192c"  label="����ְ��ʱ��" readonly="true" ondblclick="a0192eClick()"
defaultValue="<%=SV(a01.getA0192c()) %>"
  textareaCls="cright_input_btn"/>
						</td>
					</tr>

					

					<%-- <tr>
						<td class="right_td1">����ʱ��</td>
						<td class="right_td2">
							<tags:rmbDateInput2  property="zcsj"  label="����ʱ��"
								defaultValue="<%=SV(a01.getZcsj()) %>"   textareaCls="cright_input_btn"/>
						</td>
					</tr>

					<tr>
						<td class="right_td1">����ʱ��</td>
						<td class="right_td2">
							<tags:rmbDateInput2  property="fcsj"  label="����ʱ��"
								defaultValue="<%=SV(a01.getFcsj()) %>"   textareaCls="cright_input_btn"/>
						</td>
					</tr> --%>

					<tr>
						<td class="right_td1"><!-- <font color="red">*</font> -->����Ա�Ǽ�ʱ��</td>
						<td class="right_td2">
							<tags:rmbDateInput2  property="a2949"  label="����Ա�Ǽ�ʱ��" readonly="true" ondblclick="a2949Click()"
								defaultValue="<%=SV(a01.getA2949()) %>"   textareaCls="cright_input_btn"/>
						</td>
					</tr>
				</table>
			</div>

		</div>
	</div>
	<input type="hidden" value="<%=a0000 %>" name="a0000" id="a0000" alt="��Աid">
	<input type="hidden" value="<%=checkedgroupid %>" name="checkedgroupid" id="checkedgroupid" alt="��Աid">
	<input type="hidden" value="<%=SV(a01.getA0141()) %>" name="a0141" id="a0141" alt="������ò">
	<input type="hidden" value="<%=SV(a01.getA3921()) %>" name="a3921" id="a3921" alt="�ڶ�����">
	<input type="hidden" value="<%=SV(a01.getA3927()) %>" name="a3927" id="a3927" alt="��������">
	<input type="hidden" value="<%=SV(a01.getA0144()) %>" name="a0144" id="a0144" alt="�뵳ʱ��">
	<input type="hidden" value="<%=SV(a01.getA0192()) %>" name="a0192" id="a0192" alt="�ֹ�����λ��ְ����">
	<%-- <input type="hidden" value="<%=SV(a01.getA0163()) %>" name="a0163" id="a0163" alt="��Ա����״̬"> --%>
	<!-- 1�����޸�Ȩ�ޣ�2�����޸�Ȩ�� -->
	<input type="hidden" value="1" name="isUpdate" id="isUpdate" alt="�Ƿ����޸�Ȩ��">
	<input type="hidden" value="<%=SV(a01.getA0148()) %>" name="a0148" id="a0148" alt="ְ��㼶">
	<input type="hidden" value="<%=SV(a01.getA0221()) %>" name="a0221" id="a0221" alt="��ְ����">
	<input type="hidden" value="<%=SV(a01.getA0192e()) %>" name="a0192e" id="a0192e" alt="��ְ��">
	<input type="hidden" value="<%=SV(a01.getA1701()) %>" name="a1701" id="a1701" alt="����">
	<input type="hidden" value="<%=SV(a99Z1.getA99Z100()) %>" name="a99Z100" id="a99Z100" alt="id">
	<input type="hidden" id="pdfPath" alt="pdf·��">
	<input type="hidden" id="familyRowNum" alt="��ͥ��Ա���к�">
	<input type="hidden" value="<%=SV(extraTags.getA0194zcode()) %>" name="a0194zcode" id="a0194zcode" alt="��Ϥ�������">
	<input type="hidden" value="<%=SV(extraTags.getA0196zcode()) %>" name="a0196zcode" id="a0196zcode" alt="רҵ���ʹ���">
	<%-- <input type="hidden" value="<%=SV(extraTags.getA0198zcode()) %>" name="a0198zcode" id="a0198zcode" alt="��֯��ǩ����"> --%>
	<input type="hidden" value="<%=SV(a99Z1.getA99z101()) %>" name="a99z101F" id="a99z101F" alt="�Ƿ�¼">
	<input type="hidden" value="<%=SV(a99Z1.getA99z103()) %>" name="a99z103F" id="a99z103F" alt="�Ƿ�ѡ����">
<%-- 	<input type="hidden" value="<%=SV(a01.getFkbs()) %>" name="fkbs" id="fkbs" alt="�ֿ��ʶ"> --%>
	<input type="hidden" value="<%=SV(a01.getFkly()) %>" name="fkly" id="fkly" alt="�ֿ���Դ">
</form>

	</div>

<!--�����������  -->

  <!-- <div id="tabs-1" class="GBx-fieldset" align="left">
	<iframe src="" id='EnterAddPage_GB'  style="width: 854px;height: 625px;border: 0px;"></iframe>
  </div> -->
  <!-- <div id="tabs-2" class="GBx-fieldset" align="left">
  	<iframe src="" id='RegisterAddPage_GB' style="width: 854px;height: 625px;border: 0px;"></iframe>
  </div> -->
  <div id="tabs-3" class="GBx-fieldset" align="left">
  	<iframe src="" id='ExitAddPage_GB' style="width: 854px;height: 100%;border: 0px;"></iframe>
  </div>
  <!-- <div id="tabs-4" class="GBx-fieldset" align="left">
 	<iframe src="" id='AddressAddPage_GB' style="width: 854px;height: 625px;border: 0px;"></iframe>
  </div> -->
  <!-- <div id="tabs-5" class="GBx-fieldset" align="left">
 	<iframe src="" id='ExaminationsAddPage_GB' style="width: 854px;height: 625px;border: 0px;"></iframe>
  </div>
  <div id="tabs-6" class="GBx-fieldset" align="left">
 	<iframe src="" id='SelectPersonAddPage_GB' style="width: 854px;height: 625px;border: 0px;"></iframe>
  </div>
  <div id="tabs-7" class="GBx-fieldset" align="left">
 	<iframe src="" id='OpenSelectAddPage_GB' style="width: 854px;height: 625px;border: 0px;"></iframe>
  </div>
  <div id="tabs-8" class="GBx-fieldset" align="left">
 	<iframe src="" id='OpenTransferringAddPage_GB' style="width: 854px;height: 625px;border: 0px;"></iframe>
  </div> -->
  <div id="tabs-9" class="GBx-fieldset" align="left">
    <iframe src="" id='TrainAddPage_GB' style="width: 854px;height: 100%;border: 0px;"></iframe>
  </div>
  <!-- <div id="tabs-10" class="GBx-fieldset" align="left">
    <iframe src="" id='NiRenMian_GB' style="width: 854px;height: 625px;border: 0px;"></iframe>
  </div> -->
 <!--  <div id="tabs-11" class="GBx-fieldset" align="left">
    <iframe src="" id='LiTui_GB' style="width: 854px;height: 625px;border: 0px;"></iframe>
  </div> -->
  <div id="tabs-12" class="GBx-fieldset" align="left">
    <iframe src="" id='JdInformationAddPage_GB' style="width: 854px;height: 100%;border: 0px;"></iframe>
  </div>
  <!-- <div id="tabs-13" class="GBx-fieldset" align="left">
    <iframe src="" id='AttributeAddPage_GB' style="width: 854px;height: 625px;border: 0px;"></iframe>
  </div> -->
  <!-- ��־���� -->
  <div id="tabs-14" class="GBx-fieldset" align="left">
    <iframe src="" id='LogManger_GB' style="width: 854px;height: 100%;border: 0px;"></iframe>
  </div>
  <!-- <div id="tabs-15" class="GBx-fieldset" align="left">
  	<iframe src="" id='RankByRules_GB' style="width: 854px;height: 625px;border: 0px;"></iframe>
  </div> -->
  <!-- <div id="tabs-20" class="GBx-fieldset" align="left">
    <iframe src="" id='Multimedia_GB' style="width: 854px;height: 625px;border: 0px;"></iframe>
  </div> -->
  <!-- <div id="tabs-21" class="GBx-fieldset" align="left">
    <iframe src="" id='FamilyMembers_GB' style="width: 854px;height: 625px;border: 0px;"></iframe>
  </div> -->
  <!-- <div id="tabs-22" class="GBx-fieldset" align="left">
    <iframe src="" id='GBRS_GB' style="width: 854px;height: 625px;border: 0px;"></iframe>
  </div> -->
  <div id="tabs-23" class="GBx-fieldset" align="left">
  	<iframe src="" id='A0196TagsAddPage' style="width: 854px;height: 100%;border: 0px;"></iframe>
  </div>
  <!-- <div id="tabs-24" class="GBx-fieldset" align="left">
  	<iframe src="" id='AddJianLiAddPage' style="width: 854px;height: 625px;border: 0px;"></iframe>
  </div> -->
  <div id="tabs-34" class="GBx-fieldset" align="left">
  	<iframe src="" id='PersontiveFile' style="width: 854px;height: 100%;border: 0px;"></iframe>
  </div>
   <div id="tabs-25" class="GBx-fieldset" align="left">
  	<iframe src="" id='ZZBQTags' style="width: 854px;height: 100%;border: 0px;"></iframe>
  </div>
</div>




<script type="text/javascript">
// document.getElementById('a0101').setAttribute('onblur', 'validateName()');
//ȥ�������еĿո�
function validateName(){
	var a0101 = document.getElementById('a0101').value;//����
	if(a0101!=""){
		a0101=a0101.replace(/\s+/g,"");
		document.getElementById('a0101').value=a0101;
	}
}

lookdata(sign_js );

$(function() {
	$("#a0101").blur(function(){validateName();});
	$( "#tabs" ).tabs().addClass( "ui-tabs-vertical ui-helper-clearfix" );
	$( "#ulTitle" ).css("display","block");
    $( "#tabs li" ).removeClass( "ui-corner-top" ).addClass( "ui-corner-left" );
    $('#tabs ul li a').bind('click', function(event) {
    	var $this = $(this);
    	var $title = $this.attr('tabname');//titleչʾ rmbҳ�������ҳ����ʽ�л�
    	if($title!='JdInformationAddPage_GB'&&$title!='LogManger_GB'&&$title!='GBRS_GB'&&$title!='TrainAddPage_GB'&&$title!='AddJianLiAddPage'&&$title!='NiRenMian_GB'&&$title!='Multimedia_GB'&&$title!='FamilyMembers_GB'&&$title!='A0196TagsAddPage'&& $title!='PersontiveFile'
    		&&$title!='RankByRules_GB' &&$title!='ExitAddPage_GB'&& $title!='AddressAddPage_GB'&& $title!='ZZBQTags'){
    		$('#rmbButton').css('visibility','visible');
    		if($title=='rmb'){
        		$('#personInfo').removeClass("others-info-title");
        		$('#tabs-0').removeClass('tabs-0');//�����������display none �����ʽ����
        		$('#continueAdd').css('visibility','visible');//�������Ӱ�ť
        	}else{
        		$('#personInfo').addClass("others-info-title");
        		$('#tabs-0').addClass('tabs-0');
        		$('#continueAdd').css('visibility','hidden');
        	}
    	}else{
    		$('#personInfo').addClass("others-info-title");
    		$('#tabs-0').addClass('tabs-0');
    		$('#continueAdd').css('visibility','hidden');
    		$('#rmbButton').css('visibility','hidden');
    	}
    	if($title=='RankByRules_GB'){
    		$('#prtRmb').css('display','none');
    		$('#prtTG').css('display','none');
    	}else{
    		$('#prtRmb').css('display','block');
    		$('#prtTG').css('display','none');
    	}

    	
/*     	if($title=='RankByRules_GB' || $title=='ExitAddPage_GB'|| $title=='AddressAddPage_GB'){
    		$('#next_person').css('display','none');
    		$('#previous_person').css('display','nonfe');
    		$('#ylRmb').css('display','none');
    		$('#prtRmb').css('display','none');
    	} */
    	
    	var winsrc = $this.attr('winsrc');//iframe��src
		var random = Math.random();
		if (!!winsrc && winsrc != '') {
			$('#' + $this.attr('winid')).attr('src', winsrc + "&aa=" + random);
			try{
				document.getElementById('#' + $this.attr('winid')).contentWindow.location.reload(true);
			}catch(e){}
			
			//$this.attr('winsrc', '');
		}
	});
});

//�����Ѿ��򿪵�iframe
function countIframes() {
	var iframe_windows = [];
	$('#tabs ul li a').each(function (e) {

		var winid = $(this).attr('winid');
		if (!!winid && winid != '') {
			var iframeWin = document.getElementById(winid).contentWindow;
			if (typeof (iframeWin.save) == 'function') {
				iframe_windows.push(iframeWin);
			}
		}
	});
	return iframe_windows;

}

</script>
<script>
var newwin = new Ext.Window({
	contentEl: "jlcontent",
	title : '����',
	layout : 'fit',
	width : 960,
	overflow : 'hidden',
	height : 600,
	closeAction : 'hide',
	closable : true,
	minimizable : false,
	maximizable : false,
	modal : false,
	maximized:false,
	id : 'jlfl',
	bodyStyle : 'background-color:#FFFFFF; overflow-x:hidden; overflow-y:hidden',
	plain : true,
	listeners:{}
	});

//��������ģʽ
function selectall2(){
	var contenttext = document.getElementById("contenttext2").value.replace(/&nbsp;/g, ' ').replace(/&amp;/g, '&').replace(/<\/?[^>]*>/g,'').replace(/&lt;/g, '<').replace(/&gt;/g, '>');
	document.getElementById("a1701").value=contenttext;
	setContent();
	var jlfl = Ext.getCmp("jlfl");
	jlfl.hide();
	ajaxSubmit('saveJL');
	//radow.doEvent('saveJL');
	//alert(2222);
}
//��������ģʽ
function selectall(){
	var contenttext = document.getElementById("contenttext").value.replace(/&nbsp;/g, ' ').replace(/&amp;/g, '&').replace(/<\/?[^>]*>/g,'').replace(/&lt;/g, '<').replace(/&gt;/g, '>');
	document.getElementById("a1701").value=contenttext;
	setContent();
	var jlfl = Ext.getCmp("jlfl");
	jlfl.hide();
	ajaxSubmit('saveJL');
}

function setJIANLI(contenttext){
	document.getElementById("a1701").value=contenttext;
	setContent();
}

function showwin(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
	/*  document.getElementById("contenttext").value=document.getElementById("a1701").value.trim().replace(/&nbsp;/g, ' ').replace(/&amp;/g, '&').replace(/<\/?[^>]*>/g,'').replace(/&lt;/g, '<').replace(/&gt;/g, '>');
 		document.getElementById("jlcontent").style.display="block";
 		var jlfl = Ext.getCmp("jlfl");
 		//Ext.getCmp("jltab").activate("tab2");

 		Ext.getCmp("jltab").activate("tab1");		//Ĭ����ʾ�������ı��༭��tab
 		if(!jlfl.rendered){
 			jlfl.show();//alert("no reader")
 		}else if(jlfl.hidden){
 			jlfl.show();//alert("hidden")
 		}else{
 			jlfl.hide();//alert("show")
 		} */
	$h.openPageModeWin('openJLCF','pages.customquery.person.AddJianLiAddPage','�������',1200,1050,{a0000:Id},ctxPath); 
}
function showwin2(){
	var Id = document.getElementById("a0000").value;
	document.getElementById("contenttext").value=document.getElementById("a1701").value.trim().replace(/&nbsp;/g, ' ').replace(/&amp;/g, '&').replace(/<\/?[^>]*>/g,'').replace(/&lt;/g, '<').replace(/&gt;/g, '>');
	$h.openPageModeWin('a1701entrywin','pages.publicServantManage.ResumeList','�����༭',870,435,document.getElementById('a0000').value,ctxPath);


}
</script>
<%--��������--%>
	<div id="jlcontent" style="display: none;width:1052px">
	    <odin:tab id="jltab">

		    <odin:tabModel>
		    	<odin:tabItem title="�����ı��༭" id="tab1"></odin:tabItem>
		    	<odin:tabItem title="��ְ���Զ����ɼ���" id="tab2" isLast="true"></odin:tabItem>
		    </odin:tabModel>
		    <odin:tabCont itemIndex="tab1" >
				<textarea style="height:510px;width: 1050px;font-size:20px;" id="contenttext" style="font-family: '����', Simsun;"></textarea>
				<odin:button property="qx" text="&nbsp;&nbsp;ȷ&nbsp;&nbsp;��&nbsp;&nbsp;" handler="selectall"></odin:button>
			</odin:tabCont>
		    <odin:tabCont itemIndex="tab2">
		    	<textarea style="height:510px;width: 1050px;font-size:20px;" id="contenttext2" style="font-family: '����', Simsun;"></textarea>
		    	<odin:button property="qx2" text="&nbsp;&nbsp;ȷ&nbsp;&nbsp;��&nbsp;&nbsp;" handler="selectall2"></odin:button>
		    </odin:tabCont>
	    </odin:tab>
	</div>
	<odin:window src="/blank.htm" id="pdfViewWin1" width="700" height="500"
	title="�����Ԥ������" modal="true" />
<script type="text/javascript" src="js/divscroll.js"></script>

<script type="text/javascript">







 function prtRmb_new(){
	/* var a0000 = document.getElementById("a0000").value;
	storePam['a0000AndFlag']=a0000+',true';
	ajaxSubmit('printView',storePam,function(){
		var iframe_windows = countIframes();
		othersInfoReshow(iframe_windows);
	}); */
	//$h.openPageModeWin('addPartyTime','pages.publicServantManage.AddPartyTimeAddPage','�뵳ʱ��',500,250,document.getElementById('a0000').value,ctxPath);
	//$h.openPageModeWin('printMenu','pages.publicServantManage.PrintMenu','��ӡ�����',200,150,1,ctxPath);
	//var a0000 = document.getElementById("a0000").value;
	//parent.radow.doEvent('printViewNew',a0000+',true');
<%-- 	$h.openPageModeWin('pdfViewWinNew','pages.publicServantManage.PdfView','��ӡ�����',700,500,1,'<%=request.getContextPath()%>');--%>
    var a0000 = document.getElementById("a0000").value;
	//parent.document.getElementById("printPdf").value = "pdf1.1";
	parent.radow.doEvent('printViewNew',a0000+',true');
	$h.openPageModeWin('pdfViewWinNew','pages.publicServantManage.PdfView','Ԥ�������',700,500,1,ctxPath);
 }
 function PRmb(){


	    var a0000 = document.getElementById("a0000").value;

		parent.radow.doEvent('prtRmb_print',a0000+',true');

	 }



 function prtTG(){
		var a0000 = document.getElementById("a0000").value;
		storePam['a0000AndFlag']=a0000+',true';
		/* ajaxSubmit('printView',storePam,function(){
			var iframe_windows = countIframes();
			othersInfoReshow(iframe_windows);
		}); */
		//$h.openPageModeWin('addPartyTime','pages.publicServantManage.AddPartyTimeAddPage','�뵳ʱ��',500,250,document.getElementById('a0000').value,ctxPath);
		//$h.openPageModeWin('printMenu','pages.publicServantManage.PrintMenu','��ӡ�����',200,150,1,ctxPath);
		var a0000 = document.getElementById("a0000").value;
		parent.document.getElementById("printPdf").value = "pdf2.1";
		<%-- Ext.Ajax.request({
	   		timeout: 10000,
	   		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.customquery.CustomQuery&eventNames=prtTG?photodate="+a0000+",true",
	   		method :"post",
	   		success: function(response) {
	   			downloadfile();
	           }
	      }); --%>
		parent.radow.doEvent('prtTG',a0000+',true');
		//setTimeout(downloadfile,10000);
		$h.openPageModeWin('pdfViewWinNew','pages.publicServantManage.PdfView','��ӡ�׸ı�',700,500,1,ctxPath);
	 }
 function downloadfile(){
	 	var downfile = '<%=request.getSession().getAttribute("pdfFilePath") %>';
		window.location.href="<%=request.getContextPath()%>/ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
 }

/* ��ӡ����� */
function prtRmb(){
	try{
		var wdapp = new ActiveXObject("Word.Application");
  	}catch(e){
  		$helper.alertActiveX();
	    return;
  	}
	Ext.Msg.wait('���Ժ�...', 'ϵͳ��ʾ��');
	var a0000 = document.getElementById("a0000").value;
	ajaxSubmit('prtRmb', a0000);
	/* Ext.Msg.wait('���Ժ�...','ϵͳ��ʾ��');
	var Id = document.getElementById("a0000").value; */
}

 function printStart(path) {
	 var url = window.location.protocol + "//" + window.location.host + "/hzb/";
	 path = url + path;
	 var wdapp = null;
	 try {
		 wdapp = new ActiveXObject("KWPS.Application");
		 wdapp.Documents.Open(path);//��wordģ��url
		 wdapp.Application.Printout();
	 } catch (e) {
		 try {
			 wdapp = new ActiveXObject("Word.Application");
			 wdapp.Documents.Open(path);//��wordģ��url
			 wdapp.Application.Printout();
		 } catch (e) {
			 $helper.alertActiveX();
			 $h.alert('ϵͳ��ʾ��', '�����°�װWPS2016', null, 180);

		 }
	 }
 }

 /* ��ͥ��Ա�������� */
 function up(upOrDown) {
	 var familyRowNum = document.getElementById('familyRowNum').value;//��ǰ�к�
	 <%-- alert(familyRowNum+"-----"+<%=fixedNum%>) --%>
	<%-- if(true){
		if(upOrDown == '-1'){//����������ư�ť
			odin.alert("��ֹ�����ƶ���");
			return false;
		}
		if(upOrDown == '1'){//����������ư�ť
			if(familyRowNum < <%=fixedNum%>){
				odin.alert("��ֹ�����ƶ���");
				return false;
			 }
		}
	}  --%>
	//alert('��ǰ�к�:'+familyRowNum);
	var newFamilyNum = changeABC(familyRowNum);

	var upRow = parseInt(familyRowNum) + parseInt(upOrDown); //���Ƶ��к�
	var newUpRow = changeABC(upRow);
	if (upOrDown == '1') {//����������ư�ť
		//�ж������Ƿ����˻����10��
		var a3600 = document.getElementById("a3600_" + newUpRow).value;
		if (!a3600) {
			odin.alert("�Ѿ������һλ");
			return;
		}
	}

	var mycars = ["a3604a_", "a3601_", "a3607_", "a3627_", "a3611_", "a3600_", "a3684_"];
	for (var i = 0; i < mycars.length; i++) {
		var text;
		if (i == 0 || i == 3) {
			text = "setShowValue()";
		}
		if (i == 1 || i == 4) {
			text = "onblur()";
		}
		if (i == 2) {
			text = "onSubstrblurEvent()";
		}

		var up = document.getElementById(mycars[i]+newFamilyNum).value;

		document.getElementById(mycars[i]+newFamilyNum).value = document.getElementById(mycars[i]+newUpRow).value;
		document.getElementById(mycars[i]+newUpRow).value = up;

		if(i!=5){
			eval(mycars[i]+newFamilyNum+text);
			eval(mycars[i]+newUpRow+text);
		}
	}
	document.getElementById('familyRowNum').value = upRow;
}

/* ɾ����ͥ��Ա */
function addA36row(){
	var familyRowNum = document.getElementById('familyRowNum').value;
	var newFamilyNum = changeABC(familyRowNum);
	var A3600 = document.getElementById("a3600_"+newFamilyNum).value;
	if(!A3600){
		return;
	}

	$h.confirm("ϵͳ��ʾ��","�Ƿ�ȷ��ɾ��������ͥ��Ա��Ϣ��",200,function(id) {
		if("ok"==id){

			//�Ⱥ�̨ɾ������
			Ext.Ajax.request({
			 	method: 'POST',
			 	timeout: 100000,
			 	params: {'a3600':A3600},
			 	url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddZHGBRmb&eventNames=deleteFamily",
				success: function (response, options) {
					var result = Ext.util.JSON.decode(response.responseText);
					if (result.resType == 1) {
						//���������Ϣ
						var mycars = ["a3604a_", "a3601_", "a3607_", "a3627_", "a3611_", "a3600_", "a3684_"];
						for (var i = 0; i < mycars.length; i++) {
							var text;
							if (i == 0 || i == 3) {
								text = "setShowValue()";
							}
							if (i == 1 || i == 4) {
								text = "onblur()";
							}
							if (i == 2) {
								text = "onSubstrblurEvent()";
							}

							document.getElementById(mycars[i]+newFamilyNum).value = "";

							if(i!=5){
								eval(mycars[i]+newFamilyNum+text);
							}
						}
						//�������Ϣ����
						for(var i=parseInt(familyRowNum);i<10;i++){
							newFamilyNum = changeABC(familyRowNum);
							var nextNum = parseInt(familyRowNum)+1;//���Ƶ��к�
							var newUpRow = changeABC(nextNum);
							var A3600 = document.getElementById("a3600_"+newUpRow).value;//ȡ����a3600��ֹͣ
							if(!A3600){
								return;
							}
							for(var j=0;j<mycars.length;j++){
								var text;
								if(j==0 || j==3){
									text = "setShowValue()";
								}
								if(j==1 ||  j==4){
									text = "onblur()";
								}
								if(j==2){
									text = "onSubstrblurEvent()";
								}
								var up = document.getElementById(mycars[j]+newFamilyNum).value;
								document.getElementById(mycars[j]+newFamilyNum).value = document.getElementById(mycars[j]+newUpRow).value;
								document.getElementById(mycars[j]+newUpRow).value = up;
								if(j!=5){
									eval(mycars[j]+newFamilyNum+text);
									eval(mycars[j]+newUpRow+text);
								}
							}
							familyRowNum = parseInt(familyRowNum)+1;//��ǰ�к�+1
						}
					}else{
						odin.alert('ɾ��ʧ�ܣ�');
					}

			 	},
			 	failure : function(response, options){
			 		Ext.Msg.hide();
			 		odin.alert('����ʱ��');
				}
			});

		}else{

		}
	});
}

function getNum(num){
	document.getElementById('familyRowNum').value = num;
}

function changeABC(n){
	var ichar = parseInt(n)+97;
	var SCII = String.fromCharCode(ichar);
	return SCII;
}

function a0140Click(){

	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openPageModeWin('addPartyTime','pages.publicServantManage.AddPartyTimeAddPage','�뵳ʱ��',500,250,document.getElementById('a0000').value,ctxPath);
}

var numC = 0;
function a0140Click2(){

	if(numC == 1){
		return;
	}


	numC = 1;


	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openPageModeWin('addPartyTime','pages.publicServantManage.AddPartyTimeAddPage','�뵳ʱ��',500,250,document.getElementById('a0000').value,ctxPath);
}


function a0196Click(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openPageModeWin('professSkill','pages.publicServantManage.ProfessSkillAddPage','רҵ����ְ��',650,440,document.getElementById('a0000').value,ctxPath);
}
function xlxwClick(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openPageModeWin('degrees','pages.publicServantManage.DegreesAddPage','ѧ��ѧλ',870,435,document.getElementById('a0000').value,ctxPath);
}
function a0192aClick(){
	var name = document.getElementById("a0101").value;
	var a0000 = document.getElementById("a0000").value;
 	$h.openPageModeWin('workUnits','pages.publicServantManage.WorkUnitsAddPage','������λ��ְ��',1020,540,document.getElementById('a0000').value,ctxPath,null,{maximizable:false,resizable:false});
 	//$h.openPageModeWin('hzworkUnits','pages.publicServantManage.zwk.HZWorkUnits','������λ��ְ��',1200, 850,a0000,ctxPath,null,{maximizable:false,resizable:false,a0000:a0000});
}
function AssociationClick(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openPageModeWin('Association','pages.publicServantManage.AssociationAddPage','���ż�ְ',1020,540,document.getElementById('a0000').value,ctxPath,null,{maximizable:false,resizable:false});
}
/* function DbwyClick(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openPageModeWin('DbwyAddPage','pages.publicServantManage.DbwyAddPage','����ίԱ',1020,540,document.getElementById('a0000').value,ctxPath,null,{maximizable:false,resizable:false});
} */
function a0221Click(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openPageModeWin('RankAddPageWin','pages.publicServantManage.RankAddPage','��ְ����',695,320,document.getElementById('a0000').value,ctxPath);
}

 function a0192eClick() {
	 var name = document.getElementById("a0101").value;
	 var Id = document.getElementById("a0000").value;
	 $h.openPageModeWin('RradeRankAddPageWin', 'pages.publicServantManage.RradeRankAddPage', '��ְ��', 695, 320, document.getElementById('a0000').value, ctxPath);
 }

 function a2949Click() {
	 $h.openPageModeWin('RegisterAddPage_GB', 'pages.publicServantManage.RegisterAddPageGB', '����Ա�Ǽ�ʱ��', 695, 320, document.getElementById('a0000').value, ctxPath);
 }

 function setA2949(v) {
	 var a2949 = document.getElementById("a2949").value = v;
	 var a2949_1 = document.getElementById("a2949_1");
	 rmbrestoreDate('a2949');
	 rmbblurDate_bj('a2949', false, true);
 }

 function resumeClick() {

	 $h.showWindowWithSrc('resumeWin', '<%=ctxPath%>/rmb/resume.jsp?','����',811,661,null);
}

function addResume(a0201a,a0215a,a0243){
	//alert(a0201a);
	//alert(a0215a);
	//alert(a0243);
	//var jl1="\r\n"+a0243.substring(0,4)+"."+a0243.substring(4,6)+"--"+"       "+"  "+a0201a+a0215a;
	var jl2=a0243.substring(0,4)+"."+a0243.substring(4,6)+"--"+"       "+"  "+a0201a+a0215a+"\n";
	//document.getElementById('contenttext').value += jl1;
	document.getElementById('a1701').value += jl2;
	setContent();
}

//ͳ�����ڵ�λ��д
function setA0195Value(key,value){
	document.getElementById('a0195').value=key;
	document.getElementById('comboxArea_a0195').value=value;
	//radow.doEvent("setA0195Value",key+"|"+value);
}
//��ְ���λ�д
function setA0221Value(key,value){
	document.getElementById('a0221').value=key;
	document.getElementById('comboxArea_a0221').value=value;
}
//��ְ����ʱ���д
function setA0288Value(value){
	document.getElementById('a0288').value=value;
	var a0288_1 = getTime(value);
	document.getElementById('a0288_1').value=a0288_1;
}
//��ְ����д
function setA0192eValue(key,value){
	document.getElementById('a0192e').value=key;
	document.getElementById('comboxArea_a0192e').value=value;
}
//��ְ��ʱ���д
function setA0192cValue(value){
	document.getElementById('a0192c').value=value;
	var a0192c_1 = getTime(value);
	document.getElementById('a0192c_1').value=a0192c_1;
}


function getStorePam(){
	return {};
	<%
		if (fromModules != null && !"".equals(fromModules)){
	%>
		var pageSize = 1;
		var start = 1;
	<%
		}else{
	%>
	var grid = realParent.Ext.getCmp(gn);
	var bbar = grid.getBottomToolbar();
	var store = grid.getStore();
	var pageSize = bbar.pageSize;
	var start = bbar.cursor;
	<%
		}
	%>
	return {'start':start,'limit':pageSize,'gridName':gn}
}
var storePam = getStorePam();

/**
 * ��һ����һ��
 */
var actionNext = "1";
function showNext(action){

	actionNext = action;
	//���������Ƿ��Ѹ��ģ���������ʾ����
	//ajaxSubmit('isChangeNext',null);
	//return false;

	next();

}


function tishiNext(){
	$h.confirm3btn('ϵͳ��ʾ','��ǰ��Ϣ�Ѿ��޸�,�Ƿ���Ҫ����',null,function(iid){
		if(iid=='yes'){

			//�����ݽ��б���
			savePerson(1,2,null);


		}else if(iid=='no'){

			//�رյ�ǰ����
			Ext.Msg.wait('���Ժ�...','ϵͳ��ʾ��');
			//alert(realParent.Ext)
			storePam['action']=actionNext;
			//console.log(bbar)
			ajaxSubmit('showNextclick',storePam);

		}else if(iid=='cancel'){
			return false;
		}

		});
}


function next(){


	Ext.Msg.wait('���Ժ�...','ϵͳ��ʾ��');
	//alert(realParent.Ext)
	storePam['action']=actionNext;
	//console.log(bbar)
	ajaxSubmit('showNextclick',storePam,function(){
		var iframe_windows = countIframes();
		othersInfoReshow(iframe_windows);
	});

}

/**
 * ����
 */
function addnew(){
	//alert(realParent.Ext)
	ajaxSubmit('addnew',storePam,function(){
		var iframe_windows = countIframes();
		othersInfoReshow(iframe_windows);
	});
}




function validateYearM(){
	var a0184 = document.getElementById('a0184').value;//���֤��
	var a0107 = document.getElementById('a0107').value;//��������
	if(a0107!=""&&a0184!=""){
		var YM = a0184.substring(6, 12);
		var YM2 = a0107.substring(0, 6);
		if (YM2 != YM) {
			document.getElementById('out_a0107').style.border = "1px solid red";
		} else {
			document.getElementById('out_a0107').style.border = "";
		}
	}
}

 function validateYearMByIDCard() {
	 var a0000 = document.getElementById('a0000').value;
	 var a0184 = document.getElementById('a0184').value;//���֤��
	 var a0107 = document.getElementById('a0107').value.substring(0, 6);//��������
	 if (a0107 == "" && a0184 != "") {
		 var YMD = getYearMDByIDCard(a0184);
		 document.getElementById('a0107').value = YMD;
		 var Year = YMD.substring(0, 4);
		 var Month = YMD.substring(4, 6);
		 document.getElementById('div_a0107').innerText = Year + "." + Month;
	 }
	 if (a0107 != "" && a0184 != "") {
		var YMD=getYearMDByIDCard(a0184).substring(0,6);
		if(a0107!=YMD){
			document.getElementById('out_a0107').style.border = "1px solid red";
		}else{
			document.getElementById('out_a0107').style.border = "";
		}
	}
	if(a0184!=""){
	ajaxSubmit('validateReIDCard')
	}else{
		document.getElementById('a0184').style.border='1px solid #222';
	}

}
function reIDCardInfo(flag){
	if(!flag){
	document.getElementById('a0184').style.border='1px solid red';
	}else{
		document.getElementById('a0184').style.border='1px solid #222';
	}
}
function getYearMDByIDCard(a0184){
	var YMD =a0184.substring(6,14);
	return YMD;
}



function savePerson(a,b,confirm){
	$h.progress('��ȴ�', '���ڱ�����Ϣ...',null,400);
	Ext.MessageBox.updateProgress(0, ' ');//���test
	var a0101 = document.getElementById('a0101').value;//����
	var a0184 = document.getElementById('a0184').value;//���֤��
	var a0107 = document.getElementById('a0107').value;//��������
	var a0163 = document.getElementById('a0163').value;//����״̬
	var orthersWindow = null;
	//У�����֤�Ƿ�Ϸ�
	/* if(a0184==''){
		$h.alert('ϵͳ��ʾ��','���֤�Ų���Ϊ��!',null,220);
		return false;
	}
	if(a0184.length>18){
		$h.alert('ϵͳ��ʾ��','���֤�Ų��ܳ���18λ!',null,220);
		return false;
	} */

	//���֤��֤
	//var vtext = $h.IDCard(a0184);
	var vtext = isIdCard(a0184);
	var flag=true;
	if(a0163!=""){
		flag=false;
		ajaxSubmit('validateAgeByA0163');
	}
	if(flag){
		if(vtext!==true){
			//$h.alert('ϵͳ��ʾ��',vtext,null,320);
			$h.confirm("ϵͳ��ʾ��",vtext+'��<br/>�Ƿ�������棿',400,function(id) {
				if("ok"==id){
					//Ext.getCmp('a0184').clearInvalid();
					Ext.Msg.wait('���Ժ�...','ϵͳ��ʾ��');
					savePersonSub(a,b,confirm,false);
				}else{
					return false;
				}
			});
		}
		else{
			savePersonSub(a,b,confirm,true);
		}
	}
}
//��̨���÷���
function unlimitedSave(){

	var a0184 = document.getElementById('a0184').value;//���֤��
	var vtext = isIdCard(a0184);
	if(vtext!==true){
		//$h.alert('ϵͳ��ʾ��',vtext,null,320);
		$h.confirm("ϵͳ��ʾ��",vtext+'��<br/>�Ƿ�������棿',400,function(id) {
			if("ok"==id){
				//Ext.getCmp('a0184').clearInvalid();
				Ext.Msg.wait('���Ժ�...','ϵͳ��ʾ��');
				savePersonSub(a,b,confirm,false);
			}else{
				return false;
			}
		});
	}
	else{
		savePersonSub(a,b,confirm,true);
	}
}

//��̨���÷���
function isSave(){
	var a0184 = document.getElementById('a0184').value;//���֤��
	var vtext = isIdCard(a0184);
	$h.confirm("ϵͳ��ʾ��",'��ְ��Ա����С��18�����63�꣬�Ƿ񱣴棿',400,function(id) {
		if("ok"==id){
			document.getElementById('out_a0107').style.border = "1px solid red";
			if(vtext!==true){
				//$h.alert('ϵͳ��ʾ��',vtext,null,320);
				$h.confirm("ϵͳ��ʾ��",vtext+'��<br/>�Ƿ�������棿',400,function(id) {
					if("ok"==id){
						//Ext.getCmp('a0184').clearInvalid();
						Ext.Msg.wait('���Ժ�...','ϵͳ��ʾ��');
						savePersonSub(a,b,confirm,false);
					}else{
						return false;
					}
				});
			}
			else{
				savePersonSub(a,b,confirm,true);
			}
		}else{
			return false;
		}
	});
}


function savePersonSub(a,b,confirm,isIdcard){

	var a0101 = document.getElementById('a0101').value.replace(/\s/g, "");//����
	var a0184 = document.getElementById('a0184').value;//���֤��
	var a0107 = document.getElementById('a0107').value;//��������
	var a0104 = document.getElementById('a0104').value;//�Ա�
	var a0111_combo = document.getElementById('comboxArea_a0111').value;//����
	var a0114 = document.getElementById('comboxArea_a0114').value.replace(/\s/g, "");//������
	var a0141 = document.getElementById('a0141').value;//������ò
	//var a0114_combo = document.getElementById('comboxArea_a0114').value;//������


	var a0117 = document.getElementById('a0117').value;//����
	var a0128 = document.getElementById('a0128').value;//����״��
	var a0134 = document.getElementById('a0134').value;//�μӹ���ʱ��

	var a0195 = document.getElementById('a0195').value;//ͳ�ƹ�ϵ���ڵ�λ
	var a0160 = document.getElementById('a0160').value;//��Ա���
	var a0165 = document.getElementById('a0165').value;//�������
	var a1701 = document.getElementById('a1701').value;//����
	var a0121 = document.getElementById('a0121').value;//��������
	var a2949 = document.getElementById('a2949').value;//����Ա�Ǽ�ʱ��


	

	
	//�������ڸ�ʽ
	var datetext = $h.date(a0107);
	if(datetext!==true){
		$h.alert('ϵͳ��ʾ��','<font style="color:red">�������£�'+datetext+'</font>',null,320);
		return false;
	}


	/* //������Ϣҳ��У��
	if(document.getElementById('Iorthers')){
		orthersWindow = document.getElementById('Iorthers').contentWindow;
		if(orthersWindow){
			if (orthersWindow.formcheck() == true) {//ȫ��У��
			}else{
				var tabs = Ext.getCmp('rmbTabs');
				tabs.activate(tabs.getItem('orthers'));
				return false;
			}
		}
	}
	//ҵ����Ϣҳ��У��
	if(document.getElementById('IBusinessInfo')){
		orthersWindow = document.getElementById('IBusinessInfo').contentWindow;
		if(orthersWindow){
			if (orthersWindow.formcheck() == true) {//ȫ��У��
			}else{
				var tabs = Ext.getCmp('rmbTabs');
				tabs.activate(tabs.getItem('IBusinessInfo'));
				return false;
			}
		}
	} */
	var a0165 = document.getElementById('a0165').value;//�������
	if(a0165==null || a0165==''){
		$h.alert('ϵͳ��ʾ��','��ѡ��������',null,150);
		return;
	}
	var a0141 = document.getElementById('a0141').value;//��������
	if(a0141==null || a0141==''){
		$h.alert('ϵͳ��ʾ��','��˫���뵳ʱ��ά��������ò��',null,150);
		return;
	}

	var birthdaya0184 = getBirthdatByIdNo(a0184);
	var birthdaya0107 = document.getElementById('a0107').value;//��������
	var msg = '<font style="color:red">�������������֤��һ��</font>��<br/>�Ƿ�������棿';
	var iframe_windows = countIframes();
	if (formcheck(iframe_windows) == false) {//����ҳ��У��
		return;
	}
	
	
	saveAlert = new SaveAlert(iframe_windows);
	if (isIdcard && (birthdaya0107 == '' || (birthdaya0107.substring(0, 6) != birthdaya0184.substring(0, 6)))) {
		$h.confirm("ϵͳ��ʾ��", msg, 200, function (id) {
			if ("ok" == id) {
				Ext.Msg.wait('���Ժ�...', 'ϵͳ��ʾ��');
				ajaxSubmit('save.onclick');
				setTimeout(function () {
					saveOthers(iframe_windows);
				}, 500);


			} else {
				return false;
			}
		});
		return false;
	} else {
		ajaxSubmit('save.onclick');
		//������Ϣ  ///
		setTimeout(function () {
			saveOthers(iframe_windows);
		}, 500);
	}


}

//��������ҳ����Ϣ
function formcheck(iframe_windows){

	//alert(iframe_windows[0].save)
	for(var i=0;i<iframe_windows.length;i++){
		if(!!iframe_windows[i].formcheck){
			if(iframe_windows[i].formcheck()==false){
				return false;
			}

		}
	}
	return true;
}

//��������ҳ����Ϣ
function saveOthers(iframe_windows){
	//alert(iframe_windows[0].save)
	for(var i=0;i<iframe_windows.length;i++){
		if(!!iframe_windows[i].save){
			iframe_windows[i].save();

		}
	}
}
//����ҳ����Ϣ����
function othersInfoReshow(iframe_windows){

	//alert(iframe_windows[0].save)
	for(var i=0;i<iframe_windows.length;i++){
		if(!!iframe_windows[i].reShowMsg){
			iframe_windows[i].reShowMsg();

		}
	}
}
//������ʾ


 var saveAlert;

 function SaveAlert(iframe_windows) {
	 var curIndex = 0;
	 var pageLength = iframe_windows.length + 1;
	 var alertErrMsg = "";
	 var lazy = 0;
	 var updateProgress = function (cur, total, pageName, mainMessage, lazy) {
		 setTimeout(function () {
			 Ext.MessageBox.updateProgress(cur / total, pageName + "��" + mainMessage);
		 }, lazy);
	 };
	 //var alertSucMsg = "";
	 return function (messageCode, pageName, mainMessage) {
		 curIndex++;

		 if (messageCode == 0) {
			 updateProgress(curIndex, pageLength, pageName, mainMessage, lazy);
		 } else {
			 updateProgress(curIndex, pageLength, pageName, mainMessage, lazy);
			 alertErrMsg = alertErrMsg + pageName + "��" + mainMessage + "<br/>";
		 }
		lazy = lazy+50;
		if(curIndex==pageLength){
			if(alertErrMsg==''){

				setTimeout(function(){
					Ext.Msg.hide();
					Ext.example.msg('','����ɹ���',1);
				}
				,lazy+100);

			}else{
				setTimeout(function(){$h.alert('ϵͳ��ʾ��',alertErrMsg,null,500);},lazy+50);

			}
		}
	}
}

function ajaxSubmit(radowEvent,parm,callback){
	if(parm){
	}else{
		parm = {};
	}
	Ext.Ajax.request({
		method: 'POST',
		form:'rmbform',
        async: true,
        params : parm,
        timeout :300000,//���������
		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddZHGBRmb&eventNames="+radowEvent,
		success: function(resData){
			var cfg = Ext.util.JSON.decode(resData.responseText);
			//alert(cfg.messageCode)
			if(0==cfg.messageCode){
                Ext.Msg.hide();

                if(cfg.elementsScript.indexOf("\n")>0){
					cfg.elementsScript = cfg.elementsScript.replace(/\r/gi,"");
					cfg.elementsScript = cfg.elementsScript.replace(/\n/gi,"\\n");
				}

				//console.log(cfg.elementsScript);

				eval(cfg.elementsScript);
				//var realParent = parent.Ext.getCmp("setFields").initialConfig.thisWin;
				//parent.document.location.reload();
				//alert(cfg.elementsScript);
				//realParent.resetCM(cfg.elementsScript);
				//parent.Ext.getCmp("setFields").close();
				//console.log(cfg.mainMessage);

				if("�����ɹ���"!=cfg.mainMessage){
					if("save.onclick"==radowEvent){
						saveAlert(cfg.messageCode,"���������Ϣ��",cfg.mainMessage);
					}else{
						Ext.Msg.hide();
						Ext.Msg.alert('ϵͳ��ʾ:',cfg.mainMessage);
					}

				}else{
// 					Ext.Msg.hide();
				}
			}else{
				//Ext.Msg.hide();

				/* if(cfg.mainMessage.indexOf("<br/>")>0){

					$h.alert('ϵͳ��ʾ',cfg.mainMessage,null,380);
					return;
				} */

				if("�����ɹ���"!=cfg.mainMessage){
					if("save.onclick"==radowEvent){
						saveAlert(cfg.messageCode,"���������Ϣ��",cfg.mainMessage);
					}else{
						Ext.Msg.hide();
						Ext.Msg.alert('ϵͳ��ʾ:',cfg.mainMessage);
					}
				}else{
					Ext.Msg.hide();
				}
			}
			if(!!callback){
				callback();
			}
		},
		failure : function(res, options){
			Ext.Msg.hide();
			alert("�����쳣��");
		}
	});
}
//��֤���֤�Ų���ȡ��������
 function getBirthdatByIdNo(iIdNo) {
	 var tmpStr = "";
	 var idDate = "";
	 var tmpInt = 0;
	 var strReturn = "";

	 iIdNo = trim(iIdNo);
	 if (iIdNo.length == 15) {
		 tmpStr = iIdNo.substring(6, 12);
		 tmpStr = "19" + tmpStr;
		 tmpStr = tmpStr.substring(0, 4) + tmpStr.substring(4, 6) + tmpStr.substring(6);

		 return tmpStr;
	 } else {
		 tmpStr = iIdNo.substring(6, 14);
		 tmpStr = tmpStr.substring(0, 4) + tmpStr.substring(4, 6) + tmpStr.substring(6);
		 return tmpStr;
	 }
 }

 function trim(s) {
	 return s.replace(/^\s+|\s+$/g, "");
 }

 function showdialog() {
	 $h.showModalDialog('picupload', ctxPath + '/picCut/picwin.jsp?a0000="+a0000+"', 'ͷ���ϴ�', 900, 490, null, {a0000: '"+a0000+"'}, true);
}
function showdialog2(fileName) {
	 $h.showModalDialog('picupload2', ctxPath + '/picCut/uploadimage.jsp?ImgIdPostfix=&Picurl='+fileName+ '&step=2', 'ͷ���ϴ�', 900, 490, null, {a0000: '"+a0000+"'}, true);
}


 function setShowValue(id,codetypeJson){
	var value = document.getElementById(id).value;
	if(codetypeJson[value]){
		document.getElementById("comboxArea_"+id).value=codetypeJson[value];
	}else{
		document.getElementById("comboxArea_"+id).value='';
	}
}
function a0160onchange(a){
	//alert(a);
}


function namevalidator(obj){

	//var a0101obj = document.getElementById('a0101');
	var value = obj.value;

	if(value.length>18){
		obj.value = obj.value.substring(0,18);
	}

	//ȥ��ǰ��ĵ�
	while(true){
		var firstStr = value.substring(0,1);
		if(firstStr==='.'||firstStr==='��'||firstStr==='��'){
			obj.value=value=value.substring(1,value.length);
		}else{
			break;
		}
	}
	//����.תΪ��
	if (value.match(/\.|��|\r\n|\r|\n/g)) {
		obj.value = value.replace(/\.|��/g, "��").replace(/\r\n|\r|\n/g, "");
	}

	var a0101 = document.getElementById('a0101').value;
	//alert(a0101);
	var c = [];
	var s = 0;
	for (var i = 0; i < a0101.length; i++) {
		c.push(a0101.charAt(i));
	}
	for (var i = 0; i < c.length; i++) {
		if (c[i] == "��" || c[i] == ".") {
			s++;
		}
	}
	if (s > 2) {
		$h.alert('ϵͳ��ʾ��','�������������ϡ�,��ȷ��!',null,220);
	}



	return true;
}
function setTitle(){
	var a0101 = document.getElementById("a0101").value;
	var a0104 = document.getElementById("a0104Text").value;
	var a0107 = document.getElementById("a0107").value;
	var age = $h.getYear(a0107,new Date().format("Ymd"));
	//console.log(new Date().format("yyyyMMdd"));
	var title = a0101 + "��" + a0104 + "��" + age + "��";


	parent.Ext.getCmp('personInfoOP').setTitle(title.replace("<", "&lt;").replace("'", "&acute;"));
}





 function peoplePortrait(){
	 /* var a0000PeoplePortrait = document.getElementById("a0000").value;
	 var fulls = "left=0,screenX=0,top=0,screenY=0,scrollbars=1";    //���嵯�����ڵĲ���
	 if (window.screen) {
	    var ah = screen.availHeight - 30;
	    var aw = screen.availWidth - 90;
	    fulls += ",height=" + ah;
	    fulls += ",innerHeight=" + ah;
	    fulls += ",width=" + aw;
	    fulls += ",innerWidth=" + aw;
	    fulls += ",resizable"
	 } else {
	    fulls += ",resizable"; // ���ڲ�֧��screen���Ե�������������ֹ�������󻯡� manually
	 }
	 window.open(ctxPath+"/radowAction.do?method=doEvent&pageModel=pages.customquery.PeoplePortrait&a0000="+a0000PeoplePortrait,"����",fulls); */
	 var ip='<%=GlobalNames.sysConfig.get("XBDJ_IP")%>';
	 var port='<%=GlobalNames.sysConfig.get("XBDJ_MAINPORT")%>';
	 //http://71.8.177.189:1902/ngbdp/yjgz/newtable
	 var url = "http://"+ip+":"+port+"/ngbdp/versionHistory/gbhx/s2?hasback=false"; 
	 var a0000 = document.getElementById("a0000").value;
	
	var rmbWin=window.open(url+'&a0000='+a0000, '_blank', 'height=637, width=1230, top=20, left='+(screen.width/2-615)+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
}
Ext.onReady(function(){
	setShowValue("a0221",CodeTypeJson.ZB09);
	setShowValue("a0192e",CodeTypeJson.ZB148);

	/* var a1701 = document.getElementById('a1701').value;
	setResume(a1701); */

	//

	
});



//����ʹ�ù�������getEditor���������ñ༭��ʵ���������ĳ���հ������øñ༭����ֱ�ӵ���UE.getEditor('editor')�����õ���ص�ʵ��
var ue = UE.getEditor('editor');

function a1701button(){
	/* document.createElement("create").disabled = "disabled";
	//$("#create").addClass('bgclor');
	document.createElement("create").onclick=null;
	document.createElement("formatting").disabled = "disabled";
	//$("#formatting").addClass('bgclor');
	document.createElement("formatting").onclick=null; */
	$("#create").removeAttr("onclick");
	$("#formatting").removeAttr("onclick");
}

//alert(ue);
Ext.onReady(function(){
ue.ready(function () {

    // $('input').removeAttr('onclick');
    // $('input').removeAttr('disabled');
    // $('input').attr('readonly','true');
    // $('div[disabled]').removeAttr('disabled');

		//������Ϣ��Ȩ������ƣ��жϼ����Ƿ����
		if(fieldsDisabled.indexOf("a1701") != -1){
            $('input').removeAttr("disabled");//ȥ��inputԪ�ص�disabled����
			ue.setDisabled();
			Ext.getCmp('qx').setDisabled(true);
			Ext.getCmp('qx2').setDisabled(true);
			window.frames["ueditor_0"].document.getElementById("bodyContent").style.backgroundColor = "#FFFFFF";
			window.frames["ueditor_0"].document.getElementById("bodyContent").style.color = "#000";
			window.frames["ueditor_0"].document.getElementById("bodyContent").ondblclick=null;
			a1701button();
		}
		if(selectDisabled.indexOf("a1701") != -1){
			ue.setDisabled();
			Ext.getCmp('qx').setDisabled(true);
			Ext.getCmp('qx2').setDisabled(true);
			window.frames["ueditor_0"].document.getElementById("bodyContent").style.backgroundColor = "#FFFFFF";
			window.frames["ueditor_0"].document.getElementById("bodyContent").style.color = "#000";
			window.frames["ueditor_0"].document.getElementById("bodyContent").ondblclick=null;
			var div = document.createElement("div");
			var pNode = document.getElementById("resume").parentNode;
			pNode.style.position = "relative";
			div.style.width = document.getElementById("resume").offsetWidth;
			div.style.height = document.getElementById("resume").offsetHeight;
			div.style.position = "absolute";
			div.style.left = '0px';
			div.style.top = '0px';
			var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
			div.style.backgroundImage = imgdata;
			div.style.backgroundRepeat = "no-repeat";
			div.style.backgroundColor = "white";
			div.style.backgroundPosition = "center";
			pNode.appendChild(div);
			a1701button();
		}

        // editor׼����֮��ſ���ʹ��
	  // setTimeout(function() {
	        	a1701Format();
	  // }, 600);

	        	//console.log(UE.plugins)

	        	ue.setDisabled();
	});

});


if(window.clipboardData){

    //for IE

	/* var copyBtn = document.getElementById("copy");

	copyBtn.onclick = function(){
		 window.clipboardData.setData('text',document.getElementById("a1701").value);
	}  */
}


function a1701Format(){
	// alert(1);
	//var regEx1=/\s*--\s*/g;
	//var regEx2=/\s*--/g;

	var a1701 = document.getElementById("a1701").value.replace(/�ڼ�/g,'���');
	var a1701Array = a1701.replace(/\r/g,'').split('\n');
	for(var index=0;index<a1701Array.length;index++){
		var text = a1701Array[index].trim().replace(/^[\u3000\x20\xA0]{1,}/,'').replace(/[\u3000\x20\xA0]{1,}$/,'');

		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}\s*[\-\u2500\u2014\uff0d]{1,}\s*[0-9]{4}[\.\uff0e][0-9]{2}[\u3000\x20\xA0]{1,}/)){

				text=text.replace(/\s*--\s*/g,'--');

		}

		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}\s*[\-\u2500\u2014\uff0d]{1,}[\u3000\x20\xA0]{1,}/)){

				text=text.replace(/\s*--/g,'--');

		}


		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[0-9]{4}[\.\uff0e][0-9]{2}[\u3000\x20\xA0]{1,}/)){

			text = text.replace(/[\.\uff0e]/g,'.').replace(/[\-\u2500\u2014\uff0d]{1,}/,'--').replace(/[\u3000\x20\xA0]{1,}/,'\u2002\u2002');

		}else if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[\u3000\x20\xA0]{1,}/)){
			text = text.replace(/[\.\uff0e]/g,'.').replace(/[\-\u2500\u2014\uff0d]{1,}/,'--').replace(/[\u3000\x20\xA0]{1,}/,'\u2002\u2002\u2002\u2002\u2002\u2002\u2002\u2002\u2002');
		}
		a1701Array[index] = text.replace(/[\u3000\x20\xA0]{18,}/g,'\n');
	}
	var newA1701='';
	for(var index=0;index<a1701Array.length;index++){
		newA1701 = newA1701 + a1701Array[index] + '\n';
	}
	document.getElementById("a1701").value = newA1701;
	//alert(a1701Array.length)
	setContent();
	var a1701_keyWords = '<%=a1701_keyWords %>'; //��ȡ��Ա�����ؼ���
			if (a1701_keyWords.length > 0) {
				setJL_Highlight(a1701_keyWords);
			}
	//alert(a1701);
	var a1701Array = newA1701.replace(/\r/g,'').split('\n');


	if (newA1701.indexOf("\n") < 0) {
		//alert(newA1701);
	     return;
	}
	//ȡ����һ��������ʱ����е�����
	var firstIndex;
	for (var index = 0; index < a1701Array.length; index++) {
		var text = a1701Array[index];
		if (text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[\u3000\x20\xA0]{1,}/)) {
			firstIndex = index;
			break;
		}
	}
	//�����д���ʱ����������ӵ��µ�������
	//alert(firstIndex);
	var myArray = [];
	if (firstIndex == undefined) {
		for (var index = 0; index < a1701Array.length; index++) {
			var text = a1701Array[index];
			if (text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[0-9]{4}[\.\uff0e][0-9]{2}[\u3000\x20\xA0]{1,}/) || text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[\u3000\x20\xA0]{1,}/)) {
				myArray.push(text);
			}
		}
	} else {
		for (var index = 0; index < firstIndex + 1; index++) {
			var text = a1701Array[index];
			if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[0-9]{4}[\.\uff0e][0-9]{2}[\u3000\x20\xA0]{1,}/) || text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[\u3000\x20\xA0]{1,}/)){
				myArray.push(text);
			}

		}
	}


	//�ж�ʱ���Ƿ�����
	var msg="";
	for(var i=0;i<myArray.length-1;i++){
		var time=myArray[i].match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[0-9]{4}[\.\uff0e][0-9]{2}[\u3000\x20\xA0]{1,}/)+"";
		var timeNext="";
		if(firstIndex==undefined){
			timeNext=myArray[i+1].match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[0-9]{4}[\.\uff0e][0-9]{2}[\u3000\x20\xA0]{1,}/)+"";
		}else{
			if(i == myArray.length-2){
				timeNext=myArray[i+1].match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[\u3000\x20\xA0]{1,}/)+"";
			}else{
				timeNext=myArray[i+1].match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[0-9]{4}[\.\uff0e][0-9]{2}[\u3000\x20\xA0]{1,}/)+"";
			}
		}


		var timeArr=time.split('--');
		var timeNextArr=timeNext.split('--');

		//alert(timeArr[1].split('.')[0] == timeNextArr[0].split('.')[0]);
		//alert(timeArr[1].split('.')[1].trim()==timeNextArr[0].split('.')[1].trim());

		if(timeArr[1].split('.')[0] == timeNextArr[0].split('.')[0] && timeArr[1].split('.')[1].trim()==timeNextArr[0].split('.')[1].trim()){

		}else{
			 //Ext.MessageBox.alert("��ʾ��",time+"��"+timeNext+"��ʱ�䲻����");
			msg += time+"��"+timeNext+"��ʱ�䲻����"+"<br/>";
		}
	}
	if(msg==""){
		return;
	}
	Ext.MessageBox.alert("��ʾ��",msg);
}

function setContent(){
	var a1701 = document.getElementById("a1701").value;

	//var pas = a1701.split(/\r|\n|\r\n/g);
	//console.log(a1701)
	//return
	
	var a1701Word="";
	try{
		a1701Word=realParent.document.getElementById('a1701Word').value;
	}catch(e){
		a1701Word="";
	}
	a1701 = a1701.replace(/\r/g,'').replace(/\n/g,'</p><p>');

	//console.log(a1701)
	ue.ready(function(){
		ue.setContent("<p>"+a1701+"</p>", false);
		ue.fireEvent("selectionchange");
		//ue.fireEvent("contentchange");
        if(window.frames["ueditor_0"].document==undefined?false:true){
            var ps=window.frames["ueditor_0"].document.getElementById("bodyContent").getElementsByTagName("p");
            //�滻�ı����������ķֺ�ΪӢ�ķֺ�
            var a1701Words =a1701Word.replace(/��/g,";").split(";");
            for(var i=0;i<ps.length;i++){
                var p=ps[i];
                for(var j=0;j<a1701Words.length;j++){
                	if(a1701Words[j]!=""&a1701.indexOf(a1701Words[j])!=-1){
                		p.innerHTML=p.innerHTML.replace(new RegExp(a1701Words[j],"gm"),"<span style='color:red'>"+a1701Words[j]+"</span>");
                	}
                }
            }
        }
	},2);




	
	/* if(pas.length>0){
		ue.setContent(pas[0], false);

		for(var i=1;i<pas.length;i++){
			//console.log(pas[i])
			ue.setContent(pas[i], true);
		}
	}else{
		ue.setContent("", false);
		document.getElementById("editor").style.visibility = 'visible';
	} */
}

			function setJL_Highlight(keywords) {
				//var zb09Words = realParent.document.getElementById('zb09Words').value; //��ȡ��ҳ��� zb09��ֵ
				var a1701 = document.getElementById("a1701").value;
				a1701 = a1701.replace(/\r/g, '').replace(/\n/g, '</p><p>');
				ue.ready(function () {
					ue.setContent("<p>" + a1701 + "</p>", false);
					ue.fireEvent("selectionchange");
					if (window.frames["ueditor_0"].document == undefined ? false : true) {
						var ps = window.frames["ueditor_0"].document.getElementById("bodyContent").getElementsByTagName("p");
						for (var i = 0; i < ps.length; i++) {
							var p = ps[i];
							var strs = []; //����һ����
							strs = keywords.split(","); //�ַ��ָ�
							for (j = 0; j < strs.length; j++) {
								if (strs[j] != "" & a1701.indexOf(strs[j]) != -1) {

									p.innerHTML = p.innerHTML.replace(new RegExp(strs[j], "gm"), "<span style='color:red'>" + strs[j] +
											"</span>");
								}

							}
						}
					}
				}, 2);

			}

ue.addListener('blur',function(a,b,c){
	document.getElementById("a1701").value = ue.getPlainTxt().trim();
});

ue.addListener('beforepaste', myEditor_paste);
function myEditor_paste(o, html) {//alert();
    //html.html = "";
   // alert("ֻ��¼�벻��ճ��");
   //console.log(html)
  // console.log(o)
}




Ext.onReady(function(){

	genResume();
});
/**
 * �Զ����ɼ���
 */
function genResume(){
	ajaxSubmit('genResume');
	document.getElementById("contenttext").value=document.getElementById("a1701").value.trim().replace(/&lt;/g, '<').replace(/&gt;/g, '>').replace(/&nbsp;/g, ' ').replace(/&amp;/g, '&').replace(/<\/?[^>]*>/g,'');

}

//ҳ��ر�ʱ����ʾ
/* var win = parent.Ext.getCmp('personInfoOP');
win.un("beforeClose",parent.beforeClosefn);
parent.beforeClosefn=function(){

	ajaxSubmit('isChange',null);
	return false;
}
win.on("beforeClose",parent.beforeClosefn);  */


/* function tishi(){
	$h.confirm3btn('ϵͳ��ʾ','��ǰ��Ϣ�Ѿ��޸�,�Ƿ���Ҫ����',null,function(iid){
		if(iid=='yes'){

			//�����ݽ��б���
			savePerson(1,2,null);

			//�رյ�ǰ����
			win.hide(null, function(){
				win.fireEvent('close', win);
				win.destroy();
            }, win);

		}else if(iid=='no'){

			//�رյ�ǰ����
			win.hide(null, function(){
				win.fireEvent('close', win);
				win.destroy();
            }, win);


		}else if(iid=='cancel'){
			return false;
		}

		});
}

function gb(){
	win.hide(null, function(){
		win.fireEvent('close', win);
		win.destroy();
    }, win);


}
 */

/* window.onbeforeunload=function(){
	//ajaxSubmit('isChange',null);
	//radow.doEvent("close");
	return "�ر������ǰ�����ȱ������ݣ�";
} */



//tab
$(document).ready(function () {
    $(document).bind('keydown', function (event) {
        if (event.keyCode == 9) {
            //document.body.focus();
        	//return true;
        	changeNextItem(event.srcElement.id)
        }
    });
});

function changeNextItem(id){
	if(id==""||id=="ui-id-1"){
		document.getElementById("out_a0101").style.display = "none";
		$("#a0101").show();
	} else if(id=="a0101"){
		document.getElementById("a0104Text").click();
	} else if(id=="a0104Text"){//�Ա�
		$("#a0107").show();
		/* $("#a0107").focus(); */
		document.getElementById("out_a0107").style.display = "none";
		document.getElementById("a0104Menu").style.display = "none";
	}else if(id=="a0107"){//��������
		document.getElementById("a0117Text").click();
	}else if(id=="a0117Text"){//����
		$("#div_a0107").show();
		$("#out_a0111").hide();
		$("#comboxArea_a0111").show();
		/* $("#comboxArea_a0111").focus(); */
		document.getElementById("a0117Menu").style.display = "none";
	}else if(id=="comboxArea_a0111"){//����
		document.getElementById("out_a0114").style.display = "none";
		$("#a0117Text").show();
		/* $("#a0117Text").focus();  */
		$("#comboxArea_a0114").show();
		document.getElementById("comboxArea_a0114").click();
	}else if(id=="comboxArea_a0114"){//������
		document.getElementById("out_a0140").style.display = "none";
		$("#a0140").show();
	}else if(id=="a0140"){//�뵳ʱ��
		$("#div_a0114").show();
		$("#a0134").show();
		document.getElementById("out_a0134").style.display = "none";
	}else if(id=="a0134"){//�μӹ���
		$("#a0196").show();
		document.getElementById("out_a0196").style.display = "none";
		document.getElementById("a0128Text").click();
	}else if(id=="a0128Text"){//����
		$("#div_a0134").show();
		document.getElementById("a0128Menu").style.display = "none";
		/* $("#a0196").focus();  */
	}else if(id=="a0196"){//רҵ��������
		$("#a0187a").show();
		document.getElementById("out_a0187a").style.display = "none";
	}else if(id=="a15z101"){
		document.getElementById("a3604a_bText").click();//��ν
	}else if(id=="a3604a_bText"){//��νid
		document.getElementById("a3604a_bMenu").style.display = "none";//���س�ν����
		$("#a3601_b").show();//��ʾ����text
		document.getElementById("out_a3601_b").style.display = "none";//��������div
	}else if(id=="a3601_b"){//����id
		$("#a3684_b").show();//��ʾ���֤text
		document.getElementById("out_a3684_b").style.display = "none";//�������֤div
	}else if(id=="a3684_b"){//���֤
		$("#a3607_b").show();//��ʾ��������text
		document.getElementById("out_a3607_b").style.display = "none";//���س���div
	}else if(id=="a3607_b"){//
		document.getElementById("a3627_bText").click();
	}else if(id=="a3627_bText") {
		$("#a3611_b").show();//��ʾ��������text
		document.getElementById("a3627_bMenu").style.display = "none";
		document.getElementById("out_a3611_b").style.display = "none";
	}else if(id=="a3611_b"){
		document.getElementById("a3604a_cText").click();//��ν
	}else if(id=="a3604a_cText"){//��νid
		document.getElementById("a3604a_cMenu").style.display = "none";//���س�ν����
		$("#a3601_c").show();//��ʾ����text
		document.getElementById("out_a3601_c").style.display = "none";//��������div
	}else if(id=="a3601_c"){//����id
		$("#a3684_c").show();//��ʾ���֤text
		document.getElementById("out_a3684_c").style.display = "none";//�������֤div
	}else if(id=="a3684_c"){//���֤
		$("#a3607_c").show();//��ʾ��������text
		document.getElementById("out_a3607_c").style.display = "none";//���س���div
	}else if(id=="a3607_c"){//
		document.getElementById("a3627_cText").click();
	}else if(id=="a3627_cText") {
		$("#a3611_c").show();//��ʾ��������text
		document.getElementById("a3627_cMenu").style.display = "none";
		document.getElementById("out_a3611_c").style.display = "none";
	}else if(id=="a3611_c"){
		document.getElementById("a3604a_dText").click();//��ν
	}else if(id=="a3604a_dText"){//��νid
		document.getElementById("a3604a_dMenu").style.display = "none";//���س�ν����
		$("#a3601_d").show();//��ʾ����text
		document.getElementById("out_a3601_d").style.display = "none";//��������div
	}else if(id=="a3601_d"){//����id
		$("#a3684_d").show();//��ʾ���֤text
		document.getElementById("out_a3684_d").style.display = "none";//�������֤div
	}else if(id=="a3684_d"){//���֤
		$("#a3607_d").show();//��ʾ��������text
		document.getElementById("out_a3607_d").style.display = "none";//���س���div
	}else if(id=="a3607_d"){//
		document.getElementById("a3627_dText").click();
	}else if(id=="a3627_dText") {
		$("#a3611_d").show();//��ʾ��������text
		document.getElementById("a3627_dMenu").style.display = "none";
		document.getElementById("out_a3611_d").style.display = "none";
	}else if(id=="a3611_d"){
		document.getElementById("a3604a_eText").click();//��ν
	}else if(id=="a3604a_eText"){//��νid
		document.getElementById("a3604a_eMenu").style.display = "none";//���س�ν����
		$("#a3601_e").show();//��ʾ����text
		document.getElementById("out_a3601_e").style.display = "none";//��������div
	}else if(id=="a3601_e"){//����id
		$("#a3684_e").show();//��ʾ���֤text
		document.getElementById("out_a3684_e").style.display = "none";//�������֤div
	}else if(id=="a3684_e"){//���֤
		$("#a3607_e").show();//��ʾ��������text
		document.getElementById("out_a3607_e").style.display = "none";//���س���div
	}else if(id=="a3607_e"){//
		document.getElementById("a3627_eText").click();
	}else if(id=="a3627_eText") {
		$("#a3611_e").show();//��ʾ��������text
		document.getElementById("a3627_eMenu").style.display = "none";
		document.getElementById("out_a3611_e").style.display = "none";
	}else if(id=="a3611_e"){
		document.getElementById("a3604a_fText").click();//��ν
	}else if(id=="a3604a_fText"){//��νid
		document.getElementById("a3604a_fMenu").style.display = "none";//���س�ν����
		$("#a3601_f").show();//��ʾ����text
		document.getElementById("out_a3601_f").style.display = "none";//��������div
	}else if(id=="a3601_f"){//����id
		$("#a3684_f").show();//��ʾ���֤text
		document.getElementById("out_a3684_f").style.display = "none";//�������֤div
	}else if(id=="a3684_f"){//���֤
		$("#a3607_f").show();//��ʾ��������text
		document.getElementById("out_a3607_f").style.display = "none";//���س���div
	}else if(id=="a3607_f"){//
		document.getElementById("a3627_fText").click();
	}else if(id=="a3627_fText") {
		$("#a3611_f").show();//��ʾ��������text
		document.getElementById("a3627_fMenu").style.display = "none";
		document.getElementById("out_a3611_f").style.display = "none";
	}else if(id=="a3611_f"){
		document.getElementById("a3604a_gText").click();//��ν
	}else if(id=="a3604a_gText"){//��νid
		document.getElementById("a3604a_gMenu").style.display = "none";//���س�ν����
		$("#a3601_g").show();//��ʾ����text
		document.getElementById("out_a3601_g").style.display = "none";//��������div
	}else if(id=="a3601_g"){//����id
		$("#a3684_g").show();//��ʾ���֤text
		document.getElementById("out_a3684_g").style.display = "none";//�������֤div
	}else if(id=="a3684_g"){//���֤
		$("#a3607_g").show();//��ʾ��������text
		document.getElementById("out_a3607_g").style.display = "none";//���س���div
	}else if(id=="a3607_g"){//
		document.getElementById("a3627_gText").click();
	}else if(id=="a3627_gText") {
		$("#a3611_g").show();//��ʾ��������text
		document.getElementById("a3627_gMenu").style.display = "none";
		document.getElementById("out_a3611_g").style.display = "none";
	}else if(id=="a3611_g"){
		document.getElementById("a3604a_hText").click();//��ν
	}else if(id=="a3604a_hText"){//��νid
		document.getElementById("a3604a_hMenu").style.display = "none";//���س�ν����
		$("#a3601_h").show();//��ʾ����text
		document.getElementById("out_a3601_h").style.display = "none";//��������div
	}else if(id=="a3601_h"){//����id
		$("#a3684_h").show();//��ʾ���֤text
		document.getElementById("out_a3684_h").style.display = "none";//�������֤div
	}else if(id=="a3684_h"){//���֤
		$("#a3607_h").show();//��ʾ��������text
		document.getElementById("out_a3607_h").style.display = "none";//���س���div
	}else if(id=="a3607_h"){//
		document.getElementById("a3627_hText").click();
	}else if(id=="a3627_hText") {
		$("#a3611_h").show();//��ʾ��������text
		document.getElementById("a3627_hMenu").style.display = "none";
		document.getElementById("out_a3611_h").style.display = "none";
	}else if(id=="a3611_h"){
		document.getElementById("a3604a_iText").click();//��ν
	}else if(id=="a3604a_iText"){//��νid
		document.getElementById("a3604a_iMenu").style.display = "none";//���س�ν����
		$("#a3601_i").show();//��ʾ����text
		document.getElementById("out_a3601_i").style.display = "none";//��������div
	}else if(id=="a3601_i"){//����id
		$("#a3684_i").show();//��ʾ���֤text
		document.getElementById("out_a3684_i").style.display = "none";//�������֤div
	}else if(id=="a3684_i"){//���֤
		$("#a3607_i").show();//��ʾ��������text
		document.getElementById("out_a3607_i").style.display = "none";//���س���div
	}else if(id=="a3607_i"){//
		document.getElementById("a3627_iText").click();
	}else if(id=="a3627_iText") {
		$("#a3611_i").show();//��ʾ��������text
		document.getElementById("a3627_iMenu").style.display = "none";
		document.getElementById("out_a3611_i").style.display = "none";
	}else if(id=="a3611_i"){
		document.getElementById("a3604a_jText").click();//��ν
	}else if(id=="a3604a_jText"){//��νid
		document.getElementById("a3604a_jMenu").style.display = "none";//���س�ν����
		$("#a3601_j").show();//��ʾ����text
		document.getElementById("out_a3601_j").style.display = "none";//��������div
	}else if(id=="a3601_j"){//����id
		$("#a3684_j").show();//��ʾ���֤text
		document.getElementById("out_a3684_j").style.display = "none";//�������֤div
	}else if(id=="a3684_j"){//���֤
		$("#a3607_j").show();//��ʾ��������text
		document.getElementById("out_a3607_j").style.display = "none";//���س���div
	}else if(id=="a3607_j"){//
		document.getElementById("a3627_jText").click();
	}else if(id=="a3627_jText") {
		$("#a3611_j").show();//��ʾ��������text
		document.getElementById("a3627_jMenu").style.display = "none";
		document.getElementById("out_a3611_j").style.display = "none";
	}else if(id=="a3611_j"){
		document.getElementById("a3604a_kText").click();//��ν
	}else if(id=="a3604a_kText"){//��νid
		document.getElementById("a3604a_kMenu").style.display = "none";//���س�ν����
		$("#a3601_k").show();//��ʾ����text
		document.getElementById("out_a3601_k").style.display = "none";//��������div
	}else if(id=="a3601_k"){//����id
		$("#a3684_k").show();//��ʾ���֤text
		document.getElementById("out_a3684_k").style.display = "none";//�������֤div
	}else if(id=="a3684_k"){//���֤
		$("#a3607_k").show();//��ʾ��������text
		document.getElementById("out_a3607_k").style.display = "none";//���س���div
	}else if(id=="a3607_k"){//
		document.getElementById("a3627_kText").click();
	}else if(id=="a3627_kText") {
		$("#a3611_k").show();//��ʾ��������text
		document.getElementById("a3627_kMenu").style.display = "none";
		document.getElementById("out_a3611_k").style.display = "none";
	}
}



Ext.onReady(function(){

	//$("#comboxArea_a0221").after("<span class='right_qry_div'></span>");
		var a0215aWord="";
		try{
			a0215aWord=realParent.document.getElementById('a0215aWord').value;
		}catch(e){
			a0215aWord="";
		}
		var a0814Word="";
		try{
			a0814Word=realParent.document.getElementById('a0814Word').value;
		}catch(e){
			a0814Word="";
		}
		var qrzxlxx=$("#qrzxlxx_p").html();
		var qrzxwxx=$("#qrzxwxx_p").html();
		var zzxlxx=$("#zzxlxx_p").html();
		var zzxwxx=$("#zzxwxx_p").html();
		var a0192a=$('#a0192a_p').html();
		if(a0215aWord!=""){
			$('#a0192a_p').html(a0192a.replace(new RegExp(a0215aWord,"gm"),"<span style='color:red'>"+a0215aWord+"</span>"));
		}
		if(a0814Word!=""){
			  $("#qrzxlxx_p").html(qrzxlxx.replace(new RegExp(a0814Word,"gm"),"<span style='color:red'>"+a0814Word+"</span>"));
			  $("#zzxlxx_p").html(zzxlxx.replace(new RegExp(a0814Word,"gm"),"<span style='color:red'>"+a0814Word+"</span>"));
			  $("#qrzxwxx_p").html(qrzxwxx.replace(new RegExp(a0814Word,"gm"),"<span style='color:red'>"+a0814Word+"</span>"));
			  $("#zzxwxx_p").html(zzxwxx.replace(new RegExp(a0814Word,"gm"),"<span style='color:red'>"+a0814Word+"</span>"));
		}

});





//a01ͳ�ƹ�ϵ���ڵ�λ
function a0195onchange(a, b){

	var a0195 = document.getElementById("a0195").value;

	//radow.doEvent('a0195Change',a0195);
	ajaxSubmit('a0195Change',a0195);
}


Ext.onReady(function(){
	<%=setTitle%>
	/* document.title = window.dialogArguments.title+document.title; */
});


 var aCity = {
	 11: "����",
	 12: "���",
	 13: "�ӱ�",
	 14: "ɽ��",
	 15: "���ɹ�",
	 21: "����",
	 22: "����",
	 23: "������",
	 31: "�Ϻ�",
	 32: "����",
	 33: "�㽭",
	 34: "����",
	 35: "����",
	 36: "����",
	 37: "ɽ��",
	 41: "����",
	 42: "����",
	 43: "����",
	 44: "�㶫",
	 45: "����",
	 46: "����",
	 50: "����",
	 51: "�Ĵ�",
	 52: "����",
	 53: "����",
	 54: "����",
	 61: "����",
	 62: "����",
	 63: "�ຣ",
	 64: "����",
	 65: "�½�",
	 71: "̨��",
	 81: "���",
	 82: "����",
	 91: "����"
 };

 //���֤��֤

 function isIdCard(sId) {

	 //��15λ���֤����ת��Ϊ18λ
	 var sId = changeFivteenToEighteen(sId);
	 //alert(sId);
	 var iSum = 0;
	 var info = "";
	 if (!/^\d{17}(\d|x)$/i.test(sId)) return "<font style='color:red'>����������֤���Ȼ��ʽ����</font>";
	sId=sId.replace(/x$/i,"a");
	if(aCity[parseInt(sId.substr(0,2))]==null) return "<font style='color:red'>�������֤ǰ��λ�����Ƿ���</font>";
	sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2));
	var d=new Date(sBirthday.replace(/-/g,"/")) ;
	if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))return "<font style='color:red'>���֤�ϵĳ������ڷǷ�</font>";

	//aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"��":"Ů");//�˴λ������жϳ���������֤�ŵ����Ա�

	//��֤�Ա�
	//�ж����֤�����ڶ�λ�Ƿ���Ա�һ��
	var sex = sId.substr((sId.length-2), 1);
	var a0104 = document.getElementById('a0104').value;		//�Ա�

	var sexA0104 = sex%2;		//ȡ����

	if(sexA0104 == 0){
		sexA0104 = 2;
	}

	if(sexA0104 != a0104){
		return "<font style='color:red'>���֤���뵹���ڶ�λ�����Ա�һ�£�</font>";
	}

	 for (var i = 17; i >= 0; i--) iSum += (Math.pow(2, i) % 11) * parseInt(sId.charAt(17 - i), 11);
	 if (iSum % 11 != 1) return "<font style='color:red'>����������֤�ŷǷ�</font>";

	 return true;
 }

 //��15λ���֤����ת��Ϊ18λ
 function changeFivteenToEighteen(obj) {
	 if (obj.length == '15') {
		 var arrInt = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
		 var arrCh = ['1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'];
		 var cardTemp = 0, i;
		 obj = obj.substr(0, 6) + '19' + obj.substr(6, obj.length - 6);
		 for (i = 0; i < 17; i++) {
			 cardTemp += obj.substr(i, 1) * arrInt[i];
		 }
		 obj += arrCh[cardTemp % 11];
		 return obj;
	 }
	 return obj;
 }


var fieldsDisabled = <%=TableColInterface.getAllUpdateData(sign)%>;
var selectDisabled = <%=TableColInterface.getAllSelectData()%>;
var selectDisabled_in = <%=TableColInterface.getAllSelectData_in()%>;
<%-- var selectDisabledByTable = <%=TableColInterface.getSelectDataByTable("ATTRIBUTE")%>;  --%>

<%-- Ext.onReady(function(){
	if("ATTRIBUTE_attribute_all"==selectDisabledByTable){
fieldsDisabled		$("#ui-id-2").attr("winsrc","<%=request.getContextPath() %>/error/selectNoVisit.jsp");
	}
	//http://127.0.0.1:8080/hzb/error/notAllowVisit.jsp

	if("ATTRIBUTE_attribute_all"==selectDisabledByTable){
		$("#ui-id-2").hide();
	}else{
		$("#ui-id-2").show();
	}
	//����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ����ά��
	$h.fieldsDisabled(fieldsDisabled);
	//����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ���Բ鿴
	//var imgdata = "<img height='100%' width='100%' src='<%=request.getContextPath()%>/image/quanxian1.png' />";
	var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
	$h.selectDisabled(selectDisabled,imgdata);
}); --%>
$(document).ready( //��λ����

	    function(){
	        document.onkeydown = function()
	        {
	            var oEvent = window.event;
	            if (oEvent.keyCode == 86 && oEvent.ctrlKey) {
	            	CtrlV(getRow());
	            }
	        }
	    }
	);
function CtrlV (curRow){
	var call="";
	var name="";
	var birthday ="";
	var politicalStatus="";
	var job = "";

	var Fcontent = clipboardData.getData("Text");
	var Scontent = Fcontent.split("\n");
	var check = Scontent[0].split("\t");
	if (Trim(check[0]) != "��ν" || Trim(check[1]) != "����" || Trim(check[2]) != "��������" || Trim(check[3]) != "������ò" || Trim(check[4]) != "������λ��ְ��") {
		//Ext.Msg.alert("ϵͳ��ʾ","������<��ν���������������ڡ�������ò��������λ��ְ��>Ϊ��ͷ");
		return;
	}
	//tabSwitch('tabs', 'page', 2);
	var mycars = ["a3604a_", "a3601_", "a3607_", "a3627_", "a3611_", "a3684_"];
	for (var k = 1; k < Scontent.length - 1; k++) {
		var familyRowNum = curRow;//��ǰ�к�
		if (curRow > 10) {
			Ext.Msg.alert("ϵͳ��ʾ", "ճ�����ݴ���11�У�ֻȡǰ10�����ݣ�");
			break;
		}
		var newFamilyNum = changeABC(familyRowNum);
		alert(newFamilyNum);
		for (var i = 0; i < mycars.length; i++) {
			var text;
			if(i==0 || i==3){
				text = "setShowValue()";
			}
			if(i==1 ||  i==4){
				text = "onblur()";
			}
			if(i==2){
				text = "onSubstrblurEvent()";
			}
			document.getElementById(mycars[i]+newFamilyNum).value =Scontent[k].split("\t")[i];
			if(i!=5){
				eval(mycars[i]+newFamilyNum+text);
			}
		}
		document.getElementById('familyRowNum').value = curRow;
		curRow++;
	}
}
function Trim(str)
{
 return str.replace(/[\r\n]/g,"").replace(/(^\s*)|(\s*$)/g, "");
}
function getRow(){
	for(var k=1; k<11; k++){
		var s=changeABC(k);
		var a3604a=document.getElementById("a3604a_"+s).value;
		//alert(a3604a=="");
		var a3601=document.getElementById("a3601_"+s).value;
		var a3607=document.getElementById("a3607_"+s).value;

		var a3627 = document.getElementById("a3627_"+s).value;
		var a3611 = document.getElementById("a3611_"+s).value;
		if(a3604a=="" && a3601=="" && a3607=="" && a3627=="" && a3611==""){
			return k;
		}
	}
}

Ext.onReady(function() {
	$("#ZHGBrmb").css("visibility","visible");
	setTDHeight();
    /* setTimeout(function(){
        a1701Format();
	},1000); */

    Ext.getCmp('a0165_combo').on("select",function (){
    	document.getElementById("a0165").value=Ext.getCmp('a0165_combo').getCheckedValue();
    	});
	/* document.getElementById("ZHGBrmb").style.visibility="visiable"; */
});

</script>
<script type="text/javascript">
	$(window).resize(function(){
		//a1701Format();
		autoHeight();
	});

	function autoHeight() {
		var winHeight = 0;
		if (window.innerHeight)
			winHeight = window.innerHeight;
		else if ((document.body) && (document.body.clientHeight))
			winHeight = document.body.clientHeight;
		if (document.documentElement && document.documentElement.clientHeight)
			winHeight = document.documentElement.clientHeight;
		$("#rmbform").css("height", winHeight - 12 + "px");
		$(".main_div").css("height", winHeight - 12 + "px");
		$(".left_div").css("height", winHeight - 12 + "px");
		$("#ZHGBrmb").css("height", winHeight - 12 + "px");
		$(".hzdbInfo").css("height", winHeight - 44 + "px");
		
		/* var pageHeight = 0;
		var $page2 = $('#page2 table tr');
		$page2.each(function (i, item) {
			$page2 += $(this).height();
		});
		$('#page2').css("height", $page2 + "px"); */
		
		
	}

	autoHeight();
	//����iframˢ��


	

</script>
<script src="js/slabel.js"></script>

</body>
</html>
