<%@page import="java.net.URLEncoder"%>
<%@page import="com.insigma.siis.local.business.entity.A36"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib  uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page isELIgnored="false" %>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003.InfoComWindowPageModel"%>
<%

%>
<%@include file="rmbServerTest.jsp" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
	<script src="jquery-1.7.2.min.js"> </script>
	
    <link href="css/main.css" rel="stylesheet">
    <title>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</title>
    <odin:head></odin:head>
    <odin:base></odin:base>
    <script type="text/javascript" src="<%=ctxPath%>/basejs/helperUtil.js"></script>
    <script src="js/common.js"></script>
    <script type="text/javascript" charset="gbk" src="<%=ctxPath%>/ueditor/third-party/zeroclipboard/ZeroClipboard.min.js"></script>
    <script type="text/javascript" charset="gbk" src="<%=ctxPath%>/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="gbk" src="<%=ctxPath%>/ueditor/ueditor.all.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="gbk" src="<%=ctxPath%>/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body  style="width:854px;height:632px;overflow-x: hidden;overflow-y: auto;  ">
<form action="" id="rmbform" name="rmbform" style="width:854px;height:630px;overflow: hidden;  ">
<input type="hidden"  name="tabIndex" value="1" id="tabIndex" alt="tab页号">
	<div class="main_div">
		<div class="inner_div left_div">
			<div class="top_div">
				<div style="width:100%;height:100%;">
					<div id="tabs1" class="top_tab_style active" onclick="tabSwitch('tabs','page',1)">任免审批表(一)</div>
					<div id="tabs2" class="top_tab_style" onclick="tabSwitch('tabs','page',2)">任免审批表(二)</div>
					<div id="tabs3" class="top_tab_style" style="width:90px;" onclick="tabSwitch('tabs','page',3)">其他信息</div>
					<div id="personInfo" style="width:230px;height: 42px;font-size: 12px; font: normal;overflow: hidden; text-align: center;padding-top:5px;  " align="center" title=""></div>
				</div>
			</div>
			<div class="resume_div" id="table_div">
				<table id="person_resume" cellpadding="0px;" style="position: relative;">
					<tbody id="page1">
						<tr>
							<td class="width-60 height-40 fontConfig label_color"><font color="red">*</font>姓&nbsp;名</td>
							<td class="width-80 height-40">
								<tags:rmbNormalInput property="a0101" offsetTop="1" cls="width-80 height-40 no-y-scroll" label="姓名" defaultValue="<%=SV(a01.getA0101()) %>"
title="姓名字数不能超过18" textareaStyle="display:none;text-align:center;" textareaCls="cellbgclor x-form-field" 
onkeypress="namevalidator" type="true"/>
							</td>
							<td class="width-60 height-40 fontConfig label_color"><font color="red">*</font>性&nbsp;别</td>
							<td class="width-80 height-40 ">
								<tags:rmbSelect property="a0104" cls="height-40 input-text2" label="性别" selectTDStyle="width:80px;" selectDivStyle="width:300px;"
textareaStyle="width:80px;line-height:40px !important;" codetype="GB2261" defaultValue="<%=SV(a01.getA0104()) %>"/>
							</td>
							<td class="width-60 height-40 fontConfig label_color"><font color="red">*</font>出&nbsp;生<br/>&nbsp;年&nbsp;月</td>
							<td class="width-80 height-40 ">
								<tags:rmbDateInput type="true" property="a0107" offsetTop="1" cls="width-80 height-40 no-y-scroll" label="出生年月"  defaultValue="<%=SV(a01.getA0107()) %>"
title="" textareaStyle="display:none;text-align:center;"  textareaCls="cellbgclor x-form-field"/>
							</td>
							<td class="width-120 height-140" rowspan="4">
								<img alt="照片" id="personImg" style="cursor:pointer;" onclick="showdialog()" width="131" height="100%" src="<%= request.getContextPath()%>/servlet/DownloadUserHeadImage?a0000=<%=URLEncoder.encode(URLEncoder.encode(a01.getA0000(),"UTF-8"),"UTF-8")%>"> 
							</td>

						</tr>
						<tr>
							<td class="width-60 height-40 fontConfig label_color" ><font color="red">*</font>民&nbsp;族</td>
							<td class="width-80 height-40 ">
								<tags:rmbSelect property="a0117" cls="height-40 input-text2" label="民族" 
textareaStyle="width:80px;line-height:40px !important;" codetype="GB3304" defaultValue="<%=SV(a01.getA0117()) %>"/>
							</td>

							<td class="width-60 height-40 fontConfig label_color"><font color="red">*</font>籍&nbsp;贯</td>
							<td class="width-80 height-40 ">
								<tags:rmbPopWinInput property="a0111" cls="width-80 height-40 no-y-scroll" label="籍贯" 
title="" textareaStyle="display:none;text-align:center;" textareaCls="cellbgclor x-form-field" codetype="ZB01" codename="code_name3"
 defaultValue="<%=SV(a01.getComboxArea_a0111()) %>" hiddenValue="<%=SV(a01.getA0111()) %>"/>
							</td>
							<td class="width-60 height-40 fontConfig label_color"><font color="red">*</font>出生地</td>
							<td class="width-80 height-40 ">
								<tags:rmbPopWinInput property="a0114" cls="width-80 height-40 no-y-scroll" label="出生地" 
title="" textareaStyle="display:none;text-align:center;" textareaCls="cellbgclor x-form-field" codetype="ZB01" codename="code_name3"
 defaultValue="<%=SV(a01.getComboxArea_a0114()) %>" hiddenValue="<%=SV(a01.getA0114()) %>"/>
							</td>
						</tr>
						<tr>
							<td class="label-clor width-60 height-40 fontConfig label_color">入&nbsp;党时&nbsp;间</td>
							<td class="label-clor width-80 height-40 " onkeypress="a0140Click2()">
								<tags:rmbNormalInput offsetTop="1" property="a0140" ondblclick="a0140Click()" cls="width-80 height-40 no-y-scroll" label="入党时间" readonly="true"
title="" textareaStyle="display:none;text-align:center;" textareaCls="cellbgclor x-form-field" divStyle="width:80px;text-align:center;" defaultValue="<%=SV(a01.getA0140()) %>"/>
							</td>
							<td class="label-clor width-60 height-40 fontConfig label_color"><font color="red">*</font>参加工<br/>&nbsp;作时间</td>
							<td class="label-clor width-80 height-40 ">
								<tags:rmbDateInput property="a0134" offsetTop="1" cls="width-80 height-40 no-y-scroll" label="参加工作时间" 
title="" textareaStyle="display:none;text-align:center;" textareaCls="cellbgclor x-form-field" defaultValue="<%=SV(a01.getA0134()) %>"/>
							</td>
							<td class="label-clor width-60 height-40 fontConfig label_color"><font color="red">*</font>健&nbsp;康<br/>&nbsp;状&nbsp;况</td>
							<td class="label-clor width-80 height-40 ">
								<tags:rmbSelect property="a0128" cls="height-40 input-text2" label="健康情况" selectDivStyle="width:560px;"
textareaStyle="width:80px;line-height:40px !important;" codetype="GB2261D" defaultValue="<%=SV(a01.getA0128()) %>"/>
							</td>
						</tr>
						<tr>
							<td class="label-clor width-60 height-40 fontConfig label_color">专业技<br/>术职务</td>
							<td class="label-clor height-40" colspan="2">
								<tags:rmbNormalInput offsetTop="1" property="a0196" cls="width-140 height-40 no-y-scroll" label="专业技术职务" 
title="" textareaStyle="display:none;text-align:center;" ondblclick="a0196Click()"
textareaCls="cellbgclor x-form-field" defaultValue="<%=SV(a01.getA0196()) %>"/>
							</td>
							<td class="label-clor  height-40 fontConfig label_color">熟悉专业<br/>有何特长</td>
							<td class="label-clor height-40" colspan="2">
								<tags:rmbNormalInput offsetTop="1" property="a0187a" cls="width-140 height-40 no-y-scroll" label="熟悉专业有何特长" 
