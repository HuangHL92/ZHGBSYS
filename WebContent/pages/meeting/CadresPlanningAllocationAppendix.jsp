<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page import="net.sf.json.JSONArray"%>
<%@include file="/comOpenWinInit.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>导入文件</title>
	<odin:MDParam></odin:MDParam>
    <script src="<%=request.getContextPath()%>/basejs/jquery-ui/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
    <script src="<%=request.getContextPath()%>/jwjc/js/underscore.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/jwjc/js/common.js" charset="utf-8"></script>

<table>
	<tr><td height="15">&nbsp;</td></tr>
</table>
<form id="formId" name="data" method="post"  action="<%=request.getContextPath()%>/DownloadServlet?method=appendixUpload" enctype="multipart/form-data">
	<table align="center" width="96%">	
		<tr>
			<td width="50"></td>
			<td>
		       <iframe id="frame" name="frame" height="33px" width="330px" src="<%=request.getContextPath() %>/pages/sysorg/org/adfile2.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
			</td>
			<td>
				<img src="<%=request.getContextPath()%>/images/go1.jpg" onclick="sc()">
			</td>
			<td width="50"></td>
		</tr>
		<tr>
			<td height="6"></td>
		</tr>
	</table>
		
	<odin:hidden property="pid"/>
</form>
<table style="width: 100%;">
	<tr>
		<td>
			<odin:editgrid2 property="fileGrid" bbarId="pageToolBar" autoFill="false" height="387"
				 hasRightMenu="false" url="/">
				<odin:gridJsonDataModel id="name" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="add00"/>
					<odin:gridDataCol name="filename" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn2 />
					<odin:gridEditColumn2 dataIndex="filename" width="500" editor="text" edited="false" header="附件名称"/>
					<odin:gridEditColumn2 dataIndex="operateU" width="110" header="操作" editor="text" edited="false" 
						menuDisabled="true" sortable="false" align="center" renderer="operateUod" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid2>
		</td>
	</tr>
</table>

<script>
Ext.onReady(function() {
	document.getElementById('pid').value = document.getElementById('subWinIdBussessId2').value;
	// alert(document.getElementById('pid').value);
});

// 点击提交
function sc(){
	var pid = document.getElementById('pid').value;
	window.frames['frame'].imp(pid);
	//frame.window.imp(pid);
}
function gg() {
	//window.close();
	//var parentWin = window.opener;
    realParent.radow.doEvent("galeGrid.dogridquery");
    realParent.radow.doEvent("fileGrid.dogridquery");
}
function fg() {
	//window.close();
	//var parentWin = window.opener;
    radow.doEvent("fileGrid.dogridquery");
    radow.doEvent("fileGridd.dogridquery");
}
function gk() {
	//window.close();
	//var parentWin = window.opener;
    realParent.radow.doEvent("initX");
}

//点击关闭,前面页面 附件内容更新
function colseWin( html ){
	//更新前一页面
	if(realParent.document.getElementById("div_fujian")!=null){
		realParent.document.getElementById("div_fujian").innerHTML =html;
	}
	//window.close();
}

// grid操作列显示的操作情况
function operateUod(value, params, record,rowIndex,colIndex,ds){
	return "<a href=\"javascript:downloadRow('"+rowIndex+"')\">下载</a>&nbsp;" + 
	"&nbsp;<a href=\"javascript:deleteRow('"+rowIndex+"')\">删除</a>&nbsp;";
}

// 点击下载按钮
function downloadRow(rowIndex) {
	radow.doEvent("downloadFile", rowIndex);
}

// 下载
function downloadFile(url) {
	var downfile = url;
	//w = window.open("ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile)));
	window.location.href="ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile));
}

// 删除
function deleteRow(rowIndex) {
	// 确认删除
	$h.confirm("系统提示：",'是否确认删除？',200,function(id) { 
		if("ok"==id){
			radow.doEvent("deleteFile", rowIndex);
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