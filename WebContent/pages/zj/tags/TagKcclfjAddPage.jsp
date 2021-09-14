<meta http-equiv="X-UA-Compatible" content="IE=7" >
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ include file="/comOpenWinInit2.jsp"%>
<style>
body {
	margin: 1px;
	overflow: auto;
	background-color: #f5f5f5;
	font-family: '宋体', Simsun;
}

.vueBtn {
	display: inline-block;
	padding: .3em .5em;
	background-color: #6495ED;
	border: 1px solid rgba(0, 0, 0, .2);
	border-radius: .3em;
	box-shadow: 0 1px white inset;
	text-align: center;
	text-shadow: 0 1px 1px black;
	color: white;
	font-weight: bold;
	cursor: pointer;
}

.tb, .tb tr th, .tb tr td {
	border: 1px solid #74A6CC;
	border-right-width: 0px;
}

.tb {
	text-align: center;
	border-collapse: collapse;
	border-width: 2px;
}

.titleTd {
	width: 60px;
	line-height: 20px;
	font-size: 12px;
	font-weight: bold;
	letter-spacing: 3px;
	background-color: rgb(192, 220, 241);
}

.x-form-text, textarea.x-form-field {
	background-image: none;
}

.x-form-field-wrap {
	width: 100% !important;
} /*日期宽  */
.tb .x-form-trigger {
	right: 0px;
} /*图标对其  */
.tb input {
	width: 100% !important;
	border: none;
}

.tb textarea {
	border: none;
	overflow: auto;
	word-break: break-all;
}

;
.tb .x-form-item {
	margin-bottom: 0px;
}

.ext-ie7 .x-form-text {
	margin-bottom: -1px !important;
}
</style>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<div style="width: 100%;text-align: center;">
	<h1 style="margin: 10px auto;font-family: 方正小标宋简体,'宋体',Simsun;font-size: 20px;">考察材料</h1>
	<div style="height: 300px;overflow-y:auto; ">
		<table style="width: 98%;" class="tb">
			<tr height="290" style="background-color: white;">
				<tags:JUpload2 property="kcclfiles" label="材料上传" fileTypeDesc="所有文件" colspan="6"
					uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
			</tr>
		</table>
	</div>
	<table style="width: 100%;">
		<tr>
			<td align="center"><div id="save" onclick="save()" style="width:68px;" class="vueBtn">保存</div></td>
		</tr>
	</table>
</div>
<odin:hidden property="tagid"/>

<script type="text/javascript">
function save(){
	radow.doEvent('save.onclick');
}

function saveCallBack(t,fg){
	$h.alert('系统提示', t, function(){
		//parent.infoSearch();
		window.close();
	});
}

var g_contextpath = '<%=request.getContextPath() %>';

var setFileLength = (function(){//文件上传
	var flength = 0,curfindex=0,msg="";
	window.onUploadSuccess = function(file, jsondata, response){
		curfindex++;
		updateProgress(curfindex,flength,jsondata.file_name);
		if(curfindex==flength){
			Ext.Msg.hide();
			saveCallBack(msg);
		}
	}
	function updateProgress(cur,total,fname){
		if (fname.length > 15) {
			fname = fname.substr(0,15) + '...';
		}
		Ext.MessageBox.updateProgress(cur / total, '正在上传文件:'+fname+'，还剩'+(total-cur)+'个');
	}
	
	return function(t){
		msg = t;
		curfindex=0;
		flength = eval("$('#kcclfiles').data('uploadify').queueData.queueLength;");
		if(flength>0){
			$h.progress('请等待', '正在上传文件...',null,300);
		}else{
			Ext.Msg.hide();
			saveCallBack(t);
		}
	}
});

//文件下载
function download(id){
	
	//下载附件
	//encodeURI，用来做url转码，解决中文传输乱码问题 （后台接收的时候会再做转码处理，转回来）
	window.location="PublishFileServlet?method=downloadFile&SPid="+encodeURI(encodeURI(id));
	
}
</script>


