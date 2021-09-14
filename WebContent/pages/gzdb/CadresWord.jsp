<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<html>
<head>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script src="<sicp3:rewrite forward='md5'/>"> </script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<script type="text/javascript"src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>

<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>导入文件</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
<style type="text/css">
</style>
</head>
<%@include file="/comOpenWinInit.jsp" %>
<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>
<table>
<td width="40%" >
<table >
	<tr>
		<td>
			<odin:editgrid2 property="fileGrid1" bbarId="pageToolBar" autoFill="false" height="127"
				 hasRightMenu="false" url="/">
				<odin:gridJsonDataModel id="name" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="filename"/>
					<odin:gridDataCol name="fileurl"/>
					<odin:gridDataCol name="dhxjid"/>
					<odin:gridDataCol name="operateU1" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn2 />
					<odin:gridEditColumn2 dataIndex="filename" width="400" editor="text" edited="false" header="选举材料" renderer="file"/>
					<odin:gridEditColumn2 dataIndex="operateU1" width="100" header="操作" editor="text" edited="false" 
						 align="center" renderer="operateUod1" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid2>
		</td>
		<%-- <td >
			<odin:textEdit width="216" inputType="file" colspan="4"  property="excelFile" label="选举材料" ></odin:textEdit> 
		</td> --%>
	</tr>
	<tr>
		<td>
			<odin:editgrid2 property="fileGrid2" bbarId="pageToolBar" autoFill="false" height="127"
				 hasRightMenu="false" url="/">
				<odin:gridJsonDataModel id="name" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="jfilename"/>
					<odin:gridDataCol name="jfileurl"/>
					<odin:gridDataCol name="dhxjid"/>
					<odin:gridDataCol name="operateU2" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn2 />
					<odin:gridEditColumn2 dataIndex="jfilename" width="400" editor="text" edited="false" header="选举结果" renderer="filej"/>
					<odin:gridEditColumn2 dataIndex="operateU2" width="100" header="操作" editor="text" edited="false" 
						 align="center" renderer="operateUod2" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid2>
		</td>
		<%-- <td>
			<odin:textEdit width="216" inputType="file" colspan="4"  property="excelFilej" label="选举结果" ></odin:textEdit> 
		</td> --%>
	</tr>
	<tr>
		<td>
			<odin:editgrid2 property="fileGrid3" bbarId="pageToolBar" autoFill="false" height="127"
				 hasRightMenu="false" url="/">
				<odin:gridJsonDataModel id="name" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="hfilename"/>
					<odin:gridDataCol name="hfileurl"/>
					<odin:gridDataCol name="dhxjid"/>
					<odin:gridDataCol name="operateU3" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn2 />
					<odin:gridEditColumn2 dataIndex="hfilename" width="400" editor="text" edited="false" header="会议材料" renderer="fileh"/>
					<odin:gridEditColumn2 dataIndex="operateU3" width="100" header="操作" editor="text" edited="false" 
						 align="center" renderer="operateUod3" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid2>
		</td>
		<%-- <td>
			<odin:textEdit width="216" inputType="file" colspan="4"  property="excelFileh" label="会议材料" ></odin:textEdit> 
		</td> --%>
	</tr>
</table>
</td>
<td width="30%" >
<form name="data" id="data" method="post"  action="<%=request.getContextPath()%>/PublishFileServlet?method=addMeetingElection"  enctype="multipart/form-data">
	<table  align="center" >	
	<tr>
		<%-- <td >
		<odin:textEdit width="156" inputType="file" colspan="4"  property="excelFile" label="选举材料" ></odin:textEdit> 
		<odin:textEdit width="156" inputType="file" colspan="4"  property="excelFilej" label="选举结果" ></odin:textEdit>
		<odin:textEdit width="156" inputType="file" colspan="4"  property="excelFileg" label="会议材料" ></odin:textEdit> 
		</td>  --%>
		<tr >
		       <iframe id="frame" name="frame" height="413px" width="296px" src="<%=request.getContextPath() %>/pages/sysorg/org/adfile3.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
		</tr>
		<tr >
			<td style="width:40%">
			<img src="<%=request.getContextPath()%>/images/go1.jpg" onclick="sc()">
			</td>
		</tr>
	</tr>
	
	<%-- <tr>
			<td>
				<img src="<%=request.getContextPath()%>/images/go1.jpg" onclick="scc()">
			</td>
	</tr>  --%>
	</table>
	<odin:hidden property="dhxjid"/>
