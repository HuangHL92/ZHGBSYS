<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page import="net.sf.json.JSONArray"%>
<%@include file="/comOpenWinInit2.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>导入文件</title>
	<odin:MDParam></odin:MDParam>
    <script src="<%=request.getContextPath()%>/basejs/jquery-ui/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
    <script src="<%=request.getContextPath()%>/jwjc/js/underscore.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/jwjc/js/common.js" charset="utf-8"></script>
<form id="formId" name="data" method="post"  action="<%=request.getContextPath()%>/DownloadServlet?method=appendixUpload" enctype="multipart/form-data">
	<table align="center" width="96%">	
		<tr>
			<td width="10"></td>
			<td width="20">
		       <iframe id="frame" name="frame" height="33px" width="280px" src="<%=request.getContextPath() %>/pages/sysorg/org/adfile2.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
			</td>
			<td  height="15" >
	 		 <span  id = "CPDText"></span>
	 		</td>
			<td>
				<img src="<%=request.getContextPath()%>/images/go1.jpg" onclick="sc()">
			</td>
		</tr>
	</table>
	<odin:hidden property="zbid"/>
</form>
<table style="width: 100%;">
	<tr>
		<td>
			<odin:editgrid2 property="fileGrid" bbarId="pageToolBar" autoFill="false" height="387"
				 hasRightMenu="false" url="/">
				<odin:gridJsonDataModel id="name" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="add00"/>
					<odin:gridDataCol name="zbid"/>
					<odin:gridDataCol name="filename"/>
					<odin:gridDataCol name="fileurl"/>
					<odin:gridDataCol name="operateU" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn2 />
					<odin:gridEditColumn2 dataIndex="add00" width="250" editor="text" edited="false" header="id" hidden="true"/>
					<odin:gridEditColumn2 dataIndex="filename" width="600" editor="text" edited="false" header="文件名称" renderer="file"/>
					<odin:gridEditColumn2 dataIndex="fileurl" width="80" editor="text" edited="false" header="url" hidden="true"/>
					<odin:gridEditColumn2 dataIndex="operateU" width="100" header="操作" editor="text" edited="false" 
						 align="center" renderer="operateUod" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid2>
		</td>
	</tr>
</table>
<script>
Ext.onReady(function() {
	document.getElementById('zbid').value = document.getElementById('subWinIdBussessId2').value;
	
});

// 点击提交
function sc(){
	var pid = document.getElementById('zbid').value;
	window.frames['frame'].impdx(pid);
	//frame.window.imp(pid);
}
function gg() {
	//window.close();
	//var parentWin = window.opener;
   radow.doEvent("fileGrid.dogridquery");
   realParent.radow.doEvent("zwzcMxGrid.dogridquery");
}
function yulan_rmb(sh000){
	document.getElementById('mainIframe').src='<%= request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.meeting.MeetingPreview&publishid=sh000@@'+sh000;
} 
//来文原件
function file(value, params, rs, rowIndex, colIndex, ds){
	var fileurl = rs.get('fileurl');
	var filename = rs.get('filename');
	
	 if(filename != null && filename != ''&& fileurl!=null && fileurl!=''){
		 var url=fileurl.replace(/\\/g,"/");
		return "<a href=\"javascript:downloadFile('" +url +"')\">"+filename+"</a>";
	} 
	

} 
//点击关闭,前面页面 附件内容更新
function colseWin( html ){
	//更新前一页面
	if(realParent.document.getElementById("div_fujian")!=null){
		realParent.document.getElementById("div_fujian").innerHTML =html;
	}
	//window.close();
}

//点击关闭,前面页面 附件内容更新
function fz( meetingname ){
	//更新前一页面
	if(document.getElementById("CPDText")!=null){
		document.getElementById("CPDText").innerHTML =meetingname;
	}
	//window.close();
}

// grid操作列显示的操作情况
function operateUod(value, params, record,rowIndex,colIndex,ds){
	var add00 = record.get('add00');
	return "<a href=\"javascript:deleteRow('"+add00+"')\">删除</a>&nbsp;";
}

// 点击下载按钮
function downloadRow(rowIndex) {
	radow.doEvent("downloadFile", rowIndex);
}

//点击上传按钮
function dRow(sh000){
	window.frames['frame'].impdx(sh000);
	//radow.doEvent("file", rowIndex);
	<%-- $h.openWin("AppendixWin","pages.meeting.CadresPlanningAllocationAppendix", "附件列表", 760, 560, sh000,"<%=request.getContextPath()%>"); --%>
}

// 下载
function downloadFile(url) {
	var downfile = url;
	//w = window.open("ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile)));
	window.location.href="ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile));
}

// 删除
function deleteRow(add00) {
	// 确认删除
	$h.confirm("系统提示：",'是否确认删除？',200,function(id) { 
		if("ok"==id){
			radow.doEvent("deleteFile", add00);
			window.frames['frame'].imd();
		}else{
			return false;
		}		
	});
}
function restore(){
	parent.odin.ext.getCmp('grid1').store.reload();
	window.close();
}

function info(type){
	document.all('excelFile').value='';
	odin.ext.get(document.body).unmask();

		doCloseWin(type);
}

var businessData = "";
function doCloseWin(type){
	odin.ext.get(document.body).mask('正在刷新页面......', odin.msgCls);
	parent.odin.ext.getCmp('impWin').hide();
	if(businessData!="" && type==1){
		if(typeof parent.resFuncImpExcel != 'undefined'){
			parent.resFuncImpExcel(odin.ext.decode(businessData));
		}
	}
}
</script>