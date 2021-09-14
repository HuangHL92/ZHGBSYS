<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>

<%@include file="/comOpenWinInit2.jsp" %>
<script  type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';
</script>
<style>
body{
margin: 1px;overflow: auto;
/* background-color: #f5f5f5; */
font-family:'宋体',Simsun;
}
#talbe_1{
border-collapse: collapse;
}
#tr2 td{
border:1px solid #74A6CC;
height: 57px;
}
.span{
font-size: 12px;font-family:tahoma,arial,verdana,sans-serif;color: #15428b;font-weight: bold;
}

.td1{
	height: 30px;
	color:#15428b;
	font-weight:bold; 
    font-size: 11px;
    font-family: tahoma,arial,verdana,sans-serif;
    border-color:#99bbe8;
    background-color: #f5f5f5;
    background-image: url(./basejs/ext/resources/images/default/panel/white-top-bottom.gif);
}
.td2{
	text-align: center;
	align-content: center;
	border-color:#a9bfd3;
    background-color:#d0def0;
    background-image:url(./basejs/ext/resources/images/default/toolbar/bg.gif);
}
</style>
<odin:hidden property="checkregid"/>
<table id="talbe_1" style="width: 100%; top: 0px;">
	<tr id="tr1" class="td1">
		<td colspan="1" width="100px">
			<span class="span">附件上传</span>
		</td>
		<td align="right" style="padding: 0px 30px 0px 0px;" width="88%">
			<odin:button text="&nbsp;上&nbsp;传&nbsp;" property="saveBtn"></odin:button>
			<!-- <div style="float: left;width: 30px;">&nbsp;</div> -->
		</td>
	</tr>
	<tr id="tr2">
		<tags:JUpload4 property="crgscg" label="资料上传" fileTypeDesc="所有文件"  colspan="2" 
			uploadLimit="1"  fileSizeLimit="20MB" fileTypeExts="*.xls;*.xlsx" labelTdcls="td2"/>
	</tr>
	<tr>
		<td colspan="2" height="0px" align="right">
		</td>
	</tr>
</table>
<odin:editgrid2 property="memberGrid" hasRightMenu="false" title="导入信息" 
	autoFill="false"  bbarId="pageToolBar" pageSize="100" url="/" >
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="crgscg001" />
		<odin:gridDataCol name="crgscg002" />
		<odin:gridDataCol name="crgscg003" />
		<odin:gridDataCol name="crgscg004" />
		<odin:gridDataCol name="crgscg005" />
		<odin:gridDataCol name="crgscg006" />
		<odin:gridDataCol name="crgscg007" />
		<odin:gridDataCol name="crgscg008" />
		<odin:gridDataCol name="crgscg009" />
		<odin:gridDataCol name="crgscg010" />
		<odin:gridDataCol name="crgscg011" />
		<odin:gridDataCol name="crgscg012" />
		<odin:gridDataCol name="crgscg013" />
		<odin:gridDataCol name="crgscg014" />
		<odin:gridDataCol name="crgscg015" />
		<odin:gridDataCol name="checkregid" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crgscg001" width="50" header="序号" />
		<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crgscg002" width="80" header="姓名" />
		<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crgscg003" width="150" header="身份证号码" />
		<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crgscg004" width="100" header="注册号" />
		<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crgscg005" width="100" header="单位名称" />
		<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crgscg006" width="100" header="注册资本元" />
		<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crgscg007" width="100" header="企业类型" />
		<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crgscg008" width="100" header="成立日期" />
		<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crgscg009" width="100" header="股东名称" />
		<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crgscg010" width="100" header="股东出资金额万" />
		<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crgscg011" width="100" header="经营范围" />
		<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crgscg012" width="100" header="登记机关" />
		<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crgscg013" width="100" header="企业状态" />
		<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crgscg014" width="100" header="注销日期" />
		<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crgscg015" width="100" header="吊销日期" />
		<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="checkregid" width="100" hidden="true" isLast="true" header="" />
	
	</odin:gridColumnModel>
	<odin:gridJsonData>
		{
	        data:[]
	    }
	</odin:gridJsonData>
</odin:editgrid2>
<div style="display: none;">
<iframe src="" id='downloadframe' ></iframe>
</div>
<script type="text/javascript">
function jfupload_file_del_callback(){
	radow.doEvent('memberGrid.dogridquery');
}
function uploadSuccess_callBack(){
	radow.doEvent('memberGrid.dogridquery');
}
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var memberGrid = Ext.getCmp('memberGrid');
	memberGrid.setHeight(viewSize.height-90);
	document.getElementById('checkregid').value = parentParam.checkregid;
});
function infoSearch(){
	radow.doEvent('memberGrid.dogridquery');
}

function download(id){
	//下载附件 downloadframe
	//encodeURI，用来做url转码，解决中文传输乱码问题 （后台接收的时候会再做转码处理，转回来）
	document.getElementById('downloadframe').src="PublishFileServlet?method=downloadFile&checkregfileid="+encodeURI(encodeURI(id));
	
}
</script>