title="" textareaStyle="display:none;text-align:center;" textareaCls="cellbgclor x-form-field" defaultValue="<%=SV(a01.getA0187a()) %>"/>
							</td>
						</tr>
						<tr>
							<td class=" fontConfig label_color" rowspan="4">学&nbsp;历<br/><br/>学&nbsp;位</td>
							<td class=" fontConfig label_color" rowspan="2">全日制<br/>教&nbsp;&nbsp;育</td>
							<td class="label-clor height-30" colspan="2">
								<input id="qrzxl" name="qrzxl"  class="height-30 input-text2"  label="全日制教育：学历"
readonly="readonly"  value="<%=SV(a01.getQrzxl()) %>" ondblclick="xlxwClick()" style="width:140px;">
							
							</td>
							<td class=" fontConfig label_color" rowspan="2">毕业院校系及专业</td>
							<td class="label-clor height-30" colspan="2">
								<input id="qrzxlxx" name="qrzxlxx" class="height-30 input-text2" label="院校系及专业(学历)"
required="false" readonly="readonly"  value="<%=SV(a01.getQrzxlxx()) %>" ondblclick="xlxwClick()" style="width:200px;text-align:left;">
							</td>
						</tr>
						<tr>
							<td class="label-clor height-30" colspan="2">
								<input id="qrzxw" name="qrzxw" class="height-30 input-text2"  label="全日制教育：学位"
required="false" readonly="readonly"  value="<%=SV(a01.getQrzxw()) %>" ondblclick="xlxwClick()" style="width:140px;">
							</td>
							<td class="label-clor height-30" colspan="2">
								<input id="qrzxwxx" name="qrzxwxx" class="height-30 input-text2" label="院校系及专业(学位)"
required="false" readonly="readonly"  value="<%=SV(a01.getQrzxwxx()) %>" ondblclick="xlxwClick()" style="width:200px;text-align:left;">
							</td>
						</tr>
						<tr>
							<td class=" fontConfig label_color" rowspan="2">在&nbsp;&nbsp;职<br/>教&nbsp;&nbsp;育</td>
							<td class="label-clor height-30" colspan="2">
								<input id="zzxl" name="zzxl" class="height-30 input-text2"  label="在职教育：学历"
required="false" readonly="readonly"  value="<%=SV(a01.getZzxl()) %>" ondblclick="xlxwClick()" style="width:140px;">
							</td>
							<td class="width-60 fontConfig label_color" rowspan="2">毕业院校系及专业</td>
							<td class="label-clor height-30" colspan="2">
								<input id="zzxlxx" name="zzxlxx" class="height-30 input-text2" label="院校系及专业(学历)"
required="false" readonly="readonly"  value="<%=SV(a01.getZzxlxx()) %>" ondblclick="xlxwClick()" style="width:200px;text-align:left;">
							</td>
						</tr>
						<tr>
							<td class="label-clor height-30" colspan="2">
								<input id="zzxw" name="zzxw" class="height-30 input-text2"  label="在职教育：学位"
required="false" label="在职教育：学历" readonly="readonly" value="<%=SV(a01.getZzxw()) %>" ondblclick="xlxwClick()" style="width:140px;">
							</td>
							<td class="label-clor height-30" colspan="2">
								<input id="zzxwxx" name="zzxwxx"  class="height-30 input-text2" label="院校系及专业(学位)"
required="false" readonly="readonly" value="<%=SV(a01.getZzxwxx()) %>" ondblclick="xlxwClick()" style="width:200px;text-align:left;">
							</td>
						</tr>
						<tr>
							<td class="height-30 fontConfig label_color" colspan="2"><font color="red">*</font>工作单位及职务</td>
							<td class="height-30" colspan="5">
								<input id="a0192a" name="a0192a" class="height-30 input-text2" label="工作单位及职务全称"
required="false" readonly="readonly" ondblclick="a0192aClick()" value="<%=SV(a01.getA0192a()) %>"  style="width:405px;text-align:left;">
							</td>
						</tr>

						<tr>
							<td class="label_color width-60 height-330">
								<div style="width: 100%;height: 100%;position: relative;">
									<div onclick="showwin();" title="简历生成" style="width:100%;height:50%;background:url(images/jl.png) no-repeat center 10px;cursor:pointer;"></div>
									<span style="" class="fontConfig"><font color="red">*</font><br>简<br>历</span>
								</div>
							</td>
							<td class="height-330" colspan="6">
								<div class="height-330" style="width:100%; overflow-y: scroll;overflow-x: hidden;">
									<script id="editor" type="text/plain"  style="width:100%;visibility:hidden; height: 233px"></script>
								</div>
								
								<%-- <textarea id="a1701Show" rows="3" cols="6" class="height-330" style="width:100%;" ondblclick="resumeClick()" 
								readonly="readonly"><%=SV(AddRmbPageModel.formatJL(a01.getA1701(), new StringBuffer())) %></textarea> --%>
							</td>
						</tr>
					</tbody>
					<tbody id="page2" style="display:none">
						<tr>
							<td class="width-60 height-76 fontConfig label_color">奖惩综述</td>
							<td class="height-76" colspan="6">
								<textarea id="a14z101" name="a14z101" rows="3" cols="6" class="height-76 maxWidth-485 font-left" label="奖惩综述" 
readonly="readonly" ondblclick="$h.openPageModeWin('rewardPunish','pages.publicServantManage.RewardPunishAddPage','奖惩情况',810,480,document.getElementById('a0000').value,ctxPath,null,{maximizable:false,resizable:false})" ><%=SV(a01.getA14z101()) %></textarea>
							</td>
						</tr>
						<tr>
							<td class="width-60 height-76 fontConfig label_color">年度考核<br/>结果综述</td>
							<td class="height-76" colspan="6">
								<textarea id="a15z101" name="a15z101" rows="3" cols="6" class="height-76 maxWidth-485 font-left" label="年度考核结果综述" 
