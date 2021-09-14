<%@page import="com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page isELIgnored="false" %>
<%@include file="/comOpenWinInit.jsp" %>

<meta http-equiv="X-UA-Compatible" content="IE=Edge" /> 
<script src="<%=request.getContextPath()%>/pages/xbrm2/js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm2/js/CoordTable/js/jquery-ui-1.8.9.custom.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm2/js/CoordTable/js/jquery.contextmenu.r2.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm2/js/CoordTable/js/coordTable.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm2/js/tableEditer.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm2/js/colResizable-1.6.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm2/js/base64.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm2/js/tpb.js" type="text/javascript"></script> 

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/mainPage/css/font-awesome.css">
<!--[if lte IE 7]>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/mainPage/css/font-awesome-ie7.min.css">    
<![endif]-->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/mainPage/css/bootstrap.min.css"> 
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/xbrm2/gbtp.css">

<style>
*{margin:0px!important;padding:0px!important;}
body{
<%--<% if (subWinIdBussessId.endsWith("TPHJ2") ||--%>
			<%--subWinIdBussessId.endsWith("TPHJ3")){ %>--%>
	<%--background:url(images/yilanbiao/shuji.jpg) no-repeat;--%>
	<%--background-position:-50px -155px;--%>
<%--<%}else{%>--%>
	<%--background:url(images/yilanbiao/dongyi.jpg) no-repeat;--%>
	<%--background-position:-145px -100px;--%>
<%--<%}%>--%>
margin: 1px;overflow-y: scroll;overflow-x: hidden;
font-family:'宋体',Simsun;
word-break:break-all;
margin-bottom: 250px;
overflow-y:hidden;
}

#jqContextMenu{
	border-style:groove;
	background-color: gary;
	border-width: 1px;
}

.txt_editer{
	border-style: none;
}

.kcclClass{
background-color: rgb(102,204,255) !important;
}
.drag_color{
	background-color: rgb(232,232,232) !important;
}
.drag_pre_color{
	background-color: rgb(233,250,238)!important;
}
.default_color{
	background-color: #FFFFFF !important;
}
</style>

<style>
@media print{
　　.noprint{
  　　display:none
　　}
}
</style>
 <script type="text/javascript">
	var g_contextpath = "<%=request.getContextPath()%>";
	var remoteServer = "<%=com.insigma.siis.local.pagemodel.xbrm2.TPBPageModel.getRemoteServer()%>";
	var g_loginTemplate = '<%=com.insigma.siis.local.business.helperUtil.SysManagerUtils.getUserloginName() %>';
</script> 

<!-- 表格内容区域  -->
<div id="selectable" style="margin-top:30px;text-align:center">
	<p id="BiaoTouTitle" class="BiaoTouP" style="text-align:center;margin-top:10px!important;margin-bottom:10px!important"> </p>
	<!-- 标题块  -->
	<div style="width:100%"> 
		<% if ("1".equals(IsGDCL)) { %> 
			<span style="position:absolute;left:0%;font-size:14pt"></span>
		<% }else{ %>
		<span style="position:absolute;left:10px;font-size:14pt">填报处室：<input type="text" id="tb_unit" size=20> </span>
		<% } %> 
		<span style="position:absolute;left:45.5%;" class="BiaoTouDate">(<span id="TiaoPeiShiJianPrint_year" class="TiaoPeiShiJianYSPAN  TNR"><%= (new java.util.Date()).getYear()+1900 %></span>年<span id="TiaoPeiShiJianPrint_month" class="TiaoPeiShiJianMSPAN TNR"><%= (new java.util.Date()).getMonth()+1%></span>月<span id="TiaoPeiShiJianPrint_date" class="TiaoPeiShiJianDSPAN TNR"><%= (new java.util.Date()).getDay() %></span>日)</span>
	
		<% if ("1".equals(IsGDCL)) { %>
			<span style="float:right;font-size:14pt"></span>
			<BR>
		<% }else{ %>
		<span style="float:right;font-size:14pt">年龄计算截止日期：<span id="NL_year" class="TiaoPeiShiJianYSPAN  TNR"><%= (new java.util.Date()).getYear()+1900 %></span>年<span id="NL_month" class="TiaoPeiShiJianMSPAN TNR"><%= (new java.util.Date()).getMonth()+1%></span>月<span id="NL_date" class="TiaoPeiShiJianDSPAN TNR"><%= (new java.util.Date()).getDay() %></span>日 &nbsp;&nbsp;</span>
		<% } %> 
	</div>
 	<!-- include方式嵌入静态页面  -->
	<div id="coordTable_div" style="padding:10px !important;margin-left:20px;margin-bottom:20px; overflow:scroll; height:500px;width: 100%;">
		<% if (subWinIdBussessId.endsWith("TPHJ2") ||
				subWinIdBussessId.endsWith("TPHJ3")){ %>	
			<jsp:include page="TPB_yntp2_3.jsp"></jsp:include>
		<%}else{ %>
			<jsp:include page="TPB_yntp1.jsp"></jsp:include>
		<%} %> 
	</div> 

	<!-- NiRenMianBianJi原来是特殊处理拟任免的显示的DIV，现在隐藏不用  -->
	<div class="NiRenMianBianJi" rowIndex="" colIndex="" id="NiRenMianBianJi" style="position: absolute;display:none; top: 0px;left: 0px; border: 1px solid silver; background-color:rgb(224,234,245); padding: 6px;">
		<div class="Ren" style="height: 33%;width: 100%;margin: 0px;padding: 0px;">
			<textarea style="height: 100%;width: 100%"></textarea>
		</div>
		<div class="Mian" style="height: 33%;width: 100%;margin: 0px;padding: 0px;">
			<textarea style="height: 100%;width: 100%"></textarea>
		</div>
		<div class="QiTa" style="height: 33%;width: 100%;margin: 0px;padding: 0px;">
			<textarea style="height: 100%;width: 100%"></textarea>
		</div>
	</div> 
