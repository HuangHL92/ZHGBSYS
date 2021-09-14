<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript">
function commGridCol(value, params, record, rowIndex, colIndex, ds){
	var status = record.get("status");
	if(status=='1'){
		return "<a href='#' onclick='isCancelPJob(\""+value+"\")'>取消执行</a>";
	}else{
		return "执行结束";
	}
}
function isCancelPJob(id){
	odin.confirm("您确定要取消该并发任务的执行吗？",function(v){
		if(v=="ok"){
			radow.doEvent("cancelPJob",id);
		}
	});
}
var pbar = null; //进度条对象
var cuePjob = null;
function startProgress(pjob,pjobRun){
	cuePjob = pjob;
	var s = pjobRun.status;
	if("1"==s){
		var html = "任务名为“"+pjob.name+"”，开始运行时间为“"+odin.Ajax.formatDateTime(pjobRun.startdate)+"”，正在执行中。";
		document.getElementById("basicInfo").innerHTML = html;
		pbar = new Ext.ProgressBar({
			width:600,
			renderTo :'PBar'
		});
		window.setTimeout(function(){
			radow.doEvent("updateProgress");
		},2000);
	}
}
function updateProgress(progress,timeMsg){
	var update_progress = odin.round(progress,2);
	console.log(progress+"||"+update_progress);
	pbar.updateProgress(update_progress/100,update_progress+'%,耗时：'+timeMsg,true);
	if(progress<100){
		window.setTimeout(function(){
			radow.doEvent("updateProgress");
		},2000);
	}else{
		pbar.updateProgress(100,"已完成,总耗时："+timeMsg,true);
		document.getElementById("basicInfo").innerHTML = "任务名为“"+cuePjob.name+"”，已经执行完成！";
		odin.ext.getCmp("pjobRunGrid").store.reload();
	}
}
</script>
<input id="pjobName" name="pjobName" value="<%=request.getParameter("initParams")%>" type="hidden"/>
<odin:groupBox title="最新进度监控">
<div id="basicInfo" style="color: red;font-size: 12px;font-weight: bold"></div>
<div id="PBar" style="margin-top: 6px;"></div>
</odin:groupBox>
<odin:groupBox title="执行历史">
<odin:editgrid property="pjobRunGrid" sm="row" bbarId="pageToolBar" hasRightMenu="false" isFirstLoadData="false" url="/" autoFill="false" forceNoScroll="false" title="" height="200" >
<odin:gridJsonDataModel  id="id">
  <odin:gridDataCol name="id"/>
  <odin:gridDataCol name="pjobname"/>
  <odin:gridDataCol name="startdate"/>
  <odin:gridDataCol name="enddate"/>
  <odin:gridDataCol name="status"/>
  <odin:gridDataCol name="loginname"/>
  <odin:gridDataCol name="username"/>
  <odin:gridDataCol name="changereason" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn></odin:gridRowNumColumn>
  <odin:gridEditColumn 	header="开始时间" align="center" width="90" dataIndex="startdate" edited="false" editor="text"/>
  <odin:gridEditColumn 	header="结束时间" align="center" width="90" dataIndex="enddate" editor="text" edited="false"/>
  <odin:gridEditColumn 	header="执行状态" align="center" width="80" edited="false" dataIndex="status" editor="select" codeType="PJOBSTATUS"/>
  <odin:gridEditColumn 	header="操作人" align="center" dataIndex="username" width="80" editor="text" edited="false"/>
  <odin:gridEditColumn 	header="结束原因" width="150" width="150" dataIndex="changereason" editor="text" edited="false"/>
  <odin:gridEditColumn 	header="操作" align="center" dataIndex="id" editor="text" edited="false" width="120" isLast="true" renderer="commGridCol"/>
</odin:gridColumnModel>		
</odin:editgrid>
</odin:groupBox>