readonly="readonly" ondblclick="$h.openPageModeWin('assessmentInfo','pages.publicServantManage.AssessmentInfoAddPage','年度考核情况',800,330,document.getElementById('a0000').value,ctxPath,null,{maximizable:false,resizable:false})" ><%=SV(a01.getA15z101()) %></textarea>
							</td>
						</tr>
						<tr>
							<td class="width-60 fontConfig label_color" rowspan="11">
								<div class="height-340" style="width:100%;position:relative;">
									<div id="upb" style="margin-top:3px;margin-left:3px;position:relative;z-index:2;width:53px;height:20px;border-radius:5px;-moz-border-radius:5px;border:1px solid #7b9ebd;background:url(images/up.png) transparent no-repeat 3px center;cursor:pointer;" onclick="up(-1)">
										<span style="line-height:20px;padding-left:18px;font-size:13px;">上移</span>
									</div>
									<div id="downb" style="margin-top:5px;margin-left:3px;margin-bottom:10px;position:relative;z-index:2;width:53px;height:20px;border-radius:5px;-moz-border-radius:5px;border:1px solid #7b9ebd;background:url(images/down.png) transparent no-repeat 3px center;cursor:pointer;" onclick="up(1)">
										<span style="line-height:20px;padding-left:18px;font-size:13px;">下移</span>
									</div>
									<div style="height:258px;padding-top:10px;">
										<span style="position:relative;vertical-align:middle;" class="fontConfig">家<br/>庭<br/>主<br/>要<br/>成<br/>员<br/>及<br/>社<br/>会<br/>重<br/>要<br/>关<br/>系</span>
									</div>
									
									<div id="addrowBtn" style="margin-top:10px;position:relative;margin-left:3px;width:53px;border:1px solid #7b9ebd;background:url(images/delete.png) transparent no-repeat 1px center;border-radius:5px;-moz-border-radius:5px;cursor:pointer;" onclick="addA36row()">
										<span style="line-height:20px;padding-left:18px;font-size:13px;">删除</span>
									</div>
								</div>
							</td>
							<td class="width-80 height-30-1 label_color fontConfig">称谓</td>
							<td class="width-80 height-30-1 label_color fontConfig">姓名</td>
							<td class="width-80 height-30-1 label_color fontConfig">出生日期</td>
							<td class="width-80 height-30-1 label_color fontConfig">政治面貌</td>
							<td class="width-95 height-30-1 label_color fontConfig">工作单位及职务
							
							<script>
								Ext.onReady(function(){
									tabSwitch('tabs','page',2);
								});
							</script>
							</td>
						</tr>
						
						
						
						
						<% for(Integer i=1;i<=10;i++){
							A36 a36 = new A36();
							if(i<=lista36Length){
								a36 = (A36)lista36.get(i-1);
							}
							int ichar = i+97;
						String tr_i = "tr_"+i;
						String a3604a_i = "a3604a_"+(char)ichar;
						String a3601_i = "a3601_"+(char)ichar;
						String a3607_iF = "a3607_"+(char)ichar+"F";
						String a3607_i = "a3607_"+(char)ichar;
						String a3627_i = "a3627_"+(char)ichar;
						String a3611_i = "a3611_"+(char)ichar;
						String a3600_i = "a3600_"+(char)ichar;
						%>
					<tr id="<%=tr_i %>" onclick="getNum(<%=i %>)">
						<td class="width-80 height-30-1">
							<tags:rmbSelect property="<%=a3604a_i %>" codetype="GB4761" cls="height-30-1 input-text2" label="称谓" outSelect="true"
selectDivStyle="width:560px;" textareaStyle="width:80px;line-height:36px !important;" defaultValue="<%=SV(a36.getA3604a()) %>"/>
						</td>
						<td class="width-80 height-30-1">
							<tags:rmbNormalInput offsetTop="0" property="<%=a3601_i %>"  cls="width-80 height-30-1  no-y-scroll" 
textareaStyle="display:none;text-align:center;" divStyle="" textareaCls="cellbgclor x-form-field" title=""	onkeypress="namevalidator"
defaultValue="<%=SV(a36.getA3601()) %>"/>
						</td>
						<td class="width-80 height-30-1" >
							<tags:rmbDateInput property="<%=a3607_i %>" offsetTop="5" cls="width-80 height-30-1 no-y-scroll" label="家庭成员：出生年月"
textareaStyle="display:none;text-align:center;" textareaCls="cellbgclor x-form-field" defaultValue="<%=SV(a36.getA3607()) %>" title=""/>
						</td>
						<td class="width-80 height-30-1">
							<tags:rmbSelect property="<%=a3627_i %>" codetype="GB9999" cls="height-30-1 input-text2" 
selectDivStyle="width:560px;" textareaStyle="width:80px;line-height:36px !important;" defaultValue="<%=SV(a36.getA3627()) %>"/>
						</td>
						<td class="width-95 height-30-1">
							<tags:rmbNormalInput offsetTop="0" property="<%=a3611_i %>"  cls="width-95 height-30-1  no-y-scroll"  title=""	
textareaStyle="display:none;text-align:center;" textareaCls="cellbgclor x-form-field" defaultValue="<%=SV(a36.getA3611()) %>" divStyle="width:156px;"	/>
							<odin:hidden property="<%=a3600_i %>" value="<%=SV(a36.getA3600()) %>"/>
						</td>
					</tr>
					<%} %>
						
					</tbody>
				</table>
			</div>
			<script>
				Ext.onReady(function(){
					tabSwitch('tabs','page',1);
				});
			</script>
			<div class="resume_div" id="page3" style="display:none;">
				<table id="remark" cellpadding="0px;" style="position: relative;">
					
						<!-- 页面修改 -->
						<tr>
							<td class="width-60 fontConfig label_color" style="height:350px;">其<br/>它<br/>信<br/>息</td>
							<td style="height:350px;width:490px;">

								<div style="height:100%;width:100%;">


										<table align="center" bgcolor="white" width="100%" cellspacing="0px;" style='table-layout: fixed;'>  
												
												<tr style="height:50px">  
													
													<td bgcolor="white"  align="center" style="padding-left:3%;width:47%;border-left:0px;border-top:0px">

															<div style="width:100%;height:50px;line-height:50px;">
																	<div style="height:100%;float:left;width:70px;text-align:left;"><label style="font-size: 13px;">是否考录</label></div>
																	
																	<input type="checkbox" name="a99z101" id="a99z101" onclick="disableInput(this,'a99z102')" style="float:left;width:13px;margin-top:16px;margin-left:5px;" >							
																</div>
													</td>  
													<td bgcolor="white"  align="center" style="padding-left:3%;width:47%;border-top:0px">
															<div style="width:100%;height:50px;line-height:50px;">
															<div style="float:left;width:70px;text-align:left;"><span id="a99z102SpanId_s" style="font-size: 13px;">录用时间</span></div>
															<tags:rmbDateInput2  property="a99z102" cls="" label="任现职务层次时间" defaultValue="<%=SV(a99Z1.getA99z102()) %>"
					title="" textareaStyle="width:150px;height:21px;float:left;margin-top:15px;margin-left:5px;" warnCls="float:left;margin-left:3px;" textareaCls="cright_input_btn linehack"/>
													</div>
													</td>  
												</tr>  

												<tr style="height:50px;">  
													 
													<td bgcolor="white"  align="center" style="padding-left:3%;width:47%;border-left:0px">
														<div style="width:100%;height:50px;line-height:50px;">
															<div style="float:left;width:70px;text-align:left;"><label style="font-size: 13px;">是否选调生</label></div>
															<input type="checkbox" name="a99z103" id="a99z103" onclick="disableInput(this,'a99z104')" style="float:left;width:13px;margin-top:16px;margin-left:5px;" >
													    </div>
													</td>  
													<td bgcolor="white"  align="center" style="padding-left:3%;width:47%">
															<div style="width:100%;height:50px;line-height:50px;">
																	<div style="float:left;width:60px;text-align:left;"><span id="a99z104SpanId_s" style="font-size: 13px;line-height:25px;text-align:left;">进入选调生&nbsp;&nbsp;时&nbsp;间</span></div>
																	<tags:rmbDateInput2  property="a99z104" cls="" label="进入选调生时间" defaultValue="<%=SV(a99Z1.getA99z104()) %>"
							title="" textareaStyle="width:150px;height:21px;float:left;margin-top:14px;margin-left:15px;" warnCls="float:left;margin-left:3px;" textareaCls="cright_input_btn linehack"/>
																</div>
													</td>  
												</tr>  

												<tr style="height:50px;">  
												   
													<td bgcolor="white"  align="center" style="padding-left:3%;width:47%; border-left:0px">
															<div style="width:100%;height:50px;line-height:50px;">
																	<div style="height:100%;float:left;width:50px;text-align:left;"><label style="font-size: 13px;">成长地</label></div>
																	<tags:rmbPopWinInput2 property="a0115a"  label="成长地" 
							title="" textareaStyle="width:134px;float:left;margin-top:14px;margin-left:5px;height:21px;" textareaCls="right_input_btn linehack" codetype="ZB01" divStyle="margin-top:14px;"
							 defaultValue="" hiddenValue="<%=SV(a01.getA0115a()) %>"/>
																</div>
													</td>  
													<td bgcolor="white"  align="center" style="padding-left:3%;width:47%;">
															<div style="width:100%;height:50px;line-height:50px;">
																	<div style="height:100%;float:left;width:70px;text-align:left;"><span style="font-size: 13px;">&nbsp;&nbsp;&nbsp;&nbsp;级&nbsp;&nbsp;别</span></div>
																	<tags:rmbPopWinInput2 property="a0120"  label="级别" 
							title="" textareaStyle="width:134px;float:left;margin-top:14px;margin-left:5px;height:21px;" textareaCls="right_input_btn linehack" codetype="ZB134" divStyle="margin-top:14px;"
							 defaultValue="" hiddenValue="<%=SV(a01.getA0120()) %>"/>
																										</div>
													
													</td>  
												</tr> 

												<tr style="height:50px;">  
												   
													<td bgcolor="white"  align="left" colspan="2" style='padding-left:3%;width:47%;border-left:0px;border-bottom: 1px solid #74A6CC'>
															<div style="width:100%;height:50px;line-height:50px;">
																	<div style="height:100%;float:left;width:160px;text-align:left;"><span style="font-size: 13px;">专业技术类公务员任职资格</span></div>
																	<tags:rmbPopWinInput2 property="a0122"  label="专业技术类公务员任职资格" 
							title="" textareaStyle="width:134px;float:left;margin-top:14px;margin-left:5px;height:21px;" textareaCls="right_input_btn linehack" codetype="ZB139" divStyle="margin-top:14px;"
							 defaultValue="" hiddenValue="<%=SV(a01.getA0122()) %>"/>
																</div>
													</td>  
													  
												</tr>  
											</table>  
								
								</div>
							</td>
						</tr>

						<tr>
							<td class="width-60 fontConfig label_color" style="height:209px;">备注</td>
							<td style="height:209px;width:490px;">
								<textarea id="a0180" name="a0180" rows="3" cols="6" label="备注" style="width:100%;height:100%"><%=SV(a01.getA0180()) %></textarea>
							</td>
						</tr>

					
				</table>
			</div>

		</div>
		<div class="inner_div right_div">
			<div class="top_div pic1" style="background-color:#92B3D4">
				<span style="" id="a0163Text">