</div>  

<!-- 下拉文本框拟任免的表格类型  -->
<div class="TiaoPeiLeiXingDIV">
	<select id="yn_type" name="yn_type">
	  <option value="TPHJ1">酝酿</option>
	  <option value="TPHJ2">市委书记专题会议成员酝酿</option>
	  <option value="TPHJ3">部务会议</option>
	  <option value="TPHJ4">市委书记专题会议</option>
	  <option value="TPHJ5">市委常委会</option>
	</select>
</div>

<!-- 调配年度  -->
<div class="TiaoPeiShiJianY">
	<select id="tp_y" name="tp_y">
	</select>
</div>

<!-- 调配月份  -->
<div class="TiaoPeiShiJianM">
	<select id="tp_m" name="tp_m">
	</select>
</div>

<!-- 调配日期  -->
<div class="TiaoPeiShiJianD">
	<select id="tp_d" name="tp_d">
	</select>
</div>
 
<!-- 底部区域 工具栏  -->
<p style="margin:10px !important;display:<%="1".equals(IsGDCL)?"none":"block" %>" class="noprint">  
	<label class="pull-left" class="label-primary">
		<!-- <input type="checkbox" id="fontCheckBox" style="margin:5px;padding:5px"  > <span class="text-primary">弹窗模式&nbsp;</span> -->
	
		<select id="fontCheckBox" style="margin:5px;padding:5px;"> 
			<option value="1">&nbsp;浏览&nbsp;</option>
			<option value="2">&nbsp;编辑&nbsp;</option>
			<!-- <option value="3">&nbsp;字体格式&nbsp;</option> -->
		</select>
	</label>
	<label class="pull-right"> 
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary" onclick="clearScreenData();">&nbsp;清空&nbsp;</button>
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary" onclick="adjStyle();">&nbsp;样式调整&nbsp;</button>
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary" onclick="calNL();">&nbsp;计算年龄&nbsp;</button>
		
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary" onclick="save();">&nbsp;保存&nbsp;</button>
		
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary"  onclick="impLrmFileToZJK()">&nbsp;中间库导入&nbsp;</button>
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary"  onclick="impLrmFilesToZJK()">&nbsp;中间库批量导入&nbsp;</button>
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary"  onclick="onAppointment()">&nbsp;正式任命&nbsp;</button>
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary"  onclick="printTable()">&nbsp;打印窗体&nbsp;</button>
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary"  onclick="ExpTPBExcel()">&nbsp;导出Excel&nbsp;</button>
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary" onclick="inputTPBExcel();" >&nbsp;导入Excel&nbsp;</button>
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary"  onclick="readGD()">&nbsp;读取归档材料&nbsp;</button>
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary"  onclick="saveGD()">&nbsp;保存归档材料&nbsp;</button>
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary"  onclick="window.location.reload(true)">&nbsp;刷新&nbsp;</button>
		&nbsp;
	</label>
</p>


<!-- 隐藏域，用于参数保存  -->
<odin:hidden property="ynId" title="酝酿批次号码" />
<odin:hidden property="appointment" title="任免标记"/> 
<odin:hidden property="yntype" title="酝酿批次类别" />
<odin:hidden property="tpy" title="年度" />
<odin:hidden property="tpm" title="月份"/>
<odin:hidden property="tpd" title="日期" />
<odin:hidden property="docpath" title="下载文档路径" />
<odin:hidden property="ID_ROWINFO" title="保存对象"/>
<odin:hidden property="ROWID" title="保存对象id"/>
<odin:hidden property="tb_unit_id" title="填报处室"/>
<odin:hidden property="cal_age_year" title="计算年龄日期"/>
<odin:hidden property="a0000" title="人员编码id"/>
<odin:hidden property="rmburl" title="任免表URL"/>
<odin:hidden property="coordTableHtmlContent" title="表格的Html内容"/>
<odin:hidden property="IsGDCL" title="是否归档材料"  value="<%=IsGDCL %>"/>


<!-- 下载用的Iframe,解决跳转不会出现新窗口  -->  
<iframe id="myframe" name="myframe" style="display:none"></iframe> 
<iframe  id="iframe_expTPB" style="display: none;" src=""></iframe>
<div style="display:none">
<form id="submitform" name="submitform" method="post" target="myframe">
	<input type=text name="username" id="username" size="18" value="" ><br>
	<input type="text" name="params" id="params" size="19" value=""/>  
	<input type=submit id="submit1" name="submit1" value="提交" >  
</form> 
</div>