</form>
</td>
</table>
<odin:hidden property="dhxjid"/>
<odin:hidden property="xjqy"/>
<script>
Ext.onReady(function() {
	document.getElementById('dhxjid').value= document.getElementById('subWinIdBussessId').value;
});

// 点击提交
function sc(){
	var pid = document.getElementById('dhxjid').value;
	window.frames['frame'].impp(pid);
	//frame.window.imp(pid);
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
//来文原件
function filej(value, params, rs, rowIndex, colIndex, ds){
	var fileurl = rs.get('jfileurl');
	var filename = rs.get('jfilename');
	
	 if(filename != null && filename != ''&& fileurl!=null && fileurl!=''){
		 var url=fileurl.replace(/\\/g,"/");
		return "<a href=\"javascript:downloadFile('" +url +"')\">"+filename+"</a>";
	} 
} 
//来文原件
function fileh(value, params, rs, rowIndex, colIndex, ds){
	var fileurl = rs.get('hfileurl');
	var filename = rs.get('hfilename');
	
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
function operateUod1(value, params, record,rowIndex,colIndex,ds){
	//var sh000 = record.get('sh000');
	var filename = record.get('filename');
	if(filename!=null){
		return "<a href=\"javascript:deleteRow1('"+rowIndex+"')\">删除</a>&nbsp;";
	}
}
//grid操作列显示的操作情况
function operateUod2(value, params, record,rowIndex,colIndex,ds){
	var jfilename = record.get('jfilename');
	if(jfilename!=null){
		return "<a href=\"javascript:deleteRow2('"+rowIndex+"')\">删除</a>&nbsp;";
	}
}
//grid操作列显示的操作情况
function operateUod3(value, params, record,rowIndex,colIndex,ds){
	var hfilename = record.get('hfilename');
	if(hfilename!=null){
		return "<a href=\"javascript:deleteRow3('"+rowIndex+"')\">删除</a>&nbsp;";
	}
}

// 点击下载按钮
function downloadRow(rowIndex) {
	radow.doEvent("downloadFile", rowIndex);
}

//点击上传按钮
function dRow(giid){
	window.frames['frame'].impp(giid);
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
function deleteRow1(rowIndex) {
	// 确认删除
	$h.confirm("系统提示：",'是否确认删除？',200,function(id) { 
		if("ok"==id){
			radow.doEvent("deleteFile1", rowIndex);
			window.frames['frame'].imd();
		}else{
			return false;
		}		
	});
}
//删除
function deleteRow2(rowIndex) {
	// 确认删除
	$h.confirm("系统提示：",'是否确认删除？',200,function(id) { 
		if("ok"==id){
			radow.doEvent("deleteFile2", rowIndex);
			window.frames['frame'].imd();
		}else{
			return false;
		}		
	});
}
//删除
function deleteRow3(rowIndex) {
	// 确认删除
	$h.confirm("系统提示：",'是否确认删除？',200,function(id) { 
		if("ok"==id){
			radow.doEvent("deleteFile3", rowIndex);
			window.frames['frame'].imd();
		}else{
			return false;
		}		
	});
}
function gg1() {
    radow.doEvent("fileGrid1.dogridquery");
    realParent.radow.doEvent("memberGrid.dogridquery");
}
function gg2() {
    radow.doEvent("fileGrid2.dogridquery");
    realParent.radow.doEvent("memberGrid.dogridquery");
}
function gg3() {
    radow.doEvent("fileGrid3.dogridquery");
    realParent.radow.doEvent("memberGrid.dogridquery");
}
function gg() {
    realParent.radow.doEvent("memberGrid.dogridquery");
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
function scc(){
	
	var file = document.getElementById('excelFile').value;
	var file1 = document.getElementById('excelFileg').value;
	var file2 = document.getElementById('excelFilej').value;
	
	if(file !=""){
		var dhxjid = encodeURI(document.getElementById('dhxjid').value);
		//var wcbz = encodeURI(document.getElementById('wcbz').value);
		//odin.ext.get(document.body).mask('正在上传数据并处理中......', odin.msgCls);
		var path = '<%=request.getContextPath()%>/PublishFileServlet?method=addMeetingElection&dhxjid='+encodeURI(dhxjid);
		odin.ext.Ajax.request({
			url:path,
			isUpload:true,
			method:'post',
			fileUpload:true,
			form:'data',
			success:function(data){
				parent.odin.info("新增大会选举成功!");
				//parent.radow.doEvent("memberGrid.dogridquery");
				realParent.odin.ext.getCmp('memberGrid').store.reload();
				parent.odin.ext.getCmp('addWin').close();
				realParent.parent.gzt.window.location.reload();
			}
		});
		
	}else{
		odin.info('文件必须上传！');
	}
	
}
function scc1(){
	var file1 = document.getElementById('excelFile').value;
	
	if(file1 !=""){
		var dhxjid = encodeURI(document.getElementById('dhxjid').value);
		//var wcbz = encodeURI(document.getElementById('wcbz').value);
		//odin.ext.get(document.body).mask('正在上传数据并处理中......', odin.msgCls);
		odin.ext.Ajax.request({
			url:'<%=request.getContextPath()%>/PublishFileServlet?method=addMeetingElection&dhxjid='+encodeURI(dhxjid),
			isUpload:true,
			method:'post',
			fileUpload:true,
			form:'formId',
			success:function(){
				parent.odin.info("新增大会选举成功!");
				//parent.radow.doEvent("memberGrid.dogridquery");
				realParent.odin.ext.getCmp('memberGrid').store.reload();
				parent.odin.ext.getCmp('addWin').close();
				realParent.parent.gzt.window.location.reload();
			}
		});
		
	}else{
		odin.info('文件必须上传！');
	}
	
}
function scc2(){
	var file2 = document.getElementById('excelFilej').value;
	
	if(file2!=""){
		var dhxjid = encodeURI(document.getElementById('dhxjid').value);
		//var wcbz = encodeURI(document.getElementById('wcbz').value);
		//odin.ext.get(document.body).mask('正在上传数据并处理中......', odin.msgCls);
		odin.ext.Ajax.request({
			url:'<%=request.getContextPath()%>/PublishFileServlet?method=addMeetingElection&dhxjid='+encodeURI(dhxjid),
			isUpload:true,
			method:'post',
			fileUpload:true,
			form:'formId',
			success:function(){
				parent.odin.info("新增大会选举成功!");
				//parent.radow.doEvent("memberGrid.dogridquery");
				realParent.odin.ext.getCmp('memberGrid').store.reload();
				parent.odin.ext.getCmp('addWin').close();
				realParent.parent.gzt.window.location.reload();
			}
		});
		
	}else{
		odin.info('文件必须上传！');
	}
	
}
function scc3(){
	var file3 = document.getElementById('excelFileg').value;
	
	if(file3 !=""){
		var dhxjid = encodeURI(document.getElementById('dhxjid').value);
		//var wcbz = encodeURI(document.getElementById('wcbz').value);
		//odin.ext.get(document.body).mask('正在上传数据并处理中......', odin.msgCls);
		odin.ext.Ajax.request({
			url:'<%=request.getContextPath()%>/PublishFileServlet?method=addMeetingElection&dhxjid='+encodeURI(dhxjid),
			isUpload:true,
			method:'post',
			fileUpload:true,
			form:'formId',
			success:function(){
				parent.odin.info("新增大会选举成功!");
				//parent.radow.doEvent("memberGrid.dogridquery");
				realParent.odin.ext.getCmp('memberGrid').store.reload();
				parent.odin.ext.getCmp('addWin').close();
				realParent.parent.gzt.window.location.reload();
			}
		});
		
	}else{
		odin.info('文件必须上传！');
	}
	
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
</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>