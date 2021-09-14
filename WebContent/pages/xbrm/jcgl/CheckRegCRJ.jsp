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
		<tags:JUpload4 property="crcrj" label="资料上传" fileTypeDesc="所有文件"  colspan="2" 
			uploadLimit="1"  fileSizeLimit="20MB" fileTypeExts="*.xls;*.xlsx" labelTdcls="td2"/>
	</tr>
	<tr>
		<td colspan="2" height="0px" align="right">
		</td>
	</tr>
</table>
<table style="width: 100%;">
  <tr>
    <td width="50%">
		<odin:editgrid2 property="memberGrid" hasRightMenu="false" title="证件信息" rowDbClick="rowDbClick"
			autoFill="false"  bbarId="pageToolBar" pageSize="100" url="/" >
			<odin:gridJsonDataModel>
				<odin:gridDataCol name="crcrjzj001" />
				<odin:gridDataCol name="crcrjzj002" />
				<odin:gridDataCol name="crcrjzj003" />
				<odin:gridDataCol name="crcrjzj004" />
				<odin:gridDataCol name="crcrjzj005" />
				<odin:gridDataCol name="crcrjzj006" />
				<odin:gridDataCol name="crcrjzj007" />
				<odin:gridDataCol name="crcrjzj008" />
				<odin:gridDataCol name="crcrjzj009" />
				<odin:gridDataCol name="checkregcrjzjid" />	
				<odin:gridDataCol name="checkregid" isLast="true"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn></odin:gridRowNumColumn>
				<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crcrjzj001" width="50" header="序号" />
				<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crcrjzj002" width="150" header="身份证号" />
				<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crcrjzj003" width="100" header="姓名" />
				<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crcrjzj004" width="100" header="性别" />
				<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crcrjzj005" width="100" header="出生日期" />
				<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crcrjzj006" width="100" header="证件种类" />
				<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crcrjzj007" width="100" header="证件号码" />
				<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crcrjzj008" width="100" header="签发日期" />
				<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crcrjzj009" width="100" header="有效期至" />
				<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="checkregcrjzjid" width="50" hidden="true" header="ss" />
				<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="checkregid" width="100" hidden="true" isLast="true" header="" />
			</odin:gridColumnModel>
			<odin:gridJsonData>
				{
			        data:[]
			    }
			</odin:gridJsonData>
		</odin:editgrid2>
	</td>
    <td width="50%">
    	<odin:editgrid2 property="memberGrid2" hasRightMenu="false" title="出入境信息" 
			autoFill="false"  bbarId="pageToolBar" pageSize="100" url="/" >
			<odin:gridJsonDataModel>
				<odin:gridDataCol name="checkregid" />
				<odin:gridDataCol name="crcrjjl001" />
				<odin:gridDataCol name="crcrjjl002" />
				<odin:gridDataCol name="crcrjjl003" />
				<odin:gridDataCol name="crcrjjl004" />
				<odin:gridDataCol name="crcrjjl005" />
				<odin:gridDataCol name="crcrjjl006" />
				<odin:gridDataCol name="crcrjjl007" />
				<odin:gridDataCol name="crcrjjl008" />
				<odin:gridDataCol name="checkregcrjjlid" />
				<odin:gridDataCol name="checkregcrjzjid" />
				<odin:gridDataCol name="checkregid" isLast="true"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn></odin:gridRowNumColumn>
				<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crcrjjl001" width="100" header="人员姓名" />
				<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crcrjjl002" width="100" header="出境理由" />
				<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crcrjjl003" width="100" header="出入境日期" />
				<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crcrjjl004" width="100" header="出入境时间" />
				<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crcrjjl005" width="100" header="出入口岸" />
				<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crcrjjl006" width="100" header="交通方式" />
				<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crcrjjl007" width="100" header="前往地" />
				<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="crcrjjl008" width="150" header="身份证号码" />
				<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="checkregcrjjlid" width="150" header="id1" hidden="true" />
				<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="checkregcrjzjid" width="150" header="id2" hidden="true" />
				<odin:gridEditColumn2 menuDisabled="true" edited="false" editor="text" dataIndex="checkregid" width="100" hidden="true" isLast="true" header="" />
			
			</odin:gridColumnModel>
			<odin:gridJsonData>
				{
			        data:[]
			    }
			</odin:gridJsonData>
		</odin:editgrid2>
    </td>
  </tr>
</table>
<<odin:hidden property="checkregcrjzjid"/>
<div style="display: none;">
<iframe src="" id='downloadframe' ></iframe>
</div>
<script type="text/javascript">
function rowDbClick(grid,rowIndex,colIndex,event){
   	var record = grid.store.getAt(rowIndex);
   	document.getElementById('checkregcrjzjid').value=record.data.checkregcrjzjid;
   	radow.doEvent('memberGrid2.dogridquery');
}
function jfupload_file_del_callback(){
	radow.doEvent('memberGrid.dogridquery');
	radow.doEvent('memberGrid2.dogridquery');
}
function uploadSuccess_callBack(){
	radow.doEvent('memberGrid.dogridquery');
	radow.doEvent('memberGrid2.dogridquery');
}
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var memberGrid = Ext.getCmp('memberGrid');
	memberGrid.setHeight(viewSize.height-90);
	var memberGrid2 = Ext.getCmp('memberGrid2');
	memberGrid2.setHeight(viewSize.height-90);
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