<script type="text/javascript">
function setA0163Text(value){
	if(CodeTypeJson.ZB126[value]){
		document.getElementById("a0163Text").innerHTML=CodeTypeJson.ZB126[value];
	}
}
Ext.onReady(function(){
	setA0163Text('<%=SV(a01.getA0163()) %>');
});

</script></span>
			</div>
			<div class="right_table">
				<table style="height:90%;width:100%;padding-top:10px;position: relative;" id="leftTable">
					<tr>
						<td class="right_td1"><font color="red">*</font>统计关系所在单位</td>
						<td class="right_td2">
							<tags:rmbPopWinInput2 property="a0195"  label="统计关系所在单位" 
title="" textareaStyle="width:114px;float:left;" textareaCls="right_input_btn" codetype="orgTreeJsonData" 
 defaultValue="<%=SV(a0195Text) %>" hiddenValue="<%=SV(a01.getA0195()) %>"/>
						</td>
					</tr>
					<tr>
						<td class="right_td1"><font color="red">*</font>身份证号</td>
						<td class="right_td2"><input type="text" name="a0184" id="a0184" value="<%=SV(a01.getA0184()) %>" class="right_input_btn" maxlength="18"></td>
					</tr>
					<tr>
						<td class="right_td1"><font color="red">*</font>管理类别</td>
						<td class="right_td2" >
							<tags:rmbSelect property="a0165" cls=" right_option" label="管理类别"
textareaStyle="height:23px;" codetype="ZB130" defaultValue="<%=SV(a01.getA0165()) %>" selectTDStyle="width:130px;" />
						</td>
					</tr>
					<tr>
						<td class="right_td1"><font color="red">*</font>人员类别</td>
						<%-- <td class="right_td2">
							<tags:rmbPopWinInput2 property="a0160"  label="人员类别" 
title="" textareaStyle="width:114px;float:left;" textareaCls="right_input_btn" codetype="ZB125" 
 defaultValue="" hiddenValue="<%=SV(a01.getA0160()) %>"/>
						</td> --%>
						
						<td class="right_td2" >
							<tags:rmbSelect property="a0160" cls=" right_option" label="人员类别"
textareaStyle="height:23px;" codetype="ZB125" defaultValue="<%=SV(a01.getA0160()) %>" selectTDStyle="width:170px;" selectMenuStyle="width:170px;"/>
						</td>
					</tr>
					<tr>
						<td class="right_td1"><font color="red">*</font>编制类型</td>
						<td class="right_td2">
							<tags:rmbSelect property="a0121" cls=" right_option" label="编制类型" 
textareaStyle="height:23px;" codetype="ZB135" defaultValue="<%=SV(a01.getA0121()) %>" selectTDStyle="width:130px;" />
						</td>
					</tr>
					<tr>
						<td class="right_td1">现职务层次</td>
						<td class="right_td2">
							<input type="text" class="right_input_btn" id="comboxArea_a0221" ondblclick="a0221Click()" readonly="readonly" style="width:114px;float:left;">
							<div id = "comboxImg_a0221" class="right_qry_div" style="" onclick="a0221Click()"></div>
						</td>
					</tr>
					<tr>
						<td class="right_td1">任现职务层次时间</td>
						<td class="right_td2">
							<tags:rmbDateInput2  property="a0288" cls="" label="任现职务层次时间" readonly="readonly" ondblclick="a0221Click()"
defaultValue="<%=SV(a01.getA0288()) %>"
title="" textareaStyle="" textareaCls="cright_input_btn"/>
							
						</td>
					</tr>
					<tr>
						<td class="right_td1">现职级</td>
						<td class="right_td2">
							<input type="text" id="comboxArea_a0192e" class="right_input_btn" ondblclick="a0192eClick()" readonly="readonly" style="width:114px;float:left;">
							<div id = "comboxImg_a0192e" class="right_qry_div" style="null" onclick="a0192eClick()"></div>
						</td>
					</tr>
					<tr>
						<td class="right_td1">任现职级时间</td>
						<td class="right_td2">
							<tags:rmbDateInput2  property="a0192c" cls="" label="任现职级时间" readonly="readonly" ondblclick="a0192eClick()"
defaultValue="<%=SV(a01.getA0192c()) %>"
title="" textareaStyle="" textareaCls="cright_input_btn"/>
						</td>
					</tr>
					
					<tr>
						<td class="right_td1">公务员登记时间</td>
						<td class="right_td2">
							<tags:rmbDateInput2  property="a2949" cls="" label="公务员登记时间"
								defaultValue="<%=SV(a01.getA2949()) %>" title="" textareaStyle="" textareaCls="cright_input_btn"/>
						</td>
					</tr>
				</table>
			</div>
			<div style="width:90%;height:40px;margin-top:10px;">
				<div class="top_btn_style" onclick="showNext('-1')">上一人</div>
				<div class="top_btn_style" onclick="showNext('1')" >下一人</div>
				<div class="top_btn_style" style="width:100px;" onclick="prtRmb()">打印任免表</div>
			</div>
			<div style="width:100%;height:70px;">
				<div class="btn_div">
					<div class="btn_style" style="background-color:#3680C9;margin-left:38%;" onclick="addnew()">继续增加</div>
				</div>
				<div class="btn_div">
					<div class="btn_style" style="background-color:#F08000;margin-left:10px;" onclick="savePerson()"  >保&nbsp;&nbsp;存</div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" value="<%=a0000 %>" name="a0000" id="a0000" alt="人员id">
	<input type="hidden" value="<%=SV(a01.getA0141()) %>" name="a0141" id="a0141" alt="政治面貌">
	<input type="hidden" value="<%=SV(a01.getA3921()) %>" name="a3921" id="a3921" alt="第二党派">
	<input type="hidden" value="<%=SV(a01.getA3927()) %>" name="a3927" id="a3927" alt="第三党派">
	<input type="hidden" value="<%=SV(a01.getA0144()) %>" name="a0144" id="a0144" alt="入党时间">
	<input type="hidden" value="<%=SV(a01.getA0192()) %>" name="a0192" id="a0192" alt="现工作单位及职务简称">
	<input type="hidden" value="<%=SV(a01.getA0163()) %>" name="a0163" id="a0163" alt="人员管理状态">
	<!-- 1：有修改权限，2：无修改权限 -->
	<input type="hidden" value="1" name="isUpdate" id="isUpdate" alt="是否有修改权限">
	<input type="hidden" value="<%=SV(a01.getA0148()) %>" name="a0148" id="a0148" alt="职务层级">
	<input type="hidden" value="<%=SV(a01.getA0221()) %>" name="a0221" id="a0221" alt="现职务层次">
	<input type="hidden" value="<%=SV(a01.getA0192e()) %>" name="a0192e" id="a0192e" alt="现职级">
	<input type="hidden" value="<%=SV(a01.getA1701()) %>" name="a1701" id="a1701" alt="简历">
	<input type="hidden" value="<%=SV(a99Z1.getA99Z100()) %>" name="a99Z100" id="a99Z100" alt="id">
	<input type="hidden" id="pdfPath" alt="pdf路径">
	<input type="hidden" id="familyRowNum" alt="家庭成员的行号">
	
	<input type="hidden" value="<%=SV(a99Z1.getA99z101()) %>" name="a99z101F" id="a99z101F" alt="是否考录">
	<input type="hidden" value="<%=SV(a99Z1.getA99z103()) %>" name="a99z103F" id="a99z103F" alt="是否选调生">
	
	</form>
