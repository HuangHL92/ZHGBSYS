<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<%String ctxPath = request.getContextPath(); 
String sign = request.getParameter("sign");
%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.TableColInterface"%>
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery-1.11.3.js"></script>
<script src="<%=request.getContextPath()%>/basejs/jwplayer/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/basejs/jwplayer/jwplayer.js"></script>

<style>
#container{margin:0 auto; text-align:center;}
body {
	background-color: rgb(214,227,243);
}
.dasda td{width: 120px;padding-bottom: 5px;}
</style>
<odin:toolBar property="toolBar8">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="上传并保存" id="impBtn" icon="images/save.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="新增" id="TrainAddBtn" isLast="true" icon="images/add.gif"></odin:buttonForToolBar>
</odin:toolBar>
<table class="dasda" style="width:40%;margin-top: 30px;margin-left: -20ox;">
	<tr>
		<td style="align:left">
		<odin:textEdit property="jsa01" label="日期" ></odin:textEdit>
		<odin:textEdit property="jsa02" label="标题" ></odin:textEdit>
		<td>
	</tr>
</table>
<table id="ukccl" style="width: 40%;margin-left: 100px;margin-top: -20px;">
 <tr style="height: 100px">
  <tags:JUpload2 property="file5" label="选择文件" fileTypeDesc="文件类型"  colspan="1" 
  uploadLimit="1" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
 </tr>
 <%--<tr>
	<td align="right" style="padding-right: 20px;">
		<odin:button text="上传" property="impBtn" />
	</td>
	<td align="right" style="padding-right: 20px;">
		<div id="downDiv">
		<odin:button text="下载"  property="downBtn" />
		</div>
	</td>	 
 </tr>--%>
</table>
	<odin:grid property="TrainInfoGrid" sm="row" isFirstLoadData="false" url="/" topBarId="toolBar8"
				height="500" autoFill="false"  >
					<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="delete" />
						<odin:gridDataCol name="jsa00" />
						<odin:gridDataCol name="jsa01" />
						<odin:gridDataCol name="jsa02" />
						<odin:gridDataCol name="jsa03" />
						<odin:gridDataCol name="jsa04" />
						<odin:gridDataCol name="jsa05" />
						<odin:gridDataCol name="jsa06" />
						<odin:gridDataCol name="jsa07" />
						<odin:gridDataCol name="comm"/>
						<odin:gridDataCol name="play" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
					  <odin:gridRowNumColumn />
					  <odin:gridEditColumn2 width="45" header="操作" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" />					  
					  <odin:gridEditColumn2 header="主键" dataIndex="jsa00" editor="text" edited="false" hidden="true"/>
					  <odin:gridEditColumn2 header="日期" align="center" dataIndex="jsa01" editor="text" edited="false" width="100"/>
					  <odin:gridEditColumn2 header="标题" align="center" dataIndex="jsa02" editor="text" edited="false" width="300"/>
					  <odin:gridEditColumn2 header="附件名称" align="center" dataIndex="jsa03" editor="text" edited="false" width="120" hidden="true"/>
					  <odin:gridEditColumn2 header="上传人" align="center" dataIndex="jsa04" editor="text" edited="false" width="120"/>
					  <odin:gridEditColumn2 header="上传时间" align="center" dataIndex="jsa05" editor="text" edited="false" width="120" hidden="true"/>
					  <odin:gridEditColumn2 header="附件地址" align="center" dataIndex="jsa06" editor="text" edited="false" width="120" hidden="true"/>
					  <odin:gridEditColumn2 header="文件大小" align="center" dataIndex="jsa07" editor="text" edited="false" width="120"/>
					  <odin:gridEditColumn2 header="下载" align="center" dataIndex="comm" renderer="showRenderer" editor="text" edited="false" width="100"/>
					  <odin:gridEditColumn2 header="播放" align="center" dataIndex="play" renderer="playRenderer" editor="text" edited="false" width="100" isLast="true"/>
					</odin:gridColumnModel>
			</odin:grid>
<odin:hidden property="a0000" title="人员主键"/>
<odin:hidden property="jsa00" title="主键id" />	
<odin:hidden property="title" title="标题" />	
<odin:hidden property="docpath"/>
</body>
<script type="text/javascript">


var fieldsDisabled = <%=TableColInterface.getUpdateDataByTable("A11",sign)%>;
var selectDisabled = <%=TableColInterface.getSelectDataByTable("A11")%>;
<%-- Ext.onReady(function(){
	//对信息集明细的权限控制，是否可以维护 
	$h.fieldsDisabled(fieldsDisabled); 
	//对信息集明细的权限控制，是否可以查看
	//var imgdata = "<img src='<%=request.getContextPath()%>/image/quanxian1.png' />";
	var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
	$h.selectDisabled(selectDisabled,imgdata); 
}); --%>
function inita1151(value, params, record,rowIndex,colIndex,ds){
	if(value==0){
		return "<span>否</span>";
	}else if(value==1){
		return "<span>是</span>";
	}else if(value==undefined||value==null||value==''){
		return "<span></span>";
	}else{
		return "<span>异常</span>";
	}
}

