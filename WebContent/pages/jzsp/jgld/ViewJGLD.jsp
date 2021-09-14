<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<%@include file="/comOpenWinInit2.jsp" %>
<style>
body{
margin: 1px;overflow: auto;
word-break:break-all;
background-color: #f5f5f5;
font-family:'宋体',Simsun;
}
.tb td{
background-color:rgb(235,235,228);
}
.tb , .tb tr th, .tb tr td
{ border:1px solid #74A6CC;  border-right-width: 0px;  }
.tb
{ text-align: center; border-collapse: collapse;border-width: 2px; }
.titleTd{
	background-color: rgb(192,220,241)!important;
	font-weight: bold;
	font-size: 12px;
	line-height:20px;  

	letter-spacing:3px;  
}
.x-form-text, textarea.x-form-field{background-image: none;}
.x-form-field-wrap{width: 100%!important;}/*日期宽  */
.tb .x-form-trigger{right: 0px;}/*图标对其  */
.tb input{width: 100%!important;border: none;}
.tb textarea{border: none;overflow: auto;background-color:rgb(235,235,228); };
.tb .x-form-item{margin-bottom: 0px;}
.ext-ie7 .x-form-text{margin-bottom: -1px!important;}
</style>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script  type="text/javascript">

</script>
<odin:hidden property="sp0100"/>
<odin:hidden property="a0000"/>
<odin:hidden property="usertype" title="类型 用户还是机构"/>
			<!-- record_batch -->
<div style="width: 100%;text-align: center;">
<h1 style="margin: 15px auto;font-family: 方正小标宋简体,'宋体',Simsun;font-size: 20px;">党政机关领导干部兼任社会团体领导职务审批表</h1>
<div style="height: 560px;overflow-y:auto; ">
<table style="width: 97%;" class="tb">
	<tr height="30" style="background-color: white;">
		<td width="12%" class="titleTd" colspan="1">姓名</td>
		<odin:textEdit property="sp0102" width="'100%'" readonly="true" title="姓名" colspan="1"/>
		<td width="12%" class="titleTd" colspan="1">性别</td>
		<odin:select2 property="sp0103" hideTrigger="true" title="性别" readonly="true" codeType="GB2261" colspan="1"/>
		<td width="12%" class="titleTd"  colspan="1">出生年月</td>
		<odin:textEdit readonly="true" property="sp0104" width="'100%'" title="出生年月" colspan="1"/>
		<td width="12%" class="titleTd" colspan="1">文化程度</td>
		<odin:textEdit property="sp0105" width="'100%'" title="文化程度" readonly="true" colspan="1"/>
	</tr>
	<tr>
		<td class="titleTd" colspan="1">现单位<br/>及职务</td>
		<odin:textarea property="sp0106" title="现任单位及职务" colspan="2" readonly="true" rows="5"/>
		<td class="titleTd" colspan="1">社团名称及<br/>选（聘）任<br/>社团职务</td>
		<odin:textarea property="sp0107" title="社团名称及选（聘）任社团职务" colspan="2" readonly="true" rows="5"/>
		<td class="titleTd" colspan="1">任期</td>
		<odin:textarea property="sp0108" title="任期" colspan="1" readonly="true" rows="5"/>
	</tr>
	<tr>
		<td class="titleTd" colspan="1">有无其它社团兼职（含法人代表）</td>
		<odin:textarea property="sp0109" title="有无其它社团兼职（含法人代表）" readonly="true" colspan="7" rows="5"/>
	</tr>
	<tr>
		<td class="titleTd" colspan="1">兼任理由</td>
		<odin:textarea property="sp0110" title="兼任理由" colspan="7" readonly="true" rows="5"/>
	</tr>
	<tr>
		<td class="titleTd" colspan="1">干部所在单位意见</td>
		<odin:textarea property="sp0111" title="干部所在单位意见" colspan="7" readonly="true" rows="5"/>
	</tr>
	<tr>
		<td class="titleTd" colspan="1">社团业务主管部门意见</td>
		<odin:textarea property="sp0112" title="社团业务主管部门意见" colspan="7" readonly="true" rows="5"/>
	</tr>
	<tr>
		<td class="titleTd" colspan="1">干部主管部门意见</td>
		<odin:textarea property="sp0113" title="干部主管部门意见" colspan="7" readonly="true" rows="5"/>
	</tr>
	<tr height="60" style="background-color: white;">
		<tags:JUpload2 property="file03" label="选择文件" fileTypeDesc="所有文件"  colspan="8"
		uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
	</tr>
	<%-- <tr>
		<td align="right" colspan="8" style="padding-right: 30px;">
			<odin:button text="保存"  property="save"></odin:button>
		</td>
	</tr> --%>
</table>

</div>
<table style="width: 100%;">
	<tr>
		<tags:ComBoxWithTree property="spb04" label="送审单位" codetype="USER" disabled="true"/>
		<td><button onclick=" location.replace(location.href); ">刷新</button></td>
	</tr>
</table>
</div>

<script type="text/javascript">
Ext.onReady(function() {
	
	$('#sp0102').parent().parent().parent().attr('width','13%'); 
	$('#sp0103').parent().parent().parent().attr('width','13%'); 
	$('#sp0104').parent().parent().parent().attr('width','13%'); 
	$('#sp0105').parent().parent().parent().attr('width','13%'); 
	/*在ie中解决断行问题(防止只有一行显示，主要解决ie兼容问题，ie8中当设宽度为100%时，文本域类容由换行时*/
	$('textarea').each(function(){$(this).css('width',$(this).innerWidth())})
	var viewSize = Ext.getBody().getViewSize();
	
	if(typeof parentParam!='undefined'&&typeof parentParam.sp0100!='undefined')
		document.getElementById('sp0100').value = parentParam.sp0100;
	
	
	
	$('#file03').text('附件');
	
	
	
});

var g_contextpath = '<%= request.getContextPath() %>';

function setDisabledInfo(){//登记节点
	$('#spb04SpanId').css('display','none');
	$('#spb04_combotree').css('display','none');
}

//文件下载
function download(id){
	
	//下载附件
	//encodeURI，用来做url转码，解决中文传输乱码问题 （后台接收的时候会再做转码处理，转回来）
	window.location="PublishFileServlet?method=downloadFile&SPid="+encodeURI(encodeURI(id));
	
}
</script>


