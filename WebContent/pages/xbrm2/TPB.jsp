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
font-family:'����',Simsun;
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
����.noprint{
  ����display:none
����}
}
</style>
 <script type="text/javascript">
	var g_contextpath = "<%=request.getContextPath()%>";
	var remoteServer = "<%=com.insigma.siis.local.pagemodel.xbrm2.TPBPageModel.getRemoteServer()%>";
	var g_loginTemplate = '<%=com.insigma.siis.local.business.helperUtil.SysManagerUtils.getUserloginName() %>';
</script> 

<!-- �����������  -->
<div id="selectable" style="margin-top:30px;text-align:center">
	<p id="BiaoTouTitle" class="BiaoTouP" style="text-align:center;margin-top:10px!important;margin-bottom:10px!important"> </p>
	<!-- �����  -->
	<div style="width:100%"> 
		<% if ("1".equals(IsGDCL)) { %> 
			<span style="position:absolute;left:0%;font-size:14pt"></span>
		<% }else{ %>
		<span style="position:absolute;left:10px;font-size:14pt">����ң�<input type="text" id="tb_unit" size=20> </span>
		<% } %> 
		<span style="position:absolute;left:45.5%;" class="BiaoTouDate">(<span id="TiaoPeiShiJianPrint_year" class="TiaoPeiShiJianYSPAN  TNR"><%= (new java.util.Date()).getYear()+1900 %></span>��<span id="TiaoPeiShiJianPrint_month" class="TiaoPeiShiJianMSPAN TNR"><%= (new java.util.Date()).getMonth()+1%></span>��<span id="TiaoPeiShiJianPrint_date" class="TiaoPeiShiJianDSPAN TNR"><%= (new java.util.Date()).getDay() %></span>��)</span>
	
		<% if ("1".equals(IsGDCL)) { %>
			<span style="float:right;font-size:14pt"></span>
			<BR>
		<% }else{ %>
		<span style="float:right;font-size:14pt">��������ֹ���ڣ�<span id="NL_year" class="TiaoPeiShiJianYSPAN  TNR"><%= (new java.util.Date()).getYear()+1900 %></span>��<span id="NL_month" class="TiaoPeiShiJianMSPAN TNR"><%= (new java.util.Date()).getMonth()+1%></span>��<span id="NL_date" class="TiaoPeiShiJianDSPAN TNR"><%= (new java.util.Date()).getDay() %></span>�� &nbsp;&nbsp;</span>
		<% } %> 
	</div>
 	<!-- include��ʽǶ�뾲̬ҳ��  -->
	<div id="coordTable_div" style="padding:10px !important;margin-left:20px;margin-bottom:20px; overflow:scroll; height:500px;width: 100%;">
		<% if (subWinIdBussessId.endsWith("TPHJ2") ||
				subWinIdBussessId.endsWith("TPHJ3")){ %>	
			<jsp:include page="TPB_yntp2_3.jsp"></jsp:include>
		<%}else{ %>
			<jsp:include page="TPB_yntp1.jsp"></jsp:include>
		<%} %> 
	</div> 

	<!-- NiRenMianBianJiԭ�������⴦�����������ʾ��DIV���������ز���  -->
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

<!-- �����ı���������ı������  -->
<div class="TiaoPeiLeiXingDIV">
	<select id="yn_type" name="yn_type">
	  <option value="TPHJ1">����</option>
	  <option value="TPHJ2">��ί���ר������Ա����</option>
	  <option value="TPHJ3">�������</option>
	  <option value="TPHJ4">��ί���ר�����</option>
	  <option value="TPHJ5">��ί��ί��</option>
	</select>
</div>

<!-- �������  -->
<div class="TiaoPeiShiJianY">
	<select id="tp_y" name="tp_y">
	</select>
</div>

<!-- �����·�  -->
<div class="TiaoPeiShiJianM">
	<select id="tp_m" name="tp_m">
	</select>
</div>

<!-- ��������  -->
<div class="TiaoPeiShiJianD">
	<select id="tp_d" name="tp_d">
	</select>
</div>
 
<!-- �ײ����� ������  -->
<p style="margin:10px !important;display:<%="1".equals(IsGDCL)?"none":"block" %>" class="noprint">  
	<label class="pull-left" class="label-primary">
		<!-- <input type="checkbox" id="fontCheckBox" style="margin:5px;padding:5px"  > <span class="text-primary">����ģʽ&nbsp;</span> -->
	
		<select id="fontCheckBox" style="margin:5px;padding:5px;"> 
			<option value="1">&nbsp;���&nbsp;</option>
			<option value="2">&nbsp;�༭&nbsp;</option>
			<!-- <option value="3">&nbsp;�����ʽ&nbsp;</option> -->
		</select>
	</label>
	<label class="pull-right"> 
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary" onclick="clearScreenData();">&nbsp;���&nbsp;</button>
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary" onclick="adjStyle();">&nbsp;��ʽ����&nbsp;</button>
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary" onclick="calNL();">&nbsp;��������&nbsp;</button>
		
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary" onclick="save();">&nbsp;����&nbsp;</button>
		
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary"  onclick="impLrmFileToZJK()">&nbsp;�м�⵼��&nbsp;</button>
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary"  onclick="impLrmFilesToZJK()">&nbsp;�м����������&nbsp;</button>
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary"  onclick="onAppointment()">&nbsp;��ʽ����&nbsp;</button>
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary"  onclick="printTable()">&nbsp;��ӡ����&nbsp;</button>
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary"  onclick="ExpTPBExcel()">&nbsp;����Excel&nbsp;</button>
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary" onclick="inputTPBExcel();" >&nbsp;����Excel&nbsp;</button>
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary"  onclick="readGD()">&nbsp;��ȡ�鵵����&nbsp;</button>
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary"  onclick="saveGD()">&nbsp;����鵵����&nbsp;</button>
		&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary"  onclick="window.location.reload(true)">&nbsp;ˢ��&nbsp;</button>
		&nbsp;
	</label>
</p>


<!-- ���������ڲ�������  -->
<odin:hidden property="ynId" title="�������κ���" />
<odin:hidden property="appointment" title="������"/> 
<odin:hidden property="yntype" title="�����������" />
<odin:hidden property="tpy" title="���" />
<odin:hidden property="tpm" title="�·�"/>
<odin:hidden property="tpd" title="����" />
<odin:hidden property="docpath" title="�����ĵ�·��" />
<odin:hidden property="ID_ROWINFO" title="�������"/>
<odin:hidden property="ROWID" title="�������id"/>
<odin:hidden property="tb_unit_id" title="�����"/>
<odin:hidden property="cal_age_year" title="������������"/>
<odin:hidden property="a0000" title="��Ա����id"/>
<odin:hidden property="rmburl" title="�����URL"/>
<odin:hidden property="coordTableHtmlContent" title="����Html����"/>
<odin:hidden property="IsGDCL" title="�Ƿ�鵵����"  value="<%=IsGDCL %>"/>


<!-- �����õ�Iframe,�����ת��������´���  -->  
<iframe id="myframe" name="myframe" style="display:none"></iframe> 
<iframe  id="iframe_expTPB" style="display: none;" src=""></iframe>
<div style="display:none">
<form id="submitform" name="submitform" method="post" target="myframe">
	<input type=text name="username" id="username" size="18" value="" ><br>
	<input type="text" name="params" id="params" size="19" value=""/>  
	<input type=submit id="submit1" name="submit1" value="�ύ" >  
</form> 
</div>