Ext.onReady(function(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;

});
function AddBtn(){
	radow.doEvent('TrainAddBtn.onclick');
}
function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var jsa00 = record.data.jsa00; 
	return "<a href=\"javascript:deleteRow2(&quot;"+jsa00+"&quot;)\">删除</a>";
}
function deleteRow2(jsa00){ 
	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',jsa00);
		}else{
			return;
		}		
	});	
}
function reShowMsg(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
	radow.doEvent('initX');
}

function lockINFO(){
	Ext.getCmp("save").disable(); 
	Ext.getCmp("TrainAddBtn").disable(); 
	Ext.getCmp("TrainInfoGrid").getColumnModel().setHidden(1,true); 
}
function showRenderer(value, params, record,rowIndex,colIndex,ds){
	var jsa00 = record.data.jsa00;
	<%-- return "<img id=\""+a1700+"\" alt=\"\" src=\"<%= request.getContextPath()%>/image/movie.png\" onclick=\"printView('"+a1700+"')\" style=\"cursor:pointer\">"; --%>
	return "<a href=\"javascript:printView(&quot;"+jsa00+"&quot;)\">下载</a>";
}

function printView(jsa00){
	radow.doEvent("downBtn",jsa00);
}

function playRenderer(value, params, record,rowIndex,colIndex,ds){
	var jsa00 = record.data.jsa00;
	<%-- return "<img id=\""+a1700+"\" alt=\"\" src=\"<%= request.getContextPath()%>/image/movie.png\" onclick=\"printView('"+a1700+"')\" style=\"cursor:pointer\">"; --%> 	
	return "<a href=\"javascript:playView(&quot;"+jsa00+"&quot;)\">播放</a>";
 
 	<%--return "<a href="+<%=request.getContextPath()%>/publicServantManage/PlayInVodeo.jsp+">播放</a>";--%>
}

function playView(jsa00){
	radow.doEvent("playBtn",jsa00);
}

function onUploadSuccess_new(isClose){//上传后  
	if($('#file5').data('uploadify').queueData.queueLength<=1 || isClose){
		//parentParam.$tr.children("td:nth-child(2)").removeClass("default_color").addClass("kcclclass")
		//window.close();
		Ext.MessageBox.alert('消息提示', '上传成功！');
	}	
}

function afterDelete(fileID){//删除后
	parentParam.$tr.children("td:nth-child(2)").addClass("default_color").removeClass("kcclclass")
	window.close();
}
function wait(){
	if($('#file5').data('uploadify').queueData.queueLength>=1){
		Ext.MessageBox.wait('正在上传文件并且导入，请稍后。。。');
	}
}
function downloadByUUID(url) 
{   
	var downloadUUID = $("#docpath").val();
	//alert($('#iframe_expKccl').attr("id")+"  "+downloadUUID)
	$('#iframe_expKccl').attr('src','<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID);
	return false
}

</script>
<odin:window src="/blank.htm" id="ExtPadData" width="1200" height="800"></odin:window> 
<iframe  id="iframe_expKccl" style="display: none;" src=""></iframe>
<div id="extPadPanel" style="display:none">
        <div id="container">loading the player...</div>
        <div id="brief" style="width:1180px;height: 400px;background: red"></div>
</div>
<script>

var thePlayer; 
jwplayer.key="hTHv8+BvigYhzJUOpkmEGlJ7ETsKmqyrQb8/PetBTBI=";		
$(function() {  
                
}); 
/* function showExtPadData(filepath){
	alert(filepath);
var noticeWindow = odin.ext.getCmp('ExtPadData');
	noticeWindow.show(noticeWindow);
	var obj = document.getElementById('extPadPanel');
	noticeWindow.body.dom.innerHTML='';
    thePlayer = jwplayer('container').setup({ 
	    autostart: true,
        flashplayer: 'js/jwplayer/jwplayer.flash.swf',  
        file: filepath,  
        width: 1180,  
        height: 400,  
		volume: 80
    });    
	noticeWindow.body.appendChild(obj);
	obj.style.display="block";
}  */
 function showExtPadData(filepath){
	 window.location.href="<%=ctxPath%>"+"/pages/publicServantManage/PlayInVideo.jsp?path="+filepath+""
}

</script>
<%
/*
//HttpSession session = request.getSession();
final HttpSession sessionitem = session;
Thread t = new Thread(){
	@Override
	public void run() {
		try {
			while (true) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("上一次访问session的时间"+new java.util.Date(sessionitem.getLastAccessedTime()));
				System.out.println("session的有效时长："+sessionitem.getMaxInactiveInterval());
				System.out.println("session的剩余时长："+(sessionitem.getMaxInactiveInterval()-((System.currentTimeMillis()-sessionitem.getLastAccessedTime()))/1000));
				if(sessionitem==null){
					System.out.println("session为空");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.run();
	}
};
t.start();
t.join();
*/
%>