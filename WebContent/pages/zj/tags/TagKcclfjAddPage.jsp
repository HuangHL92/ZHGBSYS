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
	font-family: '����', Simsun;
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
} /*���ڿ�  */
.tb .x-form-trigger {
	right: 0px;
} /*ͼ�����  */
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
	<h1 style="margin: 10px auto;font-family: ����С���μ���,'����',Simsun;font-size: 20px;">�������</h1>
	<div style="height: 300px;overflow-y:auto; ">
		<table style="width: 98%;" class="tb">
			<tr height="290" style="background-color: white;">
				<tags:JUpload2 property="kcclfiles" label="�����ϴ�" fileTypeDesc="�����ļ�" colspan="6"
					uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
			</tr>
		</table>
	</div>
	<table style="width: 100%;">
		<tr>
			<td align="center"><div id="save" onclick="save()" style="width:68px;" class="vueBtn">����</div></td>
		</tr>
	</table>
</div>
<odin:hidden property="tagid"/>

<script type="text/javascript">
function save(){
	radow.doEvent('save.onclick');
}

function saveCallBack(t,fg){
	$h.alert('ϵͳ��ʾ', t, function(){
		//parent.infoSearch();
		window.close();
	});
}

var g_contextpath = '<%=request.getContextPath() %>';

var setFileLength = (function(){//�ļ��ϴ�
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
		Ext.MessageBox.updateProgress(cur / total, '�����ϴ��ļ�:'+fname+'����ʣ'+(total-cur)+'��');
	}
	
	return function(t){
		msg = t;
		curfindex=0;
		flength = eval("$('#kcclfiles').data('uploadify').queueData.queueLength;");
		if(flength>0){
			$h.progress('��ȴ�', '�����ϴ��ļ�...',null,300);
		}else{
			Ext.Msg.hide();
			saveCallBack(t);
		}
	}
});

//�ļ�����
function download(id){
	
	//���ظ���
	//encodeURI��������urlת�룬������Ĵ����������� ����̨���յ�ʱ�������ת�봦��ת������
	window.location="PublishFileServlet?method=downloadFile&SPid="+encodeURI(encodeURI(id));
	
}
</script>