<script>
var newwin = new Ext.Window({
	contentEl: "jlcontent",
	title : '简历',
	layout : 'fit',
	width : 770,
	overflow : 'hidden',
	height : 470,
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
	
//简历生成模式
function selectall2(){
	var contenttext = document.getElementById("contenttext2").value;
	document.getElementById("a1701").value=contenttext;
	setContent();
	var jlfl = Ext.getCmp("jlfl");
	jlfl.hide();
}
//简历生成模式
function selectall(){
	var contenttext = document.getElementById("contenttext").value;
	document.getElementById("a1701").value=contenttext;
	setContent();
	var jlfl = Ext.getCmp("jlfl");
	jlfl.hide();
}
function showwin(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
	 document.getElementById("contenttext").value=document.getElementById("a1701").value.trim();
 		document.getElementById("jlcontent").style.display="block";
 		var jlfl = Ext.getCmp("jlfl");
 		Ext.getCmp("jltab").activate("tab2");
 		if(!jlfl.rendered){  
 			jlfl.show();//alert("no reader")  
 		}else if(jlfl.hidden){  
 			jlfl.show();//alert("hidden")  
 		}else{  
 			jlfl.hide();//alert("show")  
 		}
}
</script>
<%--简历生成--%>
	<div id="jlcontent" style="display: none;width:752px">
	    <odin:tab id="jltab">
		    <odin:tabModel>
		    	<odin:tabItem title="简历文本编辑" id="tab1"></odin:tabItem>
		    	<odin:tabItem title="按职务自动生成简历" id="tab2" isLast="true"></odin:tabItem>
		    </odin:tabModel> 
		    <odin:tabCont itemIndex="tab1" >
				<textarea style="height:370px;width: 750px;" id="contenttext" style="font-family: '宋体', Simsun;"></textarea>
				<odin:button property="qx" text="&nbsp;&nbsp;确&nbsp;&nbsp;定&nbsp;&nbsp;" handler="selectall"></odin:button>
			</odin:tabCont>
		    <odin:tabCont itemIndex="tab2">
		    	<textarea style="height:370px;width: 750px;" id="contenttext2" style="font-family: '宋体', Simsun;"></textarea>
		    	<odin:button property="qx2" text="&nbsp;&nbsp;确&nbsp;&nbsp;定&nbsp;&nbsp;" handler="selectall2"></odin:button>	
		    </odin:tabCont>
	    </odin:tab>
	</div>	
	
<script type="text/javascript" src="js/divscroll.js"></script>

<script type="text/javascript">
/* 打印任免表 */
function prtRmb(){
	Ext.Msg.wait('请稍候...','系统提示：');
	var Id = document.getElementById("a0000").value;
	Ext.Ajax.request({
	 	method: 'POST',
	 	timeout: 100000,
	 	params: {'a0000':Id},
	 	url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddRmb&eventNames=printFalse",
	 	success: function(response, options){
	 		var result = Ext.util.JSON.decode(response.responseText);
	 		Ext.Msg.hide();
	 		if(result.resType==1){
				//$h.openWin('pdfViewWin','pages.publicServantManage.PdfView','打印任免表',700,500,1,ctxPath,null,{maximizable:true});
				
				$h.openPageModeWin('pdfViewWin','pages.publicServantManage.PdfView','打印任免表',700,500,1,ctxPath,null,{maximizable:true});
			}else{
				odin.alert('请先保存人员信息！');
			}
	 		
	 	},
	 	failure : function(response, options){
	 		Ext.Msg.hide();
	 		odin.alert('请求超时！');
		}
	});
}

/* 家庭成员上移下移 */
function up(upOrDown){
	var familyRowNum = document.getElementById('familyRowNum').value;//当前行号
	if(upOrDown == '-1'){//点击的是上移按钮
		//如果是1，到顶
		if(familyRowNum == 1){
			odin.alert("已经是第一位");
			return;
		}
	}
	
	var newFamilyNum = changeABC(familyRowNum);
	var upRow = parseInt(familyRowNum) + parseInt(upOrDown); //上移的行号
	var newUpRow = changeABC(upRow);
	if(upOrDown == '1'){//点击的是下移按钮
		//判断下面是否有人或大于10人
		var a3600 = document.getElementById("a3600_"+newUpRow).value;
		if(!a3600){
			odin.alert("已经是最后一位");
			return;
		}
	}
	
	var mycars=new Array("a3604a_","a3601_","a3607_","a3627_","a3611_","a3600_");
	for(var i=0;i<mycars.length;i++){
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

/* 删除家庭人员 */
function addA36row(){
	var familyRowNum = document.getElementById('familyRowNum').value;
	var newFamilyNum = changeABC(familyRowNum);
	var A3600 = document.getElementById("a3600_"+newFamilyNum).value;
	if(!A3600){
		return;
	}
	
	$h.confirm("系统提示：","是否确认删除该条家庭成员信息？",200,function(id) { 
		if("ok"==id){
			
			//先后台删除处理
			Ext.Ajax.request({
			 	method: 'POST',
			 	timeout: 100000,
			 	params: {'a3600':A3600},
			 	url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddRmb&eventNames=deleteFamily",
			 	success: function(response, options){
			 		var result = Ext.util.JSON.decode(response.responseText);
			 		if(result.resType==1){
			 			//清除该条信息
						var mycars=new Array("a3604a_","a3601_","a3607_","a3627_","a3611_","a3600_");
						for(var i=0;i<mycars.length;i++){
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
							
							document.getElementById(mycars[i]+newFamilyNum).value = "";
							
							if(i!=5){
								eval(mycars[i]+newFamilyNum+text);
							}
						}
						//下面的信息上移
						for(var i=parseInt(familyRowNum);i<10;i++){
							newFamilyNum = changeABC(familyRowNum);
							var nextNum = parseInt(familyRowNum)+1;//上移的行号
							var newUpRow = changeABC(nextNum);
							var A3600 = document.getElementById("a3600_"+newUpRow).value;//取不到a3600则停止
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
							familyRowNum = parseInt(familyRowNum)+1;//当前行号+1
						}
					}else{
						odin.alert('删除失败！');
					}
			 		
			 	},
			 	failure : function(response, options){
			 		Ext.Msg.hide();
			 		odin.alert('请求超时！');
				}
			});
			
		}else{
			return;
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
 	$h.openPageModeWin('addPartyTime','pages.publicServantManage.AddPartyTimeAddPage','入党时间',500,250,document.getElementById('a0000').value,ctxPath);
}

var numC = 0;
function a0140Click2(){
	
	if(numC == 1){
		return;
	}
	
	
	numC = 1;
	
	
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openPageModeWin('addPartyTime','pages.publicServantManage.AddPartyTimeAddPage','入党时间',500,250,document.getElementById('a0000').value,ctxPath);
}


function a0196Click(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openPageModeWin('professSkill','pages.publicServantManage.ProfessSkillAddPage','专业技术职务',650,440,document.getElementById('a0000').value,ctxPath);
}
function xlxwClick(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openPageModeWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',870,435,document.getElementById('a0000').value,ctxPath);
}
function a0192aClick(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openPageModeWin('workUnits','pages.publicServantManage.WorkUnitsAddPage','工作单位及职务',960,540,document.getElementById('a0000').value,ctxPath,null,{maximizable:false,resizable:false});
}
function a0221Click(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openPageModeWin('RankAddPageWin','pages.publicServantManage.RankAddPage','现职务层次',695,320,document.getElementById('a0000').value,ctxPath);
}
function a0192eClick(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openPageModeWin('RradeRankAddPageWin','pages.publicServantManage.RradeRankAddPage','现职级',695,320,document.getElementById('a0000').value,ctxPath);
}
function resumeClick(){
	/* var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('resumeWin','pages.publicServantManage.resumeWin','简历',711,561,document.getElementById('a0000').value,ctxPath); */
 		
 		
	$h.showWindowWithSrc('resumeWin','<%=ctxPath%>/rmb/resume.jsp?','简历',811,661,null);
}


//统计所在单位回写
function setA0195Value(key,value){
	document.getElementById('a0195').value=key;
	document.getElementById('comboxArea_a0195').value=value;
	//radow.doEvent("setA0195Value",key+"|"+value);
}
//现职务层次回写 
function setA0221Value(key,value){
	document.getElementById('a0221').value=key;
	document.getElementById('comboxArea_a0221').value=value;
}
//现职务层次时间回写 
function setA0288Value(value){
	document.getElementById('a0288').value=value;
	var a0288_1 = getTime(value);
	document.getElementById('a0288_1').value=a0288_1;
}
//现职级回写
function setA0192eValue(key,value){
	document.getElementById('a0192e').value=key;
	document.getElementById('comboxArea_a0192e').value=value;
}
//现职级时间回写 
function setA0192cValue(value){
	document.getElementById('a0192c').value=value;
	var a0192c_1 = getTime(value);
	document.getElementById('a0192c_1').value=a0192c_1;
}



/**
 * 上一个下一个
 */
var actionNext = "1";
function showNext(action){
	
	actionNext = action;
	//检查任免表是否已更改，更改则提示保存
	//ajaxSubmit('isChangeNext',null);
	//return false;
	
	next();
	
}


function tishiNext(){
	$h.confirm3btn('系统提示','当前信息已经修改,是否需要保存',null,function(iid){
		if(iid=='yes'){
			
			//对数据进行保存 
			savePerson(1,2,null);
			
			
		}else if(iid=='no'){
			
			//关闭当前窗口 
			Ext.Msg.wait('请稍候...','系统提示：');
			storePam['action']=actionNext;
			//console.log(bbar)
			ajaxSubmit('showNextclick',storePam);
			
		}else if(iid=='cancel'){
			return false;
		}
		
		});
}


function next(){
	
	
	Ext.Msg.wait('请稍候...','系统提示：');
	storePam['action']=actionNext;
	//console.log(bbar)
	ajaxSubmit('showNextclick',storePam);
}

/**
 * 新增
 */
function addnew(){
	ajaxSubmit('addnew',storePam);
}

function savePerson(a,b,confirm){
	Ext.Msg.wait('请稍候...','系统提示：');
	var a0101 = document.getElementById('a0101').value;//姓名
	var a0184 = document.getElementById('a0184').value;//身份证号
	var a0107 = document.getElementById('a0107').value;//出生年月
	var orthersWindow = null;
	//校验身份证是否合法
	if(a0184==''){
		$h.alert('系统提示：','身份证号不能为空!',null,220);
		return false;
	}
	if(a0184.length>18){
		$h.alert('系统提示：','身份证号不能超过18位!',null,220);
		return false;
	}
	
	//身份证验证
	//var vtext = $h.IDCard(a0184);
	var vtext = isIdCard(a0184);
	
	
	if(vtext!==true){
		//$h.alert('系统提示：',vtext,null,320);
		$h.confirm("系统提示：",vtext+'，<br/>是否继续保存？',400,function(id) { 
			if("ok"==id){
				//Ext.getCmp('a0184').clearInvalid();
				Ext.Msg.wait('请稍候...','系统提示：');
				savePersonSub(a,b,confirm,false);
			}else{
				return false;
			}		
		});
	}else{
		savePersonSub(a,b,confirm,true);
	}
}
function savePersonSub(a,b,confirm,isIdcard){
	var a0101 = document.getElementById('a0101').value.replace(/\s/g, "");//姓名
	var a0184 = document.getElementById('a0184').value;//身份证号
	var a0107 = document.getElementById('a0107').value;//出生年月
	var a0104 = document.getElementById('a0104').value;//性别
	var a0111_combo = document.getElementById('comboxArea_a0111').value;//籍贯
	var a0114 = document.getElementById('comboxArea_a0114').value.replace(/\s/g, "");//出生地
	var a0141 = document.getElementById('a0141').value;//政治面貌
	//var a0114_combo = document.getElementById('comboxArea_a0114').value;//出生地

	
	var a0117 = document.getElementById('a0117').value;//民族
	var a0128 = document.getElementById('a0128').value;//健康状况
	var a0134 = document.getElementById('a0134').value;//参加工作时间
	
	var a0195 = document.getElementById('a0195').value;//统计关系所在单位
	var a0160 = document.getElementById('a0160').value;//人员类别
	var a0165 = document.getElementById('a0165').value;//管理类别
	var a1701 = document.getElementById('a1701').value;//简历
	var a0121 = document.getElementById('a0121').value;//编制类型
	var a2949 = document.getElementById('a2949').value;//公务员登记时间
	
	
	if(a0101==''){
		$h.alert('系统提示：','姓名不能为空！',null,220);
		return false;
	}
	if(a0101.length==1){
		$h.alert('系统提示：','姓名不能为一个字！',null,220);
		return false;
	}
	//alert(a0101.substr(a0101.length-1,1)==/\./);
	if(/^[\.\·]/.test(a0101)){
		$h.alert('系统提示：','姓名不能以·开头！',null,220);
		return false;
	}
	if(/[\.\·][\.\·]/.test(a0101)){
		$h.alert('系统提示：','姓名不能连续出现2个·！',null,220);
		return false;
	}
	if(/[\.\·]$/.test(a0101)){
		$h.alert('系统提示：','姓名不能以·结尾！',null,220);
		return false;
	}
	if(a0101.length>18){
		$h.alert('系统提示：','姓名字数不能超过18！',null,220); 
		return false;
	}
	if(a0104==''){
		$h.alert('系统提示：','性别不能为空！',null,220); 
		return false;
	}
	if(a0107==''){
		$h.alert('系统提示：','出生年月不能为空！',null,220); 
		return false;
	}
	if(a0117==''){
		$h.alert('系统提示：','民族不能为空！',null,220); 
		return false;
	}
	if(a0111_combo==''){
		$h.alert('系统提示：','籍贯不能为空！',null,220); 
		return false;
	}
	if(a0114==''){
		$h.alert('系统提示：','出生地不能为空！',null,220); 
		return false;
	}
	/* if(a0141==''){
		$h.alert('系统提示：','政治面貌不能为空！',null,220); 
		return false;
	} */
	if(a0128==''){
		$h.alert('系统提示：','健康状况不能为空！',null,220); 
		return false;
	}
	if(a0134==''){
		$h.alert('系统提示：','参加工作时间不能为空！',null,220); 
		return false;
	}
	if(a1701==''){
		$h.alert('系统提示：','简历不能为空！',null,220); 
		return false;
	}
	if(a0195==''){
		$h.alert('系统提示：','统计关系所在单位不能为空！',null,220); 
		return false;
	}
	if(a0165==''){
		$h.alert('系统提示：','管理类别不能为空！',null,220); 
		return false;
	}
	if(a0160==''){
		$h.alert('系统提示：','人员类别不能为空！',null,220); 
		return false;
	}
	if(a0121==''){
		$h.alert('系统提示：','编制类型不能为空！',null,220); 
		return false;
	}
	/* if(a2949==''){
		$h.alert('系统提示：','公务员登记时间不能为空！',null,220); 
		return false;
	} */
	
	//公务员登记时间,合法性校验
	var a2949_1 = document.getElementById('a2949_1').value;//公务员登记时间
	
	var text2 = dateValidateBeforeTady(a2949_1);
	if(a2949_1.indexOf(".") > 0){
		text2 = dateValidateBeforeTady(a2949);
	}
	if(text2!==true){
		$h.alert('系统提示','公务员登记时间：' + text2, null,400);
		return false;
	}
	
	
	var a99z102 = document.getElementById('a99z102').value;//录用时间
	var a99z104 = document.getElementById('a99z104').value;//进入选调生时间 
	var a99z102_1 = document.getElementById('a99z102_1').value;
	var a99z104_1 = document.getElementById('a99z104_1').value;
	
	var text3 = dateValidateBeforeTady(a99z102_1);
	if(a99z102_1.indexOf(".") > 0){
		text3 = dateValidateBeforeTady(a99z102);
	}
	if(text3!==true){
		$h.alert('系统提示','录用时间：' + text3, null,400);
		return false;
	}
	
	
	var text4 = dateValidateBeforeTady(a99z104_1);
	if(a99z104_1.indexOf(".") > 0){
		text4 = dateValidateBeforeTady(a99z104);
	}
	if(text4!==true){
		$h.alert('系统提示','进入选调生时间：' + text4, null,400);
		return false;
	}
	
	//出生日期格式
	var datetext = $h.date(a0107);
	if(datetext!==true){
		$h.alert('系统提示：','出生年月：'+datetext,null,320);
		return false;
	}
	
	
	//其他信息页面校验
	if(document.getElementById('Iorthers')){
		orthersWindow = document.getElementById('Iorthers').contentWindow;
		if(orthersWindow){
			if (orthersWindow.formcheck() == true) {//全局校验
			}else{
				var tabs = Ext.getCmp('rmbTabs');
				tabs.activate(tabs.getItem('orthers'));
				return false;
			}
		}
	}
	//业务信息页面校验
	if(document.getElementById('IBusinessInfo')){
		orthersWindow = document.getElementById('IBusinessInfo').contentWindow;
		if(orthersWindow){
			if (orthersWindow.formcheck() == true) {//全局校验
			}else{
				var tabs = Ext.getCmp('rmbTabs');
				tabs.activate(tabs.getItem('IBusinessInfo'));
				return false;
			}
		}
	}
	
	var birthdaya0184 = getBirthdatByIdNo(a0184);
	var birthdaya0107 = document.getElementById('a0107').value;//出身年月
	var msg = '出生日期与身份证不一致，<br/>是否继续保存？';
	if(isIdcard&&(birthdaya0107==''||(birthdaya0107!=birthdaya0184&&birthdaya0107!=birthdaya0184.substring(0, 6)))){
		$h.confirm("系统提示：",msg,200,function(id) { 
			if("ok"==id){
				Ext.Msg.wait('请稍候...','系统提示：');
				ajaxSubmit('save.onclick',confirm);
			}else{
				return false;
			}		
		});	
		return false;
	}else{
		ajaxSubmit('save.onclick',confirm);
		//其他信息
		
	}
}
function ajaxSubmit(radowEvent,parm){
	if(parm){
	}else{
		parm = {};
	}
	Ext.Ajax.request({
		method: 'POST',
		form:'rmbform',
        async: true,
        params : parm,
        timeout :300000,//按毫秒计算
		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddRmb&eventNames="+radowEvent,
		success: function(resData){
			//alert(resData.responseText);
			var cfg = Ext.util.JSON.decode(resData.responseText);
			if(0==cfg.messageCode){
				Ext.Msg.hide();	
				
				if(cfg.elementsScript.indexOf("\n")>0){
					cfg.elementsScript = cfg.elementsScript.replace(/\r/gi,"");
					cfg.elementsScript = cfg.elementsScript.replace(/\n/gi,"\\n");
				}
				
				//console.log(cfg.elementsScript);
				eval(cfg.elementsScript);
				//alert(cfg.elementsScript);
				//console.log(cfg.mainMessage);
				if("操作成功！"!=cfg.mainMessage){
					Ext.Msg.alert('系统提示:',cfg.mainMessage);
				}
			}else{
				//Ext.Msg.hide();	
				
				if(cfg.mainMessage.indexOf("<br/>")>0){
					
					$h.alert('系统提示',cfg.mainMessage,null,380);
					return;
				}
				
				if("操作成功！"!=cfg.mainMessage){
					Ext.Msg.alert('系统提示:',cfg.mainMessage);
				}
			}
			
		},
		failure : function(res, options){ 
			Ext.Msg.hide();
			alert("网络异常！");
		}  
	});
}
//验证身份证号并获取出生日期
function getBirthdatByIdNo(iIdNo) {
	var tmpStr = "";
	var idDate = "";
	var tmpInt = 0;
	var strReturn = "";
	
	iIdNo = trim(iIdNo);
	if (iIdNo.length == 15) {
		tmpStr = iIdNo.substring(6, 12);
		tmpStr = "19" + tmpStr;
		tmpStr = tmpStr.substring(0, 4) + tmpStr.substring(4, 6) + tmpStr.substring(6)
		
		return tmpStr;
	} else {
		tmpStr = iIdNo.substring(6, 14);
		tmpStr = tmpStr.substring(0, 4) + tmpStr.substring(4, 6) + tmpStr.substring(6)
		return tmpStr;
	}
}
function trim(s) { return s.replace(/^\s+|\s+$/g, ""); };
function showdialog(){              
	$h.showModalDialog('picupload',ctxPath+'/picCut/picwin.jsp?a0000="+a0000+"','头像上传',900,490,null,{a0000:'"+a0000+"'},true);
	/* var isUpdate = document.getElementById('isUpdate').value;
	if(isUpdate == 2){
		return;
	}
	var newwin = Ext.getCmp('picupload');
	newwin.show();
	var iframe = document.getElementById('iframe_picupload');
	iframe.src=iframe.src; */
}
window.onbeforeunload=function(){
	//var url = ctxPath + "/rmb/rmb.jsp?a0000="+document.getElementById('a0000').value;
	//window.location.href =url;
	//location.replace(url);
	//return false
　    　//alert();
	//给window加a0000属性实现。
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
	
	//去除前面的点
	while(true){
		var firstStr = value.substring(0,1);
		if(firstStr==='.'||firstStr==='。'||firstStr==='·'){
			obj.value=value=value.substring(1,value.length);
		}else{
			break;
		}
	}
	//将。.转为·
	if(value.match(/\.|。|\r\n|\r|\n/g)){
		obj.value = value.replace(/\.|。/g,"·").replace(/\r\n|\r|\n/g,"");
	}
	
	var a0101 = document.getElementById('a0101').value;
	//alert(a0101);
	var c = new Array();
	var s=0;
	for(var i=0;i<a0101.length;i++){
		c.push(a0101.charAt(i));
	}
	for(var i=0;i<c.length;i++){
		if(c[i]=="·"||c[i]=="."){
			s++;
		}
	}
	if(s>2){
		$h.alert('系统提示：','包含三个或以上·,请确认!',null,220);
	}

	
	
	return true;
}
function setTitle(){
	var a0101 = document.getElementById("a0101").value;
	var a0104 = document.getElementById("a0104Text").value;
	var a0107 = document.getElementById("a0107").value;
	var age = $h.getYear(a0107,new Date().format("Ymd"));
	//console.log(new Date().format("yyyyMMdd"));
	var title = a0101 + "，" + a0104 + "，" + age + "岁";
	
	
	//parent.Ext.getCmp('personInfoOP').setTitle(title.replace("<", "&lt;").replace("'", "&acute;"));
}
Ext.onReady(function(){
	setShowValue("a0221",CodeTypeJson.ZB09);
	setShowValue("a0192e",CodeTypeJson.ZB148);
	
	/* var a1701 = document.getElementById('a1701').value;
	setResume(a1701); */
	
	//
	var a99z101 = document.getElementById('a99z101F').value;
	if(a99z101 == 1){
		
		document.getElementById('a99z101').checked=true;
	}
	
	var a99z103 = document.getElementById('a99z103F').value;
	if(a99z103 == 1){
		
		document.getElementById('a99z103').checked=true;
	}
	
});


function a99(){
	
	var a99z101 = document.getElementById('a99z101F').value;
	if(a99z101 == 1){
		document.getElementById('a99z101').checked=true;
	}else{
		document.getElementById('a99z101').checked=false;
	}
	
	var a99z103 = document.getElementById('a99z103F').value;
	if(a99z103 == 1){
		document.getElementById('a99z103').checked=true;
	}else{
		document.getElementById('a99z103').checked=false;
	}
}

//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
var ue = UE.getEditor('editor');

//alert(ue);
$(function(){
	//获得简历
	ue.ready(function () { 
		
		//根据信息项权限组控制，判断简历是否可用
		if(fieldsDisabled.indexOf("a1701") != -1){
			ue.setDisabled(); 
			Ext.getCmp('qx').setDisabled(true);
			Ext.getCmp('qx2').setDisabled(true);
		}
		
        // editor准备好之后才可以使用
	  // setTimeout(function() {
	        	setContent();
	  // }, 600);
        
	        	//console.log(UE.plugins)      	
	        	
	});
}); 


if(window.clipboardData){

    //for IE

	/* var copyBtn = document.getElementById("copy");
	
	copyBtn.onclick = function(){ 
		 window.clipboardData.setData('text',document.getElementById("a1701").value); 
	}  */
}
	

function setContent(){ 
	
	
	var a1701 = document.getElementById("a1701").value;
	//var pas = a1701.split(/\r|\n|\r\n/g);
	//console.log(a1701)
	//return
	a1701 = a1701.replace(/\r/g,'').replace(/\n/g,'</p><p>');
	//console.log(a1701)
	ue.setContent("<p>"+a1701+"</p>", false);
	ue.fireEvent("selectionchange");
	
	
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


ue.addListener('blur',function(a,b,c){
	document.getElementById("a1701").value = ue.getPlainTxt().trim();
});

ue.addListener('beforepaste', myEditor_paste); 
function myEditor_paste(o, html) {//alert();
    //html.html = "";
   // alert("只能录入不能粘贴");
   //console.log(html)
  // console.log(o)
}




Ext.onReady(function(){
	
	genResume();
	a99z1Change();
});
/**
 * 自动生成简历
 */
function genResume(){
	ajaxSubmit('genResume');
	document.getElementById("contenttext").value=document.getElementById("a1701").value.trim();
}



window.onbeforeunload=function(){
	//ajaxSubmit('isChange',null);
	//radow.doEvent("close");
	return "关闭任免表前，请先保存数据 ！";
}




//tab键禁用 
$(document).ready(function () {
    $(document).bind('keydown', function (event) {
        if (event.keyCode == 9) {
            //document.body.focus();
        	return false;   
        }
    });
    
    
});


Ext.onReady(function(){
	
	//$("#comboxArea_a0221").after("<span class='right_qry_div'></span>");  
	
});	


function a99z1Change(){
	var a99z101 = document.getElementById("a99z101F").value;		//是否考录
	var a99z103 = document.getElementById("a99z103F").value;		//是否选调生
	
	
	if(a99z101 != null && a99z101 == '1'){
		
		//$h.dateEnable('a99z102');
		$('#a99z102_1').attr("disabled",false);
	}else{
		
		//$h.dateDisable('a99z102');
		var obj = $('#a99z102_1');
		
		$("#a99z102_1").removeClass('error_style');
		$("#a99z102_err").css('display','none');
		$('#a99z102').val("");
		obj.val("");
		obj.attr("disabled","disabled");
	}
	
	if(a99z103 != null && a99z103 == '1'){
		//$h.dateEnable('a99z104');
		
		$('#a99z104_1').attr("disabled",false);
	}else{
		//$h.dateDisable('a99z104');
		var obj = $('#a99z104_1');
		
		$("#a99z104_1").removeClass('error_style');
		$("#a99z104_err").css('display','none');
		$('#a99z104').val("");
		obj.val("");
		obj.attr("disabled","disabled");
	}
	
	
}


//a01统计关系所在单位
function a0195onchange(a, b){
	
	var a0195 = document.getElementById("a0195").value;
	
	//radow.doEvent('a0195Change',a0195);
	ajaxSubmit('a0195Change',a0195);
}


Ext.onReady(function(){
	<%=setTitle%>
	//document.title = window.dialogArguments.title+document.title;
	
});



var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"} 
//身份证验证

function isIdCard(sId){
	
	//将15位身份证号码转换为18位 
	var sId = changeFivteenToEighteen(sId);
	//alert(sId);
	var iSum=0 ;
	var info="" ;
	if(!/^\d{17}(\d|x)$/i.test(sId)) return "您输入的身份证长度或格式错误";
	sId=sId.replace(/x$/i,"a");
	if(aCity[parseInt(sId.substr(0,2))]==null) return "您的身份证地区非法";
	sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2));
	var d=new Date(sBirthday.replace(/-/g,"/")) ;
	if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))return "身份证上的出生日期非法";
	for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;
	if(iSum%11!=1) return "您输入的身份证号非法";
	//aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"男":"女");//此次还可以判断出输入的身份证号的人性别
	 
	//验证性别
	//判断身份证倒数第二位是否和性别一致
	var sex = sId.substr((sId.length-2), 1);
	var a0104 = document.getElementById('a0104').value;		//性别
	
	var sexA0104 = sex%2;		//取余数
	
	if(sexA0104 == 0){
		sexA0104 = 2;
	}	
	
	if(sexA0104 != a0104){
		return "身份证号码倒数第二位数和性别不一致";
	}
	 
	return true;
}
	
//将15位身份证号码转换为18位 	
function changeFivteenToEighteen(obj) { 
	 if(obj.length == '15') 
	 { 
	  var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); 
	  var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'); 
	  var cardTemp = 0, i;  
	  obj = obj.substr(0, 6) + '19' + obj.substr(6, obj.length - 6); 
	  for(i = 0; i < 17; i ++) 
	  { 
	   cardTemp += obj.substr(i, 1) * arrInt[i]; 
	  } 
	  obj += arrCh[cardTemp % 11]; 
	  return obj; 
	 } 
	 return obj; 
};


var fieldsDisabled = <%=InfoComWindowPageModel.getInfoData()%>;


Ext.onReady(function(){ 
	
	//对信息集明细的权限控制，是否可以维护 
	$h.fieldsDisabled(fieldsDisabled); 
 	
	
});

</script>
</body>
</